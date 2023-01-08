package com.js.oa.security.seamoon.action;

import com.js.oa.security.seamoon.service.SeaMoonService;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SeaMoonAjaxAction extends DispatchAction {
  private static final String masCityService = null;
  
  public void seaMoonTest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    response.setContentType("text/xml; charset=GBK");
    PrintWriter out = response.getWriter();
    String sn = request.getParameter("sn");
    String scKey = request.getParameter("scKey");
    SeaMoonService seaMoonService = new SeaMoonService();
    try {
      String re = "";
      re = seaMoonService.checkPasswordBySnDynKey(sn, scKey);
      if ("1".equals(re)) {
        re = "测试成功！";
      } else if ("2".equals(re)) {
        re = "sn号不存在！";
      } else if ("3".equals(re)) {
        re = "SN字符串有错！";
      } else if ("4".equals(re)) {
        re = "密码错误！";
      } else {
        re = "测试失败！";
      } 
      out.print(re);
    } catch (Exception e) {
      e.printStackTrace();
      out.print("测试失败！");
    } finally {
      out.flush();
      out.close();
    } 
  }
  
  public void seaMoonSyn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    response.setContentType("text/xml; charset=GBK");
    PrintWriter out = response.getWriter();
    String sn = request.getParameter("sn");
    String scKey = request.getParameter("scKey");
    SeaMoonService seaMoonService = new SeaMoonService();
    try {
      String re = "";
      re = seaMoonService.seaMoonSyn(sn, scKey);
      if ("1".equals(re)) {
        re = "同步成功！";
      } else if ("2".equals(re)) {
        re = "sn号不存在！";
      } else if ("3".equals(re)) {
        re = "SN字符串有错！";
      } else if ("4".equals(re)) {
        re = "密码错误！";
      } else {
        re = "同步失败！";
      } 
      if (re == null || "".equals(re))
        re = "同步失败!"; 
      out.print(re);
    } catch (Exception e) {
      e.printStackTrace();
      out.print("同步失败!");
    } finally {
      out.flush();
      out.close();
    } 
  }
}
