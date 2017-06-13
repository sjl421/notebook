# Scala

* 表达式以行结束, 无需`;`
* 所有操作符都是方法, 以`:`结尾的方法右结合, 如`'a'::list`, 其实是`list.::('a')`

```scala
import scala.language.postfixOps  // 后缀操作符, -language:postfixOps
import scala.language.reflectiveCalls  // 反射调用
import scala.language.implicitConversions  // 隐式方法, -language:implicitConversions
```

参数化类型:

* `+`, 协变, 如果`B`是`A`的子类, 则`List[B]`也是`List[A]`的子类, 称为协类型
* `-`, 逆变, 如果`B`是`A`的子类, 则`Foo[B]`是`Foo[A]`的父类, 称为父类型

## 字面量

```scala
// 布尔
true false
// 符号, 同名符号指向内存同一对象
'id  // scala.Symbol("id")
// 函数
val f1: (Int, String) => String = (i, s) => s+i
val f2: Function2[Int, String, String] = (i, s) => s+i
// Range
1 to 10
1 until 10  // 不包含10
1 to 10 by 3
10 to 1 by -3  // 递减
1.1f to 10.3f by 3.1f
'a' to 'g' by 3  // 字符范围
```

## 字符串

```scala
val a = "xxxhello, worldyyy"
a.stripPrefix("xxx").stripSuffix("yyy")  // 移除前后缀
a.startsWith("xxx"); a.endsWith("yyy")
val b = "  hello, world  "
b.trim()  // 移除两侧的空白
val c = """hello, world"""
val c = s"str a is $a"  // 字串插值
val c = "str a is %s".format(a)
```

## 特殊类型

```scala
// Option, Some, None, 包含0或1个元素
val a = Option(3)  // 等价Some(3)
val a = None
a.foreach(println)  // 若为Some, 则输出, 若为None, 什么也不做
for { it <- a } {...}
a.get  // 若为Some, 返回包装的值, 若为None, 抛出NoSuchElementException
a.getOrElse(0)  // 若为None, 则给定默认值; 若为Some, 则返回包装的值
// Either, Right, Left表示错误标志(Left)或某一对象值(Right)
val b = Right(3); b.right
val b = Left(s"wrong"); b.left
// Try, Success, Failure(问题保存Throwable类型)
Try { assert(i>0, s"nonpositive number $i"); i}  // 验证通过则返回Success, 有异常则返回Failure
// 枚举
object Breed extends Enumeration {
  type Breed = Value  // 将Breed标识符定义为Value的类型别名
  val a = Value("a1")  // 按定义顺序从0编号
  val b = Value("b1")  // 命名枚举值
  val c = Value("c1")
  val d, e, f = Value
}
import Breed._
for (breed <- Breed.values) println(s"${breed.id}\t$breed)  // id即编号
```

## variables变量

* 变量在用`val/var`声明时必须初始化
* 对引用类型, `val/var`只表示变量名是否可引用另一个对象, 而非其引用的对象是否可变

```scala
var x = 1  // 可变量
val x = 1  // 不可变量
val s = "hello"  // 类型推断
val i: Int = 1  // 标注类型
lazy val v = foo()  // 惰性赋值, 只在需要时计算, 且一旦结果不会重复计算, 不修饰var
val Seq(a, b, c) = List(1, 2, 3)  // 应用模式匹配定义了3个变量
val head +: tail = List(1, 2, 3)
val Person(name, age) = Person("Dean", 29) // case class在定义变量时匹配
val Date = """(\d+)-(\d+)-(\d+)""".r  // 定义正则表达式
val Date(year, month, day) = "1997-04-29"  // 应用正则匹配定义变量
```

## control structures控制结构

* `if`, `match`返回值的类型为所有条件分支的最小上界类型

