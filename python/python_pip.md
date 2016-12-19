# python package manager

install pip: `sudo apt-get install python-pip python3-pip`

更新自己: `sudo pip install --upgrade pip`


## 常用命令

```sh
pip install pkg  #安装包
pip install --upgrade pkg  #更新指定包, 默认不支持升级所有包
pip help subcmd  #子命令帮助
pip list --outdate  #列出可升级包
pip list --uptodate  #列出已安装更新的包
pip freeze --local | grep -v '^\-e' | cut -d = -f 1  | xargs pip install -U  #更新所有包
```

## 换源

```sh
pip install -i https://pypi.tuna.tsinghua.edu.cn/simple some-package  #临时
```

或者,`~/.pip/pip.conf`文件:

```sh
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
```
