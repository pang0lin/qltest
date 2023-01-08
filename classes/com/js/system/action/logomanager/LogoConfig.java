package com.js.system.action.logomanager;

import com.js.oa.webmail.util.str;
import com.js.system.util.StaticParam;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoConfig extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String isDisplayCompanyName = request.getParameter("isDisplayCompanyName");
    String isDisplayLogo = request.getParameter("isDisplayLogo");
    if (isDisplayCompanyName == null) {
      isDisplayCompanyName = "1";
    } else {
      isDisplayCompanyName = "0";
    } 
    if (isDisplayLogo == null) {
      isDisplayLogo = "1";
    } else {
      isDisplayLogo = "0";
    } 
    String companyColor = request.getParameter("companyColor");
    String companyName = request.getParameter("companyName");
    companyName = str.convertToGBK(companyName);
    HttpSession session = request.getSession();
    String domainId = (String)session.getAttribute("domainId");
    Boolean result = Boolean.valueOf(StaticParam.updateSysLogoConfig(domainId, companyName, companyColor, isDisplayCompanyName, isDisplayLogo));
    if (result.booleanValue()) {
      response.sendRedirect("logo.do?method=updateLogoComplete");
    } else {
      response.sendRedirect("logo.do?method=updateLogoFailed");
    } 
  }
}
