# discourse

用ruby开发的一个论坛项目

## install

1. 安装docker, 可参考[docker install][../tools/docker.md]
2. 创建目录,克隆代码,配置文件.

```sh
mkdir /var/discourse
git clone https://github.com/discourse/discourse_docker.git /var/discourse
cd /var/discourse
cp samples/standalone.yml containers/app.yml
```

3. 编辑配置文件, `DISCOURSE_DEVELOPER_EMAILS`, `DISCOURSE_HOSTNAME`, `DISCOURSE_SMTP_ADDRESS, DISCOURSE_SMTP_PORT, DISCOURSE_SMTP_USER_NAME, DISCOURSE_SMTP_PASSWORD` `UNICORN_WORKERS`, `db_shared_buffers`

4. 抓取镜像,`./launcher bootstrap app`,会花费较长时间
5. 运行,`./launcher start app`
