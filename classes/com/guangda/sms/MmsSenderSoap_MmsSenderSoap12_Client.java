package com.guangda.sms;

import java.net.URL;
import javax.xml.namespace.QName;

public final class MmsSenderSoap_MmsSenderSoap12_Client {
  private static final QName SERVICE_NAME = new QName("http://cebbank.com/", "MmsSender");
  
  public boolean MessageClient(String moblieNo, String msg, String code) {
    URL wsdlURL = MmsSender.WSDL_LOCATION;
    MmsSender ss = new MmsSender(wsdlURL, SERVICE_NAME);
    MmsSenderSoap port = ss.getMmsSenderSoap12();
    boolean _send__return = port.send(moblieNo, msg, code);
    return _send__return;
  }
  
  public static void main(String[] args) throws Exception {
    URL wsdlURL = MmsSender.WSDL_LOCATION;
    MmsSender ss = new MmsSender(wsdlURL, SERVICE_NAME);
    MmsSenderSoap port = ss.getMmsSenderSoap12();
    System.out.println("Invoking send...");
    String _send_mobilePhoneNo = "";
    String _send_msg = "";
    String _send_srcCode = "";
    boolean _send__return = port.send(_send_mobilePhoneNo, _send_msg, _send_srcCode);
    System.out.println("send.result=" + _send__return);
  }
}
