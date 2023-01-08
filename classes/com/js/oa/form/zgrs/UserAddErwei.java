package com.js.oa.form.zgrs;

import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.util.ArrayList;
import java.util.List;

public class UserAddErwei {
  public void insertInto(String recordId, String userId, String flag) {
    String field = "id hibernate_sequence.nextval,gx ZNXX_GX,xm ZNXX_XM,csny ZNXX_CSMY,zzmm ZNXX_ZZMM,gzdwjbm ZNXX_BMDWJBM,zw ZNXX_ZW,sfzhm SFZHM";
    List<String> list = getSqlList(field, "ORG_EMP_CHILDREN", userId, "RS_RZ_ZNXX", recordId);
    update(list);
    field = "id hibernate_sequence.nextval,gx QTCY_GX,xm QTCY_XM,csny QTCY_CSNY,zzmm QTCY_ZZMM,gzdwjbm QTCY_GZDWJBM,zw QTCY_ZW,bz QTCY_BZ,rzgrsqk QTCY_RZGRSQK,rzftjysgbqk QTCY_RZFTJYSGBQK";
    list = getSqlList(field, "ORG_EMP_OTHERMEM", userId, "ORG_EMPLOYEE_", recordId);
    update(list);
    field = "id hibernate_sequence.nextval,gx GNW_GX,xm GNW_XM,csny GNW_CSNY,zzmm GNW_ZZMM,gzdwjbm GNW_GZDWJBM,zw GNW_ZW,bz GNW_BZ";
    list = getSqlList(field, "ORG_EMP_GNWGX", userId, "ORG_EMPLOYEE_GNWGX", recordId);
    update(list);
    field = "id hibernate_sequence.nextval,hjsj JCXX_HJSJ,hjmc JCXX_HJMC,hjsx JCXX_HJSX,hjjb JCXX_HJJB,cfsj JCXX_CFSJ,cfmc JCXX_CFMC";
    list = getSqlList(field, "ORG_EMP_JCXX", userId, "ORG_EMPLOYEE_JCXX", recordId);
    update(list);
    field = "id hibernate_sequence.nextval,beginDate JYJL_KSSJ,endDate JYJL_JSSJ,schools JYJL_JDYX,speciality JYJL_ZY,education JYJL_XL,degree JYJL_XW";
    list = getSqlList(field, "ORG_EMPLOYEE_EDUSTORY", userId, "RS_RZ_JYJL", recordId);
    update(list);
    field = "id hibernate_sequence.nextval,begindate GZJL_KSSJ,endDate GZJL_JSSJ,workunit GZJL_GZJLJBM,workduty GZJL_ZW,workmemo GZJL_GZDD";
    list = getSqlList(field, "ORG_EMPLOYEE_work", userId, "RS_RZ_GZJL", recordId);
    update(list);
    field = "id hibernate_sequence.nextval,kssj PXJL_KSSJ,jssj PXJL_JSSJ,zxs PXJL_ZXS,cjpxxm PXJL_CJPXXM,pxxz PXJL_PXXZ,hdzs PXJL_HDZS,pxdd PXJL_PXDD";
    list = getSqlList(field, "ORG_EMP_pxjl", userId, "ORG_EMPLOYEE_PXJL", recordId);
    update(list);
    field = "ID BANKID,BANKCARDNAME BANKCARDNAME,BANKCARDNO BANKCARDNO";
    list = getSqlList(field, "ORG_EMP_BANKCARDINFO", userId, "rs_yhk", recordId);
    update(list);
  }
  
  private List<String> getSqlList(String field, String tableName, String userId, String erWeiName, String recordId) {
    String[] fields = field.split(",");
    String sqlFiled = "";
    for (int i = 0; i < fields.length; i++) {
      String[] f = fields[i].split(" ");
      sqlFiled = String.valueOf(sqlFiled) + "," + f[1];
    } 
    String sql = "select " + sqlFiled.substring(1) + " from " + erWeiName + " where " + erWeiName + "_foreignkey=" + recordId;
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    List<String> sqlList = new ArrayList<String>();
    for (int j = 0; j < list.size(); j++) {
      String insertField = "insert into " + tableName + " (emp_id";
      String insertValue = " values (" + userId;
      String[] obj = list.get(j);
      for (int k = 0; k < obj.length; k++) {
        String[] f = fields[k].split(" ");
        insertField = String.valueOf(insertField) + "," + f[0];
        insertValue = String.valueOf(insertValue) + "," + teshuStr(obj[k], f[1]);
      } 
      insertField = String.valueOf(insertField) + ")";
      insertValue = String.valueOf(insertValue) + ")";
      sqlList.add(String.valueOf(insertField) + insertValue);
    } 
    return sqlList;
  }
  
  private void update(List<String> list) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      for (int i = 0; i < list.size(); i++)
        base.executeUpdate(list.get(i)); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private String teshuStr(String str, String field) {
    String rValue = str;
    String timeL = ",JYJL_KSSJ,JYJL_JSSJ,GZJL_KSSJ,GZJL_JSSJ,RS_XG_LDHT_LDKSSJ,RS_XG_LDHT_LDJSSJ,";
    String timeS = ",PXJL_KSSJ,PXJL_JSSJ,JYJL_KSSJ,JYJL_JSSJ,JCXX_HJSJ,JCXX_CFSJ,ZNXX_CSMY,QTCY_CSNY,GNW_CSNY,";
    if (timeL.contains("," + field + ",")) {
      rValue = "to_date('" + str + "','yyyy-mm-dd')";
    } else if (timeS.contains("," + field + ",")) {
      rValue = "'" + str.replace("-", "/") + "'";
    } else if ("hibernate_sequence.nextval".equals(field)) {
      rValue = str;
    } else {
      rValue = "'" + CharacterTool.escapeHTMLTags(str) + "'";
    } 
    return rValue;
  }
}
