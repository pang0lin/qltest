package com.js.oa.logon.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class CAAnalysisXml {
  public static String getXML(String xml) throws JDOMException, IOException {
    String message = "";
    SAXBuilder builder = new SAXBuilder();
    ByteArrayInputStream xmlStream = null;
    try {
      xmlStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    } 
    Document doc = builder.build(xmlStream);
    Element root = doc.getRootElement();
    String messageStateFlag = "";
    Element head = root.getChild("head");
    if (head != null) {
      Element messageState = head.getChild("messageState");
      if (messageState != null) {
        messageStateFlag = (new StringBuilder(String.valueOf(messageState.getText()))).toString();
        if ("false".equals(messageStateFlag)) {
          Element body = root.getChild("body");
          if (body != null) {
            Element authResultSet = body.getChild("authResultSet");
            if (authResultSet != null) {
              Element authResult = authResultSet.getChild("authResult");
              if (authResult != null) {
                String success = authResult.getAttributeValue("success");
                if ("true".equals(success))
                  message = "true"; 
              } 
            } 
          } 
        } else if ("true".equals(messageStateFlag)) {
          Element authMessageCode = head.getChild("authMessageCode");
          Element authMessageDesc = head.getChild("authMessageDesc");
          if (authMessageCode != null && authMessageDesc != null)
            message = String.valueOf(authMessageDesc.getText()); 
        } 
      } 
    } 
    return message;
  }
}
