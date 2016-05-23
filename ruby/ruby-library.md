# ruby library

## Numeric

类继承树:

* `Numeric`
  * `Integer`
    * `Fixnum`
    * `Bignum`
  * `Float`
  * `Rational`
  * `Complex`

运算符: `+`, `-`, `*`, `/`, `%`, `**`
数的表示: `123`, `0d123`, `0123`, `0o123`, `0x123`, `0b10011`, `1.23e4`, `1.23e-4`
分数: `Rational(1,3)`
复数: `Complex(1,3)`

Float类的浮点小数用2的冥的倒数来表示，如`1/2`, `1/4`, `1/8`, 则`1/3`,`1/5`,`1/6`等无法精确表示

```ruby
11.div 7       #=> 1      (无论x,y是Integer还是Float都返回商的整数)
11.quo 7       #=> 11/7   (x,y为整数时返回Rational,有Float时返回Float)
11.modulo 7    #=> 4      (商向左舍入,[x]<=x)
11.divmod 7    #=> [1, 4]
11.remainder 7 #=> (返回结果符号与x一致，商向原点舍入)
1/0            #=> (错误)
1.0/0          #=> (Infinity)
0/0.0          #=> (NaN)
a = 0.1 + 0.2; b = 0.3; a != b
```

### Math

三角函数

* `acos` `asin` `atan` `acosh` `asinh` `atanh`
* `cos` `sin` `tan` `sinh` `cosh` `tanh` `atan2(x,y)`

平方立方

* `cbrt`(立方) `sqrt`

指数对数

* `exp` `log` `log10` `log2` `gamma` `lgamma`

extra

* `erf`(误差函数) `erfc`(余补误差函数) `frexp`(浮点数分解为尾数指数)
* `hypot`(x,y; 直角三角形斜边) `ldexp`(x,y; 返回`x*2**y`)

常量

* `PI` `E`

```ruby
1.5.ceil  #=> [x]>=x, 右舍入
1.5.floor #=> [x]<=x, 左舍入
1.5.round #=> 四舍五入
```

类型转换: `1.5.to_i`, `5.to_f`, `1.5.to_r`, `1.5.to_c`

位运算: `~` `&` `|` `^` `>>` `<<`

```ruby
b = 240   #=> 0b1111_0000
~b = -241 #=> 0b1111_0001(补码),但实际上0b0000_1111
```

随机数

```ruby
r1 = Random.new(seed);  r1.rand
Random.rand                     #=> [0,1)
Random.rand(n)                  #=> [0,n-1)整数
```

## Array

创建数组:

* `[1, 2, 3]`
* `Array.new(3, 4)` # `[4, 4, 4]`
* `%w(a b c)`       # `['a', 'b', 'c']`, (`()`为分隔符,可以用`<>`,`||`等)
* `%i(a b c)`       # `[:a, :b, :c]`

```ruby
{a:1, b:2}.to_a              #=> [[:a, 1], [:b, 2]]
"hello world".split(/\s+/)   #=> ["hello", "world"]
["hello", "world"].join(" ") #=> "hello, world"
a = Array.new(3, [0, 0, 0])  #=> 指向同一对象[0, 0, 0],变一则都变
a = Array.new(3) {[0, 0, 0]} #=> 指向不同对象，矩阵
a= Array.new(3) {|i| i+=1}   #=> [1, 2, 3]
a = %w(a b c); b = %w(aa bb cc); c = %w(aaa bbb ccc);
a.zip(b, c) #=> [['a', 'aa', 'aaa'], ['b', 'bb', 'bbb'], ['c', 'cc', 'ccc']]
```

数组索引:

* `a[n]`, `a[n..m]`, `a[n...m]`, `a[n,len]`
* `a.at[n]`, `a.slice(n)`, `a.slice(n..m)`, `a.slice(n, len)`
* `a[n, len] = ['a', 'b', 'c']`, 形似替换,len为0时表现插入
* `a.values_at(n1, n2, n3)`, 获取`n1`,`n2`,`n3`索引处的元素

集合运算:

