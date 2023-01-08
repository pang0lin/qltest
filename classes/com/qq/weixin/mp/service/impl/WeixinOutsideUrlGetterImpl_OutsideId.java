package com.qq.weixin.mp.service.impl;

import com.qq.weixin.mp.service.WeixinOutsideUrlGetter;

public class WeixinOutsideUrlGetterImpl_OutsideId implements WeixinOutsideUrlGetter {
  public String getWeiXinUrl(String outsideId, String currentUserId) {
    String url = "";
    if (outsideId != null && !"".equals(outsideId))
      url = "/weixin/zxdk/myOutsideComplete.jsp?outsideId=" + outsideId; 
    return url;
  }
}
