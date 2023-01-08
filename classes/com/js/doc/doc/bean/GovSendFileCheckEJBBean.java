package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovSendFileCheckAccePO;
import com.js.doc.doc.po.GovSendFileCheckCommentPO;
import com.js.doc.doc.po.GovSendFileCheckPO;
import com.js.util.hibernate.HibernateBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovSendFileCheckEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String add(GovSendFileCheckPO po) throws Exception {
    String retString = "-1";
    begin();
    try {
      Serializable serializable = this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      retString = "-1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public String delBatch(String ids) throws Exception {
    String retString = "false";
    begin();
    try {
      if (ids != null && ids.indexOf(",") != -1) {
        ids = ids.substring(0, ids.length() - 1);
        this.session.delete(" from com.js.doc.doc.po.GovSendFileCheckPO po where po.id in(" + 
            ids + ")");
        this.session.flush();
        retString = "true";
      } 
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public List load(String editId) throws Exception {
    List<GovSendFileCheckPO> list = new ArrayList();
    List tmp = new ArrayList();
    GovSendFileCheckPO po = new GovSendFileCheckPO();
    Long tmpId = Long.valueOf(editId);
    begin();
    try {
      po = (GovSendFileCheckPO)this.session.load(GovSendFileCheckPO.class, tmpId);
      tmp = this.session.createQuery("select po.empIdea , po.empSign,po.empName,po.createdTime from com.js.doc.doc.po.GovSendFileCheckCommentPO po where po.sendFile.id=" + editId + " order by po.createdTime").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      list.add(po);
      list.add(tmp);
    } 
    return list;
  }
  
  public String update(GovSendFileCheckPO po, String editId) throws Exception {
    String retString = "false";
    Long tmpId = Long.valueOf(editId);
    begin();
    try {
      GovSendFileCheckPO ppo = (GovSendFileCheckPO)this.session.load(
          GovSendFileCheckPO.class, tmpId);
      ppo.setSendFileCheckComeUnit(po.getSendFileCheckComeUnit());
      ppo.setSendFileCheckFileNumber(po.getSendFileCheckFileNumber());
      ppo.setSendFileCheckFinishDate(po.getSendFileCheckFinishDate());
      ppo.setSendFileCheckLeaderComment(po.getSendFileCheckLeaderComment());
      ppo.setSendFileCheckReceiveDate(po.getSendFileCheckReceiveDate());
      ppo.setSendFileCheckTitle(po.getSendFileCheckTitle());
      ppo.setSendFileCheckWriterComment(po.getSendFileCheckWriterComment());
      ppo.setField1(po.getField1());
      ppo.setField2(po.getField2());
      ppo.setField3(po.getField3());
      ppo.setField4(po.getField4());
      ppo.setField5(po.getField5());
      ppo.setField6(po.getField6());
      ppo.setField7(po.getField7());
      ppo.setField8(po.getField8());
      ppo.setField9(po.getField9());
      ppo.setField10(po.getField10());
      this.session.update(ppo);
      this.session.flush();
      retString = "true";
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public String selfPostil(String recordId, String curUserId, String leaderAdvice) throws Exception {
    String retString = "false";
    Long tmpId = Long.valueOf(recordId);
    begin();
    try {
      GovSendFileCheckPO ppo = (GovSendFileCheckPO)this.session.load(
          GovSendFileCheckPO.class, tmpId);
      GovSendFileCheckCommentPO po = new GovSendFileCheckCommentPO();
      po.setSendFile(ppo);
      po.setEmpId(Long.parseLong(curUserId));
      po.setEmpIdea(leaderAdvice);
      List<Object[]> list = this.session.createQuery("select po.empName,po.empSignImg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
          curUserId).list();
      if (list.size() > 0) {
        Object[] obj = list.get(0);
        po.setEmpName((String)obj[0]);
        if (obj[1] != null)
          po.setEmpSign(String.valueOf(obj[1])); 
      } 
      po.setCreatedTime(new Date());
      this.session.save(po);
      this.session.flush();
      retString = "true";
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public String add(GovSendFileCheckPO po, String[] displayName, String[] saveName) throws Exception {
    String retString = "-1";
    begin();
    try {
      Serializable serializable = this.session.save(po);
      if (displayName != null && saveName != null)
        for (int i = 0; i < displayName.length; i++) {
          GovSendFileCheckAccePO accePO = new GovSendFileCheckAccePO();
          accePO.setDisplayName(displayName[i]);
          accePO.setSaveName(saveName[i]);
          accePO.setSendFileCheck(po);
          this.session.save(accePO);
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      retString = "-1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public Map getDossierInfo(String sendFileCheckId) throws Exception {
    Map<Object, Object> dossMap = null;
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select a.sendFileCheckTitle, a.sendFileCheckComeUnit, a.sendFileCheckFileNumber, a.field1, a.submitTime from com.js.doc.doc.po.GovSendFileCheckPO a where a.id=" + sendFileCheckId);
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
        Iterator iter2 = this.session.iterate("select a.displayName,a.saveName from com.js.doc.doc.po.GovSendFileCheckAccePO a join a.sendFileCheck b where b.id=" + sendFileCheckId);
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
      Iterator iter = this.session.iterate("select a.thirdDossier from com.js.doc.doc.po.GovSendFileCheckPO a where a.id=" + sendFileCheckId);
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
      GovSendFileCheckPO po = (GovSendFileCheckPO)this.session.load(GovSendFileCheckPO.class, Long.valueOf(sendFileCheckId));
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
}
