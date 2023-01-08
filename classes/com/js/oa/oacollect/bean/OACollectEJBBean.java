package com.js.oa.oacollect.bean;

import com.js.oa.jsflow.po.WFWorkPO;
import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.oacollect.po.OaCollect;
import com.js.oa.oacollect.po.OaCollectEmp;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterGenerator;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class OACollectEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveOATaskCollect(OaCollect oaCollect, HttpServletRequest request) throws Exception {
    begin();
    Long collectId = null;
    HttpSession httpSession = request.getSession(true);
    String userName = httpSession.getAttribute("userName").toString();
    String userId = httpSession.getAttribute("userId").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    try {
      collectId = (Long)this.session.save(oaCollect);
      this.session.flush();
      String[] idAndName = getEmpByOrgOrGroup(oaCollect.getCollectEmps(), oaCollect.getCollectEmpNames());
      String empIds = idAndName[0];
      String empNames = idAndName[1];
      if (empIds != null && !"".equals(empIds) && empNames != null && !"".equals(empNames)) {
        empIds = empIds.substring(1, empIds.length() - 1);
        String[] empIdArr = empIds.split("\\$\\$");
        String[] empNameArr = empNames.split(",");
        OaCollectEmp collectEmp = null;
        for (int i = 0; i < empIdArr.length && empIdArr.length == empNameArr.length; i++) {
          collectEmp = new OaCollectEmp();
          collectEmp.setEmpId(new Long(empIdArr[i]));
          collectEmp.setEmpName(empNameArr[i]);
          collectEmp.setCollectId(collectId);
          collectEmp.setEmpStatus(new Long(0L));
          collectEmp.setRemindCount(new Long(0L));
          this.session.save(collectEmp);
          this.session.flush();
        } 
      } 
      this.session.flush();
      if (empIds != null && !"".equals(empIds) && empIds != null && !"".equals(empIds) && 1L == oaCollect.getCollectEnable().longValue() && collectId != null && oaCollect.getCollectEndTime() != null && !"".equals(oaCollect.getCollectEndTime()) && 
        !oaCollect.getCollectEndTime().before(Timestamp.valueOf(String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
        String tempRemindTitle = "数据采集任务：" + oaCollect.getCollectTitle();
        String url = "/jsoa/oacollect/collectMessage.jsp?collectId=" + collectId;
        int showType = 1;
        if (oaCollect.getCollectZl().longValue() == 1L) {
          url = "/jsoa/OACollectFileAction.do?action=fileList&collectId=" + collectId;
          showType = 0;
        } 
        String empIdsTemp = empIds.replaceAll("\\$\\$", ",");
        RemindUtil.sendMessageToUsers2(tempRemindTitle, String.valueOf(url) + "&fromFlagFlow=fromDuBan", empIdsTemp, "collect", new Date(), new Date("2050/1/1"), "系统", collectId, showType);
        String[] empArr = empIdsTemp.split(",");
        for (int k = 0; k < empArr.length; k++) {
          WFWorkPO wp = new WFWorkPO();
          Long wfWorkId = getTableId();
          wp.setWfWorkId(wfWorkId);
          wp.setWorkMainLinkFile(String.valueOf(url) + "&fromFlagFlow=fromDaiBan&wfWorkId=" + wfWorkId);
          wp.setWfCurEmployeeId(new Long(empArr[k]));
          wp.setWorkFileType("数据采集");
          wp.setWorkStatus(Integer.valueOf(0));
          wp.setWorkFileType("数据采集");
          wp.setWorkCurStep("数据采集");
          wp.setWorkTitle(tempRemindTitle);
          wp.setWorkLeftLinkFile("");
          wp.setWorkListControl(Integer.valueOf(1));
          wp.setWorkActivity(new Long(0L));
          wp.setWorkSubmitPerson(userName);
          wp.setWorkSubmitTime(new Date());
          wp.setWfSubmitEmployeeId(new Long(userId));
          wp.setWorkReadMarker(Integer.valueOf(0));
          wp.setWorkType(Integer.valueOf(1));
          wp.setWorkProcessId(new Long(12L));
          wp.setWorkTableId(new Long(-1L));
          wp.setWorkRecordId(collectId);
          wp.setWorkDeadLine(new Long(-1L));
          wp.setWorkPressTime(new Long(-1L));
          wp.setWorkCreateDate(new Date());
          wp.setWorkStartFlag(Integer.valueOf(1));
          wp.setWorkAllowCancel(Integer.valueOf(0));
          wp.setWorkDelete(Integer.valueOf(0));
          wp.setIsSubProcWork("0");
          wp.setPareProcActiId("0");
          wp.setPareStepCount("0");
          wp.setPareTableId("0");
          wp.setPareRecordId("0");
          wp.setPareProcNextActiId("0");
          wp.setSubmitOrg(orgName);
          wp.setDomainId(new Long(0L));
          wp.setEmergence("0");
          wp.setTransActType("1");
          wp.setRelProjectId(new Long(-1L));
          wp.setInitActivity(new Long(collectId.longValue()));
          this.session.save(wp);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return collectId;
  }
  
  public OaCollect loadOaTaskCollect(Long collectId) throws Exception {
    OaCollect oaCollect = null;
    begin();
    try {
      oaCollect = (OaCollect)this.session.get(OaCollect.class, collectId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return oaCollect;
  }
  
  public Map<String, String> loadEmpMap(String empIds) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    Map<String, String> map = new HashMap<String, String>();
    try {
      base.begin();
      String sql = "SELECT ou.EMP_ID,o.ORG_ID,o.orgName FROM org_organization o JOIN org_organization_user ou ON o.ORG_ID=ou.ORG_ID WHERE ou.EMP_ID IN (" + 
        empIds + ")";
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (map.get(rs.getString(1)) == null)
          map.put(rs.getString(1), rs.getString(3)); 
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return map;
  }
  
  public String[] loadEmpNum(String collectId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String[] empNum = new String[3];
    try {
      base.begin();
      String sql = "SELECT COUNT(id) FROM oa_collect_emp WHERE collect_id=" + collectId + " AND emp_status=0";
      rs = base.executeQuery(sql);
      if (rs.next())
        empNum[0] = rs.getString(1); 
      rs.close();
      sql = "SELECT COUNT(id) FROM oa_collect_emp WHERE collect_id=" + collectId + " AND emp_status=1";
      rs = base.executeQuery(sql);
      if (rs.next())
        empNum[1] = rs.getString(1); 
      empNum[2] = (new StringBuilder(String.valueOf(Integer.valueOf(empNum[0]).intValue() + Integer.valueOf(empNum[1]).intValue()))).toString();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empNum;
  }
  
  public boolean updateOATaskCollect(OaCollect oaCollect, HttpServletRequest request) throws HibernateException {
    boolean re = true;
    HttpSession httpSession = request.getSession(true);
    String userName = httpSession.getAttribute("userName").toString();
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    DataSourceBase dataBase = new DataSourceBase();
    begin();
    try {
      OaCollect oldOaCollect = loadOaTaskCollect(oaCollect.getCollectId());
      if (!oaCollect.getCollectEnable().toString().equals(oldOaCollect.getCollectEnable().toString()) || 
        !oaCollect.getCollectEmps().equals(oldOaCollect.getCollectEmps()) || 
        !oaCollect.getCollectTitle().equals(oldOaCollect.getCollectTitle())) {
        if (oaCollect.getCollectEmps() != null && !"".equals(oaCollect.getCollectEmps()) && oaCollect.getCollectId() != null && 
          1L == oaCollect.getCollectEnable().longValue() && oaCollect.getCollectEndTime() != null && !"".equals(oaCollect.getCollectEndTime()) && 
          !oaCollect.getCollectEndTime().before(Timestamp.valueOf(String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
          String querySql = "select p.wfWorkId,p.wfCurEmployeeId from  com.js.oa.jsflow.po.WFWorkPO p where p.workRecordId=" + oaCollect.getCollectId() + 
            " and p.workFileType='数据采集'";
          begin();
          List<Object[]> wList = this.session.createQuery(querySql).list();
          String tempRemindTitle = "数据采集任务：" + oaCollect.getCollectTitle();
          String url = "/jsoa/oacollect/collectMessage.jsp?collectId=" + oaCollect.getCollectId();
          int showType = 1;
          if (oaCollect.getCollectZl().longValue() == 1L) {
            url = "/jsoa/OACollectFileAction.do?action=fileList&collectId=" + oaCollect.getCollectId();
            showType = 0;
          } 
          String[] idAndName = getEmpByOrgOrGroup(oaCollect.getCollectEmps(), oaCollect.getCollectEmpNames());
          String empIdsTemp = idAndName[0];
          empIdsTemp = empIdsTemp.substring(1, empIdsTemp.length() - 1).replace("$$", ",");
          String[] empIdsTempArr = empIdsTemp.split(",");
          begin();
          for (int i = 0; i < empIdsTempArr.length; i++) {
            String oldEmpIdsTemp = getOldEmp((String)oldOaCollect.getCollectId())[0];
            if (oldEmpIdsTemp.length() > 0)
              oldEmpIdsTemp = oldEmpIdsTemp.substring(1, oldEmpIdsTemp.length() - 1).replace("$$", ","); 
            String[] oldEmpIdsTempArr = oldEmpIdsTemp.split(",");
            boolean f = false;
            if (oldEmpIdsTempArr != null && oldEmpIdsTempArr.length > 0)
              for (int k = 0; k < oldEmpIdsTempArr.length; k++) {
                if (empIdsTempArr[i].equals(oldEmpIdsTempArr[k])) {
                  f = true;
                  break;
                } 
              }  
            if (wList != null && wList.size() > 0) {
              for (int k = 0; k < wList.size(); k++) {
                Object[] obj = wList.get(k);
                if (empIdsTempArr[i].equals(obj[1].toString())) {
                  f = true;
                  break;
                } 
              } 
            } else {
              f = false;
            } 
            if (!f) {
              RemindUtil.sendMessageToUsers2(tempRemindTitle, String.valueOf(url) + "&fromFlagFlow=fromDuBan", empIdsTempArr[i], "collect", new Date(), new Date("2050/1/1"), "系统", oaCollect.getCollectId(), showType);
              WFWorkPO wp = new WFWorkPO();
              Long wfWorkId = getTableId();
              wp.setWfWorkId(wfWorkId);
              wp.setWorkMainLinkFile(String.valueOf(url) + "&fromFlagFlow=fromDaiBan&wfWorkId=" + wfWorkId);
              wp.setWfCurEmployeeId(new Long(empIdsTempArr[i]));
              wp.setWorkFileType("数据采集");
              wp.setWorkStatus(Integer.valueOf(0));
              wp.setWorkCurStep("数据采集");
              wp.setWorkTitle(tempRemindTitle);
              wp.setWorkLeftLinkFile("");
              wp.setWorkListControl(Integer.valueOf(1));
              wp.setWorkActivity(new Long(0L));
              wp.setWorkSubmitPerson(userName);
              wp.setWorkSubmitTime(new Date());
              wp.setWfSubmitEmployeeId(new Long(userId));
              wp.setWorkReadMarker(Integer.valueOf(0));
              wp.setWorkType(Integer.valueOf(1));
              wp.setWorkProcessId(new Long(12L));
              wp.setWorkTableId(new Long(-1L));
              wp.setWorkRecordId(oaCollect.getCollectId());
              wp.setWorkDeadLine(new Long(-1L));
              wp.setWorkPressTime(new Long(-1L));
              wp.setWorkCreateDate(new Date());
              wp.setWorkStartFlag(Integer.valueOf(1));
              wp.setWorkAllowCancel(Integer.valueOf(0));
              wp.setWorkDelete(Integer.valueOf(0));
              wp.setIsSubProcWork("0");
              wp.setPareProcActiId("0");
              wp.setPareStepCount("0");
              wp.setPareTableId("0");
              wp.setPareRecordId("0");
              wp.setPareProcNextActiId("0");
              wp.setSubmitOrg(orgName);
              wp.setDomainId(new Long(0L));
              wp.setEmergence("0");
              wp.setTransActType("1");
              wp.setRelProjectId(new Long(-1L));
              wp.setInitActivity(new Long(oaCollect.getCollectId().longValue()));
              this.session.save(wp);
            } 
          } 
          this.session.flush();
        } 
        if (!oaCollect.getCollectEnable().toString().equals(oldOaCollect.getCollectEnable().toString()) && 0L == oaCollect.getCollectEnable().longValue()) {
          String dateString = "now()";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("oracle") >= 0)
            dateString = "sysdate"; 
          String sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + " where WORKRECORD_ID=" + oaCollect.getCollectId() + 
            " and WORKFILETYPE='数据采集' and INITACTIVITY=" + oaCollect.getCollectId();
          updateBySql(sql);
        } 
        if (!oaCollect.getCollectEnable().toString().equals(oldOaCollect.getCollectEnable().toString()) && 1L == oaCollect.getCollectEnable().longValue()) {
          String dateString = "now()";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("oracle") >= 0)
            dateString = "sysdate"; 
          String sql = "update JSF_WORK set WORKDONEWITHDATE=null where WORKRECORD_ID=" + oaCollect.getCollectId() + 
            " and WORKFILETYPE='数据采集' and INITACTIVITY=" + oaCollect.getCollectId();
          updateBySql(sql);
        } 
      } 
      begin();
      this.session.update(oaCollect);
      this.session.flush();
      String[] tmp = getEmpByOrgOrGroup(oaCollect.getCollectEmps(), oaCollect.getCollectEmpNames());
      String empIds = tmp[0];
      String empNames = tmp[1];
      if (empIds != null && !"".equals(empIds) && empNames != null && !"".equals(empNames)) {
        empIds = empIds.substring(1, empIds.length() - 1);
        String[] empIdArr = empIds.split("\\$\\$");
        String[] empNameArr = empNames.split(",");
        String sql = "from com.js.oa.oacollect.po.OaCollectEmp po where po.collectId=" + oaCollect.getCollectId() + 
          " and po.empId not in(" + empIds.replace("$$", ",") + ") ";
        this.session.delete(sql);
        this.session.flush();
        sql = "from com.js.oa.oacollect.po.OaCollectFile po where po.collectId=" + oaCollect.getCollectId() + 
          " and po.collectEmpId not in(" + empIds.replace("$$", ",") + ") ";
        this.session.delete(sql);
        this.session.flush();
        OaCollectEmp collectEmp = null;
        for (int i = 0; i < empIdArr.length && empIdArr.length == empNameArr.length; i++) {
          sql = "select po.id,po.empId from com.js.oa.oacollect.po.OaCollectEmp po where  po.collectId=" + oaCollect.getCollectId() + 
            " and po.empId=" + empIdArr[i];
          List list = this.session.createQuery(sql).list();
          if (list == null || list.size() < 1) {
            collectEmp = new OaCollectEmp();
            collectEmp.setEmpId(new Long(empIdArr[i]));
            collectEmp.setEmpName(empNameArr[i]);
            collectEmp.setCollectId(oaCollect.getCollectId());
            collectEmp.setEmpStatus(new Long(0L));
            collectEmp.setRemindCount(new Long(0L));
            this.session.save(collectEmp);
          } else {
            sql = "update oa_collect_emp po set po.remind_count=po.remind_count+1 where  po.collect_id=" + oaCollect.getCollectId() + 
              " and po.emp_id=" + empIdArr[i];
            updateByYourYuanShengSql(sql);
            sql = "update oa_collect_file po set po.remind_count=po.remind_count+1 where  po.collect_id=" + oaCollect.getCollectId() + 
              " and po.collect_emp_id=" + empIdArr[i];
            updateByYourYuanShengSql(sql);
          } 
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public boolean updateJsfWorkByCollectId(String collectId) {
    boolean re = true;
    return re;
  }
  
  public boolean updateByYourYuanShengSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean deleteOATaskCollect(String ids, String ifDeleteFile) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      this.session.delete("from com.js.oa.oacollect.po.OaCollect po where po.collectId in (" + ids + ")");
      this.session.delete("from com.js.oa.oacollect.po.OaCollectEmp po where po.collectId in (" + ids + ")");
      this.session.delete("from com.js.oa.oacollect.po.OaCollectFile po where po.collectId in (" + ids + ")");
      this.session.flush();
      String dateString = "now()";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + ",WORKSTATUS=101 where WORKRECORD_ID  in(" + ids + ")" + 
        " and WORKFILETYPE='数据采集' and INITACTIVITY  in(" + ids + ")";
      updateBySql(sql);
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public boolean updateCollectTaskEnable(String ids, String inuse) throws Exception {
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      String sql = " update oa_collect set collect_enable=" + inuse + " where collect_id in(" + ids + ")";
      updateBySql(sql);
      if ("0".equals(inuse)) {
        String dateString = "now()";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0)
          dateString = "sysdate"; 
        sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + ",WORKSTATUS=101 where WORKRECORD_ID  in(" + ids + ")" + 
          " and WORKFILETYPE='数据采集' and INITACTIVITY  in(" + ids + ")";
        updateBySql(sql);
      } 
      if ("1".equals(inuse)) {
        String dateString = "now()";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0)
          dateString = "sysdate"; 
        sql = "update JSF_WORK set WORKDONEWITHDATE=null,WORKSTATUS=101 where WORKRECORD_ID  in(" + ids + ")" + 
          " and WORKFILETYPE='数据采集' and INITACTIVITY  in(" + ids + ")";
        updateBySql(sql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } 
    return re;
  }
  
  public boolean doRemind(String ids, String userName, Date begin) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids != null && !"".equals(ids)) {
        String[] idArr = ids.split(",");
        OaCollectEmp oaCollectEmp = null;
        StringBuffer emps = new StringBuffer("");
        for (int i = 0; i < idArr.length; i++) {
          oaCollectEmp = (OaCollectEmp)this.session.get(OaCollectEmp.class, new Long(idArr[i]));
          oaCollectEmp.setRemindCount(Long.valueOf(oaCollectEmp.getRemindCount().longValue() + 1L));
          if (i == 0) {
            emps.append(oaCollectEmp.getEmpId());
          } else {
            emps.append(",").append(oaCollectEmp.getEmpId());
          } 
          this.session.flush();
        } 
        OaCollect oaCollect = (OaCollect)this.session.get(OaCollect.class, oaCollectEmp.getCollectId());
        if (emps.toString() != null && !"".equals(emps.toString()) && oaCollect.getCollectId() != null && oaCollect.getCollectEndTime() != null && !"".equals(oaCollect.getCollectEndTime()) && 
          !oaCollect.getCollectEndTime().before(Timestamp.valueOf(String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
          String tempRemindTitle = "数据采集任务：" + oaCollect.getCollectTitle();
          String url = "/jsoa/oacollect/collectMessage.jsp?collectId=" + oaCollect.getCollectId() + "&fromFlagFlow=fromDuBan";
          int showType = 1;
          if (oaCollect.getCollectZl().longValue() == 1L) {
            url = "/jsoa/OACollectFileAction.do?action=fileList&collectId=" + oaCollect.getCollectId() + "&fromFlagFlow=fromDuBan";
            showType = 0;
          } 
          RemindUtil.sendMessageToUsers2(tempRemindTitle, url, emps.toString(), "collect", begin, new Date("2050/1/1"), userName, oaCollect.getCollectId(), showType);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public boolean doRemindAllNotDone(String collectId, String userName) throws Exception {
    begin();
    boolean re = true;
    try {
      if (collectId != null && !"".equals(collectId)) {
        String sql = "select po.id ,po.empId from com.js.oa.oacollect.po.OaCollectEmp po  where po.empStatus=0 and po.collectId=" + 
          collectId;
        List<Object[]> tempList = getListByYourSQL(sql);
        if (tempList != null && tempList.size() > 0) {
          OaCollectEmp oaCollectEmp = null;
          StringBuffer emps = new StringBuffer("");
          for (int i = 0; i < tempList.size(); i++) {
            Object[] obj = tempList.get(i);
            oaCollectEmp = (OaCollectEmp)this.session.get(OaCollectEmp.class, new Long(obj[0].toString()));
            oaCollectEmp.setRemindCount(Long.valueOf(oaCollectEmp.getRemindCount().longValue() + 1L));
            if (i == 0) {
              emps.append(oaCollectEmp.getEmpId());
            } else {
              emps.append(",").append(oaCollectEmp.getEmpId());
            } 
            this.session.flush();
          } 
          OaCollect oaCollect = (OaCollect)this.session.get(OaCollect.class, oaCollectEmp.getCollectId());
          if (emps.toString() != null && !"".equals(emps.toString()) && oaCollect.getCollectId() != null && oaCollect.getCollectEndTime() != null && !"".equals(oaCollect.getCollectEndTime()) && 
            !oaCollect.getCollectEndTime().before(Timestamp.valueOf(String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
            String tempRemindTitle = "数据采集任务：" + oaCollect.getCollectTitle();
            String url = "/jsoa/oacollect/collectMessage.jsp?collectId=" + oaCollect.getCollectId() + "&fromFlagFlow=fromDuBan";
            int showType = 1;
            if (oaCollect.getCollectZl().longValue() == 1L) {
              url = "/jsoa/OACollectFileAction.do?action=fileList&collectId=" + oaCollect.getCollectId() + "&fromFlagFlow=fromDuBan";
              showType = 0;
            } 
            RemindUtil.sendMessageToUsers2(tempRemindTitle, url, emps.toString(), "collect", new Date(), new Date("2050/1/1"), userName, oaCollect.getCollectId(), showType);
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public void doRemindSys() {
    String databaseType = SystemCommon.getDatabaseType();
    String where = "";
    if (databaseType.indexOf("oracle") >= 0) {
      where = String.valueOf(where) + " and (po.collectEndTime-sysdate)<=po.collectRemindDays";
    } else {
      where = String.valueOf(where) + " and (\tTO_DAYS(po.collectEndTime)-TO_DAYS(now()))<=po.collectRemindDays";
    } 
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String nowStr = format.format(new Date());
    if ("oracle".equals(databaseType)) {
      where = String.valueOf(where) + " and po.collectEndTime >=to_date('" + nowStr + "','yyyy-MM-dd HH24:mi:ss')";
      where = String.valueOf(where) + " and po.collectBeginTime <=to_date('" + nowStr + "','yyyy-MM-dd HH24:mi:ss')";
    } else {
      where = String.valueOf(where) + " and po.collectEndTime >='" + nowStr + "'";
      where = String.valueOf(where) + " and po.collectBeginTime <='" + nowStr + "'";
    } 
    String sql = "select e.id,e.collectId from com.js.oa.oacollect.po.OaCollectEmp e where e.collectId in(select po.collectId from com.js.oa.oacollect.po.OaCollect po where po.ifRemind=1 and po.collectEnable=1" + 
      
      where + ") and e.empStatus=0";
    StringBuffer empIds = new StringBuffer("");
    try {
      List<Object[]> tempList = getListByYourSQL(sql);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (i == 0) {
            empIds.append(obj[0].toString());
          } else {
            empIds.append(",").append(obj[0].toString());
          } 
        }  
      if (!"".equals(empIds.toString()))
        doRemind(empIds.toString(), "系统", new Date()); 
      where = "";
      if ("oracle".equals(databaseType)) {
        where = String.valueOf(where) + " and po.collectEndTime >=to_date('" + nowStr + "','yyyy-MM-dd HH24:mi:ss')";
        where = String.valueOf(where) + " and po.collectBeginTime <=to_date('" + nowStr + "','yyyy-MM-dd HH24:mi:ss')";
      } else {
        where = String.valueOf(where) + " and po.collectEndTime >='" + nowStr + "'";
        where = String.valueOf(where) + " and po.collectBeginTime <='" + nowStr + "'";
      } 
      sql = "select po.collectId,po.collectRemindTime from com.js.oa.oacollect.po.OaCollect po where po.ifRemindPerDay=1 and po.collectEnable=1" + 
        where;
      tempList = getListByYourSQL(sql);
      List perList = new ArrayList();
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          sql = "select e.id,e.collectId from com.js.oa.oacollect.po.OaCollectEmp e where e.collectId =" + obj[0].toString();
          List<Object[]> tempList2 = getListByYourSQL(sql);
          if (tempList2 != null && tempList2.size() > 0) {
            empIds = new StringBuffer("");
            for (int k = 0; k < tempList2.size(); k++) {
              Object[] obj2 = tempList2.get(k);
              if (k == 0) {
                empIds.append(obj2[0].toString());
              } else {
                empIds.append(",").append(obj2[0].toString());
              } 
            } 
            if (!"".equals(empIds.toString())) {
              String dateStr = format.format(new Date());
              dateStr = String.valueOf(dateStr) + " " + obj[1].toString().replace(" ", "") + ":00";
              SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
              Date begindate = bartDateFormat.parse(dateStr);
              doRemind(empIds.toString(), "系统", begindate);
            } 
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String updateBySql(String sql) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
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
  
  public String getUrlByCollectId(String collectId, String fromFlagFlow, String userId, String wfWorkId) {
    String url = "";
    try {
      List<Object[]> msList = null;
      MsManageBD msBD = new MsManageBD();
      if (fromFlagFlow == null || "fromDuBan".equals(fromFlagFlow)) {
        msList = msBD.getListByYourSQL("select p.wfWorkId,p.workStatus from com.js.oa.jsflow.po.WFWorkPO p where p.workRecordId=" + 
            collectId + " and p.workFileType='数据采集' and p.wfCurEmployeeId=" + userId);
        if (msList != null && msList.size() > 0) {
          Object[] obj = msList.get(0);
          if ("101".equals(obj[1].toString()))
            return "/jsoa/oacollect/error.jsp?errorMessage=dataissubmited"; 
          if (wfWorkId == null || "".equals(wfWorkId))
            wfWorkId = obj[0].toString(); 
        } 
      } 
      OaCollect oaCollect = loadOaTaskCollect(new Long(collectId));
      if (oaCollect != null && "0".equals(oaCollect.getIsMultiCollect().toString())) {
        String sql = "select p.id,p.collectId,p.empId from com.js.oa.oacollect.po.OaCollectEmp p where p.collectId=" + 
          collectId + " and p.empId=" + userId + " and p.empStatus=1";
        msList = msBD.getListByYourSQL(sql);
        if (msList != null && msList.size() >= 1)
          return "/jsoa/oacollect/error.jsp?errorMessage=dataisonecollect"; 
      } 
      if (oaCollect != null && oaCollect.getCollectEndTime() != null && !"".equals(oaCollect.getCollectEndTime()) && 
        1L == oaCollect.getCollectEnable().longValue() && !oaCollect.getCollectEndTime().before(Timestamp.valueOf(String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
        if (oaCollect.getCollectTProcess() != null && !"".equals(oaCollect.getCollectTProcess()) && !"-1".equals(oaCollect.getCollectTProcess())) {
          String[] proArr = oaCollect.getCollectTProcess().split("\\$");
          if (proArr != null && proArr.length >= 3) {
            url = "/jsoa/JsFlowAddAction.do?action=add&processId=" + proArr[0] + "&tableId=" + proArr[1] + "&processName=" + 
              URLEncoder.encode(proArr[2]) + "&processType=" + proArr[3] + "&remindField=&moduleId=null&collectId=" + collectId;
          } else {
            url = "/jsoa/oacollect/error.jsp?errorMessage=dataerror";
          } 
        } else {
          url = "/jsoa/collectSkipAction.do?action=add&fromFlag=task&formId=" + oaCollect.getCollectTPage() + 
            "&collectId=" + oaCollect.getCollectId();
        } 
      } else {
        url = "/jsoa/oacollect/error.jsp?errorMessage=datepass";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    url = String.valueOf(url) + "&wfWorkId=" + wfWorkId;
    return url;
  }
  
  public String getUrlByWfWorkId(String collectId, String fromFlagFlow, String userId, String wfWorkId) {
    String url = "";
    try {
      List<Object[]> msList = null;
      MsManageBD msBD = new MsManageBD();
      String sql = "select aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId,aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId from com.js.oa.jsflow.po.WFWorkPO aaa  where aaa.workStatus = 1 and aaa.wfCurEmployeeId =" + 





        
        userId + " and aaa.workListControl = 1 " + 
        " and aaa.workDelete = 0 and aaa.wfWorkId=" + wfWorkId + " order by aaa.wfWorkId desc";
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() > 0) {
        Object[] obj = msList.get(0);
        url = obj[16] + "&search=&from=dealwith&workTitle=&activityName=" + obj[18] + "&submitPersonId=" + obj[12] + "&submitPerson=" + obj[11] + 
          "&work=" + obj[10] + "&workType=" + obj[6] + "&activity=" + obj[7] + "&table=" + obj[8] + "&record=" + obj[9] + "&processName=" + obj[0] + 
          "&workStatus=1&submitTime=" + obj[17] + "&processId=" + obj[14] + "&stepCount=" + obj[15] + "&isStandForWork=" + obj[20] + "&standForUserId=" + obj[21] + 
          "&standForUserName=" + obj[22] + "&initActivity=" + obj[27] + "&initActivityName=" + obj[28] + "&submitPersonTime=" + obj[5] + "&tranType=" + obj[29] + 
          "&tranFromPersonId=" + obj[30] + "&processDeadlineDate=" + obj[31];
      } else {
        url = "/jsoa/oacollect/error.jsp?errorMessage=datepass";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    url = String.valueOf(url) + "&wfWorkId=" + wfWorkId;
    return url;
  }
  
  public String getUrlForZaiBan(String collectId, String recordId) {
    String url = "";
    try {
      OaCollect oaCollect = loadOaTaskCollect(new Long(collectId));
      if (oaCollect != null && oaCollect.getCollectEndTime() != null && !"".equals(oaCollect.getCollectEndTime()) && 
        1L == oaCollect.getCollectEnable().longValue() && !oaCollect.getCollectEndTime().before(Timestamp.valueOf(String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
        url = "/jsoa/collectSkipAction.do?action=update&fromFlag=collect&fromTabFlag=collectList&flag=1&formId=" + oaCollect.getCollectTPage() + "&recordId=" + recordId + 
          "&collectId=" + oaCollect.getCollectId();
      } else {
        url = "/jsoa/oacollect/error.jsp?errorMessage=datepass";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return url;
  }
  
  public Boolean addMenuListRight(List<HashMap> list) throws HibernateException {
    Boolean ret = null;
    DbOpt opt = null;
    try {
      if (list != null) {
        opt = new DbOpt();
        String sql = "";
        for (int i = 0; i < list.size(); i++) {
          HashMap subMap = list.get(i);
          sql = String.valueOf(sql) + "'" + subMap.get("rightCode") + "',";
        } 
        System.out.println("sql:select right_id from org_right where rightcode in (" + 
            
            sql.substring(0, sql.lastIndexOf(",")) + ")");
        String[] id = opt.executeQueryToStrArr1(
            "select right_id from org_right where rightcode in (" + 
            sql.substring(0, sql.lastIndexOf(",")) + ")");
        if (id != null && id.length > 0) {
          if (id.length == 4)
            for (int k = 0; k < list.size(); k++) {
              HashMap subMap = list.get(k);
              if ("删除".equals(subMap.get("rightName"))) {
                if ("mssqlserver".equals(DbOpt.dbtype)) {
                  sql = "insert into ORG_RIGHT values('" + 
                    subMap.get("rightName") + "','" + 
                    subMap.get("rightType") + "','" + 
                    subMap.get("rightClass") + 
                    "',1, '11111', '全部/本人/本组织及下属组织/本组织/自定义', '" + 
                    subMap.get("rightCode") + "', " + 
                    subMap.get("domainId") + ")";
                } else {
                  sql = "insert into ORG_RIGHT values(" + 
                    getMaxRightId(opt, (Statement)null) + ",'" + 
                    subMap.get("rightName") + "','" + 
                    subMap.get("rightType") + "','" + 
                    subMap.get("rightClass") + 
                    "',1, '11111', '全部/本人/本组织及下属组织/本组织/自定义', '" + 
                    subMap.get("rightCode") + "', " + 
                    subMap.get("domainId") + ")";
                } 
                opt.executeUpdate(sql);
              } 
            }  
          return Boolean.FALSE;
        } 
        for (int j = 0; j < list.size(); j++) {
          HashMap subMap = list.get(j);
          if ("mssqlserver".equals(DbOpt.dbtype)) {
            sql = "insert into ORG_RIGHT values('" + 
              subMap.get("rightName") + "','" + 
              subMap.get("rightType") + "','" + 
              subMap.get("rightClass") + 
              "'," + (
              !"新增".equals(subMap.get("rightName")
                .toString()) ? 1 : 0) + ", '" + (
              !"新增".equals(subMap.get("rightName")
                .toString()) ? "11111" : 
              "00000") + "', '" + (
              !"新增".equals(subMap.get("rightName")
                .toString()) ? 
              "全部/本人/本组织及下属组织/本组织/自定义" : "") + "', '" + 
              subMap.get("rightCode") + "', " + 
              subMap.get("domainId") + ")";
          } else {
            sql = "insert into ORG_RIGHT values(" + 
              getMaxRightId(opt, (Statement)null) + ",'" + 
              subMap.get("rightName") + "','" + 
              subMap.get("rightType") + "','" + 
              subMap.get("rightClass") + 
              "'," + (
              !"新增".equals(subMap.get("rightName")
                .toString()) ? 1 : 0) + ", '" + (
              !"新增".equals(subMap.get("rightName")
                .toString()) ? "11111" : 
              "00000") + "', '" + (
              !"新增".equals(subMap.get("rightName")
                .toString()) ? 
              "全部/本人/本组织及下属组织/本组织/自定义" : "") + "', '" + 
              subMap.get("rightCode") + "', " + 
              subMap.get("domainId") + ")";
          } 
          opt.executeUpdate(sql);
        } 
        opt.closeStatement();
        opt.closeConnection();
        ret = new Boolean(true);
      } 
    } catch (Exception ex) {
      if (opt != null)
        try {
          opt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
      ret = new Boolean(false);
    } 
    return ret;
  }
  
  private Long getMaxRightId(DbOpt opt, Statement stat) throws SQLException, Exception {
    if (opt != null) {
      String max = opt.executeQueryToStr("select max(right_id) from org_right");
      return new Long(Long.valueOf(max).longValue() + 1L);
    } 
    return null;
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListByYourSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public Map getOrgIdOrgNameByUserId(String userId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    ParameterGenerator p = new ParameterGenerator(1);
    begin();
    List<Object[]> list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(" select o.orgId,o.orgName from com.js.system.vo.organizationmanager.OrganizationVO o,com.js.system.vo.usermanager.EmployeeOrgVO eo  where o.orgId=eo.orgId and  eo.empId=" + 
          userId);
      list = query1.list();
      if (list != null && list.size() > 0) {
        Object[] ob = list.get(0);
        map.put("orgId", ob[0]);
        map.put("orgName", ob[1]);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public List getPageInfoList(String flag, String tableName) throws Exception {
    String sql = "SELECT j." + tableName + "_id,j." + tableName + "_owner,o.collect_title FROM " + tableName + " j,oa_collect o WHERE " + 
      " j." + tableName + "_relaByInde=o.collect_id AND " + tableName + "_relaByInde=" + flag;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    List<String[]> list = new ArrayList();
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] obj = new String[3];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        list.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return list;
  }
  
  public String[] getEmpByOrgOrGroup(String empId, String empName) {
    String[] returnValue = { "", "" };
    if (empId.contains("*") || empId.contains("@")) {
      String[] names = empName.split(",");
      int num = 0;
      String orgId = "";
      String groupId = "";
      while (empId.length() > 0) {
        String first = empId.substring(0, 1);
        String otherStr = empId.substring(1);
        String id = otherStr.substring(0, otherStr.indexOf(first));
        empId = otherStr.substring(otherStr.indexOf(first) + 1);
        if ("*".equals(first)) {
          orgId = String.valueOf(orgId) + id + ",";
        } else if ("@".equals(first)) {
          groupId = String.valueOf(groupId) + "," + id;
        } else {
          returnValue[0] = String.valueOf(returnValue[0]) + "$" + id + "$";
          returnValue[1] = String.valueOf(returnValue[1]) + names[num] + ",";
        } 
        num++;
      } 
      DataSourceBase base = new DataSourceBase();
      ResultSet rs = null;
      try {
        base.begin();
        if (orgId.length() > 0) {
          String[] orgIds = orgId.split(",");
          String orgWhere = "";
          for (int i = 0; i < orgIds.length; i++)
            orgWhere = String.valueOf(orgWhere) + "or org.orgIdString like '%$" + orgIds[i] + "$%' "; 
          String orgSql = "SELECT o.emp_id,e.EMPNAME FROM org_organization_user o JOIN org_employee e ON o.EMP_ID=e.EMP_ID JOIN org_organization org ON o.ORG_ID=org.org_id WHERE USERISACTIVE=1 AND USERISDELETED=0 AND USERACCOUNTS IS NOT NULL AND (" + 
            
            orgWhere.substring(2) + ")";
          rs = base.executeQuery(orgSql);
          while (rs.next()) {
            returnValue[0] = String.valueOf(returnValue[0]) + "$" + rs.getString(1) + "$";
            returnValue[1] = String.valueOf(returnValue[1]) + rs.getString(2) + ",";
          } 
        } 
        if (groupId.length() > 0) {
          String groupSql = "SELECT g.emp_id,e.EMPNAME FROM org_user_group g,org_employee e WHERE g.EMP_ID=e.EMP_ID  and USERISACTIVE=1 and USERISDELETED=0 and USERACCOUNTS is not null AND group_id IN (" + 
            
            groupId.substring(1) + ")";
          rs = base.executeQuery(groupSql);
          while (rs.next()) {
            returnValue[0] = String.valueOf(returnValue[0]) + "$" + rs.getString(1) + "$";
            returnValue[1] = String.valueOf(returnValue[1]) + rs.getString(2) + ",";
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } else {
      returnValue[0] = empId;
      returnValue[1] = empName;
    } 
    return returnValue;
  }
  
  public String[] getOldEmp(String id) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String[] re = { "", "" };
    try {
      base.begin();
      String sql = "SELECT emp_id,emp_name FROM oa_collect_emp WHERE collect_id=" + id;
      rs = base.executeQuery(sql);
      while (rs.next()) {
        re[0] = String.valueOf(re[0]) + "$" + rs.getString(1) + "$";
        re[1] = String.valueOf(re[1]) + rs.getString(2) + ",";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return re;
  }
}
