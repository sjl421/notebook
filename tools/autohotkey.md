# autohotkey

将你的命令写入脚本, 运行脚本, 再通过快捷键或文本来触发定义好的动作. 每次更改脚本都需要重新载入才能实现更新.

第一种形式, 以快捷键`hotkey`触发动作
```ahk
; comments
hotkey::
    action
return
```

这里的`return`表示此热键触发的动作到此结束, 以下为下一个热键或热串的内容.

如`^j`表示`ctrl+j`, `^!j`表示`ctrl+alt+j`.

第二种形式, 以文本`hotstring`触发动作
```ahk
::text::
    action
return
```

如`::ftw::Free the whales`定义一个文本缩写, 当键入`ftw`后任意一个非`word`的字符都会触发, 需要一个非`word`的结束符. 而`:*:ftw::Free the whales`则定义了一个立即触发文本. 所谓`word`符一般包括数字, 字母, 下划线.

第三种形式, 文本触发缩写
```ahk
::abbre::origin text
```

`AHK`有函数`Functions`, 命令`Commands`, 变量`Variables`. 不区分大小写.

快捷键: `#`表示`Win`, `!`表示`Alt`, `+`表示`Shift`, `^`表示`Ctrl`. 以上是用于组合键, 而若单独表示`ctrl`, 可直接写`ctrl`或`lctrl`或`rctrl`分别表示左右`ctrl`键. `shift`, `alt`键同理. `&`用于组合键, 如`Numpad0 & NumPad1`表示按下`0`键时再按下`1`键. 以上是用于组合

注: `Numpadx`表示小键盘上的数字键, 这里不区分大小写, `numpadx`也可以.

默认情况下, 定义的脚本是全局工作的, 不局限于特定窗口. 如定义的缩写在所有文本输入框, 包括所有文本编辑器, 浏览器地址栏, 百度搜索框都会触发缩写. 用以下的形式定义特定于窗口的热键或热串(允许我翻译成此, 分别表示快捷键和特定文本). 称为上下文敏感热键或热串.

```ahk
#IfWinActive Untiled - Notepad
#space::
    MsgBox You pressed Win+Spacebar in notepad.
Return
#IfWinActive
```

如果你的热键不能正常工作, 你可能需要替换`Untiled - Notepad`为`无标题 - 记事本`. 还记得上面提到过, 此处必须完全对应特定窗口的标题栏内容. 

这里的`#`是`Win`键, 即有windows徽标的那个键. 对话框文本里出现`+`被原样输出, 推测对于函数的参数文本, 默认为原字符. 而对于命令, 如`Send`, 默认为热键字符.则必须使用`{+}`表示原`+`字符, 而`+`字符表示`Shift`. 

除此外, `{}`还可表示特殊字符, 如`{enter}`表示回车, `{ctrl down}`表示按下`ctrl`, 对应的`{ctrl up}`表示放开`ctrl`. 于是发送复制命令`send, {ctrl down}c{ctrl up}`表示复制. 而`send, {ctrl down}v{ctrl up}`表示粘贴. 当然更简单的是`send, ^c/^v`.

还有此处必须使用`#IfWinActive`包围特定脚本. 特殊命令也大小写无关. 其实定位特定窗口, 除了标题栏文本还有多种形式, 如`HWND`窗口句柄, `group`组, `class`类等.

创建对象:
```ahk
MyObject := ["one", "two", "three", 17]  ; 数组或列表
banana := {"Shape": "Elongated", "Color": "Yellow"}  ;字典或哈希
MyObject := Array("one", "two", "three", 17)  ; 创建数组的函数
Banana := Object("Shape", "Elongated", "Color", "Yellow")
Banana.Consistency := "Mushy"  ;添加新元素或改变原键值
Banana["Pickled"] := True  ;两种方式, `.`和`[]`操作符
MyObject.NewKey := "Shiny"  ;给数组添加键值对
MyObject.InsertAt(Index, Value1, Value2, Value3, ...)  ;在指定索引处插入
MyObject.Push(Value1, Value2, Value3, ...)  ;追加元素
MyObject.NewKey := ""  ; 移除值
RemovedValue := MyObject.Delete(AnyKey)  ; 移除键
NumberOfRemovedKeys := MyObject.Delete(FirstKey, LastKey)  ; 移除一个范围的键
MyObject.Pop()
RemovedValue := MyObject.RemoveAt(Index)
NumberOfRemovedKeys := MyObject.RemoveAt(Index, Length)
```

这里需要说明的是, `Array`和`Object`是相同的对象, 数组默认的数字索引, 但也可以添加键值对. 则之前的值通过`1,2,3`索引, 加入的值通过键索引. 

对于数组, 第一个元素的索引为1.

