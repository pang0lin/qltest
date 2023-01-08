package com.js.oa.weixin.tongbu;

import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.manage.WeixinManageAction;
import com.js.oa.weixin.pojo.WeiXinORG;
import com.js.oa.weixin.pojo.WeiXinUser;
import com.qq.weixin.mp.util.WeixinUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinTongBuAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    try {
      weixinTongBu();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      PrintWriter out = res.getWriter();
      out.println("success");
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  private static String CREATE_ORG_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
  
  private static String UPDATE_ORG_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
  
  private static String DELETE_ORG_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ORGID";
  
  private static String LIST_ORG_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
  
  private static String CREATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
  
  private static String UPDATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
  
  private static String DELETE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
  
  private static String GET_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
  
  private static String GET_ORG_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=1&fetch_child=fetch_child_flag&status=0";
  
  public static final String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
  
  public void weixinTongBu() throws Exception {
    System.out.println("开始为新数据同步………………");
    String token = "";
    String sCorpID = WeixinManageAction.getPropValue("sCorpID");
    String c_Secret = WeixinManageAction.getPropValue("C_Secret");
    String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET".replace("CORPID", sCorpID).replace("CORPSECRET", c_Secret);
    JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
    if (jsonObject != null)
      try {
        token = jsonObject.getString("access_token");
      } catch (JSONException e) {
        e.printStackTrace();
        token = "";
      }  
    if ("".equals(token)) {
      System.out.println("获取操作Token失败！");
      return;
    } 
    String listOrgUrl = LIST_ORG_URL.replace("ACCESS_TOKEN", token);
    jsonObject = WeixinUtil.httpRequest(listOrgUrl, "GET", null);
    List<WeiXinORG> weixinOrgs = new ArrayList<WeiXinORG>();
    if (jsonObject != null) {
      try {
        int errcode = jsonObject.getInt("errcode");
        if (errcode != 0)
          throw new JSONException("获取所有部门错误，错误信息：" + jsonObject.getString("errmsg")); 
        JSONArray orgs = jsonObject.getJSONArray("department");
        System.out.println("微信中总共有" + orgs.size() + "个组织");
        for (int i = 0; i < orgs.size(); i++) {
          System.out.println("orgstring:" + ((JSONObject)orgs.get(i)).toString());
          WeiXinORG org = new WeiXinORG();
          org.setId(((JSONObject)orgs.get(i)).getInt("id"));
          org.setName(((JSONObject)orgs.get(i)).getString("name"));
          org.setParentid((new StringBuilder(String.valueOf(((JSONObject)orgs.get(i)).getInt("parentid")))).toString());
          org.setOrder(((JSONObject)orgs.get(i)).getString("order"));
          weixinOrgs.add(org);
        } 
      } catch (JSONException e) {
        e.printStackTrace();
        return;
      } 
    } else {
      return;
    } 
    addOrUpdateAllOrgToWeiXin(weixinOrgs, token);
    String listUserUrl = GET_ORG_USER_URL.replace("ACCESS_TOKEN", token).replace("fetch_child_flag", "1");
    jsonObject = WeixinUtil.httpRequest(listUserUrl, "GET", null);
    List<WeiXinUser> weixinUsers = new ArrayList<WeiXinUser>();
    if (jsonObject != null) {
      try {
        int errcode = jsonObject.getInt("errcode");
        if (errcode != 0)
          throw new JSONException("获取所有用户错误，错误信息：" + jsonObject.getString("errmsg")); 
        JSONArray users = jsonObject.getJSONArray("userlist");
        System.out.println("微信中总共有" + users.size() + "个用户");
        for (int i = 0; i < users.size(); i++) {
          WeiXinUser user = new WeiXinUser();
          user.setUserid(((JSONObject)users.get(i)).getString("userid"));
          user.setName(((JSONObject)users.get(i)).getString("name"));
          weixinUsers.add(user);
        } 
      } catch (JSONException e) {
        e.printStackTrace();
        return;
      } 
    } else {
      return;
    } 
    deleteUser(weixinUsers, token);
    addOrUpdateUser(weixinUsers, token);
    deleteOrg(weixinOrgs, token);
    System.out.println("微信数据同步完成………………" + new Date());
  }
  
  public void addOrUpdateOrgToWeiXin(String orgid, List<WeiXinORG> weixinOrgs, String token) {
    String sql = "select org_id,orgname,orgparentorgid,weixinorgid,orgordercode from org_organization where orgparentorgid=" + 
      orgid + " and orgStatus=0";
    String[][] result = (String[][])null;
    try {
      DbOpt db = new DbOpt();
      result = db.executeQueryToStrArr2(sql, 5);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result == null || result.length < 1)
      return; 
    WeiXinORG org = null;
    for (int j = 0; j < result.length; j++) {
      boolean isExist = false;
      for (int i = 0; i < weixinOrgs.size(); i++) {
        org = weixinOrgs.get(i);
        String tempid = result[j][3];
        if (tempid == null || "".equals(tempid))
          tempid = "-1"; 
        if (org.getId() == Integer.valueOf(tempid).intValue()) {
          isExist = true;
          break;
        } 
      } 
      if (isExist) {
        if (!result[j][1].equals(org.getName()) || !String.valueOf(10000000L - Long.parseLong(result[j][4])).equals(org.getOrder())) {
          String para = "{\"id\":" + org.getId() + ",\"name\":\"" + result[j][1] + "\",\"order\":\"" + (10000000L - Long.parseLong(result[j][4])) + "\"}";
          String updateOrgUrl = UPDATE_ORG_URL.replace("ACCESS_TOKEN", token);
          JSONObject jsonObject = WeixinUtil.httpRequest(updateOrgUrl, "POST", para);
          if (jsonObject.getInt("errcode") != 0) {
            System.out.println("修改机构【" + result[j][1] + "】出错！出错信息：" + jsonObject.getString("errmsg"));
            return;
          } 
          System.out.println("修改机构【" + result[j][1] + "】成功!");
        } 
      } else {
        String parentid = "";
        if ("0".equals(result[j][2])) {
          parentid = "1";
        } else {
          String pid = result[j][2];
          String getPidSql = "select weixinorgid from org_organization where org_id=" + pid;
          try {
            DbOpt db = new DbOpt();
            parentid = db.executeQueryToStr(getPidSql);
            db.close();
          } catch (SQLException e) {
            e.printStackTrace();
            return;
          } catch (Exception e) {
            e.printStackTrace();
            return;
          } 
        } 
        String para = "{\"name\":\"" + result[j][1] + "\",\"parentid\":\"" + parentid + "\",\"order\":\"" + (10000000L - Long.parseLong(result[j][4])) + "\"}";
        String addOrgUrl = CREATE_ORG_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeixinUtil.httpRequest(addOrgUrl, "POST", para);
        if (jsonObject.getInt("errcode") == 0) {
          System.out.println("新增机构【" + result[j][1] + "】成功!");
          int weixinorgid = jsonObject.getInt("id");
          String updateWeixinOrgIdSql = "update org_organization set weixinorgid = " + weixinorgid + " where org_id = " + result[j][0];
          try {
            DbOpt db = new DbOpt();
            db.executeUpdate(updateWeixinOrgIdSql);
            db.close();
          } catch (SQLException e) {
            e.printStackTrace();
            return;
          } catch (Exception e) {
            e.printStackTrace();
            return;
          } 
        } else {
          System.out.println("新增企业【" + result[j][1] + "】失败，原因：" + jsonObject.getString("errmsg"));
        } 
      } 
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
      addOrUpdateOrgToWeiXin(result[j][0], weixinOrgs, token);
    } 
  }
  
  private void addOrUpdateAllOrgToWeiXin(List<WeiXinORG> weixinOrgs, String token) {
    String sql = "select org_id,orgname,orgparentorgid,weixinorgid,orgordercode from org_organization where orgStatus=0 order by orgIdstring";
    String[][] result = (String[][])null;
    try {
      DbOpt db = new DbOpt();
      result = db.executeQueryToStrArr2(sql, 5);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result == null || result.length < 1)
      return; 
    Map<String, String> weixinOrgMap = new HashMap<String, String>();
    Map<String, String> orgWeixinMap = new HashMap<String, String>();
    for (int j = 0; j < result.length; j++) {
      String weixinOrgId = result[j][3];
      String orgId = result[j][0];
      if (weixinOrgId != null && !"null".equals(weixinOrgId) && !"".equals(weixinOrgId)) {
        weixinOrgMap.put(weixinOrgId, orgId);
        orgWeixinMap.put(orgId, weixinOrgId);
      } else {
        orgWeixinMap.put(orgId, "1");
      } 
    } 
    WeiXinORG org = null;
    for (int i = 0; i < result.length; i++) {
      boolean isExist = false;
      for (int k = 0; k < weixinOrgs.size(); k++) {
        org = weixinOrgs.get(k);
        String tempid = result[i][3];
        if (tempid == null || "".equals(tempid))
          tempid = "-1"; 
        if (org.getId() == Integer.valueOf(tempid).intValue()) {
          isExist = true;
          break;
        } 
      } 
      String parentid = "";
      if ("0".equals(result[i][2])) {
        parentid = "1";
      } else {
        parentid = orgWeixinMap.get(result[i][2]);
      } 
      if (isExist) {
        if (!result[i][1].equals(org.getName()) || 
          !String.valueOf(10000000L - Long.parseLong(result[i][4])).equals(org.getOrder()) || 
          !result[i][2].equals(weixinOrgMap.get(org.getParentid()))) {
          String para = "{\"id\":" + org.getId() + ",\"name\":\"" + result[i][1] + "\",\"parentid\":\"" + parentid + "\",\"order\":\"" + (10000000L - Long.parseLong(result[i][4])) + "\"}";
          String updateOrgUrl = UPDATE_ORG_URL.replace("ACCESS_TOKEN", token);
          JSONObject jsonObject = WeixinUtil.httpRequest(updateOrgUrl, "POST", para);
          if (jsonObject.getInt("errcode") != 0) {
            System.out.println("修改机构【" + result[i][1] + "】出错！出错信息：" + jsonObject.getString("errmsg"));
            return;
          } 
          System.out.println("修改机构【" + result[i][1] + "】成功!");
        } 
      } else {
        String para = "{\"name\":\"" + result[i][1] + "\",\"parentid\":\"" + parentid + "\",\"order\":\"" + (10000000L - Long.parseLong(result[i][4])) + "\"}";
        String addOrgUrl = CREATE_ORG_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeixinUtil.httpRequest(addOrgUrl, "POST", para);
        if (jsonObject.getInt("errcode") == 0) {
          System.out.println("新增机构【" + result[i][1] + "】成功!");
          int weixinorgid = jsonObject.getInt("id");
          String updateWeixinOrgIdSql = "update org_organization set weixinorgid = " + weixinorgid + " where org_id = " + result[i][0];
          try {
            DbOpt db = new DbOpt();
            db.executeUpdate(updateWeixinOrgIdSql);
            db.close();
          } catch (SQLException e) {
            e.printStackTrace();
            return;
          } catch (Exception e) {
            e.printStackTrace();
            return;
          } 
        } else {
          System.out.println("新增企业【" + result[i][1] + "】失败，原因：" + jsonObject.getString("errmsg"));
        } 
      } 
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void addOrUpdateUser(List<WeiXinUser> users, String token) {
    String sql = "select useraccounts, empname, (select max(weixinorgid) from org_organization org, org_organization_user orgemp  where org.org_id = orgemp.org_id and orgemp.emp_id = emp.emp_id) orgid, empposition, empmobilephone, empsex, empbusinessphone, empemail, weixinid from org_employee emp where (empmobilephone is not null or empemail is not null or weixinid is not null) and userisdeleted = 0 and userisactive=1 and useraccounts is not null and weixinpost='1'";
    String[][] result = (String[][])null;
    try {
      DbOpt db = new DbOpt();
      result = db.executeQueryToStrArr2(sql, 9);
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } 
    if (result == null || result.length < 1)
      return; 
    for (int i = 0; i < result.length; i++) {
      if (isNull(result[i][4]) && isNull(result[i][7]) && isNull(result[i][8]))
        continue; 
      boolean isExist = false;
      WeiXinUser weixinUser = null;
      for (int j = 0; j < users.size(); j++) {
        if (result[i][0].equals(((WeiXinUser)users.get(j)).getUserid())) {
          weixinUser = users.get(j);
          isExist = true;
          break;
        } 
      } 
      if (isExist) {
        boolean needUpdate = false;
        String getUserURL = GET_USER_URL.replace("ACCESS_TOKEN", token).replace("USERID", weixinUser.getUserid());
        JSONObject jsonObject = WeixinUtil.httpRequest(getUserURL, "GET", null);
        if (jsonObject != null && jsonObject.getInt("errcode") == 0) {
          if (jsonObject.containsKey("status"))
            if ("1".equals(jsonObject.getString("status"))) {
              System.out.println("用户【" + weixinUser.getName() + "】已关注企业号！");
            } else {
              if ("2".equals(jsonObject.getString("status"))) {
                System.out.println("用户【" + weixinUser.getName() + "】帐号已被冻结，不可以修改信息！");
                continue;
              } 
              if (!"4".equals(jsonObject.getString("status"))) {
                System.out.println("未知状态码：" + jsonObject.getString("status"));
                continue;
              } 
            }  
          if (jsonObject.containsKey("department"))
            weixinUser.setDepartment(jsonObject.getString("department")); 
          if (jsonObject.containsKey("position"))
            weixinUser.setPosition(jsonObject.getString("position")); 
          if (jsonObject.containsKey("email"))
            weixinUser.setEmail(jsonObject.getString("email")); 
          if (jsonObject.containsKey("mobile"))
            weixinUser.setMobile(jsonObject.getString("mobile")); 
          if (jsonObject.containsKey("name"))
            weixinUser.setName(jsonObject.getString("name")); 
          if (jsonObject.containsKey("weixinid"))
            weixinUser.setWeixinid(jsonObject.getString("weixinid")); 
        } else {
          System.out.println("获取用户【" + weixinUser.getName() + "】基本信息错误！原因：" + jsonObject.getString("errmsg"));
          continue;
        } 
        String para = "{";
        para = String.valueOf(para) + "\"userid\":\"" + weixinUser.getUserid() + "\",";
        if (!isEqu(result[i][1], weixinUser.getName())) {
          para = String.valueOf(para) + "\"name\":\"" + result[i][1] + "\",";
          needUpdate = true;
        } 
        if (!isEqu("[" + result[i][2] + "]", weixinUser.getDepartment())) {
          para = String.valueOf(para) + "\"department\":[" + result[i][2] + "],";
          needUpdate = true;
        } 
        if (!isEqu(result[i][3], weixinUser.getPosition())) {
          para = String.valueOf(para) + "\"position\":\"" + result[i][3] + "\",";
          needUpdate = true;
        } 
        if (!isEqu(result[i][4], weixinUser.getMobile())) {
          para = String.valueOf(para) + "\"mobile\":\"" + result[i][4] + "\",";
          needUpdate = true;
        } 
        if (!isEqu(result[i][7], weixinUser.getEmail())) {
          para = String.valueOf(para) + "\"email\":\"" + result[i][7] + "\",";
          needUpdate = true;
        } 
        if (!isEqu(result[i][8], weixinUser.getWeixinid())) {
          para = String.valueOf(para) + "\"weixinid\":\"" + result[i][8] + "\",";
          needUpdate = true;
        } 
        if (para.endsWith(","))
          para = para.substring(0, para.length() - 1); 
        para = String.valueOf(para) + "}";
        System.out.println("修改用户参数：" + para);
        if (needUpdate) {
          String updateUserURL = UPDATE_USER_URL.replace("ACCESS_TOKEN", token);
          jsonObject = WeixinUtil.httpRequest(updateUserURL, "POST", para);
          if (jsonObject.getInt("errcode") == 0) {
            System.out.println("修改用户【" + result[i][1] + "】成功！");
          } else {
            System.out.println("修改用户【" + result[i][1] + "】失败，原因:" + jsonObject.getString("errmsg") + "！");
          } 
        } 
      } else {
        String para = "{";
        para = String.valueOf(para) + "\"userid\":\"" + result[i][0] + "\",";
        para = String.valueOf(para) + "\"name\":\"" + result[i][1] + "\",";
        para = String.valueOf(para) + "\"department\":[" + result[i][2] + "],";
        if (!isNull(result[i][3]))
          para = String.valueOf(para) + "\"position\":\"" + result[i][3] + "\","; 
        if (!isNull(result[i][4]))
          para = String.valueOf(para) + "\"mobile\":\"" + result[i][4] + "\","; 
        if (!isNull(result[i][7]))
          para = String.valueOf(para) + "\"email\":\"" + result[i][7] + "\","; 
        if (!isNull(result[i][8]))
          para = String.valueOf(para) + "\"weixinid\":\"" + result[i][8] + "\","; 
        if (para.endsWith(","))
          para = para.substring(0, para.length() - 1); 
        para = String.valueOf(para) + "}";
        System.out.println("新增用户参数：" + para);
        String createUserUrl = CREATE_USER_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeixinUtil.httpRequest(createUserUrl, "POST", para);
        if (jsonObject.getInt("errcode") == 0) {
          System.out.println("新增用户【" + result[i][1] + "】成功！");
        } else {
          System.out.println("新增用户【" + result[i][1] + "】失败，原因:" + jsonObject.getString("errmsg") + "！");
          System.out.println("新增用户【" + result[i][1] + "】失败，原因:" + jsonObject.toString() + "！");
        } 
      } 
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
      continue;
    } 
  }
  
  public void deleteUser(List<WeiXinUser> users, String token) {
    for (int i = 0; i < users.size(); i++) {
      WeiXinUser user = users.get(i);
      boolean delete = false;
      try {
        String sql = "select count(*) from org_employee where  userisdeleted = 0 and userisactive=1 and weixinpost='1' and useraccounts='" + 

          
          user.getUserid() + "'";
        DbOpt db = new DbOpt();
        String count = db.executeQueryToStr(sql);
        db.close();
        if ("0".equals(count))
          delete = true; 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (delete) {
        String deleteUserUrl = DELETE_USER_URL.replace("ACCESS_TOKEN", token).replace("USERID", user.getUserid());
        JSONObject jsonObject = WeixinUtil.httpRequest(deleteUserUrl, "POST", null);
        if (jsonObject.getInt("errcode") == 0) {
          System.out.println("删除用户【" + user.getName() + "】成功！");
        } else {
          System.out.println("删除用户【" + user.getName() + "】失败，原因:" + jsonObject.getString("errmsg") + "！");
        } 
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public void deleteOrg(List<WeiXinORG> weixinOrgs, String token) {
    for (int i = weixinOrgs.size() - 1; i > 0; i--) {
      WeiXinORG org = weixinOrgs.get(i);
      boolean delete = false;
      try {
        String sql = "select count(*) from org_organization where orgStatus=0 and weixinorgid='" + org.getId() + "'";
        DbOpt db = new DbOpt();
        String count = db.executeQueryToStr(sql);
        db.close();
        if ("0".equals(count))
          delete = true; 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (delete) {
        String deleteOrgUrl = DELETE_ORG_URL.replace("ACCESS_TOKEN", token).replace("ORGID", (new StringBuilder(String.valueOf(org.getId()))).toString());
        JSONObject jsonObject = WeixinUtil.httpRequest(deleteOrgUrl, "POST", null);
        if (jsonObject.getInt("errcode") == 0) {
          System.out.println("删除机构【" + org.getName() + "】成功！");
        } else {
          System.out.println("删除机构【" + org.getName() + "】失败，原因:" + jsonObject.getString("errmsg") + "！");
        } 
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public boolean isNull(String para) {
    return !(para != null && !"".equals(para) && !"null".equalsIgnoreCase(para));
  }
  
  public boolean isEqu(String str1, String str2) {
    boolean equ = false;
    if (str1 == null && str2 == null) {
      equ = true;
    } else if (str1 != null && str1.equalsIgnoreCase(str2)) {
      equ = true;
    } else if (str2 != null && str2.equalsIgnoreCase(str1)) {
      equ = true;
    } else if (str1 == null && "".equals(str2)) {
      equ = true;
    } else if (str2 == null && "".equals(str1)) {
      equ = true;
    } else {
      equ = false;
    } 
    return equ;
  }
}
