# 建ivic模板

依赖:

```sh
sudo apt install qemu-utils qemu-kvm
```

1, 创建空的`.img`文件

```sh
qemu-img create -f qcow2 xxx.img 100G
```

`qcow2`是一种镜像格式, `xxx.img`为生成的镜像名, `100G`是生成的镜像大小.

注: 这里只是空的镜像文件, 下述命令将执行完整的系统安装过程, 安装成功后, `.img`文件才是需要的已经安装过的系统.

2, 安装系统到`.img`文件

```sh
kvm -hda ubuntu-16.04.2-server-amd64.img -cdrom ubuntu-16.04.2-server-amd64.iso -boot d -m 4096 -vnc :4
```
 
* `-boot d`表示优先从`CD-ROM`启动系统, 因为通过`-cdrom`指定了镜像
* `-vnc :4`表示绑定到本地的`4`接口, 然后在`vnc client`中指定`ip:4`来连接
* `-m 4096`指定内存大小

注: `.img`是要生成的镜像, `.iso`是下载的系统.

注: `kvm`是对`qemu-system-x86_64`命令的包装, 为了兼容旧的`qemu-kvm`包.

3, 配置系统

```sh
kvm -hda ubuntu-16.04.2-server-amd64.img -boot c -m 4096 -vnc :4
```

* `-boot c`表示从`1st`磁盘启动系统.

启动系统, 仍然通过`vnc client`做一些需要的配置.

可能需要`ssh`到虚拟机, 可采用端口映射的方式

```sh
kvm -hda ubuntu-16.04.2-server-amd64.img -boot c -m 4096 -vnc :4 -net user,hostfwd=tcp::2222-:22 -net nic
```

然后`ssh ubuntu@127.0.0.1 -p 2222`来登录.

4, 在`web`界面创建模板, 并上传创建的`.img`镜像(管理员账号)

可以用`scp`将镜像拷贝到`192.168.7.xx`的`/var/lib/ivic/storexx`目录中. 拷贝时, 目标目录中的镜像名是创建模板时上传的镜像的标识号, 并非上述命令创建的`.img`镜像名.

创建模板时, 把相关信息都要描述清楚, 特别是此版本的镜像相比原始版本的有什么不同, 以便区分.

5, 使用模板新建虚拟机

新建虚拟机后, 后台好像会做很多额外的工作, 像修改主机名, 修改`/etc/network/interfaces`文件等, 特别是`16.04`中, 启动虚拟机只启动了`localhost`接口.