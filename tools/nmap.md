# nmap

## install

`sudo apt-get install libpcre3 libpcre3-dev libpcap0.8 libpcap-dev liblua5.2-dev liblinear1 liblinear-dev libarchive13 libarchive-dev libpcre++0 libpcre++-dev`

## Usage

## 基本命令

- `nmap [-v] 目标`目标可以是域名,IP地址,IP网段等, 默认发送一个arp的ping包,探测1-10000范围的端口, 选项`-v`显示扫描过程
    - `-iL <inputfilename>`从文件读取要扫描的主机列表
    - `-iR <hostnum>`随机选取hostnum个IP进行扫描, 为0时意味着扫描所有的已知的IP
    - `--exclude <h1 [, h2] [, h3]>`从范围中排除h1,h2,h3的主机或网络
    - `--excludefile <excludefile>`排除文件列表中的主机
    - `-p1-1024`或`-p1,2,3`扫描连续或列出的端口

任何扫描最初几个步骤之一就是把一组IP范围缩小为一列活动的或你感兴趣的主机, 称为主机发现. 重点是通过各种方式来确定某IP地址是否活动. 锁定目标后才会对目标的每个端口进行扫描.

如果没有给出主机发现的选项, Nmap就发送一个TCP ACK报文到80端口和一个ICMP回声请求到每台目标机器. 一个例外是ARP扫描总是被默认用于局域网上的任何目标机器. 


### 主机发现

    - `-P`用于选择ping的类型, 其中ARP为`-PR`
    - `-P0`跳过ping的主机发现, 对每个IP的每个端口都进行扫描, 较慢. 而通常Nmap只对正在运行的主机进行高强度的探测.
    - `-PS[80,84,98]`TCP SYN Ping向列出的端口并发地发送一个设置SYN标志位的空TCP报文. 意即告诉对方您正试图建立一个连接,或目标端口关闭,则收到RST包,否则收到SYN/ACK包, 此时回复RST包, 终止最后一步的连接.但事实上, 无论收到什么包, 都会断定主机在运行.
    - `-PA[80,84,98]`TCP ACK Ping同上, 只是发送ACK标志位的空包, 意即我这边确认建立一个连接, 但对方如果运行会回复RST包, 表示他没有发送请求连接的包.
    注意, 如果以非特权用户运行, 由于不能收发原始的TCP报文, 只能通过connect()系统调用正规地发送SYN包进行连接.
    - `-PU [80,84,98]`UDP Ping发送空的UDP报文到给定的端口,默认31338端口. 如果目标端口关闭,则收到一个端口无法到达的ICMP回应报文, 即机器是运行的. 如果目标端口开放, 则大部分服务会忽略此空报文而不做回应, 会被解释为主机不运行, 这也是为什么要选择一个极不可能被使用的端口. 优势:可以穿越只过滤TCP的防火墙和过滤器.
    - `-PE`, `-PP`, `-PM`ICMP Ping Types, Nmap发送一个ICMP type 8回声请求报文, 期待收到type 0回声响应的报文. 不过目前许多主机和防火墙都封锁ICMP报文. 其中`-PE`为回声请求8, `-PP`为时间戳请求14, `-PM`为地址掩码请求18.
### 端口扫描

    - `-sL`列表扫描, 仅列出指定网络上的每台主机, 不发送任何报文到目标主机. 默认情况会对主机进行反向域名解析. 
    - `-sP`仅仅进行ping扫描,然后打印对扫描做出响应的主机,不进行进一步的测试,如端口扫描或操作系统探测.默认情况发送一个ICMP回声请求和一个ACK报文到80端口.若为非特权用户发送SYN报文, 若为局域网则发送ARP请求.
    - `-sS`TCP SYN扫描, 半开放扫描, 不建立连接, 速度快, 收到SYN/ACK表示端口开放, 收到RST表示端口关闭, 什么也没收到或收到ICMP不可到达错误则表示被过滤. 默认的扫描选项. 可明确可靠地区分`open`, `closed`, `filtered`状态
    - `-sA`TCP ACK扫描
    - `-sT`TCP connect()扫描, 通过调用connect()系统调用与目标端口建立连接, 与浏览器建立连接的方式相同, 不能读取响应的原始报文. 需要特权. 完全连接, 可能被目标记录.
    - `-sU`UDP扫描, 常见服务DNS, SNMP, DHCP. 可以和所有TCP方式的扫描结合使用, 以同时探测两种协议. 如果收到ICMP端口不可到达错误(类型3,代码3)则端口关闭, 别的不可到达错误则是被过滤, 若收到响应的UDP报文, 则是开放.
    - `-SN`, `-sF`, `-sX`TCP Null, FIN, Xmas扫描. 空扫描即不设置任何标志位, 只设置TCP FIN标志位和设置FIN,PSH,URG标志位.
    - 
    - `-n`不对活动的IP进行域名反解析
    - `-R`对所有目标IP进行域名反解析
    - `-PR`ARP Ping, 使用场景是扫描一个以太局域网.
    - `-O`操作系统扫描
    - `--traceroute`路由跟踪
    - `-A`同时进行端口扫描,操作系统扫描,脚本扫描,路由跟踪,服务探测.

端口状态:
- `open`: 应用程序正在该端口接收TCP连接或UDP报文
- `closed`: 端口是关闭的, 接受Nmap探测报文并作出响应, 但没有应用程序在其上监听
- `filtered`: 过滤器只是丢弃探测帧, 不做任何响应. 几乎不提供任何信息.
- `unfiltered`: 端口可访问, 但不能确定是开放还是关闭. 只有ACK扫描才会产生这种状态
- `open|filtered`: 
- `closed|filtered`:


## 脚本扫描

- `auth`: 负责处理鉴权证书（绕开鉴权）的脚本  
- `broadcast`: 在局域网内探查更多服务开启状况，如dhcp/dns/sqlserver等服务  
- `brute`: 提供暴力破解方式，针对常见的应用如http/snmp等  
- `default`: 使用-sC或-A选项扫描时候默认的脚本，提供基本脚本扫描能力  
- `discovery`: 对网络进行更多的信息，如SMB枚举、SNMP查询等  
- `dos`: 用于进行拒绝服务攻击  
- `exploit`: 利用已知的漏洞入侵系统  
- `external`: 利用第三方的数据库或资源，例如进行whois解析  
- `fuzzer`: 模糊测试的脚本，发送异常的包到目标机，探测出潜在漏洞 intrusive: 入侵性的脚本，此类脚本可能引发对方的IDS/IPS的记录或屏蔽  
- `malware`: 探测目标机是否感染了病毒、开启了后门等信息  
- `safe`: 此类与intrusive相反，属于安全性脚本  
- `version`: 负责增强服务与版本扫描（Version Detection）功能的脚本  
- `vuln`: 负责检查目标机是否有常见的漏洞（Vulnerability），如是否有MS08_067

- `nmap --script=script_name 192.168.168.112`

## 应用服务扫描

vnc扫描: 
- `nmap --script=realvnc-auth-bypass 192.168.168.112`检查vnc bypass
- `nmap --script=vnc-auth 192.168.168.112`检查vnc认证方式
- `nmap --script=vnc-info ip-addr`获取vnc信息

smb扫描:
- `nmap --script=smb-brute.nse  ip-addr`smb破解
- `nmap --script=smb-brute.nse --script-args=userdb=/var/passwd,passdb=/var/passwd ip-addr`smb破解
- `nmap --script=smb-check-vulns.nse --script-args=unsafe=1 ip-addr`