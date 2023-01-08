package com.js.oa.weixin.common.util;

import com.js.util.util.OfficeToHtml;
import com.js.util.util.PdfToHtml;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class OnlineShowByOpenofficeForWeChat {
  public String getHtmlContent(String path, String fileName) {
    String fileNameNoPrefix = fileName.substring(0, fileName.lastIndexOf("."));
    String filePrefix = fileName.substring(fileName.lastIndexOf(".") + 1);
    String htmlFileName = String.valueOf(fileNameNoPrefix) + "_wechattemp";
    String filePath = "";
    if (!path.substring(path.length() - 1).equals("/") && !path.substring(path.length() - 1).equals("\\"))
      path = String.valueOf(path) + File.separator; 
    if ("ppt".equalsIgnoreCase(filePrefix) || "pptx".equalsIgnoreCase(filePrefix) || "doc".equalsIgnoreCase(filePrefix) || "docx".equalsIgnoreCase(filePrefix) || "xls".equalsIgnoreCase(filePrefix) || "xlsx".equalsIgnoreCase(filePrefix)) {
      filePath = OfficeToHtml.getOnlinePathForWeChat(path, fileName, htmlFileName);
    } else if ("pdf".equalsIgnoreCase(filePrefix)) {
      filePath = PdfToHtml.dualPdfToHtmlToOneFile(String.valueOf(path) + htmlFileName + ".pdf", String.valueOf(path) + htmlFileName, String.valueOf(htmlFileName) + ".html");
    } 
    if (filePath.substring(0, 5).equals("fail:"))
      return "未能获取到手机端文件(错误标识：转换服务未配置，请联系系统管理员)"; 
    File file = new File(String.valueOf(path) + htmlFileName + File.separator + htmlFileName + ".html");
    int cnt = 0;
    while (!file.exists() && cnt < 2) {
      if ("ppt".equalsIgnoreCase(filePrefix) || "pptx".equalsIgnoreCase(filePrefix) || "doc".equalsIgnoreCase(filePrefix) || "docx".equalsIgnoreCase(filePrefix) || "xls".equalsIgnoreCase(filePrefix) || "xlsx".equalsIgnoreCase(filePrefix)) {
        filePath = OfficeToHtml.getOnlinePathForWeChat(path, fileName, htmlFileName);
      } else if ("pdf".equalsIgnoreCase(filePrefix)) {
        filePath = PdfToHtml.dualPdfToHtmlToOneFile(String.valueOf(path) + htmlFileName + ".pdf", String.valueOf(path) + htmlFileName, String.valueOf(htmlFileName) + ".html");
      } 
      file = new File(String.valueOf(path) + htmlFileName + File.separator + htmlFileName + ".html");
      cnt++;
    } 
    File pdfFile = new File(String.valueOf(path) + htmlFileName + ".pdf");
    if (pdfFile.exists())
      pdfFile.delete(); 
    if (!file.exists())
      return "文件转换失败请稍候重试"; 
    StringBuffer htmlSb = new StringBuffer();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
            new FileInputStream(file), "UTF-8"));
      while (br.ready())
        htmlSb.append(br.readLine()); 
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    String htmlStr = htmlSb.toString();
    htmlStr = htmlStr.replace("<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\">", "").replace("<meta charset=\"utf-8\"/><meta name=\"generator\" content=\"jiusi\"/><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"/>", "").replace("<title></title>", "").replace("<body>", "").replace("</body>", "").replace("</html>", "");
    return htmlStr;
  }
  
  public String getHtmlContent1(String path, String fileName) {
    String fileNameNoPrefix = fileName.substring(0, fileName.lastIndexOf("."));
    String filePrefix = fileName.substring(fileName.lastIndexOf(".") + 1);
    String htmlFileName = String.valueOf(fileNameNoPrefix) + "_wechattemp";
    String filePath = "";
    if (!path.substring(path.length() - 1).equals("/") && !path.substring(path.length() - 1).equals("\\"))
      path = String.valueOf(path) + File.separator; 
    if ("ppt".equalsIgnoreCase(filePrefix) || "pptx".equalsIgnoreCase(filePrefix) || "doc".equalsIgnoreCase(filePrefix) || "docx".equalsIgnoreCase(filePrefix) || "xls".equalsIgnoreCase(filePrefix) || "xlsx".equalsIgnoreCase(filePrefix)) {
      filePath = OfficeToHtml.getOnlinePathForWeChat(path, fileName, htmlFileName);
    } else if ("pdf".equalsIgnoreCase(filePrefix)) {
      filePath = PdfToHtml.dualPdfToHtmlToOneFile(String.valueOf(path) + fileNameNoPrefix + ".pdf", String.valueOf(path) + htmlFileName, String.valueOf(htmlFileName) + ".html");
    } 
    if (filePath.substring(0, 5).equals("fail:"))
      return "未能获取到手机端文件(错误标识：转换服务未配置，请联系系统管理员)"; 
    File file = new File(String.valueOf(path) + htmlFileName + File.separator + htmlFileName + ".html");
    int cnt = 0;
    while (!file.exists() && cnt < 2) {
      if ("ppt".equalsIgnoreCase(filePrefix) || "pptx".equalsIgnoreCase(filePrefix) || "doc".equalsIgnoreCase(filePrefix) || "docx".equalsIgnoreCase(filePrefix) || "xls".equalsIgnoreCase(filePrefix) || "xlsx".equalsIgnoreCase(filePrefix)) {
        filePath = OfficeToHtml.getOnlinePathForWeChat(path, fileName, htmlFileName);
      } else if ("pdf".equalsIgnoreCase(filePrefix)) {
        filePath = PdfToHtml.dualPdfToHtmlToOneFile(String.valueOf(path) + fileNameNoPrefix + ".pdf", String.valueOf(path) + htmlFileName, String.valueOf(htmlFileName) + ".html");
      } 
      file = new File(String.valueOf(path) + htmlFileName + File.separator + htmlFileName + ".html");
      cnt++;
    } 
    File pdfFile = new File(String.valueOf(path) + htmlFileName + ".pdf");
    if (pdfFile.exists())
      pdfFile.delete(); 
    if (!file.exists())
      return "文件转换失败请稍候重试"; 
    return String.valueOf(path) + htmlFileName + File.separator + htmlFileName + ".html";
  }
}
