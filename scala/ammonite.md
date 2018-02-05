# Ammonite

Scala的可替代REPL

安装: `sudo curl -L -o /usr/local/bin/amm https://git.io/vdNv2 && sudo chmod +x /usr/local/bin/amm && amm`

可在`~/.ammonite/predef.sc`文件中定义启动时执行的`scala`语句, 比如对可变`Map`的导入

```scala
```

## 特性

块输入: `{}`中输入的语句会一行行执行, 效果相当于把所有语句写在一行, 跟标准`REPL`的`:paste`有点像

用`{{}}`来达成`scala`语言中语句块的效果

文件导入: 可以导入`scala`脚本, 比如在脚本`abc.sc`中定义了一个变量`val a = 1`, 注意: 后缀必须是`.sc`

```scala
import $file.abc
abc.a  //=> 1
```

依赖导入: 可以导入未下载的库, 比如`"org.json4s" %% "json4s-jackson" % "3.5.3"`

```scala
import $ivy.`"org.json4s" %% "json4s-jackson" % "3.5.3"`
```