# dash

`dash`是`Mac`平台的一个软件, 提供各种开发文档的阅览. 其提供了一种标注功能, 可以方便在各种开发文档作笔记.

但可能因为`GFW`原因, 经常提示无法登录`Annotations`服务器.

在`github`上, 提供了`Kapeli/Dash-Annotations`, 可以在本地搭建`Annotations`服务器, 将标记存储在数据库中.

`Dash-Annotations`使用了`Lumen`, 一个用于`PHP`开发的微框架.

## 安装

1, `brew install php56`, 要求`php`的`5.6`版本, 不确定能不能改.

2, `brew install composer`, 用于`php`的依赖管理

3, `composer global require 'lavarel/lumen-installer'`, 要求添加`~/.composer/vendor/bin`到`PATH`路径中.

4, `git clone https://github.com/Kapeli/Dash-Annotations`, 克隆代码到本地

5, `cd Dash-Annotations && mv .env.example .env`, 主要修改`.env`文件, 用于配置.

6, 原代码使用`mysql`, 要求有`annotations`数据库

* 先确保`mysqld`已运行, `ps aux | grep mysqld`
* `mysql -uroot -p`, 进入`mysql`中
* `create user 'uname'@'localhost' identified by 'passwd'`, 添加新用户和密码
* `create database annotations`, 创建新的数据库
* `grant all privileges on annotations.* to 'uname'@'localhost' identified by 'passwd'`, 为新用户授予此数据库的所有权限.

7, `composer install`, 安装相关依赖

8, 安装`Python`和`Pygments`

9, `php artisan migrate`并确定`Y`.
