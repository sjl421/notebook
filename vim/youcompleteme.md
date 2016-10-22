# YouCompleteMe

```sh
mkdir ycm_build && cd ycm_build
cmake -G "Unix Makefiles" -DPATH_TO_LLVM_ROOT=path_to_clang_dir . ~/.vim/bundle/YouCompleteMe/third_party/cpp
cmake --build . --target ycm_core
```
