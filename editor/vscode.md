# VSCODE

## 快捷键

* `C+A+s`, 打开终端, `windows`上默认为`cmd.exe`
* `C+P`, 快速打开, 可模糊搜索文件名, 输入`>`可转为命令面板
* `C+A+P`, 打开命令面板, 命令以组划分, 有相同前缀
  * `git: xxx`, 输入`git`相关的命令
* `C+,`, 打开配置文件
* `C+\`, 在右侧打开垂直窗口

* `A+1-9`, 切换标签

* `C+n v`, 打开`markdown`的预览

## 命令

`C+P`进入快速打开.

* `ext`, 关于插件
* `edt`, 关于编辑窗口
* `task tkname`, 执行任务, `A-T`
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

## 配置

除了全局的`User`配置外, 还有`Workspace`配置, 即在当前项目目录中的`.vscode`目录中通过
`json`文件进行配置, 如`settings.json`.

1, 语言

`C+S+P -> Configure Language`, 选择`en-US`

2, 主题

`C+S+P => Preferences: Color Theme`, 选择主题`Dracula`

3, 快捷键

`C+S+P -> Preferences: Keyboard Shortcuts`, 可修改快捷键
