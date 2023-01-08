package com.js.oa.form.hqzd;

import com.js.oa.form.Workflow;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class ExpenseService extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String json = getdata(recordId);
    System.out.println("费用报销单" + json);
    SignUtil ss = new SignUtil();
    String urltemp = "http://119.254.146.199:7080/cgi_bin/order/reimburse/add";
    String msg = ss.postData(json, urltemp);
    System.out.println("msg=" + msg);
    if (msg.indexOf("操作成功") < 0) {
      SystemCommon.setHqzdUpdata("true");
      ss.saveData("expense", recordId, tableId, msg);
    } else if (ss.getRows() == 0) {
      SystemCommon.setHqzdUpdata("");
    } 
    return result;
  }
  
  private String getdata(String recordId) {
    String sql = "SELECT jst_3013_f3161,jst_3013_f3504,jst_3013_f4898,jst_3013_f3164,jst_3013_f4904 FROM jst_3013 where jst_3013_id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt_body = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    Map<String, String> map = new HashMap<String, String>();
    JSONObject json = new JSONObject();
    List<JSONObject> bodyEntity = new ArrayList();
    String secret = "KjRn+#eazfK9jaQE";
    String billNo = "", date = "", itemDeptNo = "", itemDeptName = "", payeeNo = "", payeeName = "", pruductLineNo = "", remark = "";
    SignUtil ss = new SignUtil();
    String requestTime = ss.getRequestTime();
    String str = "";
    String md5data = "";
    double amount = 0.0D;
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        billNo = rs.getString(1);
        date = rs.getString(2);
        pruductLineNo = rs.getString(3);
        payeeNo = rs.getString(4).substring(rs.getString(4).indexOf(";") + 1);
        payeeName = rs.getString(4).substring(0, rs.getString(4).indexOf(";"));
        itemDeptNo = rs.getString(5).substring(rs.getString(5).indexOf(";") + 1);
        itemDeptName = rs.getString(5).substring(0, rs.getString(5).indexOf(";"));
        map.put("requestTime", requestTime);
        map.put("billNo", billNo);
        map.put("date", date);
        map.put("productLineNo", pruductLineNo);
        map.put("payeeNo", payeeNo);
        map.put("payeeName", payeeName);
        map.put("itemDeptNo", itemDeptNo);
        map.put("itemDeptName", itemDeptName);
        map.put("remark", remark);
        md5data = SignUtil.getSign(map, secret);
        json.put("requestTime", requestTime);
        json.put("sign", md5data);
        json.put("billNo", billNo);
        json.put("date", date);
        json.put("productLineNo", pruductLineNo);
        json.put("payeeNo", payeeNo);
        json.put("payeeName", payeeName);
        json.put("itemDeptNo", itemDeptNo);
        json.put("itemDeptName", itemDeptName);
        json.put("remark", remark);
      } 
      pstmt.close();
      rs.close();
      sql = "SELECT jst_3014_f3174,jst_3014_f3176 FROM jst_3014 WHERE jst_3014_FOREIGNKEY=?";
      pstmt_body = conn.prepareStatement(sql);
      pstmt_body.setString(1, recordId);
      rs = pstmt_body.executeQuery();
      String baseid = "";
      String basename = "";
      JSONObject jsonsub = new JSONObject();
      while (rs.next()) {
        str = getBaseData(rs.getString(1));
        if (!"".equals(str)) {
          baseid = str.substring(0, str.indexOf(";"));
          basename = str.substring(str.indexOf(";") + 1);
          jsonsub.put("itemNo", baseid);
          jsonsub.put("itemName", basename);
          amount = Double.valueOf(rs.getString(2)).doubleValue();
          jsonsub.put("amount", String.format("%.2f", new Object[] { Double.valueOf(amount) }));
          bodyEntity.add(jsonsub);
        } 
      } 
      json.put("bodyEntity", bodyEntity);
      pstmt_body.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return json.toString();
  }
  
  private String getBaseData(String basevalue) {
    String sql = "SELECT base_id,base_key FROM tbase WHERE base_parent = '119' and base_value=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String str = "";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, basevalue);
      rs = pstmt.executeQuery();
      while (rs.next())
        str = String.valueOf(rs.getString(1)) + ";" + rs.getString(2); 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return str;
  }
  
  public String getJsonData(String recordId) {
    return getdata(recordId);
  }
}
