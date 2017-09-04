# Spark RDD Function

* `sc.textFile(fpath)`, 读文件
* `sc.parallelize(scala_seq)`, 创建`rdd`
* `sc.makeRDD`, 可指定分区的优先计算位置
* `sc.saveAsTextFile()`, 写文件

`rdd`的分区数与元素数量没有关系, 通常为`节点数*节点cpu核心数`, 即集群总的`cpu`核数. 有的分区为空, 并没有元素. 容易理解, 考虑到`spark`的并行度等于分区数, 每个`task`在相应的分区运行, `task`为线程, 真正的线程数为`cpu`核心数.

大体上说, 生成不同的`RDD`, 意味着分区数的变化和数据在分区间的传输. 未确认, 但目前可以认为`MapPartitionsRDD`分区数不变且数据不会在分区间传输, `ShuffledRDD`分区数不变, 但数据在分区间发生了传输.

## 普通RDD

* `toDebugString`可查看`rdd`谱系, 事实上, 输出的谱系图通过缩进表示`stage`. 一定程度上可以看出`DAG`图, 也可以看到`ShuffledRDD`是`stage`的分割点. 强调下, 不是说`reduceByKey`这样生成`ShuffledRDD`的`API`是`stage`的分割点, 而是说`ShuffledRDD`是分割点, 因为`reduceByKey`可能也不生成`ShuffledRDD`, 而是别的`RDD`.

补充: 也不是`ShuffledRDD`, 而是在两个有`shuffle`依赖的`RDD`间是`Stage`的分隔点. 除了`ShuffledRDD`还有别的`RDD`也会产生`shuffle`依赖.

非常有趣的是, 你可以在`4040`页面查看应用的`DAG Visualization`, 会展示`stage`和执行流程.

`RDD`有不同的子类型, 以下`rddx`除非特别声明, 默认全为`MapPartitionsRDD`.

`RDD`的5个主要属性:

* `partitions`, 分区集合
* `compute`, 计算每个分区的函数
* `dependencies`, 依赖关系
* `partitioner`, 分区方式
* `preferredLocations(p)`, 分区的位置偏好

一个间接的方法可以知道, 一个`RDD`的各个分区都包含那些元素:

```scala
def getEle[T](pidx: Int, piter: Iterator[T]) = {  // Iterator[T] => Iterator[U]
   val m = collection.mutable.Map(pidx -> "")
   piter.foreach(ele => m(pidx) += ele.toString + "||")
   m.iterator
}  //在REPL中, :paste开启paste模式, 直接粘贴即可
rdd.mapPartitionsWithIndex(getEle).collect
```

另一个有趣的方式, 用于探索`fold`和`foldByKey`的执行过程, 此处使用了`aggregate`和`aggregateByKey`两个等价
却能返回不同类型的函数:

```scala
// rdd1: Seq(1 to 50)
rdd1.mapPartitionsWithIndex(getEle).collect
// Array((0,"1 2 3 4 5 6 7 8 9 10 11 12 "),   //分区0
//       (1,"13 14 15 16 17 18 19 20 21 22 23 24 25 "),   //分区1
//       (2,"26 27 28 29 30 31 32 33 34 35 36 37 "),   //分区2
//       (3,"38 39 40 41 42 43 44 45 46 47 48 49 50 "))  //分区3
rdd1.aggregate((1, "1:"))(
     (acc, v) => (acc._1 + v, acc._2 + "+" + v),  //用+号连接分区内的聚合
     (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + "-" + acc2._2)  //用-号连接分区间的聚合
)
//=> (1280,        1:  //可见分区间聚合时, 也会加上这个初始值1  (treeAggregate不加1:)
//           -1:+26+27+28+29+30+31+32+33+34+35+36+37
//           -1:+1+2+3+4+5+6+7+8+9+10+11+12
//           -1:+13+14+15+16+17+18+19+20+21+22+23+24+25
//           -1:+38+39+40+41+42+43+44+45+46+47+48+49+50)
//+ rdd17: Array((a,1), (b,2), (a,3), (b,8), (c,4), (a,5))
rdd17.mapPartitionsWithIndex(getEle).collect
// Array((0,"(a,1) "),   (1,"(b,2) (a,3) "),   (2,"(b,8) "),   (3,"(c,4) (a,5) "))
 rdd17.aggregateByKey((1, "1:"))(
     (acc, v) => (acc._1 + v, acc._2 + "+" + v.toString),  //用+号连接分区内的聚合
     (a1, a2) => (a1._1+a2._1, a1._2 + "-" + a2._2)).collect  //用-号连接分区间的聚合
// Array((a,(12,1:+1 - 1:+3 - 1:+5)), (b,(12,1:+2 - 1:+8)), (c,(5,1:+4)))
// 可见, 因为初始值是针对同键元素聚合时相加的, 因此在分区间聚合时, 不加初始值
```

