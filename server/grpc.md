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
* `-I`, 从何处查找`xx.proto`文件
* `--python_out`, 在何处生成消息类文件
* `--grpc_python_out`, 在何处生成服务器/客户端类文件

```proto
message Person {  // 消息Person
  required string name = 1;  // 必需, 字段name, 顺序1, 类型字串
  required int32 id = 2;
  optional string email = 3;  // 可选

  enum PhoneType {  // 嵌套枚举类型PhoneType
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {  // 嵌套消息类型PhoneNumber
    required string number = 1;
    optional PhoneType type = 2 [default = HOME];  // 可选, 默认值
  }

  repeated PhoneNumber phone = 4;  // 第4个字段
}
```

* 数字标签在1-15编码1字节, 16-2047编码2字节


### 键的类型

* `string`
* `int32`
* `bool`

