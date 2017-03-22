# vim-plug

`vim`插件管理器, 可并发安装, 超级快.

安装: 
```sh
curl -fLo ~/.vim/autoload/plug.vim --create-dirs 
https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim
```

简单地说, 就是下载`plug.vim`到本地目录`.vim/autoload/plug.vim`.

编辑`.vimrc`文件:

```vim
" Specify a directory for plugins (for Neovim: ~/.local/share/nvim/plugged)
call plug#begin('~/.vim/plugged')

" Make sure you use single quotes

" Shorthand notation; fetches https://github.com/junegunn/vim-easy-align
Plug 'junegunn/vim-easy-align'

" 任何git链接都行
Plug 'https://github.com/junegunn/vim-github-dashboard.git'

" 多插件的单行写法
Plug 'SirVer/ultisnips' | Plug 'honza/vim-snippets'

" On-demand loading
Plug 'scrooloose/nerdtree', { 'on':  'NERDTreeToggle' }
Plug 'tpope/vim-fireplace', { 'for': 'clojure' }

" 可指定分支
Plug 'rdnetto/YCM-Generator', { 'branch': 'stable' }

" 可指定标签
Plug 'fatih/vim-go', { 'tag': '*' }

" Plugin options
Plug 'nsf/gocode', { 'tag': 'v.20150303', 'rtp': 'vim' }

" Plugin outside ~/.vim/plugged with post-update hook
Plug 'junegunn/fzf', { 'dir': '~/.fzf', 'do': './install --all' }

" Unmanaged plugin (manually installed and updated)
Plug '~/my-prototype-plugin'

" Initialize plugin system
call plug#end()
```
