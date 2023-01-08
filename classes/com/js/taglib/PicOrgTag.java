package com.js.taglib;

import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.vo.organizationmanager.OrganizationVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PicOrgTag extends TagSupport {
  private static final long serialVersionUID = 1L;
  
  private String getList(String domainId) {
    String a = "";
    try {
      ManagerService mbean = new ManagerService();
      EmployeeBD empBD = new EmployeeBD();
      List<OrganizationVO> orgList = mbean.getOrgList(domainId, "", "0");
      Map<Object, Object> map = null;
      int count = 0;
      int totalCount = 0;
      if (orgList != null && orgList.size() > 0) {
        map = new HashMap<Object, Object>();
        map.clear();
        for (int i = 0; i < orgList.size(); i++) {
          OrganizationVO org = orgList.get(i);
          count = empBD.containUsersCount(org.getOrgId().toString()).intValue();
          map.put(org.getOrgId(), Integer.valueOf(count));
          if (org.getOrgLevel() + 1 == 1)
            totalCount += count; 
        } 
        map.put("COUNT_NUM_INT", Integer.valueOf(totalCount));
      } 
      a = outPrintStr(mbean.getParentOrgList(domainId), orgList, map, mbean.getRootOrg(domainId));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return a;
  }
  
  private String outPrintStr(List parentList, List<OrganizationVO> orgList, Map map, List<OrganizationVO> rootOrg) {
    StringBuffer out = new StringBuffer();
    try {
      OrganizationVO root = rootOrg.get(0);
      out.append("<script language=\"javascript\">\n");
      out.append("var root=new OrgNode();\n");
      out.append("root.Text=\"" + root.getOrgName() + "\";\n");
      out.append("root.Description=\"" + root.getOrgName() + "\";\n");
      out.append("root.empInOrgNum=\"" + map.get("COUNT_NUM_INT") + "\";\n");
      out.append("</script>\n");
      for (int j = 0; j < orgList.size(); j++) {
        OrganizationVO org = orgList.get(j);
        long parentTempId = org.getOrgParentOrgId();
        out.append("<script language=\"javascript\">\n");
        out.append("var org_" + org.getOrgId() + "=new OrgNode();\n");
        out.append("    org_" + org.getOrgId() + ".Text=\"" + org.getOrgName() + "\";\n");
        out.append("    org_" + org.getOrgId() + ".Description=\"" + org.getOrgName() + "\";\n");
        out.append("    org_" + org.getOrgId() + ".empInOrgNum=\"" + map.get(org.getOrgId()) + "\";\n");
        if (parentTempId == Long.parseLong("0")) {
          out.append("root.Nodes.Add(org_" + org.getOrgId() + ");\n");
        } else {
          out.append("if(typeof(org_" + parentTempId + ")!='undefined'){\n");
          out.append("org_" + org.getOrgParentOrgId() + ".Nodes.Add(org_" + org.getOrgId() + ");\n");
          out.append("};\n");
        } 
        out.append("</script>\n");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return out.toString();
  }
  
  public int doStartTag() throws JspException {
    try {
      String domainId = (this.pageContext.getSession().getAttribute("domainId") == null) ? "0" : this.pageContext.getSession().getAttribute("domainId").toString();
      this.pageContext.getOut().write(getList(domainId));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return 1;
  }
  
  public int doEndTag() throws JspException {
    try {
      StringBuffer out = new StringBuffer();
      out.append("<script language=\"javascript\">\n");
      out.append("var OrgShows=new OrgShow(root);\n");
      out.append("OrgShows.Run();\n");
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
}
