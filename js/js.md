# javascript

命名规范:
* 变量和函数使用驼峰法来命名
* 全局变量和常量为大写
* 一条语句通常以`;`结束
* 块`{}`不加`;`


引入:

`js`脚本必须位于`<script></script>`标签之间, 标签可被放置在`<body>`或`<head>`中.

```html
<script src="fname.js"></script>
<script>
function () {...}
</script>
```

`js`主要有5种数据类型, `string`, `number`, `boolean`, `object`, `function`. 3种对象类型, `Object`, `Date`, `Array`. 2个不包含任何值的数据类型, `null`, `undefined`.

`typeof obj`, 返回对象类型

```javascript
typeof "hello"  //string
typeof 3.14     //number
typeof NaN      //number
typeof true     //boolean
typeof ['a', 1, 3.14, true]  //object
typeof { name: 'enalix', age: 24 }  //对象即键值对组合
typeof new Date()   //object
typeof null  //object
typeof xpzkdjls   //未声明标识符, undefined
var xpzkdjls  //声明标识符, 但无值
typeof xpzkdjls  //undefined
```

用`var`声明变量, 若不初始化, 则为`undefined`, 表示变量不含值.

对象:

```javascript
var o = { name: 'enalix', age: 24 }  //对象即键值对组合
o.name  //=> 'enalix', 访问已有属性, 或o['name']
o.school = 'beijing'  // 新属性
Object.defineProperty(o, "x", {value: 0, writable: false});  //定义只读属性
o.info = function () { return this.name + this.age; }  //函数也是对象, 可作为值
o.info()  //调用方法, 或o['info']()
```

`null`是一个只有一个值的特殊类型, 表示一个空对象引用. 可以给对象设置`null`来清空对象.

数组:

```javascript
var ary = [1,2,3,4];
ary[4]  //=> 超出范围的访问, 返回undefined值
ary[5] = 9  //越范围赋值, 即索引4没有值
ary  //=> [1,2,3,4,,9]
ary['name'] = 'lzp'  //数组本身也是对象, 也可以存储键值对, 类似ahk
ary  //=> [1,2,3,4,,9,name: 'lzp']
ary.concat(['a', 'b', 'c', 'd'], [9, 0, 2])  //数组连接
ary.join(', ')  //默认连接符','
ary.pop()  //抛出数组的尾元素
ary.push(4)  //添加数组的尾元素
ary.reverse()  //逆反数组
ary.shift()  //删除数组首元素
ary.unshift(4, 3)  //在数组首部添加2元素
ary.slice(s, e)  //返回开始和结束索引的数组, 不含结束元素, 返回新数组 
ary.sort()  //数组排序, 升序
ary.sort(function (a,b) { return ...})  //函数返回3种值, 大于0, 小于0, 等于0.
ary.splice(s, l, ele1, ele2, ...)  //插入元素, 如果l长度不为0, 则会删除指定长度元素, 再在原位置写入元素
ary.indexOf(ele)  //返回元素的索引
ary.toString()  //默认的split()
Array.prototype.ucase = function() {...}  //为数组添加新的方法
```

函数:

函数声明后不会立即执行, 直到调用. 不以`;`号结束.

```javascript
function func(a,b) {
  return a*b;
}  //定义
var func = function(a,b) { ...} //表达式定义匿名函数
var func = new Function("a", "b", "return a*b");  //函数对象构造
func()  // 调用
window.func()  //只在浏览器中成立
this.func()  //都成立, this默认为全局对象, 浏览器中全局对象为window
(function () { console.log("hello") })()  //自调用
func.toString()  //函数字串化, 匿名函数名为`anonymous`
function aa(a,b) {
  this.first = a;
  this.last = b;
  this.full = function () { return this.first + this.last; }
}  //构造函数
var x = new aa("li", "ena")  //=> 创建对象
x.first, x.last, x.full()
var a = {first: "li", last: "ena"}
function fa(age) { return this.first + this.last + age }
fa.call(a, 24)  //函数对象本身是对象, 也存在方法, call首参为对象, 之后传入参数
fa.apply(a, [24])  //参数以数组形式提供
```

