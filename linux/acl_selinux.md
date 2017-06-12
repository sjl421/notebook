# Security

## acl

* install acl : `sudo apt-get install acl`

change the `/etc/fstab`: add `acl` flag to all driver.

remount driver: `sudo mount / -o remount`

## selinux

三个模式：enforcing, permissive, disabled

* 检查当前状态： `getenforce`
* 设置模式： `setenforce 0|1`
* 查看文件的selinux信息： `ls -Z`
* 查看进程的selinux信息： `ps -Z`