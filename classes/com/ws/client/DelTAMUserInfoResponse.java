package com.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delTAMUserInfoResponse", propOrder = {"_return"})
public class DelTAMUserInfoResponse {
  @XmlElement(name = "return")
  protected boolean _return;
  
  public boolean isReturn() {
    return this._return;
  }
  
  public void setReturn(boolean value) {
    this._return = value;
  }
}
