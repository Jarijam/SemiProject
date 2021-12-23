package com.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SenderThread extends Thread{
	Socket client;
	BufferedReader clientin;//클라이언트가 보내는 메세지
	PrintWriter clientout;//클라이언트에게 보내는 메세지
	InputStream serialin;
	
	public SenderThread(Socket client, InputStream serialin) {
		super();
		this.client = client;
		this.serialin = serialin;
		try {
			clientin = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			clientout = new PrintWriter(this.client.getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("run?");
		byte[] buffer = new byte[1024];
		int len = -1;
		//System.out.println(buffer[1]+","+buffer[2]+","+buffer[3]+","+buffer[4]);
		try {
			System.out.println("try?");
			while((len = serialin.read(buffer))>-1) {
				String myd = new String(buffer,0,len);
				System.out.print("0"+myd+"0");
				System.out.println("zero");
				if(myd.trim().length()>0) {
					clientout.println(myd);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
