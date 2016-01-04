# Windows平台下的ASM汇编知识点

## mainCRTStartup

操作系统装载应用程序后，做完初始化工作就转到程序的入口点执行。程序的默认入口点由连接程序设置， 不同的连接器选择的入口函数也不尽相同。在VC++下，连接器对控制台程序设置的入口函数是 `mainCRTStartup`，`mainCRTStartup` 再调用`main` 函数；对图形用户界面（GUI）程序设置的入口函数是 `WinMainCRTStartup`，`WinMainCRTStartup` 调用你自己写的 `WinMain` 函数。具体设置哪个入口点是由连接器的`/subsystem:`选项确定的，它告诉操作系统如何运行编译生成的`.EXE`文件。可以指定四种方式：`CONSOLE|WINDOWS|NATIVE|POSIX`。如果这个选项参数的值为 `WINDOWS`，则表示该应用程序运行时不需要控制台，有关连接器参数选项的详细说明请参考 `MSDN` 库。

## 无意义nop指令

XP系统程序中开头的MOV  EDI,EDI指令的解释：在`VS .NET 2003`的`VC7\INCLUDE`目录中的`listing.inc`文件中定义了１到７个字节的无破坏性`NOP`操作的宏. `MOV  EDI,EDI`就是两个字节的`NOP`, 在程序中与`NOP`指令的意义相同。

之所以要用`MOV  EDI,EDI` 而不用两个`NOP`, 可能是因为用两个`NOP`指令耗费的CPU时钟周期要比用`MOV  EDI,EDI`指令要长，为了提高效率，就采用了`MOV  EDI,EDI`.

* 1个字节: `nop`
* 2个字节: `mov edi, edi`
* 3个字节: `lea ecx, [ecx+00]`
* 4个字节: `lea esp, [esp+00]`
* 5个字节: `add eax, DWORD PTR 0`
* 6个字节: `lea ebx, [ebx+00000000]`
* 7个字节: `lea esp, [esp+00000000]`

## fs:[0]

`FS`寄存器指向当前活动线程的`TEB`结构（线程结构）

偏移 说明:
* `000` 指向SEH链指针
* `004` 线程堆栈顶部
* `008` 线程堆栈底部
* `00C` SubSystemTib
* `010` FiberData
* `014` ArbitraryUserPointer
* `018` FS段寄存器在内存中的镜像地址
* `020` 进程PID
* `024` 线程ID
* `02C` 指向线程局部存储指针
* `030` PEB结构地址（进程结构）
* `034` 上个错误号

得到`KERNEL32.DLL`基址的方法:

```asm
assume fs:nothing       ;打开FS寄存器
`mov eax,fs:[30h]`      ;得到PEB结构地址
`mov eax,[eax + 0ch]`   ;得到PEB_LDR_DATA结构地址
`mov esi,[eax + 1ch]`   ;InInitializationOrderModuleList
`lodsd`                 ;得到KERNEL32.DLL所在LDR_MODULE结构的InInitializationOrderModuleList地址
`mov edx,[eax + 8h]`    ;得到BaseAddress，既Kernel32.dll基址 
```