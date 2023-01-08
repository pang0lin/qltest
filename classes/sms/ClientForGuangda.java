package sms;

import com.guangda.sms.MmsSenderSoap_MmsSenderSoap12_Client;

public class ClientForGuangda {
  private String serviceURL = "";
  
  public boolean ClientForGuangdaSendMessage(String receiver, String content, String sender) {
    boolean flag = false;
    MmsSenderSoap_MmsSenderSoap12_Client clientService = new MmsSenderSoap_MmsSenderSoap12_Client();
    String code = "00001";
    flag = clientService.MessageClient(receiver, content, code);
    return flag;
  }
}
