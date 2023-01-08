package com.guangda.sms;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

public final class MmsSenderSoap_MmsSenderSoap_Client {
  private static final QName SERVICE_NAME = new QName("http://cebbank.com/", "MmsSender");
  
  public static void main(String[] args) throws Exception {
    URL wsdlURL = MmsSender.WSDL_LOCATION;
    if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
      File wsdlFile = new File(args[0]);
      try {
        if (wsdlFile.exists()) {
          wsdlURL = wsdlFile.toURI().toURL();
        } else {
          wsdlURL = new URL(args[0]);
        } 
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } 
    } 
    MmsSender ss = new MmsSender(wsdlURL, SERVICE_NAME);
    MmsSenderSoap port = ss.getMmsSenderSoap();
    System.out.println("Invoking updatePwd...");
    String _updatePwd_userName = "";
    String _updatePwd_pwd = "";
    String _updatePwd_oldPwd = "";
    String _updatePwd__return = port.updatePwd(_updatePwd_userName, _updatePwd_pwd, _updatePwd_oldPwd);
    System.out.println("updatePwd.result=" + _updatePwd__return);
    System.out.println("Invoking login...");
    String _login_userName = "";
    String _login_pwd = "";
    boolean _login__return = port.login(_login_userName, _login_pwd);
    System.out.println("login.result=" + _login__return);
    System.out.println("Invoking send...");
    String _send_mobilePhoneNo = "";
    String _send_msg = "";
    String _send_srcCode = "";
    boolean _send__return = port.send(_send_mobilePhoneNo, _send_msg, _send_srcCode);
    System.out.println("send.result=" + _send__return);
    System.exit(0);
  }
}
