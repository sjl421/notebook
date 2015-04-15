# Vim Basic

## Vim初步

* 移动光标: `h`, `j`, `k`, `l`
* 删除光标处/光标左字符: `x`, `X`
* 删除一行: `dd`
* 删除行末换行符: `J`
* 撤消: `u`
* 重做: `Ctrl-R`
* 光标前插入: `i`
* 行首第一个非空字符插入: `I`
* 光标后插入: `a`
* 行尾并插入: `A`
* 光标正方开始新行: `o`
* 光标上方开始新行: `O`
* 指定重复次数: `3x`
* 退出: `ZZ`
* 帮助: `:h`
* 标签退栈: `Ctrl-T`
* 跳转至光标: `Ctrl-]`
* 跳转到前一个位置: `Ctrl-O`
* 清屏并重绘屏幕: `Ctrl-L`

## Vim 移动

`iskeyword`选项界定词word. WORD理解为由空白字符隔开的字串.

* 右跳一个word/WORD: `w`, `W`
* 左跳一个word/WORD首: `b`, `B`
* 右跳一个word/WORD尾: `e`, `E`
* 左跳一个word/WORD尾: `ge`, `gE`
* 行首/尾: `0`, `$`
* 行的第一个非空白字符: `^`
* 行内单字符右/左查找: `f`, `F`
* 行内右/左查找并置光标于字符前: `t`, `T`
* 括号匹配: `%`
* 文件首: `gg`
* 文件末: `G`
* 指定行: `3G`
* 百分比定位: `50%`
* 屏幕顶/中/底: `H`, `M`, `L`
* 确定当前位置(若`set ruler`,则无必要): `Ctrl-G`
* 向上/下滚动一屏: `Ctrl-F`, `Ctrl-B`
* 向上/下滚动半屏: `Ctrl-U`, `Ctrl-D`
* 向上/下滚动一行: `Ctrl-E`, `Ctrl-Y`
* 将光标所在行置于顶屏/中屏/底屏: `zt`, `zz`, `zb`
* 查找/反向查找: `/`, `?`
* 查找整个word(\<匹配word头,\>匹配word尾): `/\<the\>`
  * 下/上一个匹配: `n`, `N`
* 右/左查找光标处整个/部分word: `*`, `#`, `g*`, `g#`
* 标记/跳转到标记: `m[a-zA-Z]`, `'[a-zA-Z]`
  * `a-z`标记是单个文件独有的,
  * `A-Z`标记被多个文件共有
* 列出所有的标记列表: `:marks`
  * 特殊标记: 跳转前的光标位置\`, 最后编辑的光标位置", 最后修改的开始位置\[, 最后修改的结束位置\]
  * 列出标记位置: `:marks [a-zA-Z]`
* 跳回"较老/新"的位置: `Ctrl-O`, `Ctrl-I`
* 列出可跳转位置列表: `:jumps`

## 小改动

* 删除一个word(操作符+动作): `dw`, `d4w`, `d$`
* 修改word(删除并插入): `cw`, `c4w`
* 删除到行尾: `D`
* 修改到行尾: `C`
* 修改单字符: `s`
* 修改整行: `S`
* 替换单字符: `r`
* 持续替换直至esc: `R`
* 重复最后修改命令: `.`
* 字符/行/列块可视模式: `v`, `V`, `Ctrl-V`
  * 跳到被选择文本的另一端: `o`, `O`
* 粘贴文本(行下/光标右,行上/光标左): `p`, `P`
* 交换两个字符: `xp`
* 拷贝文本: `y`
  * 拷贝一行: `yy`, `Y`
* 剪贴板: `"*y`, `"*p`
* word中删除word: `[dcy]iw`, `[dcy]aw`
* sentence中删除sentence(`as`包括sentence后的空白字符): `[dcy]is`, `[dcy]as`
* 执行修改:
  * 操作符+动作: `d$`
  * 可视模式
  * 文本对象: `daw`
* 转换大小写: `~`
* 列出当前已定义映射: `:map`
* 列出选项: `:options`
* 恢复选项默认值(`&`): `:set iskeyword&`
* 使用寄存器: `"[a-z]`, `"fyas`/`"fp`
* 添加到文件(或可在可视模式选中文本): `:write >> logfile`

## .vimrc

* 高亮查找的字符: `set hlsearch`
* 增量式查找: `set incsearch`
* 查找字符忽略大小写: `set ignorecase`
* 遇文件头尾循环查找: `set wrapscan`
* 设置在插入模式下允许<BS>删除光标前字符的位置: `set backspace=indent,eol,start`
  * 分别表示: 行首空白符,换行符,插入模式开始前字符
