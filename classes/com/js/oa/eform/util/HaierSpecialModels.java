package com.js.oa.eform.util;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;

public class HaierSpecialModels {
  public static List<String[]> getSpecialModelList(Map<String, String> parms) {
    List<String[]> list = (List)new ArrayList<String>();
    String sql = "select MACHINETYPE,MACHINETYPENAME from HAIER_SPECIALMODELS WHERE STATUS='1' ";
    List<String> parmArr = new ArrayList<String>();
    if (parms != null) {
      if (parms.get("machineType") != null) {
        sql = String.valueOf(sql) + " and MACHINETYPE like ?";
        parmArr.add("%" + (String)parms.get("machineType") + "%");
      } 
      if (parms.get("machineTypeName") != null) {
        sql = String.valueOf(sql) + " and MACHINETYPENAME like ?";
        parmArr.add("%" + (String)parms.get("machineTypeName") + "%");
      } 
    } 
    sql = String.valueOf(sql) + " order by MACHINETYPE";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      for (int i = 0; i < parmArr.size(); i++)
        pstmt.setString(i + 1, parmArr.get(i)); 
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] temp = new String[2];
        temp[0] = rs.getString("MACHINETYPE");
        temp[1] = rs.getString("MACHINETYPENAME");
        list.add(temp);
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
    return list;
  }
  
  public static List<String[]> getSpecialRelationList(String eoNo, String figureNo) {
    List<String[]> list = (List)new ArrayList<String>();
    String sql = "select MACHINETYPE,MACHINETYPENAME from HAIER_SPECIALMODELS WHERE MACHINETYPE IN (select MACHINETYPE from HAIER_SPECIALMODELS_RELATION where FIGURENO=? AND EONO=?) ";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, figureNo);
      pstmt.setString(2, eoNo);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] temp = new String[2];
        temp[0] = rs.getString("MACHINETYPE");
        temp[1] = rs.getString("MACHINETYPENAME");
        list.add(temp);
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
    return list;
  }
  
  public static String saveSpecialRelationList(String eoNo, String figureNo, String specialData) {
    String[] specialDataArr = (String[])null;
    if (specialData.length() > 4)
      if (";;".equals(specialData.substring(0, 2))) {
        specialDataArr = specialData.substring(2, specialData.length() - 2).split(";;");
      } else {
        specialDataArr = specialData.substring(0, specialData.length() - 2).split(";;");
      }  
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      String delSql = "delete from HAIER_SPECIALMODELS_RELATION where FIGURENO=? AND EONO=?";
      pstmt = conn.prepareStatement(delSql);
      pstmt.setString(1, figureNo);
      pstmt.setString(2, eoNo);
      pstmt.executeUpdate();
      System.out.println("成功删除EO编号为" + eoNo + ",图号编号为" + figureNo + "的机种数据");
      pstmt.close();
      if (specialDataArr != null && specialDataArr.length > 0) {
        pstmt = conn.prepareStatement("insert into HAIER_SPECIALMODELS_RELATION values(?,?,?)");
        conn.setAutoCommit(false);
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = specialDataArr).length, b = 0; b < i; ) {
          String tmp = arrayOfString[b];
          pstmt.setString(1, tmp);
          pstmt.setString(2, figureNo);
          pstmt.setString(3, eoNo);
          pstmt.addBatch();
          b++;
        } 
        pstmt.executeBatch();
        conn.commit();
        System.out.println("成功插入EO编号为" + eoNo + ",图号编号为" + figureNo + "的" + specialDataArr.length + "条机种数据");
        conn.setAutoCommit(true);
        pstmt.close();
      } 
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
      return "0";
    } 
    return "1";
  }
  
  public static void delSpecialRelationList(String eoNo, String figureNo) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      String delSql = "delete from HAIER_SPECIALMODELS_RELATION where FIGURENO=? AND EONO=?";
      pstmt = conn.prepareStatement(delSql);
      pstmt.setString(1, figureNo);
      pstmt.setString(2, eoNo);
      pstmt.executeUpdate();
      System.out.println("成功删除EO编号为" + eoNo + ",图号编号为" + figureNo + "的冗余机种数据");
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
  }
  
  public static String getFigureNo(String key) {
    String figureNo = "";
    String sql = "select JST_3004_f3054 from JST_3004 where JST_3004_ID=? ";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, key);
      rs = pstmt.executeQuery();
      while (rs.next())
        figureNo = rs.getString("JST_3004_f3054"); 
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
    return figureNo;
  }
  
  public static String drawingChange(String ids, String status) {
    ids = ids.substring(0, ids.length() - 1);
    String[] idsArr = ids.split(",");
    String flag = "";
    if (status.equals("0")) {
      flag = "审核不通过";
    } else {
      flag = "审核通过";
    } 
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("update JST_3004 SET JST_3004_SHZT=? where JST_3004_ID=?");
      conn.setAutoCommit(false);
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = idsArr).length, b = 0; b < i; ) {
        String tmp = arrayOfString[b];
        pstmt.setString(1, flag);
        pstmt.setString(2, tmp);
        pstmt.addBatch();
        b++;
      } 
      pstmt.executeBatch();
      conn.commit();
      conn.setAutoCommit(true);
      pstmt.close();
      conn.close();
      return "1";
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
      return "0";
    } 
  }
  
  public static List<Map<String, String>> getDependentTicketData(String manufactor) {
    String sql = "select jst_3006_id,jst_3006_f3080,jst_3006_f3081,SUBSTR(jst_3006_f3084,0,INSTR(jst_3006_f3084,'@@$@@', 1, 1)-1) as jst_3006_f3084, SUBSTR(jst_3006_f3084,INSTR(jst_3006_f3084,'@@$@@', 1, 1)+5) as thmc,jst_3006_f3082,SUBSTR(jst_3006_f3083,INSTR(jst_3006_f3083,'@@$@@', 1, 1)+5) as jst_3006_f3083,jst_3006_f3085,jst_3006_f3103, 0 as sffq from jst_3006 where jst_3006_f3103 ='1' and jst_3006_id NOT in (select jst_3009_f3123 from jst_3009) ";
    if (manufactor != null && !"".equals(manufactor))
      sql = String.valueOf(sql) + " and jst_3006_f3083 like ?"; 
    sql = String.valueOf(sql) + " order by sffq asc,jst_3006_id desc";
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      if (manufactor != null && !"".equals(manufactor))
        pstmt.setString(1, "%" + manufactor + "%"); 
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("jst_3006_id", rs.getString("jst_3006_id"));
        map.put("jst_3006_f3080", rs.getString("jst_3006_f3080"));
        map.put("jst_3006_f3081", rs.getString("jst_3006_f3081"));
        map.put("jst_3006_f3084", rs.getString("jst_3006_f3084"));
        map.put("thmc", rs.getString("thmc"));
        map.put("jst_3006_f3082", rs.getString("jst_3006_f3082"));
        map.put("jst_3006_f3083", rs.getString("jst_3006_f3083"));
        map.put("jst_3006_f3085", rs.getString("jst_3006_f3085"));
        map.put("jst_3006_f3103", rs.getString("jst_3006_f3103"));
        map.put("sffq", rs.getString("sffq"));
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
    return list;
  }
  
  public static String checkBaseDataExist(String th, String eo, String recordId) {
    int res = 1;
    String sql = "select count(*) cnt from jst_3004 where  jst_3004_f3053 =? and  jst_3004_f3054=?";
    if (!"0".equals(recordId))
      sql = String.valueOf(sql) + " and jst_3004_id<>?"; 
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, eo.trim());
      pstmt.setString(2, th.trim());
      if (!"0".equals(recordId))
        pstmt.setString(3, recordId); 
      rs = pstmt.executeQuery();
      while (rs.next())
        res = rs.getInt("cnt"); 
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
    if (res == 0)
      return "0"; 
    return "1";
  }
  
  public static List<Map<String, String>> getNewProductInfo(Map<String, String> parms) {
    List<Map<String, String>> result = new ArrayList<Map<String, String>>();
    StringBuffer sql = new StringBuffer();
    sql.append("select A.*,B.*,C.*,case when  A.workstatus1='0' then 'ERP系统原材料基础数据单=>'||jd1 else case  when B.workstatus2='0' then '依赖票=>'||jd2 when B.workstatus2='' then  'ERP系统原材料基础数据单=>办理结束' when B.workstatus2 is null then  'ERP系统原材料基础数据单=>办理结束' else case when  C.workstatus3 ='0' then '新品维护单=>'||jd3 when  C.workstatus3 ='' then  '依赖票=>办理结束' when  C.workstatus3 is null then  '依赖票=>办理结束' else '新品维护单=>办理完毕' end end end as zt,");
    sql.append(" case A.workstatus1 when 0 then blr1 else  case B.workstatus2 when 0 then blr2 else case C.workstatus3 when 0 then blr3 else '-' end end end as blr FROM ");
    sql.append(" (select jst_3005_id,jst_3005_f3064 as tuhao,jst_3005_f3068 as hpzwmc, jst_3005_f3070 as lb,(select jst_3017_f3223 from jst_3017 where jst_3017_id=jst_3005_f3072) as zdw,(select empname from org_employee where emp_id=jst_3005_owner) as tbr,jst_3005_date as tbsj,");
    sql.append(" (select jst_3017_f3223 from jst_3017 where jst_3017_id=jst_3005_f3073) as fdw,jst_3005_f3074 as hsgx,case jst_3005_f3076 when '1' then '是'when '2' then '否' else '-' end as twf,jst_3005_f3077 as nsyl,");
    sql.append(" (select base_key from tbase where base_parent='2119371'and base_value=JST_3005_F3207) as ysj,case jst_3005_f3058 when '1' then '是'when '2' then '否' else '-' end as sfjj, jst_3005_f3059 as xwwcsj,jst_3005_f3209 as jyq ,");
    sql.append(" (select jst_3014_f3191 from jst_3014 where jst_3014_id=jst_3005_foreignkey) as jsdd,workstatus as workstatus1,initactivityname as jd1,(select empname from org_employee where emp_id=wf_curemployee_id) as blr1 ");
    sql.append(" from jst_3005,jsf_work where jst_3005_foreignkey=workrecord_id and (workstatus=0 or workstatus=100))A ");
    sql.append(" left join (select jst_3006_id,jst_3006_f3084,SUBSTR(jst_3006_f3083,INSTR(jst_3006_f3083,'@@$@@', 1, 1)+5) as ylp_cj,jst_3006_f3090 as ylp_sysj,jst_3006_f3091 as ylp_jywcsj,jst_3006_f3100 as ylp_cbbdd,jst_3006_f3158 as ylp_pzdd,workstatus as workstatus2,initactivityname as jd2,");
    sql.append(" (select empname from org_employee where emp_id=wf_curemployee_id) as blr2 from jst_3006,jsf_work where jst_3006_id=workrecord_id and (workstatus=0 or workstatus=100)) B on SUBSTR(B.jst_3006_f3084,0,INSTR(B.jst_3006_f3084,'@@$@@', 1, 1)-1)=A.jst_3005_id ");
    sql.append(" left join (select case jst_3009_f3143 when '1' then '寄售' when '2' then '非寄售' when '3' then '日供货' else '-' end as xp_jsbz,jst_3009_f3144 as xp_zcy,jst_3009_f3145 as xp_cgpl,jst_3009_f3146 as xp_cgq,jst_3009_f3147 as xp_hs,");
    sql.append(" jst_3009_f3148 as xp_ysck,case NVL((select empname from org_employee where useraccounts=jst_3009_f3149),'n') when 'n' then jst_3009_f3149 else (select empname from org_employee where useraccounts=jst_3009_f3149) end as xp_bgy,jst_3009_f3150 as xp_aqkc,jst_3009_f3151 as xp_zdkc,case jst_3009_f3152 when '1' then 'A' when '2' then 'B' when '3' then 'C' when '4' then 'D' when '5' then 'E' ELSE '-' END as xp_fllx,");
    sql.append(" jst_3009_f3137 as xp_jhjg,jst_3009_f3138 as xp_zxbzdw,jst_3009_f3139 as xp_zxbzsl,jst_3009_f3140 as xp_zgys,jst_3009_f3141 as xp_jccjsh,jst_3009_f3142 as xp_pjbgbh,jst_3009_f3153 as xp_yphgf,jst_3009_f3154 as xp_jyy, jst_3009_f3123,");
    sql.append(" workstatus as workstatus3,initactivityname as jd3,(select empname from org_employee where emp_id=wf_curemployee_id) as blr3");
    sql.append(" from jst_3009,jsf_work where jst_3009_foreignkey=workrecord_id and (workstatus=0 or workstatus=100))C on B.jst_3006_id=jst_3009_f3123");
    String searchTuhao = "0";
    if (parms != null) {
      sql.append(" where 1=1 ");
      if (!"".equals(((String)parms.get("tuhao")).trim())) {
        sql.append(" and tuhao like ?");
        searchTuhao = "1";
      } 
      if (!"".equals(parms.get("lczt")))
        if ("10".equals(parms.get("lczt"))) {
          sql.append(" and workstatus1 = '0' ");
        } else if ("11".equals(parms.get("lczt"))) {
          sql.append(" and workstatus1 = '100' ");
        } else if ("20".equals(parms.get("lczt"))) {
          sql.append(" and workstatus2 = '0' ");
        } else if ("21".equals(parms.get("lczt"))) {
          sql.append(" and workstatus2 = '100' ");
        } else if ("30".equals(parms.get("lczt"))) {
          sql.append(" and workstatus3 = '0' ");
        } else if ("31".equals(parms.get("lczt"))) {
          sql.append(" and workstatus3 = '100' ");
        }  
      if (!"".equals(((String)parms.get("jccjs")).trim()))
        sql.append(" and xp_jccjsh like '%" + ((String)parms.get("jccjs")).trim() + "%' "); 
      if (!"".equals(((String)parms.get("tbr")).trim()))
        sql.append(" and tbr like '%" + ((String)parms.get("tbr")).trim() + "%' "); 
      if (!"".equals(((String)parms.get("tbsjs")).trim()))
        sql.append(" and tbsj >='" + ((String)parms.get("tbsjs")).trim() + "' "); 
      if (!"".equals(((String)parms.get("tbsje")).trim()))
        sql.append(" and tbsj <= '" + ((String)parms.get("tbsje")).trim() + "' "); 
    } 
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql.toString());
      if (!"0".equals(searchTuhao))
        pstmt.setString(1, "%" + ((String)parms.get("tuhao")).trim() + "%"); 
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> subMap = new HashMap<String, String>();
        subMap.put("tuhao", rs.getString("tuhao"));
        subMap.put("hpzwmc", rs.getString("hpzwmc"));
        subMap.put("lb", rs.getString("lb"));
        subMap.put("zdw", rs.getString("zdw"));
        subMap.put("fdw", rs.getString("fdw"));
        subMap.put("hsgx", rs.getString("hsgx"));
        subMap.put("twf", rs.getString("twf"));
        subMap.put("nsyl", rs.getString("nsyl"));
        subMap.put("ysj", rs.getString("ysj"));
        subMap.put("xp_jsbz", rs.getString("xp_jsbz"));
        subMap.put("xp_zcy", rs.getString("xp_zcy"));
        subMap.put("xp_cgpl", rs.getString("xp_cgpl"));
        subMap.put("xp_cgq", rs.getString("xp_cgq"));
        subMap.put("xp_hs", rs.getString("xp_hs"));
        if (rs.getString("xp_ysck") == null) {
          subMap.put("xp_ysck", null);
        } else if (rs.getString("xp_ysck").indexOf("@@$@@") > -1) {
          subMap.put("xp_ysck", rs.getString("xp_ysck").substring(rs.getString("xp_ysck").indexOf("@@$@@") + 5));
        } else {
          subMap.put("xp_ysck", rs.getString("xp_ysck"));
        } 
        subMap.put("xp_bgy", rs.getString("xp_bgy"));
        subMap.put("xp_aqkc", rs.getString("xp_aqkc"));
        subMap.put("xp_zdkc", rs.getString("xp_zdkc"));
        subMap.put("xp_fllx", rs.getString("xp_fllx"));
        subMap.put("xp_jhjg", rs.getString("xp_jhjg"));
        subMap.put("xp_zxbzdw", rs.getString("xp_zxbzdw"));
        subMap.put("xp_zxbzsl", rs.getString("xp_zxbzsl"));
        subMap.put("xp_zgys", rs.getString("xp_zgys"));
        subMap.put("xp_jccjsh", rs.getString("xp_jccjsh"));
        subMap.put("xp_pjbgbh", rs.getString("xp_pjbgbh"));
        subMap.put("sfjj", rs.getString("sfjj"));
        subMap.put("xwwcsj", rs.getString("xwwcsj"));
        subMap.put("jsdd", rs.getString("jsdd"));
        subMap.put("ylp_cj", rs.getString("ylp_cj"));
        subMap.put("ylp_sysj", rs.getString("ylp_sysj"));
        subMap.put("ylp_jywcsj", rs.getString("ylp_jywcsj"));
        subMap.put("xp_yphgf", rs.getString("xp_yphgf"));
        subMap.put("xp_jyy", rs.getString("xp_jyy"));
        subMap.put("jyq", rs.getString("jyq"));
        subMap.put("ylp_cbbdd", rs.getString("ylp_cbbdd"));
        subMap.put("ylp_pzdd", rs.getString("ylp_pzdd"));
        subMap.put("zt", rs.getString("zt"));
        subMap.put("blr", rs.getString("blr"));
        subMap.put("tbr", rs.getString("tbr"));
        subMap.put("tbsj", rs.getString("tbsj"));
        result.add(subMap);
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
    String exportFlag = "0";
    if (parms != null)
      exportFlag = (parms.get("export") == null) ? "" : parms.get("export"); 
    if (result != null && result.size() > 0)
      for (Map<String, String> subMap : result) {
        for (Map.Entry<String, String> entry : subMap.entrySet()) {
          if (entry.getValue() == null || "null".equalsIgnoreCase(entry.getValue())) {
            if ("1".equals(exportFlag)) {
              entry.setValue(" ");
              continue;
            } 
            entry.setValue("&nbsp;");
          } 
        } 
      }  
    return result;
  }
  
  public static List<String> getYsjInfo(String cj) {
    List<String> list = new ArrayList<String>();
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select jst_3018_f3228 from jst_3018 where jst_3018_f3262=?";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, cj);
      rs = pstmt.executeQuery();
      while (rs.next())
        list.add(rs.getString("jst_3018_f3228")); 
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
    return list;
  }
  
  public static Map<String, String> checkTuhao(String tuhao) {
    Map<String, String> result = new HashMap<String, String>();
    String[] tuhaos = tuhao.split(";");
    String temParm = "";
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = tuhaos).length, b = 0; b < i; ) {
      String t = arrayOfString1[b];
      temParm = String.valueOf(temParm) + "'" + t.trim() + "',";
      b++;
    } 
    if (temParm.length() > 0)
      temParm = temParm.substring(0, temParm.length() - 1); 
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource("jdbc/haiererpin");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select prd_no from prdt where (nouse_dd is NULL OR nouse_dd='') and prd_no in (" + temParm + ")";
    Set<String> list = new HashSet<String>();
    boolean check = true;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        list.add(rs.getString("prd_no")); 
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
      check = false;
    } 
    if (!check) {
      result.put("res", "false");
      result.put("reason", "图号校验数据库连接失败，无法校验，不允许提交");
    } else if (list.size() > 0) {
      StringBuffer sb = new StringBuffer();
      for (String t : list) {
        if (tuhao.indexOf(t) > -1)
          for (int j = 0; j < tuhaos.length; j++) {
            if (t.equals(tuhaos[j]))
              sb.append("第" + (j + 1) + "行图号存在且未被停用，不允许提交;"); 
          }  
      } 
      result.put("res", "false");
      result.put("reason", sb.toString());
    } else {
      result.put("res", "true");
    } 
    return result;
  }
  
  public static Map<String, String> checkTuhaoEoGn(String tuhao) {
    Map<String, String> result = new HashMap<String, String>();
    String[] tuhaos = tuhao.split(";;");
    String temParm = "";
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = tuhaos).length, b = 0; b < i; ) {
      String t = arrayOfString1[b];
      temParm = String.valueOf(temParm) + "'" + t.trim() + "',";
      b++;
    } 
    if (temParm.length() > 0)
      temParm = temParm.substring(0, temParm.length() - 1); 
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select jst_3002_f3206 from jst_3002 where jst_3002_foreignkey in (select distinct workrecord_id from jsf_work where workprocess_id='64977' and workstatus = 1) and jst_3002_f3206 in (" + temParm + ")";
    Set<String> list = new HashSet<String>();
    boolean check = true;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        list.add(rs.getString("jst_3002_f3206")); 
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
    if (!check) {
      result.put("res", "false");
      result.put("reason", "图号校验异常，不允许提交");
    } else if (list.size() > 0) {
      StringBuffer sb = new StringBuffer();
      for (String t : list) {
        if (tuhao.indexOf(t) > -1)
          for (int j = 0; j < tuhaos.length; j++) {
            if (t.equals(tuhaos[j]))
              sb.append("第" + (j + 1) + "行图号存在正在办理的流程，不允许提交;"); 
          }  
      } 
      result.put("res", "false");
      result.put("reason", sb.toString());
    } else {
      result.put("res", "true");
    } 
    return result;
  }
  
  public static List<ArrayList<String>> getSelfProductNzpInfo(Map<String, String> parms) {
    List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    String exportFlag = "0";
    if (parms != null)
      exportFlag = (parms.get("export") == null) ? "" : parms.get("export"); 
    StringBuffer sql = new StringBuffer();
    sql.append("select jst_3010_f3159,jst_3011_f3168,jst_3011_f3169,(select base_key from tbase where base_parent='2356135' and base_value=jst_3011_f3171) as jst_3011_f3171,(select base_key from tbase where base_parent='2119371' and base_value=jst_3011_f3267) as jst_3011_f3267,");
    sql.append(" (select jst_3017_f3223 from jst_3017 where jst_3017_id=jst_3011_f3265) as jst_3011_f3265,jst_3011_f3170,jst_3011_f3236,jst_3011_f3221,jst_3011_f3264,jst_3011_f3266");
    sql.append(" from jst_3010,jst_3011 where jst_3010_id=JST_3011_FOREIGNKEY ");
    String searchTuhao = "0";
    if (parms != null) {
      if (!"".equals(((String)parms.get("tuhao")).trim())) {
        sql.append(" and jst_3011_f3168 like ?");
        searchTuhao = "1";
      } 
      if (!"".equals(((String)parms.get("glno")).trim()))
        sql.append(" and jst_3010_f3159 like '%" + ((String)parms.get("glno")).trim() + "%' "); 
      if (!"".equals(((String)parms.get("zwmc")).trim()))
        sql.append(" and jst_3011_f3169 like '%" + ((String)parms.get("zwmc")).trim() + "%' "); 
    } 
    sql.append(" order by jst_3010_f3159 desc");
    List<String> cols = new ArrayList<String>();
    cols.add("jst_3010_f3159");
    cols.add("jst_3011_f3168");
    cols.add("jst_3011_f3169");
    cols.add("jst_3011_f3171");
    cols.add("jst_3011_f3267");
    cols.add("jst_3011_f3265");
    cols.add("jst_3011_f3170");
    cols.add("jst_3011_f3236");
    cols.add("jst_3011_f3221");
    cols.add("jst_3011_f3264");
    cols.add("jst_3011_f3266");
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql.toString());
      if (!"0".equals(searchTuhao))
        pstmt.setString(1, "%" + ((String)parms.get("tuhao")).trim() + "%"); 
      rs = pstmt.executeQuery();
      String tmp = "";
      while (rs.next()) {
        ArrayList<String> subList = new ArrayList<String>();
        for (String t : cols) {
          tmp = rs.getString(t);
          if (tmp == null || "null".equalsIgnoreCase(tmp)) {
            if ("1".equals(exportFlag)) {
              subList.add(" ");
              continue;
            } 
            subList.add("&nbsp;");
            continue;
          } 
          subList.add(tmp);
        } 
        result.add(subList);
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
    return result;
  }
  
  public static List<ArrayList<String>> getSelfProductXjxInfo(Map<String, String> parms) {
    List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    String exportFlag = "0";
    if (parms != null)
      exportFlag = (parms.get("export") == null) ? "" : parms.get("export"); 
    StringBuffer sql = new StringBuffer();
    sql.append("select jst_3012_f3173,jst_3013_f3182,jst_3013_f3183,jst_3013_f3184,(select base_key from tbase where base_parent='22145' and base_value=jst_3013_f3185) as jst_3013_f3185,");
    sql.append("(select base_key from tbase where base_parent='2120067' and base_value=jst_3013_f3186) as jst_3013_f3186,(select base_key from tbase where base_parent='2120072' and base_value=jst_3013_f3187) as jst_3013_f3187,");
    sql.append("(select base_key from tbase where base_parent='2120075' and base_value=jst_3013_f3188) as jst_3013_f3188,(select jst_3016_f3202 from jst_3016 where jst_3016_id=jst_3013_f3200) as jst_3013_f3200,");
    sql.append("jst_3013_f3201,jst_3013_f3238,jst_3013_f3239,jst_3013_f3268,jst_3013_f3322,jst_3013_f3323,jst_3013_f3324,jst_3013_f3325,jst_3013_f3321,jst_3013_f3277,jst_3013_f3278,jst_3013_f3269 from jst_3012,jst_3013 where jst_3012_id=JST_3013_FOREIGNKEY");
    String searchTuhao = "0";
    if (parms != null) {
      if (!"".equals(((String)parms.get("tuhao")).trim())) {
        sql.append(" and jst_3013_f3183 like ?");
        searchTuhao = "1";
      } 
      if (!"".equals(((String)parms.get("glno")).trim()))
        sql.append(" and jst_3012_f3173 like '%" + ((String)parms.get("glno")).trim() + "%' "); 
      if (!"".equals(((String)parms.get("jxmc")).trim()))
        sql.append(" and jst_3013_f3182 like '%" + ((String)parms.get("jxmc")).trim() + "%' "); 
    } 
    sql.append(" order by jst_3012_f3173 desc");
    List<String> cols = new ArrayList<String>();
    cols.add("jst_3012_f3173");
    cols.add("jst_3013_f3182");
    cols.add("jst_3013_f3183");
    cols.add("jst_3013_f3184");
    cols.add("jst_3013_f3185");
    cols.add("jst_3013_f3186");
    cols.add("jst_3013_f3187");
    cols.add("jst_3013_f3188");
    cols.add("jst_3013_f3200");
    cols.add("jst_3013_f3201");
    cols.add("jst_3013_f3238");
    cols.add("jst_3013_f3239");
    cols.add("jst_3013_f3268");
    cols.add("jst_3013_f3322");
    cols.add("jst_3013_f3323");
    cols.add("jst_3013_f3324");
    cols.add("jst_3013_f3325");
    cols.add("jst_3013_f3321");
    cols.add("jst_3013_f3277");
    cols.add("jst_3013_f3278");
    cols.add("jst_3013_f3269");
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql.toString());
      if (!"0".equals(searchTuhao))
        pstmt.setString(1, "%" + ((String)parms.get("tuhao")).trim() + "%"); 
      rs = pstmt.executeQuery();
      String tmp = "";
      while (rs.next()) {
        ArrayList<String> subList = new ArrayList<String>();
        for (String t : cols) {
          tmp = rs.getString(t);
          if (tmp == null || "null".equalsIgnoreCase(tmp)) {
            if ("1".equals(exportFlag)) {
              subList.add(" ");
              continue;
            } 
            subList.add("&nbsp;");
            continue;
          } 
          if ("jst_3013_f3321".equals(t) && tmp.indexOf("@@$@@") > -1) {
            subList.add(tmp.substring(0, tmp.indexOf("@@$@@")));
            continue;
          } 
          subList.add(tmp);
        } 
        result.add(subList);
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
    return result;
  }
}
