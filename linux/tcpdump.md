# tcpdump

## 主机

1, 基本命令: `tcpdump -i eth0`

2, 截获主机进入和发出数据: `tcpdump host hname` 或 `tcpdump host 192.168.58.1`

注: 主机必须位于局域网内

3, 截获主机A与主机B或与主机C间的通信数据: `tcpdump host hn1 and \( hn2 or hn3 \)`

注: 转义'()'防止被shell解析

4, 截获主机A与任何非主机B的主机的通信: `tcpdump host hn1 and not hn2`

5, 截获主机A发送的所有数据: `tcpdump src host hnA`

   截获主机A收到的所有数据: `tcpdump dst host hnA`

## 指定网络

1, 截获主机23端口接发的tcp包: `tcpdum tcp port 23 and host hnA`

2, 监听本机123端口,协议udp: `tcpdump udp port 123`

## 指定网络

1, 截获通过网关gwA的ftp数据包: `tcpdump 'gateway gwA and (port ftp or ftp-data)'
