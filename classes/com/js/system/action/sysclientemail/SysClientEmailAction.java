package com.js.system.action.sysclientemail;

import com.js.system.service.sysclientemail.SysClientEmailBD;
import com.js.system.vo.sysclientemail.SysClientEmailVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SysClientEmailAction extends DispatchAction {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    SysClientEmailBD sysClientEmailBD = new SysClientEmailBD();
    BufferedReader reader = null;
    String path = System.getProperty("user.dir");
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String configFile = String.valueOf(path) + "webapps/jsoa/upload/" + year + "/sysclientemailnew";
    String configFile1 = String.valueOf(path) + "webapps/jsoa/upload/" + year + "/sysclientemailold";
    List<String> list = sysClientEmailBD.searchEmailUrl();
    File f = new File(configFile);
    File[] l = f.listFiles();
    for (int i = 0; i < l.length; i++) {
      File a = l[i];
      try {
        reader = new BufferedReader(new FileReader(a));
        String tempString = null;
        while ((tempString = reader.readLine()) != null) {
          if (!list.contains(tempString)) {
            SysClientEmailVO sysClientEmailVO = new SysClientEmailVO();
            sysClientEmailVO.setEmailUrl(tempString);
            sysClientEmailVO.setSendTime("");
            sysClientEmailBD.add(sysClientEmailVO);
            list.add(tempString);
          } 
        } 
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (reader != null)
          try {
            reader.close();
          } catch (IOException iOException) {} 
      } 
      copyFile(String.valueOf(configFile) + "/" + a.getName(), String.valueOf(configFile1) + "/" + a.getName());
      delFile(String.valueOf(configFile) + "/" + a.getName());
    } 
    String message = "ok";
    httpServletRequest.setAttribute("message", message);
    return actionMapping.findForward("ok");
  }
  
  public static void copyFile(String oldPath, String newPath) {
    try {
      int bytesum = 0;
      int byteread = 0;
      File oldfile = new File(oldPath);
      if (oldfile.exists()) {
        InputStream inStream = new FileInputStream(oldPath);
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1444];
        while ((byteread = inStream.read(buffer)) != -1) {
          bytesum += byteread;
          System.out.println(bytesum);
          fs.write(buffer, 0, byteread);
        } 
        inStream.close();
      } 
    } catch (Exception e) {
      System.out.println("复制单个文件操作出错");
      e.printStackTrace();
    } 
  }
  
  public static void delFile(String filePathAndName) {
    try {
      String filePath = filePathAndName;
      filePath = filePath.toString();
      File myDelFile = new File(filePath);
      myDelFile.delete();
    } catch (Exception e) {
      System.out.println("删除文件操作出错");
      e.printStackTrace();
    } 
  }
}
