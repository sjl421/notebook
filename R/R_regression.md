# R用于回归分析

`R`语言在统计分析上提供了大量好用的函数,用户需要做的就是`获取数据 -> 读入数据 ->
调用函数得到模型 -> 调用函数优化模型 -> 输出模型的统计信息 -> 画图`. 除了获取数据
外, `R`的操作只需要5个函数即可. 重点是你得找到适用于你的分析模型的函数.

## 常用函数

假设已有数据`data`:
```R
help(function)  #返回函数的帮助信息
library(libname)  #载入库
dim(data)  #返回维度信息,即数据的形状,在结果调用[n]获取第n轴的数据量
names(data)  #返回数据所有的列名,调用[n]返回第n列名称,从1索引
str(data)  #返回数据的结构信息,包括可获取的单列名称,列数据类型等
summary(data)  #返回对象的统计信息,此处返回数据每列的最小/最大,中位数等
rep("str", 3)  #返回向量,3个"str"元素
```

画图:
```R
plot(x, y, xlab='x', ylab='y') 
```

## 逐步回归

以葡萄酒质量分析为例, 现假设已经存在文件`winequality-white.csv`,其中有需要分析的数据:

```R
# 读入数据
wine_quality <- read.table("winequality-white.csv", header=TRUE, sep=";")
# 回归拟合得到模型
tlm <- lm(quality ~ fixed.acidity + volatile.acidity + citric.acid + residual.sugar 
+ chlorides + free.sulfur.dioxide + total.sulfur.dioxide + density + pH + sulphates 
+ alcohol, data=wine_quality)
# 应用双向逐步回归
tstep <- step(tlm, direction="both")
# 输出统计信息
summary(df)  #显示数据框的相关数据的统计
coefficients(tstep)  #返回模型的回归系数
confint(tstep)  #返回系数的置信区间
fitted(tstep)  #返回模型的拟合值
AIC(tstep)  #输出aic信息量
```
