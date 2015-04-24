# python libs argparse

`argparse`用于Python脚本的命令行参数解析.

```python
import argparse
parser = argparse.ArgumentParser()
parser.add_argument('-s', action='store', dest='simple_value',
                    help='Store a simple value')
parser.add_argument('-c', action='store-const', dest='constant_value',
                    const='value-to-store', help='Store a constant value')
parser.add_argument('-t', action='store_true', default=False,
                    dest='boolean_switch', help='set a switch to true')
parser.add_argument('-f', action='store_false', default=True,
                    dest='boolean_switch', help='set a switch to false')
parser.add_argument('-a', action='append', dest='collection'
                    default=[], help='Add repeated values to a list')
parser.add_argument('-A', action='append_const', dest='const_collection',
                    const='value-1-to-append', default=[],
                    help='add different values to list')
parser.add_argument('-B', action='append_const', dest='const_collection',
                    const='value-2-to-append', default=[],
                    help='add different values to list')
parser.add_argument('--version', action='version',
                    version='%(prog)s 1.0')
results = parser.parse_args()
print(results.simple_value)
```
