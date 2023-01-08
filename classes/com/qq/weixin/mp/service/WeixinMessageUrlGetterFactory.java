package com.qq.weixin.mp.service;

import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Backlog;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Chat;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Cooperate;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Document;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Event;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Forum;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Info;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Metting;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_MonthPostil;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_Press;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_WorkLog;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_WorkLogComment;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_WorkReport;
import com.qq.weixin.mp.service.impl.WeixinMessageUrlGetterImpl_WorkReportPostil;

public class WeixinMessageUrlGetterFactory {
  public static WeixinMessageUrlGetter getWeixinMessageUrlGetter(String messageType) {
    WeixinMessageUrlGetter getter = null;
    if ("Meeting".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Metting();
    } else if ("Cooperate".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Cooperate();
    } else if ("Info".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Info();
    } else if ("jsflow".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Backlog();
    } else if ("Event".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Event();
    } else if ("Chat".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Chat();
    } else if ("Forum".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Forum();
    } else if ("WorkLog".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_WorkLog();
    } else if ("WorkReport".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_WorkReport();
    } else if ("WorkReportPostil".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_WorkReportPostil();
    } else if ("MonthPostil".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_MonthPostil();
    } else if ("Press".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Press();
    } else if ("WorkLogComment".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_WorkLogComment();
    } else if ("document".equals(messageType)) {
      getter = new WeixinMessageUrlGetterImpl_Document();
    } 
    return getter;
  }
}
