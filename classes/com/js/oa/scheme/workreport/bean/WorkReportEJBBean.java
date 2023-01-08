package com.js.oa.scheme.workreport.bean;

import com.js.oa.scheme.workreport.po.WorkReportLeaderPO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.oa.scheme.workreport.po.WorkReportTemplatePO;
import com.js.oa.scheme.workreport.po.WorkReportTransmitPO;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.usermanager.EmployeeVO;
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
import net.sf.hibernate.Query;

public class WorkReportEJBBean extends HibernateBase implements SessionBean {
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
      result.put("reportCourse", po.getReportCourse());
      result.put("reportJob", po.getReportJob());
      result.put("reportDepart", po.getReportDepart());
      result.put("reportEmpName", po.getReportEmpName());
      result.put("sendType", po.getSendType());
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
  
  public Boolean add(String userId, String orgId, String userName, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para, String reportName) throws Exception {
    Boolean ret = new Boolean(true);
    try {
      begin();
      if (para.length > 12) {
        Iterator itor = this.session.iterate("select po.id from com.js.oa.scheme.workreport.po.WorkReportPO po where po.reportCourse='" + para[7] + "' and po.empId=" + userId + " and po.relprojectId=" + para[12] + " and po.relprojectId <> -1");
        if (itor.hasNext()) {
          ret = Boolean.FALSE;
          this.session.close();
          return ret;
        } 
      } 
      WorkReportPO po = new WorkReportPO();
      po.setEmpId(Long.parseLong(userId));
      po.setReportEmpName(userName);
      po.setReportName(reportName);
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
      po.setHadRead(Byte.parseByte("0"));
      po.setReportReader(para[0]);
      po.setReportReaderId(para[1]);
      po.setReportRemark(para[2]);
      String str3 = para[3];
      if (!"none".equals(str3))
        po.setTemplateId(Long.parseLong(str3)); 
      po.setReportType(Byte.parseByte(para[4]));
      if (para[5] == null || "".equals(para[5].trim()) || "null".equals(para[5])) {
        po.setReportTime(new Date());
      } else {
        po.setReportTime(new Date(para[5]));
      } 
      po.setReportDomainId(Long.parseLong(para[6]));
      po.setReportCourse(para[7]);
      po.setReportJob(para[8]);
      po.setReportDepart(para[9]);
      po.setReportOrgID(para[10]);
      po.setSendType(para[11]);
      po.setReportInputTime(new Date());
      if (para.length > 12) {
        po.setRelprojectId(Long.valueOf(para[12]).longValue());
      } else {
        po.setRelprojectId(-1L);
      } 
      po.setPreviousReport(previousReport);
      po.setNextReport(nextReport);
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return ret;
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
  
  private void updateLeader(String ids, WorkReportPO po, long userId, String sendtype) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    try {
      WorkReportLeaderPO poo = null;
      Iterator<WorkReportLeaderPO> iter = this.session.iterate("select poo from com.js.oa.scheme.workreport.po.WorkReportLeaderPO poo join poo.report rpo where rpo.id=" + 
          po.getId());
      while (iter.hasNext()) {
        poo = iter.next();
        this.session.delete(poo);
        this.session.flush();
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("delete from sys_messages where data_id= " + po.getId() + "  and message_type like 'WorkReport'");
      stmt.close();
      if (sendtype.equals("1"))
        addLeader(ids, po, (new StringBuilder(String.valueOf(userId))).toString(), (new StringBuilder(String.valueOf(po.getReportDomainId()))).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (conn != null)
        conn.close(); 
    } 
  }
  
  public void update(String editId, String userId, String orgId, String nextReport, String previousReport, String[] accessoryName, String[] accessorySaveName, String[] para) throws Exception {
    begin();
    try {
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
      String str3 = para[3];
      if (!"none".equals(str3))
        po.setTemplateId(Long.parseLong(str3)); 
      po.setReportType(Byte.parseByte(para[4]));
      po.setReortUpdateTime(new Date());
      po.setSendType(para[6]);
      if (para.length > 7)
        po.setRelprojectId(Long.valueOf(para[7]).longValue()); 
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
          " or ( (po.templateUseOrg is null or po.templateUseOrg='') and ( po.templateUseEmp is null or po.templateUseEmp='')and (po.templateUseGroup is null or po.templateUseGroup='')) " + 
          " order by po.templateName")
        .list();
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
      retString = (po.getTemplateContent() == null) ? "" : 
        po.getTemplateContent();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public EmployeeVO getEmployeeByID(Long userID) throws Exception {
    EmployeeVO vo = null;
    begin();
    try {
      vo = (EmployeeVO)this.session.load(EmployeeVO.class, userID);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return vo;
  }
  
  public String getWorkReportContentByCourse(String curUserID, String reportCourse) throws Exception {
    String content = "";
    begin();
    try {
      Iterator<String> itor = this.session.iterate("select po.previousReport from com.js.oa.scheme.workreport.po.WorkReportPO po where po.reportCourse='" + 
          reportCourse + "' and po.empId=" + 
          Long.parseLong(curUserID));
      if (itor.hasNext())
        content = itor.next(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return content;
  }
  
  public List getReportData(String year, String orgID, String reportDomainId, String curUserId, List<Object[]> rightlist) throws Exception {
    List<String[]> list = new ArrayList();
    try {
      String strSQL = " select distinct po.empId,po.reportEmpName,po.reportDepart,po.reportJob,po.reportOrgID from com.js.oa.scheme.workreport.po.WorkReportPO po  where po.reportType=3 and po.reportDomainId=" + 
        
        reportDomainId + 
        " and po.reportCourse like '" + year + "%'";
      if (!orgID.equals("0") && 
        rightlist != null && rightlist.size() > 0) {
        Object[] obj = rightlist.get(0);
        if (obj[0] != null && (
          obj[0].toString().equals("2") || 
          obj[0].toString().equals("0"))) {
          String ss = String.valueOf(orgID) + ",";
          List<E> sonlist = getSons(orgID);
          for (int j = 0; j < sonlist.size(); j++)
            ss = String.valueOf(ss) + sonlist.get(j).toString() + ","; 
          ss = ss.substring(0, ss.trim().length() - 1);
          strSQL = String.valueOf(strSQL) + " and (" + convertStr(ss, "po.reportOrgID") + ")";
        } else if (obj[0] != null && obj[0].toString().equals("4")) {
          String rang = obj[1].toString();
          rang = rang.substring(1, rang.length() - 1);
          String[] tmp = rang.split("\\*\\*");
          if (tmp.length > 0) {
            String ss = "";
            for (int j = 0; j < tmp.length; j++) {
              ss = String.valueOf(ss) + tmp[j] + ",";
              List<E> sonlist = getSons(tmp[j]);
              for (int k = 0; k < sonlist.size(); k++)
                ss = String.valueOf(ss) + sonlist.get(k).toString() + ","; 
            } 
            ss = ss.substring(0, 
                ss.trim().length() - 1);
            strSQL = String.valueOf(strSQL) + " and (" + convertStr(ss, "po.reportOrgID") + ")";
          } 
        } else {
          strSQL = String.valueOf(strSQL) + " and po.reportOrgID=" + orgID;
        } 
      } 
      begin();
      List<Object[]> reportEmpID = this.session.createQuery(strSQL).list();
      for (int i = 0; i < reportEmpID.size(); i++) {
        Object[] empIDobj = reportEmpID.get(i);
        String[] result = new String[15];
        result[0] = empIDobj[1].toString();
        result[1] = empIDobj[2].toString();
        result[2] = (empIDobj[3] == null) ? "" : empIDobj[3].toString();
        for (int j = 1; j < 13; j++) {
          String sql = 
            " select po.id from com.js.oa.scheme.workreport.po.WorkReportPO po where po.reportDomainId=" + 
            reportDomainId + 
            " and po.reportType=3 and po.empId=" + empIDobj[0] + " and po.reportOrgID=" + empIDobj[4];
          if (j < 10) {
            sql = String.valueOf(sql) + " and po.reportCourse='" + year + "0" + j + "'";
          } else {
            sql = String.valueOf(sql) + " and po.reportCourse='" + year + j + "'";
          } 
          Iterator<E> itor = this.session.iterate(sql);
          if (itor.hasNext()) {
            long reportID = Long.parseLong(itor.next().toString());
            result[j + 2] = getGrade(reportID, reportDomainId);
          } else {
            result[j + 2] = "";
          } 
        } 
        list.add(result);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  private String getGrade(long reportID, String domainID) throws Exception {
    String grade = "";
    try {
      String strSQL = " select po.grade,po.postilGrade from com.js.oa.scheme.workreport.po.WorkReportPostilPO po   left join po.report ppo where ppo.id=" + 
        
        reportID + 
        " and po.postilDomainId=" + 
        domainID;
      List<Object[]> list = this.session.createQuery(strSQL).list();
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        if (obj[0] != null && !"".equals(obj[0].toString()) && 
          !"-".equals(obj[0].toString()))
          grade = String.valueOf(grade) + obj[0] + "/"; 
        if (obj[1] != null && !"0".equals(obj[1].toString())) {
          grade = obj[1].toString();
          break;
        } 
      } 
      if (grade.length() > 1 && 
        grade.substring(grade.length() - 1, grade.length()).equals("/"))
        grade = grade.substring(0, grade.length() - 1); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return grade;
  }
  
  public Integer getUnderLingCount(String userID) throws Exception {
    Integer underlingCount = new Integer(0);
    begin();
    System.out.println(userID);
    try {
      Iterator<Integer> itor = this.session.iterate("select count(*) from com.js.system.vo.usermanager.EmployeeVO po where po.empLeaderId like '%$" + 
          userID + 
          "$%' and po.userIsDeleted=0");
      if (itor.hasNext())
        underlingCount = itor.next(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return underlingCount;
  }
  
  private List getSons(String orgId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
          orgId + "$%' and aaa.orgId <> " + 
          orgId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void deliverTO(String recieveID, String reportReadID, WorkReportTransmitPO wrtpo) throws Exception {
    try {
      begin();
      WorkReportLeaderPO wrlpo = (WorkReportLeaderPO)this.session.load(
          WorkReportLeaderPO.class, new Long(recieveID));
      WorkReportPO wrpo = (WorkReportPO)this.session.load(WorkReportPO.class, 
          new Long(wrlpo.getReport().getId()));
      String[] arr = ("$" + reportReadID + "$").split("\\$\\$");
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] != null && !"".equals(arr[i])) {
          List<WorkReportLeaderPO> list = this.session.createQuery(
              "select po from com.js.oa.scheme.workreport.po.WorkReportLeaderPO po  where po.empId=" + 
              Long.parseLong(arr[i]) + 
              " and po.report.id=" + wrpo.getId() + 
              " and po.rlDomainId=" + wrpo.getReportDomainId())
            .list();
          if (list == null || list.size() == 0) {
            WorkReportLeaderPO lpo = new WorkReportLeaderPO();
            lpo.setEmpId(Long.parseLong(arr[i]));
            lpo.setHadRead(Byte.parseByte("0"));
            lpo.setReport(wrpo);
            lpo.setRlDomainId(wrpo.getReportDomainId());
            this.session.save(lpo);
          } else {
            WorkReportLeaderPO lpo = new WorkReportLeaderPO();
            lpo = list.get(0);
            lpo.setHadRead(Byte.parseByte("0"));
            this.session.update(lpo);
          } 
        } 
      } 
      if (wrtpo != null) {
        Long dateId = (Long)this.session.save(wrtpo);
        String reportType = (new Integer(wrpo.getReportType())).toString();
        String title = "";
        String title1 = "";
        String url = "";
        if ("1".equals(reportType)) {
          title = String.valueOf(wrtpo.getTransFromEMPName()) + "把你的周报转发给" + wrtpo.getTransToEMPName();
          title1 = String.valueOf(wrtpo.getTransFromEMPName()) + "把" + wrpo.getReportEmpName() + "的周报转发给你";
          url = "WorkReportLeaderAction.do?action=load&receiveRecordId=" + recieveID + "&status=1&pager.offset=0&reportType=1&fromDesktop=yes";
        } else if ("3".equals(reportType)) {
          title = String.valueOf(wrtpo.getTransFromEMPName()) + "把你的月报转发给" + wrtpo.getTransToEMPName();
          title1 = String.valueOf(wrtpo.getTransFromEMPName()) + "把" + wrpo.getReportEmpName() + "的月报转发给你";
          url = "WorkReportLeaderAction.do?action=load&receiveRecordId=" + recieveID + "&status=1&pager.offset=0&reportType=3&fromDesktop=yes";
        } 
        Calendar tmp1 = Calendar.getInstance();
        tmp1.set(2050, 12, 12);
        RemindUtil.sendMessageToUsers(title, url, (new Long(wrpo.getEmpId())).toString(), "WorkReportTrans", new Date(), tmp1.getTime(), wrtpo.getTransFromEMPName(), dateId);
        String userids = wrtpo.getTransToEMP();
        userids = userids.replace("$$", ",");
        userids = userids.substring(1, userids.length() - 1);
        RemindUtil.sendMessageToUsers(title1, url, userids, "WorkReportTrans", new Date(), tmp1.getTime(), wrtpo.getTransFromEMPName(), dateId);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public List getSonsByName(String orgName) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.orgId, aaa.orgName from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgNameString like '%" + 
          orgName + "%' and aaa.orgName <> ' " + 
          orgName + "'");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  private String convertStr(String strIds, String fields) {
    StringBuffer where = new StringBuffer();
    String[] tmp = strIds.split(",");
    int max = 500;
    if (tmp.length > max) {
      int t = (tmp.length % max == 0) ? (
        tmp.length / max) : (
        tmp.length / max + 1);
      for (int i = 0; i < t; i++) {
        if (i == 0) {
          where.append(String.valueOf(fields) + " in (-1");
          for (int j = 0; j < max; j++) {
            if (i * max + j < 
              tmp.length)
              where.append(",")
                .append(
                  tmp[i * max + j]); 
          } 
          where.append(")");
        } else {
          where.append(
              " or " + fields + " in (-1");
          for (int j = 0; j < max; j++) {
            if (i * max + j < 
              tmp.length)
              where.append(",")
                .append(
                  tmp[i * max + j]); 
          } 
          where.append(")");
        } 
      } 
    } else {
      where.append(String.valueOf(fields) + " in (");
      where.append(strIds);
      where.append(")");
    } 
    return where.toString();
  }
  
  public List getWorkReportPO(String userId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select po.id, po.reportName,po.reportTime,po.hadRead from com.js.oa.scheme.workreport.po.WorkReportPO po where po.sendType<>0 and po.empId=" + 
          userId + " order by po.reportTime desc");
      query.setMaxResults(25);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
}
