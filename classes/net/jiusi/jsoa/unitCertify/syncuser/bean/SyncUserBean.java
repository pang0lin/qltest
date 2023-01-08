package net.jiusi.jsoa.unitCertify.syncuser.bean;

import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.oacollect.po.OaCollect;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SyncUserBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveOATaskCollect(OaCollect oaCollect, HttpServletRequest request) throws Exception {
    Long collectId = null;
    HttpSession httpSession = request.getSession(true);
    String userName = httpSession.getAttribute("userName").toString();
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    DataSourceBase dataBase = new DataSourceBase();
    begin();
    try {
      collectId = (Long)this.session.save(oaCollect);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return collectId;
  }
  
  public String updateEmpInfo(Integer empId, EmployeeVO employeeVO) throws Exception {
    String re = "0";
    begin();
    try {
      EmployeeVO employeeOld = (EmployeeVO)this.session.get(EmployeeVO.class, Long.valueOf(empId.intValue()));
      employeeOld.setEmpName(employeeVO.getEmpName());
      employeeOld.setEmpBirth(employeeVO.getEmpBirth());
      employeeOld.setEmpMobilePhone(employeeVO.getEmpMobilePhone());
      employeeOld.setEmpAddress(employeeVO.getEmpAddress());
      employeeOld.setEmpIdCard(employeeVO.getEmpIdCard());
      employeeOld.setUserAccounts(employeeVO.getUserAccounts());
      employeeOld.setEmpEmail(employeeVO.getEmpEmail());
      employeeOld.setEmpSex(employeeVO.getEmpSex());
      employeeOld.setUserIsDeleted((byte)0);
      employeeOld.setUserIsActive((byte)1);
      System.out.println("-------------------------------用户：" + employeeVO.getEmpName() + "同步成功！");
      this.session.update(employeeOld);
      this.session.flush();
    } catch (Exception e) {
      re = "1";
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public String updateBySql(String sql) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res.toString();
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListByYourSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
}
