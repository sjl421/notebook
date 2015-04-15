# metaprogramming ruby

## chapter1: object model

### 打开类open class技术(猴子补丁Monkeypatch)

Ruby的`class`关键字的核心任务是把你带到类的上下文中，让你可以在其中定义方法。而创建一个不存在的类不过是其的一种副作用。
从这种意义而言，`class`关键字更像一个作用域操作符而不是类型声明。

```ruby
"abc".methods.grep(/my_method/)  #=> []  (即String不包含my_methods方法)
class String
  def my_method; 'hello, string'; end
end
"abc".my_method  #=> 'hello, string'
```

隐患：当你打开类并添加某个方法，可能覆盖类本身的方法。所以，在使用打开类技术时，请确认方法名独特，或者你有意覆盖。

### Ruby的类

Ruby中，实例变量`@var`属于对象，而不属于对象的类，当给其赋值时即生成。对同一个类，可以创建具有不同实例变量的对象。

```ruby
class MyClass
  def my_method
    @v = 1
  end
end
a = MyClass.new
a.instance_variables  #=>[]  (@v在my_method中赋值生成，在方法调用前@v不存在)
a.my_method
a.instance_variables  #=>[:@v]
a.instance_variables_set(@hello, "lzp")  #(为对象a添加实例变量)
```

Ruby中，一个对象仅仅包含它的实例变量以及一个对自身类的引用。当然，还有一个唯一标识符（通过`Object#object_id`方法获得）
和一组诸如`tainted`或`frozen`的状态。

实例变量存放在对象中，方法存放在对象的类中。更准确地说，实例方法(`instance_methods`)存在于对象的类中，
单件方法(`singleton_methods`)存在于对象的单件类(`singleton_class`)中。

### 类也是对象

Ruby中，一切皆对象，类也是对象。对象的方法是类的实例方法。类作为对象，类方法也是类的类(`Class`)的实例方法。

```ruby
"abc".class  #=> String
String.class  #=> Class
Class.instance_methods(false)  #=> [:superclass, :allocate, :new]  (因此，任何类都有new和superclass这两个类方法)
Class.superclass  #=> Module  (类是增强的模块。增加了3个方法)
Module.class  #=> Class  (模块也是对象)
```

类与模块：通常，当希望它应该在别处被包含时或当成命名空间时，应该选择模块；当希望它被实例化或者被继承时，应该选择使用类。
但大部分情况下，类和模块可以互换。

### 常量

任何以大写字母开头的引用（包括类名和模块名）都是常量。在常量的引用路径中，类与模块名扮演目录角色，其他普通常量则扮演文件角色。

```ruby
module C
  Out = "out constant"
  class D
    In = "inner constant"
    Module.nesting  #=> [C::D, C]  (返回常量的路径，类似返回当前目录)
    ::C::Out  #=> "out constant"  ('::'根路径)
  end
end
C::Out #=> "out constant"
C::D::In  #=> "inner constant"  (常量的引用像文件系统对文件的引用一样，不过一个以"::"分隔，一个以"/"分隔)
C.constants  #=> [:Out, :D]  (你没看错，类名即常量名。Module#constants返回当前范围内的常量)
C::D.constants  #=> [:In]
Module.constants  #=> [:Object, :Module, ..., :C]  (Module.constants返回当前程序中所有的顶级常量)
##
load('abc.rb' [, true])  #(会将abc.rb中的常量加载到当前作用域，污染命名空间。true参数则会创建匿名类作为命名空间防止此)
# load执行代码，而require用来导入类库
```

### 方法调用

当调用一个方法时，第一步找到这个方法，第二步执行这个方法。

方法查找：接收者(`Receiver`)和祖先链(`ancestors chain`)。为了查找一个方法，Ruby首先会在接收者的类中查找，
然后一层层地在祖先链中查找，直到找到这个方法为止。“向右一步，再向上”。向右进入类，向上进入祖先链。

包含类：当在类中包含模块时，Ruby创建一个封装模块的的匿名类，再将其插入祖先链中，位置正好在包含其的类上方。
此匿名类叫做包含类(`include class`)或代理类(`proxy class`)， 且`superclass`方法会假装其不存在。当类中包含模块，
则模块也将位于祖先链中。最后包含的模块最先被查找。

