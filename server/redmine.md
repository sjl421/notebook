# redmine

依赖: `imagemagick`

```sh
sudo apt-get install imagemagick libmagickwand-dev libmagickcore-dev`
```

* 下载安装包并解压:　`www.redmine.org`
* 创建数据库和用户:　`redmine@localhost`, `redmine database`
* `config/database.yml`文件,修改`production`环境下的配置
* 修改`Gemfile`的源为`https://ruby.taobao.org`
* 安装依赖:　`bundle install --without development test`
* 可以创建`Gemfile.local`文件,写入自己需要的`gems`,以便app启动时自动载入
* 生成新随机键加密存储会话的cookies,`bundle exec rake generate_secret_token`
* 生成数据库schema:`RAILS_ENV=production bundle exec rake db:migrate`
* 数据库默认数据:`RAILS_ENV=production REDMINE_LANG=zh bundle exec rake redmine:load_default_data`
* 用户对`files log tmp public/plugin_assets`拥有写权限
* 测试运行:`bundle exec rails server webrick -e production`
* 登录:`admin:admin`
* 主要的配置文件是`config/configuration.yml`
