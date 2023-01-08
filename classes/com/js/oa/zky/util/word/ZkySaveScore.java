package com.js.oa.zky.util.word;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.Map;

public class ZkySaveScore {
  public void insertScore(String jobNumber, String nd, double[] score, Map<String, String> grxx) {
    String jq = grxx.get("杰青"), br = grxx.get("百人"), gmsx = grxx.get("冠名"), qr = grxx.get("千人"), wr = grxx.get("万人");
    String cheng = "," + (jq.equals("1") ? "j" : "0") + "," + (br.equals("1") ? "b" : "0") + "," + (gmsx.equals("1") ? "g" : "0") + "," + (
      qr.equals("1") ? "q" : "0") + "," + (wr.equals("1") ? "w" : "0") + ",";
    DataSourceBase base = new DataSourceBase();
    String sql = "select score_id,score_flag from zky_score where score_job='" + jobNumber + "' and score_nd='" + nd + "'";
    String scoreId = "0";
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      String sqlGo = "0";
      if (rs.next()) {
        if ("0".equals(rs.getString(2)))
          sqlGo = "1"; 
        scoreId = rs.getString(1);
      } else {
        sqlGo = "2";
      } 
      rs.close();
      String parentOrgId = "0";
      rs = base.executeQuery("select o.orgIdString from org_employee e join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id where e.Empnumber='" + 
          jobNumber + "'");
      if (rs.next()) {
        String[] orgIdString = ((rs.getString(1) == null) ? "" : rs.getString(1)).split("\\$");
        parentOrgId = (orgIdString.length > 5) ? orgIdString[5] : "0";
      } 
      rs.close();
      if (!"0".equals(sqlGo)) {
        if ("1".equals(sqlGo)) {
          sql = "update zky_score set score_number=" + score[14] + ",score_ch='" + cheng + "',score_grxx=" + score[0] + ",score_lwzz=" + score[1] + ",score_xshy=" + score[2] + 
            ",score_kyxm=" + score[3] + ",score_yhdjx=" + score[4] + ",score_zzsbjx=" + score[5] + ",score_sbzl=" + score[6] + ",score_rcpy=" + score[7] + ",score_shrzjz=" + score[8] + 
            ",score_cf=" + score[9] + ",score_lf=" + score[10] + ",score_gjhz=" + score[11] + ",score_ydhz=" + score[12] + ",score_zxbg=" + score[13] + ",parentOrgId=" + parentOrgId + 
            " where score_id=" + scoreId;
        } else if ("2".equals(sqlGo)) {
          sql = "insert into zky_score (score_id,score_job,score_nd,score_number,score_ch,score_grxx,score_lwzz,score_xshy,score_kyxm,score_yhdjx,score_zzsbjx,score_sbzl,score_rcpy,score_shrzjz,score_cf,score_lf,score_gjhz,score_ydhz,score_zxbg,score_flag,parentOrgId) values (hibernate_sequence.nextval,'" + 
            
            jobNumber + 
            "','" + nd + "'," + score[14] + ",'" + cheng + "'," + score[0] + "," + score[1] + "," + score[2] + "," + score[3] + "," + score[4] + "," + score[5] + 
            "," + score[6] + "," + score[7] + "," + score[8] + "," + score[9] + "," + score[10] + "," + score[11] + "," + score[12] + "," + score[13] + ",0," + parentOrgId + ")";
        } 
        base.executeUpdate(sql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  public void updateScore(String jobNumber, String nd, double score, String field) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "update zky_score set " + field + "=" + score + ",score_flag=1 where score_job='" + jobNumber + "' and score_nd='" + nd + "'";
      base.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
}
