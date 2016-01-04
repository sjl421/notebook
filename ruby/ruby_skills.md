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