* 新行缩进: `set autoindent`
* 覆盖文件时保留备份: `set backup`, `set backupext=.bak`, `set backupdir=/path/to/your/backupdir`
* 保留原始文件: `set patchmode=.orig`
* 保存命令和查找模式的历史: `set history=50`
* 在窗口右下角显示光标位置: `set ruler`
* 窗口右下角显示未完成命令: `set showcmd`
* 纯文本行超78字符自动换行: `autocmd FileType text setlocal textwidth=78`
* 禁止回绕行: `set nowrap`
  * 一次右滚10个字符: `set sidescroll=10`
* 设置可换行移动命令: `set whichwrap=b,s`
  * `b`是<BS>键, `s`是<SPACE>
  * `<`是<Left>, `>`是<Right>
  * `[`是插入模式中的<Left>, `]`是插入模式中的<Right>
* 显示TAB键和行尾符`$`: `set list`
  * TAB显示为`^I`, 行尾符显示为`$`
  * 自定义显示: `set listchars=tab:>-,trail:-`
* 界定word: `set iskeyword+=-`
* 设定命令行高度: `set cmdheight=2`

## 语法高亮

* 手动设置文件类型: `:set filetype=fortran`
  * 在文件首和文件末添加"# vim: syntax=ruby"
* 设置背景色: `set background=dark/light`
* 更换配色主题: `colorscheme evening`
* 色彩:
  * `term`    : 黑白终端
  * `cterm`   : 彩色终端
  * `ctermfg` : 彩色终端前景色
  * `ctermbg` : 彩色终端背景色
  * `gui`     : GUI版本属性
  * `guifg`   : GUI版本前景色
  * `guibg`   : GUI版本背景色
  * 用绿色显示注释: `:highlight Comment ctermfg=green guifg=green`
* 测试常用色彩组合: `:runtime syntax/colortest.vim`
* 语法高亮: `syntax enable`
* 临时关闭高亮: `:syntax clear`
* 关闭高亮: `:syntax off`
* 手动设置高亮: `:syntax manual`, `:set syntax=ON`

## 编辑多个文件

* 编辑另一个文件: `:edit fname.txt`
* 保存文件: `:write [fname.txt]`
* 文件列表: `$ vim f1.txt f2.txt f3.txt`
  * 默认显示第一个文件,编辑第二个文件: `:next`, `:n`
  * 保存第一个文件并编辑第二个文件: `:wnext`, `:wn`
  * 查看文件列表: `:args`
  * 编辑前一个文件: `:prev`, `:wprev`, `:p`, `:wp`
  * 编辑最后一个文件: `:last`
  * 编辑第一个文件: `:first`
  * 后跳两个文件: `:2next`
  * 编辑另一个文件列表: `:args t1.txt t2.txt t3.txt`
  * 两文件间跳转(不改变文件列表位置): `Ctrl-^`
  * 为列表中每个文件创建窗口: `:all`, `:vert[ical] all`
  * 水平分割窗口并编辑next/prev文件,first/last文件:
    * `:spr[evious]`, `:sn[ext]`, `:sfir[st]`, `:sla[st]`
* 跳转到上次离开这个文件时位置: `'"`
* 跳转到最后一次修改文件的位置: `'.`
* 只读模式启动Vim: `$ vim -R file`
  * 严格的只读: `$ vim -M file`
* 另存为: `:sav[eas] new_name.txt`

## 分割窗口

* 水平分割窗口: `:sp[lit] f1.txt`, `:new f1.txt`, `Ctrl-W s`
* 垂直分割窗口: `:vsp[lit] f1.txt`, `:vne[w] f1.txt`, `Ctrl-W v`
* 分割窗口并指定大小: `:3sp f1.txt`
* 改变窗口大小(接受计数前缀): `3 Ctrl-W +`, `3 Ctrl-W -`, `3 Ctrl-W _`
  * 跳转窗口(`t`顶,`b`底): `Ctrl-W h/j/k/l/t/b`
  * 移动窗口: `Ctrl-W H/J/K/L`
  * 转窗口为tab页面: `Ctrl-W T`
* 关闭窗口: `:clo[se]`
* 关闭其余窗口: `:on[ly]`
* 退出/保存所有窗口: `:qa[ll]`, `:wa[ll]`, `:wa[ll]`
* 为文件列表中每个文件打开一个窗口: `$ vim -o f1.txt f2.txt f3.txt`
* 用vimdiff显示文件差异: `$ vimdiff f1.txt f2.txt`
  * 在内部启动比较模式: `:vert[ical] diffs[plit] f2.txt`
  * 有当前文件的补丁: `:vert[ical] diffp[atch] f2.txt patch.txt`
  * 关闭比较模式的滚动绑定: `set noscrollbind`
  * 跳转/反向跳转到修改点: `]c`, `[c`
  * 消除差异: `:diffu[pdate]`
  * 文本左右拷贝: `dp`, `do`
    * 在左边窗口,从左边拷贝到右边: `diff put`, `dp`
    * 在右边窗口,从左边拷贝到右边: `diff obtain`, `do`
