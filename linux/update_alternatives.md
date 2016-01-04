# update-alternatives

Debian的linux发行版，提供了`update-alternatives`工具，用于在多个同功能的软件，或软件的多个不同版本间链接到正确的位置。

```sh
ll /usr/bin/vim
#=> /usr/bin/vim -> /etc/alternatives/vim
ll /etc/alternatives/vim
#=> /etc/alternatives/vim -> /usr/bin/vim.basic
ll /usr/bin/vim.basic
#=> /usr/bin/vim.basic
```

也就是说，真正的程序是`/usr/bin/vim.basic`，而我们在shell中键入`vim`会先找到`/usr/bin/vim`,在找到`/etc/alternatives/vim`，再找到`/usr/bin/vim.basic`。

`update-alternatives`是某种类似创建符号链接的工具。在shell命令与真正的执行程序间加入匹配层。

比如，安装Oracle Java,在设置`JAVA_HOME`的环境变量并加入`PATH`环境变量后,并不能正常启动eclipse.

```sh
sudo update-alternatives --install /usr/bin/java java $JAVA_HOME/bin/java 100
sudo update-alternatives --remove java $JAVA_HOME/bin/java
```

上述命令完成建立`/usr/bin/java`到`/etc/alternatives/java`再到`$JAVA_HOME/bin/java`符号链接的建立.

## Usage

install: `sudo update-alternatives --install link name path priority [ --slave slink sname spath]`

* `link`是在`/usr/bin/`,`/usr/local/bin/`等默认`PATH`搜索目录
* `name`是在`/etc/alternatives`目录中的链接名
* `path`是真正的可执行程序的位置,可以在任何位置
* `priority`是优先级

display: `sudo update-alternatives --display name`

config: `sudo update-alternatives --config name`

remove: `sudo update-alternatives --remove name path`

因为优先级的存在,可以为同一个`name`设置多个`path`,当删除一个`path`后,默认连接到`slave`上.

remove all: `sudo update-alternatives --remove-all name`
