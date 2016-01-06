# android的启动流程

1, 引导加载程序完成硬件初始化, 并加载内核和`initrd`到内存

2, 内核会初始化内存,输入/输出区域,内存保护,中断处理程序,`CPU`调度器和设备驱动等, 最后挂载`root`文件系统, 并启动`init`

3, `init`根据`/init.rc`文件初始化用户空间组件, 启动一些核心服务, 如通话(`rild`), VPN(`mtpd`), adbd等. 其中`zygote`服务会创建`Dalvik`虚拟机, 并启动第一个Java组件`system server`.

4, 发送`ACTION_BOOT_COMPLETED`广播