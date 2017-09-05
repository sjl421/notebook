# 理解Ruby中的类

> live with scope  --enalix, enalix@163.com

## 序言

源起于'Python开发者'公众号转载的 _深刻理解Python中的元类_ 一文, 回忆着自己看过的 _Ruby元编程_ 一书, 参照写个相应的Ruby版.

Python和Ruby在很多方面都非常相像, 特别是Ruby的设计部分参考了Python. 但在很多方面, 它俩的很多概念并非一一对应的. 在这里的 _元类_ 在Ruby 中并没有相应的概念, 如果理解为创建类的类, 最相近的应该是`Class` .

这里不会将那篇文章的内容都复制过来, 只是挑选不一样的地方写一写, 因此, 你最好已经读过那篇文章了. 读这篇时, 最好对照着读.

## 类也是对象

相比Python, Ruby语言有着最纯粹的面向对象编程的设计. 同样的,Ruby的类的概念也是借鉴于Smalltalk. 关于什么是类, 我更倾向于理解为, 描述一个对象的状态(实例变量)和操作(方法)的代码段.

```rb
class ObjectCreator < Object; end  #=> nil
my_object = ObjectCreator.new  #=>#<ObjectCreator:0x00000000b41400>
print my_object  #=>nil
```

说明:

* 类默认继承自`Object`, 因此`< Object`非必要. 原文的`Python`代码也是.
* 在Ruby 中, 在不引起歧义的前提下, 函数调用的`()`可以省略. 这里同原文的Python 代码虽然看起来相同, 但原理完全不同.Python2.7中, `print`实现为语句, 但在Python 3.x中, 实现为全局函数, 则必须加`()`表示调用.
* 这里的`#=>`表示输出的结果, `print`无输出, 即`nil`来表示无

同Python, Ruby的类同样也是对象. 不同于Python, Ruby中的`class`实际是打开一个类, 如果类不存在则创建它. 换句话说, 在Python中重复`class`定义同一类, 后者会覆盖前者, 而在Ruby中, 类是同一个, 后者只是给这个类添加了新的方法或变量.

Python:

```py
class N1:
  def __init__(self, name):
    self.name = name
  def hello(self, s):
    return self.name + s
N1("lzp").hello(" is good man")  #=> "lzp is good man"
class N1:
  def __init__(self, name):
    self.name = name
  def world(self, s):
    return self.name + s
N1("lzp").hello(" is good man")  #=> AttributeError, 无属性
N1("lzp").world(" is good man")  #=> "lzp is good man"
```

Ruby:

```rb
class N1
  def initialize(name)
    @name = name
  end
  def hello(s)
    @name + s
  end
end
N1.new("lzp").hello(" is good man")  #=> "lzp is good man"
class N1
  def initialize(name)
    @name = name
  end
  def world(s) @name + s end
end
N1("lzp").hello(" is good man")  #=> "lzp is good man"
N1("lzp").world(" is good man")  #=> "lzp is good man"
```

* Ruby少了无语的`self`, 但多了无语的`end`.
* Ruby的函数默认返回最后一个表达式的值, 但在Python中则必须显示地`return`.
* Ruby的方法定义可以写成一行,Python来咬我啊

很多语言都声称 _xx语言中一切都是对象_, 包括Java. 很明显, 不同语言中的对象概念应该是有区别的, 那么如何来理解对象呢. 这里我基本同意原文中所说, 可赋值, 可拷贝, 可增加属性, 可作参传递.

注意, 不要将对象和对象的引用混淆, 对象的引用往往表现为常见的各种标识符.

```Ruby
ObjectCreator.to_s  #=> "ObjectCreator"
```

```Python
str(ObjectCreator)  #=> <class '__main__.ObjectCreator'>
```

由于`print`函数实际是调用对象转字符串后输出, 并无特殊意义. 下面的例子更好地展示了, 作参传递.

Python:

```py
def new(o):  return o()
oc1 = new(ObjectCreator)  #=><__main__.ObjectCreator at 0x...>, 新的实例对象
```

