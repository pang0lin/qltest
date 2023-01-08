package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.SendAssociatePO;
import com.js.doc.doc.po.SendFlowResavePO;
import com.js.doc.doc.po.SenddocumentUpdate;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class SendFileEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(GovDocumentSendFilePO po) throws Exception {
    begin();
    Long result = null;
    int ny = (new Date()).getYear() + 1900;
    try {
      boolean tmp = true;
      if (po.getDocumentSendFileByteNumber() != null && 
        !"".equals(po.getDocumentSendFileByteNumber()) && 
        !"NULL".equals(po.getDocumentSendFileByteNumber()
          .toUpperCase())) {
        Iterator iter = this.session
          .iterate("select po.id from com.js.doc.doc.po.GovDocumentSendFilePO po where po.domainId=" + 
            po.getDomainId() + 
            " and po.sendFilePoNumId=" + 
            po.getSendFilePoNumId() + 
            " and po.field2=" + 
            po.getField2() + " and po.field3= " + ny);
        if (iter.hasNext()) {
          result = Long.valueOf("-1");
          tmp = false;
        } 
      } 
      if (tmp) {
        result = (Long)this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public void sendFileBoxDel(String recordId) throws Exception {
    try {
      begin();
      Long tmpId = Long.valueOf(recordId);
      GovDocumentSendFilePO po = (GovDocumentSendFilePO)this.session.load(
          GovDocumentSendFilePO.class, tmpId);
      po.setField5("0");
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("--------------------------------------");
      e.printStackTrace();
      System.out.println("--------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public Object[] getHeadInfo(String redHeadId) throws Exception {
    Object[] result = (Object[])null;
    begin();
    try {
      Iterator<Object[]> iter = this.session
        .iterate("select po.headTitle,po.headSaveFile,po.headType from com.js.system.redhead.po.InformationHeadPO po where po.id=" + 
          redHeadId);
      if (iter.hasNext())
        result = iter.next(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Object[] getSealInfo(String sealId) throws Exception {
    Object[] result = (Object[])null;
    begin();
    try {
      Iterator<Object[]> iter = this.session
        .iterate("select po.sealName,po.sealSaveFile from com.js.system.redhead.po.InformationSealPO po where po.id=" + 
          sealId);
      if (iter.hasNext())
        result = iter.next(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getRedHeadList(String wherePara) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session
        .createQuery(
          "select po.id,po.headTitle from com.js.system.redhead.po.InformationHeadPO po where " + 
          wherePara).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String getSendFileMaxNumber(String sendFileCode, String sendFileYear, String domainId) throws Exception {
    String retString = "1";
    begin();
    StringBuffer where = new StringBuffer(
        "select max(po.field3) from com.js.doc.doc.po.GovDocumentSendFilePO po");
    where.append(" where ");
    where.append(" po.field1='");
    where.append(sendFileCode.trim());
    where.append("' and po.field2='");
    where.append(sendFileYear.trim());
    where.append("'");
    where.append(" and po.domainId=" + domainId);
    try {
      Iterator<String> itr = this.session.createQuery(where.toString()).iterate();
      if (itr.hasNext()) {
        String max = itr.next();
        if (!"".equals(max) && !"null".equals(max)) {
          retString = String.valueOf(Integer.parseInt(max) + 1);
        } else {
          retString = "1";
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
    } 
    return retString;
  }
  
  public List getSealList(String redHeadId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session
        .createQuery(
          "select po.id,po.sealName from com.js.system.redhead.po.InformationSealPO po join po.heads hpo where hpo.id=" + 
          redHeadId).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long draft(String sendFileId, GovDocumentSendFilePO po) throws Exception {
    Long id = null;
    begin();
    try {
      if (sendFileId != null) {
        po.setId(Long.parseLong(sendFileId));
        this.session.update(po);
      } else {
        id = (Long)this.session.save(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public GovDocumentSendFilePO load(String editId) throws Exception {
    begin();
    GovDocumentSendFilePO po = new GovDocumentSendFilePO();
    try {
      po = (GovDocumentSendFilePO)this.session.load(
          GovDocumentSendFilePO.class, Long.valueOf(editId));
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public GovDocumentSendFilePO get(String editId) throws Exception {
    begin();
    GovDocumentSendFilePO po = new GovDocumentSendFilePO();
    try {
      po = (GovDocumentSendFilePO)this.session.get(
          GovDocumentSendFilePO.class, Long.valueOf(editId));
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String getSendFileHead(String headId) throws Exception {
    String retString = "";
    begin();
    try {
      Iterator<E> iter = this.session
        .iterate("select po.typeSetName from com.js.doc.doc.po.GovTypeSetPO po where po.id=" + 
          headId);
      if (iter.hasNext())
        retString = iter.next().toString(); 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public Long update(String sendFileId, GovDocumentSendFilePO po) throws Exception {
    begin();
    try {
      po.setId(Long.parseLong(sendFileId));
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return Long.valueOf(sendFileId);
  }
  
  public Map getNewInnerDocumentCount(String userIds) throws Exception {
    begin();
    Map<Object, Object> resultMap = new HashMap<Object, Object>();
    try {
      Iterator<Object[]> iter = this.session
        .iterate("select sendFileUser.empId,count(*) from com.js.doc.doc.po.GovDocumentSendFilePO sendFilePO join sendFilePO.sendFileUser sendFileUser where sendFileUser.empId in (" + 
          userIds + 
          ") and sendFileUser.empId in (select noBrowserPO.empId from com.js.doc.doc.po.GovSendFileNoBrowserPO noBrowserPO join noBrowserPO.govSendFile a where a.id=sendFilePO.id) group by sendFileUser.empId");
      Object[] obj = (Object[])null;
      while (iter.hasNext()) {
        obj = iter.next();
        resultMap.put(obj[0].toString(), obj[1].toString());
      } 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return resultMap;
  }
  
  public Integer send(String sendFileId, String userIds, String domainId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      GovDocumentSendFilePO po = (GovDocumentSendFilePO)this.session.load(
          GovDocumentSendFilePO.class, new Long(sendFileId));
      po.setField5("1");
      this.session.update(po);
      this.session.flush();
      char flagCode = '0';
      int nextPos = 0;
      String str = "";
      String userId = "", orgId = "", groupId = "";
      String outOrgId = "";
      for (int i = 0; i < userIds.length(); i++) {
        flagCode = userIds.charAt(i);
        nextPos = userIds.indexOf(flagCode, i + 1);
        str = userIds.substring(i, nextPos + 1);
        if (flagCode == '$') {
          userId = String.valueOf(userId) + str;
        } else if (flagCode == '*') {
          orgId = String.valueOf(orgId) + str;
        } else if (flagCode == ';') {
          outOrgId = String.valueOf(outOrgId) + str;
        } else {
          groupId = String.valueOf(groupId) + str;
        } 
        i = nextPos;
      } 
      if (!userId.equals("")) {
        if (userId.indexOf("$") >= 0)
          userId = userId.substring(1, userId.length() - 1); 
        if (userId.indexOf("$") >= 0) {
          userId = userId.replace('$', ',');
          userId = userId.replaceAll(",,", ",");
        } 
      } else {
        userId = "-1";
      } 
      if (!orgId.equals("")) {
        if (orgId.indexOf("*") >= 0)
          orgId = orgId.substring(1, orgId.length() - 1); 
        if (orgId.indexOf("*") >= 0) {
          orgId = orgId.replace('*', ',');
          orgId = orgId.replaceAll(",,", ",");
        } 
        if (orgId.indexOf(",") == -1) {
          Iterator<E> itr = this.session
            .iterate("select po.orgId from OrganizationVO po where po.orgIdString like '%$" + 
              orgId + "$%'");
          while (itr.hasNext())
            orgId = String.valueOf(orgId) + "," + itr.next().toString(); 
        } else if (orgId.indexOf(",") >= 0) {
          String[] orgIdArr = orgId.split(",");
          for (int j = 0; j < orgIdArr.length; j++) {
            Iterator<E> itr = this.session
              .iterate("select po.orgId from OrganizationVO po where po.orgIdString like '%$" + 
                orgIdArr[j] + "$%'");
            while (itr.hasNext())
              orgId = String.valueOf(orgId) + "," + itr.next().toString(); 
          } 
        } 
      } else {
        orgId = "-1";
      } 
      if (!groupId.equals("")) {
        if (groupId.indexOf("@") >= 0)
          groupId = groupId.substring(1, groupId.length() - 1); 
        if (groupId.indexOf("@") >= 0) {
          groupId = groupId.replace('@', ',');
          groupId = groupId.replaceAll(",,", ",");
        } 
      } else {
        groupId = "-1";
      } 
      if (!outOrgId.equals("")) {
        if (outOrgId.indexOf(";") >= 0)
          outOrgId = outOrgId.substring(1, outOrgId.length() - 1); 
        if (outOrgId.indexOf(";") >= 0) {
          outOrgId = outOrgId.replace(';', ',');
          outOrgId = outOrgId.replaceAll(",,", ",");
        } 
        if (outOrgId.indexOf(",") != -1)
          if (outOrgId.indexOf(",") >= 0) {
            String[] arrayOfString = outOrgId.split(",");
            for (int j = 0; j < arrayOfString.length; j++);
          }  
      } 
      String[] outOrgIdArr = (String[])null;
      if (!outOrgId.equals("")) {
        if (outOrgId.indexOf(";") >= 0)
          outOrgId = outOrgId.substring(1, outOrgId.length() - 1); 
        if (outOrgId.indexOf(";") >= 0) {
          outOrgId = outOrgId.replace(';', ',');
          outOrgId = outOrgId.replaceAll(",,", ",");
        } 
        if (outOrgId.indexOf(",") == -1) {
          outOrgIdArr = new String[] { outOrgId };
        } else if (outOrgId.indexOf(",") >= 0) {
          outOrgIdArr = outOrgId.split(",");
        } 
      } 
      DataSourceBase dsb = new DataSourceBase();
      dsb.begin();
      Object[] obj = (Object[])null;
      String sql = null;
      Iterator<Object[]> iter = this.session
        .iterate("select distinct emp.empId,emp.empName,org.orgNameString from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org left join emp.groups grp where emp.empId in (" + 
          userId + 
          ") or org.orgId in (" + 
          orgId + 
          ") or grp.groupId in (" + groupId + ")");
      while (iter.hasNext()) {
        obj = iter.next();
        List list = this.session
          .createQuery(
            "select po.empId from com.js.doc.doc.po.GovSendFileUserPO po where po.sendFile.id=" + 
            sendFileId + 
            " and po.empId=" + 
            obj[0]).list();
        if (list.size() > 0) {
          this.session.delete("from com.js.doc.doc.po.GovSendFileUserPO po where po.sendFile.id=" + 
              sendFileId + " and po.empId=" + obj[0]);
          this.session.delete("from com.js.doc.doc.po.GovSendFileNoBrowserPO po where po.govSendFile.id=" + 
              sendFileId + " and po.empId=" + obj[0]);
          this.session.delete("from com.js.doc.doc.po.GovSendFileBrowserPO po where po.govSendFile.id=" + 
              sendFileId + " and po.empId=" + obj[0]);
          this.session.flush();
        } 
        sql = "INSERT INTO JSDB.doc_sendfile_user (SENDFILE_USER_ID,SENDFILE_ID,EMP_ID,DOMAIN_ID,isreaded,isDelete) VALUES (" + 
          dsb.getTableId() + 
          "," + 
          sendFileId + 
          "," + 
          obj[0] + 
          "," + domainId + ",0 " + ",'0' )";
        dsb.executeSQL(sql);
        sql = "INSERT INTO JSDB.doc_SENDFILENOBROWSER (BROWSER_ID,DOCUMENTSENDFILE_ID,EMP_ID,BROWSERNAME,BROWSERORGNAME,DOMAIN_ID) VALUES (" + 
          dsb.getTableId() + 
          "," + 
          sendFileId + 
          "," + 
          obj[0] + 
          ",'" + 
          obj[1] + 
          "','" + 
          obj[2] + 
          "'," + 
          domainId + 
          ")";
        dsb.executeSQL(sql);
      } 
      if (outOrgIdArr != null && outOrgIdArr.length > 0)
        for (int ii = 0; ii < outOrgIdArr.length; ii++) {
          List outList = this.session
            .createQuery(
              "select po.orgId from com.js.doc.doc.po.GovSendFileUserPO po where po.sendFile.id=" + 
              sendFileId + 
              " and po.orgId='" + 
              outOrgIdArr[ii] + "'").list();
          if (outList.size() > 0) {
            this.session.delete("from com.js.doc.doc.po.GovSendFileUserPO po where po.sendFile.id=" + 
                sendFileId + 
                " and po.orgId='" + 
                outOrgIdArr[ii] + "'");
            this.session.flush();
          } 
          sql = "INSERT INTO JSDB.doc_sendfile_user (SENDFILE_USER_ID,SENDFILE_ID,DOMAIN_ID,orgId) VALUES (" + 
            dsb.getTableId() + 
            "," + 
            sendFileId + 
            "," + 
            domainId + ",'" + outOrgIdArr[ii] + "')";
          dsb.executeSQL(sql);
        }  
      dsb.end();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer send(String sendFileId, String userIds, String domainId, String userName, String orgName) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      stmt.execute("UPDATE DOC_DOCUMENTSENDFILE SET FIELD5='1' WHERE DOCUMENTSENDFILE_ID=" + 
          sendFileId);
      char flagCode = '0';
      int nextPos = 0;
      String str = "";
      String userId = "", orgId = "", groupId = "";
      String outOrgId = "";
      for (int i = 0; i < userIds.length(); i++) {
        flagCode = userIds.charAt(i);
        nextPos = userIds.indexOf(flagCode, i + 1);
        str = userIds.substring(i, nextPos + 1);
        if (flagCode == '$') {
          userId = String.valueOf(userId) + str;
        } else if (flagCode == '*') {
          orgId = String.valueOf(orgId) + str;
        } else {
          groupId = String.valueOf(groupId) + str;
        } 
        i = nextPos;
      } 
      if (!userId.equals("")) {
        if (userId.indexOf("$") >= 0) {
          userId = userId.replace('$', ',');
          userId = userId.replaceAll(",,", ",");
        } 
      } else {
        userId = ",-1,";
      } 
      StringBuffer toUserId = new StringBuffer(userId);
      if (!groupId.equals("")) {
        if (groupId.indexOf("@") >= 0)
          groupId = groupId.substring(1, groupId.length() - 1); 
        if (groupId.indexOf("@") >= 0) {
          groupId = groupId.replace('@', ',');
          groupId = groupId.replaceAll(",,", ",");
        } 
        rs = stmt
          .executeQuery("SELECT EMP_ID FROM ORG_USER_GROUP WHERE GROUP_ID IN (" + 
            groupId + ")");
        String tmp = "";
        while (rs.next()) {
          tmp = rs.getString(1);
          if (toUserId.indexOf("," + tmp + ",") < 0)
            toUserId.append(String.valueOf(tmp) + ","); 
        } 
        rs.close();
      } 
      if (!orgId.equals("")) {
        if (orgId.indexOf("*") >= 0)
          orgId = orgId.substring(1, orgId.length() - 1); 
        if (orgId.indexOf("*") >= 0) {
          orgId = orgId.replace('*', ',');
          orgId = orgId.replaceAll(",,", ",");
        } 
        String[] tmpStr = { orgId };
        if (orgId.indexOf(",") > 0)
          tmpStr = orgId.split(","); 
        String whereSql = "";
        String whereSql2 = "";
        for (int k = 0; k < tmpStr.length; k++) {
          whereSql = String.valueOf(whereSql) + "A.ORGIDSTRING LIKE '%$" + tmpStr[k] + 
            "$%' OR ";
          whereSql2 = String.valueOf(whereSql2) + " C.SIDELINEORG  like '%*" + tmpStr[k] + 
            "*%' OR ";
        } 
        whereSql2 = String.valueOf(whereSql2) + " 1>1 ";
        whereSql = String.valueOf(whereSql) + " 1 > 1 ";
        rs = stmt
          .executeQuery("SELECT B.emp_id FROM ORG_ORGANIZATION A, ORG_ORGANIZATION_USER B  WHERE   A.ORG_ID=B.ORG_ID   AND (" + 
            whereSql + ") and exists(select 1 from org_employee where emp_id = b.emp_id and userisdeleted=0)");
        String tmp = "";
        while (rs.next()) {
          tmp = rs.getString(1);
          if (toUserId.indexOf("," + tmp + ",") < 0)
            toUserId.append(String.valueOf(tmp) + ","); 
        } 
        rs.close();
        String tmp2 = "";
        rs = stmt
          .executeQuery("SELECT C.emp_id FROM  org_employee C  WHERE " + 
            whereSql2);
        while (rs.next()) {
          tmp2 = rs.getString(1);
          if (toUserId.indexOf("," + tmp2 + ",") < 0)
            toUserId.append(String.valueOf(tmp2) + ","); 
        } 
        rs.close();
      } 
      String userStrs = toUserId.toString();
      if (userStrs.startsWith(","))
        userStrs = userStrs.substring(1); 
      if (userStrs.endsWith(","))
        userStrs = userStrs.substring(0, userStrs.length() - 1); 
      String[] users = userStrs.split(",");
      int count = 0;
      PreparedStatement pstmt_delete = conn
        .prepareStatement("delete from doc_sendfile_user where sendFile_id=" + 
          sendFileId + " and EMP_ID = ?");
      PreparedStatement pstmt_insert = conn
        .prepareStatement("INSERT INTO doc_sendfile_user (EMP_ID, USERNAME, ORGNAME, SENDFILE_USER_ID, SENDFILE_ID, DOMAIN_ID,isReaded) SELECT EMP.EMP_ID, EMP.EMPNAME, ORG.ORGNAME, ?, '" + 
          sendFileId + 
          "', 0,0 FROM ORG_EMPLOYEE EMP, ORG_ORGANIZATION ORG, ORG_ORGANIZATION_USER A WHERE A.EMP_ID=EMP.EMP_ID AND A.ORG_ID=ORG.ORG_ID and EMP.EMP_ID = ?");
      DataSourceBase dsb = new DataSourceBase();
      dsb.begin();
      int idInc = Integer.valueOf(dsb.getTableId(users.length)).intValue();
      dsb.end();
      for (int j = 0; j < users.length; j++) {
        String user = users[j];
        if (!"".equals(user)) {
          count++;
          pstmt_delete.setInt(1, Integer.valueOf(user).intValue());
          pstmt_delete.addBatch();
          pstmt_insert.setInt(1, idInc++);
          pstmt_insert.setInt(2, Integer.valueOf(user).intValue());
          pstmt_insert.addBatch();
          if (count == 500) {
            pstmt_delete.executeBatch();
            pstmt_insert.executeBatch();
            count = 0;
          } 
        } 
      } 
      if (count > 0) {
        pstmt_delete.executeBatch();
        pstmt_insert.executeBatch();
      } 
      pstmt_delete.close();
      pstmt_insert.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer sendToMyReceiveCancel(String sendFileId) {
    Integer result = Integer.valueOf(0);
    String sql = "delete FROM doc_sendfile_user WHERE sendfile_id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, sendFileId);
      result = Integer.valueOf(pstmt.executeUpdate());
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getSendMsgReceiver(String userIds) throws Exception {
    StringBuffer result = new StringBuffer();
    begin();
    try {
      char flagCode = '0';
      int nextPos = 0;
      String str = "";
      String userId = "", orgId = "", groupId = "";
      for (int i = 0; i < userIds.length(); i++) {
        flagCode = userIds.charAt(i);
        nextPos = userIds.indexOf(flagCode, i + 1);
        str = userIds.substring(i, nextPos + 1);
        if (flagCode == '$') {
          userId = String.valueOf(userId) + str;
        } else if (flagCode == '*') {
          orgId = String.valueOf(orgId) + str;
        } else {
          groupId = String.valueOf(groupId) + str;
        } 
        i = nextPos;
      } 
      if (!userId.equals("")) {
        if (userId.indexOf("$") >= 0)
          userId = userId.substring(1, userId.length() - 1); 
        if (userId.indexOf("$") >= 0) {
          userId = userId.replace('$', ',');
          userId = userId.replaceAll(",,", ",");
        } 
      } else {
        userId = "-1";
      } 
      String orgCondition = "";
      if (!orgId.equals("")) {
        if (orgId.indexOf("*") >= 0)
          orgId = orgId.substring(1, orgId.length() - 1); 
        if (orgId.indexOf("*") >= 0) {
          orgId = orgId.replace('*', ',');
          orgId = orgId.replaceAll(",,", ",");
        } 
        if ("-1".equals(orgId)) {
          orgCondition = " 1=1 ";
        } else {
          String[] orgIdS = orgId.split(",");
          orgCondition = "";
          for (int j = 0; j < orgIdS.length; j++) {
            String orgid = orgIdS[j];
            DbOpt db = new DbOpt();
            String[] ids = db
              .executeQueryToStrArr1("select org_id from org_organization where orgidString like '%$" + 
                orgid + "$%'");
            db.close();
            for (int k = 0; ids != null && k < ids.length; k++) {
              if ("".equals(orgCondition)) {
                orgCondition = ids[k];
              } else {
                orgCondition = String.valueOf(orgCondition) + "," + ids[k];
              } 
            } 
          } 
          orgCondition = " org.orgId in (" + orgCondition + ") ";
        } 
      } else {
        orgId = "-1";
        orgCondition = " 0=1 ";
      } 
      if (!groupId.equals("")) {
        if (groupId.indexOf("@") >= 0)
          groupId = groupId.substring(1, groupId.length() - 1); 
        if (groupId.indexOf("@") >= 0) {
          groupId = groupId.replace('@', ',');
          groupId = groupId.replaceAll(",,", ",");
        } 
      } else {
        groupId = "-1";
      } 
      DataSourceBase dsb = new DataSourceBase();
      dsb.begin();
      Object obj = null;
      Iterator iter = this.session
        .iterate("select distinct emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org left join emp.groups grp where emp.empId in (" + 
          userId + 
          ") or " + 
          orgCondition + 
          " or grp.groupId in (" + groupId + ")");
      while (iter.hasNext()) {
        obj = iter.next();
        result.append(String.valueOf(obj.toString()) + ",");
      } 
      dsb.end();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      result = null;
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    if (result != null)
      return result.toString(); 
    return "";
  }
  
  public Integer setSendFileBrower(String sendFileId, String userId, String userName, String orgName, String domainId) throws Exception {
    begin();
    Integer result = Integer.valueOf("0");
    try {
      Iterator iter = this.session
        .iterate("select PO.id from com.js.doc.doc.po.GovSendFileUserPO PO where PO.empId=" + 
          userId + 
          " and PO.sendFile.id=" + 
          sendFileId + 
          " and (PO.isReaded is null or PO.isReaded =0) ");
      if (iter.hasNext()) {
        Connection conn = (new DataSourceBase())
          .getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.equals("oracle")) {
          stmt.execute("update doc_sendfile_user set isreaded=1 , readDate=sysdate where emp_id=" + 
              userId + " and sendfile_id=" + sendFileId);
        } else if ("mysql".equals(databaseType)) {
          stmt.execute("update doc_sendfile_user set isreaded=1 , readDate=now() where emp_id=" + 
              userId + " and sendfile_id=" + sendFileId);
        } else if ("db2".equals(databaseType)) {
          stmt.execute("update doc_sendfile_user set isreaded=1 , readDate= (CURRENT TIMESTAMP) where emp_id=" + 
              userId + " and sendfile_id=" + sendFileId);
        } else {
          stmt.execute("update doc_sendfile_user set isreaded=1 , readDate=getdate() where emp_id=" + 
              userId + " and sendfile_id=" + sendFileId);
        } 
        this.session.flush();
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getBrowerUser(String sendFileId, Integer type) throws Exception {
    List result = null;
    try {
      begin();
      if (type.intValue() == 1) {
        result = this.session
          .createQuery(
            "select PO.browserName,PO.browserOrgName from com.js.doc.doc.po.GovSendFileNoBrowserPO PO where PO.govSendFile.id=" + 
            sendFileId + " ").list();
      } else {
        result = this.session
          .createQuery(
            "select PO.browserName,PO.browserOrgName,PO.browseTime from com.js.doc.doc.po.GovSendFileBrowserPO PO where PO.govSendFile.id=" + 
            sendFileId + " ").list();
      } 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer removeSendUser(String sendUserId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("DELETE from JSDB.doc_sendfile_user WHERE SENDFILE_USER_ID IN (" + 
          sendUserId + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer removeOneSendUser(String sendUserId, String userId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("DELETE from JSDB.doc_sendfile_user WHERE SENDFILE_USER_ID IN (" + 
          sendUserId + ") and emp_id=" + userId);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Map getDossierInfo(String sendFileId) throws Exception {
    Map<Object, Object> dossMap = null;
    begin();
    try {
      Iterator<Object[]> iter = this.session
        .iterate("select sendFile.documentSendFileTitle, sendFile.documentSendFileWriteOrg, sendFile.documentSendFileByteNumber, sendFile.sendFileText, sendFile.documentSendFileSendDate, sendFile.documentSendFileTopicWord, sendFile.accessoryName, sendFile.accessorySaveName,sendFile.field2,sendFile.documentSendFileSecurityGrade,sendFile.sendFileAccessoryDesc,sendFile.sendFileDraft from com.js.doc.doc.po.GovDocumentSendFilePO sendFile where sendFile.id=" + 
          sendFileId);
      if (iter.hasNext()) {
        dossMap = new HashMap<Object, Object>();
        Object[] obj = iter.next();
        if (obj[8] != null) {
          dossMap.put("YEAR", obj[8].toString());
        } else {
          dossMap.put("YEAR", "");
        } 
        if (obj[8] != null) {
          dossMap.put("TAG", obj[8].toString());
        } else {
          dossMap.put("TAG", "");
        } 
        dossMap.put("TITLE", obj[0]);
        dossMap.put("DUTY", obj[1]);
        SimpleDateFormat formatter1 = new SimpleDateFormat(
            "yyyy-MM-dd");
        Date dt = formatter1.parse(obj[4].toString());
        dossMap.put("RECEIVEDATE", formatter1.format(dt));
        dossMap.put("SECRET", obj[9]);
        dossMap.put("TOPICWORD", obj[5]);
        dossMap.put("ATTACHNAME", obj[10]);
        dossMap.put("PERSON", obj[11]);
        dossMap.put("FILENUMBER", obj[2]);
        String accessoryName = (obj[6] == null) ? "" : obj[6].toString();
        String accessorySaveName = (obj[7] == null) ? "" : obj[7]
          .toString();
        List<String[]> accessoryList = new ArrayList();
        if (obj[3] != null && !obj[3].toString().equals("")) {
          String goldAccessoryName = (obj[0] == null) ? "" : (
            String.valueOf(obj[0].toString()) + ".doc");
          String goldAccessorySaveName = (obj[3] == null) ? "" : (
            String.valueOf(obj[3].toString()) + ".doc");
          String[] goldTmp = { goldAccessoryName, 
              goldAccessorySaveName };
          accessoryList.add(goldTmp);
        } 
        if (!accessoryName.equals("")) {
          String[] accessoryNameArray = { "" };
          String[] accessorySaveNameArray = { "" };
          if (accessoryName.indexOf("|") >= 0) {
            accessoryNameArray = accessoryName.split("\\|");
            accessorySaveNameArray = accessorySaveName.split("\\|");
          } else {
            accessoryNameArray[0] = accessoryName;
            accessorySaveNameArray[0] = accessorySaveName;
          } 
          for (int j = 0; j < accessoryNameArray.length; j++) {
            String[] tmp = { "", "" };
            tmp[0] = accessoryNameArray[j];
            tmp[1] = accessorySaveNameArray[j];
            accessoryList.add(tmp);
          } 
        } 
        dossMap.put("ACCESSORY", accessoryList);
      } 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return dossMap;
  }
  
  public Boolean isPigeonholed(String sendFileId) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      Iterator iter = this.session
        .iterate("select sendFile.thirdDossier from com.js.doc.doc.po.GovDocumentSendFilePO sendFile where sendFile.id=" + 
          sendFileId);
      if (iter.hasNext()) {
        Object thirdDossier = iter.next();
        if (thirdDossier != null && 
          Integer.parseInt(thirdDossier.toString()) == 1)
          result = Boolean.TRUE; 
      } 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer setPigeonholed(String sendFileId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      GovDocumentSendFilePO po = (GovDocumentSendFilePO)this.session.load(
          GovDocumentSendFilePO.class, Long.valueOf(sendFileId));
      po.setThirdDossier(Integer.valueOf("1"));
      this.session.flush();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String delBatch(String ids) throws Exception {
    String retString = "";
    begin();
    try {
      ids = String.valueOf(ids) + "-1";
      List<String> listAcc = this.session
        .createQuery(
          "select po.accessorySaveName from com.js.doc.doc.po.GovDocumentSendFilePO po where po.id in (" + 
          ids + ")").list();
      for (int i = 0; i < listAcc.size(); i++)
        retString = String.valueOf(retString) + listAcc.get(i) + "|"; 
      this.session.delete(" from com.js.doc.doc.po.GovDocumentSendFilePO po where po.id in (" + 
          ids + ")");
      this.session.flush();
    } catch (Exception e) {
      retString = "";
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public Map getDocWF(String id, String moduleId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt
        .executeQuery("SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + 
          moduleId);
      String tableId = "", processId = "";
      if (rs.next())
        tableId = rs.getString(1); 
      rs = stmt
        .executeQuery("SELECT WORKPROCESS_ID FROM JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID=" + id);
      if (rs.next())
        processId = rs.getString(1); 
      stmt.close();
      conn.close();
      result.put("tableId", tableId);
      result.put("processId", processId);
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Integer delete(String sendFileId) throws Exception {
    Integer result = Integer.valueOf("0");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt
        .executeQuery("select sendfile_tableId from doc_documentsendfile where documentsendfile_id=" + 
          sendFileId);
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      stmt.execute("delete from jsf_dealwithcomment where wf_dealwith_id in (select wf_dealwith_id from jsf_dealwith where databasetable_id=" + 
          tableId + " and databaserecord_id=" + sendFileId + ")");
      stmt.execute("delete from jsf_dealwith where databasetable_id=" + 
          tableId + " and databaserecord_id=" + sendFileId);
      stmt.execute("delete from jsf_p_tr where wf_proceedtransition_id in (select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + 
          tableId + " and trecord_id=" + sendFileId + " ))");
      stmt.execute("delete from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + 
          tableId + " and trecord_id=" + sendFileId + " )");
      stmt.execute("delete from jsf_p_readwritecontrol where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + 
          tableId + " and trecord_id=" + sendFileId + " )");
      stmt.execute("delete from jsf_p_activity where ttable_id=" + 
          tableId + " and trecord_id=" + sendFileId);
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID=" + sendFileId);
      stmt.execute("DELETE from JSDB.doc_sendfile_user WHERE SENDFILE_ID=" + 
          sendFileId);
      stmt.execute("DELETE from JSDB.DOC_DOCUMENTSENDFILE WHERE DOCUMENTSENDFILE_ID=" + 
          sendFileId);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public Long completeSendFile(String sendFileId) throws Exception {
    Long result = Long.valueOf(sendFileId);
    begin();
    try {
      GovDocumentSendFilePO po = (GovDocumentSendFilePO)this.session.load(
          GovDocumentSendFilePO.class, Long.valueOf(sendFileId));
      po.setTransactStatus("1");
      this.session.flush();
    } catch (Exception e) {
      result = Long.valueOf("-1");
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public HashMap getUserOrg(String empId) throws Exception {
    HashMap<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      Iterator iter = this.session
        .iterate("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org join org.employees emp where emp.empId=" + 
          empId);
      if (iter.hasNext())
        result.put("orgId", iter.next()); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String getSendRtxReceiver(String userIds) throws Exception {
    StringBuffer result = new StringBuffer();
    begin();
    try {
      char flagCode = '0';
      int nextPos = 0;
      String str = "";
      String userId = "", orgId = "", groupId = "";
      for (int i = 0; i < userIds.length(); i++) {
        flagCode = userIds.charAt(i);
        nextPos = userIds.indexOf(flagCode, i + 1);
        str = userIds.substring(i, nextPos + 1);
        if (flagCode == '$') {
          userId = String.valueOf(userId) + str;
        } else if (flagCode == '*') {
          orgId = String.valueOf(orgId) + str;
        } else {
          groupId = String.valueOf(groupId) + str;
        } 
        i = nextPos;
      } 
      if (!userId.equals("")) {
        if (userId.indexOf("$") >= 0)
          userId = userId.substring(1, userId.length() - 1); 
        if (userId.indexOf("$") >= 0) {
          userId = userId.replace('$', ',');
          userId = userId.replaceAll(",,", ",");
        } 
      } else {
        userId = "-1";
      } 
      if (!orgId.equals("")) {
        if (orgId.indexOf("*") >= 0)
          orgId = orgId.substring(1, orgId.length() - 1); 
        if (orgId.indexOf("*") >= 0) {
          orgId = orgId.replace('*', ',');
          orgId = orgId.replaceAll(",,", ",");
        } 
      } else {
        orgId = "-1";
      } 
      if (!groupId.equals("")) {
        if (groupId.indexOf("@") >= 0)
          groupId = groupId.substring(1, groupId.length() - 1); 
        if (groupId.indexOf("@") >= 0) {
          groupId = groupId.replace('@', ',');
          groupId = groupId.replaceAll(",,", ",");
        } 
      } else {
        groupId = "-1";
      } 
      Object obj = null;
      Iterator iter = this.session
        .iterate("select distinct emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org left join emp.groups grp where emp.empId in (" + 
          userId + 
          ") or org.orgId in (" + 
          orgId + 
          ") or grp.groupId in (" + groupId + ")");
      while (iter.hasNext()) {
        obj = iter.next();
        if (obj != null)
          result.append(String.valueOf(obj.toString()) + ","); 
      } 
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      result = null;
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    if (result != null)
      return result.toString(); 
    return "";
  }
  
  public Long saveResave(SendFlowResavePO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-1");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Long updateResave(SendFlowResavePO po) throws Exception {
    begin();
    Long result = null;
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-1");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String deleterResave(String id) throws Exception {
    String result = "1";
    begin();
    try {
      this.session.delete("from com.js.doc.doc.po.SendFlowResavePO po where po.id in(" + 
          id + ")");
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = "-1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getAllResavePoByEmpIdType(String empId, String type, String sendId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session
        .createQuery(
          "select po.id,po.flowEmpId,po.flowContent from com.js.doc.doc.po.SendFlowResavePO po where po.flowEmpId=" + 
          empId + 
          " and po.flowType= '" + 
          type + 
          "' and po.sendId= " + sendId).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String deleterResaveByTwo(String sendId, String type) throws Exception {
    String result = "1";
    begin();
    try {
      this.session.delete("from com.js.doc.doc.po.SendFlowResavePO po where po.sendId=" + 
          sendId + " and po.flowType='" + type + "'");
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = "-1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String monitorRedo(String recordId, String tableId, String processId, String workId, String title, String cancelReason) throws Exception {
    String retString = "false";
    begin();
    Connection conn = (new DataSourceBase())
      .getDataSource().getConnection();
    Statement stmt = conn.createStatement();
    try {
      stmt.executeUpdate(" delete from JSF_WORK where WORKPROCESS_ID= " + 
          processId + " and WORKTABLE_ID=" + tableId + 
          " and WORKRECORD_ID =" + recordId + " and wf_work_id<>" + 
          workId);
      stmt.executeUpdate(" update JSF_WORK set workTitle = '" + title + 
          "', workStatus = -2, workCancelReason = '" + cancelReason + 
          "' where wf_work_id = " + workId);
      retString = "true";
      stmt.close();
      conn.close();
    } catch (Exception e) {
      retString = "false";
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {}
    this.session.close();
    return retString;
  }
  
  public Long saveSendAssociate(SendAssociatePO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Integer getSendAssociateNum(String sendfileId) throws Exception {
    Integer result = new Integer(0);
    try {
      begin();
      Query query = this.session
        .createQuery("select count(po.aossiateId) from com.js.doc.doc.po.SendAssociatePO po where po.sendFileId=" + 
          sendfileId);
      result = query.iterate().next();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getSendAssociateList(String sendFileId) throws Exception {
    begin();
    List<Object[]> result = new ArrayList();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String ww = "select r.receivefile_id, r.receivefile_receivedate,org.orgName,r.receivefile_status from DOC_receivefile r , JSDB.ORG_ORGANIZATION org, JSDB.DOC_SendAssociate  a  where a.sendFileId=" + 
        sendFileId + 
        " and  r.receivefile_id =a.receiveFileId and  r.createdorg=org.ORG_ID  order by r.receivefile_id desc ";
      ResultSet rs = stmt.executeQuery(ww);
      while (rs.next()) {
        Object[] obj = { rs.getLong(1), 
            rs.getDate(2), rs.getString(3), rs.getString(4) };
        result.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer getOutSeeNum(String userId) throws Exception {
    Integer result = new Integer(0);
    try {
      begin();
      Query query = this.session
        .createQuery("select count(po.id) from com.js.doc.doc.po.GovSendFileUserPO po where po.empId=" + 
          userId + " and po.outSeeType='1'");
      result = query.iterate().next();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long saveSendUpdate(SenddocumentUpdate po) throws Exception {
    Long id = null;
    begin();
    try {
      Long long_1 = po.getSendFileId();
      Long long_2 = po.getUpdateEmpId();
      String type = (new StringBuilder(String.valueOf(po.getSendMainTo()))).toString();
      Iterator iter = this.session
        .iterate("select po.id from com.js.doc.doc.po.SenddocumentUpdate po where po.updateEmpId = " + 
          long_2 + 
          " and  po.sendFileId= " + 
          long_1 + " and po.sendMainTo='" + type + "'");
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null)
          id = new Long((String)obj); 
      } 
      if (id != null) {
        po.setId(id);
        this.session.update(po);
      } else {
        id = (Long)this.session.save(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public List getAllSendDocumentUpdatePO(String sendFileId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session
        .createQuery(
          "select po.id,po.updateEmpName,po.updateOrgName,po.sendTitle,po.sendMainTo,po.sendCopyTo,po.updateTime ,po.updateEmpId from com.js.doc.doc.po.SenddocumentUpdate po where po.sendFileId=" + 
          sendFileId + 
          "  order by po.updateTime asc  ").list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getWfTableInfoByTableId(String tableId) throws Exception {
    begin();
    List<Object[]> result = new ArrayList();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String ww = "select  immoForm_displayName,immoForm_realName,wf_module_id, immoForm_primaryKey from jsf_immobilityform  where  wf_immoForm_id=" + 
        tableId;
      ResultSet rs = stmt.executeQuery(ww);
      while (rs.next()) {
        Object[] obj = { rs.getString(1), 
            rs.getString(2), rs.getInt(3), rs.getString(4) };
        result.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getCommentByCommFiledList(String moduleId, String recordId, String commField) throws Exception {
    List<Object[]> list = new ArrayList();
    String comment = "", empName = "", dealwithDate = "", isStandForComm = "", standForUserName = "";
    begin();
    String processId = "";
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt
        .executeQuery("SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + 
          moduleId);
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      rs = stmt
        .executeQuery("SELECT WORKCURSTEP,WF_SUBMITEMPLOYEE_ID,WORKSUBMITPERSON,WF_WORK_ID,WORKTYPE,WORKACTIVITY,WORKFILETYPE,WORKSUBMITTIME,WORKPROCESS_ID,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + 
          " AND WORKRECORD_ID=" + 
          recordId + 
          " AND (WORKSTATUS=1 OR WORKSTATUS=100)");
      if (rs.next())
        processId = rs.getString(9); 
      rs.close();
      rs = stmt
        .executeQuery("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND A.DATABASETABLE_ID=" + 

          
          tableId + 
          " AND A.DATABASERECORD_ID=" + 
          recordId + 
          " AND B.COMMENTFIELD='" + 
          commField + 
          "' " + 
          "AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY WHERE WF_WORKFLOWPROCESS_ID=" + 
          processId + ")");
      while (rs.next()) {
        comment = rs.getString(1);
        empName = rs.getString(2);
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        Object[] obj = { comment, empName, dealwithDate, 
            isStandForComm, standForUserName };
        list.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out
        .println("----------------------------------------------");
      e.printStackTrace();
      System.out
        .println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String getCommentByCommFiled(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) throws Exception {
    StringBuffer result;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      result = new StringBuffer("<table width=100%>");
      StringBuffer sql = new StringBuffer(
          "SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME,B.WF_DEALWITHCOMMENT_ID,B.RANGENAME,B.RANGEIDSTR,C.SIGNATUREIMGSAVENAME,B.commtype,B.signcomment FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C,JSF_ACTIVITY D WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND A.ACTIVITY_ID=D.WF_ACTIVITY_ID AND A.DATABASETABLE_ID=" + 
          tableId + 
          " AND A.DATABASERECORD_ID=" + 
          recordId + 
          " AND B.COMMENTFIELD='" + 
          commField + 
          "' " + 
          "AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY ");
      if (SystemCommon.getShowBackComment() == 1) {
        sql.append(" where ");
      } else {
        sql.append(" WHERE ( B.commtype is null or B.commtype<>1 ) and ");
      } 
      sql.append("WF_WORKFLOWPROCESS_ID=" + processId + ") ");
      if (isEdit == null || isEdit.equals("0")) {
        sql.append("AND (B.RANGEIDSTR IS NULL OR B.RANGEIDSTR='' ");
        sql.append("OR B.RANGEIDSTR LIKE '%$" + userId + "$%' ");
        rs = stmt
          .executeQuery("SELECT GROUP_ID FROM ORG_USER_GROUP WHERE EMP_ID=" + 
            userId);
        while (rs.next())
          sql
            .append("OR D.COMMENTRANGE LIKE '%@" + rs.getString(1) + 
              "@%' "); 
        rs.close();
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING LIKE concat('%$', A.ORG_ID,'$%') AND B.ORG_ID=" + 
            orgId;
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING locate jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('$', A.ORG_ID),'$')>0 AND B.ORG_ID=" + 
            orgId;
        } else {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING LIKE jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%$', A.ORG_ID),'$%') AND B.ORG_ID=" + 
            orgId;
        } 
        for (rs = stmt.executeQuery(tmpSql); rs.next();)
          sql
            .append("OR D.COMMENTRANGE LIKE '%*" + rs.getString(1) + 
              "*%' "); 
        rs.close();
        sql.append(")");
      } 
      sql.append(" ORDER BY B.WF_DEALWITHCOMMENT_ID");
      System.out.println("sql:" + sql);
      rs = stmt.executeQuery(sql.toString());
      String comment = "";
      String empName = "";
      String dealwithDate = "";
      String isStandForComm = "";
      String standForUserName = "";
      String SIGNATUREIMGSAVENAME = "";
      String commtype = "";
      String commnetFontSize = 
        SystemCommon.getCommentFontSize();
      while (rs.next()) {
        if (rs.getString(1) == null || "".equals(rs.getString(1)) || 
          rs.getString(1).indexOf("无批示意见") != -1)
          continue; 
        comment = rs.getString(1);
        if (isNumeric(comment))
          break; 
        empName = rs.getString(2);
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        SIGNATUREIMGSAVENAME = (rs.getString(9) == null) ? "" : rs
          .getString(9);
        commtype = (rs.getString(10) == null) ? "" : rs.getString(10);
        String signcomment = rs.getString(11);
        if (signcomment != null && signcomment.length() > 4) {
          result.append("<tr>");
          result.append("<td height=1 nowrap valign=bottom>");
          result.append("<div id=\"signPosi_")
            .append(signcomment)
            .append("\" style=\"position:absolute;width:100%;height:100%;\"></div>");
          result.append("</td>");
          result.append("</tr>");
        } 
        result.append("<tr>");
        result.append("<td style=\"font-size:").append(commnetFontSize)
          .append(";\">&nbsp;&nbsp;");
        if (comment != null && !comment.equals("") && 
          !comment.toUpperCase().equals("NULL"))
          result.append(comment); 
        result.append("</td>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td style=\"font-size:").append(commnetFontSize)
          .append(";\" nowrap valign=bottom align=right>");
        String src = "0000";
        if (SIGNATUREIMGSAVENAME.length() > 6 && 
          SIGNATUREIMGSAVENAME.substring(4, 5).equals("_")) {
          src = SIGNATUREIMGSAVENAME.substring(0, 4);
        } else {
          src = "0000";
        } 
        if (commtype.equals("3"))
          result.append("转办人："); 
        if (SIGNATUREIMGSAVENAME.equals("")) {
          result.append((new StringBuilder(String.valueOf(empName))).toString());
        } else {
          result.append("<IMG SRC='/jsoa/upload/" + src + 
              "/peopleinfo/" + SIGNATUREIMGSAVENAME + "'>");
        } 
        if (dealwithDate.endsWith(".0"))
          dealwithDate = dealwithDate.substring(0, 10); 
        result.append("&nbsp;&nbsp;" + dealwithDate);
        if (isStandForComm != null && isStandForComm.equals("1"))
          result.append("（" + standForUserName + "代）"); 
        result.append("&nbsp;&nbsp;</td>");
        result.append("<input type=hidden name=wf_dealwithcomment_id value=" + 
            rs.getString(6) + ">");
        result.append("</tr>");
        if (isEdit != null && isEdit.equals("1")) {
          String rangeName = rs.getString(7);
          String str1 = rs.getString(8);
        } 
        result.append("<tr><td height=10></td></tr>");
      } 
      rs.close();
      result.append("</table>");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return result.toString();
  }
  
  public String updateWorkTitle(String processId, String recordId, String tableId, String workTitle) throws Exception {
    String result = "1";
    begin();
    try {
      Connection conn = (new DataSourceBase())
        .getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("update  JSF_WORK  set  worktitle='" + workTitle + 
          "'  where   workprocess_id=" + processId + 
          " and WORKTABLE_ID=" + tableId + " and  workrecord_id=" + 
          recordId);
      result = "-1";
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = "-1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    return pattern.matcher(str).matches();
  }
}
