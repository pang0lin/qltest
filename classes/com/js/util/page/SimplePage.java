package com.js.util.page;

import java.util.Iterator;
import java.util.List;

public class SimplePage {
  private int pageSize = 10;
  
  private int currentPage = 1;
  
  private int recordCount = 0;
  
  private int pageCount = 0;
  
  private Iterator resultIterator = null;
  
  private List resultList = null;
  
  private String PO;
  
  private String para;
  
  private String where;
  
  private PageImpl pageImpl;
  
  public SimplePage(String para, String PO, String where) {
    init(para, PO, where);
  }
  
  public SimplePage(String para, String PO) {
    init(para, PO, "");
  }
  
  public SimplePage(String PO) {
    init("*", PO, "");
  }
  
  public SimplePage() {}
  
  public void init(String para, String PO, String where) {
    this.para = para;
    this.PO = PO;
    this.where = where;
    this.pageImpl = new PageImpl();
  }
  
  public List getResultList() {
    this.resultList = this.pageImpl.getResultList(this.para, this.PO, this.where, 
        this.pageSize, this.currentPage);
    return this.resultList;
  }
  
  public Iterator getResultPO() {
    this.resultIterator = this.pageImpl.getResultPO(this.para, this.PO, this.where, 
        this.pageSize, this.currentPage);
    return this.resultIterator;
  }
  
  public int getPageCount() {
    if (this.pageCount == 0) {
      this.recordCount = this.pageImpl.getRecordCount();
      this.pageCount = this.recordCount / this.pageSize;
    } 
    return this.pageCount;
  }
  
  public int getPageSize() {
    return this.pageSize;
  }
  
  public int getRecordCount() {
    if (this.recordCount == 0) {
      this.recordCount = this.pageImpl.getRecordCount();
      setPageCount(this.recordCount / this.pageSize);
    } 
    return this.recordCount;
  }
  
  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }
  
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
  
  public int getcurrentPage() {
    return this.currentPage;
  }
  
  public void setcurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }
}
