package com.js.oa.netdisk.service;

import com.js.oa.netdisk.bean.NetdiskEJBBean;
import com.js.oa.netdisk.bean.NetdiskEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;

public class NetdiskBD {
  public boolean addfolder(String userId, String userName, String filename, String currenid, String userAccount, String fileidstring, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(filename, String.class);
      pg.put(currenid, String.class);
      pg.put(userAccount, String.class);
      pg.put(fileidstring, String.class);
      pg.put(domainId, String.class);
      result = ((Boolean)ejbProxy.invoke("addfolder", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("add addfolder Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deletemydisk(String fileid, String userAccount, String realpath) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(fileid, String.class);
      pg.put(userAccount, String.class);
      pg.put(realpath, String.class);
      result = ((Boolean)ejbProxy.invoke("deletemydisk", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("add deletemydisk Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean copyorcut(String currenid, String copyitem, String copyorcut, String userAccount, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(currenid, String.class);
      pg.put(copyitem, String.class);
      pg.put(copyorcut, String.class);
      pg.put(userAccount, String.class);
      pg.put(domainId, String.class);
      result = ((Boolean)ejbProxy.invoke("copyorcut", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add copyorcut Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean copyorcutshare(String currenid, String copyitem, String copyorcut, String userId, String userName, String userAccount, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", 
          "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(currenid, String.class);
      pg.put(copyitem, String.class);
      pg.put(copyorcut, String.class);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(userAccount, String.class);
      pg.put(domainId, String.class);
      result = ((Boolean)ejbProxy.invoke("copyorcutshare", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add copyorcut Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean shared(String copyitem, String sharetype, String informationReader, String informationReaderOrg, String informationReaderGroup, String informationReaderName, String shareds, String readTimeStart, String readTimeEnd, String writeTimeStart, String writeTimeEnd) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(11);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(copyitem, String.class);
      pg.put(sharetype, String.class);
      pg.put(informationReader, String.class);
      pg.put(informationReaderOrg, String.class);
      pg.put(informationReaderGroup, String.class);
      pg.put(informationReaderName, String.class);
      pg.put(shareds, String.class);
      pg.put(readTimeStart, String.class);
      pg.put(readTimeEnd, String.class);
      pg.put(writeTimeStart, String.class);
      pg.put(writeTimeEnd, String.class);
      result = ((Boolean)ejbProxy.invoke("shared", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("add copyorcut Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean rename(String rename, String netdiskid) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(rename, String.class);
      pg.put(netdiskid, String.class);
      result = ((Boolean)ejbProxy.invoke("rename", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add rename Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteall(String realpath, String userAccount, String copyitem) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(realpath, String.class);
      pg.put(userAccount, String.class);
      pg.put(copyitem, String.class);
      result = ((Boolean)ejbProxy.invoke("deleteall", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add rename Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String checksharetype(String netdiskid) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(netdiskid, String.class);
      result = (String)ejbProxy.invoke("checksharetype", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("add rename Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getInfoReader(String userId, String orgId, String orgIdString, String alias) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(alias, String.class);
      result = (String)ejbProxy.invoke("getInfoReader", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("add rename Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getupuse(String id) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", 
          "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(id, String.class);
      result = (String)ejbProxy.invoke("getupuse", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("add rename Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean saveupload(List list) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(list, List.class);
      ejbProxy.invoke("saveupload", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("add saveupload Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean unshared(String id) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(id, String.class);
      ejbProxy.invoke("unshared", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("add unshared Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getinfodetail(String id) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(id, String.class);
      result = (List)ejbProxy.invoke("getinfodetail", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("add getinfodetail Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getWriteAccess(String empId, String groupId, String orgId, String orgIdString) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(empId, String.class);
      pg.put(groupId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      result = (List)ejbProxy.invoke("getWriteAccess", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("add getinfodetail Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getReadAccess(String empId, String groupId, String orgId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(empId, String.class);
      pg.put(groupId, String.class);
      pg.put(orgId, String.class);
      result = (List)ejbProxy.invoke("getReadAccess", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("add getinfodetail Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean checkFolderName(String name, String id, String idString, String type, String userId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("NetdiskEJB", "NetdiskEJBLocal", NetdiskEJBHome.class);
      pg.put(name, String.class);
      pg.put(id, String.class);
      pg.put(idString, String.class);
      pg.put(type, String.class);
      pg.put(userId, String.class);
      result = ((Boolean)ejbProxy.invoke("checkFolderName", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("add rename Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getCanReadUserIds(String userIdString, String orgIdString, String groupIdString) throws Exception {
    NetdiskEJBBean nb = new NetdiskEJBBean();
    return nb.getCanReadUserIdsImpl(userIdString, orgIdString, groupIdString);
  }
}
