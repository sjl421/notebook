# shell

## variable

environment variable: `$HOME`, `$PATH`.

```sh
file=lzp     #=> no space between the 'file', '=' and 'lzp'
echo "$file" #=> use the var by '$var'
$0...$9      #=> the cmdline args
$#           #=> the size of args
$$           #=> the PID of script
$*,$@        #=> all args strings
```

## condition

```sh
str1 = str2; str1 != str2;      #=> str1 equal/not equal str2
-n str; -z str;                 #=> str not empty/empty
expr1 -eq/-ne/-gt/-lt/-le expr2 #=> the comparison of expr1 and expr2
! expr                          #=> not zero
-d/f file                       #=> the file is dir or file
-g/u file                       #=> the file has setgit or setuid
-r/w/x file                     #=> the file is readable, writable, executable
-s file                         #=> file size is zero
```

## control structure

* `if` structure

```sh
if condition; then
  statement1
elif
  statement2
else
  statement3
fi
```

* `for` structure

```sh
for var in vals; do
  statements
done
```

* `while` structure

```sh
while condition; do
  statements
done
```

* `until` structure

```sh
until condition; do
  statements
done
```

* `case` structure

```sh
case var in
  pattern1 | pattern2 ) statement1 ;;
  pattern3 ) statement2 ;;
  *) statement3 ;;
esac
```

* `cmd1 && cmd2 || cmd3`: cmd1成功則执行cmd2, 否则cmd3
* `function` structure

```sh
func_name () {
  local var=value
}
func_name
```

* `exit n`: 退出码, 0表示成功

## command

* `break`
* `:`: 空命令,相当true别名
* `continue`
* `.`: 在当前进程执行脚本
* `echo`
* `eval expr`: 对表达式求值
* `exec cmd args`: 执行命令,终止当前shell
* `exit n`
* `export`
* `printf`
* `return`
* `set`
* `shift [n]`
* `trap cmd signal`
* `set/unset`
