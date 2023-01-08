package com.js.oa.jsflow.util;

import com.js.db.GetConnection;
import com.js.util.util.ClobUtils;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import oracle.sql.BLOB;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class WorkflowToArchive {
  public static String archiveProcessIds;
  
  public static String docArchive;
  
  private static void init() {
    try {
      archiveProcessIds = ",";
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/workflowArchives.xml";
      FileInputStream configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> flowList = root.getChildren("flowData");
      for (int i = 0; i < flowList.size(); i++) {
        Element node = flowList.get(i);
        archiveProcessIds = String.valueOf(archiveProcessIds) + node.getAttributeValue("processId") + ",";
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private static void initDocArchive() {
    try {
      docArchive = "";
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/docArchives.xml";
      FileInputStream configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("NeedDocArchive");
      docArchive = node.getAttributeValue("use");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String getArchiveProcessIds() {
    return archiveProcessIds;
  }
  
  public static boolean isNeedArchive(String processId) {
    if (archiveProcessIds == null)
      init(); 
    if (archiveProcessIds.indexOf("," + processId + ",") >= 0)
      return true; 
    return false;
  }
  
  public static boolean isNeedDocArchive() {
    if (docArchive == null)
      initDocArchive(); 
    if ("1".equals(docArchive))
      return true; 
    return false;
  }
  
  public String transToArchive(HttpServletRequest request) {
    String returnValue = "0";
    String bodyContent = request.getParameter("bodyContent");
    String path1 = request.getContextPath();
    String basePath = String.valueOf(request.getScheme()) + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
    String realPath = request.getRealPath("");
    HttpSession session = request.getSession(true);
    String guidangren = session.getAttribute("userName").toString();
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String processName = request.getParameter("processName");
    String processId = request.getParameter("processId");
    bodyContent = "<html><head><title>" + processName + "归档</title></head>" + 
      "<link href=" + basePath + "skin/blue/style.css rel=stylesheet type=text/css />" + 
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
    Map map = getTableColumn(processId);
    String[][] transFlowData = (String[][])map.get("transFlowData");
    if (transFlowData != null && transFlowData.length > 0) {
      String flowTable = map.get("flowTable").toString();
      String archiveTable = map.get("archiveTable").toString();
      String archiveID = map.get("archiveID").toString();
      boolean hasProjectName = false;
      StringBuffer selSql = new StringBuffer("select ");
      for (int i = 0; i < transFlowData.length; i++) {
        if (i == 0) {
          if ("".equals(transFlowData[i][2])) {
            selSql.append(transFlowData[i][0]);
          } else if ("workflowProcessName".equals(transFlowData[i][2])) {
            selSql.append(flowTable).append("_id");
          } else if ("relationProjectName".equals(transFlowData[i][2])) {
            selSql.append(flowTable).append("_id");
            hasProjectName = true;
          } 
        } else if ("".equals(transFlowData[i][2])) {
          selSql.append(",").append(transFlowData[i][0]);
        } else if ("workflowProcessName".equals(transFlowData[i][2])) {
          selSql.append(",").append(flowTable).append("_id");
        } else if ("relationProjectName".equals(transFlowData[i][2])) {
          selSql.append(",").append(flowTable).append("_id");
          hasProjectName = true;
        } 
      } 
      selSql.append(" from ").append(flowTable)
        .append(" where ").append(flowTable).append("_id=").append(recordId);
      StringBuffer insSql = new StringBuffer("insert into ");
      insSql.append(archiveTable).append(" (").append(archiveID);
      int j;
      for (j = 0; j < transFlowData.length; j++)
        insSql.append(",").append(transFlowData[j][1]); 
      insSql.append(") values(?");
      for (j = 0; j < transFlowData.length; j++)
        insSql.append(",?"); 
      insSql.append(")");
      Connection daConn = null;
      Connection oaConn = null;
      try {
        oaConn = (new DataSourceBase()).getDataSource().getConnection();
        daConn = GetConnection.getConnection();
        boolean daConnStatus = daConn.getAutoCommit();
        daConn.setAutoCommit(false);
        Statement oaStmt = oaConn.createStatement();
        Statement daStmt = daConn.createStatement();
        PreparedStatement pstmt = null;
        String projectName = "";
        if (hasProjectName) {
          ResultSet resultSet = oaStmt.executeQuery("select relproject_id from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + recordId + " and relproject_id>0");
          if (resultSet.next()) {
            projectName = resultSet.getString(1);
            resultSet.close();
            resultSet = oaStmt.executeQuery("select title from pro_body where id=" + projectName);
            if (resultSet.next())
              projectName = resultSet.getString(1); 
            resultSet.close();
          } else {
            resultSet.close();
          } 
        } 
        ResultSet rs = daStmt.executeQuery("select seq_sfw.nextVal from dual");
        rs.next();
        String fId = rs.getString(1);
        rs.close();
        rs = oaStmt.executeQuery(selSql.toString());
        if (rs.next()) {
          pstmt = daConn.prepareStatement(insSql.toString());
          pstmt.setString(1, fId);
          for (int k = 0; k < transFlowData.length; k++) {
            pstmt.setString(k + 2, rs.getString(k + 1));
            if ("".equals(transFlowData[k][2])) {
              if ("fjbt".equalsIgnoreCase(transFlowData[k][1])) {
                try {
                  if (rs.getString(k + 1) != null) {
                    String[] tt = rs.getString(k + 1).split(";");
                    pstmt.setString(k + 2, tt[1]);
                  } else {
                    pstmt.setString(k + 2, "");
                  } 
                } catch (Exception e) {
                  pstmt.setString(k + 2, "");
                } 
              } else {
                pstmt.setString(k + 2, rs.getString(k + 1));
              } 
            } else if ("workflowProcessName".equals(transFlowData[k][2])) {
              pstmt.setString(k + 2, processName);
            } else if ("relationProjectName".equals(transFlowData[k][2])) {
              pstmt.setString(k + 2, projectName);
            } 
          } 
          pstmt.executeUpdate();
        } 
        rs.close();
        String archiveYWTable = (map.get("archiveYWTable") == null) ? "" : map.get("archiveYWTable").toString();
        if (!"".equals(archiveYWTable)) {
          String ywFlowColumn = map.get("ywFlowColumn").toString();
          String ywFlowTextColumn = map.get("ywFlowTextColumn").toString();
          String ywIdColumn = map.get("ywID").toString();
          String ywPIdColumn = map.get("ywPID").toString();
          String ywWJMCColumn = map.get("ywWJMC").toString();
          String ywWJNRColumn = map.get("ywWJNR").toString();
          String ywFJLXColumn = map.get("ywFJLX").toString();
          rs = daStmt.executeQuery("select seq_sfwyw.nextVal from dual");
          rs.next();
          String ywId = rs.getString(1);
          rs.close();
          insSql = insSql.delete(0, insSql.length());
          insSql.append("insert into ").append(archiveYWTable).append(" (")
            .append(ywIdColumn).append(",")
            .append(ywPIdColumn).append(",")
            .append(ywWJMCColumn).append(",")
            .append(ywWJNRColumn).append(",")
            .append(ywFJLXColumn).append(") values(?,?,?,?,?)");
          pstmt = daConn.prepareStatement(insSql.toString());
          pstmt.setString(1, ywId);
          pstmt.setString(2, fId);
          pstmt.setString(3, String.valueOf(processName) + ".html");
          pstmt.setBlob(4, (Blob)BLOB.empty_lob());
          pstmt.setString(5, "表单样式");
          pstmt.executeUpdate();
          BLOB osc = null;
          rs = daStmt.executeQuery("select " + ywWJNRColumn + " from " + archiveYWTable + " where " + ywIdColumn + "=" + ywId + " for update");
          if (rs.next()) {
            osc = ClobUtils.blobToChange(rs.getBlob(ywWJNRColumn));
            OutputStream o = osc.getBinaryOutputStream();
            byte[] contentByte = bodyContent.getBytes();
            o.write(contentByte, 0, contentByte.length);
            o.flush();
            o.close();
          } 
          rs.close();
          if (!"".equals(ywFlowTextColumn)) {
            String fileName = "";
            rs = oaStmt.executeQuery("select " + ywFlowTextColumn + " from " + flowTable + " where " + flowTable + "_id=" + recordId);
            rs.next();
            fileName = rs.getString(1);
            rs.close();
            if (fileName != null && !"".equals(fileName) && !"null".equals(fileName)) {
              rs = daStmt.executeQuery("select seq_sfwyw.nextVal from dual");
              rs.next();
              ywId = rs.getString(1);
              rs.close();
              pstmt.setString(1, ywId);
              pstmt.setString(2, fId);
              pstmt.setString(3, "正文.doc");
              pstmt.setBlob(4, (Blob)BLOB.empty_lob());
              pstmt.setString(5, "正文");
              pstmt.executeUpdate();
              InputStream in = null;
              BLOB blob = null;
              rs = oaStmt.executeQuery("select filebody from document_file where recordId=" + fileName);
              if (rs.next()) {
                blob = ClobUtils.blobToChange(rs.getBlob(1));
                in = blob.getBinaryStream();
              } 
              rs.close();
              osc = null;
              rs = daStmt.executeQuery("select " + ywWJNRColumn + " from " + archiveYWTable + " where " + ywIdColumn + "=" + ywId + " for update");
              if (rs.next()) {
                osc = ClobUtils.blobToChange(rs.getBlob(ywWJNRColumn));
                OutputStream outStream = osc.getBinaryOutputStream();
                byte[] buf = new byte[10240];
                int len;
                while ((len = in.read(buf)) > 0)
                  outStream.write(buf, 0, len); 
                outStream.flush();
                outStream.close();
                in.close();
              } 
              rs.close();
            } 
          } 
          if (!"".equals(ywFlowColumn)) {
            rs = oaStmt.executeQuery("select " + ywFlowColumn + " from " + flowTable + " where " + flowTable + "_id=" + recordId);
            if (rs.next()) {
              String flowFile = rs.getString(1);
              rs.close();
              if (flowFile != null && !"".equals(flowFile) && !"null".equals(flowFile)) {
                String[] fileSaveName = flowFile.split(";")[0].split(",");
                String[] fileName = flowFile.split(";")[1].split(",");
                for (int k = 0; k < fileSaveName.length; k++) {
                  if (!"".equals(fileSaveName[k])) {
                    rs = daStmt.executeQuery("select seq_sfwyw.nextVal from dual");
                    rs.next();
                    ywId = rs.getString(1);
                    rs.close();
                    pstmt.setString(1, ywId);
                    pstmt.setString(2, fId);
                    pstmt.setString(3, fileName[k]);
                    pstmt.setBlob(4, (Blob)BLOB.empty_lob());
                    pstmt.setString(5, "附件");
                    pstmt.executeUpdate();
                    osc = null;
                    rs = daStmt.executeQuery("select " + ywWJNRColumn + " from " + archiveYWTable + " where " + ywIdColumn + "=" + ywId + " for update");
                    if (rs.next()) {
                      osc = ClobUtils.blobToChange(rs.getBlob(ywWJNRColumn));
                      String srcTr = "0000";
                      if (fileSaveName[k] != null && fileSaveName[k].length() > 6 && fileSaveName[k].substring(4, 5).equals("_")) {
                        srcTr = fileSaveName[k].substring(0, 4);
                      } else {
                        srcTr = "0000";
                      } 
                      FileInputStream inStream = new FileInputStream(String.valueOf(realPath) + "/upload/" + srcTr + "/customform/" + fileSaveName[k]);
                      OutputStream outStream = osc.getBinaryOutputStream();
                      byte[] buf = new byte[10240];
                      int len;
                      while ((len = inStream.read(buf)) > 0)
                        outStream.write(buf, 0, len); 
                      outStream.flush();
                      outStream.close();
                      inStream.close();
                    } 
                    rs.close();
                  } 
                } 
              } 
            } else {
              rs.close();
            } 
          } 
        } 
        oaStmt.close();
        daStmt.close();
        pstmt.close();
        daConn.commit();
        daConn.setAutoCommit(daConnStatus);
        daConn.close();
        oaConn.close();
        returnValue = "1";
      } catch (Exception ex) {
        if (daConn != null)
          try {
            daConn.rollback();
            daConn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (oaConn != null)
          try {
            oaConn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
    return returnValue;
  }
  
  private Map getTableColumn(String processId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/workflowArchives.xml";
      FileInputStream configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = null, tempNode = null;
      List<Element> flowList = root.getChildren("flowData");
      boolean hasSet = false;
      for (int i = 0; i < flowList.size(); i++) {
        node = flowList.get(i);
        if (processId.equals(node.getAttributeValue("processId"))) {
          hasSet = true;
          break;
        } 
      } 
      if (hasSet) {
        map.put("flowTable", node.getAttributeValue("flowTable"));
        tempNode = node.getChild("transFlowData");
        map.put("archiveTable", tempNode.getAttributeValue("archiveTable"));
        map.put("archiveID", tempNode.getAttributeValue("archiveID"));
        flowList.clear();
        flowList = tempNode.getChildren("column");
        String[][] columnArray = new String[flowList.size()][3];
        for (int j = 0; j < flowList.size(); j++) {
          tempNode = flowList.get(j);
          columnArray[j][0] = tempNode.getAttributeValue("flowColumn");
          columnArray[j][1] = tempNode.getAttributeValue("archiveColumn");
          columnArray[j][2] = tempNode.getAttributeValue("type");
        } 
        map.put("transFlowData", columnArray);
        tempNode = node.getChild("transYW");
        map.put("archiveYWTable", tempNode.getAttributeValue("archiveYWTable"));
        map.put("ywFlowColumn", tempNode.getChild("flowColumn").getAttributeValue("column"));
        map.put("ywFlowTextColumn", tempNode.getChild("flowTextColumn").getAttributeValue("column"));
        map.put("ywID", tempNode.getChild("YWID").getAttributeValue("column"));
        map.put("ywPID", tempNode.getChild("ID").getAttributeValue("column"));
        map.put("ywWJMC", tempNode.getChild("WJMC").getAttributeValue("column"));
        map.put("ywWJNR", tempNode.getChild("WJNR").getAttributeValue("column"));
        map.put("ywFJLX", tempNode.getChild("FJLX").getAttributeValue("column"));
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return map;
  }
}