Ruby:

```rb
def new(o) o.new end
oc1 = new(ObjectCreator)  #=>#<ObjectCreator:0x...>, 新的实例对象
```

## Python属性操作

Python 中有3个全局函数, 用于对象的属性操作.

* `hasattr(obj, 'attr_name')`判断对象是否有此属性
* `getattr(obj, 'attr_name')`获取对象指定属性
* `setattr(obj, 'attr_name', attr_value)`则是设置指定属性
* `obj.new_attr = attr_value`设置属性
* `delattr(obj, 'attr_name')`删除属性.

Python中的属性是一个宽泛的概念, 包括类变量, 实例变量, 类方法和实例方法. 这其中的区别是非常经典的, 且在不同语言中有不同的名称, 有不同的书面写法.

* 类变量, 通常指依附于类本身而非类的实例的变量, 表述的是类的状态
* 实例变量, 类的每个实例有独立的变量, 来表述实例对象的状态
* 类方法, 通过类名调用的方法
* 实例方法, 通过类的实例对象调用的方法

在Python中, 通过给`self.var_name`赋值创建实例变量, 类定义中方法外赋值的非`self`变量都是类变量. 定义方法时, 传递有`self`参数的是实例方法, 否则为类方法.

Python:

```py
class N2:
  class_var = 3  # 类变量, 也能通过实例对象访问
  def __init__(self, name):
    self.name = name  #实例变量
  def hello(self, s):
    return "hello " + self.name + s
  def world(s):
    return "world " + N2.c_var + s
n2 = N2("lzp")
n2.hello(" is good man")  #=> "hello lzp is good man"
N2.hello(n2, " is good man")  #=> "hello lzp is good man"
n2.world(" lzp")  #=> 函数只要参数, 但参数多余
N2.world(" lzp")  #=> "world lzp"
```

Python在类的方法设计上很取巧. 就如之后所说, Python其实是没有类方法一说的, 全部都是函数. 类的方法第一个参数是`self`, 像在`world`方法定义中, 没有`self`, 方法内是不能引用实例变量的. 且此处是不是`self`也无所谓, 任意标识符都可以, 基于惯例使用`self`. 且在对象上调用方法, 本质上只是将对象作为接收者, 作为第一参数传递给函数. 若函数的第一参数不是`self`, 则在对象上调用方法会提示多余参数.

在Python中, 函数是对象, 同其他所有对象一样. 因此大一统的去理解Python的类概念就是: 类是对象, 对象有属性, 属性即变量名和其对应的对象. 若对应的对象是函数对象, 则对应的变量是函数名, 其中第一参数为`self`的为类实例方法.

从属性的角度重新定义N2, Python:

```py
class N2: pass
N2.c_var = 3
def init(self, name):  self.name = name
N2.__init__ = init
N2.hello = lambda self, s: "hello" + self.name + s
N2.world = lambda s: "world " + N2.c_var + s
```

这让我想起了`USB`, 支持热插拔, 即插即用, 想插就插,Python老爹真任性. 这里使用了`lambda`来定义匿名函数.

## Ruby属性操作

Ruby没有属性一说, 但你也可以去宽泛地去理解. 相反的,Ruby的类变量, 实例变量, 类方法和实例方法是清晰地分开的, 毕竟是纯粹地面向对象. 另一个,Ruby其实没有函数一说, 所有函数都有其所属的类, 没有单独的函数, 或者说Ruby只有方法. 关于属性, 另一个其他面向对象语言中相似的概念是 _域_, 就是在类中占块地, 放变量还是函数都行.

Ruby:

```rb
class N2
  @c_i_var = 1  #类的实例变量
  @@c_var = 3   #类变量, 子类可继承
  def initialize(name)
    @name = name
  end
  def hello(s)
    "hello" + @name + s
  end
  def self.world(s)  #类方法
    "world " + @@c_var.to_s + s
  end
end
N2.new("lzp").hello(" is good man")  #=> "lzp is good man"
N2.world(" is good man") #=> "world 3 is good man"
```

