# Go Cheat Sheet

## Go in a Nutshell

* Imperative language, 命令式语言
* Statically typed, 静态类型
* Syntax tokens similar to C (but less parentheses and no semicolons) and the structure to Oberon-2, 语法类C(但少括号和分号), 结构类Oberon-2
* Compiles to native code (no JVM), 编译到本地代码
* No classes, but structs with methods, 没有类, 而是带方法的结构类型
* Interfaces, 接口
* No implementation inheritance. There's [type embedding](http://golang.org/doc/effective%5Fgo.html#embedding), though. 无实现继承, 虽然有类型嵌入
* Functions are first class citizens, 函数为第一公民
* Functions can return multiple values, 函数可返回多值
* Has closures, 有闭包
* Pointers, but not pointer arithmetic, 有指针, 但没有指针运算
* Built-in concurrency primitives: Goroutines and Channels, 内建并发原语: Goroutines和Channels

## Basic Syntax

### Hello World

File `hello.go`:

```go
package main

import "fmt"

func main() {
    fmt.Println("Hello Go")
}
```

`$ go run hello.go`

### Operators, 操作符

#### Arithmetic, 算术操作符

|Operator|Description|
|--------|-----------|
|`+`|addition|
|`-`|subtraction|
|`*`|multiplication|
|`/`|quotient|
|`%`|remainder|
|`&`|bitwise and|
|`|`|bitwise or|
|`^`|bitwise xor|
|`&^`|bit clear (and not)|
|`<<`|left shift|
|`>>`|right shift|

#### Comparison, 关系运算符

|Operator|Description|
|--------|-----------|
|`==`|equal|
|`!=`|not equal|
|`<`|less than|
|`<=`|less than or equal|
|`>`|greater than|
|`>=`|greater than or equal|

#### Logical, 逻辑运算符

|Operator|Description|
|--------|-----------|
|`&&`|logical and|
|`||`|logical or|
|`!`|logical not|

#### Other

|Operator|Description|
|--------|-----------|
|`&`|address of / create pointer|
|`*`|dereference pointer|
|`<-`|send / receive operator (see 'Channels' below)|

### Declarations, 声明

类型在标识符后

```go
var foo int // 声明不初始化, 但其实会初始化为此类型的零值, 对整数为0, 对字串为""
var foo int = 42 // 声明并初始化
var foo, bar int = 42, 1302 // 声明并初始化多个变量
var foo = 42 // 省略类型, 使用推断
foo := 42 // 只在函数体中, 省略var关键字, 类型推断
const constant = "This is a constant"  // 常量
```

### Functions, 函数

```go
func functionName() {} // 无参函数
func functionName(param1 string, param2 int) {}  // 带参函数
func functionName(param1, param2 int) {}  // 多参且同类型函数
func functionName() int {  // 返回类型
    return 42
}
func returnMulti() (int, string) {  // 返回多值
    return 42, "foobar"
}
var x, str = returnMulti()  // 以函数返回值声明并初始化变量
func returnMulti2() (n int, s string) {  // 返回命名结果
    n = 42
    s = "foobar"
    return  // n and s will be returned
}
var x, str = returnMulti2()
```

#### Functions As Values And Closures, 作为值和闭包的函数

```go
func main() {
    add := func(a, b int) int {  // 将函数赋值给变量
        return a + b
    }
    fmt.Println(add(3, 4))
}

// 闭包, 词法作用域: 函数可获取其定义时的作用域的值
func scope() func() int{  // 以函数为返回值
    outer_var := 2
    foo := func() int { return outer_var}  // 定义函数时, outer_var在作用域中
    return foo
}

// 闭包: 不能改变outer变量, 但可以重新定义它
func outer() (func() int, int) {
    outer_var := 2
    inner := func() int {
        outer_var += 99 // 看似在修改outer_var, 但其实在重新定义, 即外层的outer_var不会改变
        return outer_var // => 101
    }
    return inner, outer_var // => 101, 2
}
```

#### Variadic Functions, 可变参的函数

