# ruby's regexp

只匹配`abc`, 不匹配`-`, 也不匹配前后的` `空格.

```ruby
s = "abc abcdef abc abc abc-def def-abc def-abc-def"
s.gsub(/(?<=\s|^)abc(?=\s|$)/, 'xxx')
#=> "xxx abcdef xxx xxx abc-def def-abc def-abc-def"
```

说明:
* `?=`表示, 匹配, 但不计算在匹配内
* `\s|$`, 因为`abc`处理字串头, 则前无空格, 处于字串尾, 则后无空格
