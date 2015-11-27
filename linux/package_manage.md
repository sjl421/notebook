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

## yum

### Config

配置文件:  `/etc/yum.repos.d/`

### Usage

基本命令:
* 安装软件包: `yum install [-y] pkgname`

## pacman