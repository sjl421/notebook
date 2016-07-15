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
make ycm_support_libs
```

## vimproc

在`~/.vim/bundle/vimproc.vim`中执行`make`命令或在vim中执行`VimProcInstall`命令.
