package com.js.doc.doc.service.gzkgToZeXin;

import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataPushToZeXin {
  private Map<String, String> getSendFileData(String key) {
    Map<String, String> result = new HashMap<String, String>();
    String sendSql = "select DOCUMENTSENDFILE_BYTENUMBER,DOCUMENTSENDFILE_PRINTNUMBER,DOCUMENTSENDFILE_title,DOCUMENTSENDFILE_topicword,DOCUMENTSENDFILE_SECURITYGRADE,sendfile_accessaydesc,ACCESSORYNAME,ACCESSORYSAVENAME,SENDFILE_TABLEID,sendfile_goldgridId,documentsendfile_wordType from DOC_DOCUMENTSENDFILE WHERE DOCUMENTSENDFILE_ID='" + Long.valueOf(key) + "'";
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery(sendSql);
      if (rs.next()) {
        result.put("GWWH", rs.getString("DOCUMENTSENDFILE_BYTENUMBER"));
        result.put("YZFS", rs.getString("DOCUMENTSENDFILE_PRINTNUMBER"));
        result.put("GWBT", rs.getString("DOCUMENTSENDFILE_title"));
        result.put("ZTC", rs.getString("DOCUMENTSENDFILE_topicword"));
        result.put("GWMJ", rs.getString("DOCUMENTSENDFILE_SECURITYGRADE"));
        result.put("BZ", rs.getString("sendfile_accessaydesc"));
        result.put("fjm", rs.getString("ACCESSORYNAME"));
        result.put("fjv", rs.getString("ACCESSORYSAVENAME"));
        result.put("tableid", rs.getString("SENDFILE_TABLEID"));
        result.put("zwmc", rs.getString("sendfile_goldgridId"));
        result.put("zwlx", rs.getString("documentsendfile_wordType"));
      } 
      rs.close();
      String rqSql = "select workcreatedate,workdonewithdate from jsf_work where workstatus=100 and workrecord_id='" + Long.valueOf(key) + "'";
      ResultSet rqRs = base.executeQuery(rqSql);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      if (rqRs.next()) {
        String workcreatedate = rqRs.getString("workcreatedate");
        String workdonewithdate = rqRs.getString("workdonewithdate");
        if (workcreatedate != null && !"".equals(workcreatedate) && !"null".equals(workcreatedate))
          result.put("CJRQ", sdf.format((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(workcreatedate))); 
        if (workdonewithdate != null && !"".equals(workdonewithdate) && !"null".equals(workdonewithdate))
          result.put("BBRQ", sdf.format((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(workdonewithdate))); 
        result.put("DJRQ", sdf.format(new Date()));
      } 
      rqRs.close();
      String qfSql = "select empname from org_employee where emp_id in (select dealwithemployee_id from jsf_dealwithcomment where wf_dealwith_id in (select wf_dealwith_id from jsf_dealwith where  databasetable_id='" + 
        (String)result.get("tableid") + "' and databaserecord_id='" + Long.valueOf(key) + "') and commentfield in (select gffName from form_controlfield where gffDisplayName='签发' and govFormId='" + (String)result.get("tableid") + "' and gffType='0'))";
      ResultSet qfRs = base.executeQuery(qfSql);
      if (qfRs.next())
        result.put("QFR", qfRs.getString("empname")); 
      qfRs.close();
      String ffSql = "select emp_id from doc_sendfile_user where sendfile_id='" + Long.valueOf(key) + "'";
      ResultSet ffRs = base.executeQuery(ffSql);
      result.put("SFFF", "0");
      if (ffRs.next())
        result.put("SFFF", "1"); 
      ffRs.close();
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean sendFilePush(String key, String path) {
    Map<String, String> sendFileMap = getSendFileData(key);
    String uuid = UUID.randomUUID().toString().toUpperCase();
    Connection conn = getConnection();
    StringBuffer sql = new StringBuffer("INSERT INTO OA_Bumf (ID,SFBZ,GWWH,BBRQ,YZFS,DJRQ,LWWH,GWBT,QFR,ZTC,GWMJ, SFFF, BZ, CJRQ, IsNew) VALUES (");
    sql.append("'" + uuid + "',");
    sql.append("'0','");
    sql.append((sendFileMap.get("GWWH") == null || "null".equalsIgnoreCase(sendFileMap.get("GWWH"))) ? "" : (String.valueOf(sendFileMap.get("GWWH")) + "','"));
    sql.append((sendFileMap.get("BBRQ") == null || "null".equalsIgnoreCase(sendFileMap.get("BBRQ"))) ? "" : (String.valueOf(sendFileMap.get("BBRQ")) + "','"));
    sql.append((sendFileMap.get("YZFS") == null || "null".equalsIgnoreCase(sendFileMap.get("YZFS"))) ? "" : (String.valueOf(sendFileMap.get("YZFS")) + "','"));
    sql.append((sendFileMap.get("DJRQ") == null || "null".equalsIgnoreCase(sendFileMap.get("DJRQ"))) ? "" : (String.valueOf(sendFileMap.get("DJRQ")) + "','"));
    sql.append("','");
    sql.append((sendFileMap.get("GWBT") == null || "null".equalsIgnoreCase(sendFileMap.get("GWBT"))) ? "" : (String.valueOf(sendFileMap.get("GWBT")) + "','"));
    sql.append((sendFileMap.get("QFR") == null || "null".equalsIgnoreCase(sendFileMap.get("QFR"))) ? "" : (String.valueOf(sendFileMap.get("QFR")) + "','"));
    sql.append((sendFileMap.get("ZTC") == null || "null".equalsIgnoreCase(sendFileMap.get("ZTC"))) ? "" : (String.valueOf(sendFileMap.get("ZTC")) + "','"));
    sql.append((sendFileMap.get("GWMJ") == null || "null".equalsIgnoreCase(sendFileMap.get("GWMJ"))) ? "" : (String.valueOf(sendFileMap.get("GWMJ")) + "','"));
    sql.append((sendFileMap.get("SFFF") == null || "null".equalsIgnoreCase(sendFileMap.get("SFFF"))) ? "" : (String.valueOf(sendFileMap.get("SFFF")) + "','"));
    sql.append((sendFileMap.get("BZ") == null || "null".equalsIgnoreCase(sendFileMap.get("BZ"))) ? "" : (String.valueOf(sendFileMap.get("BZ")) + "','"));
    sql.append((sendFileMap.get("CJRQ") == null || "null".equalsIgnoreCase(sendFileMap.get("CJRQ"))) ? "" : (String.valueOf(sendFileMap.get("CJRQ")) + "','"));
    sql.append("0')");
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql.toString());
      System.out.println("插入泽信中间表主表数据成功");
      if (sendFileMap.get("zwmc") == null || "null".equalsIgnoreCase(sendFileMap.get("zwmc"))) {
        System.out.println("无正文信息插入从表");
      } else {
        String wjmc = ((String)sendFileMap.get("zwmc")).trim();
        String wjgs = sendFileMap.get("zwlx");
        if (sendFileMap.get("zwlx") == null || "null".equalsIgnoreCase(sendFileMap.get("zwlx")))
          wjgs = ".doc"; 
        String subSql = "insert into OA_NearByInfo(DOCID,FILENAME,FILETYPE,FILEVALUE,CJRQ,CONTENTFLAG,FileContent) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(subSql);
        ps.setString(1, uuid.toString());
        ps.setString(2, String.valueOf(wjmc) + wjgs);
        ps.setString(3, getMimeType(wjgs.substring(1)));
        ps.setString(4, "");
        ps.setString(5, "");
        ps.setString(6, "0");
        String wjlj = String.valueOf(path) + File.separator + "upload" + File.separator + "govdocumentmanager" + File.separator + wjmc + wjgs;
        InputStream in = new FileInputStream(wjlj);
        ps.setBinaryStream(7, in, in.available());
        ps.executeUpdate();
        ps.close();
        System.out.println("正文信息插入从表成功");
      } 
      if (sendFileMap.get("fjv") == null || "null".equalsIgnoreCase(sendFileMap.get("fjv")) || "".equalsIgnoreCase(sendFileMap.get("fjv"))) {
        System.out.println("无附件信息插入从表");
      } else {
        String[] fjmcs = ((String)sendFileMap.get("fjm")).split("\\|");
        String[] fjvals = ((String)sendFileMap.get("fjv")).split("\\|");
        if (fjvals.length > 0) {
          for (int i = 0; i < fjvals.length; i++) {
            String wjmc = fjmcs[i].substring(fjmcs[i].indexOf(".") + 1, fjmcs[i].lastIndexOf("."));
            String wjgs = fjmcs[i].substring(fjmcs[i].lastIndexOf(".") + 1);
            String year = fjvals[i].substring(0, 4);
            String subSql = "insert into OA_NearByInfo(DOCID,FILENAME,FILETYPE,FILEVALUE,CJRQ,CONTENTFLAG,FileContent) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(subSql);
            ps.setString(1, uuid.toString());
            ps.setString(2, String.valueOf(wjmc) + "." + wjgs);
            ps.setString(3, getMimeType(wjgs));
            ps.setString(4, "");
            ps.setString(5, "");
            ps.setString(6, "1");
            String wjlj = String.valueOf(path) + File.separator + "upload" + File.separator + year + File.separator + "govdocumentmanager" + File.separator + fjvals[i];
            InputStream in = new FileInputStream(wjlj);
            ps.setBinaryStream(7, in, in.available());
            ps.executeUpdate();
            ps.close();
          } 
          System.out.println("附件信息插入从表成功");
        } 
      } 
      closeConnection(conn);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    updateFileStatus(key, "send");
    return true;
  }
  
  public boolean receiveFilePush(String key, String path) {
    Map<String, String> receMap = getReceiveFileData(key);
    String uuid = UUID.randomUUID().toString().toUpperCase();
    Connection conn = getConnection();
    StringBuffer sql = new StringBuffer("INSERT INTO OA_Bumf (ID,SFBZ,GWWH,BBRQ,YZFS,DJRQ,LWWH,GWBT,QFR,ZTC,GWMJ, SFFF, BZ, CJRQ, IsNew) VALUES (");
    sql.append("'" + uuid + "',");
    sql.append("'1','");
    sql.append((receMap.get("GWWH") == null || "null".equalsIgnoreCase(receMap.get("GWWH"))) ? "" : (String.valueOf(receMap.get("GWWH")) + "','"));
    sql.append("','");
    sql.append((receMap.get("YZFS") == null || "null".equalsIgnoreCase(receMap.get("YZFS"))) ? "" : (String.valueOf(receMap.get("YZFS")) + "','"));
    sql.append((receMap.get("DJRQ") == null || "null".equalsIgnoreCase(receMap.get("DJRQ"))) ? "" : (String.valueOf(receMap.get("DJRQ")) + "','"));
    sql.append((receMap.get("LWWH") == null || "null".equalsIgnoreCase(receMap.get("LWWH"))) ? "" : (String.valueOf(receMap.get("LWWH")) + "','"));
    sql.append((receMap.get("GWBT") == null || "null".equalsIgnoreCase(receMap.get("GWBT"))) ? "" : (String.valueOf(receMap.get("GWBT")) + "','"));
    sql.append("','");
    sql.append("','");
    sql.append("','");
    sql.append("','");
    sql.append("','");
    sql.append((receMap.get("CJRQ") == null || "null".equalsIgnoreCase(receMap.get("CJRQ"))) ? "" : (String.valueOf(receMap.get("CJRQ")) + "','"));
    sql.append("0')");
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql.toString());
      System.out.println("插入泽信中间表主表数据成功");
      if (receMap.get("zwv") == null || "null".equalsIgnoreCase(receMap.get("zwv")) || "".equalsIgnoreCase(receMap.get("zwv"))) {
        System.out.println("无正文信息插入从表");
      } else {
        String[] zwms = ((String)receMap.get("zwm")).split("\\|");
        String[] zwvs = ((String)receMap.get("zwv")).split("\\|");
        for (int i = 0; i < zwvs.length; i++) {
          String wjmc = zwms[i].substring(0, zwms[i].lastIndexOf("."));
          String wjgs = zwms[i].substring(zwms[i].lastIndexOf(".") + 1);
          String year = zwvs[i].substring(0, 4);
          String subSql = "insert into OA_NearByInfo(DOCID,FILENAME,FILETYPE,FILEVALUE,CJRQ,CONTENTFLAG,FileContent) VALUES(?,?,?,?,?,?,?)";
          PreparedStatement ps = conn.prepareStatement(subSql);
          ps.setString(1, uuid.toString());
          ps.setString(2, zwms[i]);
          ps.setString(3, getMimeType(wjgs));
          ps.setString(4, "");
          ps.setString(5, "");
          ps.setString(6, "0");
          String wjlj = String.valueOf(path) + File.separator + "upload" + File.separator + year + File.separator + "govdocumentmanager" + File.separator + zwvs[i];
          InputStream in = new FileInputStream(wjlj);
          ps.setBinaryStream(7, in, in.available());
          ps.executeUpdate();
          ps.close();
        } 
        System.out.println("正文信息插入从表成功");
      } 
      if (receMap.get("fjv") == null || "null".equalsIgnoreCase(receMap.get("fjv")) || "".equalsIgnoreCase(receMap.get("fjv"))) {
        System.out.println("无附件信息插入从表");
      } else {
        String[] fjmcs = ((String)receMap.get("fjm")).split("\\|");
        String[] fjvals = ((String)receMap.get("fjv")).split("\\|");
        for (int i = 0; i < fjvals.length; i++) {
          String wjmc = fjmcs[i].substring(0, fjmcs[i].lastIndexOf("."));
          String wjgs = fjmcs[i].substring(fjmcs[i].lastIndexOf(".") + 1);
          String year = fjvals[i].substring(0, 4);
          String subSql = "insert into OA_NearByInfo(DOCID,FILENAME,FILETYPE,FILEVALUE,CJRQ,CONTENTFLAG,FileContent) VALUES(?,?,?,?,?,?,?)";
          PreparedStatement ps = conn.prepareStatement(subSql);
          ps.setString(1, uuid.toString());
          ps.setString(2, fjmcs[i]);
          ps.setString(3, getMimeType(wjgs));
          ps.setString(4, "");
          ps.setString(5, "");
          ps.setString(6, "1");
          String wjlj = String.valueOf(path) + File.separator + "upload" + File.separator + year + File.separator + "govdocumentmanager" + File.separator + fjvals[i];
          InputStream in = new FileInputStream(wjlj);
          ps.setBinaryStream(7, in, in.available());
          ps.executeUpdate();
          ps.close();
        } 
        System.out.println("附件信息插入从表成功");
      } 
      closeConnection(conn);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    updateFileStatus(key, "receive");
    return true;
  }
  
  private Connection getConnection() {
    Connection conn = null;
    try {
      Context initContext = new InitialContext();
      Context envContext = (Context)initContext.lookup("java:/comp/env");
      DataSource ds = (DataSource)envContext.lookup("jdbc/zexin");
      conn = ds.getConnection();
      System.out.println("成功连接数据库！");
    } catch (NamingException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  private void closeConnection(Connection conn) {
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }  
  }
  
  private void updateFileStatus(String key, String type) {
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      String sql = "";
      if (type.equals("send")) {
        sql = "update DOC_DOCUMENTSENDFILE set sendFile_syncForward='1' where DOCUMENTSENDFILE_ID='" + Long.valueOf(key) + "'";
      } else if (type.equals("receive")) {
        sql = "update doc_receivefile set receive_syncForward='1' where RECEIVEFILE_ID='" + Long.valueOf(key) + "'";
      } 
      if (sql.length() > 1)
        base.executeSQL(sql); 
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
  }
  
  private Map<String, String> getReceiveFileData(String key) {
    Map<String, String> result = new HashMap<String, String>();
    String sendSql = "select RECEIVEFILE_FILENUMBER,FIELD6,RECEIVEFILE_QUANTITY,RECEIVEFILE_TITLE,accessorysavename_file,accessoryname_file,accessorysavename,accessoryname,RECEIVEFILE_receivedate from doc_receivefile where receivefile_id='" + Long.valueOf(key) + "'";
    DataSourceBase base = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery(sendSql);
      if (rs.next()) {
        result.put("GWWH", rs.getString("RECEIVEFILE_FILENUMBER"));
        result.put("LWWH", rs.getString("FIELD6"));
        result.put("YZFS", rs.getString("RECEIVEFILE_QUANTITY"));
        result.put("GWBT", rs.getString("RECEIVEFILE_TITLE"));
        String receivedate = rs.getString("RECEIVEFILE_receivedate");
        if (receivedate != null && !"".equals(receivedate) && !"null".equals(receivedate))
          result.put("CJRQ", sdf.format((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(receivedate))); 
        result.put("zwv", rs.getString("accessorysavename"));
        result.put("zwm", rs.getString("accessoryname"));
        result.put("fjv", rs.getString("accessorysavename_file"));
        result.put("fjm", rs.getString("accessoryname_file"));
      } 
      rs.close();
      result.put("DJRQ", sdf.format(new Date()));
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  private String getMimeType(String wjhz) {
    if ("css".equals(wjhz))
      return "text/css"; 
    if ("dll".equals(wjhz))
      return "application/x-msdownload"; 
    if ("doc".equals(wjhz))
      return "application/msword"; 
    if ("exe".equals(wjhz))
      return "application/octet-stream"; 
    if ("gif".equals(wjhz))
      return "image/gif"; 
    if ("htc".equals(wjhz))
      return "text/x-component"; 
    if ("htm".equals(wjhz) || "html".equals(wjhz))
      return "text/html"; 
    if ("jpe".equals(wjhz) || "jpeg".equals(wjhz) || "jpg".equals(wjhz))
      return "image/jpeg"; 
    if ("js".equals(wjhz))
      return "application/x-javascript"; 
    if ("mht".equals(wjhz) || "mhtml".equals(wjhz))
      return "message/rfc822 "; 
    if ("mp2".equals(wjhz) || "mp3".equals(wjhz) || "mpa".equals(wjhz) || "mpe".equals(wjhz) || "mpeg".equals(wjhz) || "mpg".equals(wjhz))
      return "video/mpeg"; 
    if ("pdf".equals(wjhz))
      return "application/pdf"; 
    if ("pot".equals(wjhz) || "pps".equals(wjhz) || "ppt".equals(wjhz))
      return "application/vnd.ms-powerpoint"; 
    if ("svg".equals(wjhz))
      return "image/svg+xml"; 
    if ("swf".equals(wjhz))
      return "application/x-shockwave-flash"; 
    if ("txt".equals(wjhz))
      return "text/plain"; 
    if ("wav".equals(wjhz))
      return "audio/x-wav"; 
    if ("xla".equals(wjhz) || "xlc".equals(wjhz) || "xlm".equals(wjhz) || "xls".equals(wjhz) || "xlt".equals(wjhz) || "xlw".equals(wjhz))
      return "application/vnd.ms-excel"; 
    if ("zip".equals(wjhz))
      return "application/zip"; 
    return "application/octet-stream";
  }
}
