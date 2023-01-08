package com.js.oa.weixin.document.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.Query;

public class WeiXinReceiveFileBean extends HibernateBase {
  public Map getReceiveFilePaperList(String hql, int beginIndex, int limit) throws Exception {
    List list = null;
    Map<Object, Object> map = new HashMap<Object, Object>();
    int size = 0;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      size = query.list().size();
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      map.put("list", list);
      map.put("size", Integer.valueOf(size));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Map<String, String> getDocInfo(String id) {
    Map<String, String> map = new HashMap<String, String>();
    String databaseType = SystemCommon.getDatabaseType();
    String createTimeString = "";
    if (databaseType.indexOf("mysql") >= 0) {
      createTimeString = " date_format(a.createdtime,'%Y-%m-%d') createtime ";
    } else {
      createTimeString = " to_char(a.createdtime,'yyyy-mm-dd') createtime ";
    } 
    String sql = "select documentsendfile_bytenumber,documentsendfile_title,(select orgname from org_organization where org_id=a.createdorg) orgname," + createTimeString + ",contentaccsavename,accessoryname,accessorysavename,sendfile_goldgridid from doc_documentsendfile a where documentsendfile_id =" + id;
    String[][] result = (String[][])null;
    DbOpt db = new DbOpt();
    try {
      result = db.executeQueryToStrArr2(sql, 8);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    if (result != null && result.length > 0) {
      map.put("wh", result[0][0]);
      map.put("title", result[0][1]);
      map.put("fwzz", result[0][2]);
      map.put("fwsj", result[0][3]);
      if (result[0][4] == null || "".equals(result[0][4])) {
        map.put("zw", String.valueOf(result[0][7]) + ".doc");
      } else {
        map.put("zw", result[0][4]);
      } 
      map.put("fj", result[0][5]);
      map.put("fjlong", result[0][6]);
    } 
    return map;
  }
}
