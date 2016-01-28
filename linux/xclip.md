# Xclip

一个剪贴板管理软件.

有两种形式的剪贴板,一种是高亮文本再通过鼠标中键粘贴,另一种是选择文本显式复制`C-c`和显式粘贴`C-v`.

Usage:
* `xclip file`, 将文件内容复制到剪贴板,中键粘贴
* `xclip -o [file]`, 输出第一种剪贴板内容
* `xclip -selection clipboard file`, `C-v`粘贴
* `xclip -selection clipboard -o [file]`, 输出第二种剪贴板内容
* `xclip -l n file`, 等待文件内容被粘贴`n`次才退出