```scala
// 条件
if (a == b) { ... }
if (a == b) { ... } else { ... }
val v = if (a == b) c else d  // if是表达式
if (a == b) { ... } else if (a == c) { ... } else { ... }
// 多分支
foo match {
  case "a" => doA()
  case "b" => doB()
  case _ => doDefault
}
val v = foo match { ... }  // match也是表达式

// 循环
while (i < foo) { ... }
do { ... } while (condition)
for (arg <- args) { ... }
for (i <- 0 to 5) { ... }
for (i <- 0 until 10 by 2) { ... }
for (
  file <- files  // 生成器表达式
  if file.isFile  // 保护式
  if file.getName.endsWith(".a")
) yield file  // 生成新集合
files.withFilter(_.isFile).withFilter(!_.getName.endsWith(".a")).map(_)  // 与上等价
args.foreach(arg => println(arg))
args.foreach(println(_))  // 省略函数字面量的参数, 以_表示
args.foreach(println)  // 省略_, 直接传递函数

// 异常
try {
  something
} catch {  // 类型匹配
  case ex: IOException => // handle
  case ex: FileNotFoundException => // handle
} finally { doStuff }
```

## functions函数

无参方法定义时省略`()`, 则调用时也省略; 若定义时添加了`()`, 则调用时可省略也可不省略. 最佳实践: 方法若无副作用则定义时省略, 若有副作用则添加`()`.

传名调用时, 参数就像普通参数, 可出现在表达式的任何位置, 不同的是, 每次(注意)引用参数都会重新计算值.

* 默认返回最后一个表达式的值, 无需显式`return`.

```scala
def hello(s: String): Unit = { println("Hello " + s) }
def hello(s: String = "lizp"): Unit = { println("Hello " + s) }  // 默认参数
def hello: Unit = println("Hello")  // 无参时, 可省略参数列表; 函数体只一个表达式时, 可省略{}
def hello = println("Hello")  // 省略Unit, 使用类型推断
def foo(args: String*) = { args.foreach(println) }  // 变长参
val f = (x: Int, y: Int) => x+y  // 函数字面量

def world(f: String => Int) = f("world")  // world(_.length), 以函数为参
def world(f: => Int) = f  // world("world".length), 传名调用, 在函数内部执行"world".length
def world(f: Int) = f  // world("world".length), 传值调用 , 在函数外执行"world".length, 再将值作参传递

foo("ab", "cd", "ef")  // 函数调用
foo(Array("ab", "cd", "ef"): _*)  // 解集合
point(x=10, y=20)  // 命名参数

var x = f.add(10)
var x = f add 10  // 中缀表达式

// 偏函数, 只处理部分输入, 以{}包围case语句
val pf1: PartialFunction[Any, String] = { case s: String => "YES" }
pf1.isDefinedAt("a")  // "a"是否在pf1的定义域中, 即pf1是否处理"a"

def doSomething(name: String)(f: String => Unit) = f(s"hello $name")  // 以函数为参, 多参数列表
doSomething("lizp")(println)
doSomething("lizp"){ x =>  // 传递参数时, ()根据需要可写成{}, 常将尾参作为隐参以简化调用
  val y = x + "  foo"
  println(y)
}

def m1[A](a: A)(f: A => String) = f(a)  // 泛函

import scala.annotation.tailrec  // 尾递归标注, 如果优化失败, 则抛出异常
def factorial(i: Int): Long = {
  @tailrec
  def fact(i: Int, accumulator: Int): Long = {  // 嵌套函数
    if (i <= 1) accumulator
    else fact(i-1, i*accumulator)  // 递归函数是该函数最后一个表达式
  }

  fact(i, 1)
}
```

## 隐式

作用: 减少代码, 向已有类型注入新的方法, 创建`DSL`

`implicit`: 使得方法或变量值可以被用于隐含转换; 将方法参数标记为可选的, 只要在调用该方法时, 作用域内有类型匹配的候选对象, 就会使用该对象作为参数.

可以将隐式理解为正常作用域的第二维空间, 用`implicit`声明一个变量或方法, 相当于将此变量和方法送入此空间; 而将方法参数声明为`implicit`就相当于告诉编译器, 若未指定此参数时, 自动从隐式空间去获取.

```scala
def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate  // 隐式参数
implicit val currentTaxRate = 0.08F  // 隐式变量, 重要的是值类型, 而非值变量名
val tax = calcTax(50000F)  // 自动在作用域中调用类型兼容值
implicit def rate(implicit n: Int): Float = 1.0f * n  // 重要的是类型, 即使rate是个函数, 但其返回Float
implicit val n = 3
val tax = calcTax(50000F)  // rate是calcTax的隐参, n是rate的隐参, 还是别搞这种多级的
```

## data types数据类型

