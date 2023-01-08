package com.js.oa.security.log.service;

import com.js.oa.security.log.bean.LogCountEJBBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LogCountBD {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public List getOrgLog(List<Object[]> list, String beginDate, String endDate, String level, Integer num) {
    LogCountEJBBean bean = new LogCountEJBBean();
    List<Object[]> rList = new ArrayList();
    try {
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        int empNum = 0;
        int dayNum = 0;
        Object object = obj[num.intValue() + 3];
        int orgNum = 0;
        String sql = "";
        if ("".equals(level)) {
          sql = "SELECT e.emp_id FROM org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id where o.org_id=" + 
            object + " AND e.USERISACTIVE=1 AND e.USERISDELETED=0";
        } else {
          sql = "SELECT e.emp_id FROM org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id WHERE o.orgIdString LIKE '%$" + 
            object + "$%'" + " AND e.USERISACTIVE=1 AND e.USERISDELETED=0";
        } 
        String empIds = bean.getOrgUserInfo(sql);
        orgNum = (empIds.split(",")).length;
        Date bDate = this.ymd.parse(beginDate);
        Date eDate = this.ymd.parse(endDate);
        Map<String, Integer> empLogMap = bean.getLogEmpNum(empIds, beginDate, endDate);
        for (long l = bDate.getTime(); l <= eDate.getTime(); l += 86400000L) {
          int empLogNum = (empLogMap.get(this.ymd.format(new Date(l))) == null) ? 0 : ((Integer)empLogMap.get(this.ymd.format(new Date(l)))).intValue();
          empNum = empLogNum + empNum;
          obj[dayNum + 1] = (new StringBuilder(String.valueOf(empLogNum))).toString();
          dayNum++;
        } 
        obj[dayNum + 1] = (new StringBuilder(String.valueOf(empNum / dayNum))).toString();
        obj[dayNum + 2] = (String.valueOf(String.format("%.2f", new Object[] { Float.valueOf((empNum / dayNum) / orgNum * 100.0F) })) + "%").replace(".0%", "%");
        rList.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return rList;
  }
  
  public List getLogin(List<Object[]> list, String beginDate, String endDate) {
    List<Object[]> rList = new ArrayList();
    LogCountEJBBean bean = new LogCountEJBBean();
    String empIds = "-1";
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      empIds = String.valueOf(empIds) + "," + obj[3];
    } 
    Map<String, String> loginMap = bean.getLoginMap(empIds, beginDate, endDate);
    Map<String, String> logoutMap = bean.getLogoutMap(empIds, beginDate, endDate);
    for (int j = 0; j < list.size(); j++) {
      Object[] obj = list.get(j);
      obj[1] = (loginMap.get(obj[3]) == null) ? "" : loginMap.get(obj[3]);
      obj[2] = (logoutMap.get(obj[3]) == null) ? "" : logoutMap.get(obj[3]);
      rList.add(obj);
    } 
    list.clear();
    return rList;
  }
  
  public List getDownloadAccount(List<Object[]> list, String beginDate, String endDate, String empName, String searchTime) {
    LogCountEJBBean bean = new LogCountEJBBean();
    List<Object[]> rList = new ArrayList();
    if (!searchTime.equals("1")) {
      if (empName.trim() == null || empName.trim().equals("")) {
        try {
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            Object object = obj[2];
            String[] temp = bean.getDownloadCount((String)object);
            obj[2] = temp[0];
            obj[3] = temp[1];
            obj[4] = temp[2];
            obj[5] = temp[3];
            obj[6] = temp[4];
            rList.add(obj);
          } 
        } catch (Exception exception) {}
      } else {
        try {
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            Object object = obj[2];
            String[] temp = bean.getDownloadCount((String)object);
            obj[2] = temp[0];
            obj[3] = temp[1];
            obj[4] = temp[2];
            obj[5] = temp[3];
            obj[6] = temp[4];
            rList.add(obj);
          } 
        } catch (Exception exception) {}
      } 
    } else {
      try {
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          Object object = obj[2];
          String temp = bean.getDownloadCount((String)object, beginDate, endDate);
          obj[2] = temp;
          rList.add(obj);
        } 
      } catch (Exception exception) {}
    } 
    return rList;
  }
}
