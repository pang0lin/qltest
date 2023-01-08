package com.js.oa.jsflow.util;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;

public class ProcessActivityUtil {
  public ConcurrentHashMap getFirstActivityInfo(HttpServletRequest request) {
    ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();
    String transactType = "";
    String directSend = "0";
    String selectUser = "", selectUserName = "", passroundUser = "", passroundUserName = "";
    String activityIds = "";
    WorkflowCommon wfc = new WorkflowCommon();
    ActivityVO activityVO = wfc.getFirstActivityInfo(request);
    if (activityVO != null && activityVO.getId() != 0L && 
      activityVO.getActivityClass() == 2) {
      if ("1".equals(activityVO.getIsBranch())) {
        map.put("firstActIsBranch", "1");
        map.put("firstCurActivityId", String.valueOf(activityVO.getId()));
        return map;
      } 
      activityVO = wfc.getNextActivity((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), request);
    } 
    if (!"0".equals(request.getParameter("processType")) && activityVO != null)
      activityVO.getId(); 
    if (activityVO != null && activityVO.getId() != 0L) {
      int participantType = activityVO.getParticipantType();
      transactType = activityVO.getTransactType();
      String[] activityUserInfo = getFirstActivityUserInfo(request, activityVO);
      directSend = activityUserInfo[0];
      selectUser = activityUserInfo[1];
      selectUserName = activityUserInfo[2];
      transactType = activityUserInfo[3];
      passroundUser = activityUserInfo[4];
      passroundUserName = activityUserInfo[5];
      activityIds = "," + activityVO.getId();
      String allowAutoCheck = activityVO.getAllowAutoCheck();
      if ("1".equals(allowAutoCheck) && "".equals(selectUser))
        if ("1".equals(directSend))
          "".equals(selectUser);  
    } 
    if (map.get("nextActivityVO") == null) {
      map.put("directSend", directSend);
      map.put("nextActivityVO", activityVO);
      map.put("transactType", transactType);
      map.put("selectUser", (selectUser == null) ? "" : selectUser);
      map.put("selectUserName", (selectUserName == null) ? "" : selectUserName);
      map.put("activityIds", activityIds);
      map.put("passroundUser", passroundUser);
      map.put("passroundUserName", passroundUserName);
    } 
    return map;
  }
  
