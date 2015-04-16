# emacs

## base

备份文件(`filename~`), 自动保存文件(`filename`)

* `c-x c-f` : 打开文件
* `c-x c-s` : 保存文件
* `c-x c-b` : 列出缓冲区
* `c-x c-c` : (`save-buffers-kill-emacs`)退出, `y`保存; `n`放弃保存; `!`保存所有; `.`保存并放弃其余q取消
* `c-x c-q` : 变缓冲区只读为可读写emacs
* `c-h t`   : `tutorial`
* `c-g`     : 取消指令
* `c-x`     : 字符扩展，`c-x`后输入另一个字符或组合键
* `m-x`     : 命令名扩展，`m-x`后输入一个命令名, `execute-extended-command`
* `c-h m`   : 显示主模式的文档
* `c-q tab` : 插入tab符
* `c-q c-j` : 插入`<ret>`符
* `c-m-\\`  : 缩进区域文本

## move

键盘宏： `<f3>`开始录制宏，`<f4>`结束宏录制，`c-x e e e`(或`c-3 <f4>`)重复执行宏3次

移除的内容可以找回，删除的内容则被扔掉了。一般大段内容是移除，一个字符是删除

* `c-v`                     : 下屏
* `m-v`                     : 上屏
* `c-l`                     : 中屏
* `c-p/n`                   : 上/下行
* `c-f/b`                   : 前/后字符
* `m-f/b`                   : 前/后字
* `c-a/e`                   : 行首/尾
* `m-a/e`                   : 句首/尾
* `m-</>`                   : 文首/尾
* `c-u n c-f`               : 指令重复
* `c-u n c-v/m-v`           : 上下n行
* `c-u n char`              : 输入n个char字符
* `c-n char/c-f`            : 等价于c-u 9
* `<del>`                   : 删除光标前的一个字符
* `c-d`                     : 删除光标后的一个字符
* `m-<del>`                 : 移除光标前的一个词
* `m-d`                     : 移除光标后的一个字符
* `c-k`                     : 移除从光标到行尾间的字符
* `m-k`                     : 移除从光标到句尾间的字符
* `c-y`                     : 粘贴被最后一次移除的内容
* `m-y`                     : 粘贴上上次(可无限回溯)移除的内容
* `c-@/c-spc   motion  c-w` : 移除选中的内容，类似vim的visual
* `c-spc`                   : 设置mark, (set-mark-command)
* `c-x u`                   : 撤销命令，没改变文字的命令不算，从键盘输入的字符以组(20字符)为单位
* `c-x b`                   : 切换缓冲区
* `c-x s`                   : 保存多个缓冲区
* `c-x i`                   : 在光标处插入文档
* `c-x f`                   : 设置行宽，默认70字符(英文,set-fill-column)
* `c-x m`                   : 写信
* `m-q`                     : 手动折行
* `c-s`                     : 向前搜索
* `c-r`                     : 向后搜索
* `c-x c-x`                 : 跳回mark点，并mark中间内容(exchange-point-and-mark)
* `c-u c-spec`              : 依序跳回mark点
* `c-u n c-l`               : 将光标所在行移至屏幕第n行
* `c-x 1`                   : 保留1个窗口
* `c-x 2`                   : 上下分隔窗口
* `c-x 3`                   : 左右分隔窗口
* `c-x 4`                   : 一串与视窗有头的指令
* `c-x 4 a`                 : 新窗口写changeLog
* `c-x 4 b`                 : 新窗口打开buffer
* `c-x 4 d`                 : 新窗口打开dired
* `c-x 4 f`                 : 在新窗口中打开文件
* `c-x 4 m`                 : 新窗口写mail
* `c-x 4 r`                 : 新窗口打开只读文件
* `c-x 5`                   : 扩展到X的视窗
* `c-x 5 2`                 : 再开另一个X视窗
* `c-x 8 char`              : 输入一些特殊字符
* `c-x o`                   : 切换视窗
* `c-m-v`                   : 移动非光标窗口
* `<esc><esc><esc>`         : 离开递归编辑
* `m-!`                     : 执行shell-cmd
* `c-u m-!`                 : 将shell-cmd输出到光标处
* `m-! etags`               : 为源文件生成tags
* `m-.`                     : find tag
* `c-u m-.`                 : find next tag
* `m-spc`                   : 将光标附近的空白缩成一个
* `c-x c-o`                 :
* `c-j`                     : newline-and-indent

