package com.js.oa.form;

import com.js.oa.routine.voiture.bean.VoitureEJBBean;
import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VoitureWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) throws ParseException {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(httpServletRequest.getParameter("empId"));
    vapo.setOrgId(httpServletRequest.getParameter("orgId"));
    vapo.setOrgName(httpServletRequest.getParameter("orgName"));
    if (httpServletRequest.getParameter("voitureId") != null && 
      !httpServletRequest.getParameter("voitureId").toString().equals(""))
      vapo.setVoitureId(new Long(httpServletRequest.getParameter(
              "voitureId"))); 
    vapo.setDestination(httpServletRequest.getParameter("destination"));
    vapo.setEmpName(httpServletRequest.getParameter("empName"));
    vapo.setReason(httpServletRequest.getParameter("reason"));
    vapo.setDomainId(Long.valueOf(domainId));
    vapo.setStatus("1");
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = httpServletRequest.getParameter("startHour");
    String startMinute = httpServletRequest.getParameter("startMinute");
    String endHour = httpServletRequest.getParameter("endHour");
    String endMinute = httpServletRequest.getParameter("endMinute");
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    vapo.setStartDate(new Date(startDate));
    vapo.setEndDate(new Date(endDate));
    vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
            startMinute)));
    vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
            endMinute)));
    vapo.setFillDate(formatter1.parse(formatter1.format(new Date())));
    if (httpServletRequest.getParameter("personNum") != null && 
      !httpServletRequest.getParameter("personNum").equals(""))
      vapo.setPersonNum(httpServletRequest.getParameter("personNum")); 
    vapo.setMotorMan((httpServletRequest.getParameter("motorMan") == null) ? 
        "" : httpServletRequest.getParameter("motorMan"));
    vapo.setVoitureStyle(httpServletRequest.getParameter("voitureStyle"));
    vapo.setRemark(httpServletRequest.getParameter("remark"));
    vapo.setYcr(httpServletRequest.getParameter("ycr"));
    vapo.setDeparturePlace(httpServletRequest.getParameter("departurePlace"));
    vapo.setVehiclePhone(httpServletRequest.getParameter("vehiclePhone"));
    vapo.setVehicleNum(httpServletRequest.getParameter("vehicleNum"));
    vapo.setCarPoolId(httpServletRequest.getParameter("carPoolId"));
    int result = vmbd.saveVoitureApply(vapo).intValue();
    return new Long(result);
  }
  
  public Long update(HttpServletRequest httpServletRequest) throws ParseException {
    System.out.println("*************************** UPDATE: ");
    String applyId = httpServletRequest.getParameter("applyId");
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(httpServletRequest.getParameter("empId"));
    vapo.setOrgId(httpServletRequest.getParameter("orgId"));
    vapo.setOrgName(httpServletRequest.getParameter("orgName"));
    if (httpServletRequest.getParameter("voitureId") != null && 
      !httpServletRequest.getParameter("voitureId").toString().equals(""))
      vapo.setVoitureId(new Long(httpServletRequest.getParameter(
              "voitureId"))); 
    vapo.setDestination(httpServletRequest.getParameter("destination"));
    vapo.setEmpName(httpServletRequest.getParameter("empName"));
    vapo.setReason(httpServletRequest.getParameter("reason"));
    vapo.setStatus("1");
    String startDate = httpServletRequest.getParameter("startDate");
    String endDate = httpServletRequest.getParameter("endDate");
    String startHour = httpServletRequest.getParameter("startHour");
    String startMinute = httpServletRequest.getParameter("startMinute");
    String endHour = httpServletRequest.getParameter("endHour");
    String endMinute = httpServletRequest.getParameter("endMinute");
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    vapo.setStartDate(new Date(startDate));
    vapo.setEndDate(new Date(endDate));
    vapo.setStartTime(formatter.format(formatter.parse(String.valueOf(startHour) + ":" + 
            startMinute)));
    vapo.setEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + ":" + 
            endMinute)));
    vapo.setFillDate(formatter1.parse(formatter1.format(new Date())));
    vapo.setMotorMan(httpServletRequest.getParameter("motorMan"));
    vapo.setRemark(httpServletRequest.getParameter("remark"));
    int result = vmbd.updateVoitureApply(vapo, applyId).intValue();
    return new Long(result);
  }
  
  public void complete(HttpServletRequest httpServletRequest) {
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setMotorMan(httpServletRequest.getParameter("motorMan"));
    VoitureManagerService vmbd = new VoitureManagerService();
    String applyId = httpServletRequest.getParameter("applyId");
    vmbd.auditingApply(applyId, vapo, "2");
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("applyId") != null && 
      !"".equals(httpServletRequest.getParameter("applyId"))) {
      VoitureManagerService vmbd = new VoitureManagerService();
      String applyId = String.valueOf(httpServletRequest.getParameter("applyId")) + ",";
      vmbd.delVoitureApply(applyId);
    } 
  }
  
  public void back(HttpServletRequest httpServletRequest) {
    String applyId = httpServletRequest.getParameter("applyId");
    VoitureEJBBean voitureEJBBean = new VoitureEJBBean();
    try {
      voitureEJBBean.updateCarPoolToMainRec(new Long(applyId));
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
