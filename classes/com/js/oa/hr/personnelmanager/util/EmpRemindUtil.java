package com.js.oa.hr.personnelmanager.util;

import com.js.oa.hr.personnelmanager.po.EmpRemindPO;
import com.js.oa.hr.personnelmanager.service.EmpRemindBD;
import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.ContractVO;
import com.js.system.vo.usermanager.EmployeeVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EmpRemindUtil {
  public static void weekEmpRemind() throws Exception {
    Date now = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Long todayLong = Long.valueOf(Long.parseLong(simpleDateFormat.format(now)));
    EmpRemindBD empRemindBD = new EmpRemindBD();
    MessagesBD messagesBD = new MessagesBD();
    List<EmpRemindPO> empRemindPOlList = empRemindBD.selectRemindList();
    for (int i = 0; i < empRemindPOlList.size(); i++) {
      EmpRemindPO empRemindPO = empRemindPOlList.get(i);
      Set<String> empIdSet = new HashSet<String>();
      empIdSet = getIdSet(empRemindPO.getEmpId());
      for (Iterator<String> it = empIdSet.iterator(); it.hasNext(); ) {
        String empIdStr = it.next();
        UserBD userBD = new UserBD();
        EmployeeVO employeeVO = userBD.getEmpByid(Long.valueOf(Long.parseLong(empIdStr.trim())));
        if (employeeVO != null) {
          if ("1".equals(empRemindPO.getRemindType()))
            if (employeeVO.getZhuanzhengDate() != null && 
              empRemindPO.getRemindTime() != null) {
              Long zhuanZhengDate = Long.valueOf(Long.parseLong(simpleDateFormat.format(employeeVO.getZhuanzhengDate())));
              Long resultDate = Long.valueOf(zhuanZhengDate.longValue() - Long.parseLong(empRemindPO.getRemindTime().trim()));
              if (todayLong.toString().equals(resultDate.toString())) {
                List<MessagesVO> messagelList = new ArrayList();
                Set<String> sendToIdSet = new HashSet<String>();
                sendToIdSet = getIdSet(empRemindPO.getSendToId());
                for (Iterator<String> sit = sendToIdSet.iterator(); sit.hasNext(); ) {
                  String sendToIdStr = sit.next();
                  EmployeeVO sendToeEmployeeVO = userBD.getEmpByid(Long.valueOf(Long.parseLong(sendToIdStr)));
                  if (sendToeEmployeeVO != null && sendToeEmployeeVO.getEmpName() != null) {
                    MessagesVO messagesVO = new MessagesVO();
                    messagesVO.setMessage_title("");
                    messagesVO.setMessage_date_begin(now);
                    messagesVO.setMessage_date_end(new Date("2050/1/1"));
                    messagesVO.setMessage_send_UserId(0L);
                    messagesVO.setMessage_send_UserName("系统提醒");
                    messagesVO.setMessage_show(1);
                    messagesVO.setMessage_status(1);
                    messagesVO.setMessage_time(now);
                    messagesVO.setMessage_title(String.valueOf(employeeVO.getEmpName()) + "距离转正还有" + empRemindPO.getRemindTime() + "天");
                    messagesVO.setMessage_toUserId(Long.valueOf(Long.parseLong(sendToIdStr)).longValue());
                    messagesVO.setMessage_type("EmpRemind");
                    messagesVO.setData_id(0L);
                    messagesVO.setMessage_url("/jsoa/EmployeeAction.do?action=personCard&hasRight=true&empId=" + empIdStr);
                    messagelList.add(messagesVO);
                  } 
                } 
                messagesBD.messageArrayAdd(messagelList);
              } 
            }  
          if ("2".equals(empRemindPO.getRemindType())) {
            Set contractSet = employeeVO.getContractVO();
            if (contractSet != null && !contractSet.isEmpty()) {
              Object[] contractArray = contractSet.toArray();
              ContractVO contractVO = new ContractVO();
              if (contractArray.length > 1) {
                for (int k = 0; k < contractArray.length; k++) {
                  for (int t = 0; t < contractArray.length; t++) {
                    ContractVO temp = new ContractVO();
                    ContractVO contractVOk = (ContractVO)contractArray[k];
                    ContractVO contractVOt = (ContractVO)contractArray[t];
                    if (contractVOk.getEndDate() == null)
                      contractVOk.setEndDate(new Date()); 
                    if (contractVOt.getEndDate() == null)
                      contractVOt.setEndDate(new Date()); 
                    if (Long.parseLong(simpleDateFormat.format(contractVOk.getEndDate())) < Long.parseLong(simpleDateFormat.format(contractVOt.getEndDate()))) {
                      temp = contractVOt;
                      contractVOt = contractVOk;
                      contractVOt = temp;
                    } 
                  } 
                } 
                contractVO = (ContractVO)contractArray[0];
              } else {
                contractVO = (ContractVO)contractArray[0];
              } 
              if (empRemindPO.getRemindTime() != null) {
                Long heTongDate = Long.valueOf(Long.parseLong(simpleDateFormat.format(contractVO.getEndDate())));
                Long resultDate = Long.valueOf(heTongDate.longValue() - Long.parseLong(empRemindPO.getRemindTime().trim()));
                if (todayLong.toString().equals(resultDate.toString())) {
                  List<MessagesVO> messagelList = new ArrayList();
                  Set<String> sendToIdSet = new HashSet<String>();
                  sendToIdSet = getIdSet(empRemindPO.getSendToId());
                  for (Iterator<String> sit = sendToIdSet.iterator(); sit.hasNext(); ) {
                    String sendToIdStr = sit.next();
                    EmployeeVO sendToeEmployeeVO = userBD.getEmpByid(Long.valueOf(Long.parseLong(sendToIdStr)));
                    if (sendToeEmployeeVO != null && sendToeEmployeeVO.getEmpName() != null) {
                      MessagesVO messagesVO = new MessagesVO();
                      messagesVO.setMessage_title("");
                      messagesVO.setMessage_date_begin(now);
                      messagesVO.setMessage_date_end(new Date("2050/1/1"));
                      messagesVO.setMessage_send_UserId(0L);
                      messagesVO.setMessage_send_UserName("系统提醒");
                      messagesVO.setMessage_show(1);
                      messagesVO.setMessage_status(1);
                      messagesVO.setMessage_time(now);
                      messagesVO.setMessage_title(String.valueOf(employeeVO.getEmpName()) + "距离合同到期还有" + empRemindPO.getRemindTime() + "天");
                      messagesVO.setMessage_toUserId(Long.valueOf(Long.parseLong(sendToIdStr)).longValue());
                      messagesVO.setMessage_type("EmpRemind");
                      messagesVO.setData_id(0L);
                      messagesVO.setMessage_url("/jsoa/EmployeeAction.do?action=personCard&hasRight=true&empId=" + empIdStr);
                      messagelList.add(messagesVO);
                    } 
                  } 
                  messagesBD.messageArrayAdd(messagelList);
                } 
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  private static Set<String> getIdSet(String empId) throws Exception {
    Set<String> empIdSet = new HashSet<String>();
    List<String> organizationList = new ArrayList<String>();
    List<String> groupList = new ArrayList<String>();
    List<String> personList = new ArrayList<String>();
    organizationList = getNumId(empId, "**", "*");
    groupList = getNumId(empId, "@@", "@");
    personList = getNumId(empId, "$$", "$");
    if (organizationList.size() != 0) {
      OrganizationBD organizationBD = new OrganizationBD();
      for (int j = 0; j < organizationList.size(); j++) {
        OrganizationVO organizationVO = organizationBD.getOrgByOrgId(organizationList.get(j));
        if (organizationVO.getOrgManagerEmpId() != null && organizationVO.getOrgManagerEmpId().trim() != "") {
          List<String> oTemplList = getNumId(organizationVO.getOrgManagerEmpId(), "$$", "$");
          for (int m = 0; m < oTemplList.size(); m++) {
            if (!empIdSet.contains(oTemplList.get(m)))
              empIdSet.add(oTemplList.get(m)); 
          } 
        } 
      } 
    } 
    if (groupList.size() != 0) {
      GroupBD groupBD = new GroupBD();
      for (int j = 0; j < groupList.size(); j++) {
        List<Object[]> gList = groupBD.selectSingle(groupList.get(j));
        for (int k = 0; k < gList.size(); k++) {
          Object[] groupStr = gList.get(k);
          List<String> gTemplList = getNumId(groupStr[1].toString(), "$$", "$");
          for (int m = 0; m < gTemplList.size(); m++) {
            if (!empIdSet.contains(gTemplList.get(m)))
              empIdSet.add(gTemplList.get(m)); 
          } 
        } 
      } 
    } 
    if (personList.size() != 0)
      for (int j = 0; j < personList.size(); j++) {
        if (!empIdSet.contains(personList.get(j)))
          empIdSet.add(personList.get(j)); 
      }  
    return empIdSet;
  }
  
  private static List<String> getNumId(String str, String splitStr1, String splitStr2) {
    List<String> strList = new ArrayList<String>();
    String tempStr = "";
    tempStr = str.replace(splitStr1, ",");
    tempStr = tempStr.replace(splitStr2, ",");
    String[] tempStrings = tempStr.split(",");
    for (int i = 0; i < tempStrings.length; i++) {
      boolean result = tempStrings[i].matches("[0-9]+");
      if (result)
        strList.add(tempStrings[i]); 
    } 
    return strList;
  }
}
