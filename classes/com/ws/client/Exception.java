package com.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Exception", propOrder = {"message"})
public class Exception {
  protected String message;
  
  public String getMessage() {
    return this.message;
  }
  
  public void setMessage(String value) {
    this.message = value;
  }
}
