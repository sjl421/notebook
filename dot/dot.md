# dot

一种绘图语言, 官网[Graphviz](https://www.graphviz.org/), 安装后可使用`dot`命令将`dot`文件转变为别的格式, 如`pdf/jpg`等.

## js

主要是3个库:

* [graphlib](https://github.com/dagrejs/graphlib), 通过代码的方法添加节点/边等来创建`graph`
* [graphlib-dot](https://github.com/dagrejs/graphlib-dot), 实现了`read/write`在字串和`graph`对象间转换
* [dagre-d3](https://github.com/dagrejs/dagre-d3), 将`graph`对象渲染为`svg`