在生成`RDD`时, 可能通过`partitionBy`指定分区器:

```scala
import org.apache.spark.HashPartitioner
// rdd17:  Array((a,1), (b,2), (a,3), (b,8), (c,4), (a,5))
// 默认分区: Array((0,"(a,1) "), (1,"(b,2) (a,3) "), (2,"(b,8) "), (3,"(c,4) (a,5) "))
val rdd017 = rdd17.partitionBy(new HashPartitioner(4))  // 4是分区的个数
// hash分区: Array((0,""), (1,"(a,1) (a,3) (a,5) "), (2,"(b,2) (b,8) "), (3,"(c,4) "))
// 对键取hash值, 再对4取模, 值相同则同分区
// hash分区器, 取决于hash函数, 可能导致数据分区的不均匀
import org.apache.spark.RangePartitioner

```

### transformation

* `map`, `flatMap`, `mapPartitions`, `mapPartitionsWithIndex`, 生成`MapPartitionsRDD`

```scala
val rdd1 = sc.parallelize(Seq(1,2,3,4))  // ParallelCollectionRdd
val rdd2 = rdd1.map(_ + 1)  //=> Seq(2, 3, 4, 5)
val rdd3 = sc.parallelize(Seq("hello world", "god bless me"))  // 虽然只有两个元素, 但事实上有4个分区
val rdd4 = rdd3.flagMap(_.split(" "))  //=> Seq("hello", "world", "god", "bless", "me")
rdd1.mapPartitions(_.map(_ + 1))  //参数为Iterator[T] => Iterator[U]的函数, 应用于RDD的每个分区
```

* `zip`, `zipPartitions`, 生成`ZippedPartitionsRDDx`, 其中`x`为数字, 要求父`RDD`的分区数相同. `zip`创建的是两`RDD`元素元组的`RDD`, 而`zipPartitions`则随意, 只要`f`的参数为`Iterator`即可.
* `glom`, 生成`MapPartitionsRDD`

```scala
// rdd1: Seq(1,2,3,4), rdd01: Seq("a", "b", "c", "d")
rdd1.zip(rdd01)  //=> Array((1,a), (2,b), (3,c), (4,d))  //ZippedPartitionsRDD2
rdd1.glom()  //=> Array(Array(1), Array(2), Array(3), Array(4))  //T => Array[T]
```

* `filter`: 生成`MapPartitionsRDD`, 应用于`pair rdd`时, 因为不会改变键, 所以会设定子`RDD`的分区方式为父`RDD`.

```scala
// rdd4: Seq("hello", "world", "god", "bless", "me")
val rdd5 = rdd4.filter(_.length < 5)  //=> Seq("god", "me")
```

* `union`, 若求并集的`RDDs`的分区器都定义了且相同, 返回`PartitionerAwareUnionRDD`, 否则返回`UnionRDD`.

* `intersection`, `subtract`, `cartesian`: 并, 交, 差, 积, 注: 类型相同

