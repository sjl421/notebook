# python

## 数值交换

```python
x, y = 2, 3
x, y = y, x
```

## 时间测量

small code snippets

```python
from timeit import Timer
# help(Timer)
Timer('x,y=y,x', 'x=2;y=3').timeit()
# 执行setup一次，执行main number次
Timer('main statement', 'setup statement').timeit([number=1000000])
# repeat执行timeit的次数
Timer('main', 'setup'),repeat([repeat=3, [number=1000000]])
```

block code

```python
from time import time
t = time() #初始时间戳
...
t = time() - t #终止时间戳
```

## 汇编python代码

dis

```python
from dis import dis
# dis('code')
dis('x,y=y,x')
# dis('function')
def swap():
    x,y = 2,3
    x,y = y,x
dis(swap)
# dis('class')
class Hello:
    def __init__(self,name):
        self.name = name
    def greet(self):
        print("hello, %s" % self.name)
dis(Hello)
# dis('methods')
dis(Hello.greet)
```

## 惰性计算

在变成过程中，对于or条件表达式应该将值为真可能性较高的变量写在or的前面，而and则应该推后。

yield

```python
# 计算斐波纳契数列
def fib():
    a, b = 0, 1
    while True:
        yield a
        a, b = b, a+b
```
