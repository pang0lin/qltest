package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFWorkFlowProcessPO implements Serializable {
  private Long wfWorkFlowProcessId;
  
  private Long accessDatabaseId;
  
  private String workFlowProcessName;
  
  private Date processCreatedDate = null;
  
  private String processDescription;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String useOrg;
  
  private String useGroup;
  
  private String usePerson;
  
  private WFPackagePO wfPackage;
  
  private String createUserName;
  
  private int processType;
  
  private String userScope;
  
  private Set wfWorkFlowWriteControl = null;
  
  private Set wfActivity = null;
  
  private String dossierFileSeeScope;
  
  private String dossierFileSeeGroup;
  
  private String dossierFileSeeOrg;
  
  private String dossierFileSeePerson;
  
  private String remindField;
  
  private Integer isPublish;
  
  private Integer isDossier;
  
  private Integer canCancel;
  
  private String formClassName;
  
  private String formClassMethod;
  
  private String formClassCompMethod;
  
  private String domainId;
  
  private String printFileSeeScope;
  
  private String printFileSeeGroup;
  
  private String printFileSeeOrg;
  
  private String printFileSeePerson;
  
  private String dossierFileExportScope;
  
  private String dossierFileExportGroup;
  
  private String dossierFileExportOrg;
  
  private String dossierFileExportPerson;
  
  private int formType;
  
  private String startJSP;
  
  private String optJSP;
  
  private String dossierFileOperScope;
  
  private String dossierFileOperOrg;
  
  private String dossierFileOperGroup;
  
  private String dossierFileOperPerson;
  
  private String processAdminScope;
  
  private String processAdminOrg;
  
  private String processAdminGroup;
  
  private String processAdminPerson;
  
  private int orderCode;
  
  private int processStatus;
  
  private Date lastUpdateTime = null;
  
  private Date processUseTime = null;
  
  private String ownerName;
  
  private Long ownerId;
  
  private Long ownerOrgId;
  
  private String infoChannelId;
  
  private int sendFileType;
  
  private Long mainFormId;
  
  private String startUrl;
  
  private String startMethod;
  
  private String startPara;
  
  private String startNameSpace;
  
  private String completeUrl;
  
  private String completeMethod;
  
  private String completePara;
  
  private String completeNameSpace;
  
  private String processDeadline = "0";
  
  private String processDeadlineType = "0";
  
  private String isJx = "0";
  
  public String getProcessDeadline() {
    return this.processDeadline;
  }
  
  public void setProcessDeadline(String processDeadline) {
    this.processDeadline = processDeadline;
  }
  
  public String getProcessDeadlineType() {
    return this.processDeadlineType;
  }
  
  public void setProcessDeadlineType(String processDeadlineType) {
    this.processDeadlineType = processDeadlineType;
  }
  
  public String getStartUrl() {
    return this.startUrl;
  }
  
  public void setStartUrl(String startUrl) {
    this.startUrl = startUrl;
  }
  
  public String getStartMethod() {
    return this.startMethod;
  }
  
  public void setStartMethod(String startMethod) {
    this.startMethod = startMethod;
  }
  
  public String getStartPara() {
    return this.startPara;
  }
  
  public void setStartPara(String startPara) {
    this.startPara = startPara;
  }
  
  public String getStartNameSpace() {
    return this.startNameSpace;
  }
  
  public void setStartNameSpace(String startNameSpace) {
    this.startNameSpace = startNameSpace;
  }
  
  public String getCompleteUrl() {
    return this.completeUrl;
  }
  
  public void setCompleteUrl(String completeUrl) {
    this.completeUrl = completeUrl;
  }
  
  public String getCompleteMethod() {
    return this.completeMethod;
  }
  
  public void setCompleteMethod(String completeMethod) {
    this.completeMethod = completeMethod;
  }
  
  public String getCompletePara() {
    return this.completePara;
  }
  
  public void setCompletePara(String completePara) {
    this.completePara = completePara;
  }
  
  public String getCompleteNameSpace() {
    return this.completeNameSpace;
  }
  
  public void setCompleteNameSpace(String completeNameSpace) {
    this.completeNameSpace = completeNameSpace;
  }
  
  public Long getMainFormId() {
    return this.mainFormId;
  }
  
  public void setMainFormId(Long mainFormId) {
    this.mainFormId = mainFormId;
  }
  
  public int getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(int orderCode) {
    this.orderCode = orderCode;
  }
  
  public Long getWfWorkFlowProcessId() {
    return this.wfWorkFlowProcessId;
  }
  
  public void setWfWorkFlowProcessId(Long wfWorkFlowProcessId) {
    this.wfWorkFlowProcessId = wfWorkFlowProcessId;
  }
  
  public Long getAccessDatabaseId() {
    return this.accessDatabaseId;
  }
  
  public void setAccessDatabaseId(Long accessDatabaseId) {
    this.accessDatabaseId = accessDatabaseId;
  }
  
  public String getWorkFlowProcessName() {
    return this.workFlowProcessName;
  }
  
  public void setWorkFlowProcessName(String workFlowProcessName) {
    this.workFlowProcessName = workFlowProcessName;
  }
  
  public Date getProcessCreatedDate() {
    return this.processCreatedDate;
  }
  
  public void setProcessCreatedDate(Date processCreatedDate) {
    this.processCreatedDate = processCreatedDate;
  }
  
  public String getProcessDescription() {
    return this.processDescription;
  }
  
  public void setProcessDescription(String processDescription) {
    this.processDescription = processDescription;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getUseOrg() {
    return this.useOrg;
  }
  
  public void setUseOrg(String useOrg) {
    this.useOrg = useOrg;
  }
  
  public String getUseGroup() {
    return this.useGroup;
  }
  
  public void setUseGroup(String useGroup) {
    this.useGroup = useGroup;
  }
  
  public String getUsePerson() {
    return this.usePerson;
  }
  
  public void setUsePerson(String usePerson) {
    this.usePerson = usePerson;
  }
  
  public WFPackagePO getWfPackage() {
    return this.wfPackage;
  }
  
  public void setWfPackage(WFPackagePO wfPackage) {
    this.wfPackage = wfPackage;
  }
  
  public String getCreateUserName() {
    return this.createUserName;
  }
  
  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }
  
  public int getProcessType() {
    return this.processType;
  }
  
  public void setProcessType(int processType) {
    this.processType = processType;
  }
  
  public String getUserScope() {
    return this.userScope;
  }
  
  public void setUserScope(String userScope) {
    this.userScope = userScope;
  }
  
  public Set getWfWorkFlowWriteControl() {
    return this.wfWorkFlowWriteControl;
  }
  
  public void setWfWorkFlowWriteControl(Set wfWorkFlowWriteControl) {
    this.wfWorkFlowWriteControl = wfWorkFlowWriteControl;
  }
  
  public Set getWfActivity() {
    return this.wfActivity;
  }
  
  public void setWfActivity(Set wfActivity) {
    this.wfActivity = wfActivity;
  }
  
  public String getDossierFileSeeScope() {
    return this.dossierFileSeeScope;
  }
  
  public void setDossierFileSeeScope(String dossierFileSeeScope) {
    this.dossierFileSeeScope = dossierFileSeeScope;
  }
  
  public String getDossierFileSeeGroup() {
    return this.dossierFileSeeGroup;
  }
  
  public void setDossierFileSeeGroup(String dossierFileSeeGroup) {
    this.dossierFileSeeGroup = dossierFileSeeGroup;
  }
  
  public String getDossierFileSeeOrg() {
    return this.dossierFileSeeOrg;
  }
  
  public void setDossierFileSeeOrg(String dossierFileSeeOrg) {
    this.dossierFileSeeOrg = dossierFileSeeOrg;
  }
  
  public String getDossierFileSeePerson() {
    return this.dossierFileSeePerson;
  }
  
  public void setDossierFileSeePerson(String dossierFileSeePerson) {
    this.dossierFileSeePerson = dossierFileSeePerson;
  }
  
  public String getRemindField() {
    return this.remindField;
  }
  
  public void setRemindField(String remindField) {
    this.remindField = remindField;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFWorkFlowProcessPO))
      return false; 
    WFWorkFlowProcessPO castOther = (WFWorkFlowProcessPO)other;
    return (new EqualsBuilder()).append(getWfWorkFlowProcessId(), castOther.getWfWorkFlowProcessId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfWorkFlowProcessId()).toHashCode();
  }
  
  public Integer getIsPublish() {
    return this.isPublish;
  }
  
  public void setIsPublish(Integer isPublish) {
    this.isPublish = isPublish;
  }
  
  public Integer getIsDossier() {
    return this.isDossier;
  }
  
  public void setIsDossier(Integer isDossier) {
    this.isDossier = isDossier;
  }
  
  public Integer getCanCancel() {
    return this.canCancel;
  }
  
  public void setCanCancel(Integer canCancel) {
    this.canCancel = canCancel;
  }
  
  public String getFormClassName() {
    return this.formClassName;
  }
  
  public void setFormClassName(String formClassName) {
    this.formClassName = formClassName;
  }
  
  public String getFormClassMethod() {
    return this.formClassMethod;
  }
  
  public void setFormClassMethod(String formClassMethod) {
    this.formClassMethod = formClassMethod;
  }
  
  public String getFormClassCompMethod() {
    return this.formClassCompMethod;
  }
  
  public void setFormClassCompMethod(String formClassCompMethod) {
    this.formClassCompMethod = formClassCompMethod;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getPrintFileSeeGroup() {
    return this.printFileSeeGroup;
  }
  
  public void setPrintFileSeeGroup(String printFileSeeGroup) {
    this.printFileSeeGroup = printFileSeeGroup;
  }
  
  public String getPrintFileSeeOrg() {
    return this.printFileSeeOrg;
  }
  
  public String getPrintFileSeePerson() {
    return this.printFileSeePerson;
  }
  
  public String getPrintFileSeeScope() {
    return this.printFileSeeScope;
  }
  
  public void setPrintFileSeeScope(String printFileSeeScope) {
    this.printFileSeeScope = printFileSeeScope;
  }
  
  public void setPrintFileSeePerson(String printFileSeePerson) {
    this.printFileSeePerson = printFileSeePerson;
  }
  
  public void setPrintFileSeeOrg(String printFileSeeOrg) {
    this.printFileSeeOrg = printFileSeeOrg;
  }
  
  public String getDossierFileExportScope() {
    return this.dossierFileExportScope;
  }
  
  public void setDossierFileExportScope(String dossierFileExportScope) {
    this.dossierFileExportScope = dossierFileExportScope;
  }
  
  public String getDossierFileExportGroup() {
    return this.dossierFileExportGroup;
  }
  
  public void setDossierFileExportGroup(String dossierFileExportGroup) {
    this.dossierFileExportGroup = dossierFileExportGroup;
  }
  
  public String getDossierFileExportOrg() {
    return this.dossierFileExportOrg;
  }
  
  public void setDossierFileExportOrg(String dossierFileExportOrg) {
    this.dossierFileExportOrg = dossierFileExportOrg;
  }
  
  public String getDossierFileExportPerson() {
    return this.dossierFileExportPerson;
  }
  
  public void setDossierFileExportPerson(String dossierFileExportPerson) {
    this.dossierFileExportPerson = dossierFileExportPerson;
  }
  
  public int getFormType() {
    return this.formType;
  }
  
  public void setFormType(int formType) {
    this.formType = formType;
  }
  
  public String getStartJSP() {
    return this.startJSP;
  }
  
  public void setStartJSP(String startJSP) {
    this.startJSP = startJSP;
  }
  
  public String getOptJSP() {
    return this.optJSP;
  }
  
  public void setOptJSP(String optJSP) {
    this.optJSP = optJSP;
  }
  
  public String getDossierFileOperScope() {
    return this.dossierFileOperScope;
  }
  
  public void setDossierFileOperScope(String dossierFileOperScope) {
    this.dossierFileOperScope = dossierFileOperScope;
  }
  
  public String getDossierFileOperOrg() {
    return this.dossierFileOperOrg;
  }
  
  public void setDossierFileOperOrg(String dossierFileOperOrg) {
    this.dossierFileOperOrg = dossierFileOperOrg;
  }
  
  public String getDossierFileOperGroup() {
    return this.dossierFileOperGroup;
  }
  
  public void setDossierFileOperGroup(String dossierFileOperGroup) {
    this.dossierFileOperGroup = dossierFileOperGroup;
  }
  
  public String getDossierFileOperPerson() {
    return this.dossierFileOperPerson;
  }
  
  public void setDossierFileOperPerson(String dossierFileOperPerson) {
    this.dossierFileOperPerson = dossierFileOperPerson;
  }
  
  public String getProcessAdminGroup() {
    return this.processAdminGroup;
  }
  
  public String getProcessAdminOrg() {
    return this.processAdminOrg;
  }
  
  public String getProcessAdminPerson() {
    return this.processAdminPerson;
  }
  
  public String getProcessAdminScope() {
    return this.processAdminScope;
  }
  
  public void setProcessAdminGroup(String processAdminGroup) {
    this.processAdminGroup = processAdminGroup;
  }
  
  public void setProcessAdminOrg(String processAdminOrg) {
    this.processAdminOrg = processAdminOrg;
  }
  
  public void setProcessAdminPerson(String processAdminPerson) {
    this.processAdminPerson = processAdminPerson;
  }
  
  public void setProcessAdminScope(String processAdminScope) {
    this.processAdminScope = processAdminScope;
  }
  
  public int getProcessStatus() {
    return this.processStatus;
  }
  
  public void setProcessStatus(int processStatus) {
    this.processStatus = processStatus;
  }
  
  public String getOwnerName() {
    return this.ownerName;
  }
  
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }
  
  public Long getOwnerId() {
    return this.ownerId;
  }
  
  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }
  
  public Long getOwnerOrgId() {
    return this.ownerOrgId;
  }
  
  public void setOwnerOrgId(Long ownerOrgId) {
    this.ownerOrgId = ownerOrgId;
  }
  
  public Date getLastUpdateTime() {
    return this.lastUpdateTime;
  }
  
  public void setLastUpdateTime(Date lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }
  
  public Date getProcessUseTime() {
    return this.processUseTime;
  }
  
  public void setProcessUseTime(Date processUseTime) {
    this.processUseTime = processUseTime;
  }
  
  public String getInfoChannelId() {
    return this.infoChannelId;
  }
  
  public void setInfoChannelId(String infoChannelId) {
    this.infoChannelId = infoChannelId;
  }
  
  public int getSendFileType() {
    return this.sendFileType;
  }
  
  public void setSendFileType(int sendFileType) {
    this.sendFileType = sendFileType;
  }
  
  public String getIsJx() {
    return this.isJx;
  }
  
  public void setIsJx(String isJx) {
    this.isJx = isJx;
  }
}
