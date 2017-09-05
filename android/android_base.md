# android lib

## android.util.Log

``` java
Log.v("tag", "message");    ->verbose
Log.w(); Log.i(); Log.e(); Log.d(); -> warning,info,error,debug
```

## android.graphics.Point, android.graphics.Rect

``` java
Rect rect = obj.getBounds();
Point p = new Point(12, 23);
p.x = rect.centerX(); p.y = rect.centerY();
```

## android.graphics.Bitmap, android.graphics.BitmapFactory

``` java
Bitmap map = BitmapFactory.decodeFile(mappath);
Bitmap.createBitmap(map, x1,y1,x2,y2);
```

## android.app.Instrumentation

``` java
Instrumentation inst = new Instrumentation();
inst.sendKeySync(KeyEvent event);
inst.sendPointerSync(MotionEvent event);
inst.sendStringSync(String text);
```

## android.view.KeyEvent, android.view.MotionEvent -> android.veiw.InputEvent

``` java
KeyEvent kd = new KeyEvent(KeyEvent.ACTION_DOWN, key);
MotionEvent md = MotionEvent.obtain(...)
```
