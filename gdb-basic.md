# gdb

`$ gdb <program> <core> <pid>`, you can point the pid to attach.

程序停止点：断点(bp)，观察点(wp)，捕捉点(cp)，信号(sig)，线程停止(ts)

很多命令只要写前几个字母，能从众多命令中辨识，即可执行

~~~ sh
$> l   -(list source)
$> b   -(break num | func(class::method) | *addr | +-offset)
$> d   -(delete bpnum)
$> dis|en -(disable|enable bpnum)
$> r   -(run)
$> c   -(continue)
$> n   -(next statement, will not get into func)
$> ni  -(next instruction)
$> s   -(step statement, will get into func)
$> si  -(step instruction)
$> u   -(run till out loop)
$> p   -(print var, p/(x|d|u|o|t|a|c|f) var)
$> x   -(check memory, x/(nfu) addr, -num, format(s|i|..), bytes(b|h|w|g))
$> i r|b|f [reg|bp|fr]   -(info register or breakpoint or frame)
$> f   -(frame num, now)
$> bt  -(backtrace  all stack)
$> fin -(run till exit from func)
$> disas -(disassemble func)
$> q   -(quit)
$> set|show args -(set or show args)
~~~

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
