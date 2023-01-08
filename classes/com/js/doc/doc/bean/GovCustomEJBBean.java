package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovCustomCheckedFieldPO;
import com.js.doc.doc.po.GovCustomPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovCustomEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long SaveGovCustomPO(GovCustomPO po) throws Exception {
    begin();
    Long sresult = new Long(-1L);
    Connection conn = null;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String jsf_immobilityform_sql = "";
      String customPO_sql = "";
      String selectId_sql = "select WF_IMMOFORM_ID  from jsf_immobilityform where immoForm_displayName= '" + po.getGovFormName() + "' ";
      String realTableName = "Gov_DocumentSendFile";
      String displayName = "";
      String table_primaryKey = "DocumentSendFile_Id";
      String modleId = "2";
      String formId = "-1";
      String Id = "";
      displayName = po.getGovFormName();
      if (po.getGovFormType() == 1) {
        realTableName = "Gov_ReceiveFile";
        table_primaryKey = "ReceiveFile_Id";
        modleId = "3";
      } else if (po.getGovFormType() == 2) {
        realTableName = "GOV_SENDFILECHECKWITHWORKFLOW";
        table_primaryKey = "SENDFILECHECK_ID";
        modleId = "34";
      } 
      if (!sresult.toString().equals("-2")) {
        if (databaseType.equals("oracle")) {
          jsf_immobilityform_sql = " Insert Into jsf_immobilityform (WF_IMMOFORM_ID,immoForm_realName, immoForm_displayName, wf_module_id, immoForm_primaryKey) Values (HIBERNATE_SEQUENCE.NEXTVAL,'" + realTableName + "','" + displayName + "'," + modleId + ",'" + table_primaryKey + "')";
          stmt.execute(jsf_immobilityform_sql);
          for (rs = stmt.executeQuery(selectId_sql); rs.next();)
            formId = rs.getString(1); 
          rs.close();
          po.setGovFormId(new Long(formId));
          this.session.save(po);
          this.session.flush();
        } else {
          jsf_immobilityform_sql = " Insert Into jsf_immobilityform (immoForm_realName, immoForm_displayName, wf_module_id, immoForm_primaryKey) Values ('" + realTableName + "','" + displayName + "'," + modleId + ",'" + table_primaryKey + "')";
          stmt.execute(jsf_immobilityform_sql);
          for (rs = stmt.executeQuery(selectId_sql); rs.next();)
            formId = rs.getString(1); 
          rs.close();
          po.setGovFormId(new Long(formId));
          this.session.save(po);
          this.session.flush();
        } 
        sresult = new Long(formId);
      } 
      stmt.close();
      conn.close();
      this.session.close();
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      this.session.close();
      e.printStackTrace();
      sresult = Long.valueOf("-3");
      throw e;
    } 
    return sresult;
  }
  
  public String updateGovCustomPO(GovCustomPO po, String gffType) throws Exception {
    begin();
    String result = "-1";
    Connection conn = null;
    try {
      String selectId_sql = "select WF_IMMOFORM_ID  from jsf_immobilityform where immoForm_displayName= '" + po.getGovFormName() + "'  and  WF_IMMOFORM_ID<>" + po.getGovFormId();
      String customPO_sql = "";
      String form_sql = "";
      if (!result.equals("-2")) {
        if (gffType.equals("0")) {
          GovCustomPO updatePO = (GovCustomPO)this.session.load(GovCustomPO.class, po.getId());
          updatePO.setGovFormName(po.getGovFormName());
          updatePO.setGovFormContent(po.getGovFormContent());
          updatePO.setGovCheckField(po.getGovCheckField());
          updatePO.setMustword(po.getMustword());
          this.session.update(updatePO);
          this.session.flush();
        } else {
          GovCustomPO updatePO = (GovCustomPO)this.session.load(GovCustomPO.class, po.getId());
          updatePO.setGovPrintFormContent(po.getGovPrintFormContent());
          this.session.update(updatePO);
          this.session.flush();
        } 
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        form_sql = "update jsf_immobilityform  set immoform_displayname='" + po.getGovFormName() + "' where WF_IMMOFORM_ID=" + po.getGovFormId();
        stmt.executeUpdate(form_sql);
        result = "1";
        stmt.close();
        conn.close();
        this.session.close();
      } 
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      this.session.close();
      e.printStackTrace();
      throw e;
    } finally {}
    return result;
  }
  
  public GovCustomPO loadGovCustomPO(String id) throws Exception {
    begin();
    GovCustomPO po = null;
    try {
      po = (GovCustomPO)this.session.load(GovCustomPO.class, Long.valueOf(id));
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public String deleteGovCustomPO(String id, String formId) throws Exception {
    String result = "-1";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String customDel = "";
      String formDel = "";
      String fieldDel = "";
      customDel = " delete from form_content where id=" + id;
      formDel = " delete from  jsf_immobilityform  where  WF_IMMOFORM_ID=" + formId;
      fieldDel = " delete from  form_controlfield  where  govFormId=" + formId;
      stmt.execute(customDel);
      stmt.execute(formDel);
      stmt.execute(fieldDel);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public List loadCheckFieldList(String formId, String gffType) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery("select po from com.js.doc.doc.po.GovCustomCheckedFieldPO po where po.govFormId=" + formId + " and po.gffType=" + gffType).list();
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String saveCheckFieldBatch(List<GovCustomCheckedFieldPO> poList, String gffType) throws Exception {
    begin();
    String result = "-1";
    Long fomrId = null;
    try {
      if (poList != null && poList.size() > 0) {
        GovCustomCheckedFieldPO po = poList.get(0);
        fomrId = po.getGovFormId();
        this.session.delete("from com.js.doc.doc.po.GovCustomCheckedFieldPO po where po.govFormId=" + fomrId + " and po.gffType=" + gffType);
        this.session.flush();
      } 
      for (int i = 0; i < poList.size(); i++) {
        GovCustomCheckedFieldPO po = poList.get(i);
        po.setGffType(Integer.parseInt(gffType));
        this.session.save(po);
        if (i % 10 == 0) {
          this.session.flush();
          this.session.clear();
        } 
      } 
      this.session.flush();
      this.session.clear();
      result = "1";
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Long saveCheckField(GovCustomCheckedFieldPO po) throws Exception {
    begin();
    Long result = Long.valueOf("-1");
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List loadFieldList(String type) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery("select po from com.js.doc.doc.po.GovCustomFieldPO po where po.govFormType=" + type).list();
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public void addWorkFlowControl(List<String[]> fieldControlList, String formId) throws Exception {
    Connection conn = null;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String deleteSql = "delete from  JSF_IMMOBILITYFIELD  where WF_IMMOFORM_ID=" + formId;
      stmt.execute(deleteSql);
      for (int i = 0; i < fieldControlList.size(); i++) {
        String[] fieldControl = fieldControlList.get(i);
        String fieldName = fieldControl[0].toString();
        String fieldDisplayName = fieldControl[1].toString();
        String fieldDisplayType = fieldControl[2].toString();
        String sql = "";
        if (databaseType.equals("oracle")) {
          if (fieldDisplayType.equals("1")) {
            sql = "insert into JSF_IMMOBILITYFIELD (wf_immofield_id,WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values ( HIBERNATE_SEQUENCE.NEXTVAL," + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 0, 1)";
          } else {
            sql = "insert into JSF_IMMOBILITYFIELD (wf_immofield_id,WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values ( HIBERNATE_SEQUENCE.NEXTVAL," + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 1, 0)";
          } 
        } else if (fieldDisplayType.equals("1")) {
          sql = "insert into JSF_IMMOBILITYFIELD (WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values (" + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 0, 1)";
        } else {
          sql = "insert into JSF_IMMOBILITYFIELD (WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values (" + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 1, 0)";
        } 
        stmt.execute(sql);
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } 
  }
  
  public void updateWorkFlowControl(List<String[]> fieldControlList, String formId) throws Exception {
    Connection conn = null;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String deleteStrArr = "'-1',";
      for (int i = 0; i < fieldControlList.size(); i++) {
        String[] fieldControl = fieldControlList.get(i);
        String fieldName = fieldControl[0].toString();
        String fieldDisplayName = fieldControl[1].toString();
        String fieldDisplayType = fieldControl[2].toString();
        String sql = "";
        deleteStrArr = String.valueOf(deleteStrArr) + "'" + fieldName + "',";
        String selectId_sql = "select  wf_immofield_id from JSF_IMMOBILITYFIELD where  IMMOFIELD_REALNAME='" + fieldName + "' and  WF_IMMOFORM_ID=" + formId;
        boolean alreadyInsert = false;
        for (rs = stmt.executeQuery(selectId_sql); rs.next();)
          alreadyInsert = true; 
        rs.close();
        if (alreadyInsert) {
          sql = " update  JSF_IMMOBILITYFIELD set IMMOFIELD_DISPLAYNAME= '" + fieldDisplayName + "' where  IMMOFIELD_REALNAME='" + fieldName + "' and  WF_IMMOFORM_ID=" + formId;
          stmt.execute(sql);
        } else {
          String insertSql;
          if (databaseType.equals("oracle")) {
            if (fieldDisplayType.equals("1")) {
              sql = "insert into JSF_IMMOBILITYFIELD (wf_immofield_id,WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values ( HIBERNATE_SEQUENCE.NEXTVAL," + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 0, 1)";
            } else {
              sql = "insert into JSF_IMMOBILITYFIELD (wf_immofield_id,WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values ( HIBERNATE_SEQUENCE.NEXTVAL," + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 1, 0)";
            } 
          } else if (fieldDisplayType.equals("1")) {
            sql = "insert into JSF_IMMOBILITYFIELD (WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values (" + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 0, 1)";
          } else {
            sql = "insert into JSF_IMMOBILITYFIELD (WF_IMMOFORM_ID, IMMOFIELD_REALNAME, IMMOFIELD_DISPLAYNAME, IMMOFIELD_CANREMIND, IMMOFIELD_POFIELD, IMMOFIELD_CANMODIFY, IMMOFIELD_CANIDEA) values (" + formId + ", '" + fieldName + "', '" + fieldDisplayName + "', 0, '" + fieldName + "', 1, 0)";
          } 
          stmt.execute(sql);
          String fieldId = "-1";
          String selectFieldId_sql = "select  wf_immofield_id  from JSF_IMMOBILITYFIELD  where IMMOFIELD_REALNAME='" + fieldName + "' and  WF_IMMOFORM_ID=" + formId;
          for (rs = stmt.executeQuery(selectFieldId_sql); rs.next();)
            fieldId = rs.getString(1); 
          rs.close();
          if (databaseType.equals("oracle")) {
            insertSql = "insert into  jsf_readwritecontrol (wf_readwritecontrol_id,controltype,controlfield,wf_activity_id,domain_id) select   HIBERNATE_SEQUENCE.NEXTVAL,0, " + fieldId + ", aa.wf_activity_id,0 from  jsf_activity aa  where form_id=" + formId;
          } else {
            insertSql = "insert into  jsf_readwritecontrol (controltype,controlfield,wf_activity_id,domain_id) select   0, " + fieldId + ", aa.wf_activity_id,0 from  jsf_activity aa  where form_id=" + formId;
          } 
          stmt.execute(insertSql);
        } 
      } 
      deleteStrArr = deleteStrArr.substring(0, deleteStrArr.length() - 1);
      String deleteSql = "delete from  JSF_IMMOBILITYFIELD  where WF_IMMOFORM_ID=" + formId + " and  IMMOFIELD_REALNAME not in (" + deleteStrArr + ")";
      stmt.execute(deleteSql);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } 
  }
  
  public List loadCheckFieldListByDisplayType(String formId, String displayType, String gffType) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery("select po from com.js.doc.doc.po.GovCustomCheckedFieldPO po where po.govFormId=" + formId + " and gffType=" + gffType + "  and  (select count(bbb.id) from com.js.doc.doc.po.GovCustomFieldPO bbb where bbb.gffName=po.gffName and bbb.gffDisplayType=" + displayType + " )>0 ").list();
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String saveBookMarks(List<String[]> fieldControlList, String domainId) throws Exception {
    String resultValue = "0";
    Connection conn = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      for (int i = 0; i < fieldControlList.size(); i++) {
        String[] fieldControl = fieldControlList.get(i);
        String fieldDisplayName = fieldControl[1].toString();
        String fieldDisplayType = fieldControl[5].toString();
        if (!fieldDisplayType.equals("5") && !fieldDisplayType.equals("10")) {
          String result = "0";
          String selectId_sql = "select BookMarkID  from BookMarks where BookMarkName= '" + fieldDisplayName + "'";
          String insertSql = " insert into BookMarks(BookMarkName,BookMarkDesc,BookMarkText,Domain_id) values('" + fieldDisplayName + "','[" + fieldDisplayName + "]','" + fieldDisplayName + "'," + domainId + ")";
          if (databaseType.equals("oracle"))
            insertSql = " insert into BookMarks(BookMarkID,BookMarkName,BookMarkDesc,BookMarkText,Domain_id) values(HIBERNATE_SEQUENCE.NEXTVAL,'" + fieldDisplayName + "','[" + fieldDisplayName + "]','" + fieldDisplayName + "'," + domainId + ")"; 
          for (rs = stmt.executeQuery(selectId_sql); rs.next();)
            result = "1"; 
          rs.close();
          if (!result.equals("1"))
            stmt.execute(insertSql); 
        } 
      } 
      resultValue = "1";
      stmt.close();
      conn.close();
    } catch (Exception e) {
      resultValue = "0";
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } finally {}
    return resultValue;
  }
}
