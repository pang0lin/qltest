package com.qq.weixin.mp.service.impl;

import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_Forum implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    String mainForumId = "";
    if (messageUrl != null && !"".equals(messageUrl)) {
      mainForumId = messageUrl.substring(messageUrl.indexOf("mainForumId=") + 12, messageUrl.length());
      url = "/weiXinLtggAction.do?action=bbsInfo&kit=yes&forumId=" + mainForumId;
    } 
    return url;
  }
  
  public String[] getRemindInfo(String title, String messageUrl, String dataid, String emp_Id) {
    return new String[] { title, title, "" };
  }
}
