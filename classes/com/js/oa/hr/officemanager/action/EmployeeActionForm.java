package com.js.oa.hr.officemanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmployeeActionForm extends ActionForm {
  private String action;
  
  private String currentPostTitle;
  
  private String empAddress;
  
  private String empBloodType;
  
  private String empBusinessFax;
  
  private String empBusinessPhone;
  
  private String empCountry;
  
  private String empDegree;
  
  private String empDescribe;
  
  private String empDuty;
  
  private String empEducationExperience;
  
  private String empEmail;
  
  private String empEmail2;
  
  private String empEmail3;
  
  private String empEnglishName;
  
  private String empGnome;
  
  private String empInterest;
  
  private String empMobilePhone;
  
  private String empName;
  
  private String empNation;
  
  private String empNativePlace;
  
  private String empNumber;
  
  private String empPhone;
  
  private String empPolity;
  
  private String empStudyExperience;
  
  private String empTrainExperience;
  
  private String empWebAddress;
  
  private String empWorkExperience;
  
  private String familyMember;
  
  private String orgId;
  
  private String otherPostTitle;
  
  private String postCompetence;
  
  private String postLevel;
  
  private String postTitleSeries;
  
  private String userAccounts;
  
  private String userPassword;
  
  private String userPasswordConfirm;
  
  private String searchEmpName;
  
  private String searchEmpEnglishName;
  
  private String certifyNumber;
  
  private byte empSex;
  
  private byte empIsMarriage;
  
  private short empHeight;
  
  private String empIdCard;
  
  private short empWeight;
  
  private byte userIsSuper;
  
  private String orgName;
  
  private String empCounty;
  
  private Long empId;
  
  private String empLivingPhoto;
  
  private String empStateSelect;
  
  private String empStateText;
  
  private String empCountySelect;
  
  private String empCountyText;
  
  private String empState;
  
  private String searchOrgName;
  
  private byte userIsActive;
  
  private String hisSearchEmpEnglishName;
  
  private String hisSearchEmpName;
  
  private String hisSearchOrgName;
  
  private String insuranceNumber;
  
  private String empZipCode;
  
  private String userOrderCode;
  
  private String empPosition;
  
  private String contractAbout;
  
  private String fireReason;
  
  private String empResumeNum;
  
  private String accumulationFund;
  
  private String medicare;
  
  private String endowmentInsurance;
  
  private String searchEmpBusinessFax;
  
  private String searchEmpPosition;
  
  private String searchStartAge;
  
  private String searchEndAge;
  
  private String compensation;
  
  private String introducer;
  
  private String searchEmpDuty;
  
  private String dignity;
  
  private String section;
  
  private String speciality;
  
  private String partyDate;
  
  private String jobStatus;
  
  private String searchEmpCountry;
  
  private String empLeaderName;
  
  private String empLeaderId;
  
  private String empbusPhone;
  
  private String empbusFAX;
  
  private String sidelineOrg;
  
  private String sidelineOrgName;
  
  private Long workAddress;
  
  private String userSimpleName;
  
  private String serial;
  
  private String serialPwd;
  
  private String personalKind;
  
  private String empFireType;
  
  private String hujiAddress;
  
  private String isdimissionprove;
  
  private String speciality1;
  
  private String speciality2;
  
  private String language1;
  
  private String language2;
  
  private String language3;
  
  private String langlevel1;
  
  private String langlevel2;
  
  private String langlevel3;
  
  private String zhicheng;
  
  private String sap_ID;
  
  private String cym;
  
  private String csd;
  
  private String hkszd;
  
  private String hyzk;
  
  private String jkzk;
  
  private String zyjszc;
  
  private String zyzg;
  
  private String wjgj;
  
  private String yjjzgj;
  
  private String qrz_zgxl;
  
  private String qrz_zgxw;
  
  private String qrz_byyxx;
  
  private String qrz_zy;
  
  private String zzjy_zgxl;
  
  private String zzjy_zgxw;
  
  private String zzjy_byyxx;
  
  private String zzjy_zy;
  
  private String cs;
  
  private String bmlx;
  
  private String sfjzgb;
  
  private String rxzsj;
  
  private String rxjsj;
  
  private String wyhrz;
  
  private String syqksrq;
  
  private String syqjsrq;
  
  private String syqqx;
  
  private String po_xm;
  
  private String po_csny;
  
  private String po_mz;
  
  private String po_jg;
  
  private String po_zzmm;
  
  private String po_cjgzsj;
  
  private String po_xjzcs;
  
  private String po_hkszd;
  
  private String po_xl;
  
  private String po_zyjszc;
  
  private String po_byyxx;
  
  private String po_zy;
  
  private String po_gzdwjbm;
  
  private String po_zw;
  
  private String dt_rdsj;
  
  private String dt_dnzw;
  
  private String dt_ssdzb;
  
  private String dt_zzgxszdw;
  
  private String rsrz;
  
  private String ftjrz;
  
  private String ivCode;
  
  private String bankCode;
  
  private String accountCode;
  
  private String bankInfo;
  
  private String province;
  
  private String city;
  
  private String hkxz;
  
  private String sfcjshbx;
  
  private String sfczjggz;
  
  private String stjfnx;
  
  private String lxrxx;
  
  private String sfhytsb;
  
  private String hkszdyzbm;
  
  private String jzdyzbm;
  
  private String yjyzbm;
  
  private String ddyljg1;
  
  private String ddyljg2;
  
  private String ddyljg3;
  
  private String ddyljg4;
  
  private String ddyljg5;
  
  private String ydwsbjz;
  
  private String ydwsbjs;
  
  private String ydwzfgjjsszx;
  
  private String rshblgjjsj;
  
  private String sfydsznz;
  
  private String ydwgjjjz;
  
  private String ydwgjjjs;
  
  private String rszfw;
  
  private String zw;
  
  private Long empPositionId;
  
  public String getCertifyNumber() {
    return this.certifyNumber;
  }
  
  public void setCertifyNumber(String certifyNumber) {
    this.certifyNumber = certifyNumber;
  }
  
  public String getAction() {
    return this.action;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public String getCurrentPostTitle() {
    return this.currentPostTitle;
  }
  
  public void setCurrentPostTitle(String currentPostTitle) {
    this.currentPostTitle = currentPostTitle;
  }
  
  public String getEmpAddress() {
    return this.empAddress;
  }
  
  public void setEmpAddress(String empAddress) {
    this.empAddress = empAddress;
  }
  
  public String getEmpBloodType() {
    return this.empBloodType;
  }
  
  public void setEmpBloodType(String empBloodType) {
    this.empBloodType = empBloodType;
  }
  
  public String getEmpBusinessFax() {
    return this.empBusinessFax;
  }
  
  public String getStjfnx() {
    return this.stjfnx;
  }
  
  public void setStjfnx(String stjfnx) {
    this.stjfnx = stjfnx;
  }
  
  public void setEmpBusinessFax(String empBusinessFax) {
    this.empBusinessFax = empBusinessFax;
  }
  
  public String getEmpBusinessPhone() {
    return this.empBusinessPhone;
  }
  
  public void setEmpBusinessPhone(String empBusinessPhone) {
    this.empBusinessPhone = empBusinessPhone;
  }
  
  public String getEmpCountry() {
    return this.empCountry;
  }
  
  public void setEmpCountry(String empCountry) {
    this.empCountry = empCountry;
  }
  
  public String getEmpDegree() {
    return this.empDegree;
  }
  
  public void setEmpDegree(String empDegree) {
    this.empDegree = empDegree;
  }
  
  public String getEmpDescribe() {
    return this.empDescribe;
  }
  
  public void setEmpDescribe(String empDescribe) {
    this.empDescribe = empDescribe;
  }
  
  public String getEmpDuty() {
    return this.empDuty;
  }
  
  public void setEmpDuty(String empDuty) {
    this.empDuty = empDuty;
  }
  
  public String getEmpEducationExperience() {
    return this.empEducationExperience;
  }
  
  public void setEmpEducationExperience(String empEducationExperience) {
    this.empEducationExperience = empEducationExperience;
  }
  
  public String getEmpEmail() {
    return this.empEmail;
  }
  
  public void setEmpEmail(String empEmail) {
    this.empEmail = empEmail;
  }
  
  public String getEmpEmail2() {
    return this.empEmail2;
  }
  
  public void setEmpEmail2(String empEmail2) {
    this.empEmail2 = empEmail2;
  }
  
  public String getEmpEmail3() {
    return this.empEmail3;
  }
  
  public void setEmpEmail3(String empEmail3) {
    this.empEmail3 = empEmail3;
  }
  
  public String getEmpEnglishName() {
    return this.empEnglishName;
  }
  
  public void setEmpEnglishName(String empEnglishName) {
    this.empEnglishName = empEnglishName;
  }
  
  public String getEmpGnome() {
    return this.empGnome;
  }
  
  public void setEmpGnome(String empGnome) {
    this.empGnome = empGnome;
  }
  
  public short getEmpHeight() {
    return this.empHeight;
  }
  
  public void setEmpHeight(short empHeight) {
    this.empHeight = empHeight;
  }
  
  public String getEmpIdCard() {
    return this.empIdCard;
  }
  
  public void setEmpIdCard(String empIdCard) {
    this.empIdCard = empIdCard;
  }
  
  public String getEmpInterest() {
    return this.empInterest;
  }
  
  public void setEmpInterest(String empInterest) {
    this.empInterest = empInterest;
  }
  
  public byte getEmpIsMarriage() {
    return this.empIsMarriage;
  }
  
  public void setEmpIsMarriage(byte empIsMarriage) {
    this.empIsMarriage = empIsMarriage;
  }
  
  public String getEmpMobilePhone() {
    return this.empMobilePhone;
  }
  
  public void setEmpMobilePhone(String empMobilePhone) {
    this.empMobilePhone = empMobilePhone;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpNation() {
    return this.empNation;
  }
  
  public void setEmpNation(String empNation) {
    this.empNation = empNation;
  }
  
  public String getEmpNativePlace() {
    return this.empNativePlace;
  }
  
  public void setEmpNativePlace(String empNativePlace) {
    this.empNativePlace = empNativePlace;
  }
  
  public String getEmpNumber() {
    return this.empNumber;
  }
  
  public void setEmpNumber(String empNumber) {
    this.empNumber = empNumber;
  }
  
  public String getEmpPhone() {
    return this.empPhone;
  }
  
  public void setEmpPhone(String empPhone) {
    this.empPhone = empPhone;
  }
  
  public String getEmpPolity() {
    return this.empPolity;
  }
  
  public void setEmpPolity(String empPolity) {
    this.empPolity = empPolity;
  }
  
  public byte getEmpSex() {
    return this.empSex;
  }
  
  public void setEmpSex(byte empSex) {
    this.empSex = empSex;
  }
  
  public String getEmpState() {
    return this.empState;
  }
  
  public void setEmpState(String empState) {
    this.empState = empState;
  }
  
  public String getEmpStudyExperience() {
    return this.empStudyExperience;
  }
  
  public void setEmpStudyExperience(String empStudyExperience) {
    this.empStudyExperience = empStudyExperience;
  }
  
  public String getEmpTrainExperience() {
    return this.empTrainExperience;
  }
  
  public void setEmpTrainExperience(String empTrainExperience) {
    this.empTrainExperience = empTrainExperience;
  }
  
  public String getEmpWebAddress() {
    return this.empWebAddress;
  }
  
  public void setEmpWebAddress(String empWebAddress) {
    this.empWebAddress = empWebAddress;
  }
  
  public String getEmpWorkExperience() {
    return this.empWorkExperience;
  }
  
  public void setEmpWorkExperience(String empWorkExperience) {
    this.empWorkExperience = empWorkExperience;
  }
  
  public String getFamilyMember() {
    return this.familyMember;
  }
  
  public void setFamilyMember(String familyMember) {
    this.familyMember = familyMember;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
  public String getOtherPostTitle() {
    return this.otherPostTitle;
  }
  
  public void setOtherPostTitle(String otherPostTitle) {
    this.otherPostTitle = otherPostTitle;
  }
  
  public String getPostCompetence() {
    return this.postCompetence;
  }
  
  public void setPostCompetence(String postCompetence) {
    this.postCompetence = postCompetence;
  }
  
  public String getPostLevel() {
    return this.postLevel;
  }
  
  public void setPostLevel(String postLevel) {
    this.postLevel = postLevel;
  }
  
  public String getPostTitleSeries() {
    return this.postTitleSeries;
  }
  
  public void setPostTitleSeries(String postTitleSeries) {
    this.postTitleSeries = postTitleSeries;
  }
  
  public String getUserAccounts() {
    return this.userAccounts;
  }
  
  public void setUserAccounts(String userAccounts) {
    this.userAccounts = userAccounts;
  }
  
  public byte getUserIsSuper() {
    return this.userIsSuper;
  }
  
  public void setUserIsSuper(byte userIsSuper) {
    this.userIsSuper = userIsSuper;
  }
  
  public String getUserPassword() {
    return this.userPassword;
  }
  
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }
  
  public String getUserPasswordConfirm() {
    return this.userPasswordConfirm;
  }
  
  public void setUserPasswordConfirm(String userPasswordConfirm) {
    this.userPasswordConfirm = userPasswordConfirm;
  }
  
  public byte getUserIsActive() {
    return this.userIsActive;
  }
  
  public void setUserIsActive(byte userIsActive) {
    this.userIsActive = userIsActive;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.empSex = Byte.parseByte("0");
    this.empIsMarriage = Byte.parseByte("0");
    this.userIsSuper = Byte.parseByte("0");
    this.userIsActive = Byte.parseByte("1");
  }
  
  public String getSearchEmpName() {
    return this.searchEmpName;
  }
  
  public void setSearchEmpName(String searchEmpName) {
    this.searchEmpName = searchEmpName;
  }
  
  public String getSearchEmpEnglishName() {
    return this.searchEmpEnglishName;
  }
  
  public void setSearchEmpEnglishName(String searchEmpEnglishName) {
    this.searchEmpEnglishName = searchEmpEnglishName;
  }
  
  public short getEmpWeight() {
    return this.empWeight;
  }
  
  public void setEmpWeight(short empWeight) {
    this.empWeight = empWeight;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getEmpCounty() {
    return this.empCounty;
  }
  
  public void setEmpCounty(String empCounty) {
    this.empCounty = empCounty;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpLivingPhoto() {
    return this.empLivingPhoto;
  }
  
  public void setEmpLivingPhoto(String empLivingPhoto) {
    this.empLivingPhoto = empLivingPhoto;
  }
  
  public String getEmpStateSelect() {
    return this.empStateSelect;
  }
  
  public void setEmpStateSelect(String empStateSelect) {
    this.empStateSelect = empStateSelect;
  }
  
  public String getEmpStateText() {
    return this.empStateText;
  }
  
  public void setEmpStateText(String empStateText) {
    this.empStateText = empStateText;
  }
  
  public String getEmpCountySelect() {
    return this.empCountySelect;
  }
  
  public void setEmpCountySelect(String empCountySelect) {
    this.empCountySelect = empCountySelect;
  }
  
  public String getEmpCountyText() {
    return this.empCountyText;
  }
  
  public void setEmpCountyText(String empCountyText) {
    this.empCountyText = empCountyText;
  }
  
  public String getSearchOrgName() {
    return this.searchOrgName;
  }
  
  public void setSearchOrgName(String searchOrgName) {
    this.searchOrgName = searchOrgName;
  }
  
  public String getHisSearchEmpEnglishName() {
    return this.hisSearchEmpEnglishName;
  }
  
  public void setHisSearchEmpEnglishName(String hisSearchEmpEnglishName) {
    this.hisSearchEmpEnglishName = hisSearchEmpEnglishName;
  }
  
  public String getHisSearchOrgName() {
    return this.hisSearchOrgName;
  }
  
  public void setHisSearchOrgName(String hisSearchOrgName) {
    this.hisSearchOrgName = hisSearchOrgName;
  }
  
  public String getHisSearchEmpName() {
    return this.hisSearchEmpName;
  }
  
  public void setHisSearchEmpName(String hisSearchEmpName) {
    this.hisSearchEmpName = hisSearchEmpName;
  }
  
  public String getInsuranceNumber() {
    return this.insuranceNumber;
  }
  
  public void setInsuranceNumber(String insuranceNumber) {
    this.insuranceNumber = insuranceNumber;
  }
  
  public String getEmpZipCode() {
    return this.empZipCode;
  }
  
  public void setEmpZipCode(String empZipCode) {
    this.empZipCode = empZipCode;
  }
  
  public void reset() {
    this.action = "";
    this.currentPostTitle = "";
    this.empAddress = "";
    this.empBloodType = "";
    this.empBusinessFax = "";
    this.empBusinessPhone = "";
    this.empCountry = "";
    this.empDegree = "";
    this.empDescribe = "";
    this.empDuty = "";
    this.empEducationExperience = "";
    this.empEmail = "";
    this.empEmail2 = "";
    this.empEmail3 = "";
    this.empEnglishName = "";
    this.empGnome = "";
    this.empInterest = "";
    this.empMobilePhone = "";
    this.empName = "";
    this.empNation = "";
    this.empNativePlace = "";
    this.empNumber = "";
    this.empPhone = "";
    this.empPolity = "";
    this.empStudyExperience = "";
    this.empTrainExperience = "";
    this.empWebAddress = "";
    this.empWorkExperience = "";
    this.familyMember = "";
    this.orgId = "";
    this.otherPostTitle = "";
    this.postCompetence = "";
    this.postLevel = "";
    this.postTitleSeries = "";
    this.userAccounts = "";
    this.userPassword = "";
    this.userPasswordConfirm = "";
    this.searchEmpName = "";
    this.searchEmpEnglishName = "";
    this.certifyNumber = "";
    this.empSex = Byte.parseByte("0");
    this.empIsMarriage = Byte.parseByte("0");
    this.empHeight = 0;
    this.empIdCard = "0";
    this.empWeight = 0;
    this.userIsSuper = Byte.parseByte("0");
    this.orgName = "";
    this.empCounty = "";
    this.empLivingPhoto = "";
    this.empStateSelect = "";
    this.empStateText = "";
    this.empCountySelect = "";
    this.empCountyText = "";
    this.empState = "";
    this.searchOrgName = "";
    this.userIsActive = 0;
    this.hisSearchEmpEnglishName = "";
    this.hisSearchEmpName = "";
    this.hisSearchOrgName = "";
    this.insuranceNumber = "";
    this.empZipCode = "";
  }
  
  public String getUserOrderCode() {
    return this.userOrderCode;
  }
  
  public void setUserOrderCode(String userOrderCode) {
    this.userOrderCode = userOrderCode;
  }
  
  public String getEmpPosition() {
    return this.empPosition;
  }
  
  public void setEmpPosition(String empPosition) {
    this.empPosition = empPosition;
  }
  
  public String getContractAbout() {
    return this.contractAbout;
  }
  
  public void setContractAbout(String contractAbout) {
    this.contractAbout = contractAbout;
  }
  
  public String getFireReason() {
    return this.fireReason;
  }
  
  public void setFireReason(String fireReason) {
    this.fireReason = fireReason;
  }
  
  public String getEmpResumeNum() {
    return this.empResumeNum;
  }
  
  public void setEmpResumeNum(String empResumeNum) {
    this.empResumeNum = empResumeNum;
  }
  
  public String getAccumulationFund() {
    return this.accumulationFund;
  }
  
  public void setAccumulationFund(String accumulationFund) {
    this.accumulationFund = accumulationFund;
  }
  
  public String getMedicare() {
    return this.medicare;
  }
  
  public void setMedicare(String medicare) {
    this.medicare = medicare;
  }
  
  public String getEndowmentInsurance() {
    return this.endowmentInsurance;
  }
  
  public void setEndowmentInsurance(String endowmentInsurance) {
    this.endowmentInsurance = endowmentInsurance;
  }
  
  public String getSearchEmpBusinessFax() {
    return this.searchEmpBusinessFax;
  }
  
  public void setSearchEmpBusinessFax(String searchEmpBusinessFax) {
    this.searchEmpBusinessFax = searchEmpBusinessFax;
  }
  
  public String getSearchEmpPosition() {
    return this.searchEmpPosition;
  }
  
  public void setSearchEmpPosition(String searchEmpPosition) {
    this.searchEmpPosition = searchEmpPosition;
  }
  
  public String getSearchStartAge() {
    return this.searchStartAge;
  }
  
  public void setSearchStartAge(String searchStartAge) {
    this.searchStartAge = searchStartAge;
  }
  
  public String getSearchEndAge() {
    return this.searchEndAge;
  }
  
  public void setSearchEndAge(String searchEndAge) {
    this.searchEndAge = searchEndAge;
  }
  
  public String getCompensation() {
    return this.compensation;
  }
  
  public void setCompensation(String compensation) {
    this.compensation = compensation;
  }
  
  public String getIntroducer() {
    return this.introducer;
  }
  
  public void setIntroducer(String introducer) {
    this.introducer = introducer;
  }
  
  public String getSearchEmpDuty() {
    return this.searchEmpDuty;
  }
  
  public void setSearchEmpDuty(String searchEmpDuty) {
    this.searchEmpDuty = searchEmpDuty;
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
  
  public String getSearchEmpCountry() {
    return this.searchEmpCountry;
  }
  
  public void setSearchEmpCountry(String searchEmpCountry) {
    this.searchEmpCountry = searchEmpCountry;
  }
  
  public String getEmpbusFAX() {
    return this.empbusFAX;
  }
  
  public void setEmpbusFAX(String empbusFAX) {
    this.empbusFAX = empbusFAX;
  }
  
  public String getEmpbusPhone() {
    return this.empbusPhone;
  }
  
  public void setEmpbusPhone(String empbusPhone) {
    this.empbusPhone = empbusPhone;
  }
  
  public String getEmpLeaderId() {
    return this.empLeaderId;
  }
  
  public void setEmpLeaderId(String empLeaderId) {
    this.empLeaderId = empLeaderId;
  }
  
  public String getEmpLeaderName() {
    return this.empLeaderName;
  }
  
  public void setEmpLeaderName(String empLeaderName) {
    this.empLeaderName = empLeaderName;
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
  
  public String getUserSimpleName() {
    return this.userSimpleName;
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
  
  public String getIsdimissionprove() {
    return this.isdimissionprove;
  }
  
  public String getLanguage1() {
    return this.language1;
  }
  
  public String getPersonalKind() {
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
  
  public String getZhicheng() {
    return this.zhicheng;
  }
  
  public void setUserSimpleName(String userSimpleName) {
    this.userSimpleName = userSimpleName;
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
  
  public void setIsdimissionprove(String isdimissionprove) {
    this.isdimissionprove = isdimissionprove;
  }
  
  public void setLanguage1(String language1) {
    this.language1 = language1;
  }
  
  public void setPersonalKind(String personalKind) {
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
  
  public void setZhicheng(String zhicheng) {
    this.zhicheng = zhicheng;
  }
  
  public String getSap_ID() {
    return this.sap_ID;
  }
  
  public void setSap_ID(String sap_ID) {
    this.sap_ID = sap_ID;
  }
  
  public String getCym() {
    return this.cym;
  }
  
  public void setCym(String cym) {
    this.cym = cym;
  }
  
  public String getCsd() {
    return this.csd;
  }
  
  public void setCsd(String csd) {
    this.csd = csd;
  }
  
  public String getHkszd() {
    return this.hkszd;
  }
  
  public void setHkszd(String hkszd) {
    this.hkszd = hkszd;
  }
  
  public String getHyzk() {
    return this.hyzk;
  }
  
  public void setHyzk(String hyzk) {
    this.hyzk = hyzk;
  }
  
  public String getJkzk() {
    return this.jkzk;
  }
  
  public void setJkzk(String jkzk) {
    this.jkzk = jkzk;
  }
  
  public String getZyjszc() {
    return this.zyjszc;
  }
  
  public void setZyjszc(String zyjszc) {
    this.zyjszc = zyjszc;
  }
  
  public String getZyzg() {
    return this.zyzg;
  }
  
  public void setZyzg(String zyzg) {
    this.zyzg = zyzg;
  }
  
  public String getWjgj() {
    return this.wjgj;
  }
  
  public void setWjgj(String wjgj) {
    this.wjgj = wjgj;
  }
  
  public String getYjjzgj() {
    return this.yjjzgj;
  }
  
  public void setYjjzgj(String yjjzgj) {
    this.yjjzgj = yjjzgj;
  }
  
  public String getQrz_zgxl() {
    return this.qrz_zgxl;
  }
  
  public void setQrz_zgxl(String qrz_zgxl) {
    this.qrz_zgxl = qrz_zgxl;
  }
  
  public String getQrz_zgxw() {
    return this.qrz_zgxw;
  }
  
  public void setQrz_zgxw(String qrz_zgxw) {
    this.qrz_zgxw = qrz_zgxw;
  }
  
  public String getQrz_byyxx() {
    return this.qrz_byyxx;
  }
  
  public void setQrz_byyxx(String qrz_byyxx) {
    this.qrz_byyxx = qrz_byyxx;
  }
  
  public String getQrz_zy() {
    return this.qrz_zy;
  }
  
  public void setQrz_zy(String qrz_zy) {
    this.qrz_zy = qrz_zy;
  }
  
  public String getZzjy_zgxl() {
    return this.zzjy_zgxl;
  }
  
  public void setZzjy_zgxl(String zzjy_zgxl) {
    this.zzjy_zgxl = zzjy_zgxl;
  }
  
  public String getZzjy_zgxw() {
    return this.zzjy_zgxw;
  }
  
  public void setZzjy_zgxw(String zzjy_zgxw) {
    this.zzjy_zgxw = zzjy_zgxw;
  }
  
  public String getZzjy_byyxx() {
    return this.zzjy_byyxx;
  }
  
  public void setZzjy_byyxx(String zzjy_byyxx) {
    this.zzjy_byyxx = zzjy_byyxx;
  }
  
  public String getZzjy_zy() {
    return this.zzjy_zy;
  }
  
  public void setZzjy_zy(String zzjy_zy) {
    this.zzjy_zy = zzjy_zy;
  }
  
  public String getCs() {
    return this.cs;
  }
  
  public void setCs(String cs) {
    this.cs = cs;
  }
  
  public String getBmlx() {
    return this.bmlx;
  }
  
  public void setBmlx(String bmlx) {
    this.bmlx = bmlx;
  }
  
  public String getSfjzgb() {
    return this.sfjzgb;
  }
  
  public void setSfjzgb(String sfjzgb) {
    this.sfjzgb = sfjzgb;
  }
  
  public String getRxzsj() {
    return this.rxzsj;
  }
  
  public void setRxzsj(String rxzsj) {
    this.rxzsj = rxzsj;
  }
  
  public String getRxjsj() {
    return this.rxjsj;
  }
  
  public void setRxjsj(String rxjsj) {
    this.rxjsj = rxjsj;
  }
  
  public String getWyhrz() {
    return this.wyhrz;
  }
  
  public void setWyhrz(String wyhrz) {
    this.wyhrz = wyhrz;
  }
  
  public String getSyqksrq() {
    return this.syqksrq;
  }
  
  public void setSyqksrq(String syqksrq) {
    this.syqksrq = syqksrq;
  }
  
  public String getSyqjsrq() {
    return this.syqjsrq;
  }
  
  public void setSyqjsrq(String syqjsrq) {
    this.syqjsrq = syqjsrq;
  }
  
  public String getSyqqx() {
    return this.syqqx;
  }
  
  public void setSyqqx(String syqqx) {
    this.syqqx = syqqx;
  }
  
  public String getPo_xm() {
    return this.po_xm;
  }
  
  public void setPo_xm(String po_xm) {
    this.po_xm = po_xm;
  }
  
  public String getPo_csny() {
    return this.po_csny;
  }
  
  public void setPo_csny(String po_csny) {
    this.po_csny = po_csny;
  }
  
  public String getPo_mz() {
    return this.po_mz;
  }
  
  public void setPo_mz(String po_mz) {
    this.po_mz = po_mz;
  }
  
  public String getPo_jg() {
    return this.po_jg;
  }
  
  public void setPo_jg(String po_jg) {
    this.po_jg = po_jg;
  }
  
  public String getPo_zzmm() {
    return this.po_zzmm;
  }
  
  public void setPo_zzmm(String po_zzmm) {
    this.po_zzmm = po_zzmm;
  }
  
  public String getPo_cjgzsj() {
    return this.po_cjgzsj;
  }
  
  public void setPo_cjgzsj(String po_cjgzsj) {
    this.po_cjgzsj = po_cjgzsj;
  }
  
  public String getPo_xjzcs() {
    return this.po_xjzcs;
  }
  
  public void setPo_xjzcs(String po_xjzcs) {
    this.po_xjzcs = po_xjzcs;
  }
  
  public String getPo_hkszd() {
    return this.po_hkszd;
  }
  
  public void setPo_hkszd(String po_hkszd) {
    this.po_hkszd = po_hkszd;
  }
  
  public String getPo_xl() {
    return this.po_xl;
  }
  
  public void setPo_xl(String po_xl) {
    this.po_xl = po_xl;
  }
  
  public String getPo_zyjszc() {
    return this.po_zyjszc;
  }
  
  public void setPo_zyjszc(String po_zyjszc) {
    this.po_zyjszc = po_zyjszc;
  }
  
  public String getPo_byyxx() {
    return this.po_byyxx;
  }
  
  public void setPo_byyxx(String po_byyxx) {
    this.po_byyxx = po_byyxx;
  }
  
  public String getPo_zy() {
    return this.po_zy;
  }
  
  public void setPo_zy(String po_zy) {
    this.po_zy = po_zy;
  }
  
  public String getPo_gzdwjbm() {
    return this.po_gzdwjbm;
  }
  
  public void setPo_gzdwjbm(String po_gzdwjbm) {
    this.po_gzdwjbm = po_gzdwjbm;
  }
  
  public String getPo_zw() {
    return this.po_zw;
  }
  
  public void setPo_zw(String po_zw) {
    this.po_zw = po_zw;
  }
  
  public String getDt_rdsj() {
    return this.dt_rdsj;
  }
  
  public void setDt_rdsj(String dt_rdsj) {
    this.dt_rdsj = dt_rdsj;
  }
  
  public String getDt_dnzw() {
    return this.dt_dnzw;
  }
  
  public void setDt_dnzw(String dt_dnzw) {
    this.dt_dnzw = dt_dnzw;
  }
  
  public String getDt_ssdzb() {
    return this.dt_ssdzb;
  }
  
  public void setDt_ssdzb(String dt_ssdzb) {
    this.dt_ssdzb = dt_ssdzb;
  }
  
  public String getDt_zzgxszdw() {
    return this.dt_zzgxszdw;
  }
  
  public void setDt_zzgxszdw(String dt_zzgxszdw) {
    this.dt_zzgxszdw = dt_zzgxszdw;
  }
  
  public String getRsrz() {
    return this.rsrz;
  }
  
  public void setRsrz(String rsrz) {
    this.rsrz = rsrz;
  }
  
  public String getFtjrz() {
    return this.ftjrz;
  }
  
  public void setFtjrz(String ftjrz) {
    this.ftjrz = ftjrz;
  }
  
  public String getIvCode() {
    return this.ivCode;
  }
  
  public void setIvCode(String ivCode) {
    this.ivCode = ivCode;
  }
  
  public String getBankCode() {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }
  
  public String getAccountCode() {
    return this.accountCode;
  }
  
  public void setAccountCode(String accountCode) {
    this.accountCode = accountCode;
  }
  
  public String getBankInfo() {
    return this.bankInfo;
  }
  
  public void setBankInfo(String bankInfo) {
    this.bankInfo = bankInfo;
  }
  
  public String getProvince() {
    return this.province;
  }
  
  public void setProvince(String province) {
    this.province = province;
  }
  
  public String getCity() {
    return this.city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  public String getHkxz() {
    return this.hkxz;
  }
  
  public void setHkxz(String hkxz) {
    this.hkxz = hkxz;
  }
  
  public String getSfcjshbx() {
    return this.sfcjshbx;
  }
  
  public void setSfcjshbx(String sfcjshbx) {
    this.sfcjshbx = sfcjshbx;
  }
  
  public String getLxrxx() {
    return this.lxrxx;
  }
  
  public void setLxrxx(String lxrxx) {
    this.lxrxx = lxrxx;
  }
  
  public String getSfhytsb() {
    return this.sfhytsb;
  }
  
  public void setSfhytsb(String sfhytsb) {
    this.sfhytsb = sfhytsb;
  }
  
  public String getHkszdyzbm() {
    return this.hkszdyzbm;
  }
  
  public void setHkszdyzbm(String hkszdyzbm) {
    this.hkszdyzbm = hkszdyzbm;
  }
  
  public String getJzdyzbm() {
    return this.jzdyzbm;
  }
  
  public void setJzdyzbm(String jzdyzbm) {
    this.jzdyzbm = jzdyzbm;
  }
  
  public String getYjyzbm() {
    return this.yjyzbm;
  }
  
  public void setYjyzbm(String yjyzbm) {
    this.yjyzbm = yjyzbm;
  }
  
  public String getDdyljg1() {
    return this.ddyljg1;
  }
  
  public void setDdyljg1(String ddyljg1) {
    this.ddyljg1 = ddyljg1;
  }
  
  public String getDdyljg2() {
    return this.ddyljg2;
  }
  
  public void setDdyljg2(String ddyljg2) {
    this.ddyljg2 = ddyljg2;
  }
  
  public String getDdyljg3() {
    return this.ddyljg3;
  }
  
  public void setDdyljg3(String ddyljg3) {
    this.ddyljg3 = ddyljg3;
  }
  
  public String getDdyljg4() {
    return this.ddyljg4;
  }
  
  public void setDdyljg4(String ddyljg4) {
    this.ddyljg4 = ddyljg4;
  }
  
  public String getDdyljg5() {
    return this.ddyljg5;
  }
  
  public void setDdyljg5(String ddyljg5) {
    this.ddyljg5 = ddyljg5;
  }
  
  public String getYdwsbjz() {
    return this.ydwsbjz;
  }
  
  public void setYdwsbjz(String ydwsbjz) {
    this.ydwsbjz = ydwsbjz;
  }
  
  public String getYdwsbjs() {
    return this.ydwsbjs;
  }
  
  public void setYdwsbjs(String ydwsbjs) {
    this.ydwsbjs = ydwsbjs;
  }
  
  public String getYdwzfgjjsszx() {
    return this.ydwzfgjjsszx;
  }
  
  public void setYdwzfgjjsszx(String ydwzfgjjsszx) {
    this.ydwzfgjjsszx = ydwzfgjjsszx;
  }
  
  public String getRshblgjjsj() {
    return this.rshblgjjsj;
  }
  
  public void setRshblgjjsj(String rshblgjjsj) {
    this.rshblgjjsj = rshblgjjsj;
  }
  
  public String getSfydsznz() {
    return this.sfydsznz;
  }
  
  public void setSfydsznz(String sfydsznz) {
    this.sfydsznz = sfydsznz;
  }
  
  public String getSfczjggz() {
    return this.sfczjggz;
  }
  
  public void setSfczjggz(String sfczjggz) {
    this.sfczjggz = sfczjggz;
  }
  
  public String getYdwgjjjz() {
    return this.ydwgjjjz;
  }
  
  public void setYdwgjjjz(String ydwgjjjz) {
    this.ydwgjjjz = ydwgjjjz;
  }
  
  public String getYdwgjjjs() {
    return this.ydwgjjjs;
  }
  
  public void setYdwgjjjs(String ydwgjjjs) {
    this.ydwgjjjs = ydwgjjjs;
  }
  
  public String getRszfw() {
    return this.rszfw;
  }
  
  public void setRszfw(String rszfw) {
    this.rszfw = rszfw;
  }
  
  public String getZw() {
    return this.zw;
  }
  
  public void setZw(String zw) {
    this.zw = zw;
  }
  
  public Long getEmpPositionId() {
    return this.empPositionId;
  }
  
  public void setEmpPositionId(Long empPositionId) {
    this.empPositionId = empPositionId;
  }
}
