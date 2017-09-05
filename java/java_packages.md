# Java

## java import packages

``` java
import java.util.Scanner;             // 容器
import java.util.Arrays;              // 数组
import java.util.ArrayList;           // 数组列表,采用类型参数的泛形类, T为类，不能为基本类型
import java.util.Date;                // 时间点,
import java.util.Calendar;
import java.util.GregorianCalendar;   // 日期表示, 继承java.util.Calendar
import java.util.Locale;              // 语系设置
import java.util.Random;
import java.math.BigInteger;          // 继承java.lang.Number
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.io.Console;
import java.io.PrintWriter;           // 继承java.io.Writer
import java.nio.file.Paths;
import java.io.FileNotFoundException;
// 继承java.io.IOException -> java.lang.Exception -> java.lang.Throwable
```

## java.net.URI

```java
val uri = new URI("http://lzp@192.168.0.1:3000/hello.html")
uri.getHost();  //=> 192.168.0.1
uri.getPort();  //=> 3000
uri.getScheme();  //=> http
uri.getPath();  //=> /hello.html
uri.getUserInfo();  //=> lzp
```

## java.util.Scanner

``` java
Scanner in = new Scanner(System.in); //new Scanner()调用Scanner的构造器。
Scanner in = new Scanner(Paths.get("file.txt")); //文件输入
in.nextLine(); in.next(); in.nextInt(); in.nextDouble();
```

## 格式输出

``` java
//%\[参数索引$]\[标志]\[宽度][.精度＋转换字符]|[.t+转换字符]
System.out.printf("%2$,8.3f, %1$s\n", "enali", 333.2345);
System.out.printf("%2$tB", new Data());
String message = String.format("hello %s", "world");
```

## String

``` java
String s = " Hello, world "; s + "lzp";
s.startsWith("Hello"); s.endsWith("world");
s.equals("Hello, world"); s.equalsIgnoreCase("hello, world");
s.indexOf("l"); s.lastIndexOf("l");
s.length();
s.replace("llo", "LLO"); s.substring(2, 8);                   // "ello, w"
s.toLowerCase(); s.toUpperCase();                             // "HELLO, WORLD"
s.trim();                                                     // "hello, world"
```

## StringBuilder

``` java
StringBuilder builder = new StringBuilder();
builder.append("hello"); builder.append("world");
builder.length();                                 // 10
builder.insert(2, "AA");                          // "heAAoworld"
builder.delete(2, 4);                             // "heoworld"
String completeString = builder.toString();
```

## java.io.BufferedReader ->java.io.Reader

读文件:

``` java
BufferedReader in = new BufferedReader(new FileReader("foo.in"));
in.readLine();in.read();in.skip(10)
in.close(); in.ready();
```

写文件:

```java
BufferedWriter bw = new BufferedWriter(new FileWriter("foo.out"));
bw.write("hello, world");
bw.close();
```

## Math

``` java
Math.sin(x); //cos/tan/tan2/sinh/cosh/tanh, exp/log/log10/log1p, PI/E, random ->(0,1)
```

## java.math.BigInteger, java.math.BigDecimal -> java.lang.Number

``` java
BigInteger a = BigInteger.valueOf(123);
a = a.add(b); a.subtract(b); a.multiply(b); a.divide(b); a.mod(b); a.compareTo(b);
BigDecimal a = BigDecimal.valueOf(1223 [, 2]); ->1223/10^2
a.add(b); a.subtract(b); a.multiply(b); a.compareTo(b);
a.divide(b)= a.divide(BigDecimal.valueOf(2), Bigdecimal.ROUND\_HALF\_UP);
```

## 数组

``` java
int a= new int[100];          // malloc in heap;
String[] a = new String[3];
int[][] a = new int[12][23];
a.length;
Arrays.sort(a);
b = Arrays.copyOf(a, length);
```

## java.util.Arrays

``` java
Arrays.toString(a);
Arrays.deepToString(a);
Arrays.copyOf(a,10);
Arrays.copyOf(a,2,8);
Arrays.sort(a);
Arrays.binarySearch(a,value);  // 在数组a中进行二分查找, a应该是有序的
Arrays.binarySearch(a, start, end, value);
Arrays.fill(a, value);  // 以指定值填充数组
Arrays.equals(a,b);  // 比较数组是否相等
Arrays.hashCode();
```

## java.util.Date, java.util.GregorianCalendar -> java.util.Calendar

``` java
Locale.setDafault(Locale.ITALY);
Date a = new Date(); a.before(b); a.after(b);
GregorianCalendar a = new GregorianCalendar([1991,11,31][23,59,59]); //23:59:59 1991-11-31
a.set(1991, 11, 31, 23, 59, 59);
a.get(Calendar.MONTH);
a.set(Calendar.MONTH, 11);
a.add(Calendar.MONTH, 1);
Date c = a.getTime();
a.setTime(c);//convert
```

## java.text.SimpleDateFormat

``` java
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
String s = sdf.format(new Date());  ->20140623_233445
```

## java.util.Random

``` java
Random generator = new Random();
int a = generator.nextInt(n); -> 0-n-1
```

## java.util.ArrayList

``` java
ArrayList<Employee> staff = new ArrayList<>([100]);//100为初始容量。区分容量与大小
staff.add([n,]new Employee());
staff.remove(n);
staff.ensureCapacity(100);
staff.size();
staff.get(i);
staff.set(i, harry);
x[] a = new X[staff.size()];
staff.toArray(a);
```

## 包装器与自动装箱

Integer, Long, Float, Double, Short, Byte, Character, Void, Boolean 全不可变，不可扩展

``` java
ArrayList<Integer> list = new ArrayList<>();
list.add(3); // list.add(Integer.valueOf(3));                     // 装箱
list.get(i); // list.get(i).intValue();                           // 拆箱
Integer.valueOf(3);  // 转int到Integer
num.intValue()  // 转Integer到int
Integer.toString(32, 2);  // 输出32的二进制
Integer.parseInt("aef", 16);  // 输出16进制下的aef表示的整数
Integer.valueOf("aef", 16);  // 返回Integer而非int
```

## Enum

``` java
Size s = Enum.valueOf(Size.class, "SMALL");
Size.SMALL.toString(); Size[] values = Size.values();
Size.SMALL.ordinal();
enum Size //java.lang.Enum<E>, java.lang.Class<T> {
   SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRALARGE("XL");
   private Size(String abbreviation) { this.abbreviation = abbreviation; }
   public String getAbbreviation() { return abbreviation; }
   private String abbreviation;
}
```

## Exception

``` java
try {
    statements
} catch (Exception e) {
    handler
}
```

## Equals

相等测试：基本类型==;类类型，Object.equals(a,b); (a,b全null,true; a,b有null,false; a,b无null，a.equals(b))

``` java
public boolean equals(Object otherObject) {
    if (this == otherObject) return true; //引用同一对象
    if (otherobject == null) return false;
    if (getClass() != otherObject.getClass()) return false;
    Employee other = (Employee) otherObject;
    return Object.equals(name, other.name)
        && salary==other.salary
        && Object.equals(hireDay, other.hireDay);
}
```

## java.io.File

``` java
File f = new File(".config/awesome/rc.lua"); f.exists();
File pf = f.getParentFile(); ->.config/awesome
String pfpath = pf.getAbsolutePath(); ->/home/enali/.config/awesome
File.separator;     ->'/' ; File.pathSeparator;     ->':'
```

## runtime

``` java
Process p = Runtime.getRuntime().exec("shell cmd");
p.waitFor();p.destroy();
```