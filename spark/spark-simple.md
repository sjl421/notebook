# 最简单的spark应用代码

## 简单的spark core代码

```scala
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object spark-simple{
	val conf = new SparkConf()  //生成SparkConf
		.setAppName(appName)  //设置应用名
		.setMaster(master)  //设置master链接
	val sc = new SparkContext(conf)  //由SparkConf生成SparkContext
	val file = sc.textFile("path")  //读文件生成RDD
	...  //RDD的转换和结算
	sc.stop()  //停止
}
```

```scala
name := "spark-simple"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.1.0" % "provided"
)

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
```

```scala
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.4")
```

## 简单的spark sql代码

```scala
import org.apache.spark.sql.SparkSession
object SparkSimple {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession
            .builder()
            .appName("Spark SQL basic example")  //应用的名称
            .config("spark.some.config.option", "some-value")  //应用额外的配置
            .getOrCreate()  //获取会话实例
        val jdbcDF = spark.read  // 通过jdbc连接数据库
            .format("jdbc")
            .option("driver", "org.postgresql.Driver")  //驱动名
            .option("url", "jdbc:postgresql://ip:port/db?user=uname&password=upwd")
            .option("dbtable", "abc")  //表名
            .load()

        spark.stop()
    }
}
