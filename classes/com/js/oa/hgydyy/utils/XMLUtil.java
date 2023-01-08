package com.js.oa.hgydyy.utils;

import com.js.oa.hgydyy.pojo.DyyObject;
import com.js.oa.hgydyy.pojo.DyyOrganization;
import com.js.oa.hgydyy.pojo.DyyPerson;
import com.js.oa.hgydyy.pojo.DyyRole;
import com.js.oa.hgydyy.pojo.ResultData;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLUtil {
  private static final String OBJ_TYPE_PERSON = "person";
  
  private static final String OBJ_TYPE_ORG = "organizationalUnit";
  
  private static final String OBJ_TYPE_ROLE = "group";
  
  private static DyyPerson getPersonFromElement(Element data) throws JDOMException, IOException {
    DyyPerson person = new DyyPerson();
    Element id = data.getChild("id");
    if (id != null)
      person.setId(id.getValue()); 
    Element operation = data.getChild("operation");
    if (operation != null)
      person.setOperation(operation.getValue()); 
    Element type = data.getChild("type");
    if (type != null)
      person.setType(type.getValue()); 
    Element uuid = data.getChild("uuid");
    if (uuid != null)
      person.setUuid(uuid.getValue()); 
    Element oldName = data.getChild("oldName");
    if (oldName != null)
      person.setOldName(oldName.getValue()); 
    Element newName = data.getChild("newName");
    if (newName != null)
      person.setNewName(newName.getValue()); 
    Element oldParentPath = data.getChild("oldParentPath");
    if (oldParentPath != null)
      person.setOldParentPath(oldParentPath.getValue()); 
    Element oldParentUuid = data.getChild("oldParentUuid");
    if (oldParentUuid != null)
      person.setOldParentUuid(oldParentUuid.getValue()); 
    Element newParentPath = data.getChild("newParentPath");
    if (newParentPath != null)
      person.setNewParentPath(newParentPath.getValue()); 
    Element newParentUuid = data.getChild("newParentUuid");
    if (newParentUuid != null)
      person.setNewParentUuid(newParentUuid.getValue()); 
    Element utsNode = data.getChild("utsNode");
    if (utsNode != null) {
      Element sn = utsNode.getChild("sn");
      if (sn != null)
        person.setSn(sn.getValue()); 
      Element cn = utsNode.getChild("cn");
      if (cn != null)
        person.setCn(cn.getValue()); 
      Element userpassword = utsNode.getChild("userpassword");
      if (userpassword != null)
        person.setUserPassWord(userpassword.getValue()); 
      Element userpasswordcipher = utsNode.getChild("userpasswordcipher");
      if (userpasswordcipher != null)
        person.setUserPassWordCipher(userpasswordcipher.getValue()); 
      Element ordercode = utsNode.getChild("ordercode");
      if (ordercode != null)
        person.setOrderCode(ordercode.getValue()); 
      Element employeemail = utsNode.getChild("employeemail");
      if (employeemail != null)
        person.setEmployeeMail(employeemail.getValue()); 
      Element employeemobile = utsNode.getChild("employeemobile");
      if (employeemobile != null)
        person.setEmployeeMobile(employeemobile.getValue()); 
      Element employeebirthday = utsNode.getChild("employeebirthday");
      if (employeebirthday != null)
        person.setEmployeeBirthday(employeebirthday.getValue()); 
      Element employeesex = utsNode.getChild("employeesex");
      if (employeesex != null)
        person.setEmployeeSex(employeesex.getValue()); 
      Element employeestate = utsNode.getChild("employeestate");
      if (employeestate != null)
        person.setEmployeestate(employeestate.getValue()); 
      Element employeecard = utsNode.getChild("employeecard");
      if (employeecard != null)
        person.setEmployeeCard(employeecard.getValue()); 
      Element employeeid = utsNode.getChild("employeeid");
      if (employeeid != null)
        person.setEmployeeId(employeeid.getValue()); 
      Element employeecode = utsNode.getChild("employeecode");
      if (employeecode != null)
        person.setEmployeeCode(employeecode.getValue()); 
      Element employeeposition = utsNode.getChild("employeeposition");
      if (employeeposition != null)
        person.setEmployeePosition(employeeposition.getValue()); 
      Element erpdeptname = utsNode.getChild("erpdeptname");
      if (erpdeptname != null)
        person.setErpdeptname(erpdeptname.getValue()); 
      Element erpdepttype = utsNode.getChild("erpdepttype");
      if (erpdepttype != null)
        person.setErpdepttype(erpdepttype.getValue()); 
      Element erpdeptcode = utsNode.getChild("erpdeptcode");
      if (erpdeptcode != null)
        person.setErpdeptcode(erpdeptcode.getValue()); 
      Element createtime = utsNode.getChild("createtime");
      if (createtime != null)
        person.setCreatetime(createtime.getValue()); 
      Element lastmodifytime = utsNode.getChild("lastmodifytime");
      if (lastmodifytime != null)
        person.setLastmodifytime(lastmodifytime.getValue()); 
    } 
    return person;
  }
  
  private static DyyOrganization getOrgFromElement(Element data) throws JDOMException, IOException {
    DyyOrganization org = new DyyOrganization();
    Element id = data.getChild("id");
    if (id != null)
      org.setId(id.getValue()); 
    Element operation = data.getChild("operation");
    if (operation != null)
      org.setOperation(operation.getValue()); 
    Element type = data.getChild("type");
    if (type != null)
      org.setType(type.getValue()); 
    Element uuid = data.getChild("uuid");
    if (uuid != null)
      org.setUuid(uuid.getValue()); 
    Element oldName = data.getChild("oldName");
    if (oldName != null)
      org.setOldName(oldName.getValue()); 
    Element newName = data.getChild("newName");
    if (newName != null)
      org.setNewName(newName.getValue()); 
    Element oldParentPath = data.getChild("oldParentPath");
    if (oldParentPath != null) {
      String value = oldParentPath.getValue();
      org.setOldParentPath(value);
    } 
    Element oldParentUuid = data.getChild("oldParentUuid");
    if (oldParentUuid != null)
      org.setOldParentUuid(oldParentUuid.getValue()); 
    Element newParentPath = data.getChild("newParentPath");
    if (newParentPath != null) {
      String value = newParentPath.getValue();
      org.setNewParentPath(value);
    } 
    Element newParentUuid = data.getChild("newParentUuid");
    if (newParentUuid != null)
      org.setNewParentUuid(newParentUuid.getValue()); 
    Element utsNode = data.getChild("utsNode");
    if (utsNode != null) {
      Element ou = utsNode.getChild("ou");
      if (ou != null)
        org.setOu(ou.getValue()); 
      Element deptcode = utsNode.getChild("deptcode");
      if (deptcode != null)
        org.setDeptCode(deptcode.getValue()); 
      Element depttype = utsNode.getChild("depttype");
      if (depttype != null)
        org.setDeptType(depttype.getValue()); 
      Element erpdeptname = utsNode.getChild("erpdeptname");
      if (erpdeptname != null)
        org.setErpdeptname(erpdeptname.getValue()); 
      Element erpdeptcode = utsNode.getChild("erpdeptcode");
      if (erpdeptcode != null)
        org.setErpdeptcode(erpdeptcode.getValue()); 
      Element erpdepttype = utsNode.getChild("erpdepttype");
      if (erpdepttype != null)
        org.setErpdepttype(erpdepttype.getValue()); 
      Element guid = utsNode.getChild("guid");
      if (guid != null)
        org.setGuid(guid.getValue()); 
      Element ordercode = utsNode.getChild("ordercode");
      if (ordercode != null)
        org.setOrderCode(ordercode.getValue()); 
      Element createtime = utsNode.getChild("createtime");
      if (createtime != null)
        org.setCreatetime(createtime.getValue()); 
      Element lastmodifytime = utsNode.getChild("lastmodifytime");
      if (lastmodifytime != null)
        org.setLastmodifytime(lastmodifytime.getValue()); 
    } 
    return org;
  }
  
  private static DyyRole getRoleFromElement(Element data) throws JDOMException, IOException {
    DyyRole role = new DyyRole();
    Element id = data.getChild("id");
    if (id != null)
      role.setId(id.getValue()); 
    Element operation = data.getChild("operation");
    if (operation != null)
      role.setOperation(operation.getValue()); 
    Element type = data.getChild("type");
    if (type != null)
      role.setType(type.getValue()); 
    Element uuid = data.getChild("uuid");
    if (uuid != null)
      role.setUuid(uuid.getValue()); 
    Element utsNode = data.getChild("utsNode");
    if (utsNode != null) {
      Element ou = utsNode.getChild("groupName");
      if (ou != null)
        role.setRoleName(ou.getValue()); 
      ou = utsNode.getChild("groupDescription");
      if (ou != null)
        role.setRoleDescription(ou.getValue()); 
    } 
    return role;
  }
  
  public static String getResultStr(List<ResultData> list) {
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<datas>";
    for (int i = 0; i < list.size(); i++) {
      ResultData data = list.get(i);
      xml = String.valueOf(xml) + "<data>";
      xml = String.valueOf(xml) + "<id>" + data.getId() + "</id>";
      xml = String.valueOf(xml) + "<code>" + data.getCode() + "</code>";
      xml = String.valueOf(xml) + "<message>" + data.getMessage() + "</message>";
      xml = String.valueOf(xml) + "</data>";
    } 
    xml = String.valueOf(xml) + "</datas>";
    return xml;
  }
  
  public static List<DyyObject> getObjectsFromXML(String xml) throws JDOMException, IOException {
    List<DyyObject> list = new ArrayList<DyyObject>();
    SAXBuilder builder = new SAXBuilder();
    ByteArrayInputStream xmlStream = null;
    try {
      xmlStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    } 
    Document doc = builder.build(xmlStream);
    Element root = doc.getRootElement();
    List<Element> datas = root.getChildren();
    for (int i = 0; i < datas.size(); i++) {
      Element data = datas.get(i);
      String objType = data.getChildText("type");
      DyyObject obj = null;
      if ("person".equals(objType)) {
        obj = getPersonFromElement(data);
      } else if ("organizationalUnit".equals(objType)) {
        obj = getOrgFromElement(data);
      } else if ("group".equals(objType)) {
        obj = getRoleFromElement(data);
      } else {
        System.out.println("无法识别的对象类型：" + objType);
      } 
      list.add(obj);
    } 
    return list;
  }
  
  public static void main(String[] args) {
    String value = "/asdasd/asdasd/asdads";
    value = value.substring(1, value.length()).replace("/", ".");
    System.out.println(value);
  }
}
