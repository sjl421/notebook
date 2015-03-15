## Install

`mysql -h hostname -D dbname -u username -p < cmd.sql`

* `\h` for help
* `\!` for execute shell cmd
* `\e` for edit with `$EDITOR`
* `\. f_name` for execute f_name
* `\q` for exit

## User

mysql的用户数据存储在mysql数据库的user表中,对用户进行的创建,更改,删除都是常规的对mysql.user表的CRUD.

* create

```sql
mysql> insert into mysql.user(Host, User, Password) values("localhost", "user_name", password("user_pwd"));
mysql> flush privileges;
```

* grant privileges

```sql
mysql> create database user_db;
mysql> grant all privileges on user_db.* to user_name@localhost identified by "user_pwd";
mysql> grant select, insert on user_db.* to user_name@localhost identified by "user_pwd";
mysql> flush privileges;
```

* delete

```sql
mysql> delete from mysql.user where User="user_name" and Host="localhost";
mysql> flush privileges;
```

* change password

```sql
mysql> update mysql.user set password=password("new_pwd") where User="user_name" and Host="localhost";
mysql> flush privileges;
```

## database

```sql
mysql> create database db_name;
mysql> show databases;
mysql> use db_name;
mysql> drop database db_name;
```

## table

`mysql> show tables;`

* create

```sql
mysql> create table tb_name (
    col_name col_type);
```

* insert

```sql
mysql> insert [into] tb_name [(col1, col2, col3, ...)] values (val1, val2, val3, ...); 
```

* select

```sql
mysql> select col_name from tb_name [where condition];
```

* update

```sql
mysql> update tb_name set col_name=new_val where condition;
```

* delete

```sql
mysql> delete from tb_name where condition;
```

* alter

`alter`用于在表创建后对表进行更改.

```sql
mysql> alter table tb_name add new_col col_type [after col_name];
mysql> alter table tb_name change col_name col_new_name col_new_type;
mysql> alter table tb_name drop col_name;
mysql> alter table tb_name rename tb_new_name;
```

* drop

```sql
mysql> drop table tb_name;
```
