# Ruby的奇巧淫技

## 1， 嵌套`def`

```ruby
class A
  def b
    def c; 'hello, c'; end
    def self.d; 'hello, d'; end
  end
end

a = A.new
A.instance_methods(false)  #=>[:b]
a.b
A.instance_methods(false)  #=>[:b, :c]
a.singleton_methods(false)  #=>[:d]
```

解析:
* 在执行`a.b`方法时，类`A`只有`:b`一个实例方法， 执行后，才有`:c`实例方法，即嵌套`def`定义方法时，`Ruby`会追踪类的引用
* 在`def`内的`self`是对类的实例对象的引用，在此处为`a`， 因此，执行`a.b`后，对象`a`拥有了一个单件方法`:d`
* `false`参数，意思是不包括继承来的方法

## 2，`main`对象

```ruby
# test.rb
self  #=>main
self.class  #=>Object

def a; 'a'; end
self.a  #=>private method, wrong
Object.private_methods.grep(/^a$/)  #=>[:a]
'abc'.instance_eval {a}  #=>'a'

def self.b; 'b'; end
b.singleton_methods.grep(/^b$/)  #=>[:b]

def h1
  def h2
    'h2'
  end
end
'abc'.h2  #=>NoMethodError
h1
'abc'.h2  #=>'h2'
Object.methods.grep(/^h\d/)  #=>[:h2]
```

解析:
* 一个文件的全局`self`是`main`对象，其类是`Object`，虽然大多数情况下，定义方法然后执行会表现的和过程式语言相似，但本质上完全不同
* 在文件中定义的方法会自动成为`Object`类的私有实例方法，所谓`private`是指方法调用时不能明确指定接收者，因此不得不通过`instance_eval`来调用，即任意`Object`及其子类的对象都可以在内部使用在全局文件中定义的方法，此处即`:a`
* 奇怪的是当在全局方法（请允许我这么称呼）的嵌套中定义的方法会成为`Object`类的实例方法，可在任意对象中调用。考虑到第一个问题中关于`Ruby`会追踪类引用这件事，当执行`:h1`方法时，方法内部定义的方法会成为当前类的方法，且不是私有的

## 3，`private method`

```ruby
class A
  def a1
    def a2; 'a2'; end
  end
  private :a1
end

a = A.new
a.instance_eval { a1 }
a.a2  #=>'a2'

def b1
  private
  def b2; 'b2'; end
end
b1
'abc'.b2  #=>private method, error
```

解析:
* `instance_eval`可以在对象内部执行代码，因为`:a1`是私有方法，不得不这样做
* 私有方法内部嵌套定义的方法会自己成为公有方法。事实上，在`:a1`执行前，是不存在`:a2`方法，也就无所谓是公有还是私有。这也解释了上问题的最后一段话
* 如果想避免如此，在想成为私有方法的作用域内都加上`private`

## 4， 单件方法继承

```ruby
class A
  def self.a; 'a'; end
end
class D < A; end
D.a  #=>'a'
```

解析:
* 方法的查找有个祖先链， 单件方法执行时也要经过方法查找再执行。如对`obj.method`执行，此处的`method`可能是普通的方法，也可能是单件方法。

## 5， `define_method`

```ruby
class A
  puts self, self.class  #=>A, Class
  define_method :a { puts self, self.class }  #=>#<A:0x..>, A
  def self.d
    puts self, self.class  #=>A, Class
    define_method(:e) {puts self, self.class}  #=>#<A:0x..>, A
  end
  class << self
    puts self, self.class  #=>#A, Class
    define_method :b { puts self, self.class }  #=>A, Class
  end
  def f
    puts self, self.class  #=>#<A:0x..>, A
    #...此处不能define_method
  end
end
#全局文件中
define_method(:c) { puts self, self.class }
c  #=>main, Object
self.c  #=>main, Object
'abc'.c  #=>'abc', String

Class.new.private_methods.grep(/^define_method$/)  #=>[:define_method]
Class.new.new.private_methods.grep(/^define_method$/)  #=>[]
self.private_methods.grep(/^define_method$/)  #=>[:define_method]
Object.new.private_methods.grep(/^define_method$/)  #=>[]
```

