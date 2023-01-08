package com.js.oa.hr.officemanager.action;

import com.js.oa.hr.officemanager.service.SqlQueryBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import com.js.util.util.EncryptSelf;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class LeaderAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    LeaderActionForm leaderActionForm = (LeaderActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    String tag = "";
    if (action.equals("toLeaderQuery")) {
      leaderActionForm.setField("emp.empsex");
      leaderActionForm.setFileName("性别");
      httpServletRequest.setAttribute("fieldName", "性别");
      tag = "toLeaderQuery";
    } else if ("tableQuery".equals(action) || "graphQuery".equals(action) || "export".equals(action)) {
      tag = action;
      httpServletRequest.setAttribute("fieldName", httpServletRequest.getParameter("fieldName"));
      String field = leaderActionForm.getField();
      ManagerService mbd = new ManagerService();
      String curUserId = httpServletRequest.getSession(true).getAttribute(
          "userId").toString();
      String curOrgId = httpServletRequest.getSession(true).getAttribute(
          "orgId").toString();
      String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
          "07*01*01", "org.org_id", "emp.emp_Id");
      String sql = "";
      String from = " from org_employee emp, ORG_ORGANIZATION_USER org ,org_emp_otherinfo empOther ";
      SqlQueryBD bd = new SqlQueryBD();
      String[][] result = (String[][])null;
      if ("emp.empsex".equalsIgnoreCase(field)) {
        sql = "select decode(emp.empsex,0,'男',1,'女','其他') lb,count(emp.emp_id) empcount,'select emp.emp_id from org_employee emp,org_organization_user org where emp.emp_id = org.emp_id and empsex='''|| emp.empsex||''''||' and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + 
          wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 

          
          wherePara;
        sql = String.valueOf(sql) + " group by emp.empsex";
        try {
          result = bd.query(sql);
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if ("emp.nl".equalsIgnoreCase(field)) {
        result = new String[11][3];
        sql = "select '25岁以下',count(emp.emp_id),'select emp.emp_id from org_employee emp ,org_organization_user org where emp.emp_id = org.emp_id and emp.userIsDeleted = 0 and emp.empBirth is not null and (to_char(sysdate, ''YYYY'') - to_char(emp.empBirth, ''YYYY'') + 1) < 25 and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and emp.empBirth is not null and (to_char(sysdate,'YYYY')-to_char(emp.empBirth, 'YYYY')+1) <25 and " + 




          
          wherePara;
        try {
          String[][] temp = bd.query(sql);
          result[0][0] = temp[0][0];
          result[0][1] = temp[0][1];
          result[0][2] = temp[0][2];
        } catch (Exception e) {
          e.printStackTrace();
        } 
        for (int i = 1; i <= 8; i++) {
          int low = 25 + (i - 1) * 5;
          int up = low + 4;
          sql = "select '" + low + "岁-" + up + "岁" + "',count(emp.emp_id),'select emp.emp_id from org_employee emp,org_organization_user org where emp.emp_id=org.emp_id and emp.emp_id > 0 and emp.userIsDeleted = 0 and emp.empBirth is not null and (to_char(sysdate, ''YYYY'') - to_char(emp.empBirth, ''YYYY'') + 1) >= 25 and (to_char(sysdate, ''YYYY'') - to_char(emp.empBirth, ''YYYY'') + 1) <= 29 and " + wherePara + "'";
          sql = String.valueOf(sql) + from;
          sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and emp.empBirth is not null and (to_char(sysdate,'YYYY')-to_char(emp.empBirth, 'YYYY')+1) >= " + 



            
            low + 
            " and (to_char(sysdate,'YYYY')-to_char(emp.empBirth, 'YYYY')+1) <=" + up + 
            " and " + wherePara;
          try {
            String[][] temp = bd.query(sql);
            result[i][0] = temp[0][0];
            result[i][1] = temp[0][1];
            result[i][2] = temp[0][2];
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
        sql = "select '65岁及以上',count(emp.emp_id),'select emp.emp_id from org_employee emp,org_organization_user org where emp.emp_id and org.emp_id and emp.emp_id > 0 and emp.userIsDeleted = 0 and emp.empBirth is not null and (to_char(sysdate, ''YYYY'') - to_char(emp.empBirth, ''YYYY'') + 1) >= 65 and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and emp.empBirth is not null and (to_char(sysdate,'YYYY')-to_char(emp.empBirth, 'YYYY')+1) >=65 and " + 




          
          wherePara;
        try {
          String[][] temp = bd.query(sql);
          result[9][0] = temp[0][0];
          result[9][1] = temp[0][1];
          result[9][2] = temp[0][2];
        } catch (Exception e) {
          e.printStackTrace();
        } 
        sql = "select '其他',count(emp.emp_id),'select emp.emp_id from org_employee emp,org_organization_user org where emp.emp_id = org.emp_id and emp.emp_id > 0 and emp.userIsDeleted = 0 and emp.empBirth is null and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and emp.empBirth is null and " + 



          
          wherePara;
        try {
          String[][] temp = bd.query(sql);
          result[10][0] = temp[0][0];
          result[10][1] = temp[0][1];
          result[10][2] = temp[0][2];
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if ("emp.empfiredate".equalsIgnoreCase(field) || "emp.intoCompanyDate".equalsIgnoreCase(field)) {
        sql = "select a,b,c from (select to_char(" + field + ",'yyyy-mm') a,count(emp.emp_id) b,'select emp.emp_id from org_employee emp,org_organization_user org where emp.emp_id=org_emp_id and  emp.emp_id > 0 and " + field + " is not null and to_char(" + field + ", '||''''||'yyyy-mm'||''''||')='||''''||to_char(" + field + ", 'yyyy-mm')||' and " + wherePara + "' c";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is not null " + 
          "and " + wherePara;
        sql = String.valueOf(sql) + " group by to_char(" + field + ",'yyyy-mm')) order by a";
        try {
          String[][] temp = bd.query(sql);
          if (temp != null) {
            result = new String[temp.length][3];
            for (int i = 0; i < temp.length; i++) {
              result[i][0] = temp[i][0];
              result[i][1] = temp[i][1];
              result[i][2] = temp[i][2];
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        sql = "select '其他',count(emp.emp_id) , 'select emp.emp_id from org_employee emp,org_organization_user org where emp.emp_id = org.emp_id and emp.emp_id > 0 and emp.userIsDeleted = 0 and emp.empfiredate is null and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is null " + 
          "and " + wherePara;
        try {
          String[][] temp = bd.query(sql);
          if (temp != null)
            if (result == null) {
              result = temp;
            } else {
              String[][] result0 = new String[result.length + 1][3];
              for (int i = 0; i < result.length; i++) {
                result0[i][0] = result[i][0];
                result0[i][1] = result[i][1];
                result0[i][2] = result[i][2];
              } 
              result0[result0.length - 1][0] = temp[0][0];
              result0[result0.length - 1][1] = temp[0][1];
              result0[result0.length - 1][2] = temp[0][2];
              result = result0;
            }  
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if ("emp.bm".equalsIgnoreCase(field)) {
        sql = "select orgidstring,orgname from org_organization where orgstatus=0";
        List<String> ids = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        DbOpt db = new DbOpt();
        try {
          String[][] temp1 = db.executeQueryToStrArr2(sql, 2);
          db.close();
          int i;
          for (i = 0; i < temp1.length; i++) {
            if (getCount(temp1[i][0]) == 6) {
              ids.add(temp1[i][0]);
              names.add(temp1[i][1]);
            } 
          } 
          result = new String[ids.size()][3];
          for (i = 0; i < ids.size(); i++) {
            result[i][0] = names.get(i);
            DbOpt db1 = new DbOpt();
            String getCountSql = "select count(emp.emp_id)";
            getCountSql = String.valueOf(getCountSql) + from;
            getCountSql = String.valueOf(getCountSql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and org.org_id in(select org_id from org_organization where orgidstring like '%" + 


              
              (String)ids.get(i) + "%')" + 
              "and " + wherePara;
            result[i][1] = db1.executeQueryToStr(getCountSql);
            db1.close();
            result[i][2] = "select emp_id from org_organization_user org where org.org_id in(select org_id from org_organization where orgidstring like '%" + (String)ids.get(i) + "%') and exists(select 8 from org_employee emp where emp_id=org.emp_id and emp.userIsDeleted = 0 and emp.emp_id > 0) and " + wherePara;
          } 
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if ("emp.empDuty".equalsIgnoreCase(field)) {
        sql = "select emp.empduty,count(emp.emp_id),'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and  emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + field + " = '''||" + field + "||''' and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is not null " + 
          "and " + wherePara;
        sql = String.valueOf(sql) + " group by " + field + ",emp.empdutylevel order by emp.empdutylevel ";
        try {
          String[][] temp = bd.query(sql);
          if (temp != null) {
            result = new String[temp.length][3];
            for (int i = 0; i < temp.length; i++) {
              result[i][0] = temp[i][0];
              result[i][1] = temp[i][1];
              result[i][2] = temp[i][2];
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        sql = "select '其他',count(emp.emp_id) ,'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + field + " is null and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is null " + 
          "and " + wherePara;
        try {
          String[][] temp = bd.query(sql);
          if (temp != null)
            if (result == null) {
              result = temp;
            } else {
              String[][] result0 = new String[result.length + 1][3];
              for (int i = 0; i < result.length; i++) {
                result0[i][0] = result[i][0];
                result0[i][1] = result[i][1];
                result0[i][2] = result[i][2];
              } 
              result0[result0.length - 1][0] = temp[0][0];
              result0[result0.length - 1][1] = temp[0][1];
              result0[result0.length - 1][2] = temp[0][2];
              result = result0;
            }  
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if ("empOther.hkszd".equalsIgnoreCase(field)) {
        sql = "select substr(empother.hkszd,0,instr(empother.hkszd,'，')-1),count(emp.emp_id),'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and  emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and empother.hkszd like '''||substr(empother.hkszd,0,instr(empother.hkszd,'，')-1)||'，%'||''' and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is not null " + 
          "and " + wherePara;
        sql = String.valueOf(sql) + " group by substr(empother.hkszd,0,instr(empother.hkszd,'，')-1)";
        try {
          String[][] temp = bd.query(sql);
          if (temp != null) {
            result = new String[temp.length][3];
            for (int i = 0; i < temp.length; i++) {
              result[i][0] = temp[i][0];
              if (result[i][0] == null || "".equals(result[i][0])) {
                result[i][0] = "未按格式要求填写户口所在地";
                temp[i][2] = "select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and  emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and empother.hkszd is not null and empother.hkszd not like '%，%' and " + wherePara;
              } 
              result[i][1] = temp[i][1];
              result[i][2] = temp[i][2];
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        sql = "select '未填写',count(emp.emp_id) ,'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + field + " is null and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is null " + 
          "and " + wherePara;
        try {
          String[][] temp = bd.query(sql);
          if (temp != null)
            if (result == null) {
              result = temp;
            } else {
              String[][] result0 = new String[result.length + 1][3];
              for (int i = 0; i < result.length; i++) {
                result0[i][0] = result[i][0];
                result0[i][1] = result[i][1];
                result0[i][2] = result[i][2];
              } 
              result0[result0.length - 1][0] = temp[0][0];
              result0[result0.length - 1][1] = temp[0][1];
              result0[result0.length - 1][2] = temp[0][2];
              result = result0;
            }  
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if ("empOther.zyzg".equalsIgnoreCase(field)) {
        Map<String, Integer> zyzgMap = new HashMap<String, Integer>();
        sql = "select empOther.zyzg ";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and empOther.zyzg is not null and " + 



          
          wherePara;
        try {
          DbOpt db = new DbOpt();
          String[][] temp = db.executeQueryToStrArr2(sql, 1);
          db.close();
          int i;
          for (i = 0; i < temp.length; i++) {
            String zyzgs = temp[i][0];
            String[] zyzgList = zyzgs.split("，");
            for (int j = 0; j < zyzgList.length; j++) {
              String zyzg = zyzgList[j];
              if (zyzgMap.containsKey(zyzg)) {
                zyzgMap.put(zyzg, new Integer(((Integer)zyzgMap.get(zyzg)).intValue() + 1));
              } else {
                zyzgMap.put(zyzg, new Integer(1));
              } 
            } 
          } 
          result = new String[zyzgMap.keySet().size() + 1][3];
          i = 0;
          for (Map.Entry<String, Integer> entry : zyzgMap.entrySet()) {
            result[i][0] = entry.getKey();
            result[i][1] = (String)entry.getValue();
            result[i][2] = "select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and  emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and empOther.zyzg like '%" + (String)entry.getKey() + "%' and " + wherePara;
            i++;
          } 
          sql = "select '其他',count(emp.emp_id) ,'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + field + " is null and " + wherePara + "'";
          sql = String.valueOf(sql) + from;
          sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


            
            field + " is null " + 
            "and " + wherePara;
          temp = bd.query(sql);
          if (temp != null) {
            result[i][0] = temp[0][0];
            result[i][1] = temp[0][1];
            result[i][2] = temp[0][2];
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else {
        sql = "select " + field + ",count(emp.emp_id),'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and  emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + field + " = '''||" + field + "||''' and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is not null " + 
          "and " + wherePara;
        sql = String.valueOf(sql) + " group by " + field;
        try {
          String[][] temp = bd.query(sql);
          if (temp != null) {
            result = new String[temp.length][3];
            for (int i = 0; i < temp.length; i++) {
              result[i][0] = temp[i][0];
              result[i][1] = temp[i][1];
              result[i][2] = temp[i][2];
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        sql = "select '其他',count(emp.emp_id) ,'select emp.emp_id from org_employee emp ,org_emp_otherInfo empother,org_organization_user org where emp.emp_id = org.emp_id and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted = 0 and " + field + " is null and " + wherePara + "'";
        sql = String.valueOf(sql) + from;
        sql = String.valueOf(sql) + " where emp.emp_ID = org.emp_id(+) and emp.emp_id = empOther.emp_id(+) and emp.emp_id > 0 and emp.userIsDeleted=0 and " + 


          
          field + " is null " + 
          "and " + wherePara;
        try {
          String[][] temp = bd.query(sql);
          if (temp != null)
            if (result == null) {
              result = temp;
            } else {
              String[][] result0 = new String[result.length + 1][3];
              for (int i = 0; i < result.length; i++) {
                result0[i][0] = result[i][0];
                result0[i][1] = result[i][1];
                result0[i][2] = result[i][2];
              } 
              result0[result0.length - 1][0] = temp[0][0];
              result0[result0.length - 1][1] = temp[0][1];
              result0[result0.length - 1][2] = temp[0][2];
              result = result0;
            }  
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      if ("tableQuery".equals(action)) {
        if (result != null) {
          String[][] result1 = new String[result.length][4];
          int sum = 0;
          int i;
          for (i = 0; i < result.length; i++) {
            if (result[i][1] == null || "".equals(result[i][1]))
              result[i][1] = "0"; 
            result1[i][0] = result[i][0];
            result1[i][1] = result[i][1];
            result1[i][3] = EncryptSelf.selfEncoder(result[i][2]);
            sum += Integer.valueOf(result[i][1]).intValue();
          } 
          if (sum == 0)
            sum = 1; 
          for (i = 0; i < result.length; i++)
            result1[i][2] = String.valueOf(Math.round((Integer.valueOf(result[i][1]).intValue() * 10000 / sum)) / 100.0D) + "%"; 
          httpServletRequest.setAttribute("result", result1);
        } else {
          httpServletRequest.setAttribute("result", null);
        } 
      } else if ("graphQuery".equalsIgnoreCase(action)) {
        httpServletRequest.setAttribute("fileName", getFlowEfficAnalyZhuChart(httpServletRequest, result, httpServletRequest.getParameter("fieldName")));
      } else if ("export".equalsIgnoreCase(action)) {
        String[][] result1 = new String[result.length][3];
        int sum = 0;
        int i;
        for (i = 0; i < result.length; i++) {
          result1[i][0] = result[i][0];
          result1[i][1] = result[i][1];
          sum += Integer.valueOf(result[i][1]).intValue();
        } 
        for (i = 0; i < result.length; i++)
          result1[i][2] = String.valueOf(Math.round((Integer.valueOf(result[i][1]).intValue() * 10000 / sum)) / 100.0D) + "%"; 
        httpServletRequest.setAttribute("result", result1);
        httpServletRequest.setAttribute("lx", httpServletRequest.getParameter("fieldName"));
      } 
    } else if ("showDetail".equalsIgnoreCase(action)) {
      tag = "showDetail";
      String sql = httpServletRequest.getParameter("sql");
      sql = EncryptSelf.selfDecoder(sql);
      list(httpServletRequest, sql);
    } 
    return actionMapping.findForward(tag);
  }
  
  private String getFlowEfficAnalyZhuChart(HttpServletRequest request, String[][] result, String fieldName) {
    int recordCount = result.length;
    double[][] data = new double[1][recordCount];
    String[] columnKeys = new String[recordCount];
    String categoryAxisLabel = fieldName;
    String valueAxisLabel = "人数";
    for (int i = 0; i < recordCount; i++) {
      columnKeys[i] = result[i][0];
      data[0][i] = Integer.valueOf(result[i][1]).intValue();
    } 
    int width = recordCount * 100;
    if (width < 600)
      width = 600; 
    String fileNames = getCreateZhuChart("工作人员[" + fieldName + "]分类分析", categoryAxisLabel, valueAxisLabel, 
        data, new String[] { "" }, columnKeys, request.getSession(), width, 450);
    return fileNames;
  }
  
  private String getCreateZhuChart(String title, String categoryAxisLabel, String valueAxisLabel, double[][] data, String[] rowKeys, String[] columnKeys, HttpSession session, int width, int height) {
    String filename = "";
    try {
      CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
          (Comparable[])rowKeys, (Comparable[])columnKeys, data);
      JFreeChart chart = ChartFactory.createBarChart3D(
          title, 
          categoryAxisLabel, 
          valueAxisLabel, 
          dataset, 
          PlotOrientation.VERTICAL, 
          true, 
          true, 
          false);
      Font font = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      CategoryPlot plot = chart.getCategoryPlot();
      plot.setNoDataMessageFont(font);
      CategoryAxis axis = plot.getDomainAxis();
      axis.setLabelFont(font);
      axis.setTickLabelFont(font);
      ValueAxis valueAxis = plot.getRangeAxis();
      valueAxis.setLabelFont(font);
      valueAxis.setTickLabelFont(font);
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(font); 
      plot.setBackgroundPaint(new Color(238, 244, 255));
      chart.setBackgroundPaint(Color.WHITE);
      ValueAxis rangeAxis = plot.getRangeAxis();
      rangeAxis.setUpperMargin(0.15D);
      rangeAxis.setLowerMargin(0.15D);
      plot.setRangeAxis(rangeAxis);
      BarRenderer3D renderer = new BarRenderer3D();
      renderer.setItemMargin(0.0D);
      renderer.setItemLabelGenerator((CategoryItemLabelGenerator)new StandardCategoryItemLabelGenerator());
      renderer.setItemLabelFont(new Font("楷体", 0, 12));
      renderer.setItemLabelsVisible(true);
      plot.setRenderer((CategoryItemRenderer)renderer);
      filename = ServletUtilities.saveChartAsPNG(chart, width, height, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  private String getFlowStatusChartPie(HttpServletRequest request, String[][] result) {
    HttpSession session = request.getSession(true);
    String fileNames = "";
    try {
      DefaultPieDataset dataset = new DefaultPieDataset();
      String[] title0 = new String[result.length];
      for (int i = 0; i < title0.length; i++) {
        title0[i] = result[i][0];
        dataset.setValue(title0[i], Double.parseDouble(String.valueOf(result[i][1])));
      } 
      fileNames = getFlowStatusChartFileName("", dataset, session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return fileNames;
  }
  
  private String getFlowStatusChartFileName(String title, DefaultPieDataset dataset, HttpSession session) {
    String filename = "";
    try {
      JFreeChart chart = ChartFactory.createPieChart3D(title, (PieDataset)dataset, true, false, false);
      Font font = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(font); 
      PiePlot pieplot = (PiePlot)chart.getPlot();
      pieplot.setLabelFont(font);
      pieplot.setLabelBackgroundPaint(Color.white);
      pieplot.setNoDataMessage("无数据显示");
      pieplot.setNoDataMessageFont(font);
      chart.setBackgroundPaint(Color.WHITE);
      pieplot.setCircular(false);
      pieplot.setLabelGap(0.02D);
      pieplot.setLabelGenerator((PieSectionLabelGenerator)new StandardPieSectionLabelGenerator(
            "{0} ：{1}"));
      pieplot.setOutlinePaint(Color.WHITE);
      pieplot.setShadowPaint(Color.WHITE);
      pieplot.setSectionPaint(0, Color.decode("#FF5555"));
      pieplot.setSectionPaint(1, Color.decode("#FF55FF"));
      pieplot.setSectionPaint(2, Color.decode("#5555FF"));
      pieplot.setSectionPaint(3, Color.decode("#55FF55"));
      filename = ServletUtilities.saveChartAsPNG(chart, 400, 240, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  private void list(HttpServletRequest httpServletRequest, String where) {
    HttpSession session = httpServletRequest.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    where = " po.emp_id =gx.emp_id and gx.org_id = orgpo.org_id and po.emp_Id in(" + where + ")";
    String viewSql = "po.emp_Id,po.empName,po.empEnglishName,po.empSex,po.empBirth,po.empDuty,po.empNativePlace,po.userAccounts,orgpo.orgNameString,po.userIsActive,po.empLeaderName,orgpo.org_id,po.empMobilePhone,'',po.userOnline,po.empEmail";
    String fromSql = " org_employee po,org_organization orgpo,org_organization_user gx ";
    String orderBy = " po.emp_id desc ";
    PageUtil page = new PageSqlUtil();
    List<Object> list = page.list(httpServletRequest, viewSql, fromSql, where, orderBy);
    httpServletRequest.setAttribute("employeeList", list);
  }
  
  private int getCount(String str) {
    int count = 0;
    for (int i = 0; str != null && i < str.length(); i++) {
      if (str.substring(i).startsWith("$"))
        count++; 
    } 
    return count;
  }
}
