package com.js.oa.form;

import com.js.oa.archives.po.ArchivesBorrowPO;
import com.js.oa.archives.service.ArchivesBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ArchivesFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    ArchivesBD archivesBD = new ArchivesBD();
    Long fileId = Long.valueOf(httpServletRequest.getParameter("fileId"));
    Long Id = archivesBD.addArchivesBorrowFile(setPO(httpServletRequest), 
        fileId);
    if ("-1".equals(Id))
      Id = Long.valueOf("-1"); 
    return Id;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    ArchivesBD archivesBD = new ArchivesBD();
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    ArchivesBorrowPO archivesBorrowPO = new ArchivesBorrowPO();
    archivesBorrowPO = setPO(httpServletRequest);
    archivesBorrowPO.setBorrowId(borrowId);
    boolean result = archivesBD.modiBorrowAuditingFile(archivesBorrowPO, 
        borrowId);
    if (result) {
      borrowId = borrowId;
    } else {
      borrowId = Long.valueOf("-1");
    } 
    return borrowId;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    ArchivesBD archivesBD = new ArchivesBD();
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    ArchivesBorrowPO archivesBorrowPO = new ArchivesBorrowPO();
    archivesBorrowPO = setPO(httpServletRequest);
    archivesBorrowPO.setBorrowId(borrowId);
    archivesBorrowPO.setStatus(Integer.valueOf("2"));
    boolean result = archivesBD.modiBorrowAuditingFile(archivesBorrowPO, 
        borrowId);
    if (result) {
      borrowId = borrowId;
    } else {
      borrowId = Long.valueOf("-1");
    } 
    return borrowId;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    ArchivesBD archivesBD = new ArchivesBD();
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    boolean result = archivesBD.untreadBorrowAuditingFile(borrowId);
    if (result) {
      borrowId = borrowId;
    } else {
      borrowId = Long.valueOf("-1");
    } 
    return borrowId;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    ArchivesBD archivesBD = new ArchivesBD();
    Long borrowId = Long.valueOf(httpServletRequest.getParameter("borrowId"));
    boolean result = archivesBD.untreadBorrowAuditingFile(borrowId);
    if (result) {
      borrowId = borrowId;
    } else {
      borrowId = Long.valueOf("-1");
    } 
    return borrowId;
  }
  
  private ArchivesBorrowPO setPO(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    ArchivesBorrowPO po = new ArchivesBorrowPO();
    po.setEmpId(Long.valueOf(request.getParameter("principal")));
    po.setEmpName(request.getParameter("principalName"));
    po.setOrgId(Long.valueOf(request.getParameter("orgId")));
    po.setOrgName(request.getParameter("orgName"));
    po.setBorrowCount(Integer.valueOf(request.getParameter("borrowCount")));
    po.setBorrowDate(new Date(request.getParameter("borrowDate")));
    po.setBorrowIntent(request.getParameter("borrowIntent"));
    po.setIsReturned(new Integer(0));
    po.setStatus(new Integer(1));
    po.setDomainId(domainId);
    return po;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("borrowId") != null && 
      !"".equals(httpServletRequest.getParameter("borrowId"))) {
      ArchivesBD archivesBD = new ArchivesBD();
      Long borrowId = Long.valueOf(httpServletRequest.getParameter(
            "borrowId"));
      archivesBD.delBorrowAuditingFile(borrowId);
    } 
  }
}