* 打开/关闭折叠: `zo`, `zc`
* 新标签页编辑: `:tabe[dit] new_file`
  * 切换标签页: `gt`
  * 任何会打开窗口的命令前都可前加`:tab`,在新标签页打开: `:tab split`, `:tab help gt`
  * 关闭其余标签页: `:tabo[nly]`
* 指定什么时候对最后一个窗口显示状态条: `set laststatus=[012]`

## GUI(.gvimrc)

* 去工具栏: `:set guioptions-=T`
* 去菜单栏: `:set guioptions-=m`
* 去右边滚动条: `:set guioptions-=r/R`
* 去左边滚动条: `:set guioptions-=l/L`
* X-window系统有两个地方可以在程序间交换文本:
  * 当前选择区:在一个程序选中文本,鼠标左键在另一个程序粘贴, `"*`寄存器表示
  * "真"剪贴板:选中文本,通过菜单的copy/paste进行复制和粘贴, `"+`寄存器
* 设置鼠标模式: `:behave xterm/mswin`

## 大修改

* 记录和回放命令:
  * 启动记录: `q[a-z]`
  * 终止记录: `q`
  * 执行记录: `[n] @[a-z`
  * 执行上次执行的记录: `@@`
  * 记录到大写字母寄存器\[A-Z]的命令会追加到小写字母寄存器\[a-z]中
* 命令记录作为文本内容保存在寄存器,可以先写出命令再复制到寄存中,再执行记录
* 字符串替换: `:[range]s/from/to/[flags]`
  * 默认当前行,`%`为全部行
  * 默认只替换第一个匹配点,`g`替换所有匹配点
  * 默认不提示替换,`c`提示
  * 替换n1到n2行的所有this为that: `:n1,n2s/this/that/g`
  * 替换n行的this为that: `:ns/this/that/g`
  * 替换当前行到最后一行: `:.,$s/this/that/g`
  * 替换当前章节中所有的`grey`为`gray`: `:?^Chapter?,/^Chapter/s=grey=gray=g`
    * `?^Chapter?` 向前搜索章节开始标志
    * `/^Chapter/` 向后搜索章节开始标志
    * 用模式匹配来选定范围
    * 若不替换标题: `:?^Chapter?+1,/^Chapter/-1s=grep=gray=g`
      * 加减号用于设定偏移
      * 替换当前行下面第三行到文本末倒数第五行的范围: `:.+3,$-5s/this/that/g`
  * 替换两个标记间的行文本: `:`a,`bs/this/that/g`
  * 在可视模式中选中文本,若`:`进入命令行模式,则可得到范围: `:'<,'>s/this/that/g`
    * `'<`, `'>`是标记,标记选中文本的开始与结尾处
  * `5:`用于指定行数,得到范围: `:.,.+4s/this/that/g`
* `:write`命令不指定范围时,默认写入整个文件
  * 只写入当前行: `:.write`
* 找到匹配点并在那里执行一个命令: `:[range]g/{pattern}/{command}`
  * `{command}`只能是冒号命令,普通命令使用`:normal`
  * 只修改C++风格注释内容中的"foobar"为"barfoo"(`+`为分隔符): `:g+//+s/foobar/barfoo/g`
  * 默认整个文件
* 可视列块模式: `Ctrl-V`
  * 将可视列块扩展到每行的末尾: `$`
  * 插到可视列块每行的左边: `I`
  * 插到可视列块每行的右边: `A`
  * 删除可视列块并插入每一行: `c`
  * 被选中的文本: 交换大小写: `~`, 转换成小写: `u`, 转换成大写: `U`
  * 用单字符填充: `r`
  * 将选中文本右移一个单位(由`:set shiftwidth=4`决定): `>`
  * 连接若干行: `J`, 不改变行前后的空白符: `gJ`
* 在光标处读入文本内容: `:r[ead] {filename}`
  * 插入文件最后: `:$r {filename}`
  * 插入文件最前: `:0r {filename}`
* 保存文件部分行内容: `:[range]w[rite] {filename}`
  * 保存当前行到文件: `:.w {filename}`
  * 追加内容到文件: `:.w >> {filename}`
* 自动换行: `:set textwidth=72`
* 排版当前段落(`ap`为段落的文本对象): `gqap`
* 改变大小写: `gU`, `gu`, `g~`
  * 改变单词的大写: `gUw`
  * 改变整行的大小写(重复): `gugu`, `gUgU`, `g~~`
* 使用外部程序对文本块进行过滤, 会改变文本的: `!{motion}{program}`
  * 相当于`:[range]!{program}`
  * 例如排序文本: `!5Gsort<Enter>`, `:.,.+4!sort<Enter>`
  * `!!`对当前行进行过滤
* 读入命令输出: `:read !ls`
  * 读入日期: `:0read !date -u`

