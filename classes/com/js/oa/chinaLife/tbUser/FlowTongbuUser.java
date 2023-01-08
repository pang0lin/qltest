package com.js.oa.chinaLife.tbUser;

import com.js.oa.chinaLife.kemi.KemiOrg;
import com.js.oa.chinaLife.kemi.KemiUser;
import com.js.oa.chinaLife.kemi.UserTongBu;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlowTongbuUser {
  public boolean kemiUser(String recordId) {
    boolean returnValue = false;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("SELECT RS_RZXX_BM,RS_RZXX_CS,RS_RZXX_GH,RS_RZXX_XM,RS_RZXX_RSSJ,RS_RZXX_XB FROM RS_RZXX WHERE rs_rzxx_id=" + 
          recordId);
      if (rs.next()) {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        UserTongBu tb = new UserTongBu();
        KemiUser user = new KemiUser();
        user.setOrgId("1");
        user.setRegCode("");
        user.setCardCode("");
        user.setIdCard("");
        user.setStatusID("1");
        user.setDimissionDate("");
        String bumen = (rs.getString(1) == null) ? "" : rs.getString(1);
        String chushi = (rs.getString(2) == null) ? "" : rs.getString(2);
        String gh = (rs.getString(3) == null) ? "" : rs.getString(3);
        String name = (rs.getString(4) == null) ? "" : rs.getString(4);
        if (name.contains(";"))
          name = name.split(";")[0]; 
        String date = String.valueOf((rs.getString(5) == null) ? ymd.format(new Date()) : rs.getString(5)) + " 00:00:00";
        String sex = (rs.getString(6) == null) ? "1" : (rs.getString(6).equals("0") ? "1" : "2");
        if (chushi.equals("")) {
          user.setDeptName(bumen);
        } else {
          user.setDeptName(chushi);
        } 
        user.setEmpCode(gh);
        user.setEmpName(name);
        user.setJoinDate(date);
        user.setSex(sex);
        if (!user.getDeptName().equals("") && !"".equals(user.getEmpName()) && !"".equals(user.getEmpCode()))
          returnValue = tb.tongBuUser(user); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return returnValue;
  }
  
  public static void keMi() {
    UserTongBu tb = new UserTongBu();
    String orgSql = "SELECT o1.org_id,o1.orgName,o1.orgParentorgId,o2.orgName FROM org_organization o1 JOIN org_organization o2 ON o1.orgparentorgid=o2.org_id WHERE o1.orgParentorgId>-1";
    String sql = "SELECT o.Orgname,e.empNumber,e.Empname,CASE WHEN e.intoCompanyDate IS NULL THEN (to_date('2014-08-01','yyyy-mm-dd')) ELSE e.intoCompanyDate END ,e.empsex FROM org_employee e JOIN org_organization_user ou ON e.emp_id=ou.emp_id JOIN org_organization o ON o.org_id=ou.org_id WHERE e.empNumber IS NOT NULL AND e.userisactive=1 AND e.userisdeleted=0 ";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(orgSql);
      while (rs.next()) {
        KemiOrg org = new KemiOrg();
        org.setOrgID("1");
        org.setName(rs.getString(2));
        org.setCode(rs.getString(1));
        if ("0".equals(rs.getString(3))) {
          org.setPcode("000001");
        } else {
          org.setPcode(rs.getString(3));
        } 
        org.setPname(rs.getString(4));
        tb.tongBuOrg(org);
      } 
      rs.close();
      ResultSet rs2 = base.executeQuery(sql);
      while (rs2.next()) {
        KemiUser user = new KemiUser();
        user.setOrgId("1");
        user.setRegCode("");
        user.setCardCode("");
        user.setIdCard("");
        user.setStatusID("1");
        user.setDimissionDate("");
        user.setDeptName((rs2.getString(1) == null) ? "" : rs2.getString(1));
        user.setEmpCode((rs2.getString(2) == null) ? "" : rs2.getString(2));
        user.setEmpName((rs2.getString(3) == null) ? "" : rs2.getString(3));
        user.setJoinDate((rs2.getString(4) == null) ? "" : rs2.getString(4));
        user.setSex((rs2.getString(5) == null) ? "" : rs2.getString(5));
        tb.tongBuUser(user);
      } 
      rs2.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
}
