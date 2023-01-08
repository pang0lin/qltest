package com.js.oa.weixin.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.w3c.dom.Document;

public class DocToHtml {
  public static String convertDoc2Html(String filePath, String outPutPath) {
    if (filePath == null || filePath.equals(""))
      return ""; 
    String content = "";
    content = doWord2007(filePath, outPutPath);
    return content;
  }
  
  public static void doWord(String fileName, String fileOutPath) {
    InputStream fileInputStream = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      fileInputStream = new FileInputStream(new File(fileName));
      if (!fileInputStream.markSupported())
        fileInputStream = new PushbackInputStream(fileInputStream, 8); 
      if (POIXMLDocument.hasOOXMLHeader(fileInputStream)) {
        doWord2007(fileName, fileOutPath);
        return;
      } 
      HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));
      WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
      wordToHtmlConverter.processDocument((HWPFDocumentCore)wordDocument);
      Document htmlDocument = wordToHtmlConverter.getDocument();
      DOMSource domSource = new DOMSource(htmlDocument);
      StreamResult streamResult = new StreamResult(out);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer serializer = tf.newTransformer();
      serializer.setOutputProperty("encoding", "UTF-8");
      serializer.setOutputProperty("indent", "yes");
      serializer.setOutputProperty("method", "html");
      serializer.transform(domSource, streamResult);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileInputStream != null)
          fileInputStream.close(); 
        if (out != null)
          out.close(); 
      } catch (Exception e2) {
        e2.printStackTrace();
      } 
    } 
    try {
      if (fileInputStream != null)
        fileInputStream.close(); 
      if (out != null)
        out.close(); 
    } catch (Exception e2) {
      e2.printStackTrace();
    } 
  }
  
  public static String doWord2007(String fileName, String fileOutPath) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    InputStream fileInputStream = null;
    String content = "";
    try {
      fileInputStream = new FileInputStream(new File(fileName));
      if (!fileInputStream.markSupported())
        fileInputStream = new PushbackInputStream(fileInputStream, 8); 
      if (POIFSFileSystem.hasPOIFSHeader(fileInputStream)) {
        fileInputStream.close();
        doWord(fileName, fileOutPath);
        return "";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileInputStream != null)
          fileInputStream.close(); 
        if (out != null)
          out.close(); 
      } catch (Exception e2) {
        e2.printStackTrace();
      } 
    } 
    try {
      if (fileInputStream != null)
        fileInputStream.close(); 
      if (out != null)
        out.close(); 
    } catch (Exception e2) {
      e2.printStackTrace();
    } 
    return content;
  }
  
  public static void writeFile(String content, String path) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    try {
      File file = new File(path);
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos));
      content = content.replace("?", "");
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
  
  public static void main(String[] args) {
    System.out.println("2010docx--------------开始");
    convertDoc2Html("C://zhsihi.docx", "C://word2010.html");
    System.out.println("word2010--------------完毕");
  }
}
