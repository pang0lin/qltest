package com.js.taglib;

import com.js.oa.crm.po.CstType;
import com.js.oa.crm.po.ProType;
import com.js.oa.crm.service.CstService;
import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.config.SystemCommon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class OrgTag extends TagSupport {
  private String condition;
  
  private String target;
  
  private String allowId;
  
  private String inputType;
  
  private String orgInputType;
  
  private String display;
  
  private String range;
  
  private String type;
  
  private String func;
  
  private String orgIdString = "";
  
  public int doStartTag() throws JspException {
    try {
      String domainId = (this.pageContext.getSession().getAttribute("domainId") == null) ? "0" : this.pageContext.getSession().getAttribute("domainId").toString();
      String userId = this.pageContext.getSession().getAttribute("userId").toString();
      String orgId = this.pageContext.getSession().getAttribute("orgIdString").toString();
      String corpId = this.pageContext.getSession().getAttribute("corpId").toString();
      String a = "";
      if (this.type != null && !this.type.equals("")) {
        if (this.type.equals("orgPerson")) {
          if (this.range.indexOf("$") < 0 && this.range.indexOf("*") >= 0) {
            a = getOrgTreeBufferString(domainId, "0");
          } else {
            a = getOrgTreeBuffer(domainId, "0");
          } 
        } else if (this.type.equals("privatePerson")) {
          a = getPrivatePersonBufferString(userId, domainId);
        } else if (this.type.equals("publicPerson")) {
          a = getPublicPersonBufferString(userId, domainId);
        } else if (this.type.equals("groupPerson")) {
          a = getGroupPersonBufferString(userId, domainId, orgId);
        } else if (this.type.equals("zhiWu")) {
          a = getZWPersonBufferString(domainId, corpId);
        } else if (this.type.equals("gangWei")) {
          a = getGWPersonBufferString(domainId, corpId);
        } else if (this.type.equals("downEmp")) {
          a = getDownEmployeeList(userId);
        } else if (this.type.equals("customer")) {
          a = getCustomerList();
        } else if (this.type.equals("project")) {
          a = getProjectList();
        } 
      } else {
        a = getOrgTreeBufferString(domainId, "1");
      } 
      this.pageContext.getOut().write(a);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return 1;
  }
  
  public int doEndTag() throws JspException {
    try {
      StringBuffer out = new StringBuffer();
      out.append("</script>\n");
      this.pageContext.getOut().write(out.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return 6;
  }
  
  public int doAfterBody() throws JspException {
    return 1;
  }
  
  private List getOrgList(String domainId, String sele) {
    List orgList = null;
    try {
      ManagerService mbean = new ManagerService();
      String rangeTemp = this.range;
      if (rangeTemp.indexOf("*") >= 0 && rangeTemp.indexOf("$") < 0) {
        rangeTemp = this.range.replaceAll("\\*", ",").replaceAll(",,", ",");
        rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
        orgList = mbean.getOrgList(domainId, rangeTemp, sele);
      } else if (rangeTemp.indexOf("*") >= 0 && rangeTemp.indexOf("$") >= 0) {
        rangeTemp = rangeTemp.substring(rangeTemp.indexOf("*") + 1, rangeTemp.lastIndexOf("*"));
        rangeTemp.replaceAll("\\*", ",").replaceAll(",,", ",");
        orgList = mbean.getOrgList(domainId, rangeTemp, sele);
      } else if (rangeTemp.indexOf("$") >= 0) {
        rangeTemp.indexOf("*");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return orgList;
  }
  
  private boolean formatOrg(long parentId) {
    boolean flag = false;
    String rangeTemp = "";
    try {
      if (this.range != null || !this.range.equals("")) {
        rangeTemp = this.range.replaceAll("\\*", ",").replaceAll(",,", ",");
        rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
        for (int i = 0; i < (rangeTemp.split(",")).length; i++) {
          if (rangeTemp.split(",")[i].equals(String.valueOf(parentId))) {
            flag = true;
            break;
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  private Map orgHashMap(List<OrganizationVO> orgList) {
    Map<Object, Object> orgMap = null;
    try {
      if (orgMap == null || orgMap.size() == 0) {
        orgMap = new HashMap<Object, Object>();
        if (this.range != null || !this.range.equals(""))
          for (int i = 0; i < orgList.size(); i++) {
            OrganizationVO org = orgList.get(i);
            orgMap.put(org.getOrgId(), "");
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return orgMap;
  }
  
  private String getOrgTreeBuffer(String domainId, String sele) {
    StringBuffer out = new StringBuffer("");
    String action = "";
    try {
      String rootOrgName;
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      if (sele.equals("1")) {
        out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar_1/','org_tree')\n");
      } else {
        out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      } 
      out.append("d.config.folderLinks=true;\n");
      OrganizationVO vo = getRootDept(domainId);
      if (SystemCommon.getOrgTreeShowSimple() == 1) {
        rootOrgName = vo.getOrgSimpleName();
      } else {
        rootOrgName = vo.getOrgName();
      } 
      if (this.range.indexOf("*-1*") < 0)
        out.append("d.add(0,-1,'" + rootOrgName + "','','" + rootOrgName + "','');\n"); 
      List<OrganizationVO> list = getOrgList(domainId, sele);
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          String orgName;
          OrganizationVO org = list.get(i);
          out.append("d.add(");
          out.append(org.getOrgId() + ",");
          long pranetId = org.getOrgParentOrgId();
          if (orgHashMap(list).containsKey(Long.valueOf(pranetId)) || this.range.equals("*0*")) {
            out.append(String.valueOf(org.getOrgParentOrgId()) + ",'");
          } else {
            out.append("0,'");
          } 
          if (SystemCommon.getOrgTreeShowSimple() == 1) {
            orgName = org.getOrgSimpleName();
          } else {
            orgName = org.getOrgName();
          } 
          out.append(String.valueOf(orgName) + "',");
          if (sele.equals("1")) {
            out.append("'#;','");
          } else {
            out.append("'javascript:submit(" + org.getOrgId() + ");d.o(" + (i + 1) + ")','");
          } 
          out.append(String.valueOf(orgName) + "','");
          if (sele.equals("1"))
            out.append(org.getOrgStatus()); 
          out.append("','','',");
          if (this.orgIdString.contains("%" + org.getOrgId() + "%")) {
            out.append("true);\n");
          } else {
            out.append("false);\n");
          } 
        }  
      if (this.range.indexOf("$") >= 0)
        if (this.range.indexOf("*") < 0) {
          out.append("d.add(2012,0,'可选人员','javascript:submit(2012);','可选人员','');\n");
        } else {
          out.append("d.add(2012,0,'其他','javascript:submit(2012);','其他','');\n");
        }  
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          OrganizationVO org = list.get(i);
          if (this.type != null && !this.type.equals("") && this.type.equals("orgPerson")) {
            String orgName;
            if (SystemCommon.getOrgTreeSelectedSimple() == 1) {
              orgName = org.getOrgSimpleName();
            } else {
              orgName = org.getOrgName();
            } 
            action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getOrgUserList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&orgName=" + orgName + "&orgId=" + org.getOrgId() + "\";\n";
            out.append("if(a==" + org.getOrgId() + "){\n");
            out.append(action);
            out.append("}\n");
          } 
        }  
      out.append("if(a==2012){\n");
      action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getOrgUserList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&orgName=" + rootOrgName + "&orgId=2012\";\n";
      out.append(action);
      out.append("}\n");
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
      if (this.range.indexOf("$") >= 0 && 
        this.range.indexOf("*") < 0)
        out.append("window.setTimeout(\"submit(2012)\", 300);"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private OrganizationVO getRootDept(String domainId) {
    ManagerService mbean = new ManagerService();
    return mbean.getRootDept(domainId).get(0);
  }
  
  private String getOrgTreeBufferString(String domainId, String sele) {
    StringBuffer out = new StringBuffer("");
    String action = "";
    try {
      String rootOrgName;
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      if (sele.equals("1")) {
        out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar_1/','org_tree')\n");
      } else {
        out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      } 
      out.append("d.config.folderLinks=true;\n");
      OrganizationVO vo = getRootDept(domainId);
      if (SystemCommon.getOrgTreeShowSimple() == 1) {
        rootOrgName = vo.getOrgSimpleName();
      } else {
        rootOrgName = vo.getOrgName();
      } 
      String url = "";
      if (sele.equals("1"))
        url = "#;"; 
      if (this.range.equals("*0*") || this.range.equals("") || this.range.equals("0")) {
        url = "javascript:submit(" + vo.getOrgId() + ");";
      } else {
        url = "";
      } 
      if (!this.range.equals("*-1*"))
        out.append("d.add(0,-1,'" + rootOrgName + "','" + url + "','" + rootOrgName + "','');\n"); 
      List<OrganizationVO> list = getOrgList(domainId, sele);
      if (!"1".equals(SystemCommon.getCurrentOrgShowUse())) {
        if (list != null && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            String orgName;
            OrganizationVO org = list.get(i);
            out.append("d.add(");
            out.append((org.getOrgId().longValue() == -1L) ? "0," : (org.getOrgId() + ","));
            long pranetId = org.getOrgParentOrgId();
            if (orgHashMap(list).containsKey(Long.valueOf(pranetId)) || this.range.equals("*0*")) {
              out.append(String.valueOf(pranetId) + ",'");
            } else {
              out.append("0,'");
            } 
            if (SystemCommon.getOrgTreeShowSimple() == 1) {
              orgName = org.getOrgSimpleName();
            } else {
              orgName = org.getOrgName();
            } 
            out.append(String.valueOf(orgName) + "',");
            if (sele.equals("1")) {
              out.append("'#;','");
            } else {
              out.append("'javascript:submit(" + org.getOrgId() + ");d.o(" + (i + 1) + ")','");
            } 
            out.append(String.valueOf(orgName) + "','");
            if (sele.equals("1"))
              out.append(org.getOrgStatus()); 
            out.append("','','',");
            if (this.orgIdString.contains("%" + org.getOrgId() + "%")) {
              out.append("true);\n");
            } else {
              out.append("false);\n");
            } 
          }  
      } else {
        ManagerService mbean = new ManagerService();
        String orgId = this.pageContext.getSession().getAttribute("orgId").toString();
        list = mbean.getOrgCurrentList(domainId, orgId);
        if (list != null && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            String orgName;
            OrganizationVO org = list.get(i);
            out.append("d.add(");
            out.append(org.getOrgId() + ",");
            out.append("0,'");
            if (SystemCommon.getOrgTreeShowSimple() == 1) {
              orgName = org.getOrgSimpleName();
            } else {
              orgName = org.getOrgName();
            } 
            out.append(String.valueOf(orgName) + "',");
            if (sele.equals("1")) {
              out.append("'#;','");
            } else {
              out.append("'javascript:submit(" + org.getOrgId() + ");d.o(" + (i + 1) + ")','");
            } 
            out.append(String.valueOf(orgName) + "','");
            if (sele.equals("1"))
              out.append(org.getOrgStatus()); 
            out.append("','','',");
            if (this.orgIdString.contains("%" + org.getOrgId() + "%")) {
              out.append("true);\n");
            } else {
              out.append("false);\n");
            } 
          }  
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          OrganizationVO org = list.get(i);
          if (this.type != null && !this.type.equals("") && this.type.equals("orgPerson")) {
            String orgName;
            if (SystemCommon.getOrgTreeSelectedSimple() == 1) {
              orgName = org.getOrgSimpleName();
            } else {
              orgName = org.getOrgName();
            } 
            action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getOrgUserList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&orgName=" + orgName + "&orgId=" + org.getOrgId() + "\";\n";
            out.append("if(a==" + org.getOrgId() + "){\n");
            out.append(action);
            out.append("}\n");
          } 
        }  
      action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getOrgUserList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&orgName=" + rootOrgName + "&orgId=" + vo.getOrgId() + "\";\n";
      out.append("if(a==" + vo.getOrgId() + "){\n");
      out.append(action);
      out.append("}\n");
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private List getPrivateList(String userId, String domainId) {
    List privateList = null;
    try {
      ManagerService mbean = new ManagerService();
      privateList = mbean.getPrivateList(userId, domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private List getPublicList(String userId, String domainId) {
    List privateList = null;
    try {
      ManagerService mbean = new ManagerService();
      privateList = mbean.getPublicList(userId, domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private String getPrivatePersonBufferString(String userId, String domainId) {
    StringBuffer out = new StringBuffer("");
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'个人联系人','','','');");
      List<PersonClassPO> list = getPrivateList(userId, domainId);
      int i;
      for (i = 0; i < list.size(); i++) {
        PersonClassPO pcp = list.get(i);
        out.append("d.add(");
        out.append(String.valueOf(pcp.getId()) + ",");
        out.append("0,'");
        out.append(String.valueOf(pcp.getClassName()) + "',");
        out.append("'javascript:submit(" + pcp.getId() + ");','");
        out.append(String.valueOf(pcp.getClassName()) + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp.getId() + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        PersonClassPO pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getPPUserList&type=0&func=" + this.func + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&className=" + pcp.getClassName() + "&classId=" + pcp.getId() + "\";\n";
        out.append("if(a==" + pcp.getId() + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private String getPublicPersonBufferString(String userId, String domainId) {
    StringBuffer out = new StringBuffer("");
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'公共联系人','','','');");
      List<PersonClassPO> list = getPublicList(userId, domainId);
      int i;
      for (i = 0; i < list.size(); i++) {
        PersonClassPO pcp = list.get(i);
        out.append("d.add(");
        out.append(String.valueOf(pcp.getId()) + ",");
        out.append("0,'");
        out.append(String.valueOf(pcp.getClassName()) + "',");
        out.append("'javascript:submit(" + pcp.getId() + ");','");
        out.append(String.valueOf(pcp.getClassName()) + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp.getId() + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        PersonClassPO pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getPPUserList&type=1&func=" + this.func + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&className=" + pcp.getClassName() + "&classId=" + pcp.getId() + "\";\n";
        out.append("if(a==" + pcp.getId() + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private List getGroupList(String userId, String domainId, String orgId, String groupRange) {
    List privateList = null;
    try {
      ManagerService mbean = new ManagerService();
      privateList = mbean.getGroupList(userId, domainId, orgId, groupRange);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private List getPrivateGroupList(String userId, String domainId, String orgId, String groupRange) {
    List privateList = null;
    try {
      ManagerService mbean = new ManagerService();
      privateList = mbean.getPrivateGroupList(userId, domainId, orgId, groupRange);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return privateList;
  }
  
  private String getGroupPersonBufferString(String userId, String domainId, String orgId) {
    StringBuffer out = new StringBuffer("");
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'群组结构','','群组结构','');\n");
      String groupRange = "";
      if (this.range.indexOf("@") >= 0) {
        groupRange = this.range.substring(this.range.indexOf("@") + 1, this.range.lastIndexOf("@"));
        groupRange = groupRange.replaceAll("\\@", ",").replaceAll(",,", ",");
      } else if (this.range != null && !this.range.equals("") && !this.range.equals("*0*") && !this.range.equals("0")) {
        groupRange = "-4";
      } 
      List<GroupVO> list = getGroupList(userId, domainId, orgId, groupRange);
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          GroupVO gv = list.get(i);
          out.append("d.add(");
          out.append(gv.getGroupId() + ",");
          out.append("0,'");
          out.append(String.valueOf(gv.getGroupName()) + "',");
          out.append("'javascript:submit(" + gv.getGroupId() + ");','系统组 ");
          out.append(String.valueOf(gv.getGroupName()) + "','");
          out.append("','','',");
          if (this.orgIdString.contains("%" + gv.getGroupId() + "%")) {
            out.append("true);\n");
          } else {
            out.append("false);\n");
          } 
        }  
      List<GroupVO> privateList = getPrivateGroupList(userId, domainId, orgId, groupRange);
      if (privateList != null && privateList.size() > 0)
        for (int i = 0; i < privateList.size(); i++) {
          GroupVO gv = privateList.get(i);
          out.append("d.add(");
          out.append(gv.getGroupId() + ",");
          out.append("0,'");
          out.append(String.valueOf(gv.getGroupName()) + "',");
          out.append("'javascript:submit(" + gv.getGroupId() + ");','个人组 ");
          out.append(String.valueOf(gv.getGroupName()) + "','");
          out.append("','/jsoa/js/tree/images/menubar/pperson.gif','',");
          if (this.orgIdString.contains("%" + gv.getGroupId() + "%")) {
            out.append("true);\n");
          } else {
            out.append("false);\n");
          } 
        }  
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          GroupVO gv = list.get(i);
          String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getGroupUserList&sop=1&func=" + this.func + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&groupName=" + gv.getGroupName() + "&groupId=" + gv.getGroupId() + "\";\n";
          out.append("if(a==" + gv.getGroupId() + "){\n");
          out.append(action);
          out.append("}\n");
        }  
      if (privateList != null && privateList.size() > 0)
        for (int i = 0; i < privateList.size(); i++) {
          GroupVO gv = privateList.get(i);
          String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getGroupUserList&sop=0&func=" + this.func + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&groupName=" + gv.getGroupName() + "&groupId=" + gv.getGroupId() + "\";\n";
          out.append("if(a==" + gv.getGroupId() + "){\n");
          out.append(action);
          out.append("}\n");
        }  
      out.append("if(a==0){\n");
      String actionRoot = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getGroupUserList&range=" + groupRange + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&groupName=系统组结构&groupId=0\";\n";
      out.append(actionRoot);
      out.append("}\n");
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private List getZWList(String domainId) {
    return getZWList(domainId, "");
  }
  
  private List getZWList(String domainId, String corpId) {
    List list = null;
    try {
      ManagerService mbean = new ManagerService();
      list = mbean.getZWList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private String getZWPersonBufferString(String domainId) {
    return getZWPersonBufferString(domainId, "");
  }
  
  private String getZWPersonBufferString(String domainId, String corpId) {
    StringBuffer out = new StringBuffer("");
    String rangeTemp = this.range;
    if (rangeTemp.indexOf("*") >= 0) {
      rangeTemp = this.range.replaceAll("\\*", ",").replaceAll(",,", ",");
      rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
    } 
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'系统职务','','','');");
      List<DutyPO> list = getZWList(domainId, corpId);
      int i;
      for (i = 0; i < list.size(); i++) {
        DutyPO pcp = list.get(i);
        out.append("d.add(");
        out.append(String.valueOf(pcp.getId()) + ",");
        out.append("0,'");
        out.append(String.valueOf(pcp.getDutyName()) + "',");
        out.append("'javascript:submit(" + pcp.getId() + ");','");
        out.append(String.valueOf(pcp.getDutyName()) + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp.getId() + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        DutyPO pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getZWUserList&range=" + rangeTemp + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&className=" + pcp.getDutyName() + "&classId=" + pcp.getId() + "\";\n";
        out.append("if(a==" + pcp.getId() + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private List getGWList(String domainId) {
    return getGWList(domainId, "");
  }
  
  private List getGWList(String domainId, String corpId) {
    List list = null;
    try {
      ManagerService mbean = new ManagerService();
      list = mbean.getGWList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private String getGWPersonBufferString(String domainId) {
    return getGWPersonBufferString(domainId, "");
  }
  
  private String getGWPersonBufferString(String domainId, String corpId) {
    StringBuffer out = new StringBuffer("");
    String rangeTemp = this.range;
    if (rangeTemp.indexOf("*") >= 0) {
      rangeTemp = this.range.replaceAll("\\*", ",").replaceAll(",,", ",");
      rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
    } 
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'系统岗位','','','');");
      List<StationPO> list = getGWList(domainId, corpId);
      int i;
      for (i = 0; i < list.size(); i++) {
        StationPO pcp = list.get(i);
        out.append("d.add(");
        out.append(pcp.getId() + ",");
        out.append("0,'");
        out.append(String.valueOf(pcp.getName()) + "',");
        out.append("'javascript:submit(" + pcp.getId() + ");','");
        out.append(String.valueOf(pcp.getName()) + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp.getId() + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        StationPO pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getGWUserList&range=" + rangeTemp + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&className=" + pcp.getName() + "&classId=" + pcp.getId() + "\";\n";
        out.append("if(a==" + pcp.getId() + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private String getDownEmployeeList(String userId) {
    StringBuffer out = new StringBuffer("");
    WorkLogBD worklogBD = new WorkLogBD();
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'我的下属','','','');");
      List<Object[]> list = worklogBD.getDownEmployeeList(userId);
      int i;
      for (i = 0; i < list.size(); i++) {
        Object[] pcp = list.get(i);
        out.append("d.add(");
        out.append(pcp[0] + ",");
        out.append("0,'");
        out.append(pcp[1] + "',");
        out.append("'javascript:submit(" + pcp[0] + ");','");
        out.append(pcp[1] + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp[0] + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        Object[] pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getDownEmployeeList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&leaderName=" + pcp[1] + "&leaderId=" + pcp[0] + "\";\n";
        out.append("if(a==" + pcp[0] + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private String getCustomerList() {
    StringBuffer out = new StringBuffer("");
    CstService cs = new CstService();
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'客户类型','','','');");
      List<CstType> list = cs.getObjList(CstType.class, "1");
      int i;
      for (i = 0; i < list.size(); i++) {
        CstType pcp = list.get(i);
        out.append("d.add(");
        out.append(String.valueOf(pcp.getId()) + ",");
        out.append("0,'");
        out.append(String.valueOf(pcp.getName()) + "',");
        out.append("'javascript:submit(" + pcp.getId() + ");','");
        out.append(String.valueOf(pcp.getName()) + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp.getId() + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        CstType pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getCstList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&leaderName=" + pcp.getName() + "&leaderId=" + pcp.getId() + "\";\n";
        out.append("if(a==" + pcp.getId() + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  private String getProjectList() {
    StringBuffer out = new StringBuffer("");
    CstService cs = new CstService();
    try {
      out.append("<script language=\"javascript\" type=\"text/javascript\">\n");
      out.append("var d=new dTree('d','/jsoa/js/tree/images/menubar/')\n");
      out.append("d.config.folderLinks=true;\n");
      out.append("d.add(0,-1,'项目类型','','','');");
      List<ProType> list = cs.getObjList(ProType.class, null);
      int i;
      for (i = 0; i < list.size(); i++) {
        ProType pcp = list.get(i);
        out.append("d.add(");
        out.append(String.valueOf(pcp.getId()) + ",");
        out.append("0,'");
        out.append(String.valueOf(pcp.getName()) + "',");
        out.append("'javascript:submit(" + pcp.getId() + ");','");
        out.append(String.valueOf(pcp.getName()) + "','");
        out.append("','','',");
        if (this.orgIdString.contains("%" + pcp.getId() + "%")) {
          out.append("true);\n");
        } else {
          out.append("false);\n");
        } 
      } 
      out.append("document.getElementById('treearea').innerHTML = d;\n");
      out.append("function submit(a){\n");
      for (i = 0; i < list.size(); i++) {
        ProType pcp = list.get(i);
        String action = "orgTreeBar.action=\"/jsoa/selectObj.do?method=getProList&range=" + this.range + "&allowId=" + this.allowId + "&type=" + this.type + "&orgInputType=" + this.orgInputType + "&inputType=" + this.inputType + "&condition=" + this.condition + "&leaderName=" + pcp.getName() + "&leaderId=" + pcp.getId() + "\";\n";
        out.append("if(a==" + pcp.getId() + "){\n");
        out.append(action);
        out.append("}\n");
      } 
      out.append("orgTreeBar.target='" + this.target + "';\n");
      out.append("orgTreeBar.submit();\n");
      out.append("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  public String getFunc() {
    return this.func;
  }
  
  public void setFunc(String func) {
    this.func = func;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getInputType() {
    return this.inputType;
  }
  
  public void setInputType(String inputType) {
    this.inputType = inputType;
  }
  
  public String getAllowId() {
    return this.allowId;
  }
  
  public void setAllowId(String allowId) {
    this.allowId = allowId;
  }
  
  public String getDisplay() {
    return this.display;
  }
  
  public void setDisplay(String display) {
    this.display = display;
  }
  
  public String getOrgInputType() {
    return this.orgInputType;
  }
  
  public void setOrgInputType(String orgInputType) {
    this.orgInputType = orgInputType;
  }
  
  public String getCondition() {
    return this.condition;
  }
  
  public void setCondition(String condition) {
    this.condition = condition;
  }
  
  public String getTarget() {
    return this.target;
  }
  
  public void setTarget(String target) {
    this.target = target;
  }
  
  public String getRange() {
    return this.range;
  }
  
  public void setRange(String range) {
    this.range = range;
  }
  
  public String getOrgIdString() {
    return this.orgIdString;
  }
  
  public void setOrgIdString(String orgIdString) {
    this.orgIdString = orgIdString;
  }
}
