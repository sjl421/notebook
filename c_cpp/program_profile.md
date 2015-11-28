# C/C++程序性能分析

## gprof

关键是加入编译选项`-pg`, 然后执行程序生成`gmon.out`, 再用`gprof`工具分析输出.

* 1, `gcc -pg test.c -o test`
* 2, `./test`
* 3, `gprof [-b] test gmon.out`, 选项`[-b]`表示简化输出.

## google perftools
