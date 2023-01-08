package com.js.oa.personalwork.setup.bean;

import com.js.ldap.OAToAD;
import com.js.oa.chinaLife.ladp.OperateLdap;
import com.js.oa.chinaLife.tbUser.SynchronizeUsers;
import com.js.oa.personalwork.setup.po.MyInfoPO;
import com.js.system.vo.organizationmanager.SyncRTXVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.MD5;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class MyInfoEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public MyInfoPO load(String userId) throws Exception {
    MyInfoPO po = null;
    try {
      begin();
      po = (MyInfoPO)this.session.load(MyInfoPO.class, new Long(userId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public String update(MyInfoPO paraPO, String userId, String userAccount) throws Exception {
    String message = "false";
    try {
      begin();
      MyInfoPO po = (MyInfoPO)this.session.load(MyInfoPO.class, new Long(userId));
      po.setEmpsex(paraPO.getEmpsex());
      po.setEmpDescribe(paraPO.getEmpDescribe());
      po.setEmpBirth(paraPO.getEmpBirth());
      po.setEmpEmail(paraPO.getEmpEmail());
      po.setEmpEmail2(paraPO.getEmpEmail2());
      po.setEmpEmail3(paraPO.getEmpEmail3());
      po.setEmpGnome(paraPO.getEmpGnome());
      po.setEmpLivingPhoto(paraPO.getEmpLivingPhoto());
      po.setEmpMobilePhone(paraPO.getEmpMobilePhone());
      po.setEmpPhone(paraPO.getEmpPhone());
      po.setEmpbusPhone(paraPO.getEmpbusPhone());
      po.setWeixinId(paraPO.getWeixinId());
      po.setEmpEnglishName(paraPO.getEmpEnglishName());
      this.session.update(po);
      SyncRTXVO rtxVO = new SyncRTXVO();
      rtxVO.setUserAccount(userAccount);
      rtxVO.setDataOpr(Byte.valueOf("1"));
      rtxVO.setDataType(Byte.valueOf("1"));
      this.session.save(rtxVO);
      this.session.flush();
      message = "true";
    } catch (Exception e) {
      e.printStackTrace();
      message = "false";
      throw new Exception(String.valueOf(message) + e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return message;
  }
  
  public String updatePass(String sessionPass, String oldPass, String newPass, String userId, String userAccount) throws Exception {
    String message = "true";
    try {
      begin();
      MD5 md5 = new MD5();
      OAToAD oa = new OAToAD();
      String isuse = oa.canUseAD();
      MyInfoPO po = (MyInfoPO)this.session.load(MyInfoPO.class, new Long(userId));
      String oldPasswordDB = "";
      if (isuse.equals("1")) {
        oldPasswordDB = md5.getMD5Code(sessionPass);
      } else {
        oldPasswordDB = po.getUserPassword();
      } 
      String oldPassword = md5.getMD5Code(oldPass);
      if ("chinaLife".equals(SystemCommon.getCustomerName()) && !"admin".equals(userAccount)) {
        OperateLdap ol = new OperateLdap();
        if (ol.authenticateUser(userAccount, oldPass).equals("0")) {
          if (SynchronizeUsers.synchronizeUserPassword(userAccount, newPass)) {
            String newPassword = newPass;
            String password = md5.getMD5Code(newPassword);
            po.setUserPassword(password);
            po.setIsChangePwd("1");
            this.session.update(po);
          } else {
            message = "false";
          } 
        } else {
          message = "false";
        } 
      } else if (md5.equals(oldPasswordDB, oldPassword)) {
        String newPassword = newPass;
        String password = md5.getMD5Code(newPassword);
        po.setUserPassword(password);
        po.setIsChangePwd("1");
        this.session.update(po);
      } else {
        message = "false";
      } 
      int isAccuess = 0;
      if ("true".equals(message)) {
        String path = System.getProperty("user.dir");
        String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        int useLDAP = 0;
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
            isAccuess = oaToAd.updatePassword(newPass, userAccount);
          } 
        } 
        if (isAccuess == 0 || useLDAP == 0) {
          this.session.flush();
        } else {
          message = "false";
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return message;
  }
  
  public String loadRTXLogin(String userId) throws Exception {
    String result = "0";
    try {
      begin();
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      result = po.getRtxIsLogin();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean updateRTXLogin(String userId, String rtxLogin) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      po.setRtxIsLogin(rtxLogin);
      this.session.flush();
      result = new Boolean(true);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer skinSetup(String userId, String skin) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      po.setSkin(skin);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      result = Integer.valueOf("-1");
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Object[] getEmpStatus(String userId) throws Exception {
    Object[] result = { "", "", "" };
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select emp.curStatus, emp.userDefineStatus from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=" + userId);
      if (iter.hasNext())
        result = iter.next(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer saveEmpDefineStatus(String userId, String DefineStatus) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      po.setUserDefineStatus(DefineStatus);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      result = Integer.valueOf("-1");
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer saveEmpNewStatus(String userId, String NewStatus) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, 
          new Long(userId));
      po.setCurStatus(NewStatus);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      result = Integer.valueOf("-1");
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer setEmpStatus(String userId, String curStatus, String rtxlogin) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      po.setCurStatus(curStatus);
      po.setRtxIsLogin(rtxlogin);
      if (!"0".equals(curStatus)) {
        String userDefineStatus = po.getUserDefineStatus();
        if (userDefineStatus != null && !userDefineStatus.equals("") && !userDefineStatus.equalsIgnoreCase("null")) {
          if (userDefineStatus.indexOf(";" + curStatus + ";") < 0)
            po.setUserDefineStatus(String.valueOf(userDefineStatus) + curStatus + ";"); 
        } else {
          po.setUserDefineStatus(";" + curStatus + ";");
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      result = Integer.valueOf("-1");
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer delEmpStatus(String userId, String curStatus) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      String userDefineStatus = po.getUserDefineStatus();
      if (!"0".equals(curStatus) && 
        userDefineStatus != null && !userDefineStatus.equals("") && !userDefineStatus.equalsIgnoreCase("null")) {
        System.out.println("userDefineStatus.indexOf=" + userDefineStatus.indexOf(";" + curStatus + ";"));
        if (userDefineStatus.indexOf(";" + curStatus + ";") != -1) {
          userDefineStatus = userDefineStatus.replaceAll(";" + curStatus + ";", ";");
          if (";".equals(userDefineStatus))
            userDefineStatus = null; 
          System.out.println("userDefineStatus=" + userDefineStatus);
          po.setUserDefineStatus(userDefineStatus);
          if (userDefineStatus != null && 
            userDefineStatus.startsWith(";") && 
            curStatus.equals(po.getCurStatus())) {
            userDefineStatus = userDefineStatus.substring(1);
            po.setCurStatus(userDefineStatus.substring(0, 
                  userDefineStatus.indexOf(";")));
          } else if (userDefineStatus == null || "".equals(userDefineStatus)) {
            po.setCurStatus("0");
          } 
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      result = Integer.valueOf("-1");
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getEmpStatusByEmpIdArr(String[] userId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < userId.length; i++) {
        if (userId[i] != null && !"".equals(userId[i]))
          sb.append(Long.valueOf(userId[i]) + ","); 
      } 
      String sql = sb.toString();
      if (sql.endsWith(","))
        sql = sql.substring(0, sql.length() - 1); 
      result = this.session.createQuery("select emp.empName, emp.curStatus from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + sql + ") and emp.curStatus<>'0'").list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getEmpStatusByEmpIdStr(String userIds) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select emp.empName, emp.curStatus from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + userIds + ") and emp.curStatus<>'0'").list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getEmpStatusByEmpOrgGrp(String empIdStr, String orgIdStr, String grpIdStr) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      if (!empIdStr.equals("")) {
        empIdStr = empIdStr.replace('$', ',');
        empIdStr = empIdStr.substring(1, empIdStr.length() - 1);
        empIdStr = (empIdStr.indexOf(",,") >= 0) ? empIdStr.replaceAll(",,", ",") : empIdStr;
      } else {
        empIdStr = "-1";
      } 
      if (!orgIdStr.equals("")) {
        orgIdStr = orgIdStr.replace('*', ',');
        orgIdStr = orgIdStr.substring(1, orgIdStr.length() - 1);
        StringBuffer sb = new StringBuffer();
        if (orgIdStr.indexOf(",,") > 0) {
          String[] tmp = orgIdStr.split(",,");
          for (int j = 0; j < tmp.length; j++)
            sb.append("org.orgIdString like '%$" + tmp[j] + "$%' or "); 
        } else {
          sb.append(" org.orgIdString like '%$" + orgIdStr + "$%' ");
        } 
        String whereSql = sb.toString();
        whereSql = whereSql.endsWith("or ") ? whereSql.substring(0, whereSql.length() - 3) : whereSql;
        List<String> list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where (" + whereSql + ") and emp.empId not in (" + empIdStr + ")").list();
        for (int i = 0; i < list.size(); i++)
          empIdStr = String.valueOf(empIdStr) + "," + list.get(i); 
      } 
      if (!grpIdStr.equals("")) {
        grpIdStr = grpIdStr.replace('*', ',');
        grpIdStr = grpIdStr.substring(1, grpIdStr.length() - 1);
        grpIdStr = (grpIdStr.indexOf(",,") >= 0) ? grpIdStr.replaceAll(",,", ",") : grpIdStr;
        if (grpIdStr.indexOf("#") >= 0)
          grpIdStr = grpIdStr.replace("##", ",").replace("#", ""); 
        List<String> list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.groups grp where grp.groupId in (" + grpIdStr + ") and emp.empId not in (" + empIdStr + ")").list();
        for (int i = 0; i < list.size(); i++)
          empIdStr = String.valueOf(empIdStr) + "," + list.get(i); 
      } 
      if (empIdStr.endsWith(","))
        empIdStr = empIdStr.substring(0, empIdStr.length() - 1); 
      result = this.session.createQuery("select emp.empName, emp.curStatus from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + empIdStr + ") and emp.curStatus<>'0'").list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
}
