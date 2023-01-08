package com.js.oa.form;

import com.js.oa.routine.boardroom.po.EquipmentApplyPO;
import com.js.oa.routine.boardroom.po.EquipmentPO;
import com.js.oa.routine.boardroom.service.EquipmentBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EquipmentWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentId"));
    Long Id = equipmentBD.addEquipmentApply(setPO(httpServletRequest), 
        equipmentId);
    if ("-1".equals(Id))
      Id = Long.valueOf("-1"); 
    return Id;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    EquipmentApplyPO equipmentApplyPO = equipmentBD.selectEquipmentApply(
        equipmentApplyId);
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
    equipmentApplyPO.setPurpose(httpServletRequest.getParameter("purpose"));
    boolean result = equipmentBD.modiEquipmentApply(equipmentApplyPO);
    if (result) {
      equipmentApplyId = equipmentApplyId;
    } else {
      equipmentApplyId = Long.valueOf("-1");
    } 
    return equipmentApplyId;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    EquipmentApplyPO equipmentApplyPO = equipmentBD.selectEquipmentApply(
        equipmentApplyId);
    EquipmentPO ePo = equipmentApplyPO.getEquipment();
    if ("0".equals(httpServletRequest.getParameter("type")))
      ePo.setStatus(Integer.valueOf("3")); 
    equipmentApplyPO.setEquipment(ePo);
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
    equipmentApplyPO.setPurpose(httpServletRequest.getParameter("purpose"));
    equipmentApplyPO.setStatus(Integer.valueOf("3"));
    boolean result = equipmentBD.modiEquipmentApply(equipmentApplyPO);
    if (result) {
      equipmentApplyId = equipmentApplyId;
    } else {
      equipmentApplyId = Long.valueOf("-1");
    } 
    return equipmentApplyId;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentApplyId = Long.valueOf(httpServletRequest.getParameter(
          "equipmentApplyId"));
    EquipmentApplyPO equipmentApplyPO = equipmentBD.selectEquipmentApply(
        equipmentApplyId);
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
    if (result) {
      equipmentApplyId = equipmentApplyId;
    } else {
      equipmentApplyId = Long.valueOf("-1");
    } 
    return equipmentApplyId;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    EquipmentBD equipmentBD = new EquipmentBD();
    Long equipmentApplyId = new Long(httpServletRequest.getParameter(
          "equipmentApplyId"));
    EquipmentApplyPO equipmentApplyPO = equipmentBD.selectEquipmentApply(
        equipmentApplyId);
    equipmentApplyPO.setStatus(new Integer(3));
    EquipmentPO equipmentPO = equipmentApplyPO.getEquipment();
    if (httpServletRequest.getParameter("backToStep") == null) {
      equipmentPO.setStatus(Integer.valueOf("3"));
    } else if (httpServletRequest.getParameter("backToStep").equals("0")) {
      equipmentPO.setStatus(Integer.valueOf("3"));
    } else {
      equipmentPO.setStatus(Integer.valueOf("1"));
    } 
    equipmentApplyPO.setEquipment(equipmentPO);
    boolean result = equipmentBD.modiEquipmentApply(equipmentApplyPO);
    if (result) {
      equipmentApplyId = equipmentApplyId;
    } else {
      equipmentApplyId = Long.valueOf("-1");
    } 
    return equipmentApplyId;
  }
  
  private EquipmentApplyPO setPO(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    EquipmentApplyPO equipmentApplyPO = new EquipmentApplyPO();
    equipmentApplyPO.setEmpId(Long.valueOf(request.getParameter("borrower")));
    equipmentApplyPO.setEmpName(request.getParameter("borrowerName"));
    equipmentApplyPO.setOrgId(Long.valueOf(request.getParameter(
            "borrowerOrg")));
    equipmentApplyPO.setOrgName(request.getParameter("borrowerOrgName"));
    equipmentApplyPO.setStartDate(new Date(request.getParameter("startDate")));
    equipmentApplyPO.setStartTime(request.getParameter("startTime"));
    equipmentApplyPO.setEndDate(new Date(request.getParameter("endDate")));
    equipmentApplyPO.setEndTime(request.getParameter("endTime"));
    equipmentApplyPO.setStatus(new Integer(1));
    equipmentApplyPO.setPurpose(request.getParameter("purpose"));
    equipmentApplyPO.setDomainId(domainId);
    return equipmentApplyPO;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("equipmentApplyId") != null && 
      !"".equals(httpServletRequest.getParameter("equipmentApplyId"))) {
      EquipmentBD equipmentBD = new EquipmentBD();
      Long equipmentApplyId = new Long(httpServletRequest.getParameter(
            "equipmentApplyId"));
      equipmentBD.deleteEquipmentApply(equipmentApplyId);
    } 
  }
}
