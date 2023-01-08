package com.js.oa.zky.bean;

import com.js.util.util.DataSourceUtil;

public class ZkyMenuBean {
  public boolean isMoKuaiManger(String userId, String menuId) {
    String sql = "select manger_id from zky_mangers where manger_users like '%$" + userId + "$%' and " + 
      "','||manger_menu||',' like '%," + menuId + ",%' and manger_type=1";
    if ((new DataSourceUtil()).getListQuery(sql, "").size() > 0)
      return true; 
    return false;
  }
  
  public boolean isJiXiaoManger(String userId, String menuId) {
    String sql = "select manger_id from zky_mangers where manger_users like '%$" + userId + "$%' and " + 
      "','||manger_menu||',' like '%," + menuId + ",%' and manger_type=2";
    if ((new DataSourceUtil()).getListQuery(sql, "").size() > 0)
      return true; 
    return false;
  }
}
