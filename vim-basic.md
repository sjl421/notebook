## Vim初步

* 移动光标: h, j, k, l
* 删除光标处/光标左字符: 'x', 'X'
* 删除一行: 'dd'
* 删除行末换行符: 'J'
* 撤消: 'u'
* 重做: 'Ctrl-R'
* 光标前插入: 'i'
* 行首第一个非空字符插入: 'I'
* 光标后插入: 'a'
* 行尾并插入: 'A'
* 光标正方开始新行: 'o'
* 光标上方开始新行: 'O'
* 指定重复次数: '3x'
* 退出: 'ZZ'
* 帮助: ':h'
* 标签退栈: 'Ctrl-T'
* 跳转至光标: 'Ctrl-]'
* 跳转到前一个位置: 'Ctrl-O'
* 清屏并重绘屏幕: 'Ctrl-L'

## Vim 移动

'iskeyword'选项界定词word. WORD理解为由空白字符隔开的字串.

* 右跳一个word/WORD: 'w', 'W'
* 左跳一个word/WORD首: 'b', 'B'
* 右跳一个word/WORD尾: 'e', 'E'
* 左跳一个word/WORD尾: 'ge', 'gE'
* 行首/尾: '0', '$'
* 行的第一个非空白字符: '^'
* 行内单字符右/左查找: 'f', 'F'
* 行内右/左查找并置光标于字符前: 't', 'T'
* 括号匹配: '%'
* 文件首: 'gg'
* 文件末: 'G'
* 指定行: '3G'
* 百分比定位: '50%'
* 屏幕顶/中/底: 'H', 'M', 'L'
* 确定当前位置(若`set ruler`,则无必要): 'Ctrl-G'
* 向上/下滚动一屏: 'Ctrl-F', 'Ctrl-B'
* 向上/下滚动半屏: 'Ctrl-U', 'Ctrl-D'
* 向上/下滚动一行: 'Ctrl-E', 'Ctrl-Y'
* 将光标所在行置于顶屏/中屏/底屏: 'zt', 'zz', 'zb'
* 查找/反向查找: '/', '?'
* 查找整个word(\<匹配word头,\>匹配word尾): '/\<the\>'
  * 下/上一个匹配: 'n', 'N'
* 右/左查找光标处整个/部分word: '\*', '#', 'g\*', 'g#'
* 标记/跳转到标记: 'm[a-zA-Z]', ''[a-zA-Z]'
  * 'a-z'标记是单个文件独有的,
  * 'A-Z'标记被多个文件共有
* 列出所有的标记列表: ':marks'
  * 特殊标记: 跳转前的光标位置', 最后编辑的光标位置", 最后修改的开始位置[, 最后修改的结束位置]
  * 列出标记位置: ':marks [a-zA-Z]'
* 跳回"较老/新"的位置: 'Ctrl-O', 'Ctrl-I'
* 列出可跳转位置列表: ':jumps'

## 小改动

* 删除一个word(操作符+动作): 'dw', 'd4w', 'd$'
* 修改word(删除并插入): 'cw', 'c4w'
* 删除到行尾: 'D'
* 修改到行尾: 'C'
* 修改单字符: 's'
* 修改整行: 'S'
* 替换单字符: 'r'
* 持续替换直至esc: 'R'
* 重复最后修改命令: '.'
* 字符/行/列块可视模式: 'v', 'V', 'Ctrl-V'
  * 跳到被选择文本的另一端: 'o', 'O'
* 粘贴文本(行下/光标右,行上/光标左): 'p', 'P'
* 交换两个字符: 'xp'
* 拷贝文本: 'y'
  * 拷贝一行: 'yy', 'Y'
* 剪贴板: '"\*y', '"\*p'
* word中删除word: '[dcy]iw', '[dcy]aw'
* sentence中删除sentence('as'包括sentence后的空白字符): '[dcy]is', '[dcy]as'
* 执行修改:
  * 操作符+动作: 'd$'
  * 可视模式
  * 文本对象: 'daw'
* 转换大小写: '~'
* 列出当前已定义映射: ':map'
* 列出选项: ':options'
* 恢复选项默认值('&'): ':set iskeyword&'
* 使用寄存器: '"[a-z]', '"fyas'/'"fp'
* 添加到文件(或可在可视模式选中文本): ':write >> logfile'

