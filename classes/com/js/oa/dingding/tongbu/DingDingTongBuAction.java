package com.js.oa.dingding.tongbu;

import com.ali.dingding.util.DingdingUtil;
import com.ali.dingding.util.HttpHelper;
import com.ali.dingding.util.OApiException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.js.oa.dingding.pojo.DingDingOrg;
import com.js.oa.dingding.pojo.DingDingUser;
import com.js.oa.userdb.util.DbOpt;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DingDingTongBuAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    try {
      dingdingTongBu();
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
  
  private static String CREATE_ORG_URL = "https://oapi.dingtalk.com/department/create?access_token=ACCESS_TOKEN";
  
  private static String UPDATE_ORG_URL = "https://oapi.dingtalk.com/department/update?access_token=ACCESS_TOKEN";
  
  private static String DELETE_ORG_URL = "https://oapi.dingtalk.com/department/delete?access_token=ACCESS_TOKEN&id=ID";
  
  private static String LIST_ORG_URL = "https://oapi.dingtalk.com/department/list?access_token=ACCESS_TOKEN";
  
  private static String CREATE_USER_URL = "https://oapi.dingtalk.com/user/create?access_token=ACCESS_TOKEN";
  
  private static String UPDATE_USER_URL = "https://oapi.dingtalk.com/user/update?access_token=ACCESS_TOKEN";
  
  private static String DELETE_USER_URL = "https://oapi.dingtalk.com/user/delete?access_token=ACCESS_TOKEN&userid=ID";
  
  private static String GET_USER_URL = "https://oapi.dingtalk.com/user/get?access_token=ACCESS_TOKEN&userid=USERID";
  
  private static String GET_ORG_USER_URL = "https://oapi.dingtalk.com/user/simplelist?access_token=ACCESS_TOKEN&department_id=1&fetch_child=1";
  
  private List<DingDingOrg> allOrgs = null;
  
  public void dingdingTongBu() throws Exception {
    System.out.println("开始为新数据同步………………");
    String token = "";
    JSONObject jsonObject = null;
    token = DingdingUtil.getAccessToken().getToken();
    System.out.println("获取token成功！ token=" + token);
    String listOrgUrl = LIST_ORG_URL.replace("ACCESS_TOKEN", token);
    jsonObject = HttpHelper.httpGet(listOrgUrl);
    List<DingDingOrg> dingdingOrgs = new ArrayList<DingDingOrg>();
    if (jsonObject != null) {
      try {
        int errcode = jsonObject.getIntValue("errcode");
        if (errcode != 0)
          throw new JSONException("获取所有部门错误，错误信息：" + jsonObject.getString("errmsg")); 
        JSONArray orgs = jsonObject.getJSONArray("department");
        System.out.println("钉钉中总共有" + orgs.size() + "个组织");
        for (int i = 0; i < orgs.size(); i++) {
          DingDingOrg org = new DingDingOrg();
          org.setId(((JSONObject)orgs.get(i)).getIntValue("id"));
          org.setName(((JSONObject)orgs.get(i)).getString("name"));
          org.setParentId(((JSONObject)orgs.get(i)).getIntValue("parentid"));
          dingdingOrgs.add(org);
        } 
      } catch (JSONException e) {
        e.printStackTrace();
        return;
      } 
    } else {
      return;
    } 
    addOrUpdateOrgToDingDing("0", dingdingOrgs, token);
    String listUserUrl = GET_ORG_USER_URL.replace("ACCESS_TOKEN", token);
    jsonObject = HttpHelper.httpGet(listUserUrl);
    List<DingDingUser> dingdingUsers = new ArrayList<DingDingUser>();
    if (jsonObject != null) {
      try {
        int errcode = jsonObject.getIntValue("errcode");
        if (errcode != 0)
          throw new JSONException("获取所有用户错误，错误信息：" + jsonObject.getString("errmsg")); 
        JSONArray users = jsonObject.getJSONArray("userlist");
        System.out.println("钉钉中总共有" + users.size() + "个用户");
        for (int i = 0; i < users.size(); i++) {
          DingDingUser user = new DingDingUser();
          user.setUserId(((JSONObject)users.get(i)).getString("userid"));
          user.setName(((JSONObject)users.get(i)).getString("name"));
          dingdingUsers.add(user);
        } 
      } catch (JSONException e) {
        e.printStackTrace();
        return;
      } 
    } else {
      return;
    } 
    deleteUser(dingdingUsers, token);
    addOrUpdateUser(dingdingUsers, token);
    this.allOrgs = dingdingOrgs;
    deleteOrg(dingdingOrgs, token);
    System.out.println("钉钉数据同步完成………………" + new Date());
  }
  
  public void addOrUpdateOrgToDingDing(String orgid, List<DingDingOrg> dingdingOrgs, String token) throws OApiException {
    String sql = "select org_id,orgname,orgparentorgid,dingdingorgid from org_organization where orgparentorgid=" + 
      orgid + " and orgStatus=0 ORDER BY ORGIDSTRING";
    String[][] result = (String[][])null;
    try {
      DbOpt db = new DbOpt();
      result = db.executeQueryToStrArr2(sql, 4);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result == null || result.length < 1)
      return; 
    DingDingOrg org = null;
    for (int j = 0; j < result.length; j++) {
      boolean isExist = false;
      for (int i = 0; i < dingdingOrgs.size(); i++) {
        org = dingdingOrgs.get(i);
        String tempid = result[j][3];
        if (tempid == null || "".equals(tempid))
          tempid = "-1"; 
        if (org.getId() == Integer.valueOf(tempid).intValue()) {
          isExist = true;
          break;
        } 
      } 
      if (isExist) {
        if (!result[j][1].equals(org.getName())) {
          String updateOrgUrl = UPDATE_ORG_URL.replace("ACCESS_TOKEN", token);
          JSONObject paraObj = new JSONObject();
          paraObj.put("id", Integer.valueOf(org.getId()));
          paraObj.put("name", result[j][1]);
          JSONObject jsonObject = null;
          try {
            jsonObject = HttpHelper.httpPost(updateOrgUrl, paraObj);
          } catch (Exception exception) {}
          if (jsonObject != null && jsonObject.getIntValue("errcode") != 0) {
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
          String getPidSql = "select dingdingorgid from org_organization where org_id=" + pid;
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
        String addOrgUrl = CREATE_ORG_URL.replace("ACCESS_TOKEN", token);
        JSONObject paraObj = new JSONObject();
        paraObj.put("name", result[j][1]);
        paraObj.put("parentid", parentid);
        JSONObject jsonObject = null;
        try {
          jsonObject = HttpHelper.httpPost(addOrgUrl, paraObj);
        } catch (Exception exception) {}
        if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
          System.out.println("新增机构【" + result[j][1] + "】成功!");
          int dingdingorgid = jsonObject.getIntValue("id");
          String updateDingDingOrgIdSql = "update org_organization set dingdingorgid = " + dingdingorgid + " where org_id = " + result[j][0];
          try {
            DbOpt db = new DbOpt();
            db.executeUpdate(updateDingDingOrgIdSql);
            db.close();
          } catch (SQLException e) {
            e.printStackTrace();
            return;
          } catch (Exception e) {
            e.printStackTrace();
            return;
          } 
        } else {
          System.out.println("新增企业【" + result[j][1] + "】失败");
        } 
      } 
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
      addOrUpdateOrgToDingDing(result[j][0], dingdingOrgs, token);
    } 
  }
  
  public void addOrUpdateUser(List<DingDingUser> users, String token) throws OApiException {
    String sql = "select useraccounts, empname, (select max(org.dingdingorgid) from org_organization org, org_organization_user orgemp  where org.org_id = orgemp.org_id and orgemp.emp_id = emp.emp_id) orgid, empposition, empmobilephone, empsex, empbusinessphone, empemail from org_employee emp where (empmobilephone is not null)  and userisdeleted = 0 and userisactive=1 and useraccounts is not null and dingdingpost='1'";
    String[][] result = (String[][])null;
    try {
      DbOpt db = new DbOpt();
      result = db.executeQueryToStrArr2(sql, 8);
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } 
    if (result == null || result.length < 1)
      return; 
    for (int i = 0; i < result.length; i++) {
      if (!isNull(result[i][4]) || !isNull(result[i][7])) {
        boolean isExist = false;
        DingDingUser dingdingUser = null;
        for (int j = 0; j < users.size(); j++) {
          if (result[i][0].equals(((DingDingUser)users.get(j)).getUserId())) {
            dingdingUser = users.get(j);
            isExist = true;
            break;
          } 
        } 
        if (isExist) {
          boolean needUpdate = false;
          String getUserURL = GET_USER_URL.replace("ACCESS_TOKEN", token).replace("USERID", dingdingUser.getUserId());
          JSONObject jsonObject = HttpHelper.httpGet(getUserURL);
          if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
            if (jsonObject.containsKey("position"))
              dingdingUser.setPosition(jsonObject.getString("position")); 
            if (jsonObject.containsKey("email"))
              dingdingUser.setEmail(jsonObject.getString("email")); 
            if (jsonObject.containsKey("mobile"))
              dingdingUser.setMobile(jsonObject.getString("mobile")); 
            if (jsonObject.containsKey("name"))
              dingdingUser.setName(jsonObject.getString("name")); 
          } else {
            System.out.println("获取用户【" + dingdingUser.getName() + "】基本信息错误！");
            i++;
          } 
          JSONObject paraObj = new JSONObject();
          paraObj.put("userid", dingdingUser.getUserId());
          if (!isEqu(result[i][1], dingdingUser.getName())) {
            paraObj.put("name", result[i][1]);
            needUpdate = true;
          } 
          if (!isEqu(result[i][3], dingdingUser.getPosition())) {
            paraObj.put("position", result[i][3]);
            needUpdate = true;
          } 
          if (!isEqu(result[i][4], dingdingUser.getMobile())) {
            paraObj.put("mobile", result[i][4]);
            needUpdate = true;
          } 
          if (!isEqu(result[i][7], dingdingUser.getEmail())) {
            paraObj.put("email", result[i][7]);
            needUpdate = true;
          } 
          if (needUpdate) {
            String updateUserURL = UPDATE_USER_URL.replace("ACCESS_TOKEN", token);
            try {
              jsonObject = HttpHelper.httpPost(updateUserURL, paraObj);
            } catch (Exception exception) {}
            if (jsonObject.getIntValue("errcode") == 0) {
              System.out.println("修改用户【" + result[i][1] + "】成功！");
            } else {
              System.out.println("修改用户【" + result[i][1] + "】失败！");
            } 
          } 
        } else {
          JSONObject paraObj = new JSONObject();
          paraObj.put("userid", result[i][0]);
          paraObj.put("name", result[i][1]);
          List<String> ds = new ArrayList<String>();
          ds.add(result[i][2]);
          paraObj.put("department", ds);
          if (!isNull(result[i][3]))
            paraObj.put("postion", result[i][3]); 
          if (!isNull(result[i][4]))
            paraObj.put("mobile", result[i][4]); 
          if (!isNull(result[i][7]))
            paraObj.put("email", result[i][7]); 
          String createUserUrl = CREATE_USER_URL.replace("ACCESS_TOKEN", token);
          JSONObject jsonObject = null;
          try {
            jsonObject = HttpHelper.httpPost(createUserUrl, paraObj);
          } catch (Exception exception) {}
          if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
            System.out.println("新增用户【" + result[i][1] + "】成功！");
          } else {
            System.out.println("新增用户【" + result[i][1] + "】失败！");
          } 
        } 
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public void deleteUser(List<DingDingUser> users, String token) throws OApiException {
    for (int i = 0; i < users.size(); i++) {
      DingDingUser user = users.get(i);
      boolean delete = false;
      try {
        String sql = "select count(*) from org_employee where  userisdeleted = 0 and userisactive=1 and dingdingpost='1' and useraccounts='" + 

          
          user.getUserId() + "'";
        DbOpt db = new DbOpt();
        String count = db.executeQueryToStr(sql);
        db.close();
        if ("0".equals(count))
          delete = true; 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (delete) {
        String deleteUserUrl = DELETE_USER_URL.replace("ACCESS_TOKEN", token).replace("ID", user.getUserId());
        JSONObject jsonObject = null;
        try {
          jsonObject = HttpHelper.httpGet(deleteUserUrl);
        } catch (Exception exception) {}
        if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
          System.out.println("删除用户【" + user.getName() + "】成功！");
        } else {
          System.out.println("删除用户【" + user.getName() + "】失败！");
        } 
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public void deleteOrg(List<DingDingOrg> dingdingOrgs, String token) throws OApiException {
    boolean isDelete;
    do {
      isDelete = false;
      for (int j = this.allOrgs.size() - 1; j >= 0; j--) {
        DingDingOrg curOrg = this.allOrgs.get(j);
        if (curOrg != null) {
          boolean hasChildren = false;
          for (int k = 0; k < this.allOrgs.size(); k++) {
            DingDingOrg temp = this.allOrgs.get(k);
            if (temp != null && 
              temp.getParentId() == curOrg.getId()) {
              hasChildren = true;
              break;
            } 
          } 
          if (!hasChildren) {
            boolean delete = isDeletedFromOA(curOrg.getId());
            if (delete) {
              String deleteOrgUrl = DELETE_ORG_URL.replace("ACCESS_TOKEN", token).replace("ID", (new StringBuilder(String.valueOf(curOrg.getId()))).toString());
              JSONObject jsonObject = null;
              try {
                jsonObject = HttpHelper.httpGet(deleteOrgUrl);
              } catch (Exception exception) {}
              if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
                System.out.println("删除机构【" + curOrg.getName() + "】成功！");
              } else {
                System.out.println("删除机构【" + curOrg.getName() + "】失败！");
              } 
              try {
                Thread.sleep(1000L);
              } catch (InterruptedException e) {
                e.printStackTrace();
              } 
              isDelete = true;
              this.allOrgs.remove(curOrg);
              break;
            } 
          } 
        } 
      } 
    } while (isDelete);
    for (int i = 0; i < this.allOrgs.size(); i++) {
      DingDingOrg org = this.allOrgs.get(i);
      if (org == null);
    } 
  }
  
  private boolean isDeletedFromOA(int ddId) {
    boolean delete = false;
    try {
      String sql = "select count(*) from org_organization where orgStatus=0 and dingdingorgid='" + ddId + "'";
      DbOpt db = new DbOpt();
      String count = db.executeQueryToStr(sql);
      db.close();
      if ("0".equals(count))
        delete = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return delete;
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
