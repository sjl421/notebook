# ASM汇编指令

## push/pop

`push reg/mem/seg`；`sp<-sp-2,ss<-reg/mem/seg`

`pop reg/seg/mem`: `reg/seg/mem<-ss:[sp],sp<-sp+2`

压栈, `sp`先减; 出栈, `sp`后加.

## rep

重复指令`ecx`次.

常与`movsb`, `movsd`, `movsw`等配合使用, 表示将字串从`esi`地址复制到`edi`地址. `movsx`带符号传送。

也常与`stosb`, `stosd`等配合使用, 表示将`eax`存储的数据复制到`edi`地址. 而`lodsb`, `lodsd`则表示将`esi`地址的数据复制到`eax`中。

以上复制的方向由标志寄存器`eflags`的标志位`DF`. 其中为0表示地址增加, 为1表示地址减小. 而`cld`和`std`分别置0和置1. 

## fld

指令格式: `FLD　STReg/MemReal`
指令功能: 将浮点数据压入协处理器的堆栈中。当进行内存单元内容压栈时，系统会自动决定传送数据的精度。比如：用`DD`或`REAL4`定义的内存单元数值是单精度数等。
`STReg`是处理器堆栈寄存器`ST(0)~ST(7)`。

## FST
 
指令格式：`FST  STReg/MemReal`
指令功能：将协处理器堆栈栈顶的数据传送到目标操作数中。在进行数据传送时，系统自动根据控制寄存器中舍入控制位的设置把栈顶浮点数舍入成相应精度的数据。

## FSTP
 
指令格式：`FSTP  STReg/MemReal`
指令功能：与`FST`相类似，所不同的是：指令`FST`执行完后，不进行堆栈的弹出操作，即：堆栈不发生变化，而指令`FSTP`执行完后，则需要进行堆栈的弹出操作，堆栈将发生变化。

## xor eax, eax

将寄存器`eax`置0， 消耗2机器指令。
而`mov eax, 0`则消耗5机器指令。

## pushad/popad

将寄存器压栈， 顺序`eax, ecx, edx, ebx, esp, ebp, esi, edi`. 而`popad`则表示将上述寄存器出栈。

## cdq, cdqe

convert double to quad, 将`eax`扩展成64位， 联合`edx`

`cdqe`, 将`eax`符号拓展到`rax`, x64位汇编指令

## SHR SAR

汇编语言中`SAR`和`SHR`指令都是右移指令，`SAR`是算数右移指令（`shift arithmetic right`），而`SHR`是逻辑右移指令（`shift logical right`）。

两者的区别在于`SAR`右移时保留操作数的符号，即用符号位来补足，而`SHR`右移时总是用0来补足。