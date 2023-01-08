package cn.flyingsoft.oais.service.webservice;

public class ExceptionException0 extends Exception {
  private OACallArchiveServiceStub.ExceptionE faultMessage;
  
  public ExceptionException0() {
    super("ExceptionException0");
  }
  
  public ExceptionException0(String s) {
    super(s);
  }
  
  public ExceptionException0(String s, Throwable ex) {
    super(s, ex);
  }
  
  public void setFaultMessage(OACallArchiveServiceStub.ExceptionE msg) {
    this.faultMessage = msg;
  }
  
  public OACallArchiveServiceStub.ExceptionE getFaultMessage() {
    return this.faultMessage;
  }
}
