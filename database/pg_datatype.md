# PostgreSQL数据类型

## 整型

* smallint, (int2), 有符号2字节整数
* smallserial, (serial2), 自增2字节整数
* integer, (int, int4), 有符号4字节整数
* serial, (serial4), 自增4字节整数
* bigint, (int8), 有符号8字节整数
* bigserial, (serial8), 自增的8字节整数

* boolean, (bool), 逻辑布尔型, 1字节

## 浮点型

* real, (float4), 单精度浮点数
* double precision, (float8), 双精度浮点数
* numeric, (decimal), 任意精度并准确运算, 如23.5141, 应该声明numeric (6, 4), 即精度6, 刻度4

`numeric`能精确运算, 但速度较慢, 允许`NaN`值, 对`NaN`的操作返回`NaN`.

## 字串型

* bit, 固定长度的比特字串
* bit varying, (varbit), 可变长度的比特字串
* character, (char), 固定长度字符串
* character varying, (varchar), 变长度字符串
* text, 变长字符串

当存储的字串小于指定的长度, `char`会补空白到指定长度, 而`varchar`则只存储小的字串.

比特字串, 即字符只有`0/1`的字串.

* bytea, 可变化二进制数据, 即字节数组

二进制字串本质上存储字节数组, 可存储非打印字串, 而`char/varchar`则只存储可打印字串.

## 日期和时间类型

* interval, 时间跨度
* date, 日期
* time, 时间, 不带时区
* time with time zone, (timetz), 时间, 带时区
* timestamp, 日期和时间, 不带时区
* timestamp with time zone, (timestamptz), 日期和时间, 带时区

## 文件格式类型

* json, 文本json数据
* jsonb, 二进制json数据
* xml, xml数据

## 几何类型

* box, 长方形
* line, 直线
* lseg, 线段
* path, 几何路径
* point, 任何点
* polygon, 多边形
* circle, 圆

`line`存储的是`Ax+By+C=0`, 其中`A,B,C`不全0, 即接受的输入为`{ A, B, C}`, 除此外, 也接受`[(x1,y1), (x2,y2)]`, 只是2点不能相同.

## 网络类型

* cidr, 网络地址, 带"/24"
* inet, 主机地址
* macaddr, mac地址

## 杂

* money, 现金数量, 8字节, 固定精度的小数, 具体精度由数据库的`lc_monetary`决定, 即`locale_sensitive`

* pg_lsn, 日志序列号

* tsquery, 文本搜索查询
* tsvector, 文本搜索文档
* txid_snapshot, 用户级别的事务ID快照

* uuid, 统一标识符

## 枚举类型

```sql
CREATE TYPE mood AS ENUM (’sad’, ’ok’, ’happy’);
CREATE TABLE person (
    name text,
    current_mood mood
);
INSERT INTO person VALUES (’Moe’, ’happy’);
SELECT * FROM person WHERE current_mood = ’happy’;
```

枚举类型值的大小即定义时的顺序, 左大. 不同枚举类型间不能比较大小.

## 数组类型

`pg`支持可变长多维数组, 任意支持的数据类型.

```sql
CREATE TABLE sal_emp (
    name text,
    pay_by_quarter integer[],  -- 一维
    schedule text[][]  -- 二维
);
INSERT INTO sal_emp 
VALUES (’Bill’, ’{10000, 10000, 10000, 10000}’, ’{{"meeting", "lunch"}, {"training", "presentation"}}’);
```

## 组合类型

```sql
CREATE TYPE complex AS (
    r double precision,
    i double precision
);
CREATE TYPE inventory_item AS (
    name text,
    supplier_id integer,
    price numeric
);
CREATE TABLE on_hand (
    item inventory_item,
    count integer
);
INSERT INTO on_hand VALUES (ROW(’fuzzy dice’, 42, 1.99), 1000);
```

## 范围类型

* int4range
* int8range
* numrange
* tsrange
* tstzrange
* daterange

```sql
CREATE TABLE reservation (room int, during tsrange);
INSERT INTO reservation VALUES (1108, ’[2010-01-01 14:30, 2010-01-01 15:30)’);
```