在`javascript`中, 很多时候, 你需要避免使用`new`关键字.

`var`声明变量, 声明在函数内, 局部变量, 运行时创建, 运行后销毁; 声明在函数外, 全局变量, 页面关闭时销毁.

未声明而直接赋值变量, 创建全局变量.

`HTML`中, 所有全局变量都属于`window`对象, 通过`window.varname`调用.

函数对参数不作任何检查, 参数多时忽略, 参数少时, 默认为`undefined`.

传递给函数的参数会构成数组成为`arguments`对象.

`z = z || 0`, 若`z`未定义则为0. 但要明白, `js`语言的假很多, `z`为`null`时, 也成立.

对于参数的修改, 普通参数不修改, 对象参数修改.

不属于任何对象的函数默认是全局对象的, 在`HTML`中默认的全局对象是页面本身, 在浏览器中页面对象是浏览器窗口, 即`window`对象.

闭包:

内嵌函数作为对象被返回时, 会保存外层函数的局部变量, 俗称闭包.

```javascript
var add = (function () {
  var counter = 0;
  return function () { return counter += 1; }
})();
add()
add()
```

事件:

`HTML`事件是发生在`HTML`元素上的事情. 当在`HTML`而面中使用`javascript`时, 可以触发这些事件.

```html
<button onclick="this.innerHTML=Date()">现在时间是?</button>
<!-- 当点击此按钮, 改变当前元素内容, 更新内容为当前时间点-->
<button onclick="getElementById("demo").innerHTML=Date()">现在时间是?</button>
<!-- 获取id为demo的元素内容为当前时间点 -->
<script>
function displayDate() {document.getElementById("demo").innerHTML=Date(); }
</script>
<button onclick="displayDate()">点这里</button>
```

有如下事件:
* `onchange`, 改变元素内容
* `onclick`, 点击元素
* `onmouseover`, 移动鼠标到元素上
* `onmouseout`, 从元素上移出鼠标
* `onkeydown`, 按下键盘按键
* `onload`, 完成页面加载


字串:

字串的断行要明确的`\\`.

```javascript
var carname = "hello, world"
carname[0]  //=> 'h', 不存在单字符, 而是长度为1的字串
carname.length  //长度
var x = "John"
var y = new String("John") //不推荐, 拖慢执行速度, 可能有副作用
typeof x  //=> string
typeof y  //=> object
x == y  //=> true, 值相等判断
x === y  //=> false, 类型和值都相等判断
y.valueOf()  //=> "John", 字串原始值
```

```javascript
var s = "你好"
s.charAt(0); s[0];  //=> '你', 看来能正确处理unicode字符
s.charCodeAt(0);  //=> 20320, 返回索引位置字符的unicode值
s.concat('world')  //=> '你好,world', 不改变原字串
var x = "hello"
x.indexOf('l')  //=> 2, 返回字串索引, 若不存在返回-1
x.lastIndexOf("l")  //=>3, 返回最后一次出现的位置
x.match(/el/)  //=> ['el', index: 1, input: 'hello'], 最常用的应该是index
x.replace(/el/, "le")  //=> "hlelo", 字串替换, 可正则可字串
x.search(/el/)  //=> 1, 直接返回索引, 可正则, 也可字串参数
x.slice(1, 2)  //=> 'e', start, end
var y = "enalix:24:beijing"
y.split(':')  //=> ["enalix", "24", "beijing"]
x.substr(1, 2)  //=> "el", start, length
x.substring(1,2) //=> "e", 同slice, start, end
"A".toLowerCase()  //=> 'a'
"a".toUpperCase()  //=> "A"
"  A  B  ".trim()  //=> "A  B", 相当于Ruby的chomp, Python的strip
"hello" + ", world"  //=> "hello, world"
"hello" + 3  //=> "hello3"
```

运算符:

支持`++`和`--`, 遵循`C`的规则, 后加先用, 先加后用.

