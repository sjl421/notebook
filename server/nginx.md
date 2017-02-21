# nginx

`nginx`有一个主进程和多个工作进程,主进程负责配置的读取和执行,工作进程负责处理请求.

`nginx`使用基于事件的模型和`OS`独立的机制在多个工作进程间有效地分发请求.

工作进程数目可设定为固定数,也可认定为`auto`,通常调节为可用的`CPU`数.

默认情况下,配置文件为`/etc/nginx/nginx.conf`.

日志文件`/var/log/nginx/*.log`.

`pid`文件`/run/nginx.pid`.

## 安装

`sudo apt install nginx`, 可禁止开机启动: `sudo systemctl disable nginx`

## 控制

`sudo nginx`启动进程

`sudo nginx -s signal`启动进程后通过`-s`发送信号来控制进程.有`stop/quit/reload/
reopen`4个信号.

`quit`信号会在工作进程处理完当前请求后自动退出.

`reload`用于重新载入配置.当配置更新后,`nginx`先检查配置的正确性,若正确才向旧工作
进程发送退出信号,并启用新的工作进程.

## 配置文件

`nginx`包含多个模块,由配置文件中的命令控制.命令分为简单命令和块命令,简单命令就是
名称+空格分开的多个参数,以`;`结尾,块命令则在以`{...}`替换`;`,并在`{}`中继续写入
多个命令.

`events`和`http`在`main`的上下文中,`server`在`http`中,`location`在`server`中.

## 静态文本

```nginx
server {
  location / {
    root /data/www;
  }

  location /images/ {
    root /data;
  }

  location ~ \.(gif|jpg|png)$ {
    root /data;
  }
}
```

以上配置, 省略`listen 80;`,因为默认情况下,特权启动`nginx`会监听`80`端口,非特权启动
监听`8000`端口.

`location`有点类似路径匹配,如`localhost`会匹配到`/`目录,此时去`/data/www`本地目录
寻找默认的`index.html`文件.而`localhost/images/xxx.jpg`则匹配`/images/`目录,此时去
`/data`目录找`images/xxx.jpg`文件.

`~`开头表示进行正则匹配,此处表示对照片类型的限制,如对`localhost/yyy/xxx.jpg`则匹配
`/data`目录下的`yyy/xxx.jpg`文件,非允许后缀不匹配.

最长匹配优先,正则匹配最后.

在基础的静态文本服务下,就是将一个个请求`URI`解析为对本地目录中特定文件的请求,即将
`URI`映射为`path`路径.

## 代理服务

所谓代理,就是`nginx`将请求传递给被代理的服务器,再将被代理服务器的返回传递给客户端.

先设置一个代理服务:
```nginx
server {
  listen 8080;
  root /data/up1;

  location / {
  }
}
```

此处设置一监听`8080`端口的服务,当`root`位于`server`中而非`location`,可理解为默认
目录.即不匹配所有`location`时的默认目录.

```nginx
server {
  location / {
    proxy_pass http://localhost:8080;
  }

  location /images/ {
    root /data;
  }
}
```

匹配`/`的请求被代理到`8080`,匹配`/images/`的请求直接处理.

## FastCGI代理

```nginx
server {
  location / {
    fastcgi_pass  localhost:9000;
    fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
    fastcgi_param QUERY_STRING    $query_string;
  }

  location ~ \.(gif|jpg|png)$ {
    root /data/images;
  }
}
```

这是适用于`PHP`的设置,但别的语言类型,即用`fastcgi_pass`代理,用`fastcgi_param`传递
参数.

## 命令

`server_name`: `nginx`在处理请求时,会接收到用户的完整`URI`,如`www.baidu.com`,这个
服务器名称也是可以处理的,常常用来进行正则匹配并设置捕获组,以方便在一个`server`中
同时配置多个服务器.

```nginx
server {
  server_name ~ ^(www\.)?(.+)$;
  root /data/wwwsite/$2;
}
```

从而可以将`www.a.com`匹配到`/data/wwwsite/a.com`;将`www.b.com`匹配到`/data/wwwsite
/b.com`.也可以使用命名捕获组`(?<name>.+)$`,使用`$name`.

默认情况下,如果访问路径没有`index`命令指定的文件,则会返回错误信息,但另一时候,可能
想要显示当前访问路径下的目录,可在`location`块中`autoindex on;`打开目录浏览.`auto
index_exact_size off`使用`K/M/G`表示文件大小,`autoindex_localtime on`使用服务器
本地时间.

## https

`https`会增加服务器的`cpu`资源消耗. 费力的操作是`SSL`握手. 有两种操作可减少每个
客户端与服务器的握手次数: 一种是允许`keepalive`, 这样在一个连接中可以发送多个请求,
另一种是重用`SSL`会话参数来避免并发和后续的连接.

```nginx
http {
  ...
  ssl_session_cache  shared:SSL:10m;  #设置会话缓存10分钟, 可在多worker共享
  ssl_session_timeout  10m;  #设置会话超时10分钟
  ...
  server {
    ...
    listen              443 ssl;
    keepalive_timeout   70;
    ssl_certificate     /etc/nginx/certs/cert.pem;  #指定证书
    ssl_certificate_key /etc/nginx/certs/privkey.pem;  #指定私钥
    ...
  }
}