```ruby
a = %w(a b c); b = %w(b c d);
# 交，并，差集
a & b #=> ['b', 'c']
a | b #=> ['a', 'b', 'c', 'd']
a - b #=> ['a']
# 数组连接
a + b #=> ['a', 'b', 'c', 'b', 'c', 'd'] # 等价a.concat b
```

队列和栈运算:

```ruby
# 数组头
a.unshift #追加 a.shift #删除 a.first #引用
# 数组尾
a.push #追加,等价<<  a.pop #删除 a.last #引用
# queue, 先进先出，unshift, push
# stack, 先进后出，pop, push
```

extra:

```ruby
a.compact[!]  # 删除nil
a.delete(item)
a.delete_at(idx)
a.delete_if {|item| condition} #结果真删除
a.reject[!] {|item| condition}
a.slice!(n)
a.slice!(n..m)
a.slice!(n, len) # 删除索引处元素
a.uniq[!]   # 删除重复元素
a.collect[!]{|item| expr}  #同 map
a.map[!]{|item| expr} # 用expr结果替换数组元素
a.fill(val)
a.fill(val,begin)
a.fill(val, begin, len)
a.fill(val, n..m) #元素替换成val
a.flatten[!]
a.reverse[!]
a.sort[!]
a.sort[!]{|i,j| …}
a.sort_by{|i| …}
```

## String

字符转义:

* `\t` `\v` `\n` `\r` `\f` `\b`
* `\nnn` `\Xnn` `\Unnnn`
* `\C-x` `\M-x` `\M-\C-x`   # C为`ctrl`, M为`alt`

创建字符串:

* `%q(ruby said, "hello world")`: `()`为分界符,`{}`, `||`, `<>`

Here document:

常用`EOF`,`EOB`. `-`会忽略结束标识符`EOB`行前的空格，用于对齐美观

```ruby
str = <<-EOB
hello!
hello!
EOB
```

执行`shell cmd`并将结果作为字符串赋值: `\`shell cmd\``

`printf`输出到`stdout`, `sprintf`则返回字符串对象，不输出

```ruby
str.length
str.size
str.bytesize
str.empty?
str[idx]    # 可以索引赋值，但不同于Array，idx不能越界
str1 + str2 #新对象
str1 << str2
str1.concat str2 #对象不变
str.delete[!](substr)
str.reverse[!]
str.split(/reg/)
str.chop[!] #删除最后一个字符
str.chomp[!] #删除换行符
str.strip[!] #删除开关末尾的空白字符
str.upcase[!]
str.downcase[!]
str.swapcase[!]
str.capitalize[!]
str.tr[!]('A-E', 'a-e')     # UNIX的tr命令
str.index('a')
str.rindex('a') # 从右到左检索
str.include?('a')
str.sub[!](/reg/, 'abcd') # 只在首次匹配正则处替换
str.gsub[!](/reg/, 'abcd') # 替换所有匹配正则的字符串为'abcd'
str.each_line("\n")
str.each_byte
str.each_char
str.encode[!]("utf-8")      #Encoding.name_list
str.center(width, padstr=" ")   # 字符串置于width宽度的中间，其余用padstr填充
str.scan(/(\w+)(\w+)(\w+)/)    # 将字符串以reg的形式组成数组，[[$1, $2, $3], …]
str.scan(/reg/) {|match| …}     # 对每个匹配到的内容处理
```

## Hash

创建Hash:

* `{'name' => 'lzp', 'telphone' => '1565291'}`
* `{name:'lzp', telphone:'1565291'}`    #符号为键
* `Hash.new(inival)`    #指定不存在的键时的返回值,否则为nil
* `Hash.new {|hash, key| hash[key] = key.upcase}`   # 用块为每个键设置默认值

键相等： `k1.hash == k2.hash; k1.eql?(k2)` 返回`true`。注意：`1.0.eql? 1` 返回`false`

