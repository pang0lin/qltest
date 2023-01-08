package com.ws.client;

import javax.xml.ws.WebFault;

@WebFault(name = "Exception", targetNamespace = "http://user.tam.clamc.com/")
public class Exception_Exception extends java.lang.Exception {
  private Exception faultInfo;
  
  public Exception_Exception(String message, Exception faultInfo) {
    super(message);
    this.faultInfo = faultInfo;
  }
  
  public Exception_Exception(String message, Exception faultInfo, Throwable cause) {
    super(message, cause);
    this.faultInfo = faultInfo;
  }
  
  public Exception getFaultInfo() {
    return this.faultInfo;
  }
}
