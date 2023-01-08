package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovReceiveFilePO implements Serializable {
  private long id;
  
  private String receiveFileFileNo;
  
  private String receiveFileSendFileUnit;
  
  private String receiveFileFileNumber;
  
  private String receiveFileTitle;
  
  private String receiveFileSafetyGrade;
  
  private Date receiveFileReceiveDate = null;
  
  private short receiveFileQuantity;
  
  private String receiveFileSettleComment;
  
  private String receiveFileLeaderComment;
  
  private String receiveFileDoComment;
  
  private String accessoryNameFile;
  
  private String accessorySaveNameFile;
  
  private String accessoryName;
  
  private String accessorySaveName;
  
  private byte receiveFileIsEnd;
  
  private Date receiveFileEndDate = null;
  
  private long receiveFileDoDepart;
  
  private String receiveFileTogetherDoDepart;
  
  private String receiveFileSendLeaderRead;
  
  private String receiveFileSendLeaderCheck;
  
  private String receiveFileDoPerson;
  
  private String receiveFileDoDepartNm;
  
  private String receiveFileTogetherDoDepartNm;
  
  private String receiveFileSendLeaderReaderNm;
  
  private String receiveFileSendLeaderCheckNm;
  
  private String receiveFileMemo;
  
  private Date receiveFileFinishDate = null;
  
  private String receiveFileLink;
  
  private long createdEmp;
  
  private long createdOrg;
  
  private byte receiveFileIsFlowMode;
  
  private String receiveFileTransPerson;
  
  private String receiveFileTransPersonNm;
  
  private String receiveFileSettleLeaderComment;
  
  private String receiveFileTransAuditComment;
  
  private Date createdTime = null;
  
  private long receiveFileFileNoCount;
  
  private long createdTimeYear;
  
  private byte receiveFileStatus;
  
  private String lwh;
  
  private String receiveFileType;
  
  private String zbstatus;
  
  private String zjkySeq;
  
  private String zjkyType;
  
  private String zjkykeepTerm;
  
  private Long seqId;
  
  private Long tableId;
  
  private String receiveTextField1;
  
  private String receiveTextField2;
  
  private String receiveDropDownSelect1;
  
  private String receiveDropDownSelect2;
  
  private String receiveMutliTextField1;
  
  private String receiveFieldSelectMoreEmp;
  
  private String isDraf;
  
  private int receiveSyncForward;
  
  private String receiveAuthorName;
  
  private Long processId;
  
  private String updateTimeStr;
  
  private Integer isSubFlow;
  
  private String field1;
  
  private String field2;
  
  private String field3;
  
  private String field4;
  
  private String field5;
  
  private String field6;
  
  private String field7;
  
  private String field8;
  
  private String field9;
  
  private String field10;
  
  private Long sendFileId;
  
  private String sendFileLink;
  
  private String sendFileTitle;
  
  private Integer thirdDossier;
  
  private String domainId;
  
  private String fileSendCheckId;
  
  private String fileSendCheckLink;
  
  private String exchangeFileId;
  
  private String exchangeFileLink;
  
  private String exchangeFileTitle;
  
  private String field16;
  
  private String field17;
  
  private String field18;
  
  private String field19;
  
  private String field20;
  
  private String field21;
  
  private String field22;
  
  private String field23;
  
  private String field24;
  
  private String field25;
  
  private String field26;
  
  public String getUpdateTimeStr() {
    return this.updateTimeStr;
  }
  
  public void setUpdateTimeStr(String updateTimeStr) {
    this.updateTimeStr = updateTimeStr;
  }
  
  public Integer getIsSubFlow() {
    return this.isSubFlow;
  }
  
  public void setIsSubFlow(Integer isSubFlow) {
    this.isSubFlow = isSubFlow;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public String getIsDraf() {
    return this.isDraf;
  }
  
  public void setIsDraf(String isDraf) {
    this.isDraf = isDraf;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getReceiveFileFileNo() {
    return this.receiveFileFileNo;
  }
  
  public void setReceiveFileFileNo(String receiveFileFileNo) {
    this.receiveFileFileNo = receiveFileFileNo;
  }
  
  public String getReceiveFileSendFileUnit() {
    return this.receiveFileSendFileUnit;
  }
  
  public void setReceiveFileSendFileUnit(String receiveFileSendFileUnit) {
    this.receiveFileSendFileUnit = receiveFileSendFileUnit;
  }
  
  public String getReceiveFileFileNumber() {
    return this.receiveFileFileNumber;
  }
  
  public void setReceiveFileFileNumber(String receiveFileFileNumber) {
    this.receiveFileFileNumber = receiveFileFileNumber;
  }
  
  public String getReceiveFileTitle() {
    return this.receiveFileTitle;
  }
  
  public void setReceiveFileTitle(String receiveFileTitle) {
    this.receiveFileTitle = receiveFileTitle;
  }
  
  public String getReceiveFileSafetyGrade() {
    return this.receiveFileSafetyGrade;
  }
  
  public void setReceiveFileSafetyGrade(String receiveFileSafetyGrade) {
    this.receiveFileSafetyGrade = receiveFileSafetyGrade;
  }
  
  public Date getReceiveFileReceiveDate() {
    return this.receiveFileReceiveDate;
  }
  
  public void setReceiveFileReceiveDate(Date receiveFileReceiveDate) {
    this.receiveFileReceiveDate = receiveFileReceiveDate;
  }
  
  public short getReceiveFileQuantity() {
    return this.receiveFileQuantity;
  }
  
  public void setReceiveFileQuantity(short receiveFileQuantity) {
    this.receiveFileQuantity = receiveFileQuantity;
  }
  
  public String getReceiveFileSettleComment() {
    return this.receiveFileSettleComment;
  }
  
  public void setReceiveFileSettleComment(String receiveFileSettleComment) {
    this.receiveFileSettleComment = receiveFileSettleComment;
  }
  
  public Date getReceiveFileFinishDate() {
    return this.receiveFileFinishDate;
  }
  
  public void setReceiveFileFinishDate(Date receiveFileFinishDate) {
    this.receiveFileFinishDate = receiveFileFinishDate;
  }
  
  public String getReceiveFileLeaderComment() {
    return this.receiveFileLeaderComment;
  }
  
  public void setReceiveFileLeaderComment(String receiveFileLeaderComment) {
    this.receiveFileLeaderComment = receiveFileLeaderComment;
  }
  
  public String getReceiveFileDoComment() {
    return this.receiveFileDoComment;
  }
  
  public void setReceiveFileDoComment(String receiveFileDoComment) {
    this.receiveFileDoComment = receiveFileDoComment;
  }
  
  public String getAccessoryNameFile() {
    return this.accessoryNameFile;
  }
  
  public void setAccessoryNameFile(String accessoryNameFile) {
    this.accessoryNameFile = accessoryNameFile;
  }
  
  public String getAccessorySaveNameFile() {
    return this.accessorySaveNameFile;
  }
  
  public void setAccessorySaveNameFile(String accessorySaveNameFile) {
    this.accessorySaveNameFile = accessorySaveNameFile;
  }
  
  public String getAccessoryName() {
    return this.accessoryName;
  }
  
  public void setAccessoryName(String accessoryName) {
    this.accessoryName = accessoryName;
  }
  
  public String getAccessorySaveName() {
    return this.accessorySaveName;
  }
  
  public void setAccessorySaveName(String accessorySaveName) {
    this.accessorySaveName = accessorySaveName;
  }
  
  public byte getReceiveFileIsEnd() {
    return this.receiveFileIsEnd;
  }
  
  public void setReceiveFileIsEnd(byte receiveFileIsEnd) {
    this.receiveFileIsEnd = receiveFileIsEnd;
  }
  
  public Date getReceiveFileEndDate() {
    return this.receiveFileEndDate;
  }
  
  public void setReceiveFileEndDate(Date receiveFileEndDate) {
    this.receiveFileEndDate = receiveFileEndDate;
  }
  
  public long getReceiveFileDoDepart() {
    return this.receiveFileDoDepart;
  }
  
  public void setReceiveFileDoDepart(long receiveFileDoDepart) {
    this.receiveFileDoDepart = receiveFileDoDepart;
  }
  
  public String getReceiveFileTogetherDoDepart() {
    return this.receiveFileTogetherDoDepart;
  }
  
  public void setReceiveFileTogetherDoDepart(String receiveFileTogetherDoDepart) {
    this.receiveFileTogetherDoDepart = receiveFileTogetherDoDepart;
  }
  
  public String getReceiveFileSendLeaderRead() {
    return this.receiveFileSendLeaderRead;
  }
  
  public void setReceiveFileSendLeaderRead(String receiveFileSendLeaderRead) {
    this.receiveFileSendLeaderRead = receiveFileSendLeaderRead;
  }
  
  public String getReceiveFileSendLeaderCheck() {
    return this.receiveFileSendLeaderCheck;
  }
  
  public void setReceiveFileSendLeaderCheck(String receiveFileSendLeaderCheck) {
    this.receiveFileSendLeaderCheck = receiveFileSendLeaderCheck;
  }
  
  public String getReceiveFileDoPerson() {
    return this.receiveFileDoPerson;
  }
  
  public void setReceiveFileDoPerson(String receiveFileDoPerson) {
    this.receiveFileDoPerson = receiveFileDoPerson;
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("id", getId()).toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovReceiveFilePO))
      return false; 
    GovReceiveFilePO castOther = (GovReceiveFilePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getReceiveFileDoDepartNm() {
    return this.receiveFileDoDepartNm;
  }
  
  public void setReceiveFileDoDepartNm(String receiveFileDoDepartNm) {
    this.receiveFileDoDepartNm = receiveFileDoDepartNm;
  }
  
  public String getReceiveFileTogetherDoDepartNm() {
    return this.receiveFileTogetherDoDepartNm;
  }
  
  public void setReceiveFileTogetherDoDepartNm(String receiveFileTogetherDoDepartNm) {
    this.receiveFileTogetherDoDepartNm = receiveFileTogetherDoDepartNm;
  }
  
  public String getReceiveFileSendLeaderReaderNm() {
    return this.receiveFileSendLeaderReaderNm;
  }
  
  public void setReceiveFileSendLeaderReaderNm(String receiveFileSendLeaderReaderNm) {
    this.receiveFileSendLeaderReaderNm = receiveFileSendLeaderReaderNm;
  }
  
  public String getReceiveFileSendLeaderCheckNm() {
    return this.receiveFileSendLeaderCheckNm;
  }
  
  public void setReceiveFileSendLeaderCheckNm(String receiveFileSendLeaderCheckNm) {
    this.receiveFileSendLeaderCheckNm = receiveFileSendLeaderCheckNm;
  }
  
  public String getReceiveFileMemo() {
    return this.receiveFileMemo;
  }
  
  public void setReceiveFileMemo(String receiveFileMemo) {
    this.receiveFileMemo = receiveFileMemo;
  }
  
  public String getReceiveFileLink() {
    return this.receiveFileLink;
  }
  
  public void setReceiveFileLink(String receiveFileLink) {
    this.receiveFileLink = receiveFileLink;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setReceiveFileIsFlowMode(byte receiveFileIsFlowMode) {
    this.receiveFileIsFlowMode = receiveFileIsFlowMode;
  }
  
  public byte getReceiveFileIsFlowMode() {
    return this.receiveFileIsFlowMode;
  }
  
  public String getReceiveFileTransPerson() {
    return this.receiveFileTransPerson;
  }
  
  public void setReceiveFileTransPerson(String receiveFileTransPerson) {
    this.receiveFileTransPerson = receiveFileTransPerson;
  }
  
  public String getReceiveFileTransPersonNm() {
    return this.receiveFileTransPersonNm;
  }
  
  public void setReceiveFileTransPersonNm(String receiveFileTransPersonNm) {
    this.receiveFileTransPersonNm = receiveFileTransPersonNm;
  }
  
  public String getReceiveFileSettleLeaderComment() {
    return this.receiveFileSettleLeaderComment;
  }
  
  public void setReceiveFileSettleLeaderComment(String receiveFileSettleLeaderComment) {
    this.receiveFileSettleLeaderComment = receiveFileSettleLeaderComment;
  }
  
  public String getReceiveFileTransAuditComment() {
    return this.receiveFileTransAuditComment;
  }
  
  public void setReceiveFileTransAuditComment(String receiveFileTransAuditComment) {
    this.receiveFileTransAuditComment = receiveFileTransAuditComment;
  }
  
  public Date getCreatedTime() {
    return this.createdTime;
  }
  
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
  
  public long getReceiveFileFileNoCount() {
    return this.receiveFileFileNoCount;
  }
  
  public void setReceiveFileFileNoCount(long receiveFileFileNoCount) {
    this.receiveFileFileNoCount = receiveFileFileNoCount;
  }
  
  public long getCreatedTimeYear() {
    return this.createdTimeYear;
  }
  
  public void setCreatedTimeYear(long createdTimeYear) {
    this.createdTimeYear = createdTimeYear;
  }
  
  public byte getReceiveFileStatus() {
    return this.receiveFileStatus;
  }
  
  public void setReceiveFileStatus(byte receiveFileStatus) {
    this.receiveFileStatus = receiveFileStatus;
  }
  
  public String getLwh() {
    return this.lwh;
  }
  
  public void setLwh(String lwh) {
    this.lwh = lwh;
  }
  
  public String getReceiveFileType() {
    return this.receiveFileType;
  }
  
  public void setReceiveFileType(String receiveFileType) {
    this.receiveFileType = receiveFileType;
  }
  
  public String getZbstatus() {
    return this.zbstatus;
  }
  
  public void setZbstatus(String zbstatus) {
    this.zbstatus = zbstatus;
  }
  
  public String getField1() {
    return this.field1;
  }
  
  public void setField1(String field1) {
    this.field1 = field1;
  }
  
  public String getField2() {
    return this.field2;
  }
  
  public void setField2(String field2) {
    this.field2 = field2;
  }
  
  public String getField3() {
    return this.field3;
  }
  
  public void setField3(String field3) {
    this.field3 = field3;
  }
  
  public String getField4() {
    return this.field4;
  }
  
  public void setField4(String field4) {
    this.field4 = field4;
  }
  
  public String getField5() {
    return this.field5;
  }
  
  public void setField5(String field5) {
    this.field5 = field5;
  }
  
  public String getField6() {
    return this.field6;
  }
  
  public void setField6(String field6) {
    this.field6 = field6;
  }
  
  public String getField7() {
    return this.field7;
  }
  
  public void setField7(String field7) {
    this.field7 = field7;
  }
  
  public String getField8() {
    return this.field8;
  }
  
  public void setField8(String field8) {
    this.field8 = field8;
  }
  
  public String getField9() {
    return this.field9;
  }
  
  public void setField9(String field9) {
    this.field9 = field9;
  }
  
  public String getField10() {
    return this.field10;
  }
  
  public void setField10(String field10) {
    this.field10 = field10;
  }
  
  public Long getSendFileId() {
    return this.sendFileId;
  }
  
  public void setSendFileId(Long sendFileId) {
    this.sendFileId = sendFileId;
  }
  
  public String getSendFileLink() {
    return this.sendFileLink;
  }
  
  public void setSendFileLink(String sendFileLink) {
    this.sendFileLink = sendFileLink;
  }
  
  public String getSendFileTitle() {
    return this.sendFileTitle;
  }
  
  public void setSendFileTitle(String sendFileTitle) {
    this.sendFileTitle = sendFileTitle;
  }
  
  public Integer getThirdDossier() {
    return this.thirdDossier;
  }
  
  public void setThirdDossier(Integer thirdDossier) {
    this.thirdDossier = thirdDossier;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getFileSendCheckId() {
    return this.fileSendCheckId;
  }
  
  public void setFileSendCheckId(String fileSendCheckId) {
    this.fileSendCheckId = fileSendCheckId;
  }
  
  public String getFileSendCheckLink() {
    return this.fileSendCheckLink;
  }
  
  public String getZjkySeq() {
    return this.zjkySeq;
  }
  
  public String getZjkyType() {
    return this.zjkyType;
  }
  
  public String getZjkykeepTerm() {
    return this.zjkykeepTerm;
  }
  
  public void setFileSendCheckLink(String fileSendCheckLink) {
    this.fileSendCheckLink = fileSendCheckLink;
  }
  
  public void setZjkySeq(String zjkySeq) {
    this.zjkySeq = zjkySeq;
  }
  
  public void setZjkyType(String zjkyType) {
    this.zjkyType = zjkyType;
  }
  
  public void setZjkykeepTerm(String zjkykeepTerm) {
    this.zjkykeepTerm = zjkykeepTerm;
  }
  
  public String getExchangeFileId() {
    return this.exchangeFileId;
  }
  
  public void setExchangeFileId(String exchangeFileId) {
    this.exchangeFileId = exchangeFileId;
  }
  
  public String getExchangeFileLink() {
    return this.exchangeFileLink;
  }
  
  public void setExchangeFileLink(String exchangeFileLink) {
    this.exchangeFileLink = exchangeFileLink;
  }
  
  public String getExchangeFileTitle() {
    return this.exchangeFileTitle;
  }
  
  public Long getSeqId() {
    return this.seqId;
  }
  
  public void setExchangeFileTitle(String exchangeFileTitle) {
    this.exchangeFileTitle = exchangeFileTitle;
  }
  
  public void setSeqId(Long seqId) {
    this.seqId = seqId;
  }
  
  public Long getTableId() {
    return this.tableId;
  }
  
  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }
  
  public String getReceiveTextField1() {
    return this.receiveTextField1;
  }
  
  public void setReceiveTextField1(String receiveTextField1) {
    this.receiveTextField1 = receiveTextField1;
  }
  
  public String getReceiveTextField2() {
    return this.receiveTextField2;
  }
  
  public void setReceiveTextField2(String receiveTextField2) {
    this.receiveTextField2 = receiveTextField2;
  }
  
  public String getReceiveDropDownSelect1() {
    return this.receiveDropDownSelect1;
  }
  
  public void setReceiveDropDownSelect1(String receiveDropDownSelect1) {
    this.receiveDropDownSelect1 = receiveDropDownSelect1;
  }
  
  public String getReceiveDropDownSelect2() {
    return this.receiveDropDownSelect2;
  }
  
  public void setReceiveDropDownSelect2(String receiveDropDownSelect2) {
    this.receiveDropDownSelect2 = receiveDropDownSelect2;
  }
  
  public String getReceiveMutliTextField1() {
    return this.receiveMutliTextField1;
  }
  
  public void setReceiveMutliTextField1(String receiveMutliTextField1) {
    this.receiveMutliTextField1 = receiveMutliTextField1;
  }
  
  public String getReceiveFieldSelectMoreEmp() {
    return this.receiveFieldSelectMoreEmp;
  }
  
  public void setReceiveFieldSelectMoreEmp(String receiveFieldSelectMoreEmp) {
    this.receiveFieldSelectMoreEmp = receiveFieldSelectMoreEmp;
  }
  
  public String getField16() {
    return this.field16;
  }
  
  public void setField16(String field16) {
    this.field16 = field16;
  }
  
  public String getField17() {
    return this.field17;
  }
  
  public void setField17(String field17) {
    this.field17 = field17;
  }
  
  public String getField18() {
    return this.field18;
  }
  
  public void setField18(String field18) {
    this.field18 = field18;
  }
  
  public String getField19() {
    return this.field19;
  }
  
  public void setField19(String field19) {
    this.field19 = field19;
  }
  
  public String getField20() {
    return this.field20;
  }
  
  public void setField20(String field20) {
    this.field20 = field20;
  }
  
  public String getField21() {
    return this.field21;
  }
  
  public void setField21(String field21) {
    this.field21 = field21;
  }
  
  public String getField22() {
    return this.field22;
  }
  
  public void setField22(String field22) {
    this.field22 = field22;
  }
  
  public String getField23() {
    return this.field23;
  }
  
  public void setField23(String field23) {
    this.field23 = field23;
  }
  
  public String getField24() {
    return this.field24;
  }
  
  public void setField24(String field24) {
    this.field24 = field24;
  }
  
  public String getField25() {
    return this.field25;
  }
  
  public void setField25(String field25) {
    this.field25 = field25;
  }
  
  public String getField26() {
    return this.field26;
  }
  
  public void setField26(String field26) {
    this.field26 = field26;
  }
  
  public int getReceiveSyncForward() {
    return this.receiveSyncForward;
  }
  
  public void setReceiveSyncForward(int receiveSyncForward) {
    this.receiveSyncForward = receiveSyncForward;
  }
  
  public String getReceiveAuthorName() {
    return this.receiveAuthorName;
  }
  
  public void setReceiveAuthorName(String receiveAuthorName) {
    this.receiveAuthorName = receiveAuthorName;
  }
}
