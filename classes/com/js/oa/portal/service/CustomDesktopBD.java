package com.js.oa.portal.service;

import com.js.oa.portal.bean.CustomDesktopEJBBean;
import com.js.oa.portal.bean.CustomDesktopEJBHome;
import com.js.oa.portal.po.CustomDefaultPO;
import com.js.oa.portal.po.CustomDesktopLayoutPO;
import com.js.oa.portal.po.CustomurlPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class CustomDesktopBD {
  private CustomDesktopEJBBean bean = new CustomDesktopEJBBean();
  
  private static Logger logger = Logger.getLogger(CustomDesktopBD.class
      .getName());
  
  public boolean delLayout(String id) {
    boolean bl = false;
    try {
      bl = this.bean.delLayout(id);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return bl;
  }
  
  public boolean audit(CustomDesktopLayoutPO po, String opreate, Long id, HttpServletRequest httpServletRequest) {
    boolean result = false;
    try {
      result = this.bean.audit(po, opreate, id, httpServletRequest);
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean autoAudit(CustomDesktopLayoutPO po, String opreate, Long id, HttpServletRequest httpServletRequest) {
    boolean result = false;
    try {
      result = this.bean.autoAudit(po, opreate, id, httpServletRequest);
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public CustomDesktopLayoutPO getLayoutById(String id) {
    CustomDesktopLayoutPO vpo = new CustomDesktopLayoutPO();
    try {
      vpo = this.bean.getLayoutById(id);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return vpo;
  }
  
  public Integer saveLayout(CustomDesktopLayoutPO customDesktopLayoutPO, String path, String domainId) {
    Integer result = new Integer(1);
    try {
      result = this.bean.saveLayout(customDesktopLayoutPO, path, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String saveLayoutForPerson(CustomDesktopLayoutPO customDesktopLayoutPO, String path, String domainId, String userId) {
    String xmlname = "";
    try {
      xmlname = this.bean.saveLayoutForPerson(customDesktopLayoutPO, path, domainId, userId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return xmlname;
  }
  
  public String updateLayoutForPerson(CustomDesktopLayoutPO customDesktopLayoutPO, String path, String domainId, String userId, String porPerId) {
    String xmlname = "";
    try {
      xmlname = this.bean.updateLayoutForPerson(customDesktopLayoutPO, path, domainId, userId, porPerId);
    } catch (Exception ex) {
      logger.error("error to updateLayoutForPerson information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return xmlname;
  }
  
  public Integer updateLayout(CustomDesktopLayoutPO customDesktopLayoutPO, String layoutId, String domainId) {
    Integer result = new Integer(1);
    try {
      result = this.bean.updateLayout(customDesktopLayoutPO, layoutId, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return result;
  }
  
  public List listInformation(String channelId, String userId, String orgId, String orgIdString, String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listInformation(channelId, userId, orgId, orgIdString, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listInformationClass(String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listInformationClass(domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listInformationClassByCorp(String domainId, String userId, String orgId, String corpId) {
    List list = new ArrayList();
    try {
      list = this.bean.listInformationClassByCorp(domainId, userId, orgId, corpId);
    } catch (Exception ex) {
      logger.error("error to listInformationClassByCorp information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listIframeUrl(String domainId, String userId) {
    List list = new ArrayList();
    try {
      list = this.bean.listIframeUrl(domainId, userId);
    } catch (Exception ex) {
      logger.error("error to listIframeUrl information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listInformationDeptClass(String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listInformationDeptClass(domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listLinkSystemClass(String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listLinkSystemClass(domainId);
    } catch (Exception ex) {
      logger.error("error to listLinkSystemClass information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listLinkSystem(String where) {
    List list = new ArrayList();
    try {
      list = this.bean.listLinkSystem(where);
    } catch (Exception ex) {
      logger.error("error to listLinkSystem information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String[] getLinkSystemUserPass(String systemId, String userId) {
    String[] res = (String[])null;
    try {
      res = this.bean.getLinkSystemUserPass(systemId, userId);
    } catch (Exception ex) {
      logger.error("error to getLinkSystemUserPass information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return res;
  }
  
  public List listNotePaper(String userId, String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listNotePaper(userId, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String getPersonalDesktop(String userId, String orgIdString, String domainId) {
    String ret = "";
    try {
      ret = this.bean.getPersonalDesktop(userId, orgIdString, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ret;
  }
  
  public List getPersonalDesktopList(String userId, String orgIdString, String domainId) {
    List ret = null;
    try {
      ret = this.bean.getPersonalDesktopList(userId, orgIdString, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ret;
  }
  
  public Map listLastUpdate(String userId, String orgId, String orgIdString, String layoutId, String domainId, String corpId) {
    Map deskInfo = null;
    try {
      deskInfo = this.bean.listLastUpdate(userId, orgId, orgIdString, layoutId, domainId, corpId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return deskInfo;
  }
  
  public Map listMyMail(String userId, String domainId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map = this.bean.listMyMail(userId, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return map;
  }
  
  public Map listMyForum(String scopeWhere) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map = this.bean.listMyForum(scopeWhere);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return map;
  }
  
  public Map listFileDeal(String userId, String domainId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map = this.bean.listFileDeal(userId, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return map;
  }
  
  public Map listSurvey(String orgIdString, String domainId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map = this.bean.listSurvey(orgIdString, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return map;
  }
  
  public void setChannelOnDesktop(String channelId, String ondesktop) {
    try {
      this.bean.setChannelOnDesktop(channelId, ondesktop);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public Object[] getInformationChannelByChannelId(String channelId) {
    Object[] obj = (Object[])null;
    try {
      obj = this.bean.getInformationChannelByChannelId(channelId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return obj;
  }
  
  public void setChannelOnDesktopSToLayout(String layoutId, String channelIdStr) {
    try {
      this.bean.setChannelOnDesktopSToLayout(layoutId, channelIdStr);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public List listAllOrgs(String domainId) {
    List list = new ArrayList();
    try {
      this.bean.listAllOrgs(domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public Object[] getOrgByOrgId(String orgId) {
    Object[] obj = (Object[])null;
    try {
      obj = this.bean.getOrgByOrgId(orgId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return obj;
  }
  
  public List listForumClass(String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listForumClass(domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listProClass(String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.listProClass(domainId);
    } catch (Exception e) {
      logger.error("error to Layout relProject :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List listFileDealDT(String userId, String domainId, String workStatus, int num) {
    List list = new ArrayList();
    try {
      list = this.bean.listFileDealDT(userId, domainId, workStatus, Integer.valueOf(num));
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listFileDealList(String userId, String domainId, String workStatus, String category, int num) {
    List list = new ArrayList();
    try {
      listFileDealList(userId, domainId, workStatus, category, null, num);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listFileDealList(String userId, String domainId, String workStatus, String category, String includeCoop, int num) {
    List list = new ArrayList();
    try {
      list = this.bean.listFileDealList(userId, domainId, workStatus, category, includeCoop, Integer.valueOf(num));
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List getSendFileUrlParameters(String workId) {
    List list = new ArrayList();
    try {
      list = this.bean.getSendFileUrlParameters(workId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listMatterCategoryList() {
    List list = new ArrayList();
    try {
      list = this.bean.listMatterCategoryList();
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List myReceiveFile(String userId, String domainId, int num) {
    List list = new ArrayList();
    try {
      list = this.bean.myReceiveFile(userId, domainId, Integer.valueOf(num));
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List getMyJoinTask(String userId, String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.getMyJoinTask(userId, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List getMyPrincipalTask(String userId, String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.getMyPrincipalTask(userId, domainId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String getOaUserAccount(String userIdString) {
    String ret = "";
    try {
      ret = this.bean.getOaUserAccount(userIdString);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ret;
  }
  
  public String getOaUserAccountByOrg(String orgIdString) {
    String ret = "";
    try {
      ret = this.bean.getOaUserAccountByOrg(orgIdString);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ret;
  }
  
  public Map getRelationInfo(String userId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map = this.bean.getRelationInfo(userId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return map;
  }
  
  public Map getRelationInfoByOrgId(String orgId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map = this.bean.getRelationInfoByOrgId(orgId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return map;
  }
  
  public List getRelationObject(String moduleType, String moduleSubId) {
    List list = new ArrayList();
    try {
      list = this.bean.getRelationObject(moduleType, moduleSubId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public boolean saveRelationModule(String moduleType, String moduleSubId, String[] objectId, String domainId) {
    boolean bl = false;
    try {
      bl = this.bean.saveRelationModule(moduleType, moduleSubId, objectId, domainId).booleanValue();
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return bl;
  }
  
  public List getDefinedRelationObject(String moduleType, String moduleSubId) {
    List list = new ArrayList();
    try {
      list = this.bean.getDefinedRelationObject(moduleType, moduleSubId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List getRelationInfo(String moduleType, String moduleSubId, String infoId) {
    List list = new ArrayList();
    try {
      list = this.bean.getRelationInfo(moduleType, moduleSubId, infoId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public boolean saveRelationInfo(String moduleType, String moduleSubId, String infoId, String infoRelationObjectType, String[] infoRelationId, String[] infoRelationName, String[] infoRelationHref, String domainId) {
    boolean bl = false;
    try {
      bl = this.bean.saveRelationInfo(moduleType, moduleSubId, infoId, infoRelationObjectType, infoRelationId, infoRelationName, infoRelationHref, domainId).booleanValue();
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return bl;
  }
  
  public boolean delRelationInfo(String moduleType, String moduleSubId, String infoId, String domainId) {
    boolean bl = false;
    try {
      bl = this.bean.delRelationInfo(moduleType, moduleSubId, infoId, domainId).booleanValue();
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return bl;
  }
  
  public boolean delRelationModule(String moduleType, String moduleSubId, String domainId) {
    boolean bl = false;
    try {
      bl = this.bean.delRelationModule(moduleType, moduleSubId, domainId).booleanValue();
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return bl;
  }
  
  public String[] getWorkFlowInfoByRelation(String relationObejctType, String relationInfoId) {
    String[] ret = { "0", "0" };
    try {
      ret = this.bean.getWorkFlowInfoByRelation(relationObejctType, relationInfoId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ret;
  }
  
  public String loadNetAddressClass(String classId) {
    String ret = "";
    try {
      ret = this.bean.loadNetAddressClass(classId);
    } catch (Exception ex) {
      logger.error("error to loadNetAddressClass information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ret;
  }
  
  public void showNO(String id) {
    try {
      this.bean.showNo(id);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public List getGroupById(String userId) throws Exception {
    List list = null;
    CustomDesktopEJBBean customDesktopEJBBean = new CustomDesktopEJBBean();
    list = customDesktopEJBBean.getGroupById(userId);
    return list;
  }
  
  public void updateLayoutEmp(String layoutId, String empId) {
    try {
      (new CustomDesktopEJBBean()).updateLayoutEmp(layoutId, empId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getLayoutEmp(String layoutId) {
    try {
      return (new CustomDesktopEJBBean()).getLayoutEmp(layoutId);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public Boolean saveCustomDeskUrl(CustomurlPO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, CustomurlPO.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      success = (Boolean)ejbProxy.invoke("saveCustomDeskUrl", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD saveCustomDeskUrl information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean saveCustomDefaultPortal(CustomDefaultPO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, CustomDefaultPO.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      success = (Boolean)ejbProxy.invoke("saveCustomDefaultPortal", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD saveCustomDefaultPortal information :" + e.getMessage());
    } 
    return success;
  }
  
  public CustomurlPO loadCustomDeskUrl(Long id) {
    CustomurlPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      po = (CustomurlPO)ejbProxy.invoke("loadCustomDeskUrl", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD loadCustomDeskUrl information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public CustomDefaultPO loadCustomDefault(Long id) {
    CustomDefaultPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      po = (CustomDefaultPO)ejbProxy.invoke("loadCustomDefault", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD loadCustomDefault information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean updateCustomDeskUrl(CustomurlPO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, CustomurlPO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("updateCustomDeskUrl", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD updateCustomDeskUrl information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean updateCustomDefaultPortal(CustomDefaultPO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, CustomDefaultPO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("updateCustomDefaultPortal", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD updateCustomDefaultPortal information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean deleteCustomDeskUrl(Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("deleteCustomDeskUrl", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD deleteCustomDeskUrl information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean deleteCustomDefault(Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDesktopEJB", 
          "CustomDesktopEJBLocal", CustomDesktopEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("deleteCustomDefault", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  CustomDesktopBD deleteCustomDefault information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String getSendDate(String processIds, String recordId) {
    String ids = "0";
    try {
      ids = this.bean.getSendDate(processIds, recordId);
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ids;
  }
  
  public String getAllSendFileProcessIds() {
    String ids = "0";
    try {
      ids = this.bean.getAllSendFileProcessIds();
    } catch (Exception ex) {
      logger.error("error to delLayout information :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return ids;
  }
}