## 从崩溃中恢复

* `$ vim -r help.txt`
  * vim在停止输入4秒或输入约200字符会更新交换文件,由`updatetime`和`updatecount`指定
  * 若编辑时未给出文件名,`$ vim -r ""`
  * 命令: `:rec[over]`
* 交换文件保存目录: `set dir`

## 小窍门

* 单词替换: `:%s/\<four\>/4/g`
* 多文件替换(使用记录-回放):
  * 用多文件作为文件参数列表: `$ vim *.cpp`
  * 用q寄存器记录: `qq`
  * 替换单文件(`e`标记通知替换命令找不到匹配点不是错误,不终止): `:%s/\<four\>/4/ge`
  * 保存并开启下一个文件: `:wn`
  * 结束录制: `q`
  * 无限回放: `999@q`
* 把"Last, First"改成"First, Last": `:%s/\(\[^,]\*\), \(.\*\)/\2, \1/g`
  * `\(\)`匹配word
  * `\2`, `\1`称为反向引用, `\0`表示整个匹配
* 排序文本(当前行到空行上一行): `:.,/^$/-1!sort`
* 反转行顺序: `:g/^/m 0`
  * 将匹配到的每一行都移到(move)到0行, 效果是反转
  * 将特定范围的行反转: `:'t+1,.g/^/m 't`
  * 将标记t到本行的范围的每一行移动到标记t所在的行
* 单词统计: `gCtrl-G`
  * 可视模式选中文本: `gCtrl-G`
* 查询光标下word的Man手册: `K`
  * 分割窗口查询Man手册: `:runtime! ftplugin/man.vim`, `:Man str`
  * 若已将man.vim加入runtime: `\K`
* 删除行末多余空格: `:%s/\s\+$//`
* 编辑包含"frame\_counter"的所有C源文件: `$ vim 'grep -l frame_counter *.c'`
  * `grep`从一组文件中查找特定的单词,`-l`参数令只列出文件不列出匹配点
  * 以`grep`命令的结果做为vim的输入文件列表
  * 在所有C文件中查找"error\_string": `:grep error_string *.c`
    * 下一个匹配点: `cnext`
    * 上一个匹配点: `cprev`
    * 列出所有匹配点: `clist`
    * `:grep`使用外部程序,由`set grepprg`指定
* 显示括号匹配: `:set showmatch`
* syntax checker会列出位置列表(location):
  * 下一个位置: `:lne`
  * 上一个位置: `:lp`

## 快速输入命令行命令

* 命令行移动光标快捷键:
  * 命令行首: `Ctrl-B`
  * 命令行尾: `Ctrl-E`
  * 删除整个单词: `Ctrl-W`
  * 删除全部: `Ctrl-U`
  * 取消命令: `Ctrl-C`, `<ESC>`
* 命令行中<tab>键可以进行上下文相关的自动补全
  * 当有多个匹配时,列出匹配列表: `Ctrl-D`
  * 将词补全为最长无歧义字符串: `Ctrl-L`
* 四种历史记录功能: `:his[tory]`
  * `:`冒号命令
  * `/`, `?`搜索命令: `:his[tory] /`
  * 函数`input()`的表达式
  * 输入行
* 打开命令行窗口,使用普通vim窗口的命令编辑命令: `q:`

## 离开和回来

* `!`命令:
  * 执行`{program}`: `:!{program}`
  * 执行`{program}`并读取其输出: `:r !{program}`
  * 执行`{program}`传送文本至其输入: `:w !{program}`
  * 经由`{program}`过滤文本: `:[range]!{program}`
* 执行新的shell: `:shell`
* 信息文件viminfo设计用来储存状态信息:
  * 选项`'`用于规定你为多少文件保存标记(a-z)
  * 选项`f`控制是否储存全局标记(A-Z0-9)
  * 选项`<`控制每个寄存器内保存几行文本
  * 选项`:`保存命令行历史记录内的行数
  * 选项`@`保存输入行历史记录内的行数
  * 选项`/`保存搜索历史记录内的行数
  * 选项`!`以大写字母开关且不包含小写字母的全局变量
  * 选项`h`启动时关闭选项'hlsearch'高亮显示
  * 选项`%`缓冲匹列表
* 重返vim中断处,使用0标记: `'0`
  * 上上次离开vim的中断: `'1`
  * 上上上次离开vim的中断: `'2`
  * 类推
* 重返某曾编辑过的文件: `:ol[dfiles]`
  * 编辑列表中第2个文件: `:e #<2`
  * 或: `:bro[wse] ol[dfiles]`