## help

1，使用手册，2，Elisp手册，3，Elisp入门

浏览手册:

* `h`                        : 教学式帮助
* `?`                        : 指令帮助
* `m:menu item`              : 页面所有以`*`开头的
* `n`                        : 下个节点
* `p`                        : 上个节点(`n/p`只在本层节点间上下)
* `u`                        : 上层节点
* `d`                        : 回根节点
* `<`                        : 文件顶节点
* `>`                        : 文件末节点
* `[`                        : 下节点
* `]`                        : 上节点(`[/]`在全部节点间上下)
* `l`                        : 节点历史回溯
* `q`                        : 离开
* `b`                        : 节点开始
* `e`                        : 节点结束
* `<spac>`                   : 下屏
* `<backspac>`               : 上屏
* `c-h i m emacs-lisp-intro` : elisp入门

帮助快捷键:

* `c-h c/k`     : Hotkey对应的指令信息，`c`简`k`详
* `c-h w`       : 指令对应的HotKey信息
* `c-h a`       : 列出包含字符串的所有指令
* `c-h f`       : describe function
* `c-h v`       : describe variable
* `c-h b`       : 列出所有的keybindings
* `c-h m`       : mode help
* `c-c c-h`     : 列出以`c-c`开头的所有keybindings
* `c-c <chr>`   : 留给user
* `c-c c-<chr>` : 留给package
* `c-h i`       : Info文档信息
* `c-x r c-h`   : 列出以`c-x r`为开始的所有快捷键

## m-x cmd

* `m-x shell`                 : 进入`shell mode`
* `m-x dired`                 : 目录编辑
* `m-x compile/gdb`           : 编译/调试
* `m-x mail/rmail`            : 发送/读取信件
* `m-x calendar/diary`        : 日历和日记
* `m-x man`                   : Man Page
* `m-x ielm`                  : `Inferior Emacs Lisp Mode`进入`ELisp`交互模式
* `m-x gnus`                  : News
* `m-x erc`                   : IRC
* `m-x recover-file`          : 恢复文件
* `m-x xxx-mode`              : 启动模式
* `m-x re-search-forward`     :
* `m-x re-search-backward`    :
* `m-x search-forward`        :
* `m-x search-backward`       : re表示正则
* `m-x replacs-string`        : 字符串替换
* `m-x customize-themes`      : 定制主题
* `m-x lisp-interaction-mode` : `c-j`(`eval-print-last-sexp`)运行表达式
* `c-m-x/c-x c-e`             : 执行表达式(当光标位于闭括号后)

## emacs irc

`m-x erc`

* `/help`
* `/join #ruby`                           : 加入频道
* `/part [#channel[, reason]]`            : 离开频道，告知原因
* `/away [reason]`                        : 暂时离开，告知原因
* `/quit [reason]`                        : 退出服务器，告知原因
* `/invite [nick[, #room]]`               : 邀请某人到指定房间
* `/kick [#room[, nick[, reason]]]`       : 踢出某人，附上原因
* `/topic [#room[, topic]]`               : 改变房间主题
* `/list`                                 : 查看服务器上所有房间及主题
* `nick:message`                          : 指定用户发信息, 频道可见
* `/msg nick message`                     : 私聊, 只用户可见
* `/query nick`                           : 单开窗口,私聊
* `/msg NickServ REGISTER password email` : 注册用户
* `/msg NickServ SET PASSWORD newpw`      : 修改密码
* `/nick newnick`                         : 改名字
* `/names [channel]`                      : 查看所有用户
* `/whois nick`                           : 查看某人资料
* `/whoami`                               : 自已
* `/who`                                  :
* `c-c c-n`                               : 列出用户
* `c-c c-b`                               : 切换频道
* `c-c c-j`                               : 加入频道
* `c-c c-p`                               : 退出频道