在这里, 类的实例变量可以理解为类作为对象的实例变量. 实例变量是专属于对象的. 而类变量则是属于整个类体系的, 即它的所有子类都可以访问.

回到原文, Python中的属性对应Ruby的多个概念. 因此对属性的操作也是分不同的在进行.

Ruby:

```rb
n1 = N1.new("lzp")
n1.instance_variables  # 返回所有实例变量
n1.instance_variable_set("@age", 3)  #=> 设置实例变量
n1.instance_variable_get("@age")  #=>实例变量
n1.instance_variable_defined?("@age")  #=> 判断有无
n1.class_variables  # 返回所有类变量
n1.class_variable_get/set/defined?  #同上
N1.instance_methods(false)  # 列出所有非继承的实例方法
N1.singleton_methods  # 列出所有非继承的类方法
```

这里的`singleton_methods`可以理解为类方法. 但严格地说, 它是专属于对象的方法. 若专属于类, 则成为类方法. 换句话说,Ruby没有类方法一说, 称为单件方法.

Ruby中, 一切皆对象. 因此有必要来理解下Ruby的对象模型, 详细地建议看 _Ruby元编程_一书.

对象由状态, 所属类的引用和操作构成. 状态和操作都是专属的, 只能由本对象进行`.`运算. 普通对象的状态即实例变量, 操作即单件方法, 类对象的状态即类的实例变量即类变量, 类对象的操作即类的单件方法即类方法, 其实本质是相同的. 每个对象都存储有对所属类的引用, 以此来知晓可调用的实例方法.

所谓所属类的引用, 很简单, 在对象上调用`#class`方法即可

```rb
1.class  #=> Fixnum
"1".class  #=> String
Fixnum.class  #=> Class
String.class  #=> Class
```

在后文会看到Python中相应的概念`type`.

## 动态地创建类

Ruby也能在函数中创建类.

```rb
def choose_class(name)
  if (name=='foo')
    Class.new {def hello "hello" end}
  else
    Class.new {def world "world" end}
  end
end
MyClass = choose_class('foo')
MyClass.new.hello  #=> "hello"
```

这里不能使用原文中相似的`class`, 会提示不能在`def`中定义类. 不得不提前使用大招`Class.new`.

之前写到`String.class`为`Class`, 也就是说, 在Ruby中, 所有的类都是`Class`的对象. 注意大小写. 自然, 创建新的类, 也就是创建`Class`的实例对象, 使用`new`操作, 同其他所有类一样. 不过创建的是匿名类, 赋值给一个首字母大写的常量名即可.

```rb
a = Class.new
a.name  #=> nil
a.new.class  #=> xxx
A = a
A.name  #=> A
A.new.class  #=> A
```

好了, 原文进行到了Python的所有类的`type`都是`type`. 在本质上, 一切类的生成都是通过调用`type`进行的.

将上述Ruby代码原样翻译过来, 对应的Python代码为:

```rb
def choose_class(name):
  if name=='foo':
    return type('Foo', (), {'hello': lambda self:"hello"})
  else:
    return type('Bar', (), {'world': lambda self:"world"})
MyClass = choose_class('foo')
MyClass().hello()  #=> "hello"
```

解释下参数, 第一个是类名字符串, 第二个基类的元组,Python支持多继承, 可以有多个基类, 所谓的基类可以理解为超类, 父类等概念. 第三个是属性, 由前所知, 类中的一切都是属性. 如此即可定义一个新类.

但不同于Ruby, `type`的第一个参数即类名, 跟`MyClass`无关, 即赋值不会改变类名. 但Ruby是在将类对象第一次赋值给常量时生成类名的, 之后赋值也不会改变.

在Ruby中, `Class.new(superclass)`来表示继承类.Ruby中只支持单继承, 通过模块来添加不同的功能.

前文提到,Ruby的类有打开性质, 给类添加方法和变量是非常方便.

## 到底什么是元类

这里需要先普及几个常用的操作:

Python:

