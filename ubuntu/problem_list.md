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

## fcitx

如果使用拼音输入法的话, 最好在语言支持里, 选择`fcitx`输入框架, 因为它支持搜狗输入法.

通常情况, `fcitx`的相关文件在`/var/share/fcitx/*`目录下.

## 显示器的分辨率问题

1, `Could not apply the stored configuration for the monitor`

直接删除`~/.config/monitors.xml`即可.

## Gtk-Message: Failed to load module "canberra-gtk-module"

`sudo apt install libcanberra-gtk3-module`

## wireshark

Q: lua error during loading
A: `sudo setcap 'CAP_NET_RAW+eip CAP_NET_ADMIN+eip' /usr/bin/dumpcap`

`wireshark`通常不希望以特权用户运行,但非特权用户运行往往看不到网络接口.所以可以为
程序设置适当的权限.

## apt pubkey

经常有在`sources.list.d`目录中添加了`.list`文件后, `sudo apt update`会提示找不到`NO_PUBKEY`的错误.

通常会在`repo`的目录中找到`.key`文件, 可以手动下载后, 执行`sudo apt-key add key_file`即可.

