# programming ruby 4th

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
