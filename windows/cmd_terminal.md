# Windows下使用类Unix的终端

主要有两种方式, 一种是将`Unix`下的各工具在`Windows`下重新编译, 作为单个的子程序
打包加入`PATH`路径, 再在类似`mintty`或`ConEmu`的终端下使用. 另一种是基于`Cygwin`
以及其二次打包来做.

`Cygwin`建立了`Unix`的系统调用到`Windows`的翻译, 也就是说, 工具几乎不用改变源代码
即可重新编译.

* Cygwin
* Babun
* cmder
