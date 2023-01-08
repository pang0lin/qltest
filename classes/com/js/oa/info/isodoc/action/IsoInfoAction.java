package com.js.oa.info.isodoc.action;

import com.js.oa.info.isodoc.po.IsoPaperPO;
import com.js.oa.info.isodoc.service.IsoDocBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IsoInfoAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    IsoInfoActionForm IsoInfoActionForm = (IsoInfoActionForm)actionForm;
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String action = (httpServletRequest.getParameter("action") == null) ? "paperlist" : httpServletRequest.getParameter("action");
    if ("paperlist".equals(action)) {
      list2(httpServletRequest);
      return actionMapping.findForward("paperlist");
    } 
    if ("paperAdd".equals(action))
      return actionMapping.findForward("paperAdd"); 
    if ("paperSave".equals(action) || "paperContinue".equals(action)) {
      IsoPaperPO po = setIsoPaperPO(httpServletRequest);
      Long result = (new IsoDocBD()).saveIsoPaperPO(po);
      if ("-1".equals(result)) {
        httpServletRequest.setAttribute("result", "1");
      } else {
        httpServletRequest.setAttribute("result", "0");
      } 
      if ("paperContinue".equals(action))
        httpServletRequest.setAttribute("continue", "1"); 
      return actionMapping.findForward("paperAdd");
    } 
    if ("loadPaper".equals(action)) {
      IsoDocBD bd = new IsoDocBD();
      IsoPaperPO po = bd.loadIsoPaperPO(httpServletRequest.getParameter("paperId"));
      httpServletRequest.setAttribute("isoPaperPO", po);
      return actionMapping.findForward("loadPaper");
    } 
    if ("setPaperStatus".equals(action)) {
      setPaperPOStatus(httpServletRequest);
      return actionMapping.findForward("changResult");
    } 
    if ("paperDelete".equals(action)) {
      IsoDocBD bd = new IsoDocBD();
      String id = httpServletRequest.getParameter("id");
      bd.deletePaperPO(id);
      list2(httpServletRequest);
    } 
    if ("informationView".equals(action)) {
      view(httpServletRequest);
      return actionMapping.findForward("informationView");
    } 
    if ("transferUserId".equals(action)) {
      String informationId = httpServletRequest.getParameter(
          "informationId");
      String transUserId = httpServletRequest.getParameter("transUserId");
      IsoDocBD bd = new IsoDocBD();
      bd.TransferUserId(informationId, transUserId);
      return actionMapping.findForward("changResult");
    } 
    return actionMapping.findForward("paperlist");
  }
  
  private void list2(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    ManagerService managerBD = new ManagerService();
    String receivewhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "bbb.receiveUser", 
        "bbb.receiveOrg", 
        "bbb.receiveGroup");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.oa.info.isodoc.po.IsoPaperPO bbb ";
    String whereSql = " 1=1 ";
    String mypaper = (request.getParameter("mypaper") == null) ? "0" : request.getParameter("mypaper");
    if ("1".equals(mypaper))
      whereSql = String.valueOf(whereSql) + " and (" + receivewhere + " ) "; 
    if (request.getParameter("informationName") != null && !request.getParameter("informationName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.informationName like '%" + request.getParameter("informationName") + "%'";
      request.setAttribute("informationName", request.getParameter("informationName"));
    } 
    if (request.getParameter("channelName") != null && !request.getParameter("channelName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.channelName like  '%" + request.getParameter("channelName") + "%'";
      request.setAttribute("channelName", request.getParameter("channelName"));
    } 
    if (request.getParameter("documentNo") != null && !request.getParameter("documentNo").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.documentNO like '%" + request.getParameter("documentNo") + "%' ";
      request.setAttribute("documentNo", request.getParameter("documentNo"));
    } 
    if (request.getParameter("infromationVersion") != null && !request.getParameter("infromationVersion").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.infromationVersion like '%" + request.getParameter("infromationVersion") + "%'";
      request.setAttribute("infromationVersion", request.getParameter("infromationVersion"));
    } 
    if (request.getParameter("provideUserName") != null && !request.getParameter("provideUserName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.provideUserName like '%" + request.getParameter("provideUserName") + "%'";
      request.setAttribute("provideUserName", request.getParameter("provideUserName"));
    } 
    if (request.getParameter("provideOrgName") != null && !request.getParameter("provideOrgName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.provideOrgName like '%" + request.getParameter("provideOrgName") + "%'";
      request.setAttribute("provideOrgName", request.getParameter("provideOrgName"));
    } 
    if (request.getParameter("receiverName") != null && !request.getParameter("receiverName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.receiveScopeName like '%" + request.getParameter("receiverName") + "%'";
      request.setAttribute("receiverName", request.getParameter("receiverName"));
    } 
    if (request.getParameter("paperStatus") != null && !request.getParameter("paperStatus").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  bbb.paperStatus like '%" + request.getParameter("paperStatus") + "%'";
      request.setAttribute("paperStatus", request.getParameter("paperStatus"));
    } 
    String searchBeginDate = "", searchEndDate = "";
    if (request.getParameter("searchBeginDate") != null && 
      !request.getParameter("searchBeginDate").toString().equals("")) {
      searchBeginDate = request.getParameter("searchBeginDate");
      request.setAttribute("searchBeginDate", request.getParameter("searchBeginDate"));
    } 
    if (request.getParameter("searchEndDate") != null && 
      !request.getParameter("searchEndDate").toString().equals("")) {
      searchEndDate = request.getParameter("searchEndDate");
      request.setAttribute("searchEndDate", request.getParameter("searchEndDate"));
    } 
    String databaseType = SystemCommon.getDatabaseType();
    String searchDate = request.getParameter("searchDate");
    if (searchDate != null)
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and bbb.provideTime between '" + 
          searchBeginDate + "' and '" + searchEndDate + 
          " 23:59:59' ";
      } else {
        whereSql = String.valueOf(whereSql) + 
          " and bbb.provideTime between JSDB.FN_STRTODATE('" + 
          searchBeginDate + "','S') and JSDB.FN_STRTODATE('" + 
          searchEndDate + " 23:59:59', '') ";
      }  
    String searchBeginDateH = "", searchEndDateH = "";
    if (request.getParameter("searchBeginDateH") != null && 
      !request.getParameter("searchBeginDateH").toString().equals("")) {
      searchBeginDateH = request.getParameter("searchBeginDateH");
      request.setAttribute("searchBeginDateH", request.getParameter("searchBeginDateH"));
    } 
    if (request.getParameter("searchEndDateH") != null && 
      !request.getParameter("searchEndDateH").toString().equals("")) {
      searchEndDateH = request.getParameter("searchEndDateH");
      request.setAttribute("searchEndDateH", request.getParameter("searchEndDateH"));
    } 
    String searchDate2 = request.getParameter("searchDate2");
    if (searchDate2 != null)
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and bbb.backTime between '" + 
          searchBeginDateH + "' and '" + searchEndDateH + 
          " 23:59:59' ";
      } else {
        whereSql = String.valueOf(whereSql) + 
          " and bbb.backTime between JSDB.FN_STRTODATE('" + 
          searchBeginDateH + "','S') and JSDB.FN_STRTODATE('" + 
          searchEndDateH + " 23:59:59', '') ";
      }  
    String orderSql = "";
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    if (sortType == null)
      sortType = "desc"; 
    if (orderBy == null || orderBy.equals("null")) {
      orderSql = String.valueOf(orderSql) + " order by bbb.paperStatus,bbb.provideTime desc";
    } else if (orderBy.equals("informationName")) {
      orderSql = String.valueOf(orderSql) + " order by bbb.informationName " + sortType;
    } else if (orderBy.equals("documentNo")) {
      orderSql = String.valueOf(orderSql) + " order by bbb.documentNO " + sortType;
    } else if (orderBy.equals("paperStatus")) {
      orderSql = String.valueOf(orderSql) + " order by bbb.paperStatus " + sortType;
    } 
    whereSql = String.valueOf(whereSql) + orderSql;
    try {
      Page page = new Page("bbb.isoPaperId,bbb.informationId,bbb.informationName,bbb.documentNO,bbb.provideTime,bbb.provideNum,bbb.infromationVersion,bbb.receiveScopeName,bbb.paperStatus,bbb.backTime,bbb.backUserName,bbb.providePage", 
          fromwhere, 
          " where " + whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      System.out.println("------------------------------");
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,mypaper,receiverName,informationName,channelName,documentNo,infromationVersion,provideUserName,paperStatus");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private IsoPaperPO setIsoPaperPO(HttpServletRequest request) {
    IsoPaperPO po = new IsoPaperPO();
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String domainid = httpSession.getAttribute("domainId").toString();
    if (request.getParameter("informationId") != null && !request.getParameter("informationId").toString().equals(""))
      po.setInformationId(new Long(request.getParameter("informationId"))); 
    if (request.getParameter("documentNO") != null) {
      po.setDocumentNO(request.getParameter("documentNO"));
    } else {
      po.setDocumentNO("");
    } 
    if (request.getParameter("informationName") != null) {
      po.setInformationName(request.getParameter("informationName"));
    } else {
      po.setInformationName("");
    } 
    if (request.getParameter("infromationVersion") != null) {
      po.setInfromationVersion(request.getParameter("infromationVersion"));
    } else {
      po.setInfromationVersion("");
    } 
    if (request.getParameter("provideTime") != null)
      po.setProvideTime(new Date(request.getParameter("provideTime"))); 
    if (request.getParameter("provideNum") != null && !request.getParameter("provideNum").toString().equals("")) {
      po.setProvideNum(new Long(request.getParameter("provideNum")));
    } else {
      po.setProvideNum(new Long(0L));
    } 
    if (request.getParameter("providePage") != null && !request.getParameter("providePage").toString().equals("")) {
      po.setProvidePage(request.getParameter("providePage"));
    } else {
      po.setProvidePage("");
    } 
    if (request.getParameter("receiveUser") != null) {
      po.setReceiveUser(request.getParameter("receiveUser"));
    } else {
      po.setReceiveUser("");
    } 
    if (request.getParameter("receiveOrg") != null) {
      po.setReceiveOrg(request.getParameter("receiveOrg"));
    } else {
      po.setReceiveOrg("");
    } 
    if (request.getParameter("receiveGroup") != null) {
      po.setReceiveGroup(request.getParameter("receiveGroup"));
    } else {
      po.setReceiveGroup("");
    } 
    if (request.getParameter("receiveScopeId") != null) {
      po.setReceiveScopeId(request.getParameter("receiveScopeId"));
    } else {
      po.setReceiveScopeId("");
    } 
    if (request.getParameter("receiveScopeName") != null) {
      po.setReceiveScopeName(request.getParameter("receiveScopeName"));
    } else {
      po.setReceiveScopeName("");
    } 
    if (request.getParameter("channelName") != null && !request.getParameter("channelName").equals("")) {
      po.setChannelName(request.getParameter("channelName"));
    } else {
      po.setChannelName("");
    } 
    po.setProvideUserId(new Long(userId));
    po.setProvideUserName(userName);
    po.setProvideOrgId(new Long(orgId));
    po.setProvideOrgName(orgName);
    po.setDomainId(new Long(domainid));
    po.setPaperStatus("0");
    return po;
  }
  
  private void view(HttpServletRequest request) {
    IsoPaperPO po = new IsoPaperPO();
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String domainId = httpSession.getAttribute("domainId").toString();
    Date now = new Date();
    String nowString = now.toLocaleString();
    nowString = nowString.substring(0, nowString.indexOf(" "));
    String viewSQL = 
      " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationAuthor, aaa.informationVersion, aaa.informationIssueTime,  aaa.informationSummary, aaa.informationHead, aaa.informationType,  aaa.validBeginTime, aaa.validEndTime, aaa.informationCommonNum,  aaa.informationIssuer,aaa.informationValidType,aaa.titleColor, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime,bbb.channelNeedCheckup";
    String fromSQL = 
      " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
    String whereSQL = "";
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      whereSQL = " where aaa.domainId=" + domainId + " and aaa.informationStatus=0 and ( aaa.informationValidType = 0 or '" + 
        nowString + "' between aaa.validBeginTime and aaa.validEndTime ) ";
    } else {
      whereSQL = " where aaa.domainId=" + domainId + " and aaa.informationStatus=0 and ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
        nowString + "','S') between aaa.validBeginTime and aaa.validEndTime ) ";
    } 
    whereSQL = String.valueOf(whereSQL) + " and  bbb.afficheChannelStatus='2' and  isoDocStatus <>'2' and aaa.isoDocStatus<>'3'  ";
    if (request.getParameter("documentNO") != null && !request.getParameter("documentNO").toString().equals(""))
      whereSQL = String.valueOf(whereSQL) + " and aaa.documentNo like '%" + request.getParameter("documentNO") + "%' "; 
    list(request, viewSQL, fromSQL, whereSQL);
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
    List<Object[]> list = page.getResultList();
    if (list != null && list.size() > 0) {
      int infoSize = list.size();
      if (infoSize == 1) {
        Object[] obj = list.get(0);
        Object object1 = obj[0];
        Object object2 = obj[1];
        Object object3 = obj[18];
        Object object4 = obj[4];
        Object object5 = obj[16];
        httpServletRequest.setAttribute("informationId", 
            object1);
        httpServletRequest.setAttribute("informationName", 
            object2);
        httpServletRequest.setAttribute("documentNO", object3);
        httpServletRequest.setAttribute("informationVersion", 
            object4);
        httpServletRequest.setAttribute("channelName", object5);
        httpServletRequest.setAttribute("type", "1");
      } else {
        httpServletRequest.setAttribute("type", "2");
      } 
    } else {
      httpServletRequest.setAttribute("type", "0");
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("informationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (httpServletRequest.getParameter("search") == null) {
      if (httpServletRequest.getParameter("depart") != null) {
        httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,orderBy,sortType,depart,styleId,setAsso,listType,channelShowType,userDefine,checkdepart,isAffiche,afficheType,isoViewType");
      } else {
        httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,isAffiche,afficheType,isoViewType");
      } 
    } else if (httpServletRequest.getParameter("depart") != null) {
      httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,depart,styleId,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType");
    } else {
      httpServletRequest.setAttribute("pageParameters", "action,type,channelType,userChannelName,searchChannel,search,searchDate,searchBeginDate,searchEndDate,searchIssuerName,keywordType,key,title,subtitle,append,orderBy,sortType,setAsso,listType,channelShowType,userDefine,checkdepart,searchOrgName,searchOrg,isAffiche,afficheType,isoViewType");
    } 
  }
  
  public void setPaperPOStatus(HttpServletRequest request) {
    IsoDocBD bd = new IsoDocBD();
    HttpSession session = request.getSession(true);
    Object object1 = session.getAttribute("userId");
    Object object2 = session.getAttribute("userName");
    String paperId = request.getParameter("paperId");
    String status = request.getParameter("status");
    String[] arg = { status, (String)object1, (String)object2 };
    bd.updatePaperPO(paperId, arg);
  }
}
