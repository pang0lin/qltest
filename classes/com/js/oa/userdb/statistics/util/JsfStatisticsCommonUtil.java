package com.js.oa.userdb.statistics.util;

import com.js.oa.module.po.ModuleMenuPO;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

public class JsfStatisticsCommonUtil {
  public String getModuleSearchParam(String domainId, String menuId, HttpServletRequest request) {
    ModuleMenuService bd = new ModuleMenuService();
    ModuleMenuPO po = 
      bd.loadMenuConfig(domainId, menuId)
      .get(0);
    CustomDatabaseBD dbBD = new CustomDatabaseBD();
    String[][] queryFields = (String[][])null;
    if (po.getMenuListQueryConditionElements() != null && 
      po.getMenuListQueryConditionElements().length() > 0) {
      queryFields = bd.getQueryField(po
          .getMenuListQueryConditionElements()
          .toString(), domainId.toString());
    } else {
      queryFields = dbBD.getQueryField(po.getMenuListTableMap()
          .toString());
    } 
    request.setAttribute("queryFields", queryFields);
    String[][] paras = (String[][])null;
    String databaseType = SystemCommon.getDatabaseType();
    String valideDateCondition = "";
    String[] validDateFields = (String[])null;
    String[] validDateExp = (String[])null;
    if (queryFields != null && queryFields.length > 0) {
      paras = new String[queryFields.length][4];
      validDateFields = new String[queryFields.length];
      validDateExp = new String[queryFields.length];
      for (int i = 0; i < queryFields.length; i++) {
        paras[i][0] = queryFields[i][2];
        paras[i][1] = request.getParameter(String.valueOf(queryFields[i][2]) + 
            "_type");
        paras[i][3] = "<input type='hidden' id='" + paras[i][0] + 
          "_hid' name='" + paras[i][0] + 
          "_hid' value='" + 
          request.getParameter(paras[i][0]) + 
          "'/>";
        if ("403".equals(queryFields[i][3])) {
          validDateFields[0] = queryFields[i][2];
          validDateExp[0] = queryFields[i][4];
        } else if ("varchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + 
            request.getParameter(queryFields[i][2]) + 
            "%' ";
        } else if ("raidonumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("raidovarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
            request.getParameter(queryFields[i][2]) + 
            "'";
        } else if ("selectnumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("selectvarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
            request.getParameter(queryFields[i][2]) + 
            "'";
        } else if ("checkbox".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          String[] values = request.getParameterValues(queryFields[i][2]);
          paras[i][2] = " ( ";
          for (int j = 0; j < values.length; j++)
            paras[i][2] = String.valueOf(paras[i][2]) + paras[i][0] + "='" + values[j] + "' or " + paras[i][0] + " like '%" + 
              values[j] + ",%' or "; 
          paras[i][2] = String.valueOf(paras[i][2].substring(0, 
                paras[i][2].lastIndexOf("or"))) + 
            ") ";
        } else if ("radiovarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + 
            request.getParameter(queryFields[i][2]) + 
            "%'";
        } else if ("radionumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("date".equals(paras[i][1]) || 
          "time".equals(paras[i][1]) || 
          "datetime".equals(paras[i][1])) {
          if ("1".equals(request.getParameter(String.valueOf(queryFields[i][2]) + 
                "date"))) {
            String end = "";
            String start = request.getParameter(String.valueOf(queryFields[i][2]) + 
                "_start");
            end = request.getParameter(String.valueOf(queryFields[i][2]) + 
                "_end");
            paras[i][3] = "<input type='hidden' id='" + 
              paras[i][0] + 
              "_start_hid' name='" + 
              paras[i][0] + 
              "_start_hid' value='" + start + "'/>" + 
              "<input type='hidden' id='" + 
              paras[i][0] + 
              "_end_hid' value='" + end + "'/>" + 
              "<input type='hidden' id='" + 
              paras[i][0] + 
              "dateHid' name='" + 
              paras[i][0] + 
              "dateHid' value='" + 
              request.getParameter(String.valueOf(queryFields[i][2]) + 
                "date") + "' />";
            if ("date".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'YYYY-MM-DD')" + 
                  " between to_date('" + 
                  start + 
                  "','YYYY-MM-DD') and to_date('" + 
                  end + 
                  "','YYYY-MM-DD') ";
              } else if ("mysql".equals(databaseType)) {
                paras[i][2] = String.valueOf(paras[i][0]) + 
                  " between '" + start + "' and '" + 
                  end + "'";
              } else {
                paras[i][2] = " cast(" + paras[i][0] + 
                  " as datetime)" + 
                  " between cast('" + 
                  start + 
                  "' as datetime) and cast('" + 
                  end + 
                  "' as datetime) ";
              } 
            } else if ("time".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'HH:MM:SS') " + 
                  " between to_date('" + 
                  start + 
                  "','HH:MI:SS') and to_date('" + 
                  end + "','HH:MI:SS') ";
              } else {
                paras[i][2] = String.valueOf(paras[i][0]) + " > '" + 
                  start + "' and " + paras[i][0] + 
                  "< '" + 
                  end + "'";
              } 
            } else if ("datetime".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'YYYY-MM-DD HH:MI:SS') " + 
                  " between to_date('" + 
                  start + 
                  "','YYYY-MM-DD HH:MI:SS') and to_date('" + 
                  end + 
                  "',YYYY-MM-DD HH:MI:SS) ";
              } else if ("mysql".equals(databaseType)) {
                paras[i][2] = String.valueOf(paras[i][0]) + 
                  " between '" + start + "' and '" + 
                  end + "'";
              } else {
                paras[i][2] = " cast(" + paras[i][0] + 
                  " as datetime) " + 
                  " between cast('" + 
                  start + 
                  "' as datetime) and cast('" + 
                  end + "' as datetime) ";
              } 
            } 
          } 
        } else if ("number".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("float".equals(paras[i][1])) {
          String js = "";
          if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
            paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
          } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
          } 
          request.setAttribute("js", js);
        } 
      } 
    } 
    String creatParaSql = "";
    String tableStr = po.getMenuListTableName();
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    if (queryFields == null || queryFields.length <= 0) {
      request.setAttribute("searchPart", null);
    } else {
      for (int i = 0; i < queryFields.length; i++)
        map.put(queryFields[i][2], 
            dbBD.getQueryFieldHTML(queryFields[i][0])); 
    } 
    StringBuffer sbf = new StringBuffer("");
    if (queryFields != null && queryFields.length > 0) {
      int pt = 0;
      int colms = 2;
      int rows = queryFields.length / colms + 1;
      HashMap<Object, Object> dateMap = new HashMap<Object, Object>();
      for (int j = 0; j < rows; j++) {
        sbf.append("<tr>");
        if (j == 0)
          sbf.append("<td width=20 rowspan='10'/>"); 
        for (int i = 0;; i++);
        if (j == 0)
          sbf.append("<td width=16 rowspan='10'/>"); 
        sbf.append("</tr>");
        if (pt >= queryFields.length)
          break; 
        continue;
      } 
      if (dateMap != null && dateMap.size() > 0) {
        Iterator<String> it = dateMap.keySet().iterator();
        while (it.hasNext()) {
          String key = it.next();
          sbf.append("<tr><td>");
          sbf.append(String.valueOf(key) + "：");
          sbf.append("</td><td colspan=\"3\">").append(dateMap.get(key)).append("</td></tr>");
        } 
      } 
      String createDate = request.getParameter("createDate");
      String createStartDate = request.getParameter("createStartDate");
      String createEndDate = request.getParameter("createEndDate");
      String checkStr = "";
      if (createDate != null && "1".equals(createDate)) {
        if (DbOpt.dbtype.indexOf("oracle") >= 0) {
          creatParaSql = String.valueOf(creatParaSql) + " and to_date(" + tableStr + "_date,'YYYY-MM-DD') between to_date('" + createStartDate + "','YYYY-MM-DD') and to_date('" + createEndDate + "','YYYY-MM-DD') ";
        } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
          creatParaSql = String.valueOf(creatParaSql) + " and " + tableStr + "_date>= convert(datetime,'" + createStartDate + "') and " + tableStr + "_date<=convert(datetime,'" + createEndDate + " 23:59:59') ";
        } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          creatParaSql = String.valueOf(creatParaSql) + " and " + tableStr + "_date>= date_format('" + createStartDate + "','%Y-%m-%d') and " + tableStr + "_date<=date_format('" + createEndDate + " 23:59:59','%Y-%m-%d %H:%i:%s') ";
        } 
        checkStr = "checked";
      } 
      String d = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
      if (createStartDate == null || "".equals(createStartDate))
        createStartDate = d; 
      if (createEndDate == null || "".equals(createEndDate))
        createEndDate = d; 
      sbf.append("");
      sbf.append("<tr>");
      sbf.append("<td>创建时间：</td>");
      sbf.append("<td><input type=\"text\" name=\"createStartDate\" class=\"inputText\" value=\"" + createStartDate + "\" onclick=\"setDay(this)\" style=\"background:url('/jsoa/eform/images/down_arrow.gif')\">&nbsp;至&nbsp;<input type=\"text\" name=\"createEndDate\" onclick=\"setDay(this)\" class=\"inputText\"  value=\"" + createEndDate + "\"  style=\"background:url('/jsoa/eform/images/down_arrow.gif')\"><input type=\"checkbox\" name=\"createDate\" id=\"createDate\" value=\"1\"" + checkStr + " ></td>");
      sbf.append("</tr>");
      sbf.append(
          "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align='right'>").append(
          "<input type=\"button\" class=\"btnButton4font\" onClick=\"searchAction();\" value=\"查询\"/>").append(
          "<input type=\"button\" class=\"btnButton2font\" onClick=\"clearSearch();searchAction();\" value=\"重置\" />").append(
          "<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>").append(
          "").append("</td></tr>");
      request.setAttribute("searchPart", sbf.toString());
    } else {
      sbf = new StringBuffer("");
      sbf.append("<tr>");
      sbf.append("<td width=20 rowspan='10'/>");
      String createDate = request.getParameter("createDate");
      String createStartDate = request.getParameter("createStartDate");
      String createEndDate = request.getParameter("createEndDate");
      String checkStr = "";
      if (createDate != null && "1".equals(createDate)) {
        if (DbOpt.dbtype.indexOf("oracle") >= 0) {
          creatParaSql = String.valueOf(creatParaSql) + " and to_date(" + tableStr + "_date,'YYYY-MM-DD') between to_date('" + createStartDate + "','YYYY-MM-DD') and to_date('" + createEndDate + "','YYYY-MM-DD') ";
        } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
          creatParaSql = String.valueOf(creatParaSql) + " and " + tableStr + "_date>= convert(datetime,'" + createStartDate + "') and " + tableStr + "_date<=convert(datetime,'" + createEndDate + " 23:59:59') ";
        } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          creatParaSql = String.valueOf(creatParaSql) + " and " + tableStr + "_date>= date_format('" + createStartDate + "','%Y-%m-%d') and " + tableStr + "_date<=date_format('" + createEndDate + " 23:59:59','%Y-%m-%d %H:%i:%s') ";
        } 
        checkStr = "checked";
      } 
      String d = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
      if (createStartDate == null || "".equals(createStartDate))
        createStartDate = d; 
      if (createEndDate == null || "".equals(createEndDate))
        createEndDate = d; 
      sbf.append("");
      sbf.append("<tr>");
      sbf.append("<td>创建时间：</td>");
      sbf.append("<td><input type=\"text\" name=\"createStartDate\" class=\"inputText\" value=\"" + createStartDate + "\" onclick=\"setDay(this)\" style=\"background:url('/jsoa/eform/images/down_arrow.gif')\">&nbsp;至&nbsp;<input type=\"text\" name=\"createEndDate\" onclick=\"setDay(this)\" class=\"inputText\"  value=\"" + createEndDate + "\"  style=\"background:url('/jsoa/eform/images/down_arrow.gif')\"><input type=\"checkbox\" name=\"createDate\" id=\"createDate\" value=\"1\"" + checkStr + " ></td>");
      sbf.append("</tr>");
      sbf.append(
          "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align='right'>").append(
          "<input type=\"button\" class=\"btnButton4font\" onClick=\"searchAction();\" value=\"查询\"/>").append(
          "<input type=\"button\" class=\"btnButton2font\" onClick=\"clearSearch();searchAction();\" value=\"重置\" />").append(
          "<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>").append(
          "").append("</td></tr>");
      request.setAttribute("searchPart", sbf.toString());
    } 
    String parameters = "";
    String defConstrain = po.getMenuDefQueryCondition();
    if (paras != null && paras.length > 0) {
      if (defConstrain == null || defConstrain.length() <= 0)
        parameters = String.valueOf(parameters) + " "; 
      for (int i = 0; i < paras.length; i++) {
        if (paras[i][2] != null && 
          paras[i][2].length() > 0)
          parameters = String.valueOf(parameters) + " and " + paras[i][2]; 
      } 
    } 
    if (!"".equals(creatParaSql))
      parameters = String.valueOf(parameters) + creatParaSql; 
    return parameters;
  }
  
  public void getZaiBanMonitorSearchParam(String tableId, HttpServletRequest request) {
    ModuleMenuService bd = new ModuleMenuService();
    CustomDatabaseBD dbBD = new CustomDatabaseBD();
    CustomDatabaseBD bdc = new CustomDatabaseBD();
    String[][] queryFields = bdc.getQueryField(tableId);
    request.setAttribute("queryFields", queryFields);
    String[][] paras = (String[][])null;
    String databaseType = SystemCommon.getDatabaseType();
    String valideDateCondition = "";
    String[] validDateFields = (String[])null;
    String[] validDateExp = (String[])null;
    if (queryFields != null && queryFields.length > 0) {
      paras = new String[queryFields.length][4];
      validDateFields = new String[queryFields.length];
      validDateExp = new String[queryFields.length];
      for (int i = 0; i < queryFields.length; i++) {
        paras[i][0] = queryFields[i][2];
        paras[i][1] = request.getParameter(String.valueOf(queryFields[i][2]) + 
            "_type");
        paras[i][3] = "<input type='hidden' id='" + paras[i][0] + 
          "_hid' name='" + paras[i][0] + 
          "_hid' value='" + 
          request.getParameter(paras[i][0]) + 
          "'/>";
        if ("403".equals(queryFields[i][3])) {
          validDateFields[0] = queryFields[i][2];
          validDateExp[0] = queryFields[i][4];
        } else if ("varchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + 
            request.getParameter(queryFields[i][2]) + 
            "%' ";
        } else if ("raidonumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("raidovarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
            request.getParameter(queryFields[i][2]) + 
            "'";
        } else if ("selectnumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("selectvarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
            request.getParameter(queryFields[i][2]) + 
            "'";
        } else if ("checkbox".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          String[] values = request.getParameterValues(queryFields[i][2]);
          paras[i][2] = " ( ";
          for (int j = 0; j < values.length; j++)
            paras[i][2] = String.valueOf(paras[i][2]) + paras[i][0] + "='" + values[j] + "' or " + paras[i][0] + " like '%" + 
              values[j] + ",%' or "; 
          paras[i][2] = String.valueOf(paras[i][2].substring(0, 
                paras[i][2].lastIndexOf("or"))) + 
            ") ";
        } else if ("radiovarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + 
            request.getParameter(queryFields[i][2]) + 
            "%'";
        } else if ("radionumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("date".equals(paras[i][1]) || 
          "time".equals(paras[i][1]) || 
          "datetime".equals(paras[i][1])) {
          if ("1".equals(request.getParameter(String.valueOf(queryFields[i][2]) + 
                "date"))) {
            String end = "";
            String start = request.getParameter(String.valueOf(queryFields[i][2]) + 
                "_start");
            end = request.getParameter(String.valueOf(queryFields[i][2]) + 
                "_end");
            paras[i][3] = "<input type='hidden' id='" + 
              paras[i][0] + 
              "_start_hid' name='" + 
              paras[i][0] + 
              "_start_hid' value='" + start + "'/>" + 
              "<input type='hidden' id='" + 
              paras[i][0] + 
              "_end_hid' value='" + end + "'/>" + 
              "<input type='hidden' id='" + 
              paras[i][0] + 
              "dateHid' name='" + 
              paras[i][0] + 
              "dateHid' value='" + 
              request.getParameter(String.valueOf(queryFields[i][2]) + 
                "date") + "' />";
            if ("date".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'YYYY-MM-DD')" + 
                  " between to_date('" + 
                  start + 
                  "','YYYY-MM-DD') and to_date('" + 
                  end + 
                  "','YYYY-MM-DD') ";
              } else if ("mysql".equals(databaseType)) {
                paras[i][2] = String.valueOf(paras[i][0]) + 
                  " between '" + start + "' and '" + 
                  end + "'";
              } else {
                paras[i][2] = " cast(" + paras[i][0] + 
                  " as datetime)" + 
                  " between cast('" + 
                  start + 
                  "' as datetime) and cast('" + 
                  end + 
                  "' as datetime) ";
              } 
            } else if ("time".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'HH:MM:SS') " + 
                  " between to_date('" + 
                  start + 
                  "','HH:MI:SS') and to_date('" + 
                  end + "','HH:MI:SS') ";
              } else {
                paras[i][2] = String.valueOf(paras[i][0]) + " > '" + 
                  start + "' and " + paras[i][0] + 
                  "< '" + 
                  end + "'";
              } 
            } else if ("datetime".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'YYYY-MM-DD HH:MI:SS') " + 
                  " between to_date('" + 
                  start + 
                  "','YYYY-MM-DD HH:MI:SS') and to_date('" + 
                  end + 
                  "',YYYY-MM-DD HH:MI:SS) ";
              } else if ("mysql".equals(databaseType)) {
                paras[i][2] = String.valueOf(paras[i][0]) + 
                  " between '" + start + "' and '" + 
                  end + "'";
              } else {
                paras[i][2] = " cast(" + paras[i][0] + 
                  " as datetime) " + 
                  " between cast('" + 
                  start + 
                  "' as datetime) and cast('" + 
                  end + "' as datetime) ";
              } 
            } 
          } 
        } else if ("number".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("float".equals(paras[i][1])) {
          String js = "";
          if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
            paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
          } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
          } 
          request.setAttribute("js", js);
        } 
      } 
    } 
    String creatParaSql = "";
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    if (queryFields == null || queryFields.length <= 0) {
      request.setAttribute("searchPart", null);
    } else {
      for (int i = 0; i < queryFields.length; i++)
        map.put(queryFields[i][2], 
            dbBD.getQueryFieldHTML(queryFields[i][0])); 
    } 
    StringBuffer sbf = new StringBuffer("");
    if (queryFields != null && queryFields.length > 0) {
      int pt = 0;
      int colms = 2;
      int rows = queryFields.length / colms + 1;
      HashMap<Object, Object> dateMap = new HashMap<Object, Object>();
      for (int j = 0; j < rows; j++) {
        sbf.append("<tr>");
        if (j == 0)
          sbf.append("<td width=20 rowspan='10'/>"); 
        for (int i = 0;; i++);
        if (j == 0)
          sbf.append("<td width=16 rowspan='10'/>"); 
        sbf.append("</tr>");
        if (pt >= queryFields.length)
          break; 
        continue;
      } 
      if (dateMap != null && dateMap.size() > 0) {
        Iterator<String> it = dateMap.keySet().iterator();
        while (it.hasNext()) {
          String key = it.next();
          sbf.append("<tr><td>");
          sbf.append(String.valueOf(key) + "：");
          sbf.append("</td><td colspan=\"3\">").append(dateMap.get(key)).append("</td></tr>");
        } 
      } 
      String createDate = request.getParameter("createDate");
      String createStartDate = request.getParameter("createStartDate");
      String createEndDate = request.getParameter("createEndDate");
      String checkStr = "";
      if (createDate != null && "1".equals(createDate))
        checkStr = "checked"; 
      String d = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
      if (createStartDate == null || "".equals(createStartDate))
        createStartDate = d; 
      if (createEndDate == null || "".equals(createEndDate))
        createEndDate = d; 
      sbf.append("");
      sbf.append("<tr>");
      sbf.append("<td>创建时间：</td>");
      sbf.append("<td><input type=\"text\" name=\"createStartDate\" class=\"inputText\" value=\"" + createStartDate + "\" onclick=\"setDay(this)\" style=\"background:url('/jsoa/eform/images/down_arrow.gif')\">&nbsp;至&nbsp;<input type=\"text\" name=\"createEndDate\" onclick=\"setDay(this)\" class=\"inputText\"  value=\"" + createEndDate + "\"  style=\"background:url('/jsoa/eform/images/down_arrow.gif')\"><input type=\"checkbox\" name=\"createDate\" id=\"createDate\" value=\"1\"" + checkStr + " ></td>");
      sbf.append("</tr>");
      sbf.append(
          "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align='right'>").append(
          "<input type=\"button\" class=\"btnButton4font\" onClick=\"searchAction();\" value=\"查询\"/>").append(
          "<input type=\"button\" class=\"btnButton2font\" onClick=\"clearSearch();searchAction();\" value=\"重置\" />").append(
          "<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>").append(
          "").append("</td></tr>");
      request.setAttribute("searchPart", sbf.toString());
    } else {
      sbf = new StringBuffer("");
      sbf.append("<tr>");
      sbf.append("<td width=20 rowspan='10'/>");
      String createDate = request.getParameter("createDate");
      String createStartDate = request.getParameter("createStartDate");
      String createEndDate = request.getParameter("createEndDate");
      String checkStr = "";
      if (createDate != null && "1".equals(createDate))
        checkStr = "checked"; 
      String d = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
      if (createStartDate == null || "".equals(createStartDate))
        createStartDate = d; 
      if (createEndDate == null || "".equals(createEndDate))
        createEndDate = d; 
      sbf.append("");
      sbf.append("<tr>");
      sbf.append("<td>创建时间：</td>");
      sbf.append("<td><input type=\"text\" name=\"createStartDate\" class=\"inputText\" value=\"" + createStartDate + "\" onclick=\"setDay(this)\" style=\"background:url('/jsoa/eform/images/down_arrow.gif')\">&nbsp;至&nbsp;<input type=\"text\" name=\"createEndDate\" onclick=\"setDay(this)\" class=\"inputText\"  value=\"" + createEndDate + "\"  style=\"background:url('/jsoa/eform/images/down_arrow.gif')\"><input type=\"checkbox\" name=\"createDate\" id=\"createDate\" value=\"1\"" + checkStr + " ></td>");
      sbf.append("</tr>");
      sbf.append(
          "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align='right'>").append(
          "<input type=\"button\" class=\"btnButton4font\" onClick=\"searchAction();\" value=\"查询\"/>").append(
          "<input type=\"button\" class=\"btnButton2font\" onClick=\"clearSearch();searchAction();\" value=\"重置\" />").append(
          "<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>").append(
          "").append("</td></tr>");
      request.setAttribute("searchPart", sbf.toString());
    } 
  }
}
