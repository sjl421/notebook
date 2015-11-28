# make命令和makefile文件

`make`命令:
- `-k`: 发现错误时仍然继续执行
- `-n`: 输出执行的操作步骤, 并不真正执行
- `-f <fn>`: 指令作为`makefile`的文件名
- `-p`: 打印默认规则

从最简单的`makefile`文件讲起.

1, 现在有源文件`hello.c`:
``` c
#include <stdio.h>
void main() {
    printf("hello");
}
```
2, `makefile`文件可写为:
```
hello: hello.o
    gcc -o hello hello.o
hello.o: hello.c
    gcc -c hello.c
.PHONY: clean
clean:
    rm hello hello.o
```

如上, `makefile`是对编译的顺序和依赖的描述.

`.PHONY`声明`clean`为伪目标, 则`make`命令不会检查当前目录是否存在名为`clean`的文件, 以保证`clean`的规则总被执行.

如果没指定一个`all`目标, 则`make`命令将只创建它在文件`makefile`中找到的第一个目标. 在此处即`hello`.

`makefile`以`#`为注释.

加上宏定义:
```
CC = gcc
INCLUDE = include
INSTDIR = /usr/local
LIB = lib
CFLAGS = -std=c99 -Wall -g

all: hello
hello: hello.o
    $(CC) -o hello hello.o
hello.o: hello.c
    $(CC) -I$(INCLUDE) -L$(LIB) $(CFLAGS) -c hello.c
clean:
    -rm hello hello.o
install: hello
    @if [ -d $(INSTDIR) ]; then \
        cp hello $(INSTDIR); \
        chmod a+x $(INSTDIR)/hello; \
        echo "Installed in $(INSTDIR)"; \
    else \
        echo "Sorry, $(INSTDIR) does not exist"; \
    fi
```

`make`命令在执行规则时会调用一个`shell`, 并且会针对每个规则使用一个新的`shell`.

预定义的宏:
- `$?`: 当前目标所依赖的文件列表中比当前目标文件还要新的文件
- `$^`: 当前目标所依赖的所有文件
- `$@`: 当前目标的名字
- `$(@D)`和`$(@F)`: 当前目标的目录名和文件名
- `$<`: 当前依赖文件的名字
- `$(<D)`和`$(<F)`: 当前依赖文件的目录名和文件名
- `$*`: 不包括后缀名的当前依赖文件的名字

特殊符号:
- `-`: 忽略所有错误
- `@`: 不显示正在执行的命令
- `*`: 通配符, 还有`?`, `[]`等
- `%`: 模式匹配, 如`%.o:%.c`

调用`shell`变量, 如`$HOME`, 要写成`$$HOME`, 因为其会对`$`进行转义.

内置规则:
``` make
OUTPUT_OPTION = -o $@
COMPILE.c = $(CC) $(CFLAGS) $(CPPFLAGS) $(TARGET_ARCH) -c
COMPILE.cc = $(CC) $(CXXFLAGS) $(CPPFLAGS) $(TARGET_ARCH) -c
LINK.c = $(CC) $(CFLAGS) $(CPPFLAGS) $(LDFLAGS) $(TARGET_ARCH)
LINK.cc = $(CC) $(CXXFLAGS) $(CPPFLAGS) $(LDFLAGS) $(TARGET_ARCH)
LINK.o = $(CC) $(LDFLAGS) $(TARGET_ARCH)
%: %.o
    $(LINK.o) $^ $(LOADLIBES) $(LDLIBS) -o $@
%.o: %.c
    $(COMPILE.c) $(OUTPUT_OPTION) $<
%.o: %.cc
    $(COMPILE.cc) $(OUTPUT_OPTION) $<
```
## 