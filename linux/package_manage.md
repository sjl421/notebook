# package manage tools

## apt-get

### Config

配置文件: `/etc/apt/sources.list`

### Usage

基本命令:
* 安装软件包: `apt-get install pkgname`
* 卸载软件包: `apt-get purge pkgname`
* 搜索软件包: `apt-cache search pkgname`
* 更新源缓存: `apt-get update`
* 更新软件包: `apt-get [-y] upgrade`
* 清理软件卸载遗留: `apt-get autoclean && apt-get autoremove`
* 系统版本升级: `apt-get dist-upgrade`
* 依赖关系修复: `apt-get -f install`

本地包安装: `dpkg -i abc.deb`
## yum

### Config

配置文件:  `/etc/yum.repos.d/`

### Usage

基本命令:
* 帮助: `yum help [subcmd]`
* 搜索包: `yum search pkgname`
* 显示包(组)信息: `yum info pkgname|groupname`
* 安装软件包: `yum install [-y] pkgname(s)`
* 重新安装包: `yum reinstall [-y] pkgname`
* 安装软件组包： `yum groupinstall groupname`
* 检查可更新的包： `yum check-update`
* 更新软件包：`yum update [pkgname]`
* 更新软件包组: `yum groupupdate groupname`
* 列出包: `yum list [pkgname]`
* 列出已安装包: `yum list installed`
* 列出包组: `yum grouplist`
* 列出包依赖: `yum deplist pkgname`
* 移除缓存的数据: `yum clean all|packages|headers|metadata|dbcache|plugins|expire-cache`清除缓存包,缓存包头,缓存元数据,缓存数据库,插件,过期缓存
* 移除包: `yum remove pkgname(s)`
* 移除遗留包: `yum autoremove`
* 生成元数据缓存: `yum makecache`
* 显示包仓库: `yum repolist [all | enabled | disabled]`
* 交互式: `yum shell`

为防止别的仓库在更新软件时替换`CentOS`的核心部分,因此通常情况下,别我仓库应处理禁止状态(通过查看相应仓库的`enabled=0|1`).然后在需要时通过`--enablerepo=reponame`和`--disablerepo=reponame`这两个选项进行相应的`yum`操作.

本地包安装: `rpm -ivh abc.rpm`

## pacman