```ruby
h["key"] = val
h.store("key", val)
h["key"]
h.fetch("key"[, val]) {}   #fetch无键时异常，或指定val为无键时返回值, 或块结果为返回值
h.keys
h.values
h.to_a
h.each_key {|k| …}
h.each_value {|v| …}
h.each{|k,v| …}
h.each{|a| a[0],a[1]}
h.key?(k)
h.has_key?(k)
h.include?(k)
h.member?(k)   # 一样
h.value?(v)
h.has_value?(v)
h.length
h.size
h.empty?
h.delete(k)
h.delete(k) {|k| "no #{k}"}    # 若键k不存在，返回块结果
h.delete_if{|k,v| …};   # 无键时，返回hash
h.reject!{|k,v| …}      # 无键时，返回nil
h.clear     #=> h={}    # 清空散列对象，注意不是让变量指向空散列
```

## Regexp

创建: `/hello/`, `Regexp.new("hello")`, `%r(hello)`
匹配: `/hello/ =~ "hello, world"`
不匹配: `/hello/ !~ "goodbye world"`

```ruby
str = "[A-D]\d+"
pat = Regexp.new(str[, Regexp::IGNORECASE | Regexp::MULTILINE])
pat =~ "C345jkjkd"
```

匹配模式:

* `^` 行首 `$`行尾 `\A`字符串首 `\z`字符串尾 `\Z`若串尾为`\n`则匹配前字符

```ruby
"abc\n".gsub(/\z/, '!')     #=> 'abc\n!'
"abc\n".gsub(/\Z/, '!')     #=> 'abc!\n!'
```

* `[]` 匹配多字符中的一个, `[a-z]`, 但`-`只有位于首或尾时仅表示`-`, 如`[-abc]`
* `[^]`匹配以外的字符
* `.`匹配1个字符
* `\s`空白符(空格、制表符、换行符、换页符) `\d`数字0-9, `\w`英文字母和数字
* `\^`, `\.`, `\[` 转义元字符
* `*`重复0次以上  `+`重复1次以上  `?`重复0或1次
* `*`, `+`贪婪匹配, `*?`, `+?`懒惰匹配
* `{n,m}`重复n-m次,包括m次, `{n}`重复n次,`{n,}`重复n次以上, `{,m}`重复0-m次
* `()`重复一个组 `(a|b)`选择
* `$1`, `$2`, `$3`: 捕获匹配内容, `()`是必须, 引用的是`()`匹配的内容, 若`()`嵌套，则最外层先被引用`$1`
* `?:` 表示过滤不需要捕获的模式

```ruby
/(.)(?:\d\d)+(.)/ =~ '123456'   #
$1  #=> '1'
$2  #=> '6'
```

* `$\``,`$&`,`$'`: 分别表示匹配部分前,匹配,后的字符

```ruby
/C./ =~ 'ABCDEF"
$`      #=> 'AB'
$&      #=> 'CD'
$'      #=> 'EF'
```

匹配修饰符:

* `/abc/i`     #`i`表示忽略大小写, `Regexp::IGNORECASE`
* `/abc/m`     #`m`匹配多行，用`.`匹配换行符, `Regexp::MULTILINE`
* `/abc/x`     #`x`忽略正则中的空白符和`#`后的字符，可以给正则写注释, `Regexp::EXTENDED`

匹配`URL`: `%r|^(([^:?/#]+):)?(//([^?/#]*))?([^?#]*)(\?([^#]*))?(#(.*))?|`

将字符串`"a-b-c"`转换为`"A-B-C"`

```ruby
#1.
str.split('-').each{|i| i.capitalize!}.join('-')
#2.
str.gsub(/[a-zA-Z]+/) { |mat| mat.capitalize }
#3.
pat = %r|([a-zA-Z]+)-([a-zA-Z]+)-([a-zA-Z]+)|
```

## IO

* 标准IO对象`$stdin`, `$stdout`, `$stderr`, 但通常无需， 真接`getc`/`gets`输入，`puts`/`print`/`printf`输出
* 标准输入和输出是行缓冲，标准错误则是不缓冲，这可能会改变输出顺序
* 从控制台读取输入时，若输入多个字符，下次`getc`时会得到上次输入的下个字符。而即使只输入一个字符，也往往会忽略回车`\n`
* 当文件指针指向多字节字符的开始字节处，`getc`读一个字符，但若指向中间字节(通过`getbyte`乱了)时，`getc`读一个字节
* `utf-8`中大部分常用汉字都是3字节字符, 英文字母和数字以及各种控制符都是单字节字符
* 不能`seek`过文件的边界, 包括`set`时`offset`不能为负，`end`时`offset`不能为正, 前者引发异常，后者返回`nil`
* 在windows平台，读时将`\r\n`转为`\n`, 写时将`\n`转为`\r\n`, 注意：可能会改变文件大小和字符的偏移量。
* `StringIO`模拟`IO`对象的对象，对其的输出会保存到对象中，而非输出到控制台

