# ROS xacro

`xacro`是ROS机器人描述的xml宏模板系统。

## property and property block

property,一个变量对应一个值

```xml
<xacro:property name="the_radius" value="2.1" />
<xacro:property name="the_length" value="4.5" />

<geometry type="cylinder" radius="${the_radius}" length="${the_length}" />
```

property block,一个变量对应一个tag

```xml
<xacro:property name="front_left_origin">
  <origin xyz="0.3 0 0" rpy="0 0 0" />
</xacro:property>

<pr2_wheel name="front_left_wheel">
  <xacro:insert_block name="front_left_origin" />
</pr2_wheel>
```

## math expressions

`${}`内可进行简单的计算

```xml
<xacro:property name="pi" value="3.1415926535897931" />
<circle circumference="${2.5 * pi}" />
```

*Jade*版本,`${}`可运行任何python支持的运算,且预定义了很多常量.

```xml
<xacro:property name="R" value="2" />
<xacro:property name="alpha" value="${30/180*pi}" />
<circle circumference="${2 * pi * R}" pos="${sin(alpha)} ${cos(alpha)}" />
```

## conditional blocks

能在xml中嵌入条块表达式,计算值为`0,1,true,false`,否则错.

```xml
<xacro:if value="<expression>">
  <... some xml code here ...>
</xacro:if>
<xacro:unless value="<expression>">
  <... some xml code here ...>
</xacro:unless>
```

*Jade*版本,表达式支持python支持的所有运算

```xml
<xacro:property name="var" value="useit"/>
<xacro:if value="${var == 'useit'}"/>
<xacro:if value="${var.startswith('use') and var.endswith('it')}"/>

<xacro:property name="allowed" value="[1,2,3]"/>
<xacro:if value="${1 in allowed}"/>
```

## rospack commands

xacro还支持rospack的命令,在`$()`内.

```xml
<foo value="$(find xacro)" />
<foo value="$(arg myvar)" />
```

还支持参数传递

```xml
<xacro:arg name="myvar" default="false"/>
<param name="robot_description" command="$(find xacro)/xacro.py $(arg model) myvar:=true" />
```

## macros

最主要的特色是支持宏.
* `suffix`是简单文本参数
* `origin`可插入一个tag
* `content`可插入容器元素内的所有元素

```xml
<xacro:macro name="pr2_caster" params="suffix *origin **content">
  <joint name="caster_${suffix}_joint">
    <axis xyz="0 0 1" />
  </joint>
  <link name="caster_${suffix}">
    <xacro:insert_block name="origin" />
    <xacro:insert_block name="content" />
  </link>
</xacro:macro>

<xacro:pr2_caster suffix="front_left">
  <pose xyz="0 1 0" rpy="0 0 0" />  #此即origin
  <container>  #此即content
    <color name="yellow"/>
    <mass>0.1</mass>
  </container>
</xacro:pr2_caster>
```

宏嵌套时,先拓展外层的宏,再拓展内部的宏.

### 默认参数

*Indigo*,`<xacro:macro name="foo" params="x:=${x} y:=${2*y} z:=0"/>`

## 包含其它xacro文件

```xml
<xacro:include filename="$(find package)/other_file.xacro" />
<xacro:include filename="other_file.xacro" />
<xacro:include filename="$(cwd)/other_file.xacro" />
```

*Jade*,相对文件名被解析为相对于当前的进程文件.即当在宏内包含文件时,不是定义宏而是调用宏的文件来处理包含.`$(cwd)`获取当前工作目录.

为包含文件创建命名空间,`<xacro:include filename="other_file.xacro" ns="namespace"/>`,获取时`${namespace.property}`
