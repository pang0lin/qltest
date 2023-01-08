package com.js.oa.hr.finance.service;

import com.js.oa.hr.finance.action.FUserActionForm;
import com.js.oa.hr.finance.bean.FUserEJBBean;
import com.js.oa.hr.finance.po.FUser;
import com.js.util.util.MD5;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class FUserService {
  public int updatePass(HttpServletRequest request, FUserActionForm form) {
    int re = 1;
    try {
      String fNewPass = form.getfNewPass();
      String foldPass = form.getfOldPass();
      MD5 md5 = new MD5();
      foldPass = md5.getMD5Code(foldPass);
      fNewPass = md5.getMD5Code(fNewPass);
      String sql = "select po.id,po.empId,po.userPassword from com.js.oa.hr.finance.po.FUser po where po.empId=" + form.getEmpId();
      FUserEJBBean fUserEJBBean = new FUserEJBBean();
      List<Object[]> tempList = fUserEJBBean.getListByYourSQL(sql);
      if (tempList != null && tempList.size() > 0) {
        Object[] obj = tempList.get(0);
        if ((new MD5()).equals(obj[2].toString(), foldPass)) {
          sql = "update f_user set user_password='" + fNewPass + "' where emp_id=" + form.getEmpId();
          if (!fUserEJBBean.updateByYourYuanShengSql(sql))
            re = 3; 
        } else {
          re = 2;
        } 
      } else {
        FUser po = new FUser();
        po.setId(form.getId());
        po.setUserAccounts(form.getUserAccounts());
        po.setUserPassword(fNewPass);
        po.setEmpId(form.getEmpId());
        po.setEmpName(form.getEmpName());
        po.setEmpNumber(form.getEmpNumber());
        po.setOrgId(form.getOrgId());
        po.setOrgName(form.getOrgName());
        po.setCreatedDate(new Date());
        Long id = fUserEJBBean.add(po);
        if (id == null)
          re = 3; 
      } 
    } catch (Exception e) {
      re = 0;
      e.printStackTrace();
    } 
    return re;
  }
  
  public List getListUsers(HttpServletRequest request) {
    List listUsers = null;
    String hql = "";
    String content = request.getParameter("empName");
    try {
      if (!"".equals(content) && content != null) {
        hql = "select po.id,po.empId,po.empName,po.orgName from com.js.oa.hr.finance.po.FUser po  where po.empName like '%" + content + "%'  ";
      } else {
        hql = "select po.id,po.empId,po.empName,po.orgName from com.js.oa.hr.finance.po.FUser po ";
      } 
      FUserEJBBean fUserEJBBean = new FUserEJBBean();
      listUsers = fUserEJBBean.getListUsers(hql);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return listUsers;
  }
  
  public boolean updateResetPwd(HttpServletRequest request) {
    String password = "111111";
    String mdPass = "";
    String pwd = request.getParameter("fNewPass");
    if (!"".equals(pwd) && pwd != null)
      password = pwd; 
    String id = request.getParameter("id");
    boolean flag = false;
    try {
      MD5 md5 = new MD5();
      mdPass = md5.getMD5Code(password);
      String sql = "update f_user set user_password='" + mdPass + "' where id='" + id + "' ";
      FUserEJBBean fUserEJBBean = new FUserEJBBean();
      flag = fUserEJBBean.updateResetPwd(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
}
