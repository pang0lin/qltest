package com.js.oa.message.action;

import com.js.oa.message.service.MsManageBD;
import com.js.util.page.Page;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MsHistoryAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    MsHistoryActionForm msHistoryActionForm = (MsHistoryActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    if ("listView".equals(action)) {
      list(httpServletRequest);
      return actionMapping.findForward("listView");
    } 
    if ("listStatus".equals(action)) {
      list(httpServletRequest);
      return actionMapping.findForward("listStatus");
    } 
    return actionMapping.findForward("listView");
  }
  
  public void list(HttpServletRequest request) {
    HttpSession session = request.getSession();
    Object object = session.getAttribute("userId");
    String wherePara = "";
    String fromUser = request.getParameter("fromUser");
    String fromOrg = request.getParameter("fromOrg");
    String fromContent = request.getParameter("fromContent");
    String sendToPerson = request.getParameter("sendToPerson");
    String start_date = request.getParameter("start_date");
    String end_date = request.getParameter("end_date");
    String searchDate = request.getParameter("searchDate");
    if (start_date != null && !"".equals(start_date))
      start_date = dateFormart(start_date); 
    if (end_date != null && !"".equals(end_date))
      try {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        Date datef = bartDateFormat.parse(end_date);
        end_date = format.format(datef);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(datef);
        calendar.add(5, 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        end_date = dateFormat.format(calendar.getTime());
      } catch (ParseException e) {
        e.printStackTrace();
      }  
    if (fromUser != null && !"".equals(fromUser))
      wherePara = String.valueOf(wherePara) + " and yee.empName like '%" + fromUser + "%' "; 
    if (fromOrg != null && !"".equals(fromOrg))
      wherePara = String.valueOf(wherePara) + " and org.orgName like '%" + fromOrg + "%' "; 
    if (fromContent != null && !"".equals(fromContent))
      wherePara = String.valueOf(wherePara) + " and ms.msContext like '%" + fromContent + "%' "; 
    if (sendToPerson != null && !"".equals(sendToPerson))
      wherePara = String.valueOf(wherePara) + " and ms.sendToPerson like '%" + sendToPerson + "%' "; 
    if (start_date != null && !"".equals(start_date) && "1".equals(searchDate))
      wherePara = String.valueOf(wherePara) + " and ms.sendTime > '" + start_date + "'"; 
    if (end_date != null && !"".equals(end_date) && "1".equals(searchDate))
      wherePara = String.valueOf(wherePara) + " and ms.sendTime <'" + end_date + "'"; 
    if ("listStatus".equals(request.getParameter("action")))
      wherePara = String.valueOf(wherePara) + " and ms.fromUserId = " + object; 
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " where ms.fromUserId=yee.empId ";
    where = String.valueOf(where) + wherePara;
    Page page = new Page(" ms.historyId,yee.empName, ms.fromUserId,ms.sendToPerson, ms.sendTime,ms.msContext,ms.flag ,ms.receiveContent,ms.extendCode,ms.receiveCode ", 
        " com.js.oa.message.po.MsHistoryPO ms,com.js.system.vo.usermanager.EmployeeVO yee ", 
        String.valueOf(where) + " order by ms.historyId desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    if (list != null && list.size() != 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        try {
          if ("系统管理员".equals(obj[1]))
            obj[1] = "系统"; 
          if ("0".equals(obj[2])) {
            obj[2] = "系统";
          } else {
            String sql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org,com.js.system.vo.usermanager.EmployeeOrgVO orguser WHERE org.orgId=orguser.orgId AND orguser.empId=" + 
              
              obj[2];
            MsManageBD msBD = new MsManageBD();
            List<Object[]> msList = msBD.getListByYourSQL(sql);
            if (msList != null && msList.size() != 0)
              for (int j = 0; j < msList.size(); j++) {
                Object[] obj2 = msList.get(j);
                String orgName = (String)obj2[1];
                if (orgName != null && "0".equals(orgName))
                  orgName = "系统"; 
                obj[2] = orgName;
              }  
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      }  
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("fromUser", fromUser);
    request.setAttribute("fromOrg", fromOrg);
    request.setAttribute("fromContent", fromContent);
    request.setAttribute("sendToPerson", sendToPerson);
    request.setAttribute("start_date", start_date);
    request.setAttribute("end_date", end_date);
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,fromUser,fromOrg,sendToPerson,start_date,end_date,searchDate");
  }
  
  public String dateFormart(String date) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
