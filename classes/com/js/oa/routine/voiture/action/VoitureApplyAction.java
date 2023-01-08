package com.js.oa.routine.voiture.action;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.routine.voiture.bean.VoitureEJBBean;
import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SysConfig;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ParameterFilter;
import java.text.SimpleDateFormat;
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

public class VoitureApplyAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute(
        "userId").toString();
    String orgIdString = session.getAttribute(
        "orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    VoitureApplyActionForm voitureApplyActionForm = 
      (VoitureApplyActionForm)actionForm;
    VoitureManagerService vbd = new VoitureManagerService();
    String flag = (httpServletRequest.getParameter("flag") == null) ? 
      "useview" : 
      httpServletRequest.getParameter("flag");
    String tag = "useview";
    if (!ParameterFilter.isNumber(httpServletRequest.getParameter("processId")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("tableId")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("voitureId")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("record")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("applyId")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("moduleId")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("work")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("workType")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("activity")) || 
      !ParameterFilter.isNumber(httpServletRequest.getParameter("workStatus")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    if (flag.equals("add")) {
      tag = "add";
      if (httpServletRequest.getParameter("processId") == null) {
        ProcessBD procbd = new ProcessBD();
        List<Object[]> list = new ArrayList();
        Object tmpl = procbd.getUserProcess(curUserId, orgIdString, 
            "11");
        if (tmpl != null)
          list = (List)tmpl; 
        if (list != null && list.size() == 1) {
          addApply(httpServletRequest, actionForm);
          Object[] rfObj = list.get(0);
          Object object1 = rfObj[2];
          Object object2 = rfObj[3];
          Object object3 = rfObj[5];
          Object object4 = rfObj[4];
          Object object5 = (rfObj[6] == null) ? "" : rfObj[6];
          String linkValue = "&processType=" + object3 + 
            "&remindField=" + object5 + 
            "&processId=" + object1 + "&tableId=" + 
            object4 + "&processName=" + object2;
          return new ActionForward(
              "/VoitureApplyAction.do?flag=add" + ((httpServletRequest.getParameter("voitureId") != null) ? ("&voitureId=" + 
              httpServletRequest.getParameter("voitureId")) : "") + 
              linkValue + 
              "&moduleId=11");
        } 
        if (list != null && list.size() > 1) {
          tag = "selectWorkFlow";
          httpServletRequest.setAttribute("voitureFlowlist", list);
        } else {
          httpServletRequest.setAttribute("noWorkFlow", "1");
        } 
      } else {
        addApply(httpServletRequest, actionForm);
      } 
      httpServletRequest.setAttribute("carPoolId", (httpServletRequest.getParameter("carPoolId") == null) ? "0" : httpServletRequest.getParameter("carPoolId"));
      httpServletRequest.setAttribute("isPC", (httpServletRequest.getParameter("isPC") == null) ? "0" : httpServletRequest.getParameter("isPC"));
    } else if (flag.equals("useadd")) {
      tag = "useadd";
      addApply(httpServletRequest, actionForm);
    } else if (flag.equals("modi")) {
      tag = "modi";
      String applyId = httpServletRequest.getParameter("record");
      httpServletRequest.setAttribute("applyId", applyId);
      HttpSession httpSession = httpServletRequest.getSession(true);
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String userId = httpSession.getAttribute("userId").toString();
      String orgString = httpSession.getAttribute("orgIdString").toString();
      List voitureList = vbd.listVoiture("0", domainId, userName, orgName, userId, orgString);
      httpServletRequest.setAttribute("voitureList", voitureList);
      loadApply(applyId, voitureApplyActionForm, httpServletRequest);
      WorkflowCommon workflowCommon = new WorkflowCommon();
      String curModifyField = "";
      if (httpServletRequest.getParameter("resubmit") != null && 
        "1".equals(httpServletRequest.getParameter("resubmit"))) {
        curModifyField = "";
      } else {
        curModifyField = workflowCommon.getCurActivityWriteField(
            httpServletRequest);
      } 
      httpServletRequest.setAttribute("curModifyField", curModifyField);
      httpServletRequest.setAttribute("myform", voitureApplyActionForm);
    } else if (flag.equals("next")) {
      tag = "next";
      getNextStep(httpServletRequest);
    } else {
      if (flag.equals("oper")) {
        oper(httpServletRequest);
        return actionMapping.findForward("next");
      } 
      if ("comp".equals(flag)) {
        comp(httpServletRequest);
        return actionMapping.findForward("next");
      } 
      if ("reSub".equals(flag)) {
        reSub(httpServletRequest, voitureApplyActionForm);
        tag = "modi";
        String applyId = httpServletRequest.getParameter("applyId");
        httpServletRequest.setAttribute("applyId", applyId);
        loadApply(applyId, voitureApplyActionForm, httpServletRequest);
      } else {
        if ("trans".equals(flag)) {
          trans(httpServletRequest, voitureApplyActionForm);
          return actionMapping.findForward("next");
        } 
        if (flag.equals("update")) {
          tag = "modi";
          String applyId = httpServletRequest.getParameter("applyId");
          updateApply(httpServletRequest, actionForm, applyId);
        } else if (flag.equals("save")) {
          tag = "save";
          saveApply(httpServletRequest, actionForm);
          httpServletRequest.setAttribute("shouldClose", "1");
        } else if (flag.equals("useview")) {
          tag = "useview";
          useview(httpServletRequest);
        } else if (flag.equals("del")) {
          tag = "useview";
          delApply(httpServletRequest, actionForm);
        } else if (flag.equals("auditing")) {
          tag = "useview";
          String status = httpServletRequest.getParameter("status");
          auditingApply(httpServletRequest, status);
        } else if (flag.equals("info")) {
          tag = "view";
          voitureInfo(httpServletRequest, voitureApplyActionForm);
        } else if (flag.equals("search")) {
          tag = "useview";
          useview(httpServletRequest);
        } else if ("isOver".equals(flag)) {
          tag = "add";
          VoitureApplyPO vapo = new VoitureApplyPO();
          String startDate = httpServletRequest.getParameter("startDate");
          String endDate = httpServletRequest.getParameter("endDate");
          String startHour = voitureApplyActionForm.getStartHour();
          String startMinute = voitureApplyActionForm.getStartMinute();
          String endHour = voitureApplyActionForm.getEndHour();
          String endMinute = voitureApplyActionForm.getEndMinute();
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
          vapo.setStartDate(new Date(startDate));
          vapo.setEndDate(new Date(endDate));
          vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
                  startMinute)));
          vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
                  endMinute)));
          if (isSameDateTime(vapo))
            httpServletRequest.setAttribute("result", "yes"); 
        } 
      } 
    } 
    if (flag.equals("notrave")) {
      tag = "notrave";
      noTraveUseview(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  public void addApply(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String orgString = httpSession.getAttribute("orgIdString").toString();
    VoitureApplyActionForm voitureApplyActionForm = 
      (VoitureApplyActionForm)actionForm;
    voitureApplyActionForm.setVoitureId(Long.valueOf(
          (httpServletRequest.getParameter("voitureId") == null) ? "0" : 
          httpServletRequest
          .getParameter("voitureId").toString()));
    List voitureList = vmbd.listVoiture("0", domainId, userName, orgName, userId, orgString);
    httpServletRequest.setAttribute("voitureList", voitureList);
    String voitureName = "";
    List vList = vmbd.listVoiture(
        voitureApplyActionForm.getVoitureId().toString(), domainId, 
        userName, orgName, userId, orgString);
    if (vList != null && vList.size() > 0)
      voitureName = ((Object[])vList.get(0))[1].toString(); 
    voitureApplyActionForm.setVoitureName(voitureName);
    if (!voitureApplyActionForm.getVoitureId().toString().equals("0")) {
      voitureApplyActionForm.setMotorMan(vmbd.getVoiturePO(
            voitureApplyActionForm.getVoitureId().toString())
          .getMotorMan());
      voitureApplyActionForm.setVehicleNum(vmbd.getVoiturePO(
            voitureApplyActionForm.getVoitureId().toString())
          .getVehicleNum());
    } 
    voitureApplyActionForm.setOrgId(orgId);
    voitureApplyActionForm.setOrgName(orgName);
    voitureApplyActionForm.setEmpId(userId);
    voitureApplyActionForm.setEmpName(userName);
  }
  
  public void saveApply(HttpServletRequest httpServletRequest, ActionForm actionForm) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyActionForm voitureApplyActionForm = 
      (VoitureApplyActionForm)actionForm;
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(voitureApplyActionForm.getEmpId());
    vapo.setOrgId(voitureApplyActionForm.getOrgId());
    vapo.setOrgName(voitureApplyActionForm.getOrgName());
    vapo.setVoitureId(voitureApplyActionForm.getVoitureId());
    vapo.setDestination(voitureApplyActionForm.getDestination());
    vapo.setEmpName(voitureApplyActionForm.getEmpName());
    vapo.setReason(voitureApplyActionForm.getReason());
    vapo.setYcr(voitureApplyActionForm.getYcr());
    vapo.setDomainId(Long.valueOf(domainId));
    if (httpServletRequest.getParameter("processId") != null) {
      vapo.setStatus("1");
    } else {
      vapo.setStatus("2");
    } 
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = voitureApplyActionForm.getStartHour();
    String startMinute = voitureApplyActionForm.getStartMinute();
    String endHour = voitureApplyActionForm.getEndHour();
    String endMinute = voitureApplyActionForm.getEndMinute();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    vapo.setStartDate(new Date(startDate));
    vapo.setEndDate(new Date(endDate));
    vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
            startMinute)));
    vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
            endMinute)));
    if (!isSameDateTime(vapo)) {
      vapo.setFillDate(formatter1.parse(formatter1.format(new Date())));
      if (voitureApplyActionForm.getPersonNum() != null && 
        !voitureApplyActionForm.getPersonNum().equals(""))
        vapo.setPersonNum(voitureApplyActionForm.getPersonNum()); 
      vapo.setMotorMan(voitureApplyActionForm.getMotorMan());
      vapo.setVoitureStyle(voitureApplyActionForm.getVoitureStyle());
      vapo.setRemark(voitureApplyActionForm.getRemark());
      vapo.setDeparturePlace(voitureApplyActionForm.getDeparturePlace());
      vapo.setVehiclePhone(voitureApplyActionForm.getVehiclePhone());
      vapo.setVehicleNum(voitureApplyActionForm.getVehicleNum());
      int result = vmbd.saveVoitureApply(vapo).intValue();
      if (httpServletRequest.getParameter("processId") != null)
        saveProcess(httpServletRequest, (new StringBuilder(String.valueOf(result))).toString()); 
      httpServletRequest.setAttribute("opResult", String.valueOf(result));
    } else {
      httpServletRequest.setAttribute("sameDateTime", "yes");
    } 
  }
  
  public boolean isSameDateTime(VoitureApplyPO vapo) {
    if (vapo != null) {
      DbOpt opt = new DbOpt();
      String sql = "";
      try {
        String dbType = 
          SysConfig.getDatabaseType();
        SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd");
        if ("oracle".equals(dbType)) {
          sql = "SELECT COUNT(*) FROM VEH_APPLY  WHERE (STARTDATE = TO_DATE('" + 
            
            format.format(vapo.getStartDate()) + 
            "','yyyy-mm-dd')) " + 
            " AND (ENDDATE = TO_DATE('" + 
            format.format(vapo.getEndDate()) + 
            "','yyyy-mm-dd')) " + 
            " AND ((STARTTIME <= '" + vapo.getStartTime() + 
            "' " + 
            " AND ENDTIME > '" + vapo.getStartTime() + 
            "')" + 
            " OR (STARTTIME < '" + vapo.getEndTime() + 
            "' AND ENDTIME >= '" + vapo.getEndTime() + 
            "')) " + 
            " ORDER BY STARTDATE DESC";
        } else {
          sql = "SELECT COUNT(*) FROM VEH_APPLY  WHERE (STARTDATE = '" + 
            
            format.format(vapo.getStartDate()) + "') " + 
            " AND (ENDDATE = '" + 
            format.format(vapo.getEndDate()) + "') " + 
            " AND ((STARTTIME <= '" + vapo.getStartTime() + 
            "' " + 
            " AND ENDTIME > '" + vapo.getStartTime() + 
            "')" + 
            " OR (STARTTIME < '" + vapo.getEndTime() + 
            "' AND ENDTIME >= '" + vapo.getEndTime() + 
            "')) " + 
            " ORDER BY STARTDATE DESC";
        } 
        String retStr = opt.executeQueryToStr(sql);
        if (retStr != null && Integer.parseInt(retStr) > 0)
          return true; 
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        try {
          opt.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
    } 
    return false;
  }
  
  public void useview(HttpServletRequest servletRequest) {
    HttpSession session = servletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSql = 
      "po.id,vpo.num,po.motorMan,po.empName,po.destination,po.status,po.fillDate,po.empId,po.ycr ";
    String fromSql = "com.js.oa.routine.voiture.po.VoitureApplyPO po,com.js.oa.routine.voiture.po.VoiturePO vpo";
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
    servletRequest.setAttribute("voitureMtRight", 
        String.valueOf(voitureMtRight));
    String range = vmbd.getVoitureUseRange(curUserName, orgName, curUserId, orgIdString);
    if (!range.equals("")) {
      whereSql = String.valueOf(whereSql) + "(";
      whereSql = String.valueOf(whereSql) + "vpo.id in(" + range.substring(0, range.length() - 1) + 
        ")";
      if (voitureMtRight || voitureAdRight)
        whereSql = String.valueOf(whereSql) + " or (" + 
          mbd.getRightFinalWhere(curUserId, curOrgId, 
            orgIdString, "车辆管理", "维护", 
            "vpo.orgId", 
            "vpo.createdEmp") + 
          ") "; 
      whereSql = String.valueOf(whereSql) + ")";
    } else if (voitureMtRight) {
      whereSql = String.valueOf(whereSql) + " (" + mbd.getRightFinalWhere(curUserId, curOrgId, 
          orgIdString, "车辆管理", "维护", "vpo.orgId", 
          "vpo.voitureTypePO.createdEmp") + " ) ";
    } else {
      whereSql = String.valueOf(whereSql) + "1<>1";
    } 
    whereSql = String.valueOf(whereSql) + " and po.voitureId=vpo.id";
    if (servletRequest.getParameter("searchMotorMan") != null && 
      !servletRequest.getParameter("searchMotorMan").equals(""))
      whereSql = String.valueOf(whereSql) + " and po.motorMan like '%" + 
        servletRequest.getParameter("searchMotorMan") + "%'"; 
    if (servletRequest.getParameter("searchNum") != null && 
      !servletRequest.getParameter("searchNum").equals(""))
      whereSql = String.valueOf(whereSql) + " and vpo.num like '%" + 
        servletRequest.getParameter("searchNum") + "%'"; 
    if (servletRequest.getParameter("searchEmpName") != null && 
      !servletRequest.getParameter("searchEmpName").equals(""))
      whereSql = String.valueOf(whereSql) + " and po.empName like '%" + 
        servletRequest.getParameter("searchEmpName") + "%'"; 
    String isDate = (servletRequest.getParameter("isDate") == null) ? "" : 
      servletRequest.getParameter("isDate");
    if (isDate.equals("1") && 
      servletRequest.getParameter("searchFillDate") != null && 
      !servletRequest.getParameter("searchFillDate").equals("")) {
      String searchFillDate = servletRequest.getParameter(
          "searchFillDate");
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and po.fillDate='" + searchFillDate + "'";
      } else {
        whereSql = String.valueOf(whereSql) + " and po.fillDate=JSDB.FN_STRTODATE('" + 
          searchFillDate + "','S')";
      } 
    } 
    String searchStatus = (servletRequest.getParameter("searchStatus") == null) ? 
      "" : servletRequest.getParameter("searchStatus");
    if (!searchStatus.equals("0") && !searchStatus.equals(""))
      whereSql = String.valueOf(whereSql) + " and po.status='" + 
        servletRequest.getParameter("searchStatus") + "'"; 
    if (servletRequest.getParameter("searchDestination") != null && 
      !servletRequest.getParameter("searchDestination").equals(""))
      whereSql = String.valueOf(whereSql) + " and po.destination like '%" + 
        servletRequest.getParameter("searchDestination") + "%'"; 
    whereSql = String.valueOf(whereSql) + " and po.domainId=" + domainId + " order by po.id desc";
    boolean voitureAddRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    servletRequest.setAttribute("voitureAddRight", 
        String.valueOf(voitureAddRight));
    boolean voitureAdminRight = mbd.hasRightTypeName(curUserId, "车辆管理", 
        "审核");
    servletRequest.setAttribute("voitureAdminRight", 
        String.valueOf(voitureAdminRight));
    useList(servletRequest, viewSql, fromSql, whereSql);
    servletRequest.setAttribute("canModifyList", getCanModify(servletRequest));
    servletRequest.setAttribute("canShList", getSHModify(servletRequest));
  }
  
  public void noTraveUseview(HttpServletRequest servletRequest) {
    HttpSession session = servletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSql = 
      "po.id,vpo.name,vpo.num,po.motorMan,po.empName,po.departurePlace,po.destination,po.startDate,po.startTime,po.endDate,po.endTime,po.voitureId,po.carPoolId ";
    String fromSql = "com.js.oa.routine.voiture.po.VoitureApplyPO po,com.js.oa.routine.voiture.po.VoiturePO vpo";
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
    servletRequest.setAttribute("voitureMtRight", 
        String.valueOf(voitureMtRight));
    String range = vmbd.getVoitureUseRange(curUserName, orgName, curUserId, orgIdString);
    if (!range.equals("")) {
      whereSql = String.valueOf(whereSql) + "(";
      whereSql = String.valueOf(whereSql) + "vpo.id in(" + range.substring(0, range.length() - 1) + 
        ")";
      if (voitureMtRight || voitureAdRight)
        whereSql = String.valueOf(whereSql) + " or (" + 
          mbd.getRightFinalWhere(curUserId, curOrgId, 
            orgIdString, "车辆管理", "维护", 
            "vpo.orgId", 
            "vpo.createdEmp") + 
          ") "; 
      whereSql = String.valueOf(whereSql) + ")";
    } else if (voitureMtRight) {
      whereSql = String.valueOf(whereSql) + " (" + mbd.getRightFinalWhere(curUserId, curOrgId, 
          orgIdString, "车辆管理", "维护", "vpo.orgId", 
          "vpo.voitureTypePO.createdEmp") + " ) ";
    } else {
      whereSql = String.valueOf(whereSql) + "1<>1";
    } 
    whereSql = String.valueOf(whereSql) + " and po.voitureId=vpo.id";
    if (servletRequest.getParameter("searchMotorMan") != null && 
      !servletRequest.getParameter("searchMotorMan").equals(""))
      whereSql = String.valueOf(whereSql) + " and po.motorMan like '%" + 
        servletRequest.getParameter("searchMotorMan") + "%'"; 
    if (servletRequest.getParameter("searchNum") != null && 
      !servletRequest.getParameter("searchNum").equals(""))
      whereSql = String.valueOf(whereSql) + " and vpo.num like '%" + 
        servletRequest.getParameter("searchNum") + "%'"; 
    if (servletRequest.getParameter("searchEmpName") != null && 
      !servletRequest.getParameter("searchEmpName").equals(""))
      whereSql = String.valueOf(whereSql) + " and po.empName like '%" + 
        servletRequest.getParameter("searchEmpName") + "%'"; 
    String isDate = (servletRequest.getParameter("isDate") == null) ? "" : 
      servletRequest.getParameter("isDate");
    if (isDate.equals("1") && 
      servletRequest.getParameter("searchFillDate") != null && 
      !servletRequest.getParameter("searchFillDate").equals("")) {
      String searchFillDate = servletRequest.getParameter(
          "searchFillDate");
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and po.fillDate='" + searchFillDate + "'";
      } else {
        whereSql = String.valueOf(whereSql) + " and po.fillDate=JSDB.FN_STRTODATE('" + 
          searchFillDate + "','S')";
      } 
    } 
    String searchStatus = (servletRequest.getParameter("searchStatus") == null) ? 
      "" : servletRequest.getParameter("searchStatus");
    if (!searchStatus.equals("0") && !searchStatus.equals(""))
      whereSql = String.valueOf(whereSql) + " and po.status='" + 
        servletRequest.getParameter("searchStatus") + "'"; 
    if (servletRequest.getParameter("searchDestination") != null && 
      !servletRequest.getParameter("searchDestination").equals(""))
      whereSql = String.valueOf(whereSql) + " and po.destination like '%" + 
        servletRequest.getParameter("searchDestination") + "%'"; 
    whereSql = String.valueOf(whereSql) + " and (po.status=1 or po.status=2) and po.carPoolId = 0";
    whereSql = String.valueOf(whereSql) + " and po.domainId=" + domainId + " order by po.id desc";
    boolean voitureAddRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    servletRequest.setAttribute("voitureAddRight", 
        String.valueOf(voitureAddRight));
    boolean voitureAdminRight = mbd.hasRightTypeName(curUserId, "车辆管理", 
        "审核");
    servletRequest.setAttribute("voitureAdminRight", 
        String.valueOf(voitureAdminRight));
    int pageSize = 15;
    int offset = 0;
    if (servletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(servletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      servletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      servletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    servletRequest.setAttribute("list", list);
    servletRequest.setAttribute("recordCount", recordCount);
    servletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    servletRequest.setAttribute("pageParameters", "flag,searchNum,searchEmpName,isDate,searchFillDate,searchStatus,searchDestination");
    servletRequest.setAttribute("canModifyList", getCanModify(servletRequest));
    servletRequest.setAttribute("canShList", getSHModify(servletRequest));
  }
  
  private void useList(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
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
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      if (obj[7].toString().equals(curUserId)) {
        obj[7] = "true";
      } else {
        obj[7] = "false";
      } 
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,searchNum,searchEmpName,isDate,searchFillDate,searchStatus,searchDestination");
  }
  
  private String getCanModify(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId")
      .toString();
    String orgIdString = httpServletRequest.getSession(true).getAttribute(
        "orgIdString").toString();
    ManagerService mbd = new ManagerService();
    String viewSql = 
      "po.id";
    String fromSql = "com.js.oa.routine.voiture.po.VoitureApplyPO po,com.js.oa.routine.voiture.po.VoiturePO vpo";
    String whereSql = " where  po.voitureId=vpo.id and " + 
      mbd.getRightFinalWhere(curUserId, curOrgId, 
        orgIdString, "车辆管理", "维护", "vpo.orgId", 
        "vpo.createdEmp");
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List<String> list = page.getResultList();
    String retStr = ",";
    if (list != null)
      for (int i = 0; i < list.size(); i++)
        retStr = String.valueOf(retStr) + list.get(i) + ",";  
    return retStr;
  }
  
  private String getSHModify(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId")
      .toString();
    String orgIdString = httpServletRequest.getSession(true).getAttribute(
        "orgIdString").toString();
    ManagerService mbd = new ManagerService();
    String viewSql = 
      "po.id";
    String fromSql = "com.js.oa.routine.voiture.po.VoitureApplyPO po,com.js.oa.routine.voiture.po.VoiturePO vpo";
    String whereSql = " where  po.voitureId=vpo.id and " + 
      mbd.getRightFinalWhere(curUserId, curOrgId, 
        orgIdString, "车辆管理", "审核", "vpo.orgId", 
        "vpo.createdEmp");
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List<String> list = page.getResultList();
    String retStr = ",";
    if (list != null)
      for (int i = 0; i < list.size(); i++)
        retStr = String.valueOf(retStr) + list.get(i) + ",";  
    return retStr;
  }
  
  public void delApply(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String delId = (httpServletRequest.getParameter("delId") == null) ? "" : 
      httpServletRequest.getParameter("delId").toString();
    boolean bl = vmbd.delVoitureApply(delId);
    if (bl) {
      httpServletRequest.setAttribute("isDel", "true");
    } else {
      httpServletRequest.setAttribute("isDel", "false");
    } 
    useview(httpServletRequest);
  }
  
  public void auditingApply(HttpServletRequest httpServletRequest, String status) {
    String applyId = httpServletRequest.getParameter("applyId");
    VoitureManagerService vmbd = new VoitureManagerService();
    vmbd.auditingApply(applyId, status);
    useview(httpServletRequest);
  }
  
  public void voitureInfo(HttpServletRequest httpServletRequest, VoitureApplyActionForm voitureApplyActionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String voitureId = httpServletRequest.getParameter("voitureId");
    httpServletRequest.setAttribute("voitureId", voitureId);
    List<Date> searchDate = new ArrayList();
    List<String[]> placeList = new ArrayList();
    Date searchStartDate = new Date();
    if (httpServletRequest.getParameter("searchStartDate") != null)
      searchStartDate = new Date(httpServletRequest.getParameter(
            "searchStartDate")); 
    long day = 3L;
    searchDate.add(searchStartDate);
    placeList.add(vmbd.getVoitureInfo(voitureId, 
          formatter.format(searchStartDate)));
    for (int i = 1; i < day; i++) {
      Date searchNextDate = new Date();
      long myTime = searchStartDate.getTime() / 1000L + (
        86400 * i);
      searchNextDate.setTime(myTime * 1000L);
      searchDate.add(searchNextDate);
      placeList.add(vmbd.getVoitureInfo(voitureId, 
            formatter.format(searchNextDate)));
    } 
    httpServletRequest.setAttribute("searchDate", searchDate);
    httpServletRequest.setAttribute("voiturePlace", placeList);
  }
  
  public void loadApply(String applyId, VoitureApplyActionForm voitureApplyActionForm, HttpServletRequest httpServletRequest) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO voitureApplyPO = vmbd.loadVoitureApply(applyId);
    voitureApplyActionForm.setEmpId(voitureApplyPO.getEmpId());
    voitureApplyActionForm.setOrgId(voitureApplyPO.getOrgId());
    voitureApplyActionForm.setOrgName(voitureApplyPO.getOrgName());
    voitureApplyActionForm.setVoitureId(voitureApplyPO.getVoitureId());
    if (voitureApplyPO.getVoitureId() != null && 
      !voitureApplyPO.getVoitureId().toString().equals(""))
      voitureApplyActionForm.setVoitureName(vmbd.getVoiturePO(
            voitureApplyPO
            .getVoitureId().toString()).getName()); 
    voitureApplyActionForm.setDestination(voitureApplyPO
        .getDestination());
    voitureApplyActionForm.setEmpName(voitureApplyPO.getEmpName());
    voitureApplyActionForm.setReason(voitureApplyPO.getReason());
    voitureApplyActionForm.setStatus(voitureApplyPO.getStatus());
    voitureApplyActionForm.setYcr(voitureApplyPO.getYcr());
    voitureApplyActionForm.setPersonNum(voitureApplyPO.getPersonNum());
    voitureApplyActionForm.setMotorMan((voitureApplyPO.getMotorMan() == null) ? 
        "" : voitureApplyPO.getMotorMan());
    voitureApplyActionForm.setVoitureStyle(voitureApplyPO.getVoitureStyle());
    voitureApplyActionForm.setRemark(voitureApplyPO.getRemark());
    voitureApplyActionForm.setDeparturePlace(voitureApplyPO.getDeparturePlace());
    voitureApplyActionForm.setVehiclePhone(voitureApplyPO.getVehiclePhone());
    voitureApplyActionForm.setVehicleNum(voitureApplyPO.getVehicleNum());
    String startDate = voitureApplyPO.getStartDate().toString();
    String endDate = voitureApplyPO.getEndDate().toString();
    String startTime = voitureApplyPO.getStartTime().toString();
    String endTime = voitureApplyPO.getEndTime().toString();
    httpServletRequest.setAttribute("startDate", startDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("carPoolId", voitureApplyPO.getCarPoolId());
    String viewId = applyId;
    if (!"0".equals(voitureApplyPO.getCarPoolId()))
      viewId = voitureApplyPO.getCarPoolId(); 
    voitureApplyActionForm.setStartHour(startTime.split("\\:")[0]);
    voitureApplyActionForm.setStartMinute(startTime.split("\\:")[1]);
    voitureApplyActionForm.setEndHour(endTime.split("\\:")[0]);
    voitureApplyActionForm.setEndMinute(endTime.split("\\:")[1]);
    VoitureEJBBean voitureEJBBean = new VoitureEJBBean();
    List<VoitureApplyPO> carPoolList = null;
    try {
      carPoolList = voitureEJBBean.listVoitureCarPools(new Long(applyId));
      if (carPoolList != null && carPoolList.size() > 0)
        for (int i = 0; i < carPoolList.size(); i++) {
          VoitureApplyPO po = carPoolList.get(i);
          if (po.getId() == new Long(viewId))
            carPoolList.remove(i); 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    httpServletRequest.setAttribute("carPoolList", carPoolList);
  }
  
  public void updateApply(HttpServletRequest httpServletRequest, ActionForm actionForm, String applyId) throws Exception {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyActionForm voitureApplyActionForm = 
      (VoitureApplyActionForm)actionForm;
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(voitureApplyActionForm.getEmpId());
    vapo.setOrgId(voitureApplyActionForm.getOrgId());
    vapo.setOrgName(voitureApplyActionForm.getOrgName());
    vapo.setVoitureId(voitureApplyActionForm.getVoitureId());
    vapo.setDestination(voitureApplyActionForm.getDestination());
    vapo.setEmpName(voitureApplyActionForm.getEmpName());
    vapo.setReason(voitureApplyActionForm.getReason());
    vapo.setStatus("2");
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = voitureApplyActionForm.getStartHour();
    String startMinute = voitureApplyActionForm.getStartMinute();
    String endHour = voitureApplyActionForm.getEndHour();
    String endMinute = voitureApplyActionForm.getEndMinute();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    vapo.setStartDate(new Date(startDate));
    vapo.setEndDate(new Date(endDate));
    vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
            startMinute)));
    vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
            endMinute)));
    vapo.setFillDate(new Date());
    int result = vmbd.updateVoitureApply(vapo, applyId).intValue();
    httpServletRequest.setAttribute("opResult", String.valueOf(result));
  }
  
  private void saveProcess(HttpServletRequest httpServletRequest, String recordId) {
    int moduleId = 10, formType = 0;
    String mainLinkFile = 
      "/jsoa/VoitureApplyAction.do?flag=modi&applyId=" + 
      recordId;
    String cancelFile = "window.open('/jsoa/jsflow/workflow_cancelReason.jsp?workStatus=1&workId=workIdValue&tableId=tableIdValue&processName=" + 
      httpServletRequest.getParameter("fileType") + 
      "&recordId=" + recordId + "&processId=" + 
      httpServletRequest.getParameter("processId") + 
      "&remindValue=&" + 
      "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')";
    (new ProcessSubmit()).saveProcess(httpServletRequest, 
        recordId, 
        moduleId, 
        formType, 
        mainLinkFile, 
        cancelFile);
  }
  
  private void getNextStep(HttpServletRequest httpServletRequest) {
    (new ProcessSubmit()).getNextStep(httpServletRequest);
  }
  
  private void oper(HttpServletRequest httpServletRequest) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(httpServletRequest.getParameter("empId"));
    vapo.setOrgId(httpServletRequest.getParameter("orgId"));
    vapo.setOrgName(httpServletRequest.getParameter("orgName"));
    vapo.setVoitureId(Long.valueOf(httpServletRequest.getParameter(
            "voitureId")));
    vapo.setDestination(httpServletRequest.getParameter("destination"));
    vapo.setEmpName(httpServletRequest.getParameter("empName"));
    vapo.setReason(httpServletRequest.getParameter("reason"));
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = httpServletRequest.getParameter("startHour");
    String startMinute = httpServletRequest.getParameter("startMinute");
    String endHour = httpServletRequest.getParameter("endHour");
    String endMinute = httpServletRequest.getParameter("endMinute");
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
      vapo.setStartDate(new Date(startDate));
      vapo.setEndDate(new Date(endDate));
      vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
              startMinute)));
      vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
              endMinute)));
    } catch (Exception exception) {}
    vapo.setFillDate(new Date());
    vapo.setStatus("1");
    int result = vmbd.updateVoitureApply(vapo, 
        httpServletRequest
        .getParameter("recordId")).intValue();
    String toMainFile = 
      "/jsoa/VoitureApplyAction.do?flag=modi&applyId=" + 
      httpServletRequest.getParameter("recordId");
    (new ProcessSubmit()).operate(httpServletRequest, toMainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void comp(HttpServletRequest httpServletRequest) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(httpServletRequest.getParameter("empId"));
    vapo.setOrgId(httpServletRequest.getParameter("orgId"));
    vapo.setOrgName(httpServletRequest.getParameter("orgName"));
    vapo.setVoitureId(Long.valueOf(httpServletRequest.getParameter(
            "voitureId")));
    vapo.setDestination(httpServletRequest.getParameter("destination"));
    vapo.setEmpName(httpServletRequest.getParameter("empName"));
    vapo.setReason(httpServletRequest.getParameter("reason"));
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = httpServletRequest.getParameter("startHour");
    String startMinute = httpServletRequest.getParameter("startMinute");
    String endHour = httpServletRequest.getParameter("endHour");
    String endMinute = httpServletRequest.getParameter("endMinute");
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
      vapo.setStartDate(new Date(startDate));
      vapo.setEndDate(new Date(endDate));
      vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
              startMinute)));
      vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
              endMinute)));
    } catch (Exception exception) {}
    vapo.setFillDate(new Date());
    vapo.setStatus("2");
    int result = vmbd.updateVoitureApply(vapo, 
        httpServletRequest
        .getParameter("recordId")).intValue();
    httpServletRequest.setAttribute("close", "1");
    (new ProcessSubmit()).comp(httpServletRequest);
  }
  
  private void reSub(HttpServletRequest httpServletRequest, VoitureApplyActionForm voitureApplyActionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    vmbd.delVoitureApply(httpServletRequest.getParameter("applyId"));
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(voitureApplyActionForm.getEmpId());
    vapo.setOrgId(voitureApplyActionForm.getOrgId());
    vapo.setOrgName(voitureApplyActionForm.getOrgName());
    vapo.setVoitureId(voitureApplyActionForm.getVoitureId());
    vapo.setDestination(voitureApplyActionForm.getDestination());
    vapo.setEmpName(voitureApplyActionForm.getEmpName());
    vapo.setReason(voitureApplyActionForm.getReason());
    vapo.setStatus("1");
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = voitureApplyActionForm.getStartHour();
    String startMinute = voitureApplyActionForm.getStartMinute();
    String endHour = voitureApplyActionForm.getEndHour();
    String endMinute = voitureApplyActionForm.getEndMinute();
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
      vapo.setStartDate(new Date(startDate));
      vapo.setEndDate(new Date(endDate));
      vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
              startMinute)));
      vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
              endMinute)));
    } catch (Exception exception) {}
    vapo.setFillDate(new Date());
    int result = vmbd.saveVoitureApply(vapo).intValue();
    saveProcess(httpServletRequest, (new StringBuilder(String.valueOf(result))).toString());
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void trans(HttpServletRequest httpServletRequest, VoitureApplyActionForm voitureApplyActionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(voitureApplyActionForm.getEmpId());
    vapo.setOrgId(voitureApplyActionForm.getOrgId());
    vapo.setOrgName(voitureApplyActionForm.getOrgName());
    vapo.setVoitureId(voitureApplyActionForm.getVoitureId());
    vapo.setDestination(voitureApplyActionForm.getDestination());
    vapo.setEmpName(voitureApplyActionForm.getEmpName());
    vapo.setReason(voitureApplyActionForm.getReason());
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = voitureApplyActionForm.getStartHour();
    String startMinute = voitureApplyActionForm.getStartMinute();
    String endHour = voitureApplyActionForm.getEndHour();
    String endMinute = voitureApplyActionForm.getEndMinute();
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
      vapo.setStartDate(new Date(startDate));
      vapo.setEndDate(new Date(endDate));
      vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
              startMinute)));
      vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
              endMinute)));
    } catch (Exception exception) {}
    vapo.setStatus("1");
    int result = vmbd.updateVoitureApply(vapo, 
        httpServletRequest
        .getParameter("recordId")).intValue();
    (new ProcessSubmit()).transition(httpServletRequest, 
        "/jsoa/VoitureApplyAction.do?flag=modi&applyId=" + 
        httpServletRequest.getParameter(
          "recordId"));
    httpServletRequest.setAttribute("close", "1");
  }
}
