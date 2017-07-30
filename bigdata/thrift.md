# Thrift

一个多语言的接口定义语言.

## 安装

依赖:

```sh
sudo apt-get install automake bison flex g++ git libboost1.55-all-dev libevent-dev libssl-dev libtool make pkg-config
sudo apt-get install python-all python-all-dev python-all-dbg  # for python
javac ant  # for java
```

安装`thrift`程序:

```sh
./configure --without-nodejs CXXFLAGS='-g -O2'
make -j8
make install  # generate thrift binary
```

生成`python`包:

```sh
cd lib/py
python setup.py install  # 生成python包
```

生成`java`包:

```sh
cd lib/java
ant  # build/libthrift-x.x.x.jar
```