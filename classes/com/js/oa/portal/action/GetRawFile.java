package com.js.oa.portal.action;

import com.js.util.util.IO2File;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRawFile extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/xml");
    PrintWriter pw = resp.getWriter();
    IO2File.printFile("RSS访问路径：" + req.getParameter("url"), "rss");
    URL url = null;
    String urlPath = req.getParameter("url");
    if (urlPath != null && (urlPath.toLowerCase().startsWith("http") || urlPath.startsWith("/jsoa"))) {
      if (urlPath.contains("/jsoa/rss/show")) {
        Object object = req.getSession().getAttribute("userId");
        url = new URL(String.valueOf(urlPath) + "&" + object);
      } else {
        url = new URL(urlPath);
      } 
      DataInputStream dis = new DataInputStream(url.openStream());
      String line;
      while ((line = dis.readLine()) != null)
        pw.println(line); 
    } 
  }
}