```ruby
$stdin.readchar # 注意：若输入'a'再回车；下次再调用readchar会直接得到'\n'，必须清输入缓存或再加个readchar语句
$stdin.readline
$stdin.readlines # 以‘EOF’为结束符，Unix系统下常c-d
$stdin.tty? #=> true # IO对象是否与控制台关联
```

`File`文件对象:

```ruby
file = File.open(file, "mode[:encoding]")    # r只读 w只写 r+/w+读写 a追加 a+读取/追加
file = open(file, mode)     # File可省略
file.close  file.closed?
File.read(file)     #一次性读取文件
```

输入输出:

```ruby
io.gets(rs)
io.each(rs)
io.each_line(rs)
io.readlines(rs) # rs为换行符，默认$/, Unix下$/ = '\n'
io.lineno
io.lineno= # 行号, 第一行编码0
io.each_char
io.each_byte   # '我'是一个字符，但是多个字节byte
io.getc
io.ungetc(ch)   # 将ch指定的字符回退到io的输入缓冲
io.getbyte
io.ungetbyte(bt)
io.read(size)   # size指定字节大小
io.puts     # 字符串尾加换行符输出，多个参数时，分别加换行符输出
puts "hello, %s" % "lzp"
puts "hello, #{name}"
io.putc(str)    # str为字符串时输出首字符，戓str可为字符编码
io.print
io.printf
io.write(str)   # 返回输出的字节数
io << str1 << str2  # 返回接收者本身
io.pos  io.pos=     # 文件指针，以字节为单位
io.seek(offset, whence)     # whence: IO::SEEK_SET文件首, IO::SEEK_CUR当前位置, IO::SEEK_END文件尾
io.rewind   #文件指针返回文件头
io.truncate(size)   # 截断文件
io.binmode  # 将文本模式转为二进制模式，不可逆
io.flush    # 刷新缓冲
io.sync
io.sync=true    # io.sync为true时，写入缓冲会自动调用flush方法, $stdout默认false,$stderr默认true
io.popen(cmd, mode)     # mode同open(file, mode)一样
open("|cmd", mode)      # cmd必须带'|'管道符号中
```

## File/Dir

文件:

```ruby
File.directory?(path)
File.file?(path)
File.rename("before.txt", "after.txt")
File.rename("before.txt", "backup/after.txt")   # backup目录必须存在
FileUtils.cp("data.txt", "backup/data.txt")     # require 'fileutils'
FileUtils.mv("data.txt", "backup/data.txt")     # 可以跨文件、驱动器复制移动
File.delete(file)
File.unlink(file)   # 当文件内容节点无属性节点链接时，系统自动删除文件
File.stat(path)     # 返回File::Stat类的对象
File.atime/mtime/ctime(path)
File.utime(atime, mtime, path)  # 修改文件的atime, mtime, 时间可以是Time对象
File.chmod(mode, path)
File.chown(owner, group, path)
File.basename(path[, suffix])   # 指定扩展名，则结果去除扩展名
File.dirname(path)
File.extname(path)  # 返回扩展名，即最后一个'.'及其字符。若以'.'开头返回空，若无扩展名返回空
File.split(path)    # 分割为目录名和文件名，若无'/'时，返回"."和文件名
File.join(name1[, name2, …])    # 用File::SEPARATOR(默认'/')连接字符串
File.expand_path(path[, dir])   # 扩展为绝对路径
```

目录:

```ruby
Dir.pwd
Dir.chdir(dir)
Dir.open(path)
Dir.close
dir.each
Dir.read    # 目录作为特殊文件，其内容为其中的文件名
Dir.glob(通配符)    # 将匹配到的文件名（包括目录名）以数组形式返回
Dir.mkdir("tmp")
Dir.rmdir("tmp")
```

