# DOS命令参考

## `win-r`

- 输入`cmd`调出命令行界面.
- 输入`e:\path`调出资源管理器, 并打开到路径
- 输入`regedit`调出注册表编辑器
- 输入`calc`调出计算器, `notepad`调出记事本
- 输入`services.msc`调出系统服务界面, 可以停止/启动/禁用服务
- 输入`mstsc`调出远程登录, 即`microsoft terminal services client`

整个dos命令有dos风格的选项`/S`，也有linux风格的选项`-a`。整体看来并不是一个设计良好的命令体系。

绝大部分基础命令使用`help cmd`来获得帮助。而有linux风格的命令则往往使用`cmd -h`。

## `cd`

`cd \\`切换到根目录
`d:`切换到D盘

## `md`

`md dir`创建目录
`md dir1\dir2\dir3`创建多层级目录

## `rd`

`rd dir`删除空目录
`rd /S /Q dir`删除目录，可以非空，安静删除不确认

`copy`:
`copy c:\test.txt d:\\`复制C盘文件到D盘

`del`:
`del d:\test`删除test目录下的所有文件，不删除子目录及子目录下的文件

`dir`:
`dir c:\Windows`查看C盘的windows目录下的文件
`dir d:\abc.*`显示D盘下基本名为abc的文件
`dir /S d:\test`显示D盘的test目录及其子目录下的所有文件
`dir /W c:\Windows`宽屏显示，不显示修改时间、文件大小等作息，相当于Linux的不带选项的`ls`
`dir /a c:`查看所有文件，包括隐藏文件, 相当于linux的`-a`
`dir /p c:\Windows`分页查看，用于文件较多时，相当于linux的`| less`

`ren`:
`ren 1.txt 1.java`重命名，相当于Linux的`mv`

`type`:
`type 1.java`查看文件内容，相当于Linux的`cat`

`cls`:
`cls`清屏

`net`:
`net user`列出本机用户
`net user /add lzp lzp123`添加用户
`net user /del lzp`删除用户
`net user u_name u_pwd`改密码
`net localgroup administrators /add lzp`将lzp加入管理员组
`net localgroup administrators /del lzp`将lzp删除管理员组
`net user /active:yes administrator`启用管理员帐户, yes改为no则禁用

`ipconfig`:
`ipconfig`查看ip,netmask,gateway等信息
`ipconfig /all`更详细的信息，包括mac,dhcp,dns等信息
`ipconfig /displaydns`查看dns缓存信息

`netstat`:
`netstat`查看网络连接
`netstat -anotb -p tcp`查看所有(`-a`)tcp协议(`-p tcp`)的网络连接和侦听端口，以数字形式(`-n`)显示，显示进程ID(`-o`)，显示连接卸载状态(`-t`针对TCP),显示开启连接的程序(`-b`)
`netstat -r`显示路由表，同`route print`
`netstat -s`显示协议接收数据统计

`netsh`:
`netsh interface ipv4 show subinterfaces`显示系统的MTU值与对应的本地连接名称, 一般为1500
`netsh interface ipv4 show subinterfaces "name" mtu=1540 store=persistent`设置本地的MTU值.
`netsh advfirewall set global StatefulFTP disable`关闭防火墙

`runas`:
:`runas /user:administrator "cmd /k"`以某用户运行命令. 在管理员帐户被禁的情况下, 以上命令不会成功.

`wmic`:
`wmic computersystem list [brief|full]`: 列出系统信息
`wmic startup list full`: 列出启动项
`wmic process list full | more`: 列出当前进程
`wmic process get description,processed,parentprocessid,commandline /format:csv`以`csv`格式列出所要列出的进程选项.
`wmic service list full | more`: 列出当前服务
`wmic service get name,processid,startmode,state,status,pathname /format:csv`同上
`wmic job list full`: 列出工作列表
`wmic process call create notepad`: 新建notepad进程
`wmic process [handle/pid] delete`: 结束进程
`wmic process [handle/pid] call terminate`: 同上
`wmic process where "name='svchost.exe' and ExecutablePath<>'C:\\WINDOWS\\system32\\svchost.exe'" call terminate`结束路径非某的`svchost.exe`的进程.

## 批处理命令

`echo`表示显示此命令后的字符

`echo off`表示在此语句后所有运行的命令都不显示命令行本身

`@`与echo off相象，但它是加在其它命令行的最前面，表示运行时不显示命令行本身。所以, 为了不显示`echo off`本身, 往往在前加`@`, 变成`@echo off`. 如此整个dos窗口都不显示命令.

`call`调用另一条批处理文件（如果直接调用别的批处理文件 ，执行完那条文件后将无法执行当前文件后续命令）

`pause`运行此句会暂停，显示`Press any key to continue... `等待用户按任意键后继续 

`rem`表示此命令后的字符为解释行，不执行，只是给自己今后查找用的. 

`%`表示参数，参数是指在运行批处理文件时在文件名后加的字符串。变量可以从 `%0`到`%9`，`%0`表示文件名本身，字符串用`%1`到`%9`顺序表示。

`setlocal`使批处理文件中的环境变量本地化, 持续到出现匹配的 `endlocal` 命令或者到达批处理文件结尾为止. 即批处理文件中的环境变量变化不影响系统.

当前路径: `%cd%`是命令或批处理文件的执行路径, 而`%~dp0`是批处理文件的目录位置

`set var=value`设置变量, `%var%`则是引用变量

`%~n0`指批处理文件的名, 不包含后缀

`if defined var goto abc`如果变量定义, 则跳转到abc. `:abc`为标号, 在此行程序后. dos批处理中的条件语句只能进行标号跳转.

`if exist path_file goto abc`如果文件或文件夹存在, 则跳转

`if var == value goto abc`如果字符串相等则跳转

`if n1 equ n2 goto abc`如果数值相等则跳转
    - `gtr`大于
    - `geq`大于或等于
    - `lss`小于
    - `leq`小于或等于
    - `neq`不等于

如果参数给出的是文件或目录, 则可能通过如下检测参数是否给出:
```
if exist %1 echo "exist"
```

`if errorlevel 0 goto abc`如果上条命令成功则跳转

`abc.exe >NUL 2>&1`将程序`abc`执行的信息转到`NUL`, 错误信息也转到`NUL`

`echo %ERRORLEVEL%`可知道上条程序执行成功与否, 0为成功, 其余为失败

`echo.`转出为空行, 相当于回车符

`echo /b 1`退出批处理脚本, 而非`cmd.exe`, 同时退出码为1. 若没有`/b`选项则退出`cmd`

## 环境变量

- `%OS%`指操作系统名称
- `%PATH%`指执行文件搜索路径
- `%windir%`指windows系统安装目录, 往往是`c:\Windows`
- `%username%`指当前用户名
- `%userprofile%`指当前用户的主目录
- `%number_of_processors%`指处理器核数
- `%processor_architecture%`指处理器架构
- `%processor_identifier%`指处理器标识
- `%processor_level%`指处理器级别
- `%processor_revision%`指处理器修订版本
- `%pathext%`指可执行文件的所有扩展名
- `%cd%`指驱动器盘符+当前目录

