# rails

## create application

```sh
rails new demo
```

## start rails server

```sh
rails server [webrick]
rails server -b ip:port  #监听指定主机的指定端口
```

## generate controller

```sh
rails generate controller Welcome index  # 生成控制器和其方法
```

## generate scaffold

```sh
rails generate scaffold Product title:string description:text image_url:string price:decimal
```

## database table

```sh
rake db:migrate
rake db:rollback
rake db:seed
rake db:migrate:status
```

## test

```sh
rake test
rake test:models
rake test:controllers
```

## route

```sh
rake routes
```

## log

```sh
rake log:clear LOGS=test
```
