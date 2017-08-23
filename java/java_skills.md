# Java技巧

1, `Class.forName`从字串返回类, 本质是加载类; `A a = (A)Class.forName("pkg.A").newInstance()`加载类并实例化. 与`new`不同, `new`是关键字, 是创建类. `newInstance`只能调用无参构造函数.

`Class.forName("pkg.A").getMethod("method", argsClass).invoke()`, 动态调用方法

2, `import java.util.Arrays`, 有一个执行二分查找的函数, 即`Arrays.binarySearch(ary, key)`. 若找到则返回匹配的位置, 找不到返回`-插入点-1`, 即负数.

3, 类通过实现`java.io.Serializable`接口具有序列化能力. 可序列化的类的所有子类仍然可以序列化. 序列化接口没有任何方法或域, 只是用来在语义上标志其可以序列化. 那些要求在序列化和反序列化过程中进行特殊处理的类必须实现以下方法, 保证完全相同的签名.

```java
private void writeObject(java.io.ObjectOutputStream out) throws IOException
private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException;
private void readObjectNoData() throws ObjectStreamException;
```

4, `Thread.isInterrupted`返回中断标记, 而`interrupted`则还会清除中断标记.

## 正则

```java
Pattern pat = Pattern.compile(reg_str);
Matcher m = pat.matcher(str);
m.matches();  // 是否匹配整个字串
m.lookingAt();  // 是否匹配字串的开头
m.find();  // 是否能找到一个匹配, 多次调用会自动找到下一个匹配, 并更新start/end/group返回的内容
m.start([group_num]);  // 匹配的开始索引, 组号是捕获组的索引, 从1开始, 0表示所有
m.end([group_num]);  // 匹配的结束索引
m.group([group_num]);  // 匹配到的内容
```

## 读文件

```java
BufferedReader br = new BufferedReader(new FileReader("hello.txt"));
String input = br.readLine();
while (input != null) {
  statment;
  input = br.readLine();
}
```

## 进制转换

```java
String a = "0xAE"
Integer.decode(a);  //=> 174
```