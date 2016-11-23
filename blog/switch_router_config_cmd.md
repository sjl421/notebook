# 网络实验常用命令

本学期一共进行的实验: 
* 网络实验入门
* 数据链路层实验
* 网络层实验
* OSPF协议实验
* 传输层实验
* 网络管理实验
* IPv6技术实验
* 无线网络实验(没有命令)
* 综合组网实验

之后的`R`表示路由器, `S`表示交换机, `PC`表示主机

## 帮助与补全

注: 在`Windows`上执行命令, 命令不补全, 如果有参数忘记, 可以执行`cmd /?`查看此命令的帮助信息. 这里的`cmd`是你的命令, 如`show /?`会列出可以`show`的所有信息.

注: 在`Linux`下执行命令, 命令会补全, 通常是没有参数需要记忆的, 如果实在忘了, 可以执行`cmd -h`或者`cmd --help`来查看帮助, 但通常信息是英文的. 所以还是直接记忆比较好.

注: 在交换机或路由器上, 命令繁杂且长, 所以补全和帮助信息很多, 切记使用`?`可查看可使用的命令, 使用`cmd ?`查看可使用的子命令或参数.

注: 关于在`Windows`的截图: 用`Print Screen`可以截整个屏幕, 用`Alt + Print Screen`可以截当前窗口. 一种方法是启动`word`再`Ctrl+v`粘贴, 另一种方法是"开始>附件>画图", 打开后再`Ctrl+v`粘贴, 再保存即可.

## Linux下的操作

1. 图形操作, 先把所有网络接口删除再自己配置, 通过`ifconfig`来查看配置的接口`ip`, 通过`route -n`来查看配置的默认网关路由. 通常不用再确认.
2. 移动文件: `mv file1 file2 ~/Desktop/`, 将多个文件移动到窗口, `~`表示当前用户的主目录, `Desktop`是在主目录中的桌面目录. 实在忘了, 记住还有文件管理器, 在里面找.
3. `minicom`, 命令不要忘了

## 通用操作

1. `?`用于显示当前视图可执行的命令, 或当前命令的子命令, 或当前命令的参数选项.
2. 默认情况下, 命令无需敲全, 系统能识别即可
3. `tab`可补全命令
4. `dis`可用来显示各种信息
5. `undo`可用来撤销上一个命令
6. `inter`和`quit`可在视图中切换, 不同视图可执行的命令不同

```sh
# R/S
<H3C> sys  #进入系统视图
[H3C] sysname R1  #重命名设备为R1
[R1] inter e1/0/1  #进入接口视图
[R1-Ethernet1/0/1] shutdown  #关闭接口, 可undo
[R1-Ethernet1/0/1] quit  #退出当前接口视图
[R1] quit  #退出用户视图

# R/S
flow-control  #开启流量控制, 可undo

# S
undo mac-address  #清空mac地址表
undo arp  #清空arp缓存

# R
inter e0/0
qos lr outbound cir 80  #配置接口转发速率
```

保存设备配置信息: (复制输出的信息, 并保存到文本文件即可)

```sh
# R/S
display current-configuration  #显示当前设备的配置信息
```

重启设备:

```sh
# R/S
<R1> reset saved-configuration  #重置配置信息
<R1> reboot  #重启设备
```

主机的操作:

```sh
# PC
arp -a  #显示主机的arp缓存表
arp -d  #删除主机的arp缓存表
tracert ip_dst  #追踪到目标IP的路径, 需要交换机开启
# ip ttl-expires enable  #允许主机ttl过期, 一定要记住, 使用tracert必须在交换机开这个
# ip unreachables enable  #允许主机不可达
```

为接口添加IP:

```sh
# Router
inter e0/0  #进入接口视图
ip add 192.168.1.2 24  #添加ip, 24为位数

# Switch
vlan 2  #不存在则创建
port e1/0/1 to e1/0/4  #范围, 添加接口到vlan
port e1/0/1 e1/0/3 e1/0/5  #列表
inter vlan 2  #进入vlan
ip add 192.168.1.2 24
inter loopback 1  #进入环回接口
ip add 192.168.1.3 24  #环回接口可直接配置ip, 无需vlan, 但掩码会转为32位
```

## 端口聚合

