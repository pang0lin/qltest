package com.js.oa.jsflow.action;

import com.js.oa.jsflow.po.WFPackagePO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WFProcessActionForm extends ActionForm {
  private String packageId;
  
  private String processDescription;
  
  private String useGroup;
  
  private String useOrg;
  
  private String usePerson;
  
  private String workFlowProcessName;
  
  private String processCreateName;
  
  private String processType;
  
  private String userScope;
  
  private String accessDatabaseId;
  
  private String processId;
  
  private String dossierFileSeeScope;
  
  private String dossierFileSeeGroup;
  
  private String dossierFileSeeOrg;
  
  private String dossierFileSeePerson;
  
  private String printFileSeeScope;
  
  private String printFileSeeOrg;
  
  private String printFileSeeGroup;
  
  private String printFileSeePerson;
  
  private String dossierFileOperScope;
  
  private String dossierFileOperGroup;
  
  private String dossierFileOperOrg;
  
  private String dossierFileOperPerson;
  
  private String dossierFileExportScope;
  
  private String dossierFileExportGroup;
  
  private String dossierFileExportOrg;
  
  private String dossierFileExportPerson;
  
  private String formType;
  
  private String processAdminScope;
  
  private String processAdminOrg;
  
  private String processAdminGroup;
  
  private String processAdminPerson;
  
  private int orderCode;
  
  private Long createdEmp;
  
  private String createUserName;
  
  private String fileoprName;
  
  private String processPackage;
  
  private ArrayList<WFPackagePO> wfPackageList;
  
  private String infoChannelId;
  
  private String processDeadline;
  
  private String processDeadlineType;
  
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
  
  public ArrayList<WFPackagePO> getWfPackageList() {
    return this.wfPackageList;
  }
  
  public void setWfPackageList(ArrayList<WFPackagePO> wfPackageList) {
    this.wfPackageList = wfPackageList;
  }
  
  public String getProcessPackage() {
    return this.processPackage;
  }
  
  public void setProcessPackage(String processPackage) {
    this.processPackage = processPackage;
  }
  
  public String getFileoprName() {
    return this.fileoprName;
  }
  
  public void setFileoprName(String fileoprName) {
    this.fileoprName = fileoprName;
  }
  
  public String getPackageId() {
    return this.packageId;
  }
  
  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }
  
  public String getProcessDescription() {
    return this.processDescription;
  }
  
  public void setProcessDescription(String processDescription) {
    this.processDescription = processDescription;
  }
  
  public String getUseGroup() {
    return this.useGroup;
  }
  
  public void setUseGroup(String useGroup) {
    this.useGroup = useGroup;
  }
  
  public String getUseOrg() {
    return this.useOrg;
  }
  
  public void setUseOrg(String useOrg) {
    this.useOrg = useOrg;
  }
  
  public String getUsePerson() {
    return this.usePerson;
  }
  
  public void setUsePerson(String usePerson) {
    this.usePerson = usePerson;
  }
  
  public String getWorkFlowProcessName() {
    return this.workFlowProcessName;
  }
  
  public void setWorkFlowProcessName(String workFlowProcessName) {
    this.workFlowProcessName = workFlowProcessName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getProcessType() {
    return this.processType;
  }
  
  public void setProcessType(String processType) {
    this.processType = processType;
  }
  
  public String getUserScope() {
    return this.userScope;
  }
  
  public void setUserScope(String userScope) {
    this.userScope = userScope;
  }
  
  public String getAccessDatabaseId() {
    return this.accessDatabaseId;
  }
  
  public void setAccessDatabaseId(String accessDatabaseId) {
    this.accessDatabaseId = accessDatabaseId;
  }
  
  public String getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(String processId) {
    this.processId = processId;
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
  
  public String getPrintFileSeeScope() {
    return this.printFileSeeScope;
  }
  
  public void setPrintFileSeeScope(String printFileSeeScope) {
    this.printFileSeeScope = printFileSeeScope;
  }
  
  public String getPrintFileSeeOrg() {
    return this.printFileSeeOrg;
  }
  
  public void setPrintFileSeeOrg(String printFileSeeOrg) {
    this.printFileSeeOrg = printFileSeeOrg;
  }
  
  public String getPrintFileSeeGroup() {
    return this.printFileSeeGroup;
  }
  
  public void setPrintFileSeeGroup(String printFileSeeGroup) {
    this.printFileSeeGroup = printFileSeeGroup;
  }
  
  public String getPrintFileSeePerson() {
    return this.printFileSeePerson;
  }
  
  public void setPrintFileSeePerson(String printFileSeePerson) {
    this.printFileSeePerson = printFileSeePerson;
  }
  
  public String getFormType() {
    return this.formType;
  }
  
  public void setFormType(String formType) {
    this.formType = formType;
  }
  
  public String getDossierFileOperGroup() {
    return this.dossierFileOperGroup;
  }
  
  public void setDossierFileOperGroup(String dossierFileOperGroup) {
    this.dossierFileOperGroup = dossierFileOperGroup;
  }
  
  public String getDossierFileOperOrg() {
    return this.dossierFileOperOrg;
  }
  
  public void setDossierFileOperOrg(String dossierFileOperOrg) {
    this.dossierFileOperOrg = dossierFileOperOrg;
  }
  
  public String getDossierFileOperPerson() {
    return this.dossierFileOperPerson;
  }
  
  public void setDossierFileOperPerson(String dossierFileOperPerson) {
    this.dossierFileOperPerson = dossierFileOperPerson;
  }
  
  public String getDossierFileOperScope() {
    return this.dossierFileOperScope;
  }
  
  public void setDossierFileOperScope(String dossierFileOperScope) {
    this.dossierFileOperScope = dossierFileOperScope;
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
  
  public String getProcessAdminScope() {
    return this.processAdminScope;
  }
  
  public void setProcessAdminScope(String processAdminScope) {
    this.processAdminScope = processAdminScope;
  }
  
  public String getProcessAdminOrg() {
    return this.processAdminOrg;
  }
  
  public void setProcessAdminOrg(String processAdminOrg) {
    this.processAdminOrg = processAdminOrg;
  }
  
  public String getProcessAdminGroup() {
    return this.processAdminGroup;
  }
  
  public void setProcessAdminGroup(String processAdminGroup) {
    this.processAdminGroup = processAdminGroup;
  }
  
  public String getProcessAdminPerson() {
    return this.processAdminPerson;
  }
  
  public void setProcessAdminPerson(String processAdminPerson) {
    this.processAdminPerson = processAdminPerson;
  }
  
  public int getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(int orderCode) {
    this.orderCode = orderCode;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getCreateUserName() {
    return this.createUserName;
  }
  
  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }
  
  public String getProcessCreateName() {
    return this.processCreateName;
  }
  
  public void setProcessCreateName(String processCreateName) {
    this.processCreateName = processCreateName;
  }
  
  public String getInfoChannelId() {
    return this.infoChannelId;
  }
  
  public void setInfoChannelId(String infoChannelId) {
    this.infoChannelId = infoChannelId;
  }
}
