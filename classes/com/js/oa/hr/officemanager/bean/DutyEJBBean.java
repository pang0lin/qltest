package com.js.oa.hr.officemanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.hibernate.Query;

public class DutyEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Integer add(DutyPO dutyPO) throws Exception {
    int result = 2;
    begin();
    try {
      String dutyName = dutyPO.getDutyName();
      result = Integer.parseInt(this.session
          .createQuery(
            "select count(*) from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + 
            dutyName + "'").iterate().next().toString());
      if (result > 0) {
        result = 1;
        return new Integer(1);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public Boolean del(String[] id) throws Exception {
    Boolean result = Boolean.FALSE;
    String idbuf = "";
    for (int i = 0; i < id.length; i++)
      idbuf = String.valueOf(idbuf) + id[i] + ","; 
    begin();
    try {
      this.session.delete("from com.js.oa.hr.officemanager.po.DutyPO po where  po.id in (" + 
          idbuf.substring(0, idbuf.length() - 1) + ")");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean validateByName(String name) throws Exception {
    boolean result = false;
    begin();
    try {
      List list = this.session.createQuery(
          "select duty from com.js.oa.hr.officemanager.po.DutyPO duty where duty.dutyName like '" + name + 
          "'").list();
      if (!list.isEmpty())
        result = true; 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getDuteList(String domainId) throws Exception {
    return getDuteList(domainId, "");
  }
  
  public List getDuteList(String domainId, String corpId) throws Exception {
    List list = new ArrayList();
    try {
      begin();
      String hql = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hql = "(po.corpId=0 or po.corpId=" + corpId + ") and"; 
      Query query = this.session.createQuery("from DutyPO po where " + hql + " po.domainId=" + domainId + 
          " order by po.dutyLevel ");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Map ImportDuty(HttpServletRequest httpServletRequest) throws FileNotFoundException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String message = "";
    String succeed = "0";
    boolean flag = false;
    String messageString = "";
    String saveTypeName = "";
    List<String> errorDutyList = new ArrayList<String>();
    String savetype = httpServletRequest.getParameter("savetype");
    if ("1".equals(savetype)) {
      saveTypeName = "忽略";
    } else {
      saveTypeName = "覆盖";
    } 
    String textValue = null;
    HttpSession session = httpServletRequest.getSession(true);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowStr = dateFormat.format(new Date());
    String realPath = httpServletRequest.getRealPath("/uploadtemplate/duty.xls");
    FileInputStream file = new FileInputStream(new File(realPath));
    try {
      this.workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      message = String.valueOf(message) + "选择的模版不正确！<br>";
      if (!"1".equals(succeed))
        succeed = "1"; 
      map.put("succeed", succeed);
      map.put("message", message);
      return map;
    } 
    this.sheet = this.workbook.getSheet(0);
    if (this.sheet != null) {
      int rows = this.sheet.getRows();
      int columns = 3;
      try {
        String duiYinWiZhi = this.sheet.getCell(0, 1).getContents().trim();
        String duiYinWiZhiValue = this.sheet.getCell(1, 1).getContents().trim();
        String databaseType = SystemCommon.getDatabaseType();
        if ("对应字段所在列".equals(duiYinWiZhi) && duiYinWiZhiValue.matches("^[0-9]*[1-9][0-9]*$")) {
          if (!"1".equals(succeed)) {
            DataSourceBase dataSourceBase = new DataSourceBase();
            for (int i = 4; i < rows; i++) {
              StringBuffer insertParamSql = new StringBuffer("insert into  oa_duty(");
              if ("mysql".equals(databaseType)) {
                insertParamSql.append("dutyNO,dutyname,dutylevel,domain_id,duty_describe,corpId");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(");
              } else {
                insertParamSql
                  .append("duty_id,dutyNO,dutyname,dutylevel,domain_id,duty_describe,corpId");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(hibernate_sequence.nextval,");
              } 
              int k;
              for (k = 0; k < columns; k++) {
                if (i != Long.valueOf(duiYinWiZhiValue).longValue() - 1L) {
                  textValue = this.sheet.getCell(k, i).getContents().trim();
                  insertParamSql.append("'").append(textValue).append("',");
                } 
              } 
              insertParamSql.append("'").append("0").append("',");
              insertParamSql.append("'").append("导入").append("'");
              if (session.getAttribute("sysManager").toString().indexOf("1") >= 0 && 
                httpServletRequest.getParameter("fromSystem") != null && 
                "1".equals(httpServletRequest.getParameter("fromSystem"))) {
                insertParamSql.append(",0)");
              } else {
                insertParamSql.append("," + session.getAttribute("corpId") + ")");
              } 
              for (k = 0; k < columns - 1; k++) {
                if (i != Long.valueOf(duiYinWiZhiValue).longValue() - 1L) {
                  textValue = this.sheet.getCell(k, i).getContents().trim();
                  if (!"".equals(textValue) && textValue != null)
                    flag = getStationName(textValue); 
                } 
              } 
              if (savetype != null && "2".equals(savetype)) {
                String sql = "delete from oa_duty  where dutyname='" + textValue + "' ";
                deleteByDutyName(sql);
                updateByDutySql(insertParamSql.toString());
              } else {
                updateByDutySql(insertParamSql.toString());
              } 
              if (flag) {
                succeed = "0";
                errorDutyList.add(textValue);
                map.put("succeed", succeed);
              } 
            } 
          } 
        } else {
          if (!"1".equals(succeed))
            succeed = "1"; 
          message = String.valueOf(message) + "选择的模版不正确！<br>";
        } 
      } catch (Exception ex) {
        succeed = "1";
        message = String.valueOf(message) + "导入数据对应不正确！<br>";
        ex.printStackTrace();
      } 
    } 
    if (errorDutyList != null && !errorDutyList.isEmpty())
      for (int m = errorDutyList.size() - 1; m > 0; m--) {
        for (int n = 0; n < errorDutyList.size(); n++) {
          if (((String)errorDutyList.get(m)).equals(errorDutyList.get(n)) && m != n)
            errorDutyList.remove(m); 
        } 
      }  
    if (errorDutyList != null && !errorDutyList.isEmpty())
      for (String errorDuty : errorDutyList) {
        messageString = String.valueOf(messageString) + "【" + errorDuty + "】";
        flag = true;
      }  
    if (flag)
      messageString = "提示：导入的职务" + messageString + "已经存在！已进行" + saveTypeName; 
    map.put("succeed", succeed);
    map.put("messageString", messageString);
    map.put("message", message);
    return map;
  }
  
  public boolean updateByDutySql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean deleteByDutyName(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean getStationName(String name) throws Exception {
    boolean flag = false;
    try {
      begin();
      List list = this.session.createQuery(
          "select a.id,a.dutyName from com.js.oa.hr.officemanager.po.DutyPO a where a.dutyName='" + name + 
          "' ").list();
      if (list != null && list.size() > 0)
        flag = true; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return flag;
  }
}
