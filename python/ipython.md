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
