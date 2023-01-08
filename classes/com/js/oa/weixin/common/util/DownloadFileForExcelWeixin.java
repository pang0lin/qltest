package com.js.oa.weixin.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DownloadFileForExcelWeixin {
  public static void main(String[] argv) {
    try {
      convert2Html("C://xx.xls", "c://3.html");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void writeFile(String content, String path) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    content = content.replaceAll(".b1\\{[\\w*-:]*;}", ".b1{text-align:center;white-space-collapsing:preserve;}");
    content = content.replaceAll("<th>[A-Z]</th>", "<th></th>");
    content = content.replaceAll("<th class=\"rownumber\">\\d*</th>", "<th class=\"rownumber\"></th>");
    try {
      File file = new File(path);
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos, "GBK"));
      bw.write(content);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (bw != null)
          bw.close(); 
        if (fos != null)
          fos.close(); 
      } catch (IOException iOException) {}
    } 
  }
  
  public static String convert2Html(String fileName, String outPutFile) throws TransformerException, IOException, ParserConfigurationException, SAXException {
    String content = "";
    HSSFWorkbook workbook = ExcelToHtmlUtils.loadXls(new File(fileName));
    ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
    excelToHtmlConverter.processWorkbook(workbook);
    System.out.println(excelToHtmlConverter.getCssClassPrefixDiv());
    System.out.println(excelToHtmlConverter.getCssClassPrefixRow());
    System.out.println(excelToHtmlConverter.getCssClassPrefixTable());
    System.out.println(excelToHtmlConverter.getCssClassPrefixCell());
    System.out.println(excelToHtmlConverter.getDocument());
    Document htmlDocument = excelToHtmlConverter.getDocument();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    DOMSource domSource = new DOMSource(htmlDocument);
    StreamResult streamResult = new StreamResult((OutputStream)out);
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
}
