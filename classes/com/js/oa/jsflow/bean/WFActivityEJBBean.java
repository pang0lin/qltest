package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.po.WFPressPO;
import com.js.oa.jsflow.po.WFProtectControlPO;
import com.js.oa.jsflow.po.WFReadWriteControlPO;
import com.js.oa.jsflow.po.WFTransitionPO;
import com.js.oa.jsflow.po.WFTransitionRestrictionPO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WFActivityEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long addWithoutCondition(String[] activityPara, String[] readField, String[] writeField, String[] protectField, String formId) throws Exception {
    begin();
    Long activityId = null;
    try {
      WFWorkFlowProcessPO processPO = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, new Long(activityPara[0]));
      WFActivityPO activityPO = new WFActivityPO();
      activityPO.setWfWorkFlowProcess(processPO);
      activityPO.setActivityName(activityPara[1]);
      activityPO.setActivityDescription(activityPara[2]);
      activityPO.setActivityType(Integer.parseInt(activityPara[3]));
      activityPO.setAllowStandFor(Integer.parseInt(activityPara[4]));
      activityPO.setAllowCancel(Integer.parseInt(activityPara[5]));
      activityPO.setAllowTransition(Integer.parseInt(activityPara[6]));
      activityPO.setAllowSmsRemind(activityPara[45]);
      activityPO.setParticipantType(Integer.parseInt(activityPara[7]));
      activityPO.setParticipantUser(activityPara[8]);
      activityPO.setParticipantUserName(activityPara[9]);
      activityPO.setParticipantUserField(activityPara[10]);
      activityPO.setPressType(Integer.parseInt(activityPara[11]));
      activityPO.setDeadLineTime(Integer.parseInt(activityPara[12]));
      activityPO.setPressMotionTime(Integer.parseInt(activityPara[13]));
      activityPO.setParticipantGroup(activityPara[14]);
      activityPO.setTransactType(activityPara[15]);
      activityPO.setActiCommType(Integer.parseInt(activityPara[16]));
      activityPO.setActiCommField(activityPara[17]);
      activityPO.setNeedPassRound(Integer.parseInt(activityPara[18]));
      activityPO.setPassRoundUserType(Integer.parseInt(activityPara[19]));
      activityPO.setPassRoundUser(activityPara[20]);
      activityPO.setPassRoundUserGroup(activityPara[21]);
      activityPO.setPassRoundUserName(activityPara[22]);
      activityPO.setPassRoundUserField(activityPara[23]);
      activityPO.setPassRoundCommField(activityPara[24]);
      activityPO.setParticipantRole(activityPara[25]);
      activityPO.setPassRoundRole(activityPara[26]);
      activityPO.setActivityClass(Integer.parseInt(activityPara[27]));
      if (Integer.parseInt(activityPara[27]) == 2)
        activityPO.setActivityType(0); 
      activityPO.setActivitySubProc(activityPara[28]);
      if (activityPara[29] != null && !activityPara[29].toUpperCase().equals("NULL"))
        activityPO.setSubProcType(Integer.parseInt(activityPara[29])); 
      activityPO.setFormClassMethod(activityPara[30]);
      activityPO.setParticipantGivenOrgName(activityPara[31]);
      activityPO.setParticipantGivenOrg(activityPara[32]);
      activityPO.setPassRoundGivenOrgName(activityPara[33]);
      activityPO.setPassRoundGivenOrg(activityPara[34]);
      activityPO.setUntreadMethod(activityPara[35]);
      activityPO.setCommentRange(activityPara[36]);
      activityPO.setCommentRangeName(activityPara[37]);
      activityPO.setOperButton(activityPara[42]);
      activityPO.setTranType(activityPara[46]);
      activityPO.setTranCustomExtent(activityPara[47]);
      activityPO.setTranCustomExtentId(activityPara[48]);
      activityPO.setTranReadType(activityPara[51]);
      activityPO.setTranReadCustomExtent(activityPara[52]);
      activityPO.setTranReadCustomExtentId(activityPara[53]);
      activityPO.setPressDealType(activityPara[49]);
      activityPO.setActiCommFieldType(activityPara[43]);
      activityPO.setPassRoundCommFieldType(activityPara[44]);
      activityPO.setExtendMainTable(activityPara[50]);
      if (activityPara.length > 55) {
        activityPO.setHandleType(Integer.valueOf(activityPara[55]).intValue());
        activityPO.setHandleClass(activityPara[56]);
        activityPO.setHandleMethod(activityPara[57]);
        activityPO.setHandleView(activityPara[58]);
        activityPO.setHandleParam(activityPara[59]);
      } else {
        activityPO.setHandleType(0);
        activityPO.setHandleClass("");
        activityPO.setHandleMethod("");
        activityPO.setHandleView("");
        activityPO.setHandleParam("");
      } 
      if (activityPara.length > 60) {
        activityPO.setWebServiceUrl(activityPara[60]);
        activityPO.setWebServiceMethod(activityPara[61]);
        activityPO.setWebServicePara(activityPara[62]);
        activityPO.setWebServiceNameSpace(activityPara[63]);
      } else {
        activityPO.setWebServiceUrl("");
        activityPO.setWebServiceMethod("");
        activityPO.setWebServicePara("");
        activityPO.setWebServiceNameSpace("");
      } 
      if (activityPara.length > 64) {
        activityPO.setSendMsgToInitiator(activityPara[64]);
      } else {
        activityPO.setSendMsgToInitiator("0");
      } 
      activityPO.setDomainId(processPO.getDomainId());
      if (formId == null || formId.equals("") || formId.toUpperCase().equals("NULL"))
        formId = "0"; 
      activityPO.setFormId(formId);
      activityId = (Long)this.session.save(activityPO);
      if (readField != null)
        for (int i = 0; i < readField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(0);
          wfrwControlPO.setControlField(new Long(readField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          wfrwControlPO.setDomainId(processPO.getDomainId());
          this.session.save(wfrwControlPO);
        }  
      if (writeField != null)
        for (int i = 0; i < writeField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(1);
          wfrwControlPO.setControlField(new Long(writeField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          wfrwControlPO.setDomainId(processPO.getDomainId());
          this.session.save(wfrwControlPO);
        }  
      if (protectField != null)
        for (int i = 0; i < protectField.length; i++) {
          WFProtectControlPO wfptControlPO = new WFProtectControlPO();
          wfptControlPO.setControlType(2);
          wfptControlPO.setControlField(new Long(protectField[i]));
          wfptControlPO.setWfActivity(activityPO);
          wfptControlPO.setDomainId(processPO.getDomainId());
          this.session.save(wfptControlPO);
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return activityId;
  }
  
  public void updateWithoutCondition(String[] activityParameter, String[] readField, String[] writeField, String[] protectField, String activityId, String formId) throws Exception {
    updateWithoutCondition(activityParameter, readField, writeField, (String[])null, protectField, activityId, formId);
  }
  
  public void updateWithoutCondition(String[] activityParameter, String[] readField, String[] writeField, String[] noDisplayField, String[] protectField, String activityId, String formId) throws Exception {
    begin();
    try {
      Query query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFPressPO aaa  join aaa.wfActivity bbb where bbb.wfActivityId = " + 
          activityId);
      List<WFPressPO> list = query.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          WFPressPO pressPO = list.get(i);
          this.session.delete(pressPO);
        }  
      query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFReadWriteControlPO aaa  join aaa.wfActivity bbb where bbb.wfActivityId = " + 
          activityId);
      list = query.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          WFReadWriteControlPO rwPO = (WFReadWriteControlPO)list.get(i);
          this.session.delete(rwPO);
        }  
      query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFProtectControlPO aaa  join aaa.wfActivity bbb where bbb.wfActivityId = " + 
          activityId);
      list = query.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          WFProtectControlPO ptPO = (WFProtectControlPO)list.get(i);
          this.session.delete(ptPO);
        }  
      WFActivityPO activityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
      activityPO.setActivityName(activityParameter[1]);
      activityPO.setActivityDescription(activityParameter[2]);
      activityPO.setActivityType(Integer.parseInt(activityParameter[3]));
      activityPO.setAllowStandFor(Integer.parseInt(activityParameter[4]));
      activityPO.setAllowCancel(Integer.parseInt(activityParameter[5]));
      activityPO.setAllowTransition(Integer.parseInt(activityParameter[6]));
      activityPO.setAllowSmsRemind(activityParameter[45]);
      activityPO.setParticipantType(Integer.parseInt(activityParameter[7]));
      activityPO.setParticipantUser(activityParameter[8]);
      activityPO.setParticipantUserName(activityParameter[9]);
      activityPO.setParticipantUserField(activityParameter[10]);
      activityPO.setPressType(Integer.parseInt(activityParameter[11]));
      activityPO.setDeadLineTime(Integer.parseInt(activityParameter[12]));
      activityPO.setPressMotionTime(Integer.parseInt(activityParameter[13]));
      activityPO.setParticipantGroup(activityParameter[14]);
      activityPO.setTransactType(activityParameter[15]);
      activityPO.setActiCommType(Integer.parseInt(activityParameter[16]));
      activityPO.setActiCommField(activityParameter[17]);
      activityPO.setNeedPassRound(Integer.parseInt(activityParameter[18]));
      activityPO.setPassRoundUserType(Integer.parseInt(activityParameter[19]));
      activityPO.setPassRoundUser(activityParameter[20]);
      activityPO.setPassRoundUserGroup(activityParameter[21]);
      activityPO.setPassRoundUserName(activityParameter[22]);
      activityPO.setPassRoundUserField(activityParameter[23]);
      activityPO.setPassRoundCommField(activityParameter[24]);
      activityPO.setParticipantRole(activityParameter[25]);
      activityPO.setPassRoundRole(activityParameter[26]);
      activityPO.setActivityClass(Integer.parseInt(activityParameter[27]));
      if (Integer.parseInt(activityParameter[27]) == 2)
        activityPO.setActivityType(0); 
      activityPO.setActivitySubProc(activityParameter[28]);
      activityPO.setSubProcType(Integer.parseInt(activityParameter[29]));
      activityPO.setFormClassMethod(activityParameter[30]);
      activityPO.setParticipantGivenOrgName(activityParameter[31]);
      activityPO.setParticipantGivenOrg(activityParameter[32]);
      activityPO.setPassRoundGivenOrgName(activityParameter[33]);
      activityPO.setPassRoundGivenOrg(activityParameter[34]);
      activityPO.setUntreadMethod(activityParameter[35]);
      activityPO.setCommentRange(activityParameter[36]);
      activityPO.setCommentRangeName(activityParameter[37]);
      activityPO.setOperButton(activityParameter[42]);
      activityPO.setTranType(activityParameter[46]);
      activityPO.setTranCustomExtent(activityParameter[47]);
      activityPO.setTranCustomExtentId(activityParameter[48]);
      activityPO.setTranReadType(activityParameter[51]);
      activityPO.setTranReadCustomExtent(activityParameter[52]);
      activityPO.setTranReadCustomExtentId(activityParameter[53]);
      activityPO.setPressDealType(activityParameter[49]);
      activityPO.setActiCommFieldType(activityParameter[43]);
      activityPO.setPassRoundCommFieldType(activityParameter[44]);
      activityPO.setExtendMainTable(activityParameter[50]);
      activityPO.setExeScript(activityParameter[54]);
      activityPO.setHandleType(Integer.valueOf(activityParameter[55]).intValue());
      activityPO.setHandleClass(activityParameter[56]);
      activityPO.setHandleMethod(activityParameter[57]);
      activityPO.setHandleView(activityParameter[58]);
      activityPO.setHandleParam(activityParameter[59]);
      activityPO.setWebServiceUrl(activityParameter[60]);
      activityPO.setWebServiceMethod(activityParameter[61]);
      activityPO.setWebServicePara(activityParameter[62]);
      activityPO.setWebServiceNameSpace(activityParameter[63]);
      activityPO.setSendMsgToInitiator(activityParameter[64]);
      activityPO.setSendMsgToDealDone(activityParameter[75]);
      activityPO.setOpinionmust(Integer.valueOf(activityParameter[65]).intValue());
      activityPO.setHandSignType(activityParameter[66]);
      activityPO.setBeforeSubmit(activityParameter[67]);
      activityPO.setOpinionLengthSet(activityParameter[68]);
      activityPO.setOpinionLengthMin(activityParameter[69]);
      activityPO.setOpinionLengthMax(activityParameter[70]);
      activityPO.setAllowAutoCheck(activityParameter[71]);
      activityPO.setIsDivide(activityParameter[72]);
      activityPO.setIsGather(activityParameter[73]);
      activityPO.setIsBranch(activityParameter[74]);
      if (readField != null)
        for (int i = 0; i < readField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(0);
          wfrwControlPO.setControlField(new Long(readField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          this.session.save(wfrwControlPO);
        }  
      if (writeField != null)
        for (int i = 0; i < writeField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(1);
          wfrwControlPO.setControlField(new Long(writeField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          this.session.save(wfrwControlPO);
        }  
      if (noDisplayField != null)
        for (int i = 0; i < noDisplayField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(2);
          wfrwControlPO.setControlField(new Long(noDisplayField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          this.session.save(wfrwControlPO);
        }  
      if (protectField != null)
        for (int i = 0; i < protectField.length; i++) {
          WFProtectControlPO wfptControlPO = new WFProtectControlPO();
          wfptControlPO.setControlType(2);
          wfptControlPO.setControlField(new Long(protectField[i]));
          wfptControlPO.setWfActivity(activityPO);
          wfptControlPO.setDomainId(activityPO.getDomainId());
          this.session.save(wfptControlPO);
        }  
      if (formId == null || formId.equals("") || formId.toUpperCase().equals("NULL"))
        formId = "0"; 
      activityPO.setFormId(formId);
      this.session.flush();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void updateWithCondition(String[] activityParameter, String[] readField, String[] writeField, String[] protectField, String[] condition, String[] operate, String[] compareValue, String[] timeLimit, String[] motionTime, String activityId, String formId) throws Exception {
    updateWithCondition(activityParameter, readField, writeField, (String[])null, protectField, condition, operate, compareValue, timeLimit, motionTime, activityId, formId);
  }
  
  public void updateWithCondition(String[] activityParameter, String[] readField, String[] writeField, String[] noDisplayField, String[] protectField, String[] condition, String[] operate, String[] compareValue, String[] timeLimit, String[] motionTime, String activityId, String formId) throws Exception {
    begin();
    try {
      Query query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFPressPO aaa  join aaa.wfActivity bbb where bbb.wfActivityId = " + 
          activityId);
      List<WFPressPO> list = query.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          WFPressPO pressPO = list.get(i);
          this.session.delete(pressPO);
        }  
      query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFReadWriteControlPO aaa  join aaa.wfActivity bbb where bbb.wfActivityId = " + 
          activityId);
      list = query.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          WFReadWriteControlPO rwPO = (WFReadWriteControlPO)list.get(i);
          this.session.delete(rwPO);
        }  
      query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFProtectControlPO aaa  join aaa.wfActivity bbb where bbb.wfActivityId = " + 
          activityId);
      list = query.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          WFProtectControlPO ptPO = (WFProtectControlPO)list.get(i);
          this.session.delete(ptPO);
        }  
      WFActivityPO activityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
      activityPO.setActivityName(activityParameter[1]);
      activityPO.setActivityDescription(activityParameter[2]);
      activityPO.setActivityType(Integer.parseInt(activityParameter[3]));
      activityPO.setAllowStandFor(Integer.parseInt(activityParameter[4]));
      activityPO.setAllowCancel(Integer.parseInt(activityParameter[5]));
      activityPO.setAllowTransition(Integer.parseInt(activityParameter[6]));
      activityPO.setAllowSmsRemind(activityParameter[45]);
      activityPO.setParticipantType(Integer.parseInt(activityParameter[7]));
      activityPO.setParticipantUser(activityParameter[8]);
      activityPO.setParticipantUserName(activityParameter[9]);
      activityPO.setParticipantUserField(activityParameter[10]);
      activityPO.setPressType(Integer.parseInt(activityParameter[11]));
      activityPO.setDeadLineTime(Integer.parseInt(activityParameter[12]));
      activityPO.setPressMotionTime(Integer.parseInt(activityParameter[13]));
      activityPO.setParticipantGroup(activityParameter[14]);
      activityPO.setTransactType(activityParameter[15]);
      activityPO.setActiCommType(Integer.parseInt(activityParameter[16]));
      activityPO.setActiCommField(activityParameter[17]);
      activityPO.setNeedPassRound(Integer.parseInt(activityParameter[18]));
      activityPO.setPassRoundUserType(Integer.parseInt(activityParameter[19]));
      activityPO.setPassRoundUser(activityParameter[20]);
      activityPO.setPassRoundUserGroup(activityParameter[21]);
      activityPO.setPassRoundUserName(activityParameter[22]);
      activityPO.setPassRoundUserField(activityParameter[23]);
      activityPO.setPassRoundCommField(activityParameter[24]);
      activityPO.setParticipantRole(activityParameter[25]);
      activityPO.setPassRoundRole(activityParameter[26]);
      activityPO.setActivityClass(Integer.parseInt(activityParameter[27]));
      if (Integer.parseInt(activityParameter[27]) == 2)
        activityPO.setActivityType(0); 
      activityPO.setActivitySubProc(activityParameter[28]);
      activityPO.setSubProcType(Integer.parseInt(activityParameter[29]));
      activityPO.setFormClassMethod(activityParameter[30]);
      activityPO.setParticipantGivenOrgName(activityParameter[31]);
      activityPO.setParticipantGivenOrg(activityParameter[32]);
      activityPO.setPassRoundGivenOrgName(activityParameter[33]);
      activityPO.setPassRoundGivenOrg(activityParameter[34]);
      activityPO.setUntreadMethod(activityParameter[35]);
      activityPO.setCommentRange(activityParameter[36]);
      activityPO.setCommentRangeName(activityParameter[37]);
      activityPO.setOperButton(activityParameter[42]);
      activityPO.setTranType(activityParameter[46]);
      activityPO.setTranCustomExtent(activityParameter[47]);
      activityPO.setTranCustomExtentId(activityParameter[48]);
      activityPO.setTranReadType(activityParameter[51]);
      activityPO.setTranReadCustomExtent(activityParameter[52]);
      activityPO.setTranReadCustomExtentId(activityParameter[53]);
      activityPO.setPressDealType(activityParameter[49]);
      activityPO.setActiCommFieldType(activityParameter[43]);
      activityPO.setPassRoundCommFieldType(activityParameter[44]);
      activityPO.setExtendMainTable(activityParameter[50]);
      activityPO.setExeScript(activityParameter[54]);
      if (readField != null)
        for (int i = 0; i < readField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(0);
          wfrwControlPO.setControlField(new Long(readField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          wfrwControlPO.setDomainId(activityPO.getDomainId());
          this.session.save(wfrwControlPO);
        }  
      if (writeField != null)
        for (int i = 0; i < writeField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(1);
          wfrwControlPO.setControlField(new Long(writeField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          wfrwControlPO.setDomainId(activityPO.getDomainId());
          this.session.save(wfrwControlPO);
        }  
      if (noDisplayField != null)
        for (int i = 0; i < noDisplayField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(2);
          wfrwControlPO.setControlField(new Long(noDisplayField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          this.session.save(wfrwControlPO);
        }  
      if (protectField != null)
        for (int i = 0; i < protectField.length; i++) {
          WFProtectControlPO wfptControlPO = new WFProtectControlPO();
          wfptControlPO.setControlType(2);
          wfptControlPO.setControlField(new Long(protectField[i]));
          wfptControlPO.setWfActivity(activityPO);
          wfptControlPO.setDomainId(activityPO.getDomainId());
          this.session.save(wfptControlPO);
        }  
      if (condition != null)
        for (int i = 0; i < condition.length; i++) {
          WFPressPO pressPO = new WFPressPO();
          pressPO.setConditionField(new Long(condition[i]));
          pressPO.setPressRelation(operate[i]);
          pressPO.setCompareValue(compareValue[i]);
          pressPO.setMotionTime(Integer.parseInt(motionTime[i]));
          pressPO.setPressMotionTime(Integer.parseInt(timeLimit[i]));
          pressPO.setWfActivity(activityPO);
          pressPO.setDomainId(activityPO.getDomainId());
          this.session.save(pressPO);
        }  
      if (formId == null || formId.equals("") || formId.toUpperCase().equals("NULL"))
        formId = "0"; 
      activityPO.setFormId(formId);
      this.session.flush();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void addWithCondition(String[] activityPara, String[] readField, String[] writeField, String[] protectField, String[] condition, String[] operate, String[] compareValue, String[] timeLimit, String[] motionTime, String formId) throws Exception {
    begin();
    try {
      WFWorkFlowProcessPO processPO = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, new Long(activityPara[0]));
      WFActivityPO activityPO = new WFActivityPO();
      activityPO.setWfWorkFlowProcess(processPO);
      activityPO.setActivityName(activityPara[1]);
      activityPO.setActivityDescription(activityPara[2]);
      activityPO.setActivityType(Integer.parseInt(activityPara[3]));
      activityPO.setAllowStandFor(Integer.parseInt(activityPara[4]));
      activityPO.setAllowCancel(Integer.parseInt(activityPara[5]));
      activityPO.setAllowTransition(Integer.parseInt(activityPara[6]));
      activityPO.setAllowSmsRemind(activityPara[45]);
      activityPO.setParticipantType(Integer.parseInt(activityPara[7]));
      activityPO.setParticipantUser(activityPara[8]);
      activityPO.setParticipantUserName(activityPara[9]);
      activityPO.setParticipantUserField(activityPara[10]);
      activityPO.setPressType(Integer.parseInt(activityPara[11]));
      activityPO.setDeadLineTime(Integer.parseInt(activityPara[12]));
      activityPO.setPressMotionTime(Integer.parseInt(activityPara[13]));
      activityPO.setParticipantGroup(activityPara[14]);
      activityPO.setTransactType(activityPara[15]);
      activityPO.setActiCommType(Integer.parseInt(activityPara[16]));
      activityPO.setActiCommField(activityPara[17]);
      activityPO.setNeedPassRound(Integer.parseInt(activityPara[18]));
      activityPO.setPassRoundUserType(Integer.parseInt(activityPara[19]));
      activityPO.setPassRoundUser(activityPara[20]);
      activityPO.setPassRoundUserGroup(activityPara[21]);
      activityPO.setPassRoundUserName(activityPara[22]);
      activityPO.setPassRoundUserField(activityPara[23]);
      activityPO.setPassRoundCommField(activityPara[24]);
      activityPO.setParticipantRole(activityPara[25]);
      activityPO.setPassRoundRole(activityPara[26]);
      if (formId == null || formId.equals("") || formId.toUpperCase().equals("NULL"))
        formId = "0"; 
      activityPO.setFormId(formId);
      activityPO.setActivityClass(Integer.parseInt(activityPara[27]));
      if (Integer.parseInt(activityPara[27]) == 2)
        activityPO.setActivityType(0); 
      activityPO.setActivitySubProc(activityPara[28]);
      activityPO.setSubProcType(Integer.parseInt(activityPara[29]));
      activityPO.setFormClassMethod(activityPara[30]);
      activityPO.setParticipantGivenOrgName(activityPara[31]);
      activityPO.setParticipantGivenOrg(activityPara[32]);
      activityPO.setPassRoundGivenOrgName(activityPara[33]);
      activityPO.setPassRoundGivenOrg(activityPara[34]);
      activityPO.setUntreadMethod(activityPara[35]);
      activityPO.setCommentRange(activityPara[36]);
      activityPO.setCommentRangeName(activityPara[37]);
      activityPO.setOperButton(activityPara[42]);
      activityPO.setTranType(activityPara[46]);
      activityPO.setTranCustomExtent(activityPara[47]);
      activityPO.setTranCustomExtentId(activityPara[48]);
      activityPO.setTranReadType(activityPara[51]);
      activityPO.setTranReadCustomExtent(activityPara[52]);
      activityPO.setTranReadCustomExtentId(activityPara[53]);
      activityPO.setPressDealType(activityPara[49]);
      activityPO.setActiCommFieldType(activityPara[43]);
      activityPO.setPassRoundCommFieldType(activityPara[44]);
      activityPO.setExtendMainTable(activityPara[50]);
      activityPO.setDomainId(processPO.getDomainId());
      this.session.save(activityPO);
      if (readField != null)
        for (int i = 0; i < readField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(0);
          wfrwControlPO.setControlField(new Long(readField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          wfrwControlPO.setDomainId(processPO.getDomainId());
          this.session.save(wfrwControlPO);
        }  
      if (writeField != null)
        for (int i = 0; i < writeField.length; i++) {
          WFReadWriteControlPO wfrwControlPO = new WFReadWriteControlPO();
          wfrwControlPO.setControlType(1);
          wfrwControlPO.setControlField(new Long(writeField[i]));
          wfrwControlPO.setWfActivity(activityPO);
          wfrwControlPO.setDomainId(processPO.getDomainId());
          this.session.save(wfrwControlPO);
        }  
      if (protectField != null)
        for (int i = 0; i < protectField.length; i++) {
          WFProtectControlPO wfptControlPO = new WFProtectControlPO();
          wfptControlPO.setControlType(2);
          wfptControlPO.setControlField(new Long(protectField[i]));
          wfptControlPO.setWfActivity(activityPO);
          wfptControlPO.setDomainId(processPO.getDomainId());
          this.session.save(wfptControlPO);
        }  
      if (condition != null)
        for (int i = 0; i < condition.length; i++) {
          WFPressPO pressPO = new WFPressPO();
          pressPO.setConditionField(new Long(condition[i]));
          pressPO.setPressRelation(operate[i]);
          pressPO.setCompareValue(compareValue[i]);
          pressPO.setMotionTime(Integer.parseInt(motionTime[i]));
          pressPO.setPressMotionTime(Integer.parseInt(timeLimit[i]));
          pressPO.setWfActivity(activityPO);
          pressPO.setDomainId(processPO.getDomainId());
          this.session.save(pressPO);
        }  
      this.session.flush();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void remove(String activityIdString) throws Exception {
    begin();
    try {
      this.session.delete(" from com.js.oa.jsflow.po.WFActivityPO aaa  where aaa.wfActivityId in (" + 
          activityIdString + ")");
      this.session.flush();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void removeAll(String processId) throws Exception {
    begin();
    try {
      Query query = this.session.createQuery(" select aaa from com.js.oa.jsflow.po.WFActivityPO aaa  join aaa.wfWorkFlowProcess bbb  where bbb.wfWorkFlowProcessId = " + 
          
          processId + 
          " and aaa.activityBeginEnd = 0 ");
      List<WFActivityPO> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        WFActivityPO activityPO = list.get(i);
        this.session.delete(activityPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public WFActivityPO getActivityInfo(String activityId) throws Exception {
    WFActivityPO activityPO = null;
    begin();
    try {
      activityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return activityPO;
  }
  
  public List getFromActivity(String activityId) throws Exception {
    List<Object[]> list = null;
    begin();
    try {
      Query query = this.session.createQuery(" select bbb.wfActivityId, bbb.activityName, bbb.activityBeginEnd  from com.js.oa.jsflow.po.WFTransitionPO aaa join  aaa.transitionFrom bbb join aaa.transitionTo ccc where  ccc.wfActivityId = " + 

          
          activityId);
      List<Object[]> tmpList = query.list();
      query = this.session.createQuery(" select bbb.wfActivityId, bbb.activityName, bbb.activityBeginEnd, ddd.conditionField,  ddd.relation, ddd.compareValue from  com.js.oa.jsflow.po.WFTransitionPO aaa join aaa.transitionFrom bbb  join aaa.transitionTo ccc join aaa.wfTransitionRestriction ddd where  ccc.wfActivityId = " + 


          
          activityId);
      list = query.list();
      for (int i = 0; i < tmpList.size(); i++) {
        Object[] obj = tmpList.get(i);
        if (obj[2].toString().equals("1")) {
          Object[] obj2 = { obj[0], obj[1], obj[2], "", "", "" };
          list.add(obj2);
          break;
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getActivity(String processId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.wfActivityId,aaa.activityName,aaa.activityBeginEnd,aaa.activityClass from com.js.oa.jsflow.po.WFActivityPO aaa join aaa.wfWorkFlowProcess bbb where bbb.wfWorkFlowProcessId = " + 
          
          processId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getToActivity(String activityId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(" select bbb.wfActivityId, bbb.activityName, bbb.activityBeginEnd,  ddd.conditionField, ddd.relation, ddd.compareValue,aaa.expression,aaa.defaultActivity from  com.js.oa.jsflow.po.WFTransitionPO aaa join aaa.transitionTo bbb  join aaa.transitionFrom ccc join aaa.wfTransitionRestriction ddd where  ccc.wfActivityId = " + 


          
          activityId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Long setStartActivity(String activityId, String startActivity) throws Exception {
    begin();
    Long transId = null;
    try {
      WFActivityPO fromActivityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(startActivity));
      WFActivityPO toActivityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
      WFTransitionPO transitionPO = new WFTransitionPO();
      transitionPO.setTransitionFrom(fromActivityPO);
      transitionPO.setTransitionTo(toActivityPO);
      transitionPO.setDomainId(fromActivityPO.getDomainId());
      transId = (Long)this.session.save(transitionPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {}
    this.session.close();
    this.session = null;
    this.transaction = null;
    return transId;
  }
  
  public Long setActivity(String[] activityIdValue, String[] conditionValue, String[] operateValue, String[] compareValue, String activityId, String[] expression, String type) throws Exception {
    begin();
    Long transId = null;
    try {
      WFActivityPO activityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
      for (int i = 0; i < activityIdValue.length; i++) {
        if (!activityIdValue[i].equals("")) {
          WFTransitionPO transitionPO = new WFTransitionPO();
          WFActivityPO activityPO2 = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityIdValue[i]));
          if (type.equals("from")) {
            transitionPO.setTransitionFrom(activityPO2);
            transitionPO.setTransitionTo(activityPO);
            transitionPO.setDomainId(activityPO.getDomainId());
            transitionPO.setExpression((expression[i] == null) ? "" : expression[i].replaceAll("'", "''").replaceAll("&", "@@@@"));
            transId = (Long)this.session.save(transitionPO);
          } else {
            transitionPO.setTransitionFrom(activityPO);
            transitionPO.setTransitionTo(activityPO2);
            transitionPO.setDomainId(activityPO.getDomainId());
            transitionPO.setExpression((expression[i] == null) ? "" : expression[i].replaceAll("'", "''").replaceAll("&", "@@@@"));
            transId = (Long)this.session.save(transitionPO);
          } 
          WFTransitionRestrictionPO restrictionPO = new WFTransitionRestrictionPO();
          restrictionPO.setConditionField(new Long(conditionValue[i]));
          restrictionPO.setRelation(operateValue[i]);
          restrictionPO.setCompareValue(compareValue[i]);
          restrictionPO.setWfTransition(transitionPO);
          restrictionPO.setDomainId(activityPO.getDomainId());
          this.session.save(restrictionPO);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    this.session.close();
    this.session = null;
    this.transaction = null;
    return transId;
  }
  
  public Long setActivity(String[] activityIdValue, String[] conditionValue, String[] operateValue, String[] compareValue, String activityId, String[] expression, String type, String defaultActivity) throws Exception {
    begin();
    Long transId = null;
    try {
      WFActivityPO activityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
      for (int i = 0; i < activityIdValue.length; i++) {
        if (!activityIdValue[i].equals("")) {
          WFTransitionPO transitionPO = new WFTransitionPO();
          WFActivityPO activityPO2 = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityIdValue[i]));
          if (type.equals("from")) {
            transitionPO.setTransitionFrom(activityPO2);
            transitionPO.setTransitionTo(activityPO);
            transitionPO.setDomainId(activityPO.getDomainId());
            transitionPO.setExpression((expression[i] == null) ? "" : expression[i].replaceAll("'", "''").replaceAll("&", "@@@@"));
            transId = (Long)this.session.save(transitionPO);
          } else {
            transitionPO.setTransitionFrom(activityPO);
            transitionPO.setTransitionTo(activityPO2);
            transitionPO.setDomainId(activityPO.getDomainId());
            transitionPO.setExpression((expression[i] == null) ? "" : expression[i].replaceAll("'", "''").replaceAll("&", "@@@@"));
            if (defaultActivity.equals(activityPO2.getWfActivityId().toString())) {
              transitionPO.setDefaultActivity(defaultActivity);
            } else {
              transitionPO.setDefaultActivity("0");
            } 
            transId = (Long)this.session.save(transitionPO);
          } 
          WFTransitionRestrictionPO restrictionPO = new WFTransitionRestrictionPO();
          restrictionPO.setConditionField(new Long(conditionValue[i]));
          restrictionPO.setRelation(operateValue[i]);
          restrictionPO.setCompareValue(compareValue[i]);
          restrictionPO.setWfTransition(transitionPO);
          restrictionPO.setDomainId(activityPO.getDomainId());
          this.session.save(restrictionPO);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    this.session.close();
    this.session = null;
    this.transaction = null;
    return transId;
  }
  
  public Long setSingelRelation(String fromActivity, String toActivity, String conditionField, String operate, String compareValue, String expression, String defaultActivity) throws Exception {
    Long transId = null;
    begin();
    try {
      WFTransitionPO transitionPO = new WFTransitionPO();
      WFActivityPO fromActivityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(fromActivity));
      WFActivityPO toActivityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(toActivity));
      transitionPO.setTransitionFrom(fromActivityPO);
      transitionPO.setTransitionTo(toActivityPO);
      transitionPO.setDomainId(toActivityPO.getDomainId());
      transitionPO.setExpression("");
      if ("1".equals(defaultActivity)) {
        transitionPO.setDefaultActivity(toActivity);
      } else {
        transitionPO.setDefaultActivity("0");
      } 
      transId = (Long)this.session.save(transitionPO);
      WFTransitionRestrictionPO restrictionPO = new WFTransitionRestrictionPO();
      restrictionPO.setConditionField(new Long(conditionField));
      restrictionPO.setRelation(operate);
      restrictionPO.setCompareValue(compareValue);
      restrictionPO.setWfTransition(transitionPO);
      restrictionPO.setDomainId(toActivityPO.getDomainId());
      this.session.save(restrictionPO);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } finally {}
    return transId;
  }
  
  public Boolean hasPrintRight(String activityId, String userId, String orgId, String groupId) throws Exception {
    Boolean hasRight = Boolean.FALSE;
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      rs = stmt.executeQuery("select PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON from JSF_ACTIVITY where Wf_ACTIVITY_ID=" + activityId);
      String org = "";
      String group = "";
      String user = "";
      if (rs.next()) {
        org = rs.getString(1);
        group = rs.getString(2);
        user = (new StringBuilder(String.valueOf(rs.getString(3)))).toString();
        rs.close();
        if (group != null && group.trim().length() > 0 && !group.trim().toUpperCase().equals("NULL")) {
          group = group.startsWith("@") ? group.substring(1, group.length()) : "";
          group = group.endsWith("@") ? group.substring(0, group.length() - 1) : "";
          group = (group.indexOf("@@") > 0) ? group.replaceAll("@@", ",") : group;
        } 
        if (org != null && org.trim().length() > 0 && !org.trim().toUpperCase().equals("NULL")) {
          org = org.startsWith("*") ? org.substring(1, org.length()) : "";
          org = org.endsWith("*") ? org.substring(0, org.length() - 1) : "";
          org = (org.indexOf("**") > 0) ? org.replaceAll("**", ",") : org;
        } 
        String[] groupIdArr = group.split(",");
        if (groupIdArr == null)
          groupIdArr = new String[0]; 
        String[] orgIdArr = org.split(",");
        if (orgIdArr == null)
          orgIdArr = new String[0]; 
        DbOpt dbopt = null;
        try {
          dbopt = new DbOpt();
          int i;
          for (i = 0; i < groupIdArr.length; i++) {
            if (groupIdArr[i] != null && groupIdArr[i].trim().length() > 0) {
              String empIdStr = dbopt.executeQueryToStr(
                  "select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + 
                  groupIdArr[i]);
              if (empIdStr != null && empIdStr.length() > 1)
                user = String.valueOf(user) + empIdStr; 
            } 
          } 
          for (i = 0; i < orgIdArr.length; i++) {
            if (orgIdArr[i] != null && orgIdArr[i].trim().length() > 0) {
              String orgCode = dbopt.executeQueryToStr(
                  "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
                  orgIdArr[i]);
              rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
                  orgCode + "%')");
              if (rs != null) {
                while (rs.next()) {
                  Object empId = rs.getObject(1);
                  if (empId != null && 
                    user.indexOf(empId.toString()) < 0)
                    user = String.valueOf(user) + empId.toString() + ","; 
                } 
                rs.close();
              } 
            } 
          } 
          dbopt.close();
        } catch (Exception e) {
          try {
            dbopt.close();
          } catch (SQLException sQLException) {}
          e.printStackTrace();
        } 
        user = "," + user + ",";
        if (user != null && !user.toUpperCase().equals("NULL") && (
          user.indexOf("," + userId + ",") >= 0 || user.indexOf("$" + userId + "$") >= 0))
          hasRight = Boolean.TRUE; 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hasRight;
  }
  
  public List getUserActivityList(String processId, String tableId, String recordId) throws Exception {
    List<String[]> list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select wf_proceedactivity_id,wf_activity_id,activityname,activitytype from jsf_p_activity where activityclass=1 and ttable_id=" + tableId + " and trecord_id=" + recordId);
      while (rs.next()) {
        String[] temp = new String[4];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        list.add(temp);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      throw e;
    } 
    return list;
  }
  
  public String getActivityHandSignType(String activityId) {
    String handSignType = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select handSignType from jsf_activity where wf_activity_id=?");
      pstmt.setString(1, activityId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        handSignType = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return handSignType;
  }
}
