# gdb

`$ gdb <program> <core> <pid>`, you can point the pid to attach.

程序停止点：断点(bp)，观察点(wp)，捕捉点(cp)，信号(sig)，线程停止(ts)

很多命令只要写前几个字母，能从众多命令中辨识，即可执行

~~~ sh
$> b   -(break num | func(class::method) | *addr | +-offset)
$> d   -(delete bpnum)
$> dis|en -(disable|enable bpnum)
$> c   -(continue)
$> u   -(run till out loop)
$> p   -(print var, p/(x|d|u|o|t|a|c|f) var)
$> f   -(frame num, now)
$> bt  -(backtrace  all stack)
$> fin -(run till exit from func)
$> set|show args -(set or show args)
~~~

## 启动

想要调试程序,首先在二进行程序中包含调试信息:`gcc -g abc.c`.

以安静模式启动gdb: `gdb -q a.out`. 不然会显示软件协议之类的信息.在gdb中反汇编出来的是`AT&T`语法,可`echo "set dis intel" > ~/.gdbinit`改变这一点.或仅仅在gdb中键入该命令.

启动调试后,通过`l`命令列出源码,是`list`的缩写.且只有在包含调试信息时才有源码信息.

通过`b`命令设置断点,如在main函数处设置断点:`b main`.

通过`disassemble`命令反汇编,如反汇编main函数:`disas main`.有了反汇编指令,就可以在指令地址处设置断点:`b 0x40053e`.

通过`r`命令运行程序,程序会自动在断点处停止.此时,可以通过`info`命令查看寄存器:`i r`,或`i r rip`.

`info`命令也可以查看断点信息:`i b`, 以及帧信息:`i f`.

当然,查看内存的命令`x`是必要的.它有多种显示内存内容的方式,如以十六进制显示:`x/x addr`.也有八进制`x/o addr`,二进制`x/t addr`,十进制`x/u addr`.

`addr`表示内存地址,多为`0x40053e`之类的形式,也可以用寄存器表示`$rip`,也可以用变量表示`$1`.变量往往通过`print`命令产生,如`p $rbp-4`会产生`$n`变量,代表`$rbp-4`的值.

通过加上数字来查看多个地址的内容,如查看`$rbp-4`后5个地址的内容:`x/5xb $rbp-4`.这里有个新的符号`b`,表示字节,也就是`$rbp-4`地址后的5个字节.也可以用`h`表示2字节,`w`表示4字节,`g`表示8字节.综上,以十六进制形式查看`addr`地址处`n`个`8字节`内存的内容:`x/nxg addr`.

通过`r`运行程序后,可单步执行汇编指令:`n`或`s`,其中若是下条指令是函数则`s`会进入函数,而`n`不会.所以取决于你自己.比如,下条指令是调用`printf`函数,我想你不会想`s`进去的.

有个相似的单步执行,不过执行的是源码的语句:`ni`或`si`.区别是:有时候一条源码语句会汇编成多条二进制指令.

通过`q`命令退出调试.

## more usage

~~~ sh
$> path [<path>] -(show or add path)
$> set|show env [<name>=<value>] -(set or show env)
$> shell <cmd> -(run shell cmd)
$> cd, pwd
$> b if var=value -(stop when var equal value)
$> watch | rwatch | awatch  -(set watchpoint, when expr c|r|rw, stop
$> catch throw|catch|exec|fork|load|unload
$> display/i $pc -(undisplay, delete display, disable|enable display, info display)
~~~
