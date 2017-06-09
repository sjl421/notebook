# smali语言

## Types类型

基本类型:

- `V`: void
- `Z`: boolean
- `B`: byte
- `S`: short
- `C`: char
- `I`: int
- `J`: long, 8字节
- `F`: float
- `D`: double, 8字节

引用类型:

- `Lpackage/name/ObjectName;`, 其中`L`表示这是引用类型, 如`Ljava/lang/String;`表示`java.lang.String`

数组:

- `[I`: 表示基本类型的一维整数数组, 即`int[]`, 而`[[I`则表示基本类型的二维整数数组, 即`int[][]`.
- `[Ljava/lang/String`: 表示引用类型的一维字符串数组, 即`String[]`, 多维数组同上.

## Methods方法

`Lpackage/name/ObjectName;->MethodName(III)Z`, 分别是:

- 调用对象`Lpackage/name/ObjectName;`例如`java.lang.String`
- 调用方法`->MethodName()`例如`.method()`
- 方法参数`III`即`int, int, int`
- 返回类型`Z`即`boolean`

有时方法调用会比较复杂, 如
`method(I[[IILjava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;`相当于
`String method(int, int[][], int, String, Object[])`

## Fields属性

`Lpackage/name/ObjectName;->FieldName:Ljava/lang/String;`分别有属性对象, 属性名, 属性类型. 相当于`String Obj.FieldName`

## 寄存器

有两种等价的方法指定一个方法调用中使用的寄存器数.

- `.register n`表示一共使用了`n`个寄存器数
- `.locals n`表示除参数寄存器外一共使用的寄存器数

方法调用中, 通过寄存器传递参数. 且参数总是存储在最后使用的寄存器. 如一共使用了5个寄存器`v0-v4`, 有3个参数`a1,a2,a3`, 则`v2`存储`a1`, `v3`存储`a2`, `v4`存储`a3`.

为了方便, 参数寄存器有时也使用`pn`命名, 且`p0`表示第1个参数, `p1`表示第2个参数, 以此类推.

注意, 对于非静态方法, 或者说是实例方法, 其调用对象总是隐含的第1参数, 也就是总存储在`p0`中.

所有的寄存器都是`4字节`的, 因此对`8字节`的参数则使用2个寄存器表示. 如非静态方法`LMyObject;->MyMethod(IJZ)V`的调用, 因为有`J`即`double`参数, 其参数传递如下:

- `p0 - this`
- `p1 - I(int)`
- `p2,p3 - J(double)`
- `p4 - Z(boolean)`
