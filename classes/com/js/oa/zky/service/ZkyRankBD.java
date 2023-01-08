package com.js.oa.zky.service;

import com.js.oa.zky.bean.ZkyRankBean;
import java.util.List;

public class ZkyRankBD {
  public List<String[]> getScoreList(String nd, String orgId, String flag) {
    ZkyRankBean bean = new ZkyRankBean();
    if ("1".equals(flag)) {
      int c = 5;
      String countSql = "select count(s.score_id) from zky_score s join org_employee e on s.score_job=e.Empnumber where s.score_nd=" + 
        
        nd + " and parentOrgId=" + orgId;
      int num = bean.getScoreNum(countSql);
      if (c > num) {
        String updateSql = "update zky_score set score_level=1";
        bean.executeUpdate(updateSql);
      } else {
        int step = num / c;
        for (int i = 0; i < c; i++) {
          String updateId = "-1";
          int b = i * step + 1;
          int e = (i == c - 1) ? num : ((i + 1) * step);
          String str1 = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select s.score_id from zky_score s where s.score_nd=" + 
            
            nd + " and parentOrgId=" + orgId + 
            " order by s.score_number desc " + 
            ") A WHERE ROWNUM <= " + e + ")WHERE RN >=" + b;
          List<String[]> ids = bean.getScoreList(str1);
          for (int t = 0; t < ids.size(); t++) {
            String[] id = ids.get(t);
            updateId = String.valueOf(updateId) + "," + id[0];
          } 
          String updateSql = "update zky_score set score_level=" + (i + 1) + " where score_id in (" + updateId + ")";
          bean.executeUpdate(updateSql);
        } 
      } 
    } 
    String sql = "select e.Empname,s.score_job,s.score_number,s.score_level,s.score_id from zky_score s join org_employee e on s.score_job=e.Empnumber where s.score_nd=" + 
      
      nd + " and parentOrgId=" + orgId + 
      " order by s.score_level,s.score_number desc";
    return bean.getScoreList(sql);
  }
  
  public void changeLevel(String level, String id) {
    String sql = "update zky_score set score_level=" + level + " where score_id=" + id;
    ZkyRankBean bean = new ZkyRankBean();
    bean.executeUpdate(sql);
  }
}
