# C语言技巧

## skill 1: 快速输入测试

对于需要大量输入的程序，每次重复输入比较费时。

```c
#ifdef DEBUG
FILE *fp;
if ((fp=freopen("data.txt", "r", stdin)) == NULL) {
    fprintf(stderr, "%s\n", "wrong open file");
    exit(1);
}
fclose(fp);
#endif
```

在编译时，加入编译选项`-DDEBUG`来开启此处代码的编译。

## skill 2: 变量随地声明

`c89`不支持变量的随时随地声明，往往一个程序的前面全是变量声明，不利于程序写和读。鉴于此，都建议使用`--std=c99`的编译选项。

```
for (int i=0; i<N; i++)
    printf("%d ", ary[i]);
```

* 注： 此处有趣在于，在`VC6.0`中，变量`i`的作用域在`for`外。则下一次打印时，`for (i=0; i<N; i++)`。

## skill 3: 输入

一般情况下，输入一个数组可一个矩阵，即使知道元素的值不会超过255，也不推荐使用`char`声明数组类型。

```
int ary[N];
for (int i=0; i<N; i++)
    scanf("%d", &ary[i]);
```

* 如果此处是`char ary[N];`，在`scanf`中也不能替换`%d`为`%c`。而最好使用`(int *)&ary[i]`强制转换以消除`warning`。
* 在`scanf`中，使用`%c`会读入字符，而所有输入都是字符，造成无法区分数字，而需要手动进行辨别。虽然，`char`本质上也是以整数存储的。
* 但`scanf`有上厉害的`%s`，用来读入以空格分开的字串。相对的`gets`会读入可行内容。

```c
char ary[300];  //"hello"
scanf("%s", ary); //5
gets(ary); //5
fgets(ary, 300, stdin); //6
```

* 虽然使用`fget`可确保不会发生数组越界的错误，但似乎会多读入一个`'\n'`。导致计算长度时多1.

## skill 4: 输出

输出指针地址：
```c
char *c;
printf("%p\n", c);
```


