package com.js.oa.hr.officemanager.bean;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditEmployeePO;
import com.js.oa.audit.po.AuditLog;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.organizationmanager.SyncRTXVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import com.js.util.util.MD5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class EmployeeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Integer add(EmployeeVO employeeVO, String orgId) throws Exception {
    int result = 2;
    begin();
    try {
      String userAccount = employeeVO.getUserAccounts();
      if (userAccount != null && !"".equals(userAccount)) {
        System.out.println("用户账号检测！");
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
              employeeVO.getUserAccounts() + "'").next().toString());
        if (result > 0) {
          result = 1;
          return new Integer(result);
        } 
        System.out.println("密码开始加密！");
        employeeVO.setUserPassword((new MD5()).getMD5Code(employeeVO.getUserPassword()));
        System.out.println("加密后的密码为:" + employeeVO.getUserPassword());
        employeeVO.setUserIsFormalUser(Integer.valueOf("1"));
        if (employeeVO.getUserIsActive() == 1) {
          SyncRTXVO rtxVO = new SyncRTXVO();
          rtxVO.setUserAccount(userAccount);
          rtxVO.setDataOpr(Byte.valueOf("0"));
          rtxVO.setDataType(Byte.valueOf("1"));
          this.session.save(rtxVO);
        } 
      } 
      if (employeeVO.getKeyValidate() == null)
        employeeVO.setKeyValidate("0"); 
      OrganizationVO org = (OrganizationVO)this.session.load(OrganizationVO.class, new Long(orgId));
      Set<OrganizationVO> set = new HashSet();
      set.add(org);
      employeeVO.setOrganizations(set);
    } catch (Exception e) {
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
  
  public Boolean forbid(String id) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
          new Long(id));
      Byte userIsActive = new Byte("0");
      employeeVO.setUserIsActive(userIsActive.byteValue());
      SyncRTXVO rtxVO = new SyncRTXVO();
      rtxVO.setUserAccount(employeeVO.getUserAccounts());
      rtxVO.setDataOpr(Byte.valueOf("2"));
      rtxVO.setDataType(Byte.valueOf("1"));
      this.session.save(rtxVO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean forbid(String id, String[] log) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
          new Long(id));
      Long logId = Long.valueOf(saveLog(log, "禁用用户管理“" + employeeVO.getEmpName() + "”"));
      AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
      Byte userIsActive = new Byte("0");
      po.setUserIsActive(userIsActive.byteValue());
      po.setOperate("disable");
      po.setOrgId(StaticParam.getOrgIdByEmpId((new StringBuilder(String.valueOf(po.getEmpId()))).toString()));
      po.setLogId(logId.longValue());
      this.session.save(po);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean regain(String id) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
          new Long(id));
      Byte userIsActive = new Byte("1");
      employeeVO.setUserIsActive(userIsActive.byteValue());
      SyncRTXVO rtxVO = new SyncRTXVO();
      rtxVO.setUserAccount(employeeVO.getUserAccounts());
      rtxVO.setDataOpr(Byte.valueOf("0"));
      rtxVO.setDataType(Byte.valueOf("1"));
      this.session.save(rtxVO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean regain(String id, String[] log) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
          new Long(id));
      Long logId = Long.valueOf(saveLog(log, "恢复用户管理“" + employeeVO.getEmpName() + "”"));
      AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
      Byte userIsActive = new Byte("1");
      po.setUserIsActive(userIsActive.byteValue());
      po.setOperate("recover");
      po.setLogId(logId.longValue());
      po.setOrgId(StaticParam.getOrgIdByEmpId((new StringBuilder(String.valueOf(po.getEmpId()))).toString()));
      this.session.save(po);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean del(String[] id) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      for (int i = 0; i < id.length; i++) {
        EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(id[i]));
        Byte userIsDeleted = new Byte("1");
        employeeVO.setUserIsDeleted(userIsDeleted.byteValue());
      } 
      result = Boolean.TRUE;
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean del(String[] id, String[] log) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      for (int i = 0; i < id.length; i++) {
        EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(id[i]));
        Long logId = Long.valueOf(saveLog(log, "删除用户管理“" + employeeVO.getEmpName() + "”"));
        AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
        Byte userIsDeleted = new Byte("1");
        po.setUserIsDeleted(userIsDeleted.byteValue());
        po.setOperate("delete");
        po.setOrgId(StaticParam.getOrgIdByEmpId((new StringBuilder(String.valueOf(po.getEmpId()))).toString()));
        po.setLogId(logId.longValue());
        this.session.save(po);
      } 
      result = Boolean.TRUE;
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean rehis(String id) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(id));
      int result1 = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
            employeeVO.getUserAccounts() + "' and emp.empId<>" + 
            id + " and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
      if (result1 > 0) {
        result = Boolean.FALSE;
      } else {
        Byte userIsDeleted = new Byte("0");
        employeeVO.setUserIsDeleted(userIsDeleted.byteValue());
        employeeVO.setUserIsActive((new Byte("1")).byteValue());
        this.session.flush();
        result = Boolean.TRUE;
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean rehis(String id, String[] log) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(id));
      Long logId = Long.valueOf(saveLog(log, "恢复用户管理“" + employeeVO.getEmpName() + "”"));
      AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
      int result1 = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
            employeeVO.getUserAccounts() + "' and emp.empId<>" + 
            id + " and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
      if (result1 > 0) {
        result = Boolean.FALSE;
      } else {
        Byte userIsDeleted = new Byte("0");
        po.setUserIsDeleted(userIsDeleted.byteValue());
        po.setUserIsActive((new Byte("1")).byteValue());
        po.setOperate("rehis");
        po.setOrgId(StaticParam.getOrgIdByEmpId(id));
        po.setLogId(logId.longValue());
        this.session.save(po);
        this.session.flush();
        result = Boolean.TRUE;
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List listDuty(String domainId) throws Exception {
    return listDuty(domainId, "");
  }
  
  public List listDuty(String domainId, String corpId) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String hql = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hql = "(po.corpId=0 or po.corpId=" + corpId + ") and "; 
      Query query = this.session.createQuery("select po.id,po.dutyName from com.js.oa.hr.officemanager.po.DutyPO po where " + 
          hql + " po.domainId=" + domainId + " order by po.dutyLevel asc");
      list = query.list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List listStation(String domainId) throws Exception {
    return listStation(domainId, "");
  }
  
  public List listStation(String domainId, String corpId) throws Exception {
    begin();
    List list = new ArrayList();
    String dataType = SystemCommon.getDatabaseType();
    try {
      String hql = "";
      String order = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hql = "(po.corpId=0 or po.corpId=" + corpId + ") and"; 
      if ("mysql".equals(dataType)) {
        order = " order by CONVERT(po.name USING GBK)";
      } else if ("oracle".equals(dataType)) {
        order = " order by NLSSORT(po.name,'NLS_SORT = SCHINESE_PINYIN_M')";
      } 
      Query query = this.session.createQuery("select po.id,po.name from StationPO po where " + hql + " po.domainId=" + domainId + order);
      list = query.list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Integer update(EmployeeVO employeeVO, String orgId, Long empId) throws Exception {
    int result = 2;
    String userAccount = employeeVO.getUserAccounts();
    begin();
    try {
      if (userAccount != null && !"".equals(userAccount)) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
              employeeVO.getUserAccounts() + "' and emp.empId<>" + 
              empId).next().toString());
        if (result > 0) {
          result = 1;
          return Integer.valueOf("1");
        } 
        Object obj = this.session.iterate("select emp.userPassword from com.js.system.vo.usermanager.EmployeeVO emp where  emp.empId=" + empId).next();
        String userPassword = (obj == null) ? "" : obj.toString();
        if (!userPassword.equals(employeeVO.getUserPassword()))
          employeeVO.setUserPassword((new MD5()).getMD5Code(employeeVO.getUserPassword())); 
        SyncRTXVO rtxVO = new SyncRTXVO();
        rtxVO.setUserAccount(userAccount);
        if (employeeVO.getUserIsActive() == 0) {
          rtxVO.setDataOpr(Byte.valueOf("2"));
        } else {
          rtxVO.setDataOpr(Byte.valueOf("0"));
        } 
        rtxVO.setDataType(Byte.valueOf("1"));
        this.session.save(rtxVO);
      } 
      if (employeeVO.getKeyValidate() == null)
        employeeVO.setKeyValidate("0"); 
      employeeVO.setUserIsFormalUser(Integer.valueOf("1"));
      EmployeeVO oldEmp = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(employeeVO.getEmpId()));
      OrganizationVO org = (OrganizationVO)this.session.load(OrganizationVO.class, new Long(orgId));
      Set<OrganizationVO> set = new HashSet();
      set.add(org);
      oldEmp.setOrganizations(set);
      oldEmp.setEmpId(empId.longValue());
      oldEmp.setCertifyNumber(employeeVO.getCertifyNumber());
      oldEmp.setContractAbout(employeeVO.getContractAbout());
      oldEmp.setCurrentPostTitle(employeeVO.getCurrentPostTitle());
      oldEmp.setEmpAddress(employeeVO.getEmpAddress());
      oldEmp.setEmpBirth(employeeVO.getEmpBirth());
      oldEmp.setEmpBloodType(employeeVO.getEmpBloodType());
      oldEmp.setEmpBusinessFax(employeeVO.getEmpBusinessFax());
      oldEmp.setEmpBusinessPhone(employeeVO.getEmpBusinessPhone());
      oldEmp.setEmpCountry(employeeVO.getEmpCountry());
      oldEmp.setEmpCounty(employeeVO.getEmpCounty());
      oldEmp.setEmpDegree(employeeVO.getEmpDegree());
      oldEmp.setEmpDescribe(employeeVO.getEmpDescribe());
      oldEmp.setEmpDuty(employeeVO.getEmpDuty());
      oldEmp.setEmpEducationExperience(employeeVO.getEmpEducationExperience());
      oldEmp.setEmpEmail(employeeVO.getEmpEmail());
      oldEmp.setEmpEmail2(employeeVO.getEmpEmail2());
      oldEmp.setEmpEmail3(employeeVO.getEmpEmail3());
      oldEmp.setEmpEnglishName(employeeVO.getEmpEnglishName());
      oldEmp.setEmpFireDate(employeeVO.getEmpFireDate());
      oldEmp.setEmpGnome(employeeVO.getEmpGnome());
      oldEmp.setEmpHeadImg(employeeVO.getEmpHeadImg());
      oldEmp.setEmpHeight(employeeVO.getEmpHeight());
      oldEmp.setEmpHireDate(employeeVO.getEmpHireDate());
      oldEmp.setEmpIdCard(employeeVO.getEmpIdCard());
      oldEmp.setEmpInterest(employeeVO.getEmpInterest());
      oldEmp.setEmpIsMarriage(employeeVO.getEmpIsMarriage());
      oldEmp.setEmpLeaderId(employeeVO.getEmpLeaderId());
      oldEmp.setEmpLeaderName(employeeVO.getEmpLeaderName());
      oldEmp.setEmpLivingPhoto(employeeVO.getEmpLivingPhoto());
      oldEmp.setEmpMobilePhone(employeeVO.getEmpMobilePhone());
      oldEmp.setEmpName(employeeVO.getEmpName());
      oldEmp.setEmpNation(employeeVO.getEmpNation());
      oldEmp.setEmpNativePlace(employeeVO.getEmpNativePlace());
      oldEmp.setEmpNumber(employeeVO.getEmpNumber());
      oldEmp.setEmpOrgIdString(employeeVO.getEmpOrgIdString());
      oldEmp.setEmpPhone(employeeVO.getEmpPhone());
      oldEmp.setEmpPhoto(employeeVO.getEmpPhoto());
      oldEmp.setEmpPolity(employeeVO.getEmpPolity());
      oldEmp.setEmpPosition(employeeVO.getEmpPosition());
      oldEmp.setEmpPostTitle(employeeVO.getEmpPostTitle());
      oldEmp.setEmpResumeNum(employeeVO.getEmpResumeNum());
      oldEmp.setEmpSex(employeeVO.getEmpSex());
      oldEmp.setEmpSignImg(employeeVO.getEmpSignImg());
      oldEmp.setEmpState(employeeVO.getEmpState());
      oldEmp.setEmpStatus(employeeVO.getEmpStatus());
      oldEmp.setEmpStudyExperience(employeeVO.getEmpStudyExperience());
      oldEmp.setEmpTrainExperience(employeeVO.getEmpTrainExperience());
      oldEmp.setEmpWebAddress(employeeVO.getEmpWebAddress());
      oldEmp.setEmpWeight(employeeVO.getEmpWeight());
      oldEmp.setEmpWorkExperience(employeeVO.getEmpWorkExperience());
      oldEmp.setEmpZipCode(employeeVO.getEmpZipCode());
      oldEmp.setFamilyMember(employeeVO.getFamilyMember());
      oldEmp.setFireReason(employeeVO.getFireReason());
      oldEmp.setInsuranceNumber(employeeVO.getInsuranceNumber());
      oldEmp.setKeySerial(employeeVO.getKeySerial());
      oldEmp.setKeyValidate(employeeVO.getKeyValidate());
      oldEmp.setOtherPostTitle(employeeVO.getOtherPostTitle());
      oldEmp.setPostCompetence(employeeVO.getPostCompetence());
      oldEmp.setPostGainTime(oldEmp.getPostGainTime());
      oldEmp.setPostLevel(employeeVO.getPostLevel());
      oldEmp.setPostTitleSeries(employeeVO.getPostTitleSeries());
      if (employeeVO.getUserAccounts() == null || employeeVO.getUserAccounts().trim().length() == 0) {
        oldEmp.setUserAccounts(null);
      } else {
        oldEmp.setUserAccounts(employeeVO.getUserAccounts());
      } 
      oldEmp.setUserIsActive(employeeVO.getUserIsActive());
      oldEmp.setUserIsDeleted(employeeVO.getUserIsDeleted());
      oldEmp.setUserIsFormalUser(employeeVO.getUserIsFormalUser());
      oldEmp.setUserIsSuper(employeeVO.getUserIsSuper());
      oldEmp.setUserOrderCode(employeeVO.getUserOrderCode());
      oldEmp.setUserPassword(employeeVO.getUserPassword());
      oldEmp.setUserSuperBegin(employeeVO.getUserSuperBegin());
      oldEmp.setUserSuperEnd(employeeVO.getUserSuperEnd());
      oldEmp.setGraduateDate(employeeVO.getGraduateDate());
      oldEmp.setIntoCompanyDate(employeeVO.getIntoCompanyDate());
      oldEmp.setPostGainTime(employeeVO.getPostGainTime());
      oldEmp.setWorkPackStartDate(employeeVO.getWorkPackStartDate());
      oldEmp.setWorkPackEndDate(employeeVO.getWorkPackEndDate());
      oldEmp.setWpContStartDate(employeeVO.getWpContStartDate());
      oldEmp.setWpContEndtDate(employeeVO.getWpContEndtDate());
      oldEmp.setHirePackStartDate(employeeVO.getHirePackStartDate());
      oldEmp.setHirePackEndDate(employeeVO.getHirePackEndDate());
      oldEmp.setFosterPackStartDate(employeeVO.getFosterPackStartDate());
      oldEmp.setFosterPackEndDate(employeeVO.getFosterPackEndDate());
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return new Integer(result);
  }
  
  public List selectSingle(Long empId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po,org.orgId,org.orgName from com.js.system.vo.usermanager.EmployeeVO  po   join  po.organizations org where po.empId = " + empId + " ");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public List listCountry() throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where po.parentId = 0  order by po.id")
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List city(String country) throws Exception {
    List listCity = new ArrayList();
    begin();
    try {
      String countryId = "";
      List<E> listCountry = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where  po.parentId=0 and po.districtName='" + 
          country + "'").list();
      if (listCountry.size() > 0) {
        countryId = listCountry.get(0).toString();
        listCity = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where  po.parentId=" + 
            countryId + 
            " order by po.districtName").list();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return listCity;
  }
  
  public List county(String country, String city) throws Exception {
    begin();
    List listCounty = new ArrayList();
    try {
      String countryId = "";
      List<E> listCountry = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where  po.parentId=0 and po.districtName='" + 
          country + "'").list();
      if (listCountry.size() > 0) {
        countryId = listCountry.get(0).toString();
        List<String> listCity = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where po.districtName='" + city + "' and  po.parentId=" + countryId).list();
        if (listCity.size() > 0)
          listCounty = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where  po.parentId=" + 
              listCity.get(0) + " order by po.districtName").list(); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return listCounty;
  }
  
  public List postTitle(String postTitleSeries) throws Exception {
    List listPostTitle = new ArrayList();
    begin();
    try {
      listPostTitle = this.session.createQuery("select po.postTitle from com.js.oa.hr.officemanager.po.PostTitlePO po where  po.postTitleSeries='" + 
          postTitleSeries + 
          "' order by po.id").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return listPostTitle;
  }
  
  public String batchResume(String[] empId) throws Exception {
    String userAccount = "";
    StringBuffer userIds = new StringBuffer();
    for (int i = 0; i < empId.length; i++) {
      if (!rehis(empId[i]).booleanValue())
        userIds.append(empId[i]).append(","); 
    } 
    if (userIds.length() > 0) {
      userIds.append("-1");
      begin();
      try {
        List list = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + userIds + ")").list();
        userIds = new StringBuffer();
        for (int j = 0; j < list.size(); j++)
          userIds.append(list.get(j)).append(","); 
        userAccount = userIds.toString();
        userAccount = userAccount.substring(0, userAccount.length() - 1);
        this.session.close();
      } catch (Exception ex) {
        this.session.close();
      } 
    } 
    return userAccount;
  }
  
  public String batchResume(String[] empId, String[] log) throws Exception {
    String userAccount = "";
    StringBuffer userIds = new StringBuffer();
    for (int i = 0; i < empId.length; i++) {
      if (!rehis(empId[i], log).booleanValue())
        userIds.append(empId[i]).append(","); 
    } 
    if (userIds.length() > 0) {
      userIds.append("-1");
      begin();
      try {
        List list = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + userIds + ")").list();
        userIds = new StringBuffer();
        for (int j = 0; j < list.size(); j++)
          userIds.append(list.get(j)).append(","); 
        userAccount = userIds.toString();
        userAccount = userAccount.substring(0, userAccount.length() - 1);
        this.session.close();
      } catch (Exception ex) {
        this.session.close();
      } 
    } 
    return userAccount;
  }
  
  public List export(String where) throws Exception {
    List<Object[]> alist = new ArrayList();
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    where = where.replaceAll("domainId", "domain_id");
    try {
      dataSourceBase.begin();
      String sql = "select po.EmpNumber,po.EmpName,po.UserAccounts,po.EmpBusinessPhone,po.PersonalKind,po.EmpBusinessFax,po.EmpNation,po.EmpNativePlace,po.EmpPolity,po.EmpBirth,po.EmpSex,po.EmpStudyExperience,po.EmpDegree,po.EmpCountry,po.GraduateDate,po.EmpFireDate,po.IntoCompanyDate,po.EmpPosition,po.EmpDuty,po.EmpWebAddress,po.EmpZipCode,po.EmpState,po.EndowmentInsurance,po.Medicare,po.AccumulationFund,po.EmpResumeNum,po.EmpIdCard,po.PostTitleSeries,po.PostLevel,po.PostCompetence,po.PostGainTime,po.CertifyNumber,po.CurrentPostTitle,po.EmpHireDate,po.InsuranceNumber,po.OtherPostTitle,po.EmpTrainExperience,po.EmpWorkExperience,po.EmpInterest,po.ContractAbout,po.WorkPackStartDate,po.WorkPackEndDate,po.WpContStartDate,po.WpContEndtDate,po.HirePackStartDate,po.HirePackStartDate,po.FosterPackStartDate,po.FosterPackStartDate,po.FireReason,po.EmpAddress,po.EmpPhone,po.EmpEmail,po.FamilyMember,orgpo.orgNameString,other.cym,other.csd,other.hkszd,other.hyzk,other.jkzk,other.qrz_zgxl,other.qrz_zgxw,other.qrz_byyxx,other.qrz_zy,other.zzjy_zgxl,zzjy_zgxw,zzjy_byyxx,zzjy_zy,other.zyjszc,other.zyzg,other.rxzsj,other.rxjsj,po.section,po.empmobilephone,po.dignity,po.partydate,po.jobstatus from org_employee po join org_organization_user ou on po.emp_id=ou.emp_id join org_organization orgpo on ou.org_id=orgpo.org_id left join org_emp_otherinfo other on po.emp_id = other.emp_id " + 











        
        where;
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        Object[] o = new Object[3];
        EmployeeVO empVO = new EmployeeVO();
        empVO.setEmpNumber(transform(rs.getString(1)));
        empVO.setEmpName(transform(rs.getString(2)));
        empVO.setUserAccounts(transform(rs.getString(3)));
        empVO.setEmpBusinessPhone(transform(rs.getString(4)));
        empVO.setPersonalKind(Long.valueOf(rs.getLong(5)));
        empVO.setEmpBusinessFax(transform(rs.getString(6)));
        empVO.setEmpNation(transform(rs.getString(7)));
        empVO.setEmpNativePlace(transform(rs.getString(8)));
        empVO.setEmpPolity(transform(rs.getString(9)));
        empVO.setEmpBirth(rs.getDate(10));
        empVO.setEmpSex(rs.getByte(11));
        empVO.setEmpStudyExperience(transform(rs.getString(12)));
        empVO.setEmpDegree(transform(rs.getString(13)));
        empVO.setEmpCountry(transform(rs.getString(14)));
        empVO.setGraduateDate(rs.getDate(15));
        empVO.setEmpFireDate(rs.getDate(16));
        empVO.setIntoCompanyDate(rs.getDate(17));
        empVO.setEmpPosition(transform(rs.getString(18)));
        empVO.setEmpDuty(transform(rs.getString(19)));
        empVO.setEmpWebAddress(transform(rs.getString(20)));
        empVO.setEmpZipCode(transform(rs.getString(21)));
        empVO.setEmpState(transform(rs.getString(22)));
        empVO.setEndowmentInsurance(transform(rs.getString(23)));
        empVO.setMedicare(transform(rs.getString(24)));
        empVO.setAccumulationFund(transform(rs.getString(25)));
        empVO.setEmpResumeNum(transform(rs.getString(26)));
        empVO.setEmpIdCard(transform(rs.getString(27)));
        empVO.setPostTitleSeries(transform(rs.getString(28)));
        empVO.setPostLevel(transform(rs.getString(29)));
        empVO.setPostCompetence(transform(rs.getString(30)));
        empVO.setPostGainTime(rs.getDate(31));
        empVO.setCertifyNumber(transform(rs.getString(32)));
        empVO.setCurrentPostTitle(transform(rs.getString(33)));
        empVO.setEmpHireDate(rs.getDate(34));
        empVO.setInsuranceNumber(transform(rs.getString(35)));
        empVO.setOtherPostTitle(transform(rs.getString(36)));
        empVO.setEmpTrainExperience(transform(rs.getString(37)));
        empVO.setEmpWorkExperience(transform(rs.getString(38)));
        empVO.setEmpInterest(transform(rs.getString(39)));
        empVO.setContractAbout(transform(rs.getString(40)));
        empVO.setWorkPackStartDate(rs.getDate(41));
        empVO.setWorkPackEndDate(rs.getDate(42));
        empVO.setWpContStartDate(rs.getDate(43));
        empVO.setWpContEndtDate(rs.getDate(44));
        empVO.setHirePackStartDate(rs.getDate(45));
        empVO.setHirePackStartDate(rs.getDate(46));
        empVO.setFosterPackStartDate(rs.getDate(47));
        empVO.setFosterPackStartDate(rs.getDate(48));
        empVO.setFireReason(transform(rs.getString(49)));
        empVO.setEmpAddress(transform(rs.getString(50)));
        empVO.setEmpPhone(transform(rs.getString(51)));
        empVO.setEmpEmail(transform(rs.getString(52)));
        empVO.setFamilyMember(transform(rs.getString(53)));
        empVO.setSection(transform(rs.getString(72)));
        empVO.setEmpMobilePhone(transform(rs.getString(73)));
        empVO.setDignity(transform(rs.getString(74)));
        empVO.setPartyDate(transform(rs.getString(75)));
        empVO.setJobStatus(rs.getString(76));
        EmployeeOtherInfoVO empOtherInfoVO = new EmployeeOtherInfoVO();
        empOtherInfoVO.setCym(rs.getString(55));
        empOtherInfoVO.setCsd(rs.getString(56));
        empOtherInfoVO.setHkszd(rs.getString(57));
        empOtherInfoVO.setHyzk(rs.getString(58));
        empOtherInfoVO.setJkzk(rs.getString(59));
        empOtherInfoVO.setQrz_zgxl(rs.getString(60));
        empOtherInfoVO.setQrz_zgxw(rs.getString(61));
        empOtherInfoVO.setQrz_byyxx(rs.getString(62));
        empOtherInfoVO.setQrz_zy(rs.getString(63));
        empOtherInfoVO.setZzjy_zgxl(rs.getString(64));
        empOtherInfoVO.setZzjy_zgxw(rs.getString(65));
        empOtherInfoVO.setZzjy_byyxx(rs.getString(66));
        empOtherInfoVO.setZzjy_zy(rs.getString(67));
        empOtherInfoVO.setZyjszc(rs.getString(68));
        empOtherInfoVO.setZyzg(rs.getString(69));
        empOtherInfoVO.setRxzsj(rs.getString(70));
        empOtherInfoVO.setRxjsj(rs.getString(71));
        o[0] = empVO;
        o[1] = transform(rs.getString(54));
        o[2] = empOtherInfoVO;
        alist.add(o);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return alist;
  }
  
  public String transform(Object obj) {
    if (obj == null || obj.toString().equalsIgnoreCase("null") || obj.toString().equals(""))
      return "&nbsp;"; 
    return obj.toString();
  }
  
  public Integer containUsersCount(String orgId) throws Exception {
    begin();
    Connection conn = null;
    Statement st = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      st = conn.createStatement();
      ResultSet rs = st.executeQuery("select count(*) from org_employee ee where ee.userisdeleted=0  and ee.emp_id in(select ou.emp_id from org_organization_user ou where ou.org_id in(select org_id from org_organization where orgidstring like '%$" + orgId + "$%'))");
      rs.next();
      return Integer.valueOf(rs.getString(1));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      st.close();
      conn.close();
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public String judgeAccountById(String userId) throws Exception {
    String repeat = "0";
    begin();
    try {
      String userAccounts = this.session.iterate("select emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=" + userId).next().toString();
      int result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.userAccounts='" + 
            userAccounts + "'").next().toString());
      if (result > 0)
        repeat = "1"; 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return repeat;
  }
  
  public String judgeAccount(String account) throws Exception {
    String repeat = "0";
    begin();
    try {
      int result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.userAccounts='" + 
            account + "'").next().toString());
      if (result > 0)
        repeat = "1"; 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return repeat;
  }
  
  public String setUserAccountAndPass(String userId, String account, String password) throws Exception {
    String result = "0";
    begin();
    try {
      EmployeeVO empVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(userId));
      empVO.setUserAccounts(account);
      empVO.setUserPassword((new MD5()).getMD5Code(password));
      empVO.setUserIsDeleted((new Byte("0")).byteValue());
      empVO.setUserIsActive((new Byte("1")).byteValue());
      this.session.flush();
      this.session.close();
      result = "1";
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return result;
  }
  
  public List export_contract(String where) throws Exception {
    List alist = new ArrayList();
    begin();
    try {
      alist = this.session.createQuery("select p,po.empName,po.empId,orgpo.orgName from com.js.system.vo.usermanager.ContractVO p join p.employeeVO po join po.organizations orgpo " + 
          where).list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List export_edusty(String where) throws Exception {
    List alist = new ArrayList();
    begin();
    try {
      alist = this.session.createQuery("select p,po.empName,po.empId,orgpo.orgName from com.js.system.vo.usermanager.EdustoryVO p join p.employeeVO po join po.organizations orgpo " + 
          where).list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List export_work(String where) throws Exception {
    List alist = new ArrayList();
    begin();
    try {
      alist = this.session.createQuery("select p,po.empName,po.empId,orgpo.orgName from com.js.system.vo.usermanager.WorkVO p join p.employeeVO po join po.organizations orgpo " + 
          where).list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List export_trainhistory(String where) throws Exception {
    List alist = new ArrayList();
    begin();
    try {
      alist = this.session.createQuery("select p,po.empName,po.empId,orgpo.orgName from com.js.system.vo.usermanager.TrainhistoryVO p join p.employeeVO po join po.organizations orgpo " + 
          where).list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List export_competence(String where) throws Exception {
    List alist = new ArrayList();
    begin();
    try {
      alist = this.session.createQuery("select p,po.empName,po.empId,orgpo.orgName from com.js.system.vo.usermanager.CompetenceVO p join p.employeeVO po join po.organizations orgpo " + 
          where).list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public long saveLog(String[] log, String caozuo) throws HibernateException {
    Long logId = Long.valueOf(0L);
    try {
      AuditLog auditLog = new AuditLog();
      auditLog.setSubmitEmpid(Long.valueOf(log[0]));
      auditLog.setSubmitEmpname(log[1]);
      auditLog.setSubmitOrgid(Long.valueOf(log[2]));
      auditLog.setSubmitTime(new Date());
      auditLog.setAuditModule(Long.valueOf(log[3]));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      logId = (Long)this.session.save(auditLog);
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      msRemindBeann.auditMsRemind(Long.valueOf(log[0]).longValue(), log[2], log[1], 
          1, 1, new Date(), "审计提醒：" + log[1] + caozuo, "audit", logId.longValue(), "/jsoa/AuditUserAddAction.do?status=active&action=update&logId=" + logId + "&disabled=disabled&comeflag=tixing");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return logId.longValue();
  }
  
  public String getOrgId(String empId) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    String orgIdString = "";
    try {
      String hqlOrg = "select ORG_ID from ORG_ORGANIZATION_USER  where EMP_ID='" + empId + "' ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        orgIdString = rs.getString(1); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
  
  public String getEmpIdByAccounts(String account) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    String empId = "";
    try {
      String sql = "select emp_id from org_employee  where UserAccounts=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, account);
      ResultSet rs = null;
      rs = pstmt.executeQuery();
      while (rs.next())
        empId = rs.getString(1); 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (pstmt != null)
        try {
          pstmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return empId;
  }
  
  public String getEmp_name(String empId) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    String orgIdString = "";
    try {
      String hqlOrg = "select EMPNAME from org_employee  where EMP_ID='" + empId + "' ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        orgIdString = rs.getString(1); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
  
  public int getPublic(String empId, String id) throws SQLException {
    String empName = getEmp_name(empId).trim();
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    int orgIdString = 0;
    try {
      String hqlOrg = "select count(*) from OA_INFORMATION  where informationtype=1 and INFORMATIONISSUER='" + empName + "' and channel_id=" + id + "  ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        orgIdString = rs.getInt(1); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
  
  public int getCheckPublic(String empId, String id) throws SQLException {
    List<E> getEmpIdInforId = getEmpIdInforId(empId, id);
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    int orgIdString = 0;
    Object[] objEmpIdInforId = (Object[])null;
    Object[] objInforId = (Object[])null;
    List<String> list = new ArrayList();
    try {
      String hqlOrg = "select information_id,channel_id from OA_INFORMATION  where informationtype=1  and channel_id=" + id + "  ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        list.add(rs.getString(1)); 
      for (int j = 0; j < list.size(); j++) {
        for (int i = 0; i < getEmpIdInforId.size(); i++) {
          if (list.get(j) == getEmpIdInforId.get(i))
            orgIdString++; 
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
  
  public List getEmpIdInforId(String empId, String id) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    Object[] obj = (Object[])null;
    List<String> list = new ArrayList();
    try {
      String hqlOrg = "select information_id,emp_id from OA_INFORMATIONBROWSER  where emp_id=" + empId + "  ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        list.add(rs.getString(1)); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public int getWorkflow(String sql) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    int orgIdString = 0;
    try {
      ResultSet rs = null;
      rs = stmt.executeQuery(sql.toString());
      while (rs.next())
        orgIdString = rs.getInt(1); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
  
  public int getKnowlege(String empId, String id) throws SQLException {
    String empName = getEmp_name(empId).trim();
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    int orgIdString = 0;
    try {
      String hqlOrg = "select count(*) from OA_INFORMATION  where informationtype=0 and INFORMATIONISSUER='" + empName + "'   ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        orgIdString = rs.getInt(1); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
  
  public int getCheckKnowlege(String empId, String id) throws SQLException {
    List<E> getEmpIdInforId = getEmpIdInforId(empId, id);
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    stmt = conn.createStatement();
    int orgIdString = 0;
    Object[] objEmpIdInforId = (Object[])null;
    Object[] objInforId = (Object[])null;
    List<String> list = new ArrayList();
    try {
      String hqlOrg = "select information_id,channel_id from OA_INFORMATION  where informationtype=0   ";
      ResultSet rs = null;
      rs = stmt.executeQuery(hqlOrg.toString());
      while (rs.next())
        list.add(rs.getString(1)); 
      for (int j = 0; j < list.size(); j++) {
        for (int i = 0; i < getEmpIdInforId.size(); i++) {
          if (list.get(j) == getEmpIdInforId.get(i))
            orgIdString++; 
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdString;
  }
}
