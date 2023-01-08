package cn.zzy.action;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class NotifyRequest extends BasicRequest {
  private String subject;
  
  private String content;
  
  public NotifyRequest() {}
  
  public NotifyRequest(long transid, String oaname, String password, String domain, String sender, String sendername, String[] recvlist, String subject, String content, String account) {
    setAccount(account);
    setDomain(domain);
    setOaname(oaname);
    setPassword(password);
    setRecvlist(recvlist);
    setSender(sender);
    setSendername(sendername);
    setTransid(transid);
    this.subject = subject;
    this.content = content;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String toString() {
    return super.toString();
  }
  
  public String toXml() {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement("OASendNotifyRequest");
    Element transid = rootElement.addElement("transid");
    transid.addText((new StringBuilder(String.valueOf(getTransid()))).toString());
    if (getOaname() != null && getOaname().length() > 0) {
      Element oaname = rootElement.addElement("oaname");
      oaname.addText(getOaname());
    } 
    Element account = rootElement.addElement("account");
    account.addText(getAccount());
    Element password = rootElement.addElement("password");
    password.addText(getPassword());
    Element domain = rootElement.addElement("domain");
    if (getDomain() == null || getDomain().length() == 0)
      setDomain("tjqnzyxy.cn"); 
    domain.addText(getDomain());
    if (getSender() != null && getSender().length() > 0) {
      Element sender = rootElement.addElement("sender");
      sender.addText(getSenderData());
    } 
    if (getSendername() != null && getSendername().length() > 0) {
      Element sendername = rootElement.addElement("sendername");
      sendername.addText(getSendername());
    } 
    if (getRecvlist() != null && (getRecvlist()).length > 0) {
      Element recvlist = rootElement.addElement("recvlist");
      recvlist.addText(getRecvlistData());
    } 
    Element subject = rootElement.addElement("subject");
    subject.addText(getSubject());
    Element content = rootElement.addElement("content");
    content.addText(getContent());
    return document.asXML();
  }
}
