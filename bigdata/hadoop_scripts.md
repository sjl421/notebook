# Hadoop的脚本文件解析

## libexec/hadoop-config.sh

`JAVA_HOME`设置`java`的安装路径

### 设置CLASSPATH

默认的`Hadoop`发行包包含了4大组件: `common`, `hdfs`, `yarn`, `mapred`. 这4大组件
通常都在默认的位置, 但无论出于什么原因, 组件的位置都是可以自定义的, 通过定义一系列
的环境变量.

* `HADOOP_COMMON_DIR`, `HADOOP_COMMON_LIB_JARS_DIR`, `HADOOP_COMMON_LIB_NATIVE_DIR`
* `HDFS_DIR`, `HDFS_LIB_JARS_DIR`
* `YARN_DIR`, `YARN_LIB_JARS_DIR`
* `MAPRED_DIR`, `MAPRED_LIB_JARS_DIR`

以上定义的都是组件的局部目录, 组件的`HOME`前缀可通过`HADOOP_XXX_HOME`环境变量来设定.
这几个变量可让用户随意存放组件. 而无论使用自定义的目录, 还是使用默认目录, 但最终的
目的都是将相关的`Jar`文件加入`CLASSPATH`.

而除了系统组件外, 用户也可设定`HADOOP_CLASSPATH`来指定自定义的`Jar`.

### 设置HADOOP_OPTS

`HADOOP_RPEFIX`用来设置`hadoop`的根目录, 主要用来作为别的配置的路径前缀

`HADOOP_CONF_DIR`用来设置`hadoop`的配置目录, 配置目录通常作为`CLASSPATH`的首项

`HADOOP_LOGLEVEL`用来设置`hadoop`的日志级别

`HADOOP_SLAVES`, `HADOOP_SLAVES_NAMES`设置`slave`节点

`HADOOP_HEAPSIZE`用来设置`java`的最大堆大小

`HADOOP_LOG_DIR`用来设置`hadoop`的日志目录, `HADOOP_LOGFILE`则设置日志文件

`HADOOP_POLICYFILE`用来设置策略文件

## etc/hadoop/hadoop-env.sh

`HADOOP_NAMENODE_OPTS`, 设置`namenode`的启动选项

`HADOOP_DATANODE_OPTS`, 设置`datanode`的启动选项

`HADOOP_SECONDARYNAMENODE_OPTS`, 设置`secondarynamenode`的启动选项

`HADOOP_PID_DIR`, 设置`pid`文件的存放目录, 默认`/tmp`, 目录最好只能被用户写

## sbin/start-dfs.sh

调用`sbin/hadoop-daemons.sh`, 启动`namenode`和`datanode`, `secondarynamenode`, 
`journalnode`, `zkfc`

`HADOOP_LIBEXEC_DIR`

## sbin/hadoop-daemons.sh

`HADOOP_LIBEXEC_DIR`, 设置`libexec`目录, 读入`libexec/hadoop-config.sh`文件

调用`sbin/slaves.sh`

## sbin/slaves.sh

`HADOOP_SSH_OPTS`, 当运行远程命令时, 传递给`ssh`的选项
