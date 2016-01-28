# number calculate

## 数字运算

使用`$(())`将表达式放在括号中，即可达到运算的功能。
```sh
$ b=$((5*5+5-3/2))
$ b=$(expr 5 \* 5 + 5 - 3 / 2)
$ echo $b
```

使用`expr 表达式`来运算：
四则运算
```sh
# 参数与运算符间的括号不可省略，乘法要转义
expr 1 + 2  #=>3
expr 1 - 2  #=>-1
expr 1 \* 2  #=>2
expr 1 / 2  #=>0
expr 1 % 2  #=>1
```
逻辑运算
```sh
expr 1 \> 2  #=>0
expr 1 \< 2  #=>1
expr 1 = 2  #=>0
expr 1 != 2  #=>1
expr 1 \>= 2  #=>0
expr 1 \<= 2  #=>1
expr 1 \| 2  #=>1
expr 1 \& 2  #=>1
```

`bash` 不支持浮点运算，如果需要进行浮点运算，需要借助`bc,awk` 处理。其实主要是`bc`的运算。

```sh
$ c=$(echo "5.01-4*2.0" | bc)
$ echo $c  #=>-2.99
```

## 时间计算

时间加减：
a.将基础时间转为时间戳
```sh
# 将指定时间'-d'转为时间戳'+%s'
$ time1=$(date +%s -d '1990-01-01 01:01:01')
$ echo $time1
```
b.将增加时间变成秒
```sh
$ time2=$((1*60*60+20*60))
```
c.两个时间相加，计算出结果时间，将时间戳转为可读时间
```sh
$ time1=$(($time1+$time2))
# '%F'相当'%Y-%m-%d', '%T'相当'%H:%M:%S', '@$time1'意即从'1970-01-01'到现在的秒数
$ time1=$(date +%F\ %T -d "@$time1")
$ echo $time1
```