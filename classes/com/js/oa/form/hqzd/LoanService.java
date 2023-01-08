package com.js.oa.form.hqzd;

import com.js.oa.form.Workflow;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class LoanService extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    System.out.println("借款单");
    String json = getParamData(recordId);
    SignUtil ss = new SignUtil();
    String urltemp = "http://119.254.146.199:7080/cgi_bin/order/loan/add";
    String msg = ss.postData(json, urltemp);
    System.out.println("msg=" + msg);
    if (msg.indexOf("操作成功") < 0) {
      SystemCommon.setHqzdUpdata("true");
      ss.saveData("loan", recordId, tableId, msg);
    } else if (ss.getRows() == 0) {
      SystemCommon.setHqzdUpdata("");
    } 
    return result;
  }
  
  private String getParamData(String recordId) {
    SignUtil ss = new SignUtil();
    String sql = "SELECT jst_3004_f3071,jst_3004_f3070,jst_3358_f4899,jst_3004_f3507,jst_3004_f3075,jst_3004_f3072 FROM jst_3358 where jst_3358_id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    Map<String, String> map = new HashMap<String, String>();
    String info = "";
    String billNo = "", date = "", deptNo = "", deptName = "", payeeNo = "", payeeName = "", remark = "";
    double amount = 0.0D;
    String secret = "KjRn+#eazfK9jaQE";
    JSONObject json = new JSONObject();
    String requestTime = ss.getRequestTime();
    JSONObject jsonsub = new JSONObject();
    DecimalFormat df = new DecimalFormat("#.00");
    List<JSONObject> bodyEntity = new ArrayList();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        billNo = rs.getString(1);
        date = rs.getString(2);
        deptNo = rs.getString(3).substring(rs.getString(3).indexOf(";") + 1);
        deptName = rs.getString(3).substring(0, rs.getString(3).indexOf(";"));
        info = rs.getString(4);
        payeeName = info.substring(0, info.indexOf(";"));
        payeeNo = info.substring(info.indexOf(";") + 1);
        remark = rs.getString(5);
        amount = Double.valueOf(rs.getString(6)).doubleValue();
        jsonsub.put("amount", String.format("%.2f", new Object[] { Double.valueOf(amount) }));
        bodyEntity.add(jsonsub);
        map.put("requestTime", requestTime);
        map.put("billNo", billNo);
        map.put("date", date);
        map.put("deptNo", deptNo);
        map.put("deptName", deptName);
        map.put("payeeNo", payeeNo);
        map.put("payeeName", payeeName);
        map.put("remark", remark);
        String md5data = SignUtil.getSign(map, secret);
        json.put("requestTime", requestTime);
        json.put("sign", md5data);
        json.put("billNo", billNo);
        json.put("date", date);
        json.put("deptNo", deptNo);
        json.put("deptName", deptName);
        json.put("payeeNo", payeeNo);
        json.put("payeeName", payeeName);
        json.put("remark", remark);
        json.put("bodyEntity", bodyEntity);
      } 
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
    return json.toString();
  }
  
  public String getJsonData(String recordId) {
    return getParamData(recordId);
  }
}
