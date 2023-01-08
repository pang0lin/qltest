package com.js.oa.userdb.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class CustomDBEJBBean implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String[][] getModelInfo(String domainid) {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select model_id,model_code,model_name,model_dis,model_owner,model_date from tModel where domain_id=" + 
          domainid + 
          " order by model_date desc", 6);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return list;
  }
  
  public int addModel(String code, String name, String remark, String user, String domainid) {
    int success = 0;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String model = dbopt.executeQueryToStr(
          "select model_id from tmodel where model_name='" + name + 
          "' and domain_id=" + domainid);
      if (model == null || model.trim().length() < 1) {
        if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          success = dbopt.executeUpdate(
              "Insert Into tModel (Model_code,Model_name,Model_dis,Model_owner,Model_date,DOMAIN_ID) values('" + 
              code + "','" + name + "','" + 
              remark + "','" + user + "',now()," + 
              domainid + ")");
        } else {
          success = dbopt.executeUpdate(
              "Insert Into tModel (Model_code,Model_name,Model_dis,Model_owner,DOMAIN_ID) values('" + 
              code + "','" + name + "','" + 
              remark + "','" + user + "'," + 
              domainid + ")");
        } 
      } else {
        success = -1;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public String getDefaultModelId(String domainid) {
    String model = "0";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      model = dbopt.executeQueryToStr("select model_id from tmodel where domain_id=" + domainid);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return model;
  }
  
  public int batchDelete(String[] modelID) {
    int success = 0;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < modelID.length; i++) {
        String[] tableid = dbopt.executeQueryToStrArr1(
            "select table_id from ttable where table_model=" + 
            modelID[i]);
        if (tableid != null)
          for (int j = 0; j < tableid.length; j++) {
            dbopt.executeUpdate(
                "delete from tlimit where limit_table =" + 
                tableid[j] + " or limit_prytable=" + tableid[j]);
            dbopt.executeUpdate(
                "delete from tfield where field_table=" + 
                tableid[j]);
            dbopt.executeUpdate(
                "delete from ttable where table_id =" + 
                tableid[j]);
          }  
        success = dbopt.executeUpdate(
            "delete from tModel where model_id =" + modelID[i]);
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return success;
  }
  
  public int delSingleModel(String modelid) throws Exception {
    int success = 0;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String[] tableid = dbopt.executeQueryToStrArr1(
          "select table_id from ttable where table_model=" + modelid);
      if (tableid != null)
        for (int j = 0; j < tableid.length; j++) {
          dbopt.executeUpdate(
              "delete from tlimit where limit_table =" + 
              tableid[j] + " or limit_prytable=" + tableid[j]);
          dbopt.executeUpdate("delete from tfield where field_table=" + 
              tableid[j]);
          dbopt.executeUpdate("delete from ttable where table_id =" + 
              tableid[j]);
        }  
      success = dbopt.executeUpdate("delete from tModel where model_id =" + 
          modelid);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public void updateModel(String modelid, String modelname, String modelcode, String modeldis) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      dbopt.executeUpdate("update tmodel set model_name='" + modelname + 
          "',model_code='" + modelcode + "',model_dis='" + 
          modeldis + "' where model_id=" + modelid);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public String[][] getModelIDName(String domainid) throws Exception {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select model_id,model_name from tModel where DOMAIN_ID=" + 
          domainid + " order by model_date desc");
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getTableInfo(String domainid) {
    String[][] list = (String[][])null;
    if (domainid.length() < 1) {
      domainid = "-1";
    } else {
      domainid = "0";
    } 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.table_id,a.table_desname,a.table_name,b.model_name,a.table_owner,a.table_date,a.table_code,a.table_filepath from ttable a, tModel b where a.table_model=b.model_id and a.DOMAIN_ID=" + 
          domainid + 
          " order by b.model_date desc,table_model,table_date desc", 
          8);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getTableInfo(String model, String tabledesname, String domainid) {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.table_id,a.table_desname,a.table_name,b.model_name,a.table_owner,a.table_date,a.table_code,a.table_filepath from ttable a, tModel b where a.table_model=b.model_id " + ((
          model == null || 
          model.trim().length() < 1 || 
          model.toUpperCase().equals(
            "NULL")) ? "" : ("and a.table_model=" + model)) + 
          " and a.table_desname like '%" + 
          tabledesname + 
          "%' and a.DOMAIN_ID=" + domainid + 
          " order by b.model_date desc,table_model,table_date desc", 
          8);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public int addTable(String tablecode, String tabledesname, String tablename, String tablemodel, String tablefilepath, String tableowner, String domainid, String empId, String orgId) throws Exception {
    int success = 1;
    DbOpt dbopt = new DbOpt();
    Statement stat = null;
    try {
      String table = dbopt.executeQueryToStr(
          "select table_id from ttable where table_desname='" + 
          tabledesname + "' and domain_id=" + domainid);
      if (table == null || table.length() < 1) {
        stat = dbopt.getStatement();
        String sql1 = "";
        String sql2 = "";
        if (DbOpt.dbtype.indexOf("oracle") >= 0) {
          sql1 = 
            "insert into tTable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,DOMAIN_ID,CREATEDEMP,CREATEDORG) VALUES('" + 
            tablecode + "','" + tabledesname + "','" + 
            tablename + "'," + 
            tablemodel + ",'" + tablefilepath + "','" + 
            tableowner + 
            "'," + domainid + "," + empId + "," + orgId + ")";
          sql2 = "create table " + tablename + 
            " (" + 
            tablename + "_ID NUMBER default '' not null," + 
            tablename + "_OWNER NUMBER default '0'," + 
            tablename + "_DATE  VARCHAR2(24) default to_char(sysdate,'YYYY-mm-dd') ," + 
            tablename + "_org VARCHAR2(100)," + 
            tablename + "_group VARCHAR2(100)," + 
            tablename + "_relaByInde VARCHAR2(500) ," + 
            tablename + "_chargBy VARCHAR2(500) ," + 
            tablename + "_orderByMa VARCHAR2(500) ," + 
            tablename + "_assignState VARCHAR2(500), " + 
            tablename + "_modifyemp VARCHAR2(50), " + 
            tablename + "_modifyTime  date" + 
            ")";
          String sql3 = "alter table " + tablename + 
            " add constraint " + tablename + 
            "_ID_PK primary key (" + tablename + "_ID)";
          stat.addBatch(sql2);
          stat.addBatch(sql3);
          stat.addBatch(sql1);
          stat.executeBatch();
        } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
          sql1 = 
            "insert into tTable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,DOMAIN_ID,CREATEDEMP,CREATEDORG) VALUES('" + 
            tablecode + "','" + tabledesname + "','" + 
            tablename + "'," + 
            tablemodel + ",'" + tablefilepath + "','" + 
            tableowner + 
            "'," + domainid + "," + empId + "," + orgId + ")";
          sql2 = "create table " + tablename + 
            " (" + 
            tablename + "_ID    bigint not null," + 
            tablename + "_OWNER bigint," + 
            tablename + "_DATE  VARCHAR(24)," + 
            tablename + "_org VARCHAR(100)," + 
            tablename + "_group VARCHAR(100)," + 
            tablename + "_relaByInde VARCHAR(500) ," + 
            tablename + "_chargBy VARCHAR(500) ," + 
            tablename + "_orderByMa VARCHAR(500) ," + 
            tablename + "_assignState VARCHAR(500), " + 
            tablename + "_modifyemp VARCHAR(50), " + 
            tablename + "_modifyTime  date" + 
            ")";
          String sql3 = "alter table " + tablename + 
            " add constraint " + tablename + 
            "_ID_PK primary key (" + tablename + "_ID)";
          stat.addBatch(sql2);
          stat.addBatch(sql3);
          stat.addBatch(sql1);
          stat.executeBatch();
        } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
          sql1 = 
            "insert into tTable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,DOMAIN_ID,CREATEDEMP,CREATEDORG) VALUES('" + 
            tablecode + "','" + tabledesname + "','" + 
            tablename + "'," + 
            tablemodel + ",'" + tablefilepath + "','" + 
            tableowner + 
            "'," + domainid + "," + empId + "," + orgId + ")";
          sql2 = "IF EXISTS(SELECT name FROM sysobjects WHERE name='" + 
            tablename + "' AND type='U')" + 
            " DROP TABLE " + tablename + ";" + 
            "CREATE TABLE [" + tablename + "] (" + 
            "[" + tablename + "_id] [int] NULL CONSTRAINT [DF__" + tablename + "__" + tablename + "_id__00DF2177] DEFAULT ('')," + 
            "[" + tablename + "_owner] [int] NULL CONSTRAINT [DF__" + tablename + "__" + tablename + "_own__01D345B0] DEFAULT ('0')," + 
            "[" + tablename + "_date] [varchar] (24) COLLATE Chinese_PRC_CI_AS NULL CONSTRAINT [DF__" + tablename + "__" + tablename + "_dat__02C769E9] DEFAULT (getdate())," + 
            "[" + tablename + "_org] [varchar] (100) ," + 
            "[" + tablename + "_group] [varchar] (100) ," + 
            "[" + tablename + "_relaByInde] [varchar] (500) ," + 
            "[" + tablename + "_chargBy] [varchar] (500) ," + 
            "[" + tablename + "_orderByMa] [varchar] (500) ," + 
            "[" + tablename + "_assignState] [varchar] (500) ," + 
            "[" + tablename + "_modifyemp] [varchar] (50) ," + 
            "[" + tablename + "_modifyTime] [datetime]  " + 
            ")  ON [PRIMARY]";
          stat.addBatch(sql2);
          stat.addBatch(sql1);
          stat.executeBatch();
        } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          sql1 = 
            "insert into tTable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,TABLE_DATE,DOMAIN_ID,CREATEDEMP,CREATEDORG) VALUES('" + 
            tablecode + "','" + tabledesname + "','" + 
            tablename + "'," + 
            tablemodel + ",'" + tablefilepath + "','" + 
            tableowner + 
            "',now()," + domainid + "," + empId + "," + orgId + ")";
          sql2 = "CREATE TABLE " + tablename + " (" + 
            tablename + "_id bigint NOT NULL PRIMARY KEY," + 
            tablename + "_owner bigint ," + 
            tablename + "_date varchar (24), " + 
            
            tablename + "_org VARCHAR(100)," + 
            tablename + "_group VARCHAR(100)," + 
            
            tablename + "_relaByInde varchar(500) ," + 
            tablename + "_chargBy varchar(500) ," + 
            tablename + "_orderByMa varchar(500) ," + 
            tablename + "_assignState varchar(500), " + 
            tablename + "_modifyemp varchar(50), " + 
            tablename + "_modifytime  datetime " + 
            ")";
          stat.addBatch(sql2);
          stat.addBatch(sql1);
          stat.executeBatch();
        } 
        table = dbopt.executeQueryToStr(
            "select table_id from ttable where table_code='" + tablecode + "' and domain_id=" + domainid);
        success = Integer.parseInt(table);
      } else {
        success = -1;
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.rollback();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      success = 0;
      throw e;
    } 
    return success;
  }
  
  public String getTableName(String domainId) {
    String name = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (dbopt.executeUpdate(
          "update tSeq set iValue=iValue+1 where domain_id=" + 
          domainId) < 1)
        dbopt.executeUpdate(
            "insert into tSeq values(1000,1000,1000,1000,1000," + 
            domainId + ")"); 
      name = dbopt.executeQueryToStr("select iValue from tSeq where domain_id=" + 
          domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return name;
  }
  
  public void batchDeleteTable(String[] tablename) {
    int success = 0;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      Statement stat = dbopt.getStatement();
      if (DbOpt.dbtype.equals("oracle")) {
        for (int i = 0; i < tablename.length; i++) {
          String tableid = dbopt.executeQueryToStr(
              "select table_id from ttable where table_name='" + 
              tablename[i] + "'");
          String[] sonids = dbopt.executeQueryToStrArr1(
              "select son_id from tablerelation where main_id=" + 
              tableid + " and relationtype='0'");
          if (sonids != null && sonids.length > 0) {
            for (int ii = 0; ii < sonids.length; ii++) {
              String idd = sonids[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + iddtablename + 
                  " drop Column " + tablename[i] + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where main_id= " + 
                  tableid + " and son_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids2 = dbopt.executeQueryToStrArr1(
              "select main_id from tablerelation where son_id=" + 
              tableid + " and relationtype='0'");
          if (sonids2 != null && sonids2.length > 0) {
            for (int ii = 0; ii < sonids2.length; ii++) {
              String idd = sonids2[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + tablename[i] + 
                  " drop Column " + iddtablename + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where son_id= " + 
                  tableid + " and main_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids3 = dbopt.executeQueryToStrArr1(
              "select linktable from tablerelation where (main_id=" + 
              tableid + " or son_id=" + tableid + 
              ") and relationtype='1'");
          if (sonids3 != null && sonids3.length > 0) {
            for (int ii = 0; ii < sonids3.length; ii++) {
              String linktablename = sonids3[ii];
              stat.addBatch(
                  "delete from tablerelation where linktable='" + 
                  linktablename + "'");
              stat.addBatch("DROP TABLE " + linktablename);
            } 
            stat.executeBatch();
          } 
          stat.addBatch("delete from tlimit where limit_table=" + 
              tableid + " or limit_prytable=" + tableid);
          stat.addBatch("delete from tfield where field_table=" + 
              tableid);
          stat.addBatch("delete from tTable where table_name='" + 
              tablename[i] + "'");
          stat.addBatch("DROP TABLE " + tablename[i] + 
              " CasCade Constraints");
          stat.executeBatch();
        } 
      } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
        for (int i = 0; i < tablename.length; i++) {
          String tableid = dbopt.executeQueryToStr(
              "select table_id from ttable where table_name='" + 
              tablename[i] + "'");
          String[] sonids = dbopt.executeQueryToStrArr1(
              "select son_id from tablerelation where main_id=" + 
              tableid + " and relationtype='0'");
          if (sonids != null && sonids.length > 0) {
            for (int ii = 0; ii < sonids.length; ii++) {
              String idd = sonids[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + iddtablename + 
                  " drop Column " + tablename[i] + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where main_id= " + 
                  tableid + " and son_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids2 = dbopt.executeQueryToStrArr1(
              "select main_id from tablerelation where son_id=" + 
              tableid + " and relationtype='0'");
          if (sonids2 != null && sonids2.length > 0) {
            for (int ii = 0; ii < sonids2.length; ii++) {
              String idd = sonids2[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + tablename[i] + 
                  " drop Column " + iddtablename + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where son_id= " + 
                  tableid + " and main_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids3 = dbopt.executeQueryToStrArr1(
              "select linktable from tablerelation where (main_id=" + 
              tableid + " or son_id=" + tableid + 
              ") and relationtype='1'");
          if (sonids3 != null && sonids3.length > 0) {
            for (int ii = 0; ii < sonids3.length; ii++) {
              String linktablename = sonids3[ii];
              stat.addBatch(
                  "delete from tablerelation where linktable='" + 
                  linktablename + "'");
              stat.addBatch("DROP TABLE " + linktablename);
            } 
            stat.executeBatch();
          } 
          stat.addBatch("delete from tlimit where limit_table=" + 
              tableid + " or limit_prytable=" + tableid);
          stat.addBatch("delete from tfield where field_table=" + 
              tableid);
          stat.addBatch("delete from tTable where table_name='" + 
              tablename[i] + "'");
          stat.addBatch(
              "if exists(select name from sysobjects where name='" + 
              tablename[i] + "') drop table " + tablename[i]);
          stat.executeBatch();
        } 
      } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        for (int i = 0; i < tablename.length; i++) {
          String tableid = dbopt.executeQueryToStr(
              "select table_id from ttable where table_name='" + 
              tablename[i] + "'");
          String[] sonids = dbopt.executeQueryToStrArr1(
              "select son_id from tablerelation where main_id=" + 
              tableid + " and relationtype='0'");
          if (sonids != null && sonids.length > 0) {
            for (int ii = 0; ii < sonids.length; ii++) {
              String idd = sonids[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + iddtablename + 
                  " drop Column " + tablename[i] + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where main_id= " + 
                  tableid + " and son_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids2 = dbopt.executeQueryToStrArr1(
              "select main_id from tablerelation where son_id=" + 
              tableid + " and relationtype='0'");
          if (sonids2 != null && sonids2.length > 0) {
            for (int ii = 0; ii < sonids2.length; ii++) {
              String idd = sonids2[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + tablename[i] + 
                  " drop Column " + iddtablename + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where son_id= " + 
                  tableid + " and main_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids3 = dbopt.executeQueryToStrArr1(
              "select linktable from tablerelation where (main_id=" + 
              tableid + " or son_id=" + tableid + 
              ") and relationtype='1'");
          if (sonids3 != null && sonids3.length > 0) {
            for (int ii = 0; ii < sonids3.length; ii++) {
              String linktablename = sonids3[ii];
              stat.addBatch(
                  "delete from tablerelation where linktable='" + 
                  linktablename + "'");
              stat.addBatch("DROP TABLE " + linktablename);
            } 
            stat.executeBatch();
          } 
          stat.addBatch("delete from tlimit where limit_table=" + 
              tableid + " or limit_prytable=" + tableid);
          stat.addBatch("delete from tfield where field_table=" + 
              tableid);
          stat.addBatch("delete from tTable where table_name='" + 
              tablename[i] + "'");
          stat.addBatch("DROP TABLE " + tablename[i]);
          stat.executeBatch();
        } 
      } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
        for (int i = 0; i < tablename.length; i++) {
          String tableid = dbopt.executeQueryToStr(
              "select table_id from ttable where table_name='" + 
              tablename[i] + "'");
          String[] sonids = dbopt.executeQueryToStrArr1(
              "select son_id from tablerelation where main_id=" + 
              tableid + " and relationtype='0'");
          if (sonids != null && sonids.length > 0) {
            for (int ii = 0; ii < sonids.length; ii++) {
              String idd = sonids[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + iddtablename + 
                  " drop Column " + tablename[i] + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where main_id= " + 
                  tableid + " and son_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids2 = dbopt.executeQueryToStrArr1(
              "select main_id from tablerelation where son_id=" + 
              tableid + " and relationtype='0'");
          if (sonids2 != null && sonids2.length > 0) {
            for (int ii = 0; ii < sonids2.length; ii++) {
              String idd = sonids2[ii];
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              stat.addBatch("alter table " + tablename[i] + 
                  " drop Column " + iddtablename + 
                  "_id");
              stat.addBatch(
                  "delete from tablerelation  where son_id= " + 
                  tableid + " and main_id= " + idd + 
                  " and relationtype='0'");
            } 
            stat.executeBatch();
          } 
          String[] sonids3 = dbopt.executeQueryToStrArr1(
              "select linktable from tablerelation where (main_id=" + 
              tableid + " or son_id=" + tableid + 
              ") and relationtype='1'");
          if (sonids3 != null && sonids3.length > 0) {
            for (int ii = 0; ii < sonids3.length; ii++) {
              String linktablename = sonids3[ii];
              stat.addBatch(
                  "delete from tablerelation where linktable='" + 
                  linktablename + "'");
              stat.addBatch("DROP TABLE " + linktablename);
            } 
            stat.executeBatch();
          } 
          stat.addBatch("delete from tlimit where limit_table=" + 
              tableid + " or limit_prytable=" + tableid);
          stat.addBatch("delete from tfield where field_table=" + 
              tableid);
          stat.addBatch("delete from tTable where table_name='" + 
              tablename[i] + "'");
          stat.addBatch("DROP TABLE " + tablename[i]);
          stat.executeBatch();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void delSignleTable(String tablename) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String tableid = dbopt.executeQueryToStr(
          "select table_id from ttable where table_name='" + 
          tablename + "'");
      Statement stat = dbopt.getStatement();
      if ("".equals(tableid))
        tableid = "-1"; 
      String[] sonids = dbopt.executeQueryToStrArr1(
          "select son_id from tablerelation where main_id=" + tableid + 
          " and relationtype='0'");
      if (sonids != null && sonids.length > 0) {
        for (int i = 0; i < sonids.length; i++) {
          String idd = sonids[i];
          String iddtablename = dbopt.executeQueryToStr(
              "select TABLE_NAME from ttable where TABLE_ID=" + 
              idd);
          stat.addBatch("alter table " + iddtablename + 
              " drop Column " + tablename + "_id");
          stat.addBatch("delete from tablerelation  where main_id= " + 
              tableid + " and son_id= " + idd + 
              " and relationtype='0'");
        } 
        stat.executeBatch();
      } 
      String[] sonids2 = dbopt.executeQueryToStrArr1(
          "select main_id from tablerelation where son_id=" + tableid + 
          " and relationtype='0'");
      if (sonids2 != null && sonids2.length > 0) {
        for (int i = 0; i < sonids2.length; i++) {
          String idd = sonids2[i];
          String iddtablename = dbopt.executeQueryToStr(
              "select TABLE_NAME from ttable where TABLE_ID=" + 
              idd);
          stat.addBatch("alter table " + tablename + " drop Column " + 
              iddtablename + "_id");
          stat.addBatch("delete from tablerelation  where son_id= " + 
              tableid + " and main_id= " + idd + 
              " and relationtype='0'");
        } 
        stat.executeBatch();
      } 
      String[] sonids3 = dbopt.executeQueryToStrArr1(
          "select linktable from tablerelation where (main_id=" + 
          tableid + " or son_id=" + tableid + 
          ") and relationtype='1'");
      if (sonids3 != null && sonids3.length > 0) {
        for (int i = 0; i < sonids3.length; i++) {
          String linktablename = sonids3[i];
          stat.addBatch("delete from tablerelation where linktable='" + 
              linktablename + "'");
          stat.addBatch("DROP TABLE " + linktablename);
        } 
        stat.executeBatch();
      } 
      stat.addBatch("delete from tlimit where limit_table=" + tableid + 
          " or limit_prytable=" + tableid);
      stat.addBatch("delete from tfield where field_table=" + tableid);
      if (DbOpt.dbtype.equals("oracle")) {
        stat.addBatch("delete from tTable where table_name='" + 
            tablename + "'");
        stat.addBatch("DROP TABLE " + tablename);
        stat.executeBatch();
      } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
        stat.addBatch(
            "if exists(select name from sysobjects where name='" + 
            tablename + "') drop table " + tablename);
        stat.addBatch("delete from tTable where table_name='" + 
            tablename + "'");
        stat.executeBatch();
      } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        stat.addBatch("delete from tTable where table_name='" + 
            tablename + "'");
        stat.addBatch("drop table " + tablename);
        stat.executeBatch();
      } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
        stat.addBatch("delete from tTable where table_name='" + 
            tablename + "'");
        stat.addBatch("drop table " + tablename);
        stat.executeBatch();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public int updateTable(String table_name, String table_code, String table_desname, String table_model, String table_filepath, String domainId) throws Exception {
    int success = 1;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String table = dbopt.executeQueryToStr(
          "select table_id from ttable where table_desname='" + 
          table_desname + "' and table_name<>'" + table_name + 
          "' and DOMAIN_ID=" + domainId);
      if (table == null || table.length() < 1) {
        dbopt.executeUpdate("update ttable set table_code='" + 
            table_code + "', table_desname='" + 
            table_desname + "',table_model=" + 
            table_model + 
            ",table_filepath='" + table_filepath + 
            "' where table_name='" + table_name + "'");
      } else {
        success = -1;
      } 
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public int updateSimpleTable(String tableId, String tableName, String domainId) throws Exception {
    int success = 1;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String table = dbopt.executeQueryToStr(
          "select table_id from ttable where table_desname='" + 
          tableName + "' and table_id<>" + tableId + 
          " and DOMAIN_ID=" + domainId);
      if (table == null || table.length() < 1) {
        dbopt.executeUpdate("update ttable set  table_desname='" + 
            tableName + "' where table_id=" + tableId);
      } else {
        success = -1;
      } 
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public String[][] getFieldInfo(String tableid, String domainid) throws Exception {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select a.field_id,a.field_desname,a.field_code,a.field_name,b.type_desname,a.field_len,a.field_null,a.field_default,a.field_des,a.field_only,a.field_index,a.field_updata,a.field_owner,a.field_date,a.field_hide,a.field_query,a.field_type from tfield a,ttype b where a.field_table=" + 
        Long.valueOf(tableid) + " and a.DOMAIN_ID=" + Long.valueOf(domainid) + 
        " and a.field_type=b.type_id";
      list = dbopt.executeQueryToStrArr2(sql, 17);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public void delSignleField(String tablename, String fieldname) {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String fieldid = dbopt.executeQueryToStr(
          "select field_id from tfield,ttable where field_table=table_id and field_name='" + 
          fieldname + "' and table_name='" + tablename + "'");
      Statement stat = dbopt.getStatement();
      stat.addBatch("delete from tlimit where limit_field=" + fieldid + 
          " or limit_pryfield=" + fieldid);
      stat.addBatch("delete from tfield where field_id=" + fieldid);
      stat.addBatch("Alter Table " + tablename + " Drop Column " + 
          fieldname);
      stat.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void batchDeleteField(String tablename, String[] fieldname) {
    int success = 0;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      Statement stat = dbopt.getStatement();
      for (int i = 0; i < fieldname.length; i++) {
        String fieldid = dbopt.executeQueryToStr(
            "select field_id from tfield,ttable where field_table=table_id and field_name='" + 
            fieldname[i] + "' and table_name='" + tablename + "'");
        stat.addBatch("delete from tlimit where limit_field=" + fieldid + 
            " or limit_pryfield=" + fieldid);
        stat.addBatch("Alter Table " + tablename + " Drop Column " + 
            fieldname[i]);
        stat.addBatch("delete from tfield where field_id=" + fieldid);
        stat.executeBatch();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public int updateField(String fieldname, String fieldnull, String fielddefault, String fielddes, String fieldindex, String fieldupdata, String fielddesname, String fieldcode, String fieldlen, String tableid, String fieldonly, String queryField, String totField) {
    int success = 1;
    DbOpt dbopt = null;
    try {
      if (!"1".equals(totField))
        totField = "0"; 
      dbopt = new DbOpt();
      String field = dbopt.executeQueryToStr(
          "select field_id from tfield where field_desname='" + 
          fielddesname + "' and field_name<>'" + fieldname + 
          "' and field_table=" + tableid);
      if (field == null || field.length() < 1) {
        String sql;
        dbopt.executeUpdate("update tfield set field_null=" + fieldnull + 
            ",field_default='" + fielddefault + 
            "',field_des='" + 
            fielddes + "',field_index=" + 
            
            totField + 
            
            ",field_updata=" + fieldupdata + 
            ",field_desname='" + 
            fielddesname + "',field_code='" + fieldcode + 
            "',field_len=" + fieldlen + ",field_only=" + 
            fieldonly + "  where field_name='" + 
            fieldname + "' and field_table=" + tableid);
        String tablename = dbopt.executeQueryToStr(
            "select table_name from ttable where table_id=" + 
            tableid);
        String fieldtype = dbopt.executeQueryToStr(
            "select field_type from tfield where field_name='" + 
            fieldname + "' and field_table=" + tableid);
        if (fieldtype.equals("1000002")) {
          sql = "";
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            sql = " alter table " + tablename + " modify " + 
              fieldname + " varchar2(" + fieldlen + ")";
          } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
            sql = " alter table " + tablename + " alter column " + 
              fieldname + " varchar(" + fieldlen + ")";
          } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
            sql = " alter table " + tablename + " change " + 
              fieldname + " " + fieldname + "  varchar(" + fieldlen + ")";
          } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
            sql = " alter table " + tablename + " alter column " + 
              fieldname + " set data type varchar(" + fieldlen + 
              ")";
          } 
          dbopt.executeUpdate(sql);
        } 
        if (fieldtype.equals("1000003") || "0".equals(queryField)) {
          sql = "update tfield set field_query=0 where field_name='" + 
            fieldname + "' and field_table=" + tableid;
        } else {
          sql = "update tfield set field_query=" + queryField + " where field_name='" + 
            fieldname + "' and field_table=" + tableid;
        } 
        dbopt.executeUpdate(sql);
        if ("1000000".equals(fieldtype) || "1000001".equals(fieldtype)) {
          String oriTotField = dbopt.executeQueryToStr("SELECT TABLE_TOTFIELD FROM TTABLE WHERE TABLE_ID=" + tableid);
          if ("1".equals(totField)) {
            if (oriTotField == null || "".equals(oriTotField) || "null".equals(oriTotField)) {
              sql = "update ttable set table_totfield='" + fieldname + "' where table_id=" + tableid;
            } else if (oriTotField.indexOf(fieldname) < 0) {
              if (oriTotField.endsWith(",")) {
                oriTotField = String.valueOf(oriTotField) + fieldname + ",";
              } else {
                oriTotField = String.valueOf(oriTotField) + "," + fieldname + ",";
              } 
              sql = "update ttable set table_totfield='" + oriTotField + "' where table_id=" + tableid;
            } 
            dbopt.executeUpdate(sql);
          } else if (oriTotField != null && !"".equals(oriTotField) && !"null".equals(oriTotField)) {
            String[] totArray = oriTotField.split(",");
            totField = "";
            for (int i = 0; i < totArray.length; i++) {
              if (!totArray[i].equals(fieldname))
                if ("".equals(totField)) {
                  totField = totArray[i];
                } else if (totField.endsWith(",")) {
                  totField = String.valueOf(totField) + fieldname + ",";
                } else {
                  totField = String.valueOf(totField) + "," + fieldname + ",";
                }  
            } 
            sql = "update ttable set table_totfield='" + totField + "' where table_id=" + tableid;
            dbopt.executeUpdate(sql);
          } 
        } 
      } else {
        success = -1;
      } 
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public int updateSimpleField(String fieldId, String tableId, String tableSysName, String fieldType, String fieldname, String fieldnull, String fielddefault, String fieldDiplayName, String fieldlen, String queryField, String totField, String fieldBoxSearch, String fieldChangeMethod, String fieldFetchSql, String fieldToForm, String fieldScript, String fieldFilterList, String fieldFilterSearch, String defaultShowTimeValue) {
    return updateSimpleField(fieldId, tableId, tableSysName, fieldType, 
        fieldname, fieldnull, fielddefault, fieldDiplayName, fieldlen, 
        queryField, totField, fieldBoxSearch, fieldChangeMethod, fieldFetchSql, 
        fieldToForm, fieldScript, fieldFilterList, fieldFilterSearch, "0", 
        "0", "0", "", "", "", "", "", "", "");
  }
  
  public int updateSimpleField(String fieldId, String tableId, String tableSysName, String fieldType, String fieldname, String fieldnull, String fielddefault, String fieldDiplayName, String fieldlen, String queryField, String totField, String fieldBoxSearch, String fieldChangeMethod, String fieldFetchSql, String fieldToForm, String fieldScript, String fieldFilterList, String fieldFilterSearch, String inputSearchType, String startRange, String handleRange, String startText, String handleText, String fieldInterfaceName, String fieldInterfaceMethodName, String fieldInterfaceMethodPara, String fieldInterfacetype, String defaultShowTimeValue) {
    int success = 1;
    DbOpt dbopt = null;
    try {
      if (!"1".equals(totField))
        totField = "0"; 
      dbopt = new DbOpt();
      String field = dbopt.executeQueryToStr(
          "select field_id from tfield where field_desname='" + 
          fieldDiplayName + "' and field_id<>" + fieldId + 
          " and field_table=" + tableId);
      if (field == null || field.length() < 1) {
        String sql;
        if ("".equals(fieldlen))
          fieldlen = "0"; 
        String pSql = "update tfield set field_null=?,field_default=?,field_desname=?,field_len=?,field_query=?,field_boxsearch=?,field_changemethod=?,field_fetchsql=?,field_script=?,field_toffield=?,field_filterlist=?,field_filtersearch=?,field_index=?,inputSearchType=?,startRange=?,handleRange=?,startText=?,handleText=?,field_interfacename=?,field_interfacemethod=?,field_interfacemethodpara=?,field_interfacetype=?,defaultShowTime=? where field_id=?";
        String[] pSqlPara = { 
            fieldnull, fielddefault, fieldDiplayName, fieldlen, queryField, fieldBoxSearch, fieldChangeMethod, 
            fieldFetchSql, fieldScript, fieldToForm, 
            fieldFilterList, fieldFilterSearch, totField, inputSearchType, 
            startRange, handleRange, startText, handleText, fieldInterfaceName, fieldInterfaceMethodName, 
            fieldInterfaceMethodPara, fieldInterfacetype, defaultShowTimeValue, fieldId };
        dbopt.executePSUpdate(pSql, pSqlPara);
        String tablename = dbopt.executeQueryToStr(
            "select table_name from ttable where table_id=" + tableId);
        if (fieldType.equals("1000002")) {
          sql = "";
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            sql = " alter table " + tableSysName + " modify " + 
              fieldname + " varchar2(" + fieldlen + ")";
          } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
            sql = " alter table " + tableSysName + " alter column " + 
              fieldname + " varchar(" + fieldlen + ")";
          } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
            sql = " alter table " + tableSysName + " change " + 
              fieldname + " " + fieldname + "  varchar(" + fieldlen + ")";
          } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
            sql = " alter table " + tableSysName + " alter column " + 
              fieldname + " set data type varchar(" + fieldlen + 
              ")";
          } 
          dbopt.executeUpdate(sql);
        } 
        if (fieldType.equals("1000003") || "0".equals(queryField)) {
          sql = "update tfield set field_query=0 where field_id=" + fieldId;
        } else {
          sql = "update tfield set field_query=" + queryField + " where field_id=" + fieldId;
        } 
        dbopt.executeUpdate(sql);
        if ("1000000".equals(fieldType) || "1000001".equals(fieldType)) {
          String oriTotField = dbopt.executeQueryToStr("SELECT TABLE_TOTFIELD FROM TTABLE WHERE TABLE_ID=" + tableId);
          if ("1".equals(totField)) {
            if (oriTotField == null || "".equals(oriTotField) || "null".equals(oriTotField)) {
              sql = "update ttable set table_totfield='" + fieldname + "' where table_id=" + tableId;
            } else if (oriTotField.indexOf(fieldname) < 0) {
              if (oriTotField.endsWith(",")) {
                oriTotField = String.valueOf(oriTotField) + fieldname + ",";
              } else {
                oriTotField = String.valueOf(oriTotField) + "," + fieldname + ",";
              } 
              sql = "update ttable set table_totfield='" + oriTotField + "' where table_id=" + tableId;
            } 
            dbopt.executeUpdate(sql);
          } else if (oriTotField != null && !"".equals(oriTotField) && !"null".equals(oriTotField)) {
            String[] totArray = oriTotField.split(",");
            totField = "";
            for (int i = 0; i < totArray.length; i++) {
              if (!totArray[i].equals(fieldname))
                if ("".equals(totField)) {
                  totField = totArray[i];
                } else if (totField.endsWith(",")) {
                  totField = String.valueOf(totField) + totArray[i] + ",";
                } else {
                  totField = String.valueOf(totField) + "," + totArray[i] + ",";
                }  
            } 
            sql = "update ttable set table_totfield='" + totField + "' where table_id=" + tableId;
            dbopt.executeUpdate(sql);
          } 
        } 
      } else {
        success = -1;
      } 
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public int addField(String fieldtypeid, String tableid, String tablename, String modelid, String fielddesname, String fieldcode, String fieldname, String fieldtype, String fieldlen, String fieldnull, String fielddefault, String fielddes, String fieldonly, String fieldindex, String fieldupdata, String fieldowner, String queryField, String totField, String domainid, String fieldShowTimeValue) {
    int success = 1;
    DbOpt dbopt = null;
    Statement stat = null;
    try {
      if (!"1".equals(totField))
        totField = "0"; 
      dbopt = new DbOpt();
      if (tablename == null || 
        tablename.toUpperCase().trim().equals("NULL"))
        tablename = dbopt.executeQueryToStr(
            "select table_name from ttable where table_id=" + 
            tableid); 
      String field = dbopt.executeQueryToStr(
          "select field_id from tfield where field_desname='" + fielddesname + "' and field_table=" + tableid);
      String oriTotField = dbopt.executeQueryToStr("SELECT TABLE_TOTFIELD FROM TTABLE WHERE TABLE_ID=" + tableid);
      if (field == null || field.length() < 1) {
        String sql2;
        stat = dbopt.getStatement();
        String sql1 = "alter table " + tablename + " add " + fieldname;
        if (fieldtype.equals("text") && 
          DbOpt.dbtype.indexOf("oracle") >= 0)
          fieldtype = "clob"; 
        if (fieldtype.equals("double") && 
          DbOpt.dbtype.indexOf("oracle") >= 0)
          fieldtype = "float"; 
        sql1 = String.valueOf(sql1) + " " + fieldtype;
        if (fieldtype != null && fieldtype.toLowerCase().indexOf("varchar") >= 0) {
          sql1 = String.valueOf(sql1) + "(" + fieldlen + ")";
        } else if ("".equals(fieldlen)) {
          fieldlen = "0";
        } 
        if (DbOpt.dbtype.indexOf("mysql") >= 0 || DbOpt.dbtype.indexOf("sqlserver") >= 0 || DbOpt.dbtype.indexOf("db2") >= 0)
          sql1 = sql1.replaceAll("varchar2", "varchar"); 
        if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          sql2 = 
            "insert into tField (field_code,field_desname,field_name,field_type,field_len,field_null,field_default,field_des,field_list,field_only,field_index,field_updata,field_owner,field_model,field_table,field_hide,field_date,field_query,DOMAIN_ID,totalValue,inputSearchType,startRange,handleRange,startText,handleText,defaultShowTime) VALUES('" + 

            
            fieldcode + "','" + fielddesname + 
            "','" + fieldname + "'," + fieldtypeid + "," + 
            fieldlen + "," + 
            fieldnull + ",'" + fielddefault.replace("'", "\\'") + "','" + 
            fielddes + "',1," + 
            fieldonly + "," + 
            
            totField + 
            "," + fieldupdata + ",'" + fieldowner + "'," + 
            modelid + "," + 
            tableid + ",0,now()," + queryField + "," + domainid + ",'1;no',1,'0','0','','','" + fieldShowTimeValue + "')";
        } else {
          sql2 = 
            "insert into tField (field_code,field_desname,field_name,field_type,field_len,field_null,field_default,field_des,field_only,field_index,field_updata,field_owner,field_model,field_table,field_hide,field_query,DOMAIN_ID,totalValue,inputSearchType,startRange,handleRange,startText,handleText,defaultShowTime) VALUES('" + 


            
            fieldcode + "','" + fielddesname + 
            "','" + fieldname + "'," + fieldtypeid + "," + 
            fieldlen + "," + 
            fieldnull + ",'" + fielddefault.replace("'", "\\'") + "','" + 
            fielddes + "'," + 
            fieldonly + "," + 
            
            totField + 
            "," + fieldupdata + ",'" + fieldowner + "'," + 
            modelid + "," + 
            tableid + ",0," + queryField + "," + domainid + ",'1;no',1,'0','0','','','" + fieldShowTimeValue + "')";
        } 
        stat.addBatch(sql1);
        stat.addBatch(sql2);
        if (fieldtypeid != null && fieldtypeid.equals("1000003")) {
          stat.addBatch(
              "update tfield set field_show=110 where field_name='" + 
              fieldname + "' and field_table=" + tableid);
        } else {
          stat.addBatch(
              "update tfield set field_show=101 where field_name='" + 
              fieldname + "' and field_table=" + tableid);
        } 
        if ("1".equals(totField) && ("1000000".equals(fieldtypeid) || "1000001".equals(fieldtypeid)))
          if (oriTotField == null || "".equals(oriTotField) || "null".equals(oriTotField)) {
            stat.addBatch("update ttable set table_totfield='" + fieldname + "' where table_id=" + tableid);
          } else {
            if (oriTotField.endsWith(",")) {
              oriTotField = String.valueOf(oriTotField) + fieldname + ",";
            } else {
              oriTotField = String.valueOf(oriTotField) + "," + fieldname + ",";
            } 
            stat.addBatch("update ttable set table_totfield='" + oriTotField + "' where table_id=" + tableid);
          }  
        stat.executeBatch();
        dbopt.executeUpdate("update tfield set field_list=0,field_query=0 where (field_show=401 or field_type=1000003) and (field_list=1 or field_query=1)");
      } else {
        success = -1;
      } 
      dbopt.close();
    } catch (Exception e) {
      success = 0;
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return success;
  }
  
  public String getFieldName(String domainId) {
    String name = "f";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (dbopt.executeUpdate(
          "update tSeq set iUser=iUser+1 where domain_id=" + domainId) < 
        1)
        dbopt.executeUpdate(
            "insert into tSeq values(1000,1000,1000,1000,1000," + 
            domainId + ")"); 
      name = String.valueOf(name) + 
        dbopt.executeQueryToStr("select iUser from tSeq where domain_id=" + 
          domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return name;
  }
  
  public String[][] getType() {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select type_id,type_desname,type_name from ttype", 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String getModelid(String tableid) throws Exception {
    String modelid = null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      modelid = dbopt.executeQueryToStr(
          "select table_model from ttable where table_id=" + tableid);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return modelid;
  }
  
  public void updateFieldHide(String[] fieldname, String tableId) {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (fieldname != null)
        for (int i = 0; i < fieldname.length; i++) {
          String sql = 
            "update tfield set field_hide=case when field_hide=0 then 1 when field_hide=1 then 0 else 0 end  where field_name='" + 
            fieldname[i] + 
            "' and field_table=" + tableId;
          dbopt.executeUpdate(sql);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public String[][] getAssociateInfo(String tableid, String domainid) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.limit_id,a.limit_name,'','',a.limit_prytable,c.table_desname,limit_pryfield,'',a.limit_owner,a.limit_date from tlimit a,ttable c where a.limit_type=1 and limit_table=" + 
          tableid + " and a.DOMAIN_ID=" + 
          domainid + 
          " and a.limit_prytable=c.table_id order by a.limit_id desc", 
          10);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public void batchDelAssociate(String[] associateid) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (associateid != null)
        for (int i = 0; i < associateid.length; i++) {
          String sql1 = 
            "update tfield set field_ref=0 where field_ref=" + 
            associateid[i];
          String sql2 = "delete from tlimit where limit_id=" + 
            associateid[i];
          dbopt.executeUpdate(sql2);
          dbopt.executeUpdate(sql1);
        }  
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void delSignleAssociate(String associateid) {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql1 = "update tfield set field_ref=0 where field_ref=" + 
        associateid;
      String sql2 = "delete from tlimit where limit_id=" + associateid;
      dbopt.executeUpdate(sql2);
      dbopt.executeUpdate(sql1);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public String[][] getMainFieldInfo(String tableid) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select field_id,field_desname,field_query from tfield where field_table=" + 
          tableid, 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getPryTableInfo(String domainid) {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select table_id,table_desname,table_name from ttable where DOMAIN_ID=" + 
          domainid + " order by table_model,table_id desc", 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getPryFieldInfo(String domainid) {
    return getPryFieldInfo(domainid, null);
  }
  
  public String[][] getPryFieldInfo(String domainid, String tableId) {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (tableId == null) {
        list = dbopt.executeQueryToStrArr2(
            "select field_table,field_id,field_desname,field_name from tfield where DOMAIN_ID=" + 
            domainid, 4);
      } else {
        list = dbopt.executeQueryToStrArr2(
            "select field_table,field_id,field_desname,field_name from tfield where DOMAIN_ID=" + domainid + 
            " and field_talbe=" + tableId, 4);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public int addAssociate(String name, String limittable, String limitfield, String limitprytable, String limitpryfield, String limitowner, String domainid) {
    int success = -1;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String state = dbopt.executeQueryToStr(
          "select count(1) from tlimit where (((limit_pryfield=" + 
          limitpryfield + " and limit_table=" + limittable + 
          ") or limit_name='" + name + "') or limit_prytable=" + limitprytable + ") and domain_id=" + domainid);
      String subTableName = dbopt.executeQueryToStr("select table_name from ttable where table_id=" + limitprytable);
      if ("0".equals(state)) {
        if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          dbopt.executeUpdate("insert into tLimit (limit_name,limit_table,limit_type,limit_field,limit_prytable,limit_pryfield,limit_des,limit_owner,limit_date,DOMAIN_ID) values('" + 
              name + "'," + limittable + ",1," + (
              (limitfield.trim().length() < 1) ? "null" : 
              limitfield) + "," + limitprytable + "," + (
              (limitpryfield.trim().length() < 1) ? "null" : 
              limitpryfield) + ",'主外键关联','" + limitowner + 
              "',now()," + domainid + ")");
          ResultSet rs = dbopt.executeQuery("SHOW COLUMNS FROM " + subTableName + " WHERE FIELD ='" + subTableName + "_FOREIGNKEY'");
          if (rs.next()) {
            rs.close();
          } else {
            rs.close();
            dbopt.executeUpdate(" ALTER TABLE " + subTableName + " ADD " + subTableName + "_FOREIGNKEY NUMERIC(20) NULL");
          } 
        } else if (DbOpt.dbtype.indexOf("oracle") >= 0) {
          dbopt.executeUpdate("insert into tLimit (limit_name,limit_table,limit_type,limit_field,limit_prytable,limit_pryfield,limit_des,limit_owner,DOMAIN_ID) values('" + 
              name + "'," + limittable + ",1," + (
              (limitfield.trim().length() < 1) ? "null" : 
              limitfield) + "," + limitprytable + "," + (
              (limitpryfield.trim().length() < 1) ? "null" : 
              limitpryfield) + ",'主外键关联','" + limitowner + 
              "'," + domainid + ")");
          ResultSet rs = dbopt.executeQuery("select count(*) from SYS.User_Tab_Columns where table_name='" + subTableName.toUpperCase() + "' and column_name='" + subTableName.toUpperCase() + "_FOREIGNKEY'");
          int columnNum = 0;
          if (rs.next())
            columnNum = rs.getInt(1); 
          rs.close();
          if (columnNum == 0)
            dbopt.executeUpdate(" ALTER TABLE " + subTableName + " ADD " + subTableName + "_FOREIGNKEY NUMBER(20) NULL"); 
        } else {
          dbopt.executeUpdate("insert into tLimit (limit_name,limit_table,limit_type,limit_field,limit_prytable,limit_pryfield,limit_des,limit_owner,DOMAIN_ID) values('" + 
              name + "'," + limittable + ",1," + (
              (limitfield.trim().length() < 1) ? "null" : 
              limitfield) + "," + limitprytable + "," + (
              (limitpryfield.trim().length() < 1) ? "null" : 
              limitpryfield) + ",'主外键关联','" + limitowner + 
              "'," + domainid + ")");
          dbopt.executeUpdate(" ALTER TABLE " + subTableName + " ADD " + subTableName + "_FOREIGNKEY NUMBER(20) NULL");
        } 
        dbopt.executeUpdate("update tfield set field_ref=" + 
            dbopt.executeQueryToStr("select max(limit_id) from tlimit") + 
            " where field_id=" + limitfield);
        success = 1;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public int updateAssociate(String limitid, String limitprytable, String limitpryfield, String name) {
    DbOpt dbopt = null;
    int success = 0;
    try {
      dbopt = new DbOpt();
      String tableid = dbopt.executeQueryToStr(
          "select limit_table from tlimit where limit_id=" + limitid);
      String state = dbopt.executeQueryToStr(
          "select count(1) from tlimit where (limit_prytable=" + 
          limitprytable + " and limit_table=" + tableid + 
          " and limit_id<>" + limitid + ") or (limit_name='" + name + 
          "' and limit_table=" + tableid + "  and limit_id<>" + 
          limitid + ")");
      if ("0".equals(state)) {
        dbopt.executeUpdate("update tlimit set limit_name='" + name + 
            "', limit_prytable=" + limitprytable + 
            ", limit_pryfield=" + (
            (limitpryfield.trim().length() < 1) ? "null" : 
            limitpryfield) + 
            " where limit_id=" + limitid);
        success = 1;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public void setShow(String fieldlist, String fieldwidth, String fieldshow, String fieldvalue, String fieldname, String fieldseq, String tableId) {
    DbOpt dbopt = null;
    try {
      if (fieldname != null) {
        if (fieldseq == null || fieldseq.trim().length() < 1)
          fieldseq = "0"; 
        dbopt = new DbOpt();
        if (fieldshow != null && fieldshow.trim().equals("111")) {
          String oldCodeShow = dbopt.executeQueryToStr("select field_value from tfield where field_name='" + fieldname + "' and field_table=" + tableId);
          if (oldCodeShow == null || !oldCodeShow.equals(fieldvalue))
            dbopt.executeUpdate("update tField set field_codevalue=" + 
                fieldvalue.split("=")[(fieldvalue.split(
                    "=")).length - 1] + " where field_name='" + 
                fieldname + "' and field_table=" + 
                tableId); 
        } 
        if (fieldlist == null || fieldlist.trim().length() < 1 || fieldlist.toUpperCase().equals("NULL")) {
          String sql = "update tField set field_width=?,field_show=?,field_value=?, field_sequence=? where field_name=? and field_table=?";
          String[] para = { fieldwidth, fieldshow, fieldvalue, fieldseq, fieldname, tableId };
          dbopt.executePSUpdate(sql, para);
        } else {
          String sql = "update tField set field_list=?,field_width=?,field_show=?,field_value=?, field_sequence=? where field_name=? and field_table=?";
          String[] para = { fieldlist, fieldwidth, fieldshow, fieldvalue, fieldseq, fieldname, tableId };
          dbopt.executePSUpdate(sql, para);
        } 
        dbopt.executeUpdate("update tfield set field_sequence=0 where field_hide<>0");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public String[][] getShow(String tableid, String domainid) {
    String[][] list = (String[][])null;
    if (domainid.length() < 1)
      domainid = "-1"; 
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.field_name,a.field_desname,a.field_list,a.field_width,a.field_show,a.field_value,b.show_name,b.show_flag,a.field_type,a.field_sequence from tfield a left join tshow b on a.field_show=b.show_id where a.DOMAIN_ID=" + 
          domainid + " and field_table=" + 
          tableid, 10);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getCountField(String tableid, String fieldname) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select b.table_desname,a.field_desname,a.field_name from tfield a,ttable b where a.field_table=b.table_id and field_name<>'" + 
          fieldname + "' and field_table=" + 
          tableid, 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getQueryField(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select distinct a.field_id,a.field_desname,a.field_name,a.field_show,a.field_value from tfield a,tlimit b where a.field_query = 1 and a.field_table=b.limit_prytable and b.limit_table=" + 


          
          tableId + 
          " union select distinct a.field_id,a.field_desname,a.field_name," + 
          "a.field_show,a.field_value from tfield a where a.field_query = 1 and a.field_table =" + 
          tableId, 5);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getQueryField(String tableId, String field) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select distinct a.field_id,a.field_desname,a.field_name,a.field_show,a.field_value from tfield a,tlimit b where a.field_table=b.limit_prytable and b.limit_table=" + 

          
          tableId + " and field_name in (" + field + "'-1')" + 
          " union select distinct a.field_id,a.field_desname,a.field_name," + 
          "a.field_show,a.field_value from tfield a where a.field_table =" + 
          tableId + " and field_name in (" + field + "'-1')", 5);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public List getAssociateTable(String tableId, String domainId) {
    List<List<String[]>> resultList = new ArrayList();
    DbOpt dbopt = null;
    List<String[]> oldlist = new ArrayList();
    List<String[]> alllist = new ArrayList();
    String[][] list = getPryTableInfo(domainId);
    String[] relationrecord1 = (String[])null;
    String[] relationrecord2 = (String[])null;
    String[] relationrecord3 = (String[])null;
    String[] filterrecord = (String[])null;
    String relationtableids = "";
    String relationtableids1 = "";
    String[][] getSingleTable1 = (String[][])null;
    String[][] getSingleTable2 = (String[][])null;
    try {
      dbopt = new DbOpt();
      relationrecord1 = dbopt.executeQueryToStrArr1(
          "select son_id from tableRelation where main_id=" + tableId + 
          " and relationType='0'");
      relationrecord2 = dbopt.executeQueryToStrArr1(
          "select son_id from tableRelation where main_id=" + tableId + 
          " and relationType='1'");
      relationrecord3 = dbopt.executeQueryToStrArr1(
          "select main_id from tableRelation where son_id=" + tableId + 
          " and relationType='1'");
      filterrecord = dbopt.executeQueryToStrArr1(
          "select main_id from tableRelation where son_id=" + tableId + 
          " and relationType='0'");
      if (relationrecord1 != null && relationrecord1.length > 0) {
        for (int i = 0; i < relationrecord1.length - 1; i++) {
          String str = relationrecord1[i];
          relationtableids = String.valueOf(relationtableids) + str + ",";
        } 
        relationtableids = String.valueOf(relationtableids) + 
          relationrecord1[relationrecord1.length - 1];
      } 
      if (relationrecord2 != null && relationrecord2.length > 0)
        for (int i = 0; i < relationrecord2.length; i++) {
          String str = relationrecord2[i];
          relationtableids1 = String.valueOf(relationtableids1) + str + ",";
        }  
      if (relationrecord3 != null && relationrecord3.length > 0)
        for (int i = 0; i < relationrecord3.length; i++) {
          String str = relationrecord3[i];
          relationtableids1 = String.valueOf(relationtableids1) + str + ",";
        }  
      if (relationtableids1 != null && !relationtableids1.equals(""))
        relationtableids1 = relationtableids1.substring(0, 
            relationtableids1.length() - 1); 
      if (relationtableids != null && !relationtableids.equals(""))
        getSingleTable1 = dbopt.executeQueryToStrArr2(
            "select TABLE_ID,TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL from TTABLE a where TABLE_ID in (" + 
            relationtableids + ")", 5); 
      if (relationtableids1 != null && !relationtableids1.equals(""))
        getSingleTable2 = dbopt.executeQueryToStrArr2(
            "select TABLE_ID,TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL from TTABLE a where TABLE_ID in (" + 
            relationtableids1 + ")", 5); 
      if (getSingleTable1 != null && getSingleTable1.length > 0)
        for (int ii = 0; ii < getSingleTable1.length; ii++) {
          String[] tableInfo = { getSingleTable1[ii][0], 
              getSingleTable1[ii][2] };
          oldlist.add(tableInfo);
        }  
      if (getSingleTable2 != null && getSingleTable2.length > 0)
        for (int ii = 0; ii < getSingleTable2.length; ii++) {
          String[] tableInfo = { getSingleTable2[ii][0], 
              String.valueOf(getSingleTable2[ii][2]) + "*" };
          oldlist.add(tableInfo);
        }  
      if (list != null && list.length > 0)
        for (int j = 0; j < list.length; j++) {
          String id = list[j][0];
          int type = 0;
          if (id.equals(tableId)) {
            type++;
          } else {
            if (oldlist != null && oldlist.size() != 0)
              for (int k = 0; k < oldlist.size(); k++) {
                String[] oneTable = oldlist.get(k);
                if (id.equals(oneTable[0]))
                  type++; 
              }  
            if (filterrecord != null && filterrecord.length > 0)
              for (int jj = 0; jj < filterrecord.length; jj++) {
                String idd = filterrecord[jj];
                if (id.equals(idd))
                  type++; 
              }  
          } 
          if (type == 0) {
            String[] ff = { list[j][0], list[j][1] };
            alllist.add(ff);
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
        resultList.add(alllist);
        resultList.add(oldlist);
      } catch (SQLException sQLException) {}
    } 
    return resultList;
  }
  
  public Boolean addQueryField(String fieldId, String queryMode, String tableId) {
    Boolean success = Boolean.TRUE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      dbopt.executeUpdate(
          "update tfield set field_query=0 where field_table=" + 
          tableId);
      String[] field = fieldId.split(",");
      for (int i = 0; i < field.length; i++) {
        if (field[i] != null && field[i].trim().length() > 0 && 
          !field[i].toUpperCase().equals("NULL"))
          dbopt.executeUpdate(
              "update tfield set field_query=1 where field_id =" + 
              field[i]); 
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public Boolean editQueryField(String fieldId, String queryMode) {
    Boolean success = Boolean.FALSE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (dbopt.executeUpdate(
          "update tfield set field_query=1 and field_querymode=" + 
          queryMode + " where field_id=" + fieldId) > 0)
        success = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public Boolean delQueryField(String fieldId) {
    Boolean success = Boolean.FALSE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (dbopt.executeUpdate(
          "update tfield set field_query=0 where field_id=" + fieldId) > 0)
        success = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public Boolean delQueryField(String[] fieldId) {
    Boolean success = Boolean.FALSE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < fieldId.length; i++)
        dbopt.executeUpdate(
            "update tfield set field_query=0 where field_id=" + 
            fieldId); 
      success = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public String getQueryFieldHTML(String fieldId) {
    String html = "";
    String temp = "";
    String[] tempArr = (String[])null;
    String[][] fieldTemp = (String[][])null;
    String type = "";
    Calendar now = Calendar.getInstance();
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      fieldTemp = dbopt.executeQueryToStrArr2(
          "select a.field_id,b.show_id,a.field_type,a.field_name,a.field_changemethod from TSHOW b,TFIELD a where FIELD_SHOW=SHOW_ID and FIELD_ID=" + 
          fieldId, 5);
      if (fieldTemp != null && fieldTemp.length > 0) {
        boolean isNum = false;
        if (fieldTemp[0][1] != null && 
          fieldTemp[0][1].trim().length() > 0) {
          int i;
          if (fieldTemp[0][2].equals("1000000") || 
            fieldTemp[0][2].equals("1000001")) {
            type = "<input type=hidden name=" + fieldTemp[0][3] + 
              "_type id=" + fieldTemp[0][3] + 
              "_type value=\"number\">";
            isNum = true;
          } else {
            type = "<input type=hidden name=" + fieldTemp[0][3] + 
              "_type id=" + fieldTemp[0][3] + 
              "_type value=\"varchar\">";
          } 
          switch (Integer.parseInt(fieldTemp[0][1])) {
            case 103:
              if (!isNum) {
                type = "<input type=hidden id=" + fieldTemp[0][3] + 
                  "_type name=" + fieldTemp[0][3] + 
                  "_type value=\"radiovarchar\">";
              } else {
                type = "<input type=hidden id=" + fieldTemp[0][3] + 
                  "_type name=" + fieldTemp[0][3] + 
                  "_type value=\"radionumber\">";
              } 
              html = "";
              temp = dbopt.executeQueryToStr("select field_value from tfield where field_id=" + fieldId);
              if (temp == null || temp.trim().length() < 1)
                break; 
              if (temp.startsWith("*")) {
                String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
                temp = (new BaseSetEJBBean()).getValue(parentId);
              } 
              if (temp.startsWith("@")) {
                String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + 
                      "_id," + table.substring(table.indexOf(".") + 1, table.length()) + " from " + table.substring(0, table.indexOf(".")));
                } catch (Exception exception) {}
                if (data != null)
                  for (int j = 0; j < data.length; j++)
                    html = String.valueOf(html) + "<input type=radio id=" + 
                      fieldTemp[0][3] + " name=" + 
                      fieldTemp[0][3] + " value=\"" + 
                      data[j][0] + "\">" + data[j][1] + "&nbsp;";  
                break;
              } 
              if (temp.startsWith("$")) {
                String sql = temp.substring(2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  data = dbopt.executeQueryToStrArr2(sql);
                } catch (Exception exception) {}
                if (data != null)
                  for (int j = 0; j < data.length; j++)
                    html = String.valueOf(html) + "<input type=radio id=" + 
                      fieldTemp[0][3] + " name=" + 
                      fieldTemp[0][3] + " value=\"" + 
                      data[j][0] + "\">" + data[j][1] + "&nbsp;";  
                break;
              } 
              tempArr = temp.split(";");
              for (i = 0; i < tempArr.length; i++) {
                if (tempArr[i] != null && 
                  tempArr[i].trim().length() > 0 && 
                  tempArr[i].indexOf("/") > 0 && 
                  tempArr[i].indexOf("/") < 
                  tempArr[i].length() - 1)
                  html = String.valueOf(html) + "<input type=radio id=" + 
                    fieldTemp[0][3] + " name=" + 
                    fieldTemp[0][3] + " value=\"" + 
                    tempArr[i].split("/")[0] + "\">" + 
                    tempArr[i].split("/")[1] + "&nbsp;"; 
              } 
              break;
            case 104:
              type = "<input type=hidden id=" + fieldTemp[0][3] + 
                "_type name=" + fieldTemp[0][3] + 
                "_type value=\"checkbox\">";
              html = "";
              temp = dbopt.executeQueryToStr(
                  "select field_value from tfield where field_id=" + 
                  fieldId);
              if (temp == null || temp.trim().length() < 1)
                break; 
              if (temp.startsWith("*")) {
                String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
                temp = (new BaseSetEJBBean()).getValue(parentId);
              } 
              tempArr = temp.split(";");
              for (i = 0; i < tempArr.length; i++) {
                if (tempArr[i] != null && 
                  tempArr[i].trim().length() > 0 && 
                  tempArr[i].indexOf("/") >= 0 && 
                  tempArr[i].indexOf("/") < 
                  tempArr[i].length() - 1)
                  html = String.valueOf(html) + "<input type=checkbox id=" + 
                    fieldTemp[0][3] + " name=" + 
                    fieldTemp[0][3] + " value=\"" + 
                    tempArr[i].split("/")[0] + "\">" + 
                    tempArr[i].split("/")[1] + "&nbsp;"; 
              } 
              break;
            case 105:
              if (isNum) {
                type = "<input type=hidden id=" + fieldTemp[0][3] + 
                  "_type name=" + fieldTemp[0][3] + 
                  "_type value=\"selectnumber\">";
              } else {
                type = "<input type=hidden id=" + fieldTemp[0][3] + 
                  "_type name=" + fieldTemp[0][3] + 
                  "_type value=\"selectvarchar\">";
              } 
              html = "<select id=" + fieldTemp[0][3] + " name=" + fieldTemp[0][3];
              if (Integer.parseInt(fieldTemp[0][4]) > 0)
                html = String.valueOf(html) + " onchange=\"" + fieldTemp[0][3] + "_onchange(this);\""; 
              html = String.valueOf(html) + "><option value=\"\">==请选择==</option>";
              temp = dbopt.executeQueryToStr("select field_value from tfield where field_id=" + fieldId);
              if (temp == null || temp.trim().length() < 1)
                break; 
              if (temp.startsWith("*")) {
                String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
                temp = (new BaseSetEJBBean()).getValue(parentId);
              } 
              if (temp.startsWith("@")) {
                String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
                String[][] data = (String[][])null;
                try {
                  String col = table.substring(table.indexOf(".") + 1, table.length());
                  data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + "_id," + col + " from " + table.substring(0, table.indexOf(".")) + " order by " + col);
                } catch (Exception exception) {}
                if (data != null)
                  for (int j = 0; j < data.length; j++)
                    html = String.valueOf(html) + "<option value=\"" + data[j][0] + "\">" + data[j][1] + "</option>";  
              } else if (temp.startsWith("$")) {
                String sql, dataSourceName = "system";
                if (temp.indexOf("].$[") > 0) {
                  dataSourceName = temp.substring(2, temp.indexOf("].$["));
                  sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
                } else {
                  sql = temp.substring(2, temp.length() - 1);
                } 
                String[][] data = (String[][])null;
                DbOpt dbo = null;
                try {
                  if (!"system".equals(dataSourceName)) {
                    dbo = new DbOpt(dataSourceName);
                    String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                    if (dbType.indexOf("oracle") >= 0) {
                      String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                      if (!"".equals(lang))
                        dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                    } 
                    data = dbo.executeQueryToStrArr2(sql);
                    dbo.close();
                  } else {
                    data = dbopt.executeQueryToStrArr2(sql);
                  } 
                } catch (Exception e3) {
                  if (dbo != null)
                    dbo.close(); 
                  e3.printStackTrace();
                } 
                if (data != null)
                  for (int j = 0; j < data.length; j++)
                    html = String.valueOf(html) + "<option value=\"" + data[j][0] + "\">" + data[j][1] + "</option>";  
              } else {
                tempArr = temp.split(";");
                for (i = 0; i < tempArr.length; i++) {
                  if (tempArr[i] != null && tempArr[i].trim().length() > 0 && tempArr[i].indexOf("/") >= 0 && tempArr[i].indexOf("/") < tempArr[i].length() - 1)
                    html = String.valueOf(html) + "<option value=\"" + tempArr[i].split("/")[0] + "\">" + tempArr[i].split("/")[1] + "</option>"; 
                } 
              } 
              html = String.valueOf(html) + "</select>";
              break;
            case 107:
              type = "<input type=hidden id=" + fieldTemp[0][3] + 
                "_type name=" + fieldTemp[0][3] + 
                "_type value=date>";
              type = String.valueOf(type) + "<input type=text class=inputText name=" + 
                fieldTemp[0][3] + "_start id=" + fieldTemp[0][3] + 
                "_start onclick=setDay(this) value=\"" + 
                String.valueOf(now.get(1)) + 
                "-" + (
                (now.get(2) >= 0) ? 
                String.valueOf(now.get(2) + 1) : (
                "0" + 
                String.valueOf(now.get(2) + 1))) + 
                "-" + (
                (now.get(5) > 9) ? 
                String.valueOf(now.get(5)) : (
                "0" + String.valueOf(now.get(5)))) + 
                "\" style=\"background:url('/jsoa/eform/images/down_arrow.gif');\">&nbsp;至&nbsp;";
              type = String.valueOf(type) + "<input type=text class=inputText name=" + 
                fieldTemp[0][3] + "_end id=" + fieldTemp[0][3] + 
                "_end onclick=setDay(this) value=\"" + 
                String.valueOf(now.get(1)) + 
                "-" + (
                (now.get(2) >= 0) ? 
                String.valueOf(now.get(2) + 1) : (
                "0" + 
                String.valueOf(now.get(2) + 1))) + 
                "-" + (
                (now.get(5) > 9) ? 
                String.valueOf(now.get(5)) : (
                "0" + String.valueOf(now.get(5)))) + 
                "\" style=\"background:url('/jsoa/eform/images/down_arrow.gif');\">";
              type = String.valueOf(type) + "<input type=checkbox name=" + fieldTemp[0][3] + 
                "date id=" + fieldTemp[0][3] + 
                "date value=\"1\">";
              break;
            case 108:
              type = "<input type=hidden id=" + fieldTemp[0][3] + 
                "_type name=" + fieldTemp[0][3] + 
                "_type value=\"time\">";
              type = String.valueOf(type) + "<INPUT type=text class=inputText name=" + 
                fieldTemp[0][3] + "_start id=" + fieldTemp[0][3] + 
                "_start onclick='showDateTimeBar(this, \"h:n:s\", \"" + 
                String.valueOf(now.get(2) + 1) + 
                "/" + 
                String.valueOf(now.get(5) + 1) + 
                "/" + 
                String.valueOf(now.get(1)) + " " + 
                String.valueOf(now.get(10)) + ":" + 
                String.valueOf(now.get(12)) + 
                ":" + 
                String.valueOf(now.get(12)) + 
                "\", " + 
                "test, \"hello world\")' name=" + 
                fieldTemp[0][3] + " value=\"" + 
                String.valueOf(now.get(10)) + ":" + 
                String.valueOf(now.get(12)) + 
                ":" + 
                String.valueOf(now.get(12)) + 
                "\" style=\"background:url('/jsoa/eform/images/down_arrow.gif');\">&nbsp;至&nbsp;";
              type = String.valueOf(type) + "<INPUT type=text class=inputText name=" + 
                fieldTemp[0][3] + "_end id=" + fieldTemp[0][3] + 
                "_end onclick='showDateTimeBar(this, \"h:n:s\", \"" + 
                String.valueOf(now.get(2) + 1) + 
                "/" + 
                String.valueOf(now.get(5) + 1) + 
                "/" + 
                String.valueOf(now.get(1)) + " " + 
                String.valueOf(now.get(10)) + ":" + 
                String.valueOf(now.get(12)) + 
                ":" + 
                String.valueOf(now.get(12)) + 
                "\", " + 
                "test, \"hello world\")' name=" + 
                fieldTemp[0][3] + " value=\"" + 
                String.valueOf(now.get(10)) + ":" + 
                String.valueOf(now.get(12)) + 
                ":" + 
                String.valueOf(now.get(12)) + 
                "\" style=\"background:url('/jsoa/eform/images/down_arrow.gif');\">";
              type = String.valueOf(type) + "<input type=checkbox name=" + fieldTemp[0][3] + 
                "date id=" + fieldTemp[0][3] + 
                "date value=\"1\">";
              break;
            case 109:
              type = "<input type=hidden id=" + fieldTemp[0][3] + 
                "_type name=" + fieldTemp[0][3] + 
                "_type value=\"datetime\">";
              type = String.valueOf(type) + "<input type=text class=inputText name=" + 
                fieldTemp[0][3] + "_start id=" + fieldTemp[0][3] + 
                "_start onclick=setDayHM(this) value=\"" + 
                String.valueOf(now.get(1)) + "-" + (
                (now.get(2) >= 0) ? 
                String.valueOf(now.get(2) + 1) : (
                "0" + 
                String.valueOf(now.get(2) + 1))) + 
                "-" + (
                (now.get(5) > 9) ? 
                String.valueOf(now.get(5)) : (
                "0" + String.valueOf(now.get(5)))) + 
                "  " + (
                (now.get(10) > 9) ? 
                String.valueOf(now.get(10)) : (
                "0" + String.valueOf(now.get(10)))) + 
                ":" + (
                (now.get(12) > 9) ? 
                String.valueOf(now.get(12)) : (
                "0" + String.valueOf(now.get(12)))) + 
                "\" style=\"background:url('/jsoa/eform/images/down_arrow.gif');\">&nbsp;至&nbsp;";
              type = String.valueOf(type) + "<input type=text class=inputText name=" + 
                fieldTemp[0][3] + "_end id=" + fieldTemp[0][3] + 
                "_end onclick=setDayHM(this) value=\"" + 
                String.valueOf(now.get(1)) + "-" + (
                (now.get(2) >= 0) ? 
                String.valueOf(now.get(2) + 1) : (
                "0" + 
                String.valueOf(now.get(2) + 1))) + 
                "-" + (
                (now.get(5) > 9) ? 
                String.valueOf(now.get(5)) : (
                "0" + String.valueOf(now.get(5)))) + 
                "  " + (
                (now.get(10) > 9) ? 
                String.valueOf(now.get(10)) : (
                "0" + String.valueOf(now.get(10)))) + 
                ":" + (
                (now.get(12) > 9) ? 
                String.valueOf(now.get(12)) : (
                "0" + String.valueOf(now.get(12)))) + 
                "\" style=\"background:url('/jsoa/eform/images/down_arrow.gif');\">";
              type = String.valueOf(type) + "<input type=checkbox name=" + fieldTemp[0][3] + 
                "date id=" + fieldTemp[0][3] + 
                "date value=\"1\">";
              break;
            case 403:
              type = "<input type=hidden id=" + fieldTemp[0][3] + 
                "_type name=" + fieldTemp[0][3] + 
                "_type value=\"checkbox\">";
              html = "<input type=checkbox name=" + fieldTemp[0][3] + 
                " id=" + fieldTemp[0][3] + " value=0>";
              break;
            case 301:
              type = "<input type=hidden name=" + fieldTemp[0][3] + 
                "_type id=" + fieldTemp[0][3] + 
                "_type value=\"float\">";
              html = "<input type=text id=" + fieldTemp[0][3] + 
                "_begin" + " onkeydown=checkNum(this) " + 
                " name=" + fieldTemp[0][3] + 
                "_begin size=8 class=\"inputText\"/>";
              html = String.valueOf(html) + " 至 <input type=text id=" + fieldTemp[0][3] + 
                "_end" + " onkeydown=checkNum(this) " + 
                " name=" + fieldTemp[0][3] + 
                "_end size=8 class=\"inputText\"/>";
              break;
            default:
              html = "<input type=\"text\" id=\"" + fieldTemp[0][3] + 
                "\"" + (
                isNum ? "onkeydown=\"checkNum(this)\"" : "") + 
                " name=\"" + fieldTemp[0][3] + 
                "\" class=\"inputText\"/>";
              break;
          } 
          if (fieldTemp[0][2].equals("1000001")) {
            type = "<input type=hidden name=" + fieldTemp[0][3] + 
              "_type id=" + fieldTemp[0][3] + 
              "_type value=\"float\">";
            html = "<input type=text id=" + fieldTemp[0][3] + 
              "_begin" + " onkeydown=checkNum(this) " + 
              " name=" + fieldTemp[0][3] + 
              "_begin size=8 class=\"inputText\"/>";
            html = String.valueOf(html) + " 至 <input type=text id=" + fieldTemp[0][3] + 
              "_end " + " onkeydown=checkNum(this) " + 
              " name=" + fieldTemp[0][3] + 
              "_end size=8 class=\"inputText\"/>";
          } else if (fieldTemp[0][2].equals("1000000")) {
            type = "<input type=hidden name=" + fieldTemp[0][3] + 
              "_type id=" + fieldTemp[0][3] + 
              "_type value=\"integer\">";
            html = "<input type=text id=" + fieldTemp[0][3] + 
              "_begin " + " onkeydown=checkNum(this) " + 
              " name=" + fieldTemp[0][3] + 
              "_begin size=8 class=\"inputText\"/>";
            html = String.valueOf(html) + " 至 <input type=text id=" + fieldTemp[0][3] + 
              "_end" + " onkeydown=checkNum(this) " + 
              " name=" + fieldTemp[0][3] + 
              "_end size=8 class=\"inputText\"/>";
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return 
      String.valueOf(type) + html;
  }
  
  public String[][] getListField(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select a.field_id,a.field_desname,a.field_name,a.field_width,a.field_show,a.field_value,field_type from tfield a where a.field_list=1 and a.field_table=? order by field_SEQUENCE asc";
      String[] para = { tableId };
      list = dbopt.executeQueryToStrArr2PS(sql, 7, para);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getListField(String tableId, String field) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select a.field_id,a.field_desname,a.field_name,a.field_width,a.field_show,a.field_value,field_type from tfield a where a.field_table=" + 
        Long.parseLong(tableId) + " and a.field_name in (" + field + "'-1') " + " order by field_SEQUENCE asc";
      list = dbopt.executeQueryToStrArr2(sql, 7);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getAllField(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (!"undefined".equals(tableId) && !"".equals(tableId))
        list = dbopt.executeQueryToStrArr2("select a.field_id,a.field_desname,a.field_name,a.field_width,a.field_show,a.field_value,field_type from tfield a where a.field_table=" + 
            tableId + 
            " order by field_SEQUENCE asc", 
            7); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getSubListField(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select c.field_id,c.field_desname,c.field_name,c.field_width,c.field_show,c.field_value,c.field_type from tlimit a,ttable b,tfield c where a.limit_prytable=b.table_id and b.table_id=c.field_table and a.limit_table=" + 
          tableId + 
          " order by c.field_name, field_SEQUENCE asc", 
          7);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getSubListField(String tableId, String field) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select c.field_id,c.field_desname,c.field_name,c.field_width,c.field_show,c.field_value,c.field_type from tlimit a,ttable b,tfield c where a.limit_prytable=b.table_id and b.table_id=c.field_table and a.limit_table=" + 
          tableId + " and field_name in (" + field + "'-1')" + 
          " order by field_SEQUENCE,c.field_name ", 
          7);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getSubTableId(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "SELECT b.table_Id,b.table_desname,b.table_name FROM tlimit a,ttable b WHERE  a.limit_prytable=b.table_id AND a.limit_table=" + tableId;
      list = dbopt.executeQueryToStrArr2(sql, 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getSingleTable(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(
          "select TABLE_ID,TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL from TTABLE a where TABLE_ID=" + 
          tableId, 5);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public Boolean batchUpdateShow(String[] fieldname, String tableId) {
    Boolean success = Boolean.TRUE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      ResultSet rs = null;
      if (fieldname != null && fieldname.length > 0) {
        for (int i = 0; i < fieldname.length; i++) {
          if (i == 0)
            dbopt.executeUpdate(
                "update tfield set field_list=0 where field_table=" + 
                tableId); 
          dbopt.executeUpdate(
              "update tfield set field_list=1 where field_name='" + 
              fieldname[i] + "' and field_table=" + tableId);
        } 
        dbopt.executeUpdate("update tfield set field_list=0,field_query=0 where (field_show=401 or field_type=1000003) and (field_list=1 or field_query=1)");
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public String getFiledCode(String domainId) {
    String name = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (dbopt.executeUpdate(
          "update tseq set filedcode=filedcode+1 where domain_id=" + 
          domainId) < 1)
        dbopt.executeUpdate(
            "insert into tSeq values(1000,1000,1000,1000,1000," + 
            domainId + ")"); 
      name = String.valueOf(name) + 
        dbopt.executeQueryToStr("select filedcode from tseq where domain_id=" + 
          domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return name;
  }
  
  public String getMarkCode(String domainId) {
    String name = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (dbopt.executeUpdate(
          "update tseq set tablecode=tablecode+1 where domain_id=" + 
          domainId) < 1)
        dbopt.executeUpdate(
            "insert into tSeq values(1000,1000,1000,1000,1000," + 
            domainId + ")"); 
      name = dbopt.executeQueryToStr("select tablecode from tseq where domain_id=" + 
          domainId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return name;
  }
  
  public String getSystemMark() {
    List<Map> list = new ArrayList();
    String name = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToList(
          "select * from ttable order by table_date ");
      if (list != null && list.size() != 0) {
        Map m = list.get(0);
        if (m != null)
          name = (String)m.get("table_name"); 
      } else {
        name = "";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return name;
  }
  
  public String checkSame(String fielddesname, String fieldname, String tableid) {
    String suss = "1";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String field = dbopt.executeQueryToStr(
          "select field_id from tfield where field_desname in (" + 
          fielddesname + ") and field_table=" + tableid);
      if (field != null && !field.equals(""))
        return "-1"; 
      String field2 = dbopt.executeQueryToStr(
          "select field_id from tfield where field_name in (" + 
          fieldname + ") and field_table=" + tableid);
    } catch (Exception e) {
      suss = "0";
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    try {
      dbopt.close();
    } catch (SQLException sQLException) {}
    return suss;
  }
  
  public Boolean addAssociateTable(String fieldId, String queryMode, String tableId, String domainId) {
    Boolean success = Boolean.TRUE;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String tablename = dbopt.executeQueryToStr(
          "select table_name from ttable where table_id=" + tableId);
      String[] ids = (String[])null;
      if (fieldId != null && !fieldId.equals(""))
        ids = fieldId.split(","); 
      List<String> delelist1 = new ArrayList();
      List<String> delelist2 = new ArrayList();
      String[] old1 = dbopt.executeQueryToStrArr1(
          "select son_id from tableRelation where main_id=" + tableId + 
          " and relationtype ='0'");
      String[] old2 = dbopt.executeQueryToStrArr1(
          "select main_id from tableRelation where son_id=" + tableId + 
          " and relationType='1'");
      String[] old3 = dbopt.executeQueryToStrArr1(
          "select son_id from tableRelation where main_id=" + tableId + 
          " and relationType='1'");
      if (old1 != null && old1.length > 0)
        for (int i = 0; i < old1.length; i++) {
          String sonid = old1[i];
          int type = 0;
          if (ids != null && ids.length != 0)
            for (int j = 0; j < ids.length; j++) {
              String idd = ids[j];
              if (idd.indexOf("*") != -1)
                idd = idd.substring(0, idd.length() - 1); 
              if (sonid.equals(idd))
                type++; 
            }  
          if (type == 0)
            delelist1.add(sonid); 
        }  
      if (old2 != null && old2.length > 0)
        for (int i = 0; i < old2.length; i++) {
          String sonid = old2[i];
          int type = 0;
          if (ids != null && ids.length != 0)
            for (int j = 0; j < ids.length; j++) {
              String idd = ids[j];
              if (idd.indexOf("*") != -1)
                idd = idd.substring(0, idd.length() - 1); 
              if (sonid.equals(idd))
                type++; 
            }  
          if (type == 0)
            delelist2.add(sonid); 
        }  
      if (old3 != null && old3.length > 0)
        for (int i = 0; i < old3.length; i++) {
          String sonid = old3[i];
          int type = 0;
          if (ids != null && ids.length != 0)
            for (int j = 0; j < ids.length; j++) {
              String idd = ids[j];
              if (idd.indexOf("*") != -1)
                idd = idd.substring(0, idd.length() - 1); 
              if (sonid.equals(idd))
                type++; 
            }  
          if (type == 0)
            delelist2.add(sonid); 
        }  
      if (delelist1 != null && delelist1.size() != 0) {
        Statement stat = dbopt.getStatement();
        for (int i = 0; i < delelist1.size(); i++) {
          String idd = delelist1.get(i);
          String iddtablename = dbopt.executeQueryToStr(
              "select TABLE_NAME from ttable where TABLE_ID=" + 
              idd);
          stat.addBatch("delete from tablerelation  where main_id= " + 
              tableId + " and son_id= " + idd + 
              " and relationtype='0'");
          stat.addBatch("alter table " + iddtablename + 
              " drop Column " + tablename + "_id");
        } 
        stat.executeBatch();
      } 
      if (delelist2 != null && delelist2.size() != 0) {
        Statement stat = dbopt.getStatement();
        for (int i = 0; i < delelist2.size(); i++) {
          String idd = delelist2.get(i);
          String linktablename = dbopt.executeQueryToStr(
              "select linktable from tablerelation where (( main_id= " + 
              tableId + " and son_id= " + idd + ") or (main_id= " + 
              idd + " and son_id=" + tableId + 
              " )) and relationtype='1'");
          if (linktablename != null && !linktablename.equals("")) {
            stat.addBatch(
                "delete from tablerelation where (( main_id= " + 
                tableId + " and son_id= " + idd + 
                ") or (main_id= " + idd + " and son_id=" + 
                tableId + " )) and relationtype='1'");
            stat.addBatch("DROP TABLE " + linktablename);
          } 
        } 
        stat.executeBatch();
      } 
      if (ids != null && ids.length != 0)
        for (int i = 0; i < ids.length; i++) {
          String idd = ids[i];
          if (idd.indexOf("*") != -1) {
            idd = idd.substring(0, idd.length() - 1);
            String linktablename = dbopt.executeQueryToStr(
                "select linktable from tablerelation where (( main_id= " + 
                tableId + " and son_id= " + idd + 
                ") or (main_id= " + idd + " and son_id=" + 
                tableId + " )) and relationtype='1'");
            if (linktablename == null || linktablename.equals("")) {
              if (dbopt.executeUpdate(
                  "update tSeq set linkcode=linkcode+1 where domain_id=" + 
                  domainId) < 1)
                dbopt.executeUpdate(
                    "insert into tSeq values(1000,1000,1000,1000,1000," + 
                    domainId + ")"); 
              String linktablename2 = "Tlink" + 
                dbopt
                .executeQueryToStr(
                  "select linkcode from tseq where domain_id=" + 
                  domainId);
              Statement stat = dbopt.getStatement();
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              String mainId = dbopt.executeQueryToStr(
                  "select main_id from tablerelation where main_id= " + 
                  tableId + " and son_id= " + idd + 
                  " and relationtype='0'");
              if (mainId != null && !mainId.equals("")) {
                stat.addBatch("alter table " + iddtablename + 
                    " drop Column " + tablename + 
                    "_id");
                stat.addBatch(
                    "delete from tablerelation  where main_id= " + 
                    tableId + " and son_id= " + idd + 
                    " and relationtype='0'");
              } 
              stat.addBatch(
                  "insert into tablerelation (main_id,son_id,relationtype,linktable) values(" + 
                  tableId + "," + idd + ",'1','" + 
                  linktablename2 + "')");
              String sql2 = "";
              if (DbOpt.dbtype.indexOf("oracle") >= 0) {
                sql2 = "create table " + linktablename2 + 
                  " (" + 
                  linktablename2 + 
                  "_ID    NUMBER default '' not null," + 
                  tablename + "_ID NUMBER default '0'," + 
                  iddtablename + 
                  "_ID  NUMBER default '0'" + 
                  ")";
                String sql3 = "alter table " + linktablename2 + 
                  " add constraint " + 
                  linktablename2 + "_" + 
                  linktablename2 + 
                  "_ID_PK primary key (" + 
                  linktablename2 + "_ID)" + 
                  " using index";
                stat.addBatch(sql2);
                stat.addBatch(sql3);
                stat.executeBatch();
              } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
                sql2 = 
                  "IF EXISTS(SELECT name FROM sysobjects WHERE name='" + 
                  linktablename2 + "' AND type='U')" + 
                  " DROP TABLE " + linktablename2 + ";" + 
                  "CREATE TABLE [" + linktablename2 + 
                  "] (" + 
                  "[" + linktablename2 + 
                  "_id] [int] NULL CONSTRAINT [DF__" + 
                  linktablename2 + "__" + linktablename2 + 
                  "_id__00DF2177] DEFAULT ('')," + 
                  "[" + tablename + 
                  "_id] [int] NULL CONSTRAINT [DF__" + 
                  tablename + "__" + tablename + 
                  "_own1__01D345B0] DEFAULT ('0')," + 
                  
                  "[" + iddtablename + 
                  "_id] [int] NULL CONSTRAINT [DF__" + 
                  iddtablename + "__" + iddtablename + 
                  "_own2__01D345B0] DEFAULT ('0')" + 
                  ")  ON [PRIMARY]";
                stat.addBatch(sql2);
                stat.executeBatch();
              } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
                sql2 = "CREATE TABLE " + linktablename2 + " (" + 
                  linktablename2 + "_id int ," + 
                  tablename + "_id int ," + 
                  iddtablename + "_id int " + 
                  
                  ")";
                stat.addBatch(sql2);
                stat.executeBatch();
              } 
            } 
          } else {
            String mainId = dbopt.executeQueryToStr(
                "select main_id from tablerelation where main_id= " + 
                tableId + " and son_id= " + idd + 
                " and relationtype='0'");
            if (mainId == null || mainId.equals("")) {
              Statement stat = dbopt.getStatement();
              String linktablename = dbopt.executeQueryToStr(
                  "select linktable from tablerelation where (( main_id= " + 
                  tableId + " and son_id= " + idd + 
                  ") or (main_id= " + idd + " and son_id=" + 
                  tableId + " )) and relationtype='1'");
              String iddtablename = dbopt.executeQueryToStr(
                  "select TABLE_NAME from ttable where TABLE_ID=" + 
                  idd);
              if (linktablename != null && 
                !linktablename.equals("")) {
                stat.addBatch(
                    "delete from tablerelation where (( main_id= " + 
                    tableId + " and son_id= " + idd + 
                    ") or (main_id= " + idd + 
                    " and son_id=" + tableId + 
                    " )) and relationtype='1'");
                stat.addBatch("DROP TABLE " + linktablename);
              } 
              stat.addBatch(
                  "insert into tablerelation (main_id,son_id,relationtype) values( " + 
                  tableId + "," + idd + " , '0') ");
              if (DbOpt.dbtype.indexOf("oracle") >= 0)
                stat.addBatch("Alter Table " + iddtablename + 
                    " add " + tablename + "_id" + 
                    " number"); 
              if (DbOpt.dbtype.indexOf("sqlserver") >= 0)
                stat.addBatch("Alter Table " + iddtablename + 
                    " add " + tablename + "_id" + 
                    " numeric"); 
              if (DbOpt.dbtype.indexOf("mysql") >= 0)
                stat.addBatch("Alter Table " + iddtablename + 
                    " add " + tablename + "_id" + 
                    " int"); 
              stat.executeBatch();
            } 
          } 
        }  
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public String[][] getTotField(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.field_id,a.field_desname,a.field_name,a.field_show,a.field_value from tfield a where a.field_type in (1000000, 1000001) and a.field_table=" + 
          tableId + 
          " order by a.field_code asc", 5);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public List getTotFieldInfo(String pageId) throws Exception {
    List<Object[]> list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select area_table,area_name from tarea where page_id=" + pageId);
      List<String[]> tableList = new ArrayList();
      while (rs.next()) {
        String[] temp = new String[2];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        tableList.add(temp);
      } 
      rs.close();
      for (int m = 0; m < tableList.size(); m++) {
        String[] temp = tableList.get(m);
        String tableName = temp[0];
        rs = stmt.executeQuery("select table_totfield,table_id from ttable where table_name='" + tableName + "'");
        if (rs.next()) {
          String totField = rs.getString(1);
          String tableId = rs.getString(2);
          rs.close();
          if (totField != null && !"".equals(totField) && !"null".equals(totField)) {
            if (totField.endsWith(","))
              totField = totField.substring(0, totField.length() - 1); 
            totField = totField.replaceAll(",", "','");
            totField = "'" + totField + "'";
            rs = stmt.executeQuery("select field_desname,field_name from tfield where  field_name in (" + 
                totField + ") and field_table=" + tableId);
            while (rs.next()) {
              Object[] obj = new Object[3];
              obj[0] = rs.getString(1);
              obj[1] = rs.getString(2);
              obj[2] = temp[1];
              list.add(obj);
            } 
            rs.close();
          } 
        } else {
          rs.close();
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      ex.printStackTrace();
    } 
    return list;
  }
  
  public Integer saveTotField(String tableId, String fieldName) {
    Integer result = Integer.valueOf("0");
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      dbopt.executeUpdate("UPDATE TTABLE SET TABLE_TOTFIELD='" + 
          fieldName + "' WHERE TABLE_ID=" + tableId);
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return result;
  }
  
  public String getTableTotField(String tableId) {
    String list = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStr(
          "SELECT TABLE_TOTFIELD FROM TTABLE WHERE TABLE_ID=" + 
          tableId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String getTableTotFieldValue(String tableId, String condition) {
    String list = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      ResultSet rs = dbopt.executeQuery(
          "SELECT TABLE_NAME, TABLE_TOTFIELD FROM TTABLE WHERE TABLE_ID=" + 
          tableId + 
          " AND (TABLE_TOTFIELD IS NOT NULL OR TABLE_TOTFIELD<>'')");
      String tableName = "", totField = "";
      if (rs.next()) {
        tableName = rs.getString(1);
        totField = rs.getString(2);
      } 
      rs.close();
      if (!tableName.equals("") && !totField.equals("")) {
        if (totField.endsWith(","))
          totField = totField.substring(0, totField.length() - 1); 
        String[] tmp = { totField };
        if (totField.indexOf(",") >= 0)
          tmp = totField.split(","); 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tmp.length; i++) {
          sb.append(dbopt.executeQueryToStr(
                "SELECT FIELD_DESNAME FROM TFIELD WHERE FIELD_NAME='" + 
                tmp[i] + "' and field_table=" + tableId));
          sb.append("合计：");
          sb.append(dbopt.executeQueryToStr("SELECT SUM(" + tmp[i] + 
                ") FROM " + tableName + " WHERE " + condition));
          sb.append("&nbsp;&nbsp;");
        } 
        list = sb.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    if ("".equals(list))
      list = "0"; 
    return list;
  }
  
  public String pasteTable(String tables, String domainId, String userId, String orgId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      String[] tableArr = tables.split(",");
      for (int i = 0; i < tableArr.length; i++) {
        if (!"".equals(tableArr[i])) {
          String tableCode = getMarkCode(domainId);
          String tableName = "jst_" + getTableName(domainId);
          ResultSet rs = stmt.executeQuery("select table_id from ttable where table_name='" + tableArr[i] + "'");
          rs.next();
          String oldTableId = rs.getString(1);
          rs.close();
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            String sql = 
              "insert into ttable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,DOMAIN_ID,createdemp,createdorg) select '" + 
              tableCode + "','复件'||t.table_desname,'" + 
              tableName + 
              "',t.table_model,t.table_filepath,t.table_owner,t.domain_id," + userId + "," + orgId + " from ttable t where t.table_name='" + 
              tableArr[i] + "'";
            stmt.executeUpdate(sql);
            rs = stmt.executeQuery("select max(table_id) from ttable");
            rs.next();
            String tableId = rs.getString(1);
            rs.close();
            stmt.executeUpdate("create table " + tableName + " as select * from " + tableArr[i]);
            stmt.executeUpdate("delete from " + tableName);
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ID");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_OWNER");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_DATE");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ORG");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_GROUP");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_RELABYINDE");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_CHARGBY");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ORDERBYMA");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ASSIGNSTATE");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_MODIFYEMP");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_MODIFYTIME");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ID NUMBER(20) NOT NULL");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_DATE VARCHAR2(24) default to_char(sysdate,'YYYY-mm-dd')");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_OWNER NUMBER(20)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ORG VARCHAR2(100)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_GROUP VARCHAR2(100)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_RELABYINDE VARCHAR2(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_CHARGBY VARCHAR2(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ORDERBYMA VARCHAR2(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ASSIGNSTATE VARCHAR2(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_MODIFYEMP VARCHAR2(50)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_MODIFYTIME DATE");
            stmt.addBatch("alter table " + tableName + " add constraint " + tableName + "_ID_PK primary key (" + tableName + "_ID)" + " using index");
            sql = "insert into tfield (FIELD_ID,FIELD_CODE,FIELD_DESNAME,FIELD_NAME,FIELD_MODEL,FIELD_TABLE,FIELD_TYPE,FIELD_LEN,FIELD_REF,FIELD_INDEX,FIELD_NULL,FIELD_SORT,FIELD_ONLY,FIELD_DEFAULT,FIELD_UPDATA,FIELD_DES,FIELD_LIST ,FIELD_WIDTH ,FIELD_SHOW,FIELD_VALUE,FIELD_LIMIT,FIELD_OWNER,FIELD_DATE,DOMAIN_ID,FIELD_HIDE,FIELD_SEQUENCE,FIELD_QUERY,FIELD_CODEVALUE,totalValue)";
            sql = String.valueOf(sql) + " select 1,t.FIELD_CODE,t.FIELD_DESNAME,t.FIELD_NAME,t.FIELD_MODEL," + tableId + ",t.FIELD_TYPE,t.FIELD_LEN,t.FIELD_REF,t.FIELD_INDEX,t.FIELD_NULL,t.field_sort,t.field_only,t.field_default,t.field_updata,t.field_des,t.field_list,t.field_width,t.field_show,t.field_value,t.field_limit,t.field_owner,t.field_date," + domainId + ",t.field_hide,t.field_sequence,t.field_query,t.field_codevalue,t.totalValue from tfield t where t.field_table=" + oldTableId;
            stmt.addBatch(sql);
          } else if (DbOpt.dbtype.indexOf("mssqlserver") >= 0) {
            String sql = 
              "insert into ttable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,DOMAIN_ID,createdemp,createdorg) select '" + 
              tableCode + "','复件'+t.table_desname,'" + 
              tableName + 
              "',t.table_model,t.table_filepath,t.table_owner,t.domain_id," + userId + "," + orgId + " from ttable t where t.table_name='" + 
              tableArr[i] + "'";
            stmt.executeUpdate(sql);
            rs = stmt.executeQuery("select max(table_id) from ttable");
            rs.next();
            String tableId = rs.getString(1);
            rs.close();
            stmt.executeUpdate("SELECT * into  " + tableName + " from " + tableArr[i] + " where 0=1");
            stmt.executeUpdate("delete from " + tableName);
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ID");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_OWNER");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_DATE");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ORG");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_GROUP");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_RELABYINDE");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_CHARGBY");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ORDERBYMA");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_ASSIGNSTATE");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_MODIFYEMP");
            stmt.addBatch("alter table " + tableName + " drop column " + tableArr[i] + "_MODIFYTIME");
            stmt.addBatch("alter table " + tableName + " add  " + tableName + "_ID INT NOT NULL");
            stmt.addBatch("alter table " + tableName + " add  " + tableName + "_DATE varchar(24) default getdate()");
            stmt.addBatch("alter table " + tableName + " add  " + tableName + "_OWNER INT");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ORG VARCHAR(100)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_GROUP VARCHAR(100)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_RELABYINDE VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_CHARGBY VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ORDERBYMA VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ASSIGNSTATE VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_MODIFYEMP VARCHAR(50)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_MODIFYTIME DATE");
            stmt.addBatch("alter table " + tableName + " add constraint " + tableName + "_ID_PK primary key (" + tableName + "_ID)");
            sql = "insert into tfield (FIELD_CODE,FIELD_DESNAME,FIELD_NAME,FIELD_MODEL,FIELD_TABLE,FIELD_TYPE,FIELD_LEN,FIELD_REF,FIELD_INDEX,FIELD_NULL,FIELD_SORT,FIELD_ONLY,FIELD_DEFAULT,FIELD_UPDATA,FIELD_DES,FIELD_LIST ,FIELD_WIDTH ,FIELD_SHOW,FIELD_VALUE,FIELD_LIMIT,FIELD_OWNER,FIELD_DATE,DOMAIN_ID,FIELD_HIDE,FIELD_SEQUENCE,FIELD_QUERY,FIELD_CODEVALUE,totalValue)";
            sql = String.valueOf(sql) + " select t.FIELD_CODE,t.FIELD_DESNAME,t.FIELD_NAME,t.FIELD_MODEL," + tableId + ",t.FIELD_TYPE,t.FIELD_LEN,t.FIELD_REF,t.FIELD_INDEX,t.FIELD_NULL,t.field_sort,t.field_only,t.field_default,t.field_updata,t.field_des,t.field_list,t.field_width,t.field_show,t.field_value,t.field_limit,t.field_owner,t.field_date," + domainId + ",t.field_hide,t.field_sequence,t.field_query,t.field_codevalue,t.totalValue from tfield t where t.field_table=" + oldTableId;
            stmt.addBatch(sql);
          } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
            String sql = 
              "insert into ttable (TABLE_CODE,TABLE_DESNAME,TABLE_NAME,TABLE_MODEL,TABLE_FILEPATH,TABLE_OWNER,table_date,DOMAIN_ID,createdemp,createdorg) select '" + 
              tableCode + "',concat('复件',t.table_desname),'" + 
              tableName + 
              "',t.table_model,t.table_filepath,t.table_owner,now(),t.domain_id," + userId + "," + orgId + " from ttable t where t.table_name='" + 
              tableArr[i] + "'";
            stmt.executeUpdate(sql);
            rs = stmt.executeQuery("select max(table_id) from ttable");
            rs.next();
            String tableId = rs.getString(1);
            rs.close();
            stmt.addBatch("create table " + tableName + " select * from  " + tableArr[i]);
            stmt.addBatch("delete from " + tableName);
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_ID");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_OWNER");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_DATE");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_ORG");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_GROUP");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_RELABYINDE");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_CHARGBY");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_ORDERBYMA");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_ASSIGNSTATE");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_MODIFYEMP");
            stmt.addBatch("alter table " + tableName + " drop " + tableArr[i] + "_MODIFYTIME");
            stmt.addBatch("alter table " + tableName + " add  " + tableName + "_ID bigint not null PRIMARY KEY");
            stmt.addBatch("alter table " + tableName + " add  " + tableName + "_OWNER bigint");
            stmt.addBatch("alter table " + tableName + " add  " + tableName + "_DATE varchar(24)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ORG VARCHAR(100)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_GROUP VARCHAR(100)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_RELABYINDE VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_CHARGBY VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ORDERBYMA VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_ASSIGNSTATE VARCHAR(500)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_MODIFYEMP VARCHAR(50)");
            stmt.addBatch("alter table " + tableName + " add " + tableName + "_MODIFYTIME DATE");
            sql = "insert into tfield (FIELD_CODE,FIELD_DESNAME,FIELD_NAME,FIELD_MODEL,FIELD_TABLE,FIELD_TYPE,FIELD_LEN,FIELD_REF,FIELD_INDEX,FIELD_NULL,FIELD_SORT,FIELD_ONLY,FIELD_DEFAULT,FIELD_UPDATA,FIELD_DES,FIELD_LIST ,FIELD_WIDTH ,FIELD_SHOW,FIELD_VALUE,FIELD_LIMIT,FIELD_OWNER,FIELD_DATE,DOMAIN_ID,FIELD_HIDE,FIELD_SEQUENCE,FIELD_QUERY,FIELD_CODEVALUE,totalValue)";
            sql = String.valueOf(sql) + " select t.FIELD_CODE,t.FIELD_DESNAME,t.FIELD_NAME,t.FIELD_MODEL," + tableId + ",t.FIELD_TYPE,t.FIELD_LEN,t.FIELD_REF,t.FIELD_INDEX,t.FIELD_NULL,t.field_sort,t.field_only,t.field_default,t.field_updata,t.field_des,t.field_list,t.field_width,t.field_show,t.field_value,t.field_limit,t.field_owner,now()," + domainId + ",t.field_hide,t.field_sequence,t.field_query,t.field_codevalue,t.totalValue from tfield t where t.field_table=" + oldTableId;
            stmt.addBatch(sql);
          } else {
            DbOpt.dbtype.indexOf("db2");
          } 
          stmt.executeBatch();
          stmt.clearBatch();
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      ex.printStackTrace();
    } 
    return null;
  }
  
  public String getModuelName(String[] id) throws Exception {
    StringBuffer buffer = new StringBuffer("0");
    for (int i = 0; i < id.length; i++)
      buffer.append(",").append(id[i]); 
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select model_name from tmodel where model_id in(" + buffer.toString() + ")");
      buffer = new StringBuffer();
      while (rs.next())
        buffer.append(rs.getString(1)).append(","); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      throw ex;
    } 
    return buffer.toString();
  }
  
  public String getTableName(String[] id) throws Exception {
    StringBuffer buffer = new StringBuffer("'0'");
    for (int i = 0; i < id.length; i++)
      buffer.append(",'").append(id[i]).append("'"); 
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select table_desname from ttable where  table_name in(" + buffer.toString() + ")");
      buffer = new StringBuffer();
      while (rs.next())
        buffer.append(rs.getString(1)).append(","); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      throw ex;
    } 
    return buffer.toString();
  }
  
  public String getTableDesName(String tableId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select table_desname from ttable where  table_id='" + tableId + "'");
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      throw ex;
    } 
    return tableId;
  }
  
  public String[][] getAllFieldInfo(String tableid, String domainId) throws Exception {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select field_id,field_code,field_desname,field_name,field_type,field_len,field_show,field_value,field_default,field_null,field_only,field_query,field_list,field_sequence,field_width,field_boxsearch,field_changemethod,field_fetchsql,field_script,field_toffield,field_filterlist,field_filtersearch,totalValue,inputSearchType,startRange,handleRange,startText,handleText,field_InterfaceName,field_interfacemethod,field_InterfaceMethodPara,field_interfacetype,defaultShowTime from tfield where field_table=" + 

        
        tableid + " and " + 
        "DOMAIN_ID=" + domainId + " order by field_code";
      list = dbopt.executeQueryToStrArr2(sql, 33);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return list;
  }
  
  public String[][] getSimpleTableInfo(String tableid, String domainId) throws Exception {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select table_id,table_code,table_desname,table_name,table_model,table_totfield,table_setName from ttable where table_id=" + 
        tableid + " and DOMAIN_ID=" + domainId;
      list = dbopt.executeQueryToStrArr2(sql, 7);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String getShouldDelId(String tableid, String[] fieldId) throws Exception {
    StringBuffer buffer = new StringBuffer("-1");
    StringBuffer result = new StringBuffer();
    if (fieldId != null)
      for (int i = 0; i < fieldId.length; i++)
        buffer.append(",").append(fieldId[i]);  
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select field_name from tfield where field_table=" + tableid + " and field_id not in(" + buffer.toString() + ")";
      ResultSet rs = dbopt.executeQuery(sql);
      int i = 0;
      while (rs.next()) {
        if (i == 0) {
          result.append(rs.getString(1));
        } else {
          result.append(",").append(rs.getString(1));
        } 
        i++;
      } 
      rs.close();
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return result.toString();
  }
  
  public String checkTableName(String tableId, String tableName, String domainId) {
    String suss = "1";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String field = dbopt.executeQueryToStr("select table_Id from ttable where table_desname='" + tableName + "' and table_id<>" + tableId + " and domain_id=" + domainId);
      if (field == null || field.equals(""))
        suss = "0"; 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return suss;
  }
  
  public String[][] getBoxSearchField(String tableId, String domainId) throws Exception {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select field_id,field_name,field_boxsearch from tfield where field_table=" + tableId + 
        " and DOMAIN_ID=" + domainId;
      list = dbopt.executeQueryToStrArr2(sql, 3);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public List getOnchangeMethodInfo(String pageId, String domainId) throws Exception {
    List<String[]> list = new ArrayList();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String tableName = "''";
      ResultSet rs = stmt.executeQuery("select area_table from tarea where page_id=" + pageId);
      while (rs.next()) {
        if ("".equals(tableName)) {
          tableName = "'" + rs.getString(1) + "'";
          continue;
        } 
        tableName = String.valueOf(tableName) + ",'" + rs.getString(1) + "'";
      } 
      rs.close();
      rs = stmt.executeQuery("select table_id from ttable where table_name in (" + tableName + ")");
      tableName = "";
      while (rs.next()) {
        if ("".equals(tableName)) {
          tableName = rs.getString(1);
          continue;
        } 
        tableName = String.valueOf(tableName) + "," + rs.getString(1);
      } 
      rs.close();
      if (tableName.length() > 0) {
        rs = stmt.executeQuery("select field_id,field_name,field_boxsearch,field_changemethod,field_fetchsql,field_script,field_toffield,field_show,field_value,field_interfaceName,field_interfacemethod,field_interfaceMethodPara,field_interfacetype from tfield where field_table in(" + 









            
            tableName + ")");
        while (rs.next()) {
          String[] obj = new String[13];
          for (int i = 0; i < 13; i++)
            obj[i] = rs.getString(i + 1); 
          list.add(obj);
        } 
        rs.close();
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public String[] getFieldExtProperty(String fieldId) throws Exception {
    String[] fieldProp = new String[17];
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select field_boxsearch,field_changemethod,field_fetchsql,field_script,field_toffield,field_value,field_filterlist,field_filtersearch,inputSearchType,startRange,handleRange,startText,handleText, field_interfaceName,field_interfacemethod,field_interfaceMethodPara,field_interfacetype from tfield where field_id=" + 

          
          fieldId);
      if (rs.next()) {
        fieldProp[0] = rs.getString(1);
        fieldProp[1] = rs.getString(2);
        fieldProp[2] = rs.getString(3);
        fieldProp[3] = rs.getString(4);
        fieldProp[4] = rs.getString(5);
        fieldProp[5] = rs.getString(6);
        fieldProp[6] = rs.getString(7);
        fieldProp[7] = rs.getString(8);
        fieldProp[8] = rs.getString(9);
        fieldProp[9] = rs.getString(10);
        fieldProp[10] = rs.getString(11);
        fieldProp[11] = rs.getString(12);
        fieldProp[12] = rs.getString(13);
        fieldProp[13] = rs.getString(14);
        fieldProp[14] = rs.getString(15);
        fieldProp[15] = rs.getString(16);
        fieldProp[16] = rs.getString(17);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      ex.printStackTrace();
      throw ex;
    } 
    return fieldProp;
  }
  
  public String[][] getFieldSimpleInfo(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.field_id,a.field_desname,a.field_name from tfield a where a.field_table=" + 
          tableId + 
          " order by field_id", 
          3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public void updateTableEmp(String tableId, String empId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      String orgId = "1";
      base.begin();
      String sql = "select org_id from org_organization_user where emp_Id=" + empId;
      rs = base.executeQuery(sql);
      if (rs.next())
        orgId = rs.getString(1); 
      rs.close();
      sql = "update ttable set createdemp='" + empId + "' ,createdorg='" + orgId + "' where table_id=" + tableId;
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public String getTableEmp(String tableId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "";
    try {
      base.begin();
      String sql = "select e.emp_id,e.empname from org_employee e,ttable t where e.emp_id=t.createdemp and t.table_id=" + tableId;
      rs = base.executeQuery(sql);
      if (rs.next())
        empId = String.valueOf(rs.getString(1)) + ":" + rs.getString(2); 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empId;
  }
  
  public String[][] getFieldFang(String tableId, String empId, String processId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (processId == null || "null".equals(processId) || "".equals(processId)) {
        list = dbopt.executeQueryToStrArr2("SELECT id,protitle,fromtable,getRow,createemp,processId FROM jsf_exportexcel WHERE fromtable=" + 
            tableId + " AND createemp=" + empId + " order by id", 6);
      } else {
        list = dbopt.executeQueryToStrArr2("SELECT id,protitle,fromtable,getRow,createemp,processId FROM jsf_exportexcel WHERE fromtable=" + 
            tableId + " AND createemp=" + empId + " AND processId=" + processId + " order by id", 6);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getSubFieldFang(String tableId, String empId, String processId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("SELECT id,protitle,fromtable,getRow,createemp,processId FROM jsf_exportexcel WHERE parentId=" + 
          tableId + " AND createemp=" + empId + " AND processId=" + processId + " order by id", 6);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public void setTotalValue(String[] displayName, String tableId, String[] totalValue, String[] fieldIsTotal) {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    PreparedStatement pstat = null;
    try {
      conn = base.getDataSource().getConnection();
      pstat = conn.prepareStatement("UPDATE tfield SET totalValue=? WHERE field_desname=? AND field_table=?");
      for (int i = 0; i < fieldIsTotal.length; i++) {
        if ("1".equals(fieldIsTotal[i])) {
          pstat.setString(1, totalValue[i]);
          pstat.setString(2, displayName[i]);
          pstat.setString(3, tableId);
          pstat.executeUpdate();
        } 
      } 
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public String getTotalValue(String fieldId) {
    String totalValue = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "SELECT totalValue FROM tfield WHERE field_id=" + fieldId;
      rs = base.executeQuery(sql);
      totalValue = (rs.getString(1) == null) ? "" : rs.getString(1);
      if ("".equals(totalValue) || "null".equals(totalValue))
        totalValue = "1;no"; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
      totalValue = "1;no";
    } 
    return totalValue;
  }
  
  public String getTemplate(String defaultString, String fileType) {
    StringBuffer template = new StringBuffer("");
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String[] type = fileType.split(",");
      fileType = "";
      for (int i = 0; i < type.length; i++)
        fileType = String.valueOf(fileType) + ",'." + type[i] + "'"; 
      String sql = "SELECT recordId,fileName FROM template_file WHERE fileType in (" + fileType.substring(1) + ") AND flag=2";
      rs = base.executeQuery(sql);
      template.append("<select style='width:82%' name=fieldShowValue id=fieldShowValue><option value=''>--选择模板--</option>");
      while (rs.next()) {
        if (defaultString.equals(rs.getString(1))) {
          template.append("<option value='" + rs.getString(1) + "' selected>" + rs.getString(2) + "</option>");
          continue;
        } 
        template.append("<option value='" + rs.getString(1) + "'>" + rs.getString(2) + "</option>");
      } 
      template.append("</select>");
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return template.toString();
  }
  
  public void setName(String flag, String tableId) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "";
      if ("1".equals(flag)) {
        sql = "update ttable set table_setName='1' where table_id=" + tableId;
      } else {
        sql = "update ttable set table_setName='0' where table_id=" + tableId;
      } 
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public String checkBaseName(String tableId, String tableName, String domainId) {
    DataSourceBase base = new DataSourceBase();
    String re = "0";
    ResultSet rs = null;
    String schema = "";
    String baseType = SystemCommon.getDatabaseType();
    if (baseType.indexOf("oracle") >= 0) {
      schema = "JSDB";
      tableName = tableName.toUpperCase();
    } 
    try {
      base.begin();
      DatabaseMetaData dbmd = base.getDataSource().getConnection().getMetaData();
      rs = dbmd.getTables(null, schema, tableName, null);
      if (rs.next()) {
        re = "1";
      } else {
        re = "0";
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return re;
  }
  
  public String setFieldName(String fieldId, String tableId, String fieldName, String domainId) {
    String sql = "SELECT t.type_name,f.field_len,f.field_type,f.field_name,ta.table_name FROM tfield f,ttype t,ttable ta WHERE f.field_id=" + 
      fieldId + " AND f.field_type=t.type_id AND f.field_table=ta.table_id";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String up = "alter table ";
    String tableName = "";
    String returnString = "&nbsp;";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        returnString = rs.getString(4);
        tableName = rs.getString(5);
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0)
          if ("1000002".equals(rs.getString(3))) {
            up = String.valueOf(up) + tableName + " change " + returnString + " " + fieldName + " " + rs.getString(1) + "(" + rs.getString(2) + ")";
          } else {
            up = String.valueOf(up) + tableName + " change " + returnString + " " + fieldName + " " + rs.getString(1);
          }  
        if (databaseType.indexOf("oracle") >= 0)
          up = "alter table " + tableName + " rename column " + returnString + " to " + fieldName; 
      } 
      base.executeUpdate(up);
      String update = "update tfield set field_name='" + fieldName + "' where field_id=" + fieldId;
      base.executeUpdate(update);
      returnString = fieldName;
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return returnString;
  }
}
