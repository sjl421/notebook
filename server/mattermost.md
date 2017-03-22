# Mattermost

`Mattermost`是一个团队沟通工具. 通常可理解为开源的`Slack`. 用于在团队中进行信息
沟通和文件共享.

`Mattermost`通常由3个组件构成, `nginx`负责代理, `Mattermost`负责响应, `postgresql`
负责存储数据. 可以全部安装在一台机器上, 也可以安装在不同机器上.

## 安装

1, 安装数据库, 以`PostgreSQL`为例:

```sh
sudo apt-get install postgresql postgresql-contrib  #安装postgresql
sudo -u postgres psql  #以postgres登录psql
```

在数据库创建用户/数据库:

```sql
postgres=# CREATE DATABASE mattermost;
postgres=# CREATE USER mmuser WITH PASSWORD 'mmuser_password';
postgres=# GRANT ALL PRIVILEGES ON DATABASE mattermost to mmuser;
```

需要注意的是, 如果将数据库安装在另外一台机器上, 需要为`postgresql server`设置监听
端口, 默认只监听本地. 编辑`/etc/postgresql/9.x/mainnn/postgresql.conf`文件, 修改
`listen_addresses = 'ip'`, 可以指定地址, 也可以使用`*`来表示监听任意地址.

除此外, 还要编辑`/etc/postgresql/9.x/main/pg_hba.conf`文件, 添加`host all all 
<mm-server-ip>/32 md5`行内容, 其中`mm-server-ip`是指`mattermost`服务器的`ip`. 意
即允许此`ip`的连接, 此处的32表示主机位数.

2, 下载并解压`mattermost`, 以解压到`/opt`为例:

创建存储数据的目录: `sudo mkdir /opt/mattermost/data`

3, 创建用户并赋予目录正确权限:

```sh
sudo adduser --system --group mattermost
sudo chown -R mattermost:mattermost /opt/mattermost
sudo chmod -R g+w /opt/mattermost
```

4, 配置`mattermost`, 编辑文件`/opt/mattermost/config/config.json`:

设置`DriverName`为`postgres` 

设置`DataSource`为`postgres://mmuser:<mmuser-password>@<host-name>:5432/mattermost?sslmode=disable&connect_timeout=10`

5, 确认可正确启动: `sudo -u mattermost bin/platform`.

出现`Server is listening on :8065`即可.

6, 编写`service`文件, 创建`/etc/systemd/system/mattermost.service`文件:

```systemd
[Unit]
Description=Mattermost
After=network.target
After=postgresql.service
Requires=postgresql.service

[Service]
Type=simple
ExecStart=/opt/mattermost/bin/platform
Restart=always
RestartSec=10
WorkingDirectory=/opt/mattermost
User=mattermost
Group=mattermost

[Install]
WantedBy=multi-user.target
```

使开机自启: `sudo systemctl enable mattermost.service`.

启动服务: `sudo systemctl start mattermost.service`.

7, 打开浏览器, 访问`ip:8065`即可. 

注意, 第一个创建的用户即为管理员, 没有外在的注册入口, 用户只能通过邀请链接注册.

