package com.js.oa.pressdeal.bean;

import com.js.oa.pressdeal.po.OaPersonoaFeedbackPO;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO;
import com.js.oa.pressdeal.util.ConversionString;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.RemindUtil;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import net.sf.hibernate.HibernateException;

public class PressDealDoEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String AddPress(OaPersonoaPressPO press) throws HibernateException {
    String rslt = "false";
    begin();
    try {
      this.session.save(press);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String updatePress(OaPersonoaPressPO press) throws HibernateException {
    String rslt = "false";
    begin();
    try {
      this.session.update(press);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public OaPersonoaPressPO getPressByIdLoad(String pressid) throws HibernateException {
    OaPersonoaPressPO rslt = null;
    begin();
    try {
      rslt = (OaPersonoaPressPO)this.session.load(OaPersonoaPressPO.class, new Long(pressid));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = null;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public OaPersonoaPressPO getPressByIdHql(String pressid) throws HibernateException {
    OaPersonoaPressPO rslt = null;
    begin();
    try {
      rslt = (OaPersonoaPressPO)this.session.load(OaPersonoaPressPO.class, new Long(pressid));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = null;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String SendPress(String receiveUserIdStr, String sendUserId, String sendUsername, String sendUserDep, String press_title, String press_content, String press_category, String press_subcategory, String domainId) throws HibernateException, ParseException, HibernateException {
    String rslt = "false";
    String receiveUserStr = "";
    String[] receiveUserIdArray = receiveUserIdStr.split("-");
    String[] receiveUserNameArray = new String[receiveUserIdArray.length];
    String[] receiveUserOrgId = new String[receiveUserIdArray.length];
    String[] receiveUserOrgName = new String[receiveUserIdArray.length];
    for (int i = 0; i < receiveUserIdArray.length; i++) {
      String tmpUserName = getUserName(receiveUserIdArray[i]);
      receiveUserNameArray[i] = tmpUserName;
      String[] tmpArray = getOrgId_name(receiveUserIdArray[i]).split("-");
      receiveUserOrgId[i] = tmpArray[0];
      receiveUserOrgName[i] = tmpArray[1];
      receiveUserStr = String.valueOf(receiveUserStr) + tmpUserName + ",";
    } 
    OaPersonoaPressPO press = new OaPersonoaPressPO();
    press.setCatorgry(press_category);
    press.setContent(press_content);
    press.setDispatchTime(Timestamp.valueOf(getDatabaseTime()));
    press.setDomainId(new Long(domainId));
    press.setIsNew(new Short("0"));
    press.setReceiveUsernameStr(receiveUserStr);
    press.setSendUserDep(sendUserDep);
    press.setSendUsername(sendUsername);
    press.setSubcatorgryName(press_subcategory);
    press.setTitle(press_title);
    begin();
    try {
      this.session.save(press);
      this.session.refresh(press);
      OaPersonoaUserPressRelatioPO[] opupr = new OaPersonoaUserPressRelatioPO[receiveUserIdArray.length + 1];
      opupr[0].setDomainId(new Long(domainId));
      String[] strTmp = getOrgId_name(sendUserId).split("-");
      opupr[0].setOrgId(new Long(strTmp[0]));
      opupr[0].setOrgName(strTmp[1]);
      opupr[0].setPressId(press.getPressId());
      opupr[0].setPressStatus(new Byte("0"));
      opupr[0].setUserId(new Long(sendUserId));
      opupr[0].setUserName(sendUsername);
      this.session.save(opupr[0]);
      for (int j = 1; j < opupr.length; j++) {
        opupr[j].setDomainId(new Long(domainId));
        opupr[j].setOrgId(new Long(receiveUserOrgId[j - 1]));
        opupr[j].setOrgName(receiveUserOrgName[j - 1]);
        opupr[j].setPressId(press.getPressId());
        opupr[j].setPressStatus(new Byte("1"));
        opupr[j].setUserId(new Long(receiveUserIdArray[j - 1]));
        opupr[j].setUserName(receiveUserNameArray[j - 1]);
        this.session.save(opupr[j]);
      } 
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String SendPress_receiveUserNameStr(String receiveUserIdStr, String receiveUserNameStr, String sendUserId, String sendUserName, String sendUserDep, String press_title, String press_content, String press_category, String press_subcategory, String domainId) throws HibernateException, ParseException, HibernateException {
    String rslt = "false";
    String[] receiveUserIdArray = receiveUserIdStr.split("-");
    String[] receiveUserNameArray = receiveUserNameStr.split(",");
    String[] receiveUserOrgId = new String[receiveUserIdArray.length];
    String[] receiveUserOrgName = new String[receiveUserIdArray.length];
    for (int i = 0; i < receiveUserIdArray.length; i++) {
      String[] tmpArray = getOrgId_name(receiveUserIdArray[i]).split("-");
      receiveUserOrgId[i] = tmpArray[0];
      receiveUserOrgName[i] = tmpArray[1];
    } 
    String tmpdate = getDatabaseTime();
    OaPersonoaPressPO press = new OaPersonoaPressPO();
    press.setCatorgry(press_category);
    press.setContent(press_content);
    press.setDispatchTime(Timestamp.valueOf(tmpdate));
    press.setDomainId(new Long(domainId));
    press.setIsNew(new Short("0"));
    press.setReceiveUsernameStr(receiveUserNameStr);
    press.setSendUserDep(sendUserDep);
    press.setSendUsername(sendUserName);
    press.setSubcatorgryName(press_subcategory);
    press.setTitle(press_title);
    OaPersonoaUserPressRelatioPO[] opupr = new OaPersonoaUserPressRelatioPO[receiveUserIdArray.length + 1];
    opupr[0] = new OaPersonoaUserPressRelatioPO();
    opupr[0].setDomainId(new Long(domainId));
    String[] strTmp = getOrgId_name(sendUserId).split("-");
    opupr[0].setOrgId(new Long(strTmp[0]));
    opupr[0].setOrgName(strTmp[1]);
    opupr[0].setPressStatus(new Byte("0"));
    opupr[0].setUserId(new Long(sendUserId));
    opupr[0].setUserName(sendUserName);
    int j;
    for (j = 1; j < opupr.length; j++) {
      opupr[j] = new OaPersonoaUserPressRelatioPO();
      opupr[j].setDomainId(new Long(domainId));
      opupr[j].setOrgId(new Long(receiveUserOrgId[j - 1]));
      opupr[j].setOrgName(receiveUserOrgName[j - 1]);
      opupr[j].setPressStatus(new Byte("1"));
      opupr[j].setUserId(new Long(receiveUserIdArray[j - 1]));
      opupr[j].setUserName(receiveUserNameArray[j - 1]);
    } 
    begin();
    try {
      this.session.save(press);
      for (j = 0; j < opupr.length; j++) {
        opupr[j].setPressId(press.getPressId());
        this.session.save(opupr[j]);
      } 
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String addPersonUserPressER(OaPersonoaUserPressRelatioPO opupr) throws HibernateException {
    String rslt = "false";
    begin();
    try {
      this.session.save(opupr);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String delPersonUserPressER(OaPersonoaUserPressRelatioPO opupr) throws HibernateException {
    String rslt = "false";
    begin();
    try {
      this.session.delete(opupr);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public OaPersonoaUserPressRelatioPO getPersonUserPressRelationByLoad(String entityId) throws HibernateException {
    OaPersonoaUserPressRelatioPO rslt = null;
    begin();
    try {
      rslt = (OaPersonoaUserPressRelatioPO)this.session.load(OaPersonoaUserPressRelatioPO.class, new Long(entityId));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = null;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public OaPersonoaUserPressRelatioPO getPersonUserPressRelationByHql(String entityId) throws HibernateException {
    OaPersonoaUserPressRelatioPO rslt = null;
    begin();
    try {
      rslt = (OaPersonoaUserPressRelatioPO)this.session.load(OaPersonoaUserPressRelatioPO.class, new Long(entityId));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = null;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String addFeedback(OaPersonoaFeedbackPO feedback) throws HibernateException {
    String rslt = "false";
    OaPersonoaPressPO press = getPressByIdHql(String.valueOf(feedback.getPressId()));
    press.setIsNew(new Short("1"));
    begin();
    try {
      this.session.save(feedback);
      this.session.update(press);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public List getAllFeedbackList(String pressId) throws HibernateException {
    List rslt = null;
    OaPersonoaPressPO press = null;
    press = getPressByIdHql(pressId);
    begin();
    try {
      String hql = "";
      List list = null;
      press.setIsNew(new Short("0"));
      hql = "from com.js.oa.pressdeal.po.OaPersonoaFeedbackPO feedback where feedback.pressId = " + pressId;
      list = this.session.find(hql);
      this.session.update(press);
      this.session.flush();
      if (list != null) {
        rslt = list;
      } else {
        rslt = new ArrayList();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      rslt = new ArrayList();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String getUserName(String userid) throws HibernateException {
    String rslt = "";
    String hql = "select empvo.empname from com.js.system.vo.usermanager.EmployeeVO empvo where empvo.empId = '" + userid + "'";
    begin();
    try {
      List<String> list = this.session.find(hql);
      rslt = list.get(0);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String getOrgId_name(String userid) throws HibernateException {
    String rslt = "";
    String hql = "select orgvo.orgId,orgvo.orgName from com.js.system.vo.organizationmanager.OrganizationVO orgvo join orgvo.employees orgemp where orgemp.empId= " + userid;
    begin();
    try {
      List<Object[]> list = this.session.find(hql);
      Object[] tup = list.get(0);
      rslt = String.valueOf(String.valueOf(tup[0])) + "-" + String.valueOf(tup[1]);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String Del_Send_Press(String pressId) throws HibernateException {
    List<OaPersonoaUserPressRelatioPO> opupr = getUserPressERList(pressId);
    List<OaPersonoaFeedbackPO> feedback = getPressFeedbackList(pressId);
    OaPersonoaPressPO press = getPressByIdHql(pressId);
    String rslt = "false";
    begin();
    try {
      if (opupr != null && opupr.size() > 0)
        for (int i = 0; i < opupr.size(); i++) {
          OaPersonoaUserPressRelatioPO tmp = opupr.get(i);
          this.session.delete(tmp);
        }  
      if (feedback != null && feedback.size() > 0)
        for (int i = 0; i < feedback.size(); i++) {
          OaPersonoaFeedbackPO tmpf = feedback.get(i);
          this.session.delete(tmpf);
        }  
      if (press != null)
        this.session.delete(press); 
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public List getUserPressERList(String pressId) throws HibernateException {
    List rslt = null;
    String hql = "from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr where opupr.pressId =" + pressId;
    begin();
    try {
      rslt = this.session.find(hql);
      if (rslt == null)
        rslt = new ArrayList(); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = new ArrayList();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public List getPressFeedbackList(String pressId) throws HibernateException {
    List rslt = null;
    String hql = "from com.js.oa.pressdeal.po.OaPersonoaFeedbackPO feedback where feedback.pressId = " + pressId;
    begin();
    try {
      rslt = this.session.find(hql);
      if (rslt == null)
        rslt = new ArrayList(); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = new ArrayList();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String getDatabaseTime() throws HibernateException, ParseException {
    Date rslt_date = null;
    begin();
    String rslt_str = "";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      DbOpt dbtypeutil = new DbOpt();
      String sql = "";
      if (DbOpt.dbtype.equals("oracle")) {
        sql = "select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual ";
      } else {
        sql = "select distinct convert(varchar,getdate(),120) from sysobjects ";
      } 
      try {
        ResultSet rs = conn.createStatement().executeQuery(sql);
        rs.next();
        rslt_str = rs.getString(1);
        rs.close();
      } catch (Exception e2) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        rslt_str = sdf.format(date);
      } 
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      rslt_str = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt_str;
  }
  
  public String del_receive_press(String pressId, String userId, String pressStatus) throws HibernateException {
    String rslt = "false";
    String hql = "from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr where opupr.pressId =" + pressId + " and opupr.pressStatus =" + pressStatus + " and opupr.userId =" + userId;
    List<OaPersonoaUserPressRelatioPO> list = null;
    begin();
    try {
      list = this.session.find(hql);
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          OaPersonoaUserPressRelatioPO tmp = list.get(i);
          this.session.delete(tmp);
        }  
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public List getUser_Press_List(String pressId, String press_status) throws HibernateException {
    String hql = "from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr where opupr.pressId =" + pressId + " and opupr.pressStatus =" + press_status;
    List rslt = null;
    begin();
    try {
      rslt = this.session.find(hql);
      if (rslt == null)
        rslt = new ArrayList(); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      rslt = new ArrayList();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String getUserAccountsByEmpId(String EmpId) throws HibernateException {
    String hql = "select vo.userAccounts  from com.js.system.vo.usermanager.EmployeeVO vo where vo.empId = " + EmpId;
    String rslt = "";
    List list = null;
    begin();
    try {
      list = this.session.find(hql);
      rslt = String.valueOf(list.get(0));
    } catch (Exception e) {
      rslt = "";
      e.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public List getUserAccountsListByEmpIdStr(String empIdstr) throws HibernateException {
    List list = null;
    String hql = "select select vo.userAccounts  from com.js.system.vo.usermanager.EmployeeVO vo where vo.empId in( " + empIdstr + " )";
    begin();
    try {
      list = this.session.find(hql);
      if (list == null)
        list = new ArrayList(); 
    } catch (Exception e) {
      e.printStackTrace();
      list = new ArrayList();
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public String updateAllSendPress(String userId, String domainId) throws Exception {
    String rslt = "false";
    String hql = "from com.js.oa.pressdeal.po.OaPersonoaPressPO po where po.pressId in (  select opupr.pressId from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr where opupr.userId =" + 
      userId + " and opupr.pressStatus = 0 and opupr.domainId = " + domainId + 
      " ) ";
    begin();
    try {
      List<OaPersonoaPressPO> list = this.session.find(hql);
      if (list != null) {
        for (int i = 0; i < list.size(); i++) {
          OaPersonoaPressPO po = list.get(i);
          po.setIsNew(new Short("0"));
          this.session.update(po);
          this.session.flush();
        } 
        rslt = "true";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String getUserIds(String orginIds) throws Exception {
    String rslt = "";
    ConversionString cs = new ConversionString(orginIds);
    String grpIds = cs.getGroupIdString();
    String orgIds = cs.getOrgIdString();
    String userIds = cs.getUserIdString();
    DbOpt dbopt = null;
    dbopt = new DbOpt();
    String hql = "";
    List list = null;
    begin();
    try {
      if (orgIds != null && orgIds.length() > 0) {
        String[] orgId = orgIds.split(",");
        for (int j = 0; j < orgId.length; j++) {
          String orgCode = dbopt.executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + orgId[j]);
          ResultSet rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + orgCode + "%')");
          while (rs.next())
            rslt = String.valueOf(rslt) + String.valueOf(rs.getLong(1)) + ","; 
        } 
      } 
      if (grpIds != null && grpIds.length() > 0) {
        String[] grpId = grpIds.split(",");
        for (int j = 0; j < grpId.length; j++) {
          String empIdStr = dbopt.executeQueryToStr(
              "select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + grpId[j]);
          ConversionString tmpcs = new ConversionString(empIdStr);
          rslt = String.valueOf(rslt) + tmpcs.getUserIdString() + ",";
        } 
      } 
      if (userIds != null && userIds.trim().length() > 0)
        rslt = String.valueOf(rslt) + userIds; 
      String[] tmpar = rslt.split(",");
      String tmprslt = "";
      for (int i = 0; i < tmpar.length; i++) {
        if (!"".equals(tmpar[i]) && !"0".equals(tmpar[i]))
          tmprslt = String.valueOf(tmprslt) + tmpar[i] + ","; 
      } 
      if (1 < tmprslt.length())
        tmprslt = tmprslt.substring(0, tmprslt.length() - 1); 
      rslt = tmprslt;
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      hql = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  private List getAlluserOrgName(String userIds) throws Exception {
    if (userIds.lastIndexOf(",") == userIds.length() - 1)
      userIds = userIds.substring(0, userIds.length() - 1); 
    userIds = userIds.replaceAll(",0", "");
    List list = new ArrayList();
    String hql = "";
    begin();
    try {
      String[] userId = userIds.split(",");
      int num = userId.length / 900 + 1;
      for (int i = 0; i < num; i++) {
        String tmpUserIds = "";
        for (int j = i * 900; j < (i + 1) * 900 && j < userId.length; j++)
          tmpUserIds = String.valueOf(tmpUserIds) + userId[j] + ","; 
        if (tmpUserIds.length() > 1)
          tmpUserIds = tmpUserIds.substring(0, tmpUserIds.length() - 1); 
        List tmplist = this.session.find("select orgvo.orgId,orgvo.orgName,orgemp.empId,orgemp.empName from com.js.system.vo.organizationmanager.OrganizationVO orgvo join orgvo.employees orgemp where orgemp.empId in ( " + tmpUserIds + ")");
        list.addAll(tmplist);
      } 
      if (list == null)
        list = new ArrayList(); 
    } catch (Exception e) {
      e.printStackTrace();
      list = new ArrayList();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public String send_press_orgin(String orginIds, String receiveNameStr, String sendUserId, String sendUserName, String sendUserDep, String press_title, String press_content, String press_category, String press_subcategory, String domainId, HttpServletRequest request) throws Exception {
    String rslt = "";
    String table = request.getParameter("tableId");
    String record = request.getParameter("recordId");
    String processId = request.getParameter("processId");
    String userIds = getUserIds(orginIds);
    List<Object[]> userOrg = getAlluserOrgName(userIds);
    String tmpdate = getDatabaseTime();
    String workflowurl = "";
    if (table != null && record != null && processId != null && !"".equals(table) && !"".equals(record) && !"".equals(processId))
      workflowurl = String.valueOf(table) + "&&" + record + "&&" + processId; 
    OaPersonoaPressPO press = new OaPersonoaPressPO();
    press.setCatorgry(press_category);
    press.setContent(press_content);
    press.setDispatchTime(Timestamp.valueOf(tmpdate));
    press.setDomainId(new Long(domainId));
    press.setIsNew(new Short("0"));
    press.setReceiveUsernameStr(receiveNameStr);
    press.setSendUserDep(sendUserDep);
    press.setSendUsername(sendUserName);
    press.setSubcatorgryName(press_subcategory);
    press.setTitle(press_title);
    press.setPressStatus(Byte.valueOf("0"));
    press.setWorkflowurl(workflowurl);
    OaPersonoaUserPressRelatioPO[] opupr = new OaPersonoaUserPressRelatioPO[userOrg.size() + 1];
    opupr[0] = new OaPersonoaUserPressRelatioPO();
    opupr[0].setDomainId(new Long(domainId));
    if ("0".equals(sendUserId)) {
      opupr[0].setOrgId(new Long(sendUserId));
      opupr[0].setOrgName(sendUserDep);
    } else {
      String[] strTmp = getOrgId_name(sendUserId).split("-");
      opupr[0].setOrgId(new Long(strTmp[0]));
      opupr[0].setOrgName(strTmp[1]);
    } 
    opupr[0].setPressStatus(new Byte("0"));
    opupr[0].setUserId(new Long(sendUserId));
    opupr[0].setUserName(sendUserName);
    if (userOrg != null && userOrg.size() > 0)
      for (int i = 0; i < userOrg.size(); i++) {
        Object[] tup = userOrg.get(i);
        opupr[i + 1] = new OaPersonoaUserPressRelatioPO();
        opupr[i + 1].setDomainId(new Long(domainId));
        opupr[i + 1].setOrgId(new Long(String.valueOf(tup[0])));
        opupr[i + 1].setOrgName(String.valueOf(tup[1]));
        opupr[i + 1].setPressStatus(new Byte("1"));
        opupr[i + 1].setUserId(new Long(String.valueOf(tup[2])));
        opupr[i + 1].setUserName(String.valueOf(tup[3]));
      }  
    begin();
    try {
      Long pressID = (Long)this.session.save(press);
      if (userOrg != null && userOrg.size() > 0)
        for (int j = 0; j < userOrg.size(); j++) {
          Object[] tup = userOrg.get(j);
          Calendar tmp = Calendar.getInstance();
          tmp.set(2050, 12, 12);
          Date endDate = tmp.getTime();
          String url = "/jsoa/pressManageAction.do?action=goto_display_press&pressId=" + pressID + "&actType=receivePress&remind=Y&workflow=1";
          String title = press.getContent();
          RemindUtil.sendMessageToUsers(title, url, String.valueOf(tup[2]), "Press", new Date(), endDate, press.getSendUsername(), pressID);
        }  
      for (int i = 0; i < opupr.length; i++) {
        opupr[i].setPressId(press.getPressId());
        this.session.save(opupr[i]);
      } 
      this.session.flush();
      rslt = userIds;
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String send_press_orgin2(String orginIds, String receiveNameStr, String sendUserId, String sendUserName, String sendUserDep, String press_title, String press_content, String press_category, String press_subcategory, String domainId) throws Exception {
    String rslt = "";
    String userIds = getUserIds(orginIds);
    List<Object[]> userOrg = getAlluserOrgName(userIds);
    String tmpdate = getDatabaseTime();
    OaPersonoaPressPO press = new OaPersonoaPressPO();
    press.setCatorgry(press_category);
    press.setContent(press_content);
    press.setDispatchTime(Timestamp.valueOf(tmpdate));
    press.setDomainId(new Long(domainId));
    press.setIsNew(new Short("0"));
    press.setReceiveUsernameStr(receiveNameStr);
    press.setSendUserDep(sendUserDep);
    press.setSendUsername(sendUserName);
    press.setSubcatorgryName(press_subcategory);
    press.setTitle(press_title);
    OaPersonoaUserPressRelatioPO[] opupr = new OaPersonoaUserPressRelatioPO[userOrg.size() + 1];
    opupr[0] = new OaPersonoaUserPressRelatioPO();
    opupr[0].setDomainId(new Long(domainId));
    if ("0".equals(sendUserId)) {
      opupr[0].setOrgId(new Long(sendUserId));
    } else {
      String[] strTmp = getOrgId_name(sendUserId).split("-");
      opupr[0].setOrgId(new Long(strTmp[0]));
    } 
    opupr[0].setOrgName(sendUserDep);
    opupr[0].setPressStatus(new Byte("0"));
    opupr[0].setUserId(new Long(sendUserId));
    opupr[0].setUserName(sendUserName);
    if (userOrg != null && userOrg.size() > 0)
      for (int i = 0; i < userOrg.size(); i++) {
        Object[] tup = userOrg.get(i);
        opupr[i + 1] = new OaPersonoaUserPressRelatioPO();
        opupr[i + 1].setDomainId(new Long(domainId));
        opupr[i + 1].setOrgId(new Long(String.valueOf(tup[0])));
        opupr[i + 1].setOrgName(String.valueOf(tup[1]));
        opupr[i + 1].setPressStatus(new Byte("1"));
        opupr[i + 1].setUserId(new Long(String.valueOf(tup[2])));
        opupr[i + 1].setUserName(String.valueOf(tup[3]));
      }  
    begin();
    try {
      this.session.save(press);
      for (int i = 0; i < opupr.length; i++) {
        opupr[i].setPressId(press.getPressId());
        this.session.save(opupr[i]);
      } 
      this.session.flush();
      rslt = userIds;
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String updateFeedbackStatus(Long feedback_Id) throws Exception {
    String rslt = "false";
    begin();
    try {
      OaPersonoaFeedbackPO feedback = (OaPersonoaFeedbackPO)this.session.load(OaPersonoaFeedbackPO.class, feedback_Id);
      feedback.setFeedbackStatus(new Byte("1"));
      this.session.update(feedback);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public String updatePressStatus(Long press_Id) throws Exception {
    String rslt = "false";
    begin();
    try {
      OaPersonoaPressPO press = (OaPersonoaPressPO)this.session.load(OaPersonoaPressPO.class, press_Id);
      press.setPressStatus(new Byte("1"));
      this.session.update(press);
      this.session.flush();
      rslt = "true";
    } catch (Exception e) {
      e.printStackTrace();
      rslt = "false";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return rslt;
  }
  
  public OaPersonoaFeedbackPO loadFeedback(Long feedback_Id) throws Exception {
    OaPersonoaFeedbackPO po = null;
    begin();
    try {
      po = (OaPersonoaFeedbackPO)this.session.load(OaPersonoaFeedbackPO.class, feedback_Id);
    } catch (Exception e) {
      e.printStackTrace();
      po = null;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return po;
  }
  
  public String loadWfwordataUrl(String tableId, String recordId, String processId, String workstatu, String curUserId, String[] ss, String ismark) throws HibernateException {
    String url = "";
    List<Object[]> list = new ArrayList();
    Object[] obj = (Object[])null;
    try {
      begin();
      String sql = " select aaa.workFileType,aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel,aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup  ,aaa.workDeadlineDate,aaa.stickie,aaa.workTitle  from com.js.oa.jsflow.po.WFWorkPO aaa  where  aaa.workTableId=" + 






































        
        tableId + " " + 
        "and aaa.workRecordId=" + recordId + " " + 
        "and aaa.workProcessId=" + processId + " " + 
        "and aaa.workStatus=" + workstatu + " " + 
        "and aaa.wfCurEmployeeId=" + curUserId + " ";
      list = this.session.createQuery(sql).list();
      if (list != null && list.size() > 0) {
        obj = list.get(0);
        if ("weixin".equals(ismark)) {
          url = obj[16] + "&search=" + "&from=dealwith" + 
            "&workTitle=" + URLEncoder.encode(URLEncoder.encode((obj[37] != null) ? obj[37].toString() : "", "utf-8"), "utf-8") + 
            "&activityName=" + URLEncoder.encode(URLEncoder.encode((obj[18] != null) ? obj[18].toString() : "", "utf-8"), "utf-8") + 
            "&submitPersonId=" + URLEncoder.encode(URLEncoder.encode((obj[12] != null) ? obj[12].toString() : "", "utf-8"), "utf-8") + 
            "&submitPerson=" + URLEncoder.encode(URLEncoder.encode((obj[11] != null) ? obj[11].toString() : "", "utf-8"), "utf-8") + 
            "&work=" + URLEncoder.encode(URLEncoder.encode((obj[10] != null) ? obj[10].toString() : "", "utf-8"), "utf-8") + 
            "&workType=" + obj[6] + 
            "&activity=" + obj[7] + 
            "&table=" + tableId + 
            "&record=" + recordId + 
            "&processName=" + URLEncoder.encode(URLEncoder.encode((obj[0] != null) ? obj[0].toString() : "", "utf-8"), "utf-8") + 
            "&workStatus=0" + 
            "&submitTime=" + obj[17] + 
            "&processId=" + processId + 
            "&stepCount=" + obj[15] + 
            "&isStandForWork=" + obj[20] + 
            "&standForUserId=" + obj[21] + 
            "&standForUserName=" + obj[22] + 
            "&initActivity=" + obj[27] + 
            "&initActivityName=" + URLEncoder.encode(URLEncoder.encode((obj[28] != null) ? obj[28].toString() : "", "utf-8"), "utf-8") + 
            "&submitPersonTime=" + obj[5] + 
            "&tranType=" + obj[29] + 
            "&tranFromPersonId=" + obj[30] + 
            "&processDeadlineDate=" + obj[31];
        } else {
          url = obj[16] + 
            "&search=" + 
            "&from=dealwith" + 
            "&workTitle=" + obj[37] + 
            "&activityName=" + obj[18] + 
            "&submitPersonId=" + obj[12] + 
            "&submitPerson=" + obj[11] + 
            "&work=" + obj[10] + 
            "&workType=" + obj[6] + 
            "&activity=" + obj[7] + 
            "&table=" + tableId + 
            "&record=" + recordId + 
            "&processName=" + obj[0] + 
            "&workStatus=0" + 
            "&submitTime=" + obj[17] + 
            "&processId=" + processId + 
            "&stepCount=" + obj[15] + 
            "&isStandForWork=" + obj[20] + 
            "&standForUserId=" + obj[21] + 
            "&standForUserName=" + obj[22] + 
            "&initActivity=" + obj[27] + 
            "&initActivityName=" + obj[28] + 
            "&submitPersonTime=" + obj[5] + 
            "&tranType=" + obj[29] + 
            "&tranFromPersonId=" + obj[30] + 
            "&processDeadlineDate=" + obj[31];
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.transaction = null;
    } 
    return url;
  }
  
  private Connection getConnection() {
    Connection conn = null;
    try {
      InitialContext initCtx = new InitialContext();
      DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/jsdb");
      conn = ds.getConnection();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return conn;
  }
  
  public String getReceiveCount(String sql) {
    String sum = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        sum = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return sum;
  }
  
  public String getUrlFormWorkIdToGuoTou(String tableId, String recordId, String processId, String curUserId, String ismark) {
    String url = "";
    List<Object[]> list = new ArrayList();
    Object[] obj = (Object[])null;
    try {
      begin();
      String sql = " select aaa.workFileType,aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel,aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup  ,aaa.workDeadlineDate,aaa.stickie,aaa.workTitle  from com.js.oa.jsflow.po.WFWorkPO aaa  where  aaa.workTableId=" + 






































        
        tableId + " " + 
        "and aaa.workRecordId=" + recordId + " " + 
        "and aaa.workProcessId=" + processId + " " + 
        "and aaa.workStatus=101 " + 
        "and aaa.wfCurEmployeeId=" + curUserId + " ";
      list = this.session.createQuery(sql).list();
      if (list != null && list.size() > 0) {
        obj = list.get(0);
        if ("weixin".equals(ismark)) {
          url = obj[16] + "&search=" + "&from=dealwith" + 
            "&workTitle=" + URLEncoder.encode(URLEncoder.encode((obj[37] != null) ? obj[37].toString() : "", "utf-8"), "utf-8") + 
            "&activityName=" + URLEncoder.encode(URLEncoder.encode((obj[18] != null) ? obj[18].toString() : "", "utf-8"), "utf-8") + 
            "&submitPersonId=" + URLEncoder.encode(URLEncoder.encode((obj[12] != null) ? obj[12].toString() : "", "utf-8"), "utf-8") + 
            "&submitPerson=" + URLEncoder.encode(URLEncoder.encode((obj[11] != null) ? obj[11].toString() : "", "utf-8"), "utf-8") + 
            "&work=" + URLEncoder.encode(URLEncoder.encode((obj[10] != null) ? obj[10].toString() : "", "utf-8"), "utf-8") + 
            "&workType=" + obj[6] + 
            "&activity=" + obj[7] + 
            "&table=" + obj[8] + 
            "&record=" + obj[9] + 
            "&processName=" + URLEncoder.encode(URLEncoder.encode((obj[0] != null) ? obj[0].toString() : "", "utf-8"), "utf-8") + 
            "&workStatus=1011" + 
            "&submitTime=" + obj[17] + 
            "&processId=" + obj[14] + 
            "&stepCount=" + obj[15] + 
            "&isStandForWork=" + obj[20] + 
            "&standForUserId=" + obj[21] + 
            "&standForUserName=" + obj[22] + 
            "&initActivity=" + obj[27] + 
            "&initActivityName=" + URLEncoder.encode(URLEncoder.encode((obj[28] != null) ? obj[28].toString() : "", "utf-8"), "utf-8") + 
            "&submitPersonTime=" + obj[5] + 
            "&tranType=" + obj[29] + 
            "&tranFromPersonId=" + obj[30] + 
            "&processDeadlineDate=" + obj[31];
        } else {
          url = obj[16] + 
            "&search=" + 
            "&from=dealwith" + 
            "&workTitle=" + obj[37] + 
            "&activityName=" + obj[18] + 
            "&submitPersonId=" + obj[12] + 
            "&submitPerson=" + obj[11] + 
            "&work=" + obj[10] + 
            "&workType=" + obj[6] + 
            "&activity=" + obj[7] + 
            "&table=" + obj[8] + 
            "&record=" + obj[9] + 
            "&processName=" + obj[0] + 
            "&workStatus=1011" + 
            "&submitTime=" + obj[17] + 
            "&processId=" + obj[14] + 
            "&stepCount=" + obj[15] + 
            "&isStandForWork=" + obj[20] + 
            "&standForUserId=" + obj[21] + 
            "&standForUserName=" + obj[22] + 
            "&initActivity=" + obj[27] + 
            "&initActivityName=" + obj[28] + 
            "&submitPersonTime=" + obj[5] + 
            "&tranType=" + obj[29] + 
            "&tranFromPersonId=" + obj[30] + 
            "&processDeadlineDate=" + obj[31];
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.transaction = null;
    } 
    return url;
  }
  
  public String checkCooperate(String pressid) {
    String flag = "0";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "SELECT CATORGRY FROM OA_PERSONOA_PRESS WHERE press_id=?";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, pressid);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        if (rs.getString(1).indexOf("协同") >= 0)
          flag = "1"; 
      } 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return flag;
  }
}
