package com.js.oa.tjgzw.bean;

import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveAssociatePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class ReceiveFileGzwEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String initNumber(String receiveFileType, String year, String domainId) throws Exception {
    int result = -1;
    begin();
    try {
      Iterator<E> iter = this.session.iterate("select max(po.receiveFileFileNoCount) from com.js.doc.doc.po.GovReceiveFilePO po where po.receiveFileType='" + 
          receiveFileType + "' and po.createdTimeYear=" + year + " and po.domainId=" + domainId);
      if (iter.hasNext()) {
        result = Integer.parseInt(iter.next().toString()) + 1;
      } else {
        result = 1;
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return (new StringBuilder(String.valueOf(result))).toString();
  }
  
  public Long save(GovReceiveFilePO po) throws Exception {
    Long result = null;
    begin();
    try {
      po.setIsDraf("1");
      if (po.getId() > 0L) {
        GovReceiveFilePO oldPo = (GovReceiveFilePO)this.session.load(GovReceiveFilePO.class, Long.valueOf(po.getId()));
        if (StringUtils.isNotEmpty(oldPo.getZjkySeq()) && StringUtils.isNotEmpty(po.getZjkySeq()) && oldPo.getZjkySeq().equals(po.getZjkySeq())) {
          result = Long.valueOf(0L);
        } else {
          result = Long.valueOf(oldPo.getId());
        } 
        BeanUtils.copyProperties(oldPo, po);
        this.session.update(oldPo);
      } else {
        result = (Long)this.session.save(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public GovReceiveFilePO load(String receiveFileId) throws Exception {
    GovReceiveFilePO result = null;
    begin();
    try {
      result = (GovReceiveFilePO)this.session.load(GovReceiveFilePO.class, new Long(receiveFileId));
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long update(GovReceiveFilePO po, String receiveFileId) throws Exception {
    begin();
    try {
      po.setId(Long.parseLong(receiveFileId));
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return Long.valueOf(receiveFileId);
  }
  
  public Long completeReceiveFile(String receiveFileId) throws Exception {
    Long result = Long.valueOf(receiveFileId);
    begin();
    try {
      GovReceiveFilePO po = (GovReceiveFilePO)this.session.load(GovReceiveFilePO.class, Long.valueOf(receiveFileId));
      po.setReceiveFileStatus(Byte.parseByte("1"));
      this.session.flush();
    } catch (Exception e) {
      result = Long.valueOf("-1");
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Map getDossierInfo(String receiveFileId) throws Exception {
    Map<Object, Object> dossMap = null;
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select receiveFile.receiveFileTitle, receiveFile.receiveFileSendFileUnit, receiveFile.receiveFileFileNo, receiveFile.accessoryNameFile, receiveFile.accessorySaveNameFile, receiveFile.accessoryName, receiveFile.accessorySaveName,receiveFile.receiveFileReceiveDate,receiveFile.receiveFileSafetyGrade,receiveFile.receiveFileFileNumber from com.js.doc.doc.po.GovReceiveFilePO receiveFile where receiveFile.id=" + receiveFileId);
      if (iter.hasNext()) {
        dossMap = new HashMap<Object, Object>();
        Object[] obj = iter.next();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        String receiveDate = obj[7].toString();
        if (receiveDate != null && !receiveDate.equals("")) {
          Date dt = formatter1.parse(receiveDate);
          dossMap.put("YEAR", String.valueOf(dt.getYear() + 1900));
          dossMap.put("TAG", String.valueOf(dt.getYear() + 1900));
          dossMap.put("RECEIVEDATE", formatter1.format(dt));
        } else {
          dossMap.put("YEAR", "");
          dossMap.put("TAG", "");
          dossMap.put("RECEIVEDATE", "");
        } 
        dossMap.put("TITLE", obj[0]);
        dossMap.put("DUTY", obj[1]);
        dossMap.put("SECRET", obj[8]);
        dossMap.put("FILENUMBER", obj[9]);
        dossMap.put("FLOWNUMBER", obj[2]);
        List<String[]> accessoryList = new ArrayList();
        String ATTACHNAME = "";
        if (obj[3] != null && !obj[3].toString().equals("")) {
          String[] accessoryNameArray = { "" };
          String[] accessorySaveNameArray = { "" };
          if (obj[3].toString().indexOf("|") >= 0) {
            accessoryNameArray = obj[3].toString().split("\\|");
            accessorySaveNameArray = obj[4].toString().split("\\|");
          } else {
            accessoryNameArray[0] = obj[3].toString();
            accessorySaveNameArray[0] = obj[4].toString();
          } 
          for (int j = 0; j < accessoryNameArray.length; j++) {
            String[] tmp = { "", "" };
            tmp[0] = accessoryNameArray[j];
            tmp[1] = accessorySaveNameArray[j];
            accessoryList.add(tmp);
            ATTACHNAME = String.valueOf(ATTACHNAME) + accessoryNameArray[j] + "|";
          } 
        } 
        if (obj[5] != null && !obj[5].toString().equals("")) {
          String[] accessoryNameArray = { "" };
          String[] accessorySaveNameArray = { "" };
          if (obj[5].toString().indexOf("|") >= 0) {
            accessoryNameArray = obj[5].toString().split("\\|");
            accessorySaveNameArray = obj[6].toString().split("\\|");
          } else {
            accessoryNameArray[0] = obj[5].toString();
            accessorySaveNameArray[0] = obj[6].toString();
          } 
          for (int j = 0; j < accessoryNameArray.length; j++) {
            String[] tmp = { "", "" };
            tmp[0] = accessoryNameArray[j];
            tmp[1] = accessorySaveNameArray[j];
            accessoryList.add(tmp);
            ATTACHNAME = String.valueOf(ATTACHNAME) + accessoryNameArray[j] + "|";
          } 
        } 
        dossMap.put("ACCESSORY", accessoryList);
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return dossMap;
  }
  
  public Boolean isPigeonholed(String receiveFileId) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      Iterator iter = this.session.iterate("select receiveFile.thirdDossier from com.js.doc.doc.po.GovReceiveFilePO receiveFile where receiveFile.id=" + receiveFileId);
      if (iter.hasNext()) {
        Object thirdDossier = iter.next();
        if (thirdDossier != null && Integer.parseInt(thirdDossier.toString()) == 1)
          result = Boolean.TRUE; 
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer setPigeonholed(String receiveFileId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      GovReceiveFilePO po = (GovReceiveFilePO)this.session.load(GovReceiveFilePO.class, Long.valueOf(receiveFileId));
      po.setThirdDossier(Integer.valueOf("1"));
      this.session.flush();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer delete(String id) throws Exception {
    Integer result = Integer.valueOf("0");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select tableId from DOC_RECEIVEFILE where RECEIVEFILE_ID=" + id);
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      stmt.execute("delete from jsf_dealwithcomment where wf_dealwith_id in (select wf_dealwith_id from jsf_dealwith where databasetable_id=" + tableId + " and databaserecord_id=" + id + ")");
      stmt.execute("delete from jsf_dealwith where databasetable_id=" + tableId + " and databaserecord_id=" + id);
      stmt.execute("delete from jsf_p_tr where wf_proceedtransition_id in (select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + tableId + " and trecord_id=" + id + " ))");
      stmt.execute("delete from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + tableId + " and trecord_id=" + id + " )");
      stmt.execute("delete from jsf_p_readwritecontrol where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + tableId + " and trecord_id=" + id + " )");
      stmt.execute("delete from jsf_p_activity where ttable_id=" + tableId + " and trecord_id=" + id);
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + id);
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + id);
      stmt.execute("DELETE from JSDB.DOC_RECEIVEFILE WHERE RECEIVEFILE_ID=" + id);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public Long saveReceiveAssociate(ReceiveAssociatePO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Integer getReceiveAssociateNum(String receiveId) throws Exception {
    Integer result = new Integer(0);
    try {
      begin();
      Query query = this.session.createQuery("select count(po.aossiateId) from com.js.doc.doc.po.SendAssociatePO po where po.receiveFileId=" + receiveId);
      result = query.iterate().next();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getRecieveAssociateList(String receiveId) throws Exception {
    begin();
    List<Object[]> result = new ArrayList();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String ww = "select s.documentsendfile_id, s.createdtime,org.orgName,s.sendfile_transactstatus,s.sendfile_tableId from doc_documentsendfile s , JSDB.ORG_ORGANIZATION org, JSDB.DOC_sendAssociate  a  where a.receiveFileId=" + receiveId + " and  s.documentsendfile_id =a.sendFileId and  s.createdorg=org.ORG_ID  order by s.documentsendfile_id desc ";
      ResultSet rs = stmt.executeQuery(ww);
      while (rs.next()) {
        Object[] obj = { rs.getLong(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5) };
        result.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
}
