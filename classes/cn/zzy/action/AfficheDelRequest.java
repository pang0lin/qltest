package cn.zzy.action;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class AfficheDelRequest extends BasicRequest {
  private long sid;
  
  public long getSid() {
    return this.sid;
  }
  
  public void setSid(long sid) {
    this.sid = sid;
  }
  
  public String toXml() {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement("OADeleteAfficheRequest");
    Element transid = rootElement.addElement("transid");
    transid.addText((new StringBuilder(String.valueOf(getTransid()))).toString());
    Element oaname = rootElement.addElement("oaname");
    oaname.addText(getOaname());
    Element account = rootElement.addElement("account");
    account.addText(getAccount());
    Element password = rootElement.addElement("password");
    password.addText(getPassword());
    Element domain = rootElement.addElement("domain");
    if (getDomain() == null || getDomain().length() == 0)
      setDomain("tjqnzyxy.cn"); 
    domain.addText(getDomain());
    Element sid = rootElement.addElement("sid");
    sid.addText(String.valueOf(getSid()));
    return document.asXML();
  }
}