文件信息:

```ruby
fs = File.stat(path)     #=> (File::Stat类的对象)
fs.dev #=> (文件系统编号)
fs.ino #=> (i-node编号)
fs.mode #=> (文件属性)
fs.nlink
fs.uid
fs.gid
fs.atime
fs.mtime
fs.ctime
fs.rdev #=> (文件系统的驱动器种类)
fs.size
fs.blksize
fs.blocks
fs.uid
fs.gid #=> (uid/gid是数字, 需要调用Etc模块获取对应的用户名组名)
require 'etc'
pw = Etc.getpwuid(stat.uid); pw.name
gr = Etc.getgrgid(stat.gid); gr.name
```

文件测试:

```ruby
exist?(path)
file?(path)
directory?(path)
owned?(path)
grpowned?(path)
readable?(path)
writable?(path)
executable?(path)
size(path)
size?(path)
zero?(path)
```

文件查找:

对指定目录下的文件作递归处理

```ruby
require 'find'
Find.find(dir) {|path| …}   # 将dir目录下所有文件路径逐个传递给块
Find.prune  # 程序会跳过当前查找目录中的所有路径（包括子目录）
```

临时文件:

```ruby
require 'tempfile'
Tempfile.new(basename[, tmpdir])
# 生成文件名"basename + 时间＋进程ID＋流水号"
# 不指定目录名时，顺序查找ENV["TMPDIR"], ENV["TMP"], ENV["TEMP"], /tmp
tmpfile.close(real)     # real=true时立刻删除
tmpfile.open    # 打开close关闭的文件
tmpfile.path    # 返回临时文件的路径
```

`FileUtils`工具:

```ruby
require 'fileutils'
FileUtils.cp(from, to)
FileUtils.cp_r(from, to) # '_r'表示递归处理
FileUtils.mv(from, to)      # 可以跨文件、驱动器复制移动
FileUtils.rm(path)
FileUtils.rm_f(path)
FileUtils.rm_r(path)
FileUtils.rm_rf(path)
FileUtils.compare(from, to)
FileUtils.install(from, to[, option])
FileUtils.mkdir_p(path)     # 可递归创建目录，如"foo/doo/hoo"
'$:'以数组形式保存Ruby中可用的库所在的目录
```

## Encoding

* 1,脚本的字符编码决定字面量字符串对象编码
* 2,内部编码指从外部获取数据在程序中如何处理的信息，外部编码指程序向外部输出时与编码相关的信息
* 魔法注释: `# encoding: utf-8`, 或emacs中`# -*- coding: utf-8 -*-` 或vim中`# vim:set fileencoding=utf-8:`
* 用`\u`创建的字符串一定是`utf-8`
* 编码不同的字符串无法连接，即使内容相同也被判断为不同对象
* 区分字符串和字节串, `ascii-8bit`将字符串转为字节串时会用到
* 使用`open-uri`库等工具获取网络文件时，若不知道编码也会默认`ascii-8bit`
* 正则表达式字面量的编码或与代码的编码一样，或与`Regexp.new(str)`的str编码一样, 正则编码若与字符串编码不同，会匹配错误
* `IO`对象都包含有外部编码与内部编码两种编码信息。外:作为输入输出对象的文件、控制台等的编码， 内:Ruby脚本中的编码
* 输出时编码作用：
  * 把字符串的编码转换为`IO`外部编码
  * `IO`外部编码未设定时不转换
  * 无法转换时抛出异常
* 输入时编码作用：
  * `IO`外部编码转换为字符串内部编码
  * 字符串没设定内部编码则把`IO`外部编码设定给字符串
  * `IO`外部编码没设定时使用`Encoding.default_external`
  * 无法转换时抛出异常

