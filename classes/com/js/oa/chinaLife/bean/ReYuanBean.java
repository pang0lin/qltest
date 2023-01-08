package com.js.oa.chinaLife.bean;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.sf.json.JSONArray;

public class ReYuanBean {
  public String getReYuan(String userId) {
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    String sql = "select ot.SAP_ID RS_RZXX_SAP,ot.CYM RS_RZXX_ZYM,e.empsex RS_RZXX_XB,e.empbirth RS_RZXX_CSNY,e.Empnation RS_RZXX_MZ,e.empNativePlace RS_RZXX_GG,ot.csd RS_RZXX_CSD,e.empPolity RS_RZXX_ZZMM,ot.hyzk RS_RZXX_HYZK,ot.jkzk RS_RZXX_JKZK,ot.zyzg RS_RZXX_ZYZG,e.empBusinessPhone RS_RZXX_BGDH,e.empMobilePhone RS_RZXX_SJHM,(CASE WHEN ot.wjgj IS NULL THEN '否' ELSE '是' END) RS_RZXX_SFWJ,ot.wjgj RS_RZXX_GJ,ot.zyjszc RS_RZXX_ZYCC, (CASE WHEN ot.yjjzgj IS NULL THEN '否' ELSE '是' END ) RS_RZXX_WJJZ,ot.yjjzgj RS_RZXX_GJ1,e.Empemail RS_RZXX_DZYJ,e.empIdCard RS_RZXX_SFZ,e.empAddress RS_RZXX_JTZZ,ot.hkszd RS_RZXX_HZSZD,e.empDescribe RS_RZXX_TC,e.empInterest RS_RZXX_XQAH,ot.qrz_zgxl RS_RZXX_ZGXL,ot.qrz_zgxw RS_RZXX_ZGXW,ot.qrz_byyxx RS_RZXX_BYYX,ot.qrz_zy RS_RZXX_ZY,ot.zzjy_zgxl RS_RZXX_ZGXL1,ot.zzjy_zgxw RS_RZXX_ZGXW1,ot.zzjy_byyxx RS_RZXX_BYYX1,ot.zzjy_zy RS_RZXX_ZY1,e.empFireDate RS_RZXX_GZSJ,e.intoCompanyDate RS_RZXX_RSSJ,substr(o.Orgnamestring,instr(o.Orgnamestring,'.',1,1)+1) RS_RZXX_BM#RS_RZXX_CS,e.Empposition RS_RZXX_GW,e.empDuty RS_RZXX_ZW,e.empdutylevel RS_RZXX_ZJ,'' RS_RZXX_XL,ot.bmlx RS_RZXX_BMLX,ot.sfjzgb RS_RZXX_JZGB,ot.rxzsj RS_RZXX_RXZSH,ot.rxjsj RS_RZXX_RXJSJ,ot.wyhrz RS_RZXX_WYH,ot.syqksrq RS_RZXX_KSSJ,ot.syqjsrq RS_RZXX_JSSJ,ot.syqqx RS_RZXX_SYQ,(CASE WHEN e.jobStatus='正式' THEN '正式员工' when e.jobStatus='试用' then '试用期员工' else '挂职' END) RS_RZXX_SF,'' RS_RZXX_LDKS,'' RS_RZXX_LDJS,'' RS_RZXX_LDQX,'' RS_RZXX_HTH,ot.po_xm RS_RZXX_POXM,ot.po_csny RS_RZXX_POCS,ot.po_mz RS_RZXX_POMZ,ot.po_jg RS_RZXX_POJG,ot.po_zzmm RS_RZXX_POZJMM,ot.po_cjgzsj RS_RZXX_SJ,ot.po_xjzcs RS_RZXX_XJ,ot.po_hkszd RS_RZXX_POHK,ot.po_xl RS_RZXX_POXL,ot.po_zyjszc RS_RZXX_POZYJ,ot.po_byyxx RS_RZXX_POYX,ot.po_zy RS_RZXX_POZY,ot.po_gzdwjbm RS_RZXX_PODW,ot.po_zw RS_RZXX_POZW,ot.dt_rdsj RS_RZXX_PORT,ot.dt_dnzw RS_RZXX_POD,ot.dt_ssdzb RS_RZXX_POS,ot.dt_zzgxszdw RS_RZXX_POZZ,e.empNumber RS_RZXX_GH,ot.hkxz HKXZ,ot.sfcjshbx SFCJSHBX,ot.sfczjggz SFCZJGGZ,ot.stjfnx STJFNX,ot.lxrxx LXRXX,ot.sfhytsb SFHYTSB,ot.hkszdyzbm HKSZDYZBM,ot.jzdyzbm JZDYZBM,ot.yjyzbm YJYZBM,ot.ddyljg1 DDYLJG1,ot.ddyljg2 DDYLJG2,ot.ddyljg3 DDYLJG3,ot.ddyljg4 DDYLJG4,ot.ddyljg5 DDYLJG5,ot.ydwsbjz YDWSBJZ,ot.ydwsbjs YDWSBJS,ot.ydwzfgjjsszx YDWZFGJJSSZX,ot.rshblgjjsj RSHBLGJJSJ,ot.sfydsznz SFYDSZNZ,ot.YDWGJJJS YDWGJJJS,ot.YDWGJJJZ YDWGJJJZ,ot.rszfw RS_RZXX_RSZFW,ot.zw RS_RZXX_ZHIWU FROM org_employee e LEFT JOIN org_emp_otherinfo ot ON e.emp_id=ot.emp_id JOIN org_organization_user ou ON e.emp_id=ou.emp_id JOIN org_organization o ON ou.org_id=o.org_id WHERE e.userisactive=1 and e.emp_id=" + 





















      
      userId;
    String timeL = ",RS_RZXX_CSNY,RS_RZXX_GZSJ,RS_RZXX_RSSJ,";
    String timeS = ",RS_RZXX_RXZSH,RS_RZXX_RXJSJ,RS_RZXX_PORT,RS_RZXX_KSSJ,RS_RZXX_JSSJ,RS_RZXX_POCS,RS_RZXX_SJ,";
    String fields = "";
    Map<String, String> map = new HashMap<String, String>();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      ResultSetMetaData rsData = rs.getMetaData();
      if (rs.next())
        for (int i = 1; i <= rsData.getColumnCount(); i++) {
          String field = rsData.getColumnName(i);
          fields = String.valueOf(fields) + field + ",";
          if (timeL.contains("," + field + ",")) {
            if ("RS_RZXX_CSNY".equals(field)) {
              int age = (new Date()).getYear() + 1900;
              if (rs.getString(field) != null)
                age = Integer.valueOf(rs.getString(field).substring(0, 4)).intValue(); 
              map.put("RS_RZXX_NL", (new StringBuilder(String.valueOf((new Date()).getYear() - age + 1900))).toString());
            } 
            map.put(field, ((rs.getString(field) == null) ? ymd.format(new Date()) : rs.getString(field)).substring(0, 10));
          } else if (timeS.contains("," + field + ",")) {
            map.put(field, (rs.getString(field) == null) ? ymd.format(new Date()) : ymd.format(ymd.parse(rs.getString(field).replace("/", "-"))));
          } else if ("RS_RZXX_BM#RS_RZXX_CS".equals(field)) {
            String[] bumen = field.split("#");
            String[] bumenV = { "", "" };
            if (rs.getString(field) != null)
              if (rs.getString(field).contains(".")) {
                bumenV = rs.getString(field).split("\\.");
              } else {
                bumenV[0] = rs.getString(field);
              }  
            map.put(bumen[0], bumenV[0]);
            map.put(bumen[1], bumenV[1]);
          } else {
            map.put(field, teshuStr((rs.getString(field) == null) ? "" : rs.getString(field)));
          } 
        }  
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    JSONArray json = JSONArray.fromObject(map);
    return json.toString();
  }
  
