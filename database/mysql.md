# mysql

安装: `sudo apt-get install mysql-server mysql-client`

登录控制台: `mysql -D dbname -u username -p < cmd.sql`

* `\h` for help
* `\q` for exit

要先确认`mysqld`已经启动. 使用`mysql -uroot`回车, 如果没有设置`root`密码则可以直接进入. 否则, 加`-p`选项, 再输入根用户密码.

## 用户操作

mysql的用户数据存储在mysql数据库的user表中,对用户进行的创建,更改,删除都是常规的对mysql.user表的CRUD.

注意: `mysql`很多操作都需要紧跟`flush privileges;`来刷新权限.

* 创建用户: `insert into mysql.user(Host, User, Password) values("localhost", "dbuser", password("password"));`
* 或`create user 'dbuser'@'localhost' identified by 'password';`
* 删除用户: `delete from mysql.user where User="dbuser";`
* 更改密码: `update mysql.user set Password=password("newpass") where User="dbuser";`

## 数据库操作

* 创建数据库: `create database exampledb;`
* 授权用户: `grant all privileges on exampledb.* to dbuser@localhost identified by "password";`
* 使用数据库: `use exampledb;`
* 删除数据库: `drop database exampledb;`

## 新版变化

在`5.7`版本中, `mysql.user`中, `Password`列被移除, 而使用`authentication_string`列. 也就是说, 无论设置还是修改密码, 都必要将上述的`Password`修改为`authentication_string`, 其余不变.

* `show databases;`: 打印已有数据库
* `use dbname;`: 使用指定数据库
* `show tables;`: 打印当前数据库的表
* `describe tbname;`: 打印指定表的描述信息

创建用户最好使用`create user 'uname'@'localhost' identified by 'passwd'`.

## python

```sh
sudo apt install mysql-client libmysqlclient-dev
pip install mysql-python
```

## 远程连接

1, `/etc/mysql/my.cnf`, 将`bind-address=127.0.0.1`注释掉.

2, `mysql -uroot -p`登录控制台, 修改用户为`%`:

```sql
update mysql.user set host='%' where user='username';
flush privileges;
grant all privileges on dbname.* to 'username'@'%' identified by 'password';
flush privileges;
```

3, 以`mysql -h remote_ip -u username -D dbname -p`登录
