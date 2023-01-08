package com.js.oa.hntdxy;

import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataService {
  public Object[] getWorkItem(String workId) {
    Object[] obj = (Object[])null;
    String viewSql = "aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.wfWorkId, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup  ,aaa.workDeadlineDate,aaa.stickie,aaa.workReadMarker  ";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where aaa.wfWorkId=" + Long.valueOf(workId);
    int pageSize = 1;
    int offset = 0;
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    if (list != null)
      obj = list.get(0); 
    return obj;
  }
  
  public List getInformationDetail(String informationId) {
    List<String> list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "select a.INFORMATIONHEAD,a.INFORMATIONTYPE,b.CHANNELTYPE from OA_INFORMATION a left join OA_INFORMATIONCHANNEL b on a.CHANNEL_ID=b.CHANNEL_ID where a.INFORMATION_ID=" + informationId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        list.add(rs.getString(1));
        list.add(rs.getString(2));
        list.add(rs.getString(3));
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
}
