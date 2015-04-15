# android

## Component

Android系统四大组件：

* 活动`Activity`
* 服务`Service`
* 广播接收器`BroadcastReceiver`
* 内容提供器`ContentProvider`

这四大组件都必须在`AndroidManifest.xml`中注册才能使用.

### AndroidManifest

整个项目的配置文件，用于注册组件，声明权限，指定SDK等。

```xml
  <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            package="com.enalix.xxxx"    //指定包名，组件注册时可以省略包名
  android:versionCode="1"
  android:versionName="1.0">
  <uses-sdk
      android:miniSdkVersion="19"
      android:targetSdkVersion="21" />
  <uses-permission
      android:name="android.permission.XXX" />
  <application
      android:allowBackup="true"
      android:icon="@drawable/ic_launcher"
      android:label="@string/app_name"
      android:theme="@style/AppTheme" >
      <activity
          android:name=".MainActivity"    //活动类
      android:label="@string/xxxLable">    //标题栏
      <intent-filter>
          <action android:name="android.intent.action.MAIN" />    //声明为主活动
          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
  </activity>
  <service
      android:name=".MyService">
  </service>
  <receiver
      android:name=".MyBroadcastReceiver">
  </receiver>
  <provider
      android:name="com.xxx.xxx.xxxProvider"
      android:authorities="com.xxx.xxx.provider"
      android:exported="true">
  </provider>
  </application>
  </manifest>
```

### Activity  (import android.app.Activity)

活动是一组可以包含用户界面的组件，主要用于与用户进行交互。

活动状态：运行状态，暂停状态，停止状态，销毁状态。当原活动被回收后，从下一个活动返回后，原活动会被onCreate重新创建，但数据丢失。

生存期：完整生存期，可见生存期，前台生存期

启动模式：`standard`, `singleTop`, `singleTask`, `singleInstance`。通过活动注册时android:launchMode属性指定。

* `standard`:每当启动一个新的活动，它就会在返回栈中入栈，并处于栈顶位置。系统不会在乎这个活动是否已经在返回栈中存在，每次启动都会创建该活动的一个新的实例。
* `singleTop`:在启动活动时，若该活动已经处于返回栈的栈顶，则不会再创建该活动的新的实例。
* `singleTask`:每次启动新活动时，系统会在返回栈中检查是否存在该活动的实例，如果发现已经存在则直接使用该实例，并把在这活动之上的所有活动统统出栈，如果没有发现就会创建一个新的活动实例。
* `singleInstance`:会启用一个新的返回栈来管理这个活动。如此，无论哪个应用程序访问这个活动，都共用一个返回栈。

代码中引用资源：`R.xxx.xxx`。项目中添加的任何资源都会在R.java文件中生成相应的资源id

XML中引用资源：`@xxx/xxx`, 添加资源`@+xxx/xxx`

Android使用任务来管理活动，一个任务就是一组存放在栈里的活动的集合。当启动一个新活动时，就会覆盖在原活动上，点击Back则销毁最上面的活动，
下面的一个活动就会重新显示出来。系统总是显示处于栈顶的活动给用户。每个应用程序都会有自己的返回栈，同一个活动在不同的返回栈入栈时都会创建
新的实例。

活动不可见与活动不可交互是不同的概念。当启动一个对话框活动时，原活动仍然可见但不可交互；当启动普通活动时，原活动既不可见也不可交互。对话框活动
与普通的活动唯一的区别是：活动注册时，指定android:theme属性为对话框。

```java
    //活动的生命周期
    public class MainActivity extends Activity {

      //活动被创建时，与onDestroy相对
      @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xxx);  //加载布局
        if (savedInstanceState != null) {
            String value = savedInstanceState.getString("key");
        }  //获取活动销毁时保存的数据
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏标题栏
        Button abc = (Button) findViewById(R.id.abcButton);    //获得Button实例
        OnClickListener listener = new OnClickListener() {    //通过匿名类写法，生成点击监听器
          @Override
          public void onClick(View v) {
            Toast.makeText(MainActivity.this, "hello, android", Toast.LENGTH_SHORT).show();
            //短暂显示一段文本
          }
        }
        abc.setOnClickListener(listener);    //为按钮注册监听器
      }

      //在活动被销毁前调用
      @Override
      protected void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState();
          outState.putString("key", value);    //用Bundle对象存储数据
      }

      //获得启动的活动销毁时的结果
      @Override
      protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
        switch (requestcode） {
        case 1:
          break;
        }
      }

      //创建菜单，返回true则允许显示
      ＠Override
      public boolean onCreateOptionMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xxx, menu);    //inflate用于为Menu对象添加菜单布局
        return true;
      }

      //定义菜单响应事件
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item1:
          ...
          break;
        case R.id.item2:
          ...
          break;
        default:
          break;
        }
        return true;
      }

      //活动由不可见变为可见，与onStop相对
      @Override
      protected void onStart() {
        super.onStart();
      }

      //活动由不可交互变为可交互，与onPause相对
      @Override
      protected void onResume() {
        super.onResume();
      }

      @Override
      protected void onPause() {
        super.onPause();
      }

      @Override
      protected void onStop() {
        super.onStop();
      }

      @Override
      protected void onDestroy() {
        super.onDestroy();
      }

      @Override void onRestart() {
        super.onRestart();
      }
    }
```

