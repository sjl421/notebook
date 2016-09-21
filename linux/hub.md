# hub

一个命令行的`Git`命令封装, 可更好地与`GitHub`配合使用.

## Install

```sh
mac$ brew install hub
unix$ git clone https://github.com/github/hub.git && cd hub
unix$ script/build -o ~/bin/hub  # 安装到~/bin目录
```

可以设置`alias git=hub`.

`hub version`, 查看版本

## Usage

* `git clone your_project`, 克隆自己的项目, 提示登录
* `git clone github/hub`, 克隆别人的项目
* `git browse -- issues`, 打开当前项目的`issues`页面
* `git browse mojombo/jekyll wiki`, 打开指定项目的`wiki`页面
* `git fork`, 克隆当前项目到自己的帐户, 提示登录