解析:
* 初步理解为，在类作用域内，`define_method`定义的方法会成为类的实例方法，而在类的单件类作用域内，其定义的方法会成为类的类方法。注意`define_method`是私有方法。
* 在全局文件执行`define_method`会定义`Object`类的实例方法，而使用`def`不会有这样的效果，但却等同嵌套`def`中的效果。考虑到设计一致性的问题，可能只有最外层的`def`定义方法时默认有`private`的效果，除此外，方法定义总会成为该作用域所属类的方法。在此指出，若作用域是单件作用域，其所属类为单件类
* 最后四行代码说明一个问题，即只有类有`define_method`这个私有方法，可以在`self`为类时使用`define_method`定义方法，而类的实例对象没有此私有方法，因此不能在`self`为类的实例化对象时使用此办法定义方法。唯一的问题是，在全局文件中，`self`为`main`对象，确仍然可以`define_method`。`self`是`Object`类的对象，而`Object`类的对象是没有`define_method`私有方法的，但`self`作为`main`对象却有。

## 6， `self`

```ruby
#1 class
class A
  puts self, self.class  #=>A, Class
  def a  #实例方法
    puts self, self.class  #=>#<A:0x..>, A
    def a_1; puts self, self.class; end  #=>#<A:0x...>, A  #实例方法
    def self.a_2; puts self, self.class; end  #=>#<A:0x...>, A  #实例的单件方法
  end
  def self.d  #类的单件方法方法
    puts self, self.class  #=>A, Class
    def e; puts self, self.class; end  #=>#<A:0x...>, A  #实例方法
  end
  define_method(:b) do  #实例方法
    puts self, self.class  #=>#<A:0x...>, A
  end
  class << self
    puts self, self.class  #=>#<Class:A>, Class
    def c; puts self, self.class; end  #=>A, Class  #类的单件方法
  end
end

#2
proc {puts self, self.class}.call  #=>main, Object
'abc'.instance_eval { puts self, self.class }  #=>abc, String
String.class_eval { puts self, self.class }  #=>String, Class

#3
class A
  def a(name)
    puts self, self.class
    yield name
  end
end
A.new.a('lzp') {|name| puts self, self.class}  #=>main, Object

#4
module B
  puts self, self.class  #=>B, Class
  def b; puts self, self.class; end
end    
```

解析:
* 单件方法内，`self`都是对象（可能是类本身也可能是类的实例）， 实例方法内，`self`都是类的实例
* 当前域内，`def self.method`定义当前域`self`的单件方法，因此不改变`self`值。当然域的`self`是类（类本身或类的单件类），则`def`定义实例方法（类实例方法，或单件类的实例方法即类单件方法），当前`self`是实例的话，Ruby会追踪类引用 ，`def`会定义实例的类的实例方法。一句话，`def`只给类定义方法
* 重点是：当`class`打开类时，当前域的`self`为类本身，当`class << self`时，打开类单件类的域，即其中`self`为单件类
* `block`内的`self`即当前上下文中的`self`，即块不改变`self`，块会继承当前上下文的绑定，即闭包。而方法与块的关系真是只简单是传参给块，块返值给方法
* 实事上，方法`:b`并未能执行，因此输出的`self`取决于上下文。当类包含模块时，`self`就是类的实例，当类的单件类包含模块时，`self`就是类本身。
* 即便如此，也有例外。对`instance_eval`和`class_eval`这样的特殊方法，块内的`self`会变成调用方法的对象。其实，就方法名就能知道

## 7，变量和常量

```ruby
#1 局部变量
a = 2
proc { a = 3 }.call
a  #=> 3
def b; a = 3; end
b
a  #=> 2
class B; a = 3; end
a  #=> 2

#块参数
a = 2
[1,2,3].each {|a| ... }
a = 2
[1,2,3].each {|b, a:0| ... }
a = 2
[1,2,3].each {|b, a:0| a = 3; ... }
a = 2
[1,2,3].each {|b| a = 3; ... }
a = 3

#2 
proc { a = 2 }.call
a  #=> undefined local variable

#3
a  #=>undefined local variable
a = '23' if false
a  #=>nil
```

