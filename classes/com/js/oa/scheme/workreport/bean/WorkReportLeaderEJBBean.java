package com.js.oa.scheme.workreport.bean;

import com.js.oa.scheme.workreport.po.WorkReportLeaderPO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.oa.scheme.workreport.po.WorkReportPostilPO;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.text.SimpleDateFormat;
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

public class WorkReportLeaderEJBBean extends HibernateBase implements SessionBean {
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
    List<Object[]> transmitLog = new ArrayList();
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
          "select po.id, po.reportTime,po.reportType,po.templateId,po.reportReader,po.reportReader,po.reportReaderId,po.accessoryName,po.accessorySaveName,po.previousReport,po.nextReport,po.reportRemark,po.empId,po.reportEmpName,po.reportCourse,po.reportJob,po.reportDepart " + 

          
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
          "select ppo.postilEmpName,ppo.postilContent,ppo.postilTime,ppo.postilEmpSign,ppo.nextWorkClew,ppo.grade,ppo.postilEmpID,ppo.postilResult,ppo.postilGrade,ppo.id from com.js.oa.scheme.workreport.po.WorkReportPostilPO ppo  where ppo.report.id=" + 
          
          object + 
          " order by ppo.id desc").list();
      transmitLog = this.session.createQuery(
          "select po.transFromEMPName,po.transToEMPName,po.transReason,po.transTime,po.id  from com.js.oa.scheme.workreport.po.WorkReportTransmitPO  po where po.workreportID=" + 
          
          object + 
          " order by po.id ").list();
      vec.add(listReport);
      vec.add(listPostil);
      vec.add(transmitLog);
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
  
  public void add(String userName, String receiveRecordId, String userId, String postilContent, String usersId, String usersName, String nextWorkClew, Long domainId) throws Exception {
    begin();
    try {
      WorkReportPostilPO po = new WorkReportPostilPO();
      Date date = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat(
          "yy-MM-dd HH:mm:ss");
      String dateString = formatter.format(date);
      if (postilContent != null && !postilContent.trim().equals(""))
        postilContent = String.valueOf(postilContent) + "        (" + dateString + ")"; 
      po.setPostilContent(postilContent);
      po.setPostilEmpName(userName);
      po.setPostilTime(new Date());
      if (nextWorkClew != null && !nextWorkClew.trim().equals(""))
        nextWorkClew = String.valueOf(nextWorkClew) + "        (" + dateString + ")"; 
      po.setNextWorkClew(nextWorkClew);
      WorkReportPO rpo = ((WorkReportLeaderPO)this.session.load(
          WorkReportLeaderPO.class, 
          new Long(receiveRecordId))).getReport();
      po.setReport(rpo);
      List<WorkReportPostilPO> list = this.session.createQuery("select po from com.js.oa.scheme.workreport.po.WorkReportPostilPO po   left join po.report ppo where ppo.id=" + 
          
          rpo.getId() + 
          " and po.postilEmpID=" + 
          Long.parseLong(userId)).list();
      for (int i = 0; i < list.size(); i++) {
        WorkReportPostilPO tmpPO = list.get(i);
        if (postilContent != null && !postilContent.trim().equals("")) {
          if (tmpPO.getPostilContent() == null || 
            tmpPO.getPostilContent().trim().equals("")) {
            po.setPostilContent(postilContent);
          } else {
            po.setPostilContent(String.valueOf(tmpPO.getPostilContent()) + "\n" + 
                postilContent);
          } 
        } else {
          po.setPostilContent((tmpPO.getPostilContent() == null) ? "" : 
              tmpPO.getPostilContent());
        } 
        if (nextWorkClew != null && !nextWorkClew.trim().equals("")) {
          if (tmpPO.getNextWorkClew() == null || 
            tmpPO.getNextWorkClew().trim().equals("")) {
            po.setNextWorkClew(nextWorkClew);
          } else {
            po.setNextWorkClew(String.valueOf(tmpPO.getNextWorkClew()) + "\n" + 
                nextWorkClew);
          } 
        } else {
          po.setNextWorkClew((tmpPO.getNextWorkClew() == null) ? "" : 
              tmpPO.getNextWorkClew());
        } 
        this.session.delete(tmpPO);
        this.session.flush();
      } 
      List<String> tmp = this.session.createQuery("select po.empSignImg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
          userId).list();
      if (tmp.size() > 0 && tmp.get(0) != null)
        po.setPostilEmpSign(tmp.get(0)); 
      po.setPostilDomainId(domainId.longValue());
      po.setPostilEmpID(Long.parseLong(userId));
      Long dateid = (Long)this.session.save(po);
      if (!"".equals(postilContent) || !"".equals(nextWorkClew)) {
        Calendar tmp1 = Calendar.getInstance();
        tmp1.set(2050, 12, 12);
        String url = "WorkReportLeaderAction.do?action=load&receiveRecordId=" + receiveRecordId + "&status=1&pager.offset=0&reportType=1";
        String title = "领导评价了你的周报和提示下周的工作";
        RemindUtil.sendMessageToUsers(title, url, (new Long(rpo.getEmpId())).toString(), "WorkReportPostil", new Date(), tmp1.getTime(), po.getPostilEmpName(), dateid);
      } 
      if (usersId != null && !"".equals(usersId))
        updateLeader(rpo, usersId, usersName); 
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
  
  private void updateLeader(WorkReportPO rpo, String usersId, String usersName) throws Exception {
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
            this.session.save(lpo);
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
              this.session.save(lpo1);
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
  
  public List getTopnNotReadReport(String readerId, String domainId, Integer top, Integer reportType) throws HibernateException {
    List list = null;
    begin();
    String wherePara = "";
    if (reportType.intValue() == 1) {
      wherePara = " and report.reportType=1 ";
    } else if (reportType.intValue() == 3) {
      wherePara = " and report.reportType=3 ";
    } else {
      wherePara = " and report.reportType not in (1,3) ";
    } 
    try {
      list = null;
      Query query = this.session.createQuery("select po.id,po.hadRead,report.reportEmpName,report.reportType,report.reportTime,report.reportReader,report.empId from com.js.oa.scheme.workreport.po.WorkReportLeaderPO po join po.report report where po.empId=" + 
          
          readerId + wherePara + 
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
  
  public void addMonthReport(String userName, String receiveRecordId, String userId, String postilContent, String usersId, String usersName, String nextWorkClew, String grade, String postilResult, String postilGrade, Long domainId) throws Exception {
    begin();
    try {
      WorkReportPostilPO po = new WorkReportPostilPO();
      po.setPostilContent(postilContent);
      po.setPostilEmpName(userName);
      po.setPostilTime(new Date());
      Date date = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat(
          "yy-MM-dd HH:mm:ss");
      String dateString = formatter.format(date);
      if (nextWorkClew != null && !nextWorkClew.trim().equals(""))
        nextWorkClew = String.valueOf(nextWorkClew) + "        (" + dateString + ")"; 
      po.setNextWorkClew(nextWorkClew);
      WorkReportPO rpo = ((WorkReportLeaderPO)this.session.load(
          WorkReportLeaderPO.class, 
          new Long(receiveRecordId))).getReport();
      po.setReport(rpo);
      List<WorkReportPostilPO> list = this.session.createQuery("select po from com.js.oa.scheme.workreport.po.WorkReportPostilPO po   left join po.report ppo where ppo.id=" + 
          
          rpo.getId() + 
          " and po.postilEmpID=" + 
          Long.parseLong(userId)).list();
      for (int i = 0; i < list.size(); i++) {
        WorkReportPostilPO tmpPO = list.get(i);
        if (nextWorkClew != null && !nextWorkClew.trim().equals("")) {
          if (tmpPO.getNextWorkClew() == null || 
            tmpPO.getNextWorkClew().trim().equals("")) {
            po.setNextWorkClew(nextWorkClew);
          } else {
            po.setNextWorkClew(String.valueOf(tmpPO.getNextWorkClew()) + "\n" + 
                nextWorkClew);
          } 
        } else {
          po.setNextWorkClew((tmpPO.getNextWorkClew() == null) ? "" : 
              tmpPO.getNextWorkClew());
        } 
        this.session.delete(tmpPO);
        this.session.flush();
      } 
      List<String> tmp = this.session.createQuery("select po.empSignImg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
          userId).list();
      if (tmp.size() > 0 && tmp.get(0) != null)
        po.setPostilEmpSign(tmp.get(0)); 
      po.setPostilDomainId(domainId.longValue());
      po.setPostilEmpID(Long.parseLong(userId));
      po.setGrade(grade);
      po.setPostilResult(postilResult);
      po.setPostilGrade(postilGrade);
      Long dataid = (Long)this.session.save(po);
      if (!"".equals(postilContent) || !"".equals(nextWorkClew)) {
        Calendar tmp1 = Calendar.getInstance();
        tmp1.set(2050, 12, 12);
        String url = "WorkReportLeaderAction.do?action=load&receiveRecordId=" + receiveRecordId + "&status=1&pager.offset=0&reportType=3";
        String title = "领导评价了你的月报和提示下月的工作";
        RemindUtil.sendMessageToUsers(title, url, (new Long(rpo.getEmpId())).toString(), "MonthPostil", new Date(), tmp1.getTime(), po.getPostilEmpName(), dataid);
      } 
      if (usersId != null && !"".equals(usersId))
        updateLeader(rpo, usersId, usersName); 
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
  
  public Boolean back(Long reportId, String leaderName, String reportType) throws HibernateException {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      this.session.delete("from com.js.oa.scheme.workreport.po.WorkReportLeaderPO po where po.report.id=" + 
          reportId);
      this.session.flush();
      WorkReportPO po = (WorkReportPO)this.session.load(WorkReportPO.class, reportId);
      po.setHadRead(Byte.parseByte("0"));
      po.setSendType("0");
      this.session.flush();
      this.session.close();
      Calendar calendar = Calendar.getInstance();
      calendar.set(2050, 12, 12);
      String url = "";
      String title = "";
      String leaderid = po.getReportReaderId();
      if ("1".equals(reportType)) {
        url = "WorkReportAction.do?action=load&receiveRecordId=" + reportId + "&status=1&pager.offset=0&reportType1=1&back=1&fromdesktop=1";
        title = "领导退回了你的周报";
      } else if ("3".equals(reportType)) {
        url = "WorkReportAction.do?action=load&receiveRecordId=" + reportId + "&status=1&pager.offset=0&reportType1=3&back=1&fromdesktop=1";
        title = "领导退回了你的月报";
      } else if ("none".equals(reportType)) {
        url = "WorkReportProductAction.do?action=load&receiveRecordId=" + reportId + "&status=1&pager.offset=0&reportType1=none&back=1&fromdesktop=1";
        String huibao = "其他工作汇报";
        if ("1".equals(SystemCommon.getReport()))
          huibao = "工作汇报"; 
        title = "领导退回了你的" + huibao;
      } 
      RemindUtil.sendMessageToUsers(title, url, (new Long(po.getEmpId())).toString(), "WorkReportPostil", new Date(), calendar.getTime(), leaderName, reportId);
    } catch (Exception e) {
      this.session.close();
      e.printStackTrace();
    } 
    return ret;
  }
  
  public String getByIds(String sendRecordId, String receiveRecordId, String status, Long domanId) throws HibernateException {
    String result = "N";
    try {
      String from;
      List listReport = new ArrayList();
      begin();
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
          "select po.id, po.reportTime,po.reportType,po.templateId,po.reportReader,po.reportReader,po.reportReaderId,po.accessoryName,po.accessorySaveName,po.previousReport,po.nextReport,po.reportRemark,po.empId,po.reportEmpName,po.reportCourse,po.reportJob,po.reportDepart " + 

          
          from).list();
      if (listReport.size() > 0)
        result = "Y"; 
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
}
