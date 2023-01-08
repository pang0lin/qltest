package com.js.oa.chinaLife.bean;

import com.js.util.util.DataSourceUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class KqNumUtil {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  private String todayStr = this.ymd.format(new Date());
  
  public int[] getKqNum(String userId, String orgId, String startDate, String endDate) {
    int[] kqNum = new int[5];
    KqShowBean kqShowBean = new KqShowBean();
    String viewSql = "k.kq_id,k.kq_userId,k.kq_userName,k.kq_userNum,k.kq_userOrg,k.kq_date,k.kq_sb,k.kq_sbType,k.kq_sbBuD,k.kq_sbbqContext,k.kq_xb,k.kq_xbType,k.kq_xbBuD,k.kq_xbbqContext,o.Orgname ";
    String fromSql = " rst_kq k join org_organization o on k.kq_userorg=o.org_id ";
    String where = " k.kq_date between '" + startDate + "' and '" + endDate + "'";
    if (userId.equals("") && orgId.equals(""))
      where = String.valueOf(where) + " and 1<>1"; 
    if (!"".equals(userId))
      where = String.valueOf(where) + " and k.kq_userId in (" + userId + ") "; 
    if (!"".equals(orgId)) {
      where = String.valueOf(where) + " and (k.kq_userOrg in (select org_id from org_organization where 1<>1 ";
      String[] orgIds = orgId.replace("**", ",").replace("*", "").split(",");
      for (int j = 0; j < orgIds.length; j++)
        where = String.valueOf(where) + " or (orgIdString like'%$" + orgIds[j] + "$%' and orgstatus=0)"; 
      where = String.valueOf(where) + "))";
    } 
    String orderBy = " k.kq_userId,k.kq_date";
    List<String[]> kqData = (new DataSourceUtil()).getListQuery("select " + viewSql + " from " + fromSql + " where " + where + " order by " + orderBy, "");
    for (int i = 0; i < kqData.size(); i++) {
      String[] kq = kqData.get(i);
      if (kqShowBean.isQingjia(kq[1], kq[5], "09:00")) {
        kqNum[4] = kqNum[4] + 1;
      } else if (!"1".equals(kq[7])) {
        if ("2".equals(kq[7])) {
          kqNum[2] = kqNum[2] + 1;
        } else if ("3".equals(kq[7])) {
          kqNum[0] = kqNum[0] + 1;
        } else if ("4".equals(kq[7])) {
          kqNum[1] = kqNum[1] + 1;
        } else if ("5".equals(kq[7])) {
          kqNum[4] = kqNum[4] + 1;
        } else {
          "6".equals(kq[7]);
        } 
      } 
      if (!kq[5].equals(this.todayStr))
        if (kqShowBean.isQingjia(kq[1], kq[5], "17:00")) {
          kqNum[4] = kqNum[4] + 1;
        } else if (!"1".equals(kq[11])) {
          if ("2".equals(kq[11])) {
            kqNum[3] = kqNum[3] + 1;
          } else if ("3".equals(kq[11])) {
            kqNum[0] = kqNum[0] + 1;
          } else if ("4".equals(kq[11])) {
            kqNum[1] = kqNum[1] + 1;
          } else if ("5".equals(kq[11])) {
            kqNum[4] = kqNum[4] + 1;
          } else {
            "6".equals(kq[11]);
          } 
        }  
    } 
    return kqNum;
  }
}
