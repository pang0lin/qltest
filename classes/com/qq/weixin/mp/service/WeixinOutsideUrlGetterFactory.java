package com.qq.weixin.mp.service;

import com.qq.weixin.mp.service.impl.WeixinOutsideUrlGetterImpl_OutsideId;

public class WeixinOutsideUrlGetterFactory {
  public static WeixinOutsideUrlGetter getWeixinOutsideUrlGetter(String outsideType) {
    WeixinOutsideUrlGetter getter = null;
    if ("outside".equals(outsideType))
      getter = new WeixinOutsideUrlGetterImpl_OutsideId(); 
    return getter;
  }
}
