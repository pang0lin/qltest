package com.js.oa.kingking;

import com.js.oa.userdb.util.DbOpt;
import java.util.Date;

public class KingkingService {
  public void updateYSYE(String recordId, String tableId) {
    DbOpt db = new DbOpt();
    try {
      String bxSQL = "SELECT jst_3078_id,jst_3078_owner,jst_3078_date,jst_3078_org,bx_niandu FROM jst_3078 WHERE jst_3078_id='" + recordId + "'";
      System.out.println("查询报销信息：" + bxSQL);
      String[][] bx = db.executeQueryToStrArr2(bxSQL, 5);
      String curYear = bx[0][4];
      if (bx != null && bx.length > 0) {
        String bxfylxSQL = "SELECT rq,fylx,fykm,je,bxdw FROM jst_3079 WHERE jst_3079_FOREIGNKEY='" + recordId + "'";
        System.out.println("查询报销子表信息：" + bxfylxSQL);
        String[][] bxfylx = db.executeQueryToStrArr2(bxfylxSQL, 5);
        if (bxfylx != null && bxfylx.length > 0)
          for (int i = 0; i < bxfylx.length; i++) {
            String updateZC = "update king_bmyszb set bmys_zc=(bmys_zc+" + bxfylx[i][3] + ") where king_bmyszb_foreignkey in " + 
              "(select king_bmysb_id from king_bmysb where king_bmysb_org=" + bxfylx[i][4].split(";")[1] + " and bmys_nd='" + curYear + "') " + 
              "and fylx=" + bxfylx[i][1];
            System.out.println("更新预算支出金额：" + updateZC);
            db.executeUpdate(updateZC);
            String insertRecord = "insert into king_record values ('" + bx[0][0] + "','" + bx[0][1] + "','" + bx[0][2] + 
              "','" + bxfylx[i][4].split(";")[1] + "','" + bxfylx[i][0] + "','" + bxfylx[i][1] + "','" + bxfylx[i][2] + "','" + bxfylx[i][3] + "')";
            System.out.println("保存报销记录：" + insertRecord);
            db.executeUpdate(insertRecord);
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void updateYSJEZJ(String tableId, String recordId) {
    DbOpt db = new DbOpt();
    int curYear = (new Date()).getYear() + 1900;
    try {
      String sql = "select distinct t.wf_submitemployee_id,t.worksubmittime from jsf_work t where t.worktable_id=" + tableId + " and t.workrecord_id=" + recordId + " and t.workstatus=100";
      String[][] info = db.executeQueryToStrArr2(sql, 2);
      String bxSQL = "SELECT king_yszj_id,king_yszj_owner,king_yszj_date,king_yszj_org,nd FROM king_yszj WHERE king_yszj_id='" + recordId + "'";
      System.out.println("查询预算追加信息：" + bxSQL);
      String[][] bx = db.executeQueryToStrArr2(bxSQL, 5);
      if (bx != null && bx.length > 0) {
        String sql3 = "select distinct king_bmysb_id from king_bmysb where king_bmysb_org=" + bx[0][3] + " and bmys_nd='" + bx[0][4] + "'";
        String[][] forenkey = db.executeQueryToStrArr2(sql3, 1);
        String bxfylxSQL = "SELECT fylx,zjje FROM king_yszjzb WHERE king_yszjzb_FOREIGNKEY='" + recordId + "'";
        System.out.println("查询预算追加子表信息：" + bxfylxSQL);
        String[][] bxfylx = db.executeQueryToStrArr2(bxfylxSQL, 2);
        if (bxfylx != null && bxfylx.length > 0)
          for (int i = 0; i < bxfylx.length; i++) {
            String sql1 = "select fylx from king_bmyszb where king_bmyszb_foreignkey in (select king_bmysb_id from king_bmysb where king_bmysb_org=" + bx[0][3] + " and bmys_nd='" + bx[0][4] + "') and fylx=" + bxfylx[i][0];
            String[][] fylx = db.executeQueryToStrArr2(sql1, 1);
            if (forenkey != null && forenkey.length > 0)
              if (fylx == null || fylx.length < 1) {
                String sql2 = "insert into king_bmyszb(king_bmyszb_id,king_bmyszb_owner,king_bmyszb_date,king_bmyszb_org,xh,fylx,bmys_ys,bmys_zc,king_bmyszb_foreignkey)values(hibernate_sequence.nextval," + 
                  info[0][0] + ",'" + info[0][1] + "'," + bx[0][3] + ",0," + bxfylx[i][0] + "," + bxfylx[i][1] + ",0," + forenkey[0][0] + ")";
                db.executeUpdate(sql2);
              } else {
                String updateZC = "update king_bmyszb set bmys_ys=(bmys_ys+" + bxfylx[i][1] + ") where king_bmyszb_foreignkey in " + 
                  "(select king_bmysb_id from king_bmysb where king_bmysb_org=" + bx[0][3] + " and bmys_nd='" + bx[0][4] + "') " + 
                  "and fylx=" + bxfylx[i][0];
                System.out.println("更新预算总金额：" + updateZC);
                db.executeUpdate(updateZC);
              }  
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void updateField(String recordId) {
    DbOpt db = new DbOpt();
    try {
      String bxSQL = "SELECT ziduan1 FROM jst_3078 WHERE jst_3078_id=" + recordId;
      String[][] bx = db.executeQueryToStrArr2(bxSQL, 1);
      if (bx != null && bx.length > 0) {
        String updateZC = "update jst_3078 set ziduan1='', ziduan2='" + bx[0][0] + "' where jst_3078_id=" + recordId;
        db.executeUpdate(updateZC);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
