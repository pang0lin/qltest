package com.js.util.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DownloadAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
    int isExist = 0;
    try {
      String filedownload, name;
      Object object = request.getQueryString();
      if (request.getAttribute("queryString") != null)
        object = request.getAttribute("queryString"); 
      if ("1".equals(request.getAttribute("fromWorkflowReport"))) {
        filedownload = request.getAttribute("fileSaveName").toString();
        name = request.getAttribute("fileName").toString();
      } else {
        String[] queryString = BASE64.BASE64DecoderNoBR((String)object).replace("&", "\\|").split("\\|");
        filedownload = queryString[0];
        name = queryString[queryString.length - 1];
      } 
      File downloadFile = new File(filedownload);
      if (downloadFile.exists()) {
        isExist = 1;
        response.setContentType("application/x-download");
        String filenamedisplay = URLEncoder.encode(name, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
        try {
          FileInputStream fis = new FileInputStream(downloadFile);
          BufferedInputStream buff = new BufferedInputStream(fis);
          byte[] b = new byte[1024];
          long k = 0L;
          ServletOutputStream servletOutputStream = response.getOutputStream();
          while (k < downloadFile.length()) {
            int j = buff.read(b, 0, 1024);
            k += j;
            servletOutputStream.write(b, 0, j);
          } 
          servletOutputStream.flush();
          buff.close();
          fis.close();
          servletOutputStream.close();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    if (isExist != 1) {
      response.setCharacterEncoding("GBK");
      String message = "<html><head><title></title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\"><script type=\"text/javascript\">alert(\"文件不存在!\");</script></head><body></body></html>";
      response.getWriter().print(message);
    } 
    return null;
  }
}