```py
a = 1
a.__class__  #=> int, 对象的类
type(1)  #=> int
int.__base__  #=> object, 类的基类
int.__bases__  #=> (object,), 类的基类元组
```

Ruby:

```rb
1.class  #=> Fixnum, 对象的类
Fixnum.superclass  #=> Integer, 类的超类
Fixnum.ancestors  #=> [Fixnum, Integer, Numeric, Comparable, Object, Kernel, BasicObject], 类的祖先链
```

所谓祖先链, 即类, 类的超类, 类的超类的超类, ...一直到最初始的类, 即`BasicObject`. 其实, 在1.9之前, 所有类都是继承自`Object`, 后来又在前面加入了`BasicObject`, 个人猜测是为了所谓洁净室技术吧.

原文提到, 不断地调用`.__class__`属性, 最终会到达`type`类型 ,Ruby中对应的, 不断调用`.class`方法, 最终会到达`Class`类型. 原文中可以从`type`继承, 来创建元类. 但在Ruby中是不能创建`Class`的子类.

原文提到的`__metaclass__`属性, 我思考了很久, 基本确认Ruby中没有相似的概念. 就举的将属性名大写的例子而言, 应该是在用`class`定义类时, 会自动调用这个属性(所引用的函数对象). 初步看, 有种钩子方法的感觉. 就是"定义类"这个事件发生时, 会自动触发执行`__metaclass__`属性.

Ruby也有一些钩子方法:

* `included`表示模块被包含时执行
* `extended`表示模块被后扩展时执行
* `prepended`表示模块被前扩展时执行
* `inherited`表示类被继承时执行
* `method_missing`表示对象调用不存在的方法时执行

但目前没找到当定义类时被执行的钩子方法. 所以像原文的大写属性名的操作, 还真不知道如何进行. 但事实上,Ruby的对应属性的标识符有严格的规定, 不可能大写首字母. 如类变量`@@var`, 实例变量`@var`, 方法名`two_method`.

但如果实现不了这个, 总觉得Ruby有种被比下去的感觉, 虽然大写所有属性首字母的操作似乎没有意义.

```rb
class N
  def hello; "hello"; end
  instance_methods(false).each {|x| alias_method x.capitalize, x; remove_method x}
end
N.new.Hello  #=> "hello"
N.new.hello  #=> 方法未定义
```

这是大写所有实例方法名的首字母, 核心的思想是, 为原方法建立新的别名, 再删掉原方法. 同Python一样,Ruby的类是在执行代码.

> Rb: class N; puts "hello"; end  #=> "hello"

Ruby:

```rb
class N
  def self.world; "world"; end
  class << self
    instance_methods(false).each {|x| alias_method x.capitalize, x; remove_method x}
  end
end
N.World  #=> "world"
N.world  #=> 方法未定义
```

这是大写所有的类方法名的首字母.

```rb
class N
  @name = "lzp"
  instance_variables.each {|x| instance_variable_set("@"+x.to_s[/\w+/].capitalize, @name); @name = nil}
end
N.class_eval {@Name}  #=> "lzp"
N.class_eval {@name}  #=> nil
```

这是大写所有的类的实例变量.

由于Ruby的实例变量默认是不能从外部访问的, 不得不使用`.class_eval`来打开类的上下文.

不存在如何大写所有实例变量的代码, 因此在类实例化前, 实例对象的实例变量是不存在的.

好吧, 我承认, 这实现的很别扭. 在同一操作的表述上, 不同语言有不同的书面写法, 也自然有简单有繁杂.

## 函数式特性

谈点别的, 有关函数式特性, 使用`map/filter/reduce`.

Python:

```py
a = ["he", "hk", "ok"]
list(map(lambda x: x*2, a))  #=>["hehe", "hkhk", "okok"]
list(filter(lambda x: x.startswith("h"), a))  #=> ["he", "hk"]
import functiontools.reduce
reduce(lambda x,y: x+":"+y, a)  #=> "he:hk:ok"
```

用上述函数来替换原文中的语句:

> Py: dict(map(lambda i: (i[0].upper(), i[1]), filter(lambda i: not i[0].startswith("__"), future_class_attr.items())))

