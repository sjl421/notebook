# YouCompleteMe

```sh
mkdir ycm_build && cd ycm_build
cmake -G "Unix Makefiles" -DPATH_TO_LLVM_ROOT=path_to_clang_dir . ~/.vim/bundle/YouCompleteMe/third_party/cpp
cmake --build . --target ycm_core
```

如何运行`cmake`提示`third_party/cpp`目录不存在, 则往往是未`git clone`完全, 需要再
执行`git submodule update --init --recursive`.
