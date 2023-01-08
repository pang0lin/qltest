package com.js.oa.portal.bean;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.AuditPortalPO;
import com.js.oa.audit.service.AuditLogBD;
import com.js.oa.audit.service.AuditPortalBD;
import com.js.oa.hr.subsidiarywork.po.NetSurveyPO;
import com.js.oa.info.channelmanager.bean.ChannelEJBBean;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.portal.po.CustomDefaultPO;
import com.js.oa.portal.po.CustomDesktopLayoutPO;
import com.js.oa.portal.po.CustomurlPO;
import com.js.system.manager.service.ManagerService;
import com.js.system.menu.bean.MenuEJBBean;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class CustomDesktopEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public CustomDesktopLayoutPO getLayoutById(String id) throws HibernateException {
    CustomDesktopLayoutPO cdlpo = null;
    begin();
    try {
      cdlpo = (CustomDesktopLayoutPO)this.session.load(CustomDesktopLayoutPO.class, 
          new Long(id));
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return cdlpo;
  }
  
  public boolean delLayout(String ids) throws HibernateException {
    begin();
    boolean bl = false;
    CustomDesktopLayoutPO po = new CustomDesktopLayoutPO();
    try {
      if (ids != null && !ids.equals("")) {
        po = (CustomDesktopLayoutPO)this.session.load(CustomDesktopLayoutPO.class, 
            new Long(ids.substring(0, ids.length() - 1)));
        this.session.delete(
            " from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")");
      } 
      File file = new File(po.getXmlFile().replace('/', '\\'));
      if (file.exists())
        file.delete(); 
      this.session.flush();
      bl = true;
      if (po.getIsUrl().equals("2")) {
        String sql = "UPDATE menu_sys SET rightUrl=rightUrlCopy where menu_id=" + (
          (po.getMenuId() == null) ? "-1" : po.getMenuId());
        (new MenuEJBBean()).executeSQL(sql);
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return bl;
  }
  
  public Integer saveLayout(CustomDesktopLayoutPO customDesktopLayoutPO, String path, String domainId) throws HibernateException {
    int result = 2;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String date = formatter.format(new Date());
    begin();
    try {
      result = Integer.parseInt(this.session.createQuery("select count(*) from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + 
            domainId + " and po.createdOrg=" + customDesktopLayoutPO.getCreatedOrg() + " and po.layoutName='" + 
            customDesktopLayoutPO.getLayoutName() + "'").iterate().next()
          .toString());
      if (result > 0) {
        result = 1;
        return new Integer(result);
      } 
      customDesktopLayoutPO.setXmlFile((String.valueOf(path) + "\\layout" + date + ".xml")
          .replace('\\', '/'));
      Long layoutId = (Long)this.session.save(customDesktopLayoutPO);
      this.session.flush();
      result = 0;
    } catch (HibernateException e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return new Integer(result);
  }
  
  public String saveLayoutForPerson(CustomDesktopLayoutPO customDesktopLayoutPO, String path, String domainId, String userId) throws HibernateException {
    String xmlname = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String date = formatter.format(new Date());
    begin();
    try {
      List<String> list = this.session.createQuery("select po.xmlFile from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + 
          domainId + " and po.createdOrg=" + customDesktopLayoutPO.getCreatedOrg() + " and po.ispublic=0 and po.personEmpId='" + userId + "'").list();
      if (list != null && list.size() > 0)
        xmlname = list.get(0); 
      if (!"".equals(xmlname)) {
        xmlname = xmlname.substring(xmlname.lastIndexOf("/") + 1, xmlname.length());
        return xmlname;
      } 
      customDesktopLayoutPO.setXmlFile((String.valueOf(path) + "\\layout" + date + ".xml")
          .replace('\\', '/'));
      xmlname = "layout" + date + ".xml";
      Long layoutId = (Long)this.session.save(customDesktopLayoutPO);
      this.session.flush();
    } catch (HibernateException e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return xmlname;
  }
  
  public String updateLayoutForPerson(CustomDesktopLayoutPO customDesktopLayoutPO, String path, String domainId, String userId, String porPerId) throws HibernateException {
    String xmlname = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String date = formatter.format(new Date());
    begin();
    try {
      List<String> list = this.session.createQuery("select po.xmlFile from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.id=" + porPerId + " and  po.domainId=" + 
          domainId + " and po.createdOrg=" + customDesktopLayoutPO.getCreatedOrg() + " and po.ispublic=0 and po.personEmpId='" + userId + "'").list();
      if (list != null && list.size() > 0)
        xmlname = list.get(0); 
      updateLayoutForPerson(customDesktopLayoutPO.getMenuName(), porPerId);
      if (!"".equals(xmlname)) {
        xmlname = xmlname.substring(xmlname.lastIndexOf("/") + 1, xmlname.length());
        return xmlname;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return xmlname;
  }
  
  public boolean updateLayoutForPerson(String name, String id) {
    DataSourceBase base = new DataSourceBase();
    boolean flag = false;
    try {
      base.begin();
      String sql = "update oa_portal set menuname='" + name + "' where id=" + id;
      base.executeUpdate(sql);
      flag = true;
      base.end();
    } catch (Exception e) {
      flag = false;
      base.end();
      e.printStackTrace();
    } 
    return flag;
  }
  
  public String getLayoutForPerson(String orgId, String domainId, String userId) throws HibernateException {
    String idandname = "";
    begin();
    try {
      List<Object[]> list = this.session.createQuery("select po.id,po.menuName from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + 
          domainId + " and po.createdOrg=" + orgId + " and po.ispublic=0 and po.personEmpId='" + userId + "'").list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        idandname = obj[0] + "&&" + obj[1];
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return idandname;
  }
  
  public boolean audit(CustomDesktopLayoutPO po, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
    boolean result = false;
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
      auditLog.setAuditModule(new Long(5L));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditPortalBD auditPortalBD = new AuditPortalBD();
      if ("delete".equals(opreate)) {
        begin();
        po = (CustomDesktopLayoutPO)this.session.load(CustomDesktopLayoutPO.class, id);
      } 
      AuditPortalPO auditPO = (AuditPortalPO)FillBean.transformOTO(po, AuditPortalPO.class);
      auditPO.setPortalId(id);
      auditPO.setAuditLogId(auditLogId);
      auditPO.setOperationType(opreate);
      Long auditIpId = auditPortalBD.saveAuditPortal(auditPO);
      if ("insert".equals(opreate))
        opreate = "新增"; 
      if ("update".equals(opreate))
        opreate = "修改"; 
      if ("delete".equals(opreate))
        opreate = "删除"; 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String userName = (String)httpsession.getAttribute("userName");
      msRemindBeann.auditMsRemind(Long.valueOf((String)httpsession.getAttribute("userId")).longValue(), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          1, 1, new Date(), "审计提醒:" + userName + opreate + "门户管理\"" + po.getLayoutName() + "\"", "audit", auditLogId.longValue(), "AuditPortalAction.do?action=forshenji&id=" + auditLogId + "&flag=fromRemind");
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public boolean autoAudit(CustomDesktopLayoutPO po, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
    boolean result = false;
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
      auditLog.setAuditModule(new Long(5L));
      auditLog.setAuditStatus(Integer.valueOf(1));
      auditLog.setIschecked(Integer.valueOf(1));
      auditLog.setCheckEmpid(new Long(0L));
      auditLog.setCheckEmpname("系统自动审核");
      auditLog.setCheckTime(ts);
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditPortalBD auditPortalBD = new AuditPortalBD();
      if ("delete".equals(opreate)) {
        begin();
        po = (CustomDesktopLayoutPO)this.session.load(CustomDesktopLayoutPO.class, id);
      } 
      AuditPortalPO auditPO = (AuditPortalPO)FillBean.transformOTO(po, AuditPortalPO.class);
      auditPO.setPortalId(id);
      auditPO.setAuditLogId(auditLogId);
      auditPO.setOperationType(opreate);
      Long auditIpId = auditPortalBD.saveAuditPortal(auditPO);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public Integer updateLayout(CustomDesktopLayoutPO customDesktopLayoutPO, String layoutId, String domainId) throws HibernateException {
    int result = 2;
    begin();
    try {
      result = Integer.parseInt(this.session.createQuery("select count(*) from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + 
            domainId + " and po.createdOrg='" + customDesktopLayoutPO.getCreatedOrg() + "' and po.layoutName='" + 
            customDesktopLayoutPO.getLayoutName() + "' and po.id<>" + layoutId).iterate().next().toString());
      if (result > 0) {
        result = 1;
        return new Integer(result);
      } 
      String oldMenuId = "-1";
      CustomDesktopLayoutPO vtpo = (CustomDesktopLayoutPO)this.session.load(
          CustomDesktopLayoutPO.class, 
          new Long(layoutId));
      oldMenuId = (vtpo.getMenuId() == null) ? "-1" : vtpo.getMenuId();
      vtpo.setLayoutName(customDesktopLayoutPO.getLayoutName());
      vtpo.setViewGroup(customDesktopLayoutPO.getViewGroup());
      vtpo.setViewOrg(customDesktopLayoutPO.getViewOrg());
      vtpo.setViewUser(customDesktopLayoutPO.getViewUser());
      vtpo.setViewRangeName(customDesktopLayoutPO.getViewRangeName());
      vtpo.setMenuName(customDesktopLayoutPO.getMenuName());
      vtpo.setShowOrder(customDesktopLayoutPO.getShowOrder());
      vtpo.setMenuType(customDesktopLayoutPO.getMenuType());
      vtpo.setIsUrl(customDesktopLayoutPO.getIsUrl());
      vtpo.setUrl(customDesktopLayoutPO.getUrl());
      vtpo.setMenuId(customDesktopLayoutPO.getMenuId());
      this.session.update(vtpo);
      this.session.flush();
    } catch (HibernateException e) {
      this.transaction.rollback();
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return new Integer(result);
  }
  
  public List listInformation(String channelId, String userId, String orgId, String orgIdString, String domainId) throws HibernateException {
    ChannelBD channelBD = new ChannelBD();
    boolean canVindicate = channelBD.canVindicate(userId, orgId, channelId);
    Date now = new Date();
    String nowString = now.toLocaleString();
    nowString = nowString.substring(0, nowString.indexOf(" "));
    String customer = SystemCommon.getCustomerName();
    List<Object[]> list = new ArrayList();
    Calendar date = Calendar.getInstance();
    String today = String.valueOf(date.get(1)) + "-" + (
      date.get(2) + 1) + "-" + 
      date.get(5) + " " + 
      date.get(11) + ":" + 
      date.get(12) + ":" + 
      date.get(13);
    StringBuffer buffer = new StringBuffer();
    List<Object[]> groupList = list;
    begin();
    try {
      InformationChannelPO informationChannelPO = 
        new InformationChannelPO();
      Iterator<InformationChannelPO> it = this.session.createQuery("select po from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=" + 
          channelId).iterate();
      if (it.hasNext()) {
        informationChannelPO = it.next();
      } else {
        return list;
      } 
      String databaseType = SystemCommon.getDatabaseType();
      orgIdString = buffer.append("$").append(orgIdString).append("$")
        .toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      StringBuffer sql = new StringBuffer();
      StringBuffer sql2 = new StringBuffer();
      buffer = new StringBuffer();
      buffer.append("select aaa.channelName, bbb.informationId, bbb.informationTitle, ");
      buffer.append(" bbb.informationKits, bbb.informationModifyTime, bbb.informationHead,bbb.informationType,aaa.channelType,aaa.channelId,aaa.channelShowType,bbb.titleColor,aaa.userDefine,bbb.isConf,bbb.informationHeadFile,bbb.orderCodeTemp,bbb.topTimeEnd,");
      buffer.append(" bbb.informationIssuer,bbb.informationIssueTime ");
      buffer.append(" from com.js.oa.info.infomanager.po.InformationPO bbb join ");
      buffer.append(" bbb.informationChannel aaa ");
      sql.append(buffer.toString());
      sql2.append(buffer.toString());
      buffer = new StringBuffer();
      list = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '知识维护' and  bbb.rightName = '维护'  and ccc.empId = " + 
          userId).list();
      boolean allScope = false;
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            buffer.append(" aaa.createdEmp = ").append(userId)
              .append(" or ");
          } else if (scopeType.equals("2")) {
            List orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                orgId + "$%'").list();
            for (int j = 0; j < orgList.size(); j++)
              buffer.append(" aaa.createdOrg = ").append(orgList
                  .get(j)).append(" or "); 
          } else if (scopeType.equals("3")) {
            buffer.append(" aaa.createdOrg = ").append(orgId)
              .append(" or ");
          } else if (scopeType.equals("4")) {
            String scopeScope = (obj[1] == null) ? "" : 
              obj[1].toString();
            if (!scopeScope.equals("")) {
              try {
                scopeScope = getJuniorOrg(scopeScope);
              } catch (Exception exception) {}
              buffer.append(" aaa.createdOrg in (").append(
                  scopeScope).append(") or ");
            } 
          } 
        } else {
          allScope = true;
        } 
      } 
      buffer.append("(");
      int i;
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" aaa.channelReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" aaa.channelReaderGroup like '%@").append(
            groupList.get(i)).append("@%' or "); 
      buffer.append(" aaa.channelReader like '%$").append(userId).append(
          "$%' ");
      buffer.append(" or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='')))");
      buffer.append(" and ((");
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" bbb.informationReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" bbb.informationReaderGroup like '%@").append(
            groupList.get(i)).append("@%' or "); 
      buffer.append(" bbb.informationReader like '%$").append(userId).append("$%' ");
      buffer.append(") or ((bbb.informationReader is null or bbb.informationReader='') and (bbb.informationReaderOrg is null or bbb.informationReaderOrg='') and (bbb.informationReaderGroup is null or bbb.informationReaderGroup='')))");
      String where = buffer.toString();
      sql.append(" where aaa.domainId=").append(domainId).append(" and (").append(where);
      InformationBD informationBD = new InformationBD();
      String childChannelIds = informationBD.getAllChildChannelIds(channelId);
      if (informationChannelPO.getIncludeChild() == 0) {
        sql.append(") and (aaa.channelId = ").append(channelId);
      } else if (informationChannelPO.getIncludeChild() == 1) {
        sql.append(") and (aaa.channelId in (").append(childChannelIds).append(")");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append(" or ( ('" + childChannelIds + "' like concat('%',bbb.otherChannel,'%') and bbb.otherChannel<>''))");
        } else {
          sql.append(" or ( ('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', bbb.otherChannel), '%') and bbb.otherChannel is not null))");
        } 
      } 
      if (!channelId.equals("100") && !channelId.equals("101")) {
        if (!informationBD.channelCanView2(userId, orgId.toString(), Integer.toString(informationChannelPO.getChannelType()), informationChannelPO.getUserDefine(), channelId)) {
          sql2.append(" where (1>2) ");
        } else if (canVindicate && informationChannelPO.getIncludeChild() == 0) {
          sql2.append(" where (aaa.channelId = ").append(channelId).append(" or bbb.otherChannel like '%,").append(channelId).append(",%') ");
        } else if (canVindicate && informationChannelPO.getIncludeChild() == 1) {
          sql2.append(" where (aaa.channelId in ( ").append(childChannelIds).append(" ) ");
          if (databaseType.indexOf("mysql") >= 0) {
            sql2.append(" or ('" + childChannelIds + "' like concat('%',bbb.otherChannel,'%') and bbb.otherChannel<>'') )");
          } else {
            sql2.append(" or ('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', bbb.otherChannel), '%') and bbb.otherChannel is not null) )");
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          sql2.append("where ( bbb.informationValidType = 0 or '").append(nowString).append("' between bbb.validBeginTime and bbb.validEndTime )");
          if (informationChannelPO.getIncludeChild() == 1) {
            ChannelEJBBean cbean = new ChannelEJBBean();
            try {
              childChannelIds = cbean.getUserViewChildCh(userId, orgId, channelId);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            sql2.append(" and (aaa.channelId in(").append(childChannelIds).append(")");
            sql2.append(" or ('" + childChannelIds + "' like concat('%',bbb.otherChannel,'%') and bbb.otherChannel <>'') )");
          } else {
            sql2.append(" and (aaa.channelId = ").append(channelId).append(" or bbb.otherChannel like '%,").append(channelId).append(",%')");
          } 
        } else {
          sql2.append(" where ( bbb.informationValidType = 0 or JSDB.FN_STRTODATE('").append(nowString).append("','S') between bbb.validBeginTime and bbb.validEndTime )");
          if (informationChannelPO.getIncludeChild() == 1) {
            ChannelEJBBean cbean = new ChannelEJBBean();
            try {
              childChannelIds = cbean.getUserViewChildCh(userId, orgId, channelId);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            sql2.append(" and (aaa.channelId in( ").append(childChannelIds).append(" )");
            sql2.append(" or ('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', bbb.otherChannel), '%') and bbb.otherChannel is not null) )");
          } else {
            sql2.append(" and (aaa.channelId = " + channelId + " or bbb.otherChannel like '%," + channelId + ",%') ");
          } 
        } 
        InformationBD infoBD = new InformationBD();
        String readerWhere = "1<>2";
        if (!canVindicate)
          readerWhere = infoBD.getInfoReader(userId, orgId, orgIdString, "bbb"); 
        String rightWhere = (new ManagerService()).getRightFinalWhere(userId, 
            orgId, "01*03*03", "bbb.informationIssueOrgId", 
            "bbb.informationIssuerId");
        sql2.append(" and ((").append(String.valueOf(readerWhere) + ")or (").append(rightWhere).append("))");
      } 
      if (databaseType.indexOf("mysql") >= 0) {
        sql.append(" or ('").append(childChannelIds.toString()).append("' like concat('%',bbb.otherChannel,'%') and bbb.otherChannel<>''))");
        sql.append(" and (bbb.informationIssueTime<='" + today + "') and bbb.informationStatus=0 and (bbb.informationValidType=0 or ( '")
          .append(today)
          .append("' >=bbb.validBeginTime and '")
          .append(today)
          .append("'<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ");
        sql2.append(" and bbb.informationIssueTime<='")
          .append(today)
          .append("' and bbb.informationStatus=0 and (bbb.informationValidType=0 or ( '")
          
          .append(today)
          .append("' >=bbb.validBeginTime and '")
          .append(today)
          .append("'<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ");
      } else {
        sql.append(" or ('" + childChannelIds.toString()).append("' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', bbb.otherChannel), '%') and bbb.otherChannel is not null))");
        sql.append(" and (JSDB.FN_STRTODATE('" + 
            today + "','S'))>=bbb.informationIssueTime and bbb.informationStatus=0 and (bbb.informationValidType=0 or (JSDB.FN_STRTODATE('")
          .append(today)
          .append("','S')>=bbb.validBeginTime and JSDB.FN_STRTODATE('")
          .append(today)
          .append("','S')<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ");
        sql2.append(" and bbb.informationStatus=0 and (JSDB.FN_STRTODATE('" + 
            today + "','S'))>=bbb.informationIssueTime and (bbb.informationValidType=0 or (JSDB.FN_STRTODATE('")
          .append(today)
          .append("','S')>=bbb.validBeginTime and JSDB.FN_STRTODATE('")
          .append(today)
          .append("','S')<=bbb.validEndTime)) order by bbb.orderCodeTemp desc,bbb.informationModifyTime desc, bbb.informationIssueTime desc,bbb.informationId desc ");
      } 
      Query query = null;
      if (!channelId.equals("100") && !channelId.equals("101")) {
        query = this.session.createQuery(sql2.toString());
      } else {
        query = this.session.createQuery(sql.toString());
      } 
      query.setFirstResult(0);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return list;
  }
  
  public List listInformationClass(String domainId) throws HibernateException {
    List<Object[]> list = new ArrayList();
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') and aaa.domainId=" + 
        
        domainId;
      hSql = String.valueOf(hSql) + " and aaa.channelType=0 or (aaa.channelType=1 and (aaa.channelIdString like '%$100$%' or aaa.channelIdString like '%$101$%'))";
      hSql = String.valueOf(hSql) + " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0")) {
          channelNameString = "知识管理.";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
          } else {
            continue;
          } 
        } else {
          query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like concat('%$', aaa.channelId, '$%') order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + 
            "."; 
        obj[2] = channelNameString.substring(0, 
            channelNameString.length() - 1);
        list.set(i, obj);
        continue;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listInformationClassByCorp(String domainId, String userId, String orgId, String corpId) throws HibernateException {
    List<Object[]> list = new ArrayList();
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') and aaa.domainId=" + 

        
        domainId + " and aaa.corpId=" + corpId + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0")) {
          channelNameString = "知识管理.";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
          } else {
            continue;
          } 
        } else {
          query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like concat('%$', aaa.channelId, '$%') order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + "."; 
        obj[2] = channelNameString.substring(0, channelNameString.length() - 1);
        list.set(i, obj);
        continue;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listIframeUrl(String domainId, String userId) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      String hSql = "select aaa.id,aaa.urlname,aaa.urlapp from com.js.oa.portal.po.CustomurlPO aaa order by aaa.id";
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listInformationDeptClass(String domainId) throws HibernateException {
    List<Object[]> list = new ArrayList();
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (aaa.channelType>0 and aaa.userDefine=0) and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') and aaa.domainId=" + 
        
        domainId + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0")) {
          channelNameString = "信息管理.";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } else {
          query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like concat('%$', aaa.channelId, '$%') order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + 
            "."; 
        obj[2] = channelNameString.substring(0, 
            channelNameString.length() - 1);
        list.set(i, obj);
        continue;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Object[] getInformationChannelByChannelId(String channelId) throws HibernateException {
    List<Object[]> list = new ArrayList();
    Object[] obj = (Object[])null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.channelShowType,aaa.channelType,aaa.channelType,aaa.includeChild from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId=" + 
        
        channelId;
      Query query = this.session.createQuery(hSql);
      list = query.list();
      Iterator<E> orgIter = null;
      if (list.size() > 0) {
        String userChannelName = "";
        String orgHasChannel = "";
        obj = list.get(0);
        if (obj[3].toString().equals("0")) {
          userChannelName = "知识管理";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext())
            userChannelName = orgIter.next().toString(); 
        } else {
          query = this.session.createQuery("select aaa.orgName,aaa.orgHasChannel from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            Object[] orgObj = (Object[])orgIter.next();
            userChannelName = orgObj[0].toString();
            orgHasChannel = orgObj[1].toString();
          } 
        } 
        obj[6] = userChannelName;
        obj[7] = orgHasChannel;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return obj;
  }
  
  public List listLinkSystemClass(String domainId) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      String hSql = "select po.id,po.className from com.js.oa.personalwork.netaddress.po.AddressClassPO po where po.domainId=" + 
        domainId;
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listLinkSystem(String where) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      String hSql = "select po.id,po.netAddressName,po.saveImg,po.netAddressUrl,po.sso,po.formaction,po.formusername,po.formuserpassword,po.ssologin,po.username,po.password,po.synopsis from com.js.oa.personalwork.netaddress.po.AddressPO po " + where;
      Query query = this.session.createQuery(hSql);
      query.setMaxResults(20);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String[] getLinkSystemUserPass(String systemId, String userId) throws HibernateException {
    String[] temp = { "", "" };
    try {
      begin();
      String hSql = "select po.username,po.password from com.js.oa.personalwork.netaddress.po.AddressPO po where po.id=" + systemId;
      Query query = this.session.createQuery(hSql);
      Iterator<Object[]> it = query.iterate();
      while (it.hasNext()) {
        Object[] obj = it.next();
        temp[0] = String.valueOf(obj[0]);
        temp[1] = String.valueOf(obj[1]);
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return temp;
  }
  
  public List listNotePaper(String userId, String domainId) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      String sql = "select po.id,po.notePaperContent,po.notePaperColor,po.createdTime,po.updateTime from com.js.oa.personalwork.paper.po.NotePaperPO po where po.domainId=" + 
        domainId + " and po.empId=" + userId + " and po.showno=0 order by po.id desc";
      Query query = this.session.createQuery(sql);
      query.setMaxResults(20);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String getPersonalDesktop(String userId, String orgIdString, String domainId) throws HibernateException {
    String ret = "layoutDefault.xml";
    begin();
    try {
      if (orgIdString == null || orgIdString.equals(""))
        return ret; 
      Query query = this.session.createQuery(
          "select po.id,po.viewRangeId,po.viewRangeName,po.xmlFile from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + 
          domainId + " order by po.id asc");
      List list = query.list();
      Iterator<Object[]> iter = list.iterator();
      StringTokenizer stOrg = new StringTokenizer(
          orgIdString, "$");
      String[] orgArr = new String[stOrg.countTokens()];
      int iik = 0;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return ret;
  }
  
  public List getPersonalDesktopList(String userId, String orgIdString, String domainId) throws HibernateException {
    List<String[]> dlist = new ArrayList();
    String ret = "";
    begin();
    try {
      if (orgIdString == null || orgIdString.equals(""))
        return dlist; 
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("((");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.ViewGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.ViewOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
      } 
      List<E> list = this.session.createQuery("select po.sidelineOrg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + userId).list();
      String[] sidelineOrgIdArr = (String[])null;
      if (list != null && list.size() > 0 && list.get(0) != null) {
        String sidelineOrg = list.get(0).toString();
        if (sidelineOrg.startsWith("*")) {
          sidelineOrg = sidelineOrg.substring(1, sidelineOrg.length() - 1);
          sidelineOrgIdArr = sidelineOrg.split("\\*\\*");
          for (i = 0; i < sidelineOrgIdArr.length; i++) {
            if (!"".equals(sidelineOrgIdArr[i]))
              buffer.append(" po.ViewOrg like '%*").append(sidelineOrgIdArr[i]).append("*%' or "); 
          } 
        } 
      } 
      buffer.append(" po.ViewUser like '%$").append(userId).append("$%'");
      buffer.append(") or ( ( po.ViewGroup='null' or po.ViewGroup is null or po.ViewGroup='') and (po.ViewOrg ='null' or po.ViewOrg is null or po.ViewOrg ='' ) and  ( po.ViewUser ='null' or po.ViewUser is null or po.ViewUser ='') ))  and po.domainId=").append(domainId);
      Query query = this.session.createQuery(
          "select distinct po.id,po.ViewUser,po.viewRangeName,po.xmlFile,po.menuName,po.menuType ,po.ViewOrg,po.ViewGroup,po.showOrder,po.isUrl,po.url,po.ispublic from com.js.oa.portal.po.CustomDesktopLayoutPO po where " + buffer.toString() + " and (po.isUrl=0 or po.isUrl=1) order by po.ispublic,po.showOrder asc ");
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return dlist;
  }
  
  public String getPersonalDesktopID(String userId, String orgIdString, String domainId) throws HibernateException {
    String id = "";
    begin();
    try {
      if (orgIdString == null || orgIdString.equals(""))
        return ""; 
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("((");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.ViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.ViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      List<E> list = this.session.createQuery("select po.sidelineOrg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + userId).list();
      String[] sidelineOrgIdArr = (String[])null;
      if (list != null && list.size() > 0 && list.get(0) != null) {
        String sidelineOrg = list.get(0).toString();
        if (sidelineOrg.startsWith("*")) {
          sidelineOrg = sidelineOrg.substring(1, sidelineOrg.length() - 1);
          sidelineOrgIdArr = sidelineOrg.split("\\*\\*");
          for (i = 0; i < sidelineOrgIdArr.length; i++) {
            if (!"".equals(sidelineOrgIdArr[i]))
              buffer.append(" po.ViewOrg like '%").append(sidelineOrgIdArr[i]).append("%' or "); 
          } 
        } 
      } 
      buffer.append(" po.ViewUser like '%").append(userId).append("%'");
      buffer.append(") or ( ( po.ViewGroup='null' or po.ViewGroup is null or po.ViewGroup='') and (po.ViewOrg ='null' or po.ViewOrg is null or po.ViewOrg ='' ) and  ( po.ViewUser ='null' or po.ViewUser is null or po.ViewUser ='') ))  and po.domainId=").append(domainId);
      Query query = this.session.createQuery(
          "select distinct po.id,po.ViewUser,po.viewRangeName,po.xmlFile,po.menuName,po.menuType ,po.ViewOrg,po.ViewGroup,po.showOrder,po.isUrl,po.url,po.ispublic from com.js.oa.portal.po.CustomDesktopLayoutPO po where " + buffer.toString() + " and (po.isUrl=0 or po.isUrl=1) order by po.ispublic,po.showOrder asc ");
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return id;
  }
  
  public List getPersonalDesktopDefaultList(String userId, String orgIdString, String domainId) throws HibernateException {
    String idsrange = getPersonalDesktopID(userId, orgIdString, domainId);
    List<String[]> dlist = new ArrayList();
    String ret = "";
    begin();
    try {
      if (orgIdString == null || orgIdString.equals(""))
        return dlist; 
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("((");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.viewgroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.vieworg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      List<E> list = this.session.createQuery("select po.sidelineOrg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + userId).list();
      String[] sidelineOrgIdArr = (String[])null;
      if (list != null && list.size() > 0 && list.get(0) != null) {
        String sidelineOrg = list.get(0).toString();
        if (sidelineOrg.startsWith("*")) {
          sidelineOrg = sidelineOrg.substring(1, sidelineOrg.length() - 1);
          sidelineOrgIdArr = sidelineOrg.split("\\*\\*");
          for (i = 0; i < sidelineOrgIdArr.length; i++) {
            if (!"".equals(sidelineOrgIdArr[i]))
              buffer.append(" po.vieworg like '%").append(sidelineOrgIdArr[i]).append("%' or "); 
          } 
        } 
      } 
      buffer.append(" po.viewuser like '%").append(userId).append("%'");
      buffer.append(") or ( ( po.viewgroup='null' or po.viewgroup is null or po.viewgroup='') and (po.vieworg ='null' or po.vieworg is null or po.vieworg ='' ) and  ( po.viewuser ='null' or po.viewuser is null or po.viewuser ='') ))  ");
      Query query = this.session.createQuery(
          "select distinct poo.id,poo.ViewUser,poo.viewRangeName,poo.xmlFile,poo.menuName,poo.menuType ,poo.ViewOrg,poo.ViewGroup,poo.showOrder,poo.isUrl,poo.url from com.js.oa.portal.po.CustomDefaultPO po,com.js.oa.portal.po.CustomDesktopLayoutPO poo  where " + buffer.toString() + " and po.portal_id=poo.id and (poo.isUrl=0 or poo.isUrl=1) order by poo.id ");
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return dlist;
  }
  
  public Map listLastUpdate(String userId, String orgId, String orgIdString, String layoutId, String domainId, String corpId) throws HibernateException, Exception {
    Calendar date = Calendar.getInstance();
    String today = String.valueOf(date.get(1)) + "-" + (date.get(2) + 1) + "-" + date.get(5) + " " + 
      date.get(11) + ":" + date.get(12) + ":" + date.get(13);
    Map<Object, Object> map = new HashMap<Object, Object>();
    StringBuffer buffer = new StringBuffer();
    List<Object[]> list = new ArrayList();
    List<Object[]> groupList = list;
    String channelIdStr = "0";
    begin();
    try {
      String corpSQL;
      if (!layoutId.equals("0")) {
        CustomDesktopLayoutPO customDesktopLayoutPO = 
          (CustomDesktopLayoutPO)this.session.load(
            CustomDesktopLayoutPO.class, new Long(layoutId));
        if (!"".equals(customDesktopLayoutPO.getMyColumn()) && 
          customDesktopLayoutPO.getMyColumn() != null) {
          channelIdStr = customDesktopLayoutPO.getMyColumn();
          channelIdStr = channelIdStr.substring(0, 
              channelIdStr.length() - 1);
          String[] channelIdArray = channelIdStr.split(",");
          for (int k = 0; k < channelIdArray.length; k++) {
            Iterator<String> iterChildChannel = this.session.iterate("select po.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelIdString  like ('%" + 
                channelIdArray[k] + "%') and po.includeChild=1");
            while (iterChildChannel.hasNext())
              channelIdStr = String.valueOf(channelIdStr) + "," + iterChildChannel.next(); 
          } 
        } 
      } 
      orgIdString = buffer.append("$").append(orgIdString).append("$")
        .toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      buffer = new StringBuffer();
      buffer.append(
          "select aaa.channelName, bbb.informationId, bbb.informationTitle, ");
      buffer.append(" bbb.informationKits, bbb.informationIssueTime, bbb.informationHead,bbb.informationType,aaa.channelType,aaa.channelId,aaa.channelShowType,bbb.titleColor,aaa.userDefine,bbb.isConf,bbb.informationHeadFile,bbb.orderCodeTemp,bbb.topTimeEnd");
      buffer.append(
          " from com.js.oa.info.infomanager.po.InformationPO bbb join ");
      buffer.append(" bbb.informationChannel aaa ");
      String sql = buffer.toString();
      buffer = new StringBuffer();
      list = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '知识维护' and  bbb.rightName = '维护'  and ccc.empId = " + 
          userId).list();
      boolean allScope = false;
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            buffer.append(" aaa.createdEmp = ").append(userId)
              .append(" or ");
          } else if (scopeType.equals("2")) {
            List orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                orgId + "$%'").list();
            for (int j = 0; j < orgList.size(); j++)
              buffer.append(" aaa.createdOrg = ").append(orgList
                  .get(j)).append(" or "); 
          } else if (scopeType.equals("3")) {
            buffer.append(" aaa.createdOrg = ").append(orgId)
              .append(" or ");
          } else if (scopeType.equals("4")) {
            String scopeScope = (obj[1] == null) ? "" : 
              obj[1].toString();
            if (!scopeScope.equals("")) {
              scopeScope = getJuniorOrg(scopeScope);
              buffer.append(" aaa.createdOrg in (").append(
                  scopeScope).append(") or ");
            } 
          } 
        } else {
          allScope = true;
        } 
      } 
      buffer.append("(");
      int i;
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" aaa.channelReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" aaa.channelReaderGroup like '%@").append(
            groupList.get(i)).append("@%' or "); 
      buffer.append(" aaa.channelReader like '%$").append(userId).append(
          "$%' ");
      buffer.append(" or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='')))");
      buffer.append(" and ");
      buffer.append(" ((");
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" bbb.informationReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" bbb.informationReaderGroup like '%@").append(
            groupList.get(i)).append("@%' or "); 
      buffer.append(" bbb.informationIssuerId=" + userId).append(" or");
      buffer.append(" bbb.informationReader like '%$").append(userId)
        .append("$%' ");
      buffer.append(") or ((bbb.informationReader is null or bbb.informationReader='') and (bbb.informationReaderOrg is null or bbb.informationReaderOrg='') and (bbb.informationReaderGroup is null or bbb.informationReaderGroup='')))");
      String where = buffer.toString();
      if ("".equals(corpId) || "0".equals(corpId)) {
        corpSQL = "";
      } else {
        corpSQL = " and aaa.corpId=" + corpId;
      } 
      if (channelIdStr.startsWith(","))
        channelIdStr = channelIdStr.substring(1); 
      sql = String.valueOf(sql) + 
        
        " where aaa.domainId=" + domainId + corpSQL + 
        " and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') " + 
        " and aaa.channelId not in (" + channelIdStr + 
        ") and aaa.isRollOnDesktop=0 and (" + where;
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + ") and (bbb.informationIssueTime<='" + today + "') and bbb.informationStatus=0 and (bbb.informationValidType=0 or ('" + today + "'>=bbb.validBeginTime and '" + today + "'<=bbb.validEndTime)) order by bbb.orderCode desc, bbb.informationIssueTime desc ";
      } else {
        sql = String.valueOf(sql) + ") and bbb.informationStatus=0  and (JSDB.FN_STRTODATE('" + 
          today + "','S'))>=bbb.informationIssueTime and (bbb.informationValidType=0 or (JSDB.FN_STRTODATE('" + 
          today + "','S')>=bbb.validBeginTime and JSDB.FN_STRTODATE('" + 
          today + "','S')<=bbb.validEndTime)) order by bbb.orderCode desc, bbb.informationIssueTime desc ";
      } 
      Query query = this.session.createQuery(sql);
      query.setFirstResult(0);
      query.setMaxResults(20);
      map.put("info", query.list());
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  private String getJuniorOrg(String range) throws Exception {
    StringBuffer buffer = new StringBuffer(" where ");
    range = "*" + range + "*";
    String[] rangeArray = range.split("\\*\\*");
    int i = 0;
    for (i = 0; i < rangeArray.length; i++) {
      if (i > 0)
        buffer.append(" or "); 
      buffer.append(" org.orgIdString like '%$");
      buffer.append(rangeArray[i]);
      buffer.append("$%' ");
    } 
    List list = this.session.createQuery(
        "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org " + 
        buffer.toString()).list();
    buffer = new StringBuffer();
    for (i = 0; i < list.size(); i++)
      buffer.append(list.get(i)).append(","); 
    range = buffer.toString();
    i = range.length();
    if (i > 0) {
      range = range.substring(0, i - 1);
    } else {
      range = "-1";
    } 
    return range;
  }
  
  public Map listMyMail(String userId, String domainId) throws HibernateException {
    Float mailboxSize = Float.valueOf("1");
    try {
      mailboxSize = getMailboxSize(userId);
    } catch (Exception exception) {}
    Map<Object, Object> map = new HashMap<Object, Object>();
    begin();
    try {
      String whereSql = "select mailuser.mailuserid,mailuser.mailstatus,mailinterior.maillevel,mailinterior.mailhasaccessory,mailinterior.mailsubject,mailinterior.mailposttime,mailuser.mailreceivetime,mailinterior.mailid,mailinterior.mailpostername,mailuser.mailreturned,mailinterior.encrypt from com.js.oa.personalwork.innermailbox.po.InnerMailUserPO mailuser join mailuser.mailInterior mailinterior where mailuser.domainId=" + 

        
        domainId + " and mailuser.employee.empId = " + 
        userId + " and (mailuser.mailstatus= 0 or mailuser.mailstatus = 1) ";
      whereSql = String.valueOf(whereSql) + "order by mailuser.mailstatus,mailuser.mailuserid desc";
      Query query = this.session.createQuery(whereSql);
      query.setFirstResult(0);
      query.setMaxResults(20);
      map.put("myMail", query.list());
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return map;
  }
  
  public Map listMyForum(String scopeWhere) throws HibernateException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    begin();
    try {
      Query query = this.session.createQuery("select po.id,po.forumTitle,po.forumType, po.newretime,classPO.id,classPO.className from com.js.oa.bbs.po.ForumPO po join po.forumClass classPO where  po.forumTopicId=0 and (" + 
          scopeWhere + 
          ") and (po.examinNum=2 or po.examinNum=3)  order by po.forumType desc , po.newretime desc , po.id desc");
      query.setFirstResult(0);
      query.setMaxResults(20);
      map.put("forum", query.list());
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return map;
  }
  
  public Map listFileDeal(String userId, String domainId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      String[] result = new String[9];
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 0 and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete<>1";
      String dataType = SystemCommon.getDatabaseType();
      if (dataType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + " and (activityDelaySend is null or activityDelaySend = '' or activityDelaySend<=date_format(now(),'%Y-%c-%d %H:%i:%s'))";
      } else {
        sql = String.valueOf(sql) + " and (activityDelaySend is null or activityDelaySend = '' or TO_DATE(activityDelaySend,'yyyy-mm-dd hh24:mi:ss')<=SYSDATE)";
      } 
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        result[0] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 1 and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[1] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workstartflag = 1 and  wf_curEmployee_id = " + 
        userId + 
        " and workStatus = -1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[2] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workstartflag = 1 and  wf_curEmployee_id = " + 
        userId + 
        " and workDoneWithDate is not null and workDelete=0";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[3] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 101 and workDoneWithDate is null and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[4] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 101  and workDoneWithDate is not null and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[8] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 2 and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[5] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = -2 and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[6] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 102 and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[7] = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
      map.put("waitsign", result);
    } catch (Exception e) {
      conn.close();
      e.printStackTrace();
      throw e;
    } 
    return map;
  }
  
  public Map listSurvey(String orgIdString, String domainId) throws HibernateException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    StringBuffer buffer = new StringBuffer();
    Calendar date = Calendar.getInstance();
    begin();
    try {
      orgIdString = buffer.append("$").append(orgIdString).append("$")
        .toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List<NetSurveyPO> surveyList = new ArrayList();
      buffer = new StringBuffer();
      buffer.append("from com.js.oa.hr.subsidiarywork.po.NetSurveyPO po where po.surveyStatus=0 and po.domainId=" + 
          domainId);
      int ii = 0;
      int i;
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i])) {
          if (ii == 0) {
            buffer.append(" and (po.surveyRange like '%*").append(
                orgIdArray[i]).append("*%'");
          } else {
            buffer.append(" or po.surveyRange like '%*").append(
                orgIdArray[i]).append("*%'");
          } 
          ii++;
        } 
      } 
      if (i > 0)
        buffer.append(") or (po.surveyRange is null or po.surveyRange='')"); 
      String day = String.valueOf(date.get(1)) + "/" + (date.get(2) + 1) + 
        "/" + date.get(5);
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        buffer.append(" and '" + day + "'>=po.surveyBeginTime and '" + day + "'<=po.surveyEndTime");
      } else {
        buffer.append(" and JSDB.FN_STRTODATE('" + day + 
            "', 'S')>=po.surveyBeginTime and JSDB.FN_STRTODATE('" + 
            day + "', 'S')<=po.surveyEndTime");
      } 
      Iterator<NetSurveyPO> it = this.session.iterate(buffer.toString());
      while (it.hasNext()) {
        NetSurveyPO netSurveyPO = it.next();
        Set surveyItems = netSurveyPO.getSurveyItems();
        if (surveyItems != null && surveyItems.size() > 0)
          surveyList.add(netSurveyPO); 
      } 
      map.put("survey", surveyList);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return map;
  }
  
  public void setChannelOnDesktop(String channelId, String ondesktop) throws Exception {
    InformationChannelPO informationChannelPO = null;
    begin();
    try {
      informationChannelPO = (InformationChannelPO)this.session.load(
          InformationChannelPO.class, new Long(channelId));
      if ("1".equals(ondesktop)) {
        informationChannelPO.setOnDesktop(1);
      } else if ("0".equals(ondesktop)) {
        informationChannelPO.setOnDesktop(0);
      } 
      this.session.update(informationChannelPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void setChannelOnDesktopSToLayout(String layoutId, String channelIdStr) throws Exception {
    CustomDesktopLayoutPO customDesktopLayoutPO = null;
    begin();
    try {
      customDesktopLayoutPO = (CustomDesktopLayoutPO)this.session.load(
          CustomDesktopLayoutPO.class, new Long(layoutId));
      customDesktopLayoutPO.setMyColumn(channelIdStr);
      this.session.update(customDesktopLayoutPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List listAllOrgs(String domainId) throws HibernateException {
    List allOrgArray = new ArrayList();
    begin();
    try {
      String sql = "SELECT org.orgId,org.orgName,org.orgIdString,org.orgNameString,org.orgHasChannel FROM com.js.system.vo.organizationmanager.OrganizationVO org where org.domainId=" + 
        
        domainId + 
        " ORDER BY org.orgIdString";
      allOrgArray = this.session.createQuery(sql).list();
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return allOrgArray;
  }
  
  public Object[] getOrgByOrgId(String orgId) throws HibernateException {
    List<Object[]> list = new ArrayList();
    Object[] obj = (Object[])null;
    begin();
    try {
      String hSql = "SELECT org.orgId,org.orgName,org.orgIdString,org.orgNameString,org.orgHasChannel FROM com.js.system.vo.organizationmanager.OrganizationVO org where org.orgId=" + 
        
        orgId;
      Query query = this.session.createQuery(hSql);
      list = query.list();
      if (list.size() > 0)
        obj = list.get(0); 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return obj;
  }
  
  public List listForumClass(String domainId) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(
          " select po.id,po.className,po.classLevel,po.classParentName from com.js.oa.bbs.po.ForumClassPO po where po.relProjectId=0 and po.classHasJunior<>1 and po.domainId=" + 
          
          domainId + " order by po.classSort ").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listProClass(String domainId) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(" select po.id,po.name from com.js.oa.relproject.po.RelProClassPO po").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listFileDealDT(String userId, String domainId, String workStatus, Integer num) throws Exception {
    List list = null;
    begin();
    try {
      String sql = "select aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId, aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime, aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId,aaa.processDeadlineDate ,aaa.wfCurEmployeeId from com.js.oa.jsflow.po.WFWorkPO aaa ";
      if (workStatus.equals("101")) {
        sql = String.valueOf(sql) + 
          " where (aaa.workStatus=101 or aaa.workStatus=102) and aaa.wfCurEmployeeId = " + 
          userId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.wfWorkId desc";
      } else {
        sql = String.valueOf(sql) + " where aaa.workStatus = " + workStatus + 
          " and aaa.wfCurEmployeeId = " + 
          userId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.wfWorkId desc";
      } 
      Query query = this.session.createQuery(sql);
      query.setFirstResult(0);
      query.setMaxResults(num.intValue());
      list = query.list();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List listFileDealList(String userId, String domainId, String workStatus, String category, String includeCoop, Integer num) throws Exception {
    List list = null;
    begin();
    try {
      String sql = "select aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId,aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime, aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId,aaa.processDeadlineDate,aaa.wfCurEmployeeId ,aaa.isSubProcWork,aaa.pareProcActiId,aaa.pareStepCount,aaa.pareTableId,aaa.pareRecordId,aaa.workStatus from com.js.oa.jsflow.po.WFWorkPO aaa";
      sql = String.valueOf(sql) + " where 1=1 ";
      if ("-100".equals(category)) {
        sql = String.valueOf(sql) + " and aaa.workProcessId in(" + getAllDocProcessIds("2") + ")";
      } else if ("-101".equals(category)) {
        sql = String.valueOf(sql) + " and aaa.workProcessId in(" + getAllDocProcessIds("3") + ")";
      } else if ("-102".equals(category)) {
        sql = String.valueOf(sql) + " and aaa.workProcessId in(" + getAllDocProcessIds("") + ")";
      } else if (category != null && !"-1".equals(category) && !"".equals(category)) {
        sql = String.valueOf(sql) + " and aaa.workProcessId=" + category;
      } 
      if (includeCoop != null)
        if ("0".equals(includeCoop)) {
          sql = String.valueOf(sql) + " and (aaa.workProcessId<>11 and aaa.workProcessId not in(" + getAllDocProcessIds("") + "))";
        } else if ("1".equals(includeCoop)) {
          sql = String.valueOf(sql) + " and aaa.workProcessId not in(" + getAllDocProcessIds("") + ")";
        } else if ("2".equals(includeCoop)) {
          sql = String.valueOf(sql) + " and aaa.workProcessId<>11 ";
        } else {
          "3".equals(includeCoop);
        }  
      if (workStatus.equals("1"))
        sql = String.valueOf(sql) + "  and  aaa.workStatus = 1 and aaa.wfCurEmployeeId = " + userId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.wfWorkId desc"; 
      if (workStatus.equals("2") || workStatus.equals("102"))
        sql = String.valueOf(sql) + "  and  aaa.workStatus = " + workStatus + " and aaa.wfCurEmployeeId = " + userId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.wfWorkId desc"; 
      if (workStatus.equals("0"))
        sql = String.valueOf(sql) + "  and  aaa.workStatus = 0 and aaa.wfCurEmployeeId = " + userId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.stickie desc, aaa.emergence desc, aaa.wfWorkId desc"; 
      if (workStatus.equals("1011"))
        sql = String.valueOf(sql) + "  and  aaa.workStatus = 101 and aaa.workDoneWithDate is null and aaa.wfCurEmployeeId = " + 
          userId + " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.wfWorkId desc"; 
      if (workStatus.equals("1012"))
        sql = String.valueOf(sql) + "  and  aaa.workStatus = 101 and aaa.workDoneWithDate is not null and aaa.wfCurEmployeeId = " + 
          userId + " and aaa.workListControl = 1 and aaa.workDelete = 0 order by aaa.wfWorkId desc"; 
      if (workStatus.equals("100"))
        sql = String.valueOf(sql) + "  and  aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
          userId + " and aaa.workDoneWithDate is not null and workDelete = 0 order by aaa.wfWorkId desc"; 
      if (workStatus.equals("-1") || workStatus.equals("-2"))
        sql = String.valueOf(sql) + "  and  aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
          userId + " and  aaa.workStatus = " + workStatus + " and aaa.workDelete = 0 order by aaa.wfWorkId desc"; 
      String dataType = SystemCommon.getDatabaseType();
      if (dataType.indexOf("mysql") >= 0) {
        sql = sql.replaceFirst("where", "where (aaa.activityDelaySend is null or aaa.activityDelaySend = '' or aaa.activityDelaySend<=date_format(now(),'%Y-%c-%d %H:%i:%s')) AND ");
      } else {
        sql = sql.replaceFirst("where", "where (aaa.activityDelaySend is null or aaa.activityDelaySend = '' or TO_DATE(aaa.activityDelaySend,'yyyy-mm-dd hh24:mi:ss')<=SYSDATE) AND ");
      } 
      Query query = this.session.createQuery(sql);
      query.setFirstResult(0);
      query.setMaxResults(num.intValue());
      list = query.list();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getSendFileUrlParameters(String workId) throws Exception {
    List list = null;
    begin();
    try {
      String sql = "select aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId,aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime, aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId,aaa.processDeadlineDate,aaa.wfCurEmployeeId ,aaa.isSubProcWork,aaa.pareProcActiId,aaa.pareStepCount,aaa.pareTableId,aaa.pareRecordId,aaa.workStatus from com.js.oa.jsflow.po.WFWorkPO aaa";
      sql = String.valueOf(sql) + " where aaa.wfWorkId=" + workId;
      Query query = this.session.createQuery(sql);
      list = query.list();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List listMatterCategoryList() throws Exception {
    List list = null;
    begin();
    try {
      String sql = "select po.wfWorkFlowProcessId,po.workFlowProcessName from com.js.oa.jsflow.po.WFWorkFlowProcessPO po ";
      Query query = this.session.createQuery(sql);
      list = query.list();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List myReceiveFile(String userId, String domainId, Integer num) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select distinct po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime,po.createdEmp,po.createdOrg,po.sendFileLink,po.sendFileOverSee,sendFileUser.id,po.goldGridId,po.tableId from com.js.doc.doc.po.GovDocumentSendFilePO po join po.sendFileUser sendFileUser where sendFileUser.empId=" + 

          
          userId + " and po.domainId=" + 
          domainId + " order by po.id desc");
      query.setFirstResult(0);
      query.setMaxResults(num.intValue());
      list = query.list();
    } catch (Exception e) {
      this.session.close();
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getMyJoinTask(String userId, String domainId) throws Exception {
    List taskList = null;
    begin();
    try {
      StringBuffer taskIds = new StringBuffer();
      Query joinQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or task.taskJoineOrg like '%" + 
          userId + "%') and task.taskDomainId = " + 
          domainId);
      joinQuery.setFirstResult(0);
      joinQuery.setMaxResults(20);
      Iterator<E> iterator = joinQuery.iterate();
      String taskid = "";
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
            taskid + "$%' and task.taskDomainId = " + domainId);
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      Query childPrincipalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal =" + 
          userId + 
          " and task.taskParentId<>0 and task.taskDomainId = " + domainId);
      childPrincipalTask.setFirstResult(0);
      childPrincipalTask.setMaxResults(20);
      iterator = childPrincipalTask.iterate();
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
            taskid + "$%' and task.taskDomainId = " + domainId);
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      String taskId = taskIds.toString();
      if (taskId != null && !taskId.trim().equals("")) {
        taskId = taskId.substring(0, taskId.length() - 1);
        Query q = this.session.createQuery("select distinct task.taskId,task.taskTitle,task.isArranged,task.parentIdString from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and task.taskStatus <> 2 and task.taskStatus <> 4 and task.taskFinishRate<100 and (task.taskHasPass<>0 or (task.taskHasPass=0 and task.createdEmp =" + 
            userId + 
            "))  and task.taskDomainId = " + 
            domainId + 
            " order by task.parentIdString");
        q.setFirstResult(0);
        q.setMaxResults(20);
        taskList = q.list();
      } 
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return taskList;
  }
  
  public List getMyPrincipalTask(String userId, String domainId) throws Exception {
    List taskList = null;
    begin();
    try {
      Query query = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and task.taskParentId =0 and task.taskPrincipal = " + 
          userId + " and task.taskDomainId = " + 
          domainId);
      query.setFirstResult(0);
      query.setMaxResults(20);
      String taskIds = "";
      List list = query.list();
      for (int i = 0; i < list.size(); i++) {
        Object o = list.get(i);
        taskIds = String.valueOf(taskIds) + o.toString() + ",";
      } 
      if (!taskIds.equals("")) {
        taskIds = taskIds.substring(0, taskIds.length() - 1);
        query = this.session.createQuery("select distinct task.taskId,task.taskTitle,task.isArranged from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
            
            taskIds + 
            ") and task.taskFinishRate<100 and task.taskDomainId = " + 
            domainId + " order by task.taskId");
        query.setFirstResult(0);
        query.setMaxResults(20);
        taskList = query.list();
      } 
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return taskList;
  }
  
  private Float getMailboxSize(String userId) throws Exception {
    begin();
    float result = 1000.0F;
    try {
      Iterator<String> iter = this.session.iterate("select emp.mailboxSize from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=" + userId);
      String mailBoxSize = "1000";
      if (iter.hasNext()) {
        Object tmp = iter.next();
        if (tmp != null && !"".equals(tmp.toString()) && !tmp.toString().equalsIgnoreCase("null"))
          mailBoxSize = tmp.toString(); 
      } 
      iter = this.session.iterate("select sum(mail.accessorySize) from com.js.oa.personalwork.innermailbox.po.InnerMailinteriorPO mail join mail.mailUser mailuser join mailuser.employee emp where emp.empId=" + userId);
      String accessorySize = "0";
      if (iter.hasNext())
        accessorySize = iter.next(); 
      accessorySize = (accessorySize == null || accessorySize.equals("") || accessorySize.equalsIgnoreCase("null")) ? "0" : accessorySize;
      result = Float.parseFloat(mailBoxSize) - Float.parseFloat(accessorySize) / 1048576.0F;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return Float.valueOf((new StringBuilder(String.valueOf(result))).toString());
  }
  
  public String getOaUserAccountByOrg(String orgIdString) throws Exception {
    String userAccountString = "";
    return userAccountString;
  }
  
  public String getOaUserAccount(String userIdString) throws Exception {
    String userAccountString = "";
    return userAccountString;
  }
  
  public Map getRelationInfo(String userId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      String[] result = new String[9];
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 1 and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        result[0] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workstartflag = 1 and  wf_curEmployee_id = " + 
        userId + 
        " and workDoneWithDate is not null and workDelete=0";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[1] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 101 and workDoneWithDate is null and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[2] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK where workStatus = 101  and workDoneWithDate is not null and  wf_curEmployee_id = " + 
        userId + 
        " and workListControl = 1 and workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[3] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(information_id) from JSDB.oa_information where  informationissuerid = " + 
        userId;
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[4] = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
      map.put("relationinfo", result);
    } catch (Exception e) {
      conn.close();
      e.printStackTrace();
      throw e;
    } 
    return map;
  }
  
  public Map getRelationInfoByOrgId(String orgId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      String[] result = new String[9];
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK a,org_organization_user b where a.workStatus = 1 and a.wf_curEmployee_id=b.emp_id  and b.org_id = " + 
        
        orgId + 
        " and a.workListControl = 1";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        result[0] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK a,org_organization_user b where a.workstartflag = 1  and a.wf_curEmployee_id=b.emp_id  and b.org_id = " + 
        
        orgId + 
        " and a.workDoneWithDate is not null and a.workDelete=0";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[1] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK a,org_organization_user b where a.workStatus = 101 and a.workDoneWithDate is null  and a.wf_curEmployee_id=b.emp_id  and b.org_id = " + 
        
        orgId + 
        " and a.workListControl = 1 and a.workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[2] = rs.getString(1); 
      rs.close();
      sql = 
        " select count(wf_work_id) from JSDB.JSF_WORK a,org_organization_user b where a.workStatus = 101  and a.workDoneWithDate is not null  and a.wf_curEmployee_id=b.emp_id  and b.org_id = " + 
        
        orgId + 
        " and a.workListControl = 1 and a.workDelete=0 ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        result[3] = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
      map.put("relationinfo", result);
    } catch (Exception e) {
      conn.close();
      e.printStackTrace();
      throw e;
    } 
    return map;
  }
  
  public List getRelationObject(String moduleType, String moduleSubId) throws Exception {
    List<Object[]> list = new ArrayList();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select a.OBJECTID,a.OBJECTNAME,a.OBJECTTYPE from JSDB.OA_RELATIONOBJECT a";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[4];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = "0";
        list.add(obj);
      } 
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        String sql2 = "select moduleid from JSDB.oa_relationmodule b  where b.moduletype='" + 
          moduleType + "' and b.modulesubid=" + moduleSubId + " and objectid=" + obj[0];
        ResultSet rs2 = stmt.executeQuery(sql2);
        if (rs2.next())
          obj[3] = "1"; 
        rs2.close();
      } 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
    } 
    return list;
  }
  
  public Boolean saveRelationModule(String moduleType, String moduleSubId, String[] objectId, String domainId) throws Exception {
    Boolean bl = new Boolean(false);
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "delete from JSDB.oa_relationmodule where moduleType='" + moduleType + "' and moduleSubId=" + moduleSubId + " and domain_id=" + domainId;
      stmt.execute(sql);
      if (objectId != null)
        for (int i = 0; i < objectId.length; i++) {
          String moduleId = getTableId2();
          sql = "insert into JSDB.oa_relationmodule values (" + 
            moduleId + ",'" + moduleType + "'," + 
            moduleSubId + "," + objectId[i] + "," + domainId + ")";
          stmt.execute(sql);
        }  
      stmt.close();
      bl = new Boolean(true);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
    } 
    return bl;
  }
  
  public String getTableId2() throws Exception {
    String seq = "0";
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      ResultSet rs = null;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
        rs = stmt.executeQuery("Select HIBERNATE_SEQUENCE.Nextval From dual");
        if (rs.next())
          seq = rs.getString(1); 
      } else {
        rs = stmt.executeQuery("select seq_seq from JSDB.oa_seq where id=1");
        if (rs.next()) {
          seq = (new StringBuilder(String.valueOf(rs.getLong(1) + 1L))).toString();
          stmt.execute("update JSDB.oa_seq set seq_seq=seq_seq+1");
        } else {
          seq = "1000";
          stmt.execute("insert into JSDB.oa_seq (id, seq_seq) values (1, 1000)");
        } 
      } 
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
      e.printStackTrace();
    } 
    return seq;
  }
  
  public List getDefinedRelationObject(String moduleType, String moduleSubId) throws Exception {
    List<Object[]> list = new ArrayList();
    begin();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select a.OBJECTID,a.OBJECTNAME,a.OBJECTTYPE,b.moduleType from JSDB.OA_RELATIONOBJECT a,JSDB.oa_relationmodule b  where a.objectid=b.objectid and b.moduletype='" + 
        moduleType + "' and b.modulesubid=" + moduleSubId;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[4];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        list.add(obj);
      } 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
      this.session.close();
    } 
    return list;
  }
  
  public List getRelationInfo(String moduleType, String moduleSubId, String infoId) throws Exception {
    List<Object[]> list = new ArrayList();
    begin();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select RELATIONINFONAME,RELATIONINFOID,RELATIONINFOHREF,moduleType,RELATIONOBJECTTYPE from JSDB.OA_RELATIONDATA  where MODULETYPE='" + 
        moduleType + "' and MODULESUBID=" + moduleSubId + " and INFOID=" + infoId;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[5];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        list.add(obj);
      } 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
      this.session.close();
    } 
    return list;
  }
  
  public Boolean saveRelationInfo(String moduleType, String moduleSubId, String infoId, String infoRelationObjectType, String[] infoRelationId, String[] infoRelationName, String[] infoRelationHref, String domainId) throws Exception {
    begin();
    Boolean bl = new Boolean(false);
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "delete from JSDB.oa_relationdata where moduleType='" + moduleType + "' and moduleSubId=" + moduleSubId + " and RELATIONOBJECTTYPE='" + infoRelationObjectType + "' and infoId=" + infoId + " and domain_id=" + domainId;
      stmt.execute(sql);
      if (infoRelationId != null && infoRelationName != null)
        for (int i = 0; i < infoRelationId.length; i++) {
          String dataId = getTableId2();
          sql = "insert into JSDB.oa_relationdata values (" + 
            dataId + ",'" + moduleType + "'," + 
            moduleSubId + "," + infoId + ",'" + infoRelationObjectType + "','" + infoRelationName[i] + "'," + infoRelationId[i] + ",'" + infoRelationHref[i] + "'," + domainId + ")";
          stmt.execute(sql);
        }  
      stmt.close();
      bl = new Boolean(true);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
      this.session.close();
    } 
    return bl;
  }
  
  public Boolean delRelationInfo(String moduleType, String moduleSubId, String infoId, String domainId) throws Exception {
    begin();
    Boolean bl = new Boolean(false);
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "delete from JSDB.oa_relationdata where moduleType='" + moduleType + "' and moduleSubId=" + moduleSubId + " and infoId in(" + infoId + ") and domain_id=" + domainId;
      stmt.execute(sql);
      stmt.close();
      bl = new Boolean(true);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
      this.session.close();
    } 
    return bl;
  }
  
  public Boolean delRelationModule(String moduleType, String moduleSubId, String domainId) throws Exception {
    begin();
    Boolean bl = new Boolean(false);
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "delete from JSDB.oa_relationmodule where moduleType='" + moduleType + "' and moduleSubId in(" + moduleSubId + ") and domain_id=" + domainId;
      stmt.execute(sql);
      stmt.close();
      bl = new Boolean(true);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
      this.session.close();
    } 
    return bl;
  }
  
  public String[] getWorkFlowInfoByRelation(String relationObejctType, String relationInfoId) throws Exception {
    String[] ret = { "0", "0" };
    begin();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select count(*) from JSDB.OA_RELATIONDATA a,JSDB.ttable b,JSDB.tarea c,JSF_WORK d  where a.MODULESUBID=b.table_id and b.table_name=c.area_table and d.WORKTABLE_ID=c.page_id  and d.WORKRECORD_ID=a.INFOID and a.MODULETYPE='customdb' and d.WORKSTATUS=1 and d.WORKDONEWITHDATE is null  and a.RELATIONOBJECTTYPE='" + 

        
        relationObejctType + "' and a.RELATIONINFOID=" + relationInfoId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        ret[0] = rs.getString(1); 
      sql = "select count(*) from JSDB.OA_RELATIONDATA a,JSDB.ttable b,JSDB.tarea c,JSF_WORK d  where a.MODULESUBID=b.table_id and b.table_name=c.area_table and d.WORKTABLE_ID=c.page_id  and d.WORKRECORD_ID=a.INFOID and a.MODULETYPE='customdb' and d.WORKSTATUS=100 and d.WORKDONEWITHDATE is not null  and a.RELATIONOBJECTTYPE='" + 

        
        relationObejctType + "' and a.RELATIONINFOID=" + relationInfoId;
      rs = stmt.executeQuery(sql);
      if (rs.next())
        ret[1] = rs.getString(1); 
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn.close();
      this.session.close();
    } 
    return ret;
  }
  
  public String loadNetAddressClass(String classId) throws Exception {
    String ret = "";
    try {
      begin();
      String hSql = "select po.className from com.js.oa.personalwork.netaddress.po.AddressClassPO po where po.id=" + classId;
      Query query = this.session.createQuery(hSql);
      Iterator it = query.iterate();
      while (it.hasNext())
        ret = String.valueOf(it.next()); 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return ret;
  }
  
  public void showNo(String id) throws Exception {
    Connection conn2 = null;
    try {
      begin();
      conn2 = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn2.createStatement();
      int a = Integer.parseInt(id);
      String sql = "update JSDB.OA_NOTEPAPER set SHOW_NO = 2 where NOTEPAPER_ID=" + a;
      stmt.execute(sql);
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      conn2.close();
      this.session.close();
    } 
  }
  
  public List getGroupById(String userId) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void updateLayoutEmp(String layoutId, String empId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      String orgId = "1";
      base.begin();
      String sql = "select org_id from org_organization_user where emp_Id=" + empId;
      rs = base.executeQuery(sql);
      if (rs.next())
        orgId = rs.getString(1); 
      rs.close();
      sql = "update oa_portal set createdemp='" + empId + "' ,createdorg='" + orgId + "' where id=" + layoutId;
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public String getLayoutEmp(String layoutId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "";
    try {
      base.begin();
      String sql = "select e.emp_id,e.empname from org_employee e,oa_portal t where e.emp_id=t.createdemp and t.id=" + layoutId;
      rs = base.executeQuery(sql);
      if (rs.next())
        empId = String.valueOf(rs.getString(1)) + ":" + rs.getString(2); 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empId;
  }
  
  public Boolean saveCustomDeskUrl(CustomurlPO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean saveCustomDefaultPortal(CustomDefaultPO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public CustomurlPO loadCustomDeskUrl(Long id) throws Exception {
    CustomurlPO po = new CustomurlPO();
    try {
      begin();
      po = (CustomurlPO)this.session.load(CustomurlPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public CustomDefaultPO loadCustomDefault(Long id) throws Exception {
    CustomDefaultPO po = new CustomDefaultPO();
    try {
      begin();
      po = (CustomDefaultPO)this.session.load(CustomDefaultPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean updateCustomDeskUrl(CustomurlPO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      CustomurlPO updatePO = (CustomurlPO)this.session.load(CustomurlPO.class, id);
      updatePO.setUrlapp(po.getUrlapp());
      updatePO.setUrlname(po.getUrlname());
      this.session.update(updatePO);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean updateCustomDefaultPortal(CustomDefaultPO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      CustomDefaultPO updatePO = (CustomDefaultPO)this.session.load(CustomDefaultPO.class, id);
      updatePO.setPortal_id(po.getPortal_id());
      updatePO.setPortalname(po.getPortalname());
      updatePO.setViewgroup(po.getViewgroup());
      updatePO.setVieworg(po.getVieworg());
      updatePO.setViewrangename(po.getViewrangename());
      updatePO.setViewuser(po.getViewuser());
      this.session.update(updatePO);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean deleteCustomDeskUrl(Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      CustomurlPO po = (CustomurlPO)this.session.load(CustomurlPO.class, id);
      this.session.delete(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean deleteCustomDefault(Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      CustomDefaultPO po = (CustomDefaultPO)this.session.load(CustomDefaultPO.class, id);
      this.session.delete(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public String getSendDate(String processIds, String recordId) {
    String sendDate = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT WORKDONEWITHDATE FROM jsf_work WHERE workstatus=100 AND WORKPROCESS_ID IN(" + processIds + ") AND WORKRECORD_ID=" + recordId);
      if (rs.next()) {
        sendDate = rs.getString(1);
        if (sendDate.indexOf(" ") > 0)
          sendDate = sendDate.substring(0, sendDate.indexOf(" ")); 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return sendDate;
  }
  
  public String getAllSendFileProcessIds() {
    String processIds = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT wf_workflowprocess_id FROM jsf_workflowprocess wfp JOIN jsf_package p ON wfp.wf_package_id=p.wf_package_id WHERE wfp.WF_PACKAGE_ID=p.WF_PACKAGE_ID AND p.WF_MODULE_ID=2");
      while (rs.next())
        processIds = String.valueOf(processIds) + "," + rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception err) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      err.printStackTrace();
    } 
    return processIds;
  }
  
  private String getAllDocProcessIds(String moduleType) {
    String processIds = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "SELECT wf_workflowprocess_id FROM jsf_workflowprocess wfp JOIN jsf_package p ON wfp.wf_package_id=p.wf_package_id WHERE wfp.WF_PACKAGE_ID=p.WF_PACKAGE_ID ";
      if ("".equals(moduleType)) {
        sql = String.valueOf(sql) + " and (p.WF_MODULE_ID=2 or p.WF_MODULE_ID=3)";
      } else {
        sql = String.valueOf(sql) + " and p.WF_MODULE_ID=" + moduleType;
      } 
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        processIds = String.valueOf(processIds) + "," + rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception err) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      err.printStackTrace();
    } 
    return processIds;
  }
}
