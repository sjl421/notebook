# Spark源码阅读计划

运行`example`: `bin/run-example SparkPi 10`, 实际执行的是`bin/spark-submit run-example SparkPi 10`

运行`spark-shell`, 实际执行的是`bin/spark-submit --class org.apache.spark.repl.Main --name "Spark shell" "$@"`.

运行`spark-sql`, 实际执行的是`bin/spark-submit --class org.apache.spark.sql.hive.thriftserver.SparkSQLCLIDriver "$@"`.

运行`sparkR`, 实际执行的是`bin/spark-submit sparkr-shell-main "$@"`

运行`pyspark`, 实际执行的是`bin/spark-submit pyspark-shell-main --name "PySparkShell" "$@"`

而`spark-submit`实际执行的是`bin/spark-class org.apache.spark.deploy.SparkSubmit "$@"`

而`spark-class`实际执行的是`java -Xmx128m -cp $SPARK_HOME/jars/* org.apache.spark.launcher.Main "$@"`

所以, 重点是`SparkSubmit`这个对象, 其是载入`spark`应用的主入口, 并处理与`spark`依赖相关的类路径的设置, 以及提供支持不同集群管理器和部署模式的层. 在其`main`函数内, 调用了`SparkSubmitArguments`类(实际调用了`launcher.SparkSubmitOptionParser`的`java`类的`parse`函数)来做命令行参数解析和封装. 之后, 根据`action`选项直接调用相应的`submit/kill/requestStatus`函数. 在`submit`函数中, 先是为子主类(根据集群管理器和部署模式选择)准备类路径/系统属性/应用参数的载入环境(`prepareSubmitEnvironment`函数), 然后在载入环境中激活`main`函数(`runMain`函数).

* `standalone`集群管理器的`cluster`模式目前不支持`python/R`
* `local`模式下没有`cluster`模式
* `spark shell/sqlshell/thriftserver`没有`cluster`部署模式

`client`部署模式中, 直接载入应用指定的`mainClass`即可, 因此只讨论`cluster`部署模式. 通常需要一个类来包装指定的`mainClass`, 以在集群中运行.

`standalone`中, 包装类为`deploy.rest.RestSubmissionClient`(若使用`rest`客户端提交应用), 或者`deploy.Client`. `yarn`中, `deploy.yarn.Client`(若运行的是`python`, 则被包装类为`deploy.PythonRunner`, 若运行的是`R`, 为`deploy.RRunner`). `mesos`中, `deploy.rest.RestSubmissionClient`(只支持`rest`方式提交).

在`SparkSubmitArguments`类, 主要处理`spark`应用的一些选项设置, 包括设置的优先级(命令行参数 > 属性文件 > 环境变量), 其中属性文件`--properties-file`优先级高于`spark-default.conf`. 此外, 还根据`action`来做参数验证. 其定义的`handle`函数真正用来处理参数解析.


## RDD

有大量的`RDD`类型, 每个不同的类型可能有不同的`Partition`类型(只要求实现`index`方法).

`RDD`有5个基本特征

* `getPartitions`方法返回此`RDD`的分区数组
* `compute`函数, 根据分区计算该分区的元素, 返回元素的迭代器
* `getDependencies`方法返回此`RDD`的依赖集合, 一元转换, 如`rdd1.map(f)`, 依赖只有一个; 多元转换, 如`rdd1.cogroup(rdd2)`, 依赖则有多个. 依赖本身有类型, 常见的有`NarrowDependency`(抽象类, 子类`OneToOneDe)pendency`, `RangeDependency`)和`ShuffleDependency`. 依赖是对父`RDD`的包装. 如窄依赖要求子类实现`getParents`方法, 可返回指定子分区`Id`的所有父分区`Id`集合.
* 可选的`partitioner`分区器, 针对`k-v`类型的`RDD`
* 可选的`getPreferredLocations`方法, 返回指定分区的位置偏好列表, 所谓位置偏好常指主机名, 即针对此分区的`task`会优先在此主机上载入.

创建`RDD`本质上保存一个对父`RDD`的引用及此`RDD`的生成函数, `getPartitions`获取其所有分区, `getDependencies`获取其所有的依赖, 所有的窄依赖`getParents`能获取指定分区依赖的所有父分区, `compute`可据依赖和生成函数, 从指定分区的所有父分区计算此分区的元素.

有些复杂的`RDD`还有对应的`Partiton`. 无论什么`Partition`, 本质上只是一个索引.

分区只是一个标志性的存在, 本身并不代表数据.

位置偏好是针对分区而言的, 分区器是针对`RDD`而言的.

似乎所有特殊的分区类型都定义了`writeObject`方法.

### MapPartitionsRDD

几乎是最简单的`RDD`, 对父`RDD`的每个分区应用一个函数, 得到子`RDD`.

`partitioner`, 若设置了分区保护, 则使用首父的分区器, 否则`None`.

`getPartitions`返回首父的所有分区, 因为是`1:1`生成的

`compute`, 先调用父`RDD`的`iterator`迭代指定分区的元素, 再应用`f`函数.

`getDependencies`, 则调用默认的`RDD`中的, 返回构造参数`deps`, 对此`RDD`而言, 指`RDD`的辅助构造函数中, 生成的`OneToOneDependency`.

### CoalescedRDD, CoalescedRDDPartiton

在`shuffle`为`false`的情况下, 通常意味着多个父分区合并成一个子分区, 即窄依赖中的`N:1`关系.

`CoalescedRDDPartition`保存了子分区与父分区的对应关系, 即每个索引的子分区对应着的父分区的索引集合. 其定义了有趣的函数`localFraction`, 字面意为本地性分数, 用跟子分区位置偏好列表有重叠的父分区的数除以父分区的总数.

因为位置偏好列表跟具体的分区有关, 所以, 通常特殊分区类型都会保存位置偏好列表.

`CoalescedRDD`要求一个特殊的类`PartionCoalescer`, 意为分区合并器, 即输入指定的分区数和父`RDD`, 返回分区组的集合, 每个分区组被用于合并为一个子分区(即`CoalescedRDDPartition`). 其`compute`的实现即简单地将子分区对应的所有父分区的元素合并.

其实大部分情况下, `compute`想返回子分区的元素迭代器, 都会调用父`RDD`的`iterator(parentPartition)`方法, 先迭代父`RDD`的相应分区的元素.

对于`getDependencies`, 匿名构造了一个新的窄依赖实例, 实现了`getParents`方法, 也很简单, 直接返回对应分区`CoalescedRDDPartition`保存的父分区列表.

对于`getPreferredLocations`, 直接返回对应分区类型保存的位置偏好.

### ZippedPartitionsRDDx

`x`表示对几个`RDD`进行`zip`, 拉链操作.

无论是对几个`RDD`进行`zip`, 所有`RDD`的分区数都必须相同.

在`compute`时, 考虑生成函数为`f(Iterator[A], Iterator[B], ...)`, 直接在相应父`RDD`的对应分区上迭代即可.

### UnionRDD, PartitionerAwareUnionRDD

`rdd1.union(rdd2, rdd3, ...)`, 有趣的是, 若所有父`RDD`的分区器定义且相同, 则生成`PartitionerAwareUnionRDD`, 否则生成`UnionRDD`

`UnionRDD`的有趣处在于, 其每个分区对应某个父`RDD`的某个分区, `1:1`对应. 感觉就像把父`RDD`的分区累起来了一样.