package com.js.oa.messageWall.bean;

import com.js.oa.messageWall.po.MessageWallPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class MessageWallBean extends HibernateBase {
  public long addMessage(MessageWallPO mw) {
    long messageWallId = 0L;
    try {
      begin();
      messageWallId = ((Long)this.session.save(mw)).longValue();
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
    return messageWallId;
  }
  
  public int updateMessage(MessageWallPO mw) {
    int n = 0;
    try {
      begin();
      this.session.update(mw);
      this.session.flush();
      n = 1;
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return n;
  }
  
  public int deleteMessage(long messageWallId) {
    Connection conn = null;
    PreparedStatement ps = null;
    int n = 0;
    String sql = "delete from OA_MESSAGEWALL where MESSAGEWALL_ID = " + messageWallId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      n = ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (ps != null)
          ps.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return n;
  }
  
  public List<MessageWallPO> showAll(String whereSQL) {
    StringBuffer sb = new StringBuffer("select * from OA_MESSAGEWALL m");
    List<MessageWallPO> mws = new ArrayList<MessageWallPO>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    MessageWallPO m = null;
    if (whereSQL != null && !"".equals(whereSQL)) {
      sb.append(" where ");
      sb.append(whereSQL);
    } 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sb.toString());
      rs = ps.executeQuery();
      while (rs.next()) {
        m = new MessageWallPO(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getLong(4), rs.getString(5), 
            rs.getString(6), rs.getString(7), rs.getString(8), rs.getTimestamp(9), rs.getInt(10));
        mws.add(m);
      } 
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
    return mws;
  }
  
  public int getCurNum(long userId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int num = 0;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement("select count(*) from OA_MESSAGEWALL where messagewall_empid=" + userId);
      rs = ps.executeQuery();
      while (rs.next())
        num = rs.getInt(1); 
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
    return num;
  }
  
  public int getMaxNum() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int num = 0;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement("select wallmaxnum from OA_MESSAGEWALL_GRANT");
      rs = ps.executeQuery();
      while (rs.next())
        num = rs.getInt(1); 
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
    return num;
  }
  
  public boolean getRightByUserId(long userId) {
    StringBuffer sb = new StringBuffer("select * from oa_messagewall_grant where wallreader like '%$" + userId + "$%' ");
    boolean flag = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String orgSQL = "SELECT org_id FROM org_organization_user WHERE emp_id=" + userId;
    String groupSQL = "SELECT * FROM org_group WHERE GROUPUSERSTRING LIKE '%$" + userId + "$%'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(orgSQL);
      rs = ps.executeQuery();
      while (rs.next())
        sb.append(" or wallreadorg like '%*" + rs.getLong(1) + "*%' "); 
      sb.append(" or wallreadorg like '%*-1*%' ");
      ps = conn.prepareStatement(groupSQL);
      rs = ps.executeQuery();
      while (rs.next())
        sb.append(" or wallreadgroup like '%@" + rs.getLong(1) + "@%' "); 
      ps = conn.prepareStatement(sb.toString());
      rs = ps.executeQuery();
      if (rs.next())
        flag = true; 
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
    return flag;
  }
  
  public boolean isAdmin(long userId) {
    String sql = "select * from oa_messagewall_grant where wallmanagerid like '%$" + userId + "$%' ";
    boolean flag = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next())
        flag = true; 
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
    return flag;
  }
}
