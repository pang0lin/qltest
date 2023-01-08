package com.js.oa.archives.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesBorrowPO implements Serializable {
  private Long borrowId;
  
  private Long fileId;
  
  private Long empId;
  
  private String empName;
  
  private Long orgId;
  
  private String orgName;
  
  private String borrowIntent;
  
  private Date borrowDate = null;
  
  private Date returnDate = null;
  
  private Integer isReturned;
  
  private Integer borrowCount;
  
  private Integer status;
  
  private ArchivesFilePO archivesFile;
  
  private String domainId;
  
  public ArchivesBorrowPO(ArchivesFilePO archivesFile, Long borrowId, Long fileId, Long empId, String empName, Long orgId, String orgName, String borrowIntent, Date borrowDate, Date returnDate, Integer isReturned, Integer borrowCount, Integer status) {
    this.archivesFile = archivesFile;
    this.borrowId = borrowId;
    this.fileId = fileId;
    this.empId = empId;
    this.empName = empName;
    this.orgId = orgId;
    this.orgName = orgName;
    this.borrowIntent = borrowIntent;
    this.borrowDate = borrowDate;
    this.returnDate = returnDate;
    this.isReturned = isReturned;
    this.borrowCount = borrowCount;
    this.status = status;
  }
  
  public ArchivesBorrowPO() {}
  
  public Date getBorrowDate() {
    return this.borrowDate;
  }
  
  public void setBorrowDate(Date borrowDate) {
    this.borrowDate = borrowDate;
  }
  
  public Long getBorrowId() {
    return this.borrowId;
  }
  
  public void setBorrowId(Long borrowId) {
    this.borrowId = borrowId;
  }
  
  public String getBorrowIntent() {
    return this.borrowIntent;
  }
  
  public void setBorrowIntent(String borrowIntent) {
    this.borrowIntent = borrowIntent;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Long getFileId() {
    return this.fileId;
  }
  
  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }
  
  public Integer getIsReturned() {
    return this.isReturned;
  }
  
  public void setIsReturned(Integer isReturned) {
    this.isReturned = isReturned;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public Date getReturnDate() {
    return this.returnDate;
  }
  
  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }
  
  public ArchivesFilePO getArchivesFile() {
    return this.archivesFile;
  }
  
  public void setArchivesFile(ArchivesFilePO archivesFile) {
    this.archivesFile = archivesFile;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("borrowId", getBorrowId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesBorrowPO))
      return false; 
    ArchivesBorrowPO castOther = (ArchivesBorrowPO)other;
    return (new EqualsBuilder())
      .append(getBorrowId(), castOther.getBorrowId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getBorrowId())
      .toHashCode();
  }
  
  public Integer getBorrowCount() {
    return this.borrowCount;
  }
  
  public void setBorrowCount(Integer borrowCount) {
    this.borrowCount = borrowCount;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
