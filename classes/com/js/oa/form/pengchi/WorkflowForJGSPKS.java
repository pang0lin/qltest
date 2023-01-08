package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForJGSPKS extends Workflow {
  public String complete(HttpServletRequest request) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT jst_3444_id,gysdzh,fbcs,gsmc,gys,xmh,jldw,gg,hbm,hsdj,dwcb,CONCAT(YEAR(str_to_date(IFNULL(sxz,now()),'%Y-%m-%d'))-1900,DATE_FORMAT(str_to_date(IFNULL(sxz,now()),'%Y-%m-%d'),'%j')) as sxz,");
    sql.append("CONCAT(YEAR(str_to_date(IFNULL(yxz,'2040-12-31'),'%Y-%m-%d'))-1900,DATE_FORMAT(str_to_date(IFNULL(yxz,'2040-12-31'),'%Y-%m-%d'),'%j')) as yxz,bz,");
    sql.append("CONCAT(YEAR(now())-1900,DATE_FORMAT(now(),'%j')) as today,DATE_FORMAT(NOW(),'%H%i%s') as time");
    sql.append(" from jst_3445,jst_3444 where jst_3445_id=jst_3444_foreignkey AND jst_3445_id=?");
    System.out.println("昆山-价格审批查询sql:" + sql.toString());
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("PMUKID", Long.valueOf(rs.getLong("jst_3444_id")));
        String fbcs = leftSpace(rs.getString("fbcs"), 12);
        map.put("PMMCU", fbcs);
        map.put("PMDL09", " ");
        map.put("PMAN8", Long.valueOf(rs.getLong("gysdzh")));
        map.put("PMDL08", " ");
        String dxmh = getLhinfo(rs.getString("xmh"), "dxmh");
        map.put("PMITM", dxmh);
        map.put("PMLITM", rs.getString("xmh"));
        String dsxmh = getLhinfo(rs.getString("xmh"), "dsxmh");
        map.put("PMAITM", dsxmh);
        String lhmc = getLhinfo(rs.getString("xmh"), "lhmc");
        map.put("PMDSC1", lhmc);
        map.put("PMDSC2", rs.getString("gg"));
        map.put("PMCATN", " ");
        map.put("PMDMCT", rs.getString("hsdj"));
        map.put("PMDMCS", Integer.valueOf(999));
        map.put("PMKCOO", " ");
        map.put("PMDL07", rs.getString("gsmc"));
        map.put("PMDOCO", Integer.valueOf(0));
        map.put("PMDCTO", " ");
        map.put("PMLNID", Integer.valueOf(0));
        map.put("PMCRCD", rs.getString("hbm"));
        String dwcode = getLhinfo(rs.getString("xmh"), "dw");
        map.put("PMUOM", dwcode);
        map.put("PMPRRC", Double.valueOf(rs.getDouble("dwcb") * 1000.0D));
        map.put("PMUORG", Integer.valueOf(0));
        map.put("PMEFTJ", Long.valueOf(rs.getLong("sxz")));
        map.put("PMEXDJ", Long.valueOf(rs.getLong("yxz")));
        map.put("PMEV01", "N");
        map.put("PMAA", Integer.valueOf(0));
        map.put("PMAA1", Integer.valueOf(0));
        map.put("PMAA2", Integer.valueOf(0));
        map.put("PMDL01", " ");
        map.put("PMDL02", " ");
        map.put("PMDS50", rs.getString("gsmc"));
        map.put("PMDL011", rs.getString("bz"));
        map.put("PMUSER", request.getSession().getAttribute("userAccount").toString());
        map.put("PMPID", "OA");
        map.put("PMJOBN", "OA");
        map.put("PMUPMT", Long.valueOf(rs.getLong("time")));
        map.put("PMUPMJ", Long.valueOf(rs.getLong("today")));
        map.put("PMDL04", " ");
        map.put("PMDL05", " ");
        list.add(map);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    int cnt = 0;
    if (list.size() > 0)
      cnt = insertTable(list); 
    if (cnt == 0) {
      System.out.println("插入中间表失败(可能原因连接报错)");
    } else if (cnt == list.size()) {
      System.out.println("采购价格审批（昆山）-全部插入中间表成功（共" + cnt + "条数据）");
    } else {
      System.out.println("采购价格审批（昆山）-部分插入中间表（共计" + list.size() + "条数据，插入成功" + cnt + "条）");
    } 
    return result;
  }
  
  private int insertTable(List<Map<String, Object>> list) {
    int cnt = 0;
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      StringBuffer sqlBuffer = new StringBuffer();
      for (Map<String, Object> map : list) {
        sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into PRODDTA.FE6OA03@jdedblink(PMUKID,PMMCU,PMDL09,PMAN8,PMDL08,PMITM,PMLITM,PMAITM,");
        sqlBuffer.append("PMDSC1,PMDSC2,PMCATN,PMDMCT,PMDMCS,PMKCOO,PMDL07,PMDOCO,");
        sqlBuffer.append("PMDCTO,PMLNID,PMCRCD,PMUOM,PMPRRC,PMUORG,PMEFTJ,PMEXDJ,");
        sqlBuffer.append("PMEV01,PMAA,PMAA1,PMAA2,PMDL01,PMDL02,PMDS50,PMDL011,");
        sqlBuffer.append("PMUSER,PMPID,PMJOBN,PMUPMT,PMUPMJ,PMDL04,PMDL05) values( ");
        sqlBuffer.append("'" + map.get("PMUKID") + "','" + map.get("PMMCU") + "','" + map.get("PMDL09") + "','" + map.get("PMAN8") + "',");
        sqlBuffer.append("'" + map.get("PMDL08") + "','" + map.get("PMITM") + "','" + map.get("PMLITM") + "','" + map.get("PMAITM") + "',");
        sqlBuffer.append("'" + map.get("PMDSC1") + "','" + map.get("PMDSC2") + "','" + map.get("PMCATN") + "','" + map.get("PMDMCT") + "',");
        sqlBuffer.append("'" + map.get("PMDMCS") + "','" + map.get("PMKCOO") + "','" + map.get("PMDL07") + "'," + map.get("PMDOCO") + ",");
        sqlBuffer.append("'" + map.get("PMDCTO") + "'," + map.get("PMLNID") + ",'" + map.get("PMCRCD") + "','" + map.get("PMUOM") + "',");
        sqlBuffer.append("'" + map.get("PMPRRC") + "'," + map.get("PMUORG") + ",'" + map.get("PMEFTJ") + "','" + map.get("PMEXDJ") + "',");
        sqlBuffer.append("'" + map.get("PMEV01") + "'," + map.get("PMAA") + "," + map.get("PMAA1") + "," + map.get("PMAA2") + ",");
        sqlBuffer.append("'" + map.get("PMDL01") + "','" + map.get("PMDL02") + "','" + map.get("PMDS50") + "','" + map.get("PMDL011") + "',");
        sqlBuffer.append("'" + map.get("PMUSER") + "','" + map.get("PMPID") + "','" + map.get("PMJOBN") + "','" + map.get("PMUPMT") + "',");
        sqlBuffer.append("'" + map.get("PMUPMJ") + "','" + map.get("PMDL04") + "','" + map.get("PMDL05") + "')");
        pstmt = conn.prepareStatement(sqlBuffer.toString());
        System.out.println("昆山-输出价格审批回写sql:" + sqlBuffer.toString());
        try {
          cnt += pstmt.executeUpdate();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        pstmt.close();
      } 
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return cnt;
  }
  
  public String getLhinfo(String xmh, String flag) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = "";
    try {
      String sql = "";
      if (flag.equals("dxmh")) {
        sql = "select IMITM from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + xmh + "'";
      } else if (flag.equals("dsxmh")) {
        sql = "select IMAITM from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + xmh + "'";
      } else if (flag.equals("lhmc")) {
        sql = "select IMDSC1 from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + xmh + "'";
      } else if (flag.equals("dw")) {
        sql = "select IMUOM1 from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + xmh + "'";
      } 
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        result = rs.getString(1).trim(); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public String leftSpace(String para, int len) {
    int paraLen = para.length();
    if (paraLen < len)
      while (paraLen < len) {
        StringBuffer str = new StringBuffer();
        str.append(" ").append(para);
        para = str.toString();
        paraLen = para.length();
      }  
    return para;
  }
}
