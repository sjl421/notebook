# HBase

## 术语

`Table`: 表由多行构成

`Row`: 由`rowkey`和多个列构成, 以`rowkey`排序存储, 设计`rowkey`以便相关连的数据相近.

`Column`: 由列`family:qualifier`构成

`Column Family`: 有一系列的存储属性, 每`Row`有相同的`Families`, 无论是否在`Family`有数据

`Column Qualifier`: 在表创建后, 列簇即固定, 但`Qualifier`则可变, 且行间可不同

`Cell`: 由`rowkey`, `Family`, `Qualifier`定位, 包含一个值和时间戳(表示值的版本)

`Timestamp`: 每个值都有自己的时间戳, 表示数据被写入时的时间, 也可表示值的版本

## 基础

1, 下载并解压
2, `conf/hbase-env.sh`设定`JAVA_HOME`
3, `conf/hbase-site.xml`

```xml
<!-- 单机模式 -->
<configuration>
  <property>
    <name>hbase.rootdir</name>
    <value>file:///home/testuser/hbase</value>
  </property>
  <property>
    <name>hbase.zookeeper.property.dataDir</name>
    <value>/home/testuser/zookeeper</value>
  </property>
</configuration>
```

`hbase.rootdir`目录会自动创建

4, `bin/start-hbase.sh`启动程序, 在`localhost:16010`访问
5, `bin/hbase shell`启动`shell`

```hbase
> help [cmd]
> create 'test', 'cf'  # 创建表test, 以及列簇cf
> list 'test'  # 列出表信息
> put 'test', 'row1', 'cf:a', 'value1'  # 向表test存键为a值为value1的数据, `row1`是`rowkey`, 唯一
> scan 'test'  # 查看表数据
> get 'test', 'row1'  # 获取单条记录
> disable/enable 'test'  # 删除表或配置表时, 要先disable
> drop 'test'  # 删除表
```

6, 启动`thrift/thrift2`服务: `bin/hbase thrift2 -b host -p port start|stop`

需要详细说的是, 启动服务后, 则别的语言可通过`thrift`服务来操作`hbase`, 但需要从`Hbase.thrift`文件来生成不同语言的包. 此文件在源码的`hbase-thrift\src\main\resources\org\apache\hadoop\hbase\thrift2`目录中.

7, `bin/stop-hbase.sh`关闭程序

## 运行模式

`HMaster`服务器控制`HBase`集群, 可启动备份进程: `bin/local-master-backup.sh start/stop 2 3 5`, 此处的`2,3,5`是端口偏移, 在`16010`的基础上. 可最多启动9个备份.

`HRegionServer`服务器在`HMaster`的指示下管理`StoreFiles`中的数据, 可启动备份进程: `bin/local-regionservers.sh start/stop 2 3 4 5`, 端口在`166200`和`16300`基础上偏移.

`HQuorumPeer`是由`HBase`控制和启动的`ZooKeeper`实例.

* 单机模式, 运行在一台`PC`, 且所有守护进程(`HMaster`, `HRegionServer`, `HQuorumPeer`)运行在相同的`JVM`实例中

* 伪分布模式, 运行在一台`PC`, 但每个守护进程运行在不同的`JVM`实例中

```xml
<!-- 伪分布模式, 需启动hdfs -->
<configuration>
  <property>
    <name>hbase.rootdir</name>
    <value>hdfs://localhost:8020/hbase</value>
  </property>
  <property>
    <name>hbase.cluster.distributed</name>
    <value>true</value>
  </property>
  <property>
    <name>hbase.zookeeper.property.dataDir</name>
    <value>/home/testuser/zookeeper</value>
  </property>
</configuration>
```

* 全分布模式, 运行在集群上, 每个节点运行的服务都可能不同

```xml
<!-- 全分布模式, 需启动hdfs -->
<configuration>
    <property>
        <name>hbase.rootdir</name>
        <value>hdfs://test57:8020/hbase</value>
    </property>
    <property>
        <name>hbase.cluster.distributed</name>
        <value>true</value>
    </property>
    <property>
        <name>hbase.zookeeper.property.dataDir</name>
        <value>/home/ivic/bigdata/zookeeper</value>
    </property>
    <!-- 配置启动zk的节点 -->
    <property>
        <name>hbase.zookeeper.quorum</name>
        <value>test57,test58,test59</value>
    </property>
</configuration>
```

在`conf/regionservers`文件中, 写入要启动`HRegionServer`服务的节点.

在`conf/backup-masters`文件中, 写入要启动备份`HMaster`服务的节点.

要求`HMaster`, 包括备份的`HMaster`, 可以`ssh`到集群中的所有其他节点.

## 进阶

### 可打开的文件数和进程数:

`HBase`要求能够同时打开大量文件, 但许多`Linux`发行版限制单个用户能打开的文件个数, 多为`1024`, 通过`ulimit -n`查看. 推荐设置为`10240`. 计算可能打开的文件数 = (`StoreFiles per ColumnFamily`) * (`regions per RegionServer`), 即每个列簇的存储文件数 * 每个区域服务器的区域数.

单个用户一次可打开的进程数, 通过`ulimit -u`查看, 如果太低会遇到`OutOfMemoryError`错误.

在`/etc/security/limits.conf`文件中设置, 以下为`hadoop`用户设置, `-`表示同时设置软/硬限制, 也可单独指定`soft`或`hard`, 修改之后要确保`/etc/pam.d/common-session`包含`session required  pam_limits.so`:

```sh
hadoop  -       nofile  32768
hadoop  -       nproc   32000
```

`ln -s ${HADOOP_HOME}/etc/hadoop/hdfs-site.xml ${HBASE_HOME}/conf/hdfs-site.xml`, 建立软链接有且于`HBase`感知到`HDFS`客户端的配置变.

### 负载均衡

在`hbase shell`中, `balance_switch true/false`可开关负载均衡.

`hbase`在`zookeeper`中存储了一个`hbase:meta`表, 保存系统中所有`regions`的列表. `HBase Client`通过查询`hbase:meta`表定位请求的`region`, 并联系服务此`region`的`RegionServer`, 发送读写请求(并不经过`Master`). `Client`会缓存这些信息以便后续的请求利用. 若此`region`被`master`的负载均衡或`RegionServer`死掉了, 则`Client`会重新查询并定位`region`的位置.

`HMaster`负责监视集群中所有的`HRegionServer`实例, 是所有元数据改变的接口. 在分布式集群中, `HMaster`通常运行在`NameNode`的结点上.

`HRegionServer`负责服务和管理`regions`. 在分布式集群中, `HRegionServer`运行在`DataNode`的结点上.

个人理解:

* `Region`就是表的某个行范围, 如`10000-15000`行
* 每个`HRegionServer`管理很多个`Region`