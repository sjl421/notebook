# Nexus Repository Manager

`Nexus`是`Sonatype`开发的仓库管理软件,有开源版本,可让用户在本地服务器上搭建私有
的软件仓库.它提供两个核心功能:1,代理远程仓库并缓存组件,可节省重复地从远程服务器
获取组件的外部带宽和下载时间;2,在开发团队中,维护一个本地仓库来进行部属.

例如,一个开发团队有N人,其中成员A在开发过程,向`nexus`请求下载依赖包,`nexus`会查看
是否有缓存此依赖包,没有则先从远程服务器下载到`nexus`服务器,再返回依赖包给A,有则直
接返回给A.成员B在开发过程中,若需要同样的依赖包,因为`nexus`已经缓存,则可直接下载.
若项目由多个组件构成,不同成员开发不同的组件,成员A开发好组件A后,可部属到`nexus`服务
器,这样成员B开发的组件,因为依赖组件A,则可直接从`nexus`中下载.因为组件A/B都是内部
组件,没有也不应该暴露到外网中.

在`Nexus`中,组件是一个抽象的概念,可能对应`java`的`jar`包,`war`包,也可能对应`Python`
的`package`,也可能对应`Ruby`的`gems`.换句话说,虽然最开始`Nexus`被开发请来用来管理
`Maven`仓库,但后续的版本已经添加了对很多别的仓库的支持,包括但不限于:`Java`平台的
`Maven`仓库,`.NET`平台的`NuGet`仓库,`Docker`镜像的仓库,`Node`的`npm`仓库,`Python`
的`PyPI`仓库,`Ruby`的`RubyGems`仓库,等等.

在仓库类型上,主要有`public`,`hosted`,`proxy`.其中`proxy`是用来代理远程仓库的,即
向`proxy`仓库请求依赖包,`proxy`会代理向远程仓库请求,所以每个`proxy`仓库在创建时
会指定远程仓库地址.`hosted`是用来本地部属的仓库,即内部仓库,用于在团队内部进行组件
共享依赖,依发布性质,分为`releases`和`snapshots`.而`public`是一个混合仓库,包含了多
个`proxy`或`hosted`仓库,以对外提供简洁的接口.即,默认情况下,在客户端的主机上只需要
设定`public`一个仓库即可,在服务器端会自动从其包含的仓库中查找依赖包.

`public`包含多个`proxy`仓库,当你的项目依赖一个比较少见的包,这个包不能在`public`
已经包含的远程仓库中找到,有两种方法:1,向管理者申请,将此包所在的仓库添加到`public`
包含的`proxy`仓库;2,在项目的`pom.xml`文件中手动添加此仓库.(第2种方法好像目前不可
行,在探索中)

`releases`仓库和`snapshots`仓库的区别: 项目的每个版本只能在`releases`仓库部属一次,
一旦部属则不能改变,除非创建新的版本.而`snapshots`则可以同一版本多次部属.所以,要非
常慎重,确定当前版本下代码不会再改变,才进行`releases`部属.如果经常改变,则会造成依赖
此项目当前版本的别的项目产生兼容问题.

除以下已包含的仓库,需要添加额外的仓库请向管理员申请.

已包含的`proxy`仓库:
* `https://repo1.maven.org/maven2/`,`Maven`中心仓库
* `http://repo.typesafe.com/typesafe/ivy-releases/`,`scala`相关仓库
* `http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/`,`sbt`插件仓库

不只是`Maven`仓库,别的语言别的平台的仓库,`Nexus`也能管理,需要的话,可以继续添加.

## 安装

推荐以特定用户运行: `sudo adduser --disabled-login nexus`

下载安装包,解压: `sudo tar zxvf nexus-x.x.x.tar.gz -C /opt/`

改变目录权限: `sudo chown -R nexus:nexus /opt/nexus-x.x.x /opt/sonatype-work`

修改运行用户: 修改`bin/nexus.rc`文件,使用`nexus`用户

运行: `sudo bin/nexus start`

作为服务运行,`/etc/systemd/system/nexus.service`:
```systemd
[Unit]
Description=nexus service
After=network.target
[Service]
Type=forking
ExecStart=/opt/nexus/bin/nexus start
ExecStop=/opt/nexus/bin/nexus stop
User=nexus
Restart=on-abort
[Install]
WantedBy=multi-user.target
```

执行: 
```sh
sudo systemctl daemon-reload
sudo systemctl enable nexus.service
sudo systemctl start nexus.service
```

日志文件: `/opt/sonatype-work/nexus/log/nexus.log`

## 约定

关于部属需要的用户名和密码,目前计划与`act`邮箱名相同.

