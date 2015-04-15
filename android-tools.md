# android tools

You can add the Android tools by typing cmd
`sudo apt-get install android-tools-*` in ubuntu.

You can just add the path of android tools to you `Path` environment
if you have install the android-sdk to your disk.

## adb

* `adb devices`: list current connected devices, include your phone and android-vm
* `adb kill/start-server`: start/kill adb server
* `adb push/pull`: copy file from local to device, or reverse.
* `adb shell`: start a android device's shell, usually `sh`
* `adb shell {cmd [args]}`: just execute a shell cmd, not start a shell
* `adb install/uninstall *.apk`: install/uninstall app
* `adb connect/disconnect ip:port`: connect phone by wifi
* `adb logcat`: get the log of phone

## adb shell

* `$ dd if=file1 of=file2`: copy file from one partition to another, `mv` can't
* `$ mount -o remount rw /system`: remount the `/system` partition to `rw` mode

## fastboot

* `adb reboot bootloader`: get into fastboot mode
* `fastboot OEM unlock`: unlock the bootloader
* `fastboot update`: update phone system using `update.zip` in TF
* `fastboot flash boot/system/recovery`: flash the `*.img` file to system partition
* `fastboot reboot`: reboot system
* `fastboot reboot-bootloader`: reboot system to fastboot mode

