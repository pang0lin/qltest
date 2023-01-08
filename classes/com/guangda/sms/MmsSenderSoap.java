package com.guangda.sms;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.apache.axis2.jaxws.description.xml.handler.ObjectFactory;

@WebService(targetNamespace = "http://cebbank.com/", name = "MmsSenderSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface MmsSenderSoap {
  @WebResult(name = "UpdatePwdResult", targetNamespace = "http://cebbank.com/")
  @RequestWrapper(localName = "UpdatePwd", targetNamespace = "http://cebbank.com/", className = "com.fbstudio.csm.domain.message.UpdatePwd")
  @WebMethod(operationName = "UpdatePwd", action = "http://cebbank.com/UpdatePwd")
  @ResponseWrapper(localName = "UpdatePwdResponse", targetNamespace = "http://cebbank.com/", className = "com.fbstudio.csm.domain.message.UpdatePwdResponse")
  String updatePwd(@WebParam(name = "userName", targetNamespace = "http://cebbank.com/") String paramString1, @WebParam(name = "pwd", targetNamespace = "http://cebbank.com/") String paramString2, @WebParam(name = "oldPwd", targetNamespace = "http://cebbank.com/") String paramString3);
  
  @WebResult(name = "LoginResult", targetNamespace = "http://cebbank.com/")
  @RequestWrapper(localName = "Login", targetNamespace = "http://cebbank.com/", className = "com.fbstudio.csm.domain.message.Login")
  @WebMethod(operationName = "Login", action = "http://cebbank.com/Login")
  @ResponseWrapper(localName = "LoginResponse", targetNamespace = "http://cebbank.com/", className = "com.fbstudio.csm.domain.message.LoginResponse")
  boolean login(@WebParam(name = "userName", targetNamespace = "http://cebbank.com/") String paramString1, @WebParam(name = "pwd", targetNamespace = "http://cebbank.com/") String paramString2);
  
  @WebResult(name = "SendResult", targetNamespace = "http://cebbank.com/")
  @RequestWrapper(localName = "Send", targetNamespace = "http://cebbank.com/", className = "com.fbstudio.csm.domain.message.Send")
  @WebMethod(operationName = "Send", action = "http://cebbank.com/Send")
  @ResponseWrapper(localName = "SendResponse", targetNamespace = "http://cebbank.com/", className = "com.fbstudio.csm.domain.message.SendResponse")
  boolean send(@WebParam(name = "MobilePhoneNo", targetNamespace = "http://cebbank.com/") String paramString1, @WebParam(name = "msg", targetNamespace = "http://cebbank.com/") String paramString2, @WebParam(name = "srcCode", targetNamespace = "http://cebbank.com/") String paramString3);
}
