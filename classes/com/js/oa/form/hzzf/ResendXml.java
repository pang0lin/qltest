package com.js.oa.form.hzzf;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResendXml {
  public static void resendXMl() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "SELECT XML FROM SENDFAILTABLE";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String xml = rs.getString(1);
        String sendStatus = SendPostUtil.sendFile(xml);
        if ("S".equals(sendStatus))
          SendPostUtil.deleteFailRecord(xml); 
      } 
    } catch (SQLException e) {
      SendPostUtil.free(rs, pstmt, conn);
      e.printStackTrace();
    } 
  }
}
