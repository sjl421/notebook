# Spark SQL

## 与hive交互

`Spark SQL`可以将`hive`数据库作为数据源, 主要体现在可以连接`hive`的`metastore`进行读写.

对于`hive`的理解: `hive`的数据分为两部分, 一部分是存储数据库数据表的元信息, 即`metastore`; 另一部分是数据的存储文件. `metastore`有三种模式: 嵌入(`derby`), 本地, 远程.

* 嵌入是默认的模式, 在不作任何配置的情况下, 启动后会在当前目录生成`metastore_db`的目录, 但只用于单元测试.
* 在本地模式下, 将`metastore`存储在`postgres/mysql`关系型数据库中(`postgres/mysql`运行在哪台主机无所谓), 直接连接远程数据库`metastore`进行读写.
* 对于远程模式, 需要启用`thriftserver`服务, 通过`bin/beeline`客户端连接`thriftserver`来对存储在`postgre/mysql`中的`metastore`进行读写. `thriftserver`支持并发多客户端连接, `bin/beeline`则支持多用户连接.

注: 使用`postgres/mysql`数据库. 一是要将相应的驱动`jar`文件放到`SPARK_HOME/jars`目录中, 二是要在配置中写明数据库的连接信息, 包括用户名密码.

注: 有个坑, `hive`后期的版本在连接`metastore`前要进行`bin/schematool -initSchema -dbType derby|mysql|postgres`初始化, 而`spark`本身并不能也没提供工具进行初始化(针对远程模式, 其对嵌入模式下可以初始化).

`sbin/start-thriftserver.sh`是开启`thrift jdbc/odbc`服务, 这样可以使用`bin/beeline`来连接, `!connect jdbc:hive2://localhost:10000`, 用户名为当前系统用户, 密码为空. 若远程连接, 则替换`localhost`为开启`thriftserver`的主机地址.

```scala
spark.read.table("people")  // 从表中读入DataFrame
peopleDF.write.saveAsTable("people")  // 将DataFrame永久保存到表中
```

再从`bin/beeline`中, `!connect`库:

```sql
show tables;  # 显示当前库中的表
describe tbname;  # 显示表的结构信息
select * from tbname;  # 显示表的数据
insert into tbname values (v1, v2)  # 向表中插入一条数据
```

`bin/spark-sql`命令可以运行`hive metastore`服务并从命令行执行查询, `spark-sql`并不连接`thrift jdbc server`. 好像可以执行`beeline`连接后的所有操作, 但一定要设置`conf/log4j.properties`文件的日志级别, 不然每条命令的执行会输出好多`INFO`信息.

`spark-sql/beeline`的区别??

## 基本操作

```scala
val df = spark.read.json("examples/src/main/resources/people.json")
df.show()
df.printSchema()
df.select("name").show()  //select name from df;
df.select($"name", $"age" + 1).show()  // select name, age+1 from df;
df.filter($"age" > 21).show()  // select * from df where age > 21;
df.groupBy("age").count().show()  // select age, count(age) from df group by age;
```

```scala
df.createOrReplaceTempView("people")  // 将df注册为临时的视图people
spark.sql("SELECT * FROM people").show()  // 在SparkSession上执行sql语句
df.createGlobalTempView("people")  // 将df注册为全局的视图people
spark.sql("SELECT * FROM global_temp.people").show()
spark.newSession().sql("SELECT * FROM global_temp.people").show()
```

`TempView`的作用域是`session`, 创建它的`SparkSession`终止的话, `TempView`也会消失.

`GlobalTempView`的作用域是`application`, 可以在所有的`SparkSession`中共享, 绑定在`global_temp`数据库上.

至于一个应用中为什么需要多个`session`尚不明白.

`Dataset`使用特殊的序列化`Encoder`, 可以在执行类似`filter`, `sort`, `hash`等操作时不用将字节反序列化为对象.

```scala
case class Person(name: String, age: Long)
val caseClassDS = Seq(Person("Andy", 32)).toDS()  // 创建Dataset
caseClassDS.show()
val primitiveDS = Seq(1, 2, 3).toDS()  // 基础类型
primitiveDS.map(_ + 1).collect() // Returns: Array(2, 3, 4)
val path = "examples/src/main/resources/people.json"
val peopleDS = spark.read.json(path).as[Person]  // 将DataFrame转化为Dataset
peopleDS.show()
```

