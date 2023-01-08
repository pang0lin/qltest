package com.js.oa.eform.service;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CustFormWebSync {
  public String getHtmlFormByCustTable(String tableId, String colset) {
    if (tableId != null && tableId.length() > 0) {
      DbOpt opt = new DbOpt();
      try {
        String retCnt = opt.executeQueryToStr(
            " select count(*) from ttable where table_id = " + 
            Long.valueOf(tableId));
        if (retCnt != null && Integer.parseInt(retCnt) > 0) {
          Map[] fidsMap = opt.executeQueryToMaps(
              " select * from tfield where field_table = " + 
              Long.valueOf(tableId) + 
              " and field_list = 1 order by field_sequence ");
          if (fidsMap != null && fidsMap.length > 0) {
            boolean isTop = true;
            String HtmlStr = "<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><TABLE  style=\"border-left:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >";
            HtmlStr = String.valueOf(HtmlStr) + "<TR>";
            int colsett = Integer.parseInt(colset);
            String tEltStr = "";
            for (int i = 0; i < fidsMap.length; i++) {
              try {
                Map field = fidsMap[i];
                tEltStr = String.valueOf(tEltStr) + field.get(
                    "field_id").toString() + "-" + 
                  field.get(
                    "field_name").toString() + ";";
                if (!"1".equals(colset) && 
                  i % colsett == 0 && i >= colsett) {
                  HtmlStr = String.valueOf(HtmlStr) + "</TR><TR>";
                  isTop = false;
                } 
                String tmp = getControlHTML(field.get(
                      "field_show").toString(), field.get(
                      "field_desname").toString(), field.get(
                      "field_id").toString(), field.get(
                      "field_name").toString());
                String tdFreg = "";
                tdFreg = "<TD ";
                if ("110".equals(field.get("field_show")
                    .toString())) {
                  tdFreg = String.valueOf(tdFreg) + "height=100 ";
                } else if ("401".equals(field.get("field_show")
                    .toString())) {
                  tdFreg = String.valueOf(tdFreg) + "height=150 ";
                } 
                if (!isTop) {
                  HtmlStr = String.valueOf(HtmlStr) + 
                    "<TD nowrap bordercolor=\"#000000\" style=\"WIDTH:20%;border-TOP:1px solid #000000;border-right:1px solid #000000;\">" + 
                    field.get("field_desname") + 
                    "：</TD>";
                  if ((i + 1) % colsett == 0) {
                    HtmlStr = String.valueOf(HtmlStr) + tdFreg + 
                      "nowrap bordercolor=\"#000000\" style=\"border-TOP:1px solid #000000;\">" + 
                      tmp + "</TD>";
                  } else {
                    HtmlStr = String.valueOf(HtmlStr) + tdFreg + "nowrap bordercolor=\"#000000\" style=\"border-TOP:1px solid #000000;border-right:1px solid #000000;\">" + 
                      tmp + "</TD>";
                  } 
                } else {
                  HtmlStr = String.valueOf(HtmlStr) + tdFreg + 
                    "nowrap bordercolor=\"#000000\" style=\"WIDTH:20%;border-right:1px solid #000000;\">" + 
                    field.get("field_desname") + 
                    "：</TD>";
                  if ((i + 1) % colsett == 0) {
                    HtmlStr = String.valueOf(HtmlStr) + tdFreg + 
                      "nowrap bordercolor=\"#000000\" style=\"\">" + 
                      tmp + "</TD>";
                  } else {
                    HtmlStr = String.valueOf(HtmlStr) + tdFreg + 
                      "nowrap bordercolor=\"#000000\" style=\"border-right:1px solid #000000;\">" + 
                      tmp + "</TD>";
                  } 
                } 
                if (!"1".equals(colset)) {
                  if (i + 1 == fidsMap.length && (
                    i + 1) % colsett > 0)
                    for (int t = 1; t < colsett; t++) {
                      HtmlStr = String.valueOf(HtmlStr) + 
                        "<TD nowrap bordercolor=\"#000000\" style=\"WIDTH:20%;border-TOP:1px solid #000000;border-right:1px solid #000000;\">&nbsp;</TD>";
                      if ((i + 1) % colsett == 0) {
                        HtmlStr = String.valueOf(HtmlStr) + 
                          "<TD nowrap bordercolor=\"#000000\" style=\"border-TOP:1px solid #000000;\">&nbsp;</TD>";
                      } else {
                        HtmlStr = String.valueOf(HtmlStr) + 
                          "<TD nowrap bordercolor=\"#000000\" style=\"border-TOP:1px solid #000000;border-right:1px solid #000000;\">&nbsp;</TD>";
                      } 
                    }  
                } else {
                  HtmlStr = String.valueOf(HtmlStr) + "</TR><TR>";
                  isTop = false;
                } 
              } catch (Exception ex) {
                try {
                  opt.close();
                } catch (Exception ez) {
                  ez.printStackTrace();
                } 
                ex.printStackTrace();
              } 
            } 
            opt.close();
            return HtmlStr = String.valueOf(HtmlStr) + "</TABLE>|" + tEltStr;
          } 
          opt.close();
        } else {
          opt.close();
        } 
      } catch (Exception ex) {
        try {
          opt.close();
          ex.printStackTrace();
        } catch (Exception ey) {
          ey.printStackTrace();
        } 
      } 
    } 
    return null;
  }
  
  private String getControlHTML(String showID, String fieldDesName, String fieldTable, String fieldName) {
    String html = "";
    if (showID != null && showID.length() > 0) {
      Date dt = new Date();
      if ("101".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("111".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("102".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=password/><" + 
          fieldDesName + "></div>"; 
      if ("103".equals(showID))
        html = 
          "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=radio checked>选项一<input type=radio>选项二<input type=radio>选项三<" + 
          fieldDesName + "></div>"; 
      if ("104".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type='checkbox' checked >选项一<input type='checkbox'>选项二<input type='checkbox'>选项三<" + 
          fieldDesName + "></div>"; 
      if ("105".equals(showID))
        html = 
          "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><select><option>选项一</option><option>选项二</option><option>选项三</option></select><" + 
          fieldDesName + "></div>"; 
      if ("107".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text value='" + (
          dt.getYear() + 1900) + "-" + (
          dt.getMonth() + 1) + "-" + 
          dt.getDate() + "'/><" + fieldDesName + "></div>"; 
      if ("108".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text value='" + 
          dt.getHours() + ":" + 
          dt.getMinutes() + ":" + 
          dt.getSeconds() + "'/><" + fieldDesName + "></div>"; 
      if ("109".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text value='" + (
          dt.getYear() + 1900) + "-" + (
          dt.getMonth() + 1) + "-" + 
          dt.getDate() + " " + dt.getHours() + 
          ":" + dt.getMinutes() + "'/><" + fieldDesName + 
          "></div>"; 
      if ("110".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><textarea cols=50 rows=5></textarea><" + 
          fieldDesName + 
          "></div>"; 
      if ("113".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=hidden name=htmledit value=" + 
          fieldDesName + 
          "><iframe src='/jsoa/public/edit/ewebeditor.htm?id=htmledit&style=coolblue' frameborder=0 scrolling=no width=100% height=350></iframe></div>"; 
      if ("115".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><table width=80% border=0 bgcolor=#999999><tr bgcolor=#CCCCCC><td width=80% align=center>文件名</td><td width=20% align=center><input type=button value=添加></td></tr></table><" + 
          fieldDesName + "></div>"; 
      if ("116".equals(showID)) {
        String bjzw2017 = "编辑正文";
        if ("shandongguotou".equals(SystemCommon.getCustomerName()))
          bjzw2017 = "打开正文"; 
        html = "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><button class=btnButton4font>" + bjzw2017 + "</button><" + 
          fieldDesName + "></div>";
      } 
      if ("117".equals(showID)) {
        String bjzw2017 = "编辑正文";
        if ("shandongguotou".equals(SystemCommon.getCustomerName()))
          bjzw2017 = "打开正文"; 
        html = "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><button class=btnButton4font>" + bjzw2017 + "</button><" + 
          fieldDesName + "></div>";
      } 
      if ("201".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("202".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("204".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text value='" + (
          dt.getYear() + 1900) + "-" + (
          dt.getMonth() + 1) + "-" + 
          dt.getDate() + "'/><" + fieldDesName + "></div>"; 
      if ("207".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("208".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("210".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("211".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("212".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("213".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("214".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("301".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><input type=text/><" + 
          fieldDesName + "></div>"; 
      if ("401".equals(showID))
        html = "<div id=\"" + fieldTable + "-" + fieldName + 
          "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><textarea cols=50 rows=5></textarea><" + 
          fieldDesName + 
          "></div>"; 
      if ("402".equals(showID))
        html = 
          "<div id=\"" + fieldTable + "-" + fieldName + "\" style=\"WIDTH: 90%; HEIGHT: 98%\"><select><option>选项一</option><option>选项二</option><option>选项三</option></select><" + 
          fieldDesName + "></div>"; 
    } 
    return html;
  }
  
  public String getAllTextByValue(String values, String tblId, String txtField) {
    String ret = "";
    DbOpt opt = new DbOpt();
    try {
      String sql = " select " + 
        tblId + "_id, " + txtField + " from " + tblId + 
        " where " + 
        tblId + "_id in (" + 
        values.substring(0, values.lastIndexOf(",")) + ")";
      String[][] rets = opt.executeQueryToStrArr2(sql, 2);
      if (rets != null && rets.length > 0)
        for (int i = 0; i < rets.length; i++)
          ret = String.valueOf(ret) + rets[i][0] + "+" + rets[i][1] + "=";  
      opt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        opt.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return ret;
  }
  
  public List getFieldInfo(String tableId) {
    String[][] result = (String[][])null;
    List<String[][]> res = new ArrayList();
    DbOpt dbopt = null;
    try {
      String[] para = { tableId };
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr2PS("select field_id,field_desname,field_name,field_show,field_null,field_type from tfield where field_table=? order by field_id ", 6, para);
      if (result != null && result.length > 0) {
        res.add(result);
        result = (String[][])null;
      } 
      result = dbopt.executeQueryToStrArr2PS("select t.limit_prytable,b.table_name,t.limit_pryfield,t.limit_field,t.limit_type,b.table_desname from tlimit t,ttable a,ttable b  where a.table_id=? and t.limit_prytable=b.table_id and t.limit_table=a.table_id", 6, para);
      if (result != null && result.length > 0) {
        String[][] temp = (String[][])null;
        for (int i = 0; i < result.length; i++) {
          temp = dbopt.executeQueryToStrArr2("select field_id,field_desname,field_name,field_show,field_null,field_type,'" + result[i][1] + "' as TABLENM,'" + result[i][5] + "' as TABLEDESNM from tfield where field_table=" + result[i][0] + " order by field_id ", 8);
          if (temp != null && temp.length > 0) {
            res.add(temp);
            temp = (String[][])null;
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.print("CustFormWebSyc.java error on execute getFieldInfo function:");
      e.printStackTrace();
    } finally {}
    try {
      dbopt.close();
    } catch (Exception exception) {}
    return res;
  }
}
