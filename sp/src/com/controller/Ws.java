package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ServerEndpoint(value = "/ws") //이 이름으로 ws라는 메소드를 쓰겠다
public class Ws {
	
	//동시에 여러명이 접속했을때 접속 정보를 세션이라 정의하고 리스트에 담겠다
	//담겨져있는 세션에다가 동시다발적으로 메세지를 보내기 위해서
	private static final List<Session> sessionList = new ArrayList<Session>();;

	public Ws() {
		System.out.println("웹소켓(서버) 객체생성");
	}

	@RequestMapping(value = "/data.mc")
	@ResponseBody
	public void getData(HttpServletRequest request) {
		String temp = request.getParameter("temp");
		String humi = request.getParameter("humi");
		System.out.println("getData:" + temp + ": " + humi);
		sendAllMessage(temp + ": " + humi);
	}

	private void sendAllMessage(String message) {
		System.out.println("웹소켓(서버) sendAllMessage");
		try {
			for (Session session : Ws.sessionList) {
					session.getBasicRemote().sendText(message);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//브라우저에서 웹소켓으로 접속을 요청하게 되면 자동으로 onopen이 요청됨.
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Open session id:" + session.getId());

		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText("Connection Established--------------------");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sessionList.add(session);
	}

	/*
	 * 모든 사용자에게 메시지를 전달한다.
	 * 
	 * @param self
	 * 
	 * @param message
	 */
	private void sendAllSessionToMessage(Session self, String message) {
		System.out.println("웹소켓(서버) sendAllSessionToMessage");
		try {
			for (Session session : Ws.sessionList) {
				//내 자신을 제외하고 현재 들어온 메세지를 모든이에게 전송.
				if (!self.getId().equals(session.getId())) {
					session.getBasicRemote().sendText(message);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//화면(브라우저)에서 메세지가 전달이 되면 자동으로 호출.
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("웹소켓(서버) onMessage");
		System.out.println("Message From " + message);
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText("sent Messge : " + message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sendAllSessionToMessage(session, message);
	}

	@OnError
	public void onError(Throwable e, Session session) {

	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("웹소켓(서버) onClose");
		sessionList.remove(session);
	}
}
