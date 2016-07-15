# npm

`nodejs`的包管理器.

搜索包: `npm search pkg`
安装包: `npm [-g] install pkg`,全局
移除包: `npm rm pkg`
移除不相关包: `npm prune [pkg]`

## 换源

```sh
npm set registry https://npm.tuna.tsinghua.edu.cn/
echo "registry=https://npm.tuna.tsinghua.edu.cn" > ~/.npmrc
npm install pkg --registry url
```
