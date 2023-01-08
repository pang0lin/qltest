package cn.zzy.action;

import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLHelper {
  public static String data2xml(XmlData data) {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement("OASendNotifyRequest");
    Element transid = rootElement.addElement("transid");
    transid.addText((new StringBuilder(String.valueOf(data.getTransid()))).toString());
    Element oaname = rootElement.addElement("oaname");
    oaname.addText(data.getOaname());
    Element account = rootElement.addElement("account");
    account.addText(data.getAccount());
    Element password = rootElement.addElement("password");
    password.addText(data.getPassword());
    Element domain = rootElement.addElement("domain");
    domain.addText(data.getDomain());
    Element sender = rootElement.addElement("sender");
    sender.addText(data.getSender());
    Element sendername = rootElement.addElement("sendername");
    sendername.addText(data.getSendername());
    Element recvlist = rootElement.addElement("recvlist");
    recvlist.addText(data.getRecvlist());
    Element subject = rootElement.addElement("subject");
    subject.addText(data.getSubject());
    Element content = rootElement.addElement("content");
    content.addText(data.getContent());
    return document.asXML();
  }
  
  public static QResponse xml2data(InputStream inputs) {
    SAXReader reader = new SAXReader();
    try {
      Document d = reader.read(inputs);
      Element root = d.getRootElement();
      String transid = root.elementText("transid");
      String code = root.elementText("code");
      String error_des = root.elementText("error_des");
      return new QResponse(transid, code, error_des);
    } catch (DocumentException e) {
      e.printStackTrace();
      return null;
    } 
  }
}
