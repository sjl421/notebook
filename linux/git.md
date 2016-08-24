# git

## config

只有`name`和`email`是必要的. `--global`表示全局选项.

```sh
git config --global user.name $USER  # 配置提交用户名
git config --global user.email $EMAIL  # 配置提交邮箱
git config --global core.editor $EDITOR  # 配置编辑器
git config --global color.ui true
git config --global credential.helper cache
```

设置命令别名:
```sh
git config --global alias.cl clone
git config --global alias.co checkout
git config --global alias.ci commit
git config --global alias.br branch
git config --global alias.mg merge
git config --global alias.rt remote
```

## usage

`git clone https://github.com/enali/cfgdir.git`, 克隆`repo`到本地, 使用`https`协议

