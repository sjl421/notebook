# Spark MLlib

有两套`API`, 基于`RDDs`的和基于`DataFrame`.

在`2.x`版本, `RDDs-based`不再增加新特性, `DataFrame-based`则应该会在`2.2`中达到
跟`RDDs-based`相同的特性, 并在`3.0`中删掉`RDDs-based API`.

有4个主要概念:
* `DataFrame`: 数据帧
* `Transformer`: 将一个`DataFrame`转换为另一个`DataFrame`. 如`feature transformer`,
  将一列数据映射为另一列; 如`learning model`, 将预测值附加到原`DataFrame`. 通常, 
  实现`transform()`方法
* `Estimators`: 从一个`DataFrame`生成一个`Transformer`. 通常, 实现`fit()`方法.
* `Pipeline`: 将多个`Transformer`和`Estimators`连起来, 生成`ML`工作流.
* `Parameter`: 共享通用的`API`用来指定参数.

机器学习, 大概分4类: 分类, 回归, 聚类, 降维. 

分类和回归都使用带标签的数据, 从样本中学习到新的模型, 再用训练的模型应用于新的数据, 
进行分类或预测. 学习的过程, 即从`DataFrame`学习`Transformer`. 应用模型的过程, 即
应用`Transformer`, 将不带标签的`DataFrame`转换为带标签的`DataFrame`. (标签即为类
别或预测值)

聚类和降维都使用不带标签的数据, 聚类将样本数据分类, 似乎没有应用模型的过程, 降维差
不多, 将多维数据转换为低维数据, 即`Transformer`, 将一个`DataFrame`转换为另一个`DataFrame`.

`Transformer`和`Estimator`都是无状态的.
