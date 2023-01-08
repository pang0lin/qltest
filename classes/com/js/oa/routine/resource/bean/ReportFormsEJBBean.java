package com.js.oa.routine.resource.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ReportFormsEJBBean extends DataSourceBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getReportForms(String[] para, String domainid) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ArrayList<String[]> alist = new ArrayList();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "";
      String dbType = "SQLSERVER";
      String connStr = SystemCommon.getDatabaseType();
      if (connStr.toUpperCase().indexOf("ORACLE") >= 0) {
        dbType = "ORACLE";
      } else if (connStr.toUpperCase().indexOf("MYSQL") >= 0) {
        dbType = "MYSQL";
      } 
      String tmpWhere = "";
      if (para[0].equals("1")) {
        if (!para[5].equals("-1"))
          tmpWhere = " and d.goodsType_id=" + para[5]; 
        if (dbType.equals("ORACLE")) {
          sql = "select d.goods_id, d.goods_name,d.specs, d.goods_unit, nvl(c.qc,0) as qc ,nvl(c.qcje,0) as qcje, nvl(a.ru,0) as ru, nvl(a.ruje,0) as ruje,nvl(b.chu,0) as chu, nvl(b.chuje,0) as chuje,(nvl(c.qc,0) + nvl(a.ru,0)-nvl(b.chu,0)) As qm,(nvl(c.qcje,0) + nvl(a.ruje,0)-nvl(b.chuje,0)) As qmje,d.price From  st_goods d left join (select a.goods_id,nvl(sum(b.change_amount),0) As ru,nvl(sum(b.factmoney),0) as ruje from st_goods a ,st_stockchange b where a.goods_id=b.goods_id(+) and change_type like 'A%' And (sc_date Between JSDB.FN_STRTODATE('" + 



            
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + "' group by a.goods_id ) a " + 
            " on d.goods_id = a.goods_id left join " + 
            "(select a.goods_id,nvl(b.chu,0) as chu,nvl(b.chuje,0) as chuje from st_goods a , (select a.goods_id,nvl(sum(b.change_amount),0) As chu,nvl(sum(b.factmoney),0) as chuje from st_goods a , st_stockchange b, st_SSMASTER c where a.goods_id=b.goods_id(+) and b.sc_no = c.ss_no and c.ss_outflag = '1' and change_type like 'D%' And (sc_date Between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + 
            "' group by a.goods_id) b where a.goods_id=b.goods_id(+)) b " + 
            " on d.goods_id = b.goods_id left join " + 
            " (select a.goods_id,a.goods_name,(b.A_amount-c.D_amount) As qc,(b.A_money-c.D_money) as qcje from st_goods a, (select a.goods_id,nvl(b.A_amount,0) as A_amount,nvl(b.A_money,0) as A_money from st_goods a ,(select a.goods_id,nvl(sum(b.change_amount),0) As A_amount,nvl(sum(b.factmoney),0) As A_money  from st_goods a , st_stockchange b where a.goods_id=b.goods_id(+) and change_type like 'A%' and sc_date< JSDB.FN_STRTODATE('" + 
            para[2] + " 00:00:00','L') and change_stock='" + 
            para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id(+)) b, (select a.goods_id,nvl(b.D_amount,0) as D_amount,nvl(b.D_money,0) as D_money from st_goods a , (select a.goods_id,nvl(sum(b.change_amount),0) As D_amount,nvl(sum(b.factmoney),0) As D_money from st_goods a  , st_stockchange b, st_SSMASTER c where a.goods_id=b.goods_id(+) and b.sc_no = c.ss_no and c.ss_outflag = '1' and change_type like 'D%' and sc_date<JSDB.FN_STRTODATE('" + 
            para[2] + "', 'L') And change_stock='" + para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id(+) ) c where a.goods_id=b.goods_id and a.goods_id=c.goods_id) c" + 
            " on d.goods_id = c.goods_id  " + 
            
            " where (ru <> 0 or chu <> 0 or qc <> 0) " + tmpWhere;
        } else if ("MYSQL".equals(dbType)) {
          sql = "select d.goods_id, d.goods_name,d.specs,d.goods_unit, ifnull(c.qc,0) as qc ,ifnull(c.qcje,0) as qcje, ifnull(a.ru,0) as ru, ifnull(a.ruje,0) as ruje,ifnull(b.chu,0) as chu, ifnull(b.chuje,0) as chuje,(ifnull(c.qc,0) + ifnull(a.ru,0)-ifnull(b.chu,0)) As qm,(ifnull(c.qcje,0) + ifnull(a.ruje,0)-ifnull(b.chuje,0)) As qmje,d.price From  st_goods d left join (select a.goods_id,ifnull(sum(b.change_amount),0) As ru,ifnull(sum(b.factmoney),0) as ruje from st_goods a ,st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' And (sc_date Between ('" + 



            
            para[2] + 
            " 00:00:00') And ('" + 
            para[3] + " 23:59:59')) and change_stock='" + 
            para[1] + "' group by a.goods_id ) a " + 
            " on d.goods_id = a.goods_id left join " + 
            "(select a.goods_id,ifnull(b.chu,0) as chu,ifnull(b.chuje,0) as chuje from st_goods a , (select a.goods_id,ifnull(sum(b.change_amount),0) As chu,ifnull(sum(b.factmoney),0) as chuje from st_goods a , st_stockchange b, st_SSMASTER c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and change_type like 'D%' And (sc_date Between ('" + 
            para[2] + 
            " 00:00:00') And ('" + 
            para[3] + " 23:59:59')) and change_stock='" + 
            para[1] + 
            "' group by a.goods_id) b where a.goods_id=b.goods_id) b " + 
            " on d.goods_id = b.goods_id left join " + 
            " (select a.goods_id,a.goods_name,(ifnull(b.A_amount,0)-ifnull(c.D_amount,0)) As qc,(ifnull(b.A_money,0)-ifnull(c.D_money,0)) as qcje from st_goods a, (select a.goods_id,ifnull(b.A_amount,0) as A_amount,ifnull(b.A_money,0) as A_money from st_goods a left join (select a.goods_id,ifnull(sum(b.change_amount),0) As A_amount,ifnull(sum(b.factmoney),0) As A_money  from st_goods a , st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' and sc_date< ('" + 
            para[2] + " 00:00:00') and change_stock='" + 
            para[1] + "' group by a.goods_id) b on a.goods_id=b.goods_id) b left join (select a.goods_id,ifnull(b.D_amount,0) as D_amount,ifnull(b.D_money,0) as D_money from st_goods a , (select a.goods_id,ifnull(sum(b.change_amount),0) As D_amount,ifnull(sum(b.factmoney),0) As D_money from st_goods a  , st_stockchange b, st_SSMASTER c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and change_type like 'D%' and sc_date<('" + 
            para[2] + "') And change_stock='" + para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id ) c on b.goods_id=c.goods_id where a.goods_id=b.goods_id) c" + 
            " on d.goods_id = c.goods_id  " + 
            
            " where (ru <> 0 or chu <> 0 or qc <> 0) " + tmpWhere;
        } else {
          sql = "select d.goods_id, d.goods_name,d.specs,d.goods_unit, isnull(c.qc,0) as qc ,isnull(c.qcje,0) as qcje, isnull(a.ru,0) as ru, isnull(a.ruje,0) as ruje,isnull(b.chu,0) as chu, isnull(b.chuje,0) as chuje,(isnull(c.qc,0) + isnull(a.ru,0)-isnull(b.chu,0)) As qm,(isnull(c.qcje,0) + isnull(a.ruje,0)-isnull(b.chuje,0)) As qmje,d.price From  st_goods d left join (select a.goods_id,isnull(sum(b.change_amount),0) As ru,isnull(sum(b.factmoney),0) as ruje from st_goods a ,st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' And (sc_date Between JSDB.FN_STRTODATE('" + 



            
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + "' group by a.goods_id ) a " + 
            " on d.goods_id = a.goods_id left join " + 
            "(select a.goods_id,isnull(b.chu,0) as chu,isnull(b.chuje,0) as chuje from st_goods a , (select a.goods_id,isnull(sum(b.change_amount),0) As chu,isnull(sum(b.factmoney),0) as chuje from st_goods a , st_stockchange b, st_SSMASTER c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and change_type like 'D%' And (sc_date Between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + 
            "' group by a.goods_id) b where a.goods_id=b.goods_id) b " + 
            " on d.goods_id = b.goods_id left join " + 
            " (select a.goods_id,a.goods_name,(isnull(b.A_amount,0)-isnull(c.D_amount,0)) As qc,(isnull(b.A_money,0)-isnull(c.D_money,0)) as qcje from st_goods a, (select a.goods_id,isnull(b.A_amount,0) as A_amount,isnull(b.A_money,0) as A_money from st_goods a left join (select a.goods_id,isnull(sum(b.change_amount),0) As A_amount,isnull(sum(b.factmoney),0) As A_money  from st_goods a , st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' and sc_date< JSDB.FN_STRTODATE('" + 
            para[2] + " 00:00:00','L') and change_stock='" + 
            para[1] + "' group by a.goods_id) b on a.goods_id=b.goods_id) b left join (select a.goods_id,isnull(b.D_amount,0) as D_amount,isnull(b.D_money,0) as D_money from st_goods a , (select a.goods_id,isnull(sum(b.change_amount),0) As D_amount,isnull(sum(b.factmoney),0) As D_money from st_goods a  , st_stockchange b, st_SSMASTER c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and change_type like 'D%' and sc_date<JSDB.FN_STRTODATE('" + 
            para[2] + "', 'L') And change_stock='" + para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id ) c on b.goods_id=c.goods_id where a.goods_id=b.goods_id) c" + 
            " on d.goods_id = c.goods_id  " + 
            
            " where (ru <> 0 or chu <> 0 or qc <> 0) " + tmpWhere;
        } 
      } else if (para[0].equals("2")) {
        if (!para[5].equals("-1"))
          tmpWhere = " and a.goodsType_id=" + para[5]; 
        if (dbType.equals("ORACLE")) {
          sql = "select b.ORGNAME,a.goods_id,a.goods_name,a.goods_unit,case when c.D_amount != 0 then round(c.D_money/c.D_amount,2) else a.averageprice end As price,c.D_amount,c.D_money As ss_money,0 as kk from ORG_ORGANIZATION b,st_goods a ,(select goods_id,drawdept,nvl(sum(change_amount),0) As D_amount, nvl(sum(factmoney),0) As D_money from st_stockchange where change_type='DSS' and SC_NO in(select ss_no from st_SSMASTER where SS_OUTFLAG='1') and (sc_date Between JSDB.FN_STRTODATE('" + 

            
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + "' group by goods_id,drawdept ) c " + 
            "where a.DOMAIN_ID=" + domainid + 
            " and a.goods_id=c.goods_id(+) and b.ORG_ID=c.drawdept " + tmpWhere + " order by b.ORGNAME";
        } else if (dbType.equals("MYSQL")) {
          sql = "select b.ORGNAME,a.goods_id,a.goods_name,a.goods_unit,case when c.D_amount != 0 then round(c.D_money/c.D_amount,2) else a.averageprice end As price,c.D_amount,c.D_money As ss_money,0 as kk from ORG_ORGANIZATION b,st_goods a left OUTER JOIN (select goods_id,drawdept,ifnull(sum(change_amount),0) As D_amount, ifnull(sum(factmoney),0) As D_money from st_stockchange where change_type='DSS' and SC_NO in(select ss_no from st_SSMASTER where SS_OUTFLAG='1') and (sc_date Between '" + 

            
            para[2] + " 00:00:00' And '" + 
            para[3] + " 23:59:59') and change_stock='" + para[1] + 
            "' group by goods_id,drawdept ) c " + 
            "on a.goods_id=c.goods_id where a.DOMAIN_ID=" + domainid + 
            " and b.ORG_ID=c.drawdept " + tmpWhere + " order by b.ORGNAME";
        } else {
          sql = "select b.ORGNAME,a.goods_id,a.goods_name,a.goods_unit,case when c.D_amount != 0 then round(c.D_money/c.D_amount,2) else a.averageprice end As price,c.D_amount,c.D_money As ss_money,0 as kk from ORG_ORGANIZATION b,st_goods a left OUTER JOIN (select goods_id,drawdept,isnull(sum(change_amount),0) As D_amount, isnull(sum(factmoney),0) As D_money from st_stockchange where change_type='DSS' and SC_NO in(select ss_no from st_SSMASTER where SS_OUTFLAG='1') and (sc_date Between JSDB.FN_STRTODATE('" + 

            
            para[2] + " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + para[1] + 
            "' group by goods_id,drawdept ) c " + 
            "on a.goods_id=c.goods_id where a.DOMAIN_ID=" + domainid + 
            " and b.ORG_ID=c.drawdept " + tmpWhere + " order by b.ORGNAME";
        } 
      } else if (para[0].equals("3")) {
        if (dbType.equals("ORACLE")) {
          sql = "select b.goodstype_id,b.goodstype_name,c.ORG_ID,c.ORGNAME,a.num from (select b.goodstype_id,c.ss_dept,sum(nvl(d.goods_money,0)) as num from st_goods a,st_goodstype b,st_ssmaster c,st_ssdetail d  where a.goodstype_id=b.goodstype_id and a.goods_id=d.goods_id and d.ss_no=c.ss_no and c.ss_outflag = '1'  and c.ss_stock='" + 
            
            para[1] + 
            "' and c.check_flag='Y' and c.check_date between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L') " + 
            " group by b.goodstype_id,c.ss_dept) a,st_goodstype b,ORG_ORGANIZATION c " + 
            " where b.DOMAIN_ID=" + domainid + " and a.goodstype_id=b.goodstype_id and a.ss_dept=c.ORG_ID " + tmpWhere + " order by c.ORG_ID,b.goodstype_id ";
        } else if ("MYSQL".equals(dbType)) {
          sql = "select b.goodstype_id,b.goodstype_name,c.ORG_ID,c.ORGNAME,a.num from (select b.goodstype_id,c.ss_dept,sum(ifnull(d.goods_money,0)) as num from st_goods a,st_goodstype b,st_ssmaster c,st_ssdetail d  where a.goodstype_id=b.goodstype_id and a.goods_id=d.goods_id and d.ss_no=c.ss_no and c.ss_outflag = '1'  and c.ss_stock='" + 
            
            para[1] + 
            "' and c.check_flag='Y' and c.check_date between ('" + 
            para[2] + " 00:00:00') And ('" + 
            para[3] + " 23:59:59') " + 
            " group by b.goodstype_id,c.ss_dept) a,st_goodstype b,ORG_ORGANIZATION c " + 
            " where b.DOMAIN_ID=" + domainid + " and a.goodstype_id=b.goodstype_id and a.ss_dept=c.ORG_ID " + tmpWhere + " order by c.ORG_ID,b.goodstype_id ";
        } else {
          sql = "select b.goodstype_id,b.goodstype_name,c.ORG_ID,c.ORGNAME,a.num from (select b.goodstype_id,c.ss_dept,sum(isnull(d.goods_money,0)) as num from st_goods a,st_goodstype b,st_ssmaster c,st_ssdetail d  where a.goodstype_id=b.goodstype_id and a.goods_id=d.goods_id and d.ss_no=c.ss_no and c.ss_outflag = '1'  and c.ss_stock='" + 
            
            para[1] + 
            "' and c.check_flag='Y' and c.check_date between JSDB.FN_STRTODATE('" + 
            para[2] + " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L') " + 
            " group by b.goodstype_id,c.ss_dept) a,st_goodstype b,ORG_ORGANIZATION c " + 
            " where b.DOMAIN_ID=" + domainid + " and a.goodstype_id=b.goodstype_id and a.ss_dept=c.ORG_ID " + tmpWhere + " order by c.ORG_ID,b.goodstype_id ";
        } 
      } else if (para[0].equals("4")) {
        if (dbType.equals("ORACLE")) {
          sql = "select b.ORGNAME,d.goods_name,d.goods_unit,sum(nvl(ss_amount,0)) num  from st_goods a,st_ssmaster c,st_ssdetail d,ORG_ORGANIZATION b  where a.DOMAIN_ID=" + 
            
            domainid + 
            " and a.goods_id=d.goods_id and d.ss_no=c.ss_no and c.ss_dept=b.org_id and c.ss_outflag = '1' " + 
            " and c.ss_stock='" + para[1] + 
            "' and c.check_flag='Y' and c.check_date between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L') " + 
            " and d.goods_name like '%" + para[4] + "%' group by b.ORGNAME,d.goods_name,d.goods_unit order by b.ORGNAME,d.goods_name ";
        } else if ("MYSQL".equals(dbType)) {
          sql = "select b.ORGNAME,d.goods_name,d.goods_unit,sum(ifnull(ss_amount,0)) num  from st_goods a,st_ssmaster c,st_ssdetail d,ORG_ORGANIZATION b  where a.DOMAIN_ID=" + 
            
            domainid + 
            " and a.goods_id=d.goods_id and d.ss_no=c.ss_no and c.ss_dept=b.org_id and c.ss_outflag = '1' " + 
            " and c.ss_stock='" + para[1] + 
            "' and c.check_flag='Y' and c.check_date between ('" + 
            para[2] + 
            " 00:00:00') And ('" + para[3] + 
            " 23:59:59') " + 
            " and d.goods_name like '%" + para[4] + "%' group by b.ORGNAME,d.goods_name,d.goods_unit order by b.ORGNAME,d.goods_name ";
        } else {
          sql = 
            "select b.ORGNAME,d.goods_name,d.goods_unit,sum(isnull(ss_amount,0)) num  from st_goods a,st_ssmaster c,st_ssdetail d,ORG_ORGANIZATION b  where a.DOMAIN_ID=" + 
            
            domainid + 
            " and a.goods_id=d.goods_id and d.ss_no=c.ss_no and c.ss_dept=b.org_id and c.ss_outflag = '1' " + 
            " and c.ss_stock='" + para[1] + 
            "' and c.check_flag='Y' and c.check_date between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + para[3] + 
            " 23:59:59', 'L') " + 
            " and d.goods_name like '%" + para[4] + "%' group by b.ORGNAME,d.goods_name,d.goods_unit order by b.ORGNAME,d.goods_name ";
        } 
      } else if (para[0].equals("11")) {
        if (!para[5].equals("-1"))
          tmpWhere = " and d.goodsType_id=" + para[5]; 
        if (dbType.equals("ORACLE"))
          sql = "select d.goods_id, d.goods_name,d.specs, d.goods_unit, nvl(c.qc,0) as qc ,nvl(c.qcje,0) as qcje, nvl(a.ru,0) as ru, nvl(a.ruje,0) as ruje,nvl(b.chu,0) as chu, nvl(b.chuje,0) as chuje,(nvl(c.qc,0) + nvl(a.ru,0)-nvl(b.chu,0)) As qm,(nvl(c.qcje,0) + nvl(a.ruje,0)-nvl(b.chuje,0)) As qmje,d.price From  st_goods d left join (select a.goods_id,nvl(sum(b.change_amount),0) As ru,nvl(sum(b.factmoney),0) as ruje from st_goods a ,st_stockchange b where a.goods_id=b.goods_id(+) and change_type like 'A%' And (sc_date Between JSDB.FN_STRTODATE('" + 



            
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + "' group by a.goods_id ) a " + 
            " on d.goods_id = a.goods_id left join " + 
            "(select a.goods_id,nvl(b.chu,0) as chu,nvl(b.chuje,0) as chuje from st_goods a , (select a.goods_id,nvl(sum(b.change_amount),0) As chu,nvl(sum(b.factmoney),0) as chuje from st_goods a , st_stockchange b, st_ssmaster c where a.goods_id=b.goods_id(+) and b.sc_no = c.ss_no and c.ss_outflag = '1' and b.change_amount > 0 and change_type like 'D%' And (sc_date Between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + 
            "' group by a.goods_id) b where a.goods_id=b.goods_id(+)) b " + 
            " on d.goods_id = b.goods_id left join " + 
            " (select a.goods_id,a.goods_name,(b.A_amount-c.D_amount) As qc,(b.A_money-c.D_money) as qcje from st_goods a, (select a.goods_id,nvl(b.A_amount,0) as A_amount,nvl(b.A_money,0) as A_money from st_goods a ,(select a.goods_id,nvl(sum(b.change_amount),0) As A_amount,nvl(sum(b.factmoney),0) As A_money  from st_goods a , st_stockchange b where a.goods_id=b.goods_id(+) and change_type like 'A%' and sc_date< JSDB.FN_STRTODATE('" + 
            para[2] + " 00:00:00','L') and change_stock='" + 
            para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id(+)) b, (select a.goods_id,nvl(b.D_amount,0) as D_amount,nvl(b.D_money,0) as D_money from st_goods a , (select a.goods_id,nvl(sum(b.change_amount),0) As D_amount,nvl(sum(b.factmoney),0) As D_money from st_goods a  , st_stockchange b, st_ssmaster c where a.goods_id=b.goods_id(+) and b.sc_no = c.ss_no and c.ss_outflag = '1' and b.change_amount > 0 and change_type like 'D%' and sc_date<JSDB.FN_STRTODATE('" + 
            para[2] + "', 'L') And change_stock='" + para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id(+) ) c where a.goods_id=b.goods_id and a.goods_id=c.goods_id) c" + 
            " on d.goods_id = c.goods_id  " + 
            
            " where (ru <> 0 or chu <> 0 or qc <> 0) " + tmpWhere; 
        if (dbType.equals("MYSQL")) {
          sql = "select d.goods_id, d.goods_name,d.specs,d.goods_unit, ifnull(c.qc,0) as qc ,ifnull(c.qcje,0) as qcje, ifnull(a.ru,0) as ru, ifnull(a.ruje,0) as ruje,ifnull(b.chu,0) as chu, ifnull(b.chuje,0) as chuje,(ifnull(c.qc,0) + ifnull(a.ru,0)-ifnull(b.chu,0)) As qm,(ifnull(c.qcje,0) + ifnull(a.ruje,0)-ifnull(b.chuje,0)) As qmje,d.price From  st_goods d left join (select a.goods_id,ifnull(sum(b.change_amount),0) As ru,ifnull(sum(b.factmoney),0) as ruje from st_goods a ,st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' And (sc_date Between ('" + 



            
            para[2] + 
            " 00:00:00') And ('" + 
            para[3] + " 23:59:59')) and change_stock='" + 
            para[1] + "' group by a.goods_id ) a " + 
            " on d.goods_id = a.goods_id left join " + 
            "(select a.goods_id,ifnull(b.chu,0) as chu,ifnull(b.chuje,0) as chuje from st_goods a , (select a.goods_id,ifnull(sum(b.change_amount),0) As chu,ifnull(sum(b.factmoney),0) as chuje from st_goods a , st_stockchange b, st_ssmaster c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and b.change_amount > 0 and change_type like 'D%' And (sc_date Between ('" + 
            para[2] + 
            " 00:00:00') And ('" + 
            para[3] + " 23:59:59')) and change_stock='" + 
            para[1] + 
            "' group by a.goods_id) b where a.goods_id=b.goods_id) b " + 
            " on d.goods_id = b.goods_id left join " + 
            " (select a.goods_id,a.goods_name,(b.A_amount-c.D_amount) As qc,(b.A_money-c.D_money) as qcje from st_goods a, (select a.goods_id,ifnull(b.A_amount,0) as A_amount,ifnull(b.A_money,0) as A_money from st_goods a ,(select a.goods_id,ifnull(sum(b.change_amount),0) As A_amount,ifnull(sum(b.factmoney),0) As A_money  from st_goods a , st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' and sc_date< ('" + 
            para[2] + " 00:00:00') and change_stock='" + 
            para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id) b, (select a.goods_id,ifnull(b.D_amount,0) as D_amount,ifnull(b.D_money,0) as D_money from st_goods a , (select a.goods_id,ifnull(sum(b.change_amount),0) As D_amount,ifnull(sum(b.factmoney),0) As D_money from st_goods a  , st_stockchange b, st_ssmaster c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and b.change_amount > 0 and change_type like 'D%' and sc_date<('" + 
            para[2] + "') And change_stock='" + para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id ) c where a.goods_id=b.goods_id and a.goods_id=c.goods_id) c" + 
            " on d.goods_id = c.goods_id  " + 
            
            " where (ru <> 0 or chu <> 0 or qc <> 0) " + tmpWhere;
        } else {
          sql = "select d.goods_id, d.goods_name,d.specs,d.goods_unit, isnull(c.qc,0) as qc ,isnull(c.qcje,0) as qcje, isnull(a.ru,0) as ru, isnull(a.ruje,0) as ruje,isnull(b.chu,0) as chu, isnull(b.chuje,0) as chuje,(isnull(c.qc,0) + isnull(a.ru,0)-isnull(b.chu,0)) As qm,(isnull(c.qcje,0) + isnull(a.ruje,0)-isnull(b.chuje,0)) As qmje,d.price From  st_goods d left join (select a.goods_id,isnull(sum(b.change_amount),0) As ru,isnull(sum(b.factmoney),0) as ruje from st_goods a ,st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' And (sc_date Between JSDB.FN_STRTODATE('" + 



            
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + "' group by a.goods_id ) a " + 
            " on d.goods_id = a.goods_id left join " + 
            "(select a.goods_id,isnull(b.chu,0) as chu,isnull(b.chuje,0) as chuje from st_goods a , (select a.goods_id,isnull(sum(b.change_amount),0) As chu,isnull(sum(b.factmoney),0) as chuje from st_goods a , st_stockchange b, st_ssmaster c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and b.change_amount > 0 and change_type like 'D%' And (sc_date Between JSDB.FN_STRTODATE('" + 
            para[2] + 
            " 00:00:00', 'L') And JSDB.FN_STRTODATE('" + 
            para[3] + " 23:59:59', 'L')) and change_stock='" + 
            para[1] + 
            "' group by a.goods_id) b where a.goods_id=b.goods_id) b " + 
            " on d.goods_id = b.goods_id left join " + 
            " (select a.goods_id,a.goods_name,(b.A_amount-c.D_amount) As qc,(b.A_money-c.D_money) as qcje from st_goods a, (select a.goods_id,isnull(b.A_amount,0) as A_amount,isnull(b.A_money,0) as A_money from st_goods a ,(select a.goods_id,isnull(sum(b.change_amount),0) As A_amount,isnull(sum(b.factmoney),0) As A_money  from st_goods a , st_stockchange b where a.goods_id=b.goods_id and change_type like 'A%' and sc_date< JSDB.FN_STRTODATE('" + 
            para[2] + " 00:00:00','L') and change_stock='" + 
            para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id) b, (select a.goods_id,isnull(b.D_amount,0) as D_amount,isnull(b.D_money,0) as D_money from st_goods a , (select a.goods_id,isnull(sum(b.change_amount),0) As D_amount,isnull(sum(b.factmoney),0) As D_money from st_goods a  , st_stockchange b, st_ssmaster c where a.goods_id=b.goods_id and b.sc_no = c.ss_no and c.ss_outflag = '1' and b.change_amount > 0 and change_type like 'D%' and sc_date<JSDB.FN_STRTODATE('" + 
            para[2] + "', 'L') And change_stock='" + para[1] + "' group by a.goods_id) b where a.goods_id=b.goods_id ) c where a.goods_id=b.goods_id and a.goods_id=c.goods_id) c" + 
            " on d.goods_id = c.goods_id  " + 
            
            " where (ru <> 0 or chu <> 0 or qc <> 0) " + tmpWhere;
        } 
      } 
      if (para[0].equals("1")) {
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String[] obj = new String[13];
          for (int i = 0; i < 13; i++)
            obj[i] = rs.getString(i + 1); 
          alist.add(obj);
        } 
        rs.close();
      } else if (para[0].equals("11")) {
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String[] obj = new String[13];
          for (int i = 0; i < 13; i++)
            obj[i] = rs.getString(i + 1); 
          alist.add(obj);
        } 
        rs.close();
      } else if (para[0].equals("2")) {
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String[] obj = { "", "", "", "", "", "", "", "" };
          obj[0] = rs.getString(1);
          obj[1] = rs.getString(2);
          obj[2] = rs.getString(3);
          obj[3] = rs.getString(4);
          obj[4] = rs.getString(5);
          obj[5] = rs.getString(6);
          obj[6] = rs.getString(7);
          obj[7] = rs.getString(8);
          alist.add(obj);
        } 
        rs.close();
      } else if (para[0].equals("3")) {
        int typeCount = 0;
        ResultSet rs = stmt.executeQuery("select goodstype.goodstype_id,goodstype.goodstype_name from st_goodstype goodstype,st_STOCK_GOODSTYPE stock  where goodstype.DOMAIN_ID=" + 
            domainid + " and stock.goodstype_id=goodstype.goodstype_id and goodstype.parentid=0 and stock.stock_id='" + 
            para[1] + 
            "' order by goodstype.goodstype_id");
        String[] objtype = new String[100];
        objtype[0] = "部门";
        while (rs.next()) {
          typeCount++;
          objtype[typeCount] = rs.getString(2);
        } 
        objtype[typeCount + 1] = "合计";
        String[] objcount = new String[100];
        objcount[0] = "数量";
        objcount[1] = (new StringBuilder(String.valueOf(typeCount))).toString();
        alist.add(objcount);
        alist.add(objtype);
        rs.close();
        rs = stmt.executeQuery(sql);
        String curBm = "";
        int i = 0;
        int j = 0;
        boolean bStart = false;
        String[] obj = new String[100];
        while (rs.next()) {
          if (!curBm.equals(rs.getString(4))) {
            if (bStart)
              alist.add(obj); 
            obj = new String[100];
            for (i = 0; i < 100; i++)
              obj[i] = ""; 
            bStart = true;
            curBm = rs.getString(4);
            obj[0] = rs.getString(4);
          } 
          j = 1;
          while (j <= typeCount) {
            if (rs.getString(2).equals(objtype[j])) {
              obj[j] = rs.getString(5);
              break;
            } 
            j++;
          } 
        } 
        if (bStart)
          alist.add(obj); 
      } else if (para[0].equals("5")) {
        int typeCount = 0;
        if (!para[5].equals("-1"))
          tmpWhere = " and b.goodsType_id=" + para[5]; 
        String str5 = "";
        if (dbType.equals("ORACLE")) {
          str5 = "select c.goodstype_name,b.goods_name,b.goods_unit,a.stock_amount,nvl(b.AVERAGEPRICE,b.price) from st_GOODSSTOCK a,st_GOODS b,st_GOODSTYPE c where b.DOMAIN_ID=" + 
            domainid + 
            " and a.goods_id=b.goods_id and b.goodstype_id=c.goodstype_id and a.stock_id='" + 
            para[1] + 
            "' " + tmpWhere + " order by c.goodstype_name";
        } else if ("MYSQL".equals(dbType)) {
          str5 = "select c.goodstype_name,b.goods_name,b.goods_unit,a.stock_amount,ifnull(b.AVERAGEPRICE,b.price) from st_GOODSSTOCK a,st_GOODS b,st_GOODSTYPE c where b.DOMAIN_ID=" + 
            domainid + 
            " and a.goods_id=b.goods_id and b.goodstype_id=c.goodstype_id and a.stock_id='" + 
            para[1] + 
            "' " + tmpWhere + " order by c.goodstype_name";
        } else {
          str5 = "select c.goodstype_name,b.goods_name,b.goods_unit,a.stock_amount,isnull(b.AVERAGEPRICE,b.price) from st_GOODSSTOCK a,st_GOODS b,st_GOODSTYPE c where b.DOMAIN_ID=" + 
            domainid + 
            " and a.goods_id=b.goods_id and b.goodstype_id=c.goodstype_id and a.stock_id='" + 
            para[1] + 
            "' " + tmpWhere + " order by c.goodstype_name";
        } 
        ResultSet rs = stmt.executeQuery(str5);
        while (rs.next()) {
          String[] obj = { "", "", "", "", "" };
          obj[0] = rs.getString(1);
          obj[1] = rs.getString(2);
          obj[2] = rs.getString(3);
          obj[3] = rs.getString(4);
          obj[4] = rs.getString(5);
          alist.add(obj);
        } 
        rs.close();
      } else {
        int typeCount = 0;
        ResultSet rs = stmt.executeQuery("select a.goods_id,a.goods_name from st_goods a,st_goodstype b where b.DOMAIN_ID=" + 
            domainid + 
            " and a.goodstype_id = b.goodstype_id and a.stock_id='" + 
            para[1] + 
            "' and a.goods_name like '%" + 
            para[4] + 
            "%' order by a.goods_name");
        String[] objtype = new String[500];
        objtype[0] = "产品";
        while (rs.next()) {
          typeCount++;
          objtype[typeCount] = rs.getString(2);
        } 
        String[] objcount = new String[500];
        objcount[0] = "数量";
        objcount[1] = (new StringBuilder(String.valueOf(typeCount))).toString();
        alist.add(objcount);
        alist.add(objtype);
        rs.close();
        rs = stmt.executeQuery(sql);
        String curBm = "";
        int i = 0;
        int j = 0;
        boolean bStart = false;
        String[] obj = new String[500];
        while (rs.next()) {
          if (!curBm.equals(rs.getString(1))) {
            if (bStart)
              alist.add(obj); 
            obj = new String[500];
            for (i = 0; i < 500; i++)
              obj[i] = ""; 
            bStart = true;
            curBm = rs.getString(1);
            obj[0] = rs.getString(1);
          } 
          j = 1;
          while (j <= typeCount) {
            if (rs.getString(2).equals(objtype[j])) {
              obj[j] = rs.getString(4);
              obj[500 - j] = rs.getString(3);
              break;
            } 
            j++;
          } 
        } 
        if (bStart)
          alist.add(obj); 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return alist;
  }
}
