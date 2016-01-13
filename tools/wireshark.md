# wireshark

源码安装:
* `sudo apt-get install libgtk-3-0 libgtk-3-dev libglib2.0-0 libglib2.0-dev libpcap0.8 libpcap0.8-dev libpcap-dev perl python`
* `./configure --without-qt`
* `make && make install`

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