### BroadcastReceiver  (import android.content.Broadcastreceiver)

Android中的每个应用程序都可以对自己感兴趣的广播进行注册，这样该程序就只会接收到自己所关心的广播内容，这些广播可能是来自于系统，
也可能是来自于其它应用程序的。Android提供了一整套完整的API，允许应用程序自由地发送和接收广播。

Android中的广播分为标准广播和有序广播。标准广播是一种完全异步执行的广播，在广播发出后，所有广播接收器几乎都会在同一时刻接收到这条广播消息。
有序广播则是一种同步执行的广播，在广播发出之后，同一时刻只会有一个接收器能够收到这条广播消息，当这个广播接收器中逻辑执行完毕后，广播才会
继续传递。

注册广播的方式一般有两种，在代码中注册和在AndroidManifest中注册，前者称为动态注册，后者称为静态注册。动态注册广播必须在程序启动之后才能
接收到广播，而静态注册则可以让程序在未启动的情况下就能接收到广播。

广播又分为系统全局广播和本地广播。系统全局广播可以被其他任何应用程序接收到，并且也可以接收到来自于其他任何应用程序的广播，很容易引起安全性问题。
本地广播只能在广播程序的内部进行传递，并且广播接收器也只能接收来自应用程序发出的广播。

动态注册：

```java
  IntentFilter intentFilter = new IntentFilter();
  intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");  //网络变化的广播
  intentFilter.setPriority(100);  //若为有序广播，可设置优先级
  class MyReceiver extends BroadcastReceiver {
      //接收到广播时，执行
      @Override
      public void onReceive(Context context, Intent intent) {
      }
  }
  MyReceiver receiver = new MyReceiver();
  registerReceiver(receiver, intentFilter);  //动态注册广播
  unregisterReceiver(MyReceiver);
  abortBroadcast();  //丢弃有序广播
```

需要注意的是，不要在`onReceive()`方法添加过多的逻辑或者进行任何的耗时操作，因为在广播接收器中是不允许开启线程的，当`onReceive()`方法运行了较长时间
而未结束时，程序就会报错。因此广播接收器更多的是扮演打开程序其他组件的角色。

静态注册：

```xml
  <receiver
      android:name=".MyReceiver">
    <intent-filter android:priority="100">
      <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
  </receiver>
```

对于有序广播，需要设置接收广播的优先级，为`intent-filter`设置`android:priority`属性。

权限：`android.permission.RECEIVE_BOOT_COMPLETED`

发送广播：

```java
  Intent intent = new Intent("...");
  sendBroadcast(intent);
  sendOrderedBroadcast(intent, null);  //二参是与权限相关的字符串
  abortBroadcast();  //接收到有序广播后，可选择丢弃，不继续进行传播
```


### ContentProvider  (import android.content.ContentProvider)

内容提供器主要用于在不同的应用程序之间实现数据共享的功能。它可主选择只对哪一部分的数据进行共享，从而保证数据安全和隐私。

内容提供器的用法有两种：一种是使用现有的内容提供器来读取操作相应程序中的数据；另一种是创建自己的内容提供器给我们程序的数据提供外部访问接口。

访问其他程序中的数据：通过`Context`中的`getContentResolver()`方法获取`ContentResolver`类的实例，其提供一系统方法用于对数据进行CRUD操作。像`insert()`,
`update()`, `delete()`, `query()`方法等。不同于数据库的CRUD， `ContentResolver`的方法接受的不是表名而是Uri参数。

内容Uri给内容提供器中的数据提供了唯一标识符，主要由两部分组成：权限和路径。`content://com.enalix.xxx.provider/table1/id`。权限用于对不同应用
程序做区分，一般用程序包名替代，`com.enalix.xxx.provider`。`/table1`是路径，`/id`是id。

```java
  Uri uri = Uri.parse("content://com.enalix.xxx.provider/table1");
```

创建自己的内容提供器：

```java
  public class MyProvider extends ContentProvider {
      @Override
      public boolean onCreate() {
          return false;
      }
      @Override
      public Cursor query(Uri uri, String[] projection, String selection,
        String[] selectionArgs, String sortOrder) {
          return null;
      }
      @Override
      public Uri insert(Uri uri, ContentValues values) {
          return null;
      }
      @Override int update(Uri uri, ContentValues values,
        String selection, String[] selectionArgs) {
          return 0;
      }
      @Override
      public int delete(Uri uri, String selection, String[] selectionArgs) {
          return 0;
      }
      @Override
      public String getType(Uri uri) {
          return null;
      }
  }
```

### Service  (import android.app.Service)

服务适合用于去执行那些不需要与用户交互而且要求长时间运行的任务。但服务依赖于创建服务时所在应用程序的进程，当某个应用程序进程被杀掉时，所有
依赖于该进程的服务也会停止运行。

服务默认并不会自动开启线程，所有的代码都是默认运行在主线程当中的。我们需要在服务内部手动创建子线程，并在这里执行具体的任务，否则就有可能出现
主线程被阻塞住的情况。

