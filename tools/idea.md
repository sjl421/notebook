# IDEA

写些自己常用的`IDEA`快捷键.

首先,装上`IdeaVim`插件. 将快捷键认为`Default`.

需要注意的是, 通常按键会有两种处理方式, 一种是被`IDE`处理, 一种是被`Vim`处理, 换
句话说, 一种是使用`IDE`的对应快捷键, 一种是使用`Vim`对应的快捷键.

对于`Vim`, 最核心的键是`Ctrl + [`, 用来替代`Esc`. `Ctrl + w`次之, 用来切换多面板.

`Ctrl + N`: 定位到类名所在的位置,需要在`IdeaVim`中修改
`Ctrl + Shift + N`: 定位到指定名称的文件
`Ctrl + /`: 注释当前行
`Ctrl + j/k`: 用于在列表中上下选择,相当于`Down/Up`
`Ctrl + E`: 选择最近打开文件
`Ctrl + I`: 插入模板
`Ctrl + Q`: 显示光标处类的快速文档
> `Ctrl + [/]`: 跳转到代码块的开始和结尾
`Ctrl + F`: 跳转到指定符号, 全局
`Ctrl + S`: 打开文件结构
`Alt + 1`: 定位到`Run`底窗口
`Alt + 2`: 定位到`TODO`底窗口
`Alt + 3`: 定位到`Terminal`底窗口
`Alt + 7`: 定位到`Structure`,即类图
`Alt + 8`: 定位到`Project`, 即项目文件图
`Alt + R`: 运行
`Alt + S`: 停止
`Alt + B`: 调试
`Alt + J`: 选择当前光标所在标识符,可继续选择,同时替换名称
`Alt + [/]`: 光标跳转到上/下方法定义外
`Alt + E`: 光标移动行行尾
`Ctrl + Alt + U`: 显示类的继承图示
`Ctrl + Alt + 上下`: 前一个方法/后一个方法
`Ctrl + Alt + 左右`: 向前向后

## 修改

修改默认的`Terminal`: `File -> Settings -> Tools -> Terminal`, 选择`Shell path`
为`Cygwin`安装目录下`bin/bash.exe`即可. 至少比`cmd.exe`好用. 字体在`Editor -> 
Colors & Fonts -> Console Font`中修改, 可选择`Consolas 14`.