* 保存和还原viminfo信息: `:wv[iminfo] {fname}`, `:rv[iminfo] {fname}`
* vim的会话保存跟编辑相关的信息,如文件列表,窗口而已,全局变量,选项以及其它,由`sessionoptions`,`ssop`指定:
  * 创建会话: `:mks[ession] vimbook.vim`
  * 还原会话: `:so[urce] vimbook.vim`
  * 从shell中启动: `$ vim -S vimbook.vim`
    * `blank`: 保留空窗口
    * `buffers`: 所有缓冲区
    * `curdir`: 当前目录
    * `folds`: 折叠
    * `help`: 帮助窗口
    * `options`: 所有选项和映射
    * `winsize`: 窗口大小
    * `resize`: 还原窗口大小
    * 若有需要在在Unix和Windows中切换工作,建议加`unix`, `slash`选项
  * 会话储存所有窗口的特性,若只存储当前窗口的特性: `:mkv[iew]`, `:lo[adview]`
    * 为同一个窗口可储存不同的视图: `:mkv [1-9]`, `:lo [1-9]`
    * 为视图指定文件: `:mkv {fname}`, `:so {fname}`
    * 选项`viewdir`储存视图文件目录
* 模式行,针对特定文件设定特定选项,在正在编辑的文件文本中添加:
  * 在C文件中添加(`/\*..\*/`是C的注释,非必须): `/\* vim:set shiftwidth=4: \*/`
  * 设定在文件起首和结尾几行内检查是否包含模式行: `:set modelines=10`
  * `:`中间为选项,因为选项中的`:`要转义为`\:`

## 寻找要编辑的文件

* 若编辑目录会自动开启文件浏览器,没有`nerdtree`好用
* 改变当前目录: `:cd`
* 显示当前目录: `:pwd`
* 默认所有窗口共享当前目录,若只改变当前窗口的本地目录: `:lcd`
* 编辑光标处文件,如C文件中的`#include <stdio.h>`,如Ruby文件中"require `date`": `gf`
  * 文件通过`path`选项中的目录寻找, 不同类型文件的`path`内容不同
  * `isfname`选项决定哪些字符可用于文件名
* 在`path`选项指定的目录查找文件: `:fin\[d] {fname}`
  * shell中启动时: `$ vim "+find stdio.h"`
* 隐藏当前缓冲区并编辑新文件: `:hid\[e] e\[dit] new.txt`
  * 若`:e new.txt`,则会提示保存缓冲区
  * 若`:set hidden`, 则无需`:hide`命令, 编辑新文件不会提示保存缓冲区
* 缓冲区:
  * 激活: 显示在窗口内,并加载文本
  * 隐藏: 不显示在窗口内,但加载文本
  * 非激活: 不显示在窗口内,不加载文本
  * 查看缓冲区列表: `:buffers`, `:ls`
    * `%`: 当前缓冲区
    * `#`: 轮换缓冲区
    * `a`: 激活缓冲区
    * `h`: 隐藏缓冲区
    * `=`: 只读
    * `-`: 不可改
    * `+`: 已修改
    * `u`: 列表外缓冲区
  * 编辑缓冲区: `:b\[uffer] 2`, `:b {fname}`
  * 编辑下一个缓冲区: `:bn\[ext]`, `:bp\[revious]`, `:bf\[irst]`, `:bl\[ast]`, `:bd\[elete]`
    * `:bn`, `:bp`, `:bf`, `:bl`, `:bd`
      * `:bd`一个缓冲区会从缓冲区列表中删除缓冲区,成为列表外缓冲区,且不出现在":buffers",但出现在":buffers!"
      * 彻底忘记一个缓冲区: `:bw\[ipe]`

## 编辑特殊文件

* 文件格式: unix, dos, mac
  * 选项`fileformats`指定vim编辑新文件时格式解析试探
  * 选项`fileformat`是vim解析的文件格式
  * 转换dos格式到unix格式: `:set ff=unix`, `:w`
  * 强制以某格式编辑文件: `:e ++ff=unix {fname}`
* 编辑超链接的文件: `gf`
  * 以新窗口编辑: `Ctrl-W f`
* 加密文件: `:X`
  * 编辑新文件时加密: `$ vim -x {fname}`
  * 取消加密: `:set key=`
  * 交换文件不加密,可设置: `:setlocal noswapfile`
    * shell: `$ vim -x -n {fname}`
  * 文件在内存中也不加密,具备读内存的人可见
  * `viminfo`文本中保存的寄存器内容也不加密
* 二进制文件: `$ vim -b {fname}`
  * 以二进制和十六进制显示: `:%!xxd`, `:%!xxd -r`复原
    * 编辑十六进制改变文本,但编辑显示的文本不改变
  * 显示光标下字符的10/16/8进制值: `ga`
  * 跳转到文件中指定字节处: `go`
* 压缩文件: `.Z`, `.gz`, `.bz2`
  * 需要安装相应程序
  * 不支持tar打包的压缩文件

## 快速插入

插入模式的快捷命令:

