# ubuntu

`cat /etc/issue`可以查看当前系统的发行版本信息.

## 精简

```sh
sudo apt purge libreoffice* thunderbird* rhythmbox*
sudo apt autoremove
sudo apt autoclean
```

## 软件设置

1, ibus wubi 98

~~~ sh
# you need abc.txt and abc.svg.
$ ibus-table-createdb -s abc.txt
$ sudo cp abc.db /usr/share/ibus-table/tables/
$ sudo cp abc.svg /usr/share/ibus-table/icons/
~~~

2, guake

有关一些快捷键的设置, 可以效仿`Mac`上的`iTerm`:
* 新建/删除标签: `alt T/W`
* 选择标签: `alt 1/2/3/4`
* 复制粘贴: `alt c/v`
* 左右标签: `alt [/]`
* 触发: `alt space`

因为大量使用了`alt`键, 需要先禁用系统的`HUD`, 在右上角的设置, 选择`System Settings`,
`Keyboard`->`Shortcuts`->`Launchers`->`Key to show the HUD`, 选择`Backspace`.

3, monaco font

install: `curl -kL https://raw.github.com/cstrap/monaco-font/master/install-font-ubuntu.sh | bash`

## 系统快捷键

可设定新的快捷键:
* 退出窗口: `Windows -> Close window`: `alt q`
* 文件管理器: `Launchers -> Home folder`: `alt e`

## 安装中文字体

```sh
sudo apt install language-pack-zh-hans
```

如果通过`cygwin`终端访问服务器版本,如果出现乱码,可以设置`cygwin`的选项:`options->text`,
选择`utf-8`.

需要复制字体文件到`/usr/share/fonts`目录, 再执行`fc-cache`命令.

通过`fc-list :lang=zh`可列出中文字体文件.

## 更改locale

`locale`列出当前系统的语言环境

`localectl list-locales`列出当前系统中所有可用的`locale`

`localectl set-locales "LANG=zh_CN-utf8"`设置语言环境变量 

## 管理开机自启动服务

`sudo systemctl enable/disable service_name`

或`sudo update-rc.d -f service_file remove`