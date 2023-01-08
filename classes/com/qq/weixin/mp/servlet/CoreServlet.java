package com.qq.weixin.mp.servlet;

import com.qq.weixin.mp.service.CoreService;
import com.qq.weixin.mp.service.ViewService;
import com.qq.weixin.mp.util.SignUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class CoreServlet extends HttpServlet {
  private static Logger log = Logger.getLogger(CoreServlet.class);
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getQueryString();
    log.debug("query = " + query);
    log.debug("url = " + request.getRequestURL().append("?").append(query));
    if (query != null && query.indexOf("signature=") != -1 && query.indexOf("echostr=") != -1) {
      String signature = request.getParameter("signature");
      String timestamp = request.getParameter("timestamp");
      String nonce = request.getParameter("nonce");
      String echostr = request.getParameter("echostr");
      PrintWriter out = response.getWriter();
      if (SignUtil.checkSignature(signature, timestamp, nonce))
        out.print(echostr); 
      out.close();
    } else {
      ViewService.processRequest(request, response);
    } 
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    System.out.println("query:doPost");
    String respMessage = CoreService.processRequest(request);
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
  }
}
