# vim surround

[vim-surround](https://github.com/tpope/vim-surround), 处理包围字符.

如删除`"hello, world"`两边的双引号, 使用`ds"`.

如改变`[123+456]/2`中的`[]`到`()`, 使用`cs])`.

如改变`"Look ma, I'm HTML!"`的双引号为`HTML`标签`<q></q>`, 使用`cs"<q>`

如为`if x>3 {`中的条件加`()`, 使用`ysW(`.

为`my $str = whee!;`的`whee`加单引号, 在可视模式下选择`whee`, 执行`S'`.

## Usage

删除包围符: `ds`接包围符的最后字符, 如删除双引号`ds"`, 删除括号`ds)`, 删除`HTML`
标签`dst`.

改变包围符: `cs`接原包围符和新包围符, 如改双引号为单引号`cs"'`, 改括号为中括号`cs)]`, 改`HTML`标签为另一个标签`cst<q>`.

添加包围符: `ys`接`vim`动作或文本对象和包围符, 为动作覆盖的文本或文本对象添加包围
符. 如为单词加括号`ysiw)`. 

为一行文本添加包围符, 忽略前导空白: `yss`接包围符, 如为一行文本加大括号`yss}`

字符`w`, `W`, `s`, `p`分别表示文本对象`word`, `WORD`, `sentence`, `paragraph`. 所以为一行文本加包围符`yss`, 为单词加包围符`ysw`.

在可视模式选中字符, 使用`S`接包围符来添加. 在行可视, 将所有行包围, 在块可视, 包围
每一行.

## 自定义包围符

可以自定义包围符, 如对`php`脚本, 以`<?php \r ?>`来包围: 
```vim
autocmd FileType php let b:surround_45 = "<?php \r ?>"
```

* 这里为`php`脚本定义符号`-`为`<?php ?>`包围符.  
* 45是`-`的`ascii`码, 通过`:echo nr2char(45)`查看. 
* `b:surround_45`表示此定义为局部于缓冲区的.

全局自定义, 如用于`erb`脚本的`<% \r %>`和`<%= \r %>`, 使用:
```vim
let g:surround_45 = "<% \r %>"  " -
let g:surround_61 = "<%= \r %>"  " =
```
