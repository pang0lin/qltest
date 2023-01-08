package com.js.oa.scheme.workreport.bean;

import com.js.oa.scheme.workreport.po.WorkReportLeaderPO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.oa.scheme.workreport.po.WorkReportPostilPO;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class WorkreportLeaderProductEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Vector load(String sendRecordId, String receiveRecordId, String status, Long domanId) throws Exception {
    List<Object[]> listReport = new ArrayList();
    List<Object[]> listPostil = new ArrayList();
    Vector<List<Object[]>> vec = new Vector();
    begin();
    try {
      String from;
      if (sendRecordId != null) {
        from = " from com.js.oa.scheme.workreport.po.WorkReportPO po where po.id = " + 
          sendRecordId;
      } else {
        if ("0".equals(status)) {
          WorkReportLeaderPO lpo = (WorkReportLeaderPO)this.session.load(
              WorkReportLeaderPO.class, new Long(receiveRecordId));
          lpo.setHadRead(Byte.parseByte("1"));
          this.session.update(lpo);
          WorkReportPO po = lpo.getReport();
          if (po.getHadRead() == 0) {
            po.setHadRead(Byte.parseByte("1"));
            this.session.update(po);
          } 
        } 
        from = " from com.js.oa.scheme.workreport.po.WorkReportLeaderPO lpo  join lpo.report po where lpo.id=" + 
          receiveRecordId;
      } 
      listReport = this.session.createQuery(
          "select po.id, po.reportTime,po.reportType,po.templateId,po.reportReader,po.reportReader,po.reportReaderId,po.accessoryName,po.accessorySaveName,po.previousReport,po.nextReport,po.reportRemark,po.empId,po.reportEmpName,po.reportCourse,po.reportJob,po.reportDepart,po.relprojectId,po.reportName " + 

          
          from).list();
      if (listReport.size() > 0) {
        Object[] obj = listReport.get(0);
        Object object1 = obj[3];
        if ("0".equals("templateId")) {
          obj[4] = "没选择模板";
        } else {
          List tmpList = this.session.createQuery(
              "select po.templateName from com.js.oa.scheme.workreport.po.WorkReportTemplatePO po  where po.id=" + 
              
              object1).list();
          if (tmpList.size() <= 0) {
            obj[4] = "没选择模板";
          } else {
            obj[4] = tmpList.get(0);
          } 
        } 
        listReport.set(0, obj);
      } 
      Object object = ((Object[])listReport.get(0))[0];
      listPostil = this.session.createQuery(
          "select ppo.postilEmpName,ppo.postilContent,ppo.postilTime,ppo.postilEmpSign,ppo.id from com.js.oa.scheme.workreport.po.WorkReportPostilPO ppo  where ppo.report.id=" + 
          
          object + 
          " order by ppo.id desc").list();
      vec.add(listReport);
      vec.add(listPostil);
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return vec;
  }
  
  public List loadContent(List<Object[]> list) throws Exception {
    ArrayList<Object[]> retList = new ArrayList();
    try {
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        Object object = obj[0];
        String content = "";
        Query query = this.session.createQuery(" select aaa.previousReport from com.js.oa.scheme.workreport.po.WorkReportPO aaa where aaa.id=" + 
            object);
        List<E> list2 = query.list();
        if (list2 != null)
          content = list2.get(0).toString(); 
        obj[9] = content;
        content = "";
        query = this.session.createQuery(" select aaa.nextReport from com.js.oa.scheme.workreport.po.WorkReportPO aaa where aaa.id=" + 
            object);
        List<E> list3 = query.list();
        if (list3 != null)
          content = list3.get(0).toString(); 
        obj[10] = content;
        retList.add(obj);
      } 
    } catch (Exception e) {
      this.transaction.rollback();
      e.printStackTrace();
      throw e;
    } 
    return retList;
  }
  
  public void add(String userName, String receiveRecordId, String userId, String postilContent, String usersId, String usersName, Long domainId) throws Exception {
    begin();
    try {
      WorkReportPostilPO po = new WorkReportPostilPO();
      po.setPostilContent(postilContent);
      po.setPostilEmpName(userName);
      po.setPostilTime(new Date());
      WorkReportPO rpo = ((WorkReportLeaderPO)this.session.load(
          WorkReportLeaderPO.class, 
          new Long(receiveRecordId))).getReport();
      po.setReport(rpo);
      List<String> tmp = this.session.createQuery("select po.empSignImg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
          userId).list();
      if (tmp.size() > 0 && tmp.get(0) != null)
        po.setPostilEmpSign(tmp.get(0)); 
      po.setPostilDomainId(domainId.longValue());
      Long dateid = (Long)this.session.save(po);
      if (postilContent != null && !"".equals(postilContent)) {
        Calendar tmp1 = Calendar.getInstance();
        tmp1.set(2050, 12, 12);
        String url = "WorkReportLeaderProductAction.do?action=load&sendRecordId=" + rpo.getId() + "&status=1&pager.offset=1";
        String huibao = "其他工作汇报";
        if ("1".equals(SystemCommon.getReport()))
          huibao = "工作汇报"; 
        String title = "领导评价了你的" + huibao;
        RemindUtil.sendMessageToUsers(title, url, (new Long(rpo.getEmpId())).toString(), "OtherPostil", new Date(), tmp1.getTime(), po.getPostilEmpName(), dateid);
      } 
      if (usersId != null && !"".equals(usersId))
        updateLeader(rpo, usersId, usersName, userName); 
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
  
  private void updateLeader(WorkReportPO rpo, String usersId, String usersName, String userName) throws Exception {
    try {
      String oriUsersId = rpo.getReportReaderId();
      String oriUsersName = rpo.getReportReader();
      String[] usersIdArr = ("$" + usersId + "$").split("\\$\\$");
      String[] usersNameArr = ("," + usersName + ",").split(",");
      for (int i = 0; i < usersIdArr.length; i++) {
        String selId = usersIdArr[i];
        if (selId != null && !"".equals(selId))
          if (oriUsersId.indexOf("$" + selId + "$") == -1) {
            WorkReportLeaderPO lpo = new WorkReportLeaderPO();
            lpo.setEmpId(Long.parseLong(selId));
            lpo.setHadRead(Byte.parseByte("0"));
            lpo.setReport(rpo);
            Long dataid = (Long)this.session.save(lpo);
            String str1 = "其他工作汇报";
            if ("1".equals(SystemCommon.getReport()))
              str1 = "工作汇报"; 
            String str2 = String.valueOf(userName) + "把" + rpo.getReportEmpName() + "的其他" + str1 + "提交给你";
            String str3 = "WorkReportLeaderProductAction.do?action=load&receiveRecordId=" + dataid + "&status=1&pager.offset=0&reportType=none";
            Calendar tmp = Calendar.getInstance();
            tmp.set(2050, 12, 12);
            RemindUtil.sendMessageToUsers(str2, str3, selId, "WorkReport", new Date(), tmp.getTime(), userName, Long.valueOf(rpo.getId()));
            oriUsersId = String.valueOf(oriUsersId) + "$" + selId + "$";
            oriUsersName = String.valueOf(oriUsersName) + usersNameArr[i] + ",";
          } else {
            List<String> list = this.session.createQuery("select po.id from com.js.oa.scheme.workreport.po.WorkReportLeaderPO po where po.empId=" + 
                selId + " and po.report.id=" + 
                rpo.getId())
              .list();
            if (list.size() <= 0) {
              WorkReportLeaderPO lpo1 = new WorkReportLeaderPO();
              lpo1.setHadRead(Byte.parseByte("0"));
              lpo1.setEmpId(Long.parseLong(selId));
              lpo1.setReport(rpo);
              Long dataid = (Long)this.session.save(lpo1);
              String str1 = "其他工作汇报";
              if ("1".equals(SystemCommon.getReport()))
                str1 = "工作汇报"; 
              String str2 = String.valueOf(userName) + "把" + rpo.getReportEmpName() + "的" + str1 + "汇报提交给你";
              String str3 = "WorkReportLeaderProductAction.do?action=load&receiveRecordId=" + dataid + "&status=1&pager.offset=0&reportType=none";
              Calendar tmp = Calendar.getInstance();
              tmp.set(2050, 12, 12);
              RemindUtil.sendMessageToUsers(str2, str3, selId, "WorkReport", new Date(), tmp.getTime(), userName, Long.valueOf(rpo.getId()));
            } else {
              WorkReportLeaderPO lpo1 = 
                (WorkReportLeaderPO)this.session.load(WorkReportLeaderPO.class, 
                  new Long(list.get(0)));
              lpo1.setHadRead(Byte.parseByte("0"));
              this.session.update(lpo1);
            } 
          }  
      } 
      rpo.setReportReader(oriUsersName);
      rpo.setReportReaderId(oriUsersId);
      this.session.update(rpo);
      Calendar tmp1 = Calendar.getInstance();
      tmp1.set(2050, 12, 12);
      String url = "WorkReportLeaderProductAction.do?action=load&sendRecordId=" + rpo.getId() + "&status=1&pager.offset=1";
      String huibao = "其他工作汇报";
      if ("1".equals(SystemCommon.getReport()))
        huibao = "工作汇报"; 
      String title = String.valueOf(userName) + "把你的" + huibao + "提交给" + usersName;
      RemindUtil.sendMessageToUsers(title, url, (new Long(rpo.getEmpId())).toString(), "OtherSubmit", new Date(), tmp1.getTime(), userName, Long.valueOf(rpo.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  public void delBatch(String ids) throws Exception {
    begin();
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.scheme.workreport.po.WorkReportLeaderPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
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
  
  public List getTopnNotReadReport(String readerId, String domainId, Integer top) throws HibernateException {
    List list = null;
    begin();
    try {
      list = null;
      Query query = this.session.createQuery("select po.id,po.hadRead,report.reportEmpName,report.reportType,report.reportTime,report.reportReader,report.empId from com.js.oa.scheme.workreport.po.WorkReportLeaderPO po join po.report report where po.empId=" + 
          
          readerId + 
          " and po.rlDomainId = " + domainId + 
          " order by po.hadRead, report.reportTime desc ,report.id desc");
      query.setFirstResult(0);
      query.setMaxResults(top.intValue());
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getReportId(String id) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String re = "0";
    try {
      base.begin();
      String sql = "SELECT report_id FROM rep_LEADER WHERE REPORTLEADER_ID=" + id;
      rs = base.executeQuery(sql);
      if (rs.next())
        re = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return re;
  }
  
  public void setReport(String id, String reportName, String previousReport) throws Exception {
    begin();
    try {
      WorkReportPO po = (WorkReportPO)this.session.load(WorkReportPO.class, new Long(id));
      po.setReportName(reportName);
      po.setPreviousReport(previousReport);
      this.session.update(po);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
}
