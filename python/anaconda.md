# anaconda

下载链接: `https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh`

`anaconda`版本包含`python`和很多常用多,体积比较大;`Miniconda`只包含`conda`管理程序和`python`.且都有多平台和`2|3`版本的区别.

## conda

一个python的包管理器.同时维护大量用于科学计算等的`python`包.

## Usage

* 帮助: `conda subcmd --help`
* 搜索包: `conda search pkgname`
* 安装包: `conda install pkgname[=x.y.z] [-n myenv]`
* 若`anaconda`不维护的包: `pip install pkgname`
* 列出包: `conda list [-n myenv]`
* 更新包: `conda update [conda]`
* 移除包: `conda remove pkgname [-n myenv]`
* 创建环境: `conda create -n myenv [anaconda pkgname] [python=2|3] [--clone env]`
* 列出所有环境: `conda info -e` 或 `conda env list`
* 移除环境: `conda remove -n myenv --all`并移除所有的包
* 清除不使用的包: `conda clean --all`
* 激活环境: `source activate myenv`
* 反激活环境: `source deactivate`
* 导入环境: `conda env create -f environment.yml`
* 导出环境: `conda env export > environment.yml`

不同版本的`conda`有默认的`python`版本,但都可以用来创建`2|3`的环境.

`anaconda`参数表示同时安装大量`anaconda`维护的用于科学计算的常量包.或者可以单独列出想安装的包.`--clone env`表示可以克隆某个环境.

`conda update python`将升级`python`到小版本的最新版本, 如`3.5`到`3.5.x`的最新.

`conda install python=3.6`则将安装`3.6`的大版本.

## 换源

更换到清华的源:

```sh
conda config --add channels 'https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/'
conda config --set show_channel_urls no
```
