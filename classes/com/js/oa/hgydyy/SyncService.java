package com.js.oa.hgydyy;

import com.js.oa.hgydyy.service.SynchronizeDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SyncService {
  private String jsonToXML(String jsonStr, int operType) {
    StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    xml.append("<datas>");
    JSONArray jsonArr = JSONArray.fromObject(jsonStr);
    if (jsonArr.size() > 0)
      for (int i = 0; i < jsonArr.size(); i++) {
        String parentUuid, uuid, unitType, orgName;
        JSONObject jsonObj = jsonArr.getJSONObject(i);
        xml.append("<data>");
        if (operType == 0) {
          uuid = jsonObj.getString("orgID");
          parentUuid = jsonObj.getString("parentID");
          orgName = jsonObj.getString("orgName");
          unitType = "organizationalUnit";
        } else {
          parentUuid = jsonObj.getString("orgID");
          orgName = "";
          String userGuid = jsonObj.getString("userID");
          uuid = userGuid;
          unitType = "person";
        } 
        String oprFlag = jsonObj.getString("type");
        if (oprFlag == null || "".equals(oprFlag)) {
          oprFlag = "INSERT";
        } else if (oprFlag.equals("add")) {
          oprFlag = "INSERT";
        } else if (oprFlag.equals("update")) {
          oprFlag = "UPDATE";
        } else if (oprFlag.equals("delete")) {
          oprFlag = "DELETE";
        } 
        xml.append("<id>").append(System.currentTimeMillis()).append("</id>");
        xml.append("<type>").append(unitType).append("</type>");
        xml.append("<operation>").append(oprFlag).append("</operation>");
        xml.append("<uuid>").append(uuid).append("</uuid>");
        xml.append("<oldName></oldName>");
        xml.append("<newName></newName>");
        xml.append("<oldParentPath></oldParentPath>");
        xml.append("<oldParentUuid>").append(parentUuid).append("</oldParentUuid>");
        xml.append("<newParentPath></newParentPath>");
        xml.append("<newParentUuid>").append(parentUuid).append("</newParentUuid>");
        xml.append("<utsNode>");
        if (operType == 0) {
          xml.append("<ou>").append(orgName).append("</ou>");
          xml.append("<deptcode>").append(uuid).append("</deptcode>");
          xml.append("<depttype>0</depttype>");
          xml.append("<deptlevel></deptlevel>");
          xml.append("<ordercode></ordercode>");
          xml.append("<guid>").append(uuid).append("</guid>");
          xml.append("<versoin></versoin>");
          xml.append("<createtime></createtime>");
          xml.append("<lastmodifytime></lastmodifytime>");
        } else {
          xml.append("<cn>").append(jsonObj.get("userID")).append("</cn>");
          xml.append("<sn>").append(jsonObj.get("userName")).append("</sn>");
          xml.append("<userpassword>").append(jsonObj.get("userPWD")).append("</userpassword>");
          xml.append("<userpasswordcipher></userpasswordcipher>");
          xml.append("<employeesex>").append(jsonObj.get("sex")).append("</employeesex>");
          xml.append("<employeemail></employeemail>");
          xml.append("<employeemobile></employeemobile>");
          xml.append("<employeebirthday>").append(jsonObj.get("birthday")).append("</employeebirthday>");
          xml.append("<employeetype></employeetype>");
          xml.append("<employeestate></employeestate>");
          xml.append("<employeeposition></employeeposition>");
          xml.append("<employeepositionjobcode></employeepositionjobcode>");
          xml.append("<employeepositionjobname></employeepositionjobname>");
          xml.append("<versoin></versoin>");
          xml.append("<createtime></createtime>");
          xml.append("<lastmodifytime></lastmodifytime>");
        } 
        xml.append("</utsNode>");
        xml.append("</data>");
      }  
    xml.append("</datas>");
    return xml.toString();
  }
  
  public String syncOrg(String orgInfo) {
    String result = "false";
    orgInfo = "[" + orgInfo + "]";
    String xml = jsonToXML(orgInfo, 0);
    SynchronizeDataService service = new SynchronizeDataService();
    String res = service.synchronizeDatas("", xml);
    if ("0".equals(res))
      result = "true"; 
    return result;
  }
  
  public String syncUser(String userInfo) {
    String result = "false";
    userInfo = "[" + userInfo + "]";
    String xml = jsonToXML(userInfo, 1);
    SynchronizeDataService service = new SynchronizeDataService();
    String res = service.synchronizeDatas("", xml);
    if ("0".equals(res))
      result = "true"; 
    return result;
  }
}
