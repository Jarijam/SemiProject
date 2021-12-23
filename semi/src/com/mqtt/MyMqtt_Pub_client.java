package com.mqtt;
import javax.servlet.http.HttpServletRequest;

//sub랑 똑같이 만들어도 됌. 디자인은 마음대로
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.http.HttpRequest;
//MQTT통신으로 메시지를 전송하기 위한 객체
public class MyMqtt_Pub_client {
	private MqttClient client;
	
	public MyMqtt_Pub_client() {
		super();
		System.out.println("컨트롤러에서 넘어왔니?");
		try {
			System.out.println("튜라이했니?");
			client = new MqttClient("tcp://192.168.0.158", "myid2");
			client.connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	//보내기
	public boolean send(String topic, String msg) {
		System.out.println("샌드들어왔니?");
		try {
			System.out.println("샌드 튜라이했니ㅏ?");
			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());//전송할 메시지
			client.publish(topic, message);
			
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return true;
	}
	//종료
	public void close() {
		if(client!=null) {
			try {
				client.disconnect();
				client.close();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}
//	public void led_show(HttpServletRequest request) {
//		String LED = request.getParameter("LED");
//		System.out.println(LED);
//	}
//	public static void main(String[] args) {
//		MyMqtt_Pub_client sender = new MyMqtt_Pub_client();
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				
//				//sender.close(); //작업 완료되면 종료하기
//			}
//		}).start();
//	}

}