```scala
// rdd1: Seq(1,2,3,4), rdd2: Seq(2, 3, 4, 5)
//union 分区数增加, 为两rdd的分区数和, 所以不同于数据的并集去重
val rdd6 = rdd1.union(rdd2)  //=> Seq(1,2,3,4,2,3,4,5)  // UnionRDD
val rdd7 = rdd1.intersection(rdd2)  //=> Seq(4,2,3)
val rdd8 = rdd1.subtract(rdd2)  //=> Seq(1)
// cartesian 分区数增加, 为两rdd分区数积
val rdd9 = rdd1.cartesian(rdd3)  //=> Seq((1,"hello world"), (1,"god bless me"),   // CartesianRDD
                                 //       (2,"hello world"), (2,"god bless me"),
                                 //       (3,"hello world"), (3,"god bless me"),
                                 //       (4,"hello world"), (4,"god bless me"))
```

* `distinct`: 去重
* 重复数据可能分散在不同的`partition`里面, 需要`shuffle`来进行`aggregate`后再去重. 然而`shuffle`要求数据类型是`<K, V>`, 如果原始数据只有`key`, 那么需要补充成`<K, Null>`, 这个补充过程由`map`完成, 生成`MappedRDD`, 然后调用`reduceByKey`来进行`shuffle`, 在`map`端进行`combine`, 然后`reduce`进一步去重, 生成`MapPartitionsRDD`, 最后将`<K, null>`还原成`K`, 仍然由`map`完成, 生成`MappedRDD`.

```scala
// rdd6: Seq(1,2,3,4,2,3,4,5)
val rdd10 = rdd6.distinct  //=> Array(1, 2, 3, 4, 5)
```

* `sample`: 采样

```scala
val rdd11 = sc.parallelize(1 to 50)  // 50个元素, 不重复
val rdd12 = rdd11.sample(false, 0.4)  //=> 约为50*0.4个元素, 无放回抽样  //PartitionwiseSampledRDD
val rdd13 = rdd11.sample(true, 0.4, 32)  //=> 有放回抽样, 随机数种子32
```

* `groupBy`: 根据函数返回值对数据分组, 可生成`pRDD`

```scala
// rdd4: Seq("hello", "world", "god", "bless", "me")
// 分区数不变, 但发生元素在分区间的移动
val rdd14 = rdd4.groupBy(_.length)  //=> Array((5,CompactBuffer(hello, world, bless)),  //ShuffledRDD
                                    //         (2,CompactBuffer(me)),
                                    //         (3,CompactBuffer(god)))
```

* `repartition`: `shuffle`为`true`的`coalesce`
* `coalesce`: 可指定是否`shuffle`, 生成`CoalescedRDD`

```scala
// rdd11: Seq(1,2,3,...,50)
rdd11.getNumPartitions  //=> 4
val rdd15 = rdd11.repartition(8)
rdd15.getNumPartitions  //=> 8
rdd11.coalesce(8).getNumPartitions  //=> 4  默认不shuffle, 则不能扩大分区数
```

* `getNumPartitions`: 获得`RDD`的分区数

### action

`action`本质上也会先在每个分区上进行计算, 再将结果传到`Driver`进行汇总.

* `reduce`, `fold`, `aggregate`, `treeAggregate`: 聚合, `aggregate`在分区间聚合时, 多进行了初始值的`combOp`;`aggregate`会将每个分区的结果全部传给`driver`, 并在`driver`端做聚合, 而`treeAggregate`, 则会在部分分区上做局部聚合, 再将聚合的结果传给`driver`, 这个局部聚合可能有多层, 构成一棵树(根是`driver`, 叶是`rdd`的分区)

```scala
// rdd11: Seq(1,2,3,...,50)
rdd11.reduce(_ + _)  //=> 1275
rdd11.fold(0)(_ + _)  //=> 1275
rdd11.fold(1)(_ + _)  //=> 1280, 注意: 此处同scala标准集合库的fold表现不同,
                                    // 先在每个分区fold, 再在分区间fold
                                    // 多"分区数+1" 个数
rdd15.fold(1)(_ + _)  //=> 1284, rdd15分区数8
rdd11.aggregate((0,0))(  # 指定初始值
     (acc, v) => (acc._1 + v, acc._2 + 1),  // 分区内的聚合函数
     (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
)  // 分区间的聚合函数, (1275, 50), 实现累加和计数
```

