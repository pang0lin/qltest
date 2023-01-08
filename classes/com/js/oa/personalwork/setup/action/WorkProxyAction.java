package com.js.oa.personalwork.setup.action;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.personalwork.setup.po.WorkProxyPO;
import com.js.oa.personalwork.setup.service.WorkProxyBD;
import com.js.util.page.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkProxyAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    Long curUserId = new Long(session.getAttribute("userId").toString());
    String curUserName = session.getAttribute("userName").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    WorkProxyActionForm workProxyActionForm = (WorkProxyActionForm)actionForm;
    WorkProxyBD bd = new WorkProxyBD();
    String action = request.getParameter("action");
    if ("delBatch".equals(action)) {
      if (request.getParameter("ids") != null) {
        String ids = request.getParameter("ids");
        bd.delBatch(ids, (String)curUserId);
      } 
      action = "list";
    } 
    if ("delAll".equals(action)) {
      bd.delAll((String)curUserId);
      action = "list";
    } 
    if ("add".equals(action)) {
      String beginHour = request.getParameter("beginHour");
      String beginMinutes = request.getParameter("beginMinutes");
      String endHour = request.getParameter("endHour");
      String endMinutes = request.getParameter("endMinutes");
      String beginHM = " ";
      String endHM = " ";
      if (beginHour != null && !"".equals(beginHour) && 
        !"null".equals(beginHour)) {
        beginHM = String.valueOf(beginHM) + beginHour + ":";
      } else {
        beginHM = String.valueOf(beginHM) + "00:";
      } 
      if (beginMinutes != null && !"".equals(beginMinutes) && 
        !"null".equals(beginMinutes)) {
        beginHM = String.valueOf(beginHM) + beginMinutes + ":";
      } else {
        beginHM = String.valueOf(beginHM) + "00:";
      } 
      beginHM = String.valueOf(beginHM) + "00";
      if (endHour != null && !"".equals(endHour) && 
        !"null".equals(endHour)) {
        endHM = String.valueOf(endHM) + endHour + ":";
      } else {
        endHM = String.valueOf(endHM) + "23:";
      } 
      if (endMinutes != null && !"".equals(endMinutes) && 
        !"null".equals(endMinutes)) {
        endHM = String.valueOf(endHM) + endMinutes + ":";
      } else {
        endHM = String.valueOf(endHM) + "59:";
      } 
      endHM = String.valueOf(endHM) + "59";
      WorkProxyPO po = new WorkProxyPO();
      po.setBeginTime(new Date(String.valueOf(workProxyActionForm.getBeginTime()) + beginHM));
      po.setEndTime(new Date(String.valueOf(workProxyActionForm.getEndTime()) + endHM));
      po.setProxyEmpId(Long.parseLong(workProxyActionForm.getProxyEmpId()));
      po.setProxyEmpName(workProxyActionForm.getProxyEmpName());
      po.setProxyOrgId(workProxyActionForm.getProxyOrgId());
      po.setProxyOrgName(workProxyActionForm.getProxyOrgName());
      po.setProxyProcess(request.getParameter("proxyProcess"));
      po.setProxyAllProcess(request.getParameter("proxyAllProcess"));
      po.setProxyState(Byte.parseByte(workProxyActionForm.getProxystate()));
      po.setDomainId(domainId);
      if ("".equals(workProxyActionForm.getEmpId()) || "".equals(workProxyActionForm.getEmpName())) {
        po.setEmpId(curUserId.longValue());
        po.setEmpName(curUserName);
      } else {
        po.setEmpId(Long.parseLong(workProxyActionForm.getEmpId()));
        po.setEmpName(workProxyActionForm.getEmpName());
      } 
      po.setCreateEmpId(curUserId.toString());
      po.setCreateEmpName(curUserName);
      po.setCreateTime(new Date());
      bd.add(po, (String)curUserId);
      workProxyActionForm.setDone("done");
      if ("continue".equals(request.getParameter("con"))) {
        ProcessBD procBD = new ProcessBD();
        List<Object[]> processList = procBD.getAllProcess(domainId);
        ArrayList<Object[]> packageList = new ArrayList();
        if (processList != null) {
          String upPackageId = "";
          for (int i = 0; i < processList.size(); i++) {
            Object[] processObj = processList.get(i);
            if (!upPackageId.equals(processObj[0].toString())) {
              upPackageId = processObj[0].toString();
              Object[] packageObj = { processObj[0], processObj[1] };
              packageList.add(packageObj);
            } 
          } 
        } 
        request.setAttribute("packageList", packageList);
        request.setAttribute("processList", processList);
      } 
      return actionMapping.findForward("add");
    } 
    if ("new".equals(action)) {
      ProcessBD procBD = new ProcessBD();
      List<Object[]> processList = procBD.getAllProcess(domainId);
      ArrayList<Object[]> packageList = new ArrayList();
      if (processList != null) {
        String upPackageId = "";
        for (int i = 0; i < processList.size(); i++) {
          Object[] processObj = processList.get(i);
          if (!upPackageId.equals(processObj[0].toString())) {
            upPackageId = processObj[0].toString();
            Object[] packageObj = { processObj[0], processObj[1] };
            packageList.add(packageObj);
          } 
        } 
      } 
      request.setAttribute("packageList", packageList);
      request.setAttribute("processList", processList);
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      WorkProxyPO po = bd.load(request.getParameter("editId"));
      request.setAttribute("beginTime", po.getBeginTime());
      request.setAttribute("endTime", po.getEndTime());
      workProxyActionForm.setEditId((new StringBuilder(String.valueOf(po.getId()))).toString());
      workProxyActionForm.setProxyEmpId((new StringBuilder(String.valueOf(po.getProxyEmpId()))).toString());
      workProxyActionForm.setProxyEmpName(po.getProxyEmpName());
      workProxyActionForm.setProxyOrgId((new StringBuilder(String.valueOf(po.getProxyOrgId()))).toString());
      workProxyActionForm.setProxyOrgName(po.getProxyOrgName());
      workProxyActionForm.setProxystate((new StringBuilder(String.valueOf(po.getProxyState()))).toString());
      request.setAttribute("proxyProcess", po.getProxyProcess());
      request.setAttribute("proxyAllProcess", po.getProxyAllProcess());
      workProxyActionForm.setEmpId((new StringBuilder(String.valueOf(po.getEmpId()))).toString());
      workProxyActionForm.setEmpName(po.getEmpName());
      ProcessBD procBD = new ProcessBD();
      List<Object[]> processList = procBD.getAllProcess(domainId);
      ArrayList<Object[]> packageList = new ArrayList();
      if (processList != null) {
        String upPackageId = "";
        for (int i = 0; i < processList.size(); i++) {
          Object[] processObj = processList.get(i);
          if (!upPackageId.equals(processObj[0].toString())) {
            upPackageId = processObj[0].toString();
            Object[] packageObj = { processObj[0], processObj[1] };
            packageList.add(packageObj);
          } 
        } 
      } 
      request.setAttribute("packageList", packageList);
      request.setAttribute("processList", processList);
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      String beginHour = request.getParameter("beginHour");
      String beginMinutes = request.getParameter("beginMinutes");
      String endHour = request.getParameter("endHour");
      String endMinutes = request.getParameter("endMinutes");
      String beginHM = " ";
      String endHM = " ";
      if (beginHour != null && !"".equals(beginHour) && 
        !"null".equals(beginHour)) {
        beginHM = String.valueOf(beginHM) + beginHour + ":";
      } else {
        beginHM = String.valueOf(beginHM) + "00:";
      } 
      if (beginMinutes != null && !"".equals(beginMinutes) && 
        !"null".equals(beginMinutes)) {
        beginHM = String.valueOf(beginHM) + beginMinutes + ":";
      } else {
        beginHM = String.valueOf(beginHM) + "00:";
      } 
      beginHM = String.valueOf(beginHM) + "00";
      if (endHour != null && !"".equals(endHour) && 
        !"null".equals(endHour)) {
        endHM = String.valueOf(endHM) + endHour + ":";
      } else {
        endHM = String.valueOf(endHM) + "23:";
      } 
      if (endMinutes != null && !"".equals(endMinutes) && 
        !"null".equals(endMinutes)) {
        endHM = String.valueOf(endHM) + endMinutes + ":";
      } else {
        endHM = String.valueOf(endHM) + "59:";
      } 
      endHM = String.valueOf(endHM) + "59";
      WorkProxyPO po = new WorkProxyPO();
      po.setBeginTime(new Date(String.valueOf(workProxyActionForm.getBeginTime()) + beginHM));
      po.setEndTime(new Date(String.valueOf(workProxyActionForm.getEndTime()) + endHM));
      po.setProxyEmpId(Long.parseLong(workProxyActionForm.getProxyEmpId()));
      po.setProxyEmpName(workProxyActionForm.getProxyEmpName());
      po.setProxyOrgId(workProxyActionForm.getProxyOrgId());
      po.setProxyOrgName(workProxyActionForm.getProxyOrgName());
      po.setProxyState(Byte.parseByte(workProxyActionForm.getProxystate()));
      po.setProxyProcess(request.getParameter("proxyAllProcess").equals("1") ? "" : request.getParameter("proxyProcess"));
      po.setProxyAllProcess(request.getParameter("proxyAllProcess"));
      if ("".equals(workProxyActionForm.getEmpId()) || "".equals(workProxyActionForm.getEmpName())) {
        po.setEmpId(curUserId.longValue());
        po.setEmpName(curUserName);
      } else {
        po.setEmpId(Long.parseLong(workProxyActionForm.getEmpId()));
        po.setEmpName(workProxyActionForm.getEmpName());
      } 
      bd.update(po, request.getParameter("editId"), (String)curUserId);
      return actionMapping.findForward("modi");
    } 
    if ("list".equals(action))
      list(request, domainId); 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, String domainId) {
    WorkProxyBD bd = new WorkProxyBD();
    bd.setUnavailableProxy();
    long userId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("userId")));
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String whereSql = "where 1=1  ";
    String proxyEmpName = (request.getParameter("proxyEmpName") == null) ? "" : request.getParameter("proxyEmpName");
    String proxystate = (request.getParameter("proxystate") == null) ? "" : request.getParameter("proxystate");
    if (!proxyEmpName.equals(""))
      whereSql = String.valueOf(whereSql) + " and  po.proxyEmpName like '%" + proxyEmpName + "%' "; 
    if (!proxystate.equals(""))
      whereSql = String.valueOf(whereSql) + " and po.proxyState= '" + proxystate + "' "; 
    Page page = new Page(
        " po.id,po.proxyEmpName,po.beginTime,po.endTime,po.proxyState,po.empId,po.createEmpName,po.createTime,po.empName ", 
        " com.js.oa.personalwork.setup.po.WorkProxyPO po ", 
        String.valueOf(whereSql) + " and po.domainId=" + domainId + " and po.empId =" + userId + " order by po.id desc");
    if ("system".equals(request.getParameter("from")))
      page = new Page(
          " po.id,po.proxyEmpName,po.beginTime,po.endTime,po.proxyState,po.empId,po.createEmpName,po.createTime,po.empName ", 
          " com.js.oa.personalwork.setup.po.WorkProxyPO po ", 
          String.valueOf(whereSql) + " and po.domainId=" + domainId + " order by po.id desc"); 
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,from");
  }
}
