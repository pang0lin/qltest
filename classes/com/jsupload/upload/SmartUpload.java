package com.jsupload.upload;

import com.js.lang.NoOpEntityResolver;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import com.js.util.util.DESFileUtil;
import com.js.util.util.JSFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SmartUpload {
  private long MAX_SIZE = 104857600L;
  
  private String[] allowType;
  
  public static String[] banType = null;
  
  private static String noEncryptTypes = null;
  
  private String uploadFileTempPath;
  
  private int tempBufferSize = 4096;
  
  private DiskFileItemFactory dfif = null;
  
  private ServletFileUpload sfu = null;
  
  private HttpServletRequest request = null;
  
  private List fileList = null;
  
  private String errorMsg;
  
  static {
    if (banType == null || noEncryptTypes == null) {
      String path = "";
      path = System.getProperty("user.dir");
      if (!"".equals(path)) {
        String configFile = String.valueOf(path) + "/jsconfig/upload-type-config.xml";
        try {
          FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
          SAXBuilder builder = new SAXBuilder();
          builder.setValidation(false);
          builder.setEntityResolver(new NoOpEntityResolver());
          Document doc = builder.build(configFileInputStream);
          Element rootNode = doc.getRootElement();
          Element typeElement = rootNode.getChild("ban-types");
          List<Element> nodeList = typeElement.getChildren("type");
          ArrayList<String> banTypeList = new ArrayList();
          String banTypeString = "";
          int i;
          for (i = 0; i < nodeList.size(); i++) {
            Element node = nodeList.get(i);
            banTypeString = node.getAttributeValue("ext");
            banTypeList.add(banTypeString);
          } 
          banType = banTypeList.<String>toArray(new String[0]);
          banTypeList.clear();
          nodeList.clear();
          typeElement = rootNode.getChild("noencrypt-types");
          if (typeElement != null) {
            noEncryptTypes = "";
            nodeList = typeElement.getChildren("type");
            for (i = 0; i < nodeList.size(); i++)
              noEncryptTypes = String.valueOf(noEncryptTypes) + ((Element)nodeList.get(i)).getAttributeValue("ext") + ","; 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public void initialize(HttpServletRequest request) {
    this.request = request;
    this.uploadFileTempPath = String.valueOf(request.getSession().getServletContext().getRealPath("/")) + "uploadtemp";
    this.dfif = new DiskFileItemFactory();
    this.dfif.setSizeThreshold(this.tempBufferSize);
    this.dfif.setRepository(new File(this.uploadFileTempPath));
    this.sfu = new ServletFileUpload((FileItemFactory)this.dfif);
    this.sfu.setSizeMax(this.MAX_SIZE);
  }
  
  public void saveAs(FileItem fileItem, String destFilePathName) throws Exception {
    if (destFilePathName.indexOf("./") >= 0 || destFilePathName.indexOf(".\\") >= 0) {
      System.out.println("不允许上传文件至upload以外的目录！");
      throw new Exception("不允许上传文件至upload以外的目录！");
    } 
    String sufix = "";
    if (destFilePathName.indexOf(".") >= 0)
      sufix = destFilePathName.substring(destFilePathName.lastIndexOf(".")); 
    System.out.println("上传文件：" + destFilePathName);
    if (!checkSuffix(sufix) || destFilePathName.toLowerCase().indexOf(".jsp") >= 0) {
      System.out.println("不允许上传非法类型的文件！" + destFilePathName);
      throw new Exception("不允许上传非法类型的文件！");
    } 
    if ("1".equals(SysSetupReader.getAccessoryEncrypt("0")) && fileNeedEncrypt(destFilePathName)) {
      byte[] fileByte = fileItem.get();
      File file = new File(destFilePathName);
      OutputStream os = new FileOutputStream(file);
      byte[] result = (new DESFileUtil()).desCrypto(fileByte);
      os.write(result);
      os.close();
    } else {
      fileItem.write(new File(destFilePathName));
    } 
    if (SystemCommon.getUseClusterServer() == 1) {
      JSFile.copyToFileServer(destFilePathName, 
          SystemCommon.getClusterServerPath());
      if (!"datas".equals(this.request.getParameter("path")))
        JSFile.delete(destFilePathName); 
    } 
  }
  
  public long getMAX_SIZE() {
    return this.MAX_SIZE;
  }
  
  public void setMAX_SIZE(long max_size) {
    this.MAX_SIZE = max_size;
    if (this.sfu != null)
      this.sfu.setSizeMax(this.MAX_SIZE); 
  }
  
  public String[] getAllowType() {
    return this.allowType;
  }
  
  public void setAllowType(String[] allowType) {
    this.allowType = allowType;
  }
  
  public String getUploadFileTempPath() {
    return this.uploadFileTempPath;
  }
  
  public void setUploadFileTempPath(String uploadFileTempPath) {
    this.uploadFileTempPath = uploadFileTempPath;
  }
  
  public long getTempBufferSize() {
    return this.tempBufferSize;
  }
  
  public void setTempBufferSize(int tempBufferSize) {
    this.tempBufferSize = tempBufferSize;
  }
  
  public DiskFileItemFactory getDfif() {
    return this.dfif;
  }
  
  public void setDfif(DiskFileItemFactory dfif) {
    this.dfif = dfif;
  }
  
  public ServletFileUpload getSfu() {
    return this.sfu;
  }
  
  public void setSfu(ServletFileUpload sfu) {
    this.sfu = sfu;
  }
  
  public HttpServletRequest getRequest() {
    return this.request;
  }
  
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }
  
  public List getFileList() throws Exception {
    this.fileList = this.sfu.parseRequest(this.request);
    return this.fileList;
  }
  
  public void setFileList(List fileList) {
    this.fileList = fileList;
  }
  
  public boolean checkSuffix(String suffix) {
    boolean flag = false;
    suffix = (suffix == null) ? "" : suffix.toLowerCase();
    suffix = suffix.trim();
    if (banType != null) {
      List<String> list = Arrays.asList(banType);
      if (list.contains(suffix)) {
        flag = false;
      } else {
        flag = true;
      } 
    } else {
      flag = true;
    } 
    return flag;
  }
  
  public static boolean fileNeedEncrypt(String fileName) {
    if (fileName != null) {
      int index = fileName.lastIndexOf(".");
      if (index > 0) {
        String ext = (String.valueOf(fileName.substring(index)) + ",").toLowerCase();
        if (noEncryptTypes.indexOf(ext) >= 0)
          return false; 
      } 
    } 
    return true;
  }
}
