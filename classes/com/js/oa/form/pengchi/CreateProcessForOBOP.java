package com.js.oa.form.pengchi;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreateProcessForOBOP {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  public List<Map<String, String>> getFieldValueForOP() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select KHUKID,KHDCTO,KHKCOO,KHDOCO,KHSFXO,KHRORN,KHAN8,ABALPH,KHMLNM,KHFUSS2,KHVR01,KHFUSS1,KHFUSS4,KHFUSS3,CRPDTA.NTOD(KHTRDJ) KHTRDJ,KHADD1,A6CRCA,KHPTD,KHOTOT,TATAXA,KHADD2,KHORBY,KHDL09 from PRODDTA.FE64311H@jdedblink, PRODDTA.F0401@jdedblink,PRODDTA.F0101@jdedblink,PRODDTA.F4008@jdedblink  where FE64311H.KHAN8= F0401.A6AN8  and F0101.ABAN8=FE64311H.KHAN8 and F4008.TATXA1=FE64311H.KHTXA1 and F4008.TAEFTJ<=CRPDTA.DTON(SYSDATE) and KHPOHP01=0 and KHNXTR=' ' and KHDCTO= 'OP'  and KHKCOO='00010'";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String updateSql = "";
    String ukids = "";
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("qgdh", rs.getString("KHRORN"));
        result.put("gys", rs.getString("ABALPH"));
        result.put("lxr", rs.getString("KHMLNM"));
        result.put("lxrdh", rs.getString("KHFUSS2"));
        result.put("cgy", rs.getString("KHVR01"));
        result.put("lxrcz", rs.getString("KHFUSS1"));
        result.put("cgycz", rs.getString("KHFUSS4"));
        result.put("cgydh", rs.getString("KHFUSS3"));
        result.put("cgdh", String.valueOf(rs.getString("KHDOCO")) + rs.getString("KHDCTO"));
        result.put("ddrq", rs.getString("KHTRDJ").substring(0, 10));
        result.put("dz", rs.getString("KHADD1"));
        result.put("bz", rs.getString("A6CRCA"));
        result.put("hjsl", "");
        result.put("zkje", "");
        result.put("fjf", "");
        result.put("zzs", "");
        result.put("fkfs", rs.getString("KHPTD"));
        result.put("fplx", rs.getString("TATAXA"));
        result.put("zje", String.valueOf(rs.getLong("KHOTOT") / 100.0D));
        result.put("shdz", rs.getString("KHADD2"));
        result.put("zdr", rs.getString("KHORBY"));
        result.put("zdrmc", rs.getString("KHDL09"));
        result.put("ukzj", rs.getString("KHUKID").trim());
        result.put("ddlx", rs.getString("KHDCTO").trim());
        result.put("ddgs", rs.getString("KHKCOO").trim());
        result.put("ddh", rs.getString("KHDOCO").trim());
        result.put("ddhz", rs.getString("KHSFXO").trim());
        datas.add(result);
        ukids = String.valueOf(ukids) + rs.getString("KHUKID").trim() + ",";
      } 
      rs.close();
      pstmt.close();
      SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
      Calendar ca = Calendar.getInstance();
      String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
      year = "1" + year.substring(2);
      int today = ca.get(6);
      String dayCount = "";
      if (today < 10) {
        dayCount = "00" + today;
      } else if (today >= 10 && today < 100) {
        dayCount = "0" + today;
      } else {
        dayCount = (new StringBuilder(String.valueOf(today))).toString();
      } 
      String updateRemark = "OA修改状态为230,时间:" + sdf.format(new Date());
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
    return datas;
  }
  
  public List<Map<String, String>> getFieldvalueForOPmx(String ukid) {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select KDUKID,KDDCTO,KDKCOO,KDDOCO,KDOCTO,KDSFXO,KDLITM,KDMCU,KDAN8,KDTRDJ,KDVR01,KDDSC1,KDDSC2,DRDL01,KDPRP3,KDUOM,KDUOM2,KDPRRC,KDUORG,KDSQOR,KDURAT,CRPDTA.NTOD(KDDRQJ) KDDRQJ from PRODDTA.FE64311D@jdedblink,PRODCTL.F0005@jdedblink where  DRSY='41' and DRRT='P3' and DRKY=KDPRP3 and KDPOHP01=0 and KDPOHP02='N' and KDKCOO='00010' and KDOCTO='OB' and KDUKID='" + 
      ukid + "'";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("wlbm", rs.getString("KDLITM"));
        result.put("wlmc", rs.getString("KDDSC1"));
        result.put("ggxh", rs.getString("KDDSC2"));
        result.put("cdpp", rs.getString("DRDL01"));
        result.put("fbcs", rs.getString("KDMCU").trim());
        result.put("gys", rs.getString("KDAN8").trim());
        result.put("ddrq", rs.getString("KDTRDJ").trim());
        result.put("dw", rs.getString("KDUOM").trim());
        result.put("dw2", rs.getString("KDUOM2").trim());
        result.put("mxbz", rs.getString("KDVR01"));
        result.put("dj", String.valueOf(rs.getLong("KDPRRC") / 10000.0D));
        result.put("sl2", String.valueOf(rs.getLong("KDSQOR") / 10000.0D));
        result.put("sl", String.valueOf(rs.getLong("KDUORG") / 10000.0D));
        result.put("je", String.valueOf(rs.getLong("KDURAT") / 100.0D));
        result.put("jhrq", rs.getString("KDDRQJ").substring(0, 10));
        result.put("ukzj", rs.getString("KDUKID").trim());
        result.put("ddlx", rs.getString("KDDCTO").trim());
        result.put("ddgs", rs.getString("KDKCOO").trim());
        result.put("ddh", rs.getString("KDDOCO").trim());
        result.put("ddhz", rs.getString("KDSFXO").trim());
        result.put("mxlx", rs.getString("KDOCTO").trim());
        datas.add(result);
      } 
      rs.close();
      pstmt.close();
      conn.close();
      if (datas.size() > 0)
        for (Map<String, String> map : datas) {
          String ckj = GetHiStoryForOP.getCkjInfo(((String)map.get("wlbm")).trim(), map.get("fbcs"), map.get("gys"), map.get("ddrq"), map.get("ddlx"));
          if (ckj != null)
            map.put("ckj", ckj); 
        }  
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return datas;
  }
  
  public void startProcess() {
    List<Map<String, String>> op = getFieldValueForOP();
    List<Map<String, String>> opmx = new ArrayList<Map<String, String>>();
    List<Map<String, String>> formBaseInfo = getBaseinfo();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String printTime = sdf.format(new Date());
    Map<String, String> info = new HashMap<String, String>();
    for (int i = 0; i < formBaseInfo.size(); i++) {
      if (((String)((Map)formBaseInfo.get(i)).get("formid")).equals("OBOPDG"))
        info = formBaseInfo.get(i); 
    } 
    if (op != null && op.size() > 0) {
      String result = "-1";
      for (Map<String, String> opmap : op) {
        String ukid = opmap.get("ukzj");
        opmx = getFieldvalueForOPmx(ukid);
        if (judgeForm(opmx).equals("OBOP"))
          try {
            float totalnum = 0.0F;
            double totalCorrectNum = 0.0D;
            double totalCorrectMoney = 0.0D;
            float totalmoney = 0.0F;
            for (Map<String, String> mxmap : opmx) {
              if (((String)opmap.get("ukzj")).equals(mxmap.get("ukzj")) && ((String)opmap.get("ddlx")).equals(mxmap.get("ddlx")) && ((String)opmap.get("ddgs")).equals(mxmap.get("ddgs")) && ((String)opmap.get("ddh")).equals(mxmap.get("ddh")) && ((String)opmap.get("ddhz")).equals(mxmap.get("ddhz"))) {
                if (!((String)mxmap.get("dw")).equals(mxmap.get("dw2")) && ((String)mxmap.get("dw2")).equals("KG")) {
                  totalnum += Float.valueOf(mxmap.get("sl2")).floatValue();
                } else {
                  totalnum += Float.valueOf(mxmap.get("sl")).floatValue();
                } 
                totalmoney += Float.valueOf(mxmap.get("je")).floatValue();
              } 
            } 
            totalCorrectNum = Math.round(totalnum * 10000.0D) / 100000.0D;
            totalCorrectMoney = Math.round(totalmoney) * 100.0D / 100.0D;
            String ddgs = opmap.get("ddgs");
            String jsr = "";
            if (ddgs.equals("00010"))
              jsr = info.get("receiveName"); 
            StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            xml.append("<WorkFlow>");
            xml.append("<Process processId=\"").append(info.get("processId")).append("\"/>");
            xml.append("<UserName  submitName=\"").append(((String)opmap.get("zdr")).trim()).append("\"  receiveName=\"").append(jsr).append("\"/>");
            xml.append("<Data>");
            xml.append("<Table tableId=\"").append(info.get("tableId")).append("\" tableName=\"").append(info.get("tableName")).append("\">");
            xml.append("<Column>");
            xml.append("<field name=\"dysj\" type=\"varchar\">").append(printTime).append("</field>");
            xml.append("<field name=\"qgdh\" type=\"varchar\">").append(((String)opmap.get("qgdh")).trim()).append("</field>");
            xml.append("<field name=\"gys\" type=\"varchar\">").append(((String)opmap.get("gys")).trim()).append("</field>");
            xml.append("<field name=\"lxr\" type=\"varchar\">").append(((String)opmap.get("lxr")).trim()).append("</field>");
            xml.append("<field name=\"lxrdh\" type=\"varchar\">").append(((String)opmap.get("lxrdh")).trim()).append("</field>");
            xml.append("<field name=\"cgy\" type=\"varchar\">").append(((String)opmap.get("cgy")).trim()).append("</field>");
            xml.append("<field name=\"lxrcz\" type=\"varchar\">").append(((String)opmap.get("lxrcz")).trim()).append("</field>");
            xml.append("<field name=\"cgycz\" type=\"varchar\">").append(((String)opmap.get("cgycz")).trim()).append("</field>");
            xml.append("<field name=\"cgydh\" type=\"varchar\">").append(((String)opmap.get("cgydh")).trim()).append("</field>");
            xml.append("<field name=\"cgdh\" type=\"varchar\">").append(((String)opmap.get("cgdh")).trim()).append("</field>");
            xml.append("<field name=\"ddrq\" type=\"varchar\">").append(((String)opmap.get("ddrq")).trim()).append("</field>");
            xml.append("<field name=\"dz\" type=\"varchar\">").append(((String)opmap.get("dz")).trim()).append("</field>");
            xml.append("<field name=\"bz\" type=\"varchar\">").append(((String)opmap.get("bz")).trim()).append("</field>");
            xml.append("<field name=\"hjsl\" type=\"varchar\">").append(totalCorrectNum).append("</field>");
            xml.append("<field name=\"zkje\" type=\"varchar\">").append(((String)opmap.get("zkje")).trim()).append("</field>");
            xml.append("<field name=\"fjf\" type=\"varchar\">").append(((String)opmap.get("fjf")).trim()).append("</field>");
            xml.append("<field name=\"zje\" type=\"varchar\">").append(((String)opmap.get("zje")).trim()).append("</field>");
            xml.append("<field name=\"zzs\" type=\"varchar\">").append(((String)opmap.get("zzs")).trim()).append("</field>");
            xml.append("<field name=\"fkfs\" type=\"varchar\">").append(((String)opmap.get("fkfs")).trim()).append("</field>");
            xml.append("<field name=\"fplx\" type=\"varchar\">").append(((String)opmap.get("fplx")).trim()).append("</field>");
            xml.append("<field name=\"shdz\" type=\"varchar\">").append(((String)opmap.get("shdz")).trim()).append("</field>");
            xml.append("<field name=\"zdr\" type=\"varchar\">").append(((String)opmap.get("zdrmc")).trim()).append("</field>");
            xml.append("<field name=\"ukzj\" type=\"varchar\">").append(((String)opmap.get("ukzj")).trim()).append("</field>");
            xml.append("<field name=\"ddlx\" type=\"varchar\">").append(((String)opmap.get("ddlx")).trim()).append("</field>");
            xml.append("<field name=\"ddgs\" type=\"varchar\">").append(((String)opmap.get("ddgs")).trim()).append("</field>");
            xml.append("<field name=\"ddh\" type=\"varchar\">").append(((String)opmap.get("ddh")).trim()).append("</field>");
            xml.append("<field name=\"ddhz\" type=\"varchar\">").append(((String)opmap.get("ddhz")).trim()).append("</field>");
            xml.append("</Column>");
            xml.append("</Table>");
            int num = 0;
            for (Map<String, String> mxmap : opmx) {
              if (((String)opmap.get("ukzj")).equals(mxmap.get("ukzj")) && ((String)opmap.get("ddlx")).equals(mxmap.get("ddlx")) && ((String)opmap.get("ddgs")).equals(mxmap.get("ddgs")) && ((String)opmap.get("ddh")).equals(mxmap.get("ddh")) && ((String)opmap.get("ddhz")).equals(mxmap.get("ddhz"))) {
                num++;
                String dw = "";
                String sl = "";
                if (!((String)mxmap.get("dw")).equals(mxmap.get("dw2")) && ((String)mxmap.get("dw2")).equals("KG")) {
                  dw = GetPersoninfo.getDwName(mxmap.get("dw2"));
                  sl = mxmap.get("sl2");
                } else {
                  dw = GetPersoninfo.getDwName(mxmap.get("dw"));
                  sl = mxmap.get("sl");
                } 
                String ggxh = ((String)mxmap.get("ggxh")).trim();
                if (ggxh.indexOf("\"") >= 0) {
                  ggxh = ggxh.replaceAll("\"", "");
                } else if (ggxh.indexOf("&") >= 0) {
                  ggxh = ggxh.replaceAll("&", "");
                } 
                String wlmc = ((String)mxmap.get("wlmc")).trim();
                if (wlmc.indexOf("\"") >= 0) {
                  wlmc = wlmc.replaceAll("\"", "");
                } else if (wlmc.indexOf("&") >= 0) {
                  wlmc = wlmc.replaceAll("&", "");
                } 
                xml.append("<SubTable tableName=\"").append(info.get("subTableName")).append("\" type=\"").append(String.valueOf(num) + "\">");
                xml.append("<Column>");
                xml.append("<field name=\"wlbm\" type=\"varchar\">").append(((String)mxmap.get("wlbm")).trim()).append("</field>");
                xml.append("<field name=\"wlmc\" type=\"varchar\">").append(wlmc).append("</field>");
                xml.append("<field name=\"ggxh\" type=\"varchar\">").append(ggxh).append("</field>");
                xml.append("<field name=\"cdpp\" type=\"varchar\">").append(((String)mxmap.get("cdpp")).trim()).append("</field>");
                xml.append("<field name=\"dw\" type=\"varchar\">").append(dw).append("</field>");
                xml.append("<field name=\"mxbz\" type=\"varchar\">").append(((String)mxmap.get("mxbz")).trim()).append("</field>");
                xml.append("<field name=\"dj\" type=\"varchar\">").append(((String)mxmap.get("dj")).trim()).append("</field>");
                xml.append("<field name=\"kf_cgdobopmx_f6355\" type=\"varchar\">").append(((String)mxmap.get("ckj")).trim()).append("</field>");
                xml.append("<field name=\"sl\" type=\"varchar\">").append(sl).append("</field>");
                xml.append("<field name=\"je\" type=\"varchar\">").append(((String)mxmap.get("je")).trim()).append("</field>");
                xml.append("<field name=\"jhrq\" type=\"varchar\">").append(((String)mxmap.get("jhrq")).trim()).append("</field>");
                xml.append("<field name=\"ukzj\" type=\"varchar\">").append(((String)mxmap.get("ukzj")).trim()).append("</field>");
                xml.append("</Column>");
                xml.append("</SubTable>");
              } 
            } 
            xml.append("</Data>");
            xml.append("</WorkFlow>");
            String flowxml = xml.toString();
            if (flowxml.indexOf("SubTable") > 0) {
              try {
                result = (new ClientInfoFromWeb()).createNewProcess(xml.toString());
              } catch (Exception e) {
                e.printStackTrace();
              } 
              if (!result.equals("-1") && result.length() < 10) {
                System.out.println("时间：[" + printTime + "]创建采购单OBOP（东莞）成功！--单号是：" + (String)opmap.get("ddh"));
                continue;
              } 
              if (result.equals("-1")) {
                System.out.println("时间：[" + printTime + "]创建采购单OBOP（东莞）失败！--单号是：" + (String)opmap.get("ddh"));
                String sql = "update PRODDTA.FE64311H@jdedblink set KHNXTR=' ' where KHUKID=?";
                DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
                PreparedStatement pstmt = null;
                Connection conn = null;
                try {
                  conn = ds.getConnection();
                  pstmt = conn.prepareStatement(sql);
                  pstmt.setString(1, ukid);
                  pstmt.executeUpdate();
                  pstmt.close();
                  conn.close();
                } catch (SQLException e1) {
                  if (conn != null)
                    try {
                      conn.close();
                    } catch (Exception err) {
                      err.printStackTrace();
                    }  
                  e1.printStackTrace();
                } 
              } 
            } 
          } catch (Exception e) {
            e.printStackTrace();
          }  
      } 
    } 
  }
  
  public String judgeForm(List<Map<String, String>> mx) {
    String flag = "";
    for (Map<String, String> mxmap : mx) {
      if (((String)mxmap.get("mxlx")).equals("OB")) {
        flag = "OBOP";
        break;
      } 
    } 
    return flag;
  }
  
  public List<Map<String, String>> getBaseinfo() {
    List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/jde.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> formNodes = root.getChildren();
      for (int i = 0; i < formNodes.size(); i++) {
        Element node = formNodes.get(i);
        Map<String, String> nodeMap = new HashMap<String, String>();
        nodeMap.put("formid", node.getAttributeValue("id"));
        if (((String)nodeMap.get("formid")).equals("OBOPDG")) {
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
          nodeMap.put("receiveName", node.getAttributeValue("receiveName"));
          nodeMap.put("subTableName", node.getAttributeValue("subTableName"));
          infos.add(nodeMap);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infos;
  }
}