* `first`: 返回`rdd`第1个元素
* `persist`, `cache`: 持久化, `cache`相当于无参的`persist`, 默认缓存到内存
* `count`, `countByValue`

`checkpoint`方法也能使`RDD`存储到磁盘, 不同的是, `checkpoint`会删除`RDD`的`Lineage`, 且需要调用`setCheckpointDir(path)`来设置保存`RDD`数据的路径.

```scala
// rdd6: Seq(1,2,3,4,2,3,4,5)
rdd6.count  //=> 8
rdd6.countByValue  //=> Map(5 -> 1, 1 -> 1, 2 -> 2, 3 -> 2, 4 -> 2)
```

* `collect`: 返回`rdd`所有元素
* `take`, `top`, `takeOrdered`, `takeSample`

```scala
val rdd16 = sc.parallelize(Seq(1,9,2,8,3,7,4,6,5,0))
rdd16.take(2)         //=> Array(1, 9)
rdd16.top(2)          //=> Array(9, 8), 要排序, 默认返回最大
rdd16.takeOrdered(5)  //=> Array(0, 1, 2, 3, 4), 默认返回最小
rdd16.takeSample(false, 4, 4)  //=>  Array(7, 6, 1, 3), 不放回, 4个元素, 种子4
// 指定种子可以每次返回相同的元素
```

* `foreach`: 对每个元素执行操作, 如输出或写文件

## Pair RDD

`Pari RDD`可以应用所有普通`RDD`的`API`, 不同的是, 函数参数为`pair`.

### transaction

* `reduceByKey`, `foldByKey`, `aggregateByKey`, `combineByKey`:  这几个操作都生成`shuffledRDD`(并非一定会生成, 可能生成别的), 意即计算时会发生数据的移动, 取决于元素的分布, 整体来看都比较耗时. 返回`hash`分区
* `reduceByKey`默认在`map`端开启`combine`, 因此在`shuffle`之前先通过`mapPartitions`操作进行`combine`, 得到`MapPartitionsRDD`, 然后`shuffle`得到`ShuffledRDD`, 然后再进行`reduce`.

```scala
val rdd17 = sc.parallelize(Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5)))
// 相像成先把同键的元素的值构成集合,再对集合reduce
val rdd18 = rdd17.reduceByKey(_ + _)  //=> Array((a,9), (b,10), (c,4))
rdd17.foldByKey(0)(_ + _)  //=> Array((a,9), (b,10), (c,4))
rdd17.foldByKey(1)(_ + _)  //=> Array((a,12), (b,12), (c,5)),  注意, 这里依然体现了分区计算, 确定你明白初始值的选择
// 在每个分区应用1st函数, 因此同键的N个分区都会多加1, 在分区间应用2nd函数, 此处不加1
val rdd19 = rdd17.aggregateByKey((0,0))(  // 初始值
     (acc, v) => (acc._1+v, acc._2 + 1),  // 同分区的同键的值, 跟迭代值合并
     (acc1, acc2) => (acc1._1 + acc2._1, acc1._2+acc2._2)  // 分区间的同键的值合并
)  // [K,V] => [K,C], Array((a,(9,3)), (b,(10,2)), (c,(4,1)))
// 因为不指定初始值, 所以acc要显示标记类型, 因为没法推断
val rdd20 = rdd17.combineByKey(  // 相比aggregateByKey, 无初始值
     v => (v, 1),  // 同分区, 首次遇到某键, 对其值执行函数
     (acc: (Int,Int), v) => (acc._1 + v, acc._2 + 1),  // 同分区, 再次遇到某键, 对其值执行函数
     (acc1: (Int,Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2+acc2._2)  // 分区间合并
)  // [K,V] => [K,C], Array((a,(9,3)), (b,(10,2)), (c,(4,1)))
```

