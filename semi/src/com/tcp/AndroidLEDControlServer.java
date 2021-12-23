package com.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//안드로이드의 요청을 받으면 아두이노 장치와 통신
public class AndroidLEDControlServer {
	private ServerSocket server;
	SerialArduinoLEDControl serialobj;
	public  AndroidLEDControlServer() {
		serialobj = new SerialArduinoLEDControl();
		serialobj.connect("COM4");
	}
	public void connect() {
		try {
			server = new ServerSocket(12345);
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
					try {
						Socket 	client = server.accept();
						String ip = client.getInetAddress().getHostName();
						System.out.println(ip+"사용자 접속");
						InputStream serialin = serialobj.getInput();
						OutputStream serialout = serialobj.getOutput();
						new ReceiverThread(client, serialout).start();
						new SenderThread(client, serialin).start();
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
	public static void main(String[] args) {
		new AndroidLEDControlServer().connect();
	}
}