支持复合运算符: `+= -= *= /= %=`

比较运算符: `== === != !== > < >= <=`. 其中, `!==`和`===`对应.

关于布尔表达式, 所有非`false`, `null`, `undefined`, `0`, `""`的值都是`true`. `javascript`是弱类型.

有个特殊的等式: `null == undefined`为`true`, 因为都没有值. 而`null === undefined`为`false`, 类型不同.

因为只有`number`一种类型, 因为`5 == 5.0`和`5 === 5.0`总是相等的.

逻辑运算符: `&& || !`

条件运算符: `condition ? v1 : v2`

条件语句:

```javascript
if (condition) {
  true_statement
} else if (condition2) {
  true_2_statement
} else {
  false_statement
}

switch (n) {  //使用===比较
  case 1:
    block1; break;
  case 2:
    block2; break;
  default:
    ...
}
```

循环语句:

```javascript
for (var i=0; i<n; i++) {
  statements;
}
for (x in obj) {
  obj[x]  //x为对名的属性, 对于数组, x为索引
}
while (condition) {
  statements
}
do {
  statements
} while (condition)
break [labelname] //跳出最近循环, 可选跳到标记语句处
continue [labelname]  //跳过此次循环, 进入下一个循环
label:  //跳到标记处, 但并不执行标记的语句, 而是进入下一条
  statement  //给语句加标记
```

类型转换:
* `Number()`, 转换为数字
* `String()`, 转换为字串, 或`.toString`
* `Boolean()`, 转换为布尔值


`constructor`属性返回所有`javascript`变量的构造函数. 因为`typeof`对所有对象统一返回`object`, 难以匹配是什么类型, 可使用`"hello".constructor.toString().indexOf("String") > -1`来判断对象是否为字串

```javascript
Number("  3.14 ")  //=> 3.14
Number("  ")  //=> 0
Number("a 3.14")  //=> NaN
Number.parseInt("3.14")  //=>3, 返回整数
Number.parseFloat("3")  //=>3, 返回浮点数, 没什么用...
+"5", -"5"  //=> 5, 为字串时, 视为类型转换
+"a ", -"a "  //=> NaN
+5  //=> 5
+-5, +"-5", -+5, -"+5"  //=> -5
Number(false)  //=>0
Number(true) //=> 1
Number(new Date())  //=> 相当于getTime()返回的毫秒数
Number(null)  //=> 0
Number(undefined)  //=> NaN
"5" + 1  //=> "51"
"5" - 1  //=> 4, 因为-没重载...
```

数字:

```javascript
var n = 12300
n.toExponential()  //=>1.23e+4 转为科学计数法
n.toPrecision(2)  //=>1.2e+4, 设置精度
var n = 3.1415
n.toFixed(2)  //=>3.14, 设置小数位数
```

注意, `new Date()`返回当前日期对象, 而`Date()`则返回当前日期字串.

日期:

```javascript
Date()  //当前时间字串
Date.now()  //当前时间的getTime毫秒数
var n = new Date()  //日期对象
n.getDate()  //月中天
n.getDay()  //周中天
n.getFullYear()  //4位数字的年
n.getMonth()  //月份
n.getHours()  //小时
n.getMinutes()  //分钟
n.getSeconds()  //秒数
n.getMillseconds()  //毫秒
n.getTime()  //返回1970年1月1日至今的毫秒数
```

正则:

```javascript
var patt = /name/i  //=> 不分大小写
/name/g  //=> 全部匹配
/name/m  //=> 多行匹配
patt.test("hello, name")  //=> true, 匹配测试
patt.exec("hello, name")  //=> ["name", index: 7, "hello, name"], 不匹配则为null
str.match(pat)  //全局匹配时, 若有多匹配, 则返回数组, 否则返回对象
pat.test(str)  //如果匹配则返回true
pat.exec(str)  //相当str.match(pat)
```

异常:

```javascript
try {
  statements
} catch (err) {
  错误处理代码
  err.message
}
throw "数字不对"  //字串,数字,布尔, 对象
```

