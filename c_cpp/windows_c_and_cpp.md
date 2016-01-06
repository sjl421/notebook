# windows下的C/C++语言

在`visual c++ 2008`中,当选择编辑一个32位Win32控制台应用程序时.初始状态下系统自带函数:

``` c
int _tmain(int argc, _TCHAR* argv[]) {
    return 0;
}
```

上述`Win32`控制台应用程序的入口程序是用来存放机器的一个环境变量的，如：机器名，系统信息等.

`_TCHAR`类型是宽字符型字符串，和我们一般常用的字符串不同，它是32位或者更 高的操作系统中所使用的类型.

`_tmain`:

1. `Main`是所有c或c++的程序执行的起点，`_tmain`是`main`为了支持`unicode`所使用的`main`的别名 .`_tmain()`不过是`unicode`版本的的`main()` .
2. `_tmain`需要一个返回值,而`main`默认为`0(int)`.
3. `_tmain`的定义在`<tchar.h>`可以找到,如`#define _tmain main`，所以要加
`#include <tchar.h>`才能用。`_tmain()`是个宏,如果是`UNICODE`则他是`wmain()`否则他是`main()`.
4. `_tmain`这个符号多见于`VC++`创建的控制台工程中，这个是为了保证移植`unicode`而加入的 （一般`_t、_T、T()`这些东西都是宏都和`unicode`有关系），对于使用非`unicode`字符集的工程来说，实际上和`main`没有差别（其实就算是使用`unicode`字符集也未必有多大的差别）。
5. 因此`_tmain compile`后仍为`main`，所以都可以执行.