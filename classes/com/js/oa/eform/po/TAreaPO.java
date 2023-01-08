package com.js.oa.eform.po;

import java.io.Serializable;
import java.util.Set;

public class TAreaPO implements Serializable {
  private TAreaTypePO areatype;
  
  private TPagePO tpage;
  
  private Set telt = null;
  
  private String areaTMC;
  
  private String areaName;
  
  private String areaTable;
  
  private String areaShow;
  
  private String areaSQL;
  
  private String areaAction;
  
  private String areaAlterColor;
  
  private int areaSFXS;
  
  private String areaRef;
  
  private String areaSQLEvent;
  
  private String areaDes;
  
  private Long areaPageCount;
  
  private Long areaColumns;
  
  private Long id;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public TAreaTypePO getAreatype() {
    return this.areatype;
  }
  
  public void setAreatype(TAreaTypePO areatype) {
    this.areatype = areatype;
  }
  
  public TPagePO getTpage() {
    return this.tpage;
  }
  
  public void setTpage(TPagePO tpage) {
    this.tpage = tpage;
  }
  
  public Set getTelt() {
    return this.telt;
  }
  
  public void setTelt(Set telt) {
    this.telt = telt;
  }
  
  public String getAreaTMC() {
    return this.areaTMC;
  }
  
  public void setAreaTMC(String areaTMC) {
    this.areaTMC = areaTMC;
  }
  
  public String getAreaName() {
    return this.areaName;
  }
  
  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }
  
  public String getAreaTable() {
    return this.areaTable;
  }
  
  public void setAreaTable(String areaTable) {
    this.areaTable = areaTable;
  }
  
  public String getAreaShow() {
    return this.areaShow;
  }
  
  public void setAreaShow(String areaShow) {
    this.areaShow = areaShow;
  }
  
  public String getAreaSQL() {
    return this.areaSQL;
  }
  
  public void setAreaSQL(String areaSQL) {
    this.areaSQL = areaSQL;
  }
  
  public String getAreaAction() {
    return this.areaAction;
  }
  
  public void setAreaAction(String areaAction) {
    this.areaAction = areaAction;
  }
  
  public String getAreaAlterColor() {
    return this.areaAlterColor;
  }
  
  public void setAreaAlterColor(String areaAlterColor) {
    this.areaAlterColor = areaAlterColor;
  }
  
  public Long getAreaPageCount() {
    return this.areaPageCount;
  }
  
  public void setAreaPageCount(Long areaPageCount) {
    this.areaPageCount = areaPageCount;
  }
  
  public int getAreaSFXS() {
    return this.areaSFXS;
  }
  
  public void setAreaSFXS(int areaSFXS) {
    this.areaSFXS = areaSFXS;
  }
  
  public String getAreaRef() {
    return this.areaRef;
  }
  
  public void setAreaRef(String areaRef) {
    this.areaRef = areaRef;
  }
  
  public String getAreaSQLEvent() {
    return this.areaSQLEvent;
  }
  
  public void setAreaSQLEvent(String areaSQLEvent) {
    this.areaSQLEvent = areaSQLEvent;
  }
  
  public String getAreaDes() {
    return this.areaDes;
  }
  
  public void setAreaDes(String areaDes) {
    this.areaDes = areaDes;
  }
  
  public Long getAreaColumns() {
    return this.areaColumns;
  }
  
  public void setAreaColumns(Long areaColumns) {
    this.areaColumns = areaColumns;
  }
}
