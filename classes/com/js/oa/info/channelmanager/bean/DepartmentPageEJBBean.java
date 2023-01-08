package com.js.oa.info.channelmanager.bean;

import com.js.oa.info.channelmanager.vo.ChannelVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class DepartmentPageEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getTopChannel(String orgId, String userId, String channelType) throws Exception {
    ArrayList<ChannelVO> topChannelList = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode='01*01*02'  and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + ", '$%') ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] arrayOfObject = tmpList.get(0);
        String scopeType = arrayOfObject[0].toString();
        String scopeScope = "";
        if (arrayOfObject[1] != null && !arrayOfObject[1].toString().equals(""))
          scopeScope = arrayOfObject[1].toString().substring(1, arrayOfObject[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgChildList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + orgChildList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(" select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      String sql = "";
      if (allScope) {
        sql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelLevel = 1 and aaa.channelType = " + 
          
          channelType + " order by aaa.channelSort";
      } else {
        String hSql = "select aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + " aaa.channelReaderGroup like '%@" + tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + groupString + " aaa.channelReader like '%$" + userId + "$%' or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='')) ) and aaa.channelType = " + 
          channelType + " order by aaa.channelIdString ";
        query = this.session.createQuery(hSql);
        StringBuffer sb = new StringBuffer();
        tmpList = query.list();
        for (int k = 0; k < tmpList.size(); k++)
          sb.append((String)tmpList.get(k)); 
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelLevel = 1 and aaa.channelType = " + 
            
            channelType + " and '" + 
            sb.toString() + "' like concat('%$', aaa.channelId, '$%') order by aaa.channelSort";
        } else {
          sql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelLevel = 1 and aaa.channelType = " + 
            
            channelType + " and '" + 
            sb.toString() + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelSort";
        } 
      } 
      query = this.session.createQuery(sql);
      Iterator<Object[]> iterator = query.iterate();
      Object[] obj = (Object[])null;
      while (iterator.hasNext()) {
        obj = iterator.next();
        ChannelVO channelVO = new ChannelVO();
        channelVO.setId(new Long(obj[0].toString()));
        channelVO.setName(obj[1].toString());
        channelVO.setShowType(obj[2].toString());
        topChannelList.add(channelVO);
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return topChannelList;
  }
  
  public List getLeftChTree(String topChId, String orgId, String userId) throws Exception {
    begin();
    ArrayList alist = new ArrayList();
    try {
      Query query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '部门主页维护' and  bbb.rightName = '维护'  and ccc.empId = " + 

          
          userId);
      List<Object[]> tmpList = query.list();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().toString().length() - 1); 
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
      String sql = "";
      if (allScope) {
        sql = "select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelParentId,aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where channelLevel > 1 and aaa.channelIdString like '%$" + 
          
          topChId + "$%' order by channelIdString";
      } else {
        String hSql = "select aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + " aaa.channelReaderGroup like '%@" + tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + groupString + " aaa.channelReader like '%$" + userId + "$%' or (aaa.channelReader is null and aaa.channelReaderOrg is null and aaa.channelReaderGroup is null) ) and aaa.channelType = " + 
          orgId + " order by aaa.channelIdString ";
        query = this.session.createQuery(hSql);
        StringBuffer sb = new StringBuffer();
        tmpList = query.list();
        for (int k = 0; k < tmpList.size(); k++)
          sb.append((String)tmpList.get(k)); 
        sql = "select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelParentId,aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where channelLevel > 1 and aaa.channelIdString like '%$" + 
          
          topChId + "$%'  order by channelIdString";
      } 
      query = this.session.createQuery(sql);
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public void ModiDepaStyle(String styleId, String orgId) throws Exception {
    begin();
    try {
      OrganizationVO orgVO = (OrganizationVO)this.session.load(OrganizationVO.class, new Long(orgId));
      orgVO.setOrgHasChannel(Integer.parseInt(styleId));
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List departmentDeskop(String viewChStr, String readerWhere) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
          viewChStr + "' like concat('%$', aaa.channelId, '$%') and aaa.onDepaDesk = 1 order by aaa.channelIdString";
      } else {
        tmpSql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
          viewChStr + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') and aaa.onDepaDesk = 1 order by aaa.channelIdString";
      } 
      Query query = this.session.createQuery(tmpSql);
      Iterator<Object[]> iterator = query.iterate();
      Object[] obj = (Object[])null;
      while (iterator.hasNext()) {
        obj = iterator.next();
        String[] channel = { "", "", "", "" };
        channel[0] = "1";
        channel[1] = obj[0].toString();
        channel[2] = obj[1].toString();
        channel[3] = obj[2].toString();
        alist.add(channel);
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.informationId, aaa.informationTitle, aaa.informationKits, aaa.informationIssueTime, aaa.informationHead, aaa.informationType,aaa.titleColor from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where (bbb.channelId = " + 
            
            obj[0] + " or aaa.otherChannel like '%," + 
            obj[0] + ",%') and aaa.informationStatus=0 and (" + readerWhere + ") and (aaa.informationValidType=0 or ('" + (new Date()).toLocaleString() + "' between aaa.validBeginTime and aaa.validEndTime)) order by aaa.orderCode desc, aaa.informationIssueTime desc, aaa.informationId desc";
        } else {
          tmpSql = "select aaa.informationId, aaa.informationTitle, aaa.informationKits, aaa.informationIssueTime, aaa.informationHead, aaa.informationType,aaa.titleColor from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where (bbb.channelId = " + 
            
            obj[0] + " or aaa.otherChannel like '%," + 
            obj[0] + ",%') and aaa.informationStatus=0 and (" + readerWhere + ") and (aaa.informationValidType=0 or (JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "', 'L') between aaa.validBeginTime and aaa.validEndTime)) order by aaa.orderCode desc, aaa.informationIssueTime desc, aaa.informationId desc";
        } 
        query = this.session.createQuery(tmpSql);
        query.setFirstResult(0);
        query.setMaxResults(6);
        Iterator<Object[]> iterator2 = query.iterate();
        while (iterator2.hasNext()) {
          obj = iterator2.next();
          String[] info = { "", "", "", "", "", "", "", "", "" };
          info[0] = "0";
          info[1] = obj[0].toString();
          info[2] = obj[1].toString();
          info[3] = obj[2].toString();
          if (obj[3].toString().indexOf(" ") > 0) {
            info[4] = obj[3].toString().substring(0, obj[3].toString().indexOf(" "));
          } else {
            info[4] = obj[3].toString();
          } 
          info[5] = obj[4].toString();
          info[6] = obj[5].toString();
          Date issueTime = (Date)obj[3];
          if ((new Date()).getTime() - issueTime.getTime() < 259200000L)
            info[7] = "1"; 
          if (obj[6] != null) {
            info[8] = obj[6].toString();
          } else {
            info[8] = "0";
          } 
          alist.add(info);
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public void setDepartFlag(String informationId, String gg, String flagValue) throws Exception {
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      int flag = Integer.parseInt(gg);
      int flagValue2 = 0;
      if (Integer.parseInt(flagValue) == 1) {
        flagValue2 = 0;
      } else {
        flagValue2 = 1;
      } 
      if (flag == 1) {
        stmt.execute("UPDATE JSDB.OA_INFORMATION SET INFODEPAFLAG=" + flagValue2 + " WHERE INFORMATION_ID=" + informationId);
        if (Integer.parseInt(flagValue) == 0)
          stmt.execute("UPDATE JSDB.OA_INFORMATION SET INFODEPAFLAG=" + flagValue + " WHERE INFORMATION_ID<>" + informationId); 
      } else {
        stmt.execute("UPDATE JSDB.OA_INFORMATION SET INFODEPAFLAG2=" + flagValue2 + " WHERE INFORMATION_ID=" + informationId);
        if (Integer.parseInt(flagValue) == 0)
          stmt.execute("UPDATE JSDB.OA_INFORMATION SET INFODEPAFLAG2=" + flagValue + " WHERE INFORMATION_ID<>" + informationId); 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getNewAnno(String channelType, String infoWhere) throws Exception {
    begin();
    List anno = null;
    try {
      Query query = this.session.createQuery("select aaa.informationId, aaa.informationSummary, aaa.informationHead, aaa.informationType, bbb.channelId, aaa.informationTitle from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where bbb.channelType = " + 
          
          channelType + " and (" + infoWhere + ") and aaa.infoDepaFlag = 1");
      query.setFirstResult(0);
      query.setMaxResults(1);
      anno = query.list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return anno;
  }
  
  public List getPhotoInfo(String channelType, String infoWhere) throws Exception {
    begin();
    List<Object[]> photoInfo = null;
    try {
      Query query = this.session.createQuery("select aaa.informationId, aaa.informationTitle, aaa.informationHead, aaa.informationType, bbb.channelId from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where bbb.channelType = " + 
          
          channelType + " and (" + infoWhere + ") and aaa.infoDepaFlag2 = 1");
      query.setFirstResult(0);
      query.setMaxResults(1);
      photoInfo = query.list();
      if (photoInfo != null && photoInfo.size() > 0) {
        Object[] obj = photoInfo.get(0);
        Object[] obj2 = new Object[obj.length + 1];
        obj2[0] = obj[0];
        obj2[1] = obj[1];
        obj2[2] = obj[2];
        obj2[3] = obj[3];
        obj2[4] = obj[4];
        query = this.session.createQuery("select aaa.accessorySaveName from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa join aaa.information bbb where bbb.informationId = " + 
            obj[0] + " and aaa.accessoryIsImage = 1");
        Iterator iter = query.iterate();
        if (iter.hasNext()) {
          obj2[5] = iter.next();
        } else {
          obj2[5] = "";
        } 
        photoInfo.set(0, obj2);
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return photoInfo;
  }
  
  public void updateBanner(String styleId, String saveName, String orgId) throws Exception {
    begin();
    try {
      OrganizationVO orgVO = (OrganizationVO)this.session.load(OrganizationVO.class, new Long(orgId));
      String orgBanner = orgVO.getOrgBanner();
      if (orgBanner == null || orgBanner.equals("")) {
        orgBanner = String.valueOf(styleId) + ":" + saveName;
      } else if (orgBanner.indexOf("::") > 0) {
        String[] tmp = orgBanner.split("::");
        boolean noStyle = true;
        orgBanner = "";
        for (int i = 0; i < tmp.length; i++) {
          if (tmp[i].startsWith(styleId)) {
            orgBanner = String.valueOf(orgBanner) + "::" + styleId + ":" + saveName;
            noStyle = false;
          } else {
            orgBanner = String.valueOf(orgBanner) + "::" + tmp[i];
          } 
        } 
        if (noStyle)
          orgBanner = String.valueOf(orgBanner) + "::" + styleId + ":" + saveName; 
      } else if (orgBanner.startsWith(styleId)) {
        orgBanner = String.valueOf(styleId) + ":" + saveName;
      } else {
        orgBanner = String.valueOf(orgBanner) + "::" + styleId + ":" + saveName;
      } 
      if (orgBanner.startsWith("::"))
        orgBanner = orgBanner.substring(2, orgBanner.length()); 
      if (orgBanner.endsWith("::"))
        orgBanner = orgBanner.substring(0, orgBanner.length() - 2); 
      orgVO.setOrgBanner(orgBanner);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getMostNewInfo(String viewChStr, String orgIdString, String userId) throws Exception {
    ArrayList alist = new ArrayList();
    begin();
    try {
      StringBuffer readOrg = new StringBuffer();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where '" + 
          orgIdString + "' like concat('%$', aaa.orgId, '$%') ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where '" + 
          orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      Iterator<E> iterator = query.iterate();
      while (iterator.hasNext()) {
        readOrg.append(" aaa.informationReaderOrg like '%*" + iterator.next().toString() + "*%' ");
        if (iterator.hasNext())
          readOrg.append(" or "); 
      } 
      StringBuffer readGroup = new StringBuffer();
      query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
          userId);
      iterator = query.iterate();
      while (iterator.hasNext()) {
        readGroup.append(" aaa.informationReaderGroup like '%@" + iterator.next().toString() + "@%' ");
        if (iterator.hasNext())
          readGroup.append(" or "); 
      } 
      String getNewInfoSql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        getNewInfoSql = "select bbb.channelName, aaa.informationId, aaa.informationTitle, aaa.informationKits, aaa.informationIssueTime,aaa.informationHead, aaa.informationType,bbb.channelId,aaa.titleColor,aaa.isConf from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where (aaa.informationStatus=0 or aaa.informationStatus=1) and '" + 

          
          viewChStr + "' like concat('%$', bbb.channelId, '$%')  and (((aaa.informationReader is null or aaa.informationReader='') and " + 
          "(aaa.informationReaderOrg is null or aaa.informationReaderOrg='') and (aaa.informationReaderGroup is null or aaa.informationReaderGroup='')) or aaa.informationReader like '%$" + userId + "$%' ";
      } else {
        getNewInfoSql = "select bbb.channelName, aaa.informationId, aaa.informationTitle, aaa.informationKits, aaa.informationIssueTime,aaa.informationHead, aaa.informationType,bbb.channelId,aaa.titleColor,aaa.isConf from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where (aaa.informationStatus=0 or aaa.informationStatus=1) and '" + 

          
          viewChStr + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(bbb.channelId)), '$%') and (((aaa.informationReader is null or aaa.informationReader='') and " + 
          "(aaa.informationReaderOrg is null or aaa.informationReaderOrg='') and (aaa.informationReaderGroup is null or aaa.informationReaderGroup='')) or aaa.informationReader like '%$" + userId + "$%' ";
      } 
      if (!readGroup.toString().equals(""))
        getNewInfoSql = String.valueOf(getNewInfoSql) + " or " + readGroup.toString(); 
      if (!readOrg.toString().equals(""))
        getNewInfoSql = String.valueOf(getNewInfoSql) + " or " + readOrg.toString(); 
      if (databaseType.indexOf("mysql") >= 0) {
        getNewInfoSql = String.valueOf(getNewInfoSql) + ") and (aaa.informationValidType=0 or ('" + (new Date()).toLocaleString() + "' between aaa.validBeginTime and aaa.validEndTime)) order by aaa.orderCode desc, aaa.informationIssueTime desc, aaa.informationId desc";
      } else {
        getNewInfoSql = String.valueOf(getNewInfoSql) + ") and (aaa.informationValidType=0 or (JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "', 'L') between aaa.validBeginTime and aaa.validEndTime)) order by aaa.orderCode desc, aaa.informationIssueTime desc, aaa.informationId desc";
      } 
      query = this.session.createQuery(getNewInfoSql);
      query.setFirstResult(0);
      query.setMaxResults(10);
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public String getOrgBanner(String orgId) throws Exception {
    begin();
    String orgBanner = "";
    try {
      OrganizationVO orgVO = (OrganizationVO)this.session.load(OrganizationVO.class, new Long(orgId));
      if (orgVO.getOrgBanner() != null && !orgVO.getOrgBanner().equals(""))
        orgBanner = orgVO.getOrgBanner(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgBanner;
  }
  
  public Map departHomepage(String viewChStr, String readerWhere) throws Exception {
    begin();
    HashMap<Object, Object> hp = new HashMap<Object, Object>();
    ArrayList<String[]> channelList = new ArrayList();
    HashMap<Object, Object> infoMap = new HashMap<Object, Object>();
    try {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
          viewChStr + "' like concat('%$', aaa.channelId, '$%') and aaa.onDepaDesk = 1 order by aaa.channelIdString";
      } else {
        tmpSql = "select aaa.channelId, aaa.channelName, aaa.channelShowType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
          viewChStr + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') and aaa.onDepaDesk = 1 order by aaa.channelIdString";
      } 
      Query query = this.session.createQuery(tmpSql);
      Iterator<Object[]> iterator = query.iterate();
      Object[] obj = (Object[])null;
      String channelId = "";
      boolean hasChild = false;
      while (iterator.hasNext()) {
        obj = iterator.next();
        channelId = obj[0].toString();
        String[] channel = { "", "", "" };
        channel[0] = obj[0].toString();
        channel[1] = obj[1].toString();
        channel[2] = obj[2].toString();
        query = this.session.createQuery("select aaa.informationId, aaa.informationTitle, aaa.informationKits, aaa.informationIssueTime, aaa.informationHead, aaa.informationType from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where (bbb.channelId = " + 
            
            obj[0] + " or aaa.otherChannel like '%," + 
            obj[0] + ",%') and aaa.informationStatus=0 and (" + readerWhere + ") order by aaa.orderCode desc, aaa.informationIssueTime desc");
        query.setFirstResult(0);
        query.setMaxResults(8);
        Iterator<Object[]> iterator2 = query.iterate();
        ArrayList<String[]> infoList = new ArrayList();
        while (iterator2.hasNext()) {
          hasChild = true;
          obj = iterator2.next();
          String[] info = { "", "", "", "", "", "", "" };
          info[0] = obj[0].toString();
          info[1] = obj[1].toString();
          info[2] = obj[2].toString();
          if (obj[3].toString().indexOf(" ") > 0) {
            info[3] = obj[3].toString().substring(0, obj[3].toString().indexOf(" "));
          } else {
            info[3] = obj[3].toString();
          } 
          info[4] = obj[4].toString();
          info[5] = obj[5].toString();
          Date issueTime = (Date)obj[3];
          if ((new Date()).getTime() - issueTime.getTime() < 259200000L)
            info[6] = "1"; 
          infoList.add(info);
        } 
        if (hasChild) {
          channelList.add(channel);
          infoMap.put(channelId, infoList);
        } 
      } 
      hp.put("channel", channelList);
      hp.put("info", infoMap);
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hp;
  }
  
  public List getCanVindicate(String where, String userId, String orgId, String domainId) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    try {
      String mySql = "";
      String corpId = "";
      if (SystemCommon.getMultiDepart() == 1) {
        String orgIdString = "";
        mySql = "select aaa.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=:orgId";
        Iterator<E> it = this.session.createQuery(mySql).setString("orgId", orgId).iterate();
        if (it.hasNext()) {
          orgIdString = it.next().toString();
          if (!"0".equals(orgIdString) && (
            orgIdString.split("_")).length > 2) {
            orgIdString = orgIdString.split("_")[2];
            corpId = orgIdString.substring(orgIdString.indexOf("$") + 1, orgIdString.lastIndexOf("$"));
          } 
        } 
      } 
      mySql = "select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        where + ") and (aaa.channelType=0 or aaa.userDefine=1) and (aaa.afficheChannelStatus is null or  aaa.afficheChannelStatus='0') and aaa.domainId=" + domainId;
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        mySql = String.valueOf(mySql) + " and aaa.corpId=" + corpId; 
      mySql = String.valueOf(mySql) + " order by aaa.channelIdString, aaa.channelType";
      Query query = this.session.createQuery(mySql);
      Iterator<Object[]> iter = query.iterate();
      Iterator<E> iter2 = null;
      StringBuffer channelNameBuf = new StringBuffer();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[4].toString().equals("1"))
          if (obj[2].toString().equals("0")) {
            obj[1] = "知识管理·" + obj[1];
          } else {
            query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
                obj[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              obj[1] = (new StringBuilder()).append(iter2.next()).append("·").append(obj[1]).toString(); 
          }  
        alist.add(obj);
      } 
      query = this.session.createQuery("select aaa.rightScopeType, aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.right bbb join aaa.employee ccc where ccc.empId = " + 

          
          userId + " and bbb.rightCode ='01*01*02'");
      iter = query.iterate();
      if (iter.hasNext()) {
        StringBuffer sql = new StringBuffer();
        sql.append("select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa,com.js.system.vo.organizationmanager.OrganizationVO bbb where aaa.channelType=bbb.orgId and aaa.domainId=" + 
            domainId + " ");
        Object[] obj = iter.next();
        int rightScopeType = Integer.parseInt(obj[0].toString());
        switch (rightScopeType) {
          case 0:
            sql.append(" and (aaa.channelType>0 and aaa.userDefine=0) ");
            break;
          case 2:
            sql.append(" and (aaa.channelType=" + orgId + ") ");
            break;
          case 3:
            query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organization.OrganizationVO aaa where aaa.orgIdString like '%$" + 
                orgId + "$%'");
            iter = query.iterate();
            while (iter.hasNext())
              sql.append(" and (aaa.channelType=" + iter.next() + ") "); 
            break;
          case 4:
            if (obj[1] != null) {
              String scope = obj[1].toString();
              scope = scope.substring(1, scope.length() - 1);
              String[] tmp = { "" };
              if (scope.indexOf("*") > 0) {
                tmp = scope.split("**");
              } else {
                tmp[0] = scope;
              } 
              for (int i = 0; i < tmp.length; i++) {
                query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
                    tmp[i] + "$%' order by aaa.orgIdString");
                iter = query.iterate();
                while (iter.hasNext())
                  sql.append(" and (aaa.channelType=" + iter.next() + ") "); 
              } 
            } 
            break;
        } 
        sql.append(" order by bbb.orgIdString");
        query = this.session.createQuery(sql.toString());
        iter = query.iterate();
        channelNameBuf = new StringBuffer();
        String orgNameString = "";
        while (iter.hasNext()) {
          Object[] obj2 = iter.next();
          if (obj2[4].toString().equals("1")) {
            query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + obj2[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              orgNameString = iter2.next().toString(); 
            orgNameString = orgNameString.replace('.', '·');
            obj2[1] = String.valueOf(orgNameString) + "·" + obj2[1];
          } 
          alist.add(obj2);
        } 
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
  
  public Boolean deptVindicateInfo(String userId, String userOrg, String curOrgId) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      String rightCode = "01*01*02";
      Query query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa join  aaa.right bbb join aaa.employee ccc where bbb.rightCode='" + 
          
          rightCode + "' and ccc.empId = " + userId);
      List<Object[]> tmpList = query.list();
      if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString(); 
        if (scopeType.equals("0")) {
          result = Boolean.TRUE;
        } else if (scopeType.equals("1")) {
          result = Boolean.FALSE;
        } else if (scopeType.equals("2")) {
          Iterator<E> iter = this.session.iterate("select count(*) from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + userOrg + "$%' or org.orgId=" + curOrgId);
          if (iter.hasNext() && 
            Integer.parseInt(iter.next().toString()) > 0)
            result = Boolean.TRUE; 
        } else if (scopeType.equals("3")) {
          if (userOrg.equals(curOrgId))
            result = Boolean.TRUE; 
        } else if (scopeType.equals("4")) {
          if (scopeScope != null && !scopeScope.equals("")) {
            String tmpSql = "";
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organization.OrganizationVO b where a.orgIdString like concat('%$', b.orgId, '$%') and '" + 

                
                scopeScope + "' like concat('%*', b.orgId, '*%')";
            } else {
              tmpSql = "select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a, com.js.system.vo.organization.OrganizationVO b where a.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(b.orgId)), '$%') and '" + 

                
                scopeScope + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(b.orgId)), '*%')";
            } 
            Iterator<E> iter = this.session.iterate(tmpSql);
            while (iter.hasNext()) {
              if (iter.next().toString().equals(curOrgId)) {
                result = Boolean.TRUE;
                break;
              } 
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getCanVindicate_ISO(String where, String userId, String orgId, String domainId) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    try {
      String mySql = "select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        where + ") and (aaa.channelType=0 or aaa.userDefine=1) and (aaa.afficheChannelStatus is null or  aaa.afficheChannelStatus='0') and aaa.domainId=" + domainId + " order by aaa.channelIdString, aaa.channelType";
      Query query = this.session.createQuery(mySql);
      Iterator<Object[]> iter = query.iterate();
      Iterator<E> iter2 = null;
      StringBuffer channelNameBuf = new StringBuffer();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[4].toString().equals("1"))
          if (obj[2].toString().equals("0")) {
            obj[1] = "知识管理·" + obj[1];
          } else {
            query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
                obj[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              obj[1] = (new StringBuilder()).append(iter2.next()).append("·").append(obj[1]).toString(); 
          }  
        alist.add(obj);
      } 
      query = this.session.createQuery("select aaa.rightScopeType, aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.right bbb join aaa.employee ccc where ccc.empId = " + 

          
          userId + " and bbb.rightCode ='01*01*02'");
      iter = query.iterate();
      if (iter.hasNext()) {
        StringBuffer sql = new StringBuffer();
        sql.append("select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa,com.js.system.vo.organizationmanager.OrganizationVO bbb where aaa.channelType=bbb.orgId and aaa.domainId=" + 
            domainId + " ");
        Object[] obj = iter.next();
        int rightScopeType = Integer.parseInt(obj[0].toString());
        switch (rightScopeType) {
          case 0:
            sql.append(" and (aaa.channelType>0 and aaa.userDefine=0) ");
            break;
          case 2:
            sql.append(" and (aaa.channelType=" + orgId + ") ");
            break;
          case 3:
            query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organization.OrganizationVO aaa where aaa.orgIdString like '%$" + 
                orgId + "$%'");
            iter = query.iterate();
            while (iter.hasNext())
              sql.append(" and (aaa.channelType=" + iter.next() + ") "); 
            break;
          case 4:
            if (obj[1] != null) {
              String scope = obj[1].toString();
              scope = scope.substring(1, scope.length() - 1);
              String[] tmp = { "" };
              if (scope.indexOf("*") > 0) {
                tmp = scope.split("**");
              } else {
                tmp[0] = scope;
              } 
              for (int i = 0; i < tmp.length; i++) {
                query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
                    tmp[i] + "$%' order by aaa.orgIdString");
                iter = query.iterate();
                while (iter.hasNext())
                  sql.append(" and (aaa.channelType=" + iter.next() + ") "); 
              } 
            } 
            break;
        } 
        sql.append(" order by bbb.orgIdString");
        query = this.session.createQuery(sql.toString());
        iter = query.iterate();
        channelNameBuf = new StringBuffer();
        String orgNameString = "";
        while (iter.hasNext()) {
          Object[] obj2 = iter.next();
          if (obj2[4].toString().equals("1")) {
            query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + obj2[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              orgNameString = iter2.next().toString(); 
            orgNameString = orgNameString.replace('.', '·');
            obj2[1] = String.valueOf(orgNameString) + "·" + obj2[1];
          } 
          alist.add(obj2);
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List getAllChannel(String orgId, String userId, String channelType) throws Exception {
    List AllChannelList = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode='01*01*02'  and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$'," + 
          orgId + ", '$%') ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, obj[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgChildList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + orgChildList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(" select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      String sql = "";
      if (allScope) {
        sql = "select aaa.channelId, aaa.channelName, aaa.channelShowType,aaa.channelLevel,aaa.channelParentId,aaa.channelSort,aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where  aaa.channelType = " + 
          
          channelType + " order by aaa.channelSort";
      } else {
        String hSql = "select aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + " aaa.channelReaderGroup like '%@" + tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + groupString + " aaa.channelReader like '%$" + userId + "$%' or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='')) ) and aaa.channelType = " + 
          channelType + " order by aaa.channelIdString ";
        query = this.session.createQuery(hSql);
        StringBuffer sb = new StringBuffer();
        tmpList = query.list();
        for (int k = 0; k < tmpList.size(); k++)
          sb.append((String)tmpList.get(k)); 
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "select aaa.channelId, aaa.channelName, aaa.channelShowType,aaa.channelLevel,aaa.channelParentId,aaa.channelSort,aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
            
            channelType + " and '" + 
            sb.toString() + "' like concat('%$', aaa.channelId, '$%') order by aaa.channelSort";
        } else {
          sql = "select aaa.channelId, aaa.channelName, aaa.channelShowType,aaa.channelLevel,aaa.channelParentId,aaa.channelSort,aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
            
            channelType + " and '" + 
            sb.toString() + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelSort";
        } 
      } 
      query = this.session.createQuery(sql);
      AllChannelList = query.list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return AllChannelList;
  }
  
  public List getCanVindicate(String where, String userId, String orgId, String domainId, String infoOrDepartmentType) throws Exception {
    String rightcode = "01*03*03";
    if (infoOrDepartmentType.equals("1"))
      rightcode = "01*01*02"; 
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    try {
      String mySql = "select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        where + ") and (aaa.channelType=0 or aaa.userDefine=1) and (aaa.afficheChannelStatus is null or  aaa.afficheChannelStatus='0') and aaa.domainId=" + domainId + " order by aaa.channelIdString, aaa.channelType";
      Query query = this.session.createQuery(mySql);
      Iterator<Object[]> iter = query.iterate();
      Iterator<E> iter2 = null;
      StringBuffer channelNameBuf = new StringBuffer();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[4].toString().equals("1"))
          if (obj[2].toString().equals("0")) {
            obj[1] = "知识管理·" + obj[1];
          } else {
            query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
                obj[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              obj[1] = (new StringBuilder()).append(iter2.next()).append("·").append(obj[1]).toString(); 
          }  
        alist.add(obj);
      } 
      query = this.session.createQuery("select aaa.rightScopeType, aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.right bbb join aaa.employee ccc where ccc.empId = " + 

          
          userId + " and bbb.rightCode ='" + rightcode + "'");
      iter = query.iterate();
      if (iter.hasNext()) {
        StringBuffer sql = new StringBuffer();
        sql.append("select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelIdString, aaa.channelLevel,aaa.afficheChannelStatus from com.js.oa.info.channelmanager.po.InformationChannelPO aaa,com.js.system.vo.organizationmanager.OrganizationVO bbb where aaa.channelType=bbb.orgId and aaa.domainId=" + 
            domainId + " ");
        Object[] obj = iter.next();
        int rightScopeType = Integer.parseInt(obj[0].toString());
        switch (rightScopeType) {
          case 0:
            sql.append(" and (aaa.channelType>0 and aaa.userDefine=0) ");
            break;
          case 2:
            sql.append(" and (aaa.channelType=" + orgId + ") ");
            break;
          case 3:
            query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organization.OrganizationVO aaa where aaa.orgIdString like '%$" + 
                orgId + "$%'");
            iter = query.iterate();
            while (iter.hasNext())
              sql.append(" and (aaa.channelType=" + iter.next() + ") "); 
            break;
          case 4:
            if (obj[1] != null) {
              String scope = obj[1].toString();
              scope = scope.substring(1, scope.length() - 1);
              String[] tmp = { "" };
              if (scope.indexOf("*") > 0) {
                tmp = scope.split("**");
              } else {
                tmp[0] = scope;
              } 
              for (int i = 0; i < tmp.length; i++) {
                query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
                    tmp[i] + "$%' order by aaa.orgIdString");
                iter = query.iterate();
                while (iter.hasNext())
                  sql.append(" and (aaa.channelType=" + iter.next() + ") "); 
              } 
            } 
            break;
        } 
        sql.append(" order by bbb.orgIdString");
        query = this.session.createQuery(sql.toString());
        iter = query.iterate();
        channelNameBuf = new StringBuffer();
        String orgNameString = "";
        while (iter.hasNext()) {
          Object[] obj2 = iter.next();
          if (obj2[4].toString().equals("1")) {
            query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + obj2[2]);
            iter2 = query.iterate();
            if (iter2.hasNext())
              orgNameString = iter2.next().toString(); 
            orgNameString = orgNameString.replace('.', '·');
            obj2[1] = String.valueOf(orgNameString) + "·" + obj2[1];
          } 
          alist.add(obj2);
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      System.out.println(e.getMessage());
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
}
