# Semi Project

## Android 

### FCM

- MyFirebaseMessagingService

``` java
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FMS";
    NotificationManagerCompat notificationManager;
    public MyFirebaseMessagingService() {
    }
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "onNewToken 호출됨 : " + token);
    }

    @Override
    public void onMessageReceived(@NonNull  RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived 호출됨.");
        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String contents1 = data.get("c1");
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Log.d(TAG, "from : " + from + ", contents : " + contents1+" " +title+" "+body);
        if(body.equals("Button ON")){
            sendToActivity(getApplicationContext(), from, title, body);
        }else if(body.equals("LED ON")){
            sendToActivity(getApplicationContext(), from, title, body);
        }else{
            sendToActivity2(getApplicationContext(), from, contents1);
        }
    }

    private void sendToActivity2(Context context, String from, String contents1) {
        Intent intent = new Intent(context, SensorActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("c1",contents1);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void sendToActivity(Context context, String from, String title, String body) {
        Intent intent = new Intent(context, semi_web.class);
        intent.putExtra("from", from);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}

```

---

### Native App

- PostActivity & activity_post

  ```java
  public class PostActivity extends AppCompatActivity {
      Handler handler;
      Runnable delayThread = new Runnable() {
          @Override
          public void run() {
              Intent intent = new Intent(PostActivity.this, NativeActivity.class);
              startActivity(intent);
              finish();
          }
      };
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_post);
          handler = new Handler((Looper.myLooper()));
          handler.postDelayed(delayThread,2000);
      }
  }
  
  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:background="@drawable/back"
      android:orientation="vertical">
      <TextView
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_marginLeft="20dp"
          android:layout_marginTop="120dp"
          android:text="HOME\nCONTROLLER"
          android:textColor="#B39283"
          android:textSize="50sp"
          android:textStyle="bold" />
      <ImageView
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:src="@drawable/lamp" />
  </LinearLayout>
  ```

- NativeActivity & activity_native

``` java
public class NativeActivity extends AppCompatActivity {
    BufferedReader server_in;
    PrintWriter server_out;
    Socket server_main;
    Button web_btn;
    Button sensor_btn;
    Button on;
    Button off;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        web_btn = findViewById(R.id.web_btn);
        sensor_btn = findViewById(R.id.web_sensor);
        new LED_Three_Thread().start();
        on = findViewById(R.id.led_on);
        off = findViewById(R.id.led_off);
        img = findViewById(R.id.imageView);

        web_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NativeActivity.this, semi_web.class);
                startActivity(intent);
            }
        });

        sensor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NativeActivity.this, SensorActivity.class);
                startActivity(intent);
            }
        });
    }

    public void send_mssage(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message = " ";
                if(view.getId() == R.id.led_on) {
                    message = "led_on";
                    img.setImageResource(R.drawable.led_on);

                }else if(view.getId() == R.id.led_off) {
                    message = "led_off";
                    img.setImageResource(R.drawable.led_off);
                }
                server_out.println(message);
            }
        }).start();
    }

    class LED_Three_Thread extends Thread {
        public void run() {
            try {
                server_main = new Socket("192.168.0.16",12345);
                if(server_main!=null) {
                    io_init();
                }
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            String msg;
                            try {
                                msg = server_in.readLine();
                                Log.d("network","서버 수신 : "+msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                t1.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void io_init() {
            try {
                server_in = new BufferedReader(new InputStreamReader(server_main.getInputStream()));
                server_out = new PrintWriter(server_main.getOutputStream(),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="#F5DEB3">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal" >
            
        <Button
            android:id="@+id/web_btn"
            android:layout_width="175dp"
            android:layout_height="50sp"
            android:layout_margin="15dp"
            android:backgroundTint="#CA841C"
            android:text="WEB"/>

        <Button
            android:id="@+id/web_sensor"
            android:layout_width="175dp"
            android:layout_height="50dp"
            android:layout_margin="15dp"
            android:backgroundTint="#CA841C"
            android:text="sensor" />
    </LinearLayout>

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/led_off"
        tools:layout_editor_absoluteX="55dp"
        tools:layout_editor_absoluteY="142dp"
        android:layout_margin="55dp"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    <Button
        android:id="@+id/led_on"
        android:layout_width="150dp"
        android:layout_height="40dp"
        android:backgroundTint="#80571A"
        android:text="ON"
        tools:ignore="MissingConstraints"
        android:layout_margin="25dp"
        android:onClick="send_mssage"/>

    <Button
        android:id="@+id/led_off"
        android:layout_width="150dp"
        android:layout_height="40dp"
        android:backgroundTint="#80571A"
        android:text="OFF"
        android:layout_margin="25dp"
        android:onClick="send_mssage"/>
        </LinearLayout>
</LinearLayout>
```

