# linux cmd

## scp

用于计算机间文件传输
`scp /dir/src_file user@ip:/dir/dest_file`
* `-r` 复制目录 
* `-p` 通过指定端口
* `-f` 若dest存在，则删除再复制
* `-v` 显示复制过程 

## useradd

用来建立用户帐号和创建用户的起始目录。
`useradd [－d home] [－s shell] [－c comment] [－m [－k template]] [－f inactive] [－e expire ] [－p passwd] [－r] name`
* `-d`：指定用户登入时的启始目录。
* `-s`：指定用户登入后所使用的shell。
* `-c`：加上备注文字，备注文字保存在`passwd`的备注栏中。 
* `-m`：自动建立用户的登入目录。
* `-f`：指定在密码过期后多少天即关闭该账号。
* `-e`：指定账号的有效期限，缺省表示永久有效。
* `-D`：变更预设值。
* `-g`：指定用户所属的起始群组。
* `-G`：指定用户所属的附加群组。
* `-M`：不要自动建立用户的登入目录。
* `-n`：取消建立以用户名称为名的群组。
* `-r`：建立系统账号。
* `-u`：指定用户ID号。

1, 建立一个新用户账户，并设置ID：`useradd david -u 544`
2, 新创建一个oracle用户，这初始属于oinstall组，且同时让他也属于dba组: `useradd oracle -g oinstall -G dba`
3, 无法使用shell，且其用户目录至/var/servlet/service: `useradd tomcat  -d /var/servlet/service -s /sbin/nologin`

## crontab

* usage: `crontab [options]`
  * `-u`: 指定用户名
  * `-e`: 编辑文件
  * `-l`: 列出任务
  * `-r`: 删除任务
* configure file: `/etc/crontab`
  * content: `分 时 日 月 周 cmd`
  * `*`:任意,`,`:单列,`-`:范围,`/`间隔
  * `* /5 3,6 * * /usr/bin/ls`: 每隔5小时,每月3号或5号,执行`ls`命令

## wget

usage: `wget [options] url`
  * `-r`: recurse
  * `-l`: level
  * `=m`: mirror
  * `-c`: continue
  * `-nc`: no-clobber
  * `-t`: tries
  * `-T`: timeout
  * `-A/R/D`: reject/accept/exclude domain type
  * `-I/X`: include/exclude dir
  * `-p`: page-requisites
  * `-np`: no parent
  * `-L`: 仅跟踪相对链接
  * `-http-user`,`-http-passwd`:
  * `-proxy-user`,`-proxy-passwd`:
  * `-Y`: proxy=on/off
  * `-b`: background
  * `-q`: quiet
  * `-v`: verbose
  * `-B`: base url
  * `-bind-address`:
  * `-N`: timestamp
  * `-w`: wait
  * `-waitretry`:
  * `-random-wait`:
  * `-Q`
  * `-k`: convert link to local link

## curl


## lsof

查找用户打开的所有文件: `lsof -u user`, 可用`grep fname`来查看特定文件

## locate

* 定位文件: `locate [-n] [-i] file`
* 更新locate使用的数据库: `updatedb`

## tail

* 查看文件末尾几行: `tail [-n N] [-f] file`
  * `-n`: 指定行数
  * `-f`: 跟随文件变化

## head

* 查看文件头几行: `head [-n N] [-c C] file`
  * `-n`: 头N行
  * `-c`: 头C个字符

## less

* 查看文件: `less file`
  * `v`: cmd v call the `$EDITOR` to edit the file

## find

find在系统搜索文件: `find [path] [options] [tests] [actions]`

* options:
  * `-depth`: 指定搜索目录层数
  * `-follow`: 跟随符号链接
  * `-maxdepths N`: 最多搜索N层目录
  * `-mount`: 不搜索其他文件系统
* tests:
  * `-atime/-mtime N`: N天前被访问或修改的文件
  * `-name pattern`: 匹配文件名
  * `-newer otherfile`: 比某文件新的文件
  * `-type d/f/l`: 类型序目录/文件/链接的文件
  * `-user uname`: 所属用户的文件
  * `-a/-o/!`: 测试间的和或反
  * `find . \( -name "_*" -o -newer f1 \) -type f -print`: 以'_'开头的文件或比f1新的文件,类型序文件,执行打印操作
* actions:
  * `-exec cmd`: 执行命令,动作以`/;`结束
  * `-ok cmd`: 执行命令,让用户确认
  * `-print`: 打印文件名
  * `-ls`:
  * `find . -newer f1 -type f -exec ls -l {} \;`: `{}`会被完整路径取代

## grep

`grep`在文件中搜索匹配的字符串: `grep [options] pattern [files]`