  public ConcurrentHashMap getNextActivityInfo(HttpServletRequest request, String curActivityId, String tableId, String recordId) {
    ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();
    String standForUserId = request.getParameter("standForUserId");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    ActivityVO activityVO = null;
    String directSend = "0";
    String selectUser = "", selectUserName = "", passroundUser = "", passroundUserName = "";
    String transactType = "";
    String activityIds = "";
    List<ActivityVO> activityList = new ArrayList();
    String stepType = workFlowBD.getActivityType(curActivityId, tableId, recordId);
    String activityClass = workFlowBD.getNextActivityClass(curActivityId, tableId, recordId, "");
    if (!activityClass.equals("0")) {
      activityVO = (new ProcessStep()).getProceedNextActi(activityClass, tableId, recordId, request);
      if (activityVO != null) {
        int participantType = activityVO.getParticipantType();
        transactType = activityVO.getTransactType();
        if (activityVO.getActivityBeginEnd() == 2) {
          activityVO.setId(2L);
          directSend = "1";
        } else {
          String[] activityUserInfo = getMiddleActivityUserInfoWithCon(request, activityVO, standForUserId, curActivityId, tableId, recordId);
          directSend = activityUserInfo[0];
          selectUser = activityUserInfo[1];
          selectUserName = activityUserInfo[2];
          transactType = activityUserInfo[3];
          activityIds = "," + activityVO.getId();
          String allowAutoCheck = activityVO.getAllowAutoCheck();
          if ("1".equals(allowAutoCheck) && "".equals(selectUser))
            map = getNextActivityInfo(request, String.valueOf(activityVO.getId()), tableId, recordId); 
        } 
      } 
    } else if (stepType.equals("0")) {
      activityVO = (new ProcessStep()).getProceedNextActi(curActivityId, tableId, recordId, request);
      if (activityVO != null) {
        activityVO.setBeginEnd(activityVO.getActivityBeginEnd());
        activityList.add(activityVO);
      } 
    } else {
      activityList = workFlowBD.getAllNextActivity(tableId, recordId, curActivityId);
      if (activityList.size() != 0)
        if (activityList.size() == 1) {
          activityVO = activityList.get(0);
          activityVO = workFlowBD.getFirstProcActiVO(String.valueOf(activityVO.getId()));
          transactType = activityVO.getTransactType();
          int participantType = activityVO.getParticipantType();
          if (2 != activityVO.getActivityBeginEnd()) {
            String[] activityUserInfo = getMiddleActivityUserInfo(request, activityVO, standForUserId, curActivityId, tableId, recordId);
            directSend = activityUserInfo[0];
            selectUser = activityUserInfo[1];
            selectUserName = activityUserInfo[2];
            transactType = activityUserInfo[3];
            passroundUser = activityUserInfo[4];
            passroundUserName = activityUserInfo[5];
            activityIds = "," + activityVO.getId();
          } else if (2 == activityVO.getActivityBeginEnd()) {
            transactType = "end";
          } 
          String allowAutoCheck = activityVO.getAllowAutoCheck();
          if (1 == participantType) {
            selectUser = "yes";
          } else if (8 == participantType) {
            ManagerService ms = new ManagerService();
            List<Object[]> userList = ms.getUsersByRange(activityVO.getParticipantGivenOrg());
            if (userList != null && userList.size() > 0) {
              Object[] obj = (Object[])null;
              for (int i = 0; i < userList.size(); i++) {
                obj = userList.get(i);
                selectUser = String.valueOf(selectUser) + obj[0];
                selectUserName = String.valueOf(selectUserName) + obj[1];
              } 
            } 
          } 
          if ("1".equals(allowAutoCheck) && "".equals(selectUser) && 2 != activityVO.getActivityBeginEnd())
            map = getNextActivityInfo(request, String.valueOf(activityVO.getId()), tableId, recordId); 
        }  
    } 
    if (map.get("nextActivityVO") == null && activityVO != null) {
      map.put("directSend", directSend);
      map.put("nextActivityVO", activityVO);
      map.put("transactType", transactType);
      map.put("selectUser", selectUser);
      map.put("selectUserName", selectUserName);
      map.put("activityIds", activityIds);
      map.put("passroundUser", passroundUser);
      map.put("passroundUserName", passroundUserName);
    } 
    return map;
  }
  
