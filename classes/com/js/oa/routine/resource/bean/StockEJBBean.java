package com.js.oa.routine.resource.bean;

import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.po.WFPackagePO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.routine.resource.po.StockPO;
import com.js.oa.routine.resource.po.WorkFlowStockPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class StockEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(StockPO stockPO) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.id from com.js.oa.routine.resource.po.StockPO aaa where aaa.stockName='" + 
          stockPO.getStockName() + 
          "' and aaa.domainid=" + stockPO.getDomainid());
      Iterator itor = query.iterate();
      if (itor.hasNext()) {
        success = Boolean.FALSE;
      } else {
        Long stockId = (Long)this.session.save(stockPO);
        if (stockPO.getChukuShenhe().indexOf("1") != -1) {
          query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId=13");
          Iterator iter = query.iterate();
          WFPackagePO packagePO = new WFPackagePO();
          if (iter.hasNext())
            packagePO = query.iterate().next(); 
          query = this.session.createQuery("select aaa.immoFormId from com.js.oa.jsflow.po.WorkFlowImmoFormPO aaa where aaa.moduleId=13");
          iter = query.iterate();
          Long formId = new Long(0L);
          if (iter.hasNext())
            formId = query.iterate().next(); 
          String[] stock_types = { "采购进货流程", "领用出库流程", "领用退库流程", "物品退货流程" };
          for (int i = 0; i < 4; i++) {
            if (stockPO.getChukuShenhe().substring(i, i + 1).equals("1")) {
              WFWorkFlowProcessPO processPO = 
                new WFWorkFlowProcessPO();
              processPO.setAccessDatabaseId(formId);
              processPO.setWorkFlowProcessName(
                  String.valueOf(stockPO.getStockName()) + 
                  stock_types[i]);
              processPO.setProcessCreatedDate(new Date());
              processPO.setProcessDescription(
                  String.valueOf(stockPO.getStockName()) + 
                  stock_types[i]);
              processPO.setProcessType(1);
              processPO.setFormClassName("ResourceWorkFlow");
              processPO.setFormClassMethod("save");
              processPO.setFormClassCompMethod("complete");
              processPO.setWfPackage(packagePO);
              Long processId = (Long)this.session.save(processPO);
              WFActivityPO startActivityPO = new WFActivityPO();
              startActivityPO.setActivityBeginEnd(1);
              startActivityPO.setWfWorkFlowProcess(processPO);
              this.session.save(startActivityPO);
              WFActivityPO endActivityPO = new WFActivityPO();
              endActivityPO.setActivityBeginEnd(2);
              endActivityPO.setWfWorkFlowProcess(processPO);
              this.session.save(endActivityPO);
              WorkFlowStockPO wfsPO = new WorkFlowStockPO();
              wfsPO.setProcessId(processId);
              wfsPO.setStock(stockPO);
              wfsPO.setStockType(i);
              this.session.save(wfsPO);
            } 
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
  
  public List getStockIDName(String domainid) throws Exception {
    begin();
    ArrayList alist = new ArrayList();
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.stockName,aaa.chukuShenhe from com.js.oa.routine.resource.po.StockPO aaa where aaa.domainid=" + 
          domainid + 
          " order by aaa.id desc");
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public Boolean delete(String stockIds) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      boolean canDelete = true;
      Query query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb where bbb.id in (" + 
          stockIds + 
          ")");
      Iterator itor = query.iterate();
      if (itor.hasNext())
        canDelete = false; 
      query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb where bbb.id in (" + 
          stockIds + ")");
      itor = query.iterate();
      if (itor.hasNext())
        canDelete = false; 
      if (canDelete) {
        this.session.delete(
            "from com.js.oa.routine.resource.po.StockPO aaa where aaa.id in (" + 
            stockIds + ")");
        this.session.flush();
      } else {
        success = Boolean.FALSE;
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
  
  public String[] getSingleStock(String stockId) throws Exception {
    String[] stock = { "", "", "", "", "", "", "", "", "", "" };
    begin();
    try {
      Query query = this.session.createQuery("select aaa.stockName,aaa.stockPut,aaa.stockPutName,aaa.stockDesci,aaa.remark,aaa.stockApplyExtension,aaa.stockApplyExtensionId,aaa.chukuShenhe,aaa.isKucun,aaa.isKucunYj from com.js.oa.routine.resource.po.StockPO aaa where aaa.id=" + 
          
          stockId);
      Iterator<Object[]> itor = query.iterate();
      if (itor.hasNext()) {
        Object[] obj = itor.next();
        stock[0] = obj[0].toString();
        stock[1] = obj[1].toString();
        stock[2] = obj[2].toString();
        stock[3] = (obj[3] == null) ? "" : obj[3].toString();
        stock[4] = (obj[4] == null) ? "" : obj[4].toString();
        stock[5] = (obj[5] == null) ? "" : obj[5].toString();
        stock[6] = (obj[6] == null) ? "" : obj[6].toString();
        stock[7] = obj[7].toString();
        stock[8] = obj[8].toString();
        stock[9] = (obj[9] == null) ? "0" : obj[9].toString();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stock;
  }
  
  public Boolean update(StockPO stockPO) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      Query query = this.session.createQuery(
          "select aaa.id from com.js.oa.routine.resource.po.StockPO aaa where aaa.stockName='" + 
          stockPO.getStockName() + 
          "' and aaa.domainid=" + stockPO.getDomainid() + 
          " and aaa.id<>" + stockPO.getId());
      Iterator itor = query.iterate();
      if (itor.hasNext()) {
        success = Boolean.FALSE;
      } else {
        StockPO updateStockPO = (StockPO)this.session.load(StockPO.class, 
            stockPO.getId());
        if (updateStockPO.getChukuShenhe().indexOf("1") < 0 && 
          stockPO.getChukuShenhe().indexOf("1") != -1) {
          query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId=13");
          Iterator iter = query.iterate();
          WFPackagePO packagePO = new WFPackagePO();
          if (iter.hasNext())
            packagePO = query.iterate()
              .next(); 
          query = this.session.createQuery("select aaa.immoFormId from com.js.oa.jsflow.po.WorkFlowImmoFormPO aaa where aaa.moduleId=13");
          iter = query.iterate();
          Long formId = new Long(0L);
          if (iter.hasNext())
            formId = query.iterate().next(); 
          Long processId0 = new Long(0L);
          String[] stock_types = { "采购进货流程", "领用出库流程", "领用退库流程", 
              "物品退货流程" };
          for (int i = 0; i < 4; i++) {
            query = this.session.createQuery("select aaa.wfWorkFlowProcessId from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa where aaa.workFlowProcessName like '%" + 
                updateStockPO.getStockName() + 
                
                "领用流程" + 
                "%'");
            iter = query.iterate();
            if (iter.hasNext()) {
              processId0 = query.iterate()
                .next();
              WFWorkFlowProcessPO processPO = 
                
                (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, 
                  processId0);
              processPO.setWorkFlowProcessName(
                  String.valueOf(stockPO.getStockName()) + 
                  stock_types[i]);
              processPO.setProcessDescription(
                  String.valueOf(stockPO.getStockName()) + 
                  stock_types[i]);
              stmt.executeUpdate("update JSF_WORKFLOWSTOCK set STOCK_TYPE = '0' where STOCKID = " + updateStockPO.getId() + " and PROCESSID = " + processId0);
            } else {
              query = this.session.createQuery("select aaa.wfWorkFlowProcessId from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa where aaa.workFlowProcessName like '%" + 
                  updateStockPO.getStockName() + 
                  stock_types[i] + 
                  "%'");
              iter = query.iterate();
              if (iter.hasNext()) {
                processId0 = query.iterate()
                  .next();
                WFWorkFlowProcessPO processPO = 
                  
                  (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, 
                    processId0);
                processPO.setWorkFlowProcessName(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
                processPO.setProcessDescription(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
              } else if (stockPO.getChukuShenhe().substring(i, i + 1)
                .equals("1")) {
                WFWorkFlowProcessPO processPO = 
                  new WFWorkFlowProcessPO();
                processPO.setAccessDatabaseId(formId);
                processPO.setWorkFlowProcessName(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
                processPO.setProcessCreatedDate(new Date());
                processPO.setProcessDescription(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
                processPO.setProcessType(1);
                processPO.setFormClassName(
                    "ResourceWorkFlow");
                processPO.setFormClassMethod("save");
                processPO.setFormClassCompMethod("complete");
                processPO.setWfPackage(packagePO);
                Long processId = (Long)this.session.save(
                    processPO);
                WFActivityPO startActivityPO = 
                  new WFActivityPO();
                startActivityPO.setActivityBeginEnd(1);
                startActivityPO.setWfWorkFlowProcess(
                    processPO);
                Long actId = (Long)this.session.save(
                    startActivityPO);
                WFActivityPO endActivityPO = 
                  new WFActivityPO();
                endActivityPO.setActivityBeginEnd(2);
                endActivityPO.setWfWorkFlowProcess(
                    processPO);
                Long eactId = (Long)this.session.save(
                    endActivityPO);
                WorkFlowStockPO wfsPO = new WorkFlowStockPO();
                wfsPO.setProcessId(processId);
                wfsPO.setStock(updateStockPO);
                wfsPO.setStockType(i);
                Long long_1 = (Long)this.session.save(wfsPO);
              } 
            } 
          } 
        } else {
          query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId=13");
          Iterator iter1 = query.iterate();
          WFPackagePO packagePO = new WFPackagePO();
          if (iter1.hasNext())
            packagePO = query.iterate()
              .next(); 
          Long formId = new Long(0L);
          String[] stock_types = { "采购进货流程", "领用出库流程", "领用退库流程", 
              "物品退货流程" };
          for (int i = 0; i < 4; i++) {
            query = this.session.createQuery("select aaa.wfWorkFlowProcessId from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa where aaa.workFlowProcessName like '%" + 
                updateStockPO.getStockName() + 
                
                "领用流程" + 
                "%'");
            Iterator iter = query.iterate();
            if (iter.hasNext()) {
              formId = query.iterate()
                .next();
              WFWorkFlowProcessPO processPO = 
                
                (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, 
                  formId);
              processPO.setWorkFlowProcessName(
                  String.valueOf(stockPO.getStockName()) + 
                  stock_types[i]);
              processPO.setProcessDescription(
                  String.valueOf(stockPO.getStockName()) + 
                  stock_types[i]);
              stmt.executeUpdate("update JSF_WORKFLOWSTOCK set STOCK_TYPE = '0' where STOCKID = " + updateStockPO.getId() + " and PROCESSID = " + formId);
            } else {
              query = this.session.createQuery("select aaa.wfWorkFlowProcessId from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa where aaa.workFlowProcessName like '%" + 
                  updateStockPO.getStockName() + 
                  stock_types[i] + 
                  "%'");
              iter = query.iterate();
              if (iter.hasNext()) {
                formId = query.iterate()
                  .next();
                WFWorkFlowProcessPO processPO = 
                  
                  (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, 
                    formId);
                processPO.setWorkFlowProcessName(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
                processPO.setProcessDescription(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
              } else if (stockPO.getChukuShenhe().substring(i, i + 1)
                .equals("1")) {
                WFWorkFlowProcessPO processPO = 
                  new WFWorkFlowProcessPO();
                processPO.setAccessDatabaseId(formId);
                processPO.setWorkFlowProcessName(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
                processPO.setProcessCreatedDate(new Date());
                processPO.setProcessDescription(
                    String.valueOf(stockPO.getStockName()) + 
                    stock_types[i]);
                processPO.setProcessType(1);
                processPO.setFormClassName(
                    "ResourceWorkFlow");
                processPO.setFormClassMethod("save");
                processPO.setFormClassCompMethod("complete");
                processPO.setWfPackage(packagePO);
                Long processId = (Long)this.session.save(
                    processPO);
                WFActivityPO startActivityPO = 
                  new WFActivityPO();
                startActivityPO.setActivityBeginEnd(1);
                startActivityPO.setWfWorkFlowProcess(
                    processPO);
                Long actId = (Long)this.session.save(
                    startActivityPO);
                WFActivityPO endActivityPO = 
                  new WFActivityPO();
                endActivityPO.setActivityBeginEnd(2);
                endActivityPO.setWfWorkFlowProcess(
                    processPO);
                Long eactId = (Long)this.session.save(
                    endActivityPO);
                WorkFlowStockPO wfsPO = new WorkFlowStockPO();
                wfsPO.setProcessId(processId);
                wfsPO.setStock(updateStockPO);
                wfsPO.setStockType(i);
                Long long_1 = (Long)this.session.save(wfsPO);
              } 
            } 
          } 
        } 
        updateStockPO.setStockName(stockPO.getStockName());
        updateStockPO.setStockPut(stockPO.getStockPut());
        updateStockPO.setStockPutName(stockPO.getStockPutName());
        updateStockPO.setStockDesci(stockPO.getStockDesci());
        updateStockPO.setRemark(stockPO.getRemark());
        updateStockPO.setStockApplyExtension(stockPO
            .getStockApplyExtension());
        updateStockPO.setStockApplyExtensionId(stockPO
            .getStockApplyExtensionId());
        updateStockPO.setChukuShenhe(stockPO.getChukuShenhe());
        updateStockPO.setIsKucun(stockPO.getIsKucun());
        updateStockPO.setIsKucunYj(stockPO.getIsKucunYj());
        this.session.update(updateStockPO);
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
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
          "select aaa.id from com.js.oa.routine.resource.po.StockPO aaa where " + 
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
  
  public List getUserManaStock(String userId, String domainid) throws Exception {
    begin();
    ArrayList stockList = new ArrayList();
    try {
      Query query = this.session.createQuery(
          "select aaa.id,aaa.stockName,aaa.chukuShenhe from com.js.oa.routine.resource.po.StockPO aaa where aaa.stockPut like '%$" + 
          userId + 
          "$%' and aaa.domainid=" + domainid + " order by aaa.id asc");
      stockList = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stockList;
  }
  
  public List getUserManaStock(String userId, String orgId, String domainid) throws Exception {
    begin();
    ArrayList stockList = new ArrayList();
    try {
      StringBuffer selectStockApplyExtensionId = new StringBuffer();
      selectStockApplyExtensionId.append(
          "select stockPO.id,stockPO.stockApplyExtensionId from");
      selectStockApplyExtensionId.append(
          " com.js.oa.routine.resource.po.StockPO stockPO where stockPO.domainid=" + 
          domainid);
      Query query = this.session.createQuery(selectStockApplyExtensionId
          .toString());
      List<Object[]> list = query.list();
      ArrayList<Object> stockIdList = new ArrayList();
      if (list != null && list.size() != 0)
        for (int j = 0; j < list.size(); j++) {
          Object[] obj = list.get(j);
          String stockApExtStr = obj[1].toString();
          if (stockApExtStr != null && 
            stockApExtStr.trim().length() != 0) {
            String[] orgAndUserIdString = splitWorker(stockApExtStr, 
                '*', false);
            if (orgAndUserIdString != null)
              for (int k = 0; k < orgAndUserIdString.length; k++) {
                if (orgAndUserIdString[k].indexOf("$") == -1) {
                  if (orgId.equals(orgAndUserIdString[k]))
                    stockIdList.add(obj[0]); 
                } else {
                  String[] userIdArray = splitWorker(
                      orgAndUserIdString[k], '$', false);
                  if (userIdArray != null)
                    for (int m = 0; m < userIdArray.length; 
                      m++) {
                      if (userId.equals(userIdArray[m])) {
                        stockIdList.add(obj[0]);
                        break;
                      } 
                    }  
                } 
              }  
          } 
        }  
      StringBuffer stockIdBuffer = new StringBuffer();
      for (int i = 0; i < stockIdList.size(); i++) {
        stockIdBuffer.append(stockIdList.get(i));
        stockIdBuffer.append(",");
      } 
      stockIdBuffer.append(",''");
      StringBuffer selectStock = new StringBuffer();
      selectStock.append("select aaa.id,aaa.stockName from ");
      selectStock.append("com.js.oa.routine.resource.po.StockPO aaa ");
      selectStock.append("where aaa.stockPut like '%$");
      selectStock.append(userId);
      selectStock.append("$%'");
      selectStock.append(" or aaa.id in(");
      selectStock.append(stockIdBuffer.toString());
      selectStock.append(")");
      Query stockResultQuery = this.session.createQuery(selectStock.toString());
      stockList = (ArrayList)stockResultQuery.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stockList;
  }
  
  private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
    if (str == null)
      return null; 
    int len = str.length();
    if (len == 0)
      return null; 
    List<String> list = new ArrayList();
    int i = 0, start = 0;
    boolean match = false;
    boolean lastMatch = false;
    while (i < len) {
      if (str.charAt(i) == separatorChar) {
        if (match || preserveAllTokens) {
          list.add(str.substring(start, i));
          match = false;
          lastMatch = true;
        } 
        start = ++i;
        continue;
      } 
      lastMatch = false;
      match = true;
      i++;
    } 
    if (match || (preserveAllTokens && lastMatch))
      list.add(str.substring(start, i)); 
    return list.<String>toArray(new String[list.size()]);
  }
  
  public List getStockIDName(String domainid, String wherePara) throws Exception {
    begin();
    ArrayList alist = new ArrayList();
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.stockName,aaa.chukuShenhe from com.js.oa.routine.resource.po.StockPO aaa where aaa.domainid=" + 
          domainid + " and (" + wherePara + 
          ") order by aaa.id asc");
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List getWorkFlowStock(Long stockId) throws Exception {
    begin();
    List<String> alist = new ArrayList();
    try {
      Query query = this.session.createQuery("select aaa.processId,aaa.stockType from com.js.oa.routine.resource.po.WorkFlowStockPO aaa where aaa.stock.id=" + stockId + 
          " and aaa.stockType is not null " + 
          " order by aaa.stockType asc");
      List<Object[]> list = query.list();
      String[] stockFlowType = { "0", "0", "0", "0" };
      if (list != null && list.size() > 0)
        for (int j = 0; j < list.size(); j++) {
          Object[] temp = list.get(j);
          if ("0".equals(temp[1].toString())) {
            stockFlowType[0] = temp[0].toString();
          } else if ("1".equals(temp[1].toString())) {
            stockFlowType[1] = temp[0].toString();
          } else if ("2".equals(temp[1].toString())) {
            stockFlowType[2] = temp[0].toString();
          } else if ("3".equals(temp[1].toString())) {
            stockFlowType[3] = temp[0].toString();
          } 
        }  
      for (int i = 0; i < 4; i++)
        alist.add(stockFlowType[i]); 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List getAllStock(String userId, String domainid) throws Exception {
    begin();
    ArrayList stockList = new ArrayList();
    String sql = "select aaa.id,aaa.stockName,aaa.chukuShenhe from com.js.oa.routine.resource.po.StockPO aaa where aaa.domainid=" + 
      domainid;
    try {
      Query query = this.session.createQuery(sql);
      stockList = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stockList;
  }
  
  public List getUserManaStockSong(String stockWhere, String domainid) throws Exception {
    begin();
    ArrayList stockList = new ArrayList();
    String sql = "select aaa.id,aaa.stockName,aaa.chukuShenhe from com.js.oa.routine.resource.po.StockPO aaa where aaa.domainid=" + 
      domainid + " and " + stockWhere;
    try {
      Query query = this.session.createQuery(sql);
      stockList = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stockList;
  }
  
  public String getStockNameById(String stockId) throws Exception {
    begin();
    ArrayList<String> stockList = new ArrayList();
    String stockName = "";
    String sql = "select aaa.stockName from com.js.oa.routine.resource.po.StockPO aaa where aaa.domainid=0 and aaa.id=" + 
      stockId;
    try {
      Query query = this.session.createQuery(sql);
      stockList = (ArrayList)query.list();
      stockName = stockList.get(0);
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stockName;
  }
}
