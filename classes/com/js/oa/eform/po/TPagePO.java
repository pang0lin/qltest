package com.js.oa.eform.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TPagePO implements Serializable {
  private String pageDes;
  
  private String pageList;
  
  private String pageRef;
  
  private String pageContent;
  
  private String pagePath;
  
  private String pageFileName;
  
  private String pageOwner;
  
  private Date pageDate = null;
  
  private int pageSession;
  
  private int pageType;
  
  private int domainId;
  
  private Set tarea = null;
  
  private String pageName;
  
  private Long id;
  
  private String jsOnload;
  
  private String jsBeforeCommit;
  
  private String formClassName;
  
  private String formClassSaveMethod;
  
  private String formClassUpdateMethod;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long printPageId;
  
  private Long mobilePageId;
  
  private String initdata;
  
  private String initdatatype;
  
  private String datasourcename;
  
  private String fetchsql;
  
  private String interfacename;
  
  private String interfacemethod;
  
  private String interfacemethodpara;
  
  private String mappingfields;
  
  public Long getPrintPageId() {
    return this.printPageId;
  }
  
  public void setPrintPageId(Long printPageId) {
    this.printPageId = printPageId;
  }
  
  public Long getMobilePageId() {
    return this.mobilePageId;
  }
  
  public void setMobilePageId(Long mobilePageId) {
    this.mobilePageId = mobilePageId;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getFormClassName() {
    return this.formClassName;
  }
  
  public void setFormClassName(String formClassName) {
    this.formClassName = formClassName;
  }
  
  public String getFormClassSaveMethod() {
    return this.formClassSaveMethod;
  }
  
  public void setFormClassSaveMethod(String formClassSaveMethod) {
    this.formClassSaveMethod = formClassSaveMethod;
  }
  
  public String getFormClassUpdateMethod() {
    return this.formClassUpdateMethod;
  }
  
  public void setFormClassUpdateMethod(String formClassUpdateMethod) {
    this.formClassUpdateMethod = formClassUpdateMethod;
  }
  
  public String getJsOnload() {
    return this.jsOnload;
  }
  
  public void setJsOnload(String jsOnload) {
    this.jsOnload = jsOnload;
  }
  
  public String getJsBeforeCommit() {
    return this.jsBeforeCommit;
  }
  
  public void setJsBeforeCommit(String jsBeforeCommit) {
    this.jsBeforeCommit = jsBeforeCommit;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getPageDes() {
    return this.pageDes;
  }
  
  public void setPageDes(String pageDes) {
    this.pageDes = pageDes;
  }
  
  public String getPageList() {
    return this.pageList;
  }
  
  public void setPageList(String pageList) {
    this.pageList = pageList;
  }
  
  public String getPageRef() {
    return this.pageRef;
  }
  
  public void setPageRef(String pageRef) {
    this.pageRef = pageRef;
  }
  
  public String getPageContent() {
    return this.pageContent;
  }
  
  public void setPageContent(String pageContent) {
    this.pageContent = pageContent;
  }
  
  public String getPagePath() {
    return this.pagePath;
  }
  
  public void setPagePath(String pagePath) {
    this.pagePath = pagePath;
  }
  
  public String getPageFileName() {
    return this.pageFileName;
  }
  
  public void setPageFileName(String pageFileName) {
    this.pageFileName = pageFileName;
  }
  
  public String getPageOwner() {
    return this.pageOwner;
  }
  
  public void setPageOwner(String pageOwner) {
    this.pageOwner = pageOwner;
  }
  
  public Date getPageDate() {
    return this.pageDate;
  }
  
  public void setPageDate(Date pageDate) {
    this.pageDate = pageDate;
  }
  
  public int getPageSession() {
    return this.pageSession;
  }
  
  public void setPageSession(int pageSession) {
    this.pageSession = pageSession;
  }
  
  public int getPageType() {
    return this.pageType;
  }
  
  public void setPageType(int pageType) {
    this.pageType = pageType;
  }
  
  public int getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(int domainId) {
    this.domainId = domainId;
  }
  
  public Set getTarea() {
    return this.tarea;
  }
  
  public void setTarea(Set tarea) {
    this.tarea = tarea;
  }
  
  public String getPageName() {
    return this.pageName;
  }
  
  public void setPageName(String pageName) {
    this.pageName = pageName;
  }
  
  public String getInitdata() {
    return this.initdata;
  }
  
  public void setInitdata(String initdata) {
    this.initdata = initdata;
  }
  
  public String getInitdatatype() {
    return this.initdatatype;
  }
  
  public void setInitdatatype(String initdatatype) {
    this.initdatatype = initdatatype;
  }
  
  public String getDatasourcename() {
    return this.datasourcename;
  }
  
  public void setDatasourcename(String datasourcename) {
    this.datasourcename = datasourcename;
  }
  
  public String getFetchsql() {
    return this.fetchsql;
  }
  
  public void setFetchsql(String fetchsql) {
    this.fetchsql = fetchsql;
  }
  
  public String getInterfacename() {
    return this.interfacename;
  }
  
  public void setInterfacename(String interfacename) {
    this.interfacename = interfacename;
  }
  
  public String getInterfacemethod() {
    return this.interfacemethod;
  }
  
  public void setInterfacemethod(String interfacemethod) {
    this.interfacemethod = interfacemethod;
  }
  
  public String getInterfacemethodpara() {
    return this.interfacemethodpara;
  }
  
  public void setInterfacemethodpara(String interfacemethodpara) {
    this.interfacemethodpara = interfacemethodpara;
  }
  
  public String getMappingfields() {
    return this.mappingfields;
  }
  
  public void setMappingfields(String mappingfields) {
    this.mappingfields = mappingfields;
  }
}
