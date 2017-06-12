# Android Apk反编译

## 工具

`git clone https://github.com/jesusFreke/smali`是`Android`虚拟机`dalvik`使用的`dex`格式的汇编和反汇编器

编译: `gradle clean build`

在smali和baksmali文件夹下各生成jar文件, 路径如`smali/baksmali/build/libs`.

将`classes.dex`反编译成`.smali`文件: `java -jar baksmali-2.0.7.jar -o classout/ classes.dex`

将`.smali`文件再编译成`classes.dex`文件: `java -jar smali-2.0.7.jar classout/ -o classes.dex`

## dex2jar

`git clone https://github.com/pxb1988/dex2jar`, 提供通用工具, 用于`.dex`, `.smali`和`.jar`文件的相互转化以及额外的加密和重签名功能

安装: `mvn clean package`或`gradle clean build`

生成文件: `./dex-tools/target/dex2jar-x.x-SNAPSHOT.zip`, 将上述文件解压到一个目录中, 其运行脚本(各种`.bat`文件)全部都是`d2j-invoke.bat`文件的调用, 而此文件又会将同目录下的`lib`目录中的所有java包`jar`添加到`classpath`, 明白此道理, 则可以将文件随意存放.

`d2j-smali.bat`脚本, 用于将`classes.dex`文件转换为`.smali`文件. `d2j-baksmali.bat`脚本则相反.

`d2j-dex2jar.bat`用于将`.dex`转为`.class`文件, 并打包为`.jar`. `d2j-jar2dex`则相反.

`d2j-apk-sign.bat`用于`.apk`文件的签名.

`d2j-jar2jasmin.bat`用于将`.jar`文件转换为java本身的反汇编文件`.j`, 类似`.smali`. `d2j-jasmin2jar.bat`则相反.

还有些别的脚本, 我也不知道作什么用.

其还提供些库, 如`dex-reader.jar`, `dex-writer.jar`, 可以用于在编程中调用相应的API对`.dex`文件进行读写.

## Apktool

`git clone git://github.com/iBotPeaches/Apktool.git`, 用于反编译`.apk`的资源文件`resources.arsc`和代码文件`classes.dex`(反编译为`.small`), 也可重新构建成`.apk`

安装: `[./gradlew][gradlew.bat] build [fatJar] [proguard]`

生成文件: `./brut.apktool/apktool-cli/build/libs/apktool-xxxxx.jar`

## jd-gui

`git clone https://github.com/java-decompiler/jd-gui`, 用于将`.smali`反编译为`.java`文件

安装: `gradle clean build`

生成文件: `./build/libs/jd-gui-x.x.x.jar`

## apktool

- decoding: `apktool d test.apk [-o outputdir] [-t tag]`反编译资源文件,反编译`.dex`文件为`smali`文件, 使用标签为`tag`的框架文件
- building: `apktool b inputdir [-o output.apk]`
- install-framework: `apktool if com.htc.resources.apk [-t htc]`得到命名`<id>-<tag>.apk`的框架文件, 否则没有`tag`标签.

关于框架文件, `apktool`依赖标准的`AOSP`应用框架来解码和构建`apk`, 但有些手机厂商的自带应用往往会加入不同的框架文件, 可能导致失败. 此时需要安装特定厂商的框架文件.

这些框架文件多在`/system/framework`文件夹下, 也可能在`/data/system-framework`或`/system/app`或`/system/priv-app`中. 命名方面, 名字多有`resources`或`res`或`framework`等字串.

发现框架文件后, 可以通过`adb pull /path/to/file`来拖到本地.

框架文件存储在`apktool`工具源码的`/apktool/framework`目录中.也可以使用`--frame-path`选项指定.

### decode

- `-b`: 不添加调试信息, 当比较同一APK的不同版本的smali文件时, 调试信息会影响比较报告
- `-d`: 添加调试信息
- `--debug-line-prefix <prefix>`: 在调试模式下解码时附加的smali行前缀
- `-f`: 如果解码的输出目录存在则删除
- `-o outputdir`: 指定输出目录
- `-r`: 不解码资源文件`resources.arsc`
- `-s`: 不解码代码文件`classes.dex`
- `-t`: 使用不同的框架文件

### rebuild

- `-a path`: 从指定路径载入`aapt`程序
- `-c`: 复制原`AndroidManifest.xml`和`META_INF`目录到构建的apk
- `-d`: 调试模式构建
- `-f`: 构建过程中覆盖存在的文件
- `-o`: 输出文件`out.apk`
- `-p path`: 框架文件路径

## apksign

下载: `git clone https://github.com/appium/sign`

安装: `mvn package`

使用: `java -jar sign.jar my.apk`, 生成签名后的`my.s.apk`包
    或者`java -jar sign.jar my.apk --override`直接替换原apk包.

签名验证: `jarsigner -verify my.s.apk`

## 命令

通过`adb install xxx.apk`安装应用, 通过`adb uninstall [-k] com.enali.xxx`来卸载应用. 此处的`com.enali.xxx`是包名.

通过`adb shell pm list packages`来列出所有的包名.

## pm

`pm list packages`列出包名
`pm list permission-groups`列出权限组
`pm list permissions`列出权限
`pm list instrumentation`
`pm list feature`列出特性
`pm list libraries`列出所有的库
`pm list users`列出用户

## adb install apk

安装`apk`本质上做了如下事情:

- 将`apk`文件放在了`/data/app`目录下
- 在`/data/system/packages.xml`中增加了条记录
- 在`/data/data`增加`apk`使用的数据库目录.

1, 在`.smali`文件中的自己添加2的注释会被删除