调试:

```javascript
console.log(msg)  //常见的输出
debugger;  //设置此处为断点
```

提升:

`javascript`中, 函数及变量的声明都将被提升到函数的最顶部. 也就是说, 变量可以在使用后声明, 也就是变量可以先使用再声明. 函数可以在声明之前调用.

只有声明的变量会提升, `var x`; 初始化的不会提升, `var x = 7`.

使用表达式定义函数时无法提升.

即便你能理解变量提升, 也推荐在每个作用域开始前声明这些变量.


作用域:

只有函数有局部作用域, 而非`{}`. 也就是说, 常用的代码块是没有作用域的. 这不同于`C`语言.

表单验证:

`var x = document.forms["form_name"]["input_name"].value`来获取输入的值.

JSON:

`JSON`格式在语法上与创建`Javascript`对象代码是相同的. 遵循以下规则:
* 数据为键值对
* 数据由逗号分隔
* 大括号保存对象
* 方括号保存数组


```javascript
var text = '{ "employees" : [' +
'{ "firstName":"John" , "lastName":"Doe" },' +
'{ "firstName":"Anna" , "lastName":"Smith" },' +
'{ "firstName":"Peter" , "lastName":"Jones" } ]}';
var obj = JSON.parse(text)  //转字串为json对象
obj.employees[1].firstName  //=> "Anna"
JSON.stringify(obj)  //转json对象为字串
```

`JSON`字串有个特殊的地方要注意, 所有的键都要用`"`包围.

`void`:

表示指定要计算一个表达式但不返回值.

```html
var a = void(0)  <!-- a undefined -->
<a href="javascript:void(0);">..</a>  <!-- 死链接 -->
<a href="#pos">..</a>  <!-- 锚到特定位置 -->
```

DOM:

`JavaScript`能改变页面中所有的`HTML`元素及其属性, 所有`CSS`样式, 响应所有的事件.

查找`HTML`元素:
* `document.getElementById("idname")`, 通过id
* `document.getElementsByTagName("p")`, 通过标签
* `document.getElementsByClassName("cname")`, 通过类名
* `document.getElementsByName("name")`, 通过名字获取元素


因为`id`是唯一的, 因此找不到则为`null`, 而标签或类名都不唯一, 找不到为`[]`.

`document.write("...")`, 直接写入`HTML`. 但如果在文档加载完成后执行, 会覆盖整个文档. 对, 说的就是不要将其写在函数内, 然后事件触发执行.

假设`ele`为元素对象, 无论你通过哪个`api`获取的.

`ele.innerHTML=new HTML`, 改变元素内容.

`ele.attr_name = new value`, 改变元素属性, 请替换`attribute`

`ele.style.property_name = new style`, 改变元素样式

样式`visibility`有两个值, `hidden`则看不到元素, `visible`则显示元素.

`onclick=js_code`, 点击事件, 注意, 所有元素都可以有点击事件, 不止`button`.

`js_code`若有`this`, 则指代元素本身的对象.

`ele.onclick=js_code`, 可以给元素指定事件属性.

`ele.addEventListener(event, function, useCapture)`, 为元素添加事件, 终于不用为元素添加一大堆的事件属性了.可以统一添加了. 可以为同一个元素添加多个相同事件.

`event`为事件名, 注意, 类似`onclick`的事件名为`click`, 去掉`on`.

`useCapture`为布尔, 描述事件是冒泡还是捕获.

`ele.removeEventListener("click")`, 移除

`onload`和`onunload`事件会在用户进入或离开页面时被触发.

`navigator.cookieEnabled`, 若浏览器启用`cookie`, 则返回`true`.

`onchange`事件常结合对输入字段的验证来使用. 当输入改变时, 调用代码.

`onfocus`事件, 当输入字段获取焦点时触发代码.

`onmouseover`和`onmouseout`, 在用户鼠标移至元素上方或移出元素时触发代码.

`onmousedown`, `onmouseup`, `onclick`构成鼠标点击事件. 按下鼠标, 抬起鼠标, 完成点击.

