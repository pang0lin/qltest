package com.js.oa.oacollect.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.oacollect.bean.OACollectFileEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OACollectFileAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    OaCollectFileActionForm form = (OaCollectFileActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String wfWorkId = request.getParameter("wfWorkId");
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    OACollectFileEJBBean fileBean = new OACollectFileEJBBean();
    if (action != null && "fileList".equals(action.trim())) {
      fileBean.addViewTimeForCollect(wfWorkId);
      try {
        fileList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      String displayFlag = request.getParameter("displayFlag");
      if (displayFlag != null && "suoluetu".equals(displayFlag))
        return actionMapping.findForward("fileList_suoluetu"); 
      return actionMapping.findForward("fileList");
    } 
    if ("forAdd".equals(action)) {
      try {
        String sql = "select po.tableId,po.tableDesname from com.js.oa.hr.finance.po.FTable po";
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("add".equals(action)) {
      String flag = "1";
      request.setAttribute("flag", flag);
      return actionMapping.findForward(action);
    } 
    if ("uploadfile".equals(action)) {
      try {
        request.setAttribute("close", "1");
        Long id = fileBean.saveCollectFile(request);
        if (id == null || "".equals(id)) {
          request.setAttribute("flag", "0");
        } else {
          request.setAttribute("flag", "1");
        } 
        request.setAttribute("collectId", request.getParameter("collectId"));
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("fileDelete".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String fromTabFlag = request.getParameter("fromTabFlag");
        String collectId = request.getParameter("collectId");
        boolean b = false;
        if (ids != null && !"".equals(ids))
          try {
            b = fileBean.deleteCollectFile(ids);
          } catch (Exception e) {
            e.printStackTrace();
          }  
        String flag = null;
        if (b) {
          flag = "deletetasksuccess";
        } else {
          flag = "deletetaskerror";
        } 
        request.setAttribute("flag", flag);
        fileList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      String displayFlag = request.getParameter("displayFlag");
      if (displayFlag != null && "suoluetu".equals(displayFlag))
        return actionMapping.findForward("fileList_suoluetu"); 
      return actionMapping.findForward("fileList");
    } 
    if ("downLoadFile".equals(action)) {
      String ids = request.getParameter("ids");
      if (ids != null && !"".equals(ids))
        try {
          String fileName = fileBean.downLoadFile(ids, userName);
          String[] zipName = fileName.split("&&");
          request.setAttribute("fileName", zipName[0]);
          request.setAttribute("src", zipName[1]);
        } catch (HibernateException e) {
          e.printStackTrace();
        }  
      return actionMapping.findForward(action);
    } 
    if ("collectFileRepeat".equals(action)) {
      String reStr = "0";
      PrintWriter out = null;
      try {
        response.setContentType("text/xml;charset=GBK");
        out = response.getWriter();
        long collectId = Long.parseLong(request.getParameter("collectId"));
        String sqlAjax = "select p.id,p.collectId,p.empId from com.js.oa.oacollect.po.OaCollectEmp p where p.collectId=" + collectId + " and p.empId=" + userId + "  and p.empStatus=1";
        MsManageBD msBD = new MsManageBD();
        List reList = msBD.getListByYourSQL(sqlAjax);
        if (reList != null)
          reStr = String.valueOf(reList.size()); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reStr);
      out.close();
      return null;
    } 
    return actionMapping.findForward("fileList");
  }
  
  public void fileList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    String displayFlag = request.getParameter("displayFlag");
    if (displayFlag != null && "suoluetu".equals(displayFlag))
      pageSize = 16; 
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.id,po.collectId,po.collectTitle,po.fileName,po.filePath,po.collectEmpId,po.collectEmpName,po.collectOrgName,po.collectEmpStatus,po.createDate,po.fileNameSys,po.fileSize";
    String table = " com.js.oa.oacollect.po.OaCollectFile po ";
    String where = " where  1=1 ";
    String collectId = request.getParameter("collectId");
    if (collectId != null && !"".equals(collectId) && !"null".equalsIgnoreCase(collectId)) {
      where = String.valueOf(where) + " and po.collectId=" + collectId;
      request.setAttribute("collectId", collectId);
      OACollectFileEJBBean fileBean = new OACollectFileEJBBean();
      List<Object[]> tempList = fileBean.getListByYourSQL("select em.id,em.collectId,po.collectEnable,po.collectEndTime from com.js.oa.oacollect.po.OaCollectEmp em,com.js.oa.oacollect.po.OaCollect po  where po.collectId=em.collectId and em.empId=" + 
          userId + " and em.collectId=" + collectId);
      if (tempList != null && tempList.size() > 0) {
        Object[] ob = tempList.get(0);
        String collectEnable = ob[2].toString();
        String collectEndTime = ob[3].toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        Date endTime = null;
        if (collectEndTime != null && !"".equals(collectEndTime) && collectEndTime.length() >= 14) {
          collectEndTime = collectEndTime.substring(0, 10);
          endTime = format.parse(String.valueOf(collectEndTime) + " 23:59:59");
        } 
        if (endTime != null && (new Date()).before(endTime) && "1".equals(collectEnable)) {
          request.setAttribute("canUpload", "1");
          request.setAttribute("canDelete", "1");
        } 
      } 
      tempList = fileBean.getListByYourSQL("select po.collectId,po.isMultiCollect,po.createEmp from com.js.oa.oacollect.po.OaCollect po  where  po.collectId=" + 
          collectId);
      if (tempList != null && tempList.size() > 0) {
        Object[] ob = tempList.get(0);
        request.setAttribute("isMultiCollect", ob[1].toString());
        if (ob[2].toString().equals(userId))
          request.setAttribute("canDelete", "1"); 
      } 
    } 
    String collectEmpName = request.getParameter("collectEmpName");
    if (collectEmpName != null && !"".equals(collectEmpName) && !"null".equalsIgnoreCase(collectEmpName))
      where = String.valueOf(where) + " and po.collectEmpName like '%" + collectEmpName + "%'"; 
    String collectOrgName = request.getParameter("collectOrgName");
    if (collectOrgName != null && !"".equals(collectOrgName) && !"null".equalsIgnoreCase(collectOrgName))
      where = String.valueOf(where) + " and po.collectOrgName like '%" + collectOrgName + "%'"; 
    String searchTime = request.getParameter("searchTime");
    String oprStartTime = request.getParameter("oprStartTime");
    String oprEndTime = request.getParameter("oprEndTime");
    String databaseType = SystemCommon.getDatabaseType();
    if (searchTime != null && "1".equals(searchTime)) {
      if (oprStartTime != null && !"".equals(oprStartTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.createDate >=to_date('" + dateFormart(oprStartTime, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.createDate >='" + dateFormart(oprStartTime, "yyyy/MM/dd") + "'";
        }  
      if (oprEndTime != null && !"".equals(oprEndTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.createDate <=to_date('" + dateFormart(oprEndTime, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.createDate <='" + dateFormart(oprEndTime, "yyyy/MM/dd") + "'";
        }  
    } 
    String fileName = request.getParameter("fileName");
    if (fileName != null && !"".equals(fileName) && !"null".equalsIgnoreCase(fileName))
      where = String.valueOf(where) + " and po.fileName like '%" + fileName + "%'"; 
    String fileSizeBegin = request.getParameter("fileSizeBegin");
    if (fileSizeBegin != null && !"".equals(fileSizeBegin) && !"null".equalsIgnoreCase(fileSizeBegin))
      where = String.valueOf(where) + " and po.fileSize >= " + fileSizeBegin; 
    String fileSizeEnd = request.getParameter("fileSizeEnd");
    if (fileSizeEnd != null && !"".equals(fileSizeEnd) && !"null".equalsIgnoreCase(fileSizeEnd))
      where = String.valueOf(where) + " and po.fileSize <= " + fileSizeEnd; 
    String fromFlag = request.getParameter("fromFlag");
    if (fromFlag != null && !"".equals(fromFlag) && !"null".equalsIgnoreCase(fromFlag))
      where = String.valueOf(where) + " and po.collectEmpId =" + userId; 
    ManagerService managerBD = new ManagerService();
    String rightName = "09*01*01";
    String whereTmp = managerBD.getRightWhere(session.getAttribute("userId").toString(), 
        session.getAttribute("orgId").toString(), 
        rightName, 
        "po.createdOrg", 
        "po.createdEmp");
    if (whereTmp.equals("") || whereTmp.equals("(1>2)") || whereTmp.equals("(1>1)"))
      where = String.valueOf(where) + " and po.createdEmp=" + userId; 
    if (whereTmp != null && !whereTmp.equals("") && whereTmp.equals("(1>2)") && whereTmp.equals("(1>1)"))
      where = String.valueOf(where) + " and " + whereTmp; 
    String orderByWh = " order by po.id desc ";
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null) {
      orderBy = "po." + orderBy + " " + sortType;
      orderByWh = " order by " + orderBy;
    } 
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderByWh);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List myList = page_ol.getResultList();
    int recordCount = page_ol.getRecordCount();
    if (offset_page >= recordCount) {
      offset_page = (recordCount - pageSize) / pageSize;
      currentPage = offset_page + 1;
      offset_page *= pageSize;
      page_ol.setcurrentPage(currentPage);
      myList = page_ol.getResultList();
      recordCount = page_ol.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset_page));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", "action,collectEmpName,collectId,collectOrgName,searchTime,oprStartTime,oprEndTime,displayFlag,fileSizeBegin,fileSizeEnd,fileName,orderBy,sortType,fromFlag");
    request.setAttribute("myList", myList);
  }
  
  public Date timeFormat(String timeStr) throws ParseException {
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
    Date datef = bartDateFormat.parse(timeStr);
    timeStr = format.format(datef);
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format2.parse(timeStr);
    return date;
  }
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
