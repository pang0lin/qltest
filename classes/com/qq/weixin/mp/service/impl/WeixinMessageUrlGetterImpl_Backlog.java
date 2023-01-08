package com.qq.weixin.mp.service.impl;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_Backlog implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String workId = "";
    String status = "";
    String mainLink = "";
    if (messageUrl != null && !"".equals(messageUrl)) {
      workId = messageUrl.substring(messageUrl.indexOf("workId=") + 7, messageUrl.length());
      String sql = "SELECT workstatus,WORKMAINLINKFILE FROM JSF_WORK where WF_WORK_ID=" + workId;
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
      if (result != null && result.length > 0) {
        status = result[0][0];
        mainLink = result[0][1];
      } 
      if (mainLink.indexOf("GovSendFileLoadAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("GovReceiveFileLoadAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("BoardRoomAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("WorkFlowProcAction.do") > -1)
        return "/weixin/workflow/weixinMessageTemp.jsp?workId=" + workId; 
      if (mainLink.indexOf("InformationAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("VoitureApplyAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("IntoStockAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("OutStockAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("EquipmentAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
      if (mainLink.indexOf("archivesAction.do") > -1)
        return "/weixin/backlog/item_info.jsp?workId=" + workId + "&status=" + status; 
    } 
    return "/weixin/workflow/weixinMessageTemp.jsp?workId=" + workId;
  }
  
  public String[] getRemindInfo(String messageTitle, String messageUrl, String dataid, String emp_Id) {
    return new String[] { messageTitle, messageTitle, "" };
  }
}
