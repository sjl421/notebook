# sqlite3

## Usage

打开数据库:
```ruby
db = SQLite3::Database.new "dbname"
```

执行`SQL`语句:
```ruby
db.execute "sql_stat"
db.execute "insert into students (name, email, grade, blog) values (?, ?, ?, ?)", ["Jane", "me@janedoe.com", "A", "http://blog.janedoe.com"]
```

对查询的行迭代:
```ruby
db.execute("select * from students") do |row|
  p row
end
```
