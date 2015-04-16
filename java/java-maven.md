# maven

## install a single jar file

You have another choice to config a specifial pom.xml,
then just execute `mvn install:install-file` cmd.

* `$ mvn install:install-file -DgroupId=xxx -DartifactId=xxx -Dversion=xxx -Dfile=xxx.jar`

## execute a java class\

You have another choice to config a specifial pom.xml,
then just execute `mvn exec:java` or `mvn exec:exec`

* `$ mvn exec:java -Dexec.mainClass="xxx"`
* `$ mvn exec:exec -Dexec.excutable="java" -Dexec.args="xxx"`
