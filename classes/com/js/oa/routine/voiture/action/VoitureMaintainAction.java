package com.js.oa.routine.voiture.action;

import com.js.oa.routine.voiture.po.VoitureMaintainPO;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureMaintainAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    VoitureMaintainActionForm voitureMaintainActionForm = 
      (VoitureMaintainActionForm)actionForm;
    String tag = "view";
    String flag = (httpServletRequest.getParameter("flag") == null) ? 
      "view" : 
      httpServletRequest.getParameter("flag");
    VoitureManagerService vmbd = new VoitureManagerService();
    String voitureName = "";
    if (flag.equals("voitureview")) {
      tag = "voitureview";
      voitureView(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
      voitureName = vmbd.loadVoiture(httpServletRequest.getParameter(
            "voitureId")).getName();
      httpServletRequest.setAttribute("voitureId", 
          httpServletRequest
          .getParameter("voitureId"));
      voitureMaintainActionForm.setVoitureName(voitureName);
    } else if (flag.equals("view")) {
      tag = "view";
      view(httpServletRequest, 
          httpServletRequest.getParameter("voitureId"));
      httpServletRequest.setAttribute("voitureId", 
          httpServletRequest
          .getParameter("voitureId"));
    } else if (flag.equals("close") || flag.equals("continue")) {
      tag = "save";
      saveMaintain(httpServletRequest, actionForm);
      System.out.println("参数::" + 
          httpServletRequest.getParameter("voitureId"));
      view(httpServletRequest, 
          httpServletRequest.getParameter("voitureId"));
      httpServletRequest.setAttribute("voitureId", 
          httpServletRequest
          .getParameter("voitureId"));
      if (flag.equals("close"))
        httpServletRequest.setAttribute("shouldClose", "1"); 
    } else if (flag.equals("del")) {
      tag = "view";
      delMaintain(httpServletRequest);
      view(httpServletRequest, 
          httpServletRequest.getParameter("voitureId"));
    } 
    return actionMapping.findForward(tag);
  }
  
  public void voitureView(HttpServletRequest servletRequest) {
    String viewSql = 
      "vpo.id,vpo.num,vpo.model,vpo.name,vpo.motorMan,max(po.maintainTime)";
    String fromSql = 
      "com.js.oa.routine.voiture.po.VoitureMaintainPO po right join po.voiturePO vpo";
    ManagerService mbd = new ManagerService();
    String curUserId = servletRequest.getSession(true).getAttribute("userId").toString();
    String curOrgId = servletRequest.getSession(true).getAttribute("orgId").toString();
    String orgIdString = servletRequest.getSession(true).getAttribute("orgIdString").toString();
    String whereSql = "where " + mbd.getRightFinalWhere(curUserId, curOrgId, orgIdString, 
        "车辆管理", "维护", "vpo.orgId", "");
    whereSql = String.valueOf(whereSql) + " group by vpo.id,vpo.num,vpo.model,vpo.name,vpo.motorMan order by vpo.id desc";
    list(servletRequest, viewSql, fromSql, whereSql);
  }
  
  public void view(HttpServletRequest servletRequest, String voitureId) {
    String viewSql = 
      "po.id,vpo.id,vpo.name,vpo.num,vpo.model,vpo.motorMan,po.maintainTime";
    String fromSql = 
      "com.js.oa.routine.voiture.po.VoitureMaintainPO po join po.voiturePO vpo";
    String whereSql = " where vpo.id=" + voitureId + " order by po.id desc";
    ManagerService mbd = new ManagerService();
    String curUserId = servletRequest.getSession(true).getAttribute("userId").toString();
    boolean voitureMtRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    servletRequest.setAttribute("voitureMtRight", String.valueOf(voitureMtRight));
    boolean voitureAddRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    servletRequest.setAttribute("voitureAddRight", String.valueOf(voitureAddRight));
    list(servletRequest, viewSql, fromSql, whereSql);
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
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
  
  public void saveMaintain(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureMaintainPO vmpo = new VoitureMaintainPO();
    VoiturePO voiturePO = new VoiturePO();
    VoitureMaintainActionForm voitureMaintainActionForm = 
      (VoitureMaintainActionForm)actionForm;
    Date maintainTime = new Date(httpServletRequest.getParameter(
          "maintainTime").replaceAll("-", "/"));
    vmpo.setMaintainTime(maintainTime);
    voiturePO = vmbd.loadVoiture(voitureMaintainActionForm.getVoitureId()
        .toString());
    vmpo.setVoiturePO(voiturePO);
    int result = vmbd.saveVoitureMaintain(vmpo).intValue();
    if (result > 0) {
      httpServletRequest.setAttribute("opResult", String.valueOf(result));
    } else {
      voitureMaintainActionForm.setMaintainTime("");
    } 
  }
  
  public void delMaintain(HttpServletRequest httpServletRequest) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String delId = (httpServletRequest.getParameter("delId") == null) ? "" : 
      httpServletRequest.getParameter("delId").toString();
    boolean bl = vmbd.delVoitureMaintain(delId);
  }
}
