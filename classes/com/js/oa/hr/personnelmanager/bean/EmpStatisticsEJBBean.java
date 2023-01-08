package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class EmpStatisticsEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map listEmpChange(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    int flag = 0;
    if (databaseType.indexOf("oracle") > -1) {
      flag = 1;
    } else if (databaseType.indexOf("mysql") > -1) {
      flag = 2;
    } 
    Connection conn = null;
    Statement stmt = null;
    begin();
    try {
      String domainId = "0";
      domainId = this.session.createQuery("select emp.domainId from com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.empId=" + 
          userId).iterate().next().toString();
      Query query = this.session.createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + " and ccc.rightCode = '" + rightCode + "' and ccc.domainId=" + domainId);
      List<Object[]> list0 = query.list();
      String where = "";
      if (list0 != null && list0.size() > 0) {
        Object[] arrayOfObject = list0.get(0);
        String scopeType = arrayOfObject[0].toString();
        if ("0".equals(scopeType)) {
          where = " 1=1 ";
        } else if ("1".equals(scopeType)) {
          where = "1>1";
        } else if ("2".equals(scopeType)) {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "**" + orgId + "**";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } else if ("3".equals(scopeType)) {
          where = "c.org_id=" + orgId;
        } else {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "*" + (String)arrayOfObject[1] + "*";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } 
      } else {
        where = "1>2";
      } 
      String countsql = "";
      countsql = String.valueOf(countsql) + " select count(a.orgname) cnt ";
      countsql = String.valueOf(countsql) + " from  ";
      countsql = String.valueOf(countsql) + " ( ";
      countsql = String.valueOf(countsql) + " select c.org_id, c.ORGNAME, c.ORGLEVEL, c.ORGIDSTRING ";
      countsql = String.valueOf(countsql) + " from ORG_ORGANIZATION c ";
      countsql = String.valueOf(countsql) + " where c.ORGSTATUS = 0 ";
      countsql = String.valueOf(countsql) + "   and " + where;
      countsql = String.valueOf(countsql) + " ) a  ";
      String sql = "";
      String sumSql = "";
      if (flag == 1) {
        sql = String.valueOf(sql) + " select *  ";
        sql = String.valueOf(sql) + " from ( ";
        sql = String.valueOf(sql) + " select n.*, rownum rn ";
        sql = String.valueOf(sql) + " from  ";
        sql = String.valueOf(sql) + " ( ";
        sql = String.valueOf(sql) + 
          " select a.orgname,a0.num1,b.num2,c.num3,d.num4,e.num5,f.num6,g.num7,(nvl(h.num8,0) - nvl(g.num7,0) + nvl(f.num6,0)) num88,a.ORGLEVEL,a.ORGIDSTRING,a.org_id ";
        sumSql = String.valueOf(sumSql) + " select sum(nvl(a0.num1,0)),sum(nvl(b.num2,0)),sum(nvl(c.num3,0)),sum(nvl(d.num4,0)),sum(nvl(e.num5,0)),sum(nvl(f.num6,0)),sum(nvl(g.num7,0)),sum(nvl((nvl(h.num8,0) - nvl(g.num7,0) + nvl(f.num6,0)),0)) num88 ";
      } else if (flag == 2) {
        sql = String.valueOf(sql) + 
          " select a.orgname,a0.num1,b.num2,c.num3,d.num4,e.num5,f.num6,g.num7,ifnull(h.num8,0) - ifnull(g.num7,0) + ifnull(f.num6,0) num88,a.ORGLEVEL,a.ORGIDSTRING,a.org_id ";
        sumSql = String.valueOf(sumSql) + " select ifnull(sum(ifnull(a0.num1,0)),0),ifnull(sum(ifnull(b.num2,0)),0),ifnull(sum(ifnull(c.num3,0)),0),ifnull(sum(ifnull(d.num4,0)),0),ifnull(sum(ifnull(e.num5,0)),0),ifnull(sum(ifnull(f.num6,0)),0),ifnull(sum(ifnull(g.num7,0)),0),ifnull(sum(ifnull((ifnull(h.num8,0) - ifnull(g.num7,0) + ifnull(f.num6,0)),0)),0) num88 ";
      } else {
        sql = String.valueOf(sql) + 
          " select TOP " + pageSize.intValue() + " a.orgname,a0.num1,b.num2,c.num3,d.num4,e.num5,f.num6,g.num7,isnull(h.num8,0) - isnull(g.num7,0) + isnull(f.num6,0) num88,a.ORGLEVEL,a.ORGIDSTRING,a.org_id ";
        sumSql = String.valueOf(sumSql) + " select sum(isnull(a0.num1,0)),sum(isnull(b.num2,0)),sum(isnull(c.num3,0)),sum(isnull(d.num4,0)),sum(isnull(e.num5,0)),sum(isnull(f.num6,0)),sum(isnull(g.num7,0)),sum(isnull((isnull(h.num8,0) - isnull(g.num7,0) + isnull(f.num6,0)),0)) num88 ";
      } 
      String tempSql = "";
      tempSql = String.valueOf(tempSql) + " from  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select c.org_id, c.ORGNAME, c.ORGLEVEL, c.ORGIDSTRING ";
      tempSql = String.valueOf(tempSql) + " from ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where c.ORGSTATUS = 0 ";
      tempSql = String.valueOf(tempSql) + "   and " + where;
      tempSql = String.valueOf(tempSql) + " ) a  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num1, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') < '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) < '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) < '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') < '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) < '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) < '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) a0  on ";
      tempSql = String.valueOf(tempSql) + " a.org_id = a0.org_id ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num2, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) = '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') = '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) = '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) = '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) b on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = b.org_id  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num3, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy') = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy-MM') = '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,7) = '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and a.EMPFIRETYPE like '%辞职%' ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) c on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = c.org_id  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num4, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy') = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy-MM') = '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,7) = '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and a.EMPFIRETYPE like '%辞退%' ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) d on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = d.org_id  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num5, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy') = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy-MM') = '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,7) = '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and a.EMPFIRETYPE like '%自离%' ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) e on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = e.org_id  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num6, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c, ST_EMPLOYEECHANGE d ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and a.emp_id = d.EMPCHANGE_EMPID ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(d.EMPCHANGE_DATE,'yyyy') = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(d.EMPCHANGE_DATE),1,4) = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),d.EMPCHANGE_DATE,20) = '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(d.EMPCHANGE_DATE,'yyyy-MM') = '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(d.EMPCHANGE_DATE),1,7) = '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),d.EMPCHANGE_DATE,20) = '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " and d.EMPCHANGE_OLDORG != c.org_id ";
      tempSql = String.valueOf(tempSql) + " and d.EMPCHANGE_NEWORG = c.org_id ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) f on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = f.org_id  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num7, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c, ST_EMPLOYEECHANGE d ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and a.emp_id = d.EMPCHANGE_EMPID ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(d.EMPCHANGE_DATE,'yyyy') = '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(d.EMPCHANGE_DATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),d.EMPCHANGE_DATE,20) = '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(d.EMPCHANGE_DATE,'yyyy-MM') = '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(d.EMPCHANGE_DATE),1,7) = '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),d.EMPCHANGE_DATE,20) = '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " and d.EMPCHANGE_OLDORG = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and d.EMPCHANGE_NEWORG != c.org_id ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) g on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = g.org_id  ";
      tempSql = String.valueOf(tempSql) + " left join  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num8, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + 
          " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + 
          yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) h on  ";
      tempSql = String.valueOf(tempSql) + " a.org_id = h.org_id ";
      sumSql = String.valueOf(sumSql) + tempSql;
      if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " ) n ";
        sql = String.valueOf(sql) + " where rownum <= " + (pageNo.intValue() * pageSize.intValue());
        sql = String.valueOf(sql) + " ) where rn>= " + ((
          pageNo.intValue() - 1) * pageSize.intValue() + 1);
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } else {
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " WHERE (a.org_id not in (SELECT org_id ";
        sql = String.valueOf(sql) + " FROM (SELECT TOP " + ((
          pageNo.intValue() - 1) * pageSize.intValue()) + 
          " c.org_id ";
        sql = String.valueOf(sql) + "       FROM ORG_ORGANIZATION c where c.ORGSTATUS = 0 and " + where;
        sql = String.valueOf(sql) + "       ORDER BY ORGIDSTRING) AS T)) ";
        sql = String.valueOf(sql) + " order by ORGIDSTRING ";
      } 
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      int recordCount = 0;
      ResultSet rs = stmt.executeQuery(countsql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      result.put("recordCount", new Integer(recordCount));
      rs = null;
      List<Object[]> list = new ArrayList(0);
      rs = stmt.executeQuery(sql);
      Object[] obj = (Object[])null;
      while (rs.next()) {
        obj = new Object[10];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        obj[5] = rs.getString(6);
        obj[6] = rs.getString(7);
        obj[7] = rs.getString(8);
        obj[8] = rs.getString(9);
        obj[9] = rs.getString(10);
        list.add(obj);
      } 
      result.put("list", list);
      rs = null;
      List<Object[]> list2 = new ArrayList(0);
      rs = stmt.executeQuery(sumSql);
      Object[] obj2 = (Object[])null;
      if (rs.next()) {
        obj2 = new Object[8];
        obj2[0] = rs.getString(1);
        obj2[1] = rs.getString(2);
        obj2[2] = rs.getString(3);
        obj2[3] = rs.getString(4);
        obj2[4] = rs.getString(5);
        obj2[5] = rs.getString(6);
        obj2[6] = rs.getString(7);
        obj2[7] = rs.getString(8);
        list2.add(obj2);
      } 
      result.put("list2", list2);
      rs.close();
    } catch (SQLException e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Map listEmpStruct(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    int flag = 0;
    if (databaseType.indexOf("oracle") > -1) {
      flag = 1;
    } else if (databaseType.indexOf("mysql") > -1) {
      flag = 2;
    } 
    Connection conn = null;
    Statement stmt = null;
    begin();
    try {
      int kindCount = 0;
      List<Object[]> personalKindList = this.session.createQuery("select po.kindId,po.kindName from com.js.oa.hr.personnelmanager.po.PersonalKindPO po order by po.kindSort")
        .list();
      Long[] kindIds = (Long[])null;
      if (personalKindList != null && personalKindList.size() > 0) {
        kindIds = new Long[personalKindList.size()];
        kindCount = personalKindList.size();
        for (int k = 0; k < kindCount; k++) {
          Object[] arrayOfObject = personalKindList.get(k);
          kindIds[k] = Long.valueOf(arrayOfObject[0].toString());
        } 
      } 
      String domainId = "0";
      domainId = this.session.createQuery("select emp.domainId from com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.empId=" + 
          userId).iterate().next().toString();
      Query query = this.session.createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + " and ccc.rightCode = '" + rightCode + "' and ccc.domainId=" + domainId);
      List<Object[]> list0 = query.list();
      String where = "";
      if (list0 != null && list0.size() > 0) {
        Object[] arrayOfObject = list0.get(0);
        String scopeType = arrayOfObject[0].toString();
        if ("0".equals(scopeType)) {
          where = " 1=1 ";
        } else if ("1".equals(scopeType)) {
          where = "1>1";
        } else if ("2".equals(scopeType)) {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "**" + orgId + "**";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } else if ("3".equals(scopeType)) {
          where = "c.org_id=" + orgId;
        } else {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "*" + (String)arrayOfObject[1] + "*";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } 
      } else {
        where = "1>2";
      } 
      String countsql = "";
      countsql = String.valueOf(countsql) + " select count(a.orgname) cnt ";
      countsql = String.valueOf(countsql) + " from  ";
      countsql = String.valueOf(countsql) + " ( ";
      countsql = String.valueOf(countsql) + " select c.org_id, c.ORGNAME, c.ORGLEVEL, c.ORGIDSTRING ";
      countsql = String.valueOf(countsql) + " from ORG_ORGANIZATION c ";
      countsql = String.valueOf(countsql) + " where c.ORGSTATUS = 0 ";
      countsql = String.valueOf(countsql) + "   and " + where;
      countsql = String.valueOf(countsql) + " ) a  ";
      String sql = "";
      String sumSql = "";
      if (flag == 1) {
        sql = String.valueOf(sql) + " select *  ";
        sql = String.valueOf(sql) + " from ( ";
        sql = String.valueOf(sql) + " select n.*, rownum rn ";
        sql = String.valueOf(sql) + " from  ";
        sql = String.valueOf(sql) + " ( ";
        sql = String.valueOf(sql) + " select a.orgname,a0.num1,b.num2,c.num3,d.num4,e.num5,f.num6,g.num7,h.num8,i.num9,j.num10,k.num11,l.num12,m.num13,n.num14,o.num15,p.num16,q.num17,r.num18,s.num19,t.num20,u.num21";
        if (kindCount > 0)
          for (int k0 = 0; k0 < kindCount; k0++)
            sql = String.valueOf(sql) + ",pk" + k0 + ".num_pk" + k0;  
        sql = String.valueOf(sql) + ",a.ORGLEVEL,a.ORGIDSTRING,a.org_id ";
        sumSql = String.valueOf(sumSql) + " select sum(nvl(a0.num1,0)),sum(nvl(b.num2,0)),sum(nvl(c.num3,0)),sum(nvl(d.num4,0)),sum(nvl(e.num5,0)),sum(nvl(f.num6,0)),sum(nvl(g.num7,0)),sum(nvl(h.num8,0)),sum(nvl(i.num9,0)),sum(nvl(j.num10,0)),sum(nvl(k.num11,0)),sum(nvl(l.num12,0)),sum(nvl(m.num13,0)),sum(nvl(n.num14,0)),sum(nvl(o.num15,0)),sum(nvl(p.num16,0)),sum(nvl(q.num17,0)),sum(nvl(r.num18,0)),sum(nvl(s.num19,0)),sum(nvl(t.num20,0)),sum(nvl(u.num21,0))";
        if (kindCount > 0)
          for (int k0 = 0; k0 < kindCount; k0++)
            sumSql = String.valueOf(sumSql) + ",sum(nvl(pk" + k0 + ".num_pk" + k0 + ",0))";  
      } else if (flag == 2) {
        sql = String.valueOf(sql) + " select a.orgname,a0.num1,b.num2,c.num3,d.num4,e.num5,f.num6,g.num7,h.num8,i.num9,j.num10,k.num11,l.num12,m.num13,n.num14,o.num15,p.num16,q.num17,r.num18,s.num19,t.num20,u.num21";
        if (kindCount > 0)
          for (int k0 = 0; k0 < kindCount; k0++)
            sql = String.valueOf(sql) + ",pk" + k0 + ".num_pk" + k0;  
        sql = String.valueOf(sql) + ",a.ORGLEVEL,a.ORGIDSTRING,a.org_id ";
        sumSql = String.valueOf(sumSql) + " select ifnull(sum(ifnull(a0.num1,0)),0),ifnull(sum(ifnull(b.num2,0)),0),ifnull(sum(ifnull(c.num3,0)),0),ifnull(sum(ifnull(d.num4,0)),0),ifnull(sum(ifnull(e.num5,0)),0),ifnull(sum(ifnull(f.num6,0)),0),ifnull(sum(ifnull(g.num7,0)),0),ifnull(sum(ifnull(h.num8,0)),0),ifnull(sum(ifnull(i.num9,0)),0),ifnull(sum(ifnull(j.num10,0)),0),ifnull(sum(ifnull(k.num11,0)),0),ifnull(sum(ifnull(l.num12,0)),0),ifnull(sum(ifnull(m.num13,0)),0),ifnull(sum(ifnull(n.num14,0)),0),ifnull(sum(ifnull(o.num15,0)),0),ifnull(sum(ifnull(p.num16,0)),0),ifnull(sum(ifnull(q.num17,0)),0),ifnull(sum(ifnull(r.num18,0)),0),ifnull(sum(ifnull(s.num19,0)),0),ifnull(sum(ifnull(t.num20,0)),0),ifnull(sum(ifnull(u.num21,0)),0)";
        if (kindCount > 0)
          for (int k0 = 0; k0 < kindCount; k0++)
            sumSql = String.valueOf(sumSql) + ",ifnull(sum(ifnull(pk" + k0 + ".num_pk" + k0 + ",0)),0)";  
      } else {
        sql = String.valueOf(sql) + " select TOP " + pageSize.intValue() + " a.orgname,a0.num1,b.num2,c.num3,d.num4,e.num5,f.num6,g.num7,h.num8,i.num9,j.num10,k.num11,l.num12,m.num13,n.num14,o.num15,p.num16,q.num17,r.num18,s.num19,t.num20,u.num21";
        if (kindCount > 0)
          for (int k0 = 0; k0 < kindCount; k0++)
            sql = String.valueOf(sql) + ",pk" + k0 + ".num_pk" + k0;  
        sql = String.valueOf(sql) + ",a.ORGLEVEL,a.ORGIDSTRING,a.org_id ";
        sumSql = String.valueOf(sumSql) + " select sum(isnull(a0.num1,0)),sum(isnull(b.num2,0)),sum(isnull(c.num3,0)),sum(isnull(d.num4,0)),sum(isnull(e.num5,0)),sum(isnull(f.num6,0)),sum(isnull(g.num7,0)),sum(isnull(h.num8,0)),sum(isnull(i.num9,0)),sum(isnull(j.num10,0)),sum(isnull(k.num11,0)),sum(isnull(l.num12,0)),sum(isnull(m.num13,0)),sum(isnull(n.num14,0)),sum(isnull(o.num15,0)),sum(isnull(p.num16,0)),sum(isnull(q.num17,0)),sum(isnull(r.num18,0)),sum(isnull(s.num19,0)),sum(isnull(t.num20,0)),sum(isnull(u.num21,0))";
        if (kindCount > 0)
          for (int k0 = 0; k0 < kindCount; k0++)
            sumSql = String.valueOf(sumSql) + ",sum(isnull(pk" + k0 + ".num_pk" + k0 + ",0))";  
      } 
      String tempSql = "";
      tempSql = String.valueOf(tempSql) + " from  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select c.org_id, c.ORGNAME, c.ORGLEVEL, c.ORGIDSTRING ";
      tempSql = String.valueOf(tempSql) + " from ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where c.ORGSTATUS = 0 ";
      tempSql = String.valueOf(tempSql) + "   and " + where;
      tempSql = String.valueOf(tempSql) + " ) a  ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num1, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) a0 ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = a0.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num2, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and (a.EMPSTUDYEXPERIENCE like '%硕士%' or a.EMPSTUDYEXPERIENCE like '%博士%' or a.EMPSTUDYEXPERIENCE like '%研究生%')";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) b ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = b.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num3, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and (a.EMPSTUDYEXPERIENCE like '%本科%' or a.EMPSTUDYEXPERIENCE like '%双学历%')";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) c ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num4, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and a.EMPSTUDYEXPERIENCE like '%专科%' ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) d ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = d.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num5, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and (a.EMPSTUDYEXPERIENCE like '%中专%' or a.EMPSTUDYEXPERIENCE like '%中技%') ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) e ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = e.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num6, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and (a.EMPSTUDYEXPERIENCE like '%高中%' or a.EMPSTUDYEXPERIENCE like '%高职%') ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) f ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = f.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num7, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and (a.EMPSTUDYEXPERIENCE like '%初中及以下%' or a.EMPSTUDYEXPERIENCE = '' or a.EMPSTUDYEXPERIENCE is null) ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) g ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = g.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num8, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and a.zhicheng like '%高级%' ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) h ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = h.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num9, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and a.zhicheng like '%中级%' ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) i ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = i.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num10, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and a.zhicheng like '%初级%' ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) j ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = j.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num11, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      tempSql = String.valueOf(tempSql) + " and (a.zhicheng not like '%高级%' or a.zhicheng is null) ";
      tempSql = String.valueOf(tempSql) + " and (a.zhicheng not like '%中级%' or a.zhicheng is null) ";
      tempSql = String.valueOf(tempSql) + " and (a.zhicheng not like '%初级%' or a.zhicheng is null) ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) k ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = k.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num12, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 >= 5 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.INTOCOMPANYDATE))/365 >= 5 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01'))/365 >= 5";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 >= 5 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.INTOCOMPANYDATE))/365 >= 5 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 >= 5";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) l ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = l.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num13, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 < 5 ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 >= 3 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.INTOCOMPANYDATE))/365 < 5 ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.INTOCOMPANYDATE))/365 >= 3 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 < 5";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 >= 3";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 < 5 ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 >= 3 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.INTOCOMPANYDATE))/365 < 5 ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.INTOCOMPANYDATE))/365 >= 3 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 < 5";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 >= 3";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) m ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = m.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num14, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 < 3 ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 >= 1 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.INTOCOMPANYDATE))/365 < 3 ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.INTOCOMPANYDATE))/365 >= 1 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 < 3";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 >= 1";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 < 3 ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 >= 1 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.INTOCOMPANYDATE))/365 < 3 ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.INTOCOMPANYDATE))/365 >= 1 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 < 3";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 >= 1";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) n ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = n.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num15, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 < 1 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.INTOCOMPANYDATE))/365 < 1 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 < 1";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.INTOCOMPANYDATE)/365 < 1 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.INTOCOMPANYDATE))/365 < 1 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.INTOCOMPANYDATE,convert(datetime,'" + yearMonth + "-28'))/365 < 1";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) o ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = o.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num16, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.EMPBIRTH)/365 >= 40 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.EMPBIRTH))/365 >= 40 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 >= 40";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.EMPBIRTH)/365 >= 40 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.EMPBIRTH))/365 >= 40 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 >= 40";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) p ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = p.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num17, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.EMPBIRTH)/365 < 40 ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.EMPBIRTH)/365 >= 30 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.EMPBIRTH))/365 < 40 ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.EMPBIRTH))/365 >= 30 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 < 40";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 >= 30";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.EMPBIRTH)/365 < 40 ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.EMPBIRTH)/365 >= 30 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.EMPBIRTH))/365 < 40 ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.EMPBIRTH))/365 >= 30 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 < 40";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 >= 30";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by  c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) q ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = q.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num18, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.EMPBIRTH)/365 < 30 ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.EMPBIRTH)/365 >= 20 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.EMPBIRTH))/365 < 30 ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.EMPBIRTH))/365 >= 20 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 < 30";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 >= 20";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.EMPBIRTH)/365 < 30 ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.EMPBIRTH)/365 >= 20 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.EMPBIRTH))/365 < 30 ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.EMPBIRTH))/365 >= 20 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 < 30";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 >= 20";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) r ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = r.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num19, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
          tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "-01-01','yyyy-MM-dd') - a.EMPBIRTH)/365 < 20 ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
          tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth.substring(0, yearMonth.indexOf("-")) + "-01-01')) - TO_DAYS(a.EMPBIRTH))/365 < 20 ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
          tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 < 20";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
        tempSql = String.valueOf(tempSql) + " and (to_date('" + yearMonth + 
          "-28','yyyy-MM-dd') - a.EMPBIRTH)/365 < 20 ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and (TO_DAYS(timestamp('" + yearMonth + "-28')) - TO_DAYS(a.EMPBIRTH))/365 < 20 ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
        tempSql = String.valueOf(tempSql) + " and datediff(dy,a.EMPBIRTH,convert(datetime,'" + yearMonth + "-28'))/365 < 20";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) s ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = s.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num20, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " and a.EMPSEX = 0 ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) t ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = t.org_id ";
      tempSql = String.valueOf(tempSql) + " left join ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + " select count(a.empname) num21, c.org_id ";
      tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
      tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + 
            "'  ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
            yearMonth + "'  ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + yearMonth + 
          "'  ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
          yearMonth + "'  ";
      } 
      tempSql = String.valueOf(tempSql) + " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
      tempSql = String.valueOf(tempSql) + " and a.EMPSEX = 1 ";
      tempSql = String.valueOf(tempSql) + " group by c.org_id ";
      tempSql = String.valueOf(tempSql) + " ) u ";
      tempSql = String.valueOf(tempSql) + " on a.org_id = u.org_id ";
      if (kindCount > 0)
        for (int k0 = 0; k0 < kindCount; k0++) {
          tempSql = String.valueOf(tempSql) + " left join ";
          tempSql = String.valueOf(tempSql) + " ( ";
          tempSql = String.valueOf(tempSql) + " select count(a.empname) num_pk" + k0 + ", c.org_id ";
          tempSql = String.valueOf(tempSql) + " from org_employee a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
          tempSql = String.valueOf(tempSql) + " where a.emp_id = b.emp_id ";
          tempSql = String.valueOf(tempSql) + " and b.org_id = c.org_id ";
          tempSql = String.valueOf(tempSql) + " and a.PERSONALKIND=" + kindIds[k0] + " ";
          if (yearMonth.endsWith("00")) {
            if (flag == 1) {
              tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy') <= '" + 
                yearMonth.substring(0, yearMonth.indexOf("-")) + 
                "'  ";
            } else if (flag == 2) {
              tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,4) <= '" + yearMonth.substring(0, yearMonth.indexOf("-")) + "'  ";
            } else {
              tempSql = String.valueOf(tempSql) + 
                " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
                yearMonth + "'  ";
            } 
          } else if (flag == 1) {
            tempSql = String.valueOf(tempSql) + " and to_char(a.INTOCOMPANYDATE,'yyyy-MM') <= '" + 
              yearMonth + 
              "'  ";
          } else if (flag == 2) {
            tempSql = String.valueOf(tempSql) + " and substr(concat(a.INTOCOMPANYDATE),1,7) <= '" + yearMonth + "'  ";
          } else {
            tempSql = String.valueOf(tempSql) + 
              " and convert(char(7),a.INTOCOMPANYDATE,20) <= '" + 
              yearMonth + "'  ";
          } 
          tempSql = String.valueOf(tempSql) + 
            " and (a.JOBSTATUS not like '%离职%' or a.JOBSTATUS is null) ";
          tempSql = String.valueOf(tempSql) + " group by c.org_id ";
          tempSql = String.valueOf(tempSql) + " ) pk" + k0 + " ";
          tempSql = String.valueOf(tempSql) + " on a.org_id = pk" + k0 + ".org_id ";
        }  
      sumSql = String.valueOf(sumSql) + tempSql;
      if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " ) n ";
        sql = String.valueOf(sql) + " where rownum <= " + (pageNo.intValue() * pageSize.intValue());
        sql = String.valueOf(sql) + " ) where rn>= " + ((
          pageNo.intValue() - 1) * pageSize.intValue() + 1);
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } else {
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " WHERE (a.org_id not in (SELECT org_id ";
        sql = String.valueOf(sql) + " FROM (SELECT TOP " + ((
          pageNo.intValue() - 1) * pageSize.intValue()) + 
          " c.org_id ";
        sql = String.valueOf(sql) + 
          "       FROM ORG_ORGANIZATION c where c.ORGSTATUS = 0 and " + where;
        sql = String.valueOf(sql) + "       ORDER BY ORGIDSTRING) AS T)) ";
        sql = String.valueOf(sql) + " order by ORGIDSTRING ";
      } 
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      int recordCount = 0;
      ResultSet rs = stmt.executeQuery(countsql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      result.put("recordCount", new Integer(recordCount));
      rs = null;
      List<Object[]> list = new ArrayList(0);
      rs = stmt.executeQuery(sql);
      Object[] obj = (Object[])null;
      while (rs.next()) {
        obj = new Object[23 + kindCount];
        for (int k0 = 0; k0 < 23 + kindCount; k0++)
          obj[k0] = rs.getString(k0 + 1); 
        list.add(obj);
      } 
      result.put("list", list);
      rs = null;
      List<Object[]> list2 = new ArrayList(0);
      rs = stmt.executeQuery(sumSql);
      Object[] obj2 = (Object[])null;
      if (rs.next()) {
        obj2 = new Object[21 + kindCount];
        for (int k0 = 0; k0 < 21 + kindCount; k0++)
          obj2[k0] = rs.getString(k0 + 1); 
        list2.add(obj2);
      } 
      result.put("list2", list2);
      rs.close();
    } catch (SQLException e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Map listEmpCizhi(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    int flag = 0;
    if (databaseType.indexOf("oracle") > -1) {
      flag = 1;
    } else if (databaseType.indexOf("mysql") > -1) {
      flag = 2;
    } 
    Connection conn = null;
    Statement stmt = null;
    begin();
    try {
      String domainId = "0";
      domainId = this.session.createQuery("select emp.domainId from com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.empId=" + 
          userId).iterate().next().toString();
      Query query = this.session.createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          userId + " and ccc.rightCode = '" + 
          rightCode + "' and ccc.domainId=" + 
          domainId);
      List<Object[]> list0 = query.list();
      String where = "";
      if (list0 != null && list0.size() > 0) {
        Object[] arrayOfObject = list0.get(0);
        String scopeType = arrayOfObject[0].toString();
        if ("0".equals(scopeType)) {
          where = " 1=1 ";
        } else if ("1".equals(scopeType)) {
          where = "1>1";
        } else if ("2".equals(scopeType)) {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "**" + orgId + "**";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } else if ("3".equals(scopeType)) {
          where = "c.org_id=" + orgId;
        } else {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "*" + (String)arrayOfObject[1] + "*";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } 
      } else {
        where = "1>2";
      } 
      String countsql = "";
      countsql = String.valueOf(countsql) + " select count(a.EMP_ID) cnt ";
      countsql = String.valueOf(countsql) + " from  ";
      countsql = String.valueOf(countsql) + " ( ";
      countsql = String.valueOf(countsql) + 
        " select a.EMP_ID ";
      countsql = String.valueOf(countsql) + " from ORG_EMPLOYEE a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      countsql = String.valueOf(countsql) + " where a.EMP_ID = b.EMP_ID and b.ORG_ID = c.ORG_ID ";
      countsql = String.valueOf(countsql) + "   and " + where;
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          countsql = String.valueOf(countsql) + " and to_char(a.LIZHIDATE,'yyyy') = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else if (flag == 2) {
          countsql = String.valueOf(countsql) + " and substr(concat(a.LIZHIDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else {
          countsql = String.valueOf(countsql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
            yearMonth + "' ";
        } 
      } else if (flag == 1) {
        countsql = String.valueOf(countsql) + " and to_char(a.LIZHIDATE,'yyyy-MM') = '" + 
          yearMonth + "' ";
      } else if (flag == 2) {
        countsql = String.valueOf(countsql) + " and substr(concat(a.LIZHIDATE),1,7) = '" + 
          yearMonth + "' ";
      } else {
        countsql = String.valueOf(countsql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
          yearMonth + "' ";
      } 
      countsql = String.valueOf(countsql) + " and a.JOBSTATUS like '%离职%' ";
      countsql = String.valueOf(countsql) + " ) a  ";
      String sql = "";
      if (flag == 1) {
        sql = String.valueOf(sql) + " select *  ";
        sql = String.valueOf(sql) + " from ( ";
        sql = String.valueOf(sql) + " select n.*, rownum rn ";
        sql = String.valueOf(sql) + " from  ";
        sql = String.valueOf(sql) + " ( ";
        sql = String.valueOf(sql) + 
          " select a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.LIZHIDATE, a.EMPFIRETYPE, a.FIREREASON, a.ORGLEVEL, a.ORGIDSTRING, a.ORGNAMESTRING, a.KIND_NAME ";
      } else if (flag == 2) {
        sql = String.valueOf(sql) + 
          " select a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.LIZHIDATE, a.EMPFIRETYPE, a.FIREREASON, a.ORGLEVEL, a.ORGIDSTRING, a.ORGNAMESTRING, a.KIND_NAME ";
      } else {
        sql = String.valueOf(sql) + 
          " select TOP " + pageSize.intValue() + " a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.LIZHIDATE, a.EMPFIRETYPE, a.FIREREASON, a.ORGLEVEL, a.ORGIDSTRING, a.ORGNAMESTRING, a.KIND_NAME ";
      } 
      String tempSql = "";
      tempSql = String.valueOf(tempSql) + " from  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + 
        " select a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.LIZHIDATE, a.EMPFIRETYPE, a.FIREREASON, c.ORGLEVEL, c.ORGIDSTRING, c.ORGNAMESTRING, d.KIND_NAME ";
      tempSql = String.valueOf(tempSql) + " from ORG_EMPLOYEE a ";
      tempSql = String.valueOf(tempSql) + " left join OA_PERSONAL_KIND d on a.PERSONALKIND = d.KIND_ID ";
      tempSql = String.valueOf(tempSql) + " , ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.EMP_ID = b.EMP_ID and b.ORG_ID = c.ORG_ID ";
      tempSql = String.valueOf(tempSql) + "   and " + where;
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy') = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
            yearMonth + "' ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.LIZHIDATE,'yyyy-MM') = '" + 
          yearMonth + "' ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.LIZHIDATE),1,7) = '" + 
          yearMonth + "' ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.LIZHIDATE,20) = '" + 
          yearMonth + "' ";
      } 
      tempSql = String.valueOf(tempSql) + " and a.JOBSTATUS like '%离职%' ";
      tempSql = String.valueOf(tempSql) + " ) a  ";
      if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " ) n ";
        sql = String.valueOf(sql) + " where rownum <= " + (
          pageNo.intValue() * pageSize.intValue());
        sql = String.valueOf(sql) + " ) where rn>= " + ((
          pageNo.intValue() - 1) * pageSize.intValue() + 1);
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } else {
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " WHERE (EMP_ID not in (SELECT EMP_ID ";
        sql = String.valueOf(sql) + " FROM (SELECT TOP " + ((
          pageNo.intValue() - 1) * pageSize.intValue()) + 
          " a.EMP_ID ";
        sql = String.valueOf(sql) + "       FROM ORG_EMPLOYEE a , ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c where a.EMP_ID = b.EMP_ID and b.ORG_ID = c.ORG_ID and " + where;
        sql = String.valueOf(sql) + " and convert(char(7),a.LIZHIDATE,20) = '" + yearMonth + "' ";
        sql = String.valueOf(sql) + " and a.JOBSTATUS like '%离职%' ";
        sql = String.valueOf(sql) + "       ORDER BY ORGIDSTRING) AS T)) ";
        sql = String.valueOf(sql) + " order by ORGIDSTRING ";
      } 
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      int recordCount = 0;
      ResultSet rs = stmt.executeQuery(countsql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      result.put("recordCount", new Integer(recordCount));
      rs = null;
      List<Object[]> list = new ArrayList(0);
      rs = stmt.executeQuery(sql);
      Object[] obj = (Object[])null;
      while (rs.next()) {
        obj = new Object[11];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        obj[5] = rs.getString(6);
        obj[6] = rs.getString(7);
        obj[7] = rs.getString(8);
        obj[8] = rs.getString(9);
        obj[9] = rs.getString(10);
        obj[10] = rs.getString(11);
        list.add(obj);
      } 
      result.put("list", list);
      rs.close();
    } catch (SQLException e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Map listEmpZhuanzheng(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    int flag = 0;
    if (databaseType.indexOf("oracle") > -1) {
      flag = 1;
    } else if (databaseType.indexOf("mysql") > -1) {
      flag = 2;
    } 
    Connection conn = null;
    Statement stmt = null;
    begin();
    try {
      String domainId = "0";
      domainId = this.session.createQuery("select emp.domainId from com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.empId=" + 
          userId).iterate().next().toString();
      Query query = this.session.createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          userId + " and ccc.rightCode = '" + 
          rightCode + "' and ccc.domainId=" + 
          domainId);
      List<Object[]> list0 = query.list();
      String where = "";
      if (list0 != null && list0.size() > 0) {
        Object[] arrayOfObject = list0.get(0);
        String scopeType = arrayOfObject[0].toString();
        if ("0".equals(scopeType)) {
          where = " 1=1 ";
        } else if ("1".equals(scopeType)) {
          where = "1>1";
        } else if ("2".equals(scopeType)) {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "**" + orgId + "**";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } else if ("3".equals(scopeType)) {
          where = "c.org_id=" + orgId;
        } else {
          String result0 = "-1";
          StringBuffer where0 = new StringBuffer(" WHERE ");
          String range = "*" + (String)arrayOfObject[1] + "*";
          String[] rangeArray = range.split("\\*\\*");
          int i = 0;
          for (i = 1; i < rangeArray.length; i++) {
            if (i > 1)
              where0.append(" or "); 
            where0.append(" org.orgIdString like '%$");
            where0.append(rangeArray[i]);
            where0.append("$%' ");
          } 
          List list1 = this.session.createQuery(
              "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + 
              where0).list();
          int j = 900;
          StringBuffer tmp0 = new StringBuffer();
          for (i = 0; i < list1.size(); i++) {
            tmp0.append(list1.get(i));
            if (i > j) {
              tmp0.append("a");
              j += 900;
            } else {
              tmp0.append(",");
            } 
          } 
          result0 = tmp0.toString();
          if (result0.length() > 0)
            result0 = result0.substring(0, result0.length() - 1); 
          String orgRange = result0;
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(c.org_id in(" + tmp[k] + 
                ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = " c.org_id in(" + orgRange + ") ";
          } 
        } 
      } else {
        where = "1>2";
      } 
      String countsql = "";
      countsql = String.valueOf(countsql) + " select count(a.EMP_ID) cnt ";
      countsql = String.valueOf(countsql) + " from  ";
      countsql = String.valueOf(countsql) + " ( ";
      countsql = String.valueOf(countsql) + 
        " select a.EMP_ID ";
      countsql = String.valueOf(countsql) + " from ORG_EMPLOYEE a, ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      countsql = String.valueOf(countsql) + " where a.EMP_ID = b.EMP_ID and b.ORG_ID = c.ORG_ID ";
      countsql = String.valueOf(countsql) + "   and " + where;
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          countsql = String.valueOf(countsql) + " and to_char(a.ZHUANZHENGDATE,'yyyy') = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else if (flag == 2) {
          countsql = String.valueOf(countsql) + " and substr(concat(a.ZHUANZHENGDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else {
          countsql = String.valueOf(countsql) + " and convert(char(7),a.ZHUANZHENGDATE,20) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } 
      } else if (flag == 1) {
        countsql = String.valueOf(countsql) + " and to_char(a.ZHUANZHENGDATE,'yyyy-MM') = '" + 
          yearMonth + "' ";
      } else if (flag == 2) {
        countsql = String.valueOf(countsql) + " and substr(concat(a.ZHUANZHENGDATE),1,7) = '" + 
          yearMonth + "' ";
      } else {
        countsql = String.valueOf(countsql) + " and convert(char(7),a.ZHUANZHENGDATE,20) = '" + 
          yearMonth + "' ";
      } 
      countsql = String.valueOf(countsql) + " ) a  ";
      String sql = "";
      if (flag == 1) {
        sql = String.valueOf(sql) + " select *  ";
        sql = String.valueOf(sql) + " from ( ";
        sql = String.valueOf(sql) + " select n.*, rownum rn ";
        sql = String.valueOf(sql) + " from  ";
        sql = String.valueOf(sql) + " ( ";
        sql = String.valueOf(sql) + 
          " select a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.ZHUANZHENGDATE, a.ORGLEVEL, a.ORGIDSTRING, a.ORGNAMESTRING ";
      } else if (flag == 2) {
        sql = String.valueOf(sql) + 
          " select a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.ZHUANZHENGDATE, a.ORGLEVEL, a.ORGIDSTRING, a.ORGNAMESTRING ";
      } else {
        sql = String.valueOf(sql) + 
          " select TOP " + pageSize.intValue() + " a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.ZHUANZHENGDATE, a.ORGLEVEL, a.ORGIDSTRING, a.ORGNAMESTRING ";
      } 
      String tempSql = "";
      tempSql = String.valueOf(tempSql) + " from  ";
      tempSql = String.valueOf(tempSql) + " ( ";
      tempSql = String.valueOf(tempSql) + 
        " select a.EMP_ID, a.EMPNAME, a.EMPPOSITION, a.INTOCOMPANYDATE, a.ZHUANZHENGDATE, c.ORGLEVEL, c.ORGIDSTRING, c.ORGNAMESTRING ";
      tempSql = String.valueOf(tempSql) + " from ORG_EMPLOYEE a ";
      tempSql = String.valueOf(tempSql) + " , ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c ";
      tempSql = String.valueOf(tempSql) + " where a.EMP_ID = b.EMP_ID and b.ORG_ID = c.ORG_ID ";
      tempSql = String.valueOf(tempSql) + "   and " + where;
      if (yearMonth.endsWith("00")) {
        if (flag == 1) {
          tempSql = String.valueOf(tempSql) + " and to_char(a.ZHUANZHENGDATE,'yyyy') = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else if (flag == 2) {
          tempSql = String.valueOf(tempSql) + " and substr(concat(a.ZHUANZHENGDATE),1,4) = '" + 
            yearMonth.substring(0, yearMonth.indexOf("-")) + "' ";
        } else {
          tempSql = String.valueOf(tempSql) + " and convert(char(7),a.ZHUANZHENGDATE,20) = '" + 
            yearMonth + "' ";
        } 
      } else if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " and to_char(a.ZHUANZHENGDATE,'yyyy-MM') = '" + 
          yearMonth + "' ";
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " and substr(concat(a.ZHUANZHENGDATE),1,7) = '" + 
          yearMonth + "' ";
      } else {
        tempSql = String.valueOf(tempSql) + " and convert(char(7),a.ZHUANZHENGDATE,20) = '" + 
          yearMonth + "' ";
      } 
      tempSql = String.valueOf(tempSql) + " ) a  ";
      if (flag == 1) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " ) n ";
        sql = String.valueOf(sql) + " where rownum <= " + (
          pageNo.intValue() * pageSize.intValue());
        sql = String.valueOf(sql) + " ) where rn>= " + ((
          pageNo.intValue() - 1) * pageSize.intValue() + 1);
      } else if (flag == 2) {
        tempSql = String.valueOf(tempSql) + " order by a.ORGIDSTRING ";
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " limit " + ((pageNo.intValue() - 1) * pageSize.intValue()) + "," + pageSize.intValue();
      } else {
        sql = String.valueOf(sql) + tempSql;
        sql = String.valueOf(sql) + " WHERE (EMP_ID not in (SELECT EMP_ID ";
        sql = String.valueOf(sql) + " FROM (SELECT TOP " + ((
          pageNo.intValue() - 1) * pageSize.intValue()) + 
          " a.EMP_ID ";
        sql = String.valueOf(sql) + "       FROM ORG_EMPLOYEE a , ORG_ORGANIZATION_USER b, ORG_ORGANIZATION c where a.EMP_ID = b.EMP_ID and b.ORG_ID = c.ORG_ID and " + where;
        sql = String.valueOf(sql) + " and convert(char(7),a.ZHUANZHENGDATE,20) = '" + yearMonth + "' ";
        sql = String.valueOf(sql) + "       ORDER BY ORGIDSTRING) AS T)) ";
        sql = String.valueOf(sql) + " order by ORGIDSTRING ";
      } 
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      int recordCount = 0;
      ResultSet rs = stmt.executeQuery(countsql);
      if (rs.next())
        recordCount = rs.getInt(1); 
      result.put("recordCount", new Integer(recordCount));
      rs = null;
      List<Object[]> list = new ArrayList(0);
      rs = stmt.executeQuery(sql);
      Object[] obj = (Object[])null;
      while (rs.next()) {
        obj = new Object[8];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        obj[5] = rs.getString(6);
        obj[6] = rs.getString(7);
        obj[7] = rs.getString(8);
        list.add(obj);
      } 
      result.put("list", list);
      rs.close();
    } catch (SQLException e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
    } 
    return result;
  }
}
