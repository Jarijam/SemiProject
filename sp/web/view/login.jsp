<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	if(id.equals("qq") && pwd.equals("11")){
		out.print("1");
	}else{
		out.print("2");
	}
%>

