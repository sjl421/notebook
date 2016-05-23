# Rust

rust最大的特点是尽可能在编译时检测出更多的错误. 换句话说, 它通过更严格的语法规则
和规范, 来让一切可能产生错误的代码在编译时被检测出来, 以此来实现速度, 安全, 并发
的目标.

```rust
fn main() {
  println!("hello, world");
}
```

## variable binding, 变量绑定

```rust
let x: i32 = 3;  //type annotation, 类型注释
let x = 3;  //type inference, 类型推断
let (x, y, z) = (1, 2, 3);  //pattern, 模式匹配
let mut x = 3;  //x is mutable, 可变量
```

`rust`的变量在使用前(注意并非声明时)必须有初始值。变量作用域为`{}`内，且变量可以覆盖.

```rust
let x = 3;
x = 4;  //wrong, x is immutable, 不可变量不能重新赋值
let x = 4;  //right, shadowing the x of upper, 覆盖变量
```

## primitive types, 基础类型

`bool`: `true` or `false`;

`char`: 单unicode向量值，4字节，单引号包含，如`'沃'`

`[i/u][8/16/32/64]`: 整型, 有符号和无符号, 1/2/3/4字节

`isize/usize`: 可变 字节大小的整型

`f32/f64`: 浮点数

`array`: 相同类型元素，固定大小

```rust
let a = [1, 2, 3, 4];
let b = [0; 20];  //20个元素并初始化为0
```

`slice`: 数据结构的内存视图，创建slice并不复制数据或者申请内存

```rust
let b = &a[..];  //all elements
let c = &a[1..4];  //1, 2, 3
```

`tuple`: 固定大小的有序列表，元素可以不同类型

```rust
let x = (1, "hello");
let x: (i32, &str) = (1, "hello");  //type annotation
let x = (0,);  //single element tuple, 单元素元组
let x = (0);  //x is 0, not tuple
x.0;  //access tuple first element, 使用'.'
```

## function, 函数

```rust
fn funcname(arg1: type, ...) -> returntype {
	...
}
let x: fn(arg1:type, ...)->returntype = funcname;  //function pointer, 函数指针
```

返回类型可以省略. 函数指针的类型注释可以省略.

## comments

* 行注释: `//`之后全是注释
* 块注释: `///`, 支持markdown语法
* 块注释: `//!`, 注释库,模块或函数
* 使用`rustdoc`生成HTML文档

## macro

rust使用宏来提供常用函数的简易用法. 方法上是方法名加后缀`!`.
* `println!`, 输出函数
* `assert!`, 断言宏, 参数false, 则输出错误信息,令程序崩溃
* `panic!`, 输出信息并令程序崩溃

## branch

```rust
if x==5 {
	...
} else if {
	...
} else {
	...
}
```

`if`是表达式,会返回结果. 但若将`if`作表达式使用, `else`分支必须有.且`{}`不可省略.

## loop

`loop`, 无限循环.

`while`, 条件循环, 

注意: `rust`中的条件表达式似乎都不加`()`,猜测是`()`配给了`tuple`. `rust`是强类似语言, 不还在C中的非0即真, 或Ruby中的非nil即真的条件判断. 即条件表达式的值必须是`bool`类型.

`for`, 更类似foreach, 不像C指定次数, 易错.

```rust
for x in 0..10 {  // rust没有...操作符
	println!("{} ", x);
}
for (i, x) in (5..10).enumerate() {
	println!("{} {}", i, x);  // i is loop count
}
```

个人觉得,此处的`.enumerate()`类似`Ruby`中的`.each_with_index`.

`break`中断最内层循环, `continue`省略之后操作进入最内层的下一次循环.

```rust
'outer: for x in 0..10 {
	'inner: for y in 0..10 {
		if x%2==0 { continue 'outer; }  //带标记的循环控制
		if y%2==0 { continue 'inner; }
		println!("x: {}, y: {}", x, y);
	}
}
```

## vector

```rust
let x = vec![1, 2, 3];  //type is Vec<i32>
x.push(5);
```

## Owner system

`Ownership`, rust确保对于每个资源有且只有一个变量绑定.

```rust
let v = vec![1, 2, 3];
let v2 = v;  //after can't access [1, 2, 3] via v
```

对于向量, 在堆中分配内存, 并用栈中变量`v`来保存指针, 若进行再次绑定, 会产生对同一块内存有多个指针, 进而产生数据冒险. 因此, rust不允许多次绑定, 此即`Owner`的含义. 但对另一些非指针本质的变量, 如`let x = 1; let y = x`, 对于基础类型, rust通常使用复制`Copy`. 从rust的观点看, 当再次绑定时, 称为`move`, 已经移动的东西自然不存在.

而事实上, 给函数传递参数时也可能发生`move`.

`reference`, 因为`move`对函数传递影响很大, 这里的引用可粗略理解为C的指针.

```rust
fn print_vec(v: Vec<i32>) {}  
//when call print_vec, the arg will be moved
fn print_vec(v: &Vec<i32>) {}
//when call print_vec, the arg will be borrowed
```

引用默认不可变, 但可使用`&mut T`可变引用.

```rust
let mut x = 5;
{
	let y = &mut x;  //y refer to x, this can't change
	*y += 1;  //change x to 6
}  //actually, '{}' can't be ignored
println!("{} ", x);
```

最后输出函数的调用借用了`x`, 而`y`也借用了`x`, 且是可变引用.如果没有`{}`, 则在同一作用域, 既有可变又有不可变引用, 可能发生数据冒险, 因此编译不过.

借用的规则:
* 任何借用者的作用域必须小于拥有者的
* 可以有一或多个不可变引用, 或一个可变引用, 但不能同时有

感觉借用和移动有点恶心, 你必须明确什么情况是借用和移动.

```rust
let mut v = vec![1, 2, 3];
for i in v {}  //move, after can't access vector via v
for in in &v {}  //reference
v.push(4);  //mutable reference
```

因为借用不能比拥有更长, 因此先定义引用会出问题.

```rust
let mut x = 5;
let y = &mut x;  //y is an immutable binding to a mutable reference.
```
`lifetimes`, 