## emacs package

* `m-x list-packages`:            列出包
* `m-x package-install`:          安装包

安装在`~/.emacs.d/elps/`

package-menu-mode下可用的命令:

* `<ret>` : describe-package
* `i`     : package-menu-mark-install
* `u`     : package-menu-mark-unmark
* `U`     : package-menu-mark-upgrades
* `d`     : package-menu-mark-delete
* `x`     : package-menu-execute
* `r`     : package-menu-refresh

## mode

* 一个major mode可搭配多个minor mode

目录编辑

* 删除文件
  * `d`                : 加删除标记
  * `u`                : 取消标记
  * `x`                : 删除有标记文件
  * `'`                : 标记auto-save file
  * `~`                : 标记backup file 前面加`-`可取消相应标记
  * `%d regexp`        : 标记匹配正则的文件
* 访问文件
  * `f`                : `dired-find-file`显示在原视窗
  * `o`                : `dired-find-file-other-window`显示在新视窗，光标移动
  * `c-o`              : `dired-display-file`新视窗，不移动
  * `v`                : dired-view-file流档案，只读
* 标记文件
  * `m`                : `dired-mark`标记光标处文件`*`
  * `*`                : `dired-mark-exectables`标记所有可执行文件
  * `@`                : `dired-mark-symlinks`标记所有符号链接
  * `/`                : `dired-mark-directories`标记所有目录
  * `esc-del markchar` : `dired-unmark-all-files`取消`markchar`的标记
  * `c old new`        : `dired-change-marks`将old标记换为new标记
  * `%m regexp`        : `dired-mark-files-regexp`标记匹配正则的文件

## org-mode

编辑`.org`文件

* `*`: 作为大纲
* `m-<ret>`: 插入同级标题
* `m-<right>/<left>`:  降级/升级标题
* `<tab>`: 折叠
* `-`, `+`, `*`: 无序
* `1.`, `1)`: 有序
* `::`: 描述列表
* `*abc*`, `/abc/`, `+abc`, `_abc_`, `=abc=`:  粗体 斜体 删除线 下划线 等宽体
* `E＝mc^2 | H_2 O`: 上标 | 下标
* `http://www.google.com`, `[[http:www.google.com][google]]`: 网址链接
* `file:~/.emacs.d/init.el::n`, `[[file:~/.emacs.d/init.el][init.el]]`: 文件链接, '::n'表示第n行
* `news:comp.emacs`: news链接
* `irc:/irc.freenode.net/#ruby`, `[[irc:/irc.freenode.net/#ruby][#ruby]]`: irc链接
* `mailto:abc@def.com`, `[[mailto:abc@def.com][abc]]`: 写信链接
* `|`, `<tab>`, `|-`: 表格分隔线, 内切换
* `#+TBLFM: $4=vmean($2..$3)`: 表格运算，在第4列求第2、3列平均值
* `<s`,`<tab>`: 插入程序代码
* `c-c '`: 编辑代码
* `# abcdefg`, `c-c ;`, `#+BEGIN_COMMENT … #+END_COMMENT`: 注释

## configure Emacs

* 变量式定制，`(setq var value)`
* 函数式定制，`(func value)`
* 快捷键，全局`(global-set-key key 'cmd)`, mode`(define-key modename-map key 'cmd)`
* key多为`(kbd "m-spc")`, `kbd`是键盘宏
* 添加hook函数`(add-hook 'modename-hook 'func)`，在进行某major-mode时会自动调用hook函数
* 将目录添加到require列表中`(add-to-list 'load-path "path" t)`, 't'表示追加到末尾
* 单文件扩展提供require加载方式`(require 'template)`
