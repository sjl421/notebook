# Apache在大数据方面的各个项目

## HDFS

分布式存储的文件系统

## MapReduce

大数据的批处理引擎

## YARN

资源调度

## MESOS

更细粒度的资源调度框架, 也有理解为分布式操作系统的, 但更多的是进行资源调度.

## HBase

`Google`的`BigTable`开源实现, `key/value`存储, 建立在`HDFS`上的分布式数据库.

## Accumulo

同上, 不同的是更注重安全, 提供`cell level security`.

## HIVE

`SQL`引擎, 即将`SQL`语句转化为`map/reduce`操作, 以在大数据上使用类`SQL`的方式进行数据处理. 分为两部分, `metastore`存储表的无信息, 存储在关系数据库中, 真正的数据存储在`HDFS`上.

## Spark

另一套充分利用内存的计算框架, 主要有`SQL/MLLib/Grxphx/Streaming`4大子模块, 每个模块解决一类具体问题, 但各模块间可以无缝集成.

其中`SQL`部分用于替换`HIVE`. `Streaming`部分用于进行流式数据处理.

## ALLUXIO

分布式内存数据库

## Kafka

分布式消息处理系统

## ZooKeeper

分布式服务框架, 为别的分布式计算框架提供分布式配置服务, 同步服务和命名注册.

## Storm

处理流式数据的分布式计算框架, 实时计算