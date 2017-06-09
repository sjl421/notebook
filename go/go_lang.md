# Go

## 数字

* `int`, `int8`, `int16`, `int32`, `int64`
* `uint`, `uint8`, `uint16`, `uint32`, `uint64`
* `float32`, `float64`

## 字串

## 布尔

`true`, `false`

## 数组

## 切片

* `append`, 为切片添加元素, 自动处理容量

## 映射

```go
dict := make(map[string]int)  // 创建
dict := map[int][]string{}  // 创建, 键`int`, 值`[]string`
dict := map[string]string{"Red": "#da1337", "Orange": "#e95a22"}  // 创建并初始化
var colors map[string][string]  // nil映射
```

* `make`
* `delete`, 删除键

## 通道

```go
unbuffered := make(chan int)
buffered := make(chan int, 10)  // 带缓冲区
buffered <- "Gopher"  // 发送
value := <-buffered   // 读取
close(buffered)       // 关闭
```

## 其他

注: `*`的是包的函数, `-`的是类型的方法

`time`:

* `Hour`, `Minute`, `Second`, `Millisecond`, `Microsecond`, `Nanosecond`, 整数, 类型为`Duration`
* `Duration`, 基类型时`int64`
* `Sleep()`, 休眠
* `Now()`, 当前时间
* `After(duration)`, 等待一段时间并发送当前时间到返回的通道`<-chan Time`, 通常用于定时
* ~ `Year()`, `Day()`, `Hour()`, `Minute()`
* ~ `Unix()`, `UnixNano()`返回时间戳的整数

`runtime`:

* `GOMAXPROCS(num)`, 分配`num`个逻辑处理器给调度器
* `NumCPU()`, 获取平台物理处理器个数
* `Gosched()`, 强制当前`goroutine`退出线程, 并放回队列

`sync.WaitGroup`: 计数信号量

* ~ `Add(num)`, 信号量加2
* ~ `Done()`, 信号量减1
* ~ `Wait()`, 等待信号量为0

`sync/atomic`, 提供一组原子函数, `xx`通常为32/64

* `AddIntxx`
* `LoadIntxx`, `LoadUIntxx`, 安全读取整数值
* `StoreIntxx`, `StoreUIntxx`, 安全修改整数值

`os`:

* `Args`, 命令行参数, 0索引为程序名
* `Exit(num)`, 退出程序
* `Interrupt`, 中断信号

`os/signal`:

* `Notify(chan, sig)`, 向通道发送信号
* `Stop(chan)`, 关闭通道

`fmt`:

* `Print`, `Printf`, `Println`
* `Fprint`, `Fprintf`, `Fprintln`
* `Sprint`, `Sprintf`, `Sprintln`, 返回格式化后的字串, 并不输出
* `Scan`, `Scanf`, `Scanln`
* `Fscan`, `Fscanf`, `Fscanln`
* `Sscan`, `Sscanf`, `Sscanln`
* `Errorf`

`log`:

* `Print`, `Printf`, `Println`
* `Fatal`, `Fatalf`, `Fatalln`
* `Panic`, `Panicf`, `Panicln`
* `SetOutput`, `SetFlags`, `SetPrefix`

`math/rand`, 非负伪随机数:

* `Seed(num)`, 设定随机数种子
* `Intn(num)`, `Int31n(num)`, `Int63n(num)`, 返回`[0,num)`间的随机数
* `Int()`, `Int31()`, `Int63()`, 返回相应位数的非负随机数
* `Float32()`, `Float64()`, 返回`[0,1.0)`间的伪随机数
* `Uint32()`, `Uint64()`, 返回相应位数的非负伪随机数

`errors`, 错误

* `New(str)`, 创建错误
