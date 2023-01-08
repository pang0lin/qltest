package com.js.oa.form;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class TestNewClass extends Workflow {
  public Map saveFlow(HttpServletRequest request) {
    Map saveMap = save(request);
    String flag = "";
    if (request.getParameter("flag") != null)
      flag = request.getParameter("flag"); 
    if ("saveToDraft".equals(flag)) {
      System.out.println("开始节点保存");
    } else if ("save".equals(flag)) {
      System.out.println("流程发起！");
    } 
    return saveMap;
  }
  
  public String updateFlow(HttpServletRequest request) {
    String returnValue = "";
    try {
      if ("同意".equals(request.getParameter("jst$3113_f3128")) || 
        "同意".equals(request.getParameter("jst$3113_f3129"))) {
        returnValue = update(request);
        System.out.println("流程提交！此处可以调用客户的扩展自定义业务逻辑");
      } else {
        System.out.println("流程提交失败！");
        returnValue = "error";
      } 
    } catch (Exception e) {
      returnValue = "error";
    } 
    return returnValue;
  }
  
  public String update0(HttpServletRequest request) {
    System.out.println("人事备案：" + request.getParameter("jst$3113_f3129"));
    return update(request);
  }
  
  public String update1(HttpServletRequest request) {
    System.out.println("这是第1个节点");
    return update(request);
  }
  
  public String update2(HttpServletRequest request) {
    System.out.println("这是第2个节点");
    return update(request);
  }
  
  public void back(HttpServletRequest request) {
    super.back(request);
    System.out.println("流程退回");
  }
  
  public String complete(HttpServletRequest request) {
    String returnValue = "";
    try {
      returnValue = super.complete(request);
      System.out.println("流程办理完毕！");
    } catch (Exception e) {
      returnValue = "error";
    } 
    System.out.println("流程结束状态：" + returnValue);
    return returnValue;
  }
  
  public void delete(HttpServletRequest request) {}
}
