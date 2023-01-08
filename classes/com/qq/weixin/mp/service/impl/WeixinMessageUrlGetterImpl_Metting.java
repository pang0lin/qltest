package com.qq.weixin.mp.service.impl;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_Metting implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    String boardroomApplyId = "";
    if (messageUrl != null && !"".equals(messageUrl)) {
      boardroomApplyId = messageUrl.substring(messageUrl.indexOf("boardroomApplyId=") + 17, messageUrl.indexOf("&boardroomName="));
      url = "/weixin/hytz/hytzInfo.jsp?boardroomApplyId=" + boardroomApplyId + "&message=yes";
    } 
    return url;
  }
  
  public String[] getRemindInfo(String title, String messageUrl, String dataid, String emp_Id) {
    String meetingId = "0";
    String content = "会议议题：";
    if (messageUrl.indexOf("boardroomApplyId=") >= 0)
      meetingId = messageUrl.substring(messageUrl.indexOf("boardroomApplyId=") + 17, messageUrl.indexOf("&boardroomName=")); 
    String sql = "SELECT depict,1 FROM oa_boardroomapply WHERE BOARDROOMAPPLYID=" + meetingId;
    String[][] result = (String[][])null;
    DbOpt db = null;
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
    if (result != null && result.length > 0)
      content = String.valueOf(content) + result[0][0]; 
    return new String[] { title, content, "" };
  }
}
