# Go

## 安装

下载安装包安装后, 设置`GOROOT`(指向安装目录), 并将`GOROOT/bin`添加到用户的`PATH`中.

`GOPATH`比较有趣, 类似于`Java`的`CLASSPATH`, 在`import`语句时搜索路径, 有固定的3个目录:
`src`, `pkg`, `bin`. 通常项目需要在`src`目录中创建, 编译后的目标文件会存放在`pkg`中, 可
执行文件在`bin`中. 通过`go get`下载安装的包也存放在`GOPATH`中, 可以有多个目录以分隔符分隔.
通常需要将`GOPATH/bin`目录添加到`PATH`路径中, 以使用下载安装的第三方工具.

* `go help`查看帮助
* `go version`查看版本
* `go build a.go`编译生成目标文件
* `go clean`删除生成的目标文件
* `go run a.go`编译并运行
* `go get [flag] pkg`下载并安装包
  * `-d`, 只下载
  * `-v`, 显示详细信息
  * `-u`, 更新包
* `go install pgk`安装包

`gopkgs`将版本号映射到`github`仓库的分支:

* `gopkg.in/pkg.v3`对应`github.com/go-pkg/pkg (branch/tag v3, v3.N, V3.N.M)`
* `gopkg.in/user/pkg.v3`对应`github.com/user/pkg (branch/tag v3, v3.N, V3.N.M)`
