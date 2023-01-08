package com.js.oa.eform.service;

import com.js.oa.eform.bean.CustomFormEJBBean;
import com.js.oa.eform.bean.CustomFormEJBHome;
import com.js.oa.eform.po.TAreaPO;
import com.js.oa.eform.po.TEltPO;
import com.js.oa.eform.po.TPagePO;
import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.eform.weixin.ElementHTMLGetterFactory;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.util.InitWorkFlowData;
import com.js.oa.search.client.SearchService;
import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.oa.userdb.newCode.service.CodeManageService;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.IO2File;
import com.js.util.util.InfoUtil;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class CustomFormBD extends HibernateBase {
  private static Logger logger = Logger.getLogger(
      CustomFormBD.class.getName());
  
  public String getCode(String id) {
    String code = "";
    try {
      code = (new CustomFormEJBBean()).getCode(id);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("CustomFormBD error on getCode information:" + 
          e.getMessage());
    } 
    code = code.replaceAll("<TD style=\"", "<TD style=\"word-break: break-all;");
    code = code.replaceAll("<td style=\"", "<td style=\"word-break: break-all;");
    return code;
  }
  
  public String getCodeByBatChAdd(String id) {
    String code = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      code = ejbProxy.invoke("getCodeByBatChAdd", pg.getParameters()).toString();
    } catch (Exception e) {
      logger.error("CustomFormBD error on getCode information:" + 
          e.getMessage());
    } finally {}
    return code;
  }
  
  public List getHostList(String pageId, String foreignkey) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(pageId, String.class);
      pg.put(foreignkey, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      list = (List)ejbProxy.invoke("getHostList", pg.getParameters());
    } catch (Exception e) {
      logger.error("得到主表信息是出错:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSubList(String id, String ids) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      list = (List)ejbProxy.invoke("getSubList", pg.getParameters());
    } catch (Exception e) {
      logger.error("得到子表信息是出错:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public String getTable(String pageId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = ejbProxy.invoke("getTable", pg.getParameters()).toString();
    } catch (Exception e) {
      logger.error("CustomFormBD error on getTable information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[] getField(String pageId) {
    String[] result = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[])ejbProxy.invoke("getField", pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getField information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[] getAllTableField(String pageId) {
    String[] result = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[])ejbProxy.invoke("getAllTableField", pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getField information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getPageField(String pageId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getPageField", pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getPageField information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getForeignField(String pageId) {
    DbOpt dbopt = null;
    String[][] result = (String[][])null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr2("select distinct a.ELT_NAME,b.area_table,a.ELT_TABLE,c.field_id from TELT a,TAREA b,tfield c,ttable d where a.PAGE_ID=" + pageId + 
          " and a.AREA_ID=b.AREA_ID and b.area_name<>'form1' and b.area_table=d.table_name and c.field_table=d.table_id and a.elt_table=c.field_name order by b.area_table desc", 4);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      logger.error("CustomFormBD error on getForeignField information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getForeignFieldList(String pageId, String table, String domainId) {
    DbOpt dbopt = null;
    String[][] result = (String[][])null;
    try {
      dbopt = new DbOpt();
      String sql = "select distinct c.field_id,c.field_show,c.field_type,c.FIELD_NULL,c.FIELD_DEFAULT,c.field_name,c.field_value,c.field_len,c.field_desname,c.field_changemethod,c.field_index from TELT a,TAREA b,tFIELD c,ttable e where a.PAGE_ID=" + 
        
        pageId + " and a.AREA_ID=b.AREA_ID and b.area_name=e.table_name and c.field_table=e.table_id and b.area_name='" + table + "'" + 
        " and a.ELT_TABLE=c.field_name and c.domain_id=" + domainId;
      result = dbopt.executeQueryToStrArr2(sql, 11);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return result;
  }
  
  public String[][] getFieldInfo(String pageId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getFieldInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getField information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getForeignFieldInfo(String pageId, String table) {
    DbOpt dbopt = null;
    String[][] result = (String[][])null;
    try {
      dbopt = new DbOpt();
      if (table != null) {
        result = dbopt.executeQueryToStrArr2("select distinct a.ELT_TABLE,c.field_only,c.field_show,b.area_name from TELT a ,tarea b,tfield c,ttable d where b.area_name=d.table_name and c.field_table=d.table_id and b.area_name='" + 
            table + "' and b.AREA_id=a.AREA_id and a.ELT_TABLE=c.field_name " + 
            " and a.PAGE_ID=" + pageId, 4);
      } else {
        result = dbopt.executeQueryToStrArr2("select distinct a.ELT_TABLE,c.field_only,c.field_show,b.area_name from TELT a ,tarea b,tfield c,ttable d where b.area_name=d.table_name and c.field_table=d.table_id and b.area_name<>'form1' and b.AREA_id=a.AREA_id and a.ELT_TABLE=c.field_name  and a.PAGE_ID=" + 
            
            pageId, 4);
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      logger.error("CustomFormBD error on getForeignFieldInfo information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[] getForeignTable(String pageId) {
    DbOpt dbopt = null;
    String[] result = (String[])null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr1("select distinct b.AREA_NAME from tarea b where b.area_name<>'form1' and b.PAGE_ID=" + 
          pageId);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      logger.error("CustomFormBD error on getForeignTable information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String getForeignTableHTML(String pageId) {
    String result = "";
    String[][] forFlds = getForeignField(pageId);
    if (forFlds != null && forFlds.length > 0)
      for (int i = 0; i < forFlds.length; i++) {
        if (i == 0) {
          result = String.valueOf(result) + "<input type=hidden id=" + forFlds[i][1] + " name=" + forFlds[i][1] + " value=";
          result = String.valueOf(result) + forFlds[i][0] + ";";
        } else if (!forFlds[i][1].equals(forFlds[i - 1][1])) {
          result = String.valueOf(result) + "><input type=hidden id=" + forFlds[i][1] + " name=" + forFlds[i][1] + " value=";
          result = String.valueOf(result) + forFlds[i][0] + ";";
        } else if (i == forFlds.length - 1) {
          result = String.valueOf(result) + forFlds[i][0] + ";";
          result = String.valueOf(result) + ">";
        } else {
          result = String.valueOf(result) + forFlds[i][0] + ";";
        } 
      }  
    return result;
  }
  
  public String getValue(String field, String infoId, String pageId) {
    String result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(field, String.class);
      pg.put(infoId, String.class);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = ejbProxy.invoke("getValue", pg.getParameters()).toString();
    } catch (Exception e) {
      logger.error("CustomFormBD error on getValue information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String getRemindValue(String field, String infoId, String pageId) {
    Object object = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(field, String.class);
      pg.put(infoId, String.class);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      object = ejbProxy.invoke("getRemindValue", pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getRemindValue information:" + 
          e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String save(HttpServletRequest request) {
    String infoId = "";
    String addFlag = request.getParameter("addFlag");
    if (addFlag != null && "batchAdd".equals(addFlag)) {
      infoId = saveBatch(request);
    } else {
      infoId = saveSingle(request);
    } 
    return infoId;
  }
  
  public String saveSingle(HttpServletRequest request) {
    DbOpt dbopt = null;
    String infoId = "";
    try {
      dbopt = new DbOpt();
      infoId = saveData(request, dbopt);
      if (infoId != null || infoId.length() >= 1) {
        String[] tables = getForeignTable(request.getParameter("Page_Id"));
        if (tables != null && tables.length > 0)
          for (int i = 0; i < tables.length; i++) {
            String forInfoIds = "";
            if (tables[i] != null && tables[i].trim().length() >= 1) {
              String[][] field = getForeignFieldInfo(request.getParameter("Page_Id"), tables[i]);
              if (field != null && field.length > 0) {
                String[] types = request.getParameterValues(String.valueOf(field[0][0]) + "_type");
                if (types != null && types.length > 0)
                  for (int j = 0; j < types.length; j++)
                    forInfoIds = String.valueOf(forInfoIds) + saveForeignData(request, tables[i], field, j, dbopt, infoId) + ",";  
              } 
            } 
          }  
        saveRelationObject(infoId, request, dbopt);
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return infoId;
  }
  
  public String saveBatch(HttpServletRequest request) {
    DbOpt dbopt = null;
    String infoId = "";
    try {
      dbopt = new DbOpt();
      String pageId = (request.getParameter("Page_Id") == null || request.getParameter("Page_Id").equals("null")) ? null : request.getParameter("Page_Id");
      if (pageId == null || pageId.length() < 1)
        return ""; 
      String[][] tableIdName = getTableIDAndName(pageId);
      String table = tableIdName[0][1];
      String[][] field = getFieldInfo(pageId);
      if (field != null && field.length > 0) {
        String[] types = request.getParameterValues(String.valueOf(field[0][0]) + "_type");
        String[] ids = request.getParameterValues(String.valueOf(field[0][0]) + "_Id");
        String[] s = request.getParameterValues(String.valueOf(field[1][0]) + "_Id");
        if (types != null && types.length > 0)
          for (int j = 0; j < types.length; j++)
            infoId = String.valueOf(infoId) + saveDataBatch(request, table, field, j, dbopt) + ",";  
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return infoId;
  }
  
  public String saveData(HttpServletRequest request, DbOpt dbopt) {
    Boolean success = Boolean.TRUE;
    String infoId = "";
    String pageId = (request.getParameter("Page_Id") == null || request.getParameter("Page_Id").equals("null")) ? null : request.getParameter("Page_Id");
    if (pageId == null || pageId.length() < 1)
      return ""; 
    List<String> upList = new ArrayList();
    String[][] fieldStr = getFieldInfo(pageId);
    String[][] tableIdName = getTableIDAndName(pageId);
    String tableId = tableIdName[0][0];
    String table = tableIdName[0][1];
    List<String[]> clobUpdateList = (List)new ArrayList<String>();
    try {
      if (fieldStr != null && fieldStr.length >= 1)
        if (table != null && table.trim().length() >= 1) {
          String collectId = request.getParameter("collectId");
          String sqlHead = "insert into " + table + "(";
          String sqlValue = " values(";
          String computeSqlHead = " update " + table + " set ";
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            infoId = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
          } else {
            dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
            infoId = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
          } 
          Map<String, String> paraMap = new ConcurrentHashMap<String, String>();
          for (int i = 0; i < fieldStr.length; i++) {
            String fieldType = request.getParameter(String.valueOf(fieldStr[i][0]) + "_type");
            if (DbOpt.dbtype.equals("oracle")) {
              if (i == 0) {
                sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group,";
                if (collectId != null && !"".equals(collectId))
                  sqlHead = String.valueOf(sqlHead) + table + "_relaByInde,"; 
                sqlValue = String.valueOf(sqlValue) + infoId + "," + request.getSession(true).getAttribute("userId") + 
                  "," + request.getSession(true).getAttribute("orgId") + "," + 
                  request.getSession(true).getAttribute("groupId") + ",";
                if (collectId != null && !"".equals(collectId))
                  sqlValue = String.valueOf(sqlValue) + collectId + ","; 
              } 
              if (fieldType != null && fieldType.equals("number") && 
                request.getParameter(fieldStr[i][0]) != null && request.getParameter(fieldStr[i][0]).trim().length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + request.getParameter(fieldStr[i][0]) + ",";
              } 
              if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime")) && 
                request.getParameter(fieldStr[i][0]) != null) {
                String val = request.getParameter(fieldStr[i][0]);
                if (!"113".equals(fieldStr[i][2])) {
                  val.replace("\"", "“").replaceAll("'", "＇");
                  val = CharacterTool.escapeHTMLTags2(val);
                  val = val.replaceAll("\\\\", "\\\\\\\\");
                } else {
                  val = val.replaceAll("\\\\", "\\\\\\\\");
                  paraMap.put(fieldStr[i][0], val);
                  i++;
                } 
                if ("111".equals(fieldStr[i][2])) {
                  boolean needCheck = true;
                  if ("1".equals(request.getParameter("parent_fromDraft"))) {
                    needCheck = false;
                  } else if ("1".equals(request.getParameter("resubmit"))) {
                    needCheck = false;
                    if ("1".equals(SystemCommon.getReSubmitDel()))
                      needCheck = true; 
                    if ("1".equals(request.getParameter("onBackResend")) && "1".equals(SystemCommon.getReSubmitOnBackUseOldNumber())) {
                      needCheck = false;
                    } else {
                      needCheck = true;
                    } 
                  } else if ("1".equals(request.getParameter("onDoneResend")) && "1".equals(SystemCommon.getReSubmitOnDoneUseOldNumber())) {
                    needCheck = false;
                  } else {
                    needCheck = true;
                  } 
                  if (needCheck) {
                    String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                    if (fieldOnly == null || fieldOnly.length < 1)
                      updateAutoCode(fieldStr[i][0], val, fieldStr[i][3]); 
                    while (fieldOnly != null && fieldOnly.length > 0) {
                      val = nextAutoCode(fieldStr[i][0], val, fieldStr[i][3]);
                      fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                    } 
                  } 
                } 
                if ("130".equals(fieldStr[i][2]))
                  if (!"1".equals(request.getParameter("parent_fromDraft")) && !"".equals(val) && (!"1".equals(request.getParameter("fromDealwithResend")) || !"1".equals(SystemCommon.getReSubmitOnDoneUseOldNumber()))) {
                    String codeId = request.getParameter(String.valueOf(fieldStr[i][0]) + "_codeId");
                    if (codeId != null) {
                      CodeManageService cmService = new CodeManageService();
                      val = cmService.checkCodeOrMarkNewCode(codeId, val, infoId);
                    } 
                  }  
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                if (!"1000003".equals(fieldStr[i][4])) {
                  if (val == null || val.length() < 2000) {
                    sqlValue = String.valueOf(sqlValue) + "'" + CharacterTool.escapeHTMLQuotOther(val) + "',";
                  } else {
                    sqlValue = String.valueOf(sqlValue) + "'  ',";
                    for (int m = 0; m * 2000 < val.length(); m++) {
                      int end = ((m + 1) * 2000 < val.length()) ? ((m + 1) * 2000) : val.length();
                      upList.add("update " + table + " set " + fieldStr[i][0] + "=" + fieldStr[i][0] + "||'" + CharacterTool.escapeHTMLQuotOther(val.substring(m * 2000, end)) + "' where " + table + "_id=" + infoId);
                    } 
                  } 
                } else {
                  sqlValue = String.valueOf(sqlValue) + "empty_clob(),";
                  String[] lobArr = { table, fieldStr[i][0], String.valueOf(table) + "_id", infoId, CharacterTool.escapeHTMLQuotOther(val) };
                  clobUpdateList.add(lobArr);
                } 
              } 
              if (fieldType != null && fieldType.equals("file") && 
                request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName") != null) {
                String fileName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_fileName");
                String saveName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName");
                String[] fileNameArr = fileName.split(";");
                String[] saveNameArr = saveName.split(";");
                boolean flag = true;
                String saveFileTemp = "";
                String fileFileTemp = "";
                for (int k = 0; k < saveNameArr.length; k++) {
                  if (saveNameArr[k] != null && !saveNameArr[k].equals("aaaaaa") && !saveNameArr[k].trim().equals("")) {
                    if (flag) {
                      sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                      sqlValue = String.valueOf(sqlValue) + "'";
                    } 
                    flag = false;
                    saveFileTemp = String.valueOf(saveFileTemp) + saveNameArr[k] + ",";
                    fileFileTemp = String.valueOf(fileFileTemp) + fileNameArr[k] + ",";
                  } 
                } 
                if (!flag)
                  sqlValue = String.valueOf(sqlValue) + ((saveFileTemp.length() > 0) ? saveFileTemp.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") : "") + 
                    ";" + ((fileFileTemp.length() > 0) ? fileFileTemp.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") : "") + "',"; 
              } 
              if (fieldType != null && fieldType.equals("combox") && 
                request.getParameterValues(fieldStr[i][0]) != null) {
                String[] temp = request.getParameterValues(fieldStr[i][0]);
                if (temp != null && temp.length > 0) {
                  sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                  sqlValue = String.valueOf(sqlValue) + "'";
                  for (int j = 0; j < temp.length; j++)
                    sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                  sqlValue = String.valueOf(sqlValue) + "',";
                } 
              } 
              if (fieldType != null && fieldType.equals("personorg") && 
                request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id") != null && request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id").length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameter(String.valueOf(fieldStr[i][0]) + "_Name").replaceAll("'", "＇").replace("\"", "“")
                  .replaceAll("&", "'||'&'||'") + ";" + request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id").replaceAll("'", "＇").replace("\"", "“")
                  .replaceAll("&", "'||'&'||'") + "',";
              } 
            } 
            if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0 || DbOpt.dbtype.indexOf("db2") >= 0) {
              if (i == 0) {
                sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group,";
                if (collectId != null && !"".equals(collectId))
                  sqlHead = String.valueOf(sqlHead) + table + "_relaByInde,"; 
                sqlValue = String.valueOf(sqlValue) + infoId + "," + request.getSession(true).getAttribute("userId") + ",'" + 
                  request.getSession(true).getAttribute("orgId") + "','" + request.getSession(true).getAttribute("groupId") + "',";
                if (collectId != null && !"".equals(collectId))
                  sqlValue = String.valueOf(sqlValue) + collectId + ","; 
              } 
              if (fieldType != null && fieldType.equals("number") && 
                request.getParameter(fieldStr[i][0]) != null && request.getParameter(fieldStr[i][0]).trim().length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + request.getParameter(fieldStr[i][0]) + ",";
              } 
              if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime")) && 
                request.getParameter(fieldStr[i][0]) != null) {
                String val = request.getParameter(fieldStr[i][0]);
                if (!"113".equals(fieldStr[i][2])) {
                  val.replace("\"", "“").replaceAll("'", "＇");
                  val = CharacterTool.escapeHTMLTags2(val);
                  val = val.replaceAll("\\\\", "\\\\\\\\");
                } else {
                  val = val.replaceAll("\\\\", "\\\\\\\\");
                  paraMap.put(fieldStr[i][0], val);
                  i++;
                } 
                if ("111".equals(fieldStr[i][2])) {
                  boolean needCheck = true;
                  if ("1".equals(request.getParameter("parent_fromDraft"))) {
                    needCheck = false;
                  } else if ("1".equals(request.getParameter("resubmit"))) {
                    needCheck = false;
                    if ("1".equals(SystemCommon.getReSubmitDel()))
                      needCheck = true; 
                    if ("1".equals(request.getParameter("onBackResend")) && "1".equals(SystemCommon.getReSubmitOnBackUseOldNumber())) {
                      needCheck = false;
                    } else {
                      needCheck = true;
                    } 
                  } else if ("1".equals(request.getParameter("onDoneResend")) && "1".equals(SystemCommon.getReSubmitOnDoneUseOldNumber())) {
                    needCheck = false;
                  } else {
                    needCheck = true;
                  } 
                  if (needCheck) {
                    String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                    if (fieldOnly == null || fieldOnly.length < 1)
                      updateAutoCode(fieldStr[i][0], val, fieldStr[i][3]); 
                    while (fieldOnly != null && fieldOnly.length > 0) {
                      val = nextAutoCode(fieldStr[i][0], val, fieldStr[i][3]);
                      fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                    } 
                  } 
                } 
                if ("130".equals(fieldStr[i][2]))
                  if (!"1".equals(request.getParameter("parent_fromDraft")) && !"".equals(val) && (!"1".equals(request.getParameter("fromDealwithResend")) || !"1".equals(SystemCommon.getReSubmitOnDoneUseOldNumber()))) {
                    String codeId = request.getParameter(String.valueOf(fieldStr[i][0]) + "_codeId");
                    if (codeId != null) {
                      CodeManageService cmService = new CodeManageService();
                      val = cmService.checkCodeOrMarkNewCode(codeId, val, infoId);
                    } 
                  }  
                if (val == null || val.length() < 2000) {
                  sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                  sqlValue = String.valueOf(sqlValue) + "'" + CharacterTool.escapeHTMLQuotOther(val) + "',";
                } else {
                  sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                  sqlValue = String.valueOf(sqlValue) + "' ',";
                  for (int m = 0; m * 2000 < val.length(); m++) {
                    int end = ((m + 1) * 2000 < val.length()) ? ((m + 1) * 2000) : val.length();
                    upList.add("update " + table + " set " + fieldStr[i][0] + "=concat(" + fieldStr[i][0] + ",'" + CharacterTool.escapeHTMLQuotOther(val.substring(m * 2000, end)) + "') where " + table + "_id=" + infoId);
                  } 
                } 
              } 
              if (fieldType != null && fieldType.equals("file") && 
                request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName") != null) {
                String fileName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_fileName");
                String saveName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName");
                String[] fileNameArr = fileName.split(";");
                String[] saveNameArr = saveName.split(";");
                boolean flag = true;
                String saveFileTemp = "";
                String fileFileTemp = "";
                for (int k = 0; k < saveNameArr.length; k++) {
                  if (saveNameArr[k] != null && !saveNameArr[k].equals("aaaaaa") && !saveNameArr[k].trim().equals("")) {
                    if (flag) {
                      sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                      sqlValue = String.valueOf(sqlValue) + "'";
                    } 
                    flag = false;
                    saveFileTemp = String.valueOf(saveFileTemp) + saveNameArr[k] + ",";
                    fileFileTemp = String.valueOf(fileFileTemp) + fileNameArr[k] + ",";
                  } 
                } 
                if (!flag)
                  sqlValue = String.valueOf(sqlValue) + saveFileTemp.replaceAll("'", "＇").replace("\"", "“") + ";" + 
                    fileFileTemp.replaceAll("'", "＇").replace("\"", "“") + "',"; 
              } 
              if (fieldType != null && fieldType.equals("combox") && 
                request.getParameterValues(fieldStr[i][0]) != null) {
                String[] temp = request.getParameterValues(fieldStr[i][0]);
                if (temp != null && temp.length > 0) {
                  sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                  sqlValue = String.valueOf(sqlValue) + "'";
                  for (int j = 0; j < temp.length; j++)
                    sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                  sqlValue = String.valueOf(sqlValue) + "',";
                } 
              } 
              if (fieldType != null && fieldType.equals("personorg") && 
                request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id") != null && 
                request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id")
                .length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameter(String.valueOf(fieldStr[i][0]) + "_Name").replaceAll("'", "＇").replace("\"", "“") + 
                  ";" + request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id").replaceAll("'", "＇").replace("\"", "“") + "',";
              } 
            } 
            if (fieldType != null && fieldType.equals("datetime"))
              if (request.getParameter(fieldStr[i][0]) != null && !"".equals(request.getParameter(fieldStr[i][0])) && !"null".equalsIgnoreCase(request.getParameter(fieldStr[i][0]))) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                String hour = request.getParameter(String.valueOf(fieldStr[i][0]) + "hours");
                String minutes = request.getParameter(String.valueOf(fieldStr[i][0]) + "minutes");
                String second = request.getParameter(String.valueOf(fieldStr[i][0]) + "second");
                String timestr = String.valueOf(hour) + ":" + minutes + ":" + second;
                if ("00:00:00".equals(timestr) || "0:0:00".equals(timestr)) {
                  hour = "";
                  minutes = "";
                  second = "";
                } 
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameter(fieldStr[i][0]) + " " + hour + 
                  ":" + minutes + ":" + ((second == null) ? "00" : second) + 
                  "',";
              }  
          } 
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            String[] computeField = request.getParameterValues("computeField");
            if (computeField != null && computeField.length > 0) {
              for (int k = 0; k < computeField.length; k++) {
                String computFieldValue = request.getParameter(computeField[k]);
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + computeField[k] + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } else {
              String cmpField = request.getParameter("computeField");
              String computFieldValue = "";
              if (cmpField != null && !cmpField.equals(""))
                computFieldValue = request.getParameter(cmpField); 
              if (cmpField != null && cmpField.trim().length() > 0 && !cmpField.toUpperCase().equals("NULL") && computFieldValue != null && computFieldValue.trim().length() > 0) {
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + cmpField + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } 
          } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0) {
            String[] computeField = request.getParameterValues("computeField");
            if (computeField != null && computeField.length > 0) {
              for (int k = 0; k < computeField.length; k++) {
                String computFieldValue = request.getParameter(computeField[k]);
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + computeField[k] + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } else {
              String cmpField = request.getParameter("computeField");
              String computFieldValue = "";
              if (cmpField != null && !cmpField.equals(""))
                computFieldValue = request.getParameter(cmpField); 
              if (cmpField != null && cmpField.trim().length() > 0 && 
                !cmpField.toUpperCase().equals("NULL") && 
                computFieldValue != null && 
                computFieldValue.trim().length() > 0) {
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + cmpField + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } 
          } 
          int result = 0;
          if (infoId != null) {
            sqlValue = CharacterTool.escapeHTMLTags2(sqlValue);
            String sql = String.valueOf(sqlHead.substring(0, sqlHead.length() - 1)) + ") " + sqlValue.substring(0, sqlValue.length() - 1) + ")";
            IO2File.printFile("表单数据存储到数据库sql：" + sql);
            result = dbopt.executeUpdate(sql);
            if (!computeSqlHead.trim().endsWith("set"))
              result = dbopt.executeUpdate(String.valueOf(computeSqlHead.substring(0, computeSqlHead.length() - 1)) + " where " + table + "_id=" + infoId); 
            if (!paraMap.isEmpty())
              result = dbopt.executePSUpdate(table, "update", paraMap, String.valueOf(table) + "_id=" + infoId); 
            String[][] updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=406");
            int j;
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("userAccount") == null) ? "" : request.getSession(true).getAttribute("userAccount").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=201");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("userId") == null) ? "" : request.getSession(true).getAttribute("userId").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=202");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("userName") == null) ? "" : request.getSession(true).getAttribute("userName").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=207");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getParameter(updateArr[j][0]) != null) ? request.getParameter(updateArr[j][0]) : (
                (request.getSession(true).getAttribute("orgName") == null) ? "" : request.getSession(true).getAttribute("orgName").toString());
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=213");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getParameter(updateArr[j][0]) != null) ? request.getParameter(updateArr[j][0]) : (
                (request.getSession(true).getAttribute("orgId") == null) ? "" : request.getSession(true).getAttribute("orgId").toString());
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            for (int n = 0; n < upList.size(); n++)
              dbopt.executeUpdate(upList.get(n).toString()); 
            if (clobUpdateList.size() > 0)
              for (String[] lobArr : clobUpdateList)
                InfoUtil.insert_oracle_clob(lobArr[0], lobArr[1], lobArr[2], Long.valueOf(lobArr[3]), lobArr[4]);  
            upList.clear();
            clobUpdateList.clear();
          } 
          if (result < 1) {
            success = Boolean.FALSE;
          } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
            dbopt.executeUpdate("update " + table + " set " + table + "_date=sysdate() where " + table + "_id=" + infoId);
          } 
          SearchService.getInstance();
          String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
          SearchService.getInstance();
          String isearchSwitch = SearchService.getiSearchSwitch();
          if ("1".equals(isearchSwitch) && infoId != null && ifActiveUpdateDelete != null && !"".equals(infoId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
            SearchService.getInstance();
            SearchService.addIndex("", "jsf_workflow");
          } 
        }  
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {}
    if (success.equals(Boolean.TRUE))
      return infoId; 
    return "";
  }
  
  public String saveDataBatch(HttpServletRequest request, String table, String[][] fieldStr, int seq, DbOpt dbopt) {
    Boolean success = Boolean.TRUE;
    String infoId = "";
    String pageId = (request.getParameter("Page_Id") == null || request.getParameter("Page_Id").equals("null")) ? null : request.getParameter("Page_Id");
    if (pageId == null || pageId.length() < 1)
      return ""; 
    List<String> upList = new ArrayList();
    try {
      if (fieldStr != null && fieldStr.length >= 1)
        if (table != null && table.trim().length() >= 1) {
          String collectId = request.getParameter("collectId");
          String sqlHead = "insert into " + table + "(";
          String sqlValue = " values(";
          String computeSqlHead = " update " + table + " set ";
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            infoId = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
          } else {
            dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
            infoId = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
          } 
          for (int i = 0; i < fieldStr.length; i++) {
            String fieldType = null;
            if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_type") != null)
              fieldType = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_type")[seq]; 
            if (DbOpt.dbtype.equals("oracle")) {
              if (i == 0) {
                sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group,";
                if (collectId != null && !"".equals(collectId))
                  sqlHead = String.valueOf(sqlHead) + table + "_relaByInde,"; 
                sqlValue = String.valueOf(sqlValue) + infoId + "," + request.getSession(true).getAttribute("userId") + 
                  "," + request.getSession(true).getAttribute("orgId") + "," + 
                  request.getSession(true).getAttribute("groupId") + ",";
                if (collectId != null && !"".equals(collectId))
                  sqlValue = String.valueOf(sqlValue) + collectId + ","; 
              } 
              if (fieldType != null && fieldType.equals("number"))
                try {
                  if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && request.getParameterValues(fieldStr[i][0])[seq].trim().length() > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + request.getParameterValues(fieldStr[i][0])[seq] + ",";
                  } 
                } catch (Exception e) {
                  if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0].trim().length() > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] + ",";
                  } 
                }  
              if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime"))) {
                String temVal = null;
                try {
                  if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null)
                    temVal = request.getParameterValues(fieldStr[i][0])[seq]; 
                } catch (Exception e) {
                  String s1 = String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1);
                  String[] str = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1));
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null)
                    temVal = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0]; 
                } 
                if (temVal != null) {
                  sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                  String val = temVal.replaceAll("'", "＇");
                  if ("111".equals(fieldStr[i][2]))
                    if (!"1".equals(request.getParameter("parent_fromDraft"))) {
                      String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                      if (fieldOnly == null || fieldOnly.length < 1)
                        updateAutoCode(fieldStr[i][0], val, fieldStr[i][3]); 
                      while (fieldOnly != null && fieldOnly.length > 0) {
                        val = nextAutoCode(fieldStr[i][0], val, fieldStr[i][3]);
                        fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                      } 
                    }  
                  if (val == null || val.length() < 2000) {
                    sqlValue = String.valueOf(sqlValue) + "'" + val + "',";
                  } else {
                    sqlValue = String.valueOf(sqlValue) + "'  ',";
                    for (int m = 0; m * 2000 < val.length(); m++) {
                      int end = ((m + 1) * 2000 < val.length()) ? ((m + 1) * 2000) : val.length();
                      upList.add("update " + table + " set " + fieldStr[i][0] + "=" + fieldStr[i][0] + "||'" + val.substring(m * 2000, end).replaceAll("'", "＇").replace("\"", "“")
                          .replaceAll("&", "'||'&'||'") + "' where " + table + "_id=" + infoId);
                    } 
                  } 
                } 
              } 
              if (fieldType != null && fieldType.equals("file")) {
                String fileName = null;
                String saveName = null;
                try {
                  if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null) {
                    fileName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName")[seq];
                    saveName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_saveName")[seq];
                  } 
                } catch (Exception e) {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName" + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName" + String.valueOf(seq + 1))[0] != null) {
                    fileName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName" + String.valueOf(seq + 1))[0];
                    saveName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_saveName" + String.valueOf(seq + 1))[0];
                  } 
                } 
                if (fileName != null) {
                  String[] fileNameArr = fileName.split(";");
                  String[] saveNameArr = saveName.split(";");
                  boolean flag = true;
                  String saveFileTemp = "";
                  String fileFileTemp = "";
                  for (int k = 0; k < saveNameArr.length; k++) {
                    if (saveNameArr[k] != null && !saveNameArr[k].equals("aaaaaa") && !saveNameArr[k].trim().equals("")) {
                      if (flag) {
                        sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                        sqlValue = String.valueOf(sqlValue) + "'";
                      } 
                      flag = false;
                      saveFileTemp = String.valueOf(saveFileTemp) + saveNameArr[k] + ",";
                      fileFileTemp = String.valueOf(fileFileTemp) + fileNameArr[k] + ",";
                    } 
                  } 
                  if (!flag)
                    sqlValue = String.valueOf(sqlValue) + ((saveFileTemp.length() > 0) ? saveFileTemp.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") : "") + 
                      ";" + ((fileFileTemp.length() > 0) ? fileFileTemp.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") : "") + "',"; 
                } 
              } 
              if (fieldType != null && fieldType.equals("combox"))
                if (seq < 1) {
                  if (request.getParameterValues(fieldStr[i][0]) != null) {
                    String[] temp = request.getParameterValues(fieldStr[i][0]);
                    if (temp != null && temp.length > 0) {
                      sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                      sqlValue = String.valueOf(sqlValue) + "'";
                      for (int j = 0; j < temp.length; j++)
                        sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                      sqlValue = String.valueOf(sqlValue) + "',";
                    } 
                  } 
                } else if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null) {
                  String[] temp = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1));
                  if (temp != null && temp.length > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'";
                    for (int j = 0; j < temp.length; j++)
                      sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                    sqlValue = String.valueOf(sqlValue) + "',";
                  } 
                }  
              if (fieldType != null && fieldType.equals("personorg"))
                try {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq] != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].length() > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name")[seq].replaceAll("'", "＇").replace("\"", "“")
                      .replaceAll("&", "'||'&'||'") + ";" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].replaceAll("'", "＇").replace("\"", "“")
                      .replaceAll("&", "'||'&'||'") + "',";
                  } 
                } catch (Exception e) {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0] != null) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + ";" + 
                      request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + "',";
                  } 
                }  
            } 
            if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0 || DbOpt.dbtype.indexOf("db2") >= 0) {
              if (i == 0) {
                sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group,";
                if (collectId != null && !"".equals(collectId))
                  sqlHead = String.valueOf(sqlHead) + table + "_relaByInde,"; 
                sqlValue = String.valueOf(sqlValue) + infoId + "," + request.getSession(true).getAttribute("userId") + ",'" + 
                  request.getSession(true).getAttribute("orgId") + "','" + request.getSession(true).getAttribute("groupId") + "',";
                if (collectId != null && !"".equals(collectId))
                  sqlValue = String.valueOf(sqlValue) + collectId + ","; 
              } 
              if (fieldType != null && fieldType.equals("number"))
                try {
                  if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && request.getParameterValues(fieldStr[i][0])[seq].trim().length() > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + request.getParameterValues(fieldStr[i][0])[seq] + ",";
                  } 
                } catch (Exception e) {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0].trim().length() > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] + ",";
                  } 
                }  
              if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime"))) {
                String temVal = null;
                try {
                  if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null)
                    temVal = request.getParameterValues(fieldStr[i][0])[seq]; 
                } catch (Exception e) {
                  String s1 = String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1);
                  String[] str = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1));
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null)
                    temVal = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0]; 
                } 
                if (temVal != null) {
                  String val = temVal.replaceAll("'", "＇");
                  if ("111".equals(fieldStr[i][2]))
                    if (!"1".equals(request.getParameter("parent_fromDraft"))) {
                      String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                      if (fieldOnly == null || fieldOnly.length < 1)
                        updateAutoCode(fieldStr[i][0], val, fieldStr[i][3]); 
                      while (fieldOnly != null && fieldOnly.length > 0) {
                        val = nextAutoCode(fieldStr[i][0], val, fieldStr[i][3]);
                        fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + val + "'");
                      } 
                    }  
                  if (val == null || val.length() < 2000) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'" + val + "',";
                  } else {
                    for (int m = 0; m * 2000 < val.length(); m++) {
                      int end = ((m + 1) * 2000 < val.length()) ? ((m + 1) * 2000) : val.length();
                      if (m == 0) {
                        upList.add("update " + table + " set " + fieldStr[i][0] + "='" + CharacterTool.escapeHTMLQuotOther(val.substring(m * 2000, end)) + "' where " + table + "_id=" + infoId);
                      } else {
                        String databaseType = SystemCommon.getDatabaseType();
                        if (databaseType.indexOf("mysql") >= 0)
                          upList.add("update " + table + " set " + fieldStr[i][0] + "=concat(" + fieldStr[i][0] + ",'" + CharacterTool.escapeHTMLQuotOther(val.substring(m * 2000, end)) + "') where " + table + "_id=" + infoId); 
                        if (databaseType.indexOf("oracle") >= 0)
                          upList.add("update " + table + " set " + fieldStr[i][0] + "=" + fieldStr[i][0] + "||'" + CharacterTool.escapeHTMLQuotOther(val.substring(m * 2000, end)) + "' where " + table + "_id=" + infoId); 
                      } 
                    } 
                  } 
                } 
              } 
              if (fieldType != null && fieldType.equals("file")) {
                String fileName = null;
                String saveName = null;
                try {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName")[seq] != null) {
                    fileName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName")[seq];
                    saveName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_saveName")[seq];
                  } 
                } catch (Exception e) {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName" + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName" + String.valueOf(seq + 1))[0] != null) {
                    fileName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_fileName" + String.valueOf(seq + 1))[0];
                    saveName = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_saveName" + String.valueOf(seq + 1))[0];
                  } 
                } 
                if (fileName != null) {
                  String[] fileNameArr = fileName.split(";");
                  String[] saveNameArr = saveName.split(";");
                  boolean flag = true;
                  String saveFileTemp = "";
                  String fileFileTemp = "";
                  for (int k = 0; k < saveNameArr.length; k++) {
                    if (saveNameArr[k] != null && !saveNameArr[k].equals("aaaaaa") && !saveNameArr[k].trim().equals("")) {
                      if (flag) {
                        sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                        sqlValue = String.valueOf(sqlValue) + "'";
                      } 
                      flag = false;
                      saveFileTemp = String.valueOf(saveFileTemp) + saveNameArr[k] + ",";
                      fileFileTemp = String.valueOf(fileFileTemp) + fileNameArr[k] + ",";
                    } 
                  } 
                  if (!flag)
                    sqlValue = String.valueOf(sqlValue) + saveFileTemp.replaceAll("'", "＇").replace("\"", "“") + ";" + fileFileTemp.replaceAll("'", "＇").replace("\"", "“") + "',"; 
                } 
              } 
              if (fieldType != null && fieldType.equals("combox"))
                if (seq < 1) {
                  if (request.getParameterValues(fieldStr[i][0]) != null) {
                    String[] temp = request.getParameterValues(fieldStr[i][0]);
                    if (temp != null && temp.length > 0) {
                      sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                      sqlValue = String.valueOf(sqlValue) + "'";
                      for (int j = 0; j < temp.length; j++)
                        sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                      sqlValue = String.valueOf(sqlValue) + "',";
                    } 
                  } 
                } else if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null) {
                  String[] temp = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1));
                  if (temp != null && temp.length > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'";
                    for (int j = 0; j < temp.length; j++)
                      sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                    sqlValue = String.valueOf(sqlValue) + "',";
                  } 
                }  
              if (fieldType != null && fieldType.equals("personorg"))
                try {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq] != null && 
                    request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].length() > 0) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name")[seq].replaceAll("'", "＇").replace("\"", "“") + ";" + 
                      request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].replaceAll("'", "＇").replace("\"", "“") + "',";
                  } 
                } catch (Exception e) {
                  if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0] != null) {
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                    sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + ";" + 
                      request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + "',";
                  } 
                }  
            } 
            if (fieldType != null && fieldType.equals("datetime") && 
              request.getParameter(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null) {
              sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
              String hour = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "hours")[seq];
              String minutes = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "minutes")[seq];
              String second = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "second")[seq];
              String timestr = String.valueOf(hour) + ":" + minutes + ":" + second;
              if ("00:00:00".equals(timestr) || "0:0:00".equals(timestr)) {
                hour = "";
                minutes = "";
                second = "";
              } 
              sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(fieldStr[i][0])[seq] + " " + hour + ":" + minutes + ":" + ((second == null) ? "00" : second) + "',";
            } 
            if (fieldType != null && fieldType.equals("url") && 
              request.getParameter(String.valueOf(fieldStr[i][0]) + "_title") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_title")[seq] != null && 
              request.getParameter(String.valueOf(fieldStr[i][0]) + "_url") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_url")[seq] != null) {
              sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_title")[seq] + "`~`~`" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_url")[seq] + "',";
            } 
          } 
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            String[] computeField = request.getParameterValues("computeField");
            if (computeField != null && computeField.length > 0) {
              for (int k = 0; k < computeField.length; k++) {
                String computFieldValue = request.getParameter(computeField[k]);
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + computeField[k] + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } else {
              String cmpField = request.getParameter("computeField");
              String computFieldValue = "";
              if (cmpField != null && !cmpField.equals(""))
                computFieldValue = request.getParameter(cmpField); 
              if (cmpField != null && cmpField.trim().length() > 0 && !cmpField.toUpperCase().equals("NULL") && computFieldValue != null && computFieldValue.trim().length() > 0) {
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + cmpField + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } 
          } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0) {
            String[] computeField = request.getParameterValues("computeField");
            if (computeField != null && computeField.length > 0) {
              for (int k = 0; k < computeField.length; k++) {
                String computFieldValue = request.getParameter(computeField[k]);
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + computeField[k] + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } else {
              String cmpField = request.getParameter("computeField");
              String computFieldValue = "";
              if (cmpField != null && !cmpField.equals(""))
                computFieldValue = request.getParameter(cmpField); 
              if (cmpField != null && cmpField.trim().length() > 0 && 
                !cmpField.toUpperCase().equals("NULL") && 
                computFieldValue != null && 
                computFieldValue.trim().length() > 0) {
                String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + cmpField + "' and field_table=table_id and table_name='" + table + "'");
                if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
                  if (!"".equals(computFieldValue)) {
                    computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                    computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
                  } 
                } else {
                  if ("''".equals(computFieldValue))
                    computFieldValue = ""; 
                  computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                  computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue + "',";
                } 
              } 
            } 
          } 
          int result = 0;
          if (infoId != null) {
            String sql = String.valueOf(sqlHead.substring(0, sqlHead.length() - 1)) + ") " + sqlValue.substring(0, sqlValue.length() - 1) + ")";
            result = dbopt.executeUpdate(sql);
            if (!computeSqlHead.trim().endsWith("set"))
              result = dbopt.executeUpdate(String.valueOf(computeSqlHead.substring(0, computeSqlHead.length() - 1)) + " where " + table + "_id=" + infoId); 
            String[][] updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=406");
            int j;
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("userAccount") == null) ? "" : request.getSession(true).getAttribute("userAccount").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=201");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("userId") == null) ? "" : request.getSession(true).getAttribute("userId").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=202");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("userName") == null) ? "" : request.getSession(true).getAttribute("userName").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=207");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("orgName") == null) ? "" : request.getSession(true).getAttribute("orgName").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=213");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = (request.getSession(true).getAttribute("orgId") == null) ? "" : request.getSession(true).getAttribute("orgId").toString();
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            for (int n = 0; n < upList.size(); n++)
              dbopt.executeUpdate(upList.get(n).toString()); 
          } 
          if (result < 1) {
            success = Boolean.FALSE;
          } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
            dbopt.executeUpdate("update " + table + " set " + table + "_date=sysdate() where " + table + "_id=" + infoId);
          } 
        }  
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {}
    if (success.equals(Boolean.TRUE))
      return infoId; 
    return "";
  }
  
  public String saveForeignData(HttpServletRequest request, String table, String[][] fieldStr, int seq, DbOpt dbopt, String priId) {
    Boolean success = Boolean.TRUE;
    String infoId = "";
    try {
      if (DbOpt.dbtype.equals("oracle")) {
        Statement stat = dbopt.getStatement();
        String ddlStr = " select count(*) from user_tab_columns where table_name='" + table.toUpperCase() + "' and (column_name = '" + 
          table.toUpperCase() + "_ORG' OR column_name = '" + table.toUpperCase() + "_GROUP')";
        String ddlStr2 = " select count(*) from user_tab_columns where table_name='" + table.toUpperCase() + "' and (column_name = '" + 
          table.toUpperCase() + "_FOREIGNKEY')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        ddlStr2 = dbopt.executeQueryToStr(ddlStr2);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + "_ORG VARCHAR2(100) NULL";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
          ddlStr = " ALTER TABLE " + table + " ADD " + table + "_GROUP VARCHAR2(100) NULL";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
        } 
        if ("0".equals(ddlStr2)) {
          ddlStr2 = " ALTER TABLE " + table + " ADD " + table + "_FOREIGNKEY NUMBER(20) NULL";
          stat.addBatch(ddlStr2.toUpperCase());
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from user_tab_columns where table_name='" + 
          table.toUpperCase() + "' and (column_name = '" + 
          table.toUpperCase() + "_OWNER')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_OWNER NUMBER(20) default 0";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from user_tab_columns where table_name='" + 
          table.toUpperCase() + "' and (column_name = '" + 
          table.toUpperCase() + "_DATE')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_DATE varchar2(24) to_char(sysdate,'YYYY-mm-dd')";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
        } 
      } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
        Statement stat = dbopt.getStatement();
        String ddlStr = " select count(*) from syscat.columns where tabname='" + table.toUpperCase() + "' and (colname = '" + 
          table.toUpperCase() + "_ORG' OR colname = '" + table.toUpperCase() + "_GROUP')";
        String ddlStr2 = " select count(*) from syscat.columns where tabname='" + table.toUpperCase() + "' and (colname = '" + 
          table.toUpperCase() + "_FOREIGNKEY')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        ddlStr2 = dbopt.executeQueryToStr(ddlStr2);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + "_ORG VARCHAR(100)";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
          ddlStr = " ALTER TABLE " + table + " ADD " + table + "_GROUP VARCHAR(100)";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
        } 
        if ("0".equals(ddlStr2)) {
          ddlStr2 = " ALTER TABLE " + table + " ADD " + table + "_FOREIGNKEY decimal(20)";
          stat.addBatch(ddlStr2.toUpperCase());
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from syscat.columns where tabname='" + 
          table.toUpperCase() + "' and (colname = '" + 
          table.toUpperCase() + "_OWNER')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_OWNER DECIMAL(20)";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from syscat.columns where tabname='" + 
          table.toUpperCase() + "' and (colname = '" + 
          table.toUpperCase() + "_DATE')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_DATE varchar(24)";
          stat.addBatch(ddlStr.toUpperCase());
          stat.executeBatch();
        } 
      } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        Statement stat = dbopt.getStatement();
        String ddlStr = " select count(*) from information_schema.columns where table_name='" + table + "' and (column_name = '" + table + 
          "_ORG' OR column_name = '" + table + "_GROUP')";
        String ddlStr2 = " select count(*) from information_schema.columns where table_name='" + table + "' and (column_name = '" + table + "_FOREIGNKEY')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        ddlStr2 = dbopt.executeQueryToStr(ddlStr2);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + "_ORG VARCHAR(100) NULL, " + table + "_GROUP VARCHAR(100) NULL";
          stat.addBatch(ddlStr);
          stat.executeBatch();
        } 
        if ("0".equals(ddlStr2)) {
          ddlStr2 = " ALTER TABLE " + table + " ADD " + table + "_FOREIGNKEY NUMERIC(20) NULL";
          stat.addBatch(ddlStr2);
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from information_schema.columns where table_name='" + 
          table + "' and (column_name = '" + table + 
          "_OWNER')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_OWNER NUMERIC(20) default 0";
          stat.addBatch(ddlStr);
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from information_schema.columns where table_name='" + 
          table + "' and (column_name = '" + table + 
          "_DATE')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_DATE varchar(24)";
          stat.addBatch(ddlStr);
          stat.executeBatch();
        } 
      } else {
        Statement stat = dbopt.getStatement();
        String ddlStr = " select count(*) from syscolumns where id=object_id('" + table + "') and (name = '" + table + 
          "_ORG' OR name = '" + table + "_GROUP')";
        String ddlStr2 = " select count(*) from syscolumns where id=object_id('" + table + "') and (name = '" + table + "_FOREIGNKEY')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        ddlStr2 = dbopt.executeQueryToStr(ddlStr2);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + "_ORG VARCHAR(100) NULL, " + table + "_GROUP VARCHAR(100) NULL";
          stat.addBatch(ddlStr);
          stat.executeBatch();
        } 
        if ("0".equals(ddlStr2)) {
          ddlStr2 = " ALTER TABLE " + table + " ADD " + table + "_FOREIGNKEY NUMERIC(20) NULL";
          stat.addBatch(ddlStr2);
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from syscolumns where id=object_id('" + 
          table + "') and (name = '" + table + 
          "_OWNER')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_OWNER NUMERIC(20) default 0";
          stat.addBatch(ddlStr);
          stat.executeBatch();
        } 
        ddlStr = 
          " select count(*) from syscolumns where id=object_id('" + 
          table + "') and (name = '" + table + 
          "_DATE')";
        ddlStr = dbopt.executeQueryToStr(ddlStr);
        if ("0".equals(ddlStr)) {
          ddlStr = " ALTER TABLE " + table + " ADD " + table + 
            "_DATE varchar(24)";
          stat.addBatch(ddlStr);
          stat.executeBatch();
        } 
      } 
      String sqlHead = "insert into " + table + "(";
      String sqlValue = " values(";
      if (DbOpt.dbtype.indexOf("oracle") >= 0) {
        infoId = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
      } else {
        dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
        infoId = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
      } 
      for (int i = 0; i < fieldStr.length; i++) {
        String fieldType = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_type")[seq];
        if (DbOpt.dbtype.equals("oracle")) {
          if (i == 0) {
            sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group," + table + "_FOREIGNKEY,";
            sqlValue = String.valueOf(sqlValue) + infoId + "," + request.getSession(true).getAttribute("userId") + "," + request.getSession(true).getAttribute("orgId") + "," + request.getSession(true).getAttribute("groupId") + "," + priId + ",";
          } 
          if (fieldType != null && fieldType.equals("number"))
            try {
              if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && request.getParameterValues(fieldStr[i][0])[seq].trim().length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + request.getParameterValues(fieldStr[i][0])[seq] + ",";
              } 
            } catch (Exception e) {
              if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0].trim().length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] + ",";
              } 
            }  
          if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime")))
            try {
              if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(fieldStr[i][0])[seq].replaceAll("'", "＇").replace("\"", "“")
                  .replaceAll("&", "'||'&'||'") + "',";
              } 
            } catch (Exception e) {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“")
                  .replaceAll("&", "'||'&'||'") + "',";
              } 
            }  
          if (fieldType != null)
            fieldType.equals("file"); 
          if (fieldType != null && fieldType.equals("personorg"))
            try {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq] != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name")[seq].replaceAll("'", "＇").replace("\"", "“")
                  .replaceAll("&", "'||'&'||'") + ";" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].replaceAll("'", "＇").replace("\"", "“").replace("\"", "“")
                  .replaceAll("&", "'||'&'||'") + "',";
              } 
            } catch (Exception e) {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0] != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + ";" + 
                  request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + "',";
              } 
            }  
        } 
        if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0 || DbOpt.dbtype.indexOf("db2") >= 0) {
          if (i == 0) {
            sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + 
              "_org," + table + "_group," + table + "_FOREIGNKEY,";
            sqlValue = String.valueOf(sqlValue) + infoId + "," + request.getSession(true).getAttribute("userId") + ",'" + 
              request.getSession(true).getAttribute("orgId") + "','" + request.getSession(true).getAttribute("groupId") + "'," + priId + ",";
          } 
          if (fieldType != null && fieldType.equals("number"))
            try {
              if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && request.getParameterValues(fieldStr[i][0])[seq].trim().length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + request.getParameterValues(fieldStr[i][0])[seq] + ",";
              } 
            } catch (Exception e) {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0].trim().length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] + ",";
              } 
            }  
          if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime")))
            try {
              if (request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                String temp = String.valueOf(request.getParameterValues(fieldStr[i][0])[seq].replaceAll("'", "＇").replace("\"", "“")) + "',";
                temp = temp.replaceAll("\\\\", "\\\\\\\\");
                sqlValue = String.valueOf(sqlValue) + "'" + temp;
              } 
            } catch (Exception e) {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0] != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + "',";
              } 
            }  
          if (fieldType != null)
            fieldType.equals("file"); 
          if (fieldType != null && fieldType.equals("personorg"))
            try {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id") != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq] != null && 
                request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].length() > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name")[seq].replaceAll("'", "＇").replace("\"", "“") + ";" + 
                  request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id")[seq].replaceAll("'", "＇").replace("\"", "“") + "',";
              } 
            } catch (Exception e) {
              if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1)) != null && request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0] != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Name" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + ";" + 
                  request.getParameterValues(String.valueOf(fieldStr[i][0]) + "_Id" + String.valueOf(seq + 1))[0].replaceAll("'", "＇").replace("\"", "“") + "',";
              } 
            }  
        } 
        if (fieldType != null && fieldType.equals("datetime") && 
          request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && !"".equals(request.getParameterValues(fieldStr[i][0])[seq])) {
          sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
          String hour = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "hours")[seq];
          String minutes = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "minutes")[seq];
          String second = request.getParameterValues(String.valueOf(fieldStr[i][0]) + "second")[seq];
          String timestr = String.valueOf(hour) + ":" + minutes + ":" + second;
          if ("00:00:00".equals(timestr) || "0:0:00".equals(timestr)) {
            hour = "";
            minutes = "";
            second = "";
          } 
          sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(fieldStr[i][0])[seq] + " " + hour + 
            ":" + minutes + ":" + ((second == null) ? "00" : second) + "',";
        } 
        if (fieldType != null && fieldType.equals("combox"))
          if (seq < 1) {
            if (request.getParameterValues(fieldStr[i][0]) != null) {
              String[] temp = request.getParameterValues(fieldStr[i][0]);
              if (temp != null && temp.length > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                sqlValue = String.valueOf(sqlValue) + "'";
                for (int j = 0; j < temp.length; j++)
                  sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
                sqlValue = String.valueOf(sqlValue) + "',";
              } 
            } 
          } else if (request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1)) != null) {
            String[] temp = request.getParameterValues(String.valueOf(fieldStr[i][0]) + String.valueOf(seq + 1));
            if (temp != null && temp.length > 0) {
              sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'";
              for (int j = 0; j < temp.length; j++)
                sqlValue = String.valueOf(sqlValue) + temp[j] + ","; 
              sqlValue = String.valueOf(sqlValue) + "',";
            } 
          }  
        if (fieldType != null && fieldType.equals("numbercompute") && 
          request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && request.getParameterValues(fieldStr[i][0])[seq].trim().length() > 0) {
          sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
          sqlValue = String.valueOf(sqlValue) + request.getParameterValues(fieldStr[i][0])[seq] + ",";
        } 
        if (fieldType != null && fieldType.equals("varcharcompute") && 
          request.getParameterValues(fieldStr[i][0]) != null && request.getParameterValues(fieldStr[i][0])[seq] != null && request.getParameterValues(fieldStr[i][0])[seq].trim().length() > 0) {
          sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + request.getParameterValues(fieldStr[i][0])[seq] + "',";
        } 
      } 
      int result = 0;
      sqlValue = CharacterTool.escapeHTMLTags2(sqlValue);
      if (infoId != null && 
        !sqlValue.endsWith("," + priId + ",")) {
        IO2File.printFile("插入到子表中：" + sqlHead.substring(0, sqlHead.length() - 1) + ") " + sqlValue.substring(0, sqlValue.length() - 1) + ")");
        result = dbopt.executeUpdate(String.valueOf(sqlHead.substring(0, sqlHead.length() - 1)) + ") " + sqlValue.substring(0, sqlValue.length() - 1) + ")");
      } 
      double temporaryForeignkey = 0.0D;
      if (priId != null)
        temporaryForeignkey = 0.0D - Double.parseDouble(priId); 
      if (result == 1) {
        IO2File.printFile("新增成功删除备份：DELETE FROM " + table + " where " + table + "_FOREIGNKEY=" + temporaryForeignkey);
        dbopt.executeUpdate("DELETE FROM " + table + " where " + table + "_FOREIGNKEY=" + temporaryForeignkey);
      } 
      if (result < 1) {
        success = Boolean.FALSE;
        IO2File.printFile("新增失败还原备份：update " + table + " set " + table + "_FOREIGNKEY=" + priId + " where " + table + "_FOREIGNKEY=" + temporaryForeignkey);
        dbopt.executeUpdate("update " + table + " set " + table + "_FOREIGNKEY=" + priId + " where " + table + "_FOREIGNKEY=" + temporaryForeignkey);
      } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        dbopt.executeUpdate("update " + table + " set " + table + "_date=sysdate() where " + table + "_id=" + infoId);
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      logger.error("CustomFormBD error on saveForeignData information:" + e.getMessage());
      e.printStackTrace();
    } 
    if (success.equals(Boolean.TRUE))
      return infoId; 
    return "";
  }
  
  public Boolean update(HttpServletRequest request) {
    Boolean success = Boolean.FALSE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      success = updateData(request, dbopt);
      if (success.booleanValue()) {
        String[] tables = request.getParameterValues("showTable");
        if (tables != null && tables.length > 0)
          for (int i = 0; i < tables.length; i++) {
            double temporaryForeignkey = 0.0D;
            if (request.getParameter("Info_Id") != null)
              temporaryForeignkey = 0.0D - Double.parseDouble(request.getParameter("Info_Id")); 
            IO2File.printFile("子表数据关联备份：update " + tables[i] + " set " + tables[i] + "_FOREIGNKEY=" + temporaryForeignkey + " where " + tables[i] + "_FOREIGNKEY=" + request.getParameter("Info_Id"));
            dbopt.executeUpdate("update " + tables[i] + " set " + tables[i] + "_FOREIGNKEY=" + temporaryForeignkey + " where " + tables[i] + "_FOREIGNKEY=" + request.getParameter("Info_Id"));
            if (tables[i] != null && tables[i].trim().length() >= 1) {
              String[][] field = getForeignFieldInfo(request.getParameter("Page_Id"), tables[i]);
              if (field != null && field.length > 0) {
                String[] types = request.getParameterValues(String.valueOf(field[0][0]) + "_type");
                String forInfoIds = "";
                if (types != null && types.length > 0)
                  for (int j = 0; j < types.length; j++)
                    forInfoIds = String.valueOf(forInfoIds) + saveForeignData(request, tables[i], field, j, dbopt, request.getParameter("Info_Id")) + ",";  
              } 
            } 
          }  
        updateRelationObject(request.getParameter("Info_Id"), request, dbopt);
        tables = request.getParameterValues("hideTable");
        if (tables != null && tables.length > 0)
          for (int i = 0; i < tables.length; i++) {
            double temporaryForeignkey = 0.0D;
            if (request.getParameter("Info_Id") != null)
              temporaryForeignkey = 0.0D - Double.parseDouble(request.getParameter("Info_Id")); 
            IO2File.printFile("子表数据关联备份：update " + tables[i] + " set " + tables[i] + "_FOREIGNKEY=" + temporaryForeignkey + " where " + tables[i] + "_FOREIGNKEY=" + request.getParameter("Info_Id"));
            dbopt.executeUpdate("update " + tables[i] + " set " + tables[i] + "_FOREIGNKEY=" + temporaryForeignkey + " where " + tables[i] + "_FOREIGNKEY=" + request.getParameter("Info_Id"));
            if (tables[i] != null && tables[i].trim().length() >= 1) {
              String[][] field = getForeignFieldInfo(request.getParameter("Page_Id"), tables[i]);
              if (field != null && field.length > 0) {
                String[] types = request.getParameterValues(String.valueOf(field[0][0]) + "_type");
                String forInfoIds = "";
                if (types != null && types.length > 0)
                  for (int j = 0; j < types.length; j++)
                    forInfoIds = String.valueOf(forInfoIds) + saveForeignData(request, tables[i], field, j, dbopt, request.getParameter("Info_Id")) + ",";  
              } 
            } 
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return success;
  }
  
  public Boolean updateData(HttpServletRequest request, DbOpt dbopt) {
    Boolean success = Boolean.TRUE;
    String pageId = (request.getParameter("Page_Id") == null || 
      request.getParameter("Page_Id").equals("null")) ? null : 
      request.getParameter("Page_Id");
    if (pageId == null || pageId.length() < 1)
      return Boolean.FALSE; 
    String infoId = (request.getParameter("Info_Id") == null || 
      request.getParameter("Info_Id").equals("null")) ? null : 
      request.getParameter("Info_Id");
    if (infoId == null || infoId.length() < 1)
      return Boolean.FALSE; 
    String[][] tableIdName = getTableIDAndName(pageId);
    String tableId = tableIdName[0][0];
    String table = tableIdName[0][1];
    try {
      String[][] fieldStr = getFieldInfo(pageId);
      if (fieldStr == null || fieldStr.length < 1)
        return Boolean.FALSE; 
      if (table == null || table.trim().length() < 1)
        return Boolean.FALSE; 
      String sqlHead = "update " + table + " set ";
      String sqlValue = " where " + table + "_id=" + infoId;
      String hideField = (request.getParameter("hide_Field") == null || request.getParameter("hide_Field").equals("null")) ? "" : request.getParameter("hide_Field");
      String computeSqlHead = sqlHead;
      Map<String, String> paraMap = new ConcurrentHashMap<String, String>();
      for (int i = 0; i < fieldStr.length; i++) {
        if (fieldStr[i][0] != null && hideField.indexOf(fieldStr[i][0]) < 0) {
          String fieldType = request.getParameter(String.valueOf(fieldStr[i][0]) + "_type");
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            if (fieldType != null && fieldType.equals("number") && 
              request.getParameter(fieldStr[i][0]) != null && request.getParameter(fieldStr[i][0]).length() > 0) {
              sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "=" + request.getParameter(fieldStr[i][0]) + ",";
              if ("1".equals(fieldStr[i][1])) {
                String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "=" + request.getParameter(fieldStr[i][0]) + " and " + table + "_id<>" + infoId);
                if (fieldOnly != null && fieldOnly.length > 0) {
                  success = Boolean.FALSE;
                  break;
                } 
              } 
            } 
            if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime")) && 
              request.getParameter(fieldStr[i][0]) != null) {
              if (fieldType.equals("varchar")) {
                String temp = request.getParameter(fieldStr[i][0]);
                paraMap.put(fieldStr[i][0], temp);
              } else {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='" + request.getParameter(fieldStr[i][0]).replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + "',";
              } 
              if ("1".equals(fieldStr[i][1])) {
                String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "=" + request.getParameter(fieldStr[i][0]) + " and " + table + "_id<>" + infoId);
                if (fieldOnly != null && fieldOnly.length > 0) {
                  success = Boolean.FALSE;
                  break;
                } 
              } 
            } 
            if (fieldType != null && fieldType.equals("file") && 
              request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName") != null) {
              String fileName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_fileName");
              String saveName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName");
              String[] fileNameArr = fileName.split(";");
              String[] saveNameArr = saveName.split(";");
              boolean flag = true;
              String saveFileTemp = "";
              String fileFileTemp = "";
              for (int k = 0; k < saveNameArr.length; k++) {
                if (saveNameArr[k] != null && 
                  !saveNameArr[k].equals("aaaaaa") && 
                  !saveNameArr[k].trim().equals("")) {
                  if (flag)
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='"; 
                  flag = false;
                  saveFileTemp = String.valueOf(saveFileTemp) + saveNameArr[k] + ",";
                  fileFileTemp = String.valueOf(fileFileTemp) + fileNameArr[k] + ",";
                } 
              } 
              if (!flag)
                sqlHead = String.valueOf(sqlHead) + saveFileTemp.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + ";" + 
                  fileFileTemp.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + "',"; 
            } 
            if (fieldType != null && fieldType.equals("combox") && 
              request.getParameterValues(fieldStr[i][0]) != null) {
              String[] temp = request.getParameterValues(fieldStr[i][0]);
              if (temp != null && temp.length > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='";
                for (int j = 0; j < temp.length; j++)
                  sqlHead = String.valueOf(sqlHead) + temp[j] + ","; 
                sqlHead = String.valueOf(sqlHead) + "',";
              } 
            } 
            if (fieldType != null && fieldType.equals("personorg") && 
              request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id") != null && 
              request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id")
              .length() > 0) {
              sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "=";
              sqlHead = String.valueOf(sqlHead) + "'" + request.getParameter(String.valueOf(fieldStr[i][0]) + "_Name").replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + ";" + 
                request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id").replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + "',";
            } 
          } 
          if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0) {
            if (fieldType != null && fieldType.equals("number") && 
              request.getParameter(fieldStr[i][0]) != null && request.getParameter(fieldStr[i][0]).length() > 0) {
              sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "=" + request.getParameter(fieldStr[i][0]) + ",";
              if ("1".equals(fieldStr[i][1])) {
                String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + 
                    fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "=" + request.getParameter(fieldStr[i][0]) + " and " + table + "_id<>" + infoId);
                if (fieldOnly != null && fieldOnly.length > 0) {
                  success = Boolean.FALSE;
                  break;
                } 
              } 
            } 
            if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime")) && 
              request.getParameter(fieldStr[i][0]) != null) {
              if (fieldType.equals("varchar")) {
                String temp = request.getParameter(fieldStr[i][0]);
                temp = temp.replaceAll("\\\\", "\\\\\\\\");
                paraMap.put(fieldStr[i][0], temp);
              } else {
                String temp = request.getParameter(fieldStr[i][0]).replaceAll("'", "'").replace("\"", "\"");
                temp = temp.replaceAll("\\\\", "\\\\\\\\");
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='" + temp + "',";
              } 
              if ("1".equals(fieldStr[i][1])) {
                String[] fieldOnly = dbopt.executeQueryToStrArr1("select " + fieldStr[i][0] + " from " + table + " where " + fieldStr[i][0] + "='" + 
                    request.getParameter(fieldStr[i][0]).replaceAll("'", "'").replace("\"", "\"") + "'" + " and " + table + "_id<>" + infoId);
                if (fieldOnly != null && fieldOnly.length > 0) {
                  success = Boolean.FALSE;
                  break;
                } 
              } 
            } 
            if (fieldType != null && fieldType.equals("file") && 
              request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName") != null) {
              String fileName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_fileName");
              String saveName = request.getParameter(String.valueOf(fieldStr[i][0]) + "_saveName");
              String[] fileNameArr = fileName.split(";");
              String[] saveNameArr = saveName.split(";");
              boolean flag = true;
              String saveFileTemp = "";
              String fileFileTemp = "";
              for (int k = 0; k < saveNameArr.length; k++) {
                if (saveNameArr[k] != null && 
                  !saveNameArr[k].equals("aaaaaa") && 
                  !saveNameArr[k].trim().equals("")) {
                  if (flag)
                    sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='"; 
                  flag = false;
                  saveFileTemp = String.valueOf(saveFileTemp) + saveNameArr[k] + ",";
                  fileFileTemp = String.valueOf(fileFileTemp) + fileNameArr[k] + ",";
                } 
              } 
              if (!flag) {
                sqlHead = String.valueOf(sqlHead) + saveFileTemp.replaceAll("'", "＇").replace("\"", "“") + ";" + fileFileTemp.replaceAll("'", "＇").replace("\"", "“") + "',";
              } else {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='',";
              } 
            } 
            if (fieldType != null && fieldType.equals("combox") && 
              request.getParameterValues(fieldStr[i][0]) != null) {
              String[] temp = request.getParameterValues(fieldStr[i][0]);
              if (temp != null && temp.length > 0) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "='";
                for (int j = 0; j < temp.length; j++)
                  sqlHead = String.valueOf(sqlHead) + temp[j] + ","; 
                sqlHead = String.valueOf(sqlHead) + "',";
              } 
            } 
            if (fieldType != null && fieldType.equals("personorg")) {
              String personorgIdValue = request.getParameter(String.valueOf(fieldStr[i][0]) + "_Id");
              if (personorgIdValue != null) {
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "=";
                if ("".equals(personorgIdValue)) {
                  sqlHead = String.valueOf(sqlHead) + "'',";
                } else {
                  sqlHead = String.valueOf(sqlHead) + "'" + request.getParameter(String.valueOf(fieldStr[i][0]) + "_Name") + ";" + personorgIdValue + "',";
                } 
              } 
            } 
          } 
          if (fieldType != null && "datetime".equals(fieldType) && 
            request.getParameter(fieldStr[i][0]) != null) {
            sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + "=";
            String hour = request.getParameter(String.valueOf(fieldStr[i][0]) + "hours");
            String minute = request.getParameter(String.valueOf(fieldStr[i][0]) + "minutes");
            String second = (request.getParameter(String.valueOf(fieldStr[i][0]) + "second") == null) ? "00" : request.getParameter(String.valueOf(fieldStr[i][0]) + "second");
            String timestr = String.valueOf(hour) + ":" + minute + ":" + second;
            if ("00:00:00".equals(timestr) || "0:0:00".equals(timestr) || "::".equals(timestr)) {
              hour = "";
              minute = "";
              second = "";
              sqlHead = String.valueOf(sqlHead) + "'" + request.getParameter(fieldStr[i][0]) + " " + "',";
            } else {
              sqlHead = String.valueOf(sqlHead) + "'" + request.getParameter(fieldStr[i][0]) + " " + hour + 
                ":" + minute + ":" + ((second == null) ? "00" : second) + "',";
            } 
          } 
        } 
      } 
      if (DbOpt.dbtype.indexOf("oracle") >= 0) {
        String[] computeField = request.getParameterValues("computeField");
        if (computeField != null && computeField.length > 0) {
          for (int k = 0; k < computeField.length; k++) {
            String computFieldValue = request.getParameter(computeField[k]);
            if (computFieldValue == null)
              computFieldValue = ""; 
            String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + 
                computeField[k] + "' and field_table=table_id and table_name='" + table + "'");
            if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
              if (!"".equals(computFieldValue)) {
                computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
              } 
            } else {
              if ("''".equals(computFieldValue))
                computFieldValue = ""; 
              computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
              computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + "',";
            } 
          } 
        } else {
          String cmpField = request.getParameter("computeField");
          if (cmpField != null && cmpField.trim().length() > 0 && 
            !cmpField.toUpperCase().equals("NULL")) {
            String cmpFieldValue = request.getParameter(cmpField);
            if (cmpFieldValue == null)
              String str = ""; 
            String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + cmpField + "' and field_table=table_id and table_name='" + table + "'");
            if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
              if (!"".equals(cmpFieldValue)) {
                computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                computeSqlHead = String.valueOf(computeSqlHead) + cmpFieldValue + ",";
              } 
            } else {
              if ("''".equals(cmpFieldValue))
                cmpFieldValue = ""; 
              computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
              computeSqlHead = String.valueOf(computeSqlHead) + "'" + cmpFieldValue.replaceAll("'", "＇").replace("\"", "“").replaceAll("&", "'||'&'||'") + "',";
            } 
          } 
        } 
      } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("mysql") >= 0) {
        String[] computeField = request.getParameterValues("computeField");
        if (computeField != null && computeField.length > 0) {
          for (int k = 0; k < computeField.length; k++) {
            String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + computeField[k] + "' and field_table=table_id and table_name='" + table + "'");
            String computFieldValue = request.getParameter(computeField[k]);
            if (computFieldValue == null)
              computFieldValue = ""; 
            if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
              if (!"".equals(computFieldValue)) {
                computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
                computeSqlHead = String.valueOf(computeSqlHead) + computFieldValue + ",";
              } 
            } else {
              if ("''".equals(computFieldValue))
                computFieldValue = ""; 
              computeSqlHead = String.valueOf(computeSqlHead) + computeField[k] + "=";
              computeSqlHead = String.valueOf(computeSqlHead) + "'" + computFieldValue.replaceAll("'", "＇").replace("\"", "“") + "',";
            } 
          } 
        } else {
          String cmpField = request.getParameter("computeField");
          if (cmpField != null && cmpField.trim().length() > 0 && 
            !cmpField.toUpperCase().equals("NULL")) {
            String cmpFieldValue = request.getParameter(cmpField);
            if (cmpFieldValue == null)
              String str = ""; 
            String type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + 
                cmpField + "' and field_table=table_id and table_name='" + table + "'");
            if (type.trim().equals("1000000") || type.trim().equals("1000001")) {
              if (!"".equals(cmpFieldValue)) {
                computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
                computeSqlHead = String.valueOf(computeSqlHead) + cmpFieldValue + ",";
              } 
            } else {
              if ("''".equals(cmpFieldValue))
                cmpFieldValue = ""; 
              computeSqlHead = String.valueOf(computeSqlHead) + cmpField + "=";
              computeSqlHead = String.valueOf(computeSqlHead) + "'" + cmpFieldValue.replaceAll("'", "＇").replace("\"", "“") + "',";
            } 
          } 
        } 
      } 
      int result = 1;
      if (!sqlHead.trim().endsWith(" set") && success.equals(Boolean.TRUE))
        result = dbopt.executeUpdate(String.valueOf(sqlHead.substring(0, sqlHead.length() - 1)) + sqlValue); 
      if (!computeSqlHead.trim().endsWith(" set") && 
        success.equals(Boolean.TRUE))
        result = dbopt.executeUpdate(String.valueOf(computeSqlHead.substring(0, computeSqlHead.length() - 1)) + sqlValue); 
      if (!paraMap.isEmpty())
        result = dbopt.executePSUpdate(table, "update", paraMap, String.valueOf(table) + "_id=" + infoId); 
      if (result < 1)
        success = Boolean.FALSE; 
    } catch (Exception e) {
      success = Boolean.FALSE;
      logger.error("CustomFormBD error on save information:" + 
          e.getMessage());
      e.printStackTrace();
    } 
    return success;
  }
  
  public String[][] setValue(String infoId, String pageId) {
    String[][] intinal = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(infoId, String.class);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = (String[][])ejbProxy.invoke("setValue", pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on setValue information:" + 
          e.getMessage());
    } 
    return intinal;
  }
  
  public String getHTML(String field, String noWriteField, String domainId, String fieldId) {
    String intinal = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(field, String.class);
      pg.put(noWriteField, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = ejbProxy.invoke("getHTML", pg.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return intinal;
  }
  
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request) {
    return getHTML(field, noWriteField, domainId, fieldId, request, "");
  }
  
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr) {
    String intinal = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(field, String.class);
      pg.put(noWriteField, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldId, String.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(computeFieldStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = ejbProxy.invoke("getHTML", pg.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return intinal;
  }
  
  public String getHTMLForWeiXin(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr) {
    String intinal = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(field, String.class);
      pg.put(noWriteField, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldId, String.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(computeFieldStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = ejbProxy.invoke("getHTMLForWeiXin", pg.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return intinal;
  }
  
  public String getHTMLBatchAdd(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr) {
    String intinal = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(field, String.class);
      pg.put(noWriteField, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldId, String.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(computeFieldStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = ejbProxy.invoke("getHTMLBatchAdd", pg.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return intinal;
  }
  
  public String getEditHTML(String field, String infoId, String pageId, String hideField, String domainId, String fieldId, String fromDraft) {
    return getEditHTML(field, infoId, pageId, hideField, domainId, fieldId, fromDraft, "", null);
  }
  
  public String getEditHTML(String field, String infoId, String pageId, String hideField, String domainId, String fieldId, String fromDraft, String computeFieldStr, HttpServletRequest request) {
    String intinal = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(9);
      pg.put(field, String.class);
      pg.put(infoId, String.class);
      pg.put(pageId, String.class);
      pg.put(hideField, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldId, String.class);
      pg.put(fromDraft, String.class);
      pg.put(computeFieldStr, String.class);
      pg.put(request, HttpServletRequest.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = ejbProxy.invoke("getEditHTML", pg.getParameters())
        .toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return intinal;
  }
  
  public String getEditHTMLForWeiXin(String field, String infoId, String pageId, String hideField, String domainId, String fieldId, String fromDraft, String computeFieldStr, HttpServletRequest request) {
    String intinal = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(9);
      pg.put(field, String.class);
      pg.put(infoId, String.class);
      pg.put(pageId, String.class);
      pg.put(hideField, String.class);
      pg.put(domainId, String.class);
      pg.put(fieldId, String.class);
      pg.put(fromDraft, String.class);
      pg.put(computeFieldStr, String.class);
      pg.put(request, HttpServletRequest.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      intinal = ejbProxy.invoke("getEditHTMLForWeiXin", pg.getParameters())
        .toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return intinal;
  }
  
  public String getForeignEditHTML(String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr) {
    return getForeignEditHTML(null, field, fieldValue, fieldTemp, hideField, index, seq, fieldId, fromDraft, foreignTableName, parentRecordId, curRecordId, isTotalField, computeFieldStr);
  }
  
  public String getForeignEditHTMLForWeiXin(String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr) {
    return getForeignEditHTML(null, field, fieldValue, fieldTemp, hideField, index, seq, fieldId, fromDraft, foreignTableName, parentRecordId, curRecordId, isTotalField, computeFieldStr);
  }
  
  public String getForeignEditHTML(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr) {
    String html = "";
    String temp = "";
    String[] tempArr = (String[])null;
    String type = "";
    Date now = new Date();
    DbOpt dbopt = null;
    boolean isNum = false;
    boolean isHide = false;
    try {
      dbopt = new DbOpt();
      if (fieldTemp != null && fieldTemp.length > 0 && 
        fieldTemp[index][1] != null && fieldTemp[index][1].trim().length() > 0) {
        String phoneNum, defaultValue, onblur;
        int i;
        String[][] selectedValue;
        int j;
        String dateString, temp1, styleValue;
        int nowHour;
        String temp2, jisuanSet;
        int nowMinute;
        String onblur1;
        int nowSecond;
        String temp_302;
        int hours;
        String fieldValueTemp;
        int minutes, second;
        boolean flagstrhours;
        int k;
        try {
          if (fieldTemp[index][2].equals("1000001")) {
            fieldValue = fieldValue.toLowerCase();
            if (fieldValue.indexOf("e") > 0) {
              String value1 = fieldValue.substring(0, fieldValue.indexOf('e'));
              String value2 = fieldValue.substring(fieldValue.indexOf('e') + 1, fieldValue.length());
              double value11 = Double.parseDouble(value1);
              double value22 = Double.parseDouble(value2);
              double laterValue = value11 * Math.pow(10.0D, value22);
              fieldValue = DecimalFormat.getInstance().format(laterValue).replaceAll(",", "");
            } 
            temp = fieldTemp[index][6];
            if (fieldValue.length() > 0 && temp != null && temp.startsWith("[point")) {
              int pointNum = Integer.parseInt(temp.substring(temp.indexOf(":") + 1, temp.indexOf(";")));
              String fillin = temp.substring(temp.indexOf("fillin:") + 7, temp.indexOf("]"));
              NumberFormat nf = NumberFormat.getNumberInstance();
              nf.setMaximumFractionDigits(pointNum);
              fieldValue = nf.format(Double.parseDouble(fieldValue)).replaceAll(",", "");
              if ("1".equals(fillin))
                if (fieldValue.indexOf(".") < 0) {
                  fieldValue = String.valueOf(fieldValue) + ".";
                  for (int x = 0; x < pointNum; x++)
                    fieldValue = String.valueOf(fieldValue) + "0"; 
                } else {
                  int nowNum = fieldValue.length() - fieldValue.indexOf(".") - 1;
                  for (int x = nowNum; x < pointNum; x++)
                    fieldValue = String.valueOf(fieldValue) + "0"; 
                }  
            } else if (fieldValue.indexOf(".") > 0) {
              while (fieldValue.endsWith("0"))
                fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
              if (fieldValue.endsWith("."))
                fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        int maxlen = 9;
        if (!"".equals(fieldTemp[index][7]))
          maxlen = Integer.parseInt(fieldTemp[index][7]); 
        if (fieldTemp[index][2].equals("1000000") || fieldTemp[index][2].equals("1000001")) {
          type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=number>";
          isNum = true;
          if (fieldTemp[index][2].equals("1000001")) {
            maxlen = 18;
          } else {
            maxlen = 8;
          } 
        } else {
          type = "<input type=hidden name=" + field + "_type id=" + field + 
            "_type value=varchar>";
        } 
        if (!fieldTemp[index][2].equals("1000003")) {
          type = String.valueOf(type) + "<input type=hidden name=" + field + "_size id=" + field + "_size value=" + maxlen + ">";
        } else {
          type = String.valueOf(type) + "<input type=hidden name=" + field + "_size id=" + field + "_size value=10000000>";
        } 
        if (hideField != null)
          if (hideField.toUpperCase().equals("ALL")) {
            isHide = true;
          } else {
            String hideFieldTmp = "," + hideField + ",";
            if (hideFieldTmp.indexOf("," + fieldTemp[index][0] + ",") >= 0 || hideFieldTmp.indexOf("," + fieldTemp[index][5] + ",") >= 0)
              isHide = true; 
          }  
        if (hideField.toUpperCase().equals("NONE"))
          isHide = false; 
        switch (Integer.parseInt(fieldTemp[index][1])) {
          case 200:
            if (isHide) {
              String str1 = (fieldValue == null) ? "" : fieldValue;
              String telAndSms = "";
              if (str1.startsWith("86")) {
                telAndSms = str1.substring(2);
              } else if (str1.startsWith("+86")) {
                telAndSms = str1.substring(3);
              } else {
                telAndSms = str1;
              } 
              telAndSms = telAndSms.replaceAll("-", "");
              String phone = "<a href=\"tel:" + telAndSms + "\">" + 
                "<img src=\"/jsoa/jsflow/images/dianhua.png\" style=\"cursor:pointer;width:20px;height:20px;border=0;\" title=\"拨打电话\" />" + 
                "</a>&nbsp;&nbsp;" + 
                "<a href=\"sms:" + telAndSms + "\">" + 
                "<img src=\"/jsoa/jsflow/images/duanxin.png\" style=\"cursor:pointer;width:20px;height:20px;border=0;\" title=\"发短信\" />" + 
                "</a>";
              String show = "if(navigator.userAgent.indexOf(\"Android\")>0 || navigator.userAgent.indexOf(\"iPad\")>0 || navigator.userAgent.indexOf(\"iPhone\")>0) ";
              html = "\n\nif(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "'))\n{\n" + 
                "document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + str1 + "&nbsp;&nbsp;';\n" + 
                show + "{\n" + 
                "document.getElementById('" + fieldTemp[index][0] + "-" + field + "').innerHTML+='&nbsp;" + phone + "';\n" + 
                "}\n" + 
                "}\n";
              break;
            } 
            phoneNum = (fieldValue == null) ? "" : fieldValue;
            phoneNum = phoneNum.equals("") ? ((fieldTemp[index][4] == null) ? "" : fieldTemp[index][4]) : phoneNum;
            html = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varchar>" + 
              "<input type=hidden name=" + field + "_size id=" + field + "_size value=255>";
            html = String.valueOf(html) + "<input type=text id=" + field + " name=" + field + " style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em;" + 
              " class=flowInput onblur=isPhone(this); maxlength=\"" + maxlen + "\"" + " value=\"" + phoneNum + " \">" + ((
              fieldTemp[index][3].equals("1") && hideField.indexOf("," + fieldTemp[index][0] + ",") < 0 && hideField.indexOf(fieldTemp[index][5]) < 0) ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + field + 
              "><label class=mustFillcolor>*</label>") : "");
            html = "\n\nif(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "'))\n{\n" + 
              "document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + html + "';\n" + 
              "}\n\n";
            break;
          case 406:
            if (isHide) {
              html = (fieldValue == null) ? "&nbsp" : 
                fieldValue.replaceAll("\n", "<br>")
                .replaceAll("\r", "");
              type = String.valueOf(type) + "<input type=hidden name=" + field + 
                "id=" + field + 
                " value=" + fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"").replaceAll("\n", "").replaceAll("\r", "") + ">";
            } else {
              html = "<input style=width:" + (
                fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + " name=" + field + " value='" + 
                fieldValue + "' " + (
                isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + 
                ">" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + 
              "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + 
              "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + 
              seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 101:
            defaultValue = "";
            if (fieldTemp[index][4] != null)
              defaultValue = fieldTemp[index][4].toString(); 
            if (defaultValue.startsWith("$[$[") && defaultValue.endsWith("]]")) {
              String dataSourceName = defaultValue.substring(4, defaultValue.indexOf("]$["));
              String sql = defaultValue.substring(defaultValue.indexOf("]$[") + 3, defaultValue.length() - 2);
              HttpSession hSession = request.getSession();
              String userId = hSession.getAttribute("userId").toString();
              String orgId = hSession.getAttribute("orgId").toString();
              String userAccount = hSession.getAttribute("userAccount").toString();
              String userName = hSession.getAttribute("userName").toString();
              sql = sql.replaceAll("\\@\\$\\@userId\\@\\$\\@", userId);
              sql = sql.replaceAll("\\@\\$\\@orgId\\@\\$\\@", orgId);
              sql = sql.replaceAll("\\@\\$\\@userAccount\\@\\$\\@", userAccount);
              sql = sql.replaceAll("\\@\\$\\@userName\\@\\$\\@", userName);
              DbOpt dbo = null;
              try {
                if (!"system".equals(dataSourceName)) {
                  dbo = new DbOpt(dataSourceName);
                  String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                  if (dbType.indexOf("oracle") >= 0) {
                    String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                    if (!"".equals(lang))
                      dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                  } 
                  defaultValue = dbo.executeQueryToStr(sql);
                  dbo.close();
                } else {
                  defaultValue = dbopt.executeQueryToStr(sql);
                } 
              } catch (Exception e3) {
                defaultValue = "";
                if (dbo != null)
                  dbo.close(); 
                e3.printStackTrace();
              } 
            } else if (defaultValue.startsWith("@[className") && defaultValue.endsWith("]")) {
              String className = defaultValue.substring(12, defaultValue.indexOf(";methodName:"));
              String classMethod = defaultValue.substring(defaultValue.indexOf(";methodName:") + 12, defaultValue.length() - 1);
              if (!"".equals(className) && !"".equals(classMethod))
                try {
                  FormReflection formReflection = new FormReflection();
                  Object obj = formReflection.execute(className, classMethod, request);
                  if (obj != null)
                    defaultValue = obj.toString(); 
                } catch (Exception e) {
                  defaultValue = "";
                  e.printStackTrace();
                }  
            } 
            onblur = "";
            if ("1".equals(isTotalField))
              onblur = "setSTotalValue(this);"; 
            if (computeFieldStr.indexOf(field) >= 0)
              onblur = String.valueOf(onblur) + "setComputeForeignFieldNew(this);"; 
            if (isHide) {
              if (fieldValue != null)
                fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\"); 
              fieldValue = fieldValue.replaceAll("\n", "").replaceAll("\r", "");
              if ("1".equals(fromDraft)) {
                html = "<input style=width:" + (
                  fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onblur=" + onblur + " id=" + 
                  field + " name=" + field + " value='" + 
                  fieldValue + "' readonly> ";
              } else {
                html = (fieldValue == null) ? "&nbsp" : 
                  fieldValue.replaceAll("\n", "<br>")
                  .replaceAll("\r", "");
                type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=\"" + fieldValue + "\">";
              } 
            } else {
              fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\");
              html = "<input style=width:" + (
                fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput id=" + 
                field + " name=" + field + " value='" + 
                fieldValue + "' " + (
                isNum ? (" onblur=checkNum(this);checkSize(this);" + onblur) : (
                "maxlength='" + maxlen + "'")) + ">" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value='" + 
              defaultValue + "'>";
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + 
              "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + 
              "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + 
              seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 102:
            if (isHide) {
              html = "<input type=password style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + 
                " name=" + field + " value='" + fieldValue + "' readOnly> ";
              type = (new StringBuilder(String.valueOf(type))).toString();
            } else {
              html = "<input type=password style=width:" + (
                fieldTemp[index][3].equals("1") ? "92" : "100") + 
                "%;font-size:1em; id=" + field + 
                " name=" + field + " id=" + field + " value='" + fieldValue + 
                "' " + (
                isNum ? " onblur=checkNum(this);checkSize(this);" : (
                "maxlength='" + maxlen + "'")) + ">" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + 
              "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + 
              "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + 
              seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 103:
            html = "";
            temp = fieldTemp[index][6];
            if (temp == null || temp.trim().length() < 1)
              break; 
            tempArr = temp.split(";");
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + "id=" + field + 
                " value=" + fieldValue + ">";
              if (temp.startsWith("@")) {
                String table = temp.substring(temp.indexOf("][") + 
                    2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  data = dbopt.executeQueryToStrArr2(
                      "select " + 
                      table.substring(0, 
                        table.indexOf(".")) + "_id," + 
                      table.substring(table.indexOf(".") + 
                        1, table.length()) + " from " + 
                      table.substring(0, 
                        table.indexOf(".")));
                } catch (Exception exception) {}
                if (data != null)
                  for (int n = 0; n < data.length; n++) {
                    html = String.valueOf(html) + "var " + field + 
                      String.valueOf(n) + 
                      "_temp='<input type=radio style=font-size:1em; disabled id=" + 
                      field + " name=" + field + 
                      " id=" + field + 
                      " value=" + data[n][0] + 
                      ">';\n";
                    html = String.valueOf(html) + 
                      "if(document.getElementsByName('" + 
                      fieldTemp[index][0] + "-" + 
                      field + "')[" + seq + 
                      "])\n{document.getElementsByName('" + 
                      fieldTemp[index][0] + "-" + 
                      field + "')[" + seq + 
                      "].innerHTML+=" + field + 
                      String.valueOf(n) + 
                      "_temp;\ndocument.getElementsByName('" + 
                      fieldTemp[index][0] + "-" + 
                      field + "')[" + seq + 
                      "].innerHTML+='" + data[n][1] + "';}";
                  }  
                break;
              } 
              for (int m = 0; m < tempArr.length; m++) {
                if (tempArr[m] != null && 
                  tempArr[m].trim().length() > 0 && 
                  tempArr[m].indexOf("/") > 0 && 
                  tempArr[m].indexOf("/") < 
                  tempArr[m].length() - 1) {
                  html = String.valueOf(html) + "var " + field + 
                    String.valueOf(m) + 
                    "_temp='<input type=radio style=font-size:1em; disabled id=" + 
                    field + " name=" + field + 
                    " value=" + 
                    tempArr[m].split("/")[0] + (
                    
                    tempArr[m].split("/")[0].equals(fieldValue) ? 
                    " checked " : "") + ">';\n";
                  html = String.valueOf(html) + 
                    "if(document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + 
                    field + 
                    "')[" + seq + 
                    "])\n{document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + 
                    field + 
                    "')[" + seq + "].innerHTML+=" + 
                    field + String.valueOf(m) + 
                    "_temp;\ndocument.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + 
                    field + "')[" + seq + 
                    "].innerHTML+='" + 
                    tempArr[m].split("/")[1] + "';}";
                } 
              } 
              html = "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + 
                "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML='" + type + 
                "';}" + html;
              break;
            } 
            if (temp.startsWith("@")) {
              String table = temp.substring(temp.indexOf("][") + 
                  2, temp.length() - 1);
              String[][] data = (String[][])null;
              try {
                data = dbopt.executeQueryToStrArr2("select " + 
                    table.substring(0, table.indexOf(".")) + 
                    "_id," + 
                    table.substring(table.indexOf(".") + 1, 
                      table.length()) + " from " + 
                    table.substring(0, table.indexOf(".")));
              } catch (Exception exception) {}
              if (data != null)
                for (int m = 0; m < data.length; m++) {
                  html = String.valueOf(html) + "var " + field + String.valueOf(m) + 
                    "_temp='<input type=radio style=font-size:1em; id=" + 
                    field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " name=" + field + 
                    String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                    " value=" + data[m][0] + ">';\n";
                  html = String.valueOf(html) + "if(document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + 
                    "')[" + seq + 
                    "])\n{document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + 
                    "')[" + seq + "].innerHTML+=" + 
                    field + String.valueOf(m) + 
                    "_temp;\ndocument.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + 
                    "')[" + seq + "].innerHTML+='" + 
                    data[m][1] + ((
                    fieldTemp[index][3].equals("1") && 
                    m == data.length - 1) ? (
                    "<input type=hidden name=mustWrite id=mustWrite value=" + 
                    field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                    "><label class=mustFillcolor>*</label>") : 
                    "") + "';}";
                }  
              break;
            } 
            for (i = 0; i < tempArr.length; i++) {
              if (tempArr[i] != null && 
                tempArr[i].trim().length() > 0 && 
                tempArr[i].indexOf("/") > 0 && 
                tempArr[i].indexOf("/") < 
                tempArr[i].length() - 1) {
                html = String.valueOf(html) + "var " + field + String.valueOf(i) + 
                  "_temp='<input type=radio style=font-size:1em; id=" + 
                  field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " name=" + 
                  field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " value=" + 
                  tempArr[i].split("/")[0] + (
                  tempArr[i].split("/")[0].equals(
                    fieldValue) ? " checked " : "") + 
                  ">';\n";
                html = String.valueOf(html) + "if(document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + 
                  "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "].innerHTML+=" + 
                  field + String.valueOf(i) + 
                  "_temp;\ndocument.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "].innerHTML+='" + 
                  tempArr[i].split("/")[1] + ((
                  fieldTemp[index][3].equals("1") && 
                  i == tempArr.length - 1) ? (
                  "<input type=hidden name=mustWrite id=mustWrite value=" + 
                  field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                  "><label class=mustFillcolor>*</label>") : 
                  "") + "';}";
              } 
            } 
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + 
              "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + "';}" + 
              html;
            break;
          case 104:
            html = "";
            temp = fieldTemp[index][6];
            if (temp == null || temp.trim().length() < 1)
              break; 
            tempArr = temp.split(";");
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + 
                "_type id=" + field + 
                "_type value=" + fieldValue + ">";
              if (temp.startsWith("@")) {
                String table = temp.substring(temp.indexOf("][") + 
                    2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  data = dbopt.executeQueryToStrArr2(
                      "select " + 
                      table.substring(0, 
                        table.indexOf(".")) + "_id," + 
                      table.substring(table.indexOf(".") + 
                        1, table.length()) + " from " + 
                      table.substring(0, 
                        table.indexOf(".")));
                } catch (Exception exception) {}
                if (data != null)
                  for (int m = 0; m < data.length; m++) {
                    html = String.valueOf(html) + "var " + field + 
                      "_temp='<input type=checkbox style=font-size:1em; disabled id=" + 
                      field + " name=" + field + 
                      " value=" + data[m][0] + (
                      (fieldValue.indexOf(String.valueOf(data[m][0]) + 
                        ",") >= 0) ? " checked " : "") + 
                      ">';\n";
                    html = String.valueOf(html) + 
                      "if(document.getElementsByName('" + 
                      fieldTemp[index][0] + "-" + 
                      field + 
                      "')[" + seq + 
                      "])\n{document.getElementsByName('" + 
                      fieldTemp[index][0] + "-" + 
                      field + 
                      "')[" + seq + "].innerHTML+=" + 
                      field + 
                      "_temp;\ndocument.getElementsByName('" + 
                      fieldTemp[index][0] + "-" + 
                      field + "')[" + seq + 
                      "].innerHTML+='" + data[m][1] + "';}";
                  }  
                break;
              } 
              for (i = 0; i < tempArr.length; i++) {
                if (tempArr[i] != null && 
                  tempArr[i].trim().length() > 0 && 
                  tempArr[i].indexOf("/") >= 0 && 
                  tempArr[i].indexOf("/") < 
                  tempArr[i].length() - 1) {
                  html = String.valueOf(html) + "var " + field + 
                    "_temp='<input type=checkbox style=font-size:1em; disabled id=" + 
                    field + " name=" + field + 
                    " value=" + 
                    tempArr[i].split("/")[0] + (
                    (fieldValue.indexOf(
                      String.valueOf(tempArr[i].split("/")[0]) + ",") >= 0) ? 
                    " checked " : "") + ">';\n";
                  html = String.valueOf(html) + 
                    "if(document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + 
                    field + 
                    "')[" + seq + 
                    "])\n{document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + 
                    field + 
                    "')[" + seq + "].innerHTML+=" + 
                    field + 
                    "_temp;\ndocument.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + 
                    field + "')[" + seq + 
                    "].innerHTML+='" + 
                    tempArr[i].split("/")[1] + "';}";
                } 
              } 
              html = "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + 
                "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML='" + type + 
                "';}" + html;
              break;
            } 
            if (temp.startsWith("@")) {
              type = "<input type=hidden name=" + field + 
                "_type id=" + field + 
                "_type value=combox>";
              String table = temp.substring(temp.indexOf("][") + 
                  2, temp.length() - 1);
              String[][] data = (String[][])null;
              try {
                data = dbopt.executeQueryToStrArr2("select " + 
                    table.substring(0, table.indexOf(".")) + 
                    "_id," + 
                    table.substring(table.indexOf(".") + 1, 
                      table.length()) + " from " + 
                    table.substring(0, table.indexOf(".")));
              } catch (Exception exception) {}
              if (data != null)
                for (int m = 0; m < data.length; m++) {
                  html = String.valueOf(html) + "var " + field + 
                    "_temp='<input type=checkbox style=font-size:1em; id=" + 
                    field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " name=" + field + 
                    String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                    " value=" + data[m][0] + (
                    (fieldValue.indexOf(String.valueOf(data[m][0]) + 
                      ",") >= 0) ? " checked " : "") + 
                    ">';\n";
                  html = String.valueOf(html) + "if(document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + 
                    "')[" + seq + 
                    "])\n{document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + 
                    "')[" + seq + "].innerHTML+=" + 
                    field + 
                    "_temp;\ndocument.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + 
                    "')[" + seq + "].innerHTML+='" + 
                    data[m][1] + ((
                    fieldTemp[index][3].equals("1") && 
                    m == data.length - 1) ? (
                    "<input type=hidden name=mustWrite name=mustWrite id=mustWrite value=" + 
                    field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                    "><label class=mustFillcolor>*</label>") : 
                    "") + "';}";
                }  
              break;
            } 
            type = "<input type=hidden name=" + field + 
              "_type id=" + field + 
              "_type value=combox>";
            for (i = 0; i < tempArr.length; i++) {
              if (tempArr[i] != null && 
                tempArr[i].trim().length() > 0 && 
                tempArr[i].indexOf("/") >= 0 && 
                tempArr[i].indexOf("/") < 
                tempArr[i].length() - 1) {
                html = String.valueOf(html) + "var " + field + 
                  "_temp='<input type=checkbox style=font-size:1em; id=" + 
                  field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " name=" + 
                  field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " value=" + 
                  tempArr[i].split("/")[0] + (
                  (fieldValue.indexOf(
                    String.valueOf(tempArr[i].split("/")[0]) + ",") >= 0) ? 
                  " checked " : "") + ">';\n";
                html = String.valueOf(html) + "if(document.getElementsByName('" + 
                  fieldTemp[index][0] + 
                  "-" + field + 
                  "')[" + seq + 
                  "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "].innerHTML+=" + 
                  field + 
                  "_temp;\ndocument.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "].innerHTML+='" + 
                  tempArr[i].split("/")[1] + ((
                  fieldTemp[index][3].equals("1") && 
                  i == tempArr.length - 1) ? (
                  "<input type=hidden name=mustWrite id=mustWrite value=" + 
                  field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                  "><label class=mustFillcolor>*</label>") : 
                  "") + "';}";
              } 
            } 
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + 
              "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + "';}" + 
              html;
            break;
          case 105:
            defaultValue = "";
            if (fieldTemp[index][4] != null && !"".equals(fieldTemp[index][4].toString())) {
              defaultValue = fieldTemp[index][4].toString();
              HttpSession hSession = request.getSession();
              String userId = hSession.getAttribute("userId").toString();
              String orgId = hSession.getAttribute("orgId").toString();
              String userAccount = hSession.getAttribute("userAccount").toString();
              String userName = hSession.getAttribute("userName").toString();
              if (defaultValue.equalsIgnoreCase("@$@CURRENTYEAR@$@")) {
                defaultValue = String.valueOf((new Date()).getYear() + 1900);
              } else if (defaultValue.equalsIgnoreCase("@$@CURRENTMONTH@$@")) {
                defaultValue = String.valueOf((new Date()).getMonth() + 1);
              } else if (defaultValue.equals("@$@userId@$@")) {
                defaultValue = userId;
              } else if (defaultValue.equals("@$@orgId@$@")) {
                defaultValue = orgId;
              } else if (defaultValue.equals("@$@userAccount@$@")) {
                defaultValue = userAccount;
              } else if (defaultValue.equals("@$@userName@$@")) {
                defaultValue = userName;
              } else {
                defaultValue = InitWorkFlowData.getValueFromRequest(request, defaultValue);
              } 
            } 
            html = "<select  style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + " name=" + field;
            if (!isHide)
              html = String.valueOf(html) + " onchange=\"" + field + "_onchange(this);\""; 
            html = String.valueOf(html) + "><option value=\"\">==请选择==</option>";
            selectedValue = fieldIsFromOther(dbopt, foreignTableName, fieldId, fieldTemp[index][5], fieldValue, parentRecordId, curRecordId);
            if (selectedValue != null) {
              if (isHide) {
                html = "";
                for (int n = 0; n < selectedValue.length; n++) {
                  if (fieldValue.equals(selectedValue[n][0])) {
                    html = "<input type=hidden name=" + field + " id=" + field + " value=\"" + 
                      selectedValue[n][0] + "\">";
                    html = String.valueOf(html) + selectedValue[n][1];
                  } 
                } 
                html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value=\"" + 
                  defaultValue + "\">";
                html = "if(document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "].innerHTML='" + type + html + "';}";
                break;
              } 
              for (int m = 0; m < selectedValue.length; m++)
                html = String.valueOf(html) + "<option value=\"" + selectedValue[m][0] + "\"" + (
                  selectedValue[m][0].equals(fieldValue) ? 
                  " selected " : "") + ">" + 
                  selectedValue[m][1] + 
                  "</option>"; 
              html = String.valueOf(html) + "</select>";
              html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value=\"" + 
                defaultValue + "\">";
              html = "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + 
                field + 
                "'))\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML='" + type + 
                "';document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + 
                field + "')[" + seq + "].innerHTML+='" + html + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "") + "';}";
              break;
            } 
            temp = fieldTemp[index][6];
            if (temp == null || temp.trim().length() < 1)
              break; 
            if (temp.startsWith("*")) {
              String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
              temp = (new BaseSetEJBBean()).getValue(parentId);
            } 
            tempArr = temp.split(";");
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "";
              if (temp.startsWith("@")) {
                String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + 
                      "_id," + table.substring(table.indexOf(".") + 1, table.length()) + 
                      " from " + table.substring(0, table.indexOf(".")));
                } catch (Exception exception) {}
                if (data != null)
                  for (int m = 0; m < data.length; m++) {
                    if (fieldValue.equals(data[m][0])) {
                      html = String.valueOf(html) + data[m][1];
                      break;
                    } 
                  }  
              } else if (temp.startsWith("$")) {
                String sql, dataSourceName = "system";
                if (temp.indexOf("].$[") > 0) {
                  dataSourceName = temp.substring(2, temp.indexOf("].$["));
                  sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
                } else {
                  sql = temp.substring(2, temp.length() - 1);
                } 
                if ("qingdaojinwang".equals(SystemCommon.getCustomerName()))
                  sql = sql.split(" where ")[0]; 
                if ("qingdaojinwang".equals(SystemCommon.getCustomerName())) {
                  sql = InitWorkFlowData.getValueFromRequestKing(request, sql, parentRecordId);
                } else {
                  sql = InitWorkFlowData.getValueFromRequest(request, sql);
                } 
                String[][] data = (String[][])null;
                DbOpt dbo = null;
                try {
                  if (!"system".equals(dataSourceName)) {
                    dbo = new DbOpt(dataSourceName);
                    String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                    if (dbType.indexOf("oracle") >= 0) {
                      String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                      if (!"".equals(lang))
                        dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                    } 
                    data = dbo.executeQueryToStrArr2(sql);
                    dbo.close();
                  } else {
                    data = dbopt.executeQueryToStrArr2(sql);
                  } 
                } catch (Exception e3) {
                  if (dbo != null)
                    dbo.close(); 
                  e3.printStackTrace();
                } 
                if (data != null)
                  for (int m = 0; m < data.length; m++) {
                    if (fieldValue.equals(data[m][0])) {
                      html = String.valueOf(html) + data[m][1];
                      break;
                    } 
                  }  
              } else {
                for (int m = 0; m < tempArr.length; m++) {
                  if (tempArr[m] != null && tempArr[m].trim().length() > 0 && tempArr[m].indexOf("/") >= 0 && 
                    tempArr[m].indexOf("/") < tempArr[m].length() - 1 && 
                    tempArr[m].split("/")[0].equals(fieldValue)) {
                    html = tempArr[m].split("/")[1];
                    break;
                  } 
                } 
              } 
              html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value=\"" + 
                defaultValue + "\">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + html + "';}";
              break;
            } 
            if (temp.startsWith("@")) {
              String table = temp.substring(temp.indexOf("][") + 
                  2, temp.length() - 1);
              String[][] data = (String[][])null;
              try {
                data = dbopt.executeQueryToStrArr2("select " + 
                    table.substring(0, table.indexOf(".")) + 
                    "_id," + 
                    table.substring(table.indexOf(".") + 1, 
                      table.length()) + " from " + 
                    table.substring(0, table.indexOf(".")));
              } catch (Exception e3) {
                e3.printStackTrace();
              } 
              if (data != null)
                for (int m = 0; m < data.length; m++)
                  html = String.valueOf(html) + "<option value=\"" + data[m][0] + "\"" + (
                    data[m][0].equals(fieldValue) ? 
                    " selected " : "") + ">" + 
                    data[m][1] + 
                    "</option>";  
              html = String.valueOf(html) + "</select>";
              html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value=\"" + 
                defaultValue + "\">";
              html = "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + 
                field + 
                "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML='" + type + 
                "';document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + 
                field + "')[" + seq + "].innerHTML+='" + html + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "") + "';}";
              break;
            } 
            if (temp.startsWith("$")) {
              String dataSourceName = "system";
              if (temp.indexOf("].$[") > 0) {
                dataSourceName = temp.substring(2, temp.indexOf("].$["));
                sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
              } else {
                sql = temp.substring(2, temp.length() - 1);
              } 
              String sql = InitWorkFlowData.getValueFromRequest(request, sql);
              String[][] data = (String[][])null;
              DbOpt dbo = null;
              try {
                if (!"system".equals(dataSourceName)) {
                  dbo = new DbOpt(dataSourceName);
                  String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                  if (dbType.indexOf("oracle") >= 0) {
                    String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                    if (!"".equals(lang))
                      dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                  } 
                  data = dbo.executeQueryToStrArr2(sql);
                  dbo.close();
                } else {
                  data = dbopt.executeQueryToStrArr2(sql);
                } 
              } catch (Exception e3) {
                if (dbo != null)
                  dbo.close(); 
                e3.printStackTrace();
              } 
              if (data != null)
                for (int m = 0; m < data.length; m++)
                  html = String.valueOf(html) + "<option value=\"" + data[m][0] + "\"" + (
                    data[m][0].equals(fieldValue) ? 
                    " selected " : "") + ">" + 
                    data[m][1] + 
                    "</option>";  
              html = String.valueOf(html) + "</select>";
              html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value=\"" + 
                defaultValue + "\">";
              html = "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "'))\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML='" + type + 
                "';document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "") + "';}";
              break;
            } 
            for (j = 0; j < tempArr.length; j++) {
              if (tempArr[j] != null && 
                tempArr[j].trim().length() > 0 && 
                tempArr[j].indexOf("/") >= 0 && 
                tempArr[j].indexOf("/") < 
                tempArr[j].length() - 1)
                html = String.valueOf(html) + "<option value=\"" + 
                  tempArr[j].split("/")[0] + "\"" + (
                  
                  tempArr[j].split("/")[0].equals(
                    fieldValue) ? 
                  " selected " : "") + ">" + 
                  tempArr[j].split("/")[1] + 
                  "</option>"; 
            } 
            html = String.valueOf(html) + "</select>";
            html = String.valueOf(html) + "<input type=hidden name=" + field + "_defaultvalue id=" + field + "_defaultvalue value=\"" + 
              defaultValue + "\">";
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+='" + html + (
              fieldTemp[index][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "") + "';}";
            break;
          case 107:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
              break;
            } 
            type = String.valueOf(type) + "<input  type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) name=" + 
              field + " id=" + field + " onclick=setDay(this) value='" + 
              fieldValue + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
              fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + "><label class=mustFillcolor>*</label>") : "");
            html = String.valueOf(html) + "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML=\"" + type + "\";}";
            break;
          case 108:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
              break;
            } 
            html = "<INPUT  type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
              field + " name=" + field + " onclick='showDateTimeBar(this, \\\"h:n:s\\\", \\\"" + 
              String.valueOf(now.getMonth() + 1) + "/" + String.valueOf(now.getDate() + 1) + "/" + 
              String.valueOf(now.getYear() + 1900) + " " + fieldValue + "\\\", " + 
              "test, \\\"hello world\\\")' name=" + field + " value=\\\"" + 
              fieldValue + "\\\" style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
              fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + "><label class=mustFillcolor>*</label>") : "");
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 109:
            if (isHide) {
              if (SystemCommon.getSYWorkflowHR() == 1) {
                String typeValue = "<input type=hidden name=" + field + " id=" + field + " value='" + fieldValue + "'>";
                html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
                html = String.valueOf(html) + "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + typeValue + "\";}";
                break;
              } 
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=\\'" + fieldValue + "\\'>";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
              break;
            } 
            dateString = "";
            nowHour = 0;
            nowMinute = 0;
            nowSecond = 0;
            if (isHide) {
              dateString = String.valueOf(String.valueOf(now.getYear() + 1900)) + "-" + ((now.getMonth() >= 9) ? String.valueOf(now.getMonth() + 1) : ("0" + String.valueOf(now.getMonth() + 1))) + 
                "-" + ((now.getDate() > 9) ? String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate())));
              nowHour = now.getHours();
              nowMinute = now.getMinutes();
              nowSecond = now.getSeconds();
            } 
            type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=datetime>";
            type = String.valueOf(type) + "<input type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) size=12 name=" + 
              field + " id=" + field + " onclick=setDay(this) value='";
            if (fieldValue == null || "".equals(fieldValue) || "null".equals(fieldValue)) {
              type = String.valueOf(type) + fieldValue + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
                fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + field + 
                "><label class=mustFillcolor>*</label>") : "");
            } else {
              type = String.valueOf(type) + fieldValue.split(" ")[0] + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
                fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + field + 
                "><label class=mustFillcolor>*</label>") : "");
            } 
            hours = -1;
            minutes = -1;
            second = -1;
            flagstrhours = false;
            if (fieldValue != null && 
              fieldValue.trim().length() >= 12 && 
              fieldValue.indexOf(" ") > 0 && 
              fieldValue.indexOf(":") > 0) {
              hours = Integer.parseInt(fieldValue.trim()
                  .split(" ")[1].split(":")[0]);
              minutes = Integer.parseInt(fieldValue.trim()
                  .split(" ")[1].split(":")[1]);
              if ((fieldValue.trim().split(" ")[1].split(":")).length == 3) {
                second = Integer.parseInt(fieldValue.trim()
                    .split(" ")[1].split(":")[2]);
                flagstrhours = true;
              } 
            } 
            if (hours == 24)
              hours = 0; 
            type = String.valueOf(type) + "日&nbsp;<select name=" + field + "hours id=" + field + "hours>";
            for (k = 0; k < 24; k++) {
              String strhours = "";
              if (k >= 0 && k < 10) {
                strhours = "0" + String.valueOf(k);
              } else {
                strhours = String.valueOf(k);
              } 
              if (flagstrhours) {
                type = String.valueOf(type) + "<option value=" + String.valueOf(strhours) + (
                  (k == hours) ? " selected" : "") + ">" + 
                  String.valueOf(strhours) + "</option>";
              } else {
                type = String.valueOf(type) + "<option value=" + String.valueOf(strhours) + (
                  (k == nowHour) ? " selected" : "") + ">" + 
                  String.valueOf(strhours) + "</option>";
              } 
            } 
            type = String.valueOf(type) + "</select>";
            type = String.valueOf(type) + "时&nbsp;<select name=" + field + "minutes id=" + field + "minutes>";
            for (k = 0; k < 60; k++) {
              String strminutes = "";
              if (k >= 0 && k < 10) {
                strminutes = "0" + String.valueOf(k);
              } else {
                strminutes = String.valueOf(k);
              } 
              if (flagstrhours) {
                type = String.valueOf(type) + "<option value=" + String.valueOf(strminutes) + (
                  (k == minutes) ? " selected" : "") + ">" + 
                  String.valueOf(strminutes) + "</option>";
              } else {
                type = String.valueOf(type) + "<option value=" + String.valueOf(strminutes) + (
                  (k == nowMinute) ? " selected" : "") + ">" + 
                  String.valueOf(strminutes) + "</option>";
              } 
            } 
            type = String.valueOf(type) + "</select>分";
            if (!"pengchi".equals(SystemCommon.getCustomerName())) {
              type = String.valueOf(type) + "<select name=" + field + "second style=font-size:1em; id=" + field + "second>";
              for (k = 0; k < 60; k++) {
                String strsecond = "";
                if (k >= 0 && k < 10) {
                  strsecond = "0" + String.valueOf(k);
                } else {
                  strsecond = String.valueOf(k);
                } 
                if (flagstrhours) {
                  type = String.valueOf(type) + "<option value=" + String.valueOf(strsecond) + (
                    (k == second) ? " selected" : 
                    "") + ">" + String.valueOf(strsecond) + 
                    "</option>";
                } else {
                  type = String.valueOf(type) + "<option value=" + String.valueOf(strsecond) + (
                    (k == nowSecond) ? " selected" : 
                    "") + ">" + String.valueOf(strsecond) + 
                    "</option>";
                } 
              } 
              type = String.valueOf(type) + "</select>秒&nbsp;";
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML=\"" + type + "\";}";
            break;
          case 110:
            if (isHide) {
              if (fieldValue != null)
                fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\"); 
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=\\'" + fieldValue.replaceAll("\n", "").replaceAll("\r", "") + "\\' >";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + ((fieldValue == null) ? "&nbsp" : 
                fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
              break;
            } 
            if (fieldValue != null)
              fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\"); 
            html = "<textarea class=flowInput onmouseover=setStyle(this) onblur=checkSize(this) onmouseout=setStyle(this) id=" + field + 
              " name=" + field + " style=\"width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em;\" rows=\"6\">" + (
              (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "\\\\n'+'")
              .replaceAll("\r", "\\\\r'+'")) + "</textarea>" + (
              fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + "><label class=mustFillcolor>*</label>") : "");
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
            break;
          case 111:
            if (isHide) {
              html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
            } else {
              html = 
                "<input type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + " name=" + field + " value=\"" + ((fieldValue == null) ? "&nbsp" : 
                fieldValue.replaceAll("\n", "").replaceAll("\r", "")) + "\" " + (
                isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + (fieldTemp[index][3].equals("1") ? (
                " style=width:80%;font-size:1em; ><input type=hidden name=mustWrite id=mustWrite value=" + field + "><label class=mustFillcolor>*</label>") : " sytle=width:95%; >");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
            break;
          case 112:
            break;
          case 113:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + ((fieldValue == null) ? "&nbsp" : 
                fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
              break;
            } 
            temp1 = 
              "<input type=hidden id=" + 
              field + " name=" + field + " value='" + (
              (fieldValue == null) ? "&nbsp" : 
              fieldValue
              .replaceAll("\n", "").replaceAll("\r", "")
              .replaceAll("\"", "\\\\\"")) + 
              "'>";
            temp2 = "<IFRAME id='" + field + "_html' src='/jsoa/public/edit/ewebeditor.htm?id=" + 
              field + "&style=coolblue&lang=zh_cn' frameborder=0" + 
              " scrolling='no' width='98%' height='350'></IFRAME>" + (
              fieldTemp[index][3].equals("1") ? ("<input type=hidden name=hasHtml id=hasHtml value=" + 
              field + "><input type=hidden name=mustWrite id=mustWrite value=" + 
              field + "><label class=mustFillcolor>*</label>") : 
              "");
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+=\"" + temp1 + 
              "\";document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+=\"" + temp2 + "\";}";
            break;
          case 114:
          case 115:
            break;
          case 116:
            if (isHide) {
              html = "<input type=hidden id=" + field + " name=" + field + " value=\\\"" + fieldValue + "\\\">";
              html = String.valueOf(html) + "<input type='button' class=btnButton4font onclick=\\\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + 
                field + ".value+'&EditType=0,0&UserName='+document.all.user_Name.value+'&ShowSign=0&CanSave=0&moduleType=information&saveHtmlImage=0&saveDocFile=0&field=" + 
                field + "&FileType=.doc&showSignButton=1', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0')\\\" value='查看正文' />";
              type = "";
            } else {
              html = "<input type=hidden id=" + field + " name=" + 
                field + " value=\\\"" + fieldValue + "\\\">";
              String bjzw2017 = "编辑正文";
              if ("shandongguotou".equals(SystemCommon.getCustomerName()))
                bjzw2017 = "打开正文"; 
              html = String.valueOf(html) + "<input type='button' class=btnButton4font onclick=\\\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + 
                field + ".value+'&EditType=1&UserName='+document.all.user_Name.value+'&ShowSign=0&CanSave=1&moduleType=information&saveHtmlImage=0&saveDocFile=0&field=" + 
                field + "&FileType=.doc&showEditButton=1&showSignButton=1', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0')\\\" value='" + bjzw2017 + "' />" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 117:
            if (isHide) {
              html = "<input type=hidden id=" + field + " name=" + field + " value=\\\"" + fieldValue + "\\\">";
              html = String.valueOf(html) + "<input type='button' class=btnButton4font onclick=\\\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + 
                field + ".value+'&EditType=1&UserName='+document.all.user_Name.value+'&ShowSign=0&CanSave=0&moduleType=information&saveHtmlImage=0&saveDocFile=0&field=" + 
                field + "&FileType=.xls', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0')\\\" value='查看正文' />";
              type = "";
            } else {
              html = "<input type=hidden id=" + field + " name=" + 
                field + " value=\\\"" + fieldValue + "\\\">";
              String bjzw2017 = "编辑正文";
              if ("shandongguotou".equals(SystemCommon.getCustomerName()))
                bjzw2017 = "打开正文"; 
              html = String.valueOf(html) + "<input type='button' class=btnButton4font onclick=\\\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + 
                field + ".value+'&EditType=1&UserName='+document.all.user_Name.value+'&ShowSign=0&CanSave=1&moduleType=information&saveHtmlImage=0&saveDocFile=0&field=" + 
                field + "&FileType=.xls&showEditButton=1&showSignButton=1', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0')\\\" value='" + bjzw2017 + "' />" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 201:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + (
                (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
              break;
            } 
            html = 
              "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
              field + " name=" + 
              field + " value='" + fieldValue + "' " + (
              isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + 
              ">" + (
              fieldTemp[index][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "");
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 202:
            try {
              fieldValue = dbopt.executeQueryToStr("select EMPNAME from ORG_EMPLOYEE where EMP_ID=" + fieldValue);
            } catch (Exception exception) {}
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + ((fieldValue == null) ? "&nbsp" : 
                fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
              break;
            } 
            html = 
              "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
              field + " name=" + 
              field + " value='" + fieldValue + "'>" + (
              fieldTemp[index][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "");
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 203:
            styleValue = "";
            if (fieldValue == null || "".equals(fieldValue))
              fieldValue = "0"; 
            jisuanSet = "";
            if (fieldTemp[index][6].length() > 0) {
              String[] setStr = fieldTemp[index][6].substring(0, fieldTemp[index][6].length() - 1).split("\\]\\.\\[");
              if (setStr.length > 2) {
                if (setStr[3].equals("1") && !"".equals(setStr[2]) && !fieldValue.equals(""))
                  if (fieldValue.indexOf(".") >= 0) {
                    String endStr = fieldValue.substring(fieldValue.indexOf(".") + 1);
                    for (int m = 0; endStr.length() < Integer.valueOf(setStr[2]).intValue() && m < Integer.valueOf(setStr[2]).intValue() - endStr.length(); m++)
                      fieldValue = String.valueOf(fieldValue) + "0"; 
                  } else {
                    fieldValue = String.valueOf(fieldValue) + ".";
                    for (int m = 0; m < Integer.valueOf(setStr[2]).intValue(); m++)
                      fieldValue = String.valueOf(fieldValue) + "0"; 
                  }  
                if (fieldValue.endsWith("."))
                  fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
                jisuanSet = "<input type=\\'hidden\\' value=\\'" + setStr[2] + ":" + setStr[3] + "\\' name=\\'" + field + "_js\\' id=\\'" + field + "_js\\' />";
              } 
            } 
            if (isHide) {
              html = fieldValue;
              html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = String.valueOf(html) + "<input type=hidden name=" + field + "_cmp id=" + field + "_cmp value=" + fieldValue + ">";
              try {
                if (Double.valueOf(fieldValue).doubleValue() < 0.0D)
                  styleValue = "document.getElementsByName('" + 
                    fieldTemp[index][0] + "-" + field + "')[" + seq + "].style.color='red';"; 
              } catch (Exception err) {
                err.printStackTrace();
              } 
            } else {
              html = "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text name=" + 
                field + "_cmp id=" + 
                field + "_cmp class=flowInputRed onmouseover=setStyle(this) onmouseout=setStyle(this) value='" + 
                fieldValue + 
                "' readonly>";
              html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
            } 
            if (fieldTemp[index][2].equals("1000000") || fieldTemp[index][2].equals("1000001")) {
              type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=numbercompute>";
              isNum = true;
            } else {
              type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varcharcompute>";
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{" + (
              "".equals(styleValue) ? "" : styleValue) + 
              "document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML=\"" + html + type + jisuanSet + "\";}";
            break;
          case 204:
            if (isHide) {
              html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
            } else {
              html = 
                "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) value='" + 
                fieldValue + 
                "'>";
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML=\"" + html + type + "\";}";
            break;
          case 206:
            break;
          case 207:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + ((fieldValue == null) ? "&nbsp" : 
                fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
              break;
            } 
            html = 
              "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
              field + " name=" + 
              field + " value='" + fieldValue + "'>" + (
              fieldTemp[index][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "");
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 208:
          case 209:
            break;
          case 210:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = String.valueOf(html) + ((fieldValue != null && (fieldValue.split(";")).length > 1) ? fieldValue.split(";")[0] : "");
            } else {
              type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=personorg>";
              html = 
                "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + "_Name name=" + 
                field + "_Name readonly title=\\\"请点击选择\\\" value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[0] : "") + "\\\" onClick=\\\"openEndow('" + 
                field + "_Id','" + field + 
                "_Name',document.all." + field + 
                "_Id.value,document.all." + field + "_Name.value,'user','yes','user','*0*',this)\\\">";
              html = String.valueOf(html) + "<input type=hidden id=" + field + 
                "_Id name=" + 
                field + "_Id value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[1] : "") + 
                "\\\">" + (




                
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 211:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = String.valueOf(html) + ((fieldValue != null && (fieldValue.split(";")).length > 1) ? fieldValue.split(";")[0] : "");
            } else {
              type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=personorg>";
              html = 
                "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + "_Name name=" + 
                field + "_Name readonly title=\\\"请点击选择\\\" value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[0] : "") + "\\\" onClick=\\\"openEndow('" + 
                field + "_Id','" + field + 
                "_Name',document.all." + field + 
                "_Id.value,document.all." + field + "_Name.value,'user','no','user','*0*')\\\">";
              html = String.valueOf(html) + "<input type=hidden id=" + field + 
                "_Id name=" + 
                field + "_Id value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[1] : "") + 
                "\\\">" + (




                
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 212:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = String.valueOf(html) + ((fieldValue != null && (fieldValue.split(";")).length > 1) ? fieldValue.split(";")[0] : "");
            } else {
              type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=personorg>";
              html = 
                "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + "_Name name=" + 
                field + "_Name title=\\\"请点击选择\\\" value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[0] : "") + "\\\" onClick=\\\"formSelectSingleOrg(this,'" + 
                field + "_Id','" + field + 
                "_Name',document.all." + field + 
                "_Id.value,document.all." + field + "_Name.value,'org','yes','org','-100','formSingleOrgCallback')\\\" readonly=readonly />";
              html = String.valueOf(html) + "<input type=hidden id=" + field + 
                "_Id name=" + 
                field + "_Id value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[1] : "") + 
                "\\\">" + (




                
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 213:
            if (isHide) {
              html = fieldValue;
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
            } else {
              html = 
                "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + " name=" + 
                field + " value=\"" + fieldValue + "\" " + (
                isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + 
                ">" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
            break;
          case 214:
            if (isHide) {
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = String.valueOf(html) + ((fieldValue != null && (fieldValue.split(";")).length > 1) ? fieldValue.split(";")[0] : "");
            } else {
              type = "<input  type=hidden name=" + field + "_type id=" + field + "_type value=personorg>";
              html = 
                "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + "_Name name=" + 
                field + "_Name readonly title=\\\"请点击选择\\\" value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[0] : "") + "\\\" onClick=\\\"openEndow('" + 
                field + "_Id','" + field + 
                "_Name',document.all." + field + 
                "_Id.value,document.all." + field + "_Name.value,'org','no','org','*0*')\\\">";
              html = String.valueOf(html) + "<input type=hidden id=" + field + 
                "_Id name=" + 
                field + "_Id value=\\\"" + ((
                fieldValue != null && (
                fieldValue.split(";")).length > 1) ? 
                fieldValue.split(";")[1] : "") + 
                "\\\">" + (




                
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
            break;
          case 215:
            fieldValue = fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"");
            if ("".equals(fieldValue) || "null".equalsIgnoreCase(fieldValue))
              fieldValue = StaticParam.getEmpNumberByEmpId(request.getSession().getAttribute("userId").toString()); 
            if (hideField != null && (
              hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf(fieldTemp[0][5]) >= 0)) {
              if ("1".equals(fromDraft)) {
                html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "% ;font-size:1em; type=text class=flowInput id=" + 
                  field + " name=" + field + " value='" + fieldValue + "' readonly>";
              } else {
                html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
                type = "<input type=hidden id=" + field + "  name=" + field + " value=\"" + fieldValue + "\">";
              } 
            } else {
              html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput id=" + 
                field + " name=" + field + " value='" + fieldValue + "'>" + ((
                fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + "><label class=mustFillcolor>*</label>") : "");
            } 
            html = "\nif(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n" + 
              "{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';" + 
              "document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";}";
            break;
          case 301:
            onblur1 = "";
            if ("1".equals(isTotalField))
              onblur1 = "setSTotalValue(this);"; 
            if (computeFieldStr.indexOf(field) >= 0)
              onblur1 = String.valueOf(onblur1) + "setComputeForeignFieldNew(this);"; 
            if (isHide) {
              html = "<div name=moneyNum id=moneyNum>" + ((fieldValue == null || fieldValue.length() < 1) ? fieldValue : 
                NumberFormat.getInstance().format(Double.parseDouble(fieldValue))) + 
                "</div><div name=moneyChar id=moneyChar style=display:none>" + changeToBig(fieldValue) + "</div>";
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
            } else {
              html = 
                "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "% type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
                field + " name=" + 
                field + " value=\"" + fieldValue + "\" " + (
                isNum ? ("  onblur=checkNum(this);checkSize(this);" + onblur1) : " onblur=checkSize(this);") + 
                ">" + (
                fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
            break;
          case 302:
            html = "<label class=xuhao>" + (Integer.parseInt(seq) + 1) + "</label>";
            temp_302 = " <input type=hidden style=width:100%  name=" + field + " id=" + field + " value=\"" + (Integer.parseInt(seq) + 1) + "\" > ";
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + temp_302 + "';document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
            break;
          case 402:
            html = "<select style=font-size:1em; id=" + field + " name=" + field + "><option value=\"\">==请选择==</option>";
            temp = dbopt.executeQueryToStr("select field_value from tfield where field_id=" + fieldId);
            if (temp == null || temp.trim().length() < 1)
              break; 
            if (isHide) {
              html = "";
              if (temp.startsWith("@")) {
                String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + "_id," + 
                      table.substring(table.indexOf(".") + 1, table.length()) + " from " + table.substring(0, table.indexOf(".")));
                } catch (Exception exception) {}
                if (data != null)
                  for (int m = 0; m < data.length; m++)
                    html = String.valueOf(html) + ((fieldValue.indexOf(data[m][1]) >= 0) ? data[m][1] : "");  
              } 
              type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
              html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + html + type + "';}";
              break;
            } 
            if (temp.startsWith("@")) {
              String table = temp.substring(temp.indexOf("][") + 
                  2, temp.length() - 1);
              String[][] data = (String[][])null;
              try {
                data = dbopt.executeQueryToStrArr2("select " + 
                    table.substring(0, table.indexOf(".")) + 
                    "_id," + 
                    table.substring(table.indexOf(".") + 1, 
                      table.length()) + " from " + 
                    table.substring(0, table.indexOf(".")));
              } catch (Exception exception) {}
              if (data != null) {
                for (int m = 0; m < data.length; m++)
                  html = String.valueOf(html) + "<option value=" + data[m][1] + (
                    data[m][1].equals(fieldValue) ? 
                    " selected " : "") + ">" + 
                    data[m][1] + 
                    "</option>"; 
                html = "if(document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + 
                  "')[" + seq + "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + field + 
                  "')[" + seq + "].innerHTML='" + type + 
                  "';document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + "')[" + seq + "].innerHTML+='" + html + (
                  fieldTemp[index][3].equals("1") ? (
                  "<input type=hidden name=mustWrite id=mustWrite value=" + 
                  field + 
                  "><label class=mustFillcolor>*</label>") : 
                  "") + "';}";
              } 
            } 
            break;
          case 450:
            fieldValue = fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"");
            fieldValueTemp = "";
            if (fieldValue.length() > 0)
              if (fieldValue.indexOf("@@$@@") >= 0) {
                fieldValueTemp = fieldValue.substring(fieldValue.indexOf("@@$@@") + 5);
              } else {
                fieldValueTemp = fieldValue;
              }  
            if (isHide) {
              html = "<input type=hidden id=" + field + " name=" + field + " value=\"" + fieldValue + "\"><div style=\\\"float:left;width:60%;\\\"><input type=hidden id=" + field + "_temp name=" + field + "_temp value=\"" + fieldValueTemp + "\">" + fieldValueTemp + "</div>";
            } else {
              html = "<input type=hidden id=" + field + " name=" + field + " value=\"" + fieldValue + "\"><div style=\\\"float:left;width:60%;font-size:1em;\\\"><input type=text id=" + field + "_temp name=" + field + "_temp value=\"" + fieldValueTemp + "\" style=\\\"width:100%;font-size:1em;\\\"></div>";
              html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\"><input style=font-size:1em; type=button id=" + field + "_btn name=" + field + "_btn onclick=\"" + field + "_enter(this);\" value=检索></div>";
              html = String.valueOf(html) + (fieldTemp[index][3].equals("1") ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + field + ">" + 
                "<input type=hidden name=mustWrite id=mustWrite value=" + field + "_temp>" + 
                "<label class=mustFillcolor>*</label>") : "");
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
              "'))\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
            break;
          default:
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='';}";
            break;
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr) {
    String html = "";
    String temp = "";
    String[] tempArr = (String[])null;
    String type = "";
    Date now = new Date();
    DbOpt dbopt = null;
    boolean isNum = false;
    boolean isHide = false;
    try {
      dbopt = new DbOpt();
      if (fieldTemp != null && fieldTemp.length > 0 && 
        fieldTemp[index][1] != null && fieldTemp[index][1].trim().length() > 0) {
        try {
          if (fieldTemp[index][2].equals("1000001")) {
            fieldValue = fieldValue.toLowerCase();
            if (fieldValue.indexOf("e") > 0) {
              String value1 = fieldValue.substring(0, fieldValue.indexOf('e'));
              String value2 = fieldValue.substring(fieldValue.indexOf('e') + 1, fieldValue.length());
              double value11 = Double.parseDouble(value1);
              double value22 = Double.parseDouble(value2);
              double laterValue = value11 * Math.pow(10.0D, value22);
              fieldValue = DecimalFormat.getInstance().format(laterValue).replaceAll(",", "");
            } 
            temp = fieldTemp[index][6];
            if (fieldValue.length() > 0 && temp != null && temp.startsWith("[point")) {
              int pointNum = Integer.parseInt(temp.substring(temp.indexOf(":") + 1, temp.indexOf(";")));
              String fillin = temp.substring(temp.indexOf("fillin:") + 7, temp.indexOf("]"));
              NumberFormat nf = NumberFormat.getNumberInstance();
              nf.setMaximumFractionDigits(pointNum);
              fieldValue = nf.format(Double.parseDouble(fieldValue)).replaceAll(",", "");
              if ("1".equals(fillin))
                if (fieldValue.indexOf(".") < 0) {
                  fieldValue = String.valueOf(fieldValue) + ".";
                  for (int x = 0; x < pointNum; x++)
                    fieldValue = String.valueOf(fieldValue) + "0"; 
                } else {
                  int nowNum = fieldValue.length() - fieldValue.indexOf(".") - 1;
                  for (int x = nowNum; x < pointNum; x++)
                    fieldValue = String.valueOf(fieldValue) + "0"; 
                }  
            } else if (fieldValue.indexOf(".") > 0) {
              while (fieldValue.endsWith("0"))
                fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
              if (fieldValue.endsWith("."))
                fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        int maxlen = 9;
        if (!"".equals(fieldTemp[index][7]))
          maxlen = Integer.parseInt(fieldTemp[index][7]); 
        if (fieldTemp[index][2].equals("1000000") || fieldTemp[index][2].equals("1000001")) {
          type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=number>";
          isNum = true;
          if (fieldTemp[index][2].equals("1000001")) {
            maxlen = 18;
          } else {
            maxlen = 8;
          } 
        } else {
          type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varchar>";
        } 
        if (!fieldTemp[index][2].equals("1000003")) {
          type = String.valueOf(type) + "<input type=hidden name=" + field + "_size id=" + field + "_size value=" + maxlen + ">";
        } else {
          type = String.valueOf(type) + "<input type=hidden name=" + field + "_size id=" + field + "_size value=10000000>";
        } 
        if (hideField != null)
          if (hideField.toUpperCase().equals("ALL")) {
            isHide = true;
          } else {
            String hideFieldTmp = "," + hideField + ",";
            if (hideFieldTmp.indexOf("," + fieldTemp[index][0] + ",") >= 0 || hideFieldTmp.indexOf("," + fieldTemp[index][5] + ",") >= 0)
              isHide = true; 
          }  
        if (hideField.toUpperCase().equals("NONE"))
          isHide = false; 
        ElementHTMLGetter getter = ElementHTMLGetterFactory.getElementHTMLGetter(Integer.parseInt(fieldTemp[index][1]));
        if (getter != null) {
          html = getter.getForeignEditHTMLForWeiXin(request, 
              field, 
              fieldValue, 
              fieldTemp, 
              hideField, 
              index, 
              seq, 
              fieldId, 
              fromDraft, 
              foreignTableName, 
              parentRecordId, 
              curRecordId, 
              isTotalField, 
              computeFieldStr, 
              isHide, 
              type, 
              isNum, 
              maxlen, 
              temp, 
              tempArr);
        } else {
          html = "if(document.getElementById('" + fieldTemp[0][0] + 
            "-" + field + "'))\n{document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "').innerHTML='';}";
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return html;
  }
  
  public String getForeignComputeFieldHTML(String formId) {
    String computeFieldHTML = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String[][] computeField = dbopt.executeQueryToStrArr2("select distinct a.field_name,a.field_value from tfield a,ttable b,tarea d where field_show=203 and d.area_name<>'form1' and d.area_table=b.table_name and b.table_id=a.field_table and d.page_id=" + 
          
          formId);
      if (computeField != null) {
        String script = "\n<script language=javascript>function addCompute(){";
        for (int i = 0; i < computeField.length; i++) {
          String temp = computeField[i][1];
          if (temp.startsWith("["))
            temp = temp.substring(1, temp.indexOf("]")); 
          computeFieldHTML = String.valueOf(computeFieldHTML) + "<input type=hidden id=\"computeForeignField\" name=\"computeForeignField\" value=\"" + computeField[i][0] + 
            "\"/><input type=hidden id=\"computeForeignFieldValue\" name=\"computeForeignFieldValue\" value=\"" + temp + "\"/>";
          if (computeField[i][1].contains("].[")) {
            String valueString = computeField[i][1].substring(computeField[i][1].indexOf("].[") + 3, computeField[i][1].length() - 1);
            computeFieldHTML = String.valueOf(computeFieldHTML) + "<input type=hidden name=\"computeForeignFieldToUpper_" + computeField[i][0] + "\" id=\"computeForeignFieldToUpper_" + computeField[i][0] + 
              "\" value=\"" + valueString + "\"/>";
          } 
          if (temp != null && temp.length() > 0) {
            String fields = temp.replaceAll("\\+", ",").replaceAll("/", ",").replaceAll("\\*", ",").replaceAll("-", ",").replaceAll("\\(", ",").replaceAll("\\)", ",");
            String[] fieldArr = fields.split(",");
            if (fieldArr != null && fieldArr.length > 0)
              for (int j = 0; j < fieldArr.length; j++) {
                if (!"".equals(fieldArr[j]) && fieldArr[j].indexOf("_") >= 0)
                  script = String.valueOf(script) + "\nfor(var m=0;m<document.getElementsByName('" + fieldArr[j] + "').length;m++)" + 

                    
                    "{document.getElementsByName('" + fieldArr[j] + "')[m].attachEvent('onblur',new Function('" + 
                    "setComputeForeignField()'));}"; 
              }  
          } 
        } 
        script = String.valueOf(script) + "}</script>";
        computeFieldHTML = String.valueOf(computeFieldHTML) + script;
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return computeFieldHTML;
  }
  
  public String getMainAndForeignComputeFields(String formId) {
    String computeFieldHTML = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String[][] computeField = dbopt.executeQueryToStrArr2("select distinct a.field_name,a.field_value from tfield a,ttable b,tarea d where field_show=203 and d.area_table=b.table_name and b.table_id=a.field_table and d.page_id=" + 
          
          formId);
      if (computeField != null)
        for (int i = 0; i < computeField.length; i++) {
          String temp = computeField[i][1];
          if (temp.startsWith("["))
            temp = temp.substring(1, temp.indexOf("]")); 
          computeFieldHTML = String.valueOf(computeFieldHTML) + temp + ",";
        }  
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return computeFieldHTML;
  }
  
  public String getForeignAllComputeField(String formId) {
    String computeFieldHTML = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String[][] computeField = dbopt.executeQueryToStrArr2("select distinct a.field_name,a.field_value from tfield a,ttable b,tarea d where field_show=203 and d.area_name<>'form1' and d.area_table=b.table_name and b.table_id=a.field_table and d.page_id=" + 
          
          formId);
      if (computeField != null)
        for (int i = 0; i < computeField.length; i++)
          computeFieldHTML = String.valueOf(computeFieldHTML) + computeField[i][1] + ",";  
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } finally {}
    return computeFieldHTML;
  }
  
  public String[][] getTotalFieldByTableName(String pageId, String tablename, String domainId) {
    DbOpt dbopt = null;
    String[][] result = (String[][])null;
    try {
      dbopt = new DbOpt();
      String sql = "select table_id,table_totfield from ttable where table_name='" + tablename + "'";
      String totField = "";
      String tableId = "";
      ResultSet rs = dbopt.executeQuery(sql);
      if (rs.next()) {
        tableId = rs.getString(1);
        totField = rs.getString(2);
      } 
      rs.close();
      if (totField != null && !"".equals(totField) && !"null".equals(totField)) {
        String[] totFieldArr = totField.split(",");
        String where = "";
        for (int i = 0; i < totFieldArr.length; i++) {
          if (!"".equals(totFieldArr[i]))
            if ("".equals(where)) {
              where = String.valueOf(where) + "(elt_table='" + totFieldArr[i] + "'";
            } else {
              where = String.valueOf(where) + " or elt_table='" + totFieldArr[i] + "'";
            }  
        } 
        if (!"".equals(where)) {
          where = String.valueOf(where) + ")";
          totField = "";
          rs = dbopt.executeQuery("select elt_table from telt where page_id=" + pageId + " and " + where);
          while (rs.next()) {
            if ("".equals(totField)) {
              totField = "(field_name='" + rs.getString(1) + "'";
              continue;
            } 
            totField = String.valueOf(totField) + " or field_name='" + rs.getString(1) + "'";
          } 
          rs.close();
          if (!"".equals(totField)) {
            sql = "select e.field_name,e.field_desname,e.field_id,e.totalValue from tfield e where e.field_table=" + tableId + " and " + totField + ") order by field_sequence, field_id";
            result = dbopt.executeQueryToStrArr2(sql, 4);
          } 
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      logger.error("CustomFormBD error on getTotalFieldByTableName information:" + 
          e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String[][] getQueryFormByTblId(String domainId, String tableId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getQueryFormByTblId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error(
          "CustomFormBD error on getQueryFormByTblId information:" + 
          e.getMessage());
    } 
    return result;
  }
  
  public String[][] getQueryMainFormByTblId(String domainId, String tableId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getQueryMainFormByTblId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error(
          "CustomFormBD error on getQueryFormByTblId information:" + 
          e.getMessage());
    } 
    return result;
  }
  
  public String[][] getTableIDAndName(String pageId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getTableIDAndName", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getTableIDAndName information:" + 
          e.getMessage());
    } 
    return result;
  }
  
  public String[][] loadDataBySQL(String sql, String col) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(sql, String.class);
      pg.put(col, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("loadDataBySQL", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on loadDataBySQL information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String getComputeFieldHTML(String formId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(formId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = ejbProxy.invoke("getComputeFieldHTML", pg.getParameters())
        .toString();
    } catch (Exception e) {
      logger.error(
          "CustomFormBD error on getComputeFieldHTML information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String getAutoCode(String fieldName, String fieldId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(fieldName, String.class);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = ejbProxy.invoke("getAutoCode", pg.getParameters())
        .toString();
    } catch (Exception e) {
      logger.error(
          "CustomFormBD error on getComputeFieldHTML information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getCommentField(String formId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(formId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getCommentField", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("CustomFormBD error on getCommentField information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String getFieldShowValue(String field, String show, String fieldValue, String fieldId, HttpServletRequest request) {
    return (new CustomFormEJBBean()).getFieldShowValue(field, show, fieldValue, fieldId, request);
  }
  
  public String getFieldShowValue(String field, String show, String fieldValue, String fieldId) {
    String result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(field, String.class);
      pg.put(show, String.class);
      pg.put(fieldValue, String.class);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = ejbProxy.invoke("getFieldShowValue", pg.getParameters())
        .toString();
    } catch (Exception e) {
      logger.error("CustomFormBD error on getFieldShowValue information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String getComment(String fieldName, String recordId) {
    return (new CustomFormEJBBean()).getComment(fieldName, recordId);
  }
  
  public String getFieldShowValue(String field, String show, String fieldValue) {
    String result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(field, String.class);
      pg.put(show, String.class);
      pg.put(fieldValue, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = ejbProxy.invoke("getFieldShowValue", pg.getParameters())
        .toString();
    } catch (Exception e) {
      logger.error("CustomFormBD error on getFieldShowValue information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public void updateAutoCode(String fieldName, String currentCode, String fieldId) {
    IO2File.printFile("编号不存在，可以保存：" + currentCode, "自动编号");
    String codeAdd = "";
    DbOpt dbopt = null;
    String nextCode = "";
    try {
      dbopt = new DbOpt();
      String[][] codeValue = dbopt.executeQueryToStrArr2(
          "select field_value,field_codevalue from tfield where field_id=" + fieldId);
      if (codeValue != null && codeValue.length > 0) {
        String[] temp = (codeValue[0][0] == null) ? null : 
          codeValue[0][0].split("=");
        if (temp != null && temp.length > 0)
          codeAdd = temp[4]; 
        nextCode = currentCode.substring(currentCode.length() - Integer.parseInt(temp[3]), currentCode.length());
        while (nextCode.startsWith(temp[2]))
          nextCode = nextCode.replaceFirst(temp[2], ""); 
        if ("".equals(nextCode))
          nextCode = temp[1]; 
        dbopt.executeUpdate("update tfield set field_codevalue=" + nextCode + " where field_id=" + fieldId);
        dbopt.executeUpdate("update tfield set field_codevalue=field_codevalue+" + codeAdd + " where field_id=" + fieldId);
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
  }
  
  public String nextAutoCode(String fieldName, String currentCode, String fieldId) {
    String codeAdd = "";
    DbOpt dbopt = null;
    String nextCode = "";
    try {
      dbopt = new DbOpt();
      String[][] codeValue = dbopt.executeQueryToStrArr2(
          "select field_value,field_codevalue from tfield where field_id=" + fieldId);
      if (codeValue != null && codeValue.length > 0) {
        String[] temp = (codeValue[0][0] == null) ? null : 
          codeValue[0][0].split("=");
        if (temp != null && temp.length > 0)
          codeAdd = temp[4]; 
        nextCode = currentCode.substring(currentCode.length() - Integer.parseInt(temp[3]), currentCode.length());
        while (nextCode.startsWith(temp[2]))
          nextCode = nextCode.replaceFirst(temp[2], ""); 
        if (nextCode == null || nextCode.equals("") || nextCode.equals("null"))
          nextCode = temp[1]; 
        if (codeAdd == null || codeAdd.equals("") || codeAdd.equals("null"))
          codeAdd = "1"; 
        nextCode = String.valueOf(Integer.parseInt(nextCode) + Integer.parseInt(codeAdd));
        dbopt.executeUpdate("update tfield set field_codevalue=" + nextCode + " where field_id=" + fieldId);
        dbopt.executeUpdate("update tfield set field_codevalue=field_codevalue+" + codeAdd + " where field_id=" + fieldId);
        while (nextCode.length() < Integer.parseInt(temp[3]))
          nextCode = String.valueOf(temp[2]) + nextCode; 
        nextCode = String.valueOf(currentCode.substring(0, currentCode.length() - Integer.parseInt(temp[3]))) + nextCode;
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    IO2File.printFile("编号（" + currentCode + "）已存在，获得下一个编号（" + nextCode + "）", "自动编号");
    return nextCode;
  }
  
  private String changeToBig(String input) {
    String s1 = "零壹贰叁肆伍陆柒捌玖";
    String s4 = "分角整元拾佰仟万拾佰仟亿拾佰仟";
    String temp = "";
    String result = "";
    if (input == null || input.length() < 1)
      return ""; 
    if (input.charAt(0) == '-') {
      input = input.substring(1, input.length());
      result = "负";
    } 
    temp = input.trim();
    try {
      float f = Float.parseFloat(temp);
    } catch (Exception e) {
      return "";
    } 
    int len = 0;
    if (temp.indexOf(".") == -1) {
      len = temp.length();
    } else {
      len = temp.indexOf(".");
    } 
    if (len > s4.length() - 3)
      return ""; 
    int n2 = 0;
    String num = "";
    String unit = "";
    int add = 0;
    if (len > 7)
      return input; 
    if (len == 7) {
      add = 3;
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(0)));
      result = result.concat(s1.substring(n1, n1 + 1)).concat("佰");
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) == 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(2))) == 0)
        result = result.concat("万"); 
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) == 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(2))) != 0) {
        n1 = Integer.parseInt(String.valueOf(temp.charAt(2)));
        result = result.concat("零").concat(s1.substring(n1, n1 + 1))
          .concat("万");
      } 
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) != 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(2))) == 0) {
        n1 = Integer.parseInt(String.valueOf(temp.charAt(1)));
        result = result.concat(s1.substring(n1, n1 + 1)).concat("拾万");
      } 
      len = 4;
    } 
    if (len == 6) {
      add = 2;
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(0)));
      result = result.concat(s1.substring(n1, n1 + 1)).concat("拾");
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) == 0)
        result = result.concat("万"); 
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) != 0) {
        n1 = Integer.parseInt(String.valueOf(temp.charAt(1)));
        result = result.concat(s1.substring(n1, n1 + 1)).concat("万");
      } 
      len = 4;
    } 
    if (len == 5) {
      add = 1;
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(0)));
      result = result.concat(s1.substring(n1, n1 + 1)).concat("万");
      len = 4;
    } 
    int i;
    for (i = 0; i < len; i++) {
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(i + add)));
      num = s1.substring(n1, n1 + 1);
      if (n1 != 0) {
        n1 = len - i + 2;
        unit = s4.substring(n1, n1 + 1);
      } else {
        unit = "";
      } 
      if (i != 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(i - 1 + add))) == 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(i + add))) == 0) {
        num = "";
        unit = "";
      } 
      result = result.concat(num).concat(unit);
    } 
    while (result.endsWith("零") && result.length() > "零".length())
      result = result.substring(0, result.length() - "零".length()); 
    result = result.concat("元");
    if (temp.indexOf(".") == -1) {
      len = temp.length();
      result = result.concat("整");
    } else {
      len = temp.indexOf(".");
    } 
    if (len < temp.length()) {
      unit = "";
      for (i = 0; i < temp.length() - len - 1; i++) {
        int n1 = Integer.parseInt(String.valueOf(temp.charAt(i + len + 1)));
        num = s1.substring(n1, n1 + 1);
        if (n1 != 0) {
          if (i == 0) {
            unit = "角";
          } else if (i == 1) {
            unit = "分";
          } else {
            unit = "";
          } 
        } else {
          unit = "";
        } 
        result = result.concat(num).concat(unit);
      } 
    } 
    while (result.endsWith("零") && result.length() > "零".length())
      result = result.substring(0, result.length() - "零".length()); 
    result = result.replaceAll("元元", "元");
    return result;
  }
  
  public void updateForm() {
    DbOpt dbopt = new DbOpt();
    FormBD formBd = new FormBD();
    try {
      String[][] pageIds = dbopt.executeQueryToStrArr2("select PAGE_ID,PAGE_NAME from TPAGE where PRINT_PAGE_ID is null and PAGE_TYPE=0");
      if (pageIds != null)
        for (int i = 0; i < pageIds.length; i++) {
          List<TPagePO> list = formBd.getSingleForm(pageIds[i][0]);
          TPagePO pagePO = null;
          if (list != null && list.size() > 0) {
            pagePO = list.get(0);
            pagePO.setId(null);
            pagePO.setPageName(String.valueOf(pagePO.getPageName()) + "打印表单");
            Long printId = formBd.save(pagePO);
            dbopt.executeUpdate("update tpage set print_page_id=" + printId + " where page_id=" + pageIds[i][0]);
            AreaBD areaBD = new AreaBD();
            EltBD eltBD = new EltBD();
            while (pagePO.getTarea().iterator().hasNext()) {
              TAreaPO area = pagePO.getTarea().iterator().next();
              String areaId = area.getId().toString();
              Long newAreaId = areaBD.save(area, printId.toString(), "102");
              while (area.getTelt().iterator().hasNext()) {
                TEltPO elt = area.getTelt().iterator().next();
                eltBD.save(elt, printId.toString(), newAreaId.toString());
              } 
            } 
          } 
        }  
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (Exception exception) {}
      e.printStackTrace();
    } 
  }
  
  public boolean isNewForm(String parentFormId, String formId) {
    boolean res = true;
    DataSourceBase ds = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getDataSource().getConnection();
      stmt = conn.createStatement();
      String parentTableName = "", tableName = null;
      ResultSet rs = stmt.executeQuery("select area_table from tarea where page_id=" + parentFormId + " and area_name='form1'");
      if (rs.next())
        parentTableName = rs.getString(1); 
      rs.close();
      rs = stmt.executeQuery("select area_table from tarea where page_id=" + formId + " and area_name='form1'");
      if (rs.next())
        tableName = rs.getString(1); 
      rs.close();
      if (parentTableName.equals(tableName))
        res = false; 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (stmt != null)
        try {
          stmt.close();
        } catch (Exception er) {
          er.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res;
  }
  
  public String pasteForm(String formId, String domainId, String userId, String orgId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(formId, String.class);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      ejbProxy.invoke("pasteForm", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to pasteForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getPageName(String[] pageId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String)ejbProxy.invoke("getPageName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to pasteForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getCurStep(String workId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String)ejbProxy.invoke("getCurStep", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to pasteForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  private String[][] fieldIsFromOther(DbOpt dbopt, String tableName, String fieldId, String fieldName, String fieldValue, String parentRecordId, String curRecordId) {
    String[][] selectedValue = (String[][])null;
    try {
      String tableId = dbopt.executeQueryToStr("select table_id from ttable where table_name='" + tableName + "'");
      String[][] fieldPara = dbopt.executeQueryToStrArr2("select field_name,field_fetchsql,field_toffield from tfield where field_table=" + tableId + " and field_toffield like '%" + fieldName + "=:=%'", 3);
      if (fieldPara != null && fieldPara.length > 0) {
        String mainFieldName = fieldPara[0][0];
        String mainFieldFetchSql = fieldPara[0][1];
        String mainToFField = fieldPara[0][2];
        if (mainToFField != null && !"".equals(mainToFField)) {
          String mainValue = dbopt.executeQueryToStr("select " + mainFieldName + " from " + tableName + " where " + tableName + "_id=" + curRecordId);
          String[] mainToFFieldArr = mainToFField.split(";;;;");
          for (int i = 0; i < mainToFFieldArr.length; i++) {
            if (mainToFFieldArr[i].indexOf(String.valueOf(fieldName) + "=:=") >= 0) {
              String fetchField = mainToFFieldArr[i].substring(mainToFFieldArr[i].indexOf("=:=") + 3);
              if (fetchField.startsWith("[")) {
                fetchField = fetchField.substring(1, fetchField.length() - 1);
                fetchField = fetchField.replaceAll("\\@\\$\\@selValue\\@\\$\\@", mainValue);
                selectedValue = dbopt.executeQueryToStrArr2(fetchField, 2);
              } 
              break;
            } 
          } 
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return selectedValue;
  }
  
  public String[] getJSCode(String pageId) {
    String[] result = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[])ejbProxy.invoke("getJSCode", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to pasteForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getflowName(String flowId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(flowId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String)ejbProxy.invoke("getflowName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to pasteForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  private void saveRelationObject(String infoId, HttpServletRequest request, DbOpt dbopt) {
    String tableId = request.getParameter("Page_Id");
    String[] relationObjects = (request.getParameterValues("relation_object") == null) ? 
      new String[0] : request.getParameterValues("relation_object");
    String relationSubId = "0";
    String databaseType = DbOpt.dbtype;
    try {
      for (int i = 0; i < relationObjects.length; i++) {
        String fieldName = relationObjects[i];
        String relationType = request.getParameter(String.valueOf(fieldName) + "_relation_type");
        String[] relationValues = request.getParameterValues(String.valueOf(fieldName) + "_relation_data");
        String[] relationTitles = request.getParameterValues(String.valueOf(fieldName) + "_relation_title");
        for (int j = 0; relationValues != null && j < relationValues.length; j++) {
          String sql, temp = relationValues[j];
          relationSubId = temp.substring(0, temp.indexOf(";"));
          String relationInfoId = temp.substring(temp.indexOf(";") + 1);
          if (databaseType.indexOf("oracle") >= 0) {
            sql = "insert into oa_relationdata(dataid,moduletype,modulesubid,infoid,relationobjecttype,relationinfoname,relationsubid,relationinfoid,relationinfohref,domain_id,fieldName) values(hibernate_sequence.nextval,'jsflow'," + 
              tableId + "," + infoId + ",'" + relationType + "','" + relationTitles[j] + "'," + relationSubId + "," + relationInfoId + ",'',0,'" + fieldName + "')";
          } else {
            sql = "insert into oa_relationdata(moduletype,modulesubid,infoid,relationobjecttype,relationinfoname,relationsubid,relationinfoid,relationinfohref,domain_id,fieldName) values('jsflow'," + 
              tableId + "," + infoId + ",'" + relationType + "','" + relationTitles[j] + "'," + relationSubId + "," + relationInfoId + ",'',0,'" + fieldName + "')";
          } 
          dbopt.executeUpdate(sql);
        } 
      } 
    } catch (Exception err) {
      err.printStackTrace();
    } 
  }
  
  private void updateRelationObject(String infoId, HttpServletRequest request, DbOpt dbopt) {
    String tableId = request.getParameter("Page_Id");
    String[] relationObjects = (request.getParameterValues("relation_object") == null) ? 
      new String[0] : request.getParameterValues("relation_object");
    String relationSubId = "0";
    String databaseType = DbOpt.dbtype;
    try {
      for (int i = 0; i < relationObjects.length; i++) {
        String fieldName = relationObjects[i];
        String relationType = request.getParameter(String.valueOf(fieldName) + "_relation_type");
        String[] relationValues = request.getParameterValues(String.valueOf(fieldName) + "_relation_data");
        String[] relationTitles = request.getParameterValues(String.valueOf(fieldName) + "_relation_title");
        if (relationValues != null) {
          dbopt.executeUpdate("delete from oa_relationdata where moduletype='jsflow' and modulesubid=" + tableId + 
              " and infoid=" + infoId + " and relationobjecttype='" + relationType + "' and fieldName='" + fieldName + "'");
          for (int j = 0; j < relationValues.length; j++) {
            String sql, temp = relationValues[j];
            relationSubId = temp.substring(0, temp.indexOf(";"));
            String relationInfoId = temp.substring(temp.indexOf(";") + 1);
            if (databaseType.indexOf("oracle") >= 0) {
              sql = "insert into oa_relationdata(dataid,moduletype,modulesubid,infoid,relationobjecttype,relationinfoname,relationsubid,relationinfoid,relationinfohref,domain_id,fieldName) values(hibernate_sequence.nextval,'jsflow'," + 
                tableId + "," + infoId + ",'" + relationType + "','" + relationTitles[j] + "'," + relationSubId + "," + relationInfoId + ",'',0,'" + fieldName + "')";
            } else {
              sql = "insert into oa_relationdata(moduletype,modulesubid,infoid,relationobjecttype,relationinfoname,relationsubid,relationinfoid,relationinfohref,domain_id,fieldName) values('jsflow'," + 
                tableId + "," + infoId + ",'" + relationType + "','" + relationTitles[j] + "'," + relationSubId + "," + relationInfoId + ",'',0,'" + fieldName + "')";
            } 
            dbopt.executeUpdate(sql);
          } 
        } 
      } 
    } catch (Exception err) {
      err.printStackTrace();
    } 
  }
}
