package com.js.oa.message.action;

import com.js.oa.message.service.MessageBD;
import com.js.oa.message.service.messageSettingBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.SysSetupReader;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ModelSendMsg {
  public boolean sendSystemMessage(String model, String title, String ids, String telephone, HttpServletRequest httpServletRequest) {
    return sendSystemMessage(model, title, ids, telephone, httpServletRequest, "0");
  }
  
  public boolean sendSystemMessage(String model, String title, String ids, String telephone, HttpServletRequest httpServletRequest, String isNotModel) {
    HttpSession session = httpServletRequest.getSession(true);
    if (!(new ManagerService()).hasRight(session.getAttribute("userId").toString(), "09*01*01"))
      return false; 
    String sendMen = session.getAttribute("userName").toString();
    String department = session.getAttribute("orgName").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    messageSettingBD messageSetting = new messageSettingBD();
    if (messageSetting.judgePurviewMessage(model, domainId)) {
      String contents = "";
      if (isNotModel.equals("1")) {
        contents = title;
      } else {
        contents = messageSetting.getModleContents(model, title, sendMen, department, domainId);
      } 
      if (ids == null || ids.equals(""))
        ids = null; 
      if (telephone == null || telephone.equals(""))
        telephone = null; 
      if (contents == null || contents.equals(""))
        contents = null; 
      MessageBD messageSend = new MessageBD();
      boolean sendSuccess = messageSend.modelSendMsg(ids, contents, 
          telephone, domainId, session.getAttribute("userId").toString(), new Date(), Long.valueOf(0L));
      return sendSuccess;
    } 
    return false;
  }
  
  public boolean sendFileSystemMsg(String model, String title, String ids, String telephone, String fileSign, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    if (!(new ManagerService()).hasRight(session.getAttribute("userId").toString(), "09*01*01"))
      return false; 
    String sendMen = session.getAttribute("userName").toString();
    String department = session.getAttribute("orgName").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    System.out.println("========模块发短信方法=公文=ids====" + ids);
    messageSettingBD messageSetting = new messageSettingBD();
    if (messageSetting.judgePurviewMessage(model, domainId)) {
      String contents = messageSetting.getModleContents(model, title, 
          sendMen, department, domainId);
      if (ids == null || ids.equals(""))
        ids = null; 
      if (telephone == null || telephone.equals(""))
        telephone = null; 
      if (contents == null || contents.equals(""))
        contents = null; 
      MessageBD messageFileSend = new MessageBD();
      boolean sendSuccess = messageFileSend.modSysFileMsg(ids, contents, 
          telephone, fileSign, domainId);
      return sendSuccess;
    } 
    return false;
  }
  
  public boolean judgePurviewMessage(String model, String domainId) {
    boolean result = false;
    try {
      messageSettingBD messageSetting = new messageSettingBD();
      if (SysSetupReader.getInstance().hasMsg(domainId))
        result = messageSetting.judgePurviewMessage(model, domainId); 
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {}
    return result;
  }
}
