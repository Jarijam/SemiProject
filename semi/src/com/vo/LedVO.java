package com.vo;

import com.mqtt.MyMqtt_Pub_client;

public class LedVO extends MyMqtt_Pub_client{
	String LED;
	
	public LedVO() {
		
	}

	public LedVO(String lED) {
		super();
		LED = lED;
	}

	public String getLED() {
		return LED;
	}

	public void setLED(String lED) {
		LED = lED;
		System.out.println(lED);
	}
	
	
	
}