关于`DataSet`和`DataFrame`:

简单地说, `DataFrame`类型是`Dataset[Row]`的别名. 因此, 想从`DataFrame`即`Dataset[Row]`创建`Dataset[People]`, 需要提供`People`类.

创建`Dataset`或`DataFrame`

* 集合: `toDS`, `toDF`
* 转换已经有的`RDD`
  * 反射, 依赖case类的定义
  * 构建`Schema`

```scala
val peopleDF = spark.sparkContext
  .textFile("examples/src/main/resources/people.txt")
  .map(_.split(","))  // RDD[Array[String]]
  .map(attributes => Person(attributes(0), attributes(1).trim.toInt))  // RDD[Person]
  .toDF()  // 转换为DataFrame
peopleDF.createOrReplaceTempView("people")
val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
teenagersDF.map(teenager => "Name: " + teenager(0)).show()
teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()  // 返回列名或列索引的值
implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]  // 定义Dataset[Map[K,V]]的序列化器
teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect()
```

* 创建`RDD[Row]`
* 创建`StructType`
* 通过`createDataFrame`, 应用`StructType`到`RDD[Row]`

```scala
// 创建RDD[Row]
val peopleRDD = spark.sparkContext.textFile("examples/src/main/resources/people.txt")
val rowRDD = peopleRDD
  .map(_.split(","))
  .map(attributes => Row(attributes(0), attributes(1).trim))  // RDD[Row]
// 构建StructType
val schemaString = "name age"
val fields = schemaString.split(" ")
  .map(fieldName => StructField(fieldName, StringType, nullable = true))
val schema = StructType(fields)
// 生成DataFrame
val peopleDF = spark.createDataFrame(rowRDD, schema)
```

其实, 利用`createDataFrame`有多种方式来创建`DataFrame`

```scala
val peopleRDD = spark.sparkContext.textFile("examples/src/main/resources/people.txt")
spark.createDataFrame(
    p.map(_.split(",")).map(att => (att(0), att(1).trim.toInt))  // 由RDD[String] -> RDD[Array[String]] -> RDD[(String, Int)]
).toDF("name", "age")  // 赋予Schema
```

```scala
def createDataFrame(rowRDD: RDD[Row], schema: StructType): DataFrame
// 就目前已知的, 考虑到表有多列, 通常A应该是Tuple类型, 或case class
def createDataFrame[A <: Product](data: Seq[A])(implicit arg0: scala.reflect.api.JavaUniverse.TypeTag[A]): DataFrame
def createDataFrame[A <: Product](rdd: RDD[A])(implicit arg0: scala.reflect.api.JavaUniverse.TypeTag[A]): DataFrame
```

`DataFrameReader`: `spark.read.csv/json/parquet/orc`, 通过`option`方法指定选项
`DataFrameWriter`: `spark.read.csv/json/parquet/orc`, 通过`option`方法指定选项, 通过`mode`方法指定写选项

```scala
val usersDF = spark.read.load("examples/src/main/resources/users.parquet")  // parquet
usersDF.select("name", "favorite_color").write.save("namesAndFavColors.parquet")
val peopleDF = spark.read.format("json").load("examples/src/main/resources/people.json")  // json
peopleDF.select("name", "age").write.format("parquet").save("namesAndAges.parquet")
peopleDF.write.
val sqlDF = spark.sql("SELECT * FROM parquet.`examples/src/main/resources/users.parquet`")  // 直接执行
val p2 = spark.read.option("header", true).option("inferSchema", true).csv("people.csv")  // csv
```

`jdbc`连接数据库:

```scala
val jdbcDF = spark.read
    .format("jdbc")
    .option("driver", "org.postgresql.Driver")
    .option("url", "jdbc:postgresql://192.168.7.57:5432/sql_manage?user=sqluser&password=sqluser")
    .option("dbtable", "jcsqltb")
    .load()
```

`.rdd`方法可取出`DataFrame`中的`rdd`数据.