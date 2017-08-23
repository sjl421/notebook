# XX-NET

[xx-net](https://github.com/XX-net/XX-Net)

* 下载, 解压, 运行`start.bat`, 会自动导入证书
* 在`SwitchOmega`中导入`OmegaOptions.bak`备份文件, 并更新情景模式
* [可选]创建自己的[appids](https://github.com/XX-net/XX-Net/wiki/how-to-create-my-appids), 并在"部署服务端"中填写, 成功后再在配置中填写

## 问题

虽然大部分时候没有必要, 但你的系统可能安装有多个`goagent xx-net`的证书, 可先删除旧证书, 再将`data/gae_proxy/CA.crt`证书导入根证书机构(可双击打开)