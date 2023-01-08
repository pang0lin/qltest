package com.js.oa.routine.voiture.action;

import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureFeedbackPO;
import com.js.oa.routine.voiture.po.VoitureSendPO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import com.js.oa.routine.voiture.util.TimeDeal;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureSendAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    VoitureSendActionForm voitureSendActionForm = (VoitureSendActionForm)actionForm;
    VoitureManagerService vbd = new VoitureManagerService();
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    String tag = "view";
    String userName = session.getAttribute("userName").toString();
    String orgName = session.getAttribute("orgName").toString();
    String userId = session.getAttribute("userId").toString();
    String orgString = session.getAttribute("orgIdString").toString();
    if (flag.equals("add")) {
      tag = "add";
      getVoitureSendNumber(actionForm);
      voitureSendActionForm.setApplyId(Long.valueOf(httpServletRequest
            .getParameter("applyId")));
      loadApply(httpServletRequest.getParameter("applyId"), 
          voitureSendActionForm, httpServletRequest);
      List voitureList = vbd.listVoiture("0", domainId, userName, orgName, userId, orgString);
      httpServletRequest.setAttribute("voitureList", voitureList);
    } else if (flag.equals("modi")) {
      tag = "modi";
      String applyId = httpServletRequest.getParameter("applyId");
      if (vbd.getVoitureSendPO(applyId).getId() == null) {
        tag = "add";
        getVoitureSendNumber(actionForm);
        voitureSendActionForm.setApplyId(Long.valueOf(applyId));
        loadApply(httpServletRequest.getParameter("applyId"), 
            voitureSendActionForm, httpServletRequest);
      } else {
        loadVoitureSend(httpServletRequest, voitureSendActionForm);
      } 
      loadApply(httpServletRequest.getParameter("applyId"), 
          voitureSendActionForm, httpServletRequest);
      List voitureList = vbd.listVoiture("0", domainId, userName, orgName, userId, orgString);
      httpServletRequest.setAttribute("voitureList", voitureList);
    } else if (flag.equals("save")) {
      tag = "add";
      saveSend(httpServletRequest, voitureSendActionForm, domainId);
      List voitureList = vbd.listVoiture("0", domainId, userName, orgName, userId, orgString);
      httpServletRequest.setAttribute("voitureList", voitureList);
    } else if (flag.equals("update")) {
      tag = "modi";
      updateSend(httpServletRequest, voitureSendActionForm, domainId);
      List voitureList = vbd.listVoiture("0", domainId, userName, orgName, userId, orgString);
      httpServletRequest.setAttribute("voitureList", voitureList);
    } else if (flag.equals("print")) {
      tag = "print";
      printVoitureSend(httpServletRequest, voitureSendActionForm);
    } else if (flag.equals("feedBack")) {
      tag = "feedBack";
      feedBack(httpServletRequest, voitureSendActionForm);
    } else if (flag.equals("feedBackSave")) {
      tag = "feedBack";
      feedBackSave(httpServletRequest);
    } else if (flag.equals("reedBackAStat")) {
      tag = "reedBackAStat";
      reedBackAStat(httpServletRequest, "");
    } 
    return actionMapping.findForward(tag);
  }
  
  public void saveSend(HttpServletRequest httpServletRequest, VoitureSendActionForm voitureSendActionForm, String domainId) throws Exception {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(voitureSendActionForm.getApplyempId());
    vapo.setOrgId(voitureSendActionForm.getApplyorgId());
    vapo.setOrgName(voitureSendActionForm.getApplyorgName());
    vapo.setVoitureId(voitureSendActionForm.getVoitureId());
    vapo.setDestination(voitureSendActionForm.getApplydestination());
    vapo.setEmpName(voitureSendActionForm.getApplyempName());
    vapo.setReason(voitureSendActionForm.getApplyreason());
    String applyStartDate = httpServletRequest.getParameter(
        "applyStartDate");
    String applyEndDate = httpServletRequest.getParameter("applyEndDate");
    String applyStartHour = voitureSendActionForm.getApplystartHour();
    String applyStartMinute = voitureSendActionForm.getApplystartMinute();
    String applyEndHour = voitureSendActionForm.getApplyendHour();
    String applyEndMinute = voitureSendActionForm.getApplyendMinute();
    SimpleDateFormat applyFormatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat applyFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
    vapo.setStartDate(new Date(applyStartDate));
    vapo.setEndDate(new Date(applyEndDate));
    vapo.setStartTime(applyFormatter.format(applyFormatter.parse(
            String.valueOf(applyStartHour) + ":" + 
            applyStartMinute)));
    vapo.setEndTime(applyFormatter.format(applyFormatter.parse(String.valueOf(applyEndHour) + 
            ":" + 
            applyEndMinute)));
    vapo.setFillDate(applyFormatter1.parse(applyFormatter1.format(new Date())));
    if (voitureSendActionForm.getApplypersonNum() != null && 
      !voitureSendActionForm.getApplypersonNum().equals(""))
      vapo.setPersonNum(voitureSendActionForm.getApplypersonNum()); 
    vapo.setMotorMan(voitureSendActionForm.getApplymotorMan());
    vapo.setVoitureStyle(voitureSendActionForm.getApplyvoitureStyle());
    vapo.setRemark(voitureSendActionForm.getApplyremark());
    String applyId = voitureSendActionForm.getApplyId().toString();
    int result = vmbd.updateVoitureApply(vapo, applyId).intValue();
    httpServletRequest.setAttribute("opResult", String.valueOf(result));
    VoitureSendPO voitureSendPO = new VoitureSendPO();
    voitureSendPO.setSendNumber(voitureSendActionForm.getSendNumber());
    String sendStartDate = httpServletRequest.getParameter("sendStartDate");
    String sendEndDate = httpServletRequest.getParameter("sendEndDate");
    String startHour = voitureSendActionForm.getSendStartHour();
    String startMinute = voitureSendActionForm.getSendStartMinute();
    String endHour = voitureSendActionForm.getSendEndHour();
    String endMinute = voitureSendActionForm.getSendEndMinute();
    TimeDeal td = new TimeDeal();
    String startDateAddTime = td.dealString(sendStartDate, startHour, 
        startMinute);
    String endDateAddTime = td.dealString(sendEndDate, endHour, endMinute);
    SimpleDateFormat formatter1 = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");
    Date startRQ = formatter1.parse(startDateAddTime);
    Date endRQ = formatter1.parse(endDateAddTime);
    voitureSendPO.setSendStartTimeTotal(new Long(startRQ.getTime()));
    voitureSendPO.setSendEndTimeTotal(new Long(endRQ.getTime()));
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    voitureSendPO.setSendStartDate(new Date(sendStartDate));
    voitureSendPO.setSendEndDate(new Date(sendEndDate));
    voitureSendPO.setSendStartTime(formatter.format(formatter.parse(
            String.valueOf(startHour) + ":" + 
            startMinute)));
    voitureSendPO.setSendEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + 
            ":" + 
            endMinute)));
    voitureSendPO.setSendStartKilo(voitureSendActionForm.getSendStartKilo());
    voitureSendPO.setSendEndKilo(voitureSendActionForm.getSendEndKilo());
    voitureSendPO.setKeepingFee(voitureSendActionForm.getKeepingFee());
    voitureSendPO.setTravelFee(voitureSendActionForm.getTravelFee());
    voitureSendPO.setSendCount(voitureSendActionForm.getSendCount());
    voitureSendPO.setSendHolidayCount(voitureSendActionForm
        .getSendHolidayCount());
    voitureSendPO.setMisMealFee(voitureSendActionForm.getMisMealFee());
    voitureSendPO.setOverTime(voitureSendActionForm.getOverTime());
    voitureSendPO.setOverTimeHoliDay(voitureSendActionForm
        .getOverTimeHoliday());
    voitureSendPO.setOverTimeWeekend(voitureSendActionForm
        .getOverTimeWeekend());
    voitureSendPO.setKiloPrice(voitureSendActionForm.getKiloPrice());
    voitureSendPO.setOtherAllowance(voitureSendActionForm.getOtherAllowance());
    VoitureApplyPO voitureApplyPO = vmbd.loadVoitureApply(applyId);
    voitureSendPO.setVoitureApplyPO(voitureApplyPO);
    voitureSendPO.setVoiturePO(vmbd.getVoiturePO(voitureApplyPO
          .getVoitureId().toString()));
    voitureSendPO.setDomainId(Long.valueOf(domainId));
    String opResult = vmbd.saveVoitureSend(voitureSendPO).toString();
    if (opResult.equals("0"))
      vmbd.auditingApply(applyId, vapo, "3"); 
    httpServletRequest.setAttribute("shouldClose", opResult);
  }
  
  public void getVoitureSendNumber(ActionForm actionForm) {
    VoitureSendActionForm voitureSendActionForm = 
      (VoitureSendActionForm)actionForm;
    VoitureManagerService vmbd = new VoitureManagerService();
    String sendNumber = "";
    String serialNumber = "";
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String maxSendNumber = vmbd.getVoitureSendNumberMax();
    if (!maxSendNumber.equals("")) {
      serialNumber = String.valueOf(Integer.parseInt(maxSendNumber
            .substring(10, 13)) + 1);
      int length = serialNumber.length();
      if (length == 1)
        serialNumber = "00" + serialNumber; 
      if (length == 2)
        serialNumber = "0" + serialNumber; 
    } else {
      serialNumber = "001";
    } 
    sendNumber = "PC" + formatter.format(cal.getTime()) + serialNumber;
    voitureSendActionForm.setSendNumber(sendNumber);
  }
  
  public void loadVoitureSend(HttpServletRequest httpServletRequest, VoitureSendActionForm voitureSendActionForm) throws ParseException {
    VoitureManagerService vmbd = new VoitureManagerService();
    String applyId = httpServletRequest.getParameter("applyId");
    String sendId = vmbd.getVoitureSendPO(applyId).getId().toString();
    VoitureSendPO vspo = vmbd.loadVoitureSend(sendId);
    voitureSendActionForm.setSendNumber(vspo.getSendNumber());
    SimpleDateFormat formatter1 = new SimpleDateFormat(
        "yyyy-MM-dd");
    httpServletRequest.setAttribute("sendStartDate", 
        formatter1.format(vspo.getSendStartDate()));
    voitureSendActionForm.setSendStartHour(vspo.getSendStartTime().split(
          "\\:")[0]);
    voitureSendActionForm.setSendStartMinute(vspo.getSendStartTime().split(
          "\\:")[1]);
    httpServletRequest.setAttribute("sendEndDate", 
        formatter1.format(vspo.getSendEndDate()));
    voitureSendActionForm.setSendEndHour(vspo.getSendEndTime().split("\\:")[
          0]);
    voitureSendActionForm.setSendEndMinute(vspo.getSendEndTime().split(
          "\\:")[1]);
    voitureSendActionForm.setSendStartKilo(vspo.getSendStartKilo());
    voitureSendActionForm.setSendEndKilo(vspo.getSendEndKilo());
    voitureSendActionForm.setKeepingFee(vspo.getKeepingFee());
    voitureSendActionForm.setTravelFee(vspo.getTravelFee());
    voitureSendActionForm.setSendCount(vspo.getSendCount());
    voitureSendActionForm.setSendHolidayCount(vspo.getSendHolidayCount());
    voitureSendActionForm.setMisMealFee(vspo.getMisMealFee());
    voitureSendActionForm.setOverTime(vspo.getOverTime());
    voitureSendActionForm.setOverTimeHoliday(vspo.getOverTimeHoliDay());
    voitureSendActionForm.setOverTimeWeekend(vspo.getOverTimeWeekend());
    voitureSendActionForm.setKiloPrice(vspo.getKiloPrice());
    voitureSendActionForm.setOtherAllowance(vspo.getOtherAllowance());
    SimpleDateFormat formatter = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");
    Date startDate = formatter.parse(vspo.getSendStartDate() + " " + 
        vspo.getSendStartTime());
    Date endDate = formatter.parse(vspo.getSendEndDate() + " " + 
        vspo.getSendEndTime());
    long totleTime = (endDate.getTime() - startDate.getTime()) / 1000L * 
      3600L;
    double totleKilo = (new BigDecimal((vspo.getSendEndKilo() == null) ? "0" : 
        vspo.getSendEndKilo())).subtract(
        new BigDecimal((vspo.getSendStartKilo() == null) ? "0" : 
          vspo.getSendStartKilo())).doubleValue();
    double totleFee = (new BigDecimal(((vspo.getKeepingFee() == null) ? 0.0F : 
        vspo.getKeepingFee().floatValue())))
      .add(new BigDecimal(((vspo.getTravelFee() == null) ? 0.0F : 
          vspo.getTravelFee().floatValue())))
      .doubleValue();
    double totlekiloPrice = (new BigDecimal((vspo.getKiloPrice() == null) ? 
        "0" : 
        vspo.getKiloPrice().toString()))
      .multiply(new BigDecimal(totleKilo))
      .doubleValue();
    httpServletRequest.setAttribute("totleKilo", Double.toString(totleKilo));
    httpServletRequest.setAttribute("totleFee", Double.toString(totleFee));
    httpServletRequest.setAttribute("totleTime", Long.toString(totleTime));
    httpServletRequest.setAttribute("totlekiloPrice", 
        Double.toString(totlekiloPrice));
  }
  
  public void printVoitureSend(HttpServletRequest httpServletRequest, VoitureSendActionForm voitureSendActionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String applyId = httpServletRequest.getParameter("applyId");
    VoitureApplyPO vapo = vmbd.loadVoitureApply(applyId);
    httpServletRequest.setAttribute("userName", vapo.getEmpName());
    httpServletRequest.setAttribute("orgName", vapo.getOrgName());
    httpServletRequest.setAttribute("destination", vapo.getDestination());
    httpServletRequest.setAttribute("reason", vapo.getReason());
    httpServletRequest.setAttribute("ycr", vapo.getYcr());
    SimpleDateFormat formatter1 = new SimpleDateFormat(
        "yyyy-MM-dd");
    httpServletRequest.setAttribute("startTime", 
        String.valueOf(formatter1.format(vapo.getStartDate())) + 
        " " + vapo.getStartTime());
    httpServletRequest.setAttribute("endTime", 
        String.valueOf(formatter1.format(vapo.getEndDate())) + 
        " " + vapo.getEndTime());
  }
  
  public void updateSend(HttpServletRequest httpServletRequest, VoitureSendActionForm voitureSendActionForm, String domainId) throws Exception {
    String applyId = voitureSendActionForm.getApplyId().toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO vapo = new VoitureApplyPO();
    vapo.setEmpId(voitureSendActionForm.getApplyempId());
    vapo.setOrgId(voitureSendActionForm.getApplyorgId());
    vapo.setOrgName(voitureSendActionForm.getApplyorgName());
    vapo.setVoitureId(voitureSendActionForm.getVoitureId());
    vapo.setDestination(voitureSendActionForm.getApplydestination());
    vapo.setEmpName(voitureSendActionForm.getApplyempName());
    vapo.setReason(voitureSendActionForm.getApplyreason());
    vapo.setYcr(voitureSendActionForm.getApplyycr());
    String applyStartDate = httpServletRequest.getParameter(
        "applyStartDate");
    String applyEndDate = httpServletRequest.getParameter("applyEndDate");
    String applyStartHour = voitureSendActionForm.getApplystartHour();
    String applyStartMinute = voitureSendActionForm.getApplystartMinute();
    String applyEndHour = voitureSendActionForm.getApplyendHour();
    String applyEndMinute = voitureSendActionForm.getApplyendMinute();
    SimpleDateFormat applyFormatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat applyFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
    vapo.setStartDate(new Date(applyStartDate));
    vapo.setEndDate(new Date(applyEndDate));
    vapo.setStartTime(applyFormatter.format(applyFormatter.parse(
            String.valueOf(applyStartHour) + ":" + 
            applyStartMinute)));
    vapo.setEndTime(applyFormatter.format(applyFormatter.parse(String.valueOf(applyEndHour) + 
            ":" + 
            applyEndMinute)));
    vapo.setFillDate(applyFormatter1.parse(applyFormatter1.format(new Date())));
    if (voitureSendActionForm.getApplypersonNum() != null && 
      !voitureSendActionForm.getApplypersonNum().equals(""))
      vapo.setPersonNum(voitureSendActionForm.getApplypersonNum()); 
    vapo.setMotorMan(voitureSendActionForm.getApplymotorMan());
    vapo.setVoitureStyle(voitureSendActionForm.getApplyvoitureStyle());
    vapo.setRemark(voitureSendActionForm.getApplyremark());
    int result = vmbd.updateVoitureApply(vapo, applyId).intValue();
    httpServletRequest.setAttribute("opResult", String.valueOf(result));
    String sendId = vmbd.getVoitureSendPO(applyId).getId().toString();
    VoitureSendPO voitureSendPO = new VoitureSendPO();
    voitureSendPO.setSendNumber(voitureSendActionForm.getSendNumber());
    String sendStartDate = httpServletRequest.getParameter("sendStartDate");
    String sendEndDate = httpServletRequest.getParameter("sendEndDate");
    String startHour = voitureSendActionForm.getSendStartHour();
    String startMinute = voitureSendActionForm.getSendStartMinute();
    String endHour = voitureSendActionForm.getSendEndHour();
    String endMinute = voitureSendActionForm.getSendEndMinute();
    TimeDeal td = new TimeDeal();
    String startDateAddTime = td.dealString(sendStartDate, startHour, 
        startMinute);
    String endDateAddTime = td.dealString(sendEndDate, endHour, endMinute);
    SimpleDateFormat formatter1 = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");
    Date startRQ = formatter1.parse(startDateAddTime);
    Date endRQ = formatter1.parse(endDateAddTime);
    voitureSendPO.setSendStartTimeTotal(new Long(startRQ.getTime()));
    voitureSendPO.setSendEndTimeTotal(new Long(endRQ.getTime()));
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    voitureSendPO.setSendStartDate(new Date(sendStartDate));
    voitureSendPO.setSendEndDate(new Date(sendEndDate));
    voitureSendPO.setSendStartTime(formatter.format(formatter.parse(
            String.valueOf(startHour) + ":" + startMinute)));
    voitureSendPO.setSendEndTime(formatter.format(formatter.parse(String.valueOf(endHour) + 
            ":" + endMinute)));
    voitureSendPO.setSendStartKilo(voitureSendActionForm.getSendStartKilo());
    voitureSendPO.setSendEndKilo(voitureSendActionForm.getSendEndKilo());
    voitureSendPO.setKeepingFee(voitureSendActionForm.getKeepingFee());
    voitureSendPO.setTravelFee(voitureSendActionForm.getTravelFee());
    voitureSendPO.setSendCount(voitureSendActionForm.getSendCount());
    voitureSendPO.setSendHolidayCount(voitureSendActionForm
        .getSendHolidayCount());
    voitureSendPO.setMisMealFee(voitureSendActionForm.getMisMealFee());
    voitureSendPO.setOverTime(voitureSendActionForm.getOverTime());
    voitureSendPO.setOverTimeHoliDay(voitureSendActionForm
        .getOverTimeHoliday());
    voitureSendPO.setOverTimeWeekend(voitureSendActionForm
        .getOverTimeWeekend());
    VoitureApplyPO voitureApplyPO = vmbd.loadVoitureApply(applyId);
    voitureSendPO.setKiloPrice(voitureSendActionForm.getKiloPrice());
    voitureSendPO.setOtherAllowance(voitureSendActionForm.getOtherAllowance());
    voitureSendPO.setVoitureApplyPO(voitureApplyPO);
    if (voitureApplyPO != null && voitureApplyPO.getVoitureId() != null)
      voitureSendPO.setVoiturePO(vmbd.getVoiturePO(voitureApplyPO
            .getVoitureId().toString())); 
    voitureSendPO.setDomainId(Long.valueOf(domainId));
    String opResult = vmbd.updateVoitureSend(voitureSendPO, sendId)
      .toString();
    if (opResult.equals("0"))
      vmbd.auditingApply(applyId, vapo, "3"); 
    httpServletRequest.setAttribute("shouldClose", opResult);
  }
  
  public void loadApply(String applyId, VoitureSendActionForm actionForm, HttpServletRequest httpServletRequest) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureApplyPO voitureApplyPO = vmbd.loadVoitureApply(applyId);
    actionForm.setApplyempId(voitureApplyPO.getEmpId());
    actionForm.setApplyorgId(voitureApplyPO.getOrgId());
    actionForm.setApplyorgName(voitureApplyPO.getOrgName());
    actionForm.setVoitureId(voitureApplyPO.getVoitureId());
    actionForm.setApplyvoitureName(vmbd.getVoiturePO(voitureApplyPO
          .getVoitureId().toString()).getName());
    actionForm.setApplydestination(voitureApplyPO
        .getDestination());
    actionForm.setApplyempName(voitureApplyPO.getEmpName());
    actionForm.setApplyreason(voitureApplyPO.getReason());
    actionForm.setApplystatus(voitureApplyPO.getStatus());
    actionForm.setApplypersonNum(voitureApplyPO.getPersonNum());
    actionForm.setApplymotorMan(voitureApplyPO.getMotorMan());
    actionForm.setApplyycr(voitureApplyPO.getYcr());
    actionForm.setApplyvoitureStyle(voitureApplyPO.getVoitureStyle());
    actionForm.setApplyremark(voitureApplyPO.getRemark());
    String startDate = voitureApplyPO.getStartDate().toString();
    String endDate = voitureApplyPO.getEndDate().toString();
    String startTime = voitureApplyPO.getStartTime().toString();
    String endTime = voitureApplyPO.getEndTime().toString();
    httpServletRequest.setAttribute("applyStartDate", startDate);
    httpServletRequest.setAttribute("applyEndDate", endDate);
    actionForm.setApplystartHour(startTime.split("\\:")[0]);
    actionForm.setApplystartMinute(startTime.split("\\:")[1]);
    actionForm.setApplyendHour(endTime.split("\\:")[0]);
    actionForm.setApplyendMinute(endTime.split("\\:")[1]);
    httpServletRequest.setAttribute("applyvoitureStyle", 
        voitureApplyPO.getVoitureStyle());
    httpServletRequest.setAttribute("applystartHour", 
        startTime.split("\\:")[0]);
    httpServletRequest.setAttribute("applystartMinute", 
        startTime.split("\\:")[1]);
    httpServletRequest.setAttribute("applyendHour", endTime.split("\\:")[0]);
    httpServletRequest.setAttribute("applyendMinute", 
        endTime.split("\\:")[1]);
    httpServletRequest.setAttribute("voitureId", 
        voitureApplyPO.getVoitureId());
  }
  
  public void feedBack(HttpServletRequest request, VoitureSendActionForm voitureSendActionForm) {
    Long long_;
    String satisfaction = "";
    String satiRemark = "";
    String feedBackId = "";
    String voitureId = request.getParameter("voitureId");
    String ismy = request.getParameter("ismy");
    VoitureManagerService bd = new VoitureManagerService();
    VoitureFeedbackPO feedbackPO = new VoitureFeedbackPO();
    feedbackPO = bd.getVoitureFeedbackPO(voitureId);
    if (feedbackPO != null && feedbackPO.getId() != null && 
      !feedbackPO.getId().toString().trim().equals("")) {
      satisfaction = feedbackPO.getSatisfaction();
      satiRemark = feedbackPO.getSatiRemark();
      long_ = feedbackPO.getId();
    } 
    voitureSendActionForm.setSatiRemark(satiRemark);
    voitureSendActionForm.setSatisfaction(satisfaction);
    request.setAttribute("feedBackId", long_);
    request.setAttribute("ismy", ismy);
    request.setAttribute("voitureId", voitureId);
  }
  
  public void feedBackSave(HttpServletRequest request) {
    String feedBackId = request.getParameter("feedBackId");
    String voitureId = request.getParameter("voitureId");
    VoitureFeedbackPO po = new VoitureFeedbackPO();
    VoitureManagerService bd = new VoitureManagerService();
    po.setSatiRemark((request.getParameter("satiRemark") == null) ? "" : 
        request.getParameter("satiRemark").toString());
    po.setSatisfaction((request.getParameter("satisfaction") == null) ? "" : 
        request.getParameter("satisfaction").toString());
    po.setVoitureId(new Long(voitureId));
    request.setAttribute("voitureId", voitureId);
    if (feedBackId != null && !feedBackId.trim().equals("")) {
      po.setId(new Long(feedBackId));
      String ur = bd.updateVoitureFeedbackPO(po);
      if (ur != null && ur.equals("1")) {
        request.setAttribute("result", "1");
      } else {
        request.setAttribute("result", "0");
      } 
    } else {
      Long sr = bd.saveVoitureFeedbackPO(po);
      if (sr != null && !sr.toString().equals("-1")) {
        request.setAttribute("result", "1");
        request.setAttribute("feedBackId", sr);
      } else {
        request.setAttribute("result", "0");
      } 
    } 
  }
  
  private void reedBackAStat(HttpServletRequest request, String type) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    List<String> list = new ArrayList();
    VoitureManagerService bd = new VoitureManagerService();
    String whereSql = " and 2>1 and vpo.domainId=" + domainId;
    String motorMan = (request.getParameter("motorMan") == null) ? "" : 
      request.getParameter("motorMan").toString();
    String empName = (request.getParameter("empName") == null) ? "" : 
      request.getParameter("empName").toString();
    String sqlStr = "";
    if (motorMan != null && !motorMan.equals("")) {
      request.setAttribute("motorMan", motorMan);
      whereSql = String.valueOf(whereSql) + " and ( vpo.motorMan like '%" + motorMan + "%' ) ";
    } 
    if (empName != null && !empName.equals("")) {
      request.setAttribute("empName", empName);
      whereSql = String.valueOf(whereSql) + " and ( vpo.empName like '%" + empName + "%' ) ";
    } 
    if (request.getParameter("beginDate") != null && 
      !request.getParameter("beginDate").toString().equals("") && 
      request.getParameter("endDate") != null && 
      !request.getParameter("endDate").toString().equals("") && 
      request.getParameter("dateCheck") != null && 
      request.getParameter("dateCheck").toString().equals("1")) {
      Date bFate = new Date(request.getParameter("beginDate"));
      Date eFate = new Date(request.getParameter("endDate"));
      request.setAttribute("beginDate", bFate);
      request.setAttribute("endDate", 
          new Date(request.getParameter("endDate")));
      request.setAttribute("dateCheck", "1");
      whereSql = String.valueOf(whereSql) + 
        "and ( vpo.startDate >=:bFate and  vpo.endDate<= :eFate )";
      sqlStr = "select po.satisfaction from com.js.oa.routine.voiture.po.VoitureApplyPO vpo, com.js.oa.routine.voiture.po.VoitureFeedbackPO po  where po.voitureId=vpo.id   " + 
        whereSql;
      list = bd.getFeedbackStat2(sqlStr, bFate, eFate);
    } else {
      sqlStr = "select po.satisfaction from com.js.oa.routine.voiture.po.VoitureApplyPO vpo, com.js.oa.routine.voiture.po.VoitureFeedbackPO po  where po.voitureId=vpo.id " + 
        whereSql;
      list = bd.getFeedbackStat(sqlStr);
    } 
    double num1 = 0.0D;
    double num2 = 0.0D;
    double num3 = 0.0D;
    double num4 = 0.0D;
    String numPer1 = "0";
    String numPer2 = "0";
    String numPer3 = "0";
    String numPer4 = "0";
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        String obj = list.get(i);
        if (obj != null && !obj.trim().equals("")) {
          String statif = obj;
          if (statif != null) {
            if (statif.equals("很满意"))
              num1++; 
            if (statif.equals("满意"))
              num2++; 
            if (statif.equals("一般"))
              num3++; 
            if (statif.equals("不满意"))
              num4++; 
          } 
        } 
      }  
    int scale = 4;
    double totalNum = num1 + num2 + num3 + num4;
    if (totalNum > 0.0D) {
      BigDecimal one = new BigDecimal("1");
      BigDecimal b1 = new BigDecimal(Double.toString(num1));
      BigDecimal b2 = new BigDecimal(Double.toString(totalNum));
      double n1 = b1.divide(b2, scale, 
          4).divide(one, scale, 
          4).doubleValue() * 100.0D;
      BigDecimal nn1 = new BigDecimal(n1);
      n1 = nn1.divide(one, 2, 4).doubleValue();
      numPer1 = n1 + "%";
      BigDecimal b12 = new BigDecimal(Double.toString(num2));
      BigDecimal b22 = new BigDecimal(Double.toString(totalNum));
      double n12 = b12.divide(b22, scale, 4)
        .divide(one, scale, 4)
        .doubleValue() * 100.0D;
      BigDecimal nn12 = new BigDecimal(n12);
      n12 = nn12.divide(one, 2, 4).doubleValue();
      numPer2 = n12 + "%";
      BigDecimal b13 = new BigDecimal(Double.toString(num3));
      BigDecimal b23 = new BigDecimal(Double.toString(totalNum));
      double n13 = b13.divide(b23, scale, 4)
        .divide(one, scale, 4)
        .doubleValue() * 100.0D;
      BigDecimal nn13 = new BigDecimal(n13);
      n13 = nn13.divide(one, 2, 4).doubleValue();
      numPer3 = n13 + "%";
      BigDecimal b14 = new BigDecimal(Double.toString(num4));
      BigDecimal b24 = new BigDecimal(Double.toString(totalNum));
      double n14 = b14.divide(b24, scale, 4)
        .divide(one, scale, 4)
        .doubleValue() * 100.0D;
      BigDecimal nn14 = new BigDecimal(n14);
      n14 = nn14.divide(one, 2, 4).doubleValue();
      numPer4 = n14 + "%";
    } 
    Map<Object, Object> mapn = new HashMap<Object, Object>();
    mapn.put("num1", (new StringBuilder(String.valueOf((int)num1))).toString());
    mapn.put("num2", (new StringBuilder(String.valueOf((int)num2))).toString());
    mapn.put("num3", (new StringBuilder(String.valueOf((int)num3))).toString());
    mapn.put("num4", (new StringBuilder(String.valueOf((int)num4))).toString());
    mapn.put("numPer1", (new StringBuilder(String.valueOf(numPer1))).toString());
    mapn.put("numPer2", (new StringBuilder(String.valueOf(numPer2))).toString());
    mapn.put("numPer3", (new StringBuilder(String.valueOf(numPer3))).toString());
    mapn.put("numPer4", (new StringBuilder(String.valueOf(numPer4))).toString());
    request.setAttribute("mapn", mapn);
  }
}
