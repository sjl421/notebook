# git server

`git`本身是分布式的程序,也就是说安装`git`后本身即可提供类似服务器的功能.

    ComputerA: `git init --bare /path/to/repository`
    ComputerB: `git clone username@hostname:/path/to/repo`  #使用ssh协议连接到A上

## gitolite

提供多用户和权限管理.

`sudo apt-get install gitolite`

添加`git`用户:
* `sudo adduser --system --shell /bin/bash --group --disabled-password --home /home/git git`
* 不能密码登录, 只能通过`ssh`的公钥登录.

配置仓库管理员的公钥:
* `ssh-keygen -t rsa -f ~/.ssh/id_rsa`
* `cp ~/.ssh/id_rsa.pub /tmp/$(whoami).pub`
* `sudo su - git`
* `gl-setup /tmp/*.pub`将临时目录下的公钥全部安装
* `exit`
* `git clone git@ip_addr:gitolite-admin.git`克隆配置仓库到本地. `ip-addr`可以为`localhost`或单独的`gitolite`服务器. 使用的是`git`协议.
* `gitolite-admin`目录下存储有`conf/gitolite.conf`配置文件, 里面是用户权限管理, 存储有`keydir/*.pub`公钥文件.
* 真正的仓库在`~/git/repositories`中, 但克隆时不需要指定路径. 只有`~/xxx/gitolite-admin/keydir`目录有公钥的用户才能克隆仓库.
* `ssh-copy-id -i .ssh/id_rsa.pub user@server`指令用于复制公钥到服务器的用户目录下. 此命令是个`shell`脚本, 当然你也可以使用`scp`命令.