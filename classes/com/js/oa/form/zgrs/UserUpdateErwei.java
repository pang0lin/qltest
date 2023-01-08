package com.js.oa.form.zgrs;

import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.util.ArrayList;
import java.util.List;

public class UserUpdateErwei {
  public void insertInto(String recordId, String userId, String flag) {
    String field = "id RS_XG_LDHT_OA_ID,beginDate RS_XG_LDHT_LDKSSJ,endDate RS_XG_LDHT_LDJSSJ,contract_limit RS_XG_LDHT_LDHTQX,signed_number RS_XG_LDHT_LDHTBH,givendate RS_XG_LDHT_LDKSSJ";
    List<String> list = getSqlList(field, "ORG_EMPLOYEE_CONTRACT", userId, "RS_XG_LDHT", recordId);
    update(list);
    field = "id ZNXX_OA_ID,gx ZNXX_GX,xm ZNXX_XM,csny ZNXX_CSMY,zzmm ZNXX_ZZMM,gzdwjbm ZNXX_BMDWJBM,zw ZNXX_ZW,sfzhm SFZHM";
    list = getSqlList(field, "ORG_EMP_CHILDREN", userId, "jst_3019", recordId);
    update(list);
    field = "id QTCY_OA_ID,gx QTCY_GX,xm QTCY_XM,csny QTCY_CSNY,zzmm QTCY_ZZMM,gzdwjbm QTCY_GZDWJBM,zw QTCY_ZW,bz QTCY_BZ,rzgrsqk QTCY_RZGRSQK,rzftjysgbqk QTCY_RZFTJYSGBQK";
    list = getSqlList(field, "ORG_EMP_OTHERMEM", userId, "jst_3024", recordId);
    update(list);
    field = "id GNW_OA_ID,gx GNW_GX,xm GNW_XM,csny GNW_CSNY,zzmm GNW_ZZMM,gzdwjbm GNW_GZDWJBM,zw GNW_ZW,bz GNW_BZ";
    list = getSqlList(field, "ORG_EMP_GNWGX", userId, "jst_3027", recordId);
    update(list);
    field = "id JCXX_OA_ID,hjsj JCXX_HJSJ,hjmc JCXX_HJMC,hjsx JCXX_HJSX,hjjb JCXX_HJJB,cfsj JCXX_CFSJ,cfmc JCXX_CFMC";
    list = getSqlList(field, "ORG_EMP_JCXX", userId, "jst_3025", recordId);
    update(list);
    field = "id JYJL_OA_ID,beginDate JYJL_KSSJ,endDate JYJL_JSSJ,schools JYJL_JDYX,speciality JYJL_ZY,education JYJL_XL,degree JYJL_XW";
    list = getSqlList(field, "ORG_EMPLOYEE_EDUSTORY", userId, "jst_3020", recordId);
    update(list);
    field = "id GZJL_OA_ID,begindate GZJL_KSSJ,endDate GZJL_JSSJ,workunit GZJL_GZJLJBM,workduty GZJL_ZW,workmemo GZJL_GZDD";
    list = getSqlList(field, "ORG_EMPLOYEE_work", userId, "jst_3021", recordId);
    update(list);
    field = "id PXJL_OA_ID,kssj PXJL_KSSJ,jssj PXJL_JSSJ,zxs PXJL_ZXS,cjpxxm PXJL_CJPXXM,pxxz PXJL_PXXZ,hdzs PXJL_HDZS,pxdd PXJL_PXDD";
    list = getSqlList(field, "ORG_EMP_pxjl", userId, "jst_3026", recordId);
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
    String ids = "0";
    for (int j = 0; j < list.size(); j++) {
      String[] obj = list.get(j);
      String updateSql = "";
      if (obj[0].equals("")) {
        updateSql = "insert into " + tableName + " (id,emp_id";
        String insertValue = " values (hibernate_sequence.nextval," + userId;
        for (int k = 1; k < obj.length; k++) {
          String[] f = fields[k].split(" ");
          updateSql = String.valueOf(updateSql) + "," + f[0];
          insertValue = String.valueOf(insertValue) + "," + teshuStr(obj[k], f[1]);
        } 
        updateSql = String.valueOf(updateSql) + ")" + insertValue + ")";
      } else {
        ids = String.valueOf(ids) + "," + obj[0];
        updateSql = "update " + tableName + " set emp_id=" + userId;
        for (int k = 1; k < obj.length; k++) {
          String[] f = fields[k].split(" ");
          updateSql = String.valueOf(updateSql) + "," + f[0] + "=" + teshuStr(obj[k], f[1]);
        } 
        updateSql = String.valueOf(updateSql) + " where id=" + obj[0];
      } 
      sqlList.add(updateSql);
    } 
    deleteItem(tableName, userId, ids);
    return sqlList;
  }
  
  private void deleteItem(String tableName, String userId, String ids) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "delete from " + tableName + " where emp_id=" + userId + " and id not in (" + ids + ")";
      base.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private void delete(String tableName, String userId) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "delete from " + tableName + " where emp_id=" + userId;
      base.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
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