* 删除光标前的一个word: `Ctrl-W`
* 删除光标字符至行首第一个非空字符: `Ctrl-U`
* 光标跳转一个word: `Ctrl-<Right>`, `Ctrl-<Left>`
* 补全: `Ctrl-P`, `Ctrl-N`
  * 补全特定文本,你知道要补全某种类型的文本
    * `Ctrl-X Ctrl-F`: 文件名
    * `Ctrl-X Ctrl-L`: 整行
    * `Ctrl-X Ctrl-K`: 字典文件内的单词,由`dict`选项设置
    * `Ctrl-X Ctrl-]`: 标签
    * `Ctrl-X Ctrl-V`: vim的命令行
  * 智能补全源代码: `Ctrl-X Ctrl-O`
* 重复上次在插入模式下输入的文本: `Ctrl-A`
  * 插入文本后退出插入模式: `Ctrl-@`
* 插入光标上方字符: `Ctrl-Y`
* 插入光标正文字符: `Ctrl-E`
* 插入一个寄存器内容: `Ctrl-R {register}`
* 缩写: `:iab {short} {long}`
  * 当键入一个非单词字符时触发扩展
  * 可用于更正打字错误,如将经常打错的字缩写成正确的字
  * 列出可用的所有缩写: `:ab\[breviate]`
  * 删除缩写: `:una\[bbreviate]`
  * 清空所有缩写: `:abc\[lear]`
  * 避免缩写再映射: `:norea\[bbrev]`
* 插入下一个字面意义上的字符: `Ctrl-V`
  * 插入`?`: `Ctrl-V 127`, `Ctrl-V x7f`, `Ctrl-V 0123`
  * 插入unicode字符: `Ctrl-V u1234`
* 插入二合字母: `Ctrl-K`
  * 插入版权符: `Ctrl-K Co`
  * 列出所有的二合字母: `:digraphs`
  * 自定义二合字母: `:dig\[raph] An ￥`
* 在插入模式下执行普通模式命令: `Ctrl-O`

## 编辑带格式的文本

* 自动断行: `:set textwidth=80`, `:set tw=80`
  * `wrap`选项是多行显示文本,实际是一行
  * 当删除某行的某些字符时,vim不会重排此段落
  * 重排文本: `gq`
    * 可视,移动,文本对象
    * 如重排段落: `gqap`
* 对齐文本:
  * `:\[range]ce\[nter] {width}`
  * `:\[range]ri\[ght] {width}`
  * `:\[range]le\[ft] {margin}`
  * 使用外部程序过滤文本: `:%!fmt`
  * \[width]使用`textwidth`选项来确定要居中行的宽度
* 增加当前行的缩进: `>>`
  * 为4行增加缩进: `4>>`
* 更换制表位: `:retab 8`
* 当设置`wrap`选项,长行分成多行显示:
  * `g0`: 移动到当前行的第一个可见字符
  * `g^`: 移动到当前行的第一个非空白的可见字符
  * `gm`: 移动到当前行的中点
  * `g$`: 移动到当前行的最后一个可见字符
  * `gj`, `gk`: 上下行
* 若`nowrap`, 则看不到整行,若`wrap`, 会严格按照字符数折行,导致最后一个单词被中断开.
  * 设置`:set linebreak`会在适当的地方折行
  * `breakat`指定可以用来作为插入换行地点的字符
* 变段为行: `:g/./,/^$/join`
  * `:g/./`: 搜索至少含有一个字符的所有行
  * `,/^$/`: 一个范围,从当前行到一个穿行
  * 若段落分隔符含有空白符: `:g/./,/^\s\*$/join`
* 编辑表格时,设置虚拟编辑: `:set virtualedit=all`, `:set ve=all`更方遍列编辑
  * 确保新字符占据了正确的屏幕空间,虚拟替换: `gr`, `gR`

## 重复

* 选中上次可视模式下选中的文本: `gv`
* 在一个数字上用`Ctrl-A`增加数字
  * 加4: `4Ctrl-A`
  * 减: `Ctrl-X`
  * `nrformats`, `nf`指定可加减的内容: `alpha`, `octal`: 010, `hex`: 0xff
* 对文件参数列表中每个文件执行命令:
  * 替换列表中每个文件中的word为neword: `:argdo %s/\<word\>/neword/ge | update`
    * `update`将改动存盘
  * 在所有窗口内执行其参数所规定的命令: `:windo`
  * 对所有缓冲区执行其参数所规定的命令: `:bufdo`
* 在shell脚本使用vim: `$ vim -e -s {fname} < {script.vim}`
  * `-e`运行vim在Ex模式, `-s`运行在安静模式
  * `{script.vim}`中存放命令行命令
  * 从标准输入计取: `$ ls | vim -`
  * 从标准输入并执行脚本: `$ ls | vim -S {script.vim} -`
  * 若`{script.vim}`存放普通模式命令: `$ vim -s {script.vim} {fname}`
    * `-s`与`-e`同时使用,意义不同
  * 将操作保存到脚本中: `$ vim -w {script.vim} {fname}`
    * `-w`是追加, `-W`是重写

