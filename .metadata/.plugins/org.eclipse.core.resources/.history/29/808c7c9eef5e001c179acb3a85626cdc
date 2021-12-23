<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div>
		<input type="text" id="messageinput">
	</div>

	<div>
		<button onclick="openSocket();">Open</button>
		<button onclick="send();">Send</button>
		<button onclick="closeSocket();">close</button>
	</div>

	<div id="message"></div>
	<script>
		var ws;
		var messages = document.getElementById("message");

		function openSocket() {
			if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
				writeResponse("WebSocket is already opend.");
				return;
			}

			//웹소켓 객체 만드는 코드
			var url = window.location.host;//웹브라우저의 주소창의 포트까지 가져옴
			var pathname = window.location.pathname; /* '/'부터 오른쪽에 있는 모든 경로*/
			var appCtx = pathname.substring(0, pathname.indexOf("/", 2));
			var root = url + appCtx;
			ws = new WebSocket("ws://"+root+"/ws");

			ws.onopen = function(event) {
				if (event.data === undefined)
					return;
				writeResponse(event.data);
			};
			ws.onmessage = function(event) {
				writeResponse(event.data);
			};
			ws.onclose = function(event) {
				writeResponse("Connection closed");
			}
		}
		function send() {
			var text = document.getElementById("messageinput").value;
			ws.send(text);
			text = "";
		}
		function closeSocket() {
			ws.close();
		}
		function writeResponse(text) {
			message.innerHTML += "<br/>" + text;
		}
	</script>
</body>
</html>
