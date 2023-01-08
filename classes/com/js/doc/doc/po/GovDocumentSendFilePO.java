package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovDocumentSendFilePO implements Serializable {
  private long id;
  
  private String documentSendFileHead;
  
  private String documentSendFileByteNumber;
  
  private String documentSendFileSecurityGrade;
  
  private String documentSendFileTitle;
  
  private String documentSendFileTopicWord;
  
  private String sendToEmp;
  
  private String sendToGroup;
  
  private String sendToOrg;
  
  private String sendToName;
  
  private String sendToType;
  
  private String sendRoundName;
  
  private String mainToName;
  
  private String sunderToName;
  
  private String copyToName;
  
  private String documentSendFileWritePerson;
  
  private Date documentSendFileWriteDate = null;
  
  private String documentSendFileWriteOrg;
  
  private String documentSendFileAssumePeople;
  
  private String documentSendFileAssumeUnit;
  
  private String documentSendFileCounterSign;
  
  private String documentSendFileCheckDate;
  
  private String documentSendFileCheckCommit;
  
  private String documentSendFileSendFile;
  
  private Date documentSendFileDate = null;
  
  private Date documentSendFileSendDate = null;
  
  private long createdOrg;
  
  private long createdEmp;
  
  private byte flowFlag;
  
  private String accessoryName;
  
  private String accessorySaveName;
  
  private int documentSendFilePrintNumber;
  
  private byte receiveFileIsFlowMode;
  
  private Date createdTime = null;
  
  private String sendFileProof;
  
  private String sendFileReadComment;
  
  private String sendFileGrade;
  
  private String sendFileAccessoryDesc;
  
  private String sendFileDraft;
  
  private String sendFileAgentDraft;
  
  private String sendFileMassDraft;
  
  private String sendFileProveDraft;
  
  private String sendFilePrinter;
  
  private String sendFileText;
  
  private String sendFileLink;
  
  private String sendFileSeal;
  
  private String sendFileRedHead;
  
  private byte sendFileShowStyle;
  
  private String sendFileRedHeadPic;
  
  private String sendFileSealPic;
  
  private long sendFileSealId;
  
  private long sendFileRedHeadId;
  
  private byte sendFileOverSee;
  
  private String sendFileType;
  
  private Set govSendFileBrowser = null;
  
  private Set govSendFileNoBrowser = null;
  
  private Long sendFilePoNumId;
  
  private String sendFileDepartWord;
  
  private String sendFileTemId;
  
  private String toPerson1Id;
  
  private String toPerson2Id;
  
  private String toPersonBaoId;
  
  private String toPersonInnerId;
  
  private String toPersonBao;
  
  private String toPersonInner;
  
  private Long zjkyWordId;
  
  private String zjkySeq;
  
  private String zjkySecrecyterm;
  
  private String zjkyContentLevel;
  
  private String contentAccName;
  
  private String contentAccSaveName;
  
  private String documentWordType;
  
  private Date signsendTime = null;
  
  private String openProperty;
  
  private String sendTextField1;
  
  private String sendTextField2;
  
  private String sendDropDownSelect1;
  
  private String sendDropDownSelect2;
  
  private String sendMutliTextField1;
  
  private String sendFieldSelectMoreEmp;
  
  private String oid;
  
  private String submitFileType;
  
  private int sendFileSyncForward;
  
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
  
  private Long receiveFileId;
  
  private String receiveFileLink;
  
  private String htmlContent;
  
  private Integer isDraft;
  
  private Long processId;
  
  private String processName;
  
  private Integer processType;
  
  private Long tableId;
  
  private String remindField;
  
  private Integer thirdDossier;
  
  private String goldGridId;
  
  private String transactStatus;
  
  private String domainId;
  
  private Set sendFileUser = null;
  
  private String fileSendCheckId;
  
  private String fileSendCheckLink;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getDocumentSendFileHead() {
    return this.documentSendFileHead;
  }
  
  public void setDocumentSendFileHead(String documentSendFileHead) {
    this.documentSendFileHead = documentSendFileHead;
  }
  
  public String getDocumentSendFileByteNumber() {
    return this.documentSendFileByteNumber;
  }
  
  public void setDocumentSendFileByteNumber(String documentSendFileByteNumber) {
    this.documentSendFileByteNumber = documentSendFileByteNumber;
  }
  
  public String getDocumentSendFileSecurityGrade() {
    return this.documentSendFileSecurityGrade;
  }
  
  public void setDocumentSendFileSecurityGrade(String documentSendFileSecurityGrade) {
    this.documentSendFileSecurityGrade = documentSendFileSecurityGrade;
  }
  
  public String getDocumentSendFileTitle() {
    return this.documentSendFileTitle;
  }
  
  public void setDocumentSendFileTitle(String documentSendFileTitle) {
    this.documentSendFileTitle = documentSendFileTitle;
  }
  
  public String getDocumentSendFileTopicWord() {
    return this.documentSendFileTopicWord;
  }
  
  public void setDocumentSendFileTopicWord(String documentSendFileTopicWord) {
    this.documentSendFileTopicWord = documentSendFileTopicWord;
  }
  
  public String getSendToEmp() {
    return this.sendToEmp;
  }
  
  public void setSendToEmp(String sendToEmp) {
    this.sendToEmp = sendToEmp;
  }
  
  public String getSendToGroup() {
    return this.sendToGroup;
  }
  
  public void setSendToGroup(String sendToGroup) {
    this.sendToGroup = sendToGroup;
  }
  
  public String getSendToOrg() {
    return this.sendToOrg;
  }
  
  public void setSendToOrg(String sendToOrg) {
    this.sendToOrg = sendToOrg;
  }
  
  public String getSendToName() {
    return this.sendToName;
  }
  
  public void setSendToName(String sendToName) {
    this.sendToName = sendToName;
  }
  
  public String getSendToType() {
    return this.sendToType;
  }
  
  public void setSendToType(String sendToType) {
    this.sendToType = sendToType;
  }
  
  public String getSendRoundName() {
    return this.sendRoundName;
  }
  
  public void setSendRoundName(String sendRoundName) {
    this.sendRoundName = sendRoundName;
  }
  
  public String getMainToName() {
    return this.mainToName;
  }
  
  public void setMainToName(String mainToName) {
    this.mainToName = mainToName;
  }
  
  public String getSunderToName() {
    return this.sunderToName;
  }
  
  public void setSunderToName(String sunderToName) {
    this.sunderToName = sunderToName;
  }
  
  public String getCopyToName() {
    return this.copyToName;
  }
  
  public void setCopyToName(String copyToName) {
    this.copyToName = copyToName;
  }
  
  public String getDocumentSendFileWritePerson() {
    return this.documentSendFileWritePerson;
  }
  
  public void setDocumentSendFileWritePerson(String documentSendFileWritePerson) {
    this.documentSendFileWritePerson = documentSendFileWritePerson;
  }
  
  public Date getDocumentSendFileWriteDate() {
    return this.documentSendFileWriteDate;
  }
  
  public void setDocumentSendFileWriteDate(Date documentSendFileWriteDate) {
    this.documentSendFileWriteDate = documentSendFileWriteDate;
  }
  
  public String getDocumentSendFileWriteOrg() {
    return this.documentSendFileWriteOrg;
  }
  
  public void setDocumentSendFileWriteOrg(String documentSendFileWriteOrg) {
    this.documentSendFileWriteOrg = documentSendFileWriteOrg;
  }
  
  public String getDocumentSendFileAssumePeople() {
    return this.documentSendFileAssumePeople;
  }
  
  public void setDocumentSendFileAssumePeople(String documentSendFileAssumePeople) {
    this.documentSendFileAssumePeople = documentSendFileAssumePeople;
  }
  
  public String getDocumentSendFileAssumeUnit() {
    return this.documentSendFileAssumeUnit;
  }
  
  public void setDocumentSendFileAssumeUnit(String documentSendFileAssumeUnit) {
    this.documentSendFileAssumeUnit = documentSendFileAssumeUnit;
  }
  
  public String getDocumentSendFileCounterSign() {
    return this.documentSendFileCounterSign;
  }
  
  public void setDocumentSendFileCounterSign(String documentSendFileCounterSign) {
    this.documentSendFileCounterSign = documentSendFileCounterSign;
  }
  
  public String getDocumentSendFileCheckDate() {
    return this.documentSendFileCheckDate;
  }
  
  public void setDocumentSendFileCheckDate(String documentSendFileCheckDate) {
    this.documentSendFileCheckDate = documentSendFileCheckDate;
  }
  
  public String getDocumentSendFileCheckCommit() {
    return this.documentSendFileCheckCommit;
  }
  
  public void setDocumentSendFileCheckCommit(String documentSendFileCheckCommit) {
    this.documentSendFileCheckCommit = documentSendFileCheckCommit;
  }
  
  public String getDocumentSendFileSendFile() {
    return this.documentSendFileSendFile;
  }
  
  public void setDocumentSendFileSendFile(String documentSendFileSendFile) {
    this.documentSendFileSendFile = documentSendFileSendFile;
  }
  
  public Date getDocumentSendFileDate() {
    return this.documentSendFileDate;
  }
  
  public void setDocumentSendFileDate(Date documentSendFileDate) {
    this.documentSendFileDate = documentSendFileDate;
  }
  
  public Date getDocumentSendFileSendDate() {
    return this.documentSendFileSendDate;
  }
  
  public void setDocumentSendFileSendDate(Date documentSendFileSendDate) {
    this.documentSendFileSendDate = documentSendFileSendDate;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public byte getFlowFlag() {
    return this.flowFlag;
  }
  
  public void setFlowFlag(byte flowFlag) {
    this.flowFlag = flowFlag;
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
  
  public int getDocumentSendFilePrintNumber() {
    return this.documentSendFilePrintNumber;
  }
  
  public void setDocumentSendFilePrintNumber(int documentSendFilePrintNumber) {
    this.documentSendFilePrintNumber = documentSendFilePrintNumber;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovDocumentSendFilePO))
      return false; 
    GovDocumentSendFilePO castOther = (GovDocumentSendFilePO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public byte getReceiveFileIsFlowMode() {
    return this.receiveFileIsFlowMode;
  }
  
  public void setReceiveFileIsFlowMode(byte receiveFileIsFlowMode) {
    this.receiveFileIsFlowMode = receiveFileIsFlowMode;
  }
  
  public Date getCreatedTime() {
    return this.createdTime;
  }
  
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
  
  public String getSendFileProof() {
    return this.sendFileProof;
  }
  
  public void setSendFileProof(String sendFileProof) {
    this.sendFileProof = sendFileProof;
  }
  
  public String getSendFileReadComment() {
    return this.sendFileReadComment;
  }
  
  public void setSendFileReadComment(String sendFileReadComment) {
    this.sendFileReadComment = sendFileReadComment;
  }
  
  public String getSendFileGrade() {
    return this.sendFileGrade;
  }
  
  public void setSendFileGrade(String sendFileGrade) {
    this.sendFileGrade = sendFileGrade;
  }
  
  public String getSendFileAccessoryDesc() {
    return this.sendFileAccessoryDesc;
  }
  
  public void setSendFileAccessoryDesc(String sendFileAccessoryDesc) {
    this.sendFileAccessoryDesc = sendFileAccessoryDesc;
  }
  
  public String getSendFileDraft() {
    return this.sendFileDraft;
  }
  
  public void setSendFileDraft(String sendFileDraft) {
    this.sendFileDraft = sendFileDraft;
  }
  
  public String getSendFileAgentDraft() {
    return this.sendFileAgentDraft;
  }
  
  public void setSendFileAgentDraft(String sendFileAgentDraft) {
    this.sendFileAgentDraft = sendFileAgentDraft;
  }
  
  public String getSendFileMassDraft() {
    return this.sendFileMassDraft;
  }
  
  public void setSendFileMassDraft(String sendFileMassDraft) {
    this.sendFileMassDraft = sendFileMassDraft;
  }
  
  public String getSendFileProveDraft() {
    return this.sendFileProveDraft;
  }
  
  public void setSendFileProveDraft(String sendFileProveDraft) {
    this.sendFileProveDraft = sendFileProveDraft;
  }
  
  public String getSendFilePrinter() {
    return this.sendFilePrinter;
  }
  
  public void setSendFilePrinter(String sendFilePrinter) {
    this.sendFilePrinter = sendFilePrinter;
  }
  
  public String getSendFileLink() {
    return this.sendFileLink;
  }
  
  public void setSendFileLink(String sendFileLink) {
    this.sendFileLink = sendFileLink;
  }
  
  public String getSendFileSeal() {
    return this.sendFileSeal;
  }
  
  public void setSendFileSeal(String sendFileSeal) {
    this.sendFileSeal = sendFileSeal;
  }
  
  public String getSendFileRedHead() {
    return this.sendFileRedHead;
  }
  
  public void setSendFileRedHead(String sendFileRedHead) {
    this.sendFileRedHead = sendFileRedHead;
  }
  
  public byte getSendFileShowStyle() {
    return this.sendFileShowStyle;
  }
  
  public void setSendFileShowStyle(byte sendFileShowStyle) {
    this.sendFileShowStyle = sendFileShowStyle;
  }
  
  public String getSendFileRedHeadPic() {
    return this.sendFileRedHeadPic;
  }
  
  public void setSendFileRedHeadPic(String sendFileRedHeadPic) {
    this.sendFileRedHeadPic = sendFileRedHeadPic;
  }
  
  public String getSendFileSealPic() {
    return this.sendFileSealPic;
  }
  
  public void setSendFileSealPic(String sendFileSealPic) {
    this.sendFileSealPic = sendFileSealPic;
  }
  
  public long getSendFileSealId() {
    return this.sendFileSealId;
  }
  
  public void setSendFileSealId(long sendFileSealId) {
    this.sendFileSealId = sendFileSealId;
  }
  
  public long getSendFileRedHeadId() {
    return this.sendFileRedHeadId;
  }
  
  public void setSendFileRedHeadId(long sendFileRedHeadId) {
    this.sendFileRedHeadId = sendFileRedHeadId;
  }
  
  public byte getSendFileOverSee() {
    return this.sendFileOverSee;
  }
  
  public void setSendFileOverSee(byte sendFileOverSee) {
    this.sendFileOverSee = sendFileOverSee;
  }
  
  public String getSendFileType() {
    return this.sendFileType;
  }
  
  public void setSendFileType(String sendFileType) {
    this.sendFileType = sendFileType;
  }
  
  public String getSendFileText() {
    return this.sendFileText;
  }
  
  public void setSendFileText(String sendFileText) {
    this.sendFileText = sendFileText;
  }
  
  public Set getGovSendFileBrowser() {
    return this.govSendFileBrowser;
  }
  
  public void setGovSendFileBrowser(Set govSendFileBrowser) {
    this.govSendFileBrowser = govSendFileBrowser;
  }
  
  public Set getGovSendFileNoBrowser() {
    return this.govSendFileNoBrowser;
  }
  
  public void setGovSendFileNoBrowser(Set govSendFileNoBrowser) {
    this.govSendFileNoBrowser = govSendFileNoBrowser;
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
  
  public Long getReceiveFileId() {
    return this.receiveFileId;
  }
  
  public void setReceiveFileId(Long receiveFileId) {
    this.receiveFileId = receiveFileId;
  }
  
  public String getReceiveFileLink() {
    return this.receiveFileLink;
  }
  
  public void setReceiveFileLink(String receiveFileLink) {
    this.receiveFileLink = receiveFileLink;
  }
  
  public String getHtmlContent() {
    return this.htmlContent;
  }
  
  public void setHtmlContent(String htmlContent) {
    this.htmlContent = htmlContent;
  }
  
  public Integer getIsDraft() {
    return this.isDraft;
  }
  
  public void setIsDraft(Integer isDraft) {
    this.isDraft = isDraft;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public String getProcessName() {
    return this.processName;
  }
  
  public void setProcessName(String processName) {
    this.processName = processName;
  }
  
  public Integer getProcessType() {
    return this.processType;
  }
  
  public void setProcessType(Integer processType) {
    this.processType = processType;
  }
  
  public Long getTableId() {
    return this.tableId;
  }
  
  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }
  
  public String getRemindField() {
    return this.remindField;
  }
  
  public void setRemindField(String remindField) {
    this.remindField = remindField;
  }
  
  public Integer getThirdDossier() {
    return this.thirdDossier;
  }
  
  public void setThirdDossier(Integer thirdDossier) {
    this.thirdDossier = thirdDossier;
  }
  
  public String getGoldGridId() {
    return this.goldGridId;
  }
  
  public void setGoldGridId(String goldGridId) {
    this.goldGridId = goldGridId;
  }
  
  public String getTransactStatus() {
    return this.transactStatus;
  }
  
  public void setTransactStatus(String transactStatus) {
    this.transactStatus = transactStatus;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public Set getSendFileUser() {
    return this.sendFileUser;
  }
  
  public void setSendFileUser(Set sendFileUser) {
    this.sendFileUser = sendFileUser;
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
  
  public Long getSendFilePoNumId() {
    return this.sendFilePoNumId;
  }
  
  public String getSendFileDepartWord() {
    return this.sendFileDepartWord;
  }
  
  public String getSendFileTemId() {
    return this.sendFileTemId;
  }
  
  public Long getZjkyWordId() {
    return this.zjkyWordId;
  }
  
  public void setFileSendCheckLink(String fileSendCheckLink) {
    this.fileSendCheckLink = fileSendCheckLink;
  }
  
  public void setSendFilePoNumId(Long sendFilePoNumId) {
    this.sendFilePoNumId = sendFilePoNumId;
  }
  
  public void setSendFileDepartWord(String sendFileDepartWord) {
    this.sendFileDepartWord = sendFileDepartWord;
  }
  
  public void setSendFileTemId(String sendFileTemId) {
    this.sendFileTemId = sendFileTemId;
  }
  
  public void setZjkySeq(String zjkySeq) {
    this.zjkySeq = zjkySeq;
  }
  
  public String getZjkySeq() {
    return this.zjkySeq;
  }
  
  public void setZjkySecrecyterm(String zjkySecrecyterm) {
    this.zjkySecrecyterm = zjkySecrecyterm;
  }
  
  public String getZjkySecrecyterm() {
    return this.zjkySecrecyterm;
  }
  
  public void setZjkyContentLevel(String zjkyContentLevel) {
    this.zjkyContentLevel = zjkyContentLevel;
  }
  
  public String getZjkyContentLevel() {
    return this.zjkyContentLevel;
  }
  
  public String getContentAccName() {
    return this.contentAccName;
  }
  
  public String getContentAccSaveName() {
    return this.contentAccSaveName;
  }
  
  public String getToPersonInner() {
    return this.toPersonInner;
  }
  
  public String getToPersonBao() {
    return this.toPersonBao;
  }
  
  public String getToPersonInnerId() {
    return this.toPersonInnerId;
  }
  
  public String getToPersonBaoId() {
    return this.toPersonBaoId;
  }
  
  public String getToPerson2Id() {
    return this.toPerson2Id;
  }
  
  public String getToPerson1Id() {
    return this.toPerson1Id;
  }
  
  public String getDocumentWordType() {
    return this.documentWordType;
  }
  
  public void setZjkyWordId(Long zjkyWordId) {
    this.zjkyWordId = zjkyWordId;
  }
  
  public void setContentAccName(String contentAccName) {
    this.contentAccName = contentAccName;
  }
  
  public void setContentAccSaveName(String contentAccSaveName) {
    this.contentAccSaveName = contentAccSaveName;
  }
  
  public void setToPersonInner(String toPersonInner) {
    this.toPersonInner = toPersonInner;
  }
  
  public void setToPersonBao(String toPersonBao) {
    this.toPersonBao = toPersonBao;
  }
  
  public void setToPersonInnerId(String toPersonInnerId) {
    this.toPersonInnerId = toPersonInnerId;
  }
  
  public void setToPersonBaoId(String toPersonBaoId) {
    this.toPersonBaoId = toPersonBaoId;
  }
  
  public void setToPerson2Id(String toPerson2Id) {
    this.toPerson2Id = toPerson2Id;
  }
  
  public void setToPerson1Id(String toPerson1Id) {
    this.toPerson1Id = toPerson1Id;
  }
  
  public void setDocumentWordType(String documentWordType) {
    this.documentWordType = documentWordType;
  }
  
  public String getSendTextField1() {
    return this.sendTextField1;
  }
  
  public void setSendTextField1(String sendTextField1) {
    this.sendTextField1 = sendTextField1;
  }
  
  public String getSendTextField2() {
    return this.sendTextField2;
  }
  
  public void setSendTextField2(String sendTextField2) {
    this.sendTextField2 = sendTextField2;
  }
  
  public String getSendDropDownSelect1() {
    return this.sendDropDownSelect1;
  }
  
  public void setSendDropDownSelect1(String sendDropDownSelect1) {
    this.sendDropDownSelect1 = sendDropDownSelect1;
  }
  
  public String getSendDropDownSelect2() {
    return this.sendDropDownSelect2;
  }
  
  public void setSendDropDownSelect2(String sendDropDownSelect2) {
    this.sendDropDownSelect2 = sendDropDownSelect2;
  }
  
  public String getSendMutliTextField1() {
    return this.sendMutliTextField1;
  }
  
  public void setSendMutliTextField1(String sendMutliTextField1) {
    this.sendMutliTextField1 = sendMutliTextField1;
  }
  
  public Date getSignsendTime() {
    return this.signsendTime;
  }
  
  public void setSignsendTime(Date signsendTime) {
    this.signsendTime = signsendTime;
  }
  
  public String getOpenProperty() {
    return this.openProperty;
  }
  
  public void setOpenProperty(String openProperty) {
    this.openProperty = openProperty;
  }
  
  public String getSendFieldSelectMoreEmp() {
    return this.sendFieldSelectMoreEmp;
  }
  
  public void setSendFieldSelectMoreEmp(String sendFieldSelectMoreEmp) {
    this.sendFieldSelectMoreEmp = sendFieldSelectMoreEmp;
  }
  
  public String getSubmitFileType() {
    return this.submitFileType;
  }
  
  public void setSubmitFileType(String submitFileType) {
    this.submitFileType = submitFileType;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public void setOid(String oid) {
    this.oid = oid;
  }
  
  public int getSendFileSyncForward() {
    return this.sendFileSyncForward;
  }
  
  public void setSendFileSyncForward(int sendFileSyncForward) {
    this.sendFileSyncForward = sendFileSyncForward;
  }
}