```go
func main() {
  fmt.Println(adder(1, 2, 3))   // 6
  fmt.Println(adder(9, 9))  // 18

  nums := []int{10, 20, 30}
  fmt.Println(adder(nums...))  // 60
}

// 在最后一个参数的类型前使用...来指示它可以接受0或多个这样的参数
func adder(args ...int) int {
  total := 0
  for _, v := range args { // 迭代参数
    total += v
  }
  return total
}
```

### Built-in Types, 内建类型

```go
bool  // 布尔, true/false

string  // 字符串

int  int8  int16  int32  int64
uint uint8 uint16 uint32 uint64 uintptr

byte // uint8的别名

rune // alias for int32 ~= a character (Unicode code point) - very Viking

float32 float64  // 浮点数

complex64 complex128  // 复数
```

### Type Conversions, 类型转换

```go
var i int = 42
var f float64 = float64(i)  // typeName(value)
var u uint = uint(f)

i := 42
f := float64(i)
u := uint(f)
```

### Packages, 包

* Package declaration at top of every source file, 包声明在每个源文件的顶部
* Executables are in package `main`, 执行入口为main包的main函数
* Convention: package name == last name of import path (import path `math/rand` => package `rand`), 约定: 包名为导入路径的最后名称
* Upper case identifier: exported (visible from other packages), 首字母大写的标识符: 导出, 即在其他包可见
* Lower case identifier: private (not visible from other packages), 首字母小写的标识符: 私有, 即在其他包不可见

### Control structures, 控制结构

#### If

```go
func main() {
  // Basic one
  if x > 0 {
    return x
  } else {
    return -x
  }

  if a := b + c; a < 42 {  // 在条件前放置一条语句
    return a
  } else {
    return a - 42
  }

  // Type assertion inside if
  var val interface{}  // interface{} 在go中表示任意类型
  val = "foo"
  if str, ok := val.(string); ok {  // 类型验证, 若val为字串, 则OK为true
    fmt.Println(str)
  }
}
```

#### Loops

```go
    // 只有for, 没有while和until
    for i := 1; i < 10; i++ {
    }
    for ; i < 10;  { // while
    }
    for i < 10  { // 当只有一个条件时, 可省略";"
    }
    for { // 无限循环, 相当于while (true)
    }
```

#### Switch

```go
    switch operatingSystem {
    case "darwin":
        fmt.Println("Mac OS Hipster")  // 默认自带break
    case "linux":
        fmt.Println("Linux Geek")
    default:  // 默认
        fmt.Println("Other")
    }

    switch os := runtime.GOOS; os {  // 在value前放置一条赋值语句
    case "darwin": ...
    }

    number := 42
    switch {
        case number < 42:  // 在case中作比较
            fmt.Println("Smaller")
        case number == 42:
            fmt.Println("Equal")
        case number > 42:
            fmt.Println("Greater")
    }
```

### Arrays, Slices, Ranges, 数组 切片 范围

#### Arrays

```go
var a [10]int // 声明整型数组, 元素数为10, 元素数是数组类型的一部分
a[3] = 42     // 设置元素
i := a[3]     // 读取元素

var a = [2]int{1, 2}  // 声明并初始化
a := [2]int{1, 2} // 简写
a := [...]int{1, 2} // 编译器会根据初始化元素的数量来推断数组的长度, ...是不可少的
```

#### Slices

```go
var a []int   // 声明切片, 类似于数组, 但不指定长度
var a = []int {1, 2, 3, 4}   // 声明并初始化切片, 切片的内部实现是数组
a := []int{1, 2, 3, 4}  // 简写
chars := []string{0:"a", 2:"c", 1: "b"}  // 指定索引的初始化, 未指定的初始化为0值

var b = a[lo:hi]  // 创建切片, 从lo到hi-1
var b = a[1:4]    // slice from index 1 to 3
var b = a[:3]    // 不指定起始索引, 默认0
var b = a[3:]    // 不指定结束索引, 默认为len(a)
a =  append(a,17,3)  // 附加元素到a
c := append(a,b...)  // 将b的所有元素添加到a中

a = make([]byte, 5, 5)  // 第一个参数是类型, 第二个参数是长度, 第三个参数是容量
a = make([]byte, 5)  // 省略容量, 等于长度

x := [3]string{"Лайка", "Белка", "Стрелка"}
s := x[:] // 从数组创建切片
```

