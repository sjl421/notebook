# 魔术方法

* `__init__(self, args)`, 构造方法
* `__del__(self)`, 在对象被删除前作额外的清理工作, 如套接字对象或文件对象

## 比较

* `__cmp__(self, other)`, 小于返回负数, 等于返回0, 大于返回正数
* `__eq__(self, other)`, 定义了等号的行为, ==
* `__ne__(self, other)`, 定义了不等号的行为, !=
* `__lt__(self, other)`, 定义了小于号的行为， <
* `__gt__(self, other)`, 定义了大于等于号的行为， >=

## 一元操作符

* `__pos__(self)`, 实现正号的特性(比如 +some_object)
* `__neg__(self)`, 实现负号的特性(比如 -some_object)
* `__abs__(self)`, 实现内置 abs() 函数的特性
* `__invert__(self)`, 实现 ~ 符号的特性

## 二元操作符

* `__add__(self, other)`, 实现加法 +
* `__sub__(self, other)`, 实现减法 -
* `__mul__(self, other)`, 实现乘法 *
* `__floordiv__(self, other)`, 实现 // 符号实现的整数除法
* `__div__(self, other)`, 实现 / 符号实现的除法
* `__truediv__(self, other)`, 实现真除法
* `__mod__(self, other)`, 实现取模算法 %
* `__divmod___(self, other)`, 实现内置 divmod() 算法
* `__pow__(self, other)`, 实现使用 ** 的指数运算
* `__lshift__(self, other)`, 实现使用 << 的按位左移动
* `__rshift__(self, other)`, 实现使用 >> 的按位左移动
* `__and__(self, other)`, 实现使用 & 的按位与
* `__or__(self, other)`, 实现使用 | 的按位或
* `__xor__(self, other)`, 实现使用 ^ 的按位异或

## 反运算

与上对应, 反运算, 即运算的左右数相反, 大多数情况下反运算结果不变, 即满足交换律:

* `__radd__(self, other)`, 实现反加
* `__rsub__(self, other)`, 实现反减
* `__rmul__(self, other)`, 实现反乘
* `__rfloordiv__(self, other)`, 实现 // 符号的反除
* `__rdiv__(self, other)`, 实现 / 符号的反除
* `__rtruediv__(self, other)`, 实现反真除，只有当 from __future__ import division 的时候会起作用
* `__rmod__(self, other)`, 实现 % 符号的反取模运算
* `__rdivmod__(self, other)`, 当 divmod(other, self) 被调用时，实现内置 divmod() 的反运算
* `__rpow__(self, other)`, 实现 ** 符号的反运算
* `__rlshift__(self, other)`, 实现 << 符号的反左位移
* `__rrshift__(self, other)`, 实现 >> 符号的反右位移
* `__rand__(self, other)`, 实现 & 符号的反与运算
* `__ror__(self, other)`, 实现 | 符号的反或运算
* `__xor__(self, other)`, 实现 ^ 符号的反异或运

## 增量赋值

即类似`x += 1`.

* `__iadd__(self, other)`, 实现赋值加法
* `__isub__(self, other)`, 实现赋值减法
* `__imul__(self, other)`, 实现赋值乘法
* `__ifloordiv__(self, other)`, 实现 //= 的赋值地板除
* `__idiv__(self, other)`, 实现符号 /= 的赋值除
* `__itruediv__(self, other)`, 实现赋值真除,只有使用 from __future__ import division 的时候才能使用
* `__imod__(self, other)`, 实现符号 %= 的赋值取模
* `__ipow__(self, other)`, 实现符号 **= 的赋值幂运算
* `__ilshift__(self, other)`, 实现符号 <<= 的赋值位左移
* `__irshift__(self, other)`, 实现符号 >>= 的赋值位右移
* `__iand__(self, other)`, 实现符号 &= 的赋值位与
* `__ior__(self, other)`, 实现符号 |= 的赋值位或
* `__ixor__(self, other)`, 实现符号 |= 的赋值位异

## 类型转换

* `__int__(self)`, 实现整形的强制转换
* `__long__(self)`, 实现长整形的强制转换
* `__float__(self)`, 实现浮点型的强制转换
* `__complex__(self)`, 实现复数的强制转换
* `__oct__(self)`, 实现八进制的强制转换
* `__hex__(self)`, 实现二进制的强制转换
* `__index__(self)`, 当对象是被应用在切片表达式中时，实现整形强制转换，如果你定义了一个可能在切片时用到的定制的数值型,你应该定义 __index__ (详见PEP357)
* `__trunc__(self)`, 当使用 math.trunc(self) 的时候被调用。 __trunc__ 应该返回数值被截取成整形(通常为长整形)的值
* `__coerce__(self, other)`, 实现混合模式算数。如果类型转换不可能的话，那么 __coerce__ 将会返回 None ,否则他将对 self 和 other 返回一个长度为2的tuple，两个为相同的类型

## 表现

* `__str__(self)`, 定义当 str() 调用的时候的返回值
* `__repr__(self)`, 定义 repr() 被调用的时候的返回值
* `__unicode__(self)`, 定义当 unicode() 调用的时候的返回值

* str() 和 repr() 的主要区别在于 repr() 返回的是机器可读的输出，而 str() 返回的是人类可读的
* unicode() 和 str() 很相似，但是返回的是unicode字符串。

* `__hash__(self)`, 定义当 hash() 调用的时候的返回值，它返回一个整形，用来在字典中进行快速比较
* `__nonzero__(self)`, 定义当 bool() 调用的时候的返回值。本方法应该返回True或者False，取决于你想让它返回的值