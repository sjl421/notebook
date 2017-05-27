# Sublime3

一个拥有现代界面的速度非常快的编辑器.

虽然收费, 但免费情况下只会提示购买, 并不会影响使用.

下载: `https://www.sublimetext.com/3`

## Package Control

`Sublime`的在线包管理软件. `https://packagecontrol.io/`

在线安装: 通过`Ctrl + ~`或`View->Console`调出终端, 并输入下面代码回车

```py
import urllib.request,os,hashlib; h = 'df21e130d211cfc94d9b0905775a7c0f' + '1e3d39e33b79698005270310898eea76'; pf = 'Package Control.sublime-package'; ipp = sublime.installed_packages_path(); urllib.request.install_opener( urllib.request.build_opener( urllib.request.ProxyHandler()) ); by = urllib.request.urlopen( 'http://packagecontrol.io/' + pf.replace(' ', '%20')).read(); dh = hashlib.sha256(by).hexdigest(); print('Error validating download (got %s instead of %s), please try manual install' % (dh, h)) if dh != h else open(os.path.join( ipp, pf), 'wb' ).write(by)
```

手动安装: 下载`Package Control.sublime-package`包, 复制到`Installed Packages`目录中(通过`Preferences->Browse Packages`找到).

使用: 通过`Ctrl + Shift + P`来调出命令面板, `Package Control`的所有命令都以`Package Control`开头. 

* `Add/Remove Channel/Repository`: 添加/移除通道和仓库
* `Install/Remove/Upgrade Package`: 安装/移除/升级包
* `Disable/Enable Package`: 禁止/允许包

## 推荐的包

### Vintageous

模拟`vi/vim`的包.

### Emmet

快速输入`html`代码的包.

### SublimeREPL

提供常用语言的`REPL`界面, 但其实并不好用, 但很有趣.
