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

最常使用的命令:
* `git clone https://github.com/enali/cfgdir.git`, 克隆`repo`到本地
* `git push origin master`, 改动提交后, 再推送到原`repo`的主分支

常用工作流程(新建分支->改动提交->合并到主分支->删除新分支):
* `git init`, 初始化当前目录为空仓库
* `git checkout -b br_name`, 新建并切换到新分支
* `git add -A`, 添加所有文件到缓存区
* `git commit -m "msg"`, 提交改动到仓库
* `git checkout master`, 切换到主分区
* `git merge br_name`, 将新分支合并进主分支
* `git branch -d br_name`, 删除新分支

远程仓库(新建仓库得在网站上完成):
* `git remote add origin url`, 将`url`认为此项目的原始远程仓库地址
  * `origin`, 其实可以是任意名称, 并与`url`对应, 即项目可以有多个远程仓库地址
* `git remote rename <old> <new>`, 重命名仓库名称
* `git remote remove <name>`, 删除指定的远程仓库
* `git remote get-url <name>`, 获取指定名称的仓库`url`
* `git remote set-url <name> <newurl>`, 设定指定名称的仓库新`url`
* `git remote -v`, 查看项目所有的远程仓库名称及地址
