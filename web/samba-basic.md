# samba

install: `sudo apt-get install samba smbclient`

backup conf and and some text to conf: `sudo cp /etc/samba/smb.conf /etc/samba/smb.conf.bk`

content: 
```
[share]
  path = /to/your/share/dir
  browseable = yes | no
  public = yes | no
  writable = yes | no
  available = yes | no
```

restart server: `sudo smbd restart` or `sudo /etc/init.d/samba restart`

test success or not: `smbclient -L //localhost/share`
