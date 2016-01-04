# ros

## install for ubuntu 14.04

添加ROS软件源和验证密钥
```sh
$ sudo sh -c 'echo "deb http://packages.ros.org/ros/ubuntu $(lsb_release -sc) main" > /etc/apt/sources.list.d/ros-latest.list'
$ sudo apt-key adv --keyserver hkp://pool.sks-keyservers.net --recv-key 0xB01FA116
```

更新软件源并安装
```sh
$ sudo apt-get update
$ sudo apt-get install ros-indigo-desktop-full
```

初始化`rosdep`并设置环境变量,`rosdep`安装代码依赖，被核心组件依赖
```sh
$ sudo rosdep init
$ rosdep update
$ echo "source /opt/ros/indigo/setup.bash" >> ~/.bashrc
```

安装`rosinstall`,命令行工具，下载ROS包的源码树
```sh
$ sudo apt-get install python-rosinstall
```
