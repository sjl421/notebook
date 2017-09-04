# spark

`spark`依赖`hadoop`的`hdfs`和`yarn`两个重要的分布式组件, 一个用于分布式存储, 另一个用于分布式的资源调度.

`spark`本身的安装要简单很多. 同样, 因为是分布式, 所以运行的每个节点都要有安装包. 只有一个节点是`master`节点, 其余的节点是`worker`. `master`节点启动后, 会启动一个监听端口, 如`spark://master:7077`(默认), 在启动`worker`时, 要指定这个端口.

通过`sbin/start-master.sh`来启动`master`进程, `sbin/start-slave.sh spark://master:7077` 来启动`worker`进程. 实际上, 并不关心`master`和`worker`是否启动在同一机器上.

同样, 要想在启动`master`时, 自动启动`worker`, 则需要`conf/slaves`文件. 同`hadoop`一样. 默认情况下, 写入`localhost`, 然后执行`sbin/start-all.sh`即会启动所有.

需要注意的是, `spark`如果要依赖`yarn`和`hdfs`, 则需要依赖`hadoop`的配置, 即需要知道`hadoop`的配置文件目录, 你可以在`.bashrc`中定义, 或者在`conf/spark-env.sh`中定义. 后者需要多定义`JAVA_HOME`变量. 虽然同样不明白, 包括`etc/hadoop/hadoop-env.sh`文件, 明明已经定义过了`JAVA_HOME`, 且明确`export`了, 此两个文件还是需要重新定义.

在`conf/spark-env.sh`除了定义`JAVA_HOME`,还需要定义`HADOOP_CONF_DIR`变量,指向`Hadoop`的配置文件目录.

要在`yarn`中运行应用, 直接指定`--master yarn`即可. 虽然还涉及一个部署模式`--deploy-mode`其有两个值`client`和`cluster`. 主要区别是, 驱动`driver`进程是在集群`cluster`外执行, 还是在内执行.

`spark-shell`只有`client`模式, 所以直接`spark-shell --master yarn`即可.

对于运行`standalone`模型下的`spark`, 在`8080`端口打开`master`的`web`界面, 在`8081`端口可打开`worker`的`web`界面. 如果运行了`spark-shell`, 则在`4040`端口打开`shell`界面. `master`的`web`界面可显示正在执行的应用和相应的执行进度.  然而如果使用了`yarn`作为资源调度, 则应用的执行进度, 只有在`8088`的`yarn`界面才能看到.

运行示例程序:

```sh
./bin/spark-submit --class org.apache.spark.examples.SparkPi \
--master yarn --deploy-mode cluster examples/jars/spark-examples*.jar 10
```

`--master local[N]`, `--master spark://ip:7077`

当在集群运行时,如果有依赖包,可以通过`--jars a1.jar a2.jar`来引入.

当希望在`--master yarn`运行时, 你可能需要设定`spark.yarn.jars`属性, 在`conf/spark-defaults.conf`文件中. 值为`hdfs`的路径, 但注意`hdfs:///jars/*`, 即最终不是目录, 是`jar`. 除了此问题外, 另一问题, 通过查看`history`, 会遇到`Container is running beyond virtual memory limits`, 即运行超出虚拟内存, 虽然不知道具体机制, 目前可以通过`spark.yarn.am.memory 2g`解决.

一般`spark`的日志在安装目录的`logs`目录下, 但应用运行的日志并不在, 所以如果查看应用为什么运行出错, 需要配置`spark.eventLog.enabled true`和`spark.eventLog.dir hdfs:///logs`两个选项(注, 请创建`logs`目录), 这样就可以在`8088`端口的应用运行条目的`history`查看失败信息了. `8088`端口是`yarn`的资源管理器端口.

配置方面, 应用内`SparkCOnf`优先级最高, 但不灵活, 传递给`spark-submit`的命令行选项次之, 但命令太长, `spark-defaults.conf`最低. 但所以的配置, 都可以在应用的`hosts:4040`页面`Environment`查看, 多应用情况下, 可能`404x`.

## 基本概念理解

`job`: 作业, 如所知, `spark`执行`transformation`时并不真正计算, 只有在遇见一个`action`才计算, 即每个`action`触发一个`job`.

`stage`: 阶段, 如所知, `spark`的`RDD`有宽窄两种依赖, 依据父`RDD`中一个分区的数据是否进入子`RDD`中不同分区中. 即宽依赖和窄依赖的边界是`stage`的划分点. 父`RDD`的分区与子`RDD`的分区, 若为`1:1`或`N:1`, 则为窄依赖; 若为`1:N`, 则为宽依赖.

`task`: 任务, 如所知, `spark`的`RDD`有多个分区, 分区可能在不同主机上, 具体的计算任务是在分区上进行的, 即一个分区执行的计算, 为一个`task`.

如上, 一个应用包含多个`job`, 一个`job`包含多个`stage`, 一个`stage`包含多个`task`. 只有`task`是并行的.

关于部属模式, 有两种: `cluster`称为集群部属, `client`称为客户端部属, 关键的区别是`driver`运行的位置. 通常, 总是需要执行`spark-submit`来提交应用, `driver`运行在提交应用的主机, 则为`client`, 运行在集群上则为`cluster`. 但要注意, 因为`driver`执行有`SparkConf`负责各执行器的协调, 即需要与执行器通信, 所以提交主机通常与别的节点的通信成本不能太高.

对`yarn`来说, 执行器和应用主进程都运行在容器中.

## 配置

总是可以通过`--conf PROP-VALUE`来指定以下属性, 也可以写入`conf/spark-default.conf`文件, 此文件可以通过`--properties-file`属性改变.

