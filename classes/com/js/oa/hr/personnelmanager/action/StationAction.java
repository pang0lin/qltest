package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.oa.hr.personnelmanager.service.NewDutyBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StationAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    StationActionForm stationActionForm = (StationActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : httpServletRequest.getParameter("flag");
    String tag = "view";
    String fromSystem = httpServletRequest.getParameter("fromSystem");
    httpServletRequest.setAttribute("fromSystem", fromSystem);
    if (flag.equals("view")) {
      tag = "view";
      view(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
    } else if (flag.equals("continue") || flag.equals("close")) {
      tag = "add";
      String result = save(stationActionForm, httpServletRequest);
      if (result.equals("success")) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else if (result.equals("name")) {
        httpServletRequest.setAttribute("close", "2");
      } else if (result.equals("no")) {
        httpServletRequest.setAttribute("close", "3");
      } 
    } else if (flag.equals("delete")) {
      delete(httpServletRequest);
    } else if (flag.equals("modify")) {
      tag = "modify";
      getSingle(stationActionForm, httpServletRequest);
    } else if (flag.equals("update")) {
      tag = "modify";
      String result = update(stationActionForm, httpServletRequest);
      if (result.equals("success")) {
        httpServletRequest.setAttribute("close", "1");
      } else if (result.equals("name")) {
        httpServletRequest.setAttribute("close", "2");
      } else if (result.equals("no")) {
        httpServletRequest.setAttribute("close", "3");
      } 
    } else if (flag.equals("view2")) {
      tag = flag;
      HttpSession session = httpServletRequest.getSession(true);
      String domainId = (session.getAttribute("domainId") == null) ? "0" : 
        session.getAttribute("domainId").toString();
      String station_name = httpServletRequest.getParameter("empPosition");
      String viewSql = "aaa";
      String fromSql = 
        "com.js.oa.hr.personnelmanager.po.StationPO aaa";
      String whereSql = "where aaa.domainId=" + domainId + " and aaa.name = '" + httpServletRequest.getParameter("empPosition") + "'" + "and aaa.name='" + station_name + "'" + 
        " order by aaa.id desc";
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } 
    return actionMapping.findForward(tag);
  }
  
  private String save(StationActionForm stationActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    StationPO stationPO = new StationPO();
    stationPO.setName(stationActionForm.getName().trim());
    stationPO.setDomainId(domainId);
    stationPO.setNo(stationActionForm.getNo());
    stationPO.setDescribe(stationActionForm.getDescribe());
    if (session.getAttribute("sysManager").toString().indexOf("1") >= 0 && httpServletRequest.getParameter("fromSystem") != null && 
      "1".equals(httpServletRequest.getParameter("fromSystem"))) {
      stationPO.setCorpId(Long.valueOf(0L));
    } else {
      stationPO.setCorpId(Long.valueOf(httpServletRequest.getSession(true).getAttribute("corpId").toString()));
    } 
    return (new NewDutyBD()).saveStation(stationPO);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String viewSql = "aaa.id,aaa.name,aaa.describe,aaa.corpId,aaa.no";
    String fromSql = "com.js.oa.hr.personnelmanager.po.StationPO aaa";
    String corpId = "";
    if (SystemCommon.getMultiDepart() == 1)
      if (session.getAttribute("sysManager").toString().indexOf("1") >= 0 && "admin".equals(session.getAttribute("userAccount").toString()) && 
        httpServletRequest.getParameter("fromSystem") != null && 
        "1".equals(httpServletRequest.getParameter("fromSystem"))) {
        corpId = "aaa.corpId=0 and";
      } else {
        corpId = "(aaa.corpId=0 or aaa.corpId=" + httpServletRequest.getSession().getAttribute("corpId") + ") and";
      }  
    String whereSql = "where " + corpId + " aaa.domainId=" + domainId + " ";
    String empPositionName = httpServletRequest.getParameter("empPositionName");
    if (!"".equals(empPositionName) && empPositionName != null)
      whereSql = String.valueOf(whereSql) + " and aaa.name like '%" + empPositionName + "%' "; 
    whereSql = String.valueOf(whereSql) + " order by aaa.id desc ";
    httpServletRequest.setAttribute("empPositionName", empPositionName);
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("stationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "fromSystem");
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new NewDutyBD()).deleteStation(httpServletRequest.getParameter("stationId")));
    view(httpServletRequest);
  }
  
  private void getSingle(StationActionForm stationActionForm, HttpServletRequest httpServletRequest) {
    String[] station = (new NewDutyBD()).getSingleStation(httpServletRequest.getParameter("stationId"));
    stationActionForm.setName(station[0]);
    stationActionForm.setDescribe(station[1]);
    stationActionForm.setNo(station[2]);
  }
  
  private String update(StationActionForm stationActionForm, HttpServletRequest httpServletRequest) {
    StationPO stationPO = new StationPO();
    stationPO.setId(new Long(httpServletRequest.getParameter("stationId")));
    stationPO.setName(stationActionForm.getName().trim());
    stationPO.setDescribe(stationActionForm.getDescribe());
    stationPO.setNo(stationActionForm.getNo().trim());
    return (new NewDutyBD()).updateStation(stationPO);
  }
}
