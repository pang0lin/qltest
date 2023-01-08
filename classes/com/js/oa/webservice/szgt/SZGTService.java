package com.js.oa.webservice.szgt;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class SZGTService {
  private final String SERVICE_URL = "http://10.10.16.4:8090/uapws/service/isynchrdata";
  
  private final String NAMESPACE = "http://pub.szgt.itf.nc/ISyncHRData";
  
  private static Map personalKindMap = new HashMap<Object, Object>();
  
  private String invokeWebService(String url, String method, String nameSpace, Object[] paras) {
    String result = "";
    try {
      Service service = new Service();
      Call call = (Call)service.createCall();
      call.setTargetEndpointAddress(url);
      call.setOperationName(new QName(nameSpace, method));
      call.addParameter("string", XMLType.XSD_STRING, ParameterMode.IN);
      call.setReturnType(XMLType.XSD_STRING);
      result = call.invoke(paras).toString().replace("\n", "").replace("\r", "");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getDatas() {
    UserOperate uo = new UserOperate();
    OrgOperate oo = new OrgOperate();
    String lastUpdate = uo.getMinLastUpdate();
    String nowTime = "";
    if ("0".equals(lastUpdate)) {
      nowTime = "2008-01-01 00:00:00";
    } else {
      nowTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(Long.parseLong(lastUpdate)));
    } 
    IO2File.printFile("上次同步时间：" + lastUpdate + "，本次同步开始时间：" + nowTime, "组织信息操作");
    String method = "updateOrg";
    String result = invokeWebService("http://10.10.16.4:8090/uapws/service/isynchrdata", method, "http://pub.szgt.itf.nc/ISyncHRData", new Object[] { nowTime });
    IO2File.printFile("获取组织数据：" + result, "组织信息操作");
    List<OrgPO> orgs = getOrgs(result, 0);
    IO2File.printFile("本次同步组织数量：" + orgs.size(), "组织信息操作");
    result = oo.saveOrg(orgs);
    if ("success".equals(result)) {
      method = "updateDept";
      IO2File.printFile("入参：" + nowTime, "组织信息操作");
      result = invokeWebService("http://10.10.16.4:8090/uapws/service/isynchrdata", method, "http://pub.szgt.itf.nc/ISyncHRData", new Object[] { nowTime });
      IO2File.printFile("获取部门数据：" + result, "组织信息操作");
      List<OrgPO> depts = getOrgs(result, 1);
      IO2File.printFile("本次同步部门数量：" + depts.size(), "组织信息操作");
      result = oo.saveOrg(depts);
      if ("success".equals(result)) {
        method = "updatePsndoc";
        IO2File.printFile("入参：" + nowTime, "用户信息操作");
        result = invokeWebService("http://10.10.16.4:8090/uapws/service/isynchrdata", method, "http://pub.szgt.itf.nc/ISyncHRData", new Object[] { nowTime });
        IO2File.printFile("获取用户数据：" + result, "用户信息操作");
        List<UserPO> users = getUsers(result);
        IO2File.printFile("本次同步用户数量：" + users.size(), "用户信息操作");
        long timeStamp = System.currentTimeMillis();
        result = uo.saveUser(users, timeStamp);
        if ("success".equals(result)) {
          result = oo.saveOrg(orgs);
          result = oo.saveOrg(depts);
        } else {
          IO2File.printFile("用户信息同步：" + result, "用户信息操作");
        } 
      } else {
        IO2File.printFile("部门信息同步：" + result, "组织信息操作");
      } 
    } else {
      IO2File.printFile("组织信息同步：" + result, "组织信息操作");
    } 
    return result;
  }
  
  private List<OrgPO> getOrgs(String xml, int type) {
    List<OrgPO> orgs = new ArrayList<OrgPO>();
    OrgPO org = null;
    StringReader read = new StringReader(xml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      List<Element> nodes = root.getChildren();
      if (nodes != null && nodes.size() > 0)
        for (Element e : nodes) {
          org = new OrgPO();
          org.setOperateType(e.getChild("type").getText());
          String pkOrg = e.getChild("pk_org").getText();
          if (type == 0) {
            org.setOrgId(pkOrg);
          } else if (1 == type) {
            org.setOrgId(e.getChild("pk_dept").getText());
          } 
          if (!"del".equals(org.getOperateType())) {
            org.setOrgCode(org.getOrgId());
            org.setOrgName(e.getChild("name").getText());
            org.setOrgParentId(e.getChild("pk_fatherorg").getText());
            org.setManager(e.getChild("principal").getText());
            if (1 == type) {
              org.setCancle(e.getChild("hrcanceled").getText());
              org.setOrgType(e.getChild("depttype").getText());
              if ("y".equalsIgnoreCase(org.getOrgType()))
                org.setOrgName(String.valueOf(org.getOrgName()) + "(虚拟)"); 
            } 
            if (org.getOrgParentId() == null || "".equals(org.getOrgParentId()))
              if (1 == type) {
                org.setOrgParentId(pkOrg);
              } else {
                continue;
              }  
          } 
          orgs.add(org);
        }  
    } catch (Exception e) {
      IO2File.printFile("组织信息解析错误：" + e.getStackTrace(), "组织信息操作");
      e.printStackTrace();
    } 
    return orgs;
  }
  
  private List<UserPO> getUsers(String xml) {
    List<UserPO> users = new ArrayList<UserPO>();
    UserPO user = null;
    StringReader read = new StringReader(xml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    String nowDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      List<Element> nodes = root.getChildren();
      if (nodes != null && nodes.size() > 0)
        for (Element e : nodes) {
          user = new UserPO();
          user.setOperateType(e.getChild("type").getText());
          user.setUserId(e.getChild("pk_psndoc").getText());
          if (!"del".equals(user.getOperateType())) {
            user.setUserName(e.getChild("psnname").getText());
            user.setUserNumber(e.getChild("clerkcode").getText());
            user.setUserAccount(e.getChild("user_code").getText());
            user.setOrgId(e.getChild("pk_dept").getText());
            user.setUserDuty(e.getChild("jobname").getText());
            user.setUserStation(e.getChild("postname").getText());
            user.setUserSex(e.getChild("sex").getText());
            if ("女".equals(user.getUserSex())) {
              user.setUserSex("1");
            } else {
              user.setUserSex("0");
            } 
            user.setUserEmail(e.getChild("email").getText());
            user.setIntoCompanyDate(e.getChild("begindate").getText());
            user.setRxzsj(e.getChild("joinsysdate").getText());
            user.setPersonalKind(getOAPersonalKind(e.getChild("psnclname").getText()));
            user.setEnablestate(e.getChild("enablestate").getText());
            user.setTrnsevent(e.getChild("trnsevent").getText());
            user.setPkOrg(e.getChild("pk_org").getText());
            user.setPkHrorg(e.getChild("pk_hrorg").getText());
            user.setJobStatus(e.getChild("trial").getText());
            if ("Y".equalsIgnoreCase(user.getJobStatus())) {
              user.setJobStatus("试用");
            } else {
              user.setJobStatus("正式");
            } 
            user.setEmpFireDate(e.getChild("joinworkdate").getText());
            if ("".equals(user.getUserAccount()))
              user.setUserAccount(user.getUserName()); 
            if ("".equals(user.getIntoCompanyDate()))
              user.setIntoCompanyDate(nowDate); 
            if ("".equals(user.getRxzsj()))
              user.setRxzsj(nowDate); 
            if ("".equals(user.getEmpFireDate()))
              user.setEmpFireDate(nowDate); 
            if (user.getEnablestate() == null)
              user.setEnablestate(""); 
            if (user.getTrnsevent() == null)
              user.setTrnsevent(""); 
            user.setGlbdef15(e.getChild("glbdef15").getText());
          } 
          users.add(user);
        }  
    } catch (Exception e) {
      IO2File.printFile("用户信息解析错误：" + e.getStackTrace(), "用户信息操作");
      e.printStackTrace();
    } 
    return users;
  }
  
  private String getOAPersonalKind(String hrKind) {
    if (hrKind == null || "null".equals(hrKind))
      return "0"; 
    String oaKind = null;
    if (personalKindMap.get(hrKind) != null) {
      oaKind = personalKindMap.get(hrKind).toString();
    } else {
      Connection conn = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        String sql = "select kind_id from OA_PERSONAL_KIND where kind_name='" + hrKind + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
          oaKind = rs.getString(1);
          rs.close();
        } else {
          sql = "insert into OA_PERSONAL_KIND(kind_id,kind_name,kind_description,kind_sort) values((select (max(kind_id)+1) from OA_PERSONAL_KIND),'" + hrKind + "','',(select (max(kind_sort)+1) from OA_PERSONAL_KIND))";
          stmt.executeUpdate(sql);
          sql = "select kind_id from OA_PERSONAL_KIND where kind_name='" + hrKind + "'";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            oaKind = rs.getString(1); 
          rs.close();
        } 
        personalKindMap.put(hrKind, oaKind);
        stmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
    return oaKind;
  }
}