活动通过与服务绑定来进行通信，服务可以和程序内的任何一个活动进行绑定，绑定后活动将获得相同的Binder实例。

活动有自己的生命周期。当`Context`类的`startService`方法调用后，相应的服务就会启动并回调`onStartCommand()`方法，若服务之前没创建过则先调用`onCreate`方法。
服务启动后会一直保持运行状态，直到`Context`类的`stopService`方法调用或服务内`stopSelf`方法调用。每个服务只有一个实例，无论启动多少次，都只需停止一次。
调用`Context`类的`bindService`方法来获取一个服务的持久连接，并回调服务的`onBind`方法。调用方获得`onBind`方法返回的`IBinder`对象，以实现与服务的通信。

一个服务只要被启动或绑定后，就会一直处于运行状态，只有被停止且被解绑后，服务才会销毁`onDestroy`。

服务的系统优先级比较低，当系统出现内存不足时，可能会回收掉后台运行的服务。前台服务和后台服务的最大区别是它会有一个正在运行的图标在系统的状态栏显示，
下拉状态栏有详细的信息，类似通知。

```java
  public class MyService extends Service {

      class MyBinder extends Binder {
          public void startTask() {
          }
          public int getProgress() {
          }
      }  //活动与服务绑定
      MyBinder mBinder = new MyBinder();

      @Override
      public IBinder onBind(Intent intent) {
          return mBinder;
      }
      //服务创建时调用
      @Override
      public void onCreate() {
          //构建通知, 构建前台服务
          Notification notification = Notification.Builder().build();
          startForeground(1, notification);
      }
      //服务启动时调用
      @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
          new Thread(new Runnable() {
                  @Override
                  public void run() {
                      //耗时操作
                      stopSelf();
                  }}).start();  //由于服务默认在主线程执行，此处开启子线程执行耗时任务
        }
        //服务销毁时调用
        @Override
        public void onDestroy() {
        }
    }
    //MainActivity中
    ServiceConnection connection = new ServiceConnection() {
            //活动与服务解绑后调用
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
            //活动与服务绑定后调用
            @Override
            public void onServiceConnected(ComponentName name, IBinder mBinder) {
                MyBinder myBinder = (MyService.MyBinder) mBinder;
                myBinder.getProgress();
                myBinder.getTask();  //就是MyService里Binder内的方法
            }
        });
    Intent bindIntent = new Intent(MainActivity.this, MyService.class);
    bindService(bindIntent, connection, BIND_AUTO_CREATE);  //活动绑定服务后，就可以调用服务里Binder提供的方法，BIND_AUTO_CREATE表示绑定后自己创建服务
    unbindService(connection);  //解绑
```

#### IntentService  (import android.app.IntentService)

`IntentService`是异步的、可自动停止的服务。自动开启子线程，且任务完成后自己停止。

```java
  public class MyIntentService extends IntentService {
      public MyIntentService() {
          super("MyIntentService");
      }
      @Override
      protected void onHandleIntent(Intent intent) {
          //子线程
      }
      @Override
      public void onDestroy() {
      }
  }
```

#### Thread

多线程编程：

```java
  //1. 继承
  class MyThread extends Thread {
      @Override
      public void run() {
      }
  }
  new MyThread().start();
  //2. 实现接口
  class MyThread implements Runnable {
      @Override
      public void run() {
      }
  }
  MyThread myThread = new MyThread();
  new Thread(myThread).start();
  //3. 匿名类
  new Thread(new Runnable() {
          @Override
          public void run() {
          }
      }).start();
```

Android不允许在子线程中进行UI操作，但提供了一套异步消息处理机制，由`Message`, `Handler`, `MessageQueue`, `Looper`组成。

`Message`对象在线程间传递消息，`Handler`用于发送和处理消息，`MessageQueue`用于存放所有通过`Handler`发送的消息，`Looper`是每个线程中的`MessageQueue`管家。
每当发现`MessageQueue`中存在一条消息，就会将它取出，并传递给`Handler`的`handleMessage()`方法。每个线程只会有一个`MessageQueue`和`Looper`。

```java
  //主线程
  Handler handler = new Handler() {
          @Override
          public void handleMessage(Message msg) {
              switch (msg.what) {
              case XXX1:
                  //更新UI
                  break;
              }
          }
      };
  //子线程
  Message message = new Message();
  message.what = XXX1;  //判断哪个子线程传递的
  message.arg1 = 2;
  message.arg2 = 3;  //携带整数
  message.obj = obj;  //携带对象
  handler.sendMessage(message);  //从子线程发送Message对象到主线程
```

#### AsyncTask  (import android.os.AsyncTask)

为了更加方便在子线程中对UI进行操作，Android提供了`AsyncTask`抽象类，其对异步消息处理机制进行很好的封装。

arg1是在执行AsyncTask时需要传入的参数；arg2为界面上显示的进度单位类型；arg3为任务完成后返回的结果类型

