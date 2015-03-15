## change gems sources

change the rubygems sources from https://rubygems.org/ to https://ruby.taobao.org/

```sh
$ gem sources --remove https://rubygems.org/
$ gem sources -a https://ruby.taobao.org/
$ gem sources -l
$ gem update
```

if you use `bundle` to manager your dependency. change your Gemfile.

```ruby
source 'https://ruby.taobao.org/'
gem 'rails', '4.2.0'
```

## change ruby sources

change the `rvm` to use taobao's sources.

```sh
$ sed -i 's!cache.ruby-lang.org/pub/ruby!ruby.taobao.org/mirrors/ruby!' $rvm_path/config/db
```
