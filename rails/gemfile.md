# rails' Gemfile

每次你新建`Rails`项目, `Gemfile`的首行指定了不能使用的`gem`源.

第一种方法, 改变模板(`$GEM_HOME/gems/railties-x.x.x/lib/rails/generators/rails/app/templates/Gemfile`)

第二种方法, 修改`~/.railsrc`文件, 其会在新建项目文件后, 在`bundle install`前载入:
* 在`~/.railsrc`文件中写入`--template=~/.template.rb`内容
* 在`~/.template.rb`文件中写入`gsub_file "Gemfile", /^source.*/, "source 'https://ruby.taobao.org/'"`内容
