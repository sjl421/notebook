# ubuntu

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

