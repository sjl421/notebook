# ruby

## install

`rvm`用于管理多个Ruby版本.

~~~sh
crul -sSL https://get.rvm.io | bash -s stable --ruby --rails
~~~

## basic

* Ruby2.0默认文本编码utf-8.
* Ruby代码行注释:`#`, 块注释:`=begin/=end`
* Ruby帮助:`ri Array::new`类方法, `ri String#split`实例方法
* Ruby库引用:`require 'data'`
* Ruby命名规范:
  * 全局变量: `$var`
  * 实例变量:`@var`
  * 类变量:`@@var`
  * 常量:`Var`
  * 局部变量:`var`
  * 变量名和方法名(下划线):`my_method`
  * 类名模块名常量名(驼峰):`MyClass`
* Ruby中一切皆对象,类也是对象.所有对象都有唯一标识(`object_id`和值

## Ruby的赋值

Ruby的赋值极其灵活:

~~~ruby
a, b, c = 1, 2, 3
a, b, c = 1, 2, 3, 4            #=> 4 is ignore
a, b, c = 1, 2                  #=> c = nil
a, b, *c = 1, 2, 3, 4           #=> c = [3, 4]
a, b = [1, 2]                   #=> a = 1, b = 2
a, b = b, a
a, = [1, 2]                     #=> a = 1
a, (b1, b2), c = [1, [2, 3], 4] #=> b1 = 2, b2 = 3
a, b, c, d = [1, [2, 3], 4]     #=> d = nil
~~~

## Ruby的相等判断

Ruby有四种方式判断对象相等: `==`, `equal?`, `eql?`, `===`.
其中`===`判断相等的方式更广泛.

~~~ruby
obj1 == obj2     # 可能被子类覆写,实现相应的特殊意义
obj1.equal? obj2 # 不应该被子类覆写,判断是否是相同的对象
obj1.eql? obj2   # 用于Hash的键同一性判断
1.0 == 1         # => true
1.0.equal? 1     # => false
other = obj.dup
obj == other     # => true
obj.equal? other # => false
~~~

## Ruby输出

Ruby有`print`,`puts`, `p`的三个主要的输出方法.

~~~ ruby
print "hello, world"    #=> 不换行, 返回nil
puts "hello, world"     #=> 默认换行, 返回nil
puts "hello, #{name}"   #=> "hello, lzp"  (name = "lzp")
puts "hello, ", "world" #=> 每个参数都换行
p "hello, world"        #=> 返回输出对象
p "hello, world\n"      #=> 不转义
pp "[{a:1, b:2}]"       #=> 更好的输出嵌套内容
~~~

## Ruby的数字

整体上,Ruby有整数Ingeger,Complex,Rational,Fixnum,Float等数类型,
都继承自`Numeric`.

~~~ ruby
100 / 7       #=> 14
100.0 / 7     #=> 14.28571428514286
-1 / 7        #=> -1
1 / -7        #=> -1
1 % -7        #=> -6
-1 % 7        #=> 6  (余数 = 被除数 - 除数 * 商
"100".to_i    #=> 100  (字符串转整数)
"100abc".to_i #=> 100
~~~

## Ruby的控制结构

顺序,条件,循环,异常.

~~~ ruby
# if条件
if condition
  statements1
elsif condition
  statements2
else
  statements3
end
statement if condition  #if修饰符
# case条件
case value_expression  #case用"==="检测相等,包括与此正则匹配,对象从属类等
when val1, val2
  statements1
else  #值都不等时执行
  statements2
end
# for循环
for var in obj
  statements1
end
# while循环
while condition
  statements1
end
# until循环
until condition
  statements1
end
# loop循环
loop do
  break
  next
  redo
end
~~~

## Ruby的each迭代

請尽可能使用Ruby的each语法.

~~~ ruby
num.times do |i|
  ...
end
n1.upto/downto(n2) do |i|
  ...
end
n1.stepto(n2, step) do |i|
  ...
end
array.each do |v|
  ...
end
hash.each do |k, v|
  ...
end
file.each_line do |line|
  ...
end
~~~

## Ruby的数组

~~~ ruby
ary = %w(a, b, c, d)  #=> ["a", "b", "c", "d"]
ary = ["a", "b", "c", "d"]
ary << "e"  #=> ["a", "b", "c", "d", "e"]
ary[6]  #=> nil  (越界索引元素)
ary[6] = "g"  #=> ["a", "b", "c", "d", "e", nil, "g"]  (越界添加对象)
ary.size  #=> 7  (ary.length是方法别名)
~~~

## Ruby的Hash

~~~ ruby
addr = {:name => "lzp", :pinyin => "lizp", :postal => "1245"}
addr = {name: "lzp", pinyin: "lizp", postal: "1234"}  #=> (Hash的键为符号时可简写)
~~~

## Ruby的string和symbol

~~~ ruby
:north.to_s  #=> "north"
"north".to_sym  #=> :north
~~~

## Ruby的regexp

~~~ ruby
/ll/ =~ "hello"  #=> 2  (返回匹配的位置)
/ll/i =~ "heLLo"  #=> 2  (i表示不区分大小写)
~~~

## Ruby的file

~~~ ruby
file = File.open(fname)
text = file.read
file.close
File.open(path) do |f|
  f.each_line do |l|
    ...
  end
end
~~~

## Ruby的method

实例方法`instance method`,类方法`class method`,单件方法`singleton method`.

~~~ ruby
# Proc对象
add_two = proc {|x| x+2}
add_two = Proc.new {|x| x+2}
add_two = lambda {|x| x+2}
add_two = -> (x) {x+2}
add_two.(4)  #=> 6
add_two[4]
add_two.call(4)
# def方法
def add_two(x); x+2; end
add_two(4)  #=> 6
# 单件方法,增强对象
s = "abc"
def s.hello; "hello, #{self}"; end
s.hello  #=> "hello, abc"
# 方法参数传递
def hello(n1, n2=3, *n3, **n4, n5:7)  #普通参,默认参,不定参,不定参,关键参
  ...
end
# 带块方法
def hello
  yield(var)
end
def hello(args, &block)
  block.call(var)
end
~~~

## Ruby的class

类是Class类的对象

~~~ ruby
obj.instance_of?(MyClass)  #从属判断
#类定义
class D < String
  def initialize(name)
    @name = name
  end
  def name=(value)  #属性设置
    @name = value
  end
  def name  #属性获取
    @name
  end
  def to_s  #覆写
    @name.to_s
  end
end
# 类方法
class << Hello  #"<<"打开对象的单件类领域
  def abc; end
end
def Hello.abc; end
class Hello
  def self.abc; end  #self指类本身
end
class Hello
  class << self
    def abc; end
  end
end
str = "ruby"
class << str
def abc; end
end
module MyModule
def abc; end
end
str.extend(MyModule)
~~~

## Ruby的异常

~~~ ruby
begin
  statements
rescue Exception => var
  var.class
  var.message
  var.backtrace
  retry  # 重执行begin下面的语句
ensure
  statements
end
var = exp1 rescue exp2  # exp1无异常,则赋值给var, 否则赋值exp2
MyError = Class.new(StandardError)
raise MyError, msg
raise msg  # 默认RuntimeError异常
raise
~~~
