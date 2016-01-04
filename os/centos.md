# CentOS

安装后默认不启动网络服务,修改`/etc/sysconfig/network-scripts/ifcfg-xxx`网络接口的`ONBOOT=yes`即可.其中通过`ip addr`查看接口名.然后运行`service networks restart`即可连接网络.

## change aliyun sources

* `wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo`
* `yum makecache`
* `yum update`

## install virtualbox guest additions

* `yum groupinstall "Development Tools`开发工具包
* `yum install kernel-devel`内核源代码
* `yum install dkms --enablerepo=epel`,需要先安装`epel-release`包
* 菜单`设备->安装增强`
* `mount /dev/cdrom /media/cdrom`
* `./VBoxLinuxAdditions.run`

## 优先级管理插件

主要用于防止优先级低的仓库软件替换优先级高的仓库软件,以保证不会发生意想不到的升级.

安装源优先级管理插件: `yum install yum-plugin-priorities`

确保`/etc/yum/pluginconf.d/priorities.conf`文件中`enabled=1`,表示启用插件.

在`/etc/yum.repo.d/*.repo`的文件中,每个源的下面添加`priority=[1-99]`表示优先级,`1`优先级最高.通常设置`Base`,`Extra`,`Updates`的优先级为`1`,其他仓库`>10`.

## 第三方仓库

1, `EPEL`是`Fedora`特别兴趣小组维护的企业`Red Hat`使用的包.

安装: 
* `sudo yum install epel-release`,
* `CentOS5/6`中,可能要`--enablerepo=extras`参数启用`extras`源

2, `ELRepo`企业Linux的仓库包,主要关注强化硬件相关的包,包含各种驱动.

官网: `elrepo.org`

安装: 
* 导入`key`: `rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org`
* CentOS7: `rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-2.el7.elrepo.noarch.rpm`
* CentOS6: `http://www.elrepo.org/elrepo-release-6-6.el6.elrepo.noarch.rpm`
* `-U`表示升级安装,安装后旧版本包被移除.

3, `IUS`为企业Linux发行版提供更新版本的软件.

官网: `https://ius.io`

安装:
* `rpm -Uvh https://centos7.iuscommunity.org/ius-release.rpm`
