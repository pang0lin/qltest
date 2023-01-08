package com.js.oa.hr.personnelmanager.bean;

import com.js.ldap.OAToAD;
import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditEmployeePO;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.userdb.util.RS;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.organizationmanager.SyncRTXVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import com.js.util.util.MD5;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.apache.commons.beanutils.BeanUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class NewEmployeeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List selectSingle(Long empId) throws Exception {
    List<EmployeeOtherInfoVO> list = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po,org.orgId,org.orgName from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org where po.empId = " + 
          
          empId + " ");
      list = query.list();
      List<EmployeeOtherInfoVO> list2 = new ArrayList();
      query = this.session.createQuery("select po from com.js.system.vo.usermanager.EmployeeOtherInfoVO po where po.empId=" + empId);
      list2 = query.list();
      if (list2 == null || list2.size() == 0) {
        list.add(new EmployeeOtherInfoVO());
      } else {
        list.add(list2.get(0));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public Integer add(EmployeeVO employeeVO, EmployeeOtherInfoVO employeeOtherInfoVO, String orgId) throws Exception {
    int result = 2;
    begin();
    try {
      if ("0".equals(SystemCommon.getLicType())) {
        int regUserNum = 0, buyUserNum = 0;
        Iterator tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts <> '') and user.userIsFormalUser=1 and user.domainId=" + 
            employeeVO.getDomainId());
        if (tmpIter.hasNext()) {
          Object obj = tmpIter.next();
          regUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
        } 
        tmpIter = this.session.iterate("select a.userNum from com.js.system.vo.organizationmanager.DomainVO a where a.id=" + 
            employeeVO.getDomainId() + 
            " and a.inUse=1");
        if (tmpIter.hasNext()) {
          Object obj = tmpIter.next();
          buyUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
        } 
        if (regUserNum >= buyUserNum) {
          this.session.close();
          return Integer.valueOf("-100");
        } 
      } 
      String userAccount = employeeVO.getUserAccounts();
      if (userAccount != null && !"".equals(userAccount)) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
              employeeVO.getUserAccounts() + "' and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
        if (result > 0) {
          result = 1;
          return new Integer(result);
        } 
        employeeVO.setUserPassword((new MD5()).getMD5Code(employeeVO
              .getUserPassword()));
        employeeVO.setUserIsFormalUser(Integer.valueOf("1"));
        if (employeeVO.getUserIsActive() == 1) {
          SyncRTXVO rtxVO = new SyncRTXVO();
          rtxVO.setUserAccount(userAccount);
          rtxVO.setDataOpr(Byte.valueOf("0"));
          rtxVO.setDataType(Byte.valueOf("1"));
          this.session.save(rtxVO);
        } 
      } 
      if (!"".equals(employeeVO.getEmpNumber()) && employeeVO.getEmpNumber() != null && !"null".equals(employeeVO.getEmpNumber())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.empNumber='" + 
              employeeVO.getEmpNumber() + "' and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
        if (result > 0) {
          result = 3;
          return new Integer(result);
        } 
      } 
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + 
            employeeVO.getDomainId() + " and emp.userSimpleName='" + 
            employeeVO.getUserSimpleName() + 
            "' and emp.userSimpleName is not null and emp.domainId=" + employeeVO.getDomainId()).next().toString());
      if (result > 0) {
        this.session.close();
        result = 4;
        return new Integer(4);
      } 
      if (employeeVO.getKeyValidate() == null)
        employeeVO.setKeyValidate("0"); 
      OrganizationVO org = (OrganizationVO)this.session.load(OrganizationVO.class, 
          new Long(orgId));
      Set<OrganizationVO> set = new HashSet();
      set.add(org);
      employeeVO.setOrganizations(set);
      employeeVO.setEmpDutyLevel(getDutyLevel(employeeVO.getEmpDuty(), employeeVO.getDomainId()));
      Long id = (Long)this.session.save(employeeVO);
      employeeOtherInfoVO.setEmpId(id.longValue());
      this.session.save(employeeOtherInfoVO);
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
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public List postTitle(String postTitleSeries) throws Exception {
    List listPostTitle = new ArrayList();
    begin();
    try {
      listPostTitle = this.session.createQuery(
          "select po.postTitle from com.js.oa.hr.officemanager.po.PostTitlePO po where  po.postTitleSeries='" + 
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
  
  public Integer update(EmployeeVO employeeVO, EmployeeOtherInfoVO employeeOtherInfoVO, String orgId, Long empId) throws Exception {
    int result = 2;
    String userAccount = employeeVO.getUserAccounts();
    begin();
    try {
      String userPassword_AD = employeeVO.getUserPassword();
      if (userAccount != null && !"".equals(userAccount)) {
        if (!"".equals(employeeVO.getUserAccounts())) {
          result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
                employeeVO.getUserAccounts() + "' and emp.empId<>" + 
                empId + " and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
          if (result > 0) {
            result = 1;
            return Integer.valueOf("1");
          } 
        } 
        if (!"".equals(employeeVO.getEmpNumber()) && employeeVO.getEmpNumber() != null && !"null".equals(employeeVO.getEmpNumber())) {
          result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.empNumber='" + 
                employeeVO.getEmpNumber() + "' and emp.empId<>" + 
                empId + " and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
          if (result > 0) {
            result = 3;
            return Integer.valueOf("3");
          } 
        } 
        if (!"".equals(employeeVO.getUserSimpleName())) {
          result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + 
                employeeVO.getDomainId() + " and emp.userSimpleName='" + 
                employeeVO.getUserSimpleName() + 
                "' and  emp.empId<>" + 
                empId + " and emp.domainId=" + employeeVO.getDomainId()).next().toString());
          if (result > 0) {
            this.session.close();
            result = 4;
            return new Integer(4);
          } 
        } 
        if (!"".equals(employeeVO.getUserPassword()) && 
          !"haier".equals(SystemCommon.getCustomerName()))
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
      EmployeeVO oldEmp = (EmployeeVO)this.session.load(EmployeeVO.class, 
          empId);
      EmployeeOtherInfoVO oldOtherInfoVO = new EmployeeOtherInfoVO();
      List<EmployeeOtherInfoVO> list = this.session.createQuery("from com.js.system.vo.usermanager.EmployeeOtherInfoVO po where  po.empId =" + oldEmp.getEmpId()).list();
      if (list.size() == 1)
        oldOtherInfoVO = list.get(0); 
      long id = oldOtherInfoVO.getId();
      BeanUtils.copyProperties(oldOtherInfoVO, employeeOtherInfoVO);
      oldOtherInfoVO.setId(id);
      oldOtherInfoVO.setEmpId(oldEmp.getEmpId());
      OrganizationVO org = (OrganizationVO)this.session.load(OrganizationVO.class, 
          new Long(orgId));
      Set<OrganizationVO> set = new HashSet();
      set.add(org);
      oldEmp.setOrganizations(set);
      oldEmp.setCertifyNumber(employeeVO.getCertifyNumber());
      oldEmp.setContractAbout(employeeVO.getContractAbout());
      oldEmp.setCurrentPostTitle(employeeVO.getCurrentPostTitle());
      oldEmp.setEmpAddress(employeeVO.getEmpAddress());
      oldEmp.setEmpBirth(employeeVO.getEmpBirth());
      oldEmp.setEmpBloodType(employeeVO.getEmpBloodType());
      oldEmp.setEmpBusinessFax(employeeVO.getEmpBusinessFax());
      oldEmp.setEmpBusinessPhone(employeeVO.getEmpBusinessPhone());
      oldEmp.setEmpDegree(employeeVO.getEmpDegree());
      oldEmp.setEmpDescribe(employeeVO.getEmpDescribe());
      oldEmp.setEmpDuty(employeeVO.getEmpDuty());
      oldEmp.setEmpEducationExperience(employeeVO
          .getEmpEducationExperience());
      oldEmp.setEmpEmail(employeeVO.getEmpEmail());
      oldEmp.setEmpEnglishName(employeeVO.getEmpEnglishName());
      oldEmp.setDomainId(employeeVO.getDomainId());
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
      oldEmp.setEmpWeight(employeeVO.getEmpWeight());
      oldEmp.setEmpWorkExperience(employeeVO.getEmpWorkExperience());
      oldEmp.setEmpZipCode(employeeVO.getEmpZipCode());
      oldEmp.setFamilyMember(employeeVO.getFamilyMember());
      oldEmp.setFireReason(employeeVO.getFireReason());
      oldEmp.setInsuranceNumber(employeeVO.getInsuranceNumber());
      oldEmp.setOtherPostTitle(employeeVO.getOtherPostTitle());
      oldEmp.setPostCompetence(employeeVO.getPostCompetence());
      oldEmp.setPostGainTime(oldEmp.getPostGainTime());
      oldEmp.setPostLevel(employeeVO.getPostLevel());
      oldEmp.setPostTitleSeries(employeeVO.getPostTitleSeries());
      oldEmp.setUserIsSuper(employeeVO.getUserIsSuper());
      oldEmp.setUserSuperBegin(employeeVO.getUserSuperBegin());
      oldEmp.setUserSuperEnd(employeeVO.getUserSuperEnd());
      if (!"".equals(employeeVO.getUserPassword()))
        oldEmp.setUserPassword(employeeVO.getUserPassword()); 
      if (employeeVO.getUserAccounts() == null || employeeVO.getUserAccounts().trim().length() == 0) {
        oldEmp.setUserAccounts(null);
      } else {
        oldEmp.setUserAccounts(employeeVO.getUserAccounts());
      } 
      oldEmp.setUserSimpleName(employeeVO.getUserSimpleName());
      oldEmp.setEndowmentInsurance(employeeVO.getEndowmentInsurance());
      oldEmp.setAccumulationFund(employeeVO.getAccumulationFund());
      oldEmp.setGraduateDate(employeeVO.getGraduateDate());
      oldEmp.setIntoCompanyDate(employeeVO.getIntoCompanyDate());
      oldEmp.setDignity(employeeVO.getDignity());
      oldEmp.setSpeciality(employeeVO.getSpeciality());
      oldEmp.setPartyDate(employeeVO.getPartyDate());
      oldEmp.setJobStatus(employeeVO.getJobStatus());
      oldEmp.setSection(employeeVO.getSection());
      oldEmp.setEmpBusinessPhone(employeeVO.getEmpBusinessPhone());
      oldEmp.setSidelineOrg(employeeVO.getSidelineOrg());
      oldEmp.setSidelineOrgName(employeeVO.getSidelineOrgName());
      oldEmp.setWorkAddress(employeeVO.getWorkAddress());
      oldEmp.setUserIsActive(Byte.parseByte("1"));
      if ("haier".equals(SystemCommon.getCustomerName()))
        oldEmp.setUserIsActive(employeeVO.getUserIsActive()); 
      oldEmp.setUserIsFormalUser(new Integer(1));
      oldEmp.setEmpDutyLevel(getDutyLevel(employeeVO.getEmpDuty(), oldEmp.getDomainId()));
      oldEmp.setUserIsDeleted(employeeVO.getUserIsDeleted());
      oldEmp.setSerial(employeeVO.getSerial());
      oldEmp.setSerialPwd(employeeVO.getSerialPwd());
      oldEmp.setZhuanzhengDate(employeeVO.getZhuanzhengDate());
      oldEmp.setPersonalKind(employeeVO.getPersonalKind());
      oldEmp.setEmpFireType(employeeVO.getEmpFireType());
      oldEmp.setLizhiDate(employeeVO.getLizhiDate());
      oldEmp.setHujiAddress(employeeVO.getHujiAddress());
      oldEmp.setIsdimissionprove(employeeVO.getIsdimissionprove());
      oldEmp.setSpeciality1(employeeVO.getSpeciality1());
      oldEmp.setSpeciality2(employeeVO.getSpeciality2());
      oldEmp.setLanguage1(employeeVO.getLanguage1());
      oldEmp.setLanguage2(employeeVO.getLanguage2());
      oldEmp.setLanguage3(employeeVO.getLanguage3());
      oldEmp.setLanglevel1(employeeVO.getLanglevel1());
      oldEmp.setLanglevel2(employeeVO.getLanglevel2());
      oldEmp.setLanglevel3(employeeVO.getLanglevel3());
      oldEmp.setZhicheng(employeeVO.getZhicheng());
      oldEmp.setEmpPositionId(employeeVO.getEmpPositionId());
      oldEmp.setUserOrderCode(employeeVO.getUserOrderCode());
      oldEmp.setLastupdate(employeeVO.getLastupdate());
      oldEmp.setWm_code(employeeVO.getWm_code());
      this.session.update(oldEmp);
      if (oldOtherInfoVO.getId() == 0L) {
        this.session.save(oldOtherInfoVO);
      } else {
        this.session.update(oldOtherInfoVO);
      } 
      if (userPassword_AD != null && !userPassword_AD.equals("")) {
        String path = System.getProperty("user.dir");
        String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        int useLDAP = 0;
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
          doc = builder.build(filePath);
        } catch (JDOMException e1) {
          e1.printStackTrace();
        } catch (IOException e1) {
          e1.printStackTrace();
        } 
        Element ldapConfig = doc.getRootElement().getChild("LdapConfig");
        if (ldapConfig != null) {
          useLDAP = Integer.parseInt(ldapConfig.getAttribute("use").getValue());
          if (useLDAP == 1) {
            OAToAD oaToAd = new OAToAD();
            int isAccuess = oaToAd.updatePassword(userPassword_AD, employeeVO.getUserAccounts());
            if (isAccuess == 1)
              return Integer.valueOf(11); 
          } 
        } 
      } 
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
  
  public Integer update(EmployeeVO employeeVO, EmployeeOtherInfoVO employeeOtherInfoVO, String orgId, Long empId, String[] log) throws Exception {
    int result = 2;
    String userAccount = employeeVO.getUserAccounts();
    begin();
    try {
      if (userAccount != null && !"".equals(userAccount)) {
        if (!"".equals(employeeVO.getUserAccounts())) {
          result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts='" + 
                employeeVO.getUserAccounts() + "' and emp.empId<>" + 
                empId + " and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
          if (result > 0) {
            result = 1;
            return Integer.valueOf("1");
          } 
        } 
        if (!"".equals(employeeVO.getEmpNumber()) && employeeVO.getEmpNumber() != null && !"null".equals(employeeVO.getEmpNumber())) {
          result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.empNumber='" + 
                employeeVO.getEmpNumber() + "' and emp.empId<>" + 
                empId + " and emp.userIsDeleted<>1 and emp.domainId=" + employeeVO.getDomainId()).next().toString());
          if (result > 0) {
            result = 3;
            return Integer.valueOf("3");
          } 
        } 
        if (!"".equals(employeeVO.getUserSimpleName())) {
          result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + 
                employeeVO.getDomainId() + " and emp.userSimpleName='" + 
                employeeVO.getUserSimpleName() + 
                "' and  emp.empId<>" + 
                empId + " and emp.domainId=" + employeeVO.getDomainId()).next().toString());
          if (result > 0) {
            this.session.close();
            result = 4;
            return new Integer(4);
          } 
        } 
      } 
      Long logId = Long.valueOf(saveLog(log, "修改用户管理“" + employeeVO.getEmpName() + "”"));
      if (employeeVO.getKeyValidate() == null)
        employeeVO.setKeyValidate("0"); 
      EmployeeVO oldEmp = (EmployeeVO)this.session.load(EmployeeVO.class, 
          new Long(employeeVO.getEmpId()));
      AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(oldEmp, AuditEmployeePO.class);
      po.setOrgId(orgId);
      po.setEmpId(empId.longValue());
      po.setCertifyNumber(employeeVO.getCertifyNumber());
      po.setContractAbout(employeeVO.getContractAbout());
      po.setCurrentPostTitle(employeeVO.getCurrentPostTitle());
      po.setEmpAddress(employeeVO.getEmpAddress());
      po.setEmpBirth(employeeVO.getEmpBirth());
      po.setEmpBloodType(employeeVO.getEmpBloodType());
      po.setEmpBusinessFax(employeeVO.getEmpBusinessFax());
      po.setEmpBusinessPhone(employeeVO.getEmpBusinessPhone());
      po.setEmpDegree(employeeVO.getEmpDegree());
      po.setEmpDescribe(employeeVO.getEmpDescribe());
      po.setEmpDuty(employeeVO.getEmpDuty());
      po.setEmpEducationExperience(employeeVO
          .getEmpEducationExperience());
      po.setEmpEmail(employeeVO.getEmpEmail());
      po.setEmpFireDate(employeeVO.getEmpFireDate());
      po.setEmpGnome(employeeVO.getEmpGnome());
      po.setEmpHeadImg(employeeVO.getEmpHeadImg());
      po.setEmpHeight(employeeVO.getEmpHeight());
      po.setEmpHireDate(employeeVO.getEmpHireDate());
      po.setEmpIdCard(employeeVO.getEmpIdCard());
      po.setEmpInterest(employeeVO.getEmpInterest());
      po.setEmpIsMarriage(employeeVO.getEmpIsMarriage());
      po.setEmpLeaderId(employeeVO.getEmpLeaderId());
      po.setEmpLeaderName(employeeVO.getEmpLeaderName());
      po.setEmpLivingPhoto(employeeVO.getEmpLivingPhoto());
      po.setEmpMobilePhone(employeeVO.getEmpMobilePhone());
      po.setEmpName(employeeVO.getEmpName());
      po.setEmpNation(employeeVO.getEmpNation());
      po.setEmpNativePlace(employeeVO.getEmpNativePlace());
      po.setEmpNumber(employeeVO.getEmpNumber());
      po.setEmpOrgIdString(employeeVO.getEmpOrgIdString());
      po.setEmpPhone(employeeVO.getEmpPhone());
      po.setEmpPhoto(employeeVO.getEmpPhoto());
      po.setEmpPolity(employeeVO.getEmpPolity());
      po.setEmpPosition(employeeVO.getEmpPosition());
      po.setEmpPostTitle(employeeVO.getEmpPostTitle());
      po.setEmpResumeNum(employeeVO.getEmpResumeNum());
      po.setEmpSex(employeeVO.getEmpSex());
      po.setEmpSignImg(employeeVO.getEmpSignImg());
      po.setEmpStatus(employeeVO.getEmpStatus());
      po.setEmpStudyExperience(employeeVO.getEmpStudyExperience());
      po.setEmpTrainExperience(employeeVO.getEmpTrainExperience());
      po.setEmpWeight(employeeVO.getEmpWeight());
      po.setEmpWorkExperience(employeeVO.getEmpWorkExperience());
      po.setEmpZipCode(employeeVO.getEmpZipCode());
      po.setFamilyMember(employeeVO.getFamilyMember());
      po.setFireReason(employeeVO.getFireReason());
      po.setInsuranceNumber(employeeVO.getInsuranceNumber());
      po.setOtherPostTitle(employeeVO.getOtherPostTitle());
      po.setPostCompetence(employeeVO.getPostCompetence());
      po.setPostGainTime(po.getPostGainTime());
      po.setPostLevel(employeeVO.getPostLevel());
      po.setPostTitleSeries(employeeVO.getPostTitleSeries());
      po.setUserIsSuper(employeeVO.getUserIsSuper());
      po.setUserSuperBegin(employeeVO.getUserSuperBegin());
      po.setUserSuperEnd(employeeVO.getUserSuperEnd());
      if (!"".equals(employeeVO.getUserPassword()))
        po.setUserPassword(employeeVO.getUserPassword()); 
      if (employeeVO.getUserAccounts() == null || employeeVO.getUserAccounts().trim().length() == 0) {
        po.setUserAccounts(null);
      } else {
        po.setUserAccounts(employeeVO.getUserAccounts());
      } 
      po.setUserSimpleName(employeeVO.getUserSimpleName());
      po.setEndowmentInsurance(employeeVO.getEndowmentInsurance());
      po.setAccumulationFund(employeeVO.getAccumulationFund());
      po.setGraduateDate(employeeVO.getGraduateDate());
      po.setIntoCompanyDate(employeeVO.getIntoCompanyDate());
      po.setDignity(employeeVO.getDignity());
      po.setSpeciality(employeeVO.getSpeciality());
      po.setPartyDate(employeeVO.getPartyDate());
      po.setJobStatus(employeeVO.getJobStatus());
      po.setSection(employeeVO.getSection());
      po.setEmpBusinessPhone(employeeVO.getEmpBusinessPhone());
      po.setSidelineOrg(employeeVO.getSidelineOrg());
      po.setSidelineOrgName(employeeVO.getSidelineOrgName());
      po.setWorkAddress(employeeVO.getWorkAddress());
      po.setUserIsActive(Byte.parseByte("1"));
      po.setUserIsFormalUser(new Integer(1));
      po.setEmpDutyLevel(getDutyLevel(oldEmp.getEmpDuty(), oldEmp.getDomainId()));
      po.setUserIsDeleted(employeeVO.getUserIsDeleted());
      po.setSerial(employeeVO.getSerial());
      po.setSerialPwd(employeeVO.getSerialPwd());
      po.setZhuanzhengDate(employeeVO.getZhuanzhengDate());
      po.setPersonalKind(employeeVO.getPersonalKind());
      po.setEmpFireType(employeeVO.getEmpFireType());
      po.setLizhiDate(employeeVO.getLizhiDate());
      po.setHujiAddress(employeeVO.getHujiAddress());
      po.setIsdimissionprove(employeeVO.getIsdimissionprove());
      po.setSpeciality1(employeeVO.getSpeciality1());
      po.setSpeciality2(employeeVO.getSpeciality2());
      po.setLanguage1(employeeVO.getLanguage1());
      po.setLanguage2(employeeVO.getLanguage2());
      po.setLanguage3(employeeVO.getLanguage3());
      po.setLanglevel1(employeeVO.getLanglevel1());
      po.setLanglevel2(employeeVO.getLanglevel2());
      po.setLanglevel3(employeeVO.getLanglevel3());
      po.setZhicheng(employeeVO.getZhicheng());
      po.setEmpPositionId(employeeVO.getEmpPositionId());
      po.setOrgId(orgId);
      po.setLogId(logId.longValue());
      po.setOperate("update-renshi");
      this.session.save(po);
      this.session.flush();
      Connection conn = null;
      Statement stmt = null;
      PreparedStatement pStmt = null;
      ResultSet rs = null;
      String empIdStr = String.valueOf(empId);
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        String sql = "select role_id from org_user_role where emp_id=" + empIdStr;
        rs = stmt.executeQuery(sql);
        String[] role = RS.toStrArr1(rs);
        pStmt = conn.prepareStatement("insert into audit_user_role (log_Id,emp_id,role_id) values (" + 
            logId + "," + empId + ",?" + ")");
        for (int i = 0; i < role.length; i++) {
          pStmt.setString(1, role[i]);
          pStmt.executeUpdate();
        } 
        pStmt.close();
        sql = "select rightscope_id,right_id,rightScopeType,rightScopeScope,rightScopeUser,rightScopeGroup,rightScope,domain_id from org_rightscope where emp_id=" + 
          empIdStr;
        rs = stmt.executeQuery(sql);
        String[][] right = RS.toStrArr2(rs, 8);
        pStmt = conn.prepareStatement("insert into JSDB.audit_rightscope(rightscope_id,emp_id,right_id,rightScopeType,rightScopeScope,rightScopeUser,rightScopeGroup,rightScope,domain_id,log_Id) values (?,?,?,?,?,?,?,?,?,?)");
        for (int j = 0; j < right.length; j++) {
          pStmt.setString(1, right[j][0]);
          pStmt.setString(2, empIdStr);
          pStmt.setString(3, right[j][1]);
          pStmt.setString(4, right[j][2]);
          pStmt.setString(5, right[j][3]);
          pStmt.setString(6, right[j][4]);
          pStmt.setString(7, right[j][5]);
          pStmt.setString(8, right[j][6]);
          pStmt.setString(9, right[j][7]);
          pStmt.setString(10, (String)logId);
          pStmt.executeUpdate();
        } 
        pStmt.close();
        stmt.close();
        conn.close();
      } catch (Exception e) {
        conn.close();
        e.printStackTrace();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return new Integer(result);
  }
  
  private String getDutyLevel(String dutyName, String domainId) throws Exception {
    String ret = "1000";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select dutylevel from oa_duty po where po.dutyName='" + 
          dutyName + "' and po.domain_Id=" + 
          domainId);
      if (rs.next())
        ret = rs.getString("dutylevel"); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return ret;
  }
  
  public List getMaturityAlertSettings(String type, String domainId) throws Exception {
    List<String[]> ret = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(
          "select name, code, value from oa_maturity_alert_settings where type = '" + 
          type + "' and domain_id = " + 
          domainId + " order by sort_code");
      String[] str = (String[])null;
      while (rs.next()) {
        str = new String[3];
        str[0] = rs.getString(1);
        str[1] = rs.getString(2);
        str[2] = rs.getString(3);
        ret.add(str);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean saveMaturityAlertSettings(String type, String[][] args, String domainId) throws Exception {
    Boolean ret = new Boolean(true);
    Connection conn = null;
    Statement stmt = null;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      if (args != null && args.length > 0)
        for (int i = 0; i < args.length; i++)
          stmt.executeUpdate(
              "update oa_maturity_alert_settings set value = '" + args[i][1] + 
              "' where type = '" + 
              type + "' and code = '" + args[i][0] + 
              "' and domain_id = " + 
              domainId);  
    } catch (Exception e) {
      ret = new Boolean(false);
      e.printStackTrace();
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return ret;
  }
  
  public String getMaturityAlertSettingsValue(String type, String code, String domainId) throws Exception {
    String ret = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(
          "select value from oa_maturity_alert_settings where type = '" + 
          type + "' and code = '" + code + "' and domain_id = " + 
          domainId);
      if (rs.next())
        ret = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return ret;
  }
  
  public Integer getLogCountByUserId(String userId) throws Exception {
    Integer ret = new Integer(0);
    try {
      begin();
      Query query = this.session.createQuery("select count(po.createdEmp) from com.js.oa.scheme.worklog.po.WorkLogPO po where po.createdEmp = " + 
          userId);
      List<E> list = query.list();
      if (list != null && list.size() > 0)
        ret = new Integer(list.get(0).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Integer getLogCountByOrgId(String orgId) throws Exception {
    Integer ret = new Integer(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    try {
      begin();
      String hql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        hql = "select count(po.createdEmp) from com.js.oa.scheme.worklog.po.WorkLogPO po, com.js.system.vo.organizationmanager.OrganizationVO poo where po.createdOrg = poo.orgId and poo.orgIdString like '%$" + 
          orgId + "$%'";
      } else if (databaseType.indexOf("db2") >= 0) {
        hql = "select count(po.createdEmp) from com.js.oa.scheme.worklog.po.WorkLogPO po, com.js.system.vo.organizationmanager.OrganizationVO poo where po.createdOrg = poo.orgId and poo.orgIdString like '%$" + 
          orgId + "$%'";
      } else {
        hql = "select count(po.createdEmp) from com.js.oa.scheme.worklog.po.WorkLogPO po, com.js.system.vo.organizationmanager.OrganizationVO poo where po.createdOrg = poo.orgId and poo.orgIdString like '%$" + 
          orgId + "$%'";
      } 
      Query query = this.session.createQuery(hql);
      List<E> list = query.list();
      if (list != null && list.size() > 0)
        ret = new Integer(list.get(0).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
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
          1, 1, new Date(), "审计提醒:" + log[1] + caozuo, "audit", logId.longValue(), "/jsoa/AuditUserAddAction.do?status=active&action=update&logId=" + logId + "&disabled=disabled&comeflag=tixing");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return logId.longValue();
  }
}