执行方法：当调用一个方法时， 接收者成为`self`。从此刻起，所有实例变量都是`self`的实例变量，所有没有明确指明接收者的方法
都在`self`上调用。当你的代码调用其他对象的方法时，这个对象成为`self`。在类或模块定义中在方法定义外，`self`由这个类或模块充当。

顶级上下文：当开始运行Ruby程序时，解释器会创建一个名为`main`的对象作为当前对象。`main`的类为`Object`。

私有方法：第一，如果调用方法的接收者不是你自己，则必须明确指明一个接收者，第二，私有方法不能明确指定一个接收者。由上，私有方法
只能在自身中调用。(私有规则)

## chapter2: methods

### 动态方法(dynamic methods)

#### 动态调用Object#send()

动态派发：通过`Object#send`方法，调用的方法名成为一个参数，这样就可以在代码运行期间，直到最后一刻才决定调用哪个方法。`send()`可以调用
任何方法，包括私有方法。`Object#public_send()`不能调用私有方法。

```ruby
class MyClass
  def hello(name)
    "hello, #{name}"
  end
end
obj = MyClass.new
obj.hello("lzp")  #=> "hello, lzp"
obj.send(:hello, "lzp")  #=> "hello, lzp"
1.send(:+, 2)  #=> 3
```

#### 动态定义Module#define_method()

动态方法：利用`Module#define_method()`方法定义一个方法，只需要为其提供一个方法名和充当方法主体的块。`define_method()`是方法，`class`实际是在运行代码。
此时，`define_method`方法的接收者是`MyClass`类，其继承`Object`。

```ruby
class MyClass
  define_method :hello do |name|
    "hello, #{name}"
  end
end
obj = MyClass.new
obj.send :hello, "lzp"
```

### 幽灵方法Kernel#method_missing

幽灵方法：当Ruby没找到方法时，就会在对象上调用`Kernel#method_missing`方法。通过覆写`Kernel#method_missing()`方法，你可以调用并不存在的方法。

动态代理：一个捕获幽灵方法调用并把它们转发给另外一个对象的对象（有时也会在转发前后包装一些自己的逻辑）。

隐患1：由于调用未定义的方法会导致调用`method_missing()`方法，对象可能会因此接受一个直接的错误方法调用。当碰到不知道如何处理的方法时，记得回到
`Kernel#method_missing`方法。由于Ruby中无参的方法调用可省略括号，会看起来像一个普通的变量。变通变量有作用域，而方法则有查找路径。注意区分。

隐患2：当一个幽灵方法和一个真实方法发生冲突时，会调用真实方法。为了安全起见，你应该在代理类中删除绝大多数继承来的方法。或直接继承自`BasicObject`。

白板类：你可能通过`Module#undef_method()`方法删除所有方法，或通过`Module#remove_method()`方法只删除接收者自己的方法而保留继承来的方法。

```ruby
# 1.
class Computer < BasicObject
  def initialize(computer_id, data_source)
    @id = computer
    @data_source = data_source
  end

  def method_missing(name, *args)
    super if !respond_to? name
    info = @data_source.send "get_#{name}_info", args[0]
    price = @data_source.send "get_#{name}_price", args[0]
    return "#{name.to_s.capitalize}: #{info} ($#{price})"
  end

  def respond_to?(method)
    @data_source.respond_to?("get_#{name}_info") || super
  end
end
# 2.
class Computer
  def initialize(computer_id, data_source)
    ...
    data_source.methods.grep(/^get_(.*)_info$/) {Computer.define_component $1}
  end

  def self.define_component
    define_method(name) do
      ....
    end
  end
end
```

### Module#const_missing方法

当引用一个不存在的常量时，Ruby会把这个常量名作为一个符号传递给`const_missing`方法。

## chapter3: blocks

### blocks

可调用`Kernel#block_given?`方法询问是否包含块。

```ruby
def a_method(a,b)
  return  a + yield(a,b) if block_given?
  a + b
end

def a_method(a, b, &block)
  a + block.call(a, b)
end

a_method(1, 2) {|x, y| (x + y) * 3}  #=> 10
a_method(1, 2)  #=> 3
```

闭包：任意一段代码运行时，都需要一个执行环境：局部变量，实例变量，self等。简称绑定binding。当定义一个块时，它会获取当时环境中的绑定，
并且把它传给一个方法时，它会带着这些绑定一起进入该方法。

