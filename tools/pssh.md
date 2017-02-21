# pssh

`sudo apt install pssh`

可能没有生成特定的短命令形式. 以下是脚本, 将`parallel-ssh`生成`pssh`命令.

```shell
for cmd in $(ls /usr/bin | grep parallel); do
  sudo ln -s $(which $cmd) $(dirname `which $cmd`)/p${cmd##*-}
done
```

主要用了`${STR##*-}`, 从字串前面开始删除到`-`, 贪婪.

并行`ssh`工具, 用于同时给多台服务器执行一个命令, 拷贝一个文件, 杀一个进程等.

* `pssh`:在多个远程主机上并行地执行命令
* `pscp`:把本地文件并行地复制到多个远程主机
* `pslurp`:从多个远程主机复制文件到本地
* `pnuke`: 在多个远程主机上打死进程
* `psync`:在多个远程主机上执行`rsync`同步

首先,要有一个主机列表文件,如`hosts.txt`,每行一个主机:`[user@]host[:port]`,也可以
使用`.ssh/config`文件中的主机配置.此后,每个命令执行时,都需要用`-h hosts.txt`表示
你执行命令的远程主机.

如复制文件到远程主机:`pscp -h hosts.txt local_file remote_absolute_path`.

如从远程主机复制文件:`pslurp -h hosts.txt remote_absolute_path_file local_file`.
不用担心会重名,会分别复制到主机名目录下.可以通过`-L local_dir`来指定本地的输出
目录,否则会在当前目录.

如并行地在远程主机执行命令:`pssh -i -h hosts.txt date`,其中`-i`表示会输出命令的
执行结果,否则什么也看不到.或者使用`-o dir`会将命令结果输出到`dir`目录下主机名文件
中.

最好的情况是在`hosts.txt`中把所有信息都写好,否则你可能需要`-l user`来指定用户名.

需要注意的是,`pssh`会把`date`及其之后的所有作为整体去执行,也就是说`-i`选项在`date`
后就可能表示为`date`命令的参数.