以上所有事件, 基本都可以作为任何元素的属性来设置, 当此元素触发事件时, 则会执行代码.

创建新的`HTML`元素:

```javascript
var para = document.createElement("p"); //创建新p元素
var node = document.createTextNode("hello, world");  //创建文本结点
para.appendChild(node);  //将文本结点添加到p元素中
var ele = document.getElementById("id1");  //获取id1元素
ele.appendChild(para); //将p元素添加到id1元素中
```

删除已有的`HTML`元素:

```javascript
var parent = document.getElementById("id1");
var child = document.getElementById("id2");
parent.removeChild(child);  //从父元素中移除子元素
```

`js`中的对象没有本质区别, 区别的只是属性及数目不同而已.

创建对象:

```javascript
var person=new Object();
person.firstname="John";
person.lastname="Doe";
person.age=50;
person.eyecolor="blue"; 
```

或者是

```javascript
person={firstname:"John",lastname:"Doe",age:50,eyecolor:"blue"}
```

或者是

```javascript
function person(firstname,lastname,age,eyecolor) {
  this.firstname=firstname;
  this.lastname=lastname;
  this.age=age;
  this.eyecolor=eyecolor;
}
myFather=new person("John","Doe",50,"blue");
```

`js`中的数字都是64位的浮点型. 前缀`0`则为八进制, 前缀`0x`则为十六进制.

`.toString`方法进行进制转换, 如`.toString(16)`则转换为十六进制. 

当数字运算超过了能表示的数字上限, 结果为一个无穷大`Infinity`, 也有个负无穷大`-Infinity`. `10/0`为无穷大.

通过`isNaN(x)`来判断是否是`NaN`, 非数字值.

数字可以是数字或者对象, `var x = new Number(123)`.

数学函数:

```javascript
Math.round(2.5), floor, ceil  //=>3 四舍五入, 左侧近似, 右侧近似
Math.random()  //产生0-1间的随机数
Math.max(1, 2, 3, 4)  //最大值
Math.min(1, 2, 3, 4)  //最小值
Math.PI, E, SQRT1_2, SQRT2, LN2, LN10, LOG2E, LOG10E  //常量值
Math.sqrt(16)  //求平方值
```

所有`js`全局对象, 函数及变量均自动成为`window`对象的成员. 全局变量是`window`对象的属性, 全局函数是`window`对象的方法. 甚至`HTML DOM`的`document`也是`window`对象的属性之一.

`BOM`, 即浏览器对象:
* `window`, 窗口对象
  * `.innerHeight`, 窗口内部高度, 
  * `.innerWidth`, 窗口内部宽度
  * `.open(url)`, 打开新窗口, 即标签, 可以设置窗口属性和外观
  * `.close()`, 关闭窗口, 即标签
  * `.focus()`, 聚集窗口
  * `.closed()`, 判断关闭与否
  * `.opener`, 返回打开此窗口的父窗口对象
  * `.moveBy`, `.moveTo`, 移动窗口, 一个相对值, 一个绝对值
  * `.print()`, 打印当前窗口
  * `.resizeBy(x,y)`, 重新认定窗口大小
  * `.scrollBy()`, `.scrollTo()`, 滚动窗口
* `screen`, 屏幕对象
  * `.availWidth`, 屏幕可用宽度, 
  * `.availHeight`, `window`可省略
* `location`, 位置对象
  * `.host`, 返回主机名
  * `.hostname`, `web`主机域名, 
  * `.pathname`, 当前页面路径和文件名, 
  * `.port`, 主机端口, 
  * `.protocol`, 使用的`web`协议
  * `.href`, 当前的`url`
  * `.assign(url)`, `.replace(url)`, 改变当前`url`, 要写明`http://`
  * `.reload()`, 重新载入当前页面
  * `.top`, `.self`, 窗口嵌套窗口, `top`是最外层窗口
* `history`, 历史对象
  * `.back()`, 点击后退按钮
  * `.forward()`, 点击前进按钮
  * `.length`, 返回历史记录长度
  * `.go(num)`, 打开指定历史记录
