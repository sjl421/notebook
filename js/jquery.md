# jQuery

基础语法: `$(selector).action()`.

令文档加载后运行`jQuery`代码, 以下两种方式相同:

```javascript
$(document).ready(function() {
  //jQuery方法
});
$(function() {
  //jQuery方法
});
```

选择器基于元素的`id`,类,类型,属性,属性值等查找`HTML`元素.

选择实例:
* `$("*")`, 选取所有元素
* `$(this)`, 选取当前元素
* `$("p.intro")`, 选择`class`
* `$("p:first")`, 选择第一个`p`元素
* `$("ul li:first")`, 选择第一个`ul`元素的第一个`li`元素
* `$("ul li:first-child")`, 选择每个`ul`元素的第一个`li`元素
* `$("[href]")`, 选择带有`href`属性的元素
* `$("a[target='_blank']")`, 选择所有`target`属性等于`_blank`的`a`元素
* `$("a[target!='_blank']")`, 选择所有`target`属性不等于`_blank`的`a`元素
* `$(":button")`, 选择所有`type`为`button`的`input`元素或`button`元素
* `$("tr:even")`, 选择偶数位置的`tr`元素
* `$("tr:odd")`, 选择奇数位置的`tr`元素


事件:
* 鼠标事件
  * `click`, 单击
  * `dblclick`, 双击
  * `mouseenter`, 鼠标进入
  * `mouseleave`, 鼠标离开
  * `mousedown`, 鼠标按下
  * `mouseup`, 鼠标松开
* 键盘事件
  * `keypress`, 按键
  * `keydown`, 按下
  * `keyup`, 弹起
* 表单事件
  * `submit`, 表单提交
  * `change`, 表单改变
  * `focus`, 表单聚集
  * `blur`, 失去焦点
* 文档窗口事件
  * `load`, 载入
  * `resize`
  * `scroll`, 滚动
  * `unload`


`jQuery`效果:
* `hide()`, 隐藏, 可指定隐藏速度和回调函数
* `show()`, 显示, 速度可取`slow/fast`或者毫秒
* `toggle()`, 在隐藏和显示切换
* `fadeIn()`, 淡入, 可指定速度和回调函数
* `fadeOut()`, 淡出
* `fadeToggle()`, 淡入淡出切换
* `fadeTo()`, 淡出到指定透明度(`arg2`, 0-1取值)
* `slideDown()`, 向下滑动, 可指定速度和回调
* `slideUp()`
* `slideToggle()`
* `animate({params}, speed, callback)`, 首参指定定义形成动画的`css`属性
* `stop(stopAll, goToEnd)`, 停止动画


可以在选择器返回的`jQuery`对象上执行方法链, 以在相同元素上运行多条`jQuery`命令, 如此则不必多次查找相同的元素.

获取内容和属性:
* `text()`, 设置或返回所选元素的文本内容, 可设置回调函数, 利用原内容
* `html()`, 设置或返回所选元素的内容(包括元素内部的`html`标记)
* `val()`, 设置或返回表单字段的值
* `attr()`, 设置或获取属性的值


添加元素:
* `append()`, 在元素结尾插入内容
* `prepend()`, 在元素开头插入内容
* `after()`, 在元素之后插入内容
* `before()`, 在元素之前插入内容

这里的内容非常的多样:

```javascript
var tx1 = "<b>I </b>"
var tx2 = $("<i></i>").text("love ");
var tx3 = document.createElement("big");
tx3.innerHTML = "jQuery";
$("img").after(tx1, tx2, tx3);
```

删除元素:
* `remove()`, 删除被选元素及其子元素, 可选过滤器以只删除指定元素
* `empty()`, 只删除被选元素的子元素


获取并设置`CSS`类:
* `addClass()`, 向被选元素添加一个或多个类
* `removeClass()`, 从被选元素删除一个或多个类
* `toggleClass()`, 对被选元素进行添加删除类的切换操作
* `css()`, 设置或返回样式属性


`.css(propertyname)`来获取属性值, `.css(property_name, value)`设置属性值.

尺寸:
* `width()`, 元素宽度
* `innerWidth()`, 元素包括padding宽度
* `outerWidth()`, 元素包括padding和border的宽度
* `outerWidth(true)`, 元素包括padding和border和margin的宽度
* `height`, `innerHeight`, `outerHeight`, `outerHeight(true)`同上


遍历, 即由当前元素找父元素,子元素,同胞元素
* `parent()`, 返回直接父元素
* `parents()`, 返回所有祖先元素,可选过滤器参数
* `parentsUntil()`, 返回当前元素到指定元素间的所有父元素
* `childer()`, 返回所有直接子元素, 可选过滤器参数 
* `find()`, 返回后代元素, `*`为所有元素, `#id`为过滤参数
* `siblings()`, 返回被选元素的所有同胞元素
* `next`, `nextAll`, `nextUntil`
* `prev`, `prevAll`, `prevUntil`


通用过滤:
* `first()`返回被选元素的首个元素
* `last()`, 返回被选元素的最后一个元素
* `eq(idx)`, 返回一组元素中指定索引的元素
* `filter()`, 返回匹配的元素组
* `not()`, 返回不匹配的元素组


`AJAX`, 在不重载全部页面的情况下, 实现对部分网页的更新. `$(selector).load(URL, data, callback)`.
* `load()`, 选择指定元素后调用方法, 可部分更新此元素内容
* `$.get(URL, callback)`, 通过`get`方法从服务器请求数据
* `$.post(URL, data, callback)`, 通过`post`方法


为了在不同框架间实现兼容, 特别是对`$`符号的使用, 在调用`$.noConflict()`后会释放对`$`符号的占用. 然后使用`jQuery`来代替`$`符号的位置. 你也可以创建自己的简写, `var jq = $.noConflict()`, 此后, 可用`jq`来替代`$`. 另外, 在释放`$`后, 你仍然可以给函数传递`$`符号参数, 以在函数内部使用`$`.

```javascript
$.noConflict();
jQuery(document).ready(function($) {
  $("button").click(function() {
    $("p").text("hello, world");
  })
});
```