## 查找命令及模式

* 若在单次查找时,指定`\cword`则忽略大小写, 指定`\Cword`则大小写敏感,无关是否设置`ignorecase`或`smartcase`
* 搜索至文件首尾折返: `:set wrapscan`, `:set ws`
* 搜索设置偏移行: `/vim/2`
  * 搜索设置偏移字符,从匹配尾开始算: `/vim/e2`
  * 搜索设置偏移字符,从匹配首开始算: `/vim/b2`
  * 重复前一次的搜索模式: `/<cr>`
  * `?vim?2`, `?vim?e2`, `?vim?b2`
  * 匹配模式:
    * `\*`: 匹配0次或无穷次
    * `+`: 匹配1次或无穷次
    * `=`: 匹配0次或1次, 相当Ruby正则中的`?`
    * `{n,m}`: 匹配n到m次
      * `{-n,m}`: 匹配n到m次,尽可能少匹配
      * `{-}`: 匹配0到无穷次,尽可能少匹配
    * `|`: 匹配A或B
    * `&`:
    * `\[]`, `\[^]`: 匹配包含或不含在其中的字符
      * `\e`: <esc>, `\t`: <tab>, `\r`: <cr>, `\b`: <bs>
    * 预定义范围, 加前缀`\_`可兼任匹配换行符:
      * `\a`: 字母
      * `\d`: 数字, `\D`: 非数字
      * `\x`: 十六进制数, `\X`: 非十六进制数
      * `\s`: 空白符, `\S`: 非空白符
      * `\l`: 小写字母, `\L`: 非小写字母
      * `\u`: 大写字母, `\U`: 非大写字母
    * 字符类:
      * `\f`: 匹配文件名, `\F`: 不包括数字, 选项: `isfname`
      * `\i`: 匹配标识符, `\I`: 不包括数字, 选项: `isident`
      * `\k`: 匹配关键字符, `\K`: 不包括数字, 选项: `iskeyword`
      * `\p`: 匹配可显示字符, `\P`: 不包括数字, 选项: `isprint`
    * `\n`: 匹配换行符, `\_s`: 匹配空格或换行符
      * 找寻位于一对双引号间,可能分隔成数行的文本: `/"\_\[^"]*"`
    * `\*\*`: 匹配所有子目录

## 折叠

* 折叠:
  * 创建折叠: `zf`
    * 折叠段落: `zfap`
  * 打开折叠: `zo`
  * 关闭折叠: `zc`
* 增加/减少折叠层级`foldlevel`:
  * 减少折叠: `zr`
  * 增加折叠: `zm`
  * 打开全部折叠: `zR`
  * 关闭全部折叠: `zM`
* 快速折叠:
  * 快速禁止折叠: `zn`
  * 快速恢复原来的折叠: `zN`
  * 切换: `zi`
* 折叠方式:
  * 依缩进折叠: `:set foldmethod=indent`, `:set fdm=indent`
  * 依标志折叠: `:set fdm=marker`
    * 标志为: `{{{`, `}}}`, 编号标志: `{{{1`, `}}}1`
  * 依语法折叠: `:set fdm=syntax`
  * 依表达式折叠: `:set fdm=expr`
  * 依是否改动折叠

## 在代码间移动

* 若已为源代码生成标签: `$ ctags \[-R] \*.c`
  * 跳转到标签(`{tagname}`为空时,默认第一个标签): `:tag {tagname}`
  * 分割窗口: `:stag {tagname}`
    * `{tagname}`可以是模式,正则
    * 下一个标签: `tn\[ext]`
    * 上一个标签: `tp\[rev]`
    * 标签选择列表: `ts\[elect]`
    * 第一个标签: `tf\[irst]`
    * 最后一个标签: `tl\[ast]`
  * 跳转到光标下字符标签: `Ctrl-]`
  * 分割窗口: `Ctrl-W ]`
  * 显示经过的标签列表: `:tags`
  * 回跳到标签: `Ctrl-T`
  * 使用多个标签文件: `:set tags=`
* 为标签打开预览窗口: `:ptag {tagname}`
  * 关闭: `:pc\[lose]`
  * 显示光标下标签: `Ctrl-W }`
  * 在预览窗口中编辑: `:ped\[it] {tagname}`
  * `previewheight`选项指定预览窗口高度
  * 在预览窗口显示匹配: `:ps\[earch] {tagname}`
* 在代码间移动,将`\[]`与`()`, `{}`, `\[]`混用: `\[#`, `]#`
  * 在代码块间移动: `\[{`, `\[}`, `]{`, `]}`
  * 在括号内移动: `\[(`, `\[)`, ...
  * 在注释间移动: `\[/`, `]/`
