package com.js.oa.oasysremind.action;

import com.js.oa.jsflow.bean.WFProcessEJBBean;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.oasysremind.bean.JsonData;
import com.js.oa.oasysremind.bean.OASysRemindEJBBean;
import com.js.oa.oasysremind.po.OASysRemindPO;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class OASysRemindAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    OASysRemindActionForm remindForm = (OASysRemindActionForm)actionForm;
    OASysRemindEJBBean oasrDB = new OASysRemindEJBBean();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    if ("list".equals(action)) {
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("forAdd".equals(action)) {
      try {
        String sql = null;
        sql = "SELECT aaa.wfWorkFlowProcessId,aaa.workFlowProcessName,ttable.tableId FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable WHERE aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1'";
        WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
        String where = processEjbBean.getProcWhereSql(userId, orgIdString, "1");
        sql = String.valueOf(sql) + " and ((" + where + ") or (aaa.createdEmp = " + userId + ")) ";
        MsManageBD msBD = new MsManageBD();
        List<Object[]> ttableList = null;
        ttableList = msBD.getListByYourSQL(sql);
        if (ttableList != null)
          for (int i = 0; i < ttableList.size(); i++) {
            Object[] obj = ttableList.get(i);
            obj[0] = obj[0] + "," + obj[2];
          }  
        request.setAttribute("ttableList", ttableList);
        remindForm.setRemindSourceType("1");
        remindForm.setRemindType("1");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("add");
    } 
    if ("add".equals(action))
      try {
        String effectiveEnd = request.getParameter("effectiveEndTemp");
        String effectiveBegin = request.getParameter("effectiveBeginTemp");
        String remindTableName = request.getParameter("remindTableName");
        remindForm.setEffectiveBegin(timeFormat(effectiveBegin));
        remindForm.setEffectiveEnd(timeFormat(effectiveEnd));
        OASysRemindPO po = (OASysRemindPO)FillBean.transformOTO(remindForm, OASysRemindPO.class);
        po.setCreateEmp(session.getAttribute("userId").toString());
        po.setCreateOrg(session.getAttribute("orgId").toString());
        po.setRemindIndexName(remindTableName);
        String remindSendpeople = request.getParameter("remindSendpeople");
        String remindField = request.getParameter("remindField");
        String remindObject = request.getParameter("remindObject");
        String remindTypeClass = "";
        if (!"".equals(remindSendpeople) && "1".equals(remindSendpeople))
          remindTypeClass = String.valueOf(remindTypeClass) + "1,"; 
        if (!"".equals(remindField) && "2".equals(remindField)) {
          remindTypeClass = String.valueOf(remindTypeClass) + "2,";
          String[] remindCoun = request.getParameterValues("remindCoun");
          String remindFieldsString = "";
          if (remindCoun != null)
            for (int i = 0; i < remindCoun.length; i++) {
              if (!"".equals(remindCoun[i]))
                if (i == remindCoun.length - 1) {
                  remindFieldsString = String.valueOf(remindFieldsString) + remindCoun[i];
                } else {
                  remindFieldsString = String.valueOf(remindFieldsString) + remindCoun[i] + ",";
                }  
            }  
          po.setRemindCoun(remindFieldsString);
        } 
        if (!"".equals(remindObject) && "3".equals(remindObject))
          remindTypeClass = String.valueOf(remindTypeClass) + "3,"; 
        po.setRemindTypeClass(remindTypeClass);
        Long id = oasrDB.saveOASysRemind(po);
        String flag = null;
        if (id != null) {
          flag = "addsuccess";
        } else {
          flag = "adderror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("addAndContinue".equals(action))
      try {
        String effectiveEnd = request.getParameter("effectiveEndTemp");
        String effectiveBegin = request.getParameter("effectiveBeginTemp");
        remindForm.setEffectiveBegin(timeFormat(effectiveBegin));
        remindForm.setEffectiveEnd(timeFormat(effectiveEnd));
        OASysRemindPO po = (OASysRemindPO)FillBean.transformOTO(remindForm, OASysRemindPO.class);
        po.setCreateEmp(session.getAttribute("userId").toString());
        po.setCreateOrg(session.getAttribute("orgId").toString());
        Long id = oasrDB.saveOASysRemind(po);
        String flag = null;
        if (id != null) {
          flag = "addAndContinueSuccess";
        } else {
          flag = "addAndContinueError";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("forUpdate".equals(action)) {
      String id = request.getParameter("id");
      try {
        OASysRemindPO po = oasrDB.loadOASysRemind(Long.valueOf(id).longValue());
        POtoForm(remindForm, po);
        request.setAttribute("remindObjId", po.getRemindObjId());
        request.setAttribute("remindObjName", po.getRemindObjName());
        request.setAttribute("remindTypeClass", po.getRemindTypeClass());
        String tableId = remindForm.getRemindTableId();
        String[] tableIdArr = tableId.split(",");
        tableId = tableIdArr[1];
        String[][] filed = oasrDB.getQueryFiled(tableId);
        MsManageBD msBD = new MsManageBD();
        List<JsonData> listJson = new ArrayList<JsonData>();
        JsonData jsonDate = null;
        List listCoun = null;
        listCoun = msBD.getListType(tableId.toString());
        request.setAttribute("listJson", listCoun);
        if (po.getRemindTypeClass() != null && !"".equals(po.getRemindTypeClass()) && po.getRemindTypeClass().toString().indexOf("2") >= 0)
          request.setAttribute("remindCoun", po.getRemindCoun()); 
        String filtstr = (remindForm.getRemindDefQueryCondition() == null) ? "" : remindForm.getRemindDefQueryCondition();
        String orderstr = (remindForm.getRemindDefQueryOrder() == null) ? "" : remindForm.getRemindDefQueryOrder();
        String mainTableNameTemp = "";
        if (filed != null && filed.length > 0) {
          for (int i = 0; i < filed.length; i++) {
            filtstr = replaceAll(filtstr, filed[i][2], 
                String.valueOf(filed[i][1]) + "." + 
                filed[i][3]);
            orderstr = replaceAll(orderstr, filed[i][2], filed[i][3]);
            mainTableNameTemp = filed[i][1];
          } 
          orderstr = replaceAll(orderstr, po.getRemindTableCode(), mainTableNameTemp);
        } 
        String sql = null;
        if (remindForm.getRemindSourceType() != null && "2".equals(remindForm.getRemindSourceType())) {
          sql = "select po.id,po.menuName,po.menuListTableMap from com.js.oa.module.po.ModuleMenuPO po where po.menuListTableMap!=0";
          List<Object[]> ttableList = null;
          ttableList = msBD.getListByYourSQL(sql);
          if (ttableList != null)
            for (int i = 0; i < ttableList.size(); i++) {
              Object[] obj = ttableList.get(i);
              obj[0] = obj[0] + "," + obj[2];
            }  
          request.setAttribute("ttableList", ttableList);
        } else {
          sql = "SELECT aaa.wfWorkFlowProcessId,aaa.workFlowProcessName,ttable.tableId FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable WHERE aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1'";
          WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
          String where = processEjbBean.getProcWhereSql(userId, orgIdString, "1");
          sql = String.valueOf(sql) + " and ((" + where + ") or (aaa.createdEmp = " + userId + ")) ";
          List<Object[]> ttableList = null;
          ttableList = msBD.getListByYourSQL(sql);
          if (ttableList != null)
            for (int i = 0; i < ttableList.size(); i++) {
              Object[] obj = ttableList.get(i);
              obj[0] = obj[0] + "," + obj[2];
            }  
          request.setAttribute("ttableList", ttableList);
        } 
        sql = "select po.fieldId,po.fieldName,po.fieldDesName from TFieldPO po where po.table.tableId =" + tableId;
        request.setAttribute("tFiledsList", msBD.getListByYourSQL(sql));
        request.setAttribute("compareType", po.getCompareType());
        request.setAttribute("remindObjName", po.getRemindObjName());
        request.setAttribute("start_date", po.getEffectiveBegin());
        request.setAttribute("end_date", po.getEffectiveEnd());
        request.setAttribute("orderstr", orderstr);
        request.setAttribute("remindTitle", po.getRemindTitle());
        request.setAttribute("filtstr", filtstr);
        return actionMapping.findForward("modi");
      } catch (NumberFormatException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("update".equals(action))
      try {
        String effectiveEnd = request.getParameter("effectiveEndTemp");
        String effectiveBegin = request.getParameter("effectiveBeginTemp");
        remindForm.setEffectiveBegin(timeFormat(effectiveBegin));
        remindForm.setEffectiveEnd(timeFormat(effectiveEnd));
        OASysRemindPO po = (OASysRemindPO)FillBean.transformOTO(remindForm, OASysRemindPO.class);
        po.setCreateEmp(session.getAttribute("userId").toString());
        po.setCreateOrg(session.getAttribute("orgId").toString());
        String remindObjId = request.getParameter("remindObjId");
        String remindObjName = request.getParameter("remindObjName");
        String remindIndexName = request.getParameter("remindIndexName");
        if (!"".equals(remindObjId) && remindObjId != null)
          remindObjId = remindObjId.replace("/", ""); 
        po.setRemindObjId(remindObjId);
        po.setRemindObjName(remindObjName);
        po.setRemindIndexName(remindIndexName);
        po.setRemindTableName(remindIndexName);
        String remindSendpeople = request.getParameter("remindSendpeople");
        String remindField = request.getParameter("remindField");
        String remindObject = request.getParameter("remindObject");
        String remindTypeClass = "";
        if (!"".equals(remindSendpeople) && "1".equals(remindSendpeople))
          remindTypeClass = String.valueOf(remindTypeClass) + "1,"; 
        if (!"".equals(remindField) && "2".equals(remindField)) {
          remindTypeClass = String.valueOf(remindTypeClass) + "2,";
          String[] remindCoun = request.getParameterValues("remindCoun");
          String remindFieldsString = "";
          if (remindCoun != null)
            for (int i = 0; i < remindCoun.length; i++) {
              if (!"".equals(remindCoun[i]))
                if (i == remindCoun.length - 1) {
                  remindFieldsString = String.valueOf(remindFieldsString) + remindCoun[i];
                } else {
                  remindFieldsString = String.valueOf(remindFieldsString) + remindCoun[i] + ",";
                }  
            }  
          po.setRemindCoun(remindFieldsString);
        } 
        if (!"".equals(remindObject) && "3".equals(remindObject))
          remindTypeClass = String.valueOf(remindTypeClass) + "3,"; 
        po.setRemindTypeClass(remindTypeClass);
        boolean b = oasrDB.updateOASysRemind(po);
        String flag = null;
        if (b) {
          flag = "updatesuccess";
        } else {
          flag = "updateerror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("delete".equals(action)) {
      String ids = request.getParameter("ids");
      boolean b = false;
      if (ids != null && !"".equals(ids))
        try {
          b = oasrDB.deleteOASysRemind(ids);
        } catch (Exception e) {
          e.printStackTrace();
        }  
      String flag = null;
      if (b) {
        flag = "deletesuccess";
      } else {
        flag = "deleteerror";
      } 
      request.setAttribute("flag", flag);
      list(request);
      return actionMapping.findForward("list");
    } 
    return actionMapping.findForward("list");
  }
  
  public void POtoForm(OASysRemindActionForm form, OASysRemindPO po) {
    form.setRemindId(po.getRemindId());
    form.setRemindSourceType(po.getRemindSourceType());
    form.setRemindTableId(po.getRemindTableId());
    form.setRemindTableName(po.getRemindTableName());
    form.setRemindTableCode(po.getRemindTableCode());
    form.setRemindType(po.getRemindType());
    form.setRemindIndex(po.getRemindIndex());
    form.setRemindIndexName(po.getRemindIndexName());
    form.setCompareType(po.getCompareType());
    form.setCompareValue(po.getCompareValue());
    form.setRemindTimeUnit(po.getRemindTimeUnit());
    form.setRemindObjId(po.getRemindObjId());
    form.setRemindObjName(po.getRemindObjName());
    form.setEffectiveBegin(po.getEffectiveBegin());
    form.setEffectiveEnd(po.getEffectiveEnd());
    form.setRemindTitle(po.getRemindTitle());
    form.setRemindUrlUser(po.getRemindUrlUser());
    form.setRemindUrlSys(po.getRemindUrlSys());
    form.setCreateEmp(po.getCreateEmp());
    form.setCreateOrg(po.getCreateOrg());
    form.setOnTimeMode(po.getOnTimeMode());
    form.setRemindWeek(po.getRemindWeek());
    form.setRemindMonthDay(po.getRemindMonthDay());
    form.setRemindYearMonth(po.getRemindYearMonth());
    form.setRemindYearMonthDay(po.getRemindYearMonthDay());
    form.setRemindDefQueryCondition(po.getRemindDefQueryCondition());
    form.setRemindDefQueryOrder(po.getRemindDefQueryOrder());
    form.setFilterStr(po.getFilterStr());
    form.setRemindTypeClass(po.getRemindTypeClass());
    form.setRemindCoun(po.getRemindCoun());
  }
  
  public void list(HttpServletRequest request) {
    int offset_page = 0;
    String test = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.remindId,po.remindTableName,po.remindObjName,po.effectiveBegin,po.effectiveEnd,po.remindTypeClass,po.remindCoun,po.remindTableId";
    String table = " OASysRemindPO po ";
    String where = " where  ";
    HttpSession session = request.getSession(true);
    ManagerService managerBD = new ManagerService();
    String rightName = "09*00*01";
    String whereTmp = managerBD.getRightWhere(session.getAttribute("userId").toString(), 
        session.getAttribute("orgId").toString(), 
        rightName, 
        "po.createOrg", 
        "po.createEmp");
    if (whereTmp.equals(""))
      where = String.valueOf(where) + "1<1"; 
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    String remindTableName = request.getParameter("remindTableName");
    String databaseType = SystemCommon.getDatabaseType();
    if (remindTableName != null && !"".equals(remindTableName))
      where = String.valueOf(where) + " and po.remindTableName like '%" + remindTableName + "%' "; 
    String remindObjName = request.getParameter("remindObjName");
    if (remindObjName != null && !"".equals(remindObjName))
      where = String.valueOf(where) + " and po.remindObjName like '%" + remindObjName + "%' "; 
    String searchTime = request.getParameter("searchTime");
    String oprStartTime = request.getParameter("oprStartTime");
    String oprEndTime = request.getParameter("oprEndTime");
    if (searchTime != null && "1".equals(searchTime)) {
      if (oprStartTime != null && !"".equals(oprStartTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.effectiveBegin >=to_date('" + dateFormart(oprStartTime) + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.effectiveBegin >='" + dateFormart(oprStartTime) + "'";
        }  
      if (oprEndTime != null && !"".equals(oprEndTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.effectiveEnd <=to_date('" + dateFormart(oprEndTime) + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.effectiveEnd <='" + dateFormart(oprEndTime) + "'";
        }  
    } 
    String orderBy = " order by po.remindId desc,po.remindTableName";
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
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
    request.setAttribute("pageParameters", "action,remindTableName,remindObjName,searchTime,oprStartTime,oprEndTime");
    request.setAttribute("myList", myList);
  }
  
  public static String replaceAll(String str, String befor, String after) {
    StringBuffer buffer = new StringBuffer();
    int i = 0, preIndex = 0, lastIndex = 0;
    int strLen = str.length();
    int litLen = befor.length();
    if (str.endsWith(befor))
      str = String.valueOf(str.substring(0, strLen - litLen)) + after; 
    if (str.startsWith(befor))
      str = String.valueOf(after) + str.substring(litLen); 
    preIndex = str.indexOf(befor);
    String tmp = "";
    while (preIndex >= 0) {
      if (preIndex == 0) {
        if (str.length() > 1) {
          str = str.substring(befor.length(), str.length());
          preIndex = str.indexOf(befor);
        } else {
          preIndex = -1;
        } 
        buffer.append(after);
      } else {
        if (i == 0) {
          buffer.append(str.substring(0, preIndex));
        } else {
          buffer.append(after).append(str.substring(0, preIndex));
        } 
        str = str.substring(preIndex + befor.length());
        preIndex = str.indexOf(befor);
      } 
      i++;
    } 
    if (!"".equals(str)) {
      if (i > 0)
        buffer.append(after); 
      buffer.append(str);
    } 
    return buffer.toString();
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
  
  public String dateFormart(String date) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
