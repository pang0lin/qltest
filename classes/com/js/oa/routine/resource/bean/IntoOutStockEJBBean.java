package com.js.oa.routine.resource.bean;

import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.routine.resource.po.CsDetailPO;
import com.js.oa.routine.resource.po.CsMasterPO;
import com.js.oa.routine.resource.po.GoodsPO;
import com.js.oa.routine.resource.po.GoodsStockPO;
import com.js.oa.routine.resource.po.PtDetailPO;
import com.js.oa.routine.resource.po.PtMasterPO;
import com.js.oa.routine.resource.po.SsDetailPO;
import com.js.oa.routine.resource.po.SsMasterPO;
import com.js.oa.routine.resource.po.StockChangePO;
import com.js.oa.routine.resource.po.StockPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class IntoOutStockEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map saveIntoStock(PtMasterPO ptMasterPO, Object[] detail) throws Exception {
    Map<Object, Object> success = new HashMap<Object, Object>();
    begin();
    try {
      Iterator<E> iter = this.session.iterate(
          "select max(po.num) from com.js.oa.routine.resource.po.PtMasterPO po");
      int num = 0;
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null && !obj.toString().equals("") && 
          !obj.toString().equals("null"))
          num = Integer.parseInt(obj.toString()); 
      } 
      num++;
      ptMasterPO.setNum(new Integer(num));
      String title = "RKD";
      if (ptMasterPO.getPtMode().equals("4"))
        title = "THD"; 
      if (num < 10) {
        ptMasterPO.setSerial(String.valueOf(title) + "000" + num);
      } else if (num < 100) {
        ptMasterPO.setSerial(String.valueOf(title) + "00" + num);
      } else if (num < 1000) {
        ptMasterPO.setSerial(String.valueOf(title) + "0" + num);
      } else {
        ptMasterPO.setSerial(String.valueOf(title) + num);
      } 
      ptMasterPO.setId(getId());
      this.session.save(ptMasterPO);
      String[] goodsId = (String[])detail[0];
      String[] amount = (String[])detail[1];
      String[] price = (String[])detail[2];
      String[] money = (String[])detail[3];
      String[] goodsName = (String[])detail[4];
      String[] goodsUnit = (String[])detail[5];
      String[] goodsSpecs = (String[])detail[6];
      String[] returnReason = (detail[7] == null) ? new String[goodsId.length] : (String[])detail[7];
      for (int i = 0; i < goodsId.length; i++) {
        PtDetailPO ptDetailPO = new PtDetailPO();
        if (!goodsId[i].startsWith("0_"))
          goodsId[i] = "0_" + goodsId[i]; 
        ptDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ptDetailPO.setAmount(new Float(amount[i]));
        ptDetailPO.setMcost(new Float(price[i]));
        ptDetailPO.setGoodsMoney(new Float(money[i]));
        ptDetailPO.setGoodsName(goodsName[i]);
        ptDetailPO.setGoodsUnit(goodsUnit[i]);
        ptDetailPO.setPtMaster(ptMasterPO);
        ptDetailPO.setSeq(new Integer(i + 1));
        ptDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ptDetailPO.setReturnReason(returnReason[i]);
        this.session.save(ptDetailPO);
      } 
      this.session.flush();
      iter = this.session.iterate(
          "select po.stockPut from com.js.oa.routine.resource.po.StockPO po where po.id=" + 
          ptMasterPO.getPtStock());
      String stockPut = "";
      if (iter.hasNext())
        stockPut = iter.next().toString(); 
      if (stockPut.indexOf("$") >= 0) {
        stockPut = stockPut.substring(1, stockPut.length() - 1);
        if (stockPut.indexOf("$") > 0) {
          stockPut = stockPut.replace('$', ',');
          stockPut = stockPut.replaceAll(",,", ",");
        } 
        iter = this.session.iterate("select po.userAccounts from com.js.system.vo.usermanager.EmployeeVO po where po.empId in (" + 
            stockPut + ")");
        String tmp = "";
        while (iter.hasNext())
          tmp = String.valueOf(tmp) + iter.next() + ","; 
        if (tmp.endsWith(","))
          tmp = tmp.substring(0, tmp.length() - 1); 
        success.put("stockPut", tmp);
      } 
      success.put("serial", ptMasterPO.getSerial());
      success.put("ptMan", ptMasterPO.getPtMan());
      success.put("success", Boolean.TRUE);
      success.put("id", ptMasterPO.getId());
    } catch (Exception e) {
      success.put("success", Boolean.FALSE);
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
  
  public Map getIntoStock(String ptMasterId) throws Exception {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    begin();
    try {
      Query query = this.session.createQuery("select aaa,bbb.stockName,ccc.empName from com.js.oa.routine.resource.po.PtMasterPO aaa,com.js.oa.routine.resource.po.StockPO bbb, com.js.system.vo.usermanager.EmployeeVO ccc where aaa.ptStock=bbb.id and aaa.makeMan=ccc.empId and aaa.id=" + 

          
          ptMasterId);
      Iterator<Object[]> iter = query.iterate();
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        hashMap.put("ptMasterPO", obj[0]);
        hashMap.put("stockName", obj[1]);
        hashMap.put("makeManName", obj[2]);
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
    return hashMap;
  }
  
  public Boolean updateIntoStock(PtMasterPO ptMasterPO, Object[] ptDetail) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      PtMasterPO needModifyPO = (PtMasterPO)this.session.load(PtMasterPO.class, 
          ptMasterPO.getId());
      needModifyPO.setPtDate(ptMasterPO.getPtDate());
      needModifyPO.setPtSupp(ptMasterPO.getPtSupp());
      needModifyPO.setPtMan(ptMasterPO.getPtMan());
      needModifyPO.setPtMoney(ptMasterPO.getPtMoney());
      needModifyPO.setRemark(ptMasterPO.getRemark());
      boolean isCheck = false;
      if (ptMasterPO.getCheckFlag().equals("Y") && 
        needModifyPO.getCheckFlag().equals("N"))
        isCheck = true; 
      Long stockId = needModifyPO.getPtStock();
      if (isCheck) {
        needModifyPO.setCheckFlag("Y");
        needModifyPO.setCheckMan(ptMasterPO.getCheckMan());
        needModifyPO.setCheckManName(ptMasterPO.getCheckManName());
        needModifyPO.setCheckDate(ptMasterPO.getCheckDate());
      } 
      needModifyPO.setPtOrg(ptMasterPO.getPtOrg());
      needModifyPO.setPtOrgName(ptMasterPO.getPtOrgName());
      needModifyPO.setPtTypeDefine(ptMasterPO.getPtTypeDefine());
      needModifyPO.setPtMode(ptMasterPO.getPtMode());
      needModifyPO.setPtHandleName(ptMasterPO.getPtHandleName());
      needModifyPO.setInvoiceNO(ptMasterPO.getInvoiceNO());
      Iterator<PtDetailPO> iter = needModifyPO.getPtDetail().iterator();
      needModifyPO.setPtDetail(null);
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      String[] goodsId = (String[])ptDetail[0];
      String[] amount = (String[])ptDetail[1];
      String[] price = (String[])ptDetail[2];
      String[] money = (String[])ptDetail[3];
      String[] goodsName = (String[])ptDetail[4];
      String[] goodsUnit = (String[])ptDetail[5];
      String[] goodsSpecs = (String[])ptDetail[6];
      String[] returnReason = (ptDetail[7] == null) ? new String[goodsId.length] : (String[])ptDetail[7];
      for (int i = 0; i < goodsId.length; i++) {
        PtDetailPO ptDetailPO = new PtDetailPO();
        ptDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ptDetailPO.setAmount(new Float(amount[i]));
        ptDetailPO.setMcost(new Float(price[i]));
        ptDetailPO.setGoodsMoney(new Float(money[i]));
        ptDetailPO.setGoodsName(goodsName[i]);
        ptDetailPO.setGoodsUnit(goodsUnit[i]);
        ptDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ptDetailPO.setReturnReason(returnReason[i]);
        ptDetailPO.setPtMaster(needModifyPO);
        this.session.save(ptDetailPO);
        if (isCheck) {
          StockChangePO stockChangePO = new StockChangePO();
          stockChangePO.setScNo(ptMasterPO.getId().toString());
          stockChangePO.setScDate(ptMasterPO.getPtDate());
          stockChangePO.setScSeq(new Integer(i));
          stockChangePO.setChangeType("APT");
          stockChangePO.setChangeStock(stockId);
          stockChangePO.setDrawDept(null);
          stockChangePO.setGoodsId(goodsId[i]);
          stockChangePO.setChangeAmount(new Float(amount[i]));
          stockChangePO.setChangeMoney(new Float(price[i]));
          stockChangePO.setFactMoney(new Float(money[i]));
          this.session.save(stockChangePO);
          float allPrice = (new Float(money[i])).floatValue();
          float allAmount = 0.0F;
          float oldAmount = 0.0F;
          float averagePrice = 0.0F;
          Query query = this.session.createQuery(
              "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
              goodsId[i] + 
              "' and aaa.stockId=" + stockId);
          iter = query.iterate();
          if (iter.hasNext()) {
            GoodsStockPO goodsStockPO = (GoodsStockPO)iter.next();
            oldAmount = goodsStockPO.getAmount().floatValue();
            goodsStockPO.setAmount(new Float(goodsStockPO.getAmount().floatValue() + Float.parseFloat(amount[i])));
            goodsStockPO.setStockMoney(new Float(goodsStockPO.getStockMoney().floatValue() + Float.parseFloat(money[i])));
            if (goodsStockPO.getAmount().floatValue() != 0.0F)
              averagePrice = goodsStockPO.getStockMoney().floatValue() / goodsStockPO.getAmount().floatValue(); 
          } else {
            GoodsStockPO goodsStockPO = new GoodsStockPO();
            goodsStockPO.setGoodsId(goodsId[i]);
            goodsStockPO.setStockId(stockId);
            goodsStockPO.setAmount(new Float(amount[i]));
            goodsStockPO.setStockMoney(new Float(money[i]));
            averagePrice = Float.parseFloat(money[i]) / Float.parseFloat(amount[i]);
            this.session.save(goodsStockPO);
          } 
          if (averagePrice != 0.0F) {
            GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, 
                goodsId[i]);
            goodsPO.setAveragePrice(new Float(Math.abs(averagePrice)));
          } 
        } 
      } 
      this.session.flush();
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
  
  public Boolean unCheckIntoStock(String ptMasterId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      PtMasterPO ptMasterPO = (PtMasterPO)this.session.load(PtMasterPO.class, 
          new Long(ptMasterId));
      ptMasterPO.setCheckFlag("N");
      Set ptDetailSet = ptMasterPO.getPtDetail();
      Iterator<PtDetailPO> iter = ptDetailSet.iterator();
      PtDetailPO ptDetailPO = null;
      GoodsStockPO goodsStockPO = null;
      Query query = null;
      Iterator<GoodsStockPO> iter2 = null;
      while (iter.hasNext()) {
        ptDetailPO = iter.next();
        this.session.delete(
            "from com.js.oa.routine.resource.po.StockChangePO aaa where aaa.scNo=" + 
            ptMasterPO.getId() + " and aaa.changeType='APT'");
        query = this.session.createQuery(
            "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
            ptDetailPO.getGoodsId() + 
            "' and aaa.stockId=" + 
            ptMasterPO.getPtStock());
        iter2 = query.iterate();
        if (iter2.hasNext()) {
          goodsStockPO = iter2.next();
          goodsStockPO.setAmount(new Float(goodsStockPO.getAmount()
                .floatValue() - 
                ptDetailPO.getAmount().floatValue()));
        } 
      } 
      this.session.flush();
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
  
  public Boolean deleteIntoStock(String ids) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      String[] id = { "" };
      if (ids.indexOf(",") > 0) {
        id = ids.split(",");
      } else {
        id[0] = ids;
      } 
      PtMasterPO ptMasterPO = null;
      for (int i = 0; i < id.length; i++) {
        ptMasterPO = (PtMasterPO)this.session.load(PtMasterPO.class, 
            new Long(id[i]));
        Iterator<PtDetailPO> iter = ptMasterPO.getPtDetail().iterator();
        ptMasterPO.setPtDetail(null);
        while (iter.hasNext())
          this.session.delete(iter.next()); 
        this.session.delete(ptMasterPO);
      } 
      this.session.flush();
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
  
  public Long saveOutStock(SsMasterPO ssMasterPO, Object[] detail) throws Exception {
    Boolean success = new Boolean(true);
    DataSourceBase base = new DataSourceBase();
    begin();
    Long id = null;
    try {
      Iterator iter = this.session.iterate(
          "select max(po.num) from com.js.oa.routine.resource.po.SsMasterPO po");
      int num = 0;
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null && !obj.toString().equals("") && 
          !obj.toString().equals("null"))
          num = Integer.parseInt(obj.toString()); 
      } 
      num++;
      ssMasterPO.setNum(new Integer(num));
      ssMasterPO.setNum(new Integer(num));
      String title = "CKD";
      if (ssMasterPO.getSsMode().equals("3"))
        title = "TKD"; 
      if (num < 10) {
        ssMasterPO.setSerial(String.valueOf(title) + "000" + num);
      } else if (num < 100) {
        ssMasterPO.setSerial(String.valueOf(title) + "00" + num);
      } else if (num < 1000) {
        ssMasterPO.setSerial(String.valueOf(title) + "0" + num);
      } else {
        ssMasterPO.setSerial(String.valueOf(title) + num);
      } 
      ssMasterPO.setId(getId());
      id = (Long)this.session.save(ssMasterPO);
      String[] goodsId = (String[])detail[0];
      String[] amount = (String[])detail[1];
      String[] price = (String[])detail[2];
      String[] money = (String[])detail[3];
      String[] goodsName = (String[])detail[4];
      String[] goodsUnit = (String[])detail[5];
      String[] goodsSpecs = (String[])detail[6];
      String[] returnReason = (detail[7] == null) ? new String[goodsId.length] : (String[])detail[7];
      String[] stockIdString = (String[])detail[8];
      for (int i = 0; i < goodsId.length; i++) {
        SsDetailPO ssDetailPO = new SsDetailPO();
        ssDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ssDetailPO.setAmount(new Float(amount[i]));
        ssDetailPO.setPrice(new Float(price[i]));
        ssDetailPO.setGoodsMoney(new Float(money[i]));
        ssDetailPO.setGoodsName(goodsName[i]);
        ssDetailPO.setGoodsUnit(goodsUnit[i]);
        ssDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ssDetailPO.setReturnReason(returnReason[i]);
        ssDetailPO.setSsMaster(ssMasterPO);
        String idStr = goodsId[i];
        if (!idStr.startsWith("0_"))
          idStr = "0_" + idStr; 
        GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, idStr);
        if (goodsPO.getAveragePrice() == null)
          goodsPO.setAveragePrice(Float.valueOf(Float.parseFloat("0.0"))); 
        ssDetailPO.setFactPrice(goodsPO.getAveragePrice());
        ssDetailPO.setFactMoney(new Float(Float.parseFloat(amount[i]) * goodsPO.getAveragePrice().floatValue()));
        if (!ssDetailPO.getGoodsId().startsWith("0_"))
          ssDetailPO.setGoodsId("0_" + ssDetailPO.getGoodsId()); 
        this.session.save(ssDetailPO);
        this.session.flush();
        float kc = (new Float(getGoodsAmoutNoSession(idStr, stockIdString[0]))).floatValue() - (new Float(amount[i])).floatValue();
        float stockmoney = (new Float(getGoodsStockmoneyNoSession(goodsId[i], stockIdString[0]))).floatValue() - (new Float(money[i])).floatValue();
        String sqlString = "update ST_GOODSSTOCK set STOCK_AMOUNT=" + kc + ",stockmoney=" + stockmoney + "  where GOODS_ID='" + idStr + "' and STOCK_ID='" + stockIdString[0] + "'";
        String sqlIskucunyj = "\tSELECT ISKUCUNYJ FROM ST_STOCK WHERE STOCK_ID='" + stockIdString[0] + "' ";
        base.begin();
        int iskucunYj = 0;
        ResultSet rSet = base.executeQuery(sqlIskucunyj);
        while (rSet.next())
          iskucunYj = rSet.getInt(1); 
        if (1 == iskucunYj)
          base.executeUpdate(sqlString); 
        base.end();
      } 
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      base.end();
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session = null;
      this.transaction = null;
    } 
    return id;
  }
  
  public Long saveDirectOutStock(SsMasterPO ssMasterPO, Object[] detail) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    Long id = null;
    try {
      Iterator<GoodsStockPO> iter = this.session.iterate(
          "select max(po.num) from com.js.oa.routine.resource.po.SsMasterPO po");
      int num = 0;
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null && !obj.toString().equals("") && 
          !obj.toString().equalsIgnoreCase("null"))
          num = Integer.parseInt(obj.toString()); 
      } 
      num++;
      ssMasterPO.setNum(new Integer(num));
      String title = "CKD";
      if (ssMasterPO.getSsMode().equals("3"))
        title = "TKD"; 
      if (num < 10) {
        ssMasterPO.setSerial(String.valueOf(title) + "000" + num);
      } else if (num < 100) {
        ssMasterPO.setSerial(String.valueOf(title) + "00" + num);
      } else if (num < 1000) {
        ssMasterPO.setSerial(String.valueOf(title) + "0" + num);
      } else {
        ssMasterPO.setSerial(String.valueOf(title) + num);
      } 
      ssMasterPO.setId(getId());
      id = (Long)this.session.save(ssMasterPO);
      String[] goodsId = (String[])detail[0];
      String[] amount = (String[])detail[1];
      String[] price = (String[])detail[2];
      String[] money = (String[])detail[3];
      String[] goodsName = (String[])detail[4];
      String[] goodsUnit = (String[])detail[5];
      String[] goodsSpecs = (String[])detail[6];
      String[] returnReason = (detail[7] == null) ? new String[goodsId.length] : (String[])detail[7];
      Long stockId = ssMasterPO.getSsStock();
      Query query = null;
      boolean isCheck = false;
      StockPO stockPO = (StockPO)this.session.load(StockPO.class, stockId);
      if (stockPO.getIsKucun().intValue() == 1)
        isCheck = true; 
      for (int i = 0; i < goodsId.length; i++) {
        GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, 
            goodsId[i]);
        SsDetailPO ssDetailPO = new SsDetailPO();
        ssDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ssDetailPO.setAmount(new Float(amount[i]));
        ssDetailPO.setPrice(new Float(price[i]));
        ssDetailPO.setGoodsMoney(new Float(money[i]));
        ssDetailPO.setGoodsName(goodsName[i]);
        ssDetailPO.setGoodsUnit(goodsUnit[i]);
        ssDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ssDetailPO.setReturnReason(returnReason[i]);
        ssDetailPO.setSsMaster(ssMasterPO);
        ssDetailPO.setFactPrice(goodsPO.getAveragePrice());
        ssDetailPO.setFactMoney(new Float(Float.parseFloat(amount[i]) * 
              goodsPO.getAveragePrice()
              .floatValue()));
        this.session.save(ssDetailPO);
        String isKucun = "0";
        String isKucunYj = "0";
        iter = this.session.createQuery("select po.isKucun from com.js.oa.routine.resource.po.StockPO po where po.id=" + stockId).iterate();
        if (iter.hasNext()) {
          Object obj = iter.next();
          if (obj != null)
            isKucun = obj.toString(); 
        } 
        iter = this.session.createQuery("select po.isKucunYj from com.js.oa.routine.resource.po.StockPO po where po.id=" + stockId).iterate();
        if (iter.hasNext()) {
          Object obj = iter.next();
          if (obj != null)
            isKucunYj = obj.toString(); 
        } 
        query = this.session.createQuery(
            "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
            goodsId[i] + 
            "' and aaa.stockId=" + stockId);
        iter = query.iterate();
        if (iter.hasNext()) {
          GoodsStockPO goodsStockPO = iter.next();
          if ("1".equals(isKucun) || "1".equals(isKucunYj)) {
            goodsStockPO.setAmount(new Float(goodsStockPO.getAmount()
                  .floatValue() - Float.parseFloat(amount[i])));
            goodsStockPO.setStockMoney(new Float(goodsStockPO
                  .getStockMoney().floatValue() - goodsPO
                  .getAveragePrice().floatValue() * 
                  Float.parseFloat(amount[i])));
          } 
        } else {
          GoodsStockPO goodsStockPO = new GoodsStockPO();
          goodsStockPO.setGoodsId(goodsId[i]);
          goodsStockPO.setStockId(stockId);
          if ("1".equals(isKucun) || "1".equals(isKucunYj)) {
            goodsStockPO.setAmount(new Float(Float.parseFloat("0") - 
                  Float.parseFloat(amount[i])));
            goodsStockPO.setStockMoney(new Float(Float.parseFloat(
                    "0") - goodsPO
                  .getAveragePrice().floatValue() * 
                  Float.parseFloat(amount[i])));
          } 
          this.session.save(goodsStockPO);
        } 
      } 
      this.session.flush();
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
    return id;
  }
  
  public Map getOutStock(String ssMasterId) throws Exception {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    begin();
    try {
      Query query = this.session.createQuery("select aaa,bbb.stockName,ccc.empName,ddd.orgName from com.js.oa.routine.resource.po.SsMasterPO aaa,com.js.oa.routine.resource.po.StockPO bbb, com.js.system.vo.usermanager.EmployeeVO ccc left join ccc.organizations ddd where aaa.ssStock=bbb.id and aaa.makeMan=ccc.empId and aaa.id=" + 

          
          ssMasterId);
      Iterator<Object[]> iter = query.iterate();
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        hashMap.put("ssMasterPO", obj[0]);
        hashMap.put("stockName", obj[1]);
        hashMap.put("makeManName", obj[2]);
        hashMap.put("ssDeptName", obj[3]);
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
    return hashMap;
  }
  
  public Boolean updateOutStock(SsMasterPO ssMasterPO, Object[] ssDetail) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      SsMasterPO needModifyPO = (SsMasterPO)this.session.load(SsMasterPO.class, 
          ssMasterPO.getId());
      needModifyPO.setSsDate(ssMasterPO.getSsDate());
      needModifyPO.setSsMan(ssMasterPO.getSsMan());
      needModifyPO.setSsMoney(ssMasterPO.getSsMoney());
      needModifyPO.setRemark(ssMasterPO.getRemark());
      needModifyPO.setSsOrg(ssMasterPO.getSsOrg());
      needModifyPO.setSsOrgName(ssMasterPO.getSsOrgName());
      needModifyPO.setSsTypeDefine(ssMasterPO.getSsTypeDefine());
      boolean isCheck = false;
      if (ssMasterPO.getCheckFlag().equals("Y") && 
        needModifyPO.getCheckFlag().equals("N"))
        isCheck = true; 
      Long stockId = needModifyPO.getSsStock();
      if (isCheck) {
        needModifyPO.setCheckFlag("Y");
        needModifyPO.setCheckMan(ssMasterPO.getCheckMan());
        needModifyPO.setCheckManName(ssMasterPO.getCheckManName());
        needModifyPO.setCheckDate(ssMasterPO.getCheckDate());
      } 
      Iterator<SsDetailPO> iter = needModifyPO.getSsDetail().iterator();
      needModifyPO.setSsDetail(null);
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      String[] goodsId = (String[])ssDetail[0];
      String[] amount = (String[])ssDetail[1];
      String[] price = (String[])ssDetail[2];
      String[] money = (String[])ssDetail[3];
      String[] goodsName = (String[])ssDetail[4];
      String[] goodsUnit = (String[])ssDetail[5];
      String[] goodsSpecs = (String[])ssDetail[6];
      String[] returnReason = (ssDetail[7] == null) ? new String[goodsId.length] : (String[])ssDetail[7];
      Query query = null;
      for (int i = 0; i < goodsId.length; i++) {
        GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, goodsId[i]);
        SsDetailPO ssDetailPO = new SsDetailPO();
        ssDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ssDetailPO.setAmount(new Float(amount[i]));
        ssDetailPO.setPrice(new Float(price[i]));
        ssDetailPO.setGoodsMoney(new Float(money[i]));
        ssDetailPO.setGoodsName(goodsName[i]);
        ssDetailPO.setGoodsUnit(goodsUnit[i]);
        ssDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ssDetailPO.setReturnReason(returnReason[i]);
        ssDetailPO.setSsMaster(needModifyPO);
        ssDetailPO.setFactPrice(goodsPO.getAveragePrice());
        ssDetailPO.setFactMoney(new Float(Float.parseFloat(amount[i]) * 
              goodsPO.getAveragePrice()
              .floatValue()));
        this.session.save(ssDetailPO);
      } 
      this.session.flush();
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
  
  public Boolean updateOutStockAmnout(SsMasterPO ssMasterPO, Object[] ssDetail) throws Exception {
    Boolean success = new Boolean(true);
    DataSourceBase base = new DataSourceBase();
    begin();
    try {
      SsMasterPO needModifyPO = (SsMasterPO)this.session.load(SsMasterPO.class, 
          ssMasterPO.getId());
      needModifyPO.setSsDate(ssMasterPO.getSsDate());
      needModifyPO.setSsMan(ssMasterPO.getSsMan());
      needModifyPO.setSsMoney(ssMasterPO.getSsMoney());
      needModifyPO.setRemark(ssMasterPO.getRemark());
      needModifyPO.setSsOrg(ssMasterPO.getSsOrg());
      needModifyPO.setSsOrgName(ssMasterPO.getSsOrgName());
      needModifyPO.setSsTypeDefine(ssMasterPO.getSsTypeDefine());
      boolean isCheck = false;
      if (ssMasterPO.getCheckFlag().equals("Y") && 
        needModifyPO.getCheckFlag().equals("N"))
        isCheck = true; 
      Long stockId = needModifyPO.getSsStock();
      if (isCheck) {
        needModifyPO.setCheckFlag("Y");
        needModifyPO.setCheckMan(ssMasterPO.getCheckMan());
        needModifyPO.setCheckManName(ssMasterPO.getCheckManName());
        needModifyPO.setCheckDate(ssMasterPO.getCheckDate());
      } 
      String[] goodsId = (String[])ssDetail[0];
      String[] amount = (String[])ssDetail[1];
      String[] price = (String[])ssDetail[2];
      String[] money = (String[])ssDetail[3];
      String[] goodsName = (String[])ssDetail[4];
      String[] goodsUnit = (String[])ssDetail[5];
      String[] goodsSpecs = (String[])ssDetail[6];
      String[] returnReason = (ssDetail[7] == null) ? new String[goodsId.length] : (String[])ssDetail[7];
      String[] stockIdString = (String[])ssDetail[8];
      Query query = null;
      for (int i = 0; i < goodsId.length; i++) {
        GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, 
            goodsId[i]);
        SsDetailPO ssDetailPO = new SsDetailPO();
        ssDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ssDetailPO.setAmount(new Float(amount[i]));
        ssDetailPO.setPrice(new Float(price[i]));
        ssDetailPO.setGoodsMoney(new Float(money[i]));
        ssDetailPO.setGoodsName(goodsName[i]);
        ssDetailPO.setGoodsUnit(goodsUnit[i]);
        ssDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ssDetailPO.setReturnReason(returnReason[i]);
        ssDetailPO.setSsMaster(needModifyPO);
        ssDetailPO.setFactPrice(goodsPO.getAveragePrice());
        ssDetailPO.setFactMoney(new Float(Float.parseFloat(amount[i]) * 
              goodsPO.getAveragePrice()
              .floatValue()));
        this.session.flush();
        float kc = (new Float(getGoodsAmoutNoSession(goodsId[i], stockIdString[0]))).floatValue() - (new Float(amount[i])).floatValue();
        float stockmoney = (new Float(getGoodsStockmoneyNoSession(goodsId[i], stockIdString[0]))).floatValue() - (new Float(money[i])).floatValue();
        String sqlString = "update ST_GOODSSTOCK set STOCK_AMOUNT=" + kc + " ,stockmoney=" + stockmoney + " where GOODS_ID='" + goodsId[i] + "' and STOCK_ID='" + stockIdString[0] + "'";
        String sqlIskucun = "\tSELECT ISKUCUNYJ,ISKUCUN FROM ST_STOCK WHERE STOCK_ID='" + stockIdString[0] + "' ";
        base.begin();
        int ISKUCUNYJ = 1;
        int ISCUKUN = 0;
        ResultSet rSet = base.executeQuery(sqlIskucun);
        while (rSet.next()) {
          ISKUCUNYJ = rSet.getInt(1);
          ISCUKUN = rSet.getInt(2);
        } 
        rSet.close();
        if (1 != ISKUCUNYJ)
          if (1 == ISCUKUN)
            base.executeUpdate(sqlString);  
        base.end();
      } 
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      base.end();
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean updateBackOutStockAmnout(SsMasterPO ssMasterPO, Object[] ssDetail) throws Exception {
    Boolean success = new Boolean(true);
    DataSourceBase base = new DataSourceBase();
    begin();
    try {
      SsMasterPO needModifyPO = (SsMasterPO)this.session.load(SsMasterPO.class, 
          ssMasterPO.getId());
      needModifyPO.setSsDate(ssMasterPO.getSsDate());
      needModifyPO.setSsMan(ssMasterPO.getSsMan());
      needModifyPO.setSsMoney(ssMasterPO.getSsMoney());
      needModifyPO.setRemark(ssMasterPO.getRemark());
      needModifyPO.setSsOrg(ssMasterPO.getSsOrg());
      needModifyPO.setSsOrgName(ssMasterPO.getSsOrgName());
      needModifyPO.setSsTypeDefine(ssMasterPO.getSsTypeDefine());
      boolean isCheck = false;
      if (ssMasterPO.getCheckFlag().equals("Y") && 
        needModifyPO.getCheckFlag().equals("N"))
        isCheck = true; 
      Long stockId = needModifyPO.getSsStock();
      if (isCheck) {
        needModifyPO.setCheckFlag("Y");
        needModifyPO.setCheckMan(ssMasterPO.getCheckMan());
        needModifyPO.setCheckManName(ssMasterPO.getCheckManName());
        needModifyPO.setCheckDate(ssMasterPO.getCheckDate());
      } 
      String[] goodsId = (String[])ssDetail[0];
      String[] amount = (String[])ssDetail[1];
      String[] price = (String[])ssDetail[2];
      String[] money = (String[])ssDetail[3];
      String[] goodsName = (String[])ssDetail[4];
      String[] goodsUnit = (String[])ssDetail[5];
      String[] goodsSpecs = (String[])ssDetail[6];
      String[] returnReason = (ssDetail[7] == null) ? new String[goodsId.length] : (String[])ssDetail[7];
      String[] stockIdString = (String[])ssDetail[8];
      Query query = null;
      for (int i = 0; i < goodsId.length; i++) {
        GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, 
            goodsId[i]);
        SsDetailPO ssDetailPO = new SsDetailPO();
        ssDetailPO.setGoodsId(goodsId[i]);
        this.session.flush();
        float kc = (new Float(getGoodsAmoutNoSession(goodsId[i], stockIdString[0]))).floatValue() + (new Float(amount[i])).floatValue();
        float stockmoney = (new Float(getGoodsStockmoneyNoSession(goodsId[i], stockIdString[0]))).floatValue() + (new Float(money[i])).floatValue();
        String sqlString = "update ST_GOODSSTOCK set STOCK_AMOUNT=" + kc + ",stockmoney=" + stockmoney + "  where GOODS_ID='" + goodsId[i] + "' and STOCK_ID='" + stockIdString[0] + "'";
        String sqlIskucun = "\tSELECT ISKUCUNYJ FROM ST_STOCK WHERE STOCK_ID='" + stockIdString[0] + "' ";
        base.begin();
        int iskucunYj = 0;
        ResultSet rSet = base.executeQuery(sqlIskucun);
        while (rSet.next())
          iskucunYj = rSet.getInt(1); 
        if (1 == iskucunYj)
          base.executeUpdate(sqlString); 
        base.end();
      } 
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      base.end();
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean uncheckOutStock(String ssMasterId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      SsMasterPO ssMasterPO = (SsMasterPO)this.session.load(SsMasterPO.class, 
          new Long(ssMasterId));
      ssMasterPO.setCheckFlag("N");
      Set ssDetailSet = ssMasterPO.getSsDetail();
      Iterator<SsDetailPO> iter = ssDetailSet.iterator();
      SsDetailPO ssDetailPO = null;
      GoodsStockPO goodsStockPO = null;
      Query query = null;
      Iterator<GoodsStockPO> iter2 = null;
      while (iter.hasNext()) {
        ssDetailPO = iter.next();
        this.session.delete(
            "from com.js.oa.routine.resource.po.StockChangePO aaa where aaa.scNo=" + 
            ssMasterPO.getId() + " and aaa.changeType='DSS'");
        query = this.session.createQuery(
            "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
            ssDetailPO.getGoodsId() + 
            "' and aaa.stockId=" + 
            ssMasterPO.getSsStock());
        iter2 = query.iterate();
        if (iter2.hasNext()) {
          goodsStockPO = iter2.next();
          goodsStockPO.setAmount(new Float(goodsStockPO.getAmount()
                .floatValue() + 
                ssDetailPO.getAmount().floatValue()));
        } 
      } 
      this.session.flush();
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
  
  public Boolean deleteOutStock(String ids, String tableId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      String[] id = { "" };
      if (ids.indexOf(",") > 0) {
        id = ids.split(",");
      } else {
        id[0] = ids;
      } 
      SsMasterPO ssMasterPO = null;
      for (int i = 0; i < id.length; i++) {
        ssMasterPO = (SsMasterPO)this.session.load(SsMasterPO.class, 
            new Long(id[i]));
        Iterator<SsDetailPO> iter = ssMasterPO.getSsDetail().iterator();
        ssMasterPO.setSsDetail(null);
        while (iter.hasNext())
          this.session.delete(iter.next()); 
        this.session.delete(ssMasterPO);
      } 
      this.session.flush();
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
  
  public List getStockGoods(String stockId) throws Exception {
    begin();
    ArrayList alist = new ArrayList();
    try {
      Query query = this.session.createQuery("select a.goodsId,b.name,b.unit,a.amount from com.js.oa.routine.resource.po.GoodsStockPO a, com.js.oa.routine.resource.po.GoodsPO b where a.goodsId=b.id and a.stockId=" + 
          
          stockId);
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public Boolean saveStockCheck(CsMasterPO csMasterPO, Object[] detail) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      this.session.save(csMasterPO);
      String[] goodsId = (String[])detail[0];
      String[] goodsName = (String[])detail[1];
      String[] goodsUnit = (String[])detail[2];
      String[] accAmount = (String[])detail[3];
      String[] factAmount = (String[])detail[4];
      String[] plAmount = (String[])detail[5];
      for (int i = 0; i < goodsId.length; i++) {
        CsDetailPO csDetailPO = new CsDetailPO();
        csDetailPO.setGoodsId(goodsId[i]);
        csDetailPO.setAccAmount(new Float(accAmount[i]));
        csDetailPO.setFactAmount(new Float(factAmount[i]));
        csDetailPO.setPlAmount(new Float(plAmount[i]));
        csDetailPO.setGoodsName(goodsName[i]);
        csDetailPO.setGoodsUnit(goodsUnit[i]);
        csDetailPO.setCsMaster(csMasterPO);
        csDetailPO.setSeq(new Integer(i + 1));
        this.session.save(csDetailPO);
      } 
      this.session.flush();
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
  
  public Map getStockCheck(String csMasterId) throws Exception {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    begin();
    try {
      Query query = this.session.createQuery("select aaa,bbb.stockName,ccc.empName from com.js.oa.routine.resource.po.CsMasterPO aaa,com.js.oa.routine.resource.po.StockPO bbb, com.js.system.vo.usermanager.EmployeeVO ccc where aaa.csStock=bbb.id and aaa.makeMan=ccc.empId and aaa.id=" + 

          
          csMasterId);
      Iterator<Object[]> iter = query.iterate();
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        hashMap.put("csMasterPO", obj[0]);
        hashMap.put("stockName", obj[1]);
        hashMap.put("makeManName", obj[2]);
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
    return hashMap;
  }
  
  public Boolean updateStockCheck(CsMasterPO csMasterPO, Object[] csDetail) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      CsMasterPO needModifyPO = (CsMasterPO)this.session.load(CsMasterPO.class, 
          csMasterPO.getId());
      needModifyPO.setCsDate(csMasterPO.getCsDate());
      needModifyPO.setCsMan(csMasterPO.getCsMan());
      needModifyPO.setRemark(csMasterPO.getRemark());
      boolean isCheck = false;
      if (csMasterPO.getCheckFlag().equals("Y") && 
        needModifyPO.getCheckFlag().equals("N"))
        isCheck = true; 
      Long stockId = needModifyPO.getCsStock();
      if (isCheck) {
        needModifyPO.setCheckFlag("Y");
        needModifyPO.setCheckMan(csMasterPO.getCheckMan());
        needModifyPO.setCheckManName(csMasterPO.getCheckManName());
        needModifyPO.setCheckDate(csMasterPO.getCheckDate());
      } 
      Iterator<CsDetailPO> iter = needModifyPO.getCsDetail().iterator();
      needModifyPO.setCsDetail(null);
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      String[] goodsId = (String[])csDetail[0];
      String[] goodsName = (String[])csDetail[1];
      String[] goodsUnit = (String[])csDetail[2];
      String[] accAmount = (String[])csDetail[3];
      String[] factAmount = (String[])csDetail[4];
      String[] plAmount = (String[])csDetail[5];
      Query query = null;
      for (int i = 0; i < goodsId.length; i++) {
        CsDetailPO csDetailPO = new CsDetailPO();
        csDetailPO.setGoodsId(goodsId[i]);
        csDetailPO.setAccAmount(new Float(accAmount[i]));
        csDetailPO.setFactAmount(new Float(factAmount[i]));
        csDetailPO.setPlAmount(new Float(plAmount[i]));
        csDetailPO.setGoodsName(goodsName[i]);
        csDetailPO.setGoodsUnit(goodsUnit[i]);
        csDetailPO.setCsMaster(needModifyPO);
        csDetailPO.setSeq(new Integer(i + 1));
        this.session.save(csDetailPO);
        if (isCheck) {
          if (Float.parseFloat(plAmount[i]) > 0.0F) {
            StockChangePO stockChangePO = new StockChangePO();
            stockChangePO.setScNo(csMasterPO.getId().toString());
            stockChangePO.setScDate(csMasterPO.getCsDate());
            stockChangePO.setScSeq(new Integer(i));
            stockChangePO.setChangeStock(stockId);
            stockChangePO.setGoodsId(goodsId[i]);
            stockChangePO.setChangeType("ACS");
            stockChangePO.setChangeAmount(new Float(plAmount[i]));
            this.session.save(stockChangePO);
            query = this.session.createQuery(
                "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
                goodsId[i] + 
                "' and aaa.stockId=" + stockId);
            iter = query.iterate();
            if (iter.hasNext()) {
              GoodsStockPO goodsStockPO = (GoodsStockPO)iter
                .next();
              goodsStockPO.setAmount(new Float(goodsStockPO
                    .getAmount().floatValue() + 
                    Float.parseFloat(plAmount[i])));
            } else {
              GoodsStockPO goodsStockPO = new GoodsStockPO();
              goodsStockPO.setGoodsId(goodsId[i]);
              goodsStockPO.setStockId(stockId);
              goodsStockPO.setAmount(new Float(plAmount[i]));
              this.session.save(goodsStockPO);
            } 
          } 
          if (Float.parseFloat(plAmount[i]) < 0.0F) {
            StockChangePO stockChangePO = new StockChangePO();
            stockChangePO.setScNo(csMasterPO.getId().toString());
            stockChangePO.setScDate(new Date());
            stockChangePO.setScSeq(new Integer(i));
            stockChangePO.setChangeStock(stockId);
            stockChangePO.setGoodsId(goodsId[i]);
            stockChangePO.setChangeType("DCS");
            stockChangePO.setChangeAmount(new Float(
                  -Float.parseFloat(plAmount[i])));
            this.session.save(stockChangePO);
            query = this.session.createQuery(
                "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
                goodsId[i] + 
                "' and aaa.stockId=" + stockId);
            iter = query.iterate();
            if (iter.hasNext()) {
              GoodsStockPO goodsStockPO = (GoodsStockPO)iter
                .next();
              goodsStockPO.setAmount(new Float(goodsStockPO
                    .getAmount().floatValue() - 
                    Float.parseFloat(plAmount[i])));
            } else {
              GoodsStockPO goodsStockPO = new GoodsStockPO();
              goodsStockPO.setGoodsId(goodsId[i]);
              goodsStockPO.setStockId(stockId);
              goodsStockPO.setAmount(new Float(-Float.parseFloat(
                      plAmount[i])));
              this.session.save(goodsStockPO);
            } 
          } 
        } 
      } 
      this.session.flush();
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
  
  public Boolean uncheckStockCheck(String csMasterId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      CsMasterPO csMasterPO = (CsMasterPO)this.session.load(CsMasterPO.class, 
          new Long(csMasterId));
      csMasterPO.setCheckFlag("N");
      Set csDetailSet = csMasterPO.getCsDetail();
      Iterator<CsDetailPO> iter = csDetailSet.iterator();
      CsDetailPO csDetailPO = null;
      GoodsStockPO goodsStockPO = null;
      Query query = null;
      Iterator<E> iter2 = null;
      String changeType = "";
      while (iter.hasNext()) {
        csDetailPO = iter.next();
        query = this.session.createQuery(
            "select aaa.changeType from com.js.oa.routine.resource.po.StockChangePO aaa where aaa.scNo=" + 
            csMasterPO.getId() + 
            " and aaa.goodsId='" + csDetailPO.getId() + "'" + 
            " and aaa.scSeq=" + csDetailPO.getSeq());
        iter2 = query.iterate();
        if (iter2.hasNext())
          changeType = iter2.next().toString(); 
        query = this.session.createQuery(
            "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
            csDetailPO.getGoodsId() + 
            "' and aaa.stockId=" + 
            csMasterPO.getCsStock());
        iter2 = query.iterate();
        if (iter2.hasNext()) {
          goodsStockPO = (GoodsStockPO)iter2.next();
          if (changeType.startsWith("A")) {
            goodsStockPO.setAmount(new Float(goodsStockPO.getAmount()
                  .floatValue() - 
                  csDetailPO.getPlAmount()
                  .floatValue()));
            continue;
          } 
          goodsStockPO.setAmount(new Float(goodsStockPO.getAmount()
                .floatValue() + 
                csDetailPO.getPlAmount()
                .floatValue()));
        } 
      } 
      this.session.delete(
          "from com.js.oa.routine.resource.po.StockChangePO aaa where aaa.scNo=" + 
          csMasterPO.getId() + 
          " and (aaa.changeType='ACS' or aaa.changeType='DCS')");
      this.session.flush();
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
  
  public Boolean deleteStockCheck(String ids) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      String[] id = { "" };
      if (ids.indexOf(",") > 0) {
        id = ids.split(",");
      } else {
        id[0] = ids;
      } 
      CsMasterPO csMasterPO = null;
      for (int i = 0; i < id.length; i++) {
        csMasterPO = (CsMasterPO)this.session.load(CsMasterPO.class, 
            new Long(id[i]));
        Iterator<CsDetailPO> iter = csMasterPO.getCsDetail().iterator();
        csMasterPO.setCsDetail(null);
        while (iter.hasNext())
          this.session.delete(iter.next()); 
        this.session.delete(csMasterPO);
      } 
      this.session.flush();
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
  
  private String getGoodsAmoutNoSession(String id, String ck) throws Exception {
    String returnValue = "0";
    try {
      Query query = this.session.createQuery(
          "select aaa.amount from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
          id + "' and aaa.stockId=" + ck);
      Iterator<E> iter2 = query.iterate();
      if (iter2.hasNext())
        returnValue = iter2.next().toString(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } 
    return returnValue;
  }
  
  private String getGoodsStockmoneyNoSession(String id, String ck) throws Exception {
    String returnValue = "0";
    try {
      Query query = this.session.createQuery(
          "select aaa.stockMoney from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
          id + "' and aaa.stockId=" + ck);
      Iterator<E> iter2 = query.iterate();
      if (iter2.hasNext())
        returnValue = iter2.next().toString(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } 
    return returnValue;
  }
  
  public String getGoodsAmount(String id, String ck) throws Exception {
    String returnValue = "0";
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.amount from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
          id + "' and aaa.stockId=" + ck);
      Iterator<E> iter2 = query.iterate();
      if (iter2.hasNext())
        returnValue = iter2.next().toString(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return returnValue;
  }
  
  public List getWFProcessInfoByStockId(String stockId) throws Exception {
    List<Long> list = new ArrayList();
    begin();
    try {
      StockPO stockPO = (StockPO)this.session.load(StockPO.class, 
          Long.valueOf(stockId));
      String chukuShenhe = (new StringBuilder(String.valueOf(stockPO.getChukuShenhe()))).toString();
      if (chukuShenhe.indexOf("1") != -1) {
        String sql = "select po.processId from com.js.oa.routine.resource.po.WorkFlowStockPO po where po.stock.id=" + 
          stockId + " order by po.stockType";
        List<E> tmplist = new ArrayList();
        tmplist = this.session.createQuery(sql).list();
        Long processId = null;
        if (!tmplist.isEmpty()) {
          processId = Long.valueOf(tmplist.get(0).toString());
          if (processId != null) {
            WFWorkFlowProcessPO processPO = 
              (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, 
                processId);
            list.add(processPO.getAccessDatabaseId());
            list.add(processPO.getWfWorkFlowProcessId());
            list.add(processPO.getWorkFlowProcessName());
          } else {
            list.clear();
            list = null;
          } 
        } 
      } else {
        list.clear();
        list = null;
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
    return list;
  }
  
  public List getWFProcessInfoByStockId(String stockId, String type) throws Exception {
    List<Long> list = new ArrayList();
    begin();
    try {
      StockPO stockPO = (StockPO)this.session.load(StockPO.class, 
          Long.valueOf(stockId));
      String chukuShenhe = (new StringBuilder(String.valueOf(stockPO.getChukuShenhe()))).toString();
      if (chukuShenhe.indexOf("1") != -1) {
        String sql = "select po.processId from com.js.oa.routine.resource.po.WorkFlowStockPO po where po.stock.id=" + 
          stockId + " and po.stockType = '" + type + "' order by po.stockType";
        List<E> tmplist = new ArrayList();
        tmplist = this.session.createQuery(sql).list();
        Long processId = null;
        if (!tmplist.isEmpty()) {
          processId = Long.valueOf(tmplist.get(0).toString());
          if (processId != null) {
            WFWorkFlowProcessPO processPO = 
              (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, 
                processId);
            list.add(processPO.getAccessDatabaseId());
            list.add(processPO.getWfWorkFlowProcessId());
            list.add(processPO.getWorkFlowProcessName());
          } else {
            list.clear();
            list = null;
          } 
        } 
      } else {
        list.clear();
        list = null;
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
    return list;
  }
  
  public String getStockCharger(String stockId) throws HibernateException {
    String ret = "";
    try {
      begin();
      ret = this.session.createQuery(" select po.stockPutName from com.js.oa.routine.resource.po.StockPO po where po.id = " + 
          stockId).list().get(0);
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ret;
  }
  
  public Integer updateOutStockCheck(String id, String checkStr) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      SsMasterPO po = (SsMasterPO)this.session.load(SsMasterPO.class, 
          Long.valueOf(id));
      po.setCheckFlag(checkStr);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Map updateOutFlag(String ids) throws Exception {
    Map<Object, Object> ret = new HashMap<Object, Object>(0);
    Connection conn = null;
    Statement stmt = null;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String serial = "";
      String goodsId = "";
      String goodsName = "";
      Query query = null;
      Iterator<GoodsStockPO> iter = null;
      String[] _ids = ids.split(",");
      for (int i = 0; i < _ids.length; i++) {
        SsMasterPO po = (SsMasterPO)this.session.load(SsMasterPO.class, 
            new Long(_ids[i]));
        if (!"1".equals(po.getSsOutFlag())) {
          Set d = po.getSsDetail();
          if (d != null && d.size() > 0) {
            int j = 0;
            boolean flag = false;
            for (Iterator<SsDetailPO> itor = d.iterator(); itor.hasNext(); j++) {
              SsDetailPO dpo = itor.next();
              if (po.getSsMoney().floatValue() >= 0.0F) {
                query = this.session.createQuery(
                    "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
                    dpo.getGoodsId() + 
                    "' and aaa.stockId=" + po.getSsStock());
                Iterator<GoodsStockPO> it = query.iterate();
                if (it.hasNext()) {
                  GoodsStockPO gp = it.next();
                  String isKucun = "0";
                  String isKucunYj = "0";
                  iter = this.session.createQuery("select po.isKucun from com.js.oa.routine.resource.po.StockPO po where po.id=" + po.getSsStock()).iterate();
                  if (iter.hasNext()) {
                    Object obj = iter.next();
                    if (obj != null)
                      isKucun = obj.toString(); 
                  } 
                  iter = this.session.createQuery("select po.isKucunYj from com.js.oa.routine.resource.po.StockPO po where po.id=" + po.getSsStock()).iterate();
                  if (iter.hasNext()) {
                    Object obj = iter.next();
                    if (obj != null)
                      isKucunYj = obj.toString(); 
                  } 
                  if ("1".equals(isKucunYj) || "1".equals(isKucun)) {
                    if (gp.getAmount().floatValue() + dpo.getAmount().floatValue() < 
                      dpo.getAmount().floatValue()) {
                      flag = true;
                      serial = String.valueOf(serial) + po.getSerial() + ",";
                      goodsId = String.valueOf(goodsId) + dpo.getGoodsId() + ",";
                      goodsName = String.valueOf(goodsName) + dpo.getGoodsName() + ",";
                      break;
                    } 
                  } else if (gp.getAmount().floatValue() < 
                    dpo.getAmount().floatValue()) {
                    flag = true;
                    serial = String.valueOf(serial) + po.getSerial() + ",";
                    goodsId = String.valueOf(goodsId) + dpo.getGoodsId() + ",";
                    goodsName = String.valueOf(goodsName) + dpo.getGoodsName() + ",";
                    break;
                  } 
                } 
              } else if (po.getSsMoney().floatValue() < 0.0F) {
                query = this.session.createQuery(
                    "select a.id,sum(b.changeAmount),sum(b.factMoney) from com.js.oa.routine.resource.po.GoodsPO a, com.js.oa.routine.resource.po.StockChangePO b, com.js.oa.routine.resource.po.SsMasterPO c where a.id = b.goodsId and a.id = '" + 
                    
                    dpo.getGoodsId() + "' and c.ssStock = " + 
                    po.getSsStock() + " and b.scNo = c.id and c.ssOutFlag = '1' and b.changeType like 'D%' group by a.id");
                Iterator<Object[]> it = query.iterate();
                Object[] obj = it.next();
                if (it.hasNext() && (new Float(obj[1].toString())).floatValue() < 
                  Math.abs(dpo.getAmount().floatValue())) {
                  flag = true;
                  serial = String.valueOf(serial) + po.getSerial() + ",";
                  goodsId = String.valueOf(goodsId) + dpo.getGoodsId() + ",";
                  goodsName = String.valueOf(goodsName) + dpo.getGoodsName() + ",";
                  break;
                } 
              } 
            } 
            if (!flag) {
              for (Iterator<SsDetailPO> iterator = d.iterator(); iterator.hasNext(); j++) {
                SsDetailPO dpo = iterator.next();
                GoodsPO goodsPO = (GoodsPO)this.session.load(GoodsPO.class, 
                    dpo.getGoodsId());
                StockChangePO stockChangePO = new StockChangePO();
                stockChangePO.setScNo(po.getId().toString());
                stockChangePO.setScDate(new Date());
                stockChangePO.setScSeq(new Integer(j));
                stockChangePO.setChangeType("DSS");
                stockChangePO.setChangeStock(po.getSsStock());
                stockChangePO.setDrawDept(po.getSsDept());
                stockChangePO.setGoodsId(dpo.getGoodsId());
                stockChangePO.setChangeAmount(dpo.getAmount());
                stockChangePO.setChangeMoney(dpo.getPrice());
                stockChangePO.setFactMoney(new Float(goodsPO
                      .getAveragePrice().floatValue() * 
                      dpo.getAmount().floatValue()));
                this.session.save(stockChangePO);
                String isKucun = "0";
                String isKucunYj = "0";
                iter = this.session.createQuery("select po.isKucun from com.js.oa.routine.resource.po.StockPO po where po.id=" + po.getSsStock()).iterate();
                if (iter.hasNext()) {
                  Object obj = iter.next();
                  if (obj != null)
                    isKucun = obj.toString(); 
                } 
                iter = this.session.createQuery("select po.isKucunYj from com.js.oa.routine.resource.po.StockPO po where po.id=" + po.getSsStock()).iterate();
                if (iter.hasNext()) {
                  Object obj = iter.next();
                  if (obj != null)
                    isKucunYj = obj.toString(); 
                } 
                query = this.session.createQuery(
                    "select aaa from com.js.oa.routine.resource.po.GoodsStockPO aaa where aaa.goodsId='" + 
                    dpo.getGoodsId() + 
                    "' and aaa.stockId=" + po.getSsStock());
                iter = query.iterate();
                if (iter.hasNext()) {
                  GoodsStockPO goodsStockPO = iter
                    .next();
                  if (!"1".equals(isKucun) && !"1".equals(isKucunYj)) {
                    goodsStockPO.setAmount(new Float(goodsStockPO
                          .getAmount()
                          .floatValue() - 
                          dpo.getAmount().floatValue()));
                    goodsStockPO.setStockMoney(new Float(
                          goodsStockPO
                          .getStockMoney().floatValue() - goodsPO
                          .getAveragePrice().floatValue() * 
                          dpo.getAmount().floatValue()));
                  } 
                } else {
                  GoodsStockPO goodsStockPO = new GoodsStockPO();
                  goodsStockPO.setGoodsId(dpo.getGoodsId());
                  goodsStockPO.setStockId(po.getSsStock());
                  if (!"1".equals(isKucun) && !"1".equals(isKucunYj)) {
                    goodsStockPO.setAmount(new Float(0.0F - dpo.getAmount().floatValue()));
                    goodsStockPO.setStockMoney(new Float(
                          Float.parseFloat(
                            "0") - goodsPO
                          .getAveragePrice().floatValue() * 
                          dpo.getAmount().floatValue()));
                  } 
                  this.session.save(goodsStockPO);
                } 
              } 
              this.session.flush();
              stmt.executeUpdate(
                  "update st_ssmaster set ss_outflag='1' where ss_no in(" + 
                  _ids[i] + ")");
            } 
          } 
        } 
      } 
      ret.put("ret", "true");
      ret.put("serial", serial);
      ret.put("goodsId", goodsId);
      ret.put("goodsName", goodsName);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean updateIntoStockMoney(PtMasterPO ptMasterPO, Object[] ptDetail) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      PtMasterPO needModifyPO = (PtMasterPO)this.session.load(PtMasterPO.class, 
          ptMasterPO.getId());
      needModifyPO.setPtMoney(ptMasterPO.getPtMoney());
      Iterator<PtDetailPO> iter = needModifyPO.getPtDetail().iterator();
      needModifyPO.setPtDetail(null);
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      String[] goodsId = (String[])ptDetail[0];
      String[] amount = (String[])ptDetail[1];
      String[] price = (String[])ptDetail[2];
      String[] money = (String[])ptDetail[3];
      String[] goodsName = (String[])ptDetail[4];
      String[] goodsUnit = (String[])ptDetail[5];
      String[] goodsSpecs = (String[])ptDetail[6];
      String[] returnReason = (ptDetail[7] == null) ? new String[goodsId.length] : (String[])ptDetail[7];
      for (int i = 0; i < goodsId.length; i++) {
        PtDetailPO ptDetailPO = new PtDetailPO();
        ptDetailPO.setGoodsId(goodsId[i]);
        if (amount[i] != null && amount[i].startsWith("--"))
          amount[i] = amount[i].substring(1); 
        if (money[i] != null && money[i].startsWith("--"))
          money[i] = money[i].substring(1); 
        ptDetailPO.setAmount(new Float(amount[i]));
        ptDetailPO.setMcost(new Float(price[i]));
        ptDetailPO.setGoodsMoney(new Float(money[i]));
        ptDetailPO.setGoodsName(goodsName[i]);
        ptDetailPO.setGoodsUnit(goodsUnit[i]);
        ptDetailPO.setGoodsSpecs(goodsSpecs[i]);
        ptDetailPO.setReturnReason(returnReason[i]);
        ptDetailPO.setPtMaster(needModifyPO);
        this.session.save(ptDetailPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Integer updateIntoStockCheck(String id, String checkStr) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      PtMasterPO po = (PtMasterPO)this.session.load(PtMasterPO.class, 
          Long.valueOf(id));
      po.setCheckFlag(checkStr);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  private Long getId() throws Exception {
    Long id = null;
    begin();
    try {
      Iterator iter = this.session.iterate(
          "select max(po.id) from com.js.oa.routine.resource.po.PtMasterPO po");
      int ptnum = 0;
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null && !obj.toString().equals("") && 
          !obj.toString().equals("null"))
          ptnum = Integer.parseInt(obj.toString()); 
      } 
      iter = this.session.iterate(
          "select max(po.id) from com.js.oa.routine.resource.po.SsMasterPO po");
      int ssnum = 0;
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null && !obj.toString().equals("") && 
          !obj.toString().equals("null"))
          ssnum = Integer.parseInt(obj.toString()); 
      } 
      if (ptnum >= ssnum) {
        id = Long.valueOf(ptnum + 1);
      } else {
        id = Long.valueOf(ssnum + 1);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return id;
  }
  
  public Map<String, String> getStockKucunMark(String stockid) throws Exception {
    String isKucun = "0";
    String isKucunYj = "0";
    Map<String, String> map = new HashMap<String, String>();
    Iterator iter = null;
    begin();
    try {
      iter = this.session.createQuery("select po.isKucun from com.js.oa.routine.resource.po.StockPO po where po.id=" + stockid).iterate();
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null)
          isKucun = obj.toString(); 
      } 
      map.put("isKucun", isKucun);
      iter = this.session.createQuery("select po.isKucunYj from com.js.oa.routine.resource.po.StockPO po where po.id=" + stockid).iterate();
      if (iter.hasNext()) {
        Object obj = iter.next();
        if (obj != null)
          isKucunYj = obj.toString(); 
      } 
      map.put("isKucunYj", isKucunYj);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return map;
  }
}
