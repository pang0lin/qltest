package com.js.oa.hr.finance.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EncryptSelf;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;

public class FSalaryRsNewEJBBean extends HibernateBase implements SessionBean {
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getSalaryInfo(String userId, String year, String month) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String result = "";
    String sql = "";
    try {
      DbOpt db = new DbOpt();
      sql = "select bz from temp_rsxc_pc where year='" + year + "' and month='" + month + "'";
      String bz = db.executeQueryToStr(sql);
      if (bz != null) {
        bz = bz.replaceAll("\n", "<br/>");
      } else {
        bz = "";
      } 
      map.put("bz", bz);
      sql = "select org,sfrq,sfgz from temp_rsxc_user where userid=" + userId + " and year='" + year + "' and month='" + month + "'";
      String[][] userInfo = db.executeQueryToStrArr2(sql, 3);
      if (userInfo == null) {
        map.put("result", "您的" + year + "年" + month + "月的工资尚未导入！");
        return map;
      } 
      map.put("org", userInfo[0][0]);
      map.put("sfrq", userInfo[0][1]);
      map.put("sfgz", decode(userInfo[0][2]));
      sql = "select itemname,itemvalue from temp_rsxc_item where userid=" + userId + " and year='" + year + "' and month='" + month + "' and position='其中' order by showorder";
      String[][] banks = db.executeQueryToStrArr2(sql, 2);
      if (banks != null)
        for (int i = 0; i < banks.length; i++) {
          banks[i][1] = decode(banks[i][1]);
          if ("".equals(banks[i][1]))
            banks[i][1] = "&nbsp;"; 
        }  
      map.put("banks", banks);
      sql = "select itemname,itemvalue from temp_rsxc_item where userid=" + userId + " and year='" + year + "' and month='" + month + "' and position='左' order by showorder";
      String[][] lefts = db.executeQueryToStrArr2(sql, 2);
      if (lefts != null)
        for (int i = 0; i < lefts.length; i++) {
          lefts[i][1] = decode(lefts[i][1]);
          if ("".equals(lefts[i][1]))
            lefts[i][1] = "&nbsp;"; 
        }  
      sql = "select itemname,itemvalue from temp_rsxc_item where userid=" + userId + " and year='" + year + "' and month='" + month + "' and position='右' order by showorder";
      String[][] rights = db.executeQueryToStrArr2(sql, 2);
      if (rights != null)
        for (int i = 0; i < rights.length; i++) {
          rights[i][1] = decode(rights[i][1]);
          if ("".equals(rights[i][1]))
            rights[i][1] = "&nbsp;"; 
        }  
      if (lefts.length > rights.length) {
        String[][] temp = new String[lefts.length][2];
        int i;
        for (i = 0; i < rights.length; i++) {
          temp[i][0] = rights[i][0];
          temp[i][1] = rights[i][1];
        } 
        for (i = rights.length; i < lefts.length; i++) {
          temp[i][0] = "&nbsp;";
          temp[i][1] = "&nbsp;";
        } 
        rights = temp;
      } else if (lefts.length < rights.length) {
        String[][] temp = new String[rights.length][2];
        int i;
        for (i = 0; i < lefts.length; i++) {
          temp[i][0] = lefts[i][0];
          temp[i][1] = lefts[i][1];
        } 
        for (i = lefts.length; i < rights.length; i++) {
          temp[i][0] = "&nbsp;";
          temp[i][1] = "&nbsp;";
        } 
        lefts = temp;
      } 
      map.put("lefts", lefts);
      map.put("rights", rights);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    map.put("result", result);
    return map;
  }
  
