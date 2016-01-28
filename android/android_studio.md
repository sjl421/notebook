# android studio

若宿主系统是`linux 64`位,需要安装32位库.不然会出现`Unable to run mksdcard SDK tool`的错误

Ubuntu: `sudo apt-get install lib32z1 lib32ncurses5 lib32bz2-1.0 lib32stdc++6`

同时,为了加速模拟器的运行效率,也推荐安装`KVM`.

确保CPU支持虚拟化: `egrep -c '(vmx|svm)' /proc/cpuinfo`, 输出非0

若运行`XEN`内核: `cat /sys/hypervisor/properties/capabilities`,应该看到`hvm`

推荐64位系统: `egrep -c ' lm ' /proc/cpuinfo`,输出非0

安装: `$ sudo apt-get install qemu-kvm libvirt-bin ubuntu-vm-builder bridge-utils`
