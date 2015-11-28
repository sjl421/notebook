# python调试

``` python
import pdb
...
pdb.set_trace()
```

## Usage

基本命令:
* `(pdb) h [cmd]`: 查看帮助
* `(pdb) l`: 查看源代码
* `(pdb) b line_num`: 在某行下断点
* `(pdb) cl [b_num]`: 清除断点
* `(pdb) c`: 运行到断点
* `(pdb) r`: 执行到函数返回前
* `(pdb) j line_num`: 跳转到指定的行
* `(pdb) n`: 步进, 不进入函数
* `(pdb) step`: 步进, 进入函数
* `(pdb) p var`: 打印变量值
* `(pdb) q`: 退出
