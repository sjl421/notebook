# 字符串

## 删除

```sh
STR="enali.github.io"
echo ${STR/i}     ->"enal.github.io"
echo ${STR//i}    ->"enal.gthub.o"
echo ${STR%.*}    ->"enali.github", cut from end, lazy
echo ${STR%%.*}   ->"enali", cut from end, greedy
echo ${STR#*.}    ->"github.io", cut from head, lazy
echo ${STR##*.}   ->"io", cut from head, greedy
```

## 替换

* `${STR/old/new}`, lazy
* `${STR//old/new}`, greedy
* `${STR/#head/new}`, just head
* `${STR/%end/new}`, just end

```sh
STR="Hello World"
echo ${STR/o/O}   ->"HellO World"
echo ${STR//o/O}  ->"HellO WOrld"
echo ${STR/#He/he}->"hello world"
echo ${STR/%ld/LD}->"Hello worLD"
```

## sed

```sh
echo $STR | sed "s/old/new/"
```

## tr

* 字符集合的替换

```sh
echo "bash" | tr "[a-z]" "[b-z]"  ->"cbti", a-b, b-c, s-t, h-i
echo "GMT+8-9" | tr "+-" "-+"     ->"GMT-8+9
```

## path

* dirname `${FULLPATH}`
* basename `${FULLPATH}`
* basename `${FULLPATH}${EXT}`

```sh
FULLPATH=/usr/work/project/backup.tar.gz
dirname "$FULLPATH"     ->/user/work/project
basename "$FULLPATH"    ->backup.tar.gz
basename "$FULLPATH".gz   ->backup.tar
basename "$FULLPATH".tar.gz   ->backup
```
