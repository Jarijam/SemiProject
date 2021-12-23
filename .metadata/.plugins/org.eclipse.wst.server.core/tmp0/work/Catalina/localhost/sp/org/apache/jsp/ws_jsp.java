/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.55
 * Generated at: 2021-12-17 04:26:00 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ws_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=EUC-KR");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"EUC-KR\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("	<div>\r\n");
      out.write("		<input type=\"text\" id=\"messageinput\">\r\n");
      out.write("	</div>\r\n");
      out.write("\r\n");
      out.write("	<div>\r\n");
      out.write("		<button onclick=\"openSocket();\">Open</button>\r\n");
      out.write("		<button onclick=\"send();\">Send</button>\r\n");
      out.write("		<button onclick=\"closeSocket();\">close</button>\r\n");
      out.write("	</div>\r\n");
      out.write("\r\n");
      out.write("	<div id=\"message\"></div>\r\n");
      out.write("	<script>\r\n");
      out.write("		var ws;\r\n");
      out.write("		var messages = document.getElementById(\"message\");\r\n");
      out.write("\r\n");
      out.write("		function openSocket() {\r\n");
      out.write("			if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {\r\n");
      out.write("				writeResponse(\"WebSocket is already opend.\");\r\n");
      out.write("				return;\r\n");
      out.write("			}\r\n");
      out.write("\r\n");
      out.write("			//웹소켓 객체 만드는 코드\r\n");
      out.write("			var url = window.location.host;//웹브라우저의 주소창의 포트까지 가져옴\r\n");
      out.write("			var pathname = window.location.pathname; /* '/'부터 오른쪽에 있는 모든 경로*/\r\n");
      out.write("			var appCtx = pathname.substring(0, pathname.indexOf(\"/\", 2));\r\n");
      out.write("			var root = url + appCtx;\r\n");
      out.write("			ws = new WebSocket(\"ws://\"+root+\"/ws\");\r\n");
      out.write("\r\n");
      out.write("			ws.onopen = function(event) {\r\n");
      out.write("				if (event.data === undefined)\r\n");
      out.write("					return;\r\n");
      out.write("				writeResponse(event.data);\r\n");
      out.write("			};\r\n");
      out.write("			ws.onmessage = function(event) {\r\n");
      out.write("				writeResponse(event.data);\r\n");
      out.write("			};\r\n");
      out.write("			ws.onclose = function(event) {\r\n");
      out.write("				writeResponse(\"Connection closed\");\r\n");
      out.write("			}\r\n");
      out.write("		}\r\n");
      out.write("		function send() {\r\n");
      out.write("			var text = document.getElementById(\"messageinput\").value;\r\n");
      out.write("			ws.send(text);\r\n");
      out.write("			text = \"\";\r\n");
      out.write("		}\r\n");
      out.write("		function closeSocket() {\r\n");
      out.write("			ws.close();\r\n");
      out.write("		}\r\n");
      out.write("		function writeResponse(text) {\r\n");
      out.write("			message.innerHTML += \"<br/>\" + text;\r\n");
      out.write("		}\r\n");
      out.write("	</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
