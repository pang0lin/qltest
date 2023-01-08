package com.js.oa.info.infomanager.action;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.system.manager.service.ManagerService;
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

public class InfoListForRWSAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InformationActionForm informationActionForm = 
      (InformationActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String type = httpServletRequest.getParameter("type");
    String userDefine = httpServletRequest.getParameter("userDefine");
    String informationId = httpServletRequest.getParameter("informationId");
    String search = httpServletRequest.getParameter("search");
    String gdzt = httpServletRequest.getParameter("gdzt");
    String channelId = httpServletRequest.getParameter("channelId");
    httpServletRequest.setAttribute("gdzt", gdzt);
    httpServletRequest.setAttribute("channelId", channelId);
    String userChannelName = httpServletRequest.getParameter("userChannelName");
    String channelType = httpServletRequest.getParameter("channelType");
    String depart = httpServletRequest.getParameter("depart");
    String setAsso = httpServletRequest.getParameter("setAsso");
    String action = httpServletRequest.getParameter("action");
    InformationBD idb = new InformationBD();
    idb.resetOrderCode();
    String gjcx = (httpServletRequest.getParameter("gjcx") == null) ? "" : httpServletRequest.getParameter("gjcx").toString();
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("type", type);
    httpServletRequest.setAttribute("depart", depart);
    httpServletRequest.setAttribute("setAsso", setAsso);
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
    List list = null;
    if (!"2".equals(channelType)) {
      if (SystemCommon.getMultiDepart() == 1) {
        list = channelBD.getUserViewCh(userId, orgId, channelType, userDefine, domainId, session.getAttribute("corpId").toString(), session.getAttribute("sidelineCorpId").toString());
      } else {
        list = channelBD.getUserViewCh(userId, orgId, channelType, userDefine, domainId, "", "");
      } 
    } else {
      list = channelBD.getProBindListByUserId(userId);
    } 
    httpServletRequest.setAttribute("channelList", list);
    Date now = new Date();
    String nowString = now.toLocaleString();
    nowString = nowString.substring(0, nowString.indexOf(" "));
    String viewSQL = 
      " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationIssuer, aaa.informationVersion, aaa.informationIssueTime, aaa.informationSummary,aaa.informationHead,aaa.informationType,  aaa.informationCommonNum, bbb.channelName, bbb.channelId, aaa.titleColor,aaa.isConf,aaa.documentNo, aaa.transmitToWebsite,aaa.informationModifyTime,aaa.orderCode, aaa.informationIssueOrg,aaa.informationHeadFile,aaa.informationReader,aaa.informationIssuerId,bbb.channelNeedCheckup";
    String fromSQL = 
      " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
    String whereSQL = "";
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      whereSQL = " where aaa.domainId=" + domainId + " and aaa.informationStatus=0 and ( aaa.informationValidType = 0 or '" + 
        nowString + "' between aaa.validBeginTime and aaa.validEndTime ) " + 
        " and aaa.informationIssueTime<=now()";
    } else {
      whereSQL = " where aaa.domainId=" + domainId + " and aaa.informationStatus=0 and ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
        nowString + "','S') between aaa.validBeginTime and aaa.validEndTime ) " + 
        " and aaa.informationIssueTime<=sysdate";
    } 
    String participationProId = httpServletRequest.getParameter("participationProId");
    if (participationProId != null && !"".equals(participationProId)) {
      whereSQL = String.valueOf(whereSQL) + " and bbb.relProjectId in (" + participationProId + ") ";
    } else if ("".equals(participationProId)) {
      whereSQL = String.valueOf(whereSQL) + " and bbb.relProjectId =-1 ";
    } 
    if (httpServletRequest.getParameter("isAffiche") != null && 
      httpServletRequest.getParameter("isAffiche").toString().equals("1")) {
      InformationBD informationBD1 = new InformationBD();
      List<String> groupList = new ArrayList();
      groupList = informationBD1.getAllGroupByUserId(userId);
      whereSQL = " where aaa.domainId=" + domainId + 
        " and ( bbb.afficheChannelStatus='1' ) and aaa.informationStatus=0 ";
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
      String afficheType = (httpServletRequest.getParameter("afficheType") == null) ? "0" : httpServletRequest.getParameter("afficheType");
      if (afficheType.equals("0")) {
        httpServletRequest.setAttribute("afficheType", "0");
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + " and ( '" + nowString + "' between aaa.validBeginTime and aaa.validEndTime ) ";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and (  JSDB.FN_STRTODATE('" + nowString + "','S') between aaa.validBeginTime and aaa.validEndTime ) ";
        } 
      } else if (afficheType.equals("1")) {
        httpServletRequest.setAttribute("afficheType", "1");
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + 
            " and ( '" + nowString + "' between aaa.validEndTime and aaa.afficheHiTime ) ";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and (  JSDB.FN_STRTODATE('" + nowString + "','S') between aaa.validEndTime and aaa.afficheHiTime ) ";
        } 
      } else if (afficheType.equals("2")) {
        httpServletRequest.setAttribute("afficheType", "2");
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + " and ( '" + nowString + "' > aaa.afficheHiTime ) ";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and (  JSDB.FN_STRTODATE('" + nowString + "','S') > aaa.afficheHiTime ) ";
        } 
      } else if (afficheType.equals("3")) {
        httpServletRequest.setAttribute("afficheType", "3");
        ManagerService managerBD = new ManagerService();
        String afficheWhere = managerBD.getRightFinalWhere(userId, orgId, "af*07*1106", "aaa.informationIssueOrgId", "aaa.informationIssuerId");
        whereSQL = " where aaa.domainId=" + domainId + " and ( ( bbb.afficheChannelStatus='1' ) and ( aaa.informationStatus=0) and  " + afficheWhere + ")";
      } else if (afficheType.equals("4")) {
        httpServletRequest.setAttribute("afficheType", "4");
        whereSQL = " where aaa.domainId=" + domainId + " and ( bbb.afficheChannelStatus='1' ) and  aaa.informationStatus=0 and aaa.informationIssuer= '" + session.getAttribute("userName") + "'";
      } 
    } else {
      whereSQL = String.valueOf(whereSQL) + " and (  bbb.afficheChannelStatus is null or bbb.afficheChannelStatus='0'  ) ";
    } 
    InformationBD informationBD = new InformationBD();
    if (httpServletRequest.getParameter("isAffiche") == null || 
      !httpServletRequest.getParameter("isAffiche").toString().equals("1")) {
      String rightWhere = (new ManagerService()).getRightFinalWhere(userId, orgId, "01*03*03", "aaa.informationIssueOrgId", "aaa.informationIssuerId");
      String canReadAllInfoChannel = informationBD.getAllInfoChannel(userId, orgId, channelType, userDefine);
      String managedChannel = informationBD.getManagedChannel(userId, orgId, channelType, userDefine);
      String scopeWhere = (new ManagerService()).getScopeFinalWhere(userId, orgId, orgIdString, "bbb.channelManager", "bbb.channelManagerOrg", "bbb.channelManagerGroup");
      whereSQL = String.valueOf(whereSQL) + " and ( ( (bbb.channelId in (" + canReadAllInfoChannel + ") or((" + scopeWhere + ") and (" + rightWhere + ")) or aaa.informationIssuerId=" + userId + ") and (bbb.afficheChannelStatus is null or bbb.afficheChannelStatus='0') ) or 1=1 ";
    } 
    String hSql = "";
    hSql = informationBD.getUserViewCh(userId, orgId, channelType, userDefine);
    String lhsql = "";
    List<String> hsqlList = informationBD.getUserViewCh2(userId, orgId, channelType, userDefine);
    if (hsqlList != null && hsqlList.size() != 0) {
      for (int ii = 0; ii < hsqlList.size() - 1; ii++)
        lhsql = String.valueOf(lhsql) + "'," + hsqlList.get(ii) + ",',"; 
      lhsql = String.valueOf(lhsql) + "'," + hsqlList.get(hsqlList.size() - 1) + ",'";
    } 
    if (informationId != null)
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationId<>" + informationId + " "; 
    boolean hasRight = false;
    String rightCode = "01*03*03";
    if (httpServletRequest.getParameter("checkdepart") != null && "1".equals(httpServletRequest.getParameter("checkdepart")))
      rightCode = "01*01*02"; 
    List<Object[]> rightScopeList = (new ManagerService()).getRightScope(userId, rightCode);
    if (rightScopeList != null && rightScopeList.size() > 0 && rightScopeList.get(0) != null) {
      Object[] obj = rightScopeList.get(0);
      if ("0".equals(obj[0].toString()))
        hasRight = true; 
    } 
    httpServletRequest.setAttribute("hasRight", Boolean.valueOf(hasRight));
    String whereTmp = "";
    if (httpServletRequest.getParameter("isAffiche") == null || 
      !httpServletRequest.getParameter("isAffiche").toString().equals("1"))
      if (httpServletRequest.getParameter("checkdepart") == null || 
        httpServletRequest.getParameter("checkdepart").equals("")) {
        if (!hasRight) {
          if ("".equals(lhsql)) {
            whereTmp = String.valueOf(whereTmp) + " and bbb.channelId in (" + hSql + ") ";
          } else {
            whereTmp = String.valueOf(whereTmp) + " and (( bbb.channelId in (" + hSql + ") ";
            whereTmp = String.valueOf(whereTmp) + " and bbb.channelType = " + channelType;
            whereTmp = String.valueOf(whereTmp) + ")or aaa.otherChannel in (" + lhsql + ")) ";
          } 
          InformationBD infoBD = new InformationBD();
          String readerWhere = infoBD.getInfoReader(session
              .getAttribute(
                "userId").toString(), 
              session.getAttribute("orgId").toString(), 
              session.getAttribute("orgIdString").toString(), 
              "aaa");
          if (readerWhere != null && !readerWhere.equals(""))
            whereTmp = String.valueOf(whereTmp) + " and (" + readerWhere + ") "; 
        } else {
          whereTmp = String.valueOf(whereTmp) + " and bbb.channelType = " + channelType;
        } 
      } else {
        if ("".equals(lhsql)) {
          whereTmp = String.valueOf(whereTmp) + " and bbb.channelId in (" + hSql + 
            ") ";
          if (!httpServletRequest.getParameter("checkdepart").equals(
              "1"))
            whereTmp = String.valueOf(whereTmp) + " and bbb.channelType = " + channelType; 
        } else {
          whereTmp = String.valueOf(whereTmp) + " and (( bbb.channelId in (" + hSql + 
            ") ";
          if (!httpServletRequest.getParameter("checkdepart").equals(
              "1"))
            whereTmp = String.valueOf(whereTmp) + " and bbb.channelType = " + channelType; 
          whereTmp = String.valueOf(whereTmp) + ")or aaa.otherChannel in (" + lhsql + ")) ";
        } 
        InformationBD infoBD = new InformationBD();
        String readerWhere = infoBD.getInfoReader(userId, orgId, 
            session.getAttribute("orgIdString").toString(), "aaa");
        if (!hasRight)
          if (readerWhere != null && !readerWhere.equals(""))
            whereTmp = String.valueOf(whereTmp) + " and (" + readerWhere + ") ";  
      }  
    whereSQL = String.valueOf(whereSQL) + whereTmp;
    if (httpServletRequest.getParameter("isAffiche") == null || 
      !httpServletRequest.getParameter("isAffiche").toString().equals("1"))
      whereSQL = String.valueOf(whereSQL) + ")"; 
    if (!type.equals("all"))
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIsCommend=1 "; 
    if (search != null) {
      httpServletRequest.setAttribute("search", "1");
      if (httpServletRequest.getParameter("checkdepart") != null) {
        httpServletRequest.getParameter("checkdepart").equals("1");
      } else {
        whereSQL = String.valueOf(whereSQL) + " and bbb.channelType = " + channelType;
      } 
      String searchChannel = httpServletRequest.getParameter("searchChannel");
      httpServletRequest.setAttribute("searchChannel", searchChannel);
      if (!searchChannel.equals("0"))
        whereSQL = String.valueOf(whereSQL) + " and ( bbb.channelIdString like '%$" + searchChannel + "$%' or aaa.otherChannel='," + searchChannel + ",')"; 
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
      String searchIssuerName = httpServletRequest.getParameter("searchIssuerName");
      httpServletRequest.setAttribute("searchIssuerName", searchIssuerName);
      if (searchIssuerName != null && !searchIssuerName.equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssuer like '%" + 
          searchIssuerName.trim() + "%' "; 
      String searchKeywordType = httpServletRequest.getParameter("keywordType");
      String searchKeyword = httpServletRequest.getParameter("keyword");
      httpServletRequest.setAttribute("keywordType", searchKeywordType);
      httpServletRequest.setAttribute("keyword", searchKeyword);
      if (searchKeywordType != null && !searchKeywordType.equals("") && !searchKeywordType.equals("null") && searchKeyword != null && !searchKeyword.equals("") && !searchKeyword.equals("null")) {
        if (searchKeywordType.equals("title"))
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + searchKeyword.trim() + "%' "; 
        if (searchKeywordType.equals("key"))
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationKey like '%" + searchKeyword.trim() + "%'"; 
        if (searchKeywordType.equals("append"))
          whereSQL = String.valueOf(whereSQL) + " and ( select count(*) from " + 
            " com.js.oa.info.infomanager.po.InformationAccessoryPO ccc " + 
            " where ccc.accessoryName like '%" + searchKeyword.trim() + 
            "%' and ccc.information.informationId = aaa.informationId) > 0 "; 
      } else {
        if (httpServletRequest.getParameter("title") != null && !httpServletRequest.getParameter("title").trim().equals(""))
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + httpServletRequest.getParameter("title").trim().trim() + "%' "; 
        if (httpServletRequest.getParameter("key") != null && !httpServletRequest.getParameter("key").trim().equals(""))
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationKey like '%" + httpServletRequest.getParameter("key").trim() + "%'"; 
        if (httpServletRequest.getParameter("subtitle") != null && !httpServletRequest.getParameter("subtitle").trim().equals(""))
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationSubTitle like '%" + httpServletRequest.getParameter("subtitle").trim() + "%'"; 
        if (httpServletRequest.getParameter("append") != null && !httpServletRequest.getParameter("append").trim().equals(""))
          whereSQL = String.valueOf(whereSQL) + " and ( select count(*) from " + 
            " com.js.oa.info.infomanager.po.InformationAccessoryPO ccc " + 
            " where ccc.accessoryName like '%" + httpServletRequest.getParameter("append").trim() + 
            "%' and ccc.information.informationId = aaa.informationId) > 0 "; 
      } 
      if (httpServletRequest.getParameter("searchOrgName") != null && !httpServletRequest.getParameter("searchOrgName").trim().equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.issueOrgIdString like '%$" + httpServletRequest.getParameter("searchOrg").trim() + "$%' "; 
    } 
    if (SystemCommon.getMultiDepart() == 1)
      whereSQL = String.valueOf(whereSQL) + " and aaa.corpId=" + session.getAttribute("corpId").toString(); 
    if ("yuguidang".equals(gdzt)) {
      whereSQL = String.valueOf(whereSQL) + " and exists(select st.id from RWSWorkFlowStatusPO st where st.recordId=aaa.id and st.tableName='information' and status='0')";
    } else if ("yiguidang".equals(gdzt)) {
      whereSQL = String.valueOf(whereSQL) + " and exists(select st.id from RWSWorkFlowStatusPO st where st.recordId=aaa.id and st.tableName='information' and status='1')";
    } 
    if (channelId != null)
      whereSQL = String.valueOf(whereSQL) + " and bbb.channelId in(select ch.channelId from InformationChannelPO ch where ch.channelIdString like '%$" + channelId + "$%') "; 
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    if (sortType == null)
      sortType = "desc"; 
    if (orderBy == null) {
      if (httpServletRequest.getParameter("isAffiche") != null && 
        httpServletRequest.getParameter("isAffiche").toString().equals("1")) {
        whereSQL = String.valueOf(whereSQL) + " order by aaa.informationId desc";
      } else {
        whereSQL = String.valueOf(whereSQL) + " order by aaa.orderCode desc,aaa.informationModifyTime desc,aaa.informationId desc";
      } 
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
    whereSQL = whereSQL.replaceAll("channelType = 0 ", "channelType in (0,1) ");
    list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    String tag = "success";
    int listType = 0;
    if (httpServletRequest.getParameter("listType") != null)
      listType = Integer.parseInt(httpServletRequest.getParameter(
            "listType")); 
    if (depart != null) {
      tag = "depart";
      if (listType == 1) {
        tag = "departList";
      } else if (httpServletRequest.getParameter("channelShowType") != null) {
        tag = "departPicList";
      } 
      String styleId = httpServletRequest.getParameter("styleId");
      httpServletRequest.setAttribute("styleId", styleId);
      httpServletRequest.setAttribute("stylePO", (new ChannelBD()).getDepaStyle("2"));
    } else if (listType == 1) {
      tag = "list";
    } else if (httpServletRequest.getParameter("channelShowType") != null) {
      tag = "picList";
    } 
    if (httpServletRequest.getParameter("isAffiche") != null && httpServletRequest.getParameter("isAffiche").toString().equals("1"))
      tag = "alist"; 
    if (gjcx.equals("1"))
      tag = "gjcx"; 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int listType = 0;
    if (httpServletRequest.getParameter("listType") != null)
      listType = Integer.parseInt(httpServletRequest.getParameter("listType")); 
    int pageSize = 100;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list != null && list.size() < 1) {
      currentPage--;
      if (currentPage > 0) {
        page.setcurrentPage(currentPage);
        list = page.getResultList();
      } 
      httpServletRequest.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("informationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (httpServletRequest.getParameter("search") == null) {
      if (httpServletRequest.getParameter("depart") != null) {
        httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,orderBy,sortType,depart,styleId,setAsso,listType,channelShowType,userDefine,checkdepart,isAffiche,afficheType,relation");
      } else {
        httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,isAffiche,afficheType,relation");
      } 
    } else if (httpServletRequest.getParameter("depart") != null) {
      httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,depart,styleId,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,relation");
    } else {
      httpServletRequest.setAttribute("pageParameters", "type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,relation");
    } 
  }
}