* 查询光标下全局标识符: `\[I`
* 查询光标下的宏: `\[D`
* 查询光标下的局部标识符: `gD`
  * 只在当前函数查找: `gd`

## 编辑程序

* `:make {args}`
  * 下一个错误: `:cn`
  * 上一个错误: `:cp`
  * 详细信息: `:cc {N}`
  * 错误信息预览: `:cl`
  * `makeprg`选项指定执行的程序
  * 旧的出错信息列表: `:col\[der]`
  * 新的: `:cnew\[er]`
  * 若更换编译器,配置`errorformat`选项
* C风格的缩进: `:set cindent`
* 格式化当前行: `==`
  * 格式化文本: `=`
    * 格式化当前`{}`: `=a{`, `=i{`
    * 格式化整个文件: `gg=G`
* 设置缩进风格: `:set cinooptions+={2`
* 在插入模式下插入缩进: `Ctrl-T`
  * 减少缩进: `Ctrl-D`
* 普通模式下缩进: `>>`, `<<`
  * 缩进`{}`: `>i{`, `>a{`
  * `a`连带`{}`缩进, 而`i`只缩进`{}`内的内容
* 排版注释格式: `gq]/`
  * `]/`移动到注释尾的动作
  * `formatoptions`选项
* 定义注释样式: `:set comments=://`
  * `{flags}:{text}`
  * 三部分注释: `:set comments=s1:/\*,mb:\*,ex:\*/`

## GUI

* 指定窗口位置: `:winpos {N1} {N2}`
* 指定窗口大小: `:set lines columns`
* 从shell的vim启动GUI: `:gui`
* GVim使用`.gvimrc`配置文件: `:e $MYGVIMRC`

## 撤消树

当撤销了一些改变,又进行了一些新的改变,新的改变构成了撤销树的一个分支.即便如此,每一次改变依时间顺序
都会有一个唯一的编号,通过`:undo {N}`跳转改变编号.通过`g+`, `g-`在时间顺序的改变上回溯或前进.而`u`和
`Ctrl-R`只在最近的分支上前进和回溯, 而不会跳转到别的分支上.

* undo(`f`代表文本状态): `:earlier 1f`
* redo(数字1): `:laster 1f`
  * undo/redo使用最近使用的分支,在树内上下移动.
  * 回到10秒前: `:earlier 10s`
  * `m`, `h`, `d`分别代表分时天
* 撤消树内任意跳转(`{N}`为在树内的改变号): `:undo {N}`
* 查看撤销树的分支数: `:undol\[ist]`

## 创建新的命令

## regexp

* 匹配行首/尾: `^`, `$`
* 匹配任意单个字符: `.`

## vim encoding

### windows与cp936

* windows的中文默认编码是cp936，即GBK。
* notepad默认编码有ANSI,Unicode,Unicode-bigeditian,utf-8。
* ANSI对英文字符采用ansi，对中文字符采用GBK，即cp936。
* unicode在vim编码中称为ucs-2le,unicode-bigeditian称为ucs-2。
* utf-8是unicode的最佳实现方式之一。
* 复杂的是notepad保存文件会加上BOM，对于unicode指示大端小端。

### unicode

* unicode为所有字符提供唯一编码，类似字典。因此任意字符编码先转为unicode再转为别的字符集。
* 存储人类所有字符需要4个字节，考虑到空间节省目的，便有utf-8编码实现。
* utf-8：单字节首0,存储ascii，两字节(首110,次10),三字节(首1110,次10,末10),...
* 因为编码方式，很容易解码一个utf-8编码的文本而不会产生歧义。

### vim

* vim的win版默认encoding=cp936,也就是说其默认以cp936编码显示字符。而无论文本以什么解码。
* fileencodings=ucs-bom指定以什么编码解码文本。默认把字符集少的放前面，发生解码错误再试着换下一个字符集。
* 正确解码后，会设置相应的fileencoding。再以encoding指定的编码编码显示。
* 再简单说：以fileencodings解码成unicode，再将unicode解码成encoding。
* 当fileencodings无法正确解析时，会默认以encoding即系统默认编码直接进行解码，而不转为unicode。
* 问题的复杂是：BOM。也就是说BOM字符没办法解析为cp936
* 虽然有些问题没弄明白，但设置encoding=utf-8会解决大部分乱码。只是菜单状态栏会有乱码。
* 大部分乱码发生在操作系统间文件的交换。
* 最好的方案是所有的操作系统和编辑器都设置成utf-8。至少大部分Linux系统是这样。
* 但windows作为中国市场垄断的操作系统，要充分考虑兼容性。而且windows进入中国市场较早，当时中文默认编码是GBK，所以出于兼容性考虑，中文版的默认编码是GBK，即cp936。
* 兼容性是善也是恶。
