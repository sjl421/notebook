# compile install apache2

1, 安装依赖: `sudo apt-get install libapr1 libapr1-dev libaprutil1 libaprutil1-dev libpcre++0 libpcre3 libpcre3-dev libpcre++-dev`

事实上,如果全部源码安装apache2,则以上这些包都应该编译安装,搜索apr,apr-util,pcre等包.

2, 
```
./configure 
--prefix=/usr/local/apache2 
--bindir=/usr/local/bin 
--datadir=/data/apache2 
--sysconfdir=/etc/apache2 
--mandir=/usr/share/apache2  
--with-apr=path_apr 
--with-aprutil=path_aprutil 
--with-pcre=path_pare

make && sudo make install
```

3, 拷贝或链接: `sudo cp /usr/local/apache2/bin/apachectl /etc/init.d/apache2`

4, 启动: `sudo /etc/init.d/apache2 start | stop | restart`
