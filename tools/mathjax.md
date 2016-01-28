# mathjax

`mathjax`是一个`javascript`库,用于渲染网页中的数学公式.

## install

无论什么方法,本质上都是在`html`文件中链接此库.

1. 通过`CDN`使用

```javascript
# 将下面代码放到<head>标签内
<script type="text/javascript" async
  src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-MML-AM_CHTML">
</script>
```

`config`后的参数决定,你的数学公式可用`Tex`,`MathML`还是`AsciiMath`写.

  `Tex`: `$$...$$`和`\[...\]`表示区块公式,`\(...\)`表示行内公式
