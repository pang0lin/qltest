package com.js.oa.jsflow.util;

import com.js.db.GetConnection;
import com.js.util.util.ClobUtils;
import com.js.util.util.DataSourceBase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import oracle.sql.BLOB;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class DocToArchive {
  public String insertFileTable_send(HttpServletRequest httpServletRequest) {
    String returnValue = "0";
    String bodyContent = httpServletRequest.getParameter("bodyContent");
    String path1 = httpServletRequest.getContextPath();
    String basePath = String.valueOf(httpServletRequest.getScheme()) + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + path1 + "/";
    bodyContent = "<html><head><title>发文归档</title></head><link href=" + 
      basePath + "skin/blue/style.css rel=stylesheet type=text/css />" + 
      "<script src=" + basePath + "js/js.js language=javascript></script>" + 
      "<link rel=stylesheet type=text/css href=" + basePath + "public/date_picker/DateObject2.css>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/DateObject.js></SCRIPT>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/DatePicker.js></SCRIPT>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/editlib.js></SCRIPT>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/tree.js></SCRIPT>" + 
      "<script language=javascript src=" + basePath + "js/openEndow.js></script>" + 
      "<script language=javascript src=" + basePath + "js/checkForm.js></script>" + 
      "<script language=javascript src=" + basePath + "eform/datetime/datetime_check.js></script>" + 
      "<script language=javascript src=" + basePath + "eform/datetime/datetime_select.js></script>" + 
      
      "<body leftmargin=0 topmargin=0 >" + bodyContent + "</body></html>";
    String content = (httpServletRequest.getParameter("content") == null) ? "" : httpServletRequest.getParameter("content");
    String[] saveFiles = httpServletRequest.getParameterValues("accessorySaveName");
    String[] saveFilesName = httpServletRequest.getParameterValues("accessoryName");
    String recordId = httpServletRequest.getParameter("recordId");
    String realPath = httpServletRequest.getRealPath("");
    String processName = httpServletRequest.getParameter("processName");
    HttpSession session = httpServletRequest.getSession(true);
    String guidangren = session.getAttribute("userName").toString();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs_seq = null;
    ResultSet rs_seqBase = null;
    String fileName = "";
    for (int i = 0; saveFilesName != null && i < saveFilesName.length; i++)
      fileName = String.valueOf(fileName) + saveFilesName[i]; 
    Map resultMap = getSendResult(recordId);
    Map columnMap = getSendColumn();
    StringBuffer sql_insertBase = new StringBuffer("insert into T_SFW(id,");
    sql_insertBase.append(columnMap.get("WENHAO").toString()).append(",");
    sql_insertBase.append(columnMap.get("BIAOTI").toString()).append(",");
    sql_insertBase.append(columnMap.get("JIGUANDAIZI").toString()).append(",");
    sql_insertBase.append(columnMap.get("ZHUTICI").toString()).append(",");
    sql_insertBase.append(columnMap.get("NIGAODANWEI").toString()).append(",");
    sql_insertBase.append(columnMap.get("NIGAOREN").toString()).append(",");
    sql_insertBase.append(columnMap.get("YINFARIQI").toString()).append(",");
    sql_insertBase.append(columnMap.get("QIANFAREN").toString()).append(",");
    sql_insertBase.append(columnMap.get("FUJIANBIAOTI").toString()).append(",");
    sql_insertBase.append(columnMap.get("LIUCHENGMINGCHENG").toString()).append(",");
    sql_insertBase.append("Sfbs,");
    sql_insertBase.append("FLAG,");
    sql_insertBase.append("YEAR,");
    sql_insertBase.append("INSERTDATE,");
    sql_insertBase.append(columnMap.get("GUIDANGREN").toString()).append(") values (seq_sfw.nextVal,");
    SimpleDateFormat matter1 = new SimpleDateFormat("yyyy");
    SimpleDateFormat matter2 = new SimpleDateFormat("yyyy-MM-dd");
    sql_insertBase.append("'" + resultMap.get("WENHAO").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("BIAOTI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("JIGUANDAIZI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("ZHUTICI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("NIGAODANWEI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("NIGAOREN").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("YINFARIQI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("QIANFAREN").toString()).append("',");
    sql_insertBase.append("'" + fileName).append("',");
    sql_insertBase.append("'" + processName).append("',");
    sql_insertBase.append("'f',");
    sql_insertBase.append("'',");
    sql_insertBase.append("'" + matter1.format(new Date()) + "',");
    sql_insertBase.append("'" + matter2.format(new Date()) + "',");
    sql_insertBase.append("'" + resultMap.get("GUIDANGREN")).append("')");
    String sql_seqBase = "select seq_sfw.currval from dual";
    String sql = "";
    String sql_seq = "select seq_sfwyw.currval from dual";
    try {
      conn = GetConnection.getConnection();
      boolean status = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      String sdfsd = sql_insertBase.toString();
      stmt.executeUpdate(sql_insertBase.toString());
      rs_seqBase = stmt.executeQuery(sql_seqBase);
      rs_seqBase.next();
      String id_base = rs_seqBase.getString(1);
      String sql_insert = "insert into t_sfwyw values(seq_sfwyw.nextVal,'" + id_base + "','" + processName + ".html',EMPTY_BLOB(),'处理单')";
      stmt.executeUpdate(sql_insert);
      rs_seq = stmt.executeQuery(sql_seq);
      rs_seq.next();
      String id = rs_seq.getString(1);
      sql = "select wjnr from t_sfwyw where ywid='" + id + "' for update";
      rs = stmt.executeQuery(sql);
      BLOB osc = null;
      if (rs.next()) {
        osc = ClobUtils.blobToChange(rs.getBlob("wjnr"));
        OutputStream o = osc.getBinaryOutputStream();
        byte[] contentByte = bodyContent.getBytes();
        o.write(contentByte, 0, contentByte.length);
        o.flush();
        o.close();
      } 
      rs.close();
      rs_seq.close();
      ResultSet rs_seqZW = null;
      ResultSet rs_ZW = null;
      String sql_insertZW = "insert into t_sfwyw values(seq_sfwyw.nextVal,'" + id_base + "','" + processName + "_正文.doc',EMPTY_BLOB(),'正文')";
      stmt.executeUpdate(sql_insertZW);
      rs_seqZW = stmt.executeQuery(sql_seq);
      rs_seqZW.next();
      String id_ZW = rs_seqZW.getString(1);
      sql = "select wjnr from t_sfwyw where ywid='" + id_ZW + "' for update";
      rs_ZW = stmt.executeQuery(sql);
      BLOB osc_ZW = null;
      if (rs_ZW.next()) {
        osc_ZW = ClobUtils.blobToChange(rs_ZW.getBlob("wjnr"));
        String srcTr = "0000";
        if (content != null && content.length() > 6 && content.substring(4, 5).equals("_")) {
          srcTr = content.substring(0, 4);
        } else {
          srcTr = "0000";
        } 
        FileInputStream inStream = new FileInputStream(String.valueOf(realPath) + "//upload//" + srcTr + "//govdocumentmanager//" + content + ".doc");
        OutputStream outStream = osc_ZW.getBinaryOutputStream();
        byte[] buf = new byte[10240];
        int len;
        while ((len = inStream.read(buf)) > 0)
          outStream.write(buf, 0, len); 
        outStream.flush();
        outStream.close();
        inStream.close();
      } 
      rs_seqZW.close();
      rs_ZW.close();
      for (int j = 0; saveFiles != null && j < saveFiles.length; j++) {
        ResultSet rs_seqFJ = null;
        ResultSet rs_FJ = null;
        String sql_insertFJ = "insert into t_sfwyw values(seq_sfwyw.nextVal,'" + 
          id_base + "','" + saveFilesName[j] + "',EMPTY_BLOB(),'附件')";
        stmt.executeUpdate(sql_insertFJ);
        rs_seqFJ = stmt.executeQuery(sql_seq);
        rs_seqFJ.next();
        String id_FJ = rs_seqFJ.getString(1);
        sql = "select wjnr from t_sfwyw where ywid='" + id_FJ + 
          "' for update";
        rs_FJ = stmt.executeQuery(sql);
        BLOB osc_FJ = null;
        if (rs_FJ.next()) {
          osc_FJ = ClobUtils.blobToChange(rs_FJ.getBlob("wjnr"));
          String srcTr = "0000";
          if (saveFiles[j] != null && saveFiles[j].length() > 6 && saveFiles[j].substring(4, 5) == "_") {
            srcTr = saveFiles[j].substring(0, 4);
          } else {
            srcTr = "0000";
          } 
          FileInputStream inStream = new FileInputStream(String.valueOf(realPath) + 
              "//upload//" + srcTr + "//govdocumentmanager//" + saveFiles[j]);
          OutputStream outStream = osc_FJ.getBinaryOutputStream();
          byte[] buf = new byte[10240];
          int len;
          while ((len = inStream.read(buf)) > 0)
            outStream.write(buf, 0, len); 
          outStream.flush();
          outStream.close();
          inStream.close();
        } 
        rs_seqFJ.close();
        rs_FJ.close();
      } 
      rs.close();
      rs_seq.close();
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status);
      conn.close();
      returnValue = "1";
    } catch (Exception e) {
      try {
        e.printStackTrace();
        conn.rollback();
        conn.close();
      } catch (SQLException err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return returnValue;
  }
  
  public String insertFileTable_receive(HttpServletRequest httpServletRequest) {
    String returnValue = "0";
    String bodyContent = httpServletRequest.getParameter("bodyContent");
    String path1 = httpServletRequest.getContextPath();
    String basePath = String.valueOf(httpServletRequest.getScheme()) + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + path1 + "/";
    bodyContent = "<html><head><title>收文归档</title></head><link href=" + 
      basePath + "skin/blue/style.css rel=stylesheet type=text/css />" + 
      "<script src=" + basePath + "js/js.js language=javascript></script>" + 
      "<link rel=stylesheet type=text/css href=" + basePath + "public/date_picker/DateObject2.css>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/DateObject.js></SCRIPT>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/DatePicker.js></SCRIPT>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/editlib.js></SCRIPT>" + 
      "<SCRIPT language=javascript src=" + basePath + "public/date_picker/tree.js></SCRIPT>" + 
      "<script language=javascript src=" + basePath + "js/openEndow.js></script>" + 
      "<script language=javascript src=" + basePath + "js/checkForm.js></script>" + 
      "<script language=javascript src=" + basePath + "eform/datetime/datetime_check.js></script>" + 
      "<script language=javascript src=" + basePath + "eform/datetime/datetime_select.js></script>" + 
      
      "<body leftmargin=0 topmargin=0 >" + bodyContent + "</body></html>";
    String[] saveFiles = httpServletRequest.getParameterValues("accessorySaveName1");
    String[] saveFilesName = httpServletRequest.getParameterValues("accessoryName1");
    String[] saveFilesFJ = httpServletRequest.getParameterValues("accessorySaveName2");
    String[] saveFilesNameFJ = httpServletRequest.getParameterValues("accessoryName2");
    String recordId = httpServletRequest.getParameter("recordId");
    String processName = httpServletRequest.getParameter("processName");
    HttpSession session = httpServletRequest.getSession(true);
    String guidangren = session.getAttribute("userName").toString();
    String realPath = httpServletRequest.getRealPath("");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs_seq = null;
    ResultSet rs_seqBase = null;
    String fileName = "";
    for (int i = 0; saveFilesNameFJ != null && i < saveFilesNameFJ.length; i++)
      fileName = String.valueOf(fileName) + saveFilesNameFJ[i]; 
    String zhengwen = "";
    for (int j = 0; saveFilesName != null && j < saveFilesName.length; j++)
      zhengwen = String.valueOf(zhengwen) + saveFilesName[j]; 
    Map resultMap = getReceiveResult(recordId);
    Map columnMap = getReceiveColumn();
    StringBuffer sql_insertBase = new StringBuffer("insert into T_SFW(id,");
    sql_insertBase.append(columnMap.get("WENHAO").toString()).append(",");
    sql_insertBase.append(columnMap.get("BIAOTI").toString()).append(",");
    sql_insertBase.append(columnMap.get("LAIWENRIQI").toString()).append(",");
    sql_insertBase.append(columnMap.get("LAIWENDANWEI").toString()).append(",");
    sql_insertBase.append(columnMap.get("FUJIANBIAOTI").toString()).append(",");
    sql_insertBase.append(columnMap.get("ZHENGWEN").toString()).append(",");
    sql_insertBase.append(columnMap.get("LIUCHENGMINGCHENG").toString()).append(",");
    sql_insertBase.append("Sfbs,");
    sql_insertBase.append("FLAG,");
    sql_insertBase.append("YEAR,");
    sql_insertBase.append("INSERTDATE,");
    sql_insertBase.append(columnMap.get("GUIDANGREN").toString()).append(") values (seq_sfw.nextVal,");
    SimpleDateFormat matter1 = new SimpleDateFormat("yyyy");
    SimpleDateFormat matter2 = new SimpleDateFormat("yyyy-MM-dd");
    sql_insertBase.append("'" + resultMap.get("WENHAO").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("BIAOTI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("LAIWENRIQI").toString()).append("',");
    sql_insertBase.append("'" + resultMap.get("LAIWENDANWEI").toString()).append("',");
    sql_insertBase.append("'" + fileName).append("',");
    sql_insertBase.append("'" + zhengwen).append("',");
    sql_insertBase.append("'" + processName).append("',");
    sql_insertBase.append("'s',");
    sql_insertBase.append("'',");
    sql_insertBase.append("'" + matter1.format(new Date()) + "',");
    sql_insertBase.append("'" + matter2.format(new Date()) + "',");
    sql_insertBase.append("'" + resultMap.get("GUIDANGREN")).append("')");
    String sql_seqBase = "select seq_sfw.currval from dual";
    String sql = "";
    String sql_seq = "select seq_sfwyw.currval from dual";
    try {
      conn = GetConnection.getConnection();
      boolean status = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql_insertBase.toString());
      rs_seqBase = stmt.executeQuery(sql_seqBase);
      rs_seqBase.next();
      String id_base = rs_seqBase.getString(1);
      String sql_insert = "insert into t_sfwyw values(seq_sfwyw.nextVal,'" + id_base + "','" + processName + ".html',EMPTY_BLOB(),'处理单')";
      stmt.executeUpdate(sql_insert);
      rs_seq = stmt.executeQuery(sql_seq);
      rs_seq.next();
      String id = rs_seq.getString(1);
      sql = "select wjnr from t_sfwyw where ywid='" + id + "' for update";
      rs = stmt.executeQuery(sql);
      BLOB osc = null;
      if (rs.next()) {
        osc = ClobUtils.blobToChange(rs.getBlob("wjnr"));
        OutputStream o = osc.getBinaryOutputStream();
        byte[] contentByte = bodyContent.getBytes();
        o.write(contentByte, 0, contentByte.length);
        o.flush();
        o.close();
      } 
      rs.close();
      rs_seq.close();
      int k;
      for (k = 0; saveFiles != null && k < saveFiles.length; k++) {
        ResultSet rs_seqZW = null;
        ResultSet rs_ZW = null;
        String sql_insertZW = "insert into t_sfwyw values(seq_sfwyw.nextVal,'" + 
          id_base + "','" + saveFilesName[k] + "',EMPTY_BLOB(),'正文')";
        stmt.executeUpdate(sql_insertZW);
        rs_seqZW = stmt.executeQuery(sql_seq);
        rs_seqZW.next();
        String id_ZW = rs_seqZW.getString(1);
        sql = "select wjnr from t_sfwyw where ywid='" + id_ZW + 
          "' for update";
        rs_ZW = stmt.executeQuery(sql);
        BLOB osc_ZW = null;
        if (rs_ZW.next()) {
          osc_ZW = ClobUtils.blobToChange(rs_ZW.getBlob("wjnr"));
          String srcTr = "0000";
          if (saveFiles[k] != null && saveFiles[k].length() > 6 && saveFiles[k].substring(4, 5).equals("_")) {
            srcTr = saveFiles[k].substring(0, 4);
          } else {
            srcTr = "0000";
          } 
          FileInputStream inStream = new FileInputStream(String.valueOf(realPath) + 
              "//upload//" + srcTr + "//govdocumentmanager//" + saveFiles[k]);
          OutputStream outStream = osc_ZW.getBinaryOutputStream();
          byte[] buf = new byte[10240];
          int len;
          while ((len = inStream.read(buf)) > 0)
            outStream.write(buf, 0, len); 
          outStream.flush();
          outStream.close();
          inStream.close();
        } 
        rs_seqZW.close();
        rs_ZW.close();
      } 
      for (k = 0; saveFilesFJ != null && k < saveFilesFJ.length; k++) {
        ResultSet rs_seqFJ = null;
        ResultSet rs_FJ = null;
        String sql_insertFJ = "insert into t_sfwyw values(seq_sfwyw.nextVal,'" + 
          id_base + "','" + saveFilesNameFJ[k] + "',EMPTY_BLOB(),'附件')";
        stmt.executeUpdate(sql_insertFJ);
        rs_seqFJ = stmt.executeQuery(sql_seq);
        rs_seqFJ.next();
        String id_FJ = rs_seqFJ.getString(1);
        sql = "select wjnr from t_sfwyw where ywid='" + id_FJ + 
          "' for update";
        rs_FJ = stmt.executeQuery(sql);
        BLOB osc_FJ = null;
        if (rs_FJ.next())
          try {
            osc_FJ = ClobUtils.blobToChange(rs_FJ.getBlob("wjnr"));
            String srcTr = "0000";
            if (saveFilesFJ[k] != null && saveFilesFJ[k].length() > 6 && saveFilesFJ[k].substring(4, 5).equals("_")) {
              srcTr = saveFilesFJ[k].substring(0, 4);
            } else {
              srcTr = "0000";
            } 
            FileInputStream inStream = new FileInputStream(String.valueOf(realPath) + 
                "/upload/" + srcTr + "/govdocumentmanager/" + 
                saveFilesFJ[k]);
            OutputStream outStream = osc_FJ.getBinaryOutputStream();
            byte[] buf = new byte[10240];
            int len;
            while ((len = inStream.read(buf)) > 0)
              outStream.write(buf, 0, len); 
            outStream.flush();
            outStream.close();
            inStream.close();
          } catch (Exception e) {
            e.printStackTrace();
          }  
        rs_seqFJ.close();
        rs_FJ.close();
      } 
      rs.close();
      rs_seq.close();
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status);
      conn.close();
      returnValue = "1";
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.rollback();
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return returnValue;
  }
  
  private Map getSendColumn() {
    String path = System.getProperty("user.dir");
    Map<Object, Object> map = new HashMap<Object, Object>();
    String filePath = String.valueOf(path) + "/jsconfig/docArchives.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("sendTable");
    if (WorkUse != null) {
      String wenhao = WorkUse.getChild("WENHAO").getAttribute("archiveColumn").getValue();
      if (wenhao != null)
        map.put("WENHAO", wenhao); 
      String biaoti = WorkUse.getChild("BIAOTI").getAttribute("archiveColumn").getValue();
      if (biaoti != null)
        map.put("BIAOTI", biaoti); 
      String jiguandaizi = WorkUse.getChild("JIGUANDAIZI").getAttribute("archiveColumn").getValue();
      if (jiguandaizi != null)
        map.put("JIGUANDAIZI", jiguandaizi); 
      String zhutici = WorkUse.getChild("ZHUTICI").getAttribute("archiveColumn").getValue();
      if (zhutici != null)
        map.put("ZHUTICI", zhutici); 
      String nigaodanwei = WorkUse.getChild("NIGAODANWEI").getAttribute("archiveColumn").getValue();
      if (nigaodanwei != null)
        map.put("NIGAODANWEI", nigaodanwei); 
      String nigaoren = WorkUse.getChild("NIGAOREN").getAttribute("archiveColumn").getValue();
      if (nigaoren != null)
        map.put("NIGAOREN", nigaoren); 
      String yinfariqi = WorkUse.getChild("YINFARIQI").getAttribute("archiveColumn").getValue();
      if (yinfariqi != null)
        map.put("YINFARIQI", yinfariqi); 
      String qianfaren = WorkUse.getChild("QIANFAREN").getAttribute("archiveColumn").getValue();
      if (qianfaren != null)
        map.put("QIANFAREN", qianfaren); 
      String fujianbiaoti = WorkUse.getChild("FUJIANBIAOTI").getAttribute("archiveColumn").getValue();
      if (fujianbiaoti != null)
        map.put("FUJIANBIAOTI", fujianbiaoti); 
      String liuchengmingcheng = WorkUse.getChild("LIUCHENGMINGCHENG").getAttribute("archiveColumn").getValue();
      if (liuchengmingcheng != null)
        map.put("LIUCHENGMINGCHENG", liuchengmingcheng); 
      String guidangren = WorkUse.getChild("GUIDANGREN").getAttribute("archiveColumn").getValue();
      if (guidangren != null)
        map.put("GUIDANGREN", guidangren); 
    } 
    return map;
  }
  
  private Map getSendResult(String id) {
    String path = System.getProperty("user.dir");
    Map<Object, Object> map = new HashMap<Object, Object>();
    String filePath = String.valueOf(path) + "/jsconfig/docArchives.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    StringBuffer buffer = new StringBuffer("select DOCUMENTSENDFILE_ID");
    Element WorkUse = doc.getRootElement().getChild("sendTable");
    if (WorkUse != null) {
      String wenhao = WorkUse.getChild("WENHAO").getAttribute("oaColumn").getValue();
      if (wenhao != null)
        buffer.append("," + wenhao); 
      String biaoti = WorkUse.getChild("BIAOTI").getAttribute("oaColumn").getValue();
      if (biaoti != null)
        buffer.append("," + biaoti); 
      String jiguandaizi = WorkUse.getChild("JIGUANDAIZI").getAttribute("oaColumn").getValue();
      if (jiguandaizi != null)
        buffer.append("," + jiguandaizi); 
      String zhutici = WorkUse.getChild("ZHUTICI").getAttribute("oaColumn").getValue();
      if (zhutici != null)
        buffer.append("," + zhutici); 
      String nigaodanwei = WorkUse.getChild("NIGAODANWEI").getAttribute("oaColumn").getValue();
      if (nigaodanwei != null)
        buffer.append("," + nigaodanwei); 
      String nigaoren = WorkUse.getChild("NIGAOREN").getAttribute("oaColumn").getValue();
      if (nigaoren != null)
        buffer.append("," + nigaoren); 
      String yinfariqi = WorkUse.getChild("YINFARIQI").getAttribute("oaColumn").getValue();
      if (yinfariqi != null)
        buffer.append("," + yinfariqi); 
      String qianfaren = WorkUse.getChild("QIANFAREN").getAttribute("oaColumn").getValue();
      if (qianfaren != null)
        buffer.append("," + qianfaren); 
      String guidangman = WorkUse.getChild("GUIDANGREN").getAttribute("oaColumn").getValue();
      if (guidangman != null)
        buffer.append("," + guidangman); 
      buffer.append(" from DOC_DOCUMENTSENDFILE where DOCUMENTSENDFILE_ID=" + id);
    } 
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(buffer.toString());
      if (rs.next()) {
        map.put("WENHAO", (rs.getString(2) != null) ? rs.getString(2) : "");
        map.put("BIAOTI", (rs.getString(3) != null) ? rs.getString(3) : "");
        map.put("JIGUANDAIZI", (rs.getString(4) != null) ? rs.getString(4) : "");
        map.put("ZHUTICI", (rs.getString(5) != null) ? rs.getString(5) : "");
        map.put("NIGAODANWEI", (rs.getString(6) != null) ? rs.getString(6) : "");
        map.put("NIGAOREN", (rs.getString(7) != null) ? rs.getString(7) : "");
        map.put("YINFARIQI", (rs.getString(8) != null) ? rs.getString(8) : "");
        map.put("QIANFAREN", (rs.getString(9) != null) ? rs.getString(9) : "");
        map.put("GUIDANGREN", (rs.getString(10) != null) ? rs.getString(10) : "");
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return map;
  }
  
  private Map getReceiveColumn() {
    String path = System.getProperty("user.dir");
    Map<Object, Object> map = new HashMap<Object, Object>();
    String filePath = String.valueOf(path) + "/jsconfig/docArchives.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("receiveTable");
    if (WorkUse != null) {
      String wenhao = WorkUse.getChild("WENHAO").getAttribute("archiveColumn").getValue();
      if (wenhao != null)
        map.put("WENHAO", wenhao); 
      String biaoti = WorkUse.getChild("BIAOTI").getAttribute("archiveColumn").getValue();
      if (biaoti != null)
        map.put("BIAOTI", biaoti); 
      String laiwenriqi = WorkUse.getChild("LAIWENRIQI").getAttribute("archiveColumn").getValue();
      if (laiwenriqi != null)
        map.put("LAIWENRIQI", laiwenriqi); 
      String laiwendanwei = WorkUse.getChild("LAIWENDANWEI").getAttribute("archiveColumn").getValue();
      if (laiwendanwei != null)
        map.put("LAIWENDANWEI", laiwendanwei); 
      String fujianbiaoti = WorkUse.getChild("FUJIANBIAOTI").getAttribute("archiveColumn").getValue();
      if (fujianbiaoti != null)
        map.put("FUJIANBIAOTI", fujianbiaoti); 
      String zhengwen = WorkUse.getChild("ZHENGWEN").getAttribute("archiveColumn").getValue();
      if (zhengwen != null)
        map.put("ZHENGWEN", zhengwen); 
      String liuchengmingcheng = WorkUse.getChild("LIUCHENGMINGCHENG").getAttribute("archiveColumn").getValue();
      if (liuchengmingcheng != null)
        map.put("LIUCHENGMINGCHENG", liuchengmingcheng); 
      String guidangren = WorkUse.getChild("GUIDANGREN").getAttribute("archiveColumn").getValue();
      if (guidangren != null)
        map.put("GUIDANGREN", guidangren); 
    } 
    return map;
  }
  
  private Map getReceiveResult(String id) {
    String path = System.getProperty("user.dir");
    Map<Object, Object> map = new HashMap<Object, Object>();
    String filePath = String.valueOf(path) + "/jsconfig/docArchives.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    StringBuffer buffer = new StringBuffer("select RECEIVEFILE_ID");
    Element WorkUse = doc.getRootElement().getChild("receiveTable");
    if (WorkUse != null) {
      String wenhao = WorkUse.getChild("WENHAO").getAttribute("oaColumn").getValue();
      if (wenhao != null)
        buffer.append("," + wenhao); 
      String biaoti = WorkUse.getChild("BIAOTI").getAttribute("oaColumn").getValue();
      if (biaoti != null)
        buffer.append("," + biaoti); 
      String laiwenriqi = WorkUse.getChild("LAIWENRIQI").getAttribute("oaColumn").getValue();
      if (laiwenriqi != null)
        buffer.append("," + laiwenriqi); 
      String laiwendanwei = WorkUse.getChild("LAIWENDANWEI").getAttribute("oaColumn").getValue();
      if (laiwendanwei != null)
        buffer.append("," + laiwendanwei); 
      String guidangman = WorkUse.getChild("GUIDANGREN").getAttribute("oaColumn").getValue();
      if (guidangman != null)
        buffer.append("," + guidangman); 
      buffer.append(" from DOC_RECEIVEFILE where RECEIVEFILE_ID=" + id);
    } 
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(buffer.toString());
      if (rs.next()) {
        map.put("WENHAO", (rs.getString(2) != null) ? rs.getString(2) : "");
        map.put("BIAOTI", (rs.getString(3) != null) ? rs.getString(3) : "");
        map.put("LAIWENRIQI", (rs.getString(4) != null) ? rs.getString(4) : "");
        map.put("LAIWENDANWEI", (rs.getString(5) != null) ? rs.getString(5) : "");
        map.put("GUIDANGREN", (rs.getString(6) != null) ? rs.getString(6) : "");
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return map;
  }
}
