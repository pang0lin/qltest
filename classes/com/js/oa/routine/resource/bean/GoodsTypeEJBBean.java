package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.GoodsPO;
import com.js.oa.routine.resource.po.GoodsStockPO;
import com.js.oa.routine.resource.po.GoodsTypePO;
import com.js.oa.routine.resource.po.StockPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class GoodsTypeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(GoodsTypePO goodsTypePO, String stockId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      Query query = this.session.createQuery("select SPO.id from com.js.oa.routine.resource.po.StockPO SPO join SPO.goodsType GYPO where (SPO.id = " + 
          stockId.replaceAll(",", 
            " or SPO.id = ") + ") and GYPO.name='" + 
          goodsTypePO.getName() + 
          "' and GYPO.domainid=" + 
          goodsTypePO.getDomainid());
      Iterator itor = query.iterate();
      Query query1 = this.session.createQuery(
          "select po from com.js.oa.routine.resource.po.GoodsTypePO po  where po.goodsTypePrefix='" + 
          
          goodsTypePO.getGoodsTypePrefix() + "' and po.domainid=" + 
          goodsTypePO.getDomainid());
      Iterator itor1 = query1.iterate();
      if (itor.hasNext() || itor1.hasNext()) {
        success = Boolean.FALSE;
      } else {
        String[] stockIdString = stockId.split(",");
        String curId = goodsTypePO.getParentid().toString();
        if (curId.equals("0")) {
          HashSet<StockPO> stockSet = new HashSet();
          for (int i = 0; i < stockIdString.length; i++) {
            StockPO stockPO = (StockPO)this.session.load(StockPO.class, 
                new Long(stockIdString[i]));
            stockSet.add(stockPO);
          } 
          goodsTypePO.setStock(stockSet);
          goodsTypePO.setStockId(Long.valueOf(stockId));
          goodsTypePO.setParentname("");
          this.session.save(goodsTypePO);
        } else {
          Query testGoodsTypeExistQuery = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.GoodsTypePO aaa where aaa.name ='" + 
              goodsTypePO.getName() + "' and aaa.parentid = " + 
              curId + " and aaa.domainid=" + 
              goodsTypePO.getDomainid());
          List<E> list = testGoodsTypeExistQuery.list();
          if (list.size() > 0) {
            GoodsTypePO po = (GoodsTypePO)this.session.load(GoodsTypePO.class, 
                new Long(list.get(0).toString()));
            po.setRemark(goodsTypePO.getRemark());
            Set<StockPO> stockSet = po.getStock();
            for (int i = 0; i < stockIdString.length; i++) {
              StockPO stockPO = (StockPO)this.session.load(StockPO.class, 
                  new Long(stockIdString[i]));
              stockSet.add(stockPO);
            } 
            po.setStock(stockSet);
          } else {
            Query parentNameQuery = this.session.createQuery("select aaa.name from com.js.oa.routine.resource.po.GoodsTypePO aaa where aaa.id=" + 
                curId + " and aaa.domainid=" + 
                goodsTypePO.getDomainid());
            List<E> list1 = parentNameQuery.list();
            String pName = list1.get(0).toString();
            goodsTypePO.setParentname(pName);
            HashSet<StockPO> stockSet = new HashSet();
            for (int i = 0; i < stockIdString.length; i++) {
              StockPO stockPO = (StockPO)this.session.load(StockPO.class, 
                  new Long(stockIdString[i]));
              stockSet.add(stockPO);
            } 
            goodsTypePO.setStock(stockSet);
            this.session.save(goodsTypePO);
          } 
        } 
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public String[] getSingleGoodsType(String goodsTypeId) throws Exception {
    begin();
    String[] goodsType = { "", "", "", "", "", "" };
    try {
      Query query = this.session.createQuery("select aaa.name,aaa.remark,bbb.id,aaa.parentid,aaa.goodsTypePrefix from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where aaa.id=" + 
          
          goodsTypeId);
      Iterator<Object[]> itor = query.iterate();
      if (itor.hasNext()) {
        Object[] obj = itor.next();
        goodsType[0] = obj[0].toString();
        goodsType[1] = (obj[1] == null) ? " " : obj[1].toString();
        goodsType[2] = obj[2].toString();
        goodsType[3] = obj[3].toString();
        goodsType[5] = (obj[4] == null) ? " " : obj[4].toString();
      } 
      Query orgIdQuery = this.session.createQuery("select count(*) from com.js.oa.routine.resource.po.GoodsTypePO aaa where aaa.parentid=" + 
          goodsTypeId);
      List<E> list = orgIdQuery.list();
      for (int i = 0; i < list.size(); i++)
        goodsType[4] = list.get(i).toString(); 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return goodsType;
  }
  
  public Boolean update(GoodsTypePO goodsTypePO, String stockId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      String[] temp = stockId.split(",");
      String newStockId = temp[0];
      String oldStockId = temp[1];
      Query query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where   aaa.name='" + 
          
          goodsTypePO.getName() + 
          "' and aaa.id<>" + 
          goodsTypePO.getId().toString() + 
          " and bbb.id=" + newStockId);
      Iterator itor = query.iterate();
      Query query1 = this.session.createQuery(
          "select po from com.js.oa.routine.resource.po.GoodsTypePO po  where po.goodsTypePrefix='" + 
          
          goodsTypePO.getGoodsTypePrefix() + "' and po.domainid=" + 
          goodsTypePO.getDomainid() + " and po.id<>" + 
          goodsTypePO.getId().toString());
      Iterator itor1 = query1.iterate();
      if (itor.hasNext() || itor1.hasNext()) {
        success = Boolean.FALSE;
      } else {
        GoodsTypePO needModifyPO = (GoodsTypePO)this.session.load(
            GoodsTypePO.class, goodsTypePO.getId());
        needModifyPO.setName(goodsTypePO.getName());
        needModifyPO.setRemark(goodsTypePO.getRemark());
        needModifyPO.setParentid(goodsTypePO.getParentid());
        needModifyPO.setGoodsTypePrefix(goodsTypePO.getGoodsTypePrefix());
        needModifyPO.setStockId(Long.valueOf(newStockId));
        if (!newStockId.equals(oldStockId)) {
          StockPO stockPO = (StockPO)this.session.load(StockPO.class, 
              new Long(newStockId));
          Object[] StockObj = needModifyPO.getStock().toArray();
          Set<Object> stockSet = new HashSet();
          for (int j = 0; j < StockObj.length; j++) {
            if (!((StockPO)StockObj[j]).getId().toString().equals(
                oldStockId))
              stockSet.add(StockObj[j]); 
          } 
          stockSet.add(stockPO);
          needModifyPO.setStock(stockSet);
        } 
        String curId = goodsTypePO.getParentid().toString();
        if (curId.equals("0")) {
          needModifyPO.setParentname("");
        } else {
          Query parentNameQuery = this.session.createQuery("select aaa.name from com.js.oa.routine.resource.po.GoodsTypePO aaa where aaa.id=" + 
              curId);
          List<E> list1 = parentNameQuery.list();
          String pName = list1.get(0).toString();
          needModifyPO.setParentname(pName);
        } 
        Query orgIdQuery = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.GoodsTypePO aaa where aaa.parentid=" + 
            goodsTypePO.getId());
        List<E> list = orgIdQuery.list();
        for (int i = 0; i < list.size(); i++) {
          String cutid = list.get(i).toString();
          GoodsTypePO needModifyPO11 = (GoodsTypePO)this.session.load(
              GoodsTypePO.class, new Long(cutid));
          needModifyPO11.setParentname(goodsTypePO.getName());
        } 
        StringBuffer goodsBuffer = new StringBuffer();
        goodsBuffer.append("select goodsPO.id from com.js.oa.routine.resource.po.GoodsPO goodsPO join goodsPO.goodsType goodsTypePO where ");
        goodsBuffer.append("goodsTypePO.id =");
        goodsBuffer.append(goodsTypePO.getId());
        goodsBuffer.append(" and goodsPO.stockId=");
        goodsBuffer.append(oldStockId);
        Iterator<String> goodsItor = this.session.createQuery(goodsBuffer.toString())
          .iterate();
        StringBuffer goodsIdBuffer = new StringBuffer();
        goodsIdBuffer.append("select gsPO from com.js.oa.routine.resource.po.GoodsStockPO gsPO where gsPO.goodsId in(");
        while (goodsItor.hasNext()) {
          String goodsId = goodsItor.next();
          goodsIdBuffer.append("'");
          goodsIdBuffer.append(goodsId);
          goodsIdBuffer.append("'");
          goodsIdBuffer.append(",");
          GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, 
              goodsId);
          goodsPO.setStockId(newStockId);
          this.session.update(goodsPO);
        } 
        goodsIdBuffer.append("'3.1415926')");
        Iterator<GoodsStockPO> goodsStockItor = this.session.createQuery(goodsIdBuffer
            .toString()).iterate();
        while (goodsStockItor.hasNext()) {
          GoodsStockPO gsPO = goodsStockItor.next();
          gsPO.setStockId(new Long(newStockId));
          this.session.update(gsPO);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean delete(String goodsTypeId) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      String[] temp = goodsTypeId.split("#");
      String goodsTypeIdString = temp[0];
      String stockIdString = temp[1];
      String[] g = goodsTypeIdString.split(",");
      String[] s = stockIdString.split(",");
      StringBuffer where = new StringBuffer();
      for (int t = 0; t < g.length; t++) {
        where.append("((bbb.id = ");
        where.append(g[t]);
        where.append(" or bbb.parentid = ");
        where.append(g[t]);
        where.append(") and ccc.id = ");
        where.append(s[t]);
        where.append(") or ");
      } 
      String deleteWhere = where.toString();
      deleteWhere = deleteWhere.substring(0, deleteWhere.lastIndexOf("or"));
      Query query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb join bbb.stock ccc  where " + 
          deleteWhere);
      Iterator itor = query.iterate();
      if (itor.hasNext()) {
        success = Boolean.FALSE;
      } else {
        Connection con = (new DataSourceBase()).getDataSource().getConnection();
        ResultSet rs = null;
        Statement stmt = con.createStatement();
        try {
          StringBuffer where1 = new StringBuffer();
          for (int i = 0; i < g.length; i++) {
            where1.append("(GOODSTYPE_ID = ");
            where1.append(g[i]);
            where1.append(" and STOCK_ID = ");
            where1.append(s[i]);
            where1.append(") or ");
          } 
          String w = where1.toString();
          w = w.substring(0, w.lastIndexOf("or"));
          stmt.execute("delete from ST_STOCK_GOODSTYPE where " + 
              w);
          StringBuffer select = new StringBuffer();
          select.append("select b.GOODSTYPE_ID from(select a.GOODSTYPE_ID from ST_GOODSTYPE a where a.GOODSTYPE_ID in(");
          select.append(goodsTypeIdString);
          select.append(") or a.PARENTID in(");
          select.append(goodsTypeIdString);
          select.append(")) b where b.GOODSTYPE_ID not in(select c.GOODSTYPE_ID from ST_STOCK_GOODSTYPE c)");
          rs = stmt.executeQuery(select.toString());
          StringBuffer lastWhere = new StringBuffer();
          while (rs.next()) {
            lastWhere.append(rs.getString(1));
            lastWhere.append(",");
          } 
          if (lastWhere.length() > 0) {
            String deleteGoodsTypeId = lastWhere.toString();
            deleteGoodsTypeId = deleteGoodsTypeId.substring(0, 
                deleteGoodsTypeId.lastIndexOf(","));
            stmt.execute(
                "delete from ST_GOODSTYPE where GOODSTYPE_ID in(" + 
                deleteGoodsTypeId + ")");
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          stmt.close();
          con.close();
        } 
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println(
          "----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "----------------------------------------------");
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
          "select aaa.id from com.js.oa.routine.resource.po.GoodsTypePO aaa where " + 
          where);
      Iterator<E> itor = query.iterate();
      while (itor.hasNext())
        ids.append("$" + itor.next().toString() + "$"); 
    } catch (Exception e) {
      System.out.println(
          "----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ids.toString();
  }
  
  public List getUserManaGoodsType(String userId) throws Exception {
    ArrayList goodsTypeList = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.name from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where bbb.stockPut like '%" + 
          
          userId + 
          "%' order by aaa.id desc");
      goodsTypeList = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println(
          "----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return goodsTypeList;
  }
  
  public List getUserManaGoodsTypeSub(String userId, String flag) throws Exception {
    ArrayList goodsTypeList = new ArrayList();
    begin();
    try {
      String sql = "select aaa.id,aaa.name,aaa.parentname from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where bbb.stockPut like '%" + 
        
        userId + 
        "%' order by bbb.id,aaa.parentid";
      if (flag.equals("2"))
        sql = "select aaa.id,aaa.name,aaa.parentname from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where bbb.stockPut like '%" + 
          userId + 
          "%' and aaa.parentid>0 order by bbb.id,aaa.parentid"; 
      Query query = this.session.createQuery(sql);
      goodsTypeList = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println(
          "----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return goodsTypeList;
  }
  
  public List getUserManaGoodsTypeParent(String userId) throws Exception {
    List goodsTypeList = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.name,bbb.id from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where bbb.stockPut like '%" + 
          
          userId + 
          "%' and aaa.parentid=0 order by aaa.id desc");
      goodsTypeList = query.list();
    } catch (Exception e) {
      System.out.println(
          "----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return goodsTypeList;
  }
}
