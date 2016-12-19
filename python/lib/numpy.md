# numpy

核心类型为`ndarry`, 对其进行操作的`ufunc`函数.

通过`ufunc`函数同时对数组操作, 而非对数组的每个元素操作.

统称为数组, 虽然一维数组也称向量, 二维数组也称为矩阵.

## 数据类型

数组类型全部存储在`np.typeDict`字典中, 键是类型的别名, 值是类型`np.xxx`.

在64位平台, `python3.x`版本下:
* 默认的整数是`np.int64`
* 默认的浮点数是`op.float64`
* 默认的复数是`np.complex128`, 即`1+2j`
* 默认的布尔值是`np.bool_`
* 默认的字串是`np.str_`, 即`'hello'`
* 默认的字节字串是`np.bytes_`, 即`b'hello'`

单字符代指类型:
* `b`代指`np.int8`, `i`,`h`代指`np.int32`, `l`,`p`,`q`代指`np.int64`
* `B`代指`np.uint8`, `I`,`H`代指`np.uint32`, `L`,`P`,`Q`代指`np.uint64`
* `e`代指`np.float16`, `f`代指`np.float32`, `d`代指`np.float64`, `g`代指`np.float128`
* `F`代指`np.complex64`, `D`代指`np.complex128`, `G`代指`np.complex256`
* `a`,`S`代指`np.bytes_`, `U`代指`np.str_`, `O`代指`np.object_`, `?`代指`np.bool_`, `M`代指`np.datetime64`

数字表示字节数, 至于具体的位数要`*8`:
* `i1, i2, i4, i8`代指有符号整数
* `u1, u2, u4, u8`代指无符号整数 
* `f2, f4, f8, f16`代指浮点数
* `c8, c16, c32`代指复数

结构类型:
* `np.dtype({'names': ['name', 'age', 'weight'], 'formats': ['S30', 'i', 'f']})`, 创建结构类型, `'names'`描述字段名, `'formats'`描述字段类型
  * `align=True`, 表示进行内存对齐, 即保持内存地址为4或8的整数倍以快捷存取
* `np.dtype([('name', '|S30'), ('age', '<i4'), ('weight', '<f4')])`, 同上. 其中`|><`表示字节顺序, 忽略, 小端, 大端
* `np.dtype([('v1', [('v2', np.int16)])])`, 嵌套的结构类型
* `np.dtype([('v1', 'i4'), ('v2', 'f8', (2,3))])`, 字段`v2`是个数组, 形状是`2,3`
* `np.dtype({'v1': ('S25', 0), 'v2': (np.uint8, 25)})`, 字典的值是元组, `(type, offset)`, 在内存中的字节偏移
* `np.array([("Zhang", 32, 75.5), ("Li", 24, 65.2)])`, 创建结构类型数组

## 创建数组

* `np.array([e1, e2, ...])`, 由集合生成数组
  * `dtype=np.complex`, 指定数据类型
  * 若列表为多维列表, 则创建多维数组, 或通过`reshape(r, c)`
  * `order="F"`, 以`Fortran`格式在内存中排列元素
* `np.arange(start, end, step)`, 生成等差数组, 不包含最终值
  * `np.arange(end)`, 默认0开始, 1步长
* `np.linspace(start, end, num)`, 指定数组元素个数, 含终值
  * `endpoint=False`, 不含终值
* `np.logspace(s, e, num)`, 生成`10`的冥的等比数组, 含终值
  * `base=2`, 指定基数
* `np.zeros((rows, cols))`, `np.ones()`, 创建0矩阵, 1矩阵
* `np.eye(n)`, 创建n阶单位矩阵
* `np.empty((rows, cols))`, 为数组分配内存不初始化
* `np.full((rows, cols), v)`, 用值v填充数组
* `np.zeros_like(a)`, `np.ones_like()`, `np.empty_like()`, `np.full()`, 根据数组a的形状创建对应数组
* `np.frombuffer(buf)`, `np.fromstring(str)`, `np.fromfile(f)`, 从缓冲区, 字符串和文件数据中创建数组
  * `dtype=np.uint8`, 决定几字节数组作为一个数组元素 
* `np.fromfunction(func, (rows, cols))`, 将数组对应下标传递给函数, 返回的结果为数组元素

## 索引

