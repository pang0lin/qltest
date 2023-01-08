package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ReportFormsActionForm extends ActionForm {
  private String searchStock;
  
  private String searchStockId;
  
  private String searchStockName;
  
  private String searchType;
  
  private String searchCountType;
  
  private String searchGoodsType;
  
  private String supp;
  
  private String invoiceNO;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getSearchStock() {
    return this.searchStock;
  }
  
  public void setSearchStock(String searchStock) {
    this.searchStock = searchStock;
  }
  
  public String getSearchStockId() {
    return this.searchStockId;
  }
  
  public void setSearchStockId(String searchStockId) {
    this.searchStockId = searchStockId;
  }
  
  public String getSearchStockName() {
    return this.searchStockName;
  }
  
  public void setSearchStockName(String searchStockName) {
    this.searchStockName = searchStockName;
  }
  
  public String getSearchType() {
    return this.searchType;
  }
  
  public void setSearchType(String searchType) {
    this.searchType = searchType;
  }
  
  public String getSearchCountType() {
    return this.searchCountType;
  }
  
  public void setSearchCountType(String searchCountType) {
    this.searchCountType = searchCountType;
  }
  
  public String getSearchGoodsType() {
    return this.searchGoodsType;
  }
  
  public void setSearchGoodsType(String searchGoodsType) {
    this.searchGoodsType = searchGoodsType;
  }
  
  public String getInvoiceNO() {
    return this.invoiceNO;
  }
  
  public void setInvoiceNO(String invoiceNO) {
    this.invoiceNO = invoiceNO;
  }
  
  public String getSupp() {
    return this.supp;
  }
  
  public void setSupp(String supp) {
    this.supp = supp;
  }
}
