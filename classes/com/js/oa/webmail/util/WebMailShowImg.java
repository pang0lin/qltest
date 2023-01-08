package com.js.oa.webmail.util;

import com.js.util.mail.Mail;
import java.util.ArrayList;
import java.util.List;

public class WebMailShowImg {
  public Mail ImgDuiying(Mail mail, String filePath) {
    String context = mail.getBoby();
    String[] affix = mail.getFileAffix();
    int aint = (affix == null) ? 0 : affix.length;
    String path = "\"/jsoa/upload/html/";
    List<String> fileList = new ArrayList<String>();
    while (context.contains(path)) {
      int index = context.indexOf(path);
      String contextTemp = context.substring(index + 1);
      int yindex = contextTemp.indexOf("\"");
      fileList.add(String.valueOf(filePath) + "/" + context.substring(index + 19, index + yindex + 1));
      context = String.valueOf(context.substring(0, index + 1)) + "cid:" + context.substring(index + 24);
    } 
    path = "\"/jsoa/upload/";
    while (context.contains(path)) {
      int index = context.indexOf(path);
      String contextTemp = context.substring(index + 1);
      int yindex = contextTemp.indexOf("\"");
      fileList.add(String.valueOf(filePath.replace("\\html", "")) + "/" + context.substring(index + 14, index + yindex + 1));
      context = String.valueOf(context.substring(0, index + 1)) + "cid:" + context.substring(index + 27);
    } 
    String[] newAffix = new String[aint + fileList.size()];
    int i;
    for (i = 0; i < aint; i++)
      newAffix[i] = affix[i]; 
    for (i = aint; i < aint + fileList.size(); i++)
      newAffix[i] = fileList.get(i - aint); 
    mail.setBoby(context);
    mail.setFileAffix(newAffix);
    return mail;
  }
}
