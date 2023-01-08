package com.js.oa.info.infomanager.action;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.info.channelmanager.bean.ChannelEJBBean;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.bean.InformationEJBBean;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DeleteFile;
import com.js.util.util.ParameterFilter;
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

public class InfoSearchAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InfoSearchActionForm infoSearchActionForm = (InfoSearchActionForm)actionForm;
    ChannelBD channelBD = new ChannelBD();
    String userChannelName = httpServletRequest.getParameter("userChannelName");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String searchKeywordType = httpServletRequest.getParameter("keywordType");
    String searchKeyword = httpServletRequest.getParameter("keyword");
    String searchChannel = httpServletRequest.getParameter("searchChannel");
    String publicInfo = httpServletRequest.getParameter("publicInfo");
    httpServletRequest.setAttribute("publicInfo", publicInfo);
    String participationProId = httpServletRequest.getParameter("participationProId");
    String projectId_ = httpServletRequest.getParameter("projectId_");
    httpServletRequest.setAttribute("projectId_", projectId_);
    String searchDate = httpServletRequest.getParameter("searchDate");
    String searchBeginDate = httpServletRequest.getParameter("searchBeginDate");
    httpServletRequest.setAttribute("searchBeginDate", searchBeginDate);
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    httpServletRequest.setAttribute("searchEndDate", searchEndDate);
    String searchIssuerName = httpServletRequest.getParameter("searchIssuerName");
    String channelType = httpServletRequest.getParameter("channelType");
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    Object object = httpServletRequest.getParameter("channelId");
    String type = httpServletRequest.getParameter("type");
    String userDefine = httpServletRequest.getParameter("userDefine");
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    if (!ParameterFilter.isNumber(channelType) || 
      !ParameterFilter.isNumber((String)object) || 
      !ParameterFilter.checkParameter(userDefine) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("userChannelName")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("channelShowType")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("channelName")) || 
      !ParameterFilter.checkParameter(searchIssuerName) || 
      !ParameterFilter.checkParameter(searchBeginDate) || 
      !ParameterFilter.checkParameter(searchEndDate) || 
      !ParameterFilter.checkParameter(searchKeyword) || 
      !ParameterFilter.checkParameter(sortType) || 
      !ParameterFilter.checkParameter(searchEndDate) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("title")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("subtitle")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("key")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("append")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("searchOrg")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    String returnValue = "";
    try {
      returnValue = (new ArchivesBD())
        .archivesPigeonholeSet("ZSGL", domainId);
    } catch (Exception exception) {}
    if (returnValue.equals(""))
      returnValue = "1,1,0"; 
    Boolean dossier = Boolean.FALSE;
    String isSendMessage = "0";
    if (!returnValue.equals("-1")) {
      String[] tmp = returnValue.split(",");
      if (tmp[0].equals("1") || tmp[0].equals("01"))
        dossier = Boolean.TRUE; 
      isSendMessage = tmp[2];
    } 
    httpServletRequest.setAttribute("dossier", dossier);
    httpServletRequest.setAttribute("isSendMessage", isSendMessage);
    InformationBD informationBD = new InformationBD();
    String hSql = "";
    hSql = informationBD.getUserViewCh3(userId, orgId, channelType, userDefine);
    if (SystemCommon.getMultiDepart() == 1) {
      list = channelBD.getUserViewCh(userId, orgId, channelType, userDefine, domainId, httpSession.getAttribute("corpId").toString(), httpSession.getAttribute("sidelineCorpId").toString());
    } else {
      list = channelBD.getUserViewCh(userId, orgId, channelType, userDefine, domainId, "", "");
    } 
    httpServletRequest.setAttribute("channelList", list);
    Date now = new Date();
    String nowString = now.toLocaleString();
    nowString = nowString.substring(0, nowString.indexOf(" "));
    String viewSQL = "aaa.informationId,aaa.informationTitle,aaa.informationKits,aaa.informationAuthor,aaa.informationVersion,aaa.informationIssueTime,aaa.informationSummary,aaa.informationHead,aaa.informationType,aaa.informationIssuer,aaa.isConf,bbb.channelId,bbb.channelName,aaa.transmitToWebsite,aaa.informationModifyTime,aaa.informationCommonNum,bbb.channelNeedCheckup,aaa.dossierStatus,aaa.informationHeadFile,aaa.orderCodeTemp,aaa.validBeginTime,aaa.validEndTime,aaa.informationValidType,aaa.titleColor,aaa.informationReader,aaa.informationIssuerId";
    String fromSQL = " com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb ";
    if (object == null || "".equals(object)) {
      String[][] info = { { "100", "新闻" }, { "101", "公告" } };
      ChannelBD cbd = new ChannelBD();
      if (SystemCommon.getMultiDepart() == 1) {
        String corpId = session.getAttribute("corpId").toString();
        info = cbd.getChannelSimpleInfoByCorpId(corpId, 0);
      } 
      List<Object[]> noticeList = new ArrayList();
      if ("XinWen".equals(type)) {
        noticeList = cbd.getChannelInfoList((new StringBuilder(String.valueOf(info[0][0]))).toString(), userId);
      } else {
        noticeList = cbd.getChannelInfoList((new StringBuilder(String.valueOf(info[1][0]))).toString(), userId);
      } 
      for (int j = 0; j < noticeList.size(); j++) {
        Object[] obj = noticeList.get(j);
        if ("XinWen".equals(type) && "新闻".equals(obj[1])) {
          Object object1 = obj[0];
        } else if ("GongGao".equals(type) && "公告".equals(obj[1])) {
          object = obj[0];
        } 
      } 
    } 
    httpServletRequest.setAttribute("channelId", object);
    boolean canVindicate = channelBD.canVindicate(userId, orgId, (String)object);
    if (canVindicate) {
      httpServletRequest.setAttribute("canVindicate", "1");
    } else {
      httpServletRequest.setAttribute("canVindicate", "0");
    } 
    String databaseType = SystemCommon.getDatabaseType();
    String whereSQL = "";
    String includeChild = "";
    List<Object[]> lists = new ArrayList();
    lists = channelBD.getSingleChannel((String)object);
    if (lists != null && lists.size() > 0) {
      Object[] obj = (Object[])null;
      obj = lists.get(0);
      includeChild = obj[22].toString();
    } 
    if ("2".equals(channelType)) {
      InformationEJBBean info = new InformationEJBBean();
      try {
        if (!info.judgeUserIsProLeader_2(userId, (String)object)) {
          whereSQL = " where 1>2 ";
        } else {
          if (includeChild.equals("0")) {
            whereSQL = " where (bbb.channelIdString like '%$" + object + 
              "%%' or aaa.otherChannel like '%," + object + ",%') ";
          } else if (includeChild.equals("1")) {
            whereSQL = " where (bbb.channelIdString like '%$" + object + 
              "$%' or aaa.otherChannel like '%," + object + 
              ",%') ";
          } else if (databaseType.indexOf("mysql") >= 0) {
            whereSQL = " where ( aaa.informationValidType = 0 or '" + 
              nowString + 
              "' between aaa.validBeginTime and aaa.validEndTime )";
            if (includeChild.equals("1")) {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelIdString like '%$" + 
                object + 
                "$%' or aaa.otherChannel like '%," + 
                object + ",%') ";
            } else {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + object + 
                " or aaa.otherChannel like '%," + object + 
                ",%') ";
            } 
          } else {
            whereSQL = " where ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
              nowString + 
              "','S') between aaa.validBeginTime and aaa.validEndTime )";
            if (includeChild.equals("1")) {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelIdString like '%$" + 
                object + 
                "$%' or aaa.otherChannel like '%," + 
                object + ",%') ";
            } else {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + object + 
                " or aaa.otherChannel like '%," + object + 
                ",%') ";
            } 
          } 
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationStatus=0 ";
          whereSQL = String.valueOf(whereSQL) + " and aaa.domainId=" + domainId;
          if ("".equals(participationProId) || "null".equals(participationProId) || participationProId == null) {
            whereSQL = String.valueOf(whereSQL) + " and aaa.informationChannel.relProjectId=-1";
          } else {
            String[] ids = participationProId.split(",");
            whereSQL = String.valueOf(whereSQL) + " and ((1=1)";
            for (int j = 0; j < ids.length; j++)
              whereSQL = String.valueOf(whereSQL) + " or (aaa.informationChannel.relProjectId=" + ids[j] + ")"; 
            whereSQL = String.valueOf(whereSQL) + ")";
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      if (!informationBD.channelCanView2(userId, orgId, channelType, userDefine, (String)object)) {
        whereSQL = " where 1>2 ";
      } else {
        if (canVindicate && includeChild.equals("0")) {
          whereSQL = " where (bbb.channelIdString like '%$" + object + 
            "$%' or aaa.otherChannel like '%," + object + ",%') ";
        } else if (canVindicate && includeChild.equals("1")) {
          String childChannelIds = informationBD.getAllChildChannelIds((String)object);
          whereSQL = " where (bbb.channelId in(" + childChannelIds + ")";
          if (databaseType.indexOf("mysql") >= 0) {
            whereSQL = String.valueOf(whereSQL) + " or ('" + childChannelIds + "' like concat('%',aaa.otherChannel,'%') and aaa.otherChannel is not null) )";
          } else {
            whereSQL = String.valueOf(whereSQL) + " or ('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', aaa.otherChannel), '%') and aaa.otherChannel is not null) )";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = " where ( aaa.informationValidType = 0 or '" + 
            nowString + 
            "' between aaa.validBeginTime and aaa.validEndTime )";
          if (includeChild.equals("1")) {
            ChannelEJBBean cbean = new ChannelEJBBean();
            String childChannelIds = "0";
            try {
              childChannelIds = cbean.getUserViewChildCh(userId, orgId, (String)object);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId in(" + childChannelIds + ")";
            whereSQL = String.valueOf(whereSQL) + " or ('" + childChannelIds + "' like concat('%',aaa.otherChannel,'%') and aaa.otherChannel is not null) )";
          } else {
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + object + 
              " or aaa.otherChannel like '%," + object + 
              ",%') ";
          } 
        } else {
          whereSQL = " where ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
            nowString + 
            "','S') between aaa.validBeginTime and aaa.validEndTime )";
          if (includeChild.equals("1")) {
            ChannelEJBBean cbean = new ChannelEJBBean();
            String childChannelIds = "0";
            try {
              childChannelIds = cbean.getUserViewChildCh(userId, orgId, (String)object);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId in(" + childChannelIds + ")";
            whereSQL = String.valueOf(whereSQL) + " or ('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', aaa.otherChannel), '%') and aaa.otherChannel is not null) )";
          } else {
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + object + 
              " or aaa.otherChannel like '%," + object + 
              ",%') ";
          } 
        } 
        if (httpServletRequest.getParameter("isAffiche") != null && httpServletRequest.getParameter("isAffiche").toString().equals("1")) {
          whereSQL = String.valueOf(whereSQL) + " and ( bbb.afficheChannelStatus='1' )";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and ( bbb.afficheChannelStatus is null or bbb.afficheChannelStatus='0' )";
        } 
        InformationBD infoBD = new InformationBD();
        String readerWhere = "1<>2";
        if (!canVindicate)
          readerWhere = infoBD.getInfoReader(userId, orgId, orgIdString, 
              "aaa"); 
        String rightWhere = (new ManagerService()).getRightFinalWhere(userId, 
            orgId, "01*03*03", "aaa.informationIssueOrgId", 
            "aaa.informationIssuerId");
        whereSQL = String.valueOf(whereSQL) + " and ((" + readerWhere + ")or (" + 
          rightWhere + ")) and aaa.informationStatus=0 ";
      } 
      whereSQL = String.valueOf(whereSQL) + " and aaa.domainId=" + domainId;
    } 
    if (searchDate != null)
      if (databaseType.indexOf("mysql") >= 0) {
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime between '" + searchBeginDate + "' and '" + searchEndDate + " 23:59:59' ";
      } else {
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime between JSDB.FN_STRTODATE('" + searchBeginDate + "','S') and JSDB.FN_STRTODATE('" + searchEndDate + " 23:59:59', '') ";
      }  
    if (searchIssuerName != null && !searchIssuerName.equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssuer like '%" + searchIssuerName + "%' "; 
    if (searchKeywordType == null || "null".equals(searchKeywordType)) {
      if (httpServletRequest.getParameter("title") != null && 
        !httpServletRequest.getParameter("title").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + 
          httpServletRequest.getParameter("title") + "%' "; 
      if (httpServletRequest.getParameter("key") != null && 
        !httpServletRequest.getParameter("key").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationKey like '%" + 
          httpServletRequest.getParameter("key") + "%'"; 
      if (httpServletRequest.getParameter("subtitle") != null && 
        !httpServletRequest.getParameter("subtitle").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationSubTitle like '%" + 
          httpServletRequest.getParameter("subtitle") + "%'"; 
      if (httpServletRequest.getParameter("append") != null && 
        !httpServletRequest.getParameter("append").equals(""))
        whereSQL = String.valueOf(whereSQL) + " and ( select count(*) from " + 
          " com.js.oa.info.infomanager.po.InformationAccessoryPO ccc " + 
          " where ccc.accessoryName like '%" + 
          httpServletRequest.getParameter("append") + 
          "%' and ccc.information.informationId = aaa.informationId) > 0 "; 
    } else if (searchKeywordType.equals("title")) {
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + searchKeyword + "%' ";
    } else if (searchKeywordType.equals("key")) {
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationKey like '%" + searchKeyword + "%'";
    } else if (searchKeywordType.equals("subtitle")) {
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationSubTitle like '%" + searchKeyword + "%'";
    } else {
      whereSQL = String.valueOf(whereSQL) + " and ( select count(*) from com.js.oa.info.infomanager.po.InformationAccessoryPO ccc where ccc.accessoryName like '%" + searchKeyword + "%' and ccc.information.informationId = aaa.informationId) > 0 ";
    } 
    if (httpServletRequest.getParameter("searchOrgName") != null && !httpServletRequest.getParameter("searchOrgName").equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.issueOrgIdString like '%$" + httpServletRequest.getParameter("searchOrg") + "$%' "; 
    if (sortType == null)
      sortType = "desc"; 
    if (orderBy == null) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.orderCodeTemp desc,aaa.informationModifyTime desc, aaa.informationIssueTime desc, aaa.informationId desc ";
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
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationId " + sortType;
    } 
    String action = (httpServletRequest.getParameter("action") == null) ? "" : httpServletRequest.getParameter("action");
    if (action.equals("commend") || action.equals("transfer") || 
      action.equals("batchDelete") || action.equals("delete")) {
      String[] batchId = httpServletRequest.getParameterValues("batchId");
      if (action.equals("commend")) {
        informationBD.commend(batchId);
      } else if (!action.equals("transfer")) {
        if (action.equals("batchDelete")) {
          list = informationBD.batchDelete(batchId);
          if (list != null && list.size() > 0) {
            DeleteFile deleteFile = new DeleteFile();
            for (int j = 0; j < list.size(); j++)
              deleteFile.delete("information", list.get(j)); 
          } 
        } 
      } 
    } else if (action.equals("singleDelete")) {
      String id = httpServletRequest.getParameter("id");
      list = informationBD.singleDelete((String)object, id);
      if (list != null && list.size() > 0) {
        String path = httpServletRequest.getRealPath("/");
        DeleteFile deleteFile = new DeleteFile();
        for (int j = 0; j < list.size(); j++)
          deleteFile.delete(String.valueOf(path) + "/upload/information", list.get(j)); 
      } 
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<String> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    if (list != null && list.size() < 1) {
      currentPage--;
      if (currentPage > 0) {
        page.setcurrentPage(currentPage);
        list = page.getResultList();
      } 
      httpServletRequest.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    StringBuffer inforIds = new StringBuffer();
    int i;
    for (i = 0; i < list.size(); i++) {
      Object[] objects = (Object[])list.get(i);
      inforIds.append(objects[0]).append(",");
    } 
    inforIds.append("-1");
    if (httpServletRequest.getParameter("title") != null && !"".equals(httpServletRequest.getParameter("title")) && 
      list != null)
      for (i = 0; i < list.size(); i++) {
        Object[] obj = (Object[])list.get(i);
        inforIds.append(obj[0]).append(",");
        String tmp = obj[1].toString();
        obj[1] = tmp.replaceAll(httpServletRequest.getParameter("title"), "<span class='mustFillcolor'>" + httpServletRequest.getParameter("title") + "</span>");
        list.set(i, obj);
      }  
    String tag = "success";
    String depart = httpServletRequest.getParameter("depart");
    if (depart != null) {
      tag = "successdepart";
      httpServletRequest.setAttribute("depart", depart);
      String styleId = httpServletRequest.getParameter("styleId");
      httpServletRequest.setAttribute("stylePO", (new ChannelBD()).getDepaStyle(styleId));
      httpServletRequest.setAttribute("styleId", styleId);
    } 
    if (!canVindicate) {
      ManagerService mbd = new ManagerService();
      List rightList = mbd.getRightScope(userId, "01*03*03");
      if (rightList != null && rightList.size() > 0 && rightList.get(0) != null)
        if (channelBD.isChannelManager((String)object, userId, orgId, orgIdString)) {
          inforIds.append("-1");
          String ids = (new InformationBD()).getInformationModiIds((String)object, userId, orgId, orgIdString, inforIds.toString(), rightList);
          httpServletRequest.setAttribute("canModifiedIds", ids);
        }  
    } 
    httpServletRequest.setAttribute("searchList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    if ("".equals(participationProId) || "null".equals(participationProId) || participationProId == null) {
      if (depart != null) {
        httpServletRequest.setAttribute("pageParameters", "action,searchChannel,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,channelType,userChannelName,styleId,depart,userDefine,searchOrgName,searchOrg,channelId,channelName,projectId_,type");
      } else {
        httpServletRequest.setAttribute("pageParameters", "action,searchChannel,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,channelType,userChannelName,userDefine,searchOrgName,searchOrg,channelId,channelName,projectId_,type");
      } 
    } else if (depart != null) {
      httpServletRequest.setAttribute("pageParameters", "action,searchChannel,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,channelType,userChannelName,styleId,depart,userDefine,searchOrgName,searchOrg,channelId,channelName,participationProId,projectId_,type");
    } else {
      httpServletRequest.setAttribute("pageParameters", "action,searchChannel,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,channelType,userChannelName,userDefine,searchOrgName,searchOrg,channelId,channelName,participationProId,projectId_,type");
    } 
    return actionMapping.findForward(tag);
  }
}
