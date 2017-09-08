# deploy模块

`RpcEnv`是远程过程调用系统, `RpcEndpoint`要向`RpcEnv`进制注册. `RpcEndpointRef`是`RpcEndpoint`实例的引用, 通过由`Ref`向实例发送消息. `Master/Worker/Driver/Executor`都是`RpcEndpoint`.

在`spark`2.x后, 用`Netty`实现的一套`Rpc`替代了`Actor`. 但大体上都是消息异步传递处理的状态机.

## 状态

`App`的状态: `waiting`, `running`, `finished`, `failed`, `killed`, `unknown`

`driver`的状态: `submitted`, `running`, `finished`, `relaunching`, `unknown`, `killed`, `failed`, `error`

`executor`的状态: `launching`, `running`, `killed`, `failed`, `lost`, `exited`

`master`的状态: `stanby`, `alive`, `recovering`, `completing_recovery`

`worker`的状态: `alive`, `dead`, `decommissioned`, `unknown`

## 变量

`SPARK_PUBLIC_DNS`  ??轮询