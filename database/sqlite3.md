# sqlite3

安装: `sudo apt-get install sqlite3`

`sqlite3 dbname`: 若库名存在则打开,不存在则创建,不指定则创建临时的库,退出时删除

## 常用命令

* `.help`: 帮助
* `.open dbname`: 打开数据库
* `.save dbname`: 保存为数据库
* `.exit`: 退出
* `.tables`: 列出表名
* `.schema [tbname]`: 列出表结构
* `.output fname.txt`: 写结果到文件
