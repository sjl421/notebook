# lamp

* install apache2: `sudo apt-get install apache2`
* install mysql: `sudo apt-get install mysql-client mysql-server`
* install php5: `sudo apt-get install php5`
* install phpmyadmin: `sudo apt-get install phpmyadmin`

link: `ln -s /usr/share/phpmyadmin /var/www/html/`

uncomment `/etc/php5/apache2/php.ini`: `mysqli.allow_local_infile=on`

restart apache2: `sudo /etc/init.d/apache2 restart`

then you can open `localhost` and `localhost/phpmyadmin` url in your browser.

打开`localhost/phpmyadmin`链接,用户`root`密码是mysql根用户的密码.
