# catkin

创建工作空间
```sh
$ mkdir -p ~/catkin_ws/src
$ cd ~/catkin_ws/src
$ catkin_init_workspace
```

在包目录下构建
```sh
$ catkin_make [install]
```

在包目录下将当前目录加入默认包路径`ROS_PACKAGE_PATH`
```sh
$ source devel/setup.bash
```

在`src`目录下创建包,并指定依赖
```sh
catkin_create_pkg <package_name> [depend1] [depend2] [depend3]
```

列出包的第一级依赖: `$ rospack depends1 beginner_tutorials`,包必须在默认路径中

A包依赖B包,B包依赖C包,则B包是A包的第1级依赖.`rospack depends pkg`会列出包的所有依赖

包的构建

`catkin_make`结合了对`cmake`和`make`的调用

Usage:
```sh
# In a catkin workspace
$ catkin_make [make_targets] [-DCMAKE_VARIABLES=...]
```

构建所有`src`目录下的项目.若源码不在标准的`src`目录中,则指定参数`--source my_src`
