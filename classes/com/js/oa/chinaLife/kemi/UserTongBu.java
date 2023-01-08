package com.js.oa.chinaLife.kemi;

import java.net.URL;
import java.util.Map;
import org.codehaus.xfire.client.Client;

public class UserTongBu {
  private Map<String, String> map = null;
  
  public boolean tongBuOrg(KemiOrg org) {
    if (this.map == null)
      this.map = KemiConfig.getKemiInfo(); 
    boolean result = false;
    try {
      Client c = new Client(new URL(this.map.get("url")));
      Object[] paras = { "sys_dept", "", org.toString(), "" };
      Object[] results = c.invoke(this.map.get("update"), paras);
      System.out.println("返回结果：" + results[0]);
      if ("1".equals(results[0]))
        result = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean tongBuUser(KemiUser user) {
    if (this.map == null)
      this.map = KemiConfig.getKemiInfo(); 
    boolean result = false;
    try {
      Client c = new Client(new URL(this.map.get("url")));
      Object[] paras = { "sys_Emp", "", user.toString(), "" };
      Object[] results = c.invoke(this.map.get("update"), paras);
      System.out.println("返回结果：" + results[0]);
      if ("1".equals(results[0]))
        result = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static void main(String[] args) {
    UserTongBu tb = new UserTongBu();
    KemiOrg org = new KemiOrg();
    org.setOrgID("1");
    org.setName("测试部门4");
    org.setCode("000011");
    org.setPcode("000001");
    org.setPname("");
    tb.tongBuOrg(org);
  }
}
