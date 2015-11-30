# linux cmd

## scp

用于计算机间文件传输
`scp /dir/src_file name@ip:/dir/dest_file`
* `-r` 复制目录 
* `-p` 通过指定端口
* `-f` 若dest存在，则删除再复制
* `-v` 显示复制过程 
* `-4|6` 强制IPv4或IPv6

## useradd

用来建立用户帐号和创建用户的起始目录。
`useradd [－d home] [－s shell] [－c comment] [－m [－k template]] [－f inactive] [－e expire ] [－p passwd] [－r] name`
* `-c`：加上备注文字，备注文字保存在passwd的备注栏中。 
* `-d`：指定用户登入时的启始目录。
* `-D`：变更预设值。
* `-e`：指定账号的有效期限，缺省表示永久有效。
* `-f`：指定在密码过期后多少天即关闭该账号。
* `-g`：指定用户所属的起始群组。
* `-G`：指定用户所属的附加群组。
* `-m`：自动建立用户的登入目录。
* `-M`：不要自动建立用户的登入目录。
* `-n`：取消建立以用户名称为名的群组。
* `-r`：建立系统账号。
* `-s`：指定用户登入后所使用的shell。
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

## lsof

look for the process using file: `lsof file`

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
