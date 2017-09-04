# maven

## install a single jar file

You have another choice to config a specifial pom.xml,
then just execute `mvn install:install-file` cmd.

* `$ mvn install:install-file -DgroupId=xxx -DartifactId=xxx -Dversion=xxx -Dfile=xxx.jar`

## execute a java class

You have another choice to config a specifial pom.xml,
then just execute `mvn exec:java` or `mvn exec:exec`

* `$ mvn exec:java -Dexec.mainClass="xxx"`
* `$ mvn exec:exec -Dexec.excutable="java" -Dexec.args="xxx"`

## 选项

`-P`是激活`profiles`, 在`pom.xml`文件的`<profiles>`标签中定义.

`-D`是定义属性, 在`pom.xml`文件的`<properties>`标签中定义.

`mvn help:evaluate -Dexpression=xx`, 似乎可以执行某种运算, 或提取项目的属性.