# gem

`gem`是`Ruby`的包管理系统.

## 使用

* 搜索包: `gem search [-r] pkg-name`
* 安装本地/远程/指定URL的包, 可指定版本: `$ gem install [-l|-r] package [--source url] [-v x.y.z]`
* 列出本地/远程匹配的包, 可显示细节: `gem list [-l|-r|-d] [regx]`
* 更新包或`gem`自己: `$ gem update [--system]`
* 构建包: `$ gem build foo.gemspec`
* 部属包: `$ gem push foo-1.0.0.gem`
* 删除已安装的旧版本gem: `$ gem cleanup`

`cleanup`只清除当前`$GEM_HOME`目录下重复安装的`gem`, 考虑`rvm`中, `gem`属于`gemset`, 而`gemset`属于`Ruby`版本. 因此, `clean`只清除当前版本和`gemset` 中的重复`gem`.

## 换源

改变`gem`安装时的默认`源`:
```sh
gem sources --remove https://rubygems.org/
gem sources -a https://ruby.taobao.org/
gem sources -l
gem update
```

如果使用`bundle`在管理依赖, 可改变`Gemfile`文件:
```ruby
source 'https://ruby.taobao.org/'
gem 'rails', '4.2.0'
```
