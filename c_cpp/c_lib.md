# C语言标准库

## stdio.h

## string.h

因为C语言的字串与字符数组本质上相同的，因此此处的大部分函数其实都可以自己写一个临时实现。

### memchr/memcmp/memcpy/memset
`void *memchr(const void *str, int c, size_t n)`:
* 搜索字符串开始的n字节，返回c所在的位置。
* 虽然指针是void，字符是int，但它真的是字符串中搜索字符

`int memcmp(const void *str1, const void *str2, size_t n)`:
* 比较两个字串开始的n个字节
* 1大返回1；相等返回0；2大返回－1；

`void memcpy(void *str1, const void *str2, size_t n)`
* 复制字串str2的前n个字符到str1中
* 或str1与str2有重叠区，则使用`memmove`更安全

`void *memset(void *str, int c, size_t n)`
* 将str的前n字节设置为c

### strchr/strcmp(strncmp)/strcpy(strncpy)

`char *strchr(const char *str, int c)`:
* 注意此处没有前n个字节，结束标准是`'\0'`
* 相似版本`strrchr`进行反向搜索
* 相似版本`strpbrk`匹配字串2的任意一个字符

`int strcmp(const char *str1, const char *str2)`:
* 有个可指定前n个字节的版本strncmp
* 默认使用ASCII码比较，相似的`strcoll`根据环境变量`LC_COLLATE`决定字符的大小

`char *strcpy(char *dest, const char *src)`:
* 这里默认dest有足够大小，但可能发生溢出
* 有个可指定字节数的版本strncpy

`char *strcat(char *dest, const char *src)`:
* 字串连接，默认dest有足够大小，可能溢出
* 有可指定字节数的版本strncat

`size_t strlen(const char *str)`:
* 返回字串长度，不包含结束符0
* 返回类型默认为unsigned long, 输出时`%lu`

`size_t strspn(const char *str1, const char *str2)`:
* 字串1中首次出现非字串2字符的位置，如`abcd`中非`acd`字符为`b`，位置1
* 相似版本`strcspn`，字串1中首次出现字串2字符的位置，如`hello`中`ae`字符为`e`，位置1

`char *strstr(const char *str1, const char *str2)`:
* 前面都是进行字符匹配，无非是单字符还是字串字符
* 此处进行字串匹配，在字串1中匹配字串2

`char *strtok(char *str, const char *delim)`:
* 字串分解，分隔符由delim指定

```c
char *token = strtok(str1, delim);
while (token != NULL) {
    token = strtok(NULL, delim);  //除了第一个指定字串外，之后不指定
}
```

## stdlib.h

### 字符串转换

`atof`, `atoi`, `atol`将字串转换为浮点数，整数，长整数

`strtod`, `strtol`, `strtoul`将字串转换为浮点数，长整数，无符号长整数

`strto*`系列与`ato*`系列的区别是，当字串中包含无法转换的字符时，`strto*`会将非法字符出现的内存地址写入参数。

```c
char *str = "3.4 hello 5";
char *ptr;
double a = atof(str);  //=>3.4000
int b = atoi(str);  //=>3
double c = strtod(str, &ptr);  //=>3.4000, " hello 5"
```

### 内存分配

* `malloc`指定内存大小，单位字节；
* `calloc`指定元素数量，元素大小，初始化元素为0；
* `realloc`重新分配内存
* `free`释放内存

`void *realloc(void *ptr, size_t size)`：
* ptr为空，则新分配内存
* size为0，且ptr不空，相当于释放内存
* 若空间不足，新内存地址可能不同于旧内存地址
* 旧内存内容也会被重新复制到新内存
* 旧内存内容可能改变，因此之前所有指向旧内存的指针可能都无效

### 函数调用

关于`exit`, `abort`, `return`:
* exit执行后会做清理工作，如销毁所有static和global对象，清空所有缓冲区，关闭所有IO，并调用注册的终止函数。但不销毁局部对象；
* abort终止程序，不做清理工作
* return清理局部对象，若在main函数中调用，之后交由系统调用exit

* `atexit()`，程序正常终止时调用参数函数，`void (*)(void)`。无参，无返回
* `exit()`，接受退出码参数
* `abort()`，终止程序

### 系统

* `getenv`，获取环境变量值
* `system`，执行字串命令，错误返回－1，否则返回执行码

### 实用函数