作用域：`Kernel#local_variables`方法会显示当前作用域的变量数组。Ruby的作用域是截然分开的，一旦进入一个新的作用域，原先的绑定就会被替换为一组新的绑定。
对于Java和C#而言，有“内部作用域”的概念，在内部作用域中可以看到外部作用域。

作用域门：程序会在三个地方关闭前一个作用域，同时打开一个新的作用域：类定义`class`,模块定义`module`,方法定义`def`。全局变量`$var`可以在任何作用域访问，
实例变量`@var`可以在同一类中不同方法间访问。在类和模块中的代码会立即执行，而方法定义中的代码只在方法被调用时执行。

顶级实例变量：顶级对象`main`的实例变量。表现为方法，类，模块之外的实例变量。只要`main`扮演`self`的角色，就可以访问顶级实例变量。

```ruby
def my_method
  x = "goodbye"
  yield "cruel"
end
x = "hello"
my_method {|y| "#{x}, #{y} world"}
#=> "hello, cruel world"  (not "goodbye, cruel world")
```

### 扁平化作用域

局部变量无法穿越作用域门。但可以通过块闭包实现绑定的穿越。使用方法来替代作用域门，就可以让一个作用域看到另一个作用域中的变量。
`class`对应`Class.new()`, `def`对应`define_method`, `module`对应`Module.new`。通过扁平作用域技术，可以在一组方法间共享变量，而不让另外的方法访问。

```ruby
local_var = "succeed"
MyClass = Class.new do
  puts "#{local_var} in the class definition"
  define_method :my_method do
    puts "#{local_var} in the method
  end
end
MyClass.new.my_method    #=>"success in the class definition
                         #=>"success in the method
```

### Object#instance_eval

在一个对象的上下文中执行一个块。传递给其的块称为上下文探针，因为它既可以携带外部作用域的绑定，也可以访问对象`self`内的实例变量。

```ruby
class MyClass
  def initialize
    @v = 1
  end
end
obj = MyClass.new
obj.instance_eval do
  self  #=> #<MyClass:0x....>
  @v    #=> 1
end
obj.instance_eval {@v = 3}
obj.instance_exec(3) {|arg| @v = arg}  #instance_exec允许对块传入参数。
```

### 洁净室clean room

有时，你创建一个对象，仅仅为了在其中执行块。

### 可调用对象

使用块分两步：第一，将代码打包备用，第二，调用块执行代码。

延迟执行(`defered evaluation`): `Proc.new`将块打包存储为对象供以后执行。

`&`:这是一个`Proc`对象，把它当成一个块使用，简单去掉`＆`操作符，就可得到`Proc`对象。

可调用对象是可以执行的代码片段，而且它们有自己的作用域。
Ruby中的可调用对象：块`block`,`Kernel#proc`,`Kernel#lambda`,`Proc.new`方法。
使用`lambda`创建的`Proc`称为`lambda`，而使用其它方式创建的则简单称为`proc`。`Proc#lambda?`用于区分。

`proc`与`lambda`：第一个区别与`return`关键字相关，第二个区别与参数检验相关。`lambda`中，`return`仅仅表示从`lambda`返回，
而在`proc`中，`return`从定义`proc`的作用域返回。如果调用`lambda`的参数数量不对，它调用失败，抛出`ArgumentError`错误，
而`proc`则会把参数调整为自己期望的形式。

```ruby
inc = Proc.new {|x| x+1}
inc = proc {|x| x+1}
inc = lambda {|x| x+1}
inc = ->(x) {x + 1}  #简洁lambda
inc.call(2)  #=> 3
inc.(2)  #=>3
inc[2]  #=>3
#
p = Proc.new {|a, b| [a, b]}
p.call(1, 2, 3)   #=> [1, 2]
p.call(1)   #=> [1, nil]
```

### Method对象

通过调用`Object#method`方法可获得用Method对象表示的方法，用`Method#call`进入调用。用`Method#unbind()`方法分离方法跟所绑定的对象，返回`UnBoundMethod`对象。
`UnboundMethod#bind()`方法重新将方法绑定在对象上。

区别：`lambda`在定义它的作用域中执行，`Method`对象会在自身所在对象的作用域中执行。

