package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.po.WFPackagePO;
import com.js.oa.jsflow.po.WFPressPO;
import com.js.oa.jsflow.po.WFProtectControlPO;
import com.js.oa.jsflow.po.WFReadWriteControlPO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.po.WFWorkFlowWriteControlPO;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.TransformObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WFProcessEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String removeProcess(String ids) throws Exception {
    Connection conn = null;
    String processName = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] idArr = ids.split(",");
      for (int i = 0; i < idArr.length; i++) {
        List<String> tableIdList = new ArrayList();
        ResultSet rs = stmt.executeQuery("select distinct worktable_id from jsf_work where workprocess_id=" + idArr[i]);
        while (rs.next())
          tableIdList.add(rs.getString(1)); 
        rs.close();
        rs = stmt.executeQuery("SELECT WORKFLOWPROCESSNAME FROM jsf_workflowprocess WHERE wf_workflowprocess_id=" + idArr[i]);
        if (rs.next())
          processName = String.valueOf(processName) + rs.getString(1) + ","; 
        rs.close();
        for (int j = 0; j < tableIdList.size(); j++) {
          String tableName = "";
          List<String> subTableList = new ArrayList();
          rs = stmt.executeQuery("select area_table from tarea where area_name='form1' and page_id=" + tableIdList.get(j).toString());
          if (rs.next())
            tableName = rs.getString(1); 
          rs.close();
          System.out.println("select area_table from tarea where area_name<>'form1' and page_id=" + tableIdList.get(j).toString());
          rs = stmt.executeQuery("select area_table from tarea where area_name<>'form1' and page_id=" + tableIdList.get(j).toString());
          while (rs.next())
            subTableList.add(rs.getString(1)); 
          rs.close();
          if (!"".equals(tableName))
            stmt.executeUpdate("delete from " + tableName + " where " + tableName + "_id in (select workrecord_id from jsf_work where workprocess_id=" + idArr[i] + ")"); 
          for (int m = 0; m < subTableList.size(); m++) {
            tableName = subTableList.get(m).toString();
            stmt.executeUpdate("delete from " + tableName + " where " + tableName + "_foreignkey in (select workrecord_id from jsf_work where workprocess_id=" + idArr[i] + ")");
          } 
        } 
        stmt.executeUpdate("delete from jsf_p_draft where process_id=" + idArr[i]);
        stmt.executeUpdate("delete from jsf_p_activity where wf_workflowprocess_id=" + idArr[i]);
        stmt.executeUpdate("delete from jsf_activity where wf_workflowprocess_id=" + idArr[i]);
        stmt.executeUpdate("delete from jsf_work where workprocess_id=" + idArr[i]);
        stmt.executeUpdate("delete from jsf_p_flow where wf_workflowprocess_id=" + idArr[i]);
        stmt.executeUpdate("delete from JSF_WORKFLOWWRITECONTROL where wf_workflowprocess_id=" + idArr[i]);
        stmt.executeUpdate("delete from jsf_workflowprocess where wf_workflowprocess_id=" + idArr[i]);
      } 
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
    return processName;
  }
  
  public Boolean addProcess(WFWorkFlowProcessPO wfWorkFlowProcessPO, String packageId, String[] noWriteField, String mainFormId) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      String processName = wfWorkFlowProcessPO.getWorkFlowProcessName();
      Query query = this.session.createQuery(" select count(*) from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  join aaa.wfPackage bbb  where bbb.wfPackageId = " + 
          
          packageId + " and aaa.workFlowProcessName = '" + 
          processName + "' and aaa.domainId=" + wfWorkFlowProcessPO.getDomainId());
      if (Integer.parseInt(query.list().iterator().next().toString()) > 0) {
        success = Boolean.FALSE;
      } else {
        WFPackagePO wfPackagePO = (WFPackagePO)this.session.load(WFPackagePO.class, new Long(packageId));
        wfWorkFlowProcessPO.setWfPackage(wfPackagePO);
        wfWorkFlowProcessPO.setProcessStatus(0);
        wfWorkFlowProcessPO.setLastUpdateTime(wfWorkFlowProcessPO.getProcessCreatedDate());
        wfWorkFlowProcessPO.setMainFormId(Long.valueOf(mainFormId));
        this.session.save(wfWorkFlowProcessPO);
        WFActivityPO startActivityPO = new WFActivityPO();
        startActivityPO.setActivityBeginEnd(1);
        startActivityPO.setWfWorkFlowProcess(wfWorkFlowProcessPO);
        startActivityPO.setDomainId(wfWorkFlowProcessPO.getDomainId());
        startActivityPO.setFormId(wfWorkFlowProcessPO.getAccessDatabaseId().toString());
        startActivityPO.setMainFormId(new Long(mainFormId));
        this.session.save(startActivityPO);
        WFActivityPO endActivityPO = new WFActivityPO();
        endActivityPO.setActivityBeginEnd(2);
        endActivityPO.setWfWorkFlowProcess(wfWorkFlowProcessPO);
        endActivityPO.setDomainId(wfWorkFlowProcessPO.getDomainId());
        endActivityPO.setFormId(wfWorkFlowProcessPO.getAccessDatabaseId().toString());
        endActivityPO.setMainFormId(new Long(mainFormId));
        this.session.save(endActivityPO);
        if (noWriteField != null)
          for (int i = 0; i < noWriteField.length; i++) {
            if (noWriteField[i] != null && !noWriteField[i].equals("")) {
              WFWorkFlowWriteControlPO wfWriteControlPO = new WFWorkFlowWriteControlPO();
              wfWriteControlPO.setWriteControlField(new Long(noWriteField[i]));
              wfWriteControlPO.setWfWorkFlowProcess(wfWorkFlowProcessPO);
              wfWriteControlPO.setDomainId(wfWorkFlowProcessPO.getDomainId());
              this.session.save(wfWriteControlPO);
            } 
          }  
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public List getNoWriteField(String processId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.writeControlField from  com.js.oa.jsflow.po.WFWorkFlowWriteControlPO aaa join aaa.wfWorkFlowProcess bbb  where bbb.wfWorkFlowProcessId = " + 

          
          processId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getNoDisplayField(String processId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.writeControlField from  com.js.oa.jsflow.po.WFWorkFlowWriteControlPO aaa join aaa.wfWorkFlowProcess bbb  where aaa.controlType=2 and bbb.wfWorkFlowProcessId = " + 

          
          processId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getFirstActivityWriteField(String processId, String moduleId) throws Exception {
    String res = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select accessdatabaseId from jsf_workflowprocess where wf_workflowprocess_id=" + processId);
      rs.next();
      String accessDataBaseId = rs.getString(1);
      rs.close();
      if (!"1".equals(moduleId)) {
        StringBuffer fields = new StringBuffer("-1");
        rs = stmt.executeQuery("select writecontrolfield from jsf_workflowwritecontrol where wf_workflowprocess_id=" + processId);
        while (rs.next())
          fields.append(",").append(rs.getString(1)); 
        rs.close();
        rs = stmt.executeQuery("select immofield_pofield from jsf_immobilityfield where wf_immofield_id not in (" + fields.toString() + ")");
        fields = fields.delete(0, fields.length());
        while (rs.next())
          fields.append("$").append(rs.getString(1)).append("$"); 
        rs.close();
        res = fields.toString();
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return res;
  }
  
  public List getProcInfo(String processId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(" select bbb.wfPackageId, aaa.workFlowProcessName, aaa.userScope, aaa.processType, aaa.accessDatabaseId, aaa.processDescription, aaa.useOrg, aaa.useGroup, aaa.usePerson, aaa.dossierFileSeeScope,aaa.dossierFileSeeOrg, aaa.dossierFileSeeGroup, aaa.dossierFileSeePerson, aaa.remindField, aaa.isDossier, aaa.canCancel, aaa.formClassName, aaa.formClassMethod, aaa.formClassCompMethod, aaa.printFileSeeScope,aaa.printFileSeeOrg, aaa.printFileSeeGroup, aaa.printFileSeePerson,aaa.formType,aaa.startJSP,aaa.optJSP, aaa.dossierFileOperScope,aaa.dossierFileOperOrg,aaa.dossierFileOperGroup, aaa.dossierFileOperPerson, aaa.processAdminScope,aaa.processAdminOrg,aaa.processAdminGroup, aaa.processAdminPerson,aaa.createUserName,aaa.createdEmp,aaa.mainFormId,aaa.infoChannelId,aaa.sendFileType,aaa.startUrl,aaa.startMethod,aaa.startPara,aaa.startNameSpace,aaa.completeUrl,aaa.completeMethod,aaa.completePara,aaa.completeNameSpace,aaa.processDeadline,aaa.processDeadlineType,aaa.isJx,aaa.dossierFileExportScope,aaa.dossierFileExportOrg,aaa.dossierFileExportGroup,aaa.dossierFileExportPerson from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  join aaa.wfPackage bbb where aaa.wfWorkFlowProcessId = " + 


















          
          processId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Boolean updateProcess(String processId, String[] parameter, String[] noWriteField, String mainFormId) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    Connection conn = null;
    Statement stat = null;
    try {
      Query query = this.session.createQuery("select count(aaa.wfWorkFlowProcessId) from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb where bbb.wfPackageId = " + 

          
          parameter[0] + " and aaa.workFlowProcessName = '" + 
          parameter[1] + "' and aaa.wfWorkFlowProcessId <>" + 
          processId + " and aaa.domainId=(select po.domainId from com.js.oa.jsflow.po.WFWorkFlowProcessPO po where po.wfWorkFlowProcessId=" + processId + ")");
      if (query.list().iterator().next().toString().equals("0")) {
        success = Boolean.TRUE;
        WFWorkFlowProcessPO wfWorkFlowProcessPO = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, new Long(processId));
        Long oldTable = wfWorkFlowProcessPO.getAccessDatabaseId();
        if (!(new Long(parameter[7])).equals(oldTable)) {
          if ("1".equals(parameter[26])) {
            conn = (new DataSourceBase()).getDataSource().getConnection();
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select AREA_TABLE from tarea where page_id=" + parameter[7]);
            String tablename = "";
            if (rs != null)
              while (rs.next())
                tablename = rs.getString(1);  
            rs.close();
            query = this.session.createQuery("select aaa.fieldId from com.js.oa.jsflow.po.TFieldPO aaa join aaa.table bbb where bbb.tableName = '" + 

                
                tablename + "'");
          } else {
            query = this.session.createQuery("select aaa.fieldId from com.js.oa.jsflow.po.TFieldPO aaa join aaa.table bbb where bbb.tableId = " + 

                
                parameter[7]);
          } 
          List<E> newField = query.list();
          query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFActivityPO aaa join aaa.wfWorkFlowProcess bbb where bbb.wfWorkFlowProcessId = " + 
              
              processId);
          List<WFActivityPO> activity = query.list();
          query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFReadWriteControlPO aaa join aaa.wfActivity bbb join bbb.wfWorkFlowProcess ccc where ccc.wfWorkFlowProcessId = " + 
              
              processId);
          List<WFReadWriteControlPO> rwControl = query.list();
          int j;
          for (j = 0; j < rwControl.size(); j++) {
            WFReadWriteControlPO wrPO = rwControl.get(j);
            this.session.delete(wrPO);
          } 
          for (j = 0; j < activity.size(); j++) {
            WFActivityPO activityPO = activity.get(j);
            for (int k = 0; k < newField.size(); k++) {
              WFReadWriteControlPO rwContrlPO = new WFReadWriteControlPO();
              rwContrlPO.setControlField(new Long(newField.get(k).toString()));
              rwContrlPO.setControlType(0);
              rwContrlPO.setWfActivity(activityPO);
              rwContrlPO.setDomainId(wfWorkFlowProcessPO.getDomainId());
              this.session.save(rwContrlPO);
            } 
            if (!(new Long(parameter[7])).equals(oldTable)) {
              activityPO.setFormId(parameter[7]);
              this.session.flush();
            } 
            activityPO.setMainFormId(Long.valueOf(mainFormId));
          } 
        } 
        WFPackagePO wfPackagePO = (WFPackagePO)this.session.load(WFPackagePO.class, new Long(parameter[0]));
        wfWorkFlowProcessPO.setWfPackage(wfPackagePO);
        wfWorkFlowProcessPO.setWorkFlowProcessName(parameter[1]);
        wfWorkFlowProcessPO.setUserScope(parameter[2]);
        wfWorkFlowProcessPO.setUseOrg(parameter[3]);
        wfWorkFlowProcessPO.setUseGroup(parameter[4]);
        wfWorkFlowProcessPO.setUsePerson(parameter[5]);
        wfWorkFlowProcessPO.setProcessType(Integer.parseInt(parameter[6]));
        wfWorkFlowProcessPO.setAccessDatabaseId(new Long(parameter[7]));
        wfWorkFlowProcessPO.setProcessDescription(parameter[8]);
        wfWorkFlowProcessPO.setDossierFileSeeScope(parameter[9]);
        wfWorkFlowProcessPO.setDossierFileSeeOrg(parameter[10]);
        wfWorkFlowProcessPO.setDossierFileSeeGroup(parameter[11]);
        wfWorkFlowProcessPO.setDossierFileSeePerson(parameter[12]);
        wfWorkFlowProcessPO.setDossierFileExportScope(parameter[50]);
        wfWorkFlowProcessPO.setDossierFileExportOrg(parameter[51]);
        wfWorkFlowProcessPO.setDossierFileExportGroup(parameter[52]);
        wfWorkFlowProcessPO.setDossierFileExportPerson(parameter[53]);
        wfWorkFlowProcessPO.setRemindField(parameter[13]);
        wfWorkFlowProcessPO.setIsDossier((parameter.length >= 15) ? Integer.valueOf(parameter[14]) : Integer.valueOf("0"));
        wfWorkFlowProcessPO.setCanCancel((parameter.length >= 16) ? Integer.valueOf(parameter[15]) : Integer.valueOf("0"));
        wfWorkFlowProcessPO.setFormClassName(parameter[16]);
        wfWorkFlowProcessPO.setFormClassMethod(parameter[17]);
        wfWorkFlowProcessPO.setFormClassCompMethod(parameter[18]);
        wfWorkFlowProcessPO.setPrintFileSeeScope(parameter[19]);
        wfWorkFlowProcessPO.setPrintFileSeeOrg(parameter[20]);
        wfWorkFlowProcessPO.setPrintFileSeeGroup(parameter[21]);
        wfWorkFlowProcessPO.setPrintFileSeePerson(parameter[22]);
        wfWorkFlowProcessPO.setFormType(Integer.parseInt(parameter[23]));
        wfWorkFlowProcessPO.setStartJSP(parameter[24]);
        wfWorkFlowProcessPO.setOptJSP(parameter[25]);
        wfWorkFlowProcessPO.setDossierFileOperScope(parameter[27]);
        wfWorkFlowProcessPO.setDossierFileOperOrg(parameter[28]);
        wfWorkFlowProcessPO.setDossierFileOperGroup(parameter[29]);
        wfWorkFlowProcessPO.setDossierFileOperPerson(parameter[30]);
        wfWorkFlowProcessPO.setProcessAdminScope(parameter[31]);
        wfWorkFlowProcessPO.setProcessAdminOrg(parameter[32]);
        wfWorkFlowProcessPO.setProcessAdminGroup(parameter[33]);
        wfWorkFlowProcessPO.setProcessAdminPerson(parameter[34]);
        wfWorkFlowProcessPO.setCreateUserName(parameter[35]);
        wfWorkFlowProcessPO.setCreatedEmp(Long.valueOf(parameter[36]));
        wfWorkFlowProcessPO.setInfoChannelId(parameter[37]);
        wfWorkFlowProcessPO.setSendFileType(Integer.valueOf(parameter[38]).intValue());
        if (parameter.length > 39) {
          wfWorkFlowProcessPO.setStartUrl(parameter[39]);
          wfWorkFlowProcessPO.setStartMethod(parameter[40]);
          wfWorkFlowProcessPO.setStartPara(parameter[41]);
          wfWorkFlowProcessPO.setStartNameSpace(parameter[42]);
          wfWorkFlowProcessPO.setCompleteUrl(parameter[43]);
          wfWorkFlowProcessPO.setCompleteMethod(parameter[44]);
          wfWorkFlowProcessPO.setCompletePara(parameter[45]);
          wfWorkFlowProcessPO.setCompleteNameSpace(parameter[46]);
        } 
        wfWorkFlowProcessPO.setProcessDeadline(parameter[47]);
        wfWorkFlowProcessPO.setProcessDeadlineType(parameter[48]);
        wfWorkFlowProcessPO.setIsJx(parameter[49]);
        query = this.session.createQuery("select bbb.orgId from com.js.system.vo.usermanager.EmployeeVO aaa join aaa.organizations bbb where aaa.empId=" + parameter[36]);
        if (query != null && query.iterate().hasNext()) {
          wfWorkFlowProcessPO.setCreatedOrg(Long.valueOf(query.iterate().next().toString()));
        } else {
          wfWorkFlowProcessPO.setCreatedOrg(Long.valueOf(0L));
        } 
        wfWorkFlowProcessPO.setMainFormId(Long.valueOf(mainFormId));
        query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFWorkFlowWriteControlPO aaa join aaa.wfWorkFlowProcess bbb where bbb.wfWorkFlowProcessId = " + 
            processId);
        List<WFWorkFlowWriteControlPO> list = query.list();
        int i;
        for (i = 0; i < list.size(); i++) {
          WFWorkFlowWriteControlPO wfwritePO = list.get(i);
          this.session.delete(wfwritePO);
        } 
        if (noWriteField != null)
          for (i = 0; i < noWriteField.length; i++) {
            if (noWriteField[i] != null && !"".equals(noWriteField[i])) {
              WFWorkFlowWriteControlPO wfwritePO = new WFWorkFlowWriteControlPO();
              wfwritePO.setWriteControlField(new Long(noWriteField[i]));
              wfwritePO.setWfWorkFlowProcess(wfWorkFlowProcessPO);
              wfwritePO.setDomainId(wfWorkFlowProcessPO.getDomainId());
              this.session.save(wfwritePO);
            } 
          }  
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (stat != null)
        stat.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return success;
  }
  
  public ActivityVO getFirstActivity(String processId) throws Exception {
    begin();
    ActivityVO firstActivity = new ActivityVO();
    try {
      Query query = this.session.createQuery(" select aaa.wfActivityId from com.js.oa.jsflow.po.WFActivityPO aaa  join aaa.wfWorkFlowProcess bbb where bbb.wfWorkFlowProcessId = " + 
          processId + 
          " and aaa.activityBeginEnd = 1");
      String startActivityId = query.iterate().next().toString();
      query = this.session.createQuery("select aaa.transitionTo from com.js.oa.jsflow.po.WFTransitionPO aaa join aaa.transitionFrom bbb where bbb.wfActivityId=" + 
          startActivityId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        WFActivityPO activityPO = query.iterate().next();
        firstActivity.setId(activityPO.getWfActivityId().longValue());
        firstActivity.setName(activityPO.getActivityName());
        firstActivity.setActivityClass(activityPO.getActivityClass());
        firstActivity.setBeginEnd(activityPO.getActivityBeginEnd());
        firstActivity.setIsBranch(activityPO.getIsBranch());
        firstActivity.setIsGather(activityPO.getIsGather());
        firstActivity.setIsDivide(activityPO.getIsDivide());
        if (activityPO.getParticipantGivenOrg() != null && 
          !activityPO.getParticipantGivenOrg().equals("") && 
          !activityPO.getParticipantGivenOrg().toUpperCase().equals("NULL"))
          firstActivity.setParticipantGivenOrg(activityPO.getParticipantGivenOrg()); 
        if (activityPO.getPassRoundGivenOrg() != null && 
          !activityPO.getPassRoundGivenOrg().equals("") && 
          !activityPO.getPassRoundGivenOrg().toUpperCase().equals("NULL"))
          firstActivity.setPassRoundGivenOrg(activityPO.getPassRoundGivenOrg()); 
        int participantType = activityPO.getParticipantType();
        firstActivity.setParticipantType(participantType);
        if (participantType == 2 || participantType == 3) {
          List<String[]> participantList = new ArrayList();
          String userString = activityPO.getParticipantUser();
          String userNameString = activityPO.getParticipantUserName();
          if (userString.indexOf("$") >= 0) {
            if (userString.endsWith(","))
              userNameString = userNameString.substring(0, userNameString.length() - 1); 
            userString = "$" + userString + "$";
            userString = userString.replace('$', ',');
            userString = userString.replaceAll(",,", ",");
            userString = userString.substring(1, userString.length() - 1);
            String[] userArr = userString.split(",");
            String[] userNameArr = userNameString.split(",");
            for (int m = 0; m < userArr.length; m++) {
              participantList.add(new String[] { userArr[m], userNameArr[m] });
            } 
          } 
          String groupString = activityPO.getParticipantGroup();
          if (groupString == null)
            groupString = "-100"; 
          if (groupString.indexOf("@") >= 0) {
            groupString = "@" + groupString + "@";
            groupString = groupString.replace('@', ',');
            groupString = groupString.replaceAll(",,", ",");
            groupString = groupString.substring(1, groupString.length() - 1);
            StringBuffer tmpBuffer = new StringBuffer("select aaa.empId, aaa.empName from com.js.system.vo.usermanager.EmployeeVO aaa join aaa.organizations bbb ");
            tmpBuffer.append(" where ");
            if (!groupString.equals("")) {
              tmpBuffer.append("aaa.empId in (select eee.empId from com.js.system.vo.usermanager.EmployeeVO eee join eee.groups fff where fff.groupId in (" + groupString + "))");
            } else {
              tmpBuffer.append(" 1<>1 ");
            } 
            tmpBuffer.append(" order by bbb.orgIdString, aaa.userOrderCode");
            query = this.session.createQuery(tmpBuffer.toString());
            List<String[]> tmpList = query.list();
            for (int m = 0; m < tmpList.size(); m++)
              participantList.add(tmpList.get(m)); 
          } 
          firstActivity.setParticipantUser(participantList);
        } else {
          firstActivity.setParticipantUser(new ArrayList());
        } 
        firstActivity.setParticipantUserField(activityPO.getParticipantUserField());
        if (activityPO.getNeedPassRound() == 1) {
          firstActivity.setNeedPassRound(Boolean.TRUE);
        } else {
          firstActivity.setNeedPassRound(Boolean.FALSE);
        } 
        int passRoundUserType = activityPO.getPassRoundUserType();
        firstActivity.setPassRoundUserType(passRoundUserType);
        if (passRoundUserType == 2 || passRoundUserType == 3) {
          String userString = activityPO.getPassRoundUser();
          String groupString = activityPO.getPassRoundUserGroup();
          if (userString == null || userString.equals(""))
            userString = "-100"; 
          if (groupString == null || groupString.equals(""))
            groupString = "-100"; 
          if (userString.indexOf("$") >= 0) {
            userString = "$" + userString + "$";
            userString = userString.replace('$', ',');
            userString = userString.replaceAll(",,", ",");
            userString = userString.substring(1, userString.length() - 1);
          } 
          if (groupString.indexOf("@") >= 0) {
            groupString = "@" + groupString + "@";
            groupString = groupString.replace('@', ',');
            groupString = groupString.replaceAll(",,", ",");
            groupString = groupString.substring(1, groupString.length() - 1);
          } 
          query = this.session.createQuery("select aaa.empId, aaa.empName from com.js.system.vo.usermanager.EmployeeVO aaa join aaa.organizations bbb where aaa.empId in (" + 
              userString + ") or aaa.empId in (" + 
              "select eee.empId from com.js.system.vo.usermanager.EmployeeVO eee join eee.groups fff " + 
              "where fff.groupId in (" + groupString + "))  order by bbb.orgIdString, aaa.userOrderCode");
          List tmpList = query.list();
          firstActivity.setPassRoundUser(tmpList);
        } else {
          firstActivity.setPassRoundUser(new ArrayList());
        } 
        firstActivity.setPassRoundUserField(activityPO.getPassRoundUserField());
        firstActivity.setAllowcancel(activityPO.getAllowCancel());
        firstActivity.setAllowStandFor(activityPO.getAllowStandFor());
        firstActivity.setPressType(activityPO.getPressType());
        firstActivity.setDeadlineTime(activityPO.getDeadLineTime());
        firstActivity.setPressMotionTime(activityPO.getPressMotionTime());
        String partRole = "";
        String roleName = "", orgName = "", roleId = "", orgId = "";
        partRole = activityPO.getParticipantRole();
        if (activityPO.getParticipantRole() != null && !activityPO.getParticipantRole().equals("") && activityPO.getParticipantRole().indexOf("and") != -1)
          if (partRole.indexOf("and") > 0) {
            roleId = partRole.split("and")[0];
            orgId = partRole.split("and")[1];
            query = this.session.createQuery("select aaa.roleName from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId=" + roleId);
            Iterator<E> roleIter = query.iterate();
            if (roleIter.hasNext())
              roleName = roleIter.next().toString(); 
            if (orgId.equals("-1")) {
              orgName = "发起人的组织";
            } else if (orgId.equals("-2")) {
              orgName = "发起人的组织及上级组织";
            } else if (orgId.equals("-3")) {
              orgName = "发起人的组织及下级组织";
            } else if (Long.parseLong(orgId) > 0L) {
              query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + orgId);
              roleIter = query.iterate();
              if (roleIter.hasNext())
                orgName = roleIter.next().toString(); 
            } 
          } else {
            roleId = partRole;
            query = this.session.createQuery("select aaa.roleName from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId=" + roleId);
            Iterator<E> roleIter = query.iterate();
            if (roleIter.hasNext())
              roleName = roleIter.next().toString(); 
          }  
        firstActivity.setPartRole(partRole);
        if (orgName.equals("")) {
          firstActivity.setPartRoleName(roleName);
        } else {
          firstActivity.setPartRoleName(String.valueOf(orgName) + "-" + roleName);
        } 
        String passRole = "";
        passRole = activityPO.getPassRoundRole();
        if (activityPO.getPassRoundRole() != null && !activityPO.getPassRoundRole().equals("") && activityPO.getPassRoundRole().indexOf("and") != -1)
          if (passRole.indexOf("and") > 0) {
            roleId = passRole.split("and")[0];
            orgId = passRole.split("and")[1];
            query = this.session.createQuery("select aaa.roleName from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId=" + roleId);
            Iterator<E> roleIter = query.iterate();
            if (roleIter.hasNext())
              roleName = roleIter.next().toString(); 
            if (orgId.equals("-1")) {
              orgName = "发起人的组织";
            } else if (orgId.equals("-2")) {
              orgName = "发起人的组织及上级组织";
            } else if (orgId.equals("-3")) {
              orgName = "发起人的组织及下级组织";
            } else if (Long.parseLong(orgId) > 0L) {
              query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + orgId);
              roleIter = query.iterate();
              if (roleIter.hasNext())
                roleName = roleIter.next().toString(); 
            } 
          } else {
            roleId = passRole;
            query = this.session.createQuery("select aaa.roleName from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId=" + roleId);
            Iterator<E> roleIter = query.iterate();
            if (roleIter.hasNext())
              roleName = roleIter.next().toString(); 
          }  
        firstActivity.setPassRole(passRole);
        firstActivity.setPassRoleName(String.valueOf(orgName.equals("") ? "" : (String.valueOf(orgName) + "-")) + roleName);
        firstActivity.setTransactType(activityPO.getTransactType());
        firstActivity.setAllowSmsRemind(activityPO.getAllowSmsRemind());
        firstActivity.setPressDealType(activityPO.getPressDealType());
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return firstActivity;
  }
  
  public String getProcWhereSql(String userId, String orgIdString, String type) throws Exception {
    begin();
    String whereSql = "";
    try {
      String tmpSql2 = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql2 = " select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa where  '" + 
          
          orgIdString + "' like concat('%$', aaa.orgId, '$%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql2 = " select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),'" + 
          
          orgIdString + "')>0";
      } else {
        tmpSql2 = " select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa where  '" + 
          
          orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%')";
      } 
      Query query = this.session.createQuery(tmpSql2);
      String orgStr = "useOrg";
      if (type.equals("dossier"))
        orgStr = "dossierFileSeeOrg"; 
      if (type.equals("dossieroper"))
        orgStr = "dossierFileOperOrg"; 
      if (type.equals("dossieradmin"))
        orgStr = "processAdminOrg"; 
      if (type.equals("dossierexport"))
        orgStr = "dossierFileExportOrg"; 
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++)
        whereSql = String.valueOf(whereSql) + " aaa." + orgStr + " like  '%*" + list.get(i) + "*%' or "; 
      whereSql = String.valueOf(whereSql) + " aaa." + orgStr + " like '%*-1*%' or ";
      query = this.session.createQuery("select aaa.sidelineOrg from com.js.system.vo.usermanager.EmployeeVO aaa where aaa.empId=" + Long.valueOf(userId));
      list = query.list();
      String sidelineOrg = (list == null || list.size() == 0 || list.get(0) == null || "".equals(list.get(0))) ? "-1" : list.get(0).toString();
      String[] sidelineOrgArr = sidelineOrg.split("\\*\\*");
      for (int j = 0; j < sidelineOrgArr.length; j++) {
        sidelineOrgArr[j] = sidelineOrgArr[j].replaceAll("\\*", "");
        whereSql = String.valueOf(whereSql) + " aaa." + orgStr + " like  '%*" + sidelineOrgArr[j] + "*%' or ";
      } 
      query = this.session.createQuery(" select aaa.groupId from  com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb  where bbb.empId = " + 
          
          Long.valueOf(userId));
      String groupStr = "useGroup";
      if (type.equals("dossier"))
        groupStr = "dossierFileSeeGroup"; 
      if (type.equals("dossieroper"))
        groupStr = "dossierFileOperGroup"; 
      if (type.equals("dossieradmin"))
        groupStr = "processAdminGroup"; 
      if (type.equals("dossierexport"))
        groupStr = "dossierFileExportGroup"; 
      list = query.list();
      for (int k = 0; k < list.size(); k++)
        whereSql = String.valueOf(whereSql) + " aaa." + groupStr + " like '%@" + list.get(k) + "@%' or "; 
      String personStr = "usePerson";
      if (type.equals("dossier"))
        personStr = "dossierFileSeePerson"; 
      if (type.equals("dossieroper"))
        personStr = "dossierFileOperPerson"; 
      if (type.equals("dossieradmin"))
        personStr = "processAdminPerson"; 
      if (type.equals("dossierexport"))
        personStr = "dossierFileExportPerson"; 
      whereSql = String.valueOf(whereSql) + " aaa." + personStr + " like '%$" + Long.valueOf(userId) + "$%' ";
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return whereSql;
  }
  
  public List getUserProcess(String userId, String orgIdString, String moduleId) throws Exception {
    List list = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String whereSql = getProcWhereSql(userId, orgIdString, "new");
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP  from com.js.oa.jsflow.po.WFWorkFlowProcessPO  aaa join aaa.wfPackage bbb where (" + 

        
        whereSql + ") " + 
        " and bbb.moduleId = " + moduleId + " and aaa.isPublish=1";
      hSql = String.valueOf(hSql) + " and aaa.processStatus=1";
      if (databaseType.indexOf("mysql") >= 0) {
        hSql = String.valueOf(hSql) + " order by bbb.orderCode,bbb.wfPackageId desc,convert(aaa.workFlowProcessName using gbk ),aaa.wfWorkFlowProcessId ";
      } else {
        hSql = String.valueOf(hSql) + " order by bbb.orderCode,bbb.wfPackageId desc,aaa.workFlowProcessName,aaa.wfWorkFlowProcessId ";
      } 
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getUserProcessList(String userId, String orgIdString, String moduleId, String workflowContent) throws Exception {
    List list = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String whereSql = getProcWhereSql(userId, orgIdString, "new");
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP  from com.js.oa.jsflow.po.WFWorkFlowProcessPO  aaa join aaa.wfPackage bbb where (" + 

        
        whereSql + ") " + 
        " and bbb.moduleId = " + moduleId + " and aaa.isPublish=1";
      hSql = String.valueOf(hSql) + " and aaa.processStatus=1";
      if (!"".equals(workflowContent) && workflowContent != null)
        hSql = String.valueOf(hSql) + " and (aaa.workFlowProcessName like '%" + workflowContent + "%' or aaa.processDescription like '%" + workflowContent + "%') "; 
      if (databaseType.indexOf("mysql") >= 0) {
        hSql = String.valueOf(hSql) + " order by bbb.orderCode,bbb.wfPackageId desc,convert(aaa.workFlowProcessName using gbk ),aaa.wfWorkFlowProcessId ";
      } else {
        hSql = String.valueOf(hSql) + " order by bbb.orderCode,bbb.wfPackageId desc,aaa.workFlowProcessName,aaa.wfWorkFlowProcessId ";
      } 
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllUserProcess(String moduleId) throws Exception {
    List list = null;
    try {
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP  from com.js.oa.jsflow.po.WFWorkFlowProcessPO  aaa join aaa.wfPackage bbb where bbb.moduleId = " + 

        
        moduleId + " and aaa.isPublish=1 order by bbb.orderCode,bbb.wfPackageId desc,aaa.wfWorkFlowProcessId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String userCanCreateFlow(String processId, String userId, String orgId, String orgIdString) throws Exception {
    String ret = "0";
    try {
      String whereSql = getProcWhereSql(userId, orgIdString, "new");
      String hSql = " select count(aaa.wfWorkFlowProcessId) from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa where (" + whereSql + ") " + 
        " and aaa.isPublish=1 and aaa.wfWorkFlowProcessId=" + Long.valueOf(processId);
      begin();
      List<E> list = this.session.createQuery(hSql).list();
      if (list != null && list.size() > 0)
        ret = list.get(0).toString(); 
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      e.printStackTrace();
      throw e;
    } finally {}
    return ret;
  }
  
  public List getUserDossProc(String userId, String orgIdString, String moduleId) throws Exception {
    List list = null;
    try {
      String whereSql = getProcWhereSql(userId, orgIdString, "dossier");
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb where (" + 
        whereSql + ") " + 
        " and bbb.moduleId = " + moduleId + " order by  bbb.orderCode,bbb.wfPackageId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllDossProc(String moduleId) throws Exception {
    List list = null;
    try {
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb  where bbb.moduleId = " + 
        
        moduleId + " order by  bbb.orderCode,bbb.wfPackageId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllProcess(String domainId) throws Exception {
    List list = null;
    try {
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb   where aaa.domainId = " + 
        
        domainId + " order by  bbb.orderCode,bbb.wfPackageId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllProcessByModule(String moduleId, String domainId) throws Exception {
    List list = null;
    try {
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,aaa.remindField,aaa.formType,aaa.startJSP from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb   where bbb.moduleId = " + 
        
        moduleId + " and aaa.domainId = " + domainId + " order by  bbb.orderCode,bbb.wfPackageId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getUserDossOperProc(String userId, String orgIdString, String moduleId) throws Exception {
    List list = null;
    try {
      String whereSql = getProcWhereSql(userId, orgIdString, "dossieroper");
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb where (" + 
        whereSql + ") " + 
        " and bbb.moduleId = " + moduleId + " order by bbb.orderCode,bbb.wfPackageId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getUserDossViewOperAdminProc(String userId, String orgIdString, String moduleId) throws Exception {
    List list = null;
    try {
      String whereSqlView = getProcWhereSql(userId, orgIdString, "dossier");
      String whereSqlOper = getProcWhereSql(userId, orgIdString, "dossieroper");
      String whereSqlAdmin = getProcWhereSql(userId, orgIdString, "dossieradmin");
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb where (" + 
        whereSqlView + ") or (" + whereSqlOper + ") or (" + whereSqlAdmin + ") " + 
        " and bbb.moduleId = " + moduleId + " order by  bbb.orderCode,bbb.wfPackageId desc, aaa.workFlowProcessName,aaa.wfWorkFlowProcessId ";
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean getUserDossExport(String userId, String orgIdString, String processId) throws Exception {
    Boolean hasRight = Boolean.valueOf(false);
    Boolean isRightOwner = Boolean.valueOf((new ManagerService()).hasRight(userId, "02*03*04"));
    List list = null;
    if (isRightOwner.booleanValue()) {
      try {
        String whereSqlView = getProcWhereSql(userId, orgIdString, "dossierexport");
        String hSql = "SELECT aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa WHERE aaa.wfWorkFlowProcessId = " + 
          
          processId + " AND (" + whereSqlView + ")" + 
          " ORDER BY aaa.workFlowProcessName, aaa.wfWorkFlowProcessId";
        begin();
        Query query = this.session.createQuery(hSql);
        list = query.list();
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally {
        this.session.close();
      } 
      if (list.size() > 0)
        hasRight = Boolean.valueOf(true); 
    } 
    return hasRight;
  }
  
  public String getRemindField(String processId) throws Exception {
    begin();
    String remindField = "";
    try {
      Iterator iter = this.session.iterate(" select aaa.remindField from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  where aaa.wfWorkFlowProcessId = " + 
          
          processId);
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null && !obj.equals(""))
          remindField = obj.toString(); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return remindField;
  }
  
  public String getNoWriteField(String processId, String processType) throws Exception {
    StringBuffer noWrite = new StringBuffer();
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.writeControlField from  com.js.oa.jsflow.po.WFWorkFlowWriteControlPO aaa join aaa.wfWorkFlowProcess bbb  where bbb.wfWorkFlowProcessId = " + 

          
          processId);
      Iterator<String> iter = query.iterate();
      String tmp = "";
      while (iter.hasNext())
        tmp = String.valueOf(tmp) + iter.next() + ","; 
      if (!tmp.equals("")) {
        tmp = tmp.substring(0, tmp.length() - 1);
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select immofield_pofield from jsf_immobilityfield where wf_immofield_id in (" + tmp + ")");
        while (rs.next())
          noWrite.append("$" + rs.getString(1) + "$"); 
        rs.close();
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return noWrite.toString();
  }
  
  public Integer getCanCancel(String processId) throws Exception {
    Integer canCancel = Integer.valueOf("0");
    begin();
    try {
      Iterator iter = this.session.iterate("select po.canCancel from com.js.oa.jsflow.po.WFWorkFlowProcessPO po where po.wfWorkFlowProcessId = " + processId);
      Object obj = null;
      if (iter.hasNext()) {
        obj = iter.next();
        canCancel = (obj == null) ? Integer.valueOf("0") : (Integer)obj;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return canCancel;
  }
  
  public Integer getIsDossier(String processId) throws Exception {
    Integer isDossier = Integer.valueOf("0");
    begin();
    try {
      Iterator iter = this.session.iterate("select po.isDossier from com.js.oa.jsflow.po.WFWorkFlowProcessPO po where po.wfWorkFlowProcessId = " + processId);
      Object obj = null;
      if (iter.hasNext()) {
        obj = iter.next();
        isDossier = (obj == null) ? Integer.valueOf("0") : (Integer)obj;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return isDossier;
  }
  
  public Boolean copyProcess(String processId) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    try {
      WFWorkFlowProcessPO wfWorkFlowProcessPO = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, new Long(processId));
      TransformObject tf = TransformObject.getInstance();
      WFWorkFlowProcessPO po = new WFWorkFlowProcessPO();
      tf.transformValue(wfWorkFlowProcessPO, po);
      po.setWorkFlowProcessName("复制流程_" + po.getWorkFlowProcessName());
      po.setWfWorkFlowWriteControl(null);
      po.setWfActivity(null);
      this.session.save(po);
      this.session.flush();
      Set<WFActivityPO> set = wfWorkFlowProcessPO.getWfActivity();
      Iterator<WFActivityPO> iter = set.iterator();
      while (iter.hasNext()) {
        WFActivityPO posrc = iter.next();
        WFActivityPO podes = new WFActivityPO();
        tf.transformValue(posrc, podes);
        podes.setWfWorkFlowProcess(po);
        podes.setWfReadWriteControl(null);
        podes.setWfProtectControl(null);
        podes.setWfPress(null);
        podes.setTransitionFrom(null);
        podes.setTransitionTo(null);
        this.session.save(podes);
        this.session.flush();
        Set<WFReadWriteControlPO> set2 = posrc.getWfReadWriteControl();
        Iterator<WFReadWriteControlPO> iter2 = set2.iterator();
        while (iter2.hasNext()) {
          WFReadWriteControlPO posrc2 = iter2.next();
          WFReadWriteControlPO podes2 = new WFReadWriteControlPO();
          tf.transformValue(posrc2, podes2);
          podes2.setWfActivity(podes);
          this.session.save(podes2);
          this.session.flush();
        } 
        set2 = posrc.getWfProtectControl();
        iter2 = set2.iterator();
        while (iter2.hasNext()) {
          WFProtectControlPO posrc2 = (WFProtectControlPO)iter2.next();
          WFProtectControlPO podes2 = new WFProtectControlPO();
          tf.transformValue(posrc2, podes2);
          podes2.setWfActivity(podes);
          this.session.save(podes2);
          this.session.flush();
        } 
        set2 = posrc.getWfPress();
        iter2 = set2.iterator();
        while (iter2.hasNext()) {
          WFPressPO posrc2 = (WFPressPO)iter2.next();
          WFPressPO podes2 = new WFPressPO();
          tf.transformValue(posrc2, podes2);
          podes2.setWfActivity(podes);
          this.session.save(podes2);
          this.session.flush();
        } 
      } 
      set = wfWorkFlowProcessPO.getWfWorkFlowWriteControl();
      iter = set.iterator();
      while (iter.hasNext()) {
        WFWorkFlowWriteControlPO posrc = (WFWorkFlowWriteControlPO)iter.next();
        WFWorkFlowWriteControlPO podes = new WFWorkFlowWriteControlPO();
        tf.transformValue(posrc, podes);
        podes.setWfWorkFlowProcess(po);
        this.session.save(podes);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return success;
  }
  
  public String getDescription(String processId) throws Exception {
    String description = "";
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.processDescription from  com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  where aaa.wfWorkFlowProcessId = " + 
          
          processId);
      Object obj = query.list().iterator().next();
      if (obj != null)
        description = obj.toString(); 
    } catch (Exception e) {
      throw e;
    } finally {}
    this.session.close();
    return description;
  }
  
  public String updateFirstActivity(String processId, String activityId, String activityName, String nowriteField, String domainId) throws Exception {
    try {
      return updateFirstActivity(processId, activityId, activityName, nowriteField, "", domainId);
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public String updateFirstActivity(String processId, String activityId, String activityName, String nowriteField, String noDispalyField, String domainId) throws Exception {
    String result = "1";
    begin();
    try {
      WFActivityPO activityPO = (WFActivityPO)this.session.load(WFActivityPO.class, new Long(activityId));
      activityPO.setActivityName(activityName);
      Query query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFWorkFlowWriteControlPO aaa join aaa.wfWorkFlowProcess bbb where bbb.wfWorkFlowProcessId = " + 
          processId);
      List<WFWorkFlowWriteControlPO> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        WFWorkFlowWriteControlPO wfwritePO = list.get(i);
        this.session.delete(wfwritePO);
      } 
      if (!"".equals(nowriteField)) {
        WFWorkFlowProcessPO wfWorkFlowProcessPO = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, new Long(processId));
        String[] nowriteFields = nowriteField.split(",");
        for (int j = 0; j < nowriteFields.length; j++) {
          if (nowriteFields[j] != null && !"".equals(nowriteFields[j])) {
            WFWorkFlowWriteControlPO wfwritePO = new WFWorkFlowWriteControlPO();
            wfwritePO.setWriteControlField(new Long(nowriteFields[j]));
            wfwritePO.setWfWorkFlowProcess(wfWorkFlowProcessPO);
            wfwritePO.setDomainId(domainId);
            wfwritePO.setControlType("0");
            this.session.save(wfwritePO);
          } 
        } 
      } 
      if (!"".equals(noDispalyField)) {
        WFWorkFlowProcessPO wfWorkFlowProcessPO = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, new Long(processId));
        String[] noDisplayFields = noDispalyField.split(",");
        for (int j = 0; j < noDisplayFields.length; j++) {
          if (noDisplayFields[j] != null && !"".equals(noDisplayFields[j])) {
            WFWorkFlowWriteControlPO wfwritePO = new WFWorkFlowWriteControlPO();
            wfwritePO.setWriteControlField(new Long(noDisplayFields[j]));
            wfwritePO.setWfWorkFlowProcess(wfWorkFlowProcessPO);
            wfwritePO.setDomainId(domainId);
            wfwritePO.setControlType("2");
            this.session.save(wfwritePO);
          } 
        } 
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception e) {
      result = "-1";
      this.session.close();
      throw e;
    } 
    return result;
  }
  
  public Boolean nameIsDuplicate(String processId, String processName, String processClass) throws Exception {
    Boolean success = Boolean.FALSE;
    try {
      Query query = null;
      begin();
      if (!"".equals(processId)) {
        query = this.session.createQuery(" select count(*) from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  join aaa.wfPackage bbb  where bbb.wfPackageId = " + 
            
            processClass + " and aaa.workFlowProcessName = '" + 
            processName + "' and aaa.wfWorkFlowProcessId<>" + processId);
      } else {
        query = this.session.createQuery(" select count(*) from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  join aaa.wfPackage bbb  where bbb.wfPackageId = " + 
            
            processClass + " and aaa.workFlowProcessName = '" + 
            processName + "'");
      } 
      if (Integer.parseInt(query.list().iterator().next().toString()) > 0)
        success = Boolean.TRUE; 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      ex.printStackTrace();
    } 
    return success;
  }
  
  public String setProcessOnDeskTop(String userId, String processId, String type) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      if ("0".equals(type)) {
        stmt.executeUpdate("delete from jsf_topflow_user where user_id=" + userId + " and flow_id=" + processId);
      } else {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("oracle") >= 0) {
          stmt.executeUpdate("insert into jsf_topflow_user(id,user_id,flow_id) values(hibernate_sequence.nextval," + userId + "," + processId + ")");
        } else {
          stmt.executeUpdate("insert into jsf_topflow_user(user_id,flow_id) values(" + userId + "," + processId + ")");
        } 
      } 
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
  
  public String getDeskTopFlowId(String userId) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select flow_id from jsf_topflow_user where user_id=" + userId + " and flow_id in(select wf_workflowprocess_id  from jsf_workflowprocess)");
      while (rs.next())
        res.append(rs.getString(1)).append(","); 
      rs.close();
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
  
  public List getUserOftenProcess(String userId, String orgIdString, String moduleId) throws Exception {
    List list = null;
    try {
      String hSql, whereSql = getProcWhereSql(userId, orgIdString, "new");
      String desktopIds = getDeskTopFlowId(userId);
      if (",".equals(desktopIds)) {
        hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP  from com.js.oa.jsflow.po.WFWorkFlowProcessPO  aaa join aaa.wfPackage bbb where (" + 

          
          whereSql + ") " + 
          " and bbb.moduleId = " + moduleId + " and aaa.processStatus=1 and aaa.isPublish=1 order by bbb.orderCode,bbb.wfPackageId desc,aaa.wfWorkFlowProcessId ";
      } else {
        hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP  from com.js.oa.jsflow.po.WFWorkFlowProcessPO  aaa join aaa.wfPackage bbb where (" + 

          
          whereSql + ") " + 
          " and bbb.moduleId = " + moduleId + " and aaa.processStatus=1 and aaa.wfWorkFlowProcessId in (-1" + desktopIds + "-1) and aaa.isPublish=1 order by bbb.orderCode,bbb.wfPackageId desc,aaa.wfWorkFlowProcessId ";
      } 
      begin();
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void endFlowInstance(String tableId, String recordId) throws Exception {
    Connection conn = null;
    Date now = new Date();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      if (databaseType.indexOf("mysql") >= 0) {
        stmt.executeUpdate("update JSDB.JSF_WORK set worktitle=concat(worktitle,'(终止)'),workstatus=100 where worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and workstartflag=1");
        stmt.executeUpdate("update JSDB.JSF_WORK set worktitle=concat(worktitle,'(终止)'),workstatus=101 where worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and workstatus=0");
      } else if (databaseType.indexOf("oracle") >= 0) {
        stmt.executeUpdate("update JSDB.JSF_WORK set worktitle=worktitle || '(终止)', workstatus=100 where worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and workstartflag=1");
        stmt.executeUpdate("update JSDB.JSF_WORK set worktitle=worktitle || '(终止)',workstatus=101 where worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and workstatus=0");
      } else {
        stmt.executeUpdate("update JSDB.JSF_WORK set worktitle=worktitle+'(终止)',workstatus=100 where worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and workstartflag=1");
        stmt.executeUpdate("update JSDB.JSF_WORK set worktitle=worktitle+'(终止)',workstatus=101 where worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and workstatus=0");
      } 
      stmt.executeUpdate("update JSDB.JSF_DEALWITH set curActivityStatus=1 where databasetable_id=" + tableId + " and databaserecord_id=" + recordId);
      if (databaseType.indexOf("mysql") >= 0) {
        stmt.executeUpdate("UPDATE JSDB.JSF_WORK SET WORKACTIVITY=0, workDoneWithDate='" + now.toLocaleString() + "' WHERE WORKRECORD_ID=" + recordId + " AND WORKTABLE_ID=" + tableId);
      } else {
        stmt.executeUpdate("UPDATE JSDB.JSF_WORK SET WORKACTIVITY=0, workDoneWithDate=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "','') WHERE WORKRECORD_ID=" + recordId + " AND WORKTABLE_ID=" + tableId);
      } 
      stmt.executeUpdate("delete from sys_messages where message_type='jsflow' and data_id in(select wf_work_id from jsf_work WHERE WORKRECORD_ID=" + recordId + " AND WORKTABLE_ID=" + tableId + ")");
      stmt.executeUpdate("delete from ms_infoflow where dataId in(select wf_work_id from jsf_work WHERE WORKRECORD_ID=" + recordId + " AND WORKTABLE_ID=" + tableId + ")");
      (new FlowEndFollowUp()).flowEnd(recordId);
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
  }
  
  public void hangupInstance(String processId, String flag, String tableId, String recordId) throws Exception {
    Connection conn = null;
    Date now = new Date();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      if (databaseType.indexOf("mysql") >= 0) {
        String sql = "update JSF_WORK set work_hangup='" + flag + "' where workprocess_id=" + processId + "  and worktable_id=" + tableId + " and " + "workrecord_id=" + recordId + " and  (WORKSTATUS=0 or WORKSTATUS=1)";
        stmt.executeUpdate(sql);
      } else if (databaseType.indexOf("oracle") >= 0) {
        stmt.executeUpdate("update JSDB.JSF_WORK set work_hangup='" + flag + "' where workprocess_id=" + processId + "  and worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and (WORKSTATUS=0 or WORKSTATUS=1)");
      } else {
        stmt.executeUpdate("update JSDB.JSF_WORK set work_hangup='" + flag + "' where workprocess_id=" + processId + "  and worktable_id=" + tableId + " and " + 
            "workrecord_id=" + recordId + " and (WORKSTATUS=0 or WORKSTATUS=1) ");
      } 
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
  }
  
  public String changeProcessStatus(String processId, String flag) {
    Connection conn = null;
    String processName = "";
    String databaseType = SystemCommon.getDatabaseType();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select workflowprocessname from jsf_workflowprocess where wf_workflowprocess_id=" + processId);
      if (rs.next())
        processName = rs.getString(1); 
      rs.close();
      stmt.executeUpdate("update jsf_workflowprocess set process_status=" + flag + " where wf_workflowprocess_id=" + processId);
      if ("1".equals(flag)) {
        if (databaseType.indexOf("mysql") >= 0)
          stmt.executeUpdate("update jsf_workflowprocess set processUseTime=now() where wf_workflowprocess_id=" + processId); 
        if (databaseType.indexOf("oracle") >= 0)
          stmt.executeUpdate("update jsf_workflowprocess set processUseTime=sysdate where wf_workflowprocess_id=" + processId); 
      } 
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
    return processName;
  }
  
  public String copyFlow(String flows, String domainId, String userId, String orgId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    DbOpt dbopt = new DbOpt();
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      String[] formArr = flows.split(",");
      if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        String flowId = "";
        String sql = "";
        for (int i = 0; i < formArr.length; i++) {
          sql = "INSERT INTO JSF_WORKFLOWPROCESS (ACCESSDATABASEID,WORKFLOWPROCESSNAME,PROCESSCREATEDDATE,PROCESSDESCRIPTION,CREATEUSERNAME,CREATEDORG,CREATEDEMP,USERSCOPE,USEORG,USEGROUP,USEPERSON,DOSSIERFILESEESCOPE,DOSSIERFILESEEORG,DOSSIERFILESEEGROUP,DOSSIERFILESEEPERSON,WF_PACKAGE_ID,PROCESSTYPE,REMINDFIELD,ISPUBLISH,ISDOSSIER,CANCANCEL,FORMCLASSNAME,FORMCLASSMETHOD,FORMCLASSCOMPMETHOD,DOMAIN_ID,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,FORMTYPE,STARTJSP,OPTJSP,DOSSIERFILEOPERSCOPE,DOSSIERFILEOPERORG,DOSSIERFILEOPERGROUP,DOSSIERFILEOPERPERSON,processAdminScope,processAdminOrg,processAdminGroup,processAdminPerson,ordercode,process_status,ownerName,ownerId,ownerOrgId,lastUpdateTime,processUseTime,main_formid,infoChannelId,sendFileType)  SELECT ACCESSDATABASEID,concat('复件',WORKFLOWPROCESSNAME),PROCESSCREATEDDATE,PROCESSDESCRIPTION,CREATEUSERNAME,CREATEDORG,CREATEDEMP,USERSCOPE,USEORG,USEGROUP,USEPERSON,DOSSIERFILESEESCOPE,DOSSIERFILESEEORG,DOSSIERFILESEEGROUP,DOSSIERFILESEEPERSON,WF_PACKAGE_ID,PROCESSTYPE,REMINDFIELD,ISPUBLISH,ISDOSSIER,CANCANCEL,FORMCLASSNAME,FORMCLASSMETHOD,FORMCLASSCOMPMETHOD,DOMAIN_ID,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,FORMTYPE,STARTJSP,OPTJSP,DOSSIERFILEOPERSCOPE,DOSSIERFILEOPERORG,DOSSIERFILEOPERGROUP,DOSSIERFILEOPERPERSON,processAdminScope,processAdminOrg,processAdminGroup,processAdminPerson,ordercode,process_status,ownerName,ownerId,ownerOrgId,lastUpdateTime,processUseTime,main_formid,infoChannelId,sendFileType  FROM JSF_WORKFLOWPROCESS where WF_WORKFLOWPROCESS_ID=" + 
            
            formArr[i];
          stmt.executeUpdate(sql);
          sql = "select max(WF_WORKFLOWPROCESS_ID) from JSF_WORKFLOWPROCESS";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            flowId = rs.getString(1); 
          List<String> activityFields = new ArrayList();
          sql = "SELECT WF_ACTIVITY_ID FROM jsf_activity WHERE WF_WORKFLOWPROCESS_ID = " + formArr[i];
          rs = stmt.executeQuery(sql);
          while (rs.next())
            activityFields.add(rs.getString(1)); 
          List<String> oldList = new ArrayList();
          List<String> newList = new ArrayList();
          sql = "INSERT INTO jsf_activity (ACTIVITYNAME,ACTIVITYDESCRIPTION,ACTIVITYTYPE,ALLOWSTANDFOR,ALLOWCANCEL,ALLOWTRANSITION,PARTICIPANTTYPE,PARTICIPANTUSER,PARTICIPANTGROUP,PARTICIPANTUSERNAME,PARTICIPANTUSERFIELD,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ACTIVITYDOCUMENTATION,ACTIVITYICON,ACTIVITYBEGINEND,WF_WORKFLOWPROCESS_ID,TRANSACTTYPE,ACTICOMMTYPE,ACTICOMMFIELD,NEEDPASSROUND,PASSROUNDUSERTYPE,PASSROUNDUSER,PASSROUNDUSERGROUP,PASSROUNDUSERNAME,PASSROUNDUSERFIELD,PASSROUNDCOMMFIELD,PARTICIPANTROLE,PASSROUNDROLE,ACTIVITYCLASS,ACTIVITYSUBPROC,SUBPROCTYPE,FORMCLASSMETHOD,UNTREADMETHOD,PARTICIPANTGIVENORGNAME,PARTICIPANTGIVENORG,PASSROUNDGIVENORGNAME,PASSROUNDGIVENORG,COMMENTRANGE,COMMENTRANGENAME,DOMAIN_ID,FORM_ID,PRINTNUM,OPERBUTTON,actiCommFieldType,passRoundCommFieldType,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId,pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,extendMainTable,positionleft,positiontop,nicknum,main_formid,exe_script)  select ACTIVITYNAME,ACTIVITYDESCRIPTION,ACTIVITYTYPE,ALLOWSTANDFOR,ALLOWCANCEL,ALLOWTRANSITION,PARTICIPANTTYPE,PARTICIPANTUSER,PARTICIPANTGROUP,PARTICIPANTUSERNAME,PARTICIPANTUSERFIELD,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ACTIVITYDOCUMENTATION,ACTIVITYICON,ACTIVITYBEGINEND,?,TRANSACTTYPE,ACTICOMMTYPE,ACTICOMMFIELD,NEEDPASSROUND,PASSROUNDUSERTYPE,PASSROUNDUSER,PASSROUNDUSERGROUP,PASSROUNDUSERNAME,PASSROUNDUSERFIELD,PASSROUNDCOMMFIELD,PARTICIPANTROLE,PASSROUNDROLE,ACTIVITYCLASS,ACTIVITYSUBPROC,SUBPROCTYPE,FORMCLASSMETHOD,UNTREADMETHOD,PARTICIPANTGIVENORGNAME,PARTICIPANTGIVENORG,PASSROUNDGIVENORGNAME,PASSROUNDGIVENORG,COMMENTRANGE,COMMENTRANGENAME,DOMAIN_ID,FORM_ID,PRINTNUM,OPERBUTTON,actiCommFieldType,passRoundCommFieldType,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId,pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,extendMainTable,positionleft,positiontop,nicknum,main_formid,exe_script from jsf_activity where WF_ACTIVITY_ID=?";
          PreparedStatement prst2 = conn.prepareStatement(sql);
          for (int j = 0; j < activityFields.size(); j++) {
            prst2.setString(1, flowId);
            prst2.setString(2, activityFields.get(j).toString());
            prst2.executeUpdate();
            sql = "select max(WF_ACTIVITY_ID) from jsf_activity";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
              oldList.add(activityFields.get(j).toString());
              newList.add(rs.getString(1));
            } 
          } 
          prst2.close();
          List<String> list = new ArrayList();
          List<String> tList = new ArrayList();
          for (int k = 0; k < activityFields.size(); k++) {
            sql = "SELECT WF_READWRITECONTROL_ID,WF_ACTIVITY_ID FROM JSF_READWRITECONTROL WHERE controltype=1 and WF_ACTIVITY_ID =" + activityFields.get(k);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
              tList.add(rs.getString(1));
              list.add(getFieldId(oldList, newList, rs.getString(2)));
            } 
          } 
          if (tList.size() > 0) {
            sql = "INSERT INTO JSF_READWRITECONTROL (CONTROLTYPE,CONTROLFIELD,WF_ACTIVITY_ID,DOMAIN_ID) select CONTROLTYPE,CONTROLFIELD,?,DOMAIN_ID from JSF_READWRITECONTROL where WF_READWRITECONTROL_ID=?";
            PreparedStatement prst = conn.prepareStatement(sql);
            for (int f = 0; f < tList.size(); f++) {
              prst.setString(1, list.get(f).toString());
              prst.setString(2, tList.get(f).toString());
              prst.executeUpdate();
            } 
            prst.close();
          } 
          list.clear();
          String actString = "-1";
          for (int y = 0; y < activityFields.size(); y++)
            actString = String.valueOf(actString) + "," + activityFields.get(y); 
          sql = "SELECT WF_TRANSITION_ID,TRANSITIONFROM,TRANSITIONTO,defaultActivity FROM jsf_transition WHERE TRANSITIONFROM in (" + actString + ")";
          rs = stmt.executeQuery(sql);
          List<String> tranFields = new ArrayList();
          while (rs.next()) {
            tranFields.add(rs.getString(1));
            String[] field = { "", "", "" };
            field[0] = getFieldId(oldList, newList, rs.getString(2));
            field[1] = getFieldId(oldList, newList, rs.getString(3));
            if (rs.getString(4) == null) {
              field[2] = null;
            } else {
              field[2] = getFieldId(oldList, newList, rs.getString(4));
            } 
            list.add((String)field);
          } 
          List<String> trOldList = new ArrayList();
          List<String> trNewList = new ArrayList();
          sql = "INSERT INTO jsf_transition (TRANSITIONFROM,TRANSITIONNAME,TRANSITIONTO,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,defaultActivity,linetype,nicknum)  select ?,TRANSITIONNAME,?,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,defaultActivity,linetype,nicknum from jsf_transition where WF_TRANSITION_ID=?";
          PreparedStatement prst3 = conn.prepareStatement(sql);
          sql = "INSERT INTO jsf_transition (TRANSITIONFROM,TRANSITIONNAME,TRANSITIONTO,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,defaultActivity,linetype,nicknum)  select ?,TRANSITIONNAME,?,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,?,linetype,nicknum from jsf_transition where WF_TRANSITION_ID=?";
          PreparedStatement prst4 = conn.prepareStatement(sql);
          for (int t = 0; t < tranFields.size(); t++) {
            String[] fStrings = (String[])list.get(t);
            if (fStrings[2] == null || "".equals(fStrings[2]) || "null".equals(fStrings[2])) {
              prst3.setString(1, fStrings[0]);
              prst3.setString(2, fStrings[1]);
              prst3.setString(3, tranFields.get(t).toString());
              prst3.executeUpdate();
            } else {
              prst4.setString(1, fStrings[0]);
              prst4.setString(2, fStrings[1]);
              prst4.setString(3, fStrings[2]);
              prst4.setString(4, tranFields.get(t).toString());
              prst4.executeUpdate();
            } 
            sql = "select max(WF_TRANSITION_ID) from jsf_transition";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
              trOldList.add(tranFields.get(t).toString());
              trNewList.add(rs.getString(1));
            } 
          } 
          prst3.close();
          prst4.close();
          String trans = "-1";
          for (int x = 0; x < tranFields.size(); x++)
            trans = String.valueOf(trans) + "," + tranFields.get(x); 
          list.clear();
          List<String> tranString = new ArrayList();
          sql = "SELECT WF_TRANSITIONRESTRICTION_ID,WF_TRANSITION_ID FROM jsf_transitionrestriction WHERE wf_transition_id in (" + trans + ")";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            tranString.add(rs.getString(1));
            String tranField = getFieldId(trOldList, trNewList, rs.getString(2));
            list.add(tranField);
          } 
          sql = "INSERT INTO jsf_transitionrestriction (CONDITIONFIELD,COMPAREVALUE,RELATION,WF_TRANSITION_ID,DOMAIN_ID) select CONDITIONFIELD,COMPAREVALUE,RELATION,?,DOMAIN_ID FROM jsf_transitionrestriction where WF_TRANSITIONRESTRICTION_ID = ?";
          PreparedStatement prst1 = conn.prepareStatement(sql);
          for (int s = 0; s < tranString.size(); s++) {
            prst1.setString(1, list.get(s).toString());
            prst1.setString(2, tranString.get(s).toString());
            prst1.executeUpdate();
          } 
          prst1.close();
        } 
      } else if (DbOpt.dbtype.indexOf("oracle") >= 0) {
        String flowId = "";
        String sql = "";
        for (int i = 0; i < formArr.length; i++) {
          sql = "select hibernate_sequence.nextval from dual";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            flowId = (new StringBuilder(String.valueOf(rs.getLong(1)))).toString(); 
          sql = "INSERT INTO JSF_WORKFLOWPROCESS (WF_WORKFLOWPROCESS_ID,ACCESSDATABASEID,WORKFLOWPROCESSNAME,PROCESSCREATEDDATE,PROCESSDESCRIPTION,CREATEUSERNAME,CREATEDORG,CREATEDEMP,USERSCOPE,USEORG,USEGROUP,USEPERSON,DOSSIERFILESEESCOPE,DOSSIERFILESEEORG,DOSSIERFILESEEGROUP,DOSSIERFILESEEPERSON,WF_PACKAGE_ID,PROCESSTYPE,REMINDFIELD,ISPUBLISH,ISDOSSIER,CANCANCEL,FORMCLASSNAME,FORMCLASSMETHOD,FORMCLASSCOMPMETHOD,DOMAIN_ID,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,FORMTYPE,STARTJSP,OPTJSP,DOSSIERFILEOPERSCOPE,DOSSIERFILEOPERORG,DOSSIERFILEOPERGROUP,DOSSIERFILEOPERPERSON,processAdminScope,processAdminOrg,processAdminGroup,processAdminPerson,ordercode,process_status,ownerName,ownerId,ownerOrgId,lastUpdateTime,processUseTime,main_formid,infoChannelId,sendFileType)  SELECT " + 
            flowId + ",ACCESSDATABASEID,concat('复件',WORKFLOWPROCESSNAME),PROCESSCREATEDDATE,PROCESSDESCRIPTION,CREATEUSERNAME,CREATEDORG,CREATEDEMP,USERSCOPE,USEORG,USEGROUP,USEPERSON,DOSSIERFILESEESCOPE,DOSSIERFILESEEORG,DOSSIERFILESEEGROUP,DOSSIERFILESEEPERSON,WF_PACKAGE_ID,PROCESSTYPE,REMINDFIELD,ISPUBLISH,ISDOSSIER,CANCANCEL,FORMCLASSNAME,FORMCLASSMETHOD,FORMCLASSCOMPMETHOD,DOMAIN_ID,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,FORMTYPE,STARTJSP,OPTJSP,DOSSIERFILEOPERSCOPE,DOSSIERFILEOPERORG,DOSSIERFILEOPERGROUP,DOSSIERFILEOPERPERSON,processAdminScope,processAdminOrg,processAdminGroup,processAdminPerson,ordercode,process_status,ownerName,ownerId,ownerOrgId,lastUpdateTime,processUseTime,main_formid,infoChannelId,sendFileType " + 
            " FROM JSF_WORKFLOWPROCESS where WF_WORKFLOWPROCESS_ID=" + formArr[i];
          stmt.executeUpdate(sql);
          List<String> activityFields = new ArrayList();
          sql = "SELECT WF_ACTIVITY_ID FROM jsf_activity WHERE WF_WORKFLOWPROCESS_ID = " + formArr[i];
          rs = stmt.executeQuery(sql);
          while (rs.next())
            activityFields.add(rs.getString(1)); 
          List<String> oldList = new ArrayList();
          List<String> newList = new ArrayList();
          sql = "INSERT INTO jsf_activity (WF_ACTIVITY_ID,ACTIVITYNAME,ACTIVITYDESCRIPTION,ACTIVITYTYPE,ALLOWSTANDFOR,ALLOWCANCEL,ALLOWTRANSITION,PARTICIPANTTYPE,PARTICIPANTUSER,PARTICIPANTGROUP,PARTICIPANTUSERNAME,PARTICIPANTUSERFIELD,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ACTIVITYDOCUMENTATION,ACTIVITYICON,ACTIVITYBEGINEND,WF_WORKFLOWPROCESS_ID,TRANSACTTYPE,ACTICOMMTYPE,ACTICOMMFIELD,NEEDPASSROUND,PASSROUNDUSERTYPE,PASSROUNDUSER,PASSROUNDUSERGROUP,PASSROUNDUSERNAME,PASSROUNDUSERFIELD,PASSROUNDCOMMFIELD,PARTICIPANTROLE,PASSROUNDROLE,ACTIVITYCLASS,ACTIVITYSUBPROC,SUBPROCTYPE,FORMCLASSMETHOD,PARTICIPANTGIVENORGNAME,PARTICIPANTGIVENORG,PASSROUNDGIVENORGNAME,PASSROUNDGIVENORG,UNTREADMETHOD,COMMENTRANGE,COMMENTRANGENAME,DOMAIN_ID,FORM_ID,OPERBUTTON,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,ACTICOMMFIELDTYPE,PASSROUNDCOMMFIELDTYPE,ALLOWSMSREMIND,TRANTYPE,TRANCUSTOMEXTENT,TRANCUSTOMEXTENTID,PRESSDEALTYPE,EXTENDMAINTABLE,TRANREADTYPE,TRANREADCUSTOMEXTENT,TRANREADCUSTOMEXTENTID,POSITIONLEFT,POSITIONTOP,NICKNUM,MAIN_FORMID,EXE_SCRIPT)  select ?,ACTIVITYNAME,ACTIVITYDESCRIPTION,ACTIVITYTYPE,ALLOWSTANDFOR,ALLOWCANCEL,ALLOWTRANSITION,PARTICIPANTTYPE,PARTICIPANTUSER,PARTICIPANTGROUP,PARTICIPANTUSERNAME,PARTICIPANTUSERFIELD,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ACTIVITYDOCUMENTATION,ACTIVITYICON,ACTIVITYBEGINEND,?,TRANSACTTYPE,ACTICOMMTYPE,ACTICOMMFIELD,NEEDPASSROUND,PASSROUNDUSERTYPE,PASSROUNDUSER,PASSROUNDUSERGROUP,PASSROUNDUSERNAME,PASSROUNDUSERFIELD,PASSROUNDCOMMFIELD,PARTICIPANTROLE,PASSROUNDROLE,ACTIVITYCLASS,ACTIVITYSUBPROC,SUBPROCTYPE,FORMCLASSMETHOD,PARTICIPANTGIVENORGNAME,PARTICIPANTGIVENORG,PASSROUNDGIVENORGNAME,PASSROUNDGIVENORG,UNTREADMETHOD,COMMENTRANGE,COMMENTRANGENAME,DOMAIN_ID,FORM_ID,OPERBUTTON,PRINTFILESEESCOPE,PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON,ACTICOMMFIELDTYPE,PASSROUNDCOMMFIELDTYPE,ALLOWSMSREMIND,TRANTYPE,TRANCUSTOMEXTENT,TRANCUSTOMEXTENTID,PRESSDEALTYPE,EXTENDMAINTABLE,TRANREADTYPE,TRANREADCUSTOMEXTENT,TRANREADCUSTOMEXTENTID,POSITIONLEFT,POSITIONTOP,NICKNUM,MAIN_FORMID,EXE_SCRIPT from jsf_activity where WF_ACTIVITY_ID=?";
          PreparedStatement pstmt0 = conn.prepareStatement(sql);
          for (int j = 0; j < activityFields.size(); j++) {
            String id0 = "";
            sql = "select hibernate_sequence.nextval from dual";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
              oldList.add(activityFields.get(j).toString());
              newList.add((new StringBuilder(String.valueOf(rs.getLong(1)))).toString());
              id0 = (new StringBuilder(String.valueOf(rs.getLong(1)))).toString();
            } 
            pstmt0.setString(1, id0);
            pstmt0.setString(2, flowId);
            pstmt0.setString(3, activityFields.get(j).toString());
            pstmt0.executeUpdate();
          } 
          pstmt0.close();
          List<String> list = new ArrayList();
          List<String> oList = new ArrayList();
          sql = "SELECT WF_READWRITECONTROL_ID,WF_ACTIVITY_ID FROM JSF_READWRITECONTROL WHERE WF_ACTIVITY_ID =? and controltype=1";
          PreparedStatement pstmt1 = conn.prepareStatement(sql);
          for (int k = 0; k < activityFields.size(); k++) {
            pstmt1.setString(1, activityFields.get(k).toString());
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
              oList.add(rs1.getString(1));
              list.add(getFieldId(oldList, newList, rs1.getString(2)));
            } 
            rs1.close();
          } 
          pstmt1.close();
          if (oList.size() > 0) {
            sql = "INSERT INTO JSF_READWRITECONTROL (WF_READWRITECONTROL_ID,CONTROLTYPE,CONTROLFIELD,WF_ACTIVITY_ID,DOMAIN_ID) select hibernate_sequence.nextval,CONTROLTYPE,CONTROLFIELD,?,DOMAIN_ID from JSF_READWRITECONTROL where WF_READWRITECONTROL_ID=?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql);
            for (int f = 0; f < oList.size(); f++) {
              pstmt2.setString(1, list.get(f).toString());
              pstmt2.setString(2, oList.get(f).toString());
              pstmt2.executeUpdate();
            } 
            pstmt2.close();
          } 
          List<String> tranFields = new ArrayList();
          String tranString = "-1";
          for (int x = 0; x < activityFields.size(); x++)
            tranString = String.valueOf(tranString) + "," + activityFields.get(x).toString(); 
          list.clear();
          sql = "SELECT WF_TRANSITION_ID,TRANSITIONFROM,TRANSITIONTO,defaultActivity FROM jsf_transition WHERE TRANSITIONFROM in (" + tranString + ")";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            tranFields.add(rs.getString(1));
            String[] field = { "", "", "" };
            field[0] = getFieldId(oldList, newList, rs.getString(2));
            field[1] = getFieldId(oldList, newList, rs.getString(3));
            if (rs.getString(4) == null) {
              field[2] = null;
            } else {
              field[2] = getFieldId(oldList, newList, rs.getString(4));
            } 
            list.add((String)field);
          } 
          List<String> trOldList = new ArrayList();
          List<String> trNewList = new ArrayList();
          sql = "INSERT INTO jsf_transition (WF_TRANSITION_ID,TRANSITIONFROM,TRANSITIONNAME,TRANSITIONTO,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,defaultActivity,linetype,nicknum)  select ?,?,TRANSITIONNAME,?,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,defaultActivity,linetype,nicknum from jsf_transition where WF_TRANSITION_ID=?";
          PreparedStatement pstmt3 = conn.prepareStatement(sql);
          sql = "INSERT INTO jsf_transition (WF_TRANSITION_ID,TRANSITIONFROM,TRANSITIONNAME,TRANSITIONTO,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,defaultActivity,linetype,nicknum)  select ?,?,TRANSITIONNAME,?,TRANSITIONDESCRIPTION,DOMAIN_ID,EXPRESSION,?,linetype,nicknum from jsf_transition where WF_TRANSITION_ID=?";
          PreparedStatement pstmt4 = conn.prepareStatement(sql);
          for (int t = 0; t < tranFields.size(); t++) {
            String[] fStrings = (String[])list.get(t);
            String id1 = "";
            sql = "select hibernate_sequence.nextval from dual";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
              trOldList.add(tranFields.get(t).toString());
              trNewList.add((new StringBuilder(String.valueOf(rs.getLong(1)))).toString());
              id1 = (new StringBuilder(String.valueOf(rs.getLong(1)))).toString();
            } 
            if (fStrings[2] == null || "".equals(fStrings[2]) || "null".equals(fStrings[2])) {
              pstmt3.setString(1, id1);
              pstmt3.setString(2, fStrings[0]);
              pstmt3.setString(3, fStrings[1]);
              pstmt3.setString(4, tranFields.get(t).toString());
              pstmt3.executeUpdate();
            } else {
              pstmt4.setString(1, id1);
              pstmt4.setString(2, fStrings[0]);
              pstmt4.setString(3, fStrings[1]);
              pstmt4.setString(4, fStrings[2]);
              pstmt4.setString(5, tranFields.get(t).toString());
              pstmt4.executeUpdate();
            } 
          } 
          pstmt3.close();
          pstmt4.close();
          String trans = "-1";
          for (int y = 0; y < tranFields.size(); y++)
            trans = String.valueOf(trans) + "," + tranFields.get(y); 
          list.clear();
          List<String> tranList = new ArrayList();
          sql = "SELECT WF_TRANSITIONRESTRICTION_ID,WF_TRANSITION_ID FROM jsf_transitionrestriction WHERE wf_transition_id in (" + trans + ")";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            tranList.add(rs.getString(1));
            String tranField = getFieldId(trOldList, trNewList, rs.getString(2));
            list.add(tranField);
          } 
          sql = "INSERT INTO jsf_transitionrestriction (WF_TRANSITIONRESTRICTION_ID,CONDITIONFIELD,COMPAREVALUE,RELATION,WF_TRANSITION_ID,DOMAIN_ID) select hibernate_sequence.nextval,CONDITIONFIELD,COMPAREVALUE,RELATION,?,DOMAIN_ID FROM jsf_transitionrestriction where WF_TRANSITIONRESTRICTION_ID = ?";
          PreparedStatement pstmt5 = conn.prepareStatement(sql);
          for (int s = 0; s < tranList.size(); s++) {
            pstmt5.setString(1, list.get(s).toString());
            pstmt5.setString(2, tranList.get(s).toString());
            pstmt5.executeUpdate();
          } 
          pstmt5.close();
        } 
      } 
      if (rs != null)
        rs.close(); 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      ex.printStackTrace();
    } 
    return "";
  }
  
  public String getFieldId(List oldList, List<E> newList, String field) {
    for (int i = 0; i < oldList.size(); i++) {
      if (field.equals(oldList.get(i)))
        return newList.get(i).toString(); 
    } 
    return field;
  }
  
  public Boolean checkProcessByPackage(String packagesId) throws Exception {
    Boolean b = Boolean.valueOf(false);
    List alist = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.wfWorkFlowProcessId from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa  join aaa.wfPackage bbb where bbb.wfPackageId in (" + 
          packagesId + ")");
      alist = query.list();
      if (alist.size() > 0) {
        b = Boolean.valueOf(true);
      } else {
        b = Boolean.valueOf(false);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.flush();
      this.session.close();
    } 
    return b;
  }
  
  public String[] getProcessDeadline(String processId) {
    String[] deadline = { "0", "2" };
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select processDeadline,processDeadlineType from jsf_workflowprocess where wf_workflowprocess_id=" + processId);
      if (rs.next()) {
        deadline[0] = rs.getString(1);
        deadline[1] = rs.getString(2);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return deadline;
  }
}
