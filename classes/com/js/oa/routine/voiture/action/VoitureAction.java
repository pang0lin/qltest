package com.js.oa.routine.voiture.action;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.oa.routine.voiture.po.VoitureTypePO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
    VoitureActionForm voitureActionForm = (VoitureActionForm)actionForm;
    HttpSession session = servletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String flag = (servletRequest.getParameter("flag") == null) ? "view" : 
      servletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view")) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      String sdate = servletRequest.getParameter("searchStartDate");
      if (sdate != null) {
        sdate = sdate.replaceAll("/", "-");
      } else {
        sdate = sf.format(new Date());
      } 
      Calendar prev = Calendar.getInstance();
      prev.setTime(strToDate(sdate));
      prev.add(5, -1);
      String y = String.valueOf(prev.get(1));
      String m = String.valueOf(prev.get(2) + 1);
      String d = String.valueOf(prev.get(5));
      servletRequest.setAttribute("prevDay", 
          String.valueOf(y) + "/" + m + "/" + d);
      Calendar next = Calendar.getInstance();
      next.setTime(strToDate(sdate));
      next.add(5, 1);
      y = String.valueOf(next.get(1));
      m = String.valueOf(next.get(2) + 1);
      d = String.valueOf(next.get(5));
      servletRequest.setAttribute("nextDay", 
          String.valueOf(y) + "/" + m + "/" + d);
      SimpleDateFormat sf0 = new SimpleDateFormat("yyyy-M-d");
      servletRequest.setAttribute("currentDay", 
          sf0.format(new Date())
          .toString().replaceAll("-", "/"));
      tag = "view";
      view(servletRequest);
      servletRequest.setAttribute("maintenanceVoitureIds", 
          getCanModify(servletRequest));
      if (servletRequest.getParameter("init") != null && 
        "0".equals(servletRequest.getParameter("init")))
        tag = "init"; 
    } else if (flag.equals("add")) {
      tag = "add";
      servletRequest.setAttribute("voitureTypeList", (
          new VoitureManagerService()).listVoitureType(
            domainId));
    } else if (flag.equals("modify")) {
      load(servletRequest, actionForm);
      servletRequest.setAttribute("voitureTypeList", (
          new VoitureManagerService()).listVoitureType(
            domainId));
      tag = "modi";
    } else if (flag.equals("detail")) {
      load(servletRequest, actionForm);
      servletRequest.setAttribute("voitureTypeList", (
          new VoitureManagerService()).listVoitureType(
            domainId));
      tag = "detail";
    } else if (flag.equals("update")) {
      updateVoiture(servletRequest, actionForm);
      servletRequest.setAttribute("shouldClose", "1");
      servletRequest.setAttribute("voitureTypeList", (
          new VoitureManagerService()).listVoitureType(
            domainId));
      tag = "modi";
    } else if (flag.equals("close") || flag.equals("continue")) {
      tag = "save";
      saveVoiture(actionMapping, servletRequest, actionForm);
      view(servletRequest);
      servletRequest.setAttribute("voitureList", (
          new VoitureManagerService()).listVoitureType(
            domainId));
      servletRequest.setAttribute("voitureTypeList", (
          new VoitureManagerService()).listVoitureType(
            domainId));
      if (flag.equals("close"))
        servletRequest.setAttribute("shouldClose", "1"); 
    } else if (flag.equals("del")) {
      tag = "view";
      delVoiture(servletRequest, actionForm);
      servletRequest.setAttribute("maintenanceVoitureIds", 
          getCanModify(servletRequest));
    } 
    return actionMapping.findForward(tag);
  }
  
  public void view(HttpServletRequest servletRequest) {
    HttpSession session = servletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSql = 
      "po.id,po.name,po.model,po.num,po.motorMan,po.voitureTypePO.name,po.status ";
    String fromSql = "com.js.oa.routine.voiture.po.VoiturePO po ";
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
    String whereSql = "where po.isDelete=0 and ";
    boolean voitureMtRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    boolean voitureAdRight = mbd.hasRightTypeName(curUserId, "车辆管理", "审核");
    servletRequest.setAttribute("voitureMtRight", String.valueOf(voitureMtRight));
    ManagerService managerBD = new ManagerService();
    String range = vmbd.getVoitureUseRange(curUserName, orgName, curUserId, orgIdString);
    if (!range.equals("")) {
      whereSql = String.valueOf(whereSql) + "(";
      whereSql = String.valueOf(whereSql) + "po.id in(" + range.substring(0, range.length() - 1) + 
        ")";
      if ("0".equals(servletRequest.getParameter("init"))) {
        if (voitureMtRight) {
          whereSql = String.valueOf(whereSql) + " and (1=1) ";
          String rightWhere = managerBD.getRightFinalWhere(curUserId, curOrgId, 
              "07*14*02", "po.orgId", "po.createdEmp");
          whereSql = String.valueOf(whereSql) + " and " + rightWhere;
        } else if (voitureAdRight) {
          whereSql = String.valueOf(whereSql) + " and (1=1) ";
        } 
        whereSql = String.valueOf(whereSql) + ")";
      } else {
        if (voitureMtRight) {
          whereSql = String.valueOf(whereSql) + " or (1=1) ";
          String rightWhere = managerBD.getRightFinalWhere(curUserId, curOrgId, 
              "07*14*02", "po.orgId", "po.createdEmp");
          whereSql = String.valueOf(whereSql) + " and " + rightWhere;
        } else if (voitureAdRight) {
          whereSql = String.valueOf(whereSql) + " or (1=1) ";
        } 
        whereSql = String.valueOf(whereSql) + ")";
      } 
    } else if (voitureMtRight) {
      whereSql = String.valueOf(whereSql) + " (" + mbd.getRightFinalWhere(curUserId, curOrgId, 
          orgIdString, "车辆管理", "维护", 
          "po.orgId", 
          "po.voitureTypePO.createdEmp") + 
        " ) ";
      String rightWhere = managerBD.getRightFinalWhere(curUserId, curOrgId, 
          "07*14*02", "po.orgId", "po.createdEmp");
      whereSql = String.valueOf(whereSql) + " and " + rightWhere;
    } else {
      whereSql = String.valueOf(whereSql) + "1<>1";
    } 
    whereSql = String.valueOf(whereSql) + " and po.domainId=" + domainId + " order by po.status,po.id ";
    boolean voitureAddRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    servletRequest.setAttribute("voitureAddRight", 
        String.valueOf(voitureAddRight));
    ProcessBD procbd = new ProcessBD();
    List list = new ArrayList();
    Object tmpl = procbd.getUserProcess(curUserId, orgIdString, "11");
    if (tmpl != null)
      list = (List)tmpl; 
    servletRequest.setAttribute("voitureFlowlist", list);
    list(servletRequest, viewSql, fromSql, whereSql, vmbd);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL, VoitureManagerService vmbd) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !"".equals(httpServletRequest.getParameter("pager.offset")))
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list != null && list.size() == 0 && offset >= 15) {
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
    httpServletRequest.setAttribute("pageParameters", 
        "processId,remindField,flag,init,searchStartDate");
  }
  
  public void saveVoiture(ActionMapping actionMapping, HttpServletRequest httpServletRequest, ActionForm actionForm) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String curUserId = (session.getAttribute("userId") == null) ? "0" : 
      session.getAttribute("userId").toString();
    String orgID = httpServletRequest.getSession(true).getAttribute(
        "orgId")
      .toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureActionForm voitureActionForm = (VoitureActionForm)actionForm;
    VoiturePO vpo = (VoiturePO)FillBean.transformOneToOne(
        voitureActionForm, VoiturePO.class);
    VoitureTypePO voitureTypePO = vmbd.loadVoitureType(voitureActionForm
        .getVoitureTypeId().toString());
    vpo.setVoitureTypePO(voitureTypePO);
    vpo.setStatus(voitureActionForm.getStatus());
    vpo.setDomainId(Long.valueOf(domainId));
    vpo.setCreatedEmp(new Long(curUserId));
    vpo.setCreatedOrg(new Long(orgID));
    int result = vmbd.saveVoiture(vpo).intValue();
    if (result > 0) {
      httpServletRequest.setAttribute("opResult", String.valueOf(result));
      httpServletRequest.setAttribute("voitureTypeId", voitureActionForm.getVoitureTypeId());
    } else {
      voitureActionForm.reset(actionMapping, httpServletRequest);
    } 
  }
  
  public void delVoiture(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String delId = (httpServletRequest.getParameter("delId") == null) ? "" : 
      httpServletRequest.getParameter("delId").toString();
    boolean bl = true;
    bl = vmbd.delVoiture(delId);
    httpServletRequest.setAttribute("isDel", String.valueOf(bl));
    view(httpServletRequest);
  }
  
  public void load(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String id = httpServletRequest.getParameter("id");
    VoitureActionForm voitureActionForm = (VoitureActionForm)actionForm;
    VoiturePO vpo = vmbd.loadVoiture(id);
    voitureActionForm.setFixedCost(vpo.getFixedCost());
    voitureActionForm.setId(vpo.getId());
    voitureActionForm.setMaintainCyc(vpo.getMaintainCyc());
    voitureActionForm.setMaintainOdograph(vpo.getMaintainOdograph());
    voitureActionForm.setModel(vpo.getModel());
    voitureActionForm.setMotorMan(vpo.getMotorMan());
    voitureActionForm.setName(vpo.getName());
    voitureActionForm.setNum(vpo.getNum());
    voitureActionForm.setOilCost(vpo.getOilCost());
    voitureActionForm.setOrgId(vpo.getOrgId());
    voitureActionForm.setOrgName(vpo.getOrgName());
    voitureActionForm.setRemark(vpo.getRemark());
    voitureActionForm.setVoitureTypeId(vpo.getVoitureTypePO().getId());
    voitureActionForm.setUseRangeId(vpo.getUseRangeId());
    voitureActionForm.setUseRangeName(vpo.getUseRangeName());
    voitureActionForm.setStatus(vpo.getStatus());
    voitureActionForm.setVehicleNum(vpo.getVehicleNum());
  }
  
  public void updateVoiture(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureActionForm voitureActionForm = (VoitureActionForm)actionForm;
    String id = voitureActionForm.getId().toString();
    VoiturePO vpo = new VoiturePO();
    vpo.setFixedCost(voitureActionForm.getFixedCost());
    vpo.setId(voitureActionForm.getId());
    vpo.setMaintainCyc(voitureActionForm.getMaintainCyc());
    vpo.setMaintainOdograph(voitureActionForm.getMaintainOdograph());
    vpo.setModel(voitureActionForm.getModel());
    vpo.setMotorMan(voitureActionForm.getMotorMan());
    vpo.setName(voitureActionForm.getName());
    vpo.setNum(voitureActionForm.getNum());
    vpo.setOilCost(voitureActionForm.getOilCost());
    vpo.setOrgId(voitureActionForm.getOrgId());
    vpo.setOrgName(voitureActionForm.getOrgName());
    vpo.setRemark(voitureActionForm.getRemark());
    vpo.setUseRangeId(voitureActionForm.getUseRangeId());
    vpo.setUseRangeName(voitureActionForm.getUseRangeName());
    vpo.setStatus(voitureActionForm.getStatus());
    vpo.setVehicleNum(voitureActionForm.getVehicleNum());
    VoitureTypePO voitureTypePO = vmbd.loadVoitureType(voitureActionForm
        .getVoitureTypeId().toString());
    vpo.setVoitureTypePO(voitureTypePO);
    vpo.setDomainId(Long.valueOf(domainId));
    int result = vmbd.updateVoiture(vpo, id).intValue();
    if (result > 0) {
      httpServletRequest.setAttribute("opResult", String.valueOf(result));
    } else {
      voitureActionForm.setName("");
    } 
    vmbd.saveMessage(session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
        String.valueOf(vpo.getNum()) + "的车辆信息已修改", "Voiture", id, "/jsoa/VoitureApplyAction.do?flag=info&onlySee=1&voitureId=" + id);
  }
  
  private List checkVoitureApplyWithCurrent(List<Object[]> list, VoitureManagerService vmbd) {
    if (list != null && list.size() > 0) {
      String voitureId = "";
      SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd");
      String date = sdm.format(Calendar.getInstance().getTime());
      List<Object[]> voApList = vmbd.getVaIdHasStatusInCurrent(date, 
          voitureId.substring(0, voitureId.lastIndexOf(",")));
      int i;
      for (i = 0; i < voApList.size(); i++) {
        Object[] obj = voApList.get(i);
        voitureId = String.valueOf(voitureId) + obj[0] + ", ";
      } 
      voApList = vmbd.getVaIdHasStatusInCurrent(date, 
          voitureId.substring(0, voitureId.lastIndexOf(",")));
      for (i = 0; i < list.size(); i++) {
        Object[] objVo = list.get(i);
        for (int j = 0; j < voApList.size(); j++) {
          Object[] objApp = voApList.get(j);
          if (!objVo[0].equals(objApp[1]));
        } 
      } 
    } 
    return null;
  }
  
  private String scopeWhere(HttpServletRequest httpServletRequest, String rightType, String rightName) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).getRightFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        rightType, 
        rightName, 
        "po.createdOrg", 
        "po.createdEmp");
  }
  
  private String getCanModify(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    ManagerService managerBD = new ManagerService();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSQL = "po.id";
    String fromSQL = "com.js.oa.routine.voiture.po.VoiturePO po ";
    String whereSQL = " where   po.domainId=" + 
      
      domainId;
    String rightWhere = managerBD.getRightFinalWhere(session.getAttribute("userId").toString(), session.getAttribute("orgId").toString(), 
        "07*14*02", "po.orgId", "po.createdEmp");
    whereSQL = String.valueOf(whereSQL) + " and " + rightWhere;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(99999);
    page.setcurrentPage(1);
    List<String> list = page.getResultList();
    String result = ",";
    if (list != null)
      for (int i = 0; i < list.size(); i++)
        result = String.valueOf(result) + list.get(i) + 
          ",";  
    return result;
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
}
