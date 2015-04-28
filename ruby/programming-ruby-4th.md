# programming ruby 4th

## part 1 - Facets of Ruby

### chapter 7 - Regular Expressions

Ruby正则匹配操作符`=~`,成功时返回匹配处的字符偏移量,失败返回`nil`.如`/cat/ =~ "dog and cat"  #=> 8`

Ruby使用`sub`方法替换找到的首个匹配字符,`gsub`则替换所有匹配字符.有相应的`sub!`和`gsub!`,不匹配时返回`nil`而非原字符串.

```ruby
str = "quick brown fox"
str.sub(`\b\w`, 'X')  #=> "Xquick Xrown Xox"
str.sub(`\b\w`) { |match| match.upcase }
str.sub(`\b\w`, &:upcase)  #=> "Quick Brwon Fox"
hash = { "quick" => 'a', "brown" => 'b', "fox" => 'c' }
str.sub(`\w+`, hash)  #=> "a b c"
```

Ruby有三种方式生成正则对象: `/cat/options`, `%r(cat)options`, `Regexp.new(cat <,options>)`.

Ruby的正则选项有`i`忽略大小写,`o`替换一次,`m`多行匹配,`x`扩展模式.

Ruby为string和regexp都提供了`match`方法,相当于`=~`.匹配成功返回`MatchData`对象,否则返回`nil`.

一旦匹配成功,`$&`引用匹配部分,`$\``引用匹配前部分,`$'`引用匹配后部分.或者调用`MatchData`对象的`[0]`,`pre_match`,`post_match`实现相应引用.

Ruby正则的特殊字符: `.`, `|`, `()`, `[]`, `{}`, `+`, `\\`, `^`, `$`, `*`, `?`.

```ruby
def show_regexp(string, pattern)
  match = pattern.match(string)
  if match
    "#{match.pre_match}->#{match[0]}<-#{match.post_match}"
  else
    "no match"
  end
end
```

Ruby的`\\A`匹配字符串头,`\\z`,`\\Z`匹配字符串尾.`\\b`匹配word的边界,而`\\B`相反.如匹配word的首字母,用`/\\b\\w`模式,先匹配word边界,再跟word字符.

Ruby可以为字符串指定字符集,`(?a)`为ASCII字符集,`(?u)`为Unicode字符集.如不同字符集对`\w`的解释不同,这决定了一个character是不是一个word.

`&&`可以连接多个匹配模式,如`/[a-z&&[^aeiou]]/`匹配所有不是元音字符的字符.

`\p`用于指定Unicode字符属性,如`\p{Alnum}`匹配字母和数字,`\p{Space}`匹配空白字符,`\p{Greek}`匹配希腊字母,`\p{Graph}`匹配可打印字符,`\p{Digit}`匹配数字.

`\\h`,`\\H`匹配十六进制字符, `\\R`匹配换行符, 

默认的`+`,`*`,`?`都是贪婪匹配,即匹配尽可能多的字符,但`+?`, `*?`,`??`则是懒惰匹配.

基于方法的引用: `MatchData`的`[0]`引用匹配部分,而`[1]`引用第一个匹配组, `[2]`类似.

基于位置的引用: 可以在当前匹配模式中引用可能匹配到的部分,如`show_regexp('He said "hello"', /(\w)\1/)  #=> He said "He->ll<-o"`, 如`show_regexp('Mississippi', /(\w+)\1/) #=> M->ississ<-ippi`.

基于名字的引用: 用`?<name>`为组`()`命名,用`\k<name>`引用命名的组.如`show_regexp('He said "Hello"', /(?<char>\w)\k<char>/)  #=> He said "He->ll<-o"`, 同上.

基于名字的引用可作为本地变量使用.

```ruby
/(?<hour>\d\d):(?<min>\d\d)(..)/ =~ "12:50am"
"Hour is #{hour}, minute #{min}"
```

## part 3 - Ruby Crystallized

### chapter 22 - The Ruby Language

#### Source File Encoding

#### Source Layout

每个Ruby源代码都可以声明`BEGIN`,`END`代码块.`BEGIN`块在程序载入时执行,`END`块在程序执行结束时执行.每个文件可以有多个这样的块,其中`BEGIN`块从上往下执行,`END`块相反.

Ruby有很多用于生成内置类型的简洁字面量表示:

* `%q`: single-quoted string
* `%Q`, `%`: double-quoted string
* `%w`, `%W`: array of strings
* `%i`, `%I`: array of symbols
* `%r`: regular expressing pattern
* `%s`: a symbol
* `%x`: shell command

注: `%Q`,`%I`,`%W`相比它们的小写版本,会对字符串进行演算,如变量替换`#{name}`,算术计算`#{1+2}`.

#### The Basic Types

Ruby基本类型:数字numbers, 字符串strings, 数组arrays, 哈希hashs, 范围ranges, 符号symbols, 正则regexps.


