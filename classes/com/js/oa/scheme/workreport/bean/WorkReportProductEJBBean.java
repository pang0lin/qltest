package com.js.oa.scheme.workreport.bean;

import com.js.oa.scheme.workreport.po.WorkReportLeaderPO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.oa.scheme.workreport.po.WorkReportTemplatePO;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WorkReportProductEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Vector list(String wherePara, String currentPage) throws Exception {
    return null;
  }
  
  public List initList(List list) throws Exception {
    begin();
    try {
      Iterator<Object[]> it = list.iterator();
      while (it.hasNext()) {
        Object[] obj = it.next();
        if (obj[5] == null || "".equals(obj[5]) || 
          "0".equals(obj[5])) {
          obj[4] = "";
          continue;
        } 
        List tempList = this.session.createQuery("select po.templateName from com.js.oa.scheme.workreport.po.WorkReportTemplatePO po where po.id=" + 
            obj[5]).list();
        if (tempList.size() > 0) {
          obj[4] = tempList.get(0);
          continue;
        } 
        obj[4] = "";
      } 
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public Map load(String id) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      WorkReportPO po = (WorkReportPO)this.session.load(WorkReportPO.class, 
          new Long(id));
      result.put("accessorySaveTmpName", po.getAccessorySaveName());
      result.put("accessoryTmpName", po.getAccessoryName());
      result.put("reportReader", po.getReportReader());
      result.put("reportReaderId", po.getReportReaderId());
      result.put("reportRemark", po.getReportRemark());
      result.put("reportType", (new StringBuilder(String.valueOf(po.getReportType()))).toString());
      result.put("templateId", (new StringBuilder(String.valueOf(po.getTemplateId()))).toString());
      result.put("reportTime", po.getReportTime());
      result.put("previousReport", po.getPreviousReport());
      result.put("nextReport", po.getNextReport());
      result.put("sendType", po.getSendType());
      result.put("relproject", Long.valueOf(po.getRelprojectId()));
      result.put("reportName", po.getReportName());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public void add(String userId, String orgId, String userName, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para) throws Exception {
    try {
      begin();
      WorkReportPO po = new WorkReportPO();
      po.setEmpId(Long.parseLong(userId));
      po.setReportEmpName(userName);
      String str1 = "";
      String str2 = "";
      if (accessoryName != null)
        for (int j = 0; j < accessoryName.length; j++) {
          if (!str1.equals(""))
            str1 = String.valueOf(str1) + "|"; 
          if (!str2.equals(""))
            str2 = String.valueOf(str2) + "|"; 
          str1 = String.valueOf(str1) + accessoryName[j];
          str2 = String.valueOf(str2) + accessorySaveName[j];
        }  
      po.setAccessoryName(str1);
      po.setAccessorySaveName(str2);
      po.setHadRead(Byte.parseByte("0"));
      po.setReportReader(para[0]);
      po.setReportReaderId(para[1]);
      po.setReportRemark(para[2]);
      String str3 = para[3];
      if (!"none".equals(str3))
        po.setTemplateId(Long.parseLong(str3)); 
      po.setReportType(Byte.parseByte(para[4]));
      po.setReportTime(new Date(para[5]));
      po.setReportDomainId(Long.parseLong(para[6]));
      po.setPreviousReport(previousReport);
      po.setNextReport(nextReport);
      this.session.save(po);
      String[] arr = ("$" + po.getReportReaderId() + "$").split("\\$\\$");
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] != null && !"".equals(arr[i])) {
          WorkReportLeaderPO lpo = new WorkReportLeaderPO();
          lpo.setEmpId(Long.parseLong(arr[i]));
          lpo.setHadRead(Byte.parseByte("0"));
          lpo.setReport(po);
          if (!para[6].equals(""))
            lpo.setRlDomainId(Long.parseLong(para[6])); 
          this.session.save(lpo);
        } 
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
  }
  
  private void addLeader(String ids, WorkReportPO po, String userId, String domainId) throws Exception {
    try {
      if (ids != null)
        ids.equals(""); 
      ids = "$" + ids + "$";
      String[] arr = ids.split("\\$\\$");
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] != null && !"".equals(arr[i])) {
          WorkReportLeaderPO lpo = new WorkReportLeaderPO();
          lpo.setEmpId(Long.parseLong(arr[i]));
          lpo.setHadRead(Byte.parseByte("0"));
          lpo.setReport(po);
          if (!domainId.equals(""))
            lpo.setRlDomainId(Long.parseLong(domainId)); 
          Long reid = (Long)this.session.save(lpo);
          String title = "";
          String url = "";
          String reportType = (new Integer(po.getReportType())).toString();
          if ("1".equals(reportType)) {
            title = String.valueOf(title) + "每周工作汇报";
            url = "WorkReportLeaderAction.do?action=load&receiveRecordId=" + reid + "&status=1&pager.offset=0&reportType=" + po.getReportType() + "&fromDesktop=yes";
          } else if ("3".equals(reportType)) {
            title = String.valueOf(title) + "每月工作汇报";
            url = "WorkReportLeaderAction.do?action=load&receiveRecordId=" + reid + "&status=1&pager.offset=0&reportType=" + po.getReportType() + "&fromDesktop=yes";
          } else {
            String huibao = "其他工作汇报";
            if ("1".equals(SystemCommon.getReport()))
              huibao = "工作汇报"; 
            title = String.valueOf(title) + huibao;
            url = "WorkReportLeaderProductAction.do?action=load&receiveRecordId=" + reid + "&status=1&pager.offset=0&reportType=none";
          } 
          Calendar tmp = Calendar.getInstance();
          tmp.set(2050, 12, 12);
          RemindUtil.sendMessageToUsers(title, url, arr[i], "WorkReport", new Date(), tmp.getTime(), po.getReportEmpName(), Long.valueOf(po.getId()));
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  private void updateLeader(String ids, WorkReportPO po, long userId, String sendType) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    try {
      WorkReportLeaderPO poo = null;
      Iterator<WorkReportLeaderPO> iter = this.session.iterate("select poo from com.js.oa.scheme.workreport.po.WorkReportLeaderPO poo join poo.report rpo where rpo.id=" + 
          po.getId());
      if (iter.hasNext()) {
        poo = iter.next();
        this.session.delete(poo);
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("delete from sys_messages where data_id= " + po.getId() + "  and message_type like 'WorkReport'");
      if (sendType.equals("1"))
        addLeader(ids, po, (new StringBuilder(String.valueOf(userId))).toString(), (new StringBuilder(String.valueOf(po.getReportDomainId()))).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
  }
  
  public void update(String editId, String userId, String orgId, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para, String reportName) throws Exception {
    begin();
    try {
      if (!"-1".equals(para[7])) {
        Iterator itor = this.session.iterate("select po.id from com.js.oa.scheme.workreport.po.WorkReportPO po where po.reportCourse='" + para[8] + "' and po.empId=" + userId + " and po.relprojectId=" + para[7] + " and po.relprojectId <> -1 and po.id <>" + editId);
        if (itor.hasNext())
          return; 
      } 
      WorkReportPO po = (WorkReportPO)this.session.load(WorkReportPO.class, 
          new Long(editId));
      String str1 = "";
      String str2 = "";
      if (accessoryName != null)
        for (int i = 0; i < accessoryName.length; i++) {
          if (!str1.equals(""))
            str1 = String.valueOf(str1) + "|"; 
          if (!str2.equals(""))
            str2 = String.valueOf(str2) + "|"; 
          str1 = String.valueOf(str1) + accessoryName[i];
          str2 = String.valueOf(str2) + accessorySaveName[i];
        }  
      po.setAccessoryName(str1);
      po.setAccessorySaveName(str2);
      po.setNextReport(nextReport);
      po.setPreviousReport(previousReport);
      po.setReportReader(para[0]);
      po.setReportReaderId(para[1]);
      po.setReportRemark(para[2]);
      po.setReportName(reportName);
      String str3 = para[3];
      if (!"none".equals(str3))
        po.setTemplateId(Long.parseLong(str3)); 
      po.setReportType(Byte.parseByte(para[4]));
      po.setReportTime(new Date(para[5]));
      po.setSendType(para[6]);
      po.setRelprojectId(Long.parseLong(para[7]));
      po.setReortUpdateTime(new Date());
      po.setReportInputTime(new Date());
      updateLeader(po.getReportReaderId(), po, Long.parseLong(userId), para[6]);
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public String delBatch(String ids) throws Exception {
    String retString = "";
    begin();
    try {
      if (ids != null && !ids.equals("")) {
        ids = ids.substring(0, ids.length() - 1);
        List<String> listAccessory = this.session.createQuery("select po.accessorySaveName  from com.js.oa.scheme.workreport.po.WorkReportPO po where po.id in( " + 
            ids + ")").list();
        for (int i = 0; i < listAccessory.size(); i++)
          retString = String.valueOf(retString) + "|" + listAccessory.get(i); 
        this.session.delete(
            " from com.js.oa.scheme.workreport.po.WorkReportPO po where po.id in (" + 
            ids + ")");
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return retString;
  }
  
  public List see(String wherePara, Long domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.id,po.templateName from com.js.oa.scheme.workreport.po.WorkReportTemplatePO po  where " + 
          wherePara + (
          (domainId != null) ? (
          " and po.templateDomainId = " + 
          domainId) : "") + 
          " order by po.templateName").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public String template(String tempId, Long domainId) throws Exception {
    String retString = "";
    begin();
    try {
      WorkReportTemplatePO po = (WorkReportTemplatePO)this.session.load(
          WorkReportTemplatePO.class, Long.valueOf(tempId));
      retString = po.getTemplateContent();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
}
