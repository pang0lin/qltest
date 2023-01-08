package com.js.oa.logon.action;

import com.js.ldap.OAToAD;
import com.js.oa.chinaLife.ladp.OperateLdap;
import com.js.oa.chinaLife.tbUser.SynchronizeUsers;
import com.js.oa.security.log.service.LogBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.MD5;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ResetPassword extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ResetPasswordForm rpForm = (ResetPasswordForm)form;
    HttpSession session = request.getSession(true);
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "";
    String moduleName = "";
    String oprType = "";
    String oprContent = "";
    String ipAddress = request.getRemoteAddr();
    String domainIdStr = (session.getAttribute("domainId").toString() != null) ? session.getAttribute("domainId").toString() : "";
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgName = session.getAttribute("orgName").toString();
    String userAccount = session.getAttribute("userAccount").toString();
    String isDisplay = request.getParameter("isDisplay");
    String minLength = request.getParameter("minLength");
    String maxLength = request.getParameter("maxLength");
    String containNumAndLetter = request.getParameter("containNumAndLetter");
    String title = request.getParameter("title");
    String oldPassword = rpForm.getOldPassword();
    String newPassword = rpForm.getNewPassword();
    MD5 md5 = new MD5();
    boolean oldPassOK = false;
    if ("chinaLife".equals(SystemCommon.getCustomerName()) && !"admin".equals(userAccount)) {
      if ("0".equals((new OperateLdap()).authenticateUser(userAccount, oldPassword)))
        oldPassOK = true; 
    } else {
      String sessionPass = session.getAttribute("userPassword").toString();
      oldPassOK = sessionPass.equals(oldPassword);
    } 
    if (oldPassOK) {
      boolean updatePassOK = false;
      if ("chinaLife".equals(SystemCommon.getCustomerName()) && !"admin".equals(userAccount)) {
        updatePassOK = SynchronizeUsers.synchronizeUserPassword(userAccount, newPassword);
      } else {
        updatePassOK = StaticParam.modifyPassword(userAccount, md5.getMD5Code(newPassword));
      } 
      if (updatePassOK) {
        StaticParam.passwordModifyComplete(userAccount);
        String path = System.getProperty("user.dir");
        String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        int useLDAP = 0, isAccuess = 0;
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
          doc = builder.build(filePath);
        } catch (JDOMException e1) {
          isAccuess = 1;
          e1.printStackTrace();
        } catch (IOException e1) {
          isAccuess = 1;
          e1.printStackTrace();
        } 
        Element ldapConfig = doc.getRootElement().getChild("LdapConfig");
        if (ldapConfig != null) {
          useLDAP = Integer.parseInt(ldapConfig.getAttribute("use").getValue());
          if (useLDAP == 1) {
            OAToAD oaToAd = new OAToAD();
            isAccuess = oaToAd.updatePassword(newPassword, userAccount);
          } 
        } 
        Date date1 = new Date();
        moduleCode = "oa_index";
        moduleName = "首次登录重置密码";
        oprType = "2";
        oprContent = "重置密码成功";
        logBD.log(userId, userName, orgName, moduleCode, moduleName, startDate, date1, oprType, oprContent, ipAddress, domainIdStr);
        return mapping.findForward("complete");
      } 
      request.setAttribute("isDisplay", isDisplay);
      request.setAttribute("userAccount", userAccount);
      request.setAttribute("minLength", minLength);
      request.setAttribute("maxLength", maxLength);
      request.setAttribute("containNumAndLetter", containNumAndLetter);
      request.setAttribute("title", title);
      Date date = new Date();
      moduleCode = "oa_index";
      moduleName = "首次登录重置密码";
      oprType = "2";
      oprContent = "重置密码失败";
      logBD.log(userId, userName, orgName, moduleCode, moduleName, startDate, date, oprType, oprContent, ipAddress, domainIdStr);
      return mapping.findForward("fail");
    } 
    request.setAttribute("isDisplay", isDisplay);
    request.setAttribute("userAccount", userAccount);
    request.setAttribute("minLength", minLength);
    request.setAttribute("maxLength", maxLength);
    request.setAttribute("containNumAndLetter", containNumAndLetter);
    request.setAttribute("title", title);
    Date endDate = new Date();
    moduleCode = "oa_index";
    moduleName = "首次登录重置密码";
    oprType = "2";
    oprContent = "重置密码失败：原始密码错误";
    logBD.log(userId, userName, orgName, moduleCode, moduleName, startDate, endDate, oprType, oprContent, ipAddress, domainIdStr);
    return mapping.findForward("validate_fail");
  }
}