有二层聚合`bridge`和三层聚合`route`, 可使用的负载均衡模式也不同. 二层能使用`mac`地址, 三层能使用`ip`地址. 聚合的前提是, 接口同速率工作在全双工模式.

```sh
# S
link-aggregation load-sharing mode destination-mac source-mac  
#目的mac和源mac模式聚合
interface bridge-aggregation 1  #创建二层聚合端口并进入视图
link-aggregation mode dynamic  #聚合组工作在动态聚合模式
port access vlan 70  #将当前access端口加入到指定的vlan, 在启用vlan的S上需要执行

inter e1/0/x  #在需要聚合的每个接口都执行
duplex full  #确保工作在全双工模式
speed 100  #确保接口速率相同
port link-mode bridge  #接口工作在二层
port link-aggregation group 1  #加入聚合组1
```

## VLAN

通常情况下, 在主机(或路由器)与交换机连接的接口, 使用`access`链路类型, 在交换机与交换机连接的接口, 使用`trunk`链路类型. `VLAN`是交换机特有的概念. 带`vlan`的标签只会转发到相同`id`的接口上. `trunk`接口可允许通过多种`vlan`的数据帧.

```sh
# S
vlan 2  #创建vlan, 可undo
port e1/0/1 to e1/0/4  #将接口加入vlan, 可undo
port link-type trunk  #设置接口的链路类型, 可undo
port trunk pvid vlan 2  #设置缺省vlan id, 缺省vlan不加id标签, 可undo
port trunk permit vlan 2 to 3  #设置接口可通过的vlan id数据帧
port hybrid vlan 3 untagged  #接口发送的vlan3的数据包不加vlan id的标签
```

## PPP

`ppp`是数据链路层协议, 只能在路由器的`Serial`串中上启用.

```sh
# R
inter s1/0
link-protocol ppp  #重启接口生效
```

开启`PAP`验证: 两步, R2发起验证, R1回复通过与否

```sh
# R1服务器, 接受验证; R2客户端, 请求验证
[R1] 
local-user RTB  #在本地数据库添加用户
service-type ppp  #服务类型为ppp
password simple  aaa  #配置用户密码, simple表示明文密码
inter s1/0
ppp authentication-mode pap  #授权pap验证
[R2] 
inter s1/0
ppp pap local-user RTB password simple aaa  #以用户名和密码发起验证
# 重启生效
```

开启`CHAP`验证: 三步, R1发起验证, R1传递主机名, R2传递主机名, R1回复通过与否

```sh
# R1-S, R2-C, S/C分别表示服务器和客户端
[R1]
local-user RTB  #在本地数据库添加用户RTB
service-type ppp
password simple aaa  #此用户对应的密码为aaa
inter s1/0
ppp authentication-mode chap
ppp chap user RTA  #本地名称为RTA
[R2]
local-user RTA  #在本地数据库添加用户RTA
service-type ppp
password simple aaa  #此用户对应密码为aaa
inter s1/0
ppp chap user RTB  ##本地名称为RTB
# 重启生效
```

## OSPF

`OSPF`是动态路由协议, 交换机和路由器通常都支持. 通常有5类`LSA`:
* `Router`, 所有路由器都会产生, 整个区域
* `Network`, 由`DR`产生, 在广播类型的网络, 整个区域
* `Network Summary`, 由`ABR`产生, 除该`LSA`生成区域以外的区域
* `ASBR Summary`, 由`ABR`产生, 描述到本区域`ASBR`的路由, 范围同上
* `AS External`, 由`ASBR`产生, 描述到`AS`外部路由的信息, 可整个`AS`

有5种报文类型:
* `hello`
* `DD`, 空`DD`用来确定主/从关系, 之后传递`LSA`数据库的摘要信息
* `LSR`, 请求本地缺少的`LSA`
* `LSU`, 发送所请求的`LSA`
* `LSAck`, 确认收到传送的`LSU`

开启ospf:

```sh
# R/S
router id 1.1.1.1  #设置设备的router id
ospf [进程号]  #默认1, 可undo关闭进程
area 0  #区域号0, 可undo删除区域
network 192.168.1.0 0.0.0.255  #注意是反掩码, 可undo取消网段
dis ospf ?  #查看ospf相关信息, 其中brief可查看DR/BDR
dis ospf lsdb ?  #查看不同类型的链路数据库
```

配置链路花费:
```sh
# R
inter e0/0
ospf cost 100
# S
inter vlan 3
ospf cost 100
```