解析:
* 局部变量对`block`封闭，意思是在`A`块中定义的局部变量在块返回时消失。但`block`能感知上下文中的局部变量并修改它，这点类似`c`语言的嵌套作用域中，小域能感知大域的局部变量
* `class`, `module`, `def`三个作用域门能隔绝局部变量，即其不修改定义时的上下文中的局部变量
* 块参数的赋值不会影响同名局部变量，包括在块参数内进行局部变量定义和赋值；若在块参数内定义并初始化了局部变量，则在块内使用该局部变量不会影响同名局部变量；若没在块参数定义并初始化，就在块内使用局部变量，依然会影响外部同名局部变量。换句话，块参数是个特殊的地方
* 定义本地变量，实际上并不一定要执行赋值语句，对于解释器而言，其看到赋值语句，执不执行都会定义本地变量

```ruby
#1 实例变量
@a  #=>nil
@a = 2
class A
  @a  #=>nil
  @a = 3
  def a
    @a  #=>nil
    @a = 4
  end
  def b
    @a  #=>4
  end
  @a = 3
end
@a  #=>2

#2
@b  #=>nil
proc {@b = 3}.call
@b  #=>3

#3
@b = 1
proc {@b = 3}.call
@b  #=>3
```

解析:
* 实例变量`@var`不同于局部变量，即使未定义也可以使用（为`nil`）
* `class`，`module`, `def`三大作用域门也隔绝实例变量。但同一类的所有实例方法间共享实例变量，本质原因是实例变量是对象的
* 注意类的实例变量和类实例的实例变量是不同的概念，`def`能隔绝二者
* 案例2,3说明一个本质现象，实例变量是和当前对象绑定的，其与作用域无关，只要当前`self`没变，则实例变量就可以联动修改。

```ruby
# 常量
module A
  AA = 2
  class B
    AA = 3
    def c
      AA = 4  #=>wrong
    end
  end
  AA  #=>2
end
```

解析:
* 常量不能动态修改，这意味着无论是在`def`中定义还是修改常量都会发生错误。也容易理解，所谓常量即一开始便存在，而`def`中的代码只有在调用时才执行，而类或模块中的代码在定义时就会执行。
* `module`, `class`隔绝常量，而事实上常量经常使用`module::class::VAR`的形式被引用

```ruby
# a.rb文件
AA = 3
aa = 4
@aa = 5
$aa = 6
# b.rb
require_relative 'a'
AA  #=>3
aa   #=>undefined local variable
@aa  #=>5
$aa  #=>6

load 'a.rb'
AA  #=>3
aa   #=>undefined local variable
@aa  #=>5
$aa  #=>6

load 'a.rb', true
AA   #=>undefined 
aa   #=>undefined
@aa  #=>nil, equal to undefined
$aa  #=>6
```

解析:
* 文件导入时，局部变量会消失；全局变量总会存在；实例变量因为本质上是属于对象的，在文件全局下同属于`main`对象的实例变量会存在(其实是说，导入后，两文件的`main`对象是同一个，但如果不导入，`main`对象不同)；文件导入合并常量空间
* `load`的第二参数，表示是否用匿名的模块封装，若为`true`，则因为常量多个匿名模块前缀不可用，实例变量变成匿名模块的实例变量，则在当前`main`对象中不可用，但全局变量仍然不变
* 少用全局变量

## 8，局部变量和方法

```ruby
def a; 'a'; end
def b; a; end
b  #=>'a'
def c; a=2; a; end
c  #=>2

class B
  attr_accessor :one, :two
  def initialize
    one = 1
    self.two = 2
  end
  def b; one=3; one; end
end
b = B.new
b.one  #=>nil
b.two  #=>2
b.b
b.one  #=>nil
```

