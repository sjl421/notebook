# Mono

`Mono`是`.NET`的跨平台运行时, 主要支持`C#`语言在多平台的运行.

## 安装

`MAC`: `brew install mono`

`Ubuntu`:

```sh
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF
echo "deb http://download.mono-project.com/repo/ubuntu xenial main" | sudo tee /etc/apt/sources.list.d/mono-official.list
sudo apt-get update
sudo apt-get install mono-devel  # mono-complete完整安装
```

## 使用

1, 编辑`hello.cs`

```cs
using System;

public class HelloWorld
{
    static public void Main ()
    {
        Console.WriteLine ("Hello Mono World");
    }
}
```

2, 编译: `mcs hello.cs`, 生成`hello.exe`

3, 运行: `mono hello.exe`.
