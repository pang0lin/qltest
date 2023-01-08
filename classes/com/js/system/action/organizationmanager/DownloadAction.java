package com.js.system.action.organizationmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DownloadAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String type = "";
    String filename = "";
    try {
      if (filename.indexOf(".xls") > 0 || filename.indexOf(".xlsx") > 0) {
        type = "application/vnd.ms-excel";
      } else if (filename.indexOf(".pdf") > 0) {
        type = "application/pdf";
      } else if (filename.indexOf(".doc") > 0 || filename.indexOf(".docx") > 0) {
        type = "application/msword";
      } else if (filename.indexOf(".txt") > 0) {
        type = "text/plain";
      } else if (filename.indexOf(".ppt") > 0) {
        type = "application/ppt";
      } 
      File localFile = new File("");
      OutputStream is = new FileOutputStream(localFile);
      FTPClient ftp = new FTPClient();
      response.setContentType(type);
      response.setHeader("Content-disposition", "inline;filename=" + URLEncoder.encode(filename, "utf-8"));
      ftp.retrieveFile(new String(filename.getBytes("GBK"), "ISO-8859-1"), is);
      is.flush();
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
}
