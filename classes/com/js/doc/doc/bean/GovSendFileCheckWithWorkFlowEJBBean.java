package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovSendFileCheckWithWorkFlowAccePO;
import com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovSendFileCheckWithWorkFlowEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(GovSendFileCheckWithWorkFlowPO paraPO, String[] displayName, String[] saveName) throws Exception {
    Long id = new Long(-1L);
    begin();
    try {
      id = (Long)this.session.save(paraPO);
      if (displayName != null && saveName != null)
        for (int i = 0; i < displayName.length; i++) {
          GovSendFileCheckWithWorkFlowAccePO accePO = new GovSendFileCheckWithWorkFlowAccePO();
          accePO.setDisplayName(displayName[i]);
          accePO.setSaveName(saveName[i]);
          accePO.setSendFileCheck(paraPO);
          this.session.save(accePO);
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      id = new Long(-1L);
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public Long update(GovSendFileCheckWithWorkFlowPO paraPO, String[] displayName, String[] saveName) throws Exception {
    Long id = new Long(-1L);
    begin();
    try {
      GovSendFileCheckWithWorkFlowPO tmpPO = 
        (GovSendFileCheckWithWorkFlowPO)this.session.load(GovSendFileCheckWithWorkFlowPO.class, 
          new Long(paraPO.getId()));
      if (tmpPO != null)
        id = new Long(paraPO.getId()); 
      Set acce = tmpPO.getAccessory();
      if (acce != null) {
        Iterator iter = acce.iterator();
        while (iter.hasNext())
          this.session.delete(iter.next()); 
      } 
      tmpPO.setSendFileCheckComeUnit(paraPO.getSendFileCheckComeUnit());
      tmpPO.setSendFileCheckTitle(paraPO.getSendFileCheckTitle());
      tmpPO.setField1(paraPO.getField1());
      tmpPO.setField2(paraPO.getField2());
      tmpPO.setField3(paraPO.getField3());
      tmpPO.setField4(paraPO.getField4());
      tmpPO.setField5(paraPO.getField5());
      tmpPO.setField6(paraPO.getField6());
      tmpPO.setField7(paraPO.getField7());
      tmpPO.setField8(paraPO.getField8());
      tmpPO.setField9(paraPO.getField9());
      tmpPO.setField10(paraPO.getField10());
      tmpPO.setTransactStatus(paraPO.getTransactStatus());
      if (displayName != null && saveName != null)
        for (int i = 0; i < displayName.length; i++) {
          GovSendFileCheckWithWorkFlowAccePO accePO = new GovSendFileCheckWithWorkFlowAccePO();
          accePO.setDisplayName(displayName[i]);
          accePO.setSaveName(saveName[i]);
          accePO.setSendFileCheck(paraPO);
          this.session.save(accePO);
        }  
      this.session.update(tmpPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      id = new Long(-1L);
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public ArrayList load(Long paraId) throws Exception {
    ArrayList<GovSendFileCheckWithWorkFlowPO> resultList = new ArrayList();
    begin();
    try {
      GovSendFileCheckWithWorkFlowPO tmpPO = 
        (GovSendFileCheckWithWorkFlowPO)this.session.load(GovSendFileCheckWithWorkFlowPO.class, 
          paraId);
      if (tmpPO != null)
        resultList.add(tmpPO); 
    } catch (Exception e) {
      System.out.println(
          "--GovSendFileCheckWithWorkFlowEJBBean(load)-----------------");
      e.printStackTrace();
      System.out.println(
          "--GovSendFileCheckWithWorkFlowEJBBean(load)-----------------");
      resultList = null;
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return resultList;
  }
  
  public Map getDossierInfo(String sendFileCheckId) throws Exception {
    Map<Object, Object> dossMap = null;
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select a.sendFileCheckTitle, a.sendFileCheckComeUnit, a.sendFileCheckFileNumber, a.field1, a.submitTime from com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO a where a.id=" + sendFileCheckId);
      if (iter.hasNext()) {
        Calendar now = Calendar.getInstance();
        dossMap = new HashMap<Object, Object>();
        Object[] obj = iter.next();
        String month = (new StringBuilder(String.valueOf(now.get(2) + 1))).toString();
        if (month.length() == 1)
          month = "0" + month; 
        String hour = (new StringBuilder(String.valueOf(now.get(11)))).toString();
        if (hour.length() == 1)
          hour = "0" + hour; 
        dossMap.put("RECID", String.valueOf(now.get(1)) + month + now.get(5) + hour + now.get(12) + sendFileCheckId);
        dossMap.put("MAINTITLE", obj[0]);
        dossMap.put("RESPONSIBLEBY", obj[1]);
        dossMap.put("DOC_NO", (obj[2] == null) ? "" : obj[2]);
        dossMap.put("FOLDER_LOCATION", obj[3]);
        dossMap.put("SEND_DATE", obj[4]);
        dossMap.put("SBT_WORD", "");
        List accessoryList = new ArrayList();
        Iterator iter2 = this.session.iterate("select a.displayName,a.saveName from com.js.doc.doc.po.GovSendFileCheckWithWorkFlowAccePO a join a.sendFileCheck b where b.id=" + sendFileCheckId);
        while (iter2.hasNext())
          accessoryList.add(iter2.next()); 
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
  
  public Boolean isPigeonholed(String sendFileCheckId) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      Iterator iter = this.session.iterate("select a.thirdDossier from com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO a where a.id=" + sendFileCheckId);
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
  
  public Integer setPigeonholed(String sendFileCheckId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      GovSendFileCheckWithWorkFlowPO po = (GovSendFileCheckWithWorkFlowPO)this.session.load(GovSendFileCheckWithWorkFlowPO.class, Long.valueOf(sendFileCheckId));
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
  
  public void deleteBatch(String ids) throws Exception {
    begin();
    String where = "from com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO po where po.id in(" + 
      ids + ")";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=34");
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + 
          " AND WORKRECORD_ID=" + ids);
      stmt.execute(
          "DELETE from JSDB.DOC_SENDFILECHECKWITHWF_ACC WHERE SENDFILECHECK_ID=" + 
          ids);
      stmt.execute(
          "DELETE from JSDB.DOC_SENDFILECHECKWITHWORKFLOW WHERE SENDFILECHECK_ID=" + 
          ids);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public Long completeSendFileCheck(String sendFileCheckId) throws Exception {
    Long result = Long.valueOf(sendFileCheckId);
    begin();
    try {
      GovSendFileCheckWithWorkFlowPO po = (GovSendFileCheckWithWorkFlowPO)this.session.load(GovSendFileCheckWithWorkFlowPO.class, Long.valueOf(sendFileCheckId));
      po.setTransactStatus("1");
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
}
