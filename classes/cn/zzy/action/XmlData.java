package cn.zzy.action;

public class XmlData {
  private long transid;
  
  private String oaname;
  
  private String account;
  
  private String password;
  
  private String domain;
  
  private String sender;
  
  private String sendername;
  
  private String recvlist;
  
  private String subject;
  
  private String content;
  
  public XmlData() {}
  
  public XmlData(long transid, String oaname, String password, String domain, String sender, String sendername, String recvlist, String subject, String content, String account) {
    this.transid = transid;
    this.oaname = oaname;
    this.password = password;
    this.domain = domain;
    this.sender = sender;
    this.sendername = sendername;
    this.recvlist = recvlist;
    this.subject = subject;
    this.content = content;
    this.account = account;
  }
  
  public long getTransid() {
    return this.transid;
  }
  
  public void setTransid(long transid) {
    this.transid = transid;
  }
  
  public String getOaname() {
    return this.oaname;
  }
  
  public void setOaname(String oaname) {
    this.oaname = oaname;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getDomain() {
    return this.domain;
  }
  
  public void setDomain(String domain) {
    this.domain = domain;
  }
  
  public String getSender() {
    return this.sender;
  }
  
  public void setSender(String sender) {
    this.sender = sender;
  }
  
  public String getSendername() {
    return this.sendername;
  }
  
  public void setSendername(String sendername) {
    this.sendername = sendername;
  }
  
  public String getRecvlist() {
    return this.recvlist;
  }
  
  public void setRecvlist(String recvlist) {
    this.recvlist = recvlist;
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
  
  public String getAccount() {
    return this.account;
  }
  
  public void setAccount(String account) {
    this.account = account;
  }
}
