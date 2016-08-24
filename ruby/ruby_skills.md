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

