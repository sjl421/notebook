# D3

[D3js](https://d3js.org/), 所谓的数据驱动文档.

安装: `cnpm install d3`, 或在页面中引入`<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>`

虽然`d3`是一个前端绘图库, 但本质上只是一个可以将数据绑定到`DOM`上的库.

```js
var d3 = require('d3')
var linear = d3.scaleLinear().domain([0, 10]).range([0, 100])
linear(10)
```

最简单的入门教程[D3.js入门](http://wiki.jikexueyuan.com/project/d3wiki/)

## SVG

可缩放矢量图形（`Scalable Vector Graphics，SVG`)，是一种用来描述二维矢量图形的`XML` 标记语言。

其每个元素都是一个`XML`标签, 有一些基本图形, 如圆(`<circle>`), 矩形(`<rect>`), 椭圆(`<ellipse>`), 线段(`<line>`), 多边形(`<polygon>`, 闭合), 路径(`<polyline>`, 不闭合).

再通过`CSS`为这些元素设置`style`来显现图形.

```html
<svg>
  <rect></rect>
  <circle></circle >
<svg>
```

## 使用

`d3`通过`select/selectAll`(返回的都是`array`)来选择(使用`css`选择器)元素, 再通过`#data`来绑定数据到选择集上, 然后就可通过`(d, i) => {return xxx}`函数来设置相应的属性或值.

`1st`参数表示此元素上绑定的数据, `2nd`参数表示此元素在选择集上的索引.

就目前的使用来看, 直接用裸`d3`画图实在费劲.

当使用`append/insert`来添加元素时, 返回的是添加元素的选择集.