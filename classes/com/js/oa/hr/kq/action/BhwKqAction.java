package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.pojo.BhwKqItem;
import com.js.oa.userdb.util.DbOpt;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BhwKqAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ParseException {
    String action = request.getParameter("action");
    String empId = request.getSession(true).getAttribute("userId").toString();
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String orgIds = request.getParameter("orgIds");
    String isPersonal = request.getParameter("isPersonal");
    if (isPersonal == null || "null".equalsIgnoreCase(isPersonal))
      isPersonal = "0"; 
    request.setAttribute("ispersonal", isPersonal);
    if (startDate == null) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      Calendar c = Calendar.getInstance();
      c.add(2, 0);
      c.set(5, 1);
      startDate = simpleDateFormat.format(c.getTime());
      Calendar ca = Calendar.getInstance();
      ca.set(5, ca.getActualMaximum(5));
      endDate = simpleDateFormat.format(ca.getTime());
    } else {
      String[] startDates = startDate.split("/");
      startDate = String.valueOf(startDates[0]) + "/" + ("0" + startDates[1]).substring(("0" + startDates[1]).length() - 2) + "/" + ("0" + startDates[2]).substring(("0" + startDates[2]).length() - 2);
      String[] endDates = endDate.split("/");
      endDate = String.valueOf(endDates[0]) + "/" + ("0" + endDates[1]).substring(("0" + endDates[1]).length() - 2) + "/" + ("0" + endDates[2]).substring(("0" + endDates[2]).length() - 2);
    } 
    System.out.println("查询时间：" + startDate + "~" + endDate);
    request.setAttribute("startdate", startDate);
    request.setAttribute("enddate", endDate);
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    endDate = format.format(getDate(new Date(endDate)));
    String dateWhere = " to_date(attdate, 'yyyy-MM-dd') between to_date('" + startDate + "', 'yyyy-MM-dd') and to_date('" + endDate + "', 'yyyy-MM-dd') ";
    String rightWhere = " ";
    if ("1".equals(isPersonal)) {
      rightWhere = " and personnumber=(select empnumber from org_employee where emp_id=" + empId + ")";
    } else {
      rightWhere = getRightSql(empId);
    } 
    if (orgIds != null && !"".equals(orgIds))
      rightWhere = String.valueOf(rightWhere) + " and personnumber in (select empnumber from org_employee where emp_id in(select emp_id from org_organization_user where org_id=" + orgIds + ")) "; 
    if ("cqqk".equals(action)) {
      DbOpt dbLocal = new DbOpt();
      try {
        String[] header = dbLocal.executeQueryToStrArr1("select distinct attdate att from bhw_cqqk where " + dateWhere + rightWhere + " order by attdate");
        request.setAttribute("header", header);
        if (header != null) {
          List<BhwKqItem> list = new ArrayList<BhwKqItem>();
          String[] ghlist = dbLocal.executeQueryToStrArr1("select distinct personnumber from bhw_cqqk where " + dateWhere + rightWhere + " order by personnumber");
          if (ghlist != null && ghlist.length > 0)
            for (int i = 0; i < ghlist.length; i++) {
              String strSql = "SELECT orgname,empnumber,empname FROM org_employee emp, org_organization_user orguser, org_organization org WHERE emp.emp_id=orguser.emp_id AND orguser.org_id=org.org_id AND empnumber='" + 
                ghlist[i] + "'";
              String[][] info = dbLocal.executeQueryToStrArr2(strSql, 3);
              System.out.println("出勤情况：" + strSql);
              if (info != null && info.length >= 1) {
                BhwKqItem item = new BhwKqItem();
                item.setDeptName(info[0][0]);
                item.setEmpNumber(info[0][1]);
                item.setName(info[0][2]);
                String[] kqqk = new String[header.length];
                for (int j = 0; j < header.length; j++) {
                  String[] kk = dbLocal.executeQueryToStrArr1("select attresult from bhw_cqqk where AttDate='" + header[j] + "' and personnumber='" + ghlist[i] + "'");
                  if (kk == null || "null".equals(kk) || kk.length == 0) {
                    kqqk[j] = "&nbsp;";
                  } else {
                    for (int k = 0; k < kk.length; k++) {
                      if (k == 0) {
                        kqqk[j] = getAttResult(kk[k]);
                      } else {
                        kqqk[j] = String.valueOf(kqqk[j]) + "、" + getAttResult(kk[k]);
                      } 
                    } 
                  } 
                } 
                item.setCqqk(kqqk);
                list.add(item);
              } 
            }  
          request.setAttribute("infoList", list);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          dbLocal.close();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } else if ("cqqkhz".equals(action)) {
      DbOpt dbLocal = new DbOpt();
      try {
        dbLocal.getStatement().executeBatch();
        String[] header = dbLocal.executeQueryToStrArr1("select distinct AttResult from bhw_cqqk where " + dateWhere + rightWhere + " order by AttResult");
        String[] headerStr = (String[])null;
        if (header != null && header.length > 0) {
          headerStr = new String[header.length];
          for (int i = 0; i < header.length; i++)
            headerStr[i] = getAttResult(header[i]); 
        } 
        request.setAttribute("header", headerStr);
        if (header != null) {
          List<BhwKqItem> list = new ArrayList<BhwKqItem>();
          String[] ghlist = dbLocal.executeQueryToStrArr1("select distinct personnumber from bhw_cqqk where " + dateWhere + rightWhere + " order by personnumber");
          if (ghlist != null && ghlist.length > 0)
            for (int i = 0; i < ghlist.length; i++) {
              String strSql = "SELECT orgname,empnumber,empname FROM org_employee emp, org_organization_user orguser, org_organization org WHERE emp.emp_id=orguser.emp_id AND orguser.org_id=org.org_id AND empnumber='" + 
                ghlist[i] + "'";
              System.out.println("出勤汇总：" + strSql);
              String[][] info = dbLocal.executeQueryToStrArr2(strSql, 3);
              if (info != null && info.length >= 1) {
                BhwKqItem item = new BhwKqItem();
                item.setDeptName(info[0][0]);
                item.setEmpNumber(info[0][1]);
                item.setName(info[0][2]);
                String[] kqqk = new String[header.length];
                for (int j = 0; j < header.length; j++) {
                  String kk = dbLocal.executeQueryToStr("select count(*) from bhw_cqqk where " + dateWhere + " and Attresult='" + header[j] + "' and personnumber='" + ghlist[i] + "'");
                  kqqk[j] = kk;
                } 
                item.setCqqk(kqqk);
                list.add(item);
              } 
            }  
          request.setAttribute("infoList", list);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          dbLocal.close();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } else if ("qdqk".equals(action)) {
      DbOpt dbLocal = new DbOpt();
      try {
        String[] header = dbLocal.executeQueryToStrArr1("select distinct AttDate from bhw_qdqk where " + dateWhere + rightWhere + " order by attDate");
        request.setAttribute("header", header);
        if (header != null) {
          List<BhwKqItem> list = new ArrayList<BhwKqItem>();
          String[] ghlist = dbLocal.executeQueryToStrArr1("select distinct personnumber from bhw_qdqk where " + dateWhere + rightWhere + " order by personnumber");
          if (ghlist != null && ghlist.length > 0)
            for (int i = 0; ghlist != null && i < ghlist.length; i++) {
              String strSql = "SELECT orgname,empnumber,empname FROM org_employee emp, org_organization_user orguser, org_organization org WHERE emp.emp_id=orguser.emp_id AND orguser.org_id=org.org_id AND empnumber='" + 
                ghlist[i] + "'";
              System.out.println("签到信息：" + strSql);
              String[][] info = dbLocal.executeQueryToStrArr2(strSql, 3);
              if (info != null && info.length >= 1) {
                BhwKqItem item = new BhwKqItem();
                item.setDeptName(info[0][0]);
                item.setEmpNumber(info[0][1]);
                item.setName(info[0][2]);
                String[] kqqk = new String[header.length * 2];
                for (int j = 0; j < header.length; j++) {
                  String[][] kk = dbLocal.executeQueryToStrArr2("select STARTTIME,ENDTIME from bhw_qdqk where AttDate='" + 
                      header[j] + "' and personnumber='" + ghlist[i] + "'");
                  if (kk == null || "null".equals(kk) || kk.length == 0) {
                    kqqk[j * 2] = "&nbsp;";
                    kqqk[j * 2 + 1] = "&nbsp;";
                  } else {
                    kqqk[j * 2] = kk[0][0];
                    kqqk[j * 2 + 1] = kk[0][1];
                  } 
                } 
                item.setCqqk(kqqk);
                list.add(item);
              } 
            }  
          request.setAttribute("infoList", list);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          dbLocal.close();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
    if (request.getParameter("export") != null) {
      request.setAttribute("export", request.getParameter("export"));
      action = "export";
    } 
    return actionMapping.findForward(action);
  }
  
  private String getAttResult(String code) {
    String result = "";
    if ("1000".equals(code)) {
      result = "正常";
    } else if ("1001".equals(code)) {
      result = "迟到";
    } else if ("1002".equals(code)) {
      result = "早退";
    } else if ("10030".equals(code)) {
      result = "事假";
    } else if ("10031".equals(code)) {
      result = "病假";
    } else if ("10032".equals(code)) {
      result = "婚假";
    } else if ("10033".equals(code)) {
      result = "年假";
    } else if ("10034".equals(code)) {
      result = "产假";
    } else if ("10035".equals(code)) {
      result = "陪产假";
    } else if ("10036".equals(code)) {
      result = "丧假";
    } else if ("10037".equals(code)) {
      result = "调休";
    } else if ("10038".equals(code)) {
      result = "其他";
    } else if ("10040".equals(code)) {
      result = "旷工半天";
    } else if ("10041".equals(code)) {
      result = "旷工一天";
    } 
    return result;
  }
  
  private Date getDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, 0);
    Date date1 = new Date(calendar.getTimeInMillis());
    return date1;
  }
  
  private String getRightSql(String empId) {
    String rightSQL = "";
    DbOpt db = new DbOpt();
    try {
      String sql = "select rightscopetype,rightscopescope,rightscopeuser,rightscopegroup from org_rightscope where right_id=(select right_id from org_right where rightcode='07*55*55') and emp_id=" + 
        empId;
      String[][] rightScope = db.executeQueryToStrArr2(sql, 4);
      if (rightScope != null && rightScope.length > 0) {
        if (!"0".equals(rightScope[0][0]))
          if ("1".equals(rightScope[0][0])) {
            rightSQL = " and personnumber=(select empnumber from org_employee where emp_id=" + empId + ") ";
          } else if ("2".equals(rightScope[0][0])) {
            rightSQL = " and personnumber in (select empnumber from org_employee where emp_id in (select emp_id from org_organization_user where org_id in (select org_id from org_organization where orgidstring like '%$'||(select org_id from org_organization_user where emp_id=" + 

              
              empId + ")||'$%')) " + 
              "and empnumber is not null) ";
          } else if ("3".equals(rightScope[0][0])) {
            rightSQL = " and personnumber in (select empnumber from org_employee where emp_id in (select emp_id from org_organization_user where org_id=（select org_id from org_organization_user where emp_id=" + 
              
              empId + "）） and empnumber is not null) ";
          } else if ("4".equals(rightScope[0][0])) {
            String rightScopes = rightScope[0][1];
            if (rightScopes.startsWith("*"))
              rightScopes = rightScopes.substring(1); 
            if (rightScopes.endsWith("*"))
              rightScopes = rightScopes.substring(0, rightScopes.length() - 1); 
            String[] ids = rightScopes.split("\\*\\*");
            rightScopes = "select org_id from org_organization where 1=2 ";
            for (int i = 0; i < ids.length; i++)
              rightScopes = String.valueOf(rightScopes) + " or orgidstring like '%$" + ids[i] + "$%' "; 
            rightSQL = " and personnumber in (select empnumber from org_employee where emp_id in (select emp_id from org_organization_user where org_id in (" + 
              rightScopes + 
              ")) and empnumber is not null) ";
          } else {
            rightSQL = " and 1=2 ";
          }  
      } else {
        rightSQL = " and 1=2 ";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return rightSQL;
  }
}
