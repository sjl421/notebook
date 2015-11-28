# Dalvik opcodes

虽然看似有近256个指令, 但绝大多数指令都是相似的, 其总是对不同类型, 不同位数有变化. 因此我们可以将最本质的指令抽取出来, 他们的每一个都代表一个指令群.

`move`, `return`, `const`, `array`, `instance`, `goto`, `switch`, `cmp`, `if`, `put`, `get`, `invoke`, `add/sub/mul/div/rem`等.



`00`: nop

---

`01`: `move vx, vy`
`02`: `move/from16 vx, vy`: 表示源寄存器vy是用16位即2字节表示, 意即其可以为前64K寄存器. vx用8位表示, 意即其可以为前256寄存器.
`05`: `move-wide/from16 vx, vy`: 其中`wide`表示移动的值是`long/double`类型.
`07`: `move-object vx, vy`: 表示移动的是引用类型的引用
`08`: `move-object/from16 vx, vy`: 
`0a`: `move-result vx`: 表示将上个方法调用的返回值给vx
`0b`: `move-result-wide vx`: 移动`long/double`的值, 由于`vx`是32位, 因此需要两个寄存器, 此处为`vx`和`vx+1`.
`0c`: `move-result-object vx`
`0d`: `move-exception vx`: 将方法调用抛出的异常类型引用给vx

---

`0e`: `return-void`, 返回空值
`0f`: `return vx`, 返回vx
`10`: `return-wide vx`
`11`: `return-object vx`

---

`12`: `const/4 vx, lit4`, 4位的常量, 0-15
`13`: `const/16 vx, lit16`, 16位的常量, 0-65535
`14`: `const vx, lit32`
`15`: `const/high16 vx, lit16`, 将16位常量存储到v0的高16位中, 用于初始化float
`16`: `const-wide/16 vx, lit16`, 此处的`wide`表示`long/double`, 因此需要把16位常量扩展成64位后再存储到`vx`和`vx+1`
`17`: `const-wide/32 vx, lit32`, 扩展32位为64位后存储
`18`: `const-wide vx, lit64`
`19`: `const-wide/high16 vx, lit16`, 初始化double
`1a`: `const-string vx, string_id`, 常量字符串
`1c`: `const-class vx, type_id`, 常量类

---

`1d`: `monitor-enter vx`, 获取vx引用的对象的monitor
`1e`: `monitor-exit`

---

`1f`: `check-cast vx, type_id`, 检查`vx`引用的对象能否转换成`type_id`指向的类, 不能则抛出`ClassCastException`异常, 可以则继续执行
`20`: `instance-fo vx, vy, type_id`, 检查`vy`是不是`type_id`的对象, 是则`vx`非0.
`22`: `new-instance vx, type`, 实例化type类, 并将引用存储在`vx`, 注意此处的`type`是`type table`的编号

---

`21`: `array-length vx, vy`, 将`vy`引用的数组的长度存储在`vx`
`23`: `new-array vx, vy, type_id`, 生成数组, vy存储数组个数, vx存储生成的引用.
`24`: `filled-new-array {parameters}, type_id`, 生成数组并用parameters填充, 通过`move-result-object vx`得到引用
`25`: `filled-new-array-range {vx..vy}, type_id`
`26`: `fill-array-data vx, array_data_offset`, 用静态数据填充vx引用的数组, 数据位置由当前指令地址加偏移量

---

`27`: `throw vx`, 抛出异常

---

`28`: `goto target`, 无条件跳转
`29`: `goto/16 target`
`2a`: `goto/32 target`

---

`2b`: `packed-switch vx, table`, 紧密表跳转
`2c`: `sparse-switch vx, table`, 稀疏表跳转

此处, 稀疏表的开始地址在table中, 是个偏移量, 其中存储跳转的case和跳转的addr. 但紧密表则是每个case相近或等差, 则table只存储跳转的偏移量.

---

`2d`: `cmpl-float vx, vy, vz`, 比较vy和vz, 并将结果存储在vx
`2e`: `cmpg-float vx, vy, vz`
`2f`: `cmpl-double vx, vy, vz`
`30`: `cmpg-double vx, vy, vz`
`31`: `cmp-long vx, vy, vz`

---

`32`: `if-eq vx, vy, target`, 如果vx等于vy, 则跳转
`33`: `if-ne vx, vy, target`, 不等于
`34`: `if-lt vx, vy, target`, 小于
`35`: `if-ge vx, vy, target`, 大于等于
`36`: `if-gt vx, vy, target`, 大于
`37`: `if-le vx, vy, target`, 小于等于
`38`: `if-eqz vx, target`, 等于0
`39`: `if-nez vx, target`, 不等于0
`3a`: `if-ltz vx, target`, 小于0
`3b`: `if-gez vx, target`, 大于等于0
`3c`: `if-gtz vx, target`, 大于0
`3d`: `if-lez vx, target`, 小于等于0

---

