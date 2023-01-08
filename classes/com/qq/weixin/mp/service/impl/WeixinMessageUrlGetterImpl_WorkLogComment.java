package com.qq.weixin.mp.service.impl;

import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_WorkLogComment implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "/weixin/workReport/weixinReadReport.jsp?from=message&logId=";
    if (messageUrl != null && !"".equals(messageUrl) && messageUrl.indexOf("&logId=") > 0)
      url = String.valueOf(url) + messageUrl.substring(messageUrl.indexOf("&logId=") + 7); 
    return url;
  }
  
  public String[] getRemindInfo(String messageTitle, String messageUrl, String dataid, String emp_Id) {
    return new String[] { messageTitle, messageTitle, "" };
  }
}
