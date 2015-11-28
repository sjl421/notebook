# ubuntu网络配置

静态IP: `/etc/network/interfaces`

```
auto eth0
iface eth0 inet static
address 192.168.168.80
netmask 255.255.255.255
gateway 192.168.168.1
```

DNS域名:

暂时改变: `/etc/resolv.conf`

```
nameserver 202.106.46.151
nameserver 202.106.195.68
```

永久改变: `/etc/resolvconf/resolv.conf.d/base`

或: `/etc/network/interfaces`加入`dns-nameservers 202.106.46.151 202.106.195.68`

停止:`ifdown eth0`, 开启: `ifup eth0`