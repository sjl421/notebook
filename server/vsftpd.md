# ubuntu安装ftp

`sudo apt-get install vsftpd`

安装完会新建`ftp`用户, 默认目录`/srv/ftp`. 此即匿名用户登录时看到的目录. 可通过`sudo usermod -d /srv/new/ftp`命令指定新的目录.

更改配置文件, 通过`sudo restart vsftpd`命令重启服务.

配置(`/etc/vsftpd.conf`):
* 允许匿名登录: `anonymous_enable=YES`
* 允许用户登录可上传: `write_enable=YES`
* 允许匿名登录可上传: `anon_upload_enable=YES`
* 限制用户只能访问自己目录: `chroot_local_user=YES`
* 限制指定用户只能访问自己目录: `chroot_list_enable=YES; chroot_list_file=/etc/vsftpd.chroot_list;`
* 禁止用户访问ftp: 添加用户到`/etc/ftpusers`文件.