# AKKA

并发, 分布式, 容错, 事件驱动

`Actor`模型, 以通信的概念来思考代码, 有状态的实体通过明确的消息发送来彼此通信:

* 通过异步消息而非方法调用来通信
* 自己管理自己的状态
* 响应消息时:
  * 创建子`Actor`
  * 向其它`Actor`发消息
  * 停止子`Actor`或自己

`Actor`封装了状态和执行, `OOP`则只封装了状态. `Actor`间的通信只能通过传递消息来进行, 消息即`Actor`的公有`API`.

虽然消息可以是任何类型, 但`case class`和`case object`可以用于模式匹配.

* 将`Actor`关联的消息定义在其伴随对象中
* 在伴随对象中定义`props`方法来描述如何构造`Actor`

在`Akka`中, `Actor`的位置并不重要, 重要的是`ActorRef`引用. 根据需要, 运行时能通过改变`Actor`的位置或整个应用的拓扑来优化系统. 这允许失败管理的`let it crash`模式, 系统能通过销毁错误的`Actor`并重启健康的来治愈自身.

`Actor`的工厂函数`actorOf`更像一个`Actor`容器, 并管理其生命周期.

`Actor`的`MailBox`, 即其消息队列, 保证发自同一个`Actor`的消息是有序的, 不同`Actor`的消息则可能交错.

## API

```scala
system.actorOf()  // 创建actor并返回引用
context.actorOf()  // 创建子actor并返回引用
```