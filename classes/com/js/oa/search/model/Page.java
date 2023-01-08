package com.js.oa.search.model;

import java.util.List;

public class Page {
  private int curPage;
  
  private int maxPage;
  
  private Long maxRowCount;
  
  private int rowsPerPage = 15;
  
  private List<?> data;
  
  private SearchTbModel[] arrmodel;
  
  public SearchTbModel[] getArrmodel() {
    return this.arrmodel;
  }
  
  public void setArrmodel(SearchTbModel[] arrmodel) {
    this.arrmodel = arrmodel;
  }
  
  public int getCurPage() {
    return this.curPage;
  }
  
  public void setCurPage(int curPage) {
    this.curPage = curPage;
  }
  
  public int getMaxPage() {
    return this.maxPage;
  }
  
  public void setMaxPage(int maxPage) {
    this.maxPage = maxPage;
  }
  
  public List<?> getData() {
    return this.data;
  }
  
  public void setData(List<?> data) {
    this.data = data;
  }
  
  public Long getMaxRowCount() {
    return this.maxRowCount;
  }
  
  public void setMaxRowCount(Long amaxRowCountxRowCount) {
    this.maxRowCount = amaxRowCountxRowCount;
  }
  
  public int getRowsPerPage() {
    return this.rowsPerPage;
  }
  
  public void setRowsPerPage(int rowsPerPage) {
    this.rowsPerPage = rowsPerPage;
  }
  
  public void countMaxPage() {
    if (this.maxRowCount.longValue() % this.rowsPerPage == 0L) {
      this.maxPage = (int)(this.maxRowCount.longValue() / this.rowsPerPage);
    } else {
      this.maxPage = (int)(this.maxRowCount.longValue() / this.rowsPerPage + 1L);
    } 
  }
}
