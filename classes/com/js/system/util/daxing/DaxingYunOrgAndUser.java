package com.js.system.util.daxing;

import com.js.system.util.OperateOrg;
import com.js.system.util.OperateUser;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.json.JsonUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class DaxingYunOrgAndUser {
  private static Map<String, String> orgMap = new HashMap<String, String>();
  
  public static void main(String[] args) {}
  
  public void tongBu() {
    if (SystemCommon.getAppUse() == 1) {
      tongBuOrg();
      tongBuTeacher();
    } 
  }
  
  private void tongBuOrg() {
    OperateOrg operateOrg = new OperateOrg();
    List<DaXingOrg> orgs = getOrgs();
    List<String> sqlList = new ArrayList<String>();
    System.out.println("部门数量：" + orgs.size());
    String orgIds = "";
    String orgNames = "";
    for (int i = 0; i < orgs.size(); i++) {
      DaXingOrg org = orgs.get(i);
      if (org.getFather_id() == null || "".equals(org.getFather_id()) || "null".equalsIgnoreCase(org.getFather_id())) {
        orgMap.put(org.getId(), String.valueOf(org.getSchool_name()) + "." + org.getDepartment_name());
      } else if (orgMap.get(org.getFather_id()) != null) {
        orgMap.put(org.getId(), String.valueOf(orgMap.get(org.getFather_id())) + "." + org.getDepartment_name());
      } else {
        orgIds = String.valueOf(orgIds) + org.getId() + ",";
        orgNames = String.valueOf(orgNames) + org.getDepartment_name() + ",";
        orgMap.put(org.getId(), org.getFather_id());
      } 
    } 
    while (orgIds.length() > 0) {
      String[] oids = orgIds.split(",");
      String[] onames = orgNames.split(",");
      orgIds = "";
      orgNames = "";
      for (int k = 0; k < oids.length; k++) {
        if (!((String)orgMap.get(orgMap.get(oids[k]))).contains(".")) {
          orgIds = String.valueOf(orgIds) + oids[k] + ",";
          orgNames = String.valueOf(orgNames) + onames[k] + ",";
        } else {
          orgMap.put(oids[k], String.valueOf(orgMap.get(orgMap.get(oids[k]))) + "." + onames[k]);
        } 
      } 
    } 
    Map<String, String[]> orgNameMap = operateOrg.getOrgInfo();
    for (int j = 0; j < orgs.size(); j++) {
      DaXingOrg org = orgs.get(j);
      org.setOrgNameString(orgMap.get(org.getId()));
      System.out.println("组织Id：" + org.getId() + "     组织名称：" + org.getDepartment_name() + "    父组织Id：" + 
          org.getFather_id() + "     所在学校Id：" + org.getSchool_id() + "     所在学校名称：" + org.getSchool_name() + 
          "     组织排序：" + org.getDep_order() + "    组织等级：" + org.getOrgNameString());
      if (orgNameMap.get(org.getOrgNameString()) == null) {
        orgNameMap = operateOrg.addOrgOrGetOrgId(org.getOrgNameString(), orgNameMap);
        sqlList.add("insert into org_org_dept (org_id,org_guid) values (" + ((String[])orgNameMap.get(org.getOrgNameString()))[0] + ",'" + org.getId() + "') ");
      } 
    } 
    (new DataSourceUtil()).executeSql(sqlList);
  }
  
  private void tongBuTeacher() {
    OperateUser ouser = new OperateUser();
    List<DaXingTeacher> teachers = getTeachers();
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    System.out.println("教师数量：" + teachers.size());
    for (int i = 0; i < teachers.size(); i++) {
      DaXingTeacher teacher = teachers.get(i);
      teacher.setZbm("f9098619a6274cdba507d963ce65d223");
      Map<String, String> map = new HashMap<String, String>();
      map.put("useraccounts", teacher.getUsername());
      map.put("empname", teacher.getName());
      map.put("empnumber", "");
      map.put("empsex", teacher.getSex());
      map.put("orgnamestring", orgMap.get(teacher.getZbm()));
      list.add(map);
      if (list.size() == 100) {
        ouser.userOperate(list, Long.valueOf((new Date()).getTime()), "1", "", "其他部门");
        list.clear();
      } 
    } 
    ouser.userOperate(list, Long.valueOf((new Date()).getTime()), "1", "", "其他部门");
  }
  
  private List<DaXingOrg> getOrgs() {
    return JsonUtil.getDTOList(getStringFromYun("department"), DaXingOrg.class);
  }
  
  private List<DaXingTeacher> getTeachers() {
    return JsonUtil.getDTOList(getStringFromYun("teacher"), DaXingTeacher.class);
  }
  
  private List<DaXingStation> getStations() {
    return JsonUtil.getDTOList(getStringFromYun("station"), DaXingStation.class);
  }
  
  private String getStringFromYun(String flag) {
    String result = "";
    String url = SystemCommon.getDaxingYun();
    url = url.replace("{标识}", flag);
    GetMethod httpMethod = new GetMethod(url);
    httpMethod.setRequestHeader("Accept", "application/json");
    String username = "lzxws";
    String password = "lzxws";
    httpMethod.setRequestHeader("Authorization", 
        "Basic " + Base64Utility.encode((String.valueOf(username) + ":" + password).getBytes()));
    HttpClient client = new HttpClient();
    try {
      int statusCode = client.executeMethod((HttpMethod)httpMethod);
      if (statusCode == 200)
        result = StringUtils.join(
            IOUtils.readLines(httpMethod.getResponseBodyAsStream(), "UTF-8"), ""); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      httpMethod.releaseConnection();
    } 
    return result;
  }
}
