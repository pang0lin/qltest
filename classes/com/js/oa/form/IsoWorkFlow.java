package com.js.oa.form;

import com.js.oa.info.isodoc.po.IsoBorrowUserPO;
import com.js.oa.info.isodoc.service.IsoDocBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.vo.WorkVO;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IsoWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) throws ParseException {
    if (httpServletRequest.getParameter("resubmitWorkId") != null && !"".equals(httpServletRequest.getParameter("resubmitWorkId"))) {
      String processId = httpServletRequest.getParameter("processId");
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      WorkVO workVO = new WorkVO();
      workVO.setProcessId(Long.valueOf(processId));
      workVO.setTableId(Long.valueOf(tableId));
      workVO.setRecordId(Long.valueOf(recordId));
      WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
      workFlowButtonBD.deleteWork(workVO);
      delete(httpServletRequest);
    } 
    IsoBorrowUserPO po = setPO(httpServletRequest);
    IsoDocBD bd = new IsoDocBD();
    Long result = bd.saveBorrowPO(po);
    return result;
  }
  
  public Long update(HttpServletRequest httpServletRequest) throws ParseException {
    IsoBorrowUserPO po = setPO(httpServletRequest);
    Long result = (new IsoDocBD()).updateBorrowPO(po);
    return result;
  }
  
  public void complete(HttpServletRequest httpServletRequest) {
    IsoBorrowUserPO po = setPO(httpServletRequest);
    po.setBorrowStatus("1");
    (new IsoDocBD()).updateBorrowPO(po);
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    IsoDocBD bd = new IsoDocBD();
    bd.deleteBorrow(httpServletRequest.getParameter("record"));
  }
  
  public void back(HttpServletRequest httpServletRequest) {
    IsoDocBD bd = new IsoDocBD();
    bd.setBorrowStatus(httpServletRequest.getParameter("isoBorrowUserId"), "2");
  }
  
  private IsoBorrowUserPO setPO(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = session.getAttribute("domainId").toString();
    IsoBorrowUserPO po = new IsoBorrowUserPO();
    po.setUserId(new Long(httpServletRequest.getParameter("userId")));
    po.setUserName(httpServletRequest.getParameter("userName"));
    po.setOrgId(new Long(httpServletRequest.getParameter("orgId")));
    po.setOrgName(httpServletRequest.getParameter("orgName"));
    po.setInforChannelId(new Long(httpServletRequest.getParameter("inforChannelId")));
    po.setInforChannelName(httpServletRequest.getParameter("inforChannelName"));
    po.setInformationId(new Long(httpServletRequest.getParameter("informationId")));
    po.setInformationName(httpServletRequest.getParameter("informationName"));
    po.setDomainId(new Long(domainId));
    if (httpServletRequest.getParameter("borrowBeginTime") != null && !httpServletRequest.getParameter("borrowBeginTime").toString().equals("")) {
      po.setBorrowBeginTime(new Date(httpServletRequest.getParameter("borrowBeginTime")));
      po.setBorrowEndTime(new Date(httpServletRequest.getParameter("borrowEndTime")));
    } 
    if (httpServletRequest.getParameter("borrowReason") != null && !httpServletRequest.getParameter("borrowReason").toString().equals("")) {
      po.setBorrowReason(httpServletRequest.getParameter("borrowReason"));
    } else {
      po.setBorrowReason("");
    } 
    po.setBorrowStatus("0");
    if (httpServletRequest.getParameter("isoBorrowUserId") != null && !httpServletRequest.getParameter("isoBorrowUserId").toString().equals(""))
      po.setIsoBorrowUserId(new Long(httpServletRequest.getParameter("isoBorrowUserId"))); 
    if (httpServletRequest.getParameter("documentNO") != null) {
      po.setDocumentNO(httpServletRequest.getParameter("documentNO"));
    } else {
      po.setDocumentNO("");
    } 
    return po;
  }
}
