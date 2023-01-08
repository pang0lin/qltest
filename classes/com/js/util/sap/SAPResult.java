package com.js.util.sap;

import java.util.Map;

public class SAPResult {
  private String resultLog;
  
  private String resultXml;
  
  private Map resultTable = null;
  
  public String getResultLog() {
    return this.resultLog;
  }
  
  public void setResultLog(String resultLog) {
    this.resultLog = resultLog;
  }
  
  public String getResultXml() {
    return this.resultXml;
  }
  
  public void setResultXml(String resultXml) {
    this.resultXml = resultXml;
  }
  
  public Map getResultTable() {
    return this.resultTable;
  }
  
  public void setResultTable(Map resultTable) {
    this.resultTable = resultTable;
  }
  
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + (
      (this.resultLog == null) ? 0 : this.resultLog.hashCode());
    result = 31 * result + (
      (this.resultTable == null) ? 0 : this.resultTable.hashCode());
    result = 31 * result + (
      (this.resultXml == null) ? 0 : this.resultXml.hashCode());
    return result;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (getClass() != obj.getClass())
      return false; 
    SAPResult other = (SAPResult)obj;
    if (this.resultLog == null) {
      if (other.resultLog != null)
        return false; 
    } else if (!this.resultLog.equals(other.resultLog)) {
      return false;
    } 
    if (this.resultTable == null) {
      if (other.resultTable != null)
        return false; 
    } else if (!this.resultTable.equals(other.resultTable)) {
      return false;
    } 
    if (this.resultXml == null) {
      if (other.resultXml != null)
        return false; 
    } else if (!this.resultXml.equals(other.resultXml)) {
      return false;
    } 
    return true;
  }
}
