package com.js.oa.routine.boardroom.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BoardRoomActionForm extends ActionForm {
  private String saveType;
  
  private String name;
  
  private String depict;
  
  private String location;
  
  private String capacitance;
  
  private Integer boardroomType = new Integer(0);
  
  private Float cost = Float.valueOf("0.00");
  
  private Integer emphasis = new Integer(0);
  
  private Integer limit;
  
  private String principal;
  
  private String principalName;
  
  private String remark;
  
  private String status;
  
  private String applyEmpName;
  
  private Long applyEmp;
  
  private String applyOrgName;
  
  private Long applyOrg;
  
  private String emcee;
  
  private String emceeName;
  
  private String motif;
  
  private Integer msg;
  
  private String type;
  
  private String attendee;
  
  private String attendeeEmpId;
  
  private String attendeeLeader;
  
  private String attendeeLeaderId;
  
  private String nonvoting;
  
  private String nonvotingEmpId;
  
  private String notePerson;
  
  private String notePersonId;
  
  private String fileNumber;
  
  private String senderName;
  
  private Long doorNumber;
  
  private Long controlNumber;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String linkTelephone;
  
  private Long points;
  
  private Long isVideo = new Long(0L);
  
  private Long maxNumber;
  
  private String workaddress;
  
  private String personNum;
  
  private String manageOrgName;
  
  private String manageOrg;
  
  private String boardroomCode;
  
  private Long relProjectId;
  
  private String applyPhone;
  
  private Integer boardroomOrder;
  
  private String useScope;
  
  private String useScopeId;
  
  public String getUseScope() {
    return this.useScope;
  }
  
  public void setUseScope(String useScope) {
    this.useScope = useScope;
  }
  
  public String getUseScopeId() {
    return this.useScopeId;
  }
  
  public void setUseScopeId(String useScopeId) {
    this.useScopeId = useScopeId;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public Integer getBoardroomType() {
    return this.boardroomType;
  }
  
  public void setBoardroomType(Integer boardroomType) {
    this.boardroomType = boardroomType;
  }
  
  public String getCapacitance() {
    return this.capacitance;
  }
  
  public void setCapacitance(String capacitance) {
    this.capacitance = capacitance;
  }
  
  public Float getCost() {
    return this.cost;
  }
  
  public void setCost(Float cost) {
    this.cost = cost;
  }
  
  public String getDepict() {
    return this.depict;
  }
  
  public void setDepict(String depict) {
    this.depict = depict;
  }
  
  public Integer getEmphasis() {
    return this.emphasis;
  }
  
  public void setEmphasis(Integer emphasis) {
    this.emphasis = emphasis;
  }
  
  public Integer getLimit() {
    return this.limit;
  }
  
  public void setLimit(Integer limit) {
    this.limit = limit;
  }
  
  public String getLocation() {
    return this.location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getPrincipal() {
    return this.principal;
  }
  
  public void setPrincipal(String principal) {
    this.principal = principal;
  }
  
  public String getPrincipalName() {
    return this.principalName;
  }
  
  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public Long getApplyEmp() {
    return this.applyEmp;
  }
  
  public void setApplyEmp(Long applyEmp) {
    this.applyEmp = applyEmp;
  }
  
  public String getApplyEmpName() {
    return this.applyEmpName;
  }
  
  public void setApplyEmpName(String applyEmpName) {
    this.applyEmpName = applyEmpName;
  }
  
  public Long getApplyOrg() {
    return this.applyOrg;
  }
  
  public void setApplyOrg(Long applyOrg) {
    this.applyOrg = applyOrg;
  }
  
  public String getApplyOrgName() {
    return this.applyOrgName;
  }
  
  public void setApplyOrgName(String applyOrgName) {
    this.applyOrgName = applyOrgName;
  }
  
  public String getEmcee() {
    return this.emcee;
  }
  
  public void setEmcee(String emcee) {
    this.emcee = emcee;
  }
  
  public String getEmceeName() {
    return this.emceeName;
  }
  
  public void setEmceeName(String emceeName) {
    this.emceeName = emceeName;
  }
  
  public String getMotif() {
    return this.motif;
  }
  
  public void setMotif(String motif) {
    this.motif = motif;
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
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getAttendee() {
    return this.attendee;
  }
  
  public void setAttendee(String attendee) {
    this.attendee = attendee;
  }
  
  public String getAttendeeEmpId() {
    return this.attendeeEmpId;
  }
  
  public void setAttendeeEmpId(String attendeeEmpId) {
    this.attendeeEmpId = attendeeEmpId;
  }
  
  public String getAttendeeLeader() {
    return this.attendeeLeader;
  }
  
  public void setAttendeeLeader(String attendeeLeader) {
    this.attendeeLeader = attendeeLeader;
  }
  
  public String getAttendeeLeaderId() {
    return this.attendeeLeaderId;
  }
  
  public void setAttendeeLeaderId(String attendeeLeaderId) {
    this.attendeeLeaderId = attendeeLeaderId;
  }
  
  public String getNonvoting() {
    return this.nonvoting;
  }
  
  public void setNonvoting(String nonvoting) {
    this.nonvoting = nonvoting;
  }
  
  public String getNonvotingEmpId() {
    return this.nonvotingEmpId;
  }
  
  public void setNonvotingEmpId(String nonvotingEmpId) {
    this.nonvotingEmpId = nonvotingEmpId;
  }
  
  public String getNotePerson() {
    return this.notePerson;
  }
  
  public void setNotePerson(String notePerson) {
    this.notePerson = notePerson;
  }
  
  public String getFileNumber() {
    return this.fileNumber;
  }
  
  public void setFileNumber(String fileNumber) {
    this.fileNumber = fileNumber;
  }
  
  public String getSenderName() {
    return this.senderName;
  }
  
  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }
  
  public Integer getMsg() {
    return this.msg;
  }
  
  public void setMsg(Integer msg) {
    this.msg = msg;
  }
  
  public Long getControlNumber() {
    return this.controlNumber;
  }
  
  public void setControlNumber(Long controlNumber) {
    this.controlNumber = controlNumber;
  }
  
  public Long getDoorNumber() {
    return this.doorNumber;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setDoorNumber(Long doorNumber) {
    this.doorNumber = doorNumber;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.saveType = null;
    this.name = null;
    this.depict = null;
    this.location = null;
    this.capacitance = null;
    this.boardroomType = new Integer(0);
    this.cost = Float.valueOf("0.00");
    this.emphasis = new Integer(0);
    this.limit = null;
    this.principal = null;
    this.principalName = null;
    this.remark = null;
    this.applyEmpName = null;
    this.applyEmp = null;
    this.applyOrgName = null;
    this.applyOrg = null;
    this.emcee = null;
    this.emceeName = null;
    this.motif = null;
    this.msg = new Integer(0);
    this.type = null;
    this.attendee = null;
    this.attendeeEmpId = null;
    this.attendeeLeader = null;
    this.attendeeLeaderId = null;
    this.nonvoting = null;
    this.nonvotingEmpId = null;
    this.notePerson = null;
    this.senderName = null;
    this.fileNumber = null;
    this.doorNumber = null;
    this.controlNumber = null;
    this.status = null;
    this.isVideo = new Long(0L);
  }
  
  public Long getIsVideo() {
    return this.isVideo;
  }
  
  public void setIsVideo(Long isVideo) {
    this.isVideo = isVideo;
  }
  
  public String getLinkTelephone() {
    return this.linkTelephone;
  }
  
  public void setLinkTelephone(String linkTelephone) {
    this.linkTelephone = linkTelephone;
  }
  
  public Long getMaxNumber() {
    return this.maxNumber;
  }
  
  public void setMaxNumber(Long maxNumber) {
    this.maxNumber = maxNumber;
  }
  
  public Long getPoints() {
    return this.points;
  }
  
  public String getWorkaddress() {
    return this.workaddress;
  }
  
  public String getPersonNum() {
    return this.personNum;
  }
  
  public String getManageOrgName() {
    return this.manageOrgName;
  }
  
  public String getManageOrg() {
    return this.manageOrg;
  }
  
  public String getBoardroomCode() {
    return this.boardroomCode;
  }
  
  public void setPoints(Long points) {
    this.points = points;
  }
  
  public void setWorkaddress(String workaddress) {
    this.workaddress = workaddress;
  }
  
  public void setPersonNum(String personNum) {
    this.personNum = personNum;
  }
  
  public void setManageOrgName(String manageOrgName) {
    this.manageOrgName = manageOrgName;
  }
  
  public void setManageOrg(String manageOrg) {
    this.manageOrg = manageOrg;
  }
  
  public void setBoardroomCode(String boardroomCode) {
    this.boardroomCode = boardroomCode;
  }
  
  public String getNotePersonId() {
    return this.notePersonId;
  }
  
  public void setNotePersonId(String notePersonId) {
    this.notePersonId = notePersonId;
  }
  
  public String getApplyPhone() {
    return this.applyPhone;
  }
  
  public void setApplyPhone(String applyPhone) {
    this.applyPhone = applyPhone;
  }
  
  public Integer getBoardroomOrder() {
    return this.boardroomOrder;
  }
  
  public void setBoardroomOrder(Integer boardroomOrder) {
    this.boardroomOrder = boardroomOrder;
  }
}
