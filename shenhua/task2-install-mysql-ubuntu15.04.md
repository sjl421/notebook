# compile and install mysql

1,创建mysql用户及组: `sudo useradd mysql -r -d /noexisten -s /bin/false`. `-r`指建立系统帐户,默认从10000减1;`-d`指帐户登录目录;`-s`指帐户登录时启动的shell,`/bin/false`指不允许登录.

2,安装依赖: `sudo apt-get install libaio1 libaio-dev libncurses5-dev`

3,下载mysql源码,解压并执行命令: `cmake . -DCMAKE_INSTALL_PREFIX=/usr/local/mysql -DMYSQL_DATADIR=/data/mysql -DSYSCONFDIR=/etc/mysql`

4,编译并安装:`sudo make && sudo makeinstall`

5,配置文件并拷贝可执行脚本:`cd /usr/local/mysql`, `sudo cp support-files/my-default.cnf /etc/my.cnf`, `sudo cp support-files/mysql.server /etc/init.d/mysqld`

6,修改配置文件: 在`/etc/mysql/my.cnf`文件,修改`basedir=/usr/local/mysql`和`datadir=/data/mysql`

7,初始化数据库: `sudo /usr/local/mysql/scripts/mysql_install_db --basedir=/usr/local/mysql --datadir=/data/mysql`

8,因为`/data/mysql`是数据库保存位置,则`mysql`用户必须有读写权限: `sudo chown -R mysql:mysql /data/mysql`

9,若系统为systemd,则需要编写相应的service文件,在`/etc/systemd/system/mysqld.service`

10,启动mysqld: `systemctl start mysqld`

11,重载相关改动和单元: `systemctl daemon-reload mysqld`

12,设置开机自启: `sudo systemctl enable mysqld`

13,如果只是想改变默认目录(数据目录,配置目录),无必要从源码编译安装.(未知方法)


(最后有点乱,事实上,开始不能成功,关闭服务后,又重载单元就成功了,此处不知,需要再探讨)

之后,`mysql -u root`登录mysql,修改root密码:`mysql>update mysql.user set Password=password("pwd") where User="root";` and `mysql>flush privileges;`


mysqld.service:
```
[Unit]
Description=MySQL Server
After=network.target

[Service]
ExecStart=/usr/local/mysql/bin/mysqld --defaults-file=/etc/mysql/my.cnf --datadir=/data/mysql
User=mysql
Group=mysql
WorkingDirectory=/usr

[Install]
WantedBy=multi-user.target
```

(相关语法请参考systemd的service语法,细节再研究)