* `void *bsearch(const void *key, const void *base, size_t nitems, size_t size, int (*compar)(const void *, const void *))`, 二分搜索，通过指针参数和自定义比较函数来增加通用性。注意这里使用`base, nitems, size`三元组来描述一个有序数组。这里的关键字也是指针类型。
* `void qsort(void *base, size_t nitems, size_t size, int (*compar)(const void *, const void *))`, 快速排序，可自定义比较函数
* `int abs(int x)`, labs针对长整数，`<math.h>`中的abs是double类型
* `div_t div(int numer, int denom)`，ldiv针对长整数，`div_t`是存储商和余数的结构体，`.quot, .rem`
* `int rand(void)`返回0-RAND_MAX之间的随机数
* `void srand(unsigned int seed)`设置随机数种子

```c
srand(time(NULL));
rand() % 50;
```


## math.h

## ctype.h

主要提供一个字符测试和映射函数。
* `isalnum`，字母或数字
* `isalpha`，字母
* `iscntrl`，控制字符
* `isdigit`，数字
* `isxdigit`，十六进制数字
* `isgraph`，可显示字符
* `isprint`，可打印字符
* `islower`，小写字母
* `isupper`，大写字母
* `ispunct`，标点
* `isspace`，空格
* `tolower`，转换为小写字母
* `toupper`，转换为大写字母

所有函数的参数都是`int`，返回值都是`int`。大写字母转换考虑了所有情况，且只处理字母。

## time.h

关于时间方面，`time.h`文件主要定义了以下3种相关的数据类型：
* `clock_t`，存储处理器时间的类型
* `time_t`，存储日历时间类型
* `struct tm`，保存时间和日期的结构

对于`clock_t`，考虑测量程序的运行时间：
```c
clock_t start, total;
start = clock();
...
total = clock() - start;
printf("run time: %lf\n", (double)total/CLOCKS_PER_SEC);
```

用长整型计算CPU时钟的嘀嗒数，再除以每秒嘀嗒数即可得秒数。

获取当前时间：
```c
time_t now;
now = time(NULL);  //time(&now);
printf("time is %s\n", ctime(&now));  //转为字串
```
`time_t`存储自`1970-01-01`起的总秒数，自然没有时区之类的差别。

可用`difftime(end, start)`来获取两个时间的差，返回秒数。考虑到`time_t`存储的本身即是秒数，因此大多数情况下等同于`(double)(end-start)`。当然这里估计考虑的是实现问题，因此仍然多使用`difftime`。

处理不同时区需要使用`struct tm`结构。
```c
struct tm {
   int tm_sec;         /* 秒，范围从 0 到 59      */
   int tm_min;         /* 分，范围从 0 到 59      */
   int tm_hour;        /* 小时，范围从 0 到 23     */
   int tm_mday;        /* 一月中的第几天，范围从 1 到 31    */
   int tm_mon;         /* 月，范围从 0 到 11      */
   int tm_year;        /* 自 1900 年起的年数      */
   int tm_wday;        /* 一周中的第几天，范围从 0 到 6 */
   int tm_yday;        /* 一年中的第几天，范围从 0 到 365   */
   int tm_isdst;       /* 夏令时               */
};
```

由`time_t`转换为`struct tm`：
```c
struct tm *local, *gmt;
local = localtime(&now);  //本地时区
gmt = gmtime(&now);  //格林尼治标准时间
```
一个有趣的地方在于，两个函数会在相同的内存地址处构建结构体，也就是说，实际上`local, gmt`是相同的地址。相反的可以使用`mktime`逆转换。

由`struct tm`转换为`char *`:
```c
asctime(&local);  //或使用strftime()可以控制显示的格式
```

## errno.h

当程序出错时，由系统调用重置`errno`整型变量，再调用`strerror(errno)`来获取错误信息。

```c
extern int errno;
FILE *fp;
if ((fp=fopen("data.txt", "r")) == NULL)
    fprintf(stderr, "Error opening file: %s\n", strerror(errno));
```

## limits.h

定义一些整型的范围的宏。
* `INT_MAX`, `INT_MIN`, `UINT_MAX`
* `LONG_MAX`, `LONG_MIN`, `ULONG_MAX`
* `SHRT_MAX`, `SHRT_MIN`, `USHRT_MAX`
* `CHAR_MIN`, `CHAR_MAX`, `UCHAR_MAX`

## stdarg.h

定义函数的变参。`va_start, va_arg, va_end`都是宏。

```c
int sum(int num, ...) {
   int val = 0;
   va_list ap;

   va_start(ap, num);  //num是...的前一个参数名
   for(int i = 0; i < num; i++)
   {
      val += va_arg(ap, int);  //下一个类型int的参数
   }
   va_end(ap);
 
   return val;
}
```
因为`va_arg`无法判断当前返回的是否是最后一个参数，则参数数量作为显示参数来传递一般是必需的。