## start

若dbname存在则打开,若不存在则创建,若不指定dbname,则创建临时db,退出时删除.

```sh
$ sqlite3 dbname
```

```sql
sqlite> .help
sqlite> .open dbname
sqlite> .save dbname
sqlite> .exit
sqlite> .tables
```

## navigation

```sql
sqlite> create table tbll(one varchar(10), two smallint);
sqlite> insert into tbll values('hello', 10);
sqlite> insert into tbll values('goodbye', 20);
sqlite> select * from tbll;
```

## change output format

```sql
sqlite3> .mode list/line/column
sqlite3> .separator ", "
sqlite3> .width num1 num2 ...
sqlite3> .header off
```

## write results to a file

```sql
sqlite3> .output fname.txt
sqlite3> .output '| grep abstr'
```

## file i/o functions

```sql
sqlite3> create table images(name text, type text, img blob);
sqlite3> insert into images(name, type, img) values('icon', 'jpeg', readfile('icon.jpg'));
sqlite3> select writefile('icon.jpg', img) from images where name="icon';
```

## query the database schema

```sql
sqlite3> .tables
sqlite3> .schema [tb_name]
```

## CSV

```sql
sqlite3> .mode csv
sqlite3> .import csv_file_path
```
