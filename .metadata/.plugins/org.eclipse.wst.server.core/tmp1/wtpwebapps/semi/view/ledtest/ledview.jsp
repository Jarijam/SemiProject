<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script>
	location.href='ledview.mc';
</script>
</head>
<body>
   <!-- <form name="category" action='/http_test/led' method='GET'> -->
   <form name="btn_on" action="led_on.mc" onclick="click()" method='GET'>
      <input type = 'hidden' name='light' value='led_on'>
      <input class='onBtn' type='submit' value='on'/>
   </form>
   <!-- <form action='/http_test/led' method='GET'> -->
   <form name="btn_off" action="led_off.mc" onclick="click()" method='GET'>
      <input type = 'hidden' name='light' value='led_off'>
      <input class='onBtn' type='submit' value='off'/>
   </form>
</body>
</html>