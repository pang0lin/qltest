package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFPackagePO;
import com.js.oa.jsflow.po.WFProtectControlPO;
import com.js.oa.jsflow.po.WFReadWriteControlPO;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.SimpleFieldVO;
import com.js.oa.jsflow.vo.TFieldVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.MessagesBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WorkFlowEJBBean extends DataSourceBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public ModuleVO getModule(Integer moduleId) throws Exception {
    begin();
    ModuleVO moduleVO = new ModuleVO();
    String sql = "select module_name, module_formType, module_singlePackage, module_singleProc, module_changeProcType, module_procType, module_chanTranType, module_tranType, module_chanRemind, module_remind, module_chanActiType, module_actiType, module_packRight, module_packRightType, module_procRight, module_procRightType, module_chanNoWrite, module_noWrite, module_actiCommType,module_chanActiClass,module_actiClass, formClassName, newFormMethod, activityFormMethod,completeMethod from JSDB.jsf_needFlowModule where wf_module_id = " + 



      
      moduleId;
    ResultSet rs = this.stmt.executeQuery(sql);
    try {
      if (rs.next()) {
        moduleVO.setId(moduleId.intValue());
        moduleVO.setName(rs.getString("module_name"));
        moduleVO.setFormType(rs.getInt("module_formType"));
        if (rs.getInt("module_singlePackage") == 1) {
          moduleVO.setSinglePackage(true);
        } else {
          moduleVO.setSinglePackage(false);
        } 
        if (rs.getInt("module_singleProc") == 1) {
          moduleVO.setSingleProc(true);
        } else {
          moduleVO.setSingleProc(false);
        } 
        if (rs.getInt("module_changeProcType") == 1) {
          moduleVO.setChangeProcType(true);
        } else {
          moduleVO.setChangeProcType(false);
        } 
        moduleVO.setProcType(rs.getInt("module_procType"));
        if (rs.getInt("module_chanTranType") == 1) {
          moduleVO.setChanTranType(true);
        } else {
          moduleVO.setChanTranType(false);
        } 
        moduleVO.setTranType(rs.getInt("module_tranType"));
        if (rs.getInt("module_chanRemind") == 1) {
          moduleVO.setChanRemind(true);
        } else {
          moduleVO.setChanRemind(false);
        } 
        moduleVO.setRemind(rs.getString("module_remind"));
        if (rs.getInt("module_chanActiType") == 1) {
          moduleVO.setChanActiType(true);
        } else {
          moduleVO.setChanActiType(false);
        } 
        moduleVO.setActiType(rs.getInt("module_actiType"));
        if (rs.getInt("module_packRight") == 1) {
          moduleVO.setPackRight(true);
        } else {
          moduleVO.setPackRight(false);
        } 
        moduleVO.setPackRightType(rs.getString("module_packRightType"));
        if (rs.getInt("module_procRight") == 1) {
          moduleVO.setProcRight(true);
        } else {
          moduleVO.setProcRight(false);
        } 
        if (rs.getInt("module_chanNoWrite") == 1) {
          moduleVO.setChanNoWrite(true);
        } else {
          moduleVO.setChanNoWrite(false);
        } 
        moduleVO.setNoWrite(rs.getString("module_noWrite"));
        moduleVO.setProcRightType(rs.getString("module_procRightType"));
        moduleVO.setActiCommType(rs.getInt("module_actiCommType"));
        if (rs.getInt("module_chanActiClass") == 1) {
          moduleVO.setChanActiClass(true);
        } else {
          moduleVO.setChanActiClass(false);
        } 
        moduleVO.setActiClass(rs.getInt("module_actiClass"));
        moduleVO.setFormClassName(rs.getString("formClassName"));
        moduleVO.setNewFormMethod(rs.getString("newFormMethod"));
        moduleVO.setActivityFormMethod(rs.getString("activityFormMethod"));
        moduleVO.setCompleteMethod(rs.getString("completeMethod"));
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return moduleVO;
  }
  
  public List getAccessTable(ModuleVO moduleVO) throws Exception {
    begin();
    ArrayList<AccessTableVO> alist = new ArrayList();
    int moduleId = moduleVO.getId();
    int formType = moduleVO.getFormType();
    ResultSet rs = null;
    try {
      if (formType == 0) {
        rs = this.stmt.executeQuery("select wf_immoForm_id, immoForm_displayName, immoForm_realName from JSDB.jsf_immobilityForm where wf_module_id = " + 
            moduleId);
        while (rs.next()) {
          AccessTableVO tableVO = new AccessTableVO();
          tableVO.setId(rs.getInt("wf_immoForm_id"));
          tableVO.setDisplayName(rs.getString("immoForm_displayName"));
          tableVO.setRealName(rs.getString("immoForm_realName"));
          alist.add(tableVO);
        } 
        rs.close();
      } else {
        String domainId = (moduleVO.getDomainId() == null) ? "0" : moduleVO.getDomainId();
        rs = this.stmt.executeQuery("select table_id, table_desname, table_name from JSDB.ttable where domain_id=" + domainId);
        while (rs.next()) {
          AccessTableVO tableVO = new AccessTableVO();
          tableVO.setId(rs.getInt("table_id"));
          tableVO.setDisplayName(rs.getString("table_desname"));
          tableVO.setRealName(rs.getString("table_name"));
          alist.add(tableVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getRelationSimpleField(String moduleId, String tableId) throws Exception {
    ModuleVO moduleVO = getModule(new Integer(moduleId));
    begin();
    ArrayList<SimpleFieldVO> alist = new ArrayList();
    int formType = moduleVO.getFormType();
    String sql = "";
    try {
      if (formType == 0) {
        sql = "select wf_immofield_id, immofield_displayName, immofield_realName, immofield_canRemind, immoField_poField, immoField_canModify, immoField_canIdea from JSDB.jsf_immobilityField where wf_immoForm_id = " + 
          tableId;
        ResultSet rs = this.stmt.executeQuery(sql);
        String canIdea = "";
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("wf_immofield_id"));
          fieldVO.setDisplayName(rs.getString("immofield_displayName"));
          fieldVO.setRealName(rs.getString("immofield_realName"));
          if (rs.getInt("immofield_canRemind") == 0) {
            fieldVO.setRemind(false);
          } else {
            fieldVO.setRemind(true);
          } 
          fieldVO.setPoField(rs.getString("immoField_poField"));
          if (rs.getInt("immofield_canModify") == 0) {
            fieldVO.setCanModify(false);
          } else {
            fieldVO.setCanModify(true);
          } 
          canIdea = rs.getString("immofield_canIdea");
          if (canIdea != null && canIdea.equals("1")) {
            fieldVO.setCanIdea(true);
          } else {
            fieldVO.setCanIdea(false);
          } 
          alist.add(fieldVO);
        } 
        rs.close();
      } else {
        if (!moduleId.trim().equals("1")) {
          sql = "select field_id, field_desname, field_name, field_show from JSDB.tfield where field_table = " + tableId;
        } else {
          sql = "select distinct field_id, field_desname, field_name, field_show from JSDB.tfield a,JSDB.ttable b,tpage c,tarea d where a.field_table = b.table_id and c.page_id=d.page_id and b.table_name=d.area_table and d.area_name='form1' and c.page_id=" + 
            tableId;
        } 
        ResultSet rs = this.stmt.executeQuery(sql);
        int show = 0;
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("field_id"));
          fieldVO.setDisplayName(rs.getString("field_desname"));
          fieldVO.setRealName(rs.getString("field_name"));
          show = rs.getInt("field_show");
          fieldVO.setPoField(String.valueOf(show));
          if (show == 103 || show == 104 || show == 105 || show == 101 || show == 107 || show == 108 || show == 109 || show == 110) {
            fieldVO.setRemind(true);
          } else {
            fieldVO.setRemind(false);
          } 
          fieldVO.setCanModify(true);
          alist.add(fieldVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getSimpleField(String moduleId, String tableId) throws Exception {
    ModuleVO moduleVO = getModule(new Integer(moduleId));
    begin();
    ArrayList<SimpleFieldVO> alist = new ArrayList();
    int formType = moduleVO.getFormType();
    String sql = "";
    try {
      if (formType == 0) {
        sql = "select wf_immofield_id, immofield_displayName, immofield_realName, immofield_canRemind, immoField_poField, immoField_canModify, immoField_canIdea from JSDB.jsf_immobilityField where wf_immoForm_id = " + 
          tableId;
        ResultSet rs = this.stmt.executeQuery(sql);
        String canIdea = "";
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("wf_immofield_id"));
          fieldVO.setDisplayName(rs.getString("immofield_displayName"));
          fieldVO.setRealName(rs.getString("immofield_realName"));
          if (rs.getInt("immofield_canRemind") == 0) {
            fieldVO.setRemind(false);
          } else {
            fieldVO.setRemind(true);
          } 
          fieldVO.setPoField(rs.getString("immoField_poField"));
          if (rs.getInt("immofield_canModify") == 0) {
            fieldVO.setCanModify(false);
          } else {
            fieldVO.setCanModify(true);
          } 
          canIdea = rs.getString("immofield_canIdea");
          if (canIdea != null && canIdea.equals("1")) {
            fieldVO.setCanIdea(true);
          } else {
            fieldVO.setCanIdea(false);
          } 
          alist.add(fieldVO);
        } 
        rs.close();
      } else {
        if (!moduleId.trim().equals("1")) {
          sql = "select field_id, field_desname, field_name, field_show from JSDB.tfield where field_table = " + tableId;
        } else {
          sql = "select distinct field_id, field_desname, field_name, field_show, field_code from JSDB.tfield a,JSDB.ttable b,tpage c,tarea d where a.field_table = b.table_id and c.page_id=d.page_id and b.table_name=d.area_table   and  c.page_id=" + 
            tableId + 
            " ORDER BY a.field_code ASC";
        } 
        ResultSet rs = this.stmt.executeQuery(sql);
        int show = 0;
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("field_id"));
          fieldVO.setDisplayName(rs.getString("field_desname"));
          fieldVO.setRealName(rs.getString("field_name"));
          show = rs.getInt("field_show");
          fieldVO.setPoField(String.valueOf(show));
          if (show == 103 || show == 104 || show == 105 || show == 101 || show == 107 || show == 108 || show == 109 || show == 110 || show == 111) {
            fieldVO.setRemind(true);
          } else {
            fieldVO.setRemind(false);
          } 
          fieldVO.setCanModify(true);
          alist.add(fieldVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getSimpleFieldByOrder(String moduleId, String tableId) throws Exception {
    ModuleVO moduleVO = getModule(new Integer(moduleId));
    begin();
    ArrayList<SimpleFieldVO> alist = new ArrayList();
    int formType = moduleVO.getFormType();
    String sql = "";
    try {
      if (formType == 0) {
        sql = "select wf_immofield_id, immofield_displayName, immofield_realName, immofield_canRemind, immoField_poField, immoField_canModify, immoField_canIdea from JSDB.jsf_immobilityField where wf_immoForm_id = " + 
          tableId;
        ResultSet rs = this.stmt.executeQuery(sql);
        String canIdea = "";
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("wf_immofield_id"));
          fieldVO.setDisplayName(rs.getString("immofield_displayName"));
          fieldVO.setRealName(rs.getString("immofield_realName"));
          if (rs.getInt("immofield_canRemind") == 0) {
            fieldVO.setRemind(false);
          } else {
            fieldVO.setRemind(true);
          } 
          fieldVO.setPoField(rs.getString("immoField_poField"));
          if (rs.getInt("immofield_canModify") == 0) {
            fieldVO.setCanModify(false);
          } else {
            fieldVO.setCanModify(true);
          } 
          canIdea = rs.getString("immofield_canIdea");
          if (canIdea != null && canIdea.equals("1")) {
            fieldVO.setCanIdea(true);
          } else {
            fieldVO.setCanIdea(false);
          } 
          alist.add(fieldVO);
        } 
        rs.close();
      } else {
        if (!moduleId.trim().equals("1")) {
          sql = "select field_id, field_desname, field_name, field_show from JSDB.tfield where field_table = " + tableId + " order by field_sequence";
        } else {
          sql = "select distinct field_id, field_desname, field_name, field_show,field_sequence from JSDB.tfield a,JSDB.ttable b,tpage c,tarea d where a.field_table = b.table_id and c.page_id=d.page_id and b.table_name=d.area_table and c.page_id=" + 
            tableId + " order by a.field_sequence";
        } 
        ResultSet rs = this.stmt.executeQuery(sql);
        int show = 0;
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("field_id"));
          fieldVO.setDisplayName(rs.getString("field_desname"));
          fieldVO.setRealName(rs.getString("field_name"));
          show = rs.getInt("field_show");
          fieldVO.setPoField(String.valueOf(show));
          if (show == 103 || show == 104 || show == 105 || show == 101 || show == 107 || show == 108 || show == 109 || show == 110 || show == 111) {
            fieldVO.setRemind(true);
          } else {
            fieldVO.setRemind(false);
          } 
          fieldVO.setCanModify(true);
          alist.add(fieldVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getSimpleField(String moduleId, String tableId, Set rwControlSet) throws Exception {
    ModuleVO moduleVO = getModule(new Integer(moduleId));
    begin();
    ArrayList<SimpleFieldVO> alist = new ArrayList();
    int formType = moduleVO.getFormType();
    String sql = "";
    Iterator<WFReadWriteControlPO> rwIter = rwControlSet.iterator();
    String rwFieldId = "";
    while (rwIter.hasNext()) {
      WFReadWriteControlPO rwPO = rwIter.next();
      rwFieldId = String.valueOf(rwFieldId) + rwPO.getControlField() + ",";
    } 
    rwFieldId = String.valueOf(rwFieldId) + "0";
    try {
      if (formType == 0) {
        sql = "select wf_immofield_id, immofield_displayName, immofield_realName, immofield_canRemind, immoField_poField, immoField_canModify, immoField_canIdea from JSDB.jsf_immobilityField where wf_immoForm_id = " + 
          tableId + " and wf_immofield_id not in (" + rwFieldId + ")";
        ResultSet rs = this.stmt.executeQuery(sql);
        String canIdea = "";
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("wf_immofield_id"));
          fieldVO.setDisplayName(rs.getString("immofield_displayName"));
          fieldVO.setRealName(rs.getString("immofield_realName"));
          if (rs.getInt("immofield_canRemind") == 0) {
            fieldVO.setRemind(false);
          } else {
            fieldVO.setRemind(true);
          } 
          fieldVO.setPoField(rs.getString("immoField_poField"));
          if (rs.getInt("immofield_canModify") == 0) {
            fieldVO.setCanModify(false);
          } else {
            fieldVO.setCanModify(true);
          } 
          canIdea = rs.getString("immofield_canIdea");
          if (canIdea != null && canIdea.equals("1")) {
            fieldVO.setCanIdea(true);
          } else {
            fieldVO.setCanIdea(false);
          } 
          alist.add(fieldVO);
        } 
        rs.close();
      } else {
        if (!moduleId.trim().equals("1")) {
          sql = "select field_id, field_desname, field_name, field_show from JSDB.tfield where field_table = " + tableId + " and field_id not in (" + rwFieldId + ")";
        } else {
          sql = "select distinct field_id, field_desname, field_name, field_show from JSDB.tfield a,JSDB.ttable b,tpage c,tarea d where a.field_table = b.table_id and c.page_id=d.page_id and b.table_name=d.area_table and c.page_id=" + 
            tableId + " and field_id not in (" + rwFieldId + ")";
        } 
        ResultSet rs = this.stmt.executeQuery(sql);
        int show = 0;
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("field_id"));
          fieldVO.setDisplayName(rs.getString("field_desname"));
          fieldVO.setRealName(rs.getString("field_name"));
          show = rs.getInt("field_show");
          fieldVO.setPoField(String.valueOf(show));
          if (show == 103 || show == 104 || show == 105 || show == 101 || show == 107 || show == 108 || show == 109 || show == 110) {
            fieldVO.setRemind(true);
          } else {
            fieldVO.setRemind(false);
          } 
          fieldVO.setCanModify(true);
          alist.add(fieldVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getNotProtectField(String moduleId, String tableId, Set ptControlSet) throws Exception {
    ModuleVO moduleVO = getModule(new Integer(moduleId));
    begin();
    ArrayList<SimpleFieldVO> alist = new ArrayList();
    int formType = moduleVO.getFormType();
    String sql = "";
    Iterator<WFProtectControlPO> rptIter = ptControlSet.iterator();
    String ptFieldId = "";
    while (rptIter.hasNext()) {
      WFProtectControlPO ptPO = rptIter.next();
      ptFieldId = String.valueOf(ptFieldId) + ptPO.getControlField() + ",";
    } 
    ptFieldId = String.valueOf(ptFieldId) + "0";
    try {
      if (formType == 0) {
        sql = "select wf_immofield_id, immofield_displayName, immofield_realName, immofield_canRemind, immoField_poField, immoField_canModify, immoField_canIdea from JSDB.jsf_immobilityField where wf_immoForm_id = " + 
          tableId + " and wf_immofield_id not in (" + ptFieldId + ")";
        ResultSet rs = this.stmt.executeQuery(sql);
        String canIdea = "";
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("wf_immofield_id"));
          fieldVO.setDisplayName(rs.getString("immofield_displayName"));
          fieldVO.setRealName(rs.getString("immofield_realName"));
          if (rs.getInt("immofield_canRemind") == 0) {
            fieldVO.setRemind(false);
          } else {
            fieldVO.setRemind(true);
          } 
          fieldVO.setPoField(rs.getString("immoField_poField"));
          if (rs.getInt("immofield_canModify") == 0) {
            fieldVO.setCanModify(false);
          } else {
            fieldVO.setCanModify(true);
          } 
          canIdea = rs.getString("immofield_canIdea");
          if (canIdea != null && canIdea.equals("1")) {
            fieldVO.setCanIdea(true);
          } else {
            fieldVO.setCanIdea(false);
          } 
          alist.add(fieldVO);
        } 
        rs.close();
      } else {
        if (!moduleId.trim().equals("1")) {
          sql = "select field_id, field_desname, field_name, field_show from JSDB.tfield where field_table = " + tableId + " and field_id not in (" + ptFieldId + ")";
        } else {
          sql = "select distinct field_id, field_desname, field_name, field_show from JSDB.tfield a,JSDB.ttable b,tpage c,tarea d where a.field_table = b.table_id and c.page_id=d.page_id and b.table_name=d.area_table and c.page_id=" + 
            tableId + " and field_id not in (" + ptFieldId + ")";
        } 
        ResultSet rs = this.stmt.executeQuery(sql);
        int show = 0;
        while (rs.next()) {
          SimpleFieldVO fieldVO = new SimpleFieldVO();
          fieldVO.setId(rs.getLong("field_id"));
          fieldVO.setDisplayName(rs.getString("field_desname"));
          fieldVO.setRealName(rs.getString("field_name"));
          show = rs.getInt("field_show");
          fieldVO.setPoField(String.valueOf(show));
          if (show == 103 || show == 104 || show == 105 || show == 101 || show == 107 || show == 108 || show == 109 || show == 110) {
            fieldVO.setRemind(true);
          } else {
            fieldVO.setRemind(false);
          } 
          fieldVO.setCanModify(true);
          alist.add(fieldVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return alist;
  }
  
  public Long insertTable(String tableName, String fieldString, List<E> valueList, String seqName) throws Exception {
    begin();
    String sql = "";
    String id = "";
    try {
      for (int i = 0; i < valueList.size(); i++) {
        id = getTableId();
        sql = "insert into JSDB." + tableName + " (" + fieldString + ") values " + 
          "(" + id + "," + valueList.get(i).toString() + ")";
        this.stmt.executeUpdate(sql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return new Long(id);
  }
  
  public Long insertWorkAndSendMessage(String tableName, String fieldString, List<E> valueList, List<MessagesVO> msg) throws Exception {
    begin();
    String sql = "";
    ResultSet rs = null;
    String id = "";
    MessagesBD messageBD = new MessagesBD();
    try {
      for (int i = 0; i < valueList.size(); i++) {
        id = getTableId();
        sql = "insert into JSDB." + tableName + " (" + fieldString + ") values " + 
          "(" + id + "," + valueList.get(i).toString() + ")";
        this.stmt.executeUpdate(sql);
        MessagesVO msgVO = msg.get(i);
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + id);
        msgVO.setData_id(Long.parseLong(id));
        msg.set(i, msgVO);
      } 
      messageBD.messageArrayAdd(msg);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return new Long(id);
  }
  
  public String copyProcInfo(String processId, String employeeId, String tTableId, String tRecordId) throws Exception {
    ResultSet rs = null;
    String sql = "";
    String returnResult = "-1";
    begin();
    Connection exeConn = null;
    Statement exeStmt = null;
    try {
      exeConn = getDataSource().getConnection();
      exeStmt = exeConn.createStatement();
      String domainId = "0";
      ArrayList<Object[]> activitylist = new ArrayList();
      sql = "select wf_activity_id,activityName,activityDescription,activityType,allowStandFor,allowCancel,allowTransition,participantType,participantUser,participantGroup,participantUserName,participantUserField,pressType,deadLineTime,pressMotionTime,activityDocumentation,activityIcon,activityBeginEnd,transactType,actiCommField,needPassRound,passRoundUserType,passRoundUser,passRoundUserGroup,passRoundUserName,passRoundUserField,passRoundCommField,participantRole,passRoundRole,activityClass,activitySubProc,subProcType,PARTICIPANTGIVENORG,PASSROUNDGIVENORG,commentRange,domain_id,operButton,form_id,actiCommFieldType,passRoundCommFieldType,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId,pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,allowautocheck,isDivide,isGather,isBranch,activityDelaySend from JSDB.jsf_activity where wf_workflowprocess_id = " + 







        
        processId;
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] arrayOfObject = new Object[52];
        arrayOfObject[0] = rs.getString("wf_activity_id");
        arrayOfObject[1] = rs.getString("activityName");
        arrayOfObject[2] = rs.getString("activityDescription");
        arrayOfObject[3] = rs.getString("activityType");
        arrayOfObject[4] = rs.getString("allowStandFor");
        arrayOfObject[5] = rs.getString("allowCancel");
        arrayOfObject[6] = rs.getString("allowTransition");
        arrayOfObject[7] = rs.getString("participantType");
        arrayOfObject[8] = rs.getString("participantUser");
        arrayOfObject[9] = rs.getString("participantGroup");
        arrayOfObject[10] = rs.getString("participantUserName");
        arrayOfObject[11] = rs.getString("participantUserField");
        arrayOfObject[12] = (new StringBuilder(String.valueOf(rs.getInt("pressType")))).toString();
        arrayOfObject[13] = (new StringBuilder(String.valueOf(rs.getInt("deadLineTime")))).toString();
        arrayOfObject[14] = (new StringBuilder(String.valueOf(rs.getInt("pressMotionTime")))).toString();
        arrayOfObject[15] = rs.getString("activityDocumentation");
        arrayOfObject[16] = rs.getString("activityIcon");
        arrayOfObject[17] = rs.getString("activityBeginEnd");
        arrayOfObject[18] = rs.getString("transactType");
        arrayOfObject[19] = rs.getString("actiCommField");
        arrayOfObject[20] = rs.getString("needPassRound");
        arrayOfObject[21] = rs.getString("passRoundUserType");
        arrayOfObject[22] = rs.getString("passRoundUser");
        arrayOfObject[23] = rs.getString("passRoundUserGroup");
        arrayOfObject[24] = rs.getString("passRoundUserName");
        arrayOfObject[25] = rs.getString("passRoundUserField");
        arrayOfObject[26] = rs.getString("passRoundCommField");
        arrayOfObject[27] = rs.getString("participantRole");
        arrayOfObject[28] = rs.getString("passRoundRole");
        arrayOfObject[29] = rs.getString("activityClass");
        arrayOfObject[30] = rs.getString("activitySubProc");
        arrayOfObject[31] = rs.getString("subProcType");
        arrayOfObject[32] = rs.getString("PARTICIPANTGIVENORG");
        arrayOfObject[33] = rs.getString("PASSROUNDGIVENORG");
        arrayOfObject[34] = rs.getString("commentRange");
        domainId = rs.getString("domain_id");
        arrayOfObject[35] = rs.getString("operButton");
        arrayOfObject[36] = rs.getString("form_id");
        arrayOfObject[37] = rs.getString("actiCommFieldType");
        arrayOfObject[38] = rs.getString("passRoundCommFieldType");
        arrayOfObject[39] = (rs.getString("allowSmsRemind") == null) ? "0" : rs.getString("allowSmsRemind");
        arrayOfObject[40] = (rs.getString("tranType") == null) ? "0" : rs.getString("tranType");
        arrayOfObject[41] = rs.getString("tranCustomExtent");
        arrayOfObject[42] = rs.getString("tranCustomExtentId");
        arrayOfObject[43] = (rs.getString("pressDealType") == null) ? "0" : rs.getString("pressDealType");
        arrayOfObject[44] = (rs.getString("tranReadType") == null) ? "0" : rs.getString("tranReadType");
        arrayOfObject[45] = rs.getString("tranReadCustomExtent");
        arrayOfObject[46] = rs.getString("tranReadCustomExtentId");
        arrayOfObject[47] = rs.getString("allowautocheck");
        arrayOfObject[48] = rs.getString("isDivide");
        arrayOfObject[49] = rs.getString("isGather");
        arrayOfObject[50] = rs.getString("isBranch");
        arrayOfObject[51] = rs.getString("activityDelaySend");
        activitylist.add(arrayOfObject);
      } 
      rs.close();
      Calendar cal = Calendar.getInstance();
      Object[] obj = (Object[])null;
      for (int i = 0; i < activitylist.size(); i++) {
        obj = activitylist.get(i);
        String wfProceedActivityId = getTableId();
        String partRole = "", passRole = "", activityDelaySend = "";
        if (obj[27] != null)
          partRole = obj[27].toString(); 
        if (obj[28] != null)
          passRole = obj[28].toString(); 
        if (obj[51] != null)
          activityDelaySend = String.valueOf(cal.get(1)) + "-" + obj[51].toString() + " 08:00:00"; 
        sql = " insert into JSDB.JSF_p_Activity ( WF_ProceedActivity_Id, wf_activity_id, activityName, activityDescription, activityType, allowStandFor, allowCancel, allowTransition, participantType, participantUser, participantGroup, participantUserName, participantUserField, pressType, deadLineTime, pressMotionTime, activityDocumentation, activityIcon, activityBeginEnd, dealWithSign, wf_workflowprocess_id, employee_id,  ttable_id, trecord_id, transactType, actiCommField , needPassRound, passRoundUserType, passRoundUser, passRoundUserGroup, passRoundUserName, passRoundUserField, passRoundCommField,participantRole, passRoundRole, activityClass, activitySubProc, subProcType,PARTICIPANTGIVENORG,PASSROUNDGIVENORG, commentRange,DOMAIN_ID,operbutton,form_id,actiCommFieldType,passRoundCommFieldType,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId, pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,allowautocheck,isDivide,isGather,isBranch,activityDelaySend) values (" + 









          
          wfProceedActivityId + "," + obj[0] + ",'" + obj[1] + "','" + obj[2] + "'," + 
          obj[3] + "," + obj[4] + "," + obj[5] + "," + obj[6] + "," + obj[7] + ",'" + 
          obj[8] + "','" + obj[9] + "','" + obj[10] + "','" + obj[11] + "'," + 
          obj[12] + "," + obj[13] + "," + obj[14] + ",'" + obj[15] + "','" + obj[16] + "'," + 
          obj[17] + ",0," + processId + "," + employeeId + "," + tTableId + "," + tRecordId + 
          ",'" + obj[18] + "', '" + obj[19] + "', " + obj[20] + ", " + obj[21] + ", '" + obj[22] + 
          "', '" + obj[23] + "','" + obj[24] + "', '" + obj[25] + "', '" + obj[26] + "', '" + partRole + "'," + 
          "'" + passRole + "', " + obj[29] + ", " + obj[30] + ", " + obj[31] + ",'" + obj[32] + "','" + obj[33] + 
          "', '" + obj[34] + "'," + domainId + ", '" + obj[35] + "', '" + obj[36] + "', '" + obj[37] + 
          "', '" + obj[38] + "', " + obj[39] + ", " + obj[40] + ", '" + obj[41] + "', '" + obj[42] + "'," + obj[43] + 
          ", " + obj[44] + ", '" + obj[45] + "', '" + obj[46] + "'," + obj[47] + "," + obj[48] + "," + obj[49] + "," + obj[50] + ",'" + activityDelaySend + "')";
        exeStmt.addBatch(sql);
        String sql2 = "";
        ArrayList<Object[]> alist = new ArrayList();
        sql2 = " select controlType, controlField from JSDB.JSF_readwritecontrol  where controltype>0 and wf_activity_id = " + 
          obj[0];
        rs = this.stmt.executeQuery(sql2);
        while (rs.next()) {
          Object[] rwControlObj = new Object[2];
          rwControlObj[0] = rs.getString("controlType");
          rwControlObj[1] = rs.getString("controlField");
          alist.add(rwControlObj);
        } 
        rs.close();
        Object[] obj2 = (Object[])null;
        int j;
        for (j = 0; j < alist.size(); j++) {
          obj2 = alist.get(j);
          String wf_proceedReadWriteControl_id = getTableId();
          sql2 = " insert into JSDB.jsf_p_readwritecontrol (  wf_proceedreadwritecontrol_id, controlType, controlField, wf_activity_id, wf_proceedactivity_id, DOMAIN_ID  ) values (" + 


            
            wf_proceedReadWriteControl_id + "," + obj2[0] + "," + obj2[1] + "," + 
            obj[0] + "," + wfProceedActivityId + "," + domainId + ")";
          exeStmt.addBatch(sql2);
        } 
        alist.clear();
        sql2 = "select conditionField, pressRelation, compareValue, pressMotionTime, motionTime from JSDB.jsf_press where wf_activity_id = " + 
          obj[0];
        rs = this.stmt.executeQuery(sql2);
        while (rs.next()) {
          Object[] pressObj = new Object[5];
          pressObj[0] = (new StringBuilder(String.valueOf(rs.getInt("conditionField")))).toString();
          pressObj[1] = rs.getString("pressRelation");
          pressObj[2] = rs.getString("compareValue");
          pressObj[3] = (new StringBuilder(String.valueOf(rs.getInt("pressMotionTime")))).toString();
          pressObj[4] = (new StringBuilder(String.valueOf(rs.getInt("motionTime")))).toString();
          alist.add(pressObj);
        } 
        rs.close();
        for (j = 0; j < alist.size(); j++) {
          obj2 = alist.get(j);
          String wf_proceedpress_id = getTableId();
          sql2 = " insert into JSDB.jsf_p_press (  wf_press_id, conditionField, pressrelation, compareValue, pressMotionTime,  motionTime, wf_activity_id, wf_proceedactivity_id,DOMAIN_ID  ) values (" + 


            
            wf_proceedpress_id + "," + obj2[0] + ",'" + obj2[1] + "','" + obj2[2] + "'," + 
            obj2[3] + "," + obj2[4] + "," + obj[0] + "," + wfProceedActivityId + "," + domainId + ")";
          exeStmt.addBatch(sql2);
        } 
        alist.clear();
        sql2 = "select wf_transition_id, transitionFrom, transitionName, transitionTo,  transitionDescription,EXPRESSION,defaultActivity,lineorder from JSDB.jsf_transition where transitionFrom = " + 
          obj[0];
        rs = this.stmt.executeQuery(sql2);
        while (rs.next()) {
          Object[] transObj = new Object[8];
          transObj[0] = (new StringBuilder(String.valueOf(rs.getInt("wf_transition_id")))).toString();
          transObj[1] = (new StringBuilder(String.valueOf(rs.getInt("transitionFrom")))).toString();
          transObj[2] = rs.getString("transitionName");
          transObj[3] = (new StringBuilder(String.valueOf(rs.getInt("transitionTo")))).toString();
          transObj[4] = rs.getString("transitionDescription");
          transObj[5] = rs.getString("EXPRESSION");
          transObj[6] = rs.getString("defaultActivity");
          transObj[7] = rs.getString("lineorder");
          alist.add(transObj);
        } 
        rs.close();
        for (j = 0; j < alist.size(); j++) {
          obj2 = alist.get(j);
          String wf_proceedtransition_id = getTableId();
          sql2 = " insert into JSDB.jsf_p_transition (  wf_proceedtransition_id, transitionFrom, transitionName, transitionTo, transitionDescription, wf_proceedactivity_id, DOMAIN_ID, EXPRESSION,defaultActivity,lineorder ) values (" + 


            
            wf_proceedtransition_id + "," + obj2[1] + ",'" + obj2[2] + "'," + obj2[3] + 
            ",'" + obj2[4] + "'," + wfProceedActivityId + ", " + domainId + ",'" + obj2[5] + "'," + ((obj2[6] == null) ? "0" : (String)obj2[6]) + "," + obj2[7] + ")";
          exeStmt.addBatch(sql2);
          sql2 = "select conditionField, compareValue, relation from JSDB.jsf_transitionrestriction  where wf_transition_id = " + 
            obj2[0];
          rs = this.stmt.executeQuery(sql2);
          ArrayList<Object[]> blist = new ArrayList();
          while (rs.next()) {
            Object[] condiObj = new Object[3];
            condiObj[0] = (new StringBuilder(String.valueOf(rs.getInt("conditionField")))).toString();
            condiObj[1] = rs.getString("compareValue");
            condiObj[2] = rs.getString("relation");
            blist.add(condiObj);
          } 
          rs.close();
          Object[] obj3 = (Object[])null;
          for (int k = 0; k < blist.size(); k++) {
            obj3 = blist.get(k);
            String wf_proceedtr_id = getTableId();
            sql2 = " insert into JSDB.jsf_p_tr ( wf_proceedtr_id, conditionField, compareValue, relation,  wf_proceedtransition_id, DOMAIN_ID  ) values (" + 


              
              wf_proceedtr_id + "," + obj3[0] + ",'" + obj3[1] + "','" + obj3[2] + "'," + 
              wf_proceedtransition_id + "," + domainId + ")";
            exeStmt.addBatch(sql2);
          } 
        } 
      } 
      exeStmt.executeBatch();
      exeStmt.clearBatch();
      exeStmt.close();
      exeConn.close();
      end();
      returnResult = "0";
    } catch (Exception e) {
      if (exeConn != null)
        exeConn.close(); 
      end();
      e.printStackTrace();
      throw e;
    } finally {}
    return returnResult;
  }
  
  public String getActivityURL(String moduleId) throws Exception {
    String url = "/jsoa/ActivityAction.do?moduleId=" + moduleId;
    begin();
    ResultSet rs = null;
    try {
      String sql = "select wf_workflowprocess_id from JSDB.jsf_workflowprocess p, JSDB.jsf_package pack where p.wf_package_id = pack.wf_package_id and pack.wf_module_id = " + 
        moduleId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        url = String.valueOf(url) + "&processId=" + rs.getString("wf_workflowprocess_id"); 
      rs.close();
      sql = "select wf_immoForm_id from JSDB.jsf_immobilityForm where wf_module_id = " + moduleId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        url = String.valueOf(url) + "&tableId=" + rs.getString("wf_immoForm_id"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return url;
  }
  
  public String getLeader(String userId) throws Exception {
    begin();
    ResultSet rs = null;
    String leader = "";
    try {
      rs = this.stmt.executeQuery("select empleaderid from JSDB.org_employee where emp_id = " + userId);
      if (rs.next())
        leader = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return leader;
  }
  
  public String[] getPress(String activityId, String tableId, String recordId, String moduleId) throws Exception {
    ModuleVO moduleVO = getModule(new Integer(moduleId));
    begin();
    ResultSet rs = null;
    String sql = "";
    String[] pressStr = { "-1", "-1" };
    try {
      sql = " select conditionField, pressRelation, compareValue, pressMotionTime, motionTime from JSDB.jsf_press where wf_activity_id = " + 
        activityId;
      rs = this.stmt.executeQuery(sql);
      ArrayList<Object[]> alist = new ArrayList();
      while (rs.next()) {
        Object[] arrayOfObject = new Object[5];
        arrayOfObject[0] = (new StringBuilder(String.valueOf(rs.getInt("conditionField")))).toString();
        arrayOfObject[1] = rs.getString("pressRelation");
        arrayOfObject[2] = rs.getString("compareValue");
        arrayOfObject[3] = (new StringBuilder(String.valueOf(rs.getInt("pressMotionTime")))).toString();
        arrayOfObject[4] = (new StringBuilder(String.valueOf(rs.getInt("motionTime")))).toString();
        alist.add(arrayOfObject);
      } 
      rs.close();
      Object[] obj = (Object[])null;
      String fieldName = "";
      String fieldValue = "";
      String tableName = "";
      int formType = moduleVO.getFormType();
      for (int i = 0; i < alist.size(); i++) {
        obj = alist.get(i);
        if (formType == 0) {
          String pk = "";
          rs = this.stmt.executeQuery("select immoForm_realName, immoForm_primaryKey from JSDB.jsf_immobilityForm where wf_module_id = " + 
              moduleId);
          if (rs.next()) {
            tableName = rs.getString("immoForm_realName");
            pk = rs.getString("immoForm_primaryKey");
          } 
          rs.close();
          rs = this.stmt.executeQuery("select immoField_realName from JSDB.jsf_immobility where wf_immoForm_id = " + 
              tableId + " and wf_immoField_id = " + 
              obj[0]);
          if (rs.next())
            fieldName = rs.getString("immoField_realName"); 
          rs.close();
          rs = this.stmt.executeQuery("select " + fieldName + " from JSDB." + tableName + " where " + 
              pk + " = " + recordId);
          if (rs.next())
            fieldValue = rs.getString(1); 
          rs.close();
        } else {
          if (moduleId.equals("1")) {
            rs = this.stmt.executeQuery(
                "select c.area_table as table_name from tpage b,tarea c where b.page_id = " + 
                tableId + " and b.page_id=c.page_id");
          } else {
            rs = this.stmt.executeQuery(
                "select table_name from JSDB.ttable where table_id = " + 
                tableId);
          } 
          if (rs.next())
            tableName = rs.getString("table_name"); 
          rs.close();
          rs = this.stmt.executeQuery("select field_name from JSDB.tfield where field_id = " + obj[0]);
          if (rs.next())
            fieldName = rs.getString("field_name"); 
          rs.close();
          rs = this.stmt.executeQuery(" select " + fieldName + " from JSDB." + tableName + " where " + 
              tableName + "_id = " + recordId);
          if (rs.next())
            fieldValue = rs.getString(1); 
          rs.close();
        } 
        if (obj[1].toString().equals("<")) {
          try {
            if (Integer.parseInt(fieldValue) < Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals("<=")) {
          try {
            if (Integer.parseInt(fieldValue) <= Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals("<>")) {
          try {
            if (Integer.parseInt(fieldValue) != Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals("=")) {
          if (fieldValue.equals(obj[2].toString())) {
            pressStr[0] = obj[3].toString();
            pressStr[1] = obj[4].toString();
            break;
          } 
        } else if (obj[1].toString().equals(">")) {
          try {
            if (Integer.parseInt(fieldValue) > Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals(">=")) {
          try {
            if (Integer.parseInt(fieldValue) >= Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return pressStr;
  }
  
  public List getAllNextActivity(String tableId, String recordId, String activityId) throws Exception {
    begin();
    ArrayList alist = new ArrayList();
    try {
      String sql = "select transitionTo,defaultActivity,b.lineorder from JSDB.jsf_p_Activity a, JSDB.jsf_p_transition b where a.wf_proceedActivity_id = b.wf_proceedActivity_id  and a.ttable_id=" + 
        
        tableId + 
        " and a.trecord_id=" + recordId + 
        " and b.transitionFrom=" + activityId + " order by b.lineorder";
      StringBuffer sb = new StringBuffer("-1");
      Map<Object, Object> map = new HashMap<Object, Object>();
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String tempActivityId = rs.getString(1);
        String defaultActivityId = rs.getString(2);
        sb.append(",").append(tempActivityId);
        map.put(tempActivityId, defaultActivityId);
      } 
      rs.close();
      sql = "select wf_activity_id,activityName,activityBeginEnd,ALLOWSTANDFOR,ALLOWCANCEL,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ALLOWTRANSITION,allowSmsRemind,transacttype from JSDB.jsf_p_Activity Where wf_activity_id in (" + 
        
        sb.toString() + ") and ttable_id=" + tableId + " and trecord_id=" + recordId + " ORDER BY wf_activity_id asc";
      if ("shandongguotou".equals(SystemCommon.getCustomerName()))
        sql = "select a.wf_activity_id,a.activityName,a.activityBeginEnd,a.ALLOWSTANDFOR,a.ALLOWCANCEL,a.PRESSTYPE,a.DEADLINETIME,a.PRESSMOTIONTIME,a.ALLOWTRANSITION,a.allowSmsRemind,a.transacttype,b.ACTIVITYORDER  from(select wf_activity_id,activityName,activityBeginEnd,ALLOWSTANDFOR,ALLOWCANCEL,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ALLOWTRANSITION,allowSmsRemind,transacttype from JSDB.jsf_p_Activity Where wf_activity_id in (" + 


          
          sb.toString() + ") and ttable_id=" + tableId + " and trecord_id=" + recordId + ") a,jsf_activity b " + 
          " Where a.wf_activity_id=b.wf_activity_id ORDER BY a.wf_activity_id asc"; 
      Map<String, ActivityVO> actMap = new HashMap<String, ActivityVO>();
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        ActivityVO activityVO = new ActivityVO();
        String tempActivityId = rs.getString("wf_activity_id");
        activityVO.setId(Long.parseLong(tempActivityId));
        activityVO.setName(rs.getString("activityName"));
        activityVO.setBeginEnd(rs.getInt("activityBeginEnd"));
        activityVO.setAllowStandFor(rs.getInt("ALLOWSTANDFOR"));
        activityVO.setAllowcancel(rs.getInt("ALLOWCANCEL"));
        activityVO.setPressType(rs.getInt("PRESSTYPE"));
        activityVO.setDeadlineTime(rs.getInt("DEADLINETIME"));
        activityVO.setPressMotionTime(rs.getInt("PRESSMOTIONTIME"));
        activityVO.setAllowTransition(rs.getInt("ALLOWTRANSITION"));
        activityVO.setAllowSmsRemind(rs.getString("allowSmsRemind"));
        activityVO.setTransactType((rs.getString("transacttype") == null) ? "1" : rs.getString("transacttype"));
        if (map.get(tempActivityId) == null || "".equals(map.get(tempActivityId).toString()) || "null".equals(map.get(tempActivityId).toString())) {
          activityVO.setDefaultActivity("0");
        } else {
          activityVO.setDefaultActivity(map.get(tempActivityId).toString());
        } 
        actMap.put(tempActivityId, activityVO);
      } 
      rs.close();
      String[] actIdArr = sb.toString().split(",");
      for (int i = 0; i < actIdArr.length; i++) {
        if (!"-1".equals(actIdArr[i]))
          alist.add(actMap.get(actIdArr[i])); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getFirstAllNextActivity(String activityId) throws Exception {
    begin();
    Connection conn2 = this.dataSource.getConnection();
    Statement stmt2 = conn2.createStatement();
    ArrayList<ActivityVO> alist = new ArrayList();
    try {
      String sql = "select transitionTo,defaultActivity from JSDB.jsf_transition where transitionFrom=" + activityId;
      StringBuffer sb = new StringBuffer("-1");
      Map<Object, Object> map = new HashMap<Object, Object>();
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String tempActivityId = rs.getString(1);
        String defaultActivityId = rs.getString(2);
        sb.append(",").append(tempActivityId);
        map.put(tempActivityId, defaultActivityId);
      } 
      rs.close();
      sql = "select wf_activity_id,activityName,activityBeginEnd,ALLOWSTANDFOR,ALLOWCANCEL,PRESSTYPE,DEADLINETIME,PRESSMOTIONTIME,ALLOWTRANSITION,allowSmsRemind,transacttype from JSDB.jsf_Activity Where wf_activity_id in (" + 
        
        sb.toString() + ")  ORDER BY wf_activity_id";
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        ActivityVO activityVO = new ActivityVO();
        String tempActivityId = rs.getString("wf_activity_id");
        activityVO.setId(Long.parseLong(tempActivityId));
        activityVO.setName(rs.getString("activityName"));
        activityVO.setBeginEnd(rs.getInt("activityBeginEnd"));
        activityVO.setAllowStandFor(rs.getInt("ALLOWSTANDFOR"));
        activityVO.setAllowcancel(rs.getInt("ALLOWCANCEL"));
        activityVO.setPressType(rs.getInt("PRESSTYPE"));
        activityVO.setDeadlineTime(rs.getInt("DEADLINETIME"));
        activityVO.setPressMotionTime(rs.getInt("PRESSMOTIONTIME"));
        activityVO.setAllowTransition(rs.getInt("ALLOWTRANSITION"));
        activityVO.setAllowSmsRemind(rs.getString("allowSmsRemind"));
        activityVO.setTransactType((rs.getString("transacttype") == null) ? "1" : rs.getString("transacttype"));
        if (map.get(tempActivityId) == null || "".equals(map.get(tempActivityId).toString()) || "null".equals(map.get(tempActivityId).toString())) {
          activityVO.setDefaultActivity("0");
        } else {
          activityVO.setDefaultActivity(map.get(tempActivityId).toString());
        } 
        alist.add(activityVO);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
      stmt2.close();
      conn2.close();
    } 
    return alist;
  }
  
  public String getImmoPO(String immoFieldId) throws Exception {
    begin();
    String sql = "", immoPO = "";
    try {
      sql = "select immoField_poField from JSDB.jsf_immobilityField where wf_immofield_id = " + immoFieldId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        immoPO = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return immoPO;
  }
  
  public List getRWList(String activityId, String tableId, String recordId, String moduleId) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      if (moduleId.equals("1")) {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("oracle") >= 0) {
          rs = this.stmt.executeQuery("select controlType, field_name,field_show,field_value from JSDB.jsf_p_readwritecontrol a left join JSDB.tfield b on a.controlfield=b.field_id left join jsf_p_activity c on a.wf_proceedactivity_id=c.wf_proceedactivity_id where c.wf_activity_id = " + 

              
              activityId + 
              " and c.ttable_id = " + tableId + 
              " and c.trecord_id = " + recordId);
        } else {
          rs = this.stmt.executeQuery("select controlType, field_name,field_show,field_value from  JSDB.jsf_p_readwritecontrol a, JSDB.tfield b,JSDB.jsf_p_activity c  where c.wf_proceedactivity_id = a.wf_proceedactivity_id  and a.controlfield = b.field_id  and c.wf_activity_id = " + 


              
              activityId + 
              " and c.ttable_id = " + tableId + 
              " and c.trecord_id = " + recordId);
        } 
        while (rs.next()) {
          String[] str = { "", "", "", "" };
          str[0] = rs.getString(1);
          str[1] = rs.getString(2);
          str[2] = rs.getString(3);
          str[3] = rs.getString(4);
          alist.add(str);
        } 
      } else {
        rs = this.stmt.executeQuery("select controlType, immoField_poField from  JSDB.jsf_p_readwritecontrol a, JSDB.jsf_immobilityField b, JSDB.jsf_p_activity c  where c.wf_proceedactivity_id = a.wf_proceedactivity_id  and a.controlfield = b.wf_immoField_id  and c.wf_activity_id = " + 


            
            activityId + 
            " and c.ttable_id = " + tableId + 
            " and c.trecord_id = " + recordId);
        while (rs.next()) {
          String[] str = { "", "", "", "" };
          str[0] = rs.getString(1);
          str[1] = rs.getString(2);
          alist.add(str);
        } 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getProtectList(String activityId, String tableId, String recordId, String moduleId) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      if (moduleId.equals("1")) {
        rs = this.stmt.executeQuery("select controlType, field_name, field_desname from  JSDB.JSF_P_PROTECTCONTROL a, JSDB.tfield b,JSDB.JSF_p_activity c  where c.wf_proceedactivity_id = a.wf_proceedactivity_id  and a.controlfield = b.field_id  and c.wf_activity_id = " + 


            
            activityId + 
            " and c.ttable_id = " + tableId + 
            " and c.trecord_id = " + recordId);
      } else {
        rs = this.stmt.executeQuery("select controlType, immoField_poField, immofield_displayname from  JSDB.JSF_P_PROTECTCONTROL a, JSDB.jsf_immobilityField b, JSDB.jsf_p_activity c  where c.wf_proceedactivity_id = a.wf_proceedactivity_id  and a.controlfield = b.wf_immoField_id  and c.wf_activity_id = " + 


            
            activityId + 
            " and c.ttable_id = " + tableId + 
            " and c.trecord_id = " + recordId);
      } 
      while (rs.next()) {
        String[] str = { "", "", "" };
        str[0] = rs.getString(1);
        str[1] = rs.getString(2);
        str[2] = rs.getString(3);
        alist.add(str);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getCommField(String tableId, String recordId, String activityId) throws Exception {
    begin();
    ResultSet rs = null;
    String commField = "";
    try {
      rs = this.stmt.executeQuery("select actiCommField, passRoundCommField from JSDB.jsf_p_Activity where ttable_id = " + tableId + 
          " and trecord_id = " + recordId + " and wf_activity_id = " + activityId);
      if (rs.next())
        commField = String.valueOf(rs.getString(1)) + "$" + rs.getString(2); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return commField;
  }
  
  public String getAllCommField(String procId, String tableId, String recordId) throws Exception {
    begin();
    ResultSet rs = null;
    StringBuffer commField = new StringBuffer();
    try {
      rs = this.stmt.executeQuery("select actiCommField, passRoundCommField from JSDB.jsf_p_Activity where ttable_id = " + tableId + 
          " and trecord_id = " + recordId + " and wf_workFlowprocess_id = " + procId);
      String tmp = "", tmp2 = "";
      while (rs.next()) {
        tmp = rs.getString(1);
        tmp2 = rs.getString(2);
        if (tmp != null && !tmp.equals(""))
          commField.append("$" + tmp + "$"); 
        if (tmp2 != null && !tmp2.equals(""))
          commField.append("$" + tmp2 + "$"); 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return commField.toString();
  }
  
  public List getActiviyCommList(String tableId, String recordId, String poField) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      rs = this.stmt.executeQuery("select wf_activity_id from JSDB.jsf_p_Activity where actiCommField = '" + poField + "' and ttable_id = " + tableId + " and trecord_id = " + recordId);
      String tmpId = "";
      if (rs.next())
        tmpId = rs.getString(1); 
      rs.close();
      if (tmpId != null && !tmpId.equals("")) {
        String sql = " Select activity_id,activityname,empname,dealwithemployeecomment, dealwithtime,curstepcount  From JSDB.jsf_dealwith,JSDB.jsf_dealwithcomment,JSDB.org_employee Where  jsf_dealwith.wf_dealwith_id = jsf_dealwithcomment.wf_dealwith_id And  emp_id = dealwithemployee_id and  databasetable_id = " + 



          
          tableId + " and databaserecord_id = " + recordId + 
          " and activity_id = " + tmpId + " Order By jsf_dealwith.wf_dealwith_id,dealwithtime";
        rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
          String[] tmp = { "", "", "", "", "", "" };
          tmp[0] = (new StringBuilder(String.valueOf(rs.getInt("activity_id")))).toString();
          tmp[1] = rs.getString("activityname");
          tmp[2] = rs.getString("empname");
          tmp[3] = rs.getString("dealwithemployeecomment");
          tmp[4] = rs.getString("dealwithtime");
          tmp[5] = rs.getString("curstepcount");
          alist.add(tmp);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public void insertDealwithWithDate(String tableId, String recordId, String activityName, String activityId, String userId, String comment, String nextActivityName, String nextActivityId, String stepCount, String date, String commentField, String isStandForWork, String standForUserId) throws Exception {
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery(" select wf_dealwith_id from JSDB.jsf_dealwith where  databasetable_id = " + 
          tableId + " and " + 
          " databaserecord_id = " + recordId + " and " + 
          " activity_id = " + activityId + " and curStepCount = " + 
          stepCount);
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId();
        int curActivityStatus = 0;
        if (nextActivityId.equals("-1"))
          curActivityStatus = 1; 
        if (nextActivityId.equals("-2"))
          curActivityStatus = -2; 
        this.stmt.executeUpdate(" insert into JSDB.jsf_dealwith (  wf_dealwith_id, databasetable_id, databaserecord_id, activityname, activity_id, nextActivityName, nextActivityId, curActivityStatus, curStepCount  ) values ( " + 


            
            wf_dealwith_id + "," + tableId + "," + recordId + ",'" + activityName + 
            "'," + activityId + ",'" + nextActivityName + "'," + nextActivityId + 
            "," + curActivityStatus + "," + stepCount + ")");
      } else if (!nextActivityId.equals("0")) {
        this.stmt.execute(" update JSDB.jsf_dealwith set nextActivityId = " + nextActivityId + "," + 
            " nextActivityName = '" + nextActivityName + 
            "' where wf_dealwith_id = " + wf_dealwith_id);
      } 
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (isStandForWork.equals("0")) {
        String wf_dealwithcomment_id = getTableId();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, commentField) values (" + 
            
            wf_dealwithcomment_id + ", " + userId + ", '" + 
            comment + "', '" + date + "', " + 
            wf_dealwith_id + ", 0, '','" + commentField + "')";
        } else {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, commentField ) values (" + 
            
            wf_dealwithcomment_id + ", " + userId + ", '" + 
            comment + "', JSDB.FN_STRTODATE('" + date + "',''), " + 
            wf_dealwith_id + ", 0, '','" + commentField + "')";
        } 
        this.stmt.executeUpdate(tmpSql);
      } else {
        String wf_dealwithcomment_id = getTableId();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, commentField ) values (" + 
            
            wf_dealwithcomment_id + ", " + standForUserId + ", '" + 
            comment + "', '" + date + "', " + 
            wf_dealwith_id + ", " + userId + ", (select empname from JSDB.org_employee where emp_id = " + userId + "),'" + commentField + "')";
        } else {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, commentField ) values (" + 
            
            wf_dealwithcomment_id + ", " + standForUserId + ", '" + 
            comment + "', JSDB.FN_STRTODATE('" + date + "',''), " + 
            wf_dealwith_id + ", " + userId + ", (select empname from JSDB.org_employee where emp_id = " + userId + "),'" + commentField + "')";
        } 
        this.stmt.executeUpdate(tmpSql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public Long setSurveillance(WorkVO workVO) throws Exception {
    begin();
    String sql = "";
    String workId = "0";
    try {
      workId = getTableId();
      String creatorCancelLink = workVO.getCreatorCancelLink();
      creatorCancelLink = creatorCancelLink.replaceAll("workIdValue", workId);
      if (creatorCancelLink.indexOf("'") >= 0)
        creatorCancelLink = creatorCancelLink.replace('\'', '"'); 
      String selfTitle = "您的" + workVO.getRemindValue() + workVO.getFileType() + "正在办理中！";
      if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals(""))
        selfTitle = workVO.getDocTitle(); 
      if (selfTitle.length() > 100)
        selfTitle = selfTitle.substring(0, 99); 
      Date now = new Date();
      String isSubProcWork = "0", pareProcActiId = "0", pareStepCount = "0";
      String pareTableId = "0", pareRecordId = "0", pareProcNextActiId = "0";
      if (workVO.getIsSubProcWork() != null)
        isSubProcWork = workVO.getIsSubProcWork(); 
      if (workVO.getPareProcActiId() != null)
        pareProcActiId = workVO.getPareProcActiId(); 
      if (workVO.getPareStepCount() != null)
        pareStepCount = workVO.getPareStepCount(); 
      if (workVO.getPareTableId() != null)
        pareTableId = workVO.getPareTableId(); 
      if (workVO.getPareRecordId() != null)
        pareRecordId = workVO.getPareRecordId(); 
      if (workVO.getPareProcNextActiId() != null)
        pareProcNextActiId = workVO.getPareProcNextActiId(); 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "insert into JSDB.JSF_WORK (wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, creatorCancelLink,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,DOMAIN_ID,processDeadlineDate,relproject_id) values (" + 





          
          workId + "," + workVO.getSubmitEmployeeId() + ",1,'" + workVO.getFileType() + "','" + 
          workVO.getCurStep() + "','" + selfTitle + "','','" + workVO.getSelfMainLinkFile() + 
          "',1," + workVO.getActivity() + ",'" + workVO.getSubmitPerson() + "','" + 
          now.toLocaleString() + "'," + workVO.getSubmitEmployeeId() + 
          ",0," + workVO.getProcessId() + "," + workVO.getTableId() + "," + workVO.getRecordId() + 
          "," + workVO.getDeadLine() + "," + workVO.getPressTime() + ",'" + 
          now.toLocaleString() + "'," + workVO.getWorkType() + ",1," + 
          workVO.getAllowCancel() + ",0,'" + creatorCancelLink + "'," + isSubProcWork + "," + 
          pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
          pareProcNextActiId + ",'" + workVO.getSubmitOrg() + "', " + workVO.getDomainId() + "," + workVO.getProcessDeadlineDate() + "," + workVO.getRelProjectId() + ")";
      } else {
        sql = "insert into JSDB.JSF_WORK (wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, creatorCancelLink,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,DOMAIN_ID,processDeadlineDate,relproject_id) values (" + 





          
          workId + "," + workVO.getSubmitEmployeeId() + ",1,'" + workVO.getFileType() + "','" + 
          workVO.getCurStep() + "','" + selfTitle + "','','" + workVO.getSelfMainLinkFile() + 
          "',1," + workVO.getActivity() + ",'" + workVO.getSubmitPerson() + "',JSDB.FN_STRTODATE('" + 
          now.toLocaleString() + "','')," + workVO.getSubmitEmployeeId() + 
          ",0," + workVO.getProcessId() + "," + workVO.getTableId() + "," + workVO.getRecordId() + 
          "," + workVO.getDeadLine() + "," + workVO.getPressTime() + ",JSDB.FN_STRTODATE('" + 
          now.toLocaleString() + "','')," + workVO.getWorkType() + ",1," + 
          workVO.getAllowCancel() + ",0,'" + creatorCancelLink + "'," + isSubProcWork + "," + 
          pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
          pareProcNextActiId + ",'" + workVO.getSubmitOrg() + "', " + workVO.getDomainId() + "," + workVO.getProcessDeadlineDate() + "," + workVO.getRelProjectId() + ")";
      } 
      this.stmt.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return new Long(workId);
  }
  
  public List getOffiDict(String userId, String domainId) throws Exception {
    begin();
    ArrayList<String> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("select distinct dictionContent from JSDB.OA_OfficalDiction where (emp_id = " + userId + 
          " or DictionIsShare = 1) and domain_id=" + domainId);
      while (rs.next())
        alist.add(rs.getString(1)); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getStandForUser(String[] userId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = null;
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < userId.length; i++) {
        if (sb.indexOf(String.valueOf(userId[i]) + ",") < 0) {
          sb.append(String.valueOf(userId[i]) + ",");
          String[] tmp = { "", "", "", "" };
          tmp[0] = userId[i];
          rs = this.stmt.executeQuery("select empName from JSDB.org_employee where emp_id = " + userId[i]);
          if (rs.next())
            tmp[1] = rs.getString(1); 
          rs.close();
          Calendar now = Calendar.getInstance();
          String tmpSql = "";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "select proxyEmpId, proxyEmpName from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
              "proxyState = 1 and '" + now.get(1) + "/" + (now.get(2) + 1) + "/" + now.get(5) + "' between beginTime and endTime";
          } else {
            tmpSql = "select proxyEmpId, proxyEmpName from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
              "proxyState = 1 and JSDB.FN_STRTODATE('" + now.get(1) + "/" + (now.get(2) + 1) + "/" + now.get(5) + "', 'L') between beginTime and endTime";
          } 
          rs = this.stmt.executeQuery(tmpSql);
          if (rs.next()) {
            tmp[2] = rs.getString(1);
            tmp[3] = rs.getString(2);
          } 
          rs.close();
          alist.add(tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public void insertDealWith(String[] para) throws Exception {
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_dealwith_id from JSDB.jsf_dealwith where databasetable_id = " + para[0] + " and " + 
          "databaserecord_id = " + para[1] + " and activity_id = " + para[3] + " and curStepCount = " + 
          para[8]);
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId();
        int curActivityStatus = 0;
        if (para[6].equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = para[12];
        if (para[11].equals("0") && para[12].equals("0")) {
          rs = this.stmt.executeQuery("select subProcWorkId from JSDB.jsf_dealwith where databasetable_id = " + para[0] + 
              " and databaserecord_id = " + para[1]);
          if (rs.next())
            subProcWorkId = rs.getString("subProcWorkId"); 
          rs.close();
        } 
        this.stmt.executeUpdate("insert into JSDB.jsf_dealwith (wf_dealwith_id,databasetable_id,databaserecord_id,activityname,activity_id,nextActivityName,nextActivityId,curActivityStatus,curStepCount,activityClass,subProcWorkId) values ( " + 

            
            wf_dealwith_id + "," + para[0] + "," + para[1] + ",'" + para[2] + "'," + para[3] + ",'" + para[6] + 
            "'," + para[7] + "," + curActivityStatus + "," + para[8] + "," + para[11] + "," + subProcWorkId + ")");
      } else if (!para[7].equals("0")) {
        this.stmt.execute("update JSDB.jsf_dealwith set nextActivityId = " + para[7] + ", nextActivityName = '" + para[6] + "' " + 
            "where wf_dealwith_id = " + wf_dealwith_id);
      } 
      Date now = new Date();
      String commentField = "";
      if (para.length == 14)
        commentField = para[13]; 
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (para[9].equals("0")) {
        String wf_dealwithcomment_id = getTableId();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName,commentField ) values ( " + 
            
            wf_dealwithcomment_id + ", " + para[4] + ", '" + 
            para[5] + "', '" + now.toLocaleString() + "', " + 
            wf_dealwith_id + ", 0, '', '" + commentField + "')";
        } else {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName,commentField ) values ( " + 
            
            wf_dealwithcomment_id + ", " + para[4] + ", '" + 
            para[5] + "', JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''), " + 
            wf_dealwith_id + ", 0, '', '" + commentField + "')";
        } 
        this.stmt.executeUpdate(tmpSql);
      } else {
        String wf_dealwithcomment_id = getTableId();
        String empName = "";
        rs = this.stmt.executeQuery("select empname from JSDB.org_employee where emp_id = " + para[4]);
        if (rs.next())
          empName = rs.getString(1); 
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, isStandForComm, commentField ) values ( " + 
            
            wf_dealwithcomment_id + ", " + para[10] + ", '" + 
            para[5] + "', '" + now.toLocaleString() + "', " + 
            wf_dealwith_id + ", " + para[4] + ", '" + empName + "', 1, '" + commentField + "')";
        } else {
          tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, isStandForComm, commentField ) values ( " + 
            
            wf_dealwithcomment_id + ", " + para[10] + ", '" + 
            para[5] + "', JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''), " + 
            wf_dealwith_id + ", " + para[4] + ", '" + empName + "', 1, '" + commentField + "')";
        } 
        this.stmt.executeUpdate(tmpSql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void operateWork(String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) throws Exception {
    List<String[]> toUserList = null;
    if (para[11].equals("1"))
      toUserList = getStandForUser(transactUser); 
    String tmpSql = "";
    String databaseType = SystemCommon.getDatabaseType();
    begin();
    try {
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      String activityClass = para[16];
      String subProcType = para[17];
      String domainId = "0";
      ResultSet rs = this.stmt.executeQuery("SELECT DISTINCT DOMAIN_ID FROM JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + nextActivityId);
      if (rs.next())
        domainId = rs.getString(1); 
      rs.close();
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg from JSDB.JSF_WORK where jsf_work_id=" + 

        
        curWorkId;
      Date now = new Date();
      rs = this.stmt.executeQuery(sql);
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
      } 
      rs.close();
      StringBuffer workUser = new StringBuffer();
      for (int i = 0; i < transactUser.length; i++)
        workUser.append("$" + transactUser[i] + "$"); 
      this.stmt.execute("delete from JSDB.JSF_WORK where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          para[13] + " and workprocess_id=" + workProcess_id);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[13]);
      String remindFieldValue = para[8];
      int k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！").length() - 100;
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String standTitle = "";
      String workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      if (!para[18].equals(""))
        workTitle = para[18]; 
      if (para[12].equals("1")) {
        rs = this.stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[13]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        myTmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (myTmp.length() > 100)
          myTmp = myTmp.substring(0, 99); 
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + myTmp + "',WORKALLOWCANCEL=" + para[6] + ",WORKUSER='" + workUser.toString() + "' where workTable_Id=" + workTable_id + " and workRecord_id=" + 
            workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + 
            para[14]);
        standTitle = "（代" + para[15] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      this.stmt.execute("update JSDB.JSF_WORK set workstatus = 101, workTitle = '" + workTitle + "', " + 
          "workAllowCancel=" + para[6] + ", workUser = '" + workUser.toString() + "' " + 
          "where wf_work_id = " + curWorkId);
      int mycount = 0;
      if (para[9].equals("1")) {
        sql = " select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + workTable_id + 
          " and workRecord_id = " + workRecord_id + " and workStepCount = " + para[7] + 
          " and workstatus = 0";
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      int workListControl = 0;
      if (mycount == 0)
        if (activityClass.equals("1") || (activityClass.equals("0") && subProcType.equals("0")) || activityClass.equals("3")) {
          this.stmt.execute("update JSDB.JSF_WORK set workListControl = 1 where worktable_id = " + workTable_id + 
              " and workrecord_id = " + workRecord_id + " and workActivity = " + nextActivityId);
          this.stmt.execute("update JSDB.JSF_WORK set workCurStep='" + nextActivityName + "' where worktable_id=" + workTable_id + 
              " and workrecord_id = " + workRecord_id);
          workListControl = 1;
          this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus = 1 where databasetable_id = " + 
              workTable_id + " and databaserecord_id = " + workRecord_id + " and curStepCount = " + 
              para[7]);
          workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
          if (!para[18].equals(""))
            workTitle = para[18]; 
          if (workTitle.length() > 100)
            workTitle = workTitle.substring(0, 99); 
        }  
      if (para[11].equals("1")) {
        String[] standForUser = (String[])null;
        for (int j = 0; j < toUserList.size(); j++) {
          standForUser = toUserList.get(j);
          rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[0] + " and worktable_id=" + 
              workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
              nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1));
          rs.next();
          int sameWorkCount = rs.getInt(1);
          rs.close();
          if (sameWorkCount == 0) {
            if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
              workTitle = String.valueOf(remindFieldValue) + workFileType;
            } else {
              workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
            } 
            if (!para[18].equals(""))
              workTitle = para[18]; 
            if (workTitle.length() > 100)
              workTitle = workTitle.substring(0, 99); 
            String workId = getTableId();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id) values ( " + 




                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "','" + workSubmitTime + "'," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",'" + now.toLocaleString() + 
                "'," + (Integer.parseInt(para[7]) + 1) + ",0,0,''," + nextActivityId + 
                "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + 
                submitOrg + "'," + domainId + ")";
            } else {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id) values ( " + 




                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",JSDB.FN_STRTODATE('" + now.toLocaleString() + 
                "','')," + (Integer.parseInt(para[7]) + 1) + ",0,0,''," + nextActivityId + 
                "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + 
                submitOrg + "'," + domainId + ")";
            } 
            this.stmt.executeUpdate(tmpSql);
          } 
          if (!standForUser[2].equals("")) {
            rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[2] + " and worktable_id=" + 
                workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
                nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1) + " and standForUserId=" + 
                standForUser[0]);
            rs.next();
            sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId();
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType + "（代" + standForUser[1] + "办理）";
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！" + "（代" + standForUser[1] + "办理）";
              } 
              if (!para[18].equals(""))
                workTitle = para[18]; 
              if (workTitle.length() > 100)
                workTitle = workTitle.substring(0, 99); 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id) values ( " + 




                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "','" + workSubmitTime + "'," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",'" + now.toLocaleString() + "'," + (
                  Integer.parseInt(para[7]) + 1) + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ")";
              } else {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id) values ( " + 




                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + (
                  Integer.parseInt(para[7]) + 1) + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ")";
              } 
              this.stmt.executeUpdate(tmpSql);
            } 
          } 
        } 
      } else {
        String tmpUser = "";
        String insertUser = "";
        for (int j = 0; j < transactUser.length; j++) {
          tmpUser = transactUser[j];
          if (insertUser.indexOf(String.valueOf(tmpUser) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + tmpUser + ",";
            if (tmpUser.startsWith("$"))
              tmpUser = tmpUser.substring(1); 
            if (tmpUser.endsWith("$"))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + (Integer.parseInt(para[7]) + 1));
            rs.next();
            int sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId();
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType;
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
              } 
              if (!para[18].equals(""))
                workTitle = para[18]; 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,isStandForWork ) values ( " + 







                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "','" + 
                  workSubmitTime + "'," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", '" + 
                  now.toLocaleString() + "', " + (
                  Integer.parseInt(para[7]) + 1) + "," + nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",0)";
              } else {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,isStandForWork ) values ( " + 







                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                  workSubmitTime + "','')," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                  now.toLocaleString() + "',''), " + (
                  Integer.parseInt(para[7]) + 1) + "," + nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",0)";
              } 
              this.stmt.executeUpdate(tmpSql);
            } 
          } 
        } 
      } 
      if (!needPassRound.equals("") && passRoundUser != null) {
        boolean flag = true;
        String tmpUser = "";
        String insertUser = "";
        for (int m = 0; m < passRoundUser.length; m++) {
          if (insertUser.indexOf(String.valueOf(passRoundUser[m]) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + passRoundUser[m] + ",";
            tmpUser = passRoundUser[m];
            if (!tmpUser.equals("")) {
              if (tmpUser.startsWith("$"))
                tmpUser = tmpUser.substring(1); 
              if (tmpUser.endsWith("$"))
                tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
              for (int j = 0; j < transactUser.length && 
                !tmpUser.equals(transactUser[j]); j++);
              if (flag) {
                String workId = getTableId();
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您的审阅！";
                if (!para[18].equals(""))
                  workTitle = para[18]; 
                if (databaseType.indexOf("mysql") >= 0) {
                  tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id ,isStandForWork ) values ( " + 







                    
                    workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                    nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                    nextActivityId + ",'" + workSubmitPerson + "','" + 
                    workSubmitTime + "'," + wf_submitEmployee_id + 
                    ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                    "," + deadLineTime + "," + pressTime + ", '" + 
                    now.toLocaleString() + "', " + (
                    Integer.parseInt(para[7]) + 1) + "," + nextActivityId + ", " + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",0)";
                } else {
                  tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id ,isStandForWork ) values ( " + 







                    
                    workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                    nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                    nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                    workSubmitTime + "','')," + wf_submitEmployee_id + 
                    ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                    "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                    now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + (
                    Integer.parseInt(para[7]) + 1) + "," + nextActivityId + ", " + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",0)";
                } 
                this.stmt.executeUpdate(tmpSql);
              } 
            } 
          } 
        } 
      } 
      if (mycount == 0)
        this.stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + nextActivityName + "', workactivity = " + 
            nextActivityId + " where worktable_id = " + workTable_id + " and " + 
            " workrecord_id = " + workRecord_id); 
      if (mycount == 0 && activityClass.equals("0") && subProcType.equals("0"))
        this.stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStepCount = " + (
            Integer.parseInt(para[7]) + 1)); 
      if (para[9].equals("0") || (activityClass.equals("0") && subProcType.equals("0"))) {
        workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
        if (!para[18].equals(""))
          workTitle = para[18]; 
        this.stmt.execute("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + 
            " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public String completeWork(String[] para, String workId) throws Exception {
    begin();
    String tmpSql = "";
    String databaseType = SystemCommon.getDatabaseType();
    Date now = new Date();
    String submitPersonLogin = "";
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_curemployee_id,workRecord_id,workTable_id,workProcess_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId from JSDB.JSF_WORK where wf_work_id=" + 
          workId);
      String workRecord_id = "", workTable_id = "", wf_curemployee_id = "", workProcess_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "", pareTableId = "";
      String pareRecordId = "", pareProcNextActiId = "";
      if (rs.next()) {
        wf_curemployee_id = rs.getString("wf_curemployee_id");
        workRecord_id = rs.getString("workRecord_id");
        workTable_id = rs.getString("workTable_id");
        workProcess_id = rs.getString("workProcess_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
      } 
      rs.close();
      this.stmt.execute("delete from JSDB.JSF_WORK where workstatus=101 and worktable_id=" + workTable_id + 
          " and workrecord_id=" + workRecord_id + " and wf_curemployee_id= " + 
          wf_curemployee_id + " and workprocess_id=" + workProcess_id);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[9] + " and workprocess_id = " + workProcess_id);
      String standTitle = "";
      String workTitle = String.valueOf(para[4]) + "的" + para[5] + para[3] + "您已办理完毕！";
      if (!para[12].equals(""))
        workTitle = para[12]; 
      if (para[8].equals("1")) {
        rs = this.stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[9]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        String tmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (tmp.length() > 100)
          tmp = tmp.substring(0, 99); 
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + tmp + "',WORKALLOWCANCEL=0 where workTable_Id=" + workTable_id + " and workRecord_id=" + 
            workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + para[10]);
        standTitle = "（代" + para[11] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      String sql = "update JSDB.JSF_WORK set workStatus=101, workTitle='" + workTitle + "'," + 
        "workAllowCancel=0 where wf_work_id=" + workId;
      this.stmt.execute(sql);
      int mycount = 0;
      if (para[6].equals("1")) {
        sql = "select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + para[0] + 
          " and workrecord_id = " + para[1] + " and workactivity = " + para[2] + 
          " and workstatus = 0";
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      if (mycount == 0) {
        rs = this.stmt.executeQuery("select userAccounts from JSDB.org_employee,JSDB.JSF_WORK where emp_id=wf_submitemployee_id and worktable_id=" + 
            para[0] + " and workrecord_id=" + para[1] + " and workstartflag=1");
        if (rs.next())
          submitPersonLogin = rs.getString(1); 
        rs.close();
        workTitle = "您的" + para[5] + para[3] + "已办理完毕！";
        if (!para[12].equals(""))
          workTitle = para[12]; 
        if (workTitle.length() > 100)
          workTitle = workTitle.substring(0, 99); 
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "update JSDB.JSF_WORK set workstatus=100,workTitle='" + workTitle + "',workDoneWithDate='" + 
            now.toLocaleString() + "' where worktable_id=" + para[0] + " and " + 
            "workrecord_id=" + para[1] + " and workstartflag=1";
        } else {
          sql = "update JSDB.JSF_WORK set workstatus=100,workTitle='" + workTitle + "',workDoneWithDate=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "','') where worktable_id=" + para[0] + " and " + 
            "workrecord_id=" + para[1] + " and workstartflag=1";
        } 
        this.stmt.execute(sql);
      } 
      if (para[6].equals("0")) {
        workTitle = String.valueOf(para[4]) + "的" + para[5] + para[3] + "已办理完毕！";
        if (!para[12].equals(""))
          workTitle = para[12]; 
        if (workTitle.length() > 100)
          workTitle = workTitle.substring(0, 99); 
        this.stmt.execute("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + workTitle + "',workAllowCancel = 0 " + 
            "where (workStatus = 0 or workStatus = 2) and workTable_id = " + para[0] + 
            " and workRecord_id = " + para[1] + " and workStepCount = " + 
            para[7] + " and wf_work_id <> " + workId);
      } 
      if (mycount == 0 || para[6].equals("0")) {
        if (para.length > 13 && 
          para[13].equals("information"))
          this.stmt.execute("update JSDB.oa_information set informationStatus = 0 where information_id=" + para[1]); 
        this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus=1 where databasetable_id=" + para[0] + " and " + 
            "databaserecord_id=" + para[1] + " and activity_id=" + para[2]);
        if (isSubProcWork.equals("1")) {
          this.stmt.execute("update JSDB.jsf_work set workListControl=1 where worktable_id=" + pareTableId + " and workRecord_id=" + 
              pareRecordId + " and workStepCount=" + (Integer.parseInt(pareStepCount) + 1) + " and " + 
              "workActivity=" + pareProcNextActiId);
          this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus=1 where databasetable_id=" + pareTableId + 
              " and databaserecord_id=" + pareRecordId + " and activity_id=" + pareProcActiId + " and " + 
              "curStepCount=" + pareStepCount);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return submitPersonLogin;
  }
  
  public void insertPassRoundDeal(String[] para) throws Exception {
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_dealwith_id from JSDB.jsf_dealwith where databasetable_id = " + para[0] + " and " + 
          "databaserecord_id = " + para[1] + " and activity_id = " + para[3] + " and curStepCount = " + 
          para[6]);
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId();
        this.stmt.execute("insert into JSDB.jsf_dealwith ( wf_dealwith_id, databasetable_id, databaserecord_id, activityname, activity_id, curActivityStatus, curStepCount ) values ( " + 
            
            wf_dealwith_id + "," + para[0] + "," + para[1] + ",'" + para[2] + "'," + para[3] + ",0," + para[6] + ")");
      } 
      String wf_dealwithcomment_id = getTableId();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, commentField ) values ( " + 
          
          wf_dealwithcomment_id + ", " + para[4] + ", '" + 
          para[5] + "', '" + para[7] + "', " + 
          wf_dealwith_id + ", 0, '', '" + para[8] + "')";
      } else {
        tmpSql = "insert into JSDB.jsf_dealwithcomment ( wf_dealwithcomment_id, dealwithemployee_id, dealwithemployeecomment, dealwithtime, wf_dealwith_id, standForUserId, standForUserName, commentField ) values ( " + 
          
          wf_dealwithcomment_id + ", " + para[4] + ", '" + 
          para[5] + "', JSDB.FN_STRTODATE('" + para[7] + "',''), " + 
          wf_dealwith_id + ", 0, '', '" + para[8] + "')";
      } 
      this.stmt.executeUpdate(tmpSql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void operPassRoundWork(String[] para) throws Exception {
    begin();
    try {
      String sql = "select workFileType, workSubmitPerson, workProcess_id, workTable_id, workRecord_id from JSDB.JSF_WORK where wf_work_id = " + 
        
        para[0];
      ResultSet rs = this.stmt.executeQuery(sql);
      String fileType = "", submitPerson = "";
      if (rs.next()) {
        fileType = rs.getString(1);
        submitPerson = rs.getString(2);
      } 
      rs.close();
      String workTitle = String.valueOf(submitPerson) + "的" + para[1] + fileType + "您已审阅完毕！";
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      if (para[2].equals("")) {
        this.stmt.executeUpdate("update JSDB.JSF_WORK set workstatus=102,workTitle='" + workTitle + "' " + 
            "where wf_work_id=" + para[0]);
      } else {
        this.stmt.executeUpdate("update JSDB.JSF_WORK set workstatus=102,workTitle='" + workTitle + "' " + 
            ", workmainlinkfile='" + para[2] + "' where wf_work_id=" + para[0]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void transitionWork(String[] para, String[] user) throws Exception {
    begin();
    Date now = new Date();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      int k;
      String workFileType = "", workCurStep = "", workActivity = "", workSubmitPerson = "";
      String workSubmitTime = "", wf_submitEmployee_id = "", workType = "", workProcess_id = "";
      String workTable_id = "", workRecord_id = "", workDeadLine = "", workPressTime = "";
      String initActivity = "", initStepCount = "", workallowcancel = "", submitOrg = "", isSubProcWork = "0", workStepCount = "";
      String pareProcActiId = "", pareStepCount = "", pareTableId = "", pareRecordId = "", pareProcNextActiId = "", curTransactType = "";
      String domain_id = "", emergence = "", INITACTIVITYNAME = "", standForUserId = "", standForUserName = "", isStandForWork = "", dealTips = "";
      String workTitle = "", mainLinkFile = "";
      Date workDeadlineDate = now, workDeadlinePressDate = now;
      int isParallel = 0, parallelStep = 0;
      String parallelId = "0", parallelCurActId = "0", parallelFromWork = "0";
      String processDeadlineDate = "null";
      String wf_curemployee_id = "";
      String sql = "select workFileType,workCurStep,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workType,workProcess_id,workTable_id,workRecord_id,workDeadLine,workPressTime,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,wf_curemployee_id,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,workStepCount,processDeadlineDate,worktitle,workmainLinkFile,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork from JSDB.JSF_WORK where wf_work_id=" + 








        
        para[0];
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workCurStep = rs.getString("workCurStep");
        workActivity = rs.getString("workActivity");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workType = rs.getString("workType");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        workDeadLine = rs.getString("workDeadLine");
        workPressTime = rs.getString("workPressTime");
        initActivity = rs.getString("initActivity");
        initStepCount = rs.getString("initStepCount");
        workallowcancel = rs.getString("workallowcancel");
        submitOrg = rs.getString("submitOrg");
        isSubProcWork = rs.getString("isSubProcWork");
        wf_curemployee_id = rs.getString("wf_curemployee_id");
        workStepCount = rs.getString("workStepCount");
        standForUserId = (rs.getString("standForUserId") == null) ? "0" : rs.getString("standForUserId");
        standForUserName = rs.getString("standForUserName");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        domain_id = rs.getString("domain_id");
        emergence = rs.getString("emergence");
        curTransactType = (rs.getString("transactType") == null) ? "" : rs.getString("transactType");
        INITACTIVITYNAME = rs.getString("INITACTIVITYNAME");
        isStandForWork = rs.getString("isStandForWork");
        dealTips = (rs.getString("dealTips") == null) ? "" : rs.getString("dealTips");
        workDeadlineDate = (rs.getTimestamp("workDeadlineDate") == null) ? now : rs.getTimestamp("workDeadlineDate");
        workDeadlinePressDate = (rs.getTimestamp("workDeadlinePressDate") == null) ? now : rs.getTimestamp("workDeadlinePressDate");
        if (databaseType.indexOf("mysql") >= 0) {
          processDeadlineDate = (rs.getDate("processDeadlineDate") == null) ? "null" : ("'" + rs.getTimestamp("processDeadlineDate").toLocaleString() + "'");
        } else {
          processDeadlineDate = (rs.getDate("processDeadlineDate") == null) ? "null" : ("JSDB.FN_STRTODATE('" + rs.getTimestamp("processDeadlineDate").toLocaleString() + "','L')");
        } 
        workTitle = rs.getString("worktitle");
        mainLinkFile = rs.getString("workmainLinkFile");
        isParallel = rs.getInt("is_parallel");
        parallelId = rs.getString("parallel_id");
        parallelStep = rs.getInt("parallel_step");
        parallelCurActId = rs.getString("parallel_curactid");
        parallelFromWork = rs.getString("parallel_fromwork");
      } 
      rs.close();
      List<String[]> toUserList = getStandForUserByProcessAndOrgWithConn(user, workProcess_id, wf_submitEmployee_id, this.conn, this.stmt);
      String curWorkUser = "";
      sql = "select wf_curemployee_id from JSDB.JSF_WORK where workProcess_id=" + workProcess_id + 
        " and workTable_id=" + workTable_id + " and workRecord_id=" + workRecord_id + " and workStepCount=" + workStepCount + " and WORKDELETE <>1 and workstatus = 0";
      rs = this.stmt.executeQuery(sql);
      while (rs.next())
        curWorkUser = String.valueOf(curWorkUser) + "$" + rs.getString("wf_curemployee_id") + "$"; 
      rs.close();
      String remindFieldValue = para[2];
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        k = (String.valueOf(remindFieldValue) + workFileType).length() - 50;
      } else {
        k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！").length() - 50;
      } 
      if (k > 0)
        remindFieldValue = remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String tmpTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        tmpTitle = String.valueOf(remindFieldValue) + workFileType;
      } else {
        tmpTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
      } 
      if (!para[4].equals(""))
        tmpTitle = para[4]; 
      if ("transend".equals(para[4])) {
        tmpTitle = workTitle;
        para[3] = mainLinkFile;
      } 
      int newWorkId = 0;
      StringBuffer workUser = new StringBuffer();
      String actionType = para[5].equals("tranByReturn") ? "1" : "0";
      List<MessagesVO> msgList = new ArrayList();
      MessagesVO msgVO = new MessagesVO();
      String[] standForUser = (String[])null;
      for (int i = toUserList.size() - 1; i >= 0; i--) {
        standForUser = toUserList.get(i);
        if (standForUser[0] != null && !standForUser[0].equals("")) {
          if (curWorkUser.indexOf("$" + standForUser[0] + "$") != -1)
            continue; 
          workUser.append("$" + standForUser[0] + "$");
          newWorkId = Integer.parseInt(getTableId());
          if (databaseType.indexOf("mysql") >= 0) {
            sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,tranType, tranFromPersonId,processDeadlineDate,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork) values (" + 









              
              newWorkId + "," + standForUser[0] + ",0,'" + workFileType + "','" + workCurStep + 
              "','" + tmpTitle + "','','" + para[3] + "',1," + workActivity + ",'" + workSubmitPerson + 
              "','" + workSubmitTime + "'," + 
              wf_submitEmployee_id + ",0," + workType + "," + workProcess_id + "," + 
              workTable_id + "," + workRecord_id + "," + workDeadLine + "," + workPressTime + 
              ",'" + now.toLocaleString() + "',0,1," + 
              para[1] + "," + initActivity + "," + initStepCount + ", " + workallowcancel + ",'" + submitOrg + "'," + isSubProcWork + ",'" + 
              standForUserId + "','" + standForUserName + "','" + pareProcActiId + "','" + 
              pareStepCount + "','" + pareTableId + "','" + pareRecordId + "','" + pareProcNextActiId + "','" + domain_id + "','" + emergence + "','" + 
              curTransactType + "','" + INITACTIVITYNAME + "','" + isStandForWork + "','" + dealTips + "','" + workDeadlineDate.toLocaleString() + "','" + 
              workDeadlinePressDate.toLocaleString() + "','" + actionType + "','" + wf_curemployee_id + "'," + processDeadlineDate + 
              "," + isParallel + "," + parallelId + "," + parallelStep + "," + parallelCurActId + "," + parallelFromWork + 
              ")";
          } else {
            sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,tranType, tranFromPersonId,processDeadlineDate,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork) values (" + 










              
              newWorkId + "," + standForUser[0] + ",0,'" + workFileType + "','" + workCurStep + 
              "','" + tmpTitle + "','','" + para[3] + "',1," + workActivity + ",'" + workSubmitPerson + 
              "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
              wf_submitEmployee_id + ",0," + workType + "," + workProcess_id + "," + 
              workTable_id + "," + workRecord_id + "," + workDeadLine + "," + workPressTime + 
              ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''),0,1," + 
              para[1] + "," + initActivity + "," + initStepCount + ", " + workallowcancel + ",'" + submitOrg + "'," + isSubProcWork + "," + 
              standForUserId + ",'" + standForUserName + "'," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + "," + domain_id + ",'" + emergence + "','" + 
              curTransactType + "','" + INITACTIVITYNAME + "'," + isStandForWork + ",'" + dealTips + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L')," + 
              "JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + actionType + ",'" + wf_curemployee_id + "'," + processDeadlineDate + 
              "," + isParallel + "," + parallelId + "," + parallelStep + "," + parallelCurActId + "," + parallelFromWork + 
              ")";
          } 
          msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
          msgVO.setMessage_send_UserName(workSubmitPerson);
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(tmpTitle);
          msgVO.setMessage_toUserId(Long.parseLong(standForUser[0]));
          msgVO.setMessage_type("jsflow");
          msgVO.setData_id(newWorkId);
          msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + newWorkId);
          msgList.add(msgVO);
          this.stmt.executeUpdate(sql);
        } 
        if (!standForUser[2].equals("")) {
          newWorkId = Integer.parseInt(getTableId());
          if (databaseType.indexOf("mysql") >= 0) {
            sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,isStandForWork, standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,tranType, tranFromPersonId,processDeadlineDate,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork) values (" + 










              
              newWorkId + "," + standForUser[2] + ",0,'" + workFileType + "','" + workCurStep + 
              "','" + tmpTitle + "','','" + para[3] + "',1," + workActivity + ",'" + workSubmitPerson + 
              "','" + workSubmitTime + "'," + 
              wf_submitEmployee_id + ",0," + workType + "," + workProcess_id + "," + 
              workTable_id + "," + workRecord_id + "," + workDeadLine + "," + workPressTime + 
              ",'" + now.toLocaleString() + "',0,1," + 
              para[1] + "," + initActivity + "," + initStepCount + ", " + workallowcancel + ",'" + submitOrg + "'," + isSubProcWork + ",',1," + 
              standForUser[0] + "','" + standForUser[1] + "','" + pareProcActiId + "','" + 
              pareStepCount + "','" + pareTableId + "','" + pareRecordId + "','" + pareProcNextActiId + "','" + domain_id + "','" + emergence + "','" + 
              curTransactType + "','" + INITACTIVITYNAME + "','" + dealTips + "','" + workDeadlineDate.toLocaleString() + "','" + 
              workDeadlinePressDate.toLocaleString() + "','" + actionType + "','" + wf_curemployee_id + "'," + processDeadlineDate + 
              "," + isParallel + "," + parallelId + "," + parallelStep + "," + parallelCurActId + "," + parallelFromWork + 
              ")";
          } else {
            sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,isStandForWork, standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,tranType, tranFromPersonId,processDeadlineDate,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork) values (" + 










              
              newWorkId + "," + standForUser[2] + ",0,'" + workFileType + "','" + workCurStep + 
              "','" + tmpTitle + "','','" + para[3] + "',1," + workActivity + ",'" + workSubmitPerson + 
              "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
              wf_submitEmployee_id + ",0," + workType + "," + workProcess_id + "," + 
              workTable_id + "," + workRecord_id + "," + workDeadLine + "," + workPressTime + 
              ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''),0,1," + 
              para[1] + "," + initActivity + "," + initStepCount + ", " + workallowcancel + ",'" + submitOrg + "'," + isSubProcWork + ",1," + 
              standForUser[0] + ",'" + standForUser[1] + "'," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + "," + domain_id + ",'" + emergence + "','" + 
              curTransactType + "','" + INITACTIVITYNAME + "','" + dealTips + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L')," + 
              "JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + actionType + ",'" + wf_curemployee_id + "'," + processDeadlineDate + 
              "," + isParallel + "," + parallelId + "," + parallelStep + "," + parallelCurActId + "," + parallelFromWork + 
              ")";
          } 
          msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
          msgVO.setMessage_send_UserName(workSubmitPerson);
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(tmpTitle);
          msgVO.setMessage_toUserId(Long.parseLong(standForUser[2]));
          msgVO.setMessage_type("jsflow");
          msgVO.setData_id(newWorkId);
          msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + newWorkId);
          msgList.add(msgVO);
          this.stmt.executeUpdate(sql);
        } 
        continue;
      } 
      if (msgList.size() > 0) {
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.messageArrayAdd(msgList);
      } 
      tmpTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      if (!para[4].equals(""))
        tmpTitle = para[4]; 
      if ("transend".equals(para[4]))
        tmpTitle = workTitle; 
      int hasDoneFile = 0;
      rs = this.stmt.executeQuery("select count(wf_work_id) from jsf_work where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          wf_curemployee_id + " and workprocess_id=" + workProcess_id);
      if (rs.next())
        hasDoneFile = rs.getInt(1); 
      rs.close();
      if ("last".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        this.stmt.execute("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + workTable_id + 
            " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
            wf_curemployee_id + " and workprocess_id=" + workProcess_id); 
      sql = "update JSDB.JSF_WORK set workstatus = 101, workTitle = '" + tmpTitle + "', workUser = '" + 
        workUser.toString() + "', workallowcancel = " + workallowcancel;
      if ("first".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        sql = String.valueOf(sql) + ", WORKDELETE =1"; 
      if (wf_curemployee_id.equals(para[6]))
        if (databaseType.indexOf("mysql") >= 0) {
          sql = String.valueOf(sql) + ",dealwithtime='" + now.toLocaleString() + "'";
        } else {
          sql = String.valueOf(sql) + ",dealwithtime=JSDB.FN_STRTODATE('" + now.toLocaleString() + "','') ";
        }  
      sql = String.valueOf(sql) + " where wf_work_id = " + para[0];
      if (!"".equals(workUser.toString()) && workUser.toString().indexOf("$" + wf_curemployee_id + "$") == -1)
        this.stmt.executeUpdate(sql); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public List getOrg(String domainId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("select org_Id, orgNameString from JSDB.org_organization where orgstatus=0 and domain_id=" + domainId + " order by orgIdString");
      while (rs.next()) {
        String[] tmp = { rs.getString(1), rs.getString(2) };
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getRole(String domainId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("select role_Id, roleName from JSDB.org_role where domain_id=" + domainId);
      while (rs.next()) {
        String[] tmp = { rs.getString(1), rs.getString(2) };
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getRoleUser(String roleUserString, String submitPersonId) throws Exception {
    begin();
    ArrayList<String> alist = new ArrayList();
    String sql = "";
    try {
      ResultSet rs = null;
      rs = this.stmt.executeQuery("select org_organization.org_Id, org_organization.orgIdString from JSDB.org_organization, org_organization_user where org_organization.org_Id = org_organization_user.org_Id and org_organization_user.emp_id=" + 
          submitPersonId);
      String orgId = "", orgIdString = "";
      if (rs.next()) {
        orgId = rs.getString(1);
        orgIdString = rs.getString(2);
      } 
      if (roleUserString.indexOf("and") > 0) {
        String roleId = roleUserString.split("and")[0];
        String roleOrg = roleUserString.split("and")[1];
        if (roleOrg.equals("-1")) {
          sql = "select distinct org_user_role.emp_id from JSDB.org_user_role, JSDB.org_organization_user where org_organization_user.emp_id=org_user_role.emp_id and org_user_role.role_id=" + 
            
            roleId + " and org_organization_user.org_id=" + orgId;
        } else if (roleOrg.equals("-2")) {
          String tmpSql = "";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "select distinct org_user_role.emp_id from JSDB.org_user_role, JSDB.org_organization_user where org_organization_user.emp_id=org_user_role.emp_id and org_user_role.role_id=" + 
              
              roleId + " and '" + orgIdString + "' like concat('%$', org_organization_user.org_id, '$%') ";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = "select distinct org_user_role.emp_id from JSDB.org_user_role, JSDB.org_organization_user where org_organization_user.emp_id=org_user_role.emp_id and org_user_role.role_id=" + 
              
              roleId + " and '" + orgIdString + "' locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(org_organization_user.org_id)), '$')>0 ";
          } else {
            sql = "select distinct org_user_role.emp_id from JSDB.org_user_role, JSDB.org_organization_user where org_organization_user.emp_id=org_user_role.emp_id and org_user_role.role_id=" + 
              
              roleId + " and '" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org_organization_user.org_id)), '$%') ";
          } 
        } else if (roleOrg.equals("-3")) {
          StringBuffer sb = new StringBuffer();
          rs = this.stmt.executeQuery("select org_Id from JSDB.org_organization where orgIdString like '%$" + orgId + "$%'");
          while (rs.next())
            sb.append(rs.getString(1)); 
          rs.close();
          sql = "select distinct org_user_role.emp_id from JSDB.org_user_role, org_organization_user where org_organization_user.emp_id=org_user_role.emp_id and org_user_role.role_id=" + 
            
            roleId + " and org_organization_user.org_id in (" + sb.toString().substring(0, sb.toString().length() - 1) + ")";
        } else if (Long.parseLong(roleOrg) > 0L) {
          sql = "select distinct org_user_role.emp_id from JSDB.org_user_role, org_organization_user where org_organization_user.emp_id=org_user_role.emp_id and org_user_role.role_id=" + 
            
            roleId + " and org_organization_user.org_id=" + roleOrg;
        } 
      } else {
        sql = "select distinct emp_id from JSDB.org_user_role where role_id=" + roleUserString;
      } 
      rs = this.stmt.executeQuery(sql);
      while (rs.next())
        alist.add(rs.getString(1)); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String[] getActivityClass(String tableId, String recordId, String activityId, String subProcWorkId) throws Exception {
    String[] actiClass = { "", "", "", "", "", "", "", "" };
    begin();
    String sql = "";
    try {
      sql = "select activityClass,activitySubProc,subProcType from JSDB.jsf_p_activity where ttable_id=" + 
        tableId + " and trecord_id=" + recordId + " and wf_activity_id=" + activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        actiClass[0] = rs.getString("activityClass");
        actiClass[1] = rs.getString("activitySubProc");
        actiClass[2] = rs.getString("subProcType");
      } 
      rs.close();
      if (actiClass[0].equals("0")) {
        sql = "select accessDatabaseId,workflowprocessname,processType,remindField from JSDB.jsf_workflowprocess where wf_workflowprocess_id=" + actiClass[1];
        rs = this.stmt.executeQuery(sql);
        if (rs.next()) {
          actiClass[3] = rs.getString(1);
          actiClass[4] = rs.getString(2);
          actiClass[5] = rs.getString(3);
          actiClass[6] = rs.getString(4);
        } 
        rs.close();
        sql = "SELECT workstatus FROM jsf_work WHERE workprocess_id=" + actiClass[1] + " AND workstartflag=1 AND worktable_id=" + actiClass[3] + " AND wf_work_id=" + subProcWorkId;
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          actiClass[7] = rs.getString(1); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return actiClass;
  }
  
  public String[] getActivityClass(String tableId, String recordId, String activityId) throws Exception {
    String[] actiClass = { "", "", "", "", "", "", "", "1" };
    begin();
    String sql = "";
    try {
      sql = "select activityClass,activitySubProc,subProcType from JSDB.jsf_p_activity where ttable_id=" + 
        tableId + " and trecord_id=" + recordId + " and wf_activity_id=" + activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        actiClass[0] = rs.getString("activityClass");
        actiClass[1] = rs.getString("activitySubProc");
        actiClass[2] = rs.getString("subProcType");
      } 
      rs.close();
      if (actiClass[0].equals("0")) {
        sql = "select accessDatabaseId,workflowprocessname,processType,remindField from JSDB.jsf_workflowprocess where wf_workflowprocess_id=" + actiClass[1];
        rs = this.stmt.executeQuery(sql);
        if (rs.next()) {
          actiClass[3] = rs.getString(1);
          actiClass[4] = rs.getString(2);
          actiClass[5] = rs.getString(3);
          actiClass[6] = rs.getString(4);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return actiClass;
  }
  
  public Long setSurveillance2(WorkVO workVO, String resubmitWorkId) throws Exception {
    begin();
    String sql = "";
    try {
      String creatorCancelLink = workVO.getCreatorCancelLink();
      creatorCancelLink = creatorCancelLink.replaceAll("workIdValue", resubmitWorkId);
      if (creatorCancelLink.indexOf("'") >= 0)
        creatorCancelLink = creatorCancelLink.replace('\'', '"'); 
      String selfTitle = "您的" + workVO.getRemindValue() + workVO.getFileType() + "正在办理中！";
      if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals(""))
        selfTitle = workVO.getDocTitle(); 
      if (selfTitle.length() > 100)
        selfTitle = selfTitle.substring(0, 99); 
      Date now = new Date();
      String isSubProcWork = "0", pareProcActiId = "0", pareStepCount = "0";
      String pareTableId = "0", pareRecordId = "0", pareProcNextActiId = "0";
      if (workVO.getIsSubProcWork() != null)
        isSubProcWork = workVO.getIsSubProcWork(); 
      if (workVO.getPareProcActiId() != null)
        pareProcActiId = workVO.getPareProcActiId(); 
      if (workVO.getPareStepCount() != null)
        pareStepCount = workVO.getPareStepCount(); 
      if (workVO.getPareTableId() != null)
        pareTableId = workVO.getPareTableId(); 
      if (workVO.getPareRecordId() != null)
        pareRecordId = workVO.getPareRecordId(); 
      if (workVO.getPareProcNextActiId() != null)
        pareProcNextActiId = workVO.getPareProcNextActiId(); 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "update JSDB.JSF_WORK set wf_curemployee_id=" + workVO.getSubmitEmployeeId() + ",workstatus=1," + 
          "workfiletype='" + workVO.getFileType() + "',workcurstep='" + workVO.getCurStep() + "'," + 
          "worktitle='" + selfTitle + "',workleftlinkfile='',workmainlinkfile='" + 
          workVO.getSelfMainLinkFile() + "',worklistcontrol=1,workactivity=" + workVO.getActivity() + 
          ",worksubmitperson='" + workVO.getSubmitPerson() + "',worksubmittime='" + now.toLocaleString() + 
          "',wf_submitemployee_id=" + workVO.getSubmitEmployeeId() + "," + 
          "workreadmarker=0,workprocess_id=" + workVO.getProcessId() + ",worktable_id=" + workVO.getTableId() + 
          ",workrecord_id=" + workVO.getRecordId() + ",workdeadline=" + workVO.getDeadLine() + "," + 
          "workpresstime=" + workVO.getPressTime() + ",workcreatedate='" + now.toLocaleString() + 
          "',workType=1,workstartflag=1,workAllowCancel=" + workVO.getAllowCancel() + 
          ",workStepCount=0," + "creatorCancelLink='" + creatorCancelLink + "',isSubProcWork=" + 
          isSubProcWork + ",pareProcActiId=" + pareProcActiId + ",pareStepCount=" + pareStepCount + 
          ",pareTableId=" + pareTableId + ",pareRecordId=" + pareRecordId + ",pareProcNextActiId=" + 
          pareProcNextActiId + " where wf_work_id=" + resubmitWorkId;
      } else {
        sql = "update JSDB.JSF_WORK set wf_curemployee_id=" + workVO.getSubmitEmployeeId() + ",workstatus=1," + 
          "workfiletype='" + workVO.getFileType() + "',workcurstep='" + workVO.getCurStep() + "'," + 
          "worktitle='" + selfTitle + "',workleftlinkfile='',workmainlinkfile='" + 
          workVO.getSelfMainLinkFile() + "',worklistcontrol=1,workactivity=" + workVO.getActivity() + 
          ",worksubmitperson='" + workVO.getSubmitPerson() + "',worksubmittime=JSDB.FN_STRTODATE('" + now.toLocaleString() + 
          "',''),wf_submitemployee_id=" + workVO.getSubmitEmployeeId() + "," + 
          "workreadmarker=0,workprocess_id=" + workVO.getProcessId() + ",worktable_id=" + workVO.getTableId() + 
          ",workrecord_id=" + workVO.getRecordId() + ",workdeadline=" + workVO.getDeadLine() + "," + 
          "workpresstime=" + workVO.getPressTime() + ",workcreatedate=JSDB.FN_STRTODATE('" + now.toLocaleString() + 
          "',''),workType=1,workstartflag=1,workAllowCancel=" + workVO.getAllowCancel() + 
          ",workStepCount=0," + "creatorCancelLink='" + creatorCancelLink + "',isSubProcWork=" + 
          isSubProcWork + ",pareProcActiId=" + pareProcActiId + ",pareStepCount=" + pareStepCount + 
          ",pareTableId=" + pareTableId + ",pareRecordId=" + pareRecordId + ",pareProcNextActiId=" + 
          pareProcNextActiId + " where wf_work_id=" + resubmitWorkId;
      } 
      this.stmt.execute(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return new Long(resubmitWorkId);
  }
  
  public void operateWorkDoc(String[] para, String[] transactUser, String needPassRound, String[] passRoundUser, String docTitle) throws Exception {
    List<String[]> toUserList = null;
    if (para[11].equals("1"))
      toUserList = getStandForUser(transactUser); 
    begin();
    try {
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      String activityClass = para[16];
      String subProcType = para[17];
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg from JSDB.JSF_WORK where wf_work_id=" + 
        
        curWorkId;
      Date now = new Date();
      ResultSet rs = this.stmt.executeQuery(sql);
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
      } 
      rs.close();
      StringBuffer workUser = new StringBuffer();
      for (int i = 0; i < transactUser.length; i++)
        workUser.append("$" + transactUser[i] + "$"); 
      this.stmt.execute("delete from JSDB.JSF_WORK where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          para[13] + " and workprocess_id=" + workProcess_id);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[13]);
      String standTitle = "";
      if (para[12].equals("1")) {
        this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
            workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + 
            para[14]);
        standTitle = "（代" + para[15] + "办理）";
      } 
      String remindFieldValue = para[8];
      int k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！").length() - 100;
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String workTitle = docTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      this.stmt.execute("update JSDB.JSF_WORK set workstatus = 101, workTitle = '" + workTitle + "', " + 
          "workAllowCancel=" + para[6] + ", workUser = '" + workUser.toString() + "' " + 
          "where wf_work_id = " + curWorkId);
      int mycount = 0;
      if (para[9].equals("1")) {
        sql = " select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + workTable_id + 
          " and workRecord_id = " + workRecord_id + " and workStepCount = " + para[7] + 
          " and workstatus = 0";
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      int workListControl = 0;
      if (mycount == 0)
        if (activityClass.equals("1") || (activityClass.equals("0") && subProcType.equals("0"))) {
          this.stmt.execute("update JSDB.JSF_WORK set workListControl = 1 where worktable_id = " + workTable_id + 
              " and workrecord_id = " + workRecord_id + " and workActivity = " + nextActivityId);
          this.stmt.execute("update JSDB.JSF_WORK set workCurStep='" + nextActivityName + "' where worktable_id=" + workTable_id + 
              " and workrecord_id = " + workRecord_id);
          workListControl = 1;
          this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus = 1 where databasetable_id = " + 
              workTable_id + " and databaserecord_id = " + workRecord_id + " and curStepCount = " + 
              para[7]);
        }  
      if (para[11].equals("1")) {
        String[] standForUser = (String[])null;
        for (int j = 0; j < toUserList.size(); j++) {
          standForUser = toUserList.get(j);
          rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[0] + " and worktable_id=" + 
              workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
              nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1));
          rs.next();
          int sameWorkCount = rs.getInt(1);
          rs.close();
          if (sameWorkCount == 0) {
            String workId = getTableId();
            String tmpSql = "";
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg) values ( " + 




                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "','" + workSubmitTime + "',," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",'" + now.toLocaleString() + 
                "'," + (Integer.parseInt(para[7]) + 1) + ",0,0,''," + nextActivityId + 
                "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
            } else {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg) values ( " + 




                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",JSDB.FN_STRTODATE('" + now.toLocaleString() + 
                "','')," + (Integer.parseInt(para[7]) + 1) + ",0,0,''," + nextActivityId + 
                "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
            } 
            this.stmt.execute(tmpSql);
          } 
          if (!standForUser[2].equals("")) {
            rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[2] + " and worktable_id=" + 
                workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
                nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1) + " and standForUserId=" + 
                standForUser[0]);
            rs.next();
            sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId();
              String tmpSql = "";
              String databaseType = SystemCommon.getDatabaseType();
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg) values ( " + 




                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "','" + workSubmitTime + "'," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",'" + now.toLocaleString() + "'," + (
                  Integer.parseInt(para[7]) + 1) + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
              } else {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg) values ( " + 




                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + (
                  Integer.parseInt(para[7]) + 1) + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
              } 
              this.stmt.execute(tmpSql);
            } 
          } 
        } 
      } else {
        String tmpUser = "";
        for (int j = 0; j < transactUser.length; j++) {
          tmpUser = transactUser[j];
          if (tmpUser.startsWith("$"))
            tmpUser = tmpUser.substring(1); 
          if (tmpUser.endsWith("$"))
            tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
          rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
              tmpUser + " and worktable_id = " + workTable_id + 
              " and workrecord_id = " + workRecord_id + 
              " and workreadmarker = 0 and workActivity = " + nextActivityId + 
              " and workStepCount = " + (Integer.parseInt(para[7]) + 1));
          rs.next();
          int sameWorkCount = rs.getInt(1);
          rs.close();
          if (sameWorkCount == 0) {
            String workId = getTableId();
            String tmpSql = "";
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId, submitOrg ) values ( " + 







                
                workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                nextActivityName + "','" + docTitle + "','','" + para[10] + "'," + workListControl + "," + 
                nextActivityId + ",'" + workSubmitPerson + "','" + 
                workSubmitTime + "'," + wf_submitEmployee_id + 
                ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ", '" + 
                now.toLocaleString() + "', " + (
                Integer.parseInt(para[7]) + 1) + "," + nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
            } else {
              tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId, submitOrg ) values ( " + 







                
                workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                nextActivityName + "','" + docTitle + "','','" + para[10] + "'," + workListControl + "," + 
                nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                workSubmitTime + "','')," + wf_submitEmployee_id + 
                ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                now.toLocaleString() + "',''), " + (
                Integer.parseInt(para[7]) + 1) + "," + nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
            } 
            this.stmt.execute(tmpSql);
          } 
        } 
      } 
      if (!needPassRound.equals("")) {
        boolean flag = true;
        String tmpUser = "";
        for (int m = 0; m < passRoundUser.length; m++) {
          tmpUser = passRoundUser[m];
          if (!tmpUser.equals("")) {
            if (tmpUser.startsWith("$"))
              tmpUser = tmpUser.substring(1); 
            if (tmpUser.endsWith("$"))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            for (int j = 0; j < transactUser.length; j++) {
              if (tmpUser.equals(transactUser[j])) {
                flag = false;
                break;
              } 
            } 
            if (flag) {
              String workId = getTableId();
              String tmpSql = "";
              String databaseType = SystemCommon.getDatabaseType();
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg  ) values ( " + 







                  
                  workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                  nextActivityName + "','" + docTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "','" + 
                  workSubmitTime + "'," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", '" + 
                  now.toLocaleString() + "', " + (
                  Integer.parseInt(para[7]) + 1) + "," + nextActivityId + ", " + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
              } else {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg  ) values ( " + 







                  
                  workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                  nextActivityName + "','" + docTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                  workSubmitTime + "','')," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                  now.toLocaleString() + "',''), " + (
                  Integer.parseInt(para[7]) + 1) + "," + nextActivityId + ", " + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "')";
              } 
              this.stmt.execute(tmpSql);
            } 
          } 
        } 
      } 
      if (mycount == 0 && activityClass.equals("0") && subProcType.equals("0")) {
        this.stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + nextActivityName + "', workactivity = " + 
            nextActivityId + " where worktable_id = " + workTable_id + " and " + 
            " workrecord_id = " + workRecord_id);
        this.stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStepCount = " + (
            Integer.parseInt(para[7]) + 1));
      } 
      if (para[9].equals("0") || (activityClass.equals("0") && subProcType.equals("0")))
        this.stmt.execute("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + 
            docTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + 
            " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void completeWorkDoc(String[] para, String workId, String docTitle) throws Exception {
    begin();
    Date now = new Date();
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_curemployee_id,workRecord_id,workTable_id,workProcess_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId from JSDB.JSF_WORK where wf_work_id=" + 
          workId);
      String workRecord_id = "", workTable_id = "", wf_curemployee_id = "", workProcess_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "", pareTableId = "";
      String pareRecordId = "", pareProcNextActiId = "";
      if (rs.next()) {
        wf_curemployee_id = rs.getString("wf_curemployee_id");
        workRecord_id = rs.getString("workRecord_id");
        workTable_id = rs.getString("workTable_id");
        workProcess_id = rs.getString("workProcess_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
      } 
      rs.close();
      this.stmt.execute("delete from JSDB.JSF_WORK where workstatus=101 and worktable_id=" + workTable_id + 
          " and workrecord_id=" + workRecord_id + " and wf_curemployee_id= " + 
          wf_curemployee_id + " and workprocess_id=" + workProcess_id);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[9] + " and workprocess_id = " + workProcess_id);
      String standTitle = "";
      if (para[8].equals("1")) {
        this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
            workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + 
            para[10] + " and workProcess_id=" + workProcess_id);
        standTitle = "（代" + para[11] + "办理）";
      } 
      String workTitle = docTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      String sql = "update JSDB.JSF_WORK set workStatus=101, workTitle='" + workTitle + "'," + 
        "workAllowCancel=0 where wf_work_id=" + workId;
      this.stmt.execute(sql);
      int mycount = 0;
      if (para[6].equals("1")) {
        sql = "select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + para[0] + 
          " and workrecord_id = " + para[1] + " and workactivity = " + para[2] + 
          " and workstatus = 0";
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      if (mycount == 0) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "update JSDB.JSF_WORK set workstatus=100,workTitle='" + workTitle + "',workDoneWithDate='" + 
            now.toLocaleString() + "' where worktable_id=" + para[0] + " and " + 
            "workrecord_id=" + para[1] + " and workstartflag=1";
        } else {
          sql = "update JSDB.JSF_WORK set workstatus=100,workTitle='" + workTitle + "',workDoneWithDate=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "','') where worktable_id=" + para[0] + " and " + 
            "workrecord_id=" + para[1] + " and workstartflag=1";
        } 
        this.stmt.execute(sql);
      } 
      if (para[6].equals("0"))
        this.stmt.execute("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + workTitle + "',workAllowCancel = 0 " + 
            "where (workStatus = 0 or workStatus = 2) and workTable_id = " + para[0] + 
            " and workRecord_id = " + para[1] + " and workStepCount = " + 
            para[7] + " and wf_work_id <> " + workId); 
      if (mycount == 0 || para[6].equals("0")) {
        if (para.length > 12 && 
          para[12].equals("information"))
          this.stmt.execute("update JSDB.oa_information set informationStatus = 0 where information_id=" + para[1]); 
        this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus=1 where databasetable_id=" + para[0] + " and " + 
            "databaserecord_id=" + para[1] + " and activity_id=" + para[2]);
        if (isSubProcWork.equals("1")) {
          this.stmt.execute("update JSDB.JSF_WORK set workListControl=1 where worktable_id=" + pareTableId + " and workRecord_id=" + 
              pareRecordId + " and workStepCount=" + (Integer.parseInt(pareStepCount) + 1) + " and " + 
              "workActivity=" + pareProcNextActiId);
          this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus=1 where databasetable_id=" + pareTableId + 
              " and databaserecord_id=" + pareRecordId + " and activity_id=" + pareProcActiId + " and " + 
              "curStepCount=" + pareStepCount);
        } 
      } 
      if (mycount == 0) {
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "UPDATE JSDB.DOC_RECEIVEFILE SET RECEIVEFILE_ISEND=1, RECEIVEFILE_FINISHDATE='" + (new Date()).toLocaleString() + "' WHERE RECEIVEFILE_ID=" + para[1];
        } else {
          tmpSql = "UPDATE JSDB.DOC_RECEIVEFILE SET RECEIVEFILE_ISEND=1, RECEIVEFILE_FINISHDATE=JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') WHERE RECEIVEFILE_ID=" + para[1];
        } 
        this.stmt.execute(tmpSql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void operPassRoundWorkDoc(String[] para, String docTitle) throws Exception {
    begin();
    try {
      String sql = "select workFileType, workSubmitPerson, workProcess_id, workTable_id, workRecord_id from JSDB.JSF_WORK where wf_work_id = " + 
        
        para[0];
      ResultSet rs = this.stmt.executeQuery(sql);
      String fileType = "", submitPerson = "";
      if (rs.next()) {
        fileType = rs.getString(1);
        submitPerson = rs.getString(2);
      } 
      rs.close();
      String workTitle = docTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      if (para[2].equals("")) {
        this.stmt.executeUpdate("update JSDB.JSF_WORK set workstatus=102,workTitle='" + workTitle + "' " + 
            "where wf_work_id=" + para[0]);
      } else {
        this.stmt.executeUpdate("update JSDB.JSF_WORK set workstatus=102,workTitle='" + workTitle + "' " + 
            ", workmainlinkfile='" + para[2] + "' where wf_work_id=" + para[0]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public List getFirstNextActi(String activityId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = "select transitionTo,conditionField,compareValue,relation,expression,formType from JSDB.jsf_transition,jsf_Activity,jsf_transitionRestriction,JSF_WORKFLOWPROCESS where jsf_transition.wf_transition_id=jsf_transitionRestriction.wf_transition_id and JSF_WORKFLOWPROCESS.WF_WORKFLOWPROCESS_ID=jsf_Activity.WF_WORKFLOWPROCESS_ID and transitionFrom=" + 

        
        activityId + " and jsf_Activity.wf_Activity_id=" + activityId + " order by transitionTo";
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getFirstNextActiWithExp(String activityId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = "select wf_transition_id,transitionTo,expression from JSDB.jsf_transition where transitionFrom=" + activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "", "" };
        tmp[6] = rs.getString(1);
        tmp[0] = rs.getString(2);
        tmp[4] = rs.getString(3);
        alist.add(tmp);
      } 
      rs.close();
      for (int i = 0; i < alist.size(); i++) {
        String[] tmp = alist.get(i);
        if (tmp[4] == null || "".equals(tmp[4]) || "null".equals(tmp[4])) {
          sql = "select conditionField,compareValue,relation from jsf_transitionRestriction where wf_transition_id=" + tmp[6];
          if (rs.next()) {
            tmp[1] = rs.getString(1);
            tmp[2] = rs.getString(2);
            tmp[3] = rs.getString(3);
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public ActivityVO getFirstProcActiVO(String activityId) throws Exception {
    begin();
    ActivityVO activityVO = new ActivityVO();
    try {
      String participantUser = "", participantGroup = "", participantUserField = "";
      String activityName = "", passRoundUser = "", passRoundUserGroup = "";
      String passRoundUserField = "", participantRole = "", passRoundRole = "";
      String participantGivenOrg = "", passRoundGivenOrg = "", transactType = "";
      String allowSmsRemind = "", pressDealType = "", allowAutoCheck = "", activitybeginend = "0";
      int activityClass = 0, participantType = 0, needPassRound = 0, passRoundUserType = 0;
      int allowCancel = 0, allowStandFor = 0, pressType = 0, deadLineTime = 0;
      int pressMotionTime = 0;
      String sql = "select participantType,participantUser,participantGroup,participantUserField,allowStandFor,allowCancel,pressType,deadLineTime,pressMotionTime,activityName,needPassRound,passRoundUserType,passRoundUser,passRoundUserGroup,passRoundUserField,participantRole,passRoundRole,activityClass,participantGivenOrg,passRoundGivenOrg,transactType,allowSmsRemind,pressDealType,allowAutoCheck,activitybeginend from JSDB.jsf_Activity where wf_activity_id = " + 



        
        activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        participantType = rs.getInt("participantType");
        participantUser = rs.getString("participantUser");
        participantGroup = rs.getString("participantGroup");
        participantUserField = rs.getString("participantUserField");
        allowStandFor = rs.getInt("allowStandFor");
        allowCancel = rs.getInt("allowCancel");
        pressType = rs.getInt("pressType");
        deadLineTime = rs.getInt("deadLineTime");
        pressMotionTime = rs.getInt("pressMotionTime");
        activityName = rs.getString("activityName");
        needPassRound = rs.getInt("needPassRound");
        passRoundUserType = rs.getInt("passRoundUserType");
        passRoundUser = rs.getString("passRoundUser");
        passRoundUserGroup = rs.getString("passRoundUserGroup");
        passRoundUserField = rs.getString("passRoundUserField");
        participantRole = rs.getString("participantRole");
        passRoundRole = rs.getString("passRoundRole");
        activityClass = rs.getInt("activityClass");
        participantGivenOrg = rs.getString("participantGivenOrg");
        passRoundGivenOrg = rs.getString("passRoundGivenOrg");
        transactType = rs.getString("transactType");
        allowSmsRemind = rs.getString("allowSmsRemind");
        pressDealType = rs.getString("pressDealType");
        allowAutoCheck = rs.getString("allowAutoCheck");
        activitybeginend = rs.getString("activitybeginend");
      } 
      rs.close();
      activityVO.setTransactType(transactType);
      if (participantGivenOrg != null && !participantGivenOrg.equals("") && !participantGivenOrg.toUpperCase().equals("NULL"))
        activityVO.setParticipantGivenOrg(participantGivenOrg); 
      if (passRoundGivenOrg != null && !passRoundGivenOrg.equals("") && !passRoundGivenOrg.toUpperCase().equals("NULL"))
        activityVO.setPassRoundGivenOrg(passRoundGivenOrg); 
      activityVO.setId(Long.parseLong(activityId));
      activityVO.setName(activityName);
      activityVO.setActivityClass(activityClass);
      activityVO.setParticipantType(participantType);
      activityVO.setActivityBeginEnd(Integer.parseInt(activitybeginend));
      if (participantType == 2 || participantType == 3) {
        if (participantUser == null || "".equals(participantUser))
          participantUser = "-100"; 
        if (participantGroup == null || "".equals(participantGroup))
          participantGroup = "-100"; 
        if (participantUser.indexOf("$") >= 0) {
          participantUser = "$" + participantUser + "$";
          participantUser = participantUser.replace('$', ',');
          participantUser = participantUser.replaceAll(",,", ",");
          participantUser = participantUser.substring(1, participantUser.length() - 1);
        } 
        if (participantGroup.indexOf("@") >= 0) {
          participantGroup = "@" + participantGroup + "@";
          participantGroup = participantGroup.replace('@', ',');
          participantGroup = participantGroup.replaceAll(",,", ",");
          participantGroup = participantGroup.substring(1, participantGroup.length() - 1);
        } 
        if ("-100".equals(participantGroup)) {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id=JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            participantUser + ")) order by JSDB.org_organization.orgIdString, JSDB.org_employee.userOrderCode";
        } else {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id=JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            participantUser + ") or JSDB.org_employee.emp_id in (select JSDB.org_employee.emp_id from JSDB.org_employee," + 
            "JSDB.org_user_group where JSDB.org_employee.emp_id=JSDB.org_user_group.emp_id and JSDB.org_user_group.group_id " + 
            "in (" + participantGroup + "))) order by JSDB.org_organization.orgIdString, JSDB.org_employee.userOrderCode";
        } 
        rs = this.stmt.executeQuery(sql);
        ArrayList<String[]> partUserList = new ArrayList();
        if (participantType == 3) {
          ConcurrentHashMap<Object, Object> userMap = new ConcurrentHashMap<Object, Object>();
          while (rs.next()) {
            String userId = rs.getString(1);
            String userName = rs.getString(2);
            String[] tmp = { userId, userName };
            userMap.put(userId, tmp);
          } 
          String[] userArr = participantUser.split(",");
          for (int i = 0; i < userArr.length; i++) {
            if (userMap.get(userArr[i]) != null)
              partUserList.add(userMap.get(userArr[i])); 
          } 
        } else {
          while (rs.next()) {
            String[] tmp = { rs.getString(1), rs.getString(2) };
            partUserList.add(tmp);
          } 
        } 
        rs.close();
        activityVO.setParticipantUser(partUserList);
      } else {
        activityVO.setParticipantUser(new ArrayList());
      } 
      activityVO.setParticipantUserField(participantUserField);
      if (needPassRound == 1) {
        activityVO.setNeedPassRound(Boolean.TRUE);
      } else {
        activityVO.setNeedPassRound(Boolean.FALSE);
      } 
      activityVO.setPassRoundUserType(passRoundUserType);
      if (passRoundUserType == 2 || passRoundUserType == 3) {
        if (passRoundUser == null || "".equals(passRoundUser))
          passRoundUser = "-100"; 
        if (passRoundUserGroup == null || "".equals(passRoundUserGroup))
          passRoundUserGroup = "-100"; 
        if (passRoundUser.indexOf("$") >= 0) {
          passRoundUser = "$" + passRoundUser + "$";
          passRoundUser = passRoundUser.replace('$', ',');
          passRoundUser = passRoundUser.replaceAll(",,", ",");
          passRoundUser = passRoundUser.substring(1, passRoundUser.length() - 1);
        } 
        if (passRoundUserGroup.indexOf("@") >= 0) {
          passRoundUserGroup = "@" + passRoundUserGroup + "@";
          passRoundUserGroup = passRoundUserGroup.replace('@', ',');
          passRoundUserGroup = passRoundUserGroup.replaceAll(",,", ",");
          passRoundUserGroup = passRoundUserGroup.substring(1, passRoundUserGroup.length() - 1);
        } 
        if ("-100".equals(passRoundUserGroup)) {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id = JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            passRoundUser + ")) order by JSDB.org_organization.orgIdString, JSDB.org_employee.userOrderCode";
        } else {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id = JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            passRoundUser + ") or JSDB.org_employee.emp_id in (select JSDB.org_employee.emp_id from JSDB.org_employee," + 
            "JSDB.org_user_group where JSDB.org_employee.emp_id=JSDB.org_user_group.emp_id and JSDB.org_user_group.group_id " + 
            "in (" + passRoundUserGroup + "))) order by JSDB.org_organization.orgIdString, JSDB.org_employee.userOrderCode";
        } 
        rs = this.stmt.executeQuery(sql);
        ArrayList<String[]> passUserList = new ArrayList();
        while (rs.next()) {
          String[] tmp = { rs.getString(1), rs.getString(2) };
          passUserList.add(tmp);
        } 
        rs.close();
        activityVO.setPassRoundUser(passUserList);
      } else {
        activityVO.setPassRoundUser(new ArrayList());
      } 
      activityVO.setPassRoundUserField(passRoundUserField);
      activityVO.setAllowcancel(allowCancel);
      activityVO.setAllowStandFor(allowStandFor);
      activityVO.setPressType(pressType);
      activityVO.setDeadlineTime(deadLineTime);
      activityVO.setPressMotionTime(pressMotionTime);
      activityVO.setPartRole(participantRole);
      activityVO.setPassRole(passRoundRole);
      String orgName = "", roleName = "", orgId = "", roleId = "";
      if (participantRole != null && !"".equals(participantRole) && participantRole.indexOf("|") == -1) {
        if (participantRole.indexOf("and") > 0) {
          roleId = participantRole.split("and")[0];
          orgId = participantRole.split("and")[1];
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + roleId);
          if (rs.next())
            roleName = rs.getString(1); 
          rs.close();
          if (orgId.equals("-1")) {
            orgName = "发起人的组织";
          } else if (orgId.equals("-2")) {
            orgName = "发起人的组织及上级组织";
          } else if (orgId.equals("-3")) {
            orgName = "发起人的组织及下级组织";
          } else if (Long.parseLong(orgId) > 0L) {
            rs = this.stmt.executeQuery("select orgNameString from JSDB.org_organization where org_id=" + orgId);
            if (rs.next())
              orgName = rs.getString(1); 
            rs.close();
          } 
        } else {
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + participantRole);
          if (rs.next())
            roleName = rs.getString(1); 
        } 
        if (orgName.equals("")) {
          activityVO.setPartRoleName(roleName);
        } else {
          activityVO.setPartRoleName(String.valueOf(orgName) + "-" + roleName);
        } 
      } 
      orgName = "";
      roleName = "";
      if (passRoundRole != null && !"".equals(passRoundRole) && 14 == passRoundUserType) {
        if (passRoundRole.indexOf("and") > 0) {
          roleId = passRoundRole.split("and")[0];
          orgId = passRoundRole.split("and")[1];
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + roleId);
          if (rs.next())
            roleName = rs.getString(1); 
          if (orgId.equals("-1")) {
            orgName = "发起人的组织";
          } else if (orgId.equals("-2")) {
            orgName = "发起人的组织及上级组织";
          } else if (orgId.equals("-3")) {
            orgName = "发起人的组织及下级组织";
          } else if (Long.parseLong(orgId) > 0L) {
            rs = this.stmt.executeQuery("select orgNameString from JSDB.org_organization where org_id=" + orgId);
            if (rs.next())
              orgName = rs.getString(1); 
          } 
        } else {
          if (passRoundRole.indexOf("|") > 0) {
            rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id" + passRoundRole.split("\\|")[0] + passRoundRole.split("\\|")[1]);
          } else {
            rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + passRoundRole);
          } 
          if (rs.next())
            roleName = rs.getString(1); 
        } 
        if (orgName.equals("")) {
          activityVO.setPassRoleName(roleName);
        } else {
          activityVO.setPassRoleName(String.valueOf(orgName) + "-" + roleName);
        } 
      } 
      activityVO.setAllowSmsRemind(allowSmsRemind);
      activityVO.setPressDealType(pressDealType);
      activityVO.setAllowAutoCheck(allowAutoCheck);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return activityVO;
  }
  
  public ActivityVO getProceedActiVO(String tableId, String recordId, String activityId, String moduleId) throws Exception {
    begin();
    String realTableId = tableId;
    ActivityVO activityVO = new ActivityVO();
    String sql = "";
    String isEnd = "0";
    try {
      sql = "select 1 from jsdb.jsf_work where WORKSTATUS = 100 and workTable_Id in(" + tableId + ") and workRecord_id=" + recordId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        isEnd = "1"; 
      String participantUser = "", participantGroup = "", participantUserField = "";
      String activityName = "", passRoundUser = "", passRoundUserGroup = "";
      String passRoundUserField = "", participantRole = "", passRoundRole = "";
      String participantGivenOrg = "", passRoundGivenOrg = "", operButton = "", form_id = "";
      String activitydescription = "", actiCommField = "", passRoundCommField = "", actiCommFieldType = "", passRoundCommFieldType = "", transactType = "", allowAutoCheck = "0";
      int activityClass = 0, participantType = 0, needPassRound = 0, passRoundUserType = 0;
      int allowCancel = 0, allowStandFor = 0, pressType = 0, deadLineTime = 0;
      int pressMotionTime = 0, allowTransition = 0, activityBeginEnd = 0;
      String allowSmsRemind = "0";
      sql = "select participantType, participantUser, participantGroup,participantUserField,allowStandFor,allowCancel,allowTransition,pressType,deadLineTime,pressMotionTime,activityName,activityBeginEnd,needPassRound,passRoundUserType,passRoundUser,passRoundUserGroup,passRoundUserField,participantRole,passRoundRole,activityClass,participantGivenOrg,passRoundGivenOrg,operButton,form_id,activitydescription,actiCommField,passRoundCommField,actiCommFieldType,passRoundCommFieldType,allowSmsRemind,transacttype,allowAutoCheck  from JSDB.jsf_p_Activity where wf_activity_id=" + 



        
        activityId + " and ttable_id in(" + tableId + 
        ") and trecord_id=" + recordId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        participantType = rs.getInt("participantType");
        participantUser = rs.getString("participantUser");
        participantGroup = rs.getString("participantGroup");
        participantUserField = rs.getString("participantUserField");
        allowStandFor = rs.getInt("allowStandFor");
        allowCancel = rs.getInt("allowCancel");
        allowTransition = rs.getInt("allowTransition");
        pressType = rs.getInt("pressType");
        deadLineTime = rs.getInt("deadLineTime");
        pressMotionTime = rs.getInt("pressMotionTime");
        activityName = rs.getString("activityName");
        activityBeginEnd = rs.getInt("activityBeginEnd");
        needPassRound = rs.getInt("needPassRound");
        passRoundUserType = rs.getInt("passRoundUserType");
        passRoundUser = rs.getString("passRoundUser");
        passRoundUserGroup = rs.getString("passRoundUserGroup");
        passRoundUserField = rs.getString("passRoundUserField");
        participantRole = rs.getString("participantRole");
        passRoundRole = rs.getString("passRoundRole");
        activityClass = rs.getInt("activityClass");
        participantGivenOrg = rs.getString("participantGivenOrg");
        passRoundGivenOrg = rs.getString("passRoundGivenOrg");
        operButton = rs.getString("operButton");
        if ("1".equals(isEnd))
          operButton = operButton.replaceAll("cmdWait", "").replaceAll("cmdFeedback", "").replaceAll("Undo", ""); 
        form_id = rs.getString("form_id");
        activitydescription = rs.getString("activitydescription");
        actiCommField = rs.getString("actiCommField");
        passRoundCommField = rs.getString("passRoundCommField");
        actiCommFieldType = rs.getString("actiCommFieldType");
        passRoundCommFieldType = rs.getString("passRoundCommFieldType");
        allowSmsRemind = rs.getString("allowSmsRemind");
        transactType = rs.getString("transacttype");
        allowAutoCheck = rs.getString("allowAutoCheck");
      } 
      rs.close();
      if (participantGivenOrg != null && 
        !participantGivenOrg.equals("") && 
        !participantGivenOrg.toUpperCase().equals("NULL"))
        activityVO.setParticipantGivenOrg(participantGivenOrg); 
      if (passRoundGivenOrg != null && 
        !passRoundGivenOrg.equals("") && 
        !passRoundGivenOrg.toUpperCase().equals("NULL"))
        activityVO.setPassRoundGivenOrg(passRoundGivenOrg); 
      activityVO.setOperButton(operButton);
      activityVO.setFormId(form_id);
      activityVO.setActivityDescription(activitydescription);
      activityVO.setActiCommField(actiCommField);
      activityVO.setPassRoundCommField(passRoundCommField);
      activityVO.setActiCommFieldType(actiCommFieldType);
      activityVO.setPassRoundCommFieldType(passRoundCommFieldType);
      activityVO.setId(Long.parseLong(activityId));
      activityVO.setName(activityName);
      activityVO.setActivityClass(activityClass);
      activityVO.setActivityBeginEnd(activityBeginEnd);
      activityVO.setAllowTransition(allowTransition);
      activityVO.setParticipantType(participantType);
      activityVO.setTransactType(transactType);
      activityVO.setAllowAutoCheck(allowAutoCheck);
      if (participantType == 2 || participantType == 3) {
        if (participantUser == null || "".equals(participantUser))
          participantUser = "-100"; 
        if (participantGroup == null || "".equals(participantGroup))
          participantGroup = "-100"; 
        if (participantUser.indexOf("$") >= 0) {
          participantUser = "$" + participantUser + "$";
          participantUser = participantUser.replace('$', ',');
          participantUser = participantUser.replaceAll(",,", ",");
          participantUser = participantUser.substring(1, participantUser.length() - 1);
        } 
        if (participantGroup.indexOf("@") >= 0) {
          participantGroup = "@" + participantGroup + "@";
          participantGroup = participantGroup.replace('@', ',');
          participantGroup = participantGroup.replaceAll(",,", ",");
          participantGroup = participantGroup.substring(1, participantGroup.length() - 1);
        } 
        if ("-100".equals(participantGroup)) {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id=JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            participantUser + ")) order by JSDB.org_organization.orgIdString,JSDB.org_employee.userOrderCode";
        } else {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id=JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            participantUser + ") or JSDB.org_employee.emp_id in (select JSDB.org_employee.emp_id from JSDB.org_employee," + 
            "JSDB.org_user_group where JSDB.org_employee.emp_id=JSDB.org_user_group.emp_id and JSDB.org_user_group.group_id " + 
            "in (" + participantGroup + "))) order by JSDB.org_organization.orgIdString,JSDB.org_employee.userOrderCode";
        } 
        rs = this.stmt.executeQuery(sql);
        ArrayList<String[]> partUserList = new ArrayList();
        if (participantType == 3) {
          ConcurrentHashMap<Object, Object> userMap = new ConcurrentHashMap<Object, Object>();
          while (rs.next()) {
            String userId = rs.getString(1);
            String userName = rs.getString(2);
            String[] tmp = { userId, userName };
            userMap.put(userId, tmp);
          } 
          String[] userArr = participantUser.split(",");
          for (int i = 0; i < userArr.length; i++) {
            if (userMap.get(userArr[i]) != null)
              partUserList.add(userMap.get(userArr[i])); 
          } 
        } else {
          while (rs.next()) {
            String[] tmp = { rs.getString(1), rs.getString(2) };
            partUserList.add(tmp);
          } 
        } 
        rs.close();
        activityVO.setParticipantUser(partUserList);
      } else {
        activityVO.setParticipantUser(new ArrayList());
      } 
      activityVO.setParticipantUserField(participantUserField);
      if (needPassRound == 1) {
        activityVO.setNeedPassRound(Boolean.TRUE);
      } else {
        activityVO.setNeedPassRound(Boolean.FALSE);
      } 
      activityVO.setPassRoundUserType(passRoundUserType);
      if (passRoundUserType == 2 || passRoundUserType == 3) {
        if (passRoundUser == null)
          passRoundUser = "-100"; 
        if (passRoundUserGroup == null)
          passRoundUserGroup = "-100"; 
        if (passRoundUser.indexOf("$") >= 0) {
          passRoundUser = "$" + passRoundUser + "$";
          passRoundUser = passRoundUser.replace('$', ',');
          passRoundUser = passRoundUser.replaceAll(",,", ",");
          passRoundUser = passRoundUser.substring(1, passRoundUser.length() - 1);
        } 
        if (passRoundUserGroup.indexOf("@") >= 0) {
          passRoundUserGroup = "@" + passRoundUserGroup + "@";
          passRoundUserGroup = passRoundUserGroup.replace('@', ',');
          passRoundUserGroup = passRoundUserGroup.replaceAll(",,", ",");
          passRoundUserGroup = passRoundUserGroup.substring(1, passRoundUserGroup.length() - 1);
        } 
        passRoundUserGroup = passRoundUserGroup.equals("") ? "-1" : passRoundUserGroup;
        if ("-100".equals(passRoundUserGroup)) {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id=JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            passRoundUser + ")) order by JSDB.org_organization.orgIdString,JSDB.org_employee.userOrderCode";
        } else {
          sql = "select JSDB.org_employee.emp_id,JSDB.org_employee.empName from JSDB.org_employee,JSDB.org_organization_user,JSDB.org_organization where JSDB.org_employee.emp_id=JSDB.org_organization_user.emp_id and JSDB.org_organization_user.org_id=JSDB.org_organization.org_id and (JSDB.org_employee.emp_id in (" + 

            
            passRoundUser + ") or JSDB.org_employee.emp_id in (select JSDB.org_employee.emp_id from JSDB.org_employee," + 
            "JSDB.org_user_group where JSDB.org_employee.emp_id=JSDB.org_user_group.emp_id and JSDB.org_user_group.group_id " + 
            "in (" + passRoundUserGroup + "))) order by JSDB.org_organization.orgIdString,JSDB.org_employee.userOrderCode";
        } 
        rs = this.stmt.executeQuery(sql);
        ArrayList<String[]> passUserList = new ArrayList();
        while (rs.next()) {
          String[] tmp = { rs.getString(1), rs.getString(2) };
          passUserList.add(tmp);
        } 
        rs.close();
        activityVO.setPassRoundUser(passUserList);
      } else {
        activityVO.setPassRoundUser(new ArrayList());
      } 
      activityVO.setPassRoundUserField(passRoundUserField);
      activityVO.setAllowcancel(allowCancel);
      activityVO.setAllowStandFor(allowStandFor);
      activityVO.setPressType(pressType);
      activityVO.setDeadlineTime(deadLineTime);
      activityVO.setPressMotionTime(pressMotionTime);
      activityVO.setAllowSmsRemind(allowSmsRemind);
      String orgName = "", roleName = "", orgId = "", roleId = "";
      if (participantRole != null && !participantRole.equals("") && participantRole.indexOf("and") != -1) {
        if (participantRole.indexOf("and") > 0) {
          roleId = participantRole.split("and")[0];
          orgId = participantRole.split("and")[1];
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + roleId);
          if (rs.next())
            roleName = rs.getString(1); 
          rs.close();
          if (orgId.equals("-1")) {
            orgName = "发起人的组织";
          } else if (orgId.equals("-2")) {
            orgName = "发起人的组织及上级组织";
          } else if (orgId.equals("-3")) {
            orgName = "发起人的组织及下级组织";
          } else if (Long.parseLong(orgId) > 0L) {
            rs = this.stmt.executeQuery("select orgNameString from JSDB.org_organization where org_id=" + orgId);
            if (rs.next())
              orgName = rs.getString(1); 
            rs.close();
          } 
        } else {
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + participantRole);
          if (rs.next())
            roleName = rs.getString(1); 
          rs.close();
        } 
        activityVO.setPartRole(participantRole);
        if (orgName.equals("")) {
          activityVO.setPartRoleName(roleName);
        } else {
          activityVO.setPartRoleName(String.valueOf(orgName) + "-" + roleName);
        } 
      } 
      orgName = "";
      roleName = "";
      if (passRoundRole != null && !passRoundRole.equals("") && passRoundRole.indexOf("and") != -1) {
        if (passRoundRole.indexOf("and") > 0) {
          roleId = passRoundRole.split("and")[0];
          orgId = passRoundRole.split("and")[1];
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + roleId);
          if (rs.next())
            roleName = rs.getString(1); 
          rs.close();
          if (orgId.equals("-1")) {
            orgName = "发起人的组织";
          } else if (orgId.equals("-2")) {
            orgName = "发起人的组织及上级组织";
          } else if (orgId.equals("-3")) {
            orgName = "发起人的组织及下级组织";
          } else if (Long.parseLong(orgId) > 0L) {
            rs = this.stmt.executeQuery("select orgNameString from JSDB.org_organization where org_id=" + orgId);
            if (rs.next())
              orgName = rs.getString(1); 
          } 
        } else {
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + passRoundRole);
          if (rs.next())
            roleName = rs.getString(1); 
          rs.close();
        } 
        activityVO.setPassRole(passRoundRole);
        if (orgName.equals("")) {
          activityVO.setPassRoleName(roleName);
        } else {
          activityVO.setPassRoleName(String.valueOf(orgName) + "-" + roleName);
        } 
      } 
      if (SystemCommon.getFormProgram() == 1) {
        rs = this.stmt.executeQuery("select exe_script,beforeSubmit from jsf_activity where wf_activity_id=" + activityId);
        if (rs.next()) {
          activityVO.setExeScript(rs.getString(1));
          activityVO.setBeforeSubmit(rs.getString(2));
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return activityVO;
  }
  
  public String getProceedActiVOPactivity(String tableId, String recordId, String activityId, String moduleId) throws Exception {
    begin();
    String realTableId = tableId;
    String sql = "";
    String passRoundCommFieldType = "";
    try {
      sql = "select passRoundCommFieldType from JSDB.jsf_Activity where wf_activity_id=" + 
        activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        passRoundCommFieldType = rs.getString("passRoundCommFieldType"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return passRoundCommFieldType;
  }
  
  public List getWorkUserLogin(String tableId, String recordId, String processId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_work_id,userAccounts,worksubmitperson from JSDB.JSF_WORK,JSDB.org_employee where emp_id=wf_submitemployee_id and worktable_id=" + 
          tableId + " and workrecord_id=" + recordId + " and workProcess_id=" + processId);
      while (rs.next()) {
        String[] tmp = { rs.getString(1), rs.getString(2), rs.getString(3) };
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getSingleModuleProcess(String moduleId) throws Exception {
    begin();
    String processId = "0";
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_workflowprocess_id from JSDB.jsf_workflowprocess,JSDB.jsf_package where JSDB.jsf_package.wf_package_id=JSDB.jsf_workflowprocess.wf_package_id and JSDB.jsf_package.wf_module_id=" + 
          
          moduleId);
      if (rs.next())
        processId = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return processId;
  }
  
  public List getOperUserOrg(String tableId, String recordId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("Select distinct org_Organization.org_id,org_Organization.orgname From JSDB.org_Organization,JSDB.org_organization_user Where JSDB.org_organization_user.org_id=JSDB.org_Organization.org_Id And JSDB.org_organization_user.emp_id In (Select wf_curemployee_id From JSDB.JSF_WORK Where worktable_id=" + 

          
          tableId + " And workrecord_id=" + recordId + " And workstepcount=" + 
          "(Select Max(workstepcount) From JSDB.JSF_WORK Where worktable_id=" + tableId + " And " + 
          "workrecord_id=" + recordId + " And worklistcontrol=1))");
      while (rs.next()) {
        String[] tmp = { "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getActiUserByActiName(String tableId, String recordId, String activityName) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("Select distinct emp_id,empName From JSDB.org_employee,JSDB.jsf_work,JSDB.jsf_p_Activity Where emp_id=wf_curemployee_id And workactivity=wf_activity_id And activityname='" + 
          
          activityName + "' And worktable_id=" + tableId + " And workrecord_id=" + recordId);
      while (rs.next()) {
        String[] tmp = { "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getLeaderList(String userId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select a.emp_id,a.empName from JSDB.org_employee a,JSDB.org_employee b where b.empleaderId like concat('%$', a.emp_id, '$%') and b.emp_id=" + 
          userId + " order by a.userOrderCode";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select a.emp_id,a.empName from JSDB.org_employee a,JSDB.org_employee b where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(a.emp_id)), '$'),b.empleaderId)>0 and b.emp_id=" + 
          userId + " order by a.userOrderCode";
      } else {
        tmpSql = "select a.emp_id,a.empName from JSDB.org_employee a,JSDB.org_employee b where b.empleaderId like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(a.emp_id)), '$%') and b.emp_id=" + 
          userId + " order by a.userOrderCode";
      } 
      ResultSet rs = this.stmt.executeQuery(tmpSql);
      while (rs.next()) {
        String[] tmp = { "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getUpActivityUser(String processId, String tableId, String recordId, String curActivityId) {
    String empId = "";
    ResultSet rs = null;
    try {
      begin();
      String sql = "SELECT WF_CUREMPLOYEE_ID FROM jsf_work WHERE WORKPROCESS_ID=" + processId + " AND WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + 
        recordId + " AND initactivity IN (SELECT transitionfrom FROM jsf_transition WHERE transitionto=" + curActivityId + ") ORDER BY wf_work_id DESC";
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        empId = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return empId;
  }
  
  public List getActivityUsers(String processId, String tableId, String recordId, String curActivityId, String from) {
    List<String[]> users = (List)new ArrayList<String>();
    ResultSet rs = null;
    try {
      String fromSQL = "";
      if ("1".equals(from)) {
        fromSQL = "initactivity=" + curActivityId;
      } else {
        fromSQL = "initactivity IN (SELECT transitionfrom FROM jsf_transition WHERE transitionto=" + curActivityId + ")";
      } 
      begin();
      String sql = "SELECT wf_curemployee_id,empname FROM jsf_work w,org_employee e WHERE w.wf_curemployee_id=e.emp_id AND WORKPROCESS_ID=" + processId + 
        " AND WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND " + fromSQL + " ORDER BY wf_work_id DESC";
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        users.add(new String[] { rs.getString(1), rs.getString(2) });
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return users;
  }
  
  public List getLeaderListByOrgIdOld(String orgId) throws Exception {
    begin();
    ArrayList<String> alist = new ArrayList();
    try {
      String tmpSql = "";
      tmpSql = "select a.ORGMANAGEREMPID,a.ORGMANAGEREMPNAME from JSDB.org_organization a where a.ORG_ID=" + orgId;
      ResultSet rs = this.stmt.executeQuery(tmpSql);
      if (rs.next() && 
        rs.getString(1) != null && !"".equals(rs.getString(1)) && !"0".equals(rs.getString(1))) {
        String tmp = "";
        String[] leaderId = rs.getString(1).split("\\$");
        for (int i = 0; i < leaderId.length; i++) {
          tmp = leaderId[i];
          if (!"".equals(tmp))
            alist.add(tmp); 
        } 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getLeaderListByOrgId(String orgId) throws Exception {
    ArrayList<String> alist = new ArrayList();
    if (orgId != null && !"null".equals(orgId) && !"".equals(orgId)) {
      begin();
      try {
        String tmpSql = "";
        tmpSql = "select a.ORGMANAGEREMPID,a.ORGMANAGEREMPNAME from JSDB.org_organization a where a.ORG_ID in(" + orgId + ")";
        ResultSet rs = this.stmt.executeQuery(tmpSql);
        while (rs.next()) {
          if (rs.getString(1) != null && !"".equals(rs.getString(1)) && !"0".equals(rs.getString(1))) {
            String tmp = "";
            String[] leaderId = rs.getString(1).split("\\$");
            for (int i = 0; i < leaderId.length; i++) {
              tmp = leaderId[i];
              if (!"".equals(tmp))
                alist.add(tmp); 
            } 
          } 
        } 
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally {
        end();
      } 
    } 
    return alist;
  }
  
  public String getForceCancel(String activity, String tableId, String recordId) throws Exception {
    begin();
    String forceCancel = "0";
    try {
      ResultSet rs = this.stmt.executeQuery("select allowCancel from JSDB.jsf_p_activity where wf_activity_id=" + 
          activity + " and ttable_id=" + 
          tableId + " and trecord_id=" + recordId);
      if (rs.next())
        forceCancel = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return forceCancel;
  }
  
  public Integer forceCancel(String[] para) throws Exception {
    begin();
    Integer success = new Integer("0");
    try {
      ResultSet rs = null;
      rs = this.stmt.executeQuery("select curActivityStatus,activity_Id,activityName from  JSDB.jsf_dealwith where databaseTable_id = " + 
          para[1] + " and " + 
          " databaseRecord_id = " + para[2] + " and " + 
          " curStepCount = " + para[3]);
      int curActivityStatus = 0;
      String activityId = "", activityName = "";
      if (rs.next()) {
        curActivityStatus = rs.getInt("curActivityStatus");
        activityId = rs.getString("activity_Id");
        activityName = rs.getString("activityName");
      } 
      rs.close();
      this.stmt.execute("delete from JSDB.jsf_dealwith where databasetable_id=" + para[1] + " and databaserecord_id=" + 
          para[2] + " and curStepCount>=" + para[3]);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_id=" + para[1] + " and workRecord_id=" + 
          para[2] + " and workStepCount>" + para[3]);
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "delete from JSDB.JSF_WORK where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.JSF_WORK b,JSDB.JSF_WORK a where a.workUser like concat('%$', b.wf_curEmployee_id, '$%') and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + 
          para[3] + " and workIsTran = 1";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "delete from JSDB.JSF_WORK where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.JSF_WORK b,JSDB.JSF_WORK a where a.workUser locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(b.wf_curEmployee_id)), '$')>0 and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + 
          para[3] + " and workIsTran = 1";
      } else {
        tmpSql = "delete from JSDB.JSF_WORK where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.JSF_WORK b,JSDB.JSF_WORK a where a.workUser like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(b.wf_curEmployee_id)), '$%') and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + 
          para[3] + " and workIsTran = 1";
      } 
      this.stmt.execute(tmpSql);
      this.stmt.execute("update JSDB.JSF_WORK set workStatus = 0, workCurStep = '" + activityName + "', " + 
          " workActivity = " + activityId + ", workTitle = JSDB.FN_REPLACE(workTitle,'您已办理完毕','等待您处理')" + 
          " where wf_work_id = " + para[0]);
      this.stmt.execute("update JSDB.JSF_WORK set workActivity = " + activityId + ",workCurStep = '" + 
          activityName + "' where workTable_id = " + para[1] + " and workRecord_id = " + 
          para[2]);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return success;
  }
  
  public Integer forceDel(String[] para) throws Exception {
    begin();
    Integer success = new Integer("0");
    try {
      this.stmt.execute("delete from JSDB.jsf_dealwith where databasetable_id=" + para[0] + " and databaserecord_id=" + 
          para[1]);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_id=" + para[0] + " and workRecord_id=" + 
          para[1]);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return success;
  }
  
  public void updateTable(List<E> updateSqlList) throws Exception {
    begin();
    try {
      for (int i = 0; i < updateSqlList.size(); i++)
        this.stmt.execute(updateSqlList.get(i).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void workCancel(String[] para) throws Exception {
    begin();
    ResultSet rs = null;
    try {
      rs = this.stmt.executeQuery("select curActivityStatus,activity_Id,activityName from  JSDB.jsf_dealwith where databaseTable_id = " + 
          para[1] + " and " + 
          " databaseRecord_id = " + para[2] + " and " + 
          " curStepCount = " + para[3]);
      int curActivityStatus = 0;
      String activityId = "", activityName = "";
      if (rs.next()) {
        curActivityStatus = rs.getInt("curActivityStatus");
        activityId = rs.getString("activity_Id");
        activityName = rs.getString("activityName");
      } 
      rs.close();
      rs = this.stmt.executeQuery("select wf_dealwithcomment_id from JSDB.jsf_dealwithComment,JSDB.jsf_dealwith  where JSDB.jsf_dealwithComment.wf_dealwith_id = JSDB.jsf_dealwith.wf_dealwith_id  and databaseTable_id = " + 
          
          para[1] + " and databaseRecord_id = " + para[2] + 
          " and dealwithEmployee_id = " + para[4] + " and curStepCount = " + para[3]);
      int wf_dealwithcomment_id = 0;
      if (rs.next())
        wf_dealwithcomment_id = rs.getInt("wf_dealwithcomment_id"); 
      rs.close();
      this.stmt.execute(" delete from JSDB.jsf_dealwithcomment where wf_dealwithcomment_id = " + wf_dealwithcomment_id);
      this.stmt.execute(" delete from JSDB.jsf_dealwith where wf_dealwith_id not in (select wf_dealwith_id from JSDB.jsf_dealwithComment)");
      this.stmt.execute("update JSDB.jsf_dealwith set curActivityStatus = 0 where  DatabaseTable_id = " + 
          para[1] + " and DatabaseRecord_id = " + 
          para[2] + " and curStepCount = " + para[3]);
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "delete from JSDB.jsf_work where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.jsf_work b,JSDB.jsf_work a where a.workUser like concat('%$', b.wf_curEmployee_id, '$%') and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + (
          Integer.parseInt(para[3]) + 1) + " and workIsTran = 0";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "delete from JSDB.jsf_work where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.jsf_work b,JSDB.jsf_work a where a.workUser locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(b.wf_curEmployee_id)), '$')>0 and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + (
          Integer.parseInt(para[3]) + 1) + " and workIsTran = 0";
      } else {
        tmpSql = "delete from JSDB.jsf_work where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.jsf_work b,JSDB.jsf_work a where a.workUser like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(b.wf_curEmployee_id)), '$%') and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + (
          Integer.parseInt(para[3]) + 1) + " and workIsTran = 0";
      } 
      this.stmt.execute(tmpSql);
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "delete from JSDB.JSF_WORK where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.JSF_WORK b,JSDB.JSF_WORK a where a.workUser like concat('%$', b.wf_curEmployee_id, '$%') and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + 
          para[3] + " and workIsTran = 1";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "delete from JSDB.JSF_WORK where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.JSF_WORK b,JSDB.JSF_WORK a where a.workUser locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(b.wf_curEmployee_id)), '$')>0 and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + 
          para[3] + " and workIsTran = 1";
      } else {
        tmpSql = "delete from JSDB.JSF_WORK where wf_curEmployee_id in ( select b.wf_curEmployee_id from JSDB.JSF_WORK b,JSDB.JSF_WORK a where a.workUser like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(b.wf_curEmployee_id)), '$%') and  a.wf_work_id = " + 
          
          para[0] + ") and workTable_id = " + para[1] + 
          " and workRecord_id = " + para[2] + " and workStepCount = " + 
          para[3] + " and workIsTran = 1";
      } 
      this.stmt.execute(tmpSql);
      this.stmt.execute("update JSDB.JSF_WORK set workStatus = 0, workCurStep = '" + activityName + "', " + 
          " workActivity = " + activityId + ", workTitle = replace(workTitle,'您已办理完毕','等待您处理')" + 
          " where wf_work_id = " + para[0]);
      if (curActivityStatus == 1) {
        this.stmt.execute("update JSDB.JSF_WORK set workActivity = " + activityId + ",workCurStep = '" + 
            activityName + "' where workTable_id = " + para[1] + " and workRecord_id = " + 
            para[2]);
        this.stmt.execute("update JSDB.JSF_WORK set workListControl = 0 where worktable_id = " + 
            para[1] + " and workRecord_id = " + para[2] + " and workStepCount = " + (
            Integer.parseInt(para[3]) + 1));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public String getTableName(String tableId) throws Exception {
    begin();
    String tableName = "";
    try {
      ResultSet rs = this.stmt.executeQuery("select table_name from JSDB.ttable where table_id = " + tableId);
      rs.next();
      tableName = rs.getString(1);
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return tableName;
  }
  
  public int insertFormRecord(String tableName, String fieldString, String valueString, List<E> childTable, List<E> childField, List<ArrayList> childFieldValue) throws Exception {
    begin();
    ResultSet rs = null;
    int recordId = 0;
    try {
      recordId = Integer.parseInt(getTableId());
      String sql = "insert into JSDB." + tableName + " (" + tableName + "_id," + fieldString + ") " + 
        " values (" + recordId + "," + valueString + ")";
      this.stmt.execute(sql);
      if (childTable.size() > 0) {
        String childTableName = "", childFieldName = "";
        ArrayList<String[]> childFieldValueList = new ArrayList();
        String assoField = "";
        for (int j = 0; j < childTable.size(); j++) {
          childTableName = childTable.get(j).toString();
          childFieldName = childField.get(j).toString();
          rs = this.stmt.executeQuery("select field_name from JSDB.tfield, JSDB.tlimit, ttable a,  JSDB.ttable b where field_id = limit_field and  limit_table = a.table_id and limit_prytable = b.table_id and a.table_name = '" + 

              
              childTableName + 
              "' and b.table_name = '" + tableName + "'");
          if (rs.next())
            assoField = rs.getString(1); 
          rs.close();
          childFieldValueList = childFieldValue.get(j);
          for (int k = 0; k < childFieldValueList.size(); k++) {
            String[] tmp = childFieldValueList.get(k);
            StringBuffer sb = new StringBuffer();
            for (int m = 0; m < tmp.length; m++)
              sb.append("'" + tmp[m] + "', "); 
            int childRecordId = 0;
            this.stmt.executeUpdate("update JSDB.tseq set iuser = iuser + 1");
            rs = this.stmt.executeQuery("select iuser from JSDB.tseq");
            if (rs.next())
              childRecordId = rs.getInt(1); 
            rs.close();
            sql = "insert into JSDB." + childTableName + " (" + childTableName + "_id," + 
              childFieldName + "," + assoField + ") values (" + childRecordId + "," + 
              sb.toString() + "'" + recordId + "')";
            this.stmt.execute(sql);
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return recordId;
  }
  
  public void updateChildTable(String tableId, String recordId, String childStr) throws Exception {
    begin();
    ResultSet rs = null;
    try {
      if (childStr.indexOf(";;") >= 0) {
        String[] child = childStr.split(";;");
        String assoFieldFlag = "";
        for (int i = 0; i < child.length; i++) {
          if (!child[i].equals("")) {
            String[] childInfoArray = child[i].split("::");
            rs = this.stmt.executeQuery("select field_name from JSDB.tfield, JSDB.ttable, JSDB.tlimit where  field_id = limit_field and table_id = limit_table and  table_name = '" + 
                
                childInfoArray[0] + "' and " + 
                " limit_prytable = " + tableId);
            String assoField = "";
            if (rs.next())
              assoField = rs.getString(1); 
            rs.close();
            if (!assoFieldFlag.equals(assoField)) {
              this.stmt.execute("delete fromcc JSDB." + childInfoArray[0] + " where " + assoField + " = " + 
                  recordId);
              assoFieldFlag = assoField;
            } 
            int childRecordId = 0;
            this.stmt.executeUpdate("update JSDB.tseq set iuser = iuser + 1");
            rs = this.stmt.executeQuery("select iuser from JSDB.tseq");
            if (rs.next())
              childRecordId = rs.getInt(1); 
            rs.close();
            this.stmt.execute("insert into JSDB." + childInfoArray[0] + " (" + 
                childInfoArray[0] + "_id," + childInfoArray[1] + "," + 
                assoField + ") values (" + childRecordId + "," + 
                childInfoArray[2] + "'" + recordId + "')");
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public String getAFieldValue(String tableName, String fieldId, String recordId) throws Exception {
    begin();
    ResultSet rs = null;
    String sql = "";
    String value = "";
    try {
      String field_name = "";
      sql = " select field_name from JSDB.tfield where field_id = " + fieldId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        field_name = rs.getString(1); 
      rs.close();
      sql = "select " + field_name + " form JSDB." + tableName + " where " + tableName + "_id = " + 
        recordId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        value = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return value;
  }
  
  public String[] getPress(String activityId, String tableName, String recordId) throws Exception {
    begin();
    ResultSet rs = null;
    String sql = "";
    String[] pressStr = { "-1", "-1" };
    try {
      sql = " select conditionField, pressRelation, compareValue, pressMotionTime, motionTime from JSDB.jsf_press where wf_activity_id = " + 
        activityId;
      rs = this.stmt.executeQuery(sql);
      ArrayList<Object[]> alist = new ArrayList();
      while (rs.next()) {
        Object[] arrayOfObject = new Object[5];
        arrayOfObject[0] = (new StringBuilder(String.valueOf(rs.getInt("conditionField")))).toString();
        arrayOfObject[1] = rs.getString("pressRelation");
        arrayOfObject[2] = rs.getString("compareValue");
        arrayOfObject[3] = (new StringBuilder(String.valueOf(rs.getInt("pressMotionTime")))).toString();
        arrayOfObject[4] = (new StringBuilder(String.valueOf(rs.getInt("motionTime")))).toString();
        alist.add(arrayOfObject);
      } 
      rs.close();
      Object[] obj = (Object[])null;
      String fieldName = "";
      String fieldValue = "";
      for (int i = 0; i < alist.size(); i++) {
        obj = alist.get(i);
        rs = this.stmt.executeQuery("select field_name from JSDB.tfield where field_id = " + obj[0]);
        if (rs.next())
          fieldName = rs.getString("field_name"); 
        rs.close();
        rs = this.stmt.executeQuery(" select " + fieldName + " from JSDB." + tableName + " where " + 
            tableName + "_id = " + recordId);
        if (rs.next())
          fieldValue = rs.getString(1); 
        rs.close();
        if (obj[1].toString().equals("<")) {
          try {
            if (Integer.parseInt(fieldValue) < Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals("<=")) {
          try {
            if (Integer.parseInt(fieldValue) <= Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals("<>")) {
          try {
            if (Integer.parseInt(fieldValue) != Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals("=")) {
          if (fieldValue.equals(obj[2].toString())) {
            pressStr[0] = obj[3].toString();
            pressStr[1] = obj[4].toString();
            break;
          } 
        } else if (obj[1].toString().equals(">")) {
          try {
            if (Integer.parseInt(fieldValue) > Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } else if (obj[1].toString().equals(">=")) {
          try {
            if (Integer.parseInt(fieldValue) >= Integer.parseInt(obj[2].toString())) {
              pressStr[0] = obj[3].toString();
              pressStr[1] = obj[4].toString();
              break;
            } 
          } catch (Exception exception) {}
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return pressStr;
  }
  
  public List getDealWithComment(String tableId, String recordId, String procType) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = " Select activity_id,activityname,empname,dealwithemployeecomment, dealwithtime,curstepcount,standForUserName, COMMENTFIELD,commtype,emp_id,wf_dealwithcomment_id,signcomment,dealwithOrg From JSDB.jsf_dealwith,JSDB.jsf_dealwithcomment,JSDB.org_employee Where  JSDB.jsf_dealwith.wf_dealwith_id = JSDB.jsf_dealwithcomment.wf_dealwith_id And  emp_id = dealwithemployee_id and  databasetable_id in(" + 










        
        tableId + ") and databaserecord_id = " + recordId;
      if (SystemCommon.getShowBackComment() != 1)
        sql = String.valueOf(sql) + " and jsf_dealwithcomment.commentisback<>1"; 
      sql = String.valueOf(sql) + " Order By dealwithtime";
      rs = this.stmt.executeQuery(sql);
      Map<String, String> commentTempMap = new HashMap<String, String>();
      while (rs.next()) {
        String[] tmp = { 
            "", "", "", "", "", "", "", "", "", "", 
            "", "", "", "", "", "" };
        tmp[0] = rs.getString("activity_id");
        tmp[1] = rs.getString("activityname");
        if ("1".equals(SystemCommon.getIsshowDealwithOrgName())) {
          String commentOrg = rs.getString("dealwithOrg");
          String level = SystemCommon.getIsshowOrgNameLevel();
          int showlevel = 0;
          if ("".equals(level)) {
            showlevel = 0;
          } else {
            showlevel = Integer.parseInt(level);
          } 
          if (commentOrg == null || commentOrg.equals("") || commentOrg.equals("null")) {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(StaticParam.getOrgNameStringByEmpId(rs.getString("emp_id")), showlevel)) + " " + rs.getString("empname");
          } else {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(commentOrg, showlevel)) + " " + rs.getString("empname");
          } 
        } else {
          tmp[2] = rs.getString("empname");
        } 
        String commentTemp = rs.getString("dealwithemployeecomment");
        if (commentTemp != null)
          commentTemp = commentTemp.replaceAll("\n", "<br>").replaceAll("\r", ""); 
        tmp[3] = commentTemp;
        tmp[4] = rs.getString("dealwithtime");
        tmp[5] = rs.getString("curstepcount");
        tmp[6] = rs.getString("standForUserName");
        tmp[8] = rs.getString("COMMENTFIELD");
        tmp[11] = rs.getString("commtype");
        tmp[12] = rs.getString("emp_id");
        tmp[13] = rs.getString("wf_dealwithcomment_id");
        tmp[14] = rs.getString("signcomment");
        if (SystemCommon.getSameNodeAndApproval() == 1) {
          String tempVal = commentTempMap.get(String.valueOf(tmp[0]) + tmp[12]);
          if (tempVal == null) {
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
            continue;
          } 
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date date1 = sdf.parse(tempVal);
          Date date2 = sdf.parse(tmp[4]);
          if (date1.getTime() < date2.getTime()) {
            for (int i = alist.size() - 1; i >= 0; i--) {
              String[] ttArr = alist.get(i);
              if (ttArr[0].equals(tmp[0]) && ttArr[12].equals(tmp[12]) && ttArr[4].equals(tempVal)) {
                alist.remove(i);
                break;
              } 
            } 
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
          } 
          continue;
        } 
        alist.add(tmp);
      } 
      rs.close();
      if ("1".equals(SystemCommon.getFlowCommentRange())) {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } else {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_ACTIVITY WHERE  WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getDealWithCommentNotBack(String tableId, String recordId, String procType) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = "";
      sql = " Select activity_id,activityname,empname,dealwithemployeecomment, dealwithtime,curstepcount,standForUserName, COMMENTFIELD,commtype,emp_id,wf_dealwithcomment_id,signcomment,dealwithOrg From JSDB.jsf_dealwith join JSDB.jsf_dealwithcomment on JSDB.jsf_dealwith.wf_dealwith_id = JSDB.jsf_dealwithcomment.wf_dealwith_id left join JSDB.org_employee on emp_id = dealwithemployee_id Where (JSDB.jsf_dealwithcomment.commtype is null or JSDB.jsf_dealwithcomment.commtype<>1) and  databasetable_id = " + 
















        
        tableId + " and databaserecord_id = " + recordId;
      if (SystemCommon.getShowBackComment() != 1)
        sql = String.valueOf(sql) + " and jsf_dealwithcomment.commentisback<>1"; 
      sql = String.valueOf(sql) + " Order By dealwithtime";
      rs = this.stmt.executeQuery(sql);
      Map<String, String> commentTempMap = new HashMap<String, String>();
      while (rs.next()) {
        String[] tmp = { 
            "", "", "", "", "", "", "", "", "", "", 
            "", "", "", "", "", "" };
        tmp[0] = rs.getString("activity_id");
        tmp[1] = rs.getString("activityname");
        if ("1".equals(SystemCommon.getIsshowDealwithOrgName())) {
          String commentOrg = rs.getString("dealwithOrg");
          String level = SystemCommon.getIsshowOrgNameLevel();
          int showlevel = 0;
          if ("".equals(level)) {
            showlevel = 0;
          } else {
            showlevel = Integer.parseInt(level);
          } 
          if (commentOrg == null || commentOrg.equals("") || commentOrg.equals("null")) {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(StaticParam.getOrgNameStringByEmpId(rs.getString("emp_id")), showlevel)) + " " + rs.getString("empname");
          } else {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(commentOrg, showlevel)) + " " + rs.getString("empname");
          } 
        } else {
          tmp[2] = rs.getString("empname");
        } 
        String commentTemp = rs.getString("dealwithemployeecomment");
        if (commentTemp != null)
          commentTemp = commentTemp.replaceAll("\n", "<br>").replaceAll("\r", ""); 
        tmp[3] = commentTemp;
        tmp[4] = rs.getString("dealwithtime");
        tmp[5] = rs.getString("curstepcount");
        tmp[6] = rs.getString("standForUserName");
        tmp[8] = rs.getString("COMMENTFIELD");
        tmp[11] = rs.getString("commtype");
        tmp[12] = rs.getString("emp_id");
        tmp[13] = rs.getString("wf_dealwithcomment_id");
        tmp[14] = rs.getString("signcomment");
        if (SystemCommon.getSameNodeAndApproval() == 1) {
          String tempVal = commentTempMap.get(String.valueOf(tmp[0]) + tmp[12]);
          if (tempVal == null) {
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
            continue;
          } 
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date date1 = sdf.parse(tempVal);
          Date date2 = sdf.parse(tmp[4]);
          if (date1.getTime() < date2.getTime()) {
            for (int i = alist.size() - 1; i >= 0; i--) {
              String[] ttArr = alist.get(i);
              if (ttArr[0].equals(tmp[0]) && ttArr[12].equals(tmp[12]) && ttArr[4].equals(tempVal)) {
                alist.remove(i);
                break;
              } 
            } 
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
          } 
          continue;
        } 
        alist.add(tmp);
      } 
      rs.close();
      if ("1".equals(SystemCommon.getFlowCommentRange())) {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } else {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_ACTIVITY WHERE  WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getDealWithCommentFordoc(String tableId, String recordId, String procType) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = " Select activity_id,activityname,empname,dealwithemployeecomment, dealwithtime,curstepcount,standForUserName, COMMENTFIELD,commtype,emp_id,wf_dealwithcomment_id,signcomment,dealwithOrg From JSDB.jsf_dealwith,JSDB.jsf_dealwithcomment,JSDB.org_employee Where  JSDB.jsf_dealwith.wf_dealwith_id = JSDB.jsf_dealwithcomment.wf_dealwith_id And  emp_id = dealwithemployee_id and  databasetable_id = " + 










        
        tableId + " and databaserecord_id = " + recordId;
      if (SystemCommon.getShowBackFordoc() != 1)
        sql = String.valueOf(sql) + " and jsf_dealwithcomment.commentisback<>1"; 
      sql = String.valueOf(sql) + " Order By dealwithtime";
      rs = this.stmt.executeQuery(sql);
      Map<String, String> commentTempMap = new HashMap<String, String>();
      while (rs.next()) {
        String[] tmp = { 
            "", "", "", "", "", "", "", "", "", "", 
            "", "", "", "", "", "" };
        tmp[0] = rs.getString("activity_id");
        tmp[1] = rs.getString("activityname");
        if ("1".equals(SystemCommon.getIsshowDealwithOrgName())) {
          String commentOrg = rs.getString("dealwithOrg");
          String level = SystemCommon.getIsshowOrgNameLevel();
          int showlevel = 0;
          if ("".equals(level)) {
            showlevel = 0;
          } else {
            showlevel = Integer.parseInt(level);
          } 
          if (commentOrg == null || commentOrg.equals("") || commentOrg.equals("null")) {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(StaticParam.getOrgNameStringByEmpId(rs.getString("emp_id")), showlevel)) + " " + rs.getString("empname");
          } else {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(commentOrg, showlevel)) + " " + rs.getString("empname");
          } 
        } else {
          tmp[2] = rs.getString("empname");
        } 
        String commentTemp = rs.getString("dealwithemployeecomment");
        if (commentTemp != null)
          commentTemp = commentTemp.replaceAll("\n", "<br>").replaceAll("\r", ""); 
        tmp[3] = commentTemp;
        tmp[4] = rs.getString("dealwithtime");
        tmp[5] = rs.getString("curstepcount");
        tmp[6] = rs.getString("standForUserName");
        tmp[8] = rs.getString("COMMENTFIELD");
        tmp[11] = rs.getString("commtype");
        tmp[12] = rs.getString("emp_id");
        tmp[13] = rs.getString("wf_dealwithcomment_id");
        tmp[14] = rs.getString("signcomment");
        if (SystemCommon.getSameNodeAndApproval() == 1) {
          String tempVal = commentTempMap.get(String.valueOf(tmp[0]) + tmp[12]);
          if (tempVal == null) {
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
            continue;
          } 
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date date1 = sdf.parse(tempVal);
          Date date2 = sdf.parse(tmp[4]);
          if (date1.getTime() < date2.getTime()) {
            for (int i = alist.size() - 1; i >= 0; i--) {
              String[] ttArr = alist.get(i);
              if (ttArr[0].equals(tmp[0]) && ttArr[12].equals(tmp[12]) && ttArr[4].equals(tempVal)) {
                alist.remove(i);
                break;
              } 
            } 
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
          } 
          continue;
        } 
        alist.add(tmp);
      } 
      rs.close();
      if ("1".equals(SystemCommon.getFlowCommentRange())) {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } else {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_ACTIVITY WHERE  WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getDealWithCommentNotBackFordoc(String tableId, String recordId, String procType) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = "";
      sql = " Select activity_id,activityname,empname,dealwithemployeecomment, dealwithtime,curstepcount,standForUserName, COMMENTFIELD,commtype,emp_id,wf_dealwithcomment_id,signcomment,dealwithOrg From JSDB.jsf_dealwith join JSDB.jsf_dealwithcomment on JSDB.jsf_dealwith.wf_dealwith_id = JSDB.jsf_dealwithcomment.wf_dealwith_id left join JSDB.org_employee on emp_id = dealwithemployee_id Where (JSDB.jsf_dealwithcomment.commtype is null or JSDB.jsf_dealwithcomment.commtype<>1) and  databasetable_id = " + 
















        
        tableId + " and databaserecord_id = " + recordId;
      if (SystemCommon.getShowBackFordoc() != 1)
        sql = String.valueOf(sql) + " and jsf_dealwithcomment.commentisback<>1"; 
      sql = String.valueOf(sql) + " Order By dealwithtime";
      rs = this.stmt.executeQuery(sql);
      Map<String, String> commentTempMap = new HashMap<String, String>();
      while (rs.next()) {
        String[] tmp = { 
            "", "", "", "", "", "", "", "", "", "", 
            "", "", "", "", "", "" };
        tmp[0] = rs.getString("activity_id");
        tmp[1] = rs.getString("activityname");
        if ("1".equals(SystemCommon.getIsshowDealwithOrgName())) {
          String commentOrg = rs.getString("dealwithOrg");
          String level = SystemCommon.getIsshowOrgNameLevel();
          int showlevel = 0;
          if ("".equals(level)) {
            showlevel = 0;
          } else {
            showlevel = Integer.parseInt(level);
          } 
          if (commentOrg == null || commentOrg.equals("") || commentOrg.equals("null")) {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(StaticParam.getOrgNameStringByEmpId(rs.getString("emp_id")), showlevel)) + " " + rs.getString("empname");
          } else {
            tmp[2] = String.valueOf(StaticParam.getOrgByNum(commentOrg, showlevel)) + " " + rs.getString("empname");
          } 
        } else {
          tmp[2] = rs.getString("empname");
        } 
        String commentTemp = rs.getString("dealwithemployeecomment");
        if (commentTemp != null)
          commentTemp = commentTemp.replaceAll("\n", "<br>").replaceAll("\r", ""); 
        tmp[3] = commentTemp;
        tmp[4] = rs.getString("dealwithtime");
        tmp[5] = rs.getString("curstepcount");
        tmp[6] = rs.getString("standForUserName");
        tmp[8] = rs.getString("COMMENTFIELD");
        tmp[11] = rs.getString("commtype");
        tmp[12] = rs.getString("emp_id");
        tmp[13] = rs.getString("wf_dealwithcomment_id");
        tmp[14] = rs.getString("signcomment");
        if (SystemCommon.getSameNodeAndApproval() == 1) {
          String tempVal = commentTempMap.get(String.valueOf(tmp[0]) + tmp[12]);
          if (tempVal == null) {
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
            continue;
          } 
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date date1 = sdf.parse(tempVal);
          Date date2 = sdf.parse(tmp[4]);
          if (date1.getTime() < date2.getTime()) {
            for (int i = alist.size() - 1; i >= 0; i--) {
              String[] ttArr = alist.get(i);
              if (ttArr[0].equals(tmp[0]) && ttArr[12].equals(tmp[12]) && ttArr[4].equals(tempVal)) {
                alist.remove(i);
                break;
              } 
            } 
            alist.add(tmp);
            commentTempMap.put(String.valueOf(tmp[0]) + tmp[12], tmp[4]);
          } 
          continue;
        } 
        alist.add(tmp);
      } 
      rs.close();
      if ("1".equals(SystemCommon.getFlowCommentRange())) {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId + " AND WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } else {
        for (int i = 0; i < alist.size(); i++) {
          String[] tmp = alist.get(i);
          rs = this.stmt.executeQuery("select handSignType from jsf_activity where WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next())
            tmp[15] = rs.getString(1); 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct COMMENTRANGE FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[7] = rs.getString(1);
          } else {
            tmp[7] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[9] = rs.getString(1);
          } else {
            tmp[9] = "";
          } 
          rs.close();
          rs = this.stmt.executeQuery("SELECT distinct passRoundCommFieldType FROM JSDB.JSF_ACTIVITY WHERE  WF_ACTIVITY_ID=" + tmp[0]);
          if (rs.next()) {
            tmp[10] = rs.getString(1);
          } else {
            tmp[10] = "";
          } 
          rs.close();
          alist.set(i, tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getAllowTransition(String tableId, String recordId, String activityId) throws Exception {
    begin();
    String allowTransition = "";
    try {
      String sql = " select allowTransition from JSDB.JSF_P_ACTIVITY  where ttable_id = " + 
        tableId + " and trecord_id = " + 
        recordId + " and wf_activity_id = " + activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        allowTransition = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    end();
    return allowTransition;
  }
  
  public List getIdValue(String tableName, String fieldName) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery(" select JSDB." + tableName + "_id, " + fieldName + " from " + tableName + " ");
      while (rs.next()) {
        String[] str = { (new StringBuilder(String.valueOf(rs.getInt(1)))).toString(), rs.getString(2) };
        alist.add(str);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getTransactType(String tableId, String recordId, String activityId) throws Exception {
    if (tableId == null || "".equals(tableId) || "null".equals(tableId))
      tableId = "0"; 
    if (recordId == null || "".equals(recordId) || "null".equals(recordId))
      recordId = "0"; 
    if (activityId == null || "".equals(activityId) || "null".equals(activityId))
      activityId = "0"; 
    String transactType = "";
    begin();
    ResultSet rs = null;
    try {
      rs = this.stmt.executeQuery("select transactType from JSDB.JSF_P_ACTIVITY where  ttable_id = " + 
          tableId + " and trecord_id = " + recordId + 
          " and wf_activity_id = " + activityId);
      if (rs.next())
        transactType = rs.getString("transactType"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return transactType;
  }
  
  public void newRandomWork(String workId, String userId, String remindFieldValue) throws Exception {
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select workfiletype, worksubmitperson, worksubmittime,wf_submitemployee_id, workprocess_id, worktable_id, workrecord_id from JSDB.JSF_WORK where wf_work_id = " + 
          
          workId);
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitemployee_id = "";
      String workprocess_id = "";
      String worktable_id = "";
      String workrecord_id = "";
      if (rs.next()) {
        workFileType = rs.getString(1);
        workSubmitPerson = rs.getString(2);
        workSubmitTime = rs.getString(3);
        if (workSubmitTime.indexOf(".") >= 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitemployee_id = rs.getString(4);
        workprocess_id = rs.getString(5);
        worktable_id = rs.getString(6);
        workrecord_id = rs.getString(7);
      } 
      rs.close();
      String newWorkId = getTableId();
      Date now = new Date();
      String workTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        workTitle = String.valueOf(remindFieldValue) + workFileType;
      } else {
        workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
      } 
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep,  worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity,  worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, worktype, workprocess_id, worktable_id, workrecord_id, workdeadline,  workpresstime, workcreatedate, workstartflag ) values (" + 





          
          newWorkId + "," + userId + ", 0, '" + workFileType + "', '', '" + 
          workTitle + "', '','/jsoa/WorkFlowProcAction.do?flowpara=1',1,-100,'" + 
          workSubmitPerson + "','" + workSubmitTime + "'," + 
          wf_submitemployee_id + ",0,0," + workprocess_id + "," + worktable_id + "," + workrecord_id + 
          ",-1,-1,'" + now.toLocaleString() + "',0 " + 
          ")";
      } else {
        tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep,  worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity,  worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, worktype, workprocess_id, worktable_id, workrecord_id, workdeadline,  workpresstime, workcreatedate, workstartflag ) values (" + 





          
          newWorkId + "," + userId + ", 0, '" + workFileType + "', '', '" + 
          workTitle + "', '','/jsoa/WorkFlowProcAction.do?flowpara=1',1,-100,'" + 
          workSubmitPerson + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
          wf_submitemployee_id + ",0,0," + workprocess_id + "," + worktable_id + "," + workrecord_id + 
          ",-1,-1,JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''),0 " + 
          ")";
      } 
      this.stmt.execute(tmpSql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public String getActivityType(String activityid, String tableId, String recordId) throws Exception {
    begin();
    String activityType = "";
    try {
      ResultSet rs = this.stmt.executeQuery("select activityType from JSDB.JSF_P_ACTIVITY  where wf_activity_id = " + 
          activityid + " and " + 
          " ttable_id = " + tableId + " and trecord_id = " + recordId);
      if (rs.next())
        activityType = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return activityType;
  }
  
  public List getFormFields(String tableId) throws Exception {
    begin();
    ArrayList<TFieldVO> alist = new ArrayList();
    ResultSet rs = this.stmt.executeQuery(" select field_id, field_desname, field_name, field_show,  field_len, field_value, type_name, field_null, field_default from JSDB.tfield, JSDB.tType  where field_type = type_id and field_table = " + 

        
        tableId + 
        " and field_hide = 0 order by field_code");
    try {
      while (rs.next()) {
        TFieldVO fieldVO = new TFieldVO();
        fieldVO.setFieldId(rs.getInt("field_id"));
        fieldVO.setFieldDesName(rs.getString("field_desname"));
        fieldVO.setFieldName(rs.getString("field_name"));
        fieldVO.setFieldShow(rs.getInt("field_show"));
        fieldVO.setFieldLen(rs.getInt("field_len"));
        fieldVO.setFieldValue(rs.getString("field_value"));
        fieldVO.setFieldType(rs.getString("type_name"));
        fieldVO.setFieldNull(rs.getInt("field_null"));
        fieldVO.setFieldDefault(rs.getString("field_default"));
        alist.add(fieldVO);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      end();
    } 
    return alist;
  }
  
  public List getChildField(String tableId, String childFieldString) throws Exception {
    ArrayList<TFieldVO> childFieldList = new ArrayList();
    begin();
    int firstPostion = childFieldString.indexOf(";");
    String childTableName = childFieldString.substring(0, firstPostion);
    try {
      ResultSet rs = this.stmt.executeQuery("select limit_field from JSDB.tlimit,JSDB.ttable where  limit_table = table_id and table_name = '" + 
          
          childTableName + "' and limit_prytable = " + tableId);
      int limit_field = 0;
      if (rs.next())
        limit_field = rs.getInt(1); 
      rs.close();
      String[] childFieldName = childFieldString.substring(firstPostion + 1).split(";");
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < childFieldName.length; i++)
        sb.append("field_name = '" + childFieldName[i] + "' or "); 
      String childFieldWhere = sb.toString();
      if (childFieldWhere.endsWith("or "))
        childFieldWhere = childFieldWhere.substring(0, childFieldWhere.length() - 3); 
      rs = this.stmt.executeQuery(" select field_id, field_desname, field_name, field_show,  field_len, field_value, type_name, field_null, field_default from JSDB.tfield, JSDB.tType, JSDB.ttable  where field_type = type_id and field_table = table_id  and table_name = '" + 


          
          childTableName + "' and (" + 
          childFieldWhere + " and field_id <> " + limit_field + ")");
      while (rs.next()) {
        TFieldVO fieldVO = new TFieldVO();
        fieldVO.setFieldId(rs.getInt("field_id"));
        fieldVO.setFieldDesName(rs.getString("field_desname"));
        fieldVO.setFieldName(rs.getString("field_name"));
        fieldVO.setFieldShow(rs.getInt("field_show"));
        fieldVO.setFieldLen(rs.getInt("field_len"));
        fieldVO.setFieldValue(rs.getString("field_value"));
        fieldVO.setFieldType(rs.getString("type_name"));
        fieldVO.setFieldNull(rs.getInt("field_null"));
        fieldVO.setFieldDefault(rs.getString("field_default"));
        childFieldList.add(fieldVO);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return childFieldList;
  }
  
  public List getFieldAndValue(String tableId, String recordId, String pageId) throws Exception {
    begin();
    ResultSet rs = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      String tableName = "";
      rs = this.stmt.executeQuery("select table_name from JSDB.ttable where table_id = " + tableId);
      if (rs.next())
        tableName = rs.getString("table_name"); 
      rs.close();
      rs = this.stmt.executeQuery("select curFieldStr from JSDB.jsf_p_flow where table_id=" + pageId + " and record_id=" + recordId);
      String curFieldStr = "";
      if (rs.next())
        curFieldStr = rs.getString(1); 
      if (curFieldStr.equals(""))
        curFieldStr = "'-1'"; 
      rs = this.stmt.executeQuery(" select field_name, field_id, field_desname, field_show, field_value, field_null, type_name, field_len from JSDB.tfield,JSDB.ttype  where type_id=field_type and field_table = " + 
          
          tableId + " and field_id in (" + curFieldStr + ") order by field_code");
      while (rs.next()) {
        String[] arrayOfString = { "", "", "", "", "", "", "", "", "" };
        arrayOfString[0] = rs.getString("field_name");
        arrayOfString[1] = rs.getString("field_id");
        arrayOfString[2] = rs.getString("field_desname");
        arrayOfString[3] = rs.getString("field_show");
        arrayOfString[4] = rs.getString("field_value");
        arrayOfString[6] = rs.getString("field_null");
        arrayOfString[7] = rs.getString("type_name");
        arrayOfString[8] = rs.getString("field_len");
        alist.add(arrayOfString);
      } 
      rs.close();
      String[] str = (String[])null;
      for (int i = 0; i < alist.size(); i++) {
        str = alist.get(i);
        rs = this.stmt.executeQuery("select " + str[0] + " from JSDB." + tableName + 
            " where " + tableName + "_id = " + recordId);
        if (rs.next())
          if (str[7].toUpperCase().equals("CLOB")) {
            Clob clob = (Clob)rs.getObject(1);
            if (clob != null)
              str[5] = clob.getSubString(1L, (int)clob.length()); 
          } else {
            str[5] = rs.getString(1);
          }  
        rs.close();
        alist.set(i, str);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getChildTableValue(String childTable, List childField, String tableId, String recordId) throws Exception {
    begin();
    ArrayList<String[]> childTableValue = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("select field_name from JSDB.tlimit, JSDB.tfield, ttable  where limit_field = field_id and table_id = limit_table  and limit_prytable = " + 
          
          tableId + " and " + 
          " table_name = '" + childTable + "'");
      String assoField = "";
      if (rs.next())
        assoField = rs.getString(1); 
      rs.close();
      StringBuffer fieldStr = new StringBuffer();
      for (int i = 0; i < childField.size(); i++)
        fieldStr.append((new StringBuilder()).append(childField.get(i)).append(",").toString()); 
      rs = this.stmt.executeQuery("select " + fieldStr.substring(0, fieldStr.length() - 1) + 
          " from JSDB." + childTable + " where " + assoField + " = '" + 
          recordId + "'");
      while (rs.next()) {
        String[] tmp = new String[childField.size()];
        for (int j = 1; j <= childField.size(); j++)
          tmp[j - 1] = rs.getString(j); 
        childTableValue.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return childTableValue;
  }
  
  public List getDealProc(String tableId, String recordId, String procType) throws Exception {
    begin();
    List<String[]> list = new ArrayList();
    try {
      String sql = "Select JSDB.jsf_dealwith.wf_dealwith_id,JSDB.jsf_dealwith.activityName,JSDB.jsf_dealWithcomment.dealWithTime,JSDB.org_employee.empName,JSDB.jsf_p_Activity.participantType,(Select Count(JSDB.jsf_work.wf_work_id) From JSDB.jsf_work Where JSDB.jsf_work.worktable_id=JSDB.jsf_dealwith.databasetable_id And JSDB.jsf_work.workstatus<>2 and JSDB.jsf_work.workstatus<>102 And JSDB.jsf_work.workrecord_id=JSDB.jsf_dealwith.databaserecord_id And workdonewithdate Is Not Null) as aa,JSDB.jsf_dealwith.curActivityStatus,JSDB.jsf_dealwith.nextActivityName,JSDB.jsf_dealwith.curStepCount,JSDB.jsf_dealWithcomment.standforusername,JSDB.jsf_dealwith.activityClass,JSDB.jsf_dealwith.subProcWorkId,JSDB.jsf_dealwith.curStepCount From JSDB.jsf_dealwith,JSDB.jsf_dealWithcomment,JSDB.org_employee,JSDB.jsf_p_Activity Where JSDB.jsf_dealwith.wf_dealWith_id=JSDB.jsf_dealWithcomment.wf_dealWith_id And JSDB.jsf_dealWithcomment.dealwithemployee_id=emp_id And JSDB.jsf_p_Activity.wf_activity_id = JSDB.jsf_dealwith.activity_id And JSDB.jsf_dealwith.databasetable_id=JSDB.jsf_p_Activity.ttable_id And JSDB.jsf_dealwith.databaserecord_id=JSDB.jsf_p_Activity.trecord_id ";
      if (tableId.indexOf(",") >= 0) {
        sql = String.valueOf(sql) + "And JSDB.jsf_dealwith.databaseTable_id in (" + tableId + ")";
      } else {
        sql = String.valueOf(sql) + "And JSDB.jsf_dealwith.databaseTable_id=" + tableId;
      } 
      sql = String.valueOf(sql) + " and JSDB.jsf_dealwith.databaseRecord_id=" + recordId + 
        " And JSDB.jsf_dealwith.nextActivityId<>-1 " + 
        " Order By JSDB.jsf_dealwith.wf_dealwith_id, JSDB.jsf_dealWithcomment.dealwithtime";
      ResultSet rs = this.stmt.executeQuery(sql);
      int lastStepCount = 0;
      String workFileType = "", workMainLinkFile = "", workSubmitPerson = "";
      String workCurStep = "", wf_submitEmployee_id = "", workType = "";
      String workActivity = "", workTable_id = "", workRecord_id = "", workStatus = "";
      String workSubmitTime = "", workProcess_id = "", workStepCount = "";
      while (rs != null && rs.next()) {
        String[] tmp = { 
            "", "", "", "", "", "", "", "", "", "", 
            "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        tmp[6] = rs.getString(7);
        tmp[7] = rs.getString(8);
        tmp[8] = rs.getString(9);
        tmp[9] = rs.getString(10);
        tmp[10] = rs.getString(11);
        tmp[11] = rs.getString(12);
        tmp[14] = rs.getString(13);
        list.add(tmp);
      } 
      rs.close();
      if (list.size() > 0) {
        if (tableId.indexOf(",") >= 0) {
          tableId = "worktable_id in (" + tableId + ")";
        } else {
          tableId = "worktable_id=" + tableId;
        } 
        String tmpSql = "", tmpSql2 = "";
        for (int i = 0; i < list.size(); i++) {
          String[] tmp = list.get(i);
          if (tmp[6].equals("1")) {
            if ("oracle".equals(SystemCommon.getDatabaseType())) {
              tmpSql = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id = wf_curemployee_id and " + 
                tableId + " and workrecord_id=" + recordId + 
                " and workStepCount = " + (
                Integer.parseInt(tmp[8]) + 1) + " and (isStandForWork=0 or isStandForWork='' or isStandForWork is null) and workStatus=0";
              tmpSql2 = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id = wf_curemployee_id and " + 
                tableId + " and workrecord_id=" + recordId + 
                " and workStepCount = " + (
                Integer.parseInt(tmp[8]) + 1) + " and (isStandForWork=0 or isStandForWork='' or isStandForWork is null) and workStatus=2";
            } else {
              tmpSql = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id = wf_curemployee_id and " + 
                tableId + " and workrecord_id=" + recordId + 
                " and workStepCount = " + (
                Integer.parseInt(tmp[8]) + 1) + " and (isStandForWork=0 or isStandForWork is null) and workStatus=0";
              tmpSql2 = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id = wf_curemployee_id and " + 
                tableId + " and workrecord_id=" + recordId + 
                " and workStepCount = " + (
                Integer.parseInt(tmp[8]) + 1) + " and (isStandForWork=0 or isStandForWork is null) and workStatus=2";
            } 
          } else if ("oracle".equals(SystemCommon.getDatabaseType())) {
            tmpSql = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id=wf_curemployee_id and " + 
              tableId + " and workrecord_id=" + recordId + 
              " and workStepCount = " + tmp[8] + 
              " and (isStandForWork=0 or isStandForWork='' or isStandForWork is null) and workStatus=0";
            tmpSql2 = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id=wf_curemployee_id and " + 
              tableId + " and workrecord_id=" + recordId + 
              " and workStepCount = " + tmp[8] + 
              " and (isStandForWork=0 or isStandForWork='' or isStandForWork is null) and workStatus=2";
          } else {
            tmpSql = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id=wf_curemployee_id and " + 
              tableId + " and workrecord_id=" + recordId + 
              " and workStepCount = " + tmp[8] + 
              " and (isStandForWork=0 or isStandForWork is null) and workStatus=0";
            tmpSql2 = "select empName from JSDB.org_employee,JSDB.JSF_WORK where emp_id=wf_curemployee_id and " + 
              tableId + " and workrecord_id=" + recordId + 
              " and workStepCount = " + tmp[8] + 
              " and (isStandForWork=0 or isStandForWork is null) and workStatus=2";
          } 
          StringBuffer sb = new StringBuffer();
          StringBuffer sb2 = new StringBuffer();
          rs = this.stmt.executeQuery(tmpSql);
          while (rs.next())
            sb.append(String.valueOf(rs.getString(1)) + " "); 
          rs.close();
          tmp[8] = sb.toString();
          rs = this.stmt.executeQuery(tmpSql2);
          while (rs.next())
            sb2.append(String.valueOf(rs.getString(1)) + " "); 
          rs.close();
          tmp[14] = sb2.toString();
          if (tmp[10].equals("0")) {
            rs = this.stmt.executeQuery("select workFileType,workMainLinkFile,workSubmitPerson,workCurStep,wf_submitEmployee_id,workType,workActivity,workTable_id,workRecord_id,workStatus,workSubmitTime,workProcess_id,workStepCount from JSDB.JSF_WORK where wf_work_id=" + 
                
                tmp[11]);
            if (rs.next()) {
              workFileType = rs.getString("workFileType");
              workMainLinkFile = rs.getString("workMainLinkFile");
              workSubmitPerson = rs.getString("workSubmitPerson");
              workCurStep = rs.getString("workCurStep");
              wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
              workType = rs.getString("workType");
              workActivity = rs.getString("workActivity");
              workTable_id = rs.getString("workTable_id");
              workRecord_id = rs.getString("workRecord_id");
              workStatus = rs.getString("workStatus");
              workSubmitTime = rs.getString("workSubmitTime");
              workProcess_id = rs.getString("workProcess_id");
              workStepCount = rs.getString("workStepCount");
            } 
            rs.close();
            if (workStatus.equals("100")) {
              tmp[12] = String.valueOf(workFileType) + "已经办理完毕！";
            } else if (workStatus.equals("")) {
              tmp[12] = "";
            } else {
              tmp[12] = "<label class=mustFillcolor>" + workFileType + "正在办理中！</label>";
            } 
            tmp[13] = String.valueOf(workMainLinkFile) + "&search=&workTitle=&activityName=" + workCurStep + "&submitPersonId=" + 
              wf_submitEmployee_id + "&submitPerson=" + workSubmitPerson + "&work=" + tmp[11] + 
              "&workType=" + workType + "&activity=" + workActivity + "&table=" + workTable_id + 
              "&record=" + workRecord_id + "&processName=" + workFileType + "&workStatus=" + workStatus + 
              "&submitTime=" + workSubmitTime + "&processId=" + workProcess_id + "&stepCount=" + 
              workStepCount;
            if ("0".equals(tmp[11]))
              tmp[13] = "0"; 
          } 
          list.set(i, tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return list;
  }
  
  public String getNextStepUserName(String tableId, String recordId, String nextActivityId, String curStep) throws Exception {
    begin();
    ResultSet rs = null;
    StringBuffer userName = new StringBuffer(512);
    try {
      if (tableId.indexOf(",") > 0) {
        tableId = "workTable_id in(" + tableId + ")";
      } else {
        tableId = "workTable_id=" + tableId;
      } 
      rs = this.stmt.executeQuery("select empname from JSDB.org_employee,JSDB.JSF_WORK where emp_id = WF_CurEmployee_Id and " + 
          
          tableId + " and workRecord_id = " + recordId + " and " + 
          "workactivity = " + nextActivityId + " and workStepCount = " + 
          curStep + " and isStandForWork = 0 and workStatus=0");
      while (rs.next())
        userName.append(String.valueOf(rs.getString(1)) + " "); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return userName.toString();
  }
  
  public List getNextActivity(String tableId, String recordId, String activityId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = "select transitionTo,conditionField,compareValue,relation,expression,formType from JSDB.jsf_p_Activity, JSDB.jsf_p_Transition, JSDB.jsf_p_TR,JSDB.JSF_WORKFLOWPROCESS where JSDB.jsf_p_Activity.wf_proceedActivity_id = JSDB.jsf_p_Transition.wf_proceedActivity_id and JSDB.jsf_p_Transition.wf_proceedTransition_id = JSDB.jsf_p_TR.wf_proceedTransition_id  and JSDB.JSF_WORKFLOWPROCESS.WF_WORKFLOWPROCESS_ID=JSDB.jsf_p_Activity.WF_WORKFLOWPROCESS_ID ";
      if (tableId.indexOf(",") >= 0) {
        sql = String.valueOf(sql) + "and ttable_id in(" + tableId + ")";
      } else {
        sql = String.valueOf(sql) + "and ttable_id=" + tableId;
      } 
      sql = String.valueOf(sql) + " and trecord_id=" + recordId + 
        " and transitionFrom=" + activityId + " order by conditionField";
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getNextActivityWithExp(String tableId, String recordId, String activityId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql = "select transitionTo,conditionField,compareValue,relation,expression,formType from JSDB.jsf_p_Activity, JSDB.jsf_p_Transition, JSDB.jsf_p_TR,JSDB.JSF_WORKFLOWPROCESS where JSDB.jsf_p_Activity.wf_proceedActivity_id = JSDB.jsf_p_Transition.wf_proceedActivity_id and JSDB.jsf_p_Transition.wf_proceedTransition_id = JSDB.jsf_p_TR.wf_proceedTransition_id  and JSDB.JSF_WORKFLOWPROCESS.WF_WORKFLOWPROCESS_ID=JSDB.jsf_p_Activity.WF_WORKFLOWPROCESS_ID ";
      if (tableId.indexOf(",") >= 0) {
        sql = String.valueOf(sql) + "and ttable_id in(" + tableId + ")";
      } else {
        sql = String.valueOf(sql) + "and ttable_id=" + tableId;
      } 
      sql = String.valueOf(sql) + " and trecord_id=" + recordId + 
        " and transitionFrom=" + activityId + " order by conditionField";
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getFieldName(String fieldId) throws Exception {
    begin();
    String fieldName = "";
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT FIELD_NAME FROM JSDB.TFIELD WHERE FIELD_ID=" + fieldId);
      if (rs.next()) {
        fieldName = rs.getString(1);
        rs.close();
      } else {
        rs.close();
        rs = this.stmt.executeQuery("SELECT IMMOFIELD_POFIELD FROM JSDB.JSF_IMMOBILITYFIELD WHERE WF_IMMOFIELD_ID=" + fieldId);
        if (rs.next())
          fieldName = rs.getString(1); 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return fieldName;
  }
  
  public List getModuleProc(String moduleId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = this.stmt.executeQuery("select a.wf_workflowprocess_id, a.remindField, a.processType, a.workflowprocessName from JSDB.jsf_workflowprocess a, JSDB.jsf_package b where a.wf_package_id = b.wf_package_id and b.wf_module_id = " + 
          moduleId);
      while (rs.next()) {
        String[] tmp = { "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        alist.add(tmp);
      } 
      rs.close();
      if (alist.size() == 0) {
        String[] tmp = { "", "", "", "" };
        alist.add(tmp);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getWriteControl(String processId, String moduleId) throws Exception {
    begin();
    StringBuffer sb = new StringBuffer();
    try {
      ResultSet rs = this.stmt.executeQuery("select a.immoField_poField from JSDB.jsf_immobilityField a, JSDB.jsf_immobilityForm b where a.wf_immoForm_id = b.wf_immoForm_id and b.wf_module_id=" + 
          moduleId + " and a.wf_immoField_id in " + 
          "(select writeControlField from JSDB.jsf_workflowWriteControl where wf_workflowprocess_id = " + 
          processId + ")");
      while (rs.next())
        sb.append("," + rs.getString(1) + ","); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return sb.toString();
  }
  
  public String getCancelReason(String workId) throws Exception {
    begin();
    String cancelReason = "";
    try {
      ResultSet rs = this.stmt.executeQuery("select workCancelReason from JSDB.JSF_WORK where wf_work_id = " + workId);
      if (rs.next())
        cancelReason = rs.getString(1); 
      rs.close();
      if (cancelReason == null || "null".equals(cancelReason) || "NULL".equals(cancelReason))
        cancelReason = ""; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return cancelReason;
  }
  
  public String[] getCurCompleteWork(String tableId, String recordId) throws Exception {
    begin();
    String[] work = { "", "", "", "", "" };
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_work_id, worktype, worksubmittime, workprocess_id, workmainlinkfile from JSDB.JSF_WORK where worktable_id in(" + 
          tableId + ") and workrecord_id = " + recordId + " and workStartFlag = 1 " + 
          "and (workDoneWithDate is not null)");
      while (rs.next()) {
        work[0] = rs.getString(1);
        work[1] = rs.getString(2);
        work[2] = rs.getString(3);
        work[3] = rs.getString(4);
        work[4] = rs.getString(5);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return work;
  }
  
  public String insertCurFieldStr(String processId, String tTableId, String tRecordId, String curFieldStr) throws Exception {
    begin();
    String result = "-1";
    try {
      String domainId = "0";
      this.stmt.execute("insert into JSDB.jsf_p_flow (wf_proceedflow_id, wf_workflowprocess_id, table_id, record_id, curFieldStr, DOMAIN_ID) values (" + getTableId() + ", " + processId + ", " + tTableId + ", " + tRecordId + ", '" + curFieldStr + "'," + domainId + ")");
      result = "0";
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } finally {}
    return result;
  }
  
  public String getNextStepPassRoundUserName(String tableId, String recordId, String nextActivityId, String curStep) throws Exception {
    begin();
    ResultSet rs = null;
    StringBuffer userName = new StringBuffer();
    try {
      String sql = "select empname from JSDB.org_employee,JSDB.JSF_WORK where emp_id = WF_CurEmployee_Id and workTable_id = " + 
        
        tableId + " and workRecord_id = " + recordId + " and workStepCount = " + 
        curStep + " and isStandForWork = 0 and workStatus=2";
      if (curStep != null && curStep.trim().length() > 0 && !curStep.toUpperCase().equals("NULL")) {
        rs = this.stmt.executeQuery(sql);
        while (rs.next())
          userName.append(String.valueOf(rs.getString(1)) + " "); 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return userName.toString();
  }
  
  public List getRoleUserIDAndName(String roleUserString, String submitPersonId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    String sql = "";
    try {
      ResultSet rs = null;
      if (roleUserString.indexOf("and") > 0) {
        String roleId = roleUserString.split("and")[0];
        String roleOrg = roleUserString.split("and")[1];
        String[] executors = submitPersonId.split(";");
        if (roleOrg.length() > 2 && (roleOrg.startsWith("-") || "0".equals(roleOrg))) {
          roleOrg = roleOrg.substring(0, 2);
          if (executors.length > 1 && !"".equals(executors[1])) {
            submitPersonId = executors[1];
          } else {
            submitPersonId = executors[0];
          } 
        } else {
          submitPersonId = executors[0];
        } 
        sql = "select org_organization.org_Id, org_organization.orgIdString from JSDB.org_organization, org_organization_user where org_organization.org_Id = org_organization_user.org_Id and org_organization_user.emp_id=" + 
          submitPersonId;
        rs = this.stmt.executeQuery(sql);
        String orgId = "", orgIdString = "";
        if (rs.next()) {
          orgId = rs.getString(1);
          orgIdString = rs.getString(2);
        } 
        rs.close();
        String sidelineOrg = "", slideOrgIdString = "";
        sql = "select sidelineorg from org_employee where emp_id=" + submitPersonId;
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          sidelineOrg = rs.getString(1); 
        rs.close();
        if (sidelineOrg != null && !"".equals(sidelineOrg) && sidelineOrg.length() > 2) {
          String[] sidelineOrgArr = sidelineOrg.substring(1, sidelineOrg.length() - 1).split("\\*\\*");
          for (int i = 0; i < sidelineOrgArr.length; i++) {
            orgId = String.valueOf(orgId) + "," + sidelineOrgArr[i];
            sql = "select orgidstring from org_organization where org_id=" + sidelineOrgArr[i];
            rs = this.stmt.executeQuery(sql);
            if (rs.next())
              orgIdString = String.valueOf(orgIdString) + rs.getString(1); 
            rs.close();
          } 
        } 
        if (roleOrg.equals("-1")) {
          String sidelineSql = "";
          if (orgId.indexOf(",") > 0) {
            sidelineSql = "C.ORG_ID in(" + orgId + ")";
            String[] orgIdArr = orgId.split(",");
            for (int i = 0; i < orgIdArr.length; i++)
              sidelineSql = String.valueOf(sidelineSql) + " or A.SIDELINEORG like '%*" + orgId + "*%'"; 
          } else {
            sidelineSql = "C.ORG_ID=" + orgId + " or A.SIDELINEORG like '%*" + orgId + "*%'";
          } 
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND A.EMP_ID=C.EMP_ID AND B.ROLE_ID=" + roleId + " AND (" + sidelineSql + ") order by A.USERORDERCODE";
        } else if (roleOrg.equals("-2")) {
          String databaseType = SystemCommon.getDatabaseType();
          String sidelineSql = "";
          if (databaseType.indexOf("mysql") >= 0) {
            rs = this.stmt.executeQuery("select org_Id from JSDB.org_organization where ('" + orgIdString + "' like concat('%$', org_Id, '$%'))");
          } else {
            rs = this.stmt.executeQuery("select org_Id from JSDB.org_organization where ('" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(ORG_ID)), '$%'))");
          } 
          while (rs.next())
            sidelineSql = String.valueOf(sidelineSql) + " or A.SIDELINEORG like '%*" + rs.getString(1) + "*%'"; 
          rs.close();
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND A.EMP_ID=C.EMP_ID AND B.ROLE_ID=" + roleId + " AND ('" + orgIdString + "' like concat('%$', c.ORG_ID, '$%') " + sidelineSql + ")" + " order by A.USERORDERCODE";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND A.EMP_ID=C.EMP_ID AND B.ROLE_ID=" + roleId + " AND ('" + orgIdString + "' locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(c.ORG_ID)), '$')>0 " + sidelineSql + ")" + " order by A.USERORDERCODE";
          } else {
            sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND A.EMP_ID=C.EMP_ID AND B.ROLE_ID=" + roleId + " AND ('" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(c.ORG_ID)), '$%') " + sidelineSql + ")" + " order by A.USERORDERCODE";
          } 
        } else if (roleOrg.equals("-3")) {
          StringBuffer sb = new StringBuffer();
          StringBuffer sb2 = new StringBuffer();
          if (orgId.indexOf(",") > 0) {
            String[] orgIdArr = orgId.split(",");
            for (int i = 0; i < orgIdArr.length; i++) {
              if (i == 0) {
                sql = "orgIdString like '%$" + orgIdArr[i] + "$%'";
              } else {
                sql = String.valueOf(sql) + " or orgIdString like '%$" + orgIdArr[i] + "$%'";
              } 
            } 
          } else {
            sql = "orgIdString like '%$" + orgId + "$%'";
          } 
          sql = "select org_Id from JSDB.org_organization where (" + sql + ")";
          rs = this.stmt.executeQuery(sql);
          String sidelineSql = "";
          while (rs.next()) {
            sb.append(rs.getString(1)).append(",");
            sb2.append("*").append(rs.getString(1)).append("*");
            sidelineSql = String.valueOf(sidelineSql) + " or A.SIDELINEORG like '%*" + rs.getString(1) + "*%'";
          } 
          rs.close();
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND A.EMP_ID=C.EMP_ID AND B.ROLE_ID=" + roleId + " AND (C.ORG_ID in (" + sb.toString().substring(0, sb.toString().length() - 1) + ") " + sidelineSql + ")" + " order by A.USERORDERCODE";
        } else if (roleOrg.equals("-4")) {
          String workAddressId = "-1";
          rs = this.stmt.executeQuery("select workaddress from JSDB.ORG_EMPLOYEE where EMP_ID =" + submitPersonId);
          while (rs.next())
            workAddressId = rs.getString(1); 
          rs.close();
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND B.ROLE_ID=" + roleId + " AND a.workaddress=" + workAddressId + " order by A.USERORDERCODE";
        } else if (Long.parseLong(roleOrg) > 0L) {
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_USER_ROLE B, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND A.EMP_ID=C.EMP_ID AND B.ROLE_ID=" + roleId + " AND C.ORG_ID IN (SELECT ORG_ID FROM JSDB.ORG_ORGANIZATION WHERE ORGIDSTRING LIKE '%$" + roleOrg + "$%')" + " order by A.USERORDERCODE";
        } 
      } else {
        sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A,JSDB.ORG_USER_ROLE B WHERE A.userisactive=1 and A.userisdeleted=0 and A.EMP_ID=B.EMP_ID AND B.ROLE_ID=" + roleUserString + " order by A.USERORDERCODE";
      } 
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = new String[2];
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getPositionUserIDAndName(String roleUserString, String submitPersonId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    String sql = "";
    try {
      ResultSet rs = null;
      if (roleUserString.indexOf("and") > 0) {
        String roleId = roleUserString.split("and")[0];
        String roleOrg = roleUserString.split("and")[1];
        String[] executors = submitPersonId.split(";");
        if (roleOrg.length() > 2 && (roleOrg.startsWith("-") || "0".equals(roleOrg))) {
          roleOrg = roleOrg.substring(0, 2);
          if (executors.length > 1 && !"".equals(executors[1])) {
            submitPersonId = executors[1];
          } else {
            submitPersonId = executors[0];
          } 
        } else {
          submitPersonId = executors[0];
        } 
        sql = "select org_organization.org_Id, org_organization.orgIdString from JSDB.org_organization, org_organization_user where org_organization.org_Id = org_organization_user.org_Id and org_organization_user.emp_id=" + 
          submitPersonId;
        rs = this.stmt.executeQuery(sql);
        String orgId = "", orgIdString = "";
        if (rs.next()) {
          orgId = rs.getString(1);
          orgIdString = rs.getString(2);
        } 
        rs.close();
        String sidelineOrg = "", slideOrgIdString = "";
        sql = "select sidelineorg from org_employee where emp_id=" + submitPersonId;
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          sidelineOrg = rs.getString(1); 
        rs.close();
        if (sidelineOrg != null && !"".equals(sidelineOrg) && sidelineOrg.length() > 2) {
          String[] sidelineOrgArr = sidelineOrg.substring(1, sidelineOrg.length() - 1).split("\\*\\*");
          for (int i = 0; i < sidelineOrgArr.length; i++) {
            orgId = String.valueOf(orgId) + "," + sidelineOrgArr[i];
            sql = "select orgidstring from org_organization where org_id=" + sidelineOrgArr[i];
            rs = this.stmt.executeQuery(sql);
            if (rs.next())
              orgIdString = String.valueOf(orgIdString) + rs.getString(1); 
            rs.close();
          } 
        } 
        if (roleOrg.equals("-1")) {
          String sidelineSql = "";
          if (orgId.indexOf(",") > 0) {
            sidelineSql = "C.ORG_ID in(" + orgId + ")";
            String[] orgIdArr = orgId.split(",");
            for (int i = 0; i < orgIdArr.length; i++)
              sidelineSql = String.valueOf(sidelineSql) + " or A.SIDELINEORG like '%*" + orgId + "*%'"; 
          } else {
            sidelineSql = "C.ORG_ID=" + orgId + " or A.SIDELINEORG like '%*" + orgId + "*%'";
          } 
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_ORGANIZATION_USER C WHERE A.EMP_ID=C.EMP_ID AND A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 AND (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND (" + sidelineSql + ") order by A.USERORDERCODE";
        } else if (roleOrg.equals("-2")) {
          String databaseType = SystemCommon.getDatabaseType();
          String sidelineSql = "";
          if (databaseType.indexOf("mysql") >= 0) {
            rs = this.stmt.executeQuery("select org_Id from JSDB.org_organization where ('" + orgIdString + "' like concat('%$', org_Id, '$%'))");
          } else {
            rs = this.stmt.executeQuery("select org_Id from JSDB.org_organization where ('" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(ORG_ID)), '$%'))");
          } 
          while (rs.next())
            sidelineSql = String.valueOf(sidelineSql) + " or A.SIDELINEORG like '%*" + rs.getString(1) + "*%'"; 
          rs.close();
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A,  ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and A.EMP_ID=C.EMP_ID AND (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND ('" + orgIdString + "' like concat('%$', c.ORG_ID, '$%')  " + sidelineSql + ")" + " order by A.USERORDERCODE";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A,  ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and A.EMP_ID=C.EMP_ID AND (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND ('" + orgIdString + "' locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(c.ORG_ID)), '$')>0  " + sidelineSql + ")" + " order by A.USERORDERCODE";
          } else {
            sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A,  ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and A.EMP_ID=C.EMP_ID AND (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND ('" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(c.ORG_ID)), '$%')  " + sidelineSql + ")" + " order by A.USERORDERCODE";
          } 
        } else if (roleOrg.equals("-3")) {
          StringBuffer sb = new StringBuffer();
          StringBuffer sb2 = new StringBuffer();
          if (orgId.indexOf(",") > 0) {
            String[] orgIdArr = orgId.split(",");
            for (int i = 0; i < orgIdArr.length; i++) {
              if (i == 0) {
                sql = "orgIdString like '%$" + orgIdArr[i] + "$%'";
              } else {
                sql = String.valueOf(sql) + " or orgIdString like '%$" + orgIdArr[i] + "$%'";
              } 
            } 
          } else {
            sql = "orgIdString like '%$" + orgId + "$%'";
          } 
          sql = "select org_Id from JSDB.org_organization where (" + sql + ")";
          rs = this.stmt.executeQuery(sql);
          String sidelineSql = "";
          while (rs.next()) {
            sb.append(rs.getString(1)).append(",");
            sb2.append("*").append(rs.getString(1)).append("*");
            sidelineSql = String.valueOf(sidelineSql) + " or A.SIDELINEORG like '%*" + rs.getString(1) + "*%'";
          } 
          rs.close();
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and A.EMP_ID=C.EMP_ID AND (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND (C.ORG_ID in (" + sb.toString().substring(0, sb.toString().length() - 1) + ") " + sidelineSql + ")" + " order by A.USERORDERCODE";
        } else if (roleOrg.equals("-4")) {
          String workAddressId = "-1";
          rs = this.stmt.executeQuery("select workaddress from JSDB.ORG_EMPLOYEE where EMP_ID =" + submitPersonId);
          while (rs.next())
            workAddressId = rs.getString(1); 
          rs.close();
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND a.workaddress=" + workAddressId + " order by A.USERORDERCODE";
        } else if (Long.parseLong(roleOrg) > 0L) {
          sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A, ORG_ORGANIZATION_USER C WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and A.EMP_ID=C.EMP_ID AND (A.emppositionid=" + roleId + " or A.emppositionotherid='" + roleId + "') AND C.ORG_ID IN (SELECT ORG_ID FROM JSDB.ORG_ORGANIZATION WHERE ORGIDSTRING LIKE '%$" + roleOrg + "$%')" + " order by A.USERORDERCODE";
        } 
      } else {
        sql = "SELECT DISTINCT A.EMP_ID,A.EMPNAME,A.USERORDERCODE FROM JSDB.ORG_EMPLOYEE A WHERE A.userisactive=1 and A.useraccounts like '%_%' and A.userisdeleted=0 and (A.emppositionid=" + roleUserString + " or A.emppositionotherid='" + roleUserString + "') order by A.USERORDERCODE";
      } 
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = new String[2];
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public Boolean hasPrintRight(String processId, String userId, String orgId, String groupId) throws Exception {
    Boolean hasRight = Boolean.FALSE;
    begin();
    try {
      ResultSet rs = null;
      rs = this.stmt.executeQuery("select PRINTFILESEEORG,PRINTFILESEEGROUP,PRINTFILESEEPERSON from jsf_Workflowprocess where WF_WORKFLOWPROCESS_ID=" + processId);
      String org = "";
      String group = "";
      String user = "";
      if (rs.next()) {
        org = rs.getString(1);
        group = rs.getString(2);
        user = rs.getString(3);
        rs.close();
        if ((org == null || org.length() < 1) && (user == null || user.length() < 1) && (group == null || group.length() < 1)) {
          hasRight = Boolean.TRUE;
          end();
          return hasRight;
        } 
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
        String[] groupIdArr = (String[])null;
        if (group != null && group.trim().length() > 0 && !group.trim().toUpperCase().equals("NULL"))
          groupIdArr = group.split(","); 
        if (groupIdArr == null)
          groupIdArr = new String[0]; 
        String[] orgIdArr = (String[])null;
        if (org != null && org.trim().length() > 0 && !org.trim().toUpperCase().equals("NULL"))
          orgIdArr = org.split(","); 
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
                  if (empId != null && (user == null || user.indexOf(empId.toString()) < 0))
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
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return hasRight;
  }
  
  public List getLeaderListByWorkID(String workId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select a.emp_id,a.empName from JSDB.org_employee a,JSDB.org_employee b where b.empleaderId like concat('%$', a.emp_id, '$%') and b.emp_id=(select standforuserid from JSDB.JSF_WORK where wf_work_id=" + 
          workId + ") order by a.userOrderCode";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select a.emp_id,a.empName from JSDB.org_employee a,JSDB.org_employee b where b.empleaderId locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(a.emp_id)), '$')>0 and b.emp_id=(select standforuserid from JSDB.JSF_WORK where wf_work_id=" + 
          workId + ") order by a.userOrderCode";
      } else {
        tmpSql = "select a.emp_id,a.empName from JSDB.org_employee a,JSDB.org_employee b where b.empleaderId like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(a.emp_id)), '$%') and b.emp_id=(select standforuserid from JSDB.JSF_WORK where wf_work_id=" + 
          workId + ") order by a.userOrderCode";
      } 
      ResultSet rs = this.stmt.executeQuery(tmpSql);
      while (rs.next()) {
        String[] tmp = { "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String[] getProcActiUser(String tableId, String recordId, String activityId) throws Exception {
    if (tableId == null || "".equals(tableId) || "null".equals(tableId))
      tableId = "0"; 
    if (recordId == null || "".equals(recordId) || "null".equals(recordId))
      recordId = "0"; 
    if (activityId == null || "".equals(activityId) || "null".equals(activityId))
      activityId = "0"; 
    begin();
    String[] user = new String[32];
    try {
      String participantGivenOrg = "", passRoundGivenOrg = "";
      String sql = "select participantType, participantUser, participantGroup, participantUserName, participantUserField,allowStandFor,allowCancel,allowTransition, pressType, deadLineTime, pressMotionTime, activityName, activityBeginEnd, needPassRound, passRoundUserType, passRoundUser, passRoundUserGroup, passRoundUserField,participantRole, passRoundRole,participantGivenOrg,passRoundGivenOrg,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId,pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId from JSDB.jsf_p_Activity where wf_activity_id = " + 





        
        activityId + " and ttable_id = " + tableId + 
        " and trecord_id = " + recordId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        user[0] = rs.getString(1);
        user[1] = rs.getString(2);
        user[2] = rs.getString(3);
        user[3] = rs.getString(4);
        user[4] = rs.getString(5);
        user[5] = rs.getString(6);
        user[6] = rs.getString(7);
        user[7] = rs.getString(8);
        user[8] = rs.getString(9);
        user[9] = rs.getString(10);
        user[10] = rs.getString(11);
        user[11] = rs.getString(12);
        user[12] = rs.getString(13);
        user[13] = rs.getString(14);
        user[14] = rs.getString(15);
        user[15] = rs.getString(16);
        user[16] = rs.getString(17);
        user[17] = rs.getString(18);
        user[18] = rs.getString(19);
        user[19] = "";
        user[20] = rs.getString(20);
        user[21] = "";
        participantGivenOrg = rs.getString(21);
        passRoundGivenOrg = rs.getString(22);
        user[24] = rs.getString("allowSmsRemind");
        user[25] = rs.getString("tranType");
        user[26] = rs.getString("tranCustomExtent");
        user[27] = rs.getString("tranCustomExtentId");
        user[28] = rs.getString("pressDealType");
        user[29] = rs.getString("tranReadType");
        user[30] = rs.getString("tranReadCustomExtent");
        user[31] = rs.getString("tranReadCustomExtentId");
      } 
      rs.close();
      if (participantGivenOrg != null && 
        !participantGivenOrg.equals("") && 
        !participantGivenOrg.toUpperCase().equals("NULL"))
        user[22] = participantGivenOrg; 
      if (passRoundGivenOrg != null && 
        !passRoundGivenOrg.equals("") && 
        !passRoundGivenOrg.toUpperCase().equals("NULL"))
        user[23] = passRoundGivenOrg; 
      String partRole = user[18], passRole = user[20], orgName = "";
      String roleName = "", orgId = "", roleId = "";
      if (partRole != null && !partRole.equals("") && partRole.indexOf("and") != -1)
        if (partRole.indexOf("and") > 0) {
          roleId = partRole.split("and")[0];
          orgId = partRole.split("and")[1];
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + roleId);
          if (rs.next())
            roleName = rs.getString(1); 
          if (orgId.equals("-1")) {
            orgName = "发起人的组织";
          } else if (orgId.equals("-2")) {
            orgName = "发起人的组织及上级组织";
          } else if (orgId.equals("-3")) {
            orgName = "发起人的组织及下级组织";
          } else if (Long.parseLong(orgId) > 0L) {
            rs = this.stmt.executeQuery("select orgNameString from JSDB.org_organization where org_id=" + orgId);
            if (rs.next())
              orgName = rs.getString(1); 
          } 
        } else {
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + partRole);
          if (rs.next())
            roleName = rs.getString(1); 
        }  
      if (orgName.equals("")) {
        user[19] = roleName;
      } else {
        user[19] = String.valueOf(orgName) + "-" + roleName;
      } 
      orgName = "";
      roleName = "";
      if (passRole != null && !passRole.equals("") && passRole.indexOf("and") != -1)
        if (partRole.indexOf("and") > 0) {
          roleId = passRole.split("and")[0];
          orgId = passRole.split("and")[1];
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + roleId);
          if (rs.next())
            roleName = rs.getString(1); 
          if (orgId.equals("-1")) {
            orgName = "发起人的组织";
          } else if (orgId.equals("-2")) {
            orgName = "发起人的组织及上级组织";
          } else if (orgId.equals("-3")) {
            orgName = "发起人的组织及下级组织";
          } else if (Long.parseLong(orgId) > 0L) {
            rs = this.stmt.executeQuery("select orgNameString from JSDB.org_organization where org_id=" + orgId);
            if (rs.next())
              orgName = rs.getString(1); 
          } 
        } else {
          rs = this.stmt.executeQuery("select roleName from JSDB.org_role where role_id=" + passRole);
          if (rs.next())
            roleName = rs.getString(1); 
        }  
      if (orgName.equals("")) {
        user[21] = roleName;
      } else {
        user[21] = String.valueOf(orgName) + "-" + roleName;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    end();
    return user;
  }
  
  public List getCandidate(String userIdStr, String groupIdStr) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    ResultSet rs = null;
    String sql = "";
    try {
      if (userIdStr == null || userIdStr.equals("null"))
        userIdStr = "-100"; 
      if (groupIdStr == null || groupIdStr.equals("null"))
        groupIdStr = "-100"; 
      if (userIdStr.indexOf("$") >= 0) {
        userIdStr = "$" + userIdStr + "$";
        userIdStr = userIdStr.replace('$', ',');
        userIdStr = userIdStr.replaceAll(",,", ",");
        userIdStr = userIdStr.substring(1, userIdStr.length() - 1);
      } 
      if (groupIdStr.indexOf("@") >= 0) {
        groupIdStr = "@" + groupIdStr + "@";
        groupIdStr = groupIdStr.replace('@', ',');
        groupIdStr = groupIdStr.replaceAll(",,", ",");
        groupIdStr = groupIdStr.substring(1, groupIdStr.length() - 1);
      } 
      sql = "select org_employee.emp_Id, org_employee.empName from JSDB.org_employee, org_organization_user, org_organization where org_employee.emp_Id=org_organization_user.emp_id and org_organization_user.org_id=org_organization.org_id and (";
      if (!userIdStr.equals("")) {
        sql = String.valueOf(sql) + "org_employee.emp_Id in (" + userIdStr + ") ";
      } else {
        sql = String.valueOf(sql) + "1<>1";
      } 
      sql = String.valueOf(sql) + " or ";
      if (!groupIdStr.equals("")) {
        sql = String.valueOf(sql) + "org_employee.emp_Id in (select org_employee.emp_Id from JSDB.org_employee,org_user_group where org_employee.emp_Id=org_user_group.emp_id and org_user_group.group_Id in (" + 
          
          groupIdStr + "))";
      } else {
        sql = String.valueOf(sql) + "1<>1";
      } 
      sql = String.valueOf(sql) + ") order by org_organization.orgIdString, org_employee.userOrderCode";
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[2];
        obj[0] = String.valueOf(rs.getInt(1));
        obj[1] = rs.getString(2);
        alist.add(obj);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    end();
    return alist;
  }
  
  public List getCandidateSeq(String userIdStr, String userNameStr) throws Exception {
    ArrayList<String[]> alist = new ArrayList();
    try {
      if (userIdStr == null || userIdStr.equals("null"))
        userIdStr = "-100"; 
      if (userIdStr.indexOf("$") >= 0) {
        userIdStr = "$" + userIdStr + "$";
        userIdStr = userIdStr.replace('$', ',');
        userIdStr = userIdStr.replaceAll(",,", ",");
        userIdStr = userIdStr.substring(1, userIdStr.length() - 1);
        if (userNameStr.endsWith(","))
          userNameStr = userNameStr.substring(0, userNameStr.length() - 1); 
        String[] userArr = userIdStr.split(",");
        String[] userNameArr = userNameStr.split(",");
        for (int m = 0; m < userArr.length; m++) {
          alist.add(new String[] { userArr[m], userNameArr[m] });
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    return alist;
  }
  
  public String getNextActivityClass(String curActivityId, String tableId, String recordId, String domainId) throws Exception {
    String actClass = "0";
    try {
      String pactId = "0";
      begin();
      String sql = "select wf_proceedactivity_id from jsf_p_activity where wf_activity_id=" + curActivityId + " and ttable_id=" + tableId + " and trecord_id=" + recordId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        pactId = rs.getString(1); 
      rs.close();
      sql = "select transitionto from jsf_p_transition where wf_proceedactivity_id=" + pactId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        pactId = rs.getString(1); 
      rs.close();
      sql = "select activityclass,wf_activity_id from jsf_p_activity where wf_activity_id=" + pactId + " and ttable_id=" + tableId + " and trecord_id=" + recordId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        pactId = rs.getString(1);
        if ("2".equals(pactId))
          actClass = rs.getString(2); 
      } 
      rs.close();
      end();
    } catch (Exception ex) {
      end();
      throw ex;
    } 
    return actClass;
  }
  
  public String getNextDefaultActivityId(String fatherActivityId) throws Exception {
    String nextId = "0";
    try {
      begin();
      ResultSet rs = this.stmt.executeQuery("select transitionto from jsf_transition where transitionto=defaultactivity and transitionfrom=" + fatherActivityId);
      if (rs.next())
        nextId = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception ex) {
      end();
      throw ex;
    } 
    return nextId;
  }
  
  public String getWorkStatusByWorkId(String workId) throws Exception {
    String status = "0";
    try {
      begin();
      ResultSet rs = this.stmt.executeQuery("select workstatus from jsf_work where wf_work_id=" + workId);
      if (rs.next())
        status = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception ex) {
      end();
      throw ex;
    } 
    return status;
  }
  
  public List getExportList(String viewSql, String fromSql, String whereSql) throws Exception {
    ArrayList<String[]> list = (ArrayList)new ArrayList<String>();
    try {
      begin();
      ResultSet rs = null;
      Map<String, String> organization = new HashMap<String, String>();
      rs = this.stmt.executeQuery("select org_id,orgnamestring from jsdb.org_organization");
      while (rs.next())
        organization.put(rs.getString(1), rs.getString(2)); 
      rs = this.stmt.executeQuery("select" + viewSql + " from " + fromSql + whereSql);
      while (rs.next()) {
        String workFlowProcessName = rs.getString(1);
        String packageName = rs.getString(2);
        String userScope = rs.getString(3);
        String dossierFileSeeScope = rs.getString(4);
        String dossierFileOperScope = rs.getString(5);
        String processAdminScope = rs.getString(6);
        String createUserName = rs.getString(7);
        String createOrg = ("null".equals(organization.get(rs.getString(14))) || organization.get(rs.getString(14)) == null) ? "" : organization.get(rs.getString(14));
        String ownerName = rs.getString(8);
        String ownerOrg = ("null".equals(organization.get(rs.getString(15))) || organization.get(rs.getString(15)) == null) ? "" : organization.get(rs.getString(15));
        String processCreatedDate = String.valueOf(rs.getDate(9).toString()) + " " + rs.getTime(9).toString();
        String lastUpdateTime = String.valueOf(rs.getDate(10).toString()) + " " + rs.getTime(10).toString();
        String pagename = rs.getString(11);
        String processStatus = "0".equals(rs.getString(12)) ? "禁用" : "启用";
        String processUseTime = "";
        if (!"0".equals(rs.getString(12)) && rs.getDate(13) != null)
          processUseTime = String.valueOf(rs.getDate(13).toString()) + " " + rs.getTime(13).toString(); 
        String[] value = { 
            workFlowProcessName, pagename, packageName, userScope, dossierFileSeeScope, dossierFileOperScope, 
            processAdminScope, createUserName, createOrg, ownerName, 
            ownerOrg, processCreatedDate, lastUpdateTime, processStatus, processUseTime };
        list.add(value);
      } 
      rs.close();
    } catch (Exception ex) {
      throw ex;
    } finally {
      end();
    } 
    return list;
  }
  
  public List getWfPackageList(String domainId, String packagewhere) {
    ArrayList<WFPackagePO> list = new ArrayList<WFPackagePO>();
    try {
      begin();
      String sql = "select distinct a.wf_package_id,a.packagename from jsf_package a,jsf_workflowprocess aaa " + packagewhere + 
        " and a.wf_package_id=aaa.wf_package_id and a.domain_id=" + domainId;
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        WFPackagePO wPackagePO = new WFPackagePO();
        wPackagePO.setWfPackageId(Long.valueOf(rs.getLong(1)));
        wPackagePO.setPackageName(rs.getString(2));
        list.add(wPackagePO);
      } 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getAllFormInfo(String tableId) {
    ArrayList<String[]> list = (ArrayList)new ArrayList<String>();
    try {
      begin();
      String tableName = "";
      String sql = "select area_table from tarea where page_id=" + tableId + " and areatype_id=102";
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next())
        tableName = rs.getString(1); 
      rs.close();
      sql = "select tp.page_id,tp.page_name from tarea ta,tpage tp where ta.page_id=tp.page_id and ta.areatype_id=102 and ta.area_table='" + tableName + "' and tp.page_type=0";
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = new String[2];
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        list.add(tmp);
      } 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getProcessMainFormId(String processId) {
    String formId = "";
    try {
      begin();
      String tableName = "";
      String sql = "select main_formid,accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=" + processId;
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        formId = rs.getString(1);
        if (formId == null || "0".equals(formId) || "null".equals(formId))
          formId = rs.getString(2); 
      } 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return formId;
  }
  
  public String getProcessFirstFormId(String processId) {
    String formId = "";
    try {
      begin();
      String tableName = "";
      String sql = "select accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=" + processId;
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next())
        formId = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return formId;
  }
  
  public String getFormIdByActivityId(String activityId) {
    String formId = "";
    try {
      begin();
      String tableName = "";
      String sql = "select form_id from jsf_activity where wf_activity_id=" + activityId;
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next())
        formId = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return formId;
  }
  
  public String getHangup(String processId, String recordId, String tableId, String processName) {
    String hangup = "0";
    try {
      begin();
      String tableName = "";
      String sql = "select work_hangup from jsf_work where workStatus=1 and WORKPROCESS_ID=" + processId + " and WORKTABLE_ID=" + tableId + " and WORKRECORD_ID=" + recordId + "  and WORKDELETE=0";
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next())
        hangup = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
    return hangup;
  }
  
  private List getStandForUserByProcessAndOrgWithConn(String[] userId, String processId, String submitPersonId, Connection conn, Statement stmt1) throws Exception {
    ArrayList<String[]> alist = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      ResultSet rs = null;
      String orgIdString = "";
      rs = this.stmt.executeQuery("select b.ORGIDSTRING from org_organization_user a,ORG_ORGANIZATION b where a.org_id=b.org_id and a.emp_id=" + submitPersonId);
      if (rs.next())
        orgIdString = rs.getString(1); 
      for (int i = 0; i < userId.length; i++) {
        if (userId[i] != null && !"".equals(userId[i]) && !"null".equals(userId[i])) {
          String sql, tmp[] = { "", "", "", "" };
          tmp[0] = userId[i];
          rs = stmt1.executeQuery("select empName from JSDB.org_employee where emp_id = " + userId[i]);
          if (rs.next())
            tmp[1] = rs.getString(1); 
          rs.close();
          Calendar now = Calendar.getInstance();
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
              "proxyState = 1 and '" + now.get(1) + "/" + (now.get(2) + 1) + "/" + now.get(5) + " " + now.get(10) + ":" + now.get(12) + "' between beginTime and endTime";
          } else {
            sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
              "proxyState = 1 and JSDB.FN_STRTODATE('" + now.get(1) + "/" + (now.get(2) + 1) + "/" + now.get(5) + " " + now.get(10) + ":" + now.get(12) + "', 'L') between beginTime and endTime";
          } 
          rs = stmt1.executeQuery(sql);
          while (rs.next()) {
            String proxyAllProcess = rs.getString(3);
            String proxyProcess = (rs.getString(4) == null) ? "" : rs.getString(4);
            String proxyOrgId = rs.getString(5);
            if (proxyProcess != null && ("1".equals(proxyAllProcess) || ("0".equals(proxyAllProcess) && proxyProcess.indexOf("$" + processId + "$") != -1))) {
              if (proxyOrgId != null && !"".equals(proxyOrgId) && !"null".equals(proxyOrgId.toLowerCase())) {
                boolean orgOK = false;
                if (proxyOrgId.startsWith("*")) {
                  String[] proxyOrgArr = ("*" + proxyOrgId + "*").split("\\*\\*");
                  for (int j = 0; j < proxyOrgArr.length; j++) {
                    if (!"".equals(proxyOrgArr[j]) && 
                      orgIdString.indexOf("$" + proxyOrgArr[j] + "$") != -1) {
                      orgOK = true;
                      break;
                    } 
                  } 
                } else if (orgIdString.indexOf("$" + proxyOrgId + "$") != -1) {
                  orgOK = true;
                } 
                if (orgOK) {
                  tmp[2] = rs.getString(1);
                  tmp[3] = rs.getString(2);
                } 
                continue;
              } 
              tmp[2] = rs.getString(1);
              tmp[3] = rs.getString(2);
            } 
          } 
          rs.close();
          alist.add(tmp);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return alist;
  }
  
  public String getActivityDelayTime(String activityId, String processId, String recordId, String tableId) {
    String activityDelaySend = "";
    try {
      begin();
      ResultSet rs = this.stmt.executeQuery("select activityDelaySend from jsf_p_activity where WF_ACTIVITY_ID=" + activityId + " and WF_WORKFLOWPROCESS_ID=" + processId + " and TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId);
      if (rs.next())
        activityDelaySend = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception ex) {
      end();
      ex.printStackTrace();
    } 
    if (activityDelaySend == null || activityDelaySend.equalsIgnoreCase("null"))
      activityDelaySend = ""; 
    return activityDelaySend;
  }
  
  public String getMainProcess(String workid) {
    String protdetail = "";
    String sql = "SELECT t.PARETABLEID,t.PARERECORDID FROM jsf_work t WHERE t.WF_WORK_ID=? AND t.ISSUBPROCWORK=1";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, workid);
      rs = pstmt.executeQuery();
      while (rs.next())
        protdetail = String.valueOf(rs.getString(1)) + "$" + rs.getString(2); 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return protdetail;
  }
  
  public List<String[]> getDraftComment(String workid, String recordid, String tableid) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "select id,draftcomment,signComment from jsf_commentdraft where workid=? and recordid=? and tableid=?";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, workid);
      pstmt.setString(2, recordid);
      pstmt.setString(3, tableid);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3) });
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
}
