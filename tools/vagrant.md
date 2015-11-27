# vagrant

简单的说,它是一个虚拟机管理软件.它本身不是虚拟机,通过在团队中配置相同的开发环境,解决在A电脑中可运行的代码而在B电脑中出错的痛点.

官网: `www.vagrantup.com`

## 制作自己的 base box

主要针对`virtualbox`,因为它的`vmware`版本是收费的.

1,新建虚拟机
* 用户名和密码请设置为`vagrant:vagrant`,如果你想公开你的`base box`,否则不必
* 安装`openssh-server`,在`ubuntu`中使用`sudo apt-get install openssh-server`
* 修改`root`密码为`vagrant`,命令为`sudo passwd root`.此主要方便vagrant执行一些特权命令以对虚拟机进行相应操作.
* 使`vagrant`用户的`sudo`免密码,具体为在`/etc/sudoers`文件中添加`vagrant ALL=(ALL) NOPASSWD: ALL`,一般推荐使用`visudo`命令修改此文件(但其默认使用`nano`,可添加`Default editor=/usr/bin/vim, env_editor`来配置默认编辑器为`vim`).`ubuntu`中不推荐直接修改此文件,而建议在`/etc/sudoers.d/`目录中添加文件写入你的内容.
* 添加`ssh`公钥到`~/.ssh/authorized_keys`文件中.若公布`base box`,默认`wget https://github.com/mitchellh/vagrant/tree/master/keys/vagrant.pub`,也可使用自己生成的公钥.
* 推荐安装`guest additions package`,一般点击`安装增强包`,可`sudo mount /dev/cdrom /media/cdrom; cd /media/cdrom; sudo ./VBoxLinuxAdditions.sh`,视需要可能要安装依赖`sudo apt-get install linux-headers-generic build-essential dkms`
* `virtualbox`的虚拟机使用`nat`网络设定时,默认虚拟机对主机或主机同网络的机器不可见,也就是不能`ssh`到虚拟机,需要手动设定端口转发(图形设置:`控制->设置->网络->端口转发`,关键的两点是主机端口任意和虚拟机端口`22`).但此设置会在生成`base box`时被清除.
* 安装你需要的软件,配置软件源(非必须)

2,打包为`base box`
* `vagrant package --base vmname --output /path/to/boxname.box`
* `vmname`是`virtualbox`虚拟机列表中的名字,省略`--output`时默认在当前目录下生成`.box`文件
* 此命令事实上可在任意目录下执行
* 执行命令前最好先关闭虚拟机,就目前的情况看,虽然`vagrant`能自动`ssh`到虚拟机并执行关机命令,但在`windows`执行失败,不确定和平台有没有关系

## 开始项目

```dos
mkdir project_name
cd project_name
vagrant init /path/to/basebox  # 注意:使用`\`会出错,建议路径使用`/`
vagrant up
```

之后会自动开启虚拟机,通过`vagrant ssh`可远程登录.但在`windows`平台,请确保`ssh`程序在执行路径中,或者通过你喜欢的`ssh client`,如`putty, xshell`.默认`up`时会建立主机`2222`端口到虚拟机`22`端口的映射,并删除此前添加的公开公钥而使用随机生成的公钥,而对应的私钥存储在当前项目目录的`.vagrant\machines\default\virtualbox\private_key`,用户名为`vagrant`.

开启虚拟机后,并不显示虚拟机,但其确实已运行,打开`virtualbox`后可看到项目运行的虚拟机名,点击`显示`则可显示虚拟机