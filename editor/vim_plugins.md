# vim plugins

## syntastic

* css syntax checker: csslint
  * `$ npm install -g csslint`
* coffee-script syntax checker: coffee
  * `$ npm install -g coffee-script`
* json syntax checker: jsonlint
  * `$ npm install -g jsonlint`
* markdown syntax checker: mdl
  * `$ gem install mdl`
* python syntax checker: flake8
  * `$ pip install flake8`
  * `$ pip3 install flake8`
* sass syntax checker: sass
  * `$ gem install sass`
* yaml syntax checker: js-yaml
  * `$ npm install -g js-yaml`
* ruby syntax checker: mri
* latex syntax checker: chktex
  * `$ apt-get install chktex`

## Eclim

* graphic install: `$ java -jar eclim-<version>.jar`
* start `eclimd`: `$ $ECLIPSE_HOME/eclimd`

## wmgraphviz

* install graphviz and dot2tex: `$ sudo apt-get install graphviz graphviz-dev dot2tex`

## youcompleteme

* install:

```sh
mkdir ycm_build && cd ycm_build
cmake -G "Unix Makefiles" -DPATH_TO_LLVM_ROOT=~/ycm_temp/llvm_root_dir . ~/.vim/bundle/YouCompleteMe/third_party/ycmd/cpp
cmake --build . --target ycm_core
```

注意`cmake`时会声明此次编译使用的`python`库和`python`程序. 而如果这个不同,会导致`vim`运行时`python`崩溃.
特别是对于`mac`平台,除了系统自带的旧的`python`版本,还有`brew`安装的不同`python`版本,还有`conda`安装的
`python`版本,总之非常混乱,且库和程序不能混用. 在`cmake`配置时,可通过`-DPYTHON_EXECUTABLE=/path_to_py`和
`-DPYTHON_LIBRARY=/path_to_lib`来指定`python`的程序和库路径,然后还需要在`.vimrc`中指定`let g:ycm_server
_python_interpreter=/path_to_py`来指定使用的`python`版本.

有关`python`的库, `brew`的安装位置`/usr/local/opt/python/Frameworks/Python.framework/Versions/Current/
lib/libpython2.7.dylib`.

如果使用系统的`llvm`, 可用`-DUSE_SYSTEM_LIBCLANG=ON`.

另一个要注意的地方, 关于`macvim`可能出现的`the Python's site module could not be loaded.`问题,要确保`macvim`
编译时使用的`python`和`youcomplete`编译时使用的`python`以及`ycm_server`运行时使用的`python`. 对这3个`python`
要保持一致,至少保持兼容. 

支持`c#`:

安装`Mono`:
```sh
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF
echo "deb http://download.mono-project.com/repo/debian wheezy main" | sudo tee /etc/apt/sources.list.d/mono-xamarin.list
sudo apt update && sudo apt install mono-complete
```



## vimproc

在`~/.vim/bundle/vimproc.vim`中执行`make`命令或在vim中执行`VimProcInstall`命令.