有关应用于`ipv6`的`ospfv3`协议, 替换相关关键字即可.

引入路由:

```sh
# R/S
ospf
import-route direct  #引入直连路由
ip route-static net_ip net_mask next_hop  #添加静态路由
import-route static  #引入静态路由
ip route-static 0.0.0.0 0.0.0.0 next_hop  #添加默认路由
default-route-advertise cost 100  #广播此默认路由
```

需要说明的一点是, 可以配置多个默认路由, 但默认情况下只有一个起作用, 具体哪个起
作用, 可在配置时添加`preference xx`, 表示优先级, 数字越小越好. 默认静态路由为60;
直连路由为0.

## NAT及ACL

启用nat和acl:

```sh
# R
nat address-group 1 ip1 ip2  #配置地址池为ip1-ip2
# nat address-group 1
# address ip1 ip2  #[new]
acl number 2001  #创建控制列表2001
# acl basic 2001  #[New]
rule 0 permit source 10.0.0.0 0.255.255.255  #[反掩码], 允许规则
rule 1 deny
inter e0/1  #网络出去的接口
nat outbound 2001 address-group 1  #将控制列表绑定在地址池的nat上
```

只需要绑定一次, 之后再修改2001的控制列表, 会自动生效.

## SNMP

`SNMP`即简单网络管理协议, 构建在`UDP`报文上, 采用`C/S`模式, 需要在所有路由器和交换机上启用`snmp-agent`. 有五种类型的数据帧:
* `get-request`, 向代理进程(即`snmp-agent`)请求一个或多个参数值
* `get-next-request`, 向代理进程请求一个或多个参数值的下一个参数值
* `set-request`, 设置代理进程的一个或多个参数值
* `get-response`, 代理进程返回的一个或多个参数值
* `trap`, 代理进程主动向管理进程发出, 通知某些事情的发生.

确保设备在能互相`ping`能的情况下:

```sh
# R/S
snmp-agent  #启用客户端进程
snmp sys ver v1  #启用SNMPv1
snmp com write private  #配置写团体名为private
snmp com read public  #配置读团体名为public
snmp trap enable  #开启trap报文
snmp target-host trap address udp-domain 10.7.2.2 params securityname public #trap报文的目标主机
```

## VRRP

`VRRP`虚拟路由器冗余协议, 一种局域网的设备备份机制. 可将一组多台路由器组织成一个虚拟路由器, 优先使用一个作为`master`, 其余的为`backup`. 在设备故障时可自动切换.

```sh
inter e0/1
ip add 192.168.1.3 24
vrrp vrid 11 virtual-ip 192.168.1.2  #添加备份组11的虚拟IP地址
vrrp vrid 11 priority 80  #设置此路由器在备份组11的优先级, 优先级大则为master
vrrp vrid 11 timer-advertise 4  #master的广播时间间隔, 表示自己工作正常
```

## IPv6

`ipv6`数据报由基本报头(固定40字节)和扩展报头组成. 主要的报文由路由器请求报文`RS`, 路由器通告报文`RA`, 邻居请求报文`NS`, 邻居通告报文`NA`, 重定向报文`Redirect`. 其中, `RS/RA`用于路由器发现和前缀发现, `NS/NA`用于地址解析.

配置IPv6:

```sh
# PC, winxp
ipv6 install
ipv6 if  #列出ipv6的接口编号
ipv6 adu 5/2001::2  #为5号接口配置ipv6地址
# ... life 0  #删除配置的ipv6地址
ipv6 rtu ::/0 5/2001::1  #为5号接口配置默认网关
# ... life 0  #删除配置的默认网关

# R
ipv6  #使能ipv6
inter e0/0
ipv6 add 2001::1/64
undo ipv6 nd ra halt  #取消对RA消息的发布
```

大部分别的相关命令, 相当于`ip`的`ipv6`版本, 替换命令即可, 如显示路由表, 如配置静态路由等.

查看Ipv6信息:

```sh
# PC
netsh
interface
ipv6
show /?  #显示所以可show的信息
show join  #显示主机加入的组播组信息
show address  #显示主机的IPv6地址
show routes  #显示路由表信息
show neighbors interface=5 #显示5接口的邻居缓存信息
show destinationcache  #显示目标缓存项目
```

