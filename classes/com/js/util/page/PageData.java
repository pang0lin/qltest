package com.js.util.page;

import java.util.List;

public class PageData {
  private List dataList = null;
  
  private int pageSize;
  
  private int recordCount;
  
  private int offset;
  
  public List getDataList() {
    return this.dataList;
  }
  
  public void setDataList(List dataList) {
    this.dataList = dataList;
  }
  
  public int getPageSize() {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  
  public int getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }
  
  public int getOffset() {
    return this.offset;
  }
  
  public void setOffset(int offset) {
    this.offset = offset;
  }
}
