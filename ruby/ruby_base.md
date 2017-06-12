# Ruby基础

>人生苦短, 我用 Ruby

## 初步

关于`Ruby`, 你需要先知道以下几点:
* 自1.9后, 源码编译为字节码运行在虚拟机上
* 源码中, 行注释`#`, 块注释`=begin/=end`
* 源码中`import 'test'`引入第三方库
* 查找帮助: 类方法`ri Array::new`, 实例方法`ri String#split`
* 调用方法时, 在不引起歧义前提下, `()`可省略, 空格分隔参数
* 变量命名规范:
    * 全局变量(前缀'): `$var`
    * 实例变量(前缀'@'): `@var`
    * 类变量(前缀'@@'): `@@var`
    * 常量(首字母大写): `Var`
    * 局部变量(全小写): `var`
    * 变量名和方法名(下划线): `two_var`
    * 类名和模块名(驼峰): `TwoVar`

对象: `Ruby`中一切皆对象.

```rb
obj.object_id  #=>xxxxxxx, 获取唯一标识符
"abc".class    #=>String
String.class   #=>Class
String.superclass  #=>Object, 超类
String.ancestors  #=>祖先链, [String, Comparable, Object, Kernel, BasicObject]
"abc".instance_of?(String)  #=>实例判断
```

赋值: `Ruby`在赋值`=`的两边进行匹配

```rb
a, b, c = 1, 2, 3
a, b, c = 1, 2, 3, 4            #=> 忽略4
a, b, c = 1, 2                  #=> c赋为nil
a, b, *c = 1, 2, 3, 4           #=> c = [3, 4]
a, b = [1, 2]
a, = [1, 2]                     #=> a = 1
a, (b1, b2), c = [1, [2, 3], 4] #=> b1 = 2, b2 = 3
a, b, c, d = [1, [2, 3], 4]     #=> b = [2, 3], d = nil
```

相等判断: `Ruby`有四种方式判断对象相等`==`, `equal?`, `eql?`, `===`. 

```rb
obj1 == obj2     # 可能被子类覆写,实现相应的特殊意义
obj1.equal? obj2 # 不应该被子类覆写,判断是否是相同的对象
obj1.eql? obj2   # 用于Hash的键同一性判断
1.0 == 1         #=> true, 值相等
1.0.equal? 1     #=> false, 不是一个对象
other = obj.dup  # dup复制对象
obj == other     # => true
obj.equal? other # => false, 复制的对象与原对象不是一个
```

输出: `Ruby`主要三个函数进行输出, `print`, `puts`, `p`

```rb
print "hello, world"    #=> 不换行, 返回nil
puts "hello, world"     #=> 默认换行, 返回nil
puts "hello, #{name}"   #=> 变量替换
puts "hello, ", "world" #=> 每个参数都换行
puts [1, 2, 3, 4]       #=> 每个元素一行
p "hello, world"        #=> 同puts, 但返回输出对象
p "hello, world\n"      #=> 不转义特殊字符
```


条件表达式: 最常见的`if`和多分支的`case`.

```rb
if condition       #表达式无需()
    expr
elsif condition 
    expr
else 
    expr
end

expr if condition  #修饰符

case v_expr
when v1, v2        #使用===检测相等
    expr  
else               #默认情况
    expr  
end
```

循环: 条件循环`while`, `until`和无条件循环`loop`. 良好的风格并不推荐使用这些循环, 而使用`each`等迭代.

```rb
for var in obj
    expr
end

while/until condition
    expr
end
expr while/until condition  #修饰符

loop do
    if condition break/next/redo
end
```

迭代: `Ruby`中每个多元素对象都实现有`each`方法进行元素迭代

```rb
num.times do |i|
  expr
end
n1.upto/downto(n2) do |i|
  expr
end
n1.stepto(n2, step) do |i|
  expr
end
array.each do |v|     # 数组
  expr
end
hash.each do |k, v|   # 哈希
  expr
end
file.each_line do |line|  # 文件
  expr
end
```


方法: `Ruby`的方法分为实例方法, 类方法和单件方法. 所有方法有统一的寻找链.

```rb
# def方法
def add_two(x) x+2 end
add_two(4)  #=> 6
# 单件方法,增强对象
s = "abc"
def s.hello "hello, #{self}" end
s.hello  #=> "hello, abc"
#普通参,默认参,不定参,不定参,关键参
n1, n2=3, *n3, **n4, n5:7  
# 带块方法
def hello
  yield var
end
def hello(args, &block)  #显式
  block.call(var)
end
```

类: `Ruby`的所有类都是`Class`类的对象, 包括`Class`类本身. 之前可能区分为类方法和单件方法, 但本质上是没有区别的. 类方法是类作为对象的单件方法. 它们有统一的定义形式, `def obj.method`, 只是此处的`obj`是类时为类方法, 是对象时为单件方法.

另一个有趣处, 在类定义的内部, `Ruby`会追踪`self`. 在实例方法内, `self`指代接收者. 在实例方法外, `self`指代类本身.

```rb
class D < String        #类定义, 继承
  def initialize(name)  #类初始化
    @name = name        #默认实例变量对外不可见
  end
  def name=(value)      #属性设置
    @name = value
  end
  def name              #属性获取
    @name
  end
  def to_s              #覆写
    self.name.to_s      #self为接收者
  end
  def self.hello "hello" end  #类方法, self为本身
  class << self         # 此处self为类本身
    def world "world" end     #类方法
  end
end
class << obj            #"<<"打开对象的单件类领域
  def abc; end          #单件方法
end
def obj.abc; end        #对象的单件方法
obj.extend(MyModule)    #将模块方法变为对象单件方法
```

异常: 

```rb
begin
  expr
rescue Exception => var  #捕获异常
  var.class              #异常的类
  var.message            #异常的消息
  var.backtrace          #异常的栈迹
  retry                  #重执行begin下面的语句
ensure
  expr                   #无论是否捕获异常一定执行
end
var = exp1 rescue exp2   #exp1无异常,则赋值给var, 否则赋值exp2
MyError = Class.new(StandardError)  #创建自己的异常类
raise MyError, msg       #指定异常抛出
raise msg                #默认RuntimeError异常
raise
```

块`block`: 所谓块即表达式的组合. `Ruby`有两种方式表示块, `do/end`和`{}`. 编码风格指导: `do/end`用于多行的块, 而`{}`用于一行的块. 块通常用于循环, 部分常规处理和替换部分算法.

```rb
# 3,替换部分算法
ary.sort {|a, b| a.size <=> b.size} # 对每两个元素基于.size比较排序
ary.sort_by {|a| a.size}    # 对每个元素.size后再排序
# 将块封装为对象
hello = Proc.new {|name| puts "hello, #{name}"}
hello.call("Ruby")
```
