# Python Ruby Scala

## 字符串

长度: `py: len(s)`, `rb, scala, js: s.length`

子串: `py: s[0:n]`, `rb: s[0..n], s[0...n], s.slice(0,n)`, `scala: s.slice(0,n)`, `js: s.slice(0,n)`

去除空白: `py, rb: s.strip(), s.rstrip(), s.lstrip()`, `rb: `

## 控制

条件语句: 

`python`: `a == b, a is None, a is not None, it in [a,b], cond1 and cond2`
```python
if cond1:
  stat1
elif cond2:
  stat2
else
  stat3
```

`ruby`:
```ruby
if cond
  stat1
elsif cond 
  stat2
else 
  stat3
end

stat if cond  #修饰符
stat unless cond

case v_expr
when v1, v2        #使用===检测相等
  stat
else               #默认情况
  stat
end
```

循环语句:

`python`:
```python
while cond:
  stat
```

`ruby`: 不推荐使用`while/for/until/loop`这些循环
```ruby
ary.each {|it| stat}  # 遍历列表
n1.times {|n| stat}  # 0...n1
n1.upto/downto/stepto(n2 [,step]) {|n| stat}
hash.each {|k,v| stat}
file.each_line {|l| stat}
```

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
