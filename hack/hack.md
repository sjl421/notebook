# 碎碎念

在`ipython`环境中, `obj.method??`则可以查看方法帮助.

## 逆向

1, 先用`PEid`查看下执行文件是什么类型的.

2, `.net`程序, 可以用`.NET Reflector`反编译得到`c#`代码. `csharp`可以打开`c# REPL`进行交互式探索.

## 文件

`.vmdk`文件用`DiskGenius`打开

## 脱壳

在用`IDA`文件打开时, 汇编指令前面写着`UPX`, 表示是`UPX`壳:
```sh
upx.exe abc.exe  # 给程序加壳
upx.exe -d abc.exe  # 给程序脱壳
```

## 加密

1, `md5`: 可以加盐, 即在`origin_text`后面再加字串, 特征: 32位小写字母和数字混合

md5加密后的字符串里的字符都是16进制, 即只包含`0-9`和`a-f`.

加密:
```py
from hashlib import md5
md5(b"origin_text").hexdigest()  #=> encrypt_text
```

2, `DES`, `pip install pyDes`

```
import pyDes
d = pyDes.des(b"key_str", pyDes.CBC, b"iv_str", pad=None, padmode=pyDes.PAD_PKCS5)  # key/iv都为8字节
d.encrypt(b")  # 加密
d.decrypt(b"encrypt_str")  # 解密
```

3, `base64`

```sh
import base64
base64.b64encode(b"origin_text")  # 加密
base64.b64decode(b"encrypt_text")  # 解密
```



