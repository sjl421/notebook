# kali

install vmware tools: `apt-get install linux-headers-$(uname -r)`

make link: `ln -s /usr/src/linux-headers-$(uname -r)/include/generated/uapi/linux/version.h /usr/src/linux-headers-$(uname -r)/include/linux`
