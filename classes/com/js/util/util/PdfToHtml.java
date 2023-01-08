package com.js.util.util;

import java.io.File;

public class PdfToHtml {
  private static String PDF2HTML_HOME = OfficeToHtml.getInstallPath("pdf2html");
  
  public static String getOnlineShowPath(String path, String fileName) {
    if (PDF2HTML_HOME == null || "".equals(PDF2HTML_HOME))
      return "fail:未配置文件转换服务，请联系系统管理员"; 
    String showPath = checkAndMakeDir(path);
    String fileNameNoPrefix = fileName.substring(0, fileName.lastIndexOf("."));
    String convertPath = String.valueOf(showPath) + fileNameNoPrefix + File.separator + fileNameNoPrefix + ".jsp";
    if (!checkFileExists(convertPath)) {
      String relativePath = String.valueOf(showPath) + fileNameNoPrefix;
      String outFileName = String.valueOf(fileNameNoPrefix) + ".jsp";
      pdf2html(String.valueOf(path) + fileName, relativePath, outFileName);
    } 
    if (checkFileExists(convertPath)) {
      String htmlpath = convertPath.substring(convertPath.indexOf("webapps") + 7);
      htmlpath = htmlpath.replaceAll("\\\\", "/");
      System.out.println(htmlpath);
      return htmlpath;
    } 
    return "fail:文件转换失败，请稍候重新打开";
  }
  
  protected static void dualPdfToHtml(String path, String fileName, String outFileName) {
    pdf2html(String.valueOf(path) + File.separator + fileName, path, outFileName);
  }
  
  public static String dualPdfToHtmlToOneFile(String pdfPath, String htmlPath, String outFileName) {
    dualPdfToHtmlForDoc(pdfPath, htmlPath, outFileName);
    return String.valueOf(htmlPath) + File.separator + outFileName;
  }
  
  protected static boolean dualPdfToHtmlForDoc(String pdfPath, String htmlPath, String outFileName) {
    Runtime rt = Runtime.getRuntime();
    try {
      htmlPath = htmlPath.replaceAll("/", "\\\\");
      Process p = rt.exec(String.valueOf(PDF2HTML_HOME) + " --embed-css 1 --embed-font 0 --embed-image 0 --embed-javascript 0 --embed-outline 0 --process-outline 0 --optimize-text 1 --dest-dir " + htmlPath + " " + pdfPath + " " + outFileName);
      PdfToHtmlUtil errorGobbler = new PdfToHtmlUtil(p.getErrorStream(), "ERROR");
      errorGobbler.start();
      PdfToHtmlUtil outGobbler = new PdfToHtmlUtil(p.getInputStream(), "STDOUT");
      outGobbler.start();
      int w = p.waitFor();
      int v = p.exitValue();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  private static boolean checkFileExists(String path) {
    File file = new File(path);
    if (file.exists())
      return true; 
    return false;
  }
  
  private static boolean pdf2html(String pdfPath, String htmlPath, String outFileName) {
    Runtime rt = Runtime.getRuntime();
    try {
      htmlPath = htmlPath.replaceAll("/", "\\\\");
      Process p = rt.exec(String.valueOf(PDF2HTML_HOME) + " --embed-css 0 --embed-font 0 --embed-image 0 --embed-javascript 0 --embed-outline 0 --process-outline 0 --optimize-text 1 --dest-dir " + htmlPath + " " + pdfPath + " " + outFileName);
      PdfToHtmlUtil errorGobbler = new PdfToHtmlUtil(p.getErrorStream(), "ERROR");
      errorGobbler.start();
      PdfToHtmlUtil outGobbler = new PdfToHtmlUtil(p.getInputStream(), "STDOUT");
      outGobbler.start();
      int w = p.waitFor();
      int v = p.exitValue();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  private static String checkAndMakeDir(String path) {
    String showPath = path.replace("/upload/", "/upAttachOnLineShow/").replace("\\upload\\", "\\upAttachOnLineShow\\");
    File dirFile = new File(showPath);
    if (dirFile.exists())
      return showPath; 
    dirFile.mkdirs();
    return showPath;
  }
  
  public static void main(String[] args) {}
}
