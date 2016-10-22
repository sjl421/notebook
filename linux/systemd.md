# systemd

列出所有服务单元： `systemctl` or `systemctl list-units`

输出运行失败的单元： `systemctl --failed`

列出单元文件： `systemctl list-unit-files`

激活单元： `systemctl start apache2`

停止单元： `systemctl stop apache2.service`

重启单元： `systemctl restart apache2`

重新读取配置： `systemctl reload apache2`

输出状态： `systemctl status apache2`

检查是否为自动启动： `systemctl is-enabled apache2`

开机自启单元： `systemctl enable apache2`

取消自启单元： `systemctl disable apache2`

查看单元帮助： `systemctl help apache2`

## 电源处理

重启： `systemctl reboot`

停止电源： `systemctl poweroff`

待机： `systemctl suspend`

休mian: `systemctl hibernate`

## 网络时间同步

`systemctl status systemd-timesync.service`

配置文件在`/etc/systemd/timesync.conf`.

时间同步服务的端口: `123`.
