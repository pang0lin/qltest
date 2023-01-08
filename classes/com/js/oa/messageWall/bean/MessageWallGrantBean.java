package com.js.oa.messageWall.bean;

import com.js.oa.messageWall.po.MessageWallGrantPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.sf.hibernate.HibernateException;

public class MessageWallGrantBean extends HibernateBase {
  public MessageWallGrantPO getMWGrant() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    MessageWallGrantPO mg = null;
    String sql = "select * from OA_MESSAGEWALL_GRANT";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next())
        mg = new MessageWallGrantPO(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
            rs.getString(6), rs.getString(7), rs.getString(8), rs.getLong(9), rs.getLong(10)); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (ps != null)
          ps.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return mg;
  }
  
  public void updateMWGrant(MessageWallGrantPO mg) {
    try {
      begin();
      this.session.update(mg);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
  }
}
