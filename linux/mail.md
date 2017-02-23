# Mail

从命令行发送电子邮件.

要注意的是, 有些电子邮件服务商会过滤过以这种方式发送的邮件, 因为邮件来源是无效的
地址, 但部分自搭建的邮件服务器, 如北航自己的, 可以接收到.

## mail

安装: `sudo apt install mailutils`

使用:
* `mail -s "主题" user@example.com < file`, 将文件内容发送给邮件地址
* `echo "内容" | mail -s "主题" user@example.com`, 将特定内容发送给邮件
* `echo "内容" | mail -s "主题" -A test.txt user@example.com`, 可带附件
* `mail -s "主题" user1@example.com,user2@example.com < file`, 将内容发给两个邮件