好吧, 我承认我的Python技术真不高, 如果真写成一行, 完全看不懂了, 原文作者那样写更清晰简洁易懂, 当然更主要的是, 用`map/filter`会引入新的难点, 容易偏离主题.

希望有高手能告诉我, 将一个类的所有非"__"的属性的键变为大写如何以更函数式的方式表达出来.

Ruby:

```rb
a = ["he", "hk", "ok"]
a.map {|x| x*2}  #=> ["hehe", "hkhk", "okok"]
a.select {|x| x.start_with? "h"}  #=> ["he", "hk"]
a.reject {|x| x.start_with? "h"}  #=> ["ok"]
a.reduce {|sum, x| sum + ":" + x}  #=> "he:hk:ok"
```

同样的, 用上述来替换原文的代码.

> Rb: future_class_attr.reject {|k,v| k.start_with? "__"}.map {|k,v| k.upcase}

Python3.x删除了`reduce`函数, 推荐使用`for`循环, 也可以使用`funtools.reduce`. 这跟Ruby完全不同,Ruby提倡使用`each`, `map`等迭代, 而`for`在底层也是在调用`each`.

## 一切皆对象

Python和Ruby都号称一切皆对象, 但很明显两个的对象概念并不完全对等.

```
Py: 1.__class__  #=> 语法错误
Py: a = 1; a.__class__  #=> int
Rb: 1.class  #=> Fixnum
Py: 1.real  #=> 语法错误
Py: b = 1; b.real  #=> 1
Rb: 1.real  #=> 1
Py: "lzp".upper()  #=>"LZP", 但在ipython中不补全方法
Py: s = "lzp"; s.upper()  #补全
Rb: "lzp".upcase  #=> "LZP", 补全
```

以上说明, 对对象和对象的引用调用方法是有区别的, 具体什么原理以及详细的区别, 我说明不了.

> Py: def hello(name): return "hello" + name
> Py: hello.__class__  #=> function

`Ruby`的方法不是对象, 不能赋值, 不能为参传递.

```rb
def hello(name); "hello" + name; end
hello.class  #=> 参数错误
new_hello = hello  #=> 参数错误
def echo(o); o(); end
echo(hello)  #=> 参数错误
```

你是不是觉得问题挺大的, 这几种对象的特征竟然都不满足. 但这些其实是一个错误, 前文有提到, 对于Ruby的方法调用, 在不引起歧义的情况下, `()`是可以省略. 在这里, 所有出现`hello`的位置都默认你在调用方法, 但方法定义有参数, 你不传递参数, 所以错误是同一个, 少参数.

函数作为对象最终用处都是被调用, 因此, 只从表面来看, `Ruby`中通过`def`定义的方法不是对象. 但本质上, 在Ruby中, 出现方法名的地方全被视为对方法的调用, 也就是说, `hello`是方法调用, 而不是方法引用, 并不表征方法本身. 那么如何获取方法本身的对象呢?

```rb
new_hello = method :hello
new_hello.call("lzp")  #=> "hellolzp"
new_hello.("lzp")  #=> "hellolzp"
new_hello["lzp"]  #=> "hellolzp"
new_hello.class  #=> Method
new_2_hello = new_hello
```

注意, 在这里可以看出, 在绝大部分语言中, `()`都是函数调用的标志. 但在Ruby中, `()`只是在有歧义情况下, 区分哪个参数是哪个函数的. 因此, 当函数作为对象时, 不得不创建新的表示调用的标志, 在这里是`.call`, `[]`, `.()`.

函数并不是唯一的可调用对象.

```rb
hello = lambda {|name| "hello" + name}
hello = ->(name) {"hello" + name}
hello = proc {|name| "hello" + name}
hello = Proc.new {|name| "hello" + name}
```

## 后记

事实上, `Class.new` 属于 Ruby 元编程的一部分, 但 Ruby 的元编程就像普通编程一样, 没有任何神秘复杂的语法. 这里真的只是冰山一角.