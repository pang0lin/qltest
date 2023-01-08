package com.yunshipei.servlet;

import com.js.oa.weixin.manage.WeixinManageAction;
import com.yunshipei.action.GMWAppLogonAction;
import com.yunshipei.util.MessageUtil;
import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

public class GMWCoreServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String queryString = req.getQueryString();
    System.out.println("queryString-post-" + queryString);
    System.out.println("post");
    try {
      String identity = req.getParameter("identity");
      String token = req.getParameter("token");
      String serverUrl = WeixinManageAction.getPropValue("serverUrl");
      String url = req.getParameter("url");
      String url1 = "";
      if (url.startsWith("/jsoa")) {
        url1 = String.valueOf(serverUrl.substring(0, serverUrl.length() - 5)) + url;
      } else if (url1.startsWith("/")) {
        url1 = String.valueOf(serverUrl) + url;
      } else {
        url1 = String.valueOf(serverUrl) + "/" + url;
      } 
      url = url1;
      System.out.println("url***************" + url);
      if (identity != null && !"".equals(identity) && 
        token != null && !"".equals(token) && 
        url != null && !"".equals(url)) {
        url = URLDecoder.decode(url, "utf-8");
        System.out.println("identity--" + identity);
        System.out.println("token-----" + token);
        System.out.println("url-------" + url);
        String tokenUrl = "http://42.159.29.165/cgi-bin/authOA";
        String result = MessageUtil.doHttpsPost(tokenUrl, "<xml><token>" + token + "</token></xml>");
        JSONObject jsonObj = JSONObject.fromObject(result);
        if (jsonObj.get("status") != null && "1".equals(jsonObj.getString("status"))) {
          boolean isLogin = GMWAppLogonAction.logon(req, resp);
          if (isLogin)
            resp.sendRedirect(url); 
        } else {
          System.out.println("token验证结果：" + result);
        } 
      } else {
        System.out.println("参数为空！");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
