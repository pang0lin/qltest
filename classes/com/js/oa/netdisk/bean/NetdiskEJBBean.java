package com.js.oa.netdisk.bean;

import com.js.oa.netdisk.po.NetDiskPO;
import com.js.oa.search.client.SearchService;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class NetdiskEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getmydiskfirst() throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select netdisk from com.js.oa.netdisk.NetdiskPO netdisk where netdisk.fileFatherId=0");
      list = query.list();
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
    return list;
  }
  
  public boolean addfolder(String userId, String userName, String foldername, String currenid, String userAccount, String fileidstring, String domainId) throws Exception {
    boolean result = false;
    begin();
    try {
      NetDiskPO netdiskPO = new NetDiskPO();
      netdiskPO.setFileName("");
      netdiskPO.setFileSaveName(foldername);
      netdiskPO.setFileSaveNameMin("");
      netdiskPO.setFileExtName("DIR");
      netdiskPO.setFileType(new Long(0L));
      netdiskPO.setFileOwn(userName);
      netdiskPO.setFileOwnId(new Long(userId));
      if ("".equals(currenid) || "null".equals(currenid)) {
        netdiskPO.setFileFatherId(new Long(0L));
      } else {
        netdiskPO.setFileFatherId(new Long(currenid));
      } 
      netdiskPO.setFilePath("");
      netdiskPO.setFileIsShare(new Long(0L));
      netdiskPO.setFileShareToName("");
      netdiskPO.setFileShareToEmp("");
      netdiskPO.setFileShareToGroup("");
      netdiskPO.setFileShareToOrg("");
      netdiskPO.setFileCreatedTime(new Date());
      netdiskPO.setFileSize(new Long(0L));
      netdiskPO.setFileNote("");
      netdiskPO.setFileOwenAccount(userAccount);
      netdiskPO.setDomainId(domainId);
      if ("".equals(currenid) || "null".equals(currenid))
        netdiskPO.setFileIdString("$0$"); 
      if (!"0".equals(currenid) && !"null".equals(currenid) && "0".equals(fileidstring))
        netdiskPO.setFileIdString("$" + currenid + "$"); 
      if (!"0".equals(currenid) && !"null".equals(currenid) && !"0".equals(fileidstring))
        netdiskPO.setFileIdString(String.valueOf(fileidstring) + "$" + currenid + "$"); 
      if (netdiskPO.getFileIdString() == null)
        netdiskPO.setFileIdString("$" + fileidstring + "$"); 
      this.session.save(netdiskPO);
      this.session.flush();
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
    return result;
  }
  
  public boolean deletemydisk(String fileid, String userAccount, String realpath) throws Exception {
    boolean result = false;
    String flag = "";
    String candelete = "0";
    String fileName = "";
    String fileext = "";
    String fileNameMin = "";
    String strsql = "";
    begin();
    try {
      NetDiskPO netdiskPO = new NetDiskPO();
      netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(fileid));
      fileName = netdiskPO.getFileName();
      fileext = netdiskPO.getFileExtName();
      fileNameMin = netdiskPO.getFileSaveNameMin();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      strsql = "select count(*) as cou from oa_netdisk_file where file_name='" + fileName + "'";
      ResultSet rs = stmt.executeQuery(strsql);
      if (rs.next()) {
        flag = rs.getString("cou");
        if (Integer.parseInt(flag) > 1)
          candelete = "1"; 
      } 
      Query query = this.session.createQuery("select netdisk.fileId from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileIdString like '%$" + fileid + "$%'");
      List<Long> listdelet = query.list();
      this.session.delete("from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileIdString like '%$" + fileid + "$%'");
      this.session.delete("from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileId = " + fileid);
      SearchService.getInstance();
      String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
      SearchService.getInstance();
      String isearchSwitch = SearchService.getiSearchSwitch();
      if ("1".equals(isearchSwitch) && fileid != null && ifActiveUpdateDelete != null && !"".equals(fileid) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
        SearchService.getInstance();
        SearchService.deleteIndex(fileid, "oa_netdisk_file");
      } 
      if (listdelet != null && listdelet.size() != 0)
        for (int i = 0; i < listdelet.size(); i++) {
          Long id = listdelet.get(i);
          if (id != null && ifActiveUpdateDelete != null && !"".equals(id.toString()) && !"".equals(ifActiveUpdateDelete)) {
            SearchService.getInstance();
            SearchService.deleteIndex(id.toString(), "oa_netdisk_file");
          } 
        }  
      this.session.flush();
      stmt.close();
      conn.close();
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
    return result;
  }
  
  public boolean copyorcut(String currenid, String copyitem, String copyorcut, String userAccount, String domainId) throws Exception {
    boolean result = false;
    begin();
    try {
      if (currenid == null || "".equals(currenid) || "null".equals(currenid))
        currenid = "0"; 
      if ("0".equals(copyorcut)) {
        if (!"".equals(copyitem)) {
          String[] tmp = copyitem.split(",");
          for (int i = 0; i < tmp.length; i++) {
            NetDiskPO netdiskPO = new NetDiskPO();
            NetDiskPO netdiskPOnew = new NetDiskPO();
            netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
            netdiskPOnew.setFileName(netdiskPO.getFileName());
            netdiskPOnew.setFileSaveName(netdiskPO.getFileSaveName());
            netdiskPOnew.setFileSaveNameMin(netdiskPO.getFileSaveNameMin());
            netdiskPOnew.setFileExtName(netdiskPO.getFileExtName());
            netdiskPOnew.setFileType(netdiskPO.getFileType());
            netdiskPOnew.setFileOwn(netdiskPO.getFileOwn());
            netdiskPOnew.setFileOwnId(netdiskPO.getFileOwnId());
            netdiskPOnew.setFileFatherId(new Long(currenid));
            netdiskPOnew.setFilePath(netdiskPO.getFilePath());
            netdiskPOnew.setFileIsShare(netdiskPO.getFileIsShare());
            netdiskPOnew.setFileShareToName(netdiskPO.getFileShareToName());
            netdiskPOnew.setFileShareToEmp(netdiskPO.getFileShareToEmp());
            netdiskPOnew.setFileShareToGroup(netdiskPO.getFileShareToGroup());
            netdiskPOnew.setFileShareToOrg(netdiskPO.getFileShareToOrg());
            netdiskPOnew.setFileCreatedTime(netdiskPO.getFileCreatedTime());
            netdiskPOnew.setFileSize(netdiskPO.getFileSize());
            netdiskPOnew.setFileNote(netdiskPO.getFileNote());
            netdiskPOnew.setFileOwenAccount(userAccount);
            netdiskPOnew.setDomainId(domainId);
            this.session.save(netdiskPOnew);
          } 
        } 
      } else if (!"".equals(copyitem)) {
        String[] tmp = copyitem.split(",");
        for (int i = 0; i < tmp.length; i++) {
          NetDiskPO netdiskPO = new NetDiskPO();
          NetDiskPO netdiskPOnew = new NetDiskPO();
          netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
          netdiskPOnew.setFileName(netdiskPO.getFileName());
          netdiskPOnew.setFileSaveName(netdiskPO.getFileSaveName());
          netdiskPOnew.setFileSaveNameMin(netdiskPO.getFileSaveNameMin());
          netdiskPOnew.setFileExtName(netdiskPO.getFileExtName());
          netdiskPOnew.setFileType(netdiskPO.getFileType());
          netdiskPOnew.setFileOwn(netdiskPO.getFileOwn());
          netdiskPOnew.setFileOwnId(netdiskPO.getFileOwnId());
          netdiskPOnew.setFileFatherId(new Long(currenid));
          netdiskPOnew.setFilePath(netdiskPO.getFilePath());
          netdiskPOnew.setFileIsShare(netdiskPO.getFileIsShare());
          netdiskPOnew.setFileShareToName(netdiskPO.getFileShareToName());
          netdiskPOnew.setFileShareToEmp(netdiskPO.getFileShareToEmp());
          netdiskPOnew.setFileShareToGroup(netdiskPO.getFileShareToGroup());
          netdiskPOnew.setFileShareToOrg(netdiskPO.getFileShareToOrg());
          netdiskPOnew.setFileCreatedTime(netdiskPO.getFileCreatedTime());
          netdiskPOnew.setFileSize(netdiskPO.getFileSize());
          netdiskPOnew.setFileNote(netdiskPO.getFileNote());
          netdiskPOnew.setFileOwenAccount(userAccount);
          netdiskPOnew.setDomainId(domainId);
          this.session.save(netdiskPOnew);
          this.session.delete("from com.js.oa.netdisk.po.NetDiskPO netdisk  where netdisk.fileId = " + tmp[i]);
        } 
      } 
      this.session.flush();
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
    return result;
  }
  
  public boolean copyorcutshare(String currenid, String copyitem, String copyorcut, String userId, String userName, String userAccount, String domainId) throws Exception {
    boolean result = false;
    begin();
    try {
      if (currenid == null || "".equals(currenid) || "null".equals(currenid))
        currenid = "0"; 
      if ("0".equals(copyorcut)) {
        if (!"".equals(copyitem)) {
          String[] tmp = copyitem.split(",");
          for (int i = 0; i < tmp.length; i++) {
            NetDiskPO netdiskPO = new NetDiskPO();
            NetDiskPO netdiskPOnew = new NetDiskPO();
            netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
            netdiskPOnew.setFileName(netdiskPO.getFileName());
            netdiskPOnew.setFileSaveName(netdiskPO.getFileSaveName());
            netdiskPOnew.setFileSaveNameMin(netdiskPO.getFileSaveNameMin());
            netdiskPOnew.setFileExtName(netdiskPO.getFileExtName());
            netdiskPOnew.setFileType(netdiskPO.getFileType());
            netdiskPOnew.setFileOwn(userName);
            netdiskPOnew.setFileOwnId(new Long(userId));
            netdiskPOnew.setFileFatherId(new Long(currenid));
            netdiskPOnew.setFilePath(netdiskPO.getFilePath());
            netdiskPOnew.setFileIsShare(netdiskPO.getFileIsShare());
            netdiskPOnew.setFileShareToName(netdiskPO.getFileShareToName());
            netdiskPOnew.setFileShareToEmp(netdiskPO.getFileShareToEmp());
            netdiskPOnew.setFileShareToGroup(netdiskPO.getFileShareToGroup());
            netdiskPOnew.setFileShareToOrg(netdiskPO.getFileShareToOrg());
            netdiskPOnew.setFileCreatedTime(netdiskPO.getFileCreatedTime());
            netdiskPOnew.setFileSize(netdiskPO.getFileSize());
            netdiskPOnew.setFileNote(netdiskPO.getFileNote());
            netdiskPOnew.setFileOwenAccount(userAccount);
            netdiskPOnew.setDomainId(domainId);
            this.session.save(netdiskPOnew);
          } 
        } 
      } else if (!"".equals(copyitem)) {
        String[] tmp = copyitem.split(",");
        for (int i = 0; i < tmp.length; i++) {
          NetDiskPO netdiskPO = new NetDiskPO();
          NetDiskPO netdiskPOnew = new NetDiskPO();
          netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
          netdiskPOnew.setFileName(netdiskPO.getFileName());
          netdiskPOnew.setFileSaveName(netdiskPO.getFileSaveName());
          netdiskPOnew.setFileSaveNameMin(netdiskPO.getFileSaveNameMin());
          netdiskPOnew.setFileExtName(netdiskPO.getFileExtName());
          netdiskPOnew.setFileType(netdiskPO.getFileType());
          netdiskPOnew.setFileOwn(userName);
          netdiskPOnew.setFileOwnId(new Long(userId));
          netdiskPOnew.setFileFatherId(new Long(currenid));
          netdiskPOnew.setFilePath(netdiskPO.getFilePath());
          netdiskPOnew.setFileIsShare(netdiskPO.getFileIsShare());
          netdiskPOnew.setFileShareToName(netdiskPO.getFileShareToName());
          netdiskPOnew.setFileShareToEmp(netdiskPO.getFileShareToEmp());
          netdiskPOnew.setFileShareToGroup(netdiskPO.getFileShareToGroup());
          netdiskPOnew.setFileShareToOrg(netdiskPO.getFileShareToOrg());
          netdiskPOnew.setFileCreatedTime(netdiskPO.getFileCreatedTime());
          netdiskPOnew.setFileSize(netdiskPO.getFileSize());
          netdiskPOnew.setFileNote(netdiskPO.getFileNote());
          netdiskPOnew.setFileOwenAccount(userAccount);
          netdiskPOnew.setDomainId(domainId);
          this.session.save(netdiskPOnew);
          this.session.delete("from com.js.oa.netdisk.po.NetDiskPO netdisk  where netdisk.fileId = " + tmp[i]);
        } 
      } 
      this.session.flush();
      result = true;
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
    return result;
  }
  
  public boolean shared(String copyitem, String sharetype, String informationReader, String informationReaderOrg, String informationReaderGroup, String informationReaderName, String shareds, String readTimeStart, String readTimeEnd, String writeTimeStart, String writeTimeEnd) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] tmp = copyitem.split(",");
      if ("1".equals(shareds))
        for (int i = 0; i < tmp.length; i++) {
          NetDiskPO netdiskPO = new NetDiskPO();
          netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
          if (sharetype.equals("1") || sharetype.equals("3")) {
            netdiskPO.setFileIsShare(new Long(sharetype.equals("3") ? sharetype : "0"));
            netdiskPO.setFileShareToEmp(informationReader);
            netdiskPO.setFileShareToOrg(informationReaderOrg);
            netdiskPO.setFileShareToGroup(informationReaderGroup);
            netdiskPO.setFileShareToName(informationReaderName);
            netdiskPO.setFileCreatedTime(new Date());
            if (!readTimeStart.equals(readTimeEnd)) {
              netdiskPO.setReadTimeFrom(DateHelper.string2Date(readTimeStart));
              netdiskPO.setReadTimeTo(DateHelper.string2Date(readTimeEnd));
            } 
            if (informationReaderName.equals("")) {
              netdiskPO.setReadTimeFrom(null);
              netdiskPO.setReadTimeTo(null);
            } 
          } 
          if (sharetype.equals("2") || sharetype.equals("4")) {
            netdiskPO.setFileIsShare(new Long(sharetype.equals("2") ? sharetype : "1"));
            netdiskPO.setFileShareToEmpWrite(informationReader);
            netdiskPO.setFileShareToOrgWrite(informationReaderOrg);
            netdiskPO.setFileShareToGroupWrite(informationReaderGroup);
            netdiskPO.setFileShareToNameWrite(informationReaderName);
            netdiskPO.setFileCreatedTime(new Date());
            if (!writeTimeEnd.equals(writeTimeStart)) {
              netdiskPO.setWriteTimeFrom(DateHelper.string2Date(writeTimeStart));
              netdiskPO.setWriteTimeTo(DateHelper.string2Date(writeTimeEnd));
            } 
            if (informationReaderName.equals("")) {
              netdiskPO.setWriteTimeFrom(null);
              netdiskPO.setWriteTimeTo(null);
            } 
          } 
        }  
      if ("0".equals(shareds))
        for (int i = 0; i < tmp.length; i++) {
          NetDiskPO netdiskPO = new NetDiskPO();
          netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
          netdiskPO.setFileIsShare(new Long(0L));
          netdiskPO.setFileShareToEmp("");
          netdiskPO.setFileShareToOrg("");
          netdiskPO.setFileShareToGroup("");
          netdiskPO.setFileShareToName("");
          netdiskPO.setFileCreatedTime(new Date());
          this.session.save(netdiskPO);
        }  
      this.session.flush();
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
    return result;
  }
  
  public boolean rename(String rename, String netdiskid) throws Exception {
    boolean result = false;
    begin();
    try {
      NetDiskPO netdiskPO = new NetDiskPO();
      netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(netdiskid));
      netdiskPO.setFileSaveName(rename);
      netdiskPO.setFileCreatedTime(new Date());
      this.session.flush();
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
    return result;
  }
  
  public boolean deleteall(String realpath, String userAccount, String copyitem) throws Exception {
    boolean result = false;
    String flag = "";
    String candelete = "0";
    String fileName = "";
    String fileext = "";
    String fileNameMin = "";
    String strsql = "";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] tmp = copyitem.split(",");
      for (int i = 0; i < tmp.length; i++) {
        NetDiskPO netdiskPO = new NetDiskPO();
        netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(tmp[i]));
        fileName = netdiskPO.getFileName();
        fileext = netdiskPO.getFileExtName();
        fileNameMin = netdiskPO.getFileSaveNameMin();
        strsql = "select count(*) as cou from oa_netdisk_file where file_name='" + fileName + "'";
        ResultSet rs = stmt.executeQuery(strsql);
        if (rs.next()) {
          flag = rs.getString("cou");
          if (Integer.parseInt(flag) > 1)
            candelete = "1"; 
        } 
        Query query = this.session.createQuery("select netdisk.fileId from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileIdString like '%$" + tmp[i] + "$%'");
        List<Long> listdelet = query.list();
        this.session.delete("from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileIdString like '%$" + tmp[i] + "$%'");
        this.session.delete("from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileId = " + tmp[i]);
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        if ("1".equals(isearchSwitch) && tmp[i] != null && ifActiveUpdateDelete != null && !"".equals(tmp[i]) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.deleteIndex(tmp[i], "oa_netdisk_file");
        } 
        if (listdelet != null && listdelet.size() != 0)
          for (int j = 0; j < listdelet.size(); j++) {
            Long id = listdelet.get(j);
            if (id != null && ifActiveUpdateDelete != null && !"".equals(id.toString()) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
              SearchService.getInstance();
              SearchService.deleteIndex(id.toString(), "oa_netdisk_file");
            } 
          }  
      } 
      this.session.flush();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String checksharetype(String netdiskid) throws Exception {
    String result = "";
    begin();
    try {
      Query query = this.session.createQuery("select aaa.fileIsShare from com.js.oa.netdisk.po.NetDiskPO aaa where aaa.fileFatherId=" + netdiskid);
      List<E> list = query.list();
      if (list.size() != 0)
        result = list.get(0).toString(); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String getInfoReader(String userId, String orgId, String orgIdString, String alias) throws Exception {
    StringBuffer infoReader = new StringBuffer();
    begin();
    try {
      infoReader.append("((" + alias + ".fileShareToEmp is not null and " + alias + ".fileShareToEmp<>'') and ");
      infoReader.append("(" + alias + ".fileShareToOrg is not null and " + alias + ".fileShareToOrg<>'') and ");
      infoReader.append("(" + alias + ".fileShareToGroup is not null or " + alias + ".fileShareToGroup<>'')) and ");
      infoReader.append("((" + alias + ".fileShareToEmpWrite is not null and " + alias + ".fileShareToEmpWrite<>'') and ");
      infoReader.append("(" + alias + ".fileShareToOrgWrite is not null and " + alias + ".fileShareToOrgWrite<>'') and ");
      infoReader.append("(" + alias + ".fileShareToGroupWrite is not null and " + alias + ".fileShareToGroupWrite<>''))");
      infoReader.append(" or " + alias + ".fileShareToEmp like '%$" + userId + "$%' ");
      infoReader.append(" or " + alias + ".fileShareToEmpWrite like '%$" + userId + "$%' ");
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
          orgIdString + "' like concat('%$', aaa.orgId, '$%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%'),'" + 
          orgIdString + "' )>0 ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
          orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%')";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<E> list = query.list();
      int i;
      for (i = 0; i < list.size(); i++) {
        infoReader.append(" or " + alias + ".fileShareToOrg like '%*" + list.get(i).toString() + "*%'");
        infoReader.append(" or " + alias + ".fileShareToOrgWrite like '%*" + list.get(i).toString() + "*%'");
      } 
      infoReader.append(" or " + alias + ".fileShareToOrg like '%*-1*%' ");
      query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa  join aaa.employees bbb where bbb.empId = " + 
          userId);
      list = query.list();
      for (i = 0; i < list.size(); i++) {
        infoReader.append(" or " + alias + ".fileShareToGroup like '%@" + list.get(i).toString() + "@%'");
        infoReader.append(" or " + alias + ".fileShareToGroupWrite like '%@" + list.get(i).toString() + "@%'");
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return infoReader.toString();
  }
  
  public String getupuse(String id) throws Exception {
    String result = "";
    begin();
    try {
      NetDiskPO netdiskPO = new NetDiskPO();
      netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(id));
      result = netdiskPO.getFileFatherId().toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean saveupload(List<List> listObj) throws Exception {
    boolean result = false;
    begin();
    try {
      for (int j = 0; j < listObj.size(); j++) {
        List<E> list = listObj.get(j);
        NetDiskPO netdiskPO = new NetDiskPO();
        netdiskPO.setFileName(list.get(0).toString());
        netdiskPO.setFileSaveName(list.get(1).toString());
        netdiskPO.setFileExtName(list.get(2).toString());
        netdiskPO.setFileOwn(list.get(3).toString());
        netdiskPO.setFileOwnId(new Long(list.get(4).toString()));
        netdiskPO.setFileFatherId(new Long(list.get(5).toString()));
        netdiskPO.setFileSize(new Long(list.get(6).toString()));
        netdiskPO.setFileOwenAccount(list.get(7).toString());
        netdiskPO.setFilePath("0");
        netdiskPO.setFileSaveNameMin("0");
        netdiskPO.setFileType(new Long(1L));
        netdiskPO.setFileIsShare(new Long(0L));
        netdiskPO.setFileShareToName("0");
        netdiskPO.setFileShareToOrg("0");
        netdiskPO.setFileShareToGroup("0");
        netdiskPO.setFileShareToEmp("0");
        netdiskPO.setFileCreatedTime(new Date());
        netdiskPO.setFileNote("0");
        if ("0".equals(list.get(5).toString()))
          netdiskPO.setFileIdString("0"); 
        if (!"0".equals(list.get(5).toString()) && "0".equals(list.get(8).toString()))
          netdiskPO.setFileIdString("$" + list.get(5).toString() + "$"); 
        if (!"0".equals(list.get(5).toString()) && !"0".equals(list.get(8).toString()))
          netdiskPO.setFileIdString(String.valueOf(list.get(8).toString()) + "$" + list.get(5).toString() + "$"); 
        netdiskPO.setDomainId(list.get(9).toString());
        Long diskId = (Long)this.session.save(netdiskPO);
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        if ("1".equals(isearchSwitch) && diskId != null && ifActiveUpdateDelete != null && !"".equals(diskId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.addIndex(diskId.toString(), "oa_netdisk_file");
        } 
        this.session.flush();
        result = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean unshared(String id) throws Exception {
    boolean result = false;
    begin();
    try {
      NetDiskPO netdiskPO = new NetDiskPO();
      netdiskPO = (NetDiskPO)this.session.load(NetDiskPO.class, new Long(id));
      netdiskPO.setFileIsShare(new Long(0L));
      netdiskPO.setFileShareToEmp("");
      netdiskPO.setFileShareToOrg("");
      netdiskPO.setFileShareToGroup("");
      netdiskPO.setFileShareToName("");
      netdiskPO.setFileShareToEmpWrite("");
      netdiskPO.setFileShareToOrgWrite("");
      netdiskPO.setFileShareToGroupWrite("");
      netdiskPO.setFileShareToNameWrite("");
      netdiskPO.setFileCreatedTime(new Date());
      netdiskPO.setReadTimeFrom(null);
      netdiskPO.setReadTimeTo(null);
      netdiskPO.setWriteTimeFrom(null);
      netdiskPO.setWriteTimeTo(null);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getinfodetail(String id) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select netdisk from com.js.oa.netdisk.po.NetDiskPO netdisk where netdisk.fileId=" + id);
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getWriteAccess(String empId, String groupId, String orgId, String orgIdString) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      String nowStr = "now()";
      if (databaseType.indexOf("oracle") >= 0 || databaseType.indexOf("sqlserver") >= 0)
        nowStr = "sysdate"; 
      String sql = "select g.groupId from GroupVO g where g.groupUserString like '%$" + empId + "$%'";
      List<Long> tempList = this.session.createQuery(sql).list();
      String tmpSql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
          orgIdString + "' like concat('%$', aaa.orgId, '$%')";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where '" + 
          orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%')";
      } 
      Query query1 = this.session.createQuery(tmpSql);
      StringBuffer hql = new StringBuffer("from NetDiskPO p where p.fileId=" + groupId);
      hql.append(" and (p.fileShareToEmpWrite like '%$" + empId + "$%'");
      if (tempList != null && tempList.size() > 0)
        for (int j = 0; j < tempList.size(); j++) {
          Long obj = tempList.get(j);
          hql.append(" or p.fileShareToGroupWrite like '%@" + obj + "@%' ");
        }  
      List<E> list = query1.list();
      for (int i = 0; i < list.size(); i++)
        hql.append(" or p.fileShareToOrgWrite like '%*" + list.get(i).toString() + "*%' "); 
      hql.append(" or p.fileShareToOrgWrite like '%*-1*%') ");
      hql.append(" and ((p.writeTimeFrom is null and p.writeTimeTo is null) or (" + nowStr + " between p.writeTimeFrom and p.writeTimeTo))");
      Query query = this.session.createQuery(hql.toString());
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getReadAccess(String empId, String groupId, String orgId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      String sql = "select g.groupId from GroupVO g where g.groupUserString like '$" + empId + "$'";
      List<Long> tempList = this.session.createQuery(sql).list();
      StringBuffer hql = new StringBuffer("from NetDiskPO p where p.fileShareToEmpWrite like '%$" + empId + "$%'");
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Long obj = tempList.get(i);
          hql.append(" or p.fileShareToGroupWrite like '@" + obj + "@'");
        }  
      hql.append(" or p.fileShareToOrgWrite like '*" + orgId + "*'");
      Query query = this.session.createQuery(hql.toString());
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean checkFolderName(String name, String id, String idString, String type, String userId) throws Exception {
    boolean result = false;
    begin();
    try {
      StringBuffer sql = new StringBuffer("from NetDiskPO g where g.fileSaveName='" + name + "'");
      if (type.equals("add")) {
        if (id.equals("null")) {
          sql.append(" and g.fileIdString='0'");
        } else if (!id.equals("") && !id.equals("null") && idString.equals("0")) {
          sql.append(" and g.fileIdString like '%$" + id + "$%'");
        } else {
          if (idString.length() <= 0)
            idString = "$0$"; 
          idString = idString.substring(1, idString.length() - 1).replaceAll("\\$", ",").replaceAll(",,", ",");
          String[] ids = idString.split(",");
          if (ids != null && ids.length > 0)
            for (int i = 0; i < ids.length; i++)
              sql.append(" and g.fileIdString like '%$" + ids[i] + "$%'");  
          sql.append(" and g.fileIdString like '%$" + id + "$%'");
        } 
      } else if (idString.indexOf("$") < 0) {
        sql.append(" and g.fileIdString='0'");
      } else {
        idString = idString.substring(1, idString.length() - 1).replaceAll("\\$", ",").replaceAll(",,", ",");
        String[] ids = idString.split(",");
        if (ids != null && ids.length > 0)
          for (int i = 0; i < ids.length; i++)
            sql.append(" and g.fileIdString like '%$" + ids[i] + "$%'");  
      } 
      sql.append(" and g.fileOwnId=" + userId);
      List tempList = this.session.createQuery(sql.toString()).list();
      if (tempList != null && tempList.size() > 0)
        result = true; 
      this.session.flush();
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
    return result;
  }
}
