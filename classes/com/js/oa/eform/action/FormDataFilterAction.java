package com.js.oa.eform.action;

import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.util.InitWorkFlowData;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import com.js.util.util.CharacterTool;
import com.js.util.util.IO2File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FormDataFilterAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String fromSql;
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String userAccount = session.getAttribute("userAccount").toString();
    String userName = session.getAttribute("userName").toString();
    String fieldId = request.getParameter("fieldId");
    String fieldName = request.getParameter("fieldName");
    String inputValue = request.getParameter("inputValue");
    inputValue = CharacterTool.unescape(inputValue);
    String submitPersonId = request.getParameter("submitPersonId");
    String submitPersonOrgId = "0";
    String dataFlag = request.getParameter("getDataFlag");
    if (submitPersonId == null || "null".equals(submitPersonId) || "".equals(submitPersonId)) {
      submitPersonId = userId;
      submitPersonOrgId = orgId;
    } else {
      submitPersonOrgId = (new UserBD()).getUserOrgId(Long.valueOf(submitPersonId)).toString();
    } 
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String[] fieldProp = bd.getFieldExtProperty(fieldId);
    if ("1".equals(dataFlag)) {
      String fieldValue = fieldProp[5];
      String interfaceName = "", interfaceMethodName = "", interfaceMethodPara = "";
      if (fieldValue.length() > 0 && fieldValue.startsWith("$") && 
        fieldValue.indexOf("$[interface:") >= 0) {
        interfaceName = fieldValue.substring(12, fieldValue.indexOf(";;;"));
        fieldValue = fieldValue.substring(fieldValue.indexOf(";;;") + 3);
        if (fieldValue.indexOf(";;;;") > 0) {
          interfaceMethodName = fieldValue.substring(0, fieldValue.indexOf(";;;;"));
          interfaceMethodPara = fieldValue.substring(fieldValue.indexOf(";;;;") + 4, fieldValue.indexOf("]"));
        } else {
          interfaceMethodName = fieldValue.substring(0, fieldValue.indexOf("]"));
        } 
      } 
      String[] paras = (String[])null;
      if (interfaceMethodPara.length() > 0) {
        String[] paraArr = interfaceMethodPara.split(",");
        paras = new String[paraArr.length];
        for (int i = 0; i < paraArr.length; i++) {
          String paraValue;
          if (paraArr[i].startsWith("@$@userId")) {
            paraValue = userId;
          } else if (paraArr[i].startsWith("@$@orgId")) {
            paraValue = orgId;
          } else if (paraArr[i].startsWith("@$@userAccount")) {
            paraValue = userAccount;
          } else if (paraArr[i].startsWith("@$@userName")) {
            paraValue = userName;
          } else if (paraArr[i].startsWith("@$@applyId")) {
            paraValue = submitPersonId;
          } else if (paraArr[i].startsWith("@$@applyOrgId")) {
            paraValue = submitPersonOrgId;
          } else {
            paraValue = InitWorkFlowData.getValueFromRequest(request, paraArr[i]);
          } 
          paras[i] = paraValue;
        } 
      } 
      if (!"".equals(interfaceName) && !"".equals(interfaceName)) {
        try {
          FormReflection formReflection = new FormReflection();
          List obj = (List)formReflection.execute(interfaceName, interfaceMethodName, paras);
          if (obj == null) {
            System.out.println("接口获取值为null!");
            request.setAttribute("dataList", new ArrayList());
          } else {
            request.setAttribute("dataList", obj);
          } 
        } catch (Exception exception) {}
      } else {
        request.setAttribute("dataList", new ArrayList());
      } 
      String[] interfaceFieldArr = fieldProp[6].split(";;;");
      List<String[]> fieldList = new ArrayList();
      if (interfaceFieldArr.length > 0)
        for (int i = 0; i < interfaceFieldArr.length; i++) {
          String interfaceFieldDisplayName = interfaceFieldArr[i].substring(1, interfaceFieldArr[i].indexOf("]"));
          String interfaceFieldName = interfaceFieldArr[i].substring(interfaceFieldArr[i].indexOf("]") + 1);
          String[] interfaceFieldTemp = new String[2];
          interfaceFieldTemp[0] = interfaceFieldDisplayName;
          interfaceFieldTemp[1] = interfaceFieldName;
          fieldList.add(interfaceFieldTemp);
        }  
      request.setAttribute("fieldList", fieldList);
      List<String[]> toFormFieldList = new ArrayList();
      if (fieldProp[4] != null && fieldProp[4].length() > 0) {
        String[] toffieldArr = fieldProp[4].split(";;;");
        for (int i = 0; i < toffieldArr.length; i++) {
          String fieldVal = toffieldArr[i].substring(0, toffieldArr[i].indexOf("=:="));
          String fieldText = toffieldArr[i].substring(toffieldArr[i].indexOf("=:=") + 3);
          String[] toFormFieldTemp = new String[2];
          toFormFieldTemp[0] = fieldVal;
          toFormFieldTemp[1] = fieldText;
          toFormFieldList.add(toFormFieldTemp);
        } 
      } 
      request.setAttribute("toFormFieldList", toFormFieldList);
      return actionMapping.findForward("interfaceDataList");
    } 
    String dsName = "system";
    String filterSql = fieldProp[5];
    if (filterSql.startsWith("$"))
      if (filterSql.indexOf("].$[") > 0) {
        dsName = filterSql.substring(2, filterSql.indexOf("].$["));
        filterSql = filterSql.substring(filterSql.indexOf("].$[") + 4, filterSql.length() - 1);
      } else {
        filterSql = filterSql.substring(2, filterSql.length() - 1);
      }  
    filterSql = filterSql.replaceAll("\\@\\$\\@userId\\@\\$\\@", userId);
    filterSql = filterSql.replaceAll("\\@\\$\\@orgId\\@\\$\\@", orgId);
    filterSql = filterSql.replaceAll("\\@\\$\\@userAccount\\@\\$\\@", userAccount);
    filterSql = filterSql.replaceAll("\\@\\$\\@userName\\@\\$\\@", userName);
    filterSql = filterSql.replaceAll("\\@\\$\\@applyId\\@\\$\\@", submitPersonId);
    filterSql = filterSql.replaceAll("\\@\\$\\@applyOrgId\\@\\$\\@", submitPersonOrgId);
    filterSql = InitWorkFlowData.getValueFromRequest(request, filterSql);
    String sqlTemp = filterSql;
    filterSql = filterSql.toUpperCase();
    String whereSql = "", orderSql = "";
    String viewSql = sqlTemp.substring(filterSql.indexOf("SELECT ") + 6, filterSql.indexOf(" FROM "));
    sqlTemp = sqlTemp.substring(filterSql.indexOf(" FROM ") + 5);
    filterSql = sqlTemp.toUpperCase();
    if (filterSql.indexOf(" WHERE ") > 0) {
      fromSql = sqlTemp.substring(0, filterSql.indexOf(" WHERE "));
      whereSql = sqlTemp.substring(filterSql.indexOf(" WHERE "));
    } else if (filterSql.indexOf(" ORDER ") > 0) {
      fromSql = sqlTemp.substring(0, filterSql.indexOf(" ORDER "));
      whereSql = sqlTemp.substring(filterSql.indexOf(" ORDER "));
    } else {
      fromSql = sqlTemp;
    } 
    String whereSqlTemp = whereSql.toUpperCase();
    if (whereSqlTemp.indexOf(" ORDER ") >= 0) {
      orderSql = whereSql.substring(whereSqlTemp.indexOf(" ORDER "));
      whereSql = whereSql.substring(0, whereSqlTemp.indexOf(" ORDER "));
    } 
    String[][] searchField = (String[][])null;
    if (fieldProp[7] != null && !"".equals(fieldProp[7]) && !"null".equals(fieldProp[7]))
      searchField = splitFilterField(fieldProp[7], viewSql); 
    request.setAttribute("filterListField", splitFilterField(fieldProp[6], viewSql));
    request.setAttribute("filterSearchField", searchField);
    StringBuffer pagePara = new StringBuffer("submitPersonId,fieldId,fieldName,curIndex,inputValue");
    StringBuffer query = new StringBuffer("(");
    if (searchField != null)
      for (int i = 0; i < searchField.length; i++) {
        pagePara.append(",").append(searchField[i][1]);
        if (inputValue != null && !"null".equals(inputValue) && !"".equals(inputValue)) {
          if (query.length() == 1) {
            query.append(searchField[i][1]).append(" like '%").append(inputValue).append("%'");
          } else {
            query.append(" or ").append(searchField[i][1]).append(" like '%").append(inputValue).append("%'");
          } 
        } else {
          searchField[i][2] = request.getParameter(searchField[i][1]);
          if (searchField[i][2] != null && !"".equals(searchField[i][2]))
            if (query.length() == 1) {
              query.append(searchField[i][1]).append(" like '%").append(searchField[i][2]).append("%'");
            } else {
              query.append(" and ").append(searchField[i][1]).append(" like '%").append(searchField[i][2]).append("%'");
            }  
        } 
      }  
    query.append(")");
    if (query.length() > 2) {
      String whereTemp = whereSql.toUpperCase();
      if (whereTemp.trim().indexOf("WHERE") >= 0) {
        whereSql = String.valueOf(whereSql) + " and " + query.toString();
      } else {
        whereSql = " where " + query.toString();
      } 
    } 
    if ("1".equals(fieldProp[8])) {
      String fromTable = fromSql;
      while (fromTable.endsWith(" "))
        fromTable = fromTable.substring(0, fromTable.length() - 1); 
      if (request.getParameter("submitPersonId") == null || "null".equals(request.getParameter("submitPersonId")) || 
        "".equals(request.getParameter("submitPersonId"))) {
        if ("0".equals(fieldProp[9])) {
          whereSql = (new StringBuilder(String.valueOf(whereSql))).toString();
        } else if ("1".equals(fieldProp[9])) {
          whereSql = String.valueOf(whereSql) + " and " + fromTable + "_owner=" + userId;
        } else if ("2".equals(fieldProp[9])) {
          whereSql = String.valueOf(whereSql) + " and " + fromTable + "_org=" + orgId;
        } else if ("3".equals(fieldProp[9])) {
          whereSql = String.valueOf(whereSql) + " and " + fromTable + "_org in (" + StaticParam.getOrgIdsByOrgId(orgId) + ") ";
        } else if ("4".equals(fieldProp[9])) {
          String orgIds = fieldProp[11].split(";;;")[0];
          orgIds = orgIds.replace("**", ",");
          orgIds = orgIds.replace("*", "");
          whereSql = String.valueOf(whereSql) + " and " + fromTable + "_org in (" + orgIds + ") ";
        } 
      } else if ("0".equals(fieldProp[10])) {
        whereSql = (new StringBuilder(String.valueOf(whereSql))).toString();
      } else if ("1".equals(fieldProp[10])) {
        whereSql = String.valueOf(whereSql) + " and " + fromTable + "_owner=" + userId;
      } else if ("2".equals(fieldProp[10])) {
        whereSql = String.valueOf(whereSql) + " and " + fromTable + "_org=" + orgId;
      } else if ("3".equals(fieldProp[10])) {
        whereSql = String.valueOf(whereSql) + " and " + fromTable + "_org in (" + StaticParam.getOrgIdsByOrgId(orgId) + ") ";
      } else if ("4".equals(fieldProp[10])) {
        String orgIds = fieldProp[12].split(";;;")[0];
        orgIds = orgIds.replace("**", ",");
        orgIds = orgIds.replace("*", "");
        whereSql = String.valueOf(whereSql) + " and " + fromTable + "_org in (" + orgIds + ") ";
      } 
    } 
    whereSql = String.valueOf(whereSql) + " " + orderSql;
    String action = request.getParameter("action");
    if ("export".equals(action)) {
      DbOpt dbopt = null;
      List list = null;
      try {
        if ("system".equals(dsName)) {
          dbopt = new DbOpt();
        } else {
          dbopt = new DbOpt(dsName);
          String dbType = SystemCommon.getUserDatabaseType(dsName);
          if (dbType.indexOf("oracle") >= 0) {
            String lang = SystemCommon.getUserDatabaseLang(dsName);
            if (!"".equals(lang))
              dbopt.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
          } 
        } 
        IO2File.printFile("select " + viewSql + " from " + fromSql + whereSql, "流程检索sql");
        list = dbopt.executeQueryToList("select " + viewSql + " from " + fromSql + whereSql);
        dbopt.close();
      } catch (Exception ex) {
        if (dbopt != null)
          try {
            dbopt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
      request.setAttribute("list", list);
      return actionMapping.findForward("export");
    } 
    IO2File.printFile("select " + viewSql + " from " + fromSql + " " + whereSql, "流程检索sql");
    list(request, viewSql, fromSql, whereSql, dsName);
    request.setAttribute("pageParameters", pagePara.toString());
    if (request.getParameter("flag") != null && "show".equals(request.getParameter("flag")))
      return actionMapping.findForward("show"); 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request, String viewSql, String fromSql, String whereSql, String dataSourceName) {
    List list = null;
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    if ("system".equals(dataSourceName)) {
      list = page.getResultList();
    } else {
      list = page.getResultList(dataSourceName);
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
  }
  
  private String[][] splitFilterField(String fieldStr, String paraStr) {
    String[] fieldArr = fieldStr.split(";;;");
    String[] paraArr = paraStr.split(",");
    String[][] field = new String[fieldArr.length][3];
    for (int i = 0; i < fieldArr.length; i++) {
      String temp = fieldArr[i];
      temp = temp.substring(1, temp.length());
      field[i][0] = temp.substring(0, temp.indexOf("]"));
      field[i][1] = temp.substring(temp.indexOf("]") + 1);
      for (int j = 0; j < paraArr.length; j++) {
        temp = paraArr[j].trim().toUpperCase();
        if (field[i][1].trim().toUpperCase().equals(temp)) {
          field[i][2] = String.valueOf(j);
          break;
        } 
      } 
    } 
    return field;
  }
}
