package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BoardRoomApplyPO implements Serializable {
  private Long boardroomApplyId;
  
  private BoardRoomPO boardroom;
  
  private String motif;
  
  private String emcee;
  
  private String emceeName;
  
  private String agenda;
  
  private String attendee;
  
  private String attendeeEmpId;
  
  private String startTime;
  
  private String endTime;
  
  private Date destineDate = null;
  
  private Long applyEmp;
  
  private String applyEmpName;
  
  private Long applyOrg;
  
  private String applyOrgName;
  
  private Integer status;
  
  private Integer msg;
  
  private String depict;
  
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
  
  private BoardRoomApplyTypePO bdroomAppType;
  
  private Set bdRoomAppAccessory = null;
  
  private String domainId;
  
  private String boardEquipment;
  
  private String linkTelephone;
  
  private Long points;
  
  private Date applyDate = null;
  
  private Long personNum;
  
  private Set meetingTime = null;
  
  private Set persons = null;
  
  private String boardroomCode;
  
  private Long relProjectId;
  
  private String summary;
  
  private BoardRoomRegularPO regularPO = null;
  
  private Integer ifRegular;
  
  private String boardLayout;
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public BoardRoomApplyPO(BoardRoomPO boardroom, String motif, String emcee, String emceeName, String agenda, String attendee, String attendeeEmpId, String startTime, String endTime, Date destineDate, Long applyEmp, String applyEmpName, Long applyOrg, String applyOrgName, Integer status, Integer msg, String depict, String attendeeLeader, String attendeeLeaderId, String nonvoting, String nonvotingEmpId, String notePerson, String fileNumber, String senderName, Long controlNumber, Long doorNumber, BoardRoomApplyTypePO bdroomAppType, Set bdRoomAppAccessory, String notePersonId) {
    this.boardroom = boardroom;
    this.motif = motif;
    this.emcee = emcee;
    this.emceeName = emceeName;
    this.agenda = agenda;
    this.attendee = attendee;
    this.attendeeEmpId = attendeeEmpId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.destineDate = destineDate;
    this.applyEmp = applyEmp;
    this.applyEmpName = applyEmpName;
    this.applyOrg = applyOrg;
    this.applyOrgName = applyOrgName;
    this.status = status;
    this.msg = msg;
    this.depict = depict;
    this.attendeeLeader = attendeeLeader;
    this.attendeeLeaderId = attendeeLeaderId;
    this.nonvoting = nonvoting;
    this.nonvotingEmpId = nonvotingEmpId;
    this.notePerson = notePerson;
    this.fileNumber = fileNumber;
    this.senderName = senderName;
    this.doorNumber = doorNumber;
    this.controlNumber = controlNumber;
    this.bdroomAppType = bdroomAppType;
    this.bdRoomAppAccessory = bdRoomAppAccessory;
    this.notePersonId = notePersonId;
  }
  
  public BoardRoomApplyPO() {}
  
  public String getAgenda() {
    return this.agenda;
  }
  
  public void setAgenda(String agenda) {
    this.agenda = agenda;
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
  
  public BoardRoomPO getBoardroom() {
    return this.boardroom;
  }
  
  public void setBoardroom(BoardRoomPO boardroom) {
    this.boardroom = boardroom;
  }
  
  public Long getBoardroomApplyId() {
    return this.boardroomApplyId;
  }
  
  public void setBoardroomApplyId(Long boardroomApplyId) {
    this.boardroomApplyId = boardroomApplyId;
  }
  
  public String getDepict() {
    return this.depict;
  }
  
  public void setDepict(String depict) {
    this.depict = depict;
  }
  
  public Date getDestineDate() {
    return this.destineDate;
  }
  
  public void setDestineDate(Date destineDate) {
    this.destineDate = destineDate;
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
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public String getMotif() {
    return this.motif;
  }
  
  public void setMotif(String motif) {
    this.motif = motif;
  }
  
  public Integer getMsg() {
    return this.msg;
  }
  
  public void setMsg(Integer msg) {
    this.msg = msg;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
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
  
  public BoardRoomApplyTypePO getBdroomAppType() {
    return this.bdroomAppType;
  }
  
  public void setBdroomAppType(BoardRoomApplyTypePO bdroomAppType) {
    this.bdroomAppType = bdroomAppType;
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
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("boardroomApplyId", getBoardroomApplyId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof BoardRoomApplyPO))
      return false; 
    BoardRoomApplyPO castOther = (BoardRoomApplyPO)other;
    return (new EqualsBuilder())
      .append(getBoardroomApplyId(), castOther.getBoardroomApplyId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getBoardroomApplyId())
      .toHashCode();
  }
  
  public Set getBdRoomAppAccessory() {
    return this.bdRoomAppAccessory;
  }
  
  public void setBdRoomAppAccessory(Set bdRoomAppAccessory) {
    this.bdRoomAppAccessory = bdRoomAppAccessory;
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
  
  public Long getControlNumber() {
    return this.controlNumber;
  }
  
  public void setControlNumber(Long controlNumber) {
    this.controlNumber = controlNumber;
  }
  
  public Long getDoorNumber() {
    return this.doorNumber;
  }
  
  public void setDoorNumber(Long doorNumber) {
    this.doorNumber = doorNumber;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getBoardEquipment() {
    return this.boardEquipment;
  }
  
  public void setBoardEquipment(String boardEquipment) {
    this.boardEquipment = boardEquipment;
  }
  
  public String getLinkTelephone() {
    return this.linkTelephone;
  }
  
  public void setLinkTelephone(String linkTelephone) {
    this.linkTelephone = linkTelephone;
  }
  
  public Long getPoints() {
    return this.points;
  }
  
  public void setPoints(Long points) {
    this.points = points;
  }
  
  public Date getApplyDate() {
    return this.applyDate;
  }
  
  public Long getPersonNum() {
    return this.personNum;
  }
  
  public Set getMeetingTime() {
    return this.meetingTime;
  }
  
  public Set getPersons() {
    return this.persons;
  }
  
  public String getBoardroomCode() {
    return this.boardroomCode;
  }
  
  public void setApplyDate(Date applyDate) {
    this.applyDate = applyDate;
  }
  
  public void setPersonNum(Long personNum) {
    this.personNum = personNum;
  }
  
  public void setMeetingTime(Set meetingTime) {
    this.meetingTime = meetingTime;
  }
  
  public void setPersons(Set persons) {
    this.persons = persons;
  }
  
  public void setBoardroomCode(String boardroomCode) {
    this.boardroomCode = boardroomCode;
  }
  
  public String getSummary() {
    return this.summary;
  }
  
  public void setSummary(String summary) {
    this.summary = summary;
  }
  
  public String getNotePersonId() {
    return this.notePersonId;
  }
  
  public void setNotePersonId(String notePersonId) {
    this.notePersonId = notePersonId;
  }
  
  public BoardRoomRegularPO getRegularPO() {
    return this.regularPO;
  }
  
  public void setRegularPO(BoardRoomRegularPO regularPO) {
    this.regularPO = regularPO;
  }
  
  public Integer getIfRegular() {
    return this.ifRegular;
  }
  
  public void setIfRegular(Integer ifRegular) {
    this.ifRegular = ifRegular;
  }
  
  public String getBoardLayout() {
    return this.boardLayout;
  }
  
  public void setBoardLayout(String boardLayout) {
    this.boardLayout = boardLayout;
  }
}
