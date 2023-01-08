package com.jspsmart.upload;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUpload extends HttpServlet {
  private ServletConfig config = null;
  
  public final void init(ServletConfig servletconfig) throws ServletException {
    this.config = servletconfig;
  }
  
  public final ServletConfig getServletConfig() {
    return this.config;
  }
  
  public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
    PrintWriter printwriter = httpservletresponse.getWriter();
    printwriter.println("<HTML>");
    printwriter.println("<BODY BGCOLOR='white'>");
    printwriter.println("<H1>jspSmartUpload : Servlet Sample</H1>");
    printwriter.println("<HR><BR>");
    printwriter.println("The method of the HTML form must be POST.");
    printwriter.println("</BODY>");
    printwriter.println("</HTML>");
  }
  
  protected void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
    PrintWriter printwriter = httpservletresponse.getWriter();
    printwriter.println("<HTML>");
    printwriter.println("<BODY BGCOLOR='white'>");
    printwriter.println("<H1>jspSmartUpload : Servlet Sample</H1>");
    printwriter.println("<HR>");
    boolean flag = false;
    SmartUpload smartupload = new SmartUpload();
    try {
      smartupload.initialize(this.config, httpservletrequest, httpservletresponse);
      smartupload.upload();
      int i = smartupload.save(smartupload.getRequest().getParameter("PATH"));
      printwriter.println(String.valueOf(i) + " file uploaded.");
    } catch (Exception exception) {
      printwriter.println("Unable to upload the file.<br>");
      printwriter.println("Error : " + exception.toString());
    } 
    printwriter.println("</BODY>");
    printwriter.println("</HTML>");
  }
  
  public void destroy() {}
}
