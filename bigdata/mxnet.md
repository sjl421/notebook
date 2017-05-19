# MXNet

最好安装在最新的`Ubuntu`版本中, 不然, 可能要编译安装`opencv2.4.13`

## 安装

标准安装:
* 从源码中构建共享库
* 安装支持的语言包(`Python`, `R`, `Scala`等)

安装依赖: `sudo apt install -y libopenblas-base libopenblas-dev graphviz pkg-config`

## opencv 2.x

推荐编译安装`opencv`, 系统默认的`libopencv-dev`有非常多的依赖项, 对于`server`版本的`ubuntu`. 

* `opencv`的依赖: `libjpeg-dev libtiff5-dev libjasper-dev libpng16-dev zlib1g-dev`. 
* 若要使用`java jni`, 需提前安装`java`. 
* `opencv 2.x`版本其`python`只支持`2.x`, 需要`numpy`.

编译安装`opencv`, 官方文档提示要`cmake`的版本`>=3.6`:

```
wget https://codeload.github.com/opencv/opencv/zip/2.4.13
cmake -D BUILD_opencv_gpu=OFF \
      -D WITH_CUDA=OFF \
      -D WITH_1394=OFF \
      -D CMAKE_BUILD_TYPE=RELEASE \
      -D CMAKE_INSTALL_PREFIX=/usr/local ..  
      # 可能需要-DPYTHON_EXECUTABLE=$(which python)
make -j$(nproc)  #CPU核心数
sudo make install
echo 'export PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig/' >> .bashrc
```

没有`GUI`支持, 也没有`Video`支持, 也没有第三方库

### shared lib

构建共享库:
```sh
git clone https://github.com/dmlc/mxnet.git ~/mxnet --recursive
cd ~/mxnet
cp make/config.mk .
echo "USE_BLAS=openblas" >>config.mk
echo "ADD_CFLAGS += -I/usr/include/openblas" >>config.mk
echo "ADD_LDFLAGS += -lopencv_core -lopencv_imgproc" >>config.mk  # -lopencv_imgcodecs只存在于3.x版本
# Just for GPU support
echo "USE_CUDA=1" >>config.mk
echo "USE_CUDA_PATH=/usr/local/cuda" >>config.mk
echo "USE_CUDNN=1" >>config.mk
# 构建
make -j$(nproc)  # 生成libmxnet.so
```

### python

依赖`graphviz`, `jupyter`

```sh
cd python
python setup.py install  #如果使用系统python,则需要加sudo
```

在`ipython`环境中, 执行`import mxnet as mx`, 不报错即安装成功.

