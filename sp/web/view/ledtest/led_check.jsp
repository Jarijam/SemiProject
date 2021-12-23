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
   
   <img alt="off" src="led_off.png">
   
   
</body>
</html>