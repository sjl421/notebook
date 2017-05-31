# GenyMotion

最好的案桌虚拟机模拟器

下载地址: `https://www.genymotion.com/download/`

## GenyMotion Shell

可以控制安卓虚拟机的相关功能, 但是部分功能收费.

## ARM包刷入

因为`GenyMotion`的虚拟机都是`x86`的, 因此在安装带`.so`的应用时, 会提示`INSTALL_FAILED_NO_MATCHING_ABIS`错误.

下载`GenyMotion-ARM-Translation_v1.1.zip`包.

* `adb push GenyMotion-xxx.zip /sdcard/Download`
* `cd /sdcard/Download`
* `sh /system/bin/flash-archive.sh /sdcard/Download/Genymotion-ARM-Translation_v1.1.zip`

重启.

## 安装

`adb install xxx.apk`.