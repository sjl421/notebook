# 搭建Linux工作环境之VirtualBox

>自带作用域的Ruby患者

## 序

最近需要搭建多虚拟机模拟集群来运行Spark平台, 我又重新翻开了之前的VritualBox使用笔记, 重新整理后发了上来.

搭建虚拟机主要使用`VirtualBox`和`VMware`两个软件. 通常情况, 如果建立服务器环境的虚拟机, 我使用`VirtualBox`, 否则使用`VMware`. 

考虑到任务主要是运行`Spark`平台, 并不需要桌面环境, 要尽可能节省资源. 又考虑到`VirtualBox`的开源, 免费, 跨平台以及提供命令行管理, 我选择使用`VirtualBox`来搭建需要的虚拟机集群.但就我的使用体验来看, `VMware`建立的虚拟机往往拥有更好的性能和更强的功能, 

这里除了建立第一个虚拟机的过程需要使用图形化, 其余过程全部使用命令行. 主要考虑到真实的工作可能并没有桌面环境.

这里也不是搭建大数据平台的教程, 只是在过程中如何使用`VirtualBox`命令行来管理虚拟机.

## 任务要求

需求: 5个虚拟机, 其中2个`master`, 3个`worker`.

因为要建立5个虚拟机, 不可能将安装镜像重复性的安装5次, 可以考虑将一个安装完全后, 通过克隆来复制成5个. 但这样的做法并不适合将建立的虚拟机传给别人. 这里使用虚拟机的导出和导入.

新虚拟机的建立不细说, 但有几点注意:
* 用户名和密码请设置为`hadoop`
* 将相关软件存储在`/opt`目录
* 在`/etc/bash.bashrc`文件设置相关环境变量和`PATH`路径

## 安装VirtualBox

好吧, 这应该不算作一个问题, 在`Windows`平台上. 但如果宿主机是`Linux`, 特别是`Ubuntu`时, `Oracle`提供有`PPA`方便安装.

* 添加`deb http://download.virtualbox.org/virtualbox/debian trusty contrib`到软件源文件, 或者在`/etc/apt/sources.list.d`目录下新建文件写入.
* 获取官方的软件源校验密钥`wget -q https://www.virtualbox.org/download/oracle_vbox.asc -O- | sudo apt-key add -`
* 更新本地缓存并安装`sudo apt-get update && sudo apt-get install virtualbox-5.0`

## 网络选择和操作

默认情况下, `VirtualBox`建立的虚拟机网络默认`NAT`. 虚拟机之间无法沟通, 虚拟机能连接主机和外部网络, 但主机不能访问虚拟机(可以通过端口转发来访问虚拟机). 对于大数据平台, 虚拟机之前的数据传递是必要的, 因此不能使用`NAT`.

别的网络选项, 如`Bridged networking`, 桥接网络, 会将虚拟机添加到主机所在的局域网. 不好的是, 外部网络也能连接到虚拟机, 对虚拟机的安全不利. 而`Host-only`, 主机网络, 则是虚拟机只能与主机彼此连接, 但不能访问外部网络. 虚拟机更新和安装软件, 总有连网的需求.

`VirtualBox`提供了实验性质的`NAT Service`, 虚拟机之间可以彼此连接, 虚拟机可以访问外部网络, 但主机要通过端口转发来访问虚拟机.

添加`NAT`服务网络:

```sh
vboxmanage natnetwork add --netname big_data --network "192.168.2.0/24" --enable
```

添加选项`--dhcp on`可以开启`DHCP`动态IP分配. 但虚拟机之间由于要配置主机名到IP的映射, `DHCP`可能会改变虚拟机的IP(大部分情况下不会). 因此, 这里并不开启`DHCP`.

对`NAT`服务网络的操作:

```sh
vboxmanage natnetwork [start|stop|remove] --netname big_data
```

现在假设建立的虚拟机名为`spark`, 更改其网络设置为`natnetwork`.

