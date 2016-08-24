# spacemacs

一个基于`emacs`二次开发的社区发行版. 提供了`emacs`和`vim`不同的键绑定.

## Install

```sh
$ brew tap d12frosted/emacs-plus
$ brew install emacs-plus --with-cocoa --with-gnutls --with-librsvg 
--with-imagemagick --with-spacemacs-icon
$ brew linkapps emacs-plus
```

换源, 使用清华源, 在`~/.spacemacs`文件中写入下列内容:
```lisp
(defun dotspacemacs/user-init ()
  (setq configuration-layer--elpa-archives
    '(("melpa-cn" . "http://mirrors.tuna.tsinghua.edu.cn/elpa/melpa/")
      ("org-cn"   . "http://mirrors.tuna.tsinghua.edu.cn/elpa/org/")
      ("gnu-cn"   . "http://mirrors.tuna.tsinghua.edu.cn/elpa/gnu/")
     )
  )
)
```

`spacemacs`创建了称为`layer`的配置单元, 上述代码的意思是, 在`dotspacemacs/user-init`
配置`layer`中, 设置变量`configuration-layer--elpa-archives`, 此变量用于配置包源.

## Usage

可以通过`emacs --daemon`开启服务器, 此后启动`emacsclient`会非常迅速.

添加以下两个别名:
```sh
alias et='emacsclient -t'  # open the terminal
alias ec='emacsclient -c'  # open the gui
```

但只能通过`emacsclient`才能连接已经开启的服务器, 但直接通过`alfred`启动的应用无法
使用.
