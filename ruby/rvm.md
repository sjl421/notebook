# rvm

## 安装 

`$ curl -sSL https://get.rvm.io | bash -s stable [--ruby] [--rails]`

可同时安装`ruby`和`rails`.

## 使用

* `rvm list`: 列出已安装`Ruby`版本 
  * `known`选项, 表示列出可支持的版本
* `rvm install x.y.z`: 安装指定版本的 Ruby 
* `rvm remove x.y.z`: 移除安装的 Ruby 版本 
* `rvm use x.y.z`: 指定使用的 Ruby 版本 
  * `--default`选项, 指定默认使用的版本
* `$ rvm implode`: 移除`rvm`, 但各种`.rc`文件相关配置要手动删除
* `$ rvm get stable`: 升级`rvm`
* `$ rvm docs generate [ri|rdoc|gem]`: 生成不同类型的文档
* `rvm current`: 当前正在使用的Ruby版本 
* `rvm upgrade 2.2.4 2.3.0`: 升级Ruby 
* `rvm gemdir`: 打印`$GEM_HOME`目录

## gemset

`gemset`是一个`gem`集的虚拟环境, 你只能看到和使用当前`gemset`环境下安装的`gem`. 即, 当你执行`gem`命令时 
* `list`只列出当前`gemset`下的`gem`, 
* `install`只将`gem`安装到当前`gemset`, 
* `cleanup`只清除当前`gemset`下安装的重复的`gem`.

当安装任意版本时, 会创建两个默认环境`@default`和`@global`. `@default`没任意意义, `@global`下安装的`gem`对所有`gemset`可见.

`gem`相对于`gemset`可见, 而`gemset`相对于`Ruby`版本可见.

使用:
* `rvm gemset name`: 列出当前的`gemset`名
* `rvm gemset list`: 列出当前`Ruby`版本下存在的`gemset`
* `rvm gemset list_all`: 列出所有`Ruby`版本的所有`gemset`
* `rvm gemset create set_name`: 创建当前`Ruby`版本下的`gemset`
* `rvm use x.y.z@set_name`: 切换`Ruby`版本和`gemset`, 若不切换版本, 则`x.y.z`可省
  * `--create`, 如果`gemset`不存在, 则创建
* `gem install pkg`: 将包安装在当前`gemset`下
* `rvm gemset empty [x.y.z@set_name]`: 清空`gemset`下所有的`Gem`
* `rvm gemset delete set_name`: 删除当前`Ruby`版本下的`gemset`
* `rvm gemset export set_name.gems`: 将当前`gemset`下安装的所有`gems`信息导出
* `rvm gemset import set_name.gems`: 将指定的`set_name`文件中列出的`gem`导入当前`gemset`中, 会自动开始安装

导出的`set_name.gems`中, `gem_name -vx.y.z`的格式(`-vx.y.z`可省略), 每个`gem`一行, 以记录此`gemset`下安装的所有`gem`信息.

在创建`gemset`时, 若`$rvm_path/gemsets`下存在同名的`set_name.gems`文件, 则创建`set_name`的`gemset`时, 会自动安装列出的`gem`.

在任意项目中, 新建`.rvmrc`文件, 并写入`rvm use x.y.z@set_name`, 则进入项目目录时, 会自动切换版本和`gemset`.

现在好像推荐使用`.ruby-version`, 直接写入`2.3.0@rails5`即可.

多`gemset`相同的`gem`要尽量安装在`@global`中, 否则, `gem`的重复安装会造成大量浪费. 

本质上, `gem install`时, 会将`gem`安装在`GEM_HOME`环境变量中, 切换`gemset`会自动更新当前`shell`中的`GEM_HOME`环境变量.

而`require 'pkg'`载入`gem`时, 会从`GEM_PATH`环境变量中搜索`gem`. 切换`gemset`也会自动更新此变量. 同时`@global`的`gemset`永远在`GEM_PATH`中. 所以说, 安装在`@global`中的`gem`对所有`gemset`可见.

通常情况下, 你可能需要不同版本的`Rails`去工作, 可以创建`@rails4`和`@rails5`两个环境, 在各自的环境下安装`rails`不同版本, 再通过切换`gemset`来使用不同的`rails`

## 换源

文件`$rvm_path/config/db`中的`ruby_url=URL`指定`rvm`使用的`Ruby`源. 

常用的`URL`有:
* `https://cache.ruby-lang.org/pub/ruby`
* `https://cache.ruby-china.org/pub/ruby`

```sh
sed -i 's!cache.ruby-lang.org/pub/ruby!ruby.taobao.org/mirrors/ruby!' $rvm_path/config/db
```
