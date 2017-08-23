# docker

## install

* 添加gpg键: `sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D`
* 添加源: `deb https://mirrors.tuna.tsinghua.edu.cn/docker/apt/repo ubuntu-xenial main`
* 安装`docker-engine`,开启守护进程`systemctl start docker`,验证安装`docker run hello-world`

官方的`hub`在国内访问速度很慢, 推荐使用阿里云的`docker hub`镜像加速服务, 是免费的.

原理:

* `client`联系`daemon`
* `daemon`从`hub`拉`hello-world`的镜像
* `daemon`为镜像创建容器,镜像运行程序,程序产生输出
* `daemon`将输出重定向到`client`

## 命令

* `docker run`, 运行`image`
  * `-d`, 以后台运行容器
  * `-p p1:p2`, 将主机端口`p1`映射到容器端口`p2`
* `docker ps`, 查看运行的`container`
  * `-a`, 运行过的所有容器
* `docker stop container_id`, 停止后台运行的容器
* `docker images`, 查看本地的`image`
* `docker rmi reponame:tagname`, 移除镜像
* `docker rm container_id`, 移除容器, 不能移除正在运行的容器
* `docker build -t tag_name .`, 构建镜像, 要求当前目录包含`Dockerfile`文件
* `docker tag local-image:tagname reponame:tagname`, 为镜像打标签
* `docker push reponame:tagname`, 推送镜像到`registry`.
* `docker login`, 登录`dockerhub`

* `docker stack ls`, 列出正在运行的应用
* `docker stack services appname`, 列出指定应用开启的服务
* `docker stack deploy -c file.yml appname`, 部署应用, 配置文件为`file.yml`
* `docker stack ps appname`, 列出指定应用启动的容器
* `docker stack rm appname`, 移除指定应用
* `docker node ls`, 列出当前`swarm`集群的节点
* `docker swarm init`, 启用`swarm`并将此节点作为`manager`
* `docker swarm join --token xxx ip:port`, 将当前节点加入`swarm`集群
* `docker swarm leave [--force]`, 将此节点从`swarm`集群移除, `--force`是移除`manager`

`registry`是`repository`的集合, `repository`是镜像的集合. 通常`reponame`是`username/repo`的形式, 而镜像其实是`tagname`的形式.

`container`, `service`, `stack`是由底到高的三个层次. `container`指运行的`image`, `service`定义`container`的行为, `stack`则定义`service`间的交互.

`docker-machine`

* `docker-machine create --driver virtualbox myvm1`, 创建虚拟机`myvm1`
* `docker-machine ssh myvm1 [shell command]`, 登录虚拟机, 或只执行命令
* `docker-machine scp xx myvm1:path`, 拷贝文件到虚拟机
* `docker-machine start/stop/rm myvm1`, 启动/停止/移除虚拟机

## 概念

`image`包含执行程序, 运行时, 依赖库, `container`是`image`的运行实例, 在宿主机上以单独的进程运行, 不依赖宿主机的环境, 除了按配置访问主机文件和端口.

`docker`绑定在`unix socket`,其默认被`root`用户拥有,所以以特权运行.

创建docker用户组并添加用户: `sudo usermod -aG docker user`,之后就可以非特权运行docker程序.

如果遇到错误`Cannot connect to the Docker daemon. Is 'docker daemon' running on this host?`,可`unset $DOCKER_HOST`

升级: `apt-get upgrade docker-engine`
卸载: `apt-get purge docker-engine`, `apt-get autoremove`, `rm -rf /var/lib/docker`
