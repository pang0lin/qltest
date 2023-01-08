package com.js.oa.jsflow.action;

import com.js.oa.eform.service.FormBD;
import com.js.oa.info.util.InfoArchives;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.service.ModuleBD;
import com.js.oa.jsflow.service.PackageBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.DataSynchro;
import com.js.oa.jsflow.util.ExportFlow;
import com.js.oa.jsflow.util.ImportFlow;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.SimpleFieldVO;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WFProcessAction extends Action {
  public void jsflog(HttpServletRequest httpServletRequest, String moduleCode, String moduleName, String oprType, String oprContent) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    Date endDate = new Date();
    HttpSession sess = httpServletRequest.getSession(true);
    logBD.log(sess.getAttribute("userId").toString(), sess.getAttribute("userName").toString(), 
        sess.getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, 
        oprType, oprContent, httpServletRequest.getRemoteAddr(), sess.getAttribute("domainId").toString());
  }
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "";
    String moduleName = "";
    String oprType = "";
    String oprContent = "";
    WFProcessActionForm wFProcessActionForm = (WFProcessActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    if (action == null)
      action = "view"; 
    String tag = "";
    int moduleId = 0;
    if (httpServletRequest.getParameter("moduleId") != null)
      moduleId = Integer.parseInt(httpServletRequest.getParameter("moduleId")); 
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    switch (moduleId) {
      case 2:
        moduleCode = "oa_gw_fw";
        moduleName = "发文管理";
        break;
      case 3:
        moduleCode = "oa_gw_fw";
        moduleName = "收文管理";
        break;
      default:
        moduleCode = "oa_workflow_set";
        moduleName = "流程设置";
        break;
    } 
    httpServletRequest.setAttribute("moduleId", (new StringBuilder(String.valueOf(moduleId))).toString());
    if (action.equals("exportFlow")) {
      tag = "exportFlow";
      ExportFlow export = new ExportFlow();
      String fileInfo = export.exportFlowInfo(httpServletRequest);
      httpServletRequest.setAttribute("fileName", fileInfo);
    } else if (action.equals("importFlow")) {
      tag = "importFlow";
      ImportFlow importFlow = new ImportFlow();
      boolean flag = importFlow.importInfo(httpServletRequest);
      httpServletRequest.setAttribute("close", "1");
      if (flag) {
        httpServletRequest.setAttribute("flag", "1");
      } else {
        httpServletRequest.setAttribute("flag", "0");
      } 
    } else if (action.equals("view") || action.equals("search") || action.equals("singleDelete") || action.equals("batchDelete") || action.equals("changeStatus") || action.equals("copy")) {
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(moduleId);
      if (action.equals("singleDelete") || action.equals("batchDelete")) {
        String ids = "0";
        if (action.equals("singleDelete")) {
          ids = httpServletRequest.getParameter("id");
        } else {
          String[] batchId = httpServletRequest.getParameterValues("batchId");
          if (batchId != null) {
            for (int i = 0; i < batchId.length; i++)
              ids = String.valueOf(ids) + batchId[i] + ","; 
            ids = ids.substring(0, ids.length() - 1);
          } 
        } 
        ProcessBD processBD = new ProcessBD();
        oprContent = processBD.removeProcess(ids);
      } else if (action.equals("copy")) {
        String ids = "";
        String[] batchId = httpServletRequest.getParameterValues(
            "batchId");
        ProcessBD processBD = new ProcessBD();
        if (batchId != null)
          for (int i = 0; i < batchId.length; i++)
            processBD.copyProcess(batchId[i]);  
      } else if (action.equals("changeStatus")) {
        String flag = httpServletRequest.getParameter("flag");
        String processId = httpServletRequest.getParameter("id");
        ProcessBD processBD = new ProcessBD();
        oprContent = processBD.changeProcessStatus(processId, flag);
        if (oprContent == null)
          oprContent = processId; 
      } 
      HttpSession httpSession = httpServletRequest.getSession(true);
      tag = "view";
      String viewSql = " aaa.wfWorkFlowProcessId, bbb.packageName, aaa.workFlowProcessName, aaa.userScope,  aaa.processType, aaa.createUserName, aaa.accessDatabaseId,aaa.processStatus,aaa.ownerName ";
      String fromSql = " com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb ";
      ManagerService managerBD = new ManagerService();
      if (moduleVO.isProcRight()) {
        String tmpRightCode = (moduleVO.getProcRightType().length() == 8) ? moduleVO.getProcRightType() : (String.valueOf(moduleVO.getProcRightType()) + "*02");
        if (managerBD.hasRight(httpSession.getAttribute("userId").toString(), tmpRightCode)) {
          httpServletRequest.setAttribute("canAdd", "1");
        } else {
          httpServletRequest.setAttribute("canAdd", "0");
        } 
      } else {
        httpServletRequest.setAttribute("canAdd", "1");
      } 
      String whereSql = "";
      if (moduleVO.isProcRight()) {
        String tmpRightCode = (moduleVO.getProcRightType().length() == 8) ? moduleVO.getProcRightType() : (String.valueOf(moduleVO.getProcRightType()) + "*02");
        if (moduleId == 1 || moduleId == 2 || moduleId == 3) {
          boolean hasRightWeihu = managerBD.hasRight(userId, tmpRightCode);
          boolean hasRightLiulan = false;
          if (!hasRightWeihu) {
            hasRightLiulan = managerBD.hasRight(userId, "02*02*04");
            if (hasRightLiulan) {
              tmpRightCode = "02*02*04";
              httpServletRequest.setAttribute("hasRightWeihu", "0");
            } 
          } else {
            httpServletRequest.setAttribute("hasRightWeihu", "1");
          } 
          if (managerBD.hasRight(userId, "02*02*05"))
            httpServletRequest.setAttribute("hasRightShenhe", "1"); 
        } 
        String where = managerBD.getRightFinalWhere(httpSession.getAttribute("userId").toString(), 
            httpSession.getAttribute("orgId").toString(), 
            tmpRightCode, 
            "aaa.createdOrg", "aaa.createdEmp");
        whereSql = " where ((" + where + ") or (aaa.createdEmp = " + userId + ")) ";
      } else {
        whereSql = " where 1=1 ";
      } 
      String packagewhere = String.valueOf(whereSql) + " and a.wf_module_id=" + moduleVO.getId();
      whereSql = String.valueOf(whereSql) + " and bbb.moduleId = " + moduleVO.getId();
      String workFlowProcessName = httpServletRequest.getParameter("workFlowProcessName");
      if (workFlowProcessName == null || "null".equals(workFlowProcessName))
        workFlowProcessName = ""; 
      wFProcessActionForm.setWorkFlowProcessName(workFlowProcessName);
      if (workFlowProcessName != null && !workFlowProcessName.equals(""))
        whereSql = String.valueOf(whereSql) + " and aaa.workFlowProcessName like '%" + workFlowProcessName + "%'"; 
      String processCreateName = httpServletRequest.getParameter("processCreateName");
      if (processCreateName == null || "null".equals(processCreateName))
        processCreateName = ""; 
      wFProcessActionForm.setProcessCreateName(processCreateName);
      if (processCreateName != null && !processCreateName.equals(""))
        whereSql = String.valueOf(whereSql) + " and aaa.ownerName like '%" + processCreateName + "%'"; 
      String fileoprName = httpServletRequest.getParameter("fileoprName");
      wFProcessActionForm.setFileoprName(fileoprName);
      if (fileoprName != null && !fileoprName.equals(""))
        whereSql = String.valueOf(whereSql) + " and aaa.createUserName like '%" + fileoprName + "%'"; 
      String processPackage = httpServletRequest.getParameter("processPackage");
      if (processPackage == null || "null".equals(processPackage))
        processPackage = ""; 
      wFProcessActionForm.setProcessPackage(processPackage);
      if (processPackage != null && !processPackage.equals(""))
        whereSql = String.valueOf(whereSql) + " and aaa.wfPackage=" + processPackage; 
      String mark = httpServletRequest.getParameter("mark");
      String desc = httpServletRequest.getParameter("desc");
      httpServletRequest.setAttribute("desc", desc);
      String sqlOrderDisc = "";
      if ("2".equals(desc)) {
        sqlOrderDisc = " desc";
      } else {
        sqlOrderDisc = " asc";
      } 
      if (mark != null && "1".equals(mark)) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSql = String.valueOf(whereSql) + " order by bbb.orderCode asc,convert(aaa.workFlowProcessName using gbk )" + sqlOrderDisc + ", aaa.wfWorkFlowProcessId desc";
        } else {
          whereSql = String.valueOf(whereSql) + " ORDER BY bbb.orderCode asc,NLSSORT(aaa.workFlowProcessName, 'NLS_SORT=SCHINESE_PINYIN_M')" + sqlOrderDisc + " ,  aaa.wfWorkFlowProcessId desc";
        } 
      } else {
        whereSql = String.valueOf(whereSql) + " and aaa.domainId=" + session.getAttribute("domainId") + " order by bbb.orderCode asc, aaa.wfWorkFlowProcessId desc";
      } 
      list(httpServletRequest, viewSql, fromSql, whereSql);
      List wfPackageList = (new WorkFlowBD()).getWfPackageList(session.getAttribute("domainId").toString(), packagewhere);
      httpServletRequest.setAttribute("wfPackageList", wfPackageList);
    } else if (action.equals("add") || action.equals("modify")) {
      String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      List infoList = (new InfoArchives()).getCanIssueChannel(userId, orgId, orgIdString, domainId);
      httpServletRequest.setAttribute("infoList", infoList);
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(moduleId);
      PackageBD packageBD = new PackageBD();
      httpServletRequest.setAttribute("packageList", packageBD.getModulePackage((new StringBuilder(String.valueOf(moduleId))).toString(), session.getAttribute("domainId").toString()));
      httpServletRequest.setAttribute("isChangeProcType", new Boolean(moduleVO.isChangeProcType()));
      httpServletRequest.setAttribute("procType", (new StringBuilder(String.valueOf(moduleVO.getProcType()))).toString());
      httpServletRequest.setAttribute("chanNoWrite", new Boolean(moduleVO.isChanNoWrite()));
      httpServletRequest.setAttribute("noWrite", moduleVO.getNoWrite());
      httpServletRequest.setAttribute("chanRemind", new Boolean(moduleVO.isChanRemind()));
      httpServletRequest.setAttribute("remind", moduleVO.getRemind());
      moduleVO.setDomainId(domainId);
      WorkFlowBD workFlowBD = new WorkFlowBD();
      if (moduleId == 1) {
        FormBD formBD = new FormBD();
        if (SystemCommon.getMultiDepart() == 1) {
          httpServletRequest.setAttribute("tableList", formBD.getFormBaseInfoByRange(domainId, userId, orgId));
        } else {
          httpServletRequest.setAttribute("tableList", formBD.getFormBaseInfo(domainId));
        } 
      } else {
        httpServletRequest.setAttribute("tableList", workFlowBD.getAccessTable(moduleVO));
      } 
      if (action.equals("add")) {
        tag = "add";
        if (moduleVO.getFormClassName() != null && 
          !moduleVO.getFormClassName().equals("") && 
          !moduleVO.getFormClassName().toUpperCase().equals("NULL")) {
          httpServletRequest.setAttribute("formClassName", moduleVO.getFormClassName());
        } else {
          httpServletRequest.setAttribute("formClassName", "");
        } 
        if (moduleVO.getNewFormMethod() != null && 
          !moduleVO.getNewFormMethod().equals("") && 
          !moduleVO.getNewFormMethod().toUpperCase().equals("NULL")) {
          httpServletRequest.setAttribute("newFormMethod", moduleVO.getNewFormMethod());
        } else {
          httpServletRequest.setAttribute("newFormMethod", "");
        } 
        if (moduleVO.getCompleteMethod() != null && 
          !moduleVO.getCompleteMethod().equals("") && 
          !moduleVO.getCompleteMethod().toUpperCase().equals("NULL")) {
          httpServletRequest.setAttribute("completeMethod", moduleVO.getCompleteMethod());
        } else {
          httpServletRequest.setAttribute("completeMethod", "");
        } 
      } else {
        tag = "modify";
        String id = httpServletRequest.getParameter("id");
        String tableId = httpServletRequest.getParameter("tableId");
        String workFlowProcessName = httpServletRequest.getParameter("workFlowProcessName");
        String processCreateName = httpServletRequest.getParameter("processCreateName");
        String processPackage = httpServletRequest.getParameter("processPackage");
        httpServletRequest.setAttribute("id", id);
        httpServletRequest.setAttribute("tableId", tableId);
        httpServletRequest.setAttribute("workFlowProcessName", workFlowProcessName);
        httpServletRequest.setAttribute("processCreateName", processCreateName);
        httpServletRequest.setAttribute("processPackage", processPackage);
        httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleFieldByOrder((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
        ProcessBD processBD = new ProcessBD();
        httpServletRequest.setAttribute("noWriteFieldList", processBD.getNoWriteField(id));
        httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
        wFProcessActionForm.setProcessId(id);
        List<Object[]> list = processBD.getProcInfo(id);
        Object[] obj = list.get(0);
        if (obj[37] != null && !"null".equals(obj[37].toString()))
          httpServletRequest.setAttribute("infoChannelId", obj[37]); 
        httpServletRequest.setAttribute("sendFileType", obj[38]);
        httpServletRequest.setAttribute("packageId", obj[0]);
        wFProcessActionForm.setWorkFlowProcessName(obj[1].toString());
        wFProcessActionForm.setUserScope((obj[2] == null) ? "" : obj[2].toString());
        httpServletRequest.setAttribute("processType", obj[3]);
        httpServletRequest.setAttribute("accessDatabaseId", obj[4]);
        if (obj[5] != null) {
          wFProcessActionForm.setProcessDescription(obj[5].toString());
        } else {
          wFProcessActionForm.setProcessDescription("");
        } 
        if (obj[6] != null) {
          wFProcessActionForm.setUseOrg(obj[6].toString());
        } else {
          wFProcessActionForm.setUseOrg("");
        } 
        if (obj[7] != null) {
          wFProcessActionForm.setUseGroup(obj[7].toString());
        } else {
          wFProcessActionForm.setUseGroup("");
        } 
        if (obj[8] != null) {
          wFProcessActionForm.setUsePerson(obj[8].toString());
        } else {
          wFProcessActionForm.setUsePerson("");
        } 
        if (obj[9] != null) {
          wFProcessActionForm.setDossierFileSeeScope(obj[9].toString());
        } else {
          wFProcessActionForm.setDossierFileSeeScope("");
        } 
        String dossierFileSeeScopeId = "";
        if (obj[10] != null) {
          wFProcessActionForm.setDossierFileSeeOrg(obj[10].toString());
          dossierFileSeeScopeId = String.valueOf(dossierFileSeeScopeId) + obj[10].toString();
        } else {
          wFProcessActionForm.setDossierFileSeeOrg("");
        } 
        if (obj[11] != null) {
          wFProcessActionForm.setDossierFileSeeGroup(obj[11].toString());
          dossierFileSeeScopeId = String.valueOf(dossierFileSeeScopeId) + obj[11].toString();
        } else {
          wFProcessActionForm.setDossierFileSeeGroup("");
        } 
        if (obj[12] != null) {
          wFProcessActionForm.setDossierFileSeePerson(obj[12].toString());
          dossierFileSeeScopeId = String.valueOf(dossierFileSeeScopeId) + obj[12].toString();
        } else {
          wFProcessActionForm.setDossierFileSeePerson("");
        } 
        if (obj[13] != null) {
          httpServletRequest.setAttribute("remindField", obj[13]);
        } else {
          httpServletRequest.setAttribute("remindField", "");
        } 
        if (obj.length >= 15 && obj[14] != null) {
          httpServletRequest.setAttribute("isDossier", obj[14]);
        } else {
          httpServletRequest.setAttribute("isDossier", "0");
        } 
        if (obj.length >= 16 && obj[15] != null) {
          httpServletRequest.setAttribute("canCancel", obj[15]);
        } else {
          httpServletRequest.setAttribute("canCancel", "0");
        } 
        Map tmp = (new WorkFlowCommonBD()).getProcessClassMethod(id);
        if (obj[16] != null) {
          httpServletRequest.setAttribute("formClassName", obj[16]);
        } else if (tmp != null) {
          if (tmp.get("formClassName") != null && !tmp.get("formClassName").toString().equals("") && !tmp.get("formClassName").toString().toUpperCase().equals("NULL"))
            httpServletRequest.setAttribute("formClassName", tmp.get("formClassName")); 
        } else {
          httpServletRequest.setAttribute("formClassName", "");
        } 
        if (obj[17] != null) {
          httpServletRequest.setAttribute("formClassMethod", obj[17]);
        } else if (tmp != null) {
          if (tmp.get("formClassMethod") != null && !tmp.get("formClassMethod").toString().equals("") && !tmp.get("formClassMethod").toString().toUpperCase().equals("NULL"))
            httpServletRequest.setAttribute("formClassMethod", tmp.get("formClassMethod")); 
        } else {
          httpServletRequest.setAttribute("formClassMethod", "");
        } 
        if (obj[18] != null && !obj[18].toString().toUpperCase().equals("NULL")) {
          httpServletRequest.setAttribute("formClassCompMethod", obj[18]);
        } else {
          httpServletRequest.setAttribute("formClassCompMethod", "");
        } 
        httpServletRequest.setAttribute("dossierFileSeeScopeId", dossierFileSeeScopeId);
        if (obj[19] != null) {
          wFProcessActionForm.setPrintFileSeeScope(obj[19].toString());
        } else {
          wFProcessActionForm.setPrintFileSeeScope("");
        } 
        String printFileSeeScopeId = "";
        if (obj[20] != null) {
          wFProcessActionForm.setPrintFileSeeOrg(obj[20].toString());
          dossierFileSeeScopeId = String.valueOf(printFileSeeScopeId) + obj[20].toString();
        } else {
          wFProcessActionForm.setPrintFileSeeOrg("");
        } 
        if (obj[21] != null) {
          wFProcessActionForm.setPrintFileSeeGroup(obj[21].toString());
          dossierFileSeeScopeId = String.valueOf(printFileSeeScopeId) + obj[21].toString();
        } else {
          wFProcessActionForm.setPrintFileSeeGroup("");
        } 
        if (obj[22] != null) {
          wFProcessActionForm.setPrintFileSeePerson(obj[22].toString());
          dossierFileSeeScopeId = String.valueOf(printFileSeeScopeId) + obj[22].toString();
        } else {
          wFProcessActionForm.setPrintFileSeePerson("");
        } 
        httpServletRequest.setAttribute("printFileSeeScopeId", printFileSeeScopeId);
        httpServletRequest.setAttribute("formType", obj[23]);
        httpServletRequest.setAttribute("startJSP", obj[24]);
        httpServletRequest.setAttribute("optJSP", obj[25]);
        if (obj[26] != null) {
          wFProcessActionForm.setDossierFileOperScope(obj[26].toString());
        } else {
          wFProcessActionForm.setDossierFileOperScope("");
        } 
        String dossierFileOperScopeId = "";
        if (obj[27] != null) {
          wFProcessActionForm.setDossierFileOperOrg(obj[27].toString());
          dossierFileOperScopeId = String.valueOf(dossierFileOperScopeId) + obj[27].toString();
        } else {
          wFProcessActionForm.setDossierFileOperOrg("");
        } 
        if (obj[28] != null) {
          wFProcessActionForm.setDossierFileOperGroup(obj[28].toString());
          dossierFileOperScopeId = String.valueOf(dossierFileOperScopeId) + obj[28].toString();
        } else {
          wFProcessActionForm.setDossierFileOperGroup("");
        } 
        if (obj[29] != null) {
          wFProcessActionForm.setDossierFileOperPerson(obj[29].toString());
          dossierFileOperScopeId = String.valueOf(dossierFileOperScopeId) + obj[29].toString();
        } else {
          wFProcessActionForm.setDossierFileOperPerson("");
        } 
        httpServletRequest.setAttribute("dossierFileOperScopeId", dossierFileOperScopeId);
        if (obj[50] != null) {
          wFProcessActionForm.setDossierFileExportScope(obj[50].toString());
        } else {
          wFProcessActionForm.setDossierFileExportScope("");
        } 
        String dossierFileExportScopeId = "";
        if (obj[51] != null) {
          wFProcessActionForm.setDossierFileExportOrg(obj[51].toString());
          dossierFileExportScopeId = String.valueOf(dossierFileExportScopeId) + obj[51].toString();
        } else {
          wFProcessActionForm.setDossierFileExportOrg("");
        } 
        if (obj[52] != null) {
          wFProcessActionForm.setDossierFileExportGroup(obj[52].toString());
          dossierFileExportScopeId = String.valueOf(dossierFileExportScopeId) + obj[52].toString();
        } else {
          wFProcessActionForm.setDossierFileExportGroup("");
        } 
        if (obj[53] != null) {
          wFProcessActionForm.setDossierFileExportPerson(obj[53].toString());
          dossierFileExportScopeId = String.valueOf(dossierFileExportScopeId) + obj[53].toString();
        } else {
          wFProcessActionForm.setDossierFileExportPerson("");
        } 
        httpServletRequest.setAttribute("dossierFileExportScopeId", dossierFileExportScopeId);
        if (obj[30] != null) {
          wFProcessActionForm.setProcessAdminScope(obj[30].toString());
        } else {
          wFProcessActionForm.setProcessAdminScope("");
        } 
        String processAdminScopeId = "";
        if (obj[31] != null) {
          wFProcessActionForm.setProcessAdminOrg(obj[31].toString());
          processAdminScopeId = String.valueOf(processAdminScopeId) + obj[31].toString();
        } else {
          wFProcessActionForm.setProcessAdminOrg("");
        } 
        if (obj[32] != null) {
          wFProcessActionForm.setProcessAdminGroup(obj[32].toString());
          processAdminScopeId = String.valueOf(processAdminScopeId) + obj[32].toString();
        } else {
          wFProcessActionForm.setProcessAdminGroup("");
        } 
        if (obj[33] != null) {
          wFProcessActionForm.setProcessAdminPerson(obj[33].toString());
          processAdminScopeId = String.valueOf(processAdminScopeId) + obj[33].toString();
        } else {
          wFProcessActionForm.setProcessAdminPerson("");
        } 
        httpServletRequest.setAttribute("processAdminScopeId", processAdminScopeId);
        if (obj[35] != null && !"null".equals(obj[35].toString().toLowerCase()) && !"".equals(obj[35].toString())) {
          wFProcessActionForm.setCreateUserName(obj[34].toString());
          wFProcessActionForm.setCreatedEmp(Long.valueOf(obj[35].toString()));
        } else {
          wFProcessActionForm.setCreateUserName("系统管理员");
          wFProcessActionForm.setCreatedEmp(Long.valueOf("0"));
        } 
        httpServletRequest.setAttribute("mainFormId", obj[36]);
        WorkFlowBD bd = new WorkFlowBD();
        httpServletRequest.setAttribute("relationFormInfo", bd.getAllFormInfo(obj[4].toString()));
        httpServletRequest.setAttribute("startUrl", (obj[39] == null) ? "" : obj[39]);
        httpServletRequest.setAttribute("startMethod", (obj[40] == null) ? "" : obj[40]);
        httpServletRequest.setAttribute("startPara", (obj[41] == null) ? "" : obj[41]);
        httpServletRequest.setAttribute("startNameSpace", (obj[42] == null) ? "" : obj[42]);
        httpServletRequest.setAttribute("completeUrl", (obj[43] == null) ? "" : obj[43]);
        httpServletRequest.setAttribute("completeMethod", (obj[44] == null) ? "" : obj[44]);
        httpServletRequest.setAttribute("completePara", (obj[45] == null) ? "" : obj[45]);
        httpServletRequest.setAttribute("completeNameSpace", (obj[46] == null) ? "" : obj[46]);
        httpServletRequest.setAttribute("processDeadline", obj[47]);
        httpServletRequest.setAttribute("processDeadlineType", obj[48]);
        httpServletRequest.setAttribute("isJx", obj[49]);
      } 
    } else if (action.equals("continue") || action.equals("close")) {
      ProcessBD processBD = new ProcessBD();
      String[] noWriteField = httpServletRequest.getParameterValues("noWriteField");
      String mainFormId = httpServletRequest.getParameter("accessDatabaseId");
      boolean success = false;
      success = processBD.addProcess(setWFWorkFlowProcessPO(httpServletRequest, wFProcessActionForm), wFProcessActionForm.getPackageId(), noWriteField, mainFormId);
      if (success) {
        if (action.equals("continue")) {
          tag = "addcontinue";
          ModuleBD moduleBD = new ModuleBD();
          ModuleVO moduleVO = moduleBD.getModule(moduleId);
          PackageBD packageBD = new PackageBD();
          httpServletRequest.setAttribute("packageList", packageBD.getModulePackage((new StringBuilder(String.valueOf(moduleId))).toString(), session.getAttribute("domainId").toString()));
          WorkFlowBD workFlowBD = new WorkFlowBD();
          if (moduleId == 1) {
            FormBD formBD = new FormBD();
            httpServletRequest.setAttribute("tableList", formBD.getFormBaseInfo(session.getAttribute("domainId").toString()));
          } else {
            httpServletRequest.setAttribute("tableList", workFlowBD.getAccessTable(moduleVO));
          } 
          httpServletRequest.setAttribute("isChangeProcType", new Boolean(moduleVO.isChangeProcType()));
          httpServletRequest.setAttribute("procType", (new StringBuilder(String.valueOf(moduleVO.getProcType()))).toString());
          httpServletRequest.setAttribute("chanNoWrite", new Boolean(moduleVO.isChanNoWrite()));
          httpServletRequest.setAttribute("noWrite", moduleVO.getNoWrite());
          httpServletRequest.setAttribute("chanRemind", new Boolean(moduleVO.isChanRemind()));
          httpServletRequest.setAttribute("remind", moduleVO.getRemind());
          wFProcessActionForm.setWorkFlowProcessName("");
          wFProcessActionForm.setUserScope("");
          wFProcessActionForm.setDossierFileSeeScope("");
          wFProcessActionForm.setDossierFileOperScope("");
          if (moduleVO.getFormClassName() != null && 
            !moduleVO.getFormClassName().equals("") && 
            !moduleVO.getFormClassName().toUpperCase().equals("NULL")) {
            httpServletRequest.setAttribute("formClassName", moduleVO.getFormClassName());
          } else {
            httpServletRequest.setAttribute("formClassName", "");
          } 
          if (moduleVO.getNewFormMethod() != null && 
            !moduleVO.getNewFormMethod().equals("") && 
            !moduleVO.getNewFormMethod().toUpperCase().equals("NULL")) {
            httpServletRequest.setAttribute("newFormMethod", moduleVO.getNewFormMethod());
          } else {
            httpServletRequest.setAttribute("newFormMethod", "");
          } 
          if (moduleVO.getCompleteMethod() != null && 
            !moduleVO.getCompleteMethod().equals("") && 
            !moduleVO.getCompleteMethod().toUpperCase().equals("NULL")) {
            httpServletRequest.setAttribute("completeMethod", moduleVO.getCompleteMethod());
          } else {
            httpServletRequest.setAttribute("completeMethod", "");
          } 
        } else {
          tag = "close";
        } 
      } else {
        tag = "errorAdd";
        ModuleBD moduleBD = new ModuleBD();
        ModuleVO moduleVO = moduleBD.getModule(moduleId);
        PackageBD packageBD = new PackageBD();
        httpServletRequest.setAttribute("packageList", packageBD.getModulePackage((new StringBuilder(String.valueOf(moduleId))).toString(), session.getAttribute("domainId").toString()));
        WorkFlowBD workFlowBD = new WorkFlowBD();
        if (moduleId == 1) {
          FormBD formBD = new FormBD();
          httpServletRequest.setAttribute("tableList", formBD.getFormBaseInfo(session.getAttribute("domainId").toString()));
        } else {
          httpServletRequest.setAttribute("tableList", workFlowBD.getAccessTable(moduleVO));
        } 
        httpServletRequest.setAttribute("isChangeProcType", new Boolean(moduleVO.isChangeProcType()));
        httpServletRequest.setAttribute("procType", (new StringBuilder(String.valueOf(moduleVO.getProcType()))).toString());
        httpServletRequest.setAttribute("chanNoWrite", new Boolean(moduleVO.isChanNoWrite()));
        httpServletRequest.setAttribute("noWrite", moduleVO.getNoWrite());
        httpServletRequest.setAttribute("chanRemind", new Boolean(moduleVO.isChanRemind()));
        httpServletRequest.setAttribute("remind", moduleVO.getRemind());
      } 
    } else {
      if (action.equals("nameDuplicate")) {
        String processId = httpServletRequest.getParameter("processId");
        String processName = httpServletRequest.getParameter("processName");
        String processClass = httpServletRequest.getParameter("processClass");
        ProcessBD processBD = new ProcessBD();
        try {
          PrintWriter out = httpServletResponse.getWriter();
          if (processBD.nameIsDuplicate(processId, processName, processClass)) {
            out.print("YES");
          } else {
            out.print("NO");
          } 
          out.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
        return null;
      } 
      if (action.equals("trueModify")) {
        String[] parameter = new String[54];
        parameter[0] = wFProcessActionForm.getPackageId();
        parameter[1] = wFProcessActionForm.getWorkFlowProcessName();
        parameter[2] = wFProcessActionForm.getUserScope();
        parameter[3] = wFProcessActionForm.getUseOrg();
        parameter[4] = wFProcessActionForm.getUseGroup();
        parameter[5] = wFProcessActionForm.getUsePerson();
        parameter[6] = wFProcessActionForm.getProcessType();
        parameter[7] = wFProcessActionForm.getAccessDatabaseId();
        parameter[8] = wFProcessActionForm.getProcessDescription();
        parameter[9] = wFProcessActionForm.getDossierFileSeeScope();
        parameter[10] = wFProcessActionForm.getDossierFileSeeOrg();
        parameter[11] = wFProcessActionForm.getDossierFileSeeGroup();
        parameter[12] = wFProcessActionForm.getDossierFileSeePerson();
        parameter[50] = wFProcessActionForm.getDossierFileExportScope();
        parameter[51] = wFProcessActionForm.getDossierFileExportOrg();
        parameter[52] = wFProcessActionForm.getDossierFileExportGroup();
        parameter[53] = wFProcessActionForm.getDossierFileExportPerson();
        String[] remindField = httpServletRequest.getParameterValues("remindField");
        StringBuffer sb = new StringBuffer();
        if (remindField != null)
          for (int j = 0; j < remindField.length; j++)
            sb.append("S" + remindField[j] + "S");  
        parameter[13] = sb.toString();
        parameter[14] = (httpServletRequest.getParameter("isDossier") == null) ? "0" : "1";
        parameter[15] = (httpServletRequest.getParameter("canCancel") == null) ? "0" : "1";
        String formClassName = httpServletRequest.getParameter("formClassName");
        parameter[16] = formClassName.substring(0, formClassName.lastIndexOf("."));
        parameter[17] = httpServletRequest.getParameter("formClassMethod");
        parameter[18] = httpServletRequest.getParameter("formClassCompMethod");
        parameter[19] = wFProcessActionForm.getPrintFileSeeScope();
        parameter[20] = wFProcessActionForm.getPrintFileSeeOrg();
        parameter[21] = wFProcessActionForm.getPrintFileSeeGroup();
        parameter[22] = wFProcessActionForm.getPrintFileSeePerson();
        parameter[23] = httpServletRequest.getParameter("formType");
        parameter[24] = httpServletRequest.getParameter("startJSPName");
        parameter[25] = httpServletRequest.getParameter("optJSPName");
        parameter[26] = httpServletRequest.getParameter("moduleId");
        parameter[27] = wFProcessActionForm.getDossierFileOperScope();
        parameter[28] = wFProcessActionForm.getDossierFileOperOrg();
        parameter[29] = wFProcessActionForm.getDossierFileOperGroup();
        parameter[30] = wFProcessActionForm.getDossierFileOperPerson();
        parameter[31] = wFProcessActionForm.getProcessAdminScope();
        parameter[32] = wFProcessActionForm.getProcessAdminOrg();
        parameter[33] = wFProcessActionForm.getProcessAdminGroup();
        parameter[34] = wFProcessActionForm.getProcessAdminPerson();
        parameter[35] = wFProcessActionForm.getCreateUserName();
        parameter[36] = wFProcessActionForm.getCreatedEmp().toString();
        parameter[37] = wFProcessActionForm.getInfoChannelId();
        parameter[38] = httpServletRequest.getParameter("sendFileType");
        if (httpServletRequest.getParameter("startWebService") != null) {
          parameter[39] = httpServletRequest.getParameter("startUrl");
          parameter[40] = httpServletRequest.getParameter("startMethod");
          parameter[41] = httpServletRequest.getParameter("startPara");
          parameter[42] = httpServletRequest.getParameter("startNameSpace");
        } else {
          parameter[39] = "";
          parameter[40] = "";
          parameter[41] = "";
          parameter[42] = "";
        } 
        if (httpServletRequest.getParameter("completeWebService") != null) {
          parameter[43] = httpServletRequest.getParameter("completeUrl");
          parameter[44] = httpServletRequest.getParameter("completeMethod");
          parameter[45] = httpServletRequest.getParameter("completePara");
          parameter[46] = httpServletRequest.getParameter("completeNameSpace");
        } else {
          parameter[43] = "";
          parameter[44] = "";
          parameter[45] = "";
          parameter[46] = "";
        } 
        parameter[47] = (httpServletRequest.getParameter("processDeadline") == null || "".equals(httpServletRequest.getParameter("processDeadline"))) ? 
          "0" : httpServletRequest.getParameter("processDeadline");
        parameter[48] = (httpServletRequest.getParameter("processDeadlineType") == null) ? 
          "2" : httpServletRequest.getParameter("processDeadlineType");
        parameter[49] = (httpServletRequest.getParameter("isJx") == null) ? "0" : httpServletRequest.getParameter("isJx");
        String[] noWriteField = httpServletRequest.getParameterValues("noWriteField");
        String mainFormId = httpServletRequest.getParameter("mainFormId");
        if (mainFormId == null || "null".equals(mainFormId))
          mainFormId = wFProcessActionForm.getAccessDatabaseId(); 
        ProcessBD processBD = new ProcessBD();
        if (processBD.updateProcess(wFProcessActionForm.getProcessId(), parameter, noWriteField, mainFormId)) {
          if (SystemCommon.getFormProgram() == 1 && "1".equals(httpServletRequest.getParameter("moduleId"))) {
            String duiying = httpServletRequest.getParameter("duiying");
            String caozuo = httpServletRequest.getParameter("toAddOrUpdate");
            DataSynchro ds = new DataSynchro();
            if ("1".equals(duiying)) {
              if ("add".equals(caozuo)) {
                ds.save(httpServletRequest);
              } else {
                ds.update(httpServletRequest);
              } 
            } else {
              String processId = httpServletRequest.getParameter("processId");
              ds.deleteSynchro(processId);
            } 
          } 
          httpServletRequest.setAttribute("pager.offset", "0");
          tag = "close";
        } else {
          PackageBD packageBD = new PackageBD();
          httpServletRequest.setAttribute("packageList", packageBD.getModulePackage((new StringBuilder(String.valueOf(moduleId))).toString(), session.getAttribute("domainId").toString()));
          ModuleBD moduleBD = new ModuleBD();
          WorkFlowBD workFlowBD = new WorkFlowBD();
          httpServletRequest.setAttribute("tableList", workFlowBD.getAccessTable(moduleBD.getModule(moduleId)));
          tag = "errorModify";
          String id = httpServletRequest.getParameter("id");
          String tableId = httpServletRequest.getParameter("tableId");
          httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
          httpServletRequest.setAttribute("noWriteFieldList", processBD.getNoWriteField(id));
          httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
          wFProcessActionForm.setProcessId(id);
          List<Object[]> list = processBD.getProcInfo(id);
          Object[] obj = list.get(0);
          httpServletRequest.setAttribute("packageId", obj[0]);
          wFProcessActionForm.setWorkFlowProcessName(obj[1].toString());
          wFProcessActionForm.setUserScope(obj[2].toString());
          httpServletRequest.setAttribute("processType", obj[3]);
          httpServletRequest.setAttribute("accessDatabaseId", obj[4]);
          if (obj[5] != null) {
            wFProcessActionForm.setProcessDescription(obj[5].toString());
          } else {
            wFProcessActionForm.setProcessDescription("");
          } 
          if (obj[6] != null) {
            wFProcessActionForm.setUseOrg(obj[6].toString());
          } else {
            wFProcessActionForm.setUseOrg("");
          } 
          if (obj[7] != null) {
            wFProcessActionForm.setUseGroup(obj[7].toString());
          } else {
            wFProcessActionForm.setUseGroup("");
          } 
          if (obj[8] != null) {
            wFProcessActionForm.setUsePerson(obj[8].toString());
          } else {
            wFProcessActionForm.setUsePerson("");
          } 
          if (obj[9] != null) {
            wFProcessActionForm.setDossierFileSeeScope(obj[9].toString());
          } else {
            wFProcessActionForm.setDossierFileSeeScope("");
          } 
          String dossierFileSeeScopeId = "";
          if (obj[10] != null) {
            wFProcessActionForm.setDossierFileSeeOrg(obj[10].toString());
            dossierFileSeeScopeId = String.valueOf(dossierFileSeeScopeId) + obj[10].toString();
          } else {
            wFProcessActionForm.setDossierFileSeeOrg("");
          } 
          if (obj[11] != null) {
            wFProcessActionForm.setDossierFileSeeGroup(obj[11].toString());
            dossierFileSeeScopeId = String.valueOf(dossierFileSeeScopeId) + obj[11].toString();
          } else {
            wFProcessActionForm.setDossierFileSeeGroup("");
          } 
          if (obj[12] != null) {
            wFProcessActionForm.setDossierFileSeePerson(obj[12].toString());
            dossierFileSeeScopeId = String.valueOf(dossierFileSeeScopeId) + obj[12].toString();
          } else {
            wFProcessActionForm.setDossierFileSeePerson("");
          } 
          if (obj[19] != null) {
            wFProcessActionForm.setPrintFileSeeScope(obj[19].toString());
          } else {
            wFProcessActionForm.setPrintFileSeeScope("");
          } 
          String printFileSeeScopeId = "";
          if (obj[20] != null) {
            wFProcessActionForm.setPrintFileSeeOrg(obj[20].toString());
            dossierFileSeeScopeId = String.valueOf(printFileSeeScopeId) + obj[20].toString();
          } else {
            wFProcessActionForm.setPrintFileSeeOrg("");
          } 
          if (obj[21] != null) {
            wFProcessActionForm.setPrintFileSeeGroup(obj[21].toString());
            dossierFileSeeScopeId = String.valueOf(printFileSeeScopeId) + obj[21].toString();
          } else {
            wFProcessActionForm.setPrintFileSeeGroup("");
          } 
          if (obj[22] != null) {
            wFProcessActionForm.setPrintFileSeePerson(obj[22].toString());
            dossierFileSeeScopeId = String.valueOf(printFileSeeScopeId) + obj[22].toString();
          } else {
            wFProcessActionForm.setPrintFileSeePerson("");
          } 
          httpServletRequest.setAttribute("printFileSeeScopeId", printFileSeeScopeId);
          if (obj[13] != null) {
            httpServletRequest.setAttribute("remindField", obj[13]);
          } else {
            httpServletRequest.setAttribute("remindField", "");
          } 
          if (obj.length >= 15 && obj[14] != null) {
            httpServletRequest.setAttribute("isDossier", obj[14]);
          } else {
            httpServletRequest.setAttribute("isDossier", "0");
          } 
          if (obj.length >= 16 && obj[15] != null) {
            httpServletRequest.setAttribute("canCancel", obj[15]);
          } else {
            httpServletRequest.setAttribute("canCancel", "0");
          } 
          httpServletRequest.setAttribute("dossierFileSeeScopeId", dossierFileSeeScopeId);
        } 
      } else if (action.equals("iframe")) {
        tag = "iframe";
        String tableId = httpServletRequest.getParameter("tableId");
        StringBuffer sb = new StringBuffer();
        StringBuffer remindField = new StringBuffer();
        WorkFlowBD workFlowBD = new WorkFlowBD();
        List<SimpleFieldVO> fieldList = workFlowBD.getSimpleFieldByOrder((new StringBuilder(String.valueOf(moduleId))).toString(), tableId);
        sb.append("<table width=\"80%\" border=\"0\">");
        sb.append("<tr><td width=\"180\">");
        sb.append("<select name = \"field\" multiple=\"multiple\" size=\"10\" style=\"width:150px;\">");
        SimpleFieldVO simpleFieldVO = new SimpleFieldVO();
        int j = 1;
        for (int i = 0; i < fieldList.size(); i++) {
          simpleFieldVO = fieldList.get(i);
          if (!"401".equals(simpleFieldVO.getPoField()))
            sb.append("<option value=\"" + simpleFieldVO.getId() + "\">" + simpleFieldVO.getDisplayName() + "</option>"); 
          if (simpleFieldVO.isRemind()) {
            remindField.append("<input type=\"checkbox\" name=\"remindField\" value=\"" + simpleFieldVO.getRealName() + "\">" + simpleFieldVO.getDisplayName() + "&nbsp;");
            if (j % 105 == 0)
              remindField.append("<br>"); 
            j++;
          } 
        } 
        sb.append("</select>");
        sb.append("</td><td width=\"15%\" height=\"100%\">");
        sb.append("<div style=\"height:100%;padding-top:20px;\" align=\"center\">");
        sb.append("<div style=\"float:center;height:25%;\">");
        sb.append("<img src=\"/jsoa/images/arrow_right1.gif\" onclick=transferOptions(\"field\",\"noWriteField\");>");
        sb.append("</div>");
        sb.append("<div style=\"float:top;height:25%;\">");
        sb.append("<img src=\"/jsoa/images/arrow_right2.gif\" onclick=transferOptionsAll(\"field\",\"noWriteField\");>");
        sb.append("</div>");
        sb.append("<div style=\"float:top;height:25%;\">");
        sb.append("<img src=\"/jsoa/images/arrow_left1.gif\" onclick=transferOptions(\"noWriteField\",\"field\");>");
        sb.append("</div>");
        sb.append("<div style=\"float:top;height:25%;\">");
        sb.append("<img src=\"/jsoa/images/arrow_left2.gif\" onclick=transferOptionsAll(\"noWriteField\",\"field\");>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</td><td style=\"padding-left:20px;\">");
        sb.append("<select name = \"noWriteField\" multiple=\"multiple\" size=\"10\" style=\"width:150px;\">");
        sb.append("</select>");
        sb.append("</td></tr></table>");
        fieldList = workFlowBD.getAllFormInfo(tableId);
        httpServletRequest.setAttribute("pageInfo", fieldList);
        httpServletRequest.setAttribute("fieldString", sb.toString());
        httpServletRequest.setAttribute("remindField", remindField.toString());
        httpServletRequest.setAttribute("chanNoWrite", httpServletRequest.getParameter("chanNoWrite"));
        httpServletRequest.setAttribute("chanRemind", httpServletRequest.getParameter("chanRemind"));
      } else if ("export".equals(action)) {
        ModuleBD moduleBD = new ModuleBD();
        ModuleVO moduleVO = moduleBD.getModule(moduleId);
        HttpSession httpSession = httpServletRequest.getSession(true);
        tag = "export";
        String viewSql = " aaa.workFlowProcessName, bbb.packageName, aaa.userScope, aaa.dossierFileSeeScope, aaa.dossierFileOperScope, aaa.processAdminScope, aaa.createUserName, aaa.ownerName,aaa.processCreatedDate, aaa.lastUpdateTime,ccc.page_name,aaa.process_status,aaa.processUseTime,aaa.createdorg,aaa.ownerorgid ";
        String fromSql = " jsdb.JSF_WORKFLOWPROCESS aaa, jsdb.JSF_PACKAGE bbb, jsdb.tpage ccc";
        ManagerService managerBD = new ManagerService();
        String whereSql = "";
        if (moduleVO.isProcRight()) {
          String tmpRightCode = (moduleVO.getProcRightType().length() == 8) ? moduleVO.getProcRightType() : (String.valueOf(moduleVO.getProcRightType()) + "*02");
          String where = managerBD.getRightFinalWhere(httpSession.getAttribute("userId").toString(), 
              httpSession.getAttribute("orgId").toString(), 
              tmpRightCode, 
              "aaa.createdOrg", "aaa.createdEmp");
          whereSql = " where (aaa.WF_PACKAGE_ID=bbb.WF_PACKAGE_ID and ccc.page_id=aaa.ACCESSDATABASEID) and (" + where + " or (aaa.createdEmp = " + userId + ")) ";
        } else {
          whereSql = " where 1=1 ";
        } 
        whereSql = String.valueOf(whereSql) + " and bbb.wf_module_Id = " + moduleVO.getId();
        String workFlowProcessName = httpServletRequest.getParameter("workFlowProcessName");
        if (workFlowProcessName != null && !workFlowProcessName.equals(""))
          try {
            whereSql = String.valueOf(whereSql) + " and aaa.workFlowProcessName like '%" + URLDecoder.decode(workFlowProcessName, "utf-8") + "%'";
          } catch (Exception e) {
            e.printStackTrace();
          }  
        String processCreateName = httpServletRequest.getParameter("processCreateName");
        if (processCreateName != null && !processCreateName.equals(""))
          try {
            whereSql = String.valueOf(whereSql) + " and aaa.ownerName like '%" + URLDecoder.decode(processCreateName, "utf-8") + "%'";
          } catch (Exception e) {
            e.printStackTrace();
          }  
        String fileoprName = httpServletRequest.getParameter("fileoprName");
        if (fileoprName != null && !fileoprName.equals(""))
          try {
            whereSql = String.valueOf(whereSql) + " and aaa.createUserName like '%" + URLDecoder.decode(fileoprName, "utf-8") + "%'";
          } catch (Exception e) {
            e.printStackTrace();
          }  
        String processPackage = httpServletRequest.getParameter("processPackage");
        if (processPackage != null && !processPackage.equals(""))
          whereSql = String.valueOf(whereSql) + " and aaa.wf_Package_id=" + processPackage; 
        whereSql = String.valueOf(whereSql) + " and aaa.domain_Id=" + session.getAttribute("domainId") + " order by bbb.wf_Package_Id desc, aaa.wf_WorkFlowProcess_Id desc";
        WorkFlowBD workFlowBD = new WorkFlowBD();
        ArrayList<String[]> list = (ArrayList<String[]>)workFlowBD.getExportList(viewSql, fromSql, whereSql);
        httpServletRequest.setAttribute("flowlist", list);
      } else if ("copyFlow".equals(action)) {
        String domainId = session.getAttribute("domainId").toString();
        String flows = httpServletRequest.getParameter("flows");
        ProcessBD processBD = new ProcessBD();
        processBD.copyFlow(flows, domainId, userId, orgId);
        if (httpServletRequest.getParameter("search") != null) {
          httpServletRequest.setAttribute("workFlowProcessName", httpServletRequest.getParameter("workFlowProcessName"));
          httpServletRequest.setAttribute("processCreateName", httpServletRequest.getParameter("processCreateName"));
        } 
        tag = "copyFlow";
      } 
    } 
    Date endDate = new Date();
    if (action.equals("continue") || action.equals("close")) {
      oprType = "1";
      oprContent = "新建流程：" + wFProcessActionForm.getWorkFlowProcessName();
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("trueModify")) {
      oprType = "2";
      oprContent = wFProcessActionForm.getWorkFlowProcessName();
      oprContent = "修改：" + oprContent;
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("singleDelete")) {
      String title = httpServletRequest.getParameter("title");
      if (oprContent != null && !"".equals(oprContent) && !"null".equals(oprContent) && 
        oprContent.endsWith(","))
        title = oprContent.substring(0, oprContent.lastIndexOf(",")); 
      oprType = "3";
      oprContent = "删除流程：" + title;
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("batchDelete")) {
      oprType = "3";
      if (oprContent != null && !"".equals(oprContent) && !"null".equals(oprContent) && 
        oprContent.endsWith(","))
        oprContent = oprContent.substring(0, oprContent.lastIndexOf(",")); 
      oprContent = "批量删除流程：" + oprContent;
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("changeStatus")) {
      String flag = httpServletRequest.getParameter("flag");
      if ("1".equals(flag)) {
        oprType = "6";
        oprContent = "启用流程：" + oprContent;
        logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
      } else {
        oprType = "5";
        oprContent = "禁用流程：" + oprContent;
        logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
      } 
    } 
    if ("addcontinue".equals(tag)) {
      ActionForward af = new ActionForward();
      af.setPath("WFProcessAction.do?action=add&moduleId=" + moduleId);
      af.setRedirect(true);
      return af;
    } 
    return actionMapping.findForward(tag);
  }
  
  public WFWorkFlowProcessPO setWFWorkFlowProcessPO(HttpServletRequest httpServletRequest, WFProcessActionForm wFProcessActionForm) {
    WFWorkFlowProcessPO wfWorkFlowProcessPO = new WFWorkFlowProcessPO();
    HttpSession httpSession = httpServletRequest.getSession(true);
    wfWorkFlowProcessPO.setAccessDatabaseId(new Long(wFProcessActionForm.getAccessDatabaseId()));
    wfWorkFlowProcessPO.setWorkFlowProcessName(wFProcessActionForm.getWorkFlowProcessName());
    wfWorkFlowProcessPO.setProcessCreatedDate(new Date());
    wfWorkFlowProcessPO.setProcessDescription(wFProcessActionForm.getProcessDescription());
    wfWorkFlowProcessPO.setOwnerName(httpSession.getAttribute("userName").toString());
    wfWorkFlowProcessPO.setOwnerOrgId(new Long(httpSession.getAttribute("orgId").toString()));
    wfWorkFlowProcessPO.setOwnerId(new Long(httpSession.getAttribute("userId").toString()));
    wfWorkFlowProcessPO.setCreateUserName(httpSession.getAttribute("userName").toString());
    wfWorkFlowProcessPO.setCreatedEmp(new Long(httpSession.getAttribute("userId").toString()));
    wfWorkFlowProcessPO.setCreatedOrg(new Long(httpSession.getAttribute("orgId").toString()));
    wfWorkFlowProcessPO.setUserScope(wFProcessActionForm.getUserScope());
    wfWorkFlowProcessPO.setUseOrg(wFProcessActionForm.getUseOrg());
    wfWorkFlowProcessPO.setUseGroup(wFProcessActionForm.getUseGroup());
    wfWorkFlowProcessPO.setUsePerson(wFProcessActionForm.getUsePerson());
    wfWorkFlowProcessPO.setProcessType(Integer.parseInt(wFProcessActionForm.getProcessType()));
    wfWorkFlowProcessPO.setDossierFileSeeScope(wFProcessActionForm.getDossierFileSeeScope());
    wfWorkFlowProcessPO.setDossierFileSeeOrg(wFProcessActionForm.getDossierFileSeeOrg());
    wfWorkFlowProcessPO.setDossierFileSeeGroup(wFProcessActionForm.getDossierFileSeeGroup());
    wfWorkFlowProcessPO.setDossierFileSeePerson(wFProcessActionForm.getDossierFileSeePerson());
    wfWorkFlowProcessPO.setDossierFileExportScope(wFProcessActionForm.getDossierFileExportScope());
    wfWorkFlowProcessPO.setDossierFileExportOrg(wFProcessActionForm.getDossierFileExportOrg());
    wfWorkFlowProcessPO.setDossierFileExportGroup(wFProcessActionForm.getDossierFileExportGroup());
    wfWorkFlowProcessPO.setDossierFileExportPerson(wFProcessActionForm.getDossierFileExportPerson());
    wfWorkFlowProcessPO.setDossierFileOperScope(wFProcessActionForm.getDossierFileOperScope());
    wfWorkFlowProcessPO.setDossierFileOperOrg(wFProcessActionForm.getDossierFileOperOrg());
    wfWorkFlowProcessPO.setDossierFileOperGroup(wFProcessActionForm.getDossierFileOperGroup());
    wfWorkFlowProcessPO.setDossierFileOperPerson(wFProcessActionForm.getDossierFileOperPerson());
    wfWorkFlowProcessPO.setProcessAdminScope(wFProcessActionForm.getProcessAdminScope());
    wfWorkFlowProcessPO.setProcessAdminOrg(wFProcessActionForm.getProcessAdminOrg());
    wfWorkFlowProcessPO.setProcessAdminGroup(wFProcessActionForm.getProcessAdminGroup());
    wfWorkFlowProcessPO.setProcessAdminPerson(wFProcessActionForm.getProcessAdminPerson());
    wfWorkFlowProcessPO.setPrintFileSeeScope(wFProcessActionForm.getPrintFileSeeScope());
    wfWorkFlowProcessPO.setPrintFileSeeOrg(wFProcessActionForm.getPrintFileSeeOrg());
    wfWorkFlowProcessPO.setPrintFileSeeGroup(wFProcessActionForm.getPrintFileSeeGroup());
    wfWorkFlowProcessPO.setPrintFileSeePerson(wFProcessActionForm.getPrintFileSeePerson());
    wfWorkFlowProcessPO.setFormType(Integer.parseInt(httpServletRequest.getParameter("formType")));
    wfWorkFlowProcessPO.setStartJSP(httpServletRequest.getParameter("startJSPName"));
    wfWorkFlowProcessPO.setOptJSP(httpServletRequest.getParameter("optJSPName"));
    wfWorkFlowProcessPO.setInfoChannelId(httpServletRequest.getParameter("infoChannelId"));
    wfWorkFlowProcessPO.setSendFileType((httpServletRequest.getParameter("sendFileType") == null) ? 0 : 
        Integer.valueOf(httpServletRequest.getParameter("sendFileType")).intValue());
    String[] remindField = httpServletRequest.getParameterValues("remindField");
    StringBuffer sb = new StringBuffer();
    if (remindField != null)
      for (int i = 0; i < remindField.length; i++)
        sb.append("S" + remindField[i] + "S");  
    wfWorkFlowProcessPO.setRemindField(sb.toString());
    wfWorkFlowProcessPO.setIsDossier((httpServletRequest.getParameter("isDossier") == null) ? Integer.valueOf("0") : Integer.valueOf("1"));
    wfWorkFlowProcessPO.setCanCancel((httpServletRequest.getParameter("canCancel") == null) ? Integer.valueOf("0") : Integer.valueOf("1"));
    wfWorkFlowProcessPO.setIsPublish(Integer.valueOf("1"));
    String formClassName = httpServletRequest.getParameter("formClassName");
    if (formClassName.indexOf(".") >= 0)
      formClassName = formClassName.substring(0, formClassName.indexOf(".")); 
    wfWorkFlowProcessPO.setFormClassName(formClassName);
    wfWorkFlowProcessPO.setFormClassMethod(httpServletRequest.getParameter("formClassMethod"));
    wfWorkFlowProcessPO.setFormClassCompMethod(httpServletRequest.getParameter("formClassCompMethod"));
    wfWorkFlowProcessPO.setDomainId(httpSession.getAttribute("domainId").toString());
    if (httpServletRequest.getParameter("startWebService") != null) {
      wfWorkFlowProcessPO.setStartUrl(httpServletRequest.getParameter("startUrl"));
      wfWorkFlowProcessPO.setStartMethod(httpServletRequest.getParameter("startMethod"));
      wfWorkFlowProcessPO.setStartPara(httpServletRequest.getParameter("startPara"));
      wfWorkFlowProcessPO.setStartNameSpace(httpServletRequest.getParameter("startNameSpace"));
    } else {
      wfWorkFlowProcessPO.setStartUrl("");
      wfWorkFlowProcessPO.setStartMethod("");
      wfWorkFlowProcessPO.setStartPara("");
      wfWorkFlowProcessPO.setStartNameSpace("");
    } 
    if (httpServletRequest.getParameter("completeWebService") != null) {
      wfWorkFlowProcessPO.setCompleteUrl(httpServletRequest.getParameter("completeUrl"));
      wfWorkFlowProcessPO.setCompleteMethod(httpServletRequest.getParameter("completeMethod"));
      wfWorkFlowProcessPO.setCompletePara(httpServletRequest.getParameter("completePara"));
      wfWorkFlowProcessPO.setCompleteNameSpace(httpServletRequest.getParameter("completeNameSpace"));
    } else {
      wfWorkFlowProcessPO.setCompleteUrl("");
      wfWorkFlowProcessPO.setCompleteMethod("");
      wfWorkFlowProcessPO.setCompletePara("");
      wfWorkFlowProcessPO.setCompleteNameSpace("");
    } 
    if (!"".equals(httpServletRequest.getParameter("processDeadline"))) {
      wfWorkFlowProcessPO.setProcessDeadline(httpServletRequest.getParameter("processDeadline"));
    } else {
      wfWorkFlowProcessPO.setProcessDeadline("0");
    } 
    wfWorkFlowProcessPO.setProcessDeadlineType(httpServletRequest.getParameter("processDeadlineType"));
    return wfWorkFlowProcessPO;
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !httpServletRequest.getParameter("pager.offset").equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list != null && 
      list.size() == 0 && offset >= 15) {
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
    String mark = httpServletRequest.getParameter("mark");
    String desc = httpServletRequest.getParameter("desc");
    httpServletRequest.setAttribute("processList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,workFlowProcessName,processCreateName,processPackage,fileoprName,moduleId,mark,desc");
  }
}
