# jupyter notebook

安装: `conda install jupyter`

执行命令`jupyter notebook`会在当前目录打开`notebook`.

在`markdown`模式下, 以`$...$`包围`tex`格式字串, 以渲染数学公式.

为了`PDF`输出, 可能需要安装`pip install nbbrowserpdf`.

## 快捷键

主要分命令模式和编辑模式, `h`显示帮助.

命令模式, `c-m`进入命令模式:
* `k/j`, 上下移动单元格
* `s-k/j`, 上/下选择多个单元格
* `a/b`, 在当前单元格上下插入新单元格
* `dd`, 删除当前单元格
* `c/x/v`, 复制/剪切单元格
* `v/c-v`, 在当前单元格下/上粘贴已复制或剪切的单元格
* `l`, 在单元格显示行号
* `o`, 打开或关闭命令输出 
* `m/y`, 进入`markdown`/代码模式

编辑模式, `enter`回车进入编辑模式:
* `c-a/e`, 定位单元格当前行的首尾
* `c-b/f`, 移动光标左/右一字符
* `c-h`, 删除光标左一字符
* `c-n/p`, 在单元格下/上一行
* `cmd-[/]`, 控制缩进
* `s-enter`, 执行单元格


## 魔法命令

`%magic`会列出所有魔法命令的相关说明.

魔法命令以`%`前缀字符, 表示对当前行有效, 以`%%`为前缀字符表示对当前单元格有效.

魔法命令可以紧跟参数或选项.

对于第三方库提供的魔法命令, 需要`%load_ext lib`载入才可使用.

可通过`register_line_magic`和`register_cell_magic`装饰函数将自定义函数转换为魔法命令.

当在类中定义了`_repr_*_()`的相关函数时, 类会以相关方式在`notebook`中显示. 

这里的`*`可以是`html`, `svg`, `javascript`, `latex`, `png`等格式.

若有多种显示格式时, `html`优先显示. 可通过`IPython.display.display_*(obj)`以别的格式显示.

* `%matplotlib inline`, 将图像内嵌在`notebook`中.
  * `qt5`, 以`qt`窗口显示窗口
* `%timeit stat`, 将`stat`语句执行多次后, 打印平均执行时间, 
  * `%%timeit`则是求单元格代码的执行时间.
  * `i = %timeit -o stat`, 将时间相关信息返回给变量`i`
* `%time stat`, 执行语句一次并打印执行时间, `%%time`求单元格代码的执行时间
* `%%capture var`, 将单元格输出全部保存到变量`var`中
* `%%prun`, 调用`profile`模块, 对单元格代码进行性能分析
* `%debug`, 在单元代码抛出异常后, 执行, 可进入`pdb`调试模式, 执行相关命令
* `%%cython`, 单元格代码将调用`cython`编译
* `%%file fname`, 将单元格代码保存到文件`fname`中
  * `!cmd`, 可执行`shell`命令
