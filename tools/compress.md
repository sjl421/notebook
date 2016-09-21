# compress

多数平台支持的压缩和解压缩命令的常用法.

## zip

如有目录`foo`, 执行`zip -r foo.zip foo`即可. 

归档文件的结构会包含`foo`目录.

`-r`表示递归`foo`目录, 否则只压缩目录而非目录的文件.

如果`foo.zip`文件已经存在, 则会替换同名文件, 添加没有的文件.

使用`unzip -l foo.zip`来查看归档文件.

使用`unzip foo.zip`来解压归档文件到当前目录, 使用`-d path`来指定解压目录
