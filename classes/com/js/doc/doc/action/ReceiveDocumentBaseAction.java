package com.js.doc.doc.action;

import com.js.doc.doc.po.ReceiveBaseInfoPO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReceiveDocumentBaseAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    ReceiveDocumentBaseActionForm rbaseForm = (ReceiveDocumentBaseActionForm)actionForm;
    String action = request.getParameter("action");
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    String corpId = request.getSession(true).getAttribute("corpId").toString();
    Object object3 = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    ReceivedocumentBD rbaseBd = new ReceivedocumentBD();
    if ("receiveBase".equals(action)) {
      Object[] obj = rbaseBd.getReceivedocumentBaseInfo();
      if (obj != null && obj.length > 0) {
        rbaseForm.setReceiveFileType((obj[1] == null) ? "" : obj[1].toString());
        rbaseForm.setPigeonholeType((obj[2] == null) ? "" : obj[2].toString());
        rbaseForm.setDecumentKind((obj[3] == null) ? "" : obj[3].toString());
        rbaseForm.setReceiveSecretLevel((obj[4] == null) ? "" : obj[4].toString());
        rbaseForm.setUrgencyLevel((obj[5] == null) ? "" : obj[5].toString());
        rbaseForm.setKeepTerm((obj[6] == null) ? "" : obj[6].toString());
        rbaseForm.setSeqType((obj[7] == null) ? "" : obj[7].toString());
        rbaseForm.setReceiveDropDownSelect1((obj[8] == null) ? "" : obj[8].toString());
        rbaseForm.setReceiveDropDownSelect2((obj[9] == null) ? "" : obj[9].toString());
        request.setAttribute("baseId", obj[0]);
      } 
      return actionMapping.findForward("receBaseList");
    } 
    if ("receiveBaseSave".equals(action)) {
      ReceiveBaseInfoPO po = new ReceiveBaseInfoPO();
      po.setPigeonholeType((request.getParameter("pigeonholeType") == null) ? "" : request.getParameter("pigeonholeType"));
      po.setReceiveFileType((request.getParameter("receiveFileType") == null) ? "" : request.getParameter("receiveFileType"));
      po.setReceiveSecretLevel((request.getParameter("receiveSecretLevel") == null) ? "" : request.getParameter("receiveSecretLevel"));
      po.setDecumentKind((request.getParameter("decumentKind") == null) ? "" : request.getParameter("decumentKind"));
      po.setUrgencyLevel((request.getParameter("urgencyLevel") == null) ? "" : request.getParameter("urgencyLevel"));
      po.setKeepTerm((request.getParameter("keepTerm") == null) ? "" : request.getParameter("keepTerm").toString());
      po.setSeqType((request.getParameter("seqType") == null) ? "" : request.getParameter("seqType").toString());
      po.setReceiveDropDownSelect1((request.getParameter("receiveDropDownSelect1") == null) ? "" : request.getParameter("receiveDropDownSelect1").toString());
      po.setReceiveDropDownSelect2((request.getParameter("receiveDropDownSelect2") == null) ? "" : request.getParameter("receiveDropDownSelect2").toString());
      String id = (request.getParameter("baseId") == null) ? "" : request.getParameter("baseId");
      if (id.trim().equals("")) {
        Long result = rbaseBd.saveReceiveBaseInfo(po);
        if (result != null && !result.toString().equals("-2")) {
          request.setAttribute("saveBaseInfoSuccess", "0");
          request.setAttribute("baseId", result.toString());
        } else {
          request.setAttribute("saveBaseInfoSuccess", "1");
        } 
      } else {
        po.setId(new Long(id));
        request.setAttribute("baseId", id);
        String updResult = rbaseBd.updateReceiveBaseInfo(po);
        if (updResult != null && updResult.equals("0")) {
          request.setAttribute("saveBaseInfoSuccess", "0");
        } else {
          request.setAttribute("saveBaseInfoSuccess", "1");
        } 
      } 
      return actionMapping.findForward("receBaseList");
    } 
    if ("receiveSeqList".equals(action)) {
      seqlist(request);
      return actionMapping.findForward("receiveSeqList");
    } 
    if ("receiveSeqAdd".equals(action)) {
      receiveSeqAdd(request);
      return actionMapping.findForward("receiveSeqAdd");
    } 
    if ("receiveSeqLoad".equals(action)) {
      receiveSeqLoad(request, rbaseForm);
      receiveSeqAdd(request);
      return actionMapping.findForward("receiveSeqModiLoad");
    } 
    if ("receiveSeqDel".equals(action)) {
      receiveSeqDel(request);
      return actionMapping.findForward("receiveSeqList");
    } 
    if ("receiveseqclose".equals(action)) {
      ReceiveFileSeqPO po = setseqPO(request);
      po.setCorpId(Long.valueOf(corpId));
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      po.setRepeatOfYear(request.getParameter("repeatOfYear"));
      po.setSeqCurYear(String.valueOf(Calendar.getInstance().get(1)));
      Long result = rbaseBd.saveRecSeqInfo(po);
      if (result != null && !result.toString().equals("-2"))
        request.setAttribute("continue", "0"); 
      receiveSeqAdd(request);
      return actionMapping.findForward("receiveSeqAdd");
    } 
    if ("receiveseqcontinue".equals(action)) {
      ReceiveFileSeqPO po = setseqPO(request);
      po.setCorpId(Long.valueOf(corpId));
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      po.setRepeatOfYear(request.getParameter("repeatOfYear"));
      po.setSeqCurYear(String.valueOf(Calendar.getInstance().get(1)));
      Long result = rbaseBd.saveRecSeqInfo(po);
      if (result != null && !result.toString().equals("-2"))
        request.setAttribute("continue", "1"); 
      receiveSeqAdd(request);
      return actionMapping.findForward("receiveSeqAdd");
    } 
    if ("receiveseqUpdate".equals(action)) {
      ReceiveFileSeqPO po = new ReceiveFileSeqPO();
      po = setseqPO(request);
      po.setRepeatOfYear(request.getParameter("repeatOfYear"));
      po.setSeqCurYear(String.valueOf(Calendar.getInstance().get(1)));
      if (request.getParameter("editId") != null && !request.getParameter("editId").toString().equals("")) {
        po.setId(new Long(request.getParameter("editId")));
        String uprs = rbaseBd.updateRecSeqPO(po);
        if (uprs != null && uprs.equals("0")) {
          request.setAttribute("SUS", "1");
        } else {
          request.setAttribute("SUS", "0");
        } 
      } 
      receiveSeqAdd(request);
      return actionMapping.findForward("receiveSeqModiLoad");
    } 
    return actionMapping.findForward("receBaseList");
  }
  
  private void seqlist(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      " com.js.doc.doc.po.ReceiveFileSeqPO po ";
    ManagerService service = new ManagerService();
    String whereSql = service.getRightFinalWhere(userId, orgId, "03*03*01", "po.createdOrg", "po.createdEmp");
    if (request.getParameter("seqNameR") != null && !request.getParameter("seqNameR").toString().equals(""))
      try {
        String seqNameR = URLDecoder.decode(request.getParameter("seqNameR"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and  po.seqNameR like '%" + seqNameR + "%' ";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    try {
      Page page = new Page("po.id, po.seqNameR,po.seqProceNameR,po.seqProceId,po.seqBitNumR,po.seqIsYearR ,po.seqInitValueR,po.seqModeR", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
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
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,seqNameR,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void receiveSeqAdd(HttpServletRequest request) {
    request.setAttribute("proList", getSendfileList(request));
    request.setAttribute("seqTypeList", getAllSeqType());
  }
  
  private void receiveSeqLoad(HttpServletRequest request, ReceiveDocumentBaseActionForm rbaseForm) {
    ReceivedocumentBD rbaseBd = new ReceivedocumentBD();
    String editId = request.getParameter("editId");
    ReceiveFileSeqPO po = rbaseBd.loadRecSeqPO(editId);
    if (po != null) {
      rbaseForm.setSeqNameR(po.getSeqNameR());
      rbaseForm.setSeqProceNameR(po.getSeqProceNameR());
      request.setAttribute("seqProceNameR", po.getSeqProceNameR());
      rbaseForm.setSeqProceId((String)po.getSeqProceId());
      rbaseForm.setSeqBitNumR((String)po.getSeqBitNumR());
      rbaseForm.setSeqIsYearR((String)po.getSeqIsYearR());
      rbaseForm.setSeqInitValueR((String)po.getSeqInitValueR());
      rbaseForm.setSeqFormatR(po.getSeqFormatR());
      rbaseForm.setSeqModeR(po.getSeqModeR());
      if (po.getSeqType() != null && !po.getSeqType().equals("") && !po.getSeqType().equals("null")) {
        rbaseForm.setSeqType(po.getSeqType());
      } else {
        rbaseForm.setSeqType("");
      } 
      request.setAttribute("seqIsYearR", po.getSeqIsYearR());
      request.setAttribute("seqfigR", po.getSeqfigR());
      request.setAttribute("editId", po.getId());
      request.setAttribute("seqIsName", po.getSeqIsName());
      request.setAttribute("repeatOfYear", po.getRepeatOfYear());
      if (po.getReceiveScopeId() != null && !po.getReceiveScopeId().equals("")) {
        request.setAttribute("receiveUser", po.getReceiveUser());
        request.setAttribute("receiveOrg", po.getReceiveOrg());
        request.setAttribute("receiveGroup", po.getReceiveGroup());
        request.setAttribute("receiveScopeId", po.getReceiveScopeId());
        request.setAttribute("receiveScopeName", po.getReceiveScopeName());
      } else {
        request.setAttribute("receiveUser", "");
        request.setAttribute("receiveOrg", "");
        request.setAttribute("receiveGroup", "");
        request.setAttribute("receiveScopeId", "");
        request.setAttribute("receiveScopeName", "");
      } 
    } 
  }
  
  private void receiveSeqDel(HttpServletRequest request) {
    String ids = request.getParameter("ids");
    ReceivedocumentBD rbaseBd = new ReceivedocumentBD();
    rbaseBd.deleteRecSeqPO(ids);
    seqlist(request);
  }
  
  private ReceiveFileSeqPO setseqPO(HttpServletRequest request) {
    ReceiveFileSeqPO po = new ReceiveFileSeqPO();
    int intValue = Integer.parseInt(request.getParameter("seqInitValueR"));
    int figR = intValue - 1;
    if (request.getParameter("seqfigR") != null && !request.getParameter("seqfigR").toString().equals("")) {
      figR = Integer.parseInt(request.getParameter("seqfigR"));
      if (intValue - 1 > figR)
        figR = intValue - 1; 
    } 
    po.setSeqBitNumR(new Long(request.getParameter("seqBitNumR")));
    po.setSeqfigR(new Long(figR));
    po.setSeqFormatR(request.getParameter("seqFormatR"));
    po.setSeqInitValueR(new Long(request.getParameter("seqInitValueR")));
    po.setSeqIsYearR(new Long(request.getParameter("seqIsYearR")));
    po.setSeqModeR(request.getParameter("seqModeR"));
    po.setSeqNameR(request.getParameter("seqNameR"));
    po.setSeqProceId(new Long(request.getParameter("seqProceId")));
    po.setSeqProceNameR(request.getParameter("seqProceNameR"));
    po.setSeqType(request.getParameter("seqType"));
    if (request.getParameter("receiveUser") != null && !request.getParameter("receiveUser").toString().equals("")) {
      po.setReceiveUser(request.getParameter("receiveUser"));
    } else {
      po.setReceiveUser("");
    } 
    if (request.getParameter("receiveOrg") != null && !request.getParameter("receiveOrg").toString().equals("")) {
      po.setReceiveOrg(request.getParameter("receiveOrg"));
    } else {
      po.setReceiveOrg("");
    } 
    if (request.getParameter("receiveGroup") != null && !request.getParameter("receiveGroup").toString().equals("")) {
      po.setReceiveGroup(request.getParameter("receiveGroup"));
    } else {
      po.setReceiveGroup("");
    } 
    if (request.getParameter("receiveScopeName") != null && !request.getParameter("receiveScopeName").toString().equals("")) {
      po.setReceiveScopeName(request.getParameter("receiveScopeName"));
    } else {
      po.setReceiveScopeName("");
    } 
    if (request.getParameter("receiveScopeId") != null && !request.getParameter("receiveScopeId").toString().equals("")) {
      po.setReceiveScopeId(request.getParameter("receiveScopeId"));
    } else {
      po.setReceiveScopeId("");
    } 
    if (request.getParameter("seqIsName") != null && !request.getParameter("seqIsName").toString().equals("")) {
      po.setSeqIsName(new Long(request.getParameter("seqIsName")));
    } else {
      po.setSeqIsName(new Long("3"));
    } 
    return po;
  }
  
  private List getSendfileList(HttpServletRequest request) {
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgIdString");
    ProcessBD procbd = new ProcessBD();
    List list = new ArrayList();
    Object tmp = procbd.getAllDossProc("3");
    if (tmp != null)
      list = (List)tmp; 
    return list;
  }
  
  private List getAllSeqType() {
    List<String> list = new ArrayList();
    String sectNums = "";
    ReceivedocumentBD rbaseBd = new ReceivedocumentBD();
    Object[] baseObj = rbaseBd.getReceivedocumentBaseInfo();
    if (baseObj != null && baseObj.length > 7 && 
      baseObj[7] != null)
      sectNums = baseObj[7].toString(); 
    if (sectNums != null && sectNums.length() > 1) {
      String[] tmp = sectNums.split(";");
      if (tmp != null && tmp.length > 0)
        for (int i = 0; i < tmp.length; i++)
          list.add(tmp[i]);  
    } 
    return list;
  }
}
