# mit-scheme

下载地址: `http://ftp.gnu.org/gnu/mit-scheme/stable.pkg/9.2/mit-scheme-9.2-x86-64.tar.gz`

安装:
* 依赖: `sudo apt-get install m4`
* 解压: `tar zxvf mit-scheme-9.2-x86-64.tar.gz`
* 安装: `cd mit-scheme-9.2/src && make compile-microcode && sudo make install`
* 动态可载入选项的安装: `blowfish`,`gdbm2`,`md5`,`mhash`.但要安装依赖:`libssl-dev`,`libgdbm-dev`,`libmhash-dev`

使用: `mit-scheme`