```ruby
str = '\u3042\u3044'
str.encode!("EUC-JP")
str.encoding    # 返回Encoding对象
Encoding.compatible?(str1, str2)    # 若字符串编码兼容，返回连接后的编码，不兼容返回nil
Encoding.default_external   # 返回默认外部编码
Encoding.default_internal   # 返回默认内部编码
Encoding.find(name)         # 返回编码名为name的Encoding对象，形似#<Encoding:UTF-8>
Encoding.list   # 以数组形式返回Ruby支持的编码对象列表
Encoding.name_list   # 以数组形式返回Ruby支持的编码名字符串列表
utf = Encoding.find("utf-8")
utf.name
utf.names   # 一个编码可能有多个名称
str = [127, 0, 0, 1].pack("C4") # "C4"表示4个8位不带符号的整数
str.encoding   #=> #<Encoding:ASCII-8BIT>
str = open("http://www.example.jp/").read
str.encoding  #=> #<Encoding:ASCII-8BIT>
str.force_encoding("utf-8")
str.valid_encoding?     # 检查编码是否正确
io.external_encoding
io.internal_encoding
io.set_encoding("external:internal")
```

## Time/Date

* `Time`表示时间，即年月日时分秒，时区, 以秒为单位计算
* `Date`表示日期，即年月日，以天为单位计算
* 时间可以比较, 基于总秒数.`to_i`比较大小


时间`Time`:

```ruby
Time.new Time.now
t.year t.month t.day t.hour t.min t.sec t.usec # 毫秒
t.to_i # 从1970年1月1日到目前的秒数
t.wday t.mday t.yday t.zone

Time.mktime(year[, month[, day[, hour[, min[, sec[, usec]]]]]])
t + 60*60   # 加的单位为秒，即3600秒，为1小时， 但两个Time对象不能相加
t.strftime(format)

# %A 星期名称 %a 星期缩写 %B 月份名称 %b 月份缩写 %c 日期与时间 %d 日 %H 24小时制 %I 12小时制
# %j 一年中的天 %M 分 %m 月份数字 %p 上午下午 %S 秒 %U 周(星期日开始)数字 %W 周(星期一开始)数字
# %X 时间 %x 日期 %Y 西历数字 %y 西历后两位 %Z 时区 %z 时区 %% '%'

t.to_s      #=> 相当t.strftime("%Y-%m-%d %H:%M:%S %z")
# 2015-01-07 12:11:59 +0800
t.rfc2822   # 生成符合电子邮件头部信息中的Date:字段格式的字符串, "%a, %d %b %Y %H:%M:%S %z", require 'time'
# Wed, 07 Jan 2015 12:11:59 +0800
t.iso8601   # 生成符合ISO 8601国际标准时间格式的字符串, require 'time'
# 2015-01-07T12:11:59+09:00
t.utc   # local time->UTC
t.localtime     # UTC->local time

t = Time.parse(str)     # require 'time'    str可以为以下格式
# Sat Mar 30 03:54:15 UTC 2013
# Sat, 30 Mar 2013 03:54:15 +0900
# 2013/03/30
# 2013/03/30 03:54:15
# H25.03.31
# S48.9.28
```

日期`Date`:

```ruby
d = Date.today
d.year  d.month  d.day  d.wday  d.mday  d.yday
d = Date.new(2013, 2, -1)   # '-1'表示月末，智能处理闰年
d >> n # 获取后n个月相同日期的Date对象
d << n # 获取前n个月相同日期的Date对象
d.strftime(format)  # 同Time对象，只是时间域为0
d = Date.parse(str)     # require 'date'
```

## Proc

* 使块对象化的类
* 带块的方法：1，`method(...) {|x| …}`; 2，`method(..., &blk)`, 两种传递块的方法等价
* `Proc`不只单纯地对象化块，同时保存定义块时的状态(闭包)
* 块变量的种类：
  * `:opt`  可省略变量,即默认参数,`a=3`
  * `:req`  必需变量,即普通参数,`a,b`
  * `:rest` 以`*args`形式表示的变量
  * `:key`  关键字参数形式表示的变量,`a:1`
  * `:keyrest`  以`**args`形式表示的变量
  * `:block`  块参数, `&blk`

