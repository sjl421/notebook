# chocolatey

Windows平台的包管理软件.

安装: 
* 管理员权限的`cmd.exe`输入`@powershell -NoProfile -ExecutionPolicy Bypass -Command "iex ((new-object net.webclient).DownloadString('https://chocolatey.org/install.ps1'))" && SET PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin`回车即可
* 管理员权限的`powershell`输入`iex ((new-object net.webclient).DownloadString('https://chocolatey.org/install.ps1'))`回车即可

## Usage

基本命令:
* 帮助: `choco [subcmd] -h`
* 列出包: `choco list [-l] [pkgname] [-s url [-u user] [-p pass]]`(只列本地包),(搜索匹配包),(使用指定源,可能需要用户名和密码)
* 安装包: `choco install pkgname [-y]`(接收许可协议)
* 卸载包: `choco uninstall pkgname`
* 更新包: `choco upgrade pkgname [-s url [-u user] [-p pass]]`
* 查看和配置源: `choco source add|remove|disable|enable [-n name] [-s url]`(列出|添加|移除|禁止|使用源,可给源一个名称)
* 查看和配置特性: `choco feature disable|enable -n name`

## packages

`toracopy`: `choco install teracopy`,一个用于快速复制的软件

`7zip`: `choco install 7zip.install`,一个压缩和解压缩的软件

`gow`: `choco install gow`,一个`GNU`工具的`windows`版本