  public Map importSalaryFormExcel(HttpServletRequest request, String fileName, String year, String month) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String currentUserID = request.getSession().getAttribute("userId").toString();
    String message = "";
    String succeed = "0";
    String messageString = "";
    try {
      FileInputStream file = new FileInputStream(new File(fileName));
      this.workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      message = String.valueOf(message) + "选择的模版不正确！<br>";
      if (!"1".equals(succeed))
        succeed = "1"; 
      map.put("succeed", succeed);
      map.put("message", message);
      return map;
    } 
    this.sheet = this.workbook.getSheet(1);
    String bz = "";
    if (this.sheet != null)
      bz = this.sheet.getCell(0, 0).getContents(); 
    this.sheet = this.workbook.getSheet(0);
    int baseFieldLength = 4;
    int mxBeginCol = 0;
    int mxEndCol = 0;
    boolean begin = false;
    for (int i = baseFieldLength;; i++) {
      if (!begin) {
        if (!"".equals(this.sheet.getCell(i, 0).getContents())) {
          mxBeginCol = i;
          begin = true;
        } 
      } else if ("".equals(this.sheet.getCell(i, 0).getContents()) || i == this.sheet.getColumns() - 1) {
        mxEndCol = i - 1;
        break;
      } 
    } 
    int rowCount = this.sheet.getRows();
    String[] wz = new String[mxEndCol - mxBeginCol + 1];
    String[] itemName = new String[mxEndCol - mxBeginCol + 1];
    String[] banks = new String[mxBeginCol - baseFieldLength - 1];
    int j = 0;
    for (int m = mxBeginCol; m <= mxEndCol; m++) {
      wz[j] = this.sheet.getCell(m, 0).getContents();
      itemName[j++] = this.sheet.getCell(m, 1).getContents();
    } 
    int k = 0;
    for (int n = baseFieldLength + 1; n < mxBeginCol; n++)
      banks[k++] = this.sheet.getCell(n, 1).getContents(); 
    int importCount = 0;
    String sql = "";
    sql = "delete temp_rsxc_pc where year='" + year + "' and month='" + month + "'";
    updateBySql(sql);
    sql = "delete temp_rsxc_user where year='" + year + "' and month='" + month + "'";
    updateBySql(sql);
    sql = "delete temp_rsxc_item where year='" + year + "' and month='" + month + "'";
    updateBySql(sql);
    sql = "insert into temp_rsxc_pc(id,year,month,importuser,usercount,bz)values(hibernate_sequence.nextval,'" + year + "','" + month + "','" + currentUserID + "',0,'" + bz + "')";
    updateBySql(sql);
    for (int row = 2; row < rowCount; row++) {
      String logonName = this.sheet.getCell(0, row).getContents().trim();
      if (logonName == null) {
        logonName = "";
      } else {
        logonName = logonName.trim();
      } 
      if ("".equals(logonName)) {
        message = String.valueOf(message) + "第" + (row + 1) + "行登录名为空！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } else {
        String userid = getUserIdByLogonName(logonName);
        if ("".equals(userid)) {
          message = String.valueOf(message) + "第" + (row + 1) + "行登录名错误，请核实！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } else {
          String org = this.sheet.getCell(2, row).getContents();
          if (org == null) {
            org = "";
          } else {
            org = org.trim();
          } 
          String ffrq = this.sheet.getCell(3, row).getContents();
          if (ffrq == null) {
            ffrq = "";
          } else {
            ffrq = ffrq.trim();
          } 
          String sfgz = this.sheet.getCell(4, row).getContents();
          if (sfgz == null) {
            sfgz = "";
          } else {
            sfgz = sfgz.trim();
          } 
          sql = "insert into temp_rsxc_user(id,userid,loginname,org,sfrq,sfgz,year,month)values(hibernate_sequence.nextval," + 
            userid + ",'" + logonName + "','" + org + "','" + ffrq + "','" + encode(sfgz) + "','" + year + "','" + month + "')";
          updateBySql(sql);
          int index = 0;
          for (int i1 = baseFieldLength + 1; i1 < mxBeginCol; i1++) {
            sql = "insert into temp_rsxc_item(id,year,month,userid,itemname,itemvalue,showorder,position)values(hibernate_sequence.nextval,'" + 
              year + "','" + month + "'," + userid + ",'" + banks[index] + "','" + encode(this.sheet.getCell(i1, row).getContents()) + "'," + index++ + ",'其中')";
            updateBySql(sql);
          } 
          index = 0;
          for (int col = mxBeginCol; col <= mxEndCol; col++) {
            sql = "insert into temp_rsxc_item(id,year,month,userid,itemname,itemvalue,showorder,position)values(hibernate_sequence.nextval,'" + 
              year + "','" + month + "'," + userid + ",'" + itemName[index] + "','" + encode(this.sheet.getCell(col, row).getContents()) + "'," + index + ",'" + wz[index++] + "')";
            updateBySql(sql);
          } 
          importCount++;
        } 
      } 
    } 
    sql = "update temp_rsxc_pc set usercount=" + importCount + "  where year='" + year + "' and month='" + month + "'";
    updateBySql(sql);
    map.put("succeed", succeed);
    map.put("messageString", messageString);
    map.put("message", message);
    return map;
  }
  
  public boolean updateBySql(String sql) {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
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
  
  private String encode(String paraIn) {
    return EncryptSelf.selfEncoder(paraIn);
  }
  
  private String decode(String paraIn) {
    return EncryptSelf.selfDecoder(paraIn);
  }
  
  private String getUserIdByLogonName(String logonName) {
    String userid = "";
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select emp_id from org_employee where useraccounts='" + logonName + "' and userIsDeleted=0 ");
      if (rs != null && rs.next())
        userid = rs.getString("emp_id"); 
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
    } 
    return userid;
  }
}
