# python有趣的技巧

## 赋值时i和i[:]的区别

`Python`中可以用`id(obj)`来查看对象的唯一标识符.

在列表的赋值中, 在列表元素发生改变时, `i=`会申请新内存并赋值给同一个变量, 而`i[:]=`会在原内存中写入.

虽然知道这个似乎没有什么用处, 时间测量结果也差不多.

## 逻辑运算符

`Python`的逻辑运算符使用`and/or/not`关键字, 不支持`&&/||/!`这些运算符, 两个版本皆如此.

但支持位运算符, 当对布尔值进行位运算时相当于进行逻辑运算.

但要注意, 位运算优先级较比较运算符高.

则有`3 > x and x > 1`, 也相当于`(3 > x) & (x > 1)`, 也相当于`3 > x > 1`.

是的, `python`可以写`3 > x > 1`.

## 字串前缀符

即`b"hello"`和`r"hello"`和`u"hello"`的区别.

`b`表示字节字串, `u`表示`unicode`字串, 在不同版本中, `str`类型默认的类型不同.

而`r`在两个版本中, 都表示去掉`\\`的转义机制. 考虑到`python`中, 单双引号的用处相同, 不存在`Ruby`中`'`会自动关闭转义.

## 字符串格式化

有三种:

* `"%s, %d" % (s, n)`
* `"{}, {}".format(s, n)`
* `format(12, "010d")`,  结果`0000000012`
* `"%010d" % 12`, 同上

第3种可以使用命名参数, `"{name}, {age}".format(name='lizp', age=12)`

## 作用域

```python
k = 4
def a():
  print(k)    #对,可以看到外部作用域
def b():
  k += 1      #错,但不可以修改外部变量
def c():
  global k
  k += 1      #对,声明k为全局变量, 可以修改
def d():
  n = 4
  def e():
    nonlocal n  #对,嵌套函数,使用外部变量,要nonlocal, 无论函数嵌套几层
    n += 4
```

## import导入

```py
# a.py
print("载入a.py")
import c
# c.py
print("载入c.py")
# b.py
print("载入b.py")
import a
######
python b.py
#=> 载入b.py
#   载入a.py
#   载入c.py
```

当运行`py`文件时, 其所导入的`py`文件中, 有`import`语句也会依次递归导入, 但同样的模块不会重复导入.

## 将01字串转为16进制字串

即, 将`"01100100101001"  =>  "0x1929"`

`hex(int(b, 2))`, 相反的有`bin(int(h, 16))`

即, `int(s, n)`, 可以将字串以`n`进制转化为整数, `bin/oct/hex`则可以将相应的整数转化为相应的进制字串.

## 编码

抛开`2/3`默认`str`编码不谈, 将`unicode`字串转为`bytes`字串: `s.encode()`, 相反的, `b.decode('utf-8')`.

## min/max/sort

```py
l = [1, 3, -2, -4]
sorted(l); min(l); max(l);
sorted(l, key=lambda x: abs(x))
min(l, key=lambda x: abs(x))
max(l, key=lambda x: abs(x))
```

## 正则表达式

正则表达式的匹配无非就两种, 一种是从给定的字符串中找某种匹配的子串, 另一种是判断给定的字符串是否符合某种匹配.

简单地说, 就是全匹配还是子匹配. 两种方式某种意义上是等价的, 因为全匹配中可以通过捕获组`()`获得某种形式子串.

`python`的正则表达式跟`Java`的很像, 也是用`re.compile(r"reg_str")`编译成正则对象, 执行`pat.search(str)`作子匹配, 执行`pat.match(str)`作起始匹配, 执行`pat.fullmatch(str)`作全匹配, 获得`Match`对象. 再通过`m.start(group_n)`, `m.end(group_n)`, `m.group(group_n)`这三个方法分别获取匹配的起始/结束索引, 以及匹配到的字串.

* `split(reg)`, 以匹配的子串为切分点, 分割字符串
* `findall(str)`, 以列表形式返回能匹配的所有子串
* `finditer(str)`, 以迭代器形式返回能匹配的所有子串
* `sub(repl, str)`, 进行匹配字串的替换
* `subn(repl, str)`, 会同时返回替换的次数

## 近似

* `math.ceil`, 向上取整
* `math.floor`, 向下取整
* `round`, 四舍五入

## zip

```py
a = ['a', 'b', 'c']
b = [1, 2, 3]
x = zip(a, b)  #=> [('a', 1), ('b', 2), ('c', 3)]
y = zip(*x)  #=> [('a', 'b', 'c'), (1, 2, 3)]
```

`zip`的参数为两个或多个可迭代对象, 因为某种程度上, `zip`能够同时实现`zip/unzip`的功能.

## 编码检测

```py
import chardet
f = open(file, 'rb')
enc = chardet.detect(f.read())  # 参数为字节序列
import codecs
codecs.lookup(enc['encoding']).name  # 编码的名称
```