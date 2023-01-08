package com.js.oa.zky.bean;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ZkyAjaxBean {
  public List<String[]> getFieldInfo(String fields, String pageId) {
    String[] field = fields.split(",");
    String fieldSql = "'-'";
    for (int i = 0; i < field.length; i++)
      fieldSql = String.valueOf(fieldSql) + ",'" + field[i] + "'"; 
    String sql = "select a.area_table,t.table_id,t.table_desname,f.field_name,f.field_desname,f.field_type,f.field_show from tarea a join ttable t on a.area_table=t.table_name join tfield f on t.table_id=f.field_table where a.page_Id=" + 
      
      pageId + " and a.area_name='form1' and f.field_name in (" + fieldSql + ")";
    DataSourceUtil util = new DataSourceUtil();
    return util.getListQuery(sql, "");
  }
  
  public String recordIsExist(String sql, List<String> list) {
    String isExist = "not exist";
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    PreparedStatement pstat = null;
    try {
      conn = base.getDataSource().getConnection();
      pstat = conn.prepareStatement(sql);
      for (int i = 0; i < list.size(); i++)
        pstat.setString(i + 1, list.get(i)); 
      ResultSet rs = pstat.executeQuery();
      if (rs.next())
        isExist = "exist"; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        pstat.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return isExist;
  }
  
  public String getHidden(String sql) {
    String hidden = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        hidden = String.valueOf(hidden) + ((rs.getString(1) == null) ? "" : (String.valueOf(rs.getString(1)) + ",")); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return hidden;
  }
}
