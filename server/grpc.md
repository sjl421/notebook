# GRPC

## python

安装: `pip install grpc grpcio-tools`

`proto`文件:

```proto
// The greeter service definition.
service Greeter {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;  // 1表示顺序
}
```

定义一个服务`Greeter`和两个消息`HelloRequest/HelloReply`, 服务内定义方法, 方法的参数和返回值使用定义的消息. 消息通常可理解为结构体, 键值对. 

通常在`Python`中, 服务和消息都会被自动生成类, 消息类会自动为每个键生成`name()/set_name()`方法以及序列化到`raw`字节和从`raw`解析. 服务会被继承, 并在子类中实现声明的方法.

生成代码: `python -m -Ixx_path --python_out=xx_path --grpc_python_out=xx_path xx.proto`
* `-I`, 从何处查找导入的`xx.proto`文件
* `--python_out`, 在何处生成消息类文件
* `--grpc_python_out`, 在何处生成服务器/客户端类文件

生成的`python`消息类中, 除了域的设置和获取外, 还有些别的方法:
* `IsInitialized()`, 检查是否所有必需的域已被设置
* `CopyFrom(other_msg)`, 从其他消息克隆一个消息
* `Clear()`, 清空设置的所有值
* `SerializeToString`, 序列化到字串
* `ParseFromString(data)`, 解析字串到对象

四种类型的服务方法:

```proto
rpc SayHello(HelloRequest) returns (HelloResponse){}  // Unary RPCs
rpc LotsOfReplies(HelloRequest) returns (stream HelloResponse){}  // Server streaming RPCs
rpc LotsOfGreetings(stream HelloRequest) returns (HelloResponse) {}  // Client streaming RPCs
rpc BidiHello(stream HelloRequest) returns (stream HelloResponse){}  // Bidirectional streaming RPCs
```

## proto buffer

```proto
import "myproject/other_protos.proto"    // 导入其它proto文件定义的消息类型
message Person {                     // 消息Person
  required string name = 1;          // 必需, 字段name, 顺序1, 类型字串
  required int32 id = 2;
  optional string email = 3;         // 可选

  enum PhoneType {                   // 嵌套枚举类型PhoneType
    option allow_alias = true        // 允许别名
    MOBILE = 0;                      // 0表示定义的顺序, 也表示值, 以0开始
    HOME = 1;
    WORK = 2;
    COMPANY = 2;                     // 注意2已经定义过了, 此处是WORK的别名
  }

  message PhoneNumber {              // 嵌套消息类型PhoneNumber
    required string number = 1;
    optional PhoneType type = 2 [default = HOME];  // 可选, 默认值
  }

  repeated PhoneNumber phone = 4;    // 第4个字段, 可被重复任意次, 数组
  reserved 2, 15, 9 to 11;           // 声明此字段受保护, 不可改用
  reserved "foo", "bar";
}
```

* 数字标签在1-15编码1字节, 16-2047编码2字节

### 键的类型

浮点型: 默认为0
* `double`
* `float`

整型: 默认为0
* `int32`, 编码负值低效
* `int64`
* `uint32`, 无符号
* `uint64`
* `sint32`, 有符号, 编码负值更高效
* `sint64`, 以上全为变长编码
* `fixed32`, 无符号, 值大于`2^28`时, 比`uint32`高效
* `fixed64`, 同, 大于`2^56`
* `sfixed32`, 有符号
* `sfixed64`, 以上为定长编码

布尔型: 默认为`false`
* `bool`

字串型: 默认为空串
* `string`

字节型: 默认为空字节
* `bytes`

枚举型: 默认为定义的第一个值

枚举型可在消息内声明, 也可在消息外声明; 前者使用`MessageType.EnumType`引用, 后者默认所有消息可使用.

`oneof`类型: 类似`C`的`union`, 即多个域共享内存. 用特殊方法决断设置的是哪个域.

```proto
message SampleMessage {
  oneof test_oneof {             // name和sub_message共享内存
    string name = 4;             // 不能是repeated类型
    SubMessage sub_message = 9;
  }
}
```

`map`类型: 键可以是任何除了浮点型/字节型外的所有纯量类型, 值可以是除`map`外的所有类型. 无序.

```proto
map<key_type, value_type> map_field = N;  // 不能为repeated
message MapFieldEntry {                   // 与上等价
  key_type key = 1;
  value_type value = 2;
}
repeated MapFieldEntry map_field = N;
```

包: 可以在`.proto`文件中声明包`package x.y.z;`, 则此文件中所有的类型, 被外部引用时都有`x.y.z`的命名空间.

### 导入

两种方式, 直接导入和公开导入

```proto
// new.proto
// All definitions are moved here

// old.proto
// This is the proto that all clients are importing.
import public "new.proto";
import "other.proto";

// client.proto
import "old.proto";  // 在此处直接导入old.proto, 可使用old.proto定义的类型
                     // old.proto公开导入了new.proto, 也可使用new.proto的类型
```


