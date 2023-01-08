package com.js.oa.routine.voiture.action;

import com.js.oa.routine.voiture.po.VoitureFeePO;
import com.js.oa.routine.voiture.service.VoitureManagerSecondBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureFeeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    Integer result = null;
    Integer update_info = null;
    String viewSQL = "";
    String fromSQL = "";
    String whereSQL = "";
    viewSQL = "b.id,b.num,b.model,b.name,b.motorMan,max(a.maintainTime)";
    fromSQL = "com.js.oa.routine.voiture.po.VoitureFeePO a right join a.voiturePO b";
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String whereSql = " (" + (new ManagerService()).getRightFinalWhere(curUserId, curOrgId, 
        orgIdString, "车辆管理", "维护", "b.createdOrg", "b.createdEmp") + " ) ";
    whereSQL = " where " + whereSql + " and b.domainId=" + domainId + " group by  b.id,b.num,b.model,b.name,b.motorMan ";
    VoitureFeeActionForm voitureFeeActionForm = (VoitureFeeActionForm)actionForm;
    VoitureManagerSecondBD bd = new VoitureManagerSecondBD();
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    if ("list".equals(action)) {
      tag = "list";
    } else if ("addwindow".equals(action)) {
      tag = "addwindow";
    } else if ("add".equals(action)) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
      VoitureFeePO paraPO = new VoitureFeePO();
      paraPO.setIsMaintain(voitureFeeActionForm.getIsMaintain());
      if (voitureFeeActionForm.getAccidentFee().toString().length() != 0) {
        paraPO.setAccidentFee(Double.valueOf(voitureFeeActionForm
              .getAccidentFee()));
      } else {
        paraPO.setAccidentFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getBrandFee().toString().length() != 0) {
        paraPO.setBrandFee(Double.valueOf(voitureFeeActionForm
              .getBrandFee()));
      } else {
        paraPO.setBrandFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getFixFee().toString().length() != 0) {
        paraPO.setFixFee(Double.valueOf(voitureFeeActionForm.getFixFee()));
      } else {
        paraPO.setFixFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getInsureFee().toString().length() != 0) {
        paraPO.setInsureFee(Double.valueOf(voitureFeeActionForm
              .getInsureFee()));
      } else {
        paraPO.setInsureFee(new Double(0.0D));
      } 
      try {
        paraPO.setMaintainTime(formatter.parse(voitureFeeActionForm
              .getMaintainTime()));
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (voitureFeeActionForm.getOilCost().toString().length() != 0) {
        paraPO.setOilCost(Double.valueOf(voitureFeeActionForm
              .getOilCost()));
      } else {
        paraPO.setOilCost(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getOilFee().toString().length() != 0) {
        paraPO.setOilFee(Double.valueOf(voitureFeeActionForm.getOilFee()));
      } else {
        paraPO.setOilFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getOtherFee().toString().length() != 0) {
        paraPO.setOtherFee(Double.valueOf(voitureFeeActionForm
              .getOtherFee()));
      } else {
        paraPO.setOtherFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getPurchaseTax().toString().length() != 0) {
        paraPO.setPurchaseTax(Double.valueOf(voitureFeeActionForm
              .getPurchaseTax()));
      } else {
        paraPO.setPurchaseTax(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getRoadFee().toString().length() != 0) {
        paraPO.setRoadFee(Double.valueOf(voitureFeeActionForm
              .getRoadFee()));
      } else {
        paraPO.setRoadFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getTaxFee().toString().length() != 0) {
        paraPO.setTaxFee(Double.valueOf(voitureFeeActionForm.getTaxFee()));
      } else {
        paraPO.setTaxFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getWashFee().toString().length() != 0) {
        paraPO.setWashFee(Double.valueOf(voitureFeeActionForm
              .getWashFee()));
      } else {
        paraPO.setWashFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getYearSensorFee().toString().length() != 0) {
        paraPO.setYearSensorFee(Double.valueOf(voitureFeeActionForm
              .getYearSensorFee()));
      } else {
        paraPO.setYearSensorFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getYearTicketFee().toString().length() != 0) {
        paraPO.setYearTicketFee(Double.valueOf(voitureFeeActionForm
              .getYearTicketFee()));
      } else {
        paraPO.setYearTicketFee(new Double(0.0D));
      } 
      if (voitureFeeActionForm.getMonthFee().toString().length() != 0) {
        paraPO.setMonthFee(Double.valueOf(voitureFeeActionForm.getMonthFee()));
      } else {
        paraPO.setMonthFee(new Double(0.0D));
      } 
      paraPO.setDomainId(Long.valueOf(domainId));
      paraPO.setRemark(voitureFeeActionForm.getRemark());
      paraPO.setJsy(voitureFeeActionForm.getJsy());
      paraPO.setFwld(voitureFeeActionForm.getFwld());
      paraPO.setSylc(voitureFeeActionForm.getSylc());
      paraPO.setBylc(voitureFeeActionForm.getBylc());
      paraPO.setXslc(voitureFeeActionForm.getXslc());
      paraPO.setYy(voitureFeeActionForm.getYy());
      paraPO.setBglhy(voitureFeeActionForm.getBglhy());
      result = bd.voitureFeeAdd(paraPO, 
          Long.valueOf(httpServletRequest
            .getParameter("voitureId").toString()));
      httpServletRequest.setAttribute("result", result);
      tag = "addwindow";
    } else if ("delBatch".equals(action)) {
      if (httpServletRequest.getParameter("ids") != null && 
        !httpServletRequest.getParameter("ids").toString().equals(""))
        bd.delVoitureFeeBatch(httpServletRequest.getParameter("ids")
            .toString()); 
      tag = "listDetail";
    } else if ("delVoitureFee".equals(action)) {
      if (httpServletRequest.getParameter("voitureFeeId") != null && 
        
        !httpServletRequest.getParameter("voitureFeeId").toString().equals(""))
        bd.delVoitureFee(Long.valueOf(httpServletRequest.getParameter(
                "voitureFeeId").toString())); 
      tag = "listDetail";
    } else if ("modify".equals(action)) {
      if (httpServletRequest.getParameter("voitureFeeId") != null && 
        
        !httpServletRequest.getParameter("voitureFeeId").toString().equals("")) {
        List<Object[]> modifyVoitureFeeList = bd.listModifyVoitureFee(
            Long.valueOf(
              httpServletRequest.getParameter("voitureFeeId")
              .toString()));
        httpServletRequest.setAttribute("modifyVoitureFeeList", 
            modifyVoitureFeeList);
        Object[] obj = modifyVoitureFeeList.get(0);
        voitureFeeActionForm.setIsMaintain(obj[17].toString());
        tag = "modify";
      } 
    } else if ("update".equals(action)) {
      if (httpServletRequest.getParameter("voitureFeeId") != null && 
        
        !httpServletRequest.getParameter("voitureFeeId").toString().equals("")) {
        SimpleDateFormat formatter = new SimpleDateFormat(
            
            "yyyy/MM/dd");
        VoitureFeePO paraPO = new VoitureFeePO();
        paraPO.setIsMaintain(voitureFeeActionForm.getIsMaintain());
        paraPO.setId(Long.valueOf(httpServletRequest.getParameter(
                "voitureFeeId").toString()));
        if (voitureFeeActionForm.getAccidentFee().toString().length() != 0) {
          paraPO.setAccidentFee(Double.valueOf(voitureFeeActionForm
                .getAccidentFee()));
        } else {
          paraPO.setAccidentFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getBrandFee().toString().length() != 0) {
          paraPO.setBrandFee(Double.valueOf(voitureFeeActionForm
                .getBrandFee()));
        } else {
          paraPO.setBrandFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getFixFee().toString().length() != 0) {
          paraPO.setFixFee(Double.valueOf(voitureFeeActionForm
                .getFixFee()));
        } else {
          paraPO.setFixFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getInsureFee().toString().length() != 0) {
          paraPO.setInsureFee(Double.valueOf(voitureFeeActionForm
                .getInsureFee()));
        } else {
          paraPO.setInsureFee(new Double(0.0D));
        } 
        try {
          paraPO.setMaintainTime(formatter.parse(voitureFeeActionForm
                .getMaintainTime()));
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (voitureFeeActionForm.getOilCost().toString().length() != 0) {
          paraPO.setOilCost(Double.valueOf(voitureFeeActionForm
                .getOilCost()));
        } else {
          paraPO.setOilCost(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getOilFee().toString().length() != 0) {
          paraPO.setOilFee(Double.valueOf(voitureFeeActionForm
                .getOilFee()));
        } else {
          paraPO.setOilFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getOtherFee().toString().length() != 0) {
          paraPO.setOtherFee(Double.valueOf(voitureFeeActionForm
                .getOtherFee()));
        } else {
          paraPO.setOtherFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getPurchaseTax().toString().length() != 0) {
          paraPO.setPurchaseTax(Double.valueOf(voitureFeeActionForm
                .getPurchaseTax()));
        } else {
          paraPO.setPurchaseTax(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getRoadFee().toString().length() != 0) {
          paraPO.setRoadFee(Double.valueOf(voitureFeeActionForm
                .getRoadFee()));
        } else {
          paraPO.setRoadFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getTaxFee().toString().length() != 0) {
          paraPO.setTaxFee(Double.valueOf(voitureFeeActionForm
                .getTaxFee()));
        } else {
          paraPO.setTaxFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getWashFee().toString().length() != 0) {
          paraPO.setWashFee(Double.valueOf(voitureFeeActionForm
                .getWashFee()));
        } else {
          paraPO.setWashFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getYearSensorFee().toString().length() != 0) {
          paraPO.setYearSensorFee(Double.valueOf(voitureFeeActionForm
                .getYearSensorFee()));
        } else {
          paraPO.setYearSensorFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getYearTicketFee().toString().length() != 0) {
          paraPO.setYearTicketFee(Double.valueOf(voitureFeeActionForm
                .getYearTicketFee()));
        } else {
          paraPO.setYearTicketFee(new Double(0.0D));
        } 
        if (voitureFeeActionForm.getMonthFee().toString().length() != 0) {
          paraPO.setMonthFee(Double.valueOf(voitureFeeActionForm.getMonthFee()));
        } else {
          paraPO.setMonthFee(new Double(0.0D));
        } 
        paraPO.setRemark(voitureFeeActionForm.getRemark());
        paraPO.setJsy(voitureFeeActionForm.getJsy());
        paraPO.setFwld(voitureFeeActionForm.getFwld());
        paraPO.setSylc(voitureFeeActionForm.getSylc());
        paraPO.setBylc(voitureFeeActionForm.getBylc());
        paraPO.setXslc(voitureFeeActionForm.getXslc());
        paraPO.setYy(voitureFeeActionForm.getYy());
        paraPO.setBglhy(voitureFeeActionForm.getBglhy());
        update_info = bd.updateVoitureFee(paraPO, 
            Long.valueOf(
              httpServletRequest
              .getParameter("voitureId").toString()));
        httpServletRequest.setAttribute("update_info", update_info);
        List modifyVoitureFeeList = bd.listModifyVoitureFee(
            Long.valueOf(
              httpServletRequest.getParameter("voitureFeeId")
              .toString()));
        httpServletRequest.setAttribute("modifyVoitureFeeList", 
            modifyVoitureFeeList);
        tag = "modify";
      } 
    } else if ("listDetail".equals(action)) {
      tag = "listDetail";
    } 
    if (tag.equals("listDetail")) {
      Long voitureId = Long.valueOf(httpServletRequest.getParameter(
            "voitureId").toString());
      ManagerService mbd = new ManagerService();
      whereSql = "where ";
      boolean voitureMtRight = mbd.hasRightTypeName(curUserId, "车辆管理", 
          "维护");
      httpServletRequest.setAttribute("voitureMtRight", 
          String.valueOf(voitureMtRight));
      boolean voitureAddRight = mbd.hasRightTypeName(curUserId, "车辆管理", 
          "维护");
      httpServletRequest.setAttribute("voitureAddRight", 
          String.valueOf(voitureAddRight));
      viewSQL = "select a.id,a.maintainTime,b.name,b.num,a.isMaintain ";
      fromSQL = 
        "from com.js.oa.routine.voiture.po.VoitureFeePO a join a.voiturePO b ";
      whereSQL = " where b.id='" + voitureId + 
        "' and a.domainId=" + domainId + " order by a.maintainTime desc";
      listVoitureFeeDetail(httpServletRequest, viewSQL, fromSQL, whereSQL);
    } 
    if (tag.equals("list"))
      list(httpServletRequest, viewSQL, fromSQL, whereSQL); 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
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
    httpServletRequest.setAttribute("voitureFeeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,voitureId");
  }
  
  private void listVoitureFeeDetail(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
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
    httpServletRequest.setAttribute("voitureFeeDetailList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,voitureId,name,num");
  }
}
