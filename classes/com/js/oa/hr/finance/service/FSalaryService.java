package com.js.oa.hr.finance.service;

import com.js.oa.hr.finance.bean.FFieldEJBBean;
import com.js.oa.hr.finance.bean.FPayableEJBBean;
import com.js.oa.hr.finance.bean.FSalaryEJBBean;
import com.js.oa.hr.finance.bean.FTableEJBBean;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ExcelOperate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;

public class FSalaryService {
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  public Map importSalary(HttpServletRequest httpServletRequest) throws FileNotFoundException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String message = "";
    String succeed = "0";
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowStr = dateFormat.format(new Date());
    String realPath = httpServletRequest
      .getRealPath("/uploadtemplate/fsalary.xls");
    FileInputStream file = new FileInputStream(new File(realPath));
    try {
      this.workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      message = String.valueOf(message) + "选择的模版不正确！<br>";
      if (!"1".equals(succeed))
        succeed = "1"; 
      map.put("succeed", succeed);
      map.put("message", message);
      return map;
    } 
    this.sheet = this.workbook.getSheet(0);
    if (this.sheet != null) {
      int rows = this.sheet.getRows();
      int columns = this.sheet.getColumns();
      try {
        String duiYinWiZhi = this.sheet.getCell(0, 1).getContents().trim();
        String duiYinWiZhiValue = this.sheet.getCell(1, 1).getContents().trim();
        String databaseType = SystemCommon.getDatabaseType();
        FPayableEJBBean fPayableEJBBean = new FPayableEJBBean();
        if ("对应字段所在列".equals(duiYinWiZhi) && duiYinWiZhiValue.matches("^[0-9]*[1-9][0-9]*$")) {
          Map<Object, Object> userAccountMap = new HashMap<Object, Object>();
          String userAccountSql = "";
          String snSql = "";
          Map<Object, Object> defineOneMap = new HashMap<Object, Object>();
          List<Object[]> tempList = null;
          Map<Object, Object> tempMap = null;
          for (int i = 0; i < columns; i++) {
            if (i != Long.valueOf(duiYinWiZhiValue).longValue() - 1L) {
              String textName = this.sheet.getCell(i, 2).getContents().trim();
              String sql = "select po.fieldName,po.fieldType,po.fieldLen from com.js.oa.hr.finance.po.FField po where po.tableName='f_salary' and po.fieldDesname='" + 
                textName + "'";
              tempList = fPayableEJBBean.getListByYourSQL(sql);
              if (tempList != null && tempList.size() > 0) {
                Object[] obj = tempList.get(0);
                tempMap = new HashMap<Object, Object>();
                tempMap.put("fieldName", obj[0]);
                tempMap.put("fieldType", obj[1]);
                tempMap.put("fieldLen", obj[2]);
                tempMap.put("fieldDesname", textName);
                defineOneMap.put("column_" + i, tempMap);
              } else {
                message = String.valueOf(message) + "第3行第" + (i + 1) + "列“" + textName + "”不存在，请先在表中新建！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
            } 
          } 
          if (!"1".equals(succeed)) {
            DataSourceBase dataSourceBase = new DataSourceBase();
            for (int j = 4; j < rows; j++) {
              String content = "";
              StringBuffer insertParamSql = new StringBuffer("insert into  f_salary(");
              StringBuffer insertValueSql = new StringBuffer("VALUES(");
              Long maxId = fPayableEJBBean.getTableIdB();
              insertParamSql.append("id ,");
              insertValueSql.append(maxId).append(",");
              String forresField = this.sheet.getCell(Long.valueOf(duiYinWiZhiValue).intValue() - 1, j).getContents().trim();
              if (forresField == null || "".equals(forresField)) {
                message = String.valueOf(message) + "第" + (j + 1) + "行对应字段不能为空！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } else {
                tempMap = fPayableEJBBean.getEmpIdEmpNameEmpNumberOrgIdOrgNameByCorresField(forresField);
                if (tempMap == null) {
                  message = String.valueOf(message) + "第" + (j + 1) + "行对应字段不存在！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else {
                  insertParamSql.append("user_accounts,emp_id,emp_name,emp_number,org_id,org_name,");
                  insertValueSql.append("'").append(tempMap.get("userAccounts").toString()).append("',");
                  insertValueSql.append("'").append(tempMap.get("empId").toString()).append("',");
                  insertValueSql.append("'").append(tempMap.get("empName").toString()).append("',");
                  insertValueSql.append("'").append((tempMap.get("empNumber") == null) ? "" : tempMap.get("empNumber").toString()).append("',");
                  insertValueSql.append("'").append(tempMap.get("orgId").toString()).append("',");
                  insertValueSql.append("'").append(tempMap.get("orgName").toString()).append("',");
                } 
              } 
              String[][] xx = ExcelOperate.getData(new File(realPath), 4);
              for (int k = 0; k <= columns; k++) {
                if (j != Long.valueOf(duiYinWiZhiValue).longValue() - 1L) {
                  tempMap = (defineOneMap.get("column_" + k) == null) ? null : (HashMap)defineOneMap.get("column_" + k);
                  if (tempMap != null) {
                    String textValue = xx[j][k];
                    String fieldName = tempMap.get("fieldName").toString();
                    String fieldType = tempMap.get("fieldType").toString();
                    String fieldLen = tempMap.get("fieldLen").toString();
                    String fieldDesname = tempMap.get("fieldDesname").toString();
                    if (fieldType.contains("varchar")) {
                      if (textValue.length() > Integer.valueOf(fieldLen).intValue()) {
                        message = String.valueOf(message) + "第" + (j + 1) + "行" + fieldDesname + "长度不得超过" + fieldLen + "！<br>";
                        if (!"1".equals(succeed))
                          succeed = "1"; 
                        break;
                      } 
                      insertParamSql.append(fieldName).append(",");
                      insertValueSql.append("'").append(textValue.replace(".00", "")).append("',");
                    } else {
                      if (!textValue.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
                        message = String.valueOf(message) + "第" + (j + 1) + "行" + fieldDesname + "应当是数字！<br>";
                        if (!"1".equals(succeed)) {
                          succeed = "1";
                          break;
                        } 
                      } 
                      if (textValue != null && !"".equals(textValue)) {
                        double textValueStr = Double.parseDouble(textValue);
                        DecimalFormat df = new DecimalFormat("0.00");
                        textValue = df.format(textValueStr);
                      } 
                      insertParamSql.append(fieldName).append(",");
                      insertValueSql.append("'").append(textValue).append("',");
                    } 
                  } 
                } 
              } 
              insertParamSql.append("created_date").append(",")
                .append("created_emp").append(",")
                .append("created_org").append(",");
              if (databaseType.indexOf("oracle") >= 0) {
                insertValueSql.append("to_date('" + nowStr + "','yyyy-MM-dd HH24:mi:ss')").append(",")
                  .append("'").append(userId).append("',")
                  .append("'").append(orgId).append("',");
              } else {
                insertValueSql.append("'").append(nowStr).append("',")
                  .append("'").append(userId).append("',")
                  .append("'").append(orgId).append("',");
              } 
              String sqlTemp = "select po.tableName,po.tablePage from com.js.oa.hr.finance.po.FTable po where po.tableName='f_salary'";
              tempList = fPayableEJBBean.getListByYourSQL(sqlTemp);
              if (tempList != null && tempList.size() > 0) {
                Object[] obj = tempList.get(0);
                if (obj[1] != null && !"".equals(obj[1])) {
                  insertParamSql.append("page_id").append(",");
                  insertValueSql.append(obj[1].toString()).append(",");
                } else {
                  message = String.valueOf(message) + "请先给予表配置好表单！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
              } else {
                message = String.valueOf(message) + "请先给予表配置好表单！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              if (!"1".equals(succeed) && 
                insertParamSql != null && !"".equals(insertParamSql)) {
                String strParam = String.valueOf(insertParamSql.substring(0, insertParamSql.length() - 1)) + ")";
                String strValue = String.valueOf(insertValueSql.substring(0, insertValueSql.length() - 1)) + ")";
                String sql = String.valueOf(strParam) + strValue;
                fPayableEJBBean.updateByYourYuanShengSql(sql);
              } 
            } 
          } 
        } else {
          if (!"1".equals(succeed))
            succeed = "1"; 
          message = String.valueOf(message) + "选择的模版不正确！<br>";
        } 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    map.put("succeed", succeed);
    map.put("message", message);
    return map;
  }
  
  public void list(HttpServletRequest request) throws Exception {
    FFieldEJBBean bean = new FFieldEJBBean();
    String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType from com.js.oa.hr.finance.po.FField po  where po.tableName='f_salary' and po.fieldIsSys=0 and po.fieldListShow=1 order by po.fieldOrder asc";
    List<Object[]> tempList = bean.getListByYourSQL(sql);
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    int recordCount = 0;
    String[][] datas = (String[][])null;
    String paras = "";
    if (tempList != null && tempList.size() > 0) {
      Page page_ol = null;
      StringBuffer sb = new StringBuffer("select id");
      for (int i = 0; i < tempList.size(); i++) {
        Object[] obj = tempList.get(i);
        sb.append(",").append(obj[1].toString());
      } 
      sb.append(",created_date,page_id");
      sb.append(" from f_salary where 1=1");
      String where = "";
      String empName = request.getParameter("empName");
      if (empName != null && !"".equals(empName))
        where = String.valueOf(where) + " and emp_name like '%" + empName + "%'"; 
      String orgName = request.getParameter("orgName");
      if (orgName != null && !"".equals(orgName))
        where = String.valueOf(where) + " and org_name like '%" + orgName + "%'"; 
      String searchTime = request.getParameter("searchTime");
      String oprStartTime = request.getParameter("oprStartTime");
      String oprEndTime = request.getParameter("oprEndTime");
      String databaseType = SystemCommon.getDatabaseType();
      if (searchTime != null && "1".equals(searchTime)) {
        if (oprStartTime != null && !"".equals(oprStartTime)) {
          if (oprStartTime.length() < 11)
            oprStartTime = String.valueOf(oprStartTime) + " 00:00:00"; 
          if ("oracle".equals(databaseType)) {
            where = String.valueOf(where) + " and created_date >=to_date('" + dateFormart(oprStartTime, "yyyy/MM/dd HH:mm:ss") + "','yyyy-MM-dd HH24:mi:ss')";
          } else {
            where = String.valueOf(where) + " and created_date >='" + dateFormart(oprStartTime, "yyyy/MM/dd HH:mm:ss") + "'";
          } 
        } 
        if (oprEndTime != null && !"".equals(oprEndTime)) {
          if (oprEndTime.length() < 11)
            oprEndTime = String.valueOf(oprEndTime) + " 23:59:59"; 
          if ("oracle".equals(databaseType)) {
            where = String.valueOf(where) + " and created_date <=to_date('" + dateFormart(oprEndTime, "yyyy/MM/dd HH:mm:ss") + "','yyyy-MM-dd HH24:mi:ss')";
          } else {
            where = String.valueOf(where) + " and created_date <='" + dateFormart(oprEndTime, "yyyy/MM/dd HH:mm:ss") + "'";
          } 
        } 
      } 
      String rightName = "07*55*10";
      String whereTmp = (new FTableEJBBean()).getRightWhere(session.getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          rightName, 
          "org_id", 
          "emp_id");
      if (whereTmp.equals("") || whereTmp.equals("(1>2)") || whereTmp.equals("(1>1)")) {
        where = String.valueOf(where) + " and emp_id=" + userId;
        request.setAttribute("canEdit", "0");
      } else {
        where = String.valueOf(where) + " and " + whereTmp;
        request.setAttribute("canEdit", "1");
      } 
      where = String.valueOf(where) + " order by created_date desc,id ";
      sb.append(where);
      recordCount = Integer.valueOf(bean.getCountByYourSql(sb.toString())).intValue();
      String tempSql = "";
      if ("oracle".equals(databaseType)) {
        tempSql = "select wkk.*,rownum as rn  from (" + sb + ") wkk ORDER BY wkk.created_date desc,wkk.id ";
      } else {
        tempSql = "select wkk.*,'rn' from (" + sb + ") wkk ORDER BY wkk.created_date desc,wkk.id ";
      } 
      String reSql = "select * from (" + tempSql + ")";
      if ("oracle".equals(databaseType)) {
        reSql = String.valueOf(reSql) + " where rn >=" + ((currentPage - 1) * pageSize + 1) + 
          "and rn <=" + (currentPage * pageSize);
        reSql = String.valueOf(reSql) + " ORDER BY created_date DESC,id";
      } else {
        reSql = String.valueOf(reSql) + "ttt limit " + ((currentPage - 1) * pageSize) + "," + pageSize;
      } 
      datas = bean.getArr2ByYourSql(reSql, tempList.size() + 3, "");
    } 
    request.setAttribute("showList", tempList);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", "action,salaryType");
    request.setAttribute("datas", datas);
  }
  
  public void forUpdate(HttpServletRequest request) {
    FFieldEJBBean bean = new FFieldEJBBean();
    String id = request.getParameter("id");
    request.setAttribute("id", id);
    String table = "f_salary";
    String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType,po.fieldLen from com.js.oa.hr.finance.po.FField po  where po.tableName='f_salary' and po.fieldIsSys=0  order by po.fieldId asc";
    try {
      List<Object[]> tempList = bean.getListByYourSQL(sql);
      if (tempList != null && tempList.size() > 0) {
        Page page_ol = null;
        StringBuffer sb = new StringBuffer("select ");
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (i == 0) {
            sb.append(obj[1].toString());
          } else {
            sb.append(",").append(obj[1].toString());
          } 
        } 
        sb.append(" from f_salary where 1=1 and id=" + id);
        String[][] datas = (String[][])null;
        datas = bean.getArr2ByYourSql(sb.toString(), tempList.size(), "");
        StringBuffer tableInfo = new StringBuffer("");
        if (datas != null && datas.length == 1) {
          double trCount = Math.ceil((datas[0]).length / 2.0D);
          for (int j = 0; j < trCount; j++) {
            Object[] obj = (Object[])null;
            boolean b = ((j + 1) * 2 <= (datas[0]).length);
            if ((j + 1) * 2 <= (datas[0]).length) {
              tableInfo.append("<tr>");
              for (int w = 0; w < 2; w++) {
                obj = tempList.get(j * 2 + w);
                tableInfo.append("<td width=\"160\">").append(obj[2]).append("：</td>");
                tableInfo.append("<td  valign=\"middle\">");
                tableInfo.append("<input type=\"text\" id='").append(obj[1]).append("' name='").append(obj[1]).append("'");
                tableInfo.append(" value='").append(datas[0][j * 2 + w]).append("' ");
                if ("double".equals(obj[3]) || "float".equals(obj[3])) {
                  tableInfo.append(" onblur=checkNum(this) ");
                } else {
                  tableInfo.append(" maxlength='").append(obj[4]).append("' ");
                } 
                tableInfo.append(" style=\"width:80%\"/>");
                tableInfo.append("</td>");
              } 
              tableInfo.append("</tr>");
            } else {
              tableInfo.append("<tr>");
              obj = tempList.get(j * 2);
              tableInfo.append("<td width=\"160\">").append(obj[2]).append("：</td>");
              tableInfo.append("<td  valign=\"middle\" >");
              tableInfo.append("<input type=\"text\" id='").append(obj[1]).append("' name='").append(obj[1]).append("'");
              tableInfo.append(" value='").append(datas[0][j * 2]).append("' ");
              if ("double".equals(obj[3]) || "float".equals(obj[3])) {
                tableInfo.append(" onblur=checkNum(this) ");
              } else {
                tableInfo.append(" maxlength='").append(obj[4]).append("' ");
              } 
              tableInfo.append(" style=\"width:80%\"/>");
              tableInfo.append("</td>");
              tableInfo.append("<td width=\"160\"> &nbsp;</td>");
              tableInfo.append("<td  valign=\"middle\" >&nbsp;");
              tableInfo.append("</td>");
              tableInfo.append("</tr>");
            } 
          } 
        } 
        request.setAttribute("tableInfo", tableInfo);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean modi(HttpServletRequest request) {
    boolean re = true;
    FFieldEJBBean bean = new FFieldEJBBean();
    String id = request.getParameter("id");
    request.setAttribute("id", id);
    String table = "f_salary";
    String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType,po.fieldLen from com.js.oa.hr.finance.po.FField po  where po.tableName='f_salary' and po.fieldIsSys=0  order by po.fieldId asc";
    try {
      List<Object[]> tempList = bean.getListByYourSQL(sql);
      if (tempList != null && tempList.size() > 0) {
        Page page_ol = null;
        StringBuffer sb = new StringBuffer("update " + table + " set id=" + id);
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (request.getParameter(obj[1].toString()) != null)
            sb.append("," + obj[1].toString() + "='" + request.getParameter(obj[1].toString()) + "'"); 
        } 
        sb.append("  where 1=1 and id=" + id);
        re = bean.updateByYourYuanShengSql(sb.toString());
      } 
    } catch (Exception e) {
      re = false;
      e.printStackTrace();
    } 
    return re;
  }
  
  public boolean delete(String ids, String deleteFlag) {
    boolean re = true;
    FSalaryEJBBean fPayableEJBBean = new FSalaryEJBBean();
    re = fPayableEJBBean.delete(ids, deleteFlag);
    return re;
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
