# packer

简单的说,它是一个根据配置文件自动化生成虚拟机的软件.可直接用于生成`vagrant`的`base box`.

官网: `www.packer.io`

当我们使用`virtualbox`新建虚拟机时,大致有如下过程:
* 在`virtualbox`软件中新建虚拟机,设置一些如`虚拟机名称,类型版本`,`内存大小`,`虚拟硬盘类型,大小,分配方式`,`虚拟机在本地存储位置,大小`,对应`packer`的`builders`配置
* 指定启动光驱,启动虚拟机
* 选择启动环境语言和启动选项,对应`packer`的`boot command`配置
* 正式进行系统安装,回答一些问题(如`语言环境,地区,键盘布局,硬盘分区,用 户名和密码,文件迁移,GRUB安装位置`等),不断下一步.此时基本交给系统安装程序(不同发行版使用的系统安装程序不同,如`Debian`的`DebianInstaller`和`ubuntu`的`ubiquity`),其主要根据`preseed.cfg`配置文件.需要在`packer`的配置中指定`preseed.cfg`.
* 安装重启后,安装常用软件,此通过`ssh`到虚拟机然后执行相应脚本或命令完成.对应`packer`的`provisioners`
* 关闭虚拟机,可选打包为`vagrant base box`,对应`packer`的`post-processors`