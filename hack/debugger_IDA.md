# IDA

最强调试器.

## 常用快捷键

* `C+E`, 跳到程序的入口点, 往往是`_start`或`main`函数入口
* `C+L`, 在符号表间跳转
* `C+P`, 在函数名间跳转
* `C+S`, 在段间跳转
* `C+X`, 谁索引了我, 
* `C+J`, 我索引了谁
* `G`, 跳转, 可输入地址
* `ESC`, 返回跳转前的位置

* `D`, 在`db/dw/dd`数据格式间转化
* `A`, 在数据和`String`间转化
* `C`, 将数据解释为代码, 即汇编码
* `;`, 在汇编码层面添加注释
* `/`, 在`C`代码层面添加注释
* `Tab`, 查看反编译代码

* `A+I`, 搜索立即数
* `C+I`, 下一个立即数
* `A+T`, 搜索文本
* `C+T`, 下一个文本
* `A+B`, 搜索字节序列
* `C+B`, 下一个字节序列

## 调试

`F9`启动调试进入断点, `F2`设置断点, `F8`步过, `F7`步入, `C+F2`终止.

调试时, 可选择`Use sourse-level debugging`.

对本地的`windows`程序, 直接在调试菜单选择"本地调试器"即可.

对`Android`程序: 

```sh
adb push dbgsrv/android_server /system
# adb shell mount -o remount,rw /system  # 可能需要重新挂载/system分区
adb shell # 进入android的shell
cd /system
./android_server  # 启动调试服务器
adb forward tcp:23946 tcp:23946  # 设置本地的端口转发
```

对于`Linux`程序:

```sh
scp dbgsrv/linux_serverx64 user@ip:~/bin  # 复制文件
sudo ./bin/linux_serverx64  # 启动调试服务器
# F9 选择Remote Linux debugger
# 在Debugger -> Process options中, Application/Input file写执行程序在user@ip中的路径, 写hostname, 剩下可留空
```

## View

有用的视图: `View->Open subviews->Strings`, 可打开字符串视图, `S+F12`