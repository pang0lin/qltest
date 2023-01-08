package com.js.oa.haier.bean;

import com.js.oa.haier.po.ERPStockPO;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class ERPStackEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void saveERPStack(List<ERPStockPO> list) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "insert into JST_3007（JST_3007_ID,JST_3007_TH,JST_3007_MC,JST_3007_ZCKKC,JST_3007_SHKC,JST_3007_WFL,JST_3007_KYKC） values(HIBERNATE_SEQUENCE.NEXTVAL,?,?,?,?,?,?)";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      for (int i = 0; i < list.size(); i++) {
        ERPStockPO erpStockPO = list.get(i);
        if (erpStockPO.getZCKKC() == null)
          erpStockPO.setZCKKC(Long.valueOf(0L)); 
        if (erpStockPO.getSHKC() == null)
          erpStockPO.setSHKC(Long.valueOf(0L)); 
        if (erpStockPO.getWFL() == null)
          erpStockPO.setWFL(Long.valueOf(0L)); 
        erpStockPO.setKYKC(Long.valueOf(erpStockPO.getZCKKC().longValue() + erpStockPO.getSHKC().longValue() - erpStockPO.getWFL().longValue()));
        ps.setString(1, erpStockPO.getERPth());
        ps.setString(2, erpStockPO.getERPMC());
        ps.setLong(3, erpStockPO.getZCKKC().longValue());
        ps.setLong(4, erpStockPO.getSHKC().longValue());
        ps.setLong(5, erpStockPO.getWFL().longValue());
        ps.setLong(6, erpStockPO.getKYKC().longValue());
        ps.addBatch();
        if (i % 1000 == 0)
          ps.executeBatch(); 
      } 
      ps.executeBatch();
      conn.commit();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
  }
  
  public List<ERPStockPO> getOAERPStack() throws HibernateException, SQLException {
    List<ERPStockPO> list = new ArrayList<ERPStockPO>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select JST_3007_ID,JST_3007_TH,JST_3007_MC,JST_3007_ZCKKC,JST_3007_SHKC,JST_3007_WFL,JST_3007_KYKC from JST_3007";
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        ERPStockPO po = new ERPStockPO();
        po.setId(Long.valueOf(rs.getLong(1)));
        po.setERPth(rs.getString(2));
        po.setERPMC(rs.getString(3));
        po.setZCKKC(Long.valueOf(rs.getLong(4)));
        po.setSHKC(Long.valueOf(rs.getLong(5)));
        po.setWFL(Long.valueOf(rs.getLong(6)));
        po.setKYKC(Long.valueOf(rs.getLong(7)));
        list.add(po);
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return list;
  }
  
  public ModuleMenuPO getModuleMenuPO() throws HibernateException {
    begin();
    ModuleMenuPO mmp = new ModuleMenuPO();
    try {
      mmp = (ModuleMenuPO)this.session.load(ModuleMenuPO.class, Long.valueOf(73545L));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return mmp;
  }
  
  public List<EmployeeVO> getEmployeeVO(String sql) throws SQLException {
    List<EmployeeVO> list = new ArrayList<EmployeeVO>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        EmployeeVO po = new EmployeeVO();
        po.setEmpEmail(rs.getString(1));
        list.add(po);
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return list;
  }
  
  public void deleteERPStack() throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "delete from JST_3007";
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
  }
  
  public void deleteData() throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "delete from HAIER_SPECIALMODELS";
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      sql = "delete from HAIER_PRDT";
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      sql = "delete from HAIER_ZC_NO";
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      sql = "delete from HAIER_CUST";
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      sql = "delete from HAIER_INDX";
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      sql = "delete from HAIER_SALM";
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      sql = "delete from HAIER_MY_WH";
      ps = conn.prepareStatement(sql);
      ps.executeUpdate(sql);
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
  }
  
  public List<String[]> getDataFromHaier(String sql, int num) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource("jdbc/haiererp").getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        String[] temp = new String[num];
        for (int i = 0; i < num; i++)
          temp[i] = rs.getString(i + 1); 
        list.add(temp);
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return list;
  }
  
  public void saveDataToOa(List<String[]> list, String sql, int num) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      for (int i = 0; i < list.size(); i++) {
        String[] temp = list.get(i);
        for (int j = 0; j < num; j++)
          ps.setString(j + 1, temp[j]); 
        ps.addBatch();
        if (i % 1000 == 0)
          ps.executeBatch(); 
      } 
      ps.executeBatch();
      conn.commit();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
  }
  
  public List<String> getThFromOa() {
    List<String> list = new ArrayList<String>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select distinct(jst_3004_f3054) from jst_3004 where jst_3004_id in(select jst_3002_f3022 from jst_3002 where jst_3002_foreignkey in (select jst_3001_id from jst_3001 where jst_3001_id in(select distinct(workrecord_id) from jsf_work where workstatus<>100)))";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next())
        list.add(rs.getString(1)); 
      rs.close();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return list;
  }
}
