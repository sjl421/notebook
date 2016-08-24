# active_record

对象关系映射: 提供类到表, 实例到记录的抽象. 不用关心底层使用的数据库类型.

使用前, 要`require 'active_record'`, 确保相应数据库的适配器已安装:
* `postgresql` 对应 `pg`

约定:
* 数据表名: 复数, 下划线分隔单词(如`book_clubs`)
* 模型类名: 单数, 每个单词首字母大写(如`BookClub`)

`ActiveRecord`能正确处理名词的复数形式, 如`Mouse`的复数`mice`, `Person`的复数`people`.

字段命名约定:
* 外键: 使用`singularized_table_name_id`, 即关联表名的单数形式加`_id`
* 主键: 使用整数`id`
* `created_at`: 创建记录时间戳
* `updated_at`: 更新记录时间戳

## 使用

建立连接, 指定适配器和数据库名, 可能包括用户名和密码:
```ruby
ActiveRecord::Base.establish_connection(
  adapter: 'postgresql',
  database: 'dbname',
  username: 'uname',
  password: 'passwd',
  host: 'localhost'
)
```

不同数据库, 需要的`key`不同. 如, 对`sqlite3`, 其数据库是文件, 且不需要用户和密码.

建立模型类:
```ruby
class Article < ActiveRecord::Base
end
```

只需要继承一下, `Article`类, 对应`articles`表, 其每个实例对应表中的一行记录.

1, 表的每个字段自动生成`#col_name`和`#col_name=`方法

创建模型类的新实例, `save`会保存到数据库中.

```ruby
p = Article.new(col1: v1, col2: v2)
p.save  # 保存到数据库
p.col1  #=> v1
p.col1 = v2  
```

```ruby
class Product < ActiveRecord::Base
  self.table_name = "product"  # 使用指定的表名, 而非约定表名
  self.primary_key = "product_id"  # 使用指定的主键名, 而非约定的id
end
```

## CRUD

```ruby
p.save  # 把记录存入数据库
Product.all  # 数据表的所有记录, 返回实例对象的数组
Product.first  # 返回第一条记录
Product.find_by(col_name: v1)  # 返回`col_name`列值为`v1`的第一条记录
Product.where(col1: v1, col2: v2).order('created_at DESC')
# 查询指定记录, 以`created_at`列降序排列
p.col = v  # 更新记录
p.update(col: v)  # 更新记录
Product.update_all("col1=v1, col2=v2")  # 更新所有的记录
p.destroy  # 删除记录
```

在数据存入数据库前, 可以进行数据验证:
```ruby
class User < ActiveRecord::Base
  validates :name, presence: true
end
User.create  #=> 返回false, 因为name字段非nil验证
User.create!  #=> 抛出异常
```

