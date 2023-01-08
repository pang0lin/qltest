package com.qq.weixin.mp.service.impl;

import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_Document implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "/weixinGovReceiveFileBoxAction.do?action=load&from=message&id=" + dataId;
    return url;
  }
  
  public String[] getRemindInfo(String messageTitle, String messageUrl, String dataid, String emp_Id) {
    return new String[] { messageTitle, messageTitle, "" };
  }
}
