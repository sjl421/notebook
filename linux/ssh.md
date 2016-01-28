# ssh

`ssh user@host [port]`

## Usage

* 通过`ssh-keygen -t rsa -f ~/.ssh/id_rsa`命令产生公钥和私钥, 
* 通过`scp ~/.ssh/id_rsa.pub user@host:/home/user/.ssh/id_rsa_A.pub`命令复制公钥到远程主机上, 也可以通过`ssh-copy-id -i ~/.ssh/id_rsa.pub user@host`命令复制. 当`-i`选项省略时, 复制默认公钥, 即`id_rsa.pub`.
* 在远程主机上执行`cat ~/.ssh/id_rsa_A.pub >> ~/.ssh/authorized_keys`将公钥添加到验证文件内, 在可以在本地通过ssh免密码登录到远程主机.
* 在`~/.ssh/config`文件中可对登录不同主机使用不同公钥进行配置. 再使用`ssh hostname_alias`命令进行直接登录.

```
host hname_alias
    user enali
    hostname enalix.org
    port 22
    identityfile ~/.ssh/enali.pub
```

禁止`ssh`使用域名解析:`/etc/ssh/sshd_config`中改`UseDNS no`.再重启服务`systemctl restart sshd.service`.

强烈建议使用`config`文件配置,同时对所有使用`ssh`的命令也有用.

如: `scp local_file user@host:/path/to/file`, 可直接写为`scp local_file hname_alias:/path/to/file`

如: `sftp hname_alias`