解析:
* 因为`ruby`的方法调用可以省略`()`，则在一个域内，单个`word`可能是方法调用，也可能是局部变量。如`var = word`可能是将`word`方法调用结果赋值给`var`，也可能是将局部变量`word`赋值给`var`；如`word=var`，在类中可能是调用了`word=`的方法，也可能仅是局部变量赋值。
* 对任何单个`word`，若在此前有变量赋值，则视为局部变量，若没有则查找方法；在类中`word=`总是默认局部变量赋值，除非显式加`self`。
* 虽然对于`B`类的方法`b`，无论解释为方法调用还是局部变量赋值都不影响结果，但事实上它就是局部变量赋值。
* 因此，可以解释为局部变量的一定不会解释为方法调用。由方法调用的开销更高也可以理解这样安排的道理

## 9，`{...}`和`do...end`

```ruby
def a(name)
  'hello'
end
a 'lzp' {1+1}  #=>wrong
a('lzp') {1+1}  #=>'hello'
a 'lzp' do; 1+1; end  #=>'hello'
```

解析:
* `a b {...}`在这种排列下，`block`会默认为`b`的参数,如果`b`不是执行对象则会发生错误，因此要明确写为`a(b) {...}`如此，`block`为`a`的参数, 这也是`define_method :a {'a'}`会失败的原因
* `def`, `lambda`, `proc`, `Proc.new`都可定义执行对象, 所有执行对象无论有无`yield`，都可接收任意`block`

## 10，属性设置方法的返回值

```ruby
class A
  def a=(val)
    @val = val
    return 99
  end
end
a = A.new
t = (a=2)
t  #=>2
```

解析:
* `return`被丢弃

## 11，数据即指令，指令即数据

若`a>b`则返回`a*b`，否则返回`a+b`

```lisp
((if (> a b) * +) a b)
```

```c
#1
var = a>b ? a*b : a+b
#2
if (a>b)
  var = a*b  #或 return a*b
else
  var = a+b  #或 return a+b
```

``` ruby
#1
if a>b; a*b; else; a+b
#2
a>b ? a*b : a+b
#3
a.send(a>b ? :* : :+ , b)
#4
a.send( if a>b; :*; else; :+; end, b)
```

解析:
* 关于上面这条`lisp`指令的思考:虽然`Ruby`有很强大的元编程能力，但并不能向`lisp`那么优雅地实现这个功能。
* `(if (> a b) * +)`表达式返回`*`或`+`，用`C`语言的思路来看，返回的应该是值，但这个值又被作为上层代码的一部分用于执行`(* a b)`，我想这就是代码即数据，数据即代码的理解吧。
* `Ruby`也是基于表达式的，即所有语句都有返回值，且对于`Ruby`而言，方法`:*`和`:+`是可以被返回的，但`Ruby`是面向对象的语言，即所有方法的调用都必须有接收者，要实现`a*b`，即`a.*(b)`，其中`a`是接收者，`b`是参数。要想调用不确定的方法(`:*`还是`:+`)，换句话说，即将方法作为参数，可以使用`Kernel#send`方法。
* 但注意`send( a>b ? :* : :+ , a, b)`并不正确，因为`a`是接收者，而非`:+`的参数，正确`a.send( a>b ? :* : :+ , b)`
* 我给了`C`语言两种实现，且它的实现看起来很像`Ruby`的前两种实现，但本质完全不同。在`ruby`中每条语句都是表达式，都有返回值，而`C`要么封装在函数中，可以返回值，要么将计算值存储在变量中，单独的表达式是不能存在的。
* `Ruby`的第2种实现看似跟`lisp`差不多，但注意变量，`Ruby`对`a`引用了3次。实现3和4其实更接近`lisp`语句表达的本质，只是`ruby`选择了类`c`的表达方式，也就失去了`lisp`自由嵌套的能力
* 当然，对这一个功能的实现，似乎也就几个字符的事，但当功能更加复杂时，`lisp`设计一致的`s`表达式和数据即代码的核心，能提供更强大的抽象能力。