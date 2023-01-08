package com.js.system.manager.action;

import com.js.system.manager.service.ManagerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SearchUserAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    SearchUserForm searchUserForm = (SearchUserForm)actionForm;
    String userCName = searchUserForm.getUserCName();
    String userEName = searchUserForm.getUserEName();
    String userOrganization = searchUserForm.getUserOrganization();
    String show = httpServletRequest.getParameter("show");
    String email = httpServletRequest.getParameter("email");
    ManagerService managerBD = new ManagerService();
    StringBuffer sql = new StringBuffer();
    List userList = null;
    if (!"privatePerson".equals(show) && !"publicPerson".equals(show)) {
      sql.append(" WHERE user.userIsActive=1 and user.userIsDeleted=0 ");
      if (userOrganization != null && !"".equals(userOrganization))
        sql.append(" and organization.orgNameString LIKE '%").append(userOrganization).append("%'"); 
      if (userCName != null && !"".equals(userCName))
        sql.append(" and user.empName LIKE  '%").append(userCName).append("%'"); 
      if (userEName != null && !"".equals(userEName))
        sql.append(" and user.empEnglishName LIKE '%").append(userEName).append("%'"); 
      if (email != null && !email.equals("null") && !email.equals("")) {
        sql.append("  and (user.empEmail <>' ' and user.empEmail is not null) ");
        userList = managerBD.getUserList("user.empId,user.empName,user.empEnglishName,user.empSex,organization.orgName", 
            " com.js.system.vo.usermanager.EmployeeVO AS user JOIN user.organizations organization", 
            sql.toString());
        httpServletRequest.setAttribute("email", email);
      } else {
        userList = managerBD.getUserList("user.empId,user.empName,user.empEnglishName,user.empSex,organization.orgName", 
            " com.js.system.vo.usermanager.EmployeeVO AS user JOIN user.organizations organization", 
            
            sql.toString());
      } 
    } else {
      if ("privatePerson".equals(show))
        sql.append(" WHERE personClassPO.classType=0 "); 
      if ("publicPerson".equals(show))
        sql.append(" WHERE personClassPO.classType=1 "); 
      if (userCName != null && !"".equals(userCName))
        sql.append(" and personPO.linkManName LIKE  '%").append(userCName).append("%'"); 
      if (userEName != null && !"".equals(userEName))
        sql.append(" and personPO.linkManEnName LIKE '%").append(userEName).append("%'"); 
      userList = managerBD.getUserList("personPO.id,personPO.linkManName,personPO.linkManEnName,personPO.linkManSex,personClassPO.className", 
          " com.js.oa.personalwork.person.po.PersonPO personPO join personPO.linkManClass personClassPO", 
          sql.toString());
    } 
    httpServletRequest.setAttribute("userList", userList);
    httpServletRequest.setAttribute("single", httpServletRequest.getParameter("single"));
    httpServletRequest.setAttribute("search", "yes");
    return actionMapping.findForward("success");
  }
}
