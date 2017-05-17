# CDH

## 安装CM

`CM(Cloudera Manager)`是用来安装和管理别的服务的, 包括`jdk`, 包括`CM Agent`, 包括各种需要的软件, 如`hadoop`, `spark`, `hive`等.

整体上, `CM`分为`Server/Agent/Service`三个部分.

`Server`安装到主机`A`, 把`Agent`安装到需要被管理的主机, 通常即你的集群的主机. 主机`A`可以在集群中, 也可以不在, 如果不在集群中, 需要能连接到集群的所有主机.

`Agent`负责收集主机的运行信息, 传送到`Server`, 形成主机运行监控. 同时还负责执行`Server`的`rpc`请求, 即用来启动服务, 如`hdfs/yarn/spark`等.

`Service`即通常使用的项目, 如`hdfs/yarn/hadoop`等.

简单的步骤可以理解为, 在主机`A`上安装`Server`, 再通过`Server`自动批量地在所有集群主机上安装`jdk/agent`以及别的软件包. 没有魔法, `Server`通过`root`用户`ssh`到集群主机, 再通过添加`repo/key`, 执行`apt-get`安装需要的软件.

注意: 网速感人.

### 安装Server

添加`repo`: `http://archive.cloudera.com/cm5/ubuntu/`, 找合适的版本, 添加`.list`, 添加`.key`.

```sh
# cloudera manage    
deb [arch=amd64] http://archive.cloudera.com/cm5/ubuntu/xenial/amd64/cm xenial-cm5 contrib
deb-src http://archive.cloudera.com/cm5/ubuntu/xenial/amd64/cm xenial-cm5 contrib
```

`sudo apt-get install cloudera-manager-daemons cloudera-manager-server`

在`postgresql`中为`CM`创建必要的数据库和用户密码, 假设都为`scm`.

在启动服务前, 配置数据库的相关属性, `/usr/share/cmf/schema/scm_prepare_database.sh postgresql dbname username pwd`(需要`sudo`), 会在`/etc/cloudera-scm-server/`生成`db.properties`文件, 内容非常简单, 就是指定`CM`将要使用的数据库属性.

在启动服务前, 对`ubuntu`用户, 可能需要在`/etc/hosts`文件中, 去掉`127.0.1.1`的条目, 可以添加`192.168.x.x hostname`条目, `ip`为本机`ip`, `hostname`为本机的`hostname`.

启动服务: `sudo service cloudera-scm-server start`, 日志文件: `/var/log/cloudera-scm-server/cloudera-scm-server.log`

打开`http://host:7180`, 默认`admin/admin`.

解释"单用户模式", 

### 安装Agent

在打开`web`后, 一系列操作, 包括添加集群主机的`ip`等, 会自动检测主机有没有运行`ssh`, 确定后会要求能`root`用户`ssh`到所有主机.

默认的`ssh`配置是禁止`PermitRootLogin prohibit-password`, 即禁止以密码形式进行`root`登录的, 可更改为`PermitRootLogin yes`, 但并不推荐, 建议以密钥形式, 即将公钥添加到集群主机的`.ssh/authorized_keys`中.

注意: 网速感人.

若一切成功后, `Server`会通过`ssh`登录到各主机, 执行一系列的`apt-get`安装, 包括`jdk/agnet`等.

```sh
sudo apt-get install cloudera-manager-agent cloudera-manager-daemons
```

配置` /etc/cloudera-scm-agent/config.ini`中的`server_host`, `server_port`两项.

`sudo service cloudera-scm-agent start`

## 手动安装

`cloudera`的`repo`从国内访问实在太慢, 建议在本地自己搭建一个`repo`.

* 从`http://archive.cloudera.com/cm5/repo-as-tarball/`下载对应系统对应版本的`repo`压缩包
* 安装`apache/nginx`以提供`http`服务, 将压缩包解压到`/var/www/html/`目录下
* 修改解压的目录权限为`ugo+rX`

在登录`Server`后, 选择使用自定义`repo`, 填写对应`list/key`即可.

手动安装`jdk`, 要求将`jdk`安装到`/usr/java/`目录中, 同时在(`Server`)`/etc/default/cloudera-scm-server`文件中设置`JAVA_HOME`环境变量.

* cloudera-manager-agent,server
* cloudera-manager-daemons
* oracle-j2sdk1.x


* /etc/cloudera-scm-agent
* /usr/lib/cmf/
* /var/log/cloudera-scm-agent
* /var/lib/cloudera-scm-agent,server
* /opt/cloudera


`archive.key`存储的是私钥, `cloudera.list`存储的是`repo`的源, `conf/distributions`存储的是源的`codenaem/components`.

如果自己搭建源的话, 通常为`http://host:port/path codename components`的形式.

目前未成功, 先如此, 再说-_-

到目前为止, 解决的最大问题是, 在`server/agent`的所有主机上, 都要在`hosts`中写明主机和`ip`的对应.