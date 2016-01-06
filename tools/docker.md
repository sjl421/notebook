# docker

## install

* 添加gpg键: `sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D`
* 新建`/etc/apt/sources.list.d/docker.list`,添加`deb https://apt.dockerproject.org/repo ubuntu-trusty main`源
* 更新,清除旧的仓库`apt-get purge lxc-docker`,验证apt从正确的仓库拉包`apt-cache policy docker-engine`
* 推荐安装`linux-image-extra-$(uname -r)`包
* 安装`docker-engine`,开启守护进程`service docker start`,验证安装`docker run hello-world`

原理: 
* `client`联系`daemon`
* `daemon`从`hub`拉`hello-world`的镜像
* `daemon`为镜像创建容器,镜像运行程序,程序产生输出
* `daemon`将输出重定向到`client`

`docker`绑定在`unix socket`,其默认被`root`用户拥有,所以以特权运行.

创建docker用户组并添加用户: `sudo usermod -aG docker user`,之后就可以非特权运行docker程序.

如果遇到错误`Cannot connect to the Docker daemon. Is 'docker daemon' running on this host?`,可`unset $DOCKER_HOST`

升级: `apt-get upgrade docker-engine`
卸载: `apt-get purge docker-engine`, `apt-get autoremove`, `rm -rf /var/lib/docker`


