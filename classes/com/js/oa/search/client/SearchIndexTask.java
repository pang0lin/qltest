package com.js.oa.search.client;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.util.TimerTask;

public class SearchIndexTask extends TimerTask {
  public void run() {
    String[][] dataList = getIndexOprateList();
    while (dataList != null && dataList.length > 0) {
      for (int i = 0; i < dataList.length; i++) {
        try {
          String id = dataList[i][0];
          String methodName = dataList[i][1];
          String tableName = dataList[i][2];
          String dataId = dataList[i][3];
          if ("addIndex".equals(methodName)) {
            SearchServiceImpl.getInstance();
            SearchServiceImpl.addIndex(dataId, tableName);
          } else if ("updateIndex".equals(methodName)) {
            SearchServiceImpl.getInstance();
            SearchServiceImpl.updateIndex(dataId, tableName);
          } else if ("deleteIndex".equals(methodName)) {
            SearchServiceImpl.getInstance();
            SearchServiceImpl.deleteIndex(dataId, tableName);
          } else if ("commit".equals(methodName)) {
            SearchServiceImpl.getInstance();
            SearchServiceImpl.commit();
          } else if ("reConstructIndex".equals(methodName)) {
            SearchServiceImpl.getInstance();
            SearchServiceImpl.reConstructIndex();
          } 
          DbOpt dbopt = null;
          try {
            dbopt = new DbOpt();
            dbopt.executeUpdate("update search_indexlist set status=1 where id=" + id);
            dbopt.close();
          } catch (Exception ex) {
            if (dbopt != null)
              try {
                dbopt.close();
              } catch (Exception err) {
                err.printStackTrace();
              }  
            ex.printStackTrace();
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
      dataList = getIndexOprateList();
    } 
  }
  
  private String[][] getIndexOprateList() {
    String[][] list = (String[][])null;
    String sql = "";
    if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
      sql = "select id,search_type,module_type,data_id,status from search_indexlist where status=0 and rownum<100 order by id asc";
    } else {
      sql = "select id,search_type,module_type,data_id,status from search_indexlist where status=0 order by id asc limit 0,100";
    } 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(sql, 4);
      dbopt.close();
    } catch (Exception ex) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return list;
  }
}
