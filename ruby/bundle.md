# bundle

`Ruby`项目的依赖管理.

## Usage

最常用的是, 或项目根目录有`Gemfile`, 则执行`bundle install`即可安装依赖`gem`. 并
生成`Gemfile.lock`, 固定项目使用的`gem`版本.

关于`Gemfile`, 第一行指定`gem`的安装源, `source url`.

除了换源外, 可使用`bundle config --global jobs 4`来指定执行作业使用的内核数. 此
方法也能加快`bundle install`的速度.

通常情况下, 项目依赖的`gem`与系统安装的`gem`并非一个版本, 对于那些带有可执行程序
的`gem`, 直接执行`cmd`执行的是系统安装的`gem`程序, 虽然可以通过`cmd _x.y.z_`来
指定执行的版本, 但更常用是执行`bundle exec cmd`, 此命令执行项目依赖的`gem`.
