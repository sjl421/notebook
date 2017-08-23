# python science computation

## numpy

* `array(list)`, 从列表生成数组
* `arange(stop)`, `arange(start, stop [, step])`, 生成等差数列, 指定步长, 不含终值
* `linspace(start, stop, num [, endpoint=True])`, 生成等差数列, 指定元素数, 默认有终值
* `logspace(start, stop, num [, base=10 , endpoint=True])`, 生成等比数列, 指定元素数, 默认有终值
* `ravel()`, 返回平坦数组

* `empty(shape [, dtype=float])`, 生成指定类型/形状的数组, 只分配内存, 不初始化
* `zeros(shape [, dtype=float])`, 生成元素为0的数组
* `ones(shape)`, 生成元素为1的数组
* `full(shape, fill_value)`, 生成数组并用指定值填充
* `zeros_like(ary)`, `ones_like(ary)`, `empty_like(ary)`, `full_like(ary)`, 生成同类型同形状的数组

* `c_[ary1, ary2]`, 按列连接向量
* `mgrid[start:stop:num, start:stop:num]`, 创建`mesh`网格

* `array_str(ary [, precision=None])`, 返回数组的字串表示, 若元素类型为浮点数, 可指定显示的精度

* `convolve(ary1, ary2)`, 求两个数组的卷积

* `allclose(a1, a2)`, 求两个数组是否相近, 考虑到浮点数表示的不精确性

### 随机分布

* `binominal(n, p[, size])`, 二项分布
* `bytes(len)`, 返回随机字节串
* `chisquare(df[, size])`, 卡方分布, `df`(degree freedom)为自由度
* `choice(a[, size, replace, p])`, 从数组`a`中取样, `replace`是否有放回, `p`为每个元素的采样概率
* `exponential([scale, size])`, 指数分布, 指定`lambda`即可
* `f(dfnum, dfden[, size])`, F分布, 分别指定分子分母上的自由度

## scipy

### constants, 常量模块

### special, 函数库

* `gamma(x)`, 伽玛函数

### optimize, 拟合和优化

* `fsolve(func, x0)`, 求解非线性方程组
* `leastsq(func, p0)`, 最小二乘拟合
* `curve_fit(func, x, y, p0)`, 一维曲线拟合

## pandas

## matplot.pyplot

* `figure(figsize=(x,x))`, 创建图表
* `ax = subplot(num_row, num_col, n)`, 对`nth`子图作画
* `subplots_adjust()`, 调整子图间的布局 ,`left/right/bottom/top/wspace/hspace`
* `f, axes = subplots(num_row, num_col)`, 返回图表和所有子图`Axes`对象

* `plot(X, y[, label=xx, linewidth=xx, color=xx])`, 拆线图
* `scatter(X, y)`, 散点图
* `semilogx(X, y)`, `X`轴为对数轴的拆线图
* `fill_between(X, y1, y2)`, 在`y1-y2`间填充色
* `axhline(y0)`, `axvline(x0)`, 画水平线/垂直线

* `gcf()`, 获得当前图表, 图表包含多个子图
* `gca()`, 获得当前子图
* `clf()`, 清除当前图表
* `sca(axes)`, 设置子图为当前子图

* `title(str)`, 为绘图添加标题
* `xlabel(str)`, `ylabel(str)`, 设置X/Y轴的文字说明
* `xlim([min, max])`, `ylim([min, max])`, 设置X/Y轴的范围
* `xticks()`, `yticks()`, 设置X/Y轴的刻度属性
* `legend()`, 显示说明
* `show()`, 显示绘图窗口
* `savefig(file_path)`, 保存为图像文件
* `annotate(text, xy, xycoords)`, 在`xy`处标注文本`text`

### Axes

* `set_xlabel`, `set_ylabel`, `set_xlim`, `set_ylim`
* `xaxis.set_visible(False)`, 可关闭X轴的刻度显示

## seaborn