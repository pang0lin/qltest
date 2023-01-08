package com.js.oa.hr.kq.bean;

import com.js.oa.userdb.util.DbOpt;

public class BhwKqBean {
  private static String DSNAME = "bhwkq";
  
  public void getInfos() {
    getCQInfos();
    getQDInfos();
  }
  
  private void getCQInfos() {
    DbOpt db = new DbOpt(DSNAME);
    DbOpt dbLocal = new DbOpt();
    try {
      dbLocal.getConnection().setAutoCommit(false);
      db.getConnection().setAutoCommit(false);
      String[][] items = db.executeQueryToStrArr2("select id from v_checkinfo where mark=2", 1);
      if (items != null && items.length > 0) {
        for (int i = 0; i < items.length; i++)
          dbLocal.getStatement().addBatch("delete from bhw_cqqk where id=" + items[i][0]); 
        dbLocal.getStatement().executeBatch();
        dbLocal.commit();
      } 
      items = db.executeQueryToStrArr2("select id,personnumber,DATE_FORMAT(AttDate, '%Y-%m-%d'),ATTRESULT from v_checkinfo where mark=0", 4);
      if (items != null && items.length > 0) {
        String sql = "";
        String[] item = (String[])null;
        for (int i = 0; i < items.length; i++) {
          item = items[i];
          sql = "update bhw_cqqk set ATTRESULT='" + item[3] + "' where personnumber='" + item[1] + "' and to_date(attdate,'yyyy-MM-dd')=to_date('" + item[2] + "','yyyy-MM-dd') and id=" + item[0];
          dbLocal.getStatement().addBatch(sql);
        } 
        int[] result = dbLocal.getStatement().executeBatch();
        dbLocal.getConnection().commit();
        if (result != null && result.length > 0)
          for (int j = 0; j < result.length; j++) {
            item = items[j];
            if (result[j] == 0) {
              item = items[j];
              sql = "insert into bhw_cqqk(id,personnumber,attdate,ATTRESULT)values(" + item[0] + ",'" + item[1] + "','" + item[2] + "','" + item[3] + "')";
              dbLocal.getStatement().addBatch(sql);
            } 
            db.getStatement().addBatch("update v_checkinfo set mark=1 where id=" + item[0]);
          }  
        dbLocal.getStatement().executeBatch();
        dbLocal.getConnection().commit();
        db.getStatement().executeBatch();
        db.getConnection().commit();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      try {
        dbLocal.getConnection().rollback();
        db.getConnection().rollback();
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
    } finally {
      try {
        db.getConnection().setAutoCommit(true);
        dbLocal.getConnection().setAutoCommit(true);
        db.close();
        dbLocal.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private void getQDInfos() {
    DbOpt db = new DbOpt(DSNAME);
    DbOpt dbLocal = new DbOpt();
    try {
      dbLocal.getConnection().setAutoCommit(false);
      db.getConnection().setAutoCommit(false);
      String[][] items = db.executeQueryToStrArr2("select id from v_attinfo where mark=2", 1);
      if (items != null && items.length > 0) {
        for (int i = 0; i < items.length; i++)
          dbLocal.getStatement().addBatch("delete from bhw_qdqk where id=" + items[i][0]); 
        dbLocal.getStatement().executeBatch();
        dbLocal.commit();
      } 
      items = db.executeQueryToStrArr2("select id,personnumber,DATE_FORMAT(AttDate, '%Y-%m-%d'),STARTTIME,ENDTIME from v_attinfo where mark=0", 5);
      if (items != null && items.length > 0) {
        String sql = "";
        String[] item = (String[])null;
        for (int i = 0; i < items.length; i++) {
          item = items[i];
          sql = "update bhw_qdqk set starttime='" + item[3] + "',endtime='" + item[4] + 
            "' where to_date(attdate,'yyyy-MM-dd')=to_date('" + item[2] + "','yyyy-MM-dd') and personnumber='" + item[1] + "' and id=" + item[0];
          dbLocal.getStatement().addBatch(sql);
        } 
        int[] result = dbLocal.getStatement().executeBatch();
        dbLocal.getConnection().commit();
        if (result != null && result.length > 0)
          for (int j = 0; j < result.length; j++) {
            item = items[j];
            if (result[j] == 0) {
              sql = "insert into bhw_qdqk(id,personnumber,attdate,starttime,endtime)values(" + item[0] + ",'" + item[1] + "','" + item[2] + "','" + item[3] + "','" + item[4] + "')";
              dbLocal.getStatement().addBatch(sql);
            } 
            db.getStatement().addBatch("update v_attinfo set mark=1 where id=" + item[0]);
          }  
        dbLocal.getStatement().executeBatch();
        dbLocal.getConnection().commit();
        db.getStatement().executeBatch();
        db.getConnection().commit();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      try {
        dbLocal.getConnection().rollback();
        db.getConnection().rollback();
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
    } finally {
      try {
        db.getConnection().setAutoCommit(true);
        dbLocal.getConnection().setAutoCommit(true);
        db.close();
        dbLocal.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