```java
  class MyTask extends AsyncTask<Params, Progress, Result> {
      //在后台任务开始执行前调用，用于进行界面的初始化操作
      @Override
          protected void onPreExecute() {
      }
      //在子线程中运行，应在这里处理所有耗时任务，如需要更新UI，可调用publishProgress(Progress ...)方法
      @Override
          protected void doInBackground(Progress ...) {
      }
      //在publishProgress()方法调用后调用
      @Override
          protected void onProgressUpdate(Progress ...) {
      }
      //后台任务执行完毕并通过return返回时调用
      @Override
          protected void onPostExecute(Result ...) {
      }
  }
  new MyTask().execute();
```

### Intent  (import android.content.Intent)

`Intent`是Android程序中各组件进行交互的一种重要方式，不仅可以指明当前组件想要执行的动作，还可以在不同组件间传递数据。
`Intent`一般被用于启动活动，启动服务，发送广播等。

`Intent`分为显式`Intent`和隐式`Intent`。隐式`Intent`并不明确指定我们想要启动哪个活动，而是指定了一系列更为抽象的`action`和`category`信息，
然后交由系统分析并帮我们找出合适的活动去启动。

通过注册活动时，添加`<intent-filter>`可以指定当前活动能够响应的`action`和`category`。当`actiont`和`category`中的内容同时能够匹配上`Intent
`中指定的`action`和`category`时，这个活动才能响应该`Intent`。

```java
Intent intent = new Intent(MainActivity.this, SecondActivity.class);    //显式，明确指定启动的活动名
Intent intent = new Intent("com.enalix.xxx.MY_ACTION");  //隐式Intent， 指定action
Intent intent = new Intent(Intent.ACTION_VIEW);    //系统内置的action
intent.addCategory("com.enalix.xxx.MY_CATEGORY");    //指定category
intent.setData(Uri.parse("http://www.baidu.com"));    //setData用于指定当前Intent正在操作的数据，根据协议系统启动相应活动
intent.setData(Uri.parse("tel:10086"));    //启动拨号盘
```

启动活动：

```java
//FromActivity中
Intent intent = new Intent(FromActivity.this, ToActivity.class);
intent.putExtra("data_key", data_value);    //传递数据给下一个活动
startActivity(intent);
startActivityForResult(intent, 1);    //1是requestCode, 请求码，在onActivityResult()方法中使用。期望启动的活动在销毁前返回结果给上一个活动。
//ToActivity中
Intent intent = getIntent();
String data_value = intent.getStringExtra("data_key");    //获取上一个活动传递的数据
Intent returnIntent = new Intent();
returnIntent.putExtra("key", data);
setResult(RESULT_OK, returnIntent);
//返回数据给上一个活动， 销毁时会回调上一个活动的onActivityResult()方法, 结果只有RESULT_OK和RESULT_CANCELED
```

启动服务：

```java
Intent intent = new Intent(FromActivity.this, ToService.class);
startService(intent);
stopService(intent);
```

发送广播：

```java
Intent intent = new Intent("com.enalix.broadcasttest.MY_BROADCAST");
sendBroadcast(intent);
sendOrderedBroadcast(intent, null);    //发送有序广播，二参为与权限相关的字符串
```

intent-filter:

```xml
<intent-filter>
  <action android:name="..." />
  <category android:name="..." />
  <data                                //data标签指定活动可响应的数据，即setData()方法指定的数据
    android:scheme="..."               //数据的协议部分
    android:host="..."                 //数据的主机名部分
    android:port="..."                 //数据的端口部分
    android:path="..."                 //数据的路径部分
    android:mimeType="..." />          //数据类型
```

`PendingIntent`：同`Intent`相似，也可以指明意图用于启动活动，启动服务，发送广播等。但`Intent`倾向于立即执行，而`PendingIntent`则只在特定
时机执行。`PendingIntent`由`Intent`生成，提供3个静态方法`getActivity()`, `getBroadcast()`, `getService()`生成`PendingIntent`实例。若意图启动
活动，则`getActivity()`，若意图发送广播，则`getBroadcast()`, 若意图启动服务，则`getService()`。

第四个参数指定`PendingIntent`的行为:

* `FLAG_ONE_SHOT`
* `FLAG_NO_CREATE`
* `FLAG_CANCEL_CURRENT`
* `FLAG_UPDATE_CURRENT`

```java
  Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
  PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
```

`Intent`的`putExtra()`方法用于携带数据,但一般不能携带自定义对象.使用`Intent`传递对象通常有两种方式,`Serializable`和`Parcelable`.

`Serializable`

```java
  public class MyClass implements Serializable {
  }
  MyClass mClass = new MyClass();
  intent.putExtra("key", mClass);
  MyClass mClass = (MyClass) getIntent().getSerializableExtra("key");
```

`Parcelable`:将一个完整的对象进行分解,而分解后的每一部分都是Intent所支持的数据类型

```java
  public class MyClass implements Parcelable {
      @Override
      public int describeContents() {
          return 0;
      }
      @Override
      public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(name);
          dest.writeInt(age);
      }
      public static final Parcelable.Creator<MyClass> CREATOR =
        new Parcelable.Creator<MyClass>() {
          @Override
          public MyClass createFromParcel(Parcel source) {
              String name = source.readString();  //读取的顺序要和写入的顺序相同
              int age = source.readInt();
              return new MyClass(name, age);
          }
          @Override
          public MyClass[] new Array(int size) {
                  return new MyClass[size];
              }
      };
  }
  MyClass mClass = (MyClass) getIntent().getParcelableExtra("key");
```

