package com.js.oa.weixin.common.util;

import com.js.oa.userdb.util.DbOpt;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;

public class WorkflowForWeiXinUtil {
  public static String getUrlByWorkId(String workId) {
    String url = "/WorkFlowProcForWeiXinAction.do?flowpara=1&search=&from=dealwith&workTitle=&activityName=";
    String urlback = "/WorkFlowReSubmitForWeiXinAction.do?pp=1&moduleId=1&remindField=null&resubmit=1";
    DbOpt db = new DbOpt();
    String sql = "select workcurstep,wf_submitemployee_id,worksubmitperson,worktype,workActivity,worktable_id,workrecord_id,workfiletype,workstatus,worksubmittime,workprocess_id,workstepcount,isStandForWork,standForUserId,standForUserName,initactivity,initactivityname,workcreatedate,trantype,tranFromPersonId,processDeadlineDate,WORKTITLE,WORKCANCELREASON,SUBMITORG,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId  from jsf_work where wf_work_id = " + 




      
      workId;
    try {
      String[][] result = db.executeQueryToStrArr2(sql, 30);
      if (result != null && result.length > 0) {
        String workcurstep = result[0][0];
        String wf_submitemployee_id = result[0][1];
        String worksubmitperson = result[0][2];
        String worktype = result[0][3];
        String workActivity = result[0][4];
        String worktable_id = result[0][5];
        String workrecord_id = result[0][6];
        String workfiletype = result[0][7];
        String workstatus = result[0][8];
        String worksubmittime = result[0][9];
        String workprocess_id = result[0][10];
        String workstepcount = result[0][11];
        String isStandForWork = result[0][12];
        String standForUserId = result[0][13];
        String standForUserName = result[0][14];
        String initactivity = result[0][15];
        String initactivityname = result[0][16];
        String workcreatedate = result[0][17];
        String trantype = result[0][18];
        String tranFromPersonId = result[0][19];
        String processDeadlineDate = result[0][20];
        String worktitle = result[0][21];
        String workcancelreason = result[0][22];
        String submitorg = result[0][23];
        String isSubProcWork = result[0][24];
        String pareProcActiId = result[0][25];
        String pareStepCount = result[0][26];
        String pareTableId = result[0][27];
        String pareRecordId = result[0][28];
        String pareProcNextActiId = result[0][29];
        url = String.valueOf(url) + encodeStr(encodeStr(encodeStr(workcurstep)));
        url = String.valueOf(url) + "&submitPersonId=" + encodeStr(encodeStr(encodeStr(wf_submitemployee_id)));
        url = String.valueOf(url) + "&submitPerson=" + encodeStr(encodeStr(encodeStr(worksubmitperson)));
        url = String.valueOf(url) + "&work=" + encodeStr(encodeStr(encodeStr(workId)));
        url = String.valueOf(url) + "&workType=" + encodeStr(encodeStr(encodeStr(worktype)));
        url = String.valueOf(url) + "&activity=" + encodeStr(encodeStr(encodeStr(workActivity)));
        url = String.valueOf(url) + "&table=" + encodeStr(encodeStr(encodeStr(worktable_id)));
        url = String.valueOf(url) + "&record=" + encodeStr(encodeStr(encodeStr(workrecord_id)));
        url = String.valueOf(url) + "&processName=" + encodeStr(encodeStr(encodeStr(workfiletype)));
        url = String.valueOf(url) + "&workStatus=" + encodeStr(encodeStr(encodeStr(workstatus)));
        url = String.valueOf(url) + "&submitTime=" + encodeStr(encodeStr(encodeStr(worksubmittime)));
        url = String.valueOf(url) + "&processId=" + encodeStr(encodeStr(encodeStr(workprocess_id)));
        url = String.valueOf(url) + "&stepCount=" + encodeStr(encodeStr(encodeStr(workstepcount)));
        url = String.valueOf(url) + "&isStandForWork=" + encodeStr(encodeStr(encodeStr(isStandForWork)));
        url = String.valueOf(url) + "&standForUserId=" + encodeStr(encodeStr(encodeStr(standForUserId)));
        url = String.valueOf(url) + "&standForUserName=" + encodeStr(encodeStr(encodeStr(standForUserName)));
        url = String.valueOf(url) + "&initActivity=" + encodeStr(encodeStr(encodeStr(initactivity)));
        url = String.valueOf(url) + "&initActivityName=" + encodeStr(encodeStr(encodeStr(initactivityname)));
        url = String.valueOf(url) + "&submitPersonTime=" + encodeStr(encodeStr(encodeStr(workcreatedate)));
        url = String.valueOf(url) + "&tranType=" + encodeStr(encodeStr(encodeStr(trantype)));
        url = String.valueOf(url) + "&tranFromPersonId=" + encodeStr(encodeStr(encodeStr(tranFromPersonId)));
        url = String.valueOf(url) + "&processDeadlineDate=" + encodeStr(encodeStr(encodeStr(processDeadlineDate)));
        if ("-999".equals(workstatus)) {
          urlback = String.valueOf(urlback) + "&isSubProcWork=" + encodeStr(encodeStr(encodeStr(isSubProcWork)));
          urlback = String.valueOf(urlback) + "&pareProcActiId=" + encodeStr(encodeStr(encodeStr(pareProcActiId)));
          urlback = String.valueOf(urlback) + "&pareStepCount=" + encodeStr(encodeStr(encodeStr(pareStepCount)));
          urlback = String.valueOf(urlback) + "&pareTableId=" + encodeStr(encodeStr(encodeStr(pareTableId)));
          urlback = String.valueOf(urlback) + "&pareRecordId=" + encodeStr(encodeStr(encodeStr(pareRecordId)));
          urlback = String.valueOf(urlback) + "&work=" + encodeStr(encodeStr(encodeStr(workId)));
          urlback = String.valueOf(urlback) + "&processType=" + encodeStr(encodeStr(encodeStr(worktype)));
          urlback = String.valueOf(urlback) + "&workType=" + encodeStr(encodeStr(encodeStr(worktype)));
          urlback = String.valueOf(urlback) + "&tableId=" + encodeStr(encodeStr(encodeStr(worktable_id)));
          urlback = String.valueOf(urlback) + "&table=" + encodeStr(encodeStr(encodeStr(worktable_id)));
          urlback = String.valueOf(urlback) + "&record=" + encodeStr(encodeStr(encodeStr(workrecord_id)));
          urlback = String.valueOf(urlback) + "&recordId=" + encodeStr(encodeStr(encodeStr(workrecord_id)));
          urlback = String.valueOf(urlback) + "&processId=" + encodeStr(encodeStr(encodeStr(workprocess_id)));
          urlback = String.valueOf(urlback) + "&processName=" + workfiletype;
          urlback = String.valueOf(urlback) + "&activity=" + encodeStr(encodeStr(encodeStr(workActivity)));
          urlback = String.valueOf(urlback) + "&activityName=" + encodeStr(encodeStr(encodeStr(workcurstep)));
          urlback = String.valueOf(urlback) + "&workFileType=" + encodeStr(encodeStr(encodeStr(workfiletype)));
          urlback = String.valueOf(urlback) + "&workStatus=" + encodeStr(encodeStr(encodeStr(workstatus)));
          urlback = String.valueOf(urlback) + "&submitPerson=" + encodeStr(encodeStr(encodeStr(worksubmitperson)));
          urlback = String.valueOf(urlback) + "&submitTime=" + encodeStr(encodeStr(encodeStr(worksubmittime)));
          urlback = String.valueOf(urlback) + "&editId=" + encodeStr(encodeStr(encodeStr(workId)));
          url = urlback;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return url;
  }
  
  public static String decordStr(Object o) {
    String string = "";
    if (o != null && !"".equals(o.toString()))
      try {
        string = URLDecoder.decode(o.toString(), "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    return string;
  }
  
  public static String encodeStr(Object o) {
    String string = "";
    if (o != null && !"".equals(o.toString()))
      try {
        string = URLEncoder.encode(o.toString(), "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    return string;
  }
}
