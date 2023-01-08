package cn.zzy.action;

public class BasicRequest {
  private long transid;
  
  private String oaname;
  
  private String account;
  
  private String password;
  
  private String domain;
  
  private String sender;
  
  private String sendername;
  
  private String[] recvlist;
  
  public String toXml() {
    return "";
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
  
  public String getAccount() {
    return this.account;
  }
  
  public void setAccount(String account) {
    this.account = account;
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
  
  public String[] getRecvlist() {
    return this.recvlist;
  }
  
  public void setRecvlist(String[] recvlist) {
    this.recvlist = recvlist;
  }
  
  public String getRecvlistData() {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < this.recvlist.length; i++) {
      buffer.append(this.recvlist[i]);
      if (!this.recvlist[i].endsWith("@tjqnzyxy.cn"))
        buffer.append("@tjqnzyxy.cn"); 
      if (i != this.recvlist.length - 1)
        buffer.append(","); 
    } 
    return buffer.toString();
  }
  
  public String getSenderData() {
    if (!getSender().endsWith("@tjqnzyxy.cn"))
      return String.valueOf(getSender()) + "@tjqnzyxy.cn"; 
    return getSender();
  }
}
