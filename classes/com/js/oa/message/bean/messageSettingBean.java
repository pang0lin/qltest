package com.js.oa.message.bean;

import com.js.oa.message.po.MsLimitPO;
import com.js.oa.message.po.MsModelPO;
import com.js.oa.message.po.MsOutMoPO;
import com.js.util.hibernate.HibernateBase;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class messageSettingBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean judgePurviewMessage(String model, String domainId) throws HibernateException {
    begin();
    try {
      List listInner = this.session.createQuery(
          "select jhl.modelSend  from com.js.oa.message.po.MsModelPO jhl where jhl.modelSend ='" + model + "'" + " and jhl.content = '1' and jhl.domainId=" + domainId).list();
      if (listInner.size() != 0)
        return true; 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("------------------------------------------------------");
      System.out.println("----judge model whether has purview to send message---");
      System.out.println("------------------fail in ejb-------------------------");
      e.getStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return false;
  }
  
  public String getModleContents(String model, String title, String sendMen, String department, String domainId) throws HibernateException {
    String resultSendMessage = "";
    begin();
    try {
      List<Object[]> listInner = this.session.createQuery(
          "select jhl.reday1,jhl.reday2,jhl.reday3,jhl.reday4,jhl.reday5,jhl.reday6,jhl.reday7,jhl.reday8,jhl.reday9,jhl.reday10,jhl.reday11  from com.js.oa.message.po.MsModelPO jhl where jhl.modelSend ='" + model + "' and jhl.domainId=" + domainId).list();
      Object[] temp = listInner.get(0);
      resultSendMessage = temp[0].toString();
      resultSendMessage = String.valueOf(resultSendMessage) + settingToRealize(temp[1].toString(), title, sendMen, department);
      resultSendMessage = String.valueOf(resultSendMessage) + temp[2].toString();
      resultSendMessage = String.valueOf(resultSendMessage) + settingToRealize(temp[3].toString(), title, sendMen, department);
      resultSendMessage = String.valueOf(resultSendMessage) + temp[4].toString();
      resultSendMessage = String.valueOf(resultSendMessage) + settingToRealize(temp[5].toString(), title, sendMen, department);
      resultSendMessage = String.valueOf(resultSendMessage) + temp[6].toString();
      resultSendMessage = String.valueOf(resultSendMessage) + settingToRealize(temp[7].toString(), title, sendMen, department);
      resultSendMessage = String.valueOf(resultSendMessage) + temp[8].toString();
      resultSendMessage = String.valueOf(resultSendMessage) + settingToRealize(temp[9].toString(), title, sendMen, department);
      resultSendMessage = String.valueOf(resultSendMessage) + temp[10].toString();
      this.session.flush();
    } catch (Exception e) {
      System.out.println("------------------------------------------------------");
      System.out.println("---------------send model message---------------------");
      System.out.println("------------------fail in ejb-------------------------");
      e.getStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return resultSendMessage;
  }
  
  public String settingToRealize(String reday, String title, String sendMen, String department) {
    if (reday.equals("－空－"))
      return ""; 
    if (reday.equals("日期"))
      return (new Timestamp(System.currentTimeMillis())).toString().substring(5, 19); 
    if (reday.equals("部门"))
      return department; 
    if (reday.equals("发送人"))
      return sendMen; 
    if (reday.equals("标题")) {
      if (title.length() > 20) {
        String titl = String.valueOf(title.substring(0, 20)) + "...";
        return titl;
      } 
      return title;
    } 
    return "";
  }
  
  public boolean changeAllMembers(Map memberIdName, String memberId, String domainId) throws Exception, HibernateException {
    boolean success = true;
    begin();
    try {
      this.session.delete("from MsOutMoPO");
      if (!memberId.equals("")) {
        MsOutMoPO[] msOutMoPO = changeFroMap(memberIdName, memberId);
        for (int i = 0; i < msOutMoPO.length; i++) {
          msOutMoPO[i].setDomainId(domainId);
          this.session.save(msOutMoPO[i]);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      success = false;
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public MsOutMoPO[] changeFroMap(Map phoneIdName, String ids) {
    String[] id = ids.replace('$', ',').split(",,");
    MsOutMoPO[] msOutPO = new MsOutMoPO[id.length];
    for (int i = 0; i < id.length; i++) {
      msOutPO[i] = new MsOutMoPO();
      System.out.println("new Long(id[i]):-->" + new Long(id[i]));
      msOutPO[i].setSendOutId(new Long(id[i]));
      msOutPO[i].setSendOutMan((String)phoneIdName.get(id[i]));
    } 
    return msOutPO;
  }
  
  public boolean whetherRepeaInMsLimit(String id) throws HibernateException {
    boolean success = true;
    List<String> membersId = null;
    begin();
    try {
      List allMembers = this.session.createQuery("from MsLimitPO").list();
      for (Iterator<MsLimitPO> it = allMembers.iterator(); it.hasNext(); ) {
        MsLimitPO temp = it.next();
        membersId.add(temp.getSendLimitId().toString());
      } 
      success = !membersId.contains(id);
      this.session.flush();
    } catch (Exception e) {
      success = false;
      System.out.println("------------------------------------------------------");
      System.out.println("-----------get old and all members failed-------------");
      System.out.println("--------------------fail in ejb-----------------------");
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean addLimitMount(MsLimitPO addMsLimitPO) throws HibernateException {
    Boolean success = new Boolean(true);
    begin();
    try {
      this.session.save(addMsLimitPO);
      this.session.flush();
    } catch (Exception e) {
      success = new Boolean(false);
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public boolean changeLimitMount(MsLimitPO addMsLimitPO) throws HibernateException {
    boolean success = true;
    MsLimitPO temp = null;
    begin();
    try {
      temp = (MsLimitPO)this.session.get(MsLimitPO.class, addMsLimitPO.getLimitId());
      temp.setLimitCount(addMsLimitPO.getLimitCount());
      temp.setSendLimitId(addMsLimitPO.getSendLimitId());
      temp.setSendLimitMan(addMsLimitPO.getSendLimitMan());
      temp.setMonthCount(addMsLimitPO.getMonthCount());
      temp.setDayCount(addMsLimitPO.getDayCount());
      this.session.update(temp);
      this.session.flush();
    } catch (Exception e) {
      success = false;
      System.out.println("------------------------------------------------------");
      System.out.println("-------change a old limit mount and new members-------");
      System.out.println("--------------------fail in ejb-----------------------");
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public boolean deletLimitMounts(String LimitMountIds) throws HibernateException {
    boolean success = true;
    MsLimitPO temp = null;
    String[] id = LimitMountIds.split(",");
    begin();
    try {
      for (int i = 0; i < id.length; i++) {
        temp = (MsLimitPO)this.session.get(MsLimitPO.class, new Long(id[i]));
        if (temp != null)
          this.session.delete(temp); 
      } 
      this.session.flush();
    } catch (Exception e) {
      success = false;
      System.out.println("--------------------------------------------------------");
      System.out.println("delet a or more even all old limit mount and new members");
      System.out.println("---------------------fail in ejb------------------------");
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public boolean addModelSelf(MsModelPO modelName) throws HibernateException {
    boolean success = true;
    begin();
    try {
      this.session.save(modelName);
      this.session.flush();
    } catch (Exception e) {
      success = false;
      System.out.println("------------------------------------------------------");
      System.out.println("-------add a model to recrease  for the system--------");
      System.out.println("---------------------fail in ejb----------------------");
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public boolean changeModelExist(MsModelPO[] modelSetting, String domainId) throws HibernateException {
    boolean success = true;
    MsModelPO temp = null;
    begin();
    try {
      Query q = this.session.createQuery("from MsModelPO po where po.domainId=" + domainId);
      List result = q.list();
      for (Iterator<MsModelPO> it = result.listIterator(); it.hasNext(); ) {
        temp = it.next();
        MsModelPO tempUpdate = (MsModelPO)this.session.get(MsModelPO.class, temp.getModelOutId());
        tempUpdate.setContent("0");
        this.session.update(tempUpdate);
      } 
      for (int i = 0; i < modelSetting.length; i++) {
        System.out.println("modelSetting[i].getModelId():->" + modelSetting[i].getModelId());
        temp = (MsModelPO)this.session.get(MsModelPO.class, modelSetting[i].getModelOutId());
        temp.setContent("1");
        temp.setModelSend(modelSetting[i].getModelSend());
        temp.setReday1(modelSetting[i].getReday1());
        temp.setReday2(modelSetting[i].getReday2());
        temp.setReday3(modelSetting[i].getReday3());
        temp.setReday4(modelSetting[i].getReday4());
        temp.setReday5(modelSetting[i].getReday5());
        temp.setReday6(modelSetting[i].getReday6());
        temp.setReday7(modelSetting[i].getReday7());
        temp.setReday8(modelSetting[i].getReday8());
        temp.setReday9(modelSetting[i].getReday9());
        temp.setReday10(modelSetting[i].getReday10());
        temp.setReday11(modelSetting[i].getReday11());
        temp.setDomainId(domainId);
        this.session.update(temp);
      } 
      this.session.flush();
    } catch (Exception e) {
      success = false;
      System.out.println("------------------------------------------------------");
      System.out.println("--change one and more model to set system interface---");
      System.out.println("------------------fail in ejb-------------------------");
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public boolean allNotExist() throws HibernateException {
    boolean success = true;
    MsModelPO temp = null;
    begin();
    try {
      Query q = this.session.createQuery("from MsModelPO");
      List result = q.list();
      for (Iterator<MsModelPO> it = result.listIterator(); it.hasNext(); ) {
        temp = it.next();
        MsModelPO tempUpdate = (MsModelPO)this.session.get(MsModelPO.class, temp.getModelOutId());
        tempUpdate.setContent("0");
        this.session.update(tempUpdate);
      } 
      this.session.flush();
    } catch (Exception e) {
      success = false;
      System.out.println("------------------------------------------------------");
      System.out.println("--change one and more model to set system interface---");
      System.out.println("------------------fail in ejb-------------------------");
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
}
