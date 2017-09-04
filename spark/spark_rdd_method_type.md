# Spark RDD Method and Result RDD Type

整理`Spark`所有的`RDD`方法:

* 产生的`RDD`的不同类型
* 前后`RDD`间的依赖关系
* 前后`RDD`的分区变化

1, `parallelize`, `makeRDD`:

`ParellelCollectionRDD`

2, `textFile`: `hadoopFile` -> `map`

`HadoopRDD` -> `MapPartitionsRDD`

3, `map`, `flatMap`, `mapPartitions`, `mapPartitionsWithIndex`:

`MapPartitionsRDD`

4, `zip`: `zipPartitions`

`ZippedPartitionsRDDx`

5, `glom`:

`MapPartitionsRDD`

6, `filter`:

`MapPartitionsRDD`

7, `union`:

(分区器相同) -> `PartitionerAwareUnionRDD`

-> `UnionRDD`(`RangeDependency`)

8, `intersection`: `map` -> `cogroup`(`mapValues`) -> `filter` -> `keys`

`MapPartitionsRDD` -> `CoGroupedRDD`(`MapPartitionsRDD`) -> `MapPartitionsRDD` -> `MapPartitionsRDD`

9, `subtract`: `map` -> `subtractByKey` -> `keys`

`MapPartitionsRDD` -> `SubtractedRDD` -> `MapPartitionsRDD`