  private String[] getFirstActivityUserInfo(HttpServletRequest request, ActivityVO activityVO) {
    int i;
    String participantUserField, fieldShow, thisDocProcess;
    List<Object[]> list1, leaderList;
    String[] activityUserInfo = { "", "", "", "", "", "" };
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String directSend = "0";
    String selectUser = "", selectUserName = "", passroundUser = "", passroundUserName = "";
    List<Object[]> candidate = activityVO.getParticipantUser();
    int participantType = activityVO.getParticipantType();
    String transactType = activityVO.getTransactType();
    switch (participantType) {
      case 3:
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (transactType.length() == 1) {
          directSend = "1";
          break;
        } 
        if (transactType.length() > 1 && i == 1) {
          directSend = "1";
          transactType = transactType.substring(0, 1);
        } 
        break;
      case 2:
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (i == 1) {
          if (selectUser.indexOf(",") != -1) {
            selectUser = selectUser.substring(0, selectUser.length() - 1);
            if (selectUserName.endsWith(","))
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1); 
          } 
          if (transactType.length() == 1) {
            directSend = "1";
            break;
          } 
          if (transactType.length() > 1) {
            directSend = "1";
            transactType = transactType.substring(0, 1);
          } 
        } 
        break;
      case 4:
        participantUserField = activityVO.getParticipantUserField();
        fieldShow = workFlowButtonBD.getFieldShowByFieldId(participantUserField);
        participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
        thisDocProcess = (request.getParameter("thisDocProcess") == null) ? 
          "" : request.getParameter("thisDocProcess");
        if ("215".equals(fieldShow)) {
          selectUser = "";
          selectUserName = "";
          String empNumber = request.getParameter(participantUserField).replace("，", ",");
          if (empNumber != null && !"".equals(empNumber) && !"null".equalsIgnoreCase(empNumber)) {
            String[] empNum = empNumber.split(",");
            for (int n = 0; n < empNum.length; n++) {
              String[] empInfo = StaticParam.getEmpInfoByEmpId(empNum[n]);
              selectUser = String.valueOf(selectUser) + empInfo[0] + ",";
              selectUserName = String.valueOf(selectUserName) + empInfo[1] + ",";
            } 
          } 
        } else if ("1".equals(thisDocProcess)) {
          selectUser = request.getParameter("sendFieldSelectMoreEmp");
          selectUserName = request.getParameter("sendFieldSelectMoreEmp_name");
        } else if ("2".equals(thisDocProcess)) {
          selectUser = request.getParameter("receiveFieldSelectMoreEmp");
          selectUserName = request.getParameter("receiveFieldSelectMoreEmp_name");
        } else {
          selectUser = request.getParameter(String.valueOf(participantUserField) + "_Id");
          selectUserName = request.getParameter(String.valueOf(participantUserField) + "_Name");
        } 
        if (transactType.length() == 1) {
          directSend = "1";
          break;
        } 
        if (transactType.length() > 1) {
          directSend = "1";
          transactType = transactType.substring(0, 1);
        } 
        break;
      case 7:
        list1 = (new WorkFlowBD()).getLeaderList(request.getSession().getAttribute("userId").toString());
        for (i = 0; i < list1.size(); i++) {
          Object[] tmp = list1.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 0:
        leaderList = (new WorkFlowBD()).getLeaderList(request.getSession().getAttribute("userId").toString());
        for (i = 0; i < leaderList.size(); i++) {
          Object[] tmp = leaderList.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 5:
        selectUser = request.getSession().getAttribute("userId").toString();
        selectUserName = request.getSession().getAttribute("userName").toString();
        if (transactType.length() > 1)
          transactType = transactType.substring(0, 1); 
        directSend = "1";
        break;
      case 6:
        candidate = workFlowBD.getRoleUserIDAndName(activityVO.getPartRole(), request.getSession().getAttribute("userId").toString());
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 14:
        candidate = workFlowButtonBD.getLeaderByDutyLevelAndOrg(request.getSession().getAttribute("userId").toString(), activityVO.getPartRole());
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 15:
        candidate = workFlowBD.getPositionUserIDAndName(activityVO.getPartRole(), request.getSession().getAttribute("userId").toString());
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
    } 
    if ("1".equals(directSend)) {
      List<Object[]> passroundList = activityVO.getPassRoundUser();
      int passroundType = activityVO.getPassRoundUserType();
      if (passroundType == 0 || 3 == passroundType || 5 == passroundType || 7 == passroundType || 10 == passroundType || 11 == passroundType) {
        for (i = 0; i < passroundList.size(); i++) {
          Object[] tmp = passroundList.get(i);
          passroundUser = String.valueOf(passroundUser) + tmp[0] + ",";
          passroundUserName = String.valueOf(passroundUserName) + tmp[1] + ",";
        } 
        if (passroundUser.indexOf(",") != -1) {
          passroundUser = passroundUser.substring(0, passroundUser.length() - 1);
          passroundUserName = passroundUserName.substring(0, passroundUserName.length() - 1);
        } 
      } else if (4 == passroundType) {
        String passRoundUserField = activityVO.getPassRoundUserField();
        String str1 = workFlowButtonBD.getFieldShowByFieldId(passRoundUserField);
        passRoundUserField = workFlowButtonBD.getFieldInfoByFieldId(passRoundUserField);
        String str2 = (request.getParameter("thisDocProcess") == null) ? "" : request.getParameter("thisDocProcess");
        if ("215".equals(str1)) {
          selectUser = "";
          selectUserName = "";
          String empNumber = request.getParameter(passRoundUserField).replace("，", ",");
          if (empNumber != null && !"".equals(empNumber) && !"null".equalsIgnoreCase(empNumber)) {
            String[] empNum = empNumber.split(",");
            for (int n = 0; n < empNum.length; n++) {
              String[] empInfo = StaticParam.getEmpInfoByEmpId(empNum[n]);
              passroundUser = String.valueOf(passroundUser) + empInfo[0] + ",";
              passroundUserName = String.valueOf(passroundUserName) + empInfo[1] + ",";
            } 
          } 
        } else if ("1".equals(str2)) {
          passroundUser = request.getParameter("sendFieldSelectMoreEmp");
          passroundUserName = request.getParameter("sendFieldSelectMoreEmp_name");
        } else if ("2".equals(str2)) {
          passroundUser = request.getParameter("receiveFieldSelectMoreEmp");
          passroundUserName = request.getParameter("receiveFieldSelectMoreEmp_name");
        } else {
          passroundUser = request.getParameter(String.valueOf(passRoundUserField) + "_Id");
          passroundUserName = request.getParameter(String.valueOf(passRoundUserField) + "_Name");
        } 
      } 
    } 
    activityUserInfo[0] = directSend;
    activityUserInfo[1] = selectUser;
    activityUserInfo[2] = selectUserName;
    activityUserInfo[3] = transactType;
    activityUserInfo[4] = passroundUser;
    activityUserInfo[5] = passroundUserName;
    return activityUserInfo;
  }
  
  private String[] getMiddleActivityUserInfoWithCon(HttpServletRequest request, ActivityVO activityVO, String standForUserId, String curActivityId, String tableId, String recordId) {
    int i;
    String participantUserField, fieldShow;
    List<Object[]> list1, leaderList;
    String currentUserId, activityUserInfo[] = { "", "", "", "" };
    String directSend = "0";
    String selectUser = "", selectUserName = "";
    List<Object[]> candidate = activityVO.getParticipantUser();
    int participantType = activityVO.getParticipantType();
    String transactType = activityVO.getTransactType();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] nextUser = workFlowBD.getProcActiUser(tableId, recordId, String.valueOf(activityVO.getId()));
    switch (participantType) {
      case 3:
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (transactType.length() == 1) {
          directSend = "1";
          break;
        } 
        if (transactType.length() > 1 && i == 1) {
          directSend = "1";
          transactType = transactType.substring(0, 1);
        } 
        break;
      case 2:
        candidate = activityVO.getParticipantUser();
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (i == 1) {
          if (selectUser.indexOf(",") != -1) {
            selectUser = selectUser.substring(0, selectUser.length() - 1);
            selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
          } 
          if (transactType.length() == 1) {
            directSend = "1";
            break;
          } 
          if (transactType.length() > 1) {
            directSend = "1";
            transactType = transactType.substring(0, 1);
          } 
        } 
        break;
      case 4:
        participantUserField = activityVO.getParticipantUserField();
        fieldShow = workFlowButtonBD.getFieldShowByFieldId(participantUserField);
        participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
        if ("215".equals(fieldShow)) {
          selectUser = "";
          selectUserName = "";
          String empNumber = request.getParameter(participantUserField).replace("，", ",");
          if (empNumber == null || empNumber.toUpperCase().equals("NULL"))
            empNumber = (new CustomFormBD()).getValue(participantUserField, recordId, tableId); 
          if (empNumber != null && !"".equals(empNumber) && !"null".equalsIgnoreCase(empNumber)) {
            String[] empNum = empNumber.split(",");
            for (int n = 0; n < empNum.length; n++) {
              String[] empInfo = StaticParam.getEmpInfoByEmpId(empNum[n]);
              selectUser = String.valueOf(selectUser) + "," + empInfo[0];
              selectUserName = String.valueOf(selectUserName) + "," + empInfo[1];
            } 
            selectUser = selectUser.substring(1);
            selectUserName = selectUserName.substring(1);
          } 
        } else {
          selectUser = request.getParameter(String.valueOf(participantUserField) + "_Id");
          selectUserName = request.getParameter(String.valueOf(participantUserField) + "_Name");
          if (selectUser == null || selectUser.toUpperCase().equals("NULL")) {
            selectUser = (new CustomFormBD()).getValue(participantUserField, recordId, tableId);
            String[] userArray = selectUser.split(";");
            selectUser = userArray[0];
            selectUserName = userArray[1];
          } 
        } 
        if (transactType.length() == 1) {
          directSend = "1";
          break;
        } 
        if (transactType.length() > 1) {
          directSend = "1";
          transactType = transactType.substring(0, 1);
        } 
        break;
      case 7:
        if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
          String tranFromPersonId = "";
          if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))) {
            tranFromPersonId = request.getParameter("tranFromPersonId");
            Connection conn = null;
            try {
              conn = (new DataSourceBase()).getDataSource().getConnection();
              Statement stmt = conn.createStatement();
              String tranFromPersonIdTemp = tranFromPersonId;
              while (!"".equals(tranFromPersonId)) {
                ResultSet rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                if (rs.next()) {
                  tranFromPersonId = rs.getString(1);
                } else {
                  tranFromPersonId = "";
                } 
                rs.close();
                if (tranFromPersonId == null || "null".equals(tranFromPersonId) || "".equals(tranFromPersonId)) {
                  tranFromPersonId = "";
                  continue;
                } 
                tranFromPersonIdTemp = tranFromPersonId;
              } 
              stmt.close();
              conn.close();
              tranFromPersonId = tranFromPersonIdTemp;
            } catch (Exception err) {
              if (conn != null)
                try {
                  conn.close();
                } catch (Exception ex) {
                  ex.printStackTrace();
                }  
              err.printStackTrace();
            } 
          } 
          if (!"".equals(tranFromPersonId)) {
            list1 = workFlowBD.getLeaderList(tranFromPersonId);
          } else {
            list1 = workFlowBD.getLeaderList(request.getSession().getAttribute("userId").toString());
          } 
        } else {
          list1 = workFlowBD.getLeaderList(standForUserId);
        } 
        for (i = 0; i < list1.size(); i++) {
          Object[] tmp = list1.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 0:
        leaderList = workFlowBD.getLeaderList(request.getParameter("submitPersonId"));
        for (i = 0; i < leaderList.size(); i++) {
          Object[] tmp = leaderList.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 5:
        selectUser = request.getParameter("submitPersonId");
        selectUserName = (new UserBD()).getUserNameById(request.getParameter("submitPersonId"));
        if (transactType.length() > 1)
          transactType = transactType.substring(0, 1); 
        directSend = "1";
        break;
      case 6:
        currentUserId = request.getSession().getAttribute("userId").toString();
        if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
          String tranFromPersonId = "";
          if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))) {
            tranFromPersonId = request.getParameter("tranFromPersonId");
            String tranFromPersonIdTemp = tranFromPersonId;
            Connection conn = null;
            try {
              conn = (new DataSourceBase()).getDataSource().getConnection();
              Statement stmt = conn.createStatement();
              while (!"".equals(tranFromPersonId)) {
                ResultSet rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                if (rs.next()) {
                  tranFromPersonId = rs.getString(1);
                } else {
                  tranFromPersonId = "";
                } 
                rs.close();
                if (tranFromPersonId == null || "null".equals(tranFromPersonId) || "".equals(tranFromPersonId)) {
                  tranFromPersonId = "";
                  continue;
                } 
                tranFromPersonIdTemp = tranFromPersonId;
              } 
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
            tranFromPersonId = tranFromPersonIdTemp;
          } 
          if (!"".equals(tranFromPersonId))
            currentUserId = tranFromPersonId; 
        } else {
          currentUserId = standForUserId;
        } 
        candidate = workFlowBD.getRoleUserIDAndName(nextUser[18], String.valueOf(request.getParameter("submitPersonId")) + ";" + currentUserId);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 14:
        candidate = workFlowButtonBD.getLeaderByDutyLevelAndOrg(request.getParameter("submitPersonId"), nextUser[18]);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 15:
        currentUserId = request.getSession().getAttribute("userId").toString();
        if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
          String tranFromPersonId = "";
          if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))) {
            tranFromPersonId = request.getParameter("tranFromPersonId");
            String tranFromPersonIdTemp = tranFromPersonId;
            Connection conn = null;
            try {
              conn = (new DataSourceBase()).getDataSource().getConnection();
              Statement stmt = conn.createStatement();
              while (!"".equals(tranFromPersonId)) {
                ResultSet rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                if (rs.next()) {
                  tranFromPersonId = rs.getString(1);
                } else {
                  tranFromPersonId = "";
                } 
                rs.close();
                if (tranFromPersonId == null || "null".equals(tranFromPersonId) || "".equals(tranFromPersonId)) {
                  tranFromPersonId = "";
                  continue;
                } 
                tranFromPersonIdTemp = tranFromPersonId;
              } 
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
            tranFromPersonId = tranFromPersonIdTemp;
          } 
          if (!"".equals(tranFromPersonId))
            currentUserId = tranFromPersonId; 
        } else {
          currentUserId = standForUserId;
        } 
        candidate = workFlowBD.getPositionUserIDAndName(nextUser[18], String.valueOf(request.getParameter("submitPersonId")) + ";" + currentUserId);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
    } 
    activityUserInfo[0] = directSend;
    activityUserInfo[1] = selectUser;
    activityUserInfo[2] = selectUserName;
    activityUserInfo[3] = transactType;
    return activityUserInfo;
  }
  
  private String[] getMiddleActivityUserInfo(HttpServletRequest request, ActivityVO activityVO, String standForUserId, String curActivityId, String tableId, String recordId) {
    int i;
    String participantUserField, fieldShow, thisDocProcess;
    List<Object[]> list1, leaderList;
    String currentUserId, activityUserInfo[] = { "", "", "", "", "", "" };
    String directSend = "0";
    String selectUser = "", selectUserName = "", passroundUser = "", passroundUserName = "";
    List<Object[]> candidate = activityVO.getParticipantUser();
    int participantType = activityVO.getParticipantType();
    String transactType = activityVO.getTransactType();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] nextUser = workFlowBD.getProcActiUser(tableId, recordId, String.valueOf(activityVO.getId()));
    switch (participantType) {
      case 3:
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (transactType.length() == 1) {
          directSend = "1";
          break;
        } 
        if (transactType.length() > 1 && i == 1) {
          directSend = "1";
          transactType = transactType.substring(0, 1);
        } 
        break;
      case 2:
        candidate = activityVO.getParticipantUser();
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (i == 1) {
          if (selectUser.indexOf(",") != -1) {
            selectUser = selectUser.substring(0, selectUser.length() - 1);
            selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
          } 
          if (transactType.length() == 1) {
            directSend = "1";
            break;
          } 
          if (transactType.length() > 1) {
            directSend = "1";
            transactType = transactType.substring(0, 1);
          } 
        } 
        break;
      case 4:
        participantUserField = activityVO.getParticipantUserField();
        fieldShow = workFlowButtonBD.getFieldShowByFieldId(participantUserField);
        participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
        thisDocProcess = (request.getParameter("thisDocProcess") == null) ? 
          "" : request.getParameter("thisDocProcess");
        if ("215".equals(fieldShow)) {
          selectUser = "";
          selectUserName = "";
          String empNumber = request.getParameter(participantUserField).replace("，", ",");
          if (empNumber == null || empNumber.toUpperCase().equals("NULL"))
            empNumber = (new CustomFormBD()).getValue(participantUserField, recordId, tableId); 
          if (empNumber != null && !"".equals(empNumber) && !"null".equalsIgnoreCase(empNumber)) {
            String[] empNum = empNumber.split(",");
            for (int n = 0; n < empNum.length; n++) {
              String[] empInfo = StaticParam.getEmpInfoByEmpId(empNum[n]);
              selectUser = String.valueOf(selectUser) + "," + empInfo[0];
              selectUserName = String.valueOf(selectUserName) + "," + empInfo[1];
            } 
            selectUser = selectUser.substring(1);
            selectUserName = selectUserName.substring(1);
          } 
        } else if (thisDocProcess.equals("1")) {
          selectUser = request.getParameter("sendFieldSelectMoreEmp");
          selectUserName = request.getParameter("sendFieldSelectMoreEmp_name");
        } else if ("2".equals(thisDocProcess)) {
          selectUser = request.getParameter("receiveFieldSelectMoreEmp");
          selectUserName = request.getParameter("receiveFieldSelectMoreEmp_name");
        } else {
          selectUser = request.getParameter(String.valueOf(participantUserField) + "_Id");
          selectUserName = request.getParameter(String.valueOf(participantUserField) + "_Name");
          if (selectUser == null || selectUser.toUpperCase().equals("NULL")) {
            selectUser = (new CustomFormBD()).getValue(participantUserField, recordId, tableId);
            String[] userArray = selectUser.split(";");
            selectUser = userArray[1];
            selectUserName = userArray[0];
          } 
        } 
        if (transactType.length() == 1) {
          directSend = "1";
          break;
        } 
        if (transactType.length() > 1) {
          directSend = "1";
          transactType = transactType.substring(0, 1);
        } 
        break;
      case 7:
        if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
          String tranFromPersonId = "";
          if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))) {
            tranFromPersonId = request.getParameter("tranFromPersonId");
            Connection conn = null;
            try {
              conn = (new DataSourceBase()).getDataSource().getConnection();
              Statement stmt = conn.createStatement();
              String tranFromPersonIdTemp = tranFromPersonId;
              while (!"".equals(tranFromPersonId)) {
                ResultSet rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                if (rs.next()) {
                  tranFromPersonId = rs.getString(1);
                } else {
                  tranFromPersonId = "";
                } 
                rs.close();
                if (tranFromPersonId == null || "null".equals(tranFromPersonId) || "".equals(tranFromPersonId)) {
                  tranFromPersonId = "";
                  continue;
                } 
                tranFromPersonIdTemp = tranFromPersonId;
              } 
              stmt.close();
              conn.close();
              tranFromPersonId = tranFromPersonIdTemp;
            } catch (Exception err) {
              if (conn != null)
                try {
                  conn.close();
                } catch (Exception ex) {
                  ex.printStackTrace();
                }  
              err.printStackTrace();
            } 
          } 
          if (!"".equals(tranFromPersonId)) {
            list1 = workFlowBD.getLeaderList(tranFromPersonId);
          } else {
            list1 = workFlowBD.getLeaderList(request.getSession().getAttribute("userId").toString());
          } 
        } else {
          list1 = workFlowBD.getLeaderList(standForUserId);
        } 
        for (i = 0; i < list1.size(); i++) {
          Object[] tmp = list1.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 0:
        leaderList = workFlowBD.getLeaderList(request.getParameter("submitPersonId"));
        for (i = 0; i < leaderList.size(); i++) {
          Object[] tmp = leaderList.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 5:
        selectUser = request.getParameter("submitPersonId");
        selectUserName = (new UserBD()).getUserNameById(request.getParameter("submitPersonId"));
        if (transactType.length() > 1)
          transactType = transactType.substring(0, 1); 
        directSend = "1";
        break;
      case 6:
        currentUserId = request.getSession().getAttribute("userId").toString();
        if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
          String tranFromPersonId = "";
          if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))) {
            tranFromPersonId = request.getParameter("tranFromPersonId");
            String tranFromPersonIdTemp = tranFromPersonId;
            Connection conn = null;
            try {
              conn = (new DataSourceBase()).getDataSource().getConnection();
              Statement stmt = conn.createStatement();
              while (!"".equals(tranFromPersonId)) {
                ResultSet rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                if (rs.next()) {
                  tranFromPersonId = rs.getString(1);
                } else {
                  tranFromPersonId = "";
                } 
                rs.close();
                if (tranFromPersonId == null || "null".equals(tranFromPersonId) || "".equals(tranFromPersonId)) {
                  tranFromPersonId = "";
                  continue;
                } 
                tranFromPersonIdTemp = tranFromPersonId;
              } 
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
            tranFromPersonId = tranFromPersonIdTemp;
          } 
          if (!"".equals(tranFromPersonId))
            currentUserId = tranFromPersonId; 
        } else {
          currentUserId = standForUserId;
        } 
        candidate = workFlowBD.getRoleUserIDAndName(nextUser[18], String.valueOf(request.getParameter("submitPersonId")) + ";" + currentUserId);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
      case 14:
        candidate = workFlowButtonBD.getLeaderByDutyLevelAndOrg(request.getParameter("submitPersonId"), nextUser[18]);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 15:
        currentUserId = request.getSession().getAttribute("userId").toString();
        if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
          String tranFromPersonId = "";
          if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))) {
            tranFromPersonId = request.getParameter("tranFromPersonId");
            String tranFromPersonIdTemp = tranFromPersonId;
            Connection conn = null;
            try {
              conn = (new DataSourceBase()).getDataSource().getConnection();
              Statement stmt = conn.createStatement();
              int ii = 0;
              while (!"".equals(tranFromPersonId) && ii < 10) {
                ii++;
                ResultSet rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                if (rs.next()) {
                  tranFromPersonId = rs.getString(1);
                } else {
                  tranFromPersonId = "";
                } 
                rs.close();
                if (tranFromPersonId == null || "null".equals(tranFromPersonId) || "".equals(tranFromPersonId)) {
                  tranFromPersonId = "";
                  continue;
                } 
                tranFromPersonIdTemp = tranFromPersonId;
              } 
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
            tranFromPersonId = tranFromPersonIdTemp;
          } 
          if (!"".equals(tranFromPersonId))
            currentUserId = tranFromPersonId; 
        } else {
          currentUserId = standForUserId;
        } 
        candidate = workFlowBD.getPositionUserIDAndName(nextUser[18], String.valueOf(request.getParameter("submitPersonId")) + ";" + currentUserId);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (i == 1) {
          directSend = "1";
          if (transactType.length() > 1)
            transactType = transactType.substring(0, 1); 
        } 
        break;
    } 
    if ("1".equals(directSend)) {
      List<Object[]> passroundList = activityVO.getPassRoundUser();
      int passroundType = activityVO.getPassRoundUserType();
      if (passroundType == 0 || 3 == passroundType || 5 == passroundType || 7 == passroundType || 10 == passroundType || 11 == passroundType) {
        for (i = 0; i < passroundList.size(); i++) {
          Object[] tmp = passroundList.get(i);
          passroundUser = String.valueOf(passroundUser) + tmp[0] + ",";
          passroundUserName = String.valueOf(passroundUserName) + tmp[1] + ",";
        } 
        if (passroundUser.indexOf(",") != -1) {
          passroundUser = passroundUser.substring(0, passroundUser.length() - 1);
          passroundUserName = passroundUserName.substring(0, passroundUserName.length() - 1);
        } 
      } else if (4 == passroundType) {
        String passRoundUserField = activityVO.getPassRoundUserField();
        String str1 = workFlowButtonBD.getFieldShowByFieldId(passRoundUserField);
        passRoundUserField = workFlowButtonBD.getFieldInfoByFieldId(passRoundUserField);
        String str2 = (request.getParameter("thisDocProcess") == null) ? "" : request.getParameter("thisDocProcess");
        if ("215".equals(str1)) {
          String empNumber = request.getParameter(passRoundUserField).replace("，", ",");
          if (empNumber == null || empNumber.toUpperCase().equals("NULL"))
            empNumber = (new CustomFormBD()).getValue(passRoundUserField, recordId, tableId); 
          if (empNumber != null && !"".equals(empNumber) && !"null".equalsIgnoreCase(empNumber)) {
            String[] empNum = empNumber.split(",");
            for (int n = 0; n < empNum.length; n++) {
              String[] empInfo = StaticParam.getEmpInfoByEmpId(empNum[n]);
              passroundUser = String.valueOf(passroundUser) + "," + empInfo[0];
              passroundUserName = String.valueOf(passroundUserName) + "," + empInfo[1];
            } 
            passroundUser = selectUser.substring(1);
            passroundUser = selectUserName.substring(1);
          } 
        } else if (str2.equals("1")) {
          passroundUser = request.getParameter("sendFieldSelectMoreEmp");
          passroundUserName = request.getParameter("sendFieldSelectMoreEmp_name");
        } else if ("2".equals(str2)) {
          passroundUser = request.getParameter("receiveFieldSelectMoreEmp");
          passroundUserName = request.getParameter("receiveFieldSelectMoreEmp_name");
        } else {
          passroundUser = request.getParameter(String.valueOf(passRoundUserField) + "_Id");
          passroundUserName = request.getParameter(String.valueOf(passRoundUserField) + "_Name");
          if (passroundUser == null || passroundUser.toUpperCase().equals("NULL")) {
            passroundUser = (new CustomFormBD()).getValue(passRoundUserField, recordId, tableId);
            String[] userArray = passroundUser.split(";");
            passroundUser = userArray[0];
            passroundUserName = userArray[1];
          } 
        } 
      } 
    } 
    activityUserInfo[0] = directSend;
    activityUserInfo[1] = selectUser;
    activityUserInfo[2] = selectUserName;
    activityUserInfo[3] = transactType;
    activityUserInfo[4] = passroundUser;
    activityUserInfo[5] = passroundUserName;
    return activityUserInfo;
  }
}
