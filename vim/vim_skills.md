# vim's skills

## 缓冲区切换

通常情况下, 无论是通过`nerdtree`或`ctrlp`或`unite`在当前窗口打开了新的文件, 
如果想切换到上一个文件使用`:bp`, 编辑下一个文件使用`:bn`.

## 调用函数

查看字符的`ascii`码: `:echo char2nr("a")`

如果字符出现在窗口上, `ga`可显示任意字符的编码.

查看数字的`ascii`字符: `:echo nr2char(97)`

进行数学计算: `echo func(args)`

## 编程接口

执行`py`语句: `:[range]py {stmt}`, 如果要得到结果, 需要显示输出.
