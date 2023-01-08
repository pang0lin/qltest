package com.js.oa.security.ip.bean;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditIpPO;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.service.AuditIpBD;
import com.js.oa.audit.service.AuditLogBD;
import com.js.oa.security.ip.po.IPPO;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.FillBean;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.Query;

public class IPEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean add(IPPO ipPO) throws Exception {
    boolean result = false;
    StringBuffer ip = new StringBuffer(16);
    try {
      begin();
      String[] ipAddr = ipPO.getIpAddressBegin().split("\\.");
      int i;
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressBegin(ip.toString().substring(0, 15));
      ipAddr = ipPO.getIpAddressEnd().split("\\.");
      ip = new StringBuffer();
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressEnd(ip.toString().substring(0, 15));
      this.session.save(ipPO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean audit(IPPO ipPO, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
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
      auditLog.setAuditModule(new Long(4L));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditIpBD auditIpBD = new AuditIpBD();
      if ("delete".equals(opreate)) {
        begin();
        ipPO = (IPPO)this.session.load(IPPO.class, id);
      } 
      if ("pass".equals(opreate)) {
        begin();
        ipPO = (IPPO)this.session.load(IPPO.class, id);
        ipPO.setIpIsOpen((byte)1);
      } 
      StringBuffer ip = new StringBuffer(16);
      String[] ipAddr = ipPO.getIpAddressBegin().split("\\.");
      int i;
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressBegin(ip.toString().substring(0, 15));
      ipAddr = ipPO.getIpAddressEnd().split("\\.");
      ip = new StringBuffer();
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressEnd(ip.toString().substring(0, 15));
      AuditIpPO auditIpPO = (AuditIpPO)FillBean.transformOTO(ipPO, AuditIpPO.class);
      auditIpPO.setIpId(id.longValue());
      auditIpPO.setAuditLogId(auditLogId);
      auditIpPO.setOperationType(opreate);
      Long auditIpId = auditIpBD.saveAuditIp(auditIpPO);
      if ("insert".equals(opreate))
        opreate = "新增"; 
      if ("update".equals(opreate))
        opreate = "修改"; 
      if ("delete".equals(opreate))
        opreate = "删除"; 
      if ("pass".equals(opreate))
        opreate = "开通"; 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String userName = (String)httpsession.getAttribute("userName");
      msRemindBeann.auditMsRemind(Long.valueOf((String)httpsession.getAttribute("userId")).longValue(), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          1, 1, new Date(), "审计提醒:" + userName + opreate + "IP管理\"" + ipPO.getIpAddressBegin() + "-" + ipPO.getIpAddressEnd() + "\"", "audit", auditLogId.longValue(), "AuditIpAction.do?action=forshenji&id=" + auditLogId + "&flag=fromRemind");
      result = true;
    } catch (Exception e) {
      throw e;
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public boolean autoAudit(IPPO ipPO, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
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
      auditLog.setAuditModule(new Long(4L));
      auditLog.setAuditStatus(Integer.valueOf(1));
      auditLog.setIschecked(Integer.valueOf(1));
      auditLog.setCheckEmpid(new Long(0L));
      auditLog.setCheckEmpname("系统自动审核");
      auditLog.setCheckTime(ts);
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditIpBD auditIpBD = new AuditIpBD();
      if ("delete".equals(opreate)) {
        begin();
        ipPO = (IPPO)this.session.load(IPPO.class, id);
      } 
      if ("pass".equals(opreate)) {
        begin();
        ipPO = (IPPO)this.session.load(IPPO.class, id);
        ipPO.setIpIsOpen((byte)1);
      } 
      StringBuffer ip = new StringBuffer(16);
      String[] ipAddr = ipPO.getIpAddressBegin().split("\\.");
      int i;
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressBegin(ip.toString().substring(0, 15));
      ipAddr = ipPO.getIpAddressEnd().split("\\.");
      ip = new StringBuffer();
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressEnd(ip.toString().substring(0, 15));
      AuditIpPO auditIpPO = (AuditIpPO)FillBean.transformOTO(ipPO, AuditIpPO.class);
      auditIpPO.setIpId(id.longValue());
      auditIpPO.setAuditLogId(auditLogId);
      auditIpPO.setOperationType(opreate);
      Long auditIpId = auditIpBD.saveAuditIp(auditIpPO);
      result = true;
    } catch (Exception e) {
      throw e;
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public String delete(String id) throws Exception {
    String result = "";
    try {
      begin();
      Iterator<Object[]> it = this.session.createQuery("select po.ipAddressBegin,po.ipAddressEnd from com.js.oa.security.ip.po.IPPO  po where po.id=" + id).iterate();
      if (it.hasNext()) {
        Object[] obj = it.next();
        String ipb = getSimpleIP(obj[0].toString());
        String ipe = getSimpleIP(obj[1].toString());
        if (!ipb.equals(ipe)) {
          result = String.valueOf(ipb) + "至" + ipe;
        } else {
          result = ipb;
        } 
      } 
      this.session.delete("from com.js.oa.security.ip.po.IPPO  po where po.id=" + id);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List selectSingle(String id) throws Exception {
    List list = null;
    try {
      begin();
      Query query = this.session.createQuery("select po.ipAddressEnd,po.ipAddressBegin,po.ipOpenBeginTime,po.ipOpenEndTime,po.ipIsOpen,po.ipProposer,po.ipApplyTime from com.js.oa.security.ip.po.IPPO po  where po.id = " + 
          id);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean modify(IPPO ipPO) throws Exception {
    boolean result = false;
    StringBuffer ip = new StringBuffer();
    try {
      String[] ipAddr = ipPO.getIpAddressBegin().split("\\.");
      int i;
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressBegin(ip.toString().substring(0, 15));
      ipAddr = ipPO.getIpAddressEnd().split("\\.");
      ip = new StringBuffer();
      for (i = 0; i < 4; i++) {
        int len = 3 - ipAddr[i].length();
        while (len > 0) {
          ip.append("0");
          len--;
        } 
        ip.append(ipAddr[i]).append(".");
      } 
      ipPO.setIpAddressEnd(ip.toString().substring(0, 15));
      begin();
      IPPO oldPO = (IPPO)this.session.load(IPPO.class, new Long(ipPO.getId()));
      oldPO.setIpAddressBegin(ipPO.getIpAddressBegin());
      oldPO.setIpAddressEnd(ipPO.getIpAddressEnd());
      oldPO.setIpApplyTime(ipPO.getIpApplyTime());
      oldPO.setIpIsOpen(ipPO.getIpIsOpen());
      oldPO.setIpOpenBeginTime(ipPO.getIpOpenBeginTime());
      oldPO.setIpOpenEndTime(ipPO.getIpOpenEndTime());
      oldPO.setIpProposer(ipPO.getIpProposer());
      this.session.flush();
      result = true;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String delBatch(String checkboxIDS) throws Exception {
    String result = "";
    try {
      begin();
      if (checkboxIDS != null && !checkboxIDS.equals("")) {
        int i = 0;
        Iterator<Object[]> it = this.session.createQuery("select po.ipAddressBegin,po.ipAddressEnd from com.js.oa.security.ip.po.IPPO  po where po.id in (" + 
            checkboxIDS.substring(0, checkboxIDS.length() - 1) + ")").iterate();
        while (it.hasNext()) {
          Object[] obj = it.next();
          String ipb = getSimpleIP(obj[0].toString());
          String ipe = getSimpleIP(obj[1].toString());
          if (!ipb.equals(ipe))
            ipb = String.valueOf(ipb) + "至" + ipe; 
          if (i == 0) {
            result = ipb;
            continue;
          } 
          result = String.valueOf(result) + "," + ipb;
        } 
        this.session.delete(
            "from com.js.oa.security.ip.po.IPPO  po where po.id in (" + 
            checkboxIDS.substring(0, checkboxIDS.length() - 1) + 
            ")");
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean delAll() throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.delete("from com.js.oa.security.ip.po.IPPO  po");
      this.session.flush();
      result = true;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String pass(String ids) throws Exception {
    String result = "";
    try {
      begin();
      List<IPPO> list = this.session.createQuery("select po from com.js.oa.security.ip.po.IPPO  po where po.id in (" + 
          ids.substring(0, ids.length() - 1) + ")").list();
      IPPO ipPO = null;
      for (int i = 0; i < list.size(); i++) {
        ipPO = list.get(i);
        ipPO.setIpIsOpen((new Byte("1")).byteValue());
        String ipb = getSimpleIP(ipPO.getIpAddressBegin());
        String ipe = getSimpleIP(ipPO.getIpAddressEnd());
        if (!ipb.equals(ipe))
          ipb = String.valueOf(ipb) + "至" + ipe; 
        if (i == 0) {
          result = ipb;
        } else {
          result = String.valueOf(result) + "," + ipb;
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  private String getSimpleIP(String ip) {
    if (ip != null) {
      if (ip.charAt(0) == '0')
        ip = ip.substring(1, ip.length()); 
      if (ip.charAt(0) == '0')
        ip = ip.substring(1, ip.length()); 
      ip = ip.replaceAll(".0", ".");
      ip = ip.replaceAll(".0", ".");
    } 
    return ip;
  }
}
