# Termux

这是我目前见过最强大的模拟终端, 几乎是一个完整的`Linux`环境, 可以通过`pkg`工具安装几乎所有的程序.

一个要注意的地方是, `Termux`使用了路径前缀, 目前是`/data/data/com.termux/files`目录. 因为不要求`root`权限, 所以那些命令工具只能安装在`usr/bin`目录下. 这会导致所有`shell`脚本, 以`#!/usr/bin/env bash`头行的无法使用`./script`来执行. 但`bash script`仍然可以.

## pkg的使用

* `search`, 搜索包
* `install`, 安装包
* `upgrade`, 更新包
* `help`, 显示帮助

## ssh

需要安装`openssh`包.

`IP`地址通过`ifconfig`查看, 默认`sshd`是不启用的, 通过`sshd`命令启动.

`Termux`的`sshd`不支持密码登录, 因为事实上, 它是一个单用户系统, 无论以哪个用户名登录都是同一个用户. 所以, `/etc/passwd`, `/etc/group`文件是不存在的, 因此也就不存在所谓的密码. 因此, 需要通过某种方式将`id_rsa.pub`公钥复制到`.ssh/authorized_keys`文件中.

但就试验结果, 即使是在`wifi`下, 使用`ssh`的体验也很差, 很慢.

最佳的方式是, 通过数据线连接手机后, 设置端口转发`adb forward tcp:8022 tcp:8022`, 将主机的`8022`端口与手机的`8022`端口连接, 然后直接登录主机的`8022`即可.

## packages

因为是全功能的`Linux`, 所以, 在手机的性能允许下, 可以安装任意有的包.
