# CDH

## 安装CM

`CM(Cloudera Manager)`是用来安装和管理别的服务的, 包括`jdk`, 包括`CM Agent`, 包括各种需要的软件, 如`hadoop`, `spark`, `hive`等.

整体上, `CM`分为`Server/Agent`两个部分, 把`Server`安装到主机`A`, 把`Agent`安装到需要被管理的主机, 通常即你的集群的主机. 主机`A`可以在集群中, 也可以不在, 如果不在集群中, 需要能连接到集群的所有主机.

`Agent`负责收集主机的运行信息, 传送到`Server`, 形成主机运行监控. 同时还负责执行`Server`的`rpc`请求, 即用来启动服务, 如`hdfs/yarn/spark`等.

简单的步骤可以理解为, 在主机`A`上安装`Server`, 再通过`Server`自动批量地在所有集群主机上安装`jdk/agent`以及别的软件包. 没有魔法, `Server`通过`root`用户`ssh`到集群主机, 再通过添加`repo/key`, 执行`apt-get`安装需要的软件.

注意: 网速感人.

### 安装Server

添加`repo`: `http://archive.cloudera.com/cm5/ubuntu/`, 找合适的版本, 添加`.list`, 添加`.key`.

```sh
# ubuntu 16.04, http://archive.cloudera.com/cm5/ubuntu/xenial/amd64/cm/    
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

