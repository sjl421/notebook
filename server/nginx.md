# nginx

`nginx`有一个主进程和多个工作进程,主进程负责配置的读取和执行,工作进程负责处理请求.

`nginx`使用基于事件的模型和`OS`独立的机制在多个工作进程间有效地分发请求.

工作进程数目可设定为固定数,也可认定为`auto`,通常调节为可用的`CPU`数.

默认情况下,配置文件为`/etc/nginx/nginx.conf`.

## 安装

`sudo apt install nginx`, 可禁止开机启动: `sudo systemctl disable nginx`

## 控制

`sudo nginx`启动进程

`sudo nginx -s signal`启动进程后通过`-s`发送信号来控制进程.有`stop/quit/reload/
reopen`4个信号.

`quit`信号会在工作进程处理完当前请求后自动退出.

`reload`用于重新载入配置.当配置更新后,`nginx`先检查配置的正确性,若正确才向旧工作
进程发送退出信号,并启用新的工作进程.

## 配置文件

`nginx`包含多个模块,由配置文件中的命令控制.命令分为简单命令和块命令,简单命令就是
名称+空格分开的多个参数,以`;`结尾,块命令则在以`{...}`替换`;`,并在`{}`中继续写入
多个命令.

`events`和`http`在`main`的上下文中,`server`在`http`中,`location`在`server`中.
