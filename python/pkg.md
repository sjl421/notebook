# pkgs

## opencv3 with python3

Mac安装:
```sh
brew install opencv3 --with-python3
export PYTHONPATH=$PYTHONPATH:/usr/local/Cellar/opencv3/3.x.y/lib/python3.x/site-packages
```

通过以下指令验证:
```python
import cv2
cv2.__version__
```

## qt5 with python

Mac安装:
```sh
pip install pyqt5
```
