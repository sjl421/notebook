# VSCODE

## python

`ext install python`

依赖:

`pip install flake8`, 代码风格检测

配置文件`~/.config/flake8`(`Linux`)或`~/.flake8`(`Windows`)

`pip install yapf`, 在保存时自动格式化代码

`pip install ptvsd`, 远程调试, 需要配置

`pip install nose`, 单元测试框架

安装`ctags`以提供`Workspace Symbols`跳转

## go

`ext install go`

依赖:

`go get -v pkg_url`来安装

* `github.com/golang/lint/golint`, `golint`代码质量检测
* `github.com/tpng/gopkgs`, `gopkgs`带版本号的包管理
* `sourcegraph.com/sqs/goreturns`, `goreturns`为返回值添加0值(对应不同类型)
* `github.com/nsf/gocode`, `gocode`提供代码补全的守护程序
* `github.com/rogpeppe/godef`, `godef`打印符号被定义的位置
* `github.com/derekparker/delve/cmd/dlv`, `dlv`调试器
* `github.com/lukehoban/go-outline`, `go-outline`从源码提取声明的`json`表示
* `github.com/newhook/go-symbols`, `go-symbols`从源码提取包符号的`json`表示
* `golang.org/x/tools/cmd/guru`, `guru`代码导航工具
* `golang.org/x/tools/cmd/gorename`, `gorename`标识符重命名
* `github.com/cweill/gotests`, `gotests`从源码生成测试文件
* `github.com/fatih/gomodifytags`, `gomodifytags`在结构中修改/更新字段标签

## Ruby

`ext install ruby`

依赖:

## C/C++

`ext install cpptools`

## js

`cnpm install -g eslint`

## csharp

下载安装[.NET Core SDK](https://www.microsoft.com/net/core#windowscmd)
