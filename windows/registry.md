# 注册表

可视化编辑器：`Win-R` -> `regedit`

命令行： `reg [/?]`

    HKLM：HKEY_LOCAL_MACHINE，是机器软硬件信息的集散地
    HKCU：HKEY_CURRENT_USER，是当前用户所用信息储存地
    HKCR：HKEY_CLASSES_ROOT：文件关联相关信息
    HKU： HKEY_USERS：所有用户信息
    HKCC: HKEY_CURRENT_CONFIG：当前系统配置

## 导出

* 帮助：`reg export /?`
* `reg export keyname filename.reg /y`

`keyname`一般为`ROOTKEY[\SubKey]`,而`ROOTKEY`只有5个类型`HKLM`,`HKCU`,`HKCR`,`HKU`,`HKCC`. /y`表示不提示覆盖同名文件

## 导入

* 帮助：`reg import /?`
* `reg import filename.reg`

## 添加

* 帮助： `reg add /?`
* `reg add keyname [/v valuename | /ve] [/t type] [/s separator] [/d data] [/f]`

注册表都是键值对，`/v`指定键，`/d`指定值，`/t`指定值类型，有`REG_SZ`字符串,`REG_MULTI_SZ`多字符串,`REG_EXPAND_SZ`,`REG_DWORD`双字节值,`REG_QWORD`四字节值,`REG_BINARY`二进制数据,`REG_NONE`.`/ve`表示键为默认键，`/s`指定`REG_MULTI_SZ`时的分隔符,`/f`覆盖同名键值对。

## 删除

* 帮助： `reg delete /?`
* `reg delete keyname [/v valuename | /ve | /va] [/f]`

`/va`表示删除该项下所有键值对。

## 查询

## 