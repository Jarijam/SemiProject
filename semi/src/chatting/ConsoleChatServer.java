package chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConsoleChatServer extends Thread{
	Socket client;
	private static ArrayList<Socket> clients = new ArrayList<Socket>(5);
	
	public ConsoleChatServer(Socket client) {
		super();
		this.client = client;
	}
	
	public void run() {
		InputStream fromClient = null;
		OutputStream toClient = null;
		
		try {
			System.out.println(client + "연결");
			
			fromClient = client.getInputStream();
			
			byte[] buf = new byte[1024];
			int count;
			while((count = fromClient.read(buf)) != -1) {
				for (Socket s : ConsoleChatServer.clients) {
					if (client != s) {
						toClient = s.getOutputStream();
						toClient.write(buf, 0, count);
						toClient.flush();
					}
				}
				System.out.write(buf, 0, count);
			}
		}catch (IOException e) {
			
		}finally {
			try {
				if(client != null) {
					client.close();
					
				}
				fromClient = null;
				toClient = null;
			} catch (IOException e) {
				
			}
		}
			
		
	}

	public static void main(String[] args) {
		Socket client = null;
		
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("서버접속완료...");
			while(true) {
				
				
				client = server.accept();
			    clients.add(client);
			    
				InetAddress clientIp = client.getInetAddress();
				System.out.println("접속한 클라이언트 : "+clientIp.getHostAddress());
				
				
				//클라이언트가 접속하면 클라이언트와 통신할 수 있는 서버쪽 쓰레드를 생성하고 start
				ConsoleChatServer myServer = new ConsoleChatServer(client);
				myServer.start();
				new ServerSenderThread(client).start();
				
			
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
