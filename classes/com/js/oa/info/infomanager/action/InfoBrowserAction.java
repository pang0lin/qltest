package com.js.oa.info.infomanager.action;

import com.js.oa.info.infomanager.service.InformationBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.page.Page;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.Session;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoBrowserAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession httpSession = httpServletRequest.getSession(true);
    InformationBD informationBD = new InformationBD();
    String informationId = httpServletRequest.getParameter("informationId");
    String read = httpServletRequest.getParameter("read");
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String databaseType = SystemCommon.getDatabaseType();
    databaseType = (databaseType == null) ? "" : databaseType;
    String searchName = (httpServletRequest.getParameter("searchName") == null) ? "" : httpServletRequest.getParameter("searchName");
    String viewSql = "";
    String fromSql = "";
    String whereSql = "";
    if ("0".equals(read)) {
      String informationReader = "";
      String informationReaderOrg = "";
      String informationReaderGroup = "";
      String channelReader = "";
      String channelReaderOrg = "";
      String channelReaderGroup = "";
      Object[] obj = (Object[])null;
      HibernateBase hb = new HibernateBase();
      Session session = null;
      StringBuffer empSB = new StringBuffer();
      try {
        session = hb.getSession();
        Iterator<Object[]> iter = session.iterate("select info.informationReader, info.informationReaderOrg, info.informationReaderGroup  from com.js.oa.info.infomanager.po.InformationPO info   where info.informationId = " + 

            
            informationId);
        if (iter.hasNext())
          obj = iter.next(); 
        iter = session.iterate("select ib.empId from com.js.oa.info.infomanager.po.InformationBrowserPO ib join ib.information info where info.informationId=" + informationId);
        while (iter.hasNext())
          empSB.append("empVO.empId<>" + iter.next() + " and "); 
        empSB.append(" 1=1 ");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          session.close();
        } catch (Exception exception) {}
      } 
      if (obj != null) {
        informationReader = (obj[0] == null) ? "" : obj[0].toString();
        informationReaderOrg = (obj[1] == null) ? "" : obj[1].toString();
        informationReaderGroup = (obj[2] == null) ? "" : obj[2].toString();
      } 
      viewSql = "empVO.empName, orgVO.orgNameString, empVO.userAccounts,empVO.imId";
      fromSql = "com.js.system.vo.usermanager.EmployeeVO empVO join empVO.organizations orgVO";
      if (informationReader.equals("") && informationReaderOrg.equals("") && informationReaderGroup.equals("")) {
        whereSql = " where 1 > 1";
      } else {
        whereSql = " where 1=1 ";
        String tmpSql = "";
        if (!informationReader.equals("")) {
          if (informationReader.startsWith("$") && informationReader.endsWith("$"))
            informationReader = informationReader.substring(1, informationReader.length() - 1); 
          String[] tmp = new String[1];
          informationReader = informationReader.replace('$', ',');
          informationReader = informationReader.replaceAll(",,", ",");
          tmpSql = String.valueOf(tmpSql) + " empVO.empId in (" + informationReader + ") or ";
        } 
        if (!informationReaderGroup.equals("")) {
          if (informationReaderGroup.startsWith("@") && informationReaderGroup.endsWith("@"))
            informationReaderGroup = informationReaderGroup.substring(1, informationReaderGroup.length() - 1); 
          String[] tmp = new String[1];
          if (informationReaderGroup.indexOf("@@") >= 0) {
            tmp = informationReaderGroup.split("@@");
          } else {
            tmp[0] = informationReaderGroup;
          } 
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < tmp.length; i++)
            sb.append(String.valueOf(tmp[i]) + ","); 
          sb.append("-1");
          tmpSql = String.valueOf(tmpSql) + " empVO.empId in (select groupEmp.empId from com.js.system.vo.usermanager.EmployeeVO groupEmp join groupEmp.groups group2 where group2.groupId in (" + sb.toString() + ")) or ";
        } 
        if (!informationReaderOrg.equals("")) {
          informationReaderOrg = informationReaderOrg.replace('*', ',');
          if (informationReaderOrg.startsWith(",") && informationReaderOrg.endsWith(","))
            informationReaderOrg = informationReaderOrg.substring(1, informationReaderOrg.length() - 1); 
          String[] tmp = new String[1];
          if (informationReaderOrg.indexOf(",,") >= 0) {
            tmp = informationReaderOrg.split(",,");
          } else {
            tmp[0] = informationReaderOrg;
          } 
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < tmp.length; i++)
            sb.append(" orgVO.orgIdString like '%$" + tmp[i] + "$%' or "); 
          tmpSql = String.valueOf(tmpSql) + sb.toString();
        } 
        whereSql = String.valueOf(whereSql) + " and (" + tmpSql + " 1 > 1) ";
      } 
      whereSql = String.valueOf(whereSql) + " and empVO.empName like '%" + 
        searchName + "%' and " + 
        " empVO.userIsDeleted=0 and empVO.userIsActive=1 and " + empSB.toString() + " order by orgVO.orgIdString";
    } else {
      OrganizationBD organizationBD = new OrganizationBD();
      List<OrganizationVO> organizationVOList = organizationBD.selectAllParentOrganization();
      String informationTitle = "";
      if (httpServletRequest.getParameter("informationTitle") != null)
        informationTitle = URLDecoder.decode(httpServletRequest.getParameter("informationTitle"), "utf-8"); 
      httpServletRequest.setAttribute("organizationVOList", organizationVOList);
      httpServletRequest.setAttribute("informationId", informationId);
      httpServletRequest.setAttribute("informationTitle", informationTitle);
    } 
    return actionMapping.findForward("success1");
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL, String read) {
    int pageSize = 30;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("browserList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "informationId,read,searchName");
  }
}
