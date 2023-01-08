package com.js.oa.dcq.bean;

import com.caucho.hessian.client.HessianProxyFactory;
import com.js.oa.dcq.util.TempFile;
import com.js.oa.dcq.webservice.OASwapInterface;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.BASE64;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BusinessShowBean {
  SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  private final long maxFileLength = 2097152L;
  
  private final String url = SystemCommon.getDcqService();
  
  private HessianProxyFactory factory = new HessianProxyFactory();
  
  private String requestWebService(String json) {
    String result = "";
    try {
      OASwapInterface oa = (OASwapInterface)this.factory.create(OASwapInterface.class, this.url);
      result = oa.handleTask(json);
    } catch (Exception e) {
      e.printStackTrace();
      result = "{\"execCode\":\"0001\",\"execMsg\":\"" + e.getMessage() + "\"}";
    } 
    return result;
  }
  
  private long requestWebServiceGetLength(String json) {
    long len = 0L;
    try {
      OASwapInterface oa = (OASwapInterface)this.factory.create(OASwapInterface.class, this.url);
      String result = oa.getfilelength(json);
      JSONObject jsonObj = JSONObject.fromObject(result);
      if (jsonObj.getString("execCode") != null && "0000".equals(jsonObj.getString("execCode"))) {
        if (jsonObj.getString("fileLength") != null && !"".equals(jsonObj.getString("fileLength")))
          len = Long.parseLong(jsonObj.getString("fileLength")); 
      } else {
        System.out.println(jsonObj.getString("execMsg"));
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return len;
  }
  
  private byte[] requestWebServiceGetBytes(String json) {
    byte[] bytes = (byte[])null;
    try {
      OASwapInterface oa = (OASwapInterface)this.factory.create(OASwapInterface.class, this.url);
      bytes = oa.download(json);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return bytes;
  }
  
  private boolean requestWebServiceUpload(byte[] fileContent, String json) {
    boolean flag = false;
    try {
      OASwapInterface oa = (OASwapInterface)this.factory.create(OASwapInterface.class, this.url);
      String result = oa.upload(fileContent, json);
      JSONObject jsonObj = JSONObject.fromObject(result);
      if (jsonObj.getString("execCode") != null && "0000".equals(jsonObj.getString("execCode"))) {
        flag = true;
      } else {
        System.out.println(jsonObj.get("execMsg"));
      } 
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public boolean getBusinessMessage(String personID, String orgID, String realPath) {
    System.out.println("数据获取请求地址：" + this.url);
    boolean f1 = getDocument(personID, orgID, realPath);
    System.out.println(f1);
    boolean f2 = getInformation(personID, orgID, realPath);
    System.out.println(f2);
    boolean f3 = getInspector(personID, orgID, realPath);
    System.out.println(f3);
    boolean f4 = getMeeting(personID, orgID, realPath);
    System.out.println(f4);
    return (f1 && f2 && f3 && f4);
  }
  
  private boolean executeUpdate(String updateSQL) {
    boolean flag = false;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      int n = base.executeUpdate(updateSQL);
      if (n > 0)
        flag = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return flag;
  }
  
  public boolean sendDocument(String realPath, String personID, String orgID, String fileId) {
    boolean flag = false;
    String businessType = "document";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    Date date = new Date();
    try {
      base.begin();
      String sql = "select SendFile_Grade,zjkySecrecyterm,DOCUMENTSENDFILE_TOPICWORD,DOCUMENTSENDFILE_SECURITYGRADE,DOCUMENTSENDFILE_BYTENUMBER,openProperty,documentsendfile_title,sendfile_goldGridId,documentSendfile_wordtype,accessoryname,accessorysavename,toPerson1Id,toPerson2Id,send_text_field1,SendFile_proof,sendFileDepartWord from DOC_DOCUMENTSENDFILE where documentsendfile_id=" + 



        
        fileId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        String postWord = rs.getString("sendFileDepartWord");
        String docNo = rs.getString("DOCUMENTSENDFILE_BYTENUMBER");
        String urgency = rs.getString("SendFile_Grade");
        String dense = rs.getString("DOCUMENTSENDFILE_SECURITYGRADE");
        String startDate = this.ymd.format(date);
        String secrecyDateStr = "";
        String sourceMethod = rs.getString("openProperty");
        String basis = "";
        String standard = "";
        String docType = "";
        String docTitle = rs.getString("documentsendfile_title");
        String kWord = rs.getString("DOCUMENTSENDFILE_TOPICWORD");
        String mainSent = rs.getString("toPerson1Id");
        String copySent = rs.getString("toPerson2Id");
        String printing = rs.getString("send_text_field1");
        String proofreading = rs.getString("SendFile_proof");
        String jointly = "";
        String browserIDStr = "";
        String docName = rs.getString("sendfile_goldGridId");
        String docFileType = rs.getString("documentSendfile_wordtype");
        String attName = rs.getString("accessoryname");
        String attSaveName = rs.getString("accessorysavename");
        String issue = "";
        String nuclear = "";
        if (mainSent != null && mainSent.indexOf("*") > -1) {
          if (mainSent.startsWith("*"))
            mainSent = mainSent.substring(1); 
          if (mainSent.equals("*"))
            mainSent = mainSent.substring(0, mainSent.length() - 1); 
          mainSent = mainSent.replace("**", ",");
          sql = "select orgname from org_organization where org_id in (" + mainSent + ")";
          rs = base.executeQuery(sql);
          while (rs.next())
            mainSent = String.valueOf(rs.getString(1)) + ","; 
        } 
        if (copySent != null && copySent.indexOf("*") > -1) {
          if (copySent.startsWith("*"))
            copySent = copySent.substring(1); 
          if (copySent.equals("*"))
            copySent = copySent.substring(0, copySent.length() - 1); 
          copySent = copySent.replace("**", ",");
          sql = "select orgname from org_organization where org_id in (" + copySent + ")";
          rs = base.executeQuery(sql);
          while (rs.next())
            copySent = String.valueOf(rs.getString(1)) + ","; 
        } 
        sql = "SELECT DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,empname FROM jsf_dealwithcomment a,org_employee e WHERE a.dealwithemployee_id=e.emp_id AND commentfield='sendFileProveDraft' AND wf_dealwith_id IN (SELECT wf_dealwith_id FROM jsf_dealwith WHERE databaserecord_id=" + 

          
          fileId + 
          ") " + "ORDER BY WF_DEALWITHCOMMENT_ID";
        rs = base.executeQuery(sql);
        while (rs.next())
          nuclear = String.valueOf(nuclear) + rs.getString(1) + "(" + rs.getString(3) + "  " + rs.getString(2) + ")<br>"; 
        sql = "SELECT DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,empname FROM jsf_dealwithcomment a,org_employee e WHERE a.dealwithemployee_id=e.emp_id AND commentfield='documentSendFileSendFile' AND wf_dealwith_id IN (SELECT wf_dealwith_id FROM jsf_dealwith WHERE databaserecord_id=" + 

          
          fileId + 
          ") " + "ORDER BY WF_DEALWITHCOMMENT_ID";
        rs = base.executeQuery(sql);
        while (rs.next())
          issue = String.valueOf(issue) + rs.getString(1) + "(" + rs.getString(3) + "  " + rs.getString(2) + ")<br>"; 
        String docJson = "{\"fileID\":\"" + docName + "\",\"fileName\":\"" + docName + docFileType + "\"}";
        TempFile docFile = new TempFile();
        docFile.setFileName(String.valueOf(docName) + docFileType);
        docFile.setFileNewName(String.valueOf(docName) + docFileType);
        docFile.setFilePath("upload/govdocumentmanager/" + docName + docFileType);
        docFile.setMainFlag(true);
        docFile.setFileType(1);
        docFile.setDocId(Long.parseLong(fileId));
        List<TempFile> attFiles = new ArrayList<TempFile>();
        TempFile attFile = null;
        String attJson = "[";
        if (attName != null && !"".equals(attName)) {
          String[] attNames = attName.split("\\|");
          String[] saveNames = attSaveName.split("\\|");
          for (int i = 0; i < attNames.length; i++) {
            attFile = new TempFile();
            attFile.setFileName(attNames[i]);
            attFile.setFileNewName(saveNames[i]);
            attFile.setFilePath("upload/" + saveNames[i].split("_")[0] + "/govdocumentmanager/" + saveNames[i]);
            attFile.setMainFlag(false);
            attFile.setFileType(0);
            attFile.setDocId(Long.parseLong(fileId));
            attFiles.add(attFile);
            attJson = String.valueOf(attJson) + "{\"fileID\":\"" + saveNames[i] + "\",\"fileName\":\"" + attNames[i] + "\"},";
          } 
        } 
        if (attJson.endsWith(","))
          attJson = attJson.substring(0, attJson.length() - 1); 
        attJson = String.valueOf(attJson) + "]";
        String beanJson = "{\"postWord\":\"" + postWord + "\",\"docType\":\"" + docType + 
          "\",\"docNo\":\"" + docNo + "\",\"urgency\":\"" + urgency + "\",\"dense\":\"" + 
          dense + "\",\"startDate\":\"" + startDate + "\",\"secrecyDateStr\":\"" + 
          secrecyDateStr + "\",\"sourceMethod\":\"" + sourceMethod + "\",\"basis\":\"" + 
          basis + "\",\"standard\":\"" + standard + "\",\"docTitle\":\"" + 
          docTitle + "\",\"kWord\":\"" + kWord + "\",\"mainSent\":\"" + mainSent + 
          "\",\"copySent\":\"" + copySent + "\",\"nuclear\":\"" + nuclear + 
          "\",\"printing\":\"" + printing + "\",\"proofreading\":\"" + proofreading + 
          "\",\"issue\":\"" + issue + "\",\"jointly\":\"" + jointly + "\",\"handle\":\"1\"," + 
          "\"regDep\":\"C3800\",\"creator\":\"" + personID + "\",\"orgId\":\"" + 
          orgID + "\",\"browserIDStr\":\"" + browserIDStr + "\",\"attFile\":" + attJson + 
          ",\"docFile\":" + docJson + "}";
        String para = "{\"operateType\":\"save\",\"businessType\":\"" + businessType + 
          "\",\"businessID\":\"\",\"personID\":\"" + personID + "\",\"businessBean\":" + beanJson + "}";
        System.out.println("发文信息报送参数：" + para);
        String result = requestWebService(para);
        JSONObject jsonObj = JSONObject.fromObject(result);
        if (jsonObj.get("execCode") != null && "0000".equals(jsonObj.getString("execCode"))) {
          String oid = jsonObj.getString("oid");
          docFile.setDocOid(oid);
          docFile.setFileID(jsonObj.getString(docName));
          boolean upload = uploadFile(realPath, businessType, "", oid, personID, docFile);
          System.out.println("正文附件上传==" + upload);
          for (TempFile f : attFiles) {
            f.setDocOid(oid);
            f.setFileID(jsonObj.getString(f.getFileNewName()));
            upload = uploadFile(realPath, businessType, "", oid, personID, f);
            System.out.println("附件《" + f.getFileName() + "》上传==" + upload);
          } 
          sql = "update DOC_DOCUMENTSENDFILE set oid='" + oid + "' where documentsendfile_id=" + fileId;
          flag = executeUpdate(sql);
        } else {
          System.out.println(jsonObj.getString("execMsg"));
        } 
      } else {
        System.out.println("未找到发文信息。");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return flag;
  }
  
  public List<Object[]> getSendfileDealwithInfo(String oid, String personID) {
    String para = "{\"businessType\":\"document\",\"businessID\":\"" + oid + "\",\"personID\":\"" + personID + "\"," + 
      "\"businessBean\":\"\",\"operateType\":\"manage\"}";
    System.out.println("查询发文办理情况：" + para);
    List<Object[]> list = new ArrayList();
    String result = requestWebService(para);
    JSONObject json = JSONObject.fromObject(result);
    if (json != null && json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
      if (json.get("postDoc") != null && !"".equals(json.getString("postDoc"))) {
        JSONArray arr = json.getJSONArray("postDoc");
        if (arr != null && arr.size() > 0) {
          JSONObject j = null;
          for (int i = 0; i < arr.size(); i++) {
            j = arr.getJSONObject(i);
            list.add(new Object[] { j.getString("accDepID"), j.getString("accDep"), 
                  j.getString("accPerID"), j.getString("accPer"), j.getString("accDate"), 
                  j.getString("status") });
          } 
        } 
      } else {
        System.out.println("无发文办理情况。");
      } 
    } else {
      System.out.println("获取发文办理情况失败：" + json.getString("execMsg"));
    } 
    return list;
  }
  
  public boolean documentPress(String personID, String documentId, String title, String recIDs, String content) {
    boolean flag = false;
    String orgIds = "";
    if (recIDs != null && !"".equals(recIDs)) {
      if (recIDs.startsWith("*"))
        recIDs = recIDs.substring(1); 
      if (recIDs.endsWith("*"))
        recIDs = recIDs.substring(0, recIDs.length() - 1); 
      recIDs = recIDs.replace("**", ",");
      DataSourceBase base = new DataSourceBase();
      ResultSet rs = null;
      try {
        base.begin();
        String sql = "SELECT guid FROM org_organization WHERE org_id IN (" + recIDs + ")";
        rs = base.executeQuery(sql);
        String guid = "";
        boolean getOrgIds = true;
        while (rs.next()) {
          guid = rs.getString(1);
          if (guid != null && !"".equals(guid) && !"null".equalsIgnoreCase(guid)) {
            orgIds = String.valueOf(orgIds) + "," + guid;
            continue;
          } 
          getOrgIds = false;
        } 
        if (getOrgIds) {
          if (orgIds.startsWith(","))
            orgIds = orgIds.substring(1); 
          String para = "{\"businessType\":\"remind\",\"businessID\":\"\",\"personID\":\"" + personID + 
            "\",\"businessBean\":{\"title\":\"" + title + "\",\"recIDs\":\"" + orgIds + "\",\"remindID\":\"" + 
            personID + "\",\"remindContent\":\"" + content + "\"},\"operateType\":\"save\" }";
          String jsonStr = requestWebService(para);
          JSONObject json = JSONObject.fromObject(jsonStr);
          if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
            try {
              JSONArray arr = (json.get("remindDoc") != null && !"".equals(json.getString("remindDoc"))) ? json.getJSONArray("remindDoc") : null;
              JSONObject obj = null;
              for (int i = 0; i < arr.size(); i++) {
                obj = arr.getJSONObject(i);
                sql = "insert into dcq_document_wait(wait_oid,doc_id,press_org,personID,title,accDate,remindContent,docType) values ('" + 
                  
                  obj.getString("oid") + "'," + documentId + ",'" + orgIds + "','" + personID + "'," + 
                  "'" + title + "','" + this.ymdhms.format(new Date()) + "','" + content + "','sendfile')";
                base.executeUpdate(sql);
              } 
              flag = true;
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } else {
            System.out.println("发文催办信息发送失败：" + json.getString("execMsg"));
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close(); 
          base.end();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return flag;
  }
  
  private boolean getDocument(String personID, String orgID, String realPath) {
    boolean flag = false;
    String businessType = "document";
    System.out.println("开始获取收文数据。。。" + (new Date()).getTime());
    JSONArray arr = getDocumentList(personID, orgID);
    System.out.println("收文数据待收列表获取结束。。。" + (new Date()).getTime());
    if (arr != null && arr.size() > 0) {
      DataSourceBase base = new DataSourceBase();
      ResultSet rs = null;
      try {
        base.begin();
        JSONObject obj = null;
        for (int i = 0; i < arr.size(); i++) {
          obj = arr.getJSONObject(i);
          System.out.println(String.valueOf(i) + "获取收文详细信息开始。。。" + (new Date()).getTime());
          JSONObject jsonObj = getDocumentDetails(obj.getString("oid"), personID);
          System.out.println(String.valueOf(i) + "获取收文详细信息结束。。。" + (new Date()).getTime());
          if (jsonObj != null && jsonObj.get("execCode") != null && "0000".equals(jsonObj.getString("execCode"))) {
            Date date = new Date();
            String secrecyDateStr = getJSONValueByKey(jsonObj, "secrecyDateStr");
            if ("".equals(secrecyDateStr))
              secrecyDateStr = this.ymd.format(date); 
            String createDate = getDate(getJSONValueByKey(jsonObj, "createDate"));
            if ("".equals(createDate))
              createDate = this.ymdhms.format(date); 
            String deptName = jsonObj.getString("deptName");
            String sql = "SELECT orgname FROM org_organization WHERE guid='" + deptName + "'";
            rs = base.executeQuery(sql);
            String releaseDeptName = "";
            while (rs.next())
              releaseDeptName = String.valueOf(releaseDeptName) + rs.getString(1); 
            if (releaseDeptName == null || "".equals(releaseDeptName))
              releaseDeptName = deptName; 
            String docType = getJSONValueByKey(jsonObj, "docType");
            if ("0".equals(docType)) {
              docType = "命令";
            } else if ("1".equals(docType)) {
              docType = "议案";
            } else if ("2".equals(docType)) {
              docType = "决定";
            } else if ("3".equals(docType)) {
              docType = "意见";
            } else if ("4".equals(docType)) {
              docType = "公告";
            } else if ("5".equals(docType)) {
              docType = "通知";
            } else if ("6".equals(docType)) {
              docType = "通告";
            } else if ("7".equals(docType)) {
              docType = "通报";
            } else if ("8".equals(docType)) {
              docType = "报告";
            } else if ("9".equals(docType)) {
              docType = "请示";
            } else if ("10".equals(docType)) {
              docType = "批复";
            } else if ("11".equals(docType)) {
              docType = "函";
            } else if ("12".equals(docType)) {
              docType = "会议纪要";
            } 
            sql = "insert into dcq_recivefile(file_oid,docTitle,docNo,releaseDate,releaseStatus,releaseDepID,postWord,urgency,file_dense,secrecyDateStr,sourceMethod,basis,file_standard,docType,kword,mainSent,copySent,deptName,creatorName,createDate,issue,jointly,nuclear,printing,proofreading,personID,deptID) values ('" + 



              
              obj.getString("oid") + "','" + 
              obj.getString("docTitle") + "','" + 
              jsonObj.getString("docNo") + "','" + 
              getDate(obj.getString("releaseDate")) + "','" + 
              obj.getString("releaseStatus") + "','" + 
              getJSONValueByKey(obj, "releaseDepID") + "','" + 
              getJSONValueByKey(jsonObj, "postWord") + "','" + 
              jsonObj.getString("urgency") + "','" + 
              jsonObj.getString("dense") + "','" + 
              secrecyDateStr + "','" + 
              getJSONValueByKey(jsonObj, "sourceMethod") + "','" + 
              getJSONValueByKey(jsonObj, "basis") + "','" + 
              getJSONValueByKey(jsonObj, "standard") + "','" + 
              docType + "','" + 
              jsonObj.getString("kword") + "','" + 
              getJSONValueByKey(jsonObj, "mainSent") + "','" + 
              getJSONValueByKey(jsonObj, "copySent") + "','" + 
              releaseDeptName + "','" + 
              getJSONValueByKey(jsonObj, "creatorName") + "','" + 
              createDate + "','" + 
              getJSONValueByKey(jsonObj, "issue") + "','" + 
              getJSONValueByKey(jsonObj, "jointly") + "','" + 
              getJSONValueByKey(jsonObj, "nuclear") + "','" + 
              jsonObj.getString("printing") + "','" + 
              getJSONValueByKey(jsonObj, "proofreading") + "','" + 
              getJSONValueByKey(jsonObj, "personID") + "','" + 
              getJSONValueByKey(jsonObj, "deptID") + "')";
            base.executeUpdate(sql);
            System.out.println(String.valueOf(i) + "收文详细信息入库完成。。" + (new Date()).getTime());
            String newId = "";
            String getNewIdSQL = "select file_id from dcq_recivefile where file_oid='" + obj.getString("oid") + "'";
            rs = base.executeQuery(getNewIdSQL);
            if (rs.next())
              newId = rs.getString(1); 
            boolean dFlag = false;
            if (jsonObj.getString("docFile") != null && !"".equals(jsonObj.getString("docFile"))) {
              JSONArray docArr = jsonObj.getJSONArray("docFile");
              if (docArr != null && docArr.size() > 0) {
                JSONObject docObj = docArr.getJSONObject(0);
                dFlag = downFile(realPath, businessType, personID, newId, obj.getString("oid"), docObj.getString("fileID"), 
                    docObj.getString("fileName"), true, "recive", 1);
              } 
            } 
            if (jsonObj.getString("attFile") != null && !"".equals(jsonObj.getString("attFile"))) {
              JSONArray attArr = jsonObj.getJSONArray("attFile");
              if (attArr != null && attArr.size() > 0)
                for (int j = 0; j < attArr.size(); j++) {
                  JSONObject attFile = attArr.getJSONObject(j);
                  dFlag = downFile(realPath, businessType, personID, newId, obj.getString("oid"), attFile.getString("fileID"), 
                      attFile.getString("fileName"), false, "recive", 0);
                }  
            } 
            JSONArray waitArr = documentPressList(personID, orgID);
            if (waitArr != null && waitArr.size() > 0) {
              JSONObject wait = null;
              String insertWaitSQL = null;
              for (int j = 0; j < waitArr.size(); j++) {
                wait = documentWaitDetails(obj.getString("oid"), personID);
                if (wait != null) {
                  insertWaitSQL = "insert into dcq_document_wait(wait_oid,oid,doc_id,title,accDate,remindContent,docType) values('" + 
                    waitArr.getJSONObject(j).getString("oid") + "','" + obj.getString("oid") + "'," + newId + ",'" + 
                    wait.getString("title") + "'," + "'" + getDate(waitArr.getJSONObject(j).getString("accDate")) + "'," + 
                    "'" + wait.getString("remindContent") + "','recive')";
                  base.executeUpdate(insertWaitSQL);
                } 
              } 
            } 
            System.out.println(String.valueOf(i) + "收文信息附件获取完成。。。" + (new Date()).getTime());
            String feedbackJson = "{\"businessType\":\"" + businessType + 
              "\",\"businessID\":\"\",\"personID\":\"" + personID + 
              "\",\"businessBean\":{\"oid\":\"" + obj.getString("oid") + 
              "\",\"orgID\":\"" + orgID + "\",\"personID\":\"" + personID + 
              "\",\"feedbackDate\":\"" + this.ymdhms.format(date) + 
              "\"},\"operateType\":\"feedback\" }";
            String feedbackResult = requestWebService(feedbackJson);
            JSONObject feedbackObj = JSONObject.fromObject(feedbackResult);
            if (feedbackObj.get("execCode") != null && "0000".equals(feedbackObj.getString("execCode"))) {
              String feedbackSQL = "update dcq_recivefile set feedbackInfo='" + feedbackObj.getString("execMsg") + "',feedbackTime='" + this.ymdhms.format(date) + "' where file_id=" + newId;
              base.executeUpdate(feedbackSQL);
              System.out.println("收文信息获取成功，反馈信息提交成功。");
            } else {
              System.out.println(feedbackObj.getString("execMsg"));
            } 
            System.out.println(String.valueOf(i) + "收文反馈完成。。。" + (new Date()).getTime());
          } else {
            System.out.println(jsonObj.getString("execMsg"));
          } 
        } 
        flag = true;
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close(); 
          base.end();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } else {
      System.out.println("收文信息无未接收数据。");
      flag = true;
    } 
    System.out.println("收文信息获取完成。。。" + (new Date()).getTime());
    return flag;
  }
  
  private JSONArray getDocumentList(String personID, String orgID) {
    String json = "{\"businessType\":\"document\",\"businessID\":\"\",\"personID\":\"" + personID + "\",\"businessBean\":{\"orgID\":\"" + 
      orgID + "\",\"personID\":\"" + personID + "\"},\"operateType\":\"worklist\"}";
    System.out.println("收文列表：" + json);
    String jsonStr = requestWebService(json);
    JSONArray arr = null;
    JSONObject jsonObj = JSONObject.fromObject(jsonStr);
    if (jsonObj.getString("execCode") != null && "0000".equals(jsonObj.getString("execCode"))) {
      arr = (jsonObj.get("accDoc") == null || jsonObj.getString("accDoc") == null || "".equals(jsonObj.getString("accDoc"))) ? null : jsonObj.getJSONArray("accDoc");
    } else {
      System.out.println("获取收文信息失败：" + jsonObj.getString("execMsg"));
    } 
    return arr;
  }
  
  private JSONObject getDocumentDetails(String oId, String personID) {
    String json = "{\"businessType\":\"document\",\"businessID\":\"" + oId + "\",\"personID\":\"" + personID + 
      "\",\"businessBean\":\"\",\"operateType\":\"details\"}";
    String jsonStr = requestWebService(json);
    JSONObject jsonObj = JSONObject.fromObject(jsonStr);
    return jsonObj;
  }
  
  private JSONArray documentPressList(String personID, String orgID) {
    JSONArray arr = null;
    String para = "{\"businessType\":\"remind\",\"businessID\":\"\",\"personID\":\"" + personID + "\",\"businessBean\":{\"orgID\":\"" + 
      orgID + "\",\"accID\":\"\"},\"operateType\":\"worklist\"}";
    String jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      arr = (json.getString("remindDoc") == null || "".equals(json.getString("remindDoc"))) ? null : json.getJSONArray("remindDoc");
    } else {
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    return arr;
  }
  
  private JSONObject documentWaitDetails(String oId, String personID) {
    String para = "{\"businessType\":\"remind\",\"businessID\":\"" + oId + "\",\"personID\":\"" + personID + 
      "\",\"businessBean\":\"\",\"operateType\":\"details\" }";
    String jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode")))
      return json; 
    System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    return null;
  }
  
  public List<Object[]> findPressByFileId(String fileId, String fileType) {
    String sql = "select title,accDate,remindContent from dcq_document_wait where docType='" + fileType + "' and doc_id=" + fileId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    List<Object[]> list = new ArrayList();
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        list.add(new Object[] { rs.getString("title"), rs.getString("accDate"), rs.getString("remindContent") });
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return list;
  }
  
  private boolean getInformation(String personID, String orgID, String realPath) {
    boolean flag = false;
    System.out.println("获取通知公告信息开始。。。" + (new Date()).getTime());
    String jsonStr = "";
    String para = "{\"businessType\":\"notice\",\"businessID\":\"\",\"personID\":\"" + personID + "\",\"businessBean\":{\"receiverID\":\"" + orgID + "\",\"receiverType\":\"D\"},\"operateType\":\"worklist\" }";
    jsonStr = requestWebService(para);
    System.out.println("通知公告列表：" + para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    System.out.println("通知公告待收列表获取完成。。。" + (new Date()).getTime());
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      JSONArray arr = (json.get("accNotice") == null || "".equals(json.get("accNotice"))) ? null : json.getJSONArray("accNotice");
      if (arr != null && arr.size() > 0) {
        JSONObject jsonObj = null;
        DataSourceBase base = new DataSourceBase();
        ResultSet rs = null;
        try {
          base.begin();
          for (int i = 0; i < arr.size(); i++) {
            jsonObj = arr.getJSONObject(i);
            String sql = "SELECT oid FROM dcq_information WHERE oid='" + jsonObj.getString("oid") + "'";
            rs = base.executeQuery(sql);
            if (rs.next()) {
              System.out.println(String.valueOf(jsonObj.getString("oid")) + "==通知公告信息已存在。");
            } else {
              System.out.println(String.valueOf(i) + "获取通知公告详细信息。。。" + (new Date()).getTime());
              String paraSplit = "{\"businessType\":\"notice\",\"businessID\":\"" + 
                jsonObj.getString("oid") + "\",\"personID\":\"" + personID + 
                "\",\"businessBean\":\"\",\"operateType\":\"details\"}";
              String jsonStrNotice = requestWebService(paraSplit);
              JSONObject jsonNotice = JSONObject.fromObject(jsonStrNotice);
              System.out.println(String.valueOf(i) + "通知公告详细信息获取完成。。。" + (new Date()).getTime());
              if (jsonNotice != null && jsonNotice.get("execCode") != null && "0000".equals(jsonNotice.get("execCode"))) {
                String havContent = getJSONValueByKey(jsonNotice, "havContent");
                String oid = jsonObj.getString("oid");
                String type = getJSONValueByKey(jsonObj, "announcementType");
                String typeName = "";
                if ("MaterialScience".equalsIgnoreCase(type)) {
                  typeName = "群众路线材料";
                } else if ("UrgentBusiness".equalsIgnoreCase(type)) {
                  typeName = "紧急公务";
                } else if ("MeetingNotice".equalsIgnoreCase(type)) {
                  typeName = "会议通知";
                } else if ("Announcement".equalsIgnoreCase(type)) {
                  typeName = "公告";
                } else if ("Notice".equalsIgnoreCase(type)) {
                  typeName = "通知";
                } 
                StringBuffer InsertSql = new StringBuffer();
                InsertSql.append("insert into dcq_information");
                InsertSql.append("(senderName,releaseDate,announcementTitle,sendOrgName,oid,sendOrgID,senderID,announcementType,havContent,startDate, endDate,MARK,important)");
                InsertSql.append(" values ( '" + 
                    jsonObj.getString("senderName") + "', " + 
                    "'" + getDate(jsonNotice.getString("releaseDate")) + "', " + 
                    "'" + jsonObj.getString("announcementTitle") + "', " + 
                    "'" + jsonObj.getString("sendOrgName") + "', " + 
                    "'" + oid + "', " + 
                    "'" + jsonObj.getString("sendOrgID") + "', " + 
                    "'" + jsonObj.getString("senderID") + "', " + 
                    "'" + typeName + "', " + 
                    "'" + havContent + "', " + 
                    "'" + getDate(jsonNotice.getString("startDate")) + "', " + 
                    "'" + getDate(jsonNotice.getString("endDate")) + "', " + 
                    "'0'," + "'" + getJSONValueByKey(jsonObj, "important") + "'" + 
                    ")");
                System.out.println(String.valueOf(i) + "通知公共信息入库完成。。。" + (new Date()).getTime());
                int n = base.executeUpdate(InsertSql.toString());
                if (n > 0) {
                  String sqlStringTitle = "select informationId from dcq_information where oid=" + jsonObj.getString("oid");
                  String newId = "";
                  rs = base.executeQuery(sqlStringTitle);
                  if (rs.next())
                    newId = rs.getString(1); 
                  if ("Y".equalsIgnoreCase(havContent))
                    downFile(realPath, "notice", personID, newId, oid, oid, 
                        "正文附件" + oid + ".html", true, "notice", 1); 
                  JSONObject jsonDownload = null;
                  JSONArray arrFile = (jsonNotice.getString("attFile") == null || "".equals(jsonNotice.getString("attFile"))) ? null : jsonNotice.getJSONArray("attFile");
                  if (arrFile != null && arrFile.size() > 0)
                    for (int d = 0; d < arrFile.size(); d++) {
                      jsonDownload = arrFile.getJSONObject(d);
                      String fileID = jsonDownload.getString("fileID");
                      String fileName = jsonDownload.getString("fileName");
                      downFile(realPath, "notice", personID, newId, oid, fileID, 
                          fileName, false, "notice", 0);
                    }  
                  System.out.println(String.valueOf(i) + "通知公共附件信息获取完成。。。" + (new Date()).getTime());
                  String back = "{\"businessType\":\"notice\",\"businessID\":\"\",\"personID\":\"" + personID + 
                    "\",\"businessBean\":{\"oid\":\"" + jsonObj.getString("oid") + "\",\"browseDate\":\"" + 
                    this.ymdhms.format(new Date()) + "\"," + "\"feedbackDate\":\"" + this.ymdhms.format(new Date()) + 
                    "\",\"feedbackDepID\":\"" + orgID + "\",\"content\":\"\",\"personID\":\"" + 
                    personID + "\"},\"operateType\":\"feedback\"}";
                  String backStr = requestWebService(back);
                  JSONObject backObj = JSONObject.fromObject(backStr);
                  if (backObj.get("execCode") != null && "0000".equals(backObj.getString("execCode"))) {
                    System.out.println("通知公共" + jsonObj.getString("oid") + "初次反馈成功。");
                  } else {
                    System.out.println("通知公告" + jsonObj.getString("oid") + "初次反馈失败。" + backObj.getString("execMsg"));
                  } 
                  System.out.println(String.valueOf(i) + "通知公共反馈完成。。。" + (new Date()).getTime());
                } 
              } else {
                System.out.println(jsonNotice.get("execMsg"));
              } 
            } 
          } 
          flag = true;
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            base.begin();
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } else {
        System.out.println("通知公告无未接收数据。");
        flag = true;
      } 
    } else {
      flag = false;
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    System.out.println("通知公告信息获取完成。。。" + (new Date()).getTime());
    return flag;
  }
  
  public boolean releaseInformation(String realPath, String fileId, String orgID, String personID, String orgId) {
    boolean flag = false;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      if (orgId != null && !"".equals(orgId)) {
        if (orgId.startsWith("*"))
          orgId = orgId.substring(1); 
        if (orgId.endsWith("*"))
          orgId = orgId.substring(0, orgId.length() - 1); 
        orgId = orgId.replace("**", ",");
        String sql = "SELECT guid FROM org_organization WHERE org_id IN (" + orgId + ")";
        rs = base.executeQuery(sql);
        String id = "";
        String getOrgIds = "";
        boolean getOrgId = true;
        while (rs.next()) {
          id = rs.getString(1);
          if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id)) {
            getOrgIds = String.valueOf(getOrgIds) + "," + id;
            continue;
          } 
          getOrgId = false;
        } 
        if (getOrgId && !"".equals(getOrgIds)) {
          if (getOrgIds.startsWith(","))
            getOrgIds = getOrgIds.substring(1); 
          sql = "select documentsendfile_title,sendFile_AccessayDesc,send_text_field1,send_text_field2,ACCESSORYNAME,ACCESSORYSAVENAME from doc_documentsendfile where documentsendfile_id=" + 
            fileId;
          rs = base.executeQuery(sql);
          if (rs.next()) {
            String announcementTitle = rs.getString("documentsendfile_title");
            String announcementType = "Notice";
            String sendType = "D";
            String startDate = "";
            String endDate = "";
            String implement = "";
            String browserIDStr = getOrgIds;
            String fileName = rs.getString("ACCESSORYNAME");
            String fileSaveName = rs.getString("ACCESSORYSAVENAME");
            List<TempFile> files = new ArrayList<TempFile>();
            String filePara = "";
            if (fileName != null && !"".equals(fileName) && !"null".equalsIgnoreCase(fileName)) {
              String[] names = fileName.split("\\|");
              String[] saves = fileSaveName.split("\\|");
              TempFile file = null;
              for (int i = 0; i < names.length; i++) {
                filePara = String.valueOf(filePara) + ",{\"fileID\":\"" + saves[i] + "\",\"fileName\":\"" + names[i] + "\"}";
                file = new TempFile();
                file.setFileName(names[i]);
                file.setFileNewName(saves[i]);
                file.setFilePath("upload/" + saves[i].split("_")[0] + "/govdocumentmanager/" + saves[i]);
                files.add(file);
              } 
            } 
            if (filePara.startsWith(","))
              filePara = filePara.substring(1); 
            String para = "{\"businessType\":\"notice\",\"businessID\":\"\",\"personID\":\"" + personID + 
              "\",\"businessBean\":{\"announcementTitle\":\"" + announcementTitle + "\"," + 
              "\"announcementType\":\"" + announcementType + "\",\"sendType\":\"" + sendType + 
              "\",\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate + "\",\"orgID\":\"" + 
              orgID + "\",\"personID\":\"" + personID + "\",\"releaseDate\":\"" + this.ymdhms.format(new Date()) + 
              "\",\"implement\":\"" + implement + "\",\"edmOrgID\":\"" + orgID + "\",\"leadersIDs\":\"\"," + 
              "\"browserIDStr\":\"" + browserIDStr + "\",\"attFile\":[" + filePara + "]},\"operateType\":\"save\" }";
            System.out.println("通知公告报送参数：" + para);
            String result = requestWebService(para);
            JSONObject json = JSONObject.fromObject(result);
            if (json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
              String oid = json.getString("oid");
              boolean upload = true;
              for (TempFile f : files) {
                f.setFileID(json.getString(f.getFileNewName()));
                upload = uploadFile(realPath, "notice", "", oid, personID, f);
                if (upload) {
                  sql = "insert into dcq_fileaccessory(file_oid,file_name,file_newname,file_path,mainFlag,doc_id,doc_oid,doc_type,file_type) values ('" + 
                    f.getFileID() + 
                    "','" + f.getFileName() + "','" + f.getFileNewName() + "','" + f.getFilePath() + 
                    "','false','" + fileId + "','" + oid + "','sendnotice',0)";
                  base.executeUpdate(sql);
                  continue;
                } 
                break;
              } 
              if (upload) {
                sql = "update doc_documentsendfile set oid='" + oid + "' where documentsendfile_id=" + fileId;
                int n = base.executeUpdate(sql);
                if (n > 0) {
                  System.out.println("通知公告信息发布成功。");
                  flag = true;
                } else {
                  System.out.println("更新本地数据库信息失败。");
                } 
              } 
            } else {
              System.out.println(json.getString("execMsg"));
            } 
          } else {
            System.out.println("通知公告信息不存在。");
          } 
        } else {
          System.out.println("组织唯一标识不存在。");
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.begin();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return flag;
  }
  
  public List<Object[]> getInformationStatus(String personID, String oId) {
    String para = "{\"businessType\":\"notice\",\"businessID\":\"" + oId + "\",\"personID\":\"" + personID + 
      "\",\"businessBean\":\"\",\"operateType\":\"manage\"}";
    List<Object[]> list = new ArrayList();
    try {
      String jsonStr = requestWebService(para);
      JSONObject json = JSONObject.fromObject(jsonStr);
      if (json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
        JSONArray arr = (json.get("postDoc") == null || "".equals(json.getString("postDoc"))) ? null : json.getJSONArray("postDoc");
        JSONObject jsonObj = null;
        if (arr != null && arr.size() > 0)
          for (int i = 0; i < arr.size(); i++) {
            jsonObj = arr.getJSONObject(i);
            list.add(new Object[] { jsonObj.getString("feedbackUnitID"), jsonObj.getString("feedbackUnitName"), 
                  jsonObj.getString("feedbackPersonID"), jsonObj.getString("feedbackPersonName"), 
                  jsonObj.getString("feedbackContent"), jsonObj.getString("feedbackDate"), 
                  jsonObj.getString("browseDate") });
          }  
      } else {
        System.out.println("获取通知公告反馈信息失败：");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public boolean feedbackInformation(String personID, String oId, String orgID, String content, String browseDate) {
    boolean flag = false;
    String feedbackDate = this.ymdhms.format(new Date());
    String para = "{\"businessType\":\"notice\",\"businessID\":\"\",\"personID\":\"" + personID + 
      "\",\"businessBean\":{\"oid\":\"" + oId + "\",\"browseDate\":\"" + browseDate + 
      "\",\"feedbackDate\":\"" + feedbackDate + "\",\"feedbackDepID\":\"" + 
      orgID + "\",\"content\":\"" + content + "\",\"personID\":\"" + personID + 
      "\"},\"operateType\":\"feedback\"}";
    String jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    if (json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
      String sql = "insert into dcq_informationfeedback(informationOid,personID,browseDate,feedbackDate,orgID,content) values('" + 
        oId + "','" + personID + "','" + browseDate + 
        "','" + feedbackDate + "','" + orgID + "','" + content + "')";
      flag = executeUpdate(sql);
      if (flag) {
        System.out.println(String.valueOf(oId) + "==通知通告反馈成功。" + feedbackDate);
      } else {
        System.out.println("信息反馈保存失败。");
      } 
    } else {
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    return flag;
  }
  
  public boolean releaseMessage(String realPath, String infoId, String personID, String orgID, String amendments, String urgencyLevel, String tel, String deptId) {
    boolean flag = false;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "SELECT informationtitle,informationcontent,oid FROM OA_INFORMATION WHERE information_id=" + infoId;
      rs = base.executeQuery(sql);
      boolean canSubmit = false;
      String infoTitle = "", infoContent = "", oid = "";
      if (rs.next()) {
        infoTitle = rs.getString("informationtitle");
        oid = rs.getString("oid");
        if (oid == null)
          oid = ""; 
        canSubmit = true;
      } else {
        System.out.println("未查询到报送消息。");
      } 
      if (canSubmit) {
        if (deptId.startsWith("*"))
          deptId = deptId.substring(1); 
        if (deptId.endsWith("*"))
          deptId = deptId.substring(0, deptId.length() - 1); 
        deptId = deptId.replace("**", ",");
        sql = "select guid from org_organization where org_id in (" + deptId + ")";
        rs = base.executeQuery(sql);
        String browserIDStr = "";
        while (rs.next()) {
          String guid = rs.getString(1);
          if (guid == null || "".equals(guid)) {
            System.out.println("组织唯一标识不存在。");
            canSubmit = false;
            break;
          } 
          browserIDStr = String.valueOf(browserIDStr) + "," + guid;
        } 
        if (canSubmit) {
          if (browserIDStr.startsWith(","))
            browserIDStr = browserIDStr.substring(1); 
          sql = "SELECT accessory_id,accessoryname,accessorysavename FROM oa_informationaccessory WHERE information_id=" + infoId;
          rs = base.executeQuery(sql);
          List<TempFile> files = new ArrayList<TempFile>();
          TempFile file = null;
          String filePara = "";
          while (rs.next()) {
            long newId = rs.getLong("accessory_id");
            String name = rs.getString("accessoryname");
            String save = rs.getString("accessorysavename");
            file = new TempFile();
            file.setFileNewId(newId);
            file.setFileName(name);
            file.setFileNewName(save);
            file.setFilePath("upload/" + save.split("_")[0] + "/information/" + save);
            file.setFileType(0);
            file.setMainFlag(false);
            files.add(file);
            filePara = String.valueOf(filePara) + ",{\"fileID\":\"" + newId + "\",\"fileName\":\"" + name + "\"}";
          } 
          if (filePara.startsWith(","))
            filePara = filePara.substring(1); 
          String para = "{\"operateType\":\"save\",\"businessBean\":{\"infoContent\":\"" + infoContent + 
            "\",\"releaseStatus\":\"1\"," + "\"sendPersonID\":\"" + personID + "\",\"isReturn\":\"\",\"attFile\":" + 
            "[" + filePara + "],\"browserIDStr\":\"" + browserIDStr + "\",\"Status\":\"1\"," + 
            "\"amendments\":\"" + amendments + "\",\"oid\":\"" + oid + "\",\"urgencyLevel\":\"" + 
            urgencyLevel + "\",\"receives\":\"109137,109182\"," + "\"reportUnit\":\"" + orgID + 
            "\",\"telephone\":\"" + tel + "\",\"infoTitle\":\"" + infoTitle + "\"},\"businessID\":" + 
            "\"" + oid + "\",\"businessType\":\"information\",\"personID\":\"" + personID + "\"}";
          System.out.println("通知报送参数：" + para);
          String result = requestWebService(para);
          System.out.println("信息上报结果：" + result);
          JSONObject json = JSONObject.fromObject(result);
          if (json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
            if (json.get("oid") != null)
              oid = json.getString("oid"); 
            for (TempFile f : files) {
              f.setFileID(json.getString((new StringBuilder(String.valueOf(f.getFileNewId()))).toString()));
              canSubmit = uploadFile(realPath, "information", "", oid, personID, f);
            } 
            if (canSubmit) {
              sql = "update OA_INFORMATION set oid='" + oid + "' where information_id=" + infoId;
              int n = base.executeUpdate(sql);
              if (n > 0) {
                System.out.println("信息报送成功，本地数据oid已更新。");
                flag = true;
              } else {
                System.out.println("更新本地数据库失败。");
              } 
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.begin();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return flag;
  }
  
  public boolean updateMessage(HttpServletRequest request) {
    boolean flag = true;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String messageId = request.getParameter("messageId");
      String realPath = request.getParameter("realPath");
      StringBuffer sqlmessageId = new StringBuffer();
      sqlmessageId.append("select oid,browserIDStr,receives,infoTitle,infoContent,urgencyLevel,telephone, ");
      sqlmessageId.append("amendments,releaseStatus,isReturn,Status,reportUnit,sendPersonID,leadersIDStr");
      sqlmessageId.append("from dcq_messagesubmission");
      sqlmessageId.append("where mark=1 and messageId=" + messageId + " order by messageId desc ");
      String[][] dataArr = base.getArrayQuery(sqlmessageId.toString());
      if (dataArr != null && dataArr.length > 0) {
        String oid = (new StringBuilder(String.valueOf(dataArr[0][0]))).toString();
        String browserIDStr = (new StringBuilder(String.valueOf(dataArr[0][1]))).toString();
        String receives = (new StringBuilder(String.valueOf(dataArr[0][2]))).toString();
        String infoTitle = (new StringBuilder(String.valueOf(dataArr[0][3]))).toString();
        String infoContent = (new StringBuilder(String.valueOf(dataArr[0][4]))).toString();
        String urgencyLevel = (new StringBuilder(String.valueOf(dataArr[0][5]))).toString();
        String telephone = (new StringBuilder(String.valueOf(dataArr[0][6]))).toString();
        String amendments = (new StringBuilder(String.valueOf(dataArr[0][7]))).toString();
        String releaseStatus = (new StringBuilder(String.valueOf(dataArr[0][8]))).toString();
        String isReturn = (new StringBuilder(String.valueOf(dataArr[0][9]))).toString();
        String Status = (new StringBuilder(String.valueOf(dataArr[0][10]))).toString();
        String reportUnit = (new StringBuilder(String.valueOf(dataArr[0][11]))).toString();
        String sendPersonID = (new StringBuilder(String.valueOf(dataArr[0][12]))).toString();
        String leadersIDStr = (new StringBuilder(String.valueOf(dataArr[0][13]))).toString();
        StringBuffer sqlAttfile = new StringBuffer();
        sqlAttfile.append("select file_id,file_name,file_newname,file_path,mainflag,doc_id,doc_oid,doc_type,file_type");
        sqlAttfile.append("from  dcq_fileaccessory");
        sqlAttfile.append("where doc_id='" + messageId + "' and doc_type='information'  order by file_id desc");
        DbOpt db = new DbOpt();
        String[][] attfileArr = db.executeQueryToStrArr2(sqlAttfile.toString(), 9);
        String attfile = "";
        String fileId = "";
        if (attfileArr != null && attfileArr.length > 0)
          for (int s = 0; s < attfileArr.length; s++) {
            attfile = String.valueOf(attfile) + "{'fileID':'" + attfileArr[s][0] + "','fileName':'" + attfileArr[s][1] + "'},";
            fileId = String.valueOf(fileId) + attfileArr[s][0] + ",";
          }  
        String jsonInformation = "{\"operateType\":\"saveinfo\",\"businessBean\":{\"infoContent\":'" + infoContent + "'," + 
          "\"releaseStatus\":'" + releaseStatus + "',\"sendPersonID\":'" + sendPersonID + "',\"isReturn\":'" + isReturn + "'," + 
          "\"attFile\":['" + attfile + "'],\"browserIDStr\":'" + browserIDStr + "',\"Status\":'" + Status + "'," + 
          "\"amendments\":'" + amendments + "',\"leadersIDStr\":'" + leadersIDStr + "',\"oid\":'" + oid + "',\"urgencyLevel\":'" + urgencyLevel + "'," + 
          "\"receives\":'" + receives + "',\"reportUnit\":'" + reportUnit + "',\"telephone\":'" + telephone + "',\"infoTitle\":'" + infoTitle + "'}," + 
          "\"businessID\":'',\"businessType\":'information',\"personID\":'" + sendPersonID + "'}";
        String jsonStrNotice = requestWebService(jsonInformation);
        JSONObject jsonNotice = JSONObject.fromObject(jsonStrNotice);
        base.executeUpdate("update dcq_messagesubmission set oid='" + jsonNotice.getString("oid") + "' where messageId=" + messageId);
        TempFile tempFile = new TempFile();
        if (jsonNotice.getString("execCode") != null && "0000".equals(jsonNotice.getString("execCode"))) {
          String businessId = jsonNotice.getString("oid");
          if (fileId != null && !"".equals(fileId) && fileId.endsWith(",")) {
            fileId = fileId.substring(0, fileId.length() - 1);
            String[] filedArr = fileId.split(",");
            for (int j = 0; j < filedArr.length; j++) {
              tempFile.setDocId(Long.parseLong(messageId));
              tempFile.setDocOid(jsonNotice.getString("oid"));
              tempFile.setDocType("information");
              tempFile.setFileID(jsonNotice.getString((new StringBuilder(String.valueOf(filedArr[j]))).toString()));
              tempFile.setFileName((new StringBuilder(String.valueOf(attfileArr[0][2]))).toString());
              tempFile.setFileNewId(Long.parseLong((new StringBuilder(String.valueOf(attfileArr[0][0]))).toString()));
              tempFile.setFilePath((new StringBuilder(String.valueOf(attfileArr[0][3]))).toString());
              tempFile.setFileType(0);
              tempFile.setMainFlag(false);
              uploadFile(realPath, "information", businessId, jsonNotice.getString("oid"), sendPersonID, tempFile);
            } 
          } 
        } 
        db.close();
      } 
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        base.begin();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return flag;
  }
  
  public String getMessageStatus(String oid, String personID) {
    String result = "";
    try {
      String para = "{\"operateType\":\"details\",\"businessBean\":\"\",\"businessID\":\"" + oid + "\"," + 
        "\"businessType\":\"information\",\"personID\":\"" + personID + "\"}";
      String jsonStr = requestWebService(para);
      JSONObject json = JSONObject.fromObject(jsonStr);
      if (json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
        if (json.get("isReturn") != null) {
          if ("1".equals(json.getString("isReturn"))) {
            result = String.valueOf(result) + "退回状态：已退回";
          } else if ("2".equals(json.getString("isReturn"))) {
            result = String.valueOf(result) + "退回状态：未退回";
          } else {
            result = String.valueOf(result) + "退回状态：未知";
          } 
        } else {
          result = String.valueOf(result) + "退回状态：未知";
        } 
        if (json.get("status") != null) {
          if ("1".equals(json.getString("status"))) {
            result = String.valueOf(result) + "，录用状态：已录用。";
          } else if ("2".equals(json.getString("status"))) {
            result = String.valueOf(result) + "，录用状态：未录用。";
          } else {
            result = String.valueOf(result) + "，录用状态：未知。";
          } 
        } else {
          result = String.valueOf(result) + "，录用状态：未知。";
        } 
      } else {
        result = json.getString("execMsg");
        System.out.println("获取收文信息失败：" + json.getString("execMsg"));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = e.getMessage();
    } 
    return result;
  }
  
  private boolean getInspector(String personID, String orgID, String realPath) {
    boolean flag = false;
    String jsonStr = "";
    String para = "{'businessType':'Inspector','businessID':'','personID':'" + personID + "','businessBean':{orgID:" + orgID + "},'operateType':'worklist'} ";
    System.out.println("督查督办列表：" + para);
    System.out.println("获取督办待收列表开始。。。" + (new Date()).getTime());
    jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    System.out.println("获取督办待收列表完成。。。" + (new Date()).getTime());
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      JSONArray arr = (json.getString("GovernmentInspector") == null || "".equals(json.getString("GovernmentInspector"))) ? 
        null : json.getJSONArray("GovernmentInspector");
      if (arr != null && arr.size() > 0) {
        JSONObject jsonObj = null;
        DataSourceBase base = new DataSourceBase();
        ResultSet rs = null;
        try {
          base.begin();
          for (int i = 0; i < arr.size(); i++) {
            jsonObj = arr.getJSONObject(i);
            String sql = "SELECT oid FROM dcq_inspector WHERE oid='" + jsonObj.getString("oid") + "'";
            rs = base.executeQuery(sql);
            if (rs.next()) {
              System.out.println("督查督办信息" + jsonObj.getString("oid") + "已存在。");
            } else {
              String paraSplit = "{\"businessType\":\"Inspector\",\"businessID\":\"" + 
                jsonObj.getString("oid") + "\",\"personID\":\"" + personID + 
                "\",\"businessBean\":\"\",\"operateType\":\"details\"}";
              System.out.println(String.valueOf(i) + "获取督办详细信息开始。。。" + (new Date()).getTime());
              String jsonStrInspector = requestWebService(paraSplit);
              System.out.println(String.valueOf(i) + "获取督办详细信息结束。。。" + (new Date()).getTime());
              JSONObject jsonInspector = JSONObject.fromObject(jsonStrInspector);
              StringBuffer InsertSql = new StringBuffer();
              String docType = getJSONValueByKey(jsonObj, "type");
              String category = getJSONValueByKey(jsonInspector, "category");
              String categoryName = category;
              String hostUID = getJSONValueByKey(jsonInspector, "hostUID");
              String hostUIDName = "";
              if (hostUID != null && !"".equals(hostUID)) {
                String[] hIds = hostUID.split(",");
                if (hIds != null && hIds.length > 0) {
                  sql = "SELECT orgname FROM org_organization WHERE 1>2 ";
                  for (int j = 0; j < hIds.length; j++)
                    sql = String.valueOf(sql) + "or guid='" + hIds[j] + "'"; 
                  rs = base.executeQuery(sql);
                  while (rs.next())
                    hostUIDName = String.valueOf(hostUIDName) + rs.getString(1) + ","; 
                } 
              } 
              String otherUID = getJSONValueByKey(jsonInspector, "otherUID");
              String otherUIDName = "";
              if (otherUID != null && !"".equals(otherUID)) {
                String[] hIds = otherUID.split(",");
                if (hIds != null && hIds.length > 0) {
                  sql = "SELECT orgname FROM org_organization WHERE 1>2 ";
                  for (int j = 0; j < hIds.length; j++)
                    sql = String.valueOf(sql) + "or guid='" + hIds[j] + "'"; 
                  rs = base.executeQuery(sql);
                  while (rs.next())
                    otherUIDName = String.valueOf(otherUIDName) + rs.getString(1) + ","; 
                } 
              } 
              InsertSql.append("insert into dcq_inspector ");
              InsertSql.append("(title,type,oid,code,source,jobType,keyword,contentDigest,inspectorRequire,category,sourceUnit,person,telephone,things,deliveryTime,finishTime,userID,hostUID,otherUID,MARK)");
              InsertSql.append(" values ('" + 
                  jsonInspector.getString("title") + "', " + 
                  "'" + docType + "', " + 
                  "'" + jsonObj.getString("oid") + "', " + 
                  "'" + jsonInspector.getString("code") + "', " + 
                  "'" + jsonInspector.getString("source") + "', " + 
                  "'" + jsonInspector.getString("jobType") + "', " + 
                  "'" + jsonInspector.getString("keyword") + "', " + 
                  "'" + jsonInspector.getString("contentDigest") + jsonInspector.getString("things") + "', " + 
                  "'" + jsonInspector.getString("inspectorRequire") + "', " + 
                  "'" + categoryName + "', " + 


                  
                  "'" + jsonInspector.getString("source") + jsonInspector.getString("sourceUnit") + "', " + 
                  "'" + jsonInspector.getString("personer") + "', " + 
                  "'" + jsonInspector.getString("telephone") + "', " + 
                  "'" + jsonInspector.getString("things") + "', " + 
                  "'" + getDate(jsonInspector.getString("deliveryTime")) + "'," + 
                  "'" + getDate(jsonInspector.getString("finishTime")) + "'," + 
                  "'" + jsonInspector.getString("userID") + "', " + 
                  "'" + hostUIDName + "', " + 
                  "'" + otherUIDName + "', " + 
                  "'0')");
              int n = base.executeUpdate(InsertSql.toString());
              System.out.println(String.valueOf(i) + "督办详细信息入库完成。。。" + (new Date()).getTime());
              if (n > 0) {
                String sqlStringTitle = "select INSPECTORID from dcq_inspector where oid=" + jsonObj.getString("oid");
                DbOpt db = new DbOpt();
                String newId = db.executeQueryToStr(sqlStringTitle);
                db.close();
                JSONObject jsonInspectorDownload = null;
                String oid = jsonObj.getString("oid");
                JSONArray arrInspectorFile = (jsonInspector.get("fileArray") == null || jsonInspector.getString("fileArray") == null || "".equals(jsonInspector.getString("fileArray"))) ? 
                  null : jsonInspector.getJSONArray("fileArray");
                if (arrInspectorFile != null && arrInspectorFile.size() > 0)
                  for (int d = 0; d < arrInspectorFile.size(); d++) {
                    jsonInspectorDownload = arrInspectorFile.getJSONObject(d);
                    String fileID = jsonInspectorDownload.getString("fileID");
                    String fileName = jsonInspectorDownload.getString("fileName");
                    downFile(realPath, "Inspector", personID, newId, oid, fileID, 
                        fileName, false, "Inspector", 0);
                  }  
                JSONObject jsonInspectorIdea = null;
                JSONArray arrInspectorIdea = (jsonInspector.getString("leaderArray") == null || "".equals(jsonInspector.getString("leaderArray"))) ? 
                  null : jsonInspector.getJSONArray("leaderArray");
                if (arrInspectorIdea != null && arrInspectorIdea.size() > 0)
                  for (int d = 0; d < arrInspectorIdea.size(); d++) {
                    jsonInspectorIdea = arrInspectorIdea.getJSONObject(d);
                    String instructionsIdea = jsonInspectorIdea.getString("instructionsIdea");
                    String instructionsLeader = jsonInspectorIdea.getString("instructionsLeader");
                    String instructionsTime = getDate(jsonInspectorIdea.getString("instructionsTime"));
                    StringBuffer InsertConmonSql = new StringBuffer();
                    InsertConmonSql.append("insert into dcq_dealwithcommon ");
                    InsertConmonSql.append("(instructionsIdea,instructionsLeader, instructionsTime,businessType,oid,MARK)");
                    InsertConmonSql.append(" values ('" + instructionsIdea + "'," + 
                        "'" + instructionsLeader + "','" + getDate(instructionsTime) + "'," + 
                        "'Inspector'," + "'" + jsonObj.getString("oid") + "','0')");
                    base.executeUpdate(InsertConmonSql.toString());
                  }  
                String back = "{\"businessType\":\"Inspector\",\"operateType\":\"feedback\",\"businessID\":\"\",\"personID\":\"" + personID + 
                  "\",\"businessBean\":{\"oid\":\"" + jsonObj.getString("oid") + "\",\"orgID\":\"" + orgID + "\",\"workPlan\":\"\"," + 
                  "\"editor\":\"" + personID + "\",\"completePlan\":[],\"undertake\":[{\"situation\":\"\",\"problem\":\"\",\"time\":\"\"," + 
                  "\"unitID\":\"" + orgID + "\",\"attFile\":[]}]}}";
                String backStr = requestWebService(back);
                JSONObject backObj = JSONObject.fromObject(backStr);
                if (backObj.get("execCode") != null && "0000".equals(backObj.getString("execCode"))) {
                  System.out.println("督查督办" + jsonObj.getString("oid") + "初次反馈成功。");
                } else {
                  System.out.println("督查督办" + jsonObj.getString("oid") + "初次反馈失败。" + json.getString("execMsg"));
                } 
                System.out.println(String.valueOf(i) + "督办信息附件获取，反馈完成。。。" + (new Date()).getTime());
              } 
            } 
          } 
          flag = true;
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            base.begin();
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } else {
        System.out.println("督查督办无未接收数据。");
        flag = true;
      } 
    } else {
      flag = false;
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    System.out.println("督办信息获取完成。。。" + (new Date()).getTime());
    return flag;
  }
  
  public boolean feedbackInspector(String realPath, String personID, String oId, String orgID, String gzjh, String czwt, String cbsj) {
    boolean flag = false;
    String para = "{\"businessType\":\"Inspector\",\"operateType\":\"feedback\",\"businessID\":\"\",\"personID\":\"" + personID + 
      "\",\"businessBean\":{\"oid\":\"" + oId + "\",\"orgID\":\"" + orgID + "\",\"workPlan\":\"\"," + 
      "\"editor\":\"" + personID + "\",\"completePlan\":[],\"undertake\":[{\"situation\":\"" + gzjh + 
      "\",\"problem\":\"" + czwt + "\",\"time\":\"" + cbsj + "\",\"unitID\":\"" + orgID + 
      "\",\"attFile\":[";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "select receivefile_id,ACCESSORYNAME_FILE,ACCESSORYSAVENAME_FILE from doc_receivefile where field20='inspector' and field19='" + oId + "'";
      rs = base.executeQuery(sql);
      String fileId = "", fileName = "", fileSaveName = "";
      if (rs.next()) {
        fileId = String.valueOf(fileId) + "," + rs.getString(1);
        fileName = String.valueOf(fileName) + rs.getString(2);
        fileSaveName = String.valueOf(fileSaveName) + rs.getString(3);
      } 
      if (fileId.startsWith(","))
        fileId = fileId.substring(1); 
      String[] fileNames = (String[])null;
      String[] fileSaveNames = (String[])null;
      if (fileName != null && !"".equals(fileName)) {
        fileNames = fileName.split("\\|");
        fileSaveNames = fileSaveName.split("\\|");
        for (int i = 0; i < fileNames.length; i++) {
          para = String.valueOf(para) + "{";
          para = String.valueOf(para) + "\"fileID\":\"" + fileSaveNames[i] + "\",\"fileName\":\"" + fileNames[i] + "\"";
          para = String.valueOf(para) + "},";
        } 
      } 
      if (para.endsWith(","))
        para = para.substring(0, para.length() - 1); 
      para = String.valueOf(para) + "]}]}}";
      System.out.println("督办信息反馈参数：" + para);
      String jsonStr = requestWebService(para);
      JSONObject json = JSONObject.fromObject(jsonStr);
      if (json.get("execCode") != null && "0000".equals(json.getString("execCode"))) {
        if (fileSaveNames != null && fileSaveNames.length > 0) {
          String[] ids = fileId.split(",");
          TempFile f = null;
          for (int i = 0; i < fileSaveNames.length; i++) {
            f = new TempFile();
            f.setDocOid(oId);
            f.setDocType("inspector");
            f.setFileID(json.getString(fileSaveNames[i]));
            f.setFileName(fileNames[i]);
            f.setFileNewName(fileSaveNames[i]);
            f.setFilePath("upload/" + fileSaveNames[i].split("_")[0] + "/govdocumentmanager/" + fileSaveNames[i]);
            f.setFileType(0);
            f.setMainFlag(false);
            boolean upload = uploadFile(realPath, "Inspector", "", oId, personID, f);
            System.out.println("督查督办反馈附件--《" + fileNames[i] + "》上传==" + upload);
          } 
        } 
        sql = "insert into dcq_inspectorfeedback(oid,personID,workPlan,problem,cbTime) values('" + oId + 
          "','" + personID + "','" + gzjh + "','" + czwt + "','" + cbsj + "')";
        flag = executeUpdate(sql);
        if (flag) {
          System.out.println("督办信息反馈成功。");
        } else {
          System.out.println("督办信息反馈失败。");
        } 
      } else {
        System.out.println(json.getString("execMsg"));
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return flag;
  }
  
  public List<Object[]> selectInspectorFeedback(String oid) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    List<Object[]> list = new ArrayList();
    try {
      base.begin();
      String sql = "select workPlan,problem,cbTime from dcq_inspectorfeedback where oid='" + oid + "'";
      rs = base.executeQuery(sql);
      while (rs.next()) {
        list.add(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  private boolean getMeeting(String personID, String orgID, String realPath) {
    boolean flag = false;
    String jsonStr = "";
    String para = "{'businessType':'meeting','businessID':'','personID':'" + personID + "','businessBean':{orgID:" + orgID + "},'operateType':'worklist'}   ";
    System.out.println("会议信息列表：" + para);
    System.out.println("获取会议信息列表开始。。。" + (new Date()).getTime());
    jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    System.out.println("获取会议信息列表完成。。。" + (new Date()).getTime());
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      JSONArray arr = (json.getString("meetingInformIssue") == null || "".equals(json.getString("meetingInformIssue"))) ? 
        null : json.getJSONArray("meetingInformIssue");
      if (arr != null && arr.size() > 0) {
        JSONObject jsonObj = null;
        DataSourceBase base = new DataSourceBase();
        ResultSet rs = null;
        try {
          base.begin();
          for (int i = 0; i < arr.size(); i++) {
            jsonObj = arr.getJSONObject(i);
            String sql = "SELECT oid FROM dcq_meeting WHERE oid='" + jsonObj.getString("oid") + "'";
            rs = base.executeQuery(sql);
            if (rs.next()) {
              System.out.println("会议通知" + jsonObj.getString("oid") + "已存在。");
            } else {
              System.out.println(String.valueOf(i) + "会议详细信息开始。。。" + (new Date()).getTime());
              String paraSplit = "{\"businessType\":\"meeting\",\"businessID\":\"" + 
                jsonObj.getString("oid") + "\",\"personID\":\"" + personID + 
                "\",\"businessBean\":\"\",\"operateType\":\"details\"}";
              String jsonStrMeeting = requestWebService(paraSplit);
              System.out.println(String.valueOf(i) + "会议详细信息完成。。。" + (new Date()).getTime());
              JSONObject jsonMeeting = JSONObject.fromObject(jsonStrMeeting);
              if (jsonMeeting.getString("execCode") != null && "0000".equals(jsonMeeting.getString("execCode"))) {
                StringBuffer InsertSql = new StringBuffer();
                InsertSql.append("insert into dcq_meeting  ");
                InsertSql.append("(meetingName,beginTime,endTime,meetingSite,oid,mark,type)");
                InsertSql.append(" values ( '" + 
                    getJSONValueByKey(jsonMeeting, "meetingName") + "', " + 
                    "'" + getDate(jsonObj.getString("beginTime")) + "', " + 
                    "'" + getDate(jsonObj.getString("endTime")) + "', " + 
                    "'" + jsonObj.getString("meetingSite") + "', " + 
                    "'" + jsonObj.getString("oid") + "', " + 
                    "'" + jsonMeeting.getString("remark") + "', " + 
                    "'" + jsonMeeting.getString("type") + "')");
                int n = base.executeUpdate(InsertSql.toString());
                System.out.println(String.valueOf(i) + "会议信息入库完成。。。" + (new Date()).getTime());
                if (n > 0) {
                  String sqlStringTitle = "select meetingID from dcq_meeting where oid=" + jsonObj.getString("oid");
                  rs = base.executeQuery(sqlStringTitle);
                  String newId = "0";
                  if (rs.next())
                    newId = rs.getString(1); 
                  JSONObject jsonMeetingDownload = null;
                  String oid = jsonObj.getString("oid");
                  JSONArray arrMeetingFile = (jsonMeeting.get("attFile") == null || jsonMeeting.getString("attFile") == null || "".equals(jsonMeeting.getString("attFile"))) ? 
                    null : jsonMeeting.getJSONArray("attFile");
                  if (arrMeetingFile != null && arrMeetingFile.size() > 0)
                    for (int d = 0; d < arrMeetingFile.size(); d++) {
                      jsonMeetingDownload = arrMeetingFile.getJSONObject(d);
                      String fileID = jsonMeetingDownload.getString("fileID");
                      String fileName = jsonMeetingDownload.getString("fileName");
                      downFile(realPath, "meeting", personID, newId, oid, fileID, 
                          fileName, false, "meeting", 0);
                    }  
                  String back = "{'businessType':'meeting','businessID':'','personID':'" + personID + 
                    "','businessBean':{'oid':'" + jsonObj.getString("oid") + "','userID':'" + 
                    personID + "','orgID':'" + orgID + "','personInfo':[],'remark':''},'operateType':'feed'}";
                  String backStr = requestWebService(back);
                  JSONObject backObj = JSONObject.fromObject(backStr);
                  if (backObj.get("execCode") != null && "0000".equals(backObj.getString("execCode"))) {
                    System.out.println("会议通知信息" + jsonObj.getString("oid") + "初次反馈成功。");
                    flag = true;
                  } else {
                    System.out.println("获取收文信息" + jsonObj.getString("oid") + "初次失败：" + json.getString("execMsg"));
                  } 
                  System.out.println(String.valueOf(i) + "会议信息附件获取，反馈完成。。。" + (new Date()).getTime());
                } 
              } 
            } 
          } 
          flag = true;
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            base.end();
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } else {
        System.out.println("会议通知无未接收数据。");
        flag = true;
      } 
    } else {
      flag = false;
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    System.out.println("会议信息获取完成。。。" + (new Date()).getTime());
    return flag;
  }
  
  public List<Object[]> selectMeetingFeedback(String oid) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    List<Object[]> list = new ArrayList();
    try {
      base.begin();
      String sql = "select personname,personduty,persontel,remark from dcq_meetingfeedback where meetingOid='" + oid + "'";
      rs = base.executeQuery(sql);
      while (rs.next()) {
        list.add(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) });
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public boolean getMeetingMaterials(String personID, String orgID, String realPath) {
    boolean flag = false;
    String jsonStr = "";
    String para = "{'businessType':'data','businessID':'','personID':'" + personID + "','orgID':'" + orgID + "','businessBean':'','operateType':'worklist'}   ";
    jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      JSONArray arr = (json.getString("meetingInformIssue") == null || "".equals(json.getString("meetingInformIssue"))) ? 
        null : json.getJSONArray("meetingInformIssue");
      if (arr != null && arr.size() > 0) {
        JSONObject jsonObj = null;
        DataSourceBase base = new DataSourceBase();
        try {
          base.begin();
          for (int i = 0; i < arr.size(); i++) {
            jsonObj = arr.getJSONObject(i);
            String jsonStrMeeting = "";
            String paraSplit = "{'businessType':'data','businessID':'" + jsonObj.getString("oid") + "','personID':'" + personID + "','businessBean':''},'operateType':'dataRead'}";
            jsonStrMeeting = requestWebService(paraSplit);
            JSONObject jsonMeeting = JSONObject.fromObject(jsonStrMeeting);
            if (jsonMeeting.getString("execCode") != null && "0000".equals(jsonMeeting.getString("execCode"))) {
              StringBuffer InsertSql = new StringBuffer();
              InsertSql.append("insert into dcq_meetingMaterials  ");
              InsertSql.append(" ( dataName,beginTime, readType,dataID,oid,meetingSubject,meetingBatch,meetingType,registerDate,register,registerID,remark,MARK)");
              InsertSql.append(" values ( '" + 
                  jsonObj.getString("dataName") + "', " + 
                  "'" + getDate(jsonObj.getString("beginTime")) + "', " + 
                  "'" + jsonObj.getString("readType") + "', " + 
                  "'" + jsonObj.getString("dataID") + "', " + 
                  "'" + jsonObj.getString("oid") + "', " + 
                  "'" + jsonMeeting.getString("meetingSubject") + "', " + 
                  "'" + jsonMeeting.getString("meetingBatch") + "', " + 
                  "'" + jsonMeeting.getString("meetingType") + "', " + 
                  "'" + getDate(jsonMeeting.getString("registerDate")) + "', " + 
                  "'" + jsonMeeting.getString("register") + "', " + 
                  "'" + jsonMeeting.getString("registerID") + "', " + 
                  "'" + jsonMeeting.getString("remark") + "', " + 
                  "'0')");
              base.executeUpdate(InsertSql.toString());
              String sqlStringTitle = "select MaterialsId from dcq_meetingMaterials where oid=" + jsonObj.getString("oid");
              DbOpt db = new DbOpt();
              String newId = db.executeQueryToStr(sqlStringTitle);
              db.close();
              JSONObject jsonMeetingDownload = null;
              String oid = jsonObj.getString("oid");
              JSONArray arrMeetingFile = (jsonMeeting.getString("attFile") == null || "".equals(jsonMeeting.getString("attFile"))) ? 
                null : jsonMeeting.getJSONArray("attFile");
              if (arrMeetingFile != null && arrMeetingFile.size() > 0)
                for (int d = 0; d < arrMeetingFile.size(); d++) {
                  jsonMeetingDownload = arr.getJSONObject(d);
                  String fileID = jsonMeetingDownload.getString("fileID");
                  String fileName = jsonMeetingDownload.getString("fileName");
                  downFile(realPath, "meeting", personID, newId, oid, fileID, 
                      fileName, false, "meeting", 0);
                }  
            } 
          } 
          flag = true;
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            base.begin();
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } 
    } else {
      flag = false;
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    return flag;
  }
  
  public boolean readMeetingMaterials(String businessID, String personID, String oid, String readTime) {
    boolean flag = false;
    String para = "{'businessType':'data','businessID':'','personID':'" + personID + "','businessBean':{'oid':'" + oid + "','readTime':'" + getDate(readTime) + "'},'operateType':'dataRead'}";
    String jsonStr = requestWebService(para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      flag = true;
    } else {
      flag = false;
      System.out.println("进行阅读操作反馈信息失败：");
    } 
    return flag;
  }
  
  public boolean feedbackMeeting(String oId, String personID, String orgID, String fn, String fd, String ft, String fb) {
    boolean flag = false;
    String para = "{\"businessType\":\"meeting\",\"businessID\":\"\",\"personID\":\"" + personID + 
      "\",\"businessBean\":{\"oid\":\"" + oId + "\",\"userID\":\"" + personID + "\",\"orgID\":\"" + 
      orgID + "\",\"personInfo\":[{\"name\":\"" + fn + "\",\"job\":\"" + fd + "\",\"phone\":\"" + ft + 
      "\"}],\"remark\":\"" + fb + "\"},\"operateType\":\"feed\"}";
    String jsonStr = requestWebService(para);
    System.out.println("会议反馈参数：" + para);
    JSONObject json = JSONObject.fromObject(jsonStr);
    if (json.getString("execCode") != null && "0000".equals(json.getString("execCode"))) {
      String sql = "insert into dcq_meetingfeedback(meetingOid,personId,personname,personduty,persontel,remark) values('" + 
        oId + "','" + personID + "','" + fn + "','" + fd + 
        "','" + ft + "','" + fb + "')";
      flag = executeUpdate(sql);
      if (flag) {
        System.out.println("会议通知信息反馈成功。");
      } else {
        System.out.println("会议通知信息反馈失败。");
      } 
    } else {
      System.out.println("获取收文信息失败：" + json.getString("execMsg"));
    } 
    return flag;
  }
  
  private boolean downFile(String realPath, String businessType, String personID, String docId, String docOid, String fileOid, String fileName, boolean mainFlag, String docType, int fileType) {
    boolean flag = false;
    String para = "";
    if (mainFlag) {
      para = "{\"fileID\":\"" + docOid + "\",\"businessType\":\"" + businessType + "\",\"personID\":\"" + personID + "\",\"mainFlag\":" + mainFlag + "}";
    } else {
      para = "{\"fileID\":\"" + fileOid + "\",\"businessType\":\"" + businessType + "\",\"personID\":\"" + personID + "\",\"mainFlag\":" + mainFlag + "}";
    } 
    long len = requestWebServiceGetLength(para);
    if (len > 0L) {
      long start = 0L;
      long tLen = 0L;
      FileOutputStream fos = null;
      DataSourceBase base = new DataSourceBase();
      Calendar cal = Calendar.getInstance();
      String newFileName = String.valueOf(cal.get(1)) + "_" + cal.getTimeInMillis() + fileName.substring(fileName.lastIndexOf("."));
      String filePath = "upload/" + cal.get(1) + "/" + docType + "/" + newFileName;
      try {
        File file = new File(String.valueOf(realPath) + filePath);
        if (!file.exists())
          file.getParentFile().mkdirs(); 
        fos = new FileOutputStream(file, true);
        for (int i = 0; i < Math.ceil(len * 1.0D / 2097152.0D); i++) {
          start = i * 2097152L;
          tLen = (len - start > 2097152L) ? 2097152L : (len - start);
          para = "{\"fileID\":\"" + fileOid + "\",\"start\":\"" + start + "\",\"length\":\"" + tLen + "\",\"businessType\":\"" + businessType + "\",\"personID\":\"" + personID + "\",\"mainFlag\":" + mainFlag + "}";
          byte[] bytes = requestWebServiceGetBytes(para);
          fos.write(bytes);
        } 
        fos.flush();
        fos.close();
        base.begin();
        String sql = "insert into dcq_fileaccessory(file_oid,file_name,file_newName,file_path,mainFlag,doc_id,doc_oid,doc_type,file_type) values('" + 
          fileOid + "'," + "'" + fileName + "','" + newFileName + 
          "','" + filePath + "','" + mainFlag + "'," + docId + "," + "'" + docOid + "','" + docType + "'," + fileType + ")";
        int n = base.executeUpdate(sql);
        if (n > 0)
          flag = true; 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } else {
      System.out.println("文件大小为0，或获取失败。");
    } 
    return flag;
  }
  
  private boolean uploadFile(String realPath, String businessType, String businessId, String oid, String personID, TempFile tempFile) {
    boolean flag = false;
    if (businessType != null && personID != null && tempFile != null)
      try {
        File file = new File(String.valueOf(realPath) + tempFile.getFilePath());
        FileInputStream fis = null;
        if (file.exists() && file.isFile()) {
          long len = file.length();
          if (len > 0L) {
            String json = null;
            String fileId = tempFile.getFileID();
            int count = (int)Math.ceil(len * 1.0D / 2097152.0D);
            String fileName = tempFile.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            boolean isUpdate = tempFile.getMainFlag();
            if (tempFile.getMainFlag()) {
              fileName = "";
              fileType = "";
              fileId = oid;
            } 
            boolean isEnd = false;
            fis = new FileInputStream(file);
            byte[] fileContent = (byte[])null;
            for (int i = 0; i < count; i++) {
              long off = i * 2097152L;
              long tLen = (off + 2097152L > len) ? (len - off) : 2097152L;
              if (i == count - 1)
                isEnd = true; 
              System.out.println("上传记录==第" + i + "次，开始：" + off + "，长度：" + tLen + "，总大小：" + len);
              fileContent = new byte[(int)tLen];
              fis.read(fileContent, 0, (int)tLen);
              json = "{\"operateType\":\"save\",\"attFile\":{\"index\":\"" + (i + 1) + "\",\"fileType\":\"" + fileType + 
                "\",\"fileID\":\"" + fileId + "\",\"isEnd\":" + isEnd + ",\"allIsEnd\":" + isEnd + 
                ",\"fileName\":\"" + fileName + "\",\"mainFlag\":" + tempFile.getMainFlag() + ",\"isUpdate\":" + 
                isUpdate + "},\"businessType\":\"" + businessType + "\",\"personID\":\"" + personID + 
                "\",\"businessId\":\"" + businessId + "\"}";
              json = "{\"operateType\":\"save\",\"attFile\":{\"index\":\"" + (i + 1) + "\",\"fileType\":\"" + fileType + 
                "\",\"fileID\":\"" + fileId + "\",\"isEnd\":" + isEnd + ",\"allIsEnd\":" + isEnd + 
                ",\"fileName\":\"" + fileName + "\",\"mainFlag\":" + tempFile.getMainFlag() + ",\"isUpdate\":" + 
                isUpdate + "},\"businessType\":\"" + businessType + "\",\"businessId\":\"" + businessId + 
                "\",\"personID\":\"" + personID + "\"}";
              flag = requestWebServiceUpload(fileContent, json);
              if (!flag)
                break; 
            } 
            if (flag)
              System.out.println("附件上传成功，ID==" + tempFile.getFileID()); 
          } 
        } else {
          System.out.println("文件《" + tempFile.getFileName() + "》已丢失，上传失败。");
        } 
      } catch (Exception e) {
        e.printStackTrace();
        flag = false;
      }  
    return flag;
  }
  
  public static void main(String[] args) {}
  
  public String getDate(String dateStr) {
    String date = dateStr;
    if (dateStr != null && dateStr.endsWith(".0"))
      date = dateStr.substring(0, dateStr.length() - 2); 
    return date;
  }
  
  private String getJSONValueByKey(JSONObject json, String key) {
    if (json == null || "".equals(json.toString()) || key == null || "".equals(key))
      return ""; 
    if (json.get(key) != null)
      return json.getString(key); 
    return "";
  }
  
  public String copyFile(String realPath, String curId, String fileType) {
    StringBuffer sb = new StringBuffer();
    if (curId != null && !"".equals(curId) && fileType != null && !"".equals(fileType)) {
      String sql = "select * from dcq_fileaccessory where doc_type='" + fileType + "' and doc_id=" + curId;
      DataSourceBase base = new DataSourceBase();
      ResultSet rs = null;
      try {
        base.begin();
        rs = base.executeQuery(sql);
        File file = null;
        int count = 0;
        while (rs.next()) {
          count++;
          String filePath = rs.getString("file_path");
          file = new File(String.valueOf(realPath) + filePath);
          if (file != null && file.exists() && file.isFile()) {
            String fileNewName = rs.getString("file_newName");
            String fileName = rs.getString("file_name");
            String id = "sp_" + fileNewName.substring(0, fileNewName.lastIndexOf("."));
            File newFile = new File(String.valueOf(realPath) + "upload/" + fileNewName.split("_")[0] + "/govdocumentmanager/" + fileNewName);
            if (!newFile.exists()) {
              FileInputStream fis = new FileInputStream(file);
              FileOutputStream fos = new FileOutputStream(newFile);
              FileChannel ic = fis.getChannel();
              FileChannel oc = fos.getChannel();
              ic.transferTo(0L, ic.size(), oc);
              fis.close();
              ic.close();
              fos.close();
              oc.close();
              System.out.println("文件《" + newFile.getPath() + "》复制成功。");
            } else {
              System.out.println("文件《" + newFile.getPath() + "》已存在");
            } 
            String baseURL = BASE64.BASE64EncoderNoBR("FileName=" + fileNewName + "&name=" + fileName + "&path=govdocumentmanager");
            sb.append("<span id=\"" + id + "\" style=\"color: blue; cursor: pointer;\" " + 
                "onclick=\"window.location.href='/jsoa/download.jsp?" + baseURL + "';\">" + 
                "<img align=\"absmiddle\" src=\"/jsoa/images/fj2.gif\">" + fileName + 
                "<input name=\"accessorySaveName1\" id=\"accessorySaveName1\" type=\"hidden\" value=\"" + fileNewName + "\">" + 
                "<input name=\"accessoryName1\" " + "id=\"accessoryName1\" type=\"hidden\" value=\"" + fileName + "\">" + 
                "<input name=\"saveTempNames_0\" id=\"saveTempNames_0\" type=\"hidden\" value=\"" + fileName + "\">" + 
                "&nbsp;(" + file.length() + "K)" + 
                "</span>");
            sb.append("<img align=\"absmiddle\" style=\"cursor: pointer;\" onclick=\"delaccessory('" + 
                id + "', '8152', this)\" src=\"/jsoa/images/del.gif\">");
            continue;
          } 
          System.out.println("原文件丢失。");
        } 
        System.out.println(String.valueOf(count) + "条附件信息已加载。");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close(); 
          base.end();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } else {
      System.out.println("参数信息错误。");
    } 
    return sb.toString();
  }
}
