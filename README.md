# Network protocol

### 개요

- **아두이노, 웹, 안드로이드를 이용한 네트워크 시스템 구축**



### 목표

- 각 구간 별 통신(HTTP, TCP/IP, MQTT, Serial, FCM)을 이용해서 데이터를 전달
- Web & App을 연동해 web과app에서 데이터 통신
- DB연동을 통해 데이터 값 저장



### 시스템 구성도

<img src="img\system.PNG"/>



### 프로젝트 환경 및 기술스택

- 언어 
  -  Java
  -  JavaScript
  -  HTML
- 프로그램 
  - Spring MVC
  - Eclipse 4.16.0
  - Android Studio 4.2.0
  - Apache Tomcat 9.0
  - Google FireBase
  - Arduino 1.8.16

- 통신
  - HTTP
  - MQTT
  - TCP/IP
  - Serial
- 협업도구 
  - Allo
  - GitHub
  - Zoom

### 프로젝트 맴버

<img src="img\member.PNG"/>

## web



## Android

#### 구성

<img src="img\home.PNG" style="zoom:80%;" />

- 앱실행시 초기화면



<img src="img\led.PNG" style="zoom: 75%;" /> <img src="img\ledon.PNG" style="zoom: 75%;" />

- LED의 데이터값을 받아와서 현 상태 출력

<img src="img\temp.PNG" style="zoom:80%;" />

- 온도의 데이터값을 실시간으로 FCM을 이용하여 출력



#### FCM

- IOT장비의 버튼 및 LED센서의 동작여부를 FCM 상단바 알림을 통하여 전송
- IOT장비의 온도 센서의 데이터값을 FCM을 통하여 App 화면에 출력
- HTTP통신을 이용하여 LED센서를 제어



``` java
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
```

- FCM 상단바 알림을 수신 하기 위해 코드 구성



<img src="img\fcmled.png"/>

- FCM_LED

<img src="img\fcmtemp.png"/>

- FCM_Temp

<img src="img\fcmbtn.png"/>

- FCM_btn
- IOT장비에서 송신한 데이터 값을 메인서버에서 수신하여 FireBase Server로 전송



#### TCP/IP

<img src="img\tcpip.PNG"/>

- 장비의 센서를 제어 하기 위해 코드작성
