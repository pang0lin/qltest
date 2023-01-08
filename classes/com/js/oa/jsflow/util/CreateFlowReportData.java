package com.js.oa.jsflow.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateFlowReportData {
  SimpleDateFormat ymdhmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  
  String dataBaseType = SystemCommon.getDatabaseType();
  
  public boolean flowReport(String filePath, String[] exportCond) {
    boolean flag = false;
    long beginLong = System.currentTimeMillis();
    try {
      List<String[]> dealWithFlow = getDealWithFlowInfo(exportCond);
      Map<String, String> userMap = new HashMap<String, String>();
      if (dealWithFlow.size() > 0) {
        userMap = getEmpNameByEmpId();
        String nodeBeginTime = "";
        List<Map<String, Object>> contList = new ArrayList<Map<String, Object>>();
        int maxPointNum = 0;
        for (int c = 0; c < dealWithFlow.size(); c++) {
          String currentActivityId = "";
          String lastActivityId = "";
          String[] flowInfo = dealWithFlow.get(c);
          Date flowBeginDate = this.ymdhmFormat.parse(flowInfo[2].substring(0, 16));
          Date flowEndDate = ("".equals(flowInfo[3]) || "null".equalsIgnoreCase(flowInfo[3])) ? null : this.ymdhmFormat.parse(flowInfo[3].substring(0, 16));
          Map<String, Object> listMap = new HashMap<String, Object>();
          String flowTitle = flowInfo[0];
          if ("-2".equals(flowInfo[9])) {
            flowTitle = flowInfo[8];
          } else {
            flowTitle = String.valueOf(flowInfo[1]) + flowInfo[8].substring(1).replace("已办理完毕！", "").replace("正在办理中！", "");
            if (flowTitle.contains("已被") && flowTitle.endsWith("退回！"))
              flowTitle = flowTitle.substring(0, flowTitle.lastIndexOf("已被")); 
          } 
          listMap.put("flowTitle", flowTitle);
          listMap.put("submitName", flowInfo[1]);
          listMap.put("submitTime", this.ymdhmFormat.format(flowBeginDate));
          listMap.put("endTime", (flowEndDate == null) ? ("-1".equals(flowInfo[9]) ? "(退回)" : "(在办)") : this.ymdhmFormat.format(flowEndDate));
          String[] dealWithTime = getTimeShow(flowBeginDate, flowEndDate);
          listMap.put("zongTime", dealWithTime[0]);
          if (flowInfo[7] == null || "null".equalsIgnoreCase(flowInfo[7]) || "".equals(flowInfo[7])) {
            listMap.put("biaoTime", "");
            listMap.put("chaeTime", "");
          } else {
            Date processDeadlineDate = this.ymdhmFormat.parse(flowInfo[7].substring(0, 16));
            Long dealLineLong = Long.valueOf((processDeadlineDate.getTime() - flowBeginDate.getTime()) / 60000L);
            listMap.put("biaoTime", getHourShow(dealLineLong));
            if (!"0".equals(dealWithTime[1])) {
              listMap.put("chaeTime", getHourShow(Long.valueOf(dealLineLong.longValue() - Long.valueOf(dealWithTime[1]).longValue())));
            } else {
              listMap.put("chaeTime", "");
            } 
          } 
          List<String[]> activityList = getActivityInfo(flowInfo[4], flowInfo[5], flowInfo[6]);
          Boolean exportFlag = Boolean.valueOf(true);
          if (!"".equals(exportCond[5]) || !"".equals(exportCond[6])) {
            exportFlag = Boolean.valueOf(false);
            for (int i = 0; i < activityList.size(); i++) {
              String[] activityInfo = activityList.get(i);
              if (!"".equals(exportCond[5]) && !"".equals(exportCond[6])) {
                if (exportCond[5].contains("*" + activityInfo[10] + "*") && exportCond[6].contains("$" + activityInfo[1] + "$"))
                  exportFlag = Boolean.valueOf(true); 
              } else if (!"".equals(exportCond[5])) {
                if (exportCond[5].contains("*" + activityInfo[10] + "*"))
                  exportFlag = Boolean.valueOf(true); 
              } else if (exportCond[6].contains("$" + activityInfo[1] + "$")) {
                exportFlag = Boolean.valueOf(true);
              } 
            } 
          } 
          if (exportFlag.booleanValue()) {
            List<Map<String, String>> pointList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < activityList.size(); i++) {
              String[] activityInfo = activityList.get(i);
              currentActivityId = activityInfo[5];
              if (i == 0)
                nodeBeginTime = activityInfo[2].substring(0, 16); 
              if (!lastActivityId.equals(currentActivityId)) {
                lastActivityId = currentActivityId;
                nodeBeginTime = activityInfo[2].substring(0, 16);
              } 
              Map<String, String> pointMap = new HashMap<String, String>();
              pointMap.put("pointName", activityInfo[0]);
              String dealName = "";
              if (activityInfo[7] == null || "".equals(activityInfo[7]) || "null".equalsIgnoreCase(activityInfo[7])) {
                dealName = (userMap.get(activityInfo[1]) == null) ? "" : userMap.get(activityInfo[1]);
              } else {
                dealName = String.valueOf((userMap.get(activityInfo[1]) == null) ? "" : userMap.get(activityInfo[1])) + 
                  "(转办人：" + ((userMap.get(activityInfo[8]) == null) ? "" : userMap.get(activityInfo[8])) + ")";
              } 
              if (!"0".equals(activityInfo[9]) && !"-1".equals(activityInfo[9]))
                dealName = String.valueOf(dealName) + "(办理人:" + ((userMap.get(activityInfo[9]) == null) ? "" : userMap.get(activityInfo[9])) + ")"; 
              if (activityInfo[11] != null && activityInfo[11].length() > 20) {
                String backInfo = activityInfo[11];
                if (backInfo != null && backInfo.indexOf("退回.<br") > 0) {
                  backInfo = backInfo.substring(0, backInfo.indexOf("退回.<br"));
                  if (backInfo.lastIndexOf("被") > 0) {
                    backInfo = backInfo.substring(backInfo.lastIndexOf("被") + 1);
                    dealName = String.valueOf(dealName) + "(退回人:" + backInfo + ")";
                  } 
                } 
              } 
              pointMap.put("dealName", dealName);
              pointMap.put("receiveTime", activityInfo[2].substring(0, 16));
              pointMap.put("dealTime", ("".equals(activityInfo[3]) || "null".equalsIgnoreCase(activityInfo[3])) ? "(未处理)" : activityInfo[3].substring(0, 16));
              String[] dealWithActTime = getTimeShow(
                  this.ymdhmFormat.parse(activityInfo[2].substring(0, 16)), (
                  "".equals(activityInfo[3]) || "null".equalsIgnoreCase(activityInfo[3])) ? new Date() : this.ymdhmFormat.parse(activityInfo[3].substring(0, 16)));
              pointMap.put("pointAllTime", dealWithActTime[0]);
              if (activityInfo[6] == null || "-1".equals(activityInfo[6]) || "".equals(activityInfo[6]) || "null".equalsIgnoreCase(activityInfo[6])) {
                pointMap.put("pointDoTime", "");
                pointMap.put("pointChaTime", "");
              } else {
                pointMap.put("pointDoTime", getHourShow(Long.valueOf(Long.valueOf(activityInfo[6]).longValue() / 60L)));
                if (activityInfo[7] == null || "".equals(activityInfo[7]) || "null".equalsIgnoreCase(activityInfo[7])) {
                  pointMap.put("pointChaTime", getHourShow(Long.valueOf(Long.valueOf(activityInfo[6]).longValue() / 60L - Long.valueOf(dealWithActTime[1]).longValue())));
                } else {
                  pointMap.put("pointChaTime", getHourShow(
                        Long.valueOf(Long.valueOf(activityInfo[6]).longValue() / 60L - Long.valueOf(
                            getTimeShow(
                              this.ymdhmFormat.parse(nodeBeginTime), (
                              "".equals(activityInfo[3]) || "null".equalsIgnoreCase(activityInfo[3])) ? new Date() : this.ymdhmFormat.parse(activityInfo[3].substring(0, 16)))[1]).longValue())));
                } 
              } 
              pointList.add(pointMap);
            } 
            listMap.put("pointMap", pointList);
            if (maxPointNum < activityList.size())
              maxPointNum = activityList.size(); 
            activityList.clear();
            contList.add(listMap);
          } 
        } 
        if (maxPointNum == 0)
          maxPointNum = 1; 
        CreateFlowReportExcel.creatExcel(filePath, contList, maxPointNum, "（" + exportCond[0] + " 到 " + exportCond[1] + "）");
        flag = true;
      } else {
        System.out.println("没有符合条件的流程！");
      } 
      userMap.clear();
      dealWithFlow.clear();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public List<String[]> getDealWithFlowInfo(String[] exportCond) {
    String sql = "SELECT DISTINCT w.WORKFILETYPE,w.worksubmitperson,w.worksubmittime,w.WORKDONEWITHDATE,w.workprocess_id,w.worktable_id,w.WORKRECORD_ID,w.processDeadlineDate,w.worktitle,w.workstatus FROM jsf_work w join org_organization_user o on w.WF_SUBMITEMPLOYEE_ID=o.emp_id WHERE 1=1 and w.worktable_id<>-1 ";
    if (!"".equals(exportCond[7])) {
      sql = String.valueOf(sql) + " and w.workstatus=" + exportCond[7] + " ";
    } else {
      sql = String.valueOf(sql) + " and (w.workstatus=1 or w.workstatus=100 or w.workstatus=-1) ";
    } 
    if (!"".equals(exportCond[2]))
      sql = String.valueOf(sql) + " and w.workprocess_id=" + exportCond[2] + " "; 
    if (!"".equals(exportCond[4]))
      if (this.dataBaseType.contains("mysql")) {
        sql = String.valueOf(sql) + " and '" + exportCond[4] + "' LIKE CONCAT('%$',w.WF_SUBMITEMPLOYEE_ID,'$%') ";
      } else {
        sql = String.valueOf(sql) + " and '" + exportCond[4] + "' LIKE '%$'||w.WF_SUBMITEMPLOYEE_ID||'$%' ";
      }  
    if (!"".equals(exportCond[3]))
      if (this.dataBaseType.contains("mysql")) {
        sql = String.valueOf(sql) + " and '" + exportCond[3] + "' LIKE CONCAT('%*',o.org_id,'*%') ";
      } else {
        sql = String.valueOf(sql) + " and '" + exportCond[3] + "' LIKE '%*'||o.org_id||'*%' ";
      }  
    if (this.dataBaseType.contains("mysql")) {
      sql = String.valueOf(sql) + " AND w.worksubmittime BETWEEN '" + exportCond[0] + " 00:00:00' AND '" + exportCond[1] + " 23:59:59' ";
    } else {
      sql = String.valueOf(sql) + " AND w.worksubmittime BETWEEN to_date('" + exportCond[0] + " 00:00:00','yyyy-MM-dd hh24:mi:ss') " + 
        "AND to_date('" + exportCond[1] + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
    } 
    sql = String.valueOf(sql) + " GROUP BY w.WORKFILETYPE,w.worksubmitperson,w.worksubmittime,w.WORKDONEWITHDATE,w.workprocess_id,w.worktable_id,w.WORKRECORD_ID,w.processDeadlineDate,w.worktitle,w.workstatus ";
    sql = String.valueOf(sql) + " order by w.worksubmittime ";
    return (new DataSourceUtil()).getListQuery(sql);
  }
  
  public List<String[]> getActivityInfo(String processId, String tableId, String recordId) {
    String sql = "select * from (SELECT DISTINCT w.initactivityname,c.dealwithemployee_id,w.workcreatedate,w.dealwithTime,c.dealwithemployeecomment,w.initactivity,w.workDeadLine,w.tranType,w.tranFromPersonId,c.STANDFORUSERID,o.org_id,w.dealtips FROM jsf_work w JOIN jsf_dealwith d ON d.databasetable_id=w.worktable_id AND d.DATABASERECORD_ID=w.WORKRECORD_ID AND d.activity_id=w.initactivity JOIN jsf_dealwithcomment c ON c.wf_dealwith_id=d.wf_dealwith_id join org_organization_user o on c.dealwithemployee_id=o.emp_id WHERE w.workprocess_id=" + 


      
      processId + " AND (w.workstatus=101 or w.workstatus=-101)  AND w.worktable_id=" + 
      tableId + " AND w.WORKRECORD_ID=" + recordId + " AND (d.wf_dealwith_id=c.wf_dealwith_id) and w.wf_curemployee_id=c.dealwithemployee_id ";
    sql = String.valueOf(sql) + " UNION SELECT DISTINCT w.initactivityname,w.wf_curemployee_id,w.workcreatedate,w.dealwithTime,'',w.initactivity,w.workDeadLine,w.tranType,w.tranFromPersonId,-1,o.org_id,w.dealtips FROM jsf_work w join org_organization_user o on w.wf_curemployee_id=o.emp_id WHERE w.workprocess_id=" + 

      
      processId + " AND w.workstatus=0 AND w.worktable_id=" + tableId + 
      " AND w.WORKRECORD_ID=" + recordId + " AND w.ISSTANDFORWORK=0 ) activity order by workcreatedate";
    return (new DataSourceUtil()).getListQuery(sql);
  }
  
  public String[] getTimeShow(Date flowBeginDate, Date flowEndDate) {
    if (flowEndDate == null)
      return new String[] { "", "0" }; 
    Long timeLong = Long.valueOf(flowEndDate.getTime() - flowBeginDate.getTime());
    String dealWithTime = "";
    if (timeLong.longValue() % 3600000L == 0L) {
      dealWithTime = String.valueOf(timeLong.longValue() / 3600000L) + "小时";
    } else if (timeLong.longValue() / 3600000L == 0L) {
      dealWithTime = String.valueOf(timeLong.longValue() % 3600000L / 60000L) + "分";
    } else {
      dealWithTime = String.valueOf(timeLong.longValue() / 3600000L) + "小时" + (
        timeLong.longValue() % 3600000L / 60000L) + "分";
    } 
    return new String[] { dealWithTime, (new StringBuilder(String.valueOf(timeLong.longValue() / 60000L))).toString() };
  }
  
  public String getHourShow(Long timeLong) {
    String dealWithTime = "";
    if (timeLong.longValue() < 0L) {
      timeLong = Long.valueOf(timeLong.longValue() * -1L);
      dealWithTime = "-";
    } 
    if (timeLong.longValue() % 60L == 0L) {
      dealWithTime = String.valueOf(dealWithTime) + (timeLong.longValue() / 60L) + "小时";
    } else if (timeLong.longValue() / 60L == 0L) {
      dealWithTime = String.valueOf(dealWithTime) + (timeLong.longValue() % 60L) + "分";
    } else {
      dealWithTime = String.valueOf(dealWithTime) + (timeLong.longValue() / 60L) + "小时" + (timeLong.longValue() % 60L) + "分";
    } 
    return dealWithTime;
  }
  
  public Map<String, String> getEmpNameByEmpId() {
    Map<String, String> userMap = new HashMap<String, String>();
    String sql = "SELECT emp_id,empName FROM org_employee WHERE emp_id>0 AND userisActive=1 AND userisDeleted=0";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        userMap.put(rs.getString(1), rs.getString(2)); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return userMap;
  }
}
