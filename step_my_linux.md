# step to mylinux

现在假设你已经把初始系统安装完成了,以下是一些配置成个人使用习惯的步骤.

注意: 全部假设系统为`ubuntu`,本着不折腾的原则.

## step 1: change software sources

编辑`/etc/apt/sources.list`,全部更换为以下内容:

```
deb[/deb-src] hostname/ubuntu versionname[-xxx] main restricted universe multiverse
```

其中:
    aliyun's host: http://mirrors.aliyun.com
    163's host:    http://mirrors.163.com
    -xxx包括:      security, updates, proposed, backports
    versionname:   15.04:vivid, 14.10:utopic, 14.04:trusty

然后更新系统: `sudo apt-get update && sudo apt-get upgrade`

## step 2: config vim


