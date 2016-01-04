# tmux

## install

`github`官网: `https://github.com/tmux/tmux`

compile install tmux: 
* `sudo apt-get install libevent-dev`
* `./configure && make`
* `sudo make install`

## problem

to solve the vim's theme problem in tmux, the simplest way is to alias `tmux` to `tmux -2`

## TPM

tmux package manager:
* `git clone https://github.com/tmux-plugins/tpm ~/.tmux/plugins/tpm`
* configure your `.tmux.conf`, addd some plugins
* `$ tmux`
* use `prefix + I` to install new plugins, 
* use `prefix + U` to update plugins
* use `prefix + alt + U` to remove/uninstall plugins not on the plugin list
