package com.js.oa.haier.bean;

import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.sql.DataSource;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ERPSyncEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public static void main(String[] args) {
    Date date = new Date();
    System.out.println(new Date(date.getTime()));
  }
  
  public static Connection getConnectionOracle() {
    Connection conn = null;
    String url = "jdbc:oracle:thin:@192.168.0.201:1521:haier";
    String user = "jsdb";
    String password = "12345678";
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      conn = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  public static Connection getConnectionSqlServer() {
    Connection conn = null;
    String url = "jdbc:sqlserver://192.168.0.201:1433;DatabaseName=HR_test";
    String user = "yws";
    String password = "12345678";
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      conn = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  public List<String[]> getNewPrdtMaterial(String sql, int num) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
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
  
  public void saveMaterialDataToErpPrdt(String[] temp, String userId, boolean stopFlag) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "insert into prdt(PRD_NO,NAME,SNM,ABC,KND,IDX1,UT,UT1,PK2_UT,PK2_QTY,SUP1,WH,CHK_MAN,CLS_DATE,MRK,ACC_NO_ORI,START_DD,SYS_DATE,QTY_LOW,NEED_DAYS,NEED_DD,ML_BY_MM,ML_UT,DFU_UT,QUOTE_UT1,QUOTE_UT2,QUOTE_UT3,RTO_CL,RTO_ID,QTY_AD_ID,NOUSE_DD,BAR_CODE,SPC_TAX,USR,SPC_PRD,CHK_BAT,CHK_TAX,TPL_NO,NAME_PY,CHK_NUM,USE_PRDMARK,BIL_ML,PAK_NW) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      ps.setString(1, "OA-" + temp[14] + temp[0]);
      ps.setString(2, temp[1]);
      ps.setString(3, temp[1]);
      ps.setString(4, changeFaLiaoType(temp[2]));
      ps.setString(5, "4");
      ps.setString(6, temp[3]);
      ps.setString(7, temp[4]);
      ps.setString(8, temp[5]);
      ps.setString(9, temp[6]);
      ps.setString(10, temp[7]);
      ps.setString(11, temp[8]);
      ps.setString(12, getWHCode(temp[9]));
      ps.setString(13, getUserInfoById(userId));
      ps.setString(14, sdf.format(new Date()));
      ps.setString(15, temp[17]);
      ps.setString(16, temp[10]);
      ps.setString(17, sdf2.format(new Date()));
      ps.setString(18, sdf.format(new Date()));
      ps.setString(19, temp[11]);
      ps.setString(20, temp[12]);
      ps.setString(21, (temp[13] == null) ? "" : temp[13].substring(0, temp[13].length() - 1));
      ps.setString(22, (temp[2] == null) ? "" : (temp[2].equals("E") ? "T" : ""));
      ps.setString(23, (temp[2] == null) ? "" : ((temp[2].equals("E") || temp[2].equals("B")) ? "2" : "1"));
      ps.setString(24, "1");
      ps.setString(25, "1");
      ps.setString(26, "1");
      ps.setString(27, "1");
      ps.setFloat(28, 0.0F);
      ps.setString(29, "1");
      ps.setString(30, "1");
      ps.setString(31, stopFlag ? null : sdf2.format(new Date()));
      ps.setString(32, temp[16]);
      ps.setString(33, "5");
      ps.setString(34, getUserInfoById(userId));
      ps.setString(35, "F");
      ps.setString(36, "F");
      ps.setString(37, "F");
      ps.setString(38, "001");
      ps.setString(39, String.valueOf(getFirstSpell(temp[1]).toUpperCase()) + "(" + getFirstSpell(temp[1]).toUpperCase() + ")()");
      ps.setString(40, "F");
      ps.setString(41, "F");
      ps.setString(42, "1");
      ps.setFloat(43, 0.0F);
      ps.executeUpdate();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("插入数据失败！");
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
  
  public void updateMaterialDataToErpPrdt(String[] temp, String userId) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "update prdt set NAME=?,SNM=?,ABC=?,KND=?,IDX1=?,UT=?,UT1=?,PK2_UT=?,PK2_QTY=?,SUP1=?,WH=?,CHK_MAN=?,CLS_DATE=?,MRK=?,ACC_NO_ORI=?,START_DD=?,SYS_DATE=?,QTY_LOW=?,NEED_DAYS=?,NEED_DD=?,ML_BY_MM=?,ML_UT=?,DFU_UT=?,QUOTE_UT1=?,QUOTE_UT2=?,QUOTE_UT3=?,RTO_CL=?,RTO_ID=?,QTY_AD_ID=?,NOUSE_DD=?,BAR_CODE=?,USR=?,NAME_PY=? where PRD_NO = ?";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      ps.setString(1, temp[1]);
      ps.setString(2, temp[1]);
      ps.setString(3, changeFaLiaoType(temp[2]));
      ps.setString(4, "4");
      ps.setString(5, temp[3]);
      ps.setString(6, temp[4]);
      ps.setString(7, temp[5]);
      ps.setString(8, temp[6]);
      ps.setString(9, temp[7]);
      ps.setString(10, temp[8]);
      ps.setString(11, getWHCode(temp[9]));
      ps.setString(12, getUserInfoById(userId));
      ps.setString(13, sdf.format(new Date()));
      ps.setString(14, temp[17]);
      ps.setString(15, temp[10]);
      ps.setString(16, sdf2.format(new Date()));
      ps.setString(17, sdf.format(new Date()));
      ps.setString(18, temp[11]);
      ps.setString(19, temp[12]);
      ps.setString(20, (temp[13] == null) ? "" : temp[13].substring(0, temp[13].length() - 1));
      ps.setString(21, (temp[2] == null) ? "" : (temp[2].equals("E") ? "T" : ""));
      ps.setString(22, (temp[2] == null) ? "" : ((temp[2].equals("E") || temp[2].equals("B")) ? "2" : "1"));
      ps.setString(23, "1");
      ps.setString(24, "1");
      ps.setString(25, "1");
      ps.setString(26, "1");
      ps.setFloat(27, 0.0F);
      ps.setString(28, "1");
      ps.setString(29, "1");
      ps.setString(30, (String)null);
      ps.setString(31, temp[16]);
      ps.setString(32, getUserInfoById(userId));
      ps.setString(33, String.valueOf(getFirstSpell(temp[1]).toUpperCase()) + "(" + getFirstSpell(temp[1]).toUpperCase() + ")()");
      ps.setString(34, "OA-" + temp[14] + temp[0]);
      ps.executeUpdate();
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
  
  public void saveMaterialDataToErpPrdtZ(String prdtNo, String prefix) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO from prdt_z where PRD_NO = ?";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, "OA-" + prefix + prdtNo);
      rs = ps.executeQuery();
      if (rs.next()) {
        sql = "update prdt_z set Z_TIME=? where PRD_NO =?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, sdf.format(new Date()));
        ps.setString(2, "OA-" + prefix + prdtNo);
        ps.executeUpdate();
      } else {
        sql = "insert into prdt_z(PRD_NO,Z_TIME,Z_JY) values(?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, "OA-" + prefix + prdtNo);
        ps.setString(2, sdf.format(new Date()));
        ps.setString(3, "F");
        ps.executeUpdate();
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
  }
  
  public void saveMaterialDataToErpPrdtZBcp(String prdtNo, String prefix) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO from prdt_z where PRD_NO = ?";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, String.valueOf(prefix) + prdtNo);
      rs = ps.executeQuery();
      if (rs.next()) {
        sql = "update prdt_z set Z_TIME=? where PRD_NO =?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, sdf.format(new Date()));
        ps.setString(2, String.valueOf(prefix) + prdtNo);
        ps.executeUpdate();
      } else {
        sql = "insert into prdt_z(PRD_NO,Z_TIME,Z_JY) values(?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, String.valueOf(prefix) + prdtNo);
        ps.setString(2, sdf.format(new Date()));
        ps.setString(3, "F");
        ps.executeUpdate();
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
  }
  
  public void saveMaterialDataToErpPrdtZForXjx(String prdtNo, String prefix, String sfpc) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO from prdt_z where PRD_NO = ?";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, String.valueOf(prefix) + prdtNo);
      rs = ps.executeQuery();
      if (rs.next()) {
        sql = "update prdt_z set Z_TIME=?,Z_PC=?,Z_JY=? where PRD_NO =?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, sdf.format(new Date()));
        ps.setString(2, sfpc);
        ps.setString(3, String.valueOf(prefix) + prdtNo);
        ps.setString(4, "F");
        ps.executeUpdate();
      } else {
        sql = "insert into prdt_z(PRD_NO,Z_TIME,Z_PC,Z_JY) values(?,?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, String.valueOf(prefix) + prdtNo);
        ps.setString(2, sdf.format(new Date()));
        ps.setString(3, sfpc);
        ps.setString(4, "F");
        ps.executeUpdate();
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
  }
  
  public void saveMaterialDataToErpPrdCusPo(String[] temp) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO from PRD_CUS_PO where PRD_NO = ?";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, "OA-" + temp[14] + temp[0]);
      rs = ps.executeQuery();
      if (rs.next()) {
        sql = "delete from PRD_CUS_PO where PRD_NO =?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, "OA-" + temp[8] + temp[0]);
        ps.executeUpdate();
      } else {
        sql = "insert into PRD_CUS_PO(PRD_NO,ITM,CUS_NO) values(?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, "OA-" + temp[14] + temp[0]);
        ps.setString(2, "1");
        ps.setString(3, temp[8]);
        ps.executeUpdate();
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
  }
  
  public void savePrdtAddDataToErpPrdt(String[] temp, String userId, boolean stopFlag) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "insert into prdt(PRD_NO,NAME,SNM,KND,UT,WH,CHK_MAN,CLS_DATE,ACC_NO_ORI,START_DD,SYS_DATE,NEED_DAYS,BOM_ID,ML_BY_MM,DFU_UT,QUOTE_UT1,QUOTE_UT2,QUOTE_UT3,RTO_CL,RTO_ID,QTY_AD_ID,NOUSE_DD,SPC_TAX,ML_UT,USR,SPC_PRD,CHK_BAT,TW_ID,CHK_TAX,TPL_NO,NAME_PY,CHK_NUM,USE_PRDMARK,BIL_ML,PAK_NW) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      ps.setString(1, String.valueOf(temp[7]) + temp[0]);
      ps.setString(2, temp[1]);
      ps.setString(3, temp[1]);
      ps.setString(4, "3");
      ps.setString(5, getMianDW(temp[2]));
      ps.setString(6, temp[3]);
      ps.setString(7, getUserInfoById(userId));
      ps.setString(8, sdf.format(new Date()));
      ps.setString(9, temp[4]);
      ps.setString(10, sdf2.format(new Date()));
      ps.setString(11, sdf.format(new Date()));
      ps.setString(12, "0");
      ps.setString(13, temp[6].equals("是") ? "Y" : "");
      ps.setString(14, "T");
      ps.setString(15, "1");
      ps.setString(16, "1");
      ps.setString(17, "1");
      ps.setString(18, "1");
      ps.setString(19, "0.00");
      ps.setString(20, "1");
      ps.setString(21, "1");
      ps.setString(22, stopFlag ? null : sdf2.format(new Date()));
      ps.setString(23, "5");
      ps.setString(24, "1");
      ps.setString(25, getUserInfoById(userId));
      ps.setString(26, "F");
      ps.setString(27, "F");
      ps.setString(28, "2500".equals(temp[3]) ? "5" : "2");
      ps.setString(29, "F");
      ps.setString(30, "001");
      ps.setString(31, String.valueOf(getFirstSpell(temp[1]).toUpperCase()) + "(" + getFirstSpell(temp[1]).toUpperCase() + ")()");
      ps.setString(32, "F");
      ps.setString(33, "F");
      ps.setString(34, "1");
      ps.setFloat(35, 0.0F);
      ps.executeUpdate();
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
  
  public void updatePrdtAddDataToErpPrdt(String[] temp, String userId) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "update prdt set NAME=?,SNM=?,KND=?,UT=?,WH=?,CHK_MAN=?,CLS_DATE=?,ACC_NO_ORI=?,START_DD=?,SYS_DATE=?,NEED_DAYS=?,BOM_ID=?,ML_BY_MM=?,DFU_UT=?,QUOTE_UT1=?,QUOTE_UT2=?,QUOTE_UT3=?,RTO_CL=?,RTO_ID=?,QTY_AD_ID=?,NOUSE_DD=?,USR=?,TW_ID=?,NAME_PY=? where PRD_NO=?";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      ps.setString(1, temp[1]);
      ps.setString(2, temp[1]);
      ps.setString(3, "3");
      ps.setString(4, getMianDW(temp[2]));
      ps.setString(5, temp[3]);
      ps.setString(6, getUserInfoById(userId));
      ps.setString(7, sdf.format(new Date()));
      ps.setString(8, temp[4]);
      ps.setString(9, sdf2.format(new Date()));
      ps.setString(10, sdf.format(new Date()));
      ps.setString(11, "0");
      ps.setString(12, temp[6].equals("是") ? "Y" : "");
      ps.setString(13, "T");
      ps.setString(14, "1");
      ps.setString(15, "1");
      ps.setString(16, "1");
      ps.setString(17, "1");
      ps.setString(18, "0.00");
      ps.setString(19, "1");
      ps.setString(20, "1");
      ps.setString(21, (String)null);
      ps.setString(22, getUserInfoById(userId));
      ps.setString(23, "2500".equals(temp[3]) ? "5" : "2");
      ps.setString(24, String.valueOf(getFirstSpell(temp[1]).toUpperCase()) + "(" + getFirstSpell(temp[1]).toUpperCase() + ")()");
      ps.setString(25, String.valueOf(temp[7]) + temp[0]);
      ps.executeUpdate();
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
  
  public void saveModelAppendDataToErpPrdt(String[] temp, String userId, boolean stopFlag) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "insert into prdt(PRD_NO,NAME,SNM,NAME_ENG,KND,UT,WH,CHK_MAN,CLS_DATE,START_DD,SYS_DATE,BAR_CODE,NEED_DAYS,PAK_UNIT,PAK_GW,PAK_MEAST,PAK_EXC,PAK_WEIGHT_UNIT,PAK_MEAST_UNIT,DFU_UT,QUOTE_UT1,QUOTE_UT2,QUOTE_UT3,RTO_CL,RTO_ID,QTY_AD_ID,NOUSE_DD,SPC_TAX,ML_UT,USR,SPC_PRD,CHK_BAT,CHK_TAX,TPL_NO,NAME_PY,CHK_NUM,USE_PRDMARK,PAK_NW,TW_ID,BIL_ML) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      ps.setString(1, String.valueOf(temp[8]) + temp[1]);
      ps.setString(2, temp[1]);
      ps.setString(3, temp[1]);
      ps.setString(4, temp[0]);
      ps.setString(5, "2");
      ps.setString(6, temp[2]);
      ps.setString(7, temp[3]);
      ps.setString(8, getUserInfoById(userId));
      ps.setString(9, sdf.format(new Date()));
      ps.setDate(10, new Date(date.getTime()));
      ps.setString(11, sdf.format(new Date()));
      ps.setString(12, temp[4]);
      ps.setString(13, "1");
      ps.setString(14, temp[5]);
      ps.setString(15, temp[6]);
      ps.setFloat(16, Float.parseFloat(changeStrToNumber(temp[7])));
      ps.setString(17, "1");
      ps.setString(18, "KG");
      ps.setString(19, "m³");
      ps.setString(20, "1");
      ps.setString(21, "1");
      ps.setString(22, "1");
      ps.setString(23, "1");
      ps.setString(24, "0.00");
      ps.setString(25, (String)null);
      ps.setString(26, "1");
      ps.setString(27, stopFlag ? null : sdf2.format(new Date()));
      ps.setString(28, "5");
      ps.setString(29, "1");
      ps.setString(30, getUserInfoById(userId));
      ps.setString(31, "F");
      ps.setString(32, "F");
      ps.setString(33, "F");
      ps.setString(34, "001");
      ps.setString(35, String.valueOf(getFirstSpell(temp[1]).toUpperCase()) + "(" + getFirstSpell(temp[1]).toUpperCase() + ")()");
      ps.setString(36, "F");
      ps.setString(37, "F");
      ps.setFloat(38, 0.0F);
      ps.setString(39, "2");
      ps.setString(40, "1");
      ps.executeUpdate();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("插入数据失败！");
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
  
  public void updateModelAppendDataToErpPrdt(String[] temp, String userId) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "update prdt set NAME=?,SNM=?,NAME_ENG=?,KND=?,UT=?,WH=?,CHK_MAN=?,CLS_DATE=?,START_DD=?,SYS_DATE=?,BAR_CODE=?,NEED_DAYS=?,PAK_UNIT=?,PAK_GW=?,PAK_MEAST=?,PAK_EXC=?,PAK_WEIGHT_UNIT=?,PAK_MEAST_UNIT=?,DFU_UT=?,QUOTE_UT1=?,QUOTE_UT2=?,QUOTE_UT3=?,RTO_CL=?,RTO_ID=?,QTY_AD_ID=?,NOUSE_DD=?,USR=?,NAME_PY=? where PRD_NO=?";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = new Date();
      ps.setString(1, temp[1]);
      ps.setString(2, temp[1]);
      ps.setString(3, temp[0]);
      ps.setString(4, "2");
      ps.setString(5, temp[2]);
      ps.setString(6, temp[3]);
      ps.setString(7, getUserInfoById(userId));
      ps.setString(8, sdf.format(new Date()));
      ps.setDate(9, new Date(date.getTime()));
      ps.setString(10, sdf.format(new Date()));
      ps.setString(11, temp[4]);
      ps.setString(12, "1");
      ps.setString(13, temp[5]);
      ps.setString(14, temp[6]);
      ps.setFloat(15, Float.parseFloat(changeStrToNumber(temp[7])));
      ps.setString(16, "1");
      ps.setString(17, "KG");
      ps.setString(18, "m³");
      System.out.println("haier-新机型跟新数据--------------------------------");
      ps.setString(19, "1");
      ps.setString(20, "1");
      ps.setString(21, "1");
      ps.setString(22, "1");
      ps.setString(23, "0.00");
      ps.setString(24, (String)null);
      ps.setString(25, "1");
      ps.setString(26, (String)null);
      ps.setString(27, getUserInfoById(userId));
      ps.setString(28, String.valueOf(getFirstSpell(temp[1]).toUpperCase()) + "(" + getFirstSpell(temp[1]).toUpperCase() + ")()");
      ps.setString(29, String.valueOf(temp[8]) + temp[1]);
      ps.executeUpdate();
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
  
  public String getUserInfoById(String userId) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select empenglishname,useraccounts from org_employee where emp_id =" + userId;
    String temp = "";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next())
        if (rs.getString(1) != null) {
          temp = rs.getString(1);
        } else {
          temp = rs.getString(2);
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
    return temp;
  }
  
  public String getPrdtNameByTh(String th) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select jst_3005_f3235 from jst_3005 where jst_3005_f3064 ='" + th + "'";
    String temp = "";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next())
        temp = rs.getString(1); 
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
    return temp;
  }
  
  public String getWHCode(String name) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select wh from HAIER_MY_WH where name=?";
    String temp = "";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, name);
      rs = ps.executeQuery();
      if (rs.next())
        temp = rs.getString(1); 
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
    return temp;
  }
  
  public boolean getInfoByPrdtNO(String prdtNo) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO from PRDT where PRD_NO=? and NOUSE_DD is not null";
    boolean flag = false;
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, prdtNo);
      rs = ps.executeQuery();
      if (rs.next())
        flag = true; 
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
    return flag;
  }
  
  public String checkPrdtNoExists(String prdtNo, String prefix) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO,NOUSE_DD from PRDT where PRD_NO=?";
    String flag = "2";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, String.valueOf(prefix) + prdtNo);
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getString(2) != null) {
          flag = "1";
        } else {
          flag = "4";
        } 
      } else {
        sql = "select PRD_NO,NOUSE_DD from prdt where substring(prd_no,4,len(prd_no)) = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, prdtNo);
        rs = ps.executeQuery();
        if (rs.next())
          if (rs.getString(2) != null) {
            flag = "2";
          } else {
            flag = "3";
          }  
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
    return flag;
  }
  
  public String checkPrdtNoExists2(String prdtNo, String prefix) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select PRD_NO,NOUSE_DD from PRDT where PRD_NO=?";
    String flag = "2";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, String.valueOf(prefix) + prdtNo);
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getString(2) != null) {
          flag = "1";
        } else {
          flag = "4";
        } 
      } else {
        sql = "select PRD_NO,NOUSE_DD from prdt where substring(prd_no,7,len(prd_no)) = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, prdtNo);
        rs = ps.executeQuery();
        if (rs.next())
          if (rs.getString(2) != null) {
            flag = "2";
          } else {
            flag = "3";
          }  
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
    return flag;
  }
  
  public String[] getUserMailById(String userId) {
    String[] resArr = (String[])null;
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select empname,empemail from org_employee where emp_id =" + userId;
    String temp = "";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next()) {
        resArr = new String[2];
        resArr[0] = rs.getString("empname");
        String email = rs.getString("empemail");
        if (email == null || "".equals(email) || "null".equalsIgnoreCase(email)) {
          resArr[1] = "";
        } else {
          resArr[1] = email;
        } 
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
    return resArr;
  }
  
  public String getMianDW(String id) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select jst_3017_f3223 from jst_3017 where jst_3017_id =" + id;
    String temp = "";
    try {
      conn = ds.getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next())
        temp = rs.getString(1); 
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
    return temp;
  }
  
  private String changeFaLiaoType(String type) {
    String res = "";
    if ("A".equals(type)) {
      res = "1";
    } else if ("B".equals(type)) {
      res = "2";
    } else if ("C".equals(type)) {
      res = "3";
    } else if ("D".equals(type)) {
      res = "4";
    } else if ("E".equals(type)) {
      res = "5";
    } 
    return res;
  }
  
  private String getFirstSpell(String chinese) {
    StringBuffer pybf = new StringBuffer();
    char[] arr = chinese.toCharArray();
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] > '') {
        try {
          String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
          if (temp != null)
            pybf.append(temp[0].charAt(0)); 
        } catch (BadHanyuPinyinOutputFormatCombination e) {
          e.printStackTrace();
        } 
      } else {
        pybf.append(arr[i]);
      } 
    } 
    return pybf.toString().replaceAll("\\W", "").trim();
  }
  
  private String changeStrToNumber(String num) {
    if (num == null)
      return "0"; 
    BigDecimal bd = new BigDecimal(num);
    return bd.toPlainString();
  }
}
