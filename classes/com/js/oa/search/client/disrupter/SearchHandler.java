package com.js.oa.search.client.disrupter;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.lmax.disruptor.EventHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchHandler implements EventHandler<SearchEvent> {
  public void onEvent(SearchEvent searchEvent, long sequence, boolean endOfBatch) throws Exception {
    SearchBean searchBean = searchEvent.getSearchBean();
    String methodName = searchBean.getMethodName();
    String id = searchBean.getId();
    String tableName = searchBean.getTableName();
    addIndexOprateLog(methodName, tableName, id);
  }
  
  private void addIndexOprateLog(String method, String tableName, String id) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean shouldRecord = true;
      String sql = "select id from search_indexlist where search_type=? and module_type=? and data_id=? and status=0";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, method);
      pstmt.setString(2, tableName);
      pstmt.setString(3, id);
      rs = pstmt.executeQuery();
      if (rs.next())
        shouldRecord = false; 
      rs.close();
      pstmt.close();
      if (shouldRecord) {
        if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
          sql = "insert into search_indexlist(id,search_type,module_type,data_id,status) values(hibernate_sequence.nextval,?,?,?,0)";
        } else {
          sql = "insert into search_indexlist(search_type,module_type,data_id,status) values(?,?,?,0)";
        } 
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, method);
        pstmt.setString(2, tableName);
        pstmt.setString(3, id);
        pstmt.execute();
        pstmt.close();
      } 
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
}
