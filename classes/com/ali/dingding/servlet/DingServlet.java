package com.ali.dingding.servlet;

import com.ali.dingding.services.DingService;
import com.ali.dingding.util.auth.AuthHelper;
import com.js.oa.weixin.manage.DingdingManageAction;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DingServlet extends HttpServlet {
  private static Logger log = Logger.getLogger(DingServlet.class);
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getQueryString();
    log.debug("query = " + query);
    log.debug("url = " + request.getRequestURL().append("?").append(query));
    System.out.println("query:" + query);
    if (query != null && query.indexOf("code=") > -1 && query.indexOf("state=") > -1) {
      (new DingService()).processRequest(request, response);
    } else {
      String REDIRECT_URI = request.getRequestURL().append("?").append(query).toString();
      String appId = DingdingManageAction.getPropValue("sCorpID");
      String url = "https://oapi.dingtalk.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE";
      url = url.replace("APPID", appId).replace("REDIRECT_URI", URLEncoder.encode(REDIRECT_URI, "utf-8"));
      response.sendRedirect(url);
    } 
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    System.out.println("query:doPost");
    String method = request.getParameter("method");
    if ("getSign".equals(method))
      try {
        String url = request.getParameter("url");
        String ticket = request.getParameter("ticket");
        String nonceStr = request.getParameter("nonceStr");
        long timeStamp = Long.parseLong(request.getParameter("timeStamp"));
        System.out.println("url:" + url);
        System.out.println("ticket:" + ticket);
        System.out.println("nonceStr:" + nonceStr);
        System.out.println("timeStamp:" + timeStamp);
        String sign = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
        response.getWriter().print(sign);
      } catch (Exception e) {
        e.printStackTrace();
      }  
  }
}