## .vimrc

* 高亮查找的字符: 'set hlsearch'
* 增量式查找: 'set incsearch'
* 查找字符忽略大小写: 'set ignorecase'
* 遇文件头尾循环查找: 'set wrapscan'
* 设置在插入模式下允许<BS>删除光标前字符的位置: 'set backspace=indent,eol,start'
  * 分别表示: 行首空白符,换行符,插入模式开始前字符
* 新行缩进: 'set autoindent'
* 覆盖文件时保留备份: 'set backup', 'set backupext=.bak', 'set backupdir=/path/to/your/backupdir'
* 保留原始文件: 'set patchmode=.orig'
* 保存命令和查找模式的历史: 'set history=50'
* 在窗口右下角显示光标位置: 'set ruler'
* 窗口右下角显示未完成命令: 'set showcmd'
* 纯文本行超78字符自动换行: 'autocmd FileType text setlocal textwidth=78'
* 禁止回绕行: 'set nowrap'
  * 一次右滚10个字符: 'set sidescroll=10'
* 设置可换行移动命令: 'set whichwrap=b,s'
  * 'b'是<BS>键, 's'是<SPACE>键, 
  * '<'是<Left>, '>'是<Right>, 
  * '['是插入模式中的<Left>, ']'是插入模式中的<Right>
* 显示TAB键和行尾符'$': 'set list'
  * TAB显示为'^I', 行尾符显示为'$'
  * 自定义显示: 'set listchars=tab:>-,trail:-'
* 界定word: 'set iskeyword+=-'
* 设定命令行高度: 'set cmdheight=2'

## 语法高亮

* 手动设置文件类型: ':set filetype=fortran'
  * 在文件首和文件末添加"# vim: syntax=ruby"
* 设置背景色: 'set background=dark/light'
* 更换配色主题: 'colorscheme evening'
* 色彩:
  * 'term'    : 黑白终端
  * 'cterm'   : 彩色终端
  * 'ctermfg' : 彩色终端前景色
  * 'ctermbg' : 彩色终端背景色
  * 'gui'     : GUI版本属性
  * 'guifg'   : GUI版本前景色
  * 'guibg'   : GUI版本背景色
  * 用绿色显示注释: ':highlight Comment ctermfg=green guifg=green'
* 测试常用色彩组合: ':runtime syntax/colortest.vim'
* 语法高亮: 'syntax enable'
* 临时关闭高亮: ':syntax clear'
* 关闭高亮: ':syntax off'
* 手动设置高亮: ':syntax manual', ':set syntax=ON'

## 编辑多个文件

* 编辑另一个文件: ':edit fname.txt'
* 保存文件: ':write [fname.txt]'
* 文件列表: '$ vim f1.txt f2.txt f3.txt'
  * 默认显示第一个文件,编辑第二个文件: ':next', ':n'
  * 保存第一个文件并编辑第二个文件: ':wnext', ':wn'
  * 查看文件列表: ':args'
  * 编辑前一个文件: ':prev', ':wprev', ':p', ':wp'
  * 编辑最后一个文件: ':last'
  * 编辑第一个文件: ':first'
  * 后跳两个文件: ':2next'
  * 编辑另一个文件列表: ':args t1.txt t2.txt t3.txt'
  * 两文件间跳转(不改变文件列表位置): 'Ctrl-^'
  * 为列表中每个文件创建窗口: ':all', ':vert[ical] all'
  * 水平分割窗口并编辑next/prev文件,first/last文件: ':spr[evious]', ':sn[ext]', ':sfir[st]', ':sla[st]'
* 跳转到上次离开这个文件时位置: ''"'
* 跳转到最后一次修改文件的位置: ''.'
* 只读模式启动Vim: '$ vim -R file'
  * 严格的只读: '$ vim -M file'
* 另存为: ':sav[eas] new_name.txt'

## 分割窗口

* 水平分割窗口: ':sp[lit] f1.txt', ':new f1.txt', 'Ctrl-W s'
* 垂直分割窗口: ':vsp[lit] f1.txt', ':vne[w] f1.txt', 'Ctrl-W v'
* 分割窗口并指定大小: ':3sp f1.txt'
* 改变窗口大小(接受计数前缀): '3 Ctrl-W +', '3 Ctrl-W -', '3 Ctrl-W \_'
  * 跳转窗口('t'顶,'b'底): 'Ctrl-W h/j/k/l/t/b'
  * 移动窗口: 'Ctrl-W H/J/K/L'
  * 转窗口为tab页面: 'Ctrl-W T'