另外, 在命令中, 默认的是文本, 似乎不能直接输出`MyObject.NewKey`, 无论是否以`%`包围. 变通的方法是赋值给变量, 再将变量以`%`输出.

## 语句

赋值语句: 
```ahk
var = text
var := expression  ; +=, -=, .=
```

文本和表达式的区别: 文本中, 字串不需要`"`, 变量需要`%`, 不进行数学运算或函数调用. 表达式相反. 相同的是, 多字串不需要任何连接操作符.

这种区别处处适用. 如`Commands`命令跟的是文本参数, 而`Functions`函数跟的是表达式参数.

条件语句:
```ahk
if (var = 5) {
    msgbox, var equals %var%!!
    exitapp
} else {

}
```

语法太冗余了, 当有`()`时, 适用表达式, 而没有`()`时, 适用文本. 但即使在文本中, 也只有在操作符如`=<>`右边才需要`%`包围变量.

输入语句:
```ahk
InputBox, OutputVar, Question 1, What is your first name?
if (OutputVar = "Bill")
    MsgBox, That's is an awesome name, %OutputBox%.
```

变量也不区分大小写.

## 特殊键special keys

* `{f1}-{f12}`
* `{enter/space/esc/tab}`
* `{bs/del/ins}`
* `{up/down/left/right}`
* `{home/end/pgup/pgdn}`
* `{capslock/scrolllock/numlock}`
* `{ctrl/lctrl/rctrl down/up}`, `alt`, `shift`, `win`同
* `{+!^#{}}`, 仅表示`+`, `!`, `^`, `#`, `{`, `}`
* `{numpad0-9}`, `{numpaddot/enter/mult/div/add/sub}`数字键盘的键, 按下`numlock`键时
* `{numpadup/down/left/right/home/end/pgup/pgdn/del/ins/clear}`数字键盘的控制键, `numlock`未按下时
* `{browser_back/forward/refresh/stop/search/favorites/home}`, 浏览器的按键
* `{volume_mute/down/up`音量键
* `{media_next/prev/stop/play_pause}`媒体控制键

## 预定义变量build-in variables

以`%`包围, 如`%A_WorkingDir%`.

脚本属性:
* `1, 2, 3`, 脚本的命令行参数, `0`表示参数数目
* `A_ScriptDir, A_ScriptName, A_ScriptFullPath, A_ScriptHwnd`运行脚本的目录,文件名,全路径,主窗口句柄.
* `A_LineFile, A_LineNumber, A_ThisFunc, A_ThisLabel`表示当前运行命令的文件,行号,函数,标号, 用于错误处理
* `A_AhkVersion, A_AhkPath`本程序的版本和路径
* `A_IsUnicode, A_IsCompiled, A_ExitReason`是否是unicode, 是否编译, 退出原因
* 

## 函数Functions

函数一般有这样的形式, `var:=func(arg1, arg2, ...)`.

变量直接用, 字串以`"`包围, 参数进行数字运算, 函数可嵌套, 有返回值.

`SubStr(string, num)`, 返回从num位置的字串, 1索引

`FileExist(path)`, 文件存在判断

## 命令Commands

命令一般有这样的形式, `Cmd, arg1, arg2, arg3`. 

参数使用变量用`%`包围, 文本和数字无需`"`包围, 参数不进行数字运算. 一行一条命令

`Send, text`: 发送键击, `,`后直到行末都是输入的键. 其有特殊字符, `! + ^ #`, 需要通过`{}`输入原字符.

`Run, program`: 运行程序, `AHK`会在`%path%`中查找. 如`Run, NotePad.exe`, 其中后缀可省略. 除了程序, 也可以直接打开网页`run, www.baidu.com`会调用默认浏览器打开百度首页. 也可以打开目录, 如`run, %A_MyDocuments`将打开`我的文档`. 此处的`%A_MyDocuments`是`AHK`预定义变量.

`WinActivate, 标题栏文本`: 激活指定标题栏的窗口, 注意操作系统环境不同, 标题栏文本的语言也不同.

`WinWaitActive, 标题栏文本`: 等待指定窗口的激活, 往往同上一起使用, 以确保接下来的命令运行在正确的窗口中.

`sleep, num`, 停止毫秒

`MsgBox, text`: 显示对话窗口, 跟文本参数, 只有`确定`按键.

`MsgBox [, options, title, text, timeout]`: 单独一个`msgbox`会提示`press ok to continue`, 选项显示不同的按钮, 如4`yes/no`或1`ok/cancel`,2`abort/retry/ignore`,3`yes/no/cancel`等.

`InputBox, output_var, title_text, question_text`, 以窗口形式获取用户输入

`IfMsgBox, Yes/No`: 如果对话框点击确定或否, 或者依据选项的不同输入, 来决定下一步