```ruby
hello = Proc.new { |name| puts "hello, #{name}}
hello = proc { |name| puts "hello, #{name}}     # 创建, 参数数量可以不同
hello = lambda {|a, b, c| p [a, b, c]}  # call方法的参数与块变量的参数严格相同
hello.call("ruby")
hello["ruby"]
hello.yield("ruby")
hello.("ruby")
hello === "ruby"    # 调用
square = lambda {|x| return x ** 2}
square = lambda {|x| x ** 2}
square = ->(x) {x**2}   # lambda的简写
square = proc {|x| return x ** 2}   #lambda的return从lambda返回，而proc的return从定义proc的定义域返回
square = proc {|x| x ** 2}
%w(42, 39, 56).map{|i| i.to_i}  # 两种传递块的方法等价
%w(42, 39, 56).map(&:to_i)  # 以&形式传递参数，符号对象的.to_proc会自动调用, Symbol#to_proc
prc.arity   # 返回块变量个数， 块变量有|*args|时，返回负数
proc{|a, b, *c| nil}.arity      #=> "-3"
prc.parameters      # 返回关于块变量的详细信息， "[种类， 变量名]"
prc.lambda?     # 判断prc是否通过lambda定义的方法
prc.source_location     # 返回定义prc的程序代码位置，'[代码文件名, 行编号]'
```

## 预定义变量常量

变量:

```ruby
$_              # gets方法最后读取的字符串
$&              # 最后一次模式匹配后得到的字符串
$~              # 最后一次模式匹配相关的信息
$`              # 最后一次模式匹配中匹配部分前的字符串
$'              # 最后一次模式匹配中匹配部分后的字符串
$+              # 最后一次模式匹配中最后一个()对应的字符串
$1,$2,...       # 最后一次模式匹配中()匹配的字符串，第n个()对应$n
$?              # 最后执行完毕的子进程的状态
$!              # 最后发生的异常信息
$@              # 最后发生的异常的相关位置信息
$SAFE           # 安全级别
$/              # 输入数据的分隔符，默认为'\n'
$\              # 输出数据的分隔符，默认为nil
$,              # Array#join的默认分隔字符串，默认为nil
$               # String#split的默认分割字符串，默认为nil
$<              # ARGF的别名
$>              # print,puts,p等的默认输出位置，默认为$stdout
$0              # $PROGRAM_NAME的别名
$*              # ARGV的别名
$$              # 当前执行中的Ruby的进程ID
$:              # $LOAD_PATH的别名
$"              # $LOADED_FEATURES的别名
$DEBUG          # 指定debug模式的标识
$FILENAME       # ARGF当前在读取的文件名
$LOAD_PATH      # 执行require读取文件时搜索的目录名数组
$stdin
$stdout
$stderr
$VERBOSE        # 指定冗长模式的标识，默认为nil
$PROGRAM_NAME   # 当前执行中的Ruby脚本的别名
$LOADED_FEATURES# require读取的类库名一览表
```

常量:

```ruby
ARGF            # 参数，或者从标准输入得到的虚拟文件对象
ARGV            # 命令行参数数组
DATA            # 访问__END__以后数据的文件对象
ENV             # 环境变量
RUBY_COPYRIGHT  # 版权信息
RUBY_DESCRIPTION# ruby -v显示的版权信息
RUBY_ENGINE     # ruby的处理引擎
RUBY_PATCHLEVEL # Ruby的补丁级别
RUBY_PLATFORM   # 运行环境信息
RUBY_RELEASE_DATE
RUBY_VERSION
STDIN
STDOUT
STDERR
```

伪变量:

```ruby
self            # 默认的接收者
nil, true, false
__FILE__        # 执行中的Ruby脚本的文件名
__LINE__        # 执行中的Ruby脚本的行编号
__ENCODING__    # 脚本的编码
```

环境变量:

```ruby
RUBYLIB         # 追加到时预定义变量 $LOAD_PATH中的目录名，目录间以':'分隔
RUBYOPT         # 启动Ruby时的默认选项
RUBYPATH        # -S选项指定的、解析器启动时脚本的搜索路径
PATH
HOME            # Dir.chdir方法的默认移动位置
LOGDIR          # HOME没有时的Dir.chdir方法的默认移动位置
LC_ALL, LC_CTYPE, LANG  #决定默认编码的本地信息（平台依赖）
RUBYSHELL, COMSPEC  # 执行外部命令时，shell需要使用的解析器路径（平台依赖）
```
