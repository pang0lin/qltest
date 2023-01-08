package com.js.oa.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class UserDefineExample {
  public String getDefaultValue(HttpServletRequest request) {
    String defaultValue = "从用户定义类中取出的默认值";
    defaultValue = request.getSession().getAttribute("userName").toString();
    return defaultValue;
  }
  
  public List getContractData(HttpServletRequest request, String contractNO) {
    List<Map<Object, Object>> list = new ArrayList();
    System.out.println("connn:" + contractNO);
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("contractNO", contractNO);
    map.put("contractTitle", "中国石油总公司采购合同");
    map.put("contractSum", "320000");
    list.add(map);
    return list;
  }
  
  public List<Map> getContractDatas(String contractNO) {
    return getContractDatas(null, contractNO);
  }
  
  public List<Map> getContractDatas(HttpServletRequest request, String contractNO) {
    List<Map<Object, Object>> list = new ArrayList();
    try {
      Map<Object, Object> map = new HashMap<Object, Object>();
      map.put("contractNO", contractNO);
      map.put("contractTitle", "中国石油总公司采购合同");
      map.put("contractSum", "320000");
      map.put("contractSelect", "{[11:选项1],[22:选项2],[33:选项3]}");
      list.add(map);
      map = new HashMap<Object, Object>();
      map.put("contractNO", contractNO);
      map.put("contractTitle", "中国天然气总公司销售合同");
      map.put("contractSum", "6320000");
      map.put("contractSelect", "{[44:选项4],[55:选项5],[66:选项6]}");
      list.add(map);
    } catch (Exception e) {
      list.clear();
      Map<Object, Object> map = new HashMap<Object, Object>();
      map.put("error", "取值错误(此处为页面提醒信息)！");
      list.add(map);
    } 
    return (List)list;
  }
  
  public List getContractDatas(HttpServletRequest request) {
    List<Map<Object, Object>> list = new ArrayList();
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("contractNO", "no");
    map.put("contractTitle", "中国石油总公司采购合同");
    map.put("contractSum", "320000");
    list.add(map);
    map = new HashMap<Object, Object>();
    map.put("contractNO", "no");
    map.put("contractTitle", "中国天然气总公司销售合同");
    map.put("contractSum", "6320000");
    list.add(map);
    return list;
  }
  
  public List getDefaultData(HttpServletRequest request, String userId, String userName, String userAccount, String orgId, String orgName, String applyId, String applyOrgId, String formValue1, String formValue2, String tempValue) {
    List<Map<Object, Object>> list = new ArrayList();
    System.out.println("userId:" + userId);
    System.out.println("userName:" + userName);
    System.out.println("userAccount:" + userAccount);
    System.out.println("orgId:" + orgId);
    System.out.println("orgName:" + orgName);
    System.out.println("applyId:" + applyId);
    System.out.println("applyOrgId:" + applyOrgId);
    System.out.println("formValue1:" + formValue1);
    System.out.println("formValue2:" + formValue2);
    System.out.println("tempValue:" + tempValue);
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("test1", "aaa1");
    map.put("test2", "aaa2");
    map.put("test3", "aaa3");
    list.add(map);
    map = new HashMap<Object, Object>();
    map.put("test1", "bbb1");
    map.put("test2", "bbb3");
    map.put("test3", "bbb3");
    list.add(map);
    return list;
  }
}
