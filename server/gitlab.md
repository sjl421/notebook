# gitlab

`gitlab`是基于`Ruby on Rails`开发的,也就是说,它就是一个`Rails`应用.

安装:
```shell
sudo apt-get install curl openssh-server ca-certificates postfix # postfix选择Internet Site
curl -sS https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.deb.sh | sudo bash
sudo apt-get install gitlab-ce
sudo gitlab-ctl reconfigure
```

在最后一步,可能一直卡在`ruby_block[supervise_redis_sleep] action run`这里,特别是
之前安装过`gitlab`的主机,解决方法是`sudo systemctl restart gitlab-runsvdir.service`.
或先前版本`sudo initctl restart gitlab-runsvdir`.

安装好后, 直接打开`ip`或主机地址即可.一般在`80`或`8080`端口(即`nginx`默认绑定端口)

通常即使安装成功,也请运行`sudo gitlab-rake gitlab:check`命令检查是否有未处理的安装
问题.通常是一些目录的权限问题,特别是`/var/opt/gitlab/`目录下,`git-data/repositories`
目录权限和`gitlab-rails/uploads`.

卸载:
```shell
sudo gitlab-ctl uninstall
sudo apt purge gitlab-ce
sudo rm -rf /opt/gitlab /var/opt/gitlab
```

通过`gitlab-ctl`来控制`gitlab`:
* `help`查看帮助
* `status`查看服务状态
* `start/stop/restart`启动/停止/失望服务

`gitlab`依赖了多个服务进程,包括:`nginx`代理请求到`unicorn`,`postgresql`数据库存储
数据,`redis`在内存中存储用户会话和后台任务队列,`sidekiq`以多线程处理后台作业,
`unicorn`服务器处理`web`请求.

可以控制单个组件:`sudo gitlab-ctl start/stop/restart sidekiq`

调用`gitlab`的`Rake`任务: `sudo gitlab-rake --tasks`查看支持多少任务.因为`gitlab`
就是一个`rails`应用,因此支持很多`rake`任务进行比较底层的控制.

`sudo gitlab-rails console`启动一个`rails`终端,慎用.

`sudo gitlab-psql -d gitlabhq_production`以超级用户权限连接到数据库.

配置文件:`/etc/gitlab/gitlab.rb`,是一个可执行的`rb`脚本,所以在改动后必须执行`sudo
 gitlab-ctl reconfigure`.

`sudo gitlab-ctl reconfigure`在首次运行时,会为`GitLab`,`PostgreSQL`,`Redis`,`Nginx`
创建用户.`git`,`gitlab-www`,`gitlab-redis`,`gitlab-psql`,`mattermost`5个用户及相应
的组.

## Runner

`Runner`主要是配合`CI`(即持续集成),其负责实际的代码运行.

安装:
```sh
curl https://packages.gitlab.com/gpg.key 2> /dev/null | sudo apt-key add - &>/dev/null
deb https://mirrors.tuna.tsinghua.edu.cn/gitlab-ci-multi-runner/ubuntu xenial main
sudo apt update && sudo apt install gitlab-ci-multi-runner
```

注册`Runner`: `sudo gitlab-ci-multi-runner register`
