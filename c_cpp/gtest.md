# GTest

谷歌的C++单元测试框架.

## 安装

* `git clone https://github.com/google/googletest`
* `cd googletest && mkdir build && cd build`
* `cmake [-DCMAKE_INSTALL_PREFIX=/usr] .. && make`
* `sudo make install`

会将生成的静态库`libgtest.a`, `libgtest_main.a`, `libgmock.a`,`libgmock_main.a`
安装到`/usr/local/lib`中,将头文件安装到`/usr/local/include/gtest/`中,或者添加
安装前缀选项.

有一个有趣的事,这样安装的`gtest`是不能用于要求`libc++`库的项目的.如果有这方面需
要的话,需要在安装时添加`-DCMAKE_CXX_FLAGS="-std=c++11 -stdlib=libc++"`和
`-DCMAKE_EXE_LINKER_FLAGS="-lc++abi"`编译选项.问题最大可能原因是`abi`的不兼容.

还可以加`-O3`选项用于生成代码优化的静态库, 以及`-DBUILD_SHARED_LIBS=ON`来生成动态库.
动态库可减少项目的测试执行文件的体积,但对运行环境有所要求.

当动态库和静态库都存在时,没有什么好的方法支有选择的性的链接,特别是当你使用`FindGTest.cmake`
模块. 通过模块的编写应该预留一个要求寻找静态库的变量,如`GTEST_USE_STATIC`但此模块没有.
可以的话,去自己实现模块.

TODO:
  如何在两种间自由切换,以及使用`libc++abi`会对项目的别的依赖库有什么影响

## 使用

如测试文件`FooTest.cc`, 测试`Foo.cc`中的函数,`Foo.c`生成库`Foo`.

在项目的`CMakeLists.txt`中添加如下内容:

```cmake
# 设置是否编译测试的选项,通过命令`cmake -DWITHTEST=OFF`关闭
option (WITHTEST, "compile with test cases", ON)

if (WITHTEST)
  # 启用测试
  enable_testing()
  # 寻找GTest包.在`/usr/share/cmake-x.x/Modules/`目录有`FindGTest.cmake`模块文件
  find_package (GTest REQUIRED)
  # 包含GTest头文件目录
  include_directories (${GTEST_INCLUDE_DIRS})
  # 添加生成测试的可执行文件目标
  add_executable (FooTest FooTest.cc)
  # 生成测试可执行文件的链接库依赖,
  target_link_libraries (FooTest Foo ${GTEST_LIBRARY} pthread)
  # 添加测试,可通过`ctest`或`make test`运行,否则需要手动运行测试可执行文件
  add_test(NAMES FooTest COMMAND FooTest)
endif (WITHTEST)
```

注意,若测试文件不自己写`main`函数,则需要链接`libgtest_main.a`,对应的`${GTEST_MAIN_LIBRARY}`.

若两个都链接, 则可直接使用`${GTEST_BOTH_LIBRARIES}`

## Ubuntu

`Ubuntu`官方仓库提供有`GTest`的源文件包.

`sudo apt-get install libgtest-dev`, 会相对`Github`低一个小版本.

在`/usr/src/gtest`中保存有源代码,需要在其中自己编译生成静态库, 再自己复制到`/usr/lib`中.
