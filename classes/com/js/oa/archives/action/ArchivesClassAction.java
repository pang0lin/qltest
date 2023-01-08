package com.js.oa.archives.action;

import com.js.oa.archives.po.ArchivesClassPO;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.archives.vo.ArchivesClassVO;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.ConversionString;
import com.js.util.util.FillBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ArchivesClassAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
    ArchivesClassActionForm archivesClassActionForm = (ArchivesClassActionForm)actionForm;
    ArchivesBD archivesBD = new ArchivesBD();
    String action = httpServletRequest.getParameter("action");
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = session.getAttribute("domainId").toString();
    ManagerService managerBD = new ManagerService();
    String archivesClassWhere = 




      
      String.valueOf(managerBD.getScopeFinalWhere(session.getAttribute("userId").toString(), session.getAttribute("orgId").toString(), session.getAttribute("orgIdString").toString(), "archivesClass.classReader", "archivesClass.classReadOrg", "archivesClass.classReadGroup")) + " and archivesClass.classParentId!=-1 ";
    String archivesClassRightWhere = managerBD.getRightFinalWhere(
        userId.toString(), 
        orgId.toString(), 
        "07*23*01", "archivesClass.createdOrg", "archivesClass.createdEmp");
    if ("viewArchivesClass".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      archivesBD.setVolume(pageSize);
      try {
        List list = archivesBD.selectArchivesClass(userId, orgId, 
            String.valueOf(archivesClassRightWhere) + " and archivesClass.classParentId!=-1  and archivesClass.domainId = " + domainId, 
            new Integer(currentPage));
        String recordCount = String.valueOf(archivesBD.getRecordCount());
        httpServletRequest.setAttribute("ArchivesClassList", list);
        httpServletRequest.setAttribute("recordCount", recordCount);
        httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
        httpServletRequest.setAttribute("pageParameters", "action");
        if (managerBD.hasRight(userId.toString(), "07*23*01")) {
          httpServletRequest.setAttribute("isAddRight", "yes");
          httpServletRequest.setAttribute("isModiRight", "yes");
        } 
        String selectValue = "archivesClass.classId";
        String from = "com.js.oa.archives.po.ArchivesClassPO archivesClass";
        String modiArchivesClassIds = archivesBD.maintenance(
            selectValue, 
            from, 
            String.valueOf(archivesClassRightWhere) + " and archivesClass.domainId = " + domainId);
        httpServletRequest.setAttribute("modiArchivesClassIds", modiArchivesClassIds);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      return actionMapping.findForward("gotoArchivesClass");
    } 
    if ("viewAddArchivesClass".equals(action)) {
      List list = archivesBD.selectArchivesClass(userId, orgId, archivesClassWhere);
      archivesClassActionForm.reset(actionMapping, httpServletRequest);
      httpServletRequest.setAttribute("ArchivesClassList", list);
      return actionMapping.findForward("gotoaddArchivesClass");
    } 
    if ("addArchivesClass".equals(action)) {
      ArchivesClassPO archivesClassPO = 
        (ArchivesClassPO)FillBean.transformOneToOne(
          archivesClassActionForm, 
          ArchivesClassPO.class);
      ConversionString conversionString = new ConversionString(
          archivesClassActionForm.getClassReadID());
      String classParentId = httpServletRequest.getParameter(
          "selectArchivesClass");
      String taxis = httpServletRequest.getParameter("taxis");
      String state = archivesClassActionForm.getState();
      String where = "archivesClass.className ='" + 
        archivesClassPO.getClassName() + "' and archivesClass.domainId=" + domainId;
      if ("0".equals(classParentId)) {
        archivesClassPO.setClassLevel(new Integer(0));
        archivesClassPO.setClassParentId(new Long(0L));
      } else {
        ArchivesClassVO archivesClassVO = archivesBD
          .selectArchivesClass(new Long(
              classParentId));
        if (taxis != null && "1".equals(taxis)) {
          archivesClassPO.setClassParentId(archivesClassVO.getClassId());
          archivesClassPO.setClassLevel(archivesClassVO.getClassLevel());
          archivesClassPO.setClassOrderCode(archivesClassVO
              .getClassOrderCode());
          where = String.valueOf(where) + " and archivesClass.classParentId=" + 
            archivesClassPO.getClassParentId();
        } else {
          archivesClassPO.setClassParentId(new Long(classParentId));
          archivesClassPO.setClassLevel(new Integer(Integer.parseInt(
                  archivesClassVO.getClassLevel().toString()) + 1));
          where = String.valueOf(where) + " and archivesClass.classParentId=" + 
            archivesClassPO.getClassParentId();
        } 
        archivesClassPO.setClassIdString(archivesClassVO
            .getClassIdString());
      } 
      archivesClassPO.setClassReadName(archivesClassActionForm
          .getClassReadName());
      String userIds = conversionString.getUserString();
      String orgIds = conversionString.getOrgString();
      String groupIds = conversionString.getGroupString();
      archivesClassPO.setclassReader(userIds);
      archivesClassPO.setClassReadOrg(orgIds);
      archivesClassPO.setClassReadGroup(groupIds);
      archivesClassPO.setDomainId(session.getAttribute("domainId").toString());
      archivesBD.selectArchivesClass(userId, orgId, archivesClassWhere);
      List list = archivesBD.selectArchivesClass(userId, orgId, String.valueOf(archivesClassWhere) + " and archivesClass.classParentId!=-1");
      httpServletRequest.setAttribute("ArchivesClassList", list);
      String from = "com.js.oa.archives.po.ArchivesClassPO archivesClass";
      boolean isRepeatName = archivesBD.isRepeatName(from, where);
      if (isRepeatName) {
        classParentId = httpServletRequest.getParameter("selectArchivesClass");
        httpServletRequest.setAttribute("classParentId", classParentId);
        archivesClassActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("gotoaddArchivesClass");
      } 
      boolean result = archivesBD.addArchivesClass(archivesClassPO, taxis, state);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        ActionForward forward = new ActionForward();
        forward.setPath("/archivesClassAction.do?action=viewAddArchivesClass");
        return forward;
      } 
      if ("saveAndExit".equals(saveType)) {
        archivesClassActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("gotoaddArchivesClass");
      } 
    } else {
      if ("viewUpdateArchivesClass".equals(action)) {
        Long classId = Long.valueOf(httpServletRequest.getParameter(
              "classId"));
        List list = archivesBD.selectArchivesClass(userId, orgId, 
            String.valueOf(archivesClassWhere) + " and archivesClass.classParentId!=-1");
        ArchivesClassVO archivesClassVO = archivesBD.selectArchivesClass(
            classId);
        archivesClassActionForm.setClassName(archivesClassVO.getClassName());
        String userIds = archivesClassVO.getClassReader();
        if (userIds == null || "null".equals(userIds))
          userIds = ""; 
        String orgIds = archivesClassVO.getClassReadOrg();
        if (orgIds == null || "null".equals(orgIds))
          orgIds = ""; 
        String groupIds = archivesClassVO.getClassReadGroup();
        if (groupIds == null || "null".equals(groupIds))
          groupIds = ""; 
        archivesClassActionForm.setClassReadID(String.valueOf(userIds) + orgIds + groupIds);
        String classParentId = archivesClassVO.getClassParentId().toString();
        archivesClassActionForm.setClassReadName(archivesClassVO
            .getClassReadName());
        httpServletRequest.setAttribute("ArchivesClassList", list);
        httpServletRequest.setAttribute("classId", classId);
        httpServletRequest.setAttribute("classParentId", classParentId);
        httpServletRequest.setAttribute("classOrderCode", 
            archivesClassVO.getClassOrderCode());
        httpServletRequest.setAttribute("classIdString", 
            archivesClassVO.getClassIdString());
        return actionMapping.findForward("gotoArchivesClassUpdateView");
      } 
      if ("deleteArchivesClass".equals(action)) {
        Long classId = Long.valueOf(httpServletRequest.getParameter(
              "classId"));
        if (classId != null && !"".equals(classId)) {
          boolean result = archivesBD.deleteArchivesClass(classId);
          if (!result) {
            httpServletRequest.setAttribute("canDelete", "No");
            return actionMapping.findForward("gotoArchivesClass_delete");
          } 
          ActionForward forward = new ActionForward();
          forward.setPath("/archivesClassAction.do?action=viewArchivesClass");
          return forward;
        } 
      } else {
        if ("deleteBatchArchivesClass".equals(action)) {
          String checkboxIDS = httpServletRequest.getParameter("ids");
          boolean result = archivesBD.deleteBatchArchivesClass(checkboxIDS);
          if (!result) {
            httpServletRequest.setAttribute("canDelete", "No");
            return actionMapping.findForward(
                "gotoArchivesClass_delete");
          } 
          ActionForward forward = new ActionForward();
          forward.setPath("/archivesClassAction.do?action=viewArchivesClass");
          return forward;
        } 
        if ("modiArchivesClass".equals(action)) {
          ArchivesClassPO archivesClassPO = 
            (ArchivesClassPO)FillBean.transformOneToOne(
              archivesClassActionForm, 
              ArchivesClassPO.class);
          ConversionString conversionString = new ConversionString(
              archivesClassActionForm.getClassReadID());
          String classId = httpServletRequest.getParameter("classId");
          String classOrderCode = httpServletRequest.getParameter("classOrderCode");
          archivesClassPO.setClassId(new Long(classId));
          String classParentId = httpServletRequest.getParameter("selectArchivesClass");
          String taxis = httpServletRequest.getParameter("taxis");
          String state = archivesClassActionForm.getState();
          String where = "archivesClass.classId <> " + classId + 
            " and archivesClass.className ='" + 
            archivesClassPO.getClassName() + "'";
          if ("0".equals(classParentId)) {
            archivesClassPO.setClassLevel(new Integer(0));
            archivesClassPO.setClassParentId(new Long(0L));
          } else {
            ArchivesClassVO archivesClassVO = archivesBD
              .selectArchivesClass(new Long(
                  classParentId));
            if (taxis != null && "1".equals(taxis)) {
              archivesClassPO.setClassParentId(archivesClassVO
                  .getClassParentId());
              archivesClassPO.setClassLevel(archivesClassVO.getClassLevel());
              archivesClassPO.setClassOrderCode(archivesClassVO
                  .getClassOrderCode());
              where = String.valueOf(where) + " and archivesClass.classParentId=" + 
                archivesClassPO.getClassParentId();
            } else {
              archivesClassPO.setClassParentId(new Long(classParentId));
              archivesClassPO.setClassLevel(new Integer(Integer.parseInt(
                      archivesClassVO.getClassLevel().toString()) + 1));
              where = String.valueOf(where) + " and archivesClass.classParentId=" + archivesClassPO.getClassParentId();
            } 
            archivesClassPO.setClassIdString(archivesClassVO.getClassIdString());
          } 
          archivesClassPO.setClassReadName(archivesClassActionForm.getClassReadName());
          String userIds = conversionString.getUserString();
          String orgIds = conversionString.getOrgString();
          String groupIds = conversionString.getGroupString();
          archivesClassPO.setclassReader(userIds);
          archivesClassPO.setClassReadOrg(orgIds);
          archivesClassPO.setClassReadGroup(groupIds);
          archivesClassPO.setDomainId(session.getAttribute("domainId").toString());
          archivesBD.selectArchivesClass(userId, orgId, archivesClassWhere);
          List list = archivesBD.selectArchivesClass(userId, orgId, 
              archivesClassWhere);
          httpServletRequest.setAttribute("ArchivesClassList", list);
          String from = "com.js.oa.archives.po.ArchivesClassPO archivesClass";
          httpServletRequest.setAttribute("classId", classId);
          boolean isRepeatName = archivesBD.isRepeatName(from, where);
          if (isRepeatName) {
            httpServletRequest.setAttribute("classParentId", classParentId);
            archivesClassActionForm.setSaveType("isRepeatName");
            return actionMapping.findForward("gotoArchivesClassUpdateView");
          } 
          boolean result = archivesBD.modiArchivesClass(archivesClassPO, 
              taxis, state, classOrderCode);
          if (!result)
            return actionMapping.findForward("failure"); 
          if ("updateAndContinue".equals(saveType)) {
            archivesClassActionForm.reset(actionMapping, httpServletRequest);
            archivesClassActionForm.setSaveType("updateAndContinue");
            return actionMapping.findForward("gotoArchivesClassUpdateView");
          } 
          if ("updateAndExit".equals(saveType)) {
            archivesClassActionForm.reset(actionMapping, httpServletRequest);
            archivesClassActionForm.setSaveType("updateAndExit");
            return actionMapping.findForward("gotoArchivesClassUpdateView");
          } 
        } 
      } 
    } 
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
}
