# Android Studio

### 问题

1, 导入项目时，卡在Building gradle project info，主要原因还是因为被墙的结果。gradle官网虽然可以访问，但是速度连蜗牛都赶不上.

看`C:\Users\user_name\.gradle\wrapper\dists\gradle-x.xx-all\xxxxxxxxxxxx`, 看下`gradle`的版本号, 再从官网`https://gradle.org/releases`下载`complete`版本的压缩包, 名称为`gradle-x.xx-all.zip`, 直接放到上述目录即可.

2, 升级时, 遇到`Failed to read or create install properties file`.

这个是权限问题, 要么更改下`sdk`目录的读写权限(`windows`下不知道怎么做), 要么直接以管理员权限启动`android studio`.