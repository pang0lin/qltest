package com.js.oa.form.jiusi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OALicenseWorkFlow extends Workflow {
  public void CompleteToLicense(HttpServletRequest request) {
    complete(request);
    DataSourceBase dbase = new DataSourceBase();
    try {
      HttpSession session = request.getSession();
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrg = session.getAttribute("orgId").toString();
      String orgId = request.getParameter("jsf_emp_org_Id");
      String info_Id = request.getParameter("Info_Id");
      String applyUserId = "", applyUserOrgId = "", applyUser = "";
      String applyTime = "";
      String cusName = "";
      String licDate = "";
      String cusDetail = "";
      String nowStr = DateHelper.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
      dbase.begin();
      String id = dbase.getTableId();
      StringBuffer sql = (new StringBuffer("select jst_3256_owner,jst_3256_org,jst_3256_f4008,jst_3256_f4010,jst_3256_f4014,jst_3256_f5228 from jst_3256 where jst_3256_id=")).append(info_Id);
      ResultSet rs = dbase.executeQuery(sql.toString());
      if (rs.next()) {
        applyUserId = rs.getString(1);
        applyUserOrgId = rs.getString(2);
        applyUser = rs.getString(3);
        applyTime = rs.getString(4);
        cusName = rs.getString(5);
        licDate = rs.getString(6);
      } 
      rs.close();
      sql = sql.delete(0, sql.length());
      sql.append("insert into cus_license(cus_license_id,cus_license_owner,cus_license_org,cus_license_date,lic_applyuser,lic_applydate,cus_name,lic_long,lic_enddate,cus_detail) values(")
        .append(id).append(",")
        .append(applyUserId).append(",")
        .append(applyUserOrgId).append(",'")
        .append(nowStr).append("','")
        .append(applyUser).append("','")
        .append(applyTime).append("','")
        .append(cusName).append("','")
        .append("0").append("','")
        .append(licDate).append("','")
        .append(cusDetail).append("')");
      dbase.executeUpdate(sql.toString());
      dbase.end();
      System.out.println("流程结束");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
