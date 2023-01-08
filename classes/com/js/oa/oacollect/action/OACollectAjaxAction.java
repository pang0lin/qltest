package com.js.oa.oacollect.action;

import com.js.oa.jsflow.bean.WFProcessEJBBean;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.module.bean.ModuleMenuEJBBean;
import com.js.oa.module.vo.ListItem;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.oasysremind.bean.JsonData;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class OACollectAjaxAction extends DispatchAction {
  private static final String masCityService = null;
  
  public void getQueryShowFieldsByCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    response.setContentType("text/xml; charset=GBK");
    String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
    PrintWriter out = response.getWriter();
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    List list = new ArrayList();
    List<JsonData> listJson = new ArrayList<JsonData>();
    JsonData jsonDate = null;
    ModuleMenuEJBBean moduleMenuEJBBean = new ModuleMenuEJBBean();
    try {
      String caseId = request.getParameter("val");
      String domainId = request.getParameter("flag");
      String flag = request.getParameter("type");
      List<ListItem> ttableList = convertStrArrToShow(moduleMenuEJBBean.getQueryShowFieldsByCase(caseId, domainId, flag), 
          0, false, false);
      if (ttableList != null && ttableList.size() != 0)
        for (int i = 0; i < ttableList.size(); i++) {
          jsonDate = new JsonData();
          ListItem item = ttableList.get(i);
          jsonDate.setId(item.getId());
          jsonDate.setName(item.getName());
          listJson.add(jsonDate);
        }  
      JSONArray jsonArray = JSONArray.fromObject(listJson);
      out.print(jsonArray.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.flush();
      out.close();
    } 
  }
  
  public void saveQLCaseSet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    response.setContentType("text/xml; charset=GBK");
    PrintWriter out = response.getWriter();
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    ModuleMenuEJBBean moduleMenuEJBBean = new ModuleMenuEJBBean();
    try {
      String tblId = request.getParameter("tblId");
      String fieldVal = request.getParameter("fieldVal");
      String flag = request.getParameter("flag");
      String caseName = URLDecoder.decode(request.getParameter("caseName"), "utf-8");
      String type = request.getParameter("type");
      Long re = moduleMenuEJBBean.saveQLCaseSet(tblId, fieldVal, flag, caseName, type);
      out.print(re);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.flush();
      out.close();
    } 
  }
  
  public void delQLCaseSets(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/xml; charset=GBK");
    PrintWriter out = response.getWriter();
    ModuleMenuEJBBean moduleMenuEJBBean = new ModuleMenuEJBBean();
    try {
      String seled = request.getParameter("seled");
      moduleMenuEJBBean.delQLCaseSet(seled);
      out.print("");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.flush();
      out.close();
    } 
  }
  
  public void getWFProcessesByTableId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    response.setContentType("text/xml; charset=GBK");
    String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
    PrintWriter out = response.getWriter();
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    List list = new ArrayList();
    List<JsonData> listJson = new ArrayList<JsonData>();
    JsonData jsonDate = null;
    OACollectEJBBean oACollectEJBBean = new OACollectEJBBean();
    try {
      String tableId = request.getParameter("tableId");
      String domainId = request.getParameter("domainId");
      String sql = null;
      sql = "SELECT ttable.tableId,ttable.tableName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable WHERE aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1' and ttable.tableId='" + 





        
        tableId + "'";
      WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
      String where = processEjbBean.getProcWhereSql(userId, orgIdString, "1");
      sql = String.valueOf(sql) + " and ((" + where + ") or (aaa.createdEmp = " + userId + ")) ";
      MsManageBD msBD = new MsManageBD();
      List<Object[]> ttableList = msBD.getListByYourSQL(sql);
      if (ttableList != null) {
        jsonDate = new JsonData();
        jsonDate.setId("-1");
        jsonDate.setName("--请选择--");
        listJson.add(jsonDate);
        for (int i = 0; i < ttableList.size(); i++) {
          Object[] processObj = ttableList.get(i);
          jsonDate = new JsonData();
          jsonDate.setId(
              String.valueOf((processObj[2] != null) ? processObj[2].toString() : "") + "$" + (
              (processObj[4] != null) ? processObj[4].toString() : 
              "") + 
              "$" + (
              (processObj[3] != null) ? processObj[3].toString() : 
              "") + 
              "$" + (
              (processObj[5] != null) ? processObj[5].toString() : 
              "") + 
              "$" + (
              (processObj[6] != null) ? processObj[6].toString() : 
              ""));
          jsonDate.setName(processObj[3].toString());
          listJson.add(jsonDate);
        } 
      } 
      JSONArray jsonArray = JSONArray.fromObject(listJson);
      out.print(jsonArray.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.flush();
      out.close();
    } 
  }
  
  public List convertStrArrToShow(String[][] tables, int offset, boolean flag, boolean fieldFlag) {
    ArrayList<ListItem> retList = new ArrayList();
    if (tables != null && tables.length > 0)
      for (int i = 0; i < tables.length; i++) {
        ListItem item = new ListItem();
        if (fieldFlag) {
          item.setId(tables[i][3]);
        } else {
          item.setId(tables[i][0]);
        } 
        item.setName(tables[i][1]);
        if (flag)
          item.setField1(tables[i][offset]); 
        retList.add(item);
      }  
    return retList;
  }
}
