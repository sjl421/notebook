# 在Ubuntu安装1080显卡

如果系统尚未安装,需要在未安装显卡的情况下,将系统安装好.

可选择有桌面环境的,也可以选择服务器版本.

系统安装好之后,修改`/boot/grub/grub.cfg`文件,在主启动项中,修改内核启动项:在`quite splash`后加上`nomodeset`.

将显卡装入主机,启动,在系统中运行在官网下载的安装文件.

如果系统是带桌面环境,安装显卡驱动需要关闭x服务器.

`sudo systemctl stop lightdm.service` 或`sudo /etc/init.d/lightdm stop`

在安装好驱动后,重新启动X服务, 将上述的`stop`改为`start`即可.
