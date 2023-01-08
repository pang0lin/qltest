package com.js.oa.form;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.info.infomanager.bean.InfoProFileEJBBean;
import com.js.oa.info.infomanager.po.InfoProFilePO;
import com.js.oa.jsflow.util.DataToOtherBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class InfoProFileReviseWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    try {
      String processeId = request.getParameter("processId");
      String recorded = request.getParameter("recordId");
      String tableId = request.getParameter("tableId");
      String work = request.getParameter("work");
      DataToOtherBase dob = new DataToOtherBase(processeId, "-1", recorded);
      dob.dataSynchro();
      InfoProFileEJBBean infoProFileEJBBean = new InfoProFileEJBBean();
      InfoProFilePO infoProFileReviseRecord = infoProFileEJBBean.getInfoProFileReviseById(recorded);
      Long fileId = infoProFileReviseRecord.getFileId();
      InfoProFilePO oldInfoProFile = infoProFileEJBBean.getInfoProFilePOById(fileId);
      oldInfoProFile.setIsNew("0");
      oldInfoProFile.setReviseRecordId(String.valueOf(processeId) + ";" + tableId + ";" + recorded);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String date = sdf.format(new Date());
      Calendar rightNow = Calendar.getInstance();
      rightNow.setTime(new Date());
      String year = String.valueOf(rightNow.get(1));
      rightNow.add(1, 1);
      InfoProFilePO newInfoProFile = new InfoProFilePO();
      newInfoProFile.setFileName(infoProFileReviseRecord.getFileName());
      newInfoProFile.setFileNum(infoProFileReviseRecord.getFileNum());
      newInfoProFile.setFilePreId(oldInfoProFile.getFilePreId());
      newInfoProFile.setCharacter(infoProFileReviseRecord.getCharacter());
      newInfoProFile.setAuthor(infoProFileReviseRecord.getAuthor());
      newInfoProFile.setDepartment(infoProFileReviseRecord.getDepartment());
      int version = Integer.parseInt(oldInfoProFile.getVersion());
      newInfoProFile.setVersion((new StringBuilder(String.valueOf(version + 1))).toString());
      newInfoProFile.setIsNew("1");
      newInfoProFile.setFileDate(sdf.parse(date));
      newInfoProFile.setReviewDate(rightNow.getTime());
      newInfoProFile.setViewMan(oldInfoProFile.getViewMan());
      String filePath = infoProFileReviseRecord.getFilePath().split(";")[0];
      filePath = filePath.substring(0, filePath.length() - 1);
      newInfoProFile.setFilePath(filePath);
      String fileViewName = infoProFileReviseRecord.getFilePath().split(";")[1];
      fileViewName = fileViewName.substring(0, fileViewName.length() - 1);
      newInfoProFile.setFileViewName(fileViewName);
      newInfoProFile.setReviseRecordId(String.valueOf(processeId) + ";" + tableId + ";" + recorded);
      infoProFileEJBBean.save(newInfoProFile);
      String from = String.valueOf(System.getProperty("user.dir").replace("bin", "webapps")) + "\\jsoa\\upload//" + year + "//customform//" + filePath;
      String to = String.valueOf(System.getProperty("user.dir").replace("bin", "webapps")) + "\\jsoa\\upload//" + year + "//proFile//" + filePath;
      File source = new File(from);
      File target = new File(to);
      customBufferStreamCopy(source, target);
      infoProFileEJBBean.updateInfoProFilePO(oldInfoProFile);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return "success";
  }
  
  private void customBufferStreamCopy(File source, File target) {
    InputStream fis = null;
    OutputStream fos = null;
    try {
      fis = new FileInputStream(source);
      fos = new FileOutputStream(target);
      byte[] buf = new byte[4096];
      int i;
      while ((i = fis.read(buf)) != -1)
        fos.write(buf, 0, i); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        fis.close();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
}
