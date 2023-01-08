package com.js.oa.oacollect.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.oacollect.bean.OACollectCategoryEJBBean;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.oacollect.po.OaCollect;
import com.js.oa.oasysremind.bean.JsonData;
import com.js.oa.oasysremind.bean.OASysRemindEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OACollectAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    OACollectActionForm form = (OACollectActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    OACollectEJBBean oaCollectEJBBean = new OACollectEJBBean();
    Long domainId = (session.getAttribute("domainId") != null) ? 
      Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    if ("taskList".equals(action)) {
      try {
        taskList(request, "taskList");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("taskList");
    } 
    if ("taskListDone".equals(action)) {
      try {
        taskList(request, "taskListDone");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("taskListDone");
    } 
    if ("taskListMonitor".equals(action)) {
      try {
        taskList(request, "taskList");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("taskListMonitorDone".equals(action)) {
      try {
        taskList(request, "taskListDone");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("collectEmpList".equals(action)) {
      try {
        collectEmpList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("collectEmpList");
    } 
    if ("collectList".equals(action)) {
      try {
        collectList(request, "collectList");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("collectList");
    } 
    if ("collectListDone".equals(action)) {
      try {
        collectList(request, "collectListDone");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("collectListDone");
    } 
    if ("forCollectTaskAdd".equals(action)) {
      try {
        MsManageBD msBD = new MsManageBD();
        List ttableList = null;
        String sql = "select po.tableId,po.tableDesName from com.js.oa.jsflow.po.TTablePO po";
        ttableList = msBD.getListByYourSQL(sql);
        request.setAttribute("ttableList", ttableList);
        form.setCollectEnable(new Long(1L));
        form.setIsMultiCollect(new Long(1L));
        form.setIfRemind(new Long(0L));
        form.setCollectZl(new Long(0L));
        form.setIfRemindPerDay(new Long(0L));
        OACollectCategoryEJBBean bean = new OACollectCategoryEJBBean();
        List<Object> list = bean.searchAll(Long.valueOf(-1L), userId);
        request.setAttribute("categoryList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("collectTaskAdd");
    } 
    if ("collectTaskAdd".equals(action))
      try {
        String collectBeginTimeTemp = request
          .getParameter("collectBeginTimeTemp");
        String collectEndTimeTemp = request
          .getParameter("collectEndTimeTemp");
        form.setCollectBeginTime(timeFormat(collectBeginTimeTemp));
        form.setCollectEndTime(timeFormat(collectEndTimeTemp));
        OaCollect po = (OaCollect)FillBean.transformOTO(form, 
            OaCollect.class);
        po.setCreateEmp(session.getAttribute("userId").toString());
        po.setCreateOrg(session.getAttribute("orgId").toString());
        po.setCollectStatus(new Long(0L));
        po.setCollectOperationType("0|");
        po.setCollectRecordOperation("|");
        po.setCollectOperationimg("|");
        po.setCollectOperationcomponert("|");
        po.setCollectOpenStyle("0");
        po.setCollectRelationRefFlow("|");
        Long id = oaCollectEJBBean.saveOATaskCollect(po, request);
        String flag = null;
        if (id != null) {
          flag = "taskaddsuccess";
        } else {
          flag = "taskadderror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("collectTaskAddAndContinue".equals(action))
      try {
        String collectBeginTimeTemp = request
          .getParameter("collectBeginTimeTemp");
        String collectEndTimeTemp = request
          .getParameter("collectEndTimeTemp");
        form.setCollectBeginTime(timeFormat(collectBeginTimeTemp));
        form.setCollectEndTime(timeFormat(collectEndTimeTemp));
        OaCollect po = (OaCollect)FillBean.transformOTO(form, 
            OaCollect.class);
        po.setCreateEmp(session.getAttribute("userId").toString());
        po.setCreateOrg(session.getAttribute("orgId").toString());
        po.setCollectStatus(new Long(0L));
        Long id = oaCollectEJBBean.saveOATaskCollect(po, request);
        String flag = null;
        if (id != null) {
          addMenuListRight(id, po.getCollectTitle(), domainId, 
              oaCollectEJBBean);
          flag = "taskAddAndContinueSuccess";
        } else {
          flag = "taskAddAndContinueError";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("forCollectTaskModi".equals(action))
      try {
        String collectId = request.getParameter("id");
        if (collectId != null && !"".equals(collectId)) {
          Pattern pattern = Pattern.compile("[0-9]*");
          OaCollect oaCollect = oaCollectEJBBean
            .loadOaTaskCollect(new Long(collectId));
          if (oaCollect.getCollectTTable() == null || !pattern.matcher(oaCollect.getCollectTTable()).matches())
            oaCollect.setCollectTTable(""); 
          poToForm(form, oaCollect);
          request.setAttribute("collectType", form.getCollectZl());
          request.setAttribute("start_date", 
              oaCollect.getCollectBeginTime());
          request.setAttribute("end_date", 
              oaCollect.getCollectEndTime());
          MsManageBD msBD = new MsManageBD();
          List<Object[]> ttableList = null;
          String sql = "select po.tableId,po.tableDesName from com.js.oa.jsflow.po.TTablePO po";
          ttableList = msBD.getListByYourSQL(sql);
          request.setAttribute("ttableList", ttableList);
          sql = "select tarea.tpage.id,tarea.tpage.pageName from  com.js.oa.jsflow.po.TTablePO ttable,com.js.oa.eform.po.TAreaPO tarea  where tarea.areaTable=ttable.tableName and tarea.areaName='form1' and tarea.tpage.pageType=0 and ttable.tableId='" + 

            
            oaCollect.getCollectTTable() + "'";
          ttableList = msBD.getListByYourSQL(sql);
          request.setAttribute("tPageList", ttableList);
          sql = "SELECT ttable.tableId,ttable.tableName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable WHERE aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1' and ttable.tableId='" + 






            
            oaCollect.getCollectTTable() + "'";
          ttableList = msBD.getListByYourSQL(sql);
          List<JsonData> listJson = new ArrayList<JsonData>();
          if (ttableList != null)
            for (int i = 0; i < ttableList.size(); i++) {
              Object[] processObj = ttableList.get(i);
              JsonData jsonDate = new JsonData();
              jsonDate.setId(
                  String.valueOf((processObj[2] != null) ? processObj[2].toString() : "") + 
                  "$" + (
                  (processObj[4] != null) ? processObj[4]
                  .toString() : "") + 
                  "$" + (
                  (processObj[3] != null) ? processObj[3]
                  .toString() : "") + 
                  "$" + (
                  (processObj[5] != null) ? processObj[5]
                  .toString() : "") + "$" + ((processObj[6] != null) ? processObj[6]
                  .toString() : ""));
              jsonDate.setName(processObj[3].toString());
              listJson.add(jsonDate);
            }  
          request.setAttribute("tProcessList", listJson);
          if (oaCollect.getCollectTTable() != null && 
            !"".equals(oaCollect.getCollectTTable())) {
            OASysRemindEJBBean oasrDB = new OASysRemindEJBBean();
            String[][] filed = oasrDB.getQueryFiled(oaCollect
                .getCollectTTable());
            String filtstr = (oaCollect
              .getCollectDefquerycondition() == null) ? "" : 
              oaCollect.getCollectDefquerycondition();
            String orderstr = (oaCollect.getCollectDefqueryorder() == null) ? "" : 
              oaCollect.getCollectDefqueryorder();
            String mainTableNameTemp = "";
            if (filed != null && filed.length > 0) {
              for (int i = 0; i < filed.length; i++) {
                filtstr = filtstr.replace(filed[i][2], 
                    String.valueOf(filed[i][1]) + "." + filed[i][3]);
                orderstr = orderstr.replace(filed[i][2], 
                    filed[i][3]);
                mainTableNameTemp = filed[i][1];
              } 
              orderstr = orderstr.replace(
                  oaCollect.getCollectTTableName(), 
                  mainTableNameTemp);
            } 
            request.setAttribute("orderstr", orderstr);
            request.setAttribute("filtstr", filtstr);
          } 
          if (oaCollect.getCollectQueryelements() != null && 
            !"".equals(oaCollect.getCollectQueryelements())) {
            String collectQueryelements = oaCollect
              .getCollectQueryelements();
            sql = "select po.id,po.menuCaseName  from com.js.oa.module.po.ModuleSEQPO po  where  po.caseType = 0 and po.menuId = " + 


              
              oaCollect.getCollectTTable() + 
              " and po.domainId = " + domainId;
            ttableList = msBD.getListByYourSQL(sql);
            request.setAttribute("queryCasesSelList", ttableList);
            form.setQueryCasesSel(collectQueryelements);
            request.setAttribute("collectQueryelements", 
                collectQueryelements);
          } 
          if (oaCollect.getCollectDisplayelements() != null && 
            !"".equals(oaCollect.getCollectDisplayelements())) {
            String collectDisplayelements = oaCollect
              .getCollectDisplayelements();
            sql = "select po.id,po.menuCaseName  from com.js.oa.module.po.ModuleSEQPO po  where  po.caseType = 1 and po.menuId = " + 


              
              oaCollect.getCollectTTable() + 
              " and po.domainId = " + domainId;
            ttableList = msBD.getListByYourSQL(sql);
            request.setAttribute("listCasesSelList", ttableList);
            form.setListCasesSel(collectDisplayelements);
            request.setAttribute("collectDisplayelements", 
                collectDisplayelements);
          } 
          if (oaCollect.getCollectTTable() != null && 
            !"".equals(oaCollect.getCollectTTable())) {
            sql = "select a.fieldId,a.fieldDesName from com.js.oa.jsflow.po.TFieldPO a   where a.fieldList=1 and a.table.tableId=" + 
              
              oaCollect.getCollectTTable();
            ttableList = msBD.getListByYourSQL(sql);
            request.setAttribute("collectMantblSubtblnameList", 
                ttableList);
          } 
          OACollectCategoryEJBBean bean = new OACollectCategoryEJBBean();
          List<Object> list = bean
            .searchAll(Long.valueOf(-1L), userId);
          request.setAttribute("categoryList", list);
          return actionMapping.findForward("collectTaskModi");
        } 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("collectTaskModi".equals(action))
      try {
        String collectBeginTimeTemp = request
          .getParameter("collectBeginTimeTemp");
        String collectEndTimeTemp = request
          .getParameter("collectEndTimeTemp");
        form.setCollectBeginTime(timeFormat(collectBeginTimeTemp));
        form.setCollectEndTime(timeFormat(collectEndTimeTemp));
        OaCollect po = (OaCollect)FillBean.transformOTO(form, 
            OaCollect.class);
        boolean b = oaCollectEJBBean.updateOATaskCollect(po, request);
        String flag = null;
        if (b) {
          flag = "taskupdatesuccess";
        } else {
          flag = "taskupdateerror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("collectTaskDelete".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String fromTabFlag = request.getParameter("fromTabFlag");
        String ifDeleteFile = request.getParameter("ifDeleteFile");
        boolean b = false;
        if (ids != null && !"".equals(ids))
          try {
            b = oaCollectEJBBean.deleteOATaskCollect(ids, 
                ifDeleteFile);
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
        taskList(request, fromTabFlag);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("taskList");
    } 
    if ("updateCollectTaskEnable".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String inuse = request.getParameter("inuse");
        boolean b = false;
        if (ids != null && !"".equals(ids))
          b = oaCollectEJBBean.updateCollectTaskEnable(ids, inuse); 
        String fromTabFlag = request.getParameter("fromTabFlag");
        taskList(request, fromTabFlag);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("taskList");
    } 
    if ("doRemind".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        boolean b = false;
        String flag = null;
        if (ids != null && !"".equals(ids))
          try {
            b = oaCollectEJBBean
              .doRemind(ids, userName, new Date());
            if (b) {
              flag = "remindsuccess";
            } else {
              flag = "reminderror";
            } 
          } catch (Exception e) {
            e.printStackTrace();
          }  
        request.setAttribute("flag", flag);
        collectEmpList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("collectEmpList");
    } 
    if ("doRemindAllNotDone".equals(action)) {
      try {
        String collectId = request.getParameter("collectId");
        boolean b = false;
        String flag = null;
        if (collectId != null && !"".equals(collectId))
          try {
            b = oaCollectEJBBean.doRemindAllNotDone(collectId, 
                userName);
            if (b) {
              flag = "remindsuccess";
            } else {
              flag = "reminderror";
            } 
          } catch (Exception e) {
            e.printStackTrace();
          }  
        request.setAttribute("flag", flag);
        collectEmpList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("collectEmpList");
    } 
    if ("fenfa".equals(action)) {
      fenfa(request);
      try {
        response.getWriter().print("OK  " + (new Date()).toString());
      } catch (IOException e) {
        e.printStackTrace();
      } 
      return null;
    } 
    return actionMapping.findForward("taskList");
  }
  
  public void poToForm(OACollectActionForm form, OaCollect po) {
    form.setCollectId(po.getCollectId());
    form.setCollectTitle(po.getCollectTitle());
    form.setCollectTTable(po.getCollectTTable());
    form.setCollectTPage(po.getCollectTPage());
    form.setCollectTProcess(po.getCollectTProcess());
    form.setCollectTTableName(po.getCollectTTableName());
    form.setCollectTProcessName(po.getCollectTProcessName());
    form.setCollectOperationType(po.getCollectOperationType());
    form.setCollectRecordOperation(po.getCollectRecordOperation());
    form.setCollectOperationimg(po.getCollectOperationimg());
    form.setCollectOperationcomponert(po.getCollectOperationcomponert());
    form.setCollectOpenStyle(po.getCollectOpenStyle());
    form.setCollectRelationRefFlow(po.getCollectRelationRefFlow());
    form.setCollectQueryelements(po.getCollectQueryelements());
    form.setCollectDisplayelements(po.getCollectDisplayelements());
    form.setCollectDefquerycondition(po.getCollectDefquerycondition());
    form.setCollectDefqueryorder(po.getCollectDefqueryorder());
    form.setCollectMantblSubtbl(po.getCollectMantblSubtbl());
    form.setCollectMantblSubtblname(po.getCollectMantblSubtblname());
    form.setCollectStatus(po.getCollectStatus());
    form.setCollectEnable(po.getCollectEnable());
    form.setCollectBeginTime(po.getCollectBeginTime());
    form.setCollectEndTime(po.getCollectEndTime());
    form.setCreateEmp(po.getCreateEmp());
    form.setCreateOrg(po.getCreateOrg());
    form.setCollectEmpNames(po.getCollectEmpNames());
    form.setCollectEmps(po.getCollectEmps());
    form.setIsMultiCollect(po.getIsMultiCollect());
    form.setIfRemind(po.getIfRemind());
    form.setCollectRemindDays(po.getCollectRemindDays());
    form.setIfRemindPerDay(po.getIfRemindPerDay());
    form.setCollectRemindTime(po.getCollectRemindTime());
    form.setCategoryId(po.getCategoryId());
    form.setCollectZl(po.getCollectZl());
  }
  
  public void taskList(HttpServletRequest request, String flag) throws Exception {
    int offset_page = 0;
    String test = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null && 
      !"".equals(request.getParameter("pager.offset")))
      offset_page = 
        Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.collectId,po.collectTitle,po.collectBeginTime,po.collectEndTime,po.collectEnable,po.collectEmpNames,po.collectZl";
    String table = " com.js.oa.oacollect.po.OaCollect po ";
    String where = " where  ";
    HttpSession session = request.getSession(true);
    ManagerService managerBD = new ManagerService();
    String rightName = "09*01*01";
    String whereTmp = managerBD.getRightWhere(session
        .getAttribute("userId").toString(), 
        session.getAttribute("orgId").toString(), rightName, 
        "po.createOrg", "po.createEmp");
    if (whereTmp.equals(""))
      where = String.valueOf(where) + "1<1"; 
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    String databaseType = SystemCommon.getDatabaseType();
    if ("taskList".equals(flag))
      where = String.valueOf(where) + " and  po.collectEnable=1"; 
    if ("taskListDone".equals(flag))
      where = String.valueOf(where) + " and po.collectEnable=0"; 
    String collectTitle = request.getParameter("collectTitle");
    if (collectTitle != null && !"".equals(collectTitle))
      where = String.valueOf(where) + " and po.collectTitle like '%" + collectTitle + "%' "; 
    String searchTime = request.getParameter("searchTime");
    String oprStartTime = request.getParameter("oprStartTime");
    String oprEndTime = request.getParameter("oprEndTime");
    if (searchTime != null && "1".equals(searchTime)) {
      if (oprStartTime != null && !"".equals(oprStartTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.collectBeginTime >=to_date('" + 
            dateFormart(oprStartTime, "yyyy/MM/dd") + 
            "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.collectBeginTime >='" + 
            dateFormart(oprStartTime, "yyyy/MM/dd") + "'";
        }  
      if (oprEndTime != null && !"".equals(oprEndTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.collectEndTime <=to_date('" + 
            dateFormart(oprEndTime, "yyyy/MM/dd") + 
            "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.collectEndTime <='" + 
            dateFormart(oprEndTime, "yyyy/MM/dd") + "'";
        }  
    } 
    String orderBy = " order by po.collectId desc,po.collectTitle";
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List<Object[]> myList = page_ol.getResultList();
    int recordCount = page_ol.getRecordCount();
    if (offset_page >= recordCount) {
      offset_page = (recordCount - pageSize) / pageSize;
      currentPage = offset_page + 1;
      offset_page *= pageSize;
      page_ol.setcurrentPage(currentPage);
      myList = page_ol.getResultList();
      recordCount = page_ol.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset_page));
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    if (myList != null) {
      MsManageBD msBD = new MsManageBD();
      for (int i = 0; i < myList.size(); i++) {
        Object[] obj = myList.get(i);
        if (obj[5] == null || "".equals(obj[5].toString()) || 
          "null".equals(obj[5].toString())) {
          String collectId = obj[0].toString();
          String empId = "", empName = "";
          List<Object[]> empList = msBD
            .getListByYourSQL("select po.empId,po.empName from com.js.oa.oacollect.po.OaCollectEmp po where po.collectId=" + 
              collectId);
          if (empList != null)
            for (int j = 0; j < empList.size(); j++) {
              Object[] obj2 = empList.get(j);
              if (j == 0) {
                empId = obj2[0].toString();
                empName = obj2[1].toString();
              } else {
                empId = String.valueOf(empId) + "," + obj2[0];
                empName = String.valueOf(empName) + "," + obj2[1];
              } 
            }  
          obj[5] = empName;
        } 
      } 
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", 
        "action,collectTitle,searchTime,oprStartTime,oprEndTime");
    request.setAttribute("myList", myList);
  }
  
  public void collectList(HttpServletRequest request, String fromTabFlag) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    int offset_page = 0;
    String test = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null && 
      !"".equals(request.getParameter("pager.offset")))
      offset_page = 
        Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.collectId,po.collectTitle,po.collectBeginTime,po.collectEndTime,po.collectEnable,po.collectId";
    String table = " com.js.oa.oacollect.po.OaCollect po,com.js.oa.oacollect.po.OaCollectEmp po2  ";
    String where = " where  po.collectId=po2.collectId and po2.empId=" + 
      userId;
    String collectTitle = request.getParameter("collectTitle");
    if (collectTitle != null && !"".equals(collectTitle))
      where = String.valueOf(where) + " and po.collectTitle like '%" + collectTitle + "%' "; 
    String searchTime = request.getParameter("searchTime");
    String oprStartTime = request.getParameter("oprStartTime");
    String oprEndTime = request.getParameter("oprEndTime");
    String databaseType = SystemCommon.getDatabaseType();
    if ("collectList".equals(fromTabFlag))
      where = String.valueOf(where) + " and  po.collectEnable=1 and po.collectZl=0"; 
    if ("collectListDone".equals(fromTabFlag))
      where = String.valueOf(where) + " and  po.collectEnable=0  and po.collectZl=0"; 
    if (searchTime != null && "1".equals(searchTime)) {
      if (oprStartTime != null && !"".equals(oprStartTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.collectBeginTime >=to_date('" + 
            dateFormart(oprStartTime, "yyyy/MM/dd") + 
            "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.collectBeginTime >='" + 
            dateFormart(oprStartTime, "yyyy/MM/dd") + "'";
        }  
      if (oprEndTime != null && !"".equals(oprEndTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and po.collectEndTime <=to_date('" + 
            dateFormart(oprEndTime, "yyyy/MM/dd") + 
            "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and po.collectEndTime <='" + 
            dateFormart(oprEndTime, "yyyy/MM/dd") + "'";
        }  
    } 
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    String nowStr = format.format(new Date());
    nowStr = dateFormart(nowStr, "yyyy/MM/dd");
    if ("oracle".equals(databaseType)) {
      where = String.valueOf(where) + " and po.collectEndTime >=to_date('" + nowStr + 
        "','yyyy-MM-dd HH24:mi:ss')";
    } else {
      where = String.valueOf(where) + " and po.collectEndTime >='" + nowStr + "'";
    } 
    String orderBy = " order by po.collectId desc,po.collectTitle";
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List<Object[]> myList = page_ol.getResultList();
    int recordCount = page_ol.getRecordCount();
    if (offset_page >= recordCount) {
      offset_page = (recordCount - pageSize) / pageSize;
      currentPage = offset_page + 1;
      offset_page *= pageSize;
      page_ol.setcurrentPage(currentPage);
      myList = page_ol.getResultList();
      recordCount = page_ol.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset_page));
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    if (myList != null) {
      MsManageBD msBD = new MsManageBD();
      for (int i = 0; i < myList.size(); i++) {
        Object[] obj = myList.get(i);
        String collectId = obj[0].toString();
        String empId = "", empName = "";
        List<Object[]> empList = msBD
          .getListByYourSQL("select po.empId,po.empName from com.js.oa.oacollect.po.OaCollectEmp po where po.collectId=" + 
            collectId);
        if (empList != null)
          for (int j = 0; j < empList.size(); j++) {
            Object[] obj2 = empList.get(j);
            if (j == 0) {
              empId = obj2[0].toString();
              empName = obj2[1].toString();
            } else {
              empId = String.valueOf(empId) + "," + obj2[0];
              empName = String.valueOf(empName) + "," + obj2[1];
            } 
          }  
        obj[5] = empName;
      } 
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", 
        "action,collectTitle,searchTime,oprStartTime,oprEndTime");
    request.setAttribute("myList", myList);
  }
  
  public void collectEmpList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String collectId = request.getParameter("collectId");
    int offset_page = 0;
    String test = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null && 
      !"".equals(request.getParameter("pager.offset")))
      offset_page = 
        Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.id,po.empId,po.empName,po.empStatus,po.remindCount,po.collectId,po2.collectTitle";
    String table = " com.js.oa.oacollect.po.OaCollectEmp  po,com.js.oa.oacollect.po.OaCollect po2";
    String where = " where po.collectId=po2.collectId and po.collectId=" + 
      collectId;
    String empName = request.getParameter("empName");
    if (empName != null && !"".equals(empName))
      where = String.valueOf(where) + " and po.empName like '%" + empName + "%' "; 
    String orderBy = " order by po.empStatus,po.id desc,po.empName";
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List<Object[]> myList = page_ol.getResultList();
    int recordCount = page_ol.getRecordCount();
    if (offset_page >= recordCount) {
      offset_page = (recordCount - pageSize) / pageSize;
      currentPage = offset_page + 1;
      offset_page *= pageSize;
      page_ol.setcurrentPage(currentPage);
      myList = page_ol.getResultList();
      recordCount = page_ol.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset_page));
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    OACollectEJBBean oaCollectEJBBean = new OACollectEJBBean();
    OaCollect oaCollect = oaCollectEJBBean.loadOaTaskCollect(new Long(
          collectId));
    if (!oaCollect.getCollectEndTime().before(
        Timestamp.valueOf(
          String.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())) + " 00:00:00"))) {
      request.setAttribute("collectDateEnable", "1");
    } else {
      request.setAttribute("collectDateEnable", "0");
    } 
    String empIds = "-99";
    for (int i = 0; i < myList.size(); i++) {
      Object[] obj = myList.get(i);
      empIds = String.valueOf(empIds) + "," + obj[1];
    } 
    Map<String, String> empMap = oaCollectEJBBean.loadEmpMap(empIds);
    String[] empNum = oaCollectEJBBean.loadEmpNum(collectId);
    request.setAttribute("collectEnable", oaCollect.getCollectEnable()
        .toString());
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", "action,empName,collectId");
    request.setAttribute("myList", myList);
    request.setAttribute("empMap", empMap);
    request.setAttribute("empNum", empNum);
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
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat(
          "yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
  
  private void addMenuListRight(Long menuId, String menuName, Long domainId, OACollectEJBBean bd) {
    if (menuId != null && menuId.longValue() > 0L) {
      ArrayList<HashMap<Object, Object>> list = new ArrayList();
      HashMap<Object, Object> subMap1 = new HashMap<Object, Object>();
      subMap1.put("rightName", "查看");
      subMap1.put("rightType", menuName);
      subMap1.put("rightClass", menuName);
      subMap1.put("rightCode", "99+" + menuId + "+01");
      subMap1.put("domainId", domainId);
      list.add(subMap1);
      HashMap<Object, Object> subMap2 = new HashMap<Object, Object>();
      subMap2.put("rightName", "新增");
      subMap2.put("rightType", menuName);
      subMap2.put("rightClass", menuName);
      subMap2.put("rightCode", "99+" + menuId + "+02");
      subMap2.put("domainId", domainId);
      list.add(subMap2);
      HashMap<Object, Object> subMap3 = new HashMap<Object, Object>();
      subMap3.put("rightName", "维护");
      subMap3.put("rightType", menuName);
      subMap3.put("rightClass", menuName);
      subMap3.put("rightCode", "99+" + menuId + "+03");
      subMap3.put("domainId", domainId);
      list.add(subMap3);
      HashMap<Object, Object> subMap4 = new HashMap<Object, Object>();
      subMap4.put("rightName", "导出");
      subMap4.put("rightType", menuName);
      subMap4.put("rightClass", menuName);
      subMap4.put("rightCode", "99+" + menuId + "+04");
      subMap4.put("domainId", domainId);
      list.add(subMap4);
      HashMap<Object, Object> subMap5 = new HashMap<Object, Object>();
      subMap5.put("rightName", "删除");
      subMap5.put("rightType", menuName);
      subMap5.put("rightClass", menuName);
      subMap5.put("rightCode", "99+" + menuId + "+05");
      subMap5.put("domainId", domainId);
      list.add(subMap5);
      try {
        bd.addMenuListRight(list);
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void fenfa(HttpServletRequest request) {
    try {
      String pageId = request.getParameter("formId");
      String collectId = request.getParameter("collectId");
      String tableName = request.getParameter("tableName");
      tableName = tableName.replace("__", "$");
      OACollectEJBBean oaCollectEJBBean = new OACollectEJBBean();
      List<String[]> list = oaCollectEJBBean.getPageInfoList(collectId, tableName);
      for (int i = 0; i < list.size(); i++) {
        String url = "/jsoa/collectSkipAction.do?action=update&fromFlag=task&fromTabFlag=taskListMonitor&flag=1";
        String[] obj = list.get(i);
        url = String.valueOf(url) + "&formId=" + pageId + "&recordId=" + obj[0] + 
          "&collectId=" + collectId;
        RemindUtil.sendMessages("数据采集分发提醒：" + obj[2], url, obj[1], 
            "collect", new Date(), new Date(2050, 1, 1), 1);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
