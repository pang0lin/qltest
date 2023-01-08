package com.js.oa.search.client;

import org.apache.struts.action.ActionForm;

public class SearchClientForm extends ActionForm {
  public String keys;
  
  public String classSearch;
  
  public String getKeys() {
    return this.keys;
  }
  
  public void setKeys(String keys) {
    this.keys = keys;
  }
  
  public String getClassSearch() {
    return this.classSearch;
  }
  
  public void setClassSearch(String classSearch) {
    this.classSearch = classSearch;
  }
}
