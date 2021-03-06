# 有关算法的基础常识

## 算法所属类别

* 蛮力法

穷举搜索, 首先生成问题的可能解, 然后再去评估可能解是否满足约束条件

* 分治法

把一个复杂问题分解为若干个相互独立的子问题, 通过求解子问题并将子问题的解合并得到原问题的解.

* 减治法

把一个大问题划分为若干个子问题, 但是这些子问题不需要分别求解, 只需求解其中的一个子问题, 因而也无需对子问题的解进行合并.

* 动态规划法

把一个复杂问题分解为若干个相互重叠的子问题, 通过求解子问题形成的一系列决策得到原问题的解.

* 回溯法

每次只构造可能解的一部分, 然后评估这个部分解, 如果可能导致一个完整解, 才对其进一步构造.

按深度优先策略遍历问题的解空间树, 在遍历过程中, 应用约束条件, 目标函数等剪枝函数实行剪枝

* 分支限界法

按广度优先策略遍历问题的解空间树, 在遍历过程中, 对已经处理的每一个结点根据限界函数估算目标函数的可能取值, 从中选取使目标函数取得极值的结点优先进行广度优先搜索, 从而不断调整搜索方向, 尽快找到问题的解.

* 概率算法

允许算法在执行过程中随机选择下一步该如何进行, 同时允许结果以较小的概率出现错误, 并以此为代价,获得算法运行时间的大幅度减少.

* 近似算法

放弃求最优解, 而用近似最优解代替最优解, 以换取算法设计上的简化和时间复杂性的降低.

启发式算法是相对于最优化算法提出的. 一个问题的最优算法求得该问题每个实例的最优解。启发式算法可以这样定义：一个基于直观或经验构造的算法，在可接受的花费(指计算时间和空间)下给出待解决组合优化问题每一个实例的一个可行解，该可行解与最优解的偏离程度一般不能被预计. 现阶段, 启发式算法以仿自然体算法为主，主要有蚁群算法、模拟退火法、神经网络等。

## 具体的算法

拉斯维加斯型(`Las Vegas`)概率算法的一个显著特征是, 它所做的随机性选择有可能导致算法找不到问题的解, 即算法运行一次, 或者得到一个正确的解, 或者无解。 因此, 需要对同一输入实例反复多次运行算法, 直到成功地获得问题的解。

蒙特卡罗型(`Monte Carlo`)概率算法总是给出解, 但是, 这个解偶尔可能是不正确的, 一般情况下, 也无法有效地判定得到的解是否正确。 蒙特卡罗型概率算法求得正确解的概率依赖于算法所用的时间, 算法所用的时间越多, 得到正确解的概率就越高。