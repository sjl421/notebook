# 关于spark的memory设置

配置文件中的`spark.driver.memory`只在`cluster`模式生效. 在`cluster`模式中, `spark-submit`提交应用后, 会在`Worker`或`Node Manager`中启动`Driver`, 而此时配置文件中的`spark.driver.memory`已经读入并参与`SparkContext`的初始化. 而在`client`模式下, `spark-submit`提交应用, 其运行的`JVM`进程同时也会作为`Driver`进程, 也就是说在初始化`SparkContext`前, `JVM`进程已经启动. 此时, 只能通过`--driver-memory`来指定. 而`spark.executor.memory`则无所谓, 因为无论`cluster`还是`client`, `executor`都是运行在`Worker`或`Node Manager`, 都是后启动的.

关于指定的内存, 对于`executor`, 除了计算用以外, 当调用`RDD`的`persist`缓存数据时, 也会存储在`executor`的`JVM`进程的堆内, 即堆内缓存.

可以通过`spark.driver.cores`或`--driver-cores`指定`Driver`使用的`cpu`核心数, 在`Standalone`和`YARN`模式下都默认为1, 只对`cluster`部署模式有效. 

通过`spark.executor.cores`或`--executor-cores`指定每个`Executor`使用的`cpu`核心数, `Standalone`默认使用全部, `YARN`默认为1. 可以设置后, 让`Standalone`模式下, 每个应用可以在单个`Worker`上运行多个`Executor`.

这一定程度上, 也说明对`Standalone`模式来说, 任一时刻只能运行一个应用, 每个节点只能启动一个`Executor`. 而对`YARN`来说, 则每个节点可启动多个`Executor`, 并运行多个应用, 因此可以延伸出应用的队列和对应用的调度. 

`spark.default.parallelism`用于设定默认的分区数. 本地模式等于主机的核心数, `Standalone`或`YARN`为所有执行器节点的总核心数.

对于`Standalone`, 只在`Jobs`间存在调度模式. 而对于`YARN`, 还在应用间存在调度. 默认情况下, `Standalone`的应用会使用集群全部的核心数, 不过可以通过`spark.cores.max`(`--total-executor-cores`)来限制, 或通过`spark.deploy.defaultCores`来设置默认的. 这种方式可以间接地允许多个用户同时运行应用. 不过真有这方面需求, 还是建议转`YARN`.

除了构建`fat Jar`外, 还可以有多种方式将应用的依赖加入`classpath`. 如通过`--jars`(`spark.jars`)来指定额外的`Jar`包, 通过`--packages`(`spark.jars.packages`)来指定安装在本地`.m2`目录下的`maven`格式的包, 即`groupId:artifactId:version`, 若`.m2`不在, 则会通过默认仓库或`--repositories`(`spark.jars.ivy`)指定的仓库中下载. 这些都会加入`Driver`和`Executor`的`classpath`. 另外, 还可以通过`--exclude-packaged`(`spark.jars.exclude`)来声明不包含的包, 来避免某些依赖冲突.

也可以单独地给`Driver`或`Executor`添加类路径. `spark.driver.extraClassPath`(在`cluster`模式生效, 在`client`模式中通过`--driver-class-path`指定), `spark.executor.extraClassPath`分别为`Driver`或`Executor`指定额外的类路径.

在`Standalone`集群中, `python`和`R`的应用都不支持`cluster`部署模式. 另外, `spark-shell`和`spark sql shell`都不支持`cluster`部署模式, 因为需要交互输入.

* `--executor-cores`, 指定每个`executor`使用的`cpu`核心数, 默认全部
* `--executor-memory`, 指定每个`executor`使用的内存量, 默认1g
* `--total-executor-cores`, 指定应用的`executor`使用的所有核心数, 限制集群资源的使用量
* 上述3个, 可以间接地控制每个`worker`上启动的`executor`数, 以及总的`executor`数.
* 可以通过`8080`的`master`页面查看效果

看来有点小看`Standalone`了 TODO 存疑:
* `spark.dynamicAllocation.enabled`, 设置是否允许根据工作负载动态调整应用的`executor`数目
* `spark.dynamicAllocation.minExecutors`
* `spark.dynamicAllocation.maxExecutors`
* `spark.dynamicAllocation.initialExecutors`

* `spark.network.timeout`, 默认的网络交互超时
* `spark.core.connection.ack.wait.timeout`
* `spark.storage.blockManagerSlaveTimeoutMs`
* `spark.shuffle.io.connectionTimeout`
* `spark.rpc.askTimeout`, `rpc ask`操作的等待时间
* `spark.rpc.lookupTimeout`, `rpc`远程端点`lookup`操作的等待时间


* `spark.dynamicAllocation.executorIdleTimeout`, 在动态分配中, `executor`在被移除前的`idle`时间. 默认60s
* `spark.dynamicAllocation.cachedExecutorIdleTimeout`, 在动态分配中, 已缓存数据块的`executor`在被移除前的`idle`时间. 默认无限, 即若`executor`缓存了数据块, 则默认不会被移除

* `spark.files.fetchTimeout`, 在获取通过`SparkContext.addFile()`添加的文件时的超时

应用外:
* 使用`kryo`序列化库, `spark.serializer`

应用内:
* 为数据选择合适的分区方式避免跨节点的数据混洗
* 适当重分区来指定适当的并行度
* 完成同样的任务, 使用合适的`API`
* 在长计算链中, 适当缓存`rdd`
