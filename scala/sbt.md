# sbt

`scala`的项目管理工具.

`sbt`的安装包并不是全部，启动脚本后还需要下载大量的`jar`包。有必要在`sbt/conf/sbtopts`
文件中设定`-scala-home scala_home`和`-scala-version 2.11.8`两个选项，不然默认使用的
`scala`版本是`2.10.6`，比较老。事实上你也可以设置`-java-home`选项。

## 默认目录结构

```sh
build.sbt
project/
src/
  main/
    scala/
    java/
    resources/
  test/
    scala/
    java/
    resources/
```

除此外, 忽略所有别的目录, 和所有隐藏目录. 

`project`目录放置额外的配置信息. 相关的输出文件在`target/`目录, 即应该加入`.gitignore`.

有关`build.sbt`:
```scala
lazy val root = (project in file("."))
  .settings(
    name  := "hello",  //项目名
    version  := "1.0",  //项目版本
    scalaVersion  := "2.11.8"  //项目使用的scala版本,不指定则使用默认版本
  )
```

其实, 如果目录中只有一个项目, 则只需要定义`name/version/scalaVersion`3个参数即可,
多余代码都可以删掉.

## 使用

`sbt`直接进入交互模式, 可运行`compile`来编译源文件, 运行`run`来运行程序, 运行
`package`来打包程序, 运行`test`来测试程序, 运行`clean`来清理目录.

当然, 也可以直接`sbt clean run`来先清理目录再运行程序.

`"testOnly TestA TestB"`表示只测试`A`和`B`, 因为3个字串其实是一个参数, 以`"`包围.

`~ compile`表示持续编译, 即源码有改动则自动编译.

## 添加库依赖

```scala
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core" % "2.0.2",
  "org.apache.logging.log4j" % "log4j-core" % "2.7"
  )
```

有个有趣的地方,"groupId", "artifactId", "version"3元组中,若有些库依赖`scala`版本,
则会出现`log4j-core_2.11`的样子,至少在`maven`库中会出现,表示适用`scala2.11.x`版本.
但在使用`sbt`时,如果声明了`scalaVersion`配置,可以去掉`_2.11`的字样.

除了托管依赖外, 对于非托管依赖, 如下载的`spark`安装目录下`jars`目录中的各种包, 可
选择使用托管依赖如上, 也可选择添加本地依赖. 一种方式是将`jars`目录下所有的包拷贝到
项目的`lib`目录下, 不实用; 另一种方式是在`build.sbt`目录下, 配置`unmanagedBase :=
new File("jars_path")`. 后一种方式有个致命的问题, 即在`IDEA`中无法被识别.

## 插件

添加插件需要在`project`目录下新建`.sbt`文件,声明要添加的插件,如`sbt-assembly`用于
打包`fat jar`的插件:

```scala
//assembly.sbt
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")
```
