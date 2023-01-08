package com.js.oa.chinaLife.tbUser;

import com.js.oa.audit.po.AuditEmployeePO;
import com.js.oa.chinaLife.ladp.LdapOp;
import com.js.oa.chinaLife.tam.TAMUserOp;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SynchronizeUsers {
  private static boolean syncFlag = getSyncFlag();
  
  private boolean addUser(UserData user) {
    boolean flag = false;
    if (syncFlag) {
      LdapOp op = new LdapOp();
      flag = op.opLdap(user, "1");
      if (flag) {
        TAMUserOp to = new TAMUserOp();
        flag = to.addTAMUser(user.getUserCode());
        if (!flag)
          op.opLdap(user, "3"); 
      } 
    } else {
      flag = true;
    } 
    return flag;
  }
  
  private boolean updateUser(UserData user) {
    boolean flag = false;
    if (syncFlag) {
      LdapOp op = new LdapOp();
      flag = op.opLdap(user, "2");
      if (flag) {
        TAMUserOp to = new TAMUserOp();
        flag = to.modifyTAMUser(user.getUserCode());
      } 
    } else {
      flag = true;
    } 
    return flag;
  }
  
  private boolean deleteUser(UserData user) {
    boolean flag = false;
    if (syncFlag) {
      TAMUserOp to = new TAMUserOp();
      flag = to.delTAMUser(user.getUserCode());
    } else {
      flag = true;
    } 
    return flag;
  }
  
  public static boolean synchronizeUsersFromEmployeeVO(EmployeeVO vo, String flag) {
    boolean result = false;
    if (SystemCommon.getCustomerName().equals("chinaLife") && syncFlag) {
      SynchronizeUsers su = new SynchronizeUsers();
      UserData user = su.getUserDataFromEmployeeVO(vo);
      if ("1".equals(flag) || "4".equals(flag)) {
        System.out.println("调用人寿人员同步--人员信息新增（非审计）");
        result = su.addUser(user);
      } else if ("2".equals(flag) || "5".equals(flag)) {
        System.out.println("调用人寿人员同步--人员信息修改（非审计）");
        result = su.updateUser(user);
      } else {
        System.out.println("调用人寿人员同步--人员信息删除（非审计）");
        result = su.deleteUser(user);
      } 
    } else {
      result = true;
    } 
    return result;
  }
  
  public static boolean synchronizeUserPassword(String uid, String password) {
    boolean result = false;
    if (SystemCommon.getCustomerName().equals("chinaLife") && syncFlag) {
      LdapOp op = new LdapOp();
      result = op.updatePassword(uid, password);
    } else {
      result = true;
    } 
    return result;
  }
  
  public static boolean synchronizeUsersFromAuditEmployeePO(AuditEmployeePO po, String flag) {
    boolean result = false;
    if (SystemCommon.getCustomerName().equals("chinaLife") && syncFlag) {
      SynchronizeUsers su = new SynchronizeUsers();
      UserData user = su.getUserDataFromAuditEmployeePO(po);
      if ("1".equals(flag)) {
        System.out.println("调用人寿人员同步--人员信息新增（审计）");
        result = su.addUser(user);
      } else if ("2".equals(flag)) {
        System.out.println("调用人寿人员同步--人员信息修改（审计）");
        result = su.updateUser(user);
      } else {
        System.out.println("调用人寿人员同步--人员信息删除（审计）");
        result = su.deleteUser(user);
      } 
    } else {
      result = true;
    } 
    return result;
  }
  
  private UserData getUserDataFromEmployeeVO(EmployeeVO vo) {
    UserData user = new UserData();
    user.setUserCode(vo.getUserAccounts());
    user.setUserName(vo.getEmpName());
    user.setPassword(vo.getUserPassword());
    user.seteMail(vo.getEmpEmail());
    user.setDuty(vo.getEmpDuty());
    user.setIdCardNo(vo.getEmpIdCard());
    return user;
  }
  
  private UserData getUserDataFromAuditEmployeePO(AuditEmployeePO po) {
    UserData user = new UserData();
    return user;
  }
  
  private static boolean getSyncFlag() {
    boolean flag = false;
    if (SystemCommon.getCustomerName().equals("chinaLife"))
      try {
        String path = System.getProperty("user.dir");
        String filePath = String.valueOf(path) + "/jsconfig/rsconfig.xml";
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(filePath);
        Element syncFlagE = doc.getRootElement().getChild("syncFlag");
        if (syncFlagE != null)
          flag = syncFlagE.getAttributeValue("use").equals("1"); 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return flag;
  }
}
