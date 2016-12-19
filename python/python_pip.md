# python package manager

install pip: `sudo apt-get install python-pip python3-pip`

```sh
pip install pkg  #安装包
pip install --upgrade pkg  #更新指定包, 默认不支持升级所有包
pip help subcmd  #子命令帮助
pip list --outdate  #列出可升级包
pip list --uptodate  #列出已安装更新的包
pip freeze --local | grep -v '^\-e' | cut -d = -f 1  | xargs pip install -U  #更新所有包
```
