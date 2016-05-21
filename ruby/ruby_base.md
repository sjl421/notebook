# Ruby基础

>人生苦短, 我用 Ruby

## 安装Ruby

有多种方式将这最优秀的脚本语言安装到你的系统上. 

在`Windows`上, 在官网[Ruby](www.ruby-lang.org)上下载二进制包直接安装. 在`Linux`下推荐使用`rvm`这个Ruby的多版本管理工具, 类似还有`rbenv`.

```rb
curl -sSL https://get.rvm.io | bash -s stable 
```

### rvm

熟悉几个`rvm`子命令:

* `rvm list [known]`: 列出已安装版本, `known`列出已支持的版本
* `rvm install [ruby] [x.y.z]`: 安装指定版本
* `rvm rvm implode`: 移除`rvm`
* `rvm get stable/head`: 获取最新稳定版或开发版
* `rvm docs generate [ri | rdoc | gem]`: 生成文档, 可指定格式

### gem

`gem`是`Ruby`的包管理工具, 能非常方便的安装第三方库和工具. 

熟悉`gem`的几个子命令:

* `gem search pkgname`: 搜索包 
* `gem install pkgname`: 安装包
* `gem update [pkgname]`: 更新包, 默认更新所有包

默认的`gem`官方软件源访问不了. 现在主要使用的国内源是淘宝的.

```rb
gem sources -r https://rubygems.org/    #移除 
gem sources -a https://ruby.taobao.org/ #添加 
```

如果你使用`bundle`来管理你的第三方依赖, 可能要改变你的`Gemfile`首行`source 'https://ruby.taobao.org/'`.

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

数字: `Ruby`中有`Integer`, `Complex`, `Rational`, `Fixnum`, `Float`等有关数字的类型, 都继承自`Numeric`类.

```rb
52, 0x32, 064, 0b110100  #十/十六/八/二进制
120.5, 1.205e2           #浮点, 科学计数
123456789.class          #=>Fixnum, 数超出范围, 自动转为Bignum
123456789123456789123456789.class  #=>Bignum
Complex(1, 2), 1+2i      #=>(1+2i), 复数
0.5r, Rational(1, 2)     #=>(1/2), 分数
```

类型转换:

```rb
4.to_s           #=> "4"
32.to_s(2/8/16)  #=> 整数转字串, 转二/八/十六进制
"32abc".to_i     #=> 字串转整数, 32
32.to_f          #=> 32.0
0.5.to_r         #=> 浮点转分数(1/2)
3.to_c           #=> 转复数3+0i
"abc".to_sym     #=> :abc, 字串转符号
:abc.to_s        #=> "abc", 符号转字串
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
# Proc对象
add_two = proc {|x| x+2}  #定义
add_two = Proc.new {|x| x+2}
add_two = lambda {|x| x+2}
add_two = -> (x) {x+2}
add_two.(4), add_two[4], add_two.call(4)  #调用
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
File.open(path) do |file|
  file.each_line do |line|
    ...
  end
end
# 等价
file = File.open(path)
begin
  file.each_line do |line|
    ...
  end
ensure
  file.close    #文件一定会关闭，无论是否异常
end
# 3,替换部分算法
ary.sort {|a, b| a.size <=> b.size} # 对每两个元素基于.size比较排序
ary.sort_by {|a| a.size}    # 对每个元素.size后再排序
# 将块封装为对象
hello = Proc.new {|name| puts "hello, #{name}"}
hello.call("Ruby")
```

## 类型

`Ruby`有一致的类体系.

数字`Numeric`:
* `Numeric`
  - `Integer`
    + `Fixnum`
    + `Bignum`
  - `Float`
  - `Rational`
  - `Complex`

```rb
52 0d52, 0x32, 064 0o64, 0b110100, 120.5, 1.205e-2  #进制, 浮点, 科学数
123456789123456789123456789.class  #=>数超出范围, 自动转为Bignum
1+2i, Complex(1, 2)      #=>(1+2i), 复数
0.5r, Rational(1, 2)     #=>(1/2), 分数
1+2, 1-2, 1*2, 1/2, 1%2, 1**2  #=>加减乘除余冥
11.2.div 2.3   #=> 4, 浮点或整数, 结果都为整, 不同于/
11.quo 7       #=> 11/7, 整数时返回Rational,有Float时返回Float
11.modulo 7    #=> 4, 商向左舍入,[x]<=x
11.divmod 7    #=> [1, 4]
11.remainder 7 #=> (返回结果符号与x一致，商向原点舍入)
1/0, 1.0/0, 0/0.0        #=> 错误, Infinity, NaN
a = 0.1 + 0.2; b = 0.3; a != b  #=> 有些浮点无法精确存储
```

字串`String`:

```rb
s = "a b c"
s = ?a         #=> "a", 单字符串
s = %{a b c}   #=> "a b c"
s = %Q{a b c}  #=> "a b c"
s = %q{a b c}  #=> 'a b c', 单绰号不转义特殊字符, 不进行变量替换
```

## 数组Array

创建数组:

```rb
["a", "b", "c", "d"], %w(a, b, c, d)   #=> ["a", "b", "c", "d"]
%i(a b c d)      #=> [:a, :b, :c, :d]
{a:1, b:2}.to_a        #=> [[:a, 1], [:b, 2]]
"hello:world".split(':')  #=> ["hello", "world"], 默认为空格分隔
Array.new(3, 4)  #=>[4, 4, 4]
Array.new(3) {|i| i+1}  #=> [1, 2, 3]
Array.new(3, [0, 0, 0]) #=> 元素指向同一对象[0,0,0], 一变则变
Array.new(3) {[0, 0, 0]}  #=> 单独的对象
```

```rb
ary << "e"  #=> ["a", "b", "c", "d", "e"]
ary[6]  #=> nil  (越界索引元素)
ary[6] = "g"  #=> ["a", "b", "c", "d", "e", nil, "g"]  (越界添加对象)
ary.size  #=> 7  (ary.length是方法别名)
```

哈希`Hash`:

```rb
addr = {:name => "lzp", :pinyin => "lizp", :postal => "1245"}
addr = {name: "lzp", pinyin: "lizp", postal: "1234"}  #=> 以符号为键
```

范围`Range`:

```rb
r = 0...5  #=>不包含2
r = 0..5   #=>包含2
```

正则`Regexp`: 

```rb
reg = /abc/
reg = %r(abc)    #=> /abc/, 正则对象
/ll/ =~ "hello"  #=> 2  (返回匹配的位置)
/ll/i =~ "heLLo"  #=> 2  (i表示不区分大小写)
/ho/ !~ "hello"   #不匹配
```


文件:

```rb
file = File.open(fname)
text = file.read  # 一次读入所有内容
file.close
File.open(path) do |f|  
  f.each_line do |l|
    ...
  end
end
```