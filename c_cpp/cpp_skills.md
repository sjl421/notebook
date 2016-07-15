# cpp skills

遍历容器元素, `for (auto ele : v1) {...}`.

将任意数值转换为字串, `to_string(1)`. 在`string`头文件中.

处理容器每个元素并返回, `transform(v1.begin(), v1.end(), v2.begin(), [](T x) { return to_string(x) + "aaa"; });`. 在`algorithm`头文件中.

顺序容器, 可存储任意数量元素, 可使用`Vector<int> v1`. 在`vector`头文件中. 向其中添加元素, `.push_back(ele)`.

字典有`map`. 还有集合`set`.

类的构造函数中, 初始化属性, `ClassName(string &name, int age): name(name), age(age) {}`.

关于内存泄露, 只有自己用`new`申请的内存, 在使用后未`delete`才会泄露. `new`的内存在堆上, 正常的变量声明建立对象申请的内存在栈上, 不泄露.

声明变量, `int x`; 声明并初始化, `int x = 3`. 对于类, 声明`ClassName cn`, 声明并初始化`ClassName cn = ClassName("lzp", 23);`.

函数指针, 可以实现类似代理的功能, 将不同函数赋予函数指针, 通过同一个函数指针调用不同函数.
