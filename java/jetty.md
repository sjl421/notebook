# Java Web (Jetty)

`filter`:

```scala
val holder = new FilterHolder()
holder.setClassName(filterClassName)
holder.setInitParameter(key, value)
handler.addFilter(holder, pathSpec, dispatches)
```

`HttpServletRequest`:

```scala
req.getParameter("param_name")  // 获取get链接中的参数
```

`HttpServletResponse`:

`handler`:

```scala
val contextHandler = new ServletContextHandler
contextHandler.setContextPath(path)
val holder = new ServletHolder(httpServlet)  // HttpServlet, 实现doGet/doPost等方法
contextHandler.addServlet(holder, path)
```