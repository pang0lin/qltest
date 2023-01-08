package com.js.oa.weixin.common.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.JSFile;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;
import org.apache.pdfbox.util.PDFText2HTML;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DownloadFileForWeixin {
  public static void main(String[] args) throws TransformerException, IOException, ParserConfigurationException, SAXException {
    convert2Html("C:\\2010.xlsx", "");
  }
  
  public String officeToHtml(String path, String file) throws TransformerException, IOException, ParserConfigurationException {
    String content = "";
    if (file.indexOf(".docx") > 0) {
      content = DocToHtml.doWord2007(String.valueOf(path) + file, path);
    } else {
      path = path.replace("1462", "");
      InputStream input = new FileInputStream(String.valueOf(path) + file);
      HWPFDocument wordDocument = new HWPFDocument(input);
      WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
      wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
              return suggestedName;
            }
          });
      wordToHtmlConverter.processDocument((HWPFDocumentCore)wordDocument);
      List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
      if (pics != null)
        for (int i = 0; i < pics.size(); i++) {
          Picture pic = pics.get(i);
          try {
            pic.writeImageContent(new FileOutputStream(String.valueOf(path) + pic.suggestFullFileName()));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } 
        }  
      Document htmlDocument = wordToHtmlConverter.getDocument();
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      DOMSource domSource = new DOMSource(htmlDocument);
      StreamResult streamResult = new StreamResult(outStream);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer serializer = tf.newTransformer();
      serializer.setOutputProperty("encoding", "gb2312");
      serializer.setOutputProperty("indent", "yes");
      serializer.setOutputProperty("method", "html");
      serializer.transform(domSource, streamResult);
      outStream.close();
      content = new String(outStream.toByteArray());
    } 
    return content;
  }
  
  public static String convertWordHtml(String path, String file) throws TransformerException, IOException, ParserConfigurationException {
    String content = "";
    String imgFilePath = String.valueOf(path) + "image/";
    HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(String.valueOf(path) + file));
    WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
        DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument());
    wordToHtmlConverter.setPicturesManager(new PicturesManager() {
          public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
            return suggestedName;
          }
        });
    wordToHtmlConverter.processDocument((HWPFDocumentCore)wordDocument);
    List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
    if (pics != null)
      for (int i = 0; i < pics.size(); i++) {
        Picture pic = pics.get(i);
        System.out.println();
        try {
          pic.writeImageContent(new FileOutputStream(String.valueOf(imgFilePath) + 
                pic.suggestFullFileName()));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } 
      }  
    Document htmlDocument = wordToHtmlConverter.getDocument();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    DOMSource domSource = new DOMSource(htmlDocument);
    StreamResult streamResult = new StreamResult(out);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer serializer = tf.newTransformer();
    serializer.setOutputProperty("encoding", "GBK");
    serializer.setOutputProperty("indent", "yes");
    serializer.setOutputProperty("method", "html");
    serializer.transform(domSource, streamResult);
    out.close();
    content = new String(out.toByteArray());
    content = content.replaceAll(".b1\\{[\\w*-:]*;}", ".b1{text-align:center;white-space-collapsing:preserve;}");
    content = content.replaceAll("<th>[A-Z]</th>", "<th></th>");
    content = content.replaceAll("<th class=\"rownumber\">\\d*</th>", "<th class=\"rownumber\"></th>");
    return content;
  }
  
  public static String convert2Html(String fileName, String outPutFile) throws TransformerException, IOException, ParserConfigurationException, SAXException {
    String content = "";
    if (fileName.indexOf(".xlsx") > 0) {
      Excel2HtmlUtil toHtml = Excel2HtmlUtil.create(fileName, new PrintWriter(
            new FileWriter("C:\\excel.html")));
      toHtml.setCompleteHTML(true);
      toHtml.printPage();
      content = readTextFile("C:\\excel.html", "GBK");
    } else {
      HSSFWorkbook workbook = ExcelToHtmlUtils.loadXls(new File(fileName));
      ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
      excelToHtmlConverter.processWorkbook(workbook);
      Document htmlDocument = excelToHtmlConverter.getDocument();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      DOMSource domSource = new DOMSource(htmlDocument);
      StreamResult streamResult = new StreamResult(out);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer serializer = tf.newTransformer();
      serializer.setOutputProperty("encoding", "GBK");
      serializer.setOutputProperty("indent", "yes");
      serializer.setOutputProperty("method", "html");
      serializer.transform(domSource, streamResult);
      out.close();
      content = new String(out.toByteArray());
      content = content.replaceAll(".b1\\{[\\w*-:]*;}", ".b1{text-align:center;white-space-collapsing:preserve;}");
      content = content.replaceAll("<th>[A-Z]</th>", "<th></th>");
      content = content.replaceAll("<th class=\"rownumber\">\\d*</th>", "<th class=\"rownumber\"></th>");
    } 
    return content;
  }
  
  public static String txtToHtml(String path) {
    StringBuffer sb = new StringBuffer();
    try {
      File file = new File(path);
      if (file.exists() && file.isFile()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = "";
        while ((lineTxt = bufferedReader.readLine()) != null)
          sb.append(String.valueOf(lineTxt.replace(" ", "&nbsp;")) + "<br>"); 
        read.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  public static String pdf2html(String path) {
    String html = "";
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(path);
      PDFParser p = new PDFParser(fis);
      p.parse();
      PDFText2HTML toHTML = new PDFText2HTML("utf-8");
      html = toHTML.getText(p.getPDDocument());
    } catch (Exception e) {
      e.printStackTrace();
      html = e.getMessage();
    } finally {
      try {
        if (fis != null)
          fis.close(); 
        if (html == null || "".equals(html))
          html = "文件解析异常..."; 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return html;
  }
  
  public static int getPDFPagesNum(String path) {
    int totalPage = 0;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(path);
      PDDocument pdf = PDDocument.load(fis);
      totalPage = pdf.getNumberOfPages();
      pdf.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fis != null)
          fis.close(); 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return totalPage;
  }
  
  public static void pdf2pic(String filePath, String fileName) throws Exception {
    FileInputStream fis = new FileInputStream(String.valueOf(filePath) + fileName);
    PDFParser p = new PDFParser(fis);
    p.parse();
    PDDocument doc = p.getPDDocument();
    int count = doc.getNumberOfPages();
    PDFImageWriter w = new PDFImageWriter();
    w.writeImage(doc, "png", null, 1, count, String.valueOf(filePath) + fileName.substring(0, fileName.lastIndexOf(".")));
    fis.close();
  }
  
  public static String pdf2html2(String filePath, String fileName, String curPage) {
    String localPath = filePath.substring(0, filePath.indexOf("\\upload")).replace("\\", "/");
    Runtime rt = Runtime.getRuntime();
    String tempPath = filePath.substring(0, filePath.indexOf(String.valueOf(File.separator) + "upload") + 7);
    if (fileName.indexOf("_") > 0) {
      tempPath = String.valueOf(tempPath) + File.separator + fileName.substring(0, fileName.indexOf("_")) + File.separator + "tempHTML" + File.separator;
    } else {
      tempPath = String.valueOf(tempPath) + File.separator + "tempHTML" + File.separator;
    } 
    String tempPath1 = tempPath.substring(tempPath.indexOf(String.valueOf(File.separator) + "upload")).replaceAll("\\\\", "/");
    File tempFileDir = new File(tempPath);
    if (!tempFileDir.exists() && !tempFileDir.isDirectory()) {
      tempFileDir.mkdir();
      System.out.println("创建" + tempPath1 + "文件夹");
    } 
    String newPath = String.valueOf(tempPath1) + fileName.substring(0, fileName.lastIndexOf(".")) + "_" + curPage + ".html";
    if (SystemCommon.getUseClusterServer() == 1)
      filePath = String.valueOf(SystemCommon.getClusterServerPath()) + filePath.substring(filePath.indexOf("\\upload")); 
    int totalPage = getPDFPagesNum(String.valueOf(filePath) + fileName);
    File tempFile = new File(String.valueOf(tempPath) + fileName.substring(0, fileName.lastIndexOf(".")) + "_" + curPage + ".html");
    if (tempFile == null || !tempFile.exists()) {
      String pdfToolPath = SystemCommon.getPdfToolPath();
      File tool = new File(pdfToolPath);
      if (!pdfToolPath.endsWith("\\pdf2htmlEX.exe") || tool == null || !tool.exists()) {
        System.out.println("请在sysconfig.xml文件中配置正确的pdf转换工具路径...");
        newPath = "";
      } else {
        String command = String.valueOf(pdfToolPath) + " -f " + curPage + " -l " + curPage + " " + filePath + fileName + " ../webapps/jsoa" + newPath;
        try {
          Process p = rt.exec(command);
          StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
          errorGobbler.start();
          StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");
          outGobbler.start();
          int w = p.waitFor();
          int v = p.exitValue();
          if (SystemCommon.getUseClusterServer() == 1) {
            JSFile.copyToFileServer(String.valueOf(localPath) + newPath, SystemCommon.getClusterServerPath());
            JSFile.delete(String.valueOf(localPath) + newPath);
            newPath = String.valueOf(SystemCommon.getClusterServerUrl()) + newPath;
          } else {
            newPath = "/jsoa/" + newPath;
          } 
          return String.valueOf(totalPage) + "|" + newPath;
        } catch (Exception e) {
          newPath = "";
        } 
      } 
    } else {
      newPath = "/jsoa/" + newPath;
    } 
    return String.valueOf(totalPage) + "|" + newPath;
  }
  
  public static String readTextFile(String sFileName, String sEncode) {
    StringBuffer sbStr = new StringBuffer();
    try {
      File ff = new File(sFileName);
      InputStreamReader read = new InputStreamReader(new FileInputStream(ff), 
          sEncode);
      BufferedReader ins = new BufferedReader(read);
      String dataLine = "";
      while ((dataLine = ins.readLine()) != null)
        sbStr.append(dataLine); 
      ins.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return sbStr.toString();
  }
}
