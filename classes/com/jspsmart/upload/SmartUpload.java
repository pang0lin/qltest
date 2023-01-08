package com.jspsmart.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class SmartUpload {
  private int m_totalBytes = 0;
  
  private int m_currentIndex = 0;
  
  private int m_startData = 0;
  
  private int m_endData = 0;
  
  private String m_boundary = "";
  
  private long m_totalMaxFileSize = 0L;
  
  private long m_maxFileSize = 0L;
  
  private Vector m_deniedFilesList = new Vector();
  
  private Vector m_allowedFilesList = new Vector();
  
  private boolean m_denyPhysicalPath = false;
  
  private String m_contentDisposition = "";
  
  private Files m_files = new Files();
  
  private Request m_formRequest = new Request();
  
  private String m_charset = "utf-8";
  
  protected byte[] m_binArray;
  
  protected HttpServletRequest m_request = null;
  
  protected HttpServletResponse m_response = null;
  
  protected ServletContext m_application = null;
  
  public static final int SAVE_AUTO = 0;
  
  public static final int SAVE_VIRTUAL = 1;
  
  public static final int SAVE_PHYSICAL = 2;
  
  private String[] FileNames;
  
  public final void init(ServletConfig servletconfig) throws ServletException {
    this.m_application = servletconfig.getServletContext();
  }
  
  public void service(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException {
    this.m_request = httpservletrequest;
    this.m_response = httpservletresponse;
  }
  
  public final void initialize(ServletConfig servletconfig, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException {
    this.m_application = servletconfig.getServletContext();
    this.m_request = httpservletrequest;
    this.m_response = httpservletresponse;
  }
  
  public final void initialize(PageContext pagecontext) throws ServletException {
    this.m_application = pagecontext.getServletContext();
    this.m_request = (HttpServletRequest)pagecontext.getRequest();
    this.m_response = (HttpServletResponse)pagecontext.getResponse();
  }
  
  public final void initialize(ServletContext servletcontext, HttpSession httpsession, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, JspWriter jspwriter) throws ServletException {
    this.m_application = servletcontext;
    this.m_request = httpservletrequest;
    this.m_response = httpservletresponse;
  }
  
  public void upload() throws ServletException, IOException, SmartUploadException {
    int i = 0;
    boolean flag = false;
    long l = 0L;
    String s = "";
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";
    String s5 = "";
    String s6 = "";
    this.m_totalBytes = this.m_request.getContentLength();
    this.m_binArray = new byte[this.m_totalBytes];
    for (; i < this.m_totalBytes; i += j) {
      int j;
      try {
        this.m_request.getInputStream();
        j = this.m_request.getInputStream().read(this.m_binArray, i, this.m_totalBytes - i);
      } catch (Exception exception) {
        throw new SmartUploadException("Unable to upload.");
      } 
    } 
    for (; !flag && this.m_currentIndex < this.m_totalBytes; this.m_currentIndex++) {
      if (this.m_binArray[this.m_currentIndex] == 13) {
        flag = true;
      } else {
        this.m_boundary = String.valueOf(this.m_boundary) + (char)this.m_binArray[this.m_currentIndex];
      } 
    } 
    if (this.m_currentIndex == 1)
      return; 
    this.m_currentIndex++;
    while (this.m_currentIndex < this.m_totalBytes) {
      String s7 = getDataHeader();
      this.m_currentIndex += 2;
      boolean flag1 = (s7.indexOf("filename") > 0);
      String s8 = getDataFieldValue(s7, "name");
      if (flag1) {
        s2 = getDataFieldValue(s7, "filename");
        s = getFileName(s2);
        s1 = getFileExt(s);
        s3 = getContentType(s7);
        s4 = getContentDisp(s7);
        s5 = getTypeMIME(s3);
        s6 = getSubTypeMIME(s3);
      } 
      getDataSection();
      if (flag1 && s.length() > 0) {
        if (this.m_deniedFilesList.contains(s1))
          throw new SecurityException("The extension of the file is denied to be uploaded (1015)."); 
        if (!this.m_allowedFilesList.isEmpty() && !this.m_allowedFilesList.contains(s1))
          throw new SecurityException("The extension of the file is not allowed to be uploaded (1010)."); 
        if (this.m_maxFileSize > 0L && (this.m_endData - this.m_startData + 1) > this.m_maxFileSize)
          throw new SecurityException("Size exceeded for this file : " + s + " (1105)."); 
        l += (this.m_endData - this.m_startData + 1);
        if (this.m_totalMaxFileSize > 0L && l > this.m_totalMaxFileSize)
          throw new SecurityException("Total File Size exceeded (1110)."); 
      } 
      if (flag1) {
        File file = new File();
        file.setParent(this);
        file.setFieldName(s8);
        file.setFileName(s);
        file.setFileExt(s1);
        file.setFilePathName(s2);
        file.setIsMissing((s2.length() == 0));
        file.setContentType(s3);
        file.setContentDisp(s4);
        file.setTypeMIME(s5);
        file.setSubTypeMIME(s6);
        if (s3.indexOf("application/x-macbinary") > 0)
          this.m_startData += 128; 
        file.setSize(this.m_endData - this.m_startData + 1);
        file.setStartData(this.m_startData);
        file.setEndData(this.m_endData);
        this.m_files.addFile(file);
      } else {
        String s9 = new String(this.m_binArray, this.m_startData, this.m_endData - this.m_startData + 1, this.m_charset);
        this.m_formRequest.putParameter(s8, s9);
      } 
      if ((char)this.m_binArray[this.m_currentIndex + 1] == '-')
        break; 
      this.m_currentIndex += 2;
    } 
  }
  
  public int save(String s) throws ServletException, IOException, SmartUploadException {
    return save(s, 0);
  }
  
  public int save(String s, int i) throws ServletException, IOException, SmartUploadException {
    int j = 0;
    if (s == null)
      s = this.m_application.getRealPath("/"); 
    if (s.indexOf("/") != -1) {
      if (s.charAt(s.length() - 1) != '/')
        s = String.valueOf(s) + "/"; 
    } else if (s.charAt(s.length() - 1) != '\\') {
      s = String.valueOf(s) + "\\";
    } 
    this.FileNames = new String[this.m_files.getCount()];
    for (int k = 0; k < this.m_files.getCount(); k++) {
      if (!this.m_files.getFile(k).isMissing()) {
        this.m_files.getFile(k).saveAs(String.valueOf(s) + this.m_files.getFile(k).getFileName(), i);
        this.FileNames[j] = String.valueOf(s) + this.m_files.getFile(k).getFileName();
        j++;
      } 
    } 
    return j;
  }
  
  public String[] getFileNames() {
    String[] as = new String[this.FileNames.length];
    System.arraycopy(this.FileNames, 0, as, 0, this.FileNames.length);
    return as;
  }
  
  public int getSize() {
    return this.m_totalBytes;
  }
  
  public byte getBinaryData(int i) {
    byte byte0;
    try {
      byte0 = this.m_binArray[i];
    } catch (Exception exception) {
      throw new ArrayIndexOutOfBoundsException("Index out of range (1005).");
    } 
    return byte0;
  }
  
  public Files getFiles() {
    return this.m_files;
  }
  
  public Request getRequest() {
    return this.m_formRequest;
  }
  
  public void downloadFile(String s) throws ServletException, IOException, SmartUploadException {
    downloadFile(s, null, null);
  }
  
  public void downloadFile(String s, String s1) throws ServletException, IOException, SmartUploadException, SmartUploadException {
    downloadFile(s, s1, null);
  }
  
  public void downloadFile(String s, String s1, String s2) throws ServletException, IOException, SmartUploadException {
    downloadFile(s, s1, s2, 65000);
  }
  
  public void downloadFile(String s, String s1, String s2, int i) throws ServletException, IOException, SmartUploadException {
    if (s == null)
      throw new IllegalArgumentException("File '" + s + "' not found (1040)."); 
    if (s.equals(""))
      throw new IllegalArgumentException("File '" + s + "' not found (1040)."); 
    if (!isVirtual(s) && this.m_denyPhysicalPath)
      throw new SecurityException("Physical path is denied (1035)."); 
    if (isVirtual(s))
      s = this.m_application.getRealPath(s); 
    File file = new File(s);
    FileInputStream fileinputstream = new FileInputStream(file);
    long l = file.length();
    int j = 0;
    byte[] abyte0 = new byte[i];
    if (s1 == null) {
      this.m_response.setContentType("application/x-msdownload");
    } else if (s1.length() == 0) {
      this.m_response.setContentType("application/x-msdownload");
    } else {
      this.m_response.setContentType(s1);
    } 
    this.m_response.setContentLength((int)l);
    this.m_contentDisposition = (this.m_contentDisposition == null) ? "attachment;" : this.m_contentDisposition;
    if (s2 == null) {
      this.m_response.setHeader("Content-Disposition", String.valueOf(this.m_contentDisposition) + " filename=" + getFileName(s));
    } else if (s2.length() == 0) {
      this.m_response.setHeader("Content-Disposition", this.m_contentDisposition);
    } else {
      this.m_response.setHeader("Content-Disposition", String.valueOf(this.m_contentDisposition) + " filename=" + s2);
    } 
    while (j < l) {
      int k = fileinputstream.read(abyte0, 0, i);
      j += k;
      this.m_response.getOutputStream().write(abyte0, 0, k);
    } 
    fileinputstream.close();
  }
  
  public void downloadField(ResultSet resultset, String s, String s1, String s2) throws ServletException, IOException, SQLException {
    if (resultset == null)
      throw new IllegalArgumentException("The RecordSet cannot be null (1045)."); 
    if (s == null)
      throw new IllegalArgumentException("The columnName cannot be null (1050)."); 
    if (s.length() == 0)
      throw new IllegalArgumentException("The columnName cannot be empty (1055)."); 
    byte[] abyte0 = resultset.getBytes(s);
    if (s1 == null) {
      this.m_response.setContentType("application/x-msdownload");
    } else if (s1.length() == 0) {
      this.m_response.setContentType("application/x-msdownload");
    } else {
      this.m_response.setContentType(s1);
    } 
    this.m_response.setContentLength(abyte0.length);
    if (s2 == null) {
      this.m_response.setHeader("Content-Disposition", "attachment;");
    } else if (s2.length() == 0) {
      this.m_response.setHeader("Content-Disposition", "attachment;");
    } else {
      this.m_response.setHeader("Content-Disposition", "attachment; filename=" + s2);
    } 
    this.m_response.getOutputStream().write(abyte0, 0, abyte0.length);
  }
  
  public void fieldToFile(ResultSet resultset, String s, String s1) throws ServletException, IOException, SmartUploadException, SQLException {
    try {
      if (this.m_application.getRealPath(s1) != null)
        s1 = this.m_application.getRealPath(s1); 
      InputStream inputstream = resultset.getBinaryStream(s);
      FileOutputStream fileoutputstream = new FileOutputStream(s1);
      int i;
      while ((i = inputstream.read()) != -1)
        fileoutputstream.write(i); 
      fileoutputstream.close();
    } catch (Exception exception) {
      throw new SmartUploadException("Unable to save file from the DataBase (1020).");
    } 
  }
  
  private String getDataFieldValue(String s, String s1) {
    String s2 = "";
    String s3 = "";
    int i = 0;
    s2 = String.valueOf(s1) + "=" + '"';
    i = s.indexOf(s2);
    if (i > 0) {
      int j = i + s2.length();
      int k = j;
      s2 = "\"";
      int l = s.indexOf(s2, j);
      if (k > 0 && l > 0)
        s3 = s.substring(k, l); 
    } 
    return s3;
  }
  
  private String getFileExt(String s) {
    int i = 0;
    int j = 0;
    if (s == null)
      return null; 
    i = s.lastIndexOf('.') + 1;
    j = s.length();
    String s1 = s.substring(i, j);
    if (s.lastIndexOf('.') > 0)
      return s1; 
    return "";
  }
  
  private String getContentType(String s) {
    String s1 = "";
    String s2 = "";
    int i = 0;
    s1 = "Content-Type:";
    i = s.indexOf(s1) + s1.length();
    if (i != -1) {
      int j = s.length();
      s2 = s.substring(i, j);
    } 
    return s2;
  }
  
  private String getTypeMIME(String s) {
    int i = 0;
    i = s.indexOf("/");
    if (i != -1)
      return s.substring(1, i); 
    return s;
  }
  
  private String getSubTypeMIME(String s) {
    int i = 0;
    i = s.indexOf("/") + 1;
    if (i != -1) {
      int j = s.length();
      return s.substring(i, j);
    } 
    return s;
  }
  
  private String getContentDisp(String s) {
    String s1 = "";
    int i = 0;
    int j = 0;
    i = s.indexOf(":") + 1;
    j = s.indexOf(";");
    s1 = s.substring(i, j);
    return s1;
  }
  
  private void getDataSection() {
    int i = this.m_currentIndex;
    int j = 0;
    int k = this.m_boundary.length();
    this.m_startData = this.m_currentIndex;
    this.m_endData = 0;
    while (i < this.m_totalBytes) {
      if (this.m_binArray[i] == (byte)this.m_boundary.charAt(j)) {
        if (j == k - 1) {
          this.m_endData = i - k + 1 - 3;
          break;
        } 
        i++;
        j++;
        continue;
      } 
      i++;
      j = 0;
    } 
    this.m_currentIndex = this.m_endData + k + 3;
  }
  
  private String getDataHeader() throws ServletException, IOException, SmartUploadException {
    int i = this.m_currentIndex;
    int j = 0;
    boolean flag = false;
    while (!flag) {
      if (this.m_binArray[this.m_currentIndex] == 13 && this.m_binArray[this.m_currentIndex + 2] == 13) {
        flag = true;
        j = this.m_currentIndex - 1;
        this.m_currentIndex += 2;
        continue;
      } 
      this.m_currentIndex++;
    } 
    String s = new String(this.m_binArray, i, j - i + 1, this.m_charset);
    return s;
  }
  
  private String getFileName(String s) {
    int i = 0;
    i = s.lastIndexOf('/');
    if (i != -1)
      return s.substring(i + 1, s.length()); 
    i = s.lastIndexOf('\\');
    if (i != -1)
      return s.substring(i + 1, s.length()); 
    return s;
  }
  
  public void setDeniedFilesList(String s) throws ServletException, IOException, SQLException {
    if (s != null) {
      String s1 = "";
      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == ',') {
          if (!this.m_deniedFilesList.contains(s1))
            this.m_deniedFilesList.addElement(s1); 
          s1 = "";
        } else {
          s1 = String.valueOf(s1) + s.charAt(i);
        } 
      } 
      if (!s1.equals(""))
        this.m_deniedFilesList.addElement(s1); 
    } else {
      this.m_deniedFilesList = null;
    } 
  }
  
  public void setAllowedFilesList(String s) {
    if (s != null) {
      String s1 = "";
      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == ',') {
          if (!this.m_allowedFilesList.contains(s1))
            this.m_allowedFilesList.addElement(s1); 
          s1 = "";
        } else {
          s1 = String.valueOf(s1) + s.charAt(i);
        } 
      } 
      if (!s1.equals(""))
        this.m_allowedFilesList.addElement(s1); 
    } else {
      this.m_allowedFilesList = null;
    } 
  }
  
  public void setDenyPhysicalPath(boolean flag) {
    this.m_denyPhysicalPath = flag;
  }
  
  public void setForcePhysicalPath(boolean flag) {}
  
  public void setContentDisposition(String s) {
    this.m_contentDisposition = s;
  }
  
  public void setTotalMaxFileSize(long l) {
    this.m_totalMaxFileSize = l;
  }
  
  public void setMaxFileSize(long l) {
    this.m_maxFileSize = l;
  }
  
  public void setCharset(String s) {
    this.m_charset = s;
  }
  
  protected String getPhysicalPath(String s, int i) throws IOException {
    String s1 = "";
    String s2 = "";
    String s3 = "";
    boolean flag = false;
    s3 = System.getProperty("file.separator");
    if (s == null)
      throw new IllegalArgumentException("There is no specified destination file (1140)."); 
    if (s.equals(""))
      throw new IllegalArgumentException("There is no specified destination file (1140)."); 
    if (s.lastIndexOf("\\") >= 0) {
      s1 = s.substring(0, s.lastIndexOf("\\"));
      s2 = s.substring(s.lastIndexOf("\\") + 1);
    } 
    if (s.lastIndexOf("/") >= 0) {
      s1 = s.substring(0, s.lastIndexOf("/"));
      s2 = s.substring(s.lastIndexOf("/") + 1);
    } 
    s1 = (s1.length() == 0) ? "/" : s1;
    File file = new File(s1);
    if (file.exists())
      flag = true; 
    if (i == 0) {
      if (isVirtual(s1)) {
        s1 = this.m_application.getRealPath(s1);
        if (s1.endsWith(s3)) {
          s1 = String.valueOf(s1) + s2;
        } else {
          s1 = String.valueOf(s1) + s3 + s2;
        } 
        return s1;
      } 
      if (flag) {
        if (this.m_denyPhysicalPath)
          throw new IllegalArgumentException("Physical path is denied (1125)."); 
        return s;
      } 
      throw new IllegalArgumentException("This path does not exist (1135).");
    } 
    if (i == 1) {
      if (isVirtual(s1)) {
        s1 = this.m_application.getRealPath(s1);
        if (s1.endsWith(s3)) {
          s1 = String.valueOf(s1) + s2;
        } else {
          s1 = String.valueOf(s1) + s3 + s2;
        } 
        return s1;
      } 
      if (flag)
        throw new IllegalArgumentException("The path is not a virtual path."); 
      throw new IllegalArgumentException("This path does not exist (1135).");
    } 
    if (i == 2) {
      if (flag) {
        if (this.m_denyPhysicalPath)
          throw new IllegalArgumentException("Physical path is denied (1125)."); 
        return s;
      } 
      if (isVirtual(s1))
        throw new IllegalArgumentException("The path is not a physical path."); 
      throw new IllegalArgumentException("This path does not exist (1135).");
    } 
    return null;
  }
  
  public void uploadInFile(String s) throws IOException, SmartUploadException {
    int i = 0;
    int j = 0;
    if (s == null)
      throw new IllegalArgumentException("There is no specified destination file (1025)."); 
    if (s.length() == 0)
      throw new IllegalArgumentException("There is no specified destination file (1025)."); 
    if (!isVirtual(s) && this.m_denyPhysicalPath)
      throw new SecurityException("Physical path is denied (1035)."); 
    i = this.m_request.getContentLength();
    this.m_binArray = new byte[i];
    for (; j < i; j += k) {
      int k;
      try {
        k = this.m_request.getInputStream().read(this.m_binArray, j, i - j);
      } catch (Exception exception) {
        throw new SmartUploadException("Unable to upload.");
      } 
    } 
    if (isVirtual(s))
      s = this.m_application.getRealPath(s); 
    try {
      File file = new File(s);
      FileOutputStream fileoutputstream = new FileOutputStream(file);
      fileoutputstream.write(this.m_binArray);
      fileoutputstream.close();
    } catch (Exception exception1) {
      throw new SmartUploadException("The Form cannot be saved in the specified file (1030).");
    } 
  }
  
  private boolean isVirtual(String s) {
    if (this.m_application.getRealPath(s) != null) {
      File file = new File(this.m_application.getRealPath(s));
      return file.exists();
    } 
    return false;
  }
}
