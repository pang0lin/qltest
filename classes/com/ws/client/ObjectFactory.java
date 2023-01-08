package com.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
  private static final QName _AddTAMUserInfoResponse_QNAME = new QName(
      "http://user.tam.clamc.com/", "addTAMUserInfoResponse");
  
  private static final QName _AddTAMUserInfo_QNAME = new QName(
      "http://user.tam.clamc.com/", "addTAMUserInfo");
  
  private static final QName _Exception_QNAME = new QName(
      "http://user.tam.clamc.com/", "Exception");
  
  private static final QName _DelTAMUserInfoResponse_QNAME = new QName(
      "http://user.tam.clamc.com/", "delTAMUserInfoResponse");
  
  private static final QName _ModifyTAMUserInfoResponse_QNAME = new QName(
      "http://user.tam.clamc.com/", "modifyTAMUserInfoResponse");
  
  private static final QName _ModifyTAMUserInfo_QNAME = new QName(
      "http://user.tam.clamc.com/", "modifyTAMUserInfo");
  
  private static final QName _DelTAMUserInfo_QNAME = new QName(
      "http://user.tam.clamc.com/", "delTAMUserInfo");
  
  public ModifyTAMUserInfo createModifyTAMUserInfo() {
    return new ModifyTAMUserInfo();
  }
  
  public ModifyTAMUserInfoResponse createModifyTAMUserInfoResponse() {
    return new ModifyTAMUserInfoResponse();
  }
  
  public DelTAMUserInfoResponse createDelTAMUserInfoResponse() {
    return new DelTAMUserInfoResponse();
  }
  
  public AddTAMUserInfoResponse createAddTAMUserInfoResponse() {
    return new AddTAMUserInfoResponse();
  }
  
  public DelTAMUserInfo createDelTAMUserInfo() {
    return new DelTAMUserInfo();
  }
  
  public AddTAMUserInfo createAddTAMUserInfo() {
    return new AddTAMUserInfo();
  }
  
  public Exception createException() {
    return new Exception();
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "addTAMUserInfoResponse")
  public JAXBElement<AddTAMUserInfoResponse> createAddTAMUserInfoResponse(AddTAMUserInfoResponse value) {
    return new JAXBElement(
        _AddTAMUserInfoResponse_QNAME, AddTAMUserInfoResponse.class, 
        null, value);
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "addTAMUserInfo")
  public JAXBElement<AddTAMUserInfo> createAddTAMUserInfo(AddTAMUserInfo value) {
    return new JAXBElement(_AddTAMUserInfo_QNAME, 
        AddTAMUserInfo.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "Exception")
  public JAXBElement<Exception> createException(Exception value) {
    return new JAXBElement(_Exception_QNAME, Exception.class, 
        null, value);
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "delTAMUserInfoResponse")
  public JAXBElement<DelTAMUserInfoResponse> createDelTAMUserInfoResponse(DelTAMUserInfoResponse value) {
    return new JAXBElement(
        _DelTAMUserInfoResponse_QNAME, DelTAMUserInfoResponse.class, 
        null, value);
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "modifyTAMUserInfoResponse")
  public JAXBElement<ModifyTAMUserInfoResponse> createModifyTAMUserInfoResponse(ModifyTAMUserInfoResponse value) {
    return new JAXBElement(
        _ModifyTAMUserInfoResponse_QNAME, 
        ModifyTAMUserInfoResponse.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "modifyTAMUserInfo")
  public JAXBElement<ModifyTAMUserInfo> createModifyTAMUserInfo(ModifyTAMUserInfo value) {
    return new JAXBElement(_ModifyTAMUserInfo_QNAME, 
        ModifyTAMUserInfo.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://user.tam.clamc.com/", name = "delTAMUserInfo")
  public JAXBElement<DelTAMUserInfo> createDelTAMUserInfo(DelTAMUserInfo value) {
    return new JAXBElement(_DelTAMUserInfo_QNAME, 
        DelTAMUserInfo.class, null, value);
  }
}
