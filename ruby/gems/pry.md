# pry

`ruby`的增强版`repl`, 用于替代`irb`.

安装: `gem install pry pry-doc`.

通过`help`命令查看`pry`提供的所有命令, 通过`cmd --help`查看特定命令的帮助.

`pry-doc`可以查看源代码, 包括`C`源码, 如`show-method String#split`. 相似的`show-doc String#split`则是显示帮助文档. `show-doc`不依赖于预先生成的文档, 而是直接抓取, 但有个缺陷, 不能显示类的文档, 此时你可以使用`ri`命令.

```ruby
cd Pry  # 进入pry类内部
ls -M --grep re  # 列出所以包含re字串的方法
show-method rep -l  # 显示rep方法的源码, 并显示行号
```

`pry`提供了很多的命令, 命令不同于方法, 必须从一行的开头写起, 可以连接选项.

命令:
* `cd`, 进入对象内部
  * 如`cd String`
* `ls`, 列出对象内部的变量或方法
  * `-i`, 列出实例变量
  * `-M`, 列出所有方法


```ruby
class Hello
  @x = 20
end
cd Hello  # 进入类内部, 即新的作用域
ls -i  # 列出实例变量
cd @x  # 进入实例变量内部
self + 10  #=> 30, 当前的self指20
nesting  # 命令, 列出作用域嵌套信息
cd ..  # 返回上层的对象内部
jump-to n  # 跳转到指定层级的作用域
cd     # 返回最顶层的默认的main对象内部
```


`pry`可以在运行时激活, 即在运行代码的过程中, 打开`pry`会话进入交互式操作, 在退出会话后, 程序会继续执行, 会话中的改变有效.

```ruby
require 'pry'
class A
  def hello() puts "hello world!" end
end
a = A.new
binding.pry  # 在当前的绑定开启会话
a.pry  # 在对象a内部开启会话
```

所有以`.`开始的命令都被作为`shell`命令来解析执行. 你可以执行常见的`ls cd awk`等命令, 也可以`.vim`打开文件, 编辑, 退出, 返回会话. 你也可以执行`.git`操作.

`shell`命令, 如`.cd`命令, 会改变当前工作目录, 即`Dir.pwd`的结果. 但退出会话后并不变化.

还有个`shell-mode`, 除了将当前目录加入提示符外, 据说加入文件名补全却没成功外, 命令执行依然需要`.`, 挺肋鸡肋的.

在与`shell`的交互中, 你可以使用`#{expr}`来内嵌`ruby`代码.

可以使用`edit`命令直接编辑方法所在的文件, 编辑成功后默认重新载入, 除非你指定`--no-reload`参数. `Pry.editor`是编辑所使用的编辑器, 默认是`$EDITOR`环境变量.

将`pry`作为`Rails`的默认终端, 可以`pry -r ./config/environment`启动会话, 或者将`pry-rails`写入你的`Gemfile`文件.

`pry`默认支持语法高亮, 虽然缩进仍然很糟糕. 可以通过`toggle-color`命令开关高亮, 或者将`Pry.color = false`写入`~/.pryrc`文件来永久关闭高亮. 如果你想改变个颜色, 要安装`pry-theme`包.
