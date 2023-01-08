package com.js.oa.userdb.action;

import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TableInfoAction extends Action {
  public void dblog(HttpServletRequest httpServletRequest, String moduleCode, String moduleName, String oprType, String oprContent) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    Date endDate = new Date();
    HttpSession sess = httpServletRequest.getSession(true);
    logBD.log(sess.getAttribute("userId").toString(), sess.getAttribute("userName").toString(), 
        sess.getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, 
        oprType, oprContent, httpServletRequest.getRemoteAddr(), sess.getAttribute("domainId").toString());
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String domainId = session.getAttribute("domainId").toString();
    String userName = session.getAttribute("userName").toString();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String moduleCode = "system_customdb_table";
    String moduleName = "自定义数据库";
    String oprType = "", oprContent = "";
    CustomDatabaseBD databaseBD = new CustomDatabaseBD();
    String operate = request.getParameter("operate");
    String tag = "add";
    if ("add".equals(operate)) {
      tag = "add";
      request.setAttribute("fieldTypeArray", databaseBD.getType());
      request.setAttribute("word", databaseBD.getTemplate("", "doc,wps"));
      request.setAttribute("excel", databaseBD.getTemplate("", "xls,et"));
    } else if ("save".equals(operate)) {
      tag = "save";
      String tableDisplayName = request.getParameter("tableDisplayName");
      oprType = "2";
      oprContent = "保存数据表：" + tableDisplayName;
      String tablecode = databaseBD.getMarkCode(domainId);
      String setBaseTableName = request.getParameter("setBaseTableName");
      String sysTableName = "";
      if ("1".equals(setBaseTableName)) {
        sysTableName = request.getParameter("baseTableName");
        if (sysTableName == null || "".equals(sysTableName))
          sysTableName = "jst_" + databaseBD.getTableName(domainId); 
      } else {
        sysTableName = "jst_" + databaseBD.getTableName(domainId);
      } 
      String modelId = databaseBD.getDefaultModelId(domainId);
      int tableId = databaseBD.addTable(tablecode, tableDisplayName, sysTableName, modelId, "/", userName, domainId, userId, orgId);
      if (tableId > 0) {
        String[] fieldDispalyName = request.getParameterValues("fieldDispalyName");
        String[] fieldType = request.getParameterValues("fieldType");
        String[] fieldTypeName = request.getParameterValues("fieldTypeName");
        String[] fieldLen = request.getParameterValues("fieldLen");
        String[] fieldShowType = request.getParameterValues("fieldShowType");
        String[] fieldShowValue = request.getParameterValues("fieldShowValue");
        String[] fieldDefault = request.getParameterValues("fieldDefault");
        String[] fieldIsNull = request.getParameterValues("fieldIsNull");
        String[] fieldIsSearch = request.getParameterValues("fieldIsSearch");
        String[] fieldIsTotal = request.getParameterValues("fieldIsTotal");
        String[] fieldListShow = request.getParameterValues("fieldListShow");
        String[] fieldSeq = request.getParameterValues("fieldSeq");
        String[] fieldWidth = request.getParameterValues("fieldWidth");
        String[] baseName = request.getParameterValues("baseName");
        String[] fieldShowTimeValues = request.getParameterValues("fieldShowTimeValue");
        if (baseName == null)
          baseName = new String[fieldDispalyName.length]; 
        if (fieldDispalyName != null)
          for (int i = 0; i < fieldDispalyName.length; i++) {
            if (fieldDispalyName[i] != null && fieldDispalyName[i].trim().length() > 0) {
              String fieldname;
              if (baseName[i] == null || "".equals(baseName[i]) || "null".equals(baseName[i])) {
                fieldname = String.valueOf(sysTableName) + "_" + databaseBD.getFieldName(domainId);
              } else {
                fieldname = baseName[i];
              } 
              String fieldcode = databaseBD.getFiledCode(domainId);
              databaseBD.addField(fieldType[i], String.valueOf(tableId), sysTableName, 
                  modelId, fieldDispalyName[i], 
                  fieldcode, fieldname, 
                  fieldTypeName[i], fieldLen[i], fieldIsNull[i], 
                  fieldDefault[i], "", 
                  "0", "0", 
                  "0", userName, fieldIsSearch[i], fieldIsTotal[i], domainId, fieldShowTimeValues[i]);
              databaseBD.setShow(fieldListShow[i], fieldWidth[i], fieldShowType[i], fieldShowValue[i], fieldname, fieldSeq[i], String.valueOf(tableId));
            } 
          }  
        String[] totalValue = request.getParameterValues("checkBoxSet");
        databaseBD.setTotalValue(fieldDispalyName, (new StringBuilder(String.valueOf(tableId))).toString(), totalValue, fieldIsTotal);
        setName(setBaseTableName, (new StringBuilder(String.valueOf(tableId))).toString());
      } 
      String opResult = request.getParameter("opResult");
      if ("continue".equals(opResult)) {
        tag = "add";
        request.setAttribute("setBaseTableName", setBaseTableName);
        request.setAttribute("fieldTypeArray", databaseBD.getType());
        return new ActionForward("/TableInfoAction.do?operate=add");
      } 
      if ("saveonly".equals(opResult)) {
        dblog(request, moduleCode, moduleName, oprType, oprContent);
        return new ActionForward("/TableInfoAction.do?reload=yes&operate=load&tableId=" + tableId + "&pager.offset=0");
      } 
      dblog(request, moduleCode, moduleName, oprType, oprContent);
    } else if ("update".equals(operate)) {
      tag = "update";
      oprType = "2";
      String tableId = request.getParameter("tableId");
      String tableName = request.getParameter("tableDisplayName");
      String tableSysName = request.getParameter("tableSysName");
      oprContent = "修改表:" + tableName + " 表结构";
      String modelId = databaseBD.getDefaultModelId(domainId);
      int updateTableSuccess = databaseBD.updateSimpleTable(tableId, tableName, domainId);
      if (updateTableSuccess > 0) {
        String[] fieldId = request.getParameterValues("fieldId");
        String[] fieldSysName = request.getParameterValues("fieldSysName");
        String[] fieldDispalyName = request.getParameterValues("fieldDispalyName");
        String[] fieldType = request.getParameterValues("fieldType");
        String[] fieldTypeName = request.getParameterValues("fieldTypeName");
        String[] fieldLen = request.getParameterValues("fieldLen");
        String[] fieldShowType = request.getParameterValues("fieldShowType");
        String[] fieldShowValue = request.getParameterValues("fieldShowValue");
        String[] fieldDefault = request.getParameterValues("fieldDefault");
        String[] fieldIsNull = request.getParameterValues("fieldIsNull");
        String[] fieldIsSearch = request.getParameterValues("fieldIsSearch");
        String[] fieldIsTotal = request.getParameterValues("fieldIsTotal");
        String[] fieldListShow = request.getParameterValues("fieldListShow");
        String[] fieldSeq = request.getParameterValues("fieldSeq");
        String[] fieldWidth = request.getParameterValues("fieldWidth");
        String[] fieldBoxSearch = request.getParameterValues("fieldBoxSearch");
        String[] fieldChangeMethod = request.getParameterValues("fieldChangeMethod");
        String[] fieldFetchSql = request.getParameterValues("fieldFetchSql");
        String[] fieldToForm = request.getParameterValues("fieldToForm");
        String[] fieldScript = request.getParameterValues("fieldScript");
        String[] fieldFilterList = request.getParameterValues("fieldFilterList");
        String[] fieldFilterSearch = request.getParameterValues("fieldFilterSearch");
        String[] inputSearchType = request.getParameterValues("inputSearchType");
        String[] startRange = request.getParameterValues("startRange");
        String[] handleRange = request.getParameterValues("handleRange");
        String[] startText = request.getParameterValues("startText");
        String[] handleText = request.getParameterValues("handleText");
        String[] fieldInterfaceName = request.getParameterValues("fieldInterfaceName");
        String[] fieldInterfaceMethodName = request.getParameterValues("fieldInterfaceMethodName");
        String[] fieldInterfaceMethodPara = request.getParameterValues("fieldInterfaceMethodPara");
        String[] fieldInterfacetype = request.getParameterValues("fieldInterfacetype");
        String[] fieldShowTimeValues = request.getParameterValues("fieldShowTimeValue");
        String[] baseName = request.getParameterValues("baseName");
        String setBaseTableName = request.getParameter("setBaseTableName");
        String empId = request.getParameter("emp_Id");
        databaseBD.updateTableEmp(tableId, empId);
        String delFieldSysName = databaseBD.getShouldDelId(tableId, fieldId);
        if (delFieldSysName != null && !"".equals(delFieldSysName)) {
          oprContent = String.valueOf(oprContent) + "删除字段：" + delFieldSysName + " ";
          databaseBD.batchDeleteField(tableSysName, delFieldSysName.split(","));
        } 
        if (fieldDispalyName != null) {
          String tString = " 新增字段：";
          String string = " 修改字段";
          for (int i = 0; i < fieldDispalyName.length; i++) {
            String fieldChangeMethodTemp, fetchSqlTemp, scriptTemp, toFFieldTemp, fieldShowTemp = fieldShowType[i];
            String fieldShowValueTemp = fieldShowValue[i];
            if (fieldShowTemp.equals("105") && !fieldShowValueTemp.startsWith("*") && !fieldShowValueTemp.startsWith("@") && 
              !fieldShowValueTemp.startsWith("$") && !fieldShowValueTemp.startsWith("#")) {
              fieldChangeMethodTemp = "0";
              fetchSqlTemp = "";
              scriptTemp = "";
              toFFieldTemp = "";
            } else {
              fieldChangeMethodTemp = "".equals(fieldChangeMethod[i]) ? "0" : fieldChangeMethod[i];
              fetchSqlTemp = fieldFetchSql[i];
              scriptTemp = fieldScript[i];
              toFFieldTemp = fieldToForm[i];
            } 
            String field_InterfaceName = null, field_Interfacetype = "", field_InterfaceMethodName = null, field_InterfaceMethodPara = null;
            String defaultShowTimeValue = fieldShowTimeValues[i];
            if (fieldShowTemp.equals("105")) {
              field_InterfaceName = fieldInterfaceName[i];
              field_InterfaceMethodName = fieldInterfaceMethodName[i];
              field_InterfaceMethodPara = fieldInterfaceMethodPara[i];
            } 
            field_Interfacetype = fieldInterfacetype[i];
            if (fieldDispalyName[i] != null && fieldDispalyName[i].trim().length() > 0 && (
              "".equals(fieldSysName[i]) || delFieldSysName.indexOf(fieldSysName[i]) < 0))
              if ("-1".equals(fieldId[i])) {
                String fieldname;
                if (baseName[i] == null || "".equals(baseName[i]) || "null".equals(baseName[i])) {
                  fieldname = String.valueOf(tableSysName) + "_" + databaseBD.getFieldName(domainId);
                } else {
                  fieldname = baseName[i];
                } 
                oprContent = String.valueOf(oprContent) + tString;
                tString = "";
                oprContent = String.valueOf(oprContent) + fieldname + ",";
                String fieldcode = databaseBD.getFiledCode(domainId);
                databaseBD.addField(fieldType[i], String.valueOf(tableId), tableSysName, 
                    modelId, fieldDispalyName[i], 
                    fieldcode, fieldname, 
                    fieldTypeName[i], fieldLen[i], fieldIsNull[i], 
                    fieldDefault[i], "", 
                    "0", "0", 
                    "0", userName, fieldIsSearch[i], fieldIsTotal[i], domainId, fieldShowTimeValues[i]);
                databaseBD.setShow(fieldListShow[i], fieldWidth[i], fieldShowType[i], fieldShowValue[i], fieldname, fieldSeq[i], String.valueOf(tableId));
              } else {
                oprContent = String.valueOf(oprContent) + string;
                string = "";
                databaseBD.updateSimpleField(fieldId[i], 
                    tableId, 
                    tableSysName, 
                    fieldType[i], 
                    fieldSysName[i], 
                    fieldIsNull[i], 
                    fieldDefault[i], 
                    fieldDispalyName[i], 
                    fieldLen[i], 
                    fieldIsSearch[i], 
                    fieldIsTotal[i], 
                    fieldBoxSearch[i], 
                    fieldChangeMethodTemp, 
                    fetchSqlTemp, 
                    toFFieldTemp, 
                    scriptTemp, 
                    fieldFilterList[i], 
                    fieldFilterSearch[i], 
                    inputSearchType[i], 
                    startRange[i], 
                    handleRange[i], 
                    startText[i], 
                    handleText[i], 
                    field_InterfaceName, 
                    field_InterfaceMethodName, 
                    field_InterfaceMethodPara, 
                    field_Interfacetype, 
                    defaultShowTimeValue);
                databaseBD.setShow(fieldListShow[i], fieldWidth[i], fieldShowType[i], fieldShowValue[i], fieldSysName[i], fieldSeq[i], String.valueOf(tableId));
              }  
          } 
        } 
        String[] totalValue = request.getParameterValues("checkBoxSet");
        databaseBD.setTotalValue(fieldDispalyName, tableId, totalValue, fieldIsTotal);
        setName(setBaseTableName, (new StringBuilder(String.valueOf(tableId))).toString());
      } else if (updateTableSuccess != -1) {
      
      } 
      String opResult = request.getParameter("opResult");
      if ("saveonly".equals(opResult)) {
        dblog(request, moduleCode, moduleName, oprType, oprContent);
        return new ActionForward("/TableInfoAction.do?reload=yes&operate=load&tableId=" + tableId + "&pager.offset=0");
      } 
      dblog(request, moduleCode, moduleName, oprType, oprContent);
    } else if ("load".equals(operate)) {
      tag = "load";
      String tableId = request.getParameter("tableId");
      if (request.getParameter("reload") != null)
        request.setAttribute("reload", "yes"); 
      String[][] tableInfo = databaseBD.getSimpleTableInfo(tableId, domainId);
      String[][] fieldInfoList = databaseBD.getAllFieldInfo(tableId, domainId);
      request.setAttribute("setBaseTableName", tableInfo[0][6]);
      request.setAttribute("word", databaseBD.getTemplate("", "doc,wps"));
      request.setAttribute("excel", databaseBD.getTemplate("", "xls,et"));
      request.setAttribute("tableInfo", tableInfo);
      request.setAttribute("fieldInfoList", fieldInfoList);
      request.setAttribute("fieldTypeArray", databaseBD.getType());
      String empId = databaseBD.getTableEmp(tableId);
      request.setAttribute("empId", empId);
    } 
    return mapping.findForward(tag);
  }
  
  public void setName(String flag, String tableId) {
    CustomDatabaseBD databaseBD = new CustomDatabaseBD();
    databaseBD.setName(flag, tableId);
  }
}
