# get hardware infomation

获取CPU信息:
```sh
$ lscpu
Architecture:          x86_64         # 架构
CPU op-mode(s):        32-bit, 64-bit # 指令模式
Byte Order:            Little Endian  # 大端/小端
CPU(s):                8              # 逻辑CPU数
On-line CPU(s) list:   0-7            # 运行的CPU列表
Thread(s) per core:    2              # 每核线程数
Core(s) per socket:    4              # 每槽核数
Socket(s):             1              # 槽数
NUMA node(s):          1              # NUMA节点数
Vendor ID:             GenuineIntel   # intel
CPU family:            6              # 家族编号
Model:                 60             # 型号
Stepping:              3              # 细节
CPU MHz:               800.015        # 运行频率
BogoMIPS:              7182.82        # 综合频率
Virtualization:        VT-x           # 虚拟化
L1d cache:             32K            # 一级数据缓存
L1i cache:             32K            # 一级指令缓存
L2 cache:              256K           # 二级缓存
L3 cache:              8192K          # 三级缓存
NUMA node0 CPU(s):     0-7
```
