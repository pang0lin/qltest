package com.js.oa.jsflow.action;

import com.js.oa.jsflow.util.pdf.HtmlToPdf;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WorkFlowPdfServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("GBK");
    String path = String.valueOf(getServletContext().getRealPath("/")) + 
      "upload\\pdf\\";
    String serverPort = (new StringBuilder(String.valueOf(request.getServerPort()))).toString();
    String html = request.getParameter("pageContent");
    html = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></meta>" + 
      html + "</body></html>";
    html = html.replace("/jsoa/upload", "http://" + SystemCommon.getServerIp() + ":" + SystemCommon.getServerPort() + 
        "/jsoa/upload");
    html = html.replace("borderColorLight=", "\"\"");
    html = html.replace("bgColor= background=\"\"", "bgColor=\"\" background=\"\"");
    String workId = request.getParameter("workId");
    DbOpt dbopt = null;
    String sql = "select aaa.WORKSUBMITPERSON,aaa.WORKFILETYPE,aaa.WORKSUBMITTIME from JSF_WORK aaa where aaa.wf_work_id=?";
    try {
      dbopt = new DbOpt();
      String[][] workData = dbopt.executeQueryToStrArr2PS(sql, 3, new String[] { workId });
      dbopt.close();
      String fileName = String.valueOf(workData[0][1]) + 
        "_" + 
        workData[0][0] + 
        "_" + 
        workData[0][2].replace(" ", "-").replace(":", "-")
        .replace(".", "-");
      File x = new File(path);
      if (!x.exists())
        x.mkdirs(); 
      String htmlpath = String.valueOf(path) + fileName + ".html";
      String pdfPath = String.valueOf(path) + fileName + ".pdf";
      File f = new File(htmlpath);
      OutputStreamWriter write = new OutputStreamWriter(
          new FileOutputStream(f), "utf-8");
      BufferedWriter writer = new BufferedWriter(write);
      writer.write(html);
      writer.flush();
      writer.close();
      HtmlToPdf.convert(htmlpath, pdfPath);
      downloadFile(pdfPath, fileName, response);
    } catch (Exception e) {
      e.printStackTrace();
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
  }
  
  public static void downloadFile(String pdfPath, String fileName, HttpServletResponse response) throws IOException {
    response.setHeader("Content-Disposition", "attachment;filename=" + 
        new String((String.valueOf(fileName) + ".pdf").getBytes("GBK"), "ISO8859-1"));
    FileInputStream in = new FileInputStream(pdfPath);
    ServletOutputStream out = response.getOutputStream();
    byte[] buffer = new byte[pdfPath.length()];
    int i;
    while ((i = in.read(buffer)) != -1)
      out.write(buffer, 0, i); 
    in.close();
    out.close();
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
