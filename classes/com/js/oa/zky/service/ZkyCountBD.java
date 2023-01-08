package com.js.oa.zky.service;

import com.js.oa.zky.bean.ZkyCountBean;
import com.js.util.config.SystemCommon;
import java.util.ArrayList;
import java.util.List;

public class ZkyCountBD {
  public List<String[]> getCountData(String gh) {
    String sql = "select menu_name,menu_listtblName,id from menu_ext where menu_iszky=1 and menu_zkyType=0 order by id";
    ZkyCountBean bean = new ZkyCountBean();
    List<String[]> menus = bean.getListData(sql);
    List<String[]> countData = (List)new ArrayList<String>();
    for (int i = 0; i < menus.size(); i++) {
      String[] menu = menus.get(i);
      String[] tableName = menu[1].split("_");
      String[] data = new String[5];
      data[0] = menu[0];
      data[1] = "/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + menu[2];
      sql = "select count(" + menu[1] + "_id) from " + menu[1] + " where " + tableName[1] + "_nd='" + SystemCommon.getZkyNd() + "' and " + tableName[1] + "_mqzt=0 and " + tableName[1] + "_gh='" + gh + "'";
      data[2] = bean.queryStrBySql(sql);
      sql = "select count(" + menu[1] + "_id) from " + menu[1] + " where " + tableName[1] + "_nd='" + SystemCommon.getZkyNd() + "' and " + tableName[1] + "_mqzt=1 and " + tableName[1] + "_gh='" + gh + "'";
      data[3] = bean.queryStrBySql(sql);
      sql = "select count(" + menu[1] + "_id) from " + menu[1] + " where " + tableName[1] + "_nd='" + SystemCommon.getZkyNd() + "' and " + tableName[1] + "_mqzt=2 and " + tableName[1] + "_gh='" + gh + "'";
      data[4] = bean.queryStrBySql(sql);
      countData.add(data);
    } 
    return countData;
  }
}
