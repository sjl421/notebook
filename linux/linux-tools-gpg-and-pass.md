# gpg

`GNU Privacy Guard`是一种加密软件，是`PGP`加密软件的满足`GPL`协议的替代物。

非对称加密:
    若A，B要通信，A有密钥对pubA,secA，B有密钥对pubB,secB，A用pubB加密信息发送给
    B（B用pubA加密信息发送给A），B用secB解密A发来的信息（A用secA解密B发来的信息)。
    pubA(pubB)是公开的，任何人可以获取（如果你放到网上的话），secA（secB）是私有
    的，禁止公示。只要能确认pubA是A的（pubB是B的），则A与B的通信是安全的。所以如
    何确认公钥是通信对方的公钥是问题的关键，百科下数字证书。

* 交互式生成密钥对，生成`pubkey`用于签名，生成`subkey`用于加密 `gpg --gen-key`
* 交互式生成公钥回收证书，请谨慎保存 `gpg --gen-revoke pubID`

* 列出当前密钥 :`gpg --list-keys`
* 编辑指定公钥 :`gpg --edit-key pubID`
* 删除公钥 :`gpg --delete-keys pubID`
* 删除私钥 :`gpg --delete-secret-keys secID`
* 导出公钥 :`gpg -o pub.gpg --export pubID`
* 导出私钥 :`gpg -o sec.gpg --export-secret-keys pubID`
* 导入公钥或私钥 :`gpg --import file.gpg`
* 加密文件，默认输出为`file.gpg`,`e`加密,`r`指定ID :`gpg [-o file.gpg] -er pubID file`
* 解密文件，默认输出到`stdout`，`d`解密 :`gpg [-o file] -d file.gpg`
* 签名，`s`签名，`e`加密，`u`指定自己的secID, `r`指定接收者的pubID :`gpg -o file.sig -seu pubID`

## pass

the standard unix password manager.密码管理工具.

实际上不止于密码管理，你可以管理任何私密信息，一种方法是把每条私密信息当作密码保存
在单个文件，另一种是添加密码insert时，`-m`可以添加多行信息。也可以重新编辑密码。

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
