package com.js.oa.form;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.info.isodoc.po.IsoCommentPO;
import com.js.oa.info.isodoc.po.IsoDeallogPO;
import com.js.oa.info.isodoc.service.IsoDocBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.oa.search.client.SearchService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.DeleteFile;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class InformationWorkFlow {
  public static String reprocess;
  
  public Long save(HttpServletRequest httpServletRequest) {
    String informationVersion = (httpServletRequest.getParameter("informationVersion") == null) ? "" : httpServletRequest.getParameter("informationVersion").toString();
    String oldisoDealCategory = (httpServletRequest.getParameter("oldisoDealCategory") == null) ? "" : httpServletRequest.getParameter("oldisoDealCategory").toString();
    reprocess = httpServletRequest.getParameter("reProcess");
    if (httpServletRequest.getParameter("resubmitWorkId") != null)
      if (httpServletRequest.getParameter("isISO") != null && 
        httpServletRequest.getParameter("isISO").toString().equals("1")) {
        Long processId = Long.valueOf(httpServletRequest.getParameter(
              "processId"));
        Long tableId = Long.valueOf(httpServletRequest.getParameter(
              "tableId"));
        Long recordId = Long.valueOf(httpServletRequest.getParameter(
              "recordId"));
        dealIsoComment((String)recordId, (String)tableId, (String)processId, 
            informationVersion, oldisoDealCategory);
        dealIsolog((String)recordId, (String)tableId, (String)processId, 
            informationVersion, oldisoDealCategory);
        WorkFlowButtonBD wBD = new WorkFlowButtonBD();
        WorkVO wVO = new WorkVO();
        wVO.setProcessId(processId);
        wVO.setTableId(tableId);
        wVO.setRecordId(recordId);
        wBD.deleteWork(wVO);
        update(httpServletRequest);
        return recordId;
      }  
    if (httpServletRequest.getParameter("reProcess") != null && !httpServletRequest.getParameter("reProcess").toString().equals("") && httpServletRequest.getParameter("informationId") != null && !httpServletRequest.getParameter("informationId").toString().equals("")) {
      Long processId = Long.valueOf(httpServletRequest.getParameter("processId"));
      Long tableId = Long.valueOf(httpServletRequest.getParameter("tableId"));
      Long recordId = Long.valueOf(httpServletRequest.getParameter("recordId"));
      if (httpServletRequest.getParameter("isISO") != null && 
        httpServletRequest.getParameter("isISO").toString().equals("1")) {
        dealIsoComment((String)recordId, (String)tableId, (String)processId, informationVersion, oldisoDealCategory);
        dealIsolog((String)recordId, (String)tableId, (String)processId, informationVersion, oldisoDealCategory);
      } 
      WorkFlowButtonBD wBD = new WorkFlowButtonBD();
      WorkVO wVO = new WorkVO();
      wVO.setProcessId(processId);
      wVO.setTableId(tableId);
      wVO.setRecordId(recordId);
      wBD.deleteWork(wVO);
      update(httpServletRequest);
      return recordId;
    } 
    InformationBD informationBD = new InformationBD();
    String channelType = httpServletRequest.getParameter("channelType");
    String channelId = httpServletRequest.getParameter("channelId");
    String channelName = httpServletRequest.getParameter("channelName");
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("channelId", channelId);
    httpServletRequest.setAttribute("channelName", channelName);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String corpId = httpSession.getAttribute("corpId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String[] para = { userId, userName, orgId, orgName, orgIdString };
    Long informationId = null;
    InformationPO informationPO = setPO(httpServletRequest);
    if (httpServletRequest.getParameter("reProcess") == null)
      informationPO.setInformationModifyTime(null); 
    informationPO.setInformationStatus(1);
    informationPO.setIssueOrgIdString(orgIdString);
    informationPO.setInformationIssueOrgId(orgId);
    informationId = informationBD.add(informationPO, para, 
        httpServletRequest.getParameterValues("associateInfo"), 
        httpServletRequest.getParameterValues("infoPicName"), 
        httpServletRequest.getParameterValues("infoPicSaveName"), 
        httpServletRequest.getParameterValues("infoAppendName"), 
        httpServletRequest.getParameterValues("infoAppendSaveName"), 
        informationPO.getDomainId().toString(), corpId);
    CustomDesktopBD cdbd = new CustomDesktopBD();
    String moduleType = "information";
    String moduleSubId = String.valueOf(httpServletRequest.getParameter("channelId"));
    List<Object[]> objectlist = (new CustomDesktopBD())
      .getDefinedRelationObject(moduleType, moduleSubId);
    for (int i = 0; i < objectlist.size(); i++) {
      Object[] objs = objectlist.get(i);
      String[] relationId = httpServletRequest.getParameterValues(objs[2] + "RelationId");
      String[] relationName = httpServletRequest.getParameterValues(objs[2] + "RelationName");
      String[] relationHref = httpServletRequest.getParameterValues(objs[2] + "RelationHref");
      if (relationId != null && relationName != null)
        cdbd.saveRelationInfo(moduleType, moduleSubId, 
            informationId.toString(), 
            objs[2].toString(), relationId, 
            relationName, relationHref, informationPO.getDomainId().toString()); 
    } 
    return informationId;
  }
  
  public void complete(HttpServletRequest httpServletRequest) {
    String informationId = "";
    informationId = httpServletRequest.getParameter("recordId");
    IsoDocBD bd = new IsoDocBD();
    InformationBD informationBD = new InformationBD();
    String docDestroy = (httpServletRequest.getParameter("docDestroy") == null) ? 
      "0" : 
      httpServletRequest.getParameter("docDestroy").toString();
    String isoDealCategory = (httpServletRequest.getParameter("isoDealCategory") == null) ? 
      "0" : 
      httpServletRequest.getParameter("isoDealCategory")
      .toString();
    if (docDestroy.equals("1")) {
      bd.setInformationStatus(informationId, "0", "2");
    } else {
      informationBD.informationStatus(informationId);
    } 
    if (httpServletRequest.getParameter("isISO") != null && 
      httpServletRequest.getParameter("isISO").equals("1") && 
      !"1".equals(docDestroy) && !"3".equals(isoDealCategory) && 
      !"4".equals(isoDealCategory))
      bd.updateBigVersion(informationId); 
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && informationId != null && ifActiveUpdateDelete != null && !"".equals(informationId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.addIndex(informationId.toString(), "oa_information");
    } 
    if (httpServletRequest.getParameter("remindUser") != null && "0".equals(httpServletRequest.getParameter("remindUser"))) {
      String url = "/jsoa/info/view_detail.jsp?informationId=" + informationId;
      String[] messageInfo = informationBD.getMessageTitleAndUsers(informationId, reprocess);
      if (messageInfo[0].equals("-1") && SystemCommon.getMultiDepart() == 1) {
        messageInfo[0] = StaticParam.getEmpIdsByOrgId((String)httpServletRequest.getSession().getAttribute("corpId"));
        if (messageInfo[0].length() > 0)
          messageInfo[0] = messageInfo[0].substring(0, messageInfo[0].length() - 1); 
      } 
      RemindUtil.sendMessageToUsers(messageInfo[1], url, messageInfo[0], "Info", new Date(), new Date("2050/1/1"));
    } 
  }
  
  public void update(HttpServletRequest httpServletRequest) {
    String forbidCopy;
    ChannelBD channelBD = new ChannelBD();
    String informationId = "";
    informationId = httpServletRequest.getParameter("recordId");
    InformationBD informationBD = new InformationBD();
    String channelType = httpServletRequest.getParameter("channelType");
    String channelId = httpServletRequest.getParameter("channelId");
    String channelName = httpServletRequest.getParameter("channelName");
    String documentNo = httpServletRequest.getParameter("documentNo");
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("channelId", channelId);
    httpServletRequest.setAttribute("channelName", channelName);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    httpServletRequest.setAttribute("channelType", channelType);
    String informationTitle = httpServletRequest.getParameter("informationTitle");
    String titleColor = (httpServletRequest.getParameter("displayColor") == null) ? "0" : httpServletRequest.getParameter("displayColor");
    String informationSubTitle = httpServletRequest.getParameter("informationSubTitle");
    String informationSummary = httpServletRequest.getParameter("informationSummary");
    String informationContent = httpServletRequest.getParameter("informationContent");
    String informationKey = httpServletRequest.getParameter("informationKey");
    String informationVaildType = httpServletRequest.getParameter("informationVaildType");
    String validBeginTime = httpServletRequest.getParameter("validBeginTime");
    String validEndTime = httpServletRequest.getParameter("validEndTime");
    String informationAuthor = httpServletRequest.getParameter("informationAuthor");
    String informationReaderName = httpServletRequest.getParameter("informationReaderName");
    String informationReader = httpServletRequest.getParameter("informationReader");
    String informationReaderOrg = httpServletRequest.getParameter("informationReaderOrg");
    String informationReaderGroup = httpServletRequest.getParameter("informationReaderGroup");
    String informationHead = httpServletRequest.getParameter("informationHead");
    String mustRead = httpServletRequest.getParameter("mustRead");
    String fileLinkContent = httpServletRequest.getParameter("fileLinkContent");
    String informationHeadFile = "", informationSeal = "", informationMark = "", infoRedIssueOrg = "", infoRedIssueTime = "", informationHeadId = "0", informationSealId = "0";
    if (informationHead != null && informationHead.equals("1")) {
      informationHeadId = httpServletRequest.getParameter("informationHeadFile");
      informationHeadFile = getHeadFile(httpServletRequest.getParameter("informationHeadFile"));
      informationSealId = httpServletRequest.getParameter("informationSeal");
      informationSeal = getSealFile(httpServletRequest.getParameter("informationSeal"));
      informationMark = httpServletRequest.getParameter("informationMark");
      infoRedIssueOrg = httpServletRequest.getParameter("infoRedIssueOrg");
      String[] tmp = httpServletRequest.getParameter("infoRedIssueTime").split("/");
      infoRedIssueTime = String.valueOf(tmp[0]) + "年" + tmp[1] + "月" + tmp[2] + "日";
    } 
    if (httpServletRequest.getParameter("flag") != null && httpServletRequest.getParameter("flag").toString().equals("complete")) {
      Date date1 = new Date();
      infoRedIssueTime = String.valueOf(date1.getYear()) + "年" + date1.getMonth() + "月" + date1.getDate() + "日";
    } 
    String flag = httpServletRequest.getParameter("flag");
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String displayTitle = "1";
    if (httpServletRequest.getParameter("displayTitle") != null)
      displayTitle = httpServletRequest.getParameter("displayTitle"); 
    String displayImage = "1";
    if (httpServletRequest.getParameter("displayImage") != null)
      displayImage = httpServletRequest.getParameter("displayImage"); 
    StringBuffer otherChannel = new StringBuffer();
    if (httpServletRequest.getParameterValues("otherChannel") != null) {
      String[] otherTmp = httpServletRequest.getParameterValues("otherChannel");
      for (int i = 0; i < otherTmp.length; i++)
        otherChannel.append("," + otherTmp[i] + ","); 
    } 
    String infoView = httpServletRequest.getParameter("isAllow");
    if (infoView == null)
      infoView = "0"; 
    String isRedo = "0";
    if (httpServletRequest.getParameter("reProcess") != null && httpServletRequest.getParameter("reProcess").toString().equals("1") && httpServletRequest.getParameter("informationId") != null && !httpServletRequest.getParameter("informationId").toString().equals("")) {
      informationId = httpServletRequest.getParameter("informationId").toString();
      isRedo = "1";
    } 
    if (httpServletRequest.getParameter("resubmitWorkId") != null && !httpServletRequest.getParameter("resubmitWorkId").toString().equals(""))
      isRedo = "1"; 
    if (httpServletRequest.getParameter("noProcess") != null && httpServletRequest.getParameter("noProcess").toString().equals("1"))
      isRedo = "0"; 
    String afficeHistoryDate = "-1";
    Date Historydate = new Date();
    if (httpServletRequest.getParameter("afficeHistoryDate") != null && !httpServletRequest.getParameter("afficeHistoryDate").toString().trim().equals("")) {
      afficeHistoryDate = httpServletRequest.getParameter("afficeHistoryDate");
      Date bgDate = new Date(validBeginTime);
      Calendar cd = Calendar.getInstance();
      cd.setTime(bgDate);
      int addtime = Integer.parseInt(afficeHistoryDate);
      cd.add(5, addtime);
      Historydate = cd.getTime();
    } 
    String informationOrISODoc = "0";
    String isoDocStatus = "0";
    String isoOldInfoId = "";
    String isoSecretStatus = "0";
    String isChange = (httpServletRequest.getParameter("isChange") == null) ? "1" : 
      httpServletRequest.getParameter("isChange").toString();
    if (httpServletRequest.getParameter("informationVersion") != null && 
      httpServletRequest.getParameter("informationVersion").toString().equals("0.1"))
      isChange = "0"; 
    String isoDealCategory = (httpServletRequest.getParameter("isoDealCategory") == null) ? "" : httpServletRequest.getParameter("isoDealCategory").toString();
    if (httpServletRequest.getParameter("action") != null && 
      httpServletRequest
      .getParameter("action").toString().equals("updateClose"))
      isoDealCategory = (httpServletRequest.getParameter("oldisoDealCategory") == null) ? 
        "" : 
        httpServletRequest.getParameter("oldisoDealCategory")
        .toString(); 
    String isoApplyName = (httpServletRequest.getParameter("isoApplyName") == null) ? 
      "" : 
      httpServletRequest.getParameter("isoApplyName").toString();
    String isoApplyId = (httpServletRequest.getParameter("isoApplyId") == null) ? 
      "" : 
      httpServletRequest.getParameter("isoApplyId").toString();
    String isoReceiveName = (httpServletRequest.getParameter("isoReceiveName") == null) ? 
      "" : 
      httpServletRequest.getParameter("isoReceiveName")
      .toString();
    String isoReceiveId = (httpServletRequest.getParameter("isoReceiveId") == null) ? 
      "" : 
      httpServletRequest.getParameter("isoReceiveId").toString();
    String isoModifyReason = (httpServletRequest.getParameter("isoModifyReason") == null) ? 
      "" : 
      httpServletRequest.getParameter("isoModifyReason")
      .toString();
    String isoAmendmentPage = (httpServletRequest.getParameter(
        "isoAmendmentPage") == null) ? "" : 
      httpServletRequest.getParameter("isoAmendmentPage")
      .toString();
    String isoModifyVersion = (httpServletRequest.getParameter(
        "isoModifyVersion") == null) ? "" : 
      httpServletRequest.getParameter("isoModifyVersion")
      .toString();
    String modifyMen = "";
    String modifyOrg = "";
    if (httpServletRequest.getParameter("reProcess") != null) {
      modifyMen = httpSession.getAttribute("userName").toString();
      modifyOrg = httpSession.getAttribute("orgName").toString();
    } 
    if (httpServletRequest.getParameter("isISO") != null && 
      httpServletRequest.getParameter("isISO").toString().equals("1")) {
      informationOrISODoc = "1";
      isoDocStatus = "0";
      isoOldInfoId = "";
      if (httpServletRequest.getParameter("isoSecretStatus") != null && 
        
        !httpServletRequest.getParameter("isoSecretStatus").toString().equals(""))
        isoSecretStatus = httpServletRequest.getParameter("isoSecretStatus"); 
      if (httpServletRequest.getParameter("docDestroy") != null && 
        !httpServletRequest.getParameter("docDestroy").toString().equals(""))
        isoDocStatus = httpServletRequest.getParameter("docDestroy"); 
      String isBigChange = (httpServletRequest.getParameter("isBigChange") == null) ? "0" : httpServletRequest.getParameter("isBigChange").toString();
      if (isChange.equals("1") || isBigChange.equals("1")) {
        informationBD.saveHistory(informationId);
        if (httpServletRequest.getParameter("reProcess") == null || 
          httpServletRequest.getParameter("reProcess").toString()
          .equals("")) {
          modifyMen = "";
          modifyOrg = "";
        } 
      } else {
        modifyMen = "";
        modifyOrg = "";
      } 
    } 
    if ("1".equals(httpServletRequest.getParameter("resubmitFirstUpdate"))) {
      if (httpServletRequest.getParameter("forbidCopy") == null) {
        forbidCopy = "1";
      } else {
        forbidCopy = "0";
      } 
    } else if (httpServletRequest.getParameter("forbidCopy") == null) {
      forbidCopy = "1";
    } else {
      forbidCopy = httpServletRequest.getParameter("forbidCopy");
    } 
    String[] para = { 
        informationTitle, 
        informationSubTitle, 
        informationSummary, 
        informationKey, 
        informationContent, 
        userId, 
        userName, 
        orgName, 
        informationReaderName, 
        informationReader, 
        informationReaderOrg, 
        informationReaderGroup, 
        informationVaildType, 
        validBeginTime, 
        validEndTime, 
        informationHead, 
        informationHeadFile, 
        informationSeal, 
        informationMark, 
        infoRedIssueOrg, 
        infoRedIssueTime, 
        informationHeadId, 
        informationSealId, 
        httpServletRequest.getParameter("transmitToWebsite"), 
        forbidCopy, 
        httpServletRequest.getParameter("orderCode"), 
        displayTitle, 
        otherChannel.toString(), 
        informationAuthor, 
        httpServletRequest.getParameter("issueTime"), 
        titleColor, 
        httpServletRequest.getParameter("channelId"), 
        httpServletRequest.getParameter("mustRead"), 
        httpServletRequest.getParameter("comeFrom"), 
        httpServletRequest.getParameter("isConf"), 
        httpServletRequest.getParameter("documentNo"), 
        httpServletRequest.getParameter("documentEditor"), 
        httpServletRequest.getParameter("documentTypeFinal"), 
        isRedo, 
        afficeHistoryDate, 
        channelId, 
        displayImage, 
        informationOrISODoc, 
        isoDocStatus, 
        isoOldInfoId, 
        isoSecretStatus, 
        isoDealCategory, 
        isoApplyName, 
        isoApplyId, 
        isoReceiveName, 
        isoReceiveId, 
        isoModifyReason, 
        isoAmendmentPage, 
        isoModifyVersion, 
        modifyMen, 
        modifyOrg, 
        isChange, 
        infoView, 
        reprocess };
    channelBD.update(informationId, 
        para, 
        httpServletRequest.getParameterValues("associateInfo"), 
        httpServletRequest.getParameterValues("infoPicName"), 
        httpServletRequest.getParameterValues("infoPicSaveName"), 
        httpServletRequest.getParameterValues("infoAppendName"), 
        httpServletRequest.getParameterValues("infoAppendSaveName"), 
        Historydate);
  }
  
  public List getHead(String userId, String orgId) {
    return null;
  }
  
  public List getSeal() {
    return null;
  }
  
  public String getHeadFile(String headId) {
    return null;
  }
  
  public String getSealFile(String sealId) {
    return null;
  }
  
  private InformationPO setPO(HttpServletRequest httpServletRequest) {
    String forbidCopy;
    InformationPO informationPO = new InformationPO();
    HttpSession httpSession = httpServletRequest.getSession(true);
    informationPO.setMustRead(Integer.valueOf(httpServletRequest.getParameter(
            "mustRead")));
    informationPO.setInformationTitle(httpServletRequest.getParameter(
          "informationTitle"));
    if (httpServletRequest.getParameter("displayColor") == null) {
      informationPO.setTitleColor(new Integer(0));
    } else {
      informationPO.setTitleColor(new Integer(httpServletRequest.getParameter("displayColor").toString()));
    } 
    informationPO.setDossierStatus(Integer.valueOf("0"));
    informationPO.setInformationSubTitle(httpServletRequest.getParameter(
          "informationSubTitle"));
    informationPO.setInformationSummary(httpServletRequest.getParameter(
          "informationSummary"));
    informationPO.setInformationReader(httpServletRequest.getParameter(
          "informationReader"));
    informationPO.setInformationReaderOrg(httpServletRequest.getParameter(
          "informationReaderOrg"));
    informationPO.setInformationReaderGroup(httpServletRequest.getParameter(
          "informationReaderGroup"));
    informationPO.setInformationReaderName(httpServletRequest.getParameter(
          "informationReaderName"));
    informationPO.setInformationAuthor(httpServletRequest.getParameter(
          "informationAuthor"));
    Calendar now = Calendar.getInstance();
    informationPO.setModifyEmp("");
    informationPO.setIsAllow((httpServletRequest.getParameter("isAllow") == null) ? "0" : httpServletRequest.getParameter("isAllow"));
    if (httpServletRequest.getParameter("issueTime") != null) {
      String[] issueTime = httpServletRequest.getParameter("issueTime").split(
          "/");
      now.set(Integer.parseInt(issueTime[0]), 
          Integer.parseInt(issueTime[1]) - 1, 
          Integer.parseInt(issueTime[2]));
    } 
    informationPO.setInformationIssueTime(now.getTime());
    informationPO.setInformationModifyTime(new Date());
    informationPO.setInformationIssuer(httpSession.getAttribute("userName")
        .toString());
    informationPO.setInformationIssueOrg(httpSession.getAttribute("orgName")
        .toString());
    informationPO.setInformationContent(httpServletRequest.getParameter(
          "informationContent"));
    informationPO.setInformationKey(httpServletRequest.getParameter(
          "informationKey"));
    informationPO.setInformationIssuerId(new Long((String)httpSession.getAttribute(
            "userId")));
    informationPO.setInformationVersion("1.0");
    informationPO.setInformationType(httpServletRequest.getParameter(
          "informationType"));
    informationPO.setInformationCommonNum(0);
    informationPO.setReprocess(reprocess);
    if (httpServletRequest.getParameter("forbidCopy") == null) {
      forbidCopy = "1";
    } else {
      forbidCopy = "0";
    } 
    informationPO.setForbidCopy(Integer.parseInt(forbidCopy));
    informationPO.setTransmitToWebsite(Integer.parseInt(httpServletRequest
          .getParameter("transmitToWebsite")));
    if (httpServletRequest.getParameter("orderCode").equals("")) {
      informationPO.setOrderCode("0");
    } else {
      informationPO.setOrderCode(httpServletRequest.getParameter("orderCode"));
    } 
    int displayTitle = 1;
    if (httpServletRequest.getParameter("displayTitle") != null)
      displayTitle = Integer.parseInt(httpServletRequest.getParameter(
            "displayTitle")); 
    informationPO.setDisplayTitle(displayTitle);
    String displayImage = "1";
    if (httpServletRequest.getParameter("displayImage") != null)
      displayImage = httpServletRequest.getParameter("displayImage"); 
    informationPO.setDisplayImage(displayImage);
    String[] otherChannel = httpServletRequest.getParameterValues(
        "otherChannel");
    if (otherChannel != null) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < otherChannel.length; i++)
        sb.append("," + otherChannel[i] + ","); 
      informationPO.setOtherChannel(sb.toString());
    } 
    String informationValidType = "0";
    if (httpServletRequest.getParameter("informationVaildType") != null && 
      !httpServletRequest.getParameter("informationVaildType").equals(""))
      informationValidType = httpServletRequest.getParameter(
          "informationVaildType"); 
    informationPO.setInformationValidType(Integer.parseInt(informationValidType));
    if (informationValidType.equals("1")) {
      informationPO.setValidBeginTime(new Date(httpServletRequest
            .getParameter("validBeginTime")));
      informationPO.setValidEndTime(new Date(httpServletRequest
            .getParameter("validEndTime")));
    } 
    informationPO.setInformationHead(Integer.parseInt(httpServletRequest
          .getParameter("informationHead")));
    if (httpServletRequest.getParameter("informationHead").equals("1")) {
      informationPO.setInformationHeadId(new Long(httpServletRequest.getParameter("informationHeadFile")));
      informationPO.setInformationHeadFile(getHeadFile(httpServletRequest.getParameter("informationHeadFile")));
      informationPO.setInformationSealId(new Long(httpServletRequest.getParameter("informationSeal")));
      informationPO.setInformationSeal(getSealFile(httpServletRequest.getParameter("informationSeal")));
      informationPO.setInformationMark(httpServletRequest.getParameter("informationMark"));
      informationPO.setInfoRedIssueOrg(httpServletRequest.getParameter("infoRedIssueOrg"));
      String[] tmp = httpServletRequest.getParameter("infoRedIssueTime").split("/");
      informationPO.setInfoRedIssueTime(String.valueOf(tmp[0]) + "年" + tmp[1] + "月" + tmp[2] + "日");
    } 
    ChannelBD channelBD = new ChannelBD();
    informationPO.setInformationChannel(channelBD.loadChannel((
          new StringBuilder(String.valueOf(httpServletRequest.getParameter("channelId")))).toString()));
    informationPO.setComeFrom(httpServletRequest.getParameter("comeFrom"));
    if (httpServletRequest.getParameter("isConf") != null) {
      informationPO.setIsConf(Integer.valueOf("1"));
    } else {
      informationPO.setIsConf(Integer.valueOf("0"));
    } 
    if (httpServletRequest.getParameter("documentNo") != null || httpServletRequest.getParameter("documentNo") != "")
      informationPO.setDocumentNo(httpServletRequest.getParameter("documentNo")); 
    if (httpServletRequest.getParameter("documentNo").equals(""))
      informationPO.setDocumentNo("0"); 
    informationPO.setDocumentEditor(httpServletRequest.getParameter("documentEditor"));
    informationPO.setDocumentType(httpServletRequest.getParameter("documentTypeFinal"));
    informationPO.setWordDisplayType(httpServletRequest.getParameter("wordDisplayType"));
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    informationPO.setDomainId(Long.valueOf(domainId));
    if (httpServletRequest.getParameter("afficeHistoryDate") != null && !httpServletRequest.getParameter("afficeHistoryDate").toString().trim().equals("")) {
      informationPO.setAfficeHistoryDate(new Long(httpServletRequest.getParameter("afficeHistoryDate")));
      Date Historydate = new Date();
      if (httpServletRequest.getParameter("afficeHistoryDate") != null && !httpServletRequest.getParameter("afficeHistoryDate").toString().trim().equals("")) {
        String afficeHistoryDate = httpServletRequest.getParameter("afficeHistoryDate");
        Date bgDate = new Date(httpServletRequest.getParameter("validBeginTime"));
        Calendar cd = Calendar.getInstance();
        cd.setTime(bgDate);
        int addtime = Integer.parseInt(afficeHistoryDate);
        cd.add(5, addtime);
        Historydate = cd.getTime();
        informationPO.setAfficheHiTime(Historydate);
      } 
    } else {
      informationPO.setAfficeHistoryDate(new Long("-1"));
    } 
    String informationOrISODoc = "0";
    String isoDocStatus = "0";
    String isoOldInfoId = "";
    String isoSecretStatus = "0";
    if (httpServletRequest.getParameter("isISO") != null && httpServletRequest.getParameter("isISO").toString().equals("1")) {
      informationOrISODoc = "1";
      isoDocStatus = "0";
      isoOldInfoId = "";
      if (httpServletRequest.getParameter("isoSecretStatus") != null && !httpServletRequest.getParameter("isoSecretStatus").toString().equals(""))
        isoSecretStatus = httpServletRequest.getParameter("isoSecretStatus"); 
      informationPO.setInformationVersion("0.1");
    } 
    informationPO.setInformationOrISODoc(informationOrISODoc);
    informationPO.setIsoDocStatus(isoDocStatus);
    informationPO.setIsoOldInfoId(isoOldInfoId);
    informationPO.setIsoSecretStatus(isoSecretStatus);
    if (httpServletRequest.getParameter("isoDealCategory") != null) {
      informationPO.setIsoDealCategory(httpServletRequest.getParameter("isoDealCategory"));
    } else {
      informationPO.setIsoDealCategory("");
    } 
    if (httpServletRequest.getParameter("isoApplyName") != null) {
      informationPO.setIsoApplyName(httpServletRequest.getParameter("isoApplyName"));
    } else {
      informationPO.setIsoApplyName("");
    } 
    if (httpServletRequest.getParameter("isoApplyId") != null) {
      informationPO.setIsoApplyId(httpServletRequest.getParameter("isoApplyId"));
    } else {
      informationPO.setIsoApplyId("");
    } 
    if (httpServletRequest.getParameter("isoReceiveName") != null) {
      informationPO.setIsoReceiveName(httpServletRequest.getParameter("isoReceiveName"));
    } else {
      informationPO.setIsoReceiveName("");
    } 
    if (httpServletRequest.getParameter("isoReceiveId") != null) {
      informationPO.setIsoReceiveId(httpServletRequest.getParameter("isoReceiveId"));
    } else {
      informationPO.setIsoReceiveId("");
    } 
    if (httpServletRequest.getParameter("isoModifyReason") != null) {
      informationPO.setIsoModifyReason(httpServletRequest.getParameter("isoModifyReason"));
    } else {
      informationPO.setIsoModifyReason("");
    } 
    if (httpServletRequest.getParameter("isoAmendmentPage") != null && !httpServletRequest.getParameter("isoAmendmentPage").toString().equals("")) {
      informationPO.setIsoAmendmentPage(httpServletRequest.getParameter("isoAmendmentPage"));
    } else {
      informationPO.setIsoAmendmentPage("");
    } 
    if (httpServletRequest.getParameter("isoModifyVersion") != null && !httpServletRequest.getParameter("isoModifyVersion").toString().equals("")) {
      informationPO.setIsoModifyVersion(httpServletRequest.getParameter("isoModifyVersion"));
    } else {
      informationPO.setIsoModifyVersion("");
    } 
    informationPO.setInforModifyMen("");
    informationPO.setInforModifyOrg("");
    informationPO.setOrderCodeTemp(Integer.valueOf(0));
    return informationPO;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    String informationId = "";
    informationId = httpServletRequest.getParameter("recordId");
    InformationBD informationBD = new InformationBD();
    String isISO = (httpServletRequest.getParameter("isISO") == null) ? "0" : httpServletRequest.getParameter("isISO");
    if (isISO.equals("1"))
      informationBD.setInformationStatus(informationId, "0"); 
    return Long.valueOf(informationId);
  }
  
  public Long delete(HttpServletRequest httpServletRequest) {
    InformationBD informationBD = new InformationBD();
    String id = httpServletRequest.getParameter("recordId");
    String channelId = httpServletRequest.getParameter("channelId");
    List<String> list = informationBD.singleDelete(channelId, id);
    if (list != null && list.size() > 0) {
      String path = httpServletRequest.getRealPath("/");
      DeleteFile deleteFile = new DeleteFile();
      for (int i = 0; i < list.size(); i++) {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(1));
        deleteFile.delete(String.valueOf(path) + "/upload/" + year + "/information", 
            list.get(i));
      } 
    } 
    return Long.valueOf(id);
  }
  
  public Long delete2(HttpServletRequest httpServletRequest) {
    InformationBD informationBD = new InformationBD();
    String id = httpServletRequest.getParameter("recordId");
    String channelId = httpServletRequest.getParameter("channelId");
    List list = informationBD.singleDelete(channelId, id);
    return Long.valueOf(id);
  }
  
  private void dealIsoComment(String isoId, String tableId, String processId, String informationVersion, String oldisoDealCategory) {
    IsoDocBD bd = new IsoDocBD();
    IsoCommentPO po = new IsoCommentPO();
    int a = Integer.parseInt(informationVersion.substring(0, 
          informationVersion.indexOf(".")));
    List<Object[]> list = bd.getCommentList(isoId, tableId, processId);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] commentObj = list.get(i);
        String empName = "";
        if (commentObj[1] != null && 
          !commentObj[1].toString().equals("") && 
          !commentObj[1].toString().equals("null"))
          Object object = commentObj[1]; 
        if (commentObj[4] != null && 
          !commentObj[4].toString().equals("") && 
          !commentObj[4].toString().equals("null"))
          empName = " " + commentObj[4]; 
        po.setInformationId(new Long(isoId));
        po.setDealComment((String)commentObj[0]);
        po.setDealDate((String)commentObj[2]);
        po.setDealEmpName(empName);
        po.setAcName((String)commentObj[5]);
        po.setInfodealType(oldisoDealCategory);
        po.setInforversion(String.valueOf(a) + ".0");
        bd.saveIsoCommentPO(po);
      }  
  }
  
  private void dealIsolog(String isoId, String tableId, String processId, String informationVersion, String oldisoDealCategory) {
    IsoDocBD bd = new IsoDocBD();
    IsoDeallogPO po = new IsoDeallogPO();
    int a = Integer.parseInt(informationVersion.substring(0, informationVersion.indexOf(".")));
    String b = informationVersion.substring(informationVersion.indexOf(".") + 1, informationVersion.length());
    List<Object[]> list = (new WorkFlowButtonBD()).getAllDealWithLog(processId, tableId, isoId);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] logObj = list.get(i);
        po.setStartUser((String)logObj[0]);
        po.setDealDate((String)logObj[1]);
        po.setDealType((String)logObj[2]);
        po.setEndUser((String)logObj[3]);
        po.setInfodealType(oldisoDealCategory);
        po.setInforversion(String.valueOf(a) + ".0");
        bd.saveIsoDeallogPO(po);
      }  
  }
}
