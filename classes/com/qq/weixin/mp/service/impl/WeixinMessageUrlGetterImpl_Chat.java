package com.qq.weixin.mp.service.impl;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_Chat implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    if (messageUrl != null && !"".equals(messageUrl))
      url = "/weiXinMessageAction.do?method=showMessage&from=1&chatId=" + dataId; 
    return url;
  }
  
  public String[] getRemindInfo(String title, String messageUrl, String dataid, String emp_Id) {
    int startIndex = messageUrl.indexOf("id=") + 3;
    int endIndex = (messageUrl.indexOf("&") > 0) ? messageUrl.indexOf("&") : messageUrl.length();
    String chatId = messageUrl.substring(startIndex, endIndex);
    String sql = "SELECT chat_content,sender_name FROM oa_chat WHERE chat_id=" + chatId;
    String[][] result = (String[][])null;
    DbOpt db = null;
    String content = "";
    String sender = "";
    try {
      db = new DbOpt();
      result = db.executeQueryToStrArr2(sql);
      db.close();
    } catch (Exception e) {
      if (db != null)
        try {
          db.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    if (result != null && result.length > 0) {
      content = result[0][0];
      sender = result[0][1];
    } 
    content = (content.length() > 30) ? (String.valueOf(content.substring(0, 30)) + "...") : content;
    sender = String.valueOf(sender) + "发来新消息：";
    return new String[] { sender, content, "" };
  }
}
