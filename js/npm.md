# npm

`nodejs`的包管理器.

搜索包: `npm search pkg`
安装包: `npm [-g] install pkg`,全局
移除包: `npm rm pkg`
移除不相关包: `npm prune [pkg]`
更新包: `npm update [-g] [pkg]`, 全局

配置:
* `npm set <key> <value>`: 设置键
* `npm get <key>`: 获取键
* `npm config delete <key>`: 删除键
* `npm config list`: 列出键
* `npm config edit`: 编辑`~/.npmrc`文件

## 换源

```sh
npm set registry https://npm.tuna.tsinghua.edu.cn/
echo "registry=https://registry.npm.taobao.org" > ~/.npmrc
npm install pkg --registry url
```

可以换淘宝源, 之后就可以直接用`cnpm`安装了:
```sh
npm install -g cnpm --registry=https://registry.npm.taobao.org
```