数组存取:
`44`: `aget vx, vy, vz`, 取整数数组vy中索引为vz的元素到vx
`45`: `aget-wide vx, vy, vz`, `long/double`到`vx, vx+1`
`46`: `aget-object vx, vy, vz`, 引用对象
`47`: `aget-boolean vx, vy, vz`, 布尔
`48`: `aget-byte vx, vy, vz`, 字节
`49`: `aget-char vx, vy, vz`, 字符
`4a`: `aget-short vx, vy, vz`, 短整型
`4b`: `aput vx, vy, vz`, 存储vx到整数数组vy的索引vz
`4c`: `aput-wide vx, vy, vz`
`4d`: `aput-object vx, vy, vz`
`4e`: `aput-boolean vx, vy, vz`
`4f`: `aput-byte vx, vy, vz`
`50`: `aput-char vx, vy, vz`
`51`: `aput-short vx, vy, vz`

对象属性存取:
`52`: `iget vx, vy, field_id`, 将vy引用的对象的field_id属性值存储到vx
`53`: `iget-wide vx, vy, field_id`
`54`: `iget-object vx, vy, field_id`
`55`: `iget-boolean vx, vy, field_id`
`56`: `iget-byte vx, vy, field_id`
`57`: `iget-char vx, vy, field_id`
`58`: `iget-short vx, vy, field_id`
`59`: `iput vx, vy, field_id`
`5a`: `iput-wide vx, vy, field_id`
`5b`: `iput-object vx, vy, field_id`
`5c`: `iput-boolean vx, vy, field_id`
`5d`: `iput-byte vx, vy, field_id`
`5e`: `iput-char vx, vy, field_id`
`5f`: `iput-short vx, vy, field_id`

类属性存取:(即静态属性)
`60`: `sget vx, field_id`, 读field_id标识的属性值到vx
`61`: `sget-wide`
`62`: `sget-object`
`63`: `sget-boolean`
`64`: `sget-byte`
`65`: `sget-char`
`66`: `sget-short`
`67`: `sput`
`68`: `sput-wide`
`69`: `sput-object`
`6a`: `sput-boolean`
`6b`: `sput-byte`
`6c`: `sput-char`
`6d`: `sput-short`

---

`6e`: `invoke-virtual {parameters}, methodtocall`, 调用实例方法, 第一参数存储this指针
`6f`: `invoke-super {parameters}, methodtocall`, 未知
`70`: `invoke-direct {parameters}, methodtocall`, 构造函数, main函数以及私有方法
`71`: `invoke-static {parameters}, methodtocall`, 调用静态公共方法
`72`: `invoke-interface {parameters}, methodtocall`, 调用接口方法
`74`: `invoke-virtual/range {vx..vy}, methodtocall`
`75`: `invoke-super/range {vx..vy}, methodtocall`
`76`: `invoke-direct/range`
`77`: `invoke-static/range`
`78`: `invoke-interface/range`

---

取负:
`7b`: `neg-int vx, vy`, vx=-vy
`7d`: `neg-long vx, vy`, vx, vx+1=-(vy, vy+1)
`7f`: `neg-float vx, vy`
`80`: `neg-double vx, vy`

类型转换:
`81`: `int-to-long vx, vy`, 将vy转换成长整型, 存储在vx,vx+1
`82`: `int-to-float vx, vy`
`83`: `int-to-double vx, vy`
`84`: `long-to-int vx, vy`
`85`: `long-to-float vx, vy`
`86`: `long-to-double vx, vy`
`87`: `float-to-int`
`88`: `float-to-long`
`89`: `float-to-double`
`8a`: `double-to-int`
`8b`: `double-to-long`
`8c`: `double-to-float`
`8d`: `int-to-byte`
`8e`: `int-to-char`
`8f`: `int-to-short`

算术运算:
`90`: `add-int vx, vy, vz`, vx=vy+vz
`91`: `sub-int vx, vy, vz`, vx=vy-vz
`92`: `mul-int vx, vy, vz`, vx=vy*vz
`93`: `div-int vx, vy, vz`, vx=vy/vz
`94`: `rem-int vx, vy, vz`, vx=vy%vz

逻辑运算:
`95`: `and-int vx, vy, vz`, vx=vy AND vz
`96`: `or-int vx, vy, vz`, vx=vy OR vz
`97`: `xor-int vx, vy, vz`, vx=vy XOR vz

移位运算:
`98`: `shl-int vx, vy, vz`, 将vy左移vz位并存储在vx
`99`: `shr-int vx, vy, vz`
`9a`: `ushr-int vx, vy, vz`, 无符号数右移

以上所有运算, 将`-int`可更换成`-long`, `-float`, `-double`以对相应类型进行运算.

而`add-int/2addr vx, vy`表示使用两个寄存器, 即将vx+vy的值再存储到vx中. 其针对`-long`, `-float`, `-double`也有相应运算.

如果有立即数, 则可以使用`add-int/lit16 vx, vy, lit16`, 即将vy+lit16的值存储在vx中. 而`add-int/lit8 vx, vy, lit8`则针对8位立即数. 此运算只对整型有效.