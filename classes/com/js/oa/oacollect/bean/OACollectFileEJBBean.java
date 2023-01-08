package com.js.oa.oacollect.bean;

import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.oacollect.po.OaCollect;
import com.js.oa.oacollect.po.OaCollectFile;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterGenerator;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class OACollectFileEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveCollectFile(HttpServletRequest request) throws Exception {
    HttpSession httpSession = request.getSession(true);
    String userName = httpSession.getAttribute("userName").toString();
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String fileName = URLDecoder.decode(request.getParameter("fileName"), "utf-8");
    String fileNameSys = request.getParameter("fileNameSys");
    String collectId = request.getParameter("collectId");
    DataSourceBase dataBase = new DataSourceBase();
    Long id = null;
    try {
      if (collectId != null && !"".equals(collectId) && fileName != null && !"".equals(fileName)) {
        begin();
        OaCollectFile oaCollectFile = new OaCollectFile();
        OaCollect oaCollect = (OaCollect)this.session.get(OaCollect.class, Long.valueOf(collectId));
        oaCollectFile.setCollectId(Long.valueOf(collectId));
        oaCollectFile.setCollectTitle(oaCollect.getCollectTitle());
        oaCollectFile.setFileName(fileName);
        oaCollectFile.setFileNameSys(fileNameSys);
        oaCollectFile.setFilePath("/upload/collectfile/" + fileNameSys);
        oaCollectFile.setFileType(fileName.substring(fileName.indexOf(".")));
        oaCollectFile.setCollectEmpId(Long.valueOf(userId));
        oaCollectFile.setCollectEmpName(userName);
        oaCollectFile.setCollectOrgId(Long.valueOf(orgId));
        oaCollectFile.setCollectOrgName(orgName);
        oaCollectFile.setCollectEmpStatus(new Long(0L));
        oaCollectFile.setRemindCount(new Long(0L));
        oaCollectFile.setCreateDate(new Date());
        oaCollectFile.setCreatedEmp(Long.valueOf(userId));
        oaCollectFile.setCreatedOrg(Long.valueOf(orgId));
        id = (Long)this.session.save(oaCollectFile);
        this.session.flush();
        updateByYourYuanShengSql("update oa_collect_emp set emp_status=1 where collect_id=" + collectId + " and emp_id=" + userId);
        String dateString = "now()";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0)
          dateString = "sysdate"; 
        String sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + ",WORKSTATUS=101 where WORKRECORD_ID=" + oaCollect.getCollectId() + 
          " and WORKFILETYPE='数据采集' and INITACTIVITY=" + oaCollect.getCollectId() + " and WF_CUREMPLOYEE_ID=" + userId;
        updateBySql(sql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return id;
  }
  
  public boolean uploadPiLiang(List<ArrayList> list) throws HibernateException {
    boolean b = true;
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        List<E> tempList = list.get(i);
        String userName = (tempList.get(3) == null) ? "" : tempList.get(3).toString();
        String userId = (tempList.get(4) == null) ? "" : tempList.get(4).toString();
        Long fileSize = new Long((tempList.get(6) == null) ? "0" : tempList.get(6).toString());
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String fSize = fnum.format(fileSize.longValue() / 1024.0D);
        String orgId = (tempList.get(11) == null) ? "" : tempList.get(11).toString();
        String orgName = (tempList.get(12) == null) ? "" : tempList.get(12).toString();
        String fileName = (tempList.get(1) == null) ? "" : tempList.get(1).toString();
        String fileNameSys = String.valueOf((tempList.get(0) == null) ? "" : tempList.get(0).toString()) + "." + ((tempList.get(2) == null) ? "" : tempList.get(2).toString());
        String collectId = (tempList.get(10) == null) ? "" : tempList.get(10).toString();
        DataSourceBase dataBase = new DataSourceBase();
        Long id = null;
        try {
          if (collectId != null && !"".equals(collectId) && fileName != null && !"".equals(fileName)) {
            begin();
            OaCollectFile oaCollectFile = new OaCollectFile();
            OaCollect oaCollect = (OaCollect)this.session.get(OaCollect.class, Long.valueOf(collectId));
            oaCollectFile.setCollectId(Long.valueOf(collectId));
            oaCollectFile.setCollectTitle(oaCollect.getCollectTitle());
            oaCollectFile.setFileName(fileName);
            oaCollectFile.setFileNameSys(fileNameSys);
            oaCollectFile.setFilePath("/upload/" + fileNameSys.subSequence(0, 4) + "/collectfile/" + fileNameSys);
            oaCollectFile.setFileType(fileName.substring(fileName.indexOf(".")));
            oaCollectFile.setCollectEmpId(Long.valueOf(userId));
            oaCollectFile.setCollectEmpName(userName);
            oaCollectFile.setCollectOrgId(Long.valueOf(orgId));
            oaCollectFile.setCollectOrgName(orgName);
            oaCollectFile.setCollectEmpStatus(new Long(0L));
            oaCollectFile.setRemindCount(new Long(0L));
            oaCollectFile.setCreateDate(new Date());
            oaCollectFile.setCreatedEmp(Long.valueOf(userId));
            oaCollectFile.setCreatedOrg(Long.valueOf(orgId));
            oaCollectFile.setFileSize(new Float(fSize));
            id = (Long)this.session.save(oaCollectFile);
            this.session.flush();
            updateByYourYuanShengSql("update oa_collect_emp set emp_status=1 where collect_id=" + collectId + " and emp_id=" + userId);
            String dateString = "now()";
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("oracle") >= 0)
              dateString = "sysdate"; 
            String sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + ",WORKSTATUS=101 where WORKRECORD_ID=" + oaCollect.getCollectId() + 
              " and WORKFILETYPE='数据采集' and INITACTIVITY=" + oaCollect.getCollectId() + " and WF_CUREMPLOYEE_ID=" + userId;
            updateBySql(sql);
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          this.session.close();
          this.session = null;
          this.transaction = null;
        } 
      } 
    } else {
      b = false;
    } 
    return b;
  }
  
  public boolean updateByYourYuanShengSql(String sql) throws Exception {
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
  
  public boolean deleteCollectFile(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      OaCollectFile aaCollectFile = null;
      List tempList = null;
      String[] idarr = ids.split(",");
      if (idarr != null && idarr.length > 0)
        for (int i = 0; i < idarr.length; i++) {
          aaCollectFile = (OaCollectFile)this.session.get(OaCollectFile.class, Long.valueOf(idarr[i]));
          this.session.delete("from com.js.oa.oacollect.po.OaCollectFile po where po.id =" + idarr[i]);
          this.session.flush();
          if (aaCollectFile != null) {
            tempList = getListByYourSQL("select po.id,po.collectId from com.js.oa.oacollect.po.OaCollectFile po  where po.collectEmpId=" + 
                aaCollectFile.getCollectEmpId() + " and po.collectId=" + aaCollectFile.getCollectId());
            if (tempList == null || tempList.size() == 0)
              updateByYourYuanShengSql("update oa_collect_emp set emp_status=0 where collect_id=" + aaCollectFile.getCollectId() + " and emp_id=" + aaCollectFile.getCollectEmpId()); 
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public String updateBySql(String sql) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
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
    return res.toString();
  }
  
  public String downLoadFile(String ids, String userName) throws HibernateException {
    String path = System.getProperty("user.dir");
    path = String.valueOf(path.substring(0, path.length() - 4)) + "\\webapps\\jsoa\\upload";
    String FilePath = String.valueOf(path) + "/collectfile/";
    String firstPath = FilePath;
    String src = "0000";
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1); 
    String zipName = "";
    begin();
    try {
      String[] idarr = ids.split(",");
      if (idarr != null && idarr.length > 0) {
        zipName = "download.zip";
        String strZipPath = String.valueOf(FilePath.replace("jsoa\\upload/", "jsoa\\upload/" + ((new Date()).getYear() + 1900) + "/")) + zipName;
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
        byte[] bufs = new byte[10240];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        for (int i = 0; i < idarr.length; i++) {
          FilePath = firstPath;
          OaCollectFile oaCollectFile = (OaCollectFile)this.session.get(OaCollectFile.class, Long.valueOf(idarr[i]));
          if (oaCollectFile.getFileNameSys() != null && oaCollectFile.getFileNameSys().toString().length() > 6 && 
            oaCollectFile.getFileNameSys().toString().substring(4, 5).equals("_")) {
            src = oaCollectFile.getFileNameSys().toString().substring(0, 4);
          } else {
            src = "0000";
          } 
          FilePath = FilePath.replace("jsoa\\upload/", "jsoa\\upload/" + src + "/");
          File file = new File(String.valueOf(FilePath) + oaCollectFile.getFileNameSys());
          ZipEntry zipEntry = new ZipEntry(oaCollectFile.getFileName());
          zipEntry.setSize(file.length());
          zipEntry.setTime(file.lastModified());
          out.putNextEntry(zipEntry);
          out.setEncoding("GBK");
          fis = new FileInputStream(file);
          bis = new BufferedInputStream(fis, 10240);
          int read = 0;
          while ((read = bis.read(bufs, 0, 10240)) != -1)
            out.write(bufs, 0, read); 
        } 
        bis.close();
        out.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    zipName = String.valueOf(zipName) + "&&" + src;
    return zipName;
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListByYourSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return listInfo;
  }
  
  public Map getOrgIdOrgNameByUserId(String userId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    ParameterGenerator p = new ParameterGenerator(1);
    begin();
    List<Object[]> list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(" select o.orgId,o.orgName from com.js.system.vo.organizationmanager.OrganizationVO o,com.js.system.vo.usermanager.EmployeeOrgVO eo  where o.orgId=eo.orgId and  eo.empId=" + 
          userId);
      list = query1.list();
      if (list != null && list.size() > 0) {
        Object[] ob = list.get(0);
        map.put("orgId", ob[0]);
        map.put("orgName", ob[1]);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public void addViewTimeForCollect(String wfWorkId) {
    Connection conn = null;
    Statement stmt = null;
    String viewFlag = "";
    ResultSet rs = null;
    String sql = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select workvieweddate from jsf_work where WF_WORK_ID=" + wfWorkId);
      while (rs.next())
        viewFlag = rs.getString("workvieweddate"); 
      if (viewFlag == null) {
        String cs = conn.getMetaData().getDatabaseProductName().toLowerCase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        if (cs.indexOf("mysql") != -1)
          sql = "update jsf_work set workvieweddate=\"" + time + "\" ,workreadmarker = 1 where WF_WORK_ID=" + wfWorkId; 
        if (cs.indexOf("oracle") != -1)
          sql = "update jsf_work set workvieweddate=to_date('" + time + "','yyyy-mm-dd hh24:mi:ss') ,workreadmarker = 1 where WF_WORK_ID=" + wfWorkId; 
        stmt.executeUpdate(sql);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
}
