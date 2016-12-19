# python package manager

install pip: `sudo apt-get install python-pip python3-pip`

更新自己: `sudo pip install --upgrade pip`

## 换源

```sh
pip install -i https://pypi.tuna.tsinghua.edu.cn/simple some-package  #临时
```

或者,`~/.pip/pip.conf`文件:

```sh
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
```
