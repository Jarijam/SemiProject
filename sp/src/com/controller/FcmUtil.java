package com.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.Service;
import com.vo.Product;

@Controller
public class FcmUtil {
	public final static String API_KEY = "AAAA-Z3m-Ws:APA91bFh6iFE-GxpWcBhPctAK-"
			+ "CuvlCDO1xuuSUO-ye7ehu6FJHVLXjrFkEIsD7G8xJXulEQy0LGa1YGnJcJ98orhfo0yH_"
			+ "dHgGVnKcfrBUGpAAkRXTxsD36CfEpuk0Ndvh2wIyxNESi";
	public final static String URL = "https://fcm.googleapis.com/fcm/send";

//	@RequestMapping("/sendfcm.mc")
//	public void sendfcm() {
//		try {
//			sendServer();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void sendServer(
			int f_temp, int f_humi) throws Exception{
		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + API_KEY);

		conn.setDoOutput(true);

		JSONObject notification = new JSONObject();

		notification.put("title", "타이틀입니다");
		notification.put("body", "바디부분입니다.");
		
		JSONObject sdata = new JSONObject();
		sdata.put("c1", f_temp+"");
		sdata.put("c2", f_humi+"");
		
		
		JSONObject body = new JSONObject();
		body.put("notification", notification);
		body.put("data",sdata);
		body.put("to", "eOY728JTRLmSGJQ3jNqfI7:APA91bHh2IJkYP8yY_fys9vCJN4j2UhwPEjqjbsoYHuuc0bYTp9_l9VjmE_2BEFR5MK6osdqyRyLTMs2mxa3TItAsVa7q-sjXMCOiiV6Av6Zxt5G_RZl_6kr2AWBqL9hQKDaX1SYvyUe");

		OutputStream os = conn.getOutputStream();

		// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ�
		os.write(body.toJSONString().getBytes("UTF-8"));
		os.flush();
		os.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

	}

}
