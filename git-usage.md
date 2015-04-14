# git usage

文件参考: [progit中文][progit@OSC]

## basic

* workdir中的文件有三种状态:modified, staged, commit, 可以通过 `$ git status`查看
* git配置文件:`/etc/gitconfig`, `~/.gitconfig`, `workdir/.git/config`
* new file处于untracked的状态，通过`$ git add file`添加到staged
* `.gitignore`文件配置无需提交的文件，支持shell通配符
* git支持3种协议：`git`, `http(s)`, `ssh`
* `$ git clone url`相当`$ git checkout -b master origin/master`
* 当处理跟踪分支时，执行pull和push, git知道对哪个分支操作

~~~ sh
mkdir enalog
cd enalog
git init
git add hello.txt #(staged, not commit)
git status
git commit [-a] -m "c1" #modified -> commit
git remote add origin https://github.com/enali/enalog
git push origin master
git clone https://github.com/torvalds/linux [localdir]
~~~

## file

~~~ sh
git diff                 # diff between modified and staged
git diff --cached        # diff between staged and commit
git rm file              # remove from staged area and workdir
git rm --cached file     # but file will not delete
git mv file_from file_to
git commit --amend       # cancel last commit
git reset HEAD file      # cancel staged file
git checkout -- file     # cancel modified file
~~~

## config && alias

~~~ sh
git config --global user.name "enali"
git config --global user.email enalix@163.com
git config --global user.signingkey pubID
git config --global core.editor vim
git config --global merge.tool vimdiff
git config --global alias.co checkout
git config --global alias.ci commit
git config --global alias.unstage 'reset HEAD --'
git config --global alias.last 'log -1 HEAD'
~~~

## branch

* 一般项目会有长期分支和特性分支
* 衍合分支, 与合并结果相同，但会删除衍合分支, 若分支已发布到公共仓库，禁止对分支衍合
* 远程分支(远程仓库名／分支名)

~~~ sh
git branch -v -a --merged --no-merged        # 查看
git branch newbr && git checkout newbr       # 新建分支并切换
git checkout -b newbr
git merge newbr                              # 合并分支
git rebase 基底分支 衍合分支                 # or
git checkout 衍合分支 && git rebase 基底分支
git rebase --onto master server client       # 将client基于server的变化在master衍合
git branch -d newbr                          # 删除分支
git mergetool                                # 冲突分支处理
git push --delete origin br                  # 删除远程分支
git push origin :br
~~~

## remote

~~~ sh
git remote [-v]
git remote show remote-name
git remote add shortname url
git remote rename shortname1 shortname2
git remote rm shortname
git pull                                # equal: git fetch origin && git merge origin/master
git push origin localbr:remotebr        # if localbr == remotebr-name
git push origin br
~~~

## log

~~~ sh
git log -p -2
git log --stat --pretty=online|"%h - %an, %ar : %s"  --graph
git log --pretty="%h - %s" --author=gitster --since="2008-10-01" \
  --before="2008-11-01" --no-merges -- t/
~~~

## tag

~~~ sh
git tag
git tag -l 'v1.4.2.\*'
git tag tagname                    # lightweight tag
git tag -a|s v1.3 -m 'version 1.3' # (-s: signed) annotated tag
git tag -v tagname                 # verify tag
git show v1.3
git push origin tagname            # push tag to remote
git push origin --tag
~~~

[progit@OSC]: https://git.oschina.net/progit
