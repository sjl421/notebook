# cygwin

选择源： `http://mirrors.163.com`

常用包： `git`,`curl`,`wget`,`ctags`,`cscope`,`vim`,`openssh`,`ruby`,`python`

## apt-cyg

一个`Cygwin`的包管理工具，包括一个命令行安装器，使用安装`Cygwin`时相同的源仓库。主页：`https://github.com/transcode-open/apt-cyg`.但从各个方面不要期望它和`apt-get`同等级好用。比如，安装包时自动安装依赖包，但删除包时则没有处理无依赖的包。

安装： 
* `lynx -source rawgit.com/transcode-open/apt-cyg/master/apt-cyg > apt-cyg`
* `install apt-cyg /bin`

Usage:
* 安装包：`apt-cyg install pkgname`
* 移除包：`apt-cyg remove pkgname`
* 更新包缓存：`apt-cyg update`
* 下载包：`apt-cyg download pkgname`
* 显示包信息：`apt-cyg show pkgname`
* 生产包依赖的树：`apt-cyg depends pkgname`
* 生成依赖包的树：`apt-cyg rdepends pkgname`
* 列出本地包：`apt-cyg list [regexp]`
* 列出所有包：`apt-cyg listall [regexp]`
* 显示指定类的所有包：`apt-cyg category [Base|Devel|Ruby...]`
* 搜索包含指定文件的本地包：`apt-cyg search filepath`
* 搜索包含指定文件的所有包：`apt-cyg searchall filepath`
* 指定源镜像：`apt-cyg mirror [url | http://mirrors.163.com/cygwin/]`
* 指定包下载路径：`apt-cyg cache [dirpath]`

## 制作便携包

本地机器：
* 压缩打包安装目录
* 导出注册表：`reg export HKLM\SOFTWARE\Cygwin abc.reg`

另一台机器：
* 解压缩安装目录
* 修改注册表相关根目录
* 导入注册表：`reg import abc.reg`

## 添加右键菜单

直接修改注册表：`reg add HKCR\Directory\Background\shell\OpenCygwin\command /ve /t REG_SZ /d "D:\Cygwin64\bin\mintty.exe -i /Cygwin-Terminal.ico [-]`

其中`OpenCygwin`名字任意，有`[-]`时，会在主目录打开，否则在当前目录打开。

## 卸载

* 删除下载包目录
* 停止服务: `cygrunsrv -L`, `cygrunsrv -S`, `cygrunsrv -R`
* 删除安装目录
* 删除注册表： `reg delete HKLM\SOFTWARE\Cygwin /f`,`reg delete HKCU\SOFTWARE\Cygwin /f`
* 删除环境变量: `PATH`, `CYGWIN`

## 特权

cygwin没有`root`用户也没有`sudo`这样的提权工具,但可以近似实现一个.

新建脚本`sudo`,并添加到`PATH`路径中:

```sh
#!/usr/bin/bash
cygstart --action=runas "$@"
```

如果需要运行特权命令,会弹出一个新的特权`cygwin`窗口,运行命令后退出.