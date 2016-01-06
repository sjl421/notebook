# rqrcode

将字符串转换为qrc码. 不提供解码. 

`gem install rqrcode`

```ruby
require 'rqrcode'
qrcode = RQRCode::QRCode.new("www.baidu.com")
image = qrcode.as_png
image.save("fname.png")
```