Serializable比Parcelable要简单,但效率要低.

### Fragment  (import android.app.Fragment)

碎片是一种可以嵌入在活动中的UI片段，它能让程序更加合理和充分地利用大屏幕空间。，因而在平板上应用的非常广泛。

代码：

```java
  public class MyFragment extends Fragment {
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.my_fragment, container, false);
          return view;
      }
  }
```

布局：

```xml
  <fragment
      android:id="@+id/my_fragment"
      android:name="com.enalix.xxx.MyFragment"
      android:layout_width="0dp"
      android:layout_height="match_parent"
      android:layout_weight="1"
      />
```

碎片的强大之处在于可以在程序运行时动态地添加到活动当中。

代码：

```java
  MyFragment mFragment = new MyFragment();
  FragmentManager fragmentManager = getFragmentManager();
  FragmentTransaction transaction = fragmentManager.beginTransaction();
  transaction.replace(R.id.xxx, mFragment);
  transaction.addToBackStack(null);  //添加到返回栈，接收一个名字用于描述返回栈的状态
  transaction.commit();
```

碎片与活动通信：

```java
  MyFragment mFragment = (MyFragment) getFragmentManager()
    .findFragmentById(R.id.xxx);  //在活动中得到相应碎片
  MainActivity activity = (MainActivity) getActivity();  //在碎片中得到活动
```

碎片的状态：

* 运行状态：当碎片可见且所关联的活动处于运行状态
* 暂停状态：活动进入暂停状态，则所关联的碎片进入暂停状态
* 停止状态：活动进入停止状态或调用FragmentTransaction的remove()、replace()方法将碎片从活动移除但事务提交前addToBackStack()
* 销毁状态：活动进入销毁状态或调用remove(), replace()但却没有addToBackStack()

碎片的生命周期：

```java
  public void onAttach(Activity activity);
  public void onCreate(Bundle savedInstanceState);
  public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
  public void onActivityCreated(Bundle savedInstanceState);
  public void onStart();
  public void onResume();
  public void onPause();
  public void onStop();
  public void onDestroyView();
  public void onDestroy();
  public void onDetach();
```

限定符：

* 大小：small, normal, large, xlarge
* 分辨率：ldpi, mdpi, hdpi, xhdpi
* 方向：land, port

最小宽度限定符："layout-sw600dp", 当屏幕宽度大于这个最小值时，就加载此布局。


### Application  (import android.app.Application)

当程序启动的时候，系统会自动将这个类进行初始化。记得在`AndroidManifest`文件中为`<application>`标签指定`android:name`为`.MyApplication`。

代码：

```java
  public class MyApplication extends Application {
      private static Context context;
      @Override
      public void onCreate() {
          context = getApplicationContext();
      }
      //获取应用程序级别的Context
      public static Context getContext() {
          return context;
      }
  }
```

## Widget

`Widget widget = (Widget) findViewById(R.id.xxx);`

所有Android控件都具有`android:visibility`属性，用于指定控件是否可见。可选值有`visible`, `invisible`, `gone`，分别表示可见，不可见但占位，不可见不占位。
可以通过`widget.getVisibility()`获取可见属性，通过`widget.setVisibility()`设置。

`android:layout_margin`指定控件边框距父布局的间隔，`android:layout_padding`指定控件的文字距边框的间隔

### TextView  (import android.widget.TextView)

主要用于在界面上显示一段文本信息

### Layout

布局是一种可放置很多控件和布局的容器，按照一定的规律调整内部控件的位置，从而编写出精美的界面。每种布局都有自己的特有属性。

布局文件中资源引用：

* 引用自定义资源：`@[package:]type/name`
* 引用系统public资源：  `@android:type/name`
* 引用系统非public资源：`@#android:type/name`
* 引用主题属性：  `?[namespace:]type/name`

`android:gravity`指定文字在控件中的对齐方式
`android:layout_gravity`指定控件在布局中的对齐方式
`android:layout_weight`以比例方式指定控件大小

```xml
<XXXLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="match_parent"
  android:layout_height="match_parent">

  <XXXWidget
    android:id="@+id/xxx"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    />

</XXXLayout>
```

#### LinearLayout  (import android.widget.LinearLayout)

线性布局会将它所包含的控件在线性方向上依次排列。
`android:orientation`指定排列方向，`vertical` or `horizontal`

#### RelativeLayout  (import android.widget.RelativeLayout)

通过相对定位的方式让控件出现在布局的任何位置。

* `android:layout_alignParentStart/End`父布局的左/右，`true` or `false`
* `android:layout_alignParentTop/Bottom`父布局的顶/底，`true` or `false`
* `android:layout_centerInParent`父布局的中，`true` or `false`
* `android:layout_above/below`相对于某控件的上/下，`@id/widget1`
* `android:layout_toStart/EndOf`相对某控件的左右，`@id/widget1`
* `android:layout_alignLeft/Right`与某控件的左/右边缘对齐，`@id/widget1`
* `android:layout_alignTop/Bottom`与某控件的上/下边缘对齐，`@id/widget1`

