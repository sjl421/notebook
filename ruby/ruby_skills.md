# ruby技巧

## 写入二进制数据：

``` ruby
ary = ["ef", "4a", "3b"]
File.open("test.bin", "wb") do |f|
    f.write ary.map{|b| b.hex}.pack("C*")
end
```

具体用法可查询: `ri Array#pack`

注意， 如果`ary`元素为`aaaa`两具字节， 则`.pack("S*")`

## 结构体

用于快速声明一种纯数据存储结构：

```ruby
Card = Struct.new(:name, :addr)
c = Card.new("lzp", "123")
```

其一定程序相当于：
```ruby
class Card
    attr_accessor :name, :addr
end
```

但结构体不提供方法， 同结构体的元素数据相等的实例相等。 同时实现序列化和反序列化。

有解释器内置的`Struct`和标准库的`OpenStruct`。 后者随时添加属性，而前者必须明确声明字段。但前者性能高。

## 为map添加参数方法

```ruby
class Symbol
  def call(*args, &block)
    ->(caller, *rest) { caller.send(self, *rest, *args, &block) }
  end
end
a = [1,3,5,7,9]
a.map {|x| x+2 }
a.map(&:+.(2))
d = [{a:1, b:2}, {a:3, b:3}]
d.map {|i| i[:a]}
d.map(&:[].(:a))
```
## &.安全调用运算子

在`nil`上调用方法`nil.length`, 会提示`NoMethodError`, 无方法错误.

在`nil`上调用方法`nil&.length`, 会返回`nil`.

在多条件的`if`判断非常有用:

```ruby
u = User.find(id)  # 查找指定id的用户
# 在方法链调用中, 任何环节返回Nil都会出错
if u && u.profile && u.profile.thumbnails && u.profile.thumbnails.large
  ...
end
```

注意其中的多条件:

```ruby
if u&.profile&.thumbnails&.large
  ...
end
```
## quoted-printable数据

在个人电子名片`vCard`格式中, 可能出现这种类型数据:
```ruby
s = "=E6=A8=8A=E4=B8=80=E5=BA=B7"
s.unpack('M')[0].force_encoding('utf-8')  #unpack的对象是字串, 返回数组
```

如果你想保存信息为`vCard`格式, 可能需要转化为`quoted-printable`形式:
```ruby
s = "中国你好"
[s.force_encoding("ascii-8bit")].pack("M")  #pack的对象是数组, 返回字串
```

## 关于231, "e7", "\xe7"

整数与不同进制字串间的转化: 在内存中的字节已经变化.
```ruby
231.to_s(16)  #=> "e7"
231.to_s(2)   #=> "11100111"
"11100111".to_i(2)  #=> 231
"e7".to_i(16)  #=> 231
"e7".hex  #=> 231
```

整数与字串间的转化: 在内存中字节不变.
```ruby
231.chr  #=> "\xe7" , 虽然是字串, 但内存中存储的仍然是231的字节码
"\xe7".bytes[0]  #=> 231 , 对字串求字节数组, 并取第0个元素
97.chr  #=> "a"  #=> 求整数的ascii符号, 虽然表现为字符a, 但存储的仍然是97
"a".ord  #=> 97
"\x61".ord  #=> 97
"a".bytes[0]  #=> 97 , 通用方法
```

255以内的数字, 都可能用`chr`来求不变内存的字符(超出127的, 无对应字符, 显示为"\xAA"
的形式). 有对应的, 127以内的, 无论是直接的符号, 如`"a"`, 还是`"\x61"`, 都可能通过
`ord`来求数字, 其实是`ascii`的序号. 超过127的, 不能执行`ord`.

这样也不完全正确. 如果只是用数字表示某个字符在某种编码集中的序号, 则`chr(encoding_name)`
理论上总是试用的. 比如, 23451已经完全超出`ascii`的范围了, 可以使用`chr(Encoding::UTF_8)`
来看对应`utf-8`的"完"字符. 由于`utf-8`几乎表示了所有字符, 则很大范围的数字都可以
找到对应的字符.

更为通用的, 求任意符号的内存表示:
```ruby
"王".force_encoding("ascii-8bit")  #=> "\xE7\x8E\x8B"
"\xE7\x8E\x8B".bytes  #=> [231, 142, 139]
"王".bytes  #=> [231, 142, 139]
[231, 142, 139].map{|i| i.chr}.join  #=> "\xE7\x8E\x8B"
"\xE7\x8E\x8B".force_encoding("utf-8")  #=> "王"
```
