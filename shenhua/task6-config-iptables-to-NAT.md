# iptables

所有经由网上的数据包,原则上都可以通过工具进行包头数据的查看和更改,并根据结果进行规则限制.

iptables做为Linux集成到内核的包收发规则管理, 由三个表(filter, nat, mangle)构成, 每个表由多个链构成(PREROUTING, INPUT, OUTPUT, FORWARD, POSTROUTING).

随内核版本改进,可能添加更多的表和链, 但原理是一样的.所有的表和链构成数据进出本地的关卡.

NAT分为改源IP的SNAT和目标IP的DNAT. 本质相同都是修改数据包的字段.

## SNAT

局域网主机访问外网,　数据包经路由导向网关,　网关通过内核数据转发机制,　修改数据包的源IP地址, 并缓存相关数据, 当收到回包时, 通过比较缓存数据再发送到内网主机. 以此实现IP共享的功能.

命令: `iptables -t nat -A POSTROUTING -s 192.168.44.0/24 -o eth1 -j SNAT --to-source 192.168.58.200`

## DNAT

当外网访问服务器时, DNAT服务器检测包信息, 自动将来自80端口的数据包目标地址改为局域网的真正服务器的地址, 以实现其访问, 并缓存相关数据. 当服务器处理完请求, 响应包发到DNAT, 再比较缓存, 将响应包回送给外网.

命令: `iptables -t nat -A PREROUTING -p tcp -dport 80 -i eth0 -j DNAT --to-destination 192.168.44.200:80
