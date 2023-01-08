package com.js.oa.jsflow.util;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.message.action.ModelSendMsg;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProcessSubmit {
  public long newProcSubmit(WorkVO workVO, String[] toUser, String moduleId, String[] para) {
    String databaseType = SystemCommon.getDatabaseType();
    int workType = workVO.getWorkType();
    String submitPerson = workVO.getSubmitPerson();
    Long submitPersonId = workVO.getSubmitEmployeeId();
    String remindValue = workVO.getRemindValue();
    if (remindValue == null || remindValue.toUpperCase().equals("NULL")) {
      remindValue = "";
      workVO.setRemindValue("");
    } 
    String fileType = workVO.getFileType();
    String selfTitle = "您的" + remindValue + fileType + "正在办理中！";
    String toUserTitle = null;
    if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
      toUserTitle = String.valueOf(remindValue) + fileType + "等待您处理！";
    } else {
      toUserTitle = String.valueOf(submitPerson) + "的" + remindValue + fileType + "等待您处理！";
    } 
    if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals("")) {
      selfTitle = workVO.getDocTitle();
      toUserTitle = workVO.getDocTitle();
      if ("shandongguotou".equals(SystemCommon.getCustomerName()))
        toUserTitle = String.valueOf(toUserTitle) + fileType + "等待您处理！"; 
    } 
    if (selfTitle.length() > 100)
      selfTitle = selfTitle.substring(0, 99); 
    if (toUserTitle.length() > 100)
      toUserTitle = toUserTitle.substring(0, 99); 
    Long processId = workVO.getProcessId();
    Long tableId = workVO.getTableId();
    Long recordId = workVO.getRecordId();
    String selfMainLinkFile = workVO.getSelfMainLinkFile();
    String toMainLinkFile = workVO.getToMainLinkFile();
    String submitOrg = workVO.getSubmitOrg();
    Date now = new Date();
    WorkFlowButtonBD wfbd = new WorkFlowButtonBD();
    String submitEmployeeOrgId = wfbd.getUserOrgId(submitPersonId.toString());
    String submitPersonOrgIdString = submitEmployeeOrgId;
    String processDeadlineDateStr = "null";
    ProcessBD processBD = new ProcessBD();
    String[] processDeadline = processBD.getProcessDeadline(processId.toString());
    if (processDeadline != null)
      if (!"0".equals(processDeadline[0])) {
        long processDeadlineTimeTemp = 0L;
        if ("2".equals(processDeadline[1])) {
          processDeadlineTimeTemp = Integer.parseInt(processDeadline[0]) * 86400L;
        } else if ("1".equals(processDeadline[1])) {
          processDeadlineTimeTemp = Integer.parseInt(processDeadline[0]) * 3600L;
        } else {
          processDeadlineTimeTemp = Integer.parseInt(processDeadline[0]) * 60L;
        } 
        Date processDeadlineDate = FlowDeadline.getDeadline(now, processDeadlineTimeTemp);
        if (databaseType.indexOf("mysql") >= 0) {
          processDeadlineDateStr = "'" + processDeadlineDate.toLocaleString() + "'";
        } else {
          processDeadlineDateStr = "JSDB.FN_STRTODATE('" + processDeadlineDate.toLocaleString() + "','L')";
        } 
      }  
    workVO.setProcessDeadlineDate(processDeadlineDateStr);
    String fieldString = "wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, isStandForWork, standForUserId, standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,DOMAIN_ID,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,activityDelaySend";
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
    long surveillanceId = 0L;
    if (workType != 0) {
      WorkFlowBD workFlowBD = new WorkFlowBD();
      WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
      String res = workFlowBD.copyProcInfo(processId.toString(), submitPersonId.toString(), 
          tableId.toString(), recordId.toString());
      if (!"0".equals(res))
        return 0L; 
      List<MessagesVO> msgList = new ArrayList();
      int userType = workVO.getUserType();
      if (toUser == null || toUser.length == 0 || toUser[0] == null || toUser[0].equals(""))
        return -1000L; 
      String delayTime = workFlowBD.getActivityDelayTime(workVO.getActivity().toString(), processId.toString(), recordId.toString(), tableId.toString());
      if ("null".equalsIgnoreCase(delayTime))
        delayTime = ""; 
      if (!"".equals(delayTime)) {
        SimpleDateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
          Date d = timeFormater.parse(delayTime);
          if (d.getTime() >= now.getTime())
            now = d; 
        } catch (ParseException e) {
          System.out.println("格式化延迟时间出错，使用当前时间");
          delayTime = "";
        } 
      } 
      String deadLineTime = "-1";
      String pressMotionTime = "-1";
      Date workDeadlineDate = now;
      Date workDeadlinePressDate = now;
      if (workVO.getPressType() == 2) {
        String[] str = workFlowBD.getPress(workVO.getActivity().toString(), tableId.toString(), recordId.toString(), moduleId);
        deadLineTime = str[0];
        pressMotionTime = str[1];
      } else {
        deadLineTime = workVO.getDeadLine();
        pressMotionTime = workVO.getPressTime();
      } 
      if (!"-1".equals(deadLineTime)) {
        workDeadlineDate = FlowDeadline.getDeadline(now, Long.parseLong(deadLineTime));
        workDeadlinePressDate = new Date(workDeadlineDate.getTime() - Long.parseLong(pressMotionTime) * 1000L);
      } 
      ArrayList<String> valueList = new ArrayList();
      if (workVO.getAllowStandFor() == 1) {
        List<String[]> toUserList = workFlowCommonBD.getStandForUserByProcessAndOrg(toUser, processId.toString(), submitPersonOrgIdString);
        String[] allowStandForUser = (String[])null;
        String tmp = "";
        for (int i = toUserList.size() - 1; i >= 0; i--) {
          allowStandForUser = toUserList.get(i);
          String tmpSql = "";
          if (databaseType.indexOf("mysql") >= 0) {
            tmp = String.valueOf(allowStandForUser[0]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
              toUserTitle + "', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
              ", '" + submitPerson + "', '" + now.toLocaleString() + "', " + 
              submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
              ", " + pressMotionTime + ", '" + now.toLocaleString() + "', " + 
              "1, 0, 0, 1, 0, 0, '', " + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + workVO.getEmergence() + 
              "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
          } else {
            tmp = String.valueOf(allowStandForUser[0]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
              toUserTitle + "', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
              ", '" + submitPerson + "', JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
              submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
              ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
              "1, 0, 0, 1, 0, 0, '', " + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + workVO.getEmergence() + 
              "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
          } 
          MessagesVO msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(submitPersonId.longValue());
          msgVO.setMessage_send_UserName(submitPerson);
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(toUserTitle);
          msgVO.setMessage_toUserId(Long.parseLong(allowStandForUser[0]));
          msgVO.setMessage_type("jsflow");
          msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
          msgList.add(msgVO);
          valueList.add(tmp);
          if (!allowStandForUser[2].equals("")) {
            if (databaseType.indexOf("mysql") >= 0) {
              tmp = String.valueOf(allowStandForUser[2]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
                toUserTitle + "（代" + allowStandForUser[1] + "办理）', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
                ", '" + submitPerson + "', '" + now.toLocaleString() + "', " + 
                submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
                ", " + pressMotionTime + ", '" + now.toLocaleString() + "', " + 
                "1, 0, 0, 1, 1, " + allowStandForUser[0] + ", '" + allowStandForUser[1] + "'," + 
                workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + 
                workVO.getEmergence() + "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
            } else {
              tmp = String.valueOf(allowStandForUser[2]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
                toUserTitle + "（代" + allowStandForUser[1] + "办理）', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
                ", '" + submitPerson + "', JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
                submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
                ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
                "1, 0, 0, 1, 1, " + allowStandForUser[0] + ", '" + allowStandForUser[1] + "'," + 
                workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + 
                workVO.getEmergence() + "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
            } 
            valueList.add(tmp);
            msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(submitPersonId.longValue());
            msgVO.setMessage_send_UserName(submitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(String.valueOf(toUserTitle) + "（代" + allowStandForUser[1] + "办理）'");
            msgVO.setMessage_toUserId(Long.parseLong(allowStandForUser[2]));
            msgVO.setMessage_type("jsflow");
            msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
            msgList.add(msgVO);
          } 
        } 
      } else {
        String tmp = "";
        String insertStr = "";
        for (int i = toUser.length - 1; i >= 0; i--) {
          if (insertStr.indexOf(String.valueOf(toUser[i]) + ",") < 0) {
            insertStr = String.valueOf(insertStr) + toUser[i] + ",";
            if (databaseType.indexOf("mysql") >= 0) {
              tmp = String.valueOf(toUser[i]) + ", 0 , '" + fileType + "', " + 
                "'" + workVO.getCurStep() + "','" + toUserTitle + "'," + 
                "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
                "'" + now.toLocaleString() + "'," + submitPersonId + 
                ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
                ", " + pressMotionTime + ", '" + now.toLocaleString() + "'," + 
                "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + 
                workVO.getDomainId() + ",'" + workVO.getEmergence() + "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
            } else {
              tmp = String.valueOf(toUser[i]) + ", 0 , '" + fileType + "', " + 
                "'" + workVO.getCurStep() + "','" + toUserTitle + "'," + 
                "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
                "JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L')," + submitPersonId + 
                ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
                ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L')," + 
                "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + 
                workVO.getDomainId() + ",'" + workVO.getEmergence() + "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
            } 
            valueList.add(tmp);
            MessagesVO msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(submitPersonId.longValue());
            msgVO.setMessage_send_UserName(submitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(toUserTitle);
            msgVO.setMessage_toUserId(Long.parseLong(toUser[i]));
            msgVO.setMessage_type("jsflow");
            msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
            msgList.add(msgVO);
          } 
        } 
      } 
      surveillanceId = workFlowBD.setSurveillance(workVO);
      if (workVO.isNeedPassRound()) {
        int passRoundUserType = workVO.getPassRoundUserType();
        String[] passRoundUser = { "" };
        if (workVO.getPassRoundUser() != null && (workVO.getPassRoundUser()).length > 0)
          passRoundUser = workVO.getPassRoundUser(); 
        String passRoundTitle = String.valueOf(submitPerson) + "的" + remindValue + fileType + "等待您的审阅！";
        if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals("")) {
          passRoundTitle = workVO.getDocTitle();
          if ("shandongguotou".equals(SystemCommon.getCustomerName()))
            passRoundTitle = String.valueOf(passRoundTitle) + fileType + "等待您的审阅！"; 
        } 
        if (passRoundTitle.length() > 100)
          passRoundTitle = passRoundTitle.substring(0, 99); 
        boolean flag = true;
        String insertStr = "";
        for (int i = 0; i < passRoundUser.length; i++) {
          if (insertStr.indexOf(String.valueOf(passRoundUser[i]) + ",") < 0) {
            insertStr = String.valueOf(insertStr) + passRoundUser[i] + ",";
            flag = true;
            if (passRoundUser[i] != null && !passRoundUser[i].equals("")) {
              for (int j = 0; j < toUser.length; j++) {
                if (passRoundUser[i].equals(toUser[j])) {
                  flag = false;
                  break;
                } 
              } 
              if (flag) {
                String tmp = "";
                if (databaseType.indexOf("mysql") >= 0) {
                  tmp = String.valueOf(passRoundUser[i]) + ", 2 , '" + fileType + "', " + 
                    "'" + workVO.getCurStep() + "','" + passRoundTitle + "'," + 
                    "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
                    "'" + now.toLocaleString() + "'," + submitPersonId + 
                    ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
                    ", " + pressMotionTime + ", '" + now.toLocaleString() + "'," + 
                    "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + 
                    workVO.getDomainId() + ",'" + workVO.getEmergence() + "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
                } else {
                  tmp = String.valueOf(passRoundUser[i]) + ", 2 , '" + fileType + "', " + 
                    "'" + workVO.getCurStep() + "','" + passRoundTitle + "'," + 
                    "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
                    "JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L')," + submitPersonId + 
                    ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
                    ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L')," + 
                    "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + 
                    workVO.getDomainId() + ",'" + workVO.getEmergence() + "','" + workVO.getTransactType() + "','" + workVO.getCurStep() + "','" + para[1] + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + workVO.getRelProjectId() + ",'" + delayTime + "'";
                } 
                valueList.add(tmp);
                MessagesVO msgVO = new MessagesVO();
                msgVO.setMessage_date_begin(now);
                msgVO.setMessage_date_end(new Date("2050/1/1"));
                msgVO.setMessage_send_UserId(submitPersonId.longValue());
                msgVO.setMessage_send_UserName(submitPerson);
                msgVO.setMessage_show(1);
                msgVO.setMessage_status(1);
                msgVO.setMessage_time(now);
                msgVO.setMessage_title(passRoundTitle);
                msgVO.setMessage_toUserId(Long.parseLong(passRoundUser[i]));
                msgVO.setMessage_type("jsflow");
                msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
                msgList.add(msgVO);
              } 
            } 
          } 
        } 
      } 
      if ("3".equals(workVO.getTransactType())) {
        workFlowBD.insertTable("JSF_WORK", fieldString, valueList, "JSF_WORK_seq");
        List<String> showList = new ArrayList();
        showList.add(
            "update JSDB.JSF_WORK set worklistcontrol=0 where workTable_id = " + 
            tableId + " and workRecord_Id = " + recordId + 
            " and workStatus = 0");
        String minWorkId = "0";
        Connection conn = null;
        String itemTitle = "", toUserId = "";
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("select max(wf_work_id) from  JSDB.jsf_work where workTable_id = " + 
              tableId + " and workRecord_Id = " + recordId + " and workStatus = 0");
          if (rs.next())
            minWorkId = rs.getString(1); 
          rs.close();
          rs = stmt.executeQuery("select worktitle,wf_curemployee_id from JSDB.jsf_work where wf_work_id=" + minWorkId);
          if (rs.next()) {
            itemTitle = rs.getString(1);
            toUserId = rs.getString(2);
          } 
          rs.close();
          stmt.close();
          conn.close();
        } catch (Exception err) {
          if (conn != null)
            try {
              conn.close();
            } catch (Exception err2) {
              err2.printStackTrace();
            }  
          err.printStackTrace();
        } 
        showList.add("update JSDB.JSF_WORK set worklistcontrol=1 where wf_work_id = " + minWorkId);
        workFlowBD.updateTable(showList);
        MessagesVO msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(submitPersonId.longValue());
        msgVO.setMessage_send_UserName(submitPerson);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(itemTitle);
        msgVO.setMessage_toUserId(Long.parseLong(toUserId));
        msgVO.setMessage_type("jsflow");
        msgVO.setData_id(Long.parseLong(minWorkId));
        msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + minWorkId);
        try {
          MessagesBD mbd = new MessagesBD();
          mbd.messageAdd(msgVO);
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } else {
        workFlowBD.insertWorkAndSendMessage("JSF_WORK", fieldString, valueList, msgList);
      } 
    } 
    return surveillanceId;
  }
  
  public long newProcBranchSubmit(WorkVO workVO, String[] toUser, String moduleId, String[] para) {
    String databaseType = SystemCommon.getDatabaseType();
    int workType = workVO.getWorkType();
    String submitPerson = workVO.getSubmitPerson();
    Long submitPersonId = workVO.getSubmitEmployeeId();
    String remindValue = workVO.getRemindValue();
    if (remindValue == null || remindValue.toUpperCase().equals("NULL")) {
      remindValue = "";
      workVO.setRemindValue("");
    } 
    String fileType = workVO.getFileType();
    String selfTitle = "您的" + remindValue + fileType + "正在办理中！";
    String toUserTitle = String.valueOf(submitPerson) + "的" + remindValue + fileType + "等待您处理！";
    if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals("")) {
      selfTitle = workVO.getDocTitle();
      toUserTitle = workVO.getDocTitle();
    } 
    if (selfTitle.length() > 100)
      selfTitle = selfTitle.substring(0, 99); 
    if (toUserTitle.length() > 100)
      toUserTitle = toUserTitle.substring(0, 99); 
    Long processId = workVO.getProcessId();
    Long tableId = workVO.getTableId();
    Long recordId = workVO.getRecordId();
    String selfMainLinkFile = workVO.getSelfMainLinkFile();
    String toMainLinkFile = workVO.getToMainLinkFile();
    String submitOrg = workVO.getSubmitOrg();
    Date now = new Date();
    WorkFlowButtonBD wfbd = new WorkFlowButtonBD();
    String submitEmployeeOrgId = wfbd.getUserOrgId(submitPersonId.toString());
    String submitPersonOrgIdString = submitEmployeeOrgId;
    String[] branchActArray = para[4].split(";;;;");
    String processDeadlineDateStr = "null";
    ProcessBD processBD = new ProcessBD();
    String[] processDeadline = processBD.getProcessDeadline(processId.toString());
    if (processDeadline != null) {
      long processDeadlineTime = (new Date()).getTime();
      if (!"0".equals(processDeadline[0])) {
        if ("2".equals(processDeadline[1])) {
          processDeadlineTime += Integer.parseInt(processDeadline[0]) * 86400L * 1000L;
        } else if ("1".equals(processDeadline[1])) {
          processDeadlineTime += Integer.parseInt(processDeadline[0]) * 3600L * 1000L;
        } else {
          processDeadlineTime += Integer.parseInt(processDeadline[0]) * 60L * 1000L;
        } 
        if (databaseType.indexOf("mysql") >= 0) {
          processDeadlineDateStr = "'" + (new Date(processDeadlineTime)).toLocaleString() + "'";
        } else {
          processDeadlineDateStr = "JSDB.FN_STRTODATE('" + (new Date(processDeadlineTime)).toLocaleString() + "','L')";
        } 
      } 
    } 
    workVO.setProcessDeadlineDate(processDeadlineDateStr);
    WorkFlowBD workFlowBD = new WorkFlowBD();
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    String res = workFlowBD.copyProcInfo(processId.toString(), submitPersonId.toString(), 
        tableId.toString(), recordId.toString());
    if (!"0".equals(res))
      return 0L; 
    String fieldString = "wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, isStandForWork, standForUserId, standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,DOMAIN_ID,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork";
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
    long surveillanceId = 0L;
    List<MessagesVO> msgList = new ArrayList();
    DataSourceBase database = new DataSourceBase();
    try {
      database.begin();
      for (int actInd = 0; actInd < branchActArray.length; actInd++) {
        String branchActId = branchActArray[actInd].split(";;")[0];
        String branchActName = branchActArray[actInd].split(";;")[1];
        String branchActUser = branchActArray[actInd].split(";;")[2];
        String branchTransActType = branchActArray[actInd].split(";;")[3];
        ArrayList<String> valueList = new ArrayList();
        String deadLineTime = "-1";
        String pressMotionTime = "-1";
        Date workDeadlineDate = now;
        Date workDeadlinePressDate = now;
        if (workVO.getPressType() == 2) {
          String[] str = workFlowBD.getPress(branchActId, tableId.toString(), recordId.toString(), moduleId);
          deadLineTime = str[0];
          pressMotionTime = str[1];
        } else {
          deadLineTime = workVO.getDeadLine();
          pressMotionTime = workVO.getPressTime();
        } 
        if (!"-1".equals(deadLineTime)) {
          workDeadlineDate = new Date(now.getTime() + Long.parseLong(deadLineTime) * 1000L);
          workDeadlinePressDate = new Date(now.getTime() + (Long.parseLong(deadLineTime) - Long.parseLong(pressMotionTime)) * 1000L);
        } 
        toUser = branchActUser.split(",");
        List<String[]> toUserList = workFlowCommonBD.getStandForUserByProcessAndOrg(toUser, processId.toString(), submitPersonOrgIdString);
        String[] allowStandForUser = (String[])null;
        String tmp = "";
        for (int i = toUserList.size() - 1; i >= 0; i--) {
          allowStandForUser = toUserList.get(i);
          String newWorkParallelId = database.getTableId();
          if (databaseType.indexOf("mysql") >= 0) {
            tmp = String.valueOf(allowStandForUser[0]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
              toUserTitle + "', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
              ", '" + submitPerson + "', '" + now.toLocaleString() + "', " + 
              submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
              ", " + pressMotionTime + ", '" + now.toLocaleString() + "', " + 
              "1, 0, 0, 1, 0, 0, '', " + branchActId + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + workVO.getEmergence() + 
              "','" + branchTransActType + "','" + workVO.getCurStep() + "','" + para[1] + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + workVO.getRelProjectId() + 
              ",1," + newWorkParallelId + ",1," + branchActId + ",0";
          } else {
            tmp = String.valueOf(allowStandForUser[0]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
              toUserTitle + "', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
              ", '" + submitPerson + "', JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
              submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
              ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
              "1, 0, 0, 1, 0, 0, '', " + branchActId + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + workVO.getEmergence() + 
              "','" + branchTransActType + "','" + workVO.getCurStep() + "','" + para[1] + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + workVO.getRelProjectId() + 
              ",1," + newWorkParallelId + ",1," + branchActId + ",0";
          } 
          MessagesVO msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(submitPersonId.longValue());
          msgVO.setMessage_send_UserName(submitPerson);
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(toUserTitle);
          msgVO.setMessage_toUserId(Long.parseLong(allowStandForUser[0]));
          msgVO.setMessage_type("jsflow");
          msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
          msgList.add(msgVO);
          valueList.add(tmp);
          if (!allowStandForUser[2].equals("")) {
            newWorkParallelId = database.getTableId();
            if (databaseType.indexOf("mysql") >= 0) {
              tmp = String.valueOf(allowStandForUser[2]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
                toUserTitle + "（代" + allowStandForUser[1] + "办理）', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
                ", '" + submitPerson + "', '" + now.toLocaleString() + "', " + 
                submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
                ", " + pressMotionTime + ", '" + now.toLocaleString() + "', " + 
                "1, 0, 0, 1, 1, " + allowStandForUser[0] + ", '" + allowStandForUser[1] + "'," + 
                branchActId + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + 
                workVO.getEmergence() + "','" + branchTransActType + "','" + workVO.getCurStep() + "','" + para[1] + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + workVO.getRelProjectId() + 
                ",1," + newWorkParallelId + ",1," + branchActId + ",0";
            } else {
              tmp = String.valueOf(allowStandForUser[2]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
                toUserTitle + "（代" + allowStandForUser[1] + "办理）', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
                ", '" + submitPerson + "', JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
                submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
                ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L'), " + 
                "1, 0, 0, 1, 1, " + allowStandForUser[0] + ", '" + allowStandForUser[1] + "'," + 
                branchActId + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + workVO.getDomainId() + ",'" + 
                workVO.getEmergence() + "','" + branchTransActType + "','" + workVO.getCurStep() + "','" + para[1] + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + workVO.getRelProjectId() + 
                ",1," + newWorkParallelId + ",1," + branchActId + ",0";
            } 
            valueList.add(tmp);
            msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(submitPersonId.longValue());
            msgVO.setMessage_send_UserName(submitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(String.valueOf(toUserTitle) + "（代" + allowStandForUser[1] + "办理）'");
            msgVO.setMessage_toUserId(Long.parseLong(allowStandForUser[2]));
            msgVO.setMessage_type("jsflow");
            msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
            msgList.add(msgVO);
          } 
        } 
        if ("3".equals(workVO.getTransactType())) {
          workFlowBD.insertTable("JSF_WORK", fieldString, valueList, "JSF_WORK_seq");
          List<String> showList = new ArrayList();
          showList.add(
              "update JSDB.JSF_WORK set worklistcontrol=0 where workTable_id = " + 
              tableId + " and workRecord_Id = " + recordId + 
              " and workStatus = 0");
          String minWorkId = "0";
          Connection conn = null;
          String itemTitle = "", toUserId = "";
          try {
            conn = (new DataSourceBase()).getDataSource().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select max(wf_work_id) from  JSDB.jsf_work where workTable_id = " + 
                tableId + " and workRecord_Id = " + recordId + " and workStatus = 0");
            if (rs.next())
              minWorkId = rs.getString(1); 
            rs.close();
            rs = stmt.executeQuery("select worktitle,wf_curemployee_id from JSDB.jsf_work where wf_work_id=" + minWorkId);
            if (rs.next()) {
              itemTitle = rs.getString(1);
              toUserId = rs.getString(2);
            } 
            rs.close();
            stmt.close();
            conn.close();
          } catch (Exception err) {
            if (conn != null)
              try {
                conn.close();
              } catch (Exception err2) {
                err2.printStackTrace();
              }  
            err.printStackTrace();
          } 
          showList.add("update JSDB.JSF_WORK set worklistcontrol=1 where wf_work_id = " + minWorkId);
          workFlowBD.updateTable(showList);
          MessagesVO msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(submitPersonId.longValue());
          msgVO.setMessage_send_UserName(submitPerson);
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(itemTitle);
          msgVO.setMessage_toUserId(Long.parseLong(toUserId));
          msgVO.setMessage_type("jsflow");
          msgVO.setData_id(Long.parseLong(minWorkId));
          msgVO.setSendSMS((para.length > 3) ? Integer.valueOf(para[3]).intValue() : 1);
          msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + minWorkId);
          try {
            MessagesBD mbd = new MessagesBD();
            mbd.messageAdd(msgVO);
          } catch (Exception ex) {
            ex.printStackTrace();
          } 
        } else {
          workFlowBD.insertWorkAndSendMessage("JSF_WORK", fieldString, valueList, msgList);
        } 
      } 
      database.end();
    } catch (Exception err) {
      database.end();
      err.printStackTrace();
    } 
    surveillanceId = workFlowBD.setSurveillance(workVO);
    return surveillanceId;
  }
  
  public long newProcSubmit(WorkVO workVO, String[] toUser, String moduleId, String resubmitWorkId) {
    int workType = workVO.getWorkType();
    String submitPerson = workVO.getSubmitPerson();
    Long submitPersonId = workVO.getSubmitEmployeeId();
    String remindValue = workVO.getRemindValue();
    if (remindValue == null || remindValue.toUpperCase().equals("NULL"))
      remindValue = ""; 
    String fileType = workVO.getFileType();
    String selfTitle = "您的" + remindValue + fileType + "正在办理中！";
    if (selfTitle.length() > 100)
      selfTitle = selfTitle.substring(0, 99); 
    String toUserTitle = String.valueOf(submitPerson) + "的" + remindValue + fileType + "等待您处理！";
    if (toUserTitle.length() > 100)
      toUserTitle = toUserTitle.substring(0, 99); 
    Long processId = workVO.getProcessId();
    Long tableId = workVO.getTableId();
    Long recordId = workVO.getRecordId();
    String selfMainLinkFile = workVO.getSelfMainLinkFile();
    String toMainLinkFile = workVO.getToMainLinkFile();
    Date now = new Date();
    String submitOrg = workVO.getSubmitOrg();
    String fieldString = "wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, isStandForWork, standForUserId, standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,INITACTIVITYNAME";
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
    long surveillanceId = 0L;
    if (workType != 0) {
      WorkFlowBD workFlowBD = new WorkFlowBD();
      String res = workFlowBD.copyProcInfo(processId.toString(), submitPersonId.toString(), 
          tableId.toString(), recordId.toString());
      if (!"0".equals(res))
        return 0L; 
      if (toUser == null || toUser.length == 0 || toUser[0] == null || toUser[0].equals(""))
        return -1000L; 
      String deadLineTime = "-1";
      String pressMotionTime = "-1";
      if (workVO.getPressType() == 2) {
        String[] str = workFlowBD.getPress(workVO.getActivity().toString(), tableId.toString(), recordId.toString(), moduleId);
        deadLineTime = str[0];
        pressMotionTime = str[1];
      } else {
        deadLineTime = workVO.getDeadLine();
        pressMotionTime = workVO.getPressTime();
      } 
      ArrayList<String> valueList = new ArrayList();
      if (workVO.getAllowStandFor() == 1) {
        List<String[]> toUserList = workFlowBD.getStandForUser(toUser);
        String[] allowStandForUser = (String[])null;
        String tmp = "";
        for (int i = 0; i < toUserList.size(); i++) {
          allowStandForUser = toUserList.get(i);
          tmp = String.valueOf(allowStandForUser[0]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
            toUserTitle + "', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
            ", '" + submitPerson + "', to_date('" + now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + 
            submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
            ", " + pressMotionTime + ", to_date('" + now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + 
            "1, 0, 0, 1, 0, 0, '', " + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
            pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'" + ",'" + workVO.getCurStep() + "'";
          valueList.add(tmp);
          if (!allowStandForUser[2].equals("")) {
            tmp = String.valueOf(allowStandForUser[2]) + ", 0, '" + fileType + "', '" + workVO.getCurStep() + "', '" + 
              toUserTitle + "（代" + allowStandForUser[1] + "办理）', '', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + 
              ", '" + submitPerson + "', to_date('" + now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + 
              submitPersonId + ", 0, " + processId + ", " + tableId + ", " + recordId + ", " + deadLineTime + 
              ", " + pressMotionTime + ", to_date('" + now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + 
              "1, 0, 0, 1, 1, " + allowStandForUser[0] + ", '" + allowStandForUser[1] + "'," + 
              workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'" + ",'" + workVO.getCurStep() + "'";
            valueList.add(tmp);
          } 
        } 
      } else {
        String tmp = "";
        String insertUser = "";
        for (int i = 0; i < toUser.length; i++) {
          if (insertUser.indexOf(String.valueOf(toUser[i]) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + toUser[i] + ",";
            tmp = String.valueOf(toUser[i]) + ", 0 , '" + fileType + "', " + 
              "'" + workVO.getCurStep() + "','" + toUserTitle + "'," + 
              "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
              "to_date('" + now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss')," + submitPersonId + 
              ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
              ", " + pressMotionTime + ", to_date('" + now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss')," + 
              "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
              pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'" + ",'" + workVO.getCurStep() + "'";
            valueList.add(tmp);
          } 
        } 
      } 
      surveillanceId = workFlowBD.setSurveillance2(workVO, resubmitWorkId);
      if (workVO.isNeedPassRound()) {
        int passRoundUserType = workVO.getPassRoundUserType();
        String[] passRoundUser = { "" };
        if (workVO.getPassRoundUser() != null && (workVO.getPassRoundUser()).length > 0)
          passRoundUser = workVO.getPassRoundUser(); 
        String passRoundTitle = String.valueOf(submitPerson) + "的" + remindValue + fileType + "等待您的审阅！";
        if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals(""))
          passRoundTitle = workVO.getDocTitle(); 
        if (passRoundTitle.length() > 100)
          passRoundTitle = passRoundTitle.substring(0, 99); 
        boolean flag = true;
        String insertUser = "";
        for (int i = 0; i < passRoundUser.length; i++) {
          if (passRoundUser[i] != null && !passRoundUser[i].equals(""))
            if (insertUser.indexOf(String.valueOf(passRoundUser[i]) + ",") < 0) {
              insertUser = String.valueOf(insertUser) + passRoundUser[i] + ",";
              for (int j = 0; j < toUser.length; j++) {
                if (passRoundUser[i].equals(toUser[j])) {
                  flag = false;
                  break;
                } 
              } 
              if (flag) {
                String tmp = "";
                String databaseType = SystemCommon.getDatabaseType();
                if (databaseType.indexOf("mysql") >= 0) {
                  tmp = String.valueOf(passRoundUser[i]) + ", 2 , '" + fileType + "', " + 
                    "'" + workVO.getCurStep() + "','" + passRoundTitle + "'," + 
                    "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
                    "'" + now.toLocaleString() + "'," + submitPersonId + 
                    ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
                    ", " + pressMotionTime + ", '" + now.toLocaleString() + "'," + 
                    "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'";
                } else {
                  tmp = String.valueOf(passRoundUser[i]) + ", 2 , '" + fileType + "', " + 
                    "'" + workVO.getCurStep() + "','" + passRoundTitle + "'," + 
                    "'', '" + toMainLinkFile + "', 1, " + workVO.getActivity().toString() + ", '" + submitPerson + "'," + 
                    "JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L')," + submitPersonId + 
                    ", 0, " + processId + ", " + tableId + "," + recordId + "," + deadLineTime + 
                    ", " + pressMotionTime + ", JSDB.FN_STRTODATE('" + now.toLocaleString() + "','L')," + 
                    "1,0,0,1,0,0,''," + workVO.getActivity().toString() + ",1," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'" + ",'" + workVO.getCurStep() + "'";
                } 
                valueList.add(tmp);
              } 
            }  
        } 
      } 
      workFlowBD.insertTable("JSF_WORK", fieldString, valueList, "JSF_WORK_seq");
    } 
    return surveillanceId;
  }
  
  public void saveProcess(HttpServletRequest httpServletRequest, String recordId, int moduleId, int formType, String mainLinkFile, String cancelFile) {
    String operProcUser;
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    if (httpServletRequest.getParameter("workId") != null) {
      ArrayList<String> alist = new ArrayList();
      alist.add("delete from JSDB.JSF_WORK where worktable_id = (select worktable_id from JSDB.JSF_WORK where  wf_work_id = " + httpServletRequest.getParameter("workId") + ") and workrecord_id = (select workrecord_id from JSDB.jsf_work where wf_work_id = " + httpServletRequest.getParameter("workId") + ")");
      (new WorkFlowBD()).updateTable(alist);
    } 
    WorkVO workVO = new WorkVO();
    int workType = 1;
    if (httpServletRequest.getParameter("processType") != null && 
      "".equals(httpServletRequest.getParameter("processType")))
      workType = Integer.parseInt(httpServletRequest.getParameter("processType")); 
    workVO.setWorkType(workType);
    workVO.setSubmitPerson(httpSession.getAttribute("userName").toString());
    workVO.setSubmitEmployeeId(new Long(httpSession.getAttribute("userId").toString()));
    workVO.setRemindValue("");
    workVO.setFileType(httpServletRequest.getParameter("fileType"));
    workVO.setProcessId(new Long(httpServletRequest.getParameter("processId")));
    workVO.setRecordId(new Long(recordId));
    ModuleVO moduleVO = new ModuleVO();
    moduleVO.setFormType(formType);
    moduleVO.setId(moduleId);
    List<AccessTableVO> list = (new WorkFlowBD()).getAccessTable(moduleVO);
    AccessTableVO tableVO = list.get(0);
    workVO.setTableId(new Long(tableVO.getId()));
    workVO.setToMainLinkFile(mainLinkFile);
    workVO.setSelfMainLinkFile(mainLinkFile);
    workVO.setActivity(new Long(httpServletRequest.getParameter("activityId")));
    workVO.setCurStep(httpServletRequest.getParameter("activityName"));
    workVO.setAllowStandFor(Integer.parseInt(httpServletRequest.getParameter("allowStandFor")));
    workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
    workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
    workVO.setPressTime(httpServletRequest.getParameter("pressTime"));
    workVO.setUserType(Integer.parseInt(httpServletRequest.getParameter("userType")));
    workVO.setSubmitOrg(httpSession.getAttribute("orgName").toString());
    String[] toUser = { "" };
    switch (Integer.parseInt(httpServletRequest.getParameter("userType"))) {
      case 0:
        toUser = httpServletRequest.getParameterValues("candidateUser");
        break;
      case 1:
        operProcUser = httpServletRequest.getParameter("operProcUser");
        if (operProcUser != null && !operProcUser.equals("")) {
          operProcUser = operProcUser.substring(1, operProcUser.length() - 1);
          if (operProcUser.indexOf("$$") >= 0) {
            toUser = operProcUser.split("\\$\\$");
            break;
          } 
          toUser[0] = operProcUser;
        } 
        break;
      case 2:
        toUser = httpServletRequest.getParameterValues("candidateUser");
        break;
      case 3:
        toUser = httpServletRequest.getParameterValues("allUser");
        break;
      case 5:
        toUser[0] = userId;
        break;
      case 6:
        toUser = (new WorkFlowBD()).getRoleUser(httpServletRequest.getParameter("partRole"), userId);
        break;
    } 
    sendMsg(httpServletRequest, toUser, 0);
    workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
    int pressType = Integer.parseInt(httpServletRequest.getParameter("pressType"));
    switch (pressType) {
      case 0:
        workVO.setDeadLine("-1");
        workVO.setPressTime("-1");
        break;
      case 1:
        workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
        workVO.setPressTime(httpServletRequest.getParameter("pressMotionTime"));
        break;
    } 
    if (httpServletRequest.getParameter("needPassRound") != null) {
      String passRoundUser;
      workVO.setNeedPassRound(true);
      int passRoundUserType = Integer.parseInt(httpServletRequest.getParameter("passRoundUserType"));
      workVO.setPassRoundUserType(passRoundUserType);
      String[] passUser = { "" };
      switch (passRoundUserType) {
        case 0:
          passUser = httpServletRequest.getParameterValues("passRoundCandUser");
          break;
        case 1:
          passRoundUser = httpServletRequest.getParameter("passRoundUser");
          if (passRoundUser != null && !passRoundUser.equals("")) {
            passRoundUser = passRoundUser.substring(1, passRoundUser.length() - 1);
            if (passRoundUser.indexOf("$$") >= 0) {
              passUser = passRoundUser.split("\\$\\$");
              break;
            } 
            passUser[0] = passRoundUser;
          } 
          break;
        case 2:
          passUser = httpServletRequest.getParameterValues("passRoundCandUser");
          break;
        case 3:
          passUser = httpServletRequest.getParameterValues("passRoundAllUser");
          break;
        case 5:
          passUser[0] = userId;
          break;
        case 6:
          passUser = (new WorkFlowBD()).getRoleUser(httpServletRequest.getParameter("passRole"), userId);
          break;
      } 
      workVO.setPassRoundUser(passUser);
      sendMsg(httpServletRequest, passUser, 1);
    } else {
      workVO.setNeedPassRound(false);
    } 
    cancelFile = cancelFile.replaceAll("tableIdValue", (new StringBuilder(String.valueOf(tableVO.getId()))).toString());
    workVO.setCreatorCancelLink(cancelFile);
    if (httpServletRequest.getParameter("title") != null)
      workVO.setDocTitle(httpServletRequest.getParameter("title")); 
    newProcSubmit(workVO, toUser, (new StringBuilder(String.valueOf(moduleId))).toString(), httpSession.getAttribute("orgIdString").toString());
  }
  
  public String getButton(HttpServletRequest httpServletRequest) {
    StringBuffer nextStep = new StringBuffer();
    String workStatus = httpServletRequest.getParameter("workStatus");
    if (workStatus.equals("0")) {
      nextStep.append("<tr>");
      nextStep.append("<td colspan=10 align=center>");
      nextStep.append("<table width=40% border=0>");
      nextStep.append("<tr>");
      nextStep.append("<td>");
      nextStep.append("<table width=110 border=0 cellspacing=0 cellpadding=0>");
      nextStep.append("<tr>");
      nextStep.append("<td align=center>");
      nextStep.append("<button class=btnButton5Font onclick=\"javascript:save('next');\">下一办理步骤</button></td>");
      nextStep.append("</tr>");
      nextStep.append("</table>");
      nextStep.append("</td>");
      nextStep.append("<td>");
      nextStep.append("<table width=\"110\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      nextStep.append("<tr>");
      nextStep.append("<td align=center><button class=btnButton5Font onclick=\"javascript:untread();\">退回发起人</button></td>");
      nextStep.append("</tr>");
      nextStep.append("</table>");
      nextStep.append("</td>");
      nextStep.append("</tr>");
      nextStep.append("</table>");
      nextStep.append("</td></tr>");
      WorkFlowBD workFlowBD = new WorkFlowBD();
      String allowTransition = workFlowBD.getAllowTransition(httpServletRequest.getParameter("table"), httpServletRequest.getParameter("record"), httpServletRequest.getParameter("activity"));
      if (allowTransition.equals("1")) {
        nextStep.append("<tr><td colspan=\"2\" align=\"center\">");
        nextStep.append("<table border=\"0\">");
        nextStep.append("<tr><td>");
        nextStep.append("<input type=\"text\" name=\"transitionUserName\" readonly size=\"40\" class=\"inputText\">&nbsp;&nbsp;");
        nextStep.append("<input type=\"hidden\" name=\"transitionUser\" >");
        nextStep.append("<img width=\"15\" height=\"16\" style=\"cursor:'hand'\" src=\"images/select.gif\" onclick=\"openEndow('transitionUser','transitionUserName','','','user','no','user','*0*');\">&nbsp;&nbsp;");
        nextStep.append("</td><td>");
        nextStep.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        nextStep.append("<tr>");
        nextStep.append("<td align=center><button class=btnButton2Font onclick=\"javascript:trans();\">转办</button></td>");
        nextStep.append("</tr>");
        nextStep.append("</table>");
        nextStep.append("</td></tr></table>");
      } 
    } else if (workStatus.equals("-1")) {
      nextStep.append("<tr>");
      nextStep.append("<td colspan=\"2\" align=\"center\">");
      nextStep.append("<table width=\"40%\" border=\"0\">");
      nextStep.append("<tr>");
      nextStep.append("<td align=\"center\">");
      nextStep.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      nextStep.append("<tr>");
      nextStep.append("<td align=center><button class=btnButton2Font onclickf=\"javascript:save('reSub');\">提交</button></td>");
      nextStep.append("</tr>");
      nextStep.append("</table>");
      nextStep.append("</td>");
      nextStep.append("</tr></table>");
    } 
    ArrayList<String> alist = new ArrayList();
    alist.add("update JSDB.JSF_WORK set workReadMarker = 1 where wf_work_id = " + httpServletRequest.getParameter("work"));
    if (workStatus.equals("0"))
      alist.add(" update JSDB.JSF_WORK set workAllowCancel = 0 where worktable_id = " + httpServletRequest.getParameter("record") + 
          " and workrecord_id = " + httpServletRequest.getParameter("record") + 
          " and workStepCount = " + (Integer.parseInt(httpServletRequest.getParameter("stepCount")) - 1)); 
    (new WorkFlowBD()).updateTable(alist);
    return nextStep.toString();
  }
  
  public void getNextStep(HttpServletRequest httpServletRequest) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String activityId = httpServletRequest.getParameter("activityId");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String stepType = workFlowBD.getActivityType(activityId, tableId, recordId);
    httpServletRequest.setAttribute("stepType", stepType);
    String[] actiClass = workFlowBD.getActivityClass(tableId, recordId, activityId);
    httpServletRequest.setAttribute("activityClass", actiClass[0]);
    if (stepType.equals("1")) {
      List<ActivityVO> activityList = workFlowBD.getAllNextActivity(tableId, recordId, activityId);
      StringBuffer nextActivity = new StringBuffer();
      boolean canFinish = false;
      boolean hasNextActivity = false;
      if (activityList != null && activityList.size() > 0) {
        ActivityVO activityVO = null;
        if (activityList.size() == 1) {
          activityVO = activityList.get(0);
          if (activityVO.getBeginEnd() == 2) {
            hasNextActivity = false;
          } else {
            hasNextActivity = true;
            nextActivity.append("<tr>");
            nextActivity.append("<td width=\"15%\" height=\"22\">选择下一节点：</td>");
            nextActivity.append("<td>");
          } 
        } else {
          hasNextActivity = true;
          nextActivity.append("<tr>");
          nextActivity.append("<td width=\"15%\" height=\"22\">选择下一节点：</td>");
          nextActivity.append("<td>");
        } 
        for (int j = 0; j < activityList.size(); j++) {
          activityVO = activityList.get(j);
          if (activityVO.getBeginEnd() == 2) {
            canFinish = true;
          } else {
            nextActivity.append("<input type=\"radio\" name=\"nextActivity\" value=\"" + activityVO.getId() + "\" onclick=\"nextUser(this)\">");
            nextActivity.append(String.valueOf(activityVO.getName()) + "&nbsp;&nbsp;");
          } 
        } 
        nextActivity.append("</td>");
        nextActivity.append("</tr>");
      } else {
        canFinish = true;
      } 
      httpServletRequest.setAttribute("nextActivity", nextActivity.toString());
      StringBuffer button = new StringBuffer();
      button.append("<tr id=\"nextTr\"><td colspan=\"2\" align=\"center\">");
      button.append("<table width=\"40%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\">");
      button.append("<tr>");
      if (hasNextActivity) {
        button.append("<td align=\"center\">");
        button.append("<table width=\"90\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
        if (actiClass[0].equals("1")) {
          button.append("<a href=\"javascript:sub();\"><font color=\"#000000\">提交</font></a>");
        } else {
          button.append("<a href=\"javascript:nextSub();\"><font color=\"#000000\">下一步骤</font></a>");
        } 
        button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
        button.append("</table>");
        button.append("</td>");
      } 
      if (canFinish) {
        button.append("<td align=\"center\">");
        button.append("<table width=\"80\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
        button.append("<a href=\"javascript:comp();\"><font color=\"#000000\">结束流程</font></a>");
        button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
        button.append("</table>");
        button.append("</td>");
      } 
      button.append("<td align=\"center\">");
      button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
      button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
      button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
      button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
      button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
      button.append("</table>");
      button.append("</td>");
      button.append("</tr>");
      button.append("</table>");
      button.append("</td></tr>");
      button.append("<tr id=\"subTr\" style=\"display:none\"><td colspan=\"2\" align=\"center\">");
      button.append("<table width=\"50%\"><tr><td align=\"center\">");
      button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      button.append("<tr>");
      button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
      button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
      button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
      button.append("<a href=\"javascript:sub();\"><font color=\"#000000\">提交</font></a>");
      button.append("</td><td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
      button.append("</tr>");
      button.append("</table>");
      button.append("</td><td>");
      button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
      button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
      button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
      button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
      button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
      button.append("</table>");
      button.append("</td></tr></table>");
      button.append("</td></tr>");
      httpServletRequest.setAttribute("button", button.toString());
    } else {
      ActivityVO activityVO = (new ProcessStep()).getProceedNextActi(activityId, tableId, 
          recordId, httpServletRequest);
      if (activityVO == null || activityVO.getActivityBeginEnd() == 2) {
        StringBuffer button = new StringBuffer();
        button.append("<tr><td colspan=\"2\" align=\"center\">");
        button.append("<table width=\"200\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
        button.append("<a href=\"javascript:comp();\"><font color=\"#000000\">结束流程</font></a></td>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
        button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a></td>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
        button.append("</tr>");
        button.append("</table>");
        button.append("</td></tr>");
        httpServletRequest.setAttribute("nextActivity", "");
        httpServletRequest.setAttribute("button", button.toString());
      } else {
        int i;
        List<Object[]> list1, candidate;
        Object[] obj;
        int j;
        StringBuffer tmpBuffer = new StringBuffer();
        tmpBuffer.append("<tr>");
        tmpBuffer.append("<td>下一办理步骤：</td>");
        tmpBuffer.append("<td>");
        tmpBuffer.append("节点名称：<label class=mustFillcolor>" + activityVO.getName() + "</label>&nbsp;&nbsp;办理用户：");
        Object[] leaderObj = (Object[])null;
        List<Object[]> leaderList = null;
        switch (activityVO.getParticipantType()) {
          case 0:
            leaderList = (new WorkFlowBD()).getLeaderList(httpServletRequest.getParameter("submitPersonId"));
            tmpBuffer.append("<select name=\"mainCandidateUser\" size=\"4\" multiple=\"multiple\">");
            for (i = 0; i < leaderList.size(); i++) {
              leaderObj = leaderList.get(i);
              tmpBuffer.append("<option value=\"" + leaderObj[0] + "\"");
              if (i == 0)
                tmpBuffer.append("selected"); 
              tmpBuffer.append(">" + leaderObj[1] + "</option>");
            } 
            tmpBuffer.append("</select>");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"0\">");
            break;
          case 1:
            tmpBuffer.append("<input type=\"text\" name=\"mainOperProcUserName\" readonly size=\"40\" class=\"css0\">");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainOperProcUser\">&nbsp;");
            tmpBuffer.append("<img src=\"images/group.gif\">&nbsp;&nbsp;");
            tmpBuffer.append("<img width=\"15\" height=\"16\" style=\"cursor:'hand'\"src=\"images/select.gif\" onclick=\"openEndow('mainOperProcUser','mainOperProcUserName',mainOperProcUser.value,mainOperProcUserName.value,'user','no','user','*0*');\">");
            tmpBuffer.append("&nbsp;<label class=mustFillcolor>*</label>");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"1\">");
            break;
          case 2:
            tmpBuffer.append("<select name=\"mainCandidateUser\" size=\"4\" multiple=\"multiple\">");
            list1 = activityVO.getParticipantUser();
            obj = (Object[])null;
            for (j = 0; j < list1.size(); j++) {
              obj = list1.get(j);
              tmpBuffer.append("<option value=\"" + obj[0] + "\"");
              if (j == 0)
                tmpBuffer.append("selected"); 
              tmpBuffer.append(">" + obj[1] + "</option>");
            } 
            tmpBuffer.append("</select>");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"2\">");
            break;
          case 3:
            candidate = activityVO.getParticipantUser();
            tmpBuffer.append("<label class=mustFillcolor>指定全部办理人</label>");
            for (j = 0; j < candidate.size(); j++) {
              obj = candidate.get(j);
              tmpBuffer.append("<input type=\"hidden\" name=\"mainAllUser\" value=\"" + obj[0] + "\">");
            } 
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"3\">");
            break;
          case 4:
            tmpBuffer.append("<label class=mustFillcolor>由表单中的某个字段值决定</label>");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserField\" value=\"" + activityVO.getParticipantUserField() + "\">");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"4\">");
            break;
          case 5:
            tmpBuffer.append("<label class=mustFillcolor>流程发起人</label>");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"5\">");
            break;
          case 6:
            tmpBuffer.append("<label class=mustFillcolor>" + activityVO.getPartRoleName() + "</label>");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"6\">");
            tmpBuffer.append("<input type=\"hidden\" name=\"mainPartRole\" value=\"" + activityVO.getPartRole() + "\">");
            break;
        } 
        if (activityVO.getNeedPassRound().booleanValue()) {
          int k;
          List<Object[]> passRoundCand;
          Object[] passRoundObj;
          int m;
          List<Object[]> passRoundAllUser;
          Object[] passRoundAllUserObj;
          int n;
          tmpBuffer.append("&nbsp;&nbsp;阅件用户：<input type=\"hidden\" name=\"mainNeedPassRound\" value=\"1\">");
          switch (activityVO.getPassRoundUserType()) {
            case 0:
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"0\">");
              leaderList = (new WorkFlowBD()).getLeaderList(httpServletRequest.getParameter("submitPersonId"));
              for (k = 0; k < leaderList.size(); k++) {
                leaderObj = leaderList.get(k);
                tmpBuffer.append("<option value=\"" + leaderObj[0] + "\"");
                if (k == 0)
                  tmpBuffer.append(" selected "); 
                tmpBuffer.append(">" + leaderObj[1] + "</option>");
              } 
              tmpBuffer.append("</select>");
              break;
            case 1:
              tmpBuffer.append("<input type=\"text\" name=\"mainPassRoundUserName\" readonly size=\"30\" class=\"css0\"><input type=\"hidden\" name=\"mainPassRoundUser\">&nbsp;<img src=\"images/group.gif\">&nbsp;<img width=\"15\" height=\"16\" style=\"cursor:'hand'\"src=\"images/select.gif\" onclick=\"openEndow('mainPassRoundUser','mainPassRoundUserName',mainPassRoundUser.value,mainPassRoundUserName.value,'user','no','user','*0*');\"><input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"1\">");
              break;
            case 2:
              tmpBuffer.append("<select name=\"mainPassRoundCandUser\" size=\"4\" multiple=\"multiple\">");
              passRoundCand = activityVO.getPassRoundUser();
              passRoundObj = (Object[])null;
              for (m = 0; m < passRoundCand.size(); m++) {
                passRoundObj = passRoundCand.get(m);
                tmpBuffer.append("<option value=\"" + passRoundObj[0] + "\"");
                if (m == 0)
                  tmpBuffer.append(" selected "); 
                tmpBuffer.append(">" + passRoundObj[1] + "</option>");
              } 
              tmpBuffer.append("</select>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"2\">");
              break;
            case 3:
              tmpBuffer.append("<label class=mustFillcolor>指定全部办理人</label>");
              passRoundAllUser = activityVO.getPassRoundUser();
              passRoundAllUserObj = (Object[])null;
              for (n = 0; n < passRoundAllUser.size(); n++) {
                passRoundAllUserObj = passRoundAllUser.get(n);
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundAllUser\" value=\"" + passRoundAllUserObj[0] + "\">");
              } 
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"3\">");
              break;
            case 4:
              tmpBuffer.append("<label class=mustFillcolor>由表单中的某个字段值决定</label><input type=\"hidden\" name=\"mainPassRoundUserField\" value=\"" + 
                  activityVO.getPassRoundUserField() + "\">" + 
                  "<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"4\">");
              break;
            case 5:
              tmpBuffer.append("<label class=mustFillcolor>流程发起人</label>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"5\">");
              break;
            case 6:
              tmpBuffer.append("<label class=mustFillcolor>" + activityVO.getPassRoleName() + "</label>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"6\">");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRole\" value=\"" + activityVO.getPassRole() + "\">");
              break;
          } 
        } 
        tmpBuffer.append("<input type=checkbox name=needSendMsg value=1>短信提醒");
        tmpBuffer.append("</td>");
        tmpBuffer.append("</tr>");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainAllowStandFor\" value=\"" + activityVO.getAllowStandFor() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainAllowCancel\" value=\"" + activityVO.getAllowcancel() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainAllowTransition\" value=\"" + activityVO.getAllowTransition() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainPressType\" value=\"" + activityVO.getPressType() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainDeadLineTime\" value=\"" + activityVO.getDeadlineTime() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainPressMotionTime\" value=\"" + activityVO.getPressMotionTime() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainNextActivityId\" value=\"" + activityVO.getId() + "\">");
        tmpBuffer.append("<input type=\"hidden\" name=\"mainNextActivityName\" value=\"" + activityVO.getName() + "\">");
        httpServletRequest.setAttribute("nextActivity", tmpBuffer.toString());
        StringBuffer button = new StringBuffer();
        button.append("<tr id=\"nextTr\"><td colspan=\"2\" align=\"center\">");
        button.append("<table width=\"50%\"><tr><td align=\"center\">");
        button.append("<table width=\"80\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
        if (actiClass[0].equals("1")) {
          button.append("<a href=\"javascript:sub2();\"><font color=\"#000000\">提交</font></a>");
        } else {
          button.append("<a href=\"javascript:nextSub();\"><font color=\"#000000\">下一步骤</font></a>");
        } 
        button.append("</td><td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
        button.append("</tr>");
        button.append("</table>");
        button.append("</td><td>");
        button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
        button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
        button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
        button.append("</table>");
        button.append("</td></tr></table>");
        button.append("</td></tr>");
        button.append("<tr id=\"subTr\" style=\"display:none\"><td colspan=\"2\" align=\"center\">");
        button.append("<table width=\"50%\"><tr><td align=\"center\">");
        button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
        button.append("<a href=\"javascript:sub2();\"><font color=\"#000000\">提交</font></a>");
        button.append("</td><td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
        button.append("</tr>");
        button.append("</table>");
        button.append("</td><td>");
        button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
        button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
        button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
        button.append("</table>");
        button.append("</td></tr></table>");
        button.append("</td></tr>");
        httpServletRequest.setAttribute("button", button.toString());
      } 
    } 
    String transactType = workFlowBD.getTransactType(tableId, recordId, activityId);
    httpServletRequest.setAttribute("curTransactType", transactType);
  }
  
  public void operate(HttpServletRequest httpServletRequest, String toMainFile) {
    String str[], mainOperProcUser;
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowBD newWorkFlowBD = new WorkFlowBD();
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String userId = httpSession.getAttribute("userId").toString();
    String mainNextActivityName = httpServletRequest.getParameter("mainNextActivityName");
    String mainNextActivityId = httpServletRequest.getParameter("mainNextActivityId");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String activityClass = "1";
    String subProcWorkId = "0";
    String comment = (httpServletRequest.getParameter("comment") == null) ? "" : httpServletRequest.getParameter("comment");
    String actiCommField = (httpServletRequest.getParameter("actiCommField") == null) ? "" : httpServletRequest.getParameter("actiCommField");
    String[] dealWithPara = { 
        tableId, 
        recordId, 
        curActivityName, 
        curActivityId, 
        userId, 
        comment, 
        mainNextActivityName, 
        mainNextActivityId, 
        stepCount, 
        isStandForWork, 
        standForUserId, 
        activityClass, 
        subProcWorkId, 
        actiCommField };
    newWorkFlowBD.insertDealWith(dealWithPara);
    String workId = httpServletRequest.getParameter("workId");
    String mainPressType = httpServletRequest.getParameter("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    switch (Integer.parseInt(mainPressType)) {
      case 0:
        mainDeadLineTime = "-1";
        mainPressMotionTime = "-1";
        break;
      case 1:
        mainDeadLineTime = httpServletRequest.getParameter("mainDeadLineTime");
        mainPressMotionTime = httpServletRequest.getParameter("mainPressMotionTime");
        break;
      case 2:
        str = newWorkFlowBD.getPress(mainNextActivityId, "", (new StringBuilder(String.valueOf(recordId))).toString());
        mainDeadLineTime = str[0];
        mainPressMotionTime = str[1];
        break;
    } 
    String mainAllowCancel = httpServletRequest.getParameter("mainAllowCancel");
    String curTransactType = httpServletRequest.getParameter("curTransactType");
    String mainAllowStandFor = httpServletRequest.getParameter("mainAllowStandFor");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String docTitle = "";
    if (httpServletRequest.getParameter("title") != null)
      docTitle = httpServletRequest.getParameter("title"); 
    String[] para = { 
        mainNextActivityName, mainNextActivityId, workId, 
        mainDeadLineTime, mainPressMotionTime, curActivityId, 
        mainAllowCancel, stepCount, "", curTransactType, 
        toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, 
        standForUserName, activityClass, "", docTitle };
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    String[] mainTransactUser = { "" };
    int mainUserType = Integer.parseInt(httpServletRequest.getParameter("mainUserType"));
    switch (mainUserType) {
      case 0:
        mainTransactUser = httpServletRequest.getParameterValues("mainCandidateUser");
        break;
      case 1:
        mainOperProcUser = httpServletRequest.getParameter("mainOperProcUser");
        if (mainOperProcUser != null && !mainOperProcUser.equals("")) {
          mainOperProcUser = mainOperProcUser.substring(1, mainOperProcUser.length() - 1);
          if (mainOperProcUser.indexOf("$$") >= 0) {
            mainTransactUser = mainOperProcUser.split("\\$\\$");
            break;
          } 
          mainTransactUser[0] = mainOperProcUser;
        } 
        break;
      case 2:
        mainTransactUser = httpServletRequest.getParameterValues("mainCandidateUser");
        break;
      case 3:
        mainTransactUser = httpServletRequest.getParameterValues("mainAllUser");
        break;
      case 5:
        mainTransactUser[0] = submitPersonId;
        break;
      case 6:
        mainTransactUser = newWorkFlowBD.getRoleUser(httpServletRequest.getParameter("mainPartRole"), submitPersonId);
        break;
    } 
    sendMsg(httpServletRequest, mainTransactUser, 0);
    String mainNeedPassRound = "";
    String[] mainPassRoundUser = { "" };
    if (httpServletRequest.getParameter("mainNeedPassRound") != null) {
      String mainPassRoundUserleader;
      mainNeedPassRound = httpServletRequest.getParameter("mainNeedPassRound");
      switch (Integer.parseInt(httpServletRequest.getParameter("mainPassRoundUserType"))) {
        case 0:
          mainPassRoundUserleader = newWorkFlowBD.getLeader(submitPersonId);
          if (mainPassRoundUserleader != null && !mainPassRoundUserleader.equals("")) {
            if (mainPassRoundUserleader.indexOf("$") > 0) {
              if (mainPassRoundUserleader.indexOf("$$") > 0)
                mainPassRoundUser = mainPassRoundUserleader.split("\\$\\$"); 
              break;
            } 
            mainPassRoundUser[0] = mainPassRoundUserleader;
          } 
          break;
        case 1:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundCandUser");
          break;
        case 2:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundCandUser");
          break;
        case 3:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundAllUser");
          break;
        case 5:
          mainPassRoundUser[0] = submitPersonId;
          break;
        case 6:
          mainPassRoundUser = newWorkFlowBD.getRoleUser(httpServletRequest.getParameter("mainPassRole"), submitPersonId);
          break;
      } 
      sendMsg(httpServletRequest, mainTransactUser, 1);
    } 
    newWorkFlowBD.operateWork(para, mainTransactUser, mainNeedPassRound, mainPassRoundUser);
  }
  
  public String getComment(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("table");
    String recordId = httpServletRequest.getParameter("record");
    String workType = httpServletRequest.getParameter("workType");
    String workStatus = httpServletRequest.getParameter("workStatus");
    StringBuffer comment = new StringBuffer();
    StringBuffer ActivityIDStr = new StringBuffer();
    List<String[]> commentList = (new WorkFlowBD()).getDealWithComment(tableId, recordId, workType);
    if (workType.equals("0")) {
      if (commentList.size() > 0) {
        comment.append("<tr>");
        comment.append("<td>用户办理意见：</td>");
        comment.append("<td>");
        String[] comm = (String[])null;
        for (int j = 0; j < commentList.size(); j++) {
          comm = commentList.get(j);
          comment.append("&nbsp;&nbsp;&nbsp;&nbsp;");
          if (comm[1] != null)
            comment.append(CharacterTool.escapeHTMLTags(comm[1])); 
          comment.append("<br>");
          comment.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
          comment.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
          comment.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
          comment.append(String.valueOf(comm[0]) + "&nbsp;&nbsp;(");
          if (comm[2].indexOf(".") > 0) {
            comment.append(comm[2].substring(0, comm[2].indexOf(".")));
          } else {
            comment.append(comm[2]);
          } 
          comment.append(") <br><br>");
        } 
        comment.append("</td>");
        comment.append("</tr>");
      } 
      if (workStatus.equals("0") || workStatus.equals("2")) {
        comment.append("<tr>");
        comment.append("<td>办理意见：</td>");
        comment.append("<td><textarea name=\"comment\" cols=\"60\" Class=\"css0\" rows=\"4\" onblur=\"checkTextArea(this,'办理意见',100);\"></textarea>&nbsp;<a href=\"javascript:offiDict(" + httpServletRequest.getSession(true).getAttribute("userId") + ", 'comment');\">选择办公用语</a></td>");
        comment.append("</tr>");
      } 
    } else {
      ArrayList<String[]> tmpList = new ArrayList();
      String disActivityId = "";
      String disCurStepCount = "";
      int j;
      for (j = 0; j < commentList.size(); j++) {
        String[] str2 = commentList.get(j);
        if (!disActivityId.equals(str2[0]) && !disCurStepCount.equals(str2[5])) {
          ActivityIDStr.append("$").append(str2[0]).append("$");
          disActivityId = str2[0];
          disCurStepCount = str2[5];
          String[] str3 = { str2[0], str2[1], str2[5] };
          tmpList.add(str3);
        } 
      } 
      if (tmpList.size() > 0) {
        comment.append("<tr><td colspan=\"2\">");
        comment.append("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#666666\" bgcolor=\"#999999\">");
      } 
      for (j = 0; j < tmpList.size(); j++) {
        String[] str2 = tmpList.get(j);
        comment.append("<tr bgcolor=\"#E1E1E1\">");
        comment.append("<td>");
        comment.append(String.valueOf(str2[1]) + "：");
        comment.append("</td>");
        comment.append("<td>");
        for (int k = 0; k < commentList.size(); k++) {
          String[] str3 = commentList.get(k);
          if (str2[0].equals(str3[0]) && str2[2].equals(str3[5])) {
            comment.append("&nbsp;&nbsp;&nbsp;&nbsp;");
            if (str3[3] != null)
              comment.append(CharacterTool.escapeHTMLTags(str3[3])); 
            comment.append("<br>");
            comment.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            comment.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            comment.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            comment.append(String.valueOf(str3[2]) + "&nbsp;&nbsp;(");
            if (str3[4].indexOf(".") > 0) {
              comment.append(str3[4].substring(0, str3[4].indexOf(".")));
            } else {
              comment.append(str3[4]);
            } 
            comment.append(") ");
            if (str3[6] != null && !str3[6].toString().equals(""))
              comment.append("<font color=blue>(" + str3[6] + "代办)</font>"); 
            comment.append("<br><br>");
          } 
        } 
        comment.append("</td>");
        comment.append("</tr>");
      } 
      if (tmpList.size() > 0)
        comment.append("</table></td></tr>"); 
      if (workStatus.equals("0") || workStatus.equals("2")) {
        comment.append("<tr>");
        comment.append("<td>办理意见：</td>");
        comment.append("<td><textarea name=\"comment\" cols=\"60\" Class=\"css0\" rows=\"4\" onblur=\"checkTextArea(this,'办理意见',100);\"></textarea>&nbsp;<a href=\"javascript:offiDict(" + httpServletRequest.getSession(true).getAttribute("userId") + ", 'comment');\">选择办公用语</a></td>");
        comment.append("</tr>");
      } 
    } 
    httpServletRequest.setAttribute("ActivityIDStr", ActivityIDStr.toString());
    return comment.toString();
  }
  
  public void comp(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String userId = httpSession.getAttribute("userId").toString();
    String mainNextActivityName = httpServletRequest.getParameter("mainNextActivityName");
    String mainNextActivityId = httpServletRequest.getParameter("mainNextActivityId");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String activityClass = "1";
    String subProcWorkId = "0";
    String comment = httpServletRequest.getParameter("comment");
    String commentField = (httpServletRequest.getParameter("actiCommField") == null) ? "" : httpServletRequest.getParameter("actiCommField");
    String[] dealPara = { 
        tableId, recordId, curActivityName, curActivityId, userId, 
        comment, "", "-1", stepCount, isStandForWork, 
        standForUserId, 
        activityClass, "0", commentField };
    WorkFlowBD newWorkFlowBD = new WorkFlowBD();
    newWorkFlowBD.insertDealWith(dealPara);
    String workId = httpServletRequest.getParameter("workId");
    String mainPressType = httpServletRequest.getParameter("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    String processName = httpServletRequest.getParameter("processName");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String curTransactType = httpServletRequest.getParameter("curTransactType");
    String docTitle = "";
    if (httpServletRequest.getParameter("title") != null)
      docTitle = httpServletRequest.getParameter("title"); 
    String[] para = { 
        tableId, recordId, curActivityId, processName, submitPerson, 
        "", curTransactType, stepCount, 
        isStandForWork, 
        userId, 
        standForUserId, 
        httpServletRequest.getParameter("standForUserName"), 
        docTitle };
    String tmp = newWorkFlowBD.completeWork(para, workId);
  }
  
  public void Untread(HttpServletRequest httpServletRequest) {
    String comment = String.valueOf(httpServletRequest.getParameter("comment")) + "&nbsp;&nbsp;（退回）";
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String stepCount = httpServletRequest.getParameter("stepCount");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] para = { tableId, recordId, curActivityName, curActivityId, userId, comment, "", "0", stepCount };
    workFlowBD.insertDealWith(para);
    String remindField = httpServletRequest.getParameter("remindField");
    StringBuffer remindFieldValue = new StringBuffer();
    int k = (String.valueOf(submitPerson) + "的" + remindFieldValue.toString() + processName + "已被您退回！").length() - 50;
    if (k > 0)
      remindFieldValue.substring(0, remindFieldValue.length() - k); 
    ArrayList<String> alist = new ArrayList();
    alist.add("update JSDB.JSF_WORK set workStatus = -1, workTitle = '您的" + 
        remindFieldValue.toString() + processName + "已被" + userName + 
        "退回！',workCancelReason = '" + comment + "' where workstartflag = 1 and worktable_id = " + 
        tableId + " and workrecord_id = " + recordId);
    alist.add("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + submitPerson + 
        "的" + remindFieldValue.toString() + processName + 
        "已被您退回！',workAllowCancel = 0 where wf_work_id = " + workId);
    alist.add(" delete from JSDB.JSF_WORK where ( workReadMarker = 0 or workListControl = 0 )  and worktable_id = " + 
        tableId + " and workrecord_id = " + 
        recordId + " and wf_work_id <> " + workId + " and workstartflag <> 1");
    alist.add("update JSDB.JSF_WORK set workAllowCancel = 0 where workTable_id = " + tableId + 
        " and workRecord_id = " + recordId + " and workStepCount = " + (Integer.parseInt(stepCount) - 1));
    workFlowBD.updateTable(alist);
  }
  
  public void transition(HttpServletRequest httpServletRequest, String mailLinkFile) {
    String workId = httpServletRequest.getParameter("workId");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String transitionUser = httpServletRequest.getParameter("transitionUser");
    String activityId = httpServletRequest.getParameter("curActivityId");
    String activityName = httpServletRequest.getParameter("curActivityName");
    String comment = (httpServletRequest.getParameter("comment") == null) ? "" : httpServletRequest.getParameter("comment");
    String commentField = (httpServletRequest.getParameter("commentField") == null) ? "" : httpServletRequest.getParameter("commentField");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String remindField = httpServletRequest.getParameter("remindField");
    StringBuffer remindFieldValue = new StringBuffer();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    transitionUser = transitionUser.replace('$', ',');
    String[] user = ("," + transitionUser + ",").split(",,");
    String[] para = { workId, stepCount, remindFieldValue.toString(), mailLinkFile };
    workFlowBD.transitionWork(para, user);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String[] tmpPara = { 
        tableId, 
        recordId, 
        activityName, 
        activityId, 
        userId, 
        comment, 
        "", 
        "1", 
        stepCount, 
        httpServletRequest.getParameter("isStandForWork"), 
        httpServletRequest.getParameter("standForUserId"), 
        "1", 
        "0", 
        commentField };
    workFlowBD.insertDealWith(tmpPara);
  }
  
  public void creatorCancel(HttpServletRequest httpServletRequest) {
    String cancelReason = httpServletRequest.getParameter("cancelReason");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String processName = httpServletRequest.getParameter("processName");
    String workId = httpServletRequest.getParameter("workId");
    String processId = httpServletRequest.getParameter("processId");
    String remindValue = "";
    if (httpServletRequest.getParameter("remindValue") != null)
      remindValue = httpServletRequest.getParameter("remindValue"); 
    WorkFlowBD workFlowBD = new WorkFlowBD();
    List alist = workFlowBD.getWorkUserLogin(tableId, recordId, processId);
    ArrayList<String> sqlList = new ArrayList();
    sqlList.add("delete from JSF_WORK where worktable_id = " + tableId + " and workrecord_id = " + 
        recordId + " and workProcess_id = " + processId + " and wf_work_id <> " + workId);
    if (httpServletRequest.getParameter("channelType") != null) {
      sqlList.add("update JSF_WORK set workTitle = '您已取消了您的" + remindValue + processName + 
          "', workStatus = -1, workCancelReason = '" + cancelReason + 
          "', workMainLinkFile = '/jsoa/InfoProcAction.do?channelType=" + httpServletRequest.getParameter("channelType") + "&informationType=" + httpServletRequest.getParameter("informationType") + "&redhead=" + httpServletRequest.getParameter("redhead") + "' where wf_work_id = " + workId);
    } else {
      sqlList.add("update JSF_WORK set workTitle = '您已取消了您的" + remindValue + processName + 
          "', workStatus = -1, workCancelReason = '" + cancelReason + 
          "', workMainLinkFile = '/jsoa/WorkFlowReSubmitAction.do?pp=1' where wf_work_id = " + workId);
    } 
    workFlowBD.updateTable(sqlList);
  }
  
  public void archiveCancel(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String processName = httpServletRequest.getParameter("processName");
    try {
      processName = new String(processName.getBytes("ISO8859-1"), "GBK");
    } catch (Exception exception) {}
    String workId = httpServletRequest.getParameter("workId");
    String processId = httpServletRequest.getParameter("processId");
    String remindValue = "";
    if (httpServletRequest.getParameter("remindValue") != null)
      remindValue = httpServletRequest.getParameter("remindValue"); 
    WorkFlowBD workFlowBD = new WorkFlowBD();
    List alist = workFlowBD.getWorkUserLogin(tableId, recordId, processId);
    ArrayList<String> sqlList = new ArrayList();
    sqlList.add("delete from JSF_WORK where worktable_id = (select worktable_id from JSDB.jsf_work where wf_work_id=" + workId + ") and workrecord_id = (select workrecord_id from JSDB.jsf_work where wf_work_id=" + workId + ") and workProcess_id = (select workProcess_id from JSDB.jsf_work where wf_work_id=" + workId + ")");
    workFlowBD.updateTable(sqlList);
  }
  
  public void sendMsg(HttpServletRequest httpServletRequest, String[] toUser, int flag) {
    if (httpServletRequest.getParameter("sendMsg") != null && 
      httpServletRequest.getParameter("needSendMsg") != null) {
      int sendMsg = 0;
      try {
        sendMsg = Integer.parseInt(httpServletRequest.getParameter("sendMsg"));
      } catch (Exception exception) {}
      StringBuffer tmp = new StringBuffer();
      for (int i = 0; i < toUser.length; i++)
        tmp.append(String.valueOf(toUser[i]) + ","); 
      if (tmp.toString().endsWith(","))
        tmp.substring(0, tmp.length() - 1); 
      String msgFrom = "", title = "";
      switch (sendMsg) {
        case 1:
          msgFrom = "发文管理";
          title = httpServletRequest.getParameter("title");
          break;
        case 2:
          msgFrom = "收文管理";
          title = httpServletRequest.getParameter("title");
          break;
        case 3:
          msgFrom = "议题管理";
          title = httpServletRequest.getParameter("subjectTitle");
          break;
        case 4:
          msgFrom = "工作流程";
          title = httpServletRequest.getParameter("processName");
          break;
        case 5:
          msgFrom = "知识管理";
          title = httpServletRequest.getParameter("informationTitle");
          break;
      } 
      try {
        (new ModelSendMsg()).sendSystemMessage(msgFrom, title, tmp.toString(), "", httpServletRequest);
      } catch (Exception exception) {}
    } 
  }
}
