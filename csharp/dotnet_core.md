# .NET Core

## 安装

`Windows`: 下载安装[.NET Core SDK](https://www.microsoft.com/net/core#windowscmd)

`Ubuntu`:

```sh
sudo sh -c 'echo "deb [arch=amd64] https://apt-mo.trafficmanager.net/repos/dotnet-release/ xenial main" > /etc/apt/sources.list.d/dotnetdev.list'
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 417A0893
sudo apt-get update
```

## Usage

```sh
dotnet new console -o hwapp  # 创建终端项目
cd hwapp
dotnet restore
dotnet run
```