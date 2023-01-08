package com.js.oa.archives.action;

import com.js.oa.archives.po.ArchivesBorrowPO;
import com.js.oa.archives.po.ArchivesDossierAccessoryPO;
import com.js.oa.archives.po.ArchivesDossierPO;
import com.js.oa.archives.po.ArchivesFileAccessoryPO;
import com.js.oa.archives.po.ArchivesFilePO;
import com.js.oa.archives.po.ArchivesPigeonholeSetPO;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.archives.vo.ArchivesDossierVO;
import com.js.oa.archives.vo.ArchivesFileVO;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.message.action.ModelSendMsg;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ConversionString;
import com.js.util.util.FillBean;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ArchivesAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
    ArchivesActionForm archivesActionForm = (ArchivesActionForm)actionForm;
    ArchivesBD archivesBD = new ArchivesBD();
    String action = httpServletRequest.getParameter("action");
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = session.getAttribute("domainId").toString();
    String classId = httpServletRequest.getParameter("classId");
    httpServletRequest.setAttribute("send", httpServletRequest.getParameter("send"));
    String selectArchivesClass = httpServletRequest.getParameter("selectArchivesClass");
    ManagerService managerBD = new ManagerService();
    String archivesClassWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "archivesClass.classReader", 
        "archivesClass.classReadOrg", 
        "archivesClass.classReadGroup");
    String archivesDossierWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "archivesDossier.classReader", 
        "archivesDossier.classReadOrg", 
        "archivesDossier.classReadGroup");
    String archivesDossierRightWhere = managerBD.getRightFinalWhere(userId, orgId, 
        "07*23*03", 
        "archivesDossier.createdOrg", 
        "archivesDossier.createdEmp");
    String archivesFileWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "archivesFile.classReader", 
        "archivesFile.classReadOrg", 
        "archivesFile.classReadGroup");
    String archivesFileRightWhere = managerBD.getRightFinalWhere(userId, orgId, 
        "07*23*03", "archivesFile.createdOrg", "archivesFile.createdEmp");
    String archivesClassWhere2 = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "archivesDossier.archivesClass.classReader", 
        "archivesDossier.archivesClass.classReadOrg", 
        "archivesDossier.archivesClass.classReadGroup");
    String archivesDossierWhere2 = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "archivesFile.archivesDossier.classReader", 
        "archivesFile.archivesDossier.classReadOrg", 
        "archivesFile.archivesDossier.classReadGroup");
    String archivesClassHql = "";
    if ("viewArchivesDossier".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      archivesBD.setVolume(pageSize);
      try {
        archivesDossierWhere = "((" + archivesDossierWhere + ") or (" + 
          archivesDossierRightWhere + ")) and (" + 
          archivesClassWhere2 + ")";
        if (httpServletRequest.getParameter("selectArchivesClass") != null && 
          !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
          !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
          archivesDossierWhere = String.valueOf(archivesDossierWhere) + " and archivesDossier.archivesClass.classId='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
        if (!"".equals(classId) && classId != null)
          if (!"1".equals(classId)) {
            archivesDossierWhere = String.valueOf(archivesDossierWhere) + " and  archivesDossier.archivesClass.classId= " + classId;
            httpServletRequest.setAttribute("classId", classId);
          }  
        List list = archivesBD.selectArchivesDossier(Long.valueOf(userId), Long.valueOf(orgId), 
            archivesDossierWhere, new Integer(currentPage));
        String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%'";
        List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
        httpServletRequest.setAttribute("ArchivesClassList", listClass);
        String recordCount = String.valueOf(archivesBD.getRecordCount());
        httpServletRequest.setAttribute("pageState", 
            "viewArchivesDossier");
        httpServletRequest.setAttribute("ArchivesDossierList", list);
        httpServletRequest.setAttribute("recordCount", recordCount);
        httpServletRequest.setAttribute("maxPageItems", 
            String.valueOf(pageSize));
        httpServletRequest.setAttribute("pageParameters", 
            "action,showflag");
        httpServletRequest.setAttribute("dossierState", "nologout");
        if (managerBD.hasRight(userId, "07*23*04")) {
          httpServletRequest.setAttribute("isAddRight", "yes");
          httpServletRequest.setAttribute("isModiRight", "yes");
        } 
        String selectValue = "archivesDossier.dossierId";
        String from = "com.js.oa.archives.po.ArchivesDossierPO archivesDossier";
        String modiArchivesDossierIds = archivesBD.maintenance(selectValue, from, archivesDossierRightWhere);
        httpServletRequest.setAttribute("modiArchivesDossierIds", modiArchivesDossierIds);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      String jumpName = httpServletRequest.getParameter("nameJump");
      if ("logout".equals(jumpName))
        return actionMapping.findForward("gotoArchivesDossierclass"); 
      return actionMapping.findForward("gotoArchivesDossier");
    } 
    if ("viewArchivesDossierclass".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      archivesBD.setVolume(pageSize);
      try {
        archivesDossierWhere = "((" + archivesDossierWhere + ") or (" + 
          archivesDossierRightWhere + ")) and (" + 
          archivesClassWhere2 + ")";
        if (httpServletRequest.getParameter("selectArchivesClass") != null && 
          !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
          !"0".equals(httpServletRequest.getParameter("selectArchivesClass")))
          archivesDossierWhere = String.valueOf(archivesDossierWhere) + " and archivesDossier.archivesClass.classId='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
        if (!"".equals(classId) && classId != null) {
          if (!"1".equals(classId))
            archivesDossierWhere = String.valueOf(archivesDossierWhere) + " and  archivesDossier.archivesClass.classId= " + classId; 
          httpServletRequest.setAttribute("classId", classId);
        } 
        if (httpServletRequest.getParameter("dossierNo") != null && !"".equals(httpServletRequest.getParameter("dossierNo")))
          archivesDossierWhere = String.valueOf(archivesDossierWhere) + "and archivesDossier.dossierNo like '%" + httpServletRequest.getParameter("dossierNo") + "%'"; 
        if (httpServletRequest.getParameter("dossierName") != null && !"".equals(httpServletRequest.getParameter("dossierName")))
          archivesDossierWhere = String.valueOf(archivesDossierWhere) + "and archivesDossier.dossierName like '%" + httpServletRequest.getParameter("dossierName") + "%'"; 
        if (httpServletRequest.getParameter("className") != null && !"".equals(httpServletRequest.getParameter("className")))
          archivesDossierWhere = String.valueOf(archivesDossierWhere) + "and archivesDossier.archivesClass.className like '%" + httpServletRequest.getParameter("className") + "%'"; 
        List list = archivesBD.selectArchivesDossier(Long.valueOf(userId), Long.valueOf(orgId), 
            archivesDossierWhere, new Integer(currentPage));
        String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%' ";
        List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
        httpServletRequest.setAttribute("ArchivesClassList", listClass);
        String recordCount = String.valueOf(archivesBD.getRecordCount());
        httpServletRequest.setAttribute("pageState", 
            "viewArchivesDossier");
        httpServletRequest.setAttribute("ArchivesDossierList", list);
        httpServletRequest.setAttribute("recordCount", recordCount);
        httpServletRequest.setAttribute("maxPageItems", 
            String.valueOf(pageSize));
        httpServletRequest.setAttribute("pageParameters", 
            "action,showflag");
        httpServletRequest.setAttribute("dossierState", "nologout");
        if (managerBD.hasRight(userId, "07*23*04")) {
          httpServletRequest.setAttribute("isAddRight", "yes");
          httpServletRequest.setAttribute("isModiRight", "yes");
        } 
        String selectValue = "archivesDossier.dossierId";
        String from = "com.js.oa.archives.po.ArchivesDossierPO archivesDossier";
        String modiArchivesDossierIds = archivesBD.maintenance(selectValue, from, archivesDossierRightWhere);
        httpServletRequest.setAttribute("modiArchivesDossierIds", modiArchivesDossierIds);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      return actionMapping.findForward("gotoArchivesDossierclass");
    } 
    if ("viewAddArchivesDossier".equals(action)) {
      List list = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
      archivesActionForm.reset(actionMapping, httpServletRequest);
      httpServletRequest.setAttribute("ArchivesClassList", list);
      return actionMapping.findForward("gotoaddArchivesDossier");
    } 
    if ("addArchivesDossier".equals(action)) {
      ArchivesDossierPO archivesDossierPO = 
        (ArchivesDossierPO)FillBean.transformOneToOne(archivesActionForm, ArchivesDossierPO.class);
      Long classNo = Long.valueOf(httpServletRequest.getParameter("selectArchivesClass"));
      String saveBeginTime = httpServletRequest.getParameter("saveBeginTime");
      archivesDossierPO.setSaveBeginTime(new Date(saveBeginTime));
      String saveEndTime = httpServletRequest.getParameter("saveEndTime");
      archivesDossierPO.setSaveEndTime(new Date(saveEndTime));
      String pigeonholeTime = httpServletRequest.getParameter("pigeonholeTime");
      archivesDossierPO.setPigeonholeTime(new Date(pigeonholeTime));
      archivesDossierPO.setDossierStatus(new Integer(0));
      ConversionString conversionString = new ConversionString(archivesActionForm.getClassReadID());
      archivesDossierPO.setClassReadName(archivesActionForm.getClassReadName());
      String userIds = conversionString.getUserString();
      String orgIds = conversionString.getOrgString();
      String groupIds = conversionString.getGroupString();
      archivesDossierPO.setClassReader(userIds);
      archivesDossierPO.setClassReadOrg(orgIds);
      archivesDossierPO.setClassReadGroup(groupIds);
      archivesDossierPO.setCreatedEmp(Long.valueOf(userId));
      archivesDossierPO.setCreatedOrg(Long.valueOf(orgId));
      List list = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
      httpServletRequest.setAttribute("ArchivesClassList", list);
      HashSet<ArchivesDossierAccessoryPO> Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues("archivesFileName");
      String[] saveName = httpServletRequest.getParameterValues("archivesSaveName");
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            ArchivesDossierAccessoryPO archivesDossierAccessoryPO = 
              new ArchivesDossierAccessoryPO();
            archivesDossierAccessoryPO.setAccessoryname(fileName[i]);
            archivesDossierAccessoryPO.setAccessorysavename(
                saveName[i]);
            Accessory.add(archivesDossierAccessoryPO);
          } 
        }  
      archivesDossierPO.setArchivesDossierAccessory(Accessory);
      String from = "com.js.oa.archives.po.ArchivesDossierPO archivesDossier left join archivesDossier.archivesClass archivesClass ";
      String where = "archivesDossier.dossierName ='" + archivesDossierPO.getDossierName() + 
        "' and archivesClass.classId=" + classNo;
      boolean isRepeatName = archivesBD.isRepeatName(from, where);
      if (isRepeatName) {
        httpServletRequest.setAttribute("classNo", classNo);
        archivesActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("gotoaddArchivesDossier");
      } 
      boolean result = archivesBD.addArchivesDossier(archivesDossierPO, classNo);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        archivesActionForm.reset(actionMapping, httpServletRequest);
        archivesActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("saveAndContinueDossier");
      } 
      if ("saveAndExit".equals(saveType)) {
        archivesActionForm.reset(actionMapping, httpServletRequest);
        archivesActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("saveAndExitDossier");
      } 
    } else {
      if ("viewUpdateArchivesDossier".equals(action)) {
        Long dossierId = Long.valueOf(httpServletRequest.getParameter("dossierId"));
        List list = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
        Map map = archivesBD.selectArchivesDossier(dossierId);
        ArchivesDossierVO archivesDossierVO = (ArchivesDossierVO)map.get("archivesDossierVO");
        archivesActionForm.setGeneralNo(archivesDossierVO.getGeneralNo());
        String classNo = archivesDossierVO.getClassNo();
        archivesActionForm.setCatalogNo(archivesDossierVO.getCatalogNo());
        archivesActionForm.setDossierNo(archivesDossierVO.getDossierNo());
        archivesActionForm.setDossierName(archivesDossierVO.getDossierName());
        archivesActionForm.setDossierKey(archivesDossierVO.getDossierKey());
        archivesActionForm.setPageCount(archivesDossierVO.getPageCount());
        archivesActionForm.setCopyCount(archivesDossierVO.getCopyCount());
        archivesActionForm.setPrincipal(archivesDossierVO.getPrincipal());
        archivesActionForm.setPrincipalName(archivesDossierVO.getPrincipalName());
        Date saveBeginTime = archivesDossierVO.getSaveBeginTime();
        Date saveEndTime = archivesDossierVO.getSaveEndTime();
        Date pigeonholeTime = archivesDossierVO.getPigeonholeTime();
        String userIds = archivesDossierVO.getClassReader();
        if (userIds == null || "null".equals(userIds))
          userIds = ""; 
        String orgIds = archivesDossierVO.getClassReadOrg();
        if (orgIds == null || "null".equals(orgIds))
          orgIds = ""; 
        String groupIds = archivesDossierVO.getClassReadGroup();
        if (groupIds == null || "null".equals(groupIds))
          groupIds = ""; 
        archivesActionForm.setClassReadID(String.valueOf(userIds) + orgIds + groupIds);
        archivesActionForm.setClassReadName(archivesDossierVO.getClassReadName());
        List accessoryList = (List)map.get("accessory");
        httpServletRequest.setAttribute("accessoryList", accessoryList);
        httpServletRequest.setAttribute("ArchivesClassList", list);
        httpServletRequest.setAttribute("dossierId", dossierId);
        httpServletRequest.setAttribute("classNo", classNo);
        httpServletRequest.setAttribute("saveBeginTime", saveBeginTime);
        httpServletRequest.setAttribute("saveEndTime", saveEndTime);
        httpServletRequest.setAttribute("saveStyle", archivesDossierVO.getSaveStyle());
        httpServletRequest.setAttribute("secretLevel", archivesDossierVO.getSecretLevel());
        httpServletRequest.setAttribute("pigeonholeTime", pigeonholeTime);
        return actionMapping.findForward("gotoArchivesDossierUpdateView");
      } 
      if ("modiArchivesDossier".equals(action)) {
        ArchivesDossierPO archivesDossierPO = 
          (ArchivesDossierPO)FillBean.transformOneToOne(archivesActionForm, ArchivesDossierPO.class);
        Long dossierId = Long.valueOf(httpServletRequest.getParameter("dossierId"));
        archivesDossierPO.setDossierId(dossierId);
        Long classNo = Long.valueOf(httpServletRequest.getParameter("selectArchivesClass"));
        String saveBeginTime = httpServletRequest.getParameter("saveBeginTime");
        archivesDossierPO.setSaveBeginTime(new Date(saveBeginTime));
        String saveEndTime = httpServletRequest.getParameter("saveEndTime");
        archivesDossierPO.setSaveEndTime(new Date(saveEndTime));
        String pigeonholeTime = httpServletRequest.getParameter("pigeonholeTime");
        archivesDossierPO.setPigeonholeTime(new Date(pigeonholeTime));
        archivesDossierPO.setDossierStatus(new Integer(0));
        List list = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
        httpServletRequest.setAttribute("ArchivesClassList", list);
        ConversionString conversionString = new ConversionString(archivesActionForm.getClassReadID());
        archivesDossierPO.setClassReadName(archivesActionForm.getClassReadName());
        String userIds = conversionString.getUserString();
        String orgIds = conversionString.getOrgString();
        String groupIds = conversionString.getGroupString();
        archivesDossierPO.setClassReader(userIds);
        archivesDossierPO.setClassReadOrg(orgIds);
        archivesDossierPO.setClassReadGroup(groupIds);
        HashSet<ArchivesDossierAccessoryPO> Accessory = new HashSet();
        String[] fileName = httpServletRequest.getParameterValues("archivesFileName");
        String[] saveName = httpServletRequest.getParameterValues("archivesSaveName");
        if (fileName != null)
          for (int i = 0; i < fileName.length; i++) {
            if (!"".equals(fileName[i])) {
              ArchivesDossierAccessoryPO archivesDossierAccessoryPO = 
                new ArchivesDossierAccessoryPO();
              archivesDossierAccessoryPO.setAccessoryname(fileName[i]);
              archivesDossierAccessoryPO.setAccessorysavename(saveName[i]);
              Accessory.add(archivesDossierAccessoryPO);
            } 
          }  
        archivesDossierPO.setArchivesDossierAccessory(Accessory);
        String from = "com.js.oa.archives.po.ArchivesDossierPO archivesDossier left join archivesDossier.archivesClass archivesClass ";
        String where = "archivesDossier.dossierId <>" + dossierId + 
          " and archivesDossier.dossierName ='" + archivesDossierPO.getDossierName() + 
          "' and archivesClass.classId=" + classNo;
        httpServletRequest.setAttribute("dossierId", dossierId);
        boolean isRepeatName = archivesBD.isRepeatName(from, where);
        if (isRepeatName) {
          httpServletRequest.setAttribute("classNo", classNo);
          archivesActionForm.setSaveType("isRepeatName");
          return actionMapping.findForward(
              "gotoArchivesDossierUpdateView");
        } 
        boolean result = archivesBD.modiArchivesDossier(archivesDossierPO, classNo);
        if (!result)
          return actionMapping.findForward("failure"); 
        if ("updateAndExit".equals(saveType)) {
          archivesActionForm.reset(actionMapping, httpServletRequest);
          archivesActionForm.setSaveType("updateAndExit");
          return actionMapping.findForward("gotoArchivesDossierUpdateView");
        } 
      } else {
        if ("viewReadArchivesDossier".equals(action)) {
          Long dossierId = Long.valueOf(httpServletRequest.getParameter("dossierId"));
          List list = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
          Map map = archivesBD.selectArchivesDossier(dossierId);
          ArchivesDossierVO archivesDossierVO = (ArchivesDossierVO)map.get("archivesDossierVO");
          archivesActionForm.setGeneralNo(archivesDossierVO.getGeneralNo());
          String classNo = archivesDossierVO.getClassNo();
          archivesActionForm.setCatalogNo(archivesDossierVO.getCatalogNo());
          archivesActionForm.setDossierNo(archivesDossierVO.getDossierNo());
          archivesActionForm.setDossierName(archivesDossierVO.getDossierName());
          archivesActionForm.setDossierKey(archivesDossierVO.getDossierKey());
          archivesActionForm.setPageCount(archivesDossierVO.getPageCount());
          archivesActionForm.setCopyCount(archivesDossierVO.getCopyCount());
          archivesActionForm.setPrincipal(archivesDossierVO.getPrincipal());
          archivesActionForm.setPrincipalName(archivesDossierVO.getPrincipalName());
          Date saveBeginTime = archivesDossierVO.getSaveBeginTime();
          Date saveEndTime = archivesDossierVO.getSaveEndTime();
          Date pigeonholeTime = archivesDossierVO.getPigeonholeTime();
          String userIds = archivesDossierVO.getClassReader();
          if (userIds == null || "null".equals(userIds))
            userIds = ""; 
          String orgIds = archivesDossierVO.getClassReadOrg();
          if (orgIds == null || "null".equals(orgIds))
            orgIds = ""; 
          String groupIds = archivesDossierVO.getClassReadGroup();
          if (groupIds == null || "null".equals(groupIds))
            groupIds = ""; 
          archivesActionForm.setClassReadID(String.valueOf(userIds) + orgIds + groupIds);
          archivesActionForm.setClassReadName(archivesDossierVO.getClassReadName());
          List accessoryList = (List)map.get("accessory");
          httpServletRequest.setAttribute("accessoryList", accessoryList);
          httpServletRequest.setAttribute("ArchivesClassList", list);
          httpServletRequest.setAttribute("dossierId", dossierId);
          httpServletRequest.setAttribute("classNo", classNo);
          httpServletRequest.setAttribute("saveBeginTime", saveBeginTime);
          httpServletRequest.setAttribute("saveEndTime", saveEndTime);
          httpServletRequest.setAttribute("saveStyle", archivesDossierVO.getSaveStyle());
          httpServletRequest.setAttribute("secretLevel", archivesDossierVO.getSecretLevel());
          httpServletRequest.setAttribute("pigeonholeTime", pigeonholeTime);
          return actionMapping.findForward("gotoArchivesDossierReadView");
        } 
        if ("logoutArchivesDossier".equals(action)) {
          Long dossierId = Long.valueOf(httpServletRequest.getParameter("dossierId"));
          String pager = (httpServletRequest.getParameter("pager.offset") == null) ? 
            "0" : httpServletRequest.getParameter("pager.offset");
          if (dossierId != null && !"".equals(dossierId)) {
            boolean result = archivesBD.logoutArchivesDossier(dossierId);
            if (!result) {
              httpServletRequest.setAttribute("canDelete", "No");
              ActionForward actionForward = new ActionForward();
              actionForward.setPath(
                  "/archivesAction.do?nameJump=logout&action=viewArchivesDossierclass&showflag=1&pager.offset=" + 
                  pager);
              return actionForward;
            } 
            ActionForward forward = new ActionForward();
            forward.setPath(
                "/archivesAction.do?nameJump=logout&action=viewArchivesDossierclass&showflag=1&pager.offset=" + 
                pager);
            return forward;
          } 
        } else {
          if ("logoutBatchArchivesDossier".equals(action)) {
            String checkboxIDS = httpServletRequest.getParameter("ids");
            boolean result = archivesBD.logoutBatchArchivesDossier(checkboxIDS);
            if (!result) {
              httpServletRequest.setAttribute("canDelete", "No");
              ActionForward actionForward = new ActionForward();
              actionForward.setPath(
                  "/archivesAction.do?action=viewArchivesDossierclass&showflag=1");
              return actionForward;
            } 
            ActionForward forward = new ActionForward();
            forward.setPath(
                "/archivesAction.do?action=viewArchivesDossierclass&showflag=1");
            return forward;
          } 
          if ("viewLogoutArchivesDossier".equals(action)) {
            int pageSize = 15;
            int offset = 0;
            if (httpServletRequest.getParameter("pager.offset") != null && 
              !"".equals(httpServletRequest.getParameter("pager.offset")) && 
              !"null".equals(httpServletRequest.getParameter("pager.offset")))
              offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
            int currentPage = offset / pageSize + 1;
            archivesBD.setVolume(pageSize);
            try {
              List list = archivesBD.selectLogoutArchivesDossier(Long.valueOf(userId), 
                  Long.valueOf(orgId), archivesDossierWhere, 
                  new Integer(currentPage));
              String recordCount = String.valueOf(archivesBD.getRecordCount());
              httpServletRequest.setAttribute("pageState", "viewLogoutArchivesDossier");
              httpServletRequest.setAttribute("ArchivesDossierList", list);
              httpServletRequest.setAttribute("recordCount", recordCount);
              httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
              httpServletRequest.setAttribute("pageParameters", "action");
              httpServletRequest.setAttribute("dossierState", "logout");
              if (managerBD.hasRight(userId, "07*23*04")) {
                httpServletRequest.setAttribute("isAddRight", "yes");
                httpServletRequest.setAttribute("isModiRight", "yes");
              } 
              String selectValue = "archivesDossier.dossierId";
              String from = "com.js.oa.archives.po.ArchivesDossierPO archivesDossier";
              String modiArchivesDossierIds = archivesBD.maintenance(
                  selectValue, from, archivesDossierRightWhere);
              httpServletRequest.setAttribute("modiArchivesDossierIds", modiArchivesDossierIds);
            } catch (Exception ex) {
              ex.printStackTrace();
            } 
            return actionMapping.findForward("gotoArchivesDossierclass_zhuxiao");
          } 
          if ("viewArchivesDossier_zhuxiao".equals(action)) {
            int pageSize = 15;
            int offset = 0;
            if (httpServletRequest.getParameter("pager.offset") != null)
              offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
            int currentPage = offset / pageSize + 1;
            archivesBD.setVolume(pageSize);
            try {
              archivesDossierWhere = "((" + archivesDossierWhere + ") or (" + 
                archivesDossierRightWhere + ")) and (" + 
                archivesClassWhere2 + ")";
              if (httpServletRequest.getParameter("selectArchivesClass") != null && 
                !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
                !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
                archivesDossierWhere = String.valueOf(archivesDossierWhere) + " and archivesDossier.classNO='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
              List list = archivesBD.selectArchivesDossier(Long.valueOf(userId), Long.valueOf(orgId), 
                  archivesDossierWhere, new Integer(currentPage));
              String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%'";
              List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
              httpServletRequest.setAttribute("ArchivesClassList", listClass);
              String recordCount = String.valueOf(archivesBD.getRecordCount());
              httpServletRequest.setAttribute("pageState", 
                  "viewArchivesDossier");
              httpServletRequest.setAttribute("ArchivesDossierList", list);
              httpServletRequest.setAttribute("recordCount", recordCount);
              httpServletRequest.setAttribute("maxPageItems", 
                  String.valueOf(pageSize));
              httpServletRequest.setAttribute("pageParameters", 
                  "action,showflag");
              httpServletRequest.setAttribute("dossierState", "nologout");
              if (managerBD.hasRight(userId, "07*23*04")) {
                httpServletRequest.setAttribute("isAddRight", "yes");
                httpServletRequest.setAttribute("isModiRight", "yes");
              } 
              String selectValue = "archivesDossier.dossierId";
              String from = "com.js.oa.archives.po.ArchivesDossierPO archivesDossier";
              String modiArchivesDossierIds = archivesBD.maintenance(selectValue, from, archivesDossierRightWhere);
              httpServletRequest.setAttribute("modiArchivesDossierIds", modiArchivesDossierIds);
            } catch (Exception ex) {
              ex.printStackTrace();
            } 
            return actionMapping.findForward("gotoArchivesDossierclass_zhuxiao");
          } 
          if ("resumeArchivesDossier".equals(action)) {
            String dossierId = httpServletRequest.getParameter("dossierId");
            if (dossierId != null && !"".equals(dossierId)) {
              boolean result = archivesBD.resumeArchivesDossier(
                  (dossierId.indexOf(",") > 0) ? 
                  dossierId.substring(0, dossierId.lastIndexOf(",")) : 
                  dossierId);
              if (!result)
                return actionMapping.findForward("failure"); 
              ActionForward forward = new ActionForward();
              forward.setPath(
                  "/archivesAction.do?action=viewLogoutArchivesDossier&pager.offset=" + 
                  httpServletRequest.getParameter("pager.offset"));
              return forward;
            } 
          } else if ("delArchivesDossier".equals(action)) {
            Long dossierId = Long.valueOf(httpServletRequest.getParameter(
                  "dossierId"));
            if (dossierId != null && !"".equals(dossierId)) {
              boolean result = archivesBD.delArchivesDossier(dossierId);
              if (!result)
                return actionMapping.findForward("failure"); 
              ActionForward forward = new ActionForward();
              forward.setPath(
                  "/archivesAction.do?action=viewLogoutArchivesDossier&pager.offset=" + 
                  httpServletRequest.getParameter("pager.offset"));
              return forward;
            } 
          } else {
            if ("delBatchArchivesDossier".equals(action)) {
              String checkboxIDS = httpServletRequest.getParameter("ids");
              boolean result = archivesBD.delBatchArchivesDossier(checkboxIDS);
              if (!result)
                return actionMapping.findForward("failure"); 
              ActionForward forward = new ActionForward();
              forward.setPath(
                  "/archivesAction.do?action=viewLogoutArchivesDossier&showflag=2");
              return forward;
            } 
            if ("viewArchivesFile".equals(action)) {
              String viewSQL = 
                "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey";
              String fromSQL = "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
              String whereSQL = "where archivesFile.domainId=" + domainId + 
                " and archivesFile.fileStatus=0 and archivesFile.pigeonholeStatus=0";
              String dossierId = httpServletRequest.getParameter("dossierId");
              archivesFileWhere = "((" + archivesFileWhere + ") or (" + 
                archivesFileRightWhere + ")) and (" + 
                archivesDossierWhere2 + ")";
              if (dossierId != null && !"".equals(dossierId) && !dossierId.equalsIgnoreCase("null"))
                archivesFileWhere = "((" + archivesFileWhere + ") or (" + 
                  archivesFileRightWhere + 
                  ")) and archivesFile.archivesDossier.dossierId=" + 
                  dossierId; 
              if (httpServletRequest.getParameter("searchArchivesDossier") != null)
                if (!"0".equals(httpServletRequest.getParameter(
                      "searchArchivesDossier")))
                  if (!"".equals(httpServletRequest.getParameter(
                        "searchArchivesDossier")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
                      httpServletRequest.getParameter("searchArchivesDossier");   
              if (httpServletRequest.getParameter("selectArchivesClass") != null && 
                !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
                !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.classNO='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
              if (httpServletRequest.getParameter("searchPrincipalName") != null && 
                !"".equals(httpServletRequest.getParameter("searchPrincipalName")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
                  httpServletRequest.getParameter("searchPrincipalName") + "%'"; 
              if (httpServletRequest.getParameter("searchSecretLevel") != null && 
                !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
                  httpServletRequest.getParameter("searchSecretLevel"); 
              if (httpServletRequest.getParameter("searchSaveStyle") != null && 
                !"-1".equals(httpServletRequest.getParameter("searchSaveStyle")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
                  httpServletRequest.getParameter("searchSaveStyle"); 
              if (httpServletRequest.getParameter("fileName") != null && 
                !"".equals(httpServletRequest.getParameter("fileName")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
                  httpServletRequest.getParameter("fileName") + 
                  "%'"; 
              if (httpServletRequest.getParameter("fileNo") != null && 
                !"".equals(httpServletRequest.getParameter("fileNo")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
                  httpServletRequest.getParameter("fileNo") + 
                  "%'"; 
              if (httpServletRequest.getParameter("fileKey") != null && 
                !"".equals(httpServletRequest.getParameter("fileKey")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
                  httpServletRequest.getParameter("fileKey") + 
                  "%'"; 
              String databaseType = SystemCommon.getDatabaseType();
              if (httpServletRequest.getParameter("chkStartDay") != null && 
                "1".equals(httpServletRequest.getParameter(
                    "chkStartDay"))) {
                if (databaseType.indexOf("mysql") >= 0) {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveBeginTime between '" + 
                    httpServletRequest.getParameter(
                      "searchBeginStartDay") + "' and '" + 
                    httpServletRequest.getParameter("searchEndstartDay") + 
                    "'";
                } else {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter(
                      "searchBeginStartDay") + 
                    "','L') and JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter("searchEndstartDay") + 
                    "','L')";
                } 
                httpServletRequest.setAttribute("chkStartDay", "1");
              } 
              if (httpServletRequest.getParameter("chkEndDay") != null && 
                "1".equals(httpServletRequest.getParameter(
                    "chkEndDay"))) {
                if (databaseType.indexOf("mysql") >= 0) {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveEndTime between '" + 
                    httpServletRequest.getParameter("searchBeginEndDay") + 
                    "' and '" + 
                    httpServletRequest.getParameter("searchEndEndDay") + 
                    "'";
                } else {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter("searchBeginEndDay") + 
                    "','L') and JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter("searchEndEndDay") + 
                    "','L')";
                } 
                httpServletRequest.setAttribute("chkEndDay", "1");
              } 
              whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileWhere + ") ORDER BY archivesFile.fileId DESC ";
              list(httpServletRequest, viewSQL, fromSQL, whereSQL);
              if (managerBD.hasRight(userId, "07*23*02"))
                httpServletRequest.setAttribute("isAddRight", "yes"); 
              if (managerBD.hasRight(userId, "07*23*03"))
                httpServletRequest.setAttribute("isModiRight", "yes"); 
              String selectValue = "archivesFile.fileId";
              String from = "com.js.oa.archives.po.ArchivesFilePO archivesFile";
              String modiArchivesFileIds = archivesBD.maintenance(selectValue, from, archivesFileRightWhere);
              httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
              ProcessBD procbd = new ProcessBD();
              List voitureFlowlist = new ArrayList();
              Object tmpl = procbd.getUserProcess(userId, orgIdString, "12");
              if (tmpl != null)
                voitureFlowlist = (List)tmpl; 
              String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%' ";
              List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
              httpServletRequest.setAttribute("ArchivesClassList", listClass);
              httpServletRequest.setAttribute("voitureFlowlist", voitureFlowlist);
              httpServletRequest.setAttribute("pageState", "viewArchivesFile");
              httpServletRequest.setAttribute("fileState", "nologout");
              httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
              if ("export".equals(httpServletRequest.getParameter("flag")))
                return actionMapping.findForward("gotoArchivesFileExport"); 
              List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
              httpServletRequest.setAttribute("ArchivesDossierList", list);
              String jumpName = httpServletRequest.getParameter("jumpName");
              if ("logout".equals(jumpName))
                return actionMapping.findForward("gotoArchivesFileclass"); 
              return actionMapping.findForward("gotoArchivesFile");
            } 
            if ("viewArchivesFileclass".equals(action)) {
              String viewSQL = 
                "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey,archivesFile.classNO";
              String fromSQL = "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
              String whereSQL = "where archivesFile.domainId=" + domainId + 
                " and archivesFile.fileStatus=0 and archivesFile.pigeonholeStatus=0";
              String dossierId = httpServletRequest.getParameter("dossierId");
              archivesFileWhere = "((" + archivesFileWhere + ") or (" + 
                archivesFileRightWhere + ")) and (" + 
                archivesDossierWhere2 + ")";
              if (dossierId != null && !"".equals(dossierId) && !dossierId.equalsIgnoreCase("null"))
                archivesFileWhere = "((" + archivesFileWhere + ") or (" + 
                  archivesFileRightWhere + 
                  ")) and archivesFile.archivesDossier.dossierId=" + 
                  dossierId; 
              if (httpServletRequest.getParameter("searchArchivesDossier") != null)
                if (!"0".equals(httpServletRequest.getParameter(
                      "searchArchivesDossier")))
                  if (!"".equals(httpServletRequest.getParameter(
                        "searchArchivesDossier")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
                      httpServletRequest.getParameter("searchArchivesDossier");   
              if (httpServletRequest.getParameter("selectArchivesClass") != null && 
                !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
                !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.classNO='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
              if (!"".equals(classId) && classId != null) {
                if (!"1".equals(classId))
                  whereSQL = String.valueOf(whereSQL) + " and  archivesFile.classNO= " + classId; 
                httpServletRequest.setAttribute("classId", classId);
              } 
              if (httpServletRequest.getParameter("searchPrincipalName") != null && 
                !"".equals(httpServletRequest.getParameter("searchPrincipalName")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
                  httpServletRequest.getParameter("searchPrincipalName") + "%'"; 
              if (httpServletRequest.getParameter("searchSecretLevel") != null && 
                !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
                  httpServletRequest.getParameter("searchSecretLevel"); 
              if (httpServletRequest.getParameter("searchSaveStyle") != null && 
                !"-1".equals(httpServletRequest.getParameter("searchSaveStyle")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
                  httpServletRequest.getParameter("searchSaveStyle"); 
              if (httpServletRequest.getParameter("fileName") != null && 
                !"".equals(httpServletRequest.getParameter("fileName")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
                  httpServletRequest.getParameter("fileName") + 
                  "%'"; 
              if (httpServletRequest.getParameter("fileNo") != null && 
                !"".equals(httpServletRequest.getParameter("fileNo")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
                  httpServletRequest.getParameter("fileNo") + 
                  "%'"; 
              if (httpServletRequest.getParameter("fileKey") != null && 
                !"".equals(httpServletRequest.getParameter("fileKey")))
                whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
                  httpServletRequest.getParameter("fileKey") + 
                  "%'"; 
              String databaseType = SystemCommon.getDatabaseType();
              if (httpServletRequest.getParameter("chkStartDay") != null && 
                "1".equals(httpServletRequest.getParameter(
                    "chkStartDay"))) {
                if (databaseType.indexOf("mysql") >= 0) {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveBeginTime between '" + 
                    httpServletRequest.getParameter(
                      "searchBeginStartDay") + "' and '" + 
                    httpServletRequest.getParameter("searchEndstartDay") + 
                    "'";
                } else {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter(
                      "searchBeginStartDay") + 
                    "','L') and JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter("searchEndstartDay") + 
                    "','L')";
                } 
                httpServletRequest.setAttribute("chkStartDay", "1");
              } 
              if (httpServletRequest.getParameter("chkEndDay") != null && 
                "1".equals(httpServletRequest.getParameter(
                    "chkEndDay"))) {
                if (databaseType.indexOf("mysql") >= 0) {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveEndTime between '" + 
                    httpServletRequest.getParameter("searchBeginEndDay") + 
                    "' and '" + 
                    httpServletRequest.getParameter("searchEndEndDay") + 
                    "'";
                } else {
                  whereSQL = String.valueOf(whereSQL) + 
                    " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter("searchBeginEndDay") + 
                    "','L') and JSDB.FN_STRTODATE('" + 
                    httpServletRequest.getParameter("searchEndEndDay") + 
                    "','L')";
                } 
                httpServletRequest.setAttribute("chkEndDay", "1");
              } 
              whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileWhere + ") ORDER BY archivesFile.fileId DESC ";
              list(httpServletRequest, viewSQL, fromSQL, whereSQL);
              if (managerBD.hasRight(userId, "07*23*02"))
                httpServletRequest.setAttribute("isAddRight", "yes"); 
              if (managerBD.hasRight(userId, "07*23*03"))
                httpServletRequest.setAttribute("isModiRight", "yes"); 
              String selectValue = "archivesFile.fileId";
              String from = "com.js.oa.archives.po.ArchivesFilePO archivesFile";
              String modiArchivesFileIds = archivesBD.maintenance(selectValue, from, archivesFileRightWhere);
              httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
              ProcessBD procbd = new ProcessBD();
              List voitureFlowlist = new ArrayList();
              Object tmpl = procbd.getUserProcess(userId, orgIdString, "12");
              if (tmpl != null)
                voitureFlowlist = (List)tmpl; 
              httpServletRequest.setAttribute("voitureFlowlist", voitureFlowlist);
              httpServletRequest.setAttribute("pageState", "viewArchivesFile");
              httpServletRequest.setAttribute("fileState", "nologout");
              httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
              if ("export".equals(httpServletRequest.getParameter("flag")))
                return actionMapping.findForward("gotoArchivesFileExport"); 
              List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
              String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%' ";
              List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
              httpServletRequest.setAttribute("ArchivesClassList", listClass);
              httpServletRequest.setAttribute("ArchivesDossierList", list);
              return actionMapping.findForward("gotoArchivesFileclass");
            } 
            if ("viewAddArchivesFile".equals(action)) {
              String classNo = httpServletRequest.getParameter("classId");
              httpServletRequest.setAttribute("classId", classNo);
              List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
              httpServletRequest.setAttribute("ArchivesClassList", listClass);
              String dossierType = httpServletRequest.getParameter("dossierType");
              List list = null;
              if (!"1".equals(dossierType) && dossierType != null) {
                list = archivesBD.selectArchivesDossierType(archivesDossierWhere, dossierType);
              } else {
                list = archivesBD.selectArchivesDossier(archivesDossierWhere);
              } 
              archivesActionForm.reset(actionMapping, httpServletRequest);
              httpServletRequest.setAttribute("ArchivesDossierList", list);
              if ("waitPigeonhole".equals(httpServletRequest.getParameter("type")))
                return actionMapping.findForward("gotoaddArchivesFile2"); 
              if ("1".equals(httpServletRequest.getParameter("archivesType")))
                return actionMapping.findForward("gotoaddTechnicArchivesFile"); 
              return actionMapping.findForward("gotoaddArchivesFile");
            } 
            if ("addArchivesFile".equals(action)) {
              List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
              httpServletRequest.setAttribute("ArchivesClassList", listClass);
              ArchivesFilePO archivesFilePO = 
                (ArchivesFilePO)FillBean.transformOneToOne(
                  archivesActionForm, ArchivesFilePO.class);
              Long dossierId = Long.valueOf(httpServletRequest.getParameter(
                    "selectArchivesDossier"));
              String saveBeginTime = httpServletRequest.getParameter(
                  "saveBeginTime");
              archivesFilePO.setSaveBeginTime(new Date(saveBeginTime));
              String saveEndTime = httpServletRequest.getParameter("saveEndTime");
              archivesFilePO.setSaveEndTime(new Date(saveEndTime));
              archivesFilePO.setFileStatus(new Integer(0));
              archivesFilePO.setIsBorrow(new Integer(0));
              archivesFilePO.setResidualCount(archivesActionForm.getCopyCount());
              if (!"0".equals(httpServletRequest.getParameter(
                    "selectArchivesDossier"))) {
                archivesFilePO.setPigeonholeStatus(new Integer(0));
                archivesFilePO.setPigeonholeDate(new Date());
              } else {
                archivesFilePO.setPigeonholeStatus(new Integer(1));
              } 
              archivesFilePO.setArchivesType(archivesActionForm.getArchivesType());
              archivesFilePO.setDossierNO(httpServletRequest.getParameter(
                    "dossierNO"));
              archivesFilePO.setClassNO((httpServletRequest.getParameter("selectArchivesClass") != null) ? httpServletRequest.getParameter("selectArchivesClass") : (
                  new ArchivesBD()).getClassNOByDossierNO((String)dossierId));
              ConversionString conversionString = new ConversionString(
                  archivesActionForm.getClassReadID());
              archivesFilePO.setClassReadName(archivesActionForm
                  .getClassReadName());
              String userIds = conversionString.getUserString();
              String orgIds = conversionString.getOrgString();
              String groupIds = conversionString.getGroupString();
              archivesFilePO.setClassReader(userIds);
              archivesFilePO.setClassReadOrg(orgIds);
              archivesFilePO.setClassReadGroup(groupIds);
              archivesFilePO.setCreatedEmp(Long.valueOf(userId));
              archivesFilePO.setCreatedOrg(Long.valueOf(orgId));
              archivesFilePO.setDomainId(new Long(session.getAttribute("domainId")
                    .toString()));
              HashSet<ArchivesFileAccessoryPO> Accessory = new HashSet();
              String[] fileName = (String[])null;
              String[] saveName = (String[])null;
              if ("0".equals(httpServletRequest.getParameter("isWaitPigeonhole"))) {
                fileName = httpServletRequest.getParameterValues(
                    "archivesFileName");
                saveName = httpServletRequest.getParameterValues(
                    "archivesSaveName");
              } 
              if (fileName != null)
                for (int i = 0; i < fileName.length; i++) {
                  if (!"".equals(fileName[i])) {
                    ArchivesFileAccessoryPO archivesFileAccessoryPO = 
                      new ArchivesFileAccessoryPO();
                    archivesFileAccessoryPO.setAccessoryname(fileName[i]);
                    archivesFileAccessoryPO.setAccessorysavename(saveName[i]);
                    Accessory.add(archivesFileAccessoryPO);
                  } 
                }  
              archivesFilePO.setArchivesFileAccessory(Accessory);
              Long waitPigeonholeId = new Long(0L);
              if ("1".equals(httpServletRequest.getParameter("isWaitPigeonhole"))) {
                archivesFilePO.setIsWaitPigeonhole(new Integer(1));
                archivesFilePO.setPigeonholeCaption(httpServletRequest
                    .getParameter("waitCaption"));
                archivesFilePO.setPigeonholeFileName(httpServletRequest
                    .getParameter("waitFileName"));
                archivesFilePO.setPigeonholeTypeName(httpServletRequest
                    .getParameter("waitTypeName"));
                waitPigeonholeId = Long.valueOf(httpServletRequest.getParameter(
                      "waitPigeonholeId"));
              } 
              String dossierType = httpServletRequest.getParameter("dossierType");
              List list = null;
              if (!"1".equals(dossierType) && dossierType != null) {
                list = archivesBD.selectArchivesDossierType(archivesDossierWhere, dossierType);
              } else {
                list = archivesBD.selectArchivesDossier(archivesDossierWhere);
              } 
              httpServletRequest.setAttribute("ArchivesDossierList", list);
              InformationBD informationBD = new InformationBD();
              String returnValue = archivesBD.archivesPigeonholeSet("ZSGL", 
                  session.getAttribute("domainId").toString());
              String isHold = "1";
              if (returnValue != "-1") {
                String[] values = returnValue.split(",");
                isHold = values[1].toString();
              } 
              if (archivesActionForm.getArchivesType().equals("1")) {
                archivesFilePO.setAppraisalDate(new Date(httpServletRequest
                      .getParameter("appraisalDate")));
                archivesFilePO.setApproveDate(new Date(httpServletRequest
                      .getParameter("approveDate")));
                archivesFilePO.setAwardDate(new Date(httpServletRequest
                      .getParameter("awardDate")));
              } 
              boolean result = archivesBD.addArchivesFile(archivesFilePO, 
                  dossierId, waitPigeonholeId);
              httpServletRequest.setAttribute("waitTypeName", httpServletRequest.getParameter("waitTypeName"));
              if (!result)
                return actionMapping.findForward("failure"); 
              if ("saveAndContinue".equals(saveType)) {
                if ("ZSGL".equals(httpServletRequest.getParameter(
                      "waitTypeName")) && "0".equals(isHold))
                  informationBD.singleDelete(httpServletRequest.getParameter(
                        "waitFileId")); 
                archivesActionForm.reset(actionMapping, httpServletRequest);
                if (archivesActionForm.getArchivesType().equals("1"))
                  return actionMapping.findForward(
                      "saveAndContinueTechnicFile"); 
                return actionMapping.findForward("saveAndContinueFile");
              } 
              if ("saveAndExit".equals(saveType)) {
                if ("ZSGL".equals(httpServletRequest.getParameter(
                      "waitTypeName")) && "0".equals(isHold))
                  informationBD.singleDelete(httpServletRequest.getParameter(
                        "waitFileId")); 
                archivesActionForm.reset(actionMapping, httpServletRequest);
                return actionMapping.findForward("saveAndExitFile");
              } 
            } else if ("logoutArchivesFile".equals(action)) {
              Long fileId = Long.valueOf(httpServletRequest.getParameter(
                    "fileId"));
              String dossierId = httpServletRequest.getParameter("dossierId");
              if (fileId != null && !"".equals(fileId)) {
                boolean result = archivesBD.logoutArchivesFile(fileId);
                if (!result) {
                  ActionForward actionForward = new ActionForward();
                  actionForward.setPath(
                      "/archivesAction.do?jumpName=logout&action=viewArchivesFileclass&dossierId=" + 
                      dossierId + "&canDelete=No&pager.offset=" + 
                      httpServletRequest.getParameter("pager.offset"));
                  return actionForward;
                } 
                ActionForward forward = new ActionForward();
                forward.setPath(
                    "/archivesAction.do?jumpName=logout&action=viewArchivesFileclass&dossierId=" + 
                    dossierId + "&pager.offset=" + 
                    httpServletRequest.getParameter("pager.offset"));
                return forward;
              } 
            } else {
              if ("logoutBatchArchivesFile".equals(action)) {
                String dossierId = httpServletRequest.getParameter("dossierId");
                String checkboxIDS = httpServletRequest.getParameter("ids");
                boolean result = archivesBD.logoutBatchArchivesFile(checkboxIDS);
                if (!result) {
                  httpServletRequest.setAttribute("canDelete", "No");
                  return actionMapping.findForward("gotoArchivesFile_logout");
                } 
                ActionForward forward = new ActionForward();
                forward.setPath(
                    "/archivesAction.do?action=viewArchivesFileclass&dossierId=" + 
                    dossierId);
                return forward;
              } 
              if ("viewNoPigeonholeArchivesFile".equals(action)) {
                String viewSQL = 
                  "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey";
                String fromSQL = 
                  "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
                String whereSQL = "where archivesFile.domainId=" + domainId + 
                  " and archivesFile.fileStatus=0 and archivesFile.pigeonholeStatus=1 ";
                if (httpServletRequest.getParameter("searchArchivesDossier") != null && 
                  !"0".equals(httpServletRequest.getParameter("searchArchivesDossier")) && 
                  !"".equals(httpServletRequest.getParameter("searchArchivesDossier")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
                    httpServletRequest.getParameter("searchArchivesDossier"); 
                if (httpServletRequest.getParameter("searchPrincipalName") != null && 
                  !"".equals(httpServletRequest.getParameter("searchPrincipalName")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
                    httpServletRequest.getParameter("searchPrincipalName") + 
                    "%'"; 
                if (httpServletRequest.getParameter("searchSecretLevel") != null && 
                  !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
                    httpServletRequest.getParameter("searchSecretLevel"); 
                if (httpServletRequest.getParameter("searchSaveStyle") != null && 
                  !"-1".equals(httpServletRequest.getParameter(
                      "searchSaveStyle")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
                    httpServletRequest.getParameter("searchSaveStyle"); 
                if (httpServletRequest.getParameter("fileName") != null && 
                  !"".equals(httpServletRequest.getParameter("fileName")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
                    httpServletRequest.getParameter("fileName") + 
                    "%'"; 
                if (httpServletRequest.getParameter("fileNo") != null && 
                  !"".equals(httpServletRequest.getParameter("fileNo")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
                    httpServletRequest.getParameter("fileNo") + 
                    "%'"; 
                if (httpServletRequest.getParameter("fileKey") != null && 
                  !"".equals(httpServletRequest.getParameter("fileKey")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
                    httpServletRequest.getParameter("fileKey") + 
                    "%'"; 
                String databaseType = SystemCommon.getDatabaseType();
                if (httpServletRequest.getParameter("chkStartDay") != null && 
                  "1".equals(httpServletRequest.getParameter("chkStartDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveBeginTime between '" + 
                      httpServletRequest.getParameter(
                        "searchBeginStartDay") + "' and '" + 
                      httpServletRequest.getParameter("searchEndstartDay") + 
                      "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter(
                        "searchBeginStartDay") + 
                      "','L') and JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchEndstartDay") + 
                      "','L')";
                  } 
                  httpServletRequest.setAttribute("chkStartDay", "1");
                } 
                if (httpServletRequest.getParameter("chkEndDay") != null && 
                  "1".equals(httpServletRequest.getParameter("chkEndDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveEndTime between '" + 
                      httpServletRequest.getParameter("searchBeginEndDay") + 
                      "' and '" + 
                      httpServletRequest.getParameter("searchEndEndDay") + 
                      "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchBeginEndDay") + 
                      "','L') and JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchEndEndDay") + 
                      "','L')";
                  } 
                  httpServletRequest.setAttribute("chkEndDay", "1");
                } 
                whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileRightWhere + 
                  ") ORDER BY archivesFile.fileId DESC ";
                list(httpServletRequest, viewSQL, fromSQL, whereSQL);
                if (managerBD.hasRight(userId, "07*23*02"))
                  httpServletRequest.setAttribute("isAddRight", "yes"); 
                if (managerBD.hasRight(userId, "07*23*03"))
                  httpServletRequest.setAttribute("isModiRight", "yes"); 
                String selectValue = "archivesFile.fileId";
                String from = "com.js.oa.archives.po.ArchivesFilePO archivesFile";
                String modiArchivesFileIds = archivesBD.maintenance(selectValue, from, archivesFileRightWhere);
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                ProcessBD procbd = new ProcessBD();
                List voitureFlowlist = new ArrayList();
                Object tmpl = procbd.getUserProcess(userId, orgIdString, "12");
                if (tmpl != null)
                  voitureFlowlist = (List)tmpl; 
                httpServletRequest.setAttribute("voitureFlowlist", voitureFlowlist);
                httpServletRequest.setAttribute("pageState", "viewArchivesFile");
                httpServletRequest.setAttribute("fileState", "nopigeonhole");
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                if ("export".equals(httpServletRequest.getParameter("flag")))
                  return actionMapping.findForward("gotoArchivesFileExport"); 
                List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                httpServletRequest.setAttribute("ArchivesDossierList", list);
                httpServletRequest.setAttribute("noproc", "1");
                return actionMapping.findForward("gotoArchivesFile");
              } 
              if ("viewNoPigeonholeArchivesFileCollect".equals(action)) {
                String archivesType = httpServletRequest.getParameter("archivesType");
                String viewSQL = "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey,archivesFile.pigeonholeStatus,archivesFile.archivesType,archivesFile.serialNO";
                String fromSQL = "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
                String whereSQL = "where archivesFile.domainId=" + domainId + " and (archivesFile.isWaitPigeonhole is null or archivesFile.isWaitPigeonhole != 1) " + " and archivesFile.archivesType=" + archivesType;
                if (httpServletRequest.getParameter("pigeonholeStatus") != null && !"-1".equals(httpServletRequest.getParameter("pigeonholeStatus")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.pigeonholeStatus=" + httpServletRequest.getParameter("pigeonholeStatus"); 
                if (httpServletRequest.getParameter("searchArchivesDossier") != null && !"0".equals(httpServletRequest.getParameter("searchArchivesDossier")) && !"".equals(httpServletRequest.getParameter("searchArchivesDossier")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + httpServletRequest.getParameter("searchArchivesDossier"); 
                if (httpServletRequest.getParameter("searchPrincipalName") != null && !"".equals(httpServletRequest.getParameter("searchPrincipalName")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + httpServletRequest.getParameter("searchPrincipalName") + "%'"; 
                if (httpServletRequest.getParameter("searchSecretLevel") != null && !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + httpServletRequest.getParameter("searchSecretLevel"); 
                if (httpServletRequest.getParameter("searchSaveStyle") != null && !"-1".equals(httpServletRequest.getParameter("searchSaveStyle")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + httpServletRequest.getParameter("searchSaveStyle"); 
                if (httpServletRequest.getParameter("fileName") != null && !"".equals(httpServletRequest.getParameter("fileName")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + httpServletRequest.getParameter("fileName") + "%'"; 
                if (httpServletRequest.getParameter("fileNo") != null && !"".equals(httpServletRequest.getParameter("fileNo")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + httpServletRequest.getParameter("fileNo") + "%'"; 
                if (httpServletRequest.getParameter("fileKey") != null && !"".equals(httpServletRequest.getParameter("fileKey")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + httpServletRequest.getParameter("fileKey") + "%'"; 
                String databaseType = SystemCommon.getDatabaseType();
                if (httpServletRequest.getParameter("chkStartDay") != null && "1".equals(httpServletRequest.getParameter("chkStartDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveBeginTime between '" + httpServletRequest.getParameter("searchBeginStartDay") + "' and '" + httpServletRequest.getParameter("searchEndstartDay") + "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchBeginStartDay") + "','L') and JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchEndstartDay") + "','L')";
                  } 
                  httpServletRequest.setAttribute("chkStartDay", "1");
                } 
                if (httpServletRequest.getParameter("chkEndDay") != null && "1".equals(httpServletRequest.getParameter("chkEndDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveEndTime between '" + httpServletRequest.getParameter("searchBeginEndDay") + "' and '" + httpServletRequest.getParameter("searchEndEndDay") + "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchBeginEndDay") + "','L') and JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchEndEndDay") + "','L')";
                  } 
                  httpServletRequest.setAttribute("chkEndDay", "1");
                } 
                String sortType = httpServletRequest.getParameter("sortType");
                String sortProperty = httpServletRequest.getParameter("sortProperty");
                String orderBy = "";
                if (sortType != null && !"".equals(sortType) && !"null".equals(sortType)) {
                  orderBy = String.valueOf(orderBy) + " order by archivesFile." + sortProperty + " " + sortType;
                  httpServletRequest.setAttribute("sortType", sortType);
                  httpServletRequest.setAttribute("sortProperty", sortProperty);
                } else {
                  orderBy = String.valueOf(orderBy) + "ORDER BY archivesFile.fileId DESC";
                } 
                whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileRightWhere + ") " + orderBy;
                list(httpServletRequest, viewSQL, fromSQL, whereSQL);
                if (managerBD.hasRight(userId, "07*23*02"))
                  httpServletRequest.setAttribute("isAddRight", "yes"); 
                if (managerBD.hasRight(userId, "07*23*03"))
                  httpServletRequest.setAttribute("isModiRight", "yes"); 
                String selectValue = "archivesFile.fileId";
                String from = "com.js.oa.archives.po.ArchivesFilePO archivesFile";
                String modiArchivesFileIds = archivesBD.maintenance(selectValue, from, archivesFileRightWhere);
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                ProcessBD procbd = new ProcessBD();
                List voitureFlowlist = new ArrayList();
                Object tmpl = procbd.getUserProcess(userId, orgIdString, "12");
                if (tmpl != null)
                  voitureFlowlist = (List)tmpl; 
                httpServletRequest.setAttribute("voitureFlowlist", voitureFlowlist);
                httpServletRequest.setAttribute("pageState", "viewArchivesFile");
                httpServletRequest.setAttribute("fileState", "nopigeonhole2");
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                httpServletRequest.setAttribute("action", action);
                if ("export".equals(httpServletRequest.getParameter("flag")))
                  return actionMapping.findForward("gotoArchivesFileExport"); 
                List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                httpServletRequest.setAttribute("ArchivesDossierList", list);
                httpServletRequest.setAttribute("noproc", "1");
                return actionMapping.findForward("gotoArchivesFileCollect");
              } 
              if ("viewLogoutArchivesFile".equals(action)) {
                String viewSQL = 
                  "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey";
                String fromSQL = 
                  "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
                String whereSQL = "where archivesFile.domainId=" + domainId + 
                  " and archivesFile.fileStatus=1 and archivesFile.pigeonholeStatus=0 ";
                if (httpServletRequest.getParameter("searchArchivesDossier") != null)
                  if (!"0".equals(httpServletRequest.getParameter(
                        "searchArchivesDossier")))
                    if (!"".equals(httpServletRequest.getParameter(
                          "searchArchivesDossier")))
                      whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
                        httpServletRequest.getParameter("searchArchivesDossier");   
                if (httpServletRequest.getParameter("searchPrincipalName") != null)
                  if (!"".equals(httpServletRequest.getParameter(
                        "searchPrincipalName")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
                      httpServletRequest.getParameter("searchPrincipalName") + 
                      "%'";  
                if (httpServletRequest.getParameter("searchSecretLevel") != null)
                  if (!"-1".equals(httpServletRequest.getParameter(
                        "searchSecretLevel")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
                      httpServletRequest.getParameter("searchSecretLevel");  
                if (httpServletRequest.getParameter("searchSaveStyle") != null)
                  if (!"-1".equals(httpServletRequest.getParameter(
                        "searchSaveStyle")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
                      httpServletRequest.getParameter("searchSaveStyle");  
                if (httpServletRequest.getParameter("fileName") != null)
                  if (!"".equals(httpServletRequest.getParameter(
                        "fileName")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
                      httpServletRequest.getParameter("fileName") + 
                      "%'";  
                if (httpServletRequest.getParameter("fileNo") != null)
                  if (!"".equals(httpServletRequest.getParameter(
                        "fileNo")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
                      httpServletRequest.getParameter("fileNo") + 
                      "%'";  
                if (httpServletRequest.getParameter("fileKey") != null)
                  if (!"".equals(httpServletRequest.getParameter(
                        "fileKey")))
                    whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
                      httpServletRequest.getParameter("fileKey") + 
                      "%'";  
                String databaseType = 
                  SystemCommon.getDatabaseType();
                if (httpServletRequest.getParameter("chkStartDay") != null && 
                  "1".equals(httpServletRequest.getParameter(
                      "chkStartDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveBeginTime between '" + 
                      httpServletRequest.getParameter(
                        "searchBeginStartDay") + "' and '" + 
                      httpServletRequest.getParameter("searchEndstartDay") + 
                      "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter(
                        "searchBeginStartDay") + 
                      "','L') and JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchEndstartDay") + 
                      "','L')";
                  } 
                  httpServletRequest.setAttribute("chkStartDay", "1");
                } 
                if (httpServletRequest.getParameter("chkEndDay") != null && 
                  "1".equals(httpServletRequest.getParameter(
                      "chkEndDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveEndTime between '" + 
                      httpServletRequest.getParameter("searchBeginEndDay") + 
                      "' and '" + 
                      httpServletRequest.getParameter("searchEndEndDay") + 
                      "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchBeginEndDay") + 
                      "','L') and JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchEndEndDay") + 
                      "','L')";
                  } 
                  httpServletRequest.setAttribute("chkEndDay", "1");
                } 
                whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileWhere + ") ORDER BY archivesFile.fileId DESC ";
                list(httpServletRequest, viewSQL, fromSQL, whereSQL);
                if (managerBD.hasRight(userId, "07*23*04")) {
                  httpServletRequest.setAttribute("isAddRight", "yes");
                  httpServletRequest.setAttribute("isModiRight", "yes");
                } 
                String selectValue = "archivesFile.fileId";
                String from = "com.js.oa.archives.po.ArchivesFilePO archivesFile";
                String modiArchivesFileIds = archivesBD.maintenance(selectValue, 
                    from, archivesFileRightWhere);
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                ProcessBD procbd = new ProcessBD();
                List voitureFlowlist = new ArrayList();
                Object tmpl = procbd.getUserProcess(userId, 
                    orgIdString, "12");
                if (tmpl != null)
                  voitureFlowlist = (List)tmpl; 
                httpServletRequest.setAttribute("voitureFlowlist", 
                    voitureFlowlist);
                httpServletRequest.setAttribute("pageState", "viewArchivesFile");
                httpServletRequest.setAttribute("fileState", "logout");
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                if ("export".equals(httpServletRequest.getParameter("flag")))
                  return actionMapping.findForward("gotoArchivesFileExport"); 
                List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                httpServletRequest.setAttribute("ArchivesDossierList", list);
                httpServletRequest.setAttribute("noproc", "1");
                return actionMapping.findForward("gotoArchivesFileclass_zhuxiao");
              } 
              if ("viewArchivesFile".equals(action)) {
                String viewSQL = 
                  "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey";
                String fromSQL = "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
                String whereSQL = "where archivesFile.domainId=" + domainId + 
                  " and archivesFile.fileStatus=0 and archivesFile.pigeonholeStatus=0";
                String dossierId = httpServletRequest.getParameter("dossierId");
                archivesFileWhere = "((" + archivesFileWhere + ") or (" + 
                  archivesFileRightWhere + ")) and (" + 
                  archivesDossierWhere2 + ")";
                if (dossierId != null && !"".equals(dossierId) && !dossierId.equalsIgnoreCase("null"))
                  archivesFileWhere = "((" + archivesFileWhere + ") or (" + 
                    archivesFileRightWhere + 
                    ")) and archivesFile.archivesDossier.dossierId=" + 
                    dossierId; 
                if (httpServletRequest.getParameter("searchArchivesDossier") != null)
                  if (!"0".equals(httpServletRequest.getParameter(
                        "searchArchivesDossier")))
                    if (!"".equals(httpServletRequest.getParameter(
                          "searchArchivesDossier")))
                      whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
                        httpServletRequest.getParameter("searchArchivesDossier");   
                if (httpServletRequest.getParameter("selectArchivesClass") != null && 
                  !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
                  !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.classNO='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
                if (httpServletRequest.getParameter("searchPrincipalName") != null && 
                  !"".equals(httpServletRequest.getParameter("searchPrincipalName")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
                    httpServletRequest.getParameter("searchPrincipalName") + "%'"; 
                if (httpServletRequest.getParameter("searchSecretLevel") != null && 
                  !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
                    httpServletRequest.getParameter("searchSecretLevel"); 
                if (httpServletRequest.getParameter("searchSaveStyle") != null && 
                  !"-1".equals(httpServletRequest.getParameter("searchSaveStyle")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
                    httpServletRequest.getParameter("searchSaveStyle"); 
                if (httpServletRequest.getParameter("fileName") != null && 
                  !"".equals(httpServletRequest.getParameter("fileName")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
                    httpServletRequest.getParameter("fileName") + 
                    "%'"; 
                if (httpServletRequest.getParameter("fileNo") != null && 
                  !"".equals(httpServletRequest.getParameter("fileNo")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
                    httpServletRequest.getParameter("fileNo") + 
                    "%'"; 
                if (httpServletRequest.getParameter("fileKey") != null && 
                  !"".equals(httpServletRequest.getParameter("fileKey")))
                  whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
                    httpServletRequest.getParameter("fileKey") + 
                    "%'"; 
                String databaseType = SystemCommon.getDatabaseType();
                if (httpServletRequest.getParameter("chkStartDay") != null && 
                  "1".equals(httpServletRequest.getParameter(
                      "chkStartDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveBeginTime between '" + 
                      httpServletRequest.getParameter(
                        "searchBeginStartDay") + "' and '" + 
                      httpServletRequest.getParameter("searchEndstartDay") + 
                      "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter(
                        "searchBeginStartDay") + 
                      "','L') and JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchEndstartDay") + 
                      "','L')";
                  } 
                  httpServletRequest.setAttribute("chkStartDay", "1");
                } 
                if (httpServletRequest.getParameter("chkEndDay") != null && 
                  "1".equals(httpServletRequest.getParameter(
                      "chkEndDay"))) {
                  if (databaseType.indexOf("mysql") >= 0) {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveEndTime between '" + 
                      httpServletRequest.getParameter("searchBeginEndDay") + 
                      "' and '" + 
                      httpServletRequest.getParameter("searchEndEndDay") + 
                      "'";
                  } else {
                    whereSQL = String.valueOf(whereSQL) + 
                      " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchBeginEndDay") + 
                      "','L') and JSDB.FN_STRTODATE('" + 
                      httpServletRequest.getParameter("searchEndEndDay") + 
                      "','L')";
                  } 
                  httpServletRequest.setAttribute("chkEndDay", "1");
                } 
                whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileWhere + ") ORDER BY archivesFile.fileId DESC ";
                list(httpServletRequest, viewSQL, fromSQL, whereSQL);
                if (managerBD.hasRight(userId, "07*23*02"))
                  httpServletRequest.setAttribute("isAddRight", "yes"); 
                if (managerBD.hasRight(userId, "07*23*03"))
                  httpServletRequest.setAttribute("isModiRight", "yes"); 
                String selectValue = "archivesFile.fileId";
                String from = "com.js.oa.archives.po.ArchivesFilePO archivesFile";
                String modiArchivesFileIds = archivesBD.maintenance(selectValue, from, archivesFileRightWhere);
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                ProcessBD procbd = new ProcessBD();
                List voitureFlowlist = new ArrayList();
                Object tmpl = procbd.getUserProcess(userId, orgIdString, "12");
                if (tmpl != null)
                  voitureFlowlist = (List)tmpl; 
                String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%' ";
                List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
                httpServletRequest.setAttribute("ArchivesClassList", listClass);
                httpServletRequest.setAttribute("voitureFlowlist", voitureFlowlist);
                httpServletRequest.setAttribute("pageState", "viewArchivesFile");
                httpServletRequest.setAttribute("fileState", "nologout");
                httpServletRequest.setAttribute("modiArchivesFileIds", modiArchivesFileIds);
                if ("export".equals(httpServletRequest.getParameter("flag")))
                  return actionMapping.findForward("gotoArchivesFileExport"); 
                List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                httpServletRequest.setAttribute("ArchivesDossierList", list);
                return actionMapping.findForward("gotoArchivesFileclass_zhuxiao");
              } 
              if ("resumeArchivesFile".equals(action)) {
                Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                if (fileId != null && !"".equals(fileId)) {
                  boolean result = archivesBD.resumeArchivesFile(fileId);
                  if (!result)
                    return actionMapping.findForward("failure"); 
                  ActionForward forward = new ActionForward();
                  forward.setPath(
                      "/archivesAction.do?action=viewLogoutArchivesFile&pager.offset=" + 
                      httpServletRequest.getParameter(
                        "pager.offset"));
                  return forward;
                } 
              } else if ("delArchivesFile".equals(action)) {
                Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                if (fileId != null) {
                  boolean result = archivesBD.delArchivesFile(fileId);
                  if (!result)
                    return actionMapping.findForward("failure"); 
                  String pagerOffset = httpServletRequest.getParameter("pager.offset");
                  String fileState = httpServletRequest.getParameter("fileState");
                  if ("nopigeonhole2".equals(fileState)) {
                    String archivesType = httpServletRequest.getParameter("archivesType");
                    ActionForward forward = new ActionForward();
                    forward.setPath(
                        "/archivesAction.do?action=viewNoPigeonholeArchivesFileCollect&noproc=1&archivesType=" + archivesType + 
                        "&pager.offset=" + pagerOffset);
                    return forward;
                  } 
                  if ("logout".equals(fileState)) {
                    ActionForward forward = new ActionForward();
                    forward.setPath(
                        "/archivesAction.do?action=viewLogoutArchivesFile&noproc=1&innerInfo=0&pager.offset=" + pagerOffset);
                    return forward;
                  } 
                } 
              } else if ("delBatchArchivesFile".equals(action)) {
                String checkboxIDS = httpServletRequest.getParameter("ids");
                boolean result = archivesBD.delBatchArchivesFile(checkboxIDS);
                if (!result)
                  return actionMapping.findForward("failure"); 
                String fileState = httpServletRequest.getParameter("fileState");
                if ("nopigeonhole2".equals(fileState)) {
                  String archivesType = httpServletRequest.getParameter("archivesType");
                  ActionForward forward = new ActionForward();
                  forward.setPath(
                      "/archivesAction.do?action=viewNoPigeonholeArchivesFileCollect&noproc=1&archivesType=" + archivesType);
                  return forward;
                } 
                if ("logout".equals(fileState)) {
                  ActionForward forward = new ActionForward();
                  forward.setPath(
                      "/archivesAction.do?action=viewLogoutArchivesFile&noproc=1&innerInfo=0");
                  return forward;
                } 
              } else {
                if ("viewUpdateArchivesFile".equals(action)) {
                  List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
                  httpServletRequest.setAttribute("ArchivesClassList", listClass);
                  Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                  String dossierType = httpServletRequest.getParameter("dossierType");
                  List list = null;
                  if (!"1".equals(dossierType) && dossierType != null) {
                    list = archivesBD.selectArchivesDossierType(archivesDossierWhere, dossierType);
                  } else {
                    list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                  } 
                  Map map = archivesBD.selectArchivesFile(fileId);
                  ArchivesFileVO archivesFileVO = (ArchivesFileVO)map.get("archivesFileVO");
                  archivesActionForm.setPlaceNo(archivesFileVO.getPlaceNo());
                  archivesActionForm.setRoomNo(archivesFileVO.getRoomNo());
                  archivesActionForm.setFileNo(archivesFileVO.getFileNo());
                  archivesActionForm.setClassNO(archivesFileVO.getClassNO());
                  archivesActionForm.setPrincipal(archivesFileVO.getPrincipal());
                  archivesActionForm.setPrincipalName(archivesFileVO.getPrincipalName());
                  archivesActionForm.setFileName(archivesFileVO.getFileName());
                  archivesActionForm.setFileKey(archivesFileVO.getFileKey());
                  archivesActionForm.setPageCount(archivesFileVO.getPageCount());
                  archivesActionForm.setCopyCount(archivesFileVO.getCopyCount());
                  archivesActionForm.setFileRemark(archivesFileVO.getFileRemark());
                  archivesActionForm.setDossierNO(archivesFileVO.getDossierNO());
                  Date saveBeginTime = archivesFileVO.getSaveBeginTime();
                  Date saveEndTime = archivesFileVO.getSaveEndTime();
                  String userIds = archivesFileVO.getClassReader();
                  if (userIds == null || "null".equals(userIds))
                    userIds = ""; 
                  String orgIds = archivesFileVO.getClassReadOrg();
                  if (orgIds == null || "null".equals(orgIds))
                    orgIds = ""; 
                  String groupIds = archivesFileVO.getClassReadGroup();
                  if (groupIds == null || "null".equals(groupIds))
                    groupIds = ""; 
                  if (archivesFileVO.getArchivesType().equals("1")) {
                    archivesActionForm.setSerialNO(archivesFileVO.getSerialNO());
                    archivesActionForm.setRegistrNO(archivesFileVO.getRegistrNO());
                    archivesActionForm.setClassNO(archivesFileVO.getClassNO());
                    archivesActionForm.setMicroNO(archivesFileVO.getMicroNO());
                    archivesActionForm.setDossierNO(archivesFileVO.getDossierNO());
                    archivesActionForm.setModel(archivesFileVO.getModel());
                    archivesActionForm.setArchiveCode(archivesFileVO.getArchiveCode());
                    archivesActionForm.setDuty(archivesFileVO.getDuty());
                    archivesActionForm.setAttendEmp(archivesFileVO.getAttendEmp());
                    archivesActionForm.setAttendEmpName(archivesFileVO.getAttendEmpName());
                    archivesActionForm.setPigeonholeOrg(archivesFileVO.getPigeonholeOrg());
                    archivesActionForm.setPigeonholeOrgName(archivesFileVO.getPigeonholeOrgName());
                    archivesActionForm.setAchievePhase(archivesFileVO.getAchievePhase());
                    archivesActionForm.setItemClass(archivesFileVO.getItemClass());
                    archivesActionForm.setVolume(archivesFileVO.getVolume());
                    archivesActionForm.setTotalLength(archivesFileVO.getTotalLength());
                    archivesActionForm.setDrawingNO(archivesFileVO.getDrawingNO());
                    archivesActionForm.setSpecPage(archivesFileVO.getSpecPage());
                    archivesActionForm.setCooperateUnits(archivesFileVO.getCooperateUnits());
                    archivesActionForm.setAppraisalUnit(archivesFileVO.getAppraisalUnit());
                    archivesActionForm.setPatentNO(archivesFileVO.getPatentNO());
                    archivesActionForm.setAwardUnit(archivesFileVO.getAwardUnit());
                    archivesActionForm.setHortationLevel(archivesFileVO.getHortationLevel());
                    archivesActionForm.setMerit(archivesFileVO.getMerit());
                    archivesActionForm.setTechnicData(archivesFileVO.getTechnicData());
                    archivesActionForm.setReachLevel(archivesFileVO.getReachLevel());
                    httpServletRequest.setAttribute("appraisalDate", archivesFileVO.getAppraisalDate());
                    httpServletRequest.setAttribute("approveDate", archivesFileVO.getApproveDate());
                    httpServletRequest.setAttribute("awardDate", archivesFileVO.getAwardDate());
                  } 
                  archivesActionForm.setClassReadID(String.valueOf(userIds) + orgIds + groupIds);
                  archivesActionForm.setClassReadName(archivesFileVO.getClassReadName());
                  List accessoryList = (List)map.get("accessory");
                  httpServletRequest.setAttribute("ArchivesDossierList", list);
                  httpServletRequest.setAttribute("accessoryList", accessoryList);
                  httpServletRequest.setAttribute("fileId", fileId);
                  httpServletRequest.setAttribute("dossierId", 
                      archivesFileVO.getDossierId());
                  httpServletRequest.setAttribute("saveBeginTime", saveBeginTime);
                  httpServletRequest.setAttribute("saveEndTime", saveEndTime);
                  httpServletRequest.setAttribute("saveStyle", 
                      archivesFileVO.getSaveStyle());
                  httpServletRequest.setAttribute("secretLevel", 
                      archivesFileVO.getSecretLevel());
                  if ("1".equals(httpServletRequest.getParameter("isWaitPigeonhole"))) {
                    httpServletRequest.setAttribute("pigeonholeCaption", 
                        archivesFileVO.getPigeonholeCaption());
                    httpServletRequest.setAttribute("pigeonholeTypeName", 
                        archivesFileVO.getPigeonholeTypeName());
                    httpServletRequest.setAttribute("pigeonholeFileName", 
                        archivesFileVO.getPigeonholeFileName());
                    return actionMapping.findForward("gotoArchivesFileUpdateView2");
                  } 
                  if (archivesFileVO.getArchivesType().equals("1"))
                    return actionMapping.findForward("gotoTechnicArchivesFileUpdateView"); 
                  return actionMapping.findForward("gotoArchivesFileUpdateView");
                } 
                if ("modiArchivesFile".equals(action)) {
                  List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), archivesClassWhere);
                  httpServletRequest.setAttribute("ArchivesClassList", listClass);
                  ArchivesFilePO archivesFilePO = 
                    (ArchivesFilePO)FillBean.transformOneToOne(
                      archivesActionForm, ArchivesFilePO.class);
                  Long dossierId = Long.valueOf(httpServletRequest.getParameter("selectArchivesDossier"));
                  Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                  archivesFilePO.setFileId(fileId);
                  String saveBeginTime = httpServletRequest.getParameter("saveBeginTime");
                  archivesFilePO.setSaveBeginTime(new Date(saveBeginTime));
                  String saveEndTime = httpServletRequest.getParameter("saveEndTime");
                  archivesFilePO.setSaveEndTime(new Date(saveEndTime));
                  archivesFilePO.setFileStatus(new Integer(0));
                  archivesFilePO.setIsBorrow(new Integer(0));
                  if (!"0".equals(httpServletRequest.getParameter("selectArchivesDossier"))) {
                    archivesFilePO.setPigeonholeStatus(new Integer(0));
                    archivesFilePO.setPigeonholeDate(new Date());
                  } else {
                    archivesFilePO.setPigeonholeStatus(new Integer(1));
                  } 
                  archivesFilePO.setDossierNO(httpServletRequest.getParameter("dossierNO"));
                  archivesFilePO.setClassNO(httpServletRequest.getParameter("selectArchivesClass"));
                  ConversionString conversionString = new ConversionString(
                      archivesActionForm.getClassReadID());
                  archivesFilePO.setClassReadName(archivesActionForm
                      .getClassReadName());
                  String userIds = conversionString.getUserString();
                  String orgIds = conversionString.getOrgString();
                  String groupIds = conversionString.getGroupString();
                  archivesFilePO.setClassReader(userIds);
                  archivesFilePO.setClassReadOrg(orgIds);
                  archivesFilePO.setClassReadGroup(groupIds);
                  HashSet<ArchivesFileAccessoryPO> Accessory = new HashSet();
                  String[] fileName = httpServletRequest.getParameterValues("archivesFileName");
                  String[] saveName = httpServletRequest.getParameterValues("archivesSaveName");
                  if (fileName != null)
                    for (int i = 0; i < fileName.length; i++) {
                      if (!"".equals(fileName[i])) {
                        ArchivesFileAccessoryPO archivesFileAccessoryPO = 
                          new ArchivesFileAccessoryPO();
                        archivesFileAccessoryPO.setAccessoryname(fileName[i]);
                        archivesFileAccessoryPO.setAccessorysavename(saveName[i]);
                        Accessory.add(archivesFileAccessoryPO);
                      } 
                    }  
                  archivesFilePO.setArchivesFileAccessory(Accessory);
                  List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                  httpServletRequest.setAttribute("ArchivesDossierList", list);
                  if (archivesActionForm.getArchivesType() != null && archivesActionForm.getArchivesType().equals("1")) {
                    archivesFilePO.setAppraisalDate(new Date(httpServletRequest
                          .getParameter("appraisalDate")));
                    archivesFilePO.setApproveDate(new Date(httpServletRequest
                          .getParameter("approveDate")));
                    archivesFilePO.setAwardDate(new Date(httpServletRequest
                          .getParameter("awardDate")));
                  } 
                  boolean result = archivesBD.modiArchivesFile(archivesFilePO, 
                      dossierId);
                  if (!result)
                    return actionMapping.findForward("failure"); 
                  if ("updateAndExit".equals(saveType)) {
                    archivesActionForm.reset(actionMapping, httpServletRequest);
                    return actionMapping.findForward("updateAndExitFile");
                  } 
                } else {
                  if ("viewReadArchivesFile".equals(action)) {
                    Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                    List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
                    Map map = archivesBD.selectArchivesFile(fileId);
                    ArchivesFileVO archivesFileVO = (ArchivesFileVO)map.get(
                        "archivesFileVO");
                    archivesActionForm.setPlaceNo(archivesFileVO.getPlaceNo());
                    archivesActionForm.setRoomNo(archivesFileVO.getRoomNo());
                    archivesActionForm.setFileNo(archivesFileVO.getFileNo());
                    archivesActionForm.setPrincipal(archivesFileVO.getPrincipal());
                    archivesActionForm.setPrincipalName(archivesFileVO.getPrincipalName());
                    archivesActionForm.setFileName(archivesFileVO.getFileName());
                    archivesActionForm.setFileKey(archivesFileVO.getFileKey());
                    archivesActionForm.setPageCount(archivesFileVO.getPageCount());
                    archivesActionForm.setCopyCount(archivesFileVO.getCopyCount());
                    archivesActionForm.setFileRemark(archivesFileVO.getFileRemark());
                    archivesActionForm.setDossierNO(archivesFileVO.getDossierNO());
                    Date saveBeginTime = archivesFileVO.getSaveBeginTime();
                    Date saveEndTime = archivesFileVO.getSaveEndTime();
                    String userIds = archivesFileVO.getClassReader();
                    String pigeonholefileName = archivesFileVO.getPigeonholeFileName();
                    if (pigeonholefileName != null && !"".equals(pigeonholefileName) && !"null".equals(pigeonholefileName)) {
                      httpServletRequest.setAttribute("pigeonholefileName", pigeonholefileName);
                      httpServletRequest.setAttribute("fileName", archivesFileVO.getFileName());
                    } 
                    if (userIds == null || "null".equals(userIds))
                      userIds = ""; 
                    String orgIds = archivesFileVO.getClassReadOrg();
                    if (orgIds == null || "null".equals(orgIds))
                      orgIds = ""; 
                    String groupIds = archivesFileVO.getClassReadGroup();
                    if (groupIds == null || "null".equals(groupIds))
                      groupIds = ""; 
                    archivesActionForm.setClassReadID(String.valueOf(userIds) + orgIds + groupIds);
                    archivesActionForm.setClassReadName(archivesFileVO.getClassReadName());
                    if (archivesFileVO.getArchivesType().equals("1")) {
                      archivesActionForm.setSerialNO(archivesFileVO.getSerialNO());
                      archivesActionForm.setRegistrNO(archivesFileVO.getRegistrNO());
                      archivesActionForm.setClassNO(archivesFileVO.getClassNO());
                      archivesActionForm.setMicroNO(archivesFileVO.getMicroNO());
                      archivesActionForm.setDossierNO(archivesFileVO.getDossierNO());
                      archivesActionForm.setModel(archivesFileVO.getModel());
                      archivesActionForm.setArchiveCode(archivesFileVO.getArchiveCode());
                      archivesActionForm.setDuty(archivesFileVO.getDuty());
                      archivesActionForm.setAttendEmp(archivesFileVO.getAttendEmp());
                      archivesActionForm.setAttendEmpName(archivesFileVO
                          .getAttendEmpName());
                      archivesActionForm.setPigeonholeOrg(archivesFileVO
                          .getPigeonholeOrg());
                      archivesActionForm.setPigeonholeOrgName(archivesFileVO
                          .getPigeonholeOrgName());
                      archivesActionForm.setAchievePhase(archivesFileVO
                          .getAchievePhase());
                      archivesActionForm.setItemClass(archivesFileVO.getItemClass());
                      archivesActionForm.setVolume(archivesFileVO.getVolume());
                      archivesActionForm.setTotalLength(archivesFileVO.getTotalLength());
                      archivesActionForm.setDrawingNO(archivesFileVO.getDrawingNO());
                      archivesActionForm.setSpecPage(archivesFileVO.getSpecPage());
                      archivesActionForm.setCooperateUnits(archivesFileVO
                          .getCooperateUnits());
                      archivesActionForm.setAppraisalUnit(archivesFileVO
                          .getAppraisalUnit());
                      archivesActionForm.setPatentNO(archivesFileVO.getPatentNO());
                      archivesActionForm.setAwardUnit(archivesFileVO.getAwardUnit());
                      archivesActionForm.setHortationLevel(archivesFileVO
                          .getHortationLevel());
                      archivesActionForm.setMerit(archivesFileVO.getMerit());
                      archivesActionForm.setTechnicData(archivesFileVO.getTechnicData());
                      archivesActionForm.setReachLevel(archivesFileVO.getReachLevel());
                      httpServletRequest.setAttribute("appraisalDate", 
                          archivesFileVO.getAppraisalDate());
                      httpServletRequest.setAttribute("approveDate", 
                          archivesFileVO.getApproveDate());
                      httpServletRequest.setAttribute("awardDate", 
                          archivesFileVO.getAwardDate());
                    } 
                    List accessoryList = (List)map.get("accessory");
                    httpServletRequest.setAttribute("ArchivesDossierList", list);
                    httpServletRequest.setAttribute("accessoryList", accessoryList);
                    httpServletRequest.setAttribute("fileId", fileId);
                    httpServletRequest.setAttribute("isWaitPigeonhole", httpServletRequest.getParameter("isWaitPigeonhole"));
                    httpServletRequest.setAttribute("dossierId", 
                        archivesFileVO.getDossierId());
                    httpServletRequest.setAttribute("saveBeginTime", saveBeginTime);
                    httpServletRequest.setAttribute("saveEndTime", saveEndTime);
                    httpServletRequest.setAttribute("saveStyle", 
                        archivesFileVO.getSaveStyle());
                    httpServletRequest.setAttribute("secretLevel", 
                        archivesFileVO.getSecretLevel());
                    if ("1".equals(httpServletRequest.getParameter("isWaitPigeonhole"))) {
                      httpServletRequest.setAttribute("pigeonholeCaption", 
                          archivesFileVO
                          .getPigeonholeCaption());
                      httpServletRequest.setAttribute("pigeonholeTypeName", 
                          archivesFileVO
                          .getPigeonholeTypeName());
                      httpServletRequest.setAttribute("pigeonholeFileName", 
                          archivesFileVO
                          .getPigeonholeFileName());
                      return actionMapping.findForward("gotoArchivesFileReadView2");
                    } 
                    if (archivesFileVO.getArchivesType().equals("1"))
                      return actionMapping.findForward(
                          "gotoTechnicArchivesFileReadView"); 
                    return actionMapping.findForward("gotoArchivesFileReadView");
                  } 
                  if ("viewBorrowArchivesFile".equals(action)) {
                    Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                    ArchivesFilePO po = archivesBD.loadArchivesFilePO(fileId);
                    String residualCount = String.valueOf(po.getResidualCount());
                    String fileTitle = po.getFileName();
                    httpServletRequest.setAttribute("fileId", fileId);
                    httpServletRequest.setAttribute("residualCount", residualCount);
                    httpServletRequest.setAttribute("fileTitle", fileTitle);
                    if (httpServletRequest.getParameter("processId") == null) {
                      ProcessBD procbd = new ProcessBD();
                      List<Object[]> list = new ArrayList();
                      Object tmpl = procbd.getUserProcess(session.getAttribute(
                            "userId")
                          .toString(), 
                          session
                          .getAttribute("orgIdString")
                          .toString(), "12");
                      if (tmpl != null)
                        list = (List)tmpl; 
                      if (list != null && list.size() == 1) {
                        Object[] rfObj = list.get(0);
                        Object object1 = rfObj[2];
                        Object object2 = rfObj[3];
                        Object object3 = rfObj[5];
                        Object object4 = rfObj[4];
                        Object object5 = (rfObj[6] == null) ? "" : rfObj[6];
                        String linkValue = "processType=" + object3 + 
                          "&remindField=" + object5 + 
                          "&processId=" + object1 + "&tableId=" + 
                          object4 + "&processName=" + URLEncoder.encode((String)object2);
                        return new ActionForward(
                            "/archivesAction.do?action=viewBorrowArchivesFile&fileId=" + 
                            httpServletRequest.getParameter("fileId") + 
                            "&" + 
                            linkValue + "&moduleId=12");
                      } 
                      if (list != null && list.size() > 1) {
                        httpServletRequest.setAttribute("borrowArchivesFlowlist", 
                            list);
                        return actionMapping.findForward("selectWorkFlow");
                      } 
                      httpServletRequest.setAttribute("noWorkFlow", "1");
                      return actionMapping.findForward(
                          "gotoArchivesBorrowFile_add");
                    } 
                    return actionMapping.findForward("gotoArchivesBorrowFile_add");
                  } 
                  if ("addArchivesBorrowFile".equals(action)) {
                    ArchivesBorrowPO archivesBorrowPO = 
                      (ArchivesBorrowPO)FillBean.transformOneToOne(
                        archivesActionForm, ArchivesBorrowPO.class);
                    Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
                    String borrowDate = httpServletRequest.getParameter("borrowDate");
                    archivesBorrowPO.setEmpId(archivesActionForm.getPrincipal());
                    archivesBorrowPO.setEmpName(archivesActionForm.getPrincipalName());
                    archivesBorrowPO.setOrgId(archivesActionForm.getOrgId());
                    archivesBorrowPO.setOrgName(archivesActionForm.getOrgName());
                    archivesBorrowPO.setBorrowDate(new Date(borrowDate));
                    archivesBorrowPO.setIsReturned(new Integer(0));
                    archivesBorrowPO.setStatus(new Integer(1));
                    Long recordId = archivesBD.addArchivesBorrowFile(archivesBorrowPO, 
                        fileId);
                    saveProcess(httpServletRequest, recordId.toString());
                    if (recordId.intValue() < 0)
                      return actionMapping.findForward("failure"); 
                    if ("saveAndContinue".equals(saveType)) {
                      archivesActionForm.reset(actionMapping, httpServletRequest);
                      return actionMapping.findForward("saveAndContinueBorrowFile");
                    } 
                    if ("saveAndExit".equals(saveType)) {
                      archivesActionForm.reset(actionMapping, httpServletRequest);
                      return actionMapping.findForward("saveAndExitBorrowFile");
                    } 
                  } else {
                    if ("viewArchivesBorrowFile".equals(action)) {
                      archivesBorrowFileList(httpServletRequest, archivesFileRightWhere);
                      if (managerBD.hasRight(userId, "07*23*04"))
                        httpServletRequest.setAttribute("isModiRight", "yes"); 
                      return actionMapping.findForward("gotoArchivesBorrowFile");
                    } 
                    if ("viewUpdateBorrowAuditingFile".equals(action)) {
                      String borrowIdStr = httpServletRequest.getParameter("borrowId");
                      if (borrowIdStr == null || "null".equals(borrowIdStr))
                        borrowIdStr = httpServletRequest.getParameter("record"); 
                      Long borrowId = Long.valueOf(borrowIdStr);
                      ArchivesBorrowPO archivesBorrowPO = archivesBD
                        .selectBorrowAuditingFile(borrowId);
                      archivesActionForm.setPrincipal(archivesBorrowPO.getEmpId());
                      archivesActionForm.setPrincipalName(archivesBorrowPO.getEmpName());
                      archivesActionForm.setOrgId(archivesBorrowPO.getOrgId());
                      archivesActionForm.setOrgName(archivesBorrowPO.getOrgName());
                      archivesActionForm.setBorrowCount(archivesBorrowPO.getBorrowCount());
                      archivesActionForm.setBorrowIntent((archivesBorrowPO.getBorrowIntent() == null) ? 
                          "" : 
                          archivesBorrowPO.getBorrowIntent());
                      Date borrowDate = archivesBorrowPO.getBorrowDate();
                      httpServletRequest.setAttribute("borrowDate", borrowDate);
                      Long fileId = archivesBorrowPO.getArchivesFile().getFileId();
                      String fileTitle = archivesBorrowPO.getArchivesFile().getFileName();
                      int residualCount = Integer.parseInt(archivesBD.selectResidualCount(
                            archivesBorrowPO.getArchivesFile().getFileId())) + 
                        Integer.parseInt(archivesBorrowPO.getBorrowCount().toString());
                      httpServletRequest.setAttribute("residualCount", String.valueOf(residualCount));
                      httpServletRequest.setAttribute("fileId", fileId);
                      httpServletRequest.setAttribute("fileTitle", fileTitle);
                      WorkflowCommon workflowCommon = new WorkflowCommon();
                      String curModifyField = "";
                      if (httpServletRequest.getParameter("resubmit") != null && 
                        "1".equals(httpServletRequest.getParameter("resubmit"))) {
                        curModifyField = "";
                      } else {
                        curModifyField = workflowCommon.getCurActivityWriteField(httpServletRequest);
                      } 
                      httpServletRequest.setAttribute("curModifyField", curModifyField);
                      httpServletRequest.setAttribute("myform", archivesActionForm);
                      return actionMapping.findForward("gotoBorrowAuditingFileView");
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    if ("modiBorrowAuditingFile".equals(action)) {
      String borrowIdStr = httpServletRequest.getParameter("borrowId");
      if (borrowIdStr == null || "null".equals(borrowIdStr))
        borrowIdStr = httpServletRequest.getParameter("record"); 
      Long borrowId = Long.valueOf(borrowIdStr);
      ArchivesBorrowPO archivesBorrowPO = 
        (ArchivesBorrowPO)FillBean.transformOneToOne(
          archivesActionForm, ArchivesBorrowPO.class);
      archivesBorrowPO.setEmpId(archivesActionForm.getPrincipal());
      archivesBorrowPO.setEmpName(archivesActionForm.getPrincipalName());
      archivesBorrowPO.setBorrowDate(new Date(httpServletRequest
            .getParameter("borrowDate")));
      httpServletRequest.setAttribute("borrowDate2", 
          httpServletRequest
          .getParameter("borrowDate"));
      boolean result = archivesBD.modiBorrowAuditingFile(archivesBorrowPO, 
          borrowId);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        archivesActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("modiBorrowAuditingFile");
      } 
    } else {
      if ("delBorrowAuditingFile".equals(action)) {
        Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
        boolean result = archivesBD.delBorrowAuditingFile(borrowId);
        if (!result)
          return actionMapping.findForward("failure"); 
        ActionForward forward = new ActionForward();
        forward.setPath(
            "/archivesAction.do?action=viewArchivesBorrowFile&status=0");
        return forward;
      } 
      if ("viewArchivesRestituteFile".equals(action)) {
        archivesRestituteFileList(httpServletRequest, archivesFileRightWhere);
        return actionMapping.findForward("gotoArchivesRestituteFile");
      } 
    } 
    if ("restituteArchivesFile".equals(action)) {
      Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
      Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
      boolean result = archivesBD.restituteArchivesFile(borrowId, fileId);
      if (!result)
        return actionMapping.findForward("failure"); 
      ActionForward forward = new ActionForward();
      forward.setPath("/archivesAction.do?action=viewArchivesBorrowFile");
      return forward;
    } 
    if ("archivesSearch".equals(action)) {
      String viewSQL = 
        "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey";
      String fromSQL = 
        "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
      String whereSQL = "where archivesFile.domainId=" + domainId + 
        " and archivesFile.fileStatus=0 and archivesFile.pigeonholeStatus=0  ";
      if (httpServletRequest.getParameter("searchArchivesDossier") != null && 
        !"0".equals(httpServletRequest.getParameter("searchArchivesDossier")) && 
        !"".equals(httpServletRequest.getParameter("searchArchivesDossier")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
          httpServletRequest.getParameter("searchArchivesDossier"); 
      if (httpServletRequest.getParameter("selectArchivesClass") != null && 
        !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
        !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.classNO='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
      if (httpServletRequest.getParameter("searchPrincipalName") != null)
        if (!"".equals(httpServletRequest.getParameter(
              "searchPrincipalName")))
          whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
            httpServletRequest.getParameter("searchPrincipalName") + 
            "%'";  
      if (httpServletRequest.getParameter("searchSecretLevel") != null && 
        !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
          httpServletRequest.getParameter("searchSecretLevel"); 
      if (httpServletRequest.getParameter("searchSaveStyle") != null && 
        !"-1".equals(httpServletRequest.getParameter("searchSaveStyle")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
          httpServletRequest.getParameter("searchSaveStyle"); 
      if (httpServletRequest.getParameter("fileName") != null && 
        !"".equals(httpServletRequest.getParameter("fileName")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
          httpServletRequest.getParameter("fileName") + 
          "%'"; 
      if (httpServletRequest.getParameter("fileNo") != null && 
        !"".equals(httpServletRequest.getParameter("fileNo")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
          httpServletRequest.getParameter("fileNo") + 
          "%'"; 
      if (httpServletRequest.getParameter("fileKey") != null && 
        !"".equals(httpServletRequest.getParameter("fileKey")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
          httpServletRequest.getParameter("fileKey") + 
          "%'"; 
      String databaseType = SystemCommon.getDatabaseType();
      if (httpServletRequest.getParameter("chkStartDay") != null && 
        "1".equals(httpServletRequest.getParameter("chkStartDay"))) {
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveBeginTime between '" + 
            httpServletRequest.getParameter(
              "searchBeginStartDay") + "' and '" + 
            httpServletRequest.getParameter("searchEndstartDay") + 
            "'";
        } else {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter(
              "searchBeginStartDay") + 
            "','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndstartDay") + 
            "','L')";
        } 
        httpServletRequest.setAttribute("chkStartDay", "1");
      } 
      if (httpServletRequest.getParameter("chkEndDay") != null && 
        "1".equals(httpServletRequest.getParameter(
            "chkEndDay"))) {
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveEndTime between '" + 
            httpServletRequest.getParameter("searchBeginEndDay") + 
            "' and '" + 
            httpServletRequest.getParameter("searchEndEndDay") + 
            "'";
        } else {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchBeginEndDay") + 
            "','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndEndDay") + 
            "','L')";
        } 
        httpServletRequest.setAttribute("chkEndDay", "1");
      } 
      whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileWhere + ") ORDER BY archivesFile.fileId DESC ";
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
      List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
      httpServletRequest.setAttribute("ArchivesDossierList", list);
      String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%'";
      List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
      httpServletRequest.setAttribute("ArchivesClassList", listClass);
      if ("export".equals(httpServletRequest.getParameter("flag")))
        return actionMapping.findForward("gotoArchivesSearchExport"); 
      return actionMapping.findForward("gotoArchivesSearch");
    } 
    if ("archivesSearchclass".equals(action)) {
      String viewSQL = 
        "archivesFile.fileId,archivesFile.isBorrow,archivesFile.dossierNO,archivesFile.fileName,archivesFile.fileStatus,archivesFile.pigeonholeDate,archivesFile.principalName,archivesFile.secretLevel,archivesFile.saveStyle,archivesFile.isWaitPigeonhole,archivesFile.fileNo,archivesFile.fileKey";
      String fromSQL = 
        "com.js.oa.archives.po.ArchivesFilePO archivesFile ";
      String whereSQL = "where archivesFile.domainId=" + domainId + 
        " and archivesFile.fileStatus=0 and archivesFile.pigeonholeStatus=0  ";
      if (httpServletRequest.getParameter("searchArchivesDossier") != null && 
        !"0".equals(httpServletRequest.getParameter("searchArchivesDossier")) && 
        !"".equals(httpServletRequest.getParameter("searchArchivesDossier")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.archivesDossier.dossierId=" + 
          httpServletRequest.getParameter("searchArchivesDossier"); 
      if (httpServletRequest.getParameter("selectArchivesClass") != null && 
        !"1".equals(httpServletRequest.getParameter("selectArchivesClass")) && 
        !"".equals(httpServletRequest.getParameter("selectArchivesClass")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.classNO='" + httpServletRequest.getParameter("selectArchivesClass") + "'"; 
      if (httpServletRequest.getParameter("searchPrincipalName") != null)
        if (!"".equals(httpServletRequest.getParameter(
              "searchPrincipalName")))
          whereSQL = String.valueOf(whereSQL) + " and archivesFile.principalName like '%" + 
            httpServletRequest.getParameter("searchPrincipalName") + 
            "%'";  
      if (httpServletRequest.getParameter("searchSecretLevel") != null && 
        !"-1".equals(httpServletRequest.getParameter("searchSecretLevel")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.secretLevel=" + 
          httpServletRequest.getParameter("searchSecretLevel"); 
      if (httpServletRequest.getParameter("searchSaveStyle") != null && 
        !"-1".equals(httpServletRequest.getParameter("searchSaveStyle")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.saveStyle=" + 
          httpServletRequest.getParameter("searchSaveStyle"); 
      if (httpServletRequest.getParameter("fileName") != null && 
        !"".equals(httpServletRequest.getParameter("fileName")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileName like '%" + 
          httpServletRequest.getParameter("fileName") + 
          "%'"; 
      if (httpServletRequest.getParameter("fileNo") != null && 
        !"".equals(httpServletRequest.getParameter("fileNo")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileNo like '%" + 
          httpServletRequest.getParameter("fileNo") + 
          "%'"; 
      if (httpServletRequest.getParameter("fileKey") != null && 
        !"".equals(httpServletRequest.getParameter("fileKey")))
        whereSQL = String.valueOf(whereSQL) + " and archivesFile.fileKey like '%" + 
          httpServletRequest.getParameter("fileKey") + 
          "%'"; 
      String databaseType = SystemCommon.getDatabaseType();
      if (httpServletRequest.getParameter("chkStartDay") != null && 
        "1".equals(httpServletRequest.getParameter("chkStartDay"))) {
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveBeginTime between '" + 
            httpServletRequest.getParameter(
              "searchBeginStartDay") + "' and '" + 
            httpServletRequest.getParameter("searchEndstartDay") + 
            "'";
        } else {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveBeginTime between JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter(
              "searchBeginStartDay") + 
            "','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndstartDay") + 
            "','L')";
        } 
        httpServletRequest.setAttribute("chkStartDay", "1");
      } 
      if (httpServletRequest.getParameter("chkEndDay") != null && 
        "1".equals(httpServletRequest.getParameter(
            "chkEndDay"))) {
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveEndTime between '" + 
            httpServletRequest.getParameter("searchBeginEndDay") + 
            "' and '" + 
            httpServletRequest.getParameter("searchEndEndDay") + 
            "'";
        } else {
          whereSQL = String.valueOf(whereSQL) + 
            " and archivesFile.saveEndTime between JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchBeginEndDay") + 
            "','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndEndDay") + 
            "','L')";
        } 
        httpServletRequest.setAttribute("chkEndDay", "1");
      } 
      if (!"".equals(classId) && classId != null) {
        if (!"1".equals(classId))
          whereSQL = String.valueOf(whereSQL) + " and  archivesFile.classNO= " + classId; 
        httpServletRequest.setAttribute("classId", classId);
      } 
      whereSQL = String.valueOf(whereSQL) + " and  (" + archivesFileWhere + ") ORDER BY archivesFile.fileId DESC ";
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
      List list = archivesBD.selectArchivesDossier(archivesDossierWhere);
      String whereClass = " classreader like '%$" + userId + "$%' or classreadorg like '%*" + userId + "*%' or classparentId=-1 or classreadorg like '%*-1*%'";
      List listClass = archivesBD.selectArchivesClass(Long.valueOf(userId), Long.valueOf(orgId), whereClass);
      httpServletRequest.setAttribute("ArchivesClassList", listClass);
      httpServletRequest.setAttribute("ArchivesDossierList", list);
      if ("export".equals(httpServletRequest.getParameter("flag")))
        return actionMapping.findForward("gotoArchivesSearchExport"); 
      return actionMapping.findForward("gotoArchivesSearchclass");
    } 
    if ("next".equals(action)) {
      getNextStep(httpServletRequest);
      return actionMapping.findForward("next");
    } 
    if ("oper".equals(action)) {
      oper(httpServletRequest);
      return actionMapping.findForward("next");
    } 
    if ("comp".equals(action)) {
      comp(httpServletRequest);
      return actionMapping.findForward("next");
    } 
    if ("trans".equals(action)) {
      trans(httpServletRequest, archivesActionForm);
      httpServletRequest.setAttribute("close", "1");
      return actionMapping.findForward("modiBorrowAuditingFile");
    } 
    if ("untread".equals(action)) {
      untread(httpServletRequest, archivesActionForm);
      httpServletRequest.setAttribute("close", "1");
      return actionMapping.findForward("modiBorrowAuditingFile");
    } 
    if ("pigeonholeSet".equals(action)) {
      List list = archivesBD.selectArchivesPigeonholeSet(session.getAttribute("domainId").toString());
      if (list.size() == 0) {
        httpServletRequest.setAttribute("type", "add");
      } else {
        httpServletRequest.setAttribute("type", "update");
        httpServletRequest.setAttribute("ArchivesPigeonholeSetList", list);
      } 
      return actionMapping.findForward("pigeonholeSet");
    } 
    if ("pigeonholeSetAdd".equals(action)) {
      ArchivesPigeonholeSetPO archivesPigeonholeSetPO = 
        new ArchivesPigeonholeSetPO();
      String pigeonholeSetType = httpServletRequest.getParameter("hiddenMode");
      archivesPigeonholeSetPO.setPigeonholeSetType(pigeonholeSetType);
      String pigeonholeSetPlace = httpServletRequest.getParameter("hiddenPlace");
      if (pigeonholeSetPlace != null && !"".equals(pigeonholeSetPlace)) {
        archivesPigeonholeSetPO.setPigeonholeSetPlace(
            pigeonholeSetPlace);
      } else {
        archivesPigeonholeSetPO.setPigeonholeSetPlace("");
      } 
      String isHold = httpServletRequest.getParameter("hold");
      if (isHold != null && !"".equals(isHold)) {
        archivesPigeonholeSetPO.setIsHold(Integer.valueOf(isHold));
      } else {
        archivesPigeonholeSetPO.setIsHold(new Integer(1));
      } 
      String isSendMessage = "0";
      if (httpServletRequest.getParameter("message") != null)
        isSendMessage = httpServletRequest.getParameter("message"); 
      archivesPigeonholeSetPO.setIsSendMessage(Integer.valueOf(
            isSendMessage));
      archivesPigeonholeSetPO.setDomainId(session.getAttribute("domainId").toString());
      boolean result = archivesBD.addArchivesPigeonholeSet(
          archivesPigeonholeSetPO);
      if (!result)
        return actionMapping.findForward("failure"); 
      List list = archivesBD.selectArchivesPigeonholeSet(session
          .getAttribute("domainId").toString());
      httpServletRequest.setAttribute("type", "update");
      httpServletRequest.setAttribute("ArchivesPigeonholeSetList", 
          list);
      httpServletRequest.setAttribute("massage", "归档设置成功！");
      return actionMapping.findForward("pigeonholeSet");
    } 
    if ("pigeonholeSetUpdate".equals(action)) {
      ArchivesPigeonholeSetPO archivesPigeonholeSetPO = new ArchivesPigeonholeSetPO();
      Long pigeonholeSetId = Long.valueOf(httpServletRequest.getParameter("hiddenPigeonholeSetId"));
      archivesPigeonholeSetPO.setPigeonholeSetId(pigeonholeSetId);
      String pigeonholeSetType = httpServletRequest.getParameter("hiddenMode");
      archivesPigeonholeSetPO.setPigeonholeSetType(pigeonholeSetType);
      int zsglNumber = pigeonholeSetType.indexOf("ZSGL,");
      if (zsglNumber == 0) {
        String pigeonholeSetPlace = httpServletRequest.getParameter("hiddenPlace");
        if (pigeonholeSetPlace != null && !"".equals(pigeonholeSetPlace))
          archivesPigeonholeSetPO.setPigeonholeSetPlace(
              pigeonholeSetPlace); 
        String isHold = httpServletRequest.getParameter("hold");
        if (isHold != null && !"".equals(isHold))
          archivesPigeonholeSetPO.setIsHold(Integer.valueOf(isHold)); 
      } else {
        archivesPigeonholeSetPO.setPigeonholeSetPlace("");
        archivesPigeonholeSetPO.setIsHold(new Integer(1));
      } 
      String isSendMessage = "0";
      if (httpServletRequest.getParameter("message") != null)
        isSendMessage = httpServletRequest.getParameter("message"); 
      archivesPigeonholeSetPO.setIsSendMessage(Integer.valueOf(isSendMessage));
      archivesPigeonholeSetPO.setDomainId(session.getAttribute("domainId").toString());
      boolean result = false;
      if (archivesBD.havePigeholeSetInDomain(session.getAttribute("domainId")
          .toString()) > 0) {
        result = archivesBD.updateArchivesPigeonholeSet(
            archivesPigeonholeSetPO);
      } else {
        result = archivesBD.addArchivesPigeonholeSet(
            archivesPigeonholeSetPO);
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      List list = archivesBD.selectArchivesPigeonholeSet(session.getAttribute("domainId").toString());
      httpServletRequest.setAttribute("ArchivesPigeonholeSetList", list);
      httpServletRequest.setAttribute("type", "update");
      httpServletRequest.setAttribute("massage", "归档设置成功！");
      return actionMapping.findForward("pigeonholeSet");
    } 
    if ("ArchivesPigeonholeSet".equals(action)) {
      String where = httpServletRequest.getParameter("modeValue");
      String setValue = archivesBD.archivesPigeonholeSet(where, session.getAttribute("domainId").toString());
      httpServletRequest.setAttribute("setValue", setValue);
      return actionMapping.findForward("pigeonholeSet2");
    } 
    if ("viewArchivesWaitPigeonhole".equals(action)) {
      archivesWaitPigeonholeList(httpServletRequest);
      if ("export".equals(httpServletRequest.getParameter("flag")))
        return actionMapping.findForward("archivesWaitPigeonholeExport"); 
      return actionMapping.findForward("archivesWaitPigeonhole");
    } 
    if ("delArchivesWaitPigeonholeAll".equals(action)) {
      archivesBD.delArchivesWaitPigeonholeAll(httpServletRequest
          .getParameter("id"));
      if (httpServletRequest.getParameter("searchType") != null)
        if (httpServletRequest.getParameter("searchType").equals("ZSGL")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=ZSGL&searchCaption=&searchState=-1&ts=0");
        } else if (httpServletRequest.getParameter("searchType").equals(
            "GZLC")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=GZLC&searchCaption=&searchState=-1&ts=0");
        } else if (httpServletRequest.getParameter("searchType").equals(
            "GWGL")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=GWGL&searchCaption=&searchState=-1&ts=0");
        } else if (httpServletRequest.getParameter("searchType").equals(
            "GWGL-FWGL")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=GWGL-FWGL&searchCaption=&searchState=-1&ts=0");
        } else if (httpServletRequest.getParameter("searchType").equals(
            "GWGL-SWGL")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=GWGL-SWGL&searchCaption=&searchState=-1&ts=0");
        } else if (httpServletRequest.getParameter("searchType").equals(
            "GWGL-WJSS")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=GWGL-WJSS&searchCaption=&searchState=-1&ts=0");
        } else if (httpServletRequest.getParameter("searchType").equals(
            "COOP")) {
          httpServletResponse.sendRedirect("/jsoa/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=COOP&searchCaption=&searchState=-1&ts=0");
        }  
      return null;
    } 
    if ("delArchivesWaitPigeonhole".equals(action)) {
      Long waitPigeonholeId = Long.valueOf(httpServletRequest
          .getParameter(
            "waitPigeonholeId"));
      String fileId = httpServletRequest.getParameter("fileId");
      String typeName = httpServletRequest.getParameter("typeName");
      boolean result = archivesBD.delArchivesWaitPigeonhole(
          waitPigeonholeId);
      Integer result2 = new Integer(0);
      if (result) {
        "ZSGL".equals(typeName);
        archivesWaitPigeonholeList(httpServletRequest);
        ActionForward forward = new ActionForward();
        forward.setPath(
            "/archivesAction.do?action=viewArchivesWaitPigeonhole&isSearch=1&searchType=" + 
            httpServletRequest.getParameter("searchType") + 
            "&=searchState=" + 
            httpServletRequest.getParameter("searchState") + 
            "&flag=" + httpServletRequest.getParameter("flag") + 
            "&searchCaption=" + 
            httpServletRequest.getParameter("searchCaption") + 
            "&pager.offset=" + 
            httpServletRequest.getParameter("pager.offset"));
        return forward;
      } 
      return actionMapping.findForward("failure");
    } 
    if ("creatorCancel".equals(action)) {
      String cancelReason = httpServletRequest.getParameter(
          "cancelReason");
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String processName = httpServletRequest.getParameter("processName");
      String workId = httpServletRequest.getParameter("workId");
      String processId = httpServletRequest.getParameter("processId");
      String remindValue = "";
      if (httpServletRequest.getParameter("remindValue") != null)
        remindValue = httpServletRequest.getParameter("remindValue"); 
      WorkFlowBD workFlowBD = new WorkFlowBD();
      List alist = workFlowBD.getWorkUserLogin(tableId, recordId, 
          processId);
      ArrayList<String> sqlList = new ArrayList();
      sqlList.add("delete JSF_WORK where worktable_id = " + tableId + 
          " and workrecord_id = " + 
          recordId + " and workProcess_id = " + processId + 
          " and wf_work_id <> " + workId);
      if (httpServletRequest.getParameter("channelType") != null) {
        sqlList.add("update JSF_WORK set workTitle = '您已取消了您的" + 
            remindValue + processName + 
            "', workStatus = -2, workCancelReason = '" + 
            cancelReason + 
            "', workMainLinkFile = '/jsoa/InfoProcAction.do?channelType=" + 
            httpServletRequest.getParameter("channelType") + 
            "&informationType=" + 
            httpServletRequest.getParameter("informationType") + 
            "&redhead=" + 
            httpServletRequest.getParameter("redhead") + 
            "' where wf_work_id = " + workId);
      } else {
        sqlList.add("update JSF_WORK set workTitle = '您已取消了您的" + 
            remindValue + processName + 
            "', workStatus = -2, workCancelReason = '" + 
            cancelReason + 
            "', workMainLinkFile = '/jsoa/WorkFlowReSubmitAction.do?pp=1' where wf_work_id = " + 
            workId);
      } 
      workFlowBD.updateTable(sqlList);
      archivesBD.delBorrowAuditingFile(new Long(recordId));
      return actionMapping.findForward("cancel");
    } 
    return actionMapping.findForward("archivesWaitPigeonhole");
  }
  
  public void archivesBorrowFileList(HttpServletRequest httpServletRequest, String Where) {
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = new Long(session.getAttribute("domainId").toString());
    String userId = session.getAttribute("userId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "archivesBorrow.borrowId,archivesBorrow.empName,archivesBorrow.borrowIntent,archivesBorrow.borrowDate,archivesFile.placeNo,archivesFile.roomNo,archivesFile.fileNo,archivesFile.fileName,archivesFile.fileId,archivesBorrow.orgName,archivesBorrow.borrowCount,archivesBorrow.status", 
        "com.js.oa.archives.po.ArchivesBorrowPO archivesBorrow join archivesBorrow.archivesFile archivesFile", 
        "where (" + Where + 
        " or archivesBorrow.empId=" + userId + ") and archivesBorrow.domainId=" + domainId + 
        " and archivesBorrow.isReturned=0 ORDER BY archivesBorrow.borrowDate DESC");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("ArchivsBorrowFileList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void archivesRestituteFileList(HttpServletRequest httpServletRequest, String Where) {
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = new Long(session.getAttribute("domainId").toString());
    String userId = session.getAttribute("userId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "archivesBorrow.borrowId,archivesBorrow.empName,archivesBorrow.borrowIntent,archivesBorrow.borrowDate,archivesFile.placeNo,archivesFile.roomNo,archivesFile.fileNo,archivesFile.fileName,archivesBorrow.returnDate,archivesBorrow.orgName,archivesBorrow.borrowCount", 
        "com.js.oa.archives.po.ArchivesBorrowPO archivesBorrow join archivesBorrow.archivesFile archivesFile", 
        "where (" + Where + 
        " or archivesBorrow.empId=" + userId + ") and archivesBorrow.domainId=" + domainId + 
        " and archivesBorrow.isReturned=1 ORDER BY archivesBorrow.borrowDate DESC");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("ArchivsRestituteFileList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void archivesWaitPigeonholeList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !"null".equals(httpServletRequest.getParameter("pager.offset")))
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    if ("export".equals(httpServletRequest.getParameter("flag")))
      pageSize = 100000; 
    int currentPage = offset / pageSize + 1;
    String where = "";
    String searchCaption = "";
    String searchType = "";
    String searchState = "";
    String searchStatus = "";
    if ("1".equals(httpServletRequest.getParameter("isSearch"))) {
      searchCaption = httpServletRequest.getParameter("searchCaption");
      where = " where archivesWaitPigeonhole.pigeonholeCaption like '%" + ((
        searchCaption != null && searchCaption.length() > 0 && 
        !"null".equals(searchCaption)) ? searchCaption : "") + 
        "%'";
      searchType = httpServletRequest.getParameter("searchType");
      if (!"0".equals(httpServletRequest.getParameter("searchType")))
        if (searchType != null && "GWGL".equals(searchType)) {
          where = String.valueOf(where) + 
            " and archivesWaitPigeonhole.pigeonholeTypeName like '" + 
            searchType + "%'";
        } else {
          where = String.valueOf(where) + " and archivesWaitPigeonhole.pigeonholeTypeName='" + 
            searchType + "'";
        }  
      searchState = httpServletRequest.getParameter("searchState");
      if (searchState != null && searchState.length() > 0 && 
        !"null".equals(searchState) && 
        !"-1".equals(httpServletRequest.getParameter("searchState")))
        where = String.valueOf(where) + " and archivesWaitPigeonhole.pigeonholeState=" + 
          searchState; 
    } 
    ManagerService managerBD = new ManagerService();
    String whereSql = "";
    String type = httpServletRequest.getParameter("searchType");
    whereSql = managerBD.getRightFinalWhere(session.getAttribute(
          "userId").toString(), 
        session.getAttribute("orgId").toString(), 
        "07*23*03", "archivesWaitPigeonhole.createdOrg", 
        "archivesWaitPigeonhole.createdEmp");
    whereSql = String.valueOf(whereSql) + " and  1=1 ";
    where = where.equals("") ? (" where " + whereSql) : (
      String.valueOf(where) + " and (" + whereSql + ")");
    Page page = new Page(
        "archivesWaitPigeonhole.waitPigeonholeId,archivesWaitPigeonhole.pigeonholeFileId,archivesWaitPigeonhole.pigeonholeCaption,archivesWaitPigeonhole.pigeonholeTypeName,archivesWaitPigeonhole.pigeonholeFileName,archivesWaitPigeonhole.pigeonholePromulgator,archivesWaitPigeonhole.pigeonholeDate,archivesWaitPigeonhole.pigeonholeState", 
        "com.js.oa.archives.po.ArchivesWaitPigeonholePO archivesWaitPigeonhole", 
        where + 
        " and archivesWaitPigeonhole.domainId = " + 
        session.getAttribute("domainId") + 
        " ORDER BY archivesWaitPigeonhole.pigeonholeDate DESC");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchCaption", searchCaption);
    httpServletRequest.setAttribute("searchType", searchType);
    httpServletRequest.setAttribute("searchState", searchState);
    httpServletRequest.setAttribute("searchStatus", searchStatus);
    httpServletRequest.setAttribute("ArchivesWaitPigeonholeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pager.offset", 
        String.valueOf(offset));
    httpServletRequest.setAttribute("pageParameters", 
        "action,searchCaption,searchType,searchState,isSearch");
  }
  
  private void saveProcess(HttpServletRequest httpServletRequest, String recordId) {
    int moduleId = 10, formType = 0;
    String mainLinkFile = 
      "/jsoa/archivesAction.do?action=viewUpdateBorrowAuditingFile&borrowId=" + 
      recordId;
    String cancelFile = 
      "window.open('/jsoa/archives/archivesBorrowFile_cancel.jsp?recordId=" + 
      recordId + 
      "&workId=workIdValue&tableId=tableIdValue&processName=" + 
      httpServletRequest.getParameter("fileType") + "&remindValue=','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')";
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
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    ArchivesBorrowPO archivesBorrowPO = new ArchivesBorrowPO();
    archivesBorrowPO.setBorrowIntent(httpServletRequest.getParameter("borrowIntent"));
    archivesBorrowPO.setBorrowCount(Integer.valueOf(httpServletRequest
          .getParameter("borrowCount")));
    archivesBorrowPO.setOrgId(Long.valueOf(httpServletRequest.getParameter("orgId")));
    archivesBorrowPO.setOrgName(httpServletRequest.getParameter("orgName"));
    archivesBorrowPO.setEmpId(Long.valueOf(httpServletRequest.getParameter("principal")));
    archivesBorrowPO.setEmpName(httpServletRequest.getParameter(
          "principalName"));
    archivesBorrowPO.setBorrowDate(new Date(httpServletRequest.getParameter("borrowDate")));
    boolean result = (new ArchivesBD()).modiBorrowAuditingFile(
        archivesBorrowPO, borrowId);
    String toMainFile = 
      "/jsoa/archivesAction.do?action=viewUpdateBorrowAuditingFile&borrowId=" + 
      borrowId.toString();
    (new ProcessSubmit()).operate(httpServletRequest, toMainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void comp(HttpServletRequest httpServletRequest) {
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    ArchivesBorrowPO archivesBorrowPO = new ArchivesBorrowPO();
    archivesBorrowPO.setBorrowIntent(httpServletRequest.getParameter("borrowIntent"));
    archivesBorrowPO.setBorrowCount(Integer.valueOf(httpServletRequest.getParameter("borrowCount")));
    archivesBorrowPO.setOrgId(Long.valueOf(httpServletRequest.getParameter("orgId")));
    archivesBorrowPO.setOrgName(httpServletRequest.getParameter("orgName"));
    archivesBorrowPO.setStatus(Integer.valueOf("2"));
    archivesBorrowPO.setEmpId(Long.valueOf(httpServletRequest.getParameter("principal")));
    archivesBorrowPO.setEmpName(httpServletRequest.getParameter("principalName"));
    archivesBorrowPO.setBorrowDate(new Date(httpServletRequest.getParameter("borrowDate")));
    boolean result = (new ArchivesBD()).modiBorrowAuditingFile(
        archivesBorrowPO, borrowId);
    httpServletRequest.setAttribute("close", "1");
    (new ProcessSubmit()).comp(httpServletRequest);
  }
  
  private void trans(HttpServletRequest httpServletRequest, ArchivesActionForm archivesActionForm) {
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    ArchivesBorrowPO archivesBorrowPO = 
      (ArchivesBorrowPO)FillBean.transformOneToOne(
        archivesActionForm, ArchivesBorrowPO.class);
    archivesBorrowPO.setEmpId(archivesActionForm.getPrincipal());
    archivesBorrowPO.setEmpName(archivesActionForm.getPrincipalName());
    archivesBorrowPO.setBorrowDate(new Date(httpServletRequest.getParameter("borrowDate")));
    httpServletRequest.setAttribute("borrowDate2", 
        httpServletRequest
        .getParameter("borrowDate"));
    boolean result = (new ArchivesBD()).modiBorrowAuditingFile(
        archivesBorrowPO, borrowId);
    String mainFile = 
      "/jsoa/archivesAction.do?action=viewUpdateBorrowAuditingFile&borrowId=" + 
      borrowId;
    (new ProcessSubmit()).transition(httpServletRequest, mainFile);
  }
  
  private void untread(HttpServletRequest httpServletRequest, ArchivesActionForm archivesActionForm) {
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    (new ArchivesBD()).untreadBorrowAuditingFile(borrowId);
    (new ProcessSubmit()).Untread(httpServletRequest);
  }
  
  public boolean addArchivesWaitPigeonhole(String pigeonholeCaption, String pigeonholeFileName, Long pigeonholeFileId, String pigeonholeTypeName, String pigeonholePromulgator, Date pigeonholeDate, String isSendMessage, HttpServletRequest httpServletRequest) {
    boolean result = false;
    ArchivesBD archivesBD = new ArchivesBD();
    String domainId = (String)httpServletRequest.getSession().getAttribute(
        "domainId");
    try {
      result = archivesBD.addArchivesWaitPigeonhole(pigeonholeCaption, 
          pigeonholeFileName, pigeonholeFileId, pigeonholeTypeName, 
          pigeonholePromulgator, pigeonholeDate, "", 
          httpServletRequest.getSession().getAttribute("orgId")
          .toString(), domainId);
      if (result) {
        ModelSendMsg sendMsg = new ModelSendMsg();
        boolean falg = sendMsg.judgePurviewMessage("档案管理-归档", domainId);
        if (falg && 
          "1".equals(isSendMessage)) {
          String userIds = archivesBD.selectRightUserIds(
              "档案管理-归档管理", "归档", domainId);
          boolean bool = sendMsg.sendSystemMessage("档案管理-归档", 
              pigeonholeCaption, userIds, "", 
              httpServletRequest);
        } 
        result = true;
      } 
    } catch (Exception ex) {
      System.out.println("AddArchivesWaitPigeonhole Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public boolean addArchivesWaitPigeonhole(String pigeonholeCaption, String pigeonholeFileName, Long pigeonholeFileId, String pigeonholeTypeName, String pigeonholePromulgator, Date pigeonholeDate, String isSendMessage, HttpServletRequest httpServletRequest, String createdEmp, String createdOrg, String fileType) {
    boolean result = false;
    ArchivesBD archivesBD = new ArchivesBD();
    String domainId = (String)httpServletRequest.getSession().getAttribute(
        "domainId");
    try {
      if (fileType != null && fileType.length() > 0)
        pigeonholeTypeName = String.valueOf(pigeonholeTypeName) + "-" + fileType; 
      result = archivesBD.addArchivesWaitPigeonhole(pigeonholeCaption, 
          pigeonholeFileName, pigeonholeFileId, pigeonholeTypeName, 
          pigeonholePromulgator, pigeonholeDate, createdEmp, 
          createdOrg, domainId);
      if (result) {
        ModelSendMsg sendMsg = new ModelSendMsg();
        boolean falg = sendMsg.judgePurviewMessage("档案管理-归档", domainId);
        if (falg && 
          "1".equals(isSendMessage)) {
          String userIds = archivesBD.selectRightUserIds(
              "档案管理-归档管理", "归档", domainId);
          boolean bool = sendMsg.sendSystemMessage("档案管理-归档", 
              pigeonholeCaption, userIds, "", 
              httpServletRequest);
        } 
        result = true;
      } 
    } catch (Exception ex) {
      System.out.println("AddArchivesWaitPigeonhole Exception:" + ex.getMessage());
    } 
    return result;
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !"".equals(httpServletRequest.getParameter("pager.offset")) && 
      !"null".equals(httpServletRequest.getParameter("pager.offset")))
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    if ("export".equals(httpServletRequest.getParameter("flag"))) {
      page.setPageSize(999999);
      page.setcurrentPage(1);
    } else {
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
    } 
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
    httpServletRequest.setAttribute("ArchivesFileList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,searchArchivesDossier,searchPrincipalName,searchSecretLevel,searchSaveStyle,searchBeginStartDay,searchEndstartDay,chkStartDay,searchBeginEndDay,searchEndEndDay,chkEndDay,archivesType,dossierId");
  }
}