#### FrameLayout  (import android.widget.FrameLayout)

所有控件摆放在布局的左上角

#### TableLayout  (import android.widget.TableLayout)

使用表格方式排列控件。设计表格时，应让每一行的列数相同，列数不等可合并单元格。

TableRow中的控件不能指定宽度。

* `android:layout_span`合并单元格的数目
* `android:stretchColumns`将某一列进行拉伸，以达到自动适应屏幕宽度的作用，“0”是第一列

```xml
  <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
               android:layout_width="match_parent"
               android:layout_height="match_parent">
    <TableRow>
      <Widget1 />
      <Widget2 />
    </TableRow>
    <TableRow>
      <Widget3 android:layout_span="2" />
    </TableRow>
  </TableLayout>
```

#### MyWidget

目前，我们所用的所有控件都直接或间接继承自`View`，所用的布局者直接或间接继承自`ViewGroup`。`View`是Android中一种最基本的UI组件，可以在屏幕上
绘制一块矩形区域，并能响应这块区域的各种属性。

引入布局：通过`<include layout="@layout/myLayout">`来引入一个定义好的布局文件。这解决了重复编写布局代码的问题，但我们需要在每个活动中
为这些控件单独编写一次事件注册的代码。

自定义控件：

```java
  public class MyLayout extends LinearLayout {
      public MyLayout(Context context, AttributeSet attrs) {
          super(context, attrs);
          LayoutInflater.from(context).inflate(R.layout.my_layout, this);
          //为加载的布局文件中的控件添加响应事件，注意这里不是Activity，很多在Activity中的代码不能直接调用，但有参数context
          //(Activity) getContext()可以获得引入这布局的活动。
      }
  }
```

当在布局文件中引入MyLayout就会调用构造函数，在构造函数中对布局进行动态加载。`LayoutInflater.from(context)`构建出一个`LayoutInflater`对象，
再调用`inflate()`方法动态加载一个布局文件，二参是给加载好的布局添加父布局。也就是说，要自定义布局，必须由布局文件生成布局，然后再在别的地方
引用这个布局。

引用自定义布局：

```xml
  <com.enalix.xxx.MyLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
  </com.enalix.xxx.MyLayout>
```

### Extras

dp是密度无关像素，在160dpi的屏幕上，1dp = 1px, 在320dpi的屏幕上，1dp = 2px。用于指定控件的大小
sp是可伸缩像素，用于指定文字大小。

`Nine-Patch`图片：是一种被特殊处理过的png图片，能够指定哪个区域可以被拉伸而哪些区域不可以。
上边框：水平拉伸时可拉伸的范围，
左边框：垂直拉伸时可拉伸的范围

## Multi-Media

### SMS

当手机接收到时一条短信时，系统会发出一条值为`android.provider.Telephony.SMS_RECEIVED`的有序广播。

接收短信：

```java
  //从短信广播中构建短信
  @Override
  public void onReceive(Context context, Intent intent) {
      Bundle bundle = intent.getExtras();
      Object[] pdus = (Object[]) bundle.get("pdus");  //提取pud数组
      SmsMessage[] messages = new SmsMessage[pdus.length];
      for (int i=0; i<messages.length; i++) {
          messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);  //将pdu字节数组转换为SmsMessage对象
      }
      String address = messages[0].getOriginatingAddress();  //获取发送号码
      String fullMessage = "";
      for (SmsMessage message : messages) {
          fullMessage += message.getMessageBody();  //获取短信内容
      }
  }
```

发送短信：

```java
  Intent intent = new Intent("...");
  PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
  SmsManager smsManager = SmsManager.getDefault();
  smsManager.sendTextMessage(smsNum, null, smsText, pi, null);
  //四参指定PendingIntent，此处即发送短信后，系统回调意图，启动广播，再注册个广播接收器就能得到发送短信的情况，包括短信是否发送成功。
  smsManager.sendMultipartTextMessage(...);  //每条短信长度不得超过160字符，超过则分割成多条短信发送
```

权限：`android.permission.RECEIVE_SMS`
      `android.permission.SEND_SMS`

### Camera And Album

File与Uri:

```java
  File file = new File(file_path);  //文件对象和文件路径是不同概念
  file.exists();  //文件是否存在
  file.delete();  //删除文件
  file.createNewFile();  //创建新文件
  Uri fileUri = Uri.fromFile(file);  //文件对象和文件Uri对象也不同
```

启动相机：

```java
  Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  //启动相机意图
  startActivity(intent);
```

打开相册：

```java
  Intent intent = new Intent("android.intent.action.GET_CONTENT");
```

### MediaPlayer  (import android.media.MediaPlayer)

```java
  MediaPlayer mediaPlayer = new MediaPlayer();
  mediaPlayer.setDataSource(filePath);
  mediaPlayer.prepare();  //准备
  mediaPlayer.start();
  mediaPlayer.pause();
  mediaPlayer.reset();
  mediaPlayer.seekTo();
  mediaPlayer.stop();
  mediaPlayer.release();  //释放掉相关资源
  mediaPlayer.isPlaying();
  mediaPlayer.getDuration();  //获取文件时长
```

