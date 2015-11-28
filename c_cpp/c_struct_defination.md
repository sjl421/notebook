# C语言结构体定义

在C语言中使用:

``` c
struct test {
    int x, y;
};
```

以上定义结构体`struct test`, 进行类型声明时, 不能省略`struct`, 即不能只使用`test`作为类型.

而使用:

``` c
typedef struct _test {
    int x,y;
} test, *pt;
```

以上定义结构体`test`, 即`test`本身即是类型. 而`_test`可有可无. 若有`*pt`, 即定义`pt`为此结构体的指针类型. 这里已经是类型, 无需再加上`struct`关键字.