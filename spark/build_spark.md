# 构建Spark

获取源码: `git clone -b branch-2.2 https://github.com/apache/spark`

构建:

* 简单`build/mvn -DskipTests -Phadoop-2.7 clean package`
* 完整`build/mvn -DskipTests -Phadoop-2.7 -Pyarn -Pmesos -Phive-thriftserver clean package`
* 子模块`build/mvn -pl :spark-streaming_2.11 clean package`

测试: `build/mvn test`

## 详细

* `-Phadoop-2.7`, 指定使用`hadoop2.7`, 默认使用`2.6.5`
* `-Dhadoop.version=2.7.3`, 可指定更具体的版本号

以下3个, 会编译特定的模块:

* `-Pyarn`, 编译`yarn`
* `-Pmesos`, 编译`mesos`
* `-Phive-thriftserver`, 编译`hive-thriftserver`

在指定了`hadoop`的版本后, 相当于确定了依赖的版本号, 会通过`mvn`下载指定版本的`jar`包.

在`2.2`分支中, 默认`hadoop-2.6.5`, `java-1.8`, `hive-1.2.1.spark2`, `scala-2.11.8`

可指定`scala`的版本, `-Dscala.version=2.12.2`, `-Dscala.binary.version=2.12`. 不过, 考虑到`scala`的二进制不兼容, 很多包可能没有针对最新版本的`scala`进行编译.

`pom.xml`文件

* `<modules>`标签定义默认编译的子模块, 可修改此来决定编译哪些模块.
* `<properties>`标签定义默认的属性, 主要是各依赖的版本, 通过`-D`指定.
* `<profiles>`标签定义可额外编译的子模块, 通过`-P`指定.

`build/mvn scala:cc`, 使用`scala-maven-plugin`插件进行增量编译, 只能在子模块内部使用. 考虑到模块之间的依赖性, 需要先在根目标中执行`build/mvn install`将已编译好的模块安装到全局依赖中.

有一部分测试可能失败, 因为`bin`, `sbin`目录中某些脚本没有`x`权限.