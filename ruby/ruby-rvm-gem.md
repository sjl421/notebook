# ruby

## rvm

install: `$ curl -sSL https://get.rvm.io | bash -s stable`

usage:

* 列出已安装 Ruby 版本: `rvm list`
* 列出已知的 Ruby 版本: `rvm list known`
* 安装指定版本的 Ruby: `rvm install x.y.z`
* 移除安装的 Ruby 版本: `rvm remove x.y.z`
* 指定使用的 Ruby 版本: `rvm [--default] use x.y.z`
* remove rvm: `$ rvm implode`
* update rvm: `$ rvm get stable`
* generate doc: `$ rvm docs generate [ri|rdoc|gem]`
* 重新生成配置文件: `rvm get stable --auto-dotfiles`

### change ruby sources

change the `rvm` to use taobao's sources.

```sh
sed -i 's!cache.ruby-lang.org/pub/ruby!ruby.taobao.org/mirrors/ruby!' $rvm_path/config/db
```

## gem

`gem` is the ruby's premier packaging system.

usage:

* search package: `gem search [-r] pkg-name`
* install package (from local or remote) (use pointed server url): `$ gem install [-l|-r] package [--source url]`
* list (matched name) (local or remote) package (with details): `gem list [-l|-r|-d] [regx]`
* update package or itself: `$ gem update [--system]`
* build your gem: `$ gem build foo.gemspec`
* deploy your gem: `$ gem push foo-1.0.0.gem`
* 删除已安装的旧版本gem: `$ gem cleanup`

### change gems sources

change the rubygems sources from `https://rubygems.org/` to `https://ruby.taobao.org/`

```sh
gem sources --remove https://rubygems.org/
gem sources -a https://ruby.taobao.org/
gem sources -l
gem update
```

if you use `bundle` to manager your dependency. change your Gemfile.

```ruby
source 'https://ruby.taobao.org/'
gem 'rails', '4.2.0'
```

### rails' Gemfile

Everytime you new a rails' project, the Gemfile's first line has the wrong source.

first method:
* changing its template.
* `gems/railties-x.x.x/lib/rails/generators/rails/app/templates/Gemfile`

second method:
* `~/.railsrc` file will be loaded after rails new all files before bundle install.
* write `--template=~/.template.rb` into `~/.railsrc`, then the file will be exce.
* write `gsub_file Gemfile, /^source.*$/, "source 'https://ruby.taobao.org/'"` into `~/.template.rb` to exec substitude
