package com.js.oa.info.infomanager.bean;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.infomanager.po.AssociateInfoPO;
import com.js.oa.info.infomanager.po.InforHistoryAccessoryPO;
import com.js.oa.info.infomanager.po.InforOrgStatPO;
import com.js.oa.info.infomanager.po.InforPersonalStatPO;
import com.js.oa.info.infomanager.po.InformationAccessoryPO;
import com.js.oa.info.infomanager.po.InformationBrowserPO;
import com.js.oa.info.infomanager.po.InformationCommentPO;
import com.js.oa.info.infomanager.po.InformationHistoryPO;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.isodoc.po.IsoBorrowUserPO;
import com.js.oa.info.isodoc.po.IsoCommentPO;
import com.js.oa.info.isodoc.po.IsoDeallogPO;
import com.js.oa.info.isodoc.po.IsoPaperPO;
import com.js.oa.search.client.SearchService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import com.js.util.util.InfoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.sql.DataSource;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;

public class InformationEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getSingleInfo(String informationId, String channelId) throws Exception {
    List<String> list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.informationTitle, aaa.informationSubTitle, aaa.informationContent, aaa.informationIssuer, aaa.informationIssueOrg, aaa.informationIssueTime, aaa.informationModifyTime, aaa.informationVersion, aaa.informationAuthor, aaa.informationMark, aaa.informationHeadFile, aaa.informationSeal, aaa.infoRedIssueOrg, aaa.infoRedIssueTime, aaa.informationSummary, aaa.informationKey, aaa.informationReaderName, aaa.informationReader, aaa.informationReaderOrg, aaa.informationReaderGroup, aaa.informationValidType,aaa.validBeginTime, aaa.validEndTime, aaa.informationHead,aaa.informationHeadId, aaa.informationSealId, aaa.forbidCopy,aaa.transmitToWebsite, aaa.infoDepaFlag,aaa.infoDepaFlag2, aaa.orderCode, aaa.displayTitle, aaa.otherChannel,aaa.titleColor,aaa.showSign,aaa.showSignName, aaa.modifyEmp, aaa.dossierStatus, aaa.mustRead,aaa.comeFrom,aaa.isConf,aaa.documentNo,aaa.documentEditor,aaa.documentType, aaa.displayImage ,aaa.afficeHistoryDate,aaa.wordDisplayType,bbb.channelReader,bbb.channelReaderOrg,bbb.channelReaderGroup, aaa.informationOrISODoc,aaa.isoDocStatus,aaa.isoOldInfoId,aaa.isoSecretStatus, aaa.isoDealCategory,aaa.isoApplyName,aaa.isoApplyId, aaa.isoReceiveName,aaa.isoReceiveId,aaa.isoModifyReason ,aaa.isoAmendmentPage,aaa.isoModifyVersion,aaa.inforModifyMen,aaa.inforModifyOrg,aaa.isAllow,bbb.isAllowReview  from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where aaa.informationId = " + 





















          
          informationId);
      list = query.list();
      if (list.size() > 0 && channelId != null) {
        String channelName = "知识管理";
        List<Object[]> array = new ArrayList();
        array = this.session.createQuery("select ch.channelType,ch.channelIdString,ch.userDefine from com.js.oa.info.channelmanager.po.InformationChannelPO ch where ch.channelId=" + channelId).list();
        if (array != null && array.size() > 0) {
          Object[] obj = array.get(0);
          String chType = obj[0].toString();
          String chIdString = obj[1].toString();
          String userDefine = obj[2].toString();
          if ("1".equals(userDefine)) {
            channelName = this.session.createQuery("select po.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO po where po.userChannelId=" + chType).iterate().next().toString();
          } else if (Integer.parseInt(chType) > 0) {
            channelName = this.session.createQuery("select po.orgName from com.js.system.vo.organizationmanager.OrganizationVO po where po.orgLevel=" + chType).iterate().next().toString();
          } 
          String databaseType = 
            SystemCommon.getDatabaseType();
          Iterator<E> it = null;
          if (databaseType.indexOf("mysql") >= 0) {
            it = this.session.createQuery("select po.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO po where '" + chIdString + "' like concat('%', po.channelId, '%') order by po.channelLevel")
              .iterate();
          } else {
            it = this.session.createQuery("select po.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO po where '" + chIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', po.channelId), '%') order by po.channelLevel")
              .iterate();
          } 
          if (it != null)
            while (it.hasNext())
              channelName = String.valueOf(channelName) + "." + it.next().toString();  
        } 
        list.add(channelName);
      } 
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
  
  public void setBrowser(String userId, String userName, String orgName, String informationId, String orgIdString) throws Exception {
    begin();
    try {
      Query query = this.session.createQuery("select count(aaa.browserId) from com.js.oa.info.infomanager.po.InformationBrowserPO aaa join aaa.information bbb where aaa.empId = " + 
          userId + 
          " and bbb.informationId = " + 
          informationId);
      int count = ((Integer)query.iterate().next()).intValue();
      if (count == 0) {
        InformationBrowserPO informationBrowserPO = 
          new InformationBrowserPO();
        informationBrowserPO.setEmpId(new Long(userId));
        informationBrowserPO.setBrowserName(userName);
        informationBrowserPO.setBrowserOrgName(orgName);
        informationBrowserPO.setBrowserOrgIdStr(orgIdString);
        informationBrowserPO.setBrowseTime(new Date());
        Long browserId = (Long)this.session.save(informationBrowserPO);
        this.session.flush();
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(
            "update JSDB.oa_informationBrowser set information_id=" + 
            informationId + " where browser_id=" + browserId);
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void setBrowserKits(String userId, String userName, String orgName, String informationId, String orgIdString, String domainId) throws Exception {
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement("select count(browser_id) from oa_informationbrowser where emp_id=? and information_id=?");
      pstmt.setString(1, userId);
      pstmt.setString(2, informationId);
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      int count = rs.getInt(1);
      rs.close();
      if (count == 0) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0) {
          pstmt = conn.prepareStatement("insert into oa_informationbrowser(browser_id,information_id,emp_id,browsername,browserorgname,browsetime,browserorgidstr,domain_id) values( hibernate_sequence.nextval,?,?,?,?,sysdate,?,?)");
          pstmt.setString(1, informationId);
          pstmt.setString(2, userId);
          pstmt.setString(3, userName);
          pstmt.setString(4, orgName);
          pstmt.setString(5, orgIdString);
          pstmt.setString(6, domainId);
        } else if (databaseType.indexOf("mysql") >= 0) {
          pstmt = conn.prepareStatement("insert into oa_informationbrowser(information_id,emp_id,browsername,browserorgname,browsetime,browserorgidstr,domain_id) values(?,?,?,?,now(),?,?)");
          pstmt.setString(1, informationId);
          pstmt.setString(2, userId);
          pstmt.setString(3, userName);
          pstmt.setString(4, orgName);
          pstmt.setString(5, orgIdString);
          pstmt.setString(6, domainId);
        } else {
          pstmt = conn.prepareStatement("insert into oa_informationbrowser(information_id,emp_id,browsername,browserorgname,browsetime,browserorgidstr,domain_id) values(?,?,?,?,date(),?,?)");
          pstmt.setString(1, informationId);
          pstmt.setString(2, userId);
          pstmt.setString(3, userName);
          pstmt.setString(4, orgName);
          pstmt.setString(5, orgIdString);
          pstmt.setString(6, domainId);
        } 
        pstmt.executeUpdate();
      } 
      pstmt = conn.prepareStatement("update oa_information set informationKits=informationKits+1 where information_id=?");
      pstmt.setString(1, informationId);
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
    } 
  }
  
  public List getBrowser(String informationId, String searchName) throws Exception {
    List list = null;
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.browserName,aaa.browserOrgName,emp.userAccounts from com.js.oa.info.infomanager.po.InformationBrowserPO aaa join aaa.information bbb,com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=aaa.empId and bbb.informationId = " + 
          informationId + 
          " and aaa.browserName like concat('%', '" + searchName + 
          "', '%') order by aaa.browserOrgIdStr";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select aaa.browserName,aaa.browserOrgName,emp.userAccounts from com.js.oa.info.infomanager.po.InformationBrowserPO aaa join aaa.information bbb,com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=aaa.empId and bbb.informationId = " + 
          informationId + 
          " and aaa.browserName like '%" + 
          searchName + "%' order by aaa.browserOrgIdStr";
      } else {
        tmpSql = "select aaa.browserName,aaa.browserOrgName,emp.userAccounts from com.js.oa.info.infomanager.po.InformationBrowserPO aaa join aaa.information bbb,com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=aaa.empId and bbb.informationId = " + 
          informationId + 
          " and aaa.browserName like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', '" + 
          searchName + "'), '%') order by aaa.browserOrgIdStr";
      } 
      Query query = this.session.createQuery(tmpSql);
      list = query.list();
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
  
  public List getchannleinfo(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.channelId,aaa.channelName,bbb.isAllow from com.js.oa.info.channelmanager.po.InformationChannelPO aaa join aaa.information bbb where bbb.informationId = " + 
          informationId);
      list = query.list();
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
  
  public List getOrgName(String channelId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select org.orgName, ch.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO ch,com.js.system.vo.organizationmanager.OrganizationVO org where ch.channelType=org.orgId and ch.channelType>0 and ch.userDefine=0 and ch.channelId=" + 
          channelId);
      list = query.list();
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
  
  public List getAllOrgName(String flag) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select org.orgName, ch.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO ch,com.js.system.vo.organizationmanager.OrganizationVO org where ch.channelType=org.orgId and ch.channelType>0 and ch.userDefine=0 order by org.orgName");
      list = query.list();
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
  
  public boolean informationStatus(String informationId) throws Exception {
    boolean result = false;
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") >= 0) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        boolean status = conn.getAutoCommit();
        conn.setAutoCommit(false);
        String sql = "update oa_information set InformationStatus=0,InformationModifyTime=sysdate where information_id=" + informationId;
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.commit();
        conn.setAutoCommit(status);
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (SQLException err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else {
      begin();
      try {
        InformationPO informationPO = (InformationPO)this.session.load(
            InformationPO.class, new Long(informationId));
        informationPO.setInformationStatus(0);
        informationPO.setInformationModifyTime(new Date());
        this.session.flush();
        result = true;
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally {
        this.session.close();
        this.session = null;
        this.transaction = null;
      } 
    } 
    return result;
  }
  
  public List getHistoryVersion(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.historyVersion,aaa.historyIssueOrg,aaa.historyIssuerName,aaa.historyTime,aaa.historyId, aaa.historyHead,ccc.channelId,ccc.channelType,aaa.isoDealCategory,aaa.isoAmendmentPage,aaa.isoModifyReason from com.js.oa.info.infomanager.po.InformationHistoryPO aaa join aaa.information bbb join bbb.informationChannel ccc where bbb.informationId = " + 


          
          informationId + 
          " order by aaa.historyTime desc ");
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
  
  public List getComment(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.commentIssuerOrg,aaa.commentIssuerName,aaa.commentIssueTime,aaa.commentContent,aaa.commentId,aaa.commentIssuerId  from com.js.oa.info.infomanager.po.InformationCommentPO aaa join aaa.information bbb where bbb.informationId = " + 
          informationId + 
          " order by aaa.commentId desc ");
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
  
  public List getOrderedComment(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.commentIssuerOrg,aaa.commentIssuerName,aaa.commentIssueTime,aaa.commentContent,aaa.commentId,aaa.commentIssuerId,aaa.commentParentId  from com.js.oa.info.infomanager.po.InformationCommentPO aaa join aaa.information bbb where bbb.informationId = " + 
          informationId + 
          " order by aaa.layer ");
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
  
  public List getinformation(String informationId) throws Exception {
    List contentID = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.informationType,aaa.informationKey,aaa.isAllow from com.js.oa.info.infomanager.po.InformationPO aaa where aaa.informationId = " + 
          informationId);
      contentID = query.list();
    } catch (Exception e) {
      e.getMessage();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return contentID;
  }
  
  public List getinformationID(String informationId_same, String informationId, String domainId) throws Exception {
    List contentID_same = null;
    begin();
    try {
      Query query = this.session.createQuery("select info.informationTitle,info.informationId,info.informationHead,ch.channelId,ch.channelName ,info.informationType from com.js.oa.info.infomanager.po.InformationPO info join info.informationChannel ch where info.domainId=" + 
          domainId + 
          " and info.informationKey = '" + 
          informationId_same + 
          "' and info.informationId <> " + 
          informationId);
      contentID_same = query.list();
    } catch (Exception e) {
      e.getMessage();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return contentID_same;
  }
  
  public void setComment(String userId, String userName, String orgName, String content, String informationId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    int commonNum = 0;
    ResultSet rs = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    boolean status = conn.getAutoCommit();
    conn.setAutoCommit(false);
    stmt = conn.createStatement();
    try {
      rs = stmt.executeQuery("select InformationCommonNum from oa_information where information_id=" + informationId);
      if (rs.next())
        commonNum = rs.getInt(1) + 1; 
      rs.close();
      stmt.executeUpdate("update oa_information set InformationCommonNum=" + commonNum + " where information_id=" + informationId);
      begin();
      InformationCommentPO informationCommentPO = 
        new InformationCommentPO();
      informationCommentPO.setCommentIssuerId(new Long(userId));
      informationCommentPO.setCommentIssuerName(userName);
      informationCommentPO.setCommentIssuerOrg(orgName);
      informationCommentPO.setCommentContent(content);
      informationCommentPO.setCommentIssueTime(new Date());
      Long commentId = (Long)this.session.save(informationCommentPO);
      informationCommentPO.setLayer(String.valueOf(commentId));
      this.session.save(informationCommentPO);
      this.session.flush();
      stmt.executeUpdate("update JSDB.oa_informationcomment set information_id = " + informationId + " where comment_id = " + commentId);
      conn.commit();
      conn.setAutoCommit(status);
      stmt.close();
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void setComment(String userId, String userName, String orgName, String content, String informationId, String commentParentId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    int commonNum = 0;
    ResultSet rs = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    boolean status = conn.getAutoCommit();
    conn.setAutoCommit(false);
    stmt = conn.createStatement();
    try {
      rs = stmt.executeQuery("select InformationCommonNum from oa_information where information_id=" + informationId);
      if (rs.next())
        commonNum = rs.getInt(1) + 1; 
      rs.close();
      stmt.executeUpdate("update oa_information set InformationCommonNum=" + commonNum + " where information_id=" + informationId);
      begin();
      InformationCommentPO informationCommentPO = 
        new InformationCommentPO();
      informationCommentPO.setCommentIssuerId(new Long(userId));
      informationCommentPO.setCommentIssuerName(userName);
      informationCommentPO.setCommentIssuerOrg(orgName);
      informationCommentPO.setCommentContent(content);
      informationCommentPO.setCommentIssueTime(new Date());
      informationCommentPO.setCommentParentId((commentParentId == null || "".equals(commentParentId)) ? null : Long.valueOf(commentParentId));
      Long commentId = (Long)this.session.save(informationCommentPO);
      String layer = null;
      if (commentParentId == null || "".equals(commentParentId)) {
        layer = String.valueOf(commentId);
      } else {
        layer = String.valueOf(commentParentId) + "-" + commentId;
      } 
      informationCommentPO.setLayer(layer);
      this.session.save(informationCommentPO);
      this.session.flush();
      stmt.executeUpdate("update JSDB.oa_informationcomment set information_id = " + informationId + " where comment_id = " + commentId);
      conn.commit();
      conn.setAutoCommit(status);
      stmt.close();
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void updateComment(String content, String commentId) throws Exception {
    begin();
    try {
      InformationCommentPO informationCommentPO = (InformationCommentPO)this.session.load(
          InformationCommentPO.class, new Long(commentId), 
          LockMode.UPGRADE);
      informationCommentPO.setCommentContent(content);
      informationCommentPO.setCommentIssueTime(new Date());
      this.session.update(informationCommentPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void setKits(String informationId) throws Exception {
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") >= 0) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        boolean status = conn.getAutoCommit();
        conn.setAutoCommit(false);
        String sql = "update oa_information set informationKits=informationKits+1 where information_id=" + informationId;
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.commit();
        conn.setAutoCommit(status);
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (SQLException err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else {
      begin();
      InformationPO informationPO = new InformationPO();
      try {
        informationPO = (InformationPO)this.session.load(InformationPO.class, 
            new Long(informationId), LockMode.UPGRADE);
        informationPO.setInformationKits(informationPO
            .getInformationKits() + 1);
        this.session.flush();
      } catch (Exception e) {
        throw e;
      } finally {
        this.session.close();
        this.session = null;
        this.transaction = null;
      } 
    } 
  }
  
  public void saveHistory(String informationId) throws Exception {
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(
          InformationPO.class, new Long(informationId), 
          LockMode.UPGRADE);
      InformationHistoryPO informationHistoryPO = 
        new InformationHistoryPO();
      informationHistoryPO.setHistoryTitle(informationPO
          .getInformationTitle());
      informationHistoryPO.setHistorySubTitle(informationPO
          .getInformationSubTitle());
      informationHistoryPO.setHistorySummary(informationPO
          .getInformationSummary());
      informationHistoryPO.setHistoryContent(informationPO
          .getInformationContent());
      informationHistoryPO.setHistoryIssuerId(informationPO
          .getInformationIssuerId());
      informationHistoryPO.setHistoryIssuerName(informationPO
          .getInformationIssuer());
      informationHistoryPO.setHistoryIssueOrg(informationPO
          .getInformationIssueOrg());
      informationHistoryPO.setHistoryTime(informationPO
          .getInformationModifyTime());
      informationHistoryPO.setHistoryVersion(informationPO
          .getInformationVersion());
      informationHistoryPO.setHistoryKey(informationPO.getInformationKey());
      informationHistoryPO.setHistoryHead(informationPO
          .getInformationHead());
      informationHistoryPO.setHistoryHeadFile(informationPO
          .getInformationHeadFile());
      informationHistoryPO.setHistoryRedIssueTime(informationPO
          .getInfoRedIssueTime());
      informationHistoryPO.setHistoryRedIssueOrg(informationPO
          .getInfoRedIssueOrg());
      informationHistoryPO.setHistorySeal(informationPO
          .getInformationSeal());
      informationHistoryPO.setHistoryMark(informationPO
          .getInformationMark());
      informationHistoryPO.setHistoryAuthor(informationPO
          .getInformationAuthor());
      informationHistoryPO.setDomainId(informationPO.getDomainId());
      informationHistoryPO.setInformation(informationPO);
      informationHistoryPO.setHisDisplayImage(informationPO
          .getDisplayImage());
      if (informationPO.getIsoDealCategory() != null && 
        !informationPO.getIsoDealCategory().toString().equals("null")) {
        informationHistoryPO.setIsoDealCategory(informationPO
            .getIsoDealCategory());
      } else {
        informationHistoryPO.setIsoDealCategory("");
      } 
      if (informationPO.getIsoAmendmentPage() != null) {
        informationHistoryPO.setIsoAmendmentPage(informationPO
            .getIsoAmendmentPage());
      } else {
        informationHistoryPO.setIsoAmendmentPage("");
      } 
      if (informationPO.getIsoModifyReason() != null && 
        !informationPO.getIsoModifyReason().toString().equals("null")) {
        informationHistoryPO.setIsoModifyReason(informationPO
            .getIsoModifyReason());
      } else {
        informationHistoryPO.setIsoModifyReason("");
      } 
      informationHistoryPO.setHistoryIssuerName(informationPO
          .getInforModifyMen());
      informationHistoryPO.setHistoryIssueOrg(informationPO
          .getInforModifyOrg());
      this.session.save(informationHistoryPO);
      Query query = this.session.createQuery(" select aaa.accessoryIsImage,aaa.accessoryType,aaa.accessoryName,aaa.accessorySaveName,aaa.domainId  from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa  join aaa.information bbb where bbb.informationId = " + 

          
          informationId);
      List<Object[]> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        InforHistoryAccessoryPO inforHistoryAccessoryPO = 
          new InforHistoryAccessoryPO();
        inforHistoryAccessoryPO.setAccessoryIsImage(Integer.parseInt(
              obj[0].toString()));
        inforHistoryAccessoryPO.setAccessoryType(obj[1].toString());
        inforHistoryAccessoryPO.setAccessoryName(obj[2].toString());
        inforHistoryAccessoryPO.setAccessorySaveName(obj[3].toString());
        inforHistoryAccessoryPO.setDomainId(informationPO.getDomainId());
        inforHistoryAccessoryPO.setInformationHistory(
            informationHistoryPO);
        this.session.save(inforHistoryAccessoryPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void update(String informationId, String[] parameters, String[] assoInfo, String[] infoAppendName, String[] infoAppendSaveName, String[] infoPicName, String[] infoPicSaveName) throws Exception {
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(
          InformationPO.class, new Long(informationId));
      InformationChannelPO channelPO = (InformationChannelPO)this.session
        .load(InformationChannelPO.class, 
          new Long(parameters[31]));
      String version = informationPO.getInformationVersion();
      String informationType = informationPO.getInformationType();
      if (parameters[41] != null && parameters[41].toString().equals("1")) {
        if (!"3".equals((new StringBuilder(String.valueOf(parameters[45]))).toString()) && !"4".equals((new StringBuilder(String.valueOf(parameters[45]))).toString())) {
          int a = Integer.parseInt(version.substring(0, 
                version.indexOf(".")));
          version = String.valueOf(a + 1) + ".0";
        } 
      } else {
        int a = Integer.parseInt(version.substring(0, 
              version.indexOf(".")));
        int b = Integer.parseInt(version.substring(version.indexOf(".") + 
              1, version.length()));
        if (b + 1 == 0) {
          version = String.valueOf(a + 1) + ".0";
        } else {
          version = String.valueOf(a) + "." + (b + 1);
        } 
      } 
      String databaseType = SystemCommon.getDatabaseType();
      informationPO.setInformationChannel(channelPO);
      informationPO.setInformationVersion(version);
      informationPO.setInformationSubTitle(parameters[1]);
      informationPO.setInformationSummary(parameters[2]);
      informationPO.setInformationKey(parameters[3]);
      if ("3".equals(informationType)) {
        informationPO.setInformationTitle(parameters[4].split(":")[0]);
        informationPO.setInformationHeadFile(parameters[4].split(":")[1]);
        informationPO.setInformationContent("");
      } else if ("6".equals(informationType)) {
        informationPO.setInformationTitle(parameters[0]);
        informationPO.setInformationContent("");
      } else {
        informationPO.setInformationTitle(parameters[0]);
        if (databaseType.indexOf("oracle") >= 0) {
          informationPO.setInformationContent("");
        } else {
          informationPO.setInformationContent(parameters[4]);
        } 
        informationPO.setInformationHeadFile(parameters[16]);
      } 
      informationPO.setModifyEmp(String.valueOf(parameters[7]) + "." + parameters[6]);
      informationPO.setInformationModifyTime(new Date());
      informationPO.setInformationReaderName(parameters[8]);
      informationPO.setInformationReader(parameters[9]);
      informationPO.setInformationReaderOrg(parameters[10]);
      informationPO.setInformationReaderGroup(parameters[11]);
      informationPO.setInformationValidType(Integer.parseInt(parameters[
              12]));
      informationPO.setValidBeginTime(new Date(parameters[13]));
      informationPO.setValidEndTime(new Date(parameters[14]));
      if (parameters[15] != null && !parameters[15].equals(""))
        informationPO.setInformationHead(Integer.parseInt(parameters[15])); 
      informationPO.setInformationSeal(parameters[17]);
      informationPO.setInformationMark(parameters[18]);
      informationPO.setInfoRedIssueOrg(parameters[19]);
      informationPO.setInfoRedIssueTime(parameters[20]);
      informationPO.setInformationHeadId(new Long(parameters[21]));
      informationPO.setInformationSealId(new Long(parameters[22]));
      informationPO.setTransmitToWebsite(Integer.parseInt(parameters[23]));
      informationPO.setForbidCopy(Integer.parseInt(parameters[24]));
      informationPO.setInformationAuthor(parameters[28]);
      if (parameters[29] != null) {
        Calendar IssueTime = Calendar.getInstance();
        IssueTime.setTime(informationPO.getInformationIssueTime());
        String[] issueTime = parameters[29].split("/");
        IssueTime.set(Integer.parseInt(issueTime[0]), 
            Integer.parseInt(issueTime[1]) - 1, 
            Integer.parseInt(issueTime[2]));
        informationPO.setInformationIssueTime(IssueTime.getTime());
      } 
      informationPO.setTitleColor(new Integer(parameters[30]));
      if (parameters[25].equals("")) {
        informationPO.setOrderCode("1000");
      } else {
        informationPO.setOrderCode(parameters[25]);
      } 
      informationPO.setDisplayTitle(Integer.parseInt(parameters[26]));
      informationPO.setOtherChannel(parameters[27]);
      informationPO.setMustRead(Integer.valueOf(parameters[32]));
      informationPO.setComeFrom(parameters[33]);
      if (parameters.length > 34)
        if (parameters[34] != null) {
          informationPO.setIsConf(Integer.valueOf("1"));
        } else {
          informationPO.setIsConf(Integer.valueOf("0"));
        }  
      if (parameters.length > 35)
        if (parameters[35] != null) {
          informationPO.setDocumentNo(parameters[35]);
        } else {
          informationPO.setDocumentNo("0");
        }  
      if (parameters.length > 36)
        informationPO.setDocumentEditor(parameters[36]); 
      if (parameters.length > 37)
        informationPO.setDocumentType(parameters[37]); 
      if (parameters.length > 38)
        informationPO.setDisplayImage(parameters[38]); 
      if (parameters.length > 39)
        informationPO.setWordDisplayType(parameters[39]); 
      if (parameters.length > 41)
        informationPO.setInformationOrISODoc(parameters[41]); 
      if (parameters.length > 42)
        informationPO.setIsoDocStatus(parameters[42]); 
      if (parameters.length > 43)
        informationPO.setIsoOldInfoId(parameters[43]); 
      if (parameters.length > 44)
        informationPO.setIsoSecretStatus(parameters[44]); 
      if (parameters.length > 45)
        informationPO.setIsoDealCategory(parameters[45]); 
      if (parameters.length > 46)
        informationPO.setIsoApplyName(parameters[46]); 
      if (parameters.length > 47)
        informationPO.setIsoApplyId(parameters[47]); 
      if (parameters.length > 48)
        informationPO.setIsoReceiveName(parameters[48]); 
      if (parameters.length > 49)
        informationPO.setIsoReceiveId(parameters[49]); 
      if (parameters.length > 50)
        informationPO.setIsoModifyReason(parameters[50]); 
      if (parameters.length > 51)
        if (parameters[51] != null && 
          !parameters[51].toString().equals("null")) {
          informationPO.setIsoAmendmentPage(parameters[51]);
        } else {
          informationPO.setIsoAmendmentPage("");
        }  
      if (parameters.length > 52)
        informationPO.setIsoModifyVersion(parameters[52]); 
      if (parameters.length > 53)
        informationPO.setInforModifyMen(parameters[53]); 
      if (parameters.length > 54)
        informationPO.setInforModifyOrg(parameters[54]); 
      informationPO.setIsAllow(parameters[55]);
      Set accessory = informationPO.getInformationAccessory();
      informationPO.setInformationAccessory(null);
      Iterator<InformationAccessoryPO> iter = accessory.iterator();
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      HashSet<InformationAccessoryPO> acceSet = new HashSet();
      if (infoAppendName != null)
        for (int i = 0; i < infoAppendName.length; i++) {
          InformationAccessoryPO accePO = new InformationAccessoryPO();
          accePO.setAccessoryIsImage(0);
          accePO.setAccessoryName(infoAppendName[i]);
          accePO.setAccessorySaveName(infoAppendSaveName[i]);
          if (infoAppendSaveName[i].indexOf(".") > 0) {
            accePO.setAccessoryType(infoAppendSaveName[i].substring(
                  infoAppendSaveName[i].indexOf(".") + 1));
          } else {
            accePO.setAccessoryType("");
          } 
          accePO.setInformation(informationPO);
          acceSet.add(accePO);
          this.session.save(accePO);
        }  
      if (infoPicName != null)
        for (int i = 0; i < infoPicName.length; i++) {
          InformationAccessoryPO accePO = new InformationAccessoryPO();
          accePO.setAccessoryIsImage(1);
          accePO.setAccessoryName(infoPicName[i]);
          accePO.setAccessorySaveName(infoPicSaveName[i]);
          if (infoPicSaveName[i].indexOf(".") > 0) {
            accePO.setAccessoryType(infoPicSaveName[i].substring(
                  infoPicSaveName[i].indexOf(".") + 1));
          } else {
            accePO.setAccessoryType("");
          } 
          accePO.setInformation(informationPO);
          acceSet.add(accePO);
          this.session.save(accePO);
        }  
      informationPO.setInformationAccessory(acceSet);
      this.session.update(informationPO);
      this.session.flush();
      if (databaseType.indexOf("oracle") >= 0 && !"3".equals(informationType))
        InfoUtil.insertInfoContent_oracle(informationPO.getInformationId(), parameters[4]); 
      this.session.delete(
          "from com.js.oa.info.infomanager.po.AssociateInfoPO aaa  where aaa.masterInfo = " + 
          informationId);
      if (assoInfo != null)
        for (int j = 0; j < assoInfo.length; j++) {
          AssociateInfoPO assoPO = new AssociateInfoPO();
          assoPO.setAssociateInfo(new Long(assoInfo[j]));
          assoPO.setMasterInfo(new Long(informationId));
          this.session.save(assoPO);
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void deleteAccessory(String informationId, String accessory) throws Exception {
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(
          InformationPO.class, new Long(informationId), 
          LockMode.UPGRADE);
      Set acceSet = informationPO.getInformationAccessory();
      InformationAccessoryPO accessoryPO = 
        (InformationAccessoryPO)this.session.load(
          InformationAccessoryPO.class, new Long(accessory), 
          LockMode.UPGRADE);
      acceSet.remove(accessoryPO);
      informationPO.setInformationAccessory(acceSet);
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0)
        InfoUtil.insertInfoContent_oracle(informationPO.getInformationId(), informationPO.getInformationContent()); 
      this.session.delete(accessoryPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getSingleHistInfo(String historyId) throws Exception {
    List<String> list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.historyTitle,aaa.historySubTitle,aaa.historyTitle,  aaa.historyIssuerName,aaa.historyIssueOrg,aaa.historyTime,  bbb.informationModifyTime,aaa.historyVersion,aaa.historyAuthor,  aaa.historyMark,aaa.historyHeadFile,aaa.historySeal,  aaa.historyRedIssueOrg,aaa.historyRedIssueTime,aaa.historySummary,  aaa.historyKey, bbb.forbidCopy, bbb.infoDepaFlag, bbb.infoDepaFlag2, bbb.displayTitle,aaa.hisDisplayImage from  com.js.oa.info.infomanager.po.InformationHistoryPO aaa  join aaa.information bbb where aaa.historyId = " + 






          
          historyId);
      list = query.list();
      query = this.session.createQuery("select aaa.historyContent from com.js.oa.info.infomanager.po.InformationHistoryPO aaa where aaa.historyId = " + 
          historyId);
      String content = query.iterate().next();
      list.add(content);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public void commend(String[] batchId) throws Exception {
    begin();
    try {
      InformationPO informationPO = new InformationPO();
      for (int i = 0; i < batchId.length; i++) {
        informationPO = (InformationPO)this.session.load(InformationPO.class, 
            new Long(batchId[i]), LockMode.UPGRADE);
        informationPO.setInformationIsCommend(new Long("1"));
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void removeCommend(String id) throws Exception {
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") >= 0) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        boolean status = conn.getAutoCommit();
        conn.setAutoCommit(false);
        String sql = "update oa_information set InformationIsCommend=0 where information_id=" + id;
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.commit();
        conn.setAutoCommit(status);
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (SQLException err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else {
      begin();
      try {
        InformationPO informationPO = new InformationPO();
        informationPO = (InformationPO)this.session.load(InformationPO.class, 
            new Long(id), LockMode.UPGRADE);
        informationPO.setInformationIsCommend(new Long("0"));
        this.session.flush();
      } catch (Exception e) {
        throw e;
      } finally {
        this.session.close();
        this.session = null;
        this.transaction = null;
      } 
    } 
  }
  
  public List batchDelete(String[] batchId) throws Exception {
    List<String> result = null;
    StringBuffer sb = new StringBuffer(batchId.length);
    for (int i = 0; i < batchId.length; i++)
      sb.append(String.valueOf(batchId[i]) + ","); 
    String tmp = sb.substring(0, sb.length() - 1).toString();
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InformationAccessoryPO aaa  join aaa.information bbb where bbb.informationId in (" + 

          
          tmp + ")");
      result = query.list();
      query = this.session.createQuery(" select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InforHistoryAccessoryPO aaa join  aaa.informationHistory bbb join bbb.information ccc where  ccc.informationId in (" + 

          
          tmp + ")");
      List list2 = query.list();
      for (int j = 0; j < list2.size(); j++)
        result.add(list2.get(j)); 
      StringBuffer buffer = new StringBuffer();
      Iterator it = this.session.createQuery("select info.informationTitle from com.js.oa.info.infomanager.po.InformationPO info where info.informationId in(" + tmp + ")").iterate();
      while (it.hasNext())
        buffer.append(it.next()).append(","); 
      result.add(buffer.toString());
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "DELETE from JSDB.OA_INFORMATION WHERE INFORMATION_ID IN (" + 
          tmp + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List allDelete(String channelId) throws Exception {
    List result = null;
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.informationId from  com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb where bbb.channelId = " + 

          
          channelId);
      List<E> list = query.list();
      StringBuffer sb = new StringBuffer(list.size());
      for (int i = 0; i < list.size(); i++)
        sb.append((new StringBuilder()).append(list.get(i)).append(",").toString()); 
      String tmp = sb.substring(0, sb.length() - 1).toString();
      query = this.session.createQuery(" select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InformationAccessoryPO aaa  join aaa.information bbb where bbb.informationId in (" + 

          
          tmp + ")");
      result = query.list();
      query = this.session.createQuery(" select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InforHistoryAccessoryPO aaa join  aaa.informationHistory bbb join bbb.information ccc where  ccc.informationId in (" + 

          
          tmp + ")");
      list = query.list();
      for (int j = 0; j < list.size(); j++)
        result.add(list.get(j)); 
      InformationPO infoPO = null;
      Set histInfo = null;
      Set<InformationAccessoryPO> infoAcce = null;
      Iterator<InformationAccessoryPO> iter = null;
      for (int k = 0; k < list.size(); k++) {
        infoPO = (InformationPO)this.session.load(InformationPO.class, 
            new Long(list.get(k).toString()), LockMode.UPGRADE);
        histInfo = infoPO.getInformationHistory();
        if (histInfo != null) {
          iter = histInfo.iterator();
          while (iter.hasNext())
            this.session.delete(iter.next()); 
        } 
        infoAcce = infoPO.getInformationAccessory();
        if (infoAcce != null) {
          iter = infoAcce.iterator();
          while (iter.hasNext())
            this.session.delete(iter.next()); 
        } 
        infoPO.setInformationHistory(null);
        infoPO.setInformationAccessory(null);
        this.session.delete(infoPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List singleDelete(String channelId, String informationId) throws Exception {
    List<String> result = null;
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs1 = stmt.executeQuery(
          "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=21");
      String tableId = "0";
      if (rs1.next())
        tableId = rs1.getString(1); 
      rs1.close();
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID=" + informationId);
      Query query = this.session.createQuery(
          " select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InformationAccessoryPO aaa  join aaa.information bbb where bbb.informationId = " + 

          
          informationId);
      result = query.list();
      query = this.session.createQuery(" select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InforHistoryAccessoryPO aaa join  aaa.informationHistory bbb join bbb.information ccc where  ccc.informationId = " + 

          
          informationId);
      List list = query.list();
      for (int i = 0; i < list.size(); i++)
        result.add(list.get(i)); 
      String name = "";
      Iterator<E> it = this.session.createQuery("select info.informationTitle from com.js.oa.info.infomanager.po.InformationPO info where info.informationId =" + informationId).iterate();
      while (it.hasNext())
        name = it.next().toString(); 
      result.add(name);
      String infoChannelId = "";
      String otherChannel = "";
      ResultSet rs = stmt.executeQuery("select channel_id,otherchannel from JSDB.oa_information where information_id=" + 
          informationId);
      if (rs.next()) {
        infoChannelId = rs.getString(1);
        otherChannel = rs.getString(2);
      } 
      rs.close();
      if (channelId.equals(infoChannelId)) {
        stmt.execute(
            "delete from JSDB.oa_information where information_id=" + 
            informationId);
        stmt.execute("delete from JSDB.JSF_WORK where worktable_id in(select wf_immoform_id from JSDB.jsf_immobilityform where immoform_displayname='信息内容表') and workrecord_id=" + 
            informationId);
      } else {
        otherChannel = otherChannel.replaceAll("," + channelId + ",", 
            "");
        stmt.executeUpdate(
            "update JSDB.oa_information set otherchannel='" + 
            otherChannel + "' where information_id=" + 
            informationId);
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public void transfer(String[] infoId, String channelId, String orchannelId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    boolean status = conn.getAutoCommit();
    conn.setAutoCommit(false);
    stmt = conn.createStatement();
    try {
      String sql_channel = "select IsAllowReview from oa_informationChannel where channel_id=" + channelId;
      ResultSet rs_channel = stmt.executeQuery(sql_channel);
      String isAllowView = "";
      if (rs_channel.next())
        isAllowView = rs_channel.getString(1); 
      rs_channel.close();
      for (int i = 0; i < infoId.length; i++) {
        String curchannel = "";
        String otherChannel = "";
        ResultSet rs = stmt.executeQuery("select channel_id,otherChannel from oa_information where information_id=" + infoId[i]);
        if (rs.next()) {
          curchannel = rs.getString(1);
          otherChannel = rs.getString(2);
        } 
        rs.close();
        if (curchannel.equals(orchannelId)) {
          if (isAllowView.equals("0")) {
            String sql_updateAllow = "update oa_information set isAllow='0' where information_id=" + infoId[i];
            stmt.executeUpdate(sql_updateAllow);
          } 
          stmt.executeUpdate("update oa_information set otherChannel='' where information_id=" + infoId[i]);
          stmt.executeUpdate("update oa_information set informationreadername='',informationreader='',informationreaderorg='',informationreadergroup='',channel_id=" + channelId + " where information_id=" + infoId[i]);
        } else {
          String orOtherChannel = otherChannel;
          if (orOtherChannel == null)
            orOtherChannel = ""; 
          orOtherChannel = orOtherChannel.replaceAll("," + orchannelId + ",", "," + channelId + ",");
          stmt.executeUpdate("update oa_information set otherChannel='" + orOtherChannel + "' where information_id=" + infoId[i]);
        } 
      } 
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
  }
  
  public List getNew(String userId, String orgId) throws Exception {
    begin();
    List list = null;
    try {
      String hSql = 
        "select top 15 aaa.channelName, bbb.informationId, bbb.informationTitle,  bbb.informationKits, bbb.informationIssueTime, bb.informationHead  com.js.oa.info.infomanager.po.InformationPO bbb join  aaa.informationChannel aaa ";
      Query query = this.session.createQuery(
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          orgId + "$%'");
      List<String> orgList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '01*02*01' and  and ccc.empId = " + 
          
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
          groupString + 
          " aaa.channelReader like '%$" + userId + "$%')  ";
      } 
      hSql = " order by bbb.informationIssueTime desc ";
      query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getContent(String informationId) throws Exception {
    String content = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.informationContent from  com.js.oa.info.infomanager.po.InformationPO aaa  where aaa.informationId = " + 
          
          informationId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        Object tmp = iter.next();
        content = (tmp == null) ? "" : tmp.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return content;
  }
  
  public String getInfoReader(String userId, String orgId, String orgIdString, String alias) throws Exception {
    StringBuffer infoReader = new StringBuffer();
    begin();
    try {
      infoReader.append("((").append(alias).append(
          ".informationReader is null or ").append(alias).append(".informationReader='') and ");
      infoReader.append("(").append(alias).append(".informationReaderOrg is null or ").append(alias).append(".informationReaderOrg='') and ");
      infoReader.append("(").append(alias).append(".informationReaderGroup is null or ").append(alias).append(".informationReaderGroup=''))");
      infoReader.append(" or ").append(alias).append(".informationReader like '%$").append(userId).append("$%' ");
      infoReader.append(" or ").append(alias).append(".informationIssuerId=").append(userId);
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
          orgIdString + 
          "' like concat('%$', aaa.orgId, '$%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),'" + 
          orgIdString + "')>0";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
          orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%')";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<E> list = query.list();
      int i;
      for (i = 0; i < list.size(); i++)
        infoReader.append(" or " + alias + 
            ".informationReaderOrg like '%*" + 
            list.get(i).toString() + "*%'"); 
      infoReader.append(" or " + alias + 
          ".informationReaderOrg like '%*-1*%'");
      query = this.session.createQuery(
          "select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa  join aaa.employees bbb where bbb.empId = " + 
          userId);
      list = query.list();
      for (i = 0; i < list.size(); i++)
        infoReader.append(" or " + alias + 
            ".informationReaderGroup like '%@" + 
            list.get(i).toString() + "@%'"); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return infoReader.toString();
  }
  
  public List getAssociateInfo(String orgId, String infoId, String userId, String orgIdString, String channelType, String userDefine) throws Exception {
    List list = null;
    String readWhere = getInfoReader(userId, "", orgIdString, "aaa");
    String hSql = getUserViewCh(userId, orgId, channelType, userDefine);
    begin();
    try {
      String domainId = this.session.createQuery("select po.domainId from com.js.system.vo.organizationmanager.OrganizationVO po where po.orgId=" + 
          orgId).iterate().next()
        .toString();
      String key = "";
      Iterator it = this.session.createQuery("select aaa.informationKey from com.js.oa.info.infomanager.po.InformationPO aaa where aaa.informationId = " + 
          infoId).iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null)
          key = obj.toString(); 
      } 
      StringBuffer buffer = new StringBuffer("select distinct bbb.channelName, aaa.informationId, aaa.informationTitle, aaa.informationSummary,aaa.informationModifyTime, aaa.informationHead, aaa.informationType,bbb.channelId ");
      buffer.append("from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb ")
        .append("where aaa.domainId=").append(domainId)
        .append(" and ((").append(readWhere).append(
          ") and bbb.channelId in (").append(hSql).append(")) ")
        .append("and (");
      if (!"".equals(key))
        buffer.append("(aaa.informationKey='").append(key).append(
            "') or"); 
      buffer.append(" aaa.informationId in (select ddd.associateInfo from com.js.oa.info.infomanager.po.AssociateInfoPO ddd where ddd.masterInfo = ")
        .append(infoId).append(")")
        .append(") and aaa.informationId <> ").append(infoId)
        .append(" order by aaa.informationId desc");
      list = this.session.createQuery(buffer.toString()).list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getInformationIssueOrg(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      String sql = "select aaa.informationIssueOrg from  com.js.oa.info.infomanager.po.InformationPO aaa where aaa.informationId = " + 
        informationId;
      Query query = this.session.createQuery(sql);
      list = query.list();
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
  
  public void updateProcInfo(String infoId, List<String[]> fieldValueList) throws Exception {
    begin();
    try {
      InformationPO infoPO = (InformationPO)this.session.load(InformationPO.class, 
          new Long(infoId), LockMode.UPGRADE);
      String[] str = (String[])null;
      for (int i = 0; i < fieldValueList.size(); i++) {
        str = fieldValueList.get(i);
        if (str[0].equals("informationValidType")) {
          infoPO.setInformationValidType(Integer.parseInt(str[1]));
          if (str[1].equals("1")) {
            String[] str2 = (String[])null;
            for (int j = 0; j < fieldValueList.size(); j++) {
              str2 = fieldValueList.get(j);
              if (str2[0].equals("validBeginTime"))
                infoPO.setValidBeginTime(new Date(
                      str2[1])); 
              if (str2[0].equals("validEndTime"))
                infoPO.setValidEndTime(new Date(str2[
                        1])); 
            } 
          } 
        } else if (str[0].equals("informationTitle")) {
          infoPO.setInformationTitle(str[1]);
        } else if (str[0].equals("informationSubTitle")) {
          infoPO.setInformationSubTitle(str[1]);
        } else if (str[0].equals("informationSummary")) {
          infoPO.setInformationSummary(str[1]);
        } else if (str[0].equals("informationKey")) {
          infoPO.setInformationKey(str[1]);
        } else if (str[0].equals("informationHead")) {
          infoPO.setInformationHead(Integer.parseInt(str[1]));
          String[] str2 = (String[])null;
          for (int j = 0; j < fieldValueList.size(); j++) {
            str2 = fieldValueList.get(j);
            if (str2[0].equals("informationHeadId")) {
              infoPO.setInformationHeadId(new Long(str2[1]));
            } else if (str2[0].equals("informationSealId")) {
              infoPO.setInformationSealId(new Long(str2[1]));
            } else if (str2[0].equals("informationHeadFile")) {
              infoPO.setInformationHeadFile(str2[1]);
            } else if (str2[0].equals("informationSeal")) {
              infoPO.setInformationSeal(str2[1]);
            } else if (str[0].equals("informationMark")) {
              infoPO.setInformationMark(str[1]);
            } else if (str[0].equals("infoRedIssueTime")) {
              String[] infoRedIssueTime = str[1].split("/");
              infoPO.setInfoRedIssueTime(String.valueOf(infoRedIssueTime[0]) + 
                  "年" + infoRedIssueTime[1] + "月" + 
                  infoRedIssueTime[2] + "日");
            } else if (str[0].equals("infoRedIssueOrg")) {
              infoPO.setInfoRedIssueOrg(str[1]);
            } 
          } 
        } else if (str[0].equals("informationAuthor")) {
          infoPO.setInformationAuthor(str[1]);
        } else if (str[0].equals("infoAppendName")) {
          String infoAppendName = str[1], infoAppendSaveName = "";
          String infoPicName = "", infoPicSaveName = "";
          String[] str2 = (String[])null;
          for (int j = 0; j < fieldValueList.size(); j++) {
            str2 = fieldValueList.get(j);
            if (str2[0].equals("infoAppendSaveName"))
              infoAppendSaveName = str2[1]; 
            if (str2[0].equals("infoPicName"))
              infoPicName = str2[1]; 
            if (str2[0].equals("infoPicSaveName"))
              infoPicSaveName = str2[1]; 
          } 
          Set<InformationAccessoryPO> accessorySet = new HashSet();
          if (infoAppendName != null && !"".equals(infoAppendName)) {
            infoAppendName = infoAppendName.substring(1, 
                infoAppendName.length() - 1);
            infoAppendSaveName = infoAppendSaveName.substring(1, 
                infoAppendSaveName.length() - 1);
            String[] infoAppendNameArray = { "" };
            String[] infoAppendSaveNameArray = { "" };
            if (infoAppendName.indexOf(",,") > 0) {
              infoAppendNameArray = infoAppendName.split(",,");
              infoAppendSaveNameArray = infoAppendSaveName.split(
                  ",,");
            } else {
              infoAppendNameArray[0] = infoAppendName;
              infoAppendSaveNameArray[0] = infoAppendSaveName;
            } 
            for (int k = 0; k < infoAppendNameArray.length; k++) {
              InformationAccessoryPO accePO = 
                new InformationAccessoryPO();
              accePO.setAccessoryIsImage(0);
              accePO.setAccessoryName(infoAppendNameArray[k]);
              accePO.setAccessorySaveName(infoAppendSaveNameArray[
                    k]);
              accePO.setAccessoryType(infoAppendSaveNameArray[k]
                  .substring(infoAppendSaveNameArray[k]
                    .indexOf(".") + 1));
              accePO.setInformation(infoPO);
              this.session.save(accePO);
              accessorySet.add(accePO);
            } 
          } 
          if (infoPicName != null && !"".equals(infoPicName)) {
            infoPicName = infoPicName.substring(1, 
                infoPicName.length() - 1);
            infoPicSaveName = infoPicSaveName.substring(1, 
                infoPicSaveName.length() - 1);
            String[] infoPicNameArray = { "" };
            String[] infoPicSaveNameArray = { "" };
            if (infoPicName.indexOf(",,") > 0) {
              infoPicNameArray = infoPicName.split(",,");
              infoPicSaveNameArray = infoPicSaveName.split(",,");
            } else {
              infoPicNameArray[0] = infoPicName;
              infoPicSaveNameArray[0] = infoPicSaveName;
            } 
            for (int k = 0; k < infoPicNameArray.length; k++) {
              InformationAccessoryPO accePO = 
                new InformationAccessoryPO();
              accePO.setAccessoryIsImage(1);
              accePO.setAccessoryName(infoPicNameArray[k]);
              accePO.setAccessorySaveName(infoPicSaveNameArray[k]);
              accePO.setAccessoryType(infoPicSaveNameArray[k]
                  .substring(infoPicSaveNameArray[k].indexOf(
                      ".") + 1));
              accePO.setInformation(infoPO);
              this.session.save(accePO);
              accessorySet.add(accePO);
            } 
          } 
          infoPO.setInformationAccessory(accessorySet);
        } else if (str[0].equals("informationContent")) {
          infoPO.setInformationContent(str[1]);
        } else if (str[0].equals("informationReaderName")) {
          infoPO.setInformationReaderName(str[1]);
          String[] str2 = (String[])null;
          for (int j = 0; j < fieldValueList.size(); j++) {
            str2 = fieldValueList.get(j);
            if (str2[0].equals("informationReader"))
              infoPO.setInformationReader(str2[1]); 
            if (str2[0].equals("informationReaderOrg"))
              infoPO.setInformationReaderOrg(str2[1]); 
            if (str2[0].equals("informationReaderGroup"))
              infoPO.setInformationReaderGroup(str2[1]); 
          } 
        } else if (str[0].equals("forbidCopy")) {
          infoPO.setForbidCopy(Integer.parseInt(str[1]));
        } else if (str[0].equals("transmitToWebsite")) {
          infoPO.setTransmitToWebsite(Integer.parseInt(str[1]));
        } else if (str[0].equals("orderCode")) {
          infoPO.setOrderCode(str[1]);
        } else {
          str[0].equals("associateInfo");
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public String getAccessoryType(String infoId) throws Exception {
    String aType = "";
    begin();
    try {
      Query query = this.session.createQuery("select aaa.accessoryType from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa join aaa.information bbb where bbb.informationId=" + 
          infoId + " order by accessory_id");
      Iterator<String> itor = query.iterate();
      if (itor.hasNext())
        aType = itor.next(); 
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return aType;
  }
  
  public Long save(InformationPO informationPO, String[] para, String[] assoInfo, String[] infoPicName, String[] infoPicSaveName, String[] infoAppendName, String[] infoAppendSaveName, String domainId, String corpId) throws Exception {
    begin();
    Long informationId = null;
    String informationContent = "";
    try {
      Long long_;
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        informationContent = informationPO.getInformationContent();
        informationPO.setInformationContent("");
      } 
      informationPO.setCorpId(Long.valueOf(("".equals(corpId) || "null".equalsIgnoreCase(corpId)) ? "0" : corpId));
      informationId = (Long)this.session.save(informationPO);
      this.session.flush();
      if (infoPicName != null)
        for (int i = 0; i < infoPicName.length; i++) {
          InformationAccessoryPO informationAccessoryPO = new InformationAccessoryPO();
          informationAccessoryPO.setAccessoryName(infoPicName[i]);
          informationAccessoryPO.setAccessorySaveName(infoPicSaveName[i]);
          informationAccessoryPO.setAccessoryIsImage(1);
          if (infoPicSaveName[i].indexOf(".") > 0) {
            informationAccessoryPO.setAccessoryType(infoPicSaveName[i].substring(infoPicSaveName[i].indexOf(".") + 
                  1, infoPicSaveName[i].length()));
          } else {
            informationAccessoryPO.setAccessoryType("");
          } 
          informationAccessoryPO.setInformation(informationPO);
          informationAccessoryPO.setDomainId(Long.valueOf(domainId));
          this.session.save(informationAccessoryPO);
        }  
      if (infoAppendName != null)
        for (int i = 0; i < infoAppendName.length; i++) {
          InformationAccessoryPO informationAccessoryPO = new InformationAccessoryPO();
          informationAccessoryPO.setAccessoryName(infoAppendName[i]);
          informationAccessoryPO.setAccessorySaveName(infoAppendSaveName[i]);
          informationAccessoryPO.setAccessoryIsImage(0);
          if (infoAppendSaveName[i].indexOf(".") > 0) {
            informationAccessoryPO.setAccessoryType(infoAppendSaveName[i].substring(infoAppendSaveName[i].indexOf(".") + 1, infoAppendSaveName[i].length()));
          } else {
            informationAccessoryPO.setAccessoryType("");
          } 
          informationAccessoryPO.setInformation(informationPO);
          informationAccessoryPO.setDomainId(Long.valueOf(domainId));
          this.session.save(informationAccessoryPO);
        }  
      if (assoInfo != null)
        for (int j = 0; j < assoInfo.length; j++) {
          AssociateInfoPO assoPO = new AssociateInfoPO();
          assoPO.setMasterInfo(informationId);
          assoPO.setAssociateInfo(new Long(assoInfo[j]));
          assoPO.setDomainId(Long.valueOf(domainId));
          this.session.save(assoPO);
        }  
      String userId = para[0];
      String userName = para[1];
      String orgId = para[2];
      String orgName = para[3];
      String orgIdString = para[4];
      Date now = new Date();
      String afficheHisDate = "-1";
      String informationOrISODoc = "0";
      if (informationPO.getInformationOrISODoc() != null)
        informationOrISODoc = informationPO.getInformationOrISODoc(); 
      if (informationPO.getAfficeHistoryDate() != null)
        long_ = informationPO.getAfficeHistoryDate(); 
      if (long_.equals("-1") && informationOrISODoc.equals("0")) {
        long maxCount = 0L;
        Query query = this.session.createQuery(" select max(aaa.accumulateNum) from  com.js.oa.info.infomanager.po.InforPersonalStatPO aaa  where aaa.empId = " + 
            userId);
        List<Long> list = query.list();
        if (list != null && list.size() > 0 && list.get(0) != null)
          maxCount = ((Long)list.get(0)).longValue(); 
        query = this.session.createQuery(" select aaa from com.js.oa.info.infomanager.po.InforPersonalStatPO aaa  where aaa.empId = " + 
            userId + 
            " and aaa.statYear = " + (
            now.getYear() + 1900) + 
            " and statMonth = " + (
            now.getMonth() + 1));
        list = query.list();
        InforPersonalStatPO inforPersonalStatPO = new InforPersonalStatPO();
        if (list != null && list.size() > 0 && list.get(0) != null) {
          inforPersonalStatPO = (InforPersonalStatPO)list.get(0);
          int monthIssueNum = inforPersonalStatPO.getMonthIssueNum();
          inforPersonalStatPO.setAccumulateNum(new Long(maxCount + 1L));
          inforPersonalStatPO.setMonthIssueNum(monthIssueNum + 1);
          inforPersonalStatPO.setEmpName(userName);
          inforPersonalStatPO.setOrgId(new Long(orgId));
          inforPersonalStatPO.setOrgIdString(orgIdString);
          inforPersonalStatPO.setOrgName(orgName);
          inforPersonalStatPO.setDomainId(Long.valueOf(domainId));
          inforPersonalStatPO.setCorpId(Long.valueOf(("".equals(corpId) || "null".equalsIgnoreCase(corpId)) ? "0" : corpId));
        } else {
          inforPersonalStatPO.setAccumulateNum(new Long(maxCount + 1L));
          inforPersonalStatPO.setEmpId(new Long(userId));
          inforPersonalStatPO.setEmpName(userName);
          inforPersonalStatPO.setMonthIssueNum(1);
          inforPersonalStatPO.setOrgId(new Long(orgId));
          inforPersonalStatPO.setOrgIdString(orgIdString);
          inforPersonalStatPO.setOrgName(orgName);
          inforPersonalStatPO.setStatMonth(now.getMonth() + 1);
          inforPersonalStatPO.setStatYear(now.getYear() + 1900);
          inforPersonalStatPO.setDomainId(Long.valueOf(domainId));
          inforPersonalStatPO.setCorpId(Long.valueOf(("".equals(corpId) || "null".equalsIgnoreCase(corpId)) ? "0" : corpId));
          this.session.save(inforPersonalStatPO);
        } 
        String tmpSql = "";
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = 
            " select aaa.orgId,aaa.orgName,aaa.orgIdString,aaa.orgLevel,aaa.orgNameString  from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
            
            orgIdString + 
            "' like concat('%$', aaa.orgId, '$%') ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = 
            " select aaa.orgId,aaa.orgName,aaa.orgIdString,aaa.orgLevel,aaa.orgNameString  from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),'" + 
            
            orgIdString + "')>0";
        } else {
          tmpSql = 
            " select aaa.orgId,aaa.orgName,aaa.orgIdString,aaa.orgLevel,aaa.orgNameString  from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
            
            orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
        } 
        query = this.session.createQuery(tmpSql);
        list = query.list();
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = (Object[])list.get(i);
          query = this.session.createQuery(
              " select max(aaa.accumulateNum) from  com.js.oa.info.infomanager.po.InforOrgStatPO aaa  where aaa.orgId = " + 
              
              obj[0]);
          List<Long> tmpList = query.list();
          maxCount = 0L;
          if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null)
            maxCount = ((Long)tmpList.get(0)).longValue(); 
          query = this.session.createQuery(" select aaa from  com.js.oa.info.infomanager.po.InforOrgStatPO aaa  where aaa.orgId = " + 
              
              obj[0] + 
              " and aaa.statYear = " + (
              now.getYear() + 1900) + 
              " and aaa.statMonth = " + (
              now.getMonth() + 1));
          tmpList = query.list();
          if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
            InforOrgStatPO inforOrgStatPO = 
              (InforOrgStatPO)tmpList.get(0);
            inforOrgStatPO.setOrgIdString(obj[2].toString());
            inforOrgStatPO.setOrgName(obj[4].toString());
            inforOrgStatPO.setOrgLevel(Integer.parseInt(obj[3]
                  .toString()));
            inforOrgStatPO.setMonthIssueNum(inforOrgStatPO
                .getMonthIssueNum() + 1);
            inforOrgStatPO.setAccumulateNum(new Long(maxCount + 1L));
            inforOrgStatPO.setDomainId(Long.valueOf(domainId));
            inforOrgStatPO.setCorpId(Long.valueOf(("".equals(corpId) || "null".equalsIgnoreCase(corpId)) ? "0" : corpId));
          } else {
            InforOrgStatPO inforOrgStatPO = new InforOrgStatPO();
            inforOrgStatPO.setOrgId(new Long(obj[0].toString()));
            inforOrgStatPO.setOrgIdString(obj[2].toString());
            inforOrgStatPO.setOrgName(obj[4].toString());
            inforOrgStatPO.setStatMonth(now.getMonth() + 1);
            inforOrgStatPO.setStatYear(now.getYear() + 1900);
            inforOrgStatPO.setOrgLevel(Integer.parseInt(obj[3].toString()));
            inforOrgStatPO.setMonthIssueNum(1);
            inforOrgStatPO.setAccumulateNum(new Long(maxCount + 1L));
            inforOrgStatPO.setDomainId(Long.valueOf(domainId));
            inforOrgStatPO.setCorpId(Long.valueOf(("".equals(corpId) || "null".equalsIgnoreCase(corpId)) ? "0" : corpId));
            this.session.save(inforOrgStatPO);
          } 
        } 
      } 
      informationPO.setOrderCode("1000");
      this.session.flush();
      if (databaseType.indexOf("oracle") >= 0)
        InfoUtil.insertInfoContent_oracle(informationId, informationContent); 
      informationPO = null;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return informationId;
  }
  
  public List getNotBrowser(String informationId, String searchName, String domainId) throws Exception {
    begin();
    List list = null;
    try {
      String databaseType = 
        SystemCommon.getDatabaseType();
      String informationReader = "", informationReaderOrg = "";
      String informationReaderGroup = "", channelReader = "";
      String channelReaderOrg = "", channelReaderGroup = "";
      Iterator<Object[]> iter = this.session.iterate("select info.informationReader, info.informationReaderOrg, info.informationReaderGroup, ch.channelReader, ch.channelReaderOrg, ch.channelReaderGroup from com.js.oa.info.infomanager.po.InformationPO info join info.informationChannel ch where info.informationId = " + 


          
          informationId);
      Object[] obj = (Object[])null;
      if (iter.hasNext()) {
        obj = iter.next();
        informationReader = (obj[0] == null) ? "" : obj[0].toString();
        informationReaderOrg = (obj[1] == null) ? "" : obj[1].toString();
        informationReaderGroup = (obj[2] == null) ? "" : obj[2].toString();
        channelReader = (obj[3] == null) ? "" : obj[3].toString();
        channelReaderOrg = (obj[4] == null) ? "" : obj[4].toString();
        channelReaderGroup = (obj[5] == null) ? "" : obj[5].toString();
      } 
      String chWhere = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where 1=1 and org.domainId=" + 
        domainId + " ";
      if (channelReader.equals("") && channelReaderOrg.equals("") && 
        channelReaderGroup.equals("")) {
        chWhere = String.valueOf(chWhere) + " and 1=1 ";
      } else {
        chWhere = String.valueOf(chWhere) + " and ( 1<>1 ";
        if (!channelReader.equals(""))
          if (databaseType.indexOf("mysql") >= 0) {
            chWhere = String.valueOf(chWhere) + " or '" + channelReader + 
              "' like concat('%$', emp.empId, '$%') ";
          } else if (databaseType.indexOf("db2") >= 0) {
            chWhere = String.valueOf(chWhere) + " or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(emp.empId)), '$'),'" + channelReader + "')>0";
          } else {
            chWhere = String.valueOf(chWhere) + " or '" + channelReader + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp.empId)), '$%') ";
          }  
        if (!channelReaderOrg.equals(""))
          if (databaseType.indexOf("mysql") >= 0) {
            chWhere = String.valueOf(chWhere) + " or org.orgId in (select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organizationmanager.OrganizationVO b where a.orgIdString like concat('%$', b.orgId, '$%') and '" + 
              
              channelReaderOrg + 
              "' like concat('%*', b.orgId, '*%'))";
          } else if (databaseType.indexOf("db2") >= 0) {
            chWhere = String.valueOf(chWhere) + " or org.orgId in (select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organizationmanager.OrganizationVO b where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(b.orgId)), '$'),a.orgIdString)>0 and locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('*', JSDB.FN_INTTOSTR(b.orgId)), '*'),'" + 
              
              channelReaderOrg + "')>0)";
          } else {
            chWhere = String.valueOf(chWhere) + " or org.orgId in (select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organizationmanager.OrganizationVO b where a.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(b.orgId)), '$%') and '" + 
              
              channelReaderOrg + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(b.orgId)), '*%'))";
          }  
        if (!channelReaderGroup.equals(""))
          if (databaseType.indexOf("mysql") >= 0) {
            chWhere = String.valueOf(chWhere) + " or emp.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where '" + 
              channelReaderGroup + 
              "' like concat('%@', group2.groupId, '@%') )";
          } else if (databaseType.indexOf("db2") >= 0) {
            chWhere = String.valueOf(chWhere) + " or emp.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('@', JSDB.FN_INTTOSTR(group2.groupId)), '@'),'" + 
              channelReaderGroup + "')>0 )";
          } else {
            chWhere = String.valueOf(chWhere) + " or emp.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where '" + 
              channelReaderGroup + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(group2.groupId)), '@%') )";
          }  
        chWhere = String.valueOf(chWhere) + " ) ";
      } 
      String infoWhere = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where 1=1 ";
      if (informationReader.equals("") && informationReaderOrg.equals("") && 
        informationReaderGroup.equals("")) {
        infoWhere = String.valueOf(infoWhere) + " and 1=1 ";
      } else {
        infoWhere = String.valueOf(infoWhere) + " and ( 1<>1 ";
        if (!informationReader.equals(""))
          if (databaseType.indexOf("mysql") >= 0) {
            infoWhere = String.valueOf(infoWhere) + " or '" + informationReader + 
              "' like concat('%$', emp.empId, '$%') ";
          } else if (databaseType.indexOf("db2") >= 0) {
            infoWhere = String.valueOf(infoWhere) + " or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(emp.empId)), '$'),'" + informationReader + "')>0 ";
          } else {
            infoWhere = String.valueOf(infoWhere) + " or '" + informationReader + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp.empId)), '$%') ";
          }  
        if (!informationReaderOrg.equals(""))
          if (databaseType.indexOf("mysql") >= 0) {
            infoWhere = String.valueOf(infoWhere) + " or org.orgId in (select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organizationmanager.OrganizationVO b where a.orgIdString like concat('%$', b.orgId, '$%') and '" + 
              
              informationReaderOrg + 
              "' like concat('%*', b.orgId, '*%'))";
          } else if (databaseType.indexOf("db2") >= 0) {
            infoWhere = String.valueOf(infoWhere) + " or org.orgId in (select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organizationmanager.OrganizationVO b where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(b.orgId)), '$'),a.orgIdString)>0 and locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(b.orgId)), '*%'),'" + 
              
              informationReaderOrg + "')>0)";
          } else {
            infoWhere = String.valueOf(infoWhere) + " or org.orgId in (select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organizationmanager.OrganizationVO b where a.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(b.orgId)), '$%') and '" + 
              
              informationReaderOrg + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(b.orgId)), '*%'))";
          }  
        if (!informationReaderGroup.equals(""))
          if (databaseType.indexOf("mysql") >= 0) {
            infoWhere = String.valueOf(infoWhere) + " or emp.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where '" + 
              informationReaderGroup + 
              "' like concat('%@', group2.groupId, '@%') )";
          } else if (databaseType.indexOf("db2") >= 0) {
            infoWhere = String.valueOf(infoWhere) + " or emp.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('@', JSDB.FN_INTTOSTR(group2.groupId)), '@'),'" + 
              informationReaderGroup + "')>0)";
          } else {
            infoWhere = String.valueOf(infoWhere) + " or emp.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where '" + 
              informationReaderGroup + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(group2.groupId)), '@%') )";
          }  
        infoWhere = String.valueOf(infoWhere) + " ) ";
      } 
      String sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "select empVO.empName, orgVO.orgNameString, empVO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empVO join empVO.organizations orgVO where empVO.empId in (" + 
          chWhere + ") " + 
          "and empVO.empId in (" + infoWhere + ") and " + 
          "empVO.empId not in (select ib.empId from com.js.oa.info.infomanager.po.InformationBrowserPO ib join ib.information info where info.informationId=" + 
          informationId + ") and " + 
          "empVO.empName like concat('%', '" + searchName + 
          "', '%') and " + 
          "empVO.userIsDeleted=0 and empVO.userIsActive=1 order by orgVO.orgIdString";
      } else if (databaseType.indexOf("db2") >= 0) {
        sql = "select empVO.empName, orgVO.orgNameString, empVO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empVO join empVO.organizations orgVO where empVO.empId in (" + 
          chWhere + ") " + 
          "and empVO.empId in (" + infoWhere + ") and " + 
          "empVO.empId not in (select ib.empId from com.js.oa.info.infomanager.po.InformationBrowserPO ib join ib.information info where info.informationId=" + 
          informationId + ") and " + 
          "empVO.empName like '%" + 
          searchName + "%') and " + 
          "empVO.userIsDeleted=0 and empVO.userIsActive=1 order by orgVO.orgIdString";
      } else {
        sql = "select empVO.empName, orgVO.orgNameString, empVO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empVO join empVO.organizations orgVO where empVO.empId in (" + 
          chWhere + ") " + 
          "and empVO.empId in (" + infoWhere + ") and " + 
          "empVO.empId not in (select ib.empId from com.js.oa.info.infomanager.po.InformationBrowserPO ib join ib.information info where info.informationId=" + 
          informationId + ") and " + 
          "empVO.empName like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', '" + 
          searchName + "'), '%') and " + 
          "empVO.userIsDeleted=0 and empVO.userIsActive=1 order by orgVO.orgIdString";
      } 
      list = this.session.createQuery(sql).list();
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
  
  public Integer getUserIssueInfoCount(String userId) throws Exception {
    begin();
    Integer count = Integer.valueOf("0");
    try {
      Iterator<Integer> iter = this.session.iterate("select count(info.informationId) from com.js.oa.info.infomanager.po.InformationPO info where info.informationIssuerId=" + 
          userId);
      if (iter.hasNext())
        count = iter.next(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return count;
  }
  
  public Integer setDossierStatus(String informationId, String dossierStatus) throws Exception {
    Integer result = Integer.valueOf("0");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stat = conn.createStatement();
      stat.execute("update JSDB.OA_INFORMATION SET DOSSIERSTATUS=" + 
          dossierStatus + " WHERE INFORMATION_ID = " + 
          informationId);
      stat.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public Boolean vindicateInfo(String userId, String orgId, String informationId) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String rightCode = "", channelId = "";
      Iterator<Object[]> iter = this.session.iterate("select a.channelType, a.userDefine, a.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO a join a.information b where b.informationId = " + 
          informationId);
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[0].toString().equals("0") || 
          obj[1].toString().equals("1")) {
          rightCode = "01*02*01";
        } else {
          rightCode = "01*01*02";
        } 
        channelId = obj[2].toString();
        Query query = this.session.createQuery(
            " select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa join  aaa.right bbb join aaa.employee ccc where bbb.rightCode='" + 

            
            rightCode + "' and ccc.empId = " + userId);
        List<Object[]> tmpList = query.list();
        boolean hasAllScope = false;
        String scopeWhereSql = "";
        if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
          obj = tmpList.get(0);
          String scopeType = obj[0].toString();
          String scopeScope = "";
          if (obj[1] != null)
            scopeScope = obj[1].toString(); 
          if (scopeType.equals("0")) {
            hasAllScope = true;
          } else if (scopeType.equals("1")) {
            scopeWhereSql = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            query = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                orgId + "$%'");
            tmpList = query.list();
            for (int i = 0; i < tmpList.size(); i++)
              scopeWhereSql = String.valueOf(scopeWhereSql) + " aaa.createdOrg = " + 
                tmpList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeWhereSql = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            scopeScope != null && !scopeScope.equals("")) {
            scopeScope = scopeScope.substring(1, 
                scopeScope.length() - 1);
            query = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                scopeScope + 
                "$%'");
            tmpList = query.list();
            for (int i = 0; i < tmpList.size(); i++)
              scopeWhereSql = String.valueOf(scopeWhereSql) + 
                " aaa.createdOrg = " + 
                tmpList.get(i) + " or "; 
          } 
        } 
        if (hasAllScope) {
          result = Boolean.TRUE;
        } else {
          if (scopeWhereSql.endsWith("or "))
            scopeWhereSql = scopeWhereSql.substring(0, 
                scopeWhereSql.length() - 3); 
          if (!"".equals(scopeWhereSql)) {
            query = this.session.createQuery(
                " select count(aaa.channelId) from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa where " + 
                
                scopeWhereSql + " and aaa.channelId=" + channelId);
            int count = ((Integer)query.iterate().next()).intValue();
            if (count > 0)
              result = Boolean.TRUE; 
          } 
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getInfoUserdefine(String informationId) throws Exception {
    String userDefine = "";
    begin();
    try {
      Iterator<E> iter = this.session.iterate("select a.userDefine from com.js.oa.info.channelmanager.po.InformationChannelPO a join a.information b where b.informationId=" + 
          informationId);
      if (iter.hasNext())
        userDefine = iter.next().toString(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userDefine;
  }
  
  public Map getMustReadCount(String userIds) throws Exception {
    Map<Object, Object> mustReadMap = new HashMap<Object, Object>();
    begin();
    try {
      StringBuffer sql = new StringBuffer("select emp1.empId, count(distinct info1.informationId) from com.js.oa.info.infomanager.po.InformationPO info1 join info1.informationChannel ch1, com.js.system.vo.usermanager.EmployeeVO emp1 join emp1.organizations org1 left join emp1.groups gp1, com.js.system.vo.organizationmanager.OrganizationVO org2 where org1.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org2.orgId)), '$%') and info1.mustRead=1 and emp1.empId in (" + 
          userIds + ") and ( ");
      sql.append("((info1.informationReader is null or info1.informationReader='') and (info1.informationReaderOrg is null or info1.informationReaderOrg='') and (info1.informationReaderGroup is null or info1.informationReaderGroup='') and (ch1.channelReader is null or ch1.channelReader='') and (ch1.channelReaderOrg is null or ch1.channelReaderOrg='') and (ch1.channelReaderGroup is null or ch1.channelReaderGroup=''))");
      sql.append(" or ");
      sql.append("(ch1.channelReader like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp1.empId)), '$%'))");
      sql.append(" or ");
      sql.append("(ch1.channelReaderOrg like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(org2.orgId)), '*%'))");
      sql.append(" or ");
      sql.append("(ch1.channelReaderGroup like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(gp1.groupId)), '@%'))");
      sql.append(" or ");
      sql.append("(info1.informationReader like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp1.empId)), '$%'))");
      sql.append(" or ");
      sql.append("(info1.informationReaderOrg like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(org2.orgId)), '*%'))");
      sql.append(" or ");
      sql.append("(info1.informationReaderGroup like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(gp1.groupId)), '@%'))");
      sql.append(") ");
      sql.append(" and info1.informationId not in (select info2.informationId from com.js.oa.info.infomanager.po.InformationBrowserPO ib join ib.information info2 where ib.empId in (" + 
          userIds + "))");
      sql.append(" and info1.informationStatus=0 group by emp1.empId");
      Iterator<Object[]> iter = this.session.iterate(sql.toString());
      Object[] obj = (Object[])null;
      while (iter.hasNext()) {
        obj = iter.next();
        mustReadMap.put(obj[0].toString(), obj[1]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return mustReadMap;
  }
  
  public List getMustReadInfo(String userIds, String domainId) throws Exception {
    List<Object[]> mustReadInfo = new ArrayList();
    begin();
    try {
      StringBuffer sql = new StringBuffer("select distinct ch1.userDefine,ch1.channelType,ch1.channelIdString,info1.informationId,info1.informationTitle,info1.titleColor,info1.informationKits,info1.informationIssueOrg, info1.informationIssuer, info1.informationVersion,info1.informationIssueTime,info1.informationCommonNum,info1.informationHead,info1.informationType from com.js.oa.info.infomanager.po.InformationPO info1 join info1.informationChannel ch1, com.js.system.vo.usermanager.EmployeeVO emp1 join emp1.organizations org1 left join emp1.groups gp1, com.js.system.vo.organizationmanager.OrganizationVO org2 where org1.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org2.orgId)), '$%') and info1.mustRead=1 and info1.domainId=" + 
          domainId + 
          " and emp1.empId in (" + 
          userIds + ") and ( ");
      sql.append("((info1.informationReader is null or info1.informationReader='') and (info1.informationReaderOrg is null or info1.informationReaderOrg='') and (info1.informationReaderGroup is null or info1.informationReaderGroup='') and (ch1.channelReader is null or ch1.channelReader='') and (ch1.channelReaderOrg is null or ch1.channelReaderOrg='') and (ch1.channelReaderGroup is null or ch1.channelReaderGroup=''))");
      sql.append(" or ");
      sql.append("(ch1.channelReader like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp1.empId)), '$%'))");
      sql.append(" or ");
      sql.append("(ch1.channelReaderOrg like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(org2.orgId)), '*%'))");
      sql.append(" or ");
      sql.append("(ch1.channelReaderGroup like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(gp1.groupId)), '@%'))");
      sql.append(" or ");
      sql.append("(info1.informationReader like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp1.empId)), '$%'))");
      sql.append(" or ");
      sql.append("(info1.informationReaderOrg like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(org2.orgId)), '*%'))");
      sql.append(" or ");
      sql.append("(info1.informationReaderGroup like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(gp1.groupId)), '@%'))");
      sql.append(") ");
      sql.append(" and info1.informationStatus=0 and info1.informationId not in (select info2.informationId from com.js.oa.info.infomanager.po.InformationBrowserPO ib join ib.information info2 where ib.empId=emp1.empId) order by info1.informationIssueTime");
      Iterator<Object[]> iter = this.session.iterate(sql.toString());
      Iterator iter2 = null;
      Object[] obj = (Object[])null;
      String userDefine = "0", channelType = "0", channelIdString = "";
      String informationTitle = "", titleColor = "";
      StringBuffer title = new StringBuffer();
      while (iter.hasNext()) {
        title.append("[");
        obj = iter.next();
        userDefine = (obj[0] == null) ? "0" : obj[0].toString();
        channelType = (obj[1] == null) ? "0" : obj[1].toString();
        if (userDefine.equals("0")) {
          if (channelType.equals("0")) {
            iter2 = this.session.iterate("select menu.menuName from com.js.system.menu.po.MenuSetPO menu where menu.menuCode='information'");
          } else {
            iter2 = this.session.iterate("select org.orgName from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgId=" + 
                channelType);
          } 
        } else {
          iter2 = this.session.iterate("select a.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO a where a.userChannelId=" + 
              channelType);
        } 
        if (iter2.hasNext())
          title.append((new StringBuilder()).append(iter2.next()).append(".").toString()); 
        channelIdString = (obj[2] == null) ? "" : obj[2].toString();
        iter2 = this.session.iterate("select ch.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO ch where '" + 
            channelIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(ch.channelId)), '$%')");
        while (iter2.hasNext())
          title.append((new StringBuilder()).append(iter2.next()).append(".").toString()); 
        title.deleteCharAt(title.length() - 1);
        title.append("]");
        titleColor = (obj[5] == null) ? "0" : obj[5].toString();
        if (titleColor.equals("1"))
          title.append("<font color=red>"); 
        title.append(obj[4]);
        if (titleColor.equals("1"))
          title.append("</font>"); 
        Object[] newObj = new Object[10];
        newObj[0] = obj[3];
        newObj[1] = title.toString();
        newObj[2] = obj[6];
        newObj[3] = obj[7] + "." + obj[8];
        newObj[4] = obj[9];
        newObj[5] = obj[10];
        newObj[6] = obj[11];
        newObj[7] = obj[12];
        newObj[8] = obj[13];
        newObj[9] = obj[1];
        mustReadInfo.add(newObj);
        title.delete(0, title.length());
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return mustReadInfo;
  }
  
  public Integer setOrderCode(String informationId, String channelType, String orderNum, String dateTime, String typeState, String active) throws Exception {
    Integer result = Integer.valueOf("0");
    Connection conn = null;
    Statement stmt = null;
    conn = (new DataSourceBase()).getDataSource().getConnection();
    boolean status = conn.getAutoCommit();
    conn.setAutoCommit(false);
    stmt = conn.createStatement();
    try {
      StringBuffer sql = new StringBuffer("update oa_information ");
      if (dateTime != null && !dateTime.equals("") && 
        typeState != null && !typeState.equals("") && !typeState.equals("null")) {
        Date sTime = new Date();
        long dis = DateHelper.getDistance(sTime, DateHelper.string2Date(dateTime));
        if (dis == 0L)
          dateTime = DateHelper.date2String(DateHelper.nextDate(dateTime)); 
        if (typeState.equals("1")) {
          if (active != null && !active.equals("") && !active.equals("null") && active.equals("reSet")) {
            sql.append("set topTimeFrom=''");
            sql.append(",TopTimeTo=''");
          } else {
            sql.append("set topTimeFrom='" + DateHelper.date2String(sTime) + "'");
            sql.append(",TopTimeTo='" + dateTime + "'");
          } 
          sql.append(",OrderCode=" + orderNum);
        } 
        if (typeState.equals("0")) {
          if (active != null && !active.equals("") && !active.equals("null") && active.equals("reSet")) {
            sql.append("set TopTimeStart=''");
            sql.append(",TopTimeTo=''");
          } else {
            sql.append("set TopTimeStart='" + DateHelper.date2String(sTime) + "'");
            sql.append(",TopTimeEnd='" + dateTime + "'");
          } 
          sql.append(",OrderCodeTemp=" + orderNum);
        } 
      } 
      sql.append(" where information_id=" + informationId);
      stmt.executeUpdate(sql.toString());
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      result = Integer.valueOf("-1");
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public String getUserViewCh(String userId, String orgId, String channelType, String userDefine) throws Exception {
    String hSql = "";
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if (channelType.equals("0") || userDefine.equals("1")) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + "))>0 ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate('$" + 
          
          orgId + "$',aaa.orgIdString)>0 ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " icpo.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgChildList.size(); i++)
              scopeString = String.valueOf(scopeString) + "icpo.createdOrg = " + 
                orgChildList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "icpo.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + 
                "icpo.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " icpo.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " icpo.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        if (channelType.equals("-1")) {
          hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

            
            scopeString + orgString + groupString + 
            " icpo.channelReader like '%$" + userId + "$%' or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='')))  and (icpo.channelType > 0) and (icpo.userDefine=0) and (org.orgId=icpo.channelType) ";
        } else {
          hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
            groupString + " icpo.channelReader like '%$" + 
            userId + "$%' or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='')) ) and icpo.channelType = " + 
            channelType;
        } 
      } else if (channelType.equals("-1")) {
        hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where icpo.channelType >0 and icpo.userDefine=0 and (org.orgId=icpo.channelType)";
      } else {
        hSql = String.valueOf(hSql) + " where icpo.channelType = " + channelType;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hSql;
  }
  
  public List getUserViewCh2(String userId, String orgId, String channelType, String userDefine) throws Exception {
    String hSql = "";
    List rList = new ArrayList();
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if (channelType.equals("0") || userDefine.equals("1")) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate('$" + 
          
          orgId + "$',aaa.orgIdString)>0 ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " icpo.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            StringBuffer scopeBuffer = new StringBuffer();
            for (int i = 0; i < orgChildList.size(); i++)
              scopeBuffer.append("icpo.createdOrg = ").append(orgChildList.get(i)).append(" or "); 
            scopeString = String.valueOf(scopeString) + scopeBuffer.toString();
          } else if (scopeType.equals("3")) {
            scopeString = "icpo.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List tmpOrgList = tmpQuery.list();
            StringBuffer scopeBuffer = new StringBuffer();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeBuffer.append("icpo.createdOrg = ").append(tmpOrgList.get(i)).append(" or "); 
            scopeString = String.valueOf(scopeString) + scopeBuffer.toString();
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        StringBuffer scopeBuffer = new StringBuffer();
        for (int i = 0; i < orgList.size(); i++)
          scopeBuffer.append(" icpo.channelReaderOrg like '%*").append(orgList.get(i)).append("' or "); 
        orgString = String.valueOf(orgString) + scopeBuffer.toString();
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " icpo.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        if (channelType.equals("-1")) {
          hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

            
            scopeString + orgString + groupString + 
            " icpo.channelReader like '%$" + userId + "$%' or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='')))  and (icpo.channelType > 0) and (icpo.userDefine=0) and (org.orgId=icpo.channelType) ";
        } else {
          hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
            groupString + " icpo.channelReader like '%$" + 
            userId + "$%' or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='')) ) and icpo.channelType = " + 
            channelType;
        } 
      } else if (channelType.equals("-1")) {
        hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where icpo.channelType >0 and icpo.userDefine=0 and (org.orgId=icpo.channelType)";
      } else {
        hSql = String.valueOf(hSql) + " where icpo.channelType = " + channelType;
      } 
      query = this.session.createQuery(hSql);
      rList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return rList;
  }
  
  public String[] getSingleEditor(String documentEditor, String informationIssuer, String year, String month) throws Exception {
    begin();
    String[] result = { "", "", "", "", "", "" };
    try {
      String viewSQL1 = "", viewSQL2 = "", viewSQL3 = "", viewSQL4 = "";
      String viewSQL5 = "", viewSQL6 = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        viewSQL1 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='0' and info2.informationAuthor='" + 
          documentEditor + "' and  info2.documentEditor='" + 
          informationIssuer + 
          "' and YEAR(info2.informationIssueTime)=" + year + 
          " and MONTH(info2.informationIssueTime)=" + month + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL2 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='1' and info2.informationAuthor='" + 
          documentEditor + "' and  info2.documentEditor='" + 
          informationIssuer + 
          "' and YEAR(info2.informationIssueTime)=" + year + 
          " and MONTH(info2.informationIssueTime)=" + month + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL3 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='2' and info2.informationAuthor='" + 
          documentEditor + "' and  info2.documentEditor='" + 
          informationIssuer + 
          "' and YEAR(info2.informationIssueTime)=" + year + 
          " and MONTH(info2.informationIssueTime)=" + month + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL4 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='0' and info2.informationAuthor='" + 
          documentEditor + "'" + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL5 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='1' and info2.informationAuthor='" + 
          documentEditor + "'" + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL6 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='2' and info2.informationAuthor='" + 
          documentEditor + "'" + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
      } else {
        viewSQL1 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='0' and info2.informationAuthor='" + 
          documentEditor + "' and  info2.documentEditor='" + 
          informationIssuer + 
          "' and JSDB.FN_DATENAME('YEAR', info2.informationIssueTime)=" + 
          year + 
          " and JSDB.FN_DATENAME('MONTH', info2.informationIssueTime)=" + 
          month + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL2 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='1' and info2.informationAuthor='" + 
          documentEditor + "' and  info2.documentEditor='" + 
          informationIssuer + 
          "' and JSDB.FN_DATENAME('YEAR', info2.informationIssueTime)=" + 
          year + 
          " and JSDB.FN_DATENAME('MONTH', info2.informationIssueTime)=" + 
          month + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL3 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='2' and info2.informationAuthor='" + 
          documentEditor + "' and  info2.documentEditor='" + 
          informationIssuer + 
          "' and JSDB.FN_DATENAME('YEAR', info2.informationIssueTime)=" + 
          year + 
          " and JSDB.FN_DATENAME('MONTH', info2.informationIssueTime)=" + 
          month + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL4 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='0' and info2.informationAuthor='" + 
          documentEditor + "'" + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL5 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='1' and info2.informationAuthor='" + 
          documentEditor + "'" + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
        viewSQL6 = "select count(*) from com.js.oa.info.infomanager.po.InformationPO info2 where info2.documentType='2' and info2.informationAuthor='" + 
          documentEditor + "'" + " and (info2.afficeHistoryDate is null or info2.afficeHistoryDate =-1) and (info2.informationOrISODoc is null or info2.informationOrISODoc='0' )";
      } 
      Iterator<String> iter = this.session.iterate(viewSQL1);
      if (iter.hasNext())
        result[0] = iter.next(); 
      iter = this.session.iterate(viewSQL2);
      if (iter.hasNext())
        result[1] = iter.next(); 
      iter = this.session.iterate(viewSQL3);
      if (iter.hasNext())
        result[2] = iter.next(); 
      iter = this.session.iterate(viewSQL4);
      if (iter.hasNext())
        result[3] = iter.next(); 
      iter = this.session.iterate(viewSQL5);
      if (iter.hasNext())
        result[4] = iter.next(); 
      iter = this.session.iterate(viewSQL6);
      if (iter.hasNext())
        result[5] = iter.next(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Object[] getAllBrowser(String informationId) throws Exception {
    Object[] obj = (Object[])null;
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select info.informationReader, info.informationReaderOrg, info.informationReaderGroup, ch.channelReader, ch.channelReaderOrg, ch.channelReaderGroup from com.js.oa.info.infomanager.po.InformationPO info join info.informationChannel ch where info.informationId = " + 


          
          informationId);
      if (iter.hasNext())
        obj = iter.next(); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return obj;
  }
  
  public Boolean channelCanView(String userId, String orgId, String channelType, String userDefine, String channelId) throws Exception {
    Boolean result = Boolean.FALSE;
    String sql = getUserViewCh(userId, orgId, channelType, userDefine);
    try {
      begin();
      sql = String.valueOf(sql) + " and icpo.channelId=" + channelId;
      Iterator it = this.session.iterate(sql);
      if (it.hasNext())
        result = Boolean.TRUE; 
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer delComment(String commentId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      this.session.delete("from com.js.oa.info.infomanager.po.InformationCommentPO po where po.commentId=" + 
          commentId);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getInformationModiIds(String channelId, String userId, String orgId, String orgIdString, String inforIds, List<Object[]> rightList) throws Exception {
    StringBuffer buffer = new StringBuffer();
    begin();
    try {
      orgIdString = buffer.append("$").append(orgIdString).append("$")
        .toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List list = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      buffer = new StringBuffer(" where po.channelId=");
      buffer.append(channelId).append(" and (");
      int i;
      for (i = 0; list != null && i < list.size(); i++)
        buffer.append(" po.channelManagerGroup like '%@").append(
            list.get(i)).append("%@' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.channelManagerOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      buffer.append(" po.channelManager like '%$").append(userId).append(
          "$%')");
      int num = ((Integer)this.session.createQuery("select count(po.channelId) from com.js.oa.info.channelmanager.po.InformationChannelPO po " + 
          buffer.toString()).iterate().next()).intValue();
      if (num > 0) {
        String where = "";
        if (rightList != null && rightList.size() > 0) {
          Object[] obj = rightList.get(0);
          String scopeType = obj[0].toString();
          if ("0".equals(scopeType)) {
            where = " 1=1 ";
          } else if ("1".equals(scopeType)) {
            where = "po.informationIssuerId=" + userId;
          } else if ("2".equals(scopeType)) {
            String orgRange = getAllJuniorOrgIdByRange("*" + orgId + "*");
            if (orgRange.indexOf("a") > 0) {
              String[] tmp = orgRange.split("a");
              for (int k = 0; k < tmp.length; k++)
                where = String.valueOf(where) + "po.informationIssueOrgId in(" + tmp[k] + 
                  ") or "; 
              if (where.endsWith("or "))
                where = where.substring(0, where.length() - 3); 
            } else {
              where = "po.informationIssueOrgId in(" + orgRange + 
                ") ";
            } 
          } else if ("3".equals(scopeType)) {
            where = " po.informationIssueOrgId=" + orgId;
          } else {
            String orgRange = getAllJuniorOrgIdByRange((String)obj[1]);
            if ("".equals(orgRange)) {
              where = "1>2";
            } else if (orgRange.indexOf("a") > 0) {
              String[] tmp = orgRange.split("a");
              for (int k = 0; k < tmp.length; k++)
                where = String.valueOf(where) + "po.informationIssueOrgId in(" + tmp[k] + 
                  ") or "; 
              if (where.endsWith("or "))
                where = where.substring(0, where.length() - 3); 
            } else {
              where = "po.informationIssueOrgId in(" + orgRange + 
                ") ";
            } 
          } 
          if (!"".equals(where)) {
            where = 
              "select po.informationId from com.js.oa.info.infomanager.po.InformationPO po where po.informationId in (" + 
              inforIds + ") and (" + where + ")";
            list = this.session.createQuery(where).list();
            buffer = new StringBuffer();
            if (list != null) {
              for (i = 0; i < list.size(); i++)
                buffer.append(",").append(list.get(i)); 
              buffer.append(",");
            } 
          } 
        } 
      } 
      this.session.close();
    } catch (Exception ex) {
      buffer = new StringBuffer();
      this.session.close();
      ex.printStackTrace();
      throw ex;
    } finally {}
    return buffer.toString();
  }
  
  public String getAllJuniorOrgIdByRange(String range) throws Exception {
    String result = "-1,";
    try {
      StringBuffer where = new StringBuffer(" WHERE ");
      range = "*" + range + "*";
      String[] rangeArray = range.split("\\*\\*");
      int i = 0;
      for (i = 1; i < rangeArray.length; i++) {
        if (i > 1)
          where.append(" or "); 
        where.append(" org.orgIdString like '%$");
        where.append(rangeArray[i]);
        where.append("$%' ");
      } 
      List list = this.session.createQuery(
          "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
          where).list();
      int j = 900;
      StringBuffer tmp = new StringBuffer();
      for (i = 0; i < list.size(); i++) {
        tmp.append(list.get(i));
        if (i > j) {
          tmp.append("a");
          j += 900;
        } else {
          tmp.append(",");
        } 
      } 
      result = tmp.toString();
      if (result.length() > 0)
        result = result.substring(0, result.length() - 1); 
    } catch (Exception e) {
      throw e;
    } 
    return result;
  }
  
  public String deleteHistory(String historyId, String informationId) throws Exception {
    begin();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(
          "delete from oa_inforhistoryaccessory where history_id=" + 
          historyId);
      stmt.executeUpdate(
          "delete from oa_informationhistory where history_id=" + 
          historyId);
      stmt.close();
      conn.close();
      this.session.close();
    } catch (Exception ex) {
      if (conn != null && !conn.isClosed())
        conn.close(); 
      this.session.close();
      throw ex;
    } 
    return "1";
  }
  
  public List getAfficheList(String domainId, String userId, String orgId, String orgIdString, String hasRightStr) throws Exception {
    String channelType = "0";
    String userDefine = "0";
    Date now = new Date();
    String nowString = now.toLocaleString();
    List list = new ArrayList();
    boolean hasRight = false;
    if (hasRightStr != null && hasRightStr.equals("1"))
      hasRight = true; 
    try {
      begin();
      String viewSQL = 
        " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationIssuer, aaa.informationVersion,  aaa.informationIssueTime,aaa.informationSummary,aaa.informationHead, aaa.informationType, aaa.informationCommonNum, bbb.channelName, bbb.channelId, aaa.titleColor,aaa.isConf,aaa.documentNo,aaa.transmitToWebsite,aaa.informationModifyTime,aaa.orderCode,aaa.informationIssueOrg";
      String fromSQL = 
        " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
      String whereSQL = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      whereSQL = " where aaa.domainId=" + domainId + 
        " and ( bbb.afficheChannelStatus='1' ) and aaa.informationStatus=0 ";
      if (databaseType.indexOf("mysql") >= 0) {
        whereSQL = String.valueOf(whereSQL) + " and ( '" + nowString + 
          "' between aaa.validBeginTime and aaa.validEndTime ) ";
      } else {
        whereSQL = String.valueOf(whereSQL) + " and (  JSDB.FN_STRTODATE('" + nowString + 
          "','L') between aaa.validBeginTime and aaa.validEndTime ) ";
      } 
      Query query1 = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId);
      List<String> groupList = query1.list();
      String readerWhere1 = " and (";
      readerWhere1 = String.valueOf(readerWhere1) + "((aaa.informationReader is null or aaa.informationReader ='') and (aaa.informationReaderOrg is null or aaa.informationReaderOrg='') and ( aaa.informationReaderGroup is null or aaa.informationReaderGroup='') ) ";
      if (orgIdString != null && orgIdString.length() > 3) {
        String cStr = orgIdString.substring(1, orgIdString.length() - 1);
        cStr = cStr.replaceAll("\\$", ",");
        cStr = cStr.replaceAll(",,", ",");
        String[] gg1 = cStr.split(",");
        if (gg1 != null && gg1.length > 0)
          for (int i = 0; i < gg1.length; i++)
            readerWhere1 = String.valueOf(readerWhere1) + " or aaa.informationReaderOrg like '%*" + 
              gg1[i] + "*%' ";  
      } 
      readerWhere1 = String.valueOf(readerWhere1) + " or aaa.informationReader like '%$" + userId + 
        "$%' ";
      readerWhere1 = String.valueOf(readerWhere1) + " or aaa.informationIssuerId = " + userId;
      if (groupList != null && groupList.size() > 0)
        for (int i = 0; i < groupList.size(); i++) {
          String groupId = groupList.get(i);
          readerWhere1 = String.valueOf(readerWhere1) + "  or  aaa.informationReaderGroup like '%@" + 
            groupId + "@%' ";
        }  
      readerWhere1 = String.valueOf(readerWhere1) + " ) ";
      whereSQL = String.valueOf(whereSQL) + readerWhere1;
      whereSQL = String.valueOf(whereSQL) + " and bbb.channelType = " + channelType;
      whereSQL = String.valueOf(whereSQL) + " order by aaa.orderCode desc,aaa.informationModifyTime desc,aaa.informationId desc";
      String sql = " select " + viewSQL + " from " + fromSQL + whereSQL;
      Query query = this.session.createQuery(sql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public boolean setInformationStatus(String informationId, String status) throws Exception {
    boolean result = false;
    int intStatus = Integer.parseInt(status);
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      String sql = "update oa_information set InformationStatus=" + intStatus + ",InformationModifyTime=sysdate where information_id=" + informationId;
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getManagedChannel(String userId, String orgId, String channelType, String userDefine) throws Exception {
    String hSql = "";
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if (channelType.equals("0") || userDefine.equals("1")) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ")) >0 ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          
          orgId + "$%' ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        int i = 0;
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " icpo.createdEmp = " + userId;
          } else if (scopeType.equals("2")) {
            for (i = 0; i < orgChildList.size(); i++) {
              if (i == 0) {
                scopeString = "icpo.createdOrg = " + 
                  orgChildList.get(i);
              } else {
                scopeString = String.valueOf(scopeString) + " or icpo.createdOrg = " + 
                  orgChildList.get(i);
              } 
            } 
          } else if (scopeType.equals("3")) {
            scopeString = "icpo.createdOrg = " + orgId;
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (i = 0; i < tmpOrgList.size(); i++) {
              if (i == 0) {
                scopeString = "icpo.createdOrg = " + 
                  tmpOrgList.get(i);
              } else {
                scopeString = String.valueOf(scopeString) + " or icpo.createdOrg = " + 
                  tmpOrgList.get(i);
              } 
            } 
          } 
          if ("".equals(scopeString))
            scopeString = " 1=1 "; 
          if (channelType.equals("-1")) {
            hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

              
              scopeString + ") and  (icpo.channelType > 0) and (icpo.userDefine=0) and (org.orgId=icpo.channelType) ";
          } else {
            hSql = String.valueOf(hSql) + " where (" + scopeString + 
              ") and icpo.channelType = " + channelType;
          } 
        } else {
          allScope = true;
          if (channelType.equals("-1")) {
            hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where icpo.channelType >0 and icpo.userDefine=0 and (org.orgId=icpo.channelType)";
          } else {
            hSql = String.valueOf(hSql) + " where icpo.channelType = " + 
              channelType;
          } 
        } 
      } else {
        hSql = "-1";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hSql;
  }
  
  public String getManagedChannelStr(String userId, String orgId, String channelType, String userDefine) throws Exception {
    String hSql = "";
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if (channelType.equals("0") || userDefine.equals("1")) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ")) >0 ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          
          orgId + "$%' ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        int i = 0;
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " icpo.createdEmp = " + userId;
          } else if (scopeType.equals("2")) {
            for (i = 0; i < orgChildList.size(); i++) {
              if (i == 0) {
                scopeString = "icpo.createdOrg = " + 
                  orgChildList.get(i);
              } else {
                scopeString = String.valueOf(scopeString) + " or icpo.createdOrg = " + 
                  orgChildList.get(i);
              } 
            } 
          } else if (scopeType.equals("3")) {
            scopeString = "icpo.createdOrg = " + orgId;
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (i = 0; i < tmpOrgList.size(); i++) {
              if (i == 0) {
                scopeString = "icpo.createdOrg = " + 
                  tmpOrgList.get(i);
              } else {
                scopeString = String.valueOf(scopeString) + " or icpo.createdOrg = " + 
                  tmpOrgList.get(i);
              } 
            } 
          } 
          if ("".equals(scopeString))
            scopeString = " 1=1 "; 
          if (channelType.equals("-1")) {
            hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

              
              scopeString + ") and  (icpo.channelType > 0) and (icpo.userDefine=0) and (org.orgId=icpo.channelType) ";
          } else {
            hSql = String.valueOf(hSql) + " where (" + scopeString + 
              ") and icpo.channelType = " + channelType;
          } 
        } else {
          allScope = true;
          if (channelType.equals("-1")) {
            hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where icpo.channelType >0 and icpo.userDefine=0 and (org.orgId=icpo.channelType)";
          } else {
            hSql = String.valueOf(hSql) + " where icpo.channelType = " + 
              channelType;
          } 
        } 
      } else {
        hSql = "-1";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    begin();
    List<String> list_str = null;
    String returnVal = "";
    try {
      if (!hSql.equals("-1") && !hSql.equals("")) {
        Query query = this.session.createQuery(hSql);
        list_str = query.list();
      } 
      for (int j = 0; j < list_str.size(); j++)
        returnVal = String.valueOf(returnVal) + "'" + list_str.get(j) + "',"; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return returnVal;
  }
  
  public List getAllGroupByUserId(String userId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId);
      list = query.list();
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
  
  public Boolean channelCanView2(String userId, String orgId, String channelType, String userDefine, String channelId) throws Exception {
    Boolean result = Boolean.FALSE;
    String sql = getUserViewCh3(userId, orgId, channelType, userDefine);
    try {
      begin();
      sql = String.valueOf(sql) + " and icpo.channelId=" + channelId;
      Iterator it = this.session.iterate(sql);
      if (it.hasNext())
        result = Boolean.TRUE; 
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getUserViewCh3(String userId, String orgId, String channelType, String userDefine) throws Exception {
    String hSql = "";
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if (channelType.equals("0") || userDefine.equals("1")) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          
          orgId + "$%' ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " icpo.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgChildList.size(); i++)
              scopeString = String.valueOf(scopeString) + "icpo.createdOrg = " + 
                orgChildList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "icpo.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + 
                "icpo.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String whOrgString = "";
        String whGroupString = "";
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++) {
          orgString = String.valueOf(orgString) + " icpo.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or ";
          whOrgString = String.valueOf(whOrgString) + 
            " icpo.channelManagerOrg like '%*" + 
            orgList.get(i) + "*%' or ";
        } 
        whOrgString = String.valueOf(whOrgString) + " icpo.channelManagerOrg like '%*-1*%' or ";
        orgString = String.valueOf(orgString) + " icpo.channelReaderOrg like '%*-1*%' or ";
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++) {
          groupString = String.valueOf(groupString) + 
            " icpo.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or ";
          whGroupString = String.valueOf(whGroupString) + 
            " icpo.channelManagerGroup like '%@" + 
            tmpList.get(j) + "@%' or ";
        } 
        if (channelType.equals("-1")) {
          hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

            
            scopeString + orgString + groupString + whOrgString + 
            whGroupString + " icpo.channelReader like '%$" + 
            userId + "$%' or icpo.channelManager like '%$" + 
            userId + "$%' or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='')))  and (icpo.channelType > 0) and (icpo.userDefine=0) and (org.orgId=icpo.channelType) ";
        } else {
          hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
            groupString + whOrgString + whGroupString + 
            " icpo.channelReader like '%$" + userId + 
            "$%' or icpo.channelManager like '%$" + userId + "$%' or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='')) ) and icpo.channelType = " + 
            channelType;
        } 
      } else if (channelType.equals("-1")) {
        hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where icpo.channelType >0 and icpo.userDefine=0 and (org.orgId=icpo.channelType)";
      } else {
        hSql = String.valueOf(hSql) + " where icpo.channelType = " + channelType;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hSql;
  }
  
  public String getAllInfoChannel(String userId, String orgId, String channelType, String userDefine) throws Exception {
    String hSql = "";
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if (channelType.equals("0") || userDefine.equals("1")) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          
          orgId + "$%' ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        int i = 0;
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " icpo.createdEmp = " + userId;
          } else if (scopeType.equals("2")) {
            for (i = 0; i < orgChildList.size(); i++) {
              if (i == 0) {
                scopeString = "icpo.createdOrg = " + 
                  orgChildList.get(i);
              } else {
                scopeString = String.valueOf(scopeString) + " or icpo.createdOrg = " + 
                  orgChildList.get(i);
              } 
            } 
          } else if (scopeType.equals("3")) {
            scopeString = "icpo.createdOrg = " + orgId;
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (i = 0; i < tmpOrgList.size(); i++) {
              if (i == 0) {
                scopeString = "icpo.createdOrg = " + 
                  tmpOrgList.get(i);
              } else {
                scopeString = String.valueOf(scopeString) + " or icpo.createdOrg = " + 
                  tmpOrgList.get(i);
              } 
            } 
          } 
          if ("".equals(scopeString))
            scopeString = " 1=1 "; 
          if (channelType.equals("-1")) {
            hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

              
              scopeString + ") and  (icpo.channelType > 0) and (icpo.userDefine=0) and (org.orgId=icpo.channelType) ";
          } else {
            hSql = String.valueOf(hSql) + " where (" + scopeString + 
              ") and icpo.channelType = " + channelType;
          } 
        } else {
          allScope = true;
          if (channelType.equals("-1")) {
            hSql = "select icpo.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO icpo ,com.js.system.vo.organizationmanager.OrganizationVO org where icpo.channelType >0 and icpo.userDefine=0 and (org.orgId=icpo.channelType)";
          } else {
            hSql = String.valueOf(hSql) + " where icpo.channelType = " + 
              channelType;
          } 
        } 
      } else {
        hSql = "-1";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hSql;
  }
  
  public List getAssociateInfo(String orgId, String infoId, String userId, String orgIdString, String channelType, String userDefine, String channelStatusType) throws Exception {
    List list = null;
    String readWhere = getInfoReader(userId, "", orgIdString, "aaa");
    String hSql = getUserViewCh(userId, orgId, channelType, userDefine);
    begin();
    try {
      String domainId = this.session.createQuery("select po.domainId from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
          userId).iterate().next()
        .toString();
      String key = "";
      Iterator it = this.session.createQuery("select aaa.informationKey from com.js.oa.info.infomanager.po.InformationPO aaa where aaa.informationId = " + 
          infoId).iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null)
          key = obj.toString(); 
      } 
      StringBuffer buffer = new StringBuffer("select distinct bbb.channelName, aaa.informationId, aaa.informationTitle, aaa.informationSummary,aaa.informationModifyTime, aaa.informationHead, aaa.informationType,bbb.channelId ");
      buffer.append("from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb ")
        .append("where aaa.domainId=").append(domainId);
      if (channelStatusType.equals("2")) {
        buffer.append(" and ( bbb.afficheChannelStatus='" + 
            channelStatusType + "' )")
          .append("and (");
      } else {
        buffer.append(" and ((").append(readWhere).append(
            ") and bbb.channelId in (").append(hSql).append(")) ")
          .append(" and ( bbb.afficheChannelStatus='" + 
            channelStatusType + "' )")
          .append("and (");
      } 
      if (!"".equals(key))
        buffer.append("(aaa.informationKey='").append(key).append(
            "') or"); 
      buffer.append(" aaa.informationId in (select ddd.associateInfo from com.js.oa.info.infomanager.po.AssociateInfoPO ddd where ddd.masterInfo = ")
        .append(infoId).append(")")
        .append(") and aaa.informationId <> ").append(infoId)
        .append(" order by aaa.informationId desc");
      list = this.session.createQuery(buffer.toString()).list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Long saveIsoPaperPO(IsoPaperPO po) throws Exception {
    Long result = new Long(-1L);
    begin();
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String setPaperPOStatus(String id, String status) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("update JSDB.OA_ISO_Paper set paperStatus= '" + 
          status + "' where isoPaperId=" + id);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String updatePaperPO(String id, String[] arg) throws Exception {
    begin();
    String result = "0";
    try {
      IsoPaperPO isoPaperPO = (IsoPaperPO)this.session.load(IsoPaperPO.class, 
          new Long(id));
      if (arg.length > 0)
        isoPaperPO.setPaperStatus(arg[0]); 
      if (arg.length > 1)
        isoPaperPO.setBackUserId(new Long(arg[1])); 
      if (arg.length > 2)
        isoPaperPO.setBackUserName(arg[2]); 
      isoPaperPO.setBackTime(new Date());
      this.session.update(isoPaperPO);
      this.session.flush();
    } catch (Exception e) {
      result = "-1";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String deletePaperPO(String id) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "delete  from  JSDB.OA_ISO_Paper  where isoPaperId in ( " + 
          id + " )");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Long saveBorrowPO(IsoBorrowUserPO po) throws Exception {
    begin();
    Long result = new Long(-1L);
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      result = new Long(-1L);
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Long updateBorrowPO(IsoBorrowUserPO po) throws Exception {
    begin();
    Long result = new Long(-1L);
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      result = new Long(-1L);
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String setBorrowStatus(String id, String status) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "update JSDB.OA_ISO_BorrowUser set  borrowStatus= '" + 
          status + "' where isoBorrowUserId=" + id);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public IsoBorrowUserPO loadBorrowUserPO(String id) throws Exception {
    begin();
    IsoBorrowUserPO po = null;
    try {
      po = (IsoBorrowUserPO)this.session.load(IsoBorrowUserPO.class, 
          new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public String deleteBorrow(String ids) throws Exception {
    begin();
    String bl = "0";
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.info.isodoc.po.IsoBorrowUserPO  po where po.cardOrderId in (" + 
            ids + ")"); 
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=36");
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID in (" + 
          ids.substring(0, ids.length() - 1) + ")");
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID in (" + 
          ids.substring(0, ids.length() - 1) + ")");
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID in (" + 
          ids.substring(0, ids.length() - 1) + ")");
      bl = "1";
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return bl;
  }
  
  public List findIdsFromBorrow(String informationId, String informationOldId, String userId) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String sql = "";
      Date now = new Date();
      String nowString = now.toLocaleString();
      nowString = nowString.substring(0, nowString.indexOf(" "));
      String nowB = String.valueOf(nowString) + " 23:59:59";
      String nowE = String.valueOf(nowString) + " 00:00:00";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = " select po  from  com.js.oa.info.isodoc.po.IsoBorrowUserPO po where ( po.informationId=" + 
          informationId + " ) and po.userId= " + userId + " ";
        if (informationOldId != null && !informationOldId.equals(""))
          sql = " select po  from  com.js.oa.info.isodoc.po.IsoBorrowUserPO po where ( po.informationId=" + 
            informationId + " or " + informationOldId + 
            " like concat('%$', po.informationId, '$%') ) and po.userId= " + 
            userId + " "; 
        sql = String.valueOf(sql) + " and '" + nowB + "' > =po.borrowBeginTime and '" + nowE + 
          "' < =po.borrowEndTime";
      } else {
        sql = " select po from  com.js.oa.info.isodoc.po.IsoBorrowUserPO po where ( po.informationId=" + 
          informationId + ") and po.userId= " + userId + " ";
        if (informationOldId != null && !informationOldId.equals(""))
          if (databaseType.indexOf("db2") >= 0) {
            sql = " select po from  com.js.oa.info.isodoc.po.IsoBorrowUserPO po where ( po.informationId=" + 
              informationId + " or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR( po.informationId)), '$'),'" + informationOldId + "')>0)  and po.userId= " + 
              userId + " ";
          } else {
            sql = " select po from  com.js.oa.info.isodoc.po.IsoBorrowUserPO po where ( po.informationId=" + 
              informationId + " or " + informationOldId + " like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR( po.informationId)), '$%')) and po.userId= " + 
              userId + " ";
          }  
        sql = String.valueOf(sql) + " and  JSDB.FN_STRTODATE('" + nowB + 
          "','L') >=po.borrowBeginTime and JSDB.FN_STRTODATE('" + 
          nowE + "','L') < =po.borrowEndTime";
      } 
      sql = String.valueOf(sql) + " and po.borrowStatus='1' ";
      list = this.session.find(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean setInformationStatus(String informationId, String informationStatus, String isoDocStatus) throws Exception {
    boolean result = false;
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(
          InformationPO.class, new Long(informationId));
      informationPO.setInformationStatus(Integer.parseInt(
            informationStatus));
      informationPO.setIsoDocStatus(isoDocStatus);
      informationPO.setIsoDealCategory("2");
      informationPO.setInformationModifyTime(new Date());
      this.session.flush();
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean setInformationStatus(String informationId, String informationStatus, String isoDocStatus, String orgName, String userName) throws Exception {
    boolean result = false;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      String sql = "update oa_information set IsoDocStatus='" + isoDocStatus + "',IsoDealCategory='2',InforModifyMen='" + userName + "',InforModifyOrg='" + orgName + "',InformationModifyTime=sysdate where information_id=" + informationId;
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public void updateBigVersion(String informationId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String version = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select InformationVersion from oa_information where information_id=" + informationId);
      if (rs.next())
        version = rs.getString(1); 
      int middle = 1;
      int a = Integer.parseInt(version.substring(0, 
            version.indexOf(".")));
      String b = version.substring(version.indexOf(".") + 1, 
          version.length());
      version = (a + 1) + "." + "0";
      stmt.executeUpdate("update oa_information set InformationVersion='" + version + "' where information_id=" + informationId);
      rs.close();
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } 
  }
  
  public List getInforByVersion(String informationId, String version) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.historyVersion,aaa.historyIssueOrg,aaa.historyIssuerName,aaa.historyTime,aaa.historyId, aaa.historyHead,ccc.channelId,ccc.channelType,aaa.historyTitle, aaa.historySubTitle,aaa.historyContent,aaa.historyIssuerId, aaa.historyKey,aaa.historyHead,aaa.historyHeadFile,aaa.historySummary, aaa.historyRedIssueTime,aaa.historyRedIssueOrg,aaa.historyAuthor from com.js.oa.info.infomanager.po.InformationHistoryPO aaa join aaa.information bbb join bbb.informationChannel ccc where bbb.informationId = " + 





          
          informationId + 
          " and aaa.historyVersion= '" + 
          version + 
          "' and aaa.isoDealCategory<>'2' and aaa.isoDealCategory<>'3' and aaa.isoDealCategory<>'4'  order by aaa.historyTime");
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
  
  public String getHisModiNum(String informationId) throws Exception {
    int i;
    String result = "1";
    try {
      begin();
      Query query = 
        this.session.createQuery(
          "select count( aaa.historyId ) from  com.js.oa.info.infomanager.po.InformationHistoryPO aaa join aaa.information bbb where bbb.informationId=" + 
          informationId);
      Integer rr = query.iterate().next();
      int irr = rr.intValue();
      i = ++irr;
    } catch (Exception ex) {
      System.out.println(ex);
      throw ex;
    } finally {}
    return i;
  }
  
  public List getCanVindicate_ISO(String where, String userId, String orgId, String domainId) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    try {
      String mySql = "select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        where + ") and (aaa.channelType=0 or aaa.userDefine=1) and  aaa.afficheChannelStatus='2' and aaa.domainId=" + 
        domainId + 
        " order by aaa.channelIdString, aaa.channelType";
      Query query = this.session.createQuery(mySql);
      Iterator<Object[]> iter = query.iterate();
      Iterator iter2 = null;
      StringBuffer channelNameBuf = new StringBuffer();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[4].toString().equals("1"))
          if (obj[2].toString().equals("0")) {
            obj[1] = "文档管理·" + obj[1];
          } else {
            query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
                obj[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              obj[1] = (new StringBuilder()).append(iter2.next()).append("·").append(obj[1]).toString(); 
          }  
        alist.add(obj);
      } 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public IsoPaperPO loadIsoPaperPO(String id) throws Exception {
    begin();
    IsoPaperPO po = null;
    try {
      po = (IsoPaperPO)this.session.load(IsoPaperPO.class, new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public List getBrowByEmpAndIfoId(String informationId, String userId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.browserId from  com.js.oa.info.infomanager.po.InformationBrowserPO aaa join aaa.information bbb where  aaa.empId=" + userId + " and  bbb.informationId =" + informationId);
      list = query.list();
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
  
  public Long saveIsoCommentPO(IsoCommentPO po) throws Exception {
    begin();
    Long result = new Long(-1L);
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      result = new Long(-1L);
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getIsoCommentList(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.dealComment,aaa.acName,aaa.dealDate,aaa.dealEmpName,aaa.inforversion, aaa.infodealType from  com.js.oa.info.isodoc.po.IsoCommentPO aaa  where  aaa.informationId =" + informationId + " order by aaa.id ");
      list = query.list();
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
  
  public List getCommentList(String recordId, String tableId, String processId) throws Exception {
    String comment = "", empName = "", dealwithDate = "", isStandForComm = "", standForUserName = "", activityName = "";
    List<Object[]> list = new ArrayList();
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME, A.activityname from  JSF_DEALWITHCOMMENT B  join JSF_DEALWITH A  on  A.WF_DEALWITH_ID=B.WF_DEALWITH_ID  join  ORG_EMPLOYEE C on  B.DEALWITHEMPLOYEE_ID=C.EMP_ID  where A.WF_DEALWITH_ID in (SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + tableId + " AND DATABASERECORD_ID=" + recordId + ") order by A.curstepcount ");
      while (rs.next()) {
        comment = rs.getString(1);
        empName = rs.getString(2);
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        activityName = rs.getString(6);
        Object[] obj = { comment, empName, dealwithDate, 
            isStandForComm, standForUserName, activityName };
        list.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
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
  
  public void TransferUserId(String informationId, String userId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate("update oa_information set twoUserId=" + userId + " where information_id=" + informationId);
      conn.commit();
      stmt.close();
      conn.setAutoCommit(status);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public Long saveIsoDeallogPO(IsoDeallogPO po) throws Exception {
    begin();
    Long result = new Long(-1L);
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      result = new Long(-1L);
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getIsoDeallogList(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.startUser,aaa.dealDate,aaa.dealType,aaa.endUser,aaa.inforversion, aaa.infodealType from  com.js.oa.info.isodoc.po.IsoDeallogPO aaa  where  aaa.informationId =" + informationId + " order by aaa.id ");
      list = query.list();
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
  
  public Integer getIssueNumOrg(String orgId) throws Exception {
    Integer rs = Integer.valueOf("0");
    begin();
    try {
      Iterator<E> it = this.session.createQuery("select count(po.informationId) from com.js.oa.info.infomanager.po.InformationPO po join po.informationChannel ch where po.informationIssueOrgId=" + orgId + " and ch.channelType=0 and ( po.afficeHistoryDate  is null or  po.afficeHistoryDate = -1)  and (po.informationOrISODoc is null or po.informationOrISODoc='0' )").iterate();
      if (it != null && it.hasNext())
        rs = Integer.valueOf(it.next().toString()); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return rs;
  }
  
  public Integer getIssueNumPerson(String userId) throws Exception {
    Integer rs = Integer.valueOf("0");
    begin();
    try {
      Iterator<E> it = this.session.createQuery("select count(po.informationId) from com.js.oa.info.infomanager.po.InformationPO po join po.informationChannel ch where po.informationStatus=0 and po.informationIssuerId=" + userId + " and ch.channelType=0 and ( po.afficeHistoryDate  is null or  po.afficeHistoryDate = -1)  and (po.informationOrISODoc is null or po.informationOrISODoc='0' )").iterate();
      if (it != null && it.hasNext())
        rs = Integer.valueOf(it.next().toString()); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return rs;
  }
  
  public Integer getNumForOrg(String orgId, Long infoid, String[] rangeArr) throws Exception {
    Integer rs = Integer.valueOf("0");
    String orgidnew = "$" + orgId + "$";
    begin();
    try {
      String sqlString = "select count(po.browserId) from com.js.oa.info.infomanager.po.InformationBrowserPO po ,com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.userIsActive=1 and emp.empId =po.empId and po.information.informationId =" + 
        
        infoid + " and po.browserOrgIdStr like '%" + orgidnew + "%' ";
      String conditionString = "and(";
      for (int i = 0; i < rangeArr.length; i++) {
        if (i == 0) {
          conditionString = String.valueOf(conditionString) + " po.browserOrgIdStr like '%" + rangeArr[i] + "%' ";
        } else {
          conditionString = String.valueOf(conditionString) + " or po.browserOrgIdStr like '%" + rangeArr[i] + "%' ";
        } 
      } 
      conditionString = String.valueOf(conditionString) + ")";
      if (conditionString.length() > 5)
        sqlString = String.valueOf(sqlString) + conditionString; 
      Iterator<E> it = this.session.createQuery(sqlString).iterate();
      if (it != null && it.hasNext())
        rs = Integer.valueOf(it.next().toString()); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return rs;
  }
  
  public Integer getNumForGroup(String groupId, Long infoId) throws Exception {
    Integer rs = Integer.valueOf("0");
    begin();
    try {
      Iterator<E> iterator = this.session.createQuery("select po.groupUserString from com.js.system.vo.groupmanager.GroupVO po where po.groupId=" + groupId).iterate();
      if (iterator != null && iterator.hasNext()) {
        String groupUserString = iterator.next().toString();
        groupUserString = groupUserString.replaceAll("\\$\\$", ",");
        groupUserString = groupUserString.replaceAll("\\$", "");
        Iterator<E> it = this.session.createQuery("select count(po.browserId) from com.js.oa.info.infomanager.po.InformationBrowserPO po where po.information.informationId =" + infoId + " and po.empId in(" + groupUserString + ")").iterate();
        if (it != null && it.hasNext())
          rs = Integer.valueOf(it.next().toString()); 
      } 
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      throw e;
    } 
    return rs;
  }
  
  public List getBrowser1(String orgId, Long infoid) throws Exception {
    Object[] obj = (Object[])null;
    List<Object[]> browserlist = new ArrayList();
    String orgidnew = "$" + orgId + "$";
    begin();
    try {
      List list = this.session.createQuery("select emp.empId,emp.empName,emp.empMobilePhone ,emp.userAccounts,emp.userOnline,emp.empEmail from com.js.system.vo.usermanager.EmployeeVO emp , com.js.oa.info.infomanager.po.InformationBrowserPO browserPO where emp.userIsDeleted=0 and emp.userIsActive=1 and emp.empId =browserPO.empId and browserPO.browserOrgIdStr like '%" + orgidnew + "%' and browserPO.information.informationId =" + infoid + " order by empDutyLevel,userOrderCode").list();
      for (Iterator<Object[]> iter = list.iterator(); iter.hasNext(); ) {
        obj = iter.next();
        if ("".equals(obj[2]))
          obj[2] = null; 
        browserlist.add(obj);
      } 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public List getNoBrowser1(String orgId, Long infoid) throws Exception {
    List<Object[]> browserlist = new ArrayList();
    begin();
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      st = conn.createStatement();
      rs = st.executeQuery("select ee.emp_Id,ee.empName,ee.empMobilePhone,ee.userAccounts,ee.userOnline ,ee.empEmail from org_employee ee where ee.userisdeleted=0 and ee.userisactive=1 and ee.emp_id not in (select browser.EMP_ID from oa_informationbrowser browser where browser.INFORMATION_ID =" + infoid + ") and ee.emp_id in(select ou.emp_id from org_organization_user ou where ou.org_id in(select org_id from org_organization where orgidstring like '%$" + orgId + "$%')) " + " order by empDutyLevel,userOrderCode");
      while (rs.next()) {
        Object[] obj = new Object[6];
        obj[0] = Long.valueOf(rs.getLong(1));
        obj[1] = rs.getString(2);
        if ("".equals(rs.getString(3))) {
          obj[2] = null;
        } else {
          obj[2] = rs.getString(3);
        } 
        obj[3] = rs.getString(4);
        obj[4] = Integer.valueOf(rs.getInt(5));
        obj[5] = rs.getString(6);
        browserlist.add(obj);
      } 
      st.close();
      rs.close();
      conn.close();
    } catch (Exception ex) {
      st.close();
      rs.close();
      conn.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public List getBrowserInGroup(String groupId, Long infoId) throws Exception {
    List<Object[]> browserlist = new ArrayList();
    begin();
    try {
      Iterator<E> iterator = this.session.createQuery("select po.groupUserString from com.js.system.vo.groupmanager.GroupVO po where po.groupId=" + groupId).iterate();
      if (iterator != null && iterator.hasNext()) {
        String groupUserString = iterator.next().toString();
        groupUserString = groupUserString.replaceAll("\\$\\$", ",");
        groupUserString = groupUserString.replaceAll("\\$", "");
        List list = this.session.createQuery("select emp.empId,emp.empName,emp.empMobilePhone,emp.userAccounts,emp.userOnline,emp.empEmail from com.js.system.vo.usermanager.EmployeeVO emp,com.js.oa.info.infomanager.po.InformationBrowserPO browserPO where emp.userIsDeleted = 0 and emp.userIsActive = 1 and emp.empId = browserPO.empId and browserPO.empId in(" + groupUserString + ") and browserPO.information.informationId = " + infoId).list();
        Iterator<Object[]> it = list.iterator();
        Object[] objects = (Object[])null;
        while (it.hasNext()) {
          objects = it.next();
          browserlist.add(objects);
        } 
        this.session.close();
      } 
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public List getNoBrowserInGroup(String groupId, Long infoId) throws Exception {
    List<Object[]> browserlist = new ArrayList();
    begin();
    try {
      Iterator<E> iterator = this.session.createQuery("select po.groupUserString from com.js.system.vo.groupmanager.GroupVO po where po.groupId=" + groupId).iterate();
      if (iterator != null && iterator.hasNext()) {
        String groupUserString = iterator.next().toString();
        groupUserString = groupUserString.replaceAll("\\$\\$", ",");
        groupUserString = groupUserString.replaceAll("\\$", "");
        List list = this.session.createQuery("select empPO.empId,empPO.empName,empPO.empMobilePhone,empPO.userAccounts,empPO.userOnline,empPO.empEmail from com.js.system.vo.usermanager.EmployeeVO empPO where empPO.empId in(" + groupUserString + ") and empPO.empId not in (select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp,com.js.oa.info.infomanager.po.InformationBrowserPO browserPO where emp.userIsDeleted = 0 and emp.userIsActive = 1 and emp.empId = browserPO.empId and browserPO.empId in(" + groupUserString + ") and emp.empId in (" + groupUserString + ") and browserPO.information.informationId = " + infoId + ")").list();
        Iterator<Object[]> it = list.iterator();
        Object[] objects = (Object[])null;
        while (it.hasNext()) {
          objects = it.next();
          browserlist.add(objects);
        } 
        this.session.close();
      } 
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public List getBrowserInUsers(String userIds, Long infoId) throws Exception {
    Pattern p = null;
    Matcher m = null;
    p = Pattern.compile("@[0-9]*@");
    m = p.matcher(userIds);
    userIds = m.replaceAll("");
    p = Pattern.compile("\\*[0-9]*\\*");
    m = p.matcher(userIds);
    userIds = m.replaceAll("");
    userIds = userIds.replaceAll("\\$\\$", ",");
    userIds = userIds.replaceAll("\\$", "");
    List<Object[]> browserlist = new ArrayList();
    begin();
    try {
      List list = this.session.createQuery("select emp.empId,emp.empName,emp.empMobilePhone,emp.userAccounts,emp.userOnline,emp.empEmail from com.js.system.vo.usermanager.EmployeeVO emp,com.js.oa.info.infomanager.po.InformationBrowserPO browserPO where emp.userIsDeleted = 0 and emp.userIsActive = 1 and emp.empId = browserPO.empId and browserPO.empId in(" + userIds + ") and browserPO.information.informationId = " + infoId).list();
      Iterator<Object[]> it = list.iterator();
      Object[] objects = (Object[])null;
      while (it.hasNext()) {
        objects = it.next();
        browserlist.add(objects);
      } 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public List getNoBrowserInUsers(String userIds, Long infoId) throws Exception {
    Pattern p = null;
    Matcher m = null;
    p = Pattern.compile("@[0-9]*@");
    m = p.matcher(userIds);
    userIds = m.replaceAll("");
    p = Pattern.compile("\\*[0-9]*\\*");
    m = p.matcher(userIds);
    userIds = m.replaceAll("");
    userIds = userIds.replaceAll("\\$\\$", ",");
    userIds = userIds.replaceAll("\\$", "");
    List<Object[]> browserlist = new ArrayList();
    begin();
    try {
      List list = this.session.createQuery("select empPO.empId,empPO.empName,empPO.empMobilePhone,empPO.userAccounts,empPO.userOnline,empPO.empEmail from com.js.system.vo.usermanager.EmployeeVO empPO where empPO.empId in(" + userIds + ") and empPO.empId not in (select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp,com.js.oa.info.infomanager.po.InformationBrowserPO browserPO where emp.userIsDeleted = 0 and emp.userIsActive = 1 and emp.empId = browserPO.empId and browserPO.empId in(" + userIds + ") and emp.empId in (" + userIds + ") and browserPO.information.informationId = " + infoId + ")").list();
      Iterator<Object[]> it = list.iterator();
      Object[] objects = (Object[])null;
      while (it.hasNext()) {
        objects = it.next();
        browserlist.add(objects);
      } 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public boolean saveDocFile(List<List> list, String curUserId) {
    try {
      begin();
      InformationChannelPO channelPO = null;
      String userId = "0", userName = "";
      String orgId = "", orgName = "", orgIdString = "", domainId = "0", toUserIds = "", url = "", corpId = "0";
      Date now = new Date();
      int i = 0;
      for (i = 0; i < list.size(); i++) {
        List<E> fileList = list.get(i);
        String saveName = fileList.get(0).toString();
        String fileName = fileList.get(1).toString();
        String suffix = fileList.get(2).toString();
        userName = fileList.get(3).toString();
        userId = fileList.get(4).toString();
        domainId = fileList.get(9).toString();
        String channelId = fileList.get(10).toString();
        orgId = fileList.get(11).toString();
        orgName = fileList.get(12).toString();
        orgIdString = fileList.get(13).toString();
        if (fileList.size() > 13)
          corpId = fileList.get(14).toString(); 
        if (channelPO == null) {
          channelPO = (InformationChannelPO)this.session.load(InformationChannelPO.class, Long.valueOf(channelId));
          toUserIds = getChannelCanReadUserIds(userId, channelPO.getChannelReader(), channelPO.getChannelReaderOrg(), channelPO.getChannelReaderGroup());
        } 
        String[] arrayStrings = toUserIds.split(",");
        String userIds = "";
        for (int j = 0; j < arrayStrings.length; j++) {
          if (!arrayStrings[j].equals(curUserId))
            userIds = String.valueOf(userIds) + arrayStrings[j] + ","; 
        } 
        if (userIds.length() > 0)
          userIds = userIds.substring(0, userIds.length() - 1); 
        InformationPO po = new InformationPO();
        po.setInformationChannel(channelPO);
        po.setInformationTitle(fileName);
        po.setInformationSubTitle("");
        po.setInformationKey("");
        po.setInformationHead(0);
        po.setInformationType("3");
        po.setInformationSummary("");
        po.setInformationContent("");
        po.setInformationHeadFile(String.valueOf(saveName) + "." + suffix);
        po.setInformationStatus(0);
        po.setInformationAuthor("");
        po.setInformationIssuerId(Long.valueOf(Long.parseLong(userId)));
        po.setInformationIssuer(userName);
        po.setInformationIssueTime(now);
        po.setInformationModifyTime(now);
        po.setInformationIssueOrg(orgName);
        po.setInformationIssueOrgId(orgId);
        po.setIssueOrgIdString(orgIdString);
        po.setInformationReaderName(channelPO.getChannelReaderName());
        po.setInformationReader(channelPO.getChannelReader());
        po.setInformationReaderOrg(channelPO.getChannelReaderOrg());
        po.setInformationReaderGroup(channelPO.getChannelReaderGroup());
        po.setDomainId(Long.valueOf(domainId));
        po.setInformationVersion("1.0");
        po.setOrderCode("1000");
        po.setDisplayTitle(1);
        po.setOtherChannel(",0,");
        po.setTitleColor(Integer.valueOf(0));
        po.setModifyEmp("");
        po.setDossierStatus(Integer.valueOf(0));
        po.setMustRead(Integer.valueOf(0));
        po.setComeFrom("");
        po.setIsConf(Integer.valueOf(0));
        po.setDocumentNo("0");
        po.setDocumentEditor("");
        po.setDocumentType("0");
        po.setDisplayImage("1");
        po.setWordDisplayType("");
        po.setInformationOrISODoc("0");
        po.setIsoDocStatus("0");
        po.setIsoOldInfoId("");
        po.setIsoSecretStatus("0");
        po.setIsoDealCategory("");
        po.setIsoApplyName("");
        po.setIsoApplyId("");
        po.setIsoReceiveId("");
        po.setIsoReceiveName("");
        po.setIsoModifyReason("");
        po.setIsoAmendmentPage("");
        po.setIsoModifyVersion("");
        po.setInforModifyMen("");
        po.setInforModifyOrg("");
        po.setIsAllow("0");
        po.setOrderCodeTemp(Integer.valueOf(0));
        po.setCorpId(Long.valueOf(("".equals(corpId) || "null".equalsIgnoreCase(corpId)) ? "0" : corpId));
        Long infoId = (Long)this.session.save(po);
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        if ("1".equals(isearchSwitch) && infoId != null && ifActiveUpdateDelete != null && !"".equals(infoId) && !"".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.addIndex(infoId.toString(), "oa_information");
        } 
        url = "/jsoa/info/view_onlyfile.jsp?informationId=" + infoId;
        userIds = userIds.replaceAll(String.valueOf(curUserId) + ",", "").replaceAll(curUserId, "");
        String title = String.valueOf(userName) + "发布了:" + fileName;
        RemindUtil.sendMessageToUsersWithType(title, url, userIds, "Info", new Date(), new Date("2050/1/1"), 3);
        if ("-1".equals(userIds))
          StaticParam.deleteMessage("Info", title, url, curUserId); 
      } 
      if (i > 0) {
        long maxCount = 0L;
        Query query = this.session.createQuery(" select max(aaa.accumulateNum) from  com.js.oa.info.infomanager.po.InforPersonalStatPO aaa  where aaa.empId = " + 
            userId);
        list = query.list();
        if (list != null && list.size() > 0 && list.get(0) != null)
          maxCount = ((Long)list.get(0)).longValue(); 
        query = this.session.createQuery(" select aaa from com.js.oa.info.infomanager.po.InforPersonalStatPO aaa  where aaa.empId = " + 
            userId + 
            " and aaa.statYear = " + (
            now.getYear() + 1900) + 
            " and statMonth = " + (
            now.getMonth() + 1));
        list = query.list();
        InforPersonalStatPO inforPersonalStatPO = new InforPersonalStatPO();
        if (list != null && list.size() > 0 && list.get(0) != null) {
          inforPersonalStatPO = (InforPersonalStatPO)list.get(0);
          int monthIssueNum = inforPersonalStatPO.getMonthIssueNum();
          inforPersonalStatPO.setAccumulateNum(new Long(maxCount + i));
          inforPersonalStatPO.setMonthIssueNum(monthIssueNum + 1);
          inforPersonalStatPO.setEmpName(userName);
          inforPersonalStatPO.setOrgId(new Long(orgId));
          inforPersonalStatPO.setOrgIdString(orgIdString);
          inforPersonalStatPO.setOrgName(orgName);
          inforPersonalStatPO.setDomainId(Long.valueOf(domainId));
        } else {
          inforPersonalStatPO.setAccumulateNum(new Long(maxCount + i));
          inforPersonalStatPO.setEmpId(new Long(userId));
          inforPersonalStatPO.setEmpName(userName);
          inforPersonalStatPO.setMonthIssueNum(1);
          inforPersonalStatPO.setOrgId(new Long(orgId));
          inforPersonalStatPO.setOrgIdString(orgIdString);
          inforPersonalStatPO.setOrgName(orgName);
          inforPersonalStatPO.setStatMonth(now.getMonth() + 1);
          inforPersonalStatPO.setStatYear(now.getYear() + 1900);
          inforPersonalStatPO.setDomainId(Long.valueOf(domainId));
          this.session.save(inforPersonalStatPO);
        } 
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = 
            " select aaa.orgId,aaa.orgName,aaa.orgIdString,aaa.orgLevel,aaa.orgNameString  from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
            
            orgIdString + 
            "' like concat('%$', aaa.orgId, '$%') ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = 
            " select aaa.orgId,aaa.orgName,aaa.orgIdString,aaa.orgLevel,aaa.orgNameString  from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),'" + 
            
            orgIdString + "')>0";
        } else {
          tmpSql = 
            " select aaa.orgId,aaa.orgName,aaa.orgIdString,aaa.orgLevel,aaa.orgNameString  from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
            
            orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
        } 
        query = this.session.createQuery(tmpSql);
        list = query.list();
        for (int k = 0; k < list.size(); k++) {
          Object[] obj = (Object[])list.get(k);
          query = this.session.createQuery(
              " select max(aaa.accumulateNum) from  com.js.oa.info.infomanager.po.InforOrgStatPO aaa  where aaa.orgId = " + 
              
              obj[0]);
          List<Long> tmpList = query.list();
          maxCount = 0L;
          if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null)
            maxCount = ((Long)tmpList.get(0)).longValue(); 
          query = this.session.createQuery(" select aaa from  com.js.oa.info.infomanager.po.InforOrgStatPO aaa  where aaa.orgId = " + 
              
              obj[0] + 
              " and aaa.statYear = " + (
              now.getYear() + 1900) + 
              " and aaa.statMonth = " + (
              now.getMonth() + 1));
          tmpList = query.list();
          if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
            InforOrgStatPO inforOrgStatPO = (InforOrgStatPO)tmpList.get(0);
            inforOrgStatPO.setOrgIdString(obj[2].toString());
            inforOrgStatPO.setOrgName(obj[4].toString());
            inforOrgStatPO.setOrgLevel(Integer.parseInt(obj[3].toString()));
            inforOrgStatPO.setMonthIssueNum(inforOrgStatPO.getMonthIssueNum() + i);
            inforOrgStatPO.setAccumulateNum(new Long(maxCount + i));
            inforOrgStatPO.setDomainId(Long.valueOf(domainId));
          } else {
            InforOrgStatPO inforOrgStatPO = new InforOrgStatPO();
            inforOrgStatPO.setOrgId(new Long(obj[0].toString()));
            inforOrgStatPO.setOrgIdString(obj[2].toString());
            inforOrgStatPO.setOrgName(obj[4].toString());
            inforOrgStatPO.setStatMonth(now.getMonth() + 1);
            inforOrgStatPO.setStatYear(now.getYear() + 1900);
            inforOrgStatPO.setOrgLevel(Integer.parseInt(obj[3]
                  .toString()));
            inforOrgStatPO.setMonthIssueNum(1);
            inforOrgStatPO.setAccumulateNum(new Long(maxCount + i));
            inforOrgStatPO.setDomainId(Long.valueOf(domainId));
            this.session.save(inforOrgStatPO);
          } 
        } 
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return false;
  }
  
  public String getCanReadUserIds(String userId, String informationId) {
    String reader = "-1";
    try {
      begin();
      reader = getCanReadUserIdsImpl(userId, informationId);
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return reader;
  }
  
  public String getCanReadUserIdsImpl(String userId, String informationId) {
    String reader = "-1";
    String readOrg = "";
    String readGroup = "";
    try {
      int i = 0;
      List<Object[]> list = this.session.createQuery("select po.informationReader,po.informationReaderOrg,po.informationReaderGroup from com.js.oa.info.infomanager.po.InformationPO po where po.informationId=" + informationId).list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        reader = (obj[0] == null) ? "" : obj[0].toString();
        readOrg = (obj[1] == null) ? "" : obj[1].toString();
        readGroup = (obj[2] == null) ? "" : obj[2].toString();
        if ("".equals(reader) && "".equals(readOrg) && "".equals(readGroup)) {
          reader = "-1";
        } else {
          StringBuffer readerBuffer = new StringBuffer();
          if (!"".equals(reader)) {
            reader = reader.substring(1, reader.length() - 1);
            reader = reader.replaceAll("\\$\\$", ",");
            String[] readerArr = reader.split(",");
            for (i = 0; i < readerArr.length; i++) {
              if (!userId.equals(readerArr[i]))
                readerBuffer.append(reader).append(","); 
            } 
          } 
          if (!"".equals(readOrg)) {
            String[] orgIdArray = readOrg.substring(1, readOrg.length() - 1).split("\\*\\*");
            StringBuffer where = new StringBuffer();
            for (i = 0; i < orgIdArray.length; i++) {
              if (where.length() > 0) {
                where.append(" or orgIdString like '%$").append(orgIdArray[i]).append("$%'");
              } else {
                where.append(" where orgIdString like '%$").append(orgIdArray[i]).append("$%'");
              } 
            } 
            list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (select po.orgId from com.js.system.vo.organizationmanager.OrganizationVO po " + where.toString() + ") and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null").list();
            if (list != null && list.size() > 0)
              for (i = 0; i < list.size(); i++) {
                String empId = list.get(i).toString();
                if (!userId.equals(empId))
                  readerBuffer.append(empId).append(","); 
              }  
          } 
          if (!"".equals(readGroup)) {
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + readGroup + "' like concat('%@',gr.groupId,'@%')  and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null").list();
            } else {
              list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + readGroup + "' like '%@'||gr.groupId||'@%'  and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null").list();
            } 
            if (list != null && list.size() > 0)
              for (i = 0; i < list.size(); i++) {
                String empId = list.get(i).toString();
                if (!userId.equals(empId))
                  readerBuffer.append(empId).append(","); 
              }  
          } 
          reader = readerBuffer.toString();
          if (reader.length() > 0)
            reader = reader.substring(0, reader.length() - 1); 
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return reader;
  }
  
  private String getChannelCanReadUserIds(String userId, String reader, String readOrg, String readGroup) {
    try {
      int i = 0;
      if (("".equals(reader) || reader == null) && ("".equals(readOrg) || readOrg == null) && ("".equals(readGroup) || readGroup == null)) {
        reader = "-1";
      } else {
        StringBuffer readerBuffer = new StringBuffer();
        if (!"".equals(reader) && reader != null) {
          reader = reader.substring(1, reader.length() - 1);
          reader = reader.replaceAll("\\$\\$", ",");
          String[] readerArr = reader.split(",");
          for (i = 0; i < readerArr.length; i++) {
            if (!userId.equals(readerArr[i]))
              readerBuffer.append(reader).append(","); 
          } 
        } 
        if (!"".equals(readOrg) && readOrg != null) {
          String[] orgIdArray = readOrg.substring(1, readOrg.length() - 1).split("\\*\\*");
          StringBuffer where = new StringBuffer();
          for (i = 0; i < orgIdArray.length; i++) {
            if (where.length() > 0) {
              where.append(" or orgIdString like '%$").append(orgIdArray[i]).append("$%'");
            } else {
              where.append(" where orgIdString like '%$").append(orgIdArray[i]).append("$%'");
            } 
          } 
          List<E> list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (select po.orgId from com.js.system.vo.organizationmanager.OrganizationVO po " + where.toString() + ")").list();
          if (list != null && list.size() > 0)
            for (i = 0; i < list.size(); i++) {
              String empId = list.get(i).toString();
              if (!userId.equals(empId))
                readerBuffer.append(empId).append(","); 
            }  
        } 
        if (!"".equals(readGroup) && readGroup != null) {
          List<E> list;
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + readGroup + "' like concat('%@',gr.groupId,'@%')").list();
          } else {
            list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + readGroup + "' like '%@'||gr.groupId||'@%'").list();
          } 
          if (list != null && list.size() > 0)
            for (i = 0; i < list.size(); i++) {
              String empId = list.get(i).toString();
              if (!userId.equals(empId))
                readerBuffer.append(empId).append(","); 
            }  
        } 
        reader = readerBuffer.toString();
        if (reader.length() > 0)
          reader = reader.substring(0, reader.length() - 1); 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return reader;
  }
  
  public String userCanRead(String informationId, String userId, String orgId, String orgIdString) {
    return null;
  }
  
  public List getTopTime(String informationId, String typeState) {
    List list = null;
    String sql = "";
    try {
      begin();
      if (typeState != null && !typeState.equals("") && !typeState.equals("null")) {
        if (typeState.equals("1"))
          sql = "select p.topTimeFrom,p.topTimeTo from InformationPO p where p.informationId=" + informationId; 
        if (typeState.equals("0"))
          sql = "select p.topTimeStart, p.topTimeEnd from InformationPO p where p.informationId=" + informationId; 
      } 
      if (!sql.equals("")) {
        Query que = this.session.createQuery(sql);
        list = que.list();
      } 
      this.session.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
    } 
    return list;
  }
  
  public void resetOrderCode() {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer idBuffer = new StringBuffer("");
    StringBuffer idBufferTemp = new StringBuffer("");
    try {
      String sql1 = "";
      String sql2 = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql1 = "select INFORMATION_ID from OA_INFORMATION  where ((toptimefrom is not null and toptimeto is not null) and (toptimefrom<>'' and toptimeto<>'')) and (utc_date() not between toptimefrom and toptimeto)";
        sql2 = "select INFORMATION_ID from OA_INFORMATION  where ((toptimestart is not null and toptimeend is not null) and (toptimestart<>'' and toptimeend<>'')) and (utc_date() not between toptimestart and toptimeend) ";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql1 = "select INFORMATION_ID from OA_INFORMATION where (toptimefrom is not null and toptimeto is not null) and (sysdate not between to_date(toptimefrom,'YYYY-MM-DD') and to_date(toptimeto,'YYYY-MM-DD'))";
        sql2 = "select INFORMATION_ID from OA_INFORMATION where (toptimestart is not null and toptimeend is not null) and (sysdate not between to_date(toptimestart,'YYYY-MM-DD') and to_date(toptimeend,'YYYY-MM-DD'))";
      } else {
        sql1 = "select INFORMATION_ID from OA_INFORMATION where (toptimefrom is not null and toptimeto is not null) and (getdate() not between toptimefrom and toptimeto)";
        sql2 = "select INFORMATION_ID from OA_INFORMATION where (toptimestart is not null and toptimeend is not null) and (getdate() not between toptimestart and toptimeend)";
      } 
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql1);
      while (rs.next())
        idBuffer.append(rs.getInt(1)).append(","); 
      if (idBuffer != null && !idBuffer.toString().equals(""))
        stmt.executeUpdate("update oa_information set ORDERCODE=1000,toptimefrom='',toptimeto='' where INFORMATION_ID in (" + idBuffer.substring(0, idBuffer.toString().length() - 1).toString() + ")"); 
      rs = stmt.executeQuery(sql2);
      while (rs.next())
        idBufferTemp.append(rs.getInt(1)).append(","); 
      if (idBufferTemp != null && !idBufferTemp.toString().equals(""))
        stmt.executeUpdate("update oa_information set ORDERCODETEMP=0,toptimestart='',toptimeend='' where INFORMATION_ID in (" + idBufferTemp.substring(0, idBufferTemp.toString().length() - 1).toString() + ")"); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public int getMaxOrderCodeByTypeState(String typeState) {
    int code = 0;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      if (typeState != null && !typeState.equals("")) {
        if (typeState.equals("1"))
          rs = stmt.executeQuery("select max(ORDERCODE)+1 from oa_information"); 
        if (typeState.equals("0"))
          rs = stmt.executeQuery("select max(ORDERCODETEMP)+1 from oa_information"); 
      } 
      if (rs != null && rs.next())
        code = rs.getInt(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } 
    return code;
  }
  
  public String[] getMessageTitleAndUsers(String informationId, String reprocess) {
    String[] result = { "", "" };
    String issuerId = "-1";
    try {
      begin();
      List<Object[]> list = this.session.createQuery("select po.informationIssuerId,po.informationIssuer,po.informationTitle,po.modifyEmp from com.js.oa.info.infomanager.po.InformationPO po where po.informationId=" + informationId).list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        issuerId = obj[0].toString();
        if ("rws".equals(SystemCommon.getCustomerName())) {
          result[1] = obj[2].toString();
        } else if (reprocess == null) {
          result[1] = String.valueOf(obj[1].toString()) + "发布了:" + obj[2].toString();
        } else {
          result[1] = String.valueOf(obj[3].toString().substring(obj[3].toString().lastIndexOf(".") + 1)) + "修改了:" + obj[2].toString();
        } 
      } 
      result[0] = getCanReadUserIdsImpl(issuerId, informationId);
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean judgeUserIsProLeader(String userId, String channelId) throws Exception {
    boolean returnVal = Boolean.FALSE.booleanValue();
    StringBuffer sBuffer = new StringBuffer();
    sBuffer.append("select po.relProjectId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=");
    sBuffer.append(channelId);
    try {
      begin();
      Query query = this.session.createQuery(sBuffer.toString());
      List<E> list = query.list();
      if (list != null && list.size() > 0) {
        String relProId = list.get(0).toString();
        if (relProId != null && !relProId.equals("")) {
          StringBuffer sb = new StringBuffer();
          sb.append("select po.actorId from com.js.oa.relproject.po.RelProActorPO po where po.project.id=");
          sb.append(relProId);
          sb.append(" and (po.actorRole='10' or po.actorRole='20') ");
          query = this.session.createQuery(sb.toString());
          List<E> idList = query.list();
          StringBuffer proLeaderIds = new StringBuffer();
          String proLeaderId = "";
          for (int i = 0; i < idList.size(); i++) {
            proLeaderId = idList.get(i).toString();
            proLeaderIds.append("*" + proLeaderId + "*");
          } 
          if (proLeaderIds.toString().indexOf("*" + userId + "*") != -1)
            returnVal = Boolean.TRUE.booleanValue(); 
        } 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return returnVal;
  }
  
  public String judgeUserIsProLeader_1(String userId, String channelId) throws Exception {
    String returnVal = "0";
    StringBuffer sBuffer = new StringBuffer();
    sBuffer.append("select po.relProjectId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=");
    sBuffer.append(channelId);
    try {
      begin();
      Query query = this.session.createQuery(sBuffer.toString());
      List list = query.list();
      if (list != null && list.size() > 0) {
        String relProId = String.valueOf(list.get(0));
        if (relProId != null && !relProId.equals("") && !relProId.equals("0") && !relProId.equals("null")) {
          StringBuffer sb = new StringBuffer();
          sb.append("select po.actorId from com.js.oa.relproject.po.RelProActorPO po where po.project.id=");
          sb.append(relProId);
          sb.append(" and (po.actorRole='10' or po.actorRole='20' or po.actorRole='30') ");
          query = this.session.createQuery(sb.toString());
          List<E> idList = query.list();
          StringBuffer proLeaderIds = new StringBuffer();
          String proLeaderId = "";
          for (int i = 0; i < idList.size(); i++) {
            proLeaderId = idList.get(i).toString();
            proLeaderIds.append("*" + proLeaderId + "*");
          } 
          if (proLeaderIds.toString().indexOf("*" + userId + "*") != -1) {
            returnVal = "1";
          } else {
            returnVal = "2";
          } 
        } 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return returnVal;
  }
  
  public boolean judgeUserIsProLeader_2(String userId, String channelId) throws Exception {
    boolean returnVal = Boolean.FALSE.booleanValue();
    StringBuffer sBuffer = new StringBuffer();
    sBuffer.append("select po.relProjectId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=");
    sBuffer.append(channelId);
    try {
      begin();
      Query query = this.session.createQuery(sBuffer.toString());
      List<E> list = query.list();
      if (list != null && list.size() > 0) {
        String relProId = list.get(0).toString();
        if (relProId != null && !relProId.equals("")) {
          StringBuffer sb = new StringBuffer();
          sb.append("select po.actorId from com.js.oa.relproject.po.RelProActorPO po where po.project.id=");
          sb.append(relProId);
          sb.append(" and (po.actorRole='10' or po.actorRole='20' or po.actorRole='30' or po.actorRole='40') ");
          query = this.session.createQuery(sb.toString());
          List<E> idList = query.list();
          StringBuffer proLeaderIds = new StringBuffer();
          String proLeaderId = "";
          for (int i = 0; i < idList.size(); i++) {
            proLeaderId = idList.get(i).toString();
            proLeaderIds.append("*" + proLeaderId + "*");
          } 
          if (proLeaderIds.toString().indexOf("*" + userId + "*") != -1)
            returnVal = Boolean.TRUE.booleanValue(); 
        } 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return returnVal;
  }
  
  public String getjudgeUserName(String channelId) throws Exception {
    boolean returnVal = Boolean.FALSE.booleanValue();
    StringBuffer sBuffer = new StringBuffer();
    sBuffer.append("select po.relProjectId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=");
    sBuffer.append(channelId);
    StringBuffer proReaderNames = new StringBuffer();
    String proReaderName = "";
    try {
      begin();
      Query query = this.session.createQuery(sBuffer.toString());
      List<E> list = query.list();
      if (list != null && list.size() > 0) {
        String relProId = list.get(0).toString();
        if (relProId != null && !relProId.equals("")) {
          StringBuffer sb = new StringBuffer();
          sb.append("select distinct po.actorId, po.actorName from com.js.oa.relproject.po.RelProActorPO po where po.project.id=");
          sb.append(relProId);
          sb.append(" and (po.actorRole='10' or po.actorRole='20' or po.actorRole='30' or po.actorRole='40') ");
          query = this.session.createQuery(sb.toString());
          List<Object[]> idList = query.list();
          for (int i = 0; i < idList.size(); i++) {
            Object[] po = idList.get(i);
            proReaderName = po[1].toString();
            proReaderNames.append(String.valueOf(proReaderName) + ",");
          } 
        } 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return proReaderNames.toString();
  }
  
  public boolean ifOtherChannelIssue(String informationId, String channelId) throws Exception {
    boolean returnVal = Boolean.FALSE.booleanValue();
    StringBuffer sBuffer = new StringBuffer();
    sBuffer.append("select po.informationId from com.js.oa.info.infomanager.po.InformationPO po where po.informationChannel=" + channelId + " and po.informationId=" + informationId);
    try {
      begin();
      Query query = this.session.createQuery(sBuffer.toString());
      List list = query.list();
      this.session.close();
      if (list != null && list.size() > 0)
        return Boolean.FALSE.booleanValue(); 
      return Boolean.TRUE.booleanValue();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      return returnVal;
    } 
  }
  
  public InformationPO getInformationPoById(Long id) {
    InformationPO po = null;
    try {
      begin();
      po = (InformationPO)this.session.load(InformationPO.class, id);
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return po;
  }
  
  public String getProChannelId(String proId) {
    String channelIdString = "";
    try {
      begin();
      Query query = this.session.createQuery("select po.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.relProjectId= " + proId);
      List<Long> list = query.list();
      if (list.size() > 0)
        channelIdString = ((Long)list.get(0)).toString(); 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return channelIdString;
  }
  
  public String getAllChildChannelIds(String channelId) {
    StringBuffer channelIdString = new StringBuffer("0,");
    StringBuffer channelIdSubString = new StringBuffer();
    try {
      begin();
      channelIdString.append(channelId).append(",");
      Query query = this.session.createQuery("select ch.channelId,ch.includeChild,ch.channelLevel,ch.channelParentId from com.js.oa.info.channelmanager.po.InformationChannelPO ch where ch.channelIdString like '%$" + channelId + "$%' order by ch.channelLevel,ch.channelIdString");
      List<Object[]> list = query.list();
      for (int i = 1; i < list.size(); i++) {
        Object[] obj = list.get(i);
        String curChannelId = obj[0].toString();
        String parentId = obj[3].toString();
        if ("1".equals(obj[1].toString())) {
          if (channelIdString.indexOf("," + parentId + ",") >= 0)
            channelIdString.append(curChannelId).append(","); 
        } else if (channelIdString.indexOf("," + parentId + ",") >= 0) {
          channelIdSubString.append(curChannelId).append(",");
        } 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return channelIdString.append(channelIdSubString.toString()).append("0").toString();
  }
  
  public String getAllChildCanChannelIds(String channelId) {
    StringBuffer channelIdString = new StringBuffer("0,");
    StringBuffer channelIdSubString = new StringBuffer();
    try {
      begin();
      channelIdString.append(channelId).append(",");
      Query query = this.session.createQuery("select ch.channelId,ch.includeChild,ch.channelLevel,ch.channelParentId from com.js.oa.info.channelmanager.po.InformationChannelPO ch where ch.channelIdString like '%$" + channelId + "$%' order by ch.channelLevel,ch.channelIdString");
      List<Object[]> list = query.list();
      for (int i = 1; i < list.size(); i++) {
        Object[] obj = list.get(i);
        String curChannelId = obj[0].toString();
        String parentId = obj[3].toString();
        if ("1".equals(obj[1].toString())) {
          if (channelIdString.indexOf("," + parentId + ",") >= 0)
            channelIdString.append(curChannelId).append(","); 
        } else if (channelIdString.indexOf("," + parentId + ",") >= 0) {
          channelIdSubString.append(curChannelId).append(",");
        } 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return channelIdString.append(channelIdSubString.toString()).append("0").toString();
  }
  
  public List getUserGroupList(String userId) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getCoopContext(String id) {
    String[] coId = id.split("_");
    String sql = "";
    if ("COOP".equals(coId[1]))
      sql = "SELECT content FROM co_body WHERE id=" + coId[0]; 
    String re = "";
    if (!sql.equals("")) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        ResultSet rs = base.executeQuery(sql);
        if (rs.next())
          re = rs.getString(1); 
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    return re;
  }
  
  public String getInfoReaderNum(String informationId) {
    String num = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select count(browser_id) from oa_informationbrowser where information_id=?");
      pstmt.setString(1, informationId);
      ResultSet rs = pstmt.executeQuery();
      if (rs != null && rs.next())
        num = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } 
    return num;
  }
}
