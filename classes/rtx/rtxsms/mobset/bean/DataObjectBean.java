package rtx.rtxsms.mobset.bean;

public class DataObjectBean {
  private String cordId;
  
  private String userName;
  
  private String passwd;
  
  private String serverIP;
  
  public String getCordId() {
    return this.cordId;
  }
  
  public void setCordId(String cordId) {
    this.cordId = cordId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getPasswd() {
    return this.passwd;
  }
  
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  
  public String getServerIP() {
    return this.serverIP;
  }
  
  public void setServerIP(String serverIP) {
    this.serverIP = serverIP;
  }
  
  public DataObjectBean() {}
  
  public DataObjectBean(String cordId, String userName, String passwd, String serverIP) {
    this.cordId = cordId;
    this.userName = userName;
    this.passwd = passwd;
    this.serverIP = serverIP;
  }
}
