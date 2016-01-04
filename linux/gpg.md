# gpg

## Introduction

`GNU Privacy Guard`是一种加密软件，是`PGP`加密软件的满足`GPL`协议的替代物。

非对称加密:
    若A，B要通信，A有密钥对pubA,secA，B有密钥对pubB,secB，A用pubB加密信息发送给
    B（B用pubA加密信息发送给A），B用secB解密A发来的信息（A用secA解密B发来的信息)。pubA(pubB)公开的，任何人可以获取（如果你放到网上的话），secA（secB）是私有
    的，禁止公示。只要能确认pubA是A的（pubB是B的），则A与B的通信是安全的。所以如何确认公钥是通信对方的公钥是问题的关键，百科下数字证书。

## Usage

基本命令:
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