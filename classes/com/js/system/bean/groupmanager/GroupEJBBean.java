package com.js.system.bean.groupmanager;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.AuditOrgGroup;
import com.js.oa.audit.service.AuditLogBD;
import com.js.oa.audit.service.AuditOrgGroupBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.ConvertIdAndName;
import com.js.system.util.EndowVO;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.Query;

public class GroupEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public GroupVO loadGroup(long groupId) throws Exception {
    begin();
    GroupVO groupVO = null;
    try {
      groupVO = (GroupVO)this.session.get(GroupVO.class, Long.valueOf(groupId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return groupVO;
  }
  
  public Integer add(GroupVO groupVO, String[] id, HttpServletRequest httpServletRequest) throws Exception {
    int result = 0;
    begin();
    try {
      String name = groupVO.getGroupName();
      String groupType = groupVO.getGroupType();
      String userId = groupVO.getCreatedEmp();
      if ("0".equals(groupType)) {
        result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.groupmanager.GroupVO vo where vo.domainId=" + 
            groupVO.getDomainId() + " and vo.groupType=0 and vo.groupName='" + 
            name.trim() + "'").next()).intValue();
      } else {
        result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.groupmanager.GroupVO vo where vo.domainId=" + 
            groupVO.getDomainId() + " and vo.groupType=1 and vo.createdEmp=" + userId + " and vo.groupName='" + 
            name.trim() + "'").next()).intValue();
      } 
      if (result > 0)
        return new Integer(1); 
      Set<Object> userSet = new HashSet();
      for (int i = 0; i < id.length; i++) {
        if (!"".equals(id[i]))
          userSet.add(this.session.load(EmployeeVO.class, new Long(id[i]))); 
      } 
      if ("".equals(groupVO.getGroupOrder()))
        groupVO.setGroupOrder("1000"); 
    } catch (Exception e) {
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
      result = 2;
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public String del(String[] id, HttpServletRequest httpServletRequest) throws Exception {
    String idbuf = "";
    String name = "";
    for (int i = 0; i < id.length; i++)
      idbuf = String.valueOf(idbuf) + id[i] + ","; 
    begin();
    try {
      Iterator<E> it = this.session.createQuery("select aaa.groupName from com.js.system.vo.groupmanager.GroupVO aaa where aaa.groupId in (" + idbuf.substring(0, idbuf.length() - 1) + ")").iterate();
      int j = 0;
      while (it.hasNext()) {
        if (j == 0) {
          name = String.valueOf(name) + it.next().toString();
        } else {
          name = String.valueOf(name) + "," + it.next().toString();
        } 
        j++;
      } 
      GroupVO groupVO = null;
      Long groupId = null;
      String groupType = httpServletRequest.getParameter("groupType");
      if (1 != SystemCommon.getAudit() || "1".equals(groupType)) {
        this.session.delete("from com.js.system.vo.groupmanager.GroupVO aaa where aaa.groupId in (" + idbuf.substring(0, idbuf.length() - 1) + ")");
        this.session.flush();
      } else {
        for (int k = 0; k < id.length; k++) {
          groupVO = loadGroup(Long.valueOf(id[k]).longValue());
          doAuditOrgGroup(groupVO, "delete", Long.valueOf(id[k]), httpServletRequest);
        } 
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {}
    if (this.session != null)
      this.session.close(); 
    return name;
  }
  
  public void delAll() throws Exception {
    begin();
    try {
      this.session.delete("from com.js.system.vo.groupmanager.GroupVO aaa");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List selectSingle(String id) throws Exception {
    begin();
    List<Object[]> list = null;
    try {
      list = this.session.createQuery("select aaa.groupName,aaa.groupUserString,aaa.groupUserNames,aaa.createdOrg,aaa.rangeName,aaa.rangeEmp,aaa.rangeOrg,aaa.rangeGroup,aaa.groupOrder from com.js.system.vo.groupmanager.GroupVO aaa where aaa.groupId = " + id).list();
      if (list != null) {
        Iterator<Object[]> it = this.session.createQuery("select emp.empId,emp.empName from com.js.system.vo.groupmanager.GroupVO groupVO join groupVO.employees emp where groupVO.groupId = " + id).iterate();
        StringBuffer empIds = new StringBuffer(100);
        StringBuffer empNames = new StringBuffer(100);
        if (it != null)
          while (it.hasNext()) {
            Object[] arrayOfObject = it.next();
            empIds.append("$").append(arrayOfObject[0]).append("$");
            empNames.append(arrayOfObject[1]).append(",");
          }  
        Object[] objGroup = list.get(0);
        Object[] obj = new Object[9];
        obj[0] = objGroup[0];
        obj[3] = objGroup[1];
        obj[1] = empIds.toString();
        obj[2] = empNames.toString();
        obj[4] = objGroup[4];
        obj[5] = objGroup[5];
        obj[6] = objGroup[6];
        obj[7] = objGroup[7];
        obj[8] = objGroup[8];
        list = new ArrayList();
        list.add(obj);
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List select() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select aaa.groupId,aaa.groupName from com.js.system.vo.groupmanager.GroupVO aaa order by aaa.groupId desc");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectGroupUser(String id) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select emp.empId,emp.empName,emp.userAccounts,emp.imId,organization.orgIdString from com.js.system.vo.groupmanager.GroupVO groupVO join groupVO.employees emp join emp.organizations organization where groupVO.groupId=" + id + " and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userIsFormalUser=1 and emp.userAccounts is not null order by emp.empDutyLevel,organization.orgIdString,emp.userOrderCode,emp.empName");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectGroupUserEmail(String id) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select emp.empId,emp.empName,emp.empEmail,emp.imId,organization.orgIdString from com.js.system.vo.groupmanager.GroupVO groupVO join groupVO.employees emp join emp.organizations organization where groupVO.groupId=" + id + " and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userIsFormalUser=1 and emp.userAccounts is not null and emp.empEmail <>'' and emp.empEmail is not null order by emp.empDutyLevel,organization.orgIdString,emp.userOrderCode,emp.empName");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Integer update(String groupId, String groupName, String groupUserString, String[] userId, String groupUserNames, String createdOrg, String rangeName, String rangeEmp, String rangeOrg, String rangeGroup, String groupType, String groupOrder, HttpServletRequest httpServletRequest) throws Exception {
    int result = 0;
    begin();
    try {
      GroupVO groupVO = (GroupVO)this.session.load(GroupVO.class, new Long(groupId));
      String name = groupVO.getGroupName();
      if (!name.equals(groupName)) {
        result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.groupmanager.GroupVO vo where vo.domainId=" + groupVO.getDomainId() + " and vo.groupName='" + groupName.trim() + "' and vo.groupId<>" + groupVO.getGroupId()).next()).intValue();
        if (result > 0)
          return new Integer(1); 
      } 
      Set<Object> userSet = new HashSet();
      for (int i = 0; i < userId.length; i++) {
        if (!"".equals(userId[i]))
          userSet.add(this.session.load(EmployeeVO.class, new Long(userId[i]))); 
      } 
      groupVO.setEmployees(userSet);
      groupVO.setGroupName(groupName);
      groupVO.setGroupUserNames(groupUserNames);
      groupVO.setGroupUserString(groupUserString);
      groupVO.setCreatedOrg(createdOrg);
      groupVO.setRangeName(rangeName);
      groupVO.setRangeEmp(rangeEmp);
      groupVO.setRangeOrg(rangeOrg);
      groupVO.setRangeGroup(rangeGroup);
      groupVO.setGroupType(groupType);
    } catch (Exception e) {
      result = 2;
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public void doAuditOrgGroup(GroupVO groupVO, String opreate, Long groupId, HttpServletRequest httpServletRequest) {
    try {
      HttpSession httpsession = httpServletRequest.getSession(true);
      UserBD userBD = new UserBD();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      AuditLog auditLog = new AuditLog();
      auditLog.setSubmitEmpid(Long.valueOf((String)httpsession.getAttribute("userId")));
      EmployeeVO employee = userBD.getEmpByid(Long.valueOf((String)httpsession.getAttribute("userId")));
      if (employee != null)
        auditLog.setSubmitEmpname(employee.getEmpName()); 
      auditLog.setSubmitOrgid(Long.valueOf(httpsession.getAttribute("orgId").toString()));
      Date ts = Timestamp.valueOf(format.format(new Date()));
      auditLog.setSubmitTime(ts);
      auditLog.setAuditModule(new Long(2L));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditOrgGroupBD auditOrgGroupBD = new AuditOrgGroupBD();
      AuditOrgGroup auditOrgGroup = new AuditOrgGroup();
      auditOrgGroup.setGroupId(groupId);
      auditOrgGroup.setGroupName(groupVO.getGroupName());
      auditOrgGroup.setGroupdescription(groupVO.getGroupDescription());
      auditOrgGroup.setCreatedorg(Long.valueOf(groupVO.getCreatedOrg()));
      auditOrgGroup.setCreatedemp(Long.valueOf(groupVO.getCreatedEmp()));
      auditOrgGroup.setGroupUserNames(groupVO.getGroupUserNames());
      auditOrgGroup.setGroupUserString(groupVO.getGroupUserString());
      auditOrgGroup.setRangename(groupVO.getRangeName());
      auditOrgGroup.setRangeemp(groupVO.getRangeEmp());
      auditOrgGroup.setRangeorg(groupVO.getRangeOrg());
      auditOrgGroup.setRangegroup(groupVO.getRangeGroup());
      auditOrgGroup.setDomainId(Long.valueOf(groupVO.getDomainId()));
      auditOrgGroup.setGroupType(Long.valueOf(groupVO.getGroupType()));
      auditOrgGroup.setGroupOrder(groupVO.getGroupOrder());
      auditOrgGroup.setAuditLogId(auditLogId);
      auditOrgGroup.setOperationType(opreate);
      Long auditIpId = auditOrgGroupBD.saveAuditOrgGroup(auditOrgGroup);
      if ("insert".equals(opreate))
        opreate = "新增"; 
      if ("update".equals(opreate))
        opreate = "修改"; 
      if ("delete".equals(opreate))
        opreate = "删除"; 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String userName = (String)httpsession.getAttribute("userName");
      msRemindBeann.auditMsRemind(Long.valueOf((String)httpsession.getAttribute("userId")).longValue(), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          1, 1, new Date(), "审计提醒：" + userName + opreate + "群组管理\"" + groupVO.getGroupName() + "\"", "audit", auditLogId.longValue(), "AuditOrgGroupAction.do?action=forshenji&id=" + auditLogId + "&flag=fromRemind");
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List selectPersonUser(String id) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select personPO.id,personPO.linkManName from com.js.oa.personalwork.person.po.PersonPO personPO join personPO.linkManClass personClassPO where personClassPO.id=" + id);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List checkGroupByName(String name, String id) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery(" from GroupVO vo  where vo.createdEmp=" + id + " and vo.groupName='" + name + "' and vo.groupType=1");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Integer saveAsGroup(String name, String id, String groupName, String domainId, String orgId, String empId) throws Exception {
    begin();
    Integer i = Integer.valueOf(0);
    try {
      GroupVO vo = new GroupVO();
      vo.setGroupName(groupName);
      vo.setCreatedOrg(orgId);
      vo.setCreatedEmp(empId);
      vo.setGroupUserNames(name);
      vo.setGroupUserString(id);
      vo.setDomainId(domainId);
      vo.setGroupOrder("1000");
      vo.setGroupType("1");
      vo.setRangeEmp("");
      vo.setRangeGroup("");
      vo.setRangeName("");
      vo.setRangeOrg("");
      Set<Object> userSet = new HashSet();
      ConvertIdAndName cIdAndName = new ConvertIdAndName();
      EndowVO endowVO = cIdAndName.splitId(id);
      String strId = endowVO.getEmpIdArray();
      String[] ids = strId.split(",");
      for (int j = 0; j < ids.length; j++) {
        if (!"".equals(ids[j]))
          userSet.add(this.session.load(EmployeeVO.class, new Long(ids[j]))); 
      } 
      vo.setEmployees(userSet);
      if (this.session.save(vo) != null)
        i = Integer.valueOf(1); 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return i;
  }
  
  public List searchByUserid(long userId) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
}
