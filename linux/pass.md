# pass

## Introduction

the standard unix password manager.密码管理工具.

实际上不止于密码管理，你可以管理任何私密信息，一种方法是把每条私密信息当作密码保存
在单个文件，另一种是添加密码insert时，`-m`可以添加多行信息。也可以重新编辑密码。

## Usage

基本命令:
* 以公钥初始，实际生成`~/.password-store/` :`pass init pubID`
* 为帐号添加密码，实际生成`~/.password-store/Email/enalix@163.com.gpg`
* 基于目录层级管理，帐号即文件，密码即文件内容。相当于`gpg -er pubID enalix@163.com` :`pass insert Email/enalix@163.com`
* 为帐号生成15位密码，再也不用为那些想密码了 :`pass generate Email/enalix@163.com  15`
* 查看密码，相当于`gpg -d enalix@163.com.gpg`, `c`复制，直接`<c-v>`粘贴 :`pass [-c] Email/enalix@163.com`
* 移除帐号密码，相当于rm enalix@163.com.gpg :`pass rm Email/enalix@163.com`
* 编辑帐号密码，调用默认编辑器，如vim :`pass edit Email/enalix@163.com`
* 引入git版本控制管理 :`pass git init`
* 添加远程仓库 :`pass git remote add origin https://github/enali/Pass.git`
* 推送到远程仓库 :`pass git push origin master`