* 关闭窗口: ':clo[se]'
* 关闭其余窗口: ':on[ly]'
* 退出/保存所有窗口: ':qa[ll]', ':wa[ll]', ':wa[ll]'
* 为文件列表中每个文件打开一个窗口: '$ vim -o f1.txt f2.txt f3.txt'
* 用vimdiff显示文件差异: '$ vimdiff f1.txt f2.txt'
  * 在内部启动比较模式: ':vert[ical] diffs[plit] f2.txt'
  * 有当前文件的补丁: ':vert[ical] diffp[atch] f2.txt patch.txt'
  * 关闭比较模式的滚动绑定: 'set noscrollbind'
  * 跳转/反向跳转到修改点: ']c', '[c'
  * 消除差异: ':diffu[pdate]'
  * 文本左右拷贝: 'dp', 'do'
    * 在左边窗口,从左边拷贝到右边: 'diff put', 'dp'
    * 在右边窗口,从左边拷贝到右边: 'diff obtain', 'do'
* 打开/关闭折叠: 'zo', 'zc'
* 新标签页编辑: ':tabe[dit] new_file'
  * 切换标签页: 'gt'
  * 任何会打开窗口的命令前都可前加':tab',在新标签页打开: ':tab split', ':tab help gt'
  * 关闭其余标签页: ':tabo[nly]'
* 指定什么时候对最后一个窗口显示状态条: 'set laststatus=[012]'

## GUI(.gvimrc)

* 去工具栏: ':set guioptions-=T'
* 去菜单栏: ':set guioptions-=m'
* 去右边滚动条: ':set guioptions-=r/R'
* 去左边滚动条: ':set guioptions-=l/L'
* X-window系统有两个地方可以在程序间交换文本:
  * 当前选择区:在一个程序选中文本,鼠标左键在另一个程序粘贴, '"\*'寄存器表示
  * "真"剪贴板:选中文本,通过菜单的copy/paste进行复制和粘贴, '"+'寄存器
* 设置鼠标模式: ':behave xterm/mswin'

## 大修改

* 记录和回放命令:
  * 启动记录: 'q[a-z]'
  * 终止记录: 'q'
  * 执行记录: '[n] @[a-z'
  * 执行上次执行的记录: '@@'
  * 记录到大写字母寄存器[A-Z]的命令会追加到小写字母寄存器[a-z]中
* 命令记录作为文本内容保存在寄存器,可以先写出命令再复制到寄存中,再执行记录
* 字符串替换: ':[range]s/from/to/[flags]'
  * 默认当前行,'%'为全部行
  * 默认只替换第一个匹配点,'g'替换所有匹配点
  * 默认不提示替换,'c'提示
  * 替换n1到n2行的所有this为that: ':n1,n2s/this/that/g'
  * 替换n行的this为that: ':ns/this/that/g'
  * 替换当前行到最后一行: ':.,$s/this/that/g'
  * 替换当前章节中所有的'grey'为'gray': ':?^Chapter?,/^Chapter/s=grey=gray=g'
    * '?^Chapter?' 向前搜索章节开始标志
    * '/^Chapter/' 向后搜索章节开始标志
    * 用模式匹配来选定范围
    * 若不替换标题: ':?^Chapter?+1,/^Chapter/-1s=grep=gray=g'
      * 加减号用于设定偏移
      * 替换当前行下面第三行到文本末倒数第五行的范围: ':.+3,$-5s/this/that/g'
  * 替换两个标记间的行文本: ':'a,'bs/this/that/g'
  * 在可视模式中选中文本,若':'进入命令行模式,则可得到范围: ':'<,'>s/this/that/g'
    * ''<', ''>'是标记,标记选中文本的开始与结尾处
  * '5:'用于指定行数,得到范围: ':.,.+4s/this/that/g'
* ':write'命令不指定范围时,默认写入整个文件
  * 只写入当前行: ':.write'

## regexp

* 匹配行首/尾: '^', '$'
* 匹配任意单个字符: '.'
