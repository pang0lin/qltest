package com.js.sms;

import SmgwClient.ErrorCode;
import SmgwClient.SmgpMsgID;
import SmgwClient.UserInterface;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class TelecomSendEX {
  public static String telecomSend(String phonenum, String msgContent, String teleCode) {
    UserInterface ui = new UserInterface();
    ErrorCode err = new ErrorCode();
    int ret = ui.InitSMGPAPI(err);
    String resultStatus = "1";
    if (ret != 0)
      return resultStatus; 
    SmgpMsgID msgid = new SmgpMsgID();
    int nMsgType = Integer.parseInt("6");
    int nNeedReport = Integer.parseInt("0");
    int nMsgLevel = Integer.parseInt("1");
    String nServiceID = "PC2P";
    int nMsgFormat = Integer.parseInt("15");
    String sFeeType = "00";
    String sFeeCode = "";
    String sFixedFee = "";
    String sValidTime = "";
    String sAtTime = "";
    String sChargeTermID = phonenum;
    String sDestTermID = phonenum;
    String sReplyPath = "106592605" + teleCode;
    int nMsgLen = (msgContent.getBytes()).length;
    byte[] sMsgContent = msgContent.getBytes();
    byte[] sReserve = "".getBytes();
    int ntlvlength = 0;
    byte[] stlvbuffer = " ".getBytes();
    ret = ui.SMGPSendSingleEX(nMsgType, 
        nNeedReport, 
        nMsgLevel, 
        nServiceID, 
        nMsgFormat, 
        sFeeType, 
        sFeeCode, 
        sFixedFee, 
        sValidTime, 
        sAtTime, 
        sChargeTermID, 
        sDestTermID, 
        sReplyPath, 
        nMsgLen, 
        sMsgContent, 
        sReserve, 
        msgid, 
        err, 
        ntlvlength, 
        stlvbuffer);
    if (ret == 0)
      resultStatus = "0"; 
    try {
      Thread.sleep(200L);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return resultStatus;
  }
  
  private static String rhex(byte[] in) {
    DataInputStream data = new DataInputStream(new ByteArrayInputStream(in));
    String str = "0x";
    try {
      for (int j = 0; j < in.length; j++) {
        String tmp = Integer.toHexString(data.readUnsignedByte());
        if (tmp.length() == 1)
          tmp = "0" + tmp; 
        str = String.valueOf(str) + tmp;
      } 
    } catch (Exception exception) {}
    return str;
  }
}
