package com.js.oa.webservice.langxin;

public abstract class GetFunc_codeCallbackHandler {
  protected Object clientData;
  
  public GetFunc_codeCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }
  
  public GetFunc_codeCallbackHandler() {
    this.clientData = null;
  }
  
  public Object getClientData() {
    return this.clientData;
  }
  
  public void receiveResultgetCode(GetFunc_codeStub.GetCodeResponse result) {}
  
  public void receiveErrorgetCode(Exception e) {}
  
  public void receiveResultgetMessage(GetFunc_codeStub.GetMessageResponse result) {}
  
  public void receiveErrorgetMessage(Exception e) {}
  
  public void receiveResultgetShortMessage(GetFunc_codeStub.GetShortMessageResponse result) {}
  
  public void receiveErrorgetShortMessage(Exception e) {}
  
  public void receiveResultgetToken(GetFunc_codeStub.GetTokenResponse result) {}
  
  public void receiveErrorgetToken(Exception e) {}
}
