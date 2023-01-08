package cn.flyingsoft.oais.service.webservice;

public abstract class OACallArchiveServiceCallbackHandler {
  protected Object clientData;
  
  public OACallArchiveServiceCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }
  
  public OACallArchiveServiceCallbackHandler() {
    this.clientData = null;
  }
  
  public Object getClientData() {
    return this.clientData;
  }
  
  public void receiveResultfinishWorkFlow() {}
  
  public void receiveErrorfinishWorkFlow(Exception e) {}
  
  public void receiveResultgetWfUsingFormEntity(OACallArchiveServiceStub.GetWfUsingFormEntityResponse result) {}
  
  public void receiveErrorgetWfUsingFormEntity(Exception e) {}
}
