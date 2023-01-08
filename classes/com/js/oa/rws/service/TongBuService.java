package com.js.oa.rws.service;

import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.rws.util.FTPUtil;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SysConfigReader;
import com.js.util.util.CharacterTool;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.UUID;
import org.codehaus.xfire.client.Client;

public class TongBuService {
  public static void main(String[] args) {
    System.out.println(UUID.randomUUID());
  }
  
  public String tongbu(String workid, String recordid, String contentFilePath, String empid) {
    String result = "";
    StringBuffer dataSetXMLContent = new StringBuffer("");
    DbOpt db = new DbOpt();
    boolean tongbuOK = true;
    try {
      String pageId = db
        .executeQueryToStr("select worktable_id from jsf_work where wf_work_id=" + 
          workid);
      String tableName = db
        .executeQueryToStr("select area_table from tarea where area_name='form1' and page_id=" + 
          pageId);
      if ("".equals(tableName.trim())) {
        String processId = db
          .executeQueryToStr("select workprocess_id from jsf_work where workrecord_id=" + 
            recordid);
        pageId = db
          .executeQueryToStr("select accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=" + processId);
        tableName = db
          .executeQueryToStr("select area_table from tarea where area_name='form1' and page_id=" + 
            pageId);
      } 
      String tableId = db
        .executeQueryToStr("select table_id from ttable where table_name='" + 
          tableName + "'");
      String guid = "WorkFlow-" + System.currentTimeMillis();
      String endPoint = SysConfigReader.readConfigValue("RwsWebService", 
          "endPoint");
      String nameSpace = SysConfigReader.readConfigValue("RwsWebService", 
          "nameSpace");
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results = c.invoke("StartTransaction", new Object[0]);
        guid = results[0].toString();
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      String localPath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/rws/" + guid;
      File file = new File(String.valueOf(localPath) + "/Attachment");
      if (!file.exists())
        file.mkdirs(); 
      dataSetXMLContent
        .append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
      dataSetXMLContent.append("\n <DataPackage Importer='OA'>");
      dataSetXMLContent.append("\n <DataSets>");
      dataSetXMLContent
        .append("\n    <DataSet DSID='" + tableName + "'>");
      String[][] fields = db
        .executeQueryToStrArr2("select field_name,field_show from tfield where field_table=" + 
          tableId + " and field_show not in(115,116,117,113)");
      String querySQL = "select ";
      for (int i = 0; i < fields.length; i++) {
        if (i == 0) {
          querySQL = String.valueOf(querySQL) + fields[0][0];
        } else {
          querySQL = String.valueOf(querySQL) + "," + fields[i][0];
        } 
      } 
      querySQL = String.valueOf(querySQL) + " from " + tableName + " where " + tableName + "_ID=" + 
        recordid;
      String[][] recordContent = db.executeQueryToStrArr2(querySQL, 
          fields.length);
      String contentFIleId = UUID.randomUUID().toString();
      dataSetXMLContent.append("\n        <Row>");
      dataSetXMLContent.append("\n           <ID>" + contentFIleId + 
          "</ID>");
      for (int j = 0; j < fields.length; j++)
        dataSetXMLContent.append("\n           <" + fields[j][0] + ">" + 
            CharacterTool.replaceXMLTags(recordContent[0][j]) + "</" + fields[j][0] + ">"); 
      dataSetXMLContent.append("\n        </Row>");
      file = new File(String.valueOf(localPath) + "/Attachment" + "/" + contentFIleId);
      file.mkdirs();
      String newFilePath = String.valueOf(localPath) + 
        "/Attachment" + 
        "/" + 
        contentFIleId + 
        contentFilePath.substring(contentFilePath
          .lastIndexOf("/") + 
          contentFilePath.lastIndexOf("\\") + 1);
      copyFile(contentFilePath, newFilePath);
      fields = db
        .executeQueryToStrArr2("select field_name,field_show from tfield where field_table=" + 
          tableId + " and field_show =115");
      if (fields != null)
        for (int k = 0; k < fields.length; k++) {
          querySQL = "select " + fields[k][0];
          querySQL = String.valueOf(querySQL) + " from " + tableName + " where " + tableName + 
            "_ID=" + recordid;
          String uploadFileInfo = db.executeQueryToStr(querySQL);
          if (uploadFileInfo != null && 
            uploadFileInfo.indexOf(";") >= 0) {
            String fileStoreNameStr = uploadFileInfo.split(";")[0];
            String fileNameStr = uploadFileInfo.split(";")[1];
            String[] fileStoreNames = fileStoreNameStr.split(",");
            for (int m = 0; m < fileStoreNames.length; m++) {
              String fujianid = contentFIleId;
              String nf = fileStoreNames[m];
              if (nf.charAt(4) == '_') {
                nf = nf.substring(0, 4);
              } else {
                nf = "0000";
              } 
              String oldFilePath = 
                String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
                "upload/" + 
                nf + 
                "/customform/" + 
                fileStoreNames[m];
              String newFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + 
                fujianid + "/" + fileStoreNames[m];
              copyFile(oldFilePath, newFilePath1);
            } 
          } 
        }  
      dataSetXMLContent.append("\n     </DataSet>");
      dataSetXMLContent.append("\n   </DataSets>");
      dataSetXMLContent.append("\n </DataPackage>");
      String xmlFileName = String.valueOf(localPath) + "/DataSet.xml";
      File file1 = new File(xmlFileName);
      file1.delete();
      file1.createNewFile();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file1), "UTF-8"));
      writer.write(dataSetXMLContent.toString());
      writer.close();
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("StartUpload", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR StartUpLoad!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      FTPUtil.uploadAllFilesUnderPath(localPath, guid);
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c
          .invoke("EndUpload", new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR EndUpload!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("CommitTransaction", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR CommitTransaction!==" + 
              result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      if (tongbuOK)
        (new WorkFlowEJBBeanForRWS()).guidang(tableName, recordid, empid, 
            guid); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public String tongbuForSendDoc(String recordid, String contentFilePath, String empid) {
    String result = "";
    boolean tongbuOK = true;
    StringBuffer dataSetXMLContent = new StringBuffer("");
    DbOpt db = new DbOpt();
    try {
      String guid = "SendFile-" + System.currentTimeMillis();
      String endPoint = SysConfigReader.readConfigValue("RwsWebService", 
          "endPoint");
      String nameSpace = SysConfigReader.readConfigValue("RwsWebService", 
          "nameSpace");
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results = c.invoke("StartTransaction", new Object[0]);
        guid = results[0].toString();
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      String localPath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/rws/" + guid;
      File file = new File(String.valueOf(localPath) + "/Attachment");
      if (!file.exists())
        file.mkdirs(); 
      dataSetXMLContent
        .append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
      dataSetXMLContent.append("\n <DataPackage Importer='OA'>");
      dataSetXMLContent.append("\n <DataSets>");
      dataSetXMLContent.append("\n    <DataSet DSID='2'>");
      String[][] fields = { { "to_char(documentsendfile_senddate,'yyyy')", "nd" }, { "documentsendfile_senddate", "documentsendfile_senddate" }, { "documentsendfile_bytenumber", 
            "documentsendfile_bytenumber" }, { "documentsendfile_writeorg", "documentsendfile_writeorg" }, { "(select max(empname) from org_employee where emp_id=DOC_DOCUMENTSENDFILE.Createdemp)", 
            "ngr" }, { "documentsendfile_title", "documentsendfile_title" }, { "maintoname", "maintoname" } };
      String querySQL = "select ";
      for (int i = 0; i < fields.length; i++) {
        if (i == 0) {
          querySQL = String.valueOf(querySQL) + fields[0][0] + " " + fields[0][1];
        } else {
          querySQL = String.valueOf(querySQL) + "," + fields[i][0] + " " + fields[i][1];
        } 
      } 
      querySQL = String.valueOf(querySQL) + " from DOC_DOCUMENTSENDFILE where documentsendfile_id=" + 
        recordid;
      String[][] recordContent = db.executeQueryToStrArr2(querySQL, 
          fields.length);
      String contentFIleId = UUID.randomUUID().toString();
      dataSetXMLContent.append("\n        <Row>");
      dataSetXMLContent.append("\n           <ID>" + contentFIleId + 
          "</ID>");
      for (int j = 0; j < fields.length; j++)
        dataSetXMLContent.append("\n           <" + fields[j][1] + ">" + 
            CharacterTool.replaceXMLTags(recordContent[0][j]) + "</" + fields[j][1] + ">"); 
      dataSetXMLContent.append("\n        </Row>");
      file = new File(String.valueOf(localPath) + "/Attachment" + "/" + contentFIleId);
      file.mkdirs();
      String newFilePath = String.valueOf(localPath) + 
        "/Attachment" + 
        "/" + 
        contentFIleId + 
        contentFilePath.substring(contentFilePath
          .lastIndexOf("/") + 
          contentFilePath.lastIndexOf("\\") + 1);
      copyFile(contentFilePath, newFilePath);
      String fjs = db
        .executeQueryToStr("select accessorysavename from doc_documentsendfile where documentsendfile_id=" + 
          recordid);
      if (fjs.startsWith("|"))
        fjs = fjs.substring(1); 
      if (fjs.endsWith("|"))
        fjs = fjs.substring(0, fjs.length() - 1); 
      if (!"".equals(fjs)) {
        String[] fjList = fjs.split("\\|");
        for (int k = 0; k < fjList.length; k++) {
          String fj = contentFIleId;
          String nf = fjList[k];
          if (nf.charAt(4) == '_') {
            nf = nf.substring(0, 4);
          } else {
            nf = "0000";
          } 
          file = new File(String.valueOf(localPath) + "/Attachment" + "/" + fj);
          file.mkdirs();
          String oldFilePath = 
            String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
            "upload/" + 
            nf + 
            "/govdocumentmanager/" + 
            fjList[k];
          String newFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + fj + 
            "/" + fjList[k];
          copyFile(oldFilePath, newFilePath1);
        } 
      } 
      String sendFileName = db
        .executeQueryToStr("select sendfile_goldgridid from doc_documentsendfile where documentsendfile_id=" + 
          recordid);
      String dg = contentFIleId;
      File dgFile = new File(String.valueOf(localPath) + "/Attachment" + "/" + dg);
      dgFile.mkdirs();
      String dgFilePath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/" + 
        "/govdocumentmanager_his/" + 
        sendFileName + 
        ".doc";
      String dgFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + dg + "/" + 
        sendFileName + ".doc";
      copyFile(dgFilePath, dgFilePath1);
      dgFile = new File(String.valueOf(localPath) + "/Attachment" + "/" + dg);
      dgFile.mkdirs();
      dgFilePath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/" + 
        "/govdocumentmanager_his/" + 
        sendFileName + 
        ".doc";
      dgFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + dg + "/" + 
        sendFileName + ".doc";
      copyFile(dgFilePath, dgFilePath1);
      dataSetXMLContent.append("\n     </DataSet>");
      dataSetXMLContent.append("\n   </DataSets>");
      dataSetXMLContent.append("\n </DataPackage>");
      String xmlFileName = String.valueOf(localPath) + "/DataSet.xml";
      File file1 = new File(xmlFileName);
      file1.delete();
      file1.createNewFile();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file1), "UTF-8"));
      writer.write(dataSetXMLContent.toString());
      writer.close();
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("StartUpload", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR StartUpLoad!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      FTPUtil.uploadAllFilesUnderPath(localPath, guid);
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c
          .invoke("EndUpload", new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR EndUpload!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("CommitTransaction", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR CommitTransaction!==" + 
              result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      if (tongbuOK)
        (new WorkFlowEJBBeanForRWS()).guidang("DOC_DOCUMENTSENDFILE", 
            recordid, empid, guid); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public String tongbuForReceiveDoc(String recordid, String contentFilePath, String empid) {
    String result = "";
    boolean tongbuOK = true;
    StringBuffer dataSetXMLContent = new StringBuffer("");
    DbOpt db = new DbOpt();
    try {
      String guid = "local" + System.currentTimeMillis();
      String endPoint = SysConfigReader.readConfigValue("RwsWebService", 
          "endPoint");
      String nameSpace = SysConfigReader.readConfigValue("RwsWebService", 
          "nameSpace");
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results = c.invoke("StartTransaction", new Object[0]);
        guid = results[0].toString();
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      String localPath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/rws/" + guid;
      File file = new File(String.valueOf(localPath) + "/Attachment");
      if (!file.exists())
        file.mkdirs(); 
      dataSetXMLContent
        .append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
      dataSetXMLContent.append("\n <DataPackage Importer='OA'>");
      dataSetXMLContent.append("\n <DataSets>");
      dataSetXMLContent.append("\n    <DataSet DSID='3'>");
      String[][] fields = { { "zjkyseq", "zjkyseq" }, { "Receivefile_receivedate", "Receivefile_receivedate" }, { "Receivefile_filenumber", "Receivefile_filenumber" }, { "Receivefile_receivedate", "Receivefile_receivedate1" }, { "Receivefile_sendfileunit", "Receivefile_sendfileunit" }, { "Receivefile_title", "Receivefile_title" }, { "to_char(receivefile_receivedate,'yyyy')", "nd" }, { "(select max(orgname) from org_organization where org_id=DOC_receivefile.createdorg)", 
            "cbdw" } };
      String querySQL = "select ";
      for (int i = 0; i < fields.length; i++) {
        if (i == 0) {
          querySQL = String.valueOf(querySQL) + fields[0][0] + " " + fields[0][1];
        } else {
          querySQL = String.valueOf(querySQL) + "," + fields[i][0] + " " + fields[i][1];
        } 
      } 
      querySQL = String.valueOf(querySQL) + " from DOC_receivefile where receivefile_id=" + 
        recordid;
      String[][] recordContent = db.executeQueryToStrArr2(querySQL, 
          fields.length);
      String contentFIleId = UUID.randomUUID().toString();
      dataSetXMLContent.append("\n        <Row>");
      dataSetXMLContent.append("\n           <ID>" + contentFIleId + 
          "</ID>");
      for (int j = 0; j < fields.length; j++)
        dataSetXMLContent.append("\n           <" + fields[j][1] + ">" + 
            CharacterTool.replaceXMLTags(recordContent[0][j]) + "</" + fields[j][1] + ">"); 
      dataSetXMLContent.append("\n        </Row>");
      file = new File(String.valueOf(localPath) + "/Attachment" + "/" + contentFIleId);
      file.mkdirs();
      String newFilePath = String.valueOf(localPath) + 
        "/Attachment" + 
        "/" + 
        contentFIleId + 
        contentFilePath.substring(contentFilePath
          .lastIndexOf("/") + 
          contentFilePath.lastIndexOf("\\") + 1);
      copyFile(contentFilePath, newFilePath);
      String fjs = db
        .executeQueryToStr("select accessorysavename_file||'|'||accessorysavename from DOC_receivefile where receivefile_id=" + 
          recordid);
      if (fjs.startsWith("|"))
        fjs = fjs.substring(1); 
      if (fjs.endsWith("|"))
        fjs = fjs.substring(0, fjs.length() - 1); 
      if (!"".equals(fjs)) {
        String[] fjList = fjs.split("\\|");
        for (int k = 0; k < fjList.length; k++) {
          String fj = contentFIleId;
          String nf = fjList[k];
          if (nf.charAt(4) == '_') {
            nf = nf.substring(0, 4);
          } else {
            nf = "0000";
          } 
          file = new File(String.valueOf(localPath) + "/Attachment" + "/" + fj);
          file.mkdirs();
          String oldFilePath = 
            String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
            "upload/" + 
            nf + 
            "/govdocumentmanager/" + 
            fjList[k];
          String newFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + fj + 
            "/" + fjList[k];
          copyFile(oldFilePath, newFilePath1);
        } 
      } 
      dataSetXMLContent.append("\n     </DataSet>");
      dataSetXMLContent.append("\n   </DataSets>");
      dataSetXMLContent.append("\n </DataPackage>");
      String xmlFileName = String.valueOf(localPath) + "/DataSet.xml";
      try {
        FileWriter fw = new FileWriter(xmlFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataSetXMLContent.toString());
        bw.close();
        fw.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      File file1 = new File(xmlFileName);
      file1.delete();
      file1.createNewFile();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file1), "UTF-8"));
      writer.write(dataSetXMLContent.toString());
      writer.close();
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("StartUpload", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR StartUpLoad!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      FTPUtil.uploadAllFilesUnderPath(localPath, guid);
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c
          .invoke("EndUpload", new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR EndUpload!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("CommitTransaction", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR CommitTransaction!==" + 
              result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      if (tongbuOK)
        (new WorkFlowEJBBeanForRWS()).guidang("DOC_receivefile", recordid, 
            empid, guid); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public String tongbuForCooperate(String recordid, String contentFilePath, String empid) {
    boolean tongbuOK = true;
    String result = "";
    StringBuffer dataSetXMLContent = new StringBuffer("");
    DbOpt db = new DbOpt();
    try {
      String guid = "local" + System.currentTimeMillis();
      String endPoint = SysConfigReader.readConfigValue("RwsWebService", 
          "endPoint");
      String nameSpace = SysConfigReader.readConfigValue("RwsWebService", 
          "nameSpace");
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results = c.invoke("StartTransaction", new Object[0]);
        guid = results[0].toString();
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      String localPath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/rws/" + guid;
      File file = new File(String.valueOf(localPath) + "/Attachment");
      if (!file.exists())
        file.mkdirs(); 
      dataSetXMLContent
        .append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
      dataSetXMLContent.append("\n <DataPackage Importer='OA'>");
      dataSetXMLContent.append("\n <DataSets>");
      dataSetXMLContent.append("\n    <DataSet DSID='4'>");
      String[][] fields = { { "title", "title" }, { "posttime", "posttime" }, { "postername", "postername" }, { "postorgname", "postorgname" }, { "sendtoname", "sendtoname" } };
      String querySQL = "select ";
      for (int i = 0; i < fields.length; i++) {
        if (i == 0) {
          querySQL = String.valueOf(querySQL) + fields[0][0] + " " + fields[0][1];
        } else {
          querySQL = String.valueOf(querySQL) + "," + fields[i][0] + " " + fields[i][1];
        } 
      } 
      querySQL = String.valueOf(querySQL) + " from co_body where id=" + recordid;
      String[][] recordContent = db.executeQueryToStrArr2(querySQL, 
          fields.length);
      String contentFIleId = UUID.randomUUID().toString();
      dataSetXMLContent.append("\n        <Row>");
      dataSetXMLContent.append("\n           <ID>" + contentFIleId + 
          "</ID>");
      for (int j = 0; j < fields.length; j++)
        dataSetXMLContent.append("\n           <" + fields[j][1] + ">" + 
            recordContent[0][j] + "</" + fields[j][1] + ">"); 
      dataSetXMLContent.append("\n        </Row>");
      file = new File(String.valueOf(localPath) + "/Attachment" + "/" + contentFIleId);
      file.mkdirs();
      String newFilePath = String.valueOf(localPath) + 
        "/Attachment" + 
        "/" + 
        contentFIleId + 
        contentFilePath.substring(contentFilePath
          .lastIndexOf("/") + 
          contentFilePath.lastIndexOf("\\") + 1);
      copyFile(contentFilePath, newFilePath);
      String[][] fjs = db
        .executeQueryToStrArr2("select filename,savename from co_attach where body_id=" + 
          recordid);
      if (fjs != null && fjs.length > 0)
        for (int k = 0; k < fjs.length; k++) {
          String fj = contentFIleId;
          String nf = fjs[k][1].substring(0, 4);
          file = new File(String.valueOf(localPath) + "/Attachment" + "/" + fj);
          file.mkdirs();
          String oldFilePath = 
            String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
            "upload/" + nf + "/cooperate/" + fjs[k][1];
          String newFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + fj + 
            "/" + fjs[k][1];
          copyFile(oldFilePath, newFilePath1);
        }  
      dataSetXMLContent.append("\n     </DataSet>");
      dataSetXMLContent.append("\n   </DataSets>");
      dataSetXMLContent.append("\n </DataPackage>");
      String xmlFileName = String.valueOf(localPath) + "/DataSet.xml";
      try {
        FileWriter fw = new FileWriter(xmlFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataSetXMLContent.toString());
        bw.close();
        fw.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      File file1 = new File(xmlFileName);
      file1.delete();
      file1.createNewFile();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file1), "UTF-8"));
      writer.write(dataSetXMLContent.toString());
      writer.close();
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("StartUpload", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR StartUpLoad!");
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      FTPUtil.uploadAllFilesUnderPath(localPath, guid);
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c
          .invoke("EndUpload", new Object[] { guid });
        String result1 = results1[0].toString();
        c.close();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR EndUpload!==" + result1);
        } 
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("CommitTransaction", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        c.close();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR CommitTransaction!==" + 
              result1);
        } 
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      if (tongbuOK)
        (new WorkFlowEJBBeanForRWS()).guidang("cooperate", recordid, empid, 
            guid); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public String tongbuForInformation(String recordid, String contentFilePath, String empid) {
    String result = "";
    boolean tongbuOK = true;
    StringBuffer dataSetXMLContent = new StringBuffer("");
    DbOpt db = new DbOpt();
    try {
      String guid = "local" + System.currentTimeMillis();
      String endPoint = SysConfigReader.readConfigValue("RwsWebService", 
          "endPoint");
      String nameSpace = SysConfigReader.readConfigValue("RwsWebService", 
          "nameSpace");
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results = c.invoke("StartTransaction", new Object[0]);
        guid = results[0].toString();
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      String localPath = 
        String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
        "upload/rws/" + guid;
      File file = new File(String.valueOf(localPath) + "/Attachment");
      if (!file.exists())
        file.mkdirs(); 
      String channelId = db.executeQueryToStr("select channel_id from oa_information where information_id=" + recordid);
      dataSetXMLContent
        .append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
      dataSetXMLContent.append("\n <DataPackage Importer='OA'>");
      dataSetXMLContent.append("\n <DataSets>");
      dataSetXMLContent.append("\n    <DataSet DSID='info_" + channelId + "'>");
      String[][] fields = { { "informationtitle", "informationtitle" }, { "informationissuer", "informationissuer" }, { "informationissueorg", "informationissueorg" }, { "informationreadername", "informationreadername" }, { "to_char(informationissuetime,'yyyy-mm-dd')", "informationissuetime" } };
      String querySQL = "select ";
      for (int i = 0; i < fields.length; i++) {
        if (i == 0) {
          querySQL = String.valueOf(querySQL) + fields[0][0] + " " + fields[0][1];
        } else {
          querySQL = String.valueOf(querySQL) + "," + fields[i][0] + " " + fields[i][1];
        } 
      } 
      querySQL = String.valueOf(querySQL) + " from oa_information where information_id=" + recordid;
      String[][] recordContent = db.executeQueryToStrArr2(querySQL, 
          fields.length);
      String contentFIleId = UUID.randomUUID().toString();
      dataSetXMLContent.append("\n        <Row>");
      dataSetXMLContent.append("\n           <ID>" + contentFIleId + 
          "</ID>");
      for (int j = 0; j < fields.length; j++)
        dataSetXMLContent.append("\n           <" + fields[j][1] + ">" + 
            CharacterTool.replaceXMLTags(recordContent[0][j]) + "</" + fields[j][1] + ">"); 
      dataSetXMLContent.append("\n        </Row>");
      file = new File(String.valueOf(localPath) + "/Attachment" + "/" + contentFIleId);
      file.mkdirs();
      String newFilePath = String.valueOf(localPath) + 
        "/Attachment" + 
        "/" + 
        contentFIleId + 
        contentFilePath.substring(contentFilePath
          .lastIndexOf("/") + 
          contentFilePath.lastIndexOf("\\") + 1);
      copyFile(contentFilePath, newFilePath);
      String[][] fjs = db
        .executeQueryToStrArr2("select accessoryname,accessorysavename from oa_informationaccessory where information_id=" + 
          recordid);
      if (fjs != null && fjs.length > 0)
        for (int k = 0; k < fjs.length; k++) {
          String fj = contentFIleId;
          String nf = fjs[k][1];
          if (nf.charAt(4) == '_') {
            nf = nf.substring(0, 4);
          } else {
            nf = "0000";
          } 
          file = new File(String.valueOf(localPath) + "/Attachment" + "/" + fj);
          file.mkdirs();
          String oldFilePath = 
            String.valueOf(contentFilePath.substring(0, contentFilePath.indexOf("archivesfile"))) + 
            "upload/" + nf + "/information/" + fjs[k][1];
          String newFilePath1 = String.valueOf(localPath) + "/Attachment" + "/" + fj + 
            "/" + fjs[k][1];
          copyFile(oldFilePath, newFilePath1);
        }  
      dataSetXMLContent.append("\n     </DataSet>");
      dataSetXMLContent.append("\n   </DataSets>");
      dataSetXMLContent.append("\n </DataPackage>");
      String xmlFileName = String.valueOf(localPath) + "/DataSet.xml";
      try {
        FileWriter fw = new FileWriter(xmlFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataSetXMLContent.toString());
        bw.close();
        fw.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      File file1 = new File(xmlFileName);
      file1.delete();
      file1.createNewFile();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file1), "UTF-8"));
      writer.write(dataSetXMLContent.toString());
      writer.close();
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("StartUpload", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        c.close();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR StartUpLoad!");
        } 
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      FTPUtil.uploadAllFilesUnderPath(localPath, guid);
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c
          .invoke("EndUpload", new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR EndUpload!==" + result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      try {
        Client c = new Client(new URL(endPoint));
        Object[] results1 = c.invoke("CommitTransaction", 
            new Object[] { guid });
        String result1 = results1[0].toString();
        if (!"true".equalsIgnoreCase(result1)) {
          tongbuOK = false;
          System.out.println("ERROR FOR CommitTransaction!==" + 
              result1);
        } 
        c.close();
      } catch (Exception e) {
        tongbuOK = false;
        e.printStackTrace();
      } 
      if (tongbuOK)
        (new WorkFlowEJBBeanForRWS()).guidang("information", recordid, empid, guid); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public static void yuguidang(String tableId, String recordId, String emp_id) {
    DbOpt db = null;
    try {
      String tableName = "";
      if ("cooperate".equals(tableId)) {
        tableName = "cooperate";
      } else if (tableId.startsWith("jst_")) {
        tableName = tableId;
      } else if ("DOC_DOCUMENTSENDFILE".equalsIgnoreCase(tableId)) {
        tableName = "DOC_DOCUMENTSENDFILE";
      } else if ("DOC_receivefile".equalsIgnoreCase(tableId)) {
        tableName = "DOC_receivefile";
      } else {
        db = new DbOpt();
        tableName = db.executeQueryToStr("select area_table from tarea where page_id=" + tableId + " and area_name='form1'");
      } 
      (new WorkFlowEJBBeanForRWS()).yuguidang(tableName, recordId, emp_id);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (db != null)
          db.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void copyFile(String oldPath, String newPath) {
    try {
      int bytesum = 0;
      int byteread = 0;
      oldPath = oldPath.replaceAll("\\\\", "/");
      newPath = newPath.replaceAll("\\\\", "/");
      File oldfile = new File(oldPath);
      if (oldfile.exists()) {
        System.out.println("oldfile存在：" + oldPath);
        InputStream inStream = new FileInputStream(oldPath);
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1444];
        while ((byteread = inStream.read(buffer)) != -1) {
          bytesum += byteread;
          fs.write(buffer, 0, byteread);
        } 
        inStream.close();
        fs.flush();
        fs.close();
      } 
    } catch (Exception e) {
      System.out.println("复制单个文件操作出错");
      e.printStackTrace();
    } 
  }
}
