# hadoop

`hadoop`生态系统最有用的两个组件是: `HDFS`分布式文件系统用于存储数据, `YARN`分布
式资源管理用于进程调度.

所谓分布式, 自然要在所有节点上运行`hadoop`的相关进程, 也自然要在所有节点上安装
`hadoop`的发行包.

无论是`HDFS`还是`YARN`都采用的是主从机制. `HDFS`的主机上运行的是`namenode`, 主要
保存数据的元信息, 而从机上运行的是`datanode`, 保存的是真正的数据. `YARN`的主机上
运行的是`resourcemanager`, 负责资源的分配, 而从机上运行的是`nodemanager`, 负责
真正运行进程执行任务.

## 必要配置

最核心的是`slaves`文件, 写着从机的主机名或ip地址, 一行一个. `sbin/start-dfs.sh`和
`sbin/start-yarn.sh`都需要根据此来在从机上启动相应的`datanode`和`nodemanager`. 当然, 
前提是主机能无需密码`SSH`到从机上, 所以配置好密钥. 此外, 也就是说, `etc/hadoop/slaves`
文件, 只对主机有意义.

注: 关于`SSH`, 唯一的需要是主机用于启动从机的相关进程, 也就是说能无密码直接登录从机即可.
但几乎所有的安装教程, 都强调所有节点之间要能够互相登录. 至少目前为止, 经实验, 只实现
主机到从机的即可. 从机到主机, 从机之间的无所谓.

主机通过`etc/hadoop/slaves`文件知晓从机, 但从机并不知道主机. 因此, `etc/hadoop/core-site.xml`
文件中, `dfs.defaultFS`选项非常重要, 对主机而言, 知道在哪个节点启动`namenode`, 对从机而言, 
知道`namenode`的节点以便通信. 另外, 还顺便指定了文件系统, 如`hdfs://master:9000`, 文件系统
是`hdfs`, `namenode`是`master`(当然, 需要在`/etc/hosts`文件中添加解析, 或有着统一的`DNS`服务器),
`namenode`的监听`datanode`通信的端口是`9000`, 端口不是必需的, 默认为`8020`.

除了`dfs.defaultFS`外, 另一个必要的配置是`etc/hadoop/mapred-site.xml`文件, `mapreduce.framework.name`
选项, 值为`yarn`. 这点没什么异议, 基本都已经在使用`YARN`作为资源调度了.

同`dfs.defaultFS`相似, `etc/hadoop/yarn-site.xml`文件中的`yarn.resourcemanager.hostname`, 
用于从机的`nodemanage`知晓`resourcemanager`的运行节点以通信. `sbin/start-yarn.sh`默认执行
脚本的节点即为`resourcemanager`的节点.

就我个人角度来看, `dfs.defaultFS`, `yarn.resourcemanager.hostname`, `mapreduce.framework.name`
和从机列表. 这些即是搭建集群的必要配置. 涉及到的配置文件有`core-site.xml`, `yarn-site.xml`, 
`mapred-site.xml`, `slaves`.

注: 虽然, 主机与从机需要的配置项并不相同, 但通常情况下, 启动脚本会自动读取它需要的值. 即一份配置
文件可以在主机从机通用.

这里要谈两个坑. 
* 第一坑是, 一定要设置`etc/hadoop/hadoop-env.sh`文件中的`JAVA_HOME`环境变量, 无论你是否已经在`.bashrc`文件中`export`了.
* 每二坑是, 一定要去掉`/etc/hosts`文件的`127.0.1.1`行, 其会解析到主机名. 此`ip`只在`Debian`系的发行版有.
* 第三坑是, 如果设置了`hdfs`的`data`目录, 每次执行`hdfs namenode -format`格式化时, 都要删除数据节点的数据.

关于第二坑, 直接写`local_ip_address hostname`即可, 而不是将`127.0.1.1`改为`127.0.0.1`.

启动`HDFS`后, 即在`web`浏览器中, 打开`localhost:50070`, 显示命名节点和数据节点的相关情况, 其中`Datanodes`栏, 
可显示数据节点是否正确连接. 

启动`YARN`后, 即在浏览器中打开`localhost:8088`, 显示当前集群的运行情况, 执行了多个应用等等.

另外, 你总可以用`jps`命令来查看, 当前系统运行着的`JAVA`虚拟机, 能显示是否正在运行`namenode/datanode/secondarynamenode`和
`resourcemanager/nodemanager`等. 此可以查看相应的守护进程是否已经被启动.

如果你的`java`是直接下载二进制安装包再设置环境变量并加入`PATH`来做的, 那么`hadoop`
的安装包中, 本地动态链接库`lib/native/libhadoop.so`文件会缺少`libjvm.so`库位置.
此可以通过执行`ldd libhadoop.so`来查看. 但事实上, 通过`locate libjvm.so`来查看
库在`java`的安装包里. 可能需要执行`ln -s $JAVA_HOME/jre/lib/amd64/server/libjvm.so
/lib/x86_64-linux-gnu/libjvm.so`来修复.

`hdfs getconf`
* `-namenodes`获得`name`节点的配置
* `-secondarynamenodes`获取辅助`name`节点的配置
* `-confKey`通用的配置, `xx.xx.xx`

## 非必要配置

这样的配置实在是太多了, 以至于完全不知道从何说起. 反正, 就先列一些我自己能理解的配置.

`hdfs-site.xml`文件, 主要用于配置`HDFS`:
```xml
<property>
  <name>dfs.replication</name>
  <value>3</value>
</property>
```
此用于配置`HDFS`的块复制副本数, 用于数据安全存储的考虑, 默认为3.

```xml
<property>
  <name>dfs.namenode.name.dir</name>
  <value>local_path_dir</value>
</property>
```
此用于配置`namenode`的文件系统元数据, 默认为`/tmp/hadoop-$USER`. 不过`/tmp`目录每次启动系统
时都会被删除, 每次都要重新`format`一次.

```xml
<property>
  <name>dfs.datanode.data.dir</name>
  <value>local_path_dir</value>
</property>
```
此用于配置`datanode`的数据存储目录.

```xml
<property>
  <name>dfs.hosts/dfs.host.exclude</name>
  <value>file_name</value>
</property>
```
此用于配置从机的白名单/黑名单. 默认`slaves`文件中所有的从机都可用.

```xml
<property>
  <name>dfs.blocksize</name>
  <value>number_in_Byte</value>
</property>
```
此用于配置块大小

```xml
<property>
  <name>yarn.nodemanager.aux-services</name>
  <value>mapreduce_shuffle</value>
</property>
```
此用于配置`mapreduce`应用程序需要设置的`shuffle`服务.

```xml
<property>
  <name>yarn.log-aggregation-enable</name>
  <value>true</value>
</property>
```

是否开启日志聚合, 即将日志复制到`hdfs`, 并从本地删除

## 额外的技巧 

你总可以通过`hdfs getconf -confKey a.b.c`来获取配置的值
