package com.js.util.util;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class OfficeToHtml {
  private static String OpenOffice_HOME = getInstallPath("openoffice");
  
  private static Process pro = null;
  
  public static String getOnlinePathForWeChat(String path, String fileName, String htmlFileName) {
    String hpath = String.valueOf(path) + htmlFileName + File.separator + htmlFileName + ".html";
    if (checkFileExists(hpath))
      return hpath; 
    OpenOffice_HOME = getInstallPath("openoffice");
    if (OpenOffice_HOME == null || "".equals(OpenOffice_HOME))
      return "fail:未配置文件转换服务，请联系系统管理员"; 
    String filePrefix = fileName.substring(fileName.lastIndexOf(".") + 1);
    String convertPath = String.valueOf(path) + htmlFileName + ".html";
    if ("ppt".equalsIgnoreCase(filePrefix) || "pptx".equalsIgnoreCase(filePrefix) || "doc".equalsIgnoreCase(filePrefix) || "docx".equalsIgnoreCase(filePrefix) || "xls".equalsIgnoreCase(filePrefix) || "xlsx".equalsIgnoreCase(filePrefix)) {
      String convertPdfPath = String.valueOf(path) + htmlFileName + ".pdf";
      convert(new File(String.valueOf(path) + fileName), convertPdfPath);
      if (checkFileExists(convertPdfPath))
        PdfToHtml.dualPdfToHtmlForDoc(String.valueOf(path) + htmlFileName + ".pdf", String.valueOf(path) + htmlFileName, String.valueOf(htmlFileName) + ".html"); 
    } else {
      convert(new File(String.valueOf(path) + fileName), convertPath);
    } 
    return hpath;
  }
  
  public static String getOnlineShowPath(String path, String fileName) {
    OpenOffice_HOME = getInstallPath("openoffice");
    if (OpenOffice_HOME == null || "".equals(OpenOffice_HOME))
      return "fail:未配置文件转换服务，请联系系统管理员"; 
    String showPath = checkAndMakeDir(path);
    String fileNameNoPrefix = fileName.substring(0, fileName.lastIndexOf("."));
    String filePrefix = fileName.substring(fileName.lastIndexOf(".") + 1);
    String convertPath = String.valueOf(showPath) + fileNameNoPrefix + File.separator + fileNameNoPrefix + ".jsp";
    if (!checkFileExists(convertPath))
      if ("ppt".equalsIgnoreCase(filePrefix) || "pptx".equalsIgnoreCase(filePrefix) || "doc".equalsIgnoreCase(filePrefix) || "docx".equalsIgnoreCase(filePrefix)) {
        String convertPdfPath = String.valueOf(showPath) + fileNameNoPrefix + File.separator + fileNameNoPrefix + ".pdf";
        convert(new File(String.valueOf(path) + fileName), convertPdfPath);
        if (checkFileExists(convertPdfPath))
          PdfToHtml.dualPdfToHtml(String.valueOf(showPath) + fileNameNoPrefix, String.valueOf(fileNameNoPrefix) + ".pdf", String.valueOf(fileNameNoPrefix) + ".jsp"); 
        if (checkFileExists(convertPath)) {
          File pdfFile = new File(convertPdfPath);
          pdfFile.delete();
        } 
      } else {
        String htmlPath = String.valueOf(convertPath.substring(0, convertPath.lastIndexOf("."))) + ".html";
        convert(new File(String.valueOf(path) + fileName), htmlPath);
        if (checkFileExists(htmlPath)) {
          File f = new File(htmlPath);
          f.renameTo(new File(convertPath));
        } 
      }  
    if (checkFileExists(convertPath)) {
      String htmlpath = convertPath.substring(convertPath.indexOf("webapps") + 7);
      htmlpath = htmlpath.replaceAll("\\\\", "/");
      System.out.println(htmlpath);
      return htmlpath;
    } 
    return "fail:文件转换失败，请稍候重新打开";
  }
  
  private static String checkAndMakeDir(String path) {
    String showPath = path.replace("/upload/", "/upAttachOnLineShow/").replace("\\upload\\", "\\upAttachOnLineShow\\");
    File dirFile = new File(showPath);
    if (dirFile.exists())
      return showPath; 
    dirFile.mkdirs();
    return showPath;
  }
  
  private static boolean checkFileExists(String path) {
    File file = new File(path);
    if (file.exists())
      return true; 
    return false;
  }
  
  private static File convert(File docFile, String htmlPath) {
    File htmlFile = new File(htmlPath);
    OpenOfficeConnection con = getConnection();
    OpenOfficeDocumentConverter openOfficeDocumentConverter = new OpenOfficeDocumentConverter(con);
    openOfficeDocumentConverter.convert(docFile, htmlFile);
    closeConnection(con);
    return htmlFile;
  }
  
  private static OpenOfficeConnection getConnection() {
    SocketOpenOfficeConnection socketOpenOfficeConnection = null;
    OpenOfficeConnection connection = null;
    try {
      startService();
      if (pro != null) {
        socketOpenOfficeConnection = new SocketOpenOfficeConnection(8100);
        socketOpenOfficeConnection.connect();
      } 
    } catch (ConnectException e) {
      e.printStackTrace();
    } 
    return (OpenOfficeConnection)socketOpenOfficeConnection;
  }
  
  private static void closeConnection(OpenOfficeConnection connection) {
    if (connection != null) {
      connection.disconnect();
      stopService();
    } 
  }
  
  private static void startService() {
    if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '/')
      OpenOffice_HOME = String.valueOf(OpenOffice_HOME) + "/"; 
    try {
      String command = String.valueOf(OpenOffice_HOME) + 
        "soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
      pro = Runtime.getRuntime().exec(command);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("启动openoffice服务失败");
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private static void stopService() {
    if (pro != null) {
      pro.destroy();
      System.out.println("关闭openoffice服务成功");
    } 
  }
  
  protected static String getInstallPath(String type) {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String result = null;
    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element root = doc.getRootElement();
      Element node = root.getChild("onLineShowConfig");
      if ("openoffice".equals(type)) {
        result = node.getChild("openofficeInstall").getAttribute("path").getValue();
      } else if ("pdf2html".equals(type)) {
        result = node.getChild("pdf2HtmlInstall").getAttribute("path").getValue();
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
}
