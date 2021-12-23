<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.center_page > h5 > img{
	width:40px;
	height:50px; 
}
</style>


<div class="center_page">
<h1>Product List Page</h1>
<c:forEach var="p" items="${plist }">
<h5><img src="img/${p.imgname }"> ${p.name } ${p.price }</h5>
</c:forEach>
</div>