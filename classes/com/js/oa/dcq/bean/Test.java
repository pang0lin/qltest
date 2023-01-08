package com.js.oa.dcq.bean;

import com.js.oa.dongcheng.pojo.EmployeePojo;
import net.sf.json.JSONObject;

public class Test {
  public static void main(String[] args) {
    String string = "D:\\Tomcat_dcq\\webapps\\jsoa\\WEB-INF\\classes";
    System.out.println(string.substring(0, string.indexOf("\\WEB-INF")));
  }
  
  public String get() {
    EmployeePojo pojo = new EmployeePojo();
    return JSONObject.fromObject(pojo).toString();
  }
}
