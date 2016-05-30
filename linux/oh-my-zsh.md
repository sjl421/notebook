# oh-my-zsh

确保已经安装正确的zsh版本, 并使zsh成为你的默认shell.

改变shell: `chsh -s $(which zsh)`

oh-my-zsh依赖curl或wget, git, 也要提前安装.

安装curl: `sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"`

安装wget: `sh -c "$(wget https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh -O -)"`

卸载: `$ uninstall_oh_my_zsh`

## 问题

oh-my-zsh在生成新的.zshrc时会统一PATH路径, 即`export PATH=...`, 但rvm更喜欢`export PATH=$PATH:...`, 然后它就总是报怨.

在zsh下, `which gem`会显示函数定义而非路径, 你可以使用`which -p gem`来显示路径. 或者添加别名.
