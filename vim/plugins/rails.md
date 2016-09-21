# rails for vim

用于`Rails`开发的`vim`插件.

## 命令

`:Rails new dir_name`: 生成新项目, 问题是`Gemfile`中的源导致没法`bundle install`
成功.

`:Rlog`: 打开项目日志, 默认`development.log`文件

`:Rpreview [path]`: 在浏览器中打开路径, 如`:Preview /users`, 不能使用具名路由,
默认根路径.

`:Rpreview! [path]`: 在`vim`中打开, 也就是获取网页源码, 在`vim`中以文本形式打开.

`:Cd [{directory}]`: 在项目目录中切换

真正NB的命令是`gf`, 任何暗示文件的代码都可以直接切换. 如:
* 模型文件中声明模型关系的: `has_many :comments`, 会定向到`app/models/comment.rb`
* 视图文件中的链接标签: `link_to 'Home', edit_user_path(@user)`, 会定向到控制器
* 渲染局部视图: `render 'new'`, 会定向到`app/views/new.html.erb`
