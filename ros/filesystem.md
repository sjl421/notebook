# FileSystem 

## 概念

* `packages`: ROS代码的软件组织单元,包含库,程序,脚本和其它.
* `manifest`: package.xml, 软件包的描述,用于定义依赖和获取包的元信息,如版本,维护者,版权等.

## 工具

* `rospack`: 获取包的路径信息,`rospack find [pkg]`
* `roscd`: `rosbash`套件的一部分,可直接切换到包或栈的目录,`roscd [pkg[/subdir]]`
* roscd切换的是rospack输出的目录.且此目录必须是在`ROS_PACKAGE_PATH`环境变量定义内的.
* `roscd log`会切换到日志文件的存储目录.
* `rosls`: `rosbash`套件的一部分,列出包目录的文件,`rosls [pkg[/subdir]]`
* 以上工具支持tab补全
