# Scala

1, `case class`定义中, 编译器自动添加了不少特性和函数. 

`case class P(a1: String)(a2: String)`这种定义方式, 自动添加的特性只作用于第一个
参数列表, 对第二个参数列表无效.

2, `@transient`注解表示序列化时, 不包含此属性字段

3, `class A { self => ... }`此处, `self`为`this`的别名.

4, `()`方法本质上是`apply`方法的别名, 但通常在伴生对象中, `apply`用于创建对象,
但也可以在`class`中定义, 这样可以在实例化对象中, 调用`()`方法.

`class A { def apply() ... }`, 可以使用`(new A)()`方法.
