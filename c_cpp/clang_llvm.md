# LLVM and Clang

## 在cmake中使用clang

在`C++`项目中使用`Clang++`编译器.

1. 定义`CMakeLists.txt`文件

这里假设你下载的是压缩包,并`export LLVM_HOME=/path/to/llvm`环境变量.

```cmake
set (CMAKE_CXX_COMPILER "$ENV{LLVM_HOME}/bin/clang++")
set (CMAKE_CXX_FLAGS "-std=c++11 -stdlib=libc++ -Wall")
set (CMAKE_EXE_LINKER_FLAGS "-Wl,-rpath,$ENV{LLVM_HOME}/lib -lc++abi")
```

这里区分链接选项和编译选项:

  `-Wl,-rpath,$ENV{LLVM_HOME}/lib`, adds a runtime library search path
  `-lc++abi` is must when use libc++ in linux

不要`libc++`的话,更简单些

```cmake
set (CMAKE_CXX_COMPILER "$ENV{LLVM_HOME}/bin/clang++")
set (CMAKE_CXX_FLAGS "-std=c++11 -Wall")
```

目前存在一个问题,若每个子文件都是一个子项目,即有`project (subproj1)`,则在顶层的
`CMakeLists.txt`文件中设定`CMAKE_CXX_COMPILER`会导致重复配置问题,即`cmake ..`命令
运行无法停止. 从这个角度看,第二个方式比较好.

2. 定义`shell`环境变量

在`~/.bashrc`加入

```sh
if [ $LLVM_HOME ]; then
  export CC=$LLVM_HOME/bin/clang
  export CXX=$LLVM_HOME/bin/clang++
  export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$LLVM_HOME/lib
elif which clang >/dev/null 2>&1; then
  export CC=$(which clang)
  export CXX=$(which clang++)
fi
```

则在`CMakeLists.txt`就不需要`-I`和`-L`选项.

```cmake
set (CMAKE_CXX_FLAGS "-std=c++11 -stdlib=libc++ -Wall")
set (CMAKE_EXE_LINKER_FLAGS "-lc++abi")
```

如果不要求使用`clang`出品的`libc++`库的话,还可以更简单些

```cmake
set (CMAKE_CXX_FLAGS "-std=c++11 -Wall")
```

3. 包管理系统安装`clang`和`llvm`

```sh
sudo apt-get install llvm-3.x-* clang-3.x-* libc++1 libc++-dev libc++abi1 libc++abi-dev
sudo update-alternatives --config [c++|cc]  # 选择想使用的C++编译器
```

注: `3.x`是一个数字, 对应`3.4, 3.5, 3.6`

使用`ldd exe`来查看,可执行程序链接了什么库

## 构建类型 

```cmake
set (CMAKE_BUILD_TYPE "Debug")
set (CMAKE_CXX_FLAGS_DEBUG "-g")
set (CMAKE_CXX_FLAGS_MINSIZEREL "-Os -DNDEBUG")
set (CMAKE_CXX_FLAGS_RELEASE "-O3 -DNDEBUG")
set (CMAKE_CXX_FLAGS_RELWITHDEBINFO "-O2 -g")
```

## libc++

如果你的项目依赖多个第三方库, 则第三方库和标准库必须是相同的.考虑到标准库总是被依赖,
则你的项目和第三方库必须是使用相同的标准库编译出来的.因为`GNU libstdc++`和`Clang libc++`
两种标准库的`ABI`并不兼容,因此,在考虑到`Linux`平台大部分库都是用`GNU`标准库编译的情况,
在`Linux`上使用`libc++`并不是一个明智的选择.当然,另一方面,若第三方库你是编译安装的,则
可通过都使用相同的`libc++`来使用.
