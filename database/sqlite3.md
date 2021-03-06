# sqlite3

> live with scope    --enalix

## 序

最近突发奇想, 想写个coreutils的增强版, 实现一个非常简单的功能: 给目录加书签. 比如, `cd abc`是切换到abc目录, 但`cd ,abc`就是切换到abc书签, 这个书签可能对应很长的目录, 比如`/etc/apt/sources.d/`等.

初步思考了下书签和目录的对应用文件存取, 但发现控制逻辑太过复杂, 想着用数据库看看. 这里是学习笔记, 主要学习[菜鸟教程](www.runoob.com)提供的资料.

至于为什么用sqlite3, 因为简单, 轻量级. 本来要实现的项目就不大, 再搞个mysql或postgresql就小题大作了. android上好像有存储数据需求时也用的是sqlite.

其实项目的难点在于我还没想好要实现成什么样, 如果只是上面的cd, 其实可以简单的写个函数: `ncd() { [[ ${1:0:1} = "," ]] && cd $(sqlite3 file.db "select path from tbname where bm==${1:1} || cd $1; }`. 函数名不能是cd, 会提醒无限嵌套. 也不能代理给ruby脚本, 因为脚本会在子shell执行, 是不会改变当前shell的当前目录的.

注意, 一般在公众号发布的都不是最终稿, 笔记嘛, 总会有新认识或新见解, 又不能在公众号一发再发.可以关注我的github中的[笔记](https://github.com/enali/notebook).

## 开始

安装: 
* Ubuntu: `sudo apt-get install sqlite3`
* Mac: `brew install sqlite3`

`sqlite3 dbname`: 若库名存在则打开,不存在则创建,不指定则创建临时的库,退出时删除.

可以直接在命令行执行命令: `sqlite3 dbname .tables` 或执行sql语句: `sqlite3 dbname "select * from tb"` (不需要';').

`sqlite_master`是一个特殊表, 存储有数据库的元信息, 如表(table), 索引(index), 视图(view), 触发器(trigger), 可通过`select`查询相关信息.

sql关键字以常用函数, 大小写不敏感, 反正我喜欢都小写, 因为caps lock要给esc.

## 常用命令

sqlite3提供的特殊命令, 以`.`开头:
* `.help`: 帮助
* `.databases`: 列出数据库
* `.tables`: 列出表名
* `.open dbname`: 打开数据库
* `.save dbname`: 保存为数据库
* `.exit`: 退出, 或`Ctrl-D`
* `.schema [tbname]`: 列出表, 索引, 触发器的创建语句
* `.output fname.txt`: 写结果到文件
* `.show`, 显示各种设置的默认值
* `.indices tbname`, 列出某表的索引

各种设置:
* `.echo on|off`, 开启或关闭命令回显
* `.explain on|off`, 开启或关闭适合于EXPLAIN的输出模式, 更适合人阅读
* `.headers on|off`, 是否显示字段信息头
* `.stats on|off`, 开启或关闭统计信息
* `.timer on|off`, 开启或关闭命令执行的时间测量

模式, 即数据的显示方式, 有很多种, 但常用的就两个, `.mode line`用于表达式运算, `.mode column`用于表查询的多字段显示. 第二种通常还会开启信息头, 即字段名显示, `.header on`. 如果你想知道sql语句的执行时间, 可以`.timer on`.

## 常用操作

有趣的是, `sqlite3`使用动态类型, 默认情况下可以不指定类型, 除了主键是整数外, 其余列都可以插入任何类型, 会自动进行转换. 但你仍然 可以添加类型, 以表示优先选择此类型存储. `sqlite3`只有5种类型, `null`存储空, `integer`存储有符号整数, 字节占用看数的大小, `real`存储浮点数, `text`存储文本数据, `blob`存储(我也不知道这货做什么, 意思是说, 你怎么输入怎么存储, 不进行转换). 下表的类型其实都没有太大意义, 都会被转换成上述类型, 如`varchar(30)`, 数字参数被忽略, `varchar`含有子串`char`, 会作为`text`类型.

创建新表create:

```sql
create table company ( 
  id int primary key not null autoincrement, 
  name text not null collate nocase,   --大小写不敏感
  age int not null unique, 
  address char(50), 
  salary real default 50000.00 check(salary>0) 
);
```

注: 
* `int, text, real, char(5)`, 都是字段的类型
* `char(50)`, 表明此字段存储字符不超过50个
* `not null`, 表明此字段不能为空
* `primary key`, 表明此字段为基键, 不能重复
* `unique`, 确保某列中没有重复值
* `default`, 当列没有值时,提供默认值
* `check`, 确保某列中的所有值满足一定条件
* `autoincrement`, 确保列中值自动增加, 自然无需手动提供

修改表alter:

```sql
alter table company add column sex char(1);
--为company表添加列sex, 类型为char(1)
alter table company rename to old_company;
--为表重全名
```

注:
* Sqlite3中, `alter`允许用户重命名表,或向现有表添加一个新列
* 但不能重命名列, 删除列, 从表中添加或删除约束

删除表drop:

```sql
drop table company;
```

创建记录insert:

```sql
insert into company (id, name, age, address, salary) values (1, 'Paul', 32, 'Beijing', 20000.00);
insert into company values (1, 'Paul', 32, 'Beijing', 20000.00);
--插入所有字段时可省略列名
insert into company_bkp select * from company;
--将company表中的所有记录全部插入到company_bkp表中, 两表结构必须相似
```

更新记录update:

```sql
update company set address='Texas' where id==6; 
--将id为6的记录更新address字段为Texas
update company set address='Texas', salary=20000.00; 
--将所有记录的address字段更新为Texas, salary字段为20000
```

检索记录select:

```sql
select * from company;
select id, name from company where name = "someone" collate nocase; 
--查询company表中id和name字段, 大小写不敏感
select tbl_name from sqlite_master where type=='table'; 
--查询当前数据库存在的表
select current_timestamp; 
--查询当前时间戳
select * from company limit 6;
--只显示查询结果的前6行
select * from company limit 3 offset 2;
--只显示从第3行起, 再多2行, 一共3行
select * from company order by salary asc;
--以salary字段升序显示记录, desc为降序
select * from company order by name, salary asc;
--将结果按name和salary字段升序显示, 即name相同的按salary排序
select name, sum(salary) from company group by name;
--将结果中相同name的salary相加, 再构成name, sum(salary)列表
select name, sum(salary) from company group by name order by name;
--同上, 将结果以name升序显示
select * from company group by name having count(name) < 2;
--以name分组, 相同name记录数小于2, having设置分组的过滤条件
select distinct name from company;
--去重, 相同name不显示
select * from company where salary>10000 group by name having count(name)>=2 order by name
--相同name的记录数大于或等于2, 且salary大于10000, 以name升序显示
select * from company cross join department;
select * from company, department;
--将company的每一行与第二个表的每一行进行匹配, 分别有x和y行, 则结果有x*y行, 分别有x和y列, 则结果有x+y列. 交叉连接可能产生非常大的表
select * from company [inner] join department on company.id==department.emp_id;
--选取company的id列与department的emp_id列相等的行进行连接, 内连接是默认连接, 可省略inner, 横向连接
select * from company join department using (id);
--使用两表共有的id列进行相同值连接
select * from company natural join department;
--自动测试存在两个表中的每一列的值之间相等值 
select * from company left outer join department on company.id==department.emp_id;
--不同于内连接, 左外连接还会合并进第一个表的非匹配行, 这些行多余的列, 即对应第二个表的列为null. 之所以第一表显示, 因为是left嘛.
select col1, col2, ... from table1 where conditions
union [all]
select col1, col2, ... from table2 where conditions;
--不局限于上面的语句, 事实上union将两个select的结果纵向连接去重.因此这要求结果必须列相同, 列类型相同. join则是横向连接. union all不去重.
select c.id, c.name, c.age, d.dept from company as c, department as d where c.id==d.emp_id;
select c.id, c.name, c.age, d.dept from company as c join department as d on c.id==d.emp_id;
--通过as给表起别名
select * from company where id in (select id from company where salary > 45000);
select * from company where salary > 45000;
--子查询, `()`中的select先执行, 此处两个查询相同, 可与select, insert, update, delete混合使用
```

注:
* `where`在所选列上设置条件, `having`在由`group by`创建的分组上设置条件
* `where` -> `group by` -> `having` -> `order by`

删除记录delete:

```sql
delete from company where id==7
--删除id为7的记录
delete from company;
--删除所有记录
```

触发器trigger:

```sql
create trigger audit_log after insert on company
begin
  insert into audit (emp_id, entry_date) values (new.ID, datetime('now'));
end;
--创建触发器audit_log, 当向company表执行insert操作后, 会触发向audit表插入记录, 值为插入company表的id和执行时间戳.
select name from sqlite_master where type=='trigger' and tbl_name=='company';
--列出关联于company表的触发器
drop trigger audit_log;
--删除触发器
```

索引index:

```sql
create index salary_index on company (salary);
--对company表的salary列创建索引salary_index
select name from sqlite_master where type=='index' and tbl_name=='company';
--列出对应于company表的索引
select * from company indexed by salary_index where salary > 5000;
--使用索引从company表中选择数据
drop index salary_index
--删除索引
```

注: 
* 索引可加快数据检索, 但不利于数据更新和插入
* 索引不影响数据
* 可对多列索引, `(col1, col2)`
* 创建表时会自动创建主键primary key的索引


视图view:

```sql
create view company_view as
select id, name, age from company;
--为company表的id,name,age列创建视图
select * from company_view;
--列出视图的所有数据, 因为其只有真表的id,name,age三个列, 因此这里只列出三列
drop view company_view;
--删除视图
```

注:
* 可将视图认为是虚表, 它本身不真正存储数据, 它只是提供真正表的一个观察角度
* 因为视图不是真正的表, 因此并不能插入或更新数据, 但可能创建触发器, 当插入或更新数据时, 执行真正的操作.

事务:

```sql
begin;  --事务开始
delete from company where age==25;  --删除age等于25的所有记录
rollback;  --回滚, 即恢复数据
commit;  --提交更改
```

注:
* 事务具有原子性, 即事务要么成功要么失败, 而不会停留在中间状态
* 事务只与insert, update, delete一起使用

## 表达式

算术运算符, `+ - * / %`, 加减乘除余.

```sql
select 10+20;
```

逻辑运算符: `==, !=, >, <, >=, <=`. `and, or`

```sql
where col1 >= 25 and col2 <= 90
where col is not null
where col like 'Ki%'  
--字段为Ki开头的字串, %:零或一或多个, _:一个
where col glob 'Ki*'  
--同上, 大小写敏感, *:零或一或多个, ?:一个
where col in (25, 27)  
--字段为25或者27
where col not in (25, 27)  
--字段不是25也不是27
where col between 25 and 27  
--字段在25到27之间
select age from company
where exists (select age from company where salary > 65000)
-- 子查询, 如果存在salary大于65000的age字段, 则列出所有age字段
```

位运算符: `& | ~ << >>`, 并或反左右移.

```sql
select 60 | 13
```

`null`值, 只能用`where col is null/not null`, 而不能跟别的值比较. `null`值与零值或包含空格的字段是不同的, `null`是没有值, 而非值为空.

## 时间函数

* `date`, 日期
* `time`, 时间
* `datetime`, 日期和时间
* `strtime`, 格式化字串

```sql
select date('now');
select strtime('%s', 'now');
```

## 常用函数

sqlite提供了少量常用的函数:
* `count`, 谋算表的行数
* `max`, `min`, 选择某列的最大值, 最小值
* `avg`, 计算某列的平均值
* `sum`, 计算某列的总和
* `random`, 返回伪随机数
* `abs`, 返回绝对值, 所有字串返回0.0
* `upper`, 将字符串转换为大写字母
* `lower`, 将字符串转换为小写字母
* `length`, 返回字串的长度
* `sqlite_version`, 返回sqlite的版本

```sql
select count(*) from company; 
--company表的行数, 注意, 指定特定列时, 为null值的记录不计数
select max(salary) from company; 
--选择company表的salary列的最大值 
select avg(salary) from company;
select sum(salary) from company;
select random();
select abs(-5);
select upper(name) from company;
--列出company表的name列的大写
```

