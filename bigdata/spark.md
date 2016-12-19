# spark

`spark`依赖`hadoop`的`hdfs`和`yarn`两个重要的分布式组件, 一个用于分布式存储, 另
一个用于分布式的资源调度.

`spark`本身的安装要简单很多. 同样, 因为是分布式, 所以运行的每个节点都要有安装包.
只有一个节点是`master`节点, 其余的节点是`worker`. `master`节点启动后, 会启动一个
监听端口, 如`spark://master:7077`(默认), 在启动`worker`时, 要指定这个端口.

通过`sbin/start-master.sh`来启动`master`进程, `sbin/start-slave.sh spark://master:7077`
来启动`worker`进程. 实际上, 并不关心`master`和`worker`是否启动在同一机器上.

同样, 要想在启动`master`时, 自动启动`worker`, 则需要`conf/slaves`文件. 同`hadoop`
一样. 默认情况下, 写入`localhost`, 然后执行`sbin/start-all.sh`即会启动所有.

需要注意的是, `spark`如果要依赖`yarn`和`hdfs`, 则需要依赖`hadoop`的配置, 即需要
知道`hadoop`的配置文件目录, 你可以在`.bashrc`中定义, 或者在`conf/spark-env.sh`中
定义. 后者需要多定义`JAVA_HOME`变量. 虽然同样不明白, 包括`etc/hadoop/hadoop-env.sh`
文件, 明明已经定义过了`JAVA_HOME`, 且明确`export`了, 此两个文件还是需要重新定义.

在`conf/spark-env.sh`除了定义`JAVA_HOME`,还需要定义`HADOOP_CONF_DIR`变量,指向`Hadoop`
的配置文件目录.

要在`yarn`中运行应用, 直接指定`--master yarn`即可. 虽然还涉及一个部署模式`--deploy-mode`
其有两个值`client`和`cluster`. 主要区别是, 驱动`driver`进程是在集群`cluster`外执行,
还是在内执行.

`spark-shell`只有`client`模式, 所以直接`spark-shell --master yarn`即可.

对于运行`standalone`模型下的`spark`, 在`8080`端口打开`master`的`web`界面, 在`8081`端口
可打开`worker`的`web`界面. 如果运行了`spark-shell`, 则在`4040`端口打开`shell`界面.
`master`的`web`界面可显示正在执行的应用和相应的执行进度.  然而如果使用了`yarn`
作为资源调度, 则应用的执行进度, 只有在`8088`的`yarn`界面才能看到.

运行示例程序:
```sh
./bin/spark-submit --class org.apache.spark.examples.SparkPi \
--master yarn --deploy-mode cluster examples/jars/spark-examples*.jar 10
```

当在集群运行时,如果有依赖包,可以通过`--jars a1.jar a2.jar`来引入.
