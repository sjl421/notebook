# brew

MAC OSX 下的包管理工具, [官网 brew](www.brew.sh).

## 安装

Install: `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

如上, 请确保系统已经安装有 `ruby`, `curl` 和 `git` 两个工具.

brew默认安装软件到`/usr/local/bin`目录下, 但默认情况下这个目录在PATH下并不是最前的, 导致brew安装的软件并不会被优先使用. 可`export PATH=/usr/local/bin:PATH`来完成.

## 用法

Usage:
* 安装包: `brew install pkg`
* 添加链接: `brew linkapps pkg`
* 卸载包: `brew uninstall pkg`
* 搜索包: `brew search pkg`
* 查看包信息: `brew info pkg`
* 更新 `brew` 和包列表缓存: `brew update`
* 更新已安装包: `brew upgrade [pkg]`
* 列出已安装包: `brew list [pkg]`
* 列出已添加的库: `brew tap`
* 添加新的库: `brew tap user/repo`

通过 `brew -h` 获取帮助信息, 但其对包的称呼是 `formula`.

你可以添加库再安装包, 也可以直接 `brew install user/repo/pkg` 安装包.

## 详细

 通过 `brew subcmd -h` 获取子命令的帮助信息

`search`命令:
* `pkg`, 搜索包含 `pkg` 子串的包
* `/pkg/`, 搜索匹配 `pkg` 模式的包

`list`命令:
* `--versions`, 会列出包的版本信息
* `--versions --multiple`, 会列出相同包的不同版本信息
* `--full-name`, 列出包的全名
* `--unbrewed`, 列出 `brew` 执行目录下的非 `brew` 安装的程序

`info`命令:
* `--github`, 打开浏览器到包的 `brew` 页面

`home`命令:
* 打开浏览器到 `brew` 的官网 `www.brew.sh`
* `home pkg`, 打开到包的官网

`options`命令:
* `pkg`, 列出包的编译选项
* `--compat`, 以紧凑的格式显示为一行
* `--all`, 列出所有包的编译选项
* `--installed`, 列出已安装包的编译选项

`upgrade`命令:
* `--cleanup`, 清除之前安装的版本

`uninstall`命令:
* `--force`, 删除安装的所有版本的包

`tap`命令:
* `--list-official`, 列出所有官方库
* `user/repo url`, 添加指定链接的库而非默认的 git

## 换源

`brew` 使用两个源, `bottles`源存放编译好的二进制程序, 可快速安装. `git` 源存放包的索引信息, 包含 `brew` 本身的源, 核心库的源, 科学库的源和 `python` 库的源, `emacs`插件库的源.

设置 `HOMEBREW_BOTTLE_DOMAIN` 环境变量:`echo 'export HOMEBREW_BOTTLE_DOMAIN=https://mirrors.ustc.edu.cn/homebrew-bottles' >> ~/.bashrc`

中科大更新软件本身的源: `cd /usr/local && git remote set-url origin git://mirrors.ustc.edu.cn/brew.git`

清华源:
```sh
cd /usr/local  #  用于更新 brew 本身
git remote set-url origin https://mirrors.tuna.tsinghua.edu.cn/git/brew.git
cd /usr/local/Library/Taps/user/user-repo  #  用于更新软件库源
git remote set-url origin https://mirrors.tuna.tsinghua.edu.cn/git/user-repo.git
```

注: `user` 可为 `homebrew`, `repo` 可为 `core`, `science`, `python`.
