## psycopg2

`psycopg2`是`python`对`postgresql`数据库的连接驱动.

按pg2的文档, 可以自动进行python对象到sql类型的匹配, 那么如果rpc能完成python对象的序列及反序列化, 则pg2的插入会方便地多

默认情况, 存数据/读数据都会自动地进行python对象与sql类型间的转化, 这种转化某种程序可以自定义.

一个连接就是一个事务, 在一个连接中创建的所有cursor, 其执行的所有命令都是在这个事务中, 只要有一个命令失败, 则事务将被丢弃, 在执行rollback前, 不能再执行命令. 终止事务可用commit/rollback, 一个是成功, 一个是失败. close连接时会隐式地rollback.

尽可能快地关闭事务.

指定格式为csv后, 默认的`null`为空, 而`''`则为空字串. 格式为text, 默认的`null`为`'\N'`. 

csv的分隔符为`,`, text的分隔符为`tab`.

在插入数据时, pg2会将python对象通过adapter转化为sql类型的字串, adapters存储了所有的转化关系
在读取数据时, pg2会将sql类型的字串通过types转化为python对象, string_types存储了所有的转化关系
这个过程用户都可以参与, 即可定义python对象与sql类型之间的转换

