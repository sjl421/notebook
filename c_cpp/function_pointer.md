# 函数指针

现声明一函数指针: `float (* func)(float, float)`, 其为返回`float`类型, 参数为两个`float`类型, 变量名为`func`. 其最本质的原型是`float (*)(float, float)`.

如果函数指针的参数也是函数指针, 则形式为`float (* func)(float, int (*)(int, int))`, 即其一个参数为返回`int`类型同时参数为两个`int`类型的函数指针.

如果函数指针的返回类型也是函数指针, 则形式为`char (* (* func)(float, int (*)(int, int)))(char, char)`, 好吧, 现在有点复杂了. 它表示返回一个函数指针(返回类型为`char`, 参数类型为`(char, char)`), 其本身也是函数指针(注意这里已经指定返回类型为参数类型为`(float, int (*)(int, int))`), 同时其一个参数还是函数指针(返回类型为`int`, 参数类型为`(int, int)`).


声明一个函数指针: `float (*fp)(float, float)`, 注意这是变量声明, 声明的只是一个变量, 变量类型为`float (*)(float, float)`, 它必须指向一个函数, 函数有这样的形式: `float xxx(float, float)`. 其中`xxx`是函数名. 

声明一个带函数指针的函数指针: `float (*fp)(float (*)(float, float), float, float)`, 这同样是变量声明, 变量类型为`float (*)(float (*)(float, float), float, float)`, 指向有这样形式的函数: `float xxx(float (*)(float, float), float, float)`.

声明一个带函数指针并返回函数指针的函数指针: `float (* (*fp)(float (*)(float, float), float, float))(float, float)`, 其声明的变量类型为`float (* (*)(float (*)(float, float), float, float))(float, float)`, 其指向这样的函数: `float (* xxx(float (*)(float, float), float, float))(float, float)`.

当然上述如此复杂, 以至于又难写又难读.

定义函数指针类型: `typedef float (*fp)(float, float);`, 则`fp`表示函数指针类型, 声明一个变量`fp a_fp;`, 好了, 现在`a_fp`是个指向具有`float xxx(float, float)`形式的函数.

定义带函数指针的函数指针类型: `typedef float (*fpp)(fp a_fp, float, float)`, 则`fpp`表示一个类型, 声明一个此类型的变量`fpp a_fpp`. 注意, 其参数中有个`fp a_fp`, 表示其为函数指针参数.

定义带函数指针并返回函数指针的函数指针: `typedef fp (*fppp)(fp a_fp, float, float)`, 则`fppp`表示一个类型, 声明一个此类型的变量`fppp a_fppp`. 注意, 其参数和返回值都是`fp`.

不得不承认, 好好好好好好好好好好复杂.