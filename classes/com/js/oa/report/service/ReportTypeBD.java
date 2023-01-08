package com.js.oa.report.service;

import com.js.oa.report.bean.ReportTypeEJBBean;
import com.js.oa.report.po.ReportTypePO;
import com.js.system.util.StaticParam;
import java.util.List;

public class ReportTypeBD {
  public Long saveReport(ReportTypePO po, String insertSite) {
    try {
      return (new ReportTypeEJBBean()).saveReportType(po, insertSite);
    } catch (Exception e) {
      e.printStackTrace();
      return Long.valueOf(0L);
    } 
  }
  
  public ReportTypePO loadReportType(Long TypeId) {
    try {
      return (new ReportTypeEJBBean()).loadReportType(TypeId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public boolean updateReportType(ReportTypePO po, String insertSite) {
    try {
      return (new ReportTypeEJBBean()).updateReportType(po, insertSite);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public boolean deteleReportType(String typeId) {
    try {
      return (new ReportTypeEJBBean()).deleteReportType(typeId);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public List getQueryByHql(String userId, String orgIdString, String otherWhere) {
    String para = "select po.typeId,po.typeName,po.createEmp,po.orderCode,po.typeLevel,po.parentId from com.js.oa.report.po.ReportTypePO po";
    String where = " where 1=1 ";
    String whereTmp = "";
    String[] orgIds = orgIdString.split("\\$");
    whereTmp = String.valueOf(whereTmp) + " and (po.viewScopeId like '%$" + userId + "$%' ";
    for (int i = 1; i < orgIds.length; i += 2)
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%*" + orgIds[i] + "*%' "; 
    String group = StaticParam.getGroupIdByEmpId(userId);
    String[] groups = group.split(",");
    for (int j = 1; j < groups.length; j++)
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%@" + groups[j] + "@%' "; 
    whereTmp = String.valueOf(whereTmp) + " ) ";
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    String orderBy = " order by po.orderCode";
    String hql = String.valueOf(para) + where + otherWhere + orderBy;
    return (new ReportTypeEJBBean()).queryHql(hql);
  }
  
  public int executeUpdate(String sql) {
    return (new ReportTypeEJBBean()).executeUpdate(sql);
  }
  
  public Object[] searchByParentId(Long parentId, Integer sortCode, Long id) {
    return (new ReportTypeEJBBean()).searchByParentId(parentId, sortCode, id);
  }
  
  public String getOpr(String opr) {
    String hql = "select po.typeId from com.js.oa.report.po.ReportTypePO po where " + opr;
    List<String> idList = (new ReportTypeEJBBean()).queryHql(hql);
    String id = ",";
    for (int i = 0; i < idList.size(); i++)
      id = String.valueOf(id) + idList.get(i) + ","; 
    return id;
  }
}
