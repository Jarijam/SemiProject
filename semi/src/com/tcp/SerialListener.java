package com.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mqtt.MyMqtt_Pub_client;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

//이벤트에 대한 처리를 하는 리스너 객체를 작성
public class SerialListener implements SerialPortEventListener{
	//데이터가 입력되면 이벤트가 발생하므로 시리얼 통신을 통해 데이터를 읽을 수 있도록 input객체를
	//리스너가 갖고 있어야 한다.
	
	
	private InputStream in;
	private OutputStream out;
	String msg = "";
//	public SerialListener() {
//		mqtt = new MyMqtt_Pub_client();
//	}
	
	public SerialListener(InputStream in) {
		super();
		this.in = in;
	}
	
	public SerialListener(OutputStream out) {
		super();
		this.out = out;
	}
	//이벤트가 발생하면 호출되는 메소드
	@Override
	public void serialEvent(SerialPortEvent event) {
		/*switch(event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.RI:
			.....
		}*/
		//전달되는 데이터가 있는 경우 발생하는 이벤트 타.입.
		
		if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE) {
			try {
				
//				mqtt = new MyMqtt_Pub_client();
//				String msg = "test";
//				mqtt.send("led", msg);
				int data = in.available();	//전달되는 데이터 사이즈 
				byte[] bytedata = new byte[data]; //만큼 byte배열을 만든다.
				in.read(bytedata, 0, data);
				msg = new String(bytedata);
				//out.write(bytedata);
				
				//bytedata배열에 읽은 데이터가 저장됨.
				//출력하기
				System.out.print("받은 데이터: "+new String(bytedata));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("전역변수?"+msg);

		
	}
	public String send_btn(String btn) {
		btn = msg;
		return btn;
	}
	
}
