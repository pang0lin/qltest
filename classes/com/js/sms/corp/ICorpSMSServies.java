package com.js.sms.corp;

public interface ICorpSMSServies {
  void sendMessage(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, String paramString5, String paramString6, int paramInt);
  
  void sendMessage(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, String paramString5, String paramString6);
  
  void modelSendMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
}
