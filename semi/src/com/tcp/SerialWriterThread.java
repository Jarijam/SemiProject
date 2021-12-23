package com.tcp;

import java.io.IOException;
import java.io.OutputStream;
//아두이노로 데이터를 보내는 쓰레드
public class SerialWriterThread implements Runnable {
	OutputStream out;
	public SerialWriterThread(OutputStream out) {
		super();
		this.out = out;
	}
	@Override
	public void run() {
		//키보드로 입력한 데이터를 아두이노로 전송
		int data= 0;
		try {
			while((data=System.in.read())> -1) {
				//System.out.println("test....");
				out.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