索引元素:
* `a[5]`, `a[3:5]`, `a[:5]`, `a[:-1]`, `a[1:-1:2]`, `a[::-1]`, `a[5:1:-2]`, 切片索引, 不包含终值, 也可以通过索引直接修改元素, 创建视图
* `a[[1, 2, 3]]`, 整数列表索引, 拷贝元素
* `a[np.array([1,2,3])]`, 整数数组索引, 拷贝元素
  * 数组为多维时, 也返回多维数组
* `a[b>10]`, 布尔数组索引, `b>10`返回布尔数组, `>10`的元素为`True`, 其余为`False`, 为`True`的下标元素从a取出来
* `a[xxx, yyy, zzz...]`, 此为多维数组的索引, 其中`xxx`等可以为整数, 切片, 整数列表或元组, 整数数组, 布尔数组等. 只有整数和切片时创建的是视图. 当`xxx`的个数小于维度, 则剩余维度为`:`.

切片对象:
* `slice(None, None, 2)`创建切片, 即为`::2`, `None`表示省略
* `np.s_[::2, 2:]`, 返回切片对象元组, 注意`s_`是一个对象, 而非函数

## 常用函数

方法:
* `a.reshape((rows, cols))`, 改变数组a的开状, 视图
  * 行/列为`-1`时, 意思是自动调整
* `a.tostring()`, `a.tofile(fname)`, 将数组以二进制方式转换成字串, 或写入文件
* `a.view(new_type)`, 以新的数值类型查看同一段内存的二进制数据, 创建视图
* `a.repeat(n, axis=0)`, 在0轴上将数组a的元素重复n遍
* `a[None, :]`, `a[:, None]`, 在`None`位置创建长度为1的新轴

属性:
* `a.ndim`, 维度
* `a.dtype`, 数据类型
* `a.shape`, 数组形状
* `a.strides`, 元组, 每个轴上相邻两个元素的地址差, 即当某轴下标加1, 数据指针加的字节数
* `a.data`, 数据存储区的地址
* `a.flags`, 数据存储区域的属性

## ufunc通用函数

会作用于数组的每一个元素.

* `np.sin(a)`, `np.cos(a)`
* `np.max(a)`, `np.min(a)`
* `np.add(a, b)`, `np.subtract()`, `np.multiply()`, `np.divide()`, `np.power()`, `np.mode()`, 算术运算, `+ - * / ** %`
* `np.equal/not_equal/less/less_equal/greater/greater_equal`, 关系运算, `== != < <= > >=`
* `np.logical_and/or/xor/not`, 逻辑运算
* `np.bitwise_and`, `np.bitwise_or`, `np.bitwise_not`, `np.bitwise_xor`, 位运算, `& | ! ~`
* `np.all(a)`, 全为`True`时为`True`, `np.any(a)`, 有`True`时为`True`
* `np.frompyfunc(func, nin, nout)`, 将计算单元素的函数转换为通用函数, `nin`为入参数, `nout`为出参数
* `np.vectorize(func)`, 同上, `otypes=[np.int8]`指定返回值类型
* `np.ogrid[:5, :5]`, 创建广播运算用的数组, `ogrid`是对象
* `np.mgrid[:5, :5]`, 创建广播后的数组
  * `start:end:step`
  * `start:end:lenj`, 最后的`j`后缀不可省略, 表示创建指定长度的数组
* `np.ix_(x,y,z,...)`, 将多个一维数组转换成可广播计算的二维数组, 同`x[None, :]`, `y[:, None]`

通用函数作为对象, 也有自身的方法. 但只对两个输入, 一个输出的函数有效:
* `np.add.reduce(a)`, 加和
  * `axis=1`, 沿指定的1轴加和
* `np.add.accumulate(a)`, 返回相同形状的数组, 保存有中间结果
  * `axis=1`
* `np.add.reduceat(a, indices=[])`, 指定加和的起止位置
* `np.multiply.outer(a, b)`, 进入a和b的外积

当通用函数对两个数组计算时, 要求数组形状相同, 才能对相应元素计算. 若数组形状不相同, 要先进行广播处理成相同的形状:
* 以维度最高的数组为准, 不足的在形状元组前补1(注意, 是在前)
* 输出数组的形状是各输入数组在各个轴上的最大值
* 若输入数组某轴长为1或等于输出数组, 则可计算
* 若输入数组某轴长为1, 则计算时全部沿用此轴的第一组值
