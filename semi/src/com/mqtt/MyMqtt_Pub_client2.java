package com.mqtt;
//sub랑 똑같이 만들어도 됌. 디자인은 마음대로
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
//MQTT통신으로 메시지를 전송하기 위한 객체
public class MyMqtt_Pub_client2 {
	private MqttClient client;
	public MyMqtt_Pub_client2() {
		try {
			client = new MqttClient("tcp://192.168.0.158", "myid2");
			client.connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	public boolean send(String topic, String msg) {
		
		try {
			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());//전송할 메시지
			client.publish(topic, message); //토픽을 설정해서 메시지를 보낸다.
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
	public static void main(String[] args) {
		MyMqtt_Pub_client2 sender = new MyMqtt_Pub_client2();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i=1;
				String msg = "";
				while(true) {
					if(i==50) {
						break;
					}else {
						if(i%2==1) {
							msg = "led_on";
						}else {
							msg = "led_off";
						}
					}
					sender.send("led", msg);
					i++;
				}
				sender.close(); //작업 완료되면 종료하기
			}
		}).start();
	}

}
