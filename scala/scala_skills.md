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