* `mapValues`, `flatMapValues`: 传入的函数确定不会改变键, 因此设定结果`RDD`的分区方式同父`RDD`. 这不同于`map`, 其处理的元素是`pair`, `spark`不会分析传入的函数是否改变了键, 不设定结果`RDD`的分区方式.

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
rdd17.mapValues(_ + 1)  //=> Array((a,2), (b,3), (a,4), (b,9), (c,5), (a,6))
val rdd21 = sc.parallelize(Seq(("a", "hello world"), ("b", "god bless me")))
rdd21.flatMapValues(_.split(" "))  //=> Array((a,hello), (a,world), (b,god), (b,bless), (b,me))
```

* `groupByKey`: `[K,V]->[K,Iterable[V]]`, 生成`ShuffledRDD`, `hash`

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
val rdd22 = rdd17.groupByKey  //=> Array((a,CompactBuffer(1, 3, 5)),
                                                    //                 (b,CompactBuffer(2, 8)),
                                                    //                 (c,CompactBuffer(4)))
```

* `keys`, `values`

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
val rdd23 = rdd17.keys  //=> Array(a, b, a, b, c, a)
val rdd24 = rdd17.values  //=> Array(1, 2, 3, 8, 4, 5)
```

* `sortByKey`: 生成`ShuffledRDD`, 设定结果`RDD`的分区方式为`range`.

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
val rdd25 = rdd17.sortByKey()  //=> Array((a,3), (a,5), (a,1), (b,8), (b,2), (c,4))  //ShuffledRDD
rdd17.sortByKey(false)  //降序
implicit val xxx = new Ordering[Int] {
    override def compare(a: String, b: String) = a.toLowerCase.compare(b.toLowerCase)
}  //实例化Ordering, 自定义排序方法, Ordering的类型参数为rdd的键类型
```

* `subtractByKey`, 删除同键的元素

```scala
val rdd26 = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3)))
val rdd27 = sc.parallelize(Seq(("b", 2), ("b", 3), ("d", 9)))
val rdd28 = rdd27.subtractByKey(rdd26)  //=> Array((d,9))  //SubtractedRDD
```

* `join`, `rightOuterJoin`, `leftOuterJoin`

`join`对两个`rdd`的键通过分区器计算分组, 将同组的通过网络传到同一台机器上, 再在那台机器上对所有同键的
记录进行连接操作. 通常的分区器有: `Hash`, `Range`. 会自动设定结果`RDD`的分区方式为`hash`.

```scala
val rdd29 = rdd26.join(rdd27)  //=> Array((b,(2,2)), (b,(2,3))), 同键
val rdd30 = rdd26.rightOuterJoin(rdd27)  //=> Array((d,(None,9)), (b,(Some(2),3)), (b,(Some(2),2))), 右RDD的键一定在
val rdd31 = rdd26.leftOuterJoin(rdd27)  //=> Array((a,(1,None)), (b,(2,Some(3))), (b,(2,Some(2))), (c,(3,None))), 左RDD的键一定存在
```

* `cogroup`, 设定结果`RDD`的分区方式为`hash`. 别名函数`groupWith`.

```scala
val 32 = rdd26.cogroup(rdd27)  //=> Array((d,(CompactBuffer(),CompactBuffer(9))),
                               //         (a,(CompactBuffer(1),CompactBuffer())),
                               //         (b,(CompactBuffer(2),CompactBuffer(3, 2))),
                               //         (c,(CompactBuffer(3),CompactBuffer())))
```

### Action

* `countByKey`: 区别于应用普通`RDD`的`countByValue`

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
rdd17.countByKey  //=>Map(a -> 3, b -> 2, c -> 1)
rdd17.countByValue  //=> Map((a,5) -> 1, (b,2) -> 1, (a,3) -> 1, (b,8) -> 1, (c,4) -> 1, (a,1) -> 1)
```

* `lookup`: 找到对应键的值的集合

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
rdd17.lookup("a")  //=> WrappedArray(1, 3, 5)
```

* `collectAsMap`: 转换成`Map`类型, 注意, `Map`的键不能重复, 因此同键的元素值只能取1个

```scala
// rdd17: Seq(("a", 1), ("b", 2), ("a", 3), ("b", 8), ("c", 4), ("a", 5))
rdd17.collectAsMap  //=> Map(b -> 8, a -> 5, c -> 4)
```