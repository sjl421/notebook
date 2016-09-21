# boost

`C++`的准标准库.

## Install

* [官网](https://boost.org)下载源码包
* `./bootstrap --prefix=path_dir`, 可指定安装目录
* `./b2 --build-dir=build`, 在指定目录中构建

考虑到`boost`库非常庞大, 可用`--with-libraries=lib1,lib2`来指定要编译的库. 至于
有什么库, 可通过`--show-libraries`来列出支持的库.

若编译`python`库, 可通过`--with-python=python2`来指定使用`2.x`版本.
