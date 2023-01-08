package com.js.oa.jsflow.util;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.personalwork.setup.service.MyInfoBD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowSync {
  public String getUserStatus(String[] userId) {
    MyInfoBD myInfoBD = new MyInfoBD();
    List<Object[]> list = myInfoBD.getEmpStatusByEmpIdArr(userId);
    StringBuffer result = new StringBuffer();
    if (list != null) {
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        result.append(obj[0] + " [" + obj[1] + "]、");
      } 
    } 
    String tmp = result.toString();
    if (tmp.endsWith("、"))
      tmp = tmp.substring(0, tmp.length() - 1); 
    return tmp;
  }
  
  public String getLeaderByOrgId(String operProcOrg) {
    WorkFlowBD bd = new WorkFlowBD();
    MyInfoBD myInfoBD = new MyInfoBD();
    String[] leader = bd.getLeaderListByOrgId(operProcOrg);
    String tmp = "noleader";
    if (leader.length == 0)
      return tmp; 
    List<Object[]> list = myInfoBD.getEmpStatusByEmpIdArr(leader);
    StringBuffer result = new StringBuffer();
    Object[] obj = (Object[])null;
    for (int i = 0; i < list.size(); i++) {
      obj = list.get(i);
      result.append(obj[0] + " [" + obj[1] + "]、");
    } 
    tmp = result.toString();
    if (tmp.endsWith("、"))
      tmp = tmp.substring(0, tmp.length() - 1); 
    return tmp;
  }
  
  public String getUserStatusByEmpIdStr(String userIds) {
    MyInfoBD myInfoBD = new MyInfoBD();
    if (userIds.endsWith(","))
      userIds = userIds.substring(0, userIds.length() - 1); 
    List<Object[]> list = myInfoBD.getEmpStatusByEmpIdStr(userIds);
    StringBuffer result = new StringBuffer();
    if (list != null) {
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        result.append(obj[0] + " [" + obj[1] + "]、");
      } 
    } 
    String tmp = result.toString();
    if (tmp.endsWith("、"))
      tmp = tmp.substring(0, tmp.length() - 1); 
    return tmp;
  }
  
  public String getUserStatusByEmpOrgGrp(String empIdStr, String orgIdStr, String grpIdStr) {
    MyInfoBD myInfoBD = new MyInfoBD();
    List<Object[]> list = myInfoBD.getEmpStatusByEmpOrgGrp(empIdStr, orgIdStr, grpIdStr);
    StringBuffer result = new StringBuffer();
    if (list != null) {
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        result.append(obj[0] + " [" + obj[1] + "]、");
      } 
    } 
    String tmp = result.toString();
    if (tmp.endsWith("、"))
      tmp = tmp.substring(0, tmp.length() - 1); 
    return tmp;
  }
  
  public String getUserStatusByRole(String role, String userId) {
    String[] toUser = (new WorkFlowBD()).getRoleUser(role, userId);
    MyInfoBD myInfoBD = new MyInfoBD();
    List<Object[]> list = myInfoBD.getEmpStatusByEmpIdArr(toUser);
    StringBuffer result = new StringBuffer();
    if (list != null) {
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        result.append(obj[0] + " [" + obj[1] + "]、");
      } 
    } 
    String tmp = result.toString();
    if (tmp.endsWith("、"))
      tmp = tmp.substring(0, tmp.length() - 1); 
    return tmp;
  }
  
  public String getWFOnlineUser(String recordId, String tableId, String processId, String workId, String userAccounts, String pageKey) {
    String ret = "";
    try {
      WorkFlowButtonBD wfbd = new WorkFlowButtonBD();
      Map<Object, Object> para = new HashMap<Object, Object>();
      if (recordId != null && !"".equals(recordId))
        para.put("recordId", Long.valueOf(recordId)); 
      if (tableId != null && !"".equals(tableId))
        para.put("tableId", Long.valueOf(tableId)); 
      if (processId != null && !"".equals(processId))
        para.put("processId", Long.valueOf(processId)); 
      if (workId != null && !"".equals(workId))
        para.put("workId", Long.valueOf(workId)); 
      String[] userArr = wfbd.getWFOnlineUser(para);
      String onlineEditUser = userArr[0];
      String onlineEditUserName = userArr[1];
      String onlineWorkId = userArr[2];
      String onlinePageKey = userArr[3];
      if ("".equals(onlineEditUserName) || "".equals(onlineEditUser))
        return ret; 
      boolean onlineUserValidate = (new WorkFlowButtonBD()).getOnlineUserByUserAccounts(onlineEditUser);
      if (onlineEditUser.equals(userAccounts) && onlineWorkId.equals(workId) && onlinePageKey.equals(pageKey))
        onlineUserValidate = false; 
      if (onlineUserValidate)
        ret = String.valueOf(onlineEditUserName) + "(" + onlineEditUser + ")"; 
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return ret;
  }
  
  public String userCanCreateFlow(String processId, String userId, String orgId, String orgIdString) {
    ProcessBD pbd = new ProcessBD();
    return pbd.userCanCreateFlow(processId, userId, orgId, orgIdString);
  }
}
