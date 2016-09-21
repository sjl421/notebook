# VirtualBox

## Ubuntu install

* add `deb http://download.virtualbox.org/virtualbox/debian trusty contrib` to source list.
* get key `wget -q https://www.virtualbox.org/download/oracle_vbox.asc -O- | sudo apt-key add -`
* update and install `sudo apt-get update && sudo apt-get install virtualbox-5.0`
* select install `dkms`
* 手动安装拓展

## cli manage

First, add your install directory to system path.

Type `VBoxManage --help` to get help infomation.

基本命令:
* 列出已有的虚拟机: `VBoxManage list vms`
* 列出当前正在运行的虚拟机: `VBoxManage list runningvms`
* 列出支持的系统类型: `VBoxManage list ostypes`
* 显示虚拟机信息: `VBoxManage showvminfo vmname`
* 启动虚拟机: `VBoxManage startvm vmname`
* 启动虚拟机但不显示界面: `VBoxManage startvm vmname --type headless`
* 关闭/重启/暂停/恢复虚拟机: `VBoxManage controlvm vmname poweroff|reset|pause|resume`
* `poweroff`相当于拔电源,`reset`相当于硬件上的重置,都不保存状态,可能丢失数据
* 发送关机|休眼信息给虚拟机: `VBoxManage controlvm vmname acpipowerbutton|acpisleepbutton`(估计休眼只对windows有效)
* 保存虚拟机状态: `VBoxManage controlvm vmname savestate`

### groups

`modifyvm`只能在虚拟机未运行时修改虚拟机.

* 将虚拟机添加到组中,组不存在则创建: `VBoxManage modifyvm vmname --groups "/group1`
* 将虚拟机从所有组中移除,并删除空组: `VBoxManage modifyvm vmname --groups ""`
* 将虚拟机添加到多个组中: `VBoxManage modifyvm vmname --groups "/group1, /group2"`
* 将虚拟机添加到嵌套组中: `VBoxManage modifyvm vmname --groups "/group1/subgroup"`
* 为虚拟机重命名: `vboxmanage modifyvm vmname --name newname`

### port forward端口转发

添加: `VBoxManage modifyvm vmname --natpf n "rulename,protocol,hostip,hostport,vmip,vmport"`

删除: `vboxmanage modifyvm vmname --natpf1 delete rulename`

* `--natpf n`: 表示对虚拟机第几块网卡配置
* `hostip`: 省略表示对主机所有网络接口,也可指定为特定`ip`
* `vmip`: 当虚拟机为`dhcp`时可省略,若虚拟机为静态`ip`则不能省略

如配置`ssh`: `VBoxManage modifyvm vmname --natpf1 "ssh,tcp,,2222,,22`

在虚拟机运行时不能样更改.

### createvm

创建一个虚拟机: 

    VBoxManage createvm --name <name> 
                        --groups <group>,... 
                        --ostype <ostype>
                        --register
                        --basefolder <path>

最简单的一个创建`ubuntu`虚拟机: `VBoxManage createvm --name test --ostype "Ubuntu_64" --register`

也可以后期注册: `VBoxManage registervm /path/to/test/test.vbox`
删除虚拟机: `VBoxManage unregistervm test --delete`同时删除相关文件

### snapshot

创建快照: `VBoxManage snapshot vmname take snapname [--description desc] [--live]`,有`--live`参数,快照创建过程中不会停止虚拟机.

删除快照: `VBoxManage snapshot vmname delete snapname`

恢复快照: `VBoxManage snapshot vmname restore snapname`

恢复到当前快照: `VBoxManage snapshot vmname restorecurrent`

列出快照: `VBoxManage snapshot vmname list [--details]`

编辑快照: `VBoxManage snapshot vmname edit snapname [--name <newname>] [--description <newdesc>]`

### import/export

虚拟机导出: `VBoxManage export vmname -o <name>.<ovf/ova>`,会导出到当前目录.`ovf`是多个文件,`ova`则为一个文件. 另外, `ova`其实就是`ovf`文件通过`tar`打包后生成的. 通常包含三个文件, `.vmdk`, `.mf`, `.ovf`. 其中`.mf`为文本文件,保存了另外两个文件的`SHA`值, 用于安装检验. `.ovf`是`XML`文本文件, 保存了虚拟机的相关信息.

