# autojump

基于统计的一键目录跳转.默认情况下,自动统计记录访问文件夹的情况,并自动计算权重.

## install

* `git clone git://github.com/joelthelion/autojump.git`
* `cd autojump`
* `sudo ./install.sh`

将`[[ -s /home/vagrant/.autojump/etc/profile.d/autojump.sh ]] && source /home/vagrant/.autojump/etc/profile.d/autojump.sh`添加到`~/.bashrc`文件中,重新载入

## Usage

* `j foo`, 跳转到目录,部分关键词
* `jc bar`, 跳转到子目录包含bar的文件夹
* `jo foo`, 打开文件管理器并切换到foo文件夹
* `jco bar`, 打开文件管理器并切换到子目录包含bar的文件夹

* `autojump -a dir`, 手动添加目录
* `autojump -i weight`, 增加权重
* `autojump -d weight`, 减少权重
* `autojump -s`, 查看当前索引数据库的状态