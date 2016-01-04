# emacs plugins

## company

* 安装：`M-x package-install <ret> company <ret>`
* init.el
  * `(add-hook 'after-init-hook 'global-company-mode)`
  * `(global-set-key [tab] 'company-complete)`

Usage:

* `M-n`, `M-p` select
* `M-digit` select nth complete
* `<f1>` display the doc of selected candidate
* `C-w` see its source

## elscreen

* `M-x package-install <ret> elscreen <ret>`
* `(elscreen-start)`

Usage:

* `C-z c` create a new screen and switch to it
* `C-z k` kill current screen
* `C-z M-k` kill current screen and buffers
* `C-z K` kill other screens
* `C-z n` switch next screen in cyclic order
* `C-z p` switch prev screen in cyclic order
* `C-z '` prompt for a screen number to switch to
* `C-z "` present a list of all screens for selection
* `C-z 0..9` jump to the screen number 0-9
* `C-z C-s` swap current screen with prev one
* `C-z A` rename current screen
* `C-z w` show a list of screen
* `C-z t` show timestamp
* `C-z b` switch to the specified buffer's screen
* `C-z M-x` call function in new screen
* `C-z i` show/hide the screen number in the mode line
* `C-z T` show/hide the tab on the top of each frame
* `C-z ?` show key bindings of elscreen

## graphviz

* 安装：`M-x package-install <ret> graphviz-dot-mode <ret>`

Usage:

* 编译：`C-c c`
* 预览：`C-c p`
* 注释：`M-;`

## neotree

* 安装：`M-x package-install <ret> neotree <ret>`
* 快捷键：`global-set-key (kbd "C-c f") 'neotree-toggle)`

Usage:

* `n` next line, `p` previous line
* `spc` fold/unfold current item
* `g` refresh
* `A` max/min neotree window
* `H` toggle display hidden files
* `C-c C-n` create a file/directory
* `C-c C-d` delete a file/directory
* `C-c C-r` rename a file/directory
* `C-c C-c` change the root directory

## emmet

* 安装：`M-x package-install <ret> emmet-mode <ret>`
* init.el：
  * `(add-hook 'sgml-mode-hook 'emmet-mode)`
  * `(add-hook 'css-mode-hook 'emmet-mode)`
  * `(add-hook 'emmet-mode-hook (lambda () (setq emmet-indent-after-insert nil)))`
  * `(add-hook 'emmet-mode-hook (lambda () (setq emmet-indentation 2)))`  设置缩进
  * `(setq emmet-move-curcor-between-quotes t)`

Usage:

* `C-j` expand, `M-x emmet-expand-line`
* `M-C-left` previous edit point, `M-x emmet-next-edit-point`
* `M-C-right` next edit point, `M-x emmet-prev-edit-point`

## windmove

* init.el
  * `(windmove-default-keybindings)`, use `Shift+arrows` to move windows

## switch-window

* 安装：`M-x package-install <ret> switch-window <ret>`
* init.el
  * `(global-set-key (kbd "C-x o") 'switch-window)`, use digit number to select windows

## ace-jump

* `M-x package-install <ret> ace-jump <ret>`
* init.el
  * `(global-set-key (kbd "C-c SPC") 'ace-jump-mode)`
