package com.js.doc.doc.action;

import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.doc.doc.service.GovComeFileUnitBD;
import com.js.doc.doc.service.GovReceiveFileTypeBD;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.service.gzkgToZeXin.DataPushToZeXin;
import com.js.oa.form.ReceiveFile;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.search.client.SearchService;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.sql.DataSource;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovReceiveFileAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovReceiveFileActionForm govReceiveFileActionForm = (GovReceiveFileActionForm)actionForm;
    String action = request.getParameter("action");
    if ("see".equals(action)) {
      see(request);
      if (request.getParameter("isgovExchange") != null && request.getParameter("isgovExchange").toString().equals("1"))
        dealExchangeAcc(request); 
      String sendFileTitle = (request.getParameter("sendFileTitle") != null) ? request.getParameter("sendFileTitle").toString() : "";
      String receiveFileSendFileUnit = (request.getParameter("receiveFileSendFileUnit") != null) ? request.getParameter("receiveFileSendFileUnit").toString() : "";
      request.setAttribute("receiveFileTitle", sendFileTitle);
      request.setAttribute("receiveFileSendFileUnit", receiveFileSendFileUnit);
      String byteNum = (request.getParameter("byteNum") != null) ? request.getParameter("byteNum").toString() : "";
      request.setAttribute("receiveFileFileNumber", byteNum);
      tranReceiveBase(request, govReceiveFileActionForm);
      String sendContinue = request.getParameter("sendContinue");
      if ("1".equals(sendContinue))
        setSeq(request, govReceiveFileActionForm); 
      String fromSendFile = (request.getParameter("fromSendFile") != null) ? request.getParameter("fromSendFile").toString() : "";
      String processId = (request.getParameter("processId") != null) ? request.getParameter("processId").toString() : "";
      String processName = (request.getParameter("processName") != null) ? request.getParameter("processName").toString() : "";
      String processType = (request.getParameter("processType") != null) ? request.getParameter("processType").toString() : "";
      String tableId = (request.getParameter("tableId") != null) ? request.getParameter("tableId").toString() : "";
      String editId = (request.getParameter("editId") != null) ? request.getParameter("editId").toString() : "";
      String remindField = (request.getParameter("remindField") != null) ? request.getParameter("remindField").toString() : "";
      SendFileBD sendFileBD = new SendFileBD();
      List<Object[]> tableInfoList = sendFileBD.getWfTableInfoByTableId(tableId);
      Object object = "";
      String url = "action=see&processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField;
      if (tableInfoList != null && tableInfoList.size() > 0) {
        Object[] tableInfoObj = tableInfoList.get(0);
        object = tableInfoObj[0];
      } 
      if (!"".equals(editId) && !"1".equals(fromSendFile)) {
        request.setAttribute("receiveFileSendFileUnitId", editId);
        load(request, govReceiveFileActionForm);
      } 
      if (object.equals("收文表")) {
        Object object1 = request.getAttribute("accessory1");
        if (object1 == null || "".equals(object1) || "null".equals(object1)) {
          object1 = (request.getParameter("accessoryName1") == null) ? "" : request.getParameter("accessoryName1");
          String accessorySaveName1 = (request.getParameter("accessorySaveName1") == null) ? "" : request.getParameter("accessorySaveName1");
          request.setAttribute("accessory1", object1);
          request.setAttribute("accessorySave1", accessorySaveName1);
        } 
        Object object2 = request.getAttribute("accessory2");
        if (object2 == null || "".equals(object2) || "null".equals(object2)) {
          object2 = (request.getParameter("accessoryName") == null) ? "" : request.getParameter("accessoryName");
          String accessorySaveName = (request.getParameter("accessorySaveName") == null) ? "" : request.getParameter("accessorySaveName");
          request.setAttribute("accessory2", object2);
          request.setAttribute("accessorySave2", accessorySaveName);
        } 
        return actionMapping.findForward("add");
      } 
      ActionForward forward = new ActionForward();
      url = "/doc/doc/" + tableId + "_1_add.jsp?processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField;
      forward.setPath(url);
      return forward;
    } 
    if ("draft".equals(action)) {
      draft(request);
      String processId = request.getParameter("processId");
      String processName = request.getParameter("processName");
      String processType = request.getParameter("processType");
      String tableId = request.getParameter("tableId");
      String editId = (request.getAttribute("editId") == null) ? "" : request.getAttribute("editId").toString();
      String receiveFileSendFileUnit = request.getParameter("receiveFileSendFileUnit");
      String url = "/jsoa/GovReceiveFileAction.do?action=see&processId=" + processId + "&processName=" + processName + "&processType=1&tableId=" + tableId + "&editId=" + editId + "&receiveFileSendFileUnit=" + receiveFileSendFileUnit + "&tipRemind=1";
      request.setAttribute("url", url);
      return actionMapping.findForward("sclose");
    } 
    if ("endFlow".equals(action)) {
      String recordId = request.getParameter("endRecordId");
      String tableId = request.getParameter("tableId");
      (new ProcessBD()).endFlowInstance(tableId, recordId);
      setTransactStatus(recordId);
      action = "list";
    } 
    if ("initNumber".equals(action)) {
      initNumber(request);
      return actionMapping.findForward("initnumber");
    } 
    if ("list".equals(action)) {
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("mylist".equals(action)) {
      mylist(request);
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
    if ("pushData".equals(action)) {
      String sendFileId = request.getParameter("sendFileId");
      DataPushToZeXin zexin = new DataPushToZeXin();
      String path = request.getSession().getServletContext().getRealPath("");
      zexin.receiveFilePush(sendFileId, path);
      list(request);
      return actionMapping.findForward("list");
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
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryName = request.getParameter("queryName");
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
    String field4 = request.getParameter("field4");
    String joinwhere = "1=1";
    String fromwhere = "com.js.doc.doc.po.GovReceiveFilePO po ";
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.receiveFileFileNumber like '%").append(queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.receiveFileTitle like '%").append(
          queryTitle).append("%'"); 
    if (queryStatus != null && !"none".equals(queryStatus))
      sb.append(" and po.receiveFileStatus =").append(queryStatus); 
    if (field4 != null && !"none".equals(field4))
      sb.append(" and po.field4 ='").append(field4).append("'"); 
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
    if (queryName != null && !"".equals(queryName))
      sb.append(" and poo.workFlowProcessName like '%" + queryName + "%' "); 
    String order = "";
    if ("ynnt".equals(SystemCommon.getCustomerName()))
      order = " po.updateTimeStr desc,"; 
    try {
      Page page = new Page(" po.id,po.receiveFileFileNumber,po.receiveFileTitle,po.receiveFileIsEnd,po.receiveFileEndDate,po.receiveFileLink,po.receiveFileDoDepartNm,po.createdEmp,po.createdOrg,po.receiveFileFileNo,po.receiveFileSendFileUnit,po.receiveFileStatus,po.receiveFileReceiveDate,po.thirdDossier,po.field5,po.zjkySeq,po.tableId,po.field19,po.field20 ,poo.workFlowProcessName,po.receiveSyncForward,po.field6 ", 


          
          String.valueOf(fromwhere) + ",com.js.oa.jsflow.po.WFWorkFlowProcessPO poo", 
          " where " + 
          joinwhere + sb + " and po.processId=poo.wfWorkFlowProcessId and (po.isDraf!='1' or po.isDraf is null) and po.tableId = poo.accessDatabaseId and po.domainId=" + domainId + 
          " and po.receiveFileStatus<>-2 and (po.isSubFlow<>1 or po.isSubFlow is null) order by " + order + "po.createdTime desc ,po.receiveFileFileNoCount desc,po.id desc ");
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
      request.setAttribute("pageParameters", "action,issearch,queryItem2,queryItem3,queryBeginDate2,queryEndDate2,queryItem1,queryBeginDate1,queryEndDate1,queryNumber,querySecret,queryTransPersonName,queryTitle,queryStatus,queryOrgName,queryNumberCountBegin,queryNumberCountEnd,queryComeFileUnit,zbstatus,receiveFileTogetherDoDepartNm,receiveFileSendLeaderCheckNm,receiveFileSendLeaderReaderNm,seqId,seqType,zjkySeq,receiveType");
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
  
  private void mylist(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    getReceiveFileType(request);
    String wherePara = " po.createdEmp=" + httpSesison.getAttribute("userId") + " or pooo.wfCurEmployeeId = " + httpSesison.getAttribute("userId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryName = request.getParameter("queryName");
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
    if (queryName != null && !"".equals(queryName))
      sb.append(" and poo.workFlowProcessName like '%" + queryName + "%' "); 
    try {
      Page page = new Page(" po.id,po.receiveFileFileNumber,po.receiveFileTitle,po.receiveFileIsEnd,po.receiveFileEndDate,po.receiveFileLink,po.receiveFileDoDepartNm,po.createdEmp,po.createdOrg,po.receiveFileFileNo,po.receiveFileSendFileUnit,po.receiveFileStatus,po.receiveFileReceiveDate,po.thirdDossier,po.field5,po.zjkySeq,po.tableId,po.field19,po.field20 ,poo.workFlowProcessName,po.receiveSyncForward,po.field6 ", 


          
          String.valueOf(fromwhere) + ",com.js.oa.jsflow.po.WFWorkFlowProcessPO poo,com.js.oa.jsflow.po.WFWorkPO pooo", 
          " where " + joinwhere + sb + " and po.processId=poo.wfWorkFlowProcessId and po.id = pooo.workRecordId and (po.isDraf!='1' or po.isDraf is null) and po.tableId = poo.accessDatabaseId and po.domainId=" + domainId + " and po.receiveFileStatus<>-2 " + 
          "group by po.id order by po.createdTime desc ,po.receiveFileFileNoCount desc,po.id desc ");
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
      request.setAttribute("pageParameters", "action,issearch,queryItem2,queryItem3,queryBeginDate2,queryEndDate2,queryItem1,queryBeginDate1,queryEndDate1,queryNumber,querySecret,queryTransPersonName,queryTitle,queryStatus,queryOrgName,queryNumberCountBegin,queryNumberCountEnd,queryComeFileUnit,zbstatus,receiveFileTogetherDoDepartNm,receiveFileSendLeaderCheckNm,receiveFileSendLeaderReaderNm,seqId,seqType,zjkySeq,receiveType");
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
    String url = "/GovReceiveFileLoadAction.do?";
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
    //   0: invokestatic getInstance : ()Ljava/util/Calendar;
    //   3: iconst_1
    //   4: invokevirtual get : (I)I
    //   7: istore_2
    //   8: aload_1
    //   9: invokevirtual getSeqCurYear : ()Ljava/lang/String;
    //   12: ifnonnull -> 19
    //   15: iload_2
    //   16: goto -> 26
    //   19: aload_1
    //   20: invokevirtual getSeqCurYear : ()Ljava/lang/String;
    //   23: invokestatic parseInt : (Ljava/lang/String;)I
    //   26: istore_3
    //   27: ldc '1'
    //   29: aload_1
    //   30: invokevirtual getRepeatOfYear : ()Ljava/lang/String;
    //   33: invokevirtual equals : (Ljava/lang/Object;)Z
    //   36: ifeq -> 86
    //   39: iload_2
    //   40: iload_3
    //   41: if_icmpeq -> 86
    //   44: aload_1
    //   45: invokevirtual getSeqInitValueR : ()Ljava/lang/Long;
    //   48: invokevirtual intValue : ()I
    //   51: istore #4
    //   53: iinc #4, -1
    //   56: aload_1
    //   57: iload #4
    //   59: i2l
    //   60: invokestatic valueOf : (J)Ljava/lang/Long;
    //   63: invokevirtual setSeqfigR : (Ljava/lang/Long;)V
    //   66: aload_1
    //   67: iload_2
    //   68: invokestatic valueOf : (I)Ljava/lang/String;
    //   71: invokevirtual setSeqCurYear : (Ljava/lang/String;)V
    //   74: new com/js/doc/doc/service/ReceivedocumentBD
    //   77: dup
    //   78: invokespecial <init> : ()V
    //   81: aload_1
    //   82: invokevirtual updateRecSeqPO : (Lcom/js/doc/doc/po/ReceiveFileSeqPO;)Ljava/lang/String;
    //   85: pop
    //   86: iconst_0
    //   87: istore #4
    //   89: ldc ''
    //   91: astore #5
    //   93: aload_1
    //   94: invokevirtual getSeqfigR : ()Ljava/lang/Long;
    //   97: invokevirtual intValue : ()I
    //   100: istore #6
    //   102: iinc #6, 1
    //   105: aload_1
    //   106: invokevirtual getSeqModeR : ()Ljava/lang/String;
    //   109: astore #7
    //   111: aload_1
    //   112: invokevirtual getSeqNameR : ()Ljava/lang/String;
    //   115: astore #8
    //   117: new java/lang/StringBuilder
    //   120: dup
    //   121: invokespecial <init> : ()V
    //   124: aload_1
    //   125: invokevirtual getSeqIsYearR : ()Ljava/lang/Long;
    //   128: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   131: invokevirtual toString : ()Ljava/lang/String;
    //   134: astore #9
    //   136: new java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial <init> : ()V
    //   143: aload_1
    //   144: invokevirtual getSeqIsName : ()Ljava/lang/Long;
    //   147: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   150: invokevirtual toString : ()Ljava/lang/String;
    //   153: astore #10
    //   155: aload_1
    //   156: invokevirtual getSeqBitNumR : ()Ljava/lang/Long;
    //   159: invokevirtual intValue : ()I
    //   162: istore #11
    //   164: aload #9
    //   166: ifnull -> 234
    //   169: aload #9
    //   171: ldc '1'
    //   173: invokevirtual equals : (Ljava/lang/Object;)Z
    //   176: ifeq -> 234
    //   179: aload #7
    //   181: ldc_w '[年度]'
    //   184: invokevirtual indexOf : (Ljava/lang/String;)I
    //   187: istore #12
    //   189: iload #12
    //   191: iconst_m1
    //   192: if_icmpeq -> 234
    //   195: new java/lang/StringBuilder
    //   198: dup
    //   199: aload #7
    //   201: iconst_0
    //   202: iload #12
    //   204: invokevirtual substring : (II)Ljava/lang/String;
    //   207: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   210: invokespecial <init> : (Ljava/lang/String;)V
    //   213: iload_2
    //   214: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   217: aload #7
    //   219: iload #12
    //   221: iconst_4
    //   222: iadd
    //   223: invokevirtual substring : (I)Ljava/lang/String;
    //   226: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: invokevirtual toString : ()Ljava/lang/String;
    //   232: astore #7
    //   234: aload #10
    //   236: ifnull -> 307
    //   239: aload #10
    //   241: ldc_w '3'
    //   244: invokevirtual equals : (Ljava/lang/Object;)Z
    //   247: ifeq -> 307
    //   250: aload #7
    //   252: ldc_w '[流水号名称]'
    //   255: invokevirtual indexOf : (Ljava/lang/String;)I
    //   258: istore #12
    //   260: iload #12
    //   262: iconst_m1
    //   263: if_icmpeq -> 307
    //   266: new java/lang/StringBuilder
    //   269: dup
    //   270: aload #7
    //   272: iconst_0
    //   273: iload #12
    //   275: invokevirtual substring : (II)Ljava/lang/String;
    //   278: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   281: invokespecial <init> : (Ljava/lang/String;)V
    //   284: aload #8
    //   286: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   289: aload #7
    //   291: iload #12
    //   293: bipush #7
    //   295: iadd
    //   296: invokevirtual substring : (I)Ljava/lang/String;
    //   299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: invokevirtual toString : ()Ljava/lang/String;
    //   305: astore #7
    //   307: aload #7
    //   309: ldc_w '[顺序号]'
    //   312: invokevirtual indexOf : (Ljava/lang/String;)I
    //   315: istore #12
    //   317: iload #12
    //   319: iconst_m1
    //   320: if_icmpeq -> 465
    //   323: new java/lang/StringBuilder
    //   326: dup
    //   327: invokespecial <init> : ()V
    //   330: iload #6
    //   332: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   335: invokevirtual toString : ()Ljava/lang/String;
    //   338: astore #5
    //   340: aload #5
    //   342: invokevirtual length : ()I
    //   345: istore #13
    //   347: iload #11
    //   349: iload #13
    //   351: if_icmple -> 425
    //   354: ldc ''
    //   356: astore #14
    //   358: iconst_0
    //   359: istore #15
    //   361: goto -> 393
    //   364: new java/lang/StringBuilder
    //   367: dup
    //   368: aload #14
    //   370: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   373: invokespecial <init> : (Ljava/lang/String;)V
    //   376: ldc_w '0'
    //   379: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   382: invokevirtual toString : ()Ljava/lang/String;
    //   385: astore #14
    //   387: iinc #4, 1
    //   390: iinc #15, 1
    //   393: iload #15
    //   395: iload #11
    //   397: iload #13
    //   399: isub
    //   400: if_icmplt -> 364
    //   403: new java/lang/StringBuilder
    //   406: dup
    //   407: aload #14
    //   409: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   412: invokespecial <init> : (Ljava/lang/String;)V
    //   415: aload #5
    //   417: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   420: invokevirtual toString : ()Ljava/lang/String;
    //   423: astore #5
    //   425: new java/lang/StringBuilder
    //   428: dup
    //   429: aload #7
    //   431: iconst_0
    //   432: iload #12
    //   434: invokevirtual substring : (II)Ljava/lang/String;
    //   437: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   440: invokespecial <init> : (Ljava/lang/String;)V
    //   443: aload #5
    //   445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: aload #7
    //   450: iload #12
    //   452: iconst_5
    //   453: iadd
    //   454: invokevirtual substring : (I)Ljava/lang/String;
    //   457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: invokevirtual toString : ()Ljava/lang/String;
    //   463: astore #7
    //   465: aload #7
    //   467: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #1140	-> 0
    //   #1141	-> 8
    //   #1143	-> 27
    //   #1146	-> 44
    //   #1147	-> 53
    //   #1148	-> 56
    //   #1149	-> 66
    //   #1150	-> 74
    //   #1153	-> 86
    //   #1154	-> 89
    //   #1155	-> 93
    //   #1156	-> 102
    //   #1157	-> 105
    //   #1158	-> 111
    //   #1159	-> 117
    //   #1160	-> 136
    //   #1161	-> 155
    //   #1167	-> 164
    //   #1168	-> 179
    //   #1169	-> 189
    //   #1170	-> 195
    //   #1171	-> 217
    //   #1170	-> 229
    //   #1174	-> 234
    //   #1175	-> 250
    //   #1176	-> 260
    //   #1177	-> 266
    //   #1178	-> 289
    //   #1177	-> 302
    //   #1182	-> 307
    //   #1183	-> 317
    //   #1184	-> 323
    //   #1185	-> 340
    //   #1186	-> 347
    //   #1187	-> 354
    //   #1188	-> 358
    //   #1189	-> 364
    //   #1190	-> 387
    //   #1188	-> 390
    //   #1193	-> 403
    //   #1196	-> 425
    //   #1197	-> 448
    //   #1196	-> 460
    //   #1200	-> 465
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	468	0	this	Lcom/js/doc/doc/action/GovReceiveFileAction;
    //   0	468	1	po	Lcom/js/doc/doc/po/ReceiveFileSeqPO;
    //   8	460	2	nowYear	I
    //   27	441	3	seqYear	I
    //   53	33	4	seqInitValueR	I
    //   89	379	4	zeroNum	I
    //   93	375	5	sendSeqfig	Ljava/lang/String;
    //   102	366	6	seqfig	I
    //   111	357	7	seqmodel	Ljava/lang/String;
    //   117	351	8	seqName	Ljava/lang/String;
    //   136	332	9	seqIsYear	Ljava/lang/String;
    //   155	313	10	seqIsName	Ljava/lang/String;
    //   164	304	11	seqbitNum	I
    //   189	45	12	nh	I
    //   260	47	12	naH	I
    //   317	151	12	xh	I
    //   347	118	13	dd	I
    //   358	67	14	ling	Ljava/lang/String;
    //   361	42	15	jj	I
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
  
  public void setTransactStatus(String id) {
    DataSource ds = null;
    Connection conn = null;
    Statement stmt = null;
    try {
      ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      int rs = stmt.executeUpdate("update doc_receivefile set RECEIVEFILE_STATUS = '1' where RECEIVEFILE_ID=" + id);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
  }
  
  private void load(HttpServletRequest httpServletRequest, GovReceiveFileActionForm govReceiveFileActionForm) {
    ReceiveFileBD receiveFileBD = new ReceiveFileBD();
    WorkflowCommon workflowCommon = new WorkflowCommon();
    int workStatus = (httpServletRequest.getParameter("workStatus") == null) ? 100 : Integer.parseInt(httpServletRequest.getParameter("workStatus"));
    String curModifyField = "";
    if (workStatus == 0 || workStatus == 2)
      curModifyField = workflowCommon.getCurActivityWriteField(httpServletRequest); 
    httpServletRequest.setAttribute("curModifyField", curModifyField);
    getReceiveFileType(httpServletRequest);
    getComeFileUnit(httpServletRequest);
    Map commFieldMap = workflowCommon.getCurActivityCommField(httpServletRequest);
    String curCommField = "", curPassRoundCommField = "";
    if (commFieldMap != null) {
      if (commFieldMap.get("actiCommField") != null && 
        !commFieldMap.get("actiCommField").toString().equals("") && 
        !commFieldMap.get("actiCommField").toString().equals("-1") && 
        !commFieldMap.get("actiCommField").toString().toUpperCase().equals("NULL"))
        curCommField = commFieldMap.get("actiCommField").toString(); 
      if (commFieldMap.get("passRoundCommField") != null && 
        !commFieldMap.get("passRoundCommField").toString().equals("") && 
        !commFieldMap.get("passRoundCommField").toString().equals("-1") && 
        !commFieldMap.get("passRoundCommField").toString().toUpperCase().equals("NULL"))
        curPassRoundCommField = commFieldMap.get("passRoundCommField").toString(); 
    } 
    httpServletRequest.setAttribute("curCommField", curCommField);
    httpServletRequest.setAttribute("curPassRoundCommField", curPassRoundCommField);
    httpServletRequest.setAttribute("curProcCommField", workflowCommon.getCurProcCommField(httpServletRequest));
    httpServletRequest.setAttribute("yearItr", getYearItr());
    GovReceiveFilePO po = receiveFileBD.load((httpServletRequest.getParameter("record") == null) ? httpServletRequest.getParameter("editId") : httpServletRequest.getParameter("record"));
    if (po.getSendFileId() != null) {
      httpServletRequest.setAttribute("sendFileId", po.getSendFileId());
      httpServletRequest.setAttribute("sendFileLink", po.getSendFileLink());
      httpServletRequest.setAttribute("sendFileTitle", po.getSendFileTitle());
    } 
    if (po.getExchangeFileId() != null) {
      httpServletRequest.setAttribute("exchangeFileId", po.getExchangeFileId());
      httpServletRequest.setAttribute("exchangeFileLink", po.getExchangeFileLink());
      httpServletRequest.setAttribute("exchangeFileTitle", po.getExchangeFileTitle());
    } 
    httpServletRequest.setAttribute("lwh", po.getLwh());
    govReceiveFileActionForm.setReceiveFileDoComment(po.getReceiveFileDoComment());
    httpServletRequest.setAttribute("receiveFileDoComment", po.getReceiveFileDoComment());
    govReceiveFileActionForm.setReceiveFileDoDepart((new StringBuilder(String.valueOf(po.getReceiveFileDoDepart()))).toString());
    httpServletRequest.setAttribute("receiveFileDoDepart", po.getReceiveFileDoDepart());
    govReceiveFileActionForm.setReceiveFileDoDepartNm(po.getReceiveFileDoDepartNm());
    httpServletRequest.setAttribute("receiveFileDoDepartNm", po.getReceiveFileDoDepartNm());
    govReceiveFileActionForm.setReceiveFileFileNo((new StringBuilder(String.valueOf(po.getReceiveFileFileNo()))).toString());
    httpServletRequest.setAttribute("receiveFileFileNo", po.getReceiveFileFileNo());
    govReceiveFileActionForm.setReceiveFileFileNumber(po.getReceiveFileFileNumber());
    httpServletRequest.setAttribute("receiveFileFileNumber", po.getReceiveFileFileNumber());
    govReceiveFileActionForm.setReceiveFileLeaderComment(po.getReceiveFileLeaderComment());
    httpServletRequest.setAttribute("receiveFileLeaderComment", po.getReceiveFileLeaderComment());
    govReceiveFileActionForm.setReceiveFileLink(po.getReceiveFileLink());
    httpServletRequest.setAttribute("receiveFileLink", po.getReceiveFileLink());
    govReceiveFileActionForm.setReceiveFileMemo(po.getReceiveFileMemo());
    httpServletRequest.setAttribute("ReceiveFileMemo", po.getReceiveFileMemo());
    govReceiveFileActionForm.setReceiveFileQuantity((new StringBuilder(String.valueOf(po.getReceiveFileQuantity()))).toString());
    httpServletRequest.setAttribute("receiveFileQuantity", (new StringBuilder(String.valueOf(po.getReceiveFileQuantity()))).toString());
    govReceiveFileActionForm.setReceiveFileSafetyGrade(po.getReceiveFileSafetyGrade());
    httpServletRequest.setAttribute("receiveFileSafetyGrade", po.getReceiveFileSafetyGrade());
    govReceiveFileActionForm.setReceiveFileSendFileUnit(po.getReceiveFileSendFileUnit());
    httpServletRequest.setAttribute("receiveFileSendFileUnit", po.getReceiveFileSendFileUnit());
    govReceiveFileActionForm.setReceiveFileSendLeaderCheck(po.getReceiveFileSendLeaderCheck());
    httpServletRequest.setAttribute("receiveFileSendLeaderCheck", po.getReceiveFileSendLeaderCheck());
    govReceiveFileActionForm.setReceiveFileSendLeaderCheckNm(po.getReceiveFileSendLeaderCheckNm());
    httpServletRequest.setAttribute("receiveFileSendLeaderCheckNm", po.getReceiveFileSendLeaderCheckNm());
    govReceiveFileActionForm.setReceiveFileSendLeaderRead(po.getReceiveFileSendLeaderRead());
    httpServletRequest.setAttribute("receiveFileSendLeaderRead", po.getReceiveFileSendLeaderRead());
    govReceiveFileActionForm.setReceiveFileSendLeaderReaderNm(po.getReceiveFileSendLeaderReaderNm());
    httpServletRequest.setAttribute("receiveFileSendLeaderReaderNm", po.getReceiveFileSendLeaderReaderNm());
    govReceiveFileActionForm.setReceiveFileSettleComment(po.getReceiveFileSettleComment());
    httpServletRequest.setAttribute("receiveFileSettleComment", po.getReceiveFileSettleComment());
    govReceiveFileActionForm.setReceiveFileTitle(po.getReceiveFileTitle());
    httpServletRequest.setAttribute("receiveFileTitle", po.getReceiveFileTitle());
    govReceiveFileActionForm.setReceiveFileTogetherDoDepart(po.getReceiveFileTogetherDoDepart());
    httpServletRequest.setAttribute("receiveFileTogetherDoDepart", po.getReceiveFileTogetherDoDepart());
    govReceiveFileActionForm.setReceiveFileTogetherDoDepartNm(po.getReceiveFileTogetherDoDepartNm());
    httpServletRequest.setAttribute("receiveFileTogetherDoDepartNm", po.getReceiveFileTogetherDoDepartNm());
    govReceiveFileActionForm.setReceiveFileTransPerson(po.getReceiveFileTransPerson());
    httpServletRequest.setAttribute("receiveFileTransPerson", po.getReceiveFileTransPerson());
    govReceiveFileActionForm.setReceiveFileTransPersonNm(po.getReceiveFileTransPersonNm());
    httpServletRequest.setAttribute("receiveFileTransPersonNm", po.getReceiveFileTransPersonNm());
    govReceiveFileActionForm.setReceiveFileIsEnd((new StringBuilder(String.valueOf(po.getReceiveFileIsEnd()))).toString());
    httpServletRequest.setAttribute("receiveFileIsEnd", po.getReceiveFileIsEnd());
    govReceiveFileActionForm.setReceiveFileSettleLeaderComment(po.getReceiveFileSettleLeaderComment());
    httpServletRequest.setAttribute("receiveFileSettleLeaderComment", po.getReceiveFileSettleLeaderComment());
    govReceiveFileActionForm.setReceiveFileType(po.getReceiveFileType());
    httpServletRequest.setAttribute("receiveFileType", po.getReceiveFileType());
    govReceiveFileActionForm.setReceiveFileTransAuditComment(po.getReceiveFileTransAuditComment());
    httpServletRequest.setAttribute("receiveFileTransAuditComment", po.getReceiveFileTransAuditComment());
    govReceiveFileActionForm.setReceiveFileFileNoCount(po.getReceiveFileFileNoCount());
    httpServletRequest.setAttribute("receiveFileFileNoCount", po.getReceiveFileFileNoCount());
    govReceiveFileActionForm.setField1(po.getField1());
    httpServletRequest.setAttribute("field1", po.getField1());
    govReceiveFileActionForm.setField2(po.getField2());
    httpServletRequest.setAttribute("field2", po.getField2());
    govReceiveFileActionForm.setField3(po.getField3());
    httpServletRequest.setAttribute("field3", po.getField3());
    govReceiveFileActionForm.setField4(po.getField4());
    httpServletRequest.setAttribute("field4", po.getField4());
    govReceiveFileActionForm.setField5(po.getField5());
    httpServletRequest.setAttribute("field5", po.getField5());
    govReceiveFileActionForm.setField6(po.getField6());
    httpServletRequest.setAttribute("field6", po.getField6());
    govReceiveFileActionForm.setField7(po.getField7());
    httpServletRequest.setAttribute("field7", po.getField7());
    govReceiveFileActionForm.setField8(po.getField8());
    httpServletRequest.setAttribute("field8", po.getField8());
    govReceiveFileActionForm.setField9(po.getField9());
    httpServletRequest.setAttribute("field9", po.getField9());
    govReceiveFileActionForm.setField10(po.getField10());
    httpServletRequest.setAttribute("field10", po.getField10());
    govReceiveFileActionForm.setZjkySeq(po.getZjkySeq());
    httpServletRequest.setAttribute("zjkyseq", po.getZjkySeq());
    govReceiveFileActionForm.setZjkyType(po.getZjkyType());
    httpServletRequest.setAttribute("zjkyType", po.getZjkyType());
    govReceiveFileActionForm.setZjkykeepTerm(po.getZjkykeepTerm());
    httpServletRequest.setAttribute("zjkykeepTerm", po.getZjkykeepTerm());
    govReceiveFileActionForm.setReceiveDropDownSelect1(po.getReceiveDropDownSelect1());
    httpServletRequest.setAttribute("receiveDropDownSelect1", po.getReceiveDropDownSelect1());
    govReceiveFileActionForm.setReceiveDropDownSelect2(po.getReceiveDropDownSelect2());
    httpServletRequest.setAttribute("receiveDropDownSelect2", po.getReceiveDropDownSelect2());
    govReceiveFileActionForm.setReceiveFieldSelectMoreEmp(po.getReceiveFieldSelectMoreEmp());
    httpServletRequest.setAttribute("receiveFieldSelectMoreEmp", po.getReceiveFieldSelectMoreEmp());
    govReceiveFileActionForm.setField16(po.getField16());
    httpServletRequest.setAttribute("field16", po.getField16());
    govReceiveFileActionForm.setField17(po.getField17());
    httpServletRequest.setAttribute("field17", po.getField17());
    govReceiveFileActionForm.setField18(po.getField8());
    httpServletRequest.setAttribute("field18", po.getField18());
    govReceiveFileActionForm.setField19(po.getField19());
    httpServletRequest.setAttribute("field19", po.getField19());
    govReceiveFileActionForm.setField20(po.getField20());
    httpServletRequest.setAttribute("field20", po.getField20());
    govReceiveFileActionForm.setField21(po.getField21());
    httpServletRequest.setAttribute("field21", po.getField21());
    govReceiveFileActionForm.setField22(po.getField22());
    httpServletRequest.setAttribute("field22", po.getField22());
    httpServletRequest.setAttribute("myform", govReceiveFileActionForm);
    httpServletRequest.setAttribute("accessory1", po.getAccessoryName());
    httpServletRequest.setAttribute("accessorySave1", po.getAccessorySaveName());
    httpServletRequest.setAttribute("accessory2", po.getAccessoryNameFile());
    httpServletRequest.setAttribute("accessorySave2", po.getAccessorySaveNameFile());
    httpServletRequest.setAttribute("receiveDate", po.getReceiveFileReceiveDate());
    httpServletRequest.setAttribute("receiveFileReceiveDate", po.getReceiveFileReceiveDate());
    httpServletRequest.setAttribute("receiveStatus", po.getReceiveFileStatus());
    httpServletRequest.setAttribute("receiveTextField1", po.getReceiveTextField1());
    httpServletRequest.setAttribute("receiveTextField2", po.getReceiveTextField2());
    httpServletRequest.setAttribute("receiveDropDownSelect1", po.getReceiveDropDownSelect1());
    httpServletRequest.setAttribute("receiveDropDownSelect2", po.getReceiveDropDownSelect2());
    httpServletRequest.setAttribute("receiveMutliTextField1", po.getReceiveMutliTextField1());
    if (po.getSeqId() != null && !po.getSeqId().toString().equals("") && !po.getSeqId().toString().equals("null")) {
      httpServletRequest.setAttribute("seqId", po.getSeqId());
    } else {
      httpServletRequest.setAttribute("seqId", "");
    } 
    Calendar endDate = Calendar.getInstance();
    if (po.getReceiveFileEndDate() != null) {
      endDate.setTime(po.getReceiveFileEndDate());
      httpServletRequest.setAttribute("LimitTimeYear", (new StringBuilder(String.valueOf(endDate.get(1)))).toString());
      httpServletRequest.setAttribute("LimitTimeMonth", (new StringBuilder(String.valueOf(endDate.get(2) + 1))).toString());
      httpServletRequest.setAttribute("LimitTimeDate", (new StringBuilder(String.valueOf(endDate.get(5)))).toString());
    } else {
      httpServletRequest.setAttribute("LimitTimeYear", (new StringBuilder(String.valueOf(endDate.get(1)))).toString());
      httpServletRequest.setAttribute("LimitTimeMonth", "");
      httpServletRequest.setAttribute("LimitTimeDate", "");
    } 
    httpServletRequest.setAttribute("createdDate", po.getCreatedTime());
    if (po.getFileSendCheckId() != null) {
      httpServletRequest.setAttribute("fromFileSendCheckId", po.getFileSendCheckId());
      httpServletRequest.setAttribute("fromFileSendCheckLink", po.getFileSendCheckLink());
    } 
    if (po.getReceiveFileSendFileUnit() != null && !po.getReceiveFileSendFileUnit().equals(""))
      httpServletRequest.setAttribute("receiveFileSendFileUnit", po.getReceiveFileSendFileUnit()); 
    setMapInfo(httpServletRequest);
  }
  
  private void draft(HttpServletRequest httpServletRequest) {
    String result = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    String formClassName = "ReceiveFileDrafGzwBD";
    String formClassMethod = "saveDraf";
    FormReflection formReflection = new FormReflection();
    Object obj = formReflection.execute("com.js.oa.tjgzw.service." + formClassName, formClassMethod, httpServletRequest);
    String processId = httpServletRequest.getParameter("processId");
    String tableId = httpServletRequest.getParameter("tableId");
    httpServletRequest.setAttribute("processId", processId);
    httpServletRequest.setAttribute("tableId", tableId);
  }
}
