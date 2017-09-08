# VSCODE

## 快捷键

* `C+P`, 快速打开, 可模糊搜索文件名, 输入`>`可转为命令面板
* `C+S+P`, 打开命令面板, 命令以组划分, 有相同前缀
  * `git: xxx`, 输入`git`相关的命令
* `C+,`, 打开配置文件
* `C+\`, 在右侧打开垂直窗口

* `A+1-9`, 切换标签

## 命令

`C+P`进入快速打开.

* `ext`, 关于插件
* `edt`, 关于编辑窗口
* `task tkname`, 执行任务, `A-T`
* `term tername`, 打开终端
* `@ symname`, 跳转到文件的符号处, `A-F`
* `@: symname`, 同上, 以不同类别的形式
* `# symname`, 跳转到项目的符号处(必须是可公开访问的), `A-S`

## 插件

`C+P`, `ext install plugin_name`即可安装插件.

1, `Settings Sync`, 用来同步所有的配置和插件到`github gist`上, 因此需要`tokens`.

`S+A+U`, 上传配置, `S+A+D`, 下载配置.

2, `Project Manager`, 进行项目管理的插件, 通过`C+S+P`在命令面板中输入, 所有的命令都
支持模糊匹配.

* `Project Manager: Save Project`, 保存项目, 输入名称
* `Project Manager: Edit Projects`, 查看和修改已经保存的项目
* `Project Manager: List Projects to open`, 列出并选择要打开的项目

3, `Code Runner`, 运行文件

* `C-A-R`, 运行文件代码或选中的行代码
* `C-A-J`, 选择语言来运行代码

4, `LaTeX Workshop`, 支持写`latex`文档

5, `Git Lens`

6, `REST Client`, 可以写`url`, 通过命令执行`send request`, 并显示`response`

7, `ESLint`, 提供`js`代码风格检测

## 配置

除了全局的`User`配置外, 还有`Workspace`配置, 即在当前项目目录中的`.vscode`目录中通过
`json`文件进行配置, 如`settings.json`.

1, 语言

`C+S+P -> Configure Language`, 选择`en-US`

2, 主题

`C+S+P => Preferences: Color Theme`, 选择主题`Dracula`

3, 快捷键

`C+S+P -> Preferences: Keyboard Shortcuts`, 可修改快捷键

"selectNextSuggestion", `C-j`, 在补全建议中, 上下移动
"selectPrevSuggestion", `C-k`
"workbench.action.quickOpenNavigateNext", `C-j`, 在快速打开中, 上下移动
"workbench.action.quickOpenNavigatePrev", `C-k`
"command": "code-runner.run", `S-F10`, 运行代码
"command": "workbench.action.debug.stepOver", `F8`, 单步跳过
"command": "workbench.action.debug.stepInto", `F7`, 单步进入

`vim`:

* `C-F`, 向上滚动一屏, 可关
* `C-B`, 向下滚动一屏, 或在命令行(`VSCode`无)移动光标到行首, 可关
* `C-E`, 向上滚动一行, 或命令行行尾, 或插入光标下方字符, 可关
* `C-Y`, 向下滚动一行, 或插入光标上方字符, 可关
* `C-c`, 命令行取消命令, 可关
* `C-A`, 插入上次插入模式下输入的字符, 或在数字日期上进行增加
* `C-x`, 在数字日期上进行减

通过将`Ctrl-]`映射到块可视, 从而将`Ctrl-v`解放出来, 如此`Ctrl-c/v/x`都可以使用了.

* `C-D`, 向下滚动半屏, 或插入模式中减少缩进
* `C-T`, 标签跳转, 或插入模式下增加缩进
* `C-h`, 插入模式下, 左删一个字符
* `C-O/I`, 光标跳转到上/下一个位置
* `C-U`, 删除光标到行首的字符, (应该是Bug, 因为删除了整行字符)
* `C-w`, 左删一个word
* `C-o`, 在插入模式下执行一个普通模式的命令
