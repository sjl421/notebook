# Spark的运行脚本

## 启动Master/Slave

无论是`start-master.sh`还是`start-slave.sh`, 都使用`start-daemon.sh start`命令, 进而使用了`spark-class`命令.

你好