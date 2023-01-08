package com.js.oa.jsflow.util;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.HttpClientVisitPage;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YouNengCRMDealWith {
  public String visitPage(String recordId, String curActivityId, String tableId, String status) {
    String returnValue = "";
    String[] crmInfo = getCRMId(tableId, recordId);
    if (!crmInfo.equals("")) {
      String nextPoint = "";
      String dateStr = "";
      String userName = "";
      String view = "";
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        ResultSet rs = base.executeQuery("SELECT c.DEALWITHEMPLOYEECOMMENT,c.dealwithtime,d.NEXTACTIVITYNAME,e.empName FROM jsf_dealwithcomment c JOIN jsf_dealwith d ON c.WF_DEALWITH_ID=d.WF_DEALWITH_ID JOIN org_employee e ON c.DEALWITHEMPLOYEE_ID=e.EMP_ID WHERE d.databasetable_id=" + 
            
            tableId + " AND d.databaserecord_id=" + recordId + 
            " AND d.activity_id=" + curActivityId + " order by d.WF_DEALWITH_ID desc");
        if (rs.next()) {
          view = (rs.getString(1) == null) ? "" : rs.getString(1);
          userName = (rs.getString(4) == null) ? "" : rs.getString(4);
          dateStr = (rs.getString(2) == null) ? (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) : rs.getString(2);
          nextPoint = (rs.getString(3) == null) ? "" : rs.getString(3);
        } 
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
      Map<String, String> paraMap = getParaMap(crmInfo, userName, dateStr, status, view, "审批中".equals(status) ? nextPoint : "结束");
      returnValue = visitPageResult(paraMap);
    } 
    return returnValue;
  }
  
  public String back(String userName, String tableId, String recordId, String dealTips) {
    String returnValue = "";
    String[] crmInfo = getCRMId(tableId, recordId);
    if (!crmInfo.equals("")) {
      Map<String, String> paraMap = getParaMap(crmInfo, userName, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), 
          "已驳回", dealTips, "发起人");
      returnValue = visitPageResult(paraMap);
    } 
    return returnValue;
  }
  
  private String[] getCRMId(String tableId, String recordId) {
    String crmRecordId = "";
    String linkType = "";
    List<String> list = (new DataSourceUtil()).getQuery("SELECT f.field_id,f.field_name FROM tfield f JOIN ttable t ON f.field_table=t.table_id JOIN tarea a ON t.table_name=a.area_table WHERE a.area_name='form1' AND LOWER(f.field_name)='crmid' and a.page_id=" + 
        
        tableId, "");
    if (list.size() > 0) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        String tableName = "";
        ResultSet rs = base.executeQuery("SELECT area_table FROM tarea WHERE page_id=" + tableId + " AND area_name='form1'");
        if (rs.next())
          tableName = (rs.getString(1) == null) ? "" : rs.getString(1); 
        rs.close();
        rs = base.executeQuery("select crmId,LinkType from " + tableName + " where " + tableName + "_id=" + recordId);
        if (rs.next()) {
          crmRecordId = (rs.getString(1) == null) ? "" : rs.getString(1);
          linkType = (rs.getString(2) == null) ? "" : rs.getString(2);
        } 
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
    return new String[] { crmRecordId, linkType };
  }
  
  private Map<String, String> getParaMap(String[] crmInfo, String userName, String dateStr, String status, String view, String nextPoint) {
    Map<String, String> paraMap = new HashMap<String, String>();
    Map<String, String> sMap = new HashMap<String, String>();
    sMap.put("未审批", "1_1036_1");
    sMap.put("审批中", "1_1036_2");
    sMap.put("已审批", "1_1036_3");
    sMap.put("已驳回", "1_1036_4");
    String[] linkNo = getLinkNo(crmInfo[0]);
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><request user=\"su\" pwd=\"Uonone@2014\"><import catexkeys='1'><fields><Opportunity recId=\"" + 


      
      crmInfo[0] + "\">" + 
      "<F7047>" + (String)sMap.get(status) + "</F7047>" + 
      "</Opportunity>" + 
      "<ApprovalRecord>" + 
      "<LinkType>" + crmInfo[1] + "</LinkType>" + 
      "<LinkStatNo>" + linkNo[0] + "</LinkStatNo>" + 
      "<LinkSeqNo>" + linkNo[1] + "</LinkSeqNo>" + 
      "<F7023>" + userName + "</F7023>" + 
      "<F7024>" + dateStr + "</F7024>" + 
      "<F7025>" + status + "</F7025>" + 
      "<F7026>" + view + "</F7026>" + 
      "<F7027>" + nextPoint + "</F7027>" + 
      "</ApprovalRecord>" + 
      "</fields>" + 
      "</import>" + 
      "</request>";
    paraMap.put("xml", xml);
    System.out.println("参数：xml    值：" + xml);
    return paraMap;
  }
  
  public String[] getLinkNo(String crmId) {
    if (crmId.equals("") || "null".equalsIgnoreCase((new StringBuilder(String.valueOf(crmId))).toString()))
      return new String[] { "", "" }; 
    String recLong = Long.toHexString(Long.valueOf(crmId).longValue());
    String linkStatNo = "0";
    String linkSeqNo = "0";
    if (recLong.length() > 8) {
      linkStatNo = recLong.substring(0, recLong.length() - 8);
      int j = linkStatNo.length();
      for (int k = 0; k < 8 - j; k++)
        linkStatNo = "0" + linkStatNo; 
      linkSeqNo = recLong.substring(recLong.length() - 8);
    } else {
      linkSeqNo = recLong;
    } 
    int length = linkSeqNo.length();
    for (int i = 0; i < 8 - length; i++)
      linkSeqNo = "0" + linkSeqNo; 
    Long statNo = Long.valueOf(Long.parseLong(linkStatNo, 16));
    Long seqNo = Long.valueOf(Long.parseLong(linkSeqNo, 16));
    return new String[] { (String)statNo, (String)seqNo };
  }
  
  private String visitPageResult(Map<String, String> paraMap) {
    return HttpClientVisitPage.visitPage("http", "10.129.0.22", 80, "/CRMinterface/XML", paraMap, "post");
  }
}
