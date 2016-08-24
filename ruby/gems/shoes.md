# shoes

`shoes`是`Ruby`的一个小型图形库, 但并不能通过`gem`来安装. 需要去[官网](www.shoesrb.com)下载对应系统的安装包.

另一个区别是, 不同于普通的`ruby`脚本, 通过命令行`ruby xxx.rb`或`./xxx.rb`运行, `shoes`的脚本需要在`shoes`的程序中手动打开脚本来运行.

不要期望能做大型程序, 只适合于小型`GUI`程序的开发.

```ruby
Shoes.app do
  button "Push me" do
    alert "hello, world"
  end
end
```
 
## 概念理解

`App`, 通常对应一个窗口.

`Slot`, 槽, 或理解为容器, 或对应安卓的布局, 总之是一种容纳图形元素的框, 主要有`flow`横向槽和`stack`纵向槽.

`Element`, 图形元素, 包括文本, 按钮, 编辑框, 形状等.

## 内建函数

`alert(msg)`, 弹出新窗口, 显示警告信息

`ask(question)`, 弹出窗口, 提示问题, 要求输入答案, 返回答案

`ask_color(title)`, 弹出颜色选择窗口

`ask_open_file()`, 弹出文件选择窗口

`ask_save_file()`, 弹出保存文件窗口

`ask_open_folder()`, 弹出目录选择窗口

`ask_save_folder()`, 弹出保存目录窗口

`comfirm(question)`, 弹出确认窗口

`info(msg)`, 发送信息到控制台

`debug(msg)`, 发送调试信息到`Shoes console`窗口, `cmd+/`快捷键

`warn(msg)`, 发送警告信息

`error(msg)`, 发送错误信息到`Shoes console`窗口

`exit()`, 退出窗口程序, 如果要调用`ruby`自己的`exit`, 调用`Kernel.exit`

`font(msg)`, 从文件载入字体

`gradient(color1, color2)`, 创建颜色渐变, 参数为`Shoes::Color`对象或颜色字串

`gray(darkness, alpha)`, 创建灰度, 参数为黑色深度和透明度

`rgb(r,g,b,a)`, 创建颜色对象, 参数为红绿蓝和透明度

`Shoes::FONTS`, 系统已安装字体数组, 可通过`include?`判断字体是否存在于系统

### APP, 窗口

`Shoes.app(styles) {...}`, 生成新窗口, 一旦窗口被创建, 会被加到`Shoes.APPS`列表

风格: 
* `title: title_text`, 标题文本
* `width: 300`, 窗口宽度
* `height: 300`, 窗口高度
* `resizable: true/false`, 是否可改变窗口大小


`Shoes.APPS()`, 所有打开的窗口会加到此列表中, 关闭窗口会移除

`clipboard()`, 返回系统剪切板的内容, `clipboard = msg`, 将字串拷贝到剪切板

`close()`, 关闭窗口

`download(url, style) {|page| ...}`, 开启一个下载线程, 每个线程有3个事件: `start`, `progress`, `finish`. 如果附加块, 则会在`finish`事件后调用.

风格:
* `save: fname`, 将下载的内容保存为文件
* `method: "POST"`, `body: "v=1.0&q=shoes"`自定义`HTTP`请求


`location()`, 返回当前窗口的`URL`, (不理解)

`mouse()`, 返回鼠标按键状态, 离左/离上距离的数组, 3个数据

`owner()`, 返回调用生成当前窗口的`APP`, 通常在`window`的子窗口中获取父窗口

`started?()`, 窗口是否完整构建并绘制

`visit(url)`, 改变`location`, 为了看到不同`URL`的窗口

## 风格, styles

风格也就是元素的外观.

在元素创建的同时赋予风格, 如`:tittle, :stoke, :underline`.

```ruby
Shoes.app title: "A Style Sample" do
  para "Red with an underline", stroke: red, underline: "single"
end
```

在元素创建后赋予风格:

```ruby
Shoes.app title: "A Styling Sample" do
  @text = para "Red with an underline"
  @text.style(stroke: red, underline: "single")
end
```

或者是作为方法调用:

```ruby
Shoes.app title: "A Styling Sample" do
  @text = para "Red with an underline"
  @text.stroke = red
  @text.underline = "single"
end
```

常用风格:
* `:align`, 对齐, 取值`left, center, right`
* `:angle`, 控制颜色渐变角度
* `:attach`, 将槽附加到另一个槽或元素或窗口
* `:autoplay`, 允许视频自动播放
* `:bottom`, 设置元素或槽的底边相对容器的底边距离
* `:cap`, 设置线结束处的形状
* `:center`, 标识左/上坐标是否指向中间