```sh
vboxmanage modifyvm spark --nic1 natnetwork --nat-network1 big_data  #关机执行
vboxmanage modifyvm spark --nic1 nat
vboxmanage controlvm spark nic1 natnetwork --nat-network big_data  #开机执行
vboxmanage controlvm spark nic1 nat
```

这里是最无语的地方. 也请记住, `modifyvm`子命令只能在关机状态下执行, 而`controlvm`则只能在开机状态下执行. 像更换网络和设置端口转发, 在`GUI`界面是同样的操作, 在命令行下就不同了. 蛋疼!!!

## 导入和导出

导出这个基本配置完成的虚拟机:

```sh
vboxmanage export spark -o spark.ova --ovf20 --options manifest, nomacs --vsys 0 --product "enalix's spark vm" --version "1.0"
```

`ovf`是一种虚拟机共享协议, 用于将虚拟机导出生成多个文件, `--ovf20`最新的标准. `ova`文件格式实际上是对`ovf`的多个文件以`tar`打包成单个文件以方便传输. 一个`ova`文件可以包含多个虚拟机, 因此, 如果要添加虚拟机的产品名和版本等别的额外信息, 需要指定针对哪个虚拟机, 由`--vsys n`来指定. 从0索引. `manifest`用于导出文件在导入时检验. `nomacs`是除去虚拟机的`mac`地址信息. 在正常的导出和克隆时, `mac`是不变的, 在同一网络中, 相同`mac`的虚拟机是无法通信的. 因此这里选择除去`mac`信息, 在导入时会自动生成新的不同的地址.

生成`ova`格式的文件就可以进行传输了. 其实创建虚拟机也有命令行`vboxmanage createvm`, 再加上一些选择来控制. 但虚拟机的安装过程, 需要选择语言, 地区, 键盘, 用户等等, 这些可以使用`Ubuntu Preseeding`文件来预先写上答案来自动化安装. 这是另一篇文章的内容了, 如何自动化安装系统. 这里可以在一台桌面环境的系统安装配置完成后, 导出生成`ova`文件, 再传输到别的电脑上导入. 

导入虚拟机:

```sh
vboxmanage import spark.ova -n  #列出可更改选项
vboxmanage import spark.ova --vsys 0 --vmname spark_master1 --memory 1024 --unit xx --disk path
```

导入时还有更多的控制选项, 如禁用声卡, 磁盘控制器, CPU数等. 但更主要的是, 虚拟机名, 和磁盘路径. 这里的`--vsys 0`同上相同. 虚拟机由不同的单元组成, `--unit xx`指定用于改变哪个单元. 你可能需要导入多次来建立多个虚拟机名.

## 控制虚拟机

启动虚拟机:

```sh
vboxmanage startvm vm1 vm2 ... --type headless
```

可以同时启动多个虚拟机. `VirtualBox`提供有多种启动方式, 由于是非桌面环境, 因此可以使用`headless`来无窗口地启动. 换句话说, 虚拟机确实启动了, 你可以从主机`ssh`登录, 但并不会有某个窗口在运行虚拟机. 

`VirtualBox`提供了虚拟机组管理机制, 你可以将相关的虚拟机添加到一个组中.

```sh
vboxmanage modify spark_master1 --group "/bigdata"  #入组
vboxmanage modify spark_master1 --group ""  #退组
```

但是无语的是, 虽然`GUI`界面的`VirtualBox`提供有组启动, 但命令行并没有. 你只能在一条启动命令中列出要启动的多个虚拟机名. 另一个无语的是, 无法一条命令将多个虚拟机添加到同一组. 真不明白这种简单方便的选项怎么没提供.

除了启动外, 你还有更多的控制:

```sh
VBoxManage controlvm vmname [poweroff|reset|pause|resume|acpipowerbutton|acpisleepbutton|savestate]
```

这里面有一些区别. `poweroff`实际上相当于拔电源, `reset`相当于硬件的重置. 而`acpipowerbutton`才是系统的关机, `acpisleepbutton`则是系统的休眠. 注意, 休眠应该是`Windows`独享的功能. `pause`和`resume`则是暂停和恢复虚拟机. `savestate`会保存当前状态并关机.

