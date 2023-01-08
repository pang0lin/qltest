package com.js.oa.form.hqzd;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import common.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class SignUtil {
  static Logger log = Logger.getLogger(SignUtil.class);
  
  public static String getSign(Map<String, String> params, String secret) {
    String[] keys = (String[])params.keySet().toArray((Object[])new String[params.size()]);
    Arrays.sort((Object[])keys);
    StringBuilder query = new StringBuilder();
    query.append(secret);
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = keys).length, b = 0; b < i; ) {
      String key = arrayOfString1[b];
      String value = params.get(key);
      if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value))
        query.append(key).append(value); 
      b++;
    } 
    query.append(secret);
    return strToMd5(query.toString(), "UTF-8").toUpperCase();
  }
  
  public static String strToMd5(String str, String charSet) {
    String md5Str = null;
    if (str != null && str.length() != 0)
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charSet));
        byte[] b = md.digest();
        StringBuffer buf = new StringBuffer("");
        byte b1;
        int i;
        byte[] arrayOfByte1;
        for (i = (arrayOfByte1 = b).length, b1 = 0; b1 < i; ) {
          byte element = arrayOfByte1[b1];
          int j = element;
          if (j < 0)
            j += 256; 
          if (j < 16)
            buf.append("0"); 
          buf.append(Integer.toHexString(j));
          b1++;
        } 
        md5Str = buf.toString();
      } catch (NoSuchAlgorithmException e) {
        log.error("MD5加密发生异常。加密串：" + str);
      } catch (UnsupportedEncodingException e) {
        log.error("MD5加密发生异常。加密串：" + str);
        e.printStackTrace();
      }  
    return md5Str;
  }
  
  public String postData(String json, String urltemp) {
    String result = "";
    String temp = "";
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    System.out.println("json:" + json);
    try {
      URL url = new URL(urltemp);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
      out.write(json);
      out.flush();
      out.close();
      InputStream in = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      while ((temp = reader.readLine()) != null)
        buffer.append(temp); 
      jsonObject = JSONObject.fromObject(buffer.toString());
      result = "state：" + jsonObject.get("state") + ",code：" + jsonObject.get("code") + ",msg:" + jsonObject.get("msg");
      in.close();
      reader.close();
      connection.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public void postData() {
    String sql = "select recordid,typeid,tableid from jst_hqzdupdata";
    DbOpt dbopt = null;
    DbOpt dbopt_delete = null;
    String recodrid = "";
    String typeid = "";
    String tableid = "";
    String msg = "";
    try {
      dbopt = new DbOpt();
      ResultSet rs = dbopt.executeQuery(sql);
      while (rs.next()) {
        recodrid = rs.getString(1);
        typeid = rs.getString(2);
        tableid = rs.getString(3);
        String urltemp = "http://119.254.146.199:7080/cgi_bin/order/reimburse/add";
        String json = "";
        if ("loan".equals(typeid)) {
          urltemp = "http://119.254.146.199:7080/cgi_bin/order/loan/add";
          LoanService loan = new LoanService();
          json = loan.getJsonData(recodrid);
        } else if ("reimburse".equals(typeid)) {
          ReimburseService reimbur = new ReimburseService();
          json = reimbur.getJsonData(recodrid);
        } else if ("expense".equals(typeid)) {
          ExpenseService expense = new ExpenseService();
          json = expense.getJsonData(recodrid);
        } else if ("business".equals(typeid)) {
          BusinessExpends business = new BusinessExpends();
          json = business.getJsonData(recodrid);
        } 
        msg = postData(json, urltemp);
        if (msg.indexOf("操作成功") < 0) {
          SystemCommon.setHqzdUpdata("true");
          saveData("loan", recodrid, tableid, msg);
          System.out.println("自动同步数据失败：" + typeid + ":" + recodrid);
          continue;
        } 
        String sql_delete = "delete from jst_hqzdupdata where recordid='" + recodrid + "' and typeid='" + typeid + "'";
        dbopt_delete = new DbOpt();
        dbopt_delete.executeUpdate(sql);
        if (getRows() == 0)
          SystemCommon.setHqzdUpdata(""); 
        System.out.println("自动同步数据成功：" + typeid + ":" + recodrid);
      } 
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public String getRequestTime() {
    Calendar nowtime = new GregorianCalendar();
    String year = String.format("%04d", new Object[] { Integer.valueOf(nowtime.get(1)) });
    String month = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(2) + 1) });
    String date = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(5)) });
    String hour = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(11)) });
    String minute = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(12)) });
    String second = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(13)) });
    String millisecond = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(14)) });
    String requestTime = String.valueOf(year) + month + date + hour + minute + second + millisecond;
    return requestTime;
  }
  
  public void saveData(String typeId, String recordId, String tableId, String msg) {
    String sql = "insert into jst_hqzdupdata (typeid,recordid,tableid,msg) values('" + typeId + "','" + recordId + "','" + tableId + "','" + msg + "')";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      dbopt.executeUpdate(sql);
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public int getRows() {
    int rows = 0;
    String rowCount = "select count(*) from jst_hqzdupdata";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      ResultSet rs = dbopt.executeQuery(rowCount);
      while (rs.next())
        rows = rs.getInt(1); 
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return rows;
  }
}
