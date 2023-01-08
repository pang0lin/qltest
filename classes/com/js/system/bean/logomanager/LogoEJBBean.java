package com.js.system.bean.logomanager;

import com.js.system.vo.logomanager.LogoVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class LogoEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getLogoList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from LogoVO logoVO where logoVO.orgId='0' order by logoVO.bakString1");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public LogoVO getLogo(String orgId) throws Exception {
    begin();
    List<LogoVO> list = null;
    LogoVO logoVO = null;
    try {
      Query query = this.session.createQuery("from LogoVO lv where lv.orgId='" + orgId + "' or lv.orgId='0' and lv.logoType='1' order by lv.logoId,lv.bakString1");
      list = query.list();
      if (list.size() > 1) {
        logoVO = list.get(1);
      } else {
        logoVO = list.get(0);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return logoVO;
  }
  
  public List getBranchLogo(String orgId) throws Exception {
    begin();
    List<LogoVO> list = null;
    List<LogoVO> logoList = new ArrayList();
    try {
      Query query = this.session.createQuery("from LogoVO lv where lv.orgId='" + orgId + "' or lv.orgId='0' and lv.logoType='1' order by lv.bakString1");
      list = query.list();
      if (list.size() == 1) {
        LogoVO logoVO = list.get(0);
        logoVO.setOrgId(orgId);
        addyLogo(logoVO);
        list.clear();
        logoList.add(logoVO);
      } else {
        for (int i = 0; i < list.size(); i++) {
          LogoVO logoVO = list.get(i);
          if (logoVO.getOrgId().equals(orgId))
            logoList.add(logoVO); 
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return logoList;
  }
  
  public List getAllLogo() throws Exception {
    DataSourceBase dataSourceBase = new DataSourceBase();
    List<String[]> list = new ArrayList();
    ResultSet rs = null;
    String ids = "-2";
    try {
      dataSourceBase.begin();
      String sql = "select l.logo_id,l.logo_path,l.orgId,o.orgname,l.BAK_STRING1,l.company_color,l.isdisplay_company_name,l.isdisplay_logo,l.company_name,l.logo_name from org_logo l left join org_organization o on l.orgId=o.org_id where l.orgId != '0' order by l.logo_id";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        String[] logo = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
            rs.getString(6), rs.getString(7).equals("0") ? "不显示" : "显示", rs.getString(8).equals("0") ? "不显示" : "显示", 
            rs.getString(9), rs.getString(10) };
        ids = String.valueOf(ids) + "," + rs.getString(3);
        list.add(logo);
      } 
      rs.close();
      sql = "select l.logo_id,l.logo_path,o.org_id,o.orgname,l.BAK_STRING1,l.company_color,l.isdisplay_company_name,l.isdisplay_logo,l.company_name,l.logo_name  from org_logo l,org_organization o where o.orgparentorgid=0 and o.orgstatus=0 and l.logotype='1' and l.orgId='0' and o.org_id not in (" + 

        
        ids + ")";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        String[] logo = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
            rs.getString(6), rs.getString(7).equals("0") ? "不显示" : "显示", rs.getString(8).equals("0") ? "不显示" : "显示", 
            rs.getString(9), rs.getString(10) };
        list.add(logo);
      } 
      rs.close();
    } catch (Exception e) {
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      dataSourceBase.end();
    } 
    return list;
  }
  
  public void modifyLogo(LogoVO logoVO) throws Exception {
    begin();
    try {
      this.session.update(logoVO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public LogoVO loadLogo(String logoId) throws Exception {
    begin();
    LogoVO logoVO = new LogoVO();
    try {
      Query query = this.session.createQuery("from LogoVO lv where lv.logoId=" + logoId);
      logoVO = query.list().get(0);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return logoVO;
  }
  
  public void addyLogo(LogoVO logoVO) throws Exception {
    begin();
    try {
      this.session.save(logoVO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
}
