package com.js.oa.module.action;

import com.js.oa.module.bean.ModuleMenuEJBBean;
import com.js.oa.module.service.ModuleMenuService;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ModuleExtendsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    ModuleMenuActionForm form = (ModuleMenuActionForm)actionForm;
    ModuleMenuService bd = new ModuleMenuService();
    String action = request.getParameter("action");
    if ("forGrant".equals(action))
      return actionMapping.findForward(action); 
    if ("grant".equals(action)) {
      String adjustEmps = request.getParameter("adjustEmps");
      String adjustEmpsName = request.getParameter("adjustEmpsName");
      String tableName = request.getParameter("tableStr");
      String ids = request.getParameter("ids");
      ids = ids.substring(0, ids.length() - 1);
      int ind = tableName.toLowerCase().indexOf(" left join");
      if (ind > 0)
        tableName = tableName.substring(0, ind); 
      boolean b = adjustRecord(tableName, ids, adjustEmps, adjustEmpsName, request);
      if (b) {
        request.setAttribute("adjustFlag", "1");
      } else {
        request.setAttribute("adjustFlag", "0");
      } 
      return actionMapping.findForward(action);
    } 
    if ("forGrantAll".equals(action))
      return actionMapping.findForward(action); 
    if ("grantAll".equals(action)) {
      String adjustEmps = request.getParameter("adjustEmps");
      String adjustToEmps = request.getParameter("adjustToEmps");
      String tableStr = request.getParameter("tableStr");
      int ind = tableStr.toLowerCase().indexOf(" left join");
      if (ind > 0)
        tableStr = tableStr.substring(0, ind); 
      String sql = "UPDATE " + tableStr + " t SET t." + tableStr + "_owner=" + adjustToEmps + " WHERE t." + tableStr + "_owner=" + adjustEmps;
      ModuleMenuEJBBean bean = new ModuleMenuEJBBean();
      boolean b = bean.updateByYourYuanShengSql(sql);
      if (b) {
        request.setAttribute("adjustFlag", "1");
      } else {
        request.setAttribute("adjustFlag", "0");
      } 
      return actionMapping.findForward(action);
    } 
    return null;
  }
  
  private boolean adjustRecord(String tableName, String ids, String toEmpId, String toEmpName, HttpServletRequest request) {
    boolean result = false;
    DataSourceBase dbase = null;
    Connection conn = null;
    try {
      dbase = new DataSourceBase();
      dbase.begin();
      ResultSet rs = dbase.executeQuery("select org_id from org_organization_user where emp_id=" + toEmpId);
      rs.next();
      String toEmpOrgId = rs.getString(1);
      rs.close();
      StringBuffer sql = (new StringBuffer("UPDATE ")).append(tableName).append(" t SET t.").append(tableName).append("_owner=").append(toEmpId);
      sql.append(",").append(tableName).append("_org=").append(toEmpOrgId);
      if (tableName.indexOf("jst_3282") >= 0) {
        sql.append(",jst_3282_f4338='").append(String.valueOf(toEmpName) + ";" + toEmpId + "' ");
      } else if (tableName.indexOf("jst_3294") >= 0) {
        sql.append(",jst_3294_f4556='").append(String.valueOf(toEmpName) + ";" + toEmpId + "' ");
      } 
      sql.append(" WHERE t.").append(tableName).append("_id IN (" + ids + ")");
      dbase.executeUpdate(sql.toString());
      String projectType = request.getParameter("projectType");
      String ownerId = "";
      String ownerOrgId = "";
      String ownerGroup = "";
      String custName = "";
      String custOASum = "";
      String custType = "";
      String custReqType = "";
      String custSupper = "";
      String linkManName = "";
      String linkManTitle = "";
      String linkManDepart = "";
      String linkManPhone = "";
      String linkManMobile = "";
      String linkManQQ = "";
      String infoDetail = "";
      String linkManMemo = "";
      String custArea = "";
      String pcNum = "";
      String webCond = "";
      String dataBase = "";
      String appSystem = "";
      String appSituation = "";
      String supplier = "";
      String ITNum = "";
      String techLevel = "";
      String oaExperience = "";
      String webSite = "";
      String linkEmail = "";
      String linkMsn = "";
      String custAddress = "";
      String custMemo = "";
      String custRelation = "";
      String custFrom = "";
      String infoPostDate = "";
      String pageId = "";
      String fzr = String.valueOf(toEmpName) + ";" + toEmpId;
      conn = (new DataSourceBase()).getDataSource().getConnection();
      if ("0".equals(projectType)) {
        if (tableName.indexOf("jst_3294") >= 0) {
          sql = sql.delete(0, sql.length());
          sql.append("insert into ");
          sql.append("jst_3282 (");
          sql.append("jst_3282_id,jst_3282_owner,jst_3282_org,jst_3282_group,");
          sql.append("jst_3282_f4339,jst_3282_f4340,jst_3282_f4342,jst_3282_f4343,jst_3282_f4450,jst_3282_f4352,");
          sql.append("jst_3282_f4365,jst_3282_f4353,jst_3282_f4355,jst_3282_f4354,jst_3282_f4356,jst_3282_f4541,");
          sql.append("jst_3282_f4903,jst_3282_f4341,");
          sql.append("jst_3282_f4536,");
          sql.append("jst_3282_f4357,jst_3282_f4937,jst_3282_f4370,jst_3282_f4338,");
          sql.append("jst_3282_f4369)");
          sql.append("values(");
          sql.append("?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,");
          sql.append("?,");
          sql.append("?,?,?,?,");
          sql.append("?)");
          PreparedStatement pstmt = conn.prepareStatement(sql.toString());
          String[] idArr = ids.split(",");
          for (int i = 0; i < idArr.length; i++) {
            sql = sql.delete(0, sql.length());
            sql.append("select ");
            sql.append("jst_3294_owner,jst_3294_org,jst_3294_group,");
            sql.append("jst_3282_f4345,jst_3282_f4352,jst_3282_f4365,jst_3282_f4353,jst_3282_f4355,jst_3282_f4354,");
            sql.append("jst_3282_f4356,jst_3282_f4341,jst_3294_f4537,jst_3282_f4357,jst_3294_f4938,jst_3294_f4555");
            sql.append(" from jst_3294 where jst_3294_id=").append(idArr[i]);
            rs = dbase.executeQuery(sql.toString());
            if (rs.next()) {
              ownerId = rs.getString(1);
              ownerOrgId = rs.getString(2);
              ownerGroup = rs.getString(3);
              custName = rs.getString(4);
              linkManName = rs.getString(5);
              linkManTitle = rs.getString(6);
              linkManDepart = rs.getString(7);
              linkManPhone = rs.getString(8);
              linkManMobile = rs.getString(9);
              linkManQQ = rs.getString(10);
              custArea = rs.getString(11);
              webSite = rs.getString(12);
              linkEmail = rs.getString(13);
              pageId = rs.getString(14);
              infoPostDate = rs.getString(15);
            } 
            rs.close();
            String recordId = dbase.getTableId();
            pstmt.setString(1, recordId);
            pstmt.setString(2, toEmpId);
            pstmt.setString(3, toEmpOrgId);
            pstmt.setString(4, "null");
            pstmt.setString(5, custName);
            pstmt.setString(6, "0");
            pstmt.setString(7, "");
            pstmt.setString(8, "");
            pstmt.setString(9, "");
            pstmt.setString(10, linkManName);
            pstmt.setString(11, linkManTitle);
            pstmt.setString(12, linkManDepart);
            pstmt.setString(13, linkManPhone);
            pstmt.setString(14, linkManMobile);
            pstmt.setString(15, linkManQQ);
            pstmt.setString(16, "");
            pstmt.setString(17, "");
            pstmt.setString(18, custArea);
            pstmt.setString(19, webSite);
            pstmt.setString(20, linkEmail);
            pstmt.setString(21, pageId);
            pstmt.setString(22, "0");
            pstmt.setString(23, String.valueOf(toEmpName) + ";" + toEmpId);
            pstmt.setString(24, infoPostDate);
            pstmt.execute();
          } 
          pstmt.close();
        } 
      } else if (tableName.indexOf("jst_3282") >= 0) {
        sql = sql.delete(0, sql.length());
        sql.append("insert into ");
        sql.append("jst_3294 (");
        sql.append("jst_3294_id,jst_3294_owner,jst_3294_org,jst_3294_group,");
        sql.append("jst_3282_f4345,jst_3282_f4352,jst_3282_f4365,jst_3282_f4353,jst_3282_f4355,jst_3282_f4354,");
        sql.append("jst_3282_f4356,jst_3282_f4341,jst_3294_f4537,jst_3282_f4357,jst_3294_f4938,jst_3294_f4554,");
        sql.append("jst_3294_f4556,jst_3294_f4555)");
        sql.append("values(");
        sql.append("?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?)");
        String[] idArr = ids.split(",");
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        for (int i = 0; i < idArr.length; i++) {
          sql = sql.delete(0, sql.length());
          sql.append("select ");
          sql.append("jst_3282_owner,jst_3282_org,jst_3282_group,");
          sql.append("jst_3282_f4339,jst_3282_f4352,");
          sql.append("jst_3282_f4365,jst_3282_f4353,jst_3282_f4355,jst_3282_f4354,jst_3282_f4356,jst_3282_f4541,");
          sql.append("jst_3282_f4903,jst_3282_f4341,");
          sql.append("jst_3282_f4536,");
          sql.append("jst_3282_f4357,jst_3282_f4937,jst_3282_f4370,");
          sql.append("jst_3282_f4337,jst_3282_f4369");
          sql.append(" from jst_3282 where jst_3282_id=").append(idArr[i]);
          rs = dbase.executeQuery(sql.toString());
          if (rs.next()) {
            ownerId = rs.getString(1);
            ownerOrgId = rs.getString(2);
            ownerGroup = rs.getString(3);
            custName = rs.getString(4);
            linkManName = rs.getString(5);
            linkManTitle = rs.getString(6);
            linkManDepart = rs.getString(7);
            linkManPhone = rs.getString(8);
            linkManMobile = rs.getString(9);
            linkManQQ = rs.getString(10);
            custMemo = rs.getString(11);
            linkManMemo = rs.getString(12);
            custArea = rs.getString(13);
            webSite = rs.getString(14);
            linkEmail = rs.getString(15);
            pageId = rs.getString(16);
            custFrom = rs.getString(17);
            custMemo = rs.getString(18);
            infoPostDate = rs.getString(19);
          } 
          rs.close();
          String recordId = dbase.getTableId();
          pstmt.setString(1, recordId);
          pstmt.setString(2, toEmpId);
          pstmt.setString(3, toEmpOrgId);
          pstmt.setString(4, "null");
          pstmt.setString(5, custName);
          pstmt.setString(6, linkManName);
          pstmt.setString(7, linkManTitle);
          pstmt.setString(8, linkManDepart);
          pstmt.setString(9, linkManPhone);
          pstmt.setString(10, linkManMobile);
          pstmt.setString(11, linkManQQ);
          pstmt.setString(12, custArea);
          pstmt.setString(13, webSite);
          pstmt.setString(14, linkEmail);
          pstmt.setString(15, pageId);
          pstmt.setString(16, custFrom);
          pstmt.setString(17, String.valueOf(toEmpName) + ";" + toEmpId);
          pstmt.setString(18, infoPostDate);
          pstmt.execute();
        } 
        pstmt.close();
      } 
      conn.close();
      dbase.end();
      result = true;
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      if (dbase != null)
        dbase.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
}
