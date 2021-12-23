<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	Random r = new Random();
	int data = r.nextInt(100);
	out.print(data);
%>
