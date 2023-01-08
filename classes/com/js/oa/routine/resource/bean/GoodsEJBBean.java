package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.GoodsPO;
import com.js.oa.routine.resource.po.GoodsStockPO;
import com.js.oa.routine.resource.po.GoodsTypePO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.hibernate.Query;

public class GoodsEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(GoodsPO goodsPO, String goodsTypeId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      String[] splitId = goodsTypeId.split("#");
      Query query = this.session.createQuery(
          "select aaa.id from com.js.oa.routine.resource.po.GoodsPO aaa where aaa.name='" + 
          goodsPO.getName() + 
          "' and aaa.domainid = " + 
          goodsPO.getDomainid() + " and aaa.stockId =" + 
          splitId[1]);
      Iterator itor = query.iterate();
      if (itor.hasNext()) {
        success = Boolean.FALSE;
      } else {
        GoodsTypePO goodsTypePO = (GoodsTypePO)this.session.load(GoodsTypePO.class, new Long(splitId[0]));
        goodsPO.setGoodsType(goodsTypePO);
        goodsPO.setStockId(splitId[1]);
        itor = this.session.iterate("select max(po.num) from com.js.oa.routine.resource.po.GoodsPO po inner join po.goodsType tpo where tpo.id=" + 
            goodsPO.getGoodsType().getId());
        int num = 0;
        if (itor.hasNext()) {
          Object obj = itor.next();
          if (obj != null && !obj.toString().equals("") && 
            !obj.toString().equalsIgnoreCase("null"))
            num = Integer.parseInt(obj.toString()); 
        } 
        num++;
        goodsPO.setNum(new Integer(num));
        String prefix = "WP";
        itor = this.session.iterate("select po.goodsTypePrefix from com.js.oa.routine.resource.po.GoodsTypePO po where po.id=" + 
            goodsPO.getGoodsType().getId());
        if (itor.hasNext()) {
          Object obj = itor.next();
          if (obj != null && !obj.toString().equals("") && 
            !obj.toString().equalsIgnoreCase("null"))
            prefix = obj.toString(); 
        } 
        if (num < 10) {
          goodsPO.setId(String.valueOf(goodsPO.getDomainid()) + "_" + prefix + "000" + num);
        } else if (num < 100) {
          goodsPO.setId(String.valueOf(goodsPO.getDomainid()) + "_" + prefix + "00" + num);
        } else if (num < 1000) {
          goodsPO.setId(String.valueOf(goodsPO.getDomainid()) + "_" + prefix + "0" + num);
        } else {
          goodsPO.setId(String.valueOf(goodsPO.getDomainid()) + "_" + prefix + num);
        } 
        this.session.save(goodsPO);
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public String[] getSingleGoods(String goodsId) throws Exception {
    String[] goods = { "", "", "", "", "", "", "", "", "", "" };
    begin();
    try {
      Query query = this.session.createQuery("select bbb.id,aaa.name,aaa.unit,aaa.price,aaa.remark,aaa.maxamount,aaa.minamount,aaa.specs,aaa.model,aaa.pic from com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb where aaa.id='" + 
          
          goodsId + "'");
      Iterator<Object[]> itor = query.iterate();
      if (itor.hasNext()) {
        Object[] obj = itor.next();
        goods[0] = obj[0].toString();
        goods[1] = obj[1].toString();
        goods[2] = obj[2].toString();
        goods[3] = obj[3].toString();
        if (obj[4] != null)
          goods[4] = obj[4].toString(); 
        goods[5] = obj[5].toString();
        goods[6] = obj[6].toString();
        goods[7] = (obj[7] == null) ? "" : obj[7].toString();
        goods[8] = (obj[8] == null) ? "" : obj[8].toString();
        goods[9] = (obj[9] == null) ? "" : obj[9].toString();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return goods;
  }
  
  public Boolean delete(String ids) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "from com.js.oa.routine.resource.po.GoodsPO aaa where '" + 
          ids + "' like concat('%$', aaa.id, '$%') ";
      } else {
        tmpSql = 
          "from com.js.oa.routine.resource.po.GoodsPO aaa where '" + 
          ids + 
          "' like\tJSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(aaa.id, '$%')) ";
      } 
      this.session.delete(tmpSql);
      this.session.flush();
      deleteGoods(ids);
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean noused(String id, String opt) {
    Boolean success = new Boolean(false);
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "UPDATE st_goods SET goods_status=" + opt + " where goods_id='" + id + "'";
      int x = base.executeUpdate(sql);
      if (x > 0)
        success = Boolean.valueOf(true); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return success;
  }
  
  public Boolean update(GoodsPO goodsPO, String goodsTypeId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.id from com.js.oa.routine.resource.po.GoodsPO aaa where aaa.id<>'" + 
          goodsPO.getId() + 
          "' and aaa.domainid = " + 
          goodsPO.getDomainid() + " and aaa.stockId =" + 
          goodsPO.getStockId() + " and aaa.name='" + goodsPO.getName() + "'");
      Iterator<GoodsStockPO> itor = query.iterate();
      if (itor.hasNext()) {
        success = Boolean.FALSE;
      } else {
        GoodsPO needUpdatePO = (GoodsPO)this.session.load(GoodsPO.class, 
            goodsPO.getId());
        GoodsTypePO goodsTypePO = (GoodsTypePO)this.session.load(
            GoodsTypePO.class, 
            new Long(goodsTypeId));
        needUpdatePO.setGoodsType(goodsTypePO);
        needUpdatePO.setName(goodsPO.getName());
        needUpdatePO.setUnit(goodsPO.getUnit());
        needUpdatePO.setPrice(goodsPO.getPrice());
        needUpdatePO.setMaxamount(goodsPO.getMaxamount());
        needUpdatePO.setMinamount(goodsPO.getMinamount());
        needUpdatePO.setRemark(goodsPO.getRemark());
        needUpdatePO.setStockId(goodsPO.getStockId());
        needUpdatePO.setSpecs(goodsPO.getSpecs());
        needUpdatePO.setModel(goodsPO.getModel());
        needUpdatePO.setPic(goodsPO.getPic());
        query = this.session.createQuery("select gsPO from com.js.oa.routine.resource.po.GoodsStockPO gsPO where gsPO.goodsId ='" + 
            goodsPO.getId() + "'");
        itor = query.iterate();
        if (itor.hasNext()) {
          GoodsStockPO gsPO = itor.next();
          gsPO.setStockId(new Long(goodsPO.getStockId()));
          this.session.update(gsPO);
        } 
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public String getVindicate(String where) throws Exception {
    StringBuffer ids = new StringBuffer();
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.id from com.js.oa.routine.resource.po.GoodsPO aaa where " + 
          where);
      Iterator<E> itor = query.iterate();
      while (itor.hasNext())
        ids.append("$" + itor.next().toString() + "$"); 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ids.toString();
  }
  
  public void deleteGoods(String ids) {
    String tmpSql = "";
    String databaseType = SystemCommon.getDatabaseType();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "DELETE FROM st_ptdetail where '" + ids + "' like concat('%$', goods_Id, '$%') ";
      } else {
        tmpSql = "DELETE FROM st_ptdetail where '" + ids + "' like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(goods_Id, '$%')) ";
      } 
      base.executeUpdate(tmpSql);
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "DELETE FROM st_goodsstock where '" + ids + "' like concat('%$', goods_Id, '$%') ";
      } else {
        tmpSql = "DELETE FROM st_goodsstock where '" + ids + "' like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(goods_Id, '$%')) ";
      } 
      base.executeUpdate(tmpSql);
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "DELETE FROM st_ssdetail where '" + ids + "' like concat('%$', goods_Id, '$%') ";
      } else {
        tmpSql = "DELETE FROM st_ssdetail where '" + ids + "' like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(goods_Id, '$%')) ";
      } 
      base.executeUpdate(tmpSql);
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "DELETE FROM st_stockchange where '" + ids + "' like concat('%$', goods_Id, '$%') ";
      } else {
        tmpSql = "DELETE FROM st_stockchange where '" + ids + "' like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(goods_Id, '$%')) ";
      } 
      base.executeUpdate(tmpSql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public Map ImportGoods(HttpServletRequest httpServletRequest) throws FileNotFoundException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String message = "";
    String succeed = "0";
    HttpSession session = httpServletRequest.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowStr = dateFormat.format(new Date());
    String realPath = httpServletRequest.getRealPath("/uploadtemplate/goods.xls");
    FileInputStream file = new FileInputStream(new File(realPath));
    try {
      this.workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      message = String.valueOf(message) + "选择的模版不正确！<br>";
      if (!"1".equals(succeed))
        succeed = "1"; 
      map.put("succeed", succeed);
      map.put("message", message);
      return map;
    } 
    this.sheet = this.workbook.getSheet(0);
    if (this.sheet != null) {
      int rows = this.sheet.getRows();
      int columns = 9;
      try {
        String duiYinWiZhi = this.sheet.getCell(0, 1).getContents().trim();
        String duiYinWiZhiValue = this.sheet.getCell(1, 1).getContents().trim();
        String databaseType = SystemCommon.getDatabaseType();
        if ("对应字段所在列".equals(duiYinWiZhi) && 
          duiYinWiZhiValue.matches("^[0-9]*[1-9][0-9]*$")) {
          if (!"1".equals(succeed)) {
            DataSourceBase dataSourceBase = new DataSourceBase();
            for (int i = 4; i < rows; i++) {
              StringBuffer insertParamSql = new StringBuffer("insert into  st_goods(");
              if ("mysql".equals(databaseType)) {
                insertParamSql.append("GOODS_NAME,SPECS,GOODS_UNIT,PRICE,MAXAMOUNT,MINAMOUNT,REMARK,GOODS_ID,STOCK_ID,GOODSTYPE_ID,CREATEDEMP,CREATEDORG,DOMAIN_ID,NUM,goods_status ");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(");
              } else {
                insertParamSql.append("GOODS_NAME,SPECS,GOODS_UNIT,PRICE,MAXAMOUNT,MINAMOUNT,REMARK,GOODS_ID,STOCK_ID,GOODSTYPE_ID,CREATEDEMP,CREATEDORG,DOMAIN_ID,NUM,goods_status ");
                insertParamSql.append(")");
                insertParamSql.append("VALUES(");
              } 
              String ssk = "";
              String wplb = "";
              String GOODS_NAME = "";
              String STOCK_NAME = "";
              String GOODS_ID = "";
              String STOCK_ID = "";
              String GOODSTYPE_ID = "";
              for (int k = 0; k < columns; k++) {
                if (i != Long.valueOf(duiYinWiZhiValue).longValue() - 1L) {
                  String textValue = this.sheet.getCell(k, i).getContents().trim();
                  if (k == 2)
                    GOODS_NAME = textValue; 
                  if (k == 0) {
                    ssk = textValue;
                  } else if (k == 1) {
                    wplb = textValue;
                  } else if (k == 5) {
                    if (textValue == null || "".equals(textValue))
                      textValue = "0"; 
                    insertParamSql.append("'").append(textValue).append("',");
                  } else {
                    insertParamSql.append("'").append(textValue).append("',");
                  } 
                } 
              } 
              String stockSql = "select STOCK_ID,STOCK_NAME from st_stock where STOCK_NAME='" + ssk + "'";
              STOCK_ID = selectByCode(stockSql);
              if ("".equals(ssk) || ssk == null || "".equals(STOCK_ID)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行仓库名称为空或不存在该仓库!!!</br>";
              } else {
                String goodTypeSql = "select GOODSTYPE_ID,STOCK_ID from st_goodstype where STOCK_ID=" + STOCK_ID + " and GOODSTYPE_NAME='" + wplb + "' ";
                GOODSTYPE_ID = selectByCode(goodTypeSql);
                if ("".equals(GOODSTYPE_ID) || "".equals(wplb))
                  message = String.valueOf(message) + "第" + (i + 1) + "行类别为空或不存在该类别!!!</br>"; 
              } 
              if (!"".equals(GOODSTYPE_ID) && GOODSTYPE_ID != null) {
                String numSql = "select max(aaa.num) from st_goods aaa,st_goodstype bbb where aaa.GOODSTYPE_ID=bbb.GOODSTYPE_ID and bbb.GOODSTYPE_ID=" + GOODSTYPE_ID;
                int num = 0;
                String numString = selectByCode(numSql);
                if (!"".equals(numString))
                  num = Integer.parseInt(numString); 
                num++;
                String prefix = "WP";
                String prefixSql = "select goodstype_prefix,GOODSTYPE_ID from st_goodstype  where GOODSTYPE_ID=" + GOODSTYPE_ID;
                prefix = selectByCode(prefixSql);
                if (num < 10) {
                  GOODS_ID = "0_" + prefix + "000" + num;
                } else if (num < 100) {
                  GOODS_ID = "0_" + prefix + "00" + num;
                } else if (num < 1000) {
                  GOODS_ID = "0_" + prefix + "0" + num;
                } else {
                  GOODS_ID = "0_" + prefix + num;
                } 
                insertParamSql.append("'").append(GOODS_ID).append("',");
                insertParamSql.append("").append(STOCK_ID).append(",");
                insertParamSql.append("").append(GOODSTYPE_ID).append(",");
                insertParamSql.append("").append(userId).append(",");
                insertParamSql.append("").append(orgId).append(",");
                insertParamSql.append("").append("0").append(",");
                insertParamSql.append("").append(num).append(",");
                insertParamSql.append("").append("0").append(",");
                String insertParamSqlString = insertParamSql.toString();
                if (insertParamSqlString.endsWith(","))
                  insertParamSqlString = String.valueOf(insertParamSqlString.substring(0, insertParamSqlString.length() - 1)) + ")"; 
                String sqlString = "select GOODS_NAME,STOCK_ID from st_goods where GOODS_NAME='" + GOODS_NAME + "' and  GOODSTYPE_ID=" + GOODSTYPE_ID + " and STOCK_ID=" + STOCK_ID;
                boolean falseOrTrue = selectISTrue(sqlString);
                if (falseOrTrue) {
                  updateByDutySql(insertParamSqlString.toString());
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行物品已存在</br>";
                } 
              } 
            } 
          } 
        } else {
          if (!"1".equals(succeed))
            succeed = "1"; 
          message = String.valueOf(message) + "选择的模版不正确！<br>";
        } 
      } catch (Exception ex) {
        succeed = "1";
        message = String.valueOf(message) + "导入数据对应不正确！<br>";
        ex.printStackTrace();
      } 
    } 
    map.put("succeed", succeed);
    map.put("message", message);
    return map;
  }
  
  public boolean updateByDutySql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public String selectByCode(String sql) throws Exception {
    String result = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rSet = stmt.executeQuery(sql);
      while (rSet.next()) {
        if (sql.contains("max(aaa.num)")) {
          result = (new StringBuilder(String.valueOf(rSet.getInt(1)))).toString();
          continue;
        } 
        result = rSet.getString(1);
      } 
      rSet.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean selectISTrue(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rSet = stmt.executeQuery(sql);
      String dataCodeString = "";
      while (rSet.next())
        dataCodeString = rSet.getString(1); 
      if ("".equals(dataCodeString) || dataCodeString == null) {
        result = true;
      } else {
        result = false;
      } 
      rSet.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
}
