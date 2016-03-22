# C语言的数组和指针

我们经常需要动态申请内存，比如要读入一组数，但数的个数通过输入才知道。C语言的数组在声明时需要指定大小。在这里使用`malloc`函数和`calloc`函数。

```c
void *malloc(size_t size);
void *calloc(size_t nitems, size_t size);  //将申请的内存初始化为0
```

## 动态申请一维数组：

```c
int N;
scanf("%d", &N);
int *ary = (int *)malloc(sizeof(int)*N);  
free(ary);
```

* 强制类型转换在clang和gcc中可以省略，但vc6.0中必须有

## 动态申请二维数组：

```c
int row, col;
scanf("%d %d", &row, &col);
int (*ary)[col] = (int (*)[col])malloc(sizeof(int)*row*col);  //for clang and gcc
free(ary);
int **ary = (int **)malloc(sizeof(int *)*row);
for (int i=0; i<row; i++)
    ary[i] = (int *)malloc(sizeof(int)*col);  //for all
for (int i=0; i<row; i++)
    free(ary[i]);
free(ary);
```

C语言的特殊处在于数组本质上就是指针。
* 第一种，声明ary是指向数组`[col]`的指针，即ary+1加的是一个数组。
* 第二种，声明ary是指针的指针，即ary指向一个每个元素都是一个指针的数组。
* 两种方式有本质的区别，第一种的内存是连续的，第二种虽然可以模拟二维数组的行为，但只有行内元素连续，行间元素不连接。
* 这即意味者，可以用`*(ary+i*row+j)`获取第一种的值，而对第二种无效。但`ary[i][j]`都有效。

## 动态申请字符串数组：

```c
int N;
scanf("%d", &N);
char **ary = malloc(sizeof(char *)*N);
for (int i=0; i<N; i++) {
    char temp[20];
    scanf("%s", temp);  //%s, 遇空格或制表符或回车符，结束
    ary[i] = malloc(sizeof(char)*(strlen(temp)+1));
    strcpy(ary[i], temp);
}  //不读入字串即不知道字串长度，不知长度则无法分配内存，temp好像是必须的
for (int i=0; i<N; i++)
    free(ary[i]);
free(ary);
```

输入：
```
3
wellcome to beijing!
```

## 初始化

```c
char str[] = "hello";  //strlen(str)  5; sizeof(str)  6;
char str[10] = "hello";  //5, 10
char str[5] = "hello";  //xx, 5, 没有元素被设为0，strlen不能返回正确值
char *str = "hello";  //str指向字串内存地址，5, 8, 因此指针大小由平台决定，且不能通过str改变字串值，即默认只读
int ary[5] = {0};  //5个元素，初始为0
int ary[5] = {1, 2, 3, 4};  //前4个元素𧚥为相应值，第5个不知
int ary[] = {1, 2, 3, 4};  //声明4元素数组，相应初始化
```