## 端口转发

由于是无窗口的启动虚拟机, 因此在主机通过`ssh`登录管理系统则是非常必要的. 这里通过端口转发来完成.

```sh
vboxmanage natnetwork modify --netname bigdata --port-forward-4 "master1:tcp:[]:2010:[192.168.2.10]:22"
vboxmanage natnetwork modify --netname bigdata --port-forward-4 delete master1
```

此处建立了一条端口转发的规则`master1`, 协议`tcp`, 从主机`2010`端口到虚拟机`192.168.2.10`的`22`端口. 这里的`--port-forward-4`是指`IPv4`版本的.

有意思的是, 无论是添加`nat`服务网络, 而是添加服务网络的端口转发, 都只能在命令行中进行. `GUI`是不提供此功能的. 而事实上, `GUI`的管理工具只提供些简化的功能, 如果要完全掌握`VirtualBox`, 仍然需要探索命令行.

对于一般的`NAT`网络来说, 也可以设置端口转发:

```sh
vboxmanage modifyvm spark_master1 --natpf1 "master1,tcp,,2222,,22"  #关机执行
vboxmanage modifyvm spark_master1 --natpf1 delete master1
vboxmanage controlvm spark_master1 nic1 nat --natpf1 "master1,tcp,,2222,,22"
vboxmanage controlvm spark_master1 nic1 nat --natpf1 delete master1
```

设置中, 连续的两个`,`表示此处为空. 在`NAT`网络下, 虚拟机只和主机相连, 因此主机的IP和虚拟机的IP并不重要, 可省略. `nic1`和`natpf1`中是数字1, 表示多网卡情况下对网卡的选择. 即使只有单一网卡, 也要写1.

好了, 你可以从主机登录虚拟机进行管理了, 设置好正确的`/etc/hostname`, `/etc/hosts`, `/etc/network/interface`等文件. 并生成`ssh`密钥以实现无密码登录, 等等. 这是另一篇内容了, `如何搭建spark平台`.

## 快照管理

创建快照: `VBoxManage snapshot vmname take snapname [--description desc] [--live]`,有`--live`参数,快照创建过程中不会停止虚拟机.

删除快照: `VBoxManage snapshot vmname delete snapname`

恢复快照: `VBoxManage snapshot vmname restore snapname`

恢复到当前快照: `VBoxManage snapshot vmname restorecurrent`

列出快照: `VBoxManage snapshot vmname list [--details]`

编辑快照: `VBoxManage snapshot vmname edit snapname [--name <newname>] [--description <newdesc>]`

## 额外的命令

列出所有(运行)的虚拟机: `vboxmanage list vms|runningvms`

列出支持的虚拟系统类型: `vboxmanage list ostypes`

显示虚拟机的详细信息: `vboxmanage showvminfo vm_name`

给虚拟机重命名: `vboxmanage modifyvm vm_name --name new_name`

快照管理: `vboxmanage snapshot vm_name take/delete/restore snap_name`, 创建/删除/恢复快照.

删除虚拟机及相关文件: `VBoxManage unregistervm test --delete`

## 后记

最开始我是计划使用`vagrant`, 如果你读过之前的介绍`vagrant`的文章, 应该知道它可以生成一个`box`格式的文件, 很方便再生成新的虚拟机. 但有几点让我不舒服.

1, `vagrant`似乎不能改变虚拟机名, 默认的虚拟机名是字串加上随机数字. 这也就意味着你不能通过虚拟机名来管理虚拟机. 只能不断切换目录, 用`vagrant`命令. 但命令相比`vboxmanage`提供的功能太少.

2, `vagrant`建立的虚拟机, 并没有配置选项可以将网络设置成`natnetwork`. 而由前所述, 要建立集群, 虚拟机间的通信是必要的.

3, `box`格式并不是通用的格式, 这意味着, 如果用`box`来传输, 在对方计算机上也要安装`vagrant`. 你不一定有权限这样做.




