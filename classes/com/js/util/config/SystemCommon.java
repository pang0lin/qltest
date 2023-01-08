package com.js.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SystemCommon {
  private static String fileDealWithTitle;
  
  private static String financeSalaryCheckPassWord;
  
  private static String financeSalaryCorresField;
  
  private static String databaseType;
  
  public static int getIsShowProject() {
    return isShowProject;
  }
  
  public static void setIsShowProject(int isShowProject) {
    SystemCommon.isShowProject = isShowProject;
  }
  
  private static String isdm = "0";
  
  private static String useRTX;
  
  private static String extendMenu;
  
  private static String salaryMenu;
  
  private static String[] userDataSourceName;
  
  private static Map userDataSourceType = null;
  
  private static Map userDataSourceLang = null;
  
  private static String UKey;
  
  private static String logType;
  
  private static String licType;
  
  private static int useClusterServer = -10000;
  
  private static String clusterServerUrl;
  
  private static String clusterServerPath;
  
  private static String defaultBrowseRange = "0";
  
  private static String defaultGrantRange = "0";
  
  private static String useBrowseRange = "0";
  
  private static String useGrantRange = "0";
  
  private static String useWeiXinQYH = "0";
  
  private static String weixinQYHUserCount = "50";
  
  private static String useAutoSynchronous = "0";
  
  private static String useDingDing = "0";
  
  private static String dingDingAutoSynchronous = "0";
  
  private static int pageNum = 10;
  
  private static int forumNum = 8;
  
  private static String forumOrder = "0";
  
  private static String useSAP = "0";
  
  private static String dataExportWithSub = "0";
  
  private static String mobileLogonNum;
  
  private static int multiDepart = 0;
  
  private static int formProgram = 0;
  
  private static int syWorkflowToHR = 0;
  
  private static String isSsdz = "0";
  
  private static String modules = "";
  
  private static String showDeleteOnDone = "0";
  
  private static String showReSubmitOnDone = "0";
  
  private static String reSubmitOnDoneUseOldNumber = "0";
  
  private static String reSubmitOnBackUseOldNumber = "0";
  
  private static int showBackComment = 0;
  
  private static int showBackFordoc = 0;
  
  private static String showUnFormalUser = "0";
  
  private static int mutliBrowser = 1;
  
  private static int audit = 0;
  
  private static int autoAudit = 1;
  
  private static int office = 1;
  
  private static int personnel = 0;
  
  private static String flowDossierJoinType = "";
  
  private static int userDefineSignaturePassword = 1;
  
  private static int handWriteNotEmpty = 0;
  
  private static int eyouSSO = 1;
  
  private static int isShowProject = 0;
  
  private static int isCustomBase = 0;
  
  private static String chatSingle = "no";
  
  private static String to;
  
  private static String report = "0";
  
  private static String browseInner = "1";
  
  private static int innerShow = 0;
  
  private static int isShowButton = 0;
  
  private static String isSeaMoonService = "";
  
  private static String seaMoonServiceIp = "";
  
  private static String seaMoonServicePort = "0";
  
  private static String unitCertifySwitch = "";
  
  private static String ifAutoSyncUserUnitCertify = "";
  
  private static String organizationNameUnitCertify = "";
  
  private static String IpPortUnitCertify = "0";
  
  private static String useArchives = "0";
  
  private static String currentOrg = "0";
  
  private static String workFlowEditorVersion = "new";
  
  private static int jsoaUse = 0;
  
  private static int archiveToInfo = 0;
  
  private static int childUse = 0;
  
  private static int childShow = 1;
  
  private static int collectSub = 0;
  
  private static int fileShowUse = 0;
  
  private static String defaultType = ",";
  
  private static String extendType = ",";
  
  private static Map<String, String> contentType;
  
  private static String commentFontSize = "12px";
  
  private static String fujianFontSize = "12px";
  
  private static boolean defaultSelected = false;
  
  private static int infoEdit = 0;
  
  private static int allAttachEdit = 0;
  
  private static String ImagShowUse = "0";
  
  private static String stockShow = "0";
  
  private static int flowDirectSend = 1;
  
  private static int webservice = 0;
  
  private static int meetingNum = 50;
  
  private static int meetingListNum = 50;
  
  private static String sendSMS = "0";
  
  private static String LZGOVSplit = "0";
  
  private static String Meetingfujian = "0";
  
  private static int meetingTaskUse = 1;
  
  private static int meetingChatUse = 0;
  
  private static String meetingTaskShowName = "关联任务";
  
  private static String meetingChatShowName = "会议讨论";
  
  private static String meetingBeginShow = "08:00";
  
  private static String meetingEndShow = "22:00";
  
  private static String meetingType = "1";
  
  private static int ifShowWebsite = 0;
  
  private static int showWebsiteNum = 10;
  
  private static int showMoreNum = 15;
  
  private static String equipmentUse = "0";
  
  private static String equipmentName = "资产";
  
  private static int wxkjKq = 0;
  
  private static int wxdel = 0;
  
  private static String wxTime = "05:00";
  
  private static String dayBegin = "04:00";
  
  private static String kqBegin = "09:30";
  
  private static String kqEnd = "18:30";
  
  private static String print = "0";
  
  private static String IsShowRead = "0";
  
  private static String flowBackToSubmitPersonOnly = "0";
  
  private static String showDocumentProcessTitle = "1";
  
  private static String sendfileAccessoryEdit = "0";
  
  private static String docCommentFontSize = "12px";
  
  private static String docFormFontSize = "12px";
  
  private static String docShowDbrName = "0";
  
  private static String publicSignHeight = "160";
  
  private static String userSignHeight = "160";
  
  private static String showProcessTitle = "1";
  
  private static String showCompleteTime = "0";
  
  private static String repeatFileDealwith = "first";
  
  private static String huanbaobuCA = "0";
  
  private static String huanbaobuAuthURL = "0";
  
  private static String huanbaobuAppId = "0";
  
  private static String clFirst = "09:40";
  
  private static String clSecond = "13:40";
  
  private static String clkq = "0";
  
  private static String tbUser = "03:00";
  
  private static String emailRemindPrefix = "";
  
  private static String sWID = "";
  
  private static String backComment = "0";
  
  private static String executeLoopTask = "1";
  
  private static int outStock = 1;
  
  private static List<ZyPojo> zyMaps = new ArrayList<ZyPojo>();
  
  private static String canSendSMS = "0";
  
  private static String logCount = "";
  
  private static String workflowWordisLock = "0";
  
  private static String loginField = "userAccounts";
  
  private static int cooperateUserNum = 0;
  
  private static String workflowWordisWuhen = "0";
  
  private static String DcqService = "";
  
  private static String glxmlimit = "0";
  
  private static String glxmstatus = "0";
  
  private static boolean useMailServer = false;
  
  private static String voitureapplayrange = "0";
  
  private static String pdfToolPath = String.valueOf(System.getProperty("user.dir")) + "\\pdf\\pdf2htmlEX.exe";
  
  private static String lxOpenTimer = "0";
  
  private static String lxDatabaseName = "";
  
  private static String lxIntervalTime = "10";
  
  private static String personPortalUse = "0";
  
  private static String rtxSmsUse = "0";
  
  private static String rtxIP = "0";
  
  private static String rtxCordId = "0";
  
  private static String rtxUserName = "0";
  
  private static String rtxPassword = "0";
  
  private static String meetingApply = "0";
  
  private static String meetingApprove = "0";
  
  private static int orgTreeShowSimple = 0;
  
  private static int orgTreeSelectedSimple = 0;
  
  private static String reSubmitDel = "0";
  
  private static String contentFilter = "";
  
  private static String comeback = "0";
  
  private static String hntdIP = "";
  
  private static String hntdMainIP = "";
  
  private static String keyboardTip = "";
  
  private static String showPassword = "1";
  
  private static int sameNodeAndApproval = 0;
  
  private static String subTableScrollbar = "0";
  
  private static int subTableScrollbarBeginCnt = 15;
  
  private static String messageForDocShow = "0";
  
  private static String passwordEncryption = "1";
  
  private static String tongbutime = "00:00";
  
  private static String rwsurl = "http://zvingbj.ticp.net:86/zcms/api/json?";
  
  private static String ddurl = "";
  
  private static String position_LBS = "";
  
  private static String isOpenPDF = "1";
  
  private static String serverIp = "127.0.0.1";
  
  private static String serverPort = "8081";
  
  private static String exePath = "C:/Program Files/wkhtmltopdf/bin/wkhtmltopdf.exe";
  
  private static String workflowUpdateSyncToInstance = "0";
  
  private static String deadlineUseWorkingDay = "0";
  
  private static String fujianBatchDownload = "0";
  
  private static String fujianWatchType = "0";
  
  private static String mobileOrWechatFujianShow = "0";
  
  public static String getWorkflowUpdateSyncToInstance() {
    return workflowUpdateSyncToInstance;
  }
  
  public static void setWorkflowUpdateSyncToInstance(String workflowUpdateSyncToInstance) {
    SystemCommon.workflowUpdateSyncToInstance = workflowUpdateSyncToInstance;
  }
  
  public static String getDeadlineUseWorkingDay() {
    return deadlineUseWorkingDay;
  }
  
  public static String getFujianWatchType() {
    return fujianWatchType;
  }
  
  public static void setFujianWatchType(String fujianWatchType) {
    SystemCommon.fujianWatchType = fujianWatchType;
  }
  
  public static void setDeadlineUseWorkingDay(String deadlineUseWorkingDay) {
    SystemCommon.deadlineUseWorkingDay = deadlineUseWorkingDay;
  }
  
  public static String getIsOpenPDF() {
    return isOpenPDF;
  }
  
  public static void setIsOpenPDF(String isOpenPDF) {
    SystemCommon.isOpenPDF = isOpenPDF;
  }
  
  public static String getServerIp() {
    return serverIp;
  }
  
  public static void setServerIp(String serverIp) {
    SystemCommon.serverIp = serverIp;
  }
  
  public static String getServerPort() {
    return serverPort;
  }
  
  public static void setServerPort(String serverPort) {
    SystemCommon.serverPort = serverPort;
  }
  
  public static String getExePath() {
    return exePath;
  }
  
  public static void setExePath(String exePath) {
    SystemCommon.exePath = exePath;
  }
  
  private static String hqzdUpdata = "";
  
  public static String getPosition_LBS() {
    return position_LBS;
  }
  
  public static void setPosition_LBS(String position_LBS) {
    SystemCommon.position_LBS = position_LBS;
  }
  
  public static String getDdurl() {
    return ddurl;
  }
  
  public static void setDdurl(String ddurl) {
    SystemCommon.ddurl = ddurl;
  }
  
  public static String getRwsurl() {
    return rwsurl;
  }
  
  public static void setRwsurl(String rwsurl) {
    SystemCommon.rwsurl = rwsurl;
  }
  
  public static String getTongbutime() {
    return tongbutime;
  }
  
  public static void setTongbutime(String tongbutime) {
    SystemCommon.tongbutime = tongbutime;
  }
  
  public static String getShowPassword() {
    return showPassword;
  }
  
  public static void setShowPassword(String showPassword) {
    SystemCommon.showPassword = showPassword;
  }
  
  public static String getKeyboardTip() {
    return keyboardTip;
  }
  
  public static void setKeyboardTip(String keyboardTip) {
    SystemCommon.keyboardTip = keyboardTip;
  }
  
  public static String getHntdIP() {
    return hntdIP;
  }
  
  public static void setHntdIP(String hntdIP) {
    SystemCommon.hntdIP = hntdIP;
  }
  
  public static String getHntdMainIP() {
    return hntdMainIP;
  }
  
  public static void setHntdMainIP(String hntdMainIP) {
    SystemCommon.hntdMainIP = hntdMainIP;
  }
  
  private static String iWebOfficeVersion = "2003";
  
  public static String getiWebOfficeVersion() {
    return iWebOfficeVersion;
  }
  
  public static void setiWebOfficeVersion(String iWebOfficeVersion) {
    SystemCommon.iWebOfficeVersion = iWebOfficeVersion;
  }
  
  public static String getComeback() {
    return comeback;
  }
  
  public static void setComeback(String comeback) {
    SystemCommon.comeback = comeback;
  }
  
  public static String getContentFilter() {
    return contentFilter;
  }
  
  public static void setContentFilter(String contentFilter) {
    SystemCommon.contentFilter = contentFilter;
  }
  
  public static String getReSubmitDel() {
    return reSubmitDel;
  }
  
  public static void setReSubmitDel(String reSubmitDel) {
    SystemCommon.reSubmitDel = reSubmitDel;
  }
  
  public static int getOrgTreeShowSimple() {
    return orgTreeShowSimple;
  }
  
  public static int getOrgTreeSelectedSimple() {
    return orgTreeSelectedSimple;
  }
  
  public static void setOrgTreeSelectedSimple(int orgTreeSelectedSimple) {
    SystemCommon.orgTreeSelectedSimple = orgTreeSelectedSimple;
  }
  
  public static void setOrgTreeShowSimple(int orgTreeShowSimple) {
    SystemCommon.orgTreeShowSimple = orgTreeShowSimple;
  }
  
  public static String getRtxIP() {
    return rtxIP;
  }
  
  public static void setRtxIP(String rtxIP) {
    SystemCommon.rtxIP = rtxIP;
  }
  
  public static String getRtxSmsUse() {
    return rtxSmsUse;
  }
  
  public static void setRtxSmsUse(String rtxSmsUse) {
    SystemCommon.rtxSmsUse = rtxSmsUse;
  }
  
  public static String getRtxCordId() {
    return rtxCordId;
  }
  
  public static void setRtxCordId(String rtxCordId) {
    SystemCommon.rtxCordId = rtxCordId;
  }
  
  public static String getRtxUserName() {
    return rtxUserName;
  }
  
  public static void setRtxUserName(String rtxUserName) {
    SystemCommon.rtxUserName = rtxUserName;
  }
  
  public static String getRtxPassword() {
    return rtxPassword;
  }
  
  public static void setRtxPassword(String rtxPassword) {
    SystemCommon.rtxPassword = rtxPassword;
  }
  
  private static String addressListItem = "";
  
  public static String getAddressListItem() {
    return addressListItem;
  }
  
  public static void setAddressListItem(String addressListItem) {
    SystemCommon.addressListItem = addressListItem;
  }
  
  private static String isshowDealwithOrgName = "0";
  
  private static String isshowOrgNameLevel = "0";
  
  public static String getIsshowOrgNameLevel() {
    return isshowOrgNameLevel;
  }
  
  public static void setIsshowOrgNameLevel(String isshowOrgNameLevel) {
    SystemCommon.isshowOrgNameLevel = isshowOrgNameLevel;
  }
  
  public static String getIsshowDealwithOrgName() {
    return isshowDealwithOrgName;
  }
  
  public static void setIsshowDealwithOrgName(String isshowDealwithOrgName) {
    SystemCommon.isshowDealwithOrgName = isshowDealwithOrgName;
  }
  
  public static String getPersonPortalUse() {
    return personPortalUse;
  }
  
  public static void setPersonPortalUse(String personPortalUse) {
    SystemCommon.personPortalUse = personPortalUse;
  }
  
  public static String getVoitureapplayrange() {
    return voitureapplayrange;
  }
  
  public static void setVoitureapplayrange(String voitureapplayrange) {
    SystemCommon.voitureapplayrange = voitureapplayrange;
  }
  
  public static String getGlxmlimit() {
    return glxmlimit;
  }
  
  public static void setGlxmlimit(String glxmlimit) {
    SystemCommon.glxmlimit = glxmlimit;
  }
  
  public static String getGlxmstatus() {
    return glxmstatus;
  }
  
  public static void setGlxmstatus(String glxmstatus) {
    SystemCommon.glxmstatus = glxmstatus;
  }
  
  public static boolean isUseMailServer() {
    return useMailServer;
  }
  
  public static void setUseMailServer(boolean useMailServer1) {
    useMailServer = useMailServer1;
  }
  
  public static String getMailServerURL() {
    return mailServerURL;
  }
  
  public static void setMailServerURL(String mailServerURL1) {
    mailServerURL = mailServerURL1;
  }
  
  public static String getWorkflowWordisLock() {
    return workflowWordisLock;
  }
  
  public static void setWorkflowWordisLock(String workflowWordisLock) {
    SystemCommon.workflowWordisLock = workflowWordisLock;
  }
  
  public static String getWorkflowWordisWuhen() {
    return workflowWordisWuhen;
  }
  
  public static void setWorkflowWordisWuhen(String workflowWordisWuhen) {
    SystemCommon.workflowWordisWuhen = workflowWordisWuhen;
  }
  
  private static String mailServerURL = "";
  
  private static int appUse = 0;
  
  private static String appUrl = "";
  
  private static String apiKey = "";
  
  private static String appId = "";
  
  private static String domainName = "";
  
  private static String daxingYun = "";
  
  private static String customerName = "";
  
  private static String yunshipeiName = "";
  
  private static String docSuffix = "";
  
  private static String Officeopen = "0";
  
  private static String OfficeopenMyDoc = "0";
  
  private static String fileDownload = "1";
  
  private static String zkyNd = "2013";
  
  private static String jgdz = "0";
  
  private static String backCheckMust = "1";
  
  private static String backViewMust = "1";
  
  private static int weixinUserNum = 0;
  
  private static String firSync = "12:00";
  
  private static String secSync = "00:00";
  
  private static int inventoryWarning = 0;
  
  public static String getFirSync() {
    return firSync;
  }
  
  public static void setFirSync(String firSync) {
    SystemCommon.firSync = firSync;
  }
  
  public static String getSecSync() {
    return secSync;
  }
  
  public static void setSecSync(String secSync) {
    SystemCommon.secSync = secSync;
  }
  
  public static int getInventoryWarning() {
    return inventoryWarning;
  }
  
  public static void setInventoryWarning(int inventoryWarning) {
    SystemCommon.inventoryWarning = inventoryWarning;
  }
  
  public static int getWeixinUserNum() {
    return weixinUserNum;
  }
  
  public static void setWeixinUserNum(int weixinUserNum) {
    SystemCommon.weixinUserNum = weixinUserNum;
  }
  
  public static String getUseDingDing() {
    return useDingDing;
  }
  
  public static void setUseDingDing(String useDingDing) {
    SystemCommon.useDingDing = useDingDing;
  }
  
  public static String getDingDingAutoSynchronous() {
    return dingDingAutoSynchronous;
  }
  
  public static void setDingDingAutoSynchronous(String dingDingAutoSynchronous) {
    SystemCommon.dingDingAutoSynchronous = dingDingAutoSynchronous;
  }
  
  public static String getLZGOVSplit() {
    return LZGOVSplit;
  }
  
  public static void setLZGOVSplit(String LZGOVSplit) {
    SystemCommon.LZGOVSplit = LZGOVSplit;
  }
  
  public static String getMeetingfujian() {
    return Meetingfujian;
  }
  
  public static void setMeetingfujian(String Meetingfujian) {
    SystemCommon.Meetingfujian = Meetingfujian;
  }
  
  private static int portalLong = 40;
  
  private static int portalShort = 20;
  
  private static String batchAdd = "1";
  
  public static String getBatchAdd() {
    return batchAdd;
  }
  
  public static void setBatchAdd(String batchAdd) {
    SystemCommon.batchAdd = batchAdd;
  }
  
  public static int getIsCustomBase() {
    return isCustomBase;
  }
  
  public static int getPersonnel() {
    return personnel;
  }
  
  public static int getEyouSSO() {
    return eyouSSO;
  }
  
  public static void setEyouSSO(int eyouSSO) {
    SystemCommon.eyouSSO = eyouSSO;
  }
  
  public static void setPersonnel(int personnel) {
    SystemCommon.personnel = personnel;
  }
  
  public static int getAudit() {
    return audit;
  }
  
  public static void setAudit(int audit) {
    SystemCommon.audit = audit;
  }
  
  public static void SetShowUnFormalUser(String showUnFormalUser) {
    SystemCommon.showUnFormalUser = showUnFormalUser;
  }
  
  public static String getShowUnFormalUser() {
    return showUnFormalUser;
  }
  
  public static int getAutoAudit() {
    return autoAudit;
  }
  
  public static void setAutoAudit(int autoAudit) {
    SystemCommon.autoAudit = autoAudit;
  }
  
  public static String getFinanceSalaryCheckPassWord() {
    if (financeSalaryCheckPassWord == null)
      init(); 
    return financeSalaryCheckPassWord;
  }
  
  public static void setFinanceSalaryCheckPassWord(String financeSalaryCheckPassWord) {
    SystemCommon.financeSalaryCheckPassWord = financeSalaryCheckPassWord;
  }
  
  public static String getFinanceSalaryCorresField() {
    if (financeSalaryCorresField == null)
      init(); 
    return financeSalaryCorresField;
  }
  
  public static void setFinanceSalaryCorresField(String financeSalaryCorresField) {
    SystemCommon.financeSalaryCorresField = financeSalaryCorresField;
  }
  
  public static String getIsSeaMoonService() {
    if (isSeaMoonService == null || "".equals(isSeaMoonService))
      initSeaMoon(); 
    return isSeaMoonService;
  }
  
  public static void setIsSeaMoonService(String isSeaMoonService) {
    SystemCommon.isSeaMoonService = isSeaMoonService;
  }
  
  public static String getSeaMoonServiceIp() {
    if ((seaMoonServiceIp == null || "".equals(seaMoonServiceIp)) && "1".equals(isSeaMoonService))
      initSeaMoon(); 
    return seaMoonServiceIp;
  }
  
  public static void setSeaMoonServiceIp(String seaMoonServiceIp) {
    SystemCommon.seaMoonServiceIp = seaMoonServiceIp;
  }
  
  public static String getSeaMoonServicePort() {
    if ((seaMoonServicePort == null || "".equals(seaMoonServicePort)) && "1".equals(isSeaMoonService))
      initSeaMoon(); 
    return seaMoonServicePort;
  }
  
  public static void setSeaMoonServicePort(String seaMoonServicePort) {
    SystemCommon.seaMoonServicePort = seaMoonServicePort;
  }
  
  public static String getUnitCertifySwitch() {
    if (unitCertifySwitch == null || "".equals(unitCertifySwitch))
      initUnitCertify(); 
    return unitCertifySwitch;
  }
  
  public static void setUnitCertifySwitch(String unitCertifySwitch) {
    SystemCommon.unitCertifySwitch = unitCertifySwitch;
  }
  
  public static String getIfAutoSyncUserUnitCertify() {
    if (ifAutoSyncUserUnitCertify == null || "".equals(ifAutoSyncUserUnitCertify))
      initUnitCertify(); 
    return ifAutoSyncUserUnitCertify;
  }
  
  public static void setIfAutoSyncUserUnitCertify(String ifAutoSyncUserUnitCertify) {
    SystemCommon.ifAutoSyncUserUnitCertify = ifAutoSyncUserUnitCertify;
  }
  
  public static String getOrganizationNameUnitCertify() {
    if (organizationNameUnitCertify == null || "".equals(organizationNameUnitCertify))
      initUnitCertify(); 
    return organizationNameUnitCertify;
  }
  
  public static void setOrganizationNameUnitCertify(String organizationNameUnitCertify) {
    SystemCommon.organizationNameUnitCertify = organizationNameUnitCertify;
  }
  
  public static String getIpPortUnitCertify() {
    if (IpPortUnitCertify == null || "".equals(IpPortUnitCertify))
      initUnitCertify(); 
    return IpPortUnitCertify;
  }
  
  public static void setIpPortUnitCertify(String ipPortUnitCertify) {
    IpPortUnitCertify = ipPortUnitCertify;
  }
  
  public static int getOffice() {
    return office;
  }
  
  public static String getOfficeopen() {
    return Officeopen;
  }
  
  public static String getOfficeopenMyDoc() {
    return OfficeopenMyDoc;
  }
  
  public static String gethuanbaobuCA() {
    return huanbaobuCA;
  }
  
  public static String getDcqService() {
    return DcqService;
  }
  
  public static String gethuanbaobuAuthURL() {
    return huanbaobuAuthURL;
  }
  
  public static String getHuanbaobuAppId() {
    return huanbaobuAppId;
  }
  
  public static String getOfficeFileDownload() {
    return fileDownload;
  }
  
  public static void setOffice(int office) {
    SystemCommon.office = office;
  }
  
  public static void setOfficeFileDownload(String fileDownload) {
    SystemCommon.fileDownload = fileDownload;
  }
  
  public static void setOfficeopen(String Officeopen) {
    SystemCommon.Officeopen = Officeopen;
  }
  
  public static void setOfficeopenMyDoc(String officeopenMyDoc) {
    OfficeopenMyDoc = officeopenMyDoc;
  }
  
  public static void setHuanbaobuCA(String huanbaobuCA) {
    SystemCommon.huanbaobuCA = huanbaobuCA;
  }
  
  public static void setDcqService(String DcqService) {
    SystemCommon.DcqService = DcqService;
  }
  
  public static void setHuanbaobuAuthURL(String huanbaobuAuthURL) {
    SystemCommon.huanbaobuAuthURL = huanbaobuAuthURL;
  }
  
  public static void setHuanbaobuAppId(String huanbaobuAppId) {
    SystemCommon.huanbaobuAppId = huanbaobuAppId;
  }
  
  public static String getDataExportWithSub() {
    return dataExportWithSub;
  }
  
  public static void setDataExportWithSub(String dataExportWithSub) {
    SystemCommon.dataExportWithSub = dataExportWithSub;
  }
  
  public static String getDefaultBrowseRange() {
    return defaultBrowseRange;
  }
  
  public static void setDefaultBrowseRange(String defaultBrowseRange) {
    SystemCommon.defaultBrowseRange = defaultBrowseRange;
  }
  
  public static String getModules() {
    return modules;
  }
  
  public static void setModules(String modules) {
    SystemCommon.modules = modules;
  }
  
  public static boolean getModuleRight(String code) {
    if (modules.indexOf("," + code + ",") >= 0)
      return true; 
    return false;
  }
  
  public static String getUKey() {
    if (UKey == null)
      initUKey(); 
    return UKey;
  }
  
  public static int getPageNum() {
    return pageNum;
  }
  
  public static int getForumNum() {
    return forumNum;
  }
  
  public static String getForumOrder() {
    return forumOrder;
  }
  
  public static String getFileDealWithName() {
    if (fileDealWithTitle == null)
      init(); 
    return fileDealWithTitle;
  }
  
  public static String getDatabaseType() {
    if (databaseType == null)
      init(); 
    return databaseType;
  }
  
  public static String getImagShowUse() {
    if (ImagShowUse == null)
      init(); 
    return ImagShowUse;
  }
  
  public static String getStockShowUse() {
    if (stockShow == null)
      init(); 
    return stockShow;
  }
  
  public static String getCurrentOrgShowUse() {
    if (currentOrg == null)
      init(); 
    return currentOrg;
  }
  
  public static String getEquipmentUse() {
    if (equipmentUse == null)
      init(); 
    return equipmentUse;
  }
  
  public static String getEquipmentName() {
    if (equipmentName == null)
      init(); 
    return equipmentName;
  }
  
  public static String getUseRTX() {
    if (useRTX == null)
      init(); 
    return useRTX;
  }
  
  public static String getUseRelProject() {
    return "-1";
  }
  
  public static String getFlowCommentRange() {
    return "-1";
  }
  
  public static String getFlowSearchColumn() {
    return "-1";
  }
  
  public static int getIsShowButton() {
    return isShowButton;
  }
  
  public static void setIsShowButton(int isShowButton) {
    SystemCommon.isShowButton = isShowButton;
  }
  
  public static String getPdfToolPath() {
    return pdfToolPath;
  }
  
  public static void setPdfToolPath(String pdfToolPath) {
    SystemCommon.pdfToolPath = pdfToolPath;
  }
  
  public static void init() {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("FileDealWith");
      fileDealWithTitle = node.getAttributeValue("title");
      node = root.getChild("financeSalaryCheckPassWord");
      if (node != null) {
        financeSalaryCheckPassWord = node.getAttributeValue("use");
      } else {
        financeSalaryCheckPassWord = "1";
      } 
      node = root.getChild("financeSalaryCorresField");
      if (node != null) {
        financeSalaryCorresField = node.getAttributeValue("value");
      } else {
        financeSalaryCorresField = "userAccounts";
      } 
      node = root.getChild("SMSServer");
      canSendSMS = node.getAttributeValue("type");
      node = root.getChild("Database");
      databaseType = node.getAttributeValue("type").toLowerCase();
      if (node.getAttribute("isdm") != null)
        isdm = node.getAttributeValue("isdm"); 
      node = root.getChild("RtxServer");
      useRTX = node.getAttributeValue("use");
      node = root.getChild("BrowseRange");
      if (node != null) {
        useBrowseRange = node.getAttributeValue("use");
        defaultBrowseRange = node.getAttributeValue("defaultRange");
      } 
      node = root.getChild("GrantRange");
      if (node != null) {
        useGrantRange = node.getAttributeValue("use");
        defaultGrantRange = node.getAttributeValue("defaultRange");
      } 
      node = root.getChild("Sap");
      if (node != null)
        useSAP = node.getAttributeValue("use"); 
      node = root.getChild("userDefineSignaturePassword");
      if (node != null) {
        userDefineSignaturePassword = Integer.parseInt((node.getAttributeValue("use") == null) ? "1" : node.getAttributeValue("use"));
        setUserDefineSignaturePassword(userDefineSignaturePassword);
      } 
      node = root.getChild("ManagementCenter");
      if (node != null) {
        showDeleteOnDone = (node.getAttributeValue("showDeleteOnDone") == null) ? "0" : node.getAttributeValue("showDeleteOnDone");
        showReSubmitOnDone = (node.getAttributeValue("showReSubmitOnDone") == null) ? "0" : node.getAttributeValue("showReSubmitOnDone");
        reSubmitOnDoneUseOldNumber = (node.getAttributeValue("reSubmitOnDoneUseOldNumber") == null) ? "0" : node.getAttributeValue("reSubmitOnDoneUseOldNumber");
        reSubmitOnBackUseOldNumber = (node.getAttributeValue("reSubmitOnBackUseOldNumber") == null) ? "0" : node.getAttributeValue("reSubmitOnBackUseOldNumber");
      } 
      node = root.getChild("MailServer");
      if (node != null) {
        setUseMailServer("1".equals(node.getAttributeValue("use")));
        setMailServerURL(node.getAttributeValue("url"));
      } 
      node = root.getChild("DataExport");
      if (node != null)
        dataExportWithSub = node.getAttributeValue("withSubData"); 
      node = root.getChild("FNum");
      if (node != null)
        forumNum = Integer.valueOf(node.getAttributeValue("forum")).intValue(); 
      node = root.getChild("position_LBS");
      if (node != null)
        position_LBS = node.getAttributeValue("use"); 
      node = root.getChild("PNum");
      if (node != null)
        pageNum = Integer.valueOf(node.getAttributeValue("page")).intValue(); 
      node = root.getChild("ddurl");
      if (node != null)
        ddurl = node.getAttributeValue("url"); 
      node = root.getChild("ForumOrder");
      if (node != null)
        forumOrder = node.getAttributeValue("order"); 
      node = root.getChild("WorkFlowEditorVersion");
      if (node != null)
        workFlowEditorVersion = node.getAttributeValue("version"); 
      node = root.getChild("WeiXinQiYeHao");
      if (node != null) {
        useWeiXinQYH = node.getAttributeValue("use");
        weixinQYHUserCount = node.getAttributeValue("maxUserCount");
        useAutoSynchronous = node.getAttributeValue("useAutoSynchronous");
      } 
      node = root.getChild("DingDing");
      if (node != null) {
        useDingDing = node.getAttributeValue("use");
        dingDingAutoSynchronous = node.getAttributeValue("useAutoSynchronous");
      } 
      node = root.getChild("FormProgram");
      if (node != null)
        formProgram = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("SyWorkflowToHR");
      if (node != null)
        syWorkflowToHR = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("ShowUnFormalUser");
      if (node != null)
        showUnFormalUser = node.getAttributeValue("use"); 
      node = root.getChild("ShowBackComment");
      if (node != null)
        showBackComment = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("showPassword");
      if (node != null)
        showPassword = node.getAttributeValue("flag"); 
      node = root.getChild("Office");
      if (node != null)
        office = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("Officeopen");
      if (node != null)
        Officeopen = node.getAttributeValue("use"); 
      node = root.getChild("OfficeopenMyDoc");
      if (node != null)
        OfficeopenMyDoc = node.getAttributeValue("use"); 
      node = root.getChild("rwsurl");
      if (node != null)
        rwsurl = node.getAttributeValue("url"); 
      node = root.getChild("comeback");
      if (node != null)
        comeback = node.getAttributeValue("flag"); 
      node = root.getChild("hntdIP");
      if (node != null)
        hntdIP = node.getAttributeValue("IP"); 
      node = root.getChild("hntdMainIP");
      if (node != null)
        hntdMainIP = node.getAttributeValue("IP"); 
      node = root.getChild("tongbutime");
      if (node != null)
        tongbutime = node.getAttributeValue("timeStr"); 
      node = root.getChild("keyboardTip");
      if (node != null)
        keyboardTip = node.getAttributeValue("flag"); 
      node = root.getChild("HuanbaobuCA");
      if (node != null) {
        huanbaobuCA = node.getAttributeValue("use");
        huanbaobuAuthURL = node.getAttributeValue("authURL");
        huanbaobuAppId = node.getAttributeValue("appId");
      } 
      node = root.getChild("DcqService");
      if (node != null)
        DcqService = node.getAttributeValue("url"); 
      node = root.getChild("fileDownload");
      if (node != null)
        fileDownload = node.getAttributeValue("use"); 
      node = root.getChild("Audit");
      if (node != null)
        audit = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("AutoAudit");
      if (node != null)
        autoAudit = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("Personnel");
      if (node != null)
        personnel = Integer.parseInt(node.getAttributeValue("can")); 
      List<Element> list = root.getChildren("zyxx");
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          Element temp = list.get(i);
          ZyPojo pojo = new ZyPojo();
          pojo.setRoleid(temp.getAttributeValue("roleid"));
          pojo.setUrl(temp.getAttributeValue("url"));
          pojo.setLabel(temp.getAttributeValue("label"));
          zyMaps.add(pojo);
        }  
      node = root.getChild("MultiDepart");
      if (node != null) {
        multiDepart = Integer.parseInt(node.getAttributeValue("use"));
      } else {
        multiDepart = 0;
      } 
      node = root.getChild("FlowDossierJoinType");
      if (node != null)
        flowDossierJoinType = node.getAttributeValue("joinType"); 
      node = root.getChild("isShowProject");
      if (node != null)
        isShowProject = Integer.valueOf(node.getAttributeValue("show")).intValue(); 
      node = root.getChild("isCustomBase");
      if (node != null)
        isCustomBase = Integer.valueOf(node.getAttributeValue("use")).intValue(); 
      node = root.getChild("chatSingle");
      if (node != null)
        setChatSingle(node.getAttributeValue("single")); 
      node = root.getChild("report");
      if (node != null)
        setReport(node.getAttributeValue("use")); 
      node = root.getChild("browseInner");
      if (node != null)
        setBrowseInner(node.getAttributeValue("range")); 
      node = root.getChild("innerShow");
      if (node != null)
        setInnerShow(Integer.valueOf(node.getAttributeValue("show")).intValue()); 
      node = root.getChild("isShowButton");
      if (node != null)
        setIsShowButton(Integer.valueOf(node.getAttributeValue("show")).intValue()); 
      node = root.getChild("voitureapplayrange");
      if (node != null)
        setVoitureapplayrange(node.getAttributeValue("use")); 
      node = root.getChild("personPortalUse");
      if (node != null)
        setPersonPortalUse(node.getAttributeValue("use")); 
      node = root.getChild("isshowDealwithOrgName");
      if (node != null) {
        setIsshowDealwithOrgName(node.getAttributeValue("use"));
        setIsshowOrgNameLevel(node.getAttributeValue("show"));
      } 
      node = root.getChild("RtxSms");
      if (node != null) {
        setRtxSmsUse(node.getAttributeValue("use"));
        setRtxCordId(node.getAttributeValue("CordId"));
        setRtxUserName(node.getAttributeValue("userName"));
        setRtxPassword(node.getAttributeValue("password"));
        setRtxIP(node.getAttributeValue("ip"));
      } 
      node = root.getChild("glxmlimit");
      if (node != null)
        setGlxmlimit(node.getAttributeValue("use")); 
      node = root.getChild("glxmstatus");
      if (node != null)
        setGlxmstatus(node.getAttributeValue("use")); 
      node = root.getChild("IsShowRead");
      if (node != null) {
        IsShowRead = node.getAttributeValue("use");
        setIsShowRead(IsShowRead);
      } 
      node = root.getChild("kqdataFormOA");
      if (node != null)
        setJsoaUse(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("ArchiveToInfo");
      if (node != null)
        setArchiveToInfo(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("showChildOrg");
      if (node != null) {
        setChildUse(Integer.valueOf(node.getAttributeValue("use")).intValue());
        setChildShow(Integer.valueOf(node.getAttributeValue("show")).intValue());
      } 
      node = root.getChild("collectSub");
      if (node != null)
        setCollectSub(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("BrowserShowFile");
      if (node != null)
        setFileShowUse(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("CommentStyle");
      if (node != null) {
        commentFontSize = node.getAttributeValue("fontSize");
        if (node.getAttributeValue("docFontSize") != null)
          docCommentFontSize = node.getAttributeValue("docFontSize"); 
        if (node.getAttributeValue("publicSignHeight") != null)
          publicSignHeight = node.getAttributeValue("publicSignHeight"); 
        if (node.getAttributeValue("userSignHeight") != null)
          userSignHeight = node.getAttributeValue("userSignHeight"); 
      } 
      node = root.getChild("defaultSelected");
      if (node != null)
        setDefaultSelected("1".equals(node.getAttributeValue("type"))); 
      node = root.getChild("infoEdit");
      if (node != null)
        setInfoEdit(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("allAttachEdit");
      if (node != null)
        setAllAttachEdit(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("imagShow");
      if (node != null)
        ImagShowUse = node.getAttributeValue("use"); 
      node = root.getChild("stockShow");
      if (node != null)
        stockShow = node.getAttributeValue("use"); 
      node = root.getChild("currentOrg");
      if (node != null)
        currentOrg = node.getAttributeValue("use"); 
      node = root.getChild("Equipment");
      if (node != null) {
        equipmentUse = node.getAttributeValue("use");
        equipmentName = node.getAttributeValue("name");
      } 
      node = root.getChild("FlowDirectSend");
      if (node != null)
        setFlowDirectSend(Integer.valueOf(node.getAttributeValue("auto")).intValue()); 
      node = root.getChild("webservice");
      if (node != null)
        setWebservice(Integer.valueOf(node.getAttributeValue("use")).intValue()); 
      node = root.getChild("meeting");
      if (node != null) {
        setMeetingNum(Integer.valueOf(node.getAttributeValue("num")).intValue());
        setMeetingListNum(Integer.valueOf(node.getAttributeValue("listNum")).intValue());
      } 
      node = root.getChild("meetingTask");
      if (node != null) {
        setMeetingTaskUse(Integer.valueOf(node.getAttributeValue("use")).intValue());
        setMeetingTaskShowName(node.getAttributeValue("showName"));
      } 
      node = root.getChild("meetingChat");
      if (node != null) {
        setMeetingChatUse(Integer.valueOf(node.getAttributeValue("use")).intValue());
        setMeetingChatShShowName(node.getAttributeValue("showName"));
      } 
      node = root.getChild("meetingPrintShow");
      if (node != null) {
        setMeetingBeginShow(node.getAttributeValue("begin"));
        setMeetingEndShow(node.getAttributeValue("end"));
        setMeetingType(node.getAttributeValue("type"));
      } 
      node = root.getChild("portal");
      if (node != null) {
        setPortalLong(Integer.valueOf((node.getAttributeValue("long") == null) ? "40" : node.getAttributeValue("long")).intValue());
        setPortalShort(Integer.valueOf((node.getAttributeValue("short") == null) ? "20" : node.getAttributeValue("short")).intValue());
      } 
      node = root.getChild("FlowBackToSubmitPersonOnly");
      if (node != null)
        setFlowBackToSubmitPersonOnly(node.getAttributeValue("use")); 
      node = root.getChild("LZGOVSplit");
      if (node != null)
        setLZGOVSplit(node.getAttributeValue("use")); 
      node = root.getChild("Meetingfujian");
      if (node != null)
        setMeetingfujian(node.getAttributeValue("use")); 
      node = root.getChild("showWebsite");
      if (node != null) {
        setIfShowWebsite(Integer.valueOf((node.getAttributeValue("use") == null) ? "0" : node.getAttributeValue("use")).intValue());
        setShowWebsiteNum(Integer.valueOf((node.getAttributeValue("num") == null) ? "0" : node.getAttributeValue("num")).intValue());
        setShowMoreNum(Integer.valueOf((node.getAttributeValue("more") == null) ? "15" : node.getAttributeValue("more")).intValue());
      } 
      node = root.getChild("DocumentPageStyle");
      if (node != null) {
        showDocumentProcessTitle = node.getAttributeValue("showDocumentProcessTitle");
        sendfileAccessoryEdit = node.getAttributeValue("sendfileAccessoryEdit");
        docFormFontSize = node.getAttributeValue("formFontSize");
        docShowDbrName = (node.getAttributeValue("docShowDbrName") == null) ? "0" : node.getAttributeValue("docShowDbrName");
      } 
      node = root.getChild("WorkflowPageStyle");
      if (node != null) {
        showProcessTitle = node.getAttributeValue("showProcessTitle");
        if (node.getAttributeValue("fujianFontSize") != null)
          fujianFontSize = node.getAttributeValue("fujianFontSize"); 
        if (node.getAttributeValue("showCompleteTime") != null)
          showCompleteTime = node.getAttributeValue("showCompleteTime"); 
      } 
      node = root.getChild("wxkjKq");
      if (node != null) {
        setWxkjKq(Integer.valueOf((node.getAttributeValue("use") == null) ? "0" : node.getAttributeValue("use")).intValue());
        setWxdel(Integer.valueOf((node.getAttributeValue("deleteOldDate") == null) ? "0" : node.getAttributeValue("deleteOldDate")).intValue());
        setWxTime((node.getAttributeValue("time") == null) ? "05:00" : node.getAttributeValue("time"));
        setDayBegin((node.getAttributeValue("dayBegin") == null) ? "04:00" : node.getAttributeValue("dayBegin"));
        setKqBegin((node.getAttributeValue("kqBegin") == null) ? "09:30" : node.getAttributeValue("kqBegin"));
        setKqEnd((node.getAttributeValue("kqEnd") == null) ? "18:30" : node.getAttributeValue("kqEnd"));
      } 
      node = root.getChild("EmailRemind");
      if (node != null)
        emailRemindPrefix = node.getAttributeValue("prefix"); 
      node = root.getChild("panyu");
      if (node != null)
        setsWID(node.getAttributeValue("sWID")); 
      node = root.getChild("app");
      if (node != null) {
        setAppUse(Integer.valueOf((node.getAttributeValue("use") == null) ? "0" : node.getAttributeValue("use")).intValue());
        setAppUrl(node.getAttributeValue("url"));
        setApiKey(node.getAttributeValue("apiKey"));
        setAppId(node.getAttributeValue("appId"));
        setDomainName(node.getAttributeValue("oaDomainName"));
      } 
      node = root.getChild("daxingYun");
      if (node != null)
        setDaxingYun(node.getAttributeValue("url")); 
      node = root.getChild("repeatFileDealwith");
      if (node != null)
        repeatFileDealwith = node.getAttributeValue("showType"); 
      node = root.getChild("customer");
      if (node != null) {
        setCustomerName(node.getAttributeValue("name"));
        setDocSuffix((node.getAttribute("docSuffix") == null) ? "" : node.getAttributeValue("docSuffix"));
      } 
      node = root.getChild("yunshipei");
      if (node != null)
        setYunshipeiName(node.getAttributeValue("name")); 
      node = root.getChild("zkyJx");
      if (node != null)
        setZkyNd(node.getAttributeValue("nd")); 
      node = root.getChild("SubTableScrollbar");
      if (node != null) {
        setSubTableScrollbar(node.getAttributeValue("use"));
        setSubTableScrollbarBeginCnt(Integer.valueOf(node.getAttributeValue("cnt")).intValue());
      } 
      node = root.getChild("jgdz");
      if (node != null)
        setJgdz(node.getAttributeValue("must")); 
      node = root.getChild("chinaLifekq");
      if (node != null) {
        setClFirst(node.getAttributeValue("first"));
        setClSecond(node.getAttributeValue("second"));
        setClkq(node.getAttributeValue("clkq"));
        setTbUser((node.getAttribute("user") == null) ? "03:00" : node.getAttributeValue("user"));
      } 
      node = root.getChild("ExecuteLoopTask");
      if (node != null)
        setExecuteLoopTask(node.getAttributeValue("use")); 
      node = root.getChild("IO2File");
      if (node != null)
        setPrint(node.getAttributeValue("print")); 
      node = root.getChild("batchAdd");
      if (node != null)
        setBatchAdd(node.getAttributeValue("use")); 
      node = root.getChild("outStock");
      if (node != null)
        setOutStock(Integer.valueOf(node.getAttributeValue("kucun")).intValue()); 
      node = root.getChild("backCheckMust");
      if (node != null) {
        setBackCheckMust(node.getAttributeValue("use"));
        setBackViewMust(node.getAttributeValue("view"));
      } 
      node = root.getChild("sendSMS");
      if (node != null)
        setSendSMS(node.getAttributeValue("default")); 
      node = root.getChild("logCount");
      if (node != null)
        setLogCount(node.getAttributeValue("org")); 
      node = root.getChild("WorkflowWordisLock");
      if (node != null)
        setWorkflowWordisLock(node.getAttributeValue("use")); 
      node = root.getChild("WorkflowWordisWuhen");
      if (node != null)
        setWorkflowWordisWuhen(node.getAttributeValue("use")); 
      node = root.getChild("cooperate");
      if (node != null && 
        "1".equals(node.getAttributeValue("use")))
        setCooperateUserNum(Integer.valueOf(node.getAttributeValue("num")).intValue()); 
      node = root.getChild("loginField");
      if (node != null)
        setLoginField(node.getAttributeValue("use")); 
      node = root.getChild("pdfTool");
      if (node != null)
        setPdfToolPath(node.getAttributeValue("path")); 
      node = root.getChild("TrantoPdf");
      if (node != null) {
        setIsOpenPDF(node.getAttributeValue("open"));
        setServerIp(node.getAttributeValue("serverIp"));
        setServerPort(node.getAttributeValue("serverPort"));
        setExePath(node.getAttributeValue("exePath"));
      } 
      node = root.getChild("backComment");
      if (node != null)
        setBackComment(node.getAttributeValue("show")); 
      node = root.getChild("langxin");
      if (node != null) {
        setLxOpenTimer(node.getAttributeValue("OpenTimer"));
        setLxDatabaseName(node.getAttributeValue("DatabaseName"));
        setLxIntervalTime(node.getAttributeValue("IntervalTime"));
      } 
      node = root.getChild("addressListItem");
      if (node != null)
        setAddressListItem(node.getAttributeValue("items")); 
      node = root.getChild("meetingSet");
      if (node != null) {
        setMeetingApply(node.getAttributeValue("apply"));
        setMeetingApprove(node.getAttributeValue("approve"));
      } 
      node = root.getChild("OrgTree");
      if (node != null)
        try {
          setOrgTreeShowSimple(Integer.parseInt(node.getAttributeValue("showsimple")));
          setOrgTreeSelectedSimple(Integer.parseInt(node.getAttributeValue("selectedsimple")));
        } catch (Exception exception) {} 
      node = root.getChild("workflow");
      if (node != null)
        setReSubmitDel(node.getAttributeValue("reSubmitDel")); 
      node = root.getChild("contentFilter");
      if (node != null)
        setContentFilter(node.getAttributeValue("use")); 
      node = root.getChild("iWeboffice");
      if (node != null)
        setiWebOfficeVersion(node.getAttributeValue("version")); 
      node = root.getChild("ShowBackCommentFordoc");
      if (node != null)
        showBackFordoc = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("SameNodeAndApproval");
      if (node != null)
        sameNodeAndApproval = Integer.parseInt(node.getAttributeValue("use")); 
      node = root.getChild("messageForDocShow");
      if (node != null)
        messageForDocShow = node.getAttributeValue("use"); 
      node = root.getChild("passwordEncryption");
      if (node != null)
        passwordEncryption = node.getAttributeValue("use"); 
      node = root.getChild("erpStack");
      if (node != null) {
        firSync = node.getAttributeValue("firSync");
        secSync = node.getAttributeValue("secSync");
        inventoryWarning = Integer.valueOf(node.getAttributeValue("inventoryWarning")).intValue();
      } 
      node = root.getChild("erpStack");
      if (node != null) {
        firSync = node.getAttributeValue("firSync");
        secSync = node.getAttributeValue("secSync");
        inventoryWarning = Integer.valueOf(node.getAttributeValue("inventoryWarning")).intValue();
      } 
      node = root.getChild("FujianBatchDownload");
      if (node != null)
        fujianBatchDownload = node.getAttributeValue("use"); 
      node = root.getChild("mobileOrWechatFujianShow");
      if (node != null)
        mobileOrWechatFujianShow = node.getAttributeValue("use"); 
      node = root.getChild("WorkFlowUpdate");
      if (node != null)
        workflowUpdateSyncToInstance = node.getAttributeValue("syncToInstance"); 
      node = root.getChild("WorkFlowDeadline");
      if (node != null)
        deadlineUseWorkingDay = node.getAttributeValue("useWorkingDay"); 
      node = root.getChild("FujianWatchType");
      if (node != null)
        fujianWatchType = node.getAttributeValue("use"); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static String getCommentFontSize() {
    return commentFontSize;
  }
  
  public static String getFujianFontSize() {
    return fujianFontSize;
  }
  
  private static void initUserDatasourceName() {
    FileInputStream configFileInputStream = null;
    try {
      userDataSourceType = new HashMap<Object, Object>();
      userDataSourceLang = new ConcurrentHashMap<Object, Object>();
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/datasource.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> list = root.getChildren();
      if (list.size() > 0) {
        userDataSourceName = new String[list.size()];
        int i = 0;
        for (Element ele : list) {
          userDataSourceName[i] = ele.getAttributeValue("name");
          userDataSourceType.put(userDataSourceName[i], ele.getAttributeValue("databaseType"));
          String lang = ele.getAttributeValue("databaseLang");
          if (lang == null)
            lang = ""; 
          userDataSourceLang.put(userDataSourceName[i], lang);
          i++;
        } 
      } else {
        userDataSourceName = new String[] { "" };
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static void initExtendMenu() {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("Salary");
      salaryMenu = node.getAttributeValue("show");
    } catch (Exception ex) {
      salaryMenu = "0";
      extendMenu = "0";
      ex.printStackTrace();
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static void initUKey() {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("UKey");
      UKey = node.getAttributeValue("type");
      if ("2".equals(UKey)) {
        logType = node.getAttributeValue("logType");
      } else {
        logType = "-1";
      } 
    } catch (Exception ex) {
      UKey = "0";
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static void initSeaMoon() {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element node = doc.getRootElement().getChild("SeaMoon");
      isSeaMoonService = node.getChild("isSeaMoonService").getAttribute("value").getValue();
      seaMoonServiceIp = node.getChild("seaMoonServiceIp").getAttribute("value").getValue();
      seaMoonServicePort = node.getChild("seaMoonServicePort").getAttribute("value").getValue();
      if (seaMoonServicePort == null || "".equals(seaMoonServicePort))
        seaMoonServicePort = "0"; 
    } catch (Exception ex) {
      isSeaMoonService = "0";
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static void initUnitCertify() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element node = doc.getRootElement().getChild("DaXinUnitCertify");
      unitCertifySwitch = node.getChild("unitCertifySwitch").getAttribute("value").getValue();
      ifAutoSyncUserUnitCertify = node.getChild("ifAutoSyncUserUnitCertify").getAttribute("value").getValue();
      organizationNameUnitCertify = node.getChild("organizationNameUnitCertify").getAttribute("value").getValue();
      IpPortUnitCertify = node.getChild("IpPortUnitCertify").getAttribute("value").getValue();
      if (IpPortUnitCertify == null || "".equals(IpPortUnitCertify))
        IpPortUnitCertify = "0"; 
    } catch (Exception ex) {
      unitCertifySwitch = "0";
    } 
  }
  
  private static void initClusterInfo() {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/cluster.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("fileserver");
      useClusterServer = Integer.parseInt(node.getAttributeValue("use"));
      clusterServerUrl = node.getChildText("url");
      clusterServerPath = node.getChildText("path");
      configFileInputStream.close();
    } catch (Exception ex) {
      useClusterServer = -1;
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        System.out.println("集群部署未配置");
      } 
      System.out.println("集群部署未配置");
    } 
  }
  
  public static void getContentTypeConfig() {
    FileInputStream configFileInputStream = null;
    try {
      contentType = new HashMap<String, String>();
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/contentType.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element defaultElement = root.getChild("default");
      List<Element> typeList = defaultElement.getChildren("fileType");
      defaultType = String.valueOf(defaultType) + "begin,";
      for (int i = 0; i < typeList.size(); i++) {
        Element node = typeList.get(i);
        if (node != null) {
          contentType.put(node.getAttributeValue("type"), node.getText());
          setDefaultType(String.valueOf(getDefaultType()) + node.getAttributeValue("type") + ",");
        } 
      } 
      Element extendElement = root.getChild("extend");
      typeList = extendElement.getChildren("fileType");
      extendType = String.valueOf(extendType) + "begin,";
      for (int j = 0; j < typeList.size(); j++) {
        Element node = typeList.get(j);
        if (node != null) {
          contentType.put(node.getAttributeValue("type"), node.getText());
          setExtendType(String.valueOf(getExtendType()) + node.getAttributeValue("type") + ",");
        } 
      } 
      configFileInputStream.close();
    } catch (Exception e) {
      defaultType = ",";
      extendType = ",";
      e.printStackTrace();
    } 
  }
  
  public static int getUseClusterServer() {
    if (useClusterServer == -10000)
      initClusterInfo(); 
    return useClusterServer;
  }
  
  public static String getClusterServerUrl() {
    return clusterServerUrl;
  }
  
  public static String getClusterServerPath() {
    return clusterServerPath;
  }
  
  public static void setExtendMenu(String extendMenu) {
    SystemCommon.extendMenu = extendMenu;
  }
  
  public static String getExtendMenu() {
    return extendMenu;
  }
  
  public static String getSalaryMenu() {
    if (salaryMenu == null)
      initExtendMenu(); 
    return salaryMenu;
  }
  
  public static String[] getUserDatasourceName() {
    if (userDataSourceName == null)
      initUserDatasourceName(); 
    return userDataSourceName;
  }
  
  public static String getUserDatabaseType(String dataSourceName) {
    if (userDataSourceType == null)
      initUserDatasourceName(); 
    return (String)userDataSourceType.get(dataSourceName);
  }
  
  public static String getUserDatabaseLang(String dataSourceName) {
    if (userDataSourceLang == null)
      initUserDatasourceName(); 
    return (String)userDataSourceLang.get(dataSourceName);
  }
  
  public static String getLicType() {
    return licType;
  }
  
  public static void setLicType(String licType) {
    SystemCommon.licType = licType;
  }
  
  public static void main(String[] args) {
    getFileDealWithName();
    getDatabaseType();
  }
  
  public static String getDefaultGrantRange() {
    return defaultGrantRange;
  }
  
  public static void setDefaultGrantRange(String defaultGrantRange) {
    SystemCommon.defaultGrantRange = defaultGrantRange;
  }
  
  public static String getUseGrantRange() {
    return useGrantRange;
  }
  
  public static void setUseGrantRange(String useGrantRange) {
    SystemCommon.useGrantRange = useGrantRange;
  }
  
  public static String getUseBrowseRange() {
    return useBrowseRange;
  }
  
  public static void setUseBrowseRange(String useBrowseRange) {
    SystemCommon.useBrowseRange = useBrowseRange;
  }
  
  public static String getMobileLogonNum() {
    return mobileLogonNum;
  }
  
  public static void setMobileLogonNum(String mobileLogonNum) {
    SystemCommon.mobileLogonNum = mobileLogonNum;
  }
  
  public static int getMultiDepart() {
    return multiDepart;
  }
  
  public static void setMultiDepart(int multiDepart) {
    SystemCommon.multiDepart = multiDepart;
  }
  
  public static int getFormProgram() {
    return formProgram;
  }
  
  public static int getSYWorkflowHR() {
    return syWorkflowToHR;
  }
  
  public static int getShowBackComment() {
    return showBackComment;
  }
  
  public static void setFormProgram(int formProgram) {
    SystemCommon.formProgram = formProgram;
  }
  
  public static int getMutliBrowser() {
    return mutliBrowser;
  }
  
  public static void setMutliBrowser(int mutliBrowser) {
    SystemCommon.mutliBrowser = mutliBrowser;
  }
  
  public static String getFlowDossierJoinType() {
    return flowDossierJoinType;
  }
  
  public static void setFlowDossierJoinType(String flowDossierJoinType) {
    SystemCommon.flowDossierJoinType = flowDossierJoinType;
  }
  
  public static int getUserDefineSignaturePassword() {
    return userDefineSignaturePassword;
  }
  
  public static void setUserDefineSignaturePassword(int userDefineSignaturePassword) {
    SystemCommon.userDefineSignaturePassword = userDefineSignaturePassword;
  }
  
  public static int getHandWriteNotEmpty() {
    return handWriteNotEmpty;
  }
  
  public static void setHandWriteNotEmpty(int handWriteNotEmpty) {
    SystemCommon.handWriteNotEmpty = handWriteNotEmpty;
  }
  
  public static String getChatSingle() {
    return chatSingle;
  }
  
  public static void setChatSingle(String chatSingle) {
    SystemCommon.chatSingle = chatSingle;
  }
  
  public static String getTo() {
    return to;
  }
  
  public static void setTo(String to) {
    SystemCommon.to = to;
  }
  
  public static String getReport() {
    return report;
  }
  
  public static void setReport(String report) {
    SystemCommon.report = report;
  }
  
  public static String getLogType() {
    return logType;
  }
  
  public static void setLogType(String logType) {
    SystemCommon.logType = logType;
  }
  
  public static String getBrowseInner() {
    return browseInner;
  }
  
  public static void setBrowseInner(String browseInner) {
    SystemCommon.browseInner = browseInner;
  }
  
  public static int getInnerShow() {
    return innerShow;
  }
  
  public static void setInnerShow(int innerShow) {
    SystemCommon.innerShow = innerShow;
  }
  
  public static String getUseArchives() {
    return useArchives;
  }
  
  public static void setUseArchives(String useArchives) {
    SystemCommon.useArchives = useArchives;
  }
  
  public static int getJsoaUse() {
    return jsoaUse;
  }
  
  public static void setJsoaUse(int jsoaUse) {
    SystemCommon.jsoaUse = jsoaUse;
  }
  
  public static int getShowBackFordoc() {
    return showBackFordoc;
  }
  
  public static int getArchiveToInfo() {
    return archiveToInfo;
  }
  
  public static void setArchiveToInfo(int archiveToInfo) {
    SystemCommon.archiveToInfo = archiveToInfo;
  }
  
  public static boolean getUseDataEngine() {
    if (modules.indexOf(",dataengine,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getShowTableField() {
    if (modules.indexOf(",showtablefield,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getFormExtends() {
    if (modules.indexOf(",formextends,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getFlowExtends() {
    if (modules.indexOf(",flowextends,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getCheckon() {
    if (modules.indexOf(",checkon,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getLDAP_AD() {
    if (modules.indexOf(",ladp_ad,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getUSBKey() {
    if (modules.indexOf(",usbkey,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getRealTimeMessage() {
    if (modules.indexOf(",realtimemessage,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getSMS_CAT() {
    if (modules.indexOf(",sms_cat,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getSMS_GATE() {
    if (modules.indexOf(",sms_gate,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getHandWrite() {
    if (modules.indexOf(",handwrite,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getISignature() {
    if (modules.indexOf(",isignature,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getSalaryQuery() {
    if (modules.indexOf(",salaryquery,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getHRmanager() {
    if (modules.indexOf(",hr,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getHrUnderling() {
    if (modules.indexOf(",hr-underling,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getHrMyinfo() {
    if (modules.indexOf(",hr-myinfo,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getHrOrganization() {
    if (modules.indexOf(",hr-organization,") >= 0)
      return true; 
    return false;
  }
  
  public static boolean getHRExam() {
    if (modules.indexOf(",exam,") >= 0)
      return true; 
    return false;
  }
  
  public static int getChildUse() {
    return childUse;
  }
  
  public static void setChildUse(int childUse) {
    SystemCommon.childUse = childUse;
  }
  
  public static int getChildShow() {
    return childShow;
  }
  
  public static void setChildShow(int childShow) {
    SystemCommon.childShow = childShow;
  }
  
  public static int getCollectSub() {
    return collectSub;
  }
  
  public static void setCollectSub(int collectSub) {
    SystemCommon.collectSub = collectSub;
  }
  
  public static int getFileShowUse() {
    return fileShowUse;
  }
  
  public static void setFileShowUse(int fileShowUse) {
    SystemCommon.fileShowUse = fileShowUse;
  }
  
  public static Map<String, String> getContentType() {
    if (contentType == null)
      getContentTypeConfig(); 
    return contentType;
  }
  
  public static void setContentType(Map<String, String> contentType) {
    SystemCommon.contentType = contentType;
  }
  
  public static String getExtendType() {
    if (",".equals(extendType))
      getContentTypeConfig(); 
    return extendType;
  }
  
  public static void setExtendType(String extendType) {
    SystemCommon.extendType = extendType;
  }
  
  public static String getDefaultType() {
    if (",".equals(defaultType))
      getContentTypeConfig(); 
    return defaultType;
  }
  
  public static void setDefaultType(String defaultType) {
    SystemCommon.defaultType = defaultType;
  }
  
  public static boolean isDefaultSelected() {
    return defaultSelected;
  }
  
  public static void setDefaultSelected(boolean defaultSelected) {
    SystemCommon.defaultSelected = defaultSelected;
  }
  
  public static int getInfoEdit() {
    return infoEdit;
  }
  
  public static void setInfoEdit(int infoEdit) {
    SystemCommon.infoEdit = infoEdit;
  }
  
  public static int getFlowDirectSend() {
    return flowDirectSend;
  }
  
  public static void setFlowDirectSend(int flowDirectSend) {
    SystemCommon.flowDirectSend = flowDirectSend;
  }
  
  public static String getIsShowRead() {
    return IsShowRead;
  }
  
  public static void setIsShowRead(String IsShowRead) {
    SystemCommon.IsShowRead = IsShowRead;
  }
  
  public static int getWebservice() {
    return webservice;
  }
  
  public static void setWebservice(int webservice) {
    SystemCommon.webservice = webservice;
  }
  
  public static int getMeetingNum() {
    return meetingNum;
  }
  
  public static void setMeetingNum(int meetingNum) {
    SystemCommon.meetingNum = meetingNum;
  }
  
  public static int getMeetingListNum() {
    return meetingListNum;
  }
  
  public static void setMeetingListNum(int meetingListNum) {
    SystemCommon.meetingListNum = meetingListNum;
  }
  
  public static int getMeetingTaskUse() {
    return meetingTaskUse;
  }
  
  public static void setMeetingTaskUse(int meetingTaskUse) {
    SystemCommon.meetingTaskUse = meetingTaskUse;
  }
  
  public static int getMeetingChatUse() {
    return meetingChatUse;
  }
  
  public static void setMeetingChatUse(int meetingChatUse) {
    SystemCommon.meetingChatUse = meetingChatUse;
  }
  
  public static String getMeetingTaskShowName() {
    return meetingTaskShowName;
  }
  
  public static void setMeetingTaskShowName(String meetingTaskShowName) {
    SystemCommon.meetingTaskShowName = meetingTaskShowName;
  }
  
  public static String getMeetingChatShowName() {
    return meetingChatShowName;
  }
  
  public static void setMeetingChatShShowName(String meetingChatShShowName) {
    meetingChatShowName = meetingChatShowName;
  }
  
  public static String getMeetingBeginShow() {
    return meetingBeginShow;
  }
  
  public static void setMeetingBeginShow(String meetingBeginShow) {
    SystemCommon.meetingBeginShow = meetingBeginShow;
  }
  
  public static String getMeetingEndShow() {
    return meetingEndShow;
  }
  
  public static void setMeetingEndShow(String meetingEndShow) {
    SystemCommon.meetingEndShow = meetingEndShow;
  }
  
  public static int getPortalLong() {
    return portalLong;
  }
  
  public static void setPortalLong(int portalLong) {
    SystemCommon.portalLong = portalLong;
  }
  
  public static int getPortalShort() {
    return portalShort;
  }
  
  public static void setPortalShort(int portalShort) {
    SystemCommon.portalShort = portalShort;
  }
  
  public static String getMeetingType() {
    return meetingType;
  }
  
  public static void setMeetingType(String meetingType) {
    SystemCommon.meetingType = meetingType;
  }
  
  public static int getIfShowWebsite() {
    return ifShowWebsite;
  }
  
  public static void setIfShowWebsite(int ifShowWebsite) {
    SystemCommon.ifShowWebsite = ifShowWebsite;
  }
  
  public static String getShowDocumentProcessTitle() {
    return showDocumentProcessTitle;
  }
  
  public static void setShowDocumentProcessTitle(String showDocumentProcessTitle) {
    SystemCommon.showDocumentProcessTitle = showDocumentProcessTitle;
  }
  
  public static String getSendfileAccessoryEdit() {
    return sendfileAccessoryEdit;
  }
  
  public static void setSendfileAccessoryEdit(String sendfileAccessoryEdit) {
    SystemCommon.sendfileAccessoryEdit = sendfileAccessoryEdit;
  }
  
  public static String getShowProcessTitle() {
    return showProcessTitle;
  }
  
  public static void setShowProcessTitle(String showProcessTitle) {
    SystemCommon.showProcessTitle = showProcessTitle;
  }
  
  public static String getFlowBackToSubmitPersonOnly() {
    return flowBackToSubmitPersonOnly;
  }
  
  public static void setFlowBackToSubmitPersonOnly(String flowTureToSubmitPersonOnly) {
    flowBackToSubmitPersonOnly = flowTureToSubmitPersonOnly;
  }
  
  public static String getDocCommentFontSize() {
    return docCommentFontSize;
  }
  
  public static void setDocCommentFontSize(String docCommentFontSize) {
    SystemCommon.docCommentFontSize = docCommentFontSize;
  }
  
  public static String getDocFormFontSize() {
    return docFormFontSize;
  }
  
  public static void setDocFormFontSize(String docFormFontSize) {
    SystemCommon.docFormFontSize = docFormFontSize;
  }
  
  public static String getPublicSignHeight() {
    return publicSignHeight;
  }
  
  public static void setPublicSignHeight(String publicSignHeight) {
    SystemCommon.publicSignHeight = publicSignHeight;
  }
  
  public static String getUserSignHeight() {
    return userSignHeight;
  }
  
  public static void setUserSignHeight(String userSignHeight) {
    SystemCommon.userSignHeight = userSignHeight;
  }
  
  public static int getShowWebsiteNum() {
    return showWebsiteNum;
  }
  
  public static void setShowWebsiteNum(int showWebsiteNum) {
    SystemCommon.showWebsiteNum = showWebsiteNum;
  }
  
  public static int getShowMoreNum() {
    return showMoreNum;
  }
  
  public static void setShowMoreNum(int showMoreNum) {
    SystemCommon.showMoreNum = showMoreNum;
  }
  
  public static int getWxkjKq() {
    return wxkjKq;
  }
  
  public static void setWxkjKq(int wxkjKq) {
    SystemCommon.wxkjKq = wxkjKq;
  }
  
  public static int getWxdel() {
    return wxdel;
  }
  
  public static void setWxdel(int wxdel) {
    SystemCommon.wxdel = wxdel;
  }
  
  public static String getWxTime() {
    return wxTime;
  }
  
  public static void setWxTime(String wxTime) {
    SystemCommon.wxTime = wxTime;
  }
  
  public static String getDayBegin() {
    return dayBegin;
  }
  
  public static void setDayBegin(String dayBegin) {
    SystemCommon.dayBegin = dayBegin;
  }
  
  public static String getKqBegin() {
    return kqBegin;
  }
  
  public static void setKqBegin(String kqBegin) {
    SystemCommon.kqBegin = kqBegin;
  }
  
  public static String getKqEnd() {
    return kqEnd;
  }
  
  public static void setKqEnd(String kqEnd) {
    SystemCommon.kqEnd = kqEnd;
  }
  
  public static String getEmailRemindPrefix() {
    return emailRemindPrefix;
  }
  
  public static void setEmailRemindPrefix(String emailRemindPrefix) {
    SystemCommon.emailRemindPrefix = emailRemindPrefix;
  }
  
  public static String getsWID() {
    return sWID;
  }
  
  public static void setsWID(String sWID) {
    SystemCommon.sWID = sWID;
  }
  
  public static int getAppUse() {
    return appUse;
  }
  
  public static void setAppUse(int appUse) {
    SystemCommon.appUse = appUse;
  }
  
  public static String getAppUrl() {
    return appUrl;
  }
  
  public static void setAppUrl(String appUrl) {
    SystemCommon.appUrl = appUrl;
  }
  
  public static String getApiKey() {
    return apiKey;
  }
  
  public static void setApiKey(String apiKey) {
    SystemCommon.apiKey = apiKey;
  }
  
  public static String getAppId() {
    return appId;
  }
  
  public static void setAppId(String appId) {
    SystemCommon.appId = appId;
  }
  
  public static String getDomainName() {
    return domainName;
  }
  
  public static void setDomainName(String domainName) {
    SystemCommon.domainName = domainName;
  }
  
  public static String getRepeatFileDealwith() {
    return repeatFileDealwith;
  }
  
  public static void setRepeatFileDealwith(String repeatFileDealwith) {
    SystemCommon.repeatFileDealwith = repeatFileDealwith;
  }
  
  public static String getCustomerName() {
    return customerName;
  }
  
  public static void setCustomerName(String customerName) {
    SystemCommon.customerName = customerName;
  }
  
  public static String getDocSuffix() {
    return docSuffix;
  }
  
  public static void setDocSuffix(String docSuffix) {
    SystemCommon.docSuffix = docSuffix;
  }
  
  public static String getZkyNd() {
    return zkyNd;
  }
  
  public static void setZkyNd(String zkyNd) {
    SystemCommon.zkyNd = zkyNd;
  }
  
  public static String getJgdz() {
    return jgdz;
  }
  
  public static void setJgdz(String jgdz) {
    SystemCommon.jgdz = jgdz;
  }
  
  public static String getClFirst() {
    return clFirst;
  }
  
  public static void setClFirst(String clFirst) {
    SystemCommon.clFirst = clFirst;
  }
  
  public static String getClSecond() {
    return clSecond;
  }
  
  public static void setClSecond(String clSecond) {
    SystemCommon.clSecond = clSecond;
  }
  
  public static String getClkq() {
    return clkq;
  }
  
  public static void setClkq(String clkq) {
    SystemCommon.clkq = clkq;
  }
  
  public static String getExecuteLoopTask() {
    return executeLoopTask;
  }
  
  public static void setExecuteLoopTask(String executeLoopTask) {
    SystemCommon.executeLoopTask = executeLoopTask;
  }
  
  public static String getUseWeiXinQYH() {
    return useWeiXinQYH;
  }
  
  public static void setUseWeiXinQYH(String useWeiXinQYH) {
    SystemCommon.useWeiXinQYH = useWeiXinQYH;
  }
  
  public static String getWeixinQYHUserCount() {
    return weixinQYHUserCount;
  }
  
  public static void setWeixinQYHUserCount(String weixinQYHUserCount) {
    SystemCommon.weixinQYHUserCount = weixinQYHUserCount;
  }
  
  public static String getDaxingYun() {
    return daxingYun;
  }
  
  public static void setDaxingYun(String daxingYun) {
    SystemCommon.daxingYun = daxingYun;
  }
  
  public static String getPrint() {
    return print;
  }
  
  public static void setPrint(String print) {
    SystemCommon.print = print;
  }
  
  public static String getTbUser() {
    return tbUser;
  }
  
  public static void setTbUser(String tbUser) {
    SystemCommon.tbUser = tbUser;
  }
  
  public static String getUseSAP() {
    return useSAP;
  }
  
  public static void setUseSAP(String use) {
    useSAP = use;
  }
  
  public static int getOutStock() {
    return outStock;
  }
  
  public static void setOutStock(int outStock) {
    SystemCommon.outStock = outStock;
  }
  
  public static List<ZyPojo> getZyMaps() {
    return zyMaps;
  }
  
  public static void setZyMaps(List<ZyPojo> zyMaps) {
    SystemCommon.zyMaps = zyMaps;
  }
  
  public static String getWorkFlowEditorVersion() {
    return workFlowEditorVersion;
  }
  
  public static void setWorkFlowEditorVersion(String workFlowEditorVersion) {
    SystemCommon.workFlowEditorVersion = workFlowEditorVersion;
  }
  
  public static String getBackCheckMust() {
    return backCheckMust;
  }
  
  public static void setBackCheckMust(String backCheckMust) {
    SystemCommon.backCheckMust = backCheckMust;
  }
  
  public static String getBackViewMust() {
    return backViewMust;
  }
  
  public static void setBackViewMust(String backViewMust) {
    SystemCommon.backViewMust = backViewMust;
  }
  
  public static String getSendSMS() {
    return sendSMS;
  }
  
  public static void setSendSMS(String sendSMS) {
    SystemCommon.sendSMS = sendSMS;
  }
  
  public static String getCanSendSMS() {
    return canSendSMS;
  }
  
  public static void setCanSendSMS(String canSendSMS) {
    SystemCommon.canSendSMS = canSendSMS;
  }
  
  public static String getIsdm() {
    return isdm;
  }
  
  public static void setIsdm(String isdm) {
    SystemCommon.isdm = isdm;
  }
  
  public static String getLogCount() {
    return logCount;
  }
  
  public static void setLogCount(String logCount) {
    SystemCommon.logCount = logCount;
  }
  
  public static String getUseAutoSynchronous() {
    return useAutoSynchronous;
  }
  
  public static void setUseAutoSynchronous(String useAutoSynchronous) {
    SystemCommon.useAutoSynchronous = useAutoSynchronous;
  }
  
  public static String getLoginField() {
    return loginField;
  }
  
  public static void setLoginField(String loginField) {
    SystemCommon.loginField = loginField;
  }
  
  public static int getCooperateUserNum() {
    return cooperateUserNum;
  }
  
  public static void setCooperateUserNum(int cooperateUserNum) {
    SystemCommon.cooperateUserNum = cooperateUserNum;
  }
  
  public static String getBackComment() {
    return backComment;
  }
  
  public static void setBackComment(String backComment) {
    SystemCommon.backComment = backComment;
  }
  
  public static String getLxOpenTimer() {
    return lxOpenTimer;
  }
  
  public static void setLxOpenTimer(String lxOpenTimer) {
    SystemCommon.lxOpenTimer = lxOpenTimer;
  }
  
  public static String getLxDatabaseName() {
    return lxDatabaseName;
  }
  
  public static void setLxDatabaseName(String lxDatabaseName) {
    SystemCommon.lxDatabaseName = lxDatabaseName;
  }
  
  public static String getLxIntervalTime() {
    return lxIntervalTime;
  }
  
  public static void setLxIntervalTime(String lxIntervalTime) {
    SystemCommon.lxIntervalTime = lxIntervalTime;
  }
  
  public static String getShowDeleteOnDone() {
    return showDeleteOnDone;
  }
  
  public static void setShowDeleteOnDone(String showDeleteOnDone) {
    SystemCommon.showDeleteOnDone = showDeleteOnDone;
  }
  
  public static String getShowReSubmitOnDone() {
    return showReSubmitOnDone;
  }
  
  public static void setShowReSubmitOnDone(String showReSubmitOnDone) {
    SystemCommon.showReSubmitOnDone = showReSubmitOnDone;
  }
  
  public static String getMeetingApply() {
    return meetingApply;
  }
  
  public static void setMeetingApply(String meetingApply) {
    SystemCommon.meetingApply = meetingApply;
  }
  
  public static String getMeetingApprove() {
    return meetingApprove;
  }
  
  public static void setMeetingApprove(String meetingApprove) {
    SystemCommon.meetingApprove = meetingApprove;
  }
  
  public static String getYunshipeiName() {
    return yunshipeiName;
  }
  
  public static void setYunshipeiName(String yunshipeiName) {
    SystemCommon.yunshipeiName = yunshipeiName;
  }
  
  public static String getIsSsdz() {
    FileInputStream configFileInputStream = null;
    String isSsdz = "0";
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("isSsdz");
      isSsdz = node.getAttributeValue("value");
    } catch (Exception ex) {
      isSsdz = "0";
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return isSsdz;
  }
  
  public static void setIsSsdz(String isSsdz) {
    SystemCommon.isSsdz = isSsdz;
  }
  
  private static String delDataWhenDelFLow = "0";
  
  public static String getDelDataWhenDelFLow() {
    FileInputStream configFileInputStream = null;
    String delDataWhenDelFLow = "0";
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("delDataWhenDelFLow");
      delDataWhenDelFLow = node.getAttributeValue("value");
    } catch (Exception ex) {
      delDataWhenDelFLow = "0";
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return delDataWhenDelFLow;
  }
  
  public static void setDelDataWhenDelFLow(String delDataWhenDelFLow) {
    SystemCommon.delDataWhenDelFLow = delDataWhenDelFLow;
  }
  
  public static int getAllAttachEdit() {
    return allAttachEdit;
  }
  
  public static void setAllAttachEdit(int allAttachEdit) {
    SystemCommon.allAttachEdit = allAttachEdit;
  }
  
  public static int getSameNodeAndApproval() {
    return sameNodeAndApproval;
  }
  
  public static void setSameNodeAndApproval(int sameNodeAndApproval) {
    SystemCommon.sameNodeAndApproval = sameNodeAndApproval;
  }
  
  public static String getSubTableScrollbar() {
    return subTableScrollbar;
  }
  
  public static void setSubTableScrollbar(String subTableScrollbar) {
    SystemCommon.subTableScrollbar = subTableScrollbar;
  }
  
  public static int getSubTableScrollbarBeginCnt() {
    return subTableScrollbarBeginCnt;
  }
  
  public static void setSubTableScrollbarBeginCnt(int subTableScrollbarBeginCnt) {
    SystemCommon.subTableScrollbarBeginCnt = subTableScrollbarBeginCnt;
  }
  
  public static String getMessageForDocShow() {
    return messageForDocShow;
  }
  
  public static void setMessageForDocShow(String messageForDocShow) {
    SystemCommon.messageForDocShow = messageForDocShow;
  }
  
  public static String getPasswordEncryption() {
    return passwordEncryption;
  }
  
  public static void setPasswordEncryption(String passwordEncryption) {
    SystemCommon.passwordEncryption = passwordEncryption;
  }
  
  public static String getShowCompleteTime() {
    return showCompleteTime;
  }
  
  public static void setShowCompleteTime(String showCompleteTime) {
    SystemCommon.showCompleteTime = showCompleteTime;
  }
  
  public static String getReSubmitOnDoneUseOldNumber() {
    return reSubmitOnDoneUseOldNumber;
  }
  
  public static void setReSubmitOnDoneUseOldNumber(String reSubmitOnDoneUseOldNumber) {
    SystemCommon.reSubmitOnDoneUseOldNumber = reSubmitOnDoneUseOldNumber;
  }
  
  public static String getReSubmitOnBackUseOldNumber() {
    return reSubmitOnBackUseOldNumber;
  }
  
  public static void setReSubmitOnBackUseOldNumber(String reSubmitOnBackUseOldNumber) {
    SystemCommon.reSubmitOnBackUseOldNumber = reSubmitOnBackUseOldNumber;
  }
  
  public static String getDocShowDbrName() {
    return docShowDbrName;
  }
  
  public static void setDocShowDbrName(String docShowDbrName) {
    SystemCommon.docShowDbrName = docShowDbrName;
  }
  
  public static String getHqzdUpdata() {
    return hqzdUpdata;
  }
  
  public static void setHqzdUpdata(String hqzdUpdata) {
    SystemCommon.hqzdUpdata = hqzdUpdata;
  }
  
  public static String getFujianBatchDownload() {
    return fujianBatchDownload;
  }
  
  public static void setFujianBatchDownload(String fujianBatchDownload) {
    SystemCommon.fujianBatchDownload = fujianBatchDownload;
  }
  
  public static String getMobileOrWechatFujianShow() {
    return mobileOrWechatFujianShow;
  }
  
  public static void setMobileOrWechatFujianShow(String mobileOrWechatFujianShow) {
    SystemCommon.mobileOrWechatFujianShow = mobileOrWechatFujianShow;
  }
}
