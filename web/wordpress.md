# wordpress

wordpress是一个个人信息发布平台。

phpmyadmin是一个web界面的mysql数据库处理平台。事实上，你可以通过命令行来管理你的数据库。

apache2默认`/var/www/html/`为网站根目录，事实上安装好apache2后，在浏览器打开`localhost`后得到的页面即`/var/www/html/index.html`内容。

install : `sudo apt-get install apache2 mysql-server phpmyadmin`

get the wordpress's tar from website and extract to anywhere, then make a link to `/var/www/html` dir: `ln -s /path/to/wordpress/ /var/www/html/`

create a database and dbuser: 

* create database: `create database wpdb;`
* create dbuser: `insert into mysql.user(Host, User, Password) values("localhost", "wpuser", password("passwd"));`
* grant privileges: `grant all privileges on wpdb.* to wpuser@localhost identified by "passwd";`

then open the url `localhost/wordpress` in your browser.

write your dbname and dbuser and dbpasswd, then write content to `wp-config.php`. 

OK! you get the wordpress.
