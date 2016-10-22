# PostgreSQL

客户端: `sudo apt-get install postgresql-client`

服务器: `sudo apt-get install postgresql`

图形界面: `sudo apt-get install pgadmin3`

Mac:
* `brew install postgresql`
* `brew services start postgresql`, 开机启动服务
* `postgres -D /usr/local/var/postgres`, 启动服务

## 添加新用户和新数据库

安装后,默认生成名为`postgres`的数据库,数据库用户名和`Linux`系统用户名.

默认情况下, `psql`会以当前用户名登录, 并寻找当前用户名对应的数据库, 若没有数据库
则无法登录, 或指定`-d dbname`数据名来登录. 因此, 默认可以使用`psql -U postgres`
来登录, 或`psql -d dbname`. 否则, 用`createdb`创建用户名对应的数据库.

简单地说, 要么有当前用户对应的数据库, 要么指定当前用户对应的数据库, 要么使用有对
应数据库的用户名.

登录后, 使用`\l`查看有什么数据库, 使用`\c`来切换使用的数据库(从提示符看正在使用
什么数据库), 使用`\d`查看数据库有什么表. 使用`\?`查看命令帮助.

1, 使用`PostgreSQL`控制台

切换用户: `sudo su - postgres`

登录控制台: `psql` (系统用户以同名数据库用户身份登录)

* 设置密码: `\password postgres`
* 创建数据库用户: `CREATE USER dbuser WITH PASSWORD 'password';`
* 删除用户: `drop user dbuser;`
* 创建用户数据库: `CREATE DATABASE exampledb OWNER dbuser;`
* 赋予权限: `GRANT ALL PRIVILEGES ON DATABASE exampledb to dbuser;`
* 退出: `\q`

2, 使用`shell`命令行

`PostgreSQL`提供了命令行程序`createuser`和`createdb`.

* 创建数据库用户: `sudo -u postgres createuser [-d/-D][-r/-R][-s/-S]  dbuser`, 选项分别为是否允许用户创建数据库,创建新用户,是否为超级用户
* 登录控制台设置密码: `sudo -u postgres psql`
* 创建数据库: `sudo -u postgres createdb -O dbuser exampledb`
* 删除数据库: `dropdb exampledb`
* 删除用户: `dropuser dbuser`

## 登录数据库

`psql -U dbuser -d exampledb -h 127.0.0.1 -p 5432`, 分别指定用户,数据库,服务器,端口信息

如果当前系统用户名与登录数据库用户名相同,则`psql exampledb`即可,如果登录的数据库名也相同则`psql`即可.

## 控制台命令

* `\h`: 命令解释
* `\?`: 命令列表
* `\l`: 列出所有数据库
* `\c`: 连接其他数据库
* `\d`: 列出数据库的所有表格
* `\d [tb]`: 列出表格的结构
* `\du`: 列出所有用户
* `\e`: 打开文本编辑器
* `\conninfo`: 列出当前数据库和连接信息

## 数据库操作

* 创建新表: `CREATE TABLE user_tb1(name VARCHAR(20), signup_date DATE);`
* 插入数据: `INSERT INTO user_tb1(name, signup_date) VALUES('张三', '2013-12-22');`
* 选择记录: `SELECT * FROM user_tb1;`
* 更新数据: `UPDATE user_tb1 set name='李四' WHERE name='张三';`
* 删除记录: `DELETE FROM user_tb1 WHERE name='李四';`
* 添加字段: `ALTER TABLE user_tb1 ADD email VARCHAR(40);`
* 更新结构: `ALTER TABLE user_tb1 ALTER COLUMN signup_date SET NOT NULL;`
* 更名字段: `ALTER TABLE user_tb1 RENAME COLUMN signup_date TO signup;`
* 删除字段: `ALTER TABLE user_tb1 DROP COLUMN email;`
* 表格更名: `ALTER TABLE user_tb1 RENAME TO backup_tb1;`
* 删除表格: `DROP TABLE IF EXISTS backup_tb1;`

