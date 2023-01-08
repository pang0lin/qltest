package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.TrainClassPO;
import com.js.oa.hr.personnelmanager.service.TrainClassBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TrainClassAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    TrainClassActionForm trainClassActionForm = 
      (TrainClassActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "list" : 
      httpServletRequest.getParameter("flag");
    String tag = "list";
    if (flag.equals("list")) {
      tag = "list";
      view(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
    } else if (flag.equals("continue") || flag.equals("close")) {
      tag = "add";
      if (save(trainClassActionForm, httpServletRequest)) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("modify")) {
      tag = "modify";
      getSingle(trainClassActionForm, httpServletRequest);
    } else if (flag.equals("update")) {
      tag = "modify";
      if (update(trainClassActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("delete")) {
      delete(httpServletRequest);
    } else if (flag.equals("view")) {
      tag = "view";
      getSingle(trainClassActionForm, httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSql = "po.id,po.trainName,po.trainDescribe";
    String fromSql = 
      " com.js.oa.hr.personnelmanager.po.TrainClassPO po ";
    String whereSql = " where po.domain=" + domainId + 
      " order by po.id desc ";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
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
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("trainClassList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
  
  private boolean save(TrainClassActionForm trainClassActionForm, HttpServletRequest httpServletRequest) {
    boolean ret = false;
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? new Long(0L) : 
      new Long(session.getAttribute("domainId").toString());
    TrainClassPO po = new TrainClassPO();
    po.setTrainName(trainClassActionForm.getTrainName());
    po.setTrainDescribe(trainClassActionForm.getTrainDescribe());
    po.setTrainContent(trainClassActionForm.getTrainContent());
    po.setTrainAim(trainClassActionForm.getTrainAim());
    po.setDomain(domainId);
    TrainClassBD bd = new TrainClassBD();
    ret = bd.save(po);
    return ret;
  }
  
  private void getSingle(TrainClassActionForm trainClassActionForm, HttpServletRequest httpServletRequest) {
    TrainClassPO po = (new TrainClassBD()).load(new Long(httpServletRequest
          .getParameter("trainClassId")));
    trainClassActionForm.setId(po.getId());
    trainClassActionForm.setTrainName(po.getTrainName());
    trainClassActionForm.setTrainDescribe(po.getTrainDescribe());
    trainClassActionForm.setTrainContent(po.getTrainContent());
    trainClassActionForm.setTrainAim(po.getTrainAim());
  }
  
  private boolean update(TrainClassActionForm trainClassActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? new Long(0L) : 
      new Long(session.getAttribute("domainId").toString());
    TrainClassPO po = new TrainClassPO();
    po.setTrainName(trainClassActionForm.getTrainName());
    po.setTrainDescribe(trainClassActionForm.getTrainDescribe());
    po.setTrainContent(trainClassActionForm.getTrainContent());
    po.setTrainAim(trainClassActionForm.getTrainAim());
    po.setDomain(domainId);
    return (new TrainClassBD()).update(po, 
        new Long(httpServletRequest
          .getParameter("trainClassId")))
      .booleanValue();
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new TrainClassBD()).delete(
          httpServletRequest.getParameter("trainClassId")));
    view(httpServletRequest);
  }
}
