package com.qq.weixin.mp.service;

public interface WeixinMessageUrlGetter {
  String getWeiXinUrl(String paramString1, String paramString2, String paramString3);
  
  String[] getRemindInfo(String paramString1, String paramString2, String paramString3, String paramString4);
}
