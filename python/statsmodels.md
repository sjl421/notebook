# statsmodels

这个库主要用于统计分析,提供了多个常用的统计模型,且能和`pandas`紧密结合,也可通过
`patsy`使用类似`R`语言的`formula`形式.

`pip install statsmodels`

## 常用

`statsmodels`主要有两种使用方式, 一种是`sm`中模型类`OLS`, 一种是`smf`的模型函数
`ols`. `OLS`接受设计矩阵参数, `ols`可接受`formula`.

需要注意的是, `statsmodels`中大量术语都使用的是统计学方面的, 比如自变量`exog`, 
因变量`endog`.

```python
import numpy as np
import statsmodels.api as sm
import statsmodels.formula.api as smf

# 能通过get_rdataset在线获取R语言常用的统计数据,
data = sm.datasets.get_rdataset("Guerry", "HistData").data
# ols表示普通线性回归,首参是formula,~左边是因变量,右边是自变量
# +连接多个自变量,np.log为嵌套的计算表达式,表示对此自变量先运算
# fit()表示进行拟合,获得回归模型
rst = smf.ols('Lottery ~ Literacy + np.log(Pop1831)', data=data).fit()
# 返回模型的相关统计信息
rst.summary()
```

第二种方式:
```python
import patsy as ps
df = sm.datasets.get_rdataset("Guerry", "HistData").data  #返回数据帧格式
# 返回设计矩阵,对X会额外多增加一列常数向量1
y, X = ps.dmatrices('Lottery ~ Literacy + np.log(Pop1831)', data=df, return_type='dataframe')
mod = sm.OLS(y, X)  #建立模型
res = mod.fit()  #拟合
res.summary()  #返回统计信息
```

在对数据的处理时, 可以应用`pandas`提供的多个有用的函数, 处理之后再用于模型.

对最终的统计结果,有多个方法和缓存的属性可获取,以用于二次计算.

```python
centered_tss  #Lyy, 总离差平方和,sum(y-y_h)^2
ssr  #Q, 残差平方和
ess  #U, 回归平方和
params  #β, 回归系数
rsquared  #R², 复相关系数
tvalues  #ti, 单个回归系数的显著性检验的t值
pvalues  #pi, 对应上述ti值的p值,当α>p时,可拒绝假设,认为显著
conf_int([alpha])  #返回对应α的置信区间,默认95%
fvalue  #总体显著性检验时的F值,自由度为p,n-p-1
f_pvalue  #对应上述F值的p值,当α>p时,可拒绝假设,认为总体显著
nobs  #观测样本数
df_model  #p, 模型自由度,也就是影响因子的个数
df_resid  #n-p-1, 残差自由度
mse_model  #回归平方和的均方误差, ess/df_model
mse_resid  #残差平方和的均方误差, ssr/df_resid
mse_total  #总均方误差, Lyy/nobs
resid  #残差向量, e, Q=e'e
fittedvalues()  #应用回归模型后的y值, 即y^
```

`statsmodels`的`graphics`包也提供了常用的画图.
