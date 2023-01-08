package com.js.oa.zky.service;

import com.js.oa.zky.bean.SubmitBean;
import java.util.List;

public class SubmitBD {
  public String getTableName(String pageId) {
    SubmitBean bean = new SubmitBean();
    return bean.getTableName(pageId);
  }
  
  public void updateMqzt(String[] tableName, String mqzt, String nd) {
    updateMqzt(tableName, mqzt, nd, "");
  }
  
  public void updateMqzt(String[] tableName, String mqzt, String nd, String id) {
    SubmitBean bean = new SubmitBean();
    bean.updateMqzt(tableName, mqzt, nd, id);
  }
  
  public void backMqzt(String[] tableName, String mqzt, String nd) {
    backMqzt(tableName, mqzt, nd, "");
  }
  
  public void backMqzt(String[] tableName, String mqzt, String nd, String id) {
    SubmitBean bean = new SubmitBean();
    bean.backMqzt(tableName, mqzt, nd, id);
  }
  
  public List<String> getTableNameList() {
    SubmitBean bean = new SubmitBean();
    return bean.getTableNameList();
  }
}
