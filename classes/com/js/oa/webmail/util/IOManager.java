package com.js.oa.webmail.util;

import com.js.oa.webmail.po.Affix;
import com.js.oa.webmail.po.Attach;
import com.js.util.util.UploadFile;
import com.jsupload.upload.SmartUpload;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.mail.Part;
import javax.servlet.http.HttpServletRequest;

public class IOManager {
  public static String getFileContent(String filePath) {
    StringBuffer con = new StringBuffer();
    FileReader fr = null;
    File file = null;
    BufferedReader br = null;
    try {
      file = new File(filePath);
      if (!file.exists())
        file.createNewFile(); 
      fr = new FileReader(file);
      br = new BufferedReader(fr);
      String line = br.readLine();
      while (line != null) {
        con.append(line);
        line = br.readLine();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      file = null;
      try {
        if (br != null)
          br.close(); 
        if (fr != null)
          fr.close(); 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return con.toString();
  }
  
  public static String saveFile(InputStream is, String savePath, String fileName) throws Exception {
    if (fileName == null)
      fileName = "noname" + (new Date()).getTime(); 
    String newFileName = savePath;
    Calendar cal = Calendar.getInstance();
    String yearSub = String.valueOf(cal.get(1));
    newFileName = String.valueOf(newFileName) + yearSub + "_" + UniqueCode.generate();
    int pos = fileName.lastIndexOf(".");
    String suffix = fileName;
    if (pos >= 0)
      suffix = fileName.substring(pos); 
    if (!(new SmartUpload()).checkSuffix(suffix))
      suffix = String.valueOf(suffix) + ".temp"; 
    if (pos > 0)
      newFileName = String.valueOf(newFileName) + suffix; 
    File f = null;
    OutputStream bos = null;
    InputStream bis = null;
    try {
      f = new File(newFileName);
      bos = new BufferedOutputStream(new FileOutputStream(f));
      bis = new BufferedInputStream(is);
      byte[] b = new byte[64];
      int len;
      while ((len = bis.read(b, 0, b.length)) != -1) {
        bos.write(b, 0, len);
        bos.flush();
      } 
      UploadFile uf = new UploadFile();
      uf.setFileSize(newFileName.substring(newFileName.lastIndexOf("/") + 1, newFileName.length()), getFileSize(newFileName));
    } catch (Exception len) {
      Exception exception;
    } finally {
      f = null;
      if (bos != null)
        bos.close(); 
      if (bis != null)
        bis.close(); 
    } 
    return newFileName;
  }
  
  public static String copyFile(String oldPath, String newPath) {
    InputStream is = null;
    FileOutputStream fos = null;
    File oldFile = null;
    String bf = "hujt.jiusi";
    int byteread = 0;
    try {
      oldFile = new File(oldPath);
      if (oldFile.exists()) {
        is = new FileInputStream(oldPath);
        fos = new FileOutputStream(newPath);
        byte[] buffer = new byte[1444];
        while ((byteread = is.read(buffer)) != -1)
          fos.write(buffer, 0, byteread); 
        bf = newPath;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      oldFile = null;
      try {
        if (fos != null)
          fos.close(); 
        if (is != null)
          is.close(); 
      } catch (Exception exception) {}
    } 
    return bf.toString();
  }
  
  public static void delFile(String[] oldFile) {
    File myDelFile = null;
    try {
      if (oldFile != null && oldFile.length > 0)
        for (int i = 0; i < oldFile.length; i++) {
          String filePath = oldFile[i];
          filePath = filePath.toString();
          myDelFile = new File(filePath);
          if (myDelFile.exists())
            myDelFile.delete(); 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      myDelFile = null;
    } 
  }
  
  public static void delFile(List<String> oldFile) {
    File myDelFile = null;
    try {
      if (oldFile != null && oldFile.size() > 0)
        for (int i = 0; i < oldFile.size(); i++) {
          String filePath = oldFile.get(i);
          myDelFile = new File(filePath);
          if (myDelFile.exists())
            myDelFile.delete(); 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      myDelFile = null;
    } 
  }
  
  public static String[] transAffixName(String[] affixPath, String[] afficPath, HttpServletRequest request) {
    String[] affixNames = (String[])null;
    String[] saveNames = (String[])null;
    String[] affixAll = (String[])null;
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String tempPath = String.valueOf(request.getRealPath("/upload/")) + "/" + year + "/webmailtemp/";
    String temp = String.valueOf(request.getRealPath("/upload/")) + "/" + year + "/webmail/";
    try {
      if (affixPath != null && affixPath.length > 0) {
        affixNames = new String[affixPath.length];
        for (int i = 0; i < affixPath.length; i++) {
          String tempAffixName = AffixManager.getInstance().getAffixName(affixPath[i]);
          if (tempAffixName != null) {
            String files = copyFile(affixPath[i], String.valueOf(tempPath) + tempAffixName);
            affixNames[i] = files;
          } else {
            String a = affixPath[i];
            String tempAttachName = AffixManager.getInstance().getAttachName(a.substring(a.lastIndexOf("/") + 1, a.length()));
            String files = copyFile(affixPath[i], String.valueOf(tempPath) + tempAttachName);
            affixNames[i] = files;
          } 
        } 
      } 
      if (afficPath != null && afficPath.length > 0) {
        saveNames = new String[afficPath.length];
        for (int i = 0; i < afficPath.length; i++) {
          String files = copyFile(String.valueOf(temp) + afficPath[i], String.valueOf(tempPath) + AffixManager.getInstance().getAttachName(afficPath[i]));
          saveNames[i] = files;
        } 
      } 
      if (affixNames != null && affixNames.length > 0 && saveNames == null) {
        affixAll = affixNames;
      } else if (saveNames != null && saveNames.length > 0 && affixNames == null) {
        affixAll = saveNames;
      } else if (affixNames != null && affixNames.length > 0 && saveNames != null && saveNames.length > 0) {
        int k = 0;
        int a = 0, b = 0;
        if (affixNames == null) {
          a = 0;
        } else {
          a = affixNames.length;
        } 
        if (saveNames == null) {
          b = 0;
        } else {
          b = saveNames.length;
        } 
        affixAll = new String[a + b];
        if (affixNames != null && affixNames.length > 0)
          for (int j = 0; j < affixNames.length; j++) {
            affixAll[k] = affixNames[j];
            k++;
          }  
        if (saveNames != null && saveNames.length > 0)
          for (int j = 0; j < saveNames.length; j++) {
            affixAll[k] = saveNames[j];
            k++;
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return affixAll;
  }
  
  public static String saveFileAsEmail(Part p, String savePath, String fileName) throws Exception {
    String newFileName = savePath;
    newFileName = String.valueOf(newFileName) + UniqueCode.generate();
    int pos = fileName.lastIndexOf(".");
    if (pos > 0)
      newFileName = String.valueOf(newFileName) + fileName.substring(pos); 
    if (newFileName.length() < 4 || !".eml".equals(newFileName.toLowerCase().substring(
          newFileName.length() - 4)))
      newFileName = String.valueOf(newFileName) + ".eml"; 
    File f = new File(newFileName);
    OutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
    p.writeTo(bos);
    bos.flush();
    bos.close();
    return newFileName;
  }
  
  public static boolean isFile(String path) {
    boolean flag = true;
    try {
      File file = new File(path);
      if (!file.exists())
        return false; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public static int getFileSize(String path) {
    int byteSize = 0;
    FileInputStream fis = null;
    try {
      File file = new File(path);
      if (file.exists()) {
        fis = new FileInputStream(file);
        byteSize = fis.available();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return byteSize;
  }
  
  public static boolean delDirectory(String path) {
    File file = new File(path);
    if (file.exists()) {
      if (file.isDirectory()) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
          if (!delDirectory(files[i].getPath()))
            return false; 
        } 
        return file.delete();
      } 
      return file.delete();
    } 
    return true;
  }
  
  public static boolean setFileExtName(String path, String extName) {
    File f = new File(path);
    if (f.exists() && f.isDirectory()) {
      File tf = null;
      File[] files = f.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i].exists() && files[i].isFile()) {
          tf = new File(String.valueOf(files[i].getPath()) + "." + extName);
          files[i].renameTo(tf);
        } 
      } 
    } 
    return true;
  }
  
  public static void zip(String zipFileName, String inputFile) throws Exception {
    zip(zipFileName, new File(inputFile));
  }
  
  public static void zip(String zipFileName, File inputFile) throws Exception {
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
    zip(out, inputFile, "");
    out.close();
  }
  
  private static void zip(ZipOutputStream out, File f, String base) throws Exception {
    if (f.isDirectory()) {
      File[] fl = f.listFiles();
      out.putNextEntry(new ZipEntry(String.valueOf(base) + "/"));
      base = String.valueOf(base) + "/";
      for (int i = 0; i < fl.length; i++)
        zip(out, fl[i], String.valueOf(base) + fl[i].getName()); 
    } else {
      out.putNextEntry(new ZipEntry(f.getName()));
      FileInputStream in = new FileInputStream(f);
      int b;
      while ((b = in.read()) != -1)
        out.write(b); 
      in.close();
    } 
  }
  
  public static boolean checkDir(String path) {
    File f = new File(path);
    if (!f.exists())
      return f.mkdirs(); 
    return true;
  }
  
  public static String zipMail(String tempPath, String temp, String fname) throws Exception {
    String path = String.valueOf(tempPath) + ".zip/";
    String mailPath = String.valueOf(path) + temp + "_cur/";
    checkDir(mailPath);
    setFileExtName(mailPath, "eml");
    String zipFile = String.valueOf(tempPath) + fname + "_mail.zip";
    return zipFile;
  }
  
  public static List getAffixString(List<Affix> affList, List<Attach> lista) {
    List<Affix> affixString = new ArrayList();
    if (affList != null && affList.size() > 0) {
      Affix affixTemp = null;
      for (int i = 0; i < affList.size(); i++) {
        affixTemp = new Affix();
        Affix affix = affList.get(i);
        String names = affix.getAffixPath().substring(affix.getAffixPath().lastIndexOf("/") + 1, affix.getAffixPath().length());
        affixTemp.setSaveName(names);
        affixTemp.setAffixName(affix.getAffixName());
        affixString.add(affixTemp);
      } 
    } 
    if (lista != null && lista.size() > 0) {
      Affix affixTemp_ = null;
      for (int i = 0; i < lista.size(); i++) {
        affixTemp_ = new Affix();
        Attach affix = lista.get(i);
        affixTemp_.setSaveName(affix.getAttachDisName());
        affixTemp_.setAffixName(affix.getAttachName());
        affixString.add(affixTemp_);
      } 
    } 
    return affixString;
  }
  
  public static List getAttachString(List<Attach> affList) {
    List<Attach> attachString = null;
    String accSizeStr = "0";
    int size = 0;
    if (affList != null && affList.size() > 0) {
      attachString = new ArrayList();
      Attach affixTemp = null;
      UploadFile upFile = new UploadFile();
      for (int i = 0; i < affList.size(); i++) {
        Attach attach = affList.get(i);
        size = upFile.getFileSizeStr(attach.getAttachDisName());
        affixTemp = new Attach();
        affixTemp.setAttachName(attach.getAttachName());
        affixTemp.setAttachDisName(attach.getAttachDisName());
        affixTemp.setAttachSizeStr(getAffixSizeStr(size, accSizeStr));
        attachString.add(affixTemp);
      } 
    } 
    return attachString;
  }
  
  public static String getAffixSizeStr(int size, String accSizeStr) {
    String str = "";
    if (size < 1048576) {
      accSizeStr = String.valueOf(size / 1024.0D);
      if (accSizeStr.length() > accSizeStr.indexOf(".") + 3)
        accSizeStr = accSizeStr.substring(0, accSizeStr.indexOf(".") + 3); 
      accSizeStr = String.valueOf(accSizeStr) + "K";
    } else {
      accSizeStr = String.valueOf(size / 1048576.0D);
      if (accSizeStr.length() > accSizeStr.indexOf(".") + 3)
        accSizeStr = accSizeStr.substring(0, accSizeStr.indexOf(".") + 3); 
      accSizeStr = String.valueOf(accSizeStr) + "M";
    } 
    str = accSizeStr;
    return str;
  }
  
  public static void main(String[] args) throws Exception {
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String a = "D:/tools/tomcat-6.0.18/webapps/jsoa/upload/" + year + "/webmail/~attachment/atttemp/.zip/";
    String b = "121;222;333;444;";
    System.out.println(b.substring(0, b.lastIndexOf(";")));
  }
}
