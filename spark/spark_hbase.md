# Spark HBase

`HDFS`是主从架构的, 一个`NameNode`, 其余的`DataNode`.

`HBase`也是主从架构的, 一个`HMaster`, 其余的`HRegionServer`. 向`HBase`请求数据时, 会先从`Zookeeper`的元表定位数据所在的`region`及`regionserver`, 然后再通过`RegionServer`获取数据. 因此, 推荐每个`DataNode`上运行一个`HRegionServer`.

`Spark Standalone`也是主从架构的, 一个`Master`, 其余的`Worker`. 通过`Spark`读写`HBase`时, 考虑到数据的本地性, 即每个`Executor`只通过本节点的`RegionServer`向`HBase`库中读或写数据, 因此, 推荐每个`DataNode`上一个`Worker`.

`Zookeeper`用于保存`HBase`数据的元表, 在任意节点上执行`HBase`数据获取时, 都直接向`ZK`查询再转到`RegionServer`, 并不经过`HMaster`, 因此推荐集群的每个节点上运行`HQuorumPeer`, 以方便`executor`的`region`查询.

## spark-sql-on-hbase

通过在标准`Spark Catalyst`引擎内嵌入自己的查询优化计划来应用高级的自定义的优化技术, 将`RDD`带给`HBase`, 并在`HBase`协处理器内部执行复杂的任务, 如部分聚合. 这种方式能达到高性能, 但很难维护, 因为本身的复杂性和`spark`的快速演化. 同时, 允许在协处理器内运行任意代码也暴露了安全风险.

项目应该已经死亡了, `github`的代码最新提交是2年前.

## HBase connector

`HBase`官方的主分支上也提供了跟`Spark`的连接器. 在`RDD`层级上提供了丰富的支持, 如`BulkPut`, 但对`DataFrame`的支持则稍弱. 它依赖标准的`HadoopRDD`和`HBase`内建的`TableInputFormat`, 有一些性能限制. 额外的, 在`Driver`中执行的`BulkGet`可能是`a single point of failure`.

`SPOF`, 即当某个零件故障会造成整个系统无法正常工作.

## spark on hbase connector (shc)

实现了标准的`spark`数据源`API`, 并利用`Spark Catalyst`作为查询优化引擎. 同时, 为实现高性能, `RDD`是从头开始构建, 而非使用`TableInputFormat`. 通过这种定制的`RDD`, 可以应用和完全实现所有关键技术, 如分区修剪, 列修剪, 谓词下推和数据局部性.

以相同的方式对待`Scan/Get`, 都在`Executor`上执行. `Driver`负责处理查询, 基于`region`的元数据聚合`scan/get`, 为每个`region`生成`tasks`. `task`被发送给对应`RegionServer`的`Executor`上, 在所有`Executor`上并行执行来达到数据本地性和并发. 如果`region`未保存请求的数据, 则不会被分发任务. 一个任务可能由多个`scans/bulkget`组成, 任务请求的数据只从一个`RegionServer`获取, 而这个`RegionServer`也将是此任务的本地偏好. `Driver`不涉及真正的`job`执行, 只做`task`调度, 这避免了`driver`成为瓶颈.

### 表目录

为了将`HBase`表作为关系表带到`spark`中, 我们在`HBase`和`Spark`表之间定义了映射, 称为`Catalog`. 有两个关键的部分, 一个是`rowkey`的定义, 一个是`Spark`的表列与`HBase`的列簇,列限定符之间的映射.

### Avro原生支持

连接器本身支持`AVRO`格式, 因为将结构化数据作为字节数组保存到`HBase`是一个非常常见的做法. 用户可以直接将`Avro`记录存入`HBase`中. 在内部, `Avro`的`schema`自动转换为`Spark Catalyst`的数据类型. 注意, `HBase`表的`key/value`部分都可以定义为`Avro`格式.

### 谓词下推

连接器只从`RegionServer`接收请求的列来减少网络负载并避免`Spark Catalyst`引擎冗余的处理. 现有的标准`HBase`过滤器被用于执行谓词下推, 无需利用协处理器的功能. 因为`HBase`除了字节序列并不能知道数据类型, `Java`裸类型和字节序列之间顺序的不一致, 在设置`Scan`操作的过滤器前, 我们不得不对过滤条件进行预处理来避免数据损失. 在`RegionServer`中, 不匹配查询条件的记录会被过滤掉.

### 分区剪枝

通过从谓词中提供`rowkey`, 我们将`Scan/BulkGet`拆分为多个不重叠的范围, 只有具有请求的数据的区域服务器才能执行`Scan/BulkGet`. 当前, 分区修剪基于`rowkey`的第一个维度, 例如, 如果`rowkey`是`key1:key2:key3`, 则分区修剪只基于`key1`. 注意, `WHERE`条件需要仔细定义, 否则分区修剪可能无效. 例如, `where rowkey1 > "abc" or column = "xyz"`会造成全扫描, 因为我们不得不覆盖所有的范围, 因为这个`or`关键字.

### 数据本地化

当`Spark`执行器与`HBase`区域服务器位于同一位置时，通过识别区域服务器位置来实现数据本地化, 并尽最大努力与`RegionServer`共同定位任务. 每个`Executor`只对相同服务器上的数据执行`scan/bulkget`.

### scan and bulkget

这两个操作通过指定`where`语句暴露给用户, 如`where column > x and column < y`时执行`scan`, `where column=x`时执行`get`. 操作在`executor`中执行, `driver`只负责构造这些操作. 在内部, 它们被转化为`scan and/or get`, 并返回`iterator[Row]`给`catalyst`引擎用于上层处理.