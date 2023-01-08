package com.js.oa.search.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PageInfor implements Serializable {
  private int curPage;
  
  private int maxPage;
  
  private Long maxRowCount;
  
  private int rowsPerPage;
  
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
  
  public Long getMaxRowCount() {
    return this.maxRowCount;
  }
  
  public void setMaxRowCount(Long maxRowCount) {
    this.maxRowCount = maxRowCount;
  }
  
  public int getRowsPerPage() {
    return this.rowsPerPage;
  }
  
  public void setRowsPerPage(int rowsPerPage) {
    this.rowsPerPage = rowsPerPage;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", reDate())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PageInfor))
      return false; 
    PageInfor castOther = (PageInfor)other;
    return (new EqualsBuilder())
      .append(reDate(), castOther.reDate())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(reDate())
      .toHashCode();
  }
  
  public Date reDate() {
    return new Date();
  }
}