```scala
val names = Array("Al", "Bob")  // 调用`Array`类的伴随对象的apply方法
val names = new Array[String](5)  // 5个元素的字串数组
val names = Map(12 -> "AK")  // 映射
val things = (100, "Foo")  // 二元组的字面量
println(things._1)  // 元组元素
```

## collections集合

## classes and objects类和对象

* `import`可处于任何位置, 且只在作用域可见

```scala
package foo.bar  // 声明包, 无须拿源代码处于对应的目录层级中

import java.io.File
import java.io._  // 导入io中的所有
import java.io.{Foo, File => Bar, Too => _}  // 多导入, 命名导入, 使Too不可见

class A { ... }  // 无类参数
class A(s: String) { ... }  // s为私有域
class A(val s: String) { ... }  // s为公开不可变域 
class A(var s: String) { ... }  // s为公开可变域

class Person(s: String) {
  require(name != "Joe")  // 验证参数
  val name: String = s
  private val a = "foo"  // 私有不可变域
}
object Person {  // 定义类Person的伴随对象
  def apply(s: String) = new Person(s)  // 定义apply工厂函数
}
val p1 = new Person("lizp")  // 默认的类实例化
val p2 = Person("lizp")  // 调用伴随对象的apply方法来进行实例化

class Bird extends Animal with Wings {  // 类继承, 特征混入
  override val foo = true  // 方法覆盖
}

object Foo {  // 对象, 不可实例化, 也称单例
  def main(args: Array[String]) = {  // args从0索引, 不含脚本名
    args.foreach(println)
  }
}
object Foo extends App {  // 通过继承App, 整个对象体都是main函数
  args.foreach(println)
}

abstract class Person {  // 抽象类, 只声明不定义方法
  type In  // 类型成员
  val role: Int  // 数据成员
  def walk: Unit  // 方法成员
}
class Employee(name: String) extends Person {...}  // 必须实现抽象类中的方法

sealed abstract class Foo { ... }  // 只能在当前文件定义子类型
case class Bar(s: String) extends Foo
```

`case class`:

* 自动生成伴随对象及`apply`方法
* 默认所有类参数为公开不可变域, 即默认`val`
* 添加`toString/hashCode/equals`三个方法的实现
* 添加`copy`方法

```scala
abstract class Expr
case class Var(name: String, age: Int) extends Expr
val v = Var("lizp", 13)
v.copy(age = 14)  // case类自动定义的copy, 可只给出与原对象不同部分的参数
```

## traits特征

## underscore下划线

```scala
strs.map(_.toUpperCase())  // 对集合的每个元素执行大写操作
(1 to 10).map(_ * 2)
nums.filter(_ < 10)  // 过滤
nums.reduce(_ + _)   // 归约
println(ary: _*)  // 解集合
```

## case, match模式匹配

* 模式匹配表达式中, 小写字母开头的标记解析为变量标示符, 大写字母开头的标记解析为常量标示符
* 除了偏函数, 所有`match`语句都必须是完全覆盖所有输入的
* 在`case`类的匹配上, 默认调用其自动生成的伴随对象的`unapply`方法, `Seq`的伴随对象还实现了`unapplySeq`方法

```scala
def f(x: Any) = {
  val z = 3
  val Date = """(\d+)-(\d+)-(\d+)""".r  // 创建正则表达式, """不转义\
  x match {
    case 1 => "one"  // 值匹配
    case "a" | "b" | "c" => println("mul match")  // 多匹配
    case i: Int => "got an int" + i  // 类型匹配, 对容器类型无效, 因为JVM的类型擦除
    case _: String => "got an str" + x  // 省略变量 , 使用x
    case `z` => "found z"  // 使用已定义的变量
    case (a, b, c) => s"a=$a; b=$b; c=$c"  // 元组匹配
    case Var(name) => println(s"hello, $name")  // case类变量提取
    case _ if x%2 == 0 => println(s"even: $i")  // 条件式
    case Date(year, month, day)  => println(s"$year")  // 正则匹配
    case _ => donothing  // 默认匹配, 省略值
    case x => donothing  // 默认匹配, 将对象赋予x变量
  }
}

def seqToString[T](seq: Seq[T]): String = seq match {  // 序列匹配
  case head +: tail => s"$head +: " + seqToString(tail)  // 取头部元素, 递归
  case h1 +: h2 +: tail => s"$h1 + $h2"  // 匹配2个元素
  case Nil => "Nil"
}

case class lover(boyfriend: String, maleFriends: String*)  // 有可变参数列表的case类
val a = lover("lizp", "a", "b", "c")
a match {
  case lover(boyfriend, fs @ _*) => println(s"boyfriend is $boyfriend")  // _*是关键, @将可变的参数赋值给fs变量, 若不使用则可省略
  case _ => println("no")
}
```

