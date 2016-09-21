# cmake

CMake是一个跨平台的编译配置工具，CMake运行时，有两种主要模式：执行脚本模式，和执行编译模式 。本文主要介绍CMake执行脚本模式，以及如何编写CMake脚本，并给出简单的示例。

## Usage

```sh
mkdir build && cd build
cmake ..
# ccmake ..  # 更改配置选项
make && make install
```

通常, 可能通过`cmake ..`加一些选项来定制编译过程, 可以通过`ccmake ..`来获取终端
图形界面来配置.
