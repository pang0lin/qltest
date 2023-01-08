package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GovSendFileActionForm extends ActionForm {
  private String documentSendFileAssumePeople;
  
  private String documentSendFileAssumeUnit;
  
  private String documentSendFileByteNumber;
  
  private String documentSendFileCheckCommit;
  
  private String documentSendFileCheckDate;
  
  private String documentSendFileCounterSign;
  
  private String documentSendFileHead;
  
  private String documentSendFilePrintNumber;
  
  private String documentSendFileSecurityGrade;
  
  private String documentSendFileSendFile;
  
  private String documentSendFileSendTime;
  
  private String documentSendFileTime;
  
  private String documentSendFileTitle;
  
  private String documentSendFileTopicWord;
  
  private String documentSendFileWriteOrg;
  
  private String nextEmpId;
  
  private String nextEmpName;
  
  private String sendToId;
  
  private String sendToName;
  
  private String sendToType;
  
  private String toPerson1;
  
  private String toPerson3;
  
  private String toPerson4;
  
  private String toPerson5;
  
  private String toPerson6;
  
  private String toPerson2;
  
  private String queryItem;
  
  private String queryStatus;
  
  private String queryTitle;
  
  private String querySecret;
  
  private String queryNumber;
  
  private String queryTransPersonName;
  
  private String queryUnit;
  
  private byte receiveFileIsFlowMode;
  
  private String sendFileGrade;
  
  private String sendFileReadComment;
  
  private String sendFileDraft;
  
  private String sendFileAccessoryDesc;
  
  private String sendFileAgentDraft;
  
  private String sendFileMassDraft;
  
  private String sendFileProveDraft;
  
  private String sendFilePrinter;
  
  private String sendFileProof;
  
  private String sendFileText;
  
  private long sendFileRedHeadId;
  
  private long sendFileSealId;
  
  private String sendFileRedHead;
  
  private String sendFileSeal;
  
  private String sendFileRedHeadPic;
  
  private String sendFileSealPic;
  
  private byte sendFileShowStyle;
  
  private String queryOrg;
  
  private String sendFileLink;
  
  private String sendFileType;
  
  private String sendFileDepartWord;
  
  private String zjkySecrecyterm;
  
  private String zjkyContentLevel;
  
  private String zjkySeq;
  
  private String toPerson1Id;
  
  private String toPerson2Id;
  
  private String toPersonBaoId;
  
  private String toPersonInnerId;
  
  private String toPersonBao;
  
  private String toPersonInner;
  
  private String leader7;
  
  private String zjkyWordId;
  
  private String sendFilePoNumId;
  
  private String templateId;
  
  private String documentWordType;
  
  private String signsendTime;
  
  private String openProperty;
  
  private String sendTextField1;
  
  private String sendTextField2;
  
  private String sendDropDownSelect1;
  
  private String sendDropDownSelect2;
  
  private String sendMutliTextField1;
  
  private String sendFieldSelectMoreEmp;
  
  private String oid;
  
  private String submitFileType;
  
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
  
  public String getDocumentSendFileByteNumber() {
    return this.documentSendFileByteNumber;
  }
  
  public void setDocumentSendFileByteNumber(String documentSendFileByteNumber) {
    this.documentSendFileByteNumber = documentSendFileByteNumber;
  }
  
  public String getDocumentSendFileCheckCommit() {
    return this.documentSendFileCheckCommit;
  }
  
  public void setDocumentSendFileCheckCommit(String documentSendFileCheckCommit) {
    this.documentSendFileCheckCommit = documentSendFileCheckCommit;
  }
  
  public String getDocumentSendFileCheckDate() {
    return this.documentSendFileCheckDate;
  }
  
  public void setDocumentSendFileCheckDate(String documentSendFileCheckDate) {
    this.documentSendFileCheckDate = documentSendFileCheckDate;
  }
  
  public String getDocumentSendFileCounterSign() {
    return this.documentSendFileCounterSign;
  }
  
  public void setDocumentSendFileCounterSign(String documentSendFileCounterSign) {
    this.documentSendFileCounterSign = documentSendFileCounterSign;
  }
  
  public String getDocumentSendFileHead() {
    return this.documentSendFileHead;
  }
  
  public void setDocumentSendFileHead(String documentSendFileHead) {
    this.documentSendFileHead = documentSendFileHead;
  }
  
  public String getDocumentSendFilePrintNumber() {
    return this.documentSendFilePrintNumber;
  }
  
  public void setDocumentSendFilePrintNumber(String documentSendFilePrintNumber) {
    this.documentSendFilePrintNumber = documentSendFilePrintNumber;
  }
  
  public String getDocumentSendFileSecurityGrade() {
    return this.documentSendFileSecurityGrade;
  }
  
  public void setDocumentSendFileSecurityGrade(String documentSendFileSecurityGrade) {
    this.documentSendFileSecurityGrade = documentSendFileSecurityGrade;
  }
  
  public String getDocumentSendFileSendFile() {
    return this.documentSendFileSendFile;
  }
  
  public void setDocumentSendFileSendFile(String documentSendFileSendFile) {
    this.documentSendFileSendFile = documentSendFileSendFile;
  }
  
  public String getDocumentSendFileSendTime() {
    return this.documentSendFileSendTime;
  }
  
  public void setDocumentSendFileSendTime(String documentSendFileSendTime) {
    this.documentSendFileSendTime = documentSendFileSendTime;
  }
  
  public String getDocumentSendFileTime() {
    return this.documentSendFileTime;
  }
  
  public void setDocumentSendFileTime(String documentSendFileTime) {
    this.documentSendFileTime = documentSendFileTime;
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
  
  public String getDocumentSendFileWriteOrg() {
    return this.documentSendFileWriteOrg;
  }
  
  public void setDocumentSendFileWriteOrg(String documentSendFileWriteOrg) {
    this.documentSendFileWriteOrg = documentSendFileWriteOrg;
  }
  
  public String getNextEmpId() {
    return this.nextEmpId;
  }
  
  public void setNextEmpId(String nextEmpId) {
    this.nextEmpId = nextEmpId;
  }
  
  public String getNextEmpName() {
    return this.nextEmpName;
  }
  
  public void setNextEmpName(String nextEmpName) {
    this.nextEmpName = nextEmpName;
  }
  
  public String getSendToId() {
    return this.sendToId;
  }
  
  public void setSendToId(String sendToId) {
    this.sendToId = sendToId;
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
  
  public String getToPerson1() {
    return this.toPerson1;
  }
  
  public void setToPerson1(String toPerson1) {
    this.toPerson1 = toPerson1;
  }
  
  public String getToPerson3() {
    return this.toPerson3;
  }
  
  public void setToPerson3(String toPerson3) {
    this.toPerson3 = toPerson3;
  }
  
  public String getToPerson4() {
    return this.toPerson4;
  }
  
  public void setToPerson4(String toPerson4) {
    this.toPerson4 = toPerson4;
  }
  
  public String getToPerson5() {
    return this.toPerson5;
  }
  
  public void setToPerson5(String toPerson5) {
    this.toPerson5 = toPerson5;
  }
  
  public String getToPerson6() {
    return this.toPerson6;
  }
  
  public String getToPerson2() {
    return this.toPerson2;
  }
  
  public void setToPerson6(String toPerson6) {
    this.toPerson6 = toPerson6;
  }
  
  public void setToPerson2(String toPerson2) {
    this.toPerson2 = toPerson2;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.documentSendFileAssumePeople = null;
    this.documentSendFileAssumeUnit = null;
    this.documentSendFileByteNumber = null;
    this.documentSendFileCheckCommit = null;
    this.documentSendFileCheckDate = null;
    this.documentSendFileCounterSign = null;
    this.documentSendFileHead = null;
    this.documentSendFilePrintNumber = null;
    this.documentSendFileSecurityGrade = null;
    this.documentSendFileSendFile = null;
    this.documentSendFileSendTime = null;
    this.documentSendFileTime = null;
    this.documentSendFileTitle = null;
    this.documentSendFileTopicWord = null;
    this.documentSendFileWriteOrg = null;
    this.nextEmpId = null;
    this.nextEmpName = null;
    this.sendToId = null;
    this.sendToName = null;
    this.sendToType = null;
    this.toPerson1 = null;
    this.toPerson3 = null;
    this.toPerson4 = null;
    this.toPerson5 = null;
    this.toPerson6 = null;
  }
  
  public String getQueryItem() {
    return this.queryItem;
  }
  
  public void setQueryItem(String queryItem) {
    this.queryItem = queryItem;
  }
  
  public String getQueryStatus() {
    return this.queryStatus;
  }
  
  public void setQueryStatus(String queryStatus) {
    this.queryStatus = queryStatus;
  }
  
  public String getQueryTitle() {
    return this.queryTitle;
  }
  
  public void setQueryTitle(String queryTitle) {
    this.queryTitle = queryTitle;
  }
  
  public String getQuerySecret() {
    return this.querySecret;
  }
  
  public void setQuerySecret(String querySecret) {
    this.querySecret = querySecret;
  }
  
  public String getQueryNumber() {
    return this.queryNumber;
  }
  
  public void setQueryNumber(String queryNumber) {
    this.queryNumber = queryNumber;
  }
  
  public String getQueryTransPersonName() {
    return this.queryTransPersonName;
  }
  
  public void setQueryTransPersonName(String queryTransPersonName) {
    this.queryTransPersonName = queryTransPersonName;
  }
  
  public String getQueryUnit() {
    return this.queryUnit;
  }
  
  public void setQueryUnit(String queryUnit) {
    this.queryUnit = queryUnit;
  }
  
  public byte getReceiveFileIsFlowMode() {
    return this.receiveFileIsFlowMode;
  }
  
  public void setReceiveFileIsFlowMode(byte receiveFileIsFlowMode) {
    this.receiveFileIsFlowMode = receiveFileIsFlowMode;
  }
  
  public String getSendFileGrade() {
    return this.sendFileGrade;
  }
  
  public void setSendFileGrade(String sendFileGrade) {
    this.sendFileGrade = sendFileGrade;
  }
  
  public String getSendFileReadComment() {
    return this.sendFileReadComment;
  }
  
  public void setSendFileReadComment(String sendFileReadComment) {
    this.sendFileReadComment = sendFileReadComment;
  }
  
  public String getSendFileDraft() {
    return this.sendFileDraft;
  }
  
  public void setSendFileDraft(String sendFileDraft) {
    this.sendFileDraft = sendFileDraft;
  }
  
  public String getSendFileAccessoryDesc() {
    return this.sendFileAccessoryDesc;
  }
  
  public void setSendFileAccessoryDesc(String sendFileAccessoryDesc) {
    this.sendFileAccessoryDesc = sendFileAccessoryDesc;
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
  
  public String getSendFileProof() {
    return this.sendFileProof;
  }
  
  public void setSendFileProof(String sendFileProof) {
    this.sendFileProof = sendFileProof;
  }
  
  public String getSendFileText() {
    return this.sendFileText;
  }
  
  public void setSendFileText(String sendFileText) {
    this.sendFileText = sendFileText;
  }
  
  public long getSendFileRedHeadId() {
    return this.sendFileRedHeadId;
  }
  
  public void setSendFileRedHeadId(long sendFileRedHeadId) {
    this.sendFileRedHeadId = sendFileRedHeadId;
  }
  
  public long getSendFileSealId() {
    return this.sendFileSealId;
  }
  
  public void setSendFileSealId(long sendFileSealId) {
    this.sendFileSealId = sendFileSealId;
  }
  
  public String getSendFileRedHead() {
    return this.sendFileRedHead;
  }
  
  public void setSendFileRedHead(String sendFileRedHead) {
    this.sendFileRedHead = sendFileRedHead;
  }
  
  public String getSendFileSeal() {
    return this.sendFileSeal;
  }
  
  public void setSendFileSeal(String sendFileSeal) {
    this.sendFileSeal = sendFileSeal;
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
  
  public byte getSendFileShowStyle() {
    return this.sendFileShowStyle;
  }
  
  public void setSendFileShowStyle(byte sendFileShowStyle) {
    this.sendFileShowStyle = sendFileShowStyle;
  }
  
  public String getQueryOrg() {
    return this.queryOrg;
  }
  
  public void setQueryOrg(String queryOrg) {
    this.queryOrg = queryOrg;
  }
  
  public String getSendFileLink() {
    return this.sendFileLink;
  }
  
  public void setSendFileLink(String sendFileLink) {
    this.sendFileLink = sendFileLink;
  }
  
  public String getSendFileType() {
    return this.sendFileType;
  }
  
  public void setSendFileType(String sendFileType) {
    this.sendFileType = sendFileType;
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
  
  public String getSendFileDepartWord() {
    return this.sendFileDepartWord;
  }
  
  public String getZjkySecrecyterm() {
    return this.zjkySecrecyterm;
  }
  
  public String getZjkyContentLevel() {
    return this.zjkyContentLevel;
  }
  
  public String getZjkySeq() {
    return this.zjkySeq;
  }
  
  public String getToPersonInnerId() {
    return this.toPersonInnerId;
  }
  
  public String getToPersonInner() {
    return this.toPersonInner;
  }
  
  public String getToPersonBaoId() {
    return this.toPersonBaoId;
  }
  
  public String getToPersonBao() {
    return this.toPersonBao;
  }
  
  public String getToPerson2Id() {
    return this.toPerson2Id;
  }
  
  public String getToPerson1Id() {
    return this.toPerson1Id;
  }
  
  public void setField10(String field10) {
    this.field10 = field10;
  }
  
  public void setSendFileDepartWord(String sendFileDepartWord) {
    this.sendFileDepartWord = sendFileDepartWord;
  }
  
  public void setZjkySecrecyterm(String zjkySecrecyterm) {
    this.zjkySecrecyterm = zjkySecrecyterm;
  }
  
  public void setZjkyContentLevel(String zjkyContentLevel) {
    this.zjkyContentLevel = zjkyContentLevel;
  }
  
  public void setZjkySeq(String zjkySeq) {
    this.zjkySeq = zjkySeq;
  }
  
  public void setToPersonInnerId(String toPersonInnerId) {
    this.toPersonInnerId = toPersonInnerId;
  }
  
  public void setToPersonInner(String toPersonInner) {
    this.toPersonInner = toPersonInner;
  }
  
  public void setToPersonBaoId(String toPersonBaoId) {
    this.toPersonBaoId = toPersonBaoId;
  }
  
  public void setToPersonBao(String toPersonBao) {
    this.toPersonBao = toPersonBao;
  }
  
  public void setToPerson2Id(String toPerson2Id) {
    this.toPerson2Id = toPerson2Id;
  }
  
  public void setToPerson1Id(String toPerson1Id) {
    this.toPerson1Id = toPerson1Id;
  }
  
  public void setLeader7(String leader7) {
    this.leader7 = leader7;
  }
  
  public void setZjkyWordId(String zjkyWordId) {
    this.zjkyWordId = zjkyWordId;
  }
  
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  
  public void setSendFilePoNumId(String sendFilePoNumId) {
    this.sendFilePoNumId = sendFilePoNumId;
  }
  
  public void setDocumentWordType(String documentWordType) {
    this.documentWordType = documentWordType;
  }
  
  public String getLeader7() {
    return this.leader7;
  }
  
  public String getZjkyWordId() {
    return this.zjkyWordId;
  }
  
  public String getTemplateId() {
    return this.templateId;
  }
  
  public String getSendFilePoNumId() {
    return this.sendFilePoNumId;
  }
  
  public String getDocumentWordType() {
    return this.documentWordType;
  }
  
  public String getSignsendTime() {
    return this.signsendTime;
  }
  
  public void setSignsendTime(String signsendTime) {
    this.signsendTime = signsendTime;
  }
  
  public String getOpenProperty() {
    return this.openProperty;
  }
  
  public void setOpenProperty(String openProperty) {
    this.openProperty = openProperty;
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
  
  public String getSendFieldSelectMoreEmp() {
    return this.sendFieldSelectMoreEmp;
  }
  
  public void setSendFieldSelectMoreEmp(String sendFieldSelectMoreEmp) {
    this.sendFieldSelectMoreEmp = sendFieldSelectMoreEmp;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public void setOid(String oid) {
    this.oid = oid;
  }
  
  public String getSubmitFileType() {
    return this.submitFileType;
  }
  
  public void setSubmitFileType(String submitFileType) {
    this.submitFileType = submitFileType;
  }
}
