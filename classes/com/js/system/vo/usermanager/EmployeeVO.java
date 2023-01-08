package com.js.system.vo.usermanager;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EmployeeVO implements Serializable {
  private Long empId;
  
  private String empOrgIdString;
  
  private String empResumeNum;
  
  private String empName;
  
  private String empNumber;
  
  private Date empFireDate = null;
  
  private Date empHireDate = null;
  
  private Date empBirth = null;
  
  private String empPhoto;
  
  private byte empSex;
  
  private String empNation;
  
  private byte empIsMarriage;
  
  private String empAddress;
  
  private String empBusinessPhone;
  
  private String empBusinessFax;
  
  private String empPhone;
  
  private String empMobilePhone;
  
  private String empEmail;
  
  private String empEmail2;
  
  private String empEmail3;
  
  private String empWebAddress;
  
  private String empEnglishName;
  
  private String empInterest;
  
  private short empHeight;
  
  private short empWeight;
  
  private String empBloodType;
  
  private String empNativePlace;
  
  private String empCountry;
  
  private String empState;
  
  private String empCounty;
  
  private String empPolity;
  
  private String empWorkExperience;
  
  private String empTrainExperience;
  
  private String empEducationExperience;
  
  private String empStudyExperience;
  
  private String empDegree;
  
  private String empPosition;
  
  private String empDuty;
  
  private String empPostTitle;
  
  private String empLivingPhoto;
  
  private String empGnome;
  
  private String empDescribe;
  
  private byte empStatus;
  
  private long createdOrg;
  
  private String browseRange;
  
  private String userAccounts;
  
  private String userPassword;
  
  private byte userIsActive;
  
  private byte userIsSuper;
  
  private Date userSuperBegin = null;
  
  private Date userSuperEnd = null;
  
  private Set organizations = null;
  
  private Set roles = null;
  
  private Set groups = null;
  
  private Set rightScopes = null;
  
  private Set mailUsers = null;
  
  private byte userIsDeleted;
  
  private String insuranceNumber;
  
  private String postTitleSeries;
  
  private String postCompetence;
  
  private String postLevel;
  
  private Date postGainTime = null;
  
  private String currentPostTitle;
  
  private String otherPostTitle;
  
  private String certifyNumber;
  
  private String fireReason;
  
  private String contractAbout;
  
  private String empLeaderName;
  
  private String empLeaderId;
  
  private String browseRangeName;
  
  private String empHeadImg;
  
  private String empSignImg;
  
  private String familyMember;
  
  private String empZipCode;
  
  private String userOrderCode;
  
  private String keySerial;
  
  private String keyValidate;
  
  private Date graduateDate = null;
  
  private Date hirePackStartDate = null;
  
  private Date hirePackEndDate = null;
  
  private Date fosterPackStartDate = null;
  
  private Date fosterPackEndDate = null;
  
  private Date workPackStartDate = null;
  
  private Date workPackEndDate = null;
  
  private Date wpContStartDate = null;
  
  private Date wpContEndtDate = null;
  
  private Date intoCompanyDate = null;
  
  private String endowmentInsurance;
  
  private String medicare;
  
  private String accumulationFund;
  
  private String userSimpleName;
  
  private String mailDefaultDomain;
  
  private String mailDomain;
  
  private String mailPost;
  
  private String weixinPost;
  
  private String weixinId;
  
  private String dingdingPost;
  
  private String introducer;
  
  private String compensation;
  
  private String empIdCard;
  
  private Integer userIsFormalUser;
  
  private String dignity;
  
  private String section;
  
  private String speciality;
  
  private String partyDate;
  
  private String jobStatus;
  
  private String domainId;
  
  private String rtxIsLogin;
  
  private String olRemoteMac;
  
  private String skin;
  
  private String isChangePwd;
  
  private String mailboxSize;
  
  private String curStatus;
  
  private String userDefineStatus;
  
  private String netDiskSize;
  
  private String imId;
  
  private Set competenceVO = null;
  
  private Set contractVO = null;
  
  private Set edustoryVO = null;
  
  private Set trainhistoryVO = null;
  
  private String sidelineOrg;
  
  private String sidelineOrgName;
  
  private Long workAddress;
  
  private String empDutyLevel;
  
  private String signatureImgName;
  
  private String signatureImgSaveName;
  
  private String deptLeader;
  
  private String serial;
  
  private String serialPwd;
  
  private Long personalKind;
  
  private Date zhuanzhengDate = null;
  
  private Date lizhiDate = null;
  
  private String empFireType;
  
  private String hujiAddress;
  
  private Integer isdimissionprove;
  
  private String speciality1;
  
  private String speciality2;
  
  private String language1;
  
  private String language2;
  
  private String language3;
  
  private String langlevel1;
  
  private String langlevel2;
  
  private String langlevel3;
  
  private String zhicheng;
  
  private String guid;
  
  private int userOnline;
  
  private Long empPositionId;
  
  private String empPositionOtherId;
  
  private String grantRange;
  
  private String grantRangeName;
  
  private String mobileLog;
  
  private String initPassword = "";
  
  private String initAccount = "";
  
  private String lastupdate = "";
  
  private String opinionRemind = "";
  
  private String wm_code = "";
  
  public String getWm_code() {
    return this.wm_code;
  }
  
  public void setWm_code(String wm_code) {
    this.wm_code = wm_code;
  }
  
  public String getOpinionRemind() {
    return this.opinionRemind;
  }
  
  public void setOpinionRemind(String opinionRemind) {
    this.opinionRemind = opinionRemind;
  }
  
  public String getLastupdate() {
    return this.lastupdate;
  }
  
  public void setLastupdate(String lastupdate) {
    this.lastupdate = lastupdate;
  }
  
  public String getGrantRange() {
    return this.grantRange;
  }
  
  public void setGrantRange(String grantRange) {
    this.grantRange = grantRange;
  }
  
  public String getGrantRangeName() {
    return this.grantRangeName;
  }
  
  public void setGrantRangeName(String grantRangeName) {
    this.grantRangeName = grantRangeName;
  }
  
  public EmployeeVO(String empResumeNum, String empName, String empNumber, Date empFireDate, Date empHireDate, Date empBirth, String empPhoto, byte empSex, String empNation, byte empIsMarriage, String empAddress, String empZipCode, String empPhone, String empMobilePhone, String empBusinessPhone, String empBusinessFax, String empEmail, String empEnglishName, String empEmail2, String empEmail3, String empInterest, short empHeight, short empWeight, String empBloodType, String empNativePlace, String empCountry, String empState, String empCounty, String empPolity, String empIdCard, String empWorkExperience, String empTrainExperience, String empEducationExperience, String empStudyExperience, String empDegree, String empPosition, String empDuty, String empPostTitle, byte empStatus, String userAccounts, String userPassword, byte userIsActive, Integer userIsFormalUser, byte userIsSuper, Date userSuperBegin, Date userSuperEnd, Set organizations, String lastupdate, String opinionRemind, Set roles, Set groups, Set rightScopes, Set mailUsers, byte userIsDeleted) {
    this.empOrgIdString = this.empOrgIdString;
    this.empResumeNum = empResumeNum;
    this.empName = empName;
    this.empNumber = empNumber;
    this.empFireDate = empFireDate;
    this.empHireDate = empHireDate;
    this.empBirth = empBirth;
    this.empPhoto = empPhoto;
    this.empSex = empSex;
    this.empNation = empNation;
    this.empIsMarriage = empIsMarriage;
    this.empAddress = empAddress;
    this.empZipCode = empZipCode;
    this.empPhone = empPhone;
    this.empMobilePhone = empMobilePhone;
    this.empBusinessPhone = empBusinessPhone;
    this.empBusinessFax = empBusinessFax;
    this.empEmail = empEmail;
    this.empEmail2 = empEmail2;
    this.empEmail3 = empEmail3;
    this.empEnglishName = empEnglishName;
    this.empInterest = empInterest;
    this.empHeight = empHeight;
    this.empWeight = empWeight;
    this.empBloodType = empBloodType;
    this.empNativePlace = empNativePlace;
    this.empCountry = empCountry;
    this.empState = empState;
    this.empCounty = empCounty;
    this.empPolity = empPolity;
    this.empIdCard = empIdCard;
    this.empWorkExperience = empWorkExperience;
    this.empTrainExperience = empTrainExperience;
    this.empEducationExperience = empEducationExperience;
    this.empStudyExperience = empStudyExperience;
    this.empDegree = empDegree;
    this.empPosition = empPosition;
    this.empDuty = empDuty;
    this.empPostTitle = empPostTitle;
    this.empStatus = empStatus;
    this.organizations = organizations;
    this.lastupdate = lastupdate;
    this.roles = roles;
    this.groups = groups;
    this.rightScopes = rightScopes;
    this.mailUsers = mailUsers;
    this.userAccounts = userAccounts;
    this.userPassword = userPassword;
    this.userIsActive = userIsActive;
    this.userIsFormalUser = userIsFormalUser;
    this.userIsSuper = userIsSuper;
    this.userSuperBegin = userSuperBegin;
    this.userSuperEnd = userSuperEnd;
    this.userIsDeleted = userIsDeleted;
    this.opinionRemind = opinionRemind;
  }
  
  public void setOrganizations(Set organizations) {
    this.organizations = organizations;
  }
  
  public Set getOrganizations() {
    return this.organizations;
  }
  
  public void setRoles(Set roles) {
    this.roles = roles;
  }
  
  public Set getRoles() {
    return this.roles;
  }
  
  public EmployeeVO() {}
  
  public EmployeeVO(String empName) {
    this.empName = empName;
  }
  
  public long getEmpId() {
    return this.empId.longValue();
  }
  
  public void setEmpId(long empId) {
    this.empId = Long.valueOf(empId);
  }
  
  public String getEmpResumeNum() {
    return this.empResumeNum;
  }
  
  public void setEmpResumeNum(String empResumeNum) {
    this.empResumeNum = empResumeNum;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpNumber() {
    return this.empNumber;
  }
  
  public void setEmpNumber(String empNumber) {
    this.empNumber = empNumber;
  }
  
  public Date getEmpFireDate() {
    return this.empFireDate;
  }
  
  public void setEmpFireDate(Date empFireDate) {
    this.empFireDate = empFireDate;
  }
  
  public Date getEmpHireDate() {
    return this.empHireDate;
  }
  
  public void setEmpHireDate(Date empHireDate) {
    this.empHireDate = empHireDate;
  }
  
  public Date getEmpBirth() {
    return this.empBirth;
  }
  
  public void setEmpBirth(Date empBirth) {
    this.empBirth = empBirth;
  }
  
  public String getEmpPhoto() {
    return this.empPhoto;
  }
  
  public void setEmpPhoto(String empPhoto) {
    this.empPhoto = empPhoto;
  }
  
  public byte getEmpSex() {
    return this.empSex;
  }
  
  public void setEmpSex(byte empSex) {
    this.empSex = empSex;
  }
  
  public String getEmpNation() {
    return this.empNation;
  }
  
  public void setEmpNation(String empNation) {
    this.empNation = empNation;
  }
  
  public byte getEmpIsMarriage() {
    return this.empIsMarriage;
  }
  
  public void setEmpIsMarriage(byte empIsMarriage) {
    this.empIsMarriage = empIsMarriage;
  }
  
  public String getEmpZipCode() {
    return this.empZipCode;
  }
  
  public void setEmpZipCode(String empZipCode) {
    this.empZipCode = empZipCode;
  }
  
  public String getEmpAddress() {
    return this.empAddress;
  }
  
  public void setEmpAddress(String empAddress) {
    this.empAddress = empAddress;
  }
  
  public String getEmpPhone() {
    return this.empPhone;
  }
  
  public void setEmpPhone(String empPhone) {
    this.empPhone = empPhone;
  }
  
  public String getEmpMobilePhone() {
    return this.empMobilePhone;
  }
  
  public void setEmpMobilePhone(String empMobilePhone) {
    this.empMobilePhone = empMobilePhone;
  }
  
  public String getEmpEmail() {
    return this.empEmail;
  }
  
  public void setEmpEmail(String empEmail) {
    this.empEmail = empEmail;
  }
  
  public String getEmpEnglishName() {
    return this.empEnglishName;
  }
  
  public void setEmpEnglishName(String empEnglishName) {
    this.empEnglishName = empEnglishName;
  }
  
  public String getEmpInterest() {
    return this.empInterest;
  }
  
  public void setEmpInterest(String empInterest) {
    this.empInterest = empInterest;
  }
  
  public short getEmpHeight() {
    return this.empHeight;
  }
  
  public void setEmpHeight(short empHeight) {
    this.empHeight = empHeight;
  }
  
  public short getEmpWeight() {
    return this.empWeight;
  }
  
  public void setEmpWeight(short empWeight) {
    this.empWeight = empWeight;
  }
  
  public String getEmpBloodType() {
    return this.empBloodType;
  }
  
  public void setEmpBloodType(String empBloodType) {
    this.empBloodType = empBloodType;
  }
  
  public String getEmpNativePlace() {
    return this.empNativePlace;
  }
  
  public void setEmpNativePlace(String empNativePlace) {
    this.empNativePlace = empNativePlace;
  }
  
  public String getEmpPolity() {
    return this.empPolity;
  }
  
  public void setEmpPolity(String empPolity) {
    this.empPolity = empPolity;
  }
  
  public String getEmpIdCard() {
    return this.empIdCard;
  }
  
  public void setEmpIdCard(String empIdCard) {
    this.empIdCard = empIdCard;
  }
  
  public String getEmpWorkExperience() {
    return this.empWorkExperience;
  }
  
  public void setEmpWorkExperience(String empWorkExperience) {
    this.empWorkExperience = empWorkExperience;
  }
  
  public String getEmpTrainExperience() {
    return this.empTrainExperience;
  }
  
  public void setEmpTrainExperience(String empTrainExperience) {
    this.empTrainExperience = empTrainExperience;
  }
  
  public String getEmpEducationExperience() {
    return this.empEducationExperience;
  }
  
  public void setEmpEducationExperience(String empEducationExperience) {
    this.empEducationExperience = empEducationExperience;
  }
  
  public String getEmpStudyExperience() {
    return this.empStudyExperience;
  }
  
  public void setEmpStudyExperience(String empStudyExperience) {
    this.empStudyExperience = empStudyExperience;
  }
  
  public String getEmpDegree() {
    return this.empDegree;
  }
  
  public void setEmpDegree(String empDegree) {
    this.empDegree = empDegree;
  }
  
  public String getEmpPosition() {
    return this.empPosition;
  }
  
  public void setEmpPosition(String empPosition) {
    this.empPosition = empPosition;
  }
  
  public String getEmpDuty() {
    return this.empDuty;
  }
  
  public void setEmpDuty(String empDuty) {
    this.empDuty = empDuty;
  }
  
  public String getEmpPostTitle() {
    return this.empPostTitle;
  }
  
  public void setEmpPostTitle(String empPostTitle) {
    this.empPostTitle = empPostTitle;
  }
  
  public byte getEmpStatus() {
    return this.empStatus;
  }
  
  public void setEmpStatus(byte empStatus) {
    this.empStatus = empStatus;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("empId", getEmpId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EmployeeVO))
      return false; 
    EmployeeVO castOther = (EmployeeVO)other;
    return (new EqualsBuilder())
      .append(getEmpId(), castOther.getEmpId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEmpId())
      .toHashCode();
  }
  
  public Set getGroups() {
    return this.groups;
  }
  
  public void setGroups(Set groups) {
    this.groups = groups;
  }
  
  public Set getRightScopes() {
    return this.rightScopes;
  }
  
  public void setRightScopes(Set rightScopes) {
    this.rightScopes = rightScopes;
  }
  
  public String getUserAccounts() {
    return this.userAccounts;
  }
  
  public void setUserAccounts(String userAccounts) {
    this.userAccounts = userAccounts;
  }
  
  public void setUserIsActive(byte userIsActive) {
    this.userIsActive = userIsActive;
  }
  
  public byte getUserIsActive() {
    return this.userIsActive;
  }
  
  public Integer getUserIsFormalUser() {
    return this.userIsFormalUser;
  }
  
  public byte getUserIsSuper() {
    return this.userIsSuper;
  }
  
  public String getUserPassword() {
    return this.userPassword;
  }
  
  public Date getUserSuperBegin() {
    return this.userSuperBegin;
  }
  
  public Date getUserSuperEnd() {
    return this.userSuperEnd;
  }
  
  public void setUserSuperEnd(Date userSuperEnd) {
    this.userSuperEnd = userSuperEnd;
  }
  
  public void setUserSuperBegin(Date userSuperBegin) {
    this.userSuperBegin = userSuperBegin;
  }
  
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }
  
  public void setUserIsSuper(byte userIsSuper) {
    this.userIsSuper = userIsSuper;
  }
  
  public void setUserIsFormalUser(Integer userIsFormalUser) {
    this.userIsFormalUser = userIsFormalUser;
  }
  
  public byte getUserIsDeleted() {
    return this.userIsDeleted;
  }
  
  public void setUserIsDeleted(byte userIsDeleted) {
    this.userIsDeleted = userIsDeleted;
  }
  
  public String getEmpOrgIdString() {
    return this.empOrgIdString;
  }
  
  public void setEmpOrgIdString(String empOrgIdString) {
    this.empOrgIdString = empOrgIdString;
  }
  
  public void removeRole() {
    this.roles = null;
  }
  
  public void setMailUsers(Set mailUsers) {
    this.mailUsers = mailUsers;
  }
  
  public Set getMailUsers() {
    return this.mailUsers;
  }
  
  public String getEmpBusinessFax() {
    return this.empBusinessFax;
  }
  
  public String getEmpBusinessPhone() {
    return this.empBusinessPhone;
  }
  
  public String getEmpCountry() {
    return this.empCountry;
  }
  
  public String getEmpCounty() {
    return this.empCounty;
  }
  
  public String getEmpEmail2() {
    return this.empEmail2;
  }
  
  public String getEmpEmail3() {
    return this.empEmail3;
  }
  
  public String getEmpState() {
    return this.empState;
  }
  
  public void setEmpState(String empState) {
    this.empState = empState;
  }
  
  public void setEmpEmail3(String empEmail3) {
    this.empEmail3 = empEmail3;
  }
  
  public void setEmpEmail2(String empEmail2) {
    this.empEmail2 = empEmail2;
  }
  
  public void setEmpCounty(String empCounty) {
    this.empCounty = empCounty;
  }
  
  public void setEmpCountry(String empCountry) {
    this.empCountry = empCountry;
  }
  
  public void setEmpBusinessPhone(String empBusinessPhone) {
    this.empBusinessPhone = empBusinessPhone;
  }
  
  public void setEmpBusinessFax(String empBusinessFax) {
    this.empBusinessFax = empBusinessFax;
  }
  
  public String getEmpDescribe() {
    return this.empDescribe;
  }
  
  public void setEmpDescribe(String empDescribe) {
    this.empDescribe = empDescribe;
  }
  
  public void setEmpGnome(String empGnome) {
    this.empGnome = empGnome;
  }
  
  public String getEmpGnome() {
    return this.empGnome;
  }
  
  public String getEmpLivingPhoto() {
    return this.empLivingPhoto;
  }
  
  public void setEmpLivingPhoto(String empLivingPhoto) {
    this.empLivingPhoto = empLivingPhoto;
  }
  
  public String getBrowseRange() {
    return this.browseRange;
  }
  
  public void setBrowseRange(String browseRange) {
    this.browseRange = browseRange;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public String getInsuranceNumber() {
    return this.insuranceNumber;
  }
  
  public void setInsuranceNumber(String insuranceNumber) {
    this.insuranceNumber = insuranceNumber;
  }
  
  public String getCertifyNumber() {
    return this.certifyNumber;
  }
  
  public String getContractAbout() {
    return this.contractAbout;
  }
  
  public String getCurrentPostTitle() {
    return this.currentPostTitle;
  }
  
  public void setCurrentPostTitle(String currentPostTitle) {
    this.currentPostTitle = currentPostTitle;
  }
  
  public void setContractAbout(String contractAbout) {
    this.contractAbout = contractAbout;
  }
  
  public void setCertifyNumber(String certifyNumber) {
    this.certifyNumber = certifyNumber;
  }
  
  public void setFireReason(String fireReason) {
    this.fireReason = fireReason;
  }
  
  public void setOtherPostTitle(String otherPostTitle) {
    this.otherPostTitle = otherPostTitle;
  }
  
  public void setPostCompetence(String postCompetence) {
    this.postCompetence = postCompetence;
  }
  
  public void setPostGainTime(Date postGainTime) {
    this.postGainTime = postGainTime;
  }
  
  public void setPostLevel(String postLevel) {
    this.postLevel = postLevel;
  }
  
  public void setPostTitleSeries(String postTitleSeries) {
    this.postTitleSeries = postTitleSeries;
  }
  
  public String getPostTitleSeries() {
    return this.postTitleSeries;
  }
  
  public String getPostLevel() {
    return this.postLevel;
  }
  
  public Date getPostGainTime() {
    return this.postGainTime;
  }
  
  public String getPostCompetence() {
    return this.postCompetence;
  }
  
  public String getOtherPostTitle() {
    return this.otherPostTitle;
  }
  
  public String getFireReason() {
    return this.fireReason;
  }
  
  public String getEmpLeaderName() {
    return this.empLeaderName;
  }
  
  public void setEmpLeaderName(String empLeaderName) {
    this.empLeaderName = empLeaderName;
  }
  
  public String getEmpLeaderId() {
    return this.empLeaderId;
  }
  
  public void setEmpLeaderId(String empLeaderId) {
    this.empLeaderId = empLeaderId;
  }
  
  public String getEmpWebAddress() {
    return this.empWebAddress;
  }
  
  public void setEmpWebAddress(String empWebAddress) {
    this.empWebAddress = empWebAddress;
  }
  
  public String getBrowseRangeName() {
    return this.browseRangeName;
  }
  
  public String getDingdingPost() {
    return this.dingdingPost;
  }
  
  public void setDingdingPost(String dingdingPost) {
    this.dingdingPost = dingdingPost;
  }
  
  public void setBrowseRangeName(String browseRangeName) {
    this.browseRangeName = browseRangeName;
  }
  
  public String getEmpHeadImg() {
    return this.empHeadImg;
  }
  
  public void setEmpHeadImg(String empHeadImg) {
    this.empHeadImg = empHeadImg;
  }
  
  public String getEmpSignImg() {
    return this.empSignImg;
  }
  
  public void setEmpSignImg(String empSignImg) {
    this.empSignImg = empSignImg;
  }
  
  public String getFamilyMember() {
    return this.familyMember;
  }
  
  public void setFamilyMember(String familyMember) {
    this.familyMember = familyMember;
  }
  
  public String getUserOrderCode() {
    return this.userOrderCode;
  }
  
  public void setUserOrderCode(String userOrderCode) {
    this.userOrderCode = userOrderCode;
  }
  
  public String getKeySerial() {
    return this.keySerial;
  }
  
  public void setKeySerial(String keySerial) {
    this.keySerial = keySerial;
  }
  
  public String getKeyValidate() {
    return this.keyValidate;
  }
  
  public void setKeyValidate(String keyValidate) {
    this.keyValidate = keyValidate;
  }
  
  public String getUserSimpleName() {
    return this.userSimpleName;
  }
  
  public void setUserSimpleName(String userSimpleName) {
    this.userSimpleName = userSimpleName;
  }
  
  public String getMailDefaultDomain() {
    return this.mailDefaultDomain;
  }
  
  public void setMailDefaultDomain(String mailDefaultDomain) {
    this.mailDefaultDomain = mailDefaultDomain;
  }
  
  public String getMailDomain() {
    return this.mailDomain;
  }
  
  public void setMailDomain(String mailDomain) {
    this.mailDomain = mailDomain;
  }
  
  public String getMailPost() {
    return this.mailPost;
  }
  
  public void setMailPost(String mailPost) {
    this.mailPost = mailPost;
  }
  
  public String getAccumulationFund() {
    return this.accumulationFund;
  }
  
  public void setAccumulationFund(String accumulationFund) {
    this.accumulationFund = accumulationFund;
  }
  
  public String getEndowmentInsurance() {
    return this.endowmentInsurance;
  }
  
  public void setEndowmentInsurance(String endowmentInsurance) {
    this.endowmentInsurance = endowmentInsurance;
  }
  
  public Date getFosterPackEndDate() {
    return this.fosterPackEndDate;
  }
  
  public Date getFosterPackStartDate() {
    return this.fosterPackStartDate;
  }
  
  public Date getGraduateDate() {
    return this.graduateDate;
  }
  
  public void setFosterPackEndDate(Date fosterPackEndDate) {
    this.fosterPackEndDate = fosterPackEndDate;
  }
  
  public void setFosterPackStartDate(Date fosterPackStartDate) {
    this.fosterPackStartDate = fosterPackStartDate;
  }
  
  public void setGraduateDate(Date graduateDate) {
    this.graduateDate = graduateDate;
  }
  
  public void setHirePackEndDate(Date hirePackEndDate) {
    this.hirePackEndDate = hirePackEndDate;
  }
  
  public void setHirePackStartDate(Date hirePackStartDate) {
    this.hirePackStartDate = hirePackStartDate;
  }
  
  public Date getHirePackStartDate() {
    return this.hirePackStartDate;
  }
  
  public Date getHirePackEndDate() {
    return this.hirePackEndDate;
  }
  
  public Date getIntoCompanyDate() {
    return this.intoCompanyDate;
  }
  
  public void setIntoCompanyDate(Date intoCompanyDate) {
    this.intoCompanyDate = intoCompanyDate;
  }
  
  public void setMedicare(String medicare) {
    this.medicare = medicare;
  }
  
  public String getMedicare() {
    return this.medicare;
  }
  
  public void setWorkPackEndDate(Date workPackEndDate) {
    this.workPackEndDate = workPackEndDate;
  }
  
  public void setWorkPackStartDate(Date workPackStartDate) {
    this.workPackStartDate = workPackStartDate;
  }
  
  public void setWpContEndtDate(Date wpContEndtDate) {
    this.wpContEndtDate = wpContEndtDate;
  }
  
  public void setWpContStartDate(Date wpContStartDate) {
    this.wpContStartDate = wpContStartDate;
  }
  
  public Date getWpContStartDate() {
    return this.wpContStartDate;
  }
  
  public Date getWpContEndtDate() {
    return this.wpContEndtDate;
  }
  
  public Date getWorkPackStartDate() {
    return this.workPackStartDate;
  }
  
  public Date getWorkPackEndDate() {
    return this.workPackEndDate;
  }
  
  public String getIntroducer() {
    return this.introducer;
  }
  
  public void setIntroducer(String introducer) {
    this.introducer = introducer;
  }
  
  public String getCompensation() {
    return this.compensation;
  }
  
  public void setCompensation(String compensation) {
    this.compensation = compensation;
  }
  
  public String getDignity() {
    return this.dignity;
  }
  
  public void setDignity(String dignity) {
    this.dignity = dignity;
  }
  
  public String getSection() {
    return this.section;
  }
  
  public void setSection(String section) {
    this.section = section;
  }
  
  public String getSpeciality() {
    return this.speciality;
  }
  
  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }
  
  public String getPartyDate() {
    return this.partyDate;
  }
  
  public void setPartyDate(String partyDate) {
    this.partyDate = partyDate;
  }
  
  public String getJobStatus() {
    return this.jobStatus;
  }
  
  public void setJobStatus(String jobStatus) {
    this.jobStatus = jobStatus;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public String getRtxIsLogin() {
    return this.rtxIsLogin;
  }
  
  public String getOlRemoteMac() {
    return this.olRemoteMac;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public void setRtxIsLogin(String rtxIsLogin) {
    this.rtxIsLogin = rtxIsLogin;
  }
  
  public void setOlRemoteMac(String olRemoteMac) {
    this.olRemoteMac = olRemoteMac;
  }
  
  public String getSkin() {
    return this.skin;
  }
  
  public void setSkin(String skin) {
    this.skin = skin;
  }
  
  public String getIsChangePwd() {
    return this.isChangePwd;
  }
  
  public void setIsChangePwd(String isChangePwd) {
    this.isChangePwd = isChangePwd;
  }
  
  public String getMailboxSize() {
    return this.mailboxSize;
  }
  
  public void setMailboxSize(String mailboxSize) {
    this.mailboxSize = mailboxSize;
  }
  
  public String getCurStatus() {
    return this.curStatus;
  }
  
  public void setCurStatus(String curStatus) {
    this.curStatus = curStatus;
  }
  
  public String getUserDefineStatus() {
    return this.userDefineStatus;
  }
  
  public void setUserDefineStatus(String userDefineStatus) {
    this.userDefineStatus = userDefineStatus;
  }
  
  public String getNetDiskSize() {
    return this.netDiskSize;
  }
  
  public void setNetDiskSize(String netDiskSize) {
    this.netDiskSize = netDiskSize;
  }
  
  public String getImId() {
    return this.imId;
  }
  
  public void setImId(String imId) {
    this.imId = imId;
  }
  
  public Set getCompetenceVO() {
    return this.competenceVO;
  }
  
  public void setCompetenceVO(Set competenceVO) {
    this.competenceVO = competenceVO;
  }
  
  public Set getContractVO() {
    return this.contractVO;
  }
  
  public void setContractVO(Set contractVO) {
    this.contractVO = contractVO;
  }
  
  public Set getEdustoryVO() {
    return this.edustoryVO;
  }
  
  public void setEdustoryVO(Set edustoryVO) {
    this.edustoryVO = edustoryVO;
  }
  
  public Set getTrainhistoryVO() {
    return this.trainhistoryVO;
  }
  
  public void setTrainhistoryVO(Set trainhistoryVO) {
    this.trainhistoryVO = trainhistoryVO;
  }
  
  public String getSidelineOrg() {
    return this.sidelineOrg;
  }
  
  public void setSidelineOrg(String sidelineOrg) {
    this.sidelineOrg = sidelineOrg;
  }
  
  public String getSidelineOrgName() {
    return this.sidelineOrgName;
  }
  
  public void setSidelineOrgName(String sidelineOrgName) {
    this.sidelineOrgName = sidelineOrgName;
  }
  
  public Long getWorkAddress() {
    return this.workAddress;
  }
  
  public void setWorkAddress(Long workAddress) {
    this.workAddress = workAddress;
  }
  
  public String getEmpDutyLevel() {
    return this.empDutyLevel;
  }
  
  public void setEmpDutyLevel(String empDutyLevel) {
    this.empDutyLevel = empDutyLevel;
  }
  
  public String getSignatureImgName() {
    return this.signatureImgName;
  }
  
  public void setSignatureImgName(String signatureImgName) {
    this.signatureImgName = signatureImgName;
  }
  
  public String getSignatureImgSaveName() {
    return this.signatureImgSaveName;
  }
  
  public String getSpeciality2() {
    return this.speciality2;
  }
  
  public String getLanglevel1() {
    return this.langlevel1;
  }
  
  public String getLanglevel2() {
    return this.langlevel2;
  }
  
  public String getLanguage3() {
    return this.language3;
  }
  
  public String getLanguage2() {
    return this.language2;
  }
  
  public String getLanglevel3() {
    return this.langlevel3;
  }
  
  public String getSerialPwd() {
    return this.serialPwd;
  }
  
  public Integer getIsdimissionprove() {
    return this.isdimissionprove;
  }
  
  public String getLanguage1() {
    return this.language1;
  }
  
  public Long getPersonalKind() {
    return this.personalKind;
  }
  
  public String getHujiAddress() {
    return this.hujiAddress;
  }
  
  public String getEmpFireType() {
    return this.empFireType;
  }
  
  public String getSpeciality1() {
    return this.speciality1;
  }
  
  public String getSerial() {
    return this.serial;
  }
  
  public Date getZhuanzhengDate() {
    return this.zhuanzhengDate;
  }
  
  public Date getLizhiDate() {
    return this.lizhiDate;
  }
  
  public String getZhicheng() {
    return this.zhicheng;
  }
  
  public void setSignatureImgSaveName(String signatureImgSaveName) {
    this.signatureImgSaveName = signatureImgSaveName;
  }
  
  public void setSpeciality2(String speciality2) {
    this.speciality2 = speciality2;
  }
  
  public void setLanglevel1(String langlevel1) {
    this.langlevel1 = langlevel1;
  }
  
  public void setLanglevel2(String langlevel2) {
    this.langlevel2 = langlevel2;
  }
  
  public void setLanguage3(String language3) {
    this.language3 = language3;
  }
  
  public void setLanguage2(String language2) {
    this.language2 = language2;
  }
  
  public void setLanglevel3(String langlevel3) {
    this.langlevel3 = langlevel3;
  }
  
  public void setSerialPwd(String serialPwd) {
    this.serialPwd = serialPwd;
  }
  
  public void setIsdimissionprove(Integer isdimissionprove) {
    this.isdimissionprove = isdimissionprove;
  }
  
  public void setLanguage1(String language1) {
    this.language1 = language1;
  }
  
  public void setPersonalKind(Long personalKind) {
    this.personalKind = personalKind;
  }
  
  public void setHujiAddress(String hujiAddress) {
    this.hujiAddress = hujiAddress;
  }
  
  public void setEmpFireType(String empFireType) {
    this.empFireType = empFireType;
  }
  
  public void setSpeciality1(String speciality1) {
    this.speciality1 = speciality1;
  }
  
  public void setSerial(String serial) {
    this.serial = serial;
  }
  
  public void setZhuanzhengDate(Date zhuanzhengDate) {
    this.zhuanzhengDate = zhuanzhengDate;
  }
  
  public void setLizhiDate(Date lizhiDate) {
    this.lizhiDate = lizhiDate;
  }
  
  public void setZhicheng(String zhicheng) {
    this.zhicheng = zhicheng;
  }
  
  public int getUserOnline() {
    return this.userOnline;
  }
  
  public void setUserOnline(int userOnline) {
    this.userOnline = userOnline;
  }
  
  public String getDeptLeader() {
    return this.deptLeader;
  }
  
  public void setDeptLeader(String deptLeader) {
    this.deptLeader = deptLeader;
  }
  
  public Long getEmpPositionId() {
    return this.empPositionId;
  }
  
  public void setEmpPositionId(Long empPositionId) {
    this.empPositionId = empPositionId;
  }
  
  public String getEmpPositionOtherId() {
    return this.empPositionOtherId;
  }
  
  public void setEmpPositionOtherId(String empPositionOtherId) {
    this.empPositionOtherId = empPositionOtherId;
  }
  
  public String getWeixinPost() {
    return this.weixinPost;
  }
  
  public void setWeixinPost(String weixinPost) {
    this.weixinPost = weixinPost;
  }
  
  public String getGuid() {
    return this.guid;
  }
  
  public void setGuid(String guid) {
    this.guid = guid;
  }
  
  public String getInitPassword() {
    return this.initPassword;
  }
  
  public void setInitPassword(String initPassword) {
    this.initPassword = initPassword;
  }
  
  public String getInitAccount() {
    return this.initAccount;
  }
  
  public void setInitAccount(String initAccount) {
    this.initAccount = initAccount;
  }
  
  public String getWeixinId() {
    return this.weixinId;
  }
  
  public void setWeixinId(String weixinId) {
    this.weixinId = weixinId;
  }
}
