package com.js.oa.message.service;

import com.js.oa.message.bean.MessageEJBHome;
import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.message.po.MsManagePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class MsManageBD {
  private static Logger logger = Logger.getLogger(MsManageBD.class.getName());
  
  public List getFlowAndList(String userId, String rightCode) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsManageEJB", "MsManageEJBLocal", MessageEJBHome.class);
      p.put(userId, String.class);
      p.put(rightCode, String.class);
      listInfo = (List)ejbProxy.invoke("getMsManageList", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public MsManagePO loadMs(String msId) throws Exception {
    MsManagePO po = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsManageEJB", "MsManageEJBLocal", MessageEJBHome.class);
      p.put(msId, String.class);
      po = (MsManagePO)ejbProxy.invoke("loadMs", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public List getMsManageInfoByMsId(String msId) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsManageEJB", "MsManageEJBLocal", MessageEJBHome.class);
      p.put(msId, String.class);
      listInfo = (List)ejbProxy.invoke("getMsManageInfoByMsId", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListByYourSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public String getListOwner(String tableNameString) throws Exception {
    String infoString = "";
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      infoString = bean.getListOwner(tableNameString);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return infoString;
  }
  
  public List getListType(String tableNameString) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListType(tableNameString);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public String getListUsers(String tableNameString, String tableCode) throws Exception {
    String listInfo = null;
    ParameterGenerator p = new ParameterGenerator(2);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListUsers(tableNameString, tableCode).replace("null", ",");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo.replace("null", ",");
  }
  
  public String getListTtable(String tableNameString) throws Exception {
    String listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListTtable(tableNameString);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
}
