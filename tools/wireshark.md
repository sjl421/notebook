# wireshark

源码安装:
* `sudo apt-get install libgtk-3-0 libgtk-3-dev libglib2.0-0 libglib2.0-dev libpcap0.8 libpcap0.8-dev libpcap-dev perl python`
* `./configure --without-qt`
* `make && make install`

## Usage

过滤IP: `ip.src==192.168.168.107`或`ip.dst==192.168.168.107`

过滤端口: `tcp.port==80`或`tcp.dstport=80`或`tcp.srcport=80`

TCP参数: `tcp.flags`, `tcp.flags.syn==0x02`, `tcp.window_size==0 && tcp.flags.reset !=1`

过滤协议: `tcp/udp/icmp/arp`或`http/ftp/smtp`, 等协议名

过滤http: 动作`http.request.method=="GET/POST"`, 链接`http.request.uri=="/img/logo-edu.git"`, header`http contains "HTTP/1."`

过滤MAC地址: `eth.dst==10:00:00:04:c5:84`或`eth.src==10:00:00:04:c5:84`或`eth.addr==10:00:00:04:c5:84`

过滤包长度: `udp.length`, `tcp.len`, `ip.len`, `frame.len`

过滤内容: `tcp[20]`, `tcp[20:]`, `tcp[20:8]`

DHCP: `bootp.type==0x02`

`contains`是包含, `matches`是匹配

逻辑符: `and`, `or`, `not`

比较符: `>`, `<`, `>=`, `<=`, `==`

## problem

1. There are no interfaces on which a capture can be done.

```sh
sudo apt-get install wireshark libcap2-bin
sudo groupadd wireshark
sudo usermod -a -G wireshark $USER
sudo chgrp wireshark /usr/bin/dumpcap
sudo chmod 755 /usr/bin/dumpcap
sudo setcap cap_net_raw,cap_net_admin=eip /usr/bin/dumpcap
```
## 过滤条件

`TCP`重传: `tcp.analysis.retransmission`

`TCP`窗口大小: `tcp.analysis.window_size == 0`

`TCP`重复`ACK`报文: `tcp.analysis.duplicate_ack`