```ruby
class MyClass
  def initialize(v)
    @v =v
  end
  def my_method
    @v
  end
end
obj = MyClass.new(2)
m = obj.method :my_method    #=> Method
m.call    #=> 2
unbound = m.unbind
another_object = MyClass.new(4)
m = unbound.bind(another_object)
m.call    #=>4
```

`Method#to_proc()`方法把`Method`对象转为`Proc`对象，`define_method()`方法把块转换为方法，`&`在块与`Proc`对象间转换。

## chapter4: class defination

### Module#class_eval方法

Ruby的`class`并非是在指定对象未来的行为方式，相反，实际是在去年代码。

不管在Ruby的程序的哪个位置，总是存在一个当前对象`self`，也总是存在一个当前类或模块。当定义一个方法时，该方法将成为当前类的一个实例方法。
每当通过`class`关键字打开一个类时，或通过`module`关键字打开一个模块时，这个类或模块就成为当前类或模块。Ruby的解释器总是会追踪当前类。所有
使用`def`定义的方法都成为当前类的实例方法。

`Module#class_eval()`或`Module#module_eval()`方法会在一个已存在的类或模块上下文中执行一个块。

`Object#instance_eval()`方法会修改`self`，当在块中定义方法时，是在定义单件方法。`Module#class_eval()`方法会修改`self`或当前类，当在块中定义方法时，
是在定义实例方法。

```ruby
def add_method_to(a_class)
  a_class.class_eval do
    def m; 'hello'; end
  end
end
add_method_to String
"abc".m    #=> "hello"
```

`class_eval()`使用扁平作用域，而`class`则打开新的作用域。`class_eval()`可以对任何代表类的变量使用，而`class`则只能使用常量。

### 类实例变量

Ruby解释器假定所有实例变量都属于当前对象`self`。类定义时，`self`由类本身担任。类的实例变量即在类内方法外的实例变量，不同于类的对象的实例变量。

```ruby
class MyClass
  @my_var = 1  #类实例变量
  def self.read; @my_var; end
  def write; @my_var = 2; end  #对象实例变量
  def read; @my_var; end
end
obj = MyClass.new
obj.write
obj.read  #=> 2
MyClass.read  #=> 1
```

类实例变量是类作为`Class`类对象的实例变量，仅属于类，仅能被类本身所访问，不能被类的实例或子类访问。类变量与类实例变量不同，可以被子类或类的实例使用。
类变量并不真正属于类，而属于此类及继承此类的整个体系结构。

当把一个匿名类（通过`Class.new`创建)赋值给常量时，类便有了名字，有了`name()`方法。

### 单件方法singleton methods

Ruby允许给单个对象增加一个方法，可用于增强某个对象。

在Ruby中，对象的类型只代表一组它能响应的方法。

类方法的实质是：它们是一个类的单件方法。

```ruby
def obj.a_singleton_method; end    #=>定义对象的单件方法
def MyClass.another_singleton_method; end    #=>定义类方法
class MyClass
  def self.yet_another_singleton_method; end
end    #=> 定义类方法
class MyClass
  class << self
    def yet_another_singleton_method; end
  end
end    #=> 定义类方法
```

### 类宏class macros

### 单件类singleton class

`singleton_class()`会返回接收者的单件类。每个单件类只有一个实例，是一个对象单件方法的存在之所。`class()`方法不知道单件类的存在。

```ruby
obj = Object.new
eigenclass = class << obj; self; end  #=> #<Class:#<Object:0x....>>
eigenclass.class  #=> Class
class << obj
  def hello; end  ＃hello定义时当前self是eigenclass, 即单件方法之所。
end  #=> 为obj定义单件方法
```

方法查找：当对象有单件类时，Ruby会从单件类开始查找，或找不到方法，则沿着祖先链向上来到其单件类的超类，这个对象的类。
也就是说，对象单件类的超类是对象的类。单件类的超类就是超类的单件类。类方法存在于类的单件类中。也因此子类中可以调用父类的类方法。

单件类作为类，也是对象，也有自己的单件类。单件类的单件类也有单件类。

### 大统一

* 只有一种对象－－要么是普通对象，要么是模块。
* 只有一种模块－－可以是普通模块、类、单件类。
* 只有一个方法，它存在于一种模块中－－通常是类中
* 每个对象（包括类）都有自己的“真正类”－－要么是普通类，要么是单件类。
* 除了`BasicObject`类无超类外，每个类有且只有一个超类。这意味着从任何类只有一条向上直到`BasicObject`的祖先链。
* 一个对象的单件类的超类是这个对象的类；一个类的单件类的超类是这个类的超类的单件类。但`BasicObject`的单件类的超类是`Class`。
* 当调用一个方法，Ruby向右迈一步进入接收者真正的类，然后向上进入祖先链。