- SensorActivity &  activity_sensor

``` java
public class SensorActivity extends AppCompatActivity {
    Button main_btn;
    ImageView img;
    TextView textView;
    NotificationManagerCompat notificationManager;
    String channelId = "channel";
    String channelName = "Channel_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        main_btn = findViewById(R.id.main_btn);
        img = findViewById(R.id.sen_img);
        textView = findViewById(R.id.sen_text);

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorActivity.this, NativeActivity.class);
                startActivity(intent);
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Main", "토큰 가져오는 데 실패함", task.getException());
                            return;
                        }
                        String newToken = task.getResult();
                        println("등록 id--------------------------------------- : " + newToken);
                    }
                });
    }
    public void println(String data1) {
        Log.d("FMS", data1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출됨");
        if (intent != null) {
            processIntent(intent);
        }
        super.onNewIntent(intent);
    }
    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            println("from is null.");
            return;
        }
        String c1 = intent.getStringExtra("c1");
        String channelId = "channel";
        Log.d("c1",c1);
        textView.setText(c1+"ºC" );
        if(Integer.parseInt(c1)<= 25) {
            img.setImageResource(R.drawable.sensor);
        }else if (Integer.parseInt(c1)<= 29) {
            img.setImageResource(R.drawable.sensor25);
        }else if(Integer.parseInt(c1)<= 30) {
            img.setImageResource(R.drawable.sensor30);
        }
    }
}



<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@drawable/back">

    <Button
        android:id="@+id/main_btn"
        android:layout_width="383dp"
        android:layout_height="wrap_content"
        android:layout_margin="15dp"
        android:backgroundTint="#CA841C"
        android:textSize="30dp"
        android:text="main" />

    <ImageView
        android:id="@+id/sen_img"
        android:layout_width="match_parent"
        android:layout_height="382dp"
        android:src="@drawable/sensor" />

    <TextView
        android:id="@+id/sen_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text=" "
        android:textSize="100dp"
        android:textAlignment="center"
        android:textColor="#000000"/>
</LinearLayout>

```

---

### Web App

- semi_web &  activity_semi_web

``` java
public class semi_web extends AppCompatActivity  {
    WebView web;
    NotificationManagerCompat notificationManager;
    String channelId = "channel";
    String channelName = "Channel_name";
    int importance = NotificationManager.IMPORTANCE_LOW;
    Button btn;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi_web);
        web = findViewById(R.id.webview);
        btn = findViewById(R.id.btn);
        txt = findViewById(R.id.txt);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSaveFormData(false);
        web.loadUrl("http://192.168.0.21/sp/led.mc");

        web.setWebViewClient(new ViewClient());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl(txt.getText().toString());
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Main", "토큰 가져오는 데 실패함", task.getException());
                            return;
                        }
                        String newToken = task.getResult();
                        println("등록 id--------------------------------------- : " + newToken);
                    }
                });
    }
    public void println(String data) {
        Log.d("FMS", data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출됨");
        if (intent != null) {
            processIntent(intent);
        }
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            println("from is null.");
            return;
        }

        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String channelId = "channel";
        notificationManager = NotificationManagerCompat.from(semi_web.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        Intent intent2 = new Intent(semi_web.this, semi_web.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(semi_web.this, 101, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(semi_web.this, "channel")
                .setSmallIcon(R.drawable.led_on)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});
        notificationManager.notify(0, mBuilder.build());
    }

    private class ViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:backgroundTint="#8A5B5B"
    android:orientation="vertical">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

                <Button
                    android:id="@+id/btn"
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:backgroundTint="#F5DEB3"
                    android:text="연결"
                    android:textColor="@color/black"/>

                <EditText
                    android:id="@+id/txt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="URL link"/>
        </LinearLayout>

        <WebView
            android:id="@+id/webview"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
</LinearLayout>
```

---

### Setting

- AndroidManifest

``` java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.test.semi">
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Semi"
        android:usesCleartextTraffic="true">
        <activity android:name=".SensorActivity"></activity>
        <activity android:name=".semi_web" />
        <activity android:name=".NativeActivity" />
        <activity android:name=".PostActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <service android:name="org.eclipse.paho.android.service.MqttService" />
        <service
            android:name=".MyFirebaseMessagingService"
            android:enabled="true"
            android:exported="true"
            android:stopWithTask="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>
    </application>
</manifest>
```
