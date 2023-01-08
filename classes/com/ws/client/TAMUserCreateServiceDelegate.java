package com.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "TAMUserCreateServiceDelegate", targetNamespace = "http://user.tam.clamc.com/")
public interface TAMUserCreateServiceDelegate {
  @WebMethod
  @WebResult(targetNamespace = "")
  @RequestWrapper(localName = "addTAMUserInfo", targetNamespace = "http://user.tam.clamc.com/", className = "com.ws.client.AddTAMUserInfo")
  @ResponseWrapper(localName = "addTAMUserInfoResponse", targetNamespace = "http://user.tam.clamc.com/", className = "com.ws.client.AddTAMUserInfoResponse")
  boolean addTAMUserInfo(@WebParam(name = "arg0", targetNamespace = "") String paramString);
  
  @WebMethod
  @WebResult(targetNamespace = "")
  @RequestWrapper(localName = "modifyTAMUserInfo", targetNamespace = "http://user.tam.clamc.com/", className = "com.ws.client.ModifyTAMUserInfo")
  @ResponseWrapper(localName = "modifyTAMUserInfoResponse", targetNamespace = "http://user.tam.clamc.com/", className = "com.ws.client.ModifyTAMUserInfoResponse")
  boolean modifyTAMUserInfo(@WebParam(name = "arg0", targetNamespace = "") String paramString) throws Exception_Exception;
  
  @WebMethod
  @WebResult(targetNamespace = "")
  @RequestWrapper(localName = "delTAMUserInfo", targetNamespace = "http://user.tam.clamc.com/", className = "com.ws.client.DelTAMUserInfo")
  @ResponseWrapper(localName = "delTAMUserInfoResponse", targetNamespace = "http://user.tam.clamc.com/", className = "com.ws.client.DelTAMUserInfoResponse")
  boolean delTAMUserInfo(@WebParam(name = "arg0", targetNamespace = "") String paramString) throws Exception_Exception;
}
