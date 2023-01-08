package com.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delTAMUserInfo", propOrder = {"arg0"})
public class DelTAMUserInfo {
  protected String arg0;
  
  public String getArg0() {
    return this.arg0;
  }
  
  public void setArg0(String value) {
    this.arg0 = value;
  }
}
