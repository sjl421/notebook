# Python Ruby Scala

## 控制

## 类

`Python`:
```python
class A(B):  #创建A类, 继承B类
  def __init__(self, name, age):  #构造函数, 初始化参数
    self.name = name
    self.age = age
  def hello(self):  #普通方法, self表示对象实例
    print("hello, %s" % self.name)
  def __str__(self):  #对象的字串表示
    return "%s's age is %d" % (self.name, self.age)  #方法需要明确的return
a = A("enali", 24)  #实例化对象
a.hello  #=> "hello, enali"
str(a)  #=> "enali's age is 24"
```

`Ruby`:
```ruby
class A < B  #类A继承B
  def initialize(name, age)  #构造函数
    @name = name  #实例变量,可跨方法使用
    @age = age
  end
  def hello  #普通方法
    puts "hello, #{@name}"
  end
  def to_s  #转换为字串,即字串表示
    "#{@name}'s age is #{@age}"
  end
end
a = A.new("enali", 24)  #实例化对象
a.hello  #=> "hello, enali"
a.to_s  #=> "enali's age is 24"
```

`Scala`:
```scala
class A(name: String, age: Int) extends B {  //继承B类,初始参数为name, age
  def hello = println(s"hello, $name")
  override def toString = s"$name's age is $age"
}
val a = new A("enali", 24)  //实例化对象
a.hello  //=> "hello, enali"
a.toString  //=> "enali's age is 24"
```
