package com.js.oa.app;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServlet extends HttpServlet {
  public void destroy() {
    super.destroy();
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("get");
    response.getWriter().print("get");
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("post");
    System.out.println(request.getParameter("userAccounts"));
    System.out.println(request.getParameter("userPassword"));
    System.out.println(request.getParameter("messageURL"));
    AppService.processMessageLink(request, response);
  }
  
  public void init() throws ServletException {}
}
