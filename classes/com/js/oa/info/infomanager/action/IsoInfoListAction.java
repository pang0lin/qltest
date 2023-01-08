package com.js.oa.info.infomanager.action;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IsoInfoListAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InformationActionForm informationActionForm = 
      (InformationActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String type = "all";
    String userDefine = httpServletRequest.getParameter("userDefine");
    String informationId = httpServletRequest.getParameter("informationId");
    String search = httpServletRequest.getParameter("search");
    String userChannelName = httpServletRequest.getParameter("userChannelName");
    String channelType = httpServletRequest.getParameter("channelType");
    String depart = httpServletRequest.getParameter("depart");
    String setAsso = (httpServletRequest.getParameter("setAsso") == null) ? "" : httpServletRequest.getParameter("setAsso");
    String action = httpServletRequest.getParameter("action");
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("type", type);
    httpServletRequest.setAttribute("depart", depart);
    httpServletRequest.setAttribute("setAsso", setAsso);
    String isoViewType = (httpServletRequest.getParameter("isoViewType") == null) ? "0" : httpServletRequest.getParameter("isoViewType");
    httpServletRequest.setAttribute("isoViewType", isoViewType);
    if (type.equals("all")) {
      httpServletRequest.setAttribute("infoType", "所有信息");
    } else {
      httpServletRequest.setAttribute("infoType", "精华信息");
    } 
    if (action != null && action.equals("removeCommend")) {
      InformationBD bd = new InformationBD();
      bd.removeCommend(informationId);
    } 
    ChannelBD channelBD = new ChannelBD();
    List list = channelBD.getAllIsoChannel("");
    httpServletRequest.setAttribute("channelList", list);
    Date now = new Date();
    String nowString = now.toLocaleString();
    nowString = nowString.substring(0, nowString.indexOf(" "));
    InformationBD informationBD1 = new InformationBD();
    List<String> groupList = new ArrayList();
    groupList = informationBD1.getAllGroupByUserId(userId);
    httpServletRequest.setAttribute("groupList", groupList);
    if ("staUpdate".equals(action)) {
      String flag = (httpServletRequest.getParameter("flag") == null) ? "" : httpServletRequest.getParameter("flag");
      String str1 = 
        " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationAuthor, aaa.informationVersion, aaa.informationIssueTime,  aaa.informationSummary, aaa.informationHead, aaa.informationType,  aaa.validBeginTime, aaa.validEndTime, aaa.informationCommonNum,  aaa.informationIssuer,aaa.informationValidType,aaa.titleColor, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime,bbb.channelNeedCheckup,aaa.isoSecretStatus,aaa.informationOrISODoc,aaa.isoDocStatus,aaa.isoOldInfoId,aaa.informationIssuerId,aaa.isoAmendmentPage,aaa.inforModifyMen,aaa.inforModifyOrg,aaa.isoDealCategory,aaa.informationIssueOrg ";
      String str2 = 
        " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
      String beginUT = nowString;
      String endUT = nowString;
      if (httpServletRequest.getParameter("searchBeginDate") != null && !httpServletRequest.getParameter("searchBeginDate").toString().equals("")) {
        beginUT = httpServletRequest.getParameter("searchBeginDate");
        endUT = httpServletRequest.getParameter("searchEndDate");
        httpServletRequest.setAttribute("searchBeginDate", beginUT);
        httpServletRequest.setAttribute("searchEndDate", endUT);
      } 
      String str3 = " where aaa.domainId=" + domainId + " and  bbb.afficheChannelStatus='2' ";
      str3 = String.valueOf(str3) + " and aaa.isoDocStatus<>'3' ";
      String str4 = SystemCommon.getDatabaseType();
      if (str4.indexOf("mysql") >= 0) {
        str3 = String.valueOf(str3) + 
          " and ( aaa.informationValidType = 0 or '" + 
          nowString + 
          "' between aaa.validBeginTime and aaa.validEndTime )  ";
        str3 = String.valueOf(str3) + " and (aaa.informationModifyTime  between '" + beginUT + "' and '" + endUT + " 23:59:59' ";
        str3 = String.valueOf(str3) + " or  (select count(hh) from com.js.oa.info.infomanager.po.InformationHistoryPO hh where aaa.informationId=hh.information and  hh.historyTime  between '" + beginUT + "' and '" + endUT + " 23:59:59' )>1 )";
      } else {
        str3 = String.valueOf(str3) + "  and ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
          nowString + 
          "','S') between aaa.validBeginTime and aaa.validEndTime )  ";
        str3 = String.valueOf(str3) + " and (aaa.informationModifyTime  between JSDB.FN_STRTODATE('" + 
          beginUT + "','S') and JSDB.FN_STRTODATE('" + endUT + " 23:59:59','')";
        str3 = String.valueOf(str3) + " or  (select count(hh.historyId) from com.js.oa.info.infomanager.po.InformationHistoryPO hh join hh.information ddd where  aaa.informationId=ddd.informationId and hh.historyTime  between JSDB.FN_STRTODATE('" + 
          beginUT + "','S')  and JSDB.FN_STRTODATE('" + endUT + " 23:59:59','') )>1 )";
      } 
      str3 = String.valueOf(str3) + " and aaa.informationVersion not like '0.%' ";
      if ("export".equals(flag)) {
        list3(httpServletRequest, str1, str2, str3);
        return actionMapping.findForward("staExport");
      } 
      list2(httpServletRequest, str1, str2, str3);
      return actionMapping.findForward("staUpdate");
    } 
    String viewSQL = 
      " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationAuthor, aaa.informationVersion, aaa.informationIssueTime,  aaa.informationSummary, aaa.informationHead, aaa.informationType,  aaa.validBeginTime, aaa.validEndTime, aaa.informationCommonNum,  aaa.informationIssuer,aaa.informationValidType,aaa.titleColor, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime,bbb.channelNeedCheckup,aaa.isoSecretStatus,aaa.informationOrISODoc,aaa.isoDocStatus,aaa.isoOldInfoId,aaa.informationIssuerId,aaa.informationReader,aaa.informationReaderOrg,aaa.informationReaderGroup,aaa.informationStatus,aaa.twoUserId ";
    String fromSQL = 
      " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
    String whereSQL = " where aaa.domainId=" + domainId + " and  bbb.afficheChannelStatus='2' ";
    String databaseType = SystemCommon.getDatabaseType();
    String tag = "success";
    if (isoViewType.equals("0"))
      if (databaseType.indexOf("mysql") >= 0) {
        whereSQL = String.valueOf(whereSQL) + 
          " and ( aaa.informationStatus=0 or aaa.isoSecretStatus<>'0') and ( aaa.informationValidType = 0 or '" + 
          nowString + 
          "' between aaa.validBeginTime and aaa.validEndTime ) and aaa.isoDocStatus<>'2' and aaa.isoDocStatus<>'3' ";
      } else {
        whereSQL = String.valueOf(whereSQL) + "  and ( aaa.informationStatus=0 or aaa.isoSecretStatus<>'0') and ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
          nowString + 
          "','S') between aaa.validBeginTime and aaa.validEndTime ) and aaa.isoDocStatus<>'2' and aaa.isoDocStatus<>'3'";
      }  
    if (isoViewType.equals("1"))
      if (databaseType.indexOf("mysql") >= 0) {
        whereSQL = String.valueOf(whereSQL) + " and ( aaa.informationStatus=0 or aaa.isoSecretStatus<>'0') and ( aaa.informationValidType <> 0 and  '" + 
          nowString + 
          "' > aaa.validEndTime )  and aaa.isoDocStatus<>'2' and aaa.isoDocStatus<>'3' ";
      } else {
        whereSQL = String.valueOf(whereSQL) + "  and ( aaa.informationStatus=0 or aaa.isoSecretStatus<>'0') and (aaa.informationValidType <> 0  and  JSDB.FN_STRTODATE('" + 
          nowString + 
          "','S') > aaa.validEndTime ) and aaa.isoDocStatus<>'2' and aaa.isoDocStatus<>'3' ";
      }  
    if (isoViewType.equals("2"))
      whereSQL = String.valueOf(whereSQL) + "  and aaa.isoDocStatus='2' "; 
    if (isoViewType.equals("3"))
      whereSQL = String.valueOf(whereSQL) + "  and aaa.isoDocStatus='3' "; 
    whereSQL = String.valueOf(whereSQL) + " and aaa.informationVersion not like '0.%'";
    String isoChoose = (httpServletRequest.getParameter("isoChoose") == null) ? "0" : httpServletRequest.getParameter("isoChoose");
    if (setAsso.equals("1") && !isoChoose.equals("1")) {
      String readerWhere = " and (";
      readerWhere = String.valueOf(readerWhere) + "((aaa.informationReader is null or aaa.informationReader ='') and (aaa.informationReaderOrg is null or aaa.informationReaderOrg='') and ( aaa.informationReaderGroup is null or aaa.informationReaderGroup='') ) ";
      if (orgIdString != null && orgIdString.length() > 3) {
        String cStr = orgIdString.substring(1, orgIdString.length() - 1);
        cStr = cStr.replaceAll("\\$", ",");
        cStr = cStr.replaceAll(",,", ",");
        String[] gg1 = cStr.split(",");
        if (gg1 != null && gg1.length > 0)
          for (int i = 0; i < gg1.length; i++)
            readerWhere = String.valueOf(readerWhere) + " or aaa.informationReaderOrg like '%*" + 
              gg1[i] + "*%' ";  
      } 
      readerWhere = String.valueOf(readerWhere) + " or aaa.informationReader like '%$" + userId + "$%' ";
      readerWhere = String.valueOf(readerWhere) + " or aaa.informationIssuerId = " + userId;
      if (groupList != null && groupList.size() > 0)
        for (int i = 0; i < groupList.size(); i++) {
          String groupId = groupList.get(i);
          readerWhere = String.valueOf(readerWhere) + "  or  aaa.informationReaderGroup like '%@" + groupId + "@%' ";
        }  
      readerWhere = String.valueOf(readerWhere) + " ) ";
      whereSQL = String.valueOf(whereSQL) + readerWhere;
      whereSQL = String.valueOf(whereSQL) + " and  isoDocStatus <>'2' ";
    } 
    if (isoChoose.equals("1"))
      whereSQL = String.valueOf(whereSQL) + " and  isoDocStatus <>'2' "; 
    if ("-1".equals(channelType)) {
      whereSQL = String.valueOf(whereSQL) + " and bbb.channelType >0 and bbb.userDefine=0 ";
    } else {
      whereSQL = String.valueOf(whereSQL) + " and bbb.channelType=" + channelType;
    } 
    httpServletRequest.setAttribute("hasRight", Boolean.valueOf(true));
    String channelId = "";
    if (httpServletRequest.getParameter("channelId") != null && 
      !httpServletRequest.getParameter("channelId").toString().equals("") && 
      !httpServletRequest.getParameter("channelId").toString().equals("null") && !httpServletRequest.getParameter("channelId").toString().equals("0"))
      channelId = httpServletRequest.getParameter("channelId"); 
    if (channelId.equals("") && httpServletRequest.getParameter("channelId_v") != null && !httpServletRequest.getParameter("channelId_v").equals("") && !httpServletRequest.getParameter("channelId_v").equals("null"))
      channelId = httpServletRequest.getParameter("channelId_v").toString(); 
    if (httpServletRequest.getParameter("searchChannel") != null && !httpServletRequest.getParameter("searchChannel").toString().equals("") && !httpServletRequest.getParameter("searchChannel").toString().equals("null") && !httpServletRequest.getParameter("searchChannel").toString().equals("0"))
      channelId = httpServletRequest.getParameter("searchChannel"); 
    if (channelId != null && !channelId.equals("") && !channelId.equals("null")) {
      whereSQL = String.valueOf(whereSQL) + " and   bbb.channelId=" + channelId + " ";
      httpServletRequest.setAttribute("channelId", channelId);
    } 
    httpServletRequest.setAttribute("searchChannel", channelId);
    if (channelId != null && !channelId.equals("0") && !channelId.equals(""))
      whereSQL = String.valueOf(whereSQL) + " and ( bbb.channelId = " + channelId + 
        " or aaa.otherChannel='," + channelId + ",')"; 
    String searchIssuerName = (httpServletRequest.getParameter("searchIssuerName") == null) ? "" : httpServletRequest.getParameter("searchIssuerName");
    httpServletRequest.setAttribute("searchIssuerName", searchIssuerName);
    if (!searchIssuerName.equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssuer like '%" + 
        searchIssuerName + "%' "; 
    String title = (httpServletRequest.getParameter("title") == null) ? "" : httpServletRequest.getParameter("title");
    httpServletRequest.setAttribute("title", title);
    if (httpServletRequest.getParameter("title") != null && !httpServletRequest.getParameter("title").equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + httpServletRequest.getParameter("title") + "%' "; 
    String security = (httpServletRequest.getParameter("security") == null) ? "" : httpServletRequest.getParameter("security");
    httpServletRequest.setAttribute("security", security);
    if (security != null && !security.equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.isoSecretStatus='" + security + "'"; 
    String key = (httpServletRequest.getParameter("key") == null) ? "" : httpServletRequest.getParameter("key");
    httpServletRequest.setAttribute("key", key);
    if (httpServletRequest.getParameter("key") != null && !httpServletRequest.getParameter("key").equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationKey like '%" + httpServletRequest.getParameter("key") + "%'"; 
    if (httpServletRequest.getParameter("searchDate") != null) {
      String searchBeginDate = httpServletRequest.getParameter(
          "searchBeginDate");
      String searchEndDate = httpServletRequest.getParameter(
          "searchEndDate");
      httpServletRequest.setAttribute("searchDate", "1");
      httpServletRequest.setAttribute("searchBeginDate", searchBeginDate);
      httpServletRequest.setAttribute("searchEndDate", searchEndDate);
      if (databaseType.indexOf("mysql") >= 0) {
        whereSQL = String.valueOf(whereSQL) + 
          " and aaa.informationIssueTime between '" + 
          searchBeginDate + 
          "' and '" + searchEndDate + 
          " 23:59:59' ";
      } else {
        whereSQL = String.valueOf(whereSQL) + 
          " and aaa.informationIssueTime between JSDB.FN_STRTODATE('" + 
          searchBeginDate + 
          "','S') and JSDB.FN_STRTODATE('" + searchEndDate + 
          " 23:59:59','') ";
      } 
    } 
    String docNo = (httpServletRequest.getParameter("docNo") == null) ? "" : httpServletRequest.getParameter("docNo");
    httpServletRequest.setAttribute("docNo", docNo);
    if (docNo != null && !docNo.equals(""))
      whereSQL = String.valueOf(whereSQL) + " and  aaa.documentNo like '%" + docNo + "%' "; 
    String searchOrgName = (httpServletRequest.getParameter("searchOrgName") == null) ? "" : httpServletRequest.getParameter("searchOrgName");
    httpServletRequest.setAttribute("searchOrgName", searchOrgName);
    if (httpServletRequest.getParameter("searchOrgName") != null && !httpServletRequest.getParameter("searchOrgName").equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.issueOrgIdString like '%$" + httpServletRequest.getParameter("searchOrg") + "$%' "; 
    String append = (httpServletRequest.getParameter("append") == null) ? "" : httpServletRequest.getParameter("append");
    httpServletRequest.setAttribute("append", append);
    if (httpServletRequest.getParameter("append") != null && !httpServletRequest.getParameter("append").equals(""))
      whereSQL = String.valueOf(whereSQL) + " and ( select count(*) from " + 
        " com.js.oa.info.infomanager.po.InformationAccessoryPO ccc " + 
        " where ccc.accessoryName like '%" + httpServletRequest.getParameter("append") + 
        "%' and ccc.information.informationId = aaa.informationId) > 0 "; 
    if (search != null) {
      httpServletRequest.setAttribute("search", "1");
      if (httpServletRequest.getParameter("checkdepart") != null) {
        httpServletRequest.getParameter("checkdepart").equals("1");
      } else {
        whereSQL = String.valueOf(whereSQL) + " and bbb.channelType = " + channelType;
      } 
      String str1 = httpServletRequest.getParameter("searchIssuerName");
      httpServletRequest.setAttribute("searchIssuerName", str1);
      if (!str1.equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssuer like '%" + 
          str1 + "%' "; 
      String searchKeywordType = httpServletRequest.getParameter("keywordType");
      String searchKeyword = httpServletRequest.getParameter("keyword");
      httpServletRequest.setAttribute("keywordType", searchKeywordType);
      httpServletRequest.setAttribute("keyword", searchKeyword);
      if (httpServletRequest.getParameter("title") != null && !httpServletRequest.getParameter("title").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + httpServletRequest.getParameter("title") + "%' "; 
      if (httpServletRequest.getParameter("key") != null && !httpServletRequest.getParameter("key").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationKey like '%" + httpServletRequest.getParameter("key") + "%'"; 
      if (httpServletRequest.getParameter("subtitle") != null && !httpServletRequest.getParameter("subtitle").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationSubTitle like '%" + httpServletRequest.getParameter("subtitle") + "%'"; 
    } 
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    if (sortType == null)
      sortType = "desc"; 
    if (orderBy == null) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.orderCode desc,aaa.informationModifyTime desc,aaa.informationId desc";
    } else if (orderBy.equals("date")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
        sortType;
    } else if (orderBy.equals("kits")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationKits " + sortType;
    } else if (orderBy.equals("commonNum")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationCommonNum " + 
        sortType;
    } else if (orderBy.equals("modifydate")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationModifyTime " + 
        sortType;
    } else {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
        sortType;
    } 
    list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    int listType = 0;
    if (httpServletRequest.getParameter("listType") != null)
      listType = Integer.parseInt(httpServletRequest.getParameter(
            "listType")); 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int listType = 0;
    if (httpServletRequest.getParameter("listType") != null)
      listType = Integer.parseInt(httpServletRequest.getParameter("listType")); 
    int pageSize = 15;
    if (listType == 1)
      pageSize = 30; 
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("informationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (httpServletRequest.getParameter("search") == null) {
      if (httpServletRequest.getParameter("depart") != null) {
        httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,orderBy,sortType,depart,styleId,setAsso,listType,channelShowType,userDefine,checkdepart,isAffiche,afficheType,isoViewType,channelId_v,setAssosetAsso");
      } else {
        httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
      } 
    } else if (httpServletRequest.getParameter("depart") != null) {
      httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,depart,styleId,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
    } else {
      httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
    } 
  }
  
  private void list2(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int listType = 0;
    if (httpServletRequest.getParameter("listType") != null)
      listType = Integer.parseInt(httpServletRequest.getParameter("listType")); 
    int pageSize = 15;
    if (listType == 1)
      pageSize = 30; 
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("informationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (httpServletRequest.getParameter("depart") != null) {
      httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
    } else {
      httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
    } 
  }
  
  private void list3(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(1000000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("informationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", "1000000");
    if (httpServletRequest.getParameter("depart") != null) {
      httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
    } else {
      httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType,channelId_v,setAsso");
    } 
  }
}
