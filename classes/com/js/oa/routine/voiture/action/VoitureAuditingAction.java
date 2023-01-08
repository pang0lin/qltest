package com.js.oa.routine.voiture.action;

import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureAuditingPO;
import com.js.oa.routine.voiture.po.VoitureCancelPO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureAuditingAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
    VoitureAuditingActionForm voitureAuditingActionForm = 
      
      (VoitureAuditingActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String flag = (httpServletRequest.getParameter("flag") == null) ? "add" : 
      httpServletRequest.getParameter("flag");
    String tag = "add";
    if (flag.equals("add")) {
      tag = "add";
      String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
        "0" : 
        httpServletRequest.getParameter("applyId")
        .toString();
      if ((new VoitureManagerService()).isVoitureAuditingPO(applyId)) {
        modiAuditing(httpServletRequest, actionForm);
      } else {
        addAuditing(httpServletRequest, actionForm);
      } 
    } else if (flag.equals("save")) {
      tag = "add";
      String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
        "0" : 
        httpServletRequest.getParameter("applyId")
        .toString();
      if ((new VoitureManagerService()).isVoitureAuditingPO(applyId)) {
        updateAuditing(httpServletRequest, actionForm, domainId);
      } else {
        saveAuditing(httpServletRequest, actionForm);
      } 
      httpServletRequest.setAttribute("shouldClose", "1");
      ModelSendMsg sendMsg = new ModelSendMsg();
      VoitureApplyPO vapo = new VoitureApplyPO();
      VoitureManagerService vmbd = new VoitureManagerService();
      vapo = vmbd.getVoitureApplyPO(applyId);
      String msgStr = "您的车辆申请已经通过审核.";
      if ("2".equals(voitureAuditingActionForm.getAuditingStyle()) || 
        "3".equals(voitureAuditingActionForm.getAuditingStyle()))
        msgStr = "您的车辆使用申请情况不属实. 被管理员取消！"; 
      boolean bool = sendMsg.sendSystemMessage("车辆管理", msgStr, vapo.getEmpId(), "", httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void updateAuditing(HttpServletRequest httpServletRequest, ActionForm actionForm, String domainId) throws ParseException {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureAuditingActionForm voitureAuditingActionForm = 
      (VoitureAuditingActionForm)actionForm;
    VoitureAuditingPO vapo = new VoitureAuditingPO();
    String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
      "0" : 
      httpServletRequest.getParameter("applyId").toString();
    String auditingStyle = vmbd.getVoitureAuditingPO(applyId)
      .getAuditingStyle();
    vapo = vmbd.getVoitureAuditingPO(applyId);
    vapo.setAuditingAccount(voitureAuditingActionForm.getAuditingAccount());
    vapo.setAuditingStyle(voitureAuditingActionForm.getAuditingStyle());
    VoitureApplyPO voitureApplyPO = vmbd.getVoitureApplyPO(applyId);
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    voitureApplyPO.setStartDate(new Date(startDate));
    voitureApplyPO.setEndDate(new Date(endDate));
    vapo.setVoitureApplyPO(voitureApplyPO);
    int result = vmbd.updateVoitureAudting(vapo, vapo.getId().toString())
      .intValue();
    httpServletRequest.setAttribute("opResult", String.valueOf(result));
    if (voitureAuditingActionForm.getAuditingStyle().equals("3")) {
      if (!auditingStyle.equals("3")) {
        VoitureCancelPO vcpo = new VoitureCancelPO();
        vcpo.setVoitureApplyPO(vmbd.getVoitureApplyPO(applyId));
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        vcpo.setCancelDate(formatter1.parse(formatter1.format(new Date())));
        vcpo.setCancelMode("3");
        vcpo.setDomainId(Long.valueOf(domainId));
        vmbd.saveVoitureCancel(vcpo);
        vmbd.auditingApply(applyId, "4");
      } 
    } else if (auditingStyle.equals("3")) {
      vmbd.delVoitureCancelByApplyId(applyId);
      vmbd.auditingApply(applyId, "3");
    } 
  }
  
  private void saveAuditing(HttpServletRequest httpServletRequest, ActionForm actionForm) throws ParseException {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureAuditingActionForm voitureAuditingActionForm = 
      (VoitureAuditingActionForm)actionForm;
    VoitureAuditingPO vapo = new VoitureAuditingPO();
    String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
      "0" : 
      httpServletRequest.getParameter("applyId").toString();
    VoitureApplyPO voitureApplyPO = vmbd.getVoitureApplyPO(applyId);
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    voitureApplyPO.setStartDate(new Date(startDate));
    voitureApplyPO.setEndDate(new Date(endDate));
    vapo.setVoitureApplyPO(voitureApplyPO);
    vapo.setAuditingAccount(voitureAuditingActionForm.getAuditingAccount());
    vapo.setAuditingStyle(voitureAuditingActionForm.getAuditingStyle());
    int result = vmbd.saveVoitureAuditing(vapo).intValue();
    httpServletRequest.setAttribute("opResult", String.valueOf(result));
    if (voitureAuditingActionForm.getAuditingStyle().equals("3")) {
      VoitureCancelPO vcpo = new VoitureCancelPO();
      vcpo.setVoitureApplyPO(vmbd.getVoitureApplyPO(applyId));
      SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
      vcpo.setCancelDate(formatter1.parse(formatter1.format(new Date())));
      vcpo.setCancelMode("3");
      vcpo.setDomainId(Long.valueOf(httpServletRequest.getSession().getAttribute("domainId").toString()));
      vcpo.setCancelReason(voitureAuditingActionForm.getAuditingAccount());
      vmbd.saveVoitureCancel(vcpo);
      vmbd.auditingApply(applyId, "4");
    } else {
      vmbd.auditingApply(applyId, "3");
    } 
  }
  
  private void modiAuditing(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureAuditingActionForm voitureAuditingActionForm = 
      (VoitureAuditingActionForm)actionForm;
    VoitureAuditingPO vapo = new VoitureAuditingPO();
    String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
      "0" : 
      httpServletRequest.getParameter("applyId").toString();
    String voitureNum = httpServletRequest.getParameter("voitureNum");
    vapo = vmbd.getVoitureAuditingPO(applyId);
    voitureAuditingActionForm.setAuditingStyle(vapo.getAuditingStyle());
    voitureAuditingActionForm.setAuditingAccount(vapo.getAuditingAccount());
    voitureAuditingActionForm.setVoitureName(voitureNum);
    VoitureApplyPO vappo = new VoitureApplyPO();
    vappo = vmbd.getVoitureApplyPO(applyId);
    voitureAuditingActionForm.setEmpName(vappo.getEmpName());
    voitureAuditingActionForm.setOrgName(vappo.getOrgName());
    voitureAuditingActionForm.setDestination(vappo.getDestination());
    httpServletRequest.setAttribute("startDate", 
        vappo.getStartDate().toString());
    httpServletRequest.setAttribute("endDate", vappo.getEndDate().toString());
    voitureAuditingActionForm.setYcr(vappo.getYcr());
    voitureAuditingActionForm.setDeparturePlace(vappo.getDeparturePlace());
  }
  
  private void addAuditing(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String applyId = (httpServletRequest.getParameter("applyId") == null) ? 
      "0" : 
      httpServletRequest.getParameter("applyId").toString();
    String voitureNum = httpServletRequest.getParameter("voitureNum");
    VoitureAuditingActionForm voitureAuditingActionForm = 
      
      (VoitureAuditingActionForm)actionForm;
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo = vmbd.getVoitureApplyPO(applyId);
    voitureAuditingActionForm.setEmpName(vapo.getEmpName());
    voitureAuditingActionForm.setOrgName(vapo.getOrgName());
    voitureAuditingActionForm.setDestination(vapo.getDestination());
    httpServletRequest.setAttribute("startDate", 
        vapo.getStartDate().toString());
    httpServletRequest.setAttribute("endDate", vapo.getEndDate().toString());
    voitureAuditingActionForm.setVoitureName(voitureNum);
    voitureAuditingActionForm.setYcr(vapo.getYcr());
    voitureAuditingActionForm.setDeparturePlace(vapo.getDeparturePlace());
  }
}
