package com.js.oa.hr.kq.bry;

import com.js.oa.hr.kq.bry.util.BryKqUtil;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BryTongji {
  public void kqTongbu(String beginDate, String endDate) {
    IO2File.printFile("考勤统计的开始日期：" + beginDate + "  结束日期为：" + endDate + "  如果考勤不在此范围内，暂时不做统计", "宝日医考勤统计", 3);
    SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
    Integer num = Integer.valueOf(format.format(new Date()));
    String[] dates = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()).split("-");
    if (Integer.valueOf(dates[2]).intValue() > 25)
      if (dates[1].equals("12")) {
        num = Integer.valueOf(String.valueOf(Integer.valueOf(dates[0]).intValue() + 1) + "01");
      } else {
        num = Integer.valueOf(num.intValue() + 1);
      }  
    String sql = "select orgnameString,empName,'','','','','','','','','','','','','','','','','','','','','','','','','','','','','',emp_id,org_id,'" + 
      num + "' from org_user order by orgIdString,USERORDERCODE,empdutylevel,emp_id";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "0");
    BryKqUtil util = new BryKqUtil();
    for (int i = 0; i < list.size(); i++) {
      Object[] kq = (Object[])list.get(i);
      kq[2] = util.chuQinTianShu(beginDate, endDate, (String)kq[31]);
      kq[3] = util.chiDaoCishu(beginDate, endDate, (String)kq[31]);
      float[] qj = util.qjTongji(beginDate, endDate, (String)kq[31]);
      kq[4] = (new StringBuilder(String.valueOf(qj[0]))).toString();
      kq[5] = (new StringBuilder(String.valueOf(qj[1]))).toString();
      kq[6] = (new StringBuilder(String.valueOf(qj[2]))).toString();
      kq[7] = (new StringBuilder(String.valueOf(qj[3]))).toString();
      kq[8] = (new StringBuilder(String.valueOf(qj[4]))).toString();
      kq[9] = (new StringBuilder(String.valueOf(qj[5]))).toString();
      kq[10] = (new StringBuilder(String.valueOf(qj[6]))).toString();
      kq[11] = (new StringBuilder(String.valueOf(qj[7]))).toString();
      kq[12] = (new StringBuilder(String.valueOf(qj[8]))).toString();
      kq[13] = (new StringBuilder(String.valueOf(qj[9]))).toString();
      kq[14] = (new StringBuilder(String.valueOf(qj[10]))).toString();
      kq[15] = (new StringBuilder(String.valueOf(qj[11]))).toString();
      kq[16] = (new StringBuilder(String.valueOf(qj[12]))).toString();
      kq[17] = (new StringBuilder(String.valueOf(qj[13]))).toString();
      String[] wuCan = util.wuCanFei(beginDate, endDate, (String)kq[31]);
      kq[18] = wuCan[0];
      kq[19] = wuCan[1];
      kq[20] = wuCan[2];
      String[] nianJia = util.nianJia(beginDate, endDate, (String)kq[31]);
      kq[21] = nianJia[0];
      kq[22] = nianJia[1];
      kq[23] = nianJia[2];
      String[] tiaoxiu = util.tiaoXiu(beginDate, endDate, (String)kq[31], num.intValue(), (String)kq[12]);
      kq[24] = tiaoxiu[0];
      kq[25] = tiaoxiu[1];
      kq[26] = tiaoxiu[2];
      String[] heji = util.heji(new String[] { (String)kq[23], (String)kq[26], (String)kq[2], (new StringBuilder(String.valueOf(qj[qj.length - 1]))).toString(), "20" });
      kq[27] = heji[0];
      kq[28] = heji[1];
      kq[29] = heji[2];
      kq[30] = heji[3];
    } 
    insertTongji(list, (String)num);
  }
  
  private void insertTongji(List<String[]> list, String num) {
    String deleteSql = "delete from bry_kqtj where kqtj_num=" + num;
    String sql = "insert into bry_kqtj (kqtj_orgname,kqtj_username,kqtj_kqts,kqtj_cd,kqtj_bj,kqtj_sj,kqtj_cj,kqtj_chj,kqtj_brj,kqtj_nj,kqtj_hj,kqtj_qt,kqtj_cctx,kqtj_cczts,kqtj_htxts,kqtj_jbpt,kqtj_jbsx,kqtj_jbfdjr,kqtj_wcwcts,kqtj_bcqts,kqtj_bcqwcf,kqtj_bndyxnj,kqtj_nczsysynj,kqtj_xsynj,kqtj_zsysytx,kqtj_dyxcx,kqtj_xsycx,kqtj_hjsxxj,kqtj_sjcqts,kqtj_rjtf,kqtj_sjjtf,kqtj_userId,kqtj_orgId,kqtj_num) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    try {
      conn = base.getDataSource().getConnection();
      conn.createStatement().executeUpdate(deleteSql);
      PreparedStatement ps = conn.prepareStatement(sql);
      for (int i = 0; i < list.size(); i++) {
        String[] tj = list.get(i);
        ps.setString(1, tj[0]);
        ps.setString(2, tj[1]);
        ps.setFloat(3, Float.valueOf(tj[2]).floatValue());
        ps.setFloat(4, Float.valueOf(tj[3]).floatValue());
        ps.setFloat(5, Float.valueOf(tj[4]).floatValue());
        ps.setFloat(6, Float.valueOf(tj[5]).floatValue());
        ps.setFloat(7, Float.valueOf(tj[6]).floatValue());
        ps.setFloat(8, Float.valueOf(tj[7]).floatValue());
        ps.setFloat(9, Float.valueOf(tj[8]).floatValue());
        ps.setFloat(10, Float.valueOf(tj[9]).floatValue());
        ps.setFloat(11, Float.valueOf(tj[10]).floatValue());
        ps.setFloat(12, Float.valueOf(tj[11]).floatValue());
        ps.setFloat(13, Float.valueOf(tj[12]).floatValue());
        ps.setFloat(14, Float.valueOf(tj[13]).floatValue());
        ps.setFloat(15, Float.valueOf(tj[14]).floatValue());
        ps.setFloat(16, Float.valueOf(tj[15]).floatValue());
        ps.setFloat(17, Float.valueOf(tj[16]).floatValue());
        ps.setFloat(18, Float.valueOf(tj[17]).floatValue());
        ps.setFloat(19, Float.valueOf(tj[18]).floatValue());
        ps.setFloat(20, Float.valueOf(tj[19]).floatValue());
        ps.setFloat(21, Float.valueOf(tj[20]).floatValue());
        ps.setFloat(22, Float.valueOf(tj[21]).floatValue());
        ps.setFloat(23, Float.valueOf(tj[22]).floatValue());
        ps.setFloat(24, Float.valueOf(tj[23]).floatValue());
        ps.setFloat(25, Float.valueOf(tj[24]).floatValue());
        ps.setFloat(26, Float.valueOf(tj[25]).floatValue());
        ps.setFloat(27, Float.valueOf(tj[26]).floatValue());
        ps.setFloat(28, Float.valueOf(tj[27]).floatValue());
        ps.setFloat(29, Float.valueOf(tj[28]).floatValue());
        ps.setFloat(30, Float.valueOf(tj[29]).floatValue());
        ps.setFloat(31, Float.valueOf(tj[30]).floatValue());
        ps.setLong(32, Long.valueOf(tj[31]).longValue());
        ps.setLong(33, Long.valueOf(tj[32]).longValue());
        ps.setLong(34, Integer.valueOf(tj[33]).intValue());
        ps.execute();
      } 
      conn.close();
    } catch (SQLException e) {
      try {
        conn.close();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public void daKaRemind() {
    String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    IO2File.printFile("***" + dateStr + "考勤打卡提醒***", "打卡提醒");
    String sql = "SELECT emp_id FROM org_employee WHERE emp_id NOT IN (SELECT jst_3012_owner FROM jst_3012 WHERE CURDATE()=jst_3012_f3098) AND emp_id>0";
    String insertsql = "INSERT INTO sys_messages (message_touserid,message_type,message_title,message_time,message_send_userid,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) VALUES(?,'Chat','您在" + 
      
      dateStr + "日还未打卡!!!',NOW(),0,'系统提醒',1,0,NOW(),'2050-01-01 00:00:00',0)";
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement ps = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    try {
      conn = base.getDataSource().getConnection();
      ps = conn.createStatement();
      pst = conn.prepareStatement(insertsql);
      rs = ps.executeQuery(sql);
      while (rs.next()) {
        pst.setString(1, rs.getString(1));
        pst.executeUpdate();
        IO2File.printFile("未打卡人：" + rs.getString(1) + "\n", "打卡提醒");
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (ps != null)
          ps.close(); 
        if (pst != null)
          pst.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
  }
}
