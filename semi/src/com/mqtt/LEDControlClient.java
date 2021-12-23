package com.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.tcp.SerialArduinoLEDControl;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;



public class LEDControlClient implements MqttCallback{
	private MqttClient mqttclient;
	private MqttConnectOptions mqttOption;
	//메시지가 전송되면 messageArrived 메소드가 호출되면 데이터를 아두이노로 전송
	SerialArduinoLEDControl serialObj;
	OutputStream serialOut;
	public LEDControlClient() {
		//Mqtt객체가 생성될때 시리얼 통신을 위한 준비
		//데이터 전송마다 기다리는 작업을 해줘야해서.
		serialObj = new SerialArduinoLEDControl();
		serialObj.connect("COM5");
		serialOut = serialObj.getOutput();
	}
	public LEDControlClient init(String server, String clientId) {
		//mqtt클라이언트가 연결하기 위해서 필요한 정보를 설정
		mqttOption = new MqttConnectOptions();
		mqttOption.setCleanSession(true);
		mqttOption.setKeepAliveInterval(30);
		try {
			mqttclient = new MqttClient(server,  clientId);
			mqttclient.setCallback(this);
			mqttclient.connect(mqttOption);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		return this;
	}	
	
	@Override
	public void connectionLost(Throwable arg0) {
		
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
	}
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("===============메시지 도착===========");
		System.out.println(message+","+"topic: "+topic+", id: "+message.getId()+", Payload: "+
																		new String(message.getPayload()));
		String msg = new String(message.getPayload());
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if(msg.equals("led_on")) {
						serialOut.write('o');
					}else {
						serialOut.write('f');
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	//구독신청하기
	public boolean subscribe(String topic) {
		try {
		if(topic!=null) {
				mqttclient.subscribe(topic, 0); 
			}
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		LEDControlClient client = new LEDControlClient();
		client.init("tcp://192.168.0.158:1883", "myid").subscribe("led");
	}
}
