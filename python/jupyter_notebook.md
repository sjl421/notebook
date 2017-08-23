# jupyter notebook

安装: `conda install jupyter`

执行命令`jupyter notebook --ip 0.0.0.0`会在当前目录打开`notebook`.

在`markdown`模式下, 以`$...$`包围`latex`格式字串, 以渲染行内数学公式, `$$...$$`是渲染块公式.

为了`PDF`输出, 可能需要安装`pip install nbbrowserpdf`.

`jupyter notebook --generate-config`生成默认的配置文件. 以上指定`ip/password`的也可以在生成的配置文件中配置.

关于设置密码, 执行`jupyter notebook password`即可.

## 快捷键

主要分命令模式和编辑模式, `h`显示帮助.

命令模式, `c-m`进入命令模式:

* `f`, 打开查找/替换
* `k/j`, 上下移动单元格
* `s-k/j`, 上/下选择多个单元格
* `s-m`, 将选中的单元格合并
* `a/b`, 在当前单元格上下插入新单元格
* `dd`, 删除当前单元格
* `c/x/v`, 复制/剪切单元格
* `v/c-v`, 在当前单元格下/上粘贴已复制或剪切的单元格
* `l`, 在单元格显示行号
* `o`, 打开或关闭命令输出
* `m/y`, 进入`markdown`/代码模式

编辑模式, `enter`回车进入编辑模式:

* `cmd-[/]`, 控制缩进
* `s-enter`, 执行单元格

`mac`:

* `c-a/e`, 定位单元格当前行的首尾
* `c-b/f`, 移动光标左/右一字符
* `c-h`, 删除光标左一字符
* `c-n/p`, 在单元格下/上一行

## 插件

```sh
pip install jupyter_contrib_nbextensions
jupyter contrib nbextension install --user  # 安装js/css文件到~/.local/share/jupyter/nbextensions目录
jupyter nbextension enable/disable <nbextension require path>  # 使能/禁止插件
```

## 主题

```sh
pip install --upgrade jupyterthemes
```

Usage:

* `jt -l`, 列出主题
* `jt -t theme_name`, 应用主题
* `jt -r`, 恢复默认主题

大体上, 先用这个吧: `jt -t grade3 -f ubuntu -fs 12 -nf ubuntu -tf ubuntu -cellw 1000 -vim -T`, 即应用`grade3`主题, 字体为12的`ubuntumono`.

因为整体的`notebook`有了风格, 则`matplotlib`绘图时, 为了使图像融入整体的风格, 需要执行:

```py
from jupyterthemes import jtplot
jtplot.style(theme='grade3')
```

可将这两行代码, 写入`~/.ipython/profile_default/startup/00_startup.py`文件.

## 魔法命令

魔法命令以`%`前缀字符, 表示对当前行有效, 以`%%`为前缀字符表示对当前单元格有效. 魔法命令可以紧跟参数或选项. 对于第三方库提供的魔法命令, 需要`%load_ext lib`载入才可使用. 可通过`register_line_magic`和`register_cell_magic`装饰函数将自定义函数转换为魔法命令.

当在类中定义了`_repr_*_()`的相关函数时, 类会以相关方式在`notebook`中显示. 这里的`*`可以是`html`, `svg`, `javascript`, `latex`, `png`等格式. 若有多种显示格式时, `html`优先显示. 可通过`IPython.display.display_*(obj)`以别的格式显示.

* `%lsmagic`, 在`cell`输出中可用的魔法命令, 没有详细说明.
* `%magic`, 在`pager`内列出所有魔法命令的相关说明.

* `%matplotlib inline`, 将图像内嵌在`notebook`中, `qt5`, 以`qt`窗口显示窗口

* `%timeit stat`, 将`stat`语句执行多次后, 打印平均执行时间. `%%timeit`则是求单元格代码的执行时间. `i = %timeit -o stat`, 将时间相关信息返回给变量`i`
* `%time stat`, 执行语句一次并打印执行时间, `%%time`求单元格代码的执行时间

* `%store var_name`, 在`notebook`文件间传递变量, 在另一个文件中执行`%store -r var_name`即可.

* `%%capture var`, 将单元格输出全部保存到变量`var`中
* `%%prun`, 调用`profile`模块, 对单元格代码进行性能分析
* `%debug`, 在单元代码抛出异常后, 执行, 可进入`pdb`调试模式, 执行相关命令
* `%%cython`, 单元格代码将调用`cython`编译

* `%env AA=1`, 设置环境变量

* `%run file_path.py`, 运行`py`文件, 路径不用引号包围
* `%load file_path`, 用文件内容替换`cell`的内容, 文件路径不用引号包围
* `%pycat file_path`, 在`pager`内显示文件内容
* `%%file fname`, 将`cell`内容保存到文件`fname`中

* `%who`, 列出全局变量, 可带类型参数, 表示列出此类型的全局变量

* `%%lang`, 以指定语言的核运行此`cell`的代码, 如`%%ruby`.

`pip install cython fortran-magic`, 通过`%load_ext Cython/fortranmagic`可载入两个魔法命令: `%%cython`和`%%fortran`, 可将`cell`中定义的函数编译成本地库函数, 来加速执行.

## Kernel

官方支持的核有3个: `python2/3`, `R`, `Julia`.

### python 2

若当前系统安装有多个`python`版本, 如使用`conda`创建2的环境.

```sh
source activate py2k  # 激活环境
python2 -m pip install ipykernel  # 安装kernel
python2 -m ipykernel install --user  # 注册kernel到~/.local/share/jupyter/kernels目录
```

如果有兴趣, 可以看看`--user`目录中的各`kernels`, 其实就是一个`json`配置文件和几个`logo`图片.

### R kernel

`conda install -c r r-essentials`(推荐)

### ruby kernel

需要提前安装好`ruby`环境.

```sh
sudo apt install libtool libffi-dev libzmq-dev
git clone https://github.com/zeromq/czmq  # 16.04安装最新的czmq
cd czmq
./autogen.sh && ./configure && sudo make && sudo make install
sudo apt install libzmq3-dev libczmq-dev  # 17.04
gem install cztop iruby
iruby register --force  # 注册核到`~/.ipython/kernels`目录中
```

考虑到`jupyter`是从`ipython`中分离出去的. 在`~/.local/share/jupyter`和`~/.ipython`目录中都有`nbextensions`和`kernels`两个目录用于存放拓展和核.

## 额外

`?sum`, 获取函数帮助

`!cmd`, 可执行`shell`命令

末尾的变量或函数会自动显示结果, 通过加`;`来拒绝输出. 特别在作图时比较方便.

多光标: 按住`alt`键拖动鼠标可实现, 感觉这样的话, 光标都是挨着的.

`conda install -c damianavila82 rise`为`jupyter/ipython`提供类似`PPT`的展示效果, 主要结合使用`Reveal.js`库, 在`view/Cell Toolbar/Slideshow`菜单, 会在每个`cell`的右上角显示`Slide Type`. 其中, `slide`为单独一页`PPT`, `sub_slide`为子`PPT`, `fragment`则为同页`PPT`的一部分.

`rpy2`库提供`R/python`两种语言的混合使用.