## 并发

`proecss`, 小的同步的进程, 通过组合多个进程完成任务:

```scala
import scala.sys.process._
"ls -l path".!  // 执行shell命令, 返回执行成功与否
"ls -l path".!!  // 同上, 但返回命令的输出
Seq("ls", "-l", "path").!  // 同1
("ls -l filename" #&& "wc -l filename").!  // 左侧进程执行成功则执行右侧, 表示如果文件存在则计算其行数
("ls -al" #> "grep vim" #>> new File("hello.txt")).!  // #>管道符, #>>文件追加
```

`Future`, 表示一个未来的值, 现在可能可见也可能不可见, 但之后会计算得到, 也可能最后是个异常:

```scala
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global  // 隐式函数, Future.apply, onComplete
import scala.util.{Try, Success, Failure}
val f = Future{ "a" + "b" }  // 类型为Future[String]
f.onComplete {  // 注册回调函数, 偏函数, 参数为Try[T]类型
  case Success(v) => println(s"success! $v")  // 成功计算得到值
  case Failure(th) => println(s"failure! returned: $th")  // 失败
}
val n = Await.result(f, Duration.Inf)  // 在指定时间内阻塞当前线程, 等待f值的计算
```

`Actor`, 构建并发, 分布式, 容错, 事件驱动的系统

```scala
import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }
import scala.io.StdIn

// Greeter Actor的伴随对象, 用于定义Actor需要的消息类型, 以及props方法(描述Actor如何构建, 非必须)
// 因为case class和case object能用于模式匹配, 因此常将消息类型定义为此
object Greeter {
  // Props是配置类, 用于描述Actor如何构建, 有类参数的常用Props(new A(args)); 没有类参数的常用Props[A]
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))
  final case class WhoToGreet(who: String)
  case object Greet
}

// Greeter Actor, 继承Actor
class Greeter(message: String, printerActor: ActorRef) extends Actor {
  import Greeter._  // 导入伴随对象内容, 主要有props和消息类型
  import Printer._

  var greeting = ""  // 内部状态

  // 所有Actor要定义receive方法, 偏函数, 只有case语句匹配
  def receive = {
    case WhoToGreet(who) =>
      greeting = s"$message, $who"  // 消息, 改变内部状态
    case Greet           =>
      printerActor ! Greeting(greeting)  // 消息, 向另一个Actor发送消息
  }
}

// Printer Actor的伴随对象, 定义了Greeting消息类
object Printer {
  def props: Props = Props[Printer]
  final case class Greeting(greeting: String)
}

// Printer Actor, 因继承了ActorLogging, 所以可在内部使用log方法
class Printer extends Actor with ActorLogging {
  import Printer._

  def receive = {
    case Greeting(greeting) =>
      log.info(s"Greeting received (from ${sender()}): $greeting")  // 只有一个Greeting消息, 打印
  }
}

object AkkaQuickstart extends App {
  import Greeter._

  val system: ActorSystem = ActorSystem("helloAkka")  // 创建ActorSystem, 参数为系统名称

  try {
    // 创建Actor, 这里用工厂函数返回的只是引用ActorRef, 参数为Props和Actor的名称(可省略)
    // val priter: ActorRef = system.actorOf(Props[Printer], "printerActor")  // props方法非必须
    val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

    val howdyGreeter: ActorRef =
      system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
    val helloGreeter: ActorRef =
      system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
    val goodDayGreeter: ActorRef =
      system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")

    howdyGreeter ! WhoToGreet("Akka")  // 向howdyGreeter Actor发送WhoToGreet的消息
    howdyGreeter ! Greet

    howdyGreeter ! WhoToGreet("Lightbend")
    howdyGreeter ! Greet

    helloGreeter ! WhoToGreet("Scala")
    helloGreeter ! Greet

    goodDayGreeter ! WhoToGreet("Play")
    goodDayGreeter ! Greet

    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()  // 关闭系统
  }
}
```