* options:
  * `-c`: 输出匹配行的数目
  * `-E`: 启用扩展表达式
  * `-h`: 不列出输出行前的匹配文件名
  * `-i`: 忽略大小写
  * `-l`: 只列匹配文件名,不输出匹配行
  * `-v`: 匹配取反
* pattern
  * `^ $ . []`
  * `[:alnum:]`, `[:alpha:]`, `[:ascii:]`, `[:blank:]`, `[:cntrl:]`
  * `[:digit:]`, `[:graph:]`, `[:lower:]`, `[:upper:]`, `[:print:]`
  * `[:punct:]`, `[:space:]`, `[:xdigit:]`
  * `-E`: 扩展匹配
    * `* + {n,m} ?`

## '!'

引用历史命令

## cd

* `cd ~`: 切换到用户主目录
* `cd -`: 切换到上次切换前的目录
* `cd !@`: 切换到上个命名的最后参数指定的目录

`cd`有两个选项:
```sh
ln -s .vim abc  #将abc链接到.vim目录
cd abc && pwd   #=> /home/spark/abc
cd abc && pwd -P   #=> /home/spark/.vim
cd -P abc  && pwd  #=> /home/spark/.vim
```

`-P`强制`cd`使用物理目录结构而不是符号链接.
`-L`强制跟踪符号链接, 但貌似加不加一样

`pwd -P`, 即使在符号链接的目录, 也能返回链接的真实目录.

`cd -P`, 即使目录是符号链接, 也切换到真实目录.

`cd`, 若目录是符号链接, 则切换到符号链接

## lscpu

获取CPU信息:
```sh
$ lscpu
Architecture:          x86_64         # 架构
CPU op-mode(s):        32-bit, 64-bit # 指令模式
Byte Order:            Little Endian  # 大端/小端
CPU(s):                8              # 逻辑CPU数
On-line CPU(s) list:   0-7            # 运行的CPU列表
Thread(s) per core:    2              # 每核线程数
Core(s) per socket:    4              # 每槽核数
Socket(s):             1              # 槽数
NUMA node(s):          1              # NUMA节点数
Vendor ID:             GenuineIntel   # intel
CPU family:            6              # 家族编号
Model:                 60             # 型号
Stepping:              3              # 细节
CPU MHz:               800.015        # 运行频率
BogoMIPS:              7182.82        # 综合频率
Virtualization:        VT-x           # 虚拟化
L1d cache:             32K            # 一级数据缓存
L1i cache:             32K            # 一级指令缓存
L2 cache:              256K           # 二级缓存
L3 cache:              8192K          # 三级缓存
NUMA node0 CPU(s):     0-7
```

## diff

diff is a useful tool to differ two files.

```sh
diff file1 file2
diff -r dir1 dir2
```

* `-E`: ignore tab expansion
* `-Z`: ignore trailing space
* `-b`: ignore space change
* `-w`: ignore all spaces
* `-B`: ignore blank lines
* `-I`: ignore matching line regexp

## meld

安装: `sudo apt-get install meld`

meld is visual diff and merge tool targeted at developers.

## vimdiff

`vimdiff file1 file2`

## ag

`https://github.com/ggreer/the_silver_searcher`

安装: `apt-get install silversearcher-ag`

## sort

* `-b`, 忽略前导空白符
* `-f`, 忽略大小写
* `-i`, 忽略非打印符
* `-M`, 排序月份
* `-n`, 排序数字
* `-r`, 逆序
* `-t`, 分隔符
* `-k`, 字段数

## espeak

text to speech

`sudo apt-get install espeak espeak-data`

Usage:
* `espeak 'hello'`, 读字串
* `espeak -f <file>`, 读文件

## Xclip

一个剪贴板管理软件.

有两种形式的剪贴板,一种是高亮文本再通过鼠标中键粘贴,另一种是选择文本显式复制`C-c`和显式粘贴`C-v`.

Usage:
* `xclip file`, 将文件内容复制到剪贴板,中键粘贴
* `xclip -o [file]`, 输出第一种剪贴板内容
* `xclip -selection clipboard file`, `C-v`粘贴
* `xclip -selection clipboard -o [file]`, 输出第二种剪贴板内容
* `xclip -l n file`, 等待文件内容被粘贴`n`次才退出

## mail

安装: `sudo apt install mailutils`

使用:
* `mail -s "主题" user@example.com < file`, 将文件内容发送给邮件地址
* `echo "内容" | mail -s "主题" user@example.com`, 将特定内容发送给邮件
* `echo "内容" | mail -s "主题" -A test.txt user@example.com`, 可带附件
* `mail -s "主题" user1@example.com,user2@example.com < file`, 将内容发给两个邮件