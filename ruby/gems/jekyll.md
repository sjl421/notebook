# Jekyll

install: `$ gem install jekyll`

## Usage:

* create a blog: `$ jekyll new myblog`
* start jekyll server: `$ jekyll server [--detach --watch]`

## github pages

remote:

1. 新建仓库, 命名`username.github.io`
2. 克隆到本地`git clone https://github.com/username.github.io`

locate:

create `Gemfile`, include below content:
```ruby
source "https://ruby.taobao.org"
gem 'github-pages'
```

注意: `github pages`使用的`jekyll`版本并不是最新版本, 如果追求显示效果相同, 还是安装`github-pages`较好.

使用`bundle install`安装, 使用`bundle update`更新

使用`bundle exec jekyll serve`运行服务

在浏览器打开`localhost:4000`即可查看