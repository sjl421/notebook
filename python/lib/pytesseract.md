# pytesseract, OCR

## 安装

```sh
sudo apt install tesseract-ocr tesseract-orc-xxx  # 语言包
pip install pytesseract
pip install pillow  # Python的图像库
```

* `eng`, 英语
* `chi-sim`, 简体中文
* `chi-tra`, 繁体中文
* `jpn`, 日文

## 使用

```sh
from PIL import Image
import pytesseract
img = Image.open(file_path)
ch = pytesseract.image_to_string(img [, lang='eng'])  # 可指定语言
img.save(file_path)  # 保存图片
```