关于项目的`groupId`,目前计划使用`act.email_user_name`.

关于项目的`version`,若以`SNAPSHOT`结尾则会部属到`snapshots`仓库,可多次部属.

若需要测试部属,请使用`test.email_user_name`的`groupId`,以方便日后删除.

## 配置

通常,只需要将额外需要的代理仓库创建,再添加到`maven-public`即可.

对于部属,需要创建新的`roles`,`nx-deploy`,并授予除`nx-anonymous`外,额外的

* `nx-repository-view-*-*-add`
* `nx-repository-view-*-*-edit`

之后,创建用户时,为其赋予`nx-deploy`角色即可.

## Maven

全局配置:修改`~/.m2/settings.xml`文件,添加如下内容:

```xml
<settings>
  <mirrors>
    <!--添加部属的nexus服务器镜像-->
    <mirror>
      <id>nexus</id>
      <mirrorOf>*</mirrorOf>
      <url>http://192.168.0.200:8081/repository/maven-public/</url>
    </mirror>
  </mirrors>
  <profiles>
    <profile>
      <!--添加关于仓库和插件仓库的配置文件-->
      <id>nexus</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>http://central</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>central</id>
          <url>http://central</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  <activeProfiles>
    <!--激活上述添加的配置文件-->
    <activeProfile>nexus</activeProfile>
  </activeProfiles>
  <servers>
    <!--添加部属时用户名和密码-->
    <server>
      <id>nexus</id>
      <!--用户名密码需要向管理者申请-->
      <username>your_user_name</username>
      <password>your_user_password</password>
    </server>
  </servers>
</settings>
```

具体项目:在`pom.xml`文件中,添加部属配置:

```xml
<project>
  ...
  <distributionManagement>
    <!--release和snapshot是不同的仓库-->
    <repository>
      <id>nexus</id>
      <name>Releases</name>
      <url>http://192.168.0.200/repository/maven-releases</url>
    </repository>
    <snapshotRepository>
      <id>nexus</id>
      <name>Snapshot</name>
      <url>http://192.168.0.200/repository/maven-snapshots</url>
    </snapshotRepository>
  </distributionManagement>
</project>
```

具体部属时,取决于项目`pom.xml`中的`version`域,若类似`1.0-SNAPSHOT`则部属于`snapshot`
仓库,否则部属到`release`仓库.

一切配置好后,运行`mvn deploy`即可.

添加依赖: 在`pom.xml`中,添加:

```xml
<project>
  ...
  <dependencies>
    <dependency>
      <groupId>org.example</groupId>
      <artifactId>my-project</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
</project>
```

## Sbt

务必使用最新的`sbt`,即至少`0.13.13`及以上,否则不认识`local`.

`Sbt`是用于`scala`项目的管理软件,使用`Ivy`解析依赖,可以使用`maven2`格式的仓库.

全局配置: 修改`~/.sbt/repositories`文件(没有则创建),添加:

```yml
[repositories]
local
nexus: http://192.168.0.200:8081/repository/maven-public/
nexus-ivy: http://192.168.0.200:8081/repository/maven-public/, 
[organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]
```

以上指定了`sbt`在下载依赖包时的顺序,`local`表示本地的`.ivy2`仓库,`nexus`是搭建的
`nexus`服务器的`maven-public`仓库,下载符合`maven2`格式的包,`nexus-ivy`则是下载符合
`ivy2`格式的包.

大部分情况下,你不用管这是什么.`maven-public`是一个综合性的仓库,但如果需要的依赖包
在未列出的仓库中,请通知管理员添加.

修改`~/.sbt/.credentials`文件,添加:

```yml
realm=Sonatype Nexus Repository Manager
host=192.168.0.200
user=your_user_name
password=your_user_password
```

这是添加部属需要的验证用户和密码.

具体项目: 修改`build.sbt`,添加:

```scala
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

publishTo := {
  val nexus = "http://192.168.0.200:8081/repository/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "maven-snapshots")
  else
    Some("releases" at nexus + "maven-releases")
}
```

`publishTo`是部属配置,易知,会根据项目的`version`部属到`snapshots`或`releases`.

一切配置完成后,执行`sbt publish`.

额外的`build.sbt`解析:

```scala
name := "my-project"  //项目名,相当于maven的artifactId
organization := "org.example"  //组织名,相当于maven的groupId
version := "1.0-SNAPSHOT"  //项目版本
```

部属后,若依赖此项目,则在新项目的`build.sbt`添加:

```scala
libraryDependencies += "org.example" %% "my-project" % "1.0-SNAPSHOT"  //添加项目依赖
```

