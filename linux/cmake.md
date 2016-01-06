# cmake

```cmake
# 项目名
project(project_name)
# 最低版本要求
cmake_minimum_required(VERSION x.y.z)  
# 变量赋值
set(var value)  
# 把dir中所有源文件名称赋值给var
aux_source_directory(dir, var)
# 将var中的源文件编译生成可执行文件
add_executable(main ,${var})
# 将var中的源文件编译生成库
add_library(lib, ${var})
# 添加子目录
add_subdirectory(subdir)
# 指明程序需要链接库
target_link_libraries(main lib)
# 头文件路径
include_directories(head_file_path)
# 输出一条消息
message(STATUS "string")
# 在指定目录查找指定头文件,并将路径赋值给指定变量
find_path(var file.h dir1 dir2)
# 在指定目录查找指定库,并将路径赋值给指定变量
find_library(var libname dir)
```
