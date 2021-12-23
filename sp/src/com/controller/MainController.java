package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Service;
import com.vo.UserVO;

@Controller
public class MainController {

	
	private Logger work_log = 
			Logger.getLogger("work"); 
	private Logger user_log = 
			Logger.getLogger("user"); 
	private Logger data_log = 
			Logger.getLogger("data"); 
	
	
	@Resource(name="uservice")
	Service<String, UserVO> service;
	
	@RequestMapping("/main.mc")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main"); //view�����ؿ��� prefix suffix �˾Ƽ� ä��� ã�ƶ�.
		return mv;
	}
	
	@RequestMapping("/iot1.mc")
	@ResponseBody
	public void iotdata(HttpServletRequest request) throws IOException {
		//이놈은 쌍방향이여. 클라이언트가 값을 정해서 (예를 들면 측정된 센서값) 브라우저에 넘기면. 그 넘긴값도 콘솔에 찍고
		//로그에도 찍고 로그에 찍히는걸 콘솔에도 찍은겨.
		//굳이 브라우저에 화면을 줄 필요가 없으니 void다!!! 디비에 넣거나 로그를 찍으면 끝이니까.
		//mqtt도 마찬가지
		//브라우저 화면에서 센서에 불을 켜라? 이쪽으로 들어온다. => mqtt돌려서 불을 끄면댄다. *라이브러리 정리하기. 
		String temp = request.getParameter("temp");
		String humi = request.getParameter("humi");
//		double f_temp = Double.parseDouble(temp);
//		double f_humi = Double.parseDouble(humi);
		int f_temp = Integer.parseInt(temp);
		int f_humi = Integer.parseInt(humi);
		System.out.println("TEMP: "+f_temp+", HUMI: "+f_humi);
		if(f_temp > 100) {
			try {
				FcmUtil.sendServer(f_temp,f_humi);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		data_log.debug(f_temp+","+f_humi);
		
		
	}
	
	@RequestMapping("/iot2.mc")
	@ResponseBody
	public void iotdata2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String temp = request.getParameter("temp");
		double f_temp = Double.parseDouble(temp);
		System.out.println(f_temp);
		work_log.debug(f_temp);
		
		if(f_temp >= 0 && f_temp <= 20 ) {
			out.print("0");	
		}else if(f_temp >= 21 && f_temp <= 30) {
			out.print("2");
		}else if(f_temp >= 31 && f_temp <= 40) {
			out.print("3");
			//ex 여기서 fcm 날리면댄다.
		}
		
		out.close();
		
	}
	
	
	@RequestMapping("/uu.mc")
	@ResponseBody
	public void uu(HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ArrayList<UserVO> list = null;
		try {
			list = service.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray();
		for(UserVO u:list) {
			JSONObject jo = new JSONObject();
			jo.put("id", u.getId()+"");
			jo.put("pwd", u.getPwd()+"");
			jo.put("name", u.getName()+"");
			ja.add(jo);
		}
		out.print(ja.toJSONString());
		out.close();
	}
	
	@RequestMapping("/login.mc")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "login"); //center���� �α����� ����
		mv.setViewName("main"); //��üȭ���� main
		return mv;
	}
	@RequestMapping("/register.mc")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "register"); 
		mv.setViewName("main"); 
		return mv;
	}
	@RequestMapping("/about.mc")
	public ModelAndView about() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "about"); 
		mv.setViewName("main"); 
		return mv;
	}
	
	@RequestMapping("/pchartpage.mc")
	public ModelAndView pchart() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "pchart"); 
		mv.setViewName("main"); 
		return mv;
	}
	
	@RequestMapping("/rchartpage.mc")
	public ModelAndView rchart() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "rchart"); 
		mv.setViewName("main"); 
		return mv;
	}
	@RequestMapping("/logout.mc")
	public ModelAndView logout(
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = 
				request.getSession();
		if(session != null) {
			session.invalidate();
		}
		mv.setViewName("main");
		return mv;
	}
	
	
	@RequestMapping("/loginimpl.mc")
	public ModelAndView loginimpl(
			HttpServletRequest request) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		ModelAndView mv = new ModelAndView();
		
		UserVO dbuser = null;
		try {
			dbuser = service.get(id);
			if(dbuser.getPwd().equals(pwd)) {
				mv.addObject("center", "ok");
				HttpSession session 
				= request.getSession(); //login�����ϸ� session���� id���� �ִ´�.
				session.setAttribute("loginid", id);
			}else {
				mv.addObject("center", "fail");
			}
		} catch (Exception e) {
			mv.addObject("center", "fail");
			e.printStackTrace();
		}
		
		
		mv.setViewName("main");
		return mv;
	}
}





