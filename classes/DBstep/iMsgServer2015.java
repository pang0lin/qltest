package DBstep;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

public class iMsgServer2015 {
  private Hashtable<String, String> saveFormParam = new Hashtable<String, String>();
  
  private Hashtable<String, String> sendFormParam = new Hashtable<String, String>();
  
  private InputStream fileContentStream = null;
  
  private String fileName = "";
  
  private byte[] mFileBody = null;
  
  public void setmFileBody(byte[] mFileBody) {
    this.mFileBody = mFileBody;
  }
  
  private boolean isLoadFile = false;
  
  private String sendType = "";
  
  private static final String MsgError = "404";
  
  public String getSendType() {
    return this.sendType;
  }
  
  public void setSendType(String sendType) {
    this.sendType = sendType;
  }
  
  public void Load(HttpServletRequest request) throws FileUploadException, IOException {
    request.setCharacterEncoding("gb2312");
    DefaultFileItemFactory diskFileItemFactory = new DefaultFileItemFactory();
    DiskFileUpload fileUpload = new DiskFileUpload(diskFileItemFactory);
    List<FileItem> fileList = fileUpload.parseRequest(request);
    if (fileList != null && fileList.size() > 0)
      for (int i = 0; i < fileList.size(); i++) {
        FileItem item = fileList.get(i);
        if (item.isFormField()) {
          processFormField(item);
        } else {
          processUploadedFile(item);
        } 
      }  
  }
  
  public void processFormField(FileItem item) throws UnsupportedEncodingException {
    String fieldName = item.getFieldName();
    String fieldValue = "";
    fieldValue = item.getString("utf-8");
    if (this.sendType.equalsIgnoreCase("JSON")) {
      JSONObject json = JSONObject.fromObject(fieldValue);
      Iterator<String> iter = json.keySet().iterator();
      while (iter.hasNext()) {
        fieldName = iter.next();
        fieldValue = json.getString(fieldName);
        this.saveFormParam.put(fieldName, fieldValue);
      } 
      return;
    } 
    this.saveFormParam.put(fieldName, fieldValue);
  }
  
  public void processUploadedFile(FileItem item) throws IOException {
    this.fileName = item.getName();
    if (this.fileName.indexOf("/") >= 0) {
      this.fileName = this.fileName.substring(this.fileName.lastIndexOf("/") + 1);
    } else if (this.fileName.indexOf("\\") >= 0) {
      this.fileName = this.fileName.substring(this.fileName.lastIndexOf("\\") + 1);
    } 
    this.fileContentStream = item.getInputStream();
  }
  
  public String GetMsgByName(String fieldName) {
    return this.saveFormParam.get(fieldName);
  }
  
  public void MsgTextClear() {
    this.saveFormParam.clear();
  }
  
  public void SetMsgByName(String fieldName, String value) {
    this.saveFormParam.put(fieldName, value);
  }
  
  public byte[] MsgFileBody() throws IOException {
    this.mFileBody = null;
    this.isLoadFile = false;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = this.fileContentStream.read(buffer)))
      output.write(buffer, 0, n); 
    this.mFileBody = output.toByteArray();
    return this.mFileBody;
  }
  
  public boolean MsgFileSave(String outputFile) {
    try {
      File f = new File(outputFile);
      FileOutputStream fos = null;
      int BUFFER_SIZE = 1024;
      byte[] buf = new byte[BUFFER_SIZE];
      int size = 0;
      fos = new FileOutputStream(f);
      while ((size = this.fileContentStream.read(buf)) != -1)
        fos.write(buf, 0, size); 
      fos.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public boolean SaveFile(String outputFile) {
    try {
      File f = new File(outputFile);
      FileOutputStream fos = null;
      fos = new FileOutputStream(f);
      fos.write(this.mFileBody);
      fos.flush();
      fos.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public boolean MsgFileLoad(String fileName) throws IOException {
    File file = new File(fileName);
    if (file.exists()) {
      this.fileContentStream = new FileInputStream(new File(fileName));
      MsgFileBody();
    } else {
      this.mFileBody = new byte[0];
    } 
    this.isLoadFile = true;
    return true;
  }
  
  public void load() {}
  
  public void Send(HttpServletResponse response, boolean isLoadFile) throws IOException {
    try {
      if (isLoadFile)
        if (this.mFileBody.length != 0) {
          response.setCharacterEncoding("utf-8");
          response.setContentType("application/x-msdownload;charset=utf-8");
          response.setContentLength(this.mFileBody.length);
          response.setHeader("Content-Disposition", "attachment;filename=");
          response.getOutputStream().write(this.mFileBody, 0, this.mFileBody.length);
        } else {
          response.setHeader("MsgError", "404");
        }  
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception exception) {}
  }
}
