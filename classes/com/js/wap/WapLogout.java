package com.js.wap;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class WapLogout extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      response.sendRedirect("/jsoa/wap.jsp");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private String toStringFromDoc(Document document) {
    String result = null;
    if (document != null) {
      StringWriter strWtr = new StringWriter();
      StreamResult strResult = new StreamResult(strWtr);
      TransformerFactory tfac = TransformerFactory.newInstance();
      try {
        Transformer t = tfac.newTransformer();
        t.setOutputProperty("encoding", "UTF-8");
        t.setOutputProperty("indent", "yes");
        t.setOutputProperty("method", "xml");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        t.transform(new DOMSource(document.getDocumentElement()), strResult);
      } catch (Exception e) {
        System.err.println("XML.toString(Document): " + e);
      } 
      result = strResult.getWriter().toString();
      try {
        strWtr.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
}
