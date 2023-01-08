package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
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
import javax.sql.DataSource;

public class WorkflowForZKtest extends Workflow {
  public String complete(HttpServletRequest request) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String sql = "SELECT bh,fbcs,fgsbm,khbm,khmc,lh,yzk,sqzk,ycxzk,CONCAT(YEAR(STR_TO_DATE(IFNULL(sxrq,NOW()),'%Y-%m-%d'))-1900,DATE_FORMAT(STR_TO_DATE(IFNULL(sxrq,NOW()),'%Y-%m-%d'),'%j')) AS sxrq,CONCAT(YEAR(STR_TO_DATE(IFNULL(sixrq,NOW()),'%Y-%m-%d'))-1900,DATE_FORMAT(STR_TO_DATE(IFNULL(sixrq,NOW()),'%Y-%m-%d'),'%j')) AS sixrq,CONCAT(YEAR(NOW())-1900,DATE_FORMAT(NOW(),'%j')) AS today,DATE_FORMAT(NOW(),'%H%i%s') AS TIME FROM jst_3275 f,jst_3274 c WHERE f.jst_3275_foreignkey=c.jst_3274_id and c.jst_3274_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, Object> map = new HashMap<String, Object>();
        String lh = rs.getString("lh");
        Map<String, String> info = getLhinfo(lh);
        map.put("CDUKID", Long.valueOf(rs.getLong("bh")));
        map.put("CDITM", info.get("dxmh"));
        map.put("CDLITM", lh);
        map.put("CDAITM", info.get("dsxmh"));
        map.put("CDMCU", leftSpace(rs.getString("fgsbm"), 12));
        map.put("CDDL03", rs.getString("fbcs"));
        map.put("CDLOCN", " ");
        map.put("CDLOTN", " ");
        map.put("CDAN8", Long.valueOf(rs.getLong("khbm")));
        map.put("CDDL04", rs.getString("khmc"));
        map.put("CDIGID", Integer.valueOf(0));
        map.put("CDCGID", Integer.valueOf(0));
        map.put("CDSRP2", info.get("hldm"));
        map.put("CDDL05", " ");
        map.put("CDFRMP", Integer.valueOf(0));
        map.put("CDCRCD", "CNY");
        map.put("CDUOM", info.get("dw"));
        map.put("CDEFTJ", Long.valueOf(rs.getLong("sxrq")));
        map.put("CDEXDJ", Long.valueOf(rs.getLong("sixrq")));
        map.put("CDUPRC", Double.valueOf(rs.getDouble("sqzk") * 1000.0D));
        map.put("CDEV01", "N");
        map.put("CDAA", Integer.valueOf(0));
        map.put("CDAA1", Integer.valueOf(0));
        map.put("CDAA2", Integer.valueOf(0));
        map.put("CDDL01", " ");
        map.put("CDDL02", " ");
        map.put("CDDS50", " ");
        map.put("CDDL06", Double.valueOf(rs.getDouble("yzk") * 1000.0D));
        if (rs.getString("ycxzk").equals("是")) {
          map.put("CDDL07", "ONE");
        } else {
          map.put("CDDL07", "MORE");
        } 
        map.put("CDDL011", " ");
        map.put("CDURCD", " ");
        map.put("CDURDT", Integer.valueOf(0));
        map.put("CDURAT", Integer.valueOf(0));
        map.put("CDURAB", Integer.valueOf(0));
        map.put("CDURRF", " ");
        map.put("CDAPRS", " ");
        map.put("CDUSER", request.getSession().getAttribute("userAccount").toString());
        map.put("CDPID", "OA");
        map.put("CDJOBN", "OA");
        map.put("CDUPMJ", Long.valueOf(rs.getLong("today")));
        map.put("CDTDAY", Long.valueOf(rs.getLong("time")));
        map.put("CDAC12", " ");
        list.add(map);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e1) {
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
      System.out.println("全部插入中间表成功（共" + cnt + "条数据）");
    } else {
      System.out.println("部分插入中间表（共计" + list.size() + "条数据，插入成功" + cnt + "条）");
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
        sqlBuffer.append("insert into CRPDTA.FE6OA02 (CDUKID,CDITM,CDLITM,CDAITM,CDMCU,CDDL03,CDLOCN,CDLOTN,");
        sqlBuffer.append("CDAN8,CDDL04,CDIGID,CDCGID,CDSRP2,CDDL05,CDFRMP,CDCRCD,");
        sqlBuffer.append("CDUOM,CDEFTJ,CDEXDJ,CDUPRC,CDEV01,CDAA,CDAA1,CDAA2,");
        sqlBuffer.append("CDDL01,CDDL02,CDDS50,CDDL06,CDDL07,CDDL011,CDURCD,CDURDT,");
        sqlBuffer.append("CDURAT,CDURAB,CDURRF,CDAPRS,CDUSER,CDPID,CDJOBN,CDUPMJ,");
        sqlBuffer.append("CDTDAY,CDAC12) values( ");
        sqlBuffer.append("'" + map.get("CDUKID") + "','" + map.get("CDITM") + "','" + map.get("CDLITM") + "','" + map.get("CDAITM") + "',");
        sqlBuffer.append("'" + map.get("CDMCU") + "','" + map.get("CDDL03") + "','" + map.get("CDLOCN") + "','" + map.get("CDLOTN") + "',");
        sqlBuffer.append("'" + map.get("CDAN8") + "','" + map.get("CDDL04") + "','" + map.get("CDIGID") + "','" + map.get("CDCGID") + "',");
        sqlBuffer.append("'" + map.get("CDSRP2") + "','" + map.get("CDDL05") + "','" + map.get("CDFRMP") + "','" + map.get("CDCRCD") + "',");
        sqlBuffer.append("'" + map.get("CDUOM") + "','" + map.get("CDEFTJ") + "','" + map.get("CDEXDJ") + "','" + map.get("CDUPRC") + "',");
        sqlBuffer.append("'" + map.get("CDEV01") + "','" + map.get("CDAA") + "','" + map.get("CDAA1") + "','" + map.get("CDAA2") + "',");
        sqlBuffer.append("'" + map.get("CDDL01") + "','" + map.get("CDDL02") + "','" + map.get("CDDS50") + "','" + map.get("CDDL06") + "',");
        sqlBuffer.append("'" + map.get("CDDL07") + "','" + map.get("CDDL011") + "','" + map.get("CDURCD") + "','" + map.get("CDURDT") + "',");
        sqlBuffer.append("'" + map.get("CDURAT") + "','" + map.get("CDURAB") + "','" + map.get("CDURRF") + "','" + map.get("CDAPRS") + "',");
        sqlBuffer.append("'" + map.get("CDUSER") + "','" + map.get("CDPID") + "','" + map.get("CDJOBN") + "','" + map.get("CDUPMJ") + "',");
        sqlBuffer.append("'" + map.get("CDTDAY") + "','" + map.get("CDAC12") + "')");
        pstmt = conn.prepareStatement(sqlBuffer.toString());
        System.out.println("东莞-折扣申请写入sql:" + sqlBuffer.toString());
        try {
          cnt += pstmt.executeUpdate();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        pstmt.close();
      } 
      conn.close();
    } catch (SQLException e) {
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
  
  public String getHlmc(String bm) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String hldm = "";
    try {
      String sql = "select DRDL02 from PRODCTL.F0005@jdedblink  where DRSY='41' AND DRRT='S2' AND trim(DRKY)='" + bm + "'";
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        hldm = rs.getString("DRDL02").trim(); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return hldm;
  }
  
  public Map<String, String> getLhinfo(String xmh) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map<String, String> info = new HashMap<String, String>();
    try {
      String sql = "select IMSRP2,IMUOM1 ,IMITM,IMAITM from PRODDTA.F4101@jdedblink  where TRIM(IMLITM)='" + xmh + "'";
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        info.put("hldm", rs.getString(1).trim());
        info.put("dw", rs.getString(2).trim());
        info.put("dxmh", rs.getString("IMITM"));
        info.put("dsxmh", rs.getString("IMAITM"));
      } 
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
    return info;
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