## SystemService

`XxxManager xxxManager = (XxxManager) getSystemService(Context.XXX_SERVICE);`

### Sensor

光照传感器：`Sensor.TYPE_LIGHT`, 加速度传感器：`Sensor.TYPE_ACCELEROMETER`, 磁场传感器：`Sensor.TYPE_MAGNETIC`。

Android通过加速度传感器和地磁传感器共同计算得出手机的旋转方向和角度。

```java
SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_XXX);
SensorEventListener listener = new SensorEventListener() {
  //传感器精度变化时调用
  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }
  //传感器数值变化时调用
  @Override
  public void onSensorChanged(SensorEvent event) {
    event.values  //传感器数据数组
    event.sensor.getType()  //数值变化的传感器
  }
};
sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_XXX);
sensorManager.unregisterListener(listener);
```

### Notification

```java
  NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
  Notification.Builder builder = new Notification.Builder(MainActivity.this);
  builder.setContentTitle("title");
  builder.setContentText("content");
  builder.setTicker("ticker");
  builder.setSmallIcon(R.drawable.sIcon);
  builder.setLargeIcon(R.drawable.lIcon);
  builder.setContentIntent(pendingIntent);  //处理通知的点击事件
  Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
  builder.setSound(soundUri);  //设置通知铃声
  builder.setVibrate(new long[] {0, 1000, 1000, 1000});  //设置通知时震动，参数：静止时长，震动时长，...
  builder.setLights(argb, onms, offms);  //设置通知时灯的闪烁，参数：颜色，亮时长，灭时长
  builder.setDefaults(Notification.DEFAULT_ALL);
  //应用默认通知，DEFAULT_SOUND, DEFAULT_LIGHTS, DEFAULT_VIBRATE
  Notification notification = builder.build();
  manager.notify(1, notification);  //1是通知的id编号
  manager.cancel(1);  //取消编号1的通知
```

权限：`android.permission.VIBRATE`

### Location

Android的位置提供器:

* GPS定位`LocationManager.GPS_PROVIDER`
* 网络定位`LocationManager.NETWORK_PROVIDER`
* `LocationManager.PASSIVE_PROVIDER`

权限：`android.permission.ACCESS_FINE_LOCATION`

```java
LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
Location location = locationManager.getLastKnowLocation(LocationManager.GPS_PROVIDER);
//获取当前位置信息
location.getLatitude(); location.getLongitude();  //经纬度
List<String> providerList = locationManager.getProviders(true);  //返回可用的位置提供器
LocationListener listener = new LocationListener() {
  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
  }
  @Override
  public void onProviderEnabled(String provider) {
  }
  @Override
  public void onProviderDisabled(String provider) {
  }
  @Override
  public void onLocationChanged(Location location) {
  }
};
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
//5000是时间间隔，10是距离间隔
locationManager.removeUpdates(listener);
```

### Connectivity

```java
  ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  //管理网络连接
  NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
  networkInfo.isAvailable();  //判断是否有网络
```

权限："android.permission.ACCESS_NETWORK_STATE"

### Alarm

AlarmManager的工作类型：

* `ELAPSED_REALTIME`表示触发时间从系统开机开始算起,不唤醒CPU，
* `ELAPSE_REALTIME_WAKEUP`从开机算起，会唤醒CPU
* `RTC`不唤醒CPU
* `RTC_WAKEUP`表示从1970年1月1日0点算起，唤醒CPU

从Android4.4开始，系统会自动检测目前有多少`Alarm`任务存在，并将触发时间相近的几个任务放在一起执行，以减少CPU被唤醒的时间，延长电池使用时间。
可用`setExact()`方法替代`set()`方法，保证准时。

```java
  AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
  long triggerAtTime = SystemClock.elapsedRealtime() + 10 # 1000;
  manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
  //定时到，启动意图
```

## DataStore

数据持久化就是指将那些内存中的瞬时数据保存到存储设备中，保证即使在手机或电脑关机的情况下，这些数据仍然不会丢失。Android系统中主要提供了三种
方式用于简单地实现数据持久化功能：文件存储，`SharedPreference`存储，数据库存储。

### 文件存储

不对存储的内容进行任何的格式化处理，所有数据都 是原封不动地保存到文件当中的，适合存储一些简单的文本数据或二进行数据，如果想保存一些较为复杂的
数据，就需要定义一套自己的格式规范。

`openFileOutput()`方法的文件名不能有路径。所有的文件都是默认存储到`/data/data/<packagename>/files/`目录中。`MODE_PRIVATE`指若文件存在则覆写，
`MODE_APPEND`指文件存在则追加。

```java
  //写
  FileOutputStream out = Context.openFileOutput("filename", Context.MODE_PRIVATE);
  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
  writer.write(data);
  writer.close();
  //读
  FileInputStream in = Context.openFileInput("filename");
  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
  reader.close();
```

#### SharedPreference存储  (import android.content.SharedPreferences)

使用键值对的形式存储数据。文件默认存放在`/data/data/<packagename>/shared_prefs/`目录中。使用XML格式对数据进行管理

