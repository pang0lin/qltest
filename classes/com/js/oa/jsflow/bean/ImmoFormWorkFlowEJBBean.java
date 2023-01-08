package com.js.oa.jsflow.bean;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ImmoFormWorkFlowEJBBean extends DataSourceBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String[] getCommPO(String activityId, String tableId, String recordId) throws Exception {
    begin();
    String[] str = { "", "" };
    try {
      ResultSet rs = this.stmt.executeQuery("select actiCommField, passroundCommField from JSDB.jsf_p_Activity where ttable_id=" + 
          tableId + " and trecord_id=" + recordId + " and wf_activity_id=" + 
          activityId);
      if (rs.next()) {
        str[0] = rs.getString(1);
        str[1] = rs.getString(2);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return str;
  }
  
  public Map getComment(String processId, String tableId, String recordId) throws Exception {
    begin();
    Map<Object, Object> commentMap = new HashMap<Object, Object>();
    try {
      ResultSet rs = this.stmt.executeQuery("select distinct commentField from JSDB.jsf_dealwithcomment a, JSDB.jsf_dealwith b where a.wf_dealwith_id=b.wf_dealwith_id and b.activity_id in (select wf_activity_id from jsf_p_activity where wf_workflowprocess_id=" + 
          processId + " and ttable_id=" + 
          tableId + " and trecord_id=" + recordId + ") and b.databasetable_id=" + tableId + " and b.databaserecord_id=" + recordId);
      ArrayList<String> alist = new ArrayList();
      while (rs.next())
        alist.add(rs.getString(1)); 
      rs.close();
      String commentField = "";
      for (int i = 0; i < alist.size(); i++) {
        commentField = alist.get(i).toString();
        rs = this.stmt.executeQuery("select emp.empName, a.dealwithEmployeeComment, a.dealwithTime, a.isStandForComm, a.standForUserName from JSDB.jsf_dealwithcomment a, JSDB.jsf_dealwith b, JSDB.org_employee emp where a.wf_dealwith_id=b.wf_dealwith_id and b.activity_id in (select wf_activity_id from jsf_p_activity where wf_workflowprocess_id=" + 
            processId + " and ttable_id=" + 
            tableId + " and trecord_id=" + recordId + ") and b.databasetable_id=" + tableId + " and b.databaserecord_id=" + recordId + " and a.commentfield='" + 
            commentField + "' and a.dealwithEmployee_id=emp.emp_id order by a.dealwithTime");
        ArrayList<Object[]> commList = new ArrayList();
        while (rs.next()) {
          Object[] tmp = new Object[5];
          tmp[0] = rs.getString(1);
          tmp[1] = rs.getString(2);
          tmp[2] = rs.getDate(3);
          tmp[3] = rs.getString(4);
          tmp[4] = rs.getString(5);
          commList.add(tmp);
        } 
        rs.close();
        commentMap.put(commentField, commList);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return commentMap;
  }
  
  public String getImmoFormRealName(String immoFormId) throws Exception {
    begin();
    String immoFormRealName = "";
    try {
      ResultSet rs = this.stmt.executeQuery("select immoForm_realName from JSDB.jsf_immobilityForm where wf_immoForm_id=" + immoFormId);
      if (rs.next())
        immoFormRealName = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return immoFormRealName;
  }
}
