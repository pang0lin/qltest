package com.js.oa.hr.kq.szgt.action;

import com.js.oa.hr.kq.szgt.service.HolidayUtil;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SKqFlowCondition extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    HttpSession session = httpServletRequest.getSession();
    String empId = session.getAttribute("userId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String[] holidays = (new HolidayUtil()).getHolidyData(empId, corpId);
    StringBuffer xml = new StringBuffer(64);
    httpServletResponse.setContentType("text/xml;charset=GBK");
    PrintWriter out = httpServletResponse.getWriter();
    if (holidays != null) {
      if (holidays[0] != null) {
        xml.append(holidays[0]);
      } else {
        xml.append("0");
      } 
      xml.append(",");
      if (holidays[1] != null) {
        xml.append(holidays[1]);
      } else {
        xml.append("0");
      } 
      xml.append(",");
      if (holidays[2] != null) {
        xml.append(holidays[2]);
      } else {
        xml.append("0");
      } 
      xml.append(",");
      if (holidays[3] != null) {
        xml.append(holidays[3]);
      } else {
        xml.append("0");
      } 
      xml.append(",");
      if (holidays[4] != null) {
        xml.append(holidays[4]);
      } else {
        xml.append("0");
      } 
      xml.append(",");
      if (holidays[5] != null) {
        xml.append(holidays[5]);
      } else {
        xml.append("0");
      } 
    } else {
      xml.append("0,0,0,0,0,0");
    } 
    out.print(xml.toString());
    out.close();
    return null;
  }
}
