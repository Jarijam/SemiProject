<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>

<script>
function click()
{
if(document.btn_on.public.clicked)
   {
    document.btn_on.public.value='on';
    document.btn_on.submit();
   }
else if(document.btn_off.public.clicked)
   {
   document.btn_off.public.value='off';
    document.btn_off.submit();   
   }
  
} 
</script>
</head>
<!-- 사용자가 LED 버튼을 누르면 그 결과를 창에 표시.... 
참고할 코드는?? 
semiHTTP01,02처럼 데이터를 다시 줄건지?
or
HttpSession을 이용해서 뷰에 띄울껀지.
일단 먼저 log인 기능처럼 session을 이용해서 view에 띄워보자.-->
<body>
   <!-- <form name="category" action='/http_test/led' method='GET'> -->
   <form name="btn_on" onclick="click()" method='GET'>
      <input type = 'hidden' name='LED' value='on'>
      <input class='onBtn' type='submit' value='on'/>
   </form>
   <!-- <form action='/http_test/led' method='GET'> -->
   <form name="btn_off" onclick="click()" method='GET'>
      <input type = 'hidden' name='LED' value='off'>
      <input class='onBtn' type='submit' value='off'/>
   </form>
   <h1>led_status : ${status}</h1>
   	
<%--    <c:choose>
	<c:when test="${status == on }">
		<li><img src="/semi/img/led_on.png"></li>
	</c:when>
	<c:when test="${status == off }">
		<li><img src="/semi/img/led_off.png"></li>
	</c:when>
	<c:otherwise>
		<li><img src="/semi/img/led_off.png"></li>
	</c:otherwise>
</c:choose> --%>
</body>
</html>