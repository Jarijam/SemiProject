package chatting;
//서버가 전송하는 메시지를 읽는 일을 처리하는 쓰레드

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerReceiveThread extends Thread{
	Socket client; // 클라이언트
	
	//쓰레드가 생성될때 클라이언트 정보를 넘겨 받도록 작업
	public ServerReceiveThread(Socket client) {
		super();
		this.client = client;
	}
	
	//클라이언트가 보내는 메시지를 읽어서 콘솔에 출력하는 실행흐름
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String resMsg="";
			while(true) {
				resMsg = in.readLine();
				if(resMsg==null) {
					break;
				}
				System.out.println("클라이언트 >>>>"+resMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
}
