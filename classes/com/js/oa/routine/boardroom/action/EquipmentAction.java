package com.js.oa.routine.boardroom.action;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.routine.boardroom.bean.EquipmentEJBBean;
import com.js.oa.routine.boardroom.po.EquipmentApplyPO;
import com.js.oa.routine.boardroom.po.EquipmentPO;
import com.js.oa.routine.boardroom.po.EquipmentTypePO;
import com.js.oa.routine.boardroom.service.BoardRoomBD;
import com.js.oa.routine.boardroom.service.EquipmentBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EquipmentAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    EquipmentActionForm equipmentActionForm = 
      (EquipmentActionForm)actionForm;
    EquipmentBD equipmentBD = new EquipmentBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String corpId = session.getAttribute("corpId").toString();
    Long userID = new Long(session.getAttribute("userId").toString());
    String userName = session.getAttribute("userName").toString();
    Long orgId = new Long(session.getAttribute("orgId").toString());
    if (!hasAddRight(httpServletRequest))
      httpServletRequest.setAttribute("noAddRight", "1"); 
    if (!hasModiRight(httpServletRequest))
      httpServletRequest.setAttribute("noModiRight", "1"); 
    if ("equipmentTypeView".equals(action)) {
      equipmentTypeList(httpServletRequest);
      return actionMapping.findForward("equipmentTypeList");
    } 
    if ("exportEquipmentMessage".equals(action)) {
      try {
        exportExcelEuipment(httpServletRequest);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("exportEquipmentMessage");
    } 
    if ("addEquipmentTypeView".equals(action))
      return actionMapping.findForward("equipmentTypeAdd"); 
    if ("addEquipmentType".equals(action)) {
      EquipmentTypePO equipmentTypePO = 
        (EquipmentTypePO)FillBean.transformOneToOne(
          equipmentActionForm, EquipmentTypePO.class);
      equipmentTypePO.setCreatedEmp(userID);
      equipmentTypePO.setCreatedOrg(orgId);
      equipmentTypePO.setDomainId(domainId);
      equipmentTypePO.setCorpId(session.getAttribute("corpId").toString());
      String from = 
        "com.js.oa.routine.boardroom.po.EquipmentTypePO equipmentTypePO";
      String where = "equipmentTypePO.name ='" + equipmentTypePO.getName() + 
        "' and equipmentTypePO.domainId = " + domainId;
      if (SystemCommon.getMultiDepart() == 1)
        where = String.valueOf(where) + " and equipmentTypePO.corpId=" + session.getAttribute("corpId"); 
      boolean isRepeatName = equipmentBD.isRepeatName(from, where);
      if (isRepeatName) {
        equipmentActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("equipmentTypeAdd");
      } 
      boolean result = equipmentBD.addEquipmentType(equipmentTypePO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        equipmentActionForm.reset(actionMapping, httpServletRequest);
        equipmentActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("equipmentTypeAdd");
      } 
      if ("saveAndExit".equals(saveType)) {
        equipmentActionForm.reset(actionMapping, httpServletRequest);
        equipmentActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("equipmentTypeAdd");
      } 
    } 
    if ("deleteEquipmentType".equals(action)) {
      boolean result = true;
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (httpServletRequest.getParameter(
          "equipmentSortId") != null) {
        Long equipmentSortId = Long.valueOf(httpServletRequest
            .getParameter(
              "equipmentSortId"));
        result = equipmentBD.deleteEquipmentType(equipmentSortId);
      } 
      if (!result)
        httpServletRequest.setAttribute("delsuccess", "0"); 
      if (offsetCopy != 0) {
        equipmentTypeList(httpServletRequest, offsetCopy);
      } else {
        equipmentTypeList(httpServletRequest);
      } 
      equipmentActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("equipmentTypeList");
    } 
    if ("deleteBatchEquipmentType".equals(action)) {
      String equipmentSortIds = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = equipmentBD.deleteBatchEquipmentType(
          equipmentSortIds);
      if (!result)
        httpServletRequest.setAttribute("delsuccess", "0"); 
      if (offsetCopy != 0) {
        equipmentTypeList(httpServletRequest, offsetCopy);
      } else {
        equipmentTypeList(httpServletRequest);
      } 
      equipmentActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("equipmentTypeList");
    } 
    if ("selectEquipmentTypeView".equals(action)) {
      Long equipmentSortId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentSortId"));
      EquipmentTypePO equipmentTypePO = equipmentBD.selectEquipmentType(
          equipmentSortId);
      equipmentActionForm.setName(equipmentTypePO.getName());
      httpServletRequest.setAttribute("equipmentSortId", equipmentSortId);
      return actionMapping.findForward("equipmentTypeModi");
    } 
    if ("modiEquipmentType".equals(action)) {
      Long equipmentSortId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentSortId"));
      EquipmentTypePO equipmentTypePO = 
        (EquipmentTypePO)FillBean.transformOneToOne(
          equipmentActionForm, EquipmentTypePO.class);
      equipmentTypePO.setEquipmentSortId(equipmentSortId);
      String from = 
        "com.js.oa.routine.boardroom.po.EquipmentTypePO equipmentTypePO";
      String where = "equipmentTypePO.equipmentSortId <> " + 
        equipmentSortId + " and equipmentTypePO.name ='" + 
        equipmentTypePO.getName() + "'";
      if (SystemCommon.getMultiDepart() == 1)
        where = String.valueOf(where) + " and equipmentTypePO.corpId=" + session.getAttribute("corpId"); 
      boolean isRepeatName = equipmentBD.isRepeatName(from, where);
      if (isRepeatName) {
        equipmentActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("equipmentTypeModi");
      } 
      boolean result = equipmentBD.modiEquipmentType(equipmentTypePO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        equipmentActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("equipmentSortId", 
            equipmentSortId);
        return actionMapping.findForward("equipmentTypeModi");
      } 
    } 
    if ("equipmentView".equals(action)) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      String sdate = httpServletRequest.getParameter("searchStartDate");
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
      httpServletRequest.setAttribute("prevDay", 
          String.valueOf(y) + "/" + m + "/" + d);
      Calendar next = Calendar.getInstance();
      next.setTime(strToDate(sdate));
      next.add(5, 1);
      y = String.valueOf(next.get(1));
      m = String.valueOf(next.get(2) + 1);
      d = String.valueOf(next.get(5));
      httpServletRequest.setAttribute("nextDay", 
          String.valueOf(y) + "/" + m + "/" + d);
      SimpleDateFormat sf0 = new SimpleDateFormat("yyyy-M-d");
      httpServletRequest.setAttribute("currentDay", 
          sf0.format(new Date())
          .toString().replaceAll("-", "/"));
      equipmentList(httpServletRequest);
      String url = "";
      Enumeration<String> pNames = httpServletRequest.getParameterNames();
      while (pNames.hasMoreElements()) {
        String name = pNames.nextElement();
        if (!name.equals("pager.offset") && !name.equals("action")) {
          String value = (httpServletRequest.getParameter(name) == null) ? "" : httpServletRequest.getParameter(name);
          url = String.valueOf(url) + "&" + name + "=" + value;
        } 
      } 
      httpServletRequest.setAttribute("paraUrl", url.replace("&1=1", ""));
      if (httpServletRequest.getParameter("init") != null && 
        "0".equals(httpServletRequest.getParameter("init")))
        return actionMapping.findForward("init"); 
      return actionMapping.findForward("equipmentList");
    } 
    if ("addEquipmentView".equals(action)) {
      List equipmentTypeList = equipmentBD.selectEquipmentType(corpId);
      httpServletRequest.setAttribute("equipmentTypeList", equipmentTypeList);
      return actionMapping.findForward("equipmentAdd");
    } 
    if ("addEquipment".equals(action)) {
      List equipmentTypeList = null;
      EquipmentPO equipmentPO = (EquipmentPO)FillBean.transformOneToOne(
          equipmentActionForm, EquipmentPO.class);
      Long equipmentSortId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentType"));
      equipmentPO.setStatus(new Integer(0));
      equipmentPO.setDomainId(domainId);
      equipmentPO.setCreatedEmp(userID);
      equipmentPO.setCreatedOrg(orgId);
      equipmentPO.setCorpId(corpId);
      if (SystemCommon.getMultiDepart() == 1) {
        equipmentTypeList = equipmentBD.selectEquipmentType(corpId);
      } else {
        equipmentTypeList = equipmentBD.selectEquipmentType(domainId);
      } 
      httpServletRequest.setAttribute("equipmentTypeList", 
          equipmentTypeList);
      boolean result = equipmentBD.addEquipment(equipmentPO, 
          equipmentSortId);
      if (!result) {
        equipmentActionForm.setSaveType("isRepeatName");
        httpServletRequest.setAttribute("equipmentSortId", equipmentSortId);
        return actionMapping.findForward("equipmentAdd");
      } 
      if ("saveAndContinue".equals(saveType)) {
        equipmentActionForm.reset(actionMapping, httpServletRequest);
        equipmentActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("equipmentAdd");
      } 
      if ("saveAndExit".equals(saveType)) {
        equipmentActionForm.reset(actionMapping, httpServletRequest);
        equipmentActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("equipmentAdd");
      } 
    } 
    if ("deleteEquipment".equals(action)) {
      boolean result = true;
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (httpServletRequest.getParameter(
          "equipmentId") != null) {
        Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
              "equipmentId"));
        result = equipmentBD.deleteEquipment(equipmentId);
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      equipmentList(httpServletRequest);
      return actionMapping.findForward("equipmentList");
    } 
    if ("deleteBatchEquipment".equals(action)) {
      List equipmentTypeList = null;
      boolean result = true;
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids"))) {
        String equipmentIds = httpServletRequest.getParameter("ids");
        result = equipmentBD.deleteBatchEquipment(equipmentIds);
      } 
      if (SystemCommon.getMultiDepart() == 1) {
        equipmentTypeList = equipmentBD.selectEquipmentType(corpId);
      } else {
        equipmentTypeList = equipmentBD.selectEquipmentType(domainId);
      } 
      httpServletRequest.setAttribute("equipmentTypeList", 
          equipmentTypeList);
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (!result)
        return actionMapping.findForward("failure"); 
      equipmentList(httpServletRequest);
      return actionMapping.findForward("equipmentList");
    } 
    if ("selectEquipmentView".equals(action)) {
      List equipmentTypeList = null;
      Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentId"));
      String type = httpServletRequest.getParameter("type");
      EquipmentPO equipmentPO = equipmentBD.selectEquipment(equipmentId);
      equipmentActionForm.setName(equipmentPO.getName());
      equipmentActionForm.setCode(equipmentPO.getCode());
      Long equipmentSortId = equipmentPO.getEquipmentType().getEquipmentSortId();
      equipmentActionForm.setCost(equipmentPO.getCost());
      equipmentActionForm.setManageDept(equipmentPO.getManageDept());
      equipmentActionForm.setManageDeptName(equipmentPO.getManageDeptName());
      equipmentActionForm.setRemark(equipmentPO.getRemark());
      equipmentActionForm.setStandard(equipmentPO.getStandard());
      equipmentActionForm.setStoreManID(equipmentPO.getStoreManID());
      equipmentActionForm.setStoreManName(equipmentPO.getStoreManName());
      equipmentActionForm.setStatus(equipmentPO.getStatus());
      if (SystemCommon.getMultiDepart() == 1) {
        equipmentTypeList = equipmentBD.selectEquipmentType(corpId);
      } else {
        equipmentTypeList = equipmentBD.selectEquipmentType(domainId);
      } 
      httpServletRequest.setAttribute("equipmentTypeList", 
          equipmentTypeList);
      httpServletRequest.setAttribute("equipmentSortId", equipmentSortId);
      httpServletRequest.setAttribute("equipmentId", equipmentId);
      if ("view".equals(type))
        return actionMapping.findForward("equipmentView"); 
      if ("modi".equals(type))
        return actionMapping.findForward("equipmentModi"); 
      if ("detail".equals(type))
        return actionMapping.findForward("equipmentDetail"); 
    } 
    if ("modiEquipment".equals(action)) {
      List equipmentTypeList = null;
      Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentId"));
      EquipmentPO equipmentPO = (EquipmentPO)FillBean.transformOneToOne(
          equipmentActionForm, EquipmentPO.class);
      equipmentPO.setEquipmentId(equipmentId);
      Long equipmentSortId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentType"));
      equipmentPO.setStatus(equipmentPO.getStatus());
      equipmentPO.setDomainId(domainId);
      if (SystemCommon.getMultiDepart() == 1) {
        equipmentTypeList = equipmentBD.selectEquipmentType(corpId);
      } else {
        equipmentTypeList = equipmentBD.selectEquipmentType(domainId);
      } 
      httpServletRequest.setAttribute("equipmentTypeList", 
          equipmentTypeList);
      boolean result = equipmentBD.modiEquipment(equipmentPO, 
          equipmentSortId);
      if (!result) {
        String type = httpServletRequest.getParameter("type");
        equipmentPO = equipmentBD.selectEquipment(equipmentId);
        equipmentActionForm.setName(equipmentPO.getName());
        equipmentActionForm.setCode(equipmentPO.getCode());
        equipmentSortId = equipmentPO.getEquipmentType()
          .getEquipmentSortId();
        equipmentActionForm.setCost(equipmentPO.getCost());
        equipmentActionForm.setManageDept(equipmentPO.getManageDept());
        equipmentActionForm.setManageDeptName(equipmentPO.getManageDeptName());
        equipmentActionForm.setRemark(equipmentPO.getRemark());
        equipmentActionForm.setStandard(equipmentPO.getStandard());
        equipmentActionForm.setStoreManID(equipmentPO.getStoreManID());
        equipmentActionForm.setStoreManName(equipmentPO.getStoreManName());
        equipmentActionForm.setStatus(equipmentPO.getStatus());
        httpServletRequest.setAttribute("equipmentSortId", equipmentSortId);
        httpServletRequest.setAttribute("equipmentId", equipmentId);
        equipmentActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("equipmentModi");
      } 
      if ("updateAndExit".equals(saveType)) {
        equipmentActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("equipmentId", equipmentId);
        return actionMapping.findForward("equipmentModi");
      } 
    } 
    if ("equipmentUseView".equals(action)) {
      equipmentUseList(httpServletRequest);
      return actionMapping.findForward("equipmentUseList");
    } 
    if ("equipmentUseViewExcel".equals(action)) {
      try {
        equipmentUseViewExcel(httpServletRequest);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("equipmentUseViewExcel");
    } 
    if ("equipmentMYUseView".equals(action)) {
      equipmentMYUseList(httpServletRequest);
      return actionMapping.findForward("equipmentMYUseList");
    } 
    if ("addEquipmentApplyView".equals(action)) {
      boolean flag = false;
      EquipmentPO equipmentPO = new EquipmentPO();
      if (httpServletRequest.getParameter("equipmentId") != null && 
        !"".equals(httpServletRequest.getParameter("equipmentId")) && 
        !"null".equals(httpServletRequest.getParameter("equipmentId"))) {
        Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
              "equipmentId"));
        equipmentPO = equipmentBD.selectEquipment(equipmentId);
        httpServletRequest.setAttribute("equipmentId", equipmentId);
        flag = true;
      } else {
        Page page = new Page("po.equipmentId,po.code,po.name", 
            "com.js.oa.routine.boardroom.po.EquipmentPO po", 
            "where po.domainId=" + domainId);
        List list = page.getResultList();
        httpServletRequest.setAttribute("equipmentList", list);
      } 
      if (httpServletRequest.getParameter("processId") == null) {
        ProcessBD procbd = new ProcessBD();
        List<Object[]> equipList = new ArrayList();
        Object tmpl = procbd.getUserProcess(httpServletRequest
            .getSession()
            .getAttribute("userId").toString(), 
            httpServletRequest.getSession()
            .getAttribute("orgIdString")
            .toString(), "14");
        if (tmpl != null)
          equipList = (List)tmpl; 
        if (equipList != null && equipList.size() == 1) {
          Object[] rfObj = equipList.get(0);
          Object object1 = rfObj[2];
          Object object2 = rfObj[3];
          Object object3 = rfObj[5];
          Object object4 = rfObj[4];
          Object object5 = (rfObj[6] == null) ? "" : rfObj[6];
          String linkValue = "processType=" + object3 + 
            "&remindField=" + object5 + 
            "&processId=" + object1 + "&tableId=" + 
            object4 + "&processName=" + object2;
          if (flag) {
            equipmentActionForm.setName(equipmentPO.getName());
            equipmentActionForm.setCode(equipmentPO.getCode());
          } 
          return new ActionForward(
              "/EquipmentAction.do?action=addEquipmentApplyView" + (flag ? ("&equipmentId=" + 
              httpServletRequest.getParameter("equipmentId")) : "") + 
              "&" + 
              linkValue + "&moduleId=14");
        } 
        if (equipList != null && equipList.size() > 1) {
          httpServletRequest.setAttribute("voitureFlowlist", 
              equipList);
          return actionMapping.findForward("selectWorkFlow");
        } 
        if (flag) {
          equipmentActionForm.setName(equipmentPO.getName());
          equipmentActionForm.setCode(equipmentPO.getCode());
        } 
        httpServletRequest.setAttribute("noWorkFlow", "1");
        return actionMapping.findForward("equipmentApplyAdd");
      } 
      equipmentActionForm.setName(equipmentPO.getName());
      equipmentActionForm.setCode(equipmentPO.getCode());
      equipmentActionForm.setStatus(equipmentPO.getStatus());
      return actionMapping.findForward("equipmentApplyAdd");
    } 
    if ("addEquipmentApply".equals(action)) {
      EquipmentApplyPO equipmentApplyPO = 
        (EquipmentApplyPO)FillBean.transformOneToOne(
          equipmentActionForm, EquipmentApplyPO.class);
      Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentId"));
      equipmentApplyPO.setEmpId(Long.valueOf(equipmentActionForm
            .getBorrower()));
      equipmentApplyPO.setEmpName(equipmentActionForm.getBorrowerName());
      equipmentApplyPO.setOrgId(Long.valueOf(equipmentActionForm
            .getBorrowerOrg()));
      equipmentApplyPO.setOrgName(equipmentActionForm.getBorrowerOrgName());
      equipmentApplyPO.setStartDate(new Date(httpServletRequest
            .getParameter("startDate")));
      equipmentApplyPO.setStartTime(httpServletRequest.getParameter(
            "startTime"));
      equipmentApplyPO.setEndDate(new Date(httpServletRequest
            .getParameter("endDate")));
      equipmentApplyPO.setEndTime(httpServletRequest.getParameter(
            "endTime"));
      equipmentApplyPO.setStatus(new Integer(1));
      equipmentApplyPO.setDomainId(domainId);
      EquipmentPO equipmentPO = equipmentBD.selectEquipment(equipmentId);
      equipmentActionForm.setName(equipmentPO.getName());
      equipmentActionForm.setCode(equipmentPO.getCode());
      httpServletRequest.setAttribute("equipmentId", equipmentId);
      Long result = equipmentBD.addEquipmentApply(equipmentApplyPO, 
          equipmentId);
      saveProcess(httpServletRequest, result.toString());
      if (result.intValue() == -1)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        equipmentActionForm.reset(actionMapping, httpServletRequest);
        equipmentActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("equipmentApplyAdd");
      } 
      if ("saveAndExit".equals(saveType)) {
        equipmentActionForm.reset(actionMapping, httpServletRequest);
        equipmentActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("equipmentApplyAdd");
      } 
    } 
    if ("selectEquipmentApplyView".equals(action)) {
      Long equipmentApplyId = Long.valueOf(httpServletRequest
          .getParameter("record"));
      String type = httpServletRequest.getParameter("type");
      EquipmentApplyPO equipmentApplyPO = equipmentBD
        .selectEquipmentApply(
          equipmentApplyId);
      equipmentActionForm.setPurpose(equipmentApplyPO.getPurpose());
      equipmentActionForm.setBorrower(equipmentApplyPO.getEmpId()
          .toString());
      equipmentActionForm.setBorrowerName(equipmentApplyPO.getEmpName());
      equipmentActionForm.setBorrowerOrg(equipmentApplyPO.getOrgId()
          .toString());
      equipmentActionForm.setBorrowerOrgName(equipmentApplyPO.getOrgName());
      httpServletRequest.setAttribute("startDate", 
          equipmentApplyPO.getStartDate());
      httpServletRequest.setAttribute("startTime", 
          equipmentApplyPO.getStartTime());
      httpServletRequest.setAttribute("endDate", 
          equipmentApplyPO.getEndDate());
      httpServletRequest.setAttribute("endTime", 
          equipmentApplyPO.getEndTime());
      EquipmentPO equipmentPO = equipmentBD.selectEquipment(
          equipmentApplyPO.getEquipment().getEquipmentId());
      equipmentActionForm.setName(equipmentPO.getName());
      equipmentActionForm.setCode(equipmentPO.getCode());
      httpServletRequest.setAttribute("equipmentApplyId", 
          equipmentApplyId);
      httpServletRequest.setAttribute("equipmentId", 
          equipmentApplyPO.getEquipment()
          .getEquipmentId());
      if ("view".equals(type))
        return actionMapping.findForward("equipmentApplyView"); 
      if ("modi".equals(type)) {
        httpServletRequest.setAttribute("curWriteFields", (
            new WorkflowCommon())
            .getCurActivityWriteField(
              httpServletRequest));
        httpServletRequest.setAttribute("myform", equipmentActionForm);
        return actionMapping.findForward("equipmentApplyModi");
      } 
    } 
    if ("modiEquipmentApply".equals(action)) {
      Long equipmentApplyId = Long.valueOf(httpServletRequest
          .getParameter(
            "equipmentApplyId"));
      Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
            "equipmentId"));
      EquipmentApplyPO equipmentApplyPO = 
        (EquipmentApplyPO)FillBean.transformOneToOne(
          equipmentActionForm, EquipmentApplyPO.class);
      equipmentApplyPO.setEquipmentApplyId(equipmentApplyId);
      equipmentApplyPO.setStartDate(new Date(httpServletRequest
            .getParameter("startDate")));
      equipmentApplyPO.setStartTime(httpServletRequest.getParameter(
            "startTime"));
      equipmentApplyPO.setEndDate(new Date(httpServletRequest
            .getParameter("endDate")));
      equipmentApplyPO.setEndTime(httpServletRequest.getParameter(
            "endTime"));
      equipmentApplyPO.setStatus(new Integer(0));
      EquipmentPO equipmentPO = equipmentBD.selectEquipment(equipmentId);
      equipmentActionForm.setName(equipmentPO.getName());
      equipmentActionForm.setCode(equipmentPO.getCode());
      boolean result = equipmentBD.modiEquipmentApply(equipmentApplyPO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        equipmentActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("equipmentApplyId", 
            equipmentApplyId);
        httpServletRequest.setAttribute("equipmentId", equipmentId);
        return actionMapping.findForward("equipmentApplyModi");
      } 
    } 
    if ("deleteEquipmentApply".equals(action)) {
      Long equipmentApplyId = Long.valueOf(httpServletRequest
          .getParameter(
            "equipmentApplyId"));
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = equipmentBD.deleteEquipmentApply(equipmentApplyId);
      if (!result)
        return actionMapping.findForward("failure"); 
      String flag = httpServletRequest.getParameter("flag");
      if ("myEquipment".equals(flag)) {
        equipmentMYUseList(httpServletRequest);
        return actionMapping.findForward("equipmentMYUseList");
      } 
      if (offsetCopy != 0) {
        equipmentUseList(httpServletRequest, offsetCopy);
      } else {
        equipmentUseList(httpServletRequest);
      } 
      return actionMapping.findForward("equipmentUseList");
    } 
    if ("restituteEquipmentApply".equals(action)) {
      boolean result = true;
      if (httpServletRequest.getParameter("equipmentApplyId") != null && 
        !"".equals(httpServletRequest.getParameter("equipmentApplyId"))) {
        Long equipmentApplyId = Long.valueOf(httpServletRequest
            .getParameter(
              "equipmentApplyId"));
        result = equipmentBD.restituteEquipmentApply(
            equipmentApplyId);
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      String flag = httpServletRequest.getParameter("flag");
      if ("myEquipment".equals(flag)) {
        equipmentMYUseList(httpServletRequest);
        return actionMapping.findForward("equipmentMYUseList");
      } 
      equipmentUseList(httpServletRequest);
      return actionMapping.findForward("equipmentUseList");
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
    if ("reSub".equals(action)) {
      reSub(httpServletRequest, equipmentActionForm);
      return actionMapping.findForward("next");
    } 
    if ("trans".equals(action)) {
      trans(httpServletRequest, equipmentActionForm);
      equipmentActionForm.setSaveType("updateAndExit");
      httpServletRequest.setAttribute("equipmentApplyId", 
          httpServletRequest.getParameter(
            "equipmentApplyId"));
      httpServletRequest.setAttribute("equipmentId", 
          httpServletRequest
          .getParameter("equipmentId"));
      return actionMapping.findForward("equipmentApplyModi");
    } 
    if ("untread".equals(action)) {
      untread(httpServletRequest);
      equipmentActionForm.setSaveType("updateAndExit");
      httpServletRequest.setAttribute("equipmentApplyId", 
          httpServletRequest.getParameter(
            "equipmentApplyId"));
      httpServletRequest.setAttribute("equipmentId", 
          httpServletRequest
          .getParameter("equipmentId"));
      return actionMapping.findForward("equipmentApplyModi");
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
      equipmentBD.deleteEquipmentApply(new Long(recordId));
      return actionMapping.findForward("cancel");
    } 
    if ("statuDetail".equals(action)) {
      equipmentInfo(httpServletRequest, equipmentActionForm);
      return actionMapping.findForward("statusDetail");
    } 
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
  
  private boolean hasAddRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).hasRightTypeName(
        httpSession.getAttribute("userId").toString(), "设备管理", "维护");
  }
  
  private boolean hasModiRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).hasRightTypeName(
        httpSession.getAttribute("userId").toString(), "设备管理", "维护");
  }
  
  public void equipmentTypeList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where equipmentTypePO.domainId=" + domainId;
    if (SystemCommon.getMutliBrowser() == 1)
      where = String.valueOf(where) + " and equipmentTypePO.corpId=" + session.getAttribute("corpId"); 
    where = String.valueOf(where) + " order by equipmentTypePO.equipmentSortId desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentTypePO.equipmentSortId,equipmentTypePO.name", 
        "com.js.oa.routine.boardroom.po.EquipmentTypePO equipmentTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentTypeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action");
  }
  
  public void equipmentTypeList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where equipmentTypePO.domainId=" + domainId + 
      " order by equipmentTypePO.equipmentSortId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentTypePO.equipmentSortId,equipmentTypePO.name", 
        "com.js.oa.routine.boardroom.po.EquipmentTypePO equipmentTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentTypeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action");
  }
  
  public void exportExcelEuipment(HttpServletRequest httpServletRequest) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "设备管理", "维护")) + 
      " and equipmentPO.domainId=" + domainId;
    String maintenanceBoardRoomIds = (new EquipmentBD()).maintenanceEquipment(
        scopeWhere);
    httpServletRequest.setAttribute("maintenanceEquipmentIds", 
        maintenanceBoardRoomIds);
    String where = " where equipmentPO.domainId=" + domainId;
    String searchEquipmentName = httpServletRequest.getParameter("searchEquipmentName");
    String searchEmpName = httpServletRequest.getParameter("searchEmpName");
    String equipmentType = httpServletRequest.getParameter("equipmentType");
    if (!"".equals(searchEquipmentName) && searchEquipmentName != null)
      where = String.valueOf(where) + " and equipmentPO.name like '%" + searchEquipmentName + "%'"; 
    if (!"".equals(equipmentType) && equipmentType != null)
      where = String.valueOf(where) + " and equipmentType.equipmentSortId = " + equipmentType + " "; 
    if (!"".equals(searchEmpName) && searchEmpName != null)
      where = String.valueOf(where) + " and equipmentPO.storeManName like '%" + searchEmpName + "%'"; 
    if (httpServletRequest.getParameter("orderbystyle") != null) {
      if (httpServletRequest.getParameter("orderbystyle").equals("0")) {
        where = String.valueOf(where) + " order by equipmentType.name  ";
      } else {
        where = String.valueOf(where) + " order by equipmentType.name desc  ";
      } 
    } else if (httpServletRequest.getParameter("orderbystoreman") != null) {
      if (httpServletRequest.getParameter("orderbystoreman").equals("0")) {
        where = String.valueOf(where) + " order by equipmentPO.storeManName  ";
      } else {
        where = String.valueOf(where) + " order by equipmentPO.storeManName desc  ";
      } 
    } else {
      where = String.valueOf(where) + 
        " order by equipmentPO.equipmentId desc ";
    } 
    String sqlParam = "select equipmentPO.equipmentId,equipmentPO.code,equipmentPO.name,equipmentType.name,equipmentPO.cost,equipmentPO.manageDeptName,equipmentPO.status,equipmentPO.standard,equipmentPO.storeManID,equipmentPO.storeManName ";
    String formSql = " from com.js.oa.routine.boardroom.po.EquipmentPO equipmentPO join equipmentPO.equipmentType equipmentType ";
    String sqlAll = String.valueOf(sqlParam) + formSql + where;
    EquipmentEJBBean bean = new EquipmentEJBBean();
    httpServletRequest.setAttribute("equipmentList", bean.export(sqlAll));
  }
  
  public void equipmentList(HttpServletRequest httpServletRequest) {
    List equipmentTypeList = null;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    EquipmentBD equipmentBD = new EquipmentBD();
    if (SystemCommon.getMultiDepart() == 1) {
      equipmentTypeList = equipmentBD.selectEquipmentType(session.getAttribute("corpId").toString());
    } else {
      equipmentTypeList = equipmentBD.selectEquipmentType(domainId);
    } 
    httpServletRequest.setAttribute("equipmentTypeList", 
        equipmentTypeList);
    String scopeWhere = String.valueOf(scopeWhere(httpServletRequest, "设备管理", "维护")) + 
      " and equipmentPO.domainId=" + domainId;
    String maintenanceBoardRoomIds = (new EquipmentBD()).maintenanceEquipment(
        scopeWhere);
    httpServletRequest.setAttribute("maintenanceEquipmentIds", 
        maintenanceBoardRoomIds);
    String where = " where equipmentPO.domainId=" + domainId;
    String searchEquipmentName = httpServletRequest.getParameter("searchEquipmentName");
    String searchEmpName = httpServletRequest.getParameter("searchEmpName");
    String equipmentType = httpServletRequest.getParameter("equipmentType");
    if (!"".equals(searchEquipmentName) && searchEquipmentName != null)
      where = String.valueOf(where) + " and equipmentPO.name like '%" + searchEquipmentName + "%'"; 
    if (!"".equals(equipmentType) && equipmentType != null)
      where = String.valueOf(where) + " and equipmentType.equipmentSortId = " + equipmentType + " "; 
    if (!"".equals(searchEmpName) && searchEmpName != null)
      where = String.valueOf(where) + " and equipmentPO.storeManName like '%" + searchEmpName + "%'"; 
    if (SystemCommon.getMultiDepart() == 1)
      where = String.valueOf(where) + " and equipmentPO.corpId=" + session.getAttribute("corpId"); 
    if (httpServletRequest.getParameter("orderbystyle") != null) {
      if (httpServletRequest.getParameter("orderbystyle").equals("0")) {
        where = String.valueOf(where) + " order by equipmentType.name  ";
      } else {
        where = String.valueOf(where) + " order by equipmentType.name desc  ";
      } 
    } else if (httpServletRequest.getParameter("orderbystoreman") != null) {
      if (httpServletRequest.getParameter("orderbystoreman").equals("0")) {
        where = String.valueOf(where) + " order by equipmentPO.storeManName  ";
      } else {
        where = String.valueOf(where) + " order by equipmentPO.storeManName desc  ";
      } 
    } else {
      where = String.valueOf(where) + 
        " order by equipmentPO.equipmentId desc ";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentPO.equipmentId,equipmentPO.code,equipmentPO.name,equipmentType.name,equipmentPO.cost,equipmentPO.manageDeptName,equipmentPO.status,equipmentPO.standard,equipmentPO.storeManID,equipmentPO.storeManName", 
        "com.js.oa.routine.boardroom.po.EquipmentPO equipmentPO join equipmentPO.equipmentType equipmentType", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,init,orderbystyle,orderbystoreman,searchStartDate,searchEquipmentName,searchEmpName,equipmentType");
  }
  
  public void equipmentList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where equipmentPO.domainId=" + domainId + 
      " order by equipmentPO.equipmentId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentPO.equipmentId,equipmentPO.code,equipmentPO.name,equipmentType.name,equipmentPO.cost,equipmentPO.manageDeptName,equipmentPO.status", 
        "com.js.oa.routine.boardroom.po.EquipmentPO equipmentPO join equipmentPO.equipmentType equipmentType", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action");
    ProcessBD procbd = new ProcessBD();
    List equipList = new ArrayList();
    Object tmpl = procbd.getUserProcess(httpServletRequest.getSession()
        .getAttribute("userId").toString(), 
        httpServletRequest.getSession()
        .getAttribute("orgIdString")
        .toString(), "14");
    if (tmpl != null)
      equipList = (List)tmpl; 
    httpServletRequest.setAttribute("voitureFlowlist", equipList);
  }
  
  public void equipmentUseList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    Long userID = new Long(session.getAttribute("userId").toString());
    String searchDatecheckbox = httpServletRequest.getParameter(
        "searchDatecheckbox");
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    String where = " where equipmentApplyPO.domainId=" + domainId;
    if (httpServletRequest.getParameter("searchEquipmentName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEquipmentName")))
      where = String.valueOf(where) + " and equipment.name like '%" + 
        httpServletRequest.getParameter("searchEquipmentName") + 
        "%'"; 
    if (httpServletRequest.getParameter("searchEmpName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEmpName")))
      where = String.valueOf(where) + " and equipmentApplyPO.empName like '%" + 
        httpServletRequest.getParameter("searchEmpName") + 
        "%'"; 
    if (httpServletRequest.getParameter("searchOrgName") != null && 
      !"".equals(httpServletRequest.getParameter("searchOrgName")))
      where = String.valueOf(where) + " and equipmentApplyPO.orgName like '%" + 
        httpServletRequest.getParameter("searchOrgName") + 
        "%'"; 
    if ("1".equals(searchDatecheckbox)) {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L')";
      } 
      httpServletRequest.setAttribute("searchStartDate", 
          httpServletRequest.getParameter(
            "searchStartDate"));
      httpServletRequest.setAttribute("searchEndDate", 
          httpServletRequest.getParameter(
            "searchEndDate"));
    } 
    where = String.valueOf(where) + " and ( " + scopeWhere2(httpServletRequest, "设备管理", "维护") + 
      " ) ";
    where = String.valueOf(where) + " order by equipmentApplyPO.equipmentApplyId desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentApplyPO.equipmentApplyId,equipment.code,equipment.name,equipmentApplyPO.empName,equipmentApplyPO.orgName,equipmentApplyPO.startDate,equipmentApplyPO.startTime,equipmentApplyPO.endDate,equipmentApplyPO.endTime,equipmentApplyPO.status", 
        "com.js.oa.routine.boardroom.po.EquipmentApplyPO equipmentApplyPO join equipmentApplyPO.equipment equipment", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentUseList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchEquipmentName,searchEmpName,searchOrgName,searchStartDate,searchEndDate,searchDatecheckbox");
  }
  
  public void equipmentUseViewExcel(HttpServletRequest httpServletRequest) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    Long userID = new Long(session.getAttribute("userId").toString());
    String searchDatecheckbox = httpServletRequest.getParameter(
        "searchDatecheckbox");
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    String where = " where equipmentApplyPO.domainId=" + domainId;
    if (httpServletRequest.getParameter("searchEquipmentName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEquipmentName")))
      where = String.valueOf(where) + " and equipment.name like '%" + 
        httpServletRequest.getParameter("searchEquipmentName") + 
        "%'"; 
    if (httpServletRequest.getParameter("searchEmpName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEmpName")))
      where = String.valueOf(where) + " and equipmentApplyPO.empName like '%" + 
        httpServletRequest.getParameter("searchEmpName") + 
        "%'"; 
    if (httpServletRequest.getParameter("searchOrgName") != null && 
      !"".equals(httpServletRequest.getParameter("searchOrgName")))
      where = String.valueOf(where) + " and equipmentApplyPO.orgName like '%" + 
        httpServletRequest.getParameter("searchOrgName") + 
        "%'"; 
    if ("1".equals(searchDatecheckbox)) {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L')";
      } 
      httpServletRequest.setAttribute("searchStartDate", 
          httpServletRequest.getParameter(
            "searchStartDate"));
      httpServletRequest.setAttribute("searchEndDate", 
          httpServletRequest.getParameter(
            "searchEndDate"));
    } 
    where = String.valueOf(where) + " and ( " + scopeWhere2(httpServletRequest, "设备管理", "维护") + 
      " ) ";
    where = String.valueOf(where) + " order by equipmentApplyPO.equipmentApplyId desc";
    String sqlParam = "select equipmentApplyPO.equipmentApplyId,equipment.code,equipment.name,equipmentApplyPO.empName,equipmentApplyPO.orgName,equipmentApplyPO.startDate,equipmentApplyPO.startTime,equipmentApplyPO.endDate,equipmentApplyPO.endTime,equipmentApplyPO.status ";
    String formSql = " from com.js.oa.routine.boardroom.po.EquipmentApplyPO equipmentApplyPO join equipmentApplyPO.equipment equipment ";
    String sqlAll = String.valueOf(sqlParam) + formSql + where;
    EquipmentEJBBean bean = new EquipmentEJBBean();
    httpServletRequest.setAttribute("equipmentUseList", bean.export(sqlAll));
  }
  
  public void equipmentMYUseList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    Long userID = new Long(session.getAttribute("userId").toString());
    String searchDatecheckbox = httpServletRequest.getParameter(
        "searchDatecheckbox");
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    String where = " where equipmentApplyPO.domainId=" + domainId;
    where = String.valueOf(where) + " and equipmentApplyPO.empId = " + userID;
    if (httpServletRequest.getParameter("searchEquipmentName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEquipmentName")))
      where = String.valueOf(where) + " and equipment.name like '%" + 
        httpServletRequest.getParameter("searchEquipmentName") + 
        "%'"; 
    if ("1".equals(searchDatecheckbox)) {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L')";
      } 
      httpServletRequest.setAttribute("searchStartDate", 
          httpServletRequest.getParameter(
            "searchStartDate"));
      httpServletRequest.setAttribute("searchEndDate", 
          httpServletRequest.getParameter(
            "searchEndDate"));
    } 
    where = String.valueOf(where) + " order by equipmentApplyPO.equipmentApplyId desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentApplyPO.equipmentApplyId,equipment.code,equipment.name,equipmentApplyPO.empName,equipmentApplyPO.orgName,equipmentApplyPO.startDate,equipmentApplyPO.startTime,equipmentApplyPO.endDate,equipmentApplyPO.endTime,equipmentApplyPO.status", 
        "com.js.oa.routine.boardroom.po.EquipmentApplyPO equipmentApplyPO join equipmentApplyPO.equipment equipment", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentUseList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchEquipmentName,searchEmpName,searchOrgName,searchStartDate,searchEndDate,searchDatecheckbox");
  }
  
  public void equipmentUseList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String searchDatecheckbox = httpServletRequest.getParameter(
        "searchDatecheckbox");
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    String where = " where equipmentApplyPO.domainId=" + domainId;
    if (httpServletRequest.getParameter("searchEquipmentName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEquipmentName")))
      where = String.valueOf(where) + " and equipment.name like '%" + 
        httpServletRequest.getParameter("searchEquipmentName") + 
        "%'"; 
    if (httpServletRequest.getParameter("searchEmpName") != null && 
      !"".equals(httpServletRequest.getParameter("searchEmpName")))
      where = String.valueOf(where) + " and equipmentApplyPO.empName like '%" + 
        httpServletRequest.getParameter("searchEmpName") + 
        "%'"; 
    if (httpServletRequest.getParameter("searchOrgName") != null && 
      !"".equals(httpServletRequest.getParameter("searchOrgName")))
      where = String.valueOf(where) + " and equipmentApplyPO.orgName like '%" + 
        httpServletRequest.getParameter("searchOrgName") + 
        "%'"; 
    if ("1".equals(searchDatecheckbox)) {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and equipmentApplyPO.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L')";
      } 
    } 
    where = String.valueOf(where) + " order by equipmentApplyPO.equipmentApplyId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "equipmentApplyPO.equipmentApplyId,equipment.code,equipment.name,equipmentApplyPO.empName,equipmentApplyPO.orgName,equipmentApplyPO.startDate,equipmentApplyPO.startTime,equipmentApplyPO.endDate,equipmentApplyPO.endTime,equipment.status", 
        "com.js.oa.routine.boardroom.po.EquipmentApplyPO equipmentApplyPO join equipmentApplyPO.equipment equipment", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("equipmentUseList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchEquipmentName,searchEmpName,searchOrgName,searchStartDate,searchEndDate,searchDatecheckbox");
  }
  
  private void saveProcess(HttpServletRequest httpServletRequest, String recordId) {
    int moduleId = 10, formType = 1;
    String mainLinkFile = "/jsoa/EquipmentAction.do?action=selectEquipmentApplyView&equipmentApplyId=" + 
      recordId + "&type=modi";
    String cancelFile = "window.open('/jsoa/routine/equipment/equipmentApplyCancel.jsp?workStatus=1&workId=workIdValue&tableId=tableIdValue&processName=" + 
      httpServletRequest.getParameter("fileType") + 
      "&recordId=" + recordId + "&processId=" + 
      httpServletRequest.getParameter("processId") + 
      "&remindValue=&" + 
      "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=0,height=0')";
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
    EquipmentBD equipmentBD = new EquipmentBD();
    EquipmentApplyPO equipmentApplyPO = new EquipmentApplyPO();
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentId"));
    equipmentApplyPO.setEmpId(new Long(httpServletRequest.getParameter(
            "borrower")));
    equipmentApplyPO.setEmpName(httpServletRequest.getParameter(
          "borrowerName"));
    equipmentApplyPO.setOrgId(new Long(httpServletRequest.getParameter(
            "borrowerOrg")));
    equipmentApplyPO.setOrgName(httpServletRequest.getParameter(
          "borrowerOrgName"));
    equipmentApplyPO.setEquipmentApplyId(equipmentApplyId);
    equipmentApplyPO.setStartDate(new Date(httpServletRequest.getParameter(
            "startDate")));
    equipmentApplyPO.setStartTime(httpServletRequest.getParameter(
          "startTime"));
    equipmentApplyPO.setEndDate(new Date(httpServletRequest.getParameter(
            "endDate")));
    equipmentApplyPO.setEndTime(httpServletRequest.getParameter("endTime"));
    equipmentApplyPO.setStatus(new Integer(1));
    equipmentApplyPO.setPurpose(httpServletRequest.getParameter("purpose"));
    boolean result = equipmentBD.modiEquipmentApply(equipmentApplyPO);
    String toMainFile = "/jsoa/EquipmentAction.do?action=selectEquipmentApplyView&equipmentApplyId=" + 
      httpServletRequest.getParameter("recordId") + 
      "&type=modi";
    (new ProcessSubmit()).operate(httpServletRequest, toMainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void comp(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentId"));
    EquipmentApplyPO equipmentApplyPO = equipmentBD.selectEquipmentApply(
        new Long(httpServletRequest.getParameter("equipmentApplyId")));
    equipmentApplyPO.setEmpId(new Long(httpServletRequest.getParameter(
            "borrower")));
    equipmentApplyPO.setEmpName(httpServletRequest.getParameter(
          "borrowerName"));
    equipmentApplyPO.setOrgId(new Long(httpServletRequest.getParameter(
            "borrowerOrg")));
    equipmentApplyPO.setOrgName(httpServletRequest.getParameter(
          "borrowerOrgName"));
    equipmentApplyPO.setEquipmentApplyId(equipmentApplyId);
    equipmentApplyPO.setStartDate(new Date(httpServletRequest.getParameter(
            "startDate")));
    equipmentApplyPO.setStartTime(httpServletRequest.getParameter(
          "startTime"));
    equipmentApplyPO.setEndDate(new Date(httpServletRequest.getParameter(
            "endDate")));
    equipmentApplyPO.setEndTime(httpServletRequest.getParameter("endTime"));
    equipmentApplyPO.setStatus(new Integer(2));
    equipmentApplyPO.setPurpose(httpServletRequest.getParameter("purpose"));
    boolean result = equipmentBD.modiEquipmentApply(equipmentApplyPO);
    httpServletRequest.setAttribute("close", "1");
    (new ProcessSubmit()).comp(httpServletRequest);
  }
  
  private void reSub(HttpServletRequest httpServletRequest, EquipmentActionForm equipmentActionForm) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    equipmentBD.deleteEquipmentApply(equipmentApplyId);
    EquipmentApplyPO equipmentApplyPO = 
      (EquipmentApplyPO)FillBean.transformOneToOne(
        equipmentActionForm, EquipmentApplyPO.class);
    Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentId"));
    equipmentApplyPO.setEmpId(Long.valueOf(equipmentActionForm.getBorrower()));
    equipmentApplyPO.setEmpName(equipmentActionForm.getBorrowerName());
    equipmentApplyPO.setOrgId(Long.valueOf(equipmentActionForm
          .getBorrowerOrg()));
    equipmentApplyPO.setOrgName(equipmentActionForm.getBorrowerOrgName());
    equipmentApplyPO.setStartDate(new Date(httpServletRequest.getParameter(
            "startDate")));
    equipmentApplyPO.setStartTime(httpServletRequest.getParameter(
          "startTime"));
    equipmentApplyPO.setEndDate(new Date(httpServletRequest.getParameter(
            "endDate")));
    equipmentApplyPO.setEndTime(httpServletRequest.getParameter("endTime"));
    equipmentApplyPO.setStatus(new Integer(1));
    Long result = equipmentBD.addEquipmentApply(equipmentApplyPO, 
        equipmentId);
    saveProcess(httpServletRequest, result.toString());
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void trans(HttpServletRequest httpServletRequest, EquipmentActionForm equipmentActionForm) {
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentId"));
    EquipmentApplyPO equipmentApplyPO = 
      (EquipmentApplyPO)FillBean.transformOneToOne(
        equipmentActionForm, EquipmentApplyPO.class);
    equipmentApplyPO.setEquipmentApplyId(equipmentApplyId);
    equipmentApplyPO.setStartDate(new Date(httpServletRequest.getParameter(
            "startDate")));
    equipmentApplyPO.setStartTime(httpServletRequest.getParameter(
          "startTime"));
    equipmentApplyPO.setEndDate(new Date(httpServletRequest.getParameter(
            "endDate")));
    equipmentApplyPO.setEndTime(httpServletRequest.getParameter("endTime"));
    equipmentApplyPO.setStatus(new Integer(1));
    boolean result = (new EquipmentBD()).modiEquipmentApply(equipmentApplyPO);
    String mainFile = "/jsoa/EquipmentAction.do?action=selectEquipmentApplyView&equipmentApplyId=" + 
      httpServletRequest.getParameter("recordId") + 
      "&type=modi";
    (new ProcessSubmit()).transition(httpServletRequest, mainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void untread(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    EquipmentApplyPO equipmentApplyPO = equipmentBD.selectEquipmentApply(
        new Long(httpServletRequest.getParameter("equipmentApplyId")));
    equipmentApplyPO.setStatus(new Integer(3));
    EquipmentPO equipmentPO = equipmentApplyPO.getEquipment();
    equipmentPO.setStatus(new Integer(0));
    equipmentApplyPO.setEquipment(equipmentPO);
    equipmentBD.modiEquipmentApply(equipmentApplyPO);
    (new ProcessSubmit()).Untread(httpServletRequest);
  }
  
  public void equipmentInfo(HttpServletRequest httpServletRequest, EquipmentActionForm voitureApplyActionForm) {
    BoardRoomBD vmbd = new BoardRoomBD();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String equipmentid = httpServletRequest.getParameter("equipmentid");
    httpServletRequest.setAttribute("equipmentid", equipmentid);
    List<Date> searchDate = new ArrayList();
    List<String[]> placeList = new ArrayList();
    Date searchStartDate = new Date();
    if (httpServletRequest.getParameter("searchStartDate") != null)
      searchStartDate = new Date(httpServletRequest.getParameter(
            "searchStartDate")); 
    long day = 3L;
    searchDate.add(searchStartDate);
    placeList.add(vmbd.getEquipmentInfo(equipmentid, 
          formatter.format(searchStartDate)));
    for (int i = 1; i < day; i++) {
      Date searchNextDate = new Date();
      long myTime = searchStartDate.getTime() / 1000L + (
        86400 * i);
      searchNextDate.setTime(myTime * 1000L);
      searchDate.add(searchNextDate);
      placeList.add(vmbd.getEquipmentInfo(equipmentid, 
            formatter.format(searchNextDate)));
    } 
    httpServletRequest.setAttribute("searchDate", searchDate);
    httpServletRequest.setAttribute("voiturePlace", placeList);
  }
  
  private String scopeWhere(HttpServletRequest httpServletRequest, String rightType, String rightName) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).getRightFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        rightType, 
        rightName, 
        "equipmentPO.createdOrg", 
        "equipmentPO.createdEmp");
  }
  
  private String scopeWhere2(HttpServletRequest httpServletRequest, String rightType, String rightName) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).getRightFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        rightType, 
        rightName, 
        "equipment.createdOrg", 
        "equipment.createdEmp");
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
}
