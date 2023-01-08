package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.BryKqEJBBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BryKqBD {
  public List<String[]> getDataList(String sql) {
    BryKqEJBBean bean = new BryKqEJBBean();
    return bean.getDataList(sql);
  }
  
  public List<String[]> getYueList() {
    String sql = "SELECT kqtj_num,CONCAT(SUBSTRING(kqtj_num,1,4),'-',SUBSTRING(kqtj_num,5,6)) FROM bry_kqtj GROUP BY kqtj_num ORDER BY kqtj_num DESC";
    BryKqEJBBean bean = new BryKqEJBBean();
    List<String[]> yueList = bean.getYueList(sql);
    return yueList;
  }
  
  public List<String[]> getTiaoxiu(String userId) {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
    Integer num = Integer.valueOf(format.format(new Date()));
    String[] dates = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()).split("-");
    if (Integer.valueOf(dates[2]).intValue() > 25)
      if (dates[1].equals("12")) {
        num = Integer.valueOf(String.valueOf(Integer.valueOf(dates[0]).intValue() + 1) + "01");
      } else {
        num = Integer.valueOf(num.intValue() + 1);
      }  
    String sql = "SELECT e.empName,e.orgname,b.kqtj_cctx,b.kqtj_zsysytx,b.kqtj_dyxcx,b.kqtj_xsycx FROM org_user e LEFT JOIN bry_kqtj b ON e.EMP_ID=b.kqtj_userId AND b.kqtj_num=" + num + " WHERE e.EMP_ID=" + userId;
    return (new BryKqEJBBean()).getStrings(sql);
  }
  
  public List<String[]> getNianjia(String userId) {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
    Integer num = Integer.valueOf(format.format(new Date()));
    String[] dates = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()).split("-");
    if (Integer.valueOf(dates[2]).intValue() > 25)
      if (dates[1].equals("12")) {
        num = Integer.valueOf(String.valueOf(Integer.valueOf(dates[0]).intValue() + 1) + "01");
      } else {
        num = Integer.valueOf(num.intValue() + 1);
      }  
    String sql = "SELECT e.empName,e.orgname,b.kqtj_bndyxnj,b.kqtj_nczsysynj,b.kqtj_xsynj FROM org_user e LEFT JOIN bry_kqtj b ON e.EMP_ID=b.kqtj_userId AND b.kqtj_num=" + num + " WHERE e.EMP_ID=" + userId;
    return (new BryKqEJBBean()).getStrings(sql);
  }
}
