# mysql

安装: `sudo apt-get install mysql-server mysql-client`

登录控制台: `mysql -D dbname -u username -p < cmd.sql`

* `\h` for help
* `\q` for exit

## 用户操作

mysql的用户数据存储在mysql数据库的user表中,对用户进行的创建,更改,删除都是常规的对mysql.user表的CRUD.

注意: `mysql`很多操作都需要紧跟`flush privileges;`来刷新权限.

* 创建用户: `insert into mysql.user(Host, User, Password) values("localhost", "dbuser", password("password"));`
* 删除用户: `delete from mysql.user where User="dbuser";`
* 更改密码: `update mysql.user set Password=password("newpass") where User="dbuser";`

## 数据库操作

* 创建数据库: `create database exampledb;`
* 授权用户: `grant all privileges on exampledb.* to dbuser@localhost identified by "password";`
* 使用数据库: `use exampledb;`
* 删除数据库: `drop database exampledb;`
