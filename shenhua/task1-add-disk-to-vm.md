# 给虚拟机添加新的硬盘

1, 在菜单栏虚拟机中调出设置，添加硬件：硬盘，给定大小。是否立即分配不重要。

2，重启系统。通过`sudo fdisk -l`命令查看，可`grep sdb`搜索关键字。

3, 分区`sudo fdisk /dev/sdb`,并格式化`sudo mkfs.ext4 /dev/sdb1`.

4, 编辑`/etc/fstab`文件设置自动挂载,注意能过`sudo blkid`获得设备UUID号.

5, 关于此文件有6个字段,`<dump>`是关于是否要备份,`0`不备份,`1`备份;`<pass>`是关于是否要`fsck`磁盘检查,`0`不检查,`1`优先检查,`2`检查.

6, `<options>`比较复杂,常用的可写`defaults`,其相当于`rw,suid,dev,exec,auto,nouser,aync`.分别是可读写,允许suid/sgid,,系统内二进行文件可执行,响应`mount -a`命令自动挂载,只允许root用户挂载,IO异步.

7, 除上外,`nls`可设置本地编码,防止乱码,`iocharset`用于不同文件系统交换文件时,自动进行编码转换,`noatime`关闭atime特性,`umask`设置整个文件系统的权限掩码.
