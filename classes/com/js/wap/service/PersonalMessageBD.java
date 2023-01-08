package com.js.wap.service;

import com.js.wap.bean.PersonalMessageBean;
import java.util.Map;
import org.apache.log4j.Logger;

public class PersonalMessageBD {
  private static Logger logger = Logger.getLogger(PersonalMessageBD.class.getName());
  
  private PersonalMessageBean bean = new PersonalMessageBean();
  
  public Map getMessageList(String userId, String orgId, String orgIdString, int beginIndex, int limited) throws Exception {
    Map map = null;
    try {
      map = this.bean.getMessageList(userId, orgId, orgIdString, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
      throw e;
    } 
    return map;
  }
  
  public void setRead(String chatId, String userId) throws Exception {
    try {
      this.bean.setRead(chatId, userId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  public void replyMessage(String userId, String userName, String toID, String content, String userNames) throws Exception {
    this.bean.replyMessage(userId, userName, toID, content, userNames);
  }
}
