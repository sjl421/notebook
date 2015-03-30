## create application

```sh
$ rails new demo
```

## start rails server

```sh
$ rails server [webrick]
```

## generate controller

```sh
$ rails generate controller Welcome index
```

## generate scaffold

```sh
$ rails generate scaffold Product title:string description:text image_url:string price:decimal
```

## database table

```sh
$ rake db:migrate
$ rake db:rollback
$ rake db:seed
$ rake db:migrate:status
```

## test

```sh
$ rake test
$ rake test:models
$ rake test:controllers
```

## route

```sh
$ rake routes
```

## log

```sh
$ rake log:clear LOGS=test
```
