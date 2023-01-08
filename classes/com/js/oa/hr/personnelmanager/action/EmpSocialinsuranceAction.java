package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO;
import com.js.oa.hr.personnelmanager.service.EmpSocialinsuranceBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmpSocialinsuranceAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    EmpSocialinsuranceActionForm form = (EmpSocialinsuranceActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    if ("new".equals(action)) {
      httpServletRequest.setAttribute("rang", getManagerRange(session));
      action = "add";
    } 
    if ("save".equals(action)) {
      save(form, httpServletRequest);
      httpServletRequest.setAttribute("saveType", 
          httpServletRequest
          .getParameter("saveType"));
      action = "add";
    } 
    if ("load".equals(action) || "view".equals(action)) {
      httpServletRequest.setAttribute("rang", getManagerRange(session));
      load(form, httpServletRequest);
      httpServletRequest.setAttribute("action", action);
      action = "modify";
    } 
    if ("update".equals(action)) {
      update(form, httpServletRequest);
      httpServletRequest.setAttribute("saveType", 
          httpServletRequest
          .getParameter("saveType"));
      action = "modify";
    } 
    if ("delete".equals(action)) {
      (new EmpSocialinsuranceBD()).delete(new Long(httpServletRequest.getParameter(
              "id")));
      action = "list";
    } 
    if ("batchDel".equals(action)) {
      (new EmpSocialinsuranceBD()).batchDel(httpServletRequest.getParameter("ids"));
      action = "list";
    } 
    if ("list".equals(action))
      list(httpServletRequest); 
    if ("listCard".equals(action))
      listCard(httpServletRequest); 
    if ("export".equals(action))
      export(httpServletRequest); 
    return actionMapping.findForward(action);
  }
  
  private void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String empName = request.getParameter("empName");
    String payType = request.getParameter("payType");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*04", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.payMonth,po.payType,po.payBase,po.stopMonth,po.memos,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(payType)) {
      whereSQL = String.valueOf(whereSQL) + " and po.payType like '%" + payType + "%'";
      request.setAttribute("payType", payType);
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.payMonth desc ";
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      request.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,empName,payType");
  }
  
  private void export(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String empName = request.getParameter("empName");
    String payType = request.getParameter("payType");
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*04", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.payMonth,po.payType,po.payBase,po.stopMonth,po.memos,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    if (!isEmpty(empName)) {
      whereSQL = String.valueOf(whereSQL) + " and ppo.empName like '%" + empName + "%'";
      request.setAttribute("empName", empName);
    } 
    if (!isEmpty(payType)) {
      whereSQL = String.valueOf(whereSQL) + " and po.payType like '%" + payType + "%'";
      request.setAttribute("payType", payType);
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.payMonth desc ";
    int pageSize = 999999;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      request.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,empName,payType");
  }
  
  private void listCard(HttpServletRequest request) {
    String viewSQL = 
      "po.id,po.payMonth,po.payType,po.payBase,po.stopMonth,po.memos,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    whereSQL = String.valueOf(whereSQL) + " and ppo.empId = " + request.getParameter("empID");
    whereSQL = String.valueOf(whereSQL) + " order by po.payMonth desc ";
    int pageSize = 999999;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmpSocialinsuranceActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EmpSocialinsurancePO po = new EmpSocialinsurancePO();
    EmployeeVO emp = new EmployeeVO();
    emp.setEmpId(Long.parseLong(form.getEmpID()));
    po.setEmp(emp);
    UserBD ubd = new UserBD();
    List<Object[]> list = ubd.getUserInfo(new Long(form.getEmpID()));
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      po.setEmpOrg((obj[8] != null) ? new Long(obj[8].toString()) : null);
    } 
    po.setPayMonth(form.getPayMonth());
    po.setPayType(form.getPayType());
    if (form.getPayBase() != null && !"".equals(form.getPayBase()))
      po.setPayBase(new Double(form.getPayBase())); 
    po.setStopMonth(form.getStopMonth());
    po.setMemos(form.getMemos());
    retflag = (new EmpSocialinsuranceBD()).save(po).booleanValue();
    return retflag;
  }
  
  private boolean update(EmpSocialinsuranceActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EmpSocialinsurancePO po = new EmpSocialinsurancePO();
    po.setId(form.getId());
    EmployeeVO emp = new EmployeeVO();
    emp.setEmpId(Long.parseLong(form.getEmpID()));
    po.setEmp(emp);
    UserBD ubd = new UserBD();
    List<Object[]> list = ubd.getUserInfo(new Long(form.getEmpID()));
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      po.setEmpOrg((obj[8] != null) ? new Long(obj[8].toString()) : null);
    } 
    po.setPayMonth(form.getPayMonth());
    po.setPayType(form.getPayType());
    if (form.getPayBase() != null && !"".equals(form.getPayBase()))
      po.setPayBase(new Double(form.getPayBase())); 
    po.setStopMonth(form.getStopMonth());
    po.setMemos(form.getMemos());
    retflag = (new EmpSocialinsuranceBD()).modify(po).booleanValue();
    return retflag;
  }
  
  private void load(EmpSocialinsuranceActionForm form, HttpServletRequest request) {
    EmpSocialinsurancePO po = (new EmpSocialinsuranceBD()).load(new Long(request.getParameter(
            "id")));
    form.setEmpID((new StringBuilder(String.valueOf(po.getEmp().getEmpId()))).toString());
    form.setEmpName(po.getEmp().getEmpName());
    form.setPayMonth(po.getPayMonth());
    form.setPayType(po.getPayType());
    if (po.getPayBase() != null)
      form.setPayBase((String)po.getPayBase()); 
    form.setStopMonth(po.getStopMonth());
    form.setMemos(po.getMemos());
  }
  
  private String getManagerRange(HttpSession session) {
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    List<Object[]> rightlist = (new ManagerService()).getRightScope(session
        .getAttribute("userId").toString(), "07*55*04");
    if (rightlist == null || rightlist.size() == 0) {
      managerScope = "*AAAA*";
    } else {
      Object[] obj = rightlist.get(
          0);
      String type = obj[0].toString();
      if ("4".equals(type)) {
        if (obj[1] != null)
          managerScope = obj[1].toString(); 
      } else if ("1".equals(type)) {
        managerScope = "*AAAA*";
      } else if ("0".equals(type)) {
        managerScope = "*0*";
      } 
    } 
    return managerScope;
  }
  
  protected boolean isEmpty(Object o) {
    if (o == null)
      return true; 
    String s = null;
    if (!(o instanceof String)) {
      s = o.toString();
    } else {
      s = (String)o;
    } 
    if (s == null || s.trim().length() == 0 || "null".equals(s))
      return true; 
    return false;
  }
}
