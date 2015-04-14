# sql

## mysql

### install

`$ mysql -D dbname -u username -p < cmd.sql`

* `\h` for help
* `\q` for exit

### User

mysql的用户数据存储在mysql数据库的user表中,对用户进行的创建,更改,删除都是常规的对mysql.user表的CRUD.

* create
~~~ sql
mysql> insert into mysql.user(Host, User, Password)
      values("localhost", "user1", password("xxx8"));
mysql> flush privileges;
~~~
* grant privileges
~~~ sql
mysql> create database user1db;
mysql> grant all privileges on user1db.* to user1@localhost identified by "12345678";
mysql> flush privileges;
~~~
* delete
~~~ sql
mysql> delete from mysql.user where User="user1" and Host="localhost";
mysql> flush privileges;
~~~
* change password
~~~ sql
mysql> update mysql.user set password=password("new pwd")
      where User="user1" and Host="localhost";
mysql> flush privileges;
~~~

### database

~~~ sql
mysql> create database sqmp_db;
mysql> show databases;
mysql> use samp_db;
mysql> drop database samp_db;
~~~

### table

* create table
~~~ sql
mysql> create table samp_tb (
    id int unsigned not null auto_increment primary key,
    name char(8) not null,
    age tinyint unsigned not null,
    tel char(13) null default "-");
~~~
* insert table
~~~ sql
mysql> insert into samp_tb (id, name, age, tel) values (NULL, "lzp", "18", "12345678");
~~~
* select from table
~~~ sql
mysql> select name, age from samp_tb where age > 21;
~~~
* update table
~~~ sql
mysql> update samp_tb set name="enali" where id=5;
~~~
* delete table
~~~ sql
mysql> delete from samp_tb where id=2;
~~~
* alter
~~~ sql
mysql> alter table samp_tb add/change/drop/rename
~~~
* drop
~~~ sql
mysql> drop table samp_tb;
~~~

## postgresql

### user

~~~ sh
sudo -u postgres creatuser username
sudo -u postgres dropuser username
~~~

### database

~~~ sh
sudo -u postgres createdb -O username dbname
sudo -u postgres dropdb dbname
~~~

### console

~~~ sh
psql -U username -d dbname -h 127.0.0.1 -p 5432
sudo -u postgres psql
    \password username
    CREATE USER username WITH PASSWORD 'password'; # ';'不可省略
    CREATE DATABASE dbname OWNER username;
    GRANT ALL PRIVILEGES ON DATABASE dbname TO username;
    DROP DATABASE dbname;
    DROP USER username;
    \q
~~~