#### Operations on Arrays and Slices, 数组 切片上的操作

`len(a)` 返回数组或切片的长度, 内建函数而非方法

```go
for i, e := range a {  // 迭代数组或切片, i是索引, e是元素
}

for _, e := range a {  // 用_省略索引
}

for i := range a {  // 只用索引
}

for range time.Tick(time.Second) {  // 省略索引和变量
    // do it once a sec
}
```

### Maps, 映射

```go
var m map[string]int  // nil映射, 键为string, 值为int, 不能存储键值对
m = make(map[string]int)  // 将空映射赋值给m
m := make(map[string]int)
m := map[string]int{}  // 空映射
m["key"] = 42  // 存储
fmt.Println(m["key"])  // 读

delete(m, "key")  // 删除键

elem, ok := m["key"] // 测试键是否存在

// map literal
var m = map[string]Vertex{  // 映射字面量
    "Bell Labs": {40.68433, -74.39967},
    "Google":    {37.42202, -122.08408},
}
```

### Structs

没有类, 只有结构, 结构可以有方法. 结构是类型, 是域的集合

```go
type Vertex struct {  // 声明
    X, Y int  // 两个导出域 X, Y
}

var v = Vertex{1, 2}  // 创建值, 依序赋值
var v = Vertex{X: 1, Y: 2} // 创建值, 按键赋值
var v = []Vertex{{1,2},{5,2},{5,5}} // 结构切片

v.X = 4  // 为域赋值

func (v Vertex) Abs() float64 {  // 在func关键字和方法名之间, 方法接收者(值接收者和指针接收者)
    return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

v.Abs()  // 方法调用, 因为是值接收者, 传入函数的是v的副本

func (v *Vertex) add(n float64) {  // 指针接收者, 则传入函数的是v的指针, 可改变v值
    v.X += n
    v.Y += n
}
```

匿名结构: 比使用`map[string]interface{}`更高效和安全

```go
point := struct {  // 省略结构名
  X, Y int
}{1, 2}  // 直接初始化
```

### Pointers, 指针

```go
p := Vertex{1, 2}
q := &p            // 对p取地址, 则q是指向Vertex{1,2}的指针
r := &Vertex{1, 2} // r也是指针

// 指向Vertex的指针, 其类型为*Vertex

var s *Vertex = new(Vertex) // new返回指向新的结构实例的指针
```

### Interfaces, 接口

```go
type Awesomizer interface {  // 接口声明
    Awesomize() string  // 无参函数, 返回string
}

type Foo struct {}  // 类型不需要声明其实现了接口

func (foo Foo) Awesomize() string {  // 类型隐式地满足一个接口, 如果它们实现了所有必要的方法
    return "Awesome!"
}
```

### Embedding, 嵌入

没有子类的概念, 但有接口和结构嵌入

```go
type ReadWriter interface {  // ReadWriter的实现必须同时满足Reader和Writer接口
    Reader
    Writer
}

type Server struct {  // 将Logger的所有方法提升到Server类型中
    Host string
    Port int
    *log.Logger
}

server := &Server{"localhost", 80, log.New(os.Stdout, "INFO: ", log.Ldate|log.Ltime|log.Lshortfile)}

server.Println(...)  // 相当于server.Logger.Println(...)

var logger *log.Logger = server.Logger  // 嵌入类型的域名即其类型名
```

### Errors

没有异常处理, 可能产生错误的函数声明一个额外的类型为`Error`的返回值即可.

```go
type error interface {  // 要实现Error方法
    Error() string
}
```