* `spark.driver.memory`, 配置驱动器内存, 默认1G, 相当于`--driver-memory`
* `spark.executor.memory`, 配置每个执行器的内存, 默认1G, 相当于`--executor-memory`
* `spark.driver.maxResultSize`, 每个"动作"产生分区的序列化结果的限制大小, 默认1G
* `spark.local.dir`, 写中间数据的目录, 可以`,`分割, 可配置多路径到多磁盘来增加`IO`带宽

* `spark.app.name`, 指定应用名称, 相当于`--name`
* `spark.master`, 指定`master`链接, 相当于`--master`

运行时:

* `spark.driver.extraClassPath`, 额外的类路径, 前缀, 不能在`SparkCOnf`设置, 已经这时候`JVM`已经启动. 相当于`--driver-class-path`
* `spark.driver.extraJavaOption`, 额外的`Java`选项. 不能设置最大堆大小, 可`cluster`设置`spark.driver.memory`, `client`设置`--driver-memory`, 相当于`--driver-java-options`
* `spark.driver.extraLibraryPath`, 额外的库路径, 此`库`指各种`.so`文件等. 相当于`--driver-library-path`

执行器有对应的`spark.executor.xxx`.

日志相关: `spark.executor.logs.rolling`, 设置日志滚动

* `maxRetainedFiles`, 日志文件的数量, 超过则清理旧文件
* `enableCompression`, 是否允许压缩
* `maxSize`, 滚动的大小限制, 即超过此大小, 滚动日志, 字节计
* `strategy`, 滚动策略, 基于时间`time`还是基于大小`size`
* `time.interval`, 滚动时间间隔, 即过此时间滚动

`spark.executorEnv.[EnvironmentVariableName`, 为执行器指定环境变量

* `spark.jars`, 将包含进执行器和驱动器的类路径, 相当于`--jars`
* `spark.jars.packages`, 将`maven`坐标指定的包, 包含进执行器和驱动器的类路径, 相当于`--packages`
* `spark.jars.excludes`, 去掉指定的`groupId:artifactId`, 用于解析`spark.jars.packages`依赖时, 避免依赖冲突, 相当于`--exclude-packages`
* `spark.jars.ivy`, 额外的远程仓库, 相当于`--repositories`

`spark.eventLog.compress/dir/enabled`, 事件日志

`spark.ui`方面:

* `enabled`: 是否为应用运行`Web UI`
* `killEnabled`: 是否允许作业和阶段从`Web UI`中杀掉
* `port`: 端口, 默认4040
* `retainedJobs/Stages/Tasks/DeadExecutors`, 在垃圾收集前, `Spark UI`和状态`API`能记住的数量
* `reverseProxy`, 是否设置逆向代理, 让`master`代理`worker`的`URL`, 需要为所有的`Worker`设置
* `reverseProxyUrl`, 代理运行的`URL`

`spark.worker.ui.retainedExecutors/Drivers`, 在垃圾收集前, `Spark UI`和状态`API`能记住的运行完成的`executors`

`spark.sql.ui.retainedExecutions`

`spark.streaming.ui.retainedBatches`

压缩和序列化:

* `broadcast.compress`: 在发送广播变量时是否压缩, 默认真
* `io.compression.codec`: 压缩内部数据(`RDD`分区,广播变量,`shuffle`输出)的编码, 有`lz4`, `lzf`, `snappy`, 默认`lz4`
* `io.compression.lz4/snappy.blockSize`, 压缩时的块大小, 默认32K

序列化`kryo`:

* `classesToRegister`, 注册类
* `referenceTracking`, 序列化时是否追踪相同对象的引用, 如果对象图有循环则是必要的, 如果对象包含多个副本则也利于效率.

`spark.rdd.compress`, 是否压缩序列化的`RDD`

执行行为

* `executor.cores`, 每个执行器的`CPU`核心数量, 在`standalone`和`Mesos`模式下, 可允许一个应用在同一个`worker`运行多个执行器, 除此外, 每个应用在每个`worker`只能有一个执行器
* `default.parallelism`, 默认的并行数, 即默认的分区数. 对分布式`shuffle`, 如`reduceByKey`和`join`, 是父`RDD`中最大的分区数, 对无父`RDD`的`parallelize`操作, 依赖于集群管理器, 本地模式为`CPU`的核心数, `Mesos`为8, 其他为在所有执行器节点的总核心数.
* `executor.heartbeatInterval`, 心跳间隔, 默认10s.应该小于`spark.network.timeout`

`spark.driver.host/port`, 驱动器的主机和端口, 用于执行器与`Master`沟通

`spark.network.timeout`, 默认120s

`spark.scheduler.mode`, 提交给同一个`SparkConf`的作业间的高度模式, 有`FIFO`, `FAIR`

## 日志级别

编辑`conf/log4j.properties`文件, 修改`log4j.rootCategory=INFO, console`中的`INFO`为任意你想要的级别, 如`WARN/ERROR`等.

或者在`spark-shell`或程序中:

```scala
import org.apache.log4j.{Logger, Level}
Logger.getRootLogger().setLevel(Level.WARN)  // #1, 以下三种取决于spark的logger怎么分布
Logger.getLogger("org").setLevel(Level.OFF)
Logger.getLogger("akka").setLevel(Level.OFF)  // #2
sc.setLogLevel("WARN")  // #3
```

## 问题

1, `SLF4J: Class path contains multiple SLF4J bindings`

`SLF4J`的`API`被设计为仅能绑定一个日志框架, 如果在类路径中发现了超过一个的绑定, 则会抛出此消息.

```xml
<exclusions>
    <exclusion>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
    </exclusion>
</exclusions>
```

```scala
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.1" exclude("xxx", "xxx")
```

2, `Failed to load class org.slf4j.impl.StaticLoggerBinder`

`SLF4J`没有合适的绑定在类路径中被发现.