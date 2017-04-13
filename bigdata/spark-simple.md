# 最简单的spark应用代码

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
