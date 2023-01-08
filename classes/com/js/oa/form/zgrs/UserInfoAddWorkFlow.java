package com.js.oa.form.zgrs;

import com.js.oa.chinaLife.tbUser.FlowTongbuUser;
import com.js.oa.form.Workflow;
import com.js.oa.hr.finance.util.EmployeeSynchro;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public class UserInfoAddWorkFlow extends Workflow {
  public String synchro(HttpServletRequest request) {
    update(request);
    String result = "result";
    if ("1".equals(SystemCommon.getUseSAP())) {
      result = (new EmployeeSynchro()).sysnchroEmp(request, "rs_rzxx");
      if (!"".equals(result)) {
        request.setAttribute("ErrorMessage", result);
        return "error";
      } 
      result = "success";
    } 
    String recordId = request.getParameter("recordId");
    String userId = "0";
    if ("success".equals(result)) {
      String sql = "";
      String updSql = "update org_employee set ";
      String empStr = "empsex RS_RZXX_XB,empbirth RS_RZXX_CSNY,Empnation RS_RZXX_MZ,empNativePlace RS_RZXX_GG,empPolity RS_RZXX_ZZMM,Empemail RS_RZXX_DZYJ,empIdCard RS_RZXX_SFZ,empAddress RS_RZXX_JTZZ,empBusinessPhone RS_RZXX_BGDH,empMobilePhone RS_RZXX_SJHM,empDescribe RS_RZXX_TC,empInterest RS_RZXX_XQAH,empFireDate RS_RZXX_GZSJ,intoCompanyDate RS_RZXX_RSSJ,Empposition RS_RZXX_GW,empDuty RS_RZXX_ZW,empdutylevel RS_RZXX_ZJ,jobStatus RS_RZXX_SF";
      String[] empStrs = empStr.split(",");
      for (int i = 0; i < empStrs.length; i++) {
        String[] emp = empStrs[i].split(" ");
        sql = String.valueOf(sql) + "," + emp[1];
      } 
      sql = "select " + sql.substring(1) + ",RS_RZXX_RYID from RS_RZXX where RS_RZXX_id=" + recordId;
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        ResultSet rs = base.executeQuery(sql);
        if (rs.next()) {
          for (int k = 0; k < empStrs.length; k++) {
            String[] duiying = empStrs[k].split(" ");
            if (k == empStrs.length - 1) {
              updSql = String.valueOf(updSql) + duiying[0] + "=" + teshuStr((rs.getString(duiying[1]) == null) ? "" : rs.getString(duiying[1]), duiying[1]);
            } else {
              updSql = String.valueOf(updSql) + duiying[0] + "=" + teshuStr((rs.getString(duiying[1]) == null) ? "" : rs.getString(duiying[1]), duiying[1]) + ",";
            } 
          } 
          userId = (rs.getString("RS_RZXX_RYID") == null) ? "0" : rs.getString("RS_RZXX_RYID");
        } 
        rs.close();
        updSql = String.valueOf(updSql) + " where emp_id=" + userId;
        base.executeUpdate(updSql);
        sql = "select emp_id from org_emp_otherinfo where emp_id=" + userId;
        boolean isCun = false;
        rs = base.executeQuery(sql);
        if (rs.next())
          isCun = true; 
        sql = "";
        String empInfo = "csd RS_RZXX_CSD,SAP_ID RS_RZXX_SAP,CYM RS_RZXX_ZYM,hyzk RS_RZXX_HYZK,jkzk RS_RZXX_JKZK,zyzg RS_RZXX_ZYZG,wjgj RS_RZXX_GJ,zyjszc RS_RZXX_ZYCC,yjjzgj RS_RZXX_GJ1,hkszd RS_RZXX_HZSZD,qrz_zgxl RS_RZXX_ZGXL,qrz_zgxw RS_RZXX_ZGXW,qrz_byyxx RS_RZXX_BYYX,qrz_zy RS_RZXX_ZY1,zzjy_zgxl RS_RZXX_ZGXL1,zzjy_zgxw RS_RZXX_ZGXW1,zzjy_byyxx RS_RZXX_BYYX1,zzjy_zy RS_RZXX_ZY1,bmlx RS_RZXX_BMLX,sfjzgb RS_RZXX_JZGB,rxzsj RS_RZXX_RXZSH,rxjsj RS_RZXX_RXJSJ,wyhrz RS_RZXX_WYH,syqksrq RS_RZXX_KSSJ,syqjsrq RS_RZXX_JSSJ,syqqx RS_RZXX_SYQ,po_xm RS_RZXX_POXM,po_csny RS_RZXX_POCS,po_mz RS_RZXX_POMZ,po_jg RS_RZXX_POJG,po_zzmm RS_RZXX_POZJMM,po_cjgzsj RS_RZXX_SJ,po_xjzcs RS_RZXX_XJ,po_hkszd RS_RZXX_POHK,po_xl RS_RZXX_POXL,po_zyjszc RS_RZXX_POZYJ,po_byyxx RS_RZXX_POYX,po_zy RS_RZXX_POZY,po_gzdwjbm RS_RZXX_PODW,po_zw RS_RZXX_POZW,dt_rdsj RS_RZXX_PORT,dt_dnzw RS_RZXX_POD,dt_ssdzb RS_RZXX_POS,dt_zzgxszdw RS_RZXX_POZZ,hkxz HKXZ,sfcjshbx SFCJSHBX,sfczjggz SFCZJGGZ,stjfnx STJFNX,lxrxx LXRXX,sfhytsb SFHYTSB,hkszdyzbm HKSZDYZBM,jzdyzbm JZDYZBM,yjyzbm YJYZBM,ddyljg1 DDYLJG1,ddyljg2 DDYLJG2,ddyljg3 DDYLJG3,ddyljg4 DDYLJG4,ddyljg5 DDYLJG5,ydwsbjz YDWSBJZ,ydwsbjs YDWSBJS,ydwzfgjjsszx YDWZFGJJSSZX,rshblgjjsj RSHBLGJJSJ,sfydsznz SFYDSZNZ,YDWGJJJS YDWGJJJS,YDWGJJJZ YDWGJJJZ";
        String[] empInfos = empInfo.split(",");
        int j;
        for (j = 0; j < empInfos.length; j++) {
          String[] emp = empInfos[j].split(" ");
          sql = String.valueOf(sql) + "," + emp[1];
        } 
        sql = "select " + sql.substring(1) + " from RS_RZXX where RS_RZXX_id=" + recordId;
        rs = base.executeQuery(sql);
        if (isCun) {
          updSql = "update org_emp_otherinfo set ";
          if (rs.next())
            for (j = 0; j < empInfos.length; j++) {
              String[] duiying = empInfos[j].split(" ");
              if (j == empInfos.length - 1) {
                updSql = String.valueOf(updSql) + duiying[0] + "=" + teshuStr((rs.getString(duiying[1]) == null) ? "" : rs.getString(duiying[1]), duiying[1]);
              } else {
                updSql = String.valueOf(updSql) + duiying[0] + "=" + teshuStr((rs.getString(duiying[1]) == null) ? "" : rs.getString(duiying[1]), duiying[1]) + ",";
              } 
            }  
          rs.close();
          updSql = String.valueOf(updSql) + " where emp_id=" + userId;
        } else {
          updSql = "insert into org_emp_otherinfo (id,emp_id";
          String vaStr = " values (hibernate_sequence.nextval," + userId;
          if (rs.next())
            for (int k = 0; k < empInfos.length; k++) {
              String[] duiying = empInfos[k].split(" ");
              updSql = String.valueOf(updSql) + "," + duiying[0];
              vaStr = String.valueOf(vaStr) + "," + teshuStr((rs.getString(duiying[1]) == null) ? "" : rs.getString(duiying[1]), duiying[1]);
            }  
          rs.close();
          updSql = String.valueOf(updSql) + ")" + vaStr + ")";
        } 
        base.executeUpdate(updSql);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
      (new UserAddErwei()).insertInto(recordId, userId, "add");
      laoDong(recordId, userId);
    } else {
      request.setAttribute("ErrorMessage", result);
      return "error";
    } 
    FlowTongbuUser kemi = new FlowTongbuUser();
    kemi.kemiUser(recordId);
    return result;
  }
  
  public void laoDong(String recordId, String userId) {
    String sql = "select RS_RZXX_LDKS,RS_RZXX_LDJS,RS_RZXX_LDQX,RS_RZXX_HTH from RS_RZXX where RS_RZXX_id=" + recordId;
    DataSourceBase base = new DataSourceBase();
    String insertSql = "insert into ORG_EMPLOYEE_CONTRACT (id,emp_id,beginDate,endDate,contract_limit,signed_number,givendate) values (hibernate_sequence.nextval," + 
      userId;
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        insertSql = String.valueOf(insertSql) + ",to_date('" + ((rs.getString(1) == null) ? "" : rs.getString(1)) + "','yyyy-mm-dd')";
        insertSql = String.valueOf(insertSql) + ",to_date('" + ((rs.getString(2) == null) ? "" : rs.getString(2)) + "','yyyy-mm-dd')";
        insertSql = String.valueOf(insertSql) + ",'" + ((rs.getString(3) == null) ? "" : rs.getString(3)) + "'";
        insertSql = String.valueOf(insertSql) + ",'" + ((rs.getString(4) == null) ? "" : rs.getString(4)) + "'";
        insertSql = String.valueOf(insertSql) + ",to_date('" + ((rs.getString(1) == null) ? "" : rs.getString(1)) + "','yyyy-mm-dd')";
      } 
      insertSql = String.valueOf(insertSql) + ")";
      base.executeUpdate(insertSql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private String teshuStr(String str, String field) {
    String rValue = str;
    String timeL = ",RS_RZXX_CSNY,RS_RZXX_GZSJ,RS_RZXX_RSSJ,";
    String timeS = ",RS_RZXX_RXZSH,RS_RZXX_RXJSJ,RS_RZXX_PORT,RS_RZXX_KSSJ,RS_RZXX_JSSJ,RS_RZXX_POCS,RS_RZXX_SJ,";
    if (timeL.contains("," + field + ",")) {
      rValue = "to_date('" + str + "','yyyy-mm-dd')";
    } else if (timeS.contains("," + field + ",")) {
      rValue = "'" + str.replace("-", "/") + "'";
    } else if ("RS_RZXX_SF".equals(field)) {
      if ("正式员工".equals(str)) {
        rValue = "'正式'";
      } else if ("试用期员工".equals(str)) {
        rValue = "'试用'";
      } else {
        rValue = "'临时'";
      } 
    } else {
      rValue = "'" + CharacterTool.escapeHTMLTags(str) + "'";
    } 
    return rValue;
  }
}
