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

### port forward端口转发

`VBoxManage modifyvm vmname --natpf n "rulename,protocol,hostip,hostport,vmip,vmport"`

* `--natpf n`: 表示对虚拟机第几块网卡配置
* `hostip`: 省略表示对主机所有网络接口,也可指定为特定`ip`
* `vmip`: 当虚拟机为`dhcp`时可省略,若虚拟机为静态`ip`则不能省略

如配置`ssh`: `VBoxManage modifyvm vmname --natpf1 "ssh,tcp,,2222,,22`

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

虚拟机导出: `VBoxManage export vmname -o <name>.<ovf/ova>`,会导出到当前目录.`ovf`是多个文件,`ova`则为一个文件.

也可以添加一些别的信息: `--product pname`产品名, `--producturl purl`产品链接, `--vendor vname`厂商名, `--vendorurl vurl`厂商链接, `--version v`产品版本, `--description desc`产品描述, `--eula`最终用户许可协议信息,`--eulafile fname`版权文件.由于可以导出多个虚拟机到一个文件中,因此上述信息必须指定是对第几个虚拟机的描述,通过`--vsys num`指定.

通常推荐加入`--manifest`以生成`manifest`文件,可在导入时进行完整性检查,加入`--iso`以生成镜像,也可以使用`--options manifest,iso,nomacs,nomacsbutnat`,其中`nomacs`表示去掉导出虚拟机的`mac`地址.

导入虚拟机: `VBoxManage import ovafile|ovffile`


