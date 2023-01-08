package cn.zzy.action;

import org.dom4j.Document;
import org.dom4j.Element;

public class QResponse {
  private String transid;
  
  private String code;
  
  private String error_des;
  
  private String root;
  
  private long sid;
  
  public long getSid() {
    return this.sid;
  }
  
  public void setSid(long sid) {
    this.sid = sid;
  }
  
  public String getTransid() {
    return this.transid;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public String getError_des() {
    return this.error_des;
  }
  
  public String getRoot() {
    return this.root;
  }
  
  public void setRoot(String root) {
    this.root = root;
  }
  
  public void setTransid(String transid) {
    this.transid = transid;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public void setError_des(String error_des) {
    this.error_des = error_des;
  }
  
  public QResponse(String transid, String code, String error_des) {
    this.transid = transid;
    this.code = code;
    this.error_des = error_des;
  }
  
  public QResponse(Document document) {
    Element root = document.getRootElement();
    this.root = root.getName();
    String transid = root.elementText("transid");
    String code = root.elementText("code");
    String error_des = root.elementText("error_des");
    this.transid = transid;
    this.code = code;
    this.error_des = error_des;
    String tmpid = root.elementText("sid");
    if (tmpid == null) {
      this.sid = 0L;
    } else {
      this.sid = Long.valueOf(tmpid).longValue();
    } 
  }
}
