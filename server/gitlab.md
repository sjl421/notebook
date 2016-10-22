# gitlab

安装:
```shell
sudo apt-get install curl openssh-server ca-certificates postfix # postfix选择Internet Site
curl -sS https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.deb.sh | sudo bash
sudo apt-get install gitlab-ce
sudo gitlab-ctl reconfigure
```

安装好后, 直接打开`ip`或主机地址即可.

卸载:
```shell
sudo gitlab-ctl uninstall
sudo apt purge gitlab-ce
```
