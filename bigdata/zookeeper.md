# ZooKeeper

为分布式应用提供分布式协调服务, 它暴露一个简单的元语集, 分布式应用可借此实现更高级别的服务, 像同步, 配置维护, 组和命名.

* 层次命名空间, 类比文件系统

![zknamespace](../images/zk/zknamespace.jpg)

* 数据保存在内存
* 高性能, 高可用, 严格顺序访问
* 读写比为`10:1`时, 性能最佳
* 节点`znode`有关联数据和子节点
* 分布式的部署结构:

![zkservice](../images/zk/zkservice.jpg)

构成`zk`服务的服务器, 有`leader`和其余的`follower`.

构成`zk`服务的服务器必须知道彼此, 他们维护一个在内存中的状态镜像, 以及在持久设备的事务日志和快照. 只要主服务器可用, 则`zk`服务可用.

`zk`为每次更新打上数字戳, 其反映`zk`事务的顺序. `znode`维护一个统计结构, 包含数据改变/`ACL`改变/时间戳的版本号, 来允许缓存验证和协调更新. 每次`znode`数据改变, 版本号增加.

`znode`的数据读写有原子性, 即读即读所有数据, 写即写所有数据, 不存在部分更新. 且每个`znode`有`ACL`来限制谁可以执行操作.

`zk`有临时节点的概念, 若创建节点的会话关闭, 则节点被删除. 若要实现`tbd`时, 临时节点有用.

`zk`支持监视的概念. 客户端可以在`znode`上设置监视器, 当`znode`改变时, 监视器被触发和移除. 此时, 客户端收到一个数据包表示监视器被触发. 如果`client`和`zk server`的连接中断了, 则`client`会收到本地的提醒.

* 顺序一致性 - 来自`client`的所有更新以其发送的顺序被依次应用
* 原子性 - 数据或者更新成功或者失败. 不存在部分结果
* 单系统镜像 - 无论`client`连接的哪台`server`, 其看到的服务是相同的
* 可靠性 - 一旦应用一个更新, 其将持续存在, 直到下次更新
* `timeliness` - 系统的`client`视图保证在确定的时间界内更新

## 简单的API

* `create`, 在树的指定位置创建结点
* `delete`, 删除节点
* `exists`, 测试指定位置是否存在结点
* `get data`, 从节点获取数据
* `set data`, 写数据到节点
* `get children`, 获取节点的子节点列表
* `sync`, 等待数据扩散

## 实现

![zkcomponents](../images/zk/zkcomponents.jpg)

副本数据库包含完整的数据树, 在内存中. 更新被记录到磁盘(为恢复考虑), 在被应用到内存数据库前, 写会被序列化到磁盘.

来自`client`的写请求会被转发到`leader`, 再由`leader`转发给其余`follower`. 一旦`leader`失败, 由消息层选举新`leader`并通知所有`follower`.

## 运行

配置:

```sh
tickTime=2000
dataDir=/var/lib/zookeeper  # 保证目录可写
clientPort=2181
```

运行: `bin/zkServer.sh start|stop`

连接: `bin/zkCli.sh -server 127.0.0.1:2181`

`zkshell`: `help`打印帮助信息

```sh
create [-s] [-e] path data acl  # 在指定路径创建节点, 并附上关联数据和acl
ls path [watch]  # 列出路径
ls2 path [watch]  # 列出路径, 及其统计信息
stat path [watch]  # 路径的统计信息
get path [watch]  # 路径的关联数据和统计信息
set path data [version]  # 更新路径的数据, 会同时更新统计信息中的dataVersion
delete path [version]
rmr path
sync path

listquota path
setquota -n|-b val path
delquota [-n|-b] path

getAcl path
setAcl path acl

history 
redo cmdno
printwatches on|off
addauth scheme auth
quit  # 退出
close 
connect host:port
```

统计信息:

```sh
cZxid = 0x0  # 创建此节点的事务id
ctime = Thu Jan 01 08:00:00 CST 1970  # 创建时间
mZxid = 0x0  # 最近修改此节点的事务id
mtime = Thu Jan 01 08:00:00 CST 1970  # 修改时间
pZxid = 0x0  # 最近修改其子节点的事务id
cversion = -1  # 修改其子节点的次数
dataVersion = 0  # 数据版本
aclVersion = 0  # acl版本
ephemeralOwner = 0x0  # 临时节点所属的会话
dataLength = 0   # 数据字节长度
numChildren = 1  # 子节点数, /默认有zookeeper的子节点
```