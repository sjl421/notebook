# install mysql

1, get source tar, and extract to any where.

2, 分别指定安装目录，数据目录和配置目录： `cmake . -DCMAKE_INSTALL_PREFIX=/usr/local/mysql -DMYSQL_DATADIR=/data/mysql -DSYSCONFDIR=/etc`