  public String erWeiBiao(String userId, String tableName) {
    String sql = "";
    if ("RS_XG_LDHT".equals(tableName)) {
      sql = "SELECT id RS_XG_LDHT_OA_ID,beginDate RS_XG_LDHT_LDKSSJ,endDate RS_XG_LDHT_LDJSSJ,contract_limit RS_XG_LDHT_LDHTQX,signed_number RS_XG_LDHT_LDHTBH FROM ORG_EMPLOYEE_CONTRACT WHERE emp_id=" + 
        userId + " order by id";
    } else if ("jst_3019".equals(tableName)) {
      sql = "SELECT id ZNXX_OA_ID,gx ZNXX_GX,xm ZNXX_XM,csny ZNXX_CSMY,zzmm ZNXX_ZZMM,gzdwjbm ZNXX_BMDWJBM,zw ZNXX_ZW,sfzhm SFZHM FROM ORG_EMP_CHILDREN WHERE emp_id=" + userId + " order by id";
    } else if ("jst_3024".equals(tableName)) {
      sql = "SELECT id QTCY_OA_ID,gx QTCY_GX,xm QTCY_XM,csny QTCY_CSNY,zzmm QTCY_ZZMM,gzdwjbm QTCY_GZDWJBM,zw QTCY_ZW,bz QTCY_BZ,rzgrsqk QTCY_RZGRSQK,rzftjysgbqk QTCY_RZFTJYSGBQK FROM ORG_EMP_OTHERMEM WHERE emp_id=" + 
        userId + " order by id";
    } else if ("jst_3027".equals(tableName)) {
      sql = "SELECT id GNW_OA_ID,gx GNW_GX,xm GNW_XM,csny GNW_CSNY,zzmm GNW_ZZMM,gzdwjbm GNW_GZDWJBM,zw GNW_ZW,bz GNW_BZ FROM ORG_EMP_GNWGX WHERE emp_id=" + userId + " order by id";
    } else if ("jst_3025".equals(tableName)) {
      sql = "SELECT id JCXX_OA_ID,hjsj JCXX_HJSJ,hjmc JCXX_HJMC,hjsx JCXX_HJSX,hjjb JCXX_HJJB,cfsj JCXX_CFSJ,cfmc JCXX_CFMC FROM ORG_EMP_JCXX WHERE emp_id=" + userId + " order by id";
    } else if ("jst_3020".equals(tableName)) {
      sql = "SELECT id JYJL_OA_ID,beginDate JYJL_KSSJ,endDAte JYJL_JSSJ,schools JYJL_JDYX,speciality JYJL_ZY,education JYJL_XL,degree JYJL_XW FROM ORG_EMPLOYEE_EDUSTORY WHERE emp_id=" + 
        userId + " order by id";
    } else if ("jst_3021".equals(tableName)) {
      sql = "SELECT id GZJL_OA_ID,begindate GZJL_KSSJ,endDate GZJL_JSSJ,workunit GZJL_GZJLJBM,workduty GZJL_ZW,workmemo GZJL_GZDD FROM ORG_EMPLOYEE_work WHERE emp_id=" + 
        userId + " order by id";
    } else if ("jst_3026".equals(tableName)) {
      sql = "SELECT id PXJL_OA_ID,kssj PXJL_KSSJ,jssj PXJL_JSSJ,zxs PXJL_ZXS,cjpxxm PXJL_CJPXXM,pxxz PXJL_PXXZ,hdzs PXJL_HDZS,pxdd PXJL_PXDD FROM ORG_EMP_pxjl WHERE emp_id=" + 
        userId + " order by id";
    } else if ("RS_YHK".equals(tableName)) {
      sql = "SELECT ID BANKID,BANKCARDNAME BANKCARDNAME,BANKCARDNO BANKCARDNO FROM ORG_EMP_BANKCARDINFO WHERE emp_id=" + userId;
    } 
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    if (!"".equals(sql)) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        ResultSet rs = base.executeQuery(sql);
        ResultSetMetaData rsData = rs.getMetaData();
        while (rs.next()) {
          Map<String, String> map = new HashMap<String, String>();
          for (int i = 1; i <= rsData.getColumnCount(); i++) {
            String field = rsData.getColumnName(i);
            map.put(field, teshuStr((rs.getString(field) == null) ? "" : rs.getString(field)));
          } 
          list.add(map);
        } 
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    JSONArray json = JSONArray.fromObject(list);
    return json.toString();
  }
  
  public String teshuStr(String str) {
    Pattern pattern = Pattern.compile("^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$");
    if (str.endsWith(" 00:00:00.0")) {
      str = str.substring(0, str.indexOf(" 00:00:00.0"));
    } else if (pattern.matcher(str).matches()) {
      str = str.replace("/", "-");
    } 
    return str;
  }
  
  public static void main(String[] aa) {
    ReYuanBean eBean = new ReYuanBean();
    eBean.teshuStr("");
  }
}
