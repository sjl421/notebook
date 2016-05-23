# go spark

搭建`spark`开发环境.

相关软件: `Java`, `maven`, `Scala`, `Hadoop`, `Spark`.

3个虚拟机, 分别命名为`master`, `worker1`, `worker2`. 用户名和密码最好设置成`hadoop`. 解压相关软件的二进制发行包到`/opt`, 并在`/etc/bash.bashrc`文件中设置相关`xx_HOME`的环境变量, 并添加执行程序到`PATH`路径.

在`VirtualBox`建立`natnetwork`网络, 但不开启`dhcp`, 使用静态IP. 

```sh
vboxmanage natnetwork add --netname bigdata --network "192.168.2.0/24" --enable
```

在虚拟机中, 修改`/etc/hostname`文件, 将主机名改为对应的`master/worker1/worker2`.

在虚拟机中, 设置静态IP. 修改`/etc/network/interfaces`文件. 其中`xx`可自取, 如`master`为`192.168.2.10`, 其余的为`.11`和`.12`. DNS一般设置成主机的即可. 除了图形界面查看外, 可输入`ipconfig /all`查看.

```sh
auto eth0
iface eth0 inet static
address 192.168.2.xx
netmask 255.255.255.0
gateway 192.168.2.1
dns-nameservers ip1 ip2
```

在虚拟机中, 修改`/etc/hosts`文件, 设置主机名到IP的映射. 此处有一个`127.0.1.1`对应本机, 修改其对应的主机名为`master/worker1/worker2`. 此主要方便考虑, 并非必要.

```sh
192.168.2.10    master
192.168.2.11    worker1
192.168.2.12    worker2
```

在虚拟机中生成密钥, `ssh-keygen -t rsa`, 一直回车即可. 执行`ssh-copy-id user@host`将公钥复制到别的虚拟机中. 如在`master`中, 此处的`host`是指`worker1/worker2`. 此处的`host`指用户, 即你建立的用户`hadoop`.

除了修改`hosts`文件外, 另一种方法是在`~/.ssh/config`文件中添加相关内容.

```sh
host master
    user hadoop
    hostname 192.168.2.10
    port 22
    identityfile ~/.ssh/id_rsa.pub
```

为方便在主机中管理虚拟机, 为所有虚拟机添加到主机端口的映射.

```sh
vboxmanage natnetwork modify --netname bigdata --port-forward-4 "master:tcp:[]:2010:[192.168.2.10]:22"
vboxmanage natnetwork modify --netname bigdata --port-forward-4 "worker1:tcp:[]:201x:[192.168.2.1x]:22"
```

如果安装有`Cygwin`或类似产品, 在主机中生成主机密钥, 并将密钥复制到虚拟机中. 并可考虑配置`.ssh/config`文件, 不过`hostname`为`127.0.0.1`.

```sh
ssh-copy-id hadoop@127.0.0.1 -p 201x
```

现在基本已经不需要`VirtualBox`的图形管理界面了. 开启虚拟机时, `vboxmanage startvm spark_master spark_worker1 spark_worker2 --type headless`, 后再通过`ssh master/worker1/worker2`直接登录虚拟机进行管理. 如果你还安装了`tmux`且显示器足够大, 则可以开3个面板, 分别进行管理.



