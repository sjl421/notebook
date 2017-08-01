# ipython

帮助: `str.split?[?]`

`shell`命令: `!pwd`

使用`python`变量: `dirname='helo'; !mkdir $dirname`

目录和书签:
* `%pwd`显示当前目录
* `%cd`切换目录
* `%bookmark`目录书签
  * `%bookmark [dirpath] bmname`定义指定目录书签名
  * `%bookmark -l`列出书签列表
  * `%bookmark -d bmname`删除书签
  * `%cd -b bmname`使用书签跳转

编辑,退出后运行: `edit hello.py`

运行: `run hello`

输入命令历史: `%hist`

工作目录历史: `%dhist`

查看对象源码文件: `%pfile obj`

## profile

```sh
ipython profile list  # 列出当前可用的配置
ipython locate profile [foo]  # 列出配置foo的路径, 省略则为default创建
ipython profile create foo  # 创建配置foo
ipython --profile foo  # 以配置foo启动ipython
```

创建配置后, 会创建`~/.ipython/profile_xxx`目录. 其中`startup`子目录存储`xx_filename.py`文件, `xx`是数字表示在`ipython`启动时依次读入的顺序, 小优先. `ipython_config.py`, `ipython_kernel_config.py`, `ipython_notebook.py`3个文件分别是对`ipython`和`ipython kernel`的配置.

在`ipython_config.py`文件中, 配置`c.InteractiveShell.ast_node_interactivity = "all"`可为独占一行的所有变量或语句都自动显示结果.