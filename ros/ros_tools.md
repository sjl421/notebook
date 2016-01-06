# Tools

## rviz

一个3D可视化环境,可以结合传感器数据,机器人模型以及其它3D数据构成综合视图.

## rosbag rqt_bag

`rosbag`: 记录与回放消息到`bag`文件的命令行工具

`rqt_bag`: `bag`文件数据的可视化工具

## rqt_plot

实时将发布到ROS`topics`的标量数据可视化的工具.

## rqt_graph

生成运行在ROS和其`connections`的进程图的可视化工具

## 命令行工具

运行ROS系统
* `roslaunch`: 载入和配置多个程序
* `rosrun`: 运行单个程序
* `roscore`: 运行核心系统

交互和调试运行系统
* `rostopic`: 主题
* `rosservice`,`rossrv`: 服务
* `rosnode`: 节点
* `rosparam`: 参数
* `rosmsg`: 消息
* `roswtf`: 通用调试

安装,构建和文件系统工具
* `rosmake`: 构建
* `rosinstall`: 从源码安装
* `roslocate`: 包/栈搜索
* `rosdep`: 第三方库安装
* `rospack`,`roscd`: 包
* `rosstack`,`roscd`: 栈
