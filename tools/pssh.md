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
