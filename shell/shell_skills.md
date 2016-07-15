# shell skills

1. `sudo !!`,以特权执行上一条命令
2. `^old^new`,替换上条命令字串再执行
3. `cd -`,切换到上一次的目录
4. `man ascii`,列出ascii表
5. `printenv`,打印环境变量
6. 计算机信息查看:

```sh
lscpu  #显示CPU信息
lsusb  #列出所有USB设备
lspci  #列出所有PCI设备
lshw   #列出硬件信息
lsmod  #显示内核模块状态
lsblk  #显示块设备
lsb_release  #显示发行版信息
```

7. `cmd | wc [-l|-w|-b]`, 显示命令输出的行数/字数/字节数
8. `pkg-config pkg --modversion`,显示安装C库的版本


## 通配符

`*`可匹配0或多个字符, 但范围仅局限于指定的目录, 而`**`则可以递归进入指定目录的子目录. 如`**/*.js`当前目录及其子目录下所有的`.js`文件.
