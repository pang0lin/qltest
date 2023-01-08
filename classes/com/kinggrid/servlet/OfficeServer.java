package com.kinggrid.servlet;

import DBstep.iDBManager2000;
import DBstep.iMsgServer2015;
import com.js.util.config.SystemCommon;
import com.js.util.util.InfoUtil;
import com.js.util.util.JSFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

public class OfficeServer extends HttpServlet {
  private iMsgServer2015 MsgObj = new iMsgServer2015();
  
  private iDBManager2000 DbaObj = new iDBManager2000();
  
  String mOption;
  
  String mUserName;
  
  String mRecordID;
  
  String mFileName;
  
  String mFileType;
  
  byte[] mFileBody;
  
  String mFileDate;
  
  String mDescript;
  
  byte[] mFileBodyDM;
  
  String mTemplate;
  
  int mTemplateId;
  
  String mBookmark = "";
  
  String mCommand = "";
  
  int mFileSize = 0;
  
  String isdm = SystemCommon.getIsdm();
  
  String databaseType = SystemCommon.getDatabaseType();
  
  String mFilePath;
  
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.mFilePath = request.getSession().getServletContext().getRealPath("");
    Boolean isAjaxDownloadTmplate = Boolean.valueOf(request.getParameter("isAjaxDownloadTemplate"));
    if (isAjaxDownloadTmplate.booleanValue()) {
      this.mTemplate = request.getParameter("fileName");
      loadTemplateFromDB();
      String path = String.valueOf(request.getSession().getServletContext().getRealPath("")) + "\\Document";
      File file = new File(path);
      if (!file.exists())
        file.mkdirs(); 
      String savePath = String.valueOf(path) + "\\" + this.mTemplate + ".doc";
      file = new File(savePath);
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
      bos.write(this.mFileBody);
      bos.close();
      PrintWriter out = response.getWriter();
      out.write(path);
      out.flush();
      file.delete();
      return;
    } 
    try {
      if (request.getMethod().equalsIgnoreCase("POST")) {
        this.MsgObj.setSendType("JSON");
        this.MsgObj.Load(request);
        this.mOption = this.MsgObj.GetMsgByName("OPTION");
        this.mUserName = this.MsgObj.GetMsgByName("USERNAME");
        System.out.println(this.mOption);
        if (this.mOption.equalsIgnoreCase("LOADFILE")) {
          this.mRecordID = this.MsgObj.GetMsgByName("RECORDID");
          this.mFileName = this.MsgObj.GetMsgByName("FILENAME");
          this.mFileType = this.MsgObj.GetMsgByName("FILETYPE");
          String viewDocHistory = this.MsgObj
            .GetMsgByName("viewDocHistory");
          boolean loadFileSuccess = false;
          if ("1".equals(viewDocHistory)) {
            loadFileSuccess = LoadFileFromDisk(this.mFileName, this.mFilePath);
          } else {
            loadFileSuccess = LoadFile();
          } 
          if (!loadFileSuccess) {
            System.out.println("打开失败!");
            this.mTemplate = this.MsgObj.GetMsgByName("TEMPLATE");
            if (loadTemplateFromDB()) {
              this.MsgObj.setmFileBody(this.mFileBody);
              this.MsgObj.Send(response, true);
            } 
          } else {
            this.MsgObj.setmFileBody(this.mFileBody);
            this.MsgObj.Send(response, true);
          } 
        } else if (this.mOption.equalsIgnoreCase("LOADTEMPLATE")) {
          this.mFileName = this.MsgObj.GetMsgByName("FILENAME");
          this.mFileType = this.MsgObj.GetMsgByName("FILETYPE");
          this.mTemplate = this.MsgObj.GetMsgByName("TEMPLATE");
          if (loadTemplateFromDB()) {
            this.MsgObj.setmFileBody(this.mFileBody);
            this.MsgObj.Send(response, true);
          } 
        } else if (this.mOption.equalsIgnoreCase("SAVEFILE")) {
          System.out.println(String.valueOf(this.mRecordID) + "文档上传中");
          this.mRecordID = this.MsgObj.GetMsgByName("RECORDID");
          this.mFileName = this.MsgObj.GetMsgByName("FILENAME");
          save2DB();
          this.MsgObj.MsgTextClear();
          this.MsgObj.Send(response, false);
        } else if (this.mOption.equalsIgnoreCase("SAVEPDF")) {
          System.out.println("文档转PDF");
          this.mRecordID = this.MsgObj.GetMsgByName("RECORDID");
          this.mFileName = this.MsgObj.GetMsgByName("FILENAME");
          this.MsgObj.MsgTextClear();
          if (this.MsgObj.MsgFileSave(String.valueOf(this.mFilePath) + "\\PDF\\" + this.mFileName))
            System.out.println(String.valueOf(this.mRecordID) + "文档已经转换成功"); 
          this.MsgObj.Send(response, false);
        } else if (this.mOption.equalsIgnoreCase("SAVETEMPLATE")) {
          this.mRecordID = this.MsgObj.GetMsgByName("RECORDID");
          this.mTemplate = this.MsgObj.GetMsgByName("TEMPLATE");
          this.mFileName = this.MsgObj.GetMsgByName("FILENAME");
          this.mFileType = this.MsgObj.GetMsgByName("FILETYPE");
          this.mFileDate = this.DbaObj.GetDateTime();
          this.mFileBody = this.MsgObj.MsgFileBody();
          this.mFileSize = this.mFileBody.length;
          this.mFilePath = "";
          this.mDescript = "通用模板";
          this.MsgObj.MsgTextClear();
          SaveTemplate();
          this.MsgObj.MsgTextClear();
        } 
        System.out.println("SendPackage");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private boolean loadTemplateFromDB() {
    boolean mResult = false;
    String Sql = "SELECT FileBody,FileSize FROM Template_File WHERE RecordID='" + this.mTemplate + "'";
    try {
      if (this.DbaObj.OpenConnection())
        try {
          ResultSet result = this.DbaObj.ExecuteQuery(Sql);
          if (result.next())
            try {
              if ("oracle".equals(this.databaseType) && 
                "0".equals(this.isdm)) {
                this.mFileSize = result.getInt("FileSize");
                GetAtBlob((
                    (OracleResultSet)result)
                    .getBLOB("FileBody"), 
                    this.mFileSize);
                mResult = true;
              } else if ("1".equals(this.isdm)) {
                this.mFileBody = InfoUtil.read_oracle_blob(
                    "Document_File", "FileBody", 
                    "RecordID", Long.valueOf(this.mRecordID));
                mResult = true;
              } else {
                this.mFileBody = result.getBytes("FileBody");
                if (result.wasNull())
                  this.mFileBody = null; 
                mResult = true;
              } 
            } catch (Exception ex) {
              System.out.println(ex.toString());
            }  
          result.close();
        } catch (SQLException e) {
          System.out.println(e.getMessage());
          mResult = false;
        }  
    } finally {
      this.DbaObj.CloseConnection();
    } 
    return mResult;
  }
  
  private void save2DB() throws IOException {
    this.mRecordID = this.MsgObj.GetMsgByName("RECORDID");
    this.mFileName = this.MsgObj.GetMsgByName("FILENAME");
    this.mFileType = this.MsgObj.GetMsgByName("FILETYPE");
    this.mFileDate = this.DbaObj.GetDateTime();
    this.mFileBody = this.MsgObj.MsgFileBody();
    this.mFileSize = this.mFileBody.length;
    String isDoc = this.MsgObj.GetMsgByName("isDoc");
    String moduleType = this.MsgObj.GetMsgByName("moduleType");
    String docName = this.MsgObj.GetMsgByName("docName");
    String isReTemplate = this.MsgObj.GetMsgByName("isReTemplate");
    this.mDescript = "通用版本";
    if (isDoc.equals("false")) {
      if (SaveFile())
        if (moduleType.equals("govdocument")) {
          String myPath = String.valueOf(this.mFilePath) + "/upload/govdocumentmanager";
          this.MsgObj.SaveFile(String.valueOf(myPath) + "/" + this.mRecordID + this.mFileType);
          this.MsgObj.SaveFile(String.valueOf(myPath) + "/" + this.mRecordID + "_wh" + this.mFileType);
        }  
    } else if (isDoc.equals("true")) {
      this.mFileType = ".doc";
      String myPath = String.valueOf(this.mFilePath) + "/iWebOfficeSign/Document";
      if (moduleType.equals("information")) {
        myPath = String.valueOf(this.mFilePath) + "/upload/information";
      } else if (moduleType.equals("govdocument")) {
        myPath = String.valueOf(this.mFilePath) + "/upload/govdocumentmanager";
      } else if (moduleType.indexOf("dossier") > -1) {
        myPath = String.valueOf(this.mFilePath) + 
          "/upload/dossier" + 
          "/sendType" + 
          moduleType
          .substring(moduleType.indexOf("dossier") + 7);
        try {
          File myFilePath = new File(myPath);
          if (!myFilePath.exists())
            myFilePath.mkdir(); 
        } catch (Exception e) {
          System.out.println("新建目录操作出错");
          e.printStackTrace();
        } 
      } 
      if ("1".equals(isReTemplate))
        JSFile.copyToFileServerPath(String.valueOf(myPath) + "/" + 
            this.mRecordID + this.mFileType, String.valueOf(this.mRecordID) + this.mFileType, 
            String.valueOf(this.mFilePath) + "/upload", "/govdocumentmanager_his/"); 
      if (moduleType.indexOf("dossier") > -1) {
        this.MsgObj.SaveFile(String.valueOf(myPath) + "/" + docName + "(带痕)" + this.mFileType);
      } else {
        this.MsgObj.SaveFile(String.valueOf(myPath) + "/" + this.mRecordID + this.mFileType);
      } 
      if ("chongqingshangtou".equals(SystemCommon.getCustomerName()))
        if (JSFile.fileIsExists(String.valueOf(this.mFilePath) + 
            "/upload/govdocumentmanager_his/" + this.mRecordID + 
            this.mFileType))
          JSFile.copyToFileServerPath(String.valueOf(myPath) + "/" + 
              this.mRecordID + this.mFileType, String.valueOf(this.mRecordID) + "_2" + 
              this.mFileType, String.valueOf(this.mFilePath) + "/upload", 
              "/govdocumentmanager_his/");  
    } 
  }
  
  private boolean SaveFile() {
    boolean mResult = false;
    int iFileId = -1;
    String Sql = "SELECT * FROM Document_File WHERE RecordID='" + this.mRecordID + 
      "'";
    try {
      if (this.DbaObj.OpenConnection()) {
        try {
          ResultSet result = this.DbaObj.ExecuteQuery(Sql);
          if (result.next()) {
            if ("oracle".equals(this.databaseType) && "0".equals(this.isdm)) {
              Sql = "update Document_File set FileID=?,RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=EMPTY_BLOB(),FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
              iFileId = result.getInt("FileId");
            } else if ("1".equals(this.isdm)) {
              Sql = "update Document_File set FileID=?,RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=EMPTY_BLOB(),FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
              iFileId = result.getInt("FileId");
            } else if ("mysql".equals(this.databaseType)) {
              Sql = "update Document_File set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
            } else {
              Sql = "update Document_File set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
            } 
          } else if ("oracle".equals(this.databaseType) && "0".equals(this.isdm)) {
            Sql = "insert into Document_File (FileID,RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,EMPTY_BLOB(),?,?,? )";
            iFileId = this.DbaObj
              .GetMaxID("Document_File", "FileId");
          } else if ("1".equals(this.isdm)) {
            Sql = "insert into Document_File (FileID,RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,EMPTY_BLOB(),?,?,? )";
            iFileId = this.DbaObj
              .GetMaxID("Document_File", "FileId");
          } else if ("mysql".equals(this.databaseType)) {
            Sql = "insert into Document_File (RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,?,?,? )";
          } else {
            Sql = "insert into Document_File (RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,?,?,? )";
          } 
          result.close();
        } catch (SQLException e) {
          System.out.println(e.toString());
          mResult = false;
        } 
        PreparedStatement prestmt = null;
        try {
          if ("oracle".equals(this.databaseType) && "0".equals(this.isdm)) {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setInt(1, iFileId);
            prestmt.setString(2, this.mRecordID);
            prestmt.setString(3, this.mFileName);
            prestmt.setString(4, this.mFileType);
            prestmt.setInt(5, this.mFileSize);
            prestmt.setDate(6, this.DbaObj.GetDate());
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            this.DbaObj.Conn.setAutoCommit(true);
            prestmt.execute();
            this.DbaObj.Conn.commit();
            prestmt.close();
            Statement stmt = null;
            this.DbaObj.Conn.setAutoCommit(false);
            stmt = this.DbaObj.Conn.createStatement();
            OracleResultSet update = (OracleResultSet)stmt
              .executeQuery("select FileBody from Document_File where Fileid=" + 
                String.valueOf(iFileId) + 
                " for update");
            if (update.next())
              try {
                PutAtBlob(
                    update
                    .getBLOB("FileBody"), 
                    this.mFileSize);
              } catch (IOException e) {
                System.out.println(e.toString());
                mResult = false;
              }  
            update.close();
            stmt.close();
            this.DbaObj.Conn.commit();
            this.mFileBody = null;
            mResult = true;
          } else if ("1".equals(this.isdm)) {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setInt(1, iFileId);
            prestmt.setString(2, this.mRecordID);
            prestmt.setString(3, this.mFileName);
            prestmt.setString(4, this.mFileType);
            prestmt.setInt(5, this.mFileSize);
            prestmt.setDate(6, this.DbaObj.GetDate());
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            this.DbaObj.Conn.setAutoCommit(true);
            prestmt.execute();
            this.DbaObj.Conn.commit();
            prestmt.close();
            InfoUtil.insert_oracle_blob("Document_File", 
                "FileBody", "Fileid", Long.valueOf(iFileId), this.mFileBody);
            this.mFileBody = null;
            mResult = true;
          } else if ("mysql".equals(this.databaseType)) {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setString(1, this.mRecordID);
            prestmt.setString(2, this.mFileName);
            prestmt.setString(3, this.mFileType);
            prestmt.setInt(4, this.mFileSize);
            prestmt.setString(5, this.mFileDate);
            prestmt.setBytes(6, this.mFileBody);
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            prestmt.execute();
            prestmt.close();
            mResult = true;
          } else {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setString(1, this.mRecordID);
            prestmt.setString(2, this.mFileName);
            prestmt.setString(3, this.mFileType);
            prestmt.setInt(4, this.mFileSize);
            prestmt.setString(5, this.mFileDate);
            prestmt.setBytes(6, this.mFileBody);
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            this.DbaObj.Conn.setAutoCommit(true);
            prestmt.execute();
            this.DbaObj.Conn.commit();
            prestmt.close();
            mResult = true;
          } 
        } catch (SQLException e) {
          System.out.println(e.toString());
          mResult = false;
        } 
      } 
    } finally {
      this.DbaObj.CloseConnection();
    } 
    return mResult;
  }
  
  private void PutAtBlob(BLOB vField, int vSize) throws IOException {
    try {
      OutputStream outstream = vField.getBinaryOutputStream();
      outstream.write(this.mFileBody, 0, vSize);
      outstream.close();
    } catch (SQLException sQLException) {}
  }
  
  private boolean LoadFileFromDisk(String fileName, String path) {
    boolean mResult = false;
    this.mFileBody = null;
    File f = new File(String.valueOf(path) + "/upload/govdocumentmanager_his/" + fileName);
    if (!f.exists()) {
      f = new File(String.valueOf(path) + "/upload/govdocumentmanager/" + fileName);
      if (!f.exists())
        return false; 
    } 
    try {
      this.mFileBodyDM = getBytesFromFile(f);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
    BufferedInputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(f));
      int buf_size = 1024;
      byte[] buffer = new byte[buf_size];
      int len = 0;
      while (-1 != (len = in.read(buffer, 0, buf_size)))
        bos.write(buffer, 0, len); 
      this.mFileBody = bos.toByteArray();
      in.close();
      bos.close();
      mResult = true;
    } catch (IOException e) {
      try {
        in.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      } 
      try {
        bos.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return mResult;
  }
  
  public byte[] getBytesFromFile(File file) throws IOException {
    InputStream is = new FileInputStream(file);
    long length = file.length();
    if (length > 2147483647L)
      throw new IOException("File is to large " + file.getName()); 
    byte[] bytes = new byte[(int)length];
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length && (
      numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
      offset += numRead; 
    if (offset < bytes.length)
      throw new IOException("Could not completely read file " + 
          file.getName()); 
    is.close();
    return bytes;
  }
  
  private boolean LoadFile() {
    boolean mResult = false;
    String Sql = "SELECT FileBody,FileSize FROM Document_File WHERE RecordID='" + 
      this.mRecordID + "'";
    try {
      if (this.DbaObj.OpenConnection())
        try {
          ResultSet result = this.DbaObj.ExecuteQuery(Sql);
          if (result.next())
            try {
              if ("oracle".equals(this.databaseType) && 
                "0".equals(this.isdm)) {
                this.mFileSize = result.getInt("FileSize");
                GetAtBlob((
                    (OracleResultSet)result)
                    .getBLOB("FileBody"), 
                    this.mFileSize);
                mResult = true;
              } else if ("1".equals(this.isdm)) {
                this.mFileBody = InfoUtil.read_oracle_blob(
                    "Document_File", "FileBody", 
                    "RecordID", Long.valueOf(this.mRecordID));
                mResult = true;
              } else {
                this.mFileBody = result.getBytes("FileBody");
                if (result.wasNull())
                  this.mFileBody = null; 
                mResult = true;
              } 
            } catch (Exception ex) {
              System.out.println(ex.toString());
            }  
          result.close();
        } catch (SQLException e) {
          System.out.println(e.getMessage());
          mResult = false;
        }  
    } finally {
      this.DbaObj.CloseConnection();
    } 
    return mResult;
  }
  
  private void GetAtBlob(BLOB vField, int vSize) throws IOException {
    try {
      this.mFileBody = new byte[vSize];
      InputStream instream = vField.getBinaryStream();
      instream.read(this.mFileBody, 0, vSize);
      instream.close();
    } catch (SQLException sQLException) {}
  }
  
  private boolean ListBookmarks() {
    boolean mResult = false;
    String Sql = "SELECT * FROM Bookmarks ";
    this.mBookmark = "";
    this.mDescript = "";
    try {
      if (this.DbaObj.OpenConnection())
        try {
          ResultSet result = this.DbaObj.ExecuteQuery(Sql);
          while (result.next()) {
            try {
              this
                .mBookmark = String.valueOf(this.mBookmark) + result.getString("BookMarkName") + "\r\n";
              this
                .mDescript = String.valueOf(this.mDescript) + result.getString("BookMarkDesc") + "\r\n";
            } catch (Exception ex) {
              System.out.println(ex.toString());
            } 
          } 
          result.close();
          mResult = true;
        } catch (SQLException e) {
          System.out.println(e.getMessage());
          mResult = false;
        }  
    } finally {
      this.DbaObj.CloseConnection();
    } 
    return mResult;
  }
  
  private boolean SaveTemplate() {
    boolean mResult = false;
    int iFileId = -1;
    String Sql = "SELECT * FROM Template_File WHERE RecordID='" + this.mRecordID + 
      "'";
    try {
      if (this.DbaObj.OpenConnection()) {
        try {
          ResultSet result = this.DbaObj.ExecuteQuery(Sql);
          if (result.next()) {
            if ("oracle".equals(this.databaseType) && "0".equals(this.isdm)) {
              Sql = "update Template_File set TemplateID=?,RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=EMPTY_BLOB(),FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
              this.mTemplateId = result.getInt("TemplateId");
            } else if ("1".equals(this.isdm)) {
              Sql = "update Template_File set TemplateID=?,RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=EMPTY_BLOB(),FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
              this.mTemplateId = result.getInt("TemplateId");
            } else if ("mysql".equals(this.databaseType)) {
              Sql = "update Template_File set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
            } else {
              Sql = "update Template_File set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,FilePath=?,UserName=?,Descript=? WHERE RecordID='" + 
                this.mRecordID + "'";
            } 
          } else if ("oracle".equals(this.databaseType) && "0".equals(this.isdm)) {
            Sql = "insert into Template_File (TemplateID,RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,EMPTY_BLOB(),?,?,? )";
            this.mTemplateId = this.DbaObj.GetMaxID("Template_File", 
                "TemplateId");
          } else if ("1".equals(this.isdm)) {
            Sql = "insert into Template_File (TemplateID,RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,EMPTY_BLOB(),?,?,? )";
            this.mTemplateId = this.DbaObj.GetMaxID("Template_File", 
                "TemplateId");
          } else if ("mysql".equals(this.databaseType)) {
            Sql = "insert into Template_File (RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,?,?,? )";
          } else {
            Sql = "insert into Template_File (RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,?,?,? )";
          } 
          result.close();
        } catch (SQLException e) {
          System.out.println(e.toString());
          mResult = false;
        } 
        PreparedStatement prestmt = null;
        try {
          if ("oracle".equals(this.databaseType) && "0".equals(this.isdm)) {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setInt(1, this.mTemplateId);
            prestmt.setString(2, this.mTemplate);
            prestmt.setString(3, this.mFileName);
            prestmt.setString(4, this.mFileType);
            prestmt.setInt(5, this.mFileSize);
            prestmt.setDate(6, this.DbaObj.GetDate());
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            this.DbaObj.Conn.setAutoCommit(true);
            prestmt.execute();
            this.DbaObj.Conn.commit();
            prestmt.close();
            Statement stmt = null;
            this.DbaObj.Conn.setAutoCommit(false);
            stmt = this.DbaObj.Conn.createStatement();
            OracleResultSet update = (OracleResultSet)stmt
              .executeQuery("select FileBody from Template_File where TEMPLATEID=" + 
                String.valueOf(this.mTemplateId) + 
                " for update");
            if (update.next())
              try {
                this.mFileSize = this.mFileBody.length;
                PutAtBlob(
                    update
                    .getBLOB("FileBody"), 
                    this.mFileSize);
              } catch (IOException e) {
                System.out.println(e.toString());
                mResult = false;
              }  
            update.close();
            stmt.close();
            this.DbaObj.Conn.commit();
            this.mFileBody = null;
            mResult = true;
          } else if ("1".equals(this.isdm)) {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setInt(1, this.mTemplateId);
            prestmt.setString(2, this.mTemplate);
            prestmt.setString(3, this.mFileName);
            prestmt.setString(4, this.mFileType);
            prestmt.setInt(5, this.mFileSize);
            prestmt.setDate(6, this.DbaObj.GetDate());
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            this.DbaObj.Conn.setAutoCommit(true);
            prestmt.execute();
            this.DbaObj.Conn.commit();
            prestmt.close();
            InfoUtil.insert_oracle_blob("Template_File", 
                "FileBody", "TEMPLATEID", Long.valueOf(this.mTemplateId), 
                this.mFileBody);
            this.mFileBody = null;
            mResult = true;
          } else if ("mysql".equals(this.databaseType)) {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setString(1, this.mRecordID);
            prestmt.setString(2, this.mFileName);
            prestmt.setString(3, this.mFileType);
            prestmt.setInt(4, this.mFileSize);
            prestmt.setString(5, this.mFileDate);
            prestmt.setBytes(6, this.mFileBody);
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            prestmt.execute();
            prestmt.close();
            mResult = true;
          } else {
            prestmt = this.DbaObj.Conn.prepareStatement(Sql);
            prestmt.setString(1, this.mRecordID);
            prestmt.setString(2, this.mFileName);
            prestmt.setString(3, this.mFileType);
            prestmt.setInt(4, this.mFileSize);
            prestmt.setString(5, this.mFileDate);
            prestmt.setBytes(6, this.mFileBody);
            prestmt.setString(7, this.mFilePath);
            prestmt.setString(8, this.mUserName);
            prestmt.setString(9, this.mDescript);
            this.DbaObj.Conn.setAutoCommit(true);
            prestmt.execute();
            this.DbaObj.Conn.commit();
            prestmt.close();
            mResult = true;
          } 
        } catch (SQLException e) {
          System.out.println(e.toString());
          mResult = false;
        } 
      } 
    } finally {
      this.DbaObj.CloseConnection();
    } 
    return mResult;
  }
}
