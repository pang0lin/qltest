package com.js.oa.userdb.newCode.bean;

import com.js.oa.userdb.newCode.po.MaxCodeSetRecord;
import com.js.oa.userdb.newCode.po.NewCodePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeManageDao extends HibernateBase {
  public Map<String, MaxCodeSetRecord> getMaxCodeSetRecord(long codeId) {
    Map<String, MaxCodeSetRecord> map = new HashMap<String, MaxCodeSetRecord>();
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery("select s.sub_codeid,s.sub_codesetid,s.sub_dateformat,s.sub_num from JSF_NEWCODE_LOG_SUB s where s.sub_codeid='" + codeId + "'");
      while (rs.next()) {
        MaxCodeSetRecord record = new MaxCodeSetRecord();
        record.setCodeId(rs.getLong("sub_codeid"));
        record.setCodeSetId(rs.getLong("sub_codesetid"));
        record.setDateFormat(rs.getString("sub_dateformat"));
        record.setNum(rs.getInt("sub_num"));
        record.setIsUsered(0);
        map.put((new StringBuilder(String.valueOf(record.getCodeSetId()))).toString(), record);
      } 
      rs.close();
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return map;
  }
  
  public void maxCodeSetRecordSaveOrUpdate(List<String> sqlList) {
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      base.executeSqlList(sqlList);
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
  }
  
  public boolean executeSql(String sql) {
    boolean result = false;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      result = base.executeSQL(sql);
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public int checkCodeExist(long codeId, String codeNumber) {
    String sql = "SELECT LOG_CODENUMBER FROM JSF_NEWCODE_LOG WHERE LOG_CODEID='" + codeId + "' AND LOG_CODENUMBER='" + codeNumber + "'";
    int count = 0;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        count++; 
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      count = 1;
      ex.printStackTrace();
    } 
    return count;
  }
  
  public int checkCodeUsered(long codeId, String codeNumber, long proKey) {
    String sql = "SELECT LOG_ISUSERED,LOG_PRIKEY FROM JSF_NEWCODE_LOG WHERE LOG_CODEID='" + codeId + "' AND LOG_CODENUMBER='" + codeNumber + "'";
    int result = 1;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        int usered = rs.getInt("LOG_ISUSERED");
        long logPriKey = rs.getLong("LOG_PRIKEY");
        if (usered == 1 && proKey == logPriKey) {
          result = 0;
        } else {
          result = usered;
        } 
      } 
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String getNoUserCode(long codeId, String codeNumberFormat) {
    String sql = "SELECT LOG_CODENUMBER FROM JSF_NEWCODE_LOG WHERE LOG_CODEID='" + codeId + "' AND LOG_ISUSERED='0' AND LOG_CODENUMBER like'" + codeNumberFormat + "'";
    String result = "";
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        result = rs.getString("LOG_CODENUMBER"); 
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public List<NewCodePO> getCodeList() {
    List<NewCodePO> list = new ArrayList<NewCodePO>();
    String sql = "select code_id,code_name,code_content from jsF_newcode where code_status='1' order by code_id desc";
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        NewCodePO po = new NewCodePO();
        po.setCodeContent(rs.getString("code_content"));
        po.setCodeId(rs.getLong("code_id"));
        po.setCodeName(rs.getString("code_name"));
        list.add(po);
      } 
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return list;
  }
}
