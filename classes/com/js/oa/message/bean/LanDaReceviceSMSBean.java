package com.js.oa.message.bean;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import sms.MasSMS;

public class LanDaReceviceSMSBean {
  public boolean receviceSMS() {
    boolean flag = false;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      List<Map<String, String>> list = MasSMS.getReceivedSms();
      if (list != null && list.size() != 0) {
        int i = 0;
        for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext(); ) {
          Map<String, String> map = iterator.next();
          String extendCode = map.get("extendCode");
          String phone = map.get("phone");
          String msg = map.get("msg");
          if (extendCode != null && !"".equals(extendCode)) {
            StringBuffer sqlSb = new StringBuffer();
            sqlSb.append(" UPDATE MS_HISTORY A ");
            sqlSb.append(" SET ");
            sqlSb.append(" A.receiveContent ='").append(msg).append("' ");
            sqlSb.append(" WHERE ");
            sqlSb.append(" A.RECEIVECODE='").append(phone).append("' ");
            sqlSb.append(" AND ");
            sqlSb.append(" substr(A.extendCode,9,6)='").append(extendCode).append("' ");
            base.addBatch(sqlSb.toString());
            StringBuffer inB = new StringBuffer();
            inB.append("INSERT INTO tmp_ms_Log(MS_LOGID,PHONE,EXTENDCODE,MSG) ");
            inB.append("values(hibernate_sequence.nextval,");
            inB.append("'").append(phone).append("',");
            inB.append("'").append(extendCode).append("',");
            inB.append("'").append(msg).append("'");
            inB.append(") ");
            base.addBatch(inB.toString());
            i++;
          } 
        } 
        if (i != 0) {
          base.executeBatch();
          base.clearBatch();
        } 
        flag = true;
      } 
      base.end();
    } catch (Exception e) {
      flag = false;
      e.printStackTrace();
      base.end();
    } 
    List<Map<String, String>> telList = new ArrayList<Map<String, String>>();
    Connection conn = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select telreceive_id,telreceivenum,telcontent,telextend from ms_telreceive");
      while (rs.next()) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", rs.getString("telreceivenum"));
        map.put("msg", rs.getString("telcontent"));
        map.put("extendCode", rs.getString("telextend"));
        boolean selflag = telList.add(map);
        if (selflag) {
          String delsql = "delete from ms_telreceive where telreceive_id=" + rs.getString("telreceive_id");
          stmt.executeUpdate(delsql);
        } 
      } 
      rs.close();
      stmt.close();
      stmt = conn.createStatement();
      if (telList != null && telList.size() != 0) {
        int j = 0;
        for (Iterator<Map<String, String>> iterator = telList.iterator(); iterator.hasNext(); ) {
          Map<String, String> map = iterator.next();
          String telextendCode = map.get("extendCode");
          String telphone = map.get("phone");
          String telmsg = map.get("msg");
          if (telextendCode != null && !"".equals(telextendCode)) {
            StringBuffer sqlSb = new StringBuffer();
            sqlSb.append(" UPDATE MS_HISTORY A ");
            sqlSb.append(" SET ");
            sqlSb.append(" A.receiveContent ='").append(telmsg).append("' ");
            sqlSb.append(" WHERE ");
            sqlSb.append(" A.RECEIVECODE='").append(telphone).append("' ");
            sqlSb.append(" AND ");
            sqlSb.append("A.extendCode='").append(telextendCode).append("' ");
            stmt.addBatch(sqlSb.toString());
            StringBuffer inB = new StringBuffer();
            inB.append("INSERT INTO tmp_ms_Log(MS_LOGID,PHONE,EXTENDCODE,MSG) ");
            inB.append("values(hibernate_sequence.nextval,");
            inB.append("'").append(telphone).append("',");
            inB.append("'").append(telextendCode).append("',");
            inB.append("'").append(telmsg).append("'");
            inB.append(") ");
            stmt.addBatch(inB.toString());
            j++;
          } 
        } 
        if (j != 0)
          stmt.executeBatch(); 
        flag = true;
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      flag = false;
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
    return flag;
  }
}
