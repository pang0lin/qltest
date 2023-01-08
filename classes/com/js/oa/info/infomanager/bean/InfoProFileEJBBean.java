package com.js.oa.info.infomanager.bean;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.info.infomanager.po.InfoFileReviewFlowPO;
import com.js.oa.info.infomanager.po.InfoProFilePO;
import com.js.oa.info.infomanager.po.InfoProFileReviewPO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class InfoProFileEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(InfoProFilePO infoProFilePO) throws HibernateException {
    Long id = Long.valueOf(0L);
    try {
      begin();
      id = (Long)this.session.save(infoProFilePO);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public List listAll(Map<String, String> paraMap) throws HibernateException {
    List<InfoProFilePO> list = new ArrayList<InfoProFilePO>();
    try {
      begin();
      String hql = "select aaa.fileId,aaa.proFile,aaa.fileName,aaa.fileNum,aaa.character,aaa.fileDate,aaa.reviewDate,aaa.department,aaa.author,aaa.viewMan,aaa.filePreId,aaa.version,aaa.isNew,aaa.filePath,aaa.appendPath,aaa.fileViewName,aaa.fileAppendName from com.js.oa.info.infomanager.po.InfoProFilePO aaa where 1=1 ";
      String where = "";
      if (paraMap != null) {
        if (paraMap.get("fileName") != null)
          where = String.valueOf(where) + " and aaa.fileName = ?"; 
        if (paraMap.get("isNew") != null)
          where = String.valueOf(where) + " and aaa.isNew = " + (String)paraMap.get("isNew"); 
        if (paraMap.get("fileSaveName") != null)
          where = String.valueOf(where) + " and aaa.fileName like :name"; 
        if (paraMap.get("fileNum") != null)
          where = String.valueOf(where) + " and aaa.fileNum like :fileNum"; 
        if (paraMap.get("reviewbeginDate") != null && paraMap.get("reviewendDate") != null)
          where = String.valueOf(where) + " and aaa.reviewDate > :reviewbeginDate and aaa.reviewDate < :reviewendDate"; 
      } 
      Query query = this.session.createQuery(String.valueOf(hql) + where);
      if (paraMap != null) {
        if (paraMap.get("fileName") != null)
          query.setString(0, paraMap.get("fileName")); 
        if (paraMap.get("fileSaveName") != null)
          query.setString("name", "%" + (String)paraMap.get("fileSaveName") + "%"); 
        if (paraMap.get("fileNum") != null)
          query.setString("fileNum", "%" + (String)paraMap.get("fileNum") + "%"); 
        if (paraMap.get("reviewbeginDate") != null && paraMap.get("reviewendDate") != null) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date startDate = null;
          Date endDae = null;
          try {
            startDate = sdf.parse(paraMap.get("reviewbeginDate"));
            endDae = sdf.parse(paraMap.get("reviewendDate"));
          } catch (ParseException e) {
            e.printStackTrace();
          } 
          query.setDate("reviewbeginDate", startDate);
          query.setDate("reviewendDate", endDae);
        } 
      } 
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getAllVersion(Long filePreId) throws HibernateException {
    List<InfoProFilePO> list = new ArrayList<InfoProFilePO>();
    try {
      begin();
      String hql = "select aaa.fileId,aaa.proFile,aaa.fileName,aaa.fileNum,aaa.character,aaa.fileDate,aaa.reviewDate,aaa.department,aaa.author,aaa.viewMan,aaa.filePreId,aaa.version,aaa.isNew,aaa.filePath,aaa.appendPath,aaa.fileViewName,aaa.fileAppendName from com.js.oa.info.infomanager.po.InfoProFilePO aaa where aaa.filePreId = ? order by aaa.fileDate";
      Query query = this.session.createQuery(hql);
      query.setLong(0, filePreId.longValue());
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listAllToReview(Map<String, String> paraMap) throws HibernateException {
    List<InfoProFilePO> list = new ArrayList<InfoProFilePO>();
    try {
      begin();
      String hql = "select aaa.fileId,aaa.proFile,aaa.fileName,aaa.fileNum,aaa.character,aaa.fileDate,aaa.reviewDate,aaa.department,aaa.author,aaa.viewMan,aaa.filePreId,aaa.version,aaa.isNew,aaa.filePath,aaa.appendPath from com.js.oa.info.infomanager.po.InfoProFilePO aaa ";
      String where = "";
      if (paraMap != null) {
        if (paraMap.get("fileName") != null)
          where = " where aaa.fileName = " + (String)paraMap.get("fileName"); 
        if (paraMap.get("isNew") != null)
          where = " where aaa.isNew = " + (String)paraMap.get("isNew"); 
        if (paraMap.get("year") != null)
          where = String.valueOf(where) + " and aaa.reviewDate >= ? and aaa.reviewDate <=?"; 
      } 
      Query query = this.session.createQuery(String.valueOf(hql) + where);
      if (paraMap != null && 
        paraMap.get("year") != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDae = null;
        try {
          startDate = sdf.parse(String.valueOf(paraMap.get("year")) + "-01-01");
          endDae = sdf.parse(String.valueOf(paraMap.get("year")) + "-12-31");
        } catch (ParseException e) {
          e.printStackTrace();
        } 
        query.setDate(0, startDate);
        query.setDate(1, endDae);
      } 
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listReview(Map paraMap) throws HibernateException {
    List<InfoProFileReviewPO> list = new ArrayList<InfoProFileReviewPO>();
    try {
      begin();
      Calendar cal = Calendar.getInstance();
      int year = cal.get(1);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date startDate = null;
      Date endDae = null;
      try {
        startDate = sdf.parse(String.valueOf(String.valueOf(year)) + "-01-01");
        endDae = sdf.parse(String.valueOf(String.valueOf(year)) + "-12-31");
      } catch (ParseException e) {
        e.printStackTrace();
      } 
      String hql = "select aaa.ReviewId,aaa.fileId,aaa.fileName,aaa.result,aaa.editId,aaa.editName,aaa.reviewDate,aaa.version,aaa.fileNum from com.js.oa.info.infomanager.po.InfoProFileReviewPO aaa";
      String where = " where aaa.reviewDate >= ? and aaa.reviewDate <=?";
      Query query = this.session.createQuery(String.valueOf(hql) + where);
      query.setDate(0, startDate);
      query.setDate(1, endDae);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void saveReview(InfoProFileReviewPO infoProFileReviewPO) throws HibernateException {
    try {
      begin();
      this.session.save(infoProFileReviewPO);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List getInfoProFileReviewPOByFileNum(String fileNum) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String hql = "select aaa.ReviewId,aaa.fileId,aaa.fileName,aaa.result,aaa.editId,aaa.editName,aaa.reviewDate,aaa.version,aaa.fileNum from com.js.oa.info.infomanager.po.InfoProFileReviewPO aaa";
      String where = " where aaa.fileNum=?";
      Query query = this.session.createQuery(String.valueOf(hql) + where);
      query.setString(0, fileNum);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void updateInfoProFileReviewPO(InfoProFileReviewPO infoProFileReviewPO) throws HibernateException {
    begin();
    try {
      this.session.update(infoProFileReviewPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public boolean delele(String ids) throws HibernateException {
    begin();
    boolean bl = false;
    InfoProFilePO infoProFilePO = new InfoProFilePO();
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.info.infomanager.po.InfoProFilePO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
      bl = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return bl;
  }
  
  public InfoProFilePO getInfoProFilePOById(Long id) throws HibernateException {
    begin();
    InfoProFilePO infoProFilePO = new InfoProFilePO();
    try {
      infoProFilePO = (InfoProFilePO)this.session.load(InfoProFilePO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return infoProFilePO;
  }
  
  public void updateInfoProFilePO(InfoProFilePO infoProFilePO) throws HibernateException {
    begin();
    try {
      this.session.update(infoProFilePO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List<InfoFileReviewFlowPO> getInfoFileReviewFlowPOById(String id) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<InfoFileReviewFlowPO> lists = new ArrayList<InfoFileReviewFlowPO>();
    InfoFileReviewFlowPO ifrf = null;
    String sql = "select file_1_name,file_1_num,version_1,review_data_1,review_result_1,review_persion_1 from HAIER_FILEREVIEW_C where HAIER_FILEREVIEW_C_FOREIGNKEY = ?";
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      while (rs.next()) {
        ifrf = new InfoFileReviewFlowPO();
        ifrf.setFileName(rs.getString(1));
        ifrf.setFileNum(rs.getString(2));
        ifrf.setVersion(rs.getString(3));
        ifrf.setReviewDate(rs.getDate(4));
        ifrf.setResult(rs.getString(5));
        ifrf.setEditName(rs.getString(6));
        lists.add(ifrf);
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
    return lists;
  }
  
  public EmployeeVO getEmployeeVOById(String id) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    EmployeeVO ee = new EmployeeVO();
    String sql = "select useraccounts,empname from org_employee where emp_id =?";
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        ee.setUserAccounts(rs.getString(1));
        ee.setEmpName(rs.getString(2));
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
    return ee;
  }
  
  public String getOrgNameById(String id) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String result = "";
    String sql = "select orgname from ORG_ORGANIZATION where org_id =?";
    conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
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
    return result;
  }
  
  public InfoProFilePO getInfoProFileReviseById(String id) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    InfoProFilePO ipfp = new InfoProFilePO();
    String sql = "select haier_fileid,haier_filename,haier_filenum,haier_character,haier_data,haier_dept,haier_author,haier_path from HAIER_FILEREVISE where HAIER_FILEREVISE_ID =?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ipfp.setFileId(Long.valueOf(rs.getString(1)));
        ipfp.setFileName(rs.getString(2));
        ipfp.setFileNum(rs.getString(3));
        ipfp.setCharacter(rs.getString(4));
        try {
          ipfp.setFileDate(sdf.parse(rs.getString(5)));
        } catch (ParseException e) {
          e.printStackTrace();
        } 
        ipfp.setDepartment(rs.getString(6));
        ipfp.setAuthor(rs.getString(7));
        ipfp.setFilePath(rs.getString(8));
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
    return ipfp;
  }
  
  public WFWorkFlowProcessPO getWFWorkFlowProcessPOById(Long id) throws HibernateException {
    begin();
    WFWorkFlowProcessPO wwfp = new WFWorkFlowProcessPO();
    try {
      wwfp = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return wwfp;
  }
  
  public boolean hasRoleId(String userId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select role_id from ORG_ROLE where roleuserid like '%" + userId + "%'";
    boolean isHas = false;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      ResourceBundle resource = ResourceBundle.getBundle("haier");
      String roleId = resource.getString("roleId");
      while (rs.next()) {
        if (roleId.equals(rs.getString(1)))
          isHas = true; 
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
    return isHas;
  }
}
