package com.js.oa.zky.service;

import com.js.oa.zky.bean.ZkyMenuBean;

public class ZkyMenuBD {
  public boolean isMoKuaiManger(String userId, String menuId) {
    ZkyMenuBean bean = new ZkyMenuBean();
    return bean.isMoKuaiManger(userId, menuId);
  }
  
  public boolean isJiXiaoManger(String userId, String menuId) {
    ZkyMenuBean bean = new ZkyMenuBean();
    return bean.isJiXiaoManger(userId, menuId);
  }
  
  public boolean isMoKuaiAndJiXiaoManger(String userId, String menuId) {
    return (isJiXiaoManger(userId, menuId) && isMoKuaiManger(userId, menuId));
  }
  
  public boolean isMoKuaiOrJiXiaoManger(String userId, String menuId) {
    return !(!isJiXiaoManger(userId, menuId) && !isMoKuaiManger(userId, menuId));
  }
}