一个`ova`文件可以包含多个虚拟机. 导出时, 可指定`ova`文件的标准, `--ovf09/10/20`, 默认1.0, 当然要使用最新标准2.0了. 可指定生成`mf`文件`--options manifest|iso|nomacs|nomacsbutnat`. 其中`nomacs`是去除虚拟机的网卡号. 也可以添加一些别的信息, 但这些信息是针对指定虚拟机的, 要`--vsys n`来指定, 从0开始: `--product pname`产品名, `--producturl purl`产品链接, `--vendor vname`厂商名, `--vendorurl vurl`厂商链接, `--version v`产品版本, `--description desc`产品描述, `--eula`最终用户许可协议信息,`--eulafile fname`版权文件.

导入虚拟机: `VBoxManage import ovafile|ovffile`

* 列出支持的更改选项: `-n`;
* 若要改变虚拟机名: `--vsys 0 --vmname newname`; 
* 若要改变系统类型: `--vsys 0 --ostype newtype`; 
* 若要改变cpu数: `--vsys 0 --cpus n`; 
* 若要改变内存: `--vsys 0 --memory newmb`; 
* 若要禁止声卡,各种控制器: `--vsys 0 --unit x --ignore`
* 改变虚拟磁盘路径: `--vsys 0 --unit x --disk newpath`

因为一个`ova`文件可能包含多个虚拟机, 因此所有设置选项需要通过`--vsys n`来指定是对哪个虚拟机设定. 另外, 各种控制器的`unit`数并不固定, 取决于你在导出时有没有加入多余的描述信息, 如产品名, 产品描述等. 也就是说, 最好在导入前`-n`选项查看一个.

通常情况下, 在导入虚拟机时, 可能需要改变虚拟机名, 内存, CPU数以及磁盘路径.

## networking

虚拟机可选择`nat network`, 选择同一个`nat`网络的虚拟机彼此可通信, 可访问外部网络. 同时可`dhcp`获取`ip`地址. 注意, 同一个网卡`mac`地址的获取到的是同一`ip`, 因此, 确保`mac`地址不同. 这就要求在导出虚拟机时去掉`mac`.

`virtual box`的`GUI`界面不提供创建`nat network`的功能. 需要使用命令行:

列出已建立的网络: `vboxmanage list natnetworks`

创建同时打开dhcp:
```sh
vboxmanage natnetwork add --netname bigdata --network "192.168.15.0/24" --enable --dhcp on  
```

创建后打开dhcp:
```sh
vboxmanage natnetwork modify --netname bigdata --dhcp on/off
```

开始和关闭, 移除网络:
```sh
vboxmanage natnetwork start/stop/remove --netname bigdata
```

添加删除端口转发:

```sh
vboxmanage natnetwork modify --netname bigdata --port-forward-4 "ssh:tcp:[]:1022:[192.168.15.5]:22"
vboxmanage natnetwork modify --netname bigdata --port-forward-4 delete ssh
```

添加名称为`ssh`的`tcp`协议的端口转发, 从主机的`1022`到客户机`192.168.15.5`的`22`端口. `--port-forward-4`是`ipv4`协议.

## 共享文件夹

设置共享条目:

```sh
vboxmanage sharedfolder add vmname --name share --hostpath d:\share [--transient] [--readonly] [--automount]
vboxmanage sharedfolder remove vmname --name share [--transient]
```

设置共享属性:

```sh
vboxmanage guestproperty set vmname /VirtualBox/GuestAdd/SharedFolders/MountPrefix "abc_"
# 设置挂载点前缀, 默认为"sf_"
vboxmanage guestproperty set vmname /VirtualBox/GuestAdd/SharedFolders/MountDir "/mnt"
# 设置挂载点目录, 默认为"/media"
```

最终会挂载到`/mnt/abc_share`.

## 克隆

相比导入导出, 可以用于传输虚拟机, 但如果只是在本地, 则没有必要. 可以直接使用`clonevm`.

```sh
vboxmanage clonevm vmname --name new_vm_name --register
```