* `navigator`, 浏览器对象
  * `.appCodeName`, 浏览器代号
  * `.appName`, 浏览器名
  * `.appVersion`, 浏览器版本号
  * `.cookieEnabled`, 是否启用存储`cookie`
  * `.platform`, 操作系统版本
  * `.userAgent`, 用户代理 


`open`窗口的同时可以设置窗口属性, 如`width=800,height=100`等.

`navigator`对象的信息具有误导性, 并不能真正用于检测浏览器版本. 浏览器使用者可更改这些信息. 由于不同浏览器支持不同对象, 可以使用对象检测浏览器.

可以在`js`中创建三种消息框: 警告框, 确认框, 提示框. `alert(msg)`, `confirm(msg)`, `prompt(msg)`. 提示框要求用户输入信息.

启动时事件: `setInterval(func, number)`, 间隔指定的毫秒数不停地执行指定的代码, `setTimeout(func, number)`, 暂停指定的毫秒数后执行指定的代码. 可用`clearInterval(varname)`来清除间隔事件. `clearTimeout(varname)`.
至于屏幕和窗口的区别, 窗口是显示`html`元素的浏览器空间, 而屏幕是用户的屏幕. 通常情况下, 屏幕要更大.

`cookie`:
* `document.cookie = "username=John Doe;"`, 添加
* `document.cookie = "username=John Doe; expires=Thu, 18 Dec 2013 12:00:00 GMT"`, 添加, 并添加过期时间
* `document.cookie = "username=..; expires=..; path=/";`, 添加路径
* `var x = document.cookie;`, 返回所有`cookie`
* `....`, 修改同添加
* `...`, 删除同添加, 只是过期时间是过去而非未来


查找指定键的`cookie`:

```javascript
function getCookie(key) {
  for (x of document.cookie.split(";")) {
    ary = x.trim().split("=")
    if (ary[0] == key) return ary[1]
  }
}
function setCookie(key, value, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + exdays*24*60*60*1000);
  document.cookie = key + "=" + value + "; " + "expires=" + d.toGMTString();
}
```

通常只需要在页面中引用`js`库即可正常使用, 在国内可以使用[百度静态资源公共库](http://cdn.code.baidu.com). 基本只需要在`<head>`标签中嵌入`<script>`标签即可, 写好`src=`属性.

`jquery`返回`jquery`对象, 与已传递的`DOM`对象不同. `jquery`对象拥有的属性和方法, 与`DOM`对象的不同. 不能在`jquery`对象上使用`HTML DOM`的属性和方法.

锚, `document.anchors`是所有锚的数组, `.length`返回锚的数目, `[n].innerHTML`返回锚的内容.

表单, `document.forms`, `.length`, `[n].name`.

图像, `document.images`, `.length`, `[n].id`.

链接, `document.links`, `.length`, `[n].id`

`document.domain`, 加载文档的服务器域名

`document.lastModified`, 返回文档的最后一次修改时间

`document.referrer`, 在A文档中打开B文档, 此处显示A的链接

`document.URL`, 此处显示B文档的链接

`document.write(msg)`, `.writeln(msg)`, 向文档输入内容

`document.title`, 返回文档的标题


锚: `ele = getElement...`, 有属性`.charset`,`.href`,`.hreflang`,`.name`,`.rel`,`.target`,`.type`.

按钮: `ele = ...`, 有属性`.disabled`表示是否可用, `.name`, `.type`, `.value`属性.

表单: `ele = ...`, 返回表单的所有元素, `.elements`, `.elements[n].value`返回元素的值, `.action`动作属性, `.method`, `.name`, `.target`, `.reset()`重置表单, `.submit()`提交表单.

事件`Event`: `.keyCode`键盘按键, `.clientX`, `.clientY`鼠标相对窗口坐标, `.screenX`, `.screenY`鼠标相对屏幕的坐标. `.shiftKey`为1表示按下`shift`键, 为0表示没按下. `.type`返回事件的类型.
