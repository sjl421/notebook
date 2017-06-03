# Scala Skills

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