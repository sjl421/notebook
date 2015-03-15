## Install

`mysql -D dbname -u username -p < cmd.sql`

* `\h` for help
* `\q` for exit

## User

mysql的用户数据存储在mysql数据库的user表中,对用户进行的创建,更改,删除都是常规的对mysql.user表的CRUD.

* create

```sql
mysql> insert into mysql.user(Host, User, Password) values("localhost", "user1", password("12345678"));
mysql> flush privileges;
```

* grant privileges

```sql
mysql> create database user1db;
mysql> grant all privileges on user1db.* to user1@localhost identified by "12345678";
mysql> flush privileges;
```

* delete

```sql
mysql> delete from mysql.user where User="user1" and Host="localhost";
mysql> flush privileges;
```
