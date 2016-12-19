# Ryu

## Install

```shell
sudo apt-get install python-pip python-dev build-essential
sudo pip install --upgrade pip
sudo apt-get install python-eventlet
sudo apt-get install python-routes
sudo apt-get install python-webob
sudo apt-get install python-paramiko
sudo pip install ryu
```

第二种方式:
```sh
git clone git://github.com/osrg/ryu.git
cd ryu
sudo pip install -r tools/pip-requires
sudo python setup.py install  #或者pip install ryu
```
