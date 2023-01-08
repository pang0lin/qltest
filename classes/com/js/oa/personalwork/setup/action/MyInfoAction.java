package com.js.oa.personalwork.setup.action;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblUserBD;
import com.buguniao.TransBuguniao;
import com.js.message.RealTimeUtil;
import com.js.oa.personalwork.setup.po.MyInfoPO;
import com.js.oa.personalwork.setup.service.MyInfoBD;
import com.js.oa.security.log.service.LogBD;
import com.js.system.util.StaticParam;
import com.js.util.util.DataSourceBase;
import com.js.util.util.MD5;
import com.js.util.util.ReadActiveXml;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyInfoAction extends Action {
  protected DataSource dataSource = null;
  
  protected Connection conn = null;
  
  protected Statement stmt = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    HttpSession session = request.getSession(true);
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "";
    String moduleName = "";
    String oprType = "";
    String oprContent = "";
    String ipAddress = request.getRemoteAddr();
    String domainId = (session.getAttribute("domainId").toString() != null) ? session.getAttribute("domainId").toString() : "";
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curUserName = request.getSession(true).getAttribute("userName").toString();
    String curOrgName = request.getSession(true).getAttribute("orgName").toString();
    String userAccount = String.valueOf(request.getSession(true).getAttribute("userAccount"));
    MyInfoActionForm myInfoActionForm = (MyInfoActionForm)actionForm;
    MyInfoBD bd = new MyInfoBD();
    String action = request.getParameter("action");
    if ("update".equals(action)) {
      MyInfoPO paraPO = new MyInfoPO();
      paraPO.setEmpDescribe(myInfoActionForm.getEmpDescribe());
      paraPO.setEmpEnglishName(myInfoActionForm.getEmpEnglishName());
      paraPO.setEmpEmail(myInfoActionForm.getEmpEmail());
      paraPO.setEmpEmail2(myInfoActionForm.getEmpEmail2());
      paraPO.setEmpEmail3(myInfoActionForm.getEmpEmail3());
      paraPO.setEmpGnome(myInfoActionForm.getEmpGnome());
      if (request.getParameter("empBirth") != null)
        paraPO.setEmpBirth(new Date(request.getParameter("empBirth").toString())); 
      paraPO.setWeixinId(myInfoActionForm.getWeixinId());
      paraPO.setEmpLivingPhoto(myInfoActionForm.getEmpLivingPhoto());
      paraPO.setEmpMobilePhone(myInfoActionForm.getEmpMobilePhone());
      paraPO.setEmpPhone(myInfoActionForm.getEmpPhone());
      paraPO.setEmpbusPhone(myInfoActionForm.getEmpbusPhone());
      paraPO.setEmpsex(myInfoActionForm.getEmpsex());
      String mes = bd.update(paraPO, (new StringBuilder(String.valueOf(curUserId))).toString(), userAccount);
      try {
        this.dataSource = (new DataSourceBase()).getDataSource();
        this.conn = this.dataSource.getConnection();
        this.stmt = this.conn.createStatement();
        this.stmt.executeUpdate("update org_employee oe set oe.EMPBUSINESSPHONE='" + myInfoActionForm.getEmpbusPhone() + "' where oe.EMP_ID=" + curUserId);
      } catch (Exception ex) {
        ex.printStackTrace();
        this.dataSource = null;
      } finally {
        try {
          this.stmt.close();
          this.conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
        this.dataSource = null;
      } 
      request.setAttribute("message", mes);
      action = "load";
    } 
    if ("load".equals(action)) {
      MyInfoPO myInfoPo = bd.load((new StringBuilder(String.valueOf(curUserId))).toString());
      myInfoActionForm.setEmpName(myInfoPo.getEmpName());
      myInfoActionForm.setEmpDescribe(myInfoPo.getEmpDescribe());
      myInfoActionForm.setEmpEmail(myInfoPo.getEmpEmail());
      myInfoActionForm.setEmpEmail2(myInfoPo.getEmpEmail2());
      myInfoActionForm.setEmpEmail3(myInfoPo.getEmpEmail3());
      myInfoActionForm.setEmpGnome(myInfoPo.getEmpGnome());
      myInfoActionForm.setEmpLivingPhoto(myInfoPo.getEmpLivingPhoto());
      myInfoActionForm.setEmpMobilePhone(myInfoPo.getEmpMobilePhone());
      myInfoActionForm.setEmpPhone(myInfoPo.getEmpPhone());
      myInfoActionForm.setEmpbusPhone(myInfoPo.getEmpbusPhone());
      myInfoActionForm.setEmpsex(myInfoPo.getEmpsex());
      myInfoActionForm.setWeixinId(myInfoPo.getWeixinId());
      request.setAttribute("empBirth", myInfoPo.getEmpBirth());
      myInfoActionForm.setEmpEnglishName(myInfoPo.getEmpEnglishName());
      myInfoActionForm.setUserAccounts(myInfoPo.getUserAccounts());
      myInfoActionForm.setUserSimpleName(myInfoPo.getUserSimpleName());
      request.setAttribute("empLivingPhoto", myInfoPo.getEmpLivingPhoto());
      String projectType = request.getParameter("projectType");
      String forward = "modiinfo";
      if (StringUtils.isNotEmpty(projectType) && "tjgzw".equals(projectType))
        forward = "modiinfo_tjgzw"; 
      return actionMapping.findForward(forward);
    } 
    if ("updatePass".equals(action)) {
      String sessionPass = (session.getAttribute("userPassword") == null) ? myInfoActionForm.getOldPass() : 
        session.getAttribute("userPassword").toString();
      String mes = bd.updatePass(sessionPass, myInfoActionForm.getOldPass(), 
          myInfoActionForm.getNewPass(), (
          new StringBuilder(String.valueOf(curUserId))).toString(), (String)request.getSession().getAttribute("userAccount"));
      if ("true".equals(mes)) {
        StaticParam.updateLastModifyPwdDate(userAccount);
        (new TransBuguniao()).updateUserPass((new MD5()).getMD5Code(myInfoActionForm.getNewPass()), userAccount, userAccount);
      } 
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse()) && 
        "true".equals(mes)) {
        TblUserBD tblUserBD = new TblUserBD();
        TblUser tblUser = new TblUser();
        try {
          tblUser = tblUserBD.findTblUser((String)request.getSession().getAttribute("userAccount"));
          tblUser.setPassWord(myInfoActionForm.getNewPass());
          tblUserBD.updateTblUser(tblUser);
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      RealTimeUtil util = new RealTimeUtil();
      if (util.getUsed() && 
        "gk".equals(util.getType()))
        util.modifyPassword(userAccount, myInfoActionForm.getNewPass(), ""); 
      request.setAttribute("message", mes);
      if (session.getAttribute("empEnglishName") != null)
        quickmail(2, session.getAttribute("empEnglishName").toString(), myInfoActionForm.getNewPass()); 
      session.setAttribute("userPassword", myInfoActionForm.getNewPass());
      String projectType = request.getParameter("projectType");
      String forward = "modipass";
      if (StringUtils.isNotEmpty(projectType) && "tjgzw".equals(projectType))
        forward = "modipass_tjgzw"; 
      Date endDate = new Date();
      moduleCode = "oa_personalwork";
      moduleName = "个人设置-密码设置";
      oprType = "2";
      oprContent = "修改登录密码";
      logBD.log(curUserId, curUserName, curOrgName, moduleCode, moduleName, startDate, endDate, oprType, oprContent, ipAddress, domainId);
      return actionMapping.findForward(forward);
    } 
    if ("rtxSet".equals(action)) {
      String rtxLogin = request.getParameter("rtxLogin");
      boolean result = bd.updateRTXLogin(curUserId.toString(), rtxLogin);
      if (bd.updateRTXLogin(curUserId.toString(), rtxLogin))
        request.setAttribute("updateRTXLogin", "1"); 
      action = "loadRTXLogin";
    } 
    if ("loadRTXLogin".equals(action)) {
      String rtxLogin = bd.loadRTXLogin(curUserId.toString());
      request.setAttribute("rtxLogin", rtxLogin);
      return actionMapping.findForward("loadRTXLogin");
    } 
    return null;
  }
  
  private void quickmail(int type, String userAccount, String pwd) {}
}
