package com.js.oa.form.servlet;

import com.js.oa.form.ClientInfoFromWeb;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class WebServiceProxy extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setExpandEntityReferences(false);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse((InputStream)request.getInputStream());
      String xml = toStringFromDoc(doc);
      String action = doc.getElementsByTagName("Action").item(0).getFirstChild().getNodeValue();
      ClientInfoFromWeb webservice = new ClientInfoFromWeb();
      String result = "";
      if ("createNewProcess".equals(action)) {
        result = webservice.createNewProcess(xml);
      } else if ("toNextActivity".equals(action)) {
        result = webservice.toNextActivity(xml);
      } else if ("completeProcess".equals(action)) {
        result = webservice.completeProcess(xml);
      } 
      StringBuffer buffer = new StringBuffer(1024);
      response.setContentType("text/xml;charset=GBK");
      PrintWriter out = response.getWriter();
      buffer.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
      buffer.append("<message>\n");
      buffer.append("  <result>" + result + "</result>\n");
      buffer.append("</message>\n");
      out.print(buffer.toString());
      out.close();
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
