package com.js.doc.doc.action;

import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.doc.doc.service.GovComeFileUnitBD;
import com.js.doc.doc.service.GovReceiveFileTypeBD;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.oa.form.ReceiveFile;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.search.client.SearchService;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovReceiveFileForRWSAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovReceiveFileActionForm govReceiveFileActionForm = (GovReceiveFileActionForm)actionForm;
    String action = request.getParameter("action");
    String status = "";
    if ("yuguidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "weiguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.yuguidang("DOC_receiveFile", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
      request.setAttribute("optMsg", "预归档成功！");
    } 
    if ("chexiaoguidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.quxiaoguidang("DOC_receiveFile", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
      request.setAttribute("optMsg", "驳回归档成功！");
    } 
    if ("guidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.guidang("DOC_receiveFile", recordId, request.getSession().getAttribute("userId").toString(), "");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
      request.setAttribute("optMsg", "归档成功！");
    } 
    if ("tongyiguidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.tongyiguidang("DOC_receiveFile", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
    } 
    if ("guidangbukejian".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yiguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.guidangbukejian("DOC_receiveFile", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
    } 
    if ("see".equals(action)) {
      see(request);
      if (request.getParameter("isgovExchange") != null && request.getParameter("isgovExchange").toString().equals("1"))
        dealExchangeAcc(request); 
      String sendFileTitle = (request.getParameter("sendFileTitle") != null) ? request.getParameter("sendFileTitle").toString() : "";
      request.setAttribute("receiveFileTitle", sendFileTitle);
      String byteNum = (request.getParameter("byteNum") != null) ? request.getParameter("byteNum").toString() : "";
      request.setAttribute("receiveFileFileNumber", byteNum);
      tranReceiveBase(request, govReceiveFileActionForm);
      String sendContinue = request.getParameter("sendContinue");
      if ("1".equals(sendContinue))
        setSeq(request, govReceiveFileActionForm); 
      String processId = (request.getParameter("processId") != null) ? request.getParameter("processId").toString() : "";
      String processName = (request.getParameter("processName") != null) ? request.getParameter("processName").toString() : "";
      String processType = (request.getParameter("processType") != null) ? request.getParameter("processType").toString() : "";
      String tableId = (request.getParameter("tableId") != null) ? request.getParameter("tableId").toString() : "";
      String remindField = (request.getParameter("remindField") != null) ? request.getParameter("remindField").toString() : "";
      SendFileBD sendFileBD = new SendFileBD();
      List<Object[]> tableInfoList = sendFileBD.getWfTableInfoByTableId(tableId);
      Object object = "";
      String url = "action=see&processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField;
      if (tableInfoList != null && tableInfoList.size() > 0) {
        Object[] tableInfoObj = tableInfoList.get(0);
        object = tableInfoObj[0];
      } 
      if (object.equals("收文表"))
        return actionMapping.findForward("add"); 
      ActionForward forward = new ActionForward();
      url = "/doc/doc/" + tableId + "_1_add.jsp?processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField;
      forward.setPath(url);
      return forward;
    } 
    if ("initNumber".equals(action)) {
      initNumber(request);
      return actionMapping.findForward("initnumber");
    } 
    if ("list".equals(action)) {
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("export".equals(action)) {
      export(request);
      return actionMapping.findForward("export");
    } 
    if ("listLoad".equals(action)) {
      String gd = (String)request.getAttribute("gd");
      ActionForward forward = new ActionForward();
      forward.setPath(listLoad(request, httpServletResponse));
      request.setAttribute("gd", gd);
      return forward;
    } 
    if ("delete".equals(action)) {
      Date beginDate = new Date();
      HttpSession session = request.getSession(true);
      Object object = session.getAttribute("domainId");
      String deleTitle = request.getParameter("deleTitle");
      delete(request);
      Date endDate = new Date();
      LogBD bd = new LogBD();
      bd.log(session.getAttribute("userId").toString(), 
          session.getAttribute("userName").toString(), 
          session.getAttribute("orgName").toString(), "oa_gw_sw", "", 
          beginDate, endDate, "3", deleTitle, 
          session.getAttribute("userIP").toString(), (String)object);
      return actionMapping.findForward("list");
    } 
    if ("update".equals(action)) {
      update(request, httpServletResponse);
      return actionMapping.findForward("close");
    } 
    if ("receiveAssociate".equals(action)) {
      receiveAssociate(request);
      return actionMapping.findForward("receiveAssociate");
    } 
    if ("iniSeqNum".equals(action)) {
      iniSeqNum(request);
      return actionMapping.findForward("iniSeqNum");
    } 
    if ("mailtransmit".equals(action)) {
      mailtransmit(request);
      ActionForward forward = new ActionForward();
      forward.setPath(listLoad(request, httpServletResponse));
      return forward;
    } 
    return actionMapping.findForward("list");
  }
  
  private void update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    (new ReceiveFile()).update(httpServletRequest);
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    String id = httpServletRequest.getParameter("id");
    (new ReceiveFileBD()).delete(id);
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.deleteIndex(id.toString(), "doc_receivefile");
    } 
    list(httpServletRequest);
  }
  
  private void initNumber(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    ReceiveFileBD bd = new ReceiveFileBD();
    String tmp = "";
    StringBuffer receiveFileYear = new StringBuffer();
    if (httpServletRequest.getParameter("year") != null && !"".equals(httpServletRequest.getParameter("year"))) {
      receiveFileYear.append(httpServletRequest.getParameter("year").toString());
    } else {
      receiveFileYear.append((new Date()).getYear() + 1900);
    } 
    tmp = bd.initNumber(httpServletRequest.getParameter("receiveFileType"), receiveFileYear.toString(), domainId);
    httpServletRequest.setAttribute("initnumber", tmp);
    String receiveFileType = httpServletRequest.getParameter("receiveFileType").toString();
    httpServletRequest.setAttribute("receiveFileType", receiveFileType);
    httpServletRequest.setAttribute("year", receiveFileYear.toString());
  }
  
  private void see(HttpServletRequest httpServeltRequest) {
    getReceiveFileType(httpServeltRequest);
    httpServeltRequest.setAttribute("yearItr", getYearItr());
    getComeFileUnit(httpServeltRequest);
    setMapInfo(httpServeltRequest);
  }
  
  private void tranReceiveBase(HttpServletRequest request, GovReceiveFileActionForm govReceiveFileActionForm) {
    String receiveFileSendFileUnit = (request.getParameter("receiveFileSendFileUnit") == null) ? "" : request.getParameter("receiveFileSendFileUnit").toString();
    String receiveFileSafetyGrade = (request.getParameter("receiveFileSafetyGrade") == null) ? "" : request.getParameter("receiveFileSafetyGrade").toString();
    String receiveFileQuantity = (request.getParameter("receiveFileQuantity") == null) ? "" : request.getParameter("receiveFileQuantity").toString();
    String field4 = (request.getParameter("field4") == null) ? "" : request.getParameter("field4").toString();
    String receiveFileType = (request.getParameter("receiveFileType") == null) ? "" : request.getParameter("receiveFileType").toString();
    String zjkyType = (request.getParameter("zjkyType") == null) ? "" : request.getParameter("zjkyType").toString();
    String zjkykeepTerm = (request.getParameter("zjkykeepTerm") == null) ? "" : request.getParameter("zjkykeepTerm").toString();
    String receiveFileDoComment = (request.getParameter("receiveFileDoComment") == null) ? "" : request.getParameter("receiveFileDoComment").toString();
    govReceiveFileActionForm.setReceiveFieldSelectMoreEmp((request.getParameter("receiveFieldSelectMoreEmp") == null) ? "" : request.getParameter("receiveFieldSelectMoreEmp"));
    if (receiveFileSendFileUnit != null && 
      !receiveFileSendFileUnit.equals(""))
      govReceiveFileActionForm.setReceiveFileSendFileUnit(receiveFileSendFileUnit); 
    if (receiveFileSafetyGrade != null && 
      !receiveFileSafetyGrade.equals(""))
      govReceiveFileActionForm.setReceiveFileSafetyGrade(receiveFileSafetyGrade); 
    if (receiveFileQuantity != null && !receiveFileQuantity.equals(""))
      govReceiveFileActionForm.setReceiveFileQuantity(receiveFileQuantity); 
    if (field4 != null && !field4.equals(""))
      govReceiveFileActionForm.setField4(field4); 
    if (receiveFileType != null && !receiveFileType.equals(""))
      govReceiveFileActionForm.setReceiveFileType(receiveFileType); 
    if (zjkyType != null && !zjkyType.equals(""))
      govReceiveFileActionForm.setZjkyType(zjkyType); 
    if (zjkykeepTerm != null && !zjkykeepTerm.equals(""))
      govReceiveFileActionForm.setZjkykeepTerm(zjkykeepTerm); 
    if (receiveFileDoComment != null && !receiveFileDoComment.equals(""))
      govReceiveFileActionForm.setReceiveFileDoComment(receiveFileDoComment); 
  }
  
  private void getReceiveFileType(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String scopeWhere = (new ManagerService()).getScopeFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "po.userId", "po.orgId", "po.groupId");
    scopeWhere = String.valueOf(scopeWhere) + "and po.domainId=" + domainId;
    GovReceiveFileTypeBD govReceiveFileTypeBD = new GovReceiveFileTypeBD();
    List grftList = govReceiveFileTypeBD.govReceiveFileTypeList(scopeWhere);
    httpServletRequest.setAttribute("grftList", grftList);
  }
  
  private void getComeFileUnit(HttpServletRequest httpServletRequest) {
    GovComeFileUnitBD gcfbd = new GovComeFileUnitBD();
    String domainId = (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "0" : httpServletRequest.getSession(true).getAttribute("domainId").toString();
    String wherePara = "1=1 and po.domainId=" + domainId;
    httpServletRequest.setAttribute("unitlist", gcfbd.getComeFileUnit(wherePara));
  }
  
  private Iterator getYearItr() {
    List<Integer> itr = new ArrayList();
    Calendar now = new GregorianCalendar();
    int year = now.get(1);
    for (int i = -10; i <= 10; i++)
      itr.add(new Integer(year + i)); 
    return itr.iterator();
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    getReceiveFileType(request);
    String wherePara = " po.createdEmp=" + httpSesison.getAttribute("userId") + " or (" + mbd.getRightFinalWhere(httpSesison.getAttribute("userId").toString(), httpSesison.getAttribute("orgId").toString(), "03*05*03", "po.createdOrg", "po.createdEmp") + ")";
    wherePara = String.valueOf(wherePara) + " or (" + mbd.getRightFinalWhere(httpSesison.getAttribute("userId").toString(), httpSesison.getAttribute("orgId").toString(), "03*05*02", "po.createdOrg", "po.createdEmp") + ")";
    int pageSize = 100;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String querySecret = request.getParameter("querySecret");
    String queryTransPersonName = request.getParameter("queryTransPersonName");
    String queryItem1 = request.getParameter("queryItem1");
    String queryItem2 = request.getParameter("queryItem2");
    String queryItem3 = "0";
    if (request.getParameter("queryItem3") != null)
      queryItem3 = request.getParameter("queryItem3").toString(); 
    String queryBeginDate1 = request.getParameter("queryBeginDate1");
    String queryEndDate1 = request.getParameter("queryEndDate1");
    String queryBeginDate2 = request.getParameter("queryBeginDate2");
    String queryEndDate2 = request.getParameter("queryEndDate2");
    String queryNumberCountBegin = request.getParameter("queryNumberCountBegin");
    String queryNumberCountEnd = request.getParameter("queryNumberCountEnd");
    String queryComeFileUnit = request.getParameter("queryComeFileUnit");
    String queryStatus = request.getParameter("queryStatus");
    String queryOrgName = request.getParameter("queryOrgName");
    String joinwhere = "1=1";
    String fromwhere = "com.js.doc.doc.po.GovReceiveFilePO po ,RWSWorkFlowStatusPO st ";
    sb.append(" and st.tableName='DOC_receivefile' and st.recordId=po.id ");
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.receiveFileFileNumber like '%").append(queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.receiveFileTitle like '%").append(
          queryTitle).append("%'"); 
    if (queryStatus != null && !"none".equals(queryStatus))
      sb.append(" and po.receiveFileStatus =").append(queryStatus); 
    if (querySecret != null && !"none".equals(querySecret))
      sb.append(" and po.receiveFileSafetyGrade = '").append(querySecret).append("'"); 
    int ii = 0;
    String databaseType = SystemCommon.getDatabaseType();
    if ("1".equals(queryItem1))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.receiveFileReceiveDate between '").append(queryBeginDate1).append(" 00:00:00").append("' and '").append(queryEndDate1).append(" 23:59:59").append("'").append(" or  po.receiveFileReceiveDate between '").append(queryEndDate1).append(" 00:00:00").append("' and '").append(queryBeginDate1).append(" 23:59:59").append("' )");
      } else {
        sb.append(" and ( po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryBeginDate1).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate1).append(" 23:59:59").append("','L')").append(" or  po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryEndDate1).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate1).append(" 23:59:59").append("','L') )");
      }  
    if ("1".equals(queryItem2)) {
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and (( po.receiveFileEndDate between '").append(queryBeginDate2).append(" 00:00:00").append("' and '").append(queryEndDate2).append(" 23:59:59").append("'").append(" or  po.receiveFileEndDate between '").append(queryEndDate2).append(" 00:00:00").append("' and '").append(queryBeginDate2).append(" 23:59:59").append("' )");
      } else {
        sb.append(" and (( po.receiveFileEndDate between JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 23:59:59").append("','L')").append(" or  po.receiveFileEndDate between JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 23:59:59").append("','L') )");
      } 
      if (ii == 1)
        if (databaseType.indexOf("mysql") >= 0) {
          sb.append(" or ((( po.receiveFileReceiveDate between '").append(queryBeginDate2).append(" 00:00:00").append("' and '").append(queryEndDate2).append(" 23:59:59").append("'").append(" or  po.receiveFileReceiveDate between '").append(queryEndDate2).append(" 00:00:00").append("' and '").append(queryBeginDate2).append(" 23:59:59").append("') and po.receiveFileEndDate is null ))");
        } else {
          sb.append(" or ((( po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 23:59:59").append("','L')").append(" or  po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 23:59:59").append("','L')) and po.receiveFileEndDate is null ))");
        }  
      sb.append(")");
    } 
    if (queryNumberCountBegin != null && !"".equals(queryNumberCountBegin))
      sb.append(" and po.receiveFileFileNoCount >=" + queryNumberCountBegin); 
    if (queryNumberCountEnd != null && !"".equals(queryNumberCountEnd))
      sb.append(" and po.receiveFileFileNoCount <= " + queryNumberCountEnd); 
    if (queryComeFileUnit != null && !"".equals(queryComeFileUnit))
      sb.append(" and po.receiveFileSendFileUnit  like '%" + queryComeFileUnit + "%'"); 
    if (request.getParameter("zjkySeq") != null && 
      !request.getParameter("zjkySeq").toString().equals(""))
      sb.append(" and po.zjkySeq  like '%" + request.getParameter("zjkySeq") + "%'"); 
    if (request.getParameter("seqId") != null && 
      !request.getParameter("seqId").toString().equals("")) {
      sb.append(" and ( po.seqId= " + request.getParameter("seqId") + " )");
    } else if (request.getParameter("seqType") != null && 
      !request.getParameter("seqType").toString().equals("")) {
      List<ReceiveFileSeqPO> seqList = (new ReceivedocumentBD()).getSeqPoListBySeqClass(
          request.getParameter("seqType"));
      String sqlStr = "";
      if (seqList != null && seqList.size() > 0)
        for (int i = 0; i < seqList.size(); i++) {
          ReceiveFileSeqPO po = seqList.get(i);
          sqlStr = String.valueOf(sqlStr) + po.getId() + ",";
        }  
      if (sqlStr != null && sqlStr.length() > 1) {
        sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
        sb.append(" and ( po.seqId in (" + sqlStr + " ) )");
      } 
    } 
    sb.append(" and (").append(wherePara).append(")");
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    if ("weiguidang".equals(status)) {
      sb.append(" and (not exists(select st.id from RWSWorkFlowStatusPO st where st.tableName='DOC_receivefile' and st.recordId=po.id) or  exists(select st2.id from com.js.doc.doc.po.RWSWorkFlowStatusPO st2 where st2.tableName='DOC_receivefile' and st2.recordId=po.id and st2.status=2)) ");
    } else if ("yuguidang".equals(status)) {
      sb.append(" and st.status = 0 ");
    } else if ("yiguidang".equals(status)) {
      sb.append(" and  st.status=1");
    } 
    if (request.getParameter("receiveType") != null && 
      !request.getParameter("receiveType").toString().equals("")) {
      String receiveType = request.getParameter("receiveType").toString();
      if (receiveType.equals("end")) {
        sb.append(" and ( ").append(" po.receiveFileStatus= '1' ").append(
            " )");
        request.setAttribute("receiveType", "end");
      } else {
        sb.append(" and ( ").append(" po.receiveFileStatus <> '1' ").append(
            " )");
        request.setAttribute("receiveType", "noend");
      } 
    } 
    try {
      Page page = new Page(" po.id,po.receiveFileFileNumber,po.receiveFileTitle,po.receiveFileIsEnd,po.receiveFileEndDate,po.receiveFileLink,po.receiveFileDoDepartNm,po.createdEmp,po.createdOrg,po.receiveFileFileNo,po.receiveFileSendFileUnit,po.receiveFileStatus,po.receiveFileReceiveDate,po.thirdDossier,po.field5,po.zjkySeq,po.tableId,st.yuguidang_time,st.guidang_time ", 
          fromwhere, 
          " where " + joinwhere + sb + " and po.domainId=" + domainId + " order by po.createdTime desc ,po.receiveFileFileNoCount desc,po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
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
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,issearch,queryItem2,queryItem3,queryBeginDate2,queryEndDate2,queryItem1,queryBeginDate1,queryEndDate1,queryNumber,querySecret,queryTransPersonName,queryTitle,queryStatus,queryOrgName,queryNumberCountBegin,queryNumberCountEnd,queryComeFileUnit,zbstatus,receiveFileTogetherDoDepartNm,receiveFileSendLeaderCheckNm,receiveFileSendLeaderReaderNm,seqId,seqType,zjkySeq,receiveType,status");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    List<Object[]> dRight = mbd.getRightScope(httpSesison.getAttribute("userId").toString(), "03*16*01");
    String dRightScopeType = "", dRightScopeScope = "", dOrgRange = "";
    if (dRight != null && dRight.size() > 0) {
      Object[] dObj = dRight.get(0);
      dRightScopeType = dObj[0].toString();
      dRightScopeScope = (dObj[1] == null) ? "" : dObj[1].toString();
      if ("2".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange("*" + httpSesison.getAttribute("orgId") + "*"); 
      if ("4".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange(dRightScopeScope); 
    } 
    request.setAttribute("dRightScopeType", dRightScopeType);
    request.setAttribute("dOrgRange", dOrgRange);
    getDefendRight(request);
  }
  
  private void export(HttpServletRequest request) {
    String databaseType = SystemCommon.getDatabaseType();
    HttpSession httpSesison = request.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    getReceiveFileType(request);
    String wherePara = " po.createdEmp=" + httpSesison.getAttribute("userId") + " or (" + mbd.getRightFinalWhere(httpSesison.getAttribute("userId").toString(), httpSesison.getAttribute("orgId").toString(), "03*05*03", "po.createdOrg", "po.createdEmp") + ")" + 
      " or (" + mbd.getRightFinalWhere(httpSesison.getAttribute("userId").toString(), httpSesison.getAttribute("orgId").toString(), "03*05*02", "po.createdOrg", "po.createdEmp") + ")";
    int pageSize = 1000000;
    int offset = 0;
    int currentPage = 1;
    StringBuffer sb = new StringBuffer("");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String querySecret = request.getParameter("querySecret");
    String queryTransPersonName = request.getParameter("queryTransPersonName");
    String queryItem1 = request.getParameter("queryItem1");
    String queryItem2 = request.getParameter("queryItem2");
    String queryItem3 = "0";
    if (request.getParameter("queryItem3") != null)
      queryItem3 = request.getParameter("queryItem3").toString(); 
    String queryBeginDate1 = request.getParameter("queryBeginDate1");
    String queryEndDate1 = request.getParameter("queryEndDate1");
    String queryBeginDate2 = request.getParameter("queryBeginDate2");
    String queryEndDate2 = request.getParameter("queryEndDate2");
    String queryNumberCountBegin = request.getParameter("queryNumberCountBegin");
    String queryNumberCountEnd = request.getParameter("queryNumberCountEnd");
    String queryComeFileUnit = request.getParameter("queryComeFileUnit");
    String queryStatus = request.getParameter("queryStatus");
    String queryOrgName = request.getParameter("queryOrgName");
    String joinwhere = "1=1";
    String fromwhere = "com.js.doc.doc.po.GovReceiveFilePO po ";
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.receiveFileFileNumber like '%").append(queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.receiveFileTitle like '%").append(
          queryTitle).append("%'"); 
    if (queryStatus != null && !"none".equals(queryStatus))
      sb.append(" and po.receiveFileStatus =").append(queryStatus); 
    if (querySecret != null && !"none".equals(querySecret))
      sb.append(" and po.receiveFileSafetyGrade = '").append(querySecret).append("'"); 
    if (queryTransPersonName != null && !"".equals(queryTransPersonName)) {
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setFormType(1);
      moduleVO.setId(3);
      String tableId = "0";
      List<AccessTableVO> list = (new WorkFlowBD()).getAccessTable(moduleVO);
      if (list != null && list.size() > 0)
        tableId = (new StringBuilder(String.valueOf(((AccessTableVO)list.get(0)).getId()))).toString(); 
      sb.append(" and emp.empName like '%").append(queryTransPersonName).append("%'");
      fromwhere = "com.js.doc.doc.po.GovReceiveFilePO po ,com.js.system.vo.usermanager.EmployeeVO emp , com.js.goa.workflow.po.wfDealWithCommentPO wfcomm join wfcomm.dealWith wfdw ";
      joinwhere = String.valueOf(joinwhere) + " and wfcomm.dealWithEmployeeId=emp.empId and wfdw.databaseRecordId=po.id and wfdw.databaseTableId=" + tableId;
    } 
    int ii = 0;
    if ("1".equals(queryItem1))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.receiveFileReceiveDate between '").append(queryBeginDate1).append(" 00:00:00").append("' and '").append(queryEndDate1).append(" 23:59:59").append("'").append(" or  po.receiveFileReceiveDate between '").append(queryEndDate1).append(" 00:00:00").append("' and '").append(queryBeginDate1).append(" 23:59:59").append("')");
      } else {
        sb.append(" and ( po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryBeginDate1).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate1).append(" 23:59:59").append("','L')").append(" or  po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryEndDate1).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate1).append(" 23:59:59").append("','L') )");
      }  
    if ("1".equals(queryItem2)) {
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and (( po.receiveFileEndDate between '").append(queryBeginDate2).append(" 00:00:00").append("' and '").append(queryEndDate2).append(" 23:59:59").append("'").append(" or  po.receiveFileEndDate between '").append(queryEndDate2).append(" 00:00:00").append("' and '").append(queryBeginDate2).append(" 23:59:59").append("' )");
      } else {
        sb.append(" and (( po.receiveFileEndDate between JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 23:59:59").append("','L')").append(" or  po.receiveFileEndDate between JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 23:59:59").append("','L') )");
      } 
      if (ii == 1)
        if (databaseType.indexOf("mysql") >= 0) {
          sb.append(" or ((( po.receiveFileReceiveDate between '").append(queryBeginDate2).append(" 00:00:00").append("' and '").append(queryEndDate2).append(" 23:59:59").append("'").append(" or  po.receiveFileReceiveDate between '").append(queryEndDate2).append(" 00:00:00").append("' and '").append(queryBeginDate2).append(" 23:59:59").append("') and po.receiveFileEndDate is null ))");
        } else {
          sb.append(" or ((( po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 23:59:59").append("','L')").append(" or  po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 23:59:59").append("','L')) and po.receiveFileEndDate is null ))");
        }  
      sb.append(")");
    } 
    if (queryNumberCountBegin != null && !"".equals(queryNumberCountBegin))
      sb.append(" and po.receiveFileFileNoCount >=" + queryNumberCountBegin); 
    if (queryNumberCountEnd != null && !"".equals(queryNumberCountEnd))
      sb.append(" and po.receiveFileFileNoCount <= " + queryNumberCountEnd); 
    if (queryComeFileUnit != null && !"".equals(queryComeFileUnit))
      sb.append(" and po.receiveFileSendFileUnit  like '%" + queryComeFileUnit + "%'"); 
    sb.append(" and (").append(wherePara).append(")");
    try {
      Page page = new Page(" po.id,po.receiveFileFileNumber,po.receiveFileTitle,po.receiveFileIsEnd,po.receiveFileEndDate,po.receiveFileLink,po.receiveFileDoDepartNm,po.createdEmp,po.createdOrg,po.receiveFileFileNo,po.receiveFileSendFileUnit,po.receiveFileStatus,po.receiveFileReceiveDate, po.thirdDossier,po.zjkySeq ", 
          fromwhere, 
          " where " + joinwhere + sb + " and po.domainId=" + domainId + " order by po.createdTime desc ,po.receiveFileFileNoCount desc,po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,issearch,queryItem2,queryItem3,queryBeginDate2,queryEndDate2,queryItem1,queryBeginDate1,queryEndDate1,queryNumber,querySecret,queryTransPersonName,queryTitle,queryStatus,queryOrgName,queryNumberCountBegin,queryNumberCountEnd,queryComeFileUnit,zbstatus,receiveFileTogetherDoDepartNm,receiveFileSendLeaderCheckNm,receiveFileSendLeaderReaderNm");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    List<Object[]> dRight = mbd.getRightScope(httpSesison.getAttribute("userId").toString(), "03*16*01");
    String dRightScopeType = "", dRightScopeScope = "", dOrgRange = "";
    if (dRight != null && dRight.size() > 0) {
      Object[] dObj = dRight.get(0);
      dRightScopeType = dObj[0].toString();
      dRightScopeScope = (dObj[1] == null) ? "" : dObj[1].toString();
      if ("2".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange("*" + httpSesison.getAttribute("orgId") + "*"); 
      if ("4".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange(dRightScopeScope); 
    } 
    request.setAttribute("dRightScopeType", dRightScopeType);
    request.setAttribute("dOrgRange", dOrgRange);
    getDefendRight(request);
  }
  
  private String listLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String pTableName = (httpServletRequest.getParameter("pTableName") == null) ? "" : httpServletRequest.getParameter("pTableName");
    String isPrint = (httpServletRequest.getParameter("isPrint") != null) ? httpServletRequest.getParameter("isPrint").toString() : "0";
    String tableId = (httpServletRequest.getParameter("tableId") != null) ? httpServletRequest.getParameter("tableId").toString() : "";
    Map workMap = wfcBD.getWorkInfoByTableID(httpServletRequest.getParameter("editId"), tableId);
    String activityName = (workMap.get("activityName") == null) ? "" : URLEncoder.encode(workMap.get("activityName").toString());
    String submitPerson = (workMap.get("submitPerson") == null) ? "" : URLEncoder.encode(workMap.get("submitPerson").toString());
    String processName = (workMap.get("processName") == null) ? "" : URLEncoder.encode(workMap.get("processName").toString());
    String standForUserName = (workMap.get("standForUserName") == null) ? "" : URLEncoder.encode(workMap.get("standForUserName").toString());
    String url = "/GovReceiveFileLoadForRWSAction.do?";
    String sendFileLink = String.valueOf(url) + "action=load&activityName=" + activityName + 
      "&submitPersonId=" + workMap.get("submitPersonId") + "&submitPerson=" + submitPerson + 
      "&work=" + workMap.get("work") + "&workType=" + workMap.get("workType") + "&activity=" + 
      workMap.get("activity") + "&table=" + workMap.get("tableId") + "&record=" + 
      httpServletRequest.getParameter("editId") + "&processName=" + processName + 
      "&workStatus=1&submitTime=" + workMap.get("submitTime") + "&processId=" + workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + "&isStandForWork=" + workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + "&standForUserName=" + standForUserName + "&submitPersonTime=" + workMap.get("submitTime") + "&isPrint=" + isPrint;
    if (httpServletRequest.getParameter("isEdit") != null)
      sendFileLink = String.valueOf(sendFileLink) + "&isEdit=1"; 
    return sendFileLink;
  }
  
  private void getDefendRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    List<Object[]> rightList = managerBD.getRightScope(userId, "03*05*02");
    if (rightList != null && rightList.size() > 0) {
      Object[] objRight = rightList.get(0);
      String scopeType = objRight[0].toString();
      String orgRange = "";
      if ("3".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange("*" + orgId + "*");
      } else if ("4".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange((String)objRight[1]);
      } 
      httpServletRequest.setAttribute("defendScopeType", scopeType);
      httpServletRequest.setAttribute("defendOrgRange", "," + orgRange + ",");
    } 
  }
  
  public void iniSeqNum(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String processId = request.getParameter("processId");
    ReceivedocumentBD bd = new ReceivedocumentBD();
    InformationBD informationBD1 = new InformationBD();
    List<String> groupList = new ArrayList();
    groupList = informationBD1.getAllGroupByUserId(userId);
    String readerWhere = " and (";
    readerWhere = String.valueOf(readerWhere) + "((po.receiveUser is null or po.receiveUser ='') and (po.receiveOrg is null or po.receiveOrg='') and ( po.receiveGroup is null or po.receiveGroup='') ) ";
    if (orgIdString != null && orgIdString.length() > 3) {
      String cStr = orgIdString.substring(1, orgIdString.length() - 1);
      cStr = cStr.replaceAll("\\$", ",");
      cStr = cStr.replaceAll(",,", ",");
      String[] gg1 = cStr.split(",");
      if (gg1 != null && gg1.length > 0)
        for (int i = 0; i < gg1.length; i++)
          readerWhere = String.valueOf(readerWhere) + " or po.receiveOrg like '%*" + 
            gg1[i] + "*%' ";  
    } 
    readerWhere = String.valueOf(readerWhere) + " or po.receiveUser like '%$" + userId + "$%' ";
    if (groupList != null && groupList.size() > 0)
      for (int i = 0; i < groupList.size(); i++) {
        String groupId = groupList.get(i);
        readerWhere = String.valueOf(readerWhere) + "  or  po.receiveGroup like '%@" + 
          groupId + "@%' ";
      }  
    readerWhere = String.valueOf(readerWhere) + " ) ";
    List<ReceiveFileSeqPO> list = bd.getRecSeqListByProceId(processId, readerWhere);
    List<String[]> seqList = new ArrayList();
    request.setAttribute("seqList", list);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        ReceiveFileSeqPO po = list.get(i);
        if (po != null && po.getId() != null) {
          String seqStr = getSeqStr(po);
          String[] obj = { (String)po.getId(), seqStr, po.getSeqNameR() };
          seqList.add(obj);
        } 
      }  
    request.setAttribute("seqList", seqList);
  }
  
  private String getSeqStr(ReceiveFileSeqPO po) {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: ldc ''
    //   4: astore_3
    //   5: aload_1
    //   6: invokevirtual getSeqfigR : ()Ljava/lang/Long;
    //   9: invokevirtual intValue : ()I
    //   12: istore #4
    //   14: iinc #4, 1
    //   17: aload_1
    //   18: invokevirtual getSeqModeR : ()Ljava/lang/String;
    //   21: astore #5
    //   23: aload_1
    //   24: invokevirtual getSeqNameR : ()Ljava/lang/String;
    //   27: astore #6
    //   29: new java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial <init> : ()V
    //   36: aload_1
    //   37: invokevirtual getSeqIsYearR : ()Ljava/lang/Long;
    //   40: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   43: invokevirtual toString : ()Ljava/lang/String;
    //   46: astore #7
    //   48: new java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial <init> : ()V
    //   55: aload_1
    //   56: invokevirtual getSeqIsName : ()Ljava/lang/Long;
    //   59: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   62: invokevirtual toString : ()Ljava/lang/String;
    //   65: astore #8
    //   67: aload_1
    //   68: invokevirtual getSeqBitNumR : ()Ljava/lang/Long;
    //   71: invokevirtual intValue : ()I
    //   74: istore #9
    //   76: new java/util/Date
    //   79: dup
    //   80: invokespecial <init> : ()V
    //   83: invokevirtual getYear : ()I
    //   86: sipush #1900
    //   89: iadd
    //   90: istore #10
    //   92: aload #7
    //   94: ifnull -> 163
    //   97: aload #7
    //   99: ldc '1'
    //   101: invokevirtual equals : (Ljava/lang/Object;)Z
    //   104: ifeq -> 163
    //   107: aload #5
    //   109: ldc_w '[年度]'
    //   112: invokevirtual indexOf : (Ljava/lang/String;)I
    //   115: istore #11
    //   117: iload #11
    //   119: iconst_m1
    //   120: if_icmpeq -> 163
    //   123: new java/lang/StringBuilder
    //   126: dup
    //   127: aload #5
    //   129: iconst_0
    //   130: iload #11
    //   132: invokevirtual substring : (II)Ljava/lang/String;
    //   135: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   138: invokespecial <init> : (Ljava/lang/String;)V
    //   141: iload #10
    //   143: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   146: aload #5
    //   148: iload #11
    //   150: iconst_4
    //   151: iadd
    //   152: invokevirtual substring : (I)Ljava/lang/String;
    //   155: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: invokevirtual toString : ()Ljava/lang/String;
    //   161: astore #5
    //   163: aload #8
    //   165: ifnull -> 235
    //   168: aload #8
    //   170: ldc '3'
    //   172: invokevirtual equals : (Ljava/lang/Object;)Z
    //   175: ifeq -> 235
    //   178: aload #5
    //   180: ldc_w '[流水号名称]'
    //   183: invokevirtual indexOf : (Ljava/lang/String;)I
    //   186: istore #11
    //   188: iload #11
    //   190: iconst_m1
    //   191: if_icmpeq -> 235
    //   194: new java/lang/StringBuilder
    //   197: dup
    //   198: aload #5
    //   200: iconst_0
    //   201: iload #11
    //   203: invokevirtual substring : (II)Ljava/lang/String;
    //   206: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   209: invokespecial <init> : (Ljava/lang/String;)V
    //   212: aload #6
    //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: aload #5
    //   219: iload #11
    //   221: bipush #7
    //   223: iadd
    //   224: invokevirtual substring : (I)Ljava/lang/String;
    //   227: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: invokevirtual toString : ()Ljava/lang/String;
    //   233: astore #5
    //   235: aload #5
    //   237: ldc_w '[顺序号]'
    //   240: invokevirtual indexOf : (Ljava/lang/String;)I
    //   243: istore #11
    //   245: iload #11
    //   247: iconst_m1
    //   248: if_icmpeq -> 388
    //   251: new java/lang/StringBuilder
    //   254: dup
    //   255: invokespecial <init> : ()V
    //   258: iload #4
    //   260: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   263: invokevirtual toString : ()Ljava/lang/String;
    //   266: astore_3
    //   267: aload_3
    //   268: invokevirtual length : ()I
    //   271: istore #12
    //   273: iload #9
    //   275: iload #12
    //   277: if_icmple -> 349
    //   280: ldc ''
    //   282: astore #13
    //   284: iconst_0
    //   285: istore #14
    //   287: goto -> 319
    //   290: new java/lang/StringBuilder
    //   293: dup
    //   294: aload #13
    //   296: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   299: invokespecial <init> : (Ljava/lang/String;)V
    //   302: ldc_w '0'
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: invokevirtual toString : ()Ljava/lang/String;
    //   311: astore #13
    //   313: iinc #2, 1
    //   316: iinc #14, 1
    //   319: iload #14
    //   321: iload #9
    //   323: iload #12
    //   325: isub
    //   326: if_icmplt -> 290
    //   329: new java/lang/StringBuilder
    //   332: dup
    //   333: aload #13
    //   335: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   338: invokespecial <init> : (Ljava/lang/String;)V
    //   341: aload_3
    //   342: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   345: invokevirtual toString : ()Ljava/lang/String;
    //   348: astore_3
    //   349: new java/lang/StringBuilder
    //   352: dup
    //   353: aload #5
    //   355: iconst_0
    //   356: iload #11
    //   358: invokevirtual substring : (II)Ljava/lang/String;
    //   361: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   364: invokespecial <init> : (Ljava/lang/String;)V
    //   367: aload_3
    //   368: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: aload #5
    //   373: iload #11
    //   375: iconst_5
    //   376: iadd
    //   377: invokevirtual substring : (I)Ljava/lang/String;
    //   380: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   383: invokevirtual toString : ()Ljava/lang/String;
    //   386: astore #5
    //   388: aload #5
    //   390: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #896	-> 0
    //   #897	-> 2
    //   #898	-> 5
    //   #899	-> 14
    //   #900	-> 17
    //   #901	-> 23
    //   #902	-> 29
    //   #903	-> 48
    //   #904	-> 67
    //   #905	-> 76
    //   #907	-> 92
    //   #908	-> 107
    //   #909	-> 117
    //   #910	-> 123
    //   #911	-> 146
    //   #910	-> 158
    //   #914	-> 163
    //   #915	-> 178
    //   #916	-> 188
    //   #917	-> 194
    //   #918	-> 217
    //   #917	-> 230
    //   #922	-> 235
    //   #923	-> 245
    //   #924	-> 251
    //   #925	-> 267
    //   #926	-> 273
    //   #927	-> 280
    //   #928	-> 284
    //   #929	-> 290
    //   #930	-> 313
    //   #928	-> 316
    //   #933	-> 329
    //   #936	-> 349
    //   #937	-> 371
    //   #936	-> 383
    //   #940	-> 388
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	391	0	this	Lcom/js/doc/doc/action/GovReceiveFileForRWSAction;
    //   0	391	1	po	Lcom/js/doc/doc/po/ReceiveFileSeqPO;
    //   2	389	2	zeroNum	I
    //   5	386	3	sendSeqfig	Ljava/lang/String;
    //   14	377	4	seqfig	I
    //   23	368	5	seqmodel	Ljava/lang/String;
    //   29	362	6	seqName	Ljava/lang/String;
    //   48	343	7	seqIsYear	Ljava/lang/String;
    //   67	324	8	seqIsName	Ljava/lang/String;
    //   76	315	9	seqbitNum	I
    //   92	299	10	nowYear	I
    //   117	46	11	nh	I
    //   188	47	11	naH	I
    //   245	146	11	xh	I
    //   273	115	12	dd	I
    //   284	65	13	ling	Ljava/lang/String;
    //   287	42	14	jj	I
  }
  
  public void setMapInfo(HttpServletRequest request) {
    ReceivedocumentBD rbd = new ReceivedocumentBD();
    Object[] rObj = rbd.getReceivedocumentBaseInfo();
    Map<Object, Object> mapinfo = new HashMap<Object, Object>();
    if (rObj != null) {
      if (rObj[1] != null) {
        String[] arrayOfString = rObj[1].toString().split(";");
        mapinfo.put("receiveFileType", arrayOfString);
      } else {
        mapinfo.put("receiveFileType", new Object[0]);
      } 
      if (rObj[2] != null) {
        String[] arrayOfString = rObj[2].toString().split(";");
        mapinfo.put("pigeonholeType", arrayOfString);
      } else {
        mapinfo.put("pigeonholeType", new Object[0]);
      } 
      if (rObj[3] != null) {
        String[] arrayOfString = rObj[3].toString().split(";");
        mapinfo.put("decumentKind", arrayOfString);
      } else {
        mapinfo.put("decumentKind", new Object[0]);
      } 
      if (rObj[4] != null) {
        String[] arrayOfString = rObj[4].toString().split(";");
        mapinfo.put("receiveSecretLevel", arrayOfString);
      } else {
        mapinfo.put("receiveSecretLevel", new Object[0]);
      } 
      if (rObj[5] != null) {
        String[] arrayOfString = rObj[5].toString().split(";");
        mapinfo.put("urgencyLevel", arrayOfString);
      } else {
        mapinfo.put("urgencyLevel", new Object[0]);
      } 
      if (rObj[6] != null) {
        String[] arrayOfString = rObj[6].toString().split(";");
        mapinfo.put("keepTerm", arrayOfString);
      } else {
        mapinfo.put("keepTerm", new Object[0]);
      } 
      if (rObj[8] != null) {
        String[] arrayOfString = rObj[8].toString().split(";");
        mapinfo.put("receiveDropDownSelect1", arrayOfString);
      } else {
        mapinfo.put("receiveDropDownSelect1", new Object[0]);
      } 
      if (rObj[9] != null) {
        String[] arrayOfString = rObj[9].toString().split(";");
        mapinfo.put("receiveDropDownSelect2", arrayOfString);
      } else {
        mapinfo.put("receiveDropDownSelect2", new Object[0]);
      } 
    } 
    request.setAttribute("mapinfo", mapinfo);
  }
  
  private void mailtransmit(HttpServletRequest request) {}
  
  private void setSeq(HttpServletRequest request, GovReceiveFileActionForm govReceiveFileActionForm) {
    String seqId = (request.getParameter("seqId") != null) ? 
      request.getParameter("seqId") : "";
    ReceivedocumentBD rbaseBd = new ReceivedocumentBD();
    ReceiveFileSeqPO po = rbaseBd.loadRecSeqPO(seqId);
    String zjkySeq = getSeqStr(po);
    govReceiveFileActionForm.setZjkySeq(zjkySeq);
    request.setAttribute("seqId", seqId);
  }
  
  private void receiveAssociate(HttpServletRequest request) {
    String receiveFileId = request.getParameter("receiveFileId");
    ReceiveFileBD bd = new ReceiveFileBD();
    List list = bd.getRecieveAssociateList(receiveFileId);
    request.setAttribute("mylist", list);
  }
  
  private void dealExchangeAcc(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String fileNames = "";
    if (request.getParameter("accessorySaveName") != null && !request.getParameter("accessorySaveName").toString().equals("") && !request.getParameter("accessorySaveName").toString().equals("null"))
      fileNames = request.getParameter("accessorySaveName").toString(); 
    if (request.getParameter("accessorySaveName1") != null && !request.getParameter("accessorySaveName1").toString().equals("") && !request.getParameter("accessorySaveName1").toString().equals("null"))
      if (fileNames.equals("")) {
        fileNames = request.getParameter("accessorySaveName1").toString();
      } else {
        fileNames = String.valueOf(fileNames) + "|" + request.getParameter("accessorySaveName1").toString();
      }  
    if (fileNames != null && !fileNames.equals("") && !fileNames.equals("null")) {
      String[] fileNameArr = fileNames.split("\\|");
      String localPath = request.getRealPath("upload");
      copyFileFromDocument(fileNameArr, httpSession, localPath);
    } 
  }
  
  private boolean copyFileFromDocument(String[] fileNames, HttpSession session, String localPath) {
    boolean rs = false;
    if (fileNames != null && fileNames.length > 0)
      try {
        Map sysMap = 
          SysSetupReader.getInstance().getSysSetupMap(session
            .getAttribute("domainId").toString());
        for (int i = 0; i < fileNames.length; i++)
          copyFile(String.valueOf(localPath) + "\\govexchange\\" + fileNames[i], 
              String.valueOf(localPath) + "\\govdocumentmanager\\" + fileNames[i]); 
      } catch (Exception ex) {
        ex.printStackTrace();
      }  
    return true;
  }
  
  public boolean copyFile(String srcName, String aimName) {
    FileInputStream from = null;
    FileOutputStream to = null;
    try {
      from = new FileInputStream(srcName);
      to = new FileOutputStream(aimName);
      byte[] buffer = new byte[4096];
      int pos;
      while ((pos = from.read(buffer)) != -1)
        to.write(buffer, 0, pos); 
      if (from != null)
        from.close(); 
      if (to != null)
        to.close(); 
      return true;
    } catch (Exception ex) {
      System.out.println("复制文件出错！" + ex);
      return false;
    } 
  }
}