```go
func doStuff() (int, error) {  // 返回error的函数
}

func main() {
    result, error := doStuff()
    if (error != nil) {  // 最典型的错误处理
        // handle error
    } else {
        // all is good, use result
    }
}
```

## Concurrency, 并发

### Goroutines

`Goroutine`是轻量级的线程, 由`Go`管理而非`OS`. `go f(a,b)`启动一个新的`goroutine`, 运行函数`f`.

```go
func doStuff(s string) {  // 简单定义一个函数
}

func main() {
    go doStuff("foobar")  // 在goroutine运行命名函数

    go func (x int) {  // 在goroutine启动匿名函数
        // function body goes here
    }(42)
}
```

### Channels, 通道

```go
ch := make(chan int) // 整数类型的通道, 即只能传递整数
ch <- 42             // 发送值到通道
v := <-ch            // 从通道接收一个值

无缓冲通道阻塞, 当通道没有值时, 读操作被阻塞; 如果值被写入通道但未被读取, 则写操作被阻塞

// 创建有缓冲的通道. 若已写但未读的字节数小于缓冲大小, 则写操作不会阻塞.
ch := make(chan int, 100)

close(ch) // 关闭通道, 只有发送端能关闭

// 从通道读并测试它是否已关闭
v, ok := <-ch  // 如果ok是false, 则通道已关闭

for i := range ch {  // 读通道直到它关闭
    fmt.Println(i)
}

// 多个通道操作的select, 若某个通道不阻塞, 则执行相应的case
func doStuff(channelOut, channelIn chan int) {
    select {
    case channelOut <- 42:
        fmt.Println("We could write to channelOut!")
    case x := <- channelIn:
        fmt.Println("We could read from channelIn")
    case <-time.After(time.Second * 1):
        fmt.Println("timeout")
    }
}
```

#### Channel Axioms

* 向`nil`通道的发送操作将永远被阻塞

  ```go
  var c chan string  // nil通道
  c <- "Hello, World!"
  // fatal error: all goroutines are asleep - deadlock!
  ```
* 从`nil`通道的接收操作将永远被阻塞

  ```go
  var c chan string
  fmt.Println(<-c)
  // fatal error: all goroutines are asleep - deadlock!
  ```
* 向已关闭通道的发送操作引发`panic`错误

  ```go
  var c = make(chan string, 1)
  c <- "Hello, World!"
  close(c)
  c <- "Hello, Panic!"
  // panic: send on closed channel
  ```
* 从已关闭通道的接收操作立即得到0值

  ```go
  var c = make(chan int, 2)
  c <- 1
  c <- 2
  close(c)
  for i := 0; i < 3; i++ {
      fmt.Printf("%d ", <-c)
  }
  // 1 2 0
  ```

### Printing, 打印

```go
fmt.Println("Hello, 你好, नमस्ते, Привет, ᎣᏏᏲ") // 最基础的打印, 换行
p := struct { X, Y int }{ 17, 2 }
fmt.Println( "My point:", p, "x coord=", p.X ) // print structs, ints, etc
s := fmt.Sprintln( "My point:", p, "x coord=", p.X ) // 返回字符串

fmt.Printf("%d hex:%x bin:%b fp:%f sci:%e",17,17,17,17.0,17.0) // c-ish format
s2 := fmt.Sprintf( "%d %f", 17, 17.0 ) // formatted print to string variable

hellomsg := `
 "Hello" in Chinese is 你好 ('Ni Hao')
 "Hello" in Hindi is नमस्ते ('Namaste')
` //多行字串的字面量, 使用``
```

## Snippets

### HTTP Server

```go
package main

import (
    "fmt"
    "net/http"
)

// define a type for the response
type Hello struct{}

// 在Hello类型上实现ServerHTTP方法, 此方法在http.Handler接口中定义
func (h Hello) ServeHTTP(w http.ResponseWriter, r *http.Request) {
    fmt.Fprint(w, "Hello!")
}

func main() {
    var h Hello
    http.ListenAndServe("localhost:4000", h)
}
```