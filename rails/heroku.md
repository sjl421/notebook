# heroku

提供`Rails`和其它`Web`应用的部署, 只要保存各开发框架的目录结构, 并将源码纳入`Git`
版本控制系统就好.

`heroku`使用`PostgreSQL`数据库, 需要在`Gemfile`添加, 再`bundle install`:
```ruby
group :production do
  gem 'pg', 'x.y.z'
end
```

`heroku`默认使用`puma`作为`Rails`的运行服务器.

## Install

```sh
mac$ brew install heroku  # 从s3.amazonaws.com下载, 国内似乎不能访问
```

## Usage

* `heroku version`, 查看版本
* `heroku login`, 登录你注册的帐号
* `heroku keys:add`, 添加`ssh`公钥
* `heroku create`, 在当前目录创建应用, 会自动添加`heroku`的远程仓库`URL`
* `git push heroku master`, 将项目推送到`heroku`, 会自动下载所需`gem`
* `heroku open`, 在本地浏览器打开服务器地址, 主要是应用名是随机字串
* `heroku rename new_name`, 重全名应用名, 使用有意义名字, 而非系统的随机字串
* `heroku run cmd`, 在远程服务器运行命令, 包括`shell`命令和`rails`命令
  * `rails db:migrate`, 此命令同本地相同, 执行数据库迁移
  * `[rails] console`, 运行`Rails`控制台, `Rails`可省略
* `heroku logs`, 查看生产环境下服务器运行的日志
* `heroku maintenance:on/off`, 开启/关闭维护模式, 推送后迁移前, 显示标准错误页面
* `heroku pg:reset DATABASE`, 重置生产环境的数据库
  * `--confirm app_name`, 不用提示确认
* `heroku restart`, 重启应用
* `heroku config:get ENV_NAME`, 获取环境变量的值
