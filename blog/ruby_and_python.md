# ruby and python

1, 去掉字串两端的空格

py|rb
----|----
str#strip() | String#chomp


连接列表中的字串:

```python
l = ["a", "b", "c"]
# Py
",".join(l)  #=> "a,b,c"
# Rb
l.join(",")  #=> "a,b,c"
```

从列表中删除元素:

```python
l = ["a", "b", "c", "b"]
# Py
l.pop(1)  #=>["a", "c", "b"]
l.remove("b")  #=>["a", "c", "b"], 删除第一个b元素
# Rb
l.delete_at(1)  #=>["a", "c", "b"]
l.delete("b")  #=>["a", "c"], 删除所有b元素
```
