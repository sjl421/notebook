# irssi

configure file: `~/.irssi/config`, `~/.irssi/scripts/autorun/*.pl`

## Usage

* `/<cmd> [arguments]`: execute internal command
* `/connect irc.freenode.net 6667 nickname:passwd`: connect server
* `/disconnect`: disconnect server
* `/msg NickServ register passwd email`: register account
* `/join #python`: join channel
* `/q <nick>`
* `/wc`: leave current window
* `<Alt-1..0>`: switch windows

## Set

* `/set timestamp_format %d>%H:%M:%S`: set time's format

## Concept

a network contains many channel in serval server.

* `/server add -network freenode irc.freenode.net`: add server to network
* `/network add -autosendcmd "<cmd>" freenode`: run cmd when connect network
* `/connect freenode`: use network not server to connect
* `/network list`: list network
* `/server list`: list server
