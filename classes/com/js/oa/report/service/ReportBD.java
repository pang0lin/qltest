package com.js.oa.report.service;

import com.js.oa.report.bean.ReportEJBBean;
import com.js.oa.report.po.ReportPO;
import com.js.system.util.StaticParam;
import java.util.List;

public class ReportBD {
  public Long saveReport(ReportPO po) {
    try {
      return (new ReportEJBBean()).saveReport(po);
    } catch (Exception e) {
      e.printStackTrace();
      return Long.valueOf(0L);
    } 
  }
  
  public ReportPO loadReport(Long reportId) {
    try {
      return (new ReportEJBBean()).loadReport(reportId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public boolean updateReport(ReportPO po) {
    try {
      return (new ReportEJBBean()).updateReport(po);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public boolean deteleReport(Long reportId) {
    try {
      return (new ReportEJBBean()).deleteReport(reportId);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public List getReportInfo(String userId, String orgIdString, String domainId, String corpId) {
    String hql = "select po.reportId,po.reportName,po.reportUrl,tpo.typeName from com.js.oa.report.po.ReportPO po, com.js.oa.report.po.ReportTypePO tpo where po.reportType = tpo.typeId ";
    String whereTmp = "";
    String typeWhere = "";
    String[] orgIds = orgIdString.split("\\$");
    whereTmp = String.valueOf(whereTmp) + " and (po.viewScopeId like '%$" + userId + "$%' ";
    typeWhere = String.valueOf(typeWhere) + " and (tpo.viewScopeId like '%$" + userId + "$%' ";
    for (int i = 1; i < orgIds.length; i += 2) {
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%*" + orgIds[i] + "*%' ";
      typeWhere = String.valueOf(typeWhere) + " or tpo.viewScopeId like '%*" + orgIds[i] + "*%' ";
    } 
    String group = StaticParam.getGroupIdByEmpId(userId);
    String[] groups = group.split(",");
    for (int j = 1; j < groups.length; j++) {
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%@" + groups[j] + "@%' ";
      typeWhere = String.valueOf(typeWhere) + " or tpo.viewScopeId like '%@" + groups[j] + "@%' ";
    } 
    whereTmp = String.valueOf(whereTmp) + " ) ";
    typeWhere = String.valueOf(typeWhere) + " ) ";
    ReportEJBBean bean = new ReportEJBBean();
    return bean.queryHql(String.valueOf(hql) + whereTmp + typeWhere + " order by po.reportId");
  }
  
  public List getType(String hql) {
    ReportEJBBean bean = new ReportEJBBean();
    return bean.queryHql(hql);
  }
  
  public String getOperation(String opr) {
    String hql = "select po.reportId from com.js.oa.report.po.ReportPO po where " + opr;
    List<String> idList = (new ReportEJBBean()).queryHql(hql);
    String id = ",";
    for (int i = 0; i < idList.size(); i++)
      id = String.valueOf(id) + idList.get(i) + ","; 
    return id;
  }
}
