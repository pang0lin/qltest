package com.qq.weixin.mp.service.impl;

import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_Event implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    if (messageUrl != null && !"".equals(messageUrl))
      url = "/weiXinRcapAction.do?action=rcapInfo&from=message&eventId=" + dataId; 
    return url;
  }
  
  public String[] getRemindInfo(String title, String messageUrl, String dataid, String emp_Id) {
    return new String[] { title, title, "" };
  }
}
