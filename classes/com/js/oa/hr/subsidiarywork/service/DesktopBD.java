package com.js.oa.hr.subsidiarywork.service;

import com.js.oa.hr.subsidiarywork.bean.DesktopEJBBean;
import java.util.List;

public class DesktopBD {
  private DesktopEJBBean desktopEJBBean = new DesktopEJBBean();
  
  public List getWishRemind(String userId, String orgIdString) throws Exception {
    List list = null;
    list = this.desktopEJBBean.getWishRemind(userId, orgIdString);
    return list;
  }
  
  public void executeWishRemind() {
    this.desktopEJBBean.executeWishRemind();
  }
}
