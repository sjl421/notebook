# Scala Skills

## 知识点

1, `case class`定义中, 编译器自动添加了不少特性和函数.

`case class P(a1: String)(a2: String)`这种定义方式, 自动添加的特性只作用于第一个
参数列表, 对第二个参数列表无效.

2, `@transient`注解表示序列化时, 不包含此属性字段

3, `class A { self => ... }`此处, `self`为`this`的别名.

4, `()`方法本质上是`apply`方法的别名, 但通常在伴生对象中, `apply`用于创建对象,
但也可以在`class`中定义, 这样可以在实例化对象中, 调用`()`方法.

`class A { def apply() ... }`, 可以使用`(new A)()`方法.

5, `private[spark] class Hello`: 表示这个类只能在包名中含有`spark`的类中访问.

6, `sys.env`是一个字串到字串的映射, 保存系统的环境变量, 如`sys.env("HOME")`可取用户主目录

7, 对于`.jar`文件, 可能通过`java.util.JarFile`类来处理

8, `Ordering[T]`是一个`trait`, 表示元素间的序的关系. 通常要实现`compare`函数, 小于为负, 大于为正, 等于为0. 除此外, 还基于`compare`定义了一些方便的函数, 如`lteq/gteq/lt/gt/equiv/max/min`. 还有`reverse`返回其逆序关系实例. 还有个`on`, 接受函数参数, 返回基于此函数的序关系实例(即将函数应用于元素比较返回值, 而非比较元素).

## 自动关闭文件

```scala
object Control {
    // A拥有close方法的对象
    def using[A <: {def close(): Unit}, B](resource: A)(f: A => B): B =
        // 以try/finally包围, 以确保文件关闭
        try {
            f(resource)
        } finally {
            resource.close()
        }
}
import Control._
using(io.Source.fromFile("example.txt")) { source =>
    for (line <- source.getLines) {
        println(line)
    }
}
// 成功打开则返回Some(字串列表), 否则返回None
def readTextFile(filename: String): Option[List[String]] =
    try {
        val lines = using(io.Source.fromFile(filename)) { source =>
            (for (line <- source.getLines) yield line).toList
        }
        Some(lines)
    } catch {
        case e: Exception => None
    }
```

## DynamicVariable

类似一个栈, 使用`withValue`来添加新值, 但这个新值只在`withValue`的`2nd`参数可见, 一旦离开`2nd`参数的作用域, 则新值相当于执行了出栈, 变量又返回了原值.

`DynamicVariable`很适合实现上下文.

```scala
val context = new scala.util.DynamicVariable[String]("000")
println(context) // -> DynamicVariable(000)
context.withValue("111") {
    println(context) // -> DynamicVariable(111)
}
println(context) // -> DynamicVariable(000)
```

## scala与java的类型转换

```scala
import scala.collection.JavaConverters._
val a = Vector(1, 2, 3)
a.asJava
val b = new java.util.ArrayList[Int]()
b.add(1)
b.add(2)
b.asScala
```

## scala.util.Properties

有大量的属性

```scala
Properties.jaavVersion
```

## Hadoop HDFS

从`hadoop`文件系统中获取文件:

```scala
import org.apache.hadoop.fs.{FileSystem, FileUtils, Path}
import org.apache.hadoop.conf.Configuration
val s = "hdfs://test57/user/ivic/README.md"
// 方式1
val uri = new URI(s)
val fs = FileSystem.get(uri, conf)  // 指sc.hadoopConfiguration
// 方式2
val path = new Path(s)
val fs2 = path.getFileSystem(conf)
fs.isFile(path) // 判断是否是文件
fs.isDirectory(path) // 判断是否是目录
fs.open(path) // 打开文件获取输入流
fs.listStatus(dirPath)  // 列出目录所有文件的状态
fs.getFileStatus(path)  // 获取指定文件的状态
```

## Hadoop User

```scala
import org.apache.hadoop.security.UserGroupInformation
val user = UserGroupInformation.getCurrentUser  // 获取当前用户
user.getUserName; user.getShortUserName  // 用户名
user.getGroupNames; user.getPrimaryGroupName  // 组名
```

## Hadoop Conf

```scala
import org.apache.hadoop.conf.Configuration
val hconf = new Configuration  // 实例化配置, 会默认载入classPath中的xml配置文件
val hconf = new Configuration(false)  // 不载入配置文件的配置
hconf.get("fs.defaultFS")  // 获取配置文件中定义的属性值
hconf.set("property.name", "value")  // 设置属性
```

## scala xml

`scala`在语言层面提供对`XML`的支持.

```scala
import scala.xml.Node
val a = <a>1</a>  //=> 能自动转换为xml.Node
val b = <b>{a}</b>  //=> xml嵌套
case class X(name: String)
val x = X("lizp")
val c = <c>{x.name}</c>  //=> 引用外部变量
```

## 获取包名

```scala
obj.getClass.getPackage.getName
```

## {}的本质

```scala
List(1,2).map { i => println("Hi"); i+1 }
List(1,2).map { println("Hi"); _ + 1 }
```

上述代码执行结果并不相同, 前者会对每个元素调用`{}`内的匿名函数, 因此会打印两次`Hi`. 但后者则只打印一次.

误区在于, 我们认为`{}`内的语句即`map`的参数, 但实际上, `{}`只是包括多个语句而已, 其返回值才是`map`的参数, 在这里`_+1`, 使用点位符实现的匿名函数才是`map`参数.

用占位符语法定义的匿名函数的范围只延伸到含有下划线的表达式.

## 模式匹配中的大写变量

```scala
var (a, b, c) = (1, 2, 3)
var (A, B, C) = (1, 2, 3)  // 编译错误
```

多变量赋值是基于模式匹配, 而在模式匹配中, 大写变量是静态标识符, 用于进行常量匹配. 小写变量才是进行变量赋值的.

即, 不能用多变量赋值为大写变量赋值
