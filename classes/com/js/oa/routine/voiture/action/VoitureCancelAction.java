package com.js.oa.routine.voiture.action;

import com.js.oa.routine.voiture.bean.VoitureEJBBean;
import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureCancelPO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureCancelAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
    VoitureCancelActionForm voitureCancelActionForm = 
      
      (VoitureCancelActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view")) {
      tag = "view";
      viewCancel(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
      addCancel(httpServletRequest, actionForm);
    } else if (flag.equals("save")) {
      tag = "add";
      saveCancel(httpServletRequest, actionForm, domainId);
      httpServletRequest.setAttribute("shouldClose", "1");
    } else if (flag.equals("del")) {
      tag = "view";
      delCancel(httpServletRequest, actionForm);
    } else if (flag.equals("search")) {
      tag = "view";
      viewCancel(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void saveCancel(HttpServletRequest httpServletRequest, ActionForm actionForm, String domainId) throws ParseException {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureCancelActionForm voitureCancelActionForm = 
      
      (VoitureCancelActionForm)actionForm;
    VoitureCancelPO vcpo = new VoitureCancelPO();
    String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
      "0" : 
      httpServletRequest.getParameter("applyId").toString();
    VoitureApplyPO voitureApplyPO = vmbd.getVoitureApplyPO(applyId);
    vcpo.setVoitureApplyPO(vmbd.getVoitureApplyPO(applyId));
    vcpo.setCancelReason(voitureCancelActionForm.getCancelReason());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    vcpo.setCancelDate(formatter1.parse(formatter1.format(new Date())));
    String startApplyDateStr = String.valueOf(formatter1.format(vcpo.getVoitureApplyPO().getStartDate())) + " " + vcpo.getVoitureApplyPO().getStartTime();
    Date startApplyDate = formatter.parse(startApplyDateStr);
    long curTime = formatter.parse(formatter.format(new Date())).getTime();
    long temp = startApplyDate.getTime() - curTime;
    if (curTime < startApplyDate.getTime() && temp > 7200000L) {
      vcpo.setCancelMode("1");
    } else {
      vcpo.setCancelMode("2");
    } 
    vcpo.setDomainId(Long.valueOf(domainId));
    int result = vmbd.saveVoitureCancel(vcpo).intValue();
    httpServletRequest.setAttribute("opResult", String.valueOf(result));
    vmbd.auditingApply(applyId, "4");
    VoitureEJBBean voitureEJBBean = new VoitureEJBBean();
    try {
      List<VoitureApplyPO> list = voitureEJBBean.listVoitureCarPools(voitureApplyPO.getId());
      if (list != null && list.size() > 0) {
        VoitureApplyPO firstRec = list.get(0);
        voitureEJBBean.updateCarPoolToMainRec(firstRec.getId());
        for (int i = 1; i < list.size(); i++) {
          VoitureApplyPO otherRec = list.get(i);
          voitureEJBBean.updateCarPoolToOtherRec(otherRec.getId(), firstRec.getId().toString());
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void delCancel(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String delId = (httpServletRequest.getParameter("delId") == null) ? "" : 
      httpServletRequest.getParameter("delId").toString();
    boolean bl = vmbd.delVoitureCancel(delId);
    viewCancel(httpServletRequest);
  }
  
  private void addCancel(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureCancelActionForm voitureCancelActionForm = 
      
      (VoitureCancelActionForm)actionForm;
    httpServletRequest.setAttribute("applyId", 
        httpServletRequest.getParameter(
          "applyId"));
  }
  
  private void listCancel(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
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
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,searchDept,searchEmpName,isDate,cancelStartDate,cancelEndDate,searchCancelMode");
  }
  
  public void viewCancel(HttpServletRequest servletRequest) {
    HttpSession session = servletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String viewSql = 
      "vcpo.id,vpo.name,vcpo.voitureApplyPO.orgName,vcpo.voitureApplyPO.empName,vcpo.voitureApplyPO.startDate,vcpo.cancelDate,vcpo.cancelMode,vcpo.cancelReason,vcpo.voitureApplyPO.ycr";
    String fromSql = "com.js.oa.routine.voiture.po.VoiturePO vpo,com.js.oa.routine.voiture.po.VoitureCancelPO vcpo";
    ManagerService mbd = new ManagerService();
    VoitureManagerService vmbd = new VoitureManagerService();
    String curUserId = servletRequest.getSession(true).getAttribute(
        "userId").toString();
    String curUserName = servletRequest.getSession(true).getAttribute(
        "userName").toString();
    String curOrgId = servletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String orgIdString = servletRequest.getSession(true).getAttribute(
        "orgIdString").toString();
    String orgName = servletRequest.getSession(true).getAttribute(
        "orgName").toString();
    String whereSql = "where ";
    boolean voitureMtRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    boolean voitureAdRight = mbd.hasRightTypeName(curUserId, "车辆管理", "审核");
    servletRequest.setAttribute("voitureMtRight", String.valueOf(voitureMtRight));
    if (voitureMtRight) {
      whereSql = String.valueOf(whereSql) + " (" + mbd.getRightFinalWhere(curUserId, curOrgId, 
          orgIdString, "车辆管理", "维护", "vpo.orgId", 
          "vpo.createdEmp") + " ) ";
    } else {
      whereSql = String.valueOf(whereSql) + "1<>1";
    } 
    whereSql = String.valueOf(whereSql) + " and vcpo.voitureApplyPO.voitureId=vpo.id";
    if (servletRequest.getParameter("searchDept") != null && 
      !servletRequest.getParameter("searchDept").equals(""))
      whereSql = String.valueOf(whereSql) + " and vcpo.voitureApplyPO.orgName like '%" + 
        servletRequest.getParameter("searchDept") + "%'"; 
    if (servletRequest.getParameter("searchEmpName") != null && 
      !servletRequest.getParameter("searchEmpName").equals(""))
      whereSql = String.valueOf(whereSql) + " and vcpo.voitureApplyPO.empName like '%" + 
        servletRequest.getParameter("searchEmpName") + "%'"; 
    String isDate = (servletRequest.getParameter("isDate") == null) ? "" : 
      servletRequest.getParameter("isDate");
    if (isDate.equals("1") && 
      servletRequest.getParameter("cancelStartDate") != null && 
      !servletRequest.getParameter("cancelStartDate").equals("") && 
      servletRequest.getParameter("cancelEndDate") != null && 
      !servletRequest.getParameter("cancelEndDate").equals("")) {
      String cancelStartDate = servletRequest.getParameter(
          "cancelStartDate");
      String cancelEndDate = servletRequest.getParameter("cancelEndDate");
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and vcpo.cancelDate>='" + 
          cancelStartDate + "' and " + 
          "vcpo.cancelDate<='" + 
          cancelEndDate + "'";
      } else {
        whereSql = String.valueOf(whereSql) + " and vcpo.cancelDate>=JSDB.FN_STRTODATE('" + 
          cancelStartDate + "','S') and " + 
          "vcpo.cancelDate<=JSDB.FN_STRTODATE('" + 
          cancelEndDate + "','S')";
      } 
    } 
    String searchStatus = (servletRequest.getParameter("searchCancelMode") == null) ? 
      "" : 
      servletRequest.getParameter("searchCancelMode");
    if (!searchStatus.equals("0") && !searchStatus.equals(""))
      whereSql = String.valueOf(whereSql) + " and vcpo.cancelMode='" + 
        servletRequest.getParameter("searchCancelMode") + "'"; 
    whereSql = String.valueOf(whereSql) + " and vcpo.domainId=" + domainId + " order by vcpo.id desc";
    listCancel(servletRequest, viewSql, fromSql, whereSql);
  }
}
