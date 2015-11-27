# Fix broken packages

* 若因为依赖问题安装失败：
  * `sudo apt-get -f install && sudo dpkg --configure -a`
* 若遇到'MergeList'和'Package:header' error：
  * `sudo rm -rf /var/lib/apt/lists/* && sudo apt-get update`
* 若遇到`Hash Sum mismatch` error:
  * `sudo rm -rf /var/lib/apt/lists/partial/*`
  * `sudo apt-get update`
* 若遇到不能获取apt缓存锁：
  * `sudo fuser -cuk /var/lib/dpkg/lock`;
  * `sudo rm -f /var/lib/dpkg/lock`;
  * `sudo fuser -cuk /var/cache/apt/archives/lock`;
  * `sudo rm -f /var/cache/apt/archives/lock`
* 安装openssh-server: 依赖openssh-client，系统安装有但提示"held broken packages", 简单粗暴有效的方法是把相关packages全删掉再安装。
  * `sudo dpkg -l | grep pkgname`
  * `sudo dpkg -P --force-all pkgname`, 强制删除相关packages, 注意不能用apt
  * `sudo apt-get -f install`, 修复依赖，自动安装packages
  * `sudo apt-get install what-you-want`

## ibus wubi 98

~~~ sh
# you need abc.txt and abc.svg.
$ ibus-table-createdb -s abc.txt
$ sudo cp abc.db /usr/share/ibus-table/tables/
$ sudo cp abc.svg /usr/share/ibus-table/icons/
~~~

## chrome

bookmarks messy code

~~~ sh
sudo apt-get install ttf-wqy-microhei ttf-wqy-zenhei xfonts-wqy
~~~

## file manager

文件管理器侧边栏错误:

* edit the `~/.config/user-dirs.dirs`
* 确保相关目录存在
* 注销重登陆

## monaco font

install: `curl -kL https://raw.github.com/cstrap/monaco-font/master/install-font-ubuntu.sh | bash`

## grub fixed

```sh
sudo grub-install /dev/sda
sudo update-grub2
# update-grub2是个脚本，内部调用下列命令
sudo grub-mkconfig -o /boot/grub/grub.cfg
```

## ibus

默认的ubuntu英文安装环境是不会安装中文输入法。

如果你不能调出ibus的中文输入法，安装如下：

* `sudo apt-get install ibus-pinyin`
* `sudo apt-get install ibus-sunpinyin`

可通过调用`ibus-setup`命令来输入法。