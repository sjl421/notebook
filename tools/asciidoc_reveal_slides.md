# asciidoctore-reveal.js

制作幻灯片,使用`asciidoc`标记语言和`reveal.js`库.

```sh
$ gem install asciidoctor tilt thread_safe
$ gem install slim --version 2.1.0
$ git clone git://github.com/asciidoctor/asciidoctor-reveal.js.git
cd asciidoctor-reveal.js
# 编辑.adoc文件
$ asciidoctor -T templates/slim file.adoc  # 使用模板生成HTML
# 或使用构建脚本,如rake,make等
$ git clone -b 3.0.0 git://github.com/hakimel/reveal.js.git
```

Example:
```asciidoc
= Title Slide

:source-highlighter: pygments

== Slide One

* Foo
* Bar
* World

== Slide Two

Hello World - Good Bye Cruel World

[NOTE.speaker]
--
Actually things aren't that bad
--

[data-background="yellow"]
== Slide Three

Is very yellow

== Slide Four

[%step]
* this
* is
* revealed
* gradually

== Slide Five

Uses highlighted code

[source,ruby]
----
put "Hello World"
----

== Slide Six

Top slide

=== Slide Six.One

This is a vertical subslide

== !
This is a slide with hidden title
```

## asciidoctor

生成`HTML`: `asciidoctor aaa.adoc`
生成`pdf`: `asciidoctor-pdf aaa.adoc`
生成`epub`: `asciidoctor-epub3 aaa.adoc`
生成`mobi`: `asciidoctor-epub3 -a ebook-format=kf8 aaa.adoc`,依赖`kindlegen`包
生成`latex`: `asciidoctor-latex aaa.adoc`

## more

声明图片文件夹,文档包含,高亮引擎,目录
```asciidoc
= Title
author ( @nickle ) <aaa@example.com>
:imagesdir: images
:source-highlighter: hightlightjs
:toc:

include::subdir/aaa.adoc[]
```

图片,可指定宽度, 音视频
```asciidoc
image::aaa.png[width=xxx]
audio::path/to/audio.ogg[]
video::path/to/video.ogg[]
```

引用
```asciidoc
[quote, 引用作者, 引用内容]
____
bbbbbbbbbbbbbbbbbbbb
____
```

源代码,可标注
```asciidoc
.title
[source,ruby]
----
puts 'hello,world' # <1>
----
<1> xxxxxxx
```

链接
```asciidoc
url[text]
```

换行
```asciidoc
oneline +
twoline
# paragraphs attribute
[%hardbreaks]
oneline
twoline
# global attribute
= Title
:hardbreaks:
```

脚注
```asciidoc
aaaaaaaaaaaaa footnote:[bbbbbbbb]
```

提示,重要,警告,确认
```asciidoc
TIP: xxxx
IMPORTANT: xxx
WARNING: xxx
CAUTION: xxx
```

注释,按`s`键可显示本幻灯片的注释
```asciidoc
[NOTE.speaker]
--
注释内容
--
```