### 对象扩展Object#extend()

对象扩展：当类包含模块时，它获得的是该模块的实例方法，而模块的类方法则存在于模块的单件类中，不被包含。但通过在类的单件类中包含模块，即可将模块
中的实例方法变成类的类方法。此方法对任意对象都可用。

```ruby
module MyModule
  def my_method; 'hello'; end
end
class MyClass
  class << self
    include MyModule
  end
end    #=> 在单件类内包含模块，会将模块的实例方法变成此类的类方法
class MyClass
  extend MyModule
end    #=> 类扩展Object#extend()
MyClass.extend MyModule  #=>  类扩展
obj = Object.new
obj.extend MyModule    #=> 对象扩展
```

### 方法别名method aliases

```ruby
class MyClass
  def my_method; 'my_method()'; end
  alias :m :my_method    #=> 别名与原名间没有','间隔， alias是关键字而非方法
  alias_method :m2, :m    #=> Module#alias_method是方法，有','间隔
end
obj = MyClass.new
puts obj.my_method
puts obj.m
puts obj.m2
```

环绕别名：先定义别名再重定义方法，则调用别名引用的是原方法，调用原方法引用的是重定义的方法。这样就可以在不改变原方法前提下，
为原方法名添加自己的逻辑。

隐患1：一旦使用环绕别名，则重新添加逻辑的方法可能会破坏期望调用真正原始方法的库或方法。也是一种猴子补丁。

```ruby
class String
  alias :real_length :length
  def length
    real_length > 5 ? 'long':'short'
  end
end

"War and Peace".length    #=> long
"War and Peace".real_length    #=> 13
```

隐患2：永远不该把一个环绕别名加载两次。由于`class`关键字只是打开类，首次加载时，`real_length`引用原始的`length`，二次加载时，
`real_length`将引用定义后的`length`。如此会导致互相引用.

绝大多数Ruby操作符实际上都是拟态方法。如整数`+`操作符是名为`Fixnum#+()`方法的语法糖。即`1+1`，解析器转换为`1.+(1)`。

## chapter5: ctwc

### Kernel#eval()方法

如果深入底层，代码只不过是文本而已。

`Binding`类：`Binding`是一个用对象表示的完整作用域。可能通过创建`Binding`对象来捕获并带走当前作用域。
接下来通过`eval()`,`instance_eval()`, `class_eval()`方法
在`Binding`对象所携带的作用域中执行代码。使用`Kernel#binding()`方法创建`Binding`对象。

```ruby
class MyClass
  def my_method
    @x = 1
    binding    #=> binding创建当前作用域对象
  end
end

b = MyClass.new.my_method
puts(eval "@x", b)
```

`Kernel#eval`(语句, binding对象，文件，行), 后面三个参数可选，`Binding`对象用于语句的执行上下文，文件和行用于发生异常时跟踪调用栈信息。

嵌套`irb`会话：可以在`irb`中对某个对象使用`irb`， 此对象成为它的上下文。

`eval()`方法问题需要一个代码字符串作为参数，而`instance_eval()`方法和`class_eval()`方法除了块，也可以接收代码字符串作为参数。

代码字符串：1，不能利用编辑器的功能特性如语法高亮和自动完成；2，难以阅读和修改；3，Ruby不会对其进行语法检查，易导致运行时失败；
4，代码注入的安全性

`Kernel#load()`和`Kernel#require()`方法会接收文件名作参数并执行其中的代码。也有安全性问题。

### 污染对象

Ruby会自动把不安全的对象－－尤其是外部传入的对象－－标记为被污染的。每次从污染对象字符串运算来的新字符串也是被污染的。
通过`tainted?()`方法判断类是不是被污染。通过`Object#untaint()`方法来显式去除污染性。

安全级别：当设置一个安全级别时，通过给`$SAFE`全局变量赋值，就禁止了某些特定的潜在危险操作。Ruby有5个安全级别，从0－4，数字越大限制越多。
且在任何大于0的安全级别上，Ruby都会拒绝执行污染的字符串。
