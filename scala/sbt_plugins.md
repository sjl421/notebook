# sbt 插件

在`project/plugins.sbt`文件中添加

## sbt-assembly

说明: 打包`fat jar`的插件:

添加: `addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")`

使用: `assembly`

## sbt-dependency-graph

说明: 可视化项目的依赖

添加: `addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")`

使用: `dependencyTree`, `dependencyGraph`