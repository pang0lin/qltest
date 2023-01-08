package com.js.oa.jsflow.util;

import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FieldValue {
  public String specialField(String[] fieldValue, String value) {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stam = null;
    ResultSet rset = null;
    if (",103,104,105,210,211,212,214,".indexOf("," + fieldValue[1] + ",") >= 0) {
      String returnValue = "";
      try {
        if (",103,104,105,".indexOf("," + fieldValue[1] + ",") >= 0) {
          String sql = "";
          if (fieldValue[2].startsWith("$") || fieldValue[2].startsWith("@")) {
            try {
              if (fieldValue[2].indexOf("].$[") >= 0) {
                String database = fieldValue[2].substring(fieldValue[2].indexOf("$[") + 
                    2, fieldValue[2].indexOf("].$["));
                if ("system".equals(database)) {
                  conn = base.getDataSource().getConnection();
                } else {
                  conn = base.getDataSource(database).getConnection();
                } 
                sql = fieldValue[2].substring(fieldValue[2].indexOf("].$[") + 4, fieldValue[2].length() - 1);
              } else {
                conn = base.getDataSource().getConnection();
                if (fieldValue[2].startsWith("@")) {
                  sql = fieldValue[2].substring(fieldValue[2].indexOf("][") + 2, fieldValue[2].length() - 1);
                  String[] sqls = sql.split("\\.");
                  sql = "select " + sqls[0] + "_id," + sqls[1] + " from " + sqls[0];
                } else {
                  sql = fieldValue[2].substring(fieldValue[2].indexOf("$[") + 2, fieldValue[2].length() - 1);
                } 
              } 
              stam = conn.createStatement();
              rset = stam.executeQuery(sql);
              while (rset.next()) {
                if ("104".equals(fieldValue[1])) {
                  while (value.startsWith(","))
                    value = value.substring(1); 
                  while (value.endsWith(","))
                    value = value.substring(0, value.length() - 1); 
                  value = ";" + value.replaceAll(",", ";,;") + ";";
                  if (value.indexOf(";" + rset.getString(1) + ";") >= 0)
                    returnValue = value.replaceAll(";" + rset.getString(1) + ";", rset.getString(2)); 
                  continue;
                } 
                if (value.equals(rset.getString(1))) {
                  returnValue = rset.getString(2);
                  break;
                } 
              } 
              rset.close();
              stam.close();
              conn.close();
            } catch (Exception e) {
              if (stam != null)
                stam.close(); 
              if (conn != null)
                conn.close(); 
              e.printStackTrace();
            } 
          } else {
            String[] xiala = (String[])null;
            if (fieldValue[2].startsWith("*")) {
              String parentId = fieldValue[2].substring(fieldValue[2].indexOf(".*[") + 3, fieldValue[2].length() - 1);
              xiala = (new BaseSetEJBBean()).getValue(parentId).split(";");
            } else {
              xiala = fieldValue[2].split(";");
            } 
            for (int i = 0; i < xiala.length; i++) {
              String[] item = xiala[i].split("/");
              if ("104".equals(fieldValue[1])) {
                while (value.startsWith(","))
                  value = value.substring(1); 
                while (value.endsWith(","))
                  value = value.substring(0, value.length() - 1); 
                value = ";" + value.replaceAll(",", ";,;") + ";";
                if (value.indexOf(";" + item[0] + ";") >= 0)
                  returnValue = value.replaceAll(";" + item[0] + ";", item[1]); 
              } else if (value.equals(item[0])) {
                returnValue = item[1];
                break;
              } 
            } 
          } 
        } else if (",210,211,212,214,".indexOf("," + fieldValue[2] + ",") >= 0) {
          returnValue = value.split(";")[1];
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return returnValue;
    } 
    if ("199".equals(fieldValue[1]))
      return value.split("`~`~`")[0]; 
    if ("401".equals(fieldValue[1])) {
      String opinion = "";
      try {
        base.begin();
        String sql = "SELECT d.DEALWITHEMPLOYEECOMMENT,d.DEALWITHTIME,e.EMPNAME,d.commtype FROM jsf_dealwithcomment d JOIN org_employee e ON d.DEALWITHEMPLOYEE_ID=e.EMP_ID JOIN jsf_dealwith j ON j.WF_DEALWITH_ID = d.wf_dealwith_id WHERE j.DATABASERECORD_ID=" + 
          value + " AND d.commentfield='" + fieldValue[0] + "' ORDER BY d.WF_DEALWITHCOMMENT_ID";
        rset = base.executeQuery(sql);
        while (rset.next()) {
          if ("3".equals(rset.getString(4))) {
            opinion = String.valueOf(opinion) + "<div style=\"width:100%;font-size:12px;padding:3px;\" align=\"right\">(转办人)" + rset.getString(3) + "&nbsp;&nbsp;(" + 
              rset.getString(2).substring(0, 19) + ")&nbsp;</div>";
            continue;
          } 
          opinion = String.valueOf(opinion) + "<div style=\"width:100%;padding:3px;\">&nbsp;" + rset.getString(1) + "</div>";
          opinion = String.valueOf(opinion) + "<div style=\"width:100%;font-size:12px;padding:3px;\" align=\"right\">" + rset.getString(3) + "&nbsp;&nbsp;(" + 
            rset.getString(2).substring(0, 19) + ")&nbsp;</div>";
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
      return opinion;
    } 
    if ("115".equals(fieldValue[1])) {
      if (value.indexOf(",;") >= 0) {
        String[] fileNames = value.split(",;")[1].split(",");
        String fileName = "";
        for (int i = 0; i < fileNames.length; i++)
          fileName = String.valueOf(fileName) + "<div align=\"left\">" + fileNames[i] + "</div>"; 
        return fileName;
      } 
      return "";
    } 
    return value;
  }
  
  public String getOpinion(String recordId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    StringBuffer opinion = new StringBuffer("");
    try {
      base.begin();
      String sql = "SELECT d.DEALWITHEMPLOYEECOMMENT,d.DEALWITHTIME,e.EMPNAME,j.ACTIVITYNAME FROM jsf_dealwithcomment d JOIN org_employee e ON d.DEALWITHEMPLOYEE_ID=e.EMP_ID JOIN jsf_dealwith j ON j.WF_DEALWITH_ID = d.wf_dealwith_id WHERE j.DATABASERECORD_ID=" + 
        recordId + " AND d.commentfield='' ORDER BY d.WF_DEALWITHCOMMENT_ID";
      rs = base.executeQuery(sql);
      while (rs.next()) {
        opinion.append("<table width=\"100%\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"#dce0e1 1px solid;\">");
        opinion.append("<tr><td height=\"24\" style=\"border-bottom:1px solid #dce0e1; background:#f5f5f5;font-size:12px;\"><b>&nbsp;" + rs.getString(4) + "</b></td></tr>");
        opinion.append("<tr><td style=\"padding-top:8px;font-size:12px;\">&nbsp;" + rs.getString(1) + "</td></tr>");
        opinion.append("<tr><td height=\"20\" align=\"right\" style=\"font-size:12px;\">" + rs.getString(3) + "&nbsp;&nbsp;(" + rs.getString(2).substring(0, 16) + ")&nbsp;</td></tr>");
        opinion.append("</table><br />\n");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return opinion.toString();
  }
}