```java
  //写
  SharedPreferences.Editor editor = getSharedPreferences("filename", Context.MODE_PRIVATE).edit();
  //ContextWrapper.java使用指定的文件名
  SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE);  //Activity.java使用当前活动的类名作为文件名
  SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context);
  //使用应用程序的包名作为文件名
  editor.putString("key", value);
  editor.putInt("key1", 2);
  editor.putBoolean("key2", true);
  editor.commit();
  //读
  SharedPreferences pref = getSharedPreferences("filename");
  pref.getString("key");
  pref.getInt("key1");
```

#### 数据库存储sqlite  (import android.database.sqlite.SQLiteDatabase)

数据库文件一般存储在`/data/data/<packagename>/databases/`目录中。
当数据库不可写入时，`getReadableDatabase()`返回只读对象，`getWritableDatabase()`返回异常。

```java
  //SQLiteOpenHelper是抽象类
  public class MyDatabaseHelper extends SQLiteOpenHelper {
      public MyDatabaseHelper(Context context, String dbname,
        CursorFactory factory, int version) {
          super(context, name, factory, version);
      }
      //数据库创建时调用
      @Override
      public void onCreate(SQLiteDatabase db) {
          db.execSQL("create table Book");
      }
      //数据库升级时调用
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          switch (oldVersion) {
          case 1:
              db.execSQL("navigation1");
          case 2:
              db.execSQL("navigation2");
          default:
          }  //补丁式升级数据库
      }
  }
  //MainActivity中
  MyDatabaseHelper mdbHelper = new MyDatabaseHelper(MainActivity.this,
    "Bookstore.db", null, 1);
  SQLiteDatabase db = mdbHelper.getWritableDatabase();
  SQLiteDatabase db = mdbHelper.getReadableDatabase();
  //添加数据
  ContentValues values = new ContentValues();
  values.put("colName1", "v1");
  db.insert("tableName", null, values);  //二参用于在未指定数据为某些可为空的列自动赋值null
  //查询数据
  Cursor cursor = db.query(table, columns, selection, selectionArgs,
    groupBy, having, orderBy);
  //更新数据
  ContentValues values = new ContentValues();
  db.update("tableName", values, "name = ?", new String[] {"abc"});  //"name=?"为查找更新的行，'?'为占位符，由后面的String[]中相应位指定
  //删除数据
  db.delete("tableName", "pages > ?", new String[] {"500"});  //二参为where部分，用于筛选操作(如删除，更新)的行
  db.beginTransaction();  //开启事务
  db.setTransactionSuccessful();  //设置事务执行成功
  db.endTransaction();  //结束事务
```

对数据库的所有操作，都有两种方式：

* 一种是自己构建SQL语句，通过`db.execSQL()`执行；
* 一种是调用`SQLiteDatabase`对象的`insert()`, `update()`, `delete()`, `query()`方法，然后把SQL语句转换为方法的参数。

事务的特性可以保证让某一系列的操作要么完成，要么一个也不完成。

每一个数据库版本都会对应一个版本号(不同于数据库软件的版本号)，当指定的版本号(DatabaseHelper实例化时指定)大于当前数据库版本号的时候，就会进入
`onUpgrade()`方法中执行更新。

创建数据库与生成数据库的表不同，生成表并不会调用`onCreate()`方法。


## Network

### HttpURLConnection  (import java.net.HttpURLConnection)

```java
  URL url = new URL("http://www.baidu.com");
  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  connection.setRequestMethod("GET");  //"GET", "POST"
  connection.setConnectionTimeout(8000);  //设置连接超时
  connection.setReadTimeout(8000);  //设置读取超时
  InputStream in = connection.getInputStream();  //获取服务器返回的输入流
  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
  reader.readLine();
  connection.setRequestMethod("POST");
  DataOutputStream out = new DataOutputStream(connection.getOutputStream);
  out.writeBytes("data");
  connection.disconnect();
```

### HttpClient  (

```java
  HttpClient httpClient = new DefaultHttpClient();
  HttpGet httpGet = new HttpGet("http://www.baidu.com");
  httpClient.execute(httpGet);
  httpPost httpPost = new HttpPost("http://www.baidu.com");
  List<NameValuePair> params = new ArrayList<NameValuePair>();
  params.add(new BasicNameValuePair("username", "admin"));
  params.add(new BasicNameValuePair("password", "123456"));
  UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
  httpPost.setEntity(entity);
  HttpResponse response = httpClient.execute(httpPost);
  if (httpResponse.getStatusLine().getStatusCode() == 200) {
      //请求和响应成功
  }
  HttpEntity entity = response.getEntity();
  String responseText = EntityUtils.toString(entity, "utf-8");
```


## Test

新建Android Test Project, 指定需要测试的项目.

```java
  public class MyTest extends AndroidTestCase {
      //测试用例执行前调用
      @Override
      protected void setUp() throws Exception {
          super.setUp();
      }
      //测试用例执行后调用
      @Override
      protected void tearDown() throws Exception {
          super.tearDown();
      }
      //以test开头的方法都会被执行
      public void testXXX() {
      }
  }
```
