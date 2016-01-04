# 为VMware虚拟机配置静态IP

项目需要,有3个虚拟机,其中一个用于各种测试,一个用于正常工作,一个用于kali渗透.

在kali渗透成功后,再在工作Linux机上手动安装软件再重现渗透.

因为彼此有SSH的需求和ftp的需求,且各个虚拟机扮演不同角色,因此配置静态IP非常必要,以在`/etc/hosts`文件写死IP方便使用用户名操作.

目前,默认所有虚拟机在NAT模式下,查看VMware的`编辑->虚拟网络编辑`的NAT设置看默认网关,查看DHCP设置看分配IP范围,掩码,再通过在宿主机上查看DNS服务器地址.

获取以上信息后,在Ubuntu的网络管理(右上角)编辑相关配置:其中IPV4的DHCP设置为手动,再创建静态地址,使用以上信息.

通过命令:`systemctl restart NetworkManager`或`sudo /etc/init.d/network-manager restart`来重启网络.

问题: Ubuntu有两种配置网络方式,甚至两种网络进程,分别是`networking`和`network-manager`.具体各发挥什么作用未知.

但已知`networking`方式(修改`/etc/network/interfaces`)无效.
