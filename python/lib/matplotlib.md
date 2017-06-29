# matplotlib

## 问题

1, 中文乱码问题

在确保系统有中文字体(如`SimHei.ttf`)后, 做如下配置:

```py
import matplotlib.pyplot as plt
plt.rcParams["font.family"] = "SimHei"
```

在`Linux`系统下, 查看系统安装的字体: `fc-list :lang=zh`.

安装字体, 即将字体文件`.ttf`复制到`/usr/share/fonts`目录中, 再执行`fc-cache`即可.

但对`matplotlib`偶尔还是会有问题, 提示找不到字体, 可以将文件拷贝到`lib/pythonx.x/site-packages/matplotlib/mpl-data/fonts/ttf`目录中, 再删除`~/.cache/matplotlib`缓冲目录即可.

2, 绘图后端的选择

`matplotlib`支持多种绘图后端, 如`wxwidget`, `tk`, `qt4/5`等, 通过如下配置绘图后端:

```py
import matplotlib
matplotlib.